<template>
    <div class="views-jiaolian-web-detail">
        <div>
<e-container>
            <div class="title-modelbox-widget">
    <h3 class="section-title">
                详情    </h3>
    <div class="sidebar-widget-body">
        
<div class="">
    <div class="goods-info clearfix">
        <div class="gallery-list">
            <e-shangpinban :images="map."></e-shangpinban>
        </div>
        <div class="goods-right-content">
            <h3 class="title" v-text="map.xingming"></h3>
            <div class="descount">
                                    <div>
                        <span class="name">
                            职称：
                        </span>
                        <span class="val">
                            {{ map.zhicheng }}                        </span>
                    </div>
                                    <div>
                        <span class="name">
                            性别：
                        </span>
                        <span class="val">
                            {{  map.xingbie  }}                        </span>
                    </div>
                                    <div>
                        <span class="name">
                            联系电话：
                        </span>
                        <span class="val">
                            {{ map.lianxidianhua }}                        </span>
                    </div>
                            </div>

            
            



    




        </div>
    </div>
    <div class="goods-content" v-html="map.zhijiaojingyan">

    </div>
</div>
    </div>
    <!-- /.sidebar-widget-body -->
</div>
    

</e-container>
</div>    </div>
</template>

<script setup>
import http from '@/utils/ajax/http';
import DB from '@/utils/db';
import EShangpinban from '@/components/shangpin/shangpinban.vue';

import {ref,reactive,watch,computed} from 'vue';
import {useRoute} from 'vue-router';
import {session} from '@/utils/utils';
import {extend} from '@/utils/extend';
import {useJiaolianFindById,canJiaolianFindById} from '@/module';

const route = useRoute();
    const props = defineProps({
        id:{
            type:[Number,String],
        },
        isShowBtn:{
            type:Boolean,
            default:true
        }
    });


/**
 * 获取详情页面的一行数据,当url参数id变更时，当url参数id变更时，自动更新map中的数据
 * @type {EJiaolian}
 */
const map = useJiaolianFindById(props.id);
watch(()=>props.id , (id)=>{
    canJiaolianFindById(id).then(res=>{
        extend(map,res);
    });
});
// end 获取详情页面的一行数据

</script>



<style scoped lang="scss">
.views-jiaolian-web-detail{

}

</style>