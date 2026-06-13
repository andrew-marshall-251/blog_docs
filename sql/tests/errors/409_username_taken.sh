#!/usr/bin/env bash

set -euo pipefail

# Expected: 409 USERNAME_TAKEN.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/users/ - duplicate username"

curl -i -X POST "$BASE_URL/api/users/" \
    -H "Content-Type: application/json" \
    -d '{
          "username": "admin",
          "email": "new-admin-email@example.com",
          "password": "Password1",
          "mascotId": 1,
          "bio": "Duplicate username request."
        }'
