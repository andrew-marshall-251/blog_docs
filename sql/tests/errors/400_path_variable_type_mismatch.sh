#!/usr/bin/env bash

set -euo pipefail

# Expected: 400 Bad Request because user_id must be a Long.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "GET" "$BASE_URL/api/users/not-a-number"

curl -i -X GET "$BASE_URL/api/users/not-a-number"
