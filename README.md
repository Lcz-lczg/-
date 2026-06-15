<p align="center">
  <h1 align="center">🏀 校园篮球比赛赛程管理系统</h1>
  <p align="center">
    <img src="https://img.shields.io/badge/Spring%20Boot-2.3.12-brightgreen" alt="Spring Boot" />
    <img src="https://img.shields.io/badge/Vue-3.5-blue" alt="Vue 3" />
    <img src="https://img.shields.io/badge/Element%20Plus-2.8-409EFF" alt="Element Plus" />
    <img src="https://img.shields.io/badge/MySQL-5.7-orange" alt="MySQL" />
    <img src="https://img.shields.io/badge/JDK-1.8-red" alt="JDK 1.8" />
    <img src="https://img.shields.io/badge/license-MIT-green" alt="License" />
  </p>
  <p align="center">
    一个基于 Spring Boot + Vue 3 的篮球赛事全流程管理平台<br/>
    覆盖从赛前报名、自动编排到赛后积分统计的完整业务链路
  </p>
</p>

---

## 📖 目录

- [项目介绍](#-项目介绍)
- [技术架构](#-技术架构)
- [项目结构](#-项目结构)
- [功能模块](#-功能模块)
- [数据库设计](#-数据库设计)
- [快速开始](#-快速开始)
- [API 文档](#-api-文档)
- [赛程编排算法](#-赛程编排算法)
- [部署说明](#-部署说明)
- [微信小程序集成](#-微信小程序集成)

---

## 🎯 项目介绍

校园篮球比赛赛程管理系统是一个面向高校体育赛事的信息化平台，旨在解决传统人工编排赛程效率低、易出错、信息分散的问题。系统提供以下核心能力：

- **赛事全生命周期管理** — 从比赛创建、球队报名、赛程自动生成到比赛结果录入与积分统计
- **智能赛程编排引擎** — 内置贝格尔编排算法，支持单循环 / 双循环 / 淘汰赛三种赛制，一键生成完整赛程
- **多端服务能力** — 同时为 Web 管理后台和微信小程序提供 RESTful API
- **丰富的内容运营** — 内置新闻公告、评论互动、轮播图、友情链接等 CMS 功能

---

## 🛠 技术架构

<table>
  <tr><th>层级</th><th>技术选型</th><th>版本</th></tr>
  <tr><td>后端框架</td><td>Spring Boot</td><td>2.3.12</td></tr>
  <tr><td>ORM</td><td>MyBatis-Plus + DB-Query</td><td>3.4 / 1.2</td></tr>
  <tr><td>安全认证</td><td>Spring Security RSA + JJWT</td><td>—</td></tr>
  <tr><td>数据库</td><td>MySQL + HikariCP</td><td>5.7</td></tr>
  <tr><td>前端框架</td><td>Vue 3 (Composition API)</td><td>3.5</td></tr>
  <tr><td>构建工具</td><td>Vite</td><td>4.4</td></tr>
  <tr><td>UI 组件库</td><td>Element Plus</td><td>2.8</td></tr>
  <tr><td>状态管理</td><td>Pinia</td><td>2.1</td></tr>
  <tr><td>数据可视化</td><td>ECharts</td><td>4.9</td></tr>
  <tr><td>富文本编辑器</td><td>TinyMCE</td><td>6.7</td></tr>
  <tr><td>Java 版本</td><td>JDK 8</td><td>—</td></tr>
  <tr><td>Node 版本</td><td>Node.js 18+ / pnpm</td><td>—</td></tr>
</table>

---

## 📁 项目结构

```
bishe/
├── java/                          # 🔧 后端 Spring Boot 项目
│   ├── pom.xml                    #   Maven 依赖管理
│   ├── Dockerfile                 #   Docker 镜像构建文件
│   └── src/main/
│       ├── java/com/spboot/app/
│       │   ├── StartApplication.java    #  应用启动入口
│       │   ├── config/                  #  配置层（拦截器、Swagger、跨域等）
│       │   ├── controller/              #  控制器层（19 个业务模块）
│       │   ├── service/                 #  业务逻辑层
│       │   ├── mapper/                  #  数据访问层（MyBatis Mapper）
│       │   ├── pojo/                    #  实体类
│       │   ├── encoder/                 #  密码编码器（MD5 / 明文）
│       │   └── utils/                   #  工具类
│       └── resources/
│           └── application.properties   #  应用配置
├── vue/                           # 🎨 前端 Vue 3 项目
│   ├── vite.config.js             #   Vite 构建配置
│   ├── package.json               #   依赖声明
│   └── src/
│       ├── main.js                #   应用入口
│       ├── config.js              #   API 地址 & 系统配置
│       ├── router/                #   路由（管理端 / 门户端分离）
│       ├── stores/                #   Pinia 状态管理
│       ├── views/                 #   页面组件（90+ 个 .vue 文件）
│       ├── components/            #   公共组件
│       ├── module/                #   业务模块
│       └── utils/                 #   工具函数 & Axios 封装
├── database/                      # 🗄 数据库
│   └── 1.sql                      #   建表脚本 + 示例数据
└── docs/                          # 📚 技术文档
    ├── 赛程编排功能说明.md
    ├── uni-app实施计划.md
    ├── 小程序框架对比-Taro-vs-uniApp-vs-原生.md
    └── 小程序移植需求手册.md
```

---

## ✨ 功能模块

### 🏆 核心比赛业务

| 模块 | 说明 |
|------|------|
| 比赛信息管理 | 创建比赛、设置赛制（5V5 / 3V3 / 技巧挑战赛）、上传封面图、配置场地 |
| 球队管理 | 球队注册、队员名单维护、实力等级（1–5 级）评定、积分追踪 |
| 球员档案 | 个人信息、场上位置、身高体重、球衣号码、所属球队 |
| 教练管理 | 教练注册、资质证书上传、执教经验、联系方式 |
| 比赛报名 | 球队在线选择比赛报名，自动关联球队信息与球员名单 |

### 🧩 赛程自动编排（核心亮点）

这是本系统区别于简单 CRUD 项目的关键模块，实现了算法驱动的赛程自动生成。

| 赛制 | 算法 | 场次公式 | 适用场景 |
|:---:|------|:---:|------|
| **单循环** | 贝格尔编排法 (Berger) | `n(n−1)/2` | 联赛常规赛 |
| **双循环** | 单循环 + 主客场互换 | `n(n−1)` | 主客场制联赛 |
| **淘汰赛** | 种子排位 + 轮空补齐 | `n−1` | 杯赛 / 季后赛 |

**编排流程：** 选择比赛 → 确认参赛球队 → 设置赛制 & 种子策略 → 预览赛程 → 一键保存

> 💡 启用"种子编排"后，系统按球队实力等级排序，确保强队在淘汰赛中后期相遇，避免强队提前对决。

### 📊 比赛数据

- **比分录入** — 赛后成绩登记，自动更新赛程状态
- **积分排行榜** — 根据胜负自动计算积分，实时排名
- **数据可视化** — ECharts 驱动的赛事统计图表

### 📰 内容运营

- **新闻公告** — 分类管理、富文本编辑、封面图上传
- **评论互动** — 比赛评论 + 回复，支持评分
- **轮播图管理** — 首页 Banner 配置
- **点赞 & 友情链接** — 基础 CMS 功能

---

## 🗄 数据库设计

系统共包含 **16 张业务表**，核心表关系如下：

```
                  ┌──────────────┐
                  │  bisaixinxi  │  ← 比赛信息
                  └──────┬───────┘
           ┌─────────────┼─────────────┐
           ▼             ▼             ▼
   ┌───────────┐  ┌───────────┐  ┌────────────┐
   │ bisaibaoming│  │saichengbiao│  │bisaiwancheng│
   │  (报名)    │  │  (赛程)   │  │  (完赛)    │
   └─────┬─────┘  └─────┬─────┘  └──────┬─────┘
         │              │               │
         ▼              ▼               ▼
   ┌───────────┐  ┌───────────┐  ┌────────────┐
   │   qiudui  │  │ qiudui(A/B)│  │shengliqiudui│
   │  (球队)   │  │  (对阵)   │  │  (胜者)    │
   └───────────┘  └───────────┘  └────────────┘
```

<details>
<summary>📋 全部数据表（点击展开）</summary>

| 表名 | 说明 |
|------|------|
| `admins` | 管理员 |
| `bisaixinxi` | 比赛信息 |
| `bisaileixing` | 比赛类型（字典） |
| `bisaibaoming` | 比赛报名 |
| `saichengbiao` | 赛程表 |
| `bisaiwancheng` | 比赛完成记录 |
| `qiudui` | 球队 |
| `qiuyuanxinxi` | 球员信息 |
| `jiaolian` | 教练 |
| `guanzhong` | 观众 |
| `xinwengonggao` | 新闻公告 |
| `xinwenfenlei` | 新闻分类（字典） |
| `pinglun` | 评论 |
| `pinglunhuifu` | 评论回复 |
| `dianzan` | 点赞记录 |
| `lunbotu` | 轮播图 |
| `youqinglianjie` | 友情链接 |

</details>

---

## 🚀 快速开始

### 环境要求

| 工具 | 版本要求 | 说明 |
|------|:---:|------|
| JDK | ≥ 1.8 | 后端编译与运行 |
| MySQL | ≥ 5.7 | 数据持久化 |
| Maven | ≥ 3.6 | 后端依赖管理 |
| Node.js | ≥ 18 | 前端运行环境 |
| pnpm | 最新 | 前端包管理（推荐） |

### ① 数据库初始化

```sql
-- 创建数据库
CREATE DATABASE bishe DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 导入表结构与示例数据
USE bishe;
SOURCE database/1.sql;
```

> 🔑 默认数据库连接：`root / root`，如需修改见 `java/src/main/resources/application.properties`

### ② 启动后端

```bash
cd java

# 安装依赖
mvn clean install -DskipTests

# 启动应用（开发模式，支持热重载）
mvn spring-boot:run
```

✅ 后端启动成功 → `http://localhost:8088`

### ③ 启动前端

```bash
cd vue

# 安装依赖
pnpm install

# 启动开发服务器
pnpm dev
```

✅ 前端启动成功 → `http://localhost:5174`

> 📝 如果后端端口非 8088，编辑 `vue/src/config.js` 修改 `service_url`

### ④ 登录系统

| 角色 | 账号 | 密码 | 入口 |
|------|------|------|------|
| 管理员 | `admin` | `admin` | `/#/admin/sy` |
| 教练 | `888` | `888` | `/#/login` |
| 球员 | `q001` | `123456` | `/#/login` |

---

## 📡 API 文档

### 接口总览

| 路由前缀 | 模块说明 |
|----------|----------|
| `/api/user/*` | 用户认证（登录、Token 刷新、验证码） |
| `/api/bisaixinxi/*` | 比赛信息 CRUD |
| `/api/bisaibaoming/*` | 比赛报名管理 |
| `/api/saichengbiao/*` | 赛程表管理 |
| `/api/schedule/*` | 🔥 赛程自动编排引擎 |
| `/api/bisaiwancheng/*` | 比分录入 & 完赛管理 |
| `/api/qiudui/*` | 球队管理 |
| `/api/qiuyuanxinxi/*` | 球员信息管理 |
| `/api/jiaolian/*` | 教练管理 |
| `/api/guanzhong/*` | 观众管理 |
| `/api/xinwengonggao/*` | 新闻公告 |
| `/api/pinglun/*` / `/api/pinglunhuifu/*` | 评论 & 回复 |
| `/api/dianzan/*` | 点赞 |
| `/api/lunbotu/*` | 轮播图 |
| `/api/youqinglianjie/*` | 友情链接 |
| `/api/upload/*` | 文件上传 |
| `/api/wechat/*` | 微信小程序登录 |

### 赛程编排接口（核心）

```text
GET   /api/schedule/availableTeams          # 获取可编排球队列表
GET   /api/schedule/matchInfo               # 获取比赛详情
POST  /api/schedule/generate                # 自动生成赛程（预览）
POST  /api/schedule/save                    # 批量保存赛程
POST  /api/schedule/clear                   # 清除已有赛程
GET   /api/schedule/preview                 # 预览已保存赛程
```

<details>
<summary>📨 编排请求示例（点击展开）</summary>

```json
{
  "bisaixinxiid": 1,
  "teamIds": [2, 3, 5, 7, 9, 10],
  "mode": "single",
  "levelBased": true
}
```

| 参数 | 类型 | 说明 |
|------|------|------|
| `bisaixinxiid` | int | 比赛信息 ID（必填） |
| `teamIds` | int[] | 参赛球队 ID 列表（必填） |
| `mode` | string | `single` / `double` / `knockout` |
| `levelBased` | bool | 是否启用种子编排 |

</details>

> 📖 完整 API 文档见 `docs/赛程编排功能说明.md`

---

## 🧮 赛程编排算法

### 单循环（贝格尔编排法）

固定 1 号位，其余球队每轮顺时针旋转一位，共进行 `n−1` 轮，每轮 `n/2` 场比赛。

```
8 队示例：
  第 1 轮: 1-8  2-7  3-6  4-5
  第 2 轮: 1-7  8-6  2-5  3-4
  ...
  第 7 轮: 1-2  3-8  4-7  5-6
```

### 双循环

在单循环基础上生成第二循环，交换主客场。

### 淘汰赛（种子编排）

1. 按实力等级降序排列球队
2. 不足 2ⁿ 时补轮空位
3. 强队对弱队配对，确保强强对话仅发生在决赛阶段

```
8 队种子对阵：
  1-8  (1号种子 vs 8号种子)
  4-5  (4号种子 vs 5号种子)
  ──────── 分区线 ────────
  2-7  (2号种子 vs 7号种子)
  3-6  (3号种子 vs 6号种子)
```

---

## 🐳 部署说明

### Docker 部署（后端）

```bash
cd java
mvn clean package -DskipTests
docker build -t basketball-schedule:latest .
docker run -d \
  --name basketball-app \
  -p 8088:8088 \
  -e MYSQL_HOST=your-mysql-host \
  basketball-schedule:latest
```

### 生产环境前端

```bash
cd vue
pnpm build      # 产出 dist/ 目录，部署至 Nginx 或 CDN
```

---

## 📱 微信小程序集成

系统预置微信小程序登录接口，相关配置与环境变量：

```properties
wechat.appid=${WECHAT_APPID:wx_dev_placeholder}
wechat.secret=${WECHAT_SECRET:dev_secret_placeholder}
```

| 文档 | 说明 |
|------|------|
| `docs/uni-app实施计划.md` | uni-app 小程序端开发实施方案 |
| `docs/小程序移植需求手册.md` | 小程序功能需求规格说明书 |
| `docs/小程序框架对比-Taro-vs-uniApp-vs-原生.md` | 技术选型对比分析 |

---

## 📄 License

MIT © 2026
