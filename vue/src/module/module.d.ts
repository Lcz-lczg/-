
declare interface EModuleForm<T>{
    form:T;
}

declare interface EModuleReadForm<T,R>{
    form:T;
    readMap:R;
}



// "管理员" 模块结构。
declare interface EAdmins {
    // 帐号 。
    username?:string;
    // 密码 。
    pwd?:string;
        
}

declare type EAdminsForm = EModuleForm<EAdmins>;

// "观众" 模块结构。
declare interface EGuanzhong {
    // 用户名 。
    yonghuming?:string;
    // 密码 。
    mima?:string;
    // 姓名 。
    xingming?:string;
    // 性别 。
    // 可选项:男,女。
    xingbie?:string;
    // 手机 。
    shouji?:string;
    // 邮箱 。
    youxiang?:string;
    // 头像 。
    touxiang?:string;
    issh?:string;
    
}

declare type EGuanzhongForm = EModuleForm<EGuanzhong>;

// "友情链接" 模块结构。
declare interface EYouqinglianjie {
    // 网站名称 。
    wangzhanmingcheng?:string;
    // 网址 。
    wangzhi?:string;
        
}

declare type EYouqinglianjieForm = EModuleForm<EYouqinglianjie>;

// "轮播图" 模块结构。
declare interface ELunbotu {
    // 标题 。
    title?:string;
    // 图片 。
    image?:string;
    // 连接地址 。
    url?:string;
        
}

declare type ELunbotuForm = EModuleForm<ELunbotu>;

// "评论" 模块结构。
declare interface EPinglun {
    // 表 。
    biao?:string;
    // 表id 。
    biaoid?:number;
    // 标题 。
    biaoti?:string;
    // 评分 。
    pingfen?:number;
    // 评论内容 。
    pinglunneirong?:string;
    // 姓名 。
    xingming?:string;
    // 头像 。
    touxiang?:string;
    // 评论人 。
    pinglunren?:string;
    // 评论时间 。
    addtime?:string;
            // 评论回复的数量。
    pinglunhuifuCount?:number;

}

declare type EPinglunForm = EModuleForm<EPinglun>;

// "评论回复" 模块结构。
declare interface EPinglunhuifu {
    // 评论id 。
    pinglunid?:number;
    // 表 。
    biao?:string;
    // 表id 。
    biaoid?:number;
    // 标题 。
    biaoti?:string;
    // 评论内容 。
    pinglunneirong?:string;
    // 评论人 。
    pinglunren?:string;
    // 回复内容 。
    huifuneirong?:string;
    // 回复人 。
    huifuren?:string;
    // 回复时间 。
    addtime?:string;
        
}

declare type EPinglunhuifuForm = EModuleReadForm<EPinglunhuifu,EPinglun>;

// "点赞" 模块结构。
declare interface EDianzan {
    // 用户 。
    username?:string;
    // 关联表id 。
    biaoid?:number;
    // 关联表 。
    biao?:string;
    // 标题 。
    biaoti?:string;
    // 点赞时间 。
    addtime?:string;
        
}

declare type EDianzanForm = EModuleForm<EDianzan>;

// "教练" 模块结构。
declare interface EJiaolian {
    // 工号 。
    gonghao?:string;
    // 密码 。
    mima?:string;
    // 职称 。
    zhicheng?:string;
    // 姓名 。
    xingming?:string;
    // 性别 。
    // 可选项:男,女。
    xingbie?:string;
    // 资质证书 。
    zizhizhengshu?:string;
    // 执教经验 。
    zhijiaojingyan?:string;
    // 联系电话 。
    lianxidianhua?:string;
    // 头像 。
    touxiang?:string;
    issh?:string;
    
}

declare type EJiaolianForm = EModuleForm<EJiaolian>;

// "新闻分类" 模块结构。
declare interface EXinwenfenlei {
    // 分类名称 。
    fenleimingcheng?:string;
        
}

declare type EXinwenfenleiForm = EModuleForm<EXinwenfenlei>;

// "新闻公告" 模块结构。
declare interface EXinwengonggao {
    // 标题 。
    biaoti?:string;
    // 分类 。
    fenlei?:number;
    // 图片 。
    tupian?:string;
    // 添加人 。
    tianjiaren?:string;
    // 点击量 。
    dianjiliang?:number;
    // 内容 。
    neirong?:string;
        
}

declare type EXinwengonggaoForm = EModuleForm<EXinwengonggao>;

// "球员信息" 模块结构。
declare interface EQiuyuanxinxi {
    // 账号 。
    zhanghao?:string;
    // 密码 。
    mima?:string;
    // 姓名 。
    xingming?:string;
    // 性别 。
    // 可选项:男,女。
    xingbie?:string;
    // 联系电话 。
    lianxidianhua?:string;
    // 年龄 。
    nianling?:number;
    // 身高 。
    // 单位：/cm。
    shengao?:string;
    // 体重 。
    // 单位：/kg。
    tizhong?:string;
    // 所属球队 。
    suoshuqiudui?:string;
    // 球员位置 。
    qiuyuanweizhi?:string;
    // 球衣号 。
    qiuyihao?:string;
    // 个人简介 。
    gerenjianjie?:string;
    // 头像 。
    touxiang?:string;
        
}

declare type EQiuyuanxinxiForm = EModuleForm<EQiuyuanxinxi>;

// "球队" 模块结构。
declare interface EQiudui {
    // 球队编号 。
    qiuduibianhao?:string;
    // 球队名称 。
    qiuduimingcheng?:string;
    // 球队图片 。
    qiuduitupian?:string;
    // 球队队员 。
    qiuduiduiyuan?:string;
    // 实力等级 。
    shilidengji?:number;
    // 球队积分 。
    qiuduijifen?:number;
    // 球队详情 。
    qiuduixiangqing?:string;
    // 教练姓名 。
    jiaolianxingming?:string;
    // 球队教练 。
    qiuduijiaolian?:string;
    // 成立时间 。
    chenglishijian?:string;
        
}

declare type EQiuduiForm = EModuleForm<EQiudui>;

// "比赛类型" 模块结构。
declare interface EBisaileixing {
    // 类型名称 。
    leixingmingcheng?:string;
        
}

declare type EBisaileixingForm = EModuleForm<EBisaileixing>;

// "比赛信息" 模块结构。
declare interface EBisaixinxi {
    // 比赛编号 。
    bisaibianhao?:string;
    // 比赛标题 。
    bisaibiaoti?:string;
    // 比赛类型 。
    bisaileixing?:number;
    // 比赛模式 。
    bisaimoshi?:string;
    // 比赛图片 。
    bisaitupian?:string;
    // 参加球队 。
    canjiaqiudui?:number;
    // 比赛场地 。
    bisaichangdi?:string;
    // 比赛内容 。
    bisaineirong?:string;
    // 添加人 。
    tianjiaren?:string;
            // 比赛报名的数量。
    bisaibaomingCount?:number;
    // 赛程表的数量。
    saichengbiaoCount?:number;

}

declare type EBisaixinxiForm = EModuleForm<EBisaixinxi>;

// "比赛报名" 模块结构。
declare interface EBisaibaoming {
    // 比赛信息id 。
    bisaixinxiid?:number;
    // 比赛编号 。
    bisaibianhao?:string;
    // 比赛标题 。
    bisaibiaoti?:string;
    // 比赛类型 。
    bisaileixing?:number;
    // 报名编号 。
    baomingbianhao?:string;
    // 报名球队 。
    baomingqiudui?:number;
    // 球队编号 。
    qiuduibianhao?:string;
    // 球队名称 。
    qiuduimingcheng?:string;
    // 球队队员 。
    qiuduiduiyuan?:string;
    // 教练姓名 。
    jiaolianxingming?:string;
    // 球队教练 。
    qiuduijiaolian?:string;
    // 联系方式 。
    lianxifangshi?:string;
    // 备注 。
    beizhu?:string;
    // 状态 。
    // 可选项:已报名,已编排。
    zhuangtai?:"已报名"|"已编排";
    // 添加时间 。
    addtime?:string;
        
}

declare type EBisaibaomingForm = EModuleReadForm<EBisaibaoming,EBisaixinxi>;

// "赛程表" 模块结构。
declare interface ESaichengbiao {
    // 比赛信息id 。
    bisaixinxiid?:number;
    // 比赛编号 。
    bisaibianhao?:string;
    // 比赛标题 。
    bisaibiaoti?:string;
    // 比赛类型 。
    bisaileixing?:number;
    // 球队A 。
    qiuduia?:number;
    // VS 。
    vs?:string;
    // 球队B 。
    qiuduib?:number;
    // 比分 。
    bifen?:string;
    // 状态 。
    // 可选项:待开始,进行中,已结束。
    zhuangtai?:"待开始"|"进行中"|"已结束";
    // 比赛内容 。
    bisaineirong?:string;
    // 添加时间 。
    addtime?:string;
            // 比赛完成的数量。
    bisaiwanchengCount?:number;

}

declare type ESaichengbiaoForm = EModuleReadForm<ESaichengbiao,EBisaixinxi>;

// "比赛完成" 模块结构。
declare interface EBisaiwancheng {
    // 赛程表id 。
    saichengbiaoid?:number;
    // 比赛编号 。
    bisaibianhao?:string;
    // 比赛标题 。
    bisaibiaoti?:string;
    // 比赛类型 。
    bisaileixing?:number;
    // 球队A 。
    qiuduia?:number;
    // 球队B 。
    qiuduib?:number;
    // 完成编号 。
    wanchengbianhao?:string;
    // 胜利球队 。
    shengliqiudui?:number;
    // 完成内容 。
    wanchengneirong?:string;
    // 添加时间 。
    addtime?:string;
        
}

declare type EBisaiwanchengForm = EModuleReadForm<EBisaiwancheng,ESaichengbiao>;


// ajax 返回数据的结构
declare interface EResponseData<T>{
    // 为0 表示成功，其他表示错误码
    code:number;
    // 错误信息
    msg:string;
    // 数据
    data:T;
}
