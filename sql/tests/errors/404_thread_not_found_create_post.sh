#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 THREAD_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/posts - missing threadId"

curl -i -X POST "$BASE_URL/api/posts" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "title": "Post with missing thread",
          "body": "This request references a thread that does not exist.",
          "threadId": 999999
        }'
