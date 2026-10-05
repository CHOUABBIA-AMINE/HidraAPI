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

backend hidra_postgres_primary
    mode tcp
    option httpchk GET /primary
    http-check expect status 200
    default-server inter 2s fall 3 rise 2 on-marked-down shutdown-sessions
    server hidra-pg-1 __HIDRA_PG_NODE_1__:5432 check port 8008
    server hidra-pg-2 __HIDRA_PG_NODE_2__:5432 check port 8008
