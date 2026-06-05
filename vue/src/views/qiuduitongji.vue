<template>
    <div class="views-qiuduitongji">
        <div class="tongji-header">
            <e-container>
                <h1 class="page-title">
                    <i class="el-icon-trophy"></i>
                    球队统计
                </h1>
                <p class="page-desc">实时数据 · 实力排行</p>
            </e-container>
        </div>

        <div>
            <e-container>
                <div class="search-box">
                    <el-input
                        v-model="search.qiuduimingcheng"
                        placeholder="搜索球队名称"
                        prefix-icon="el-icon-search"
                        clearable
                        class="search-input"
                    />
                </div>

                <div class="tongji-content">
                    <!-- 积分排行榜 -->
                    <div class="ranking-section">
                        <h2 class="section-title">
                            <i class="el-icon-trophy"></i>
                            球队积分排行榜
                        </h2>
                        <div class="ranking-grid">
                            <div 
                                class="ranking-card" 
                                v-for="(team, index) in rankedTeams" 
                                :key="team.id"
                                :class="['rank-' + (index + 1)]"
                            >
                                <div class="rank-badge">
                                    <span class="rank-number">{{ index + 1 }}</span>
                                </div>
                                <div class="team-info">
                                    <div class="team-name">{{ team.qiuduimingcheng }}</div>
                                    <div class="team-stats">
                                        <span class="stat-item">
                                            <i class="el-icon-star-on"></i>
                                            实力：{{ team.sum_shilidengji }}
                                        </span>
                                    </div>
                                </div>
                                <div class="team-score">
                                    <span class="score-value">{{ team.sum_qiuduijifen }}</span>
                                    <span class="score-label">积分</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="data-table">
                        <el-table :data="qiuduiList" style="width: 100%" :header-cell-style="{ background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', color: '#fff' }">
                            <el-table-column type="index" label="排名" width="80" align="center" />
                            <el-table-column prop="qiuduimingcheng" label="球队名称" align="center" />
                            <el-table-column prop="sum_shilidengji" label="实力等级" sortable align="center">
                                <template #default="{ row }">
                                    <el-rate v-model="row.sum_shilidengji" disabled show-text text-color="#ff9900" />
                                </template>
                            </el-table-column>
                            <el-table-column prop="sum_qiuduijifen" label="球队积分" sortable align="center">
                                <template #default="{ row }">
                                    <span class="score-tag">{{ row.sum_qiuduijifen }}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>

                    <div class="chart-box">
                        <v3-echarts :options="barOptions" width="100%" height="400px"></v3-echarts>
                    </div>
                </div>
            </e-container>
        </div>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import DB from "@/utils/db";

    import { ref, reactive, watch, computed, unref } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { extend } from "@/utils/extend";

    const route = useRoute();

    const search = reactive({ qiuduimingcheng: "" });
    extend(search, route.query);
    const serachSubmit = () => {
        loadDatabaseList();
    };
    var timer;
    watch(
        () => search,
        () => {
            clearTimeout(timer);
            timer = setTimeout(() => {
                serachSubmit();
            }, 200);
        },
        { deep: true }
    );
    const qiuduiList = ref([]);
    const loadDatabaseList = async () => {
        var where = " 1=1 ";
        if (search.qiuduimingcheng) {
            where += ` AND qd.qiuduimingcheng LIKE '%${search.qiuduimingcheng}%' `;
        }
        qiuduiList.value = await DB.name("qiudui").alias("qd").field("qd.qiuduimingcheng").group("qd.qiuduimingcheng").field("sum(qd.shilidengji) sum_shilidengji").order("sum_shilidengji DESC").field("sum(qd.qiuduijifen) sum_qiuduijifen").order("sum_qiuduijifen DESC").where(where).select();
    };

    loadDatabaseList();
    
    // 计算属性：按积分排序的球队列表
    const rankedTeams = computed(() => {
        return [...unref(qiuduiList)].sort((a, b) => {
            // 先按积分排序
            const scoreDiff = parseFloat(b.sum_qiuduijifen) - parseFloat(a.sum_qiuduijifen);
            if (scoreDiff !== 0) return scoreDiff;
            // 积分相同按实力排序
            return parseFloat(b.sum_shilidengji) - parseFloat(a.sum_shilidengji);
        });
    });

    const barOptions = computed(() => {
        var list = unref(qiuduiList);
        return {
            title: {
                text: "球队实力与积分对比",
                left: "center",
                textStyle: {
                    fontSize: 18,
                    color: "#1e3a8a",
                    fontWeight: "bold"
                }
            },
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    type: "shadow",
                },
            },
            legend: {
                data: ["实力等级", "球队积分"],
                top: "30px",
                textStyle: {
                    fontSize: 14
                }
            },
            grid: {
                left: "3%",
                right: "4%",
                bottom: "3%",
                top: "80px",
                containLabel: true,
            },
            xAxis: [
                {
                    type: "category",
                    data: list.map((s) => s.qiuduimingcheng),
                    axisTick: {
                        alignWithLabel: true,
                    },
                    axisLabel: {
                        interval: 0,
                        rotate: 30,
                        fontSize: 12,
                        color: "#666"
                    }
                },
            ],
            yAxis: [
                {
                    type: "value",
                    name: "数值",
                    axisLabel: {
                        color: "#666"
                    },
                    splitLine: {
                        lineStyle: {
                            color: "#eee",
                            type: "dashed"
                        }
                    }
                },
            ],
            series: [
                {
                    name: "实力等级",
                    type: "bar",
                    barWidth: "30px",
                    itemStyle: {
                        color: "#667eea"
                    },
                    data: list.map((s) => parseFloat(s.sum_shilidengji)),
                },
                {
                    name: "球队积分",
                    type: "bar",
                    barWidth: "30px",
                    itemStyle: {
                        color: "#f5576c"
                    },
                    data: list.map((s) => parseFloat(s.sum_qiuduijifen)),
                },
            ],
        };
    });
</script>

<style scoped lang="scss">
    .views-qiuduitongji {
        min-height: 100vh;
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        padding-bottom: 40px;

        .tongji-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 60px 0;
            margin-bottom: 30px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);

            .page-title {
                font-size: 36px;
                color: white;
                margin: 0;
                text-align: center;
                font-weight: bold;

                i {
                    margin-right: 15px;
                    font-size: 42px;
                }
            }

            .page-desc {
                text-align: center;
                color: rgba(255,255,255,0.8);
                font-size: 16px;
                margin-top: 10px;
            }
        }

        .search-box {
            margin-bottom: 30px;
            display: flex;
            justify-content: center;

            .search-input {
                width: 400px;
                :deep(.el-input__wrapper) {
                    box-shadow: 0 2px 12px rgba(0,0,0,0.1);
                    border-radius: 25px;
                    padding: 8px 20px;
                }
            }
        }

        .tongji-content {
            display: grid;
            gap: 30px;

            // 排行榜样式
            .ranking-section {
                .section-title {
                    font-size: 28px;
                    color: #1e3a8a;
                    margin: 0 0 25px 0;
                    display: flex;
                    align-items: center;
                    gap: 12px;
                    font-weight: bold;

                    i {
                        font-size: 32px;
                        color: #f59e0b;
                    }
                }

                .ranking-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
                    gap: 20px;
                }

                .ranking-card {
                    background: white;
                    border-radius: 16px;
                    padding: 20px;
                    display: flex;
                    align-items: center;
                    gap: 15px;
                    box-shadow: 0 4px 15px rgba(0,0,0,0.08);
                    transition: all 0.3s;
                    position: relative;
                    overflow: hidden;

                    &:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 10px 30px rgba(0,0,0,0.15);
                    }

                    // 前三名特殊样式
                    &.rank-1 {
                        background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
                        border: 2px solid #f59e0b;

                        .rank-badge {
                            background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
                            box-shadow: 0 4px 15px rgba(245,158,11,0.4);
                        }
                    }

                    &.rank-2 {
                        background: linear-gradient(135deg, #e5e7eb 0%, #d1d5db 100%);
                        border: 2px solid #9ca3af;

                        .rank-badge {
                            background: linear-gradient(135deg, #9ca3af 0%, #6b7280 100%);
                            box-shadow: 0 4px 15px rgba(156,163,175,0.4);
                        }
                    }

                    &.rank-3 {
                        background: linear-gradient(135deg, #fed7aa 0%, #fdba74 100%);
                        border: 2px solid #ea580c;

                        .rank-badge {
                            background: linear-gradient(135deg, #ea580c 0%, #c2410c 100%);
                            box-shadow: 0 4px 15px rgba(234,88,12,0.4);
                        }
                    }

                    .rank-badge {
                        width: 50px;
                        height: 50px;
                        border-radius: 50%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        flex-shrink: 0;

                        .rank-number {
                            font-size: 22px;
                            font-weight: bold;
                            color: white;
                        }
                    }

                    .team-info {
                        flex: 1;
                        min-width: 0;

                        .team-name {
                            font-size: 16px;
                            font-weight: bold;
                            color: #1e293b;
                            margin-bottom: 8px;
                            white-space: nowrap;
                            overflow: hidden;
                            text-overflow: ellipsis;
                        }

                        .team-stats {
                            .stat-item {
                                font-size: 13px;
                                color: #64748b;
                                display: flex;
                                align-items: center;
                                gap: 5px;

                                i {
                                    color: #f59e0b;
                                    font-size: 14px;
                                }
                            }
                        }
                    }

                    .team-score {
                        text-align: right;
                        flex-shrink: 0;

                        .score-value {
                            display: block;
                            font-size: 28px;
                            font-weight: bold;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            -webkit-background-clip: text;
                            -webkit-text-fill-color: transparent;
                            background-clip: text;
                            line-height: 1;
                            margin-bottom: 4px;
                        }

                        .score-label {
                            font-size: 12px;
                            color: #64748b;
                        }
                    }
                }
            }

            .data-table {
                background: white;
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.08);

                :deep(.el-table) {
                    th {
                        font-weight: 600;
                    }
                    
                    .el-table__row:hover {
                        background: #f5f7fa;
                    }
                }

                .score-tag {
                    display: inline-block;
                    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                    color: white;
                    padding: 4px 12px;
                    border-radius: 12px;
                    font-weight: bold;
                    font-size: 14px;
                }
            }

            .chart-box {
                background: white;
                border-radius: 12px;
                padding: 30px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.08);
            }
        }
    }
</style>
