<template>
    <div class="views-schedule-generate">
        <el-card class="box-card">
            <template #header>
                <div class="clearfix">
                    <span class="title"> 赛程自动编排 </span>
                </div>
            </template>

            <!-- 步骤条 -->
            <el-steps :active="currentStep" finish-status="success" align-center style="margin-bottom: 20px">
                <el-step title="选择比赛" />
                <el-step title="选择球队" />
                <el-step title="设置编排方式" />
                <el-step title="预览确认" />
            </el-steps>

            <!-- 步骤 1: 选择比赛 -->
            <div v-if="currentStep === 0" class="step-content">
                <el-form :model="form" label-width="120px" size="default">
                    <el-form-item label="选择比赛信息">
                        <el-select v-model="form.bisaixinxiid" placeholder="请选择比赛信息" @change="onMatchChange" style="width: 400px">
                            <el-option
                                v-for="item in matchList"
                                :key="item.id"
                                :label="item.bisaibiaoti + ' - ' + item.bisaibianhao"
                                :value="item.id"
                            />
                        </el-select>
                    </el-form-item>

                    <el-form-item label="比赛信息详情" v-if="matchInfo">
                        <el-descriptions :column="2" border>
                            <el-descriptions-item label="比赛编号">{{ matchInfo.bisaibianhao }}</el-descriptions-item>
                            <el-descriptions-item label="比赛标题">{{ matchInfo.bisaibiaoti }}</el-descriptions-item>
                            <el-descriptions-item label="比赛类型">{{ matchInfo.leixingmingcheng }}</el-descriptions-item>
                            <el-descriptions-item label="比赛模式">{{ matchInfo.bisaimoshi }}</el-descriptions-item>
                            <el-descriptions-item label="比赛场地">{{ matchInfo.bisaichangdi }}</el-descriptions-item>
                            <el-descriptions-item label="参赛球队数">{{ matchInfo.canjiaqiudui }}支</el-descriptions-item>
                        </el-descriptions>
                    </el-form-item>

                    <el-form-item label="已报名球队" v-if="matchInfo && matchInfo.teams && matchInfo.teams.length > 0">
                        <el-tag
                            v-for="team in matchInfo.teams"
                            :key="team.id"
                            type="success"
                            effect="plain"
                            style="margin-right: 10px; margin-bottom: 10px"
                        >
                            {{ team.qiuduimingcheng }}
                            <el-tag size="small" type="warning" style="margin-left: 5px">实力：{{ team.shilidengji }}</el-tag>
                        </el-tag>
                    </el-form-item>
                    <el-form-item v-if="matchInfo && (!matchInfo.teams || matchInfo.teams.length === 0)">
                        <el-alert type="warning" title="暂无球队报名，请先让球队报名参赛" show-icon />
                    </el-form-item>
                </el-form>
            </div>

            <!-- 步骤 2: 选择球队 -->
            <div v-if="currentStep === 1" class="step-content">
                <el-alert
                    type="info"
                    title="请选择参赛球队（已默认选中已报名球队，可手动调整）"
                    show-icon
                    style="margin-bottom: 15px"
                />

                <el-checkbox-group v-model="selectedTeamIds">
                    <el-table :data="allTeams" border style="width: 100%">
                        <el-table-column type="selection" width="55" :selectable="isTeamSelectable" />
                        <el-table-column label="选择" >
                            <template #default="{ row }">
                                <el-checkbox :value="row.id" :disabled="!isTeamSelectable(row)" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="qiuduibianhao" label="球队编号"  />
                        <el-table-column prop="qiuduimingcheng" label="球队名称"/>
                        <el-table-column label="实力等级" >
                            <template #default="{ row }">
                                <el-rate v-model="row.shilidengji" disabled show-text text-color="#ff9900" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="jiaolianxingming" label="教练"  />
                        <el-table-column label="状态" >
                            <template #default="{ row }">
                                <el-tag v-if="row.yibaoming" type="success" size="small">已报名</el-tag>
                                <el-tag v-else type="info" size="small">未报名</el-tag>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-checkbox-group>

                <div style="margin-top: 15px; text-align: right">
                    <el-button @click="selectAllTeams" :disabled="selectedTeamIds.length === allTeams.length">全选</el-button>
                    <el-button @click="selectOnlyRegistered" :disabled="selectedTeamIds.length === registeredTeamIds.length">
                        仅选已报名
                    </el-button>
                    <el-button @click="clearSelection">清空</el-button>
                    <span style="margin-left: 20px; color: #999">已选 {{ selectedTeamIds.length }} 支球队</span>
                </div>
            </div>

            <!-- 步骤 3: 设置编排方式 -->
            <div v-if="currentStep === 2" class="step-content">
                <el-form :model="scheduleConfig" label-width="150px" size="default">
                    <el-form-item label="比赛模式">
                        <el-radio-group v-model="scheduleConfig.mode">
                            <el-radio-button label="single">单循环赛</el-radio-button>
                            <el-radio-button label="double">双循环赛</el-radio-button>
                            <el-radio-button label="knockout">淘汰赛</el-radio-button>
                        </el-radio-group>
                    </el-form-item>

                    <el-form-item label="编排方式">
                        <el-switch
                            v-model="scheduleConfig.levelBased"
                            active-text="按实力种子编排（强队后相遇）"
                            inactive-text="随机编排"
                        />
                    </el-form-item>

                    <el-alert
                        :title="getModeDescription(scheduleConfig.mode)"
                        type="info"
                        show-icon
                        style="margin-top: 15px"
                    />

                    <el-alert
                        v-if="scheduleConfig.mode === 'knockout' && !isPowerOf2(selectedTeamIds.length)"
                        title="淘汰赛制需要参赛队伍数量为 2 的幂次（如 2、4、8、16）"
                        type="error"
                        show-icon
                        style="margin-top: 10px"
                    />

                    <div class="schedule-info" style="margin-top: 20px">
                        <h4>预计生成赛程：</h4>
                        <el-descriptions :column="1" border>
                            <el-descriptions-item label="参赛队伍数">{{ selectedTeamIds.length }}支</el-descriptions-item>
                            <el-descriptions-item label="预计比赛场次">
                                {{ getEstimatedMatches(selectedTeamIds.length, scheduleConfig.mode) }}场
                            </el-descriptions-item>
                        </el-descriptions>
                    </div>
                </el-form>
            </div>

            <!-- 步骤 4: 预览确认 -->
            <div v-if="currentStep === 3" class="step-content">
                <el-alert
                    :title="loading ? '正在生成赛程...' : '赛程预览（共 ' + previewSchedules.length + ' 场比赛）'"
                    :type="loading ? 'info' : 'success'"
                    show-icon
                    style="margin-bottom: 15px"
                />

                <el-table :data="previewSchedules" border style="width: 100%" max-height="500" v-loading="loading">
                    <el-table-column type="index" label="序号" width="60" />
                    <el-table-column prop="bisaibiaoti" label="轮次"  />
                    <el-table-column label="对阵双方" min-width="300">
                        <template #default="{ row }">
                            <div class="match-row">
                                <span class="team-name">{{ getTeamName(row.qiuduia) }}</span>
                                <span class="vs">VS</span>
                                <span class="team-name">{{ getTeamName(row.qiuduib) }}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="实力对比" width="200">
                        <template #default="{ row }">
                            <div class="level-compare">
                                <el-tag size="small" type="warning">
                                    {{ getTeamLevel(row.qiuduia) }}
                                </el-tag>
                                <span style="margin: 0 5px">vs</span>
                                <el-tag size="small" type="warning">
                                    {{ getTeamLevel(row.qiuduib) }}
                                </el-tag>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" >
                        <template #default="{ row }">
                            <el-tag size="small">{{ row.zhuangtai }}</el-tag>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <!-- 操作按钮 -->
            <div class="step-actions" style="margin-top: 20px; text-align: center">
                <el-button @click="prevStep" :disabled="currentStep === 0">上一步</el-button>
                <el-button @click="nextStep" type="primary" :disabled="!canNext">
                    {{ currentStep === 3 ? '确认保存' : '下一步' }}
                </el-button>
                <el-button @click="resetForm" v-if="currentStep > 0">重新选择</el-button>
            </div>
        </el-card>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import { ref, reactive, computed, onMounted } from "vue";
    import { ElMessage, ElMessageBox } from "element-plus";

    const currentStep = ref(0);
    const loading = ref(false);

    // 表单数据
    const form = reactive({
        bisaixinxiid: null,
    });

    // 比赛列表
    const matchList = ref([]);
    const matchInfo = ref(null);
    const allTeams = ref([]);
    const selectedTeamIds = ref([]);
    const registeredTeamIds = ref([]);

    // 赛程配置
    const scheduleConfig = reactive({
        mode: 'single', // single, double, knockout
        levelBased: false,
    });

    // 预览赛程
    const previewSchedules = ref([]);

    // 加载比赛列表
    const loadMatchList = () => {
        http.post("/api/bisaixinxi/selectPages", { page: 1, pagesize: 100 }).then((res) => {
            if (res.code == 0) {
                matchList.value = res.data.lists.records || [];
            }
        });
    };

    // 比赛信息变化时
    const onMatchChange = (id) => {
        http.get("/api/schedule/matchInfo", { id }).then((res) => {
            if (res.code == 0) {
                matchInfo.value = res.data;
                // 自动选中已报名球队
                if (res.data.teams && res.data.teams.length > 0) {
                    registeredTeamIds.value = res.data.teams.map((t) => t.id);
                    selectedTeamIds.value = [...registeredTeamIds.value];
                }
            }
        });
    };

    // 加载所有球队
    const loadTeams = () => {
        http.get("/api/schedule/availableTeams").then((res) => {
            if (res.code == 0) {
                allTeams.value = res.data;
            }
        });
    };

    // 是否可选择球队
    const isTeamSelectable = (row) => {
        // 淘汰赛模式限制队伍数量
        if (scheduleConfig.mode === 'knockout') {
            const count = selectedTeamIds.value.length;
            const isPowerOf2 = (n) => n > 0 && (n & (n - 1)) === 0;
            
            // 如果已经选中的是 2 的幂次，不能再选
            if (isPowerOf2(count) && !selectedTeamIds.value.includes(row.id)) {
                return false;
            }
        }
        return true;
    };

    // 全选
    const selectAllTeams = () => {
        selectedTeamIds.value = allTeams.value.map((t) => t.id);
    };

    // 仅选已报名
    const selectOnlyRegistered = () => {
        selectedTeamIds.value = [...registeredTeamIds.value];
    };

    // 清空选择
    const clearSelection = () => {
        selectedTeamIds.value = [];
    };

    // 获取比赛模式说明
    const getModeDescription = (mode) => {
        const descriptions = {
            single: '单循环赛：每支球队与其他球队各比赛一次，适合队伍数量较少的情况',
            double: '双循环赛：每支球队与其他球队各比赛两次（主客场），比赛场次是单循环的两倍',
            knockout: '淘汰赛：单败淘汰制，输一场即出局，需要队伍数量为 2 的幂次（2、4、8、16...）',
        };
        return descriptions[mode] || '';
    };

    // 判断是否为 2 的幂次
    const isPowerOf2 = (n) => {
        return n > 0 && (n & (n - 1)) === 0;
    };

    // 获取预计比赛场次
    const getEstimatedMatches = (teamCount, mode) => {
        if (mode === 'single') {
            return (teamCount * (teamCount - 1)) / 2;
        } else if (mode === 'double') {
            return teamCount * (teamCount - 1);
        } else if (mode === 'knockout') {
            return teamCount - 1;
        }
        return 0;
    };

    // 获取球队名称
    const getTeamName = (teamId) => {
        if (!teamId || teamId <= 0) return '待定';
        const team = allTeams.value.find((t) => t.id === teamId);
        return team ? team.qiuduimingcheng : '未知球队';
    };

    // 获取球队实力等级
    const getTeamLevel = (teamId) => {
        if (!teamId || teamId <= 0) return '?';
        const team = allTeams.value.find((t) => t.id === teamId);
        return team ? team.shilidengji : '?';
    };

    // 上一步
    const prevStep = () => {
        if (currentStep.value > 0) {
            currentStep.value--;
        }
    };

    // 下一步
    const nextStep = () => {
        if (currentStep.value === 0) {
            // 验证比赛选择
            if (!form.bisaixinxiid) {
                ElMessage.warning('请选择比赛信息');
                return;
            }
            currentStep.value++;
            loadTeams();
        } else if (currentStep.value === 1) {
            // 验证球队选择
            if (selectedTeamIds.value.length < 2) {
                ElMessage.warning('至少需要选择 2 支球队');
                return;
            }
            if (scheduleConfig.mode === 'knockout' && !isPowerOf2(selectedTeamIds.value.length)) {
                ElMessage.warning('淘汰赛制需要队伍数量为 2 的幂次（如 2、4、8、16）');
                return;
            }
            currentStep.value++;
        } else if (currentStep.value === 2) {
            // 生成赛程预览
            generatePreview();
        } else if (currentStep.value === 3) {
            // 保存赛程
            saveSchedule();
        }
    };

    // 生成赛程预览
    const generatePreview = () => {
        loading.value = true;
        previewSchedules.value = [];

        http.post("/api/schedule/generate", {
            bisaixinxiid: form.bisaixinxiid,
            teamIds: selectedTeamIds.value,
            mode: scheduleConfig.mode,
            levelBased: scheduleConfig.levelBased,
        }).then((res) => {
            loading.value = false;
            if (res.code == 0) {
                previewSchedules.value = res.data.schedules || [];
                currentStep.value++;
            } else {
                ElMessage.error(res.msg);
            }
        }).catch((err) => {
            loading.value = false;
            ElMessage.error(err.message);
        });
    };

    // 保存赛程
    const saveSchedule = () => {
        if (previewSchedules.value.length === 0) {
            ElMessage.warning('没有需要保存的赛程');
            return;
        }

        ElMessageBox.confirm('确认保存生成的赛程吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }).then(() => {
            loading.value = true;
            http.post("/api/schedule/save", previewSchedules.value).then((res) => {
                loading.value = false;
                if (res.code == 0) {
                    ElMessage.success('保存成功，共生成 ' + res.data.count + ' 场比赛');
                    // 跳转到赛程列表
                    setTimeout(() => {
                        window.location.href = '#/admin/saichengbiao?bisaixinxiid=' + form.bisaixinxiid;
                    }, 1000);
                } else {
                    ElMessage.error(res.msg);
                }
            }).catch((err) => {
                loading.value = false;
                ElMessage.error(err.message);
            });
        });
    };

    // 重置表单
    const resetForm = () => {
        currentStep.value = 0;
        form.bisaixinxiid = null;
        matchInfo.value = null;
        selectedTeamIds.value = [];
        registeredTeamIds.value = [];
        previewSchedules.value = [];
        scheduleConfig.mode = 'single';
        scheduleConfig.levelBased = false;
    };

    // 计算是否可以下一步
    const canNext = computed(() => {
        if (currentStep.value === 0) {
            return !!form.bisaixinxiid;
        } else if (currentStep.value === 1) {
            return selectedTeamIds.value.length >= 2;
        } else if (currentStep.value === 2) {
            if (scheduleConfig.mode === 'knockout') {
                return isPowerOf2(selectedTeamIds.value.length);
            }
            return true;
        }
        return true;
    });

    onMounted(() => {
        loadMatchList();
    });
</script>

<style scoped lang="scss">
    .views-schedule-generate {
        .step-content {
            min-height: 400px;
            padding: 20px 0;
        }

        .match-row {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 15px;

            .team-name {
                font-weight: bold;
                font-size: 14px;
                min-width: 100px;
                text-align: center;
            }

            .vs {
                color: #f56c6c;
                font-weight: bold;
                font-size: 16px;
            }
        }

        .level-compare {
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
</style>
