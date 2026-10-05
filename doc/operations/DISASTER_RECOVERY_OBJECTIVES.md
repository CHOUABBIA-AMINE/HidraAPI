# HidraAPI Disaster Recovery Objectives

## Status

**APPROVED TARGET — HPR-P1-004**

Owner approval date: **2026-10-05**

Execution base: `1fa7f25e6985287cf6548653b99312afcdb9bce0`

Pre-task verification:
- lightweight documentation validation run #1 / run id `37340736184`: **SUCCESS**;
- one-time full CI after workflow split run #534 / run id `37340736111`: **SUCCESS**.

This document establishes recovery objectives and backup/PITR policy. It does not claim that the required infrastructure, runbooks, automation, or exercises are already implemented.

## 1. Scope

This decision defines:

- recovery time objective (RTO);
- recovery point objective (RPO);
- PostgreSQL backup coverage;
- backup retention;
- WAL archiving and point-in-time recovery;
- separation of backup from HA replication;
- recovery authority and ownership;
- required recovery evidence.

It does not select a backup product, storage platform, snapshot implementation, object store, database service, orchestration platform, or DR site technology.

## 2. Recovery Time Objective

**APPROVED TARGET**

Production RTO is:

**≤ 60 minutes**

### RTO clock start

The clock starts when the authorized incident/operations authority formally declares that disaster recovery is required for the production HidraAPI service.

Investigation time before formal DR declaration is not hidden inside the RTO measurement; incident records must preserve the incident start time and the DR declaration time separately.

### RTO clock stop

The clock stops only when the recovered production service passes the mandatory recovery acceptance gate defined in this document.

Starting a database process or application JVM alone does not satisfy RTO.

## 3. Recovery Point Objective

**APPROVED TARGET**

Production RPO is:

**≤ 5 minutes**

RPO is the maximum acceptable gap between the selected recovery point and the latest production data that must be recoverable after the incident.

An asynchronous remote standby may assist recovery but does not, by itself, prove the RPO.

The actual achieved recovery point must be measured and recorded during every DR exercise or real recovery.

## 4. PostgreSQL Recovery Strategy

**APPROVED TARGET**

The recovery strategy requires:

- periodic recoverable base/full backups;
- continuous WAL archiving;
- PostgreSQL point-in-time recovery capability;
- recovery to a selected safe point rather than only to the latest available state;
- validation that archived WAL required by retained recovery points is available and usable.

PITR is required because HA replication can reproduce logical corruption, erroneous writes, malicious deletion, or operator mistakes.

No PostgreSQL backup utility or commercial product is selected by HPR-P1-004.

## 5. Backup Frequency / Coverage

**APPROVED TARGET**

Production must have at least **one successful recoverable base/full backup per 24-hour period**.

Continuous WAL archiving must cover the interval between base/full backups so that recovery points can be selected within the approved retention window.

A scheduled backup that ran but cannot be restored does not count as successful recovery coverage.

Backup monitoring must detect missed/failed backup or WAL archival coverage.

The specific time of day is an operations scheduling decision and is not invented here.

## 6. Backup Retention

### 6.1 Operational retention

**APPROVED TARGET**

Operationally recoverable backup coverage must be retained for **35 days**.

This means the required base/full backup material and corresponding WAL needed for supported PITR points must remain recoverable throughout that window.

### 6.2 Monthly recovery points

**APPROVED TARGET**

A monthly recovery point must be preserved according to applicable enterprise records, legal, compliance, or records-governance policy.

HPR-P1-004 intentionally does **not** invent a multi-month or multi-year monthly-retention duration because no controlling enterprise-policy duration is established in the repository evidence.

If the enterprise policy requires a longer period than 35 days, that policy governs the monthly retained recovery points.

## 7. Backup Independence

**APPROVED TARGET**

A PostgreSQL standby is **not** a backup.

At least one protected backup copy must be operationally independent from the live primary/standby failure domain so a database-cluster, storage, operator, or destructive logical event cannot destroy every recovery copy through the same failure path.

The exact physical/site/storage implementation is deferred to infrastructure selection.

Backup access must follow least privilege and environment separation.

Production backup credentials/keys must not be stored in Git.

## 8. WAL Archiving and PITR

**APPROVED TARGET**

Continuous WAL archiving must:

- preserve continuity required for supported recovery points;
- report archive failures;
- prevent silent loss of PITR coverage;
- retain WAL long enough to support every retained recovery point within policy;
- be validated through actual restore/PITR exercises.

RPO compliance is measured from recoverability evidence, not from configuration presence alone.

No fixed WAL segment-upload interval is invented; the implementation must demonstrate that the achieved recovery point satisfies **RPO ≤ 5 minutes**.

## 9. DR Declaration Authority

**APPROVED TARGET**

DR activation follows the approved incident-governance model.

The authorized Incident Commander / Operations duty authority may declare that DR recovery is required under the established incident process.

For cyber/security incidents, Security Incident Command retains containment authority before recovery from a potentially compromised point.

The exact individual names and contact details remain outside Git.

## 10. Recovery Ownership

**APPROVED TARGET**

- **Database Operations** owns PostgreSQL backup integrity, restore, WAL/PITR execution, and database recovery verification.
- **Platform/Operations** owns HidraAPI runtime/environment restoration, runtime secrets/configuration injection, startup, traffic restoration, and infrastructure coordination.
- **Application Engineering** supports application/Flyway/JPA validation and diagnosis but does not become the routine owner of production database recovery.
- **Security** participates when the incident involves compromise, destructive activity, credential exposure, or recovery-point trust.
- **Accountable Operations/Business authority** accepts restoration of production service after technical acceptance checks pass.

## 11. Recovery Dependency Order

HPR-P1-004 establishes the objective-level dependency order:

1. contain/isolate the failed or compromised environment as required;
2. identify an authorized recovery point;
3. establish trusted database recovery infrastructure;
4. restore the base/full backup;
5. apply WAL/PITR to the selected recovery point;
6. verify PostgreSQL consistency and writable-primary authority;
7. establish the stable database endpoint;
8. restore required externalized secrets/configuration;
9. start HidraAPI with the production profile;
10. verify Flyway/JPA compatibility;
11. verify application health, authentication/authorization, and representative business read/write behavior;
12. restore production traffic only after acceptance;
13. record achieved RTO/RPO and residual issues.

HPR-P1-005 must convert this into the executable DR runbook.

## 12. Recovery Acceptance Gate

A recovery is not accepted until, at minimum:

- the selected recovery point is documented;
- database recovery completes without unresolved corruption;
- exactly one writable primary is established;
- the stable database endpoint resolves/routes to the recovered authority;
- Flyway schema validation succeeds;
- JPA schema validation succeeds;
- HidraAPI starts with the explicit production profile;
- application health/readiness is acceptable;
- protected authentication/authorization succeeds;
- representative authorized reads succeed;
- representative authorized writes succeed where safe for the recovery test;
- audit/security records required for the recovery context remain available;
- recovered data timestamp/recovery point is sufficient to determine achieved RPO;
- elapsed time from DR declaration to acceptance is sufficient to determine achieved RTO;
- residual data loss, skipped dependencies, or degraded capabilities are explicitly recorded.

## 13. Mandatory Recovery Evidence

Every DR exercise or real recovery must retain, without raw secrets:

- environment;
- incident/exercise/change identifier;
- incident time and DR declaration time;
- accountable recovery roles;
- source backup identifier/date;
- selected recovery timestamp/LSN-equivalent evidence where available;
- WAL/PITR range applied;
- start/end timestamps for restore phases;
- database integrity/consistency result;
- primary/standby role result;
- stable-endpoint result;
- Flyway/JPA validation result;
- application health result;
- authentication/authorization result;
- representative read/write acceptance result;
- achieved RPO;
- achieved RTO;
- whether targets were met;
- deviations, failures, rollback/containment actions;
- follow-up actions.

Evidence must not contain passwords, private keys, raw tokens, or protected secret values.

## 14. Restore and PITR Exercise Requirement

**APPROVED TARGET**

Backup configuration alone is insufficient.

Before P1 survivability closure, the project must execute and record:

- a restore from retained backup material;
- PITR to an intentionally selected point;
- verification of database/application startup from the recovered state;
- measurement of achieved RTO and RPO.

HPR-P1-012 is responsible for measured survivability closure.

A backup that has never been restored under controlled verification is not sufficient evidence of recoverability.

## 15. Relationship to High Availability

HA and DR are different controls.

The HPR-P1-003 local synchronous standby addresses database availability/failover.

DR addresses recoverability after events where ordinary HA is insufficient, including:

- replicated logical corruption;
- destructive operator action;
- malicious deletion or compromise;
- loss of the database failure domain;
- unrecoverable HA-cluster failure;
- need to recover to an earlier trusted point.

A remote asynchronous standby may contribute to recovery but does not replace independent backup/PITR.

## 16. Product-Neutral Requirements

HPR-P1-004 does **not** select:

- pgBackRest;
- Barman;
- filesystem snapshot tooling;
- object-storage product;
- tape platform;
- SAN/NAS product;
- cloud backup service;
- cloud PostgreSQL backup facility;
- Kubernetes/OpenShift backup operator;
- DR orchestration product.

The selected implementation must satisfy these objectives rather than redefining them silently.

## 17. Objective Summary

| Objective | Approved target |
|---|---|
| Production RTO | ≤ 60 minutes from formal DR declaration to service acceptance |
| Production RPO | ≤ 5 minutes |
| Base/full backup coverage | At least one successful recoverable backup per 24 hours |
| WAL | Continuous archiving required |
| PITR | Required |
| Operational retention | 35 days |
| Monthly recovery point | Required; duration governed by applicable enterprise records policy |
| Backup independence | At least one protected copy outside the live primary/standby failure domain |
| Restore verification | Mandatory |
| PITR exercise | Mandatory |
| Measured RTO/RPO evidence | Mandatory before P1 closure |

## 18. Production Readiness

The DR objectives are now approved and documented.

The backup/WAL infrastructure, executable DR runbook, database operations runbook, and measured restore/PITR evidence are not yet established.

Therefore disaster-recovery readiness and overall production readiness remain **NOT ESTABLISHED**.
