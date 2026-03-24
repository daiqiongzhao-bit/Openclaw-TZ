# DEPLOYMENT

本文档说明如何部署 `OpenClaw-TZ` 工作区，并将其作为 `taizi` 代理的工作目录运行。

## 一、部署目标

完成后，你将得到：

- 一个可运行的 OpenClaw 实例
- 一个绑定到 `taizi` 代理的独立工作区
- 可持续维护的人设、记忆、技能与脚本目录
- 可通过 Telegram 等渠道与代理交互的基础环境

## 二、环境要求

建议环境：

- Linux 服务器 / Linux 桌面
- Node.js 22+
- npm / pnpm
- Git
- 已安装 OpenClaw

可选能力：

- GitHub CLI（用于仓库与发布管理）
- Docker / Docker Compose（如需运行附带项目）
- ffmpeg、python3 等常用工具

## 三、获取仓库

```bash
git clone https://github.com/daiqiongzhao-bit/Openclaw-TZ.git
cd Openclaw-TZ
```

如果你是已有本地目录，也可直接把当前目录作为工作区使用。

## 四、安装 OpenClaw

若系统尚未安装 OpenClaw，请先按官方方式安装。

你可以先检查状态：

```bash
openclaw status
```

如需查看更多信息：

```bash
openclaw status --deep
```

## 五、配置工作区

OpenClaw 的核心配置通常位于：

```text
~/.openclaw/openclaw.json
```

请确认 `agents.list` 中存在 `taizi`，并把它的工作区指向当前仓库，例如：

```json
{
  "id": "taizi",
  "workspace": "/path/to/Openclaw-TZ"
}
```

如果已经存在多代理结构，也可以按你的组织方式保留上下游代理关系。

## 六、关键文件说明

部署后建议重点检查这些文件：

- `AGENTS.md`：工作协议
- `SOUL.md`：角色设定与语气
- `IDENTITY.md`：身份信息
- `USER.md`：用户信息
- `HEARTBEAT.md`：定时关注内容
- `skills/`：本地技能
- `memory/`：长期记忆与工作记录

## 七、启动与重启

检查 Gateway 状态：

```bash
openclaw gateway status
```

启动 Gateway：

```bash
openclaw gateway start
```

重启 Gateway：

```bash
openclaw gateway restart
```

如果配置文件刚改过，建议重启一次使其生效。

## 八、接入 Telegram（可选）

若要通过 Telegram 使用，请在 OpenClaw 配置中填写：

- `channels.telegram.enabled`
- `channels.telegram.botToken`
- `channels.telegram.allowFrom`
- `channels.telegram.dmPolicy`

配置完成后重启 Gateway，再通过 Telegram 私聊机器人测试。

## 九、验证部署是否成功

建议按以下顺序验证：

### 1. 检查 OpenClaw 状态

```bash
openclaw status
```

### 2. 检查 Gateway 状态

```bash
openclaw gateway status
```

### 3. 向 `taizi` 发送一条测试消息

例如：

- “你好”
- “查看配置文件”
- “帮我写一份部署文档”

### 4. 检查工作区是否正常读写

观察：

- 是否能读取 `SOUL.md`
- 是否能访问 `memory/`
- 是否能识别 `skills/`

## 十、常见问题

### 1. 图片无法识别

如果图像识别报模型不存在：

- 检查 `agents.defaults.imageModel`
- 检查后端模型服务是否真的支持该视觉模型
- 修改后重启 Gateway

### 2. Telegram 能收消息但工具异常

请检查：

- bot token 是否正确
- `allowFrom` 是否包含你的账号
- `tools.exec` / `tools.elevated` / `sandbox` 配置是否符合预期

### 3. GitHub 推送失败

请检查：

```bash
gh auth status
gh auth setup-git
```

必要时重新登录：

```bash
gh auth login
```

### 4. 配置改了但不生效

通常需要重启：

```bash
openclaw gateway restart
```

## 十一、安全建议

部署前建议至少检查以下事项：

- 不要把 token、密钥、认证文件直接提交到公开仓库
- 对公开群聊禁用高风险工具或收紧权限
- 尽量限制 elevated 工具的可用范围
- 检查 `groupPolicy`、`allowFrom`、`sandbox` 等安全配置
- 定期运行：

```bash
openclaw security audit --deep
openclaw update status
```

## 十二、推荐部署流程（最简版）

```bash
# 1. 克隆仓库
git clone https://github.com/daiqiongzhao-bit/Openclaw-TZ.git
cd Openclaw-TZ

# 2. 检查 OpenClaw 状态
openclaw status

# 3. 修改 ~/.openclaw/openclaw.json
#    让 taizi.workspace 指向当前目录

# 4. 重启服务
openclaw gateway restart

# 5. 检查状态
openclaw gateway status

# 6. 发送测试消息验证
```

## 十三、后续增强建议

你还可以继续完善：

- 增加 `.env.example`
- 增加 `README_EN.md`
- 增加初始化脚本
- 增加架构图与流程图
- 增加仓库清理规则与发布规范

---

如果后续要把这个仓库面向更多人使用，建议再补一份：

- `ARCHITECTURE.md`
- `FAQ.md`
- `SECURITY.md`
