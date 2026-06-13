#!/usr/bin/env bash

set -euo pipefail

# Expected: 401/403 Security error because this endpoint requires Authorization.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/posts - missing Authorization"

curl -i -X POST "$BASE_URL/api/posts" \
    -H "Content-Type: application/json" \
    -d '{
          "title": "Unauthorized Post",
          "body": "This request has no bearer token.",
          "threadId": 1
        }'
