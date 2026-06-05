<template>
    <div class="views-saichengtongji">
        <div>
            <el-form action="javascript:;" @submit="searchSubmit" inline>
                <el-form-item label="比赛标题">
                    <el-input v-model="search.bisaibiaoti"></el-input> </el-form-item
            ></el-form>
        </div>
        <div>
            <e-container>
                <div class="title-modelbox-widget1">
                    <h3 class="section-title">统计</h3>
                    <div class="sidebar-widget-body">
                        <div style="padding: 10px; background: #ffffff">
                            <el-table :data="saichengbiaoList">
                                <el-table-column prop="bisaibiaoti" label="比赛标题"  />
                                <el-table-column prop="qiuduia" label="球队A"  />
                                <el-table-column prop="qiuduib" label="球队B"  />
                                <el-table-column prop="sum_bifen" label="比分" sortable />
                            </el-table>
                        </div>

                        <v3-echarts :options="LineOptions" width="100%" height="300px"></v3-echarts>
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

    import { ref, reactive, watch, computed, unref } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { extend } from "@/utils/extend";

    const route = useRoute();

    const search = reactive({ bisaibiaoti: "" });
    extend(search, route.query);
    const serachSubmit = () => {
        loadDatabaseList();
    };
    var timer;
    watch(
        () => search,
        () => {
            clearTimeout(timer);
            timer = setTimeout(() => {
                serachSubmit();
            }, 200);
        },
        { deep: true }
    );
    const saichengbiaoList = ref([]);
    const loadDatabaseList = async () => {
        var where = " 1=1 ";
        if (search.bisaibiaoti) {
            where += ` AND scb.bisaibiaoti LIKE '%${search.bisaibiaoti}%' `;
        }
        saichengbiaoList.value = await DB.name("saichengbiao").alias("scb").field("scb.bisaibiaoti").group("scb.bisaibiaoti").field("scb.qiuduia").group("scb.qiuduia").field("sum(scb.bifen) sum_bifen").field("scb.qiuduib").group("scb.qiuduib").where(where).select();
    };

    loadDatabaseList();
    const LineOptions = computed(() => {
        var list = unref(saichengbiaoList);
        return {
            title: {
                text: "",
            },
            tooltip: {
                trigger: "axis",
            },
            grid: {
                left: "3%",
                right: "4%",
                bottom: "3%",
                containLabel: true,
            },
            toolbox: {
                feature: {
                    saveAsImage: {},
                },
            },
            xAxis: {
                type: "category",
                boundaryGap: false,
                data: list.map((s) => s.bisaibiaoti),
            },
            yAxis: {
                type: "value",
            },
            series: [
                {
                    name: "比分",
                    type: "line",
                    smooth: true,
                    data: list.map((s) => parseFloat(s.sum_bifen)),
                },
            ],
        };
    });
</script>

<style scoped lang="scss">
    .views-saichengtongji {
    }
</style>
