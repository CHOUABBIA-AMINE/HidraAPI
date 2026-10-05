# HidraAPI Production Observability

## Status

**IMPLEMENTED-PENDING-LIVE-DELIVERY-EXERCISE — HPR-P1-019**

The owner-approved P1 observability stack is:

- Prometheus for metrics collection/evaluation;
- Alertmanager for routing;
- Grafana for dashboards;
- Loki for centralized logs.

The repository now contains executable configuration templates and alert rules for the approved P1 thresholds.

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

- HidraAPI /actuator/prometheus on both application nodes;
- HAProxy Prometheus metrics;
- Patroni /metrics on both PostgreSQL members;
- the Hidra-owned pgBackRest metrics exporter.

HAProxy metrics provide the authoritative P1 application-node count and stable-write-backend cardinality used by the HA alerts.

## pgBackRest metrics exporter

The repository-owned exporter exposes:

- hidra_pgbackrest_latest_backup_age_seconds;
- hidra_pgbackrest_check_success.

It executes pgBackRest info/check against the approved stanza and exposes no credentials or backup content as metric labels.

## Alert routing

Alertmanager routes:

- critical operational alerts to the critical receiver;
- database/backup alerts to Database Operations;
- security-labeled alerts to the security incident receiver;
- other warnings to the default operations receiver.

Receiver URLs are placeholders and must be injected from the controlled production configuration. They must not be committed with real routing secrets.

## Grafana and Loki

Grafana provisioning binds Prometheus and Loki datasources.

The Loki baseline is deliberately single-process for P1 repository configuration and does not claim HA. Production retention/access-control values remain deployment governance and are not invented here.

## Live verification

Run:

ops/production/observability/verify-alert-routing.sh

with HIDRA_ALERTMANAGER_URL set to the production-equivalent Alertmanager endpoint.

The script injects representative warning, critical, database and security alerts. It verifies that Alertmanager accepts them. Actual receiver delivery/escalation still must be confirmed from the configured destination/on-call system and retained as HPR-P1-012 evidence.

## Remaining closure boundary

HPR-P1-019 repository implementation is complete, but production-equivalent evidence is still required for:

- Prometheus scrape/evaluation success;
- representative rule firing and resolution;
- Alertmanager receiver delivery;
- security/database route delivery;
- no-secret validation of labels/log content;
- Grafana/Loki ingestion visibility;
- alert recovery/resolve notification.

Production readiness remains NOT ESTABLISHED until those exercises are complete.
