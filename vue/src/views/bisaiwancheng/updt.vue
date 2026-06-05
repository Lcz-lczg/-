<template>
    <div class="views-bisaiwancheng-updt">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 添加比赛完成 </span>
                    </div>
                </template>

                <el-form :model="form" ref="formModel" :label-width="labelWidth" status-icon validate-on-rule-change>
                    <el-form-item v-if="isRead" label="比赛编号 " prop="bisaibianhao"> {{ form.bisaibianhao }} </el-form-item>

                    <el-form-item v-if="isRead" label="比赛标题 " prop="bisaibiaoti"> {{ form.bisaibiaoti }} </el-form-item>

                    <el-form-item v-if="isRead" label="比赛类型 " prop="bisaileixing"> <e-select-view module="bisaileixing" :value="form.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </el-form-item>

                    <el-form-item v-if="isRead" label="球队A " prop="qiuduia"> <e-select-view module="qiudui" :value="form.qiuduia" select="id" show="qiuduimingcheng"></e-select-view> </el-form-item>

                    <el-form-item v-if="isRead" label="球队B " prop="qiuduib"> <e-select-view module="qiudui" :value="form.qiuduib" select="id" show="qiuduimingcheng"></e-select-view> </el-form-item>

                    <el-form-item label="完成编号 " prop="wanchengbianhao" :rules="[{ required: true, message: '请填写完成编号' }]"> <el-input type="text" placeholder="输入完成编号" style="width: 450px" v-model="form.wanchengbianhao" /> </el-form-item>

                    <el-form-item label="胜利球队 " prop="shengliqiudui" required :rules="[{ required: true, message: '请填写胜利球队' }]">
                        <el-select v-model="form.shengliqiudui"><e-select-option type="option" module="qiudui" value="id" label="qiuduimingcheng"></e-select-option></el-select>
                    </el-form-item>

                    <el-form-item label="完成内容 " prop="wanchengneirong"> <el-input type="textarea" v-model="form.wanchengneirong"></el-input> </el-form-item>

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
    import { useBisaiwanchengFindById, canBisaiwanchengFindById, canBisaiwanchengUpdate, canSaichengbiaoFindById } from "@/module";
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
    const form = useBisaiwanchengFindById(props.id);
    const emit = defineEmits(["success"]);
    const formModel = ref();
    const loading = ref(false);

    const submit = () => {
        formModel.value.validate().then((res) => {
            if (loading.value) return;
            loading.value = true;
            canBisaiwanchengUpdate(form).then(
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
        () => form.saichengbiaoid,
        (id) => {
            canSaichengbiaoFindById(id).then((res) => {
                extend(readMap, res);
            });
        }
    );
</script>

<style scoped lang="scss">
    .views-bisaiwancheng-updt {
    }
</style>
