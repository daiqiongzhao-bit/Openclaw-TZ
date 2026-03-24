#!/usr/bin/env bash
set -euo pipefail

WORKSPACE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OPENCLAW_DIR="${OPENCLAW_DIR:-$HOME/.openclaw}"
OPENCLAW_CONFIG="${OPENCLAW_CONFIG:-$OPENCLAW_DIR/openclaw.json}"
AGENT_ID="${OPENCLAW_AGENT_ID:-taizi}"
GATEWAY_PORT="${OPENCLAW_GATEWAY_PORT:-18789}"
FAILURES=0
DASHBOARD_LINK=""

info() { printf '\033[1;34m[verify]\033[0m %s\n' "$*"; }
pass() { printf '\033[1;32m[PASS]\033[0m %s\n' "$*"; }
warn() { printf '\033[1;33m[WARN]\033[0m %s\n' "$*"; }
fail() { printf '\033[1;31m[FAIL]\033[0m %s\n' "$*" >&2; FAILURES=$((FAILURES + 1)); }

check_command() {
  local cmd="$1"
  if command -v "$cmd" >/dev/null 2>&1; then
    pass "已检测到命令: $cmd"
  else
    fail "缺少命令: $cmd"
  fi
}

check_file() {
  local path="$1"
  local label="$2"
  if [[ -f "$path" ]]; then
    pass "$label 存在: $path"
  else
    fail "$label 不存在: $path"
  fi
}

check_workspace_binding() {
  if [[ ! -f "$OPENCLAW_CONFIG" ]]; then
    fail "OpenClaw 配置不存在，无法检查 workspace 绑定: $OPENCLAW_CONFIG"
    return 0
  fi

  if python3 - "$OPENCLAW_CONFIG" "$AGENT_ID" "$WORKSPACE_DIR" <<'PY'
import json, sys
path, agent_id, workspace = sys.argv[1:4]
with open(path, 'r', encoding='utf-8') as f:
    data = json.load(f)
for agent in data.get('agents', {}).get('list', []):
    if agent.get('id') == agent_id and agent.get('workspace') == workspace:
        raise SystemExit(0)
raise SystemExit(1)
PY
  then
    pass "代理 $AGENT_ID 已绑定到当前工作区"
  else
    fail "代理 $AGENT_ID 未正确绑定到当前工作区 $WORKSPACE_DIR"
  fi
}

check_status() {
  if ! command -v openclaw >/dev/null 2>&1; then
    fail "未检测到 openclaw，无法执行状态检查"
    return 0
  fi

  local status_output
  if status_output="$(openclaw status 2>&1)"; then
    pass "openclaw status 执行成功"
    printf '%s\n' "$status_output" | tail -n 20
  else
    fail "openclaw status 执行失败"
    printf '%s\n' "$status_output"
  fi
}

check_gateway_status() {
  if ! command -v openclaw >/dev/null 2>&1; then
    return 0
  fi

  local gateway_output
  if gateway_output="$(openclaw gateway status 2>&1)"; then
    pass "openclaw gateway status 执行成功"
    printf '%s\n' "$gateway_output" | tail -n 12
  else
    fail "openclaw gateway status 执行失败"
    printf '%s\n' "$gateway_output"
  fi
}

check_dashboard_link() {
  if ! command -v openclaw >/dev/null 2>&1; then
    return 0
  fi

  DASHBOARD_LINK="$(openclaw dashboard --no-open 2>/dev/null | grep -Eo 'https?://[^[:space:]]+' | head -n 1 || true)"
  if [[ -n "$DASHBOARD_LINK" ]]; then
    pass "已获取 dashboard 链接"
    printf '%s\n' "Dashboard: $DASHBOARD_LINK"
  else
    warn "未能直接提取 dashboard 链接，可尝试手动执行: openclaw dashboard --no-open"
    printf '%s\n' "Fallback: http://127.0.0.1:${GATEWAY_PORT}/"
  fi
}

check_env_hints() {
  local env_file="$WORKSPACE_DIR/.env"
  if [[ -f "$env_file" ]]; then
    pass ".env 已存在"
    if grep -q '^TELEGRAM_BOT_TOKEN=' "$env_file"; then
      pass ".env 中存在 TELEGRAM_BOT_TOKEN 配置项"
    else
      warn ".env 中未发现 TELEGRAM_BOT_TOKEN 配置项"
    fi
    if grep -q '^OPENAI_API_KEY=' "$env_file"; then
      pass ".env 中存在 OPENAI_API_KEY 配置项"
    else
      warn ".env 中未发现 OPENAI_API_KEY 配置项"
    fi
  else
    fail ".env 不存在：$env_file"
  fi
}

main() {
  info "开始验证 OpenClaw-TZ 安装闭环"
  check_command python3
  check_command git
  check_command openclaw
  check_file "$WORKSPACE_DIR/bin/init-openclaw-tz.sh" "初始化脚本"
  check_file "$WORKSPACE_DIR/install.sh" "安装入口脚本"
  check_file "$OPENCLAW_CONFIG" "OpenClaw 配置"
  check_workspace_binding
  check_env_hints
  check_status
  check_gateway_status
  check_dashboard_link

  if [[ "$FAILURES" -gt 0 ]]; then
    printf '\n'
    fail "验收未通过：共 $FAILURES 项失败"
    exit 1
  fi

  printf '\n'
  pass "验收通过：安装入口、工作区绑定、状态检查、dashboard 链接均已打通"
}

main "$@"
