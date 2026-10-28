# HPS-006 — Stations and Equipment: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-006 — Stations and Equipment

<a id="hapi-248"></a>
## HAPI-248 — remediate semantic review Equipment

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HMR-054

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review Equipment

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeVersionJpaRepository.java`
- `src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql`
- `src/test/java/dz/sh/hidra/DomainInvariantGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`.
- Application services: `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Topology.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/topology.md`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeVersionJpaRepository.java`
- `src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql`
- `src/test/java/dz/sh/hidra/DomainInvariantGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-054 — topology.Equipment

- Source review: `HMSR-063`
- Exact commit: `fix(topology): remediate semantic review Equipment`
- Status: **Completed — implemented; exact-head CI pending**
- SCC: —
- Recorded upstream HMS dependencies: party.Party, topology.Facility
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql`
- Owner-contract prerequisite: Resolved by existing Party-owned TopologyPartyReferenceContract and query service; export already admitted by both architecture checks.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentKind.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeDefinitionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeValueJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentIntegrityMigrationTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java` — update constructor fixture after taxonomy removal.
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=EquipmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Remove the duplicated EquipmentKind business-taxonomy source of truth.** Reconcile `Equipment.equipmentKind` and `EquipmentType.equipmentKind` with the roadmap's catalog-backed EquipmentType architecture rather than preserving fixed enum classification in parallel.
  2. **Protect populated same-module attachment references.** `facilityId`, `nodeId` and `pipelineSegmentId` should fail closed against their Topology-owned targets using the appropriate same-module FK and/or application validation strategy.
  3. **Validate populated `manufacturerPartyId` through a Party-owned application/lookup boundary.** Preserve manufacturer snapshots and do not introduce a cross-module database FK.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/DomainInvariantGuardrailTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentIntegrityMigrationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `674914cccf97aa4903df2f6f0e69562e9ce074da` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:57](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L57)
- [doc/model-remediation/RECONCILIATION.md:89](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L89)
- [doc/model-remediation/RECONCILIATION.md:308](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L308)
- [doc/model-remediation/RECONCILIATION.md:342](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L342)
- [doc/model-remediation/RECONCILIATION.md:13701](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13701)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2422](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2422)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15441](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15441)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:88](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L88)
- [docs/data definition/Topology.md:288](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Topology.md#L288)
- [docs/roadmap/model-semantic-remediation.md:210](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L210)
- [docs/roadmap/model-semantic-remediation.md:3079](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3079)
- [docs/roadmap/topology.md:590](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/topology.md#L590)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:210 | HMR-054 | HMSR-063 | topology | Equipment | — | party.Party, topology.Facility | fix(topology): remediate semantic review Equipment | Completed — HPR-P2-008; catalog-only classification, attachment FKs and owner manufacturer validation; CI pending
doc/model-remediation/RECONCILIATION.md:89 | HMR-054 | HMSR-063 | topology.Equipment | COMPLETED — HPR-P2-008 | EquipmentType identity/code is sole active classification; EquipmentKind deleted from domain/JPA; forward V20261006_009 preserves legacy strings, rejects conflicting classification/orphan attachments and adds nullable same-module FKs; manufacturer checked by existing Party contract; snapshots preserved; focused tests added; CI #604 passed.
doc/model-remediation/RECONCILIATION.md:13701 | HMR-054 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:88 | HMR-054 | HMSR-063 | [topology.Equipment](../domain/SEMANTIC_DECISIONS.md#topology-equipment) | [EquipmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java) | [V20261006_009__hmr_054_equipment_catalog_and_attachments](../../src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql); [TopologyPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15441 | HMR-054 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-373"></a>
## HAPI-373 — review topology Equipment

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HMSR-063

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review topology Equipment

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Distinguish installed physical units and station connections from maintainable-asset management.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 76. HMSR-063 — topology.Equipment review

**Decision:** REVISE  
**Review code:** HMSR-063  
**Dependency level:** 2  
**Bounded context:** topology  
**Confirmed upstream subject dependencies:** optional `topology.Facility`, optional `party.Party`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 76.1 Semantic role

`Equipment` is the Topology-owned physical component model attached to the hydrocarbon network. It carries stable equipment identity, optional attachment references to facility/node/pipeline-segment topology structures, a mandatory EquipmentType reference, lifecycle status, optional manufacturer Party snapshots and installation/retirement timestamps.

Topology owns the physical equipment identity and network placement. Party owns manufacturer identity. Assets owns maintainability and maintenance execution and must not replace the Topology equipment identity.

### 76.2 Equipment-type ownership

`equipmentTypeId` is mandatory and unambiguously references Topology-owned `EquipmentType`.

HRA-111 protects same-module row existence with:

```text
hidra_topology_equipment.equipment_type_id -> hidra_topology_equipment_type.id
```

That relationship is correct.

The Topology roadmap explicitly states that Equipment type is a catalog-backed controlled vocabulary and that Topology business taxonomy concepts are not roadmap-accepted as fixed Java enums.

### 76.3 EquipmentKind taxonomy duplication

The live `Equipment` model and `EquipmentType` persistence both also carry mandatory `EquipmentKind` values:

```text
PUMP
COMPRESSOR
VALVE
METER
TANK
FILTER
HEATER
SCADA_DEVICE
OTHER
```

These are business-facing equipment classifications, not lifecycle/technical states.

This duplicates classification semantics already assigned to the catalog-backed `EquipmentType` model and conflicts with the Topology roadmap rule that business type concepts are catalog-backed rather than fixed Java enums.

HMSR-063 does not decide the exact migration shape, but the target baseline must eliminate the duplicated taxonomy source of truth. Equipment classification should be governed through the Topology-owned EquipmentType/catalog design, while genuine lifecycle state remains enum-backed where appropriate.

### 76.4 Same-module topology attachments

`facilityId`, `nodeId` and `pipelineSegmentId` are nullable in the Equipment model and persistence.

Their intended targets are all Topology-owned:

```text
facilityId        -> topology.Facility
nodeId            -> topology.TopologyNode
pipelineSegmentId -> topology.PipelineSegment
```

Current HRA-111 evidence protects `equipmentTypeId` but does not add Equipment-side FKs for these three optional attachment references.

Because these are same-module ownership references rather than historical/external/polymorphic identifiers, populated values should fail closed against their Topology-owned targets.

HMSR-063 does not invent a rule requiring exactly one attachment, at least one attachment, or a particular combination; current Topology DDD evidence does not state that cardinality.

### 76.5 Manufacturer Party boundary

`manufacturerPartyId` is optional and accompanied by code/name snapshots.

The field clearly belongs to the Party bounded context. No cross-module database FK should be added.

Current repository evidence does not expose an Equipment registration/application use case that resolves a populated manufacturer through a Party-owned contract before persistence. Therefore the production obligation is owner-boundary validation when a manufacturer ID is supplied, while preserving the stored snapshots as historical/display metadata.

HMSR-063 does not make manufacturer identity mandatory.

### 76.6 Lifecycle semantics

`EquipmentStatus` is a Topology-owned lifecycle enum:

```text
PLANNED
ACTIVE
OUT_OF_SERVICE
DECOMMISSIONED
RETIRED
```

This is consistent with the repository's controlled-vocabulary policy distinction between business taxonomies and technical/lifecycle enums.

Current Topology evidence does not define a complete state-transition matrix, so HMSR-063 does not invent transition rules.

### 76.7 Installation and retirement timestamps

`installedAt` and `retiredAt` are optional.

Current invariant-classification/Topology evidence does not establish a required `installedAt <= retiredAt` invariant for Equipment. HMSR-063 therefore does not promote intuitive chronology into a new domain rule without stronger repository evidence.

### 76.8 Required revisions

Three evidence-backed obligations remain:

1. **Remove the duplicated EquipmentKind business-taxonomy source of truth.** Reconcile `Equipment.equipmentKind` and `EquipmentType.equipmentKind` with the roadmap's catalog-backed EquipmentType architecture rather than preserving fixed enum classification in parallel.

2. **Protect populated same-module attachment references.** `facilityId`, `nodeId` and `pipelineSegmentId` should fail closed against their Topology-owned targets using the appropriate same-module FK and/or application validation strategy.

3. **Validate populated `manufacturerPartyId` through a Party-owned application/lookup boundary.** Preserve manufacturer snapshots and do not introduce a cross-module database FK.

HMSR-063 does not modify production Java, JPA, Flyway, API/application contracts, tests, Topology catalog data, Party data, or provisioned data.

### 76.9 Review conclusion

**REVISE.** `Equipment` is a coherent 16-field Topology-owned physical component model whose mandatory EquipmentType reference is already protected by same-module FK and whose EquipmentStatus is a legitimate lifecycle enum.

The target baseline cannot mark it APPROVED while a fixed `EquipmentKind` enum duplicates the catalog-backed EquipmentType taxonomy, optional same-module placement references can dangle when populated, and manufacturer Party identity is not validated through its owning bounded-context contract.

HMS reconciliation must retain these obligations until explicitly authorized Topology production corrections resolve them or stronger repository evidence changes the governing Topology DDD.

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

- `781606a40cbf24aedf1338a2fcbce5976d3b6285` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:115](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L115)
- [docs/roadmap/model-semantic-review.md:258](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L258)
- [docs/roadmap/model-semantic-review.md:12504](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L12504)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:258 | HMSR-063 | 2 | topology | Equipment | — | party.Party, topology.Facility | 0 | 0 | 2 | REVISE | Completed | docs(model-review): review topology Equipment
doc/domain/SEMANTIC_DECISIONS.md:115 | HMSR-063 | [topology.Equipment](#topology-equipment) | REVISE | HMR-054
~~~~~~~~

<a id="hapi-504"></a>
## HAPI-504 — establish product aware twin network inputs

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-002

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-503

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Extend only demonstrated gaps in product-aware connected topology, fluid-property/composition references, equipment characteristics and versioned operating configurations; reject unsupported physics/product combinations.

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/database-dictionary-ownership.json`
- `AGENTS.md`
- `doc/database/DATA_DICTIONARY.md`
- `doc/database/README.md`
- `doc/database/SCHEMA_OWNERSHIP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/custody.md`
- `doc/modules/simulation.md`
- `doc/modules/topology.md`
- `doc/modules/workflow.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationGasFluidRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationProductCandidateContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/CustodyGasFluidWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/WorkflowGasFluidApprovalEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyGasFluidRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcCustodyGasFluidRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyApprovalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationQualifiedFluidRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCompressorCurveInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentModelInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInitialStateInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/CustodyQualifiedFluidRevisionQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationEquipmentParameterWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyStoredPhysicalNetworkRevisionQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/WorkflowEquipmentParameterApprovalEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcSimulationEquipmentParameterRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationInputSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/simulation/SimulationTrustedReadingContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyValueException.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologySnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/RevisionApprovalEvidenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowConfigurationPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/RevisionApprovalEvidenceQueryService.java`
- `src/main/resources/db/migration/V20261009_001__p25_topology_physical_network_revisions.sql`
- `src/main/resources/db/migration/V20261010_001__p25_custody_gas_fluid_revisions.sql`
- `src/main/resources/db/migration/V20261010_002__p25_simulation_equipment_parameter_revisions.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/CustodyGasFluidRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyGasFluidRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/QualifiedFluidEquipmentDeliveryIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationEquipmentParameterRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/RevisionApprovalEvidenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/RevisionApprovalEvidencePostgresIntegrationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent installed-unit/model/serial identity, effective station membership and series/parallel incidence with versioned links. The exact bounded remaining delivery is: Extend only demonstrated gaps in product-aware connected topology, fluid-property/composition references, equipment characteristics and versioned operating configurations; reject unsupported physics/product combinations..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCompressorCurveInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentInput.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidRevisionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`, `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/CustodyGasFluidWorkflowTargetLookup.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/WorkflowGasFluidApprovalEvidenceAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyGasFluidRevisionCodec.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcCustodyGasFluidRevisionRepositoryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/database-dictionary-ownership.json`
- `AGENTS.md`
- `doc/database/DATA_DICTIONARY.md`
- `doc/database/README.md`
- `doc/database/SCHEMA_OWNERSHIP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/custody.md`
- `doc/modules/simulation.md`
- `doc/modules/topology.md`
- `doc/modules/workflow.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationGasFluidRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationProductCandidateContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyGasFluidRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/CustodyGasFluidWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/WorkflowGasFluidApprovalEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyGasFluidRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcCustodyGasFluidRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyApprovalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationQualifiedFluidRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCompressorCurveInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentModelInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInitialStateInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/CustodyQualifiedFluidRevisionQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationEquipmentParameterWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyStoredPhysicalNetworkRevisionQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/WorkflowEquipmentParameterApprovalEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcSimulationEquipmentParameterRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationInputSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/simulation/SimulationTrustedReadingContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyValueException.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologySnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/RevisionApprovalEvidenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowConfigurationPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/RevisionApprovalEvidenceQueryService.java`
- `src/main/resources/db/migration/V20261009_001__p25_topology_physical_network_revisions.sql`
- `src/main/resources/db/migration/V20261010_001__p25_custody_gas_fluid_revisions.sql`
- `src/main/resources/db/migration/V20261010_002__p25_simulation_equipment_parameter_revisions.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/CustodyGasFluidRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyGasFluidRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/QualifiedFluidEquipmentDeliveryIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationEquipmentParameterRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/RevisionApprovalEvidenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/RevisionApprovalEvidencePostgresIntegrationTest.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-002 Input-Contract Execution Preflight — 2026-10-09

Owner Next selects ONLY this documentation-only preflight, exact message
`docs(twin): register HPR-P25-002 execution preflight`. Write ONLY the roadmap and
reconciliation. Verified parent `61c1311f458e6844d28c4b4d162b0916885d01a0`, tree
`774e1806a8953b5582dcb32081256449d5d290de`; Documentation #144
(run 37955963870) PASSED. Mandatory instructions and both execution memories read;
local source tree matches. Source behavior remains unchanged by this preflight.

##### Source revalidation and selected technical boundaries

- Topology exports `SimulationTopologyScopeContract` and
  `SimulationTopologyTargetContract` under its already architecture-exported
  `application/contract/simulation` package. Scope resolution currently supports
  active PIPELINE/PIPELINE_SYSTEM and explicitly rejects SEGMENT_GROUP/FACILITY_NETWORK.
  It checks eligibility, not physical completeness or immutable connected-network data.
- `topology/infrastructure/persistence/entity/TopologySnapshotJpaEntity.java` has
  snapshot code/version and JSONB payload. Do not infer payload schema, numerical
  completeness or immutable capture API from table fields.
- `simulation/infrastructure/persistence/entity/SimulationInputSnapshotJpaEntity.java`
  has scenario/topology/other snapshot references, capturedAt and captureHash; no
  corresponding current SimulationInputSnapshot domain record was found. Preserve this
  entity/table and its historical semantics; the first stage does not remap it.
- Node/segment JPA records and Equipment expose asset connectivity/identity, not complete
  physical curves or internal gas-engine inputs. Custody's Planning product contract
  exports catalogue identity only. Telemetry trust remains owned by Telemetry.
- ArchitectureGuardrailTest already exports Topology's simulation contract package.
  The first stage below imports no foreign module, so no new export/architecture
  allowlist change is needed. Later owner adapters require explicit exact-file review.

Selected engineering boundary: Simulation owns immutable solver input representations
and copies of versioned parameters, not the mutable source-of-truth network/catalogue.
Topology retains connectivity/configuration; Custody retains product identity; Telemetry
retains raw/trusted measurement provenance. Integration handles read-only acquisition.
Adapters use owner-exported DTOs, never foreign domain aggregates/JPA entities. Engine
inputs are prepared through Simulation application orchestration and local ports.
This is an implementation design consistent with existing boundaries, not fabricated
approval of GZ2 operating parameters or a new cross-module catalogue owner.

##### Input decomposition and version semantics

First build an **input manifest**: identities, versions, validity and provenance of the
inputs selected for one calculation. This is a concrete domain contract, not a placeholder
solver or a claim that physical payloads already exist. Later stages add typed payloads,
owner exports and capture/persistence. Distinguish metadata validation from physical
readiness; a valid manifest alone must never authorize an operational recommendation.

Every source version records kind, owner, source identity, immutable revision identity,
payload SHA-256 reference, recordedAt, effectiveFrom and optional effectiveUntil, plus
origin/evidence identity. Use inclusive start/exclusive end intervals. A missing end
means open-ended validity, not that the value is verified forever. Corrections create a
new revision; never update old run inputs. A digest is a reference at the domain stage;
canonical hashing/content verification belongs to capture, not guessed hash strings.

The manifest records identity/schema version, product identity reference, scope identity,
STEADY_STATE or TRANSIENT mode, initial stateAt, capturedAt, source versions, optional
measurement watermark and transient horizonEnd. Versions must be valid at initial
stateAt and recorded by capturedAt; initial state cannot be later than capture. Planned
future conditions belong in the transient schedule, never invented measured history.
Transient runs additionally reference a BOUNDARY_SCHEDULE with horizonEnd > stateAt.
A steady-state manifest has no transient horizon/schedule. A watermark, when supplied,
cannot be later than stateAt. Future forecast/scenario events remain distinguishable
from observations. Do not assign arbitrary freshness/uncertainty limits here.

Base source kinds: TOPOLOGY_CONFIGURATION, FLUID_MODEL, EQUIPMENT_PARAMETERS,
OPERATING_STATE; require exactly one explicit reference for each kind. EQUIPMENT_PARAMETERS
may identify an explicitly empty equipment model for a synthetic pipe-only network,
not an implicit missing/default curve. TRANSIENT adds BOUNDARY_SCHEDULE. Every source
kind has one revision in the initial manifest; an older/newer duplicate cannot silently
win. Richer partitioned inputs require a separately registered schema revision.
Origins: APPROVED_PARAMETER, TRUSTED_TELEMETRY, ESTIMATED, SYNTHETIC, SCENARIO and
FORECAST. Origin is declared evidence metadata, not proof of trust; later capture
resolves source evidence and validates whether the origin is permitted for the kind.
Mark any manifest with a SYNTHETIC source as synthetic; no operational readiness method
is introduced. Engine product/mode capability checks remain a separate execution gate.

##### Bounded HPR-P25-002 implementation stages

The parent HPR-P25-002 stays PENDING until all required stages are implemented and
verified; this preflight completion does not complete product-aware physical inputs.
Each stage retains its own commit and later evidence; no batch is selected.

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P25-002A | COMPLETED — implementation eef35dbd35d4558a4ceb57f87d8670fb97c933ea passed full Java 21 CI #612 and Documentation #146; three immutable contracts and 14 JUnit tests present; per-class report not independently retained at this baseline | Simulation / Input manifest | Code/Test | Implement immutable manifest, source-version identities/validity/origin and steady-state/transient metadata invariants under the exact six-file envelope below. | `feat(simulation): establish immutable input manifest contracts` | HPR-P25-002 preflight Documentation CI success |
| HPR-P25-002B | COMPLETED — B1..B4 CI-confirmed; status aligned by owner-approved consolidation; source resolution/capture remain parent 002 obligations | Simulation / Physical payloads | Code/Test | Define validated typed network/fluid/equipment/initial-state and transient timeline payloads with units, reference basis and missing-data semantics; register exact files and numeric design assumptions before mutation. | `feat(simulation): define versioned physical input payloads` | HPR-P25-002A; selected payload/physics preflight |
| HPR-P25-002C | PENDING — C1/C2 CI-confirmed; remaining C3/002D organized into four delivery outcomes; next shared network-delivery preflight | Owner modules / Simulation | Code/Test | Implement bounded owner-exported topology/product/measurement queries and Simulation adapters; preserve eligibility/trust/ownership and reject unavailable physical inputs. | `feat(simulation): resolve versioned owner input references` | HPR-P25-002B; exact owner-contract/export preflight |
| HPR-P25-002D | PENDING | Simulation / Capture | Code/Test | Assemble coherent immutable payload/manifest captures, verify canonical hashes and evidence, persist reproducible revisions and reject incompatible/missing inputs; register forward migration only if source-backed design requires one. | `feat(simulation): capture reproducible network calculation inputs` | HPR-P25-002C; exact capture/schema preflight |

##### HPR-P25-002A exact implementation envelope

The next owner Next after successful preflight Documentation CI selects ONLY
HPR-P25-002A implementation; this preflight already registers its exact scope.
Do not create another documentation-only preflight for A unless evidence changes.

Create ONLY:

1. `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`
   — enum STEADY_STATE and TRANSIENT; no implicit mode default.
2. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
   — immutable Java record with nested SourceKind/Origin enums and local validation.
3. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
   — immutable Java record with defensive list copy and coherent mode/time/version rules.
4. `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`
   — meaningful positive/negative metadata and temporal/immutability cases.

Update ONLY `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Total authorized scope: six paths.
Canonical Java headers keep Author Abir MEDJERAB and CreatedOn 2025-06-26.
Reuse existing InvalidSimulationValueException; production imports only Java standard
library and local Simulation types. No Spring/JPA/foreign modules, physical calculation,
controller/API, dictionary/OpenAPI regeneration, dependencies, POM, workflows, existing
models/entities or ArchitectureGuardrailTest may change. No migration is needed because
these contracts are not persisted or exposed; later capture has a separate schema gate.
Nested validation/private helpers stay within these three production files.

Minimum behavioral validation: valid synthetic steady-state and transient manifests;
required IDs and positive schema version; valid SHA-256 syntax; effective interval and
captured/recorded/state/watermark order; missing/duplicate source kinds; transient horizon
and schedule requirements; rejection of schedule in steady-state; unmodifiable defensive
copy; synthetic origin detection and preserved original version after replacement input.
Use fixed timestamps; never depend on wall-clock time or actual GZ2 values. Test only
metadata guarantees, not hydraulic accuracy. Origin declarations do not bypass trust.

Commands when A is selected:
`./mvnw -B -q -Dtest=SimulationInputManifestTest,ArchitectureGuardrailTest test`,
then `./mvnw -B -q clean verify`; also canonical documentation/OpenAPI/P1 evidence
validators and `git diff --check`. Require exact-head applicable full CI and report
local toolchain/dependency blocks accurately. Publish with expected-parent lease;
observe CI startup and STOP for owner Next/Fail. Do not implement B..D or the engine.

For B/C/D, first select a supporting exact preflight message
`docs(twin): register HPR-P25-002X execution preflight`, replacing X with B/C/D,
writing these two execution memories only. Later exact files/contracts/migrations are
not authorized by this high-level decomposition. Physical formulation/units/reference
fixtures can be designed with synthetic cases; actual GZ2 data gates field calibration.
HPR-P25-008A numerical design may start after the physical input contract is established;
no solver method is dictated by a manifest. HPR-P25-017 closure includes A..D as well as
008A..F; incomplete inputs cannot be hidden by parent-task metadata completion.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidQualificationServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/application/service/CustodyGasFluidRevisionQueryServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevisionTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/CustodyGasFluidRevisionPostgresIntegrationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyGasFluidRevisionCodecTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevisionTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java` — exact-head oracle suite 20 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java` — exact-head oracle suite 14 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java` — exact-head oracle suite 21 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/QualifiedFluidEquipmentDeliveryIntegrationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationEquipmentParameterRevisionPostgresIntegrationTest.java` — exact-head oracle suite 11 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodecTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java` — exact-head oracle suite 20 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/RevisionApprovalEvidenceQueryServiceTest.java` — exact-head oracle suite 19 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/RevisionApprovalEvidencePostgresIntegrationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Independent cases A–G; mixed parent not closed
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-503. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:7405](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7405)
- [doc/model-remediation/RECONCILIATION.md:9246](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L9246)
- [doc/model-remediation/RECONCILIATION.md:9362](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L9362)
- [doc/model-remediation/RECONCILIATION.md:9680](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L9680)
- [doc/model-remediation/RECONCILIATION.md:9725](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L9725)
- [doc/model-remediation/RECONCILIATION.md:9755](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L9755)
- [doc/model-remediation/RECONCILIATION.md:9789](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L9789)
- [doc/model-remediation/RECONCILIATION.md:9829](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L9829)
- [doc/model-remediation/RECONCILIATION.md:10273](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10273)
- [doc/model-remediation/RECONCILIATION.md:10593](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10593)
- [doc/model-remediation/RECONCILIATION.md:13631](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13631)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6068](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6068)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:594](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L594)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:853](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L853)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:10986](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L10986)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:11102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L11102)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:11420](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L11420)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:11465](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L11465)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:11495](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L11495)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:11529](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L11529)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:11569](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L11569)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12013](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12013)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12333](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12333)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15371](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15371)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:853 | HPR-P25-002 | PENDING — input-contract preflight delivered; A and B1..B4 CI-confirmed; C/D and aggregate closure remain PENDING | Topology / Custody / Simulation | Code/Test | Extend only demonstrated gaps in product-aware connected topology, fluid-property/composition references, equipment characteristics and versioned operating configurations; reject unsupported physics/product combinations. | feat(topology): establish product aware twin network inputs | HPR-P25-001 approved input/ownership contracts
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15371 | HPR-P25-002 | PENDING — input-contract preflight delivered; A and B1..B4 CI-confirmed; C/D and aggregate closure remain PENDING | FOUNDATION_SCOPE_REFINEMENT | Topology / Custody / Simulation | Independent successor/design 018/028/029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 018/028/029 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6068 | HPR-P25-002 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13631 | HPR-P25-002 | PENDING — input-contract preflight delivered; A and B1..B4 CI-confirmed; C/D and aggregate closure remain PENDING | FOUNDATION_SCOPE_REFINEMENT | Topology / Custody / Simulation | Independent successor/design 018/028/029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 018/028/029 separately selected scope separation
~~~~~~~~

<a id="hapi-547"></a>
## HAPI-547 — inventory station graph persistence prerequisites

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-035

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

f1659fe7677050c1a0d80542506db401ccbbdcec, Documentation #226 SUCCESS

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraphRevision.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Distinguish installed physical units and station connections from maintainable-asset management.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraphRevision.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInternalGraphRevision.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P25-035 — preflight, NOT implementation
Registered separate next task message: `docs(topology): inventory station graph persistence prerequisites`. Exclusive future preflight allowlist is the same two append-only governance documents. Requires owner separate `next` and successful exact-head Documentation Validation for this registration.

The preflight shall inspect the current Topology entity/repository/adapter/application-port layout, migration version namespace and latest Flyway inventory, current authorization/tenancy contracts, existing physical source revision persistence and OpenAPI architecture guards. Record actual source paths and identities. Define the smallest separately registerable immutable PostgreSQL append/read slice for `StationInternalGraphRevision`; do not presume existing tables, schema, migration numbers, JPA patterns or APIs. Specify canonical serialization version and stable ordering before any digest, exact station+revision uniqueness, expected-head concurrency boundary, idempotent same-byte replay versus conflicting bytes, source classification and effective/recorded history, append-only withdrawal/event records, and transactional PostgreSQL failure handling. Preserve synthetic provenance; qualified evidence must be an independently verified source/actor contract. No cross-owner writes.

Deliver a future **implementation** envelope proposal with exhaustive exact production/test/migration paths, migration naming and rollback policy, responsible Topology owner, focused Maven/PostgreSQL integration tests (new database and existing upgraded database), architecture/security/OpenAPI compatibility checks, and full production exact-head verification. Operator-facing publication/API and Workflow approval remain separate registered slices; no hydraulic execution and no changes to physical V1/V2 or historical Gas contracts.

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

- `f1659fe7677050c1a0d80542506db401ccbbdcec` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:14574](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L14574)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6126](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6126)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:16559](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L16559)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:16779](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L16779)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:16779 | HPR-P25-035 | COMPLETED — documentation preflight | f1659fe7677050c1a0d80542506db401ccbbdcec, Documentation #226 SUCCESS
doc/roadmap/ULTIMATE_ROADMAP.md:6126 | HPR-P25-035 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:14574 | HPR-P25-035 | COMPLETED — documentation preflight | f1659fe7677050c1a0d80542506db401ccbbdcec, Documentation #226 SUCCESS
~~~~~~~~

<a id="hapi-549"></a>
## HAPI-549 — persist immutable station graph revisions

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-037

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

persist immutable station graph revisions

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/database-dictionary-ownership.json`
- `doc/database/DATA_DICTIONARY.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261011_002__p25_topology_station_graph_revisions.sql`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/StationInternalGraphRevisionPostgresIntegrationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Distinguish installed physical units and station connections from maintainable-asset management.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java`, `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/StationInternalGraphRevisionPostgresIntegrationTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/database-dictionary-ownership.json`
- `doc/database/DATA_DICTIONARY.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261011_002__p25_topology_station_graph_revisions.sql`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/StationInternalGraphRevisionPostgresIntegrationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### HPR-P25-037 — Immutable station-graph PostgreSQL append and exact read

**State:** COMPLETED within immutable storage scope. Implementation `4e7ac44f9eb917fa6eee42aaf858e2e6b0886fa4` plus corrective test commit `0868f2d17ce0eaefc53902ce36fa711681cd406b`; exact-head Production #654 and Documentation #237 SUCCESS. The complete original registration below remains historical and binding for its delivered scope.

**Exact approved implementation commit:** `feat(topology): persist immutable station graph revisions`

**Prerequisites:** successful registration-documentation CI, separate owner Next, refreshed `main`, migration/path collision check; HPR-P25-032/034/035/036 accepted foundations.

**Exact exclusive future implementation files:**

| Operation | Path |
|---|---|
| CREATE | `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java` |
| CREATE | `src/main/resources/db/migration/V20261011_002__p25_topology_station_graph_revisions.sql` |
| CREATE | `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/StationInternalGraphRevisionPostgresIntegrationTest.java` |
| UPDATE | `.github/database-dictionary-ownership.json` |
| UPDATE | `doc/database/DATA_DICTIONARY.md` |
| UPDATE | `doc/roadmap/ULTIMATE_ROADMAP.md` |
| UPDATE | `doc/model-remediation/RECONCILIATION.md` |
| DELETE | **None authorized** |

**Engineering contract:** Store V1 canonical byte payload and verified digest under exact (facility, revision) identity; identical replays succeed; differing payload at same key conflicts; invalid format/digest/canonical encoding or altered identity raises error; immutable SQL mutation guards; concurrency, rollback and actual PostgreSQL 16 fresh/upgrade tests. Owner-private persistence does **not** establish company authorization, REST publication, approval/head CAS or SITE acceptance. The complete original exact approved specifications, SQL constraints, test commands, source references and limitations appear next in the **verbatim 037 registration** section; these remain binding, not replaced by this summary.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/StationInternalGraphRevisionPostgresIntegrationTest.java` — exact-head oracle suite 14 cases, zero failures/errors/skips.

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

- `4e7ac44f9eb917fa6eee42aaf858e2e6b0886fa4` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/roadmap/ULTIMATE_ROADMAP.md:6128](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6128)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6253](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6253)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:6128 | HPR-P25-037 | COMPLETED / ACCEPTED (recorded bounded scope) | 049 directly confirmed #666/#266; other accepted subsets retain original source/CI limits
~~~~~~~~

<a id="hapi-598"></a>
## HAPI-598 — Integrated closure: five installed-unit/manufacturer-data packages

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-054

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-603

Cross-Repository Dependencies: HWEB-F04, HWEB-F05 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-054; child tasks own implementation. five installed-unit/manufacturer-data packages

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent installed-unit/model/serial identity, effective station membership and series/parallel incidence with versioned links. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-054; child tasks own implementation. five installed-unit/manufacturer-data packages.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Distinguish installed physical units and station connections from maintainable-asset management.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-054 — five installed-unit/manufacturer-data packages

Prerequisites:052 source ownership and retained020/021 design, with D3's per-owner
contracts. Topology owns installed configuration; Assets owns maintainability; Party
owns manufacturer identity/role; existing Simulation stores retain accepted characteristic
bytes even while numerical development stays deferred. No ownership relocation implied.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| HPR-P25-054A | Independent installed-unit identity, model/serial/lifecycle and correct source-owner references. | Distinguish installed units from maintainable assets, validate manufacturer identity/role/kind and company ownership, preserve existing Assets interfaces. |
| HPR-P25-054B | Effective facility/station configuration membership and series/parallel installed incidence. | Nine distinct units across three synthetic stations, install/retire/time/conflict cases, no shared identity or dangling incidence, authorized reload/history. |
| HPR-P25-054C | Supplied manufacturer maps/tables/limits/axes/units/reference conditions and source evidence. | Immutable source revisions retain exact supplied facts; unsupported/missing curve/axis/unit/evidence stays incomplete, no interpolation engine or invented vendor data. |
| HPR-P25-054D | Versioned installed-unit to characteristic links, approval and current/historical read. | Pin exact owner revisions/digests, changed source cannot mutate old station/baseline content, cross-owner failure rolls back; no implicit simulator qualification. |
| HPR-P25-054E | Owner/API/security/compatibility and consumer-ready qualification. | Actual save/read/revise across all owners, nine independent unit/source fixtures, malformed maps, denial, race/idempotency/rollback and old-format decode; exact accepted contracts for F04/F05. |

Manufacturer data is reusable engineering evidence; do not force general vendor facts
into synthetic formulas or make it depend on a future solver run or baseline.

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Distinguish installed physical units and station connections from maintainable-asset management.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-603. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23385](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23385)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1323](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1323)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5151](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5151)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-599"></a>
## HAPI-599 — Independent installed-unit identity and references

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-054A

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-567

Cross-Repository Dependencies: HWEB-F04, HWEB-F05 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Independent installed-unit identity, model/serial/lifecycle and correct source-owner references.

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent installed-unit/model/serial identity, effective station membership and series/parallel incidence with versioned links. The exact bounded remaining delivery is: Independent installed-unit identity, model/serial/lifecycle and correct source-owner references..

### 4. Functional Requirements

Distinguish installed units from maintainable assets, validate manufacturer identity/role/kind and company ownership, preserve existing Assets interfaces.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/InstalledUnit.java` — pure Java owned aggregate/value and invariants for independent installed identity/model/serial/lifecycle and validated Party manufacturer reference.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/InstalledUnitUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/InstalledUnitRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/InstalledUnitApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/InstalledUnitController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcInstalledUnitRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/topology/installed-units` for independent installed identity/model/serial/lifecycle and validated Party manufacturer reference. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Distinguish installed units from maintainable assets, validate manufacturer identity/role/kind and company ownership, preserve existing Assets interfaces.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-567. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23394](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23394)
- [doc/model-remediation/RECONCILIATION.md:23705](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23705)
- [doc/model-remediation/RECONCILIATION.md:23744](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23744)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1013](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1013)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1052](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1052)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1332](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1332)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1013 | HPR-P25-054A | Confirm canonical identity relationship among Topology Equipment, physical installed unit, manufacturer model/serial and optional MaintainableAsset. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1052 | HPR-P25-054A | REQUIRES_OWNER_DECISION | Independent installed-unit identity and references
doc/roadmap/ULTIMATE_ROADMAP.md:1332 | HPR-P25-054A | Independent installed-unit identity, model/serial/lifecycle and correct source-owner references. | Distinguish installed units from maintainable assets, validate manufacturer identity/role/kind and company ownership, preserve existing Assets interfaces.
doc/model-remediation/RECONCILIATION.md:23394 | HPR-P25-054A | Independent installed-unit identity, model/serial/lifecycle and correct source-owner references. | Distinguish installed units from maintainable assets, validate manufacturer identity/role/kind and company ownership, preserve existing Assets interfaces.
doc/model-remediation/RECONCILIATION.md:23705 | HPR-P25-054A | Confirm canonical identity relationship among Topology Equipment, physical installed unit, manufacturer model/serial and optional MaintainableAsset. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23744 | HPR-P25-054A | REQUIRES_OWNER_DECISION | Independent installed-unit identity and references
~~~~~~~~

### HAPI-599 owner policy registration — 2026-10-27

**Decision authority, scope and evidence.** The repository owner explicitly selected Option A for each OD-HAPI-599-01, OD-HAPI-599-02 and OD-HAPI-599-03 during the 2026-10-27 owner-decision sequence and instructed publication of the decisions in separate task-scoped stages. This one-document record belongs to HAPI-599 (HPS-006, legacy HPR-P25-054A), with exact message `docs(topology): record owner-approved installed-unit identity policy`. It records *owner-approved target policy only*; no implementation, test, API, schema, status or upstream evidence changes are performed in this documentation stage.

**Approved owner decisions (all Option A):**

1. **OD-HAPI-599-01 — Separate physical installed-unit identity and effective-dated installation history.** The topology owner distinguishes the operational/topological `Equipment` position/reference from a physically replaceable unit with its own persistent Hidra identity. Versioned effective-dated installation records associate a physical unit to an identified position and preserve previous occupancy when a compressor, valve, pump or instrument is removed, replaced, repaired and installed elsewhere. Manufacturer/model/serial details belong to physical-unit identity and its evidence, not to an overwritten equipment-position record. `MaintainableAsset` remains an *optional*, independently Assets-owned maintenance record linked only by an approved neutral reference; do not merge or duplicate its aggregate ownership.
2. **OD-HAPI-599-02 — Canonical manufacturer/model references with explicitly provisional metadata.** Each physical unit receives an immutable Hidra identity irrespective of any serial marking. Validate manufacturer identity, role/kind and appropriate company ownership using the existing **Party** public reference contract. Model definition/references are manufacturer-specific and distinct from topology equipment-type classification and individual unit identity; do not assume a new manufacturer-model aggregate already exists. Manufacturer, model and serial fields carry provenance and verified/unverified/unknown distinctions, and unknown values remain explicitly unresolved rather than fabricated. Manufacturer-scoped or repeated serial identifiers are not proof of global physical identity; potential duplicates/conflicts require controlled review, **not automatic merging**. Unverified facts never silently qualify an engineering simulation, manufacturer characteristic or operator recommendation.
3. **OD-HAPI-599-03 — Atomic installation lifecycle, temporal non-overlap and immutable evidence.** An installation has a required effective-start instant and optional effective-end instant. A unit may not have mutually incompatible simultaneous effective installations; an identified single-occupancy mounting position may not host conflicting units over the same effective interval. Intentional multi-unit assemblies use explicitly distinct positions and qualified incidence rather than bypassing overlap invariants. Replace, relocate and remove operations are authorized and transactionally consistent: closing prior effective occupancy and establishing replacement/move must commit atomically, with stale-version/race protection. Preserve historical installations and their provenance; corrections must be auditable revisions, not silent rewrites of prior identity, times or evidence. No generic claim of deployment-specific operational dates or calibrated characteristic values is made.

**Ownership and architectural preservation.** `topology` owns equipment/physical-unit identity and installation-position links. `assets` owns maintenance semantics and `MaintainableAsset`, reached through approved neutral references/public contracts. Manufacturer identity and validity come from Party's approved public reference. Manufacturer model, serial identity, current position, topology graph incidence and maintenance history must not be conflated. Preserve pure-Java domain invariants, inward hexagonal dependencies, explicit DTO/adapter mapping and company/resource scoped authorization before exposing existence, history or errors. Versioned engineering input and station graph revisions retain independent authorship and provenance; simulation numerical/P3 and OT actuation remain out of scope.

**Acceptance work not delivered by this policy record.** HAPI-567 is recorded VERIFIED/COMPLETED as a prerequisite, not independent proof of this new feature. Before admitting executable paths, inspect exact current Topology/Party/Assets source contracts, installed-unit references, station-graph revision ownership, migration inventory, PostgreSQL constraints and OpenAPI. Task proposals under section 6 remain **PROPOSED / DISCOVERY_REQUIRED**; no declaration that `InstalledUnit.java`, a REST endpoint, tables, ownership links or concurrency fences already exist. Register a bounded future implementation stage with its own exact message and allowlist. Require synthetic positive/negative tests for missing/wrong-owner/wrong-kind Party identities, provisional serial evidence, duplicate suspicion handling, movement and replacement, overlap and concurrent installs, atomic rollback, historical exact reads, company/role scope, preservation of Assets interfaces, fresh PostgreSQL/Flyway proof, API/architecture behavior and exact-SHA CI. Human engineering qualification and SITE/deployment acceptance remain separate.

**Planning and implementation disposition.** At prepublication main `dbb1f81374f73ca7a26ad0782af6078ca70f597c`, HAPI-599 remains **NOT_IMPLEMENTED / BLOCKED**. The three owner-policy selection questions are resolved and documented; implementation, source-backed admission, tests, CI and qualification gates remain open. ROADMAP/PHASES frozen table statuses and all other task definitions are unchanged. HAPI-611 has separately approved owner decisions that require a separate bounded task publication, not this commit.



### HAPI-599 — exact-source installed-unit implementation preflight — 2026-10-28

**Selected bounded result.** The owner `Next` after HAPI-593 parent closure
selects this source/contract discovery step under the *existing* HAPI-599 /
HPR-P25-054A obligation; it is not a new standalone task or an approval of a
runtime implementation. Exact preflight commit message:
`docs(topology): register installed-unit identity source preflight`.
CREATE none; MODIFY only `doc/roadmap/TASKS_HPS-006.md`; DELETE none.
No task status, product/Party/Assets/Topology source, schema, inventory,
API/OpenAPI, workflow, authorization rule or other task record is changed.

**Recovered main and prerequisite.** Main at
`484226bf1e23960eb23b8d26e573736b6cb817cc` is the latest source
baseline. Its HAPI-593 governance-only Documentation CI #664/run
`40668031121` completed success. Prior unchanged runtime source at
`d32164613ea7a4acf43826dd72d0f4a6824235ca` has Full #945 and
Fast #248 success, with no independent HAPI-599 installed-unit code execution.
HAPI-567 is VERIFIED/COMPLETED in the frozen roadmap; that identity/security
prerequisite alone does not confer a generic operation grant.
OD-HAPI-599-01/02/03 Option A were approved and recorded on 2026-10-27.
This preflight preserves those decisions without repeating or weakening them.

**Exact verified current owner surfaces and what they do not establish.**

| Owning boundary and current file | Source-proven reality | Missing HAPI-599 obligation |
|---|---|---|
| Topology domain `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`; JPA `infrastructure/persistence/entity/EquipmentJpaEntity.java` | Equipment has its own id/code/facility/node/segment/type, optional manufacturer Party snapshot, status and installed/retired instants. Equipment adapter `JpaEquipmentRepositoryAdapter` uses `TopologyPartyReferenceContract.exists` for optional manufacturer reference. | The existing Equipment record is **not** a separate replaceable physical-unit registry or immutable effective installation history. Do not silently repurpose its identity or manufacturer snapshot. |
| Topology `domain/model/StationInternalGraph.java`, `domain/model/StationInternalGraphRevision.java`, `infrastructure/persistence/adapter/JdbcStationInternalGraphRevisionRepositoryAdapter.java` | Station graph declares headers, unit slots by `equipmentId`, ports and edges; revision has source/qualification/time evidence and append-only stored bytes/digest. | Station graph `Unit.equipmentId` does not prove a unique physical installed unit, model/serial or valid position-occupancy period. Never overwrite historical graph identity or infer an approved unit/port correlation. |
| Party `application/contract/topology/TopologyPartyReferenceContract.java`, `TopologyPartyEligibilityContract.java`, `TopologyProtectedPartyEligibilityContract.java` | Respectively existence, general active-reference eligibility, and native protected owner coordinates/fences. Protected contribution is *not* a transferable permission. | No reviewed exported contract currently proves manufacturer role/kind, model provenance or company-specific eligibility for this new operation. `exists` and general `eligibleForNewReference` are insufficient. |
| Party `infrastructure/persistence/entity/ManufacturerProfileJpaEntity.java`, `PartyRoleAssignmentJpaEntity.java` | Persist manufacturer capability and qualification metadata, role effective dates/status, but these are Party-private tables. | Topology may not import Party JPA or infer a manufacturer grant from an enum or a role label. Need a new explicit provider-owned public eligibility contract with reviewed status, time, role/kind and company behavior. |
| Identity `application/contract/topology/TopologyScopedAccessContract.java`; Topology `application/service/ProtectedStationAdmissionCoordinator.java` | Existing station operations have explicit company/facility actions and protected Identity/Party/Facility admission with final fences in the physical transaction. | `installed-unit` action/scope/position membership is not defined here. A new public endpoint cannot inherit station permission or reuse its authority as a universal installed-unit permit. |
| Assets `domain/model/MaintainableAsset.java`; `application/service/AssetsApplicationService.java` | Assets owns independent maintenance identity, service history and references to Topology/Party via approved exported contracts. | Optional maintenance linkage must be a neutral ID/contract; never merge or write Assets state from Topology. |

**Actual current schema/transport discovery.** The GitHub
`src/main/resources/db/migration/` directory lists 182 versioned files
through `V20261028_001__custody_publication_trigger_row_dispatch.sql`.
Existing relevant migrations include
`V20261011_002__p25_topology_station_graph_revisions.sql`,
`V20261018_003__p25_topology_protected_station_graph_operations.sql`,
`V20261019_001__p25_topology_engineering_drafts.sql` and
`V20261021_001__party_organization_configuration_reference.sql`.
This is an inventory observation, **not** a reserved migration version or
actual fresh PostgreSQL verification. Current Topology controller directory has
no `InstalledUnitController.java`; repository code search found no
`InstalledUnit` source model. The section 6 `POST /api/v1/topology/installed-units`
and its DTOs remain proposals, not published OpenAPI operations. No external
HidraWEB edit is admitted by this step.

**Bounded implementation decomposition (registration proposal, NOT EXECUTED).**

- **A — independent domain and durable identity/history:** first reconcile
  existing Equipment/Station graph position IDs with the approved Option A.
  Candidate `domain/model/InstalledUnit.java`,
  `domain/model/InstalledUnitInstallation.java`, an inward
  `application/port/out/InstalledUnitRepositoryPort.java`, a single Topology
  JDBC persistence adapter and additive Flyway DDL, with focused pure-Java and
  disposable PostgreSQL tests. Require independently immutable physical IDs,
  evidence classification/unknown serials, half-open installation
  `[effectiveFrom,effectiveUntil)` intervals, effective-end greater than
  start, per-unit and single-position non-overlap, same-transaction
  replace/relocate/removal, optimistic versioning, immutable historical reads,
  rollback and concurrent-writer constraints. This is an *internal candidate*
  until exact filenames, topology reference representation, temporal DDL,
  approved lock order and version reservation are reviewed. No unprotected
  public mutation or implicit manufacturer attestation.
- **B — owner evidence and protected transaction orchestration:** extend only
  the approved Party `application.contract.topology` public surface with
  explicitly scoped manufacturer role/kind/company/current eligibility, plus
  current protected Identity/Topology position admission. Use actual Party-owned
  resolution and one guarded transaction; fail closed on unavailable, wrong
  company/kind, revoked permission, stale evidence, conflict/duplicate or
  ambiguous serial. Register the exact provider/consumer implementation files,
  fences, tests and operation deadlines separately after inspecting Party
  ownership/role and current Topology protected admission implementations.
  No domain-to-foreign-private dependency is allowed.
- **C — typed browser contract and qualification:** introduce explicit
  REST DTO/mapper/controller only after A and B. Verify create/read/history/
  revision and required replacement/relocation semantics, principal scope
  before existence/history, 400/401/403/404/409 behavior and the accepted
  OpenAPI/permission/current-base comparison. Register actual synthetic
  lifecycle, duplicate suspicion, temporal overlap, stale CAS, rollback,
  per-company denial and historical-replay tests and CI source/case hashes.
  Do not present this as HAPI-600 station incidence or HAPI-601 manufacturer
  characteristic editor completion.

**Acceptance and block disposition.** This source preflight closes only the
evidence gap between owner-approved policy and actual current application
boundaries; HAPI-599 remains `NOT_IMPLEMENTED / BLOCKED` in both frozen status
tables until a separately selected, exact-file executable package is admitted
and implemented/tested. In particular, current Party public contracts lack the
role/kind/company proof necessary to authorize new installed-unit writes;
migration number, exact DTOs, physical position mapping and concurrency design
are deliberately not invented. The next candidate is bounded **HAPI-599 A
technical-registration/implementation** after review of those exact seams,
not HAPI-600/601 and not a numerical solver. All later stages require their own
message, CREATE/MODIFY/DELETE allowlist and fresh synthetic acceptance.
This preflight's actual validation is document structure, unchanged status rows
and exact scope; it does not execute Java, Flyway, PostgreSQL or runtime OpenAPI.
Documentation CI on the exact new docs-only SHA is the remaining gate; observe
startup after guarded publication and stop.



### HAPI-599 Package A1 — pure-Java installed-unit identity and effective installation invariants — 2026-10-28

**Registered bounded execution after owner Next.** Documentation CI #665 on
`f3a30d18f5d2461fbc9a002e71d37dd1ddf67fa7` passed. This one
existing HAPI-599 / HPR-P25-054A child package has exact message:
`feat(topology): introduce installed-unit domain identity and effective installation invariants`.

CREATE: `src/main/java/dz/sh/hidra/modules/topology/domain/model/InstalledUnit.java`,
`src/main/java/dz/sh/hidra/modules/topology/domain/model/InstalledUnitInstallation.java`,
`src/test/java/dz/sh/hidra/modules/topology/domain/model/InstalledUnitTest.java`,
`src/test/java/dz/sh/hidra/modules/topology/domain/model/InstalledUnitInstallationTest.java`.
MODIFY: `doc/roadmap/TASKS_HPS-006.md`,
`.github/ci-test-inventory.json`. DELETE: none. Do not alter unrelated
modules, existing migrations or frozen ROADMAP/PHASES status cells.

**Domain scope.** Topology now represents a physical-unit id distinct from
the topological Equipment position and optional Assets MaintainableAsset.
Manufacturer Party/model/serial evidence is explicitly UNKNOWN, UNVERIFIED
or VERIFIED, with provenance for known values. A supplied classification
does not attest Party role, current eligibility, company ownership or
engineering qualification; repeated serials do not merge two units.
Immutable effective installation declarations contain unit/position/company,
inclusive start/exclusive end, provenance, recorded time and revision. A
candidate snapshot rejects duplicate installation identities and overlapping
unit/single-position occupancy, preserving adjacent replacement history.
Ten synthetic JUnit tests cover these invariants. Their two test classes
are exact-source registered in the CI unit-architecture lane with fast
selection. `baseline_seconds=0` for both is explicitly an **unmeasured
scheduling placeholder**, not observed test runtime.

**Not completed.** No persisted identity/history, atomic relocation or
replacement, PostgreSQL temporal exclusion/lock/race protection, owner-backed
manufacturer role/kind/company validation, protected Identity authorization,
REST contract/OpenAPI or manufacturer curve data has been delivered.
A1 is partial pure-Java domain implementation only; package A2 must
register database and repository, B protected owner evidence, and C
transport/qualification under separately selected exact messages and
allowlists. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED as an integrated
workflow until those gates pass. No Flyway version is reserved. Static
inventory and source shape can be checked before publication; exact-SHA
Full/Fast/Docs acceptance is pending and SITE is not claimed.



### HAPI-599 Package A — durable identity, temporal persistence and history continuation — 2026-10-28

**Owner package-boundary clarification.** The original **A / B / C** package
structure remains authoritative. The earlier published A1 label describes only
a *completed domain commit within Package A*; it is not a newly approved
package, and no independently registered A2 or D stage is created. This
continuation adds the remaining owner-private storage work under the SAME
Package A. Future extra packages require explicit owner approval. This
documentation records the classification without rewriting earlier append-only
history or changing frozen task-table status.

**Exact selected execution.** Previous source SHA
`c4e87366f87b0d8a250e5249d13358352c5aa0f2` passed
Full #946, Fast #249 and Docs #666. Owner Next selects a bounded
Package A persistence commit, exact message:
`feat(topology): persist installed units and guarded installation history`.
CREATE only:
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/InstalledUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcInstalledUnitRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261028_002__topology_installed_unit_identity_and_history.sql`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/InstalledUnitPostgresIntegrationTest.java`

MODIFY only `.github/ci-test-inventory.json` (one real new test class,
seven cases, actual source SHA256) and `doc/roadmap/TASKS_HPS-006.md`.
DELETE none. Do not alter earlier Flyway versions, public REST/OpenAPI,
identity grants, existing Equipment or Assets models, approved manufacturer
semantics, frozen ROADMAP/PHASES or unrelated registry entries. Reserved
new migration version is `20261028.002` following the source-confirmed
`V20261028_001` tail; verify uniqueness again at the guarded write.

**Owned source and SQL contract.** Independent installed-unit immutable
identity includes exact company, kind and explicitly classified provenance
for manufacturer Party/model/serial. Physical identity uses unit id, never
serial uniqueness; duplicate serials do not auto-merge. Owner-private
persistence port and JDBC adapter return scoped exact reads, append
installation evidence, optimistic revision closure and exact revision
history. The new additive PostgreSQL schema enforces:
1. Composite unit+company reference for installation, and a physical
   `hidra_topology_equipment(id)` reference for the mounting position
   (position company/business ownership still requires Package B).
2. `btree_gist` effective-time half-open exclusion for **each unit** and
   **each single-occupancy position**, enforced by PostgreSQL on simultaneous
   writers, not only the Java snapshot validator.
3. One-way closure of an open installation by expected revision, without
   changing unit, position, company, start or recorded provenance.
   The mutable effective projection and immutable per-revision event rows
   remain separate: the first/open snapshot survives later closure.
4. Native no-update/delete/truncate guards on identity/history and
   reject-direct-event-insertion trigger protection. Repeated exact
   identity registration is idempotent; changed contents are rejected.
   Protected replace/relocate requires caller-managed same physical
   transaction over close + append, with rollback on failure.

**Synthetic validation and remaining acceptance.** One dedicated PostgreSQL
Testcontainers class has seven tests: immutable unit/serial replay,
versioned history, exclusion by unit/position, rollback, concurrent
competing positions, direct mutation rejection and a fresh complete Flyway
chain including `20261028.002`. The class is registered in the current
ten-shard PostgreSQL CI inventory, with `baseline_seconds=0` explicitly
UNMEASURED until actual execution. Local code/SQL review and source hashes
are not PostgreSQL execution proof. The existing full CI runs on exact
source SHA are required to admit this package.

**Preservation and unfinished cross-owner obligations.** This is a
private repository/storage capability only: no public use case, web
controller, REST exposure or UI; no standalone grant, Party manufacturer
role/kind/company eligibility, protected in-transaction admission or
station connectivity qualification exists yet. **Package B** still owns
live Identity/Party/position permissions, transaction admission and
compensation/audit governance before any external mutation; **Package C**
still owns authorized HTTP DTOs/contract and compatibility/consumer
qualification. Existing `MaintainableAsset` ownership remains Assets-only;
the optional link is only a neutral string and no Assets state is modified.
No inferred simulator-ready manufacturer characteristic is asserted.
HAPI-599 remains NOT_IMPLEMENTED/BLOCKED in the frozen registry until
all its owner-scope, tests and API acceptance gates actually pass.
This is NOT deployed, SITE accepted or numerical/P3 scope.




### HAPI-599 Package A — CI #947 focused source and test-environment repair — 2026-10-28

**Failed acceptance and scope.** Exact source `96d63850deb185817195c5915160fd8ea7ca7709`
has Full CI #947/run `40673485774` **FAILED**, while same-SHA Fast #250 and
Docs #667 succeeded. Full CI logs in all ten PostgreSQL shards and
the API contract lane identify Spring `BeanCreationException`/
`Cannot subclass final class ...JdbcInstalledUnitRepositoryAdapter`.
The new Topology `@Repository` has `@Transactional` methods and is
proxied by Spring; declaring it `final` prevents CGLIB proxy creation,
breaking previously valid application-context tests across other modules.
Separate PostgreSQL 18 fresh-schema Flyway `clean()` tests report SQLSTATE
`53200` / `out of shared memory` and explicit
`max_locks_per_transaction` advice in OrganizationGreenfieldScopePersistenceTest
(shard 1), OrganizationEmployeeBirthDataMigrationTest (shard 5), and
OrganizationOperationalScopeSchemaMigrationTest (shard 7), after adding a
new exclusion-index/extension-bearing schema. These are direct runtime
failures, not grounds to reclassify or skip test cases.

**Exact registered repair message and allowlist.**
`fix(topology): restore transactional adapter proxy and PostgreSQL test lock capacity`.
MODIFY ONLY:
`src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcInstalledUnitRepositoryAdapter.java`
(remove `final` from the proxy target, retaining all `@Transactional` methods);
`src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationGreenfieldScopePersistenceTest.java`,
`src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationEmployeeBirthDataMigrationTest.java`,
`src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/OrganizationOperationalScopeSchemaMigrationTest.java`
(start their isolated PostgreSQL Testcontainers with `max_locks_per_transaction=256`);
`.github/ci-test-inventory.json` (update only actual changed test source hashes);
and this owning `doc/roadmap/TASKS_HPS-006.md` evidence record.
CREATE none. DELETE none. The isolated Testcontainers setting is **test-only
capacity**, not a deployment database setting and not an increase of test
concurrency. Preserve all seven installed-unit cases, existing Organization
assertions, ten PostgreSQL shards, exact source inventory and frozen
ROADMAP/PHASES statuses.

**Validation and remaining gate.** Source/sha256 equivalence checks, preserved
test-class case lists, guarded publication and CI startup are the local
prepublication gates. No test fixture, production migrations, authorization
fences, denial/overlap constraints, historical revision semantics or public
API are weakened. The failure cannot be marked repaired until new exact-SHA
Full/Fast/Docs CI reports are available. Keep HAPI-599 integrated workflow
NOT_IMPLEMENTED/BLOCKED, **Package A only**, and do not select Package B
or introduce a new A1/A2/D package. CI can reveal further defects; never
preclaim a green gate.




### HAPI-599 Package A — CI #948 physical dictionary ownership repair — 2026-10-28

Full #948 on `6996cfcbfc1f35f1fab5f2f79b36abe12c8d9255` failed after
Full #947 proxy/lock-capacity repair. The database dictionary renderer
reported three unmapped owner-private tables:
`public.hidra_topology_installed_unit`,
`public.hidra_topology_installed_unit_installation` and
`public.hidra_topology_installed_unit_installation_event`.
These were created by the approved additive HAPI-599 Package A migration
but omitted from reviewed `.github/database-dictionary-ownership.json`.
No evidence supports relaxing owner validation or pretending the old
`doc/database/DATA_DICTIONARY.md` is current.

**Exact repair message and file scope.**
`fix(topology): register installed-unit physical ownership and preserve catalog capture`.
MODIFY ONLY `.github/database-dictionary-ownership.json`,
`.github/workflows/ci.yml`, and `doc/roadmap/TASKS_HPS-006.md`.
Register Topology as physical owner with precise source-linked semantic
evidence for each relation. CI now captures and writes a real migrated
catalog and its complete reviewed-ownership Markdown **before** applying
the same strict `--check doc/database/DATA_DICTIONARY.md` comparison;
`if: always()` retains the actual schema/catalog artifacts even on
comparison failure. **The comparison is not disabled.** Its known
remaining gate is approved regeneration of the checked-in dictionary
using exact captured PostgreSQL metadata, not fabricated table columns.
The next `Fail` should retrieve that real artifact, verify its
relation evidence and register the reviewed generated dictionary under
the existing Package A; only then can the Full gate be expected to
pass. No new packages, tests skipped, production migration rewrites,
security/OT changes or status promotion. HAPI-599 remains blocked.



### HAPI-599 Package A — full physical dictionary regenerated from CI #949 — 2026-10-28

**Verified failure.** Exact-SHA Full #949 (main
`c9e4385904e2ec57695720d99e1bfe3e812c49a9`) failed the strict
checked-in physical dictionary comparison after the three new installed-unit
relations were given reviewed Topology ownership. Authentic API-lane
artifact #12811400207 captured source-matched PostgreSQL 16.15 metadata:
183 Flyway migrations, 470 JPA mappings, 531 relations, zero unknown
physical owners. Its generated `DATA_DICTIONARY.md` SHA-256 is
`6d457aefbbd4cff34b3e8f35a5ddf91ab19381dea19c8852afafd4ab23984443`.
Comparison with the successful #946 migrated catalog identifies exactly
three new Topology installed-unit sections, a new migration-reference mention
on existing Topology Equipment, and generation counters/source bundle
metadata. All other relation sections remain unchanged.

**Bounded repair scope and message.** Exact commit:
`docs(topology): reconcile installed-unit physical dictionary with migrated catalog`.
MODIFY only `doc/database/DATA_DICTIONARY.md` and this
`doc/roadmap/TASKS_HPS-006.md`; no added/deleted files, unchanged Flyway SQL,
JPA, authorization, CIs and test inventory. The regenerated checked-in
dictionary matches the authentic CI artifact byte-for-byte, including all
PostgreSQL constraints, triggers, indexes and source evidence. The ordinary
generator comparison is preserved; only the subsequent exact source-SHA
provenance line is exempted by its existing comparator rule.

**Other failing acceptance retained.** PostgreSQL shard 1
`ProductRevisionPublicationPostgresIntegrationTest.reactivateNeedsFreshApplicableApprovalAndExactUnitPolicy`
and shard 6
`ProductRevisionPublicationNativeHttpPostgresIntegrationTest.staleSourcesAndSubstitutedApprovalConflictWithoutPublication`
were rejected by the existing 500ms protected-work budget at #949. Neither
deadline nor the protected security decisions were altered or ignored. Full
CI acceptance remains required on the new exact commit; failing deadline
cases need a separate safe diagnosis/repair, never blanket test relaxation.
HAPI-599 remains NOT_IMPLEMENTED/BLOCKED, and A/B/C packages remain fixed.

### HAPI-599 Package A / Full950 — owner-requested500ms policy review and checked receipt repair — 2026-10-28

Owner says Fail and explicitly asks whether500ms is arbitrary and can be skipped.
Exact main0250d4d254b3677bffaaa032bec4ec6de6c3a593,
treed3a00f8295994127428efdc48668ad0376cba4f2, Full950/run40679709702 FAILED.
Docs670 passed. Eight PostgreSQL shards, unit and static pass; shards6/9 have
three positive Custody publication failures caused by aggregate work cap expiry
in SEALED phase at500.364/512.837/521.737ms. These are not native-expiry denials.
The API lane independently fails its strict evidence gate despite real dictionary
comparison success. Preserve all failed history and all installed-unit tests.

**Finding and owner policy disposition.** The exact500ms value is the server's
shared accepted writer-blocking work policy, not an industrial/HTTP SLA and not
the native authorization lifetime. HAPI-778's2026-10-25 timing-policy review already
established that distinction and adopted configurable bounded operation-family
caps; its7500ms appointment cap is based on that family's measured workload, not
proof Custody needs7500ms. No external timing specification or measured Custody
SLA justifies the exact500ms aggregate cutoff. Owner's current request authorizes
replacing that fixed cutoff for the three compiled Custody publication routes,
not disabling finite resource limits or any authorization expiry.

Use an independent immutable startup property
hidra.platform.persistence.execution.custody-work-budget-ms, default1000ms,
integer1..10000. This provisional two-times-old-cap policy covers the observed
small overrun with bounded headroom; it is not a calibrated operational SLA or
accepted load percentile. Never unlimited/zero, no request field, HTTP argument,
profile bypass, runtime mutation or public numeric execution overload. Other
families and ordinary execute retain500ms; appointment configuration remains
independent and backward-compatible. Exact three already allowlisted compiled
Custody service callers alone select the new cap. A private budget selector
follows the existing caller whitelist; no new authority or credential route.

Do NOT change native/source/token/grant/policy earliest-future bounds, activity,
DENY precedence, row/transaction fences, the final scalar database-time guard,
observed commit,250ms lock/750ms statement/1000ms idle settings or publication
storage's existing500ms evidence window. The work cap is not an authority grace
period: a final guard that observes expiry must still fail even with work time
remaining. Because storage freshness remains500ms, this change does not promise
that every operation longer than500ms will succeed. It removes a separate CPU/
round-trip cutoff; actual guarded runtime success remains a hosted CI obligation.
All183 published migrations and physical dictionary bytes remain unchanged.

**Independent API failure.** Actual schema artifact12812436825 ZIP SHA256
 afed529e2f9d959c9693ad1bc03055042c1a4aa1ccec0514cb04e08bb9dfdfd0
contains same-source verification.json with reviewed_dictionary_checked=false
from pre-comparison capture and comparison.json with true from successful strict
--check. Source/catalog/dictionary digests match; generator comparison already
passed. The sealing helper reads verification.json, so rejects the unchecked
receipt. After the strict comparison command succeeds under set-e, copy its
actual comparison.json to verification.json before artifact/seal steps. A failed
comparison must never reach that copy; retain the initial unchecked receipt for
failure diagnosis. Keep all strict source/hash/catalog/equality checks unchanged.
No regenerated or guessed physical metadata and no relaxed evidence gate.

**Registered message and scope.**
`fix(ci): separate custody work policy and retain checked dictionary receipt`.
MODIFY only ProtectedExecutionBudgetProperties, ProtectedTransactionExecutor,
their two existing Java tests, .github/ci-test-inventory.json,
.github/workflows/ci.yml, .github/scripts/test_verify_full_ci_evidence.py,
this phase and compact ULTIMATE_TASKS pointer. Zero CREATE/DELETE, migration,
JPA, DTO/controller/consumer/Identity validity or generated artifact changes.
Preserve constructors used by existing appointment callers and all old cases;
admit only actual added configuration/selector regression methods and source
hashes. Ten mandatory balanced shards and separate class JVMs remain intact.

Verify default/override independence, fail-closed malformed/out-of-range values,
exact compiled-family budget selection, standard500ms preservation, finite new
cap expiry and existing genuine native-expiry/final-guard rollback cases. Add an
API evidence regression rejecting unchecked receipts and accepting only matching
checked provenance with all other guards still active. Available local Java syntax,
focused policy probes, CI/docs self-tests, inventory/OpenAPI/whitespace and exact
scope/history checks are required; local Maven remains unavailable. Do not call
new JUnit/PostgreSQL cases passed from source review. Successful current capture
is evidence only for its original0250d4d source, not this new source SHA.

Actual local results:88 CI inventory/evidence and29 documentation/OpenAPI validator
self-tests pass (117 total); full docs/link/inventory and offline API validation
pass. All four changed Java files parse. Actual policy record compiles through
the available Java17 compiler with a temporary Environment signature, and default,
finite bounds/nanoseconds and independence probes pass; this is not Spring/JUnit.
Focused Maven command exits127 (mvn not found). Re-running the unchanged dictionary
generator on the original actual950 schema with strict --check returns true:
183 migrations/470 mappings/531 relations, zero unresolved owners; physical bytes
and original capture SHA remain unchanged. All old cases and timing provenance
are preserved. Five new JUnit cases are authored, not locally executed.

Publish only these nine MODIFY paths with expected0250d4d, verify parent/tree/
message/blobs and exact-SHA Full/Fast/Docs creation/queue/start then STOP. HAPI-599
remains NOT_IMPLEMENTED/BLOCKED pending separate Package A acceptance and remaining
B/C. Products' prior accepted behavior and statuses remain intact; operational
workload/latency qualification, releases, deployment, numerical/P3 remain separate.

### HAPI-599 Package A exact-SHA acceptance and Package B protected admission preflight — 2026-10-28

**Owner Next selection and bounded stage.** Existing HAPI-599 / HPR-P25-054A,
Package A, has cleared its actual software CI acceptance gate on exact source
`78a34c610b2986e3a73900cac28af3fca31d05fa`: Full #951/run
`40682543463`, Fast #253/run `40682543500`, and Docs #671/run
`40682543460` all completed SUCCESS. All ten PostgreSQL integration shards,
Java unit/architecture, static checks, API contract/physical dictionary and the
Full Verification Gate succeeded. The separate Maven verification matrix job
was SKIPPED by workflow design; no assertion of its execution is made. The
previous Full #950 failure and bounded Custody/dictionary repairs remain in
append-only history. Package A's owner-private domain/storage/temporal behavior
is software CI-accepted; protected use cases and external API are **not**.

The next stage is an **owner-boundary source preflight and Package B technical
registration**, not executable authorization or an HTTP endpoint. Under this
existing registered HAPI-599 A/B/C decomposition, its exact one-commit message
is `docs(topology): register protected installed-unit admission preflight`.
CREATE none; MODIFY only `doc/roadmap/TASKS_HPS-006.md`; DELETE none.
ROADMAP/PHASES states remain NOT_IMPLEMENTED/BLOCKED for the integrated HAPI-599,
and no new package, standalone task, migration or owner policy is adopted.

**Refreshed exact-source seams (above accepted SHA):**
- `topology/application/port/out/InstalledUnitRepositoryPort.java` exposes
  owner-private `register`, `record`, `close`, exact reads and history;
  `JdbcInstalledUnitRepositoryAdapter` implements those operations but is
  NOT an authorization boundary. The additive `V20261028_002` SQL enforces
  physical-unit/company identity, equipment-position FK, effective-range
  exclusion, controlled closure and event evidence. Position FK by itself does
  NOT prove the position belongs to the same company.
- `identity/application/contract/topology/TopologyProtectedAuthorizationContract`
  compiles `topology:station-revision:*` permission codes; its existing
  `TopologyScopedAccessContract.Operation` values are station-oriented.
  Reusing those codes for an installed-unit writer would expand authority
  without approval. New installed-unit-specific Identity/position operation
  admission must be reviewed before any publication.
- `party/application/contract/topology/TopologyProtectedPartyEligibilityContract`
  and `JdbcTopologyProtectedPartyEligibilityAdapter` attest ACTIVE Party
  existence/generation and guarded transaction coordinates, NOT manufacturer
  role, company ownership or equipment-kind fitness. Party-private
  `ManufacturerProfileJpaEntity` contains capability and qualification
  fields; `PartyRoleAssignmentJpaEntity` contains status and effective dates.
  Neither JPA entity may be imported by Topology application/domain.
- `ProtectedStationAdmissionCoordinator` binds existing Identity/Party/
  facility owners, uses a reviewed station-DML whitelist and seals a
  single physical transaction for station graph operations. Its opaque
  admission and final fences cannot be silently recycled as an installed-unit
  permission. `StationProtectedFacilityOwnershipContract` provides facility
  ownership evidence, not arbitrary equipment-position occupancy authority.
- `InstalledUnit` evidence UNKNOWN/UNVERIFIED/VERIFIED classification is
  provenance, not current manufacturer eligibility, model qualification,
  globally unique serial identity or a transferable owner approval.
  Optional MaintainableAsset references remain Assets-owned and neutral.

**Package B implementation admission to register separately before code:**
Review existing Party manufacturer profile/role/status/qualification semantics
and effective-date company relationship, current Identity permission and
authenticated-principal policy, equipment-position-to-facility/company binding,
and protected transaction relation-fence and SQL allowlist mechanics. Register
an exact CREATE/MODIFY/DELETE allowlist and distinct implementation commit
message after that contract review. The future bounded B deliverable must use
owner-provided, current company/manufacturer-kind/position evidence in one
authorized physical transaction before registration, close/replace, relocate
or history disclosure; preserve fail-closed DENY/INDETERMINATE, time validity,
final recheck, concurrency, rollback and immutable audit, without broadening
the station graph permission. Do not add public controller/DTO/OpenAPI (C),
manufacturer simulation qualification (HAPI-601), station incidence (HAPI-600)
or numerical/P3 scope.

**Acceptance for this preflight:** truthful exact-SHA CI evidence, actual
source-backed contract gaps, frozen statuses and owner decisions unchanged,
a one-file append-only documentation diff, guarded publication to existing main
and Documentation Validation creation/queue/start on the new SHA. No Java,
PostgreSQL, migration, production deployment or SITE validation is claimed for
this documentation-only registration. After publish observe CI startup and STOP.

<a id="hapi-600"></a>
## HAPI-600 — Effective station configuration and connectivity

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-054B

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-588, HAPI-599

Cross-Repository Dependencies: HWEB-F04, HWEB-F05 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Effective facility/station configuration membership and series/parallel installed incidence.

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent installed-unit/model/serial identity, effective station membership and series/parallel incidence with versioned links. The exact bounded remaining delivery is: Effective facility/station configuration membership and series/parallel installed incidence..

### 4. Functional Requirements

Nine distinct units across three synthetic stations, install/retire/time/conflict cases, no shared identity or dangling incidence, authorized reload/history.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/StationInstalledConfiguration.java` — pure Java owned aggregate/value and invariants for effective membership and serial/parallel incidence with nine distinct units across three synthetic stations.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/StationInstalledConfigurationUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInstalledConfigurationRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/StationInstalledConfigurationApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/StationInstalledConfigurationController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationInstalledConfigurationRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `PUT /api/v1/topology/facilities/{id}/installed-configuration` for effective membership and serial/parallel incidence with nine distinct units across three synthetic stations. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Nine distinct units across three synthetic stations, install/retire/time/conflict cases, no shared identity or dangling incidence, authorized reload/history.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-588, HAPI-599. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23395](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23395)
- [doc/model-remediation/RECONCILIATION.md:23745](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23745)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1053](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1053)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1333](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1333)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1053 | HPR-P25-054B | BLOCKED_SPECIFICATION | Effective station configuration and connectivity
doc/roadmap/ULTIMATE_ROADMAP.md:1333 | HPR-P25-054B | Effective facility/station configuration membership and series/parallel installed incidence. | Nine distinct units across three synthetic stations, install/retire/time/conflict cases, no shared identity or dangling incidence, authorized reload/history.
doc/model-remediation/RECONCILIATION.md:23395 | HPR-P25-054B | Effective facility/station configuration membership and series/parallel installed incidence. | Nine distinct units across three synthetic stations, install/retire/time/conflict cases, no shared identity or dangling incidence, authorized reload/history.
doc/model-remediation/RECONCILIATION.md:23745 | HPR-P25-054B | BLOCKED_SPECIFICATION | Effective station configuration and connectivity
~~~~~~~~

<a id="hapi-602"></a>
## HAPI-602 — Versioned installed-unit/characteristic links

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-054D

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-600, HAPI-601

Cross-Repository Dependencies: HWEB-F04, HWEB-F05 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Versioned installed-unit to characteristic links, approval and current/historical read.

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent installed-unit/model/serial identity, effective station membership and series/parallel incidence with versioned links. The exact bounded remaining delivery is: Versioned installed-unit to characteristic links, approval and current/historical read..

### 4. Functional Requirements

Pin exact owner revisions/digests, changed source cannot mutate old station/baseline content, cross-owner failure rolls back; no implicit simulator qualification.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/InstalledCharacteristicLink.java` — pure Java owned aggregate/value and invariants for exact approved source revision/digest binding with effective selection and old-history preservation.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/InstalledCharacteristicLinkUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/InstalledCharacteristicLinkRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/InstalledCharacteristicLinkApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/InstalledCharacteristicLinkController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcInstalledCharacteristicLinkRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/topology/installed-units/{id}/characteristic-links` for exact approved source revision/digest binding with effective selection and old-history preservation. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Pin exact owner revisions/digests, changed source cannot mutate old station/baseline content, cross-owner failure rolls back; no implicit simulator qualification.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-600, HAPI-601. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23397](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23397)
- [doc/model-remediation/RECONCILIATION.md:23747](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23747)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1055](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1055)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1335](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1335)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1055 | HPR-P25-054D | BLOCKED_SPECIFICATION | Versioned installed-unit/characteristic links
doc/roadmap/ULTIMATE_ROADMAP.md:1335 | HPR-P25-054D | Versioned installed-unit to characteristic links, approval and current/historical read. | Pin exact owner revisions/digests, changed source cannot mutate old station/baseline content, cross-owner failure rolls back; no implicit simulator qualification.
doc/model-remediation/RECONCILIATION.md:23397 | HPR-P25-054D | Versioned installed-unit to characteristic links, approval and current/historical read. | Pin exact owner revisions/digests, changed source cannot mutate old station/baseline content, cross-owner failure rolls back; no implicit simulator qualification.
doc/model-remediation/RECONCILIATION.md:23747 | HPR-P25-054D | BLOCKED_SPECIFICATION | Versioned installed-unit/characteristic links
~~~~~~~~

<a id="hapi-603"></a>
## HAPI-603 — Installed-equipment consumer qualification

Phase: HPS-006 — Stations and Equipment

Legacy IDs: HPR-P25-054E

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-602

Cross-Repository Dependencies: HWEB-F04, HWEB-F05 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Owner/API/security/compatibility and consumer-ready qualification.

### 2. Existing Implementation

Topology Equipment and station graph revisions exist; Assets has independent maintainable-asset creation and concurrency-protected update. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent installed-unit/model/serial identity, effective station membership and series/parallel incidence with versioned links. The exact bounded remaining delivery is: Owner/API/security/compatibility and consumer-ready qualification..

### 4. Functional Requirements

Actual save/read/revise across all owners, nine independent unit/source fixtures, malformed maps, denial, race/idempotency/rollback and old-format decode; exact accepted contracts for F04/F05.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. Do not merge manufacturer model, installed unit, station configuration and maintainable asset aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/topology/qualification/InstalledEquipmentOperatorQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/application/service/StationRevisionOwnerQueryServiceTest.java` — exact-head oracle suite 42 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Actual save/read/revise across all owners, nine independent unit/source fixtures, malformed maps, denial, race/idempotency/rollback and old-format decode; exact accepted contracts for F04/F05.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-602. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23398](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23398)
- [doc/model-remediation/RECONCILIATION.md:23748](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23748)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1056](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1056)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1336](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1336)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1056 | HPR-P25-054E | BLOCKED_SPECIFICATION | Installed-equipment consumer qualification
doc/roadmap/ULTIMATE_ROADMAP.md:1336 | HPR-P25-054E | Owner/API/security/compatibility and consumer-ready qualification. | Actual save/read/revise across all owners, nine independent unit/source fixtures, malformed maps, denial, race/idempotency/rollback and old-format decode; exact accepted contracts for F04/F05.
doc/model-remediation/RECONCILIATION.md:23398 | HPR-P25-054E | Owner/API/security/compatibility and consumer-ready qualification. | Actual save/read/revise across all owners, nine independent unit/source fixtures, malformed maps, denial, race/idempotency/rollback and old-format decode; exact accepted contracts for F04/F05.
doc/model-remediation/RECONCILIATION.md:23748 | HPR-P25-054E | BLOCKED_SPECIFICATION | Installed-equipment consumer qualification
~~~~~~~~

<!-- task-register:payload:end -->
