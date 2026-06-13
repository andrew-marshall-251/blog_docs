#!/usr/bin/env bash

set -euo pipefail

# Expected: 409 MASCOT_NAME_TAKEN.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/mascots - duplicate mascot name"

curl -i -X POST "$BASE_URL/api/mascots" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "mascotName": "Byte",
          "mascotImgUrl": "https://example.com/mascots/duplicate-byte.png"
        }'
