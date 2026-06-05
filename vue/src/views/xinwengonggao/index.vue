<template>
    <div class="views-xinwengonggao-index">
        <div style="margin: 20px 0 0 0">
            <e-container>
                <div class="title-modelbox-widget1">
                    <h3 class="section-title">列表</h3>
                    <div class="sidebar-widget-body">
                        <form action="javascript:;" @submit="searchSubmit" class="form-search">
                            <table class="jd-search">
                                <tbody>
                                    <tr>
                                        <td class="label">分类</td>
                                        <td>
                                            <p class="search-radio">
                                                <a href="javascript:;" @click="selectRadio('fenlei', '')" :class="{ active: !search.fenlei }">全部</a>
                                                <a href="javascript:;" v-for="r in mapxinwenfenlei1" @click="selectRadio('fenlei', r.id)" :class="{ active: search.fenlei == r.id }" v-text="r.fenleimingcheng"> </a>
                                            </p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">标题</td>
                                        <td>
                                            <el-input type="text" style="width: 150px" v-model="search.biaoti" placeholder="请输入关键词"> </el-input>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">排序</td>
                                        <td>
                                            <div style="display: flex; justify-content: space-between">
                                                <p class="search-radio">
                                                    <a href="javascript:;" @click="selectRadio('orderby', 'id')" :class="{ active: search.orderby == 'id' }">发布时间</a>
                                                </p>
                                                <p class="search-radio">
                                                    <a href="javascript:;" @click="selectRadio('sort', 'desc')" :class="{ active: search.sort == 'desc' }">倒序</a>
                                                    <a href="javascript:;" @click="selectRadio('sort', 'asc')" :class="{ active: search.sort == 'asc' }">升序</a>
                                                </p>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>
                                            <el-button type="success" @click="searchSubmit">搜索</el-button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>

                        <div class="">
                            <div class="e-news-info clearfix" v-for="r in lists" :key="r.id">
                                <div class="thumb" v-if="r.tupian" @click="$goto('/xinwengonggao/detail?id=' + r.id)">
                                    <e-img :src="r.tupian" pb="100" />
                                </div>
                                <div class="news-content-text" :class="{ 'not-thumb': !r.tupian }">
                                    <h3 @click="$goto('/xinwengonggao/detail?id=' + r.id)">
                                        {{ r.biaoti }}
                                    </h3>
                                    <div class="description" v-html="$substr(r.neirong, 30)"></div>
                                    <div class="other-content">
                                        <span>分类: <e-select-view module="xinwenfenlei" :value="r.fenlei" select="id" show="fenleimingcheng"></e-select-view></span>
                                        <span>点击量:{{ r.dianjiliang }}</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div style="margin-top: 10px; text-align: center">
                            <el-pagination @current-change="loadList" :page-sizes="[12, 24, 36, 48, 60]" v-model:current-page="search.page" v-model:page-size="search.pagesize" @size-change="sizeChange" layout="total, sizes, prev, pager, next" :total="totalCount"> </el-pagination>
                        </div>
                    </div>
                    <!-- /.sidebar-widget-body -->
                </div>
            </e-container>
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
    import { canXinwengonggaoSelect, useXinwengonggaoSelect, canXinwengonggaoDelete } from "@/module";
    import { extend } from "@/utils/extend";
    import { ElMessageBox, ElMessage } from "element-plus";

    const route = useRoute();
    const search = reactive({
        biaoti: "",
        fenlei: "",
        tianjiaren: "",
        dianjiliang_start: "",
        dianjiliang_end: "",
        neirong: "",
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
     * @type {EXinwengonggao[]}
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

    // 加载新闻公告列表方法
    const loadList = (page) => {
        // 加载
        if (unref(loading)) return;
        loading.value = true;
        search.page = page;

        http.post("/api/xinwengonggao/selectPages", search).then(
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
    const selectRadio = (target, name) => {
        search[target] = name;
        searchSubmit(1);
    };

    const searchSubmit = (page = 1) => {
        loadList(1);
    };
    const mapxinwenfenlei1 = DB.name("xinwenfenlei").field("id,fenleimingcheng").order("id desc").selectRef();
</script>

<style scoped lang="scss">
    .views-xinwengonggao-index {
    }
</style>
