#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

MASCOT_NAME="${MASCOT_NAME:-Curl Mascot $(date +%s)}"
MASCOT_IMG_URL="${MASCOT_IMG_URL:-https://example.com/mascots/curl.png}"

print_request "POST" "$BASE_URL/api/mascots"

curl -i -X POST "$BASE_URL/api/mascots" \
    -H "Content-Type: application/json" \
    -H "$AUTH_HEADER" \
    -d "{
          \"mascotName\": \"$MASCOT_NAME\",
          \"mascotImgUrl\": \"$MASCOT_IMG_URL\"
        }"
