#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 USER_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/auth/login - unknown username/email"

curl -i -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{
          "usernameOrEmail": "missing-user",
          "password": "password"
        }'
