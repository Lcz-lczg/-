<template>
    <div class="views-qiuyuanxinxi-updt">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 添加球员信息 </span>
                    </div>
                </template>

                <el-form :model="form" ref="formModel" :label-width="labelWidth" status-icon validate-on-rule-change>
                    <el-form-item
                        label="账号 "
                        prop="zhanghao"
                        required
                        :rules="[
                            { required: true, message: '请填写账号' },
                            { validator: rule.checkRemote, message: '内容重复了', checktype: 'update', module: 'qiuyuanxinxi', col: 'zhanghao', id: form.id, trigger: 'blur' },
                        ]"
                    >
                        <el-input type="text" placeholder="输入账号" style="width: 450px" v-model="form.zhanghao" />
                    </el-form-item>

                    <el-form-item label="姓名 " prop="xingming" required :rules="[{ required: true, message: '请填写姓名' }]"> <el-input type="text" placeholder="输入姓名" style="width: 450px" v-model="form.xingming" /> </el-form-item>

                    <el-form-item label="性别 " prop="xingbie" required :rules="[{ required: true, message: '请填写性别' }]">
                        <el-select v-model="form.xingbie"
                            ><el-option label="男" value="男"></el-option>
                            <el-option label="女" value="女"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item
                        label="联系电话 "
                        prop="lianxidianhua"
                        required
                        :rules="[
                            { required: true, message: '请填写联系电话' },
                            { validator: rule.checkPhone, message: '请输入正确手机号码' },
                        ]"
                    >
                        <el-input type="text" placeholder="输入联系电话" style="width: 450px" v-model="form.lianxidianhua" />
                    </el-form-item>

                    <el-form-item
                        label="年龄 "
                        prop="nianling"
                        required
                        :rules="[
                            { required: true, message: '请填写年龄' },
                            { validator: rule.checkNumber, message: '输入一个有效数字' },
                        ]"
                    >
                        <el-input type="number" placeholder="输入年龄" style="width: 450px" v-model.number="form.nianling" />
                    </el-form-item>

                    <el-form-item label="身高 /cm" prop="shengao" required :rules="[{ required: true, message: '请填写身高' }]"> <el-input type="text" placeholder="输入身高" style="width: 450px" v-model="form.shengao" /> </el-form-item>

                    <el-form-item label="体重 /kg" prop="tizhong" required :rules="[{ required: true, message: '请填写体重' }]"> <el-input type="text" placeholder="输入体重" style="width: 450px" v-model="form.tizhong" /> </el-form-item>

                    <el-form-item label="所属球队 " prop="suoshuqiudui"> <el-input type="text" placeholder="输入所属球队" style="width: 450px" v-model="form.suoshuqiudui" /> </el-form-item>

                    <el-form-item label="球员位置 " prop="qiuyuanweizhi"> <el-input type="text" placeholder="输入球员位置" style="width: 450px" v-model="form.qiuyuanweizhi" /> </el-form-item>

                    <el-form-item label="球衣号 " prop="qiuyihao"> <el-input type="text" placeholder="输入球衣号" style="width: 450px" v-model="form.qiuyihao" /> </el-form-item>

                    <el-form-item label="个人简介 " prop="gerenjianjie"> <el-input type="textarea" v-model="form.gerenjianjie"></el-input> </el-form-item>

                    <el-form-item label="头像 " prop="touxiang"> <e-upload-image v-model="form.touxiang" is-paste></e-upload-image> </el-form-item>

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

    import { ref, reactive, computed } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { ElMessage, ElMessageBox } from "element-plus";
    import { useQiuyuanxinxiFindById, canQiuyuanxinxiFindById, canQiuyuanxinxiUpdate } from "@/module";

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
    const form = useQiuyuanxinxiFindById(props.id);
    const emit = defineEmits(["success"]);
    const formModel = ref();
    const loading = ref(false);

    const submit = () => {
        formModel.value.validate().then((res) => {
            if (loading.value) return;
            loading.value = true;
            canQiuyuanxinxiUpdate(form).then(
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
</script>

<style scoped lang="scss">
    .views-qiuyuanxinxi-updt {
    }
</style>
