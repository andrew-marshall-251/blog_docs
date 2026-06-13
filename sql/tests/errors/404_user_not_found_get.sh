#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 USER_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

USER_ID="${USER_ID:-999999}"

print_request "GET" "$BASE_URL/api/users/$USER_ID"

curl -i -X GET "$BASE_URL/api/users/$USER_ID"
