#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 COMMENT_NOT_FOUND.
# Note: current service reports the post id in the message instead of missing parentId.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

POST_ID="${POST_ID:-1}"

print_request "POST" "$BASE_URL/api/posts/$POST_ID/comments - missing parentId"

curl -i -X POST "$BASE_URL/api/posts/$POST_ID/comments" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "commentBody": "This reply targets a missing parent comment.",
          "parentId": 999999
        }'
