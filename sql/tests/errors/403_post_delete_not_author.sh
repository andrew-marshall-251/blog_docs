#!/usr/bin/env bash

set -euo pipefail

# Expected: 403 NOT_AUTHORIZED when reader deletes admin's post #1.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

POST_ID="${POST_ID:-1}"

print_request "DELETE" "$BASE_URL/api/posts/$POST_ID - non-author"

curl -i -X DELETE "$BASE_URL/api/posts/$POST_ID" \
    -H "$USER_AUTH_HEADER"
