#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

print_request "GET" "$BASE_URL/api/users/me/posts"

curl -i -X GET "$BASE_URL/api/users/me/posts" \
    -H "$AUTH_HEADER"
