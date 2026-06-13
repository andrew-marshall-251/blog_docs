#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

USERNAME="${USERNAME:-admin}"
EMAIL="${EMAIL:-admin@example.com}"
MASCOT_ID="${MASCOT_ID:-1}"
BIO="${BIO:-Updated from curl test script.}"

print_request "PATCH" "$BASE_URL/api/users/me"

curl -i -X PATCH "$BASE_URL/api/users/me" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"username\": \"$USERNAME\",
          \"email\": \"$EMAIL\",
          \"mascotId\": $MASCOT_ID,
          \"bio\": \"$BIO\"
        }"
