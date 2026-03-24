# FAQ

## 1. 这个仓库是做什么的？

这是 `taizi` 代理对应的 OpenClaw 工作区仓库，用来承载人格、协议、记忆、技能、脚本和一些业务材料。

## 2. 它能直接运行吗？

它不是一个单独的通用应用，而是一个 **OpenClaw 工作区**。你需要先有 OpenClaw 运行环境，再把 `taizi.workspace` 指向这个仓库目录。

## 3. 最关键的配置在哪？

通常在：

```text
~/.openclaw/openclaw.json
```

重点看：

- `agents.list`
- `agents.defaults`
- `channels.telegram`
- `gateway`
- `tools`

## 4. 修改配置后为什么没生效？

通常需要重启 Gateway：

```bash
openclaw gateway restart
```

然后再检查：

```bash
openclaw gateway status
```

## 5. 为什么图片识别有时会失败？

常见原因：

- `imageModel` 配置不正确
- 模型服务端没有对应视觉模型
- 配置改了但服务未重启
- 工具链对模型名称解析不一致

## 6. 为什么 Telegram 能发消息，但某些能力用不了？

请检查：

- `allowFrom` 是否包含你的账号
- bot token 是否正确
- 群聊策略是否放开
- 执行工具是否启用
- sandbox / elevated 策略是否限制了能力

## 7. 这个仓库为什么有 `edict/`？

`edict/` 目前是一个独立项目目录，并且它本身还是一个嵌套 git 仓库。你可以：

- 保持它独立
- 把它作为子模块
- 把它改成普通目录再统一纳管

## 8. 哪些内容不应该推到公开仓库？

至少包括：

- token、密钥、认证文件
- `.openclaw/` 运行态文件
- 日志
- 截图中的敏感信息
- 个人隐私型记忆文件

## 9. 如何检查当前 OpenClaw 是否健康？

常用命令：

```bash
openclaw status
openclaw gateway status
openclaw security audit --deep
openclaw update status
```

## 10. 如何继续扩展这个仓库？

建议方向：

- 补架构说明
- 增加初始化脚本
- 增加环境变量模板
- 清理不该纳管的目录
- 整理技能说明
- 增加示例工作流

## 11. 适合公开开源吗？

可以，但建议先做这些准备：

- 脱敏
- 清理历史提交
- 增加许可证
- 补充架构文档
- 规范目录结构
- 补充安全说明

## 12. 这是一个适合生产的模板吗？

更准确地说，它目前更像一个**真实在用的工作区样本**，而不是精修后的标准模板。若要给更多人复用，建议再做一次工程化整理。
