#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../../common.sh"

USERNAME_OR_EMAIL="${USERNAME_OR_EMAIL:-admin}"
PASSWORD="${PASSWORD:-password}"

print_request "POST" "$BASE_URL/api/auth/login"

RESPONSE="$(
    curl -sS -X POST "$BASE_URL/api/auth/login" \
        -H "Content-Type: application/json" \
        -d "{
              \"usernameOrEmail\": \"$USERNAME_OR_EMAIL\",
              \"password\": \"$PASSWORD\"
            }"
)"

printf '%s\n' "$RESPONSE"

TOKEN="$(printf '%s' "$RESPONSE" | sed -n 's/.*"accessToken"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p')"

if [ -z "$TOKEN" ]; then
    echo "Login response did not contain accessToken." >&2
    exit 1
fi

export AUTH_TOKEN="$TOKEN"
export AUTH_HEADER="Authorization: Bearer $AUTH_TOKEN"

cat > "$AUTH_ENV_FILE" <<EOF
export AUTH_TOKEN='$AUTH_TOKEN'
export AUTH_HEADER='Authorization: Bearer $AUTH_TOKEN'
EOF

echo
echo "Stored AUTH_HEADER in $AUTH_ENV_FILE"
