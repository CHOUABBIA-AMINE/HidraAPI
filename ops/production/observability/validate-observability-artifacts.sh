#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd)"
prom="${root}/ops/production/observability/prometheus/prometheus.yml.tpl"
rules="${root}/ops/production/observability/prometheus/rules/hidra-alerts.yml"
am="${root}/ops/production/observability/alertmanager/alertmanager.yml.tpl"
grafana="${root}/ops/production/observability/grafana/datasources.yml.tpl"
loki="${root}/ops/production/observability/loki/loki.yml"
exporter="${root}/ops/production/observability/pgbackrest_exporter.py"
exercise="${root}/ops/production/observability/verify-alert-routing.sh"

grep -q 'job_name: hidra-api' "${prom}"
grep -q 'job_name: haproxy' "${prom}"
grep -q 'job_name: patroni' "${prom}"
grep -q 'job_name: pgbackrest' "${prom}"

grep -q 'alert: HidraApiUnavailable' "${rules}"
grep -q 'alert: HidraApiRedundancyDegraded' "${rules}"
grep -q 'alert: HidraHttp5xxCritical' "${rules}"
grep -q 'alert: HidraHttpLatencyCritical' "${rules}"
grep -q 'alert: HidraHikariPoolSaturation' "${rules}"
grep -q 'alert: HidraPostgresWriteAuthorityUnavailable' "${rules}"
grep -q 'alert: HidraBackupCoverageCritical' "${rules}"
grep -q 'alert: HidraWalArchiveContinuityCritical' "${rules}"

grep -q 'operations-critical' "${am}"
grep -q 'database-operations' "${am}"
grep -q 'security-incidents' "${am}"

grep -q 'type: prometheus' "${grafana}"
grep -q 'type: loki' "${grafana}"
grep -q 'schema: v13' "${loki}"

python3 -m py_compile "${exporter}"
bash -n "${exercise}"

if command -v promtool >/dev/null 2>&1; then
  promtool check rules "${rules}"
fi

if command -v amtool >/dev/null 2>&1; then
  amtool check-config "${am}"
fi

echo "Production observability/alerting artifacts passed static validation."
