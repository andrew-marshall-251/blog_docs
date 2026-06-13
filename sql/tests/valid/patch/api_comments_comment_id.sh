#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

COMMENT_ID="${COMMENT_ID:-3}"
COMMENT_BODY="${COMMENT_BODY:-Comment updated by curl test script.}"

print_request "PATCH" "$BASE_URL/api/comments/$COMMENT_ID"

curl -i -X PATCH "$BASE_URL/api/comments/$COMMENT_ID" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"commentBody\": \"$COMMENT_BODY\"
        }"
