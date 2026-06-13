#!/usr/bin/env bash

set -euo pipefail

# Expected with current code: broken/empty response because MeController.getSelf returns null.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "GET" "$BASE_URL/api/users/me - controller returns null"

curl -i -X GET "$BASE_URL/api/users/me" \
    -H "$ADMIN_AUTH_HEADER"
