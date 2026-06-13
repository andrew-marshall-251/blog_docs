#!/usr/bin/env bash

set -u

TEST_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8080}"
AUTH_ENV_FILE="${AUTH_ENV_FILE:-$TEST_ROOT/.auth_env}"

load_auth() {
    if [ -f "$AUTH_ENV_FILE" ]; then
        # shellcheck disable=SC1090
        source "$AUTH_ENV_FILE"
    fi

    if [ -z "${AUTH_HEADER:-}" ]; then
        echo "Missing AUTH_HEADER. Run sql/tests/post/api_auth_login.sh first." >&2
        exit 1
    fi
}

print_request() {
    echo
    echo "$1 $2"
    echo
}
