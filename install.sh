#!/usr/bin/env bash
set -euo pipefail

REPO_OWNER="daiqiongzhao-bit"
REPO_NAME="Openclaw-TZ"
DEFAULT_BRANCH="main"
INSTALL_DIR_DEFAULT="$HOME/Openclaw-TZ"

log() { printf '\033[1;34m[install]\033[0m %s\n' "$*"; }
warn() { printf '\033[1;33m[warn]\033[0m %s\n' "$*"; }
err() { printf '\033[1;31m[error]\033[0m %s\n' "$*" >&2; }

require_cmd() {
  command -v "$1" >/dev/null 2>&1 || {
    err "缺少命令: $1"
    return 1
  }
}

ensure_dependency() {
  local cmd="$1"
  local hint="$2"
  if ! command -v "$cmd" >/dev/null 2>&1; then
    err "未检测到依赖: $cmd"
    err "$hint"
    exit 1
  fi
}

pick_branch() {
  local branch="${OPENCLAW_TZ_BRANCH:-$DEFAULT_BRANCH}"
  printf '%s' "$branch"
}

clone_or_update_repo() {
  local branch="$1"
  local install_dir="${OPENCLAW_TZ_INSTALL_DIR:-$INSTALL_DIR_DEFAULT}"
  local repo_url="https://github.com/${REPO_OWNER}/${REPO_NAME}.git"

  mkdir -p "$(dirname "$install_dir")"

  if [[ -d "$install_dir/.git" ]]; then
    log "检测到已有仓库，开始更新: $install_dir"
    git -C "$install_dir" fetch origin
    git -C "$install_dir" checkout "$branch"
    git -C "$install_dir" pull --ff-only origin "$branch"
  else
    if [[ -e "$install_dir" && ! -d "$install_dir/.git" ]]; then
      err "目标目录已存在但不是 git 仓库: $install_dir"
      err "请先清理该目录，或设置 OPENCLAW_TZ_INSTALL_DIR 到新的目录"
      exit 1
    fi
    log "开始克隆仓库到: $install_dir"
    git clone --branch "$branch" "$repo_url" "$install_dir"
  fi

  printf '%s' "$install_dir"
}

check_optional_runtime() {
  if command -v docker >/dev/null 2>&1; then
    log "已检测到 Docker: $(docker --version | head -n 1)"
  else
    warn "未检测到 Docker；若你计划使用 compose 初始化容器，可后续自行安装"
  fi

  if command -v docker >/dev/null 2>&1 && docker compose version >/dev/null 2>&1; then
    log "已检测到 Docker Compose 插件"
  else
    warn "未检测到 docker compose；本次仍可走宿主机脚本初始化"
  fi
}

main() {
  ensure_dependency bash "请先安装 bash"
  ensure_dependency git "请先安装 git 后重试"
  ensure_dependency python3 "请先安装 Python 3 后重试"

  local branch
  branch="$(pick_branch)"
  log "安装分支: $branch"

  check_optional_runtime

  local install_dir
  install_dir="$(clone_or_update_repo "$branch")"

  chmod +x "$install_dir/bin/init-openclaw-tz.sh" "$install_dir/bin/verify-openclaw-tz.sh"
  log "开始执行工作区初始化脚本"
  (
    cd "$install_dir"
    bash "$install_dir/bin/init-openclaw-tz.sh"
  )

  log "开始执行安装后验收脚本"
  (
    cd "$install_dir"
    bash "$install_dir/bin/verify-openclaw-tz.sh" || warn "验收脚本发现问题，请根据输出继续排查"
  )

  cat <<EOF

安装完成。

工作区目录：$install_dir
远程一键命令：
  curl -fsSL https://raw.githubusercontent.com/${REPO_OWNER}/${REPO_NAME}/$branch/install.sh | bash

如需自定义：
  OPENCLAW_TZ_INSTALL_DIR=/your/path curl -fsSL https://raw.githubusercontent.com/${REPO_OWNER}/${REPO_NAME}/$branch/install.sh | bash
  OPENCLAW_AGENT_ID=my-agent curl -fsSL https://raw.githubusercontent.com/${REPO_OWNER}/${REPO_NAME}/$branch/install.sh | bash
  OPENCLAW_RESTART_GATEWAY=0 curl -fsSL https://raw.githubusercontent.com/${REPO_OWNER}/${REPO_NAME}/$branch/install.sh | bash
EOF
}

main "$@"
