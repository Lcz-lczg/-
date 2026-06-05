<template>
    <div class="login-page">
        <div class="login-container">
            <div class="login-card">
                <div class="login-header">
                    <div class="logo-box">
                        <span class="logo-icon">🏀</span>
                        <h1 class="logo-title">{{ config.title }}</h1>
                    </div>
                    <p class="welcome-text">欢迎登录系统</p>
                </div>

                <form class="login-form" @submit.prevent="login">
                    <div class="form-group">
                        <label>
                            <i class="el-icon-user"></i>
                            <span>账号</span>
                        </label>
                        <input 
                            type="text" 
                            v-model="form.username" 
                            placeholder="请输入账号"
                            autocomplete="off"
                        />
                    </div>

                    <div class="form-group">
                        <label>
                            <i class="el-icon-lock"></i>
                            <span>密码</span>
                        </label>
                        <input 
                            type="password" 
                            v-model="form.pwd" 
                            placeholder="请输入密码"
                        />
                    </div>

                    <div class="form-group">
                        <label>
                            <i class="el-icon-key"></i>
                            <span>验证码</span>
                        </label>
                        <div class="captcha-box">
                            <input 
                                type="text" 
                                v-model="form.pagerandom" 
                                placeholder="验证码"
                                autocomplete="off"
                            />
                            <img 
                                :src="captchUrl" 
                                @click="loadCaptch" 
                                class="captcha-img"
                                title="点击刷新验证码"
                            />
                        </div>
                    </div>

                    <div class="form-group">
                        <label>
                            <i class="el-icon-connection"></i>
                            <span>角色</span>
                        </label>
                        <select v-model="form.cx">
                            <option :value="r" :key="r" v-for="r in rules">{{ r }}</option>
                        </select>
                    </div>

                    <button type="submit" class="login-button" :disabled="loading">
                        <span v-if="!loading">
                            <i class="el-icon-check"></i> 登录系统
                        </span>
                        <span v-else>
                            <i class="el-icon-loading"></i> 登录中...
                        </span>
                    </button>
                </form>

                <div class="login-footer">
                    <p>© 2026 校园篮球比赛赛程管理系统</p>
                </div>
            </div>

            <div class="decoration">
                <div class="basketball-court">
                    <div class="court-line line-1"></div>
                    <div class="court-line line-2"></div>
                    <div class="court-circle"></div>
                </div>
                <div class="floating-basketball">🏀</div>
            </div>
        </div>
    </div>
</template>

<script>
    import config from "@/config";
    import { canLogin } from "@/stores";
    import { captch } from "@/utils/utils";
    import { ElMessage } from "element-plus";

    export default {
        name: "login-page",
        data() {
            return {
                config,
                captchUrl: "",
                loading: false,
                form: {
                    username: "",
                    pwd: "",
                    cx: "管理员",
                    pagerandom: "",
                    a: "a",
                    captchToken: "",
                },
                rules: ["管理员", "观众", "教练", "球员信息"],
            };
        },
        methods: {
            login() {
                this.loading = true;
                canLogin(this.form)
                    .then((res) => {
                        this.loading = false;
                        if (res.code == 0) {
                            ElMessage.success("登录成功");
                            var redirect = this.$route.query.redirect;
                            if (redirect) {
                                this.$router.replace(redirect);
                            } else {
                                this.$router.replace("/admin/sy");
                            }
                        } else {
                            ElMessage.error(res.msg);
                        }
                        if (res.code == 20) {
                            this.loadCaptch();
                        }
                    })
                    .catch((err) => {
                        this.loading = false;
                        ElMessage.error(err.message);
                    });
            },
            loadCaptch() {
                captch().then((res) => {
                    this.form.captchToken = res.token;
                    this.captchUrl = res.url;
                });
            },
        },
        created() {
            this.form.cx = this.rules[0];
            this.loadCaptch();
        },
    };
</script>

<style lang="scss" scoped>
    .login-page {
        min-height: 100vh;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 40px 20px;
        position: relative;
        overflow: hidden;

        &::before {
            content: "";
            position: absolute;
            top: -50%;
            right: -20%;
            width: 800px;
            height: 800px;
            background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
            border-radius: 50%;
            animation: float 6s ease-in-out infinite;
        }

        &::after {
            content: "";
            position: absolute;
            bottom: -30%;
            left: -10%;
            width: 600px;
            height: 600px;
            background: radial-gradient(circle, rgba(255,255,255,0.08) 0%, transparent 70%);
            border-radius: 50%;
            animation: float 8s ease-in-out infinite reverse;
        }
    }

    @keyframes float {
        0%, 100% {
            transform: translateY(0) rotate(0deg);
        }
        50% {
            transform: translateY(-30px) rotate(5deg);
        }
    }

    .login-container {
        display: flex;
        gap: 60px;
        align-items: center;
        max-width: 1000px;
        width: 100%;
        position: relative;
        z-index: 10;
    }

    .login-card {
        flex: 1;
        max-width: 450px;
        background: white;
        border-radius: 24px;
        padding: 50px 45px;
        box-shadow: 0 25px 80px rgba(0,0,0,0.2);
        animation: slideUp 0.6s ease-out;

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login-header {
            text-align: center;
            margin-bottom: 40px;

            .logo-box {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 15px;
                margin-bottom: 20px;

                .logo-icon {
                    font-size: 48px;
                    animation: bounce 2s ease infinite;
                }

                @keyframes bounce {
                    0%, 100% {
                        transform: translateY(0);
                    }
                    50% {
                        transform: translateY(-10px);
                    }
                }

                .logo-title {
                    font-size: 24px;
                    font-weight: bold;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    -webkit-background-clip: text;
                    -webkit-text-fill-color: transparent;
                    background-clip: text;
                    margin: 0;
                }
            }

            .welcome-text {
                font-size: 16px;
                color: #64748b;
                margin: 0;
            }
        }

        .login-form {
            .form-group {
                margin-bottom: 24px;

                label {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    font-size: 14px;
                    color: #475569;
                    margin-bottom: 10px;
                    font-weight: 600;

                    i {
                        color: #667eea;
                        font-size: 16px;
                    }
                }

                input, select {
                    width: 100%;
                    padding: 14px 18px;
                    border: 2px solid #e2e8f0;
                    border-radius: 14px;
                    font-size: 15px;
                    transition: all 0.3s;
                    box-sizing: border-box;
                    background: #f8fafc;

                    &:focus {
                        border-color: #667eea;
                        background: white;
                        outline: none;
                        box-shadow: 0 0 0 4px rgba(102,126,234,0.1);
                    }

                    &::placeholder {
                        color: #94a3b8;
                    }
                }

                select {
                    cursor: pointer;
                    appearance: none;
                    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%2364748b' d='M6 8L1 3h10z'/%3E%3C/svg%3E");
                    background-repeat: no-repeat;
                    background-position: right 16px center;
                    padding-right: 40px;
                }

                .captcha-box {
                    display: flex;
                    gap: 12px;

                    input {
                        flex: 1;
                    }

                    .captcha-img {
                        width: 120px;
                        height: 48px;
                        border-radius: 10px;
                        cursor: pointer;
                        border: 2px solid #e2e8f0;
                        transition: all 0.3s;

                        &:hover {
                            opacity: 0.8;
                            transform: scale(1.02);
                        }
                    }
                }
            }

            .login-button {
                width: 100%;
                padding: 16px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border: none;
                border-radius: 14px;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px;
                margin-top: 10px;

                &:hover:not(:disabled) {
                    transform: translateY(-3px);
                    box-shadow: 0 10px 30px rgba(102,126,234,0.4);
                }

                &:active:not(:disabled) {
                    transform: translateY(-1px);
                }

                &:disabled {
                    opacity: 0.7;
                    cursor: not-allowed;
                }

                i {
                    font-size: 18px;
                }
            }
        }

        .login-footer {
            text-align: center;
            margin-top: 30px;
            padding-top: 25px;
            border-top: 1px solid #e2e8f0;

            p {
                font-size: 13px;
                color: #94a3b8;
                margin: 0;
            }
        }
    }

    .decoration {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;
        min-height: 500px;

        .basketball-court {
            width: 400px;
            height: 500px;
            border: 3px solid rgba(255,255,255,0.3);
            border-radius: 20px;
            position: relative;
            background: rgba(255,255,255,0.05);

            .court-line {
                position: absolute;
                background: rgba(255,255,255,0.3);

                &.line-1 {
                    width: 100%;
                    height: 3px;
                    top: 50%;
                    left: 0;
                    transform: translateY(-50%);
                }

                &.line-2 {
                    width: 3px;
                    height: 100%;
                    left: 50%;
                    top: 0;
                    transform: translateX(-50%);
                }
            }

            .court-circle {
                width: 120px;
                height: 120px;
                border: 3px solid rgba(255,255,255,0.3);
                border-radius: 50%;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
        }

        .floating-basketball {
            position: absolute;
            font-size: 80px;
            animation: floatBasketball 3s ease-in-out infinite;
            filter: drop-shadow(0 10px 20px rgba(0,0,0,0.3));
        }

        @keyframes floatBasketball {
            0%, 100% {
                transform: translateY(0) rotate(0deg);
            }
            50% {
                transform: translateY(-20px) rotate(10deg);
            }
        }
    }

    @media (max-width: 900px) {
        .login-container {
            flex-direction: column;
            gap: 40px;
        }

        .decoration {
            display: none;
        }

        .login-card {
            max-width: 100%;
            padding: 40px 30px;
        }
    }
</style>
