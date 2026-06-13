#!/usr/bin/env bash

set -u

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TEST_ROOT="$ROOT_DIR/tests"
AUTH_ENV_FILE="${AUTH_ENV_FILE:-$TEST_ROOT/.auth_env}"
BASE_URL="${BASE_URL:-http://localhost:8080}"

export AUTH_ENV_FILE
export BASE_URL

scripts=()

while IFS= read -r script; do
    case "$script" in
        */common.sh|*/_error_common.sh)
            continue
            ;;
        *)
            scripts+=("$script")
            ;;
    esac
done < <(find "$TEST_ROOT" -type f -name '*.sh' | sort)

bootstrap_login="$TEST_ROOT/valid/post/api_auth_login.sh"

run_script() {
    local script_path="$1"
    local label="$2"
    local tmp_output
    local status
    local started_at
    local ended_at
    local elapsed

    tmp_output="$(mktemp)"
    started_at="$(date +%s)"

    printf '\n[%s] %s\n' "$label" "${script_path#$ROOT_DIR/}"
    printf '%s\n' "------------------------------------------------------------"

    if bash "$script_path" >"$tmp_output" 2>&1; then
        status=0
    else
        status=$?
    fi

    cat "$tmp_output"
    rm -f "$tmp_output"

    ended_at="$(date +%s)"
    elapsed=$((ended_at - started_at))

    if [ "$status" -eq 0 ]; then
        printf '%s\n' "------------------------------------------------------------"
        printf '[PASS] %s (%ss)\n' "${script_path#$ROOT_DIR/}" "$elapsed"
        return 0
    fi

    printf '%s\n' "------------------------------------------------------------"
    printf '[FAIL] %s (%ss, exit %s)\n' "${script_path#$ROOT_DIR/}" "$elapsed" "$status"
    return "$status"
}

passed=0
failed=0

printf 'Running API test scripts from %s\n' "$TEST_ROOT"
printf 'Base URL: %s\n' "$BASE_URL"
printf 'Auth env: %s\n' "$AUTH_ENV_FILE"

if [ -x "$bootstrap_login" ]; then
    printf '\nBootstrapping auth with %s\n' "${bootstrap_login#$ROOT_DIR/}"
    if bash "$bootstrap_login" >/dev/null 2>&1; then
        printf 'Auth bootstrap complete.\n'
    else
        printf 'Auth bootstrap failed. Remaining auth-dependent scripts may fail.\n'
    fi
else
    printf 'Login bootstrap script not found or not executable.\n'
fi

for script in "${scripts[@]}"; do
    if run_script "$script" "$((passed + failed + 1))"; then
        passed=$((passed + 1))
    else
        failed=$((failed + 1))
    fi
done

printf '\nSummary\n'
printf 'Passed: %s\n' "$passed"
printf 'Failed: %s\n' "$failed"
printf 'Total:  %s\n' "$((passed + failed))"

if [ "$failed" -ne 0 ]; then
    exit 1
fi
