<template>
    <div class="views-saichengbiao-updt">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 添加赛程表 </span>
                    </div>
                </template>

                <el-form :model="form" ref="formModel" :label-width="labelWidth" status-icon validate-on-rule-change>
                    <el-form-item v-if="isRead" label="比赛编号 " prop="bisaibianhao"> {{ form.bisaibianhao }} </el-form-item>

                    <el-form-item v-if="isRead" label="比赛标题 " prop="bisaibiaoti"> {{ form.bisaibiaoti }} </el-form-item>

                    <el-form-item v-if="isRead" label="比赛类型 " prop="bisaileixing"> <e-select-view module="bisaileixing" :value="form.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </el-form-item>

                    <el-form-item label="球队A " prop="qiuduia" required :rules="[{ required: true, message: '请填写球队A' }]">
                        <el-select v-model="form.qiuduia"><e-select-option type="option" module="qiudui" value="id" label="qiuduimingcheng"></e-select-option></el-select>
                    </el-form-item>

                    <el-form-item label="VS " prop="vs"> <el-input type="text" placeholder="输入VS" style="width: 450px" v-model="form.vs" /> </el-form-item>

                    <el-form-item label="球队B " prop="qiuduib" required :rules="[{ required: true, message: '请填写球队B' }]">
                        <el-select v-model="form.qiuduib"><e-select-option type="option" module="qiudui" value="id" label="qiuduimingcheng"></e-select-option></el-select>
                    </el-form-item>

                    <el-form-item label="比分 " prop="bifen"> <el-input type="text" placeholder="输入比分" style="width: 450px" v-model="form.bifen" /> </el-form-item>

                    <el-form-item label="状态 " prop="zhuangtai">
                        <el-select v-model="form.zhuangtai"
                            ><el-option label="待开始" value="待开始"></el-option>
                            <el-option label="进行中" value="进行中"></el-option>
                            <el-option label="已结束" value="已结束"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="比赛内容 " prop="bisaineirong"> <el-input type="textarea" v-model="form.bisaineirong"></el-input> </el-form-item>

                    <el-form-item v-if="btnText">
                        <el-button type="primary" @click="submit">{{ btnText }}</el-button>
                    </el-form-item>
                </el-form></el-card
            >
        </div>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import DB from "@/utils/db";
    import rule from "@/utils/rule";
    import router from "@/router";

    import { ref, reactive, computed, watch } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { ElMessage, ElMessageBox } from "element-plus";
    import { useSaichengbiaoFindById, canSaichengbiaoFindById, canSaichengbiaoUpdate, canBisaixinxiFindById } from "@/module";
    import { extend } from "@/utils/extend";

    const route = useRoute();
    const props = defineProps({
        id: [String, Number],
        btnText: {
            type: String,
            default: "保存",
        },
        isRead: {
            type: Boolean,
            default: true,
        },
        isHouxu: {
            type: Boolean,
            default: true,
        },
        labelWidth: {
            type: String,
            default: "140px",
        },
    });
    const form = useSaichengbiaoFindById(props.id);
    const emit = defineEmits(["success"]);
    const formModel = ref();
    const loading = ref(false);

    const submit = () => {
        formModel.value.validate().then((res) => {
            if (loading.value) return;
            loading.value = true;
            canSaichengbiaoUpdate(form).then(
                (res) => {
                    loading.value = false;
                    if (res.code == 0) {
                        emit("success", res.data);
                        if (props.isHouxu) {
                            ElMessage.success("更新成功");
                            router.go(-1);
                        }
                    } else {
                        ElMessageBox.alert(res.msg);
                    }
                },
                (err) => {
                    loading.value = false;
                    ElMessageBox.alert(err.message);
                }
            );
        });
    };

    const readMap = reactive({});
    watch(
        () => form.bisaixinxiid,
        (id) => {
            canBisaixinxiFindById(id).then((res) => {
                extend(readMap, res);
            });
        }
    );
</script>

<style scoped lang="scss">
    .views-saichengbiao-updt {
    }
</style>
