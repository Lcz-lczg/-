<template>
    <div class="views-xinwengonggao-web-detail">
        <div>
            <e-container>
                <div class="title-modelbox-widget1">
                    <h3 class="section-title">详情</h3>
                    <div class="sidebar-widget-body">
                        <div class="news-info">
                            <h3 class="title" v-text="map.biaoti"></h3>
                            <div class="descount">
                                <span class="title">
                                    分类：
                                    <e-select-view module="xinwenfenlei" :value="map.fenlei" select="id" show="fenleimingcheng"></e-select-view>
                                </span>
                                <span class="title">
                                    点击量：
                                    {{ map.dianjiliang }}
                                </span>
                                <span class="title">
                                    添加人：
                                    {{ map.tianjiaren }}
                                </span>

                                <e-collect module="dianzan" :form="{ biaoid: 'biaoid', biao: 'biao', biaoti: 'biaoti' }" :biaoid="$route.query.id" biao="xinwengonggao" :biaoti="map.biaoti">
                                    <template #icon="{ isCollect }">
                                        <i :class="is_dianzan ? 'fa fa-thumbs-up' : 'fa fa-thumbs-o-up'"></i>
                                    </template>
                                    <template #default="{ isCollect }">
                                        {{ isCollect ? "已点赞" : "赞" }}
                                    </template>
                                </e-collect>
                            </div>
                            <div class="content" v-html="map.neirong"></div>
                        </div>
                    </div>
                    <!-- /.sidebar-widget-body -->
                </div>
            </e-container>
        </div>
    </div>
</template>

<script setup>
    import http from "@/utils/ajax/http";
    import DB from "@/utils/db";

    import { ref, reactive, watch, computed } from "vue";
    import { useRoute } from "vue-router";
    import { session } from "@/utils/utils";
    import { extend } from "@/utils/extend";
    import { useXinwengonggaoFindById, canXinwengonggaoFindById } from "@/module";

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
    const loadWebFind = (id) => {
        http.post("/api/xinwengonggao/detailWeb", { id }).then((res) => {
            console.log(res.data);
        });
    };
    watch(() => props.id, loadWebFind, { immediate: true });

    /**
     * 获取详情页面的一行数据,当url参数id变更时，当url参数id变更时，自动更新map中的数据
     * @type {EXinwengonggao}
     */
    const map = useXinwengonggaoFindById(props.id);
    watch(
        () => props.id,
        (id) => {
            canXinwengonggaoFindById(id).then((res) => {
                extend(map, res);
            });
        }
    );
    // end 获取详情页面的一行数据
</script>

<style scoped lang="scss">
    .views-xinwengonggao-web-detail {
    }
</style>
