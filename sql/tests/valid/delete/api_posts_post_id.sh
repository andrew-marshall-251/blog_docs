#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"
load_auth

POST_ID="${POST_ID:-3}"

print_request "DELETE" "$BASE_URL/api/posts/$POST_ID"

curl -i -X DELETE "$BASE_URL/api/posts/$POST_ID" \
    -H "$AUTH_HEADER"
