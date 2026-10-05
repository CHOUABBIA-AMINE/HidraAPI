#!/usr/bin/env bash
set -euo pipefail

sha="${1:-}"
artifact="${2:-}"
expected_sha256="${3:-}"

[[ "${sha}" =~ ^[0-9a-f]{40}$ ]] || exit 2
[[ -f "${artifact}" ]] || exit 2
[[ "${expected_sha256}" =~ ^[0-9a-f]{64}$ ]] || exit 2

actual_sha256="$(sha256sum "${artifact}" | awk '{print $1}')"
[[ "${actual_sha256}" == "${expected_sha256}" ]] || { echo "artifact checksum mismatch" >&2; exit 1; }

grep -qx 'SPRING_PROFILES_ACTIVE=production' /etc/hidra/hidra.env || { echo "production profile marker missing" >&2; exit 1; }
grep -qx 'HIDRA_ENVIRONMENT=production' /etc/hidra/hidra.env || { echo "production environment marker missing" >&2; exit 1; }
[[ -s /run/hidra/hidra-secrets.env ]] || { echo "Vault-backed runtime secret file missing" >&2; exit 1; }
grep -qx 'HIDRA_SECRETS_SOURCE=vault' /run/hidra/hidra-secrets.env || { echo "Vault source marker missing" >&2; exit 1; }

release_dir="/opt/hidra/releases/${sha}"
install -d -o hidra -g hidra -m 0750 "${release_dir}"
install -o hidra -g hidra -m 0640 "${artifact}" "${release_dir}/hidra.jar"
printf '%s\n' "${expected_sha256}" > "${release_dir}/hidra.jar.sha256"
chown hidra:hidra "${release_dir}/hidra.jar.sha256"
chmod 0640 "${release_dir}/hidra.jar.sha256"

if [[ -L /opt/hidra/current ]]; then
  previous="$(readlink -f /opt/hidra/current || true)"
  if [[ -n "${previous}" && -d "${previous}" && "${previous}" != "${release_dir}" ]]; then
    ln -sfn "${previous}" /opt/hidra/previous
  fi
fi

systemctl stop hidra-api
ln -sfn "${release_dir}" /opt/hidra/current
systemctl start hidra-api

for attempt in $(seq 1 60); do
  if curl --fail --silent http://127.0.0.1:8080/actuator/health/readiness >/dev/null; then exit 0; fi
  [[ "${attempt}" -lt 60 ]] || { systemctl status hidra-api --no-pager || true; exit 1; }
  sleep 2
done
