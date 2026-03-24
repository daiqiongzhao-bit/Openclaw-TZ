#!/usr/bin/env bash
set -euo pipefail

echo "[1/4] Checking OpenClaw availability..."
if ! command -v openclaw >/dev/null 2>&1; then
  echo "OpenClaw not found. Please install OpenClaw first."
  exit 1
fi

echo "[2/4] Workspace: $(pwd)"
echo "Please ensure your ~/.openclaw/openclaw.json points taizi.workspace to this directory."

echo "[3/4] OpenClaw status"
openclaw status || true

echo "[4/4] Done"
echo
echo "Next steps:"
echo "  1. Edit ~/.openclaw/openclaw.json"
echo "  2. Set taizi.workspace to $(pwd)"
echo "  3. Run: openclaw gateway restart"
echo "  4. Test by sending a message to taizi"
