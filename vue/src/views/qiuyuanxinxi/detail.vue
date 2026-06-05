<template>
    <div class="views-qiuyuanxinxi-detail">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 球员信息详情 </span>
                    </div>
                </template>

                <div id="printdetail">
                    <el-descriptions class="margin-top" :column="3" border>
                        <el-descriptions-item label="账号"> {{ map.zhanghao }} </el-descriptions-item>
                        <el-descriptions-item label="密码"> {{ map.mima }} </el-descriptions-item>
                        <el-descriptions-item label="姓名"> {{ map.xingming }} </el-descriptions-item>
                        <el-descriptions-item label="性别"> {{ map.xingbie }} </el-descriptions-item>
                        <el-descriptions-item label="联系电话"> {{ map.lianxidianhua }} </el-descriptions-item>
                        <el-descriptions-item label="年龄"> {{ map.nianling }} </el-descriptions-item>
                        <el-descriptions-item label="身高"> {{ map.shengao }} </el-descriptions-item>
                        <el-descriptions-item label="体重"> {{ map.tizhong }} </el-descriptions-item>
                        <el-descriptions-item label="所属球队"> {{ map.suoshuqiudui }} </el-descriptions-item>
                        <el-descriptions-item label="球员位置"> {{ map.qiuyuanweizhi }} </el-descriptions-item>
                        <el-descriptions-item label="球衣号"> {{ map.qiuyihao }} </el-descriptions-item>
                        <el-descriptions-item label="头像"> <e-img :src="map.touxiang" class="detail-image" style="max-width: 120px" /> </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions direction="vertical" class="margin-top" :column="1" border>
                        <el-descriptions-item label="个人简介"> {{ map.gerenjianjie }} </el-descriptions-item>
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
    import { useQiuyuanxinxiFindById, canQiuyuanxinxiFindById } from "@/module";

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
     * @type {EQiuyuanxinxi}
     */
    const map = useQiuyuanxinxiFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canQiuyuanxinxiFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-qiuyuanxinxi-detail {
    }
</style>
