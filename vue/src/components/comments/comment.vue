<template>
    <div class="">
        <div class="" v-if="isComment">
            <el-form action="javascript:;" ref="form" :model="comment" method="post" status-icon validate-on-rule-change label-width="100px">
                <el-form-item label="评分" prop="pingfen">
                    <el-rate v-model="comment.pingfen"></el-rate>
                </el-form-item>
                <el-form-item label="评论内容" prop="pinglunneirong" required :rules="[{ required: true, message: '请输入评论内容' }]">
                    <el-input type="textarea" v-model="comment.pinglunneirong" placeholder="请输入评论内容"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitComment">发布评论</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div v-else>
            <slot name="notCommit"></slot>
        </div>
        <div style="margin-top: 10px">
            <div class="comment" v-for="(v, index) in lists" :key="index">
                <div class="common-avatar J_User">
                    <e-img v-if="getUserHeadimg(v)" :src="getUserHeadimg(v)" width="100%" height="100%"></e-img>
                    <img :src="defaultHeadimg" v-else width="100%" height="100%" />
                </div>
                <div class="comment-block">
                    <p class="comment-user">
                        <span class="comment-username J_User"> {{ getUserName(v) }} </span>
                        <span>
                            <el-rate v-model="v.pingfen" disabled show-score text-color="#ff9900" score-template="{value}"> </el-rate>
                        </span>
                        <span class="comment-time">{{ v.addtime ? v.addtime.substr(0, 10) : '' }}</span>
                        <router-link v-if="isReply" :to="'/pinglunhuifu/add?id=' + v.id">回复</router-link>
                    </p>
                    <div class="comment-content J_CommentContent">{{ v.pinglunneirong }}</div>
                    <reply-list v-if="isReply" :commitid="v.id"></reply-list>
                </div>
            </div>
        </div>
    </div>
</template>
<style type="text/scss" scoped lang="scss">
    .comment {
        position: relative;
        margin-top: 32px;
        padding: 0px 0px 32px 68px;
        border-bottom: 1px solid rgb(240, 240, 240);
        .common-avatar {
            display: block;
            position: absolute;
            left: 0px;
            top: 8px;
            z-index: 1;
            width: 50px;
            height: 50px;
            font-size: 0px;
            background: url("./asset/default.gif") no-repeat;
            border-radius: 50%;
            background-size: cover;
            overflow: hidden;
        }
        .common-avatar img {
            width: 100%;
            height: 100%;
        }
        .comment-user {
            height: 32px;
            line-height: 32px;
            font-size: 12px;
            > span {
                display: inline-block;
            }
            .comment-username {
                font-weight: 600;
                color: rgb(55, 155, 233);
            }
            .comment-time {
                color: rgb(153, 153, 153);
                display: inline-block;
                margin-left: 12px;
            }
        }
    }
    .comment-content {
        font-size: 14px;
        color: rgb(75, 75, 75);
        line-height: 24px;
        margin-top: 10px;
        overflow-wrap: break-word;
    }
    .comment-short .comment-content {
        margin-top: 2px;
    }
</style>
<script>
    import { extend, isFunction } from "@/utils/extend";
    import ReplyList from "@/components/comments/replyList";
    import defaultHeadimg from "./asset/default.gif";
    import { canPinglunInsert } from "@/module";

    export default {
        name: "e-comments",
        components: { ReplyList },
        data() {
            return {
                comment: {
                    pingfen: 5,
                    pinglunneirong: "",
                    biao: "",
                    biaoid: 0,
                    biaoti: "",
                },
                isLoading: false,
                defaultHeadimg: defaultHeadimg,
            };
        },
        props: {
            lists: Array,
            module: String,
            biaoti: String,
            headimg: String,
            name: String,
            isReply: Boolean,
            commitCallback: Function,
            isComment: {
                type: Boolean,
                default: true,
            },
        },
        watch: {},
        computed: {},
        methods: {
            // 获取用户头像
            getUserHeadimg(comment) {
                // 优先使用评论中的头像字段
                if (comment.touxiang) {
                    return comment.touxiang;
                }
                // 兼容旧数据：使用传入的 headimg 字段名
                if (this.headimg && comment[this.headimg]) {
                    return comment[this.headimg];
                }
                return null;
            },
            // 获取用户姓名
            getUserName(comment) {
                // 优先使用评论中的姓名字段
                if (comment.xingming) {
                    return comment.xingming;
                }
                // 使用评论人字段
                if (comment.pinglunren) {
                    return comment.pinglunren;
                }
                // 兼容旧数据：使用传入的 name 字段名
                if (this.name && comment[this.name]) {
                    return comment[this.name];
                }
                return '匿名用户';
            },
            submitComment() {
                this.$refs.form.validate().then(() => {
                    var comment = extend(true, {}, this.comment);
                    comment.biao = this.module;
                    comment.biaoti = this.biaoti;
                    comment.biaoid = this.$route.query.id;
                    
                    // 根据当前登录用户类型获取信息
                    const cx = this.$session.cx; // 用户类型：观众、教练、球员信息、管理员
                    comment.pinglunren = this.$session.username;
                    
                    // 根据不同用户类型获取姓名和头像
                    if (cx === '观众') {
                        comment.xingming = this.$session.xingming || this.$session.username;
                        comment.touxiang = this.$session.touxiang || '';
                    } else if (cx === '教练') {
                        comment.xingming = this.$session.xingming || this.$session.username;
                        comment.touxiang = this.$session.touxiang || '';
                    } else if (cx === '球员信息') {
                        comment.xingming = this.$session.xingming || this.$session.username;
                        comment.touxiang = this.$session.touxiang || '';
                    } else {
                        // 管理员或其他
                        comment.xingming = this.$session.username;
                        comment.touxiang = this.$session.touxiang || '';
                    }
                    
                    if (isFunction(this.commitCallback)) {
                        this.commitCallback(comment);
                    }

                    this.isLoading = true;
                    canPinglunInsert(comment)
                        .then((res) => {
                            this.isLoading = false;
                            if (res.code == 0) {
                                comment.addtime = new Date().toISOString().substr(0, 19);

                                this.$emit("success");
                                this.$message.success("评论成功");
                            } else {
                                this.$message.error(res.msg);
                            }
                        })
                        .catch((err) => {
                            this.isLoading = false;
                            this.$message.error(err.message);
                        });
                });
            },
        },
        created() {},
        mounted() {},
        destroyed() {},
    };
</script>
