global:
  scrape_interval: 15s
  evaluation_interval: 15s
  external_labels:
    environment: production
    service: hidra-api

rule_files:
  - /etc/prometheus/rules/hidra-alerts.yml

alerting:
  alertmanagers:
    - static_configs:
        - targets:
            - __ALERTMANAGER_TARGET__

scrape_configs:
  - job_name: hidra-api
    metrics_path: /actuator/prometheus
    authorization:
      type: Bearer
      credentials_file: __HIDRA_METRICS_BEARER_TOKEN_FILE__
    static_configs:
      - targets:
          - __HIDRA_APP_NODE_1_METRICS__
          - __HIDRA_APP_NODE_2_METRICS__

  - job_name: haproxy
    metrics_path: /metrics
    static_configs:
      - targets:
          - __HIDRA_APP_HAPROXY_METRICS__
          - __HIDRA_POSTGRES_HAPROXY_METRICS__

  - job_name: patroni
    metrics_path: /metrics
    scheme: https
    tls_config:
      ca_file: __PATRONI_CA_FILE__
      cert_file: __PATRONI_CLIENT_CERT__
      key_file: __PATRONI_CLIENT_KEY__
    static_configs:
      - targets:
          - __HIDRA_PG_NODE_1_PATRONI__
          - __HIDRA_PG_NODE_2_PATRONI__

  - job_name: pgbackrest
    metrics_path: /metrics
    static_configs:
      - targets:
          - __PGBACKREST_EXPORTER_TARGET__
