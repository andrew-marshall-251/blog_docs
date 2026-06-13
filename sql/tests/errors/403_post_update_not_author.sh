#!/usr/bin/env bash

set -euo pipefail

# Expected: 403 NOT_AUTHORIZED when reader updates admin's post #1.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

POST_ID="${POST_ID:-1}"

print_request "PATCH" "$BASE_URL/api/posts/$POST_ID - non-author"

curl -i -X PATCH "$BASE_URL/api/posts/$POST_ID" \
    -H "Content-Type: application/json" \
    -H "$USER_AUTH_HEADER" \
    -d '{
          "threadId": 1,
          "postTitle": "Forbidden edit",
          "postBody": "A non-author should not be allowed to edit this."
        }'
