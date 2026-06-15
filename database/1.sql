/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26)
 Source Host           : localhost:3306
 Source Schema         : springvue26519jsdxylqbsscglxt

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26)
 File Encoding         : 65001

 Date: 22/03/2026 21:34:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '帐号',
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES (1, 'admin', 'admin');

-- ----------------------------
-- Table structure for bisaibaoming
-- ----------------------------
DROP TABLE IF EXISTS `bisaibaoming`;
CREATE TABLE `bisaibaoming`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `bisaixinxiid` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '比赛信息id',
  `bisaibianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛编号',
  `bisaibiaoti` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛标题',
  `bisaileixing` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '比赛类型',
  `baomingbianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '报名编号',
  `baomingqiudui` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '报名球队ID',
  `qiuduibianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队编号',
  `qiuduimingcheng` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队名称',
  `qiuduiduiyuan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队队员',
  `jiaolianxingming` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '教练姓名',
  `qiuduijiaolian` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队教练',
  `lianxifangshi` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系方式',
  `beizhu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `zhuangtai` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '状态',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bisaixinxi_index`(`bisaixinxiid`) USING BTREE,
  INDEX `bisaileixing_index`(`bisaileixing`) USING BTREE,
  INDEX `bisaibaoming_baomingqiudui_index`(`baomingqiudui`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '比赛报名' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bisaibaoming
-- ----------------------------
INSERT INTO `bisaibaoming` VALUES (2, 3, 'B003', '全明星技巧挑战赛', 3, '032219069818', 2, 'T002', '猛虎队', '张三、李四、徐八、朱九、何十、高一、梁二、宋三', '王交', '888', '17845454455', '11', '已编排', '2026-03-22 19:06:38');
INSERT INTO `bisaibaoming` VALUES (3, 3, 'B003', '全明星技巧挑战赛', 3, '032219065407', 3, 'T003', '闪电队', '吴十、郑一、王二、刘三、陈四、杨五、黄六、林七', '王交', '888', '17845454455', '11', '已编排', '2026-03-22 19:06:43');
INSERT INTO `bisaibaoming` VALUES (4, 3, 'B003', '全明星技巧挑战赛', 3, '032219073542', 5, 'T005', '火箭队', '冯五、邓六、许七、曹八、彭九、曾十、萧一、田二', '王梅', '999', '17845454545', '1', '已报名', '2026-03-22 19:07:08');
INSERT INTO `bisaibaoming` VALUES (5, 4, 'B004', '新生杯篮球赛', 1, '032221267724', 2, 'T002', '猛虎队', '张三、李四、徐八、朱九、何十、高一、梁二、宋三', '王交', '888', '17845454455', '萨法阿', '已报名', '2026-03-22 21:26:35');
INSERT INTO `bisaibaoming` VALUES (6, 4, 'B004', '新生杯篮球赛', 1, '032221266102', 3, 'T003', '闪电队', '吴十、郑一、王二、刘三、陈四、杨五、黄六、林七', '王交', '888', '17845454455', '艾师傅阿方', '已报名', '2026-03-22 21:26:39');
INSERT INTO `bisaibaoming` VALUES (7, 4, 'B004', '新生杯篮球赛', 1, '032221263643', 5, 'T005', '火箭队', '冯五、邓六、许七、曹八、彭九、曾十、萧一、田二', '王梅', '999', '17845454545', '艾师傅sa', '已报名', '2026-03-22 21:26:54');

-- ----------------------------
-- Table structure for bisaileixing
-- ----------------------------
DROP TABLE IF EXISTS `bisaileixing`;
CREATE TABLE `bisaileixing`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `leixingmingcheng` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '比赛类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bisaileixing
-- ----------------------------
INSERT INTO `bisaileixing` VALUES (1, '5 人制篮球赛');
INSERT INTO `bisaileixing` VALUES (2, '3 人制篮球赛');
INSERT INTO `bisaileixing` VALUES (3, '技巧挑战赛');

-- ----------------------------
-- Table structure for bisaiwancheng
-- ----------------------------
DROP TABLE IF EXISTS `bisaiwancheng`;
CREATE TABLE `bisaiwancheng`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `saichengbiaoid` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '赛程表id',
  `bisaibianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛编号',
  `bisaibiaoti` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛标题',
  `bisaileixing` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '比赛类型',
  `qiuduia` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '球队A',
  `qiuduib` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '球队B',
  `wanchengbianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '完成编号',
  `shengliqiudui` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '胜利球队',
  `wanchengneirong` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '完成内容',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `saichengbiao_index`(`saichengbiaoid`) USING BTREE,
  INDEX `bisaileixing_index`(`bisaileixing`) USING BTREE,
  INDEX `qiuduia_index`(`qiuduia`) USING BTREE,
  INDEX `qiuduib_index`(`qiuduib`) USING BTREE,
  INDEX `shengliqiudui_index`(`shengliqiudui`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '比赛完成' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bisaiwancheng
-- ----------------------------
INSERT INTO `bisaiwancheng` VALUES (1, 1, 'B003', '全明星技巧挑战赛', 3, 3, 5, '032219433149', 3, '萨法', '2026-03-22 19:43:29');
INSERT INTO `bisaiwancheng` VALUES (2, 1, 'B003', '全明星技巧挑战赛', 3, 3, 5, '032219463739', 3, '11', '2026-03-22 19:49:12');
INSERT INTO `bisaiwancheng` VALUES (3, 2, 'B003', '全明星技巧挑战赛', 3, 2, 5, '032219499661', 2, '11', '2026-03-22 19:49:46');
INSERT INTO `bisaiwancheng` VALUES (4, 3, 'B003', '全明星技巧挑战赛', 3, 2, 3, '03221949423', 2, '11', '2026-03-22 19:50:05');
INSERT INTO `bisaiwancheng` VALUES (5, 6, 'B004', '新生杯篮球赛', 1, 2, 3, '032221288443', 3, '11', '2026-03-22 21:28:08');
INSERT INTO `bisaiwancheng` VALUES (6, 5, 'B004', '新生杯篮球赛', 1, 2, 5, '032221286862', 5, '1', '2026-03-22 21:28:13');
INSERT INTO `bisaiwancheng` VALUES (7, 4, 'B004', '新生杯篮球赛', 1, 3, 5, '032221283417', 5, '1', '2026-03-22 21:28:19');

-- ----------------------------
-- Table structure for bisaixinxi
-- ----------------------------
DROP TABLE IF EXISTS `bisaixinxi`;
CREATE TABLE `bisaixinxi`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `bisaibianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛编号',
  `bisaibiaoti` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛标题',
  `bisaileixing` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '比赛类型',
  `bisaimoshi` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛模式',
  `bisaitupian` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛图片',
  `canjiaqiudui` int(11) NOT NULL DEFAULT 0 COMMENT '参加球队',
  `bisaichangdi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛场地',
  `bisaineirong` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '比赛内容',
  `tianjiaren` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '添加人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bisaileixing_index`(`bisaileixing`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '比赛信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bisaixinxi
-- ----------------------------
INSERT INTO `bisaixinxi` VALUES (1, 'B001', '2026 年春季校园篮球联赛', 1, '5V5', '/upload/3b62457994e8a1d5e5121d1ced2482df.png', 8, '学校体育馆主馆', '<p>一年一度的校园篮球联赛，8 支球队进行单循环赛，积分前四名进入季后赛。</p>', 'admin');
INSERT INTO `bisaixinxi` VALUES (2, 'B002', '三对三篮球挑战赛', 2, '3V3', '/upload/ce835b54d2997b1bce929c0ebe3ea53c.png', 6, '学校室外篮球场', '<p>三人制篮球赛，节奏更快，对抗更激烈，每队可报名 3-4 名球员。</p>', 'admin');
INSERT INTO `bisaixinxi` VALUES (3, 'B003', '全明星技巧挑战赛', 3, '个人赛', '/upload/111ba997854b7dc2b95276e15b99f6cc.png', 16, '学校体育馆副馆', '<p>包含运球技巧、传球接力、定点投篮、扣篮大赛等多个项目。</p>', 'admin');
INSERT INTO `bisaixinxi` VALUES (4, 'B004', '新生杯篮球赛', 1, '5V5', '/upload/79f432922134957cc5023a381be36ac3.png', 8, '学校体育馆主馆', '<p>面向大一新生的篮球比赛，旨在发掘新人，培养篮球后备力量。</p>', 'admin');

-- ----------------------------
-- Table structure for dianzan
-- ----------------------------
DROP TABLE IF EXISTS `dianzan`;
CREATE TABLE `dianzan`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户',
  `biaoid` int(11) NOT NULL DEFAULT 0 COMMENT '关联表id',
  `biao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '关联表',
  `biaoti` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点赞' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dianzan
-- ----------------------------
INSERT INTO `dianzan` VALUES (1, '888', 4, 'bisaixinxi', '新生杯篮球赛', '2026-03-22 18:41:59');
INSERT INTO `dianzan` VALUES (2, '001', 3, 'bisaixinxi', '全明星技巧挑战赛', '2026-03-22 20:41:49');
INSERT INTO `dianzan` VALUES (3, 'q001', 4, 'bisaixinxi', '新生杯篮球赛', '2026-03-22 20:46:36');

-- ----------------------------
-- Table structure for guanzhong
-- ----------------------------
DROP TABLE IF EXISTS `guanzhong`;
CREATE TABLE `guanzhong`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `yonghuming` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `mima` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `xingming` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `xingbie` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '性别',
  `shouji` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机',
  `youxiang` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `touxiang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `issh` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '否' COMMENT '是否审核',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '观众' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of guanzhong
-- ----------------------------
INSERT INTO `guanzhong` VALUES (1, '001', '001', '黎明', '男', '17854454544', '4545@qq.com', '/upload/5ad36c1b58f13cbf291c6ac1c9848fca.png', '否');

-- ----------------------------
-- Table structure for jiaolian
-- ----------------------------
DROP TABLE IF EXISTS `jiaolian`;
CREATE TABLE `jiaolian`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gonghao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '工号',
  `mima` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `zhicheng` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '职称',
  `xingming` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `xingbie` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '性别',
  `zizhizhengshu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资质证书',
  `zhijiaojingyan` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执教经验',
  `lianxidianhua` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `touxiang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `issh` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '否' COMMENT '是否审核',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '教练' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jiaolian
-- ----------------------------
INSERT INTO `jiaolian` VALUES (1, '888', '888', '高价', '王交', '男', '/upload/9212a121ff3781a3f5ac5978c0ccde8c.png', 'werewolf惹我额外惹我  时段十多个是', '17845454455', '/upload/220821f6eac7ffa1cd45cbae34935eec.png', '否');
INSERT INTO `jiaolian` VALUES (2, '999', '999', '低级', '王梅', '男', '/upload/f4c60ba318ac98abf19a570dbeac8758.png', '热污染讽德诵功当当是感受到', '17845454545', '/upload/e80e148f555c40e16c5c6706c3fccbec.png', '否');

-- ----------------------------
-- Table structure for lunbotu
-- ----------------------------
DROP TABLE IF EXISTS `lunbotu`;
CREATE TABLE `lunbotu`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '连接地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '轮播图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lunbotu
-- ----------------------------
INSERT INTO `lunbotu` VALUES (1, '1', '/upload/5ab5241f32344e40b2dee9063351bc62.png', '#');
INSERT INTO `lunbotu` VALUES (2, '2', '/upload/725817c6db6f4f0f1146822ec53b9075.png', '#');
INSERT INTO `lunbotu` VALUES (3, '3', '/upload/ef7f0808cc67cbc925127ccba8a97d03.png', '#');

-- ----------------------------
-- Table structure for pinglun
-- ----------------------------
DROP TABLE IF EXISTS `pinglun`;
CREATE TABLE `pinglun`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `biao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '表',
  `biaoid` int(11) NOT NULL DEFAULT 0 COMMENT '表id',
  `biaoti` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `pingfen` int(11) NOT NULL DEFAULT 0 COMMENT '评分',
  `pinglunneirong` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `xingming` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `touxiang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `pinglunren` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评论人',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pinglun
-- ----------------------------
INSERT INTO `pinglun` VALUES (1, 'bisaixinxi', 4, '新生杯篮球赛', 5, '11', '', '', '888', '2026-03-22 18:42:02');
INSERT INTO `pinglun` VALUES (2, 'bisaixinxi', 3, '全明星技巧挑战赛', 5, '大公司个', '', '', '888', '2026-03-22 20:34:11');
INSERT INTO `pinglun` VALUES (3, 'bisaixinxi', 3, '全明星技巧挑战赛', 5, '阿发 方', '王交', '/upload/220821f6eac7ffa1cd45cbae34935eec.png', '888', '2026-03-22 20:40:46');
INSERT INTO `pinglun` VALUES (4, 'bisaixinxi', 3, '全明星技巧挑战赛', 5, '萨法阿萨', '黎明', '/upload/5ad36c1b58f13cbf291c6ac1c9848fca.png', '001', '2026-03-22 20:41:51');
INSERT INTO `pinglun` VALUES (5, 'bisaixinxi', 3, '全明星技巧挑战赛', 5, '11', '张三', '/upload/player1.jpg', 'q001', '2026-03-22 20:45:34');
INSERT INTO `pinglun` VALUES (6, 'bisaixinxi', 2, '三对三篮球挑战赛', 5, '11', '张三', '/upload/player1.jpg', 'q001', '2026-03-22 20:46:14');
INSERT INTO `pinglun` VALUES (7, 'bisaixinxi', 1, '2026 年春季校园篮球联赛', 5, '1', '张三', '/upload/1351ef697d137caaa02618cca986ac49.png', 'q001', '2026-03-22 20:46:21');
INSERT INTO `pinglun` VALUES (8, 'bisaixinxi', 4, '新生杯篮球赛', 5, '第三方时段', '王交', '/upload/220821f6eac7ffa1cd45cbae34935eec.png', '888', '2026-03-22 21:26:30');

-- ----------------------------
-- Table structure for pinglunhuifu
-- ----------------------------
DROP TABLE IF EXISTS `pinglunhuifu`;
CREATE TABLE `pinglunhuifu`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pinglunid` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论id',
  `biao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '表',
  `biaoid` int(11) NOT NULL DEFAULT 0 COMMENT '表id',
  `biaoti` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `pinglunneirong` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `pinglunren` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '评论人',
  `huifuneirong` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `huifuren` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '回复人',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pinglun_index`(`pinglunid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论回复' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pinglunhuifu
-- ----------------------------
INSERT INTO `pinglunhuifu` VALUES (1, 3, 'bisaixinxi', 3, '全明星技巧挑战赛', '阿发 方', '888', '艾师傅', '888', '2026-03-22 20:40:50');

-- ----------------------------
-- Table structure for qiudui
-- ----------------------------
DROP TABLE IF EXISTS `qiudui`;
CREATE TABLE `qiudui`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `qiuduibianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队编号',
  `qiuduimingcheng` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队名称',
  `qiuduitupian` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队图片',
  `qiuduiduiyuan` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '球队队员',
  `shilidengji` int(11) NOT NULL DEFAULT 0 COMMENT '实力等级',
  `qiuduijifen` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '球队积分',
  `qiuduixiangqing` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '球队详情',
  `jiaolianxingming` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '教练姓名',
  `qiuduijiaolian` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球队教练',
  `chenglishijian` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '成立时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '球队' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qiudui
-- ----------------------------
INSERT INTO `qiudui` VALUES (2, 'T002', '猛虎队', '/upload/c4abe52655af8fc1923ffedcf1edc051.png', '张三、李四、徐八、朱九、何十、高一、梁二、宋三', 5, 102.00, '<p>猛虎队以强悍的防守和内线优势闻名，是联赛中的传统强队。</p>', '王交', '888', '2024-09-01');
INSERT INTO `qiudui` VALUES (3, 'T003', '闪电队', '/upload/f00cdd5a0f86a6536f828bb2c5efec8c.png', '吴十、郑一、王二、刘三、陈四、杨五、黄六、林七', 4, 108.50, '<p>闪电队以外线投射精准著称，三分球命中率联盟第一。</p>', '王交', '888', '2025-03-01');
INSERT INTO `qiudui` VALUES (5, 'T005', '火箭队', '/upload/1b59b37d2f5ca03a54ea6b903bdf1f77.png', '冯五、邓六、许七、曹八、彭九、曾十、萧一、田二', 3, 92.50, '<p>火箭队注重团队配合，战术执行力强，是联赛中的黑马。</p>', '王梅', '999', '2025-09-02');
INSERT INTO `qiudui` VALUES (7, 'T007', '勇士队', '/upload/bac90f88695f5847ea705494c16957ee.png', '赵六、唐四', 4, 75.50, '<p>勇士队打法华丽，观赏性强，深受球迷喜爱。</p>', '王梅', '999', '2024-03-01');
INSERT INTO `qiudui` VALUES (9, '032216207274', '飞鹰队', '/upload/024a3d2e7b1a5ec868e3f261614a9632.png', '王五、钱七、王黎明', 4, 0.00, '<p>飞鹰队是一支以速度快、配合默契著称的球队，擅长快攻反击。</p>', '王交', '888', '2026-03-22');
INSERT INTO `qiudui` VALUES (10, '032216276575', '战狼队', '/upload/877144f94470f4261cef28b007a49c7f.png', '孙八、周九', 5, 20.00, '<p>战狼队是一支年轻有活力的队伍，拼抢积极，作风顽强。</p>', '王交', '888', '2026-03-05');

-- ----------------------------
-- Table structure for qiuyuanxinxi
-- ----------------------------
DROP TABLE IF EXISTS `qiuyuanxinxi`;
CREATE TABLE `qiuyuanxinxi`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `zhanghao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `mima` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `xingming` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `xingbie` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '性别',
  `lianxidianhua` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `nianling` int(11) NOT NULL DEFAULT 0 COMMENT '年龄',
  `shengao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '身高',
  `tizhong` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '体重',
  `suoshuqiudui` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '所属球队',
  `qiuyuanweizhi` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球员位置',
  `qiuyihao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '球衣号',
  `gerenjianjie` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '个人简介',
  `touxiang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `suoshuqiudui_index`(`suoshuqiudui`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '球员信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qiuyuanxinxi
-- ----------------------------
INSERT INTO `qiuyuanxinxi` VALUES (1, 'q001', '123456', '张三', '男', '13800138001', 20, '185', '75', '猛虎队', '控球后卫', '1', '<p>速度快，组织能力强，是球队的进攻发动机。</p>', '/upload/1351ef697d137caaa02618cca986ac49.png');
INSERT INTO `qiuyuanxinxi` VALUES (2, 'q002', '123456', '李四', '男', '13800138002', 21, '190', '80', '猛虎队', '得分后卫', '3', '<p>投篮精准，尤其擅长三分球，是球队的主要得分手。</p>', '/upload/player2.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (3, 'q003', '123456', '王五', '男', '13800138003', 19, '188', '78', '飞鹰队', '小前锋', '7', '<p>全能型球员，攻防俱佳，身体素质出色。</p>', '/upload/player3.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (4, 'q004', '123456', '赵六', '男', '13800138004', 20, '195', '85', '勇士队', '大前锋', '15', '<p>篮板能力强，内线防守稳健。</p>', '/upload/player4.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (5, 'q005', '123456', '钱七', '男', '13800138005', 22, '200', '90', '飞鹰队', '中锋', '33', '<p>球队内线支柱，盖帽和篮板数据亮眼。</p>', '/upload/player5.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (6, 'q006', '123456', '孙八', '男', '13800138006', 21, '183', '73', '战狼队', '控球后卫', '5', '<p>传球视野开阔，助攻数联盟前列。</p>', '/upload/player6.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (7, 'q007', '123456', '周九', '男', '13800138007', 20, '192', '82', '战狼队', '得分后卫', '11', '<p>突破犀利，造犯规能力强。</p>', '/upload/player7.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (8, 'q008', '123456', '吴十', '男', '13800138008', 19, '190', '79', '闪电队', '小前锋', '21', '<p>防守专家，多次获得最佳防守球员。</p>', '/upload/player8.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (9, 'q009', '123456', '郑一', '男', '13800138009', 21, '196', '86', '闪电队', '大前锋', '23', '<p>中距离投篮稳定，策应能力强。</p>', '/upload/player9.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (10, 'q010', '123456', '王二', '男', '13800138010', 22, '202', '92', '闪电队', '中锋', '55', '<p>篮下霸主，得分效率高。</p>', '/upload/player10.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (11, 'q011', '123456', '刘三', '男', '13800138011', 20, '186', '76', '闪电队', '控球后卫', '2', '<p>三分神射手，无球跑动积极。</p>', '/upload/player11.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (12, 'q012', '123456', '陈四', '男', '13800138012', 21, '189', '78', '闪电队', '得分后卫', '8', '<p>快攻得分能力强，终结效率高。</p>', '/upload/player12.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (13, 'q013', '123456', '杨五', '男', '13800138013', 19, '191', '81', '闪电队', '小前锋', '13', '<p>身体素质劲爆，扣篮精彩。</p>', '/upload/player13.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (14, 'q014', '123456', '黄六', '男', '13800138014', 20, '194', '84', '闪电队', '大前锋', '24', '<p>拼抢积极，二次进攻能力强。</p>', '/upload/player14.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (15, 'q015', '123456', '林七', '男', '13800138015', 22, '198', '88', '闪电队', '中锋', '44', '<p>护框能力强，内线威慑力大。</p>', '/upload/player15.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (16, 'q016', '123456', '徐八', '男', '13800138016', 20, '184', '74', '猛虎队', '控球后卫', '4', '<p>新秀球员，潜力巨大，进步迅速。</p>', '/upload/player16.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (17, 'q017', '123456', '朱九', '男', '13800138017', 21, '188', '77', '猛虎队', '得分后卫', '10', '<p>外线防守尖兵，抢断数据出色。</p>', '/upload/player17.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (18, 'q018', '123456', '何十', '男', '13800138018', 19, '192', '80', '猛虎队', '小前锋', '17', '<p>学习能力强的年轻球员。</p>', '/upload/player18.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (19, 'q019', '123456', '高一', '男', '13800138019', 20, '195', '83', '猛虎队', '大前锋', '30', '<p>蓝领球员，干脏活累活。</p>', '/upload/player19.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (20, 'q020', '123456', '梁二', '男', '13800138020', 22, '199', '89', '猛虎队', '中锋', '50', '<p>经验丰富的老将，球队精神领袖。</p>', '/upload/player20.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (21, 'q021', '123456', '宋三', '男', '13800138021', 20, '185', '75', '猛虎队', '控球后卫', '6', '<p>冷静沉着，关键时刻不手软。</p>', '/upload/player21.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (22, 'q022', '123456', '唐四', '男', '13800138022', 21, '190', '79', '勇士队', '得分后卫', '12', '<p>跑位灵活，接球投篮准。</p>', '/upload/player22.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (23, 'q023', '123456', '冯五', '男', '13800138023', 19, '189', '78', '火箭队', '小前锋', '18', '<p>防守态度好，团队意识强。</p>', '/upload/player23.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (24, 'q024', '123456', '邓六', '男', '13800138024', 20, '193', '82', '火箭队', '大前锋', '25', '<p>内线技术细腻，脚步灵活。</p>', '/upload/player24.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (25, 'q025', '123456', '许七', '男', '13800138025', 22, '197', '87', '火箭队', '中锋', '45', '<p>策应型中锋，传球意识好。</p>', '/upload/player25.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (26, 'q026', '123456', '曹八', '男', '13800138026', 20, '186', '76', '火箭队', '控球后卫', '9', '<p>节奏大师，善于掌控比赛节奏。</p>', '/upload/player26.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (27, 'q027', '123456', '彭九', '男', '13800138027', 21, '191', '80', '火箭队', '得分后卫', '14', '<p>关键先生，多次绝杀对手。</p>', '/upload/player27.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (28, 'q028', '123456', '曾十', '男', '13800138028', 19, '190', '79', '火箭队', '小前锋', '20', '<p>活力四射，替补席上的得分手。</p>', '/upload/player28.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (29, 'q029', '123456', '萧一', '男', '13800138029', 20, '194', '83', '火箭队', '大前锋', '32', '<p>防守悍将，篮板痴汉。</p>', '/upload/player29.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (30, 'q030', '123456', '田二', '男', '13800138030', 22, '201', '91', '火箭队', '中锋', '52', '<p>内线大闸，盖帽高手。</p>', '/upload/player30.jpg');
INSERT INTO `qiuyuanxinxi` VALUES (36, '100', '100', '王黎明', '男', '17854545444', 23, '170', '60', '飞鹰队', '冲锋', '18', '是非得失多少佛山丰富的', '/upload/05a397e9a61cb5b87417c03d1ff80357.png');

-- ----------------------------
-- Table structure for saichengbiao
-- ----------------------------
DROP TABLE IF EXISTS `saichengbiao`;
CREATE TABLE `saichengbiao`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `bisaixinxiid` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '比赛信息id',
  `bisaibianhao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛编号',
  `bisaibiaoti` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比赛标题',
  `bisaileixing` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '比赛类型',
  `qiuduia` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '球队A',
  `vs` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'VS',
  `qiuduib` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '球队B',
  `bifen` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '比分',
  `zhuangtai` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '状态',
  `bisaineirong` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '比赛内容',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bisaixinxi_index`(`bisaixinxiid`) USING BTREE,
  INDEX `bisaileixing_index`(`bisaileixing`) USING BTREE,
  INDEX `qiuduia_index`(`qiuduia`) USING BTREE,
  INDEX `qiuduib_index`(`qiuduib`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '赛程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of saichengbiao
-- ----------------------------
INSERT INTO `saichengbiao` VALUES (1, 3, 'B003', '全明星技巧挑战赛', 3, 3, 'VS', 5, '5/7', '已完成', '1', '2026-03-22 19:38:25');
INSERT INTO `saichengbiao` VALUES (2, 3, 'B003', '全明星技巧挑战赛', 3, 2, 'VS', 5, '3/5', '已完成', '1', '2026-03-22 19:38:25');
INSERT INTO `saichengbiao` VALUES (3, 3, 'B003', '全明星技巧挑战赛', 3, 2, 'VS', 3, '5/6', '已完成', '1', '2026-03-22 19:38:25');
INSERT INTO `saichengbiao` VALUES (4, 4, 'B004', '新生杯篮球赛', 1, 3, 'VS', 5, '6/7', '已完成', '11', '2026-03-22 21:27:24');
INSERT INTO `saichengbiao` VALUES (5, 4, 'B004', '新生杯篮球赛', 1, 2, 'VS', 5, '5/6', '已完成', '11', '2026-03-22 21:27:24');
INSERT INTO `saichengbiao` VALUES (6, 4, 'B004', '新生杯篮球赛', 1, 2, 'VS', 3, '8/9', '已完成', '11', '2026-03-22 21:27:24');

-- ----------------------------
-- Table structure for xinwenfenlei
-- ----------------------------
DROP TABLE IF EXISTS `xinwenfenlei`;
CREATE TABLE `xinwenfenlei`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `fenleimingcheng` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xinwenfenlei
-- ----------------------------
INSERT INTO `xinwenfenlei` VALUES (3, '站内新闻');
INSERT INTO `xinwenfenlei` VALUES (4, '行业资讯');
INSERT INTO `xinwenfenlei` VALUES (5, '赛事动态');

-- ----------------------------
-- Table structure for xinwengonggao
-- ----------------------------
DROP TABLE IF EXISTS `xinwengonggao`;
CREATE TABLE `xinwengonggao`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `biaoti` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `fenlei` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '分类',
  `tupian` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图片',
  `tianjiaren` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '添加人',
  `dianjiliang` int(11) NOT NULL DEFAULT 0 COMMENT '点击量',
  `neirong` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fenlei_index`(`fenlei`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xinwengonggao
-- ----------------------------
INSERT INTO `xinwengonggao` VALUES (1, '2026 年春季校园篮球联赛开幕', 5, '/upload/95b1f15dc2af960aec3a110fab58f44f.png', 'admin', 156, '<p>2026 年春季校园篮球联赛于今日正式开幕，共有 8 支球队参赛，比赛将持续两个月。</p>');
INSERT INTO `xinwengonggao` VALUES (2, '篮球培训营招募新学员', 3, '/upload/1dba0ae55a84ba965f0753c7d046dcbf.png', 'admin', 90, '<p>专业篮球教练团队开设暑期培训营，面向全校招收篮球爱好者，提升篮球技能。</p>');
INSERT INTO `xinwengonggao` VALUES (3, '校篮球队荣获省级比赛冠军', 4, '/upload/8bd220f5f3db7b1c5dcce166121de379.png', 'admin', 234, '<p>我校篮球队在省级大学生篮球联赛中勇夺冠军，创造了学校历史最好成绩。</p>');
INSERT INTO `xinwengonggao` VALUES (4, 'NBA 新赛季赛程公布', 5, '/upload/7f1eaef3876a8ba8cf6a5021fa16f294.png', 'admin', 178, '<p>NBA 官方公布了 2026-2027 赛季完整赛程，揭幕战将于 10 月中旬打响。</p>');

-- ----------------------------
-- Table structure for youqinglianjie
-- ----------------------------
DROP TABLE IF EXISTS `youqinglianjie`;
CREATE TABLE `youqinglianjie`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `wangzhanmingcheng` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '网站名称',
  `wangzhi` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '网址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '友情链接' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of youqinglianjie
-- ----------------------------
INSERT INTO `youqinglianjie` VALUES (1, '百度', 'http://www.baidu.com');
INSERT INTO `youqinglianjie` VALUES (2, '谷歌', 'http://www.google.cn');
INSERT INTO `youqinglianjie` VALUES (3, '新浪', 'http://www.sina.com');
INSERT INTO `youqinglianjie` VALUES (4, 'QQ', 'http://www.qq.com');
INSERT INTO `youqinglianjie` VALUES (5, '网易', 'http://www.163.com');

SET FOREIGN_KEY_CHECKS = 1;
