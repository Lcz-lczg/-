<template>
    <div class="views-bisaixinxi-web-detail">
        <!-- 顶部横幅 -->
        <div class="detail-header">
            <e-container>
                <div class="header-content">
                    <div class="breadcrumb">
                        <router-link to="/index">首页</router-link>
                        <i class="el-icon-arrow-right"></i>
                        <router-link to="/bisaixinxi">比赛信息</router-link>
                        <i class="el-icon-arrow-right"></i>
                        <span>详情</span>
                    </div>
                    <h1 class="page-title">{{ map.bisaibiaoti }}</h1>
                </div>
            </e-container>
        </div>

        <div>
            <e-container>
                <div class="detail-content">
                    <!-- 左侧信息卡片 -->
                    <div class="info-card">
                        <div class="card-image">
                            <e-img :src="map.bisaitupian" pb="100" :is-scale="true" />
                            <div class="image-overlay">
                                <span class="badge">{{ map.bisaimoshi || '5V5' }}</span>
                            </div>
                        </div>

                        <div class="card-body">
                            <h3 class="card-title">{{ map.bisaibiaoti }}</h3>

                            <div class="info-list">
                                <div class="info-item">
                                    <i class="el-icon-document"></i>
                                    <span class="label">比赛编号</span>
                                    <span class="value">{{ map.bisaibianhao }}</span>
                                </div>
                                <div class="info-item">
                                    <i class="el-icon-trophy"></i>
                                    <span class="label">比赛类型</span>
                                    <span class="value">
                                        <e-select-view module="bisaileixing" :value="map.bisaileixing" select="id" show="leixingmingcheng"></e-select-view>
                                    </span>
                                </div>
                                <div class="info-item">
                                    <i class="el-icon-soccer"></i>
                                    <span class="label">比赛模式</span>
                                    <span class="value">{{ map.bisaimoshi }}</span>
                                </div>
                                <div class="info-item">
                                    <i class="el-icon-user"></i>
                                    <span class="label">参加球队</span>
                                    <span class="value">{{ map.canjiaqiudui }} 支</span>
                                </div>
                                <div class="info-item">
                                    <i class="el-icon-location"></i>
                                    <span class="label">比赛场地</span>
                                    <span class="value">{{ map.bisaichangdi }}</span>
                                </div>


                            </div>

                            <div class="action-buttons">
                                <template v-if="$session.cx == '教练'">
                                    <el-button type="primary" class="btn-apply" @click="$router.push('/bisaibaoming/add?id='+map.id)">
                                        <i class="el-icon-edit"></i> 立即报名
                                    </el-button>
                                </template>

                                <e-collect module="dianzan" :form="{ biaoid: 'biaoid', biao: 'biao', biaoti: 'biaoti' }" :biaoid="$route.query.id" biao="bisaixinxi" :biaoti="map.bisaibiaoti">
                                    <template #icon="{ isCollect }">
                                        <i :class="is_dianzan ? 'fa fa-thumbs-up' : 'fa fa-thumbs-o-up'"></i>
                                    </template>
                                    <template #default="{ isCollect }">
                                        {{ isCollect ? "已点赞" : "点赞" }}
                                    </template>
                                </e-collect>
                            </div>
                        </div>
                    </div>

                    <!-- 右侧详情和赛程 -->
                    <div class="right-content">
                        <el-tabs type="border-card" class="detail-tabs">
                            <el-tab-pane label="比赛详情">
                                <div class="detail-content-html" v-html="map.bisaineirong"></div>
                            </el-tab-pane>

                            <el-tab-pane label="赛程表">
                                <div class="schedule-list">
                                    <div class="schedule-card" v-for="(r, i) in saichengbiaolist" :key="i">
                                        <div class="schedule-header">
                                            <span class="schedule-title">{{ r.bisaibiaoti }}</span>
                                            <span class="schedule-status" :class="r.zhuangtai">{{ r.zhuangtai || '待开始' }}</span>
                                        </div>
                                        <div class="schedule-match">
                                            <div class="team team-a">
                                                <span class="team-name">
                                                    <e-select-view module="qiudui" :value="r.qiuduia" select="id" show="qiuduimingcheng"></e-select-view>
                                                </span>
                                            </div>
                                            <div class="vs-box">
                                                <span class="vs">VS</span>
                                                <span class="score" v-if="r.bifen && r.bifen !== '0'">{{ r.bifen }}</span>
                                            </div>
                                            <div class="team team-b">
                                                <span class="team-name">
                                                    <e-select-view module="qiudui" :value="r.qiuduib" select="id" show="qiuduimingcheng"></e-select-view>
                                                </span>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="empty-data" v-if="saichengbiaolist.length === 0">
                                        <i class="el-icon-document"></i>
                                        <p>暂无赛程数据</p>
                                    </div>
                                </div>
                            </el-tab-pane>

                            <el-tab-pane label="评论区">
                                <e-comments 
                                    module="bisaixinxi" 
                                    :lists="comments" 
                                    :biaoti="map.bisaibiaoti" 
                                    :is-comment="isCommit" 
                                    :commit-callback="commentCallback" 
                                    @success="getcommentsFunc" 
                                    user="username" 
                                    headimg="headimg" 
                                    :is-reply="true" 
                                    name="nickname">
                                </e-comments>
                            </el-tab-pane>
                        </el-tabs>
                    </div>
                </div>
            </e-container>
        </div>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import DB from "@/utils/db";
    import router from "@/router";
    import rule from "@/utils/rule";
    import EShangpinban from "@/components/shangpin/shangpinban.vue";
    import EComments from "@/components/comments/comment";

    import { ref, reactive, watch, computed, unref } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { extend } from "@/utils/extend";
    import { useBisaixinxiFindById, canBisaixinxiFindById, canBisaibaomingCreateForm, canBisaibaomingInsert } from "@/module";
    import { ElLoading, ElMessage, ElMessageBox } from "element-plus";

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

    const map = useBisaixinxiFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canBisaixinxiFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );

    const bisaibaomingForm = ref({});
    const readMap = ref({});
    const bisaibaomingFormElement = ref(null);

    watch(
        () => map,
        async () => {
            if (!map.id || !session("username")) return;

            var s = await canBisaibaomingCreateForm(map.id, map);
            bisaibaomingForm.value = s.form;
            readMap.value = s.readMap;
        },
        { deep: true }
    );

    const onAddCard = async () => {
        if (!session("username")) {
            var r = await ElMessageBox.confirm("请先登录");
            router.push({ path: "/login", query: { redirect: route.fullPath } });
            return;
        }
        bisaibaomingFormElement.value.validate().then(async () => {
            const loadingInstance = ElLoading.service({
                fullscreen: true,
                lock: true,
                text: "正在提交...",
            });
            try {
                const res = await canBisaibaomingInsert(bisaibaomingForm.value);
                loadingInstance.close();
                if (res.code == 0) {
                    ElMessage.success("添加比赛报名成功");
                } else {
                    ElMessage.error(res.msg);
                }
            } catch (e) {
                ElMessage.error(e.message);
                loadingInstance.close();
            }
        });
    };

    const isCommit = ref(true);
    const commentCallback = (commit) => {};
    const comments = ref([]);
    const getcommentsFunc = async () => {
        comments.value = await DB.name("pinglun").alias("pl").joinLeft("guanzhong u", "u.yonghuming=pl.pinglunren").field("pl.*").field("u.touxiang as headimg").field("u.xingming as nickname").field("u.yonghuming as username").where("pl.biao", "bisaixinxi").where("pl.biaoid", route.query.id).order("pl.id desc").select();
    };
    getcommentsFunc();

    const saichengbiaolist = ref([]);
    const getsaichengbiaolist = async () => {
        saichengbiaolist.value = await DB.name("saichengbiao").where("bisaixinxiid", "=", route.query?.id).limit("99").order("id desc").select();
    };
    watch(() => route, getsaichengbiaolist, { immediate: true, deep: true });
</script>

<style scoped lang="scss">
    .views-bisaixinxi-web-detail {
        min-height: 100vh;
        background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
        padding-bottom: 40px;

        // 顶部横幅
        .detail-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 40px 0;
            margin-bottom: 30px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);

            .header-content {
                .breadcrumb {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    margin-bottom: 15px;
                    font-size: 14px;
                    color: rgba(255,255,255,0.8);

                    a {
                        color: rgba(255,255,255,0.9);
                        text-decoration: none;
                        transition: color 0.3s;

                        &:hover {
                            color: white;
                        }
                    }

                    i {
                        font-size: 12px;
                    }
                }

                .page-title {
                    font-size: 32px;
                    color: white;
                    margin: 0;
                    font-weight: bold;
                    text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
                }
            }
        }

        .detail-content {
            display: grid;
            grid-template-columns: 380px 1fr;
            gap: 30px;

            // 左侧信息卡片
            .info-card {
                background: white;
                border-radius: 16px;
                overflow: hidden;
                box-shadow: 0 4px 20px rgba(0,0,0,0.08);
                transition: all 0.3s;

                &:hover {
                    box-shadow: 0 10px 40px rgba(0,0,0,0.12);
                }

                .card-image {
                    position: relative;
                    height: 280px;
                    overflow: hidden;

                    :deep(img) {
                        width: 100%;
                        height: 100%;
                        object-fit: cover;
                        transition: transform 0.5s;
                    }

                    &:hover :deep(img) {
                        transform: scale(1.05);
                    }

                    .image-overlay {
                        position: absolute;
                        top: 15px;
                        right: 15px;

                        .badge {
                            display: inline-block;
                            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                            color: white;
                            padding: 8px 16px;
                            border-radius: 20px;
                            font-size: 13px;
                            font-weight: bold;
                            box-shadow: 0 4px 15px rgba(245,87,108,0.4);
                        }
                    }
                }

                .card-body {
                    padding: 25px;

                    .card-title {
                        font-size: 20px;
                        color: #1e293b;
                        margin: 0 0 20px 0;
                        font-weight: bold;
                        line-height: 1.4;
                    }

                    .info-list {
                        .info-item {
                            display: flex;
                            align-items: center;
                            gap: 12px;
                            padding: 12px 0;
                            border-bottom: 1px solid #f1f5f9;

                            &:last-child {
                                border-bottom: none;
                            }

                            i {
                                font-size: 18px;
                                color: #667eea;
                                width: 20px;
                            }

                            .label {
                                font-size: 14px;
                                color: #64748b;
                                min-width: 80px;
                            }

                            .value {
                                font-size: 14px;
                                color: #1e293b;
                                font-weight: 500;
                                flex: 1;
                            }
                        }
                    }

                    .action-buttons {
                        margin-top: 25px;
                        display: flex;
                        flex-direction: column;
                        gap: 12px;

                        .btn-apply {
                            width: 100%;
                            padding: 14px;
                            font-size: 16px;
                            font-weight: 600;
                            border-radius: 10px;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            border: none;
                            transition: all 0.3s;

                            &:hover {
                                transform: translateY(-2px);
                                box-shadow: 0 8px 25px rgba(102,126,234,0.4);
                            }
                        }

                        :deep(.e-collect) {
                            button {
                                width: 100%;
                                padding: 12px;
                                border-radius: 10px;
                                border: 2px solid #667eea;
                                background: white;
                                color: #667eea;
                                font-weight: 600;
                                transition: all 0.3s;

                                &:hover {
                                    background: #667eea;
                                    color: white;
                                }
                            }
                        }
                    }
                }
            }

            // 右侧内容
            .right-content {
                .detail-tabs {
                    background: white;
                    border-radius: 16px;
                    overflow: hidden;
                    box-shadow: 0 4px 20px rgba(0,0,0,0.08);

                    :deep(.el-tabs__header) {
                        background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
                        padding: 0;
                        border-bottom: 2px solid #e2e8f0;

                        .el-tabs__item {
                            padding: 18px 30px;
                            font-size: 15px;
                            font-weight: 600;
                            color: #475569;
                            border: none;

                            &.is-active {
                                color: #667eea;
                                background: white;
                            }

                            &:hover {
                                color: #667eea;
                            }
                        }
                    }

                    :deep(.el-tabs__content) {
                        padding: 30px;
                    }
                }

                .detail-content-html {
                    line-height: 1.8;
                    color: #334155;

                    :deep(img) {
                        max-width: 100%;
                        height: auto;
                        border-radius: 10px;
                        margin: 15px 0;
                    }

                    :deep(p) {
                        margin-bottom: 15px;
                    }
                }

                .schedule-list {
                    display: grid;
                    grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
                    gap: 20px;

                    .schedule-card {
                        background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
                        border-radius: 12px;
                        padding: 20px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.05);
                        transition: all 0.3s;

                        &:hover {
                            transform: translateY(-3px);
                            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
                        }

                        .schedule-header {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            margin-bottom: 15px;
                            padding-bottom: 12px;
                            border-bottom: 2px solid #e2e8f0;

                            .schedule-title {
                                font-size: 15px;
                                font-weight: bold;
                                color: #1e293b;
                            }

                            .schedule-status {
                                padding: 4px 12px;
                                border-radius: 12px;
                                font-size: 12px;
                                font-weight: 500;

                                &.待开始 {
                                    background: #e3f2fd;
                                    color: #1976d2;
                                }

                                &.进行中 {
                                    background: #fff3e0;
                                    color: #f57c00;
                                }

                                &.已结束 {
                                    background: #e8f5e9;
                                    color: #388e3c;
                                }
                            }
                        }

                        .schedule-match {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            margin-bottom: 15px;

                            .team {
                                flex: 1;
                                text-align: center;

                                .team-name {
                                    font-size: 14px;
                                    font-weight: 600;
                                    color: #1e293b;
                                }
                            }

                            .vs-box {
                                display: flex;
                                flex-direction: column;
                                align-items: center;
                                gap: 8px;
                                padding: 0 15px;

                                .vs {
                                    font-size: 13px;
                                    font-weight: bold;
                                    color: #f97316;
                                }

                                .score {
                                    font-size: 18px;
                                    font-weight: bold;
                                    color: #1e293b;
                                    background: white;
                                    padding: 6px 12px;
                                    border-radius: 8px;
                                    box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                                }
                            }
                        }

                        .schedule-footer {
                            text-align: center;
                            padding-top: 15px;
                            border-top: 1px solid #e2e8f0;
                        }
                    }

                    .empty-data {
                        text-align: center;
                        padding: 60px 20px;
                        color: #94a3b8;

                        i {
                            font-size: 60px;
                            margin-bottom: 15px;
                        }

                        p {
                            font-size: 16px;
                            margin: 0;
                        }
                    }
                }
            }
        }

        // 响应式
        @media (max-width: 1024px) {
            .detail-content {
                grid-template-columns: 1fr;
            }

            .info-card {
                max-width: 500px;
                margin: 0 auto;
            }
        }
    }
</style>
