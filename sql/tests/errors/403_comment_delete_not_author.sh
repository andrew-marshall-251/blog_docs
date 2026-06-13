#!/usr/bin/env bash

set -euo pipefail

# Expected: 403 NOT_AUTHORIZED when reader deletes admin's comment #3.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

COMMENT_ID="${COMMENT_ID:-3}"

print_request "DELETE" "$BASE_URL/api/comments/$COMMENT_ID - non-author"

curl -i -X DELETE "$BASE_URL/api/comments/$COMMENT_ID" \
    -H "$USER_AUTH_HEADER"
