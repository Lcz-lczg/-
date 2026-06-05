<template>
    <div class="recover-backup" v-loading="loading">
        <div class="mb10">
            <el-button @click="backup" type="primary">备份</el-button>
        </div>
        <el-table :data="list" style="width: 100%">
            <el-table-column prop="filename" label="备份文件" width="480"> </el-table-column>
            <el-table-column prop="filesize" label="大小" >
                <template #default="{ row }">
                    {{ byteToSize(row.filesize) }}
                </template>
            </el-table-column>
            <el-table-column prop="lastTime" label="备份日期" > </el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                    <el-button @click="restore(row)" size="mini">恢复备份</el-button>
                    <el-button @click="deleteBackup(row)" size="mini" type="danger">删除备份</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<style type="text/scss" lang="scss"></style>
<script>
    export default {
        name: "recover-backup",
        data() {
            return {
                list: [],
                loading: false,
            };
        },
        watch: {},
        computed: {},
        methods: {
            byteToSize(bytes) {
                var sizes = ["B", "KB", "MB", "GB", "TB"];
                if (bytes == 0) return "0 B";
                var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
                return Math.round(bytes / Math.pow(1024, i), 2) + " " + sizes[i];
            },
            loadList() {
                this.loading = true;
                this.$get("/api/recover/backuplist")
                    .then((res) => {
                        this.loading = false;
                        if (res.code === 0) {
                            this.list = res.data;
                        } else {
                            this.$message.error(res.msg);
                            this.$router.push("/");
                        }
                    })
                    .catch((err) => {
                        this.$message.error(err.message);
                        this.$router.push("/");
                    });
            },
            backup() {
                this.loading = true;
                this.$get("/api/recover/backup")
                    .then((res) => {
                        this.loading = false;
                        if (res.code === 0) {
                            this.list = res.data;
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                    .catch((err) => {
                        this.$message.error(err.message);
                        this.$router.push("/");
                    });
            },
            restore(row) {
                this.$confirm("确认恢复" + row.filename + "该备份？").then(() => {
                    this.loading = true;
                    var form = {};
                    form.filename = row.filename;
                    this.$get("/api/recover/restore", form)
                        .then((res) => {
                            this.loading = false;
                            if (res.code === 0) {
                                this.$message.success("恢复备份成功");
                            } else {
                                this.$message.error(res.msg);
                            }
                        })
                        .catch((err) => {
                            this.$message.error(err.message);
                            this.$router.push("/");
                        });
                });
            },
            deleteBackup(row) {
                this.$confirm("确认删除" + row.filename + "该备份？").then(() => {
                    this.loading = true;
                    var form = {};
                    form.filename = row.filename;
                    this.$get("/api/recover/backupdelete", form)
                        .then((res) => {
                            this.loading = false;
                            if (res.code === 0) {
                                this.$message.success("删除备份成功");
                                this.list = res.data;
                            } else {
                                this.$message.error(res.msg);
                            }
                        })
                        .catch((err) => {
                            this.$message.error(err.message);
                            this.$router.push("/");
                        });
                });
            },
        },
        created() {
            this.loadList();
        },
        mounted() {},
        destroyed() {},
    };
</script>
