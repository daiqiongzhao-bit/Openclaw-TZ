#!/usr/bin/env bash
set -euo pipefail

WORKSPACE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OPENCLAW_DIR="${OPENCLAW_DIR:-$HOME/.openclaw}"
OPENCLAW_CONFIG="${OPENCLAW_CONFIG:-$OPENCLAW_DIR/openclaw.json}"
AGENT_ID="${OPENCLAW_AGENT_ID:-taizi}"
ENV_FILE="$WORKSPACE_DIR/.env"
ENV_EXAMPLE="$WORKSPACE_DIR/.env.example"
GATEWAY_PORT="${OPENCLAW_GATEWAY_PORT:-18789}"
DASHBOARD_LINK=""
LOCAL_IPS=""

log() { printf '\033[1;34m[OpenClaw-TZ]\033[0m %s\n' "$*"; }
warn() { printf '\033[1;33m[WARN]\033[0m %s\n' "$*"; }
err() { printf '\033[1;31m[ERROR]\033[0m %s\n' "$*" >&2; }

require_cmd() {
  command -v "$1" >/dev/null 2>&1 || {
    err "缺少命令: $1"
    return 1
  }
}

ensure_env_file() {
  if [[ -f "$ENV_FILE" ]]; then
    log ".env 已存在，跳过创建"
  elif [[ -f "$ENV_EXAMPLE" ]]; then
    cp "$ENV_EXAMPLE" "$ENV_FILE"
    log "已从 .env.example 创建 .env，请按需填写你的私有配置"
  else
    warn "未找到 .env.example，跳过 .env 初始化"
  fi
}

ensure_openclaw_dir() {
  mkdir -p "$OPENCLAW_DIR"
}

ensure_openclaw_config() {
  if [[ -f "$OPENCLAW_CONFIG" ]]; then
    log "检测到 OpenClaw 配置: $OPENCLAW_CONFIG"
    return 0
  fi

  cat > "$OPENCLAW_CONFIG" <<JSON
{
  "agents": {
    "list": [
      {
        "id": "$AGENT_ID",
        "workspace": "$WORKSPACE_DIR"
      }
    ]
  }
}
JSON
  warn "未找到现有 openclaw.json，已生成最小示例: $OPENCLAW_CONFIG"
  warn "请根据你的实际模型、渠道、工具权限补全该配置。"
}

patch_workspace_with_python() {
  python3 - "$OPENCLAW_CONFIG" "$AGENT_ID" "$WORKSPACE_DIR" <<'PY'
import json, sys
path, agent_id, workspace = sys.argv[1:4]
with open(path, 'r', encoding='utf-8') as f:
    data = json.load(f)

agents = data.setdefault('agents', {})
agent_list = agents.setdefault('list', [])
for agent in agent_list:
    if agent.get('id') == agent_id:
        agent['workspace'] = workspace
        break
else:
    agent_list.append({'id': agent_id, 'workspace': workspace})

with open(path, 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=2)
    f.write('\n')
PY
}

ensure_agent_workspace_binding() {
  require_cmd python3
  patch_workspace_with_python
  log "已确保代理 $AGENT_ID 指向工作区: $WORKSPACE_DIR"
}

collect_local_ips() {
  if command -v hostname >/dev/null 2>&1; then
    LOCAL_IPS="$(hostname -I 2>/dev/null | tr ' ' '\n' | awk 'NF' | grep -v '^127\.' | paste -sd ', ' - || true)"
  fi

  if [[ -z "$LOCAL_IPS" ]] && command -v ip >/dev/null 2>&1; then
    LOCAL_IPS="$(ip -4 addr show scope global 2>/dev/null | awk '/inet / {print $2}' | cut -d/ -f1 | paste -sd ', ' - || true)"
  fi
}

collect_dashboard_link() {
  if ! command -v openclaw >/dev/null 2>&1; then
    return 0
  fi

  DASHBOARD_LINK="$(openclaw dashboard --no-open 2>/dev/null | grep -Eo 'https?://[^[:space:]]+' | head -n 1 || true)"
}

maybe_restart_gateway() {
  if ! command -v openclaw >/dev/null 2>&1; then
    warn "未检测到 openclaw 命令，跳过状态检查与重启"
    return 0
  fi

  log "检查 OpenClaw 状态"
  openclaw status || true
  openclaw gateway status || true

  if [[ "${OPENCLAW_RESTART_GATEWAY:-1}" == "1" ]]; then
    log "尝试重启 Gateway"
    openclaw gateway restart || warn "Gateway 重启失败，请手动执行: openclaw gateway restart"
  else
    warn "已跳过 Gateway 重启（OPENCLAW_RESTART_GATEWAY=0）"
  fi
}

print_access_summary() {
  collect_local_ips
  collect_dashboard_link

  cat <<EOF

初始化完成。建议继续执行：
1. 编辑 $ENV_FILE，填入你自己的私有变量（如模型服务、Telegram Token）
2. 检查 $OPENCLAW_CONFIG 中的模型、渠道与安全配置
3. 用 Telegram / 本地渠道给 $AGENT_ID 发一条测试消息

访问入口：
- 推荐：运行 openclaw dashboard 获取并打开带 token 的控制台链接
EOF

  if [[ -n "$DASHBOARD_LINK" ]]; then
    printf '%s\n' "- 当前 dashboard 链接：$DASHBOARD_LINK"
  else
    printf '%s\n' "- 本地控制台：http://127.0.0.1:${GATEWAY_PORT}/"
  fi

  printf '%s\n' "- 本地回环地址：http://127.0.0.1:${GATEWAY_PORT}/"

  if [[ -n "$LOCAL_IPS" ]]; then
    while IFS= read -r ip; do
      ip="$(printf '%s' "$ip" | xargs)"
      [[ -n "$ip" ]] && printf '%s\n' "- 局域网候选地址：http://${ip}:${GATEWAY_PORT}/"
    done <<< "$LOCAL_IPS"
  else
    printf '%s\n' "- 未探测到可用局域网 IPv4 地址"
  fi

  cat <<EOF

说明：
- 若浏览器提示 unauthorized，请直接运行 openclaw dashboard，并使用其中带 token 的链接。
- 若你后续接了反向代理、域名或 Tailscale，请以那条外部可达地址为准。

常用命令：
  openclaw dashboard --no-open
  openclaw status
  openclaw gateway status
  openclaw gateway restart
EOF
}

main() {
  log "开始初始化 OpenClaw-TZ 工作区"
  log "工作区目录: $WORKSPACE_DIR"

  ensure_env_file
  ensure_openclaw_dir
  ensure_openclaw_config
  ensure_agent_workspace_binding
  maybe_restart_gateway
  print_access_summary
}

main "$@"
