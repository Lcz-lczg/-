import { session } from "@/utils/utils";

export default [
    {
        path: "backup",
        name: "backup",
        component: () => import("@/views/recover/backup.vue"),
        meta: { authLogin: true },
    },

    {
        path: "admins",
        name: "AdminadminsList",
        component: () => import("@/views/admins/list.vue"),
        meta: { title: "管理员列表", authLogin: true },
    },

    {
        path: "admins/add",
        name: "AdminadminsAdd",
        component: () => import("@/views/admins/add.vue"),
        meta: { title: "添加管理员", authLogin: true },
    },
    {
        path: "admins/updt",
        name: "AdminadminsUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/admins/updt.vue"),
        meta: { title: "编辑管理员", authLogin: true },
    },
    {
        path: "admins/updtself",
        name: "AdminadminsUpdtSelf",
        props: (route) => ({ id: session("id") }),
        component: () => import("@/views/admins/updtself.vue"),
        meta: { title: "更新个人资料", authLogin: true },
    },
    {
        path: "guanzhong",
        name: "AdminguanzhongList",
        component: () => import("@/views/guanzhong/list.vue"),
        meta: { title: "观众列表", authLogin: true },
    },

    {
        path: "guanzhong/add",
        name: "AdminguanzhongAdd",
        component: () => import("@/views/guanzhong/add.vue"),
        meta: { title: "添加观众", authLogin: true },
    },
    {
        path: "guanzhong/updt",
        name: "AdminguanzhongUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/guanzhong/updt.vue"),
        meta: { title: "编辑观众", authLogin: true },
    },
    {
        path: "guanzhong/updtself",
        name: "AdminguanzhongUpdtSelf",
        props: (route) => ({ id: session("id") }),
        component: () => import("@/views/guanzhong/updtself.vue"),
        meta: { title: "更新个人资料", authLogin: true },
    },
    {
        path: "youqinglianjie",
        name: "AdminyouqinglianjieList",
        component: () => import("@/views/youqinglianjie/list.vue"),
        meta: { title: "友情链接列表", authLogin: true },
    },

    {
        path: "youqinglianjie/add",
        name: "AdminyouqinglianjieAdd",
        component: () => import("@/views/youqinglianjie/add.vue"),
        meta: { title: "添加友情链接", authLogin: true },
    },
    {
        path: "youqinglianjie/updt",
        name: "AdminyouqinglianjieUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/youqinglianjie/updt.vue"),
        meta: { title: "编辑友情链接", authLogin: true },
    },
    {
        path: "lunbotu",
        name: "AdminlunbotuList",
        component: () => import("@/views/lunbotu/list.vue"),
        meta: { title: "轮播图列表", authLogin: true },
    },

    {
        path: "lunbotu/add",
        name: "AdminlunbotuAdd",
        component: () => import("@/views/lunbotu/add.vue"),
        meta: { title: "添加轮播图", authLogin: true },
    },
    {
        path: "lunbotu/updt",
        name: "AdminlunbotuUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/lunbotu/updt.vue"),
        meta: { title: "编辑轮播图", authLogin: true },
    },
    {
        path: "pinglun",
        name: "AdminpinglunList",
        component: () => import("@/views/pinglun/list.vue"),
        meta: { title: "评论列表", authLogin: true },
    },

    {
        path: "pinglun/pinglunren",
        name: "AdminpinglunListpinglunren",
        component: () => import("@/views/pinglun/pinglunren.vue"),
        meta: { title: "评论列表", authLogin: true },
    },

    {
        path: "pinglun/updt",
        name: "AdminpinglunUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/pinglun/updt.vue"),
        meta: { title: "编辑评论", authLogin: true },
    },
    {
        path: "pinglunhuifu",
        name: "AdminpinglunhuifuList",
        component: () => import("@/views/pinglunhuifu/list.vue"),
        meta: { title: "评论回复列表", authLogin: true },
    },

    {
        path: "pinglunhuifu/pinglunren",
        name: "AdminpinglunhuifuListpinglunren",
        component: () => import("@/views/pinglunhuifu/pinglunren.vue"),
        meta: { title: "评论回复列表", authLogin: true },
    },
    {
        path: "pinglunhuifu/huifuren",
        name: "AdminpinglunhuifuListhuifuren",
        component: () => import("@/views/pinglunhuifu/huifuren.vue"),
        meta: { title: "评论回复列表", authLogin: true },
    },

    {
        path: "pinglunhuifu/updt",
        name: "AdminpinglunhuifuUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/pinglunhuifu/updt.vue"),
        meta: { title: "编辑评论回复", authLogin: true },
    },
    {
        path: "dianzan",
        name: "AdmindianzanList",
        component: () => import("@/views/dianzan/list.vue"),
        meta: { title: "点赞列表", authLogin: true },
    },

    {
        path: "dianzan/username",
        name: "AdmindianzanListusername",
        component: () => import("@/views/dianzan/username.vue"),
        meta: { title: "点赞列表", authLogin: true },
    },

    {
        path: "jiaolian",
        name: "AdminjiaolianList",
        component: () => import("@/views/jiaolian/list.vue"),
        meta: { title: "教练列表", authLogin: true },
    },

    {
        path: "jiaolian/add",
        name: "AdminjiaolianAdd",
        component: () => import("@/views/jiaolian/add.vue"),
        meta: { title: "添加教练", authLogin: true },
    },
    {
        path: "jiaolian/updt",
        name: "AdminjiaolianUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/jiaolian/updt.vue"),
        meta: { title: "编辑教练", authLogin: true },
    },
    {
        path: "jiaolian/updtself",
        name: "AdminjiaolianUpdtSelf",
        props: (route) => ({ id: session("id") }),
        component: () => import("@/views/jiaolian/updtself.vue"),
        meta: { title: "更新个人资料", authLogin: true },
    },
    {
        path: "jiaolian/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminjiaolianDetail",
        component: () => import("@/views/jiaolian/detail.vue"),
        meta: { title: "教练详情", authLogin: true },
    },
    {
        path: "xinwenfenlei",
        name: "AdminxinwenfenleiList",
        component: () => import("@/views/xinwenfenlei/list.vue"),
        meta: { title: "新闻分类列表", authLogin: true },
    },

    {
        path: "xinwenfenlei/add",
        name: "AdminxinwenfenleiAdd",
        component: () => import("@/views/xinwenfenlei/add.vue"),
        meta: { title: "添加新闻分类", authLogin: true },
    },
    {
        path: "xinwenfenlei/updt",
        name: "AdminxinwenfenleiUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/xinwenfenlei/updt.vue"),
        meta: { title: "编辑新闻分类", authLogin: true },
    },
    {
        path: "xinwengonggao",
        name: "AdminxinwengonggaoList",
        component: () => import("@/views/xinwengonggao/list.vue"),
        meta: { title: "新闻公告列表", authLogin: true },
    },

    {
        path: "xinwengonggao/tianjiaren",
        name: "AdminxinwengonggaoListtianjiaren",
        component: () => import("@/views/xinwengonggao/tianjiaren.vue"),
        meta: { title: "新闻公告列表", authLogin: true },
    },

    {
        path: "xinwengonggao/add",
        name: "AdminxinwengonggaoAdd",
        component: () => import("@/views/xinwengonggao/add.vue"),
        meta: { title: "添加新闻公告", authLogin: true },
    },
    {
        path: "xinwengonggao/updt",
        name: "AdminxinwengonggaoUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/xinwengonggao/updt.vue"),
        meta: { title: "编辑新闻公告", authLogin: true },
    },
    {
        path: "xinwengonggao/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminxinwengonggaoDetail",
        component: () => import("@/views/xinwengonggao/detail.vue"),
        meta: { title: "新闻公告详情", authLogin: true },
    },
    {
        path: "qiuyuanxinxi",
        name: "AdminqiuyuanxinxiList",
        component: () => import("@/views/qiuyuanxinxi/list.vue"),
        meta: { title: "球员信息列表", authLogin: true },
    },

    {
        path: "qiuyuanxinxi/add",
        name: "AdminqiuyuanxinxiAdd",
        component: () => import("@/views/qiuyuanxinxi/add.vue"),
        meta: { title: "添加球员信息", authLogin: true },
    },
    {
        path: "qiuyuanxinxi/updt",
        name: "AdminqiuyuanxinxiUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/qiuyuanxinxi/updt.vue"),
        meta: { title: "编辑球员信息", authLogin: true },
    },
    {
        path: "qiuyuanxinxi/updtself",
        name: "AdminqiuyuanxinxiUpdtSelf",
        props: (route) => ({ id: session("id") }),
        component: () => import("@/views/qiuyuanxinxi/updtself.vue"),
        meta: { title: "更新个人资料", authLogin: true },
    },
    {
        path: "qiuyuanxinxi/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminqiuyuanxinxiDetail",
        component: () => import("@/views/qiuyuanxinxi/detail.vue"),
        meta: { title: "球员信息详情", authLogin: true },
    },
    {
        path: "qiudui",
        name: "AdminqiuduiList",
        component: () => import("@/views/qiudui/list.vue"),
        meta: { title: "球队列表", authLogin: true },
    },

    {
        path: "qiudui/qiuduijiaolian",
        name: "AdminqiuduiListqiuduijiaolian",
        component: () => import("@/views/qiudui/qiuduijiaolian.vue"),
        meta: { title: "球队列表", authLogin: true },
    },

    {
        path: "qiudui/add",
        name: "AdminqiuduiAdd",
        component: () => import("@/views/qiudui/add.vue"),
        meta: { title: "添加球队", authLogin: true },
    },
    {
        path: "qiudui/updt",
        name: "AdminqiuduiUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/qiudui/updt.vue"),
        meta: { title: "编辑球队", authLogin: true },
    },
    {
        path: "qiudui/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminqiuduiDetail",
        component: () => import("@/views/qiudui/detail.vue"),
        meta: { title: "球队详情", authLogin: true },
    },
    {
        path: "bisaileixing",
        name: "AdminbisaileixingList",
        component: () => import("@/views/bisaileixing/list.vue"),
        meta: { title: "比赛类型列表", authLogin: true },
    },

    {
        path: "bisaileixing/add",
        name: "AdminbisaileixingAdd",
        component: () => import("@/views/bisaileixing/add.vue"),
        meta: { title: "添加比赛类型", authLogin: true },
    },
    {
        path: "bisaileixing/updt",
        name: "AdminbisaileixingUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/bisaileixing/updt.vue"),
        meta: { title: "编辑比赛类型", authLogin: true },
    },
    {
        path: "bisaixinxi",
        name: "AdminbisaixinxiList",
        component: () => import("@/views/bisaixinxi/list.vue"),
        meta: { title: "比赛信息列表", authLogin: true },
    },

    {
        path: "bisaixinxi/tianjiaren",
        name: "AdminbisaixinxiListtianjiaren",
        component: () => import("@/views/bisaixinxi/tianjiaren.vue"),
        meta: { title: "比赛信息列表", authLogin: true },
    },

    {
        path: "bisaixinxi/add",
        name: "AdminbisaixinxiAdd",
        component: () => import("@/views/bisaixinxi/add.vue"),
        meta: { title: "添加比赛信息", authLogin: true },
    },
    {
        path: "bisaixinxi/updt",
        name: "AdminbisaixinxiUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/bisaixinxi/updt.vue"),
        meta: { title: "编辑比赛信息", authLogin: true },
    },
    {
        path: "bisaixinxi/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminbisaixinxiDetail",
        component: () => import("@/views/bisaixinxi/detail.vue"),
        meta: { title: "比赛信息详情", authLogin: true },
    },
    {
        path: "bisaibaoming",
        name: "AdminbisaibaomingList",
        component: () => import("@/views/bisaibaoming/list.vue"),
        meta: { title: "比赛报名列表", authLogin: true },
    },

    {
        path: "bisaibaoming/qiuduijiaolian",
        name: "AdminbisaibaomingListqiuduijiaolian",
        component: () => import("@/views/bisaibaoming/qiuduijiaolian.vue"),
        meta: { title: "比赛报名列表", authLogin: true },
    },

    {
        path: "bisaibaoming/add",
        name: "AdminbisaibaomingAdd",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/bisaibaoming/add.vue"),
        meta: { title: "添加比赛报名", authLogin: true },
    },
    {
        path: "bisaibaoming/updt",
        name: "AdminbisaibaomingUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/bisaibaoming/updt.vue"),
        meta: { title: "编辑比赛报名", authLogin: true },
    },
    {
        path: "bisaibaoming/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminbisaibaomingDetail",
        component: () => import("@/views/bisaibaoming/detail.vue"),
        meta: { title: "比赛报名详情", authLogin: true },
    },
    {
        path: "saichengbiao",
        name: "AdminsaichengbiaoList",
        component: () => import("@/views/saichengbiao/list.vue"),
        meta: { title: "赛程表列表", authLogin: true },
    },

    {
        path: "saichengbiao/add",
        name: "AdminsaichengbiaoAdd",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/saichengbiao/add.vue"),
        meta: { title: "添加赛程表", authLogin: true },
    },
    {
        path: "saichengbiao/updt",
        name: "AdminsaichengbiaoUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/saichengbiao/updt.vue"),
        meta: { title: "编辑赛程表", authLogin: true },
    },
    {
        path: "saichengbiao/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminsaichengbiaoDetail",
        component: () => import("@/views/saichengbiao/detail.vue"),
        meta: { title: "赛程表详情", authLogin: true },
    },
    {
        path: "bisaiwancheng",
        name: "AdminbisaiwanchengList",
        component: () => import("@/views/bisaiwancheng/list.vue"),
        meta: { title: "比赛完成列表", authLogin: true },
    },

    {
        path: "bisaiwancheng/add",
        name: "AdminbisaiwanchengAdd",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/bisaiwancheng/add.vue"),
        meta: { title: "添加比赛完成", authLogin: true },
    },
    {
        path: "bisaiwancheng/updt",
        name: "AdminbisaiwanchengUpdt",
        props: (route) => ({ id: route.query.id }),
        component: () => import("@/views/bisaiwancheng/updt.vue"),
        meta: { title: "编辑比赛完成", authLogin: true },
    },
    {
        path: "bisaiwancheng/detail",
        props: (route) => ({ id: route.query.id }),
        name: "AdminbisaiwanchengDetail",
        component: () => import("@/views/bisaiwancheng/detail.vue"),
        meta: { title: "比赛完成详情", authLogin: true },
    },

    {
        path: "saichengtongji",
        name: "Pagesaichengtongji",
        component: () => import("@/views/saichengtongji.vue"),
        meta: { title: "赛程统计", authLogin: true },
    },
    {
        path: "qiuduitongji",
        name: "Pageqiuduitongji",
        component: () => import("@/views/qiuduitongji.vue"),
        meta: { title: "球队统计", authLogin: true },
    },

    {
        path: "schedule/generate",
        name: "AdminScheduleGenerate",
        component: () => import("@/views/admin/schedule-generate.vue"),
        meta: { title: "赛程自动编排", authLogin: true },
    },
];
