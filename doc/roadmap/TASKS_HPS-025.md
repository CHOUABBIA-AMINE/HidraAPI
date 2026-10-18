# HPS-025 — Deployment and Operational Readiness: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-025 — Deployment and Operational Readiness

<a id="hapi-452"></a>
## HAPI-452 — define production runtime architecture

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-001

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-451

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create doc/architecture/RUNTIME_ARCHITECTURE.md from approved production decisions only; do not invent deployment technology.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-001 runtime-architecture evidence

- execution base SHA: `912b76396651c197d4db9ecccb21cd3de6a83e86`;
- P0 closure CI run #529 / run id `37326280500`: **SUCCESS**;
- latest pre-task head CI run #530 / run id `37328378048`: **SUCCESS**;
- repository runtime is Java 21 / Spring Boot 4.1.1 with PostgreSQL and Flyway;
- HTTP security is stateless; production-sensitive configuration is externalized;
- Actuator readiness/liveness and Prometheus-format metrics are configured;
- common cache is process-local Spring `simple` cache and realtime STOMP uses the in-process simple broker;
- no production Docker/Kubernetes/Helm/IaC deployment definition is established by repository evidence;
- multi-node/load distribution, PostgreSQL HA, RTO/RPO, backup/WAL/PITR, DR, deployment target, TLS product/placement, secret-manager product, network zones and production monitoring platform remain decision-required or NOT ESTABLISHED.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-451. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `ce662955a9615443be094859a68d10c029e94324` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13770](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13770)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6024](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6024)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:192](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L192)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2099](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2099)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15510](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15510)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:192 | HPR-P1-001 | COMPLETED — canonical runtime architecture created from exact-head repository evidence; current Spring Boot/JVM, PostgreSQL/Flyway, stateless HTTP security, production-profile externalization, Actuator/Prometheus, in-process cache and STOMP broker are recorded separately from approved targets and unresolved deployment/HA/DR decisions; no deployment technology was invented | Runtime Architecture | Doc | Create `doc/architecture/RUNTIME_ARCHITECTURE.md` from approved production decisions only; do not invent deployment technology. | docs(architecture): define production runtime architecture | HPR-P0-015 completed
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15510 | HPR-P1-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Runtime Architecture; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6024 | HPR-P1-001 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13770 | HPR-P1-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Runtime Architecture; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-453"></a>
## HAPI-453 — define application high availability model

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-002

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-452

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Approve stateless multi-node runtime and load-distribution mechanism; document node state, readiness/liveness integration and failure behavior.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/operations/APPLICATION_HIGH_AVAILABILITY.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/operations/APPLICATION_HIGH_AVAILABILITY.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-002 application-HA decision evidence

- execution base SHA: `ce662955a9615443be094859a68d10c029e94324`;
- pre-task exact-head CI: run #531 / run id `37330294217`: **SUCCESS**;
- owner acceptance date: 2026-10-05;
- approved application service level: minimum two simultaneously active HidraAPI nodes;
- approved traffic model: product-neutral managed load distribution with no REST session affinity;
- only ready nodes may receive new traffic; unready/failed nodes must be removed from new traffic;
- liveness is the process recovery signal and is distinct from dependency-sensitive readiness;
- planned maintenance requires traffic drain followed by graceful shutdown;
- current process-local Spring `simple` cache must not hold correctness-critical shared state across nodes;
- current in-process STOMP simple broker is not approved as clustered realtime transport; clustered realtime requires an external/shared broker or equivalent approved cross-node mechanism before HA is claimed;
- deployment/load-balancer/broker/cache products remain intentionally unselected;
- HPR-P1-003 and HPR-P1-004 owner recommendations were accepted in the same decision turn, but those HPRs remain unexecuted.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-452. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `4b4fc46d9ff78b8bd77a706ff1e36a7c1fc4ee3a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/architecture/RUNTIME_ARCHITECTURE.md:154](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/architecture/RUNTIME_ARCHITECTURE.md#L154)
- [doc/model-remediation/RECONCILIATION.md:13771](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13771)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6025](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6025)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:193](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L193)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2112](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2112)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15511](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15511)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:193 | HPR-P1-002 | COMPLETED — owner accepted the application HA baseline on 2026-10-05: minimum two active HidraAPI nodes, product-neutral managed load distribution, no REST session affinity, readiness-driven traffic admission/removal, graceful drain/shutdown, no correctness dependency on node-local cache, and no clustered realtime claim until the current in-process STOMP broker is replaced/externalized; deployment product remains unselected | Application Runtime | Infra/Doc | Approve stateless multi-node runtime and load-distribution mechanism; document node state, readiness/liveness integration and failure behavior. | docs(operations): define application high availability model | HPR-P1-001 + owner decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15511 | HPR-P1-002 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Application Runtime; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6025 | HPR-P1-002 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13771 | HPR-P1-002 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Application Runtime; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-454"></a>
## HAPI-454 — define postgres high availability model

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-003

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-452

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Approve PostgreSQL replication/failover topology, replication mode, failover authority, connection behavior, maintenance behavior and ownership.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/database/POSTGRES_HIGH_AVAILABILITY.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/database/POSTGRES_HIGH_AVAILABILITY.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-003 PostgreSQL-HA decision evidence

- execution base SHA: `4b4fc46d9ff78b8bd77a706ff1e36a7c1fc4ee3a`;
- pre-task exact-head CI: run #532 / run id `37338086447`: **SUCCESS**;
- owner acceptance date: 2026-10-05;
- approved topology: one writable primary plus at least one local synchronous streaming standby where approved latency permits;
- an additional remote asynchronous standby is permitted as the DR replication tier, but HPR-P1-003 does not define DR objectives or declare that tier sufficient for DR;
- HidraAPI must connect through a stable database endpoint rather than node-specific topology knowledge;
- normal failover/switchover authority belongs to Database Operations; emergency action follows the approved incident-command authority with Database Operations/Platform participation;
- failover must prevent dual-primary/split-brain conditions through the selected infrastructure's fencing/authority mechanism;
- application recovery after database role change is connection/retry based; in-flight transaction survival is not claimed;
- replicas are not backups; backup retention, WAL/PITR and RTO/RPO remain HPR-P1-004 scope;
- no PostgreSQL HA manager, proxy, virtual IP, DNS, load balancer, cloud database service, or orchestration product is selected by this HPR.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-452. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `f42de6936a9ecfec331c162c57ec43abcc46a201` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/architecture/RUNTIME_ARCHITECTURE.md:168](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/architecture/RUNTIME_ARCHITECTURE.md#L168)
- [doc/model-remediation/RECONCILIATION.md:13772](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13772)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6026](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6026)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:194](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L194)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2128](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2128)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15512](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15512)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:194 | HPR-P1-003 | COMPLETED — owner-approved PostgreSQL HA model documented: one writable primary, at least one local synchronous streaming standby where approved latency permits, optional remote asynchronous standby for DR separation, one stable application database endpoint, Database Operations-controlled failover/switchover, explicit fencing/split-brain prevention, reconnect-based application recovery, and no assumption that replicas replace backups; no HA product selected | PostgreSQL | Infra/Doc | Approve PostgreSQL replication/failover topology, replication mode, failover authority, connection behavior, maintenance behavior and ownership. | docs(database): define postgres high availability model | HPR-P1-001 + owner decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15512 | HPR-P1-003 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6026 | HPR-P1-003 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13772 | HPR-P1-003 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-455"></a>
## HAPI-455 — define disaster recovery objectives

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-004

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Obtain owner-approved RTO/RPO, backup frequency/retention and WAL/PITR strategy; keep values TBD until approved.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/operations/DISASTER_RECOVERY_OBJECTIVES.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/architecture/RUNTIME_ARCHITECTURE.md`
- `doc/operations/DISASTER_RECOVERY_OBJECTIVES.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-004 disaster-recovery objective evidence

- execution base SHA: `1fa7f25e6985287cf6548653b99312afcdb9bce0`;
- pre-task documentation validation: run #1 / run id `37340736184`: **SUCCESS**;
- pre-task one-time full CI after workflow split: run #534 / run id `37340736111`: **SUCCESS**;
- owner acceptance date: 2026-10-05;
- approved production RTO: **≤ 60 minutes**, measured from formal DR declaration to acceptance of the recovered HidraAPI production service;
- approved production RPO: **≤ 5 minutes**, measured as the maximum acceptable gap between the selected recovery point and the latest production data that must be recoverable;
- continuous PostgreSQL WAL archiving is required to support PITR and the approved RPO;
- backup coverage requires at least one successful recoverable base/full backup per 24-hour period plus the WAL required to reach approved PITR targets;
- operational backup retention is **35 days**;
- monthly recovery points are retained according to applicable enterprise/records-governance policy; HPR-P1-004 does not invent a longer monthly-retention duration where policy evidence is absent;
- backup copies must be operationally independent of the live primary/standby failure domain; a replica is not a backup;
- DR declaration authority follows the approved incident-governance model; Database Operations owns database restore/PITR execution, Platform/Operations owns runtime recovery, and service acceptance requires the accountable operational/business authority;
- recovery evidence must include selected recovery point, achieved data-loss interval, elapsed recovery time, database consistency, Flyway/JPA validation, application health, authentication/authorization, representative read/write acceptance, and residual issues;
- no backup, object-storage, archive, snapshot, orchestration, or DR product is selected by this HPR.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `354c34323e75e8a3684b0d811182cea9a06e591d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/architecture/RUNTIME_ARCHITECTURE.md:182](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/architecture/RUNTIME_ARCHITECTURE.md#L182)
- [doc/model-remediation/RECONCILIATION.md:13773](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13773)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6027](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6027)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:195](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L195)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2155](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2155)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15513](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15513)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:195 | HPR-P1-004 | COMPLETED — owner-approved DR objectives documented: RTO ≤ 60 minutes, RPO ≤ 5 minutes, continuous WAL archiving/PITR, successful daily backup coverage, 35-day operational backup retention, retained monthly recovery points subject to enterprise records policy, independent backup storage/failure domain, role-based recovery authority, and mandatory restore/PITR evidence; no backup product selected | DR | Infra/Doc | Obtain owner-approved RTO/RPO, backup frequency/retention and WAL/PITR strategy; keep values TBD until approved. | docs(operations): define disaster recovery objectives | owner decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15513 | HPR-P1-004 | COMPLETED within recorded scope | COMPLETED_PRESERVED | DR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6027 | HPR-P1-004 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13773 | HPR-P1-004 | COMPLETED within recorded scope | COMPLETED_PRESERVED | DR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-456"></a>
## HAPI-456 — add disaster recovery runbook

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-005

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-455

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create doc/operations/DISASTER_RECOVERY_RUNBOOK.md with declaration authority, recovery roles, dependency order, restore steps and acceptance checks based on approved objectives.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/operations/DISASTER_RECOVERY_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/operations/DISASTER_RECOVERY_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-005 disaster-recovery runbook evidence

- execution base SHA: `354c34323e75e8a3684b0d811182cea9a06e591d`;
- pre-task lightweight documentation validation: run #2 / run id `37342061352`: **SUCCESS**;
- runbook is derived strictly from approved HPR-P1-004 objectives;
- RTO remains **≤ 60 minutes** and RPO remains **≤ 5 minutes**;
- recovery sequence covers declaration/containment, role activation, recovery-point selection, database restore, WAL/PITR, single-writer validation, stable endpoint restoration, secrets/configuration, application startup, Flyway/JPA validation, authentication/authorization, representative read/write acceptance, traffic restoration, and RTO/RPO evidence;
- explicit stop/abort conditions prevent recovery from an untrusted point, dual-primary state, failed schema validation, failed security acceptance, or unknown write authority;
- no backup, storage, PostgreSQL HA, load-balancer, orchestration, secret-manager, or DR product/command is invented;
- measured restore/PITR execution remains HPR-P1-012 scope and production DR readiness remains NOT ESTABLISHED.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-455. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `f79b7875194f50460741179f63880ed176673844` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13774](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13774)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6028](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6028)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:196](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L196)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2173](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2173)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15514](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15514)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:196 | HPR-P1-005 | COMPLETED — platform-neutral DR runbook created from the approved HPR-P1-004 objectives, covering declaration/containment, role activation, recovery-point selection, PostgreSQL restore + WAL/PITR, single-writer verification, stable endpoint restoration, secrets/configuration, HidraAPI startup, Flyway/JPA validation, security/read-write acceptance, traffic restoration, RTO/RPO measurement, abort/escalation conditions, and mandatory evidence; vendor-specific commands remain deferred | DR | Doc | Create `doc/operations/DISASTER_RECOVERY_RUNBOOK.md` with declaration authority, recovery roles, dependency order, restore steps and acceptance checks based on approved objectives. | docs(operations): add disaster recovery runbook | HPR-P1-004
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15514 | HPR-P1-005 | COMPLETED within recorded scope | COMPLETED_PRESERVED | DR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6028 | HPR-P1-005 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13774 | HPR-P1-005 | COMPLETED within recorded scope | COMPLETED_PRESERVED | DR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-457"></a>
## HAPI-457 — add high availability architecture

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-006

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-453

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md covering approved application/database redundancy, failover, connection behavior and maintenance failover.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-006 high-availability architecture evidence

- execution base SHA: `f79b7875194f50460741179f63880ed176673844`;
- pre-task lightweight documentation validation: run #3 / run id `37342929433`: **SUCCESS**;
- application HA model: minimum two simultaneously active HidraAPI nodes behind a product-neutral managed load-distribution boundary;
- REST correctness must not depend on sticky sessions; traffic goes only to ready nodes and planned maintenance drains traffic before graceful shutdown;
- process-local `simple` cache cannot hold correctness-critical shared state;
- clustered realtime HA remains NOT ESTABLISHED while the in-process STOMP simple broker remains; an approved external/shared cross-node mechanism is required before such a claim;
- PostgreSQL HA model: exactly one writable primary, at least one local streaming standby using synchronous replication where approved latency permits, optional remote asynchronous standby, and one stable application-facing endpoint;
- Database Operations controls database failover/switchover; failover must fence/prevent dual-writer state;
- HidraAPI recovers database connectivity through reconnect to the stable endpoint; in-flight transaction survival/global transparent retry is not claimed;
- application and database maintenance procedures are coordinated but product-neutral;
- no runtime, load-balancer, proxy, HA manager, fencing, broker, cache, VM/container, cloud, or orchestration product is selected;
- measured failover and degraded-capacity evidence remains HPR-P1-012 scope.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-453. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `59c8b913e703e08386bab49850405e307d16002d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13775](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13775)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6029](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6029)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:197](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L197)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2185](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2185)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15515](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15515)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:197 | HPR-P1-006 | COMPLETED — canonical product-neutral HA architecture consolidates the approved application and PostgreSQL models: minimum two active HidraAPI nodes, readiness-based traffic admission, stateless REST/no sticky-session correctness, node-local cache constraints, realtime clustering restriction, one writable PostgreSQL primary with local synchronous standby where latency permits, optional remote asynchronous standby, stable database endpoint, controlled/fenced failover, maintenance drain/switchover, and explicit degraded-mode/acceptance evidence; implementation products and measured failover remain pending | HA | Doc | Create `doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md` covering approved application/database redundancy, failover, connection behavior and maintenance failover. | docs(operations): add high availability architecture | HPR-P1-002..003
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15515 | HPR-P1-006 | COMPLETED within recorded scope | COMPLETED_PRESERVED | HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6029 | HPR-P1-006 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13775 | HPR-P1-006 | COMPLETED within recorded scope | COMPLETED_PRESERVED | HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-459"></a>
## HAPI-459 — enforce openapi compatibility

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-008

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add real OpenAPI compatibility/breaking-change validation; generation/upload alone is not compatibility validation.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/scripts/openapi_compatibility.py`
- `.github/workflows/ci.yml`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/scripts/openapi_compatibility.py`
- `.github/workflows/ci.yml`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-008 OpenAPI compatibility evidence

- execution base SHA: `656a42256d3849c78e1b93d3792e7cceceed4123`;
- pre-task lightweight documentation validation: run #5 / run id `37343927263`: **SUCCESS**;
- full CI now checks out complete Git history so the actual compatibility base revision is available;
- for pull requests the comparison base is the PR base SHA; for pushes it is the event `before` SHA; manual runs use the current parent commit;
- CI generates the current OpenAPI contract from the current application and independently builds/starts the exact base revision to generate the baseline contract;
- repository-owned `.github/scripts/openapi_compatibility.py` fails CI on supported breaking changes instead of treating artifact generation/upload as compatibility evidence;
- covered break classes include removed paths/operations/parameters, new required parameters/request body/properties, request enum contraction, schema type changes, removed response codes/content types/properties, required-response guarantee weakening, response enum expansion, and public-to-authenticated operation tightening;
- compatibility validation runs before OpenAPI artifact upload;
- no API compatibility result is claimed until the post-commit full CI run completes successfully.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `39da2f1bf9bf4e6305b29a6b5bfd34a287bc6fd2` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13777](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13777)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6031](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6031)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:199](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L199)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2216](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2216)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15517](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15517)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:199 | HPR-P1-008 | COMPLETED — CI now generates OpenAPI for the exact current revision and actual Git base revision, then executes a repository-owned backward-compatibility checker that fails on supported breaking changes including removed paths/operations/parameters/responses/content types, newly required request inputs, incompatible request/response schema changes, new response enum values, and public-to-authenticated operation changes; OpenAPI artifact publication remains after the gate | CI / API | Infra | Add real OpenAPI compatibility/breaking-change validation; generation/upload alone is not compatibility validation. | ci(api): enforce openapi compatibility | P0 closed
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15517 | HPR-P1-008 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CI / API; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6031 | HPR-P1-008 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13777 | HPR-P1-008 | COMPLETED within recorded scope | COMPLETED_PRESERVED | CI / API; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-460"></a>
## HAPI-460 — add controlled deployment pipeline

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-009

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-458

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

After deployment target approval, implement controlled deployment automation, promotion, approval, post-deployment health verification and rollback; document in CI_CD_RELEASE_GUIDE.md.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `.github/workflows/release.yml`
- `doc/operations/CI_CD_RELEASE_GUIDE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/release/deploy-production-node.sh`
- `ops/production/release/install-release.sh`
- `ops/production/release/rollback-release.sh`
- `ops/production/release/validate-release-artifacts.sh`
- `ops/production/systemd/hidra-api.service`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `.github/workflows/release.yml`
- `doc/operations/CI_CD_RELEASE_GUIDE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/release/deploy-production-node.sh`
- `ops/production/release/install-release.sh`
- `ops/production/release/rollback-release.sh`
- `ops/production/release/validate-release-artifacts.sh`
- `ops/production/systemd/hidra-api.service`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-458. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `deb24ec6d3707b94bae7704581ad30877516fc78` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13778](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13778)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6032](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6032)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:200](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L200)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15518](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15518)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:200 | HPR-P1-009 | BLOCKED-DECISION — independent P1 audit confirms deployment automation is not implemented; retained as the original design-stage task and operationally superseded by HPR-P1-018 after HPR-P1-013 selects the production target | CD | Infra/Doc | After deployment target approval, implement controlled deployment automation, promotion, approval, post-deployment health verification and rollback; document in `CI_CD_RELEASE_GUIDE.md`. | ci(release): add controlled deployment pipeline | HPR-P1-007 + deployment target decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15518 | HPR-P1-009 | Historical BLOCKED-DECISION; superseded operationally by 018/019 | INDEPENDENT_UNCHANGED | CD; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6032 | HPR-P1-009 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13778 | HPR-P1-009 | Historical BLOCKED-DECISION; superseded operationally by 018/019 | INDEPENDENT_UNCHANGED | CD; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-461"></a>
## HAPI-461 — establish observability operating model

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-010

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-452

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create OBSERVABILITY_AND_SRE.md and implement approved alerting from existing Actuator/Prometheus signals; do not invent SLOs.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/operations/OBSERVABILITY_AND_SRE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/operations/OBSERVABILITY_AND_SRE.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-010 observability/SRE evidence

- execution base SHA: `39da2f1bf9bf4e6305b29a6b5bfd34a287bc6fd2`;
- pre-task exact-head full CI / OpenAPI compatibility run #535 / run id `37344473469`: **SUCCESS**;
- existing application signals are limited to Spring Boot Actuator health/info/metrics/Prometheus surfaces, readiness/liveness probes, JVM/Spring/Hikari-generated metrics, environment/application metric tags, and correlation/request/actor-enriched application logging;
- repository search found no current custom Micrometer `MeterRegistry`/`Counter`/`Timer`/`Gauge` instrumentation to claim as existing;
- canonical operating model defines collection domains, dashboards/views, alert condition classes, severity/ownership routing, evidence, incident linkage, HA/DR observability requirements, and production validation expectations;
- executable alerting is NOT IMPLEMENTED because no production metrics collector, alert manager/router, dashboard platform, log aggregation/SIEM product, on-call integration, numeric alert thresholds, or SLO/error-budget values are owner-approved;
- no Prometheus-rule syntax, Grafana/SIEM product, paging integration, numeric latency/error-rate threshold, availability target, or retention duration was invented;
- HPR-P1-010 remains BLOCKED-DECISION until the required platform/threshold decisions are approved, after which a narrow implementation commit must bind the documented conditions to the selected tooling.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-452. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `b79648f5fa1e2a337e29213aad5b3a08447329db` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13779](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13779)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6033](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6033)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:201](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L201)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2229](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2229)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15519](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15519)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:201 | HPR-P1-010 | BLOCKED-DECISION — operating model is complete, but independent P1 audit confirms executable alerting/SRE policy is not implemented; retained as the design-stage task and operationally completed only through HPR-P1-019 after HPR-P1-013 selects the monitoring/alert-routing platform and owners approve SLIs/SLOs/thresholds | Observability | Infra/Doc | Create `OBSERVABILITY_AND_SRE.md` and implement approved alerting from existing Actuator/Prometheus signals; do not invent SLOs. | docs(operations): establish observability operating model | HPR-P1-001
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15519 | HPR-P1-010 | Historical BLOCKED-DECISION; superseded operationally by 018/019 | INDEPENDENT_UNCHANGED | Observability; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6033 | HPR-P1-010 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13779 | HPR-P1-010 | Historical BLOCKED-DECISION; superseded operationally by 018/019 | INDEPENDENT_UNCHANGED | Observability; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-462"></a>
## HAPI-462 — add database operations runbook

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-011

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-454

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create DATABASE_OPERATIONS_RUNBOOK.md covering Flyway, backup/restore, connection exhaustion, failover, maintenance and migration failures.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/database/DATABASE_OPERATIONS_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/database/DATABASE_OPERATIONS_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-011 database-operations runbook evidence

- execution base SHA: `b79648f5fa1e2a337e29213aad5b3a08447329db`;
- pre-task lightweight documentation validation: run #7 / run id `37347126292`: **SUCCESS**;
- runbook is derived from HPR-P1-003 PostgreSQL HA, HPR-P1-004 DR objectives, HPR-P1-005 DR procedure, HPR-P1-007 deployment controls, and current production datasource/Flyway/JPA configuration;
- current production pool defaults are recorded as implementation defaults only: Hikari maximum 30, minimum idle 10, connection timeout 30s, validation timeout 5s; no capacity threshold is inferred from them;
- Flyway remains migration authority with validate-on-migrate and clean disabled; applied migrations are immutable and migration repair/override is not authorized as a shortcut;
- database operations cover connection exhaustion, failover/switchover, backup/WAL/PITR, maintenance, migration failures, credential rotation coordination, data-integrity incidents and evidence retention;
- no Patroni/Pgpool/HAProxy/pgBackRest/Barman/cloud database/monitoring platform or vendor-specific command is invented;
- HPR-P1-012 remains responsible for measured restore/PITR and failover execution and cannot close while HPR-P1-009/HPR-P1-010 blockers or survivability evidence gaps remain.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-454. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `f46f6c1ed7e324f66a0a26422a6dfa6da8bc3689` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13780](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13780)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6034](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6034)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:202](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L202)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2241](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2241)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15520](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15520)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:202 | HPR-P1-011 | COMPLETED — canonical product-neutral database operations runbook created for PostgreSQL/Flyway operations, covering pre-change checks, schema migration authority, migration failure handling, connection-pool exhaustion, primary/standby failover and switchover, backup/WAL/PITR operations, maintenance, credential rotation coordination, data-integrity incidents, observability/evidence requirements, and stop/escalation criteria; no HA/backup/monitoring product or vendor command was invented | Database Operations | Doc | Create `DATABASE_OPERATIONS_RUNBOOK.md` covering Flyway, backup/restore, connection exhaustion, failover, maintenance and migration failures. | docs(database): add database operations runbook | HPR-P1-003..005
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15520 | HPR-P1-011 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Database Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6034 | HPR-P1-011 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13780 | HPR-P1-011 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Database Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-465"></a>
## HAPI-465 — enforce production startup profile

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-014

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-464

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Enforce production-context startup safety so an omitted or wrong Spring profile cannot silently fall back to dev/default behavior in an approved production deployment; validate required production configuration and add automated negative tests for missing/wrong profile and mandatory inputs.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/operations/ENVIRONMENT_CONFIGURATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraProductionStartupGuard.java`
- `src/main/resources/META-INF/spring.factories`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraProductionStartupGuardTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/operations/ENVIRONMENT_CONFIGURATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraProductionStartupGuard.java`
- `src/main/resources/META-INF/spring.factories`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraProductionStartupGuardTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-014 production startup safety evidence

- execution base SHA: `7469a424c84d3b4454ff8db2eda27d660a8e437c`;
- pre-task documentation validation run #10 / run id `37354173438`: **SUCCESS**;
- added `dz.sh.hidra.platform.configuration.HidraProductionStartupGuard` as a Spring `EnvironmentPostProcessor`;
- registered guard in `META-INF/spring.factories` so validation occurs before normal bean creation;
- production hosting contract now uses `HIDRA_ENVIRONMENT=production` as an independent production-intent marker and requires active Spring profile `production`;
- guard validates non-empty datasource URL/username/password, production CORS origins when CORS is enabled, JWT authentication mode, externalized HMAC secret, and bootstrap password when bootstrap is enabled;
- negative unit tests cover wrong profile and missing mandatory production inputs; a complete production contract passes and ordinary dev runtime remains unaffected;
- exact-head full CI is required because this HPR changes executable code/resources/tests; completion must not be treated as verified until that run succeeds.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/platform/configuration/HidraProductionStartupGuardTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-464. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `c6c9037dad3277d418a8f1140df9092ba6bdfd84` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13783](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13783)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6037](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6037)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:205](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L205)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2023](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2023)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15523](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15523)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:205 | HPR-P1-014 | COMPLETED — added a pre-bean Spring EnvironmentPostProcessor production startup guard registered through `META-INF/spring.factories`; production intent now requires the production profile and mandatory datasource/CORS/JWT configuration, rejects disabled/non-JWT production authentication, and requires a bootstrap password when bootstrap is enabled. Automated negative tests cover production-intent + wrong profile and each mandatory production input, while non-production startup remains unaffected. `HIDRA_ENVIRONMENT=production` is now the explicit independent production-hosting marker paired with `SPRING_PROFILES_ACTIVE=production`. | Runtime Safety | Code/Test | Enforce production-context startup safety so an omitted or wrong Spring profile cannot silently fall back to dev/default behavior in an approved production deployment; validate required production configuration and add automated negative tests for missing/wrong profile and mandatory inputs. | fix(runtime): enforce production startup profile | HPR-P1-013 deployment/runtime decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15523 | HPR-P1-014 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Runtime Safety; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6037 | HPR-P1-014 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13783 | HPR-P1-014 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Runtime Safety; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-466"></a>
## HAPI-466 — implement application high availability

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-015

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-453, HAPI-457, HAPI-464

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement the approved two-node HidraAPI runtime on the selected deployment/traffic platform, readiness-based admission/removal, graceful drain/replacement, and safe node-local-state behavior. Resolve production treatment for local cache, background executors, and realtime (shared mechanism, explicit single-active mode, or disabled mode) and demonstrate one-node loss without hidden sticky-session correctness.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/README.md`
- `ops/production/env/hidra.env`
- `ops/production/env/node-a.env`
- `ops/production/env/node-b.env`
- `ops/production/haproxy/hidra-api.cfg`
- `ops/production/scripts/drain-node.sh`
- `ops/production/scripts/rejoin-node.sh`
- `ops/production/scripts/validate-runtime-artifacts.sh`
- `ops/production/scripts/verify-application-ha.sh`
- `ops/production/systemd/hidra-api.service`
- `src/main/java/dz/sh/hidra/platform/realtime/HidraRealtimeConfiguration.java`
- `src/test/java/dz/sh/hidra/platform/realtime/HidraRealtimeConfigurationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/README.md`
- `ops/production/env/hidra.env`
- `ops/production/env/node-a.env`
- `ops/production/env/node-b.env`
- `ops/production/haproxy/hidra-api.cfg`
- `ops/production/scripts/drain-node.sh`
- `ops/production/scripts/rejoin-node.sh`
- `ops/production/scripts/validate-runtime-artifacts.sh`
- `ops/production/scripts/verify-application-ha.sh`
- `ops/production/systemd/hidra-api.service`
- `src/main/java/dz/sh/hidra/platform/realtime/HidraRealtimeConfiguration.java`
- `src/test/java/dz/sh/hidra/platform/realtime/HidraRealtimeConfigurationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-015 application HA implementation evidence

- execution base SHA: `c6c9037dad3277d418a8f1140df9092ba6bdfd84`;
- pre-task full CI run #536 / run id `37354762079`: **SUCCESS**;
- added concrete Linux VM/systemd + HAProxy runtime artifacts under `ops/production/`;
- HAProxy REST backend declares exactly two HidraAPI nodes, round-robin balancing, readiness health checks, and no configured cookie/stick-table affinity;
- HAProxy realtime backend is deliberately single-active on `hidra-api-1`, matching the owner-approved P1 operating mode;
- `HidraRealtimeConfiguration` now uses `@ConditionalOnProperty(hidra.platform.realtime.enabled)`, so realtime is actually disabled on the REST-only secondary node;
- systemd unit uses SIGTERM so Spring graceful shutdown remains authoritative and restarts failed processes with `Restart=on-failure`;
- drain/rejoin scripts use the HAProxy runtime socket for planned maintenance;
- static validation checks backend cardinality, readiness checking, no sticky-session directives, node realtime roles and shell syntax; full CI now executes it;
- destructive verification script stops one systemd node at a time over SSH, probes readiness through HAProxy, restores the node and captures timestamped evidence;
- the destructive exercise has not been run by this repository-only task; HPR-P1-015 therefore remains IMPLEMENTED-PENDING-EXERCISE and HPR-P1-012 must not treat configuration presence as measured survivability.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/platform/realtime/HidraRealtimeConfigurationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-453, HAPI-457, HAPI-464. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `9053572efc804bcba44b14cd3aec4fa8d4900746` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13784](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13784)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6038](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6038)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:206](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L206)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2009](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2009)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15524](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15524)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:206 | HPR-P1-015 | COMPLETED — production-equivalent both-node loss/rejoin evidence retained by HPR-P1-029;  repository implementation now binds the approved Linux VM/systemd/HAProxy design: two REST nodes, readiness-based HAProxy health admission, no configured REST stickiness, systemd SIGTERM/restart semantics, drain/rejoin scripts, node-specific runtime roles, and an explicit single-active realtime backend. `hidra.platform.realtime.enabled` now actually conditions STOMP broker creation, allowing node B realtime to be disabled. Static CI validation and a destructive production-equivalent one-node-loss evidence script are included. Actual two-VM node-loss evidence is still required before this HPR can be called measured/verified and remains an HPR-P1-012 closure prerequisite. | Application HA | Infra/Code/Test/Doc | Implement the approved two-node HidraAPI runtime on the selected deployment/traffic platform, readiness-based admission/removal, graceful drain/replacement, and safe node-local-state behavior. Resolve production treatment for local cache, background executors, and realtime (shared mechanism, explicit single-active mode, or disabled mode) and demonstrate one-node loss without hidden sticky-session correctness. | feat(runtime): implement application high availability | HPR-P1-013 + HPR-P1-002 + HPR-P1-006
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15524 | HPR-P1-015 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Application HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6038 | HPR-P1-015 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13784 | HPR-P1-015 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Application HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-467"></a>
## HAPI-467 — implement postgres failover recovery

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-016

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-454, HAPI-462, HAPI-464

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement the selected PostgreSQL primary/standby replication, promotion/fencing and stable-endpoint mechanism; define explicit Hikari/JDBC recovery settings including approved max-lifetime/keepalive/idle behavior where applicable; document interrupted-transaction responsibility; execute controlled failover and prove connection replacement, single-writer authority, endpoint redirection and application read/write recovery.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `doc/database/POSTGRES_FAILOVER_IMPLEMENTATION.md`
- `doc/operations/ENVIRONMENT_CONFIGURATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/README.md`
- `ops/production/postgres/etcd/etcd.env.tpl`
- `ops/production/postgres/haproxy/hidra-postgres.cfg.tpl`
- `ops/production/postgres/patroni/patroni.yml.tpl`
- `ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh`
- `ops/production/postgres/scripts/verify-postgres-failover.sh`
- `src/main/resources/application-production.properties`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `doc/database/POSTGRES_FAILOVER_IMPLEMENTATION.md`
- `doc/operations/ENVIRONMENT_CONFIGURATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/README.md`
- `ops/production/postgres/etcd/etcd.env.tpl`
- `ops/production/postgres/haproxy/hidra-postgres.cfg.tpl`
- `ops/production/postgres/patroni/patroni.yml.tpl`
- `ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh`
- `ops/production/postgres/scripts/verify-postgres-failover.sh`
- `src/main/resources/application-production.properties`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-016 PostgreSQL failover implementation evidence

- execution base SHA: `9053572efc804bcba44b14cd3aec4fa8d4900746`;
- pre-task full CI run #537 / run id `37356537131`: **SUCCESS**;
- pre-task documentation validation run #12 / run id `37356537237`: **SUCCESS**;
- added renderable Patroni template using a three-member etcd3 DCS, TLS/mTLS placeholders, PostgreSQL streaming replication, checksums, replication slots and `pg_rewind`;
- synchronous replication remains explicitly parameterized because the approved policy is synchronous local replication where measured latency permits; no unsupported latency decision was invented;
- added three-member TLS-authenticated etcd configuration template;
- added HAProxy PostgreSQL write-endpoint template that checks Patroni `/primary` and exposes only the authoritative primary to HidraAPI;
- production Hikari connection timeout, validation timeout, idle timeout, max lifetime and keepalive are explicit/externalized; idle=600000 ms, maxLifetime=1800000 ms, keepalive=120000 ms are engineering lifecycle baselines, not SLO/capacity claims;
- documented that interrupted/in-doubt transactions are not blindly retried and require explicit use-case idempotency semantics;
- added static CI validation of Patroni/etcd/HAProxy/Hikari artifacts;
- added destructive controlled Patroni switchover harness that records topology, requires exactly one primary, verifies reversible write capability through the stable endpoint before/after role change, verifies former-primary demotion and waits for HidraAPI readiness recovery;
- the production-equivalent role-change exercise has not been executed by this repository-only task; HPR-P1-016 remains IMPLEMENTED-PENDING-EXERCISE and HPR-P1-012 must require measured evidence.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-454, HAPI-462, HAPI-464. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `9e1503154e59c7de5b06ca00bb045c2328770c1b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13785](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13785)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6039](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6039)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:207](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L207)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1994](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1994)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15525](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15525)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:207 | HPR-P1-016 | COMPLETED — production-equivalent Patroni failover/single-writer/fencing/Hikari recovery evidence retained by HPR-P1-029;  repository implementation now binds Patroni + three-member TLS-authenticated etcd + HAProxy stable write endpoint to the approved PostgreSQL HA model. HAProxy routes only to the node whose Patroni `/primary` health check succeeds. Production Hikari settings now explicitly expose connection/validation/idle/max-lifetime/keepalive controls with documented transaction-failure responsibility. Static CI validation plus a destructive controlled Patroni switchover harness verify single-primary topology, endpoint writability, former-primary demotion, new connection establishment and HidraAPI readiness recovery. Actual production-equivalent failover/switchover evidence is still required before this HPR is measured/verified. | PostgreSQL HA / Connections | Infra/Code/Test/Doc | Implement the selected PostgreSQL primary/standby replication, promotion/fencing and stable-endpoint mechanism; define explicit Hikari/JDBC recovery settings including approved max-lifetime/keepalive/idle behavior where applicable; document interrupted-transaction responsibility; execute controlled failover and prove connection replacement, single-writer authority, endpoint redirection and application read/write recovery. | feat(database): implement postgres failover recovery | HPR-P1-013 + HPR-P1-003 + HPR-P1-011
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15525 | HPR-P1-016 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL HA / Connections; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6039 | HPR-P1-016 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13785 | HPR-P1-016 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL HA / Connections; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-468"></a>
## HAPI-468 — implement backup and pitr operations

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-017

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-455, HAPI-462, HAPI-464

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Bind the approved DR model to the selected backup and storage implementation with executable backup, WAL archive, restore, recovery-target/PITR, service-control and validation procedures; enforce the 35-day operational window and the owner-approved monthly retention/hold rule; verify protected-copy independence and produce a restorable current-schema backup chain.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `doc/database/BACKUP_PITR_IMPLEMENTATION.md`
- `doc/operations/DISASTER_RECOVERY_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/pgbackrest/README.md`
- `ops/production/postgres/pgbackrest/pgbackrest.conf.tpl`
- `ops/production/postgres/pgbackrest/postgresql-archive.conf`
- `ops/production/postgres/scripts/check-backup-health.sh`
- `ops/production/postgres/scripts/run-full-backup.sh`
- `ops/production/postgres/scripts/validate-backup-pitr-artifacts.sh`
- `ops/production/postgres/scripts/verify-pitr-restore.sh`
- `ops/production/postgres/systemd/hidra-pgbackrest-full.service`
- `ops/production/postgres/systemd/hidra-pgbackrest-full.timer`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `doc/database/BACKUP_PITR_IMPLEMENTATION.md`
- `doc/operations/DISASTER_RECOVERY_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/pgbackrest/README.md`
- `ops/production/postgres/pgbackrest/pgbackrest.conf.tpl`
- `ops/production/postgres/pgbackrest/postgresql-archive.conf`
- `ops/production/postgres/scripts/check-backup-health.sh`
- `ops/production/postgres/scripts/run-full-backup.sh`
- `ops/production/postgres/scripts/validate-backup-pitr-artifacts.sh`
- `ops/production/postgres/scripts/verify-pitr-restore.sh`
- `ops/production/postgres/systemd/hidra-pgbackrest-full.service`
- `ops/production/postgres/systemd/hidra-pgbackrest-full.timer`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-017 exact-head verification evidence

- repaired exact-head SHA: `719c8bf06f3f54c5b0fa2c7a4369ae08ac7e0251`;
- full CI #544 / run id `37388204860`: **SUCCESS**;
- documentation validation #20 / run id `37388204991`: **SUCCESS**;
- roadmap duplicate-tail corruption introduced during the prior HPR-P1-017 status replacement was removed without changing the approved HPR status or closure requirements.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-455, HAPI-462, HAPI-464. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `4923d8cc3a770e108e9171feb3d53a833fa7300f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13786](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13786)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6040](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6040)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:208](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L208)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1893](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1893)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1900](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1900)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1907](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1907)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1980](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1980)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15526](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15526)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:208 | HPR-P1-017 | COMPLETED — production-equivalent backup/PITR, repo2 restore and bootstrap retention evidence retained by HPR-P1-029;  pgBackRest now uses repo1 for daily 35-day operational recovery and repo2 for owner-approved monthly retention under `HIDRA-P1-BACKUP-RETENTION-001`: one repo2 full backup per month, count-based retention of 12 full backups, WAL archived to all configured repositories with async archiving, monthly continuity/age checks, and PITR harness selection of repo1 or repo2. Repository binding is complete; production-equivalent proof of repo/storage independence, retained repo2 restore, WAL continuity, intentional PITR and measured RPO/RTO remains required. | Backup / WAL / PITR | Infra/Test/Doc | Bind the approved DR model to the selected backup and storage implementation with executable backup, WAL archive, restore, recovery-target/PITR, service-control and validation procedures; enforce the 35-day operational window and the owner-approved monthly retention/hold rule; verify protected-copy independence and produce a restorable current-schema backup chain. | feat(database): implement backup and pitr operations | HPR-P1-013 + HPR-P1-004..005 + HPR-P1-011
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15526 | HPR-P1-017 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Backup / WAL / PITR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6040 | HPR-P1-017 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13786 | HPR-P1-017 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Backup / WAL / PITR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-471"></a>
## HAPI-471 — operationalize database procedures

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-020

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-462, HAPI-464, HAPI-467

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Make the database runbook executable for the selected PostgreSQL/backup/HA stack: add reviewed maintenance and inspection commands for sessions, locks, long-running work, VACUUM/ANALYZE strategy, index/bloat/capacity checks, replication/backup health and Flyway failure diagnosis; validate the commands in production-equivalent infrastructure and cross-link them to HA/backup procedures.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/workflows/ci.yml`
- `doc/database/DATABASE_OPERATIONS_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/DATABASE_OPERATIONS.md`
- `ops/production/postgres/scripts/database-health-report.sh`
- `ops/production/postgres/scripts/reindex-concurrently.sh`
- `ops/production/postgres/scripts/terminate-session.sh`
- `ops/production/postgres/scripts/vacuum-analyze-table.sh`
- `ops/production/postgres/scripts/validate-database-operations.sh`
- `ops/production/postgres/sql/inspect-capacity.sql`
- `ops/production/postgres/sql/inspect-flyway.sql`
- `ops/production/postgres/sql/inspect-index-usage.sql`
- `ops/production/postgres/sql/inspect-locks.sql`
- `ops/production/postgres/sql/inspect-long-running.sql`
- `ops/production/postgres/sql/inspect-replication.sql`
- `ops/production/postgres/sql/inspect-sessions.sql`
- `ops/production/postgres/sql/inspect-vacuum-analyze.sql`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/workflows/ci.yml`
- `doc/database/DATABASE_OPERATIONS_RUNBOOK.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/DATABASE_OPERATIONS.md`
- `ops/production/postgres/scripts/database-health-report.sh`
- `ops/production/postgres/scripts/reindex-concurrently.sh`
- `ops/production/postgres/scripts/terminate-session.sh`
- `ops/production/postgres/scripts/vacuum-analyze-table.sh`
- `ops/production/postgres/scripts/validate-database-operations.sh`
- `ops/production/postgres/sql/inspect-capacity.sql`
- `ops/production/postgres/sql/inspect-flyway.sql`
- `ops/production/postgres/sql/inspect-index-usage.sql`
- `ops/production/postgres/sql/inspect-locks.sql`
- `ops/production/postgres/sql/inspect-long-running.sql`
- `ops/production/postgres/sql/inspect-replication.sql`
- `ops/production/postgres/sql/inspect-sessions.sql`
- `ops/production/postgres/sql/inspect-vacuum-analyze.sql`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-020 database operations implementation evidence

- execution base SHA: `68e1b9f5d4b29078c8918ce61c5732b99b00874e`;
- pre-task exact-head full CI #541 / run id `37379496810`: **SUCCESS**;
- pre-task documentation validation #16 / run id `37379496826`: **SUCCESS**;
- added read-only SQL inspection procedures for sessions, blocking chains, long-running work, VACUUM/ANALYZE history/dead tuples, index usage, database/schema/table capacity, replication/slots and Flyway history;
- added composite `database-health-report.sh` that runs the SQL inspections and records Patroni topology plus pgBackRest check/info through the approved stable-endpoint/tooling model;
- added guarded `VACUUM (ANALYZE, VERBOSE)`, `REINDEX INDEX CONCURRENTLY` and targeted `pg_terminate_backend` procedures requiring explicit operator opt-in;
- index inspection does not infer that an unused index is safe to drop; maintenance remains reviewed Database Operations action;
- routine autovacuum remains the default; blanket manual vacuum/reindex scheduling is not introduced;
- static CI validates required SQL content and shell syntax/guardrails;
- database runbook now cross-links concrete Patroni failover and executable database-operations procedures;
- no production-equivalent command execution or post-maintenance acceptance is claimed; HPR-P1-020 remains IMPLEMENTED-PENDING-PRODUCTION-EQUIVALENT-VALIDATION.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-462, HAPI-464, HAPI-467. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `d91535016026683970554b0bdb9db4b9beafb982` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13789](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13789)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6043](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6043)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:211](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L211)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1934](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1934)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15529](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15529)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:211 | HPR-P1-020 | COMPLETED — production-equivalent diagnostic/maintenance and authenticated pre/post application acceptance evidence retained by HPR-P1-029;  added executable read-only PostgreSQL inspection procedures for sessions, blocking locks, long-running queries/transactions, VACUUM/ANALYZE state, index usage, capacity, replication/slots and Flyway history; a composite health report also records Patroni topology and pgBackRest health. Guarded maintenance scripts provide explicit VACUUM (ANALYZE, VERBOSE), REINDEX INDEX CONCURRENTLY and targeted pg_terminate_backend procedures only with operator opt-in. CI statically validates all SQL/shell artifacts and the database runbook now cross-links the selected Patroni/pgBackRest implementation. Actual execution against production-equivalent PostgreSQL and controlled maintenance/post-acceptance evidence remains required before measured verification. | Database Operations | Infra/Test/Doc | Make the database runbook executable for the selected PostgreSQL/backup/HA stack: add reviewed maintenance and inspection commands for sessions, locks, long-running work, VACUUM/ANALYZE strategy, index/bloat/capacity checks, replication/backup health and Flyway failure diagnosis; validate the commands in production-equivalent infrastructure and cross-link them to HA/backup procedures. | docs(database): operationalize database procedures | HPR-P1-013 + HPR-P1-016..017 + HPR-P1-011
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15529 | HPR-P1-020 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Database Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6043 | HPR-P1-020 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13789 | HPR-P1-020 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Database Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-472"></a>
## HAPI-472 — backup/PITR binding evidence

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-021

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Infra/Test/Doc

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-021 backup/PITR binding evidence

- execution base SHA: `58d2e7d463d5570fc6d6f20e899d162600a939ab`;
- pre-task documentation validation #22 / run id `37392680174`: **SUCCESS**;
- both daily repo1 and monthly repo2 systemd services now provide the configuration path required by their backup scripts;
- the bound path is `/etc/pgbackrest/pgbackrest.conf`, consistent with the repository pgBackRest exporter default; rendered environment-specific values remain outside Git;
- `capture-flyway-history.sh` captures the successful versioned Flyway `version|checksum` set from an approved current-schema source database into retained exercise evidence;
- `verify-pitr-restore.sh` requires that manifest and fails unless the restored successful versioned Flyway history matches it exactly; the former count-only `>= 82` acceptance gate is removed;
- actual scheduled backup/WAL execution and production-equivalent PITR remain HPR-P1-029 scope.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13790](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13790)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6044](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6044)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:212](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L212)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1883](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1883)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15530](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15530)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:212 | HPR-P1-021 | COMPLETED — both committed pgBackRest backup services now bind the rendered `/etc/pgbackrest/pgbackrest.conf` path through `HIDRA_PGBACKREST_CONFIG`; PITR acceptance no longer uses the audited migration-count threshold and instead requires an approved exact successful Flyway `version | checksum` manifest, compares the restored history byte-for-byte, and retains digest/history evidence. A repository helper captures the trusted baseline without committing credentials or environment-specific data. Production-equivalent scheduled backup/WAL/PITR execution remains HPR-P1-029 evidence scope. | Backup / PITR | Infra/Test/Doc | Bind `HIDRA_PGBACKREST_CONFIG` into both committed pgBackRest systemd backup services through the approved external configuration mechanism; keep secrets external; harden restore/PITR validation so current-schema acceptance verifies the exact applied Flyway version/checksum set rather than only a migration count; preserve repo1/repo2 selection and WAL continuity checks. | fix(database): bind scheduled backup configuration | HPR-P1-017
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15530 | HPR-P1-021 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Backup / PITR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6044 | HPR-P1-021 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13790 | HPR-P1-021 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Backup / PITR; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-473"></a>
## HAPI-473 — align patroni health checks with tls

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-022

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-467

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Make HAProxy primary health checks compatible with Patroni REST mutual TLS and required client authentication; validate the rendered HAProxy/Patroni transport contract; strengthen failover verification to record exactly one writable primary, former-primary demotion and the selected fencing/split-brain-prevention evidence.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/database/POSTGRES_FAILOVER_IMPLEMENTATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/README.md`
- `ops/production/postgres/haproxy/hidra-postgres.cfg.tpl`
- `ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh`
- `ops/production/postgres/scripts/verify-postgres-failover.sh`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/database/POSTGRES_FAILOVER_IMPLEMENTATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/postgres/README.md`
- `ops/production/postgres/haproxy/hidra-postgres.cfg.tpl`
- `ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh`
- `ops/production/postgres/scripts/verify-postgres-failover.sh`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-022 Patroni/HAProxy TLS alignment evidence

- first exact-head full CI #547 / run id `37394262823`: **FAILED** at `Validate production PostgreSQL HA artifacts`; diagnosis found malformed generated content in `validate-postgres-ha-artifacts.sh`, not a Patroni/HAProxy runtime-contract failure; Maven and later stages were skipped;
- repair replaces the corrupted validator with a clean fail-fast static validator using fixed-string assertions for the mTLS contract and shell syntax validation for the failover harness; HPR-P1-023 remains blocked until the repaired exact head passes full CI;

- execution base SHA: `b630e6f29bcedbc882346495f3187673a37ca193`;
- pre-task exact-head full CI #546 / run id `37393293127`: **SUCCESS**;
- Patroni REST remains configured with `certfile`, `keyfile`, `cafile` and `verify_client: required`;
- HAProxy `/primary` checks now use `check-ssl`, `verify required`, the rendered Patroni REST CA, an externally rendered client certificate/private-key PEM, and per-node `verifyhost` values while PostgreSQL data traffic remains independently configured;
- static HA validation rejects the former plain port-8008 HTTP check and asserts the mutual-TLS contract on both PostgreSQL nodes;
- the failover harness now records `patronictl show-config` and exactly-one-primary cluster authority context before and after controlled switchover and still verifies former-primary demotion;
- controlled switchover is not mislabeled as a network-partition/fencing test; HPR-P1-029 retains that measured evidence requirement.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-467. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `9d6130bf6e0c33e5aeaebe3e3c8b7fcde103d220` — exact registered-message commit; surviving paths matched to current tree.
- `5a2b2de0e8b76a7009efb8f7c8a4dcdfee219841` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13791](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13791)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6045](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6045)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:213](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L213)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1870](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1870)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15531](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15531)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:213 | HPR-P1-022 | COMPLETED — HAProxy PostgreSQL primary health checks now match Patroni's required mutual-TLS REST contract: TLS is applied only to port-8008 health checks, the Patroni server certificate is CA-validated and hostname-verified per node, and HAProxy presents an externally rendered client certificate/private-key PEM while Patroni remains `verify_client: required`. Static validation now rejects the former plain-HTTP form and verifies the Patroni/HAProxy TLS contract. The controlled switchover harness records Patroni dynamic configuration and exactly-one-primary authority context before/after role change plus former-primary demotion; actual partition/fencing exercise evidence remains HPR-P1-029 scope. | PostgreSQL HA | Infra/Test/Doc | Make HAProxy primary health checks compatible with Patroni REST mutual TLS and required client authentication; validate the rendered HAProxy/Patroni transport contract; strengthen failover verification to record exactly one writable primary, former-primary demotion and the selected fencing/split-brain-prevention evidence. | fix(database): align patroni health checks with tls | HPR-P1-016
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15531 | HPR-P1-022 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6045 | HPR-P1-022 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13791 | HPR-P1-022 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-474"></a>
## HAPI-474 — strengthen application ha exercise

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-023

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-466

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Strengthen the two-node HA exercise so it proves both nodes are initially admitted, continuously probes a representative authenticated REST operation during each node loss, verifies survivor continuity, waits for the restarted node to rejoin before the next loss, and retains per-node removal/rejoin evidence without claiming realtime clustering.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/README.md`
- `ops/production/scripts/validate-runtime-artifacts.sh`
- `ops/production/scripts/verify-application-ha.sh`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `ops/production/README.md`
- `ops/production/scripts/validate-runtime-artifacts.sh`
- `ops/production/scripts/verify-application-ha.sh`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-023 application HA exercise hardening evidence

- first exact-head full CI #549 / run id `37395477029`: **FAILED** at `Validate production application HA runtime artifacts`; diagnosis found malformed generated content in `validate-runtime-artifacts.sh`, while `verify-application-ha.sh` retained the intended direct-node, authenticated continuity and rejoin logic; PostgreSQL, Maven and later stages were skipped;
- repair replaces the corrupted runtime validator with a clean fail-fast validator covering two REST nodes, single-active realtime, readiness routing, no stickiness, service lifecycle, HA exercise inputs/continuity functions and shell syntax; HPR-P1-024 remains blocked until the repaired exact head passes full CI;

- execution base SHA: `9d6130bf6e0c33e5aeaebe3e3c8b7fcde103d220`;
- pre-task exact-head full CI #548 / run id `37394622465`: **SUCCESS**;
- the destructive exercise now requires direct node base URLs so both nodes are individually proven ready before the first loss and after each restart;
- `HIDRA_HA_ACCEPTANCE_URL` and external `HIDRA_HA_ACCEPTANCE_CURL_CONFIG` provide a caller-approved authenticated representative REST acceptance without embedding credentials or inventing a business endpoint;
- a continuous probe loop runs across each stop/restart window and any failed representative REST request fails the exercise;
- node 1 must be directly ready again before node 2 may be stopped, and vice versa;
- realtime remains explicitly single-active and is not included in the REST HA claim;
- actual production-equivalent node-loss/rejoin evidence remains HPR-P1-029 scope.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-466. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `bc1aeec77af5c7a4c51e66f7699a352ec3918b91` — exact registered-message commit; surviving paths matched to current tree.
- `3b3adafe165bd71a61bf0f469f9f10b8b26b4acb` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13792](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13792)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6046](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6046)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:214](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L214)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1856](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1856)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15532](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15532)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:214 | HPR-P1-023 | COMPLETED — the application HA exercise now proves both nodes are directly ready before disruption, continuously issues a caller-selected authenticated representative REST request through HAProxy during each one-node loss/rejoin window, fails on any business-request interruption, verifies the restarted node directly becomes ready again before the other node is stopped, and retains separate continuity evidence. Authentication is supplied only through an external curl config and no business endpoint/credential is invented. Actual production-equivalent execution remains HPR-P1-029 evidence scope; P1 realtime remains explicitly single-active and is not claimed HA. | Application HA | Infra/Test/Doc | Strengthen the two-node HA exercise so it proves both nodes are initially admitted, continuously probes a representative authenticated REST operation during each node loss, verifies survivor continuity, waits for the restarted node to rejoin before the next loss, and retains per-node removal/rejoin evidence without claiming realtime clustering. | test(runtime): strengthen application ha exercise | HPR-P1-015
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15532 | HPR-P1-023 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Application HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6046 | HPR-P1-023 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13792 | HPR-P1-023 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Application HA; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-475"></a>
## HAPI-475 — verify application failover recovery

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-024

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-473

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Extend the database failover exercise to prove HidraAPI-managed Hikari connection replacement through the stable endpoint using authenticated database-backed application acceptance before and after role change; record recovery timing and interrupted-transaction responsibility without inventing transparent transaction retry.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `ops/production/postgres/README.md`
- `ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh`
- `ops/production/postgres/scripts/verify-postgres-failover.sh`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `ops/production/postgres/README.md`
- `ops/production/postgres/scripts/validate-postgres-ha-artifacts.sh`
- `ops/production/postgres/scripts/verify-postgres-failover.sh`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-024 application failover recovery evidence

- implementation SHA: `117da3131daa4b98a55098573c4ee933a1c012f9`;
- exact-head full CI #551 / run id `37428720127`: **SUCCESS**;
- the database failover harness now requires a caller-approved authenticated database-backed HidraAPI read acceptance before and after role change;
- authentication is externalized through `HIDRA_APP_CURL_CONFIG` and is not committed or printed;
- authenticated Actuator `hikaricp.connections.creation` is recorded before failover and must increase during recovery, providing application-managed pool replacement evidence rather than inference from fresh `psql` clients;
- recovery elapsed time is recorded from controlled switchover initiation until both application acceptance and Hikari replacement are observed;
- interrupted/in-doubt transactions remain explicitly not treated as successful and are not blindly retried;
- actual production-equivalent failover timing and application recovery evidence remain HPR-P1-029 scope.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-473. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `117da3131daa4b98a55098573c4ee933a1c012f9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13793](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13793)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6047](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6047)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:215](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L215)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1845](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1845)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15533](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15533)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:215 | HPR-P1-024 | COMPLETED — implementation SHA `117da3131daa4b98a55098573c4ee933a1c012f9` passed exact-head full CI #551 / run id `37428720127`. The failover harness now requires authenticated database-backed HidraAPI acceptance before/after role change, records `hikaricp.connections.creation` before failover, requires the application-managed Hikari creation count to increase during recovery, records recovery elapsed time, and explicitly preserves caller/use-case responsibility for interrupted or in-doubt transactions rather than claiming transparent retry. Actual production-equivalent failover execution remains HPR-P1-029 scope. | PostgreSQL / Application Recovery | Code/Test/Infra | Extend the database failover exercise to prove HidraAPI-managed Hikari connection replacement through the stable endpoint using authenticated database-backed application acceptance before and after role change; record recovery timing and interrupted-transaction responsibility without inventing transparent transaction retry. | test(database): verify application failover recovery | HPR-P1-022
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15533 | HPR-P1-024 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL / Application Recovery; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6047 | HPR-P1-024 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13793 | HPR-P1-024 | COMPLETED within recorded scope | COMPLETED_PRESERVED | PostgreSQL / Application Recovery; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-476"></a>
## HAPI-476 — enforce availability preserving rollout

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-025

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-469, HAPI-474

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Harden controlled rollout so the surviving application node is verified before draining the target, drain completion/active-request safety is explicitly checked before installation, rejoin is verified before proceeding, rollback follows the same availability gates, and repository evidence records the required GitHub production Environment reviewer protection without fabricating settings that cannot be observed.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `ops/production/release/deploy-production-node.sh`
- `ops/production/release/validate-release-artifacts.sh`
- `ops/production/scripts/drain-node.sh`
- `ops/production/scripts/rejoin-node.sh`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `ops/production/release/deploy-production-node.sh`
- `ops/production/release/validate-release-artifacts.sh`
- `ops/production/scripts/drain-node.sh`
- `ops/production/scripts/rejoin-node.sh`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-025 availability-preserving rollout evidence

- implementation SHA: `c692ab684ef79c8295d3216adafd6daf5dc1db46`;
- exact-head full CI #552 / run id `37429745480`: **SUCCESS**;
- each target deployment verifies the surviving REST node before drain;
- drain waits for the target node's active HAProxy sessions to reach zero before installation; realtime sessions are also drained for the single-active realtime node;
- successful deployment requires local readiness, authenticated database-backed acceptance and HAProxy UP state before rejoin is accepted;
- schema-compatible rollback follows equivalent readiness/acceptance/rejoin gates;
- required GitHub `production` Environment reviewer protection is documented owner policy but cannot be observed through the available connector and is therefore not fabricated as verified;
- actual approval-history plus production-equivalent exact-artifact deployment/rollback evidence remain HPR-P1-029 scope.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-469, HAPI-474. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `c692ab684ef79c8295d3216adafd6daf5dc1db46` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13794](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13794)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6048](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6048)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:216](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L216)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1834](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1834)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15534](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15534)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:216 | HPR-P1-025 | COMPLETED — implementation SHA `c692ab684ef79c8295d3216adafd6daf5dc1db46` passed exact-head full CI #552 / run id `37429745480`. Controlled rollout now verifies the surviving REST node before target drain, waits for target HAProxy sessions to reach zero before installation, verifies local readiness plus authenticated database-backed acceptance before rejoin, waits for HAProxy to report the rejoined node UP, and applies equivalent acceptance/rejoin gates to schema-compatible rollback. GitHub `production` Environment two-reviewer enforcement remains an external repository setting and is NOT ESTABLISHED by the available connector; production-equivalent deployment/rollback evidence remains HPR-P1-029 scope. | Release / Deployment | Infra/Test/Doc | Harden controlled rollout so the surviving application node is verified before draining the target, drain completion/active-request safety is explicitly checked before installation, rejoin is verified before proceeding, rollback follows the same availability gates, and repository evidence records the required GitHub production Environment reviewer protection without fabricating settings that cannot be observed. | fix(release): enforce availability preserving rollout | HPR-P1-018 + HPR-P1-023
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15534 | HPR-P1-025 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Release / Deployment; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6048 | HPR-P1-025 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13794 | HPR-P1-025 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Release / Deployment; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-478"></a>
## HAPI-478 — complete operational health inspections

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-027

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-471

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Complete database health inspection coverage with PostgreSQL index validity/readiness and a reviewed bloat/space-risk method that does not assume unapproved extensions; add host/filesystem capacity evidence outside SQL; retain guarded maintenance semantics and validate diagnostic/maintenance procedures with post-maintenance acceptance in production-equivalent infrastructure.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `ops/production/postgres/DATABASE_OPERATIONS.md`
- `ops/production/postgres/scripts/database-health-report.sh`
- `ops/production/postgres/scripts/inspect-host-capacity.sh`
- `ops/production/postgres/scripts/validate-database-operations.sh`
- `ops/production/postgres/scripts/verify-database-maintenance.sh`
- `ops/production/postgres/sql/inspect-bloat-space-risk.sql`
- `ops/production/postgres/sql/inspect-index-health.sql`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `ops/production/postgres/DATABASE_OPERATIONS.md`
- `ops/production/postgres/scripts/database-health-report.sh`
- `ops/production/postgres/scripts/inspect-host-capacity.sh`
- `ops/production/postgres/scripts/validate-database-operations.sh`
- `ops/production/postgres/scripts/verify-database-maintenance.sh`
- `ops/production/postgres/sql/inspect-bloat-space-risk.sql`
- `ops/production/postgres/sql/inspect-index-health.sql`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-027 database operations completion evidence

- implementation SHA: `cafdc42270085ddf513a23a514f437ef39f7b842`;
- exact-head full CI #555 / run id `37433450282`: **SUCCESS**;
- index inspection now records `indisvalid`, `indisready` and `indislive` from PostgreSQL catalogs;
- bloat/space-risk triage remains extension-free and is explicitly not misrepresented as an exact physical-bloat percentage;
- host/filesystem bytes and inode capacity are captured separately from PostgreSQL object-size SQL;
- guarded maintenance validation requires a selected approved action plus authenticated database-backed HidraAPI acceptance before and after maintenance;
- actual production-equivalent diagnostic/maintenance execution remains HPR-P1-029 evidence scope.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-471. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `cafdc42270085ddf513a23a514f437ef39f7b842` — exact registered-message commit; surviving paths matched to current tree.
- `814518272d39eb2b4eb2678d4e7e9e85693fea41` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13796](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13796)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6050](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6050)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:218](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L218)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1813](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1813)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15536](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15536)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:218 | HPR-P1-027 | COMPLETED — implementation SHA `cafdc42270085ddf513a23a514f437ef39f7b842` passed exact-head full CI #555 / run id `37433450282`. Database operations now include PostgreSQL index validity/readiness/liveness inspection, extension-free dead-tuple/object-size/index-ratio space-risk triage without assuming unapproved extensions, host/filesystem byte+inode capacity capture outside SQL, and a guarded maintenance-validation harness requiring authenticated database-backed HidraAPI acceptance before and after the selected action. Actual production-equivalent diagnostic/maintenance execution remains HPR-P1-029 evidence scope. | Database Operations | Infra/Test/Doc | Complete database health inspection coverage with PostgreSQL index validity/readiness and a reviewed bloat/space-risk method that does not assume unapproved extensions; add host/filesystem capacity evidence outside SQL; retain guarded maintenance semantics and validate diagnostic/maintenance procedures with post-maintenance acceptance in production-equivalent infrastructure. | fix(database): complete operational health inspections | HPR-P1-020
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15536 | HPR-P1-027 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Database Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6050 | HPR-P1-027 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13796 | HPR-P1-027 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Database Operations; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-479"></a>
## HAPI-479 — enforce p1 retention controls

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-028

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-472, HAPI-477

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Enforce the approved P1 retention controls in executable configuration: distinguish bootstrap from mature monthly coverage and verify distinct monthly repo2 recovery points up to the required 12-month horizon; bind Prometheus 30-day and Loki 90-day retention/deletion settings; verify repo2 protected-copy independence and retained recovery-point restorability in the later production-equivalent exercise.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `ops/production/observability/README.md`
- `ops/production/observability/loki/loki.yml`
- `ops/production/observability/prometheus/prometheus.service.tpl`
- `ops/production/observability/validate-observability-artifacts.sh`
- `ops/production/postgres/pgbackrest/README.md`
- `ops/production/postgres/pgbackrest/RETENTION_POLICY.md`
- `ops/production/postgres/scripts/check-monthly-retention.sh`
- `ops/production/postgres/scripts/validate-backup-pitr-artifacts.sh`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `ops/production/observability/README.md`
- `ops/production/observability/loki/loki.yml`
- `ops/production/observability/prometheus/prometheus.service.tpl`
- `ops/production/observability/validate-observability-artifacts.sh`
- `ops/production/postgres/pgbackrest/README.md`
- `ops/production/postgres/pgbackrest/RETENTION_POLICY.md`
- `ops/production/postgres/scripts/check-monthly-retention.sh`
- `ops/production/postgres/scripts/validate-backup-pitr-artifacts.sh`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-028 retention enforcement evidence

- implementation SHA: `e12a2205f397e68edf1bda96211a99966577d501`;
- exact-head full CI #559 / run id `37435329469`: **SUCCESS**;
- repo2 validation distinguishes bootstrap from mature monthly coverage and requires distinct required UTC calendar months under `HIDRA-P1-BACKUP-RETENTION-001`;
- mature coverage is bounded to the latest 12 required monthly recovery points; missing or duplicate required months fail validation;
- Prometheus executable service configuration enforces `--storage.tsdb.retention.time=30d`;
- Loki enforces `retention_period: 2160h` with compactor retention/deletion and a filesystem delete-request store;
- physical independence of repo2 and successful restore from a retained repo2 point remain measured HPR-P1-029 evidence requirements.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-472, HAPI-477. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `e12a2205f397e68edf1bda96211a99966577d501` — exact registered-message commit; surviving paths matched to current tree.
- `996ef054cf3b77eba90cdc61c9520656e3722abd` — exact registered-message commit; surviving paths matched to current tree.
- `7bc32ed9fe13517406441edae44f8b87309631b7` — exact registered-message commit; surviving paths matched to current tree.
- `ede4be2636c8bbab8e1ecac46c72930c16144f97` — exact registered-message commit; surviving paths matched to current tree.
- `0e0807e61fd558208c7c8fffefc101e100207e76` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13797](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13797)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6051](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6051)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:219](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L219)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1803](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1803)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15537](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15537)
- [ops/production/postgres/pgbackrest/RETENTION_POLICY.md:1](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/ops/production/postgres/pgbackrest/RETENTION_POLICY.md#L1)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:219 | HPR-P1-028 | COMPLETED — implementation SHA `e12a2205f397e68edf1bda96211a99966577d501` passed exact-head full CI #559 / run id `37435329469`. Repo2 validation now distinguishes bootstrap from mature policy coverage, requires one distinct completed full recovery point for each required UTC calendar month from the approved 2026-10 policy start, rejects duplicate/missing required months, and retains the 12-point mature horizon; Prometheus now runs with executable `30d` TSDB retention and Loki with `90d` retention plus compactor deletion. Protected-copy independence and retained repo2 restorability remain HPR-P1-029 measured evidence scope. | Retention / Archival | Infra/Test/Doc | Enforce the approved P1 retention controls in executable configuration: distinguish bootstrap from mature monthly coverage and verify distinct monthly repo2 recovery points up to the required 12-month horizon; bind Prometheus 30-day and Loki 90-day retention/deletion settings; verify repo2 protected-copy independence and retained recovery-point restorability in the later production-equivalent exercise. | fix(operations): enforce p1 retention controls | HPR-P1-021 + HPR-P1-026
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15537 | HPR-P1-028 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Retention / Archival; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6051 | HPR-P1-028 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13797 | HPR-P1-028 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Retention / Archival; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-480"></a>
## HAPI-480 — execute p1 survivability exercises

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P1-029

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-472

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Execute and retain one governed production-equivalent P1 evidence campaign after HPR-P1-021..028: scheduled backup/WAL operation; intentional current-schema PITR with pre-target marker present/post-target marker absent, exact Flyway validation, achieved RPO and declaration-to-service-acceptance RTO; two-node application loss/rejoin with continuous representative REST requests; PostgreSQL failover with single-writer/fencing/stable-endpoint and HidraAPI/Hikari recovery; exact-artifact deployment plus approved rollback; representative alert firing/domain delivery/resolve; database diagnostic/maintenance acceptance; and retention/independent-repo restore evidence. Record immutable environment/tool versions and evidence identifiers.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P1-029 completion evidence — 2026-10-06

- canonical retained evidence: `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`;
- deployed authoritative repository SHA: `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a` on `main`;
- repository reconciliation confirms 82 Flyway migrations with tail `V20261004_049__hmr_049_risk_risk_register.sql` then `V20261005_001__provision_risk_register_created_audit_taxonomy.sql`;
- operator-supplied measured evidence records RPO **15 seconds** and RTO **37 minutes**, both within approved P1 objectives;
- both-node application HA, PostgreSQL single-writer/fencing/Hikari recovery, exact-artifact deployment/rollback, alert delivery/acknowledgement/resolve, database maintenance acceptance and independent repo2 restore/bootstrap retention are retained in the canonical evidence document;
- HPR-P1-029 is **COMPLETED**; HPR-P1-012 remains responsible for final P1 closure and production-readiness disposition.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-472. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `133020813bb3222036713bdcc3e39d9026602ffe` — exact registered-message commit; surviving paths matched to current tree.
- `d28331030f48bead85f96589bcf92587aed56668` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13798](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13798)
- [doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md:181](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md#L181)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6052](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6052)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:220](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L220)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1780](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1780)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:1789](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L1789)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15538](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15538)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:220 | HPR-P1-029 | COMPLETED — production-equivalent evidence campaign executed 2026-10-06 against deployed HidraAPI SHA `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a`. Repository reconciliation confirms the authoritative 82-migration tail. Retained operator evidence demonstrates both-node REST continuity/rejoin, Patroni single-writer failover with watchdog fencing and Hikari recovery, intentional PITR with marker assertions, RPO 15s and RTO 37m within approved objectives, exact-artifact deployment/rollback with two approvals, alert delivery/acknowledgement/resolve including security routing, guarded database maintenance with authenticated acceptance, and independent repo2 restore plus October 2026 bootstrap retention. Canonical evidence: `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`. | Survivability Exercise | Infra/Test/Doc | Execute and retain one governed production-equivalent P1 evidence campaign after HPR-P1-021..028: scheduled backup/WAL operation; intentional current-schema PITR with pre-target marker present/post-target marker absent, exact Flyway validation, achieved RPO and declaration-to-service-acceptance RTO; two-node application loss/rejoin with continuous representative REST requests; PostgreSQL failover with single-writer/fencing/stable-endpoint and HidraAPI/Hikari recovery; exact-artifact deployment plus approved rollback; representative alert firing/domain delivery/resolve; database diagnostic/maintenance acceptance; and retention/independent-repo restore evidence. Record immutable environment/tool versions and evidence identifiers. | test(operations): execute p1 survivability exercises | HPR-P1-021..028
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15538 | HPR-P1-029 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Survivability Exercise; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6052 | HPR-P1-029 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13798 | HPR-P1-029 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Survivability Exercise; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-543"></a>
## HAPI-543 — audit engineering foundation acceptance

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P25-031

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: BLOCKED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-621

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

audit engineering foundation acceptance

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete integrated synthetic PLATFORM evidence and independent closure before any separately authorized SITE commissioning/release/deployment. The exact bounded remaining delivery is: audit engineering foundation acceptance.

### 4. Functional Requirements

Detailed registration above; actual evidence required

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-031 — Independent engineering foundation closure audit

Owner: roadmap/independent verification; each source owner accountable for its evidence.
Objective: independently disposition F1/F2/F3/F4 with exact supported coverage, separate
from deferred 017 full simulation-cycle closure. Dependencies: accepted 030 verification
design, implemented/verified F1/F2/F3, actual cases A–G, live applicable exact-head CI,
explicit owner/operator acceptance of the claimed foundation scope. No simulator gate.
Deliverables: source/environment/CI/raw-test evidence reconciliation, all acceptance
results and unsupported gaps, owner/authorization/history/reference audit and explicit
foundation PASS/CLOSED or BLOCKED/PENDING disposition. API/schema: read-only audit of
actual contracts and real PostgreSQL round-trip/upgrade evidence; no source changes.
Compatibility: preserve all historical source formats, rows, hashes and simulator records.
Negative acceptance: fail/scope-limit missing operator path, invented default engineering
fact, corrupt/dangling history, unauthorized reference or missing test/CI evidence.
Exact allowlist: this roadmap and append-only reconciliation; exact message:
`docs(roadmap): audit engineering foundation acceptance`.
Initially PLANNED — NOT SELECTED; no implementation, automatic task continuation,
full P2.5 closure, release or industrial solver/OT readiness claim.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Detailed registration above; actual evidence required
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-621. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13549](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13549)
- [doc/model-remediation/RECONCILIATION.md:13683](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13683)
- [doc/model-remediation/RECONCILIATION.md:23471](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23471)
- [doc/model-remediation/RECONCILIATION.md:23767](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23767)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1075](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1075)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1409](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1409)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5272](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5272)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6122](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6122)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15289](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15289)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15423](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15423)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: PLANNED / REGISTERED.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15423 | HPR-P25-031 | NEW — PLANNED | FOUNDATION_ACTIVE | Governance / Operations | 030; implemented verified F1/F2/F3; actual cases; applicable CI; operator acceptance | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Independent foundation closure audit after owner selection
doc/roadmap/ULTIMATE_ROADMAP.md:1075 | HPR-P25-031 | DEPENDENCY_PENDING | Independent engineering foundation closure
doc/roadmap/ULTIMATE_ROADMAP.md:6122 | HPR-P25-031 | PLANNED / REGISTERED | Existing independent closure ID and two-doc scope; after 058/owner scope acceptance
doc/model-remediation/RECONCILIATION.md:13683 | HPR-P25-031 | NEW — PLANNED | FOUNDATION_ACTIVE | Governance / Operations | 030; implemented verified F1/F2/F3; actual cases; applicable CI; operator acceptance | Independent foundation objective; original mixed parents preserved | All numerical implementation excluded | Detailed registration above; actual evidence required | PLANNED — NOT SELECTED | Independent foundation closure audit after owner selection
doc/model-remediation/RECONCILIATION.md:23767 | HPR-P25-031 | DEPENDENCY_PENDING | Independent engineering foundation closure
~~~~~~~~

<a id="hapi-621"></a>
## HAPI-621 — Integrated closure: four integrated PLATFORM qualification packages

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P25-058

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: BLOCKED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-625

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-058; child tasks own implementation. four integrated PLATFORM qualification packages

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete integrated synthetic PLATFORM evidence and independent closure before any separately authorized SITE commissioning/release/deployment. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-058; child tasks own implementation. four integrated PLATFORM qualification packages.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve controlled deployment, HA/backup/recovery procedures and separate PLATFORM from SITE acceptance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-058 — four integrated PLATFORM qualification packages

Prerequisites:accepted052–057, CI-003 closure, retained030 design and D5. The canonical
PLATFORM_ACCEPTANCE criteria remain PLANNED until actual exact-case evidence accepts
them. Current A–I and historical foundation A–G are different namespaces, not aliases.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| HPR-P25-058A | Freeze versioned supported fixture/case/contract/namespace matrix and exact test scope. | Map every historical/current criterion to concrete accepted owner APIs, permissions, input/expected result and test identity; unsupported entries remain open/excluded explicitly. No inferred acceptance. |
| HPR-P25-058B | Actual operator/API/security/history end-to-end software qualification. | Two companies/networks, multiple supported products, three stations/nine units, independent survey/section/manufacturer revisions; real filter/API/storage path, not SQL seeding as the sole operator proof. |
| HPR-P25-058C | Integrated concurrency/replay/reference/rollback and observation/action qualification. | Empty/incomplete/UNKNOWN inputs, trusted/untrusted data, current/revoked/historical access, stale CAS, partial failures and owner races preserve atomicity and supported evidence. |
| HPR-P25-058D | Exact-SHA full database/contract/dictionary/24-module evidence and PLATFORM decision. | Java21/Postgres16/retained18/Flyway/JPA, raw zero-skip tests and exact runtime API/security/base compatibility; source/fixture/report/artifact mapping, limitations and all criteria reconciled. No SITE or numerical accuracy certification. |

D5 may admit PLATFORM_ACCEPTANCE updates with exact test paths; this planning task does
not edit that criteria file or mark any row passed. Production corrections require exact
same-ID defect scope; no new unrelated business features under an acceptance-test label.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve controlled deployment, HA/backup/recovery procedures and separate PLATFORM from SITE acceptance.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-625. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23454](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23454)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1392](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1392)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5243](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5243)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-622"></a>
## HAPI-622 — Versioned PLATFORM fixture and criterion matrix

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P25-058A

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: BLOCKED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-604, HAPI-610, HAPI-616

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Freeze versioned supported fixture/case/contract/namespace matrix and exact test scope.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete integrated synthetic PLATFORM evidence and independent closure before any separately authorized SITE commissioning/release/deployment. The exact bounded remaining delivery is: Freeze versioned supported fixture/case/contract/namespace matrix and exact test scope..

### 4. Functional Requirements

Map every historical/current criterion to concrete accepted owner APIs, permissions, input/expected result and test identity; unsupported entries remain open/excluded explicitly. No inferred acceptance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/platform/qualification/PlatformCriterionMatrixTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Map every historical/current criterion to concrete accepted owner APIs, permissions, input/expected result and test identity; unsupported entries remain open/excluded explicitly. No inferred acceptance.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-604, HAPI-610, HAPI-616. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23462](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23462)
- [doc/model-remediation/RECONCILIATION.md:23763](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23763)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1071](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1071)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1400](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1400)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1071 | HPR-P25-058A | BLOCKED_SPECIFICATION | Versioned PLATFORM fixture and criterion matrix
doc/roadmap/ULTIMATE_ROADMAP.md:1400 | HPR-P25-058A | Freeze versioned supported fixture/case/contract/namespace matrix and exact test scope. | Map every historical/current criterion to concrete accepted owner APIs, permissions, input/expected result and test identity; unsupported entries remain open/excluded explicitly. No inferred acceptance.
doc/model-remediation/RECONCILIATION.md:23462 | HPR-P25-058A | Freeze versioned supported fixture/case/contract/namespace matrix and exact test scope. | Map every historical/current criterion to concrete accepted owner APIs, permissions, input/expected result and test identity; unsupported entries remain open/excluded explicitly. No inferred acceptance.
doc/model-remediation/RECONCILIATION.md:23763 | HPR-P25-058A | BLOCKED_SPECIFICATION | Versioned PLATFORM fixture and criterion matrix
~~~~~~~~

<a id="hapi-623"></a>
## HAPI-623 — Operator/API/security/history end-to-end qualification

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P25-058B

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: BLOCKED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-622

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Actual operator/API/security/history end-to-end software qualification.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete integrated synthetic PLATFORM evidence and independent closure before any separately authorized SITE commissioning/release/deployment. The exact bounded remaining delivery is: Actual operator/API/security/history end-to-end software qualification..

### 4. Functional Requirements

Two companies/networks, multiple supported products, three stations/nine units, independent survey/section/manufacturer revisions; real filter/API/storage path, not SQL seeding as the sole operator proof.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/platform/qualification/PlatformOperatorApiQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Two companies/networks, multiple supported products, three stations/nine units, independent survey/section/manufacturer revisions; real filter/API/storage path, not SQL seeding as the sole operator proof.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-622. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23463](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23463)
- [doc/model-remediation/RECONCILIATION.md:23764](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23764)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1072](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1072)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1401](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1401)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1072 | HPR-P25-058B | BLOCKED_SPECIFICATION | Operator/API/security/history end-to-end qualification
doc/roadmap/ULTIMATE_ROADMAP.md:1401 | HPR-P25-058B | Actual operator/API/security/history end-to-end software qualification. | Two companies/networks, multiple supported products, three stations/nine units, independent survey/section/manufacturer revisions; real filter/API/storage path, not SQL seeding as the sole operator proof.
doc/model-remediation/RECONCILIATION.md:23463 | HPR-P25-058B | Actual operator/API/security/history end-to-end software qualification. | Two companies/networks, multiple supported products, three stations/nine units, independent survey/section/manufacturer revisions; real filter/API/storage path, not SQL seeding as the sole operator proof.
doc/model-remediation/RECONCILIATION.md:23764 | HPR-P25-058B | BLOCKED_SPECIFICATION | Operator/API/security/history end-to-end qualification
~~~~~~~~

<a id="hapi-624"></a>
## HAPI-624 — Integrated concurrency, replay and operational evidence

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P25-058C

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: BLOCKED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-623

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Integrated concurrency/replay/reference/rollback and observation/action qualification.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete integrated synthetic PLATFORM evidence and independent closure before any separately authorized SITE commissioning/release/deployment. The exact bounded remaining delivery is: Integrated concurrency/replay/reference/rollback and observation/action qualification..

### 4. Functional Requirements

Empty/incomplete/UNKNOWN inputs, trusted/untrusted data, current/revoked/historical access, stale CAS, partial failures and owner races preserve atomicity and supported evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/platform/qualification/PlatformConcurrencyReplayQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Empty/incomplete/UNKNOWN inputs, trusted/untrusted data, current/revoked/historical access, stale CAS, partial failures and owner races preserve atomicity and supported evidence.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-623. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23464](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23464)
- [doc/model-remediation/RECONCILIATION.md:23765](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23765)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1073](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1073)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1402](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1402)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1073 | HPR-P25-058C | BLOCKED_SPECIFICATION | Integrated concurrency, replay and operational evidence
doc/roadmap/ULTIMATE_ROADMAP.md:1402 | HPR-P25-058C | Integrated concurrency/replay/reference/rollback and observation/action qualification. | Empty/incomplete/UNKNOWN inputs, trusted/untrusted data, current/revoked/historical access, stale CAS, partial failures and owner races preserve atomicity and supported evidence.
doc/model-remediation/RECONCILIATION.md:23464 | HPR-P25-058C | Integrated concurrency/replay/reference/rollback and observation/action qualification. | Empty/incomplete/UNKNOWN inputs, trusted/untrusted data, current/revoked/historical access, stale CAS, partial failures and owner races preserve atomicity and supported evidence.
doc/model-remediation/RECONCILIATION.md:23765 | HPR-P25-058C | BLOCKED_SPECIFICATION | Integrated concurrency, replay and operational evidence
~~~~~~~~

<a id="hapi-625"></a>
## HAPI-625 — Exact-source PLATFORM evidence and disposition

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-P25-058D

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: BLOCKED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-624

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Exact-SHA full database/contract/dictionary/24-module evidence and PLATFORM decision.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Complete integrated synthetic PLATFORM evidence and independent closure before any separately authorized SITE commissioning/release/deployment. The exact bounded remaining delivery is: Exact-SHA full database/contract/dictionary/24-module evidence and PLATFORM decision..

### 4. Functional Requirements

Java21/Postgres16/retained18/Flyway/JPA, raw zero-skip tests and exact runtime API/security/base compatibility; source/fixture/report/artifact mapping, limitations and all criteria reconciled. No SITE or numerical accuracy certification.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/platform/qualification/PlatformExactSourceAcceptanceTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Java21/Postgres16/retained18/Flyway/JPA, raw zero-skip tests and exact runtime API/security/base compatibility; source/fixture/report/artifact mapping, limitations and all criteria reconciled. No SITE or numerical accuracy certification.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-624. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23465](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23465)
- [doc/model-remediation/RECONCILIATION.md:23766](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23766)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1074](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1074)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1403](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1403)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1074 | HPR-P25-058D | BLOCKED_SPECIFICATION | Exact-source PLATFORM evidence and disposition
doc/roadmap/ULTIMATE_ROADMAP.md:1403 | HPR-P25-058D | Exact-SHA full database/contract/dictionary/24-module evidence and PLATFORM decision. | Java21/Postgres16/retained18/Flyway/JPA, raw zero-skip tests and exact runtime API/security/base compatibility; source/fixture/report/artifact mapping, limitations and all criteria reconciled. No SITE or numerical accuracy certification.
doc/model-remediation/RECONCILIATION.md:23465 | HPR-P25-058D | Exact-SHA full database/contract/dictionary/24-module evidence and PLATFORM decision. | Java21/Postgres16/retained18/Flyway/JPA, raw zero-skip tests and exact runtime API/security/base compatibility; source/fixture/report/artifact mapping, limitations and all criteria reconciled. No SITE or numerical accuracy certification.
doc/model-remediation/RECONCILIATION.md:23766 | HPR-P25-058D | BLOCKED_SPECIFICATION | Exact-source PLATFORM evidence and disposition
~~~~~~~~

<a id="hapi-626"></a>
## HAPI-626 — prepare 0.6.0 platform milestone

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-REL-001

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Align the project version to 0.6.0 and record the completed platform milestone; publish only the five-path release-alignment scope below; require successful exact-SHA documentation and full production CI before release readiness.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `AGENTS.md`
- `PROJECT_STATE.md`
- `VERSIONS.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `pom.xml`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `AGENTS.md`
- `PROJECT_STATE.md`
- `VERSIONS.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `pom.xml`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-REL-001 bounded release-alignment preparation — 2026-10-09

The owner's "Do next" selects registration and preparation following the read-only
release preflight; this authorization is distinct from P2 closure. AGENTS.md,
Ultimate Roadmap and reconciliation were re-read at unchanged GitHub main
ac6cbb4b1cd584b0c780dd208543f41ebe326e06, tree
4ed7be83ee5a25dd1ecc63feea053e48f8ccbc0f, before changes. The isolated local
baseline was reconstructed and its complete tree matched that GitHub tree.

The preflight inspected pom.xml, VERSIONS.md, PROJECT_STATE.md, completed phase
records, current main/CI, all returned Git tag refs and GitHub Releases, ci.yml,
release.yml, OpenAPI configuration and maintained validators. The only published
tag/release is v0.5.0 at 492d9916369a58e60c1a437647411e7c5a523990. The existing
0.6.0-SNAPSHOT development line and completed architecture/stabilization milestone
justify preparing 0.6.0. This is release preparation, not a completed formal release.

Registered exact message: `chore(release): prepare 0.6.0 platform milestone`.
Write ONLY pom.xml, VERSIONS.md, PROJECT_STATE.md,
doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
The POM project version becomes 0.6.0; dependencies and release-plugin settings
remain unchanged. Version history and project state distinguish the candidate,
last formal release, semantic anchor and pending exact-SHA verification.
HPR-REL-001 remains IN PROGRESS pending successful candidate CI.

The semantic milestone anchor a8905e32289a583f47b831e0381783e556ae0c8d has successful
Documentation #136 and production #609. Current parent documentation #137 passed.
Those results are inherited evidence, not verification of the candidate. Publish
one preparation commit from the verified parent with an expected-head lease;
verify immutable parent/tree/changed paths and observe both documentation and full
HidraAPI CI startup. The POM change activates the existing full-CI push trigger.
STOP after startup; wait for owner Next/failure notification before verification.
Required candidate gates include Java 21 clean verify, canonical documentation,
fresh OpenAPI identity/equality and backward compatibility, current migrated
physical dictionary comparison and the existing P1 evidence/operations gates.

P0/P1/P2 disposition, 57 completed HMR identities including HMR-080, 123 permanent
subjects, P3 DEFERRED rows, legacy records and physical evidence applicability are
preserved. OpenAPI stays v1. No source/test/schema/migration/workflow/configuration,
contract/dictionary/owner-policy, data import, physical campaign or deployment
change is selected. No tag or GitHub Release is created; these require separate
explicit authorization under AGENTS.md §19. A future v0.6.0 tag must target the
successfully verified alignment SHA, never the snapshot-version milestone anchor.
Do not advance to 0.7.0-SNAPSHOT until formal release exists or separately directed.


Local preparation validation PASSED: 37 maintained Python tests (10 documentation,
10 OpenAPI, 17 dictionary); canonical validator (95 documents, 5,062 links,
24 modules, 13 P2 rows); offline OpenAPI integrity (244 paths, 263 operations,
231 schemas); P1 closure evidence validator; exact five-file allowlist; unchanged
P0/P1/P2/P3 primary registry rows and 57 completed HMR identities; unchanged
permanent semantic catalogue; only the POM project version changed; all other
tracked blobs unchanged; git diff --check. These are local/static checks.

Actual `bash ./mvnw -B -q clean verify` FAILED before compilation resolving the
uncached Spring Boot parent 4.1.1 because repo.maven.apache.org DNS is unavailable.
Installed Java is 17.0.20; Java 21 is required. No local Java/runtime verification
success is claimed. Full GitHub CI on the future alignment SHA is mandatory;
release readiness and HPR-REL-001 completion remain PENDING.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `09bf1cbf82c20f19c50ebb1ff9e27047c7f34856` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:7025](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7025)
- [doc/model-remediation/RECONCILIATION.md:7085](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7085)
- [doc/model-remediation/RECONCILIATION.md:13820](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13820)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6149](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6149)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:952](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L952)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:8998](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L8998)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:9058](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L9058)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15560](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15560)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:952 | HPR-REL-001 | COMPLETED — release alignment 09bf1cbf82c20f19c50ebb1ff9e27047c7f34856 verified by Documentation #138 and full HidraAPI CI #610; v0.6.0 tag and GitHub Release published by owner on 2026-10-09 | Release Management | Build/Doc | Align the project version to 0.6.0 and record the completed platform milestone; publish only the five-path release-alignment scope below; require successful exact-SHA documentation and full production CI before release readiness. | chore(release): prepare 0.6.0 platform milestone | P0/P1/P2 closed; verified release preflight
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15560 | HPR-REL-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Release Management; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6149 | HPR-REL-001 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13820 | HPR-REL-001 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Release Management; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-627"></a>
## HAPI-627 — start 0.7.0 development

Phase: HPS-025 — Deployment and Operational Readiness

Legacy IDs: HPR-REL-002

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-626

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Record real v0.6.0 publication and immutable target; advance only the Maven project version to 0.7.0-SNAPSHOT; refresh release/development traceability within the five-path scope below; verify both exact-head CI workflows.

### 2. Existing Implementation

Repository P1 runbooks/scripts and historical production-equivalent exercise records exist; no current production deployment was independently established. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `AGENTS.md`
- `PROJECT_STATE.md`
- `VERSIONS.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `pom.xml`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. Owner supplies deployment measurements/calibration. This baseline performs no deployment, tag, release or OT action. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `AGENTS.md`
- `PROJECT_STATE.md`
- `VERSIONS.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `pom.xml`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-REL-002 published release record and development transition — 2026-10-09

The owner's Next selects recording the published v0.6.0 release and advancing
main to 0.7.0-SNAPSHOT under AGENTS.md §19. Before changes, GitHub main was checked
unchanged at 045f062f782b0f5240f255853db2e9025e69d631, tree
292b31f10bc0f4b6cb9b664cf79cb5210ca5e185. The local complete tree matched that
remote tree. AGENTS.md, Ultimate Roadmap and reconciliation were re-read, together
with POM, version history and project state. Parent Documentation #139 PASSED.

The actual latest GitHub Release is HidraAPI v0.6.0 / tag v0.6.0, ID 407972853,
published by owner 2026-10-09T14:02:18Z, non-draft and non-prerelease. The tag ref
is lightweight/unsigned and resolves directly to
09bf1cbf82c20f19c50ebb1ff9e27047c7f34856. Full production CI #610 (37936829442)
PASSED on that exact release alignment SHA. The release and verified tag remain
immutable targets of this task; no tag rewrite, replacement, asset modification,
new publication or production deployment is performed.

Registered exact message: `chore(release): start 0.7.0 development`.
Write ONLY pom.xml, VERSIONS.md, PROJECT_STATE.md,
doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Only the POM project version changes from 0.6.0 to 0.7.0-SNAPSHOT. Version history
records the actual formal release SHA/date/tag form and separates the current
development transition from the release and semantic milestone anchors. Project
state and canonical execution memories register this selected transition and
pending exact-head verification. HPR-REL-001 remains completed; HPR-REL-002 is
IN PROGRESS until both transition workflows pass.

Validate canonical documentation, offline OpenAPI and P1 evidence; exact five-file
scope and POM-only version difference; preserved P0/P1/P2/P3 registry rows,
57 completed HMR identities, 123 semantic subjects and historical provenance;
all other tracked blobs unchanged; git diff --check. Prior release CI #610 and
parent documentation #139 are inherited evidence, not verification of the new
snapshot. Publish once with expected-head lease, verify parent/tree/blobs and
observe Documentation Validation and full HidraAPI CI startup through existing
triggers, then STOP for Next/failure notification without waiting for completion.
The next selected stage verifies those exact-transition gates including Java 21
clean verify, OpenAPI equality/compatibility, migrated dictionary comparison and
retained P1 evidence/operations gates. No later task executes automatically.

P0/P1/P2 remain CLOSED; all P3 tasks stay DEFERRED. No source/test/configuration,
migration/schema, contract/dictionary/owner-policy or operations change occurs.
No private-data import, policy approval, physical campaign, deployed AI inference
or production promotion is authorized. Dated candidate/unpublished/version
statements retain historical scope and are superseded by this actual release and
current development disposition. Deployment remains a separate governed workflow.


Local transition validation PASSED: canonical documentation (95 documents,
5,062 links, 24 modules, 13 P2 rows), offline OpenAPI integrity (244 paths,
263 operations, 231 schemas), P1 closure evidence, exact five-file scope,
POM-only project version difference, preserved phase registries/57 HMR identities/
permanent semantic catalogue, identical transition records and git diff --check.
Every other tracked blob is unchanged; no new test campaign is claimed.

Actual `bash ./mvnw -B -q clean verify` FAILED before compilation resolving the
uncached Spring Boot parent 4.1.1 because repo.maven.apache.org DNS is unavailable.
Installed Java is 17.0.20; Java 21 is required. The new transition requires its
own full GitHub CI and documentation results; no future success is pre-asserted.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-626. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `ce8867c252fb537d9cca652f9b397a6d35bbc96a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:7139](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7139)
- [doc/model-remediation/RECONCILIATION.md:7199](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7199)
- [doc/model-remediation/RECONCILIATION.md:13821](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13821)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6150](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6150)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:953](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L953)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:9112](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L9112)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:9172](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L9172)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15561](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15561)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:953 | HPR-REL-002 | COMPLETED — 0.7.0-SNAPSHOT transition ce8867c252fb537d9cca652f9b397a6d35bbc96a verified by Documentation #140 and full HidraAPI CI #611; published v0.6.0 target preserved | Release Management | Build/Doc | Record real v0.6.0 publication and immutable target; advance only the Maven project version to 0.7.0-SNAPSHOT; refresh release/development traceability within the five-path scope below; verify both exact-head CI workflows. | chore(release): start 0.7.0 development | HPR-REL-001; v0.6.0 tag/release exist
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15561 | HPR-REL-002 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Release Management; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6150 | HPR-REL-002 | COMPLETED / ACCEPTED (recorded bounded scope) | P0/P1/P2/CI/release retained; original exact message/evidence, no reopening
doc/model-remediation/RECONCILIATION.md:13821 | HPR-REL-002 | COMPLETED within recorded scope | COMPLETED_PRESERVED | Release Management; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<!-- task-register:payload:end -->
