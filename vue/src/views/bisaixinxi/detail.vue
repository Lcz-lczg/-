<template>
    <div class="views-bisaixinxi-detail">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 比赛信息详情 </span>
                    </div>
                </template>

                <div id="printdetail">
                    <el-descriptions class="margin-top" :column="3" border>
                        <el-descriptions-item label="比赛编号"> {{ map.bisaibianhao }} </el-descriptions-item>
                        <el-descriptions-item label="比赛标题"> {{ map.bisaibiaoti }} </el-descriptions-item>
                        <el-descriptions-item label="比赛类型"> <e-select-view module="bisaileixing" :value="map.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="比赛模式"> {{ map.bisaimoshi }} </el-descriptions-item>
                        <el-descriptions-item label="比赛图片"> <e-img :src="map.bisaitupian" class="detail-image" style="max-width: 120px" /> </el-descriptions-item>
                        <el-descriptions-item label="参加球队"> {{ map.canjiaqiudui }} </el-descriptions-item>
                        <el-descriptions-item label="比赛场地"> {{ map.bisaichangdi }} </el-descriptions-item>
                        <el-descriptions-item label="添加人"> {{ map.tianjiaren }} </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions direction="vertical" class="margin-top" :column="1" border>
                        <el-descriptions-item label="比赛内容"> <div v-html="map.bisaineirong"></div> </el-descriptions-item>
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
    import { useBisaixinxiFindById, canBisaixinxiFindById } from "@/module";

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
     * @type {EBisaixinxi}
     */
    const map = useBisaixinxiFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canBisaixinxiFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-bisaixinxi-detail {
    }
</style>
