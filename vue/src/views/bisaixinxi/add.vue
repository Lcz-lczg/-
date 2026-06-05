<template>
    <div class="views-bisaixinxi-add">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 添加比赛信息 </span>
                    </div>
                </template>

                <el-form :model="form" ref="formModel" :label-width="labelWidth" status-icon validate-on-rule-change>
                    <el-form-item label="比赛编号 " prop="bisaibianhao" :rules="[{ required: true, message: '请填写比赛编号' }]"> <el-input type="text" placeholder="输入比赛编号" style="width: 450px" v-model="form.bisaibianhao" /> </el-form-item>

                    <el-form-item label="比赛标题 " prop="bisaibiaoti" required :rules="[{ required: true, message: '请填写比赛标题' }]"> <el-input type="text" placeholder="输入比赛标题" style="width: 450px" v-model="form.bisaibiaoti" /> </el-form-item>

                    <el-form-item label="比赛类型 " prop="bisaileixing">
                        <el-select v-model="form.bisaileixing"><e-select-option type="option" module="bisaileixing" value="id" label="leixingmingcheng"></e-select-option></el-select>
                    </el-form-item>

                    <el-form-item label="比赛模式 " prop="bisaimoshi"> <el-input type="text" placeholder="输入比赛模式" style="width: 450px" v-model="form.bisaimoshi" /> </el-form-item>

                    <el-form-item label="比赛图片 " prop="bisaitupian"> <e-upload-image v-model="form.bisaitupian" is-paste></e-upload-image> </el-form-item>

                    <el-form-item label="参加球队 " prop="canjiaqiudui" :rules="[{ validator: rule.checkNumber, message: '输入一个有效数字' }]"> <el-input type="number" placeholder="输入参加球队" style="width: 450px" v-model.number="form.canjiaqiudui" /> </el-form-item>

                    <el-form-item label="比赛场地 " prop="bisaichangdi"> <el-input type="text" placeholder="输入比赛场地" style="width: 450px" v-model="form.bisaichangdi" /> </el-form-item>

                    <el-form-item label="比赛内容 " prop="bisaineirong"> <e-editor v-model="form.bisaineirong" @getContent="getbisaineirongContent"></e-editor> </el-form-item>

                    <el-form-item label="添加人 " prop="tianjiaren"> <el-input v-model="form.tianjiaren" readonly style="width: 250px"></el-input> </el-form-item>

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
    import EEditor from "@/components/EEditor.vue";

    import { ref, reactive, computed } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { ElMessage, ElMessageBox } from "element-plus";
    import { useBisaixinxiCreateForm, canBisaixinxiInsert } from "@/module";
    import { extend } from "vue-design-plugin";

    const route = useRoute();
    const props = defineProps({
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
    const { form } = useBisaixinxiCreateForm();
    const emit = defineEmits(["success"]);
    const formModel = ref();
    const loading = ref(false);
    var submit = () => {
        return new Promise((resolve, reject) => {
            formModel.value
                .validate()
                .then((res) => {
                    if (loading.value) return;
                    loading.value = true;
                    canBisaixinxiInsert(form).then(
                        (res) => {
                            loading.value = false;
                            if (res.code == 0) {
                                emit("success", res.data);
                                if (props.isHouxu) {
                                    ElMessage.success("添加成功");
                                    formModel.value.resetFields();
                                    form.bisaibianhao = rule.getID();
                                }
                            } else {
                                ElMessageBox.alert(res.msg);
                            }
                            resolve(res);
                        },
                        (err) => {
                            loading.value = false;
                            ElMessageBox.alert(err.message);
                            reject(err);
                        }
                    );
                })
                .catch((err) => {
                    reject(err);
                });
        });
    };

    const getbisaineirongContent = (v) => {
        form.bisaineirong = v;
    };
</script>

<style scoped lang="scss">
    .views-bisaixinxi-add {
    }
</style>
