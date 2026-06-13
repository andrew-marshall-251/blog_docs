#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

COMMENT_ID="${COMMENT_ID:-3}"

print_request "DELETE" "$BASE_URL/api/comments/$COMMENT_ID"

curl -i -X DELETE "$BASE_URL/api/comments/$COMMENT_ID" \
    -H "$AUTH_HEADER"
