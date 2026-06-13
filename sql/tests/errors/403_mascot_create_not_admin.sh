#!/usr/bin/env bash

set -euo pipefail

# Expected: 403 NOT_AUTHORIZED.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/mascots - non-admin user"

curl -i -X POST "$BASE_URL/api/mascots" \
    -H "Content-Type: application/json" \
    -H "$USER_AUTH_HEADER" \
    -d '{
          "mascotName": "Forbidden Mascot",
          "mascotImgUrl": "https://example.com/forbidden.png"
        }'
