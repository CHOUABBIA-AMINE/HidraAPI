# HidraAPI Production Observability

## Status

**IMPLEMENTED-PENDING-LIVE-DELIVERY-EXERCISE — HPR-P1-019 / HPR-P1-026**

The owner-approved P1 observability stack is Prometheus, Alertmanager, Grafana and Loki. Repository configuration now includes the HPR-P1-026 integration fixes required by the 2026-10-06 re-audit. Live production-equivalent firing/delivery/resolve evidence remains pending under HPR-P1-029.

## Approved service objectives and thresholds

- monthly availability SLO: 99.9%, excluding formally approved maintenance;
- HTTP 5xx objective: below 1% over the service window;
- warning: 5xx >1% for 10 minutes;
- critical: 5xx >2% for 5 minutes;
- p95 request latency objective: <=1 second;
- warning: p95 >1 second for 10 minutes;
- critical: p95 >2 seconds for 5 minutes;
- zero active HidraAPI nodes for 1 minute: critical;
- fewer than two active HidraAPI nodes for 2 minutes: warning/degraded HA;
- process CPU >85% for 15 minutes: warning;
- process CPU >95% for 5 minutes: critical;
- JVM heap >85% for 10 minutes: warning;
- JVM heap >95% for 5 minutes: critical;
- Hikari pending connections >0 for 2 minutes: warning;
- Hikari utilization >90% for 5 minutes: warning;
- PostgreSQL stable write backend not exactly one active primary for 1 minute: critical;
- Patroni monitored members below two for 2 minutes: warning;
- replica replay lag >60 seconds for 5 minutes: warning;
- replica replay lag >180 seconds for 2 minutes: critical;
- latest successful backup older than 24 hours: critical;
- pgBackRest/WAL continuity check failure for 5 minutes: critical;
- Prometheus target unavailable for 2 minutes: warning and 5 minutes: critical.

Security escalation retains the existing incident policy: SEV-1 immediate escalation and SEV-2 within 30 minutes of confirmation/classification.

## Collection model

Prometheus scrapes:

- protected HidraAPI `/actuator/prometheus` on both application nodes using bearer authentication from the externally rendered `__HIDRA_METRICS_BEARER_TOKEN_FILE__` path;
- the application HAProxy built-in Prometheus exporter;
- the PostgreSQL stable-endpoint HAProxy built-in Prometheus exporter;
- Patroni `/metrics` on both PostgreSQL members using the existing TLS client configuration;
- the Hidra-owned pgBackRest metrics exporter.

The metrics bearer token is production secret material. Its real path/value is rendered by the approved production secret/configuration mechanism and is not committed to Git. The metrics endpoint remains protected; this HPR does not make Actuator metrics public.

Both HAProxy configurations expose `/metrics` only on dedicated monitoring listeners. Those listeners must be bound to the approved monitoring network; they are not application/public ingress endpoints.

HAProxy metrics provide the P1 application-node count and PostgreSQL stable-write-backend cardinality used by the HA rules.

## HTTP histogram binding

The production Spring profile explicitly enables the `http.server.requests` percentile histogram. This supplies the `http_server_requests_seconds_bucket` series consumed by the approved p95 `histogram_quantile` recording rule.

## Alert routing

Alertmanager evaluates domain-specific routes before the generic critical route:

- `domain=security` -> security incident receiver;
- `domain=database` -> Database Operations;
- `domain=backup` -> Database Operations;
- remaining `severity=critical` alerts -> critical Operations receiver;
- all other alerts -> default Operations receiver.

This prevents generic critical routing from shadowing database, backup or security ownership. Receiver URLs remain externally rendered placeholders and no real routing secret is committed.

## pgBackRest metrics exporter

The repository-owned exporter exposes:

- `hidra_pgbackrest_latest_backup_age_seconds`;
- `hidra_pgbackrest_check_success`.

It executes pgBackRest info/check against the approved stanza and exposes no credentials or backup content as metric labels.

## Static/native validation

Run:

```text
ops/production/observability/validate-observability-artifacts.sh
```

The validator asserts authenticated application scraping, HTTP histogram enablement, both HAProxy exporter bindings and domain-first Alertmanager route order. When installed, `promtool` validates Prometheus configuration/rules, `amtool` validates Alertmanager configuration, and `haproxy -c` validates a rendered application HAProxy configuration. PostgreSQL HAProxy mutual-TLS material remains deployment-rendered and is separately guarded by the PostgreSQL HA validator.

## Live verification

Run `ops/production/observability/verify-alert-routing.sh` with `HIDRA_ALERTMANAGER_URL` set to the production-equivalent Alertmanager endpoint.

The script injects representative warning, critical, database and security alerts. Alertmanager acceptance alone does not prove receiver delivery. HPR-P1-029 must retain live evidence of representative rule firing, correct domain recipient delivery, resolve/recovery notification, scrape success and no-secret behavior.

## Grafana and Loki

Grafana provisioning binds Prometheus and Loki datasources. Loki remains deliberately single-process for the current P1 repository baseline and does not claim HA. Approved retention enforcement is handled separately by HPR-P1-028.

## Remaining closure boundary

Production readiness remains NOT ESTABLISHED. Repository wiring is not a substitute for production-equivalent evidence of scrape success, rule firing, routing/delivery, resolution, Loki ingestion and receiver behavior.
