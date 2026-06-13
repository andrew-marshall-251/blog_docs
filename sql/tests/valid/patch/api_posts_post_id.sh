#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

POST_ID="${POST_ID:-1}"
THREAD_ID="${THREAD_ID:-1}"
POST_TITLE="${POST_TITLE:-Updated Post Title}"
POST_BODY="${POST_BODY:-Post body updated by curl test script.}"

print_request "PATCH" "$BASE_URL/api/posts/$POST_ID"

curl -i -X PATCH "$BASE_URL/api/posts/$POST_ID" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"threadId\": $THREAD_ID,
          \"postTitle\": \"$POST_TITLE\",
          \"postBody\": \"$POST_BODY\"
        }"
