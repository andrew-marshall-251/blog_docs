#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 MASCOT_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

USERNAME="${USERNAME:-missing_mascot_$(date +%s)}"

print_request "POST" "$BASE_URL/api/users/ - missing mascotId"

curl -i -X POST "$BASE_URL/api/users/" \
    -H "Content-Type: application/json" \
    -d "{
          \"username\": \"$USERNAME\",
          \"email\": \"$USERNAME@example.com\",
          \"password\": \"Password1\",
          \"mascotId\": 999999,
          \"bio\": \"This request references a missing mascot.\"
        }"
