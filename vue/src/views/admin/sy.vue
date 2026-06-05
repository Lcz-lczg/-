<template>
    <div class="v-admin-sy">
        <div class="welcome-card">
            <div class="welcome-icon">
                <i class="el-icon-trophy"></i>
            </div>
            <h1 class="welcome-title">欢迎使用 {{ config.title }}</h1>
            <p class="welcome-subtitle">校园篮球比赛赛程管理系统</p>
            
            <div class="user-info">
                <div class="avatar">
                    <i class="el-icon-user"></i>
                </div>
                <div class="user-detail">
                    <div class="username">{{ $session.username }}</div>
                    <div class="role">{{ $session.cx }}</div>
                </div>
            </div>



        </div>
    </div>
</template>

<style type="text/scss" scoped lang="scss">
    .v-admin-sy {
        display: flex;
        align-items: center;
        justify-content: center;
        min-height: calc(100vh - 100px);
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        padding: 40px;

        .welcome-card {
            background: white;
            border-radius: 24px;
            padding: 60px;
            text-align: center;
            box-shadow: 0 20px 60px rgba(0,0,0,0.2);
            max-width: 600px;
            width: 100%;
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

            .welcome-icon {
                width: 100px;
                height: 100px;
                margin: 0 auto 30px;
                background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                box-shadow: 0 10px 30px rgba(245,87,108,0.3);

                i {
                    font-size: 50px;
                    color: white;
                }
            }

            .welcome-title {
                font-size: 32px;
                color: #1e3a8a;
                margin: 0 0 15px 0;
                font-weight: bold;
            }

            .welcome-subtitle {
                font-size: 16px;
                color: #666;
                margin: 0 0 40px 0;
            }

            .user-info {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 20px;
                padding: 25px;
                background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
                border-radius: 16px;
                margin-bottom: 40px;

                .avatar {
                    width: 60px;
                    height: 60px;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    border-radius: 50%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    box-shadow: 0 4px 15px rgba(102,126,234,0.3);

                    i {
                        font-size: 28px;
                        color: white;
                    }
                }

                .user-detail {
                    text-align: left;

                    .username {
                        font-size: 20px;
                        color: #1e3a8a;
                        font-weight: bold;
                        margin-bottom: 5px;
                    }

                    .role {
                        font-size: 14px;
                        color: #666;
                        background: white;
                        padding: 4px 12px;
                        border-radius: 12px;
                        display: inline-block;
                    }
                }
            }

            .quick-links {
                display: grid;
                grid-template-columns: repeat(4, 1fr);
                gap: 15px;

                .quick-link {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    padding: 20px 15px;
                    background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
                    border-radius: 16px;
                    cursor: pointer;
                    transition: all 0.3s;

                    &:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 10px 25px rgba(0,0,0,0.1);
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

                        i {
                            color: white;
                        }

                        span {
                            color: white;
                        }
                    }

                    i {
                        font-size: 32px;
                        color: #667eea;
                        margin-bottom: 10px;
                        transition: color 0.3s;
                    }

                    span {
                        font-size: 14px;
                        color: #666;
                        font-weight: 500;
                        transition: color 0.3s;
                    }
                }
            }
        }
    }
</style>

<script setup>
    import config from "@/config";
</script>
