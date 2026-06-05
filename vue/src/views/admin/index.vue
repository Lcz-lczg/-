<template>
    <div class="flatlab-index" :class="{ 'open-sidebar': openSidebar }">
        <header class="header">
            <div class="sidebar-toggle-box">
                <i class="fa fa-reorder" @click="openSidebar = !openSidebar"></i>
            </div>
            <a href="javascript:;" class="logo">{{ config.title }}</a>
            <div class="top-nav">
                <!--search & user info start-->
                <ul class="nav pull-right top-menu">
                    <!-- user login dropdown start-->

                    <li class="dropdown">
                        <el-popover placement="bottom-start" width="280" popper-class="popperLogout" :offset="-30" trigger="click">
                            <template #reference>
                                <a class="dropdown-toggle" href="#">
                                    <img alt="" v-if="!$session.touxiang" src="./image/img.png" />
                                    <img alt="" v-else :src="$formatImageSrc($session.touxiang)" style="width: 32px" />
                                    <span class="username">{{ $session.username }}</span>
                                    <span class="caret"></span>
                                </a>
                            </template>

                            <ul class="dropdown-menu extended logout" slot="default">
                                <li>
                                    <router-link to="/"> <i class="fa fa-home"></i> 前台首页 </router-link>
                                </li>
                                <li>
                                    <router-link to="/admin/mod"> <i class="fa fa-edit"></i> 修改密码 </router-link>
                                </li>
                                <li>
                                    <router-link to="/admin/sy"> <i class="fa fa-arrow-right"></i> 后台首页</router-link>
                                </li>
                                <li>
                                    <a @click="logout" href="javascript:;">
                                        <i class="fa fa-lock"></i>
                                        退出系统
                                    </a>
                                </li>
                            </ul>
                        </el-popover>
                    </li>
                    <!-- user login dropdown end -->
                </ul>
                <!--search & user info end-->
            </div>
        </header>
        <div class="sidebar nav-collapse scroll-scrollbar-style" tabindex="5000" @click="isCloseSidebar">
            <!-- sidebar menu start-->
            <ul class="sidebar-menu" style="display: block">
                <li class="sub-menu" :class="{ active: active == k }" v-for="(r, k) in menus" :key="k">
                    <a href="javascript:;" @click.stop="active = active == k ? -1 : k">
                        <i :class="r.icon ? r.icon : 'el-icon-user'"></i>
                        <span>{{ r.label }}</span>
                        <span class="arrow el-icon-caret-right"></span>
                    </a>
                    <collapse-transition>
                        <ul class="sub" v-show="active == k" style="">
                            <li v-for="(v, j) in r.children" :key="j">
                                <router-link :to="v.to"> {{ v.label }} </router-link>
                            </li>
                        </ul>
                    </collapse-transition>
                </li>
            </ul>
            <!-- sidebar menu end-->
        </div>

        <div class="main-content">
            <div class="wrapper scroll-scrollbar-style">
                <router-view></router-view>
            </div>
        </div>
        <div class="mask-bg" @click="openSidebar = false"></div>
    </div>
</template>
<style type="text/scss" lang="scss">
    .flatlab-index {
        height: 100%;
        width: 100%;
        background: url(./image/login-bg.jpg) no-repeat;
        background-size: cover;
        color: #797979;
        a {
            text-decoration: none;
        }
        a:hover {
            text-decoration: none;
        }
        .mask-bg {
            position: fixed;
            z-index: 666;
            left: 0;
            top: 0;
            bottom: 0;
            right: 0;
            background: rgba(0, 0, 0, 0.5);
            display: none;
        }
        .header {
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            z-index: 999;
            background: rgba(255, 255, 255, 0.6) !important;
            min-height: 60px;
            padding: 0 15px;
            .sidebar-toggle-box {
                float: left;
                padding-right: 15px;
                margin-top: 20px;
                > i {
                    display: inline-block;
                    font-size: 20px;
                }
            }
            a.logo {
                font-size: 21px;
                color: #2e2e2e;
                float: left;
                margin-top: 15px;
                text-transform: uppercase;
            }
            .top-nav {
                margin-top: 7px;
                .nav {
                    padding-left: 0;
                    margin-bottom: 0;
                    list-style: none;
                }
                .pull-right {
                    float: right !important;
                }
                .nav > li {
                    position: relative;
                    display: block;
                    float: left;
                    margin-left: 10px;
                }
                .nav > li a {
                    position: relative;
                    display: block;
                    color: #666666;
                    font-size: 16px;
                    border: 1px solid #f0f0f8 !important;
                    border-radius: 4px;
                    padding: 6px;
                    background: none;
                    margin-right: 0;
                }
                .nav > li a > img {
                    max-width: none;
                    margin-right: 8px;
                    border: 1px solid #dededc;
                    border-radius: 5px;
                }
                img {
                    vertical-align: middle;
                }
                .top-nav .username {
                    font-size: 13px;
                    color: #555555;
                }
                .caret {
                    display: inline-block;
                    width: 0;
                    height: 0;
                    margin-left: 8px;
                    vertical-align: middle;
                    border-top: 4px solid #4c4c4c;
                    border-right: 4px solid transparent;
                    border-bottom: 0 dotted;
                    border-left: 4px solid transparent;
                    content: "";
                }
            }
        }
        .sidebar {
            top: 70px;
            width: 180px;
            max-width: 0px;
            height: calc(100% - 90px);
            position: fixed;
            z-index: 888;
            background: rgba(42, 53, 66, 0.63);
            overflow-x: hidden;
            overflow-y: scroll;
            outline: none;
            margin-left: 0px;
            transition: max-width 200ms ease-in-out;
            ul.sidebar-menu,
            ul.sidebar-menu li ul.sub {
                margin: -2px 0 0;
                padding: 0;
            }
            ul.sidebar-menu {
                margin-top: 10px;
            }
            ul.sidebar-menu li {
                line-height: 20px !important;
                margin-bottom: 5px;
                margin-left: 10px;
                margin-right: 10px;
            }
            ul.sidebar-menu li a {
                color: #8b9199;
                text-decoration: none;
                display: block;
                padding: 15px 0 15px 10px;
                font-size: 12px;
                outline: none;
                transition: all 0.3s ease;
            }
            ul.sidebar-menu li.active a,
            ul.sidebar-menu li a:hover,
            ul.sidebar-menu li a:focus {
                background: rgb(53, 64, 77);
                color: #fff;
                display: block;
                border-radius: 4px;
                transition: all 0.3s ease;
            }
            ul.sidebar-menu li a i {
                font-size: 15px;
                padding-right: 6px;
            }
            ul.sidebar-menu li.active a i {
                color: #ff6c60;
            }
            ul.sidebar-menu li a span {
                display: inline-block;
            }
            ul > li > a .arrow {
                float: right;
                margin-right: 10px;
                margin-top: 6px;
                font-size: 11px;
            }
            ul > li.active > a .arrow,
            ul > li > a:hover .arrow,
            ul > li > a:focus .arrow {
                transform: rotate(90deg);
            }
            ul > li > ul.sub {
                display: none;
            }
            ul > li.active > ul.sub,
            ul > li > ul.sub > li > a {
                display: block;
            }
            ul.sidebar-menu li ul.sub li {
                background: rgb(53, 64, 77);
                margin-bottom: 0;
                margin-left: 0;
                margin-right: 0;
            }
            ul.sidebar-menu li ul.sub li a {
                font-size: 12px;
                padding: 0 0 0 32px;
                line-height: 35px;
                height: 35px;
                transition: all 0.3s ease;
                color: #737b83;
            }
            ul.sidebar-menu li ul.sub li a.router-link-active {
                color: #ffffff;
            }
        }
        .main-content {
            margin-left: 20px;
            padding-top: 80px;
            transition: margin-left 200ms ease-in-out;

            .wrapper {
                width: calc(100% - 20px);
                padding: 15px;
                height: calc(100vh - 100px);
                background: #ffffff;
                border-radius: 2px;
                .iframe {
                    width: calc(100% + 30px);
                    height: calc(100% - 5px);
                    border: none;
                    margin-left: -15px;
                }
            }
        }
        &.open-sidebar {
            .sidebar {
                max-width: 180px;
            }
            .main-content {
                margin-left: 200px;
            }
        }
    }

    @media screen and (max-width: 768px) {
        .open-sidebar {
            .mask-bg {
                display: block;
            }
        }
    }

    .popperLogout {
        padding: 0px;
        margin-right: 8px;
        border: none;

        .logout {
        }
        .logout > li {
            float: left;
            text-align: center;
            width: 33.3%;
        }
        .logout > li > a {
            clear: both;
            font-weight: normal;
            line-height: 1.428571429;
            white-space: nowrap;
            font-size: 12px;
            list-style: none;
            padding: 15px 10px !important;
            width: 100%;
            display: inline-block;

            color: #a4abbb;
            border-bottom: none !important;

            i {
                display: block;
                font-size: 17px;
            }
        }
        .logout > li > a:hover {
            background: none !important;
            color: #50c8ea;
            text-decoration: none;
        }
        .logout > li:last-child {
            text-align: center;
            width: 100%;
            background: #a9d96c;
            border-radius: 0 0 3px 3px;
        }
        .logout > li:last-child > a {
            color: #fff;
            border-bottom: none !important;
            text-transform: uppercase;
        }
    }
    /* 滚动条样式 */
    .scroll-scrollbar-style {
        overflow-x: hidden;
        overflow-y: scroll !important;
        &::-webkit-scrollbar {
            width: 2px;
            height: 2px;
        }
        &::-webkit-scrollbar-track {
            background: rgba(53, 56, 64, 0.63);
            border-radius: 2px;
        }
        &::-webkit-scrollbar-thumb {
            cursor: pointer;
            background: rgba(53, 56, 64, 0.63);
            border-radius: 1px;
        }
        &:hover::-webkit-scrollbar-thumb {
            background: #ff6c60;
            border-radius: 1px;
        }
        &::-webkit-scrollbar-thumb:hover {
            background: #999;
        }
        &::-webkit-scrollbar-corner {
            background: rgba(53, 56, 64, 0.63);
        }
    }
</style>
<script>
    import config from "@/config";
    import { logout } from "@/utils/utils";

    import menu from "@/views/admin/sidebar";
    import CollapseTransition from "./collapseTransition";

    function getWindowWidth() {
        //函数：获取尺寸
        var winWidth = 0;
        //获取窗口宽度
        if (window.innerWidth) winWidth = window.innerWidth;
        else if (document.body && document.body.clientWidth) winWidth = document.body.clientWidth;

        //通过深入Document内部对body进行检测，获取窗口大小
        if (document.documentElement && document.documentElement.clientWidth) {
            winWidth = document.documentElement.clientWidth;
        }
        return winWidth;
    }

    export default {
        name: "flatlab-index",
        components: { CollapseTransition },
        data() {
            return {
                config,
                active: 0,
                openSidebar: true,
                menus: [],
            };
        },
        watch: {},
        computed: {},
        methods: {
            isCloseSidebar() {
                var windowWidth = getWindowWidth();
                if (windowWidth <= 768) {
                    this.openSidebar = false;
                }
            },
            logout,
        },
        created() {
            var windowWidth = getWindowWidth();
            this.openSidebar = windowWidth >= 768;
        },
        mounted() {
            menu.getMenus().then((res) => {
                this.menus = res;
                var menus = this.menus;
                var active = -1;
                var path = this.$route.path;

                for (var i in menus) {
                    var ci = menus[i];
                    for (var j in ci.children) {
                        var v = ci.children[j];
                        if (v.to == path) {
                            active = i;
                            break;
                        }
                    }
                    if (active != -1) break;
                }
                if (active != -1) {
                    this.active = active;
                }
            });
        },
        destroyed() {},
    };
</script>
