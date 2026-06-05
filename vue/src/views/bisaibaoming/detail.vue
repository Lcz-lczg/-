<template>
    <div class="views-bisaibaoming-detail">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 比赛报名详情 </span>
                    </div>
                </template>

                <div id="printdetail">
                    <el-descriptions class="margin-top" :column="3" border>
                        <el-descriptions-item label="比赛编号"> {{ map.bisaibianhao }} </el-descriptions-item>
                        <el-descriptions-item label="比赛标题"> {{ map.bisaibiaoti }} </el-descriptions-item>
                        <el-descriptions-item label="比赛类型"> <e-select-view module="bisaileixing" :value="map.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="报名编号"> {{ map.baomingbianhao }} </el-descriptions-item>
                        <el-descriptions-item label="报名球队">
                            <p>球队编号：{{ map.qiuduibianhao }}</p>
                            <p>球队名称：{{ map.qiuduimingcheng }}</p>
                            <p>球队队员： <e-select-view module="qiuyuanxinxi" :value="map.qiuduiduiyuan" select="id" show="xingming"></e-select-view></p>
                            <p>教练姓名：{{ map.jiaolianxingming }}</p>
                            <p>球队教练：{{ map.qiuduijiaolian }}</p>
                        </el-descriptions-item>
                        <el-descriptions-item label="球队编号"> {{ map.qiuduibianhao }} </el-descriptions-item>
                        <el-descriptions-item label="球队名称"> {{ map.qiuduimingcheng }} </el-descriptions-item>
                        <el-descriptions-item label="球队队员"> {{ map.qiuduiduiyuan }} </el-descriptions-item>
                        <el-descriptions-item label="教练姓名"> {{ map.jiaolianxingming }} </el-descriptions-item>
                        <el-descriptions-item label="球队教练"> {{ map.qiuduijiaolian }} </el-descriptions-item>
                        <el-descriptions-item label="联系方式"> {{ map.lianxifangshi }} </el-descriptions-item>
                        <el-descriptions-item label="备注"> {{ map.beizhu }} </el-descriptions-item>
                        <el-descriptions-item label="状态"> {{ map.zhuangtai }} </el-descriptions-item>
                        <el-descriptions-item label="添加时间"> {{ map.addtime }} </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions direction="vertical" class="margin-top" :column="1" border> </el-descriptions>
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
    import { useBisaibaomingFindById, canBisaibaomingFindById } from "@/module";

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
     * @type {EBisaibaoming}
     */
    const map = useBisaibaomingFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canBisaibaomingFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-bisaibaoming-detail {
    }
</style>
