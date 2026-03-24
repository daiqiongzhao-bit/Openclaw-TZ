# INSTALL

本文档面向第一次接手 `OpenClaw-TZ` 的使用者，目标是以最少步骤完成初始化并开始测试。

## 1. 适用对象

适合以下场景：

- 想快速把本仓库作为 `taizi` 工作区跑起来
- 已有 OpenClaw 环境，只缺工作区初始化
- 需要把仓库整理成可交付、可复用、可部署的样子

## 2. 前提条件

至少需要：

- Linux / macOS Shell 环境
- Git
- OpenClaw 已安装并可执行 `openclaw`
- Python 3（脚本会用它安全修改 `~/.openclaw/openclaw.json`）

可选：

- Docker / Docker Compose
- Telegram Bot Token
- 模型服务地址与 API Key

## 3. 远程单行安装（推荐）

```bash
curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
```

可选自定义方式：

```bash
OPENCLAW_TZ_INSTALL_DIR=$HOME/Openclaw-TZ curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
OPENCLAW_AGENT_ID=my-agent curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
OPENCLAW_RESTART_GATEWAY=0 curl -fsSL https://raw.githubusercontent.com/daiqiongzhao-bit/Openclaw-TZ/main/install.sh | bash
```

远程脚本会先做以下事情：

1. 检查 `git` 与 `python3` 等基础依赖
2. 拉取或更新 `Openclaw-TZ` 仓库到本地目录（默认 `$HOME/Openclaw-TZ`）
3. 自动调用仓库内的 `bin/init-openclaw-tz.sh`
4. 输出后续命令与自定义入口示例

## 4. 克隆仓库后执行（可选）

```bash
git clone https://github.com/daiqiongzhao-bit/Openclaw-TZ.git
cd Openclaw-TZ
bash install.sh
```

仓库内初始化脚本会继续做以下事情：

1. 若不存在 `.env`，则由 `.env.example` 复制生成
2. 检查并创建 `~/.openclaw/openclaw.json`（若不存在则生成最小示例）
3. 确保 `taizi.workspace` 指向当前仓库目录
4. 尝试执行 `openclaw status` 与 `openclaw gateway restart`

如果你不想自动重启 Gateway：

```bash
OPENCLAW_RESTART_GATEWAY=0 bash bin/init-openclaw-tz.sh
```

如果你的 agent id 不是 `taizi`：

```bash
OPENCLAW_AGENT_ID=my-agent bash bin/init-openclaw-tz.sh
```

## 5. 使用 Docker 进行初始化（可选）

如果宿主机不想直接执行脚本，也可用仓库根目录的 compose：

```bash
docker compose run --rm openclaw-tz-init
```

> 注意：这里的 compose 主要是“初始化容器”，不是完整的 OpenClaw 生产编排。真正长期运行的 OpenClaw 守护进程仍建议按宿主机方式部署，或改造成你自己的专用镜像。

## 6. 填写你的私有配置

初始化后建议检查：

### `.env`

```bash
cp .env.example .env
```

典型变量包括：

- `OPENAI_BASE_URL`
- `OPENAI_API_KEY`
- `TELEGRAM_BOT_TOKEN`
- `TELEGRAM_ALLOW_FROM`

### `~/.openclaw/openclaw.json`

重点检查：

- `agents.list[].workspace`
- 模型配置
- Telegram 渠道配置
- 工具权限与安全策略

## 7. 验证安装

```bash
openclaw status
openclaw gateway status
```

然后通过你的接入渠道给 `taizi` 发一条测试消息，例如：

- 你好
- 帮我查看 README
- 帮我总结当前工作区用途

## 8. 常见失败点

### `openclaw` 命令不存在

说明 OpenClaw 还未安装或未加入 PATH。请先完成 OpenClaw 本体安装。

### Gateway 重启失败

先手动查看：

```bash
openclaw gateway status
openclaw gateway restart
```

### JSON 配置写入失败

请检查：

- `~/.openclaw/openclaw.json` 是否为合法 JSON
- 当前用户是否有写权限
- 是否被其他进程锁定

## 9. 下一步

安装完成后继续阅读：

- [README.md](./README.md)
- [DEPLOYMENT.md](./DEPLOYMENT.md)
- [SECURITY.md](./SECURITY.md)
- [ARCHITECTURE.md](./ARCHITECTURE.md)
