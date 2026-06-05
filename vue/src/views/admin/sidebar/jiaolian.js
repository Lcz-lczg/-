export default [
    {
        label: "球队管理",
        to: "",
        children: [
            {
                label: "球队添加",
                to: { path: "/admin/qiudui/add" },
            },
            {
                label: "球队查询",
                to: { path: "/admin/qiudui/qiuduijiaolian" },
            },
        ],
    },
    {
        label: "报名赛程",
        to: "",
        children: [
            {
                label: "报名查询",
                to: { path: "/admin/bisaibaoming/qiuduijiaolian" },
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
                to: { path: "/admin/jiaolian/updtself" },
            },
            {
                label: "修改密码",
                to: { path: "/admin/mod" },
            },
        ],
    },
];
