#!/usr/bin/env bash

set -euo pipefail

# Expected: 401/403 Security error from AuthenticationManager.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/auth/login - bad password"

curl -i -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{
          "usernameOrEmail": "admin",
          "password": "wrong-password"
        }'
