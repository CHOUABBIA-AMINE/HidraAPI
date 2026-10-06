global
    log stdout format raw local0

defaults
    log global
    timeout connect 5s
    timeout client 60s
    timeout server 60s

frontend hidra_postgres_write
    mode tcp
    bind __HIDRA_POSTGRES_BIND__
    default_backend hidra_postgres_primary

# Built-in HAProxy Prometheus exporter for PostgreSQL stable-endpoint state.
# Bind only on the approved monitoring network.
frontend hidra_postgres_haproxy_metrics
    mode http
    bind __HIDRA_POSTGRES_HAPROXY_METRICS_BIND__
    no log
    http-request use-service prometheus-exporter if { path /metrics }
    http-request deny

backend hidra_postgres_primary
    mode tcp
    option httpchk GET /primary
    http-check expect status 200
    default-server inter 2s fall 3 rise 2 on-marked-down shutdown-sessions
    # Patroni REST requires mutual TLS. check-ssl applies TLS only to the
    # health-check connection on port 8008; PostgreSQL data traffic remains
    # governed independently by the application/PostgreSQL connection policy.
    server hidra-pg-1 __HIDRA_PG_NODE_1__:5432 check port 8008 check-ssl verify required ca-file __PATRONI_REST_TLS_CA__ crt __PATRONI_REST_TLS_CLIENT_PEM__ verifyhost __HIDRA_PG_NODE_1_PATRONI_TLS_NAME__
    server hidra-pg-2 __HIDRA_PG_NODE_2__:5432 check port 8008 check-ssl verify required ca-file __PATRONI_REST_TLS_CA__ crt __PATRONI_REST_TLS_CLIENT_PEM__ verifyhost __HIDRA_PG_NODE_2_PATRONI_TLS_NAME__
