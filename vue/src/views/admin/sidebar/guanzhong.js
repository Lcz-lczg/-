export default [
    {
        label: "评论管理",
        to: "",
        children: [
            {
                label: "评论查询",
                to: { path: "/admin/pinglun/pinglunren" },
            },
            {
                label: "ta回复我",
                to: { path: "/admin/pinglunhuifu/pinglunren" },
            },
            {
                label: "我回复ta",
                to: { path: "/admin/pinglunhuifu/huifuren" },
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
        label: "个人中心",
        to: "",
        children: [
            {
                label: "修改个人资料",
                to: { path: "/admin/guanzhong/updtself" },
            },
            {
                label: "修改密码",
                to: { path: "/admin/mod" },
            },
            {
                label: "我的点赞",
                to: { path: "/admin/dianzan/username" },
            },
        ],
    },
];
