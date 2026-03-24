# OpenClaw-TZ

> 一个围绕 OpenClaw 构建的中文个人 AI 工作区示例：包含人格设定、长期记忆、技能体系、多代理协作与实际工作流。

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

## 能力概览

- **人格工作区**：通过 `SOUL.md`、`AGENTS.md`、`IDENTITY.md` 约束行为与语气
- **长期记忆**：通过 `memory/` 保存历史记录、偏好与关键事项
- **技能扩展**：通过 `skills/` 挂载文档、表格、前端、自动化等能力
- **多代理协作**：可接入中书省、尚书省等上游/下游代理结构
- **渠道接入**：支持 Telegram 等消息入口
- **本地自动化**：支持文件、命令、脚本与报告工作流

## 适用场景

- 搭建个人 OpenClaw 助理
- 维护带人格和记忆的长期 AI 工作区
- 做中文多代理协作实验
- 管理技能仓库与自动化脚本
- 作为 OpenClaw 中文实战示例参考

## 目录结构

```text
.
├── AGENTS.md          # 工作协议
├── SOUL.md            # 角色设定与语气
├── USER.md            # 用户信息
├── IDENTITY.md        # 身份定义
├── HEARTBEAT.md       # 心跳任务
├── memory/            # 长期记忆与工作记录
├── skills/            # 本地技能
├── reports/           # 报告脚本与产出
├── screenshots/       # 项目截图（默认不建议公开纳管）
├── edict/             # 独立项目目录（当前为嵌套仓库）
└── .openclaw/         # 本地运行态文件（不应公开）
```

## 快速开始

### 1. 克隆仓库

```bash
git clone https://github.com/daiqiongzhao-bit/Openclaw-TZ.git
cd Openclaw-TZ
```

### 2. 检查 OpenClaw 是否可用

```bash
openclaw status
```

### 3. 配置 `taizi` 工作区

在 `~/.openclaw/openclaw.json` 中确认代理工作区指向当前目录：

```json
{
  "id": "taizi",
  "workspace": "/path/to/Openclaw-TZ"
}
```

### 4. 重启 Gateway

```bash
openclaw gateway restart
```

### 5. 发送测试消息

例如：

- 你好
- 查看配置文件
- 帮我写一份部署文档

## 文档导航

- [INSTALL.md](./INSTALL.md) — 快速安装说明
- [DEPLOYMENT.md](./DEPLOYMENT.md) — 部署与启动流程
- [ARCHITECTURE.md](./ARCHITECTURE.md) — 架构说明
- [FAQ.md](./FAQ.md) — 常见问题
- [SECURITY.md](./SECURITY.md) — 安全建议
- [LICENSE](./LICENSE) — 开源许可证

## 运行思路

```text
用户消息
   ↓
OpenClaw 渠道接入
   ↓
taizi 工作区
   ↓
读取人格 / 记忆 / 技能 / 规则
   ↓
执行任务 / 协作 / 输出结果
   ↓
返回用户
```

## 注意事项

1. 本仓库更像真实工作区样本，不是纯净脚手架模板。
2. `edict/` 当前为嵌套 git 仓库，是否保留需按实际用途处理。
3. `.openclaw/`、认证文件、日志、锁文件等不应进入公开仓库。
4. 若用于公开展示，建议继续做目录清理、脱敏与模板化处理。

## 后续建议

如果你准备继续把它打磨成更成熟的公开项目，建议补充：

- `.env.example`
- `INSTALL.md`
- `CONTRIBUTING.md`
- 初始化脚本（已提供基础版）
- 架构图 / 流程图图片版
- 仓库清理与模板化拆分

---

如果你也在搭建自己的 OpenClaw 工作区，这个仓库可以作为一个可直接参考、可持续演化的中文起点。
