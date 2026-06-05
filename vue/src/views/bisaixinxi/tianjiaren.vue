<template>
    <div class="views-bisaixinxi-list">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 比赛信息查询 </span>
                    </div>
                </template>

                <div class="form-search">
                    <el-form @submit.prevent.stop :inline="true" size="small">

                        <el-form-item label="比赛标题">
                            <el-input v-model="search.bisaibiaoti"></el-input>
                        </el-form-item>
                        <el-form-item label="比赛类型">
                            <el-select v-model="search.bisaileixing"><el-option label="请选择" value=""></el-option><e-select-option type="option" module="bisaileixing" value="id" label="leixingmingcheng"></e-select-option></el-select>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="searchSubmit" icon="el-icon-search">查询</el-button>
                        </el-form-item>
                    </el-form>
                </div>

                <el-table border :data="lists" style="width: 100%" @sort-change="sortChange" highlight-current-row>
                    <el-table-column type="index" label="#"></el-table-column>
                    <!-- 序号 -->

                    <el-table-column prop="bisaibianhao" label="比赛编号" >
                        <template #default="{ row }"> {{ row.bisaibianhao }} </template>
                    </el-table-column>
                    <el-table-column prop="bisaibiaoti" label="比赛标题" >
                        <template #default="{ row }"> {{ row.bisaibiaoti }} </template>
                    </el-table-column>
                    <el-table-column prop="bisaileixing" label="比赛类型" >
                        <template #default="{ row }"> <e-select-view module="bisaileixing" :value="row.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </template>
                    </el-table-column>
                    <el-table-column prop="bisaimoshi" label="比赛模式" >
                        <template #default="{ row }"> {{ row.bisaimoshi }} </template>
                    </el-table-column>
                    <el-table-column prop="bisaitupian" label="比赛图片" >
                        <template #default="{ row }"> <e-img :src="row.bisaitupian" style="max-width: 120px" /> </template>
                    </el-table-column>
                    <el-table-column prop="canjiaqiudui" label="参加球队" >
                        <template #default="{ row }"> {{ row.canjiaqiudui }} </template>
                    </el-table-column>
                    <el-table-column prop="bisaichangdi" label="比赛场地">
                        <template #default="{ row }"> {{ row.bisaichangdi }} </template>
                    </el-table-column>
                    <el-table-column prop="tianjiaren" label="添加人" >
                        <template #default="{ row }"> {{ row.tianjiaren }} </template>
                    </el-table-column>

                    <el-table-column label="操作" fixed="right" width="250">
                        <template #default="{ row }">
                            <el-button-group>
                                <el-button type="primary" :icon="Plus" size="small" @click="$router.push('/admin/bisaibaoming/add?id=' + row.id)">添加比赛报名</el-button>

                                <el-button type="default" :icon="Search" size="small" @click="$router.push('/admin/bisaibaoming?bisaixinxiid=' + row.id)">查询比赛报名</el-button>

                                <el-button type="primary" :icon="Plus" size="small" @click="$router.push('/admin/saichengbiao/add?id=' + row.id)">添加赛程表</el-button>

                                <el-button type="default" :icon="Search" size="small" @click="$router.push('/admin/saichengbiao?bisaixinxiid=' + row.id)">查询赛程表</el-button>

                                <el-tooltip effect="dark" content="详情" placement="top-start"><el-button type="info" :icon="InfoFilled" size="small" @click="$router.push('/admin/bisaixinxi/detail?id=' + row.id)">详情</el-button> </el-tooltip>
                                <el-tooltip effect="dark" content="编辑" placement="top-start"><el-button type="success" :icon="Edit" size="small" @click="$router.push('/admin/bisaixinxi/updt?id=' + row.id)">编辑</el-button> </el-tooltip>
                                <el-tooltip effect="dark" content="删除" placement="top-start"><el-button type="danger" :icon="Delete" size="small" @click="deleteItems(row.id)">删除</el-button> </el-tooltip>
                            </el-button-group>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="e-pages" style="margin-top: 10px; text-align: center">
                    <el-pagination @current-change="loadList" :page-sizes="[12, 24, 36, 48, 60]" v-model:current-page="search.page" v-model:page-size="search.pagesize" @size-change="sizeChange" layout="total, sizes, prev, pager, next" :total="totalCount"> </el-pagination>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import DB from "@/utils/db";
    import router from "@/router";

    import { ref, reactive, watch, unref, onBeforeMount } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { canBisaixinxiSelect, useBisaixinxiSelect, canBisaixinxiDelete } from "@/module";
    import { extend } from "@/utils/extend";
    import { ElMessageBox, ElMessage } from "element-plus";
    import { Plus, Search, InfoFilled, Edit, Delete } from "@element-plus/icons-vue";

    const route = useRoute();
    const search = reactive({
        bisaibianhao: "",
        bisaibiaoti: "",
        bisaileixing: "",
        page: 1, // 当前页
        pagesize: 12, // 每页行数
        orderby: "id", // 排序字段
        sort: "desc", // 排序类型
    });
    extend(search, route.query);
    // 链接参数变化时更新这些内容
    watch(
        () => route.query,
        () => {
            extend(search, route.query);
            loadList(1);
        },
        { deep: true }
    );

    // 总行数
    const totalCount = ref(0);
    /**
     * 列表数据
     * @type {EBisaixinxi[]}
     */
    const lists = ref([]);
    // 加载状态
    const loading = ref(false);

    // 排序操作
    const sortChange = (e) => {
        console.log(e);
        if (e.order == null) {
            search.orderby = "id";
            search.sort = "desc";
        } else {
            search.orderby = e.prop;
            search.sort = e.order == "ascending" ? "asc" : "desc";
        }
        loadList(1);
    };
    // 设置页数多少
    const sizeChange = (e) => {
        search.pagesize = e;
        loadList(1);
    };

    const deleteItems = (ids) => {
        return new Promise((resolve, reject) => {
            ElMessageBox.confirm("确定删除？")
                .then((res) => {
                    canBisaixinxiDelete(ids).then((res) => {
                        if (res.code == 0) {
                            ElMessage.success("删除成功");
                            loadList(search.page);
                            resolve(res.data);
                        } else {
                            ElMessage.error(res.msg);
                        }
                    });
                })
                .catch((err) => {
                    reject(err);
                });
        });
    };

    // 加载比赛信息列表方法
    const loadList = (page) => {
        // 加载
        if (unref(loading)) return;
        loading.value = true;
        search.page = page;

        http.post("/api/bisaixinxi/selectTianjiaren", search).then(
            (res) => {
                loading.value = false;
                if (res.code == 0) {
                    var data = res.data;
                    lists.value = data.lists.records;
                    totalCount.value = data.lists.total;
                }
            },
            (err) => {
                loading.value = false;
                ElMessage.error(err.message);
            }
        );
    };

    onBeforeMount(() => {
        loadList(1);
    });
    const searchSubmit = () => {
        loadList(1);
    };
</script>

<style scoped lang="scss">
    .views-bisaixinxi-list {
    }
</style>
