#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

THREAD_ID="${THREAD_ID:-3}"
THREAD_NAME="${THREAD_NAME:-Updated General}"

print_request "PATCH" "$BASE_URL/api/threads/$THREAD_ID"

curl -i -X PATCH "$BASE_URL/api/threads/$THREAD_ID" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"threadName\": \"$THREAD_NAME\"
        }"
