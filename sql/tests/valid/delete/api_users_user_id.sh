#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

USER_ID="${USER_ID:-3}"

print_request "DELETE" "$BASE_URL/api/users/$USER_ID"

curl -i -X DELETE "$BASE_URL/api/users/$USER_ID" \
    -H "$AUTH_HEADER"
