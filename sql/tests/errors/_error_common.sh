#!/usr/bin/env bash

set -u

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# shellcheck disable=SC1091
source "$SCRIPT_DIR/../common.sh"

login_header() {
    local username_or_email="${1:-admin}"
    local password="${2:-password}"
    local response
    local token

    response="$(
        curl -sS -X POST "$BASE_URL/api/auth/login" \
            -H "Content-Type: application/json" \
            -d "{
                  \"usernameOrEmail\": \"$username_or_email\",
                  \"password\": \"$password\"
                }"
    )"

    token="$(printf '%s' "$response" | sed -n 's/.*"accessToken"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p')"

    if [ -z "$token" ]; then
        echo "Could not log in as $username_or_email. Login response:" >&2
        printf '%s\n' "$response" >&2
        exit 1
    fi

    printf 'Authorization: Bearer %s' "$token"
}

ADMIN_AUTH_HEADER="$(login_header "${ADMIN_USERNAME:-admin}" "${ADMIN_PASSWORD:-password}")"
USER_AUTH_HEADER="$(login_header "${USER_USERNAME:-reader}" "${USER_PASSWORD:-password}")"
