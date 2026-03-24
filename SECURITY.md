# SECURITY

本文档说明 `OpenClaw-TZ` 在部署与公开使用时的基本安全建议。

## 1. 总原则

该仓库本质上是一个可执行工具、可读写文件、可持久化记忆的 AI 工作区，因此安全边界必须先于功能边界设计。

请默认遵循以下原则：

- 最小权限
- 默认不公开
- 凭证不入库
- 对外暴露最少接口
- 高风险操作需人工确认

## 2. 不应提交到仓库的内容

请不要把以下内容直接提交到公开仓库：

- API Key
- Bot Token
- GitHub Token
- `*_auth.json`
- 各类本地凭证文件
- `.openclaw/` 下的运行态状态文件
- 日志文件
- 截图中可见的敏感信息
- 包含用户隐私的长期记忆文件

## 3. OpenClaw 配置建议

重点检查这些配置项：

- `channels.telegram.groupPolicy`
- `channels.telegram.allowFrom`
- `tools.elevated.enabled`
- `tools.exec`
- `agents.defaults.sandbox.mode`
- `gateway.bind`
- `gateway.auth`

### 推荐方向

- 私聊优先，群聊谨慎开放
- 非必要不要开放高权限执行工具
- 不要把 Gateway 暴露到公网且无额外保护
- 对高风险命令保留人工审核

## 4. 凭证管理建议

建议所有凭证都采取以下方式管理：

- 只保存在本机安全位置
- 使用环境变量或受控配置文件注入
- 定期轮换
- 用完即撤销或至少降低权限

若曾在聊天、终端、提交记录中暴露过 Token，应立即：

1. 作废旧 token
2. 生成新 token
3. 检查仓库历史是否有泄露
4. 检查本机是否残留认证文件

## 5. 群聊与远程访问风险

如果你在 Telegram 群组、公开环境或多用户环境中运行 OpenClaw，请重点关注：

- Prompt Injection 风险
- 高权限工具被误触发
- 文件系统暴露
- 命令执行被滥用
- 多用户之间的数据串扰

建议：

- 群聊默认只开放低风险能力
- 将高风险代理与私人工作区隔离
- 对不同用户/场景使用独立工作区

## 6. 定期审计

建议定期执行：

```bash
openclaw security audit --deep
openclaw update status
```

并重点留意：

- 群策略是否过宽
- Gateway 是否暴露
- elevated 工具是否收敛
- 认证文件是否被误提交

## 7. GitHub 使用建议

若仓库是公开的：

- 不要提交包含真实 token 的示例
- 提供 `.env.example` 而不是 `.env`
- 清理提交历史中的敏感信息
- 开启 GitHub Secret Scanning（如可用）

## 8. 事故处置建议

若发现仓库或聊天中已有敏感信息泄露：

1. 先撤销凭证
2. 再清理本地与仓库残留
3. 检查自动化服务是否仍持有旧凭证
4. 重新生成凭证并最小化授权
5. 必要时重建受影响的集成

---

安全不是一次性工作，而是持续收敛风险的过程。对 OpenClaw 这类高能力代理系统尤应如此。
