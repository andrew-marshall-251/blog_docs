#!/usr/bin/env bash

set -euo pipefail

# Expected: 403 NOT_AUTHORIZED when reader updates admin's comment #3.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

COMMENT_ID="${COMMENT_ID:-3}"

print_request "PATCH" "$BASE_URL/api/comments/$COMMENT_ID - non-author"

curl -i -X PATCH "$BASE_URL/api/comments/$COMMENT_ID" \
    -H "Content-Type: application/json" \
    -H "$USER_AUTH_HEADER" \
    -d '{
          "commentBody": "A non-author should not be allowed to edit this."
        }'
