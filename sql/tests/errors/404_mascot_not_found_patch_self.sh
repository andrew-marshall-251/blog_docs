#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 MASCOT_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "PATCH" "$BASE_URL/api/users/me - missing mascotId"

curl -i -X PATCH "$BASE_URL/api/users/me" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "mascotId": 999999
        }'
