# HidraAPI PostgreSQL High Availability Model

## Status

**HISTORICAL — HPR-P1-003 target-model baseline; retained for P1 stage provenance.**

Current database authority is the HPR-P2-006 set indexed by `doc/database/README.md`, together with executable configuration/migrations and the closed P1 evidence. Later P1 closure supersedes any pre-closure “pending”, “target”, “not selected” or “not established” statements below when they conflict with current evidence.

Owner approval date: **2026-10-05**

Execution base: `4b4fc46d9ff78b8bd77a706ff1e36a7c1fc4ee3a`

Pre-task exact-head verification: GitHub Actions run #532 / run id `37338086447` completed **SUCCESS**.

This document defines the approved PostgreSQL high-availability model without selecting a PostgreSQL HA manager, proxy, virtual IP, DNS implementation, cloud database service, operating system cluster, container platform, or orchestration product.

## 1. Scope

This HPR defines:

- PostgreSQL primary/standby roles;
- replication mode;
- failover and switchover authority;
- the application connection contract;
- split-brain prevention requirements;
- maintenance behavior;
- ownership;
- minimum HA acceptance evidence.

It does not define RTO, RPO, backup frequency, backup retention, WAL archive retention, PITR objectives, or a disaster-recovery runbook. Those belong to HPR-P1-004 and later tasks.

## 2. Current Repository Baseline

**CURRENT — repository verified**

HidraAPI currently uses PostgreSQL as authoritative persistence.

Production configuration externalizes:

- `HIDRA_DATASOURCE_URL`;
- `HIDRA_DATASOURCE_USERNAME`;
- `HIDRA_DATASOURCE_PASSWORD`.

JPA validates the schema with `ddl-auto=validate`.

Flyway is enabled, validates on migrate, and disables clean in production.

HidraAPI uses HikariCP. Current pool defaults are implementation defaults, not HA timing or capacity guarantees.

The repository does not currently implement or select PostgreSQL replication/failover infrastructure.

## 3. Approved Production Topology

**APPROVED TARGET**

The production database tier must have:

1. exactly one authoritative writable PostgreSQL primary during normal operation;
2. at least one local or low-latency standby in a distinct failure domain;
3. streaming replication from the writable primary to the standby;
4. a stable application-facing database endpoint independent of individual database-node identity.

Preferred logical model:

```text
HidraAPI nodes
      |
      v
Stable PostgreSQL service endpoint
      |
      v
Writable PostgreSQL primary
      |
      +---- synchronous streaming ----> Local HA standby
      |
      +---- asynchronous streaming ---> Optional remote/DR standby
```

The remote/DR standby is optional in HPR-P1-003 and is not itself a substitute for HPR-P1-004 disaster-recovery objectives.

## 4. Replication Policy

### 4.1 Local HA standby

**APPROVED TARGET**

The local HA standby uses **synchronous streaming replication where the approved production latency budget permits**.

The objective is to avoid acknowledging a transaction as safely committed when the accepted HA design requires that transaction to be durable on the local standby.

If production measurements show that synchronous local replication violates an approved operational latency requirement, changing to asynchronous local HA requires an explicit documented owner/operations decision. It must not occur as an undocumented implementation shortcut.

This document does not invent a commit-latency target.

### 4.2 Remote standby

**APPROVED TARGET**

A geographically or failure-domain separated standby may use **asynchronous streaming replication** to avoid coupling normal transaction latency to WAN or long-distance replication latency.

Its acceptable lag and recovery consequences are governed by HPR-P1-004 RPO decisions and later measured evidence.

HPR-P1-003 does not claim zero data loss for a remote asynchronous failover.

## 5. Write Authority

**APPROVED TARGET**

Only the current primary may accept authoritative application writes.

Standbys are not approved as independent writable databases.

Read scaling from standbys is not part of HPR-P1-003. HidraAPI continues to use the stable service endpoint for normal persistence traffic unless a later roadmap decision explicitly introduces read routing.

A promoted standby becomes writable only through the authorized failover/switchover process.

## 6. Stable Application Connection Contract

**APPROVED TARGET**

HidraAPI must use one stable database endpoint through `HIDRA_DATASOURCE_URL`.

Application configuration must not require awareness of individual PostgreSQL node identities for ordinary runtime operation.

The infrastructure behind the stable endpoint may change which database node is primary, but the application contract remains stable.

No particular endpoint technology is selected. The endpoint could later be implemented by an approved proxy, virtual address, DNS/service abstraction, managed database endpoint, or equivalent enterprise mechanism.

## 7. Failover Authority

### 7.1 Normal operational failover

**APPROVED TARGET**

Database Operations owns planned switchover and normal database failover authority.

Platform/Operations coordinates the application/runtime side of the change.

### 7.2 Emergency failover

**APPROVED TARGET**

Emergency database failover follows the existing incident-governance model.

The Security/Incident Commander or applicable incident authority may direct containment/recovery under the approved incident procedure, but database role promotion/demotion requires Database Operations participation or the formally delegated database duty authority.

No application node or business-module code may independently decide to promote a PostgreSQL standby.

## 8. Split-Brain Prevention and Fencing

**APPROVED TARGET**

The selected PostgreSQL HA implementation must guarantee a single-writer authority model.

Before a standby is promoted, the prior primary must be demonstrably unable to continue serving as an independent writable primary, or the selected infrastructure must provide equivalent fencing/consensus protection.

A failover mechanism that can leave two independently writable primaries is not acceptable.

The exact fencing/consensus technology is intentionally not selected here.

## 9. Application Behavior During Failover

**APPROVED TARGET**

A database failover may interrupt existing TCP/database sessions.

HidraAPI must recover by establishing new connections through the stable database endpoint once the promoted primary is available.

The architecture does **not** claim that:

- an in-flight database transaction survives failover;
- every request can be transparently retried;
- a write whose outcome is uncertain can be blindly replayed;
- HikariCP alone provides database HA.

Transaction outcome ambiguity must be handled by application/business idempotency rules where such retry behavior is explicitly designed.

No global automatic write retry policy is introduced by this HPR.

## 10. Planned Switchover and Maintenance

**APPROVED TARGET**

Planned primary maintenance should prefer controlled switchover rather than an avoidable unplanned outage.

A controlled switchover must include:

1. confirm standby health and replication state;
2. confirm the intended target is eligible for promotion;
3. prevent concurrent write authority;
4. drain or quiesce database-changing activity as required by the selected procedure;
5. promote the approved standby;
6. redirect the stable service endpoint;
7. verify application reconnection and health;
8. verify read/write behavior;
9. verify Flyway/JPA schema expectations remain valid;
10. return the former primary only in a safe replica/standby role or keep it isolated until repaired;
11. preserve operational evidence.

The exact commands and automation are deferred until the HA platform is selected.

## 11. Flyway and Schema Change Interaction

**CURRENT + APPROVED TARGET**

Flyway remains the authoritative schema migration mechanism for HidraAPI startup/deployment.

Schema migrations must execute against the writable primary through the stable endpoint.

A migration must not be independently executed against each standby.

Standbys receive replicated database changes through PostgreSQL replication.

Deployment procedures must avoid concurrent uncontrolled Flyway migration attempts from multiple application nodes. The exact deployment coordination mechanism belongs to HPR-P1-007 and the selected runtime platform.

## 12. Backup Is Independent of HA

**APPROVED TARGET**

PostgreSQL replication does not replace backup.

Logical or physical corruption, accidental data modification, malicious deletion, operator error, or an invalid application transaction may replicate to standbys.

Therefore backup, retention, WAL archiving/PITR, and restore verification remain mandatory separate controls under HPR-P1-004, HPR-P1-005, HPR-P1-011, and HPR-P1-012.

No backup frequency or retention value is executed by HPR-P1-003.

## 13. Credential and Security Ownership

**CURRENT APPROVED OPERATING BASELINE**

Database Operations owns the HidraAPI PostgreSQL service credential lifecycle with Platform/Operations coordination.

The approved routine credential rotation baseline remains every 90 days unless stricter enterprise policy applies.

Production credentials remain externalized and must not be stored in Git or application artifacts.

Database TLS/network segmentation details remain separate production infrastructure/security decisions and are not invented here.

## 14. Monitoring Requirements

**APPROVED TARGET**

The eventual HA implementation must expose enough operational state to determine:

- current primary identity;
- standby availability;
- replication health;
- replication lag;
- synchronous-standby state where applicable;
- failover/switchover events;
- connection exhaustion;
- recovery/replay state after restart;
- loss of required redundancy.

HPR-P1-003 does not invent alert thresholds or monitoring products. HPR-P1-010 will define the broader observability operating model.

## 15. Failure Scenarios

The approved model must later be exercised against at least:

- primary process failure;
- primary host/failure-domain loss;
- standby failure while primary remains healthy;
- loss of synchronous standby;
- connection interruption during failover;
- planned switchover;
- attempted return of the former primary;
- loss of the stable endpoint mechanism;
- replication lag on the asynchronous remote standby, if implemented.

Expected behavior and measured recovery time/data loss must be recorded during HPR-P1-012.

## 16. Acceptance Criteria

PostgreSQL HA may be claimed only when evidence demonstrates:

- exactly one writable primary at any time;
- at least one eligible local HA standby;
- the approved replication mode is active;
- failover promotion is controlled by the approved authority;
- split-brain protection/fencing is effective;
- HidraAPI uses the stable endpoint rather than node-specific configuration;
- application connections recover after role change;
- ordinary read/write operations succeed after failover;
- Flyway/JPA startup validation remains healthy;
- planned switchover can be completed without uncontrolled dual-writer state;
- the former primary cannot rejoin as an independent writer;
- monitoring exposes replication/failover health;
- failover evidence is retained.

These criteria are targets until implemented and exercised.

## 17. Explicitly Not Selected

HPR-P1-003 does **not** select:

- Patroni;
- Pgpool-II;
- HAProxy;
- Keepalived;
- Pacemaker/Corosync;
- etcd;
- Consul;
- a cloud-managed PostgreSQL service;
- Kubernetes/OpenShift database operators;
- a virtual-IP product;
- a DNS failover implementation;
- a backup product.

A future infrastructure selection must satisfy this model rather than redefining it silently.

## 18. Relationship to Disaster Recovery

The local synchronous standby is the approved HA tier.

An optional remote asynchronous standby may contribute to DR, but the project must not claim DR readiness from replication alone.

HPR-P1-004 remains responsible for approving and recording RTO, RPO, backup frequency/retention and WAL/PITR objectives.

## 19. Production Readiness

The PostgreSQL HA **target model is approved and documented**.

The HA infrastructure is not yet implemented or exercised by repository evidence.

Therefore database HA and overall production readiness remain **NOT VERIFIED / NOT ESTABLISHED** until later P1 implementation and survivability verification.
