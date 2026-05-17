# 智能校园预约系统 🎓

> 从可复制的代码，到不可复制的系统

## 项目简介

智能校园预约系统是一个面向高校的校园资源管理平台，解决自习室、实验室、会议室等公共资源"预约难、使用乱、管理黑"的三大痛点。

## 系统架构

```
┌──────────────────────────────────┐
│    表现层  (Vue 3 + Element Plus) │  ← Web / 小程序 / App
├──────────────────────────────────┤
│    应用层  (Spring Boot 微服务)   │  ← 用户中心 / 资源管理 / 预约服务 / 信用分
├──────────────────────────────────┤
│    数据层  (MySQL + Redis + ES)   │  ← 业务数据 / 缓存 / 日志检索
└──────────────────────────────────┘
```

## 核心技术栈

| 层级 | 技术 | 用途 |
|------|------|------|
| 前端 | Vue 3 + Vite + Element Plus | 用户界面 |
| 后端 | Spring Boot 3 + JPA | 业务逻辑 |
| 数据库 | MySQL 8.0 | 持久化存储 |
| 缓存 | Redis 7 | 高性能缓存 |
| 容器化 | Docker + docker-compose | 一键部署 |

## 核心功能

- **用户系统** — 注册、登录、JWT身份认证
- **资源管理** — 自习室/实验室/会议室的信息展示与检索
- **预约服务** — 在线预约、时间冲突检测、签到/取消
- **信用体系** — 信用分激励，违约自动扣分
- **管理后台** — 资源管理、预约记录查看

## 三大创新点

1. **Redis双机制应对高并发** — 日常轻量锁 + 高峰期分布式红锁
2. **智能释放 + 信用分** — No-show自动释放，用激励引导自律
3. **数据驱动决策** — 资源使用预测与个性化推荐（拓展方向）

## 快速启动

### 方式一：Docker 一键启动（推荐）

```bash
# 克隆项目
git clone https://github.com/Vae-Scrooge/smart-campus-reservation.git
cd smart-campus-reservation

# 一键启动所有服务
docker compose up -d

# 查看运行状态
docker compose ps
```

启动后访问：
- 前端页面：http://localhost:3000
- 后端API：http://localhost:8080

### 方式二：本地开发

#### 后端
```bash
# 需要 JDK 17+ 和 Maven
cd backend
mvn spring-boot:run
```

#### 前端
```bash
# 需要 Node.js 18+
cd frontend
npm install
npm run dev
```

### 默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 普通用户 | zhangsan | 123456 |
| 普通用户 | lisi | 123456 |

## 项目结构

```
smart-campus-reservation/
├── backend/                # Spring Boot 后端
│   ├── src/main/java/      # Java 源代码
│   ├── src/main/resources/  # 配置文件
│   └── pom.xml
├── frontend/               # Vue 3 前端
│   ├── src/views/          # 页面组件
│   ├── src/api/            # API 接口
│   ├── src/store/          # 状态管理
│   └── package.json
├── docker-compose.yml      # Docker 编排
└── README.md
```

## 演示截图

> （建议放几张页面截图增加可读性）

## 关于

软件工程4班第3组 · 创新创业理论与实务课程项目
