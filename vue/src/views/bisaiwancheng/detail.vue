<template>
    <div class="views-bisaiwancheng-detail">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 比赛完成详情 </span>
                    </div>
                </template>

                <div id="printdetail">
                    <el-descriptions class="margin-top" :column="3" border>
                        <el-descriptions-item label="比赛编号"> {{ map.bisaibianhao }} </el-descriptions-item>
                        <el-descriptions-item label="比赛标题"> {{ map.bisaibiaoti }} </el-descriptions-item>
                        <el-descriptions-item label="比赛类型"> <e-select-view module="bisaileixing" :value="map.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="球队A"> <e-select-view module="qiudui" :value="map.qiuduia" select="id" show="qiuduimingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="球队B"> <e-select-view module="qiudui" :value="map.qiuduib" select="id" show="qiuduimingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="完成编号"> {{ map.wanchengbianhao }} </el-descriptions-item>
                        <el-descriptions-item label="胜利球队"> <e-select-view module="qiudui" :value="map.shengliqiudui" select="id" show="qiuduimingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="添加时间"> {{ map.addtime }} </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions direction="vertical" class="margin-top" :column="1" border>
                        <el-descriptions-item label="完成内容"> {{ map.wanchengneirong }} </el-descriptions-item>
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
    import { useBisaiwanchengFindById, canBisaiwanchengFindById } from "@/module";

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
     * @type {EBisaiwancheng}
     */
    const map = useBisaiwanchengFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canBisaiwanchengFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-bisaiwancheng-detail {
    }
</style>
