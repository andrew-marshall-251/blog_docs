#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

OLD_PASSWORD="${OLD_PASSWORD:-password}"
NEW_PASSWORD="${NEW_PASSWORD:-Password1}"

print_request "PATCH" "$BASE_URL/api/users/me/password"

curl -i -X PATCH "$BASE_URL/api/users/me/password" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"oldPassword\": \"$OLD_PASSWORD\",
          \"newPassword\": \"$NEW_PASSWORD\"
        }"
