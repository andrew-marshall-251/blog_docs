#!/usr/bin/env bash

set -euo pipefail

# Expected: 401/403 Security error because the JWT is invalid.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "GET" "$BASE_URL/api/users/me - invalid bearer token"

curl -i -X GET "$BASE_URL/api/users/me" \
    -H "Authorization: Bearer not-a-real-jwt"
