# DEPLOYMENT

本文档说明如何把 `OpenClaw-TZ` 整理为“别人拿到就能部署”的工作区交付物。

## 一、交付定位

`OpenClaw-TZ` 不是一个独立 Web SaaS，而是一个 **OpenClaw 工作区仓库**。部署目标是：

- 把当前仓库绑定为某个 OpenClaw agent 的 workspace
- 补齐初始化脚本，减少手动修改成本
- 提供最小可用的 Docker/Compose 入口
- 明确哪些内容可纳管、哪些内容不应打包公开

## 二、推荐部署方式

### 方式 A：宿主机直接部署（推荐）

适合已经有 OpenClaw 的用户。

```bash
git clone https://github.com/daiqiongzhao-bit/Openclaw-TZ.git
cd Openclaw-TZ
bash bin/init-openclaw-tz.sh
```

优点：

- 最贴近 OpenClaw 的实际运行方式
- 更容易接入本机已有配置、模型服务、Telegram 渠道
- 更适合长期维护

### 方式 B：Docker Compose 运行初始化容器

适合想通过容器统一执行初始化逻辑的人。

```bash
docker compose run --rm openclaw-tz-init
```

优点：

- 初始化动作可复用
- 便于在 CI、测试机、临时机器上跑一遍

限制：

- 当前 compose 不是完整生产环境编排
- 仍需你自己准备 OpenClaw 镜像与长期运行方式

## 三、初始化脚本做了什么

根目录脚本：

```text
bin/init-openclaw-tz.sh
```

主要动作：

1. 自动生成 `.env`（若不存在）
2. 自动创建或更新 `~/.openclaw/openclaw.json`
3. 确保 agent `taizi` 指向当前工作区
4. 检查 OpenClaw / Gateway 状态
5. 尝试重启 Gateway

支持的环境变量：

- `OPENCLAW_AGENT_ID`：覆盖默认 agent id（默认 `taizi`）
- `OPENCLAW_DIR`：覆盖默认 `~/.openclaw`
- `OPENCLAW_CONFIG`：指定 OpenClaw 配置文件路径
- `OPENCLAW_RESTART_GATEWAY=0`：跳过自动重启

## 四、推荐交付内容

当前仓库适合被保留并公开的核心内容包括：

- `README.md`
- `INSTALL.md`
- `DEPLOYMENT.md`
- `ARCHITECTURE.md`
- `SECURITY.md`
- `AGENTS.md` / `SOUL.md` / `IDENTITY.md` / `USER.md`
- `skills/`
- `bin/init-openclaw-tz.sh`
- `.env.example`
- `docker-compose.yml`

## 五、不建议纳入公开打包的内容

以下内容更像运行态、个人态或敏感态数据：

- `.openclaw/`
- `*_auth.json`
- `*_auth_audit.json`
- `*.lock`
- `*.log`
- `memory/`（可能含私人记录或接入信息）
- `screenshots/`（演示图可按需单独挑选）
- `reports/*.pptx`
- `edict/`（当前为独立嵌套项目，建议单独维护）
- `data/`、`scripts/` 这类软链接/外部依赖目录

## 六、最小必要清理建议

为了不破坏当前公开仓库结构，建议采用“**只增强忽略规则，不强删文件**”的方式：

1. 用 `.gitignore` 屏蔽运行态与私有配置
2. 文档里明确哪些目录是样例、哪些不是交付主体
3. 若后续要做模板仓库，可再拆分：
   - `memory-template/`
   - `skills/`
   - `examples/`
   - `docs/`
4. 将 `edict/` 从当前仓库定位为“外部附属项目”而不是核心部署依赖

## 七、生产部署建议

如果你要给别人长期托管运行，建议再补：

- 一个明确的 OpenClaw 官方/自建镜像来源
- 完整 `openclaw.json` 模板
- systemd service 示例
- 反向代理（Nginx/Caddy）配置样例
- 健康检查与日志轮转说明
- 备份策略（尤其是 `memory/`）

## 八、部署后验收清单

部署完成后，至少检查：

```bash
openclaw status
openclaw gateway status
```

并验证：

- agent `taizi` 能正常回复消息
- 工作区文件可读
- `skills/` 可被识别
- `memory/` 可正常写入（若你保留该能力）
- Telegram 或其它渠道配置已生效

## 九、当前版本交付结论

本次整理后，仓库已经具备一个 **可落地的初版交付方向**：

- 有一键初始化脚本
- 有最小 Docker Compose 入口
- 有安装与部署文档
- 有对运行态/敏感态内容的打包边界说明

如果下一步要继续升级，可以再把它推进为：

1. 真正的模板仓库
2. 标准化的生产部署仓库
3. 带完整样例配置与 CI 校验的发行版
