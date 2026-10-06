#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd)"
prom="${root}/ops/production/observability/prometheus/prometheus.yml.tpl"
prom_service="${root}/ops/production/observability/prometheus/prometheus.service.tpl"
rules="${root}/ops/production/observability/prometheus/rules/hidra-alerts.yml"
am="${root}/ops/production/observability/alertmanager/alertmanager.yml.tpl"
grafana="${root}/ops/production/observability/grafana/datasources.yml.tpl"
loki="${root}/ops/production/observability/loki/loki.yml"
exporter="${root}/ops/production/observability/pgbackrest_exporter.py"
exercise="${root}/ops/production/observability/verify-alert-routing.sh"
app_proxy="${root}/ops/production/haproxy/hidra-api.cfg"
postgres_proxy="${root}/ops/production/postgres/haproxy/hidra-postgres.cfg.tpl"
prod="${root}/src/main/resources/application-production.properties"

grep -Fq 'job_name: hidra-api' "${prom}"
grep -Fq 'credentials_file: __HIDRA_METRICS_BEARER_TOKEN_FILE__' "${prom}"
grep -Fq '__HIDRA_APP_HAPROXY_METRICS__' "${prom}"
grep -Fq '__HIDRA_POSTGRES_HAPROXY_METRICS__' "${prom}"
grep -Fq 'job_name: patroni' "${prom}"
grep -Fq 'job_name: pgbackrest' "${prom}"
grep -Fq -- '--storage.tsdb.retention.time=30d' "${prom_service}"
grep -Fq -- '--storage.tsdb.path=__PROMETHEUS_TSDB_PATH__' "${prom_service}"

grep -Fqx 'management.metrics.distribution.percentiles-histogram.http.server.requests=true' "${prod}"
grep -Fq 'http_server_requests_seconds_bucket' "${rules}"

grep -Fq 'frontend hidra_haproxy_metrics' "${app_proxy}"
grep -Fq 'http-request use-service prometheus-exporter if { path /metrics }' "${app_proxy}"
grep -Fq 'frontend hidra_postgres_haproxy_metrics' "${postgres_proxy}"
grep -Fq '__HIDRA_POSTGRES_HAPROXY_METRICS_BIND__' "${postgres_proxy}"
grep -Fq 'http-request use-service prometheus-exporter if { path /metrics }' "${postgres_proxy}"

grep -Fq 'alert: HidraApiUnavailable' "${rules}"
grep -Fq 'alert: HidraApiRedundancyDegraded' "${rules}"
grep -Fq 'alert: HidraHttp5xxCritical' "${rules}"
grep -Fq 'alert: HidraHttpLatencyCritical' "${rules}"
grep -Fq 'alert: HidraHikariPoolSaturation' "${rules}"
grep -Fq 'alert: HidraPostgresWriteAuthorityUnavailable' "${rules}"
grep -Fq 'alert: HidraBackupCoverageCritical' "${rules}"
grep -Fq 'alert: HidraWalArchiveContinuityCritical' "${rules}"

security_line="$(grep -n 'domain="security"' "${am}" | head -n1 | cut -d: -f1)"
database_line="$(grep -n 'domain="database"' "${am}" | head -n1 | cut -d: -f1)"
backup_line="$(grep -n 'domain="backup"' "${am}" | head -n1 | cut -d: -f1)"
critical_line="$(grep -n 'severity="critical"' "${am}" | head -n1 | cut -d: -f1)"
[[ "${security_line}" -lt "${critical_line}" ]]
[[ "${database_line}" -lt "${critical_line}" ]]
[[ "${backup_line}" -lt "${critical_line}" ]]
grep -Fq 'receiver: operations-critical' "${am}"
grep -Fq 'receiver: database-operations' "${am}"
grep -Fq 'receiver: security-incidents' "${am}"

grep -Fq 'type: prometheus' "${grafana}"
grep -Fq 'type: loki' "${grafana}"
grep -Fq 'schema: v13' "${loki}"
grep -Fq 'retention_period: 2160h' "${loki}"
grep -Fq 'retention_enabled: true' "${loki}"
grep -Fq 'retention_delete_delay: 2h' "${loki}"
grep -Fq 'delete_request_store: filesystem' "${loki}"
grep -Fq 'working_directory: /var/lib/loki/compactor' "${loki}"

python3 -m py_compile "${exporter}"
bash -n "${exercise}"

if command -v promtool >/dev/null 2>&1; then
  promtool check config "${prom}"
  promtool check rules "${rules}"
fi

if command -v amtool >/dev/null 2>&1; then
  amtool check-config "${am}"
fi

if command -v haproxy >/dev/null 2>&1; then
  rendered="$(mktemp)"
  trap 'rm -f "${rendered}"' EXIT
  sed \
    -e 's/${HIDRA_API_BIND}/127.0.0.1:18080/g' \
    -e 's/${HIDRA_HAPROXY_METRICS_BIND}/127.0.0.1:18404/g' \
    -e 's/${HIDRA_APP_NODE_1}/127.0.0.1:18081/g' \
    -e 's/${HIDRA_APP_NODE_2}/127.0.0.1:18082/g' \
    "${app_proxy}" > "${rendered}"
  haproxy -c -f "${rendered}"
fi

echo "Production observability/alerting artifacts passed static/native validation."
