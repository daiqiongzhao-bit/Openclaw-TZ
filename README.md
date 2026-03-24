# OpenClaw-TZ

> 一个围绕 OpenClaw 构建的中文个人 AI 工作区示例，现已补齐“可一键初始化”的初版交付能力。

![Platform](https://img.shields.io/badge/platform-OpenClaw-blue)
![Workspace](https://img.shields.io/badge/workspace-Taizi-purple)
![Docs](https://img.shields.io/badge/docs-ready-brightgreen)
![License](https://img.shields.io/badge/license-MIT-black)

## 项目简介

`OpenClaw-TZ` 是 `taizi` 代理所使用的工作区仓库。

它不是单独的 SaaS 应用，而是一个 **长期运行型 AI 助理工作区**：

- 有明确的人格与行为协议
- 有用户上下文与长期记忆
- 有本地技能系统与自动化能力
- 可通过 Telegram 等渠道与用户交互
- 可与其他代理形成分工协作

如果你想搭建一个“不是一次性聊天框，而是可持续工作的 AI 助理工作区”，这个仓库就是一个中文实践样例。

## 现在可以怎样用

### 方式 1：远程单行安装（推荐）

```bash
curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
```

可选自定义：

```bash
OPENCLAW_TZ_INSTALL_DIR=$HOME/Openclaw-TZ curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
OPENCLAW_AGENT_ID=taizi curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
OPENCLAW_RESTART_GATEWAY=0 curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
```

### 方式 2：宿主机克隆后初始化

```bash
git clone https://github.com/daiqiongzhao-bit/Openclaw-TZ.git
cd Openclaw-TZ
bash install.sh
```

### 方式 3：用 Docker Compose 跑初始化容器

```bash
docker compose run --rm openclaw-tz-init
```

> 说明：当前仓库提供的是“工作区初始化交付”，不是 OpenClaw 全家桶生产镜像。长期运行仍建议在宿主机上部署 OpenClaw，或自行扩展专用镜像。

## 能力概览

- **人格工作区**：通过 `SOUL.md`、`AGENTS.md`、`IDENTITY.md` 约束行为与语气
- **长期记忆**：通过 `memory/` 保存历史记录、偏好与关键事项
- **技能扩展**：通过 `skills/` 挂载文档、表格、前端、自动化等能力
- **多代理协作**：可接入中书省、尚书省等上游/下游代理结构
- **渠道接入**：支持 Telegram 等消息入口
- **本地自动化**：支持文件、命令、脚本与报告工作流

## 目录结构

```text
.
├── AGENTS.md                 # 工作协议
├── SOUL.md                   # 角色设定与语气
├── USER.md                   # 用户信息
├── IDENTITY.md               # 身份定义
├── HEARTBEAT.md              # 心跳任务
├── memory/                   # 长期记忆与工作记录（公开前建议自查/脱敏）
├── skills/                   # 本地技能
├── reports/                  # 报告脚本与产出
├── screenshots/              # 项目截图（默认不建议纳入部署包）
├── edict/                    # 附属嵌套项目，非当前部署主体
├── install.sh                # 远程单行安装总入口
├── bin/init-openclaw-tz.sh   # 工作区初始化脚本
├── docker-compose.yml        # 初始化容器示例
└── .openclaw/                # 本地运行态文件（不应公开）
```

## 快速开始

### 1. 直接执行远程安装入口

```bash
curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
```

这个总入口会自动：

- 检查 `git` 与 `python3`
- 拉取或更新 `Openclaw-TZ` 仓库
- 调用 `bin/init-openclaw-tz.sh`
- 输出后续使用与自定义方式

### 2. 初始化脚本会继续尝试

- 生成 `.env`
- 确保 `~/.openclaw/openclaw.json` 存在
- 把 `taizi.workspace` 指向当前仓库
- 检查 OpenClaw 状态并重启 Gateway

### 3. 发送测试消息

例如：

- 你好
- 查看配置文件
- 帮我写一份部署文档

## 文档导航

- [INSTALL.md](./INSTALL.md) — 快速安装与初始化
- [DEPLOYMENT.md](./DEPLOYMENT.md) — 部署方式、交付边界与清理建议
- [ARCHITECTURE.md](./ARCHITECTURE.md) — 架构说明
- [FAQ.md](./FAQ.md) — 常见问题
- [SECURITY.md](./SECURITY.md) — 安全建议
- [LICENSE](./LICENSE) — 开源许可证

## 交付边界说明

本仓库适合公开交付的主要是：

- 工作区结构
- 协议/人格/技能样例
- 初始化脚本
- 安装/部署文档

默认不建议直接打包公开的内容包括：

- `.openclaw/`
- `*_auth.json`
- `*.lock`
- `*.log`
- 含私人记录的 `memory/`
- 大体积生成物与截图
- `edict/` 这类附属嵌套项目

## 注意事项

1. 本仓库更像真实工作区样本，不是纯净脚手架模板。
2. `edict/` 当前为嵌套 git 项目，建议单独维护，不作为当前部署主路径。
3. `.openclaw/`、认证文件、日志、锁文件等不应进入公开仓库。
4. 若要进一步产品化，建议后续拆分模板目录、最小配置模板与生产镜像。

## 后续建议

如果你准备继续把它打磨成更成熟的公开项目，建议补充：

- 标准化 `openclaw.json` 模板
- systemd / Supervisor 运行示例
- 生产级 Docker 镜像
- 仓库模板化拆分
- CI 校验（文档、脚本、JSON 格式）

---

如果你也在搭建自己的 OpenClaw 工作区，这个仓库现在已经可以作为一个更接近“可交付”的中文起点。
