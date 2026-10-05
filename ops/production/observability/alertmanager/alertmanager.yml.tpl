global:
  resolve_timeout: 5m

route:
  receiver: operations-default
  group_by: [alertname, domain, severity]
  group_wait: 30s
  group_interval: 5m
  repeat_interval: 4h
  routes:
    - matchers:
        - severity="critical"
      receiver: operations-critical
      repeat_interval: 30m
    - matchers:
        - domain="security"
      receiver: security-incidents
      repeat_interval: 30m
    - matchers:
        - domain="database"
      receiver: database-operations
    - matchers:
        - domain="backup"
      receiver: database-operations

receivers:
  - name: operations-default
    webhook_configs:
      - url: __OPERATIONS_DEFAULT_WEBHOOK__
        send_resolved: true

  - name: operations-critical
    webhook_configs:
      - url: __OPERATIONS_CRITICAL_WEBHOOK__
        send_resolved: true

  - name: database-operations
    webhook_configs:
      - url: __DATABASE_OPERATIONS_WEBHOOK__
        send_resolved: true

  - name: security-incidents
    webhook_configs:
      - url: __SECURITY_INCIDENT_WEBHOOK__
        send_resolved: true
