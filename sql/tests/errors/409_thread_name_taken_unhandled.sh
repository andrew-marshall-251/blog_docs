#!/usr/bin/env bash

set -euo pipefail

# Expected with current code: likely 500 database unique constraint error.
# There is no custom ThreadNameAlreadyTakenException handler yet.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/_error_common.sh"

print_request "POST" "$BASE_URL/api/threads - duplicate thread name"

curl -i -X POST "$BASE_URL/api/threads" \
    -H "Content-Type: application/json" \
    -H "$ADMIN_AUTH_HEADER" \
    -d '{
          "threadName": "Announcements"
        }'
