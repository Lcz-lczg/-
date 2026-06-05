export default [
    {
        label: "账号管理",
        to: "",
        children: [
            {
                label: "管理员账号管理",
                to: { path: "/admin/admins" },
            },
            {
                label: "管理员账号添加",
                to: { path: "/admin/admins/add" },
            },
            {
                label: "密码修改",
                to: { path: "/admin/mod" },
            },
        ],
    },
    {
        label: "教练管理",
        to: "",
        children: [
            {
                label: "教练添加",
                to: { path: "/admin/jiaolian/add" },
            },
            {
                label: "教练查询",
                to: { path: "/admin/jiaolian" },
            },
        ],
    },
    {
        label: "球员信息管理",
        to: "",
        children: [
            {
                label: "球员信息添加",
                to: { path: "/admin/qiuyuanxinxi/add" },
            },
            {
                label: "球员信息查询",
                to: { path: "/admin/qiuyuanxinxi" },
            },
        ],
    },
    {
        label: "观众管理",
        to: "",
        children: [
            {
                label: "观众添加",
                to: { path: "/admin/guanzhong/add" },
            },
            {
                label: "观众查询",
                to: { path: "/admin/guanzhong" },
            },
        ],
    },
    {
        label: "球队管理",
        to: "",
        children: [


            {
                label: "球队查询",
                to: { path: "/admin/qiudui" },
            },
        ],
    },


    {
        label: "比赛信息管理",
        to: "",
        children: [
            {
                label: "比赛类型添加",
                to: { path: "/admin/bisaileixing/add" },
            },
            {
                label: "比赛类型查询",
                to: { path: "/admin/bisaileixing" },
            },
            {
                label: "比赛信息添加",
                to: { path: "/admin/bisaixinxi/add" },
            },
            {
                label: "比赛信息查询",
                to: { path: "/admin/bisaixinxi" },
            },
        ],
    },
    {
        label: "比赛报名管理",
        to: "",
        children: [
            {
                label: "比赛报名查询",
                to: { path: "/admin/bisaibaoming" },
            },
        ],
    },
    {
        label: "赛程表管理",
        to: "",
        children: [
            {
                label: "赛程自动编排",
                to: { path: "/admin/schedule/generate" },
            },
            {
                label: "赛程表查询",
                to: { path: "/admin/saichengbiao" },
            },
            {
                label: "比赛完成查询",
                to: { path: "/admin/bisaiwancheng" },
            },
        ],
    },
    {
        label: "新闻公告管理",
        to: "",
        children: [
            {
                label: "新闻分类添加",
                to: { path: "/admin/xinwenfenlei/add" },
            },
            {
                label: "新闻分类查询",
                to: { path: "/admin/xinwenfenlei" },
            },
            {
                label: "新闻公告添加",
                to: { path: "/admin/xinwengonggao/add" },
            },
            {
                label: "新闻公告查询",
                to: { path: "/admin/xinwengonggao" },
            },
        ],
    },




    {
        label: "统计报表",
        to: "",
        children: [

            {
                label: "球队统计",
                to: { path: "/admin/qiuduitongji" },
            },


        ],
    },








    {
        label: "系统管理",
        to: "",
        children: [
            {
                label: "友情链接添加",
                to: { path: "/admin/youqinglianjie/add" },
            },
            {
                label: "友情链接查询",
                to: { path: "/admin/youqinglianjie" },
            },
            {
                label: "轮播图添加",
                to: { path: "/admin/lunbotu/add" },
            },
            {
                label: "轮播图查询",
                to: { path: "/admin/lunbotu" },
            },
            {
                label: "评论管理",
                to: { path: "/admin/pinglun" },
            },
            {
                label: "评论回复查询",
                to: { path: "/admin/pinglunhuifu" },
            },
            {
                label: "数据库备份",
                to: { path: "/admin/backup" },
            },
        ],
    },
];
