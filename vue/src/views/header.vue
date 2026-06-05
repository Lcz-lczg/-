<template>
    <div class="aa-cy-header-main">
        <header class="aa-header clearfix">
            <a href="/" class="logo" :title="config.title">
                <span class="logo-text">{{ config.title }}</span>
                <span class="logo-badge">🏀</span>
            </a>
            <ul class="nav">
                <li :class="{ current: isFullPathActive('/index') }">
                    <router-link :to="'/index'">
                        <i class="el-icon-house"></i> 首页
                    </router-link>
                </li>
                <li :class="{ current: isFullPathActive({ path: '/bisaixinxi' }) }">
                    <router-link :to="{ path: '/bisaixinxi' }">
                        <i class="el-icon-trophy"></i> 比赛信息
                    </router-link>
                </li>
                <li :class="{ current: isFullPathActive({ path: '/xinwengonggao' }) }">
                    <router-link :to="{ path: '/xinwengonggao' }">
                        <i class="el-icon-document"></i> 新闻公告
                    </router-link>
                </li>
                <li class="has-submenu" :class="{ current: isFullPathActive({ path: '/guanzhong/add' }) || isFullPathActive({ path: '/guanzhong' }) }">
                    <router-link :to="{ path: '/guanzhong/add' }">
                        <i class="el-icon-user"></i> 观众注册
                    </router-link>
                </li>




                







            </ul>
            <div class="right clearfix">
                <template v-if="$session.username">
                    <div class="user-menu">
                        <el-dropdown>
                            <div class="user-info">
                                <div class="avatar">
                                    <i class="el-icon-user"></i>
                                </div>
                                <div class="user-detail">
                                    <span class="name">{{ $session.username }}</span>
                                    <span class="role">{{ $session.cx }}</span>
                                </div>
                                <i class="el-icon-arrow-down"></i>
                            </div>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item @click="$router.push('/admin/sy')">
                                        <i class="el-icon-user"></i> 个人中心
                                    </el-dropdown-item>
                                    <el-dropdown-item divided @click="logout">
                                        <i class="el-icon-switch-button"></i> 退出登录
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>
                </template>
                <template v-else>
                    <router-link to="/login" class="login-btn">
                        <i class="el-icon-user"></i> 登录
                    </router-link>
                </template>
            </div>
        </header>
    </div>
</template>

<script setup>
    import { useRouter, useRoute } from "vue-router";
    import { ref } from "vue";
    import { logout, session } from "@/utils";
    import config from "@/config";
    import { isFullPathActive } from "@/router/router-utils";

    const router = useRouter();
    const route = useRoute();
</script>

<style lang="scss" scoped>
    .aa-cy-header-main {
        height: 70px;
    }

    .aa-header {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 70px;
        padding: 0 5%;
        background: rgba(255, 255, 255, 0.98);
        backdrop-filter: blur(10px);
        z-index: 999;
        box-shadow: 0 2px 20px rgba(0,0,0,0.08);
        display: flex;
        align-items: center;

        .logo {
            display: flex;
            align-items: center;
            gap: 10px;
            text-decoration: none;
            padding-right: 40px;

            .logo-text {
                font-size: 22px;
                font-weight: bold;
                background: linear-gradient(135deg, #f97316 0%, #ea580c 100%);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
            }

            .logo-badge {
                font-size: 24px;
            }
        }

        .nav {
            display: flex;
            gap: 8px;
            margin-left: 20px;
            flex: 1;

            li {
                position: relative;

                > a {
                    display: flex;
                    align-items: center;
                    gap: 6px;
                    padding: 10px 18px;
                    color: #475569;
                    text-decoration: none;
                    border-radius: 10px;
                    font-weight: 500;
                    transition: all 0.3s;

                    i {
                        font-size: 16px;
                    }

                    &:hover {
                        background: linear-gradient(135deg, rgba(249,115,22,0.1) 0%, rgba(234,88,12,0.1) 100%);
                        color: #f97316;
                    }
                }

                &.current > a {
                    background: linear-gradient(135deg, #f97316 0%, #ea580c 100%);
                    color: white;
                    box-shadow: 0 4px 12px rgba(249,115,22,0.3);
                }

                &.has-submenu {
                    .submenu {
                        display: none;
                        position: absolute;
                        top: 100%;
                        left: 50%;
                        transform: translateX(-50%);
                        background: white;
                        border-radius: 12px;
                        box-shadow: 0 10px 40px rgba(0,0,0,0.15);
                        padding: 8px;
                        min-width: 140px;
                        margin-top: 8px;
                        z-index: 1000;

                        li {
                            a {
                                padding: 10px 16px;
                                color: #475569;
                                border-radius: 8px;

                                &:hover {
                                    background: #f1f5f9;
                                    color: #f97316;
                                }
                            }
                        }
                    }

                    &:hover .submenu {
                        display: block;
                        animation: slideDown 0.3s ease;
                    }
                }
            }
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateX(-50%) translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateX(-50%) translateY(0);
            }
        }

        .right {
            display: flex;
            align-items: center;
            gap: 15px;

            .user-menu {
                .user-info {
                    display: flex;
                    align-items: center;
                    gap: 12px;
                    padding: 8px 16px;
                    background: linear-gradient(135deg, #f1f5f9 0%, #e2e8f0 100%);
                    border-radius: 25px;
                    cursor: pointer;
                    transition: all 0.3s;

                    &:hover {
                        background: linear-gradient(135deg, #e2e8f0 0%, #cbd5e1 100%);
                    }

                    .avatar {
                        width: 36px;
                        height: 36px;
                        background: linear-gradient(135deg, #f97316 0%, #ea580c 100%);
                        border-radius: 50%;
                        display: flex;
                        align-items: center;
                        justify-content: center;

                        i {
                            color: white;
                            font-size: 18px;
                        }
                    }

                    .user-detail {
                        display: flex;
                        flex-direction: column;

                        .name {
                            font-size: 14px;
                            font-weight: 600;
                            color: #1e293b;
                        }

                        .role {
                            font-size: 12px;
                            color: #64748b;
                        }
                    }

                    .el-icon-arrow-down {
                        font-size: 14px;
                        color: #64748b;
                    }
                }
            }

            .login-btn {
                padding: 10px 24px;
                background: linear-gradient(135deg, #f97316 0%, #ea580c 100%);
                color: white;
                border: none;
                border-radius: 25px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s;
                display: flex;
                align-items: center;
                gap: 8px;
                text-decoration: none;

                &:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 6px 20px rgba(249,115,22,0.4);
                }
            }
        }
    }
</style>
