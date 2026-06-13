#!/usr/bin/env bash

set -euo pipefail

# Expected: 400 Bad Request from bean validation.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/users/ - invalid CreateUserRequest"

curl -i -X POST "$BASE_URL/api/users/" \
    -H "Content-Type: application/json" \
    -d '{
          "username": "no",
          "email": "not-an-email",
          "password": "weak",
          "mascotId": null,
          "bio": ""
        }'
