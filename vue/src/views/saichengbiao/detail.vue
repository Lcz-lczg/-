<template>
    <div class="views-saichengbiao-detail">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 赛程表详情 </span>
                    </div>
                </template>

                <div id="printdetail">
                    <el-descriptions class="margin-top" :column="3" border>
                        <el-descriptions-item label="比赛编号"> {{ map.bisaibianhao }} </el-descriptions-item>
                        <el-descriptions-item label="比赛标题"> {{ map.bisaibiaoti }} </el-descriptions-item>
                        <el-descriptions-item label="比赛类型"> <e-select-view module="bisaileixing" :value="map.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="球队A"> <e-select-view module="qiudui" :value="map.qiuduia" select="id" show="qiuduimingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="VS"> {{ map.vs }} </el-descriptions-item>
                        <el-descriptions-item label="球队B"> <e-select-view module="qiudui" :value="map.qiuduib" select="id" show="qiuduimingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="比分"> {{ map.bifen }} </el-descriptions-item>
                        <el-descriptions-item label="状态"> {{ map.zhuangtai }} </el-descriptions-item>
                        <el-descriptions-item label="添加时间"> {{ map.addtime }} </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions direction="vertical" class="margin-top" :column="1" border>
                        <el-descriptions-item label="比赛内容"> {{ map.bisaineirong }} </el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="no-print" v-if="isShowBtn">
                    <el-button @click="$router.go(-1)">返回</el-button>
                    <el-button @click="$print('#printdetail')">打印</el-button>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import DB from "@/utils/db";

    import { ref, reactive, watch, computed } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { extend } from "@/utils/extend";
    import { useSaichengbiaoFindById, canSaichengbiaoFindById } from "@/module";

    const route = useRoute();
    const props = defineProps({
        id: {
            type: [Number, String],
        },
        isShowBtn: {
            type: Boolean,
            default: true,
        },
    });

    /**
     * 获取详情页面的一行数据,当url参数id变更时，当url参数id变更时，自动更新map中的数据
     * @type {ESaichengbiao}
     */
    const map = useSaichengbiaoFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canSaichengbiaoFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-saichengbiao-detail {
    }
</style>
