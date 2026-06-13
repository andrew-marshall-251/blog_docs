#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 COMMENT_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

COMMENT_ID="${COMMENT_ID:-999999}"

print_request "PATCH" "$BASE_URL/api/comments/$COMMENT_ID"

curl -i -X PATCH "$BASE_URL/api/comments/$COMMENT_ID" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "commentBody": "Missing comment update"
        }'
