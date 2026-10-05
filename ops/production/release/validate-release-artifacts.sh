#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd)"
workflow="${root}/.github/workflows/release.yml"
unit="${root}/ops/production/systemd/hidra-api.service"

grep -q '^name: HidraAPI Controlled Production Release$' "${workflow}"
grep -q '^      source_sha:$' "${workflow}"
grep -q '^      deployment_mode:$' "${workflow}"
grep -q '^      artifact_rollback_compatible:$' "${workflow}"
grep -q '^    environment:$' "${workflow}"
grep -q '^      name: production$' "${workflow}"
grep -q 'Require successful exact-SHA full CI' "${workflow}"
grep -q 'sha256sum -c' "${workflow}"

for script in "${root}"/ops/production/release/*.sh; do bash -n "${script}"; done

grep -q 'HIDRA_SECRETS_SOURCE=vault' "${root}/ops/production/release/install-release.sh"
grep -q 'SPRING_PROFILES_ACTIVE=production' "${root}/ops/production/release/install-release.sh"
grep -q 'HIDRA_ARTIFACT_ROLLBACK_COMPATIBLE' "${root}/ops/production/release/rollback-release.sh"
grep -q 'drain-node.sh' "${root}/ops/production/release/deploy-production-node.sh"
grep -q 'rejoin-node.sh' "${root}/ops/production/release/deploy-production-node.sh"
grep -q '^EnvironmentFile=/run/hidra/hidra-secrets.env$' "${unit}"
grep -q '^ExecStartPre=/usr/bin/test -s /run/hidra/hidra-secrets.env$' "${unit}"

echo "Controlled production release artifacts passed static validation."
