#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 POST_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

POST_ID="${POST_ID:-999999}"

print_request "PATCH" "$BASE_URL/api/posts/$POST_ID"

curl -i -X PATCH "$BASE_URL/api/posts/$POST_ID" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "postTitle": "Missing post update"
        }'
