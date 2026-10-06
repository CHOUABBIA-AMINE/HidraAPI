#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)"
patroni="${root}/ops/production/postgres/patroni/patroni.yml.tpl"
etcd="${root}/ops/production/postgres/etcd/etcd.env.tpl"
proxy="${root}/ops/production/postgres/haproxy/hidra-postgres.cfg.tpl"
prod="${root}/src/main/resources/application-production.properties"
exercise="${root}/ops/production/postgres/scripts/verify-postgres-failover.sh"

grep -q '^scope: __HIDRA_PATRONI_SCOPE__$' "${patroni}"
grep -q 'synchronous_mode: __PATRONI_SYNCHRONOUS_MODE__' "${patroni}"
grep -q 'use_pg_rewind: true' "${patroni}"
grep -q 'data-checksums' "${patroni}"
grep -q 'hostssl replication' "${patroni}"
grep -q '^  verify_client: required
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200"$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  certfile: __PATRONI_REST_TLS_CERT__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  keyfile: __PATRONI_REST_TLS_KEY__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  cafile: __PATRONI_REST_TLS_CA__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"

member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${proxy}"
grep -q 'check port 8008 check-ssl verify required ca-file __PATRONI_REST_TLS_CA__ crt __PATRONI_REST_TLS_CLIENT_PEM__ verifyhost __HIDRA_PG_NODE_1_PATRONI_TLS_NAME__"$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  certfile: __PATRONI_REST_TLS_CERT__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  keyfile: __PATRONI_REST_TLS_KEY__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  cafile: __PATRONI_REST_TLS_CA__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"

member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${proxy}"
grep -q 'check port 8008 check-ssl verify required ca-file __PATRONI_REST_TLS_CA__ crt __PATRONI_REST_TLS_CLIENT_PEM__ verifyhost __HIDRA_PG_NODE_2_PATRONI_TLS_NAME__"$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  certfile: __PATRONI_REST_TLS_CERT__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  keyfile: __PATRONI_REST_TLS_KEY__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  cafile: __PATRONI_REST_TLS_CA__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"

member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${proxy}"
if grep -qE '^    server hidra-pg-[12].* check port 8008"$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  certfile: __PATRONI_REST_TLS_CERT__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  keyfile: __PATRONI_REST_TLS_KEY__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  cafile: __PATRONI_REST_TLS_CA__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"

member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${proxy}"; then
  echo "Plain Patroni HTTP health check detected; mutual TLS is required." >&2
  exit 1
fi
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  certfile: __PATRONI_REST_TLS_CERT__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  keyfile: __PATRONI_REST_TLS_KEY__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"
grep -q '^  cafile: __PATRONI_REST_TLS_CA__
member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
 "${patroni}"

member_count="$(grep -o '__ETCD_NODE_[123]_NAME__=' "${etcd}" | wc -l | tr -d ' ')"
[[ "${member_count}" -eq 3 ]] || { echo "Expected three etcd members." >&2; exit 1; }
grep -q '^ETCD_CLIENT_CERT_AUTH=true$' "${etcd}"
grep -q '^ETCD_PEER_CLIENT_CERT_AUTH=true$' "${etcd}"

grep -q '^frontend hidra_postgres_write$' "${proxy}"
grep -q '^    option httpchk GET /primary$' "${proxy}"
grep -q '^    http-check expect status 200$' "${proxy}"
pg_count="$(grep -c '^    server hidra-pg-' "${proxy}")"
[[ "${pg_count}" -eq 2 ]] || { echo "Expected two PostgreSQL nodes in HAProxy template." >&2; exit 1; }

grep -q '^spring.datasource.hikari.idle-timeout=${HIDRA_DATASOURCE_IDLE_TIMEOUT:600000}$' "${prod}"
grep -q '^spring.datasource.hikari.max-lifetime=${HIDRA_DATASOURCE_MAX_LIFETIME:1800000}$' "${prod}"
grep -q '^spring.datasource.hikari.keepalive-time=${HIDRA_DATASOURCE_KEEPALIVE_TIME:120000}$' "${prod}"

bash -n "${exercise}"
echo "Production PostgreSQL HA/failover artifacts passed static validation."
