<template>
    <div class="views-qiudui-list">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 球队查询 </span>
                    </div>
                </template>

                <div class="form-search">
                    <el-form @submit.prevent.stop :inline="true" size="small">

                        <el-form-item label="球队名称">
                            <el-input v-model="search.qiuduimingcheng"></el-input>
                        </el-form-item>
                        <el-form-item label="球队队员">
                            <el-select v-model="search.qiuduiduiyuan"><el-option label="请选择" value=""></el-option><e-select-option type="option" module="qiuyuanxinxi" value="id" label="xingming"></e-select-option></el-select>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="searchSubmit" icon="el-icon-search">查询</el-button>
                        </el-form-item>
                    </el-form>
                </div>

                <el-table border :data="lists" style="width: 100%" @sort-change="sortChange" highlight-current-row>
                    <el-table-column type="index" label="#"></el-table-column>
                    <!-- 序号 -->


                    <el-table-column prop="qiuduimingcheng" label="球队名称">
                        <template #default="{ row }"> {{ row.qiuduimingcheng }} </template>
                    </el-table-column>
                    <el-table-column prop="qiuduitupian" label="球队图片" >
                        <template #default="{ row }"> <e-img :src="row.qiuduitupian" style="max-width: 120px" /> </template>
                    </el-table-column>
                    <el-table-column prop="qiuduiduiyuan" label="球队队员" >
                        <template #default="{ row }">
                            <span v-if="row.qiuduiduiyuan">{{ row.qiuduiduiyuan }}</span>
                            <span v-else style="color: #999">无</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="shilidengji" label="实力等级">
                        <template #default="{ row }"> <el-rate v-model="row.shilidengji" disabled /> </template>
                    </el-table-column>
                    <el-table-column prop="qiuduijifen" label="球队积分" >
                        <template #default="{ row }"> {{ row.qiuduijifen }} </template>
                    </el-table-column>
                    <el-table-column prop="jiaolianxingming" label="教练姓名" >
                        <template #default="{ row }"> {{ row.jiaolianxingming }} </template>
                    </el-table-column>
                    <el-table-column prop="qiuduijiaolian" label="球队教练" >
                        <template #default="{ row }"> {{ row.qiuduijiaolian }} </template>
                    </el-table-column>
                    <el-table-column prop="chenglishijian" label="成立时间" >
                        <template #default="{ row }"> {{ row.chenglishijian }} </template>
                    </el-table-column>

                    <el-table-column label="操作" fixed="right" width="250">
                        <template #default="{ row }">
                            <el-button-group>
                                <el-tooltip effect="dark" content="详情" placement="top-start"><el-button type="info" :icon="InfoFilled" size="small" @click="$router.push('/admin/qiudui/detail?id=' + row.id)">详情</el-button> </el-tooltip>
                                <el-tooltip effect="dark" content="编辑" placement="top-start"><el-button type="success" :icon="Edit" size="small" @click="$router.push('/admin/qiudui/updt?id=' + row.id)">编辑</el-button> </el-tooltip>
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
    import { canQiuduiSelect, useQiuduiSelect, canQiuduiDelete } from "@/module";
    import { extend } from "@/utils/extend";
    import { ElMessageBox, ElMessage } from "element-plus";
    import { InfoFilled, Edit, Delete } from "@element-plus/icons-vue";

    const route = useRoute();
    const search = reactive({
        qiuduibianhao: "",
        qiuduimingcheng: "",
        qiuduiduiyuan: "",
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
     * @type {EQiudui[]}
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
                    canQiuduiDelete(ids).then((res) => {
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

    // 加载球队列表方法
    const loadList = (page) => {
        // 加载
        if (unref(loading)) return;
        loading.value = true;
        search.page = page;

        // 根据用户类型设置查询条件
        const cx = session('cx');
        const username = session('username');
        const xingming = session('xingming');
        
        // 球员只能看到自己所在的球队
        if (cx === '球员信息') {
            search.qiuduiduiyuan = xingming;
        }
        // 管理员可以看到全部，不需要额外条件

        http.post("/api/qiudui/selectPages", search).then(
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
    .views-qiudui-list {
    }
</style>
