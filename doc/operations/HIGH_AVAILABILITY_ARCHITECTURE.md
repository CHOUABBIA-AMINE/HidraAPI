# HidraAPI High Availability Architecture

## Status

**APPROVED TARGET ARCHITECTURE — HPR-P1-006**

Execution base: `f79b7875194f50460741179f63880ed176673844`

Pre-task documentation validation: run #3 / run id `37342929433` completed **SUCCESS**.

This document consolidates the approved HPR-P1-002 application HA model and HPR-P1-003 PostgreSQL HA model into one canonical production high-availability architecture.

It defines required behavior and ownership without selecting a hosting platform, load balancer, proxy, database HA manager, fencing implementation, broker, cache, virtualization platform, container platform, or cloud product.

## 1. Availability Objective

HidraAPI production availability is based on independent but coordinated redundancy at two layers:

1. **application service redundancy** — multiple active HidraAPI nodes;
2. **database redundancy** — one writable PostgreSQL primary with standby capacity and controlled role change.

Neither layer alone is sufficient to claim service HA.

The target architecture must avoid hidden single-node correctness dependencies at the application layer and uncontrolled multi-writer state at the database layer.

## 2. Canonical Logical Topology

```text
                         +----------------------+
Clients / API consumers |                      |
------------------------>| Managed traffic      |
                         | distribution boundary|
                         +----------+-----------+
                                    |
                      +-------------+-------------+
                      |                           |
                      v                           v
              HidraAPI node A              HidraAPI node B
              active / ready               active / ready
                      |                           |
                      +-------------+-------------+
                                    |
                                    v
                         Stable PostgreSQL endpoint
                                    |
                                    v
                         Writable PostgreSQL primary
                             |               |
              synchronous*   |               | asynchronous**
                             v               v
                      Local HA standby   Optional remote standby

* where the approved latency budget permits
** DR-oriented / failure-domain separated tier
```

The diagram is logical and product-neutral. It does not imply Kubernetes, OpenShift, Docker, VMs, a specific load balancer, a specific proxy, a specific PostgreSQL HA manager, or a specific network topology.

## 3. Application Tier

### 3.1 Minimum redundancy

**APPROVED TARGET**

Production requires at least **two simultaneously active HidraAPI nodes**.

No application primary is required for normal REST traffic.

Any ready node may serve an eligible REST request.

### 3.2 Session model

**APPROVED TARGET**

Protected REST/API traffic remains stateless at the HTTP session layer.

Correctness must not require sticky sessions or affinity for ordinary REST traffic.

Authentication/authorization continues to rely on the established bearer/JWT security model rather than local HTTP session state.

### 3.3 Traffic admission

**APPROVED TARGET**

Readiness controls whether a node may receive new traffic.

A node that is not ready must be removed from new request distribution.

Liveness is a process-recovery signal and must not substitute for readiness.

The implementation consuming those signals is not selected here.

### 3.4 Planned maintenance

A planned application-node change follows this sequence:

1. stop admitting new traffic by making/removing the node from readiness;
2. allow routing to drain from that node;
3. allow in-flight requests/work to finish within the approved operational window;
4. use graceful application shutdown;
5. apply maintenance/change;
6. restart the node;
7. verify liveness;
8. verify readiness and required dependencies;
9. return the node to traffic only after readiness succeeds.

The exact drain timeout is not invented by this document.

## 4. Node-Local State Constraints

### 4.1 Cache

**CURRENT — repository verified**

The common cache uses Spring `simple`, which is process-local.

**APPROVED TARGET**

Process-local cache must not be authoritative for:

- business correctness;
- authorization truth;
- audit truth;
- safety-relevant operational decisions;
- cross-node coordination.

If a cached value can affect correctness across nodes, an approved shared/consistent strategy must replace the node-local assumption before production HA is claimed.

No distributed-cache product is selected here.

### 4.2 Background work

Application-local executors are not a distributed scheduler.

Any background work that can run on multiple nodes must have explicit duplicate-execution and ownership semantics.

Where duplicate execution would be unsafe, an approved coordination/persistence mechanism must exist before multi-node execution is considered HA-safe.

No scheduler, queue, leader-election, or broker product is selected here.

## 5. Realtime / WebSocket Constraint

**CURRENT — repository verified**

HidraAPI realtime STOMP uses Spring's in-process simple broker.

**APPROVED TARGET**

The current simple broker is not a clustered realtime HA mechanism.

Until an approved shared/external cross-node realtime mechanism is implemented and verified, production must explicitly choose one operational constraint:

- confine realtime traffic to one active realtime node while REST remains multi-node; or
- disable realtime capability in the multi-node production deployment.

REST session affinity must not be introduced merely to conceal the current realtime limitation.

Clustered realtime HA must not be claimed until cross-node message and connection behavior is verified.

## 6. PostgreSQL Tier

### 6.1 Write authority

**APPROVED TARGET**

Exactly one PostgreSQL instance is authoritative and writable at a time.

Standbys are not independent writable databases.

HidraAPI does not perform application-side database role election.

### 6.2 Local HA standby

The production database tier requires at least one local/low-latency standby in a distinct failure domain.

The local standby uses streaming replication.

Synchronous replication is the approved target where the validated production latency budget permits it.

Changing the local HA tier to asynchronous replication because of latency requires an explicit operational/owner decision and must not occur silently.

### 6.3 Optional remote standby

An additional failure-domain/geographically separated standby may use asynchronous streaming replication.

This tier may support DR but does not replace independent backup/PITR and does not by itself prove RPO compliance.

## 7. Stable Database Endpoint

**APPROVED TARGET**

All HidraAPI application nodes use one stable database endpoint through `HIDRA_DATASOURCE_URL`.

The application must not encode primary/standby host identities for normal runtime operation.

During controlled database role change, infrastructure updates the stable endpoint or its underlying routing so HidraAPI can reconnect to the new primary.

The endpoint implementation remains product-neutral.

## 8. Database Failover

### 8.1 Authority

Database Operations owns planned switchover and normal PostgreSQL failover.

Emergency failover follows the approved incident-governance model with Database Operations or formally delegated database duty authority participating in promotion/demotion.

No HidraAPI node may independently promote a standby.

### 8.2 Single-writer / fencing requirement

Before a standby becomes writable, the former primary must be proven unable to continue as an independent writable primary, or equivalent fencing/consensus protection must guarantee that outcome.

Dual-writer state is prohibited.

Failover speed never overrides the single-writer requirement.

### 8.3 Application connection behavior

Existing database sessions may be lost during failover.

HidraAPI recovers by opening new connections to the stable endpoint after the promoted primary is available.

The architecture does not claim:

- transparent survival of in-flight transactions;
- global automatic replay of failed writes;
- correctness of blind write retries;
- HikariCP as an HA mechanism.

Retry behavior remains subject to explicit business/application idempotency design.

## 9. Failure Behavior

### 9.1 Application-node loss

If one HidraAPI node fails:

- it must stop receiving new traffic;
- surviving ready nodes continue serving eligible REST traffic;
- correctness must not depend on recovering the failed node's memory;
- requests in flight on the failed node may fail;
- realtime behavior remains subject to the current non-clustered broker constraint.

Service continuity in this state is limited by demonstrated capacity of surviving nodes.

HPR-P1-006 does not claim that one node can carry full peak load.

### 9.2 PostgreSQL-primary loss

If the primary fails:

1. Database Operations/infrastructure identifies an eligible standby;
2. prior-primary write authority is fenced/removed;
3. the standby is promoted under the approved authority;
4. the stable endpoint is redirected/updated;
5. application connections reconnect;
6. application/database health and read/write behavior are verified;
7. the former primary remains isolated or returns only as a safe standby.

No automatic failover product is selected by this architecture.

### 9.3 Standby loss

Loss of a standby while the primary remains healthy is a **degraded HA condition**.

Production may continue only according to the approved operational risk/maintenance procedure.

The loss must be observable and remediation must restore the approved redundancy posture.

This architecture does not invent the maximum allowable degraded duration.

## 10. Planned Database Maintenance

Planned primary maintenance should use controlled switchover when the selected infrastructure supports the approved model.

The sequence is:

1. confirm standby health and replication state;
2. verify target eligibility;
3. prevent concurrent writer state;
4. quiesce/drain database-changing activity if required by the selected procedure;
5. promote the intended target under Database Operations authority;
6. redirect the stable endpoint;
7. verify HidraAPI reconnection;
8. verify representative reads and writes;
9. verify Flyway/JPA schema expectations;
10. return the former primary only as a safe standby or keep it isolated;
11. preserve change/failover evidence.

## 11. Coordinated Application + Database Maintenance

Application and database maintenance must be coordinated so that:

- application nodes are not all removed simultaneously;
- database role changes do not coincide with uncontrolled application migration/startup activity;
- Flyway is not allowed to execute concurrently and independently from multiple nodes against an uncertain database authority;
- traffic is restored only to ready application nodes connected to the stable authoritative database endpoint;
- security and audit controls remain active.

The exact release/deployment coordination belongs to HPR-P1-007.

## 12. Flyway / Schema Safety

Flyway remains the schema migration authority.

Migrations execute through the stable endpoint against the writable primary.

Standbys receive database changes through replication; migrations are not independently re-run against each standby.

Multi-node application startup must not cause uncontrolled concurrent schema migration.

The deployment mechanism that serializes or otherwise safely controls migration belongs to HPR-P1-007.

## 13. Observability Requirements

The eventual production HA implementation must expose enough state to identify:

### Application

- node availability;
- readiness;
- liveness;
- request/error behavior;
- degraded node count;
- runtime dependency failures.

### PostgreSQL

- current primary;
- standby availability;
- replication state;
- synchronous-standby state where applicable;
- replication lag;
- failover/switchover events;
- connection exhaustion;
- recovery/replay status;
- loss of required redundancy.

This document does not select a metrics, dashboarding, alerting, or SIEM product and does not invent SLO thresholds.

HPR-P1-010 owns the broader observability operating model.

## 14. Degraded Modes

The following must be represented explicitly as degraded, not healthy HA:

- only one surviving HidraAPI node;
- PostgreSQL primary with no eligible local standby;
- local synchronous protection unavailable when it is the approved active mode;
- realtime constrained to one node because the in-process STOMP broker remains;
- required cross-node state coordination unavailable;
- stable endpoint mechanism impaired even if the database itself is alive.

Operational acceptance of a degraded state requires explicit ownership and evidence. This document does not invent allowable degraded-state duration.

## 15. Security Requirements During HA Events

Failover and maintenance must not bypass:

- authentication/authorization;
- production secret handling;
- database credential controls;
- audit attribution;
- error-disclosure restrictions;
- incident containment requirements;
- certificate/secret lifecycle controls.

A security incident may override ordinary availability optimization if containment requires isolation.

## 16. Ownership

| Area | Primary owner |
|---|---|
| Application readiness/liveness correctness | Application Engineering |
| Application node lifecycle / traffic distribution | Platform/Operations |
| PostgreSQL replication/roles/failover | Database Operations |
| Stable database endpoint | Database Operations + Platform/Operations |
| Security containment during HA incident | Security / Incident Command |
| Business-service acceptance after major failover | Accountable Operations/Business authority |

Specific personnel names are intentionally outside Git.

## 17. HA Acceptance Criteria

Production HA may be claimed only when evidence demonstrates at minimum:

### Application evidence

- at least two HidraAPI nodes run simultaneously;
- traffic reaches only ready nodes;
- ordinary REST correctness does not require sticky sessions;
- removal/failure of one node stops new traffic to that node;
- surviving node(s) continue serving the approved degraded workload;
- planned drain and graceful shutdown work;
- correctness does not depend on node-local cache state;
- background work has safe multi-node semantics;
- realtime is either explicitly constrained or uses a verified shared/cluster-capable mechanism.

### Database evidence

- exactly one writable PostgreSQL primary exists;
- at least one eligible local standby exists;
- approved replication mode is active;
- fencing/split-brain prevention is effective;
- controlled promotion works;
- the stable endpoint moves to/reaches the promoted primary;
- HidraAPI reconnects successfully;
- representative reads/writes work after role change;
- former primary cannot return as an independent writer;
- planned switchover succeeds;
- monitoring identifies loss/restoration of redundancy.

### Integrated evidence

- application service remains available through approved failover scenarios;
- database failover does not require app configuration to be rewritten with node identities;
- Flyway/JPA validation remains correct;
- security controls remain enforced;
- measured behavior is recorded.

HPR-P1-012 owns measured survivability verification.

## 18. Product Choices Explicitly Deferred

This architecture does **not** select:

- Kubernetes;
- OpenShift;
- Docker or another container runtime;
- a VM/hypervisor platform;
- a cloud provider;
- a hardware/software load balancer;
- reverse proxy/ingress product;
- Redis or another distributed cache;
- RabbitMQ, ActiveMQ, Kafka, or another realtime/message broker;
- Patroni;
- Pgpool-II;
- HAProxy;
- Keepalived;
- Pacemaker/Corosync;
- etcd/Consul;
- managed PostgreSQL;
- a fencing product;
- a virtual-IP/DNS failover product;
- autoscaling rules.

Infrastructure selection must implement this approved model rather than silently redefining it.

## 19. Relationship to Disaster Recovery

HA is for continuity through component/node failure.

DR is for recovery when HA is insufficient or unsafe.

The approved HPR-P1-004/HPR-P1-005 DR objectives and runbook remain separate controls:

- RTO ≤ 60 minutes;
- RPO ≤ 5 minutes;
- daily recoverable backup coverage;
- continuous WAL/PITR;
- 35-day operational retention;
- measured restore/PITR evidence.

A standby does not replace backup.

## 20. Production Readiness

The integrated application + PostgreSQL HA target architecture is now documented.

The implementing infrastructure and measured failover evidence are not yet established.

Therefore production HA and overall production readiness remain **NOT ESTABLISHED** until later P1 implementation and HPR-P1-012 survivability verification succeed.
