#!/usr/bin/env bash

set -euo pipefail

# Expected with current code: 500 NullPointerException.
# CommentServiceImpl.updateComment calls comment.getParent().getId() without a null check.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

COMMENT_ID="${COMMENT_ID:-3}"

print_request "PATCH" "$BASE_URL/api/comments/$COMMENT_ID - parent is null"

curl -i -X PATCH "$BASE_URL/api/comments/$COMMENT_ID" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "commentBody": "This should expose the null parent response bug."
        }'
