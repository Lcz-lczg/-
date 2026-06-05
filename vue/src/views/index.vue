<template>
    <div class="views-zhuye">
        <!-- 轮播图区域 -->
        <div class="banner-section">
            <el-carousel indicator-position="outside" height="600px" :autoplay="true" :interval="4000">
                <el-carousel-item v-for="item in bhtList" :key="item.id">
                    <div class="banner-item" @click="$goto(item.url)" :style="{ 'background-image': 'url(' + $formatImageSrc(item.image) + ')' }">
                        <div class="banner-overlay"></div>
                        <div class="banner-content">
                            <h2 class="banner-title">{{ item.title || '校园篮球联赛' }}</h2>
                            <p class="banner-desc">激情燃烧 · 梦想起航</p>
                        </div>
                    </div>
                </el-carousel-item>
            </el-carousel>
        </div>

        <!-- 比赛信息区域 -->
        <div class="section match-section">
            <e-container>
                <div class="section-header">
                    <div class="title-box">
                        <i class="el-icon-soccer"></i>
                        <h3 class="section-title">比赛信息</h3>
                    </div>
                    <div class="more">
                        <router-link to="/bisaixinxi">查看更多 <i class="el-icon-arrow-right"></i></router-link>
                    </div>
                </div>
                <div class="match-grid">
                    <el-row :gutter="30">
                        <el-col v-for="r in bisaixinxilist" :md="6" :key="r.id">
                            <article class="match-card" @click="$goto('/bisaixinxi/detail?id=' + r.id)">
                                <div class="match-image">
                                    <div class="image-overlay"></div>
                                    <e-img class="primary_img" :src="r.bisaitupian" pb="100" :is-scale="true" />
                                    <div class="match-badge">{{ r.bisaimoshi || '5V5' }}</div>
                                </div>
                                <div class="match-content">
                                    <h4 class="match-title">{{ r.bisaibiaoti }}</h4>
                                    <div class="match-info">
                                        <span class="location"><i class="el-icon-location"></i> {{ r.bisaichangdi || '待定' }}</span>
                                        <span class="teams"><i class="el-icon-user"></i> {{ r.canjiaqiudui || 0 }} 支球队</span>
                                    </div>
                                    <div class="view-detail">
                                        <span>查看详情</span>
                                        <i class="el-icon-arrow-right"></i>
                                    </div>
                                </div>
                            </article>
                        </el-col>
                    </el-row>
                </div>
            </e-container>
        </div>

        <!-- 新闻公告区域 -->
        <div class="section news-section">
            <e-container>
                <div class="section-header">
                    <div class="title-box">
                        <i class="el-icon-document"></i>
                        <h3 class="section-title">新闻公告</h3>
                    </div>
                    <div class="more">
                        <router-link to="/xinwengonggao">查看更多 <i class="el-icon-arrow-right"></i></router-link>
                    </div>
                </div>
                <div class="news-grid">
                    <el-row :gutter="30">
                        <el-col v-for="r in xinwengonggaolist" :md="12" :key="r.id">
                            <article class="news-card" @click="$goto('/xinwengonggao/detail?id=' + r.id)">
                                <div class="news-image" v-if="r.tupian">
                                    <e-img :src="r.tupian" pb="100" />
                                </div>
                                <div class="news-content">
                                    <div class="news-tag">
                                        <e-select-view module="xinwenfenlei" :value="r.fenlei" select="id" show="fenleimingcheng"></e-select-view>
                                    </div>
                                    <h3 class="news-title">{{ r.biaoti }}</h3>
                                    <p class="news-desc" v-html="$substr(r.neirong, 50)"></p>
                                    <div class="news-footer">
                                        <span class="clicks"><i class="el-icon-view"></i> {{ r.dianjiliang }}</span>
                                        <span class="date"><i class="el-icon-time"></i> 最新发布</span>
                                    </div>
                                </div>
                            </article>
                        </el-col>
                    </el-row>
                </div>
            </e-container>
        </div>

        <!-- 赛程表区域 -->
        <div class="section schedule-section">
            <e-container>
                <div class="section-header">
                    <div class="title-box">
                        <i class="el-icon-trophy"></i>
                        <h3 class="section-title">赛程表</h3>
                    </div>

                    
                </div>
                <div class="schedule-table">
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
                </div>
            </e-container>
        </div>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import DB from "@/utils/db";

    import { ref, reactive } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";

    const route = useRoute();
    const bhtList = DB.name("lunbotu").order("id desc").limit(5).selectRef();
    const bisaixinxilist = DB.name("bisaixinxi").limit("4").order("id desc").selectRef();
    const saichengbiaolist = DB.name("saichengbiao").limit("6").order("id desc").selectRef();
    const xinwengonggaolist = DB.name("xinwengonggao").limit("4").order("id desc").selectRef();
</script>

<style scoped lang="scss">
    .views-zhuye {
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        min-height: 100vh;

        // 轮播图样式
        .banner-section {
            position: relative;
            
            .banner-item {
                position: relative;
                width: 100%;
                height: 600px;
                background-size: cover;
                background-position: center;
                cursor: pointer;
                overflow: hidden;

                .banner-overlay {
                    position: absolute;
                    top: 0;
                    left: 0;
                    right: 0;
                    bottom: 0;
                    background: rgba(0,0,0,0.3);
                }

                .banner-content {
                    position: absolute;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%);
                    text-align: center;
                    color: white;
                    z-index: 10;

                    .banner-title {
                        font-size: 48px;
                        font-weight: bold;
                        margin-bottom: 20px;
                        text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
                    }

                    .banner-desc {
                        font-size: 24px;
                        opacity: 0.9;
                    }
                }
            }

            :deep(.el-carousel__indicator) {
                background-color: rgba(255,255,255,0.5);
                
                &.is-active button {
                    background-color: #ff6b35;
                }
            }
        }

        // 通用区块样式
        .section {
            padding: 60px 0;

            .section-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 40px;
                padding-bottom: 20px;
                border-bottom: 3px solid #ff6b35;

                .title-box {
                    display: flex;
                    align-items: center;
                    gap: 12px;

                    i {
                        font-size: 28px;
                        color: #ff6b35;
                    }

                    .section-title {
                        font-size: 28px;
                        font-weight: bold;
                        color: #1e3a8a;
                        margin: 0;
                    }
                }

                .more {
                    a {
                        color: #ff6b35;
                        text-decoration: none;
                        font-weight: 500;
                        transition: all 0.3s;

                        &:hover {
                            color: #1e3a8a;
                            transform: translateX(5px);
                        }
                    }
                }
            }
        }

        // 比赛信息样式
        .match-section {
            background: white;

            .match-grid {
                .match-card {
                    background: white;
                    border-radius: 12px;
                    overflow: hidden;
                    box-shadow: 0 4px 20px rgba(0,0,0,0.08);
                    transition: all 0.3s;
                    cursor: pointer;
                    margin-bottom: 20px;

                    &:hover {
                        transform: translateY(-8px);
                        box-shadow: 0 12px 30px rgba(255,107,53,0.2);
                    }

                    .match-image {
                        position: relative;
                        height: 200px;
                        overflow: hidden;

                        .image-overlay {
                            position: absolute;
                            top: 0;
                            left: 0;
                            right: 0;
                            bottom: 0;
                            background: linear-gradient(180deg, transparent 0%, rgba(0,0,0,0.3) 100%);
                            z-index: 1;
                        }

                        .match-badge {
                            position: absolute;
                            top: 12px;
                            right: 12px;
                            background: linear-gradient(135deg, #ff6b35 0%, #f7931e 100%);
                            color: white;
                            padding: 6px 14px;
                            border-radius: 20px;
                            font-size: 12px;
                            font-weight: bold;
                            z-index: 2;
                        }
                    }

                    .match-content {
                        padding: 20px;

                        .match-title {
                            font-size: 18px;
                            font-weight: bold;
                            color: #1e3a8a;
                            margin-bottom: 12px;
                            line-height: 1.4;
                        }

                        .match-info {
                            display: flex;
                            gap: 15px;
                            margin-bottom: 15px;
                            font-size: 14px;
                            color: #666;

                            span {
                                display: flex;
                                align-items: center;
                                gap: 5px;
                            }
                        }

                        .view-detail {
                            display: flex;
                            align-items: center;
                            gap: 8px;
                            color: #ff6b35;
                            font-weight: 500;
                            font-size: 14px;
                        }
                    }
                }
            }
        }

        // 新闻公告样式
        .news-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

            .section-header {
                border-bottom-color: rgba(255,255,255,0.3);

                .title-box {
                    i, .section-title {
                        color: white;
                    }
                }

                .more a {
                    color: rgba(255,255,255,0.9);
                    
                    &:hover {
                        color: white;
                    }
                }
            }

            .news-grid {
                .news-card {
                    background: white;
                    border-radius: 12px;
                    overflow: hidden;
                    box-shadow: 0 4px 20px rgba(0,0,0,0.1);
                    transition: all 0.3s;
                    cursor: pointer;
                    margin-bottom: 20px;
                    display: flex;

                    &:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 12px 30px rgba(0,0,0,0.15);
                    }

                    .news-image {
                        width: 280px;
                        flex-shrink: 0;
                        overflow: hidden;

                        :deep(img) {
                            width: 100%;
                            height: 100%;
                            object-fit: cover;
                            transition: transform 0.3s;
                        }
                    }

                    &:hover .news-image :deep(img) {
                        transform: scale(1.1);
                    }

                    .news-content {
                        padding: 25px;
                        flex: 1;

                        .news-tag {
                            display: inline-block;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: white;
                            padding: 4px 12px;
                            border-radius: 12px;
                            font-size: 12px;
                            margin-bottom: 12px;
                        }

                        .news-title {
                            font-size: 20px;
                            font-weight: bold;
                            color: #1e3a8a;
                            margin-bottom: 12px;
                            line-height: 1.4;
                        }

                        .news-desc {
                            font-size: 14px;
                            color: #666;
                            line-height: 1.8;
                            margin-bottom: 15px;
                        }

                        .news-footer {
                            display: flex;
                            gap: 20px;
                            font-size: 13px;
                            color: #999;

                            span {
                                display: flex;
                                align-items: center;
                                gap: 5px;
                            }
                        }
                    }
                }
            }
        }

        // 赛程表样式
        .schedule-section {
            background: white;

            .schedule-table {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
                gap: 20px;
            }

            .schedule-card {
                background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.08);
                transition: all 0.3s;

                &:hover {
                    transform: translateY(-3px);
                    box-shadow: 0 8px 25px rgba(0,0,0,0.12);
                }

                .schedule-header {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    margin-bottom: 20px;
                    padding-bottom: 15px;
                    border-bottom: 2px solid #ff6b35;

                    .schedule-title {
                        font-size: 16px;
                        font-weight: bold;
                        color: #1e3a8a;
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
                    gap: 15px;

                    .team {
                        flex: 1;
                        text-align: center;

                        .team-name {
                            display: block;
                            font-size: 15px;
                            font-weight: 600;
                            color: #1e3a8a;
                            line-height: 1.4;
                            word-break: break-all;
                        }
                    }

                    .vs-box {
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        gap: 8px;
                        padding: 0 15px;
                        flex-shrink: 0;

                        .vs {
                            font-size: 14px;
                            font-weight: bold;
                            color: #ff6b35;
                        }

                        .score {
                            font-size: 20px;
                            font-weight: bold;
                            color: #1e3a8a;
                            background: white;
                            padding: 6px 14px;
                            border-radius: 8px;
                            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                        }
                    }
                }
            }
        }

        // 响应式
        @media (max-width: 768px) {
            .banner-item .banner-content .banner-title {
                font-size: 28px;
            }

            .news-grid .news-card {
                flex-direction: column;

                .news-image {
                    width: 100%;
                    height: 200px;
                }
            }

            .schedule-table {
                grid-template-columns: 1fr;
            }
        }
    }
</style>
