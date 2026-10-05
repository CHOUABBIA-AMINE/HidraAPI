# HidraAPI Observability and SRE Operating Model

## Status

**OPERATING MODEL ESTABLISHED / P1 ALERTING IMPLEMENTED-PENDING-LIVE-EXERCISE — HPR-P1-010 + HPR-P1-019**

Execution base: `39da2f1bf9bf4e6305b29a6b5bfd34a287bc6fd2`

Pre-task exact-head verification: HidraAPI CI run #535 / run id `37344473469` completed **SUCCESS**, including the OpenAPI backward-compatibility gate.

This document defines the canonical production observability operating model from current HidraAPI evidence and approved HA/DR/security governance.

It does **not** invent:

- an SLO;
- an error budget;
- a latency threshold;
- an error-rate threshold;
- a metrics-retention period;
- a log-retention period;
- a Prometheus server/managed metrics product;
- Grafana or another dashboard product;
- Alertmanager or another alert router;
- a SIEM;
- an on-call/paging product;
- a trace backend.

Executable P1 production alert rules are now implemented under `ops/production/observability/` after owner approval on 2026-10-05. Live receiver delivery and production-equivalent alert exercises remain pending.

## 1. Current Repository-Verified Observability Surface

### 1.1 Actuator

HidraAPI currently exposes these Actuator surfaces through the configured management endpoint boundary:

- `/actuator/health`;
- `/actuator/info`;
- `/actuator/metrics`;
- `/actuator/prometheus`.

Readiness and liveness health probes are enabled.

Production suppresses health detail disclosure.

### 1.2 Metrics

Micrometer Prometheus export is enabled.

Common metric tags include:

- application = `hidra-api`;
- environment = `HIDRA_ENVIRONMENT`, which should be `production` in production.

The current Spring Boot/Micrometer runtime can expose framework/runtime signals such as JVM, process, HTTP and datasource/pool metrics according to active instrumentation.

**CURRENT LIMITATION**

Repository inspection for HPR-P1-010 found no custom application-owned Micrometer `MeterRegistry`, `Counter`, `Timer`, or `Gauge` instrumentation that can be claimed as current business/service telemetry.

Therefore this operating model must not pretend that domain-specific business KPIs already exist as metrics.

### 1.3 Logging

The current console logging pattern includes:

- timestamp;
- severity level;
- thread;
- `correlationId`;
- `requestId`;
- `actorId`;
- logger;
- message.

Application observability configuration requires correlation/request response headers and sensitive-value masking.

Production log levels reduce Spring Security and Hibernate SQL/bind verbosity.

### 1.4 Audit / security evidence

Operational diagnosis may also use repository-backed persisted evidence from:

- Identity authentication/session/event records;
- Audit-module records;
- Flyway migration history;
- CI/deployment evidence;
- application logs;
- PostgreSQL operational evidence.

Audit records and application logs are distinct evidence sources and must not be treated as interchangeable.

## 2. Observability Objectives

The production observability model must support:

1. **availability detection** — determine whether HidraAPI is serving and whether each node is ready/live;
2. **dependency diagnosis** — identify database and required external-identity dependency failures;
3. **performance diagnosis** — expose enough runtime/HTTP/pool evidence to investigate degradation;
4. **HA state awareness** — identify application-node and PostgreSQL redundancy degradation;
5. **deployment validation** — verify a new revision is healthy before/after traffic admission;
6. **security response** — preserve correlation, request and authenticated-actor context and connect to incident governance;
7. **DR evidence** — provide timestamps/state required to measure and explain recovery;
8. **capacity evidence** — support later capacity decisions without inventing limits before measurement.

## 3. Signal Classes

### 3.1 Application health

Required signals:

- node liveness;
- node readiness;
- aggregate application availability as seen by the production traffic layer;
- restart/crash behavior where the selected runtime exposes it.

Readiness is the traffic-admission signal.

Liveness is the process-recovery signal.

A liveness alert must not substitute for readiness/dependency diagnosis.

### 3.2 HTTP service

Use available server/Micrometer HTTP signals to observe:

- request volume;
- response status distribution;
- request duration distribution;
- abnormal increase in server errors;
- abnormal latency/change from established baseline.

**OWNER-APPROVED 2026-10-05**

Monthly availability SLO is 99.9% excluding formally approved maintenance. HTTP 5xx and latency thresholds are implemented in the P1 Prometheus rules: warning >1% 5xx for 10m, critical >2% for 5m, p95 warning >1s for 10m and critical >2s for 5m.

### 3.3 JVM / process

Use available runtime metrics to observe:

- heap/non-heap memory;
- garbage collection;
- thread behavior;
- CPU/process utilization where exported;
- process uptime/restarts.

**OWNER-APPROVED 2026-10-05**

Process CPU warning/critical thresholds are >85% for 15m and >95% for 5m. JVM heap warning/critical thresholds are >85% for 10m and >95% for 5m.

### 3.4 PostgreSQL client / Hikari pool

Observe application-side datasource signals sufficient to diagnose:

- active/idle/pending connections where exposed;
- connection acquisition/exhaustion;
- connectivity failures;
- pool saturation trends.

Database-side replication/failover/WAL/backup signals are governed jointly with Database Operations and HPR-P1-003/HPR-P1-004/HPR-P1-011.

### 3.5 PostgreSQL HA

The production monitoring implementation must expose enough database state to determine:

- current writable primary;
- standby availability;
- replication status;
- synchronous standby state where applicable;
- replication lag;
- failover/switchover event;
- recovery/replay state;
- loss of required redundancy.

The application repository does not currently implement those server-side database exporters/collectors.

### 3.6 Backup / WAL / DR

Required operational conditions include:

- successful recoverable base/full backup coverage within the approved 24-hour objective;
- continuous WAL archival continuity;
- loss of PITR coverage;
- restore/PITR exercise result;
- achieved RTO/RPO evidence during exercises/incidents.

No backup-monitoring product is selected here.

### 3.7 Authentication / security

Operational monitoring must make material authentication/security failures diagnosable while preserving secret safety.

Relevant evidence may include:

- failed/successful authentication events as provided by implemented Identity/audit persistence;
- external OIDC/LDAP dependency failures where applicable;
- authorization/security incidents;
- unusual privilege/security events identified through approved security processes.

Raw passwords, bearer tokens, signing secrets and private keys must never be observability labels or log content.

### 3.8 Realtime

Current realtime uses an in-process STOMP simple broker.

Until clustered realtime is implemented, monitoring must distinguish REST HA from realtime availability and must not present the current broker as cluster-wide HA.

## 4. Cardinality and Secret-Safety Rules

Observability labels/tags must not contain high-cardinality or sensitive values merely because they are available.

Do not use raw:

- bearer tokens;
- passwords;
- private keys;
- JWT signing material;
- LDAP bind passwords;
- database passwords;
- request/response bodies containing sensitive operational data.

Correlation/request IDs belong primarily in logs/traces/evidence, not indiscriminately as metric labels.

Actor identifiers may be required for security/audit evidence but must not be introduced as unbounded metric labels.

## 5. Canonical Operational Views

The selected observability platform must eventually provide equivalent views for:

### 5.1 Service overview

- active/ready HidraAPI node count;
- readiness/liveness state;
- request rate;
- HTTP error behavior;
- request latency behavior;
- application revision/environment identity;
- datasource health.

### 5.2 JVM/runtime

- memory/GC;
- threads;
- process uptime/restart evidence;
- CPU/utilization where available.

### 5.3 PostgreSQL client

- connection pool utilization;
- pending/failed acquisition;
- connectivity errors.

### 5.4 HA

- application node redundancy state;
- PostgreSQL primary identity;
- standby health;
- replication state/lag;
- degraded redundancy condition;
- failover/switchover event.

### 5.5 DR

- latest successful recoverable backup coverage;
- WAL archival continuity;
- retained recovery coverage;
- latest restore/PITR exercise;
- latest measured RTO/RPO result.

### 5.6 Security/identity

- material authentication-provider availability/failure;
- security incident context/evidence links;
- audit/event accessibility.

No dashboard product is selected by this document.

## 6. Alert Condition Classes

The following are **approved condition classes**, not yet executable product rules.

### 6.1 Immediate actionable availability conditions

Alerting must be able to notify the responsible operational role when:

- no HidraAPI node is ready;
- the production service cannot pass the approved external/service acceptance check;
- database write authority is unavailable or ambiguous;
- a security containment event intentionally removes service availability.

Evaluation windows for P1 service availability are owner-approved: zero active HidraAPI nodes for 1m is critical; fewer than two active nodes for 2m is warning/degraded HA. Receiver endpoints and named on-call destinations remain controlled environment configuration.

### 6.2 HA degradation conditions

Alerting must identify:

- active/ready application-node count below the approved minimum of two;
- PostgreSQL local HA standby unavailable;
- required synchronous protection unavailable when synchronous local HA is the active approved mode;
- stable PostgreSQL endpoint failure;
- split-brain/dual-writer suspicion;
- failed or incomplete planned failover/switchover.

Loss of redundancy is degraded HA even when traffic still works.

### 6.3 Database operational conditions

Alerting must be able to identify:

- datasource connectivity failure;
- connection-pool exhaustion/saturation condition;
- replication failure;
- abnormal replication lag relative to an approved threshold once one exists;
- database failover/recovery events;
- backup coverage failure;
- WAL archival failure/loss of PITR continuity.

### 6.4 Application degradation conditions

Alerting must support detection of:

- readiness failure;
- repeated process/liveness failure;
- material HTTP server-error increase;
- material request-latency degradation;
- resource exhaustion trends.

Numeric P1 thresholds are owner-approved and implemented in `ops/production/observability/prometheus/rules/hidra-alerts.yml`. Live delivery and alert-resolution evidence remains pending.

### 6.5 Security conditions

Security monitoring/escalation follows `doc/security/INCIDENT_RESPONSE.md`.

SEV-1 conditions require immediate escalation.

SEV-2 conditions require escalation within 30 minutes of confirmation/classification.

This document does not create new security severity definitions.

## 7. Severity and Ownership Routing

Use the existing incident-governance roles.

| Condition domain | Operational owner |
|---|---|
| HidraAPI node/readiness/runtime | Platform/Operations + Application Engineering |
| PostgreSQL connectivity/HA/replication | Database Operations |
| Stable DB endpoint | Database Operations + Platform/Operations |
| OIDC/LDAP provider operation | Identity/Directory Operations |
| Security event | Security Incident Commander + affected technical owner |
| Deployment regression | Platform/Operations + Application Engineering |
| Backup/WAL/PITR | Database Operations |
| Business-service acceptance | Accountable Operations/Business authority |

Named contacts/on-call rotations remain in the organization's controlled operational system, not Git.

## 8. SLO / Error-Budget Governance

**P1 SLO BASELINE APPROVED 2026-10-05**

The P1 service availability SLO is 99.9% monthly, excluding formally approved maintenance. A formal error-budget operating policy/burn-rate automation beyond the approved alert thresholds is not introduced by P1.

Therefore HPR-P1-010 does not invent:

- monthly availability percentage;
- latency percentile objective;
- error-rate objective;
- burn-rate alert;
- error budget;
- recovery-window alert threshold.

Before SLO alerting can be implemented, owners must approve:

1. service-level indicator definitions;
2. measurement boundary;
3. target value;
4. evaluation window;
5. excluded maintenance/error classes if any;
6. error-budget policy;
7. alerting/burn-rate rules.

## 9. Numeric Alert Threshold Governance

**DECISION REQUIRED**

Numeric thresholds must come from measured production/staging behavior, platform constraints, vendor guidance where applicable, and owner approval.

At minimum thresholds remain undecided for:

- HTTP latency;
- HTTP error rate;
- CPU;
- JVM heap;
- GC behavior;
- thread count;
- Hikari pool saturation/pending wait;
- PostgreSQL replication lag;
- disk/storage capacity;
- collector scrape/data freshness;
- log-ingestion backlog.

Do not encode arbitrary values merely to make an alert rule executable.

## 10. Monitoring and Alerting Platform Decision

**PLATFORM DECISION RESOLVED 2026-10-05**

The owner-approved production stack is:

- metrics collection/storage platform;
- Prometheus-compatible scrape/ingestion mechanism;
- alert rule/evaluation engine;
- alert routing/on-call/paging integration;
- dashboard platform;
- centralized log aggregation platform;
- SIEM integration if required;
- retention/access-control model.

Selected platforms are Prometheus, Alertmanager, Grafana and Loki. Repository implementation is under `ops/production/observability/`; production-equivalent receiver delivery and integration exercises remain required.

## 11. Logging Operating Model

Production logging must preserve:

- UTC/offset-aware timestamps;
- severity;
- correlation ID;
- request ID;
- authenticated actor context when available;
- source logger/component;
- diagnostic message.

Operational logging must preserve sensitive-value masking.

Production log levels must not be raised to expose SQL bind values or security secrets as a routine monitoring shortcut.

Centralized log ingestion and retention are not established by repository evidence.

## 12. Correlation and Incident Evidence

During incident diagnosis, responders should pivot using:

- environment;
- deployed SHA/revision;
- timestamp;
- correlation ID;
- request ID;
- authenticated actor identifier;
- affected API/resource;
- database/identity dependency state.

Observability evidence must integrate with the role-based incident process without recording secrets.

## 13. Deployment Observability Gate

A production deployment must not admit a node to traffic until:

- liveness is healthy;
- readiness is healthy;
- PostgreSQL connectivity works;
- Flyway/JPA validation succeeds;
- authentication/authorization acceptance succeeds.

Post-deployment observation must confirm the expected revision is running and no material degradation is observed according to approved checks.

## 14. HA Observability Gate

Before production HA can be claimed, operations must be able to observe:

- at least two application nodes;
- individual readiness/liveness;
- traffic exclusion of unready nodes;
- database primary/standby roles;
- replication protection;
- failover event;
- stable endpoint behavior;
- restoration of redundancy.

HPR-P1-012 must retain measured failover evidence.

## 15. DR Observability Gate

Before DR readiness can be closed, operations must be able to determine and retain:

- backup used;
- WAL/PITR recovery point;
- recovery timeline;
- achieved RPO;
- achieved RTO;
- database/application acceptance result.

HPR-P1-012 must record these measurements.

## 16. Observability Failure Is an Operational Condition

Loss of monitoring/alert delivery must itself be visible to operations through the selected platform's supported self-monitoring mechanism.

The exact collector/alert-router self-monitoring rules are product-dependent and therefore deferred.

## 17. Validation Requirements After Platform Selection

Once a monitoring/alerting platform is approved, the implementation follow-up must demonstrate at minimum:

- metrics are collected from each production-equivalent HidraAPI node;
- application/environment identity is preserved;
- readiness/liveness state is visible;
- required HA/database/backup signals are available from their authoritative sources;
- representative alert conditions can be induced safely and are routed to the correct role;
- alert recovery/resolution is observable;
- no secrets are exposed in metrics/logs;
- evidence is retained for the test;
- numeric thresholds/SLOs match explicit approvals.

## 18. Current Blocker

The operating model is complete.

Executable production alerting is **BLOCKED-DECISION** because the repository contains no approved production monitoring/alert-routing product and no approved numeric alert thresholds/SLOs.

HPR-P1-010 must not be marked fully implemented until those decisions exist and the selected platform is configured/tested.

## 19. Production Readiness

Current application-side observability surfaces are sufficient to support later integration, but production observability operations and alert delivery are not yet verified.

Therefore production observability readiness and overall production readiness remain **NOT ESTABLISHED**.


## 16. Owner-Approved P1 Retention and Routing Baseline

Owner approval on 2026-10-05 establishes:

- Prometheus high-resolution operational metrics retention: **30 days**;
- Loki operational application log retention: **90 days**;
- security/audit retention remains governed by the existing enterprise security/audit policy;
- Alertmanager routing domains: operations-default, operations-critical, database-operations and security-incidents;
- critical operations and security routes must reach an on-call/paging mechanism.

Actual receiver destinations and credentials remain controlled production configuration and must not be committed to Git.
