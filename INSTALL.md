# INSTALL

本文档面向第一次拿到 `OpenClaw-TZ` 仓库的人，目标是尽快把它跑起来。

## 1. 你会得到什么

安装完成后，你会得到一个可作为 `taizi` 代理工作区使用的 OpenClaw 中文工作区样本。

## 2. 前提条件

你需要先准备：

- 一台 Linux 机器
- 已安装 OpenClaw
- 可用模型服务
- （可选）Telegram Bot Token

## 3. 获取仓库

```bash
git clone https://github.com/daiqiongzhao-bit/Openclaw-TZ.git
cd Openclaw-TZ
```

## 4. 复制示例配置

```bash
cp .env.example .env
```

> 注意：`.env.example` 只是示例；真正的 OpenClaw 运行核心配置仍主要在 `~/.openclaw/openclaw.json`。

## 5. 指向工作区

编辑你的 OpenClaw 配置文件：

```text
~/.openclaw/openclaw.json
```

在 `agents.list` 中找到或新增：

```json
{
  "id": "taizi",
  "workspace": "/path/to/Openclaw-TZ"
}
```

## 6. 检查状态

```bash
openclaw status
openclaw gateway status
```

## 7. 重启服务

```bash
openclaw gateway restart
```

## 8. 验证是否成功

向 `taizi` 发一条消息测试：

- 你好
- 查看配置文件
- 帮我写一份部署文档

## 9. 若要接入 Telegram

请在 `~/.openclaw/openclaw.json` 配置你自己的：

- `channels.telegram.botToken`
- `channels.telegram.allowFrom`
- `channels.telegram.enabled`

然后重启 Gateway。

## 10. 如果失败怎么办

先看这几份文档：

- `DEPLOYMENT.md`
- `FAQ.md`
- `SECURITY.md`
- `ARCHITECTURE.md`

---

如果你希望“一键上手”体验更完整，建议后续再补脚本化初始化与配置生成流程。
