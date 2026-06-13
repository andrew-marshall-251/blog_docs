#!/usr/bin/env bash

set -euo pipefail

# Expected: 404 THREAD_NOT_FOUND.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

THREAD_ID="${THREAD_ID:-999999}"

print_request "DELETE" "$BASE_URL/api/threads/$THREAD_ID"

curl -i -X DELETE "$BASE_URL/api/threads/$THREAD_ID" \
    -H "$ADMIN_AUTH_HEADER"
