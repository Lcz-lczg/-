<template>
    <div class="views-bisaibaoming-add">
        <div>
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span class="title"> 添加比赛报名 </span>
                    </div>
                </template>

                <el-form :model="form" ref="formModel" :label-width="labelWidth" status-icon validate-on-rule-change>
                    <el-form-item v-if="isRead" label="比赛编号 " prop="bisaibianhao"> {{ form.bisaibianhao }} </el-form-item>

                    <el-form-item v-if="isRead" label="比赛标题 " prop="bisaibiaoti"> {{ form.bisaibiaoti }} </el-form-item>

                    <el-form-item v-if="isRead" label="比赛类型 " prop="bisaileixing"> <e-select-view module="bisaileixing" :value="form.bisaileixing" select="id" show="leixingmingcheng"></e-select-view> </el-form-item>

                    <el-form-item label="报名编号 " prop="baomingbianhao" :rules="[{ required: true, message: '请填写报名编号' }]"> <el-input type="text" placeholder="输入报名编号" style="width: 450px" v-model="form.baomingbianhao" /> </el-form-item>

                    <el-form-item label="报名球队 " prop="baomingqiudui">
                        <div style="width: 100%">
                            <e-select-list :model="form" v-model="form.baomingqiudui" module="qiudui" select-update="qiuduibianhao,qiuduimingcheng,qiuduiduiyuan,jiaolianxingming,qiuduijiaolian" search-update=""></e-select-list>
                            <table>
                                <tr>
                                    <td width="120">球队编号</td>
                                    <td>{{ form.qiuduibianhao }}</td>
                                </tr>
                                <tr>
                                    <td width="120">球队名称</td>
                                    <td>{{ form.qiuduimingcheng }}</td>
                                </tr>
                                <tr>
                                    <td width="120">球队队员</td>
                                    <td><e-select-view module="qiuyuanxinxi" :value="form.qiuduiduiyuan" select="id" show="xingming"></e-select-view></td>
                                </tr>
                                <tr>
                                    <td width="120">教练姓名</td>
                                    <td>{{ form.jiaolianxingming }}</td>
                                </tr>
                                <tr>
                                    <td width="120">球队教练</td>
                                    <td>{{ form.qiuduijiaolian }}</td>
                                </tr>
                            </table>
                        </div>
                    </el-form-item>

                    <el-form-item label="球队编号 " prop="qiuduibianhao"> <el-input type="text" placeholder="输入球队编号" style="width: 450px" v-model="form.qiuduibianhao" /> </el-form-item>

                    <el-form-item label="球队名称 " prop="qiuduimingcheng"> <el-input type="text" placeholder="输入球队名称" style="width: 450px" v-model="form.qiuduimingcheng" /> </el-form-item>

                    <el-form-item label="球队队员 " prop="qiuduiduiyuan"> <el-input type="text" placeholder="输入球队队员" style="width: 450px" v-model="form.qiuduiduiyuan" /> </el-form-item>

                    <el-form-item label="教练姓名 " prop="jiaolianxingming"> <el-input type="text" placeholder="输入教练姓名" style="width: 450px" v-model="form.jiaolianxingming" /> </el-form-item>

                    <el-form-item label="球队教练 " prop="qiuduijiaolian"> <el-input v-model="form.qiuduijiaolian" readonly style="width: 250px"></el-input> </el-form-item>

                    <el-form-item label="联系方式 " prop="lianxifangshi"> <el-input type="text" placeholder="输入联系方式" style="width: 450px" v-model="form.lianxifangshi" /> </el-form-item>

                    <el-form-item label="备注 " prop="beizhu"> <el-input type="text" placeholder="输入备注" style="width: 450px" v-model="form.beizhu" /> </el-form-item>

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
    import { useBisaibaomingCreateForm, canBisaibaomingInsert } from "@/module";

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
    const { form, readMap } = useBisaibaomingCreateForm(props.id);
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
                    canBisaibaomingInsert(form).then(
                        (res) => {
                            loading.value = false;
                            if (res.code == 0) {
                                emit("success", res.data);
                                if (props.isHouxu) {
                                    ElMessage.success("添加成功");
                                    router.go(-1);
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
</script>

<style scoped lang="scss">
    .views-bisaibaoming-add {
    }
</style>
