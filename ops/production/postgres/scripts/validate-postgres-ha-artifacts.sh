#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)"
patroni="${root}/ops/production/postgres/patroni/patroni.yml.tpl"
etcd="${root}/ops/production/postgres/etcd/etcd.env.tpl"
proxy="${root}/ops/production/postgres/haproxy/hidra-postgres.cfg.tpl"
prod="${root}/src/main/resources/application-production.properties"
exercise="${root}/ops/production/postgres/scripts/verify-postgres-failover.sh"

grep -Fqx 'scope: __HIDRA_PATRONI_SCOPE__' "${patroni}"
grep -Fq 'synchronous_mode: __PATRONI_SYNCHRONOUS_MODE__' "${patroni}"
grep -Fq 'use_pg_rewind: true' "${patroni}"
grep -Fq 'data-checksums' "${patroni}"
grep -Fq 'hostssl replication' "${patroni}"
grep -Fqx '  verify_client: required' "${patroni}"
grep -Fqx '  certfile: __PATRONI_REST_TLS_CERT__' "${patroni}"
grep -Fqx '  keyfile: __PATRONI_REST_TLS_KEY__' "${patroni}"
grep -Fqx '  cafile: __PATRONI_REST_TLS_CA__' "${patroni}"

member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -Fqx 'ETCD_CLIENT_CERT_AUTH=true' "${etcd}"
grep -Fqx 'ETCD_PEER_CLIENT_CERT_AUTH=true' "${etcd}"

grep -Fqx 'frontend hidra_postgres_write' "${proxy}"
grep -Fqx '    option httpchk GET /primary' "${proxy}"
grep -Fqx '    http-check expect status 200' "${proxy}"
grep -Fq 'check port 8008 check-ssl verify required ca-file __PATRONI_REST_TLS_CA__ crt __PATRONI_REST_TLS_CLIENT_PEM__ verifyhost __HIDRA_PG_NODE_1_PATRONI_TLS_NAME__' "${proxy}"
grep -Fq 'check port 8008 check-ssl verify required ca-file __PATRONI_REST_TLS_CA__ crt __PATRONI_REST_TLS_CLIENT_PEM__ verifyhost __HIDRA_PG_NODE_2_PATRONI_TLS_NAME__' "${proxy}"

plain_check_count="$(grep -Ec '^    server hidra-pg-[12].* check port 8008$' "${proxy}" || true)"
[[ "${plain_check_count}" -eq 0 ]] || {
  echo "Plain Patroni HTTP health check detected; mutual TLS is required." >&2
  exit 1
}

pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -Fqx 'spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}' "${prod}"
grep -Fqx 'spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}' "${prod}"
grep -Fqx 'spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
