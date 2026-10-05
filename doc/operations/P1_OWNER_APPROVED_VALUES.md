# P1 Owner-Approved Production Values

## Status

**OWNER-APPROVED — 2026-10-05**

This document records the production values explicitly accepted by the accountable owner for HidraAPI P1 closure.

These values are architecture/operations policy defaults. They do not claim that production hosts, DNS names, endpoints, credentials, or monitoring receivers have already been provisioned.

## Backup retention

- operational recoverability: **35 days**;
- monthly retained recovery points: **one recovery point per month**;
- monthly retention duration: **12 months / 12 monthly recovery points**;
- at least one protected copy must remain outside the live PostgreSQL primary/standby failure domain;
- a stricter SONATRACH Records/Legal/Compliance policy overrides this baseline if later identified.

P1 policy identifier: **HIDRA-P1-BACKUP-RETENTION-001**.

The repository must distinguish this owner-approved P1 policy identifier from any later enterprise records-policy identifier.

## Application runtime

- two simultaneously active HidraAPI Linux VM nodes;
- Java 21;
- systemd service management;
- HAProxy traffic distribution;
- REST active-active;
- no sticky-session correctness;
- P1 realtime remains single-active on node 1;
- node 2 remains REST-only for realtime purposes.

Recommended logical node names are `hidra-api-01` and `hidra-api-02`. Actual deployed hostnames remain environment evidence.

## PostgreSQL HA

- two PostgreSQL data nodes;
- Patroni-managed single-writer topology;
- three-member etcd quorum;
- HAProxy stable application-facing database endpoint;
- synchronous local replication where measured latency permits;
- optional remote asynchronous standby;
- application must use the stable endpoint, never a node-specific primary address.

Recommended logical names are `hidra-pg-01`, `hidra-pg-02`, and `hidra-etcd-01..03`. Actual deployed names remain environment evidence.

## Backup repository

- pgBackRest remains the approved backup/WAL/PITR implementation;
- repository/storage must be outside the live PostgreSQL failure domain;
- dedicated hardened backup storage plus independent replication/object storage is preferred where enterprise infrastructure provides it;
- monthly retained recovery points must be bound to a storage/archive mechanism that preserves 12 monthly points.

## Secrets

- HashiCorp Vault remains authoritative for production secrets;
- use a dedicated Hidra production Vault hierarchy;
- grant least-privilege policies per runtime role;
- runtime secret material is rendered to `/run/hidra/hidra-secrets.env`;
- production secret values must never be committed to Git or copied into release evidence.

## Production deployment approval

GitHub Environment `production` must require at least **two reviewers** before live deployment:

1. Platform/Operations approver;
2. Application/Release owner.

Deployment must remain restricted to approved exact-SHA release workflow execution.

## Deployment acceptance

The production acceptance path must be:

- authenticated;
- read-only;
- harmless to business data;
- database-backed;
- sufficient to validate authentication, authorization, application/database connectivity and ORM/schema compatibility.

A dedicated authenticated `/me`, reference-data lookup or equivalent business read is preferred over any write probe.

## Observability hosting and routing

Prometheus, Alertmanager, Grafana and Loki must be hosted independently enough that loss of an application node or database node does not remove monitoring visibility.

Alertmanager must provide distinct routing for:

- operations-default;
- operations-critical;
- database-operations;
- security-incidents.

Critical operations and security routes must reach an on-call/paging mechanism.

## Observability retention

P1 owner baseline:

- Prometheus high-resolution operational metrics: **30 days**;
- operational application logs in Loki: **90 days**;
- security/audit records: governed by the existing enterprise security/audit retention policy rather than this operational baseline.

## Exercise strategy

### Application HA

Fail node 1 and node 2 separately while continuously probing the HAProxy endpoint.

Acceptance requires REST continuity, failed-node removal, survivor traffic service and safe node rejoin.

### PostgreSQL HA

Use controlled Patroni switchover first.

Acceptance requires exactly one primary before/after, former-primary demotion, unchanged stable endpoint, HidraAPI reconnection and representative authenticated database acceptance.

### PITR

Create identifiable pre/post recovery markers and choose an intentional timestamp between them.

The isolated restore must contain the pre-target marker and exclude the post-target marker.

### Internal recovery targets

Formal P1 objectives remain:

- RPO <= 5 minutes;
- RTO <= 60 minutes.

Operational engineering targets are:

- RPO < 2 minutes where achievable;
- RTO < 30 minutes where achievable.

The tighter values are engineering margin, not replacements for the formal approved P1 objectives.

### Database operations

First exercise the read-only health report, then one reviewed low-risk `VACUUM (ANALYZE)` action on a controlled production-equivalent table.

### Alerting

Exercise at least:

- one warning;
- one critical alert;
- one database alert;
- one security-route alert.

Verify firing, routing, human receipt, acknowledgement, recovery and resolved notification.

## Version evidence

HPR-P1-012 must record exact deployed versions or immutable identifiers for Java, HAProxy, PostgreSQL, Patroni, etcd, pgBackRest, Prometheus, Alertmanager, Grafana and Loki.

HidraAPI evidence must include the exact Git SHA and application JAR SHA-256.
