<template>
    <div class="views-qiudui-updt">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 编辑球队 </span>
                    </div>
                </template>

                <el-form :model="form" ref="formModel" :label-width="labelWidth" status-icon validate-on-rule-change>
                    <el-form-item
                        label="球队名称 "
                        prop="qiuduimingcheng"
                        required
                        :rules="[
                            { required: true, message: '请填写球队名称' },
                            { validator: rule.checkRemote, message: '内容重复了', checktype: 'update', module: 'qiudui', col: 'qiuduimingcheng', id: form.id, trigger: 'blur' },
                        ]"
                    >
                        <el-input type="text" placeholder="输入球队名称" style="width: 450px" v-model="form.qiuduimingcheng" />
                    </el-form-item>

                    <el-form-item label="球队图片 " prop="qiuduitupian" required :rules="[{ required: true, message: '请填写球队图片' }]"> <e-upload-image v-model="form.qiuduitupian" is-paste></e-upload-image> </el-form-item>

                    <el-form-item label="球队队员 " prop="qiuduiduiyuan">
                        <el-checkbox-group v-model="qiuduiduiyuan">
                            <e-select-option
                                type="checkbox"
                                module="qiuyuanxinxi"
                                value="id"
                                label="xingming"
                                :where="[['suoshuqiudui', '=', '无']]">
                            </e-select-option>
                        </el-checkbox-group>
                        <div class="form-tip" style="margin-top: 5px; color: #999; font-size: 12px;">
                            <i class="el-icon-info"></i> 只显示所属球队为"无"的球员
                        </div>
                    </el-form-item>

                    <el-form-item label="实力等级 " prop="shilidengji"> <el-rate v-model="form.shilidengji" /> </el-form-item>

                    <el-form-item label="球队积分 " prop="qiuduijifen" :rules="[{ validator: rule.checkNumber, message: '输入一个有效数字' }]"> <el-input type="number" placeholder="输入球队积分" style="width: 450px" v-model.number="form.qiuduijifen" /> </el-form-item>

                    <el-form-item label="球队详情 " prop="qiuduixiangqing"> <e-editor v-model="form.qiuduixiangqing" @getContent="getqiuduixiangqingContent"></e-editor> </el-form-item>

                    <el-form-item label="教练姓名 " prop="jiaolianxingming"> <el-input type="text" placeholder="输入教练姓名" style="width: 450px" v-model="form.jiaolianxingming" /> </el-form-item>

                    <el-form-item label="球队教练 " prop="qiuduijiaolian"> <el-input v-model="form.qiuduijiaolian" readonly style="width: 250px"></el-input> </el-form-item>

                    <el-form-item label="成立时间 " prop="chenglishijian"> <el-date-picker v-model="form.chenglishijian" type="date" value-format="YYYY-MM-DD" placeholder="选择日期"> </el-date-picker> </el-form-item>

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
    import { useQiuduiFindById, canQiuduiUpdate } from "@/module";
    import { formatDate } from "@/utils/date";

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
    const form = useQiuduiFindById(props.id);
    const emit = defineEmits(["success"]);
    const formModel = ref();
    const loading = ref(false);

    const submit = () => {
        formModel.value.validate().then((res) => {
            if (loading.value) return;
            loading.value = true;
            canQiuduiUpdate(form).then(
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

    const qiuduiduiyuan = computed({
        get() {
            if (form.qiuduiduiyuan) {
                return form.qiuduiduiyuan.split(",").filter((s) => s);
            }
            return [];
        },
        set(val) {
            form.qiuduiduiyuan = val ? val.join(",") : "";
        },
    });
    const getqiuduixiangqingContent = (v) => {
        form.qiuduixiangqing = v;
    };
</script>

<style scoped lang="scss">
    .views-qiudui-updt {
    }
</style>
