#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"

THREAD_ID="${THREAD_ID:-1}"

print_request "GET" "$BASE_URL/api/threads/$THREAD_ID/posts"

curl -i -X GET "$BASE_URL/api/threads/$THREAD_ID/posts"
