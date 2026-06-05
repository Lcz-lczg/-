<template>
    <div class="views-xinwengonggao-add">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 添加新闻公告 </span>
                    </div>
                </template>

                <el-form :model="form" ref="formModel" :label-width="labelWidth" status-icon validate-on-rule-change>
                    <el-form-item
                        label="标题 "
                        prop="biaoti"
                        required
                        :rules="[
                            { required: true, message: '请填写标题' },
                            { validator: rule.checkRemote, message: '内容重复了', checktype: 'insert', module: 'xinwengonggao', col: 'biaoti', trigger: 'blur' },
                        ]"
                    >
                        <el-input type="text" placeholder="输入标题" style="width: 450px" v-model="form.biaoti" />
                    </el-form-item>

                    <el-form-item label="分类 " prop="fenlei" required :rules="[{ required: true, message: '请填写分类' }]">
                        <el-select v-model="form.fenlei"><e-select-option type="option" module="xinwenfenlei" value="id" label="fenleimingcheng"></e-select-option></el-select>
                    </el-form-item>

                    <el-form-item label="图片 " prop="tupian"> <e-upload-image v-model="form.tupian" is-paste></e-upload-image> </el-form-item>

                    <el-form-item label="添加人 " prop="tianjiaren"> <el-input v-model="form.tianjiaren" readonly style="width: 250px"></el-input> </el-form-item>

                    <el-form-item label="内容 " prop="neirong"> <e-editor v-model="form.neirong" @getContent="getneirongContent"></e-editor> </el-form-item>

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
    import { useXinwengonggaoCreateForm, canXinwengonggaoInsert } from "@/module";
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
    const { form } = useXinwengonggaoCreateForm();
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
                    canXinwengonggaoInsert(form).then(
                        (res) => {
                            loading.value = false;
                            if (res.code == 0) {
                                emit("success", res.data);
                                if (props.isHouxu) {
                                    ElMessage.success("添加成功");
                                    formModel.value.resetFields();
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

    const getneirongContent = (v) => {
        form.neirong = v;
    };
</script>

<style scoped lang="scss">
    .views-xinwengonggao-add {
    }
</style>
