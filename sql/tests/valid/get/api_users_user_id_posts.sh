#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

USER_ID="${USER_ID:-1}"

print_request "GET" "$BASE_URL/api/users/$USER_ID/posts"

curl -i -X GET "$BASE_URL/api/users/$USER_ID/posts" \
    -H "$AUTH_HEADER"
