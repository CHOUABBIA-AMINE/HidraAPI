# HidraAPI Production Infrastructure Stack

## Status

**OWNER-APPROVED PRODUCTION INFRASTRUCTURE BASELINE — HPR-P1-013**

Owner acceptance date: **2026-10-05**

Execution base: `218492e6b48c672c0bca7b367634d88ebf274141`

Pre-task documentation validation: run #9 / run id `37352840037` completed **SUCCESS**.

This document records the concrete production technology selections accepted for P1 remediation.

It does not claim that the selected infrastructure is already provisioned, configured, tested, or production-ready.

## 1. Runtime and Hosting

### Approved

HidraAPI production runtime will use:

- Linux virtual machines;
- Java 21;
- systemd-managed HidraAPI services;
- minimum two simultaneously active HidraAPI application nodes.

### Rationale

The approved P1 objective is reliable multi-node service survivability, not introduction of an orchestration platform for its own sake.

Linux VMs plus systemd provide a smaller operational surface for the current modular monolith while still supporting:

- independent node lifecycle;
- graceful shutdown;
- readiness-based admission;
- rolling change;
- controlled replacement;
- explicit service ownership.

Kubernetes/OpenShift are not selected for P1.

## 2. Application Traffic Distribution

### Approved

HAProxy is the production traffic-distribution mechanism for HidraAPI application nodes.

Required behavior:

- route only to ready HidraAPI nodes;
- remove an unhealthy/unready node from new traffic;
- support controlled drain before planned shutdown;
- require no REST sticky-session correctness;
- preserve at least one serving node during rolling changes where capacity permits.

The implementation in HPR-P1-015 must bind HAProxy checks to the approved HidraAPI readiness/liveness model without exposing sensitive management data.

## 3. PostgreSQL High Availability

### Approved

PostgreSQL HA will use:

- PostgreSQL streaming replication;
- Patroni for primary/standby HA orchestration;
- etcd quorum for Patroni coordination/leader state;
- exactly one writable PostgreSQL primary;
- at least one local standby in the approved HA failure domain;
- synchronous local replication where the approved latency budget permits;
- optional remote asynchronous standby for additional failure-domain separation.

Patroni/etcd implementation must preserve the already-approved Database Operations authority and single-writer/fencing requirements.

No application node may perform database leader election.

## 4. Stable PostgreSQL Endpoint

### Approved

HAProxy will provide the stable application-facing PostgreSQL endpoint used by:

`HIDRA_DATASOURCE_URL`

HidraAPI must not embed current-primary node identity into normal application configuration.

The HPR-P1-016 implementation must prove:

- endpoint routing to the authoritative primary;
- removal of a demoted/failed primary from writable routing;
- redirection to the promoted primary;
- HidraAPI/Hikari reconnection through the same stable endpoint.

## 5. Backup, WAL Archiving and PITR

### Approved

pgBackRest is the selected PostgreSQL backup/WAL/PITR implementation.

It must provide the approved capabilities:

- base/full backup coverage at least every 24 hours;
- continuous WAL archiving;
- point-in-time recovery;
- 35-day operational recoverability;
- protected backup copies independent from the live PostgreSQL primary/standby nodes and their failure path;
- restore/PITR validation against the current HidraAPI schema;
- retained evidence suitable for HPR-P1-012 RPO/RTO measurement.

### Storage boundary

The backup repository must be operationally independent from the database nodes.

The exact storage product/medium is not selected by this decision. Infrastructure implementation must demonstrate that a database-host/storage failure cannot destroy both the live database and every protected backup copy through the same failure path.

## 6. Monthly Recovery-Point Retention

### OWNER-APPROVED 2026-10-05

Operational recovery retention remains **35 days**.

The owner-approved P1 monthly retention baseline is:

- one retained recovery point per month;
- **12 monthly recovery points / 12 months**;
- at least one protected copy outside the live PostgreSQL primary/standby failure domain;
- policy identifier **HIDRA-P1-BACKUP-RETENTION-001**.

A stricter SONATRACH Records/Legal/Compliance policy overrides this P1 baseline if later identified.

HPR-P1-017 must bind this approved rule to the selected backup/archive storage implementation and prove retained-point restore/PITR behavior.

## 7. Runtime Secrets and Sensitive Configuration

### Approved

HashiCorp Vault is selected for production secret material.

Secret-managed inputs include, where applicable:

- PostgreSQL credentials;
- Hidra JWT/HMAC signing secret;
- LDAP bind password;
- bootstrap password;
- private keys/certificates or equivalent sensitive runtime material introduced by the production environment.

Requirements:

- no production secret values in Git;
- no raw secrets in deployment/incident evidence;
- rotation must follow the approved security lifecycle;
- deployment must retrieve/inject approved secret references through the selected runtime mechanism.

Non-secret environment configuration may remain externally supplied through the production runtime mechanism.

## 8. Metrics and Alerting

### Approved platforms

- Prometheus — metrics collection/storage;
- Alertmanager — alert routing;
- Grafana — dashboards.

The current HidraAPI `/actuator/prometheus`, readiness/liveness and framework/runtime metrics are the application-side integration surface.

HPR-P1-019 must still obtain/record the concrete SLI/SLO values, evaluation windows and numeric thresholds required by the roadmap before executable alert rules can be considered complete.

Selecting Prometheus/Alertmanager/Grafana does not itself approve an availability percentage or latency/error threshold.

## 9. Centralized Logging

### Approved

Grafana Loki is selected for centralized HidraAPI application logging.

The existing log context must be preserved:

- timestamp;
- severity;
- correlation ID;
- request ID;
- actor ID when available;
- logger/component;
- message.

Loki ingestion must preserve masking requirements and must not turn bearer tokens, passwords, signing material, database credentials or other sensitive values into searchable log content.

## 10. Realtime P1 Operating Mode

### Approved

For P1:

- REST/API service is multi-node;
- current STOMP realtime remains **single-active**;
- clustered realtime HA is **not claimed**.

No external message broker is introduced solely to close P1.

If the active realtime node fails, realtime service may be interrupted/re-established according to the implemented operational procedure while REST remains available on the multi-node application tier.

A future shared/cluster-capable realtime broker requires a separate approved architecture decision and verification.

## 11. Cache P1 Operating Mode

### Approved

Spring process-local cache remains permitted only for non-authoritative optimization.

It must not hold or determine:

- authorization truth;
- audit truth;
- correctness-critical business state;
- cross-node coordination state;
- safety-relevant operational truth.

Redis or another distributed cache is not selected solely for P1.

If implementation evidence later shows a shared-cache requirement, that becomes a separate explicit architecture decision.

## 12. Background Execution

The accepted infrastructure stack does not turn the existing local executor into a distributed scheduler.

HPR-P1-015 must either:

- prove duplicate execution is safe for the applicable node-local work; or
- constrain ownership/execution so duplicate cross-node work cannot violate correctness.

No distributed scheduler/queue product is selected by HPR-P1-013.

## 13. Deployment Automation Boundary

The selected Linux VM/systemd + HAProxy target unblocks controlled deployment automation.

HPR-P1-018 must implement:

- exact artifact promotion;
- approval gate;
- Vault-backed secret/configuration injection;
- explicit production-profile enforcement;
- node drain;
- systemd service update/restart;
- health/readiness/security/database acceptance;
- rollback to the prior application artifact when schema compatibility permits;
- retained deployment evidence.

The CI/CD implementation mechanism remains HPR-P1-018 scope.

## 14. Database Operations Boundary

The selected Patroni/etcd/HAProxy/pgBackRest stack unblocks executable database operations.

HPR-P1-016, HPR-P1-017 and HPR-P1-020 must bind the existing product-neutral procedures to reviewed operational commands/configuration for the selected stack.

The repository must not claim these controls are implemented until those HPRs complete and are exercised.

## 15. Observability Boundary

The selected Prometheus/Alertmanager/Grafana/Loki stack unblocks HPR-P1-019.

HPR-P1-019 still requires:

- approved SLIs;
- approved SLOs;
- evaluation windows;
- numeric alert thresholds;
- routing/on-call integration;
- representative alert fire/delivery/recovery tests;
- alerting self-monitoring;
- no-secret validation.

## 16. Version Pinning

Specific infrastructure software versions are not invented in this decision document.

Each implementation HPR must pin or otherwise identify the exact deployed version/immutable artifact used for its production-equivalent verification and HPR-P1-012 closure evidence.

## 17. Production Readiness

The concrete P1 technology stack is now owner-approved.

It is not yet implemented or exercised.

The monthly backup recovery-point policy identifier/duration also remains unresolved.

Therefore production readiness remains **NOT ESTABLISHED**.


## 15. Owner-Approved P1 Operating Values

The consolidated accepted owner values for deployment approval, logical topology/naming, Vault use, monitoring retention, alert routing and production-equivalent exercise strategy are recorded in:

`doc/operations/P1_OWNER_APPROVED_VALUES.md`

Actual hostnames, endpoints, receiver URLs and immutable deployed versions remain environment evidence and must not be fabricated in documentation.
