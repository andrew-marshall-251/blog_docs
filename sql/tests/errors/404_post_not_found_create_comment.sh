#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 POST_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

POST_ID="${POST_ID:-999999}"

print_request "POST" "$BASE_URL/api/posts/$POST_ID/comments"

curl -i -X POST "$BASE_URL/api/posts/$POST_ID/comments" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "commentBody": "This comment targets a missing post.",
          "parentId": null
        }'
