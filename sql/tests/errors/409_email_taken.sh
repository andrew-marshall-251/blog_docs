#!/usr/bin/env bash

set -euo pipefail

# Expected: 409 EMAIL_TAKEN.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

USERNAME="${USERNAME:-unique_email_test_$(date +%s)}"

print_request "POST" "$BASE_URL/api/users/ - duplicate email"

curl -i -X POST "$BASE_URL/api/users/" \
    -H "Content-Type: application/json" \
    -d "{
          \"username\": \"$USERNAME\",
          \"email\": \"admin@example.com\",
          \"password\": \"Password1\",
          \"mascotId\": 1,
          \"bio\": \"Duplicate email request.\"
        }"
