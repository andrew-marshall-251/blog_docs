#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

THREAD_NAME="${THREAD_NAME:-Curl Thread $(date +%s)}"

print_request "POST" "$BASE_URL/api/threads"

curl -i -X POST "$BASE_URL/api/threads" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"threadName\": \"$THREAD_NAME\"
        }"
