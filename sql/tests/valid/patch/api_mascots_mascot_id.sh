#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

MASCOT_ID="${MASCOT_ID:-3}"
MASCOT_NAME="${MASCOT_NAME:-Updated Orbit}"
MASCOT_IMG_URL="${MASCOT_IMG_URL:-https://example.com/mascots/updated-orbit.png}"

print_request "PATCH" "$BASE_URL/api/mascots/$MASCOT_ID"

curl -i -X PATCH "$BASE_URL/api/mascots/$MASCOT_ID" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"mascotName\": \"$MASCOT_NAME\",
          \"mascotImgUrl\": \"$MASCOT_IMG_URL\"
        }"
