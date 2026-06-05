<template>
    <div class="views-qiudui-detail">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 球队详情 </span>
                    </div>
                </template>

                <div id="printdetail">
                    <el-descriptions class="margin-top" :column="3" border>
                        <el-descriptions-item label="球队编号"> {{ map.qiuduibianhao }} </el-descriptions-item>
                        <el-descriptions-item label="球队名称"> {{ map.qiuduimingcheng }} </el-descriptions-item>
                        <el-descriptions-item label="球队图片"> <e-img :src="map.qiuduitupian" class="detail-image" style="max-width: 120px" /> </el-descriptions-item>
                        <el-descriptions-item label="球队队员"> <e-select-view module="qiuyuanxinxi" :value="map.qiuduiduiyuan" select="id" show="xingming"></e-select-view> </el-descriptions-item>
                        <el-descriptions-item label="实力等级"> <el-rate v-model="map.shilidengji" disabled /> </el-descriptions-item>
                        <el-descriptions-item label="球队积分"> {{ map.qiuduijifen }} </el-descriptions-item>
                        <el-descriptions-item label="教练姓名"> {{ map.jiaolianxingming }} </el-descriptions-item>
                        <el-descriptions-item label="球队教练"> {{ map.qiuduijiaolian }} </el-descriptions-item>
                        <el-descriptions-item label="成立时间"> {{ map.chenglishijian }} </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions direction="vertical" class="margin-top" :column="1" border>
                        <el-descriptions-item label="球队详情"> <div v-html="map.qiuduixiangqing"></div> </el-descriptions-item>
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
    import { useQiuduiFindById, canQiuduiFindById } from "@/module";

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
     * @type {EQiudui}
     */
    const map = useQiuduiFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canQiuduiFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-qiudui-detail {
    }
</style>
