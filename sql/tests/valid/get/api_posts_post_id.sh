#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"

POST_ID="${POST_ID:-1}"

print_request "GET" "$BASE_URL/api/posts/$POST_ID"

curl -i -X GET "$BASE_URL/api/posts/$POST_ID"
