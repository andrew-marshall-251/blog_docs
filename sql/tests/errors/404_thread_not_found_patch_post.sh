#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 THREAD_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

POST_ID="${POST_ID:-1}"

print_request "PATCH" "$BASE_URL/api/posts/$POST_ID - missing threadId"

curl -i -X PATCH "$BASE_URL/api/posts/$POST_ID" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "threadId": 999999
        }'
