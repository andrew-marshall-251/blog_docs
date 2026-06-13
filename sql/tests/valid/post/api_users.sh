#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"

USERNAME="${USERNAME:-tester_$(date +%s)}"
EMAIL="${EMAIL:-$USERNAME@example.com}"
PASSWORD="${PASSWORD:-Password1}"
MASCOT_ID="${MASCOT_ID:-1}"
BIO="${BIO:-Created from curl test script.}"

print_request "POST" "$BASE_URL/api/users/"

curl -i -X POST "$BASE_URL/api/users/" \
    -H "Content-Type: application/json" \
    -d "{
          \"username\": \"$USERNAME\",
          \"email\": \"$EMAIL\",
          \"password\": \"$PASSWORD\",
          \"mascotId\": $MASCOT_ID,
          \"bio\": \"$BIO\"
        }"
