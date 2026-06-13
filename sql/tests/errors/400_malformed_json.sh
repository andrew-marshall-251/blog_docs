#!/usr/bin/env bash

set -euo pipefail

# Expected: 400 Bad Request from unreadable JSON.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/auth/login - malformed JSON"

curl -i -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{"usernameOrEmail": "admin", "password": '
