<template>
    <div class="views-jiaolian-detail">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 教练详情 </span>
                    </div>
                </template>

                <div id="printdetail">
                    <el-descriptions class="margin-top" :column="3" border>
                        <el-descriptions-item label="工号"> {{ map.gonghao }} </el-descriptions-item>
                        <el-descriptions-item label="密码"> {{ map.mima }} </el-descriptions-item>
                        <el-descriptions-item label="职称"> {{ map.zhicheng }} </el-descriptions-item>
                        <el-descriptions-item label="姓名"> {{ map.xingming }} </el-descriptions-item>
                        <el-descriptions-item label="性别"> {{ map.xingbie }} </el-descriptions-item>
                        <el-descriptions-item label="资质证书"> <e-img :src="map.zizhizhengshu" class="detail-image" style="max-width: 120px" /> </el-descriptions-item>
                        <el-descriptions-item label="联系电话"> {{ map.lianxidianhua }} </el-descriptions-item>
                        <el-descriptions-item label="头像"> <e-img :src="map.touxiang" class="detail-image" style="max-width: 120px" /> </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions direction="vertical" class="margin-top" :column="1" border>
                        <el-descriptions-item label="执教经验"> {{ map.zhijiaojingyan }} </el-descriptions-item>
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
    import { useJiaolianFindById, canJiaolianFindById } from "@/module";

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
     * @type {EJiaolian}
     */
    const map = useJiaolianFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canJiaolianFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-jiaolian-detail {
    }
</style>
