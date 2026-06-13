#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

TITLE="${TITLE:-Curl Created Post $(date +%s)}"
BODY="${BODY:-This post was created by the curl endpoint test script.}"
THREAD_ID="${THREAD_ID:-1}"

print_request "POST" "$BASE_URL/api/posts"

curl -i -X POST "$BASE_URL/api/posts" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"title\": \"$TITLE\",
          \"body\": \"$BODY\",
          \"threadId\": $THREAD_ID
        }"
