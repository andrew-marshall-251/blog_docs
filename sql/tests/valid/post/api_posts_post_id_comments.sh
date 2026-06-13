#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

POST_ID="${POST_ID:-1}"
COMMENT_BODY="${COMMENT_BODY:-Comment created by curl test script.}"
PARENT_ID="${PARENT_ID:-null}"

print_request "POST" "$BASE_URL/api/posts/$POST_ID/comments"

curl -i -X POST "$BASE_URL/api/posts/$POST_ID/comments" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"commentBody\": \"$COMMENT_BODY\",
          \"parentId\": $PARENT_ID
        }"
