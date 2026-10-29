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

### HAPI-599 Package B — register Party manufacturer evidence implementation scope — 2026-10-28

**Stage selection and accepted prerequisite.** Owner `Next` follows the HAPI-599
Package B protected admission preflight, source
`5e55894be30d97f5b1e216b67aac267a6fd1cb19`, Documentation Validation
#672/run `40684086531` SUCCESS. Previous runtime parent
`78a34c610b2986e3a73900cac28af3fca31d05fa` retains Full #951,
Fast #253 and Docs #671 SUCCESS. This stage registers **one first
implementation commit within the fixed Package B**, not Package D, a new
HAPI task or a second implementation in this `Next`. Its exact registration
commit message is
`docs(topology): register installed-unit manufacturer evidence implementation`;
CREATE none; MODIFY only `doc/roadmap/TASKS_HPS-006.md`; DELETE none.
No permission, source, migration, identity, API or frozen task status changes.

**Party source facts confirmed at this SHA.**
- `ManufacturerProfileJpaEntity` maps `hidra_party_manufacturer_profile`
  with `party_id`, `capability_type` (`EQUIPMENT`, `INSTRUMENTATION`,
  `SPARES`, `MATERIALS`, `SOFTWARE`, `OTHER`) and
  `qualification_status` (`QUALIFIED`, `CONDITIONALLY_QUALIFIED`,
  `SUSPENDED`, `EXPIRED`, `REJECTED`, `DRAFT`). The catalog
  is not a manufacturer-to-specific-equipment-kind mapping.
- `PartyRoleJpaEntity` supplies role `code`, `status` and
  `qualification_required_by_default`; `PartyRoleAssignmentJpaEntity`
  supplies `party_id`, `role_id`, `status`, effective validity bounds
  and `qualification_required`. No hard-coded globally trusted
  manufacturer-role ID or current role grant is established by these Java
  mappings alone.
- `JdbcTopologyProtectedPartyEligibilityAdapter` currently exports
  protected current ACTIVE Party/generation evidence, not manufacturer
  capability/role/qualification. Its issuer-bound, per-transaction,
  NOWAIT relation-guard approach is relevant, but its tickets, fences
  and business meaning cannot be widened by casting.
- `PartyRelationshipType` contains PARENT/SUBSIDIARY/AFFILIATE/
  JOINT_VENTURE/PARTNER/SUCCESSOR/OTHER; this does **not** prove a
  manufacturer is owned by or must equal the installation company.
  Manufacturer Party identity and installed-unit `companyPartyId` are
  distinct; company permission/position ownership are independent
  later Package B gates.
- `TopologyScopedAccessContract` and
  `TopologyProtectedAuthorizationContract` issue station-revision
  permission family only; `ProtectedTopologyAuthorizationParticipant`
  binds an authenticated principal and real Identity claims under
  MANDATORY transaction. No installed-unit operation authorization
  exists. `EquipmentJpaEntity` has nullable `facility_id`;
  an equipment FK alone neither establishes facility nor company scope.

**Exact next executable bounded Package B owner-evidence step — registered,
NOT EXECUTED.** Message:
`feat(party): export guarded installed-unit manufacturer evidence`.

CREATE only:
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyInstalledUnitManufacturerEvidenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/persistence/TopologyInstalledUnitManufacturerEvidencePostgresIntegrationTest.java`

MODIFY only:
- `.github/ci-test-inventory.json` (new actual class/cases/hash, baseline time
  unmeasured if not observed)
- `doc/roadmap/TASKS_HPS-006.md` (append actual validation and acceptance)

DELETE none. No migration/JPA entity, Identity evaluator, Topology writer,
existing owner admission, station-revision permission codes, public REST,
OpenAPI, Assets, or frozen ROADMAP/PHASES edits.

**Required limited contract.** Provide an exported Party-owned, query-only
opaque issuer/transaction-bound read with a typed evidence result recording
actual manufacturer Party status, role/code and assignment effective
applicability, manufacturer capability and qualification classification,
and authoritative same-transaction coordinates. The contract is for a future
trusted Topology coordinator only, **not** a public permission, anonymous
existence lookup, bearer attestation, or universal positive eligibility
Boolean. Never accept caller-supplied `QUALIFIED` or `VERIFIED` labels
as proof. Require a genuine active physical transaction, immutable
current-source reads and reviewed Party relation locks/fences (including
status, role assignment, manufacturer profile), deny on absent,
revoked/suspended/expired, ambiguous or malformed evidence; fail closed on
SQL/owner-fence changes or unauthorized direct use. No request-specific
numeric bypass, unlocked stale cache, or soft-fail on missing rows.

For known profiles, expose the exact manufacturer capability; do not
claim compatibility with `InstalledUnit.equipmentKindId` without a
provider-owned reviewed mapping (currently absent). Keep UNKNOWN and
UNVERIFIED engineering provenance distinct from Party eligibility. Do
not impose manufacturer==installation-company; Party relationship
alone is not company authorization. The follow-on **same Package B**
protected Identity permission family, equipment-position/company binding,
atomic admission/DML allowlist and read/history denial must be
separately registered from exact code after this owner evidence gate passes.
No Package C operation, numerical engine, industrial calibration or OT control.

**Synthetic acceptance for the registered implementation:** current
active/qualified and inactive/suspended/revoked role/profile classification,
missing Party/profile, non-manufacturer role, dates at interval boundaries,
capability category mismatch unresolved (never auto-qualified), duplicate
or inconsistent role/profile evidence, admission re-use, cross-thread or
cross-transaction ticket rejection, NOWAIT conflicts, rollback, tampering
and final fence provenance. Confirm existing station owner contracts, Party
aggregate interfaces and direct access denial stay unchanged. Run focused
PostgreSQL Testcontainers from fresh Flyway schema, current architecture
and registered CI inventory, exact-source Full/Fast/Docs; if local Maven is
unavailable record that limitation. Do not claim these tests exist or pass
before the implementation commit and its hosted CI.

**Remaining HAPI-599 status.** Package A is accepted in development CI;
Package B is NOT yet executable and Package C is unstarted. Integrated
HAPI-599 remains NOT_IMPLEMENTED/BLOCKED under the frozen registry. This
registration only establishes a checkable next file scope and acceptance,
not authorization, engineering qualification, SITE or deployment.
After guarded publication observe exact-SHA Documentation Validation
creation/queue/start then STOP.

### HAPI-599 Package B — Party manufacturer evidence implementation — 2026-10-28

**Selected bounded execution.** Documentation Validation #673/run
`40684603102` on `945a2235b0bb3f2f95f73e5b75e342a60ccc13e2`
completed SUCCESS. Prior runtime Full #951/Fast #253/Docs #671
on `78a34c610b2986e3a73900cac28af3fca31d05fa` are SUCCESS.
Owner `Next` selects the previously registered **first executable step within
the same Package B**, exact message:
`feat(party): export guarded installed-unit manufacturer evidence`.

**Exact registered scope, no expansion.** CREATE only:
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyInstalledUnitManufacturerEvidenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/persistence/TopologyInstalledUnitManufacturerEvidencePostgresIntegrationTest.java`

MODIFY only `.github/ci-test-inventory.json` (one PostgreSQL class,
11 actual methods, source SHA256
`cabd02a05e5a315d1878422b0a17a8b19fc25372643ebdef03590094e20a4077`,
unmeasured baseline_seconds=0) and this append-only task record.
DELETE none. No Flyway, JPA, Identity, Topology authorizer/writer,
controller/API, permissions, schema catalog, full-CI workflow, frozen
ROADMAP/PHASES or unrelated class changes.

**Owner contribution only.** The new Party application contract defines an
opaque single-use Admission and scalar Contribution (Party identity/current
generation, manufacturer role/assignment, half-open effective validity,
qualification requirement, capability/qualification class, database-clock
sample and backend/transaction coordinates) plus required relation fences.
The non-final Spring JDBC adapter requires an active read-committed,
writable physical transaction, current Party status and generation, one
ACTIVE role code `MANUFACTURER`, effective ACTIVE assignment and exactly
one QUALIFIED manufacturer profile classified EQUIPMENT/INSTRUMENTATION.
The code is a **lexical role evidence filter**, not a deployed role-ID
authorization or a proof of a specific equipment-kind map. UNKNOWN,
unverified, nonqualified/conditional, duplicate, nonmatching or stale
evidence does not qualify a unit, model, or human operation.

Five Party-owned relations are locked in a fixed NOWAIT order; catalog
owner/privilege, relation type/RLS and physical locks are checked, and
five typed final fences are exposed. The reader's database principal must
have no direct write/REFERENCES/TRIGGER privilege on manufacturer profile,
role or assignment, including column-level grants; any added grant fails
closed. The synthetic runtime role can update Party status only under its
existing generation-guarded owner record, but manufacturer/role/assignment
evidence is SELECT-only. A separate schema owner performs concurrent
writer tests without laundering mutation through the evidence reader. A private ticket is issuer, connection,
transaction, backend, database-role, thread and completion bound.
Failures mark the caller's physical transaction rollback-only.
No company ownership or registered installed-unit permission is asserted.
The existing station protected caller, Party reference contracts and
Assets interfaces remain unchanged. Do not extrapolate manufacturer-role
evidence to installed-unit authorization or live industrial qualification.

**Authored synthetic tests, not local pass claims.** The new PostgreSQL
Testcontainers class has 11 exact tests for positive identity/coordinates,
absent/inactive/missing owner, revoked role, disallowed qualifications/
capabilities, effective-time boundaries, duplicate profiles/assignments,
forged/reused/stale tickets, cross-thread/suspension isolation,
NOWAIT writer conflict, relation fences/RLS/privilege changes and swallowed
failure rollback. It uses fresh Flyway PostgreSQL16 and a synthetic
restricted role with no application/production data. CI inventory entry
is inserted in class sort order; the previous 495 class records and ten
shards are preserved byte-for-byte. Source SHA256 was calculated and
cross-checked against an existing registered source/hash example.

**Validation/limits.** Reviewed actual source, paths, enum spellings,
relation names, JPA field columns, installed-unit owner limitations and
the registered file scope. Existing inventory JSON is parseable and
identical except the new entry; eleven actual `@Test` methods were matched.
Local Java 21 (`javac 21.0.12.1`) is present, but Maven and Docker
are unavailable, so new Java/JUnit and PostgreSQL tests have NOT been
executed locally. Static review and synthetic test authorship are not
runtime acceptance. Exact-SHA hosted Full/Fast/Docs and actual PostgreSQL
acceptance remain required; no CI state is preclaimed. The future separate
same-Package-B Identity/position/transaction writer stage must be
registered from source and accepted; Package C is not selected.
Integrated HAPI-599 remains NOT_IMPLEMENTED/BLOCKED in frozen tables.
No SITE, simulator, release or deployment scope.

### HAPI-599 Package B — Full CI #952 PostgreSQL lock-privilege repair registration — 2026-10-28

**Current gate and source-specific failure.** Owner `Fail` selects a single
documentation-only technical repair registration, not further HAPI-599 or
HAPI-600 implementation. Exact main
`5e67781e926f5b96658ce4a67f0c5764ebaf2f65`: Documentation Validation
#674 and Fast Feedback #254 SUCCESS; Full CI #952/run `40686331829`
FAILURE. Static, unit/architecture, API/dictionary and nine PostgreSQL shards
succeeded; PostgreSQL shard 7/job `122809507701` FAILED. Its new Party
manufacturer evidence class reports 11 tests, 1 assertion failure and 7
errors. PostgreSQL reports SQLSTATE `42501` / permission denied for
`hidra_party_role` on the four-table
`LOCK TABLE ... SHARE ROW EXCLUSIVE MODE NOWAIT` in
`JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.acquire`.
One test expected NOWAIT SQLSTATE `55P03` but received permission denial
`42501`. Full Verification Gate failed because the required PostgreSQL
dependency failed. This is confirmed hosted runtime evidence, not a flaky
rerun or an inventory/classification defect.

**Actual PostgreSQL 16 privilege conflict.** That lock mode requires
UPDATE/DELETE/TRUNCATE authority (or table ownership/superuser), which
contradicts the intentionally SELECT-only Party role/assignment/manufacturer
profile reader. PostgreSQL 16 `LOCK` documentation:
https://www.postgresql.org/docs/16/sql-lock.html . Neither weakening the
conflicting lock to ACCESS SHARE, granting application DML authority,
disabling/reclassifying tests, nor changing the assertion to expect `42501`
preserves both current evidence and the denial invariants. The previously
registered `feat(party)` stage forbade migrations; this new required
database-security dependency is **not** silently admitted by that allowlist.

**This one selected registration commit.** Exact message:
`docs(topology): register Party manufacturer evidence lock privilege repair`.
CREATE none; MODIFY only `doc/roadmap/TASKS_HPS-006.md`; DELETE none.
No Java/SQL/permissions/tests/CI/workflow/roadmap status changes are
authorized by this documentation step.

**Proposed bounded corrective implementation, PENDING separate admission.**
Candidate exact message:
`fix(party): retain read-only manufacturer evidence through guarded owner locks`.
Candidate CREATE only:
`src/main/resources/db/migration/V20261028_003__party_installed_unit_manufacturer_evidence_owner_lock.sql`.
Candidate MODIFY only:
`src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.java`,
`src/test/java/dz/sh/hidra/modules/party/infrastructure/persistence/TopologyInstalledUnitManufacturerEvidencePostgresIntegrationTest.java`,
`.github/ci-test-inventory.json` (actual modified source SHA256/cases),
and `doc/roadmap/TASKS_HPS-006.md` (actual evidence). DELETE none.
The version is only a candidate based on the source-confirmed migration tail
`V20261028_002`; reverify uniqueness on the future exact main.

**Required safe technical design before implementation.** A narrowly
allowlisted Party-owned, fixed-SQL, parameterless SECURITY DEFINER
lock-only function is one candidate to acquire genuine NOWAIT
SHARE ROW EXCLUSIVE relation locks on the exact four current Party
source tables under the **same caller physical transaction**, preserving
the separate eligibility-generation ACCESS SHARE lock. It must use a
fixed trusted search_path, explicitly schema-qualified relations,
protected non-superuser owner, immutable reviewed body/ownership,
no dynamic SQL, and explicit `REVOKE EXECUTE FROM PUBLIC` with grant only
to an approved, non-owner evidence reader principal. Do not invent a
production database role or grant in the migration. The adapter must
validate function catalog, owner, EXECUTE scope and actual retained
per-relation locks/fences and fail closed on alteration, grant forgery,
unsupported database roles, cross-transaction reuse or missing helper.
SECURITY DEFINER is not authorization or manufacturer-kind qualification;
privilege escalation, metadata disclosure and availability/DoS risks
require focused negative tests and review. Reject any implementation that
needs DML grants on role, assignment or profile.

**Acceptance for future corrective stage.** Fresh Flyway PostgreSQL16
schema/owner-runtime role separation, no direct DML/column permissions
for the evidence reader, positive manufacturer classification, revoked/
stale/ambiguous/absent denial, concurrent writer-first `55P03`, reader-first
blocked writers until commit, correct five fences, catalog/tampering,
rollback, no PUBLIC EXECUTE or unreviewed owner privilege, all 11
registered tests retained and actual inventory/hash fidelity. Preserve
prior migration bytes, ten CI shards, current Identity/Assets/Topology
contracts and all unrelated operations. A new Flyway version may
affect dictionary evidence: if a strict physical dictionary gate requires
a catalog-derived update, register its exact scope separately rather
than fabricating it or disabling the check. Exact-SHA Full/Fast/Docs
execution must establish acceptance. At this documentation stage
HAPI-599 remains NOT_IMPLEMENTED/BLOCKED; Package B owner evidence
is NOT CI-accepted and Packages B authorization/C are not selected.
Do not claim code fix, PostgreSQL pass, SITE or deployment.

### HAPI-599 Package B — dictionary-dependent lock helper admission — 2026-10-28

**Owner Next; bounded source-backed acceptance prerequisite.** Documentation
Validation #675 / run `40687958071` completed SUCCESS on
`296fff877f6741071783872f0066b6513595d311`. Full CI #952 remains
FAILED on the prior implementation source
`5e67781e926f5b96658ce4a67f0c5764ebaf2f65`, solely on the
registered manufacturer-evidence PostgreSQL shard 7 and therefore final
verification. The exact error is `42501` / permission denied on the
`SHARE ROW EXCLUSIVE` lock attempted by a SELECT-only Party reader.
Changing the expected SQLSTATE, widening ordinary DML, skipping the new
class or weakening the write-conflicting lock is not an accepted repair.

**Additional discovered, authoritative dependency:** Current
`.github/workflows/ci.yml` (API contract/dictionary stage) calls
`.github/scripts/generate_data_dictionary.py` against a genuine
fresh-migrated PostgreSQL catalog, with mandatory
`--check doc/database/DATA_DICTIONARY.md`. The generator's source bundle
includes the exact Flyway migration inventory and its output includes the
migration count and relation-specific migration mentions. Its only
comparison exception is the independently verified capture source-SHA
line. Therefore adding even a helper-only migration
`V20261028_003__party_installed_unit_manufacturer_evidence_owner_lock.sql`
changes checked dictionary content; leaving the current checked
`doc/database/DATA_DICTIONARY.md` unmodified is a **predictable API
contract CI failure**. This is not a reason to disable the gate or
fabricate a catalog. The checked document presently exists and is
approximately 3.5 MB; the current local runtime has neither a full
migrated PostgreSQL catalog nor Maven/Docker. A catalog-derived output
must be captured using the same reviewed generator, owner metadata,
real source and fresh schema, not synthesized from prose.

**This single Next stage — precise registry clarification only.**
Commit message:
`docs(topology): admit physical dictionary gate for Party owner lock repair`.
CREATE none; MODIFY only `doc/roadmap/TASKS_HPS-006.md`; DELETE none.
Preserve all frozen task/status records, migrated SQL, tested code and
the previous append-only evidence. The owner-approved HAPI-599 A/B/C
split remains unchanged.

**Exact additional allowed file for the future corrective implementation:**
MODIFY `doc/database/DATA_DICTIONARY.md`, but **only with actual
catalog-derived regenerated content** from the same versioned
`.github/scripts/generate_data_dictionary.py`, checked against fresh
Flyway PostgreSQL. The future bounded repair retains its previously
registered single CREATE SQL migration and three MODIFY Java/test/
inventory files, plus this task evidence; its amended exact
implementation message remains
`fix(party): retain read-only manufacturer evidence through guarded owner locks`.
Do not modify the dictionary generator, its strict comparison, ownership
metadata, CI workflows, earlier migrations, or unrelated dictionary
content to force a green check. Do not claim a reviewed/current
dictionary until the real generator completes. If exact-source
catalog collection cannot be performed without a forbidden temporary
branch, unregistered workflow or false source SHA, report the blocker
and stop, rather than publishing deliberately failing source.

**Security/acceptance constraints retained:** The proposed helper is
SECURITY DEFINER, fixed SQL, schema-qualified, NOWAIT and lock-only
under one physical transaction, with an explicitly reviewed,
non-superuser owner and no PUBLIC EXECUTE or invented production
roles/grants. Its restricted application consumer retains SELECT-only
role/assignment/profile permissions, tested catalog/owner/body
integrity, existing tickets, rollback and five final fences. A helper
owner requiring actual relation-lock privilege is a migration/runbook
provisioning consideration; application DML grants cannot substitute.
The existing 11 test cases, ten PostgreSQL shards, strict physical
dictionary and immutable audit/Flyway history stay mandatory.

**State:** Registration only; **no SQL, Java or dictionary file has yet
been changed** by this stage. The old Full #952 failure remains
unrepaired. Package B is not CI-accepted and Package C remains
unstarted. No numerical solver, production, OT or SITE acceptance
is inferred. After guarded documentation publication, observe
Documentation Validation creation/queue/start on its exact SHA, then
STOP before any later implementation.

### HAPI-599 Package B — owner-authorized two-stage CI-first lock repair — 2026-10-28

**Explicit owner authorization supersedes the publication hold prospectively.**
Owner: “Proceed with the two-stage CI-first repair and update the publication hold accordingly”.
Exact mainb84f81b4120ac44cdceb5805a36ae2662b2f323d,
tree2e6fbfda7439c76ff6f07352aacb77c515e22865 has Docs676/run40688753886
completed/success; Full952/run40686331829 remains failed on prior5e67781e.
The previous requirement to stop before publishing without a local migrated catalog
is superseded only for these two responsible repair stages. All strict physical
catalog/source/equality, security and acceptance requirements remain mandatory.
Do not provision a complete local environment or invent a schema capture/source SHA.

**Stage one selected now.** Exact message remains
`fix(party): retain read-only manufacturer evidence through guarded owner locks`.
CREATE only V20261028_003__party_installed_unit_manufacturer_evidence_owner_lock.sql;
MODIFY only JdbcTopologyInstalledUnitManufacturerEvidenceAdapter,
TopologyInstalledUnitManufacturerEvidencePostgresIntegrationTest,
.github/ci-test-inventory.json and this phase. Zero DELETE. Preserve the current
actual doc/database/DATA_DICTIONARY.md byte-for-byte until real hosted collection.
No workflow, generator, ownership metadata, public contract or frozen status change.
Expect the strict dictionary comparison to reject the newly migrated source until
stage two; retain that failed result and real same-run schema/render artifacts.
The expected artifact gate is not software acceptance or a reason to suppress tests.

**Lock-only implementation.** The new parameterless, fixed PL/pgSQL SECURITY DEFINER
function public.hidra_party_lock_installed_unit_manufacturer_evidence() locks only
Party, role, assignment and manufacturer profile in the original order with SHARE
ROW EXCLUSIVE NOWAIT. It reads/writes no rows, has no dynamic SQL, returns void,
uses trusted search_path=pg_catalog,pg_temp and remains within the caller transaction.
Flyway transactionally creates it and revokes PUBLIC EXECUTE. It creates no role,
production principal, membership, table grant or reader EXECUTE grant.
Migration-owner privileges alone are not an accepted runtime configuration.

**Required administrative provisioning before enabling an approved reader.**
Use a reviewed, dedicated NOLOGIN/NOINHERIT non-superuser helper owner with no role
memberships, CREATEROLE/CREATEDB/REPLICATION/BYPASSRLS authority or schema CREATE.
It must not own the source tables; grant UPDATE without grant option on only the
four fixed lock relations, the privilege required to acquire their chosen lock mode.
Transfer function ownership through the schema administrator, revoke temporary
schema CREATE used for ownership transfer, and explicitly grant non-grantable
EXECUTE to the approved non-owner reader. Do not grant that reader application DML,
column write privileges, helper-owner membership or PUBLIC EXECUTE. Test role names
are synthetic fixtures only; actual production role identity is not invented here.
Until this provisioning is satisfied the adapter fails closed, including against a
superuser migration owner. This is not operator/company authorization or a deployed
manufacturer/equipment-kind qualification.

The adapter checks original relation/privilege/RLS/inheritance guards before calling
the helper; additionally checks its exact namespace/signature, void/volatile/unsafe
PLpgSQL/security-definer shape, no strict/leakproof/default/variadic arguments,
fixed config and SHA256 of prosrc:
81b4ac0abd37fca0f0b539e59ac5d0ea1a3c21217209be362248275bd7f15da1.
It requires dedicated owner privileges, no runtime owner membership, and an explicit
ACL limited to the owner plus current reader, with no PUBLIC/extra/grantable reader
EXECUTE. It locks eligibility generation separately in ACCESS SHARE NOWAIT using
the reader's ordinary SELECT privilege. Recheck helper catalog, original guards,
all five actual fences and physical ticket before disclosure and final fences;
rejection poisons the physical transaction. Existing issuer/thread/connection/XID/
role/suspension/single-use/half-open-time/ambiguity and classification semantics remain.

**Tests and evidence scope.** All11 prior cases retained; five actual new cases cover
migration PUBLIC revoke plus explicit reader grant, owner-first blocked writer and
post-commit release with same XID/current role, altered body/security/search path/
superuser owner, PUBLIC execution/helper-owner membership/column-write forgery,
and missing helper-owner lock privilege. They preserve original42501 DML denials,
writer-first55P03, rollback and retained five fences. The test schema owner provisions
a separate synthetic helper owner; no runtime DML privileges are widened. Sixteen
methods are admitted with their actual source SHA; old entries/cases/timings and ten
shards are preserved. No passing PostgreSQL result is claimed before hosted execution.

Useful local source/syntax/header/scope/immutable-history, inventory, docs/offline
API and CI/document-validator self-tests must pass before guarded publication.
Maven/Docker/PostgreSQL are unavailable locally and are not provisioned; syntax or
catalog predicate review is not native execution. Keep all183 older migrations and
current dictionary unchanged. Publish with expectedb84f81b, verify parent/tree/message/
five-path blobs, confirm exact-SHA Full/Fast/Docs creation/queue/start, then STOP under
AGENTS11.1. New function/privilege behavior and16 raw cases must still pass in CI.

Actual prepublication checks:117 unchanged CI/docs validator self-tests pass,
source inventory and full documentation/link/offline API checks pass, and both
changed Java files parse with the available compiler. Focused Maven execution
exits127 (mvn not found). All183 prior migration Git blobs and physical dictionary
bytes are unchanged; helper prosrc hash matches the new migration body. No new
JUnit/PostgreSQL execution or successful physical catalog capture is claimed.

**Stage two is already authorized, not another permission question.** After CI
retains a genuine exact-stage-one migrated catalog, verify ZIP/source/run/attempt,
complete migration/mapping/relation/owner metadata and generator provenance. Run the
unchanged generator to regenerate doc/database/DATA_DICTIONARY.md. Exact follow-up
message: `docs(party): refresh manufacturer owner-lock physical dictionary`.
MODIFY only that dictionary and this phase; no generator/workflow/ownership/migration
or test waiver. Verify exact equality and publish guarded main, then observe applicable
exact-SHA CI/start and stop. If capture is absent because migration/tests fail, repair
the responsible cause within the registered scope first; never synthesize a dictionary.
Any documentation-only Full dispatch limitation must be reported honestly.

Package B evidence remains unaccepted pending real owner/lock/catalog/full proof.
HAPI-599 remains NOT_IMPLEMENTED/BLOCKED; no integrated permission/writer or Package C,
new package, simulator, SITE, release or deployment is selected. A green repair still
requires independent evidence review before the separately registered next B scope.

### HAPI-599 Package B — stage-two physical dictionary repair evidence — 2026-10-28

Full #953 run `40692766055`, attempt `1`, exact source
`846edc515748b07df8aaf7c4cc006dff5ee45ee4`: unit/architecture, static validation
and all ten PostgreSQL shards passed. The API/dictionary lane failed on the retained
strict physical dictionary comparison; its missing successful lane receipt then
blocked the Full Verification Gate. This is the expressly anticipated stage-one
dictionary drift, not successful full acceptance. No check or case is skipped.

Genuine same-run schema artifact `12817353666` has verified ZIP SHA-256
`71f4bb808960a7da2486bfff63e512d133d027f1390ad17d149f2c99bb526ed8`.
The unchanged generator validates its exact source inventory/bundle against current
migration/JPA files: 184 migrations, 470 mappings, 531 relations, zero unresolved
owners; PostgreSQL `16.15 (Debian 16.15-1.pgdg13+2)`.
Source bundle SHA-256 is `b224960330372f46f4a2c6012a1473d779188092314b5f393125682eb330097d`;
catalog SHA-256 is `c6afd99019d3d6e2e777218a4a09fb1e5b48e7304392e7582054321160b9c245`.
The regenerated Markdown matches the retained CI render byte-for-byte, SHA-256
`a7e5f5a85ef2f5b981f105206e3c421c3dce2a17b647033467feefe1723225bd`.
Reviewed changes are capture provenance/count and the new migration mention under
its four Party relations; relation metadata otherwise remains unchanged.

Stage two updates only the dictionary and this phase using its registered message.
Local strict generator comparison passed (`reviewed_dictionary_checked=true`);
documentation validation, 18 dictionary generator self-tests and `git diff --check`
passed before publication. No local PostgreSQL execution is claimed. The strict CI gate remains
unchanged; a new exact-head Full result is still required. This documentation-only
scope does not trigger Full automatically, and the connected GitHub tools expose no
workflow-dispatch operation. After publication, manually run HidraAPI CI on `main`
with development mode, `serial_oracle=false`, normal cached dependencies.

The earlier prepublication hold is superseded only by the authorized two-stage
CI-first repair above. Publication of this truthful dictionary refresh is permitted;
Package B acceptance, HAPI-599 NOT_IMPLEMENTED/BLOCKED, integrated writer/permission
scope and release/deployment holds remain open pending exact-head full verification
and independent evidence review. No successor implementation is selected.

### HAPI-599 Package B — manufacturer evidence acceptance and protected writer preflight — 2026-10-28

**Owner Next; exact-head independent evidence review.** Main
`7c49a82d2de482b4305d29a3c1087bd818bc92fb` has Full #954/run
`40695042943`, attempt 1, completed SUCCESS (manual development dispatch,
serial_oracle=false, cached dependencies), and Docs #678/run `40694913299`
SUCCESS. No Fast workflow was created for this documentation-only head.
All ten PostgreSQL shards, unit/architecture, static validation, API/dictionary
and Full Verification Gate passed. The separate serial Maven/oracle job was
skipped by design; this is development software evidence, not release eligibility.

The retained full bundle, artifact `12818888453`, was downloaded and its ZIP
SHA-256 matched GitHub: `f62369b6b9922496923bef30f4b4907611323804ad08c895d18a7f86501c9f5d`.
Independently checked all 499 evidence-listed file hashes, exact SHA/run/attempt,
and reconstructed all raw XML reports against the current active inventory:
492 unique classes, 3,610 exact cases, zero failures/errors/skips. Four explicitly
archived greenfield classes remain excluded by existing policy, not newly waived.
The Party manufacturer evidence class ran all 16 registered cases successfully
in PostgreSQL shard 7, including all five owner-lock repair cases. Its observed
suite time was 11.266 seconds; the scheduling inventory is not rewritten from
this single observation. No new local JUnit/PostgreSQL run is claimed.

Same-run dictionary verification is checked=true, source SHA above, PostgreSQL
16.15, 184 migrations, 470 mappings, 531 relations and no unresolved owners.
Source bundle SHA-256 remains `b224960330372f46f4a2c6012a1473d779188092314b5f393125682eb330097d`;
new same-run catalog SHA-256 is `4afcab111de66a0c8c663ce4a947fc4948bae878ce2ad176e59838809f3e98df`.
This capture is distinct from Full953; its checked dictionary SHA-256 is
`4506748561ca6b7f0a5b66c3a3917e69d835bf3582a65055e07b954cbde6db3d`.
API runtime and base compatibility evidence passed against base
`78a34c610b2986e3a73900cac28af3fca31d05fa`; current API SHA-256
`802756624b23eedbf939f137962737a2e0fa5580a8757d866ff6c4800925cc22`.

**Bounded acceptance and hold disposition.** Package A remains accepted. The
first Party-owned contribution within Package B, including its guarded owner-lock
repair and truthful physical dictionary refresh, is now accepted for the tested
development software scope. Both stages of the CI-first repair are complete;
their pending exact-head full-verification hold is resolved by Full954. Preserve
Full952's privilege failure and Full953's dictionary failure as historical evidence.
Do not repeat these implementations. This does not accept the remaining Package B
Identity/position/transaction writer, Package C, manufacturer-to-equipment-kind
qualification, deployment role provisioning, engineering/SITE or production use.
HAPI-599 remains NOT_IMPLEMENTED/BLOCKED in frozen ROADMAP/PHASES.

**This selected one-file acceptance/registration stage.** Exact commit message:
`docs(topology): accept manufacturer evidence and register protected writer preflight`.
CREATE none; MODIFY only `doc/roadmap/TASKS_HPS-006.md`; DELETE none.
Local docs/link validation and diff checks precede guarded publication; observe
new exact-SHA Docs creation/start and stop. No runtime/source/status changes.

**Remaining source seams revalidated on the accepted head.**
- `TopologyProtectedAuthorizationContract.permissionCode` and
  `TopologyScopedAccessContract.Operation` encode only station-revision actions.
  `ProtectedOperationIdentityParticipant.qualifyPair` derives that exact family;
  `JdbcProtectedAuthorizationEvidenceReader` validates the station family. A new
  caller cannot relabel station authority as installed-unit authority.
- `ProtectedTopologyAuthorizationParticipant` uses actual authenticated principal,
  opaque preparation/admission, native Identity evidence and company/facility
  decisions under real transactions; it is a source pattern, not an installed-unit
  issuer. A constructible contribution or Boolean is not a grant.
- `EquipmentJpaEntity.facilityId` is nullable, and node/pipeline-segment references
  also exist. An equipment ID/FK does not prove a company-owned position. The
  station facility-owner contract proves facility ownership only. Unresolved or
  contradictory ownership must fail closed; no invented node/segment traversal.
- `InstalledUnitRepositoryPort` exposes private register/record/close/find/history
  storage. It explicitly requires protected orchestration and atomic close plus
  replacement. `ProtectedStationAdmissionCoordinator` binds a station request,
  station owners/fences and executor; copying its ticket does not authorize this
  repository or a relocation across two facilities.

**Next separately executable same-Package-B stage — source preflight only.**
Exact message: `docs(topology): register installed-unit protected writer implementation`.
CREATE none; MODIFY only this phase file; DELETE none. This registered next stage
must review the real native Identity claims/permission readers and executor's
compiled operation/DML family hooks, equipment position ownership and generation
sources, InstalledUnit persistence events and temporal contracts, and existing
architecture/test seams. Produce a concrete bounded CREATE/MODIFY/DELETE allowlist,
exact implementation message and meaningful synthetic acceptance matrix before
any runtime implementation. Do not create another A/B/C package or standalone task.

The registration must settle exact installed-unit operation-to-permission and
company/facility scope mapping (including unit registration without a position),
current-principal/read/history denial and relocation source/destination permissions;
position/facility/company binding and revocation fences; manufacturer capability
versus absent equipment-kind mapping; one physical transaction and finite native
validity/final recheck; precise SQL whitelist, CAS/close-replace concurrency and
audit rollback. Preserve station permissions, no implicit production grants,
no widened generic family bypass, no arbitrary timeout waiver, all ten shards
and strict dictionary equality. Any required migration must be explicitly named
and source justified; otherwise none is admitted. The preflight cannot claim code
or tests exist. Public controller/DTO/OpenAPI (Package C), HAPI-600/601, simulator,
frontend, release and deployment remain outside this next selection.

### HAPI-599 Package B — protected installed-unit writer source preflight and Identity registration — 2026-10-28

**Selected registered preflight.** Owner Next selects exact message
`docs(topology): register installed-unit protected writer implementation`.
CREATE none; MODIFY only this phase file; DELETE none. Main
`cfc858a2442ffc19feb8cf680c61c393f95a2fa0` has Docs #679 SUCCESS.
Runtime acceptance remains Full954 on `7c49a82d2de482b4305d29a3c1087bd818bc92fb`:
492 classes/3,610 cases, all ten PostgreSQL shards and strict API/dictionary gate.
This is a source-backed registration within fixed Package B, not runtime code,
an additional package or integrated HAPI-599 acceptance. Frozen states stay unchanged.

**Actual native Identity seam.** `ProtectedOperationIdentityParticipant` admits a
real native binding once, derives qualified claim reads from the actual principal,
and retains issuer/actor/thread/connection/transaction/time context. Its current
station `qualifyPair` compiles `topology:station-revision:*` and resource type
`STATION_GRAPH_REVISION`. `JdbcProtectedAuthorizationEvidenceReader` validates
those exact descriptors and other explicitly compiled families. The Organization
claim-group implementation demonstrates one native sample for several bounded
requirements and fresh final graph/time rechecks. It is a source pattern only:
Organization credentials, station pairs and user-constructed scalar contributions
must never become installed-unit credentials. No generic caller-chosen permission,
resource, scope or family selector is admitted.

**Concrete installed-unit Identity contract to implement next.** All operations
require actual authenticated native Identity plus a current PERMIT for every
derived scope; DENY dominates, otherwise INDETERMINATE is non-permitting. A typed
request binds exact company, unit, applicable installation/replacement identities,
and source/destination facility descriptors. These are requested descriptors, not
proof of existence/ownership. Exact references are nonblank/unpadded, at most80
Unicode code points; reject malformed operation-specific shapes before native
consumption. Use one native sample for the entire group; require fresh owner checks
and unchanged request through final recheck. No production permission/grant seed.

| Operation | Exact permission code | Resource type / reference | Required scopes |
|---|---|---|---|
| REGISTER_UNIT | `topology:installed-unit:register` | `INSTALLED_UNIT` / unit ID | Company only |
| READ_UNIT | `topology:installed-unit:read` | `INSTALLED_UNIT` / unit ID | Company only |
| RECORD_INSTALLATION | `topology:installed-unit:record-installation` | `INSTALLED_UNIT_INSTALLATION` / new installation ID | Company and destination facility |
| CLOSE_INSTALLATION | `topology:installed-unit:close-installation` | `INSTALLED_UNIT_INSTALLATION` / existing installation ID | Company and source facility |
| RELOCATE | `topology:installed-unit:relocate` | `INSTALLED_UNIT_INSTALLATION` / source installation ID and replacement installation ID | Company and both source/destination facilities, for both installation resource identities |
| READ_INSTALLATION | `topology:installed-unit:read-installation` | `INSTALLED_UNIT_INSTALLATION` / installation ID | Company and source facility |
| READ_HISTORY | `topology:installed-unit:read-history` | `INSTALLED_UNIT_INSTALLATION` / installation ID | Company and source facility |

Company scope is `CUSTOM` / `topology-company:<company ID>`; facility scope is
`FACILITY` / exact facility ID. Both relocation resource IDs must differ; both
facility descriptors remain bound even when equal (deduplicate identical required
evaluations deterministically). Unit-only requests require no fabricated facility
or installation ID; record requires destination only; close/read/history source
only; relocation both plus replacement ID. Reject forbidden extra fields and
missing fields. Derive requirement lists internally in deterministic resource/scope
order, never accept an arbitrary list of permission strings. Maximum relocation
work is six unique evaluations. Existing policy evaluator semantics stay unchanged.

The exported contract provides opaque Prepared/Admission lifecycle, non-authoritative
immutable scalar contributions, requireCurrent and exact technical final fences.
Prepare precedes the physical transaction; acquire/contribute/current/fences require
a genuine writable synchronized read-committed transaction. Tickets are single-use,
issuer/principal/thread/holder/PID/XID/current-role bound and invalid after suspension
or completion. Reject forged/replayed/mismatched-family inputs before disclosure;
poison the issuing transaction on owner failure, including swallowed and foreign-
thread misuse. Native/session/source/grant/policy temporal bounds remain authoritative;
final reread must detect in-transaction changes as well as concurrent revocation.
No new timeout, commit proof key family, optimistic Boolean or station-code alias.

**Position/owner preflight disposition for the remaining writer.**
`EquipmentJpaEntity` and V20260611_004 have nullable facility/node/segment placement;
no equipment ownership-generation registry was found in the current migration chain.
Do not invent a generation or infer a facility through a guessed node/segment path.
The first protected writer must admit only a current directly identified facility
whose existing owner export matches the company, and reject unresolved placement.
Before implementation of that writer, separately register the exact equipment
relation-lock/catalog/privilege strategy and owner export allowlist. Use a current
fenced position read held through final guard; ID/FK and client facility IDs are
insufficient. Source and destination positions must each be checked for relocation.
No equipment-kind compatibility is implied by Party EQUIPMENT/INSTRUMENTATION
capability; UNKNOWN/UNVERIFIED/VERIFIED supplied provenance remains distinct from
eligibility. Known manufacturer references require the accepted live Party evidence;
unknown provenance must never be promoted into verified qualification.

**Atomic persistence/executor preflight disposition.** V20261028_002 fixes immutable
unit company identity, half-open exclusion for unit/position occupancy, initial
revision1, one CAS versioned closure and trigger-appended immutable event history.
`JdbcInstalledUnitRepositoryAdapter` is private ordinary JDBC storage with unqualified
SQL; its methods are not protected permission gates. The future writer must bind
actual unit/company and installation/revision/position IDs before reads or mutations,
use schema-qualified exact reviewed statements, close plus replacement in one
physical transaction, and roll back both rows/events on any conflict or final failure.
Missing/foreign installation/history must not disclose rows, counts or existence.

`ProtectedTransactionExecutor` admits only compiled service/coordinator names and
reviewed per-family provisional SQL. There is currently no installed-unit family.
The future registration must name those exact classes and fixed statement hashes;
do not widen table prefixes or reuse station permits. Preserve separate credential
routing, native fences, finite standard500ms processing policy, minimum genuine
validity deadline and final recheck immediately before commit. Custody/appointment
policy overrides do not apply to this family. Keep trigger/catalog/privilege tests,
own-transaction tampering, NOWAIT, stale CAS, overlap races, atomic relocation and
history authorization in the later writer acceptance matrix. No migration or
executor/storage modification is admitted by the next Identity-only step below.

**Next executable implementation within the same Package B — Identity contribution only.**
Exact message: `feat(identity): export guarded installed-unit operation authorization`.

CREATE only:
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyInstalledUnitAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedTopologyInstalledUnitAuthorizationParticipant.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedTopologyInstalledUnitAuthorizationParticipantTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest.java`

MODIFY only:
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipantTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReaderTest.java`
- `.github/ci-test-inventory.json` (actual modified/new class cases and source hashes; unmeasured timings remain explicit)
- `doc/roadmap/TASKS_HPS-006.md` (append actual evidence/limits)

DELETE none. No Flyway, permission/grant data, Party repair, Topology writer/position
adapter, executor/configuration, public REST/OpenAPI, dictionary or frozen registry
changes. No foreign consumer exists yet, so do not add speculative cross-module
allowlist exceptions. Identity owns all internal transport/graph types; exported
contract scalars must not expose private Identity entities or authority-bearing
structurally constructible credentials. Preserve every existing native family and
its tests, and all ten shard membership/coverage controls.

**Required implementation acceptance, not existing test claims.** Focused unit
cases must cover all seven mappings and exact structural rejection, deterministic
bounded relocation requirements, issuer/family/request isolation and denial merge.
Fresh migrated PostgreSQL16 configured participant tests must prove real principal/
native binding/session/source validation, absent/revoked/stale grants, independent
company/facility denial, no station-permission substitution, both relocation IDs
and scopes, same-facility dedup without omitted authority, native/time boundaries,
current-policy changes, NOWAIT/fences, rollback and forged/replay/thread/transaction
misuse. Exercise final graph rechecks after own-transaction edits. Require current
architecture/private-owner tests and source inventory; run useful local checks and
report unavailable Maven/PostgreSQL honestly under AGENTS11.1. Publish guarded main
only in this allowlist, observe exact-SHA Full/Fast/Docs creation/start, then stop.
This preflight passed local documentation/link validation and `git diff --check`;
registered create/modify paths were checked against current files. No Java, Maven
or PostgreSQL runtime execution occurred in this documentation-only stage.
Hosted success accepts only the Identity contribution; separately register the
position/facility admission and atomic writer scope after this gate passes.
Package C, HAPI-600/601, frontend, simulation/P3, SITE, release and deployment remain
unselected. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED pending integrated B/C acceptance.

### HAPI-599 Package B — guarded installed-unit Identity contribution implementation — 2026-10-28

**Owner Next; exact registered implementation.** Parent main
`2d9385ee0be576631f02487fbf5fbcf82b9fb6f0` has Docs #680 SUCCESS.
The accepted runtime baseline remains Full954 on `7c49a82d2de482b4305d29a3c1087bd818bc92fb`.
This step executes only `feat(identity): export guarded installed-unit operation authorization`
within the preceding four-CREATE/six-MODIFY allowlist. No delete, migration,
Topology/Party implementation, executor, policy/grant seed, public API or frozen
status change. Earlier preflight and Party acceptance remain historical evidence.

**Implemented source.** `TopologyInstalledUnitAuthorizationContract` defines the
seven registered permission mappings, exact bounded typed request shapes and
deterministically derived company/facility requirements. Unit registration/read
requires company only; installation operations require applicable facility scope;
relocation binds both distinct installation IDs and both facility descriptors,
with at most six evaluations (four for equal facilities). No arbitrary permission
list or fabricated station authority is accepted. Contributions are immutable
scalar observations; opaque private Prepared/Admission tickets carry authority
only for their issuing owner lifecycle, never by structural construction.

`ProtectedOperationIdentityParticipant` now issues a distinct private installed-unit
native group from one real admission/sample. All children remain issuer, authenticated
actor, request, thread, actual holder/connection, backend/XID, database-role and
transaction-lifetime bound. Consume/current entry points reject inactive groups,
request swaps, replay and cross-family descriptors; failures poison the issuing
holder, including foreign-thread misuse. Native binding/source/session checks,
retained Identity fences and temporal bounds remain required. A final boundary
scan detects new own-transaction grant/policy cutoffs without issuing a new sample.

`JdbcProtectedAuthorizationEvidenceReader` loads every required graph before any
export, validates the exact new family only on genuine opaque credentials and
rechecks under retained locks with fresh invocation-local rows. Existing structural
diagnostic reads cannot select the installed-unit family. Public reader-surface
tests now admit exactly the two new methods with their exact native-group/request
and evidence signatures; old family assertions remain. No generic public family
selector or private entity export was introduced.

`ProtectedTopologyInstalledUnitAuthorizationParticipant` binds genuine native
preparation/admission, evaluates every derived scope with the existing domain
evaluator and permits only overall PERMIT. Any DENY or INDETERMINATE fails closed.
Final checks compare the full freshly read graphs and decisions and revalidate
native/physical context and bounded validity. Ticket invalidation and a SQL-free
commit veto preserve rollback even when owner exceptions are swallowed.
No business row or position ownership is asserted by this Identity contribution.

**Authored tests and inventory; hosted execution still pending.** Six new standalone
unit tests cover all seven mappings, structural shape/identifier rejection, exact
relocation requirement order and bounded deduplication, denial precedence and
forged admission rejection. Nineteen new configured PostgreSQL16 tests exercise
actual LOCAL/OIDC native producers, all seven operations/coordinates/fences,
company/source/destination denial, both relocation resource IDs under actual policy,
same-facility deduplication, station-family exclusion, grant and policy rechecks,
future cutoffs, actual source expiry, session revocation, role/actor replacement,
request swap/replay/forgery, thread/transaction suspension, NOWAIT and rollback.
The isolated PostgreSQL fixture uses a synthetic restricted role and genuine
Flyway/native cryptography; production roles/data are not provisioned. Two existing
unit suites each gain one family-isolation case; their previous cases are retained.

The inventory changes only those two existing case/hash identities and the two
actual new classes. New timings remain unmeasured baseline0; all old timing,
greenfield and scheduling provenance is preserved byte-for-byte. There are now
494 active classes (376 unit/architecture,118 PostgreSQL) plus four unchanged
historical exclusions, with 3,637 expected active cases. Ten required shards and
existing deterministic balancing remain intact; no suite is skipped or reclassified.

**Actual available validation.** All eight changed Java sources parse. The real
new contract and existing relation-fence type compile with the available Java17
compiler, and seven reflective operation-shape probes pass; this is not a Java21
Spring/JUnit run. 149 Python validator self-tests pass:35 inventory,53 full-evidence,
19 docs,10 OpenAPI,18 dictionary,14 API-base resolution. Inventory/source classification,
full docs/link validation, offline OpenAPI snapshot and diff whitespace checks pass.
Focused `mvn -q -Dtest=ProtectedTopologyInstalledUnitAuthorizationParticipantTest,ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest test`
exits127: `mvn: command not found`. No local JUnit/PostgreSQL execution or new physical
catalog capture is claimed. No full tool/service environment is provisioned merely
to reproduce CI, under AGENTS11.1. All184 existing migration files and the current
physical dictionary remain unchanged.

Publish only the exact ten allowed paths using the expected-head guard and registered
message; verify blobs/tree/parent, observe exact-SHA Full/Fast/Docs startup and stop.
The new native family is implemented, not yet CI-accepted. After genuine success,
independently review its exact case evidence before registering the remaining
position/facility admission and protected atomic writer within the same Package B.
No Topology consumer/architecture exception or executable storage grant is supplied
in this step. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED pending integrated B/C;
manufacturer-kind qualification, Package C, HAPI-600/601, simulator/P3, frontend,
SITE, release and deployment remain unselected.

### HAPI-599 Package B — Full955 installed-unit reader compilation repair — 2026-10-28

Owner Fail selects the responsible bounded repair on exact main
`c8f524c8f150197048159963bf324d9aed53c84b`. Full955/run `40699757101`
and Fast256/run `40699757080` failed during production compilation. Static
validation succeeded; no new Java/JUnit/PostgreSQL execution is established.
The compiler reports reader lines451/473 referencing nonexistent
`contract.organization.InstalledUnitProtectedAppointmentAuthorizationContract.Request`.
The build failure precedes every selected test lane and API capture; subsequent
missing-report/artifact and final-gate errors are consequences, not separate waivers.

Exact repair message:
`fix(identity): correct installed-unit authorization reader contract references`.
CREATE/DELETE none; MODIFY only
`src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java`
and this owning phase. Both new reader methods now take the registered actual
`contract.topology.TopologyInstalledUnitAuthorizationContract.Request`, matching
the participant, opaque native group and existing exact-signature regression.
No operation/permission/security behavior, test, inventory, shard, migration,
dictionary or workflow is changed. All27 newly authored cases remain mandatory.

Available validation: Java syntax parse passes; all28 project-qualified top-level
types referenced by the four changed/new production sources resolve to real source
paths after correction. This source-resolution check is not full type compilation.
Inventory validation passes with unchanged494 active classes/3,637 cases and ten
shards. The prior actual contract compilation/probes remain limited contract proof;
Maven remains unavailable (`mvn: command not found`), so no Java21, JUnit or
PostgreSQL success is claimed locally. Documentation/link and whitespace checks
are required before guarded publication. Exact-head hosted verification remains
pending; observe applicable Full/Fast/Docs startup and stop. HAPI-599 remains
NOT_IMPLEMENTED/BLOCKED; no dependent position/writer or Package C is selected.

### HAPI-599 Package B — Full956 fixture and exact-admission guardrail repair — 2026-10-28

Owner Fail selects the responsible repair from exact main
`2ecbd73774ad0b84750fa9b1468eb30eef78fe5c`. Full956/run `40700284629`
compiled production and tests; static, API/dictionary and nine PostgreSQL shards
passed. Unit/architecture and Fast257 failed two exact boundary assertions; shard5
ran the new installed-unit suite with19 tests, zero assertion failures, two fixture
errors and no skips. No full acceptance is claimed from partial success.

**Actual causes and bounded correction.** The new registered Identity participant
legitimately calls native `admit`, but the exact caller-set guard still listed only
the six earlier participants. Add only
`ProtectedTopologyInstalledUnitAuthorizationParticipant.java` to that native Identity
caller set; collector/acquisition sets and every executor/DML caller assertion remain
unchanged. This is not a foreign Topology consumer or generic family exception.
The reader's exact SHA-256 pin still described the pre-installed-unit source;
reviewed source adds the separate opaque installed-unit reader/recheck and exact
family validation. Pin the actual corrected reader to
`3b734e9fa7d78372a5555d551ed4e45428515f280fb4e973a76d9a734ea2fa56`,
retaining all four Party/facility source pins and all guardrail assertions.

The session-revocation fixture attempted status-only termination. Immutable
V20261013_001 requires a finite ended_at at/after started_at for a bound terminal
session. The fixture now sets status and database-clock ended_at together, asserts
one ACTIVE row transitioned and verifies the terminal tuple before asserting native
admission denial. The station-only grant fixture omitted mandatory valid_to; supply
a finite one-hour interval, assert a current station grant exists and no installed-unit
grant exists, then retain the intended cross-family denial assertion. These repairs
make the negative tests reach their authorization assertions; no migration, runtime
authorization, deadline, privilege or assertion is relaxed.

Exact message: `test(identity): align installed-unit fixtures and admission guardrails`.
CREATE/DELETE none. MODIFY only:
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest.java`
- `.github/ci-test-inventory.json` (only the two changed source SHA-256 values)
- `doc/roadmap/TASKS_HPS-006.md`

All existing cases remain; suite sizes9 and19 are unchanged, as are494 active
classes/3,637 expected cases, ten mandatory shards and every stored timing/source
observation outside those two hashes. No production, Flyway, dictionary, workflow,
permission grant seed or frozen status mutation is selected.

Available validation: both Java test sources parse; direct source checks reproduce
the three exact native caller sets (1 collector,1 acquisition,7 admission callers)
and all five SHA-256 pins successfully. These checks are source verification, not
JUnit execution. Inventory validation passes and its diff contains only two hashes;
88 CI inventory/evidence validator self-tests pass. Docs/link and whitespace checks
precede publication. Maven remains unavailable, so no new local JUnit/PostgreSQL
result is claimed. Preserve the failed956 evidence and let hosted CI exercise the
corrected fixtures and guardrails. Publish guarded main using the message above,
observe exact-SHA Full/Fast/Docs startup and stop. HAPI-599 remains
NOT_IMPLEMENTED/BLOCKED pending exact-head acceptance and remaining B/C work.

### HAPI-599 Package B — Identity acceptance and guarded position ownership registration — 2026-10-28

**Owner Next; independently reviewed exact-head acceptance.** Main
`b1ddf70793def5035f295062c21082f926bdd481` has Full957/run `40702212948`,
Fast258/run `40702212962` and Docs683/run `40702212968` completed SUCCESS.
Full is development mode, attempt1, cached dependencies, serial_oracle=false;
its separate serial job is skipped by design and release_eligible=false.
All ten PostgreSQL shards, unit/architecture, static, API/dictionary and final
gate passed. Retain Full955 compilation and Full956 guardrail/fixture failures
as history; neither supplies acceptance for the new code before these repairs.

Downloaded full bundle artifact `12821947899` has verified ZIP SHA-256
`a0b47e13f11f71d40172d0a9fc8e62815ffa7f82f3487fb4d01b56a5665f2ba2`.
Independently checked all501 evidence-listed file hashes and exact source/run/attempt;
reconstructed raw XML matches all494 active inventory classes and3,637 exact cases,
zero failures/errors/skips. The new Identity unit suite passes6 cases, PostgreSQL
suite19 cases (observed46.685s), and boundary guardrail9 cases. The timing inventory
is not rewritten from this observation. All four historical greenfield exclusions
remain unchanged. This review did not rerun JUnit/PostgreSQL locally.

Same-run dictionary receipt is checked=true on the accepted SHA: PostgreSQL16.15,
184 migrations,470 mappings,531 relations, zero unresolved owners. Source bundle
SHA-256 remains `b224960330372f46f4a2c6012a1473d779188092314b5f393125682eb330097d`;
catalog SHA-256 `53d807228061b547a7bee489b4d865ff5bfd6291e2a0a0e85da656a25a1cdf38`;
checked render SHA-256 `3fdd3091dcfe4fae54c70a44844191c182f33eaf6049ec4cec8e2d671de5e6f4`.
Runtime API/base compatibility passed against `7c49a82d2de482b4305d29a3c1087bd818bc92fb`;
current API SHA-256 `84a90e75b6de6b5b064bf786ee8066cb607e1fb6804125b89167d47778ee138b`.

**Bounded disposition.** The registered installed-unit Identity contribution and
its responsible repairs are accepted for the tested backend-development scope.
Package A and the earlier Party contribution remain accepted. This does not accept
integrated Package B or C, business position eligibility, industrial qualification,
role provisioning, SITE, release or deployment. No existing implementation should
be repeated. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED in frozen ROADMAP/PHASES.

**This one selected acceptance/registration commit.** Exact message:
`docs(topology): accept installed-unit Identity and register position ownership evidence`.
CREATE/DELETE none; MODIFY only this phase file. Validate docs/links and whitespace,
publish with expected-head guard, observe exact-SHA Docs startup and stop.
Local documentation/link validation and whitespace checks pass; all five proposed
create paths and the new SQL function name are unoccupied. No Java/PostgreSQL
execution is claimed for this documentation-only registration.

**Position/facility source facts and chosen bounded seam.** V20260611_004 and
`EquipmentJpaEntity` identify the Topology-owned equipment row and nullable
facility/node/segment placement. There is no equipment ownership-generation
registry in the current source. V20261018_002 supplies the real facility
owner-generation/tombstone registry and fixed trigger/function semantics.
`JdbcStationProtectedFacilityOwnershipAdapter` demonstrates its catalog, lifetime,
role and lock checks, but its exact contract/source pins stay unchanged. The new
adapter must query its own three fixed relations rather than cast station tickets
or infer a facility from a guessed node/pipeline-segment traversal.

**Next executable implementation within the same Package B.** Exact message:
`feat(topology): export guarded installed-unit position ownership evidence`.

CREATE only:
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/InstalledUnitPositionOwnershipPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcInstalledUnitPositionOwnershipAdapter.java`
- `src/main/resources/db/migration/V20261028_004__topology_installed_unit_position_owner_lock.sql`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcInstalledUnitPositionOwnershipAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/InstalledUnitPositionOwnershipPostgresIntegrationTest.java`

MODIFY only:
- `.github/ci-test-inventory.json` (the actual two new suites/cases/hashes, timing unmeasured until observed)
- `doc/roadmap/TASKS_HPS-006.md` (append implementation/validation/remaining gates)

DELETE none. No old migration, JPA entity, existing station/Party/Identity contract,
executor, authenticated permission, writer/storage method, public REST/OpenAPI,
foreign-consumer architecture rule or frozen registry changes. This is another
owner contribution within fixed B, not a new package or task.

**Required port and evidence.** Provide opaque acquire(exact position ID), single-use
read, requireCurrent and typed finalFences in a genuine writable read-committed
physical transaction. Only the issuing adapter accepts private tickets. Contribution
contains exact position/equipment-type/facility/company references, actual position
and facility status/type classification, actual positive live facility-owner
generation, PID/XID and bound database role. These are current owner observations,
not a permission, model compatibility approval or public existence endpoint.
No invented equipment generation, synthetic timestamp-version or caller-supplied
company/facility assertion may become proof. Require exactly one position with an
explicit direct facility, exactly one facility with exact owner and a present positive
generation. Missing, malformed, RLS/inheritance/view/temporary replacement, absent
owner/tombstone or unresolved placement fails closed. Do not invent support for
node/segment-only placement. Export recognized status classifications faithfully;
this evidence-only step does not invent an ACTIVE-only business eligibility policy.
The later protected writer must separately define and check permitted statuses.

**Exact lock privilege design, avoiding the earlier SELECT-only conflict.** The
additive migration creates only parameterless
`public.hidra_topology_lock_installed_unit_position_ownership() RETURNS void`, fixed
SECURITY DEFINER plpgsql body locking equipment then facility in SHARE ROW EXCLUSIVE
MODE NOWAIT, no DML, dynamic SQL, role selection or transaction control. Set fixed
search_path=pg_catalog,pg_temp; revoke PUBLIC EXECUTE in the same migration transaction.
No automatic production role/grant is created. Deployment must explicitly provision
a separate NOLOGIN NOINHERIT nonsuperuser owner with no memberships or administrative
flags, no CREATE on trusted schemas at use time, no ownership of source tables and
only non-grantable UPDATE needed on the two locked tables. Grant explicit non-grantable
EXECUTE only to the approved nonowner evidence reader. Reader retains SELECT-only
rights on equipment/facility/generation, including column-level no-DML checks.

Before execution and before disclosure/final checks, validate exact helper signature,
fixed body digest computed from the actual new migration, security/search-path,
owner privileges/isolation and exact reviewed ACL (no PUBLIC/extra grantees/grant option).
Like the accepted Party helper, default migration ownership alone is not valid runtime
provisioning; fail closed until the approved dedicated owner exists. The caller
transaction must actually retain both strong relation locks; lock the existing
facility-owner-generation registry ACCESS SHARE NOWAIT as a separate fixed statement.
Return exactly these three technical fences; preserve the generation registry's
existing catalog/trigger/function pins and no-write lifetime invariants.

Hold fences through final validation. Tickets bind issuer, exact request, actual
holder/connection, thread, role, PID/XID and synchronization lifetime, invalidate
on suspension/completion and reject replay/forgery/foreign use. requireCurrent must
freshly compare the bound owner scalar image and catalog/lock state, including
own-transaction tampering and lost savepoint locks; no cached positive result.
Any failure poisons the issuing transaction and prevents swallowed-error commit.
A future coordinator must compare this actual company/facility against the accepted
Identity request and relevant Party evidence before mutation or disclosure.

**Required synthetic verification.** New unit tests reject fabricated tickets,
missing/read-only transactions and malformed identities before row access. Fresh
Flyway/PostgreSQL16 tests must prove direct facility/owner/generation evidence,
unchanged existing owner exports, missing/null/wrong/tombstoned placement, faithful
status classifications without an invented eligibility Boolean, actual helper
provisioning with SELECT-only reader, no PUBLIC execution, exact least-privilege
owner/ACL and body/search-path tamper rejection, both table writer conflicts/NOWAIT,
transaction-end release, own-transaction/image changes, role/thread/request/holder
mismatch, suspension/completion/savepoint invalidation and swallowed-error rollback.
Check raw locks/coordinates on the caller connection and a separate writer; authored
cases alone are not acceptance. Keep all ten required shards and strict CI gates.

**Truthful CI-first dictionary follow-up.** Under AGENTS11.1, unavailable local
PostgreSQL/catalog is not a reason to fabricate metadata or install a full service
environment. Initial implementation preserves the current dictionary and explicitly
records pending regeneration; a strict dictionary drift failure is expected if the
new migration changes the render. Capture genuine same-head hosted schema/catalog
before any refresh. Registered follow-up message:
`docs(topology): refresh installed-unit position ownership physical dictionary`;
MODIFY only `doc/database/DATA_DICTIONARY.md` and this phase; CREATE/DELETE none.
Verify artifact ZIP digest, exact SHA/run/attempt/source bundle and full migration/
mapping/relation/owner metadata, run the unchanged generator, review exact equality
and preserve strict comparison. If capture fails because code/migration/tests fail,
repair that actual cause first. Do not invent catalog facts or weaken the gate.
Each publication observes applicable exact-SHA CI startup and stops; documentation-only
Full dispatch limitations must be reported honestly. Green code/catalog and independent
evidence review precede separate atomic-writer registration. No writer or Package C
implementation is admitted by either evidence/dictionary scope above.

### HAPI-599 Package B — guarded current position/facility ownership implementation — 2026-10-28

**Owner Next; registered implementation only.** Exact parent
`b2edd785b32644e6c025c18e1a00c3a847660454` has Docs684 SUCCESS;
accepted runtime remains Full957/Fast258 on `b1ddf70793def5035f295062c21082f926bdd481`.
Execute only `feat(topology): export guarded installed-unit position ownership evidence`
in the preceding five-CREATE/two-MODIFY allowlist. No existing station/Party/Identity
source, executor, writer/storage method, JPA entity, public API, old migration or
frozen status changes. This is an owner observation contribution within fixed B.

**Implemented source.** `InstalledUnitPositionOwnershipPort` exports opaque admission,
single-use read, requireCurrent and three exact technical fences. Scalar observations
contain current position/type/facility/company references, recognized equipment/
facility status and facility kind, actual live positive facility-owner generation,
PID/XID and database role. There is no invented equipment generation, eligibility
Boolean, Identity grant or equipment-kind compatibility claim. The reader requires
one explicit unambiguous direct facility; null/missing placement, an additional
node/segment attachment, absent owner or missing/tombstoned generation is unavailable.
Non-ACTIVE recognized statuses are faithfully reported, not silently promoted or
rejected under an unregistered eligibility policy. A future authorized writer must
compare actual company/facility and apply its separately registered business policy.

`JdbcInstalledUnitPositionOwnershipAdapter` uses the exact three fixed Topology
relations, reusing unchanged facility-generation schema/constraint/trigger/function
pins from the accepted facility owner implementation. It requires writable synchronized
read-committed context, safe search path, nonowner/nonadministrative runtime and
SELECT-only table/column privileges. It rejects RLS, temporary/view/partition/
inheritance replacements and schema-create authority. Runtime to_regclass/to_regprocedure
lookups and row tableoid checks prevent cached source names from admitting renamed
or replaced relations. Fresh private SHA-256 row images cover equipment and facility
source rows; final recheck compares both images and the entire bound scalar tuple,
catching own-transaction changes even when the ownership generation did not change.

The new additive `V20261028_004__topology_installed_unit_position_owner_lock.sql`
creates only fixed parameterless
`public.hidra_topology_lock_installed_unit_position_ownership() RETURNS void`.
Its body locks equipment then facility SHARE ROW EXCLUSIVE NOWAIT; no row DML,
dynamic SQL, grant seed or transaction control. VOLATILE/PARALLEL UNSAFE/SECURITY
DEFINER with fixed search_path=pg_catalog,pg_temp and same-transaction PUBLIC revoke.
Exact prosrc SHA-256 computed from the authored SQL body:
`60466e68d8b31baff428ba3d3bfeb318b4f2217e74fcd926007c47da444fe603`.
The reader separately locks the existing generation registry ACCESS SHARE NOWAIT.
Both strong locks must actually remain on the caller's PID through recheck.

The helper is not deployable merely by its default migration ownership. Explicit
provisioning must transfer it to the registered isolated NOLOGIN NOINHERIT owner,
with schema USAGE but no CREATE at runtime, no memberships/admin flags/source table
ownership, and only non-grantable UPDATE on the two fixed source tables. Reject
extra SELECT/INSERT/DELETE/TRUNCATE/REFERENCES/TRIGGER privileges and table/column
UPDATE grant options. Only the approved SELECT-only reader gets explicit non-grantable
EXECUTE; reject PUBLIC/extra grantees, unsafe language/signature/body/security/path
or owner changes before call and before disclosure/final checks. The migration
creates no role or runtime permission; isolated tests alone provision synthetic roles.

Private tickets bind issuer/request, thread, actual holder/connection, role and
PID/XID; reject forgery/replay/foreign issuer, suspension, completion and lost
savepoint locks. Ticket-consuming methods perform context checks inside their
bodies so foreign-thread invocation can poison the issuing holder instead of being
intercepted before invalidation. SQL-free synchronization vetoes swallowed-error
commit; all reads/fences/current checks fail closed. No underlying source DML is
made available to the evidence reader.

**Authored synthetic cases; no local runtime pass claim.** Six standalone unit tests
cover exact identity rejection, transaction/read-only rejection before SQL, forged
lifecycle tickets, non-authoritative scalar construction and private ticket shape.
Twenty-three fresh full-Flyway PostgreSQL16 tests cover actual configured restricted
reader/owner wiring, direct evidence/coordinates/fences, null/missing/tombstoned
placement and generation, status/ownership changes, SELECT-only write denial,
PUBLIC/reader/extra ACLs, helper body/security/path/owner/privilege tampering,
column/table grants, RLS/view replacement, both NOWAIT writer directions and commit
release, forged/replayed/foreign/expired tickets, read-only/isolation rejection,
thread/suspension/savepoint invalidation, swallowed failure rollback, generation
trigger tampering, same-transaction row images/owner transfer/role changes, malformed
status and ambiguous node attachment. A separate synthetic schema-owner connection
is used only for deliberate own-transaction mutation; observations use the SELECT-only
role and actual production adapter. Existing owner contracts and their pins are unchanged.

Inventory adds only those two actual classes/cases/hashes, unmeasured baseline0;
all prior entries, observed timing/provenance, historical exclusions and ten shard
controls remain unchanged. Active inventory:496 classes (377 unit/architecture,
119 PostgreSQL),3,666 expected exact cases, plus four unchanged historical classes.
No test is skipped or reclassified; the existing deterministic scheduler assigns
the new suites without inventing measured wall times.

**Actual available validation.** All four new Java sources parse. The real port and
existing relation-fence record compile with the available Java17 compiler; scalar,
positive-coordinate and rejection probes pass. This is not full Java21/Spring/JUnit
validation. All project-qualified types in the four new Java sources resolve to
actual main/test source paths. 149 Python validator self-tests pass (35 inventory,
53 full evidence,19 docs,10 OpenAPI,18 dictionary,14 API-base resolution); inventory,
offline API snapshot and whitespace checks pass. Focused Maven test command exits127:
`mvn: command not found`. No local PostgreSQL catalog, JUnit or service execution is
claimed and no full tool/service environment was installed merely for CI.

All184 earlier migration blobs and current dictionary bytes remain unchanged.
A genuine hosted migrated catalog and strict dictionary refresh remain pending
under the preceding registered follow-up. The new185-file migration chain is authored,
not claimed migrated locally. Preserve strict comparison: initial CI may fail only
on the expected stale dictionary after successful capture, or reveal an actual
code/migration/test issue that must be repaired first. Never fabricate metadata.

Publish only the seven registered paths with exact parent/tree/blobs and expected-head
guard; observe exact-SHA Full/Fast/Docs creation/start and stop. The position owner
contribution is implemented, not yet accepted. Registered dictionary follow-up remains
`docs(topology): refresh installed-unit position ownership physical dictionary`,
limited to dictionary plus this phase and based solely on genuine hosted evidence.
Green code/catalog and independent exact-case review must precede protected atomic
writer registration. HAPI-599 stays NOT_IMPLEMENTED/BLOCKED pending integrated B/C;
public API, HAPI-600/601, simulation/P3, frontend, SITE, release/deployment remain unselected.

### HAPI-599 Package B — position ownership physical dictionary refresh — 2026-10-28

Owner Fail follows the registered catalog refresh on exact main
`3d56a99ba2f3ddcce32354e8f757ca2cc03f63d7`. Full958/run `40707378238`,
attempt1, failed only its API/dictionary lane and dependent Full gate after
successful real schema capture. Static, unit/architecture and all ten PostgreSQL
shards passed; Fast259 and Docs685 also passed. No runtime repair, case skip or
check waiver is indicated by these results. The stale dictionary failure was
explicitly anticipated by the preceding CI-first implementation registration.

Exact message: `docs(topology): refresh installed-unit position ownership physical dictionary`.
CREATE/DELETE none; MODIFY only `doc/database/DATA_DICTIONARY.md` and this phase.
The same-run schema artifact `12823523667` is named for the exact source/run/attempt;
its downloaded ZIP SHA-256 matches GitHub metadata:
`1a2d8e82da1fe4d8320ec5ffe88bfa66aeec52c6dd67818e6ecb8168f6b7aa3b`.
The unchanged generator validates the complete current migration/JPA source bundle
against the actual captured document: PostgreSQL `16.15 (Debian 16.15-1.pgdg13+2)`,
185 migrations,470 mappings,531 relations, zero unresolved owners.
Source bundle SHA-256 `cc3a447e4bf313777b59a85124f57e2d244973ee7ff53a9da24155dc4a94b74c`;
catalog SHA-256 `dab338588d135ad39b80b9ea230a5cfe2b77cdcb5ce804de2bbd8a44fa22e1e3`;
render SHA-256 `e6f6d5cb926e419f6e8c2739d3c1d5fc24b53211eaf2401ff8f40b92f25bcb6a`.
The locally regenerated Markdown matches the retained CI render byte-for-byte.
Reviewed diff is capture/source provenance, migration count184 to185 and a mention
of V20261028_004 under its equipment/facility relations; all physical relation
metadata is otherwise unchanged. No catalog fact was inferred or synthesized.

The strict generator comparison passes against the refreshed dictionary with
reviewed_dictionary_checked=true. All18 generator self-tests, documentation/link
validation and whitespace checks pass before publication. No local PostgreSQL/JUnit execution is claimed. All production,
test, migration, ownership, generator and workflow bytes are preserved in this
refresh. Strict dictionary equality remains mandatory; Full958 remains a failed
historical run and does not become accepted by rewriting its receipt.

After guarded publication observe exact-SHA Docs startup and stop. This two-file
documentation-only scope is excluded from automatic Full CI; connected GitHub tools
provide no workflow dispatch operation. Manually run HidraAPI CI on main using
verification_mode=development, serial_oracle=false, dependency_cache=cached.
Position ownership and integrated Package B acceptance remain open until new
exact-head Full success and independent case/evidence review. HAPI-599 remains
NOT_IMPLEMENTED/BLOCKED; no protected atomic writer, Package C, SITE, release or
deployment is selected by this repair.

### HAPI-599 verification hold — Full959 Organization final-rejection diagnostic repair — 2026-10-28

Owner Fail refreshes exact main `af15b8a53298685cda3b400d61456328d9b12200`.
Manually dispatched Full959/run `40709188619` failed only PostgreSQL shard0
and the dependent Full gate; unit/architecture, static, API/dictionary and nine
other PostgreSQL shards succeeded. Docs686 passed. The physical dictionary drift
is resolved. Shard0 ran the unchanged HAPI-778 appointment concurrency suite:
82 cases, zero assertion failures, one error, no skips. The failing case is
`genuineExternalOriginsResolveLegacyDefaultsThroughTheirBoundNativeIssuers`,
OIDC legacy create at final preparation after managed flush. LDAP and AD iterations
committed. OIDC diagnostic call elapsed4.483545040s and observed fence hold lower
bound4.478509499s, with guard not entered; configured work cap7.5s. This is not
proof that either the work cap or an authority expiry caused the rejection.

**Evidence limit and actual defect.** `prepareFinal` catches the original failure,
invalidates admission/rejects the transaction, then throws a fresh generic denial.
It discards the existing nested owner cause and stage timings. Its final-write and
replay wrappers likewise discard underlying errors. The retained Full959 trace
therefore cannot identify which current owner, storage image, native interval or
policy check rejected. Do not label this flaky, enlarge a deadline, skip the case
or claim the underlying rejection resolved without evidence. A bounded diagnostic
repair is the necessary next CI-first step; it does not accept the remaining B/C.

Exact message: `fix(organization): retain final appointment rejection diagnostics`.
CREATE/DELETE none. MODIFY only:
- `src/main/java/dz/sh/hidra/modules/organization/application/service/ProtectedEmployeeAppointmentAdmissionCoordinator.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ProtectedEmployeeAppointmentAdmissionCoordinatorTest.java`
- `.github/ci-test-inventory.json` (only this modified unit suite's cases/source hash)
- `doc/roadmap/TASKS_HPS-006.md`

The three final-preparation wrappers now retain the original failure as a suppressed
exception and append existing diagnostic-only BoundaryTimings with explicit final
stages. Public message and null-cause boundary remain unchanged: the HTTP handler
walks causes for status selection, not suppressed exceptions, and emits no diagnostic
payload. Rejection/invalidation, holder poisoning and commit veto are preserved.
No owner query, SQL order, permission, clock authority, native/source/grant/policy
expiry, proof/storage validity,7.5s processing cap or final guard is changed.
No production storage, migration, dictionary, shard or workflow modification.

Three new unit regressions cover final native/current-owner, final storage-write
and nested replay failures; assert original suppressed provenance/stage, unchanged
message/null public cause, rollback and no final guard. Previous76 cases remain,
for79 in that suite;496 active classes and3,669 expected cases overall. The82-case
PostgreSQL suite is byte-for-byte unchanged, and its failure remains mandatory.
All timings/provenance/lane/classification outside this unit case/hash update stay
unchanged; ten required shards remain.

Available local checks: both changed Java sources parse; inventory validation and
88 CI inventory/evidence validator self-tests pass. Focused Maven command exits127
(`mvn: command not found`), so no new JUnit or PostgreSQL success is claimed.
Documentation/link and whitespace checks precede guarded publication. This repairs
lost diagnostics only; the underlying OIDC final rejection remains unresolved
pending hosted evidence. Observe exact-head Full/Fast/Docs startup and stop, then
use any retained actual cause/stage to repair the responsible invariant without a
waiver. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED, and current position acceptance,
atomic writer, Package C, release and deployment remain unselected/pending.

### HAPI-599 Package B — position ownership evidence acceptance — 2026-10-28

**Owner Next; exact-head independent acceptance.** Main
`7a6d93bb8709b0bfc2df3d9cb4b9ea68e68802b6` has Full960/run `40711999664`,
Fast260/run `40711999658` and Docs687/run `40711999535` completed SUCCESS.
All ten required PostgreSQL shards, unit/architecture, static, API/dictionary and
the final gate passed. Development mode, attempt1, cached dependencies and
serial_oracle=false are explicit; the separate serial job is skipped by design,
and release_eligible=false. No release or operational acceptance follows.

Downloaded full evidence artifact `12826443913` has ZIP SHA-256
`4ddc5e2f80e082759120b336869c8a39f6f92a2d92b285abdbb481dea3e87bd1`,
matching GitHub metadata. Independently checked all503 listed file hashes and
reproduced gate.json byte-for-byte with the unchanged full-evidence aggregator
against current source/inventory. Raw XML and exact case identities reconcile
all496 active classes and3,669 cases, zero failures/errors/skips. The position
unit suite passes6 cases and its PostgreSQL suite23 cases (12.318s observed).
The Organization coordinator suite passes79 cases, including all three diagnostic
regressions; its unchanged appointment concurrency suite passes82 cases (325.924s).
No timing inventory, case classification, historical exclusion or shard is changed.
This is retained hosted evidence review, not a local JUnit/PostgreSQL execution.

Same-run dictionary receipt is checked=true on this SHA: PostgreSQL16.15,
185 migrations,470 mappings,531 relations, zero unresolved owners. Source bundle
SHA-256 `cc3a447e4bf313777b59a85124f57e2d244973ee7ff53a9da24155dc4a94b74c`;
catalog SHA-256 `acb83aef263b18e87f44d5c9a6f15f674afe6980ac4aefcc1bfc9dbec5d28602`;
checked render SHA-256 `da2768e688f17e4a4606920675f6a802c6e5223cd81eed09ae674100f10cd598`.
API/base compatibility passed against `b1ddf70793def5035f295062c21082f926bdd481`;
current API SHA-256 `2f33b69dff12127bddcfa77b6f8b76419ed335a311eecfdb1dab8b1d6f968829`.
The physical dictionary refresh is accepted; no regenerated artifact is needed.

**Disposition and publication hold.** Accept only the registered position/facility
owner contribution for its tested development software scope. Package A, Party
manufacturer evidence and installed-unit Identity acceptance remain intact.
Full959 remains a failed historical run. Its OIDC rejection did not recur in960;
the diagnostic repair retains future rejection evidence but does not establish
or repair the unidentified underlying cause. Do not call it a proven timeout,
relax any deadline, suppress its case or claim permanent stability from one pass.
The exact-head CI/dictionary verification hold on this contribution is resolved.
Integrated Package B/C and HAPI-599 remain NOT_IMPLEMENTED/BLOCKED; protected atomic
writer implementation, public API, engineering qualification, SITE, release and
deployment remain pending. Frozen ROADMAP/PHASES statuses are unchanged.

**This single selected stage.** Exact message:
`docs(topology): accept installed-unit position ownership evidence`.
CREATE/DELETE none; MODIFY only this phase, append-only. Required available checks:
independent full-evidence reproduction, documentation/link validation and whitespace.
Publish with expected-head guard and verified parent/tree/blob; observe exact-SHA
Documentation Validation creation/queue/start and STOP. No runtime/workflow change
or unnecessary Full dispatch is selected by this documentation-only acceptance.

**Next registered stage, not executed here.** Exact message:
`docs(topology): register protected installed-unit atomic writer implementation`.
CREATE/DELETE none; MODIFY only this phase. Review current installed-unit domain,
storage schema, accepted Party/Identity/position contracts, protected executor,
permission and DML caller guards before registering an exact bounded Package B
implementation allowlist, contracts, synthetic acceptance and migration need.
Resolve lock order, native/current-owner checks, physical transaction binding,
idempotency/replay, lifecycle/history and SQL-free final-guard commit veto from
actual source. Do not guess filenames, widen existing source pins, invent an
equipment-kind compatibility mapping or implement writer/API code in that stage.
Any unresolved cross-owner policy remains explicit; registration is not integrated
acceptance. Public Package C and dependent tasks require separate selection.

### HAPI-599 Package B — protected atomic writer implementation registration — 2026-10-28

**Selected stage and evidence.** Owner Next refreshes main
`078b17c91843d3142b466c4059120bd6e396dad6`, Docs688/run `40735509969`
SUCCESS. Its unchanged runtime parent retains independently reviewed Full960,
Fast260 and Docs687 success above. This is one documentation-only registration
within existing Package B, exact message
`docs(topology): register protected installed-unit atomic writer implementation`.
CREATE/DELETE none; MODIFY only this phase, append-only. No runtime implementation,
migration execution, permissions or frozen status changes are performed here.

**Source-backed seams and limits.** `InstalledUnitRepositoryPort` and
`JdbcInstalledUnitRepositoryAdapter` are the accepted private storage seam;
their ordinary transactional register/record/close/read/history methods neither
admit Identity nor attest current company/position. Do not expose or wrap them
as a public authorization boundary. V20261028_002 provides immutable unit identity,
company binding, unit/position half-open exclusion, expected-revision one-time
closure and trigger-appended immutable history. It has no protected command receipt,
actor/native-origin provenance or guarded operation orchestration. Preserve it.

The accepted installed-unit Identity contract defines exactly seven operations.
Installation permissions name installation resources with company and applicable
source/destination facility scopes; station-revision permissions do not apply.
Its Request binds one unitId: this implementation registers same-unit relocation,
not replacement by a different unit. Different-unit replacement remains an explicit
unregistered follow-up requiring descriptor/permission review; do not silently
add an eighth operation, reuse a permission for a different meaning or claim all
owner lifecycle obligations complete. Existing future Package C remains separate.

Company eligibility uses `TopologyProtectedPartyEligibilityContract`; known
manufacturer values use the accepted manufacturer contract independently of
company identity. The latter has single-use read and guarded finalFences, but
no requireCurrent method: reacquire/read fresh issuer tickets before final guard
and compare retained relevant facts under the same locks. Do not modify its
accepted interface or mistake a previously read scalar for current authority.
UNKNOWN manufacturer stays UNKNOWN; no invented Party lookup or fabricated model,
serial or qualification. Known manufacturer/model provenance remains authored
classification, never an attestation of manufacturer-to-equipment-kind fitness.

Position evidence directly binds equipmentTypeId, facility, company, statuses,
real facility generation and private current row images. Unit equipmentKindId
must resolve as a Topology equipment-type reference and equal the selected
position equipmentTypeId for a new installation; this is exact classification
identity, not manufacturer compatibility or engineering qualification. The new
storage seam must guard the actual `hidra_topology_equipment_type` relation for
this reference check. Existing equipment/facility status enums include planned,
inactive and retired states. No owner-approved ACTIVE-only installation policy
has been established: preserve recognized status evidence, temporal recording
and company/type identity without inventing operational readiness. Record/history
is evidence, not authority to operate equipment. Unknown status remains unavailable.

**Next executable bounded implementation, NOT EXECUTED.** Exact message:
`feat(topology): add protected installed-unit lifecycle operations`.

CREATE only:
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/InstalledUnitLifecycleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/ProtectedInstalledUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/InstalledUnitLifecycleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedInstalledUnitAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcProtectedInstalledUnitRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261028_005__topology_installed_unit_protected_operations.sql`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/ProtectedInstalledUnitAdmissionCoordinatorTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/InstalledUnitLifecycleApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedInstalledUnitLifecyclePostgresIntegrationTest.java`

MODIFY only:
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `.github/database-dictionary-ownership.json`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-006.md`

DELETE none. All proposed create paths and migration version005 are unoccupied at
this main;185 earlier migration files remain immutable. No existing repository,
domain, owner provider, Identity reader/permission mapping, datasource, executor
budget property, REST/API, station graph, workflow or frozen registry change.
Use the new opaque port because the existing scalar repository cannot carry
issuer/context/current-image/final-guard authority; do not duplicate domain rules
or export the protected repository as a foreign module contract.

**Required transaction and authority protocol.** The use case has typed commands
and fully materialized results for the seven existing operations, no user callbacks
or injectable authority objects. Native preparation precedes the sole protected
transaction. Caller descriptors are assertions only: exact stored unit/company,
installation/position and actual facility must match locked owner observations
before mutation or disclosure. Acquire Identity, company Party, optional known
manufacturer Party, position sources in sorted unique position-ID order, then
storage/type/receipt fences in fixed relation order; collect the native sample
only after every required fence is held. Missing rows must retain absence fences.
Compare actual holder/connection, PID/XID, database role, native binding, request
and source/destination ownership; require every Identity decision PERMIT. Reject
DENY/INDETERMINATE, ambiguity, unavailable owner, mismatched type/company and stale
revision without disclosing foreign existence. No caller-supplied permission list.
Private pre-sample storage lookups may discover candidate manufacturer/position
descriptors but confer no authority and disclose nothing. Re-read and compare them
after fixed-order fence acquisition; any changed descriptor aborts, rather than
acquiring extra sources out of order. Identity finalFences requires completed
contribution in the actual contract: acquire its native admission first, collect
after owner/storage locks, then obtain/validate its final fence list. Do not call
finalFences on an incomplete Identity ticket or invent a provider bypass.

Use opaque issuer/request/thread/holder-bound one-use tickets, current snapshots,
savepoint-lock detection and suspension/completion invalidation. Context failure
must poison the issuing holder even if swallowed, including foreign-thread calls;
do not let a method interceptor bypass that invalidation. Union exact owner/storage
fences, deduplicate overlapping Party relations by strongest required lock and
verify exact action-specific membership; never add counts of overlapping lists.
Company and manufacturer may be the same Party without treating their distinct
business evidence as interchangeable. The protected reader keeps SELECT-only
access to Party, equipment/facility/generation and equipment-type sources; no
owner helper, role or grant changes are authorized in those sources.

The service alone selects the private credential route. Add only its exact
compiled name and the coordinator's exact name to the executor caller sets.
Coordinator and executor independently pin complete SHA-256 identities of the
actual fixed parameterized DML templates; no prefix, wildcard, arbitrary SQL or
borrowed station permit. Ordinary work remains the existing500ms policy; no new
budget or timeout waiver. Preserve custody/appointment policies and all existing
SQL hashes/source pins. Add exact architecture assertions for only this coordinator
and required exported owner contract roots/nested data, denying sibling consumers
and private provider types; do not widen exported package prefixes.

Register/record/close and same-unit relocate execute authorized provisional DML
only. Relocation closes the expected source revision and appends a new installation
for the same immutable unit, with matching company and exact source/destination
facility permissions, in one transaction. Require the replacement effective start
to equal the source effective close instant; preserve half-open occupancy and
rollback both on conflict. Database exclusion/uniqueness remains authoritative
under concurrent writers. Reads/history and exact mutation replay require fresh
current authorization before disclosure; replay never repeats DML or restores an
older projection. A missing/foreign request must not leak existing receipt content.

Before flush completion, freshly recheck Identity, company/manufacturer evidence,
positions, catalog/ACL/trigger bodies and provisional stored images. Retain minimum
native and manufacturer temporal bounds; authoritative time comes from PostgreSQL,
never System.nanoTime/Instant.now for permission validity. The final scalar SQL is
the sole final statement: verify current clock/strict expiry, exact coordinates,
role, held fence identities, source images, result/receipt/history and commit
intent. Prepare necessary catalog/owner work before that statement. Synchronizations
perform SQL-free commit veto and ticket invalidation; no SQL after sealing or
after-commit compensation. Return success only after observed physical commit;
preserve indeterminate outcome when commit cannot be established.

**Additive schema and audit scope.** Reserve005 for an immutable
`hidra_topology_installed_unit_operation_request` receipt relation, source-bound
request ID/operation/canonical command digest, company/unit/installation descriptors,
expected revision, result evidence, actor/binding/session and database PID/XID/time
origin. Exact request replay and conflicting-ID rejection must be concurrency-safe;
canonical bytes must distinguish nulls, fields, instants and operation, excluding
server-generated record times from caller command identity. DB-clock recordedAt
and existing immutable installation events retain truthful original provenance.
Pin guards preventing receipt update/delete/truncate and malformed origin/expiry;
do not treat scalar audit fields as native authorization. Preserve existing event
trigger semantics, including its invoker privileges and nesting guard; test real
restricted publisher grants rather than assuming trigger ownership. No runtime
role is auto-created or broadly granted; isolated synthetic deployment roles and
explicit least-privilege provisioning remain separate. No outbox/Audit-module
integration or inferred business-approval workflow is added.

Register the new receipt's Topology ownership with this migration and its actual
adapter as source evidence. Preserve the current dictionary until a genuine hosted
catalog exists; the initial Full may fail strict drift after capture. The already
authorized CI-first follow-up is then limited to the actual generated dictionary
and this phase, message
`docs(topology): refresh protected installed-unit operations physical dictionary`.
Never invent catalog facts, disable strict equality or rewrite prior migrations.

**Required authored synthetic acceptance.** Fresh full-Flyway PostgreSQL16 tests
must wire the actual configured Spring/JPA protected executor, separate restricted
ordinary/publisher/source-helper roles and genuine native LOCAL/LDAP/AD/OIDC issuers.
Cover every operation and current/revoked/expired/scoped grant, both relocation
facilities, known versus UNKNOWN evidence, company/type mismatch, unchanged
classification statuses, forged/sibling tickets, direct unadmitted DML, exact SQL
substitution, ambient transaction, thread/suspension/savepoint invalidation and
swallowed-error commit veto. Verify real close-plus-record atomicity, stale revisions,
unit/position races, both writer lock orders/NOWAIT, exact request replay/conflict,
revoked replay denial, immutable original/history/audit, final expiry/source/catalog/
role tampering, no post-guard SQL and indeterminate-commit behavior where applicable.
Tests must assert no leaked foreign payload and no partial head/event/receipt rows.
Keep all previous cases mandatory and register only actual new/modified test cases
and hashes; new timings remain unmeasured, ten shards and balancing stay unchanged.
Run focused unit/PostgreSQL suites and existing architecture/executor guardrails
where available; otherwise report exact missing dependencies under AGENTS11.1.
Require exact-head Full/Fast/Docs and independent case/catalog review before any
acceptance. No authored test or runtime result is claimed by this registration.

**Stage validation and hold.** Documentation/link and whitespace checks, exact
one-file append scope and create-path/migration collision checks precede guarded
publication. Available checks pass:128 documents/8,690 relative links, all921 tasks
and25 phases, whitespace, append-only phase comparison and all nine unoccupied
create paths including migration005. No local Java/JUnit/PostgreSQL execution is
claimed for this documentation-only stage. Observe exact-SHA Docs creation/queue/start
and STOP. Integrated
HAPI-599 remains NOT_IMPLEMENTED/BLOCKED. Different-unit replacement, manufacturer
kind-compatibility policy, public Package C, dependent station/characteristic work,
frontend, simulator/P3, engineering/SITE qualification, release and deployment
are not admitted by this bounded implementation registration.

### HAPI-599 Package B — writer admission blocked by company lock privilege prerequisite — 2026-10-28

**Owner Next; actual preimplementation finding.** Main remains
`9239d39d56a8bcb33267986c862a0faa47ed84b6`; Docs689/run `40736634374`
completed SUCCESS, and its unchanged runtime retains Full960/Fast260 success.
No new CI failure is asserted. Reviewing the registered writer's composed runtime
role reveals a source-backed contradiction before any writer implementation:

- `JdbcTopologyProtectedPartyEligibilityAdapter.acquire` executes
  `LOCK TABLE public.hidra_party_party IN SHARE ROW EXCLUSIVE MODE NOWAIT`
  directly as the reader. SELECT-only PostgreSQL16 credentials cannot take this
  mode. The PostgreSQL16 LOCK documentation explicitly permits SELECT only for
  ACCESS SHARE; UPDATE/DELETE/TRUNCATE permits stronger modes:
  https://www.postgresql.org/docs/16/sql-lock.html .
- The writer registration requires SELECT-only Party sources and forbids changes
  to the existing company provider/source pins. Supplying Party UPDATE to make
  that provider work would violate this registered boundary. Weakening the lock
  would lose writer exclusion; opening another connection would lose the single
  physical transaction. Neither is an authorized repair.
- The manufacturer adapter's fixed isolated-owner lock helper does not fix the
  existing company adapter's direct LOCK statement. Its current GUARD prohibits
  DML on role/assignment/profile but does not prohibit Party-root UPDATE. Its
  accepted PostgreSQL fixture explicitly grants `SELECT,UPDATE` on the Party root
  to its reader. Prior acceptance is valid for those actual tested grants, not
  proof of a SELECT-only Party-root runtime. Correct the earlier stronger prose
  claim; do not rewrite its test evidence or treat it as a new passing experiment.

**Bounded disposition.** Stop before writer/runtime mutation. The preceding
writer allowlist explicitly excludes owner-provider changes; AGENTS section13
requires creating only files listed in the selected task. Register the missing
owner contribution separately within the same fixed Package B, preserving every
accepted provider/source pin and immutable migration. No new package/task, timeout
waiver, permission, status promotion or CI skip. This source finding adds a
prerequisite hold; it does not revoke position/domain acceptance or reopen their
completed implementation. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED.

**This one corrective registration.** Exact message:
`docs(topology): register installed-unit company evidence privilege prerequisite`.
CREATE/DELETE none; MODIFY only this owning phase, append-only. No writer files,
owner implementation, tests, migration, dictionary, inventory or workflow are
changed by this stage. Publication uses expected-head guard and verified blobs,
tree and parent; observe exact-SHA Docs creation/queue/start then STOP.

**Next executable prerequisite, NOT EXECUTED.** Exact message:
`feat(party): export SELECT-only installed-unit company evidence`.

CREATE only:
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyInstalledUnitCompanyEvidenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyInstalledUnitCompanyEvidenceAdapter.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/persistence/TopologyInstalledUnitCompanyEvidencePostgresIntegrationTest.java`

MODIFY only `.github/ci-test-inventory.json` (actual new suite/cases/hash,
unmeasured timing) and this phase (implementation/verification evidence).
DELETE none. No existing company/manufacturer adapter or interface, native Identity,
source pin, role/grant seed, migration, writer, platform executor, API or frozen
registry change. These three proposed paths are unoccupied at the current head.

**Provider protocol.** Export opaque acquire(exact company Party ID), single-use
read, requireCurrent and finalFences; scalar Contribution contains actual Party
ID/status/generation and PID/XID/database role. ACTIVE company eligibility is
independent of manufacturer role/profile: a company need not be a manufacturer.
No company==manufacturer inference or installation permission is issued.

Reuse only the existing Party-private fixed parameterless technical lock function
`public.hidra_party_lock_installed_unit_manufacturer_evidence()` from003, validated
against its exact body/security/search-path/isolated-owner/ACL contract before
and after invocation. It locks four fixed Party tables without reading eligibility
or modifying rows, so the company adapter may use this provider-private capability
without manufacturing a manufacturer classification. Accept the conservative
four-table contention explicitly; do not introduce dynamic table parameters or
change the helper's body/grants. Retain an ACCESS SHARE lock on the real generation
registry. No new DDL or migration version is needed. Reserved writer005 remains
uncreated and must be rechecked before eventual implementation.

Enforce actual SELECT-only runtime on all five Party source relations, including
table/column and inherited DML privileges; prohibit ownership, elevated/member
roles, schema CREATE, RLS/view/partition/inheritance and trigger privileges. Guard
the real Party generation schema, functions/triggers, qualified relation identities
and held lock modes. Read actual current ACTIVE company and positive present
generation, record private row image and physical transaction context, and re-read
them in requireCurrent without issuing a new LOCK or consuming another ticket.
Use issuer/thread/holder/connection/role/PID/XID binding, completion/suspension and
savepoint-lock invalidation; swallowed/context failures poison the original holder
and veto commit without SQL. Ticket-consuming context checks must execute in the
body so foreign-thread misuse cannot escape invalidation via a proxy interceptor.

**Focused acceptance.** Fresh full-Flyway PostgreSQL16 and actual configured
Spring adapter with separate restricted reader and isolated helper owner. Prove
company read/current and actual raw PID/XID/locks with SELECT-only grants, and
assert denied direct Party/registry/role/assignment/profile INSERT/UPDATE/DELETE.
Exercise granted table/column/inherited Party DML rejection, missing/tombstoned/
inactive company, generation/row/role/catalog/helper/ACL tampering, revoked EXECUTE,
both writer lock directions/NOWAIT, same company as manufacturer, non-manufacturer
company, forged/foreign/replayed tickets, thread/suspension/savepoint/end reuse
and swallowed-error rollback. Compose the actual manufacturer adapter in the same
SELECT-only physical transaction and verify both contributions/fence union without
granting Party UPDATE; no scalar/mock issuer substitutes for this composition test.
Keep the old manufacturer suite and its historical grants unchanged and explicit.
Run current inventory/docs and exact-head Full/Fast/Docs; missing local Maven or
PostgreSQL is reported under AGENTS11.1, never relabeled as passing runtime proof.

**Writer resumption rule.** After this prerequisite has genuine exact-head CI and
independent acceptance, resume the previously registered writer with its existing
file allowlist/message, substituting only the newly accepted installed-unit company
contract for the unsuitable station-oriented company provider. Acquire every owner
lock before sealing admission. Manufacturer fresh rechecks can use spare opaque
tickets acquired during initial admission and single-use read just before final
preparation; reacquiring their LOCK statements after sealing is prohibited by the
existing protected datasource. The new company requireCurrent needs no late lock.
Keep all source/expiry/final-guard and receipt obligations. No protected writer,
HTTP Package C, different-unit replacement or integrated acceptance is delivered
by this prerequisite registration.

**Available validation.** Source checks confirm the exact direct LOCK, accepted
manufacturer fixture's Party UPDATE grant and absence of a Party-root DML denial
in its guard; official PostgreSQL16 privilege semantics corroborate the conflict.
Three create paths are absent; old migration/provider bytes remain unchanged.
Documentation/link, append-only scope and whitespace checks precede publication.
No local PostgreSQL/JUnit execution or newly passing synthetic cases are claimed.

### HAPI-599 Package B — SELECT-only company evidence implementation — 2026-10-28

**Selected bounded prerequisite.** Owner Next refreshes exact main
`820b77a9db1eac9853bd3e6d9fed1cd621ec913e`; Docs690/run `40738177482`
completed SUCCESS. Unchanged prior runtime retains Full960/Fast260 acceptance;
no new provider runtime success is inferred from that parent. Execute only the
registered prerequisite, exact message
`feat(party): export SELECT-only installed-unit company evidence`.

CREATE only the registered company contract, JDBC adapter and dedicated PostgreSQL
test class; MODIFY only `.github/ci-test-inventory.json` and this phase. DELETE none.
The old company/manufacturer interfaces and adapters, old manufacturer fixture,
Identity, Topology writer, platform/datasource, API, all185 migration files, physical
dictionary and frozen ROADMAP/PHASES remain byte-for-byte unchanged. No role/grant
seed or new schema is introduced; writer005 remains uncreated.

**Actual owner contract and implementation.** New
`TopologyInstalledUnitCompanyEvidenceContract` exposes opaque acquire, single-use
read, requireCurrent and finalFences with scalar actual company Party ID, ACTIVE
status, positive generation, PID/XID and database role. Scalar construction grants
no authority; diagnostics redact identifiers. An ordinary company need not possess
manufacturer role, assignment or profile. The new non-final Spring repository is
proxyable and requires a genuine writable synchronized read-committed physical
transaction. It rejects table/column/inherited DML on all five Party sources,
ownership/elevated membership, schema CREATE, RLS/view/partition/inheritance and
unreviewed source catalog changes.

The adapter reuses003's exact fixed lock-only helper, guarded before and after its
call. It requires the original body hash, volatility/security/path/signature, an
isolated NOLOGIN/NOINHERIT owner, explicit non-grantable reader EXECUTE and no
PUBLIC/extra grantee. Owner UPDATE on the fixed four relations has no table/column
grant option or extra SELECT/DML capability; generation registry privileges and
ownership are rejected. The reader itself has only SELECT on source relations
and the explicit helper capability. Four strong source locks plus generation
ACCESS SHARE remain in the caller transaction, a conservative contention scope
already explicit in the registration. No dynamic lock target or broader owner
provisioning is added.

Real generation schema/function/trigger pins are retained in the new adapter;
qualified relation/function lookups and observed tableoids prevent stale renamed
relation substitution. Private tickets bind issuer, thread, holder/connection,
PID/XID and database role. The first read retains private SHA-256 root/registry
row images; requireCurrent freshly compares both images and current ACTIVE state,
catalog/ACL/helper and held locks, without new LOCK statements. It neither consumes
another read ticket nor guesses a manufacturer/company relationship. Completion,
suspension, savepoint lock loss, forged/foreign/replayed tickets and wrong contexts
invalidate authority. Ticket-consuming context checks run inside method bodies;
failure poisons the original issuing holder even across a foreign thread.
Synchronizations invalidate tickets and veto swallowed-error commit without SQL.

**Authored tests, not yet runtime-accepted.** The dedicated28-case PostgreSQL16
suite migrates the complete unchanged Flyway chain and wires actual Spring company
and manufacturer beans with a SELECT-only reader and isolated helper owner. It
covers non-manufacturer company, distinct/same manufacturer composition on the
same actual PID/XID, deduplicated fence union, spare-ticket fresh manufacturer
read, missing/inactive/retired/tombstoned company and actual denied direct source
INSERT/UPDATE/DELETE. Privilege cases include Party table/column/inherited DML,
other-source INSERT and missing SELECT, helper EXECUTE/body/security/path/owner/
membership/schema/grant-option changes, generation/helper privileges, RLS/view
replacement and generation function/trigger tampering.

Concurrency/context cases cover raw held locks and commit release, both writer
directions/NOWAIT, read-only/isolation rejection, forged/foreign/replayed/completed
tickets, worker-thread/suspension/savepoint invalidation and swallowed failure.
A separate synthetic witness table verifies rollback of earlier unrelated work;
it is test-only, not a production migration. Deliberate same-transaction source
mutation tests use an isolated schema-owner connection and switch to the actual
restricted reader for observations; they check legal-name row images, generation,
status and role changes. Reflection/scalar rejection checks preserve ticket privacy.
The old manufacturer fixture's historical Party UPDATE grant remains explicit;
only the new composition suite is authored to prove the SELECT-only combination.

Inventory adds exactly this new class,28 exact cases and its actual source hash,
baseline_seconds=0 explicitly unmeasured. All prior entries, case identities,
timing/provenance, four historical exclusions and ten deterministic PostgreSQL
shards are unchanged. Active inventory is497 classes (377 unit/architecture,
120 PostgreSQL),3,697 expected cases. No skip, reclassification or measured timing
is invented.

**Actual available validation.** All three new Java sources parse. The actual new
contract and existing relation-fence record compile with the available Java17
compiler; seven scalar/redaction probes pass. This is not full Java21/Spring/JUnit
validation. Current source/inventory validation and107 Python validator self-tests
pass (35 inventory,53 full evidence,19 docs). Existing inventory entries/provenance
compare unchanged after removing only the new entry. Focused command
`mvn -q -Dtest=TopologyInstalledUnitCompanyEvidencePostgresIntegrationTest test`
exits127: `mvn: command not found`. No local PostgreSQL/JUnit execution or passing
new runtime cases are claimed; no full tool/service environment is provisioned.
Documentation/link, exact scope and whitespace checks precede guarded publication.

**Remaining hold.** This provider is implemented, not CI-accepted. Publish the five
registered paths with expected-head guard and verified blobs/tree/parent; observe
exact-SHA Full/Fast/Docs creation/queue/start and STOP. Independent exact-case and
SELECT-only composition evidence must pass before accepting this prerequisite and
resuming the registered writer. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED; writer,
public Package C, different-unit replacement, manufacturer-kind qualification,
dependent tasks, frontend, simulator/P3, SITE, release and deployment remain pending.

### HAPI-599 verification hold — Full961 native authorization fixture lock isolation — 2026-10-28

**Owner Fail; exact failed gate.** Main
`150e94713e68a15ef224b001b079343a4408302f` has Full961/run `40740921834`
FAILED; Fast261 and Docs691 SUCCESS. Static, Java unit/architecture, API/dictionary
and nine PostgreSQL shards passed. Shard8/job `123001978305` failed the unchanged
Identity `ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest`:
six cases, zero assertion failures, one error, no skips. Its publication-actions
case failed at line856/native admission with SQLSTATE55P03 acquiring SHARE ROW
EXCLUSIVE NOWAIT on `public.hidra_identity_permission`, before any native permit
or final guard. The dependent Full gate failure and absent successful lane receipt
are consequences; no timeout or dictionary failure is indicated.

The new SELECT-only company provider's28 cases passed, zero errors/failures/skips,
12.094s observed, in successful shard4. Downloaded artifact `12839757963` ZIP SHA-256
`bb00b51a58e110fc9d4718707494bd4bfd8adcc3f9b77fa0654fd84625bf8ec7`
matches GitHub metadata; independently checked its14 listed file hashes, exact
source/run/attempt and raw XML case identities against the unchanged aggregator.
Failed-shard diagnostics artifact `12839631983` ZIP SHA-256
`73ead3105f907cae3f2b2a2e58d8bf2b2793cd07b9ddc3fe9471bccba19f9b2f`
also matches metadata. Passing company cases are focused evidence, not acceptance
of a failed Full run or permission to start the writer.

**Established fact versus inference.** The failed lock proves a competing backend
held an incompatible relation lock. Its PID/type/mode were not retained, so the
original blocker is unidentified. This isolated class runs synchronous native
fixtures and repeatedly deletes permission/grant data, while its disposable server
used default automatic maintenance. PostgreSQL16 VACUUM/ANALYZE takes SHARE UPDATE
EXCLUSIVE, which conflicts with the required admission mode. Background maintenance
is therefore a plausible source, not a proven cause. References:
https://www.postgresql.org/docs/16/explicit-locking.html and
https://www.postgresql.org/docs/16/runtime-config-autovacuum.html .
Do not label production locking incorrect or claim that the original blocker has
been identified. The security rejection remains correct under contention.

**Bounded repair message and scope.** Exact message:
`test(identity): isolate native authorization fixture maintenance locks`.
CREATE/DELETE none. MODIFY only:
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedEngineeringDraftAuthorizationPostgresIntegrationTest.java`
- `.github/ci-test-inventory.json` (only that existing suite's cases/source hash)
- `doc/roadmap/TASKS_HPS-006.md`

Disable ordinary autovacuum only in this class's short-lived PostgreSQL16 container
using its server startup command. This is deterministic synthetic-fixture isolation,
not a production database recommendation or a change to native admission. PostgreSQL
still retains wraparound-safety behavior; no maintenance/operational qualification
is claimed. All six original cases and their four real producer/action paths remain.
No retry, success fallback, sleep, skipped assertion, weaker lock or deadline change.

Add one regression asserting the test-only setting and an actual separate backend
holding maintenance's SHARE UPDATE EXCLUSIVE mode on the permission relation. Native
admission must reject with55P03 and REJECTED_BEFORE_COMMIT, create no guard receipt,
then accept a freshly prepared native request only after explicit blocker rollback.
This preserves tested conflict rejection instead of treating contention as success.
The test harness also retains a post-rollback permission-lock snapshot on execution
failure (PID/mode/backend type/state/wait event only, no query or credential text).
It runs after the executor has closed the failed scope, grants no authority and
rethrows the original error; an empty snapshot does not identify a vanished blocker.
This diagnostic does not authorize SQL after a successful final guard.

The existing suite now has7 cases; total497 active classes and3,698 expected cases.
New company source/test bytes and all its28 cases are unchanged. Previous inventory
timings/provenance, four historical exclusions and ten shards remain unchanged;
all production sources, migrations, dictionary, workflow and native/final guards
are untouched. The fix remains a CI-first fixture hypothesis until hosted results.

**Available validation and hold.** Changed Java source parses; inventory validation
and88 inventory/full-evidence validator self-tests pass. Focused Maven command for
the changed and company suites exits127 (`mvn: command not found`); no local JUnit
or PostgreSQL success is claimed. Documentation/link, source/case/scope preservation
and whitespace checks precede guarded publication. Verify parent/tree/blobs, observe
exact-SHA Full/Fast/Docs startup and STOP. If the conflict recurs, use retained actual
backend evidence before another repair. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED;
company acceptance, atomic writer, Package C, SITE, release and deployment stay held.

### HAPI-599 Package B — SELECT-only company evidence acceptance — 2026-10-28

**Owner Next; exact-head independent acceptance.** Main
`98d77d88fc0551826427d32bb96ffc835bf27545` has Full962/run `40743859110`,
Fast262/run `40743859161` and Docs692/run `40743859260` completed SUCCESS.
All ten required PostgreSQL shards, unit/architecture, static, API/dictionary and
Full gate passed. Development mode, cached dependencies, attempt1 and
serial_oracle=false are explicit; the separate serial job is skipped by design,
release_eligible=false. No release, deployment or operational acceptance follows.

Downloaded full bundle artifact `12841342346` has ZIP SHA-256
`3fa890ab2795a1a302cd5464e648136ea81ba1ac29dc7a7393de18af857fe4ee`,
matching GitHub metadata. The unchanged full-evidence aggregator independently
reproduces gate.json byte-for-byte against current source/inventory, verifying
all504 listed file hashes and exact source/run/attempt. Raw XML case identities
reconcile all497 active classes and3,698 exact cases, zero failures/errors/skips.
Four historical exclusions and ten required shards remain unchanged; no timing
inventory is rewritten from this observation.

The actual SELECT-only company suite passes28 cases (18.548s observed), including
non-manufacturer company and same/different manufacturer composition, actual
PID/XID and locks, denied source DML, privilege/catalog/helper changes and lifecycle
poisoning. This supplies the previously missing combined SELECT-only runtime
evidence. The repaired native engineering-authorization suite passes7 cases
(45.914s), including all six original cases and the explicit separate-backend
maintenance-mode NOWAIT rejection/no-receipt/release regression. The unchanged
Organization appointment concurrency suite passes82 cases (355.012s).

Full961 remains a failed historical run. Its competing permission-table backend
was not identified; this success does not prove that autovacuum caused the earlier
conflict or establish permanent stability. Test-only maintenance isolation and
post-rollback diagnostics remain explicit. Production NOWAIT locks, native expiry,
processing budgets and final guards were never weakened. The earlier OIDC
appointment rejection likewise remains unidentified historical evidence, not a
new root-cause claim or a reason to skip its mandatory cases.

Same-run dictionary receipt is checked=true on the accepted SHA: PostgreSQL16.15,
185 migrations,470 mappings,531 relations, zero unresolved owners. Source bundle
SHA-256 `cc3a447e4bf313777b59a85124f57e2d244973ee7ff53a9da24155dc4a94b74c`;
catalog SHA-256 `3ba7b302a857d94f43ba729311f164f51c23c47ab4064bd8a60a5b1439be297f`;
checked render SHA-256 `686fae72408ae4eb802a6b24a413098b6f9e5e08ed55bb269dcc2edb4f449779`.
API/base compatibility passed against `7a6d93bb8709b0bfc2df3d9cb4b9ea68e68802b6`;
current API SHA-256 `2e984876631c37c04498a72a3426c199df902f7c4b9a19375a70b3bc08cb4757`.
No new DDL/catalog refresh is required by this provider.

**Bounded acceptance and hold update.** Accept the new Party-owned company
contribution and its demonstrated SELECT-only composition within tested backend
development scope. Clear its prerequisite CI/evidence hold and the Full961 gate
hold for progression. Package A, manufacturer/Identity/position contributions
remain accepted; do not repeat them. This accepts no protected atomic writer,
HTTP operation, engineering readiness or integrated Package B/C. HAPI-599 remains
NOT_IMPLEMENTED/BLOCKED in frozen ROADMAP/PHASES pending those actual deliverables.

**This one selected acceptance commit.** Exact message:
`docs(party): accept installed-unit SELECT-only company evidence`.
CREATE/DELETE none; MODIFY only this phase, append-only. Independent evidence
reproduction, documentation/link, scope and whitespace checks precede guarded
publication. No local JUnit/PostgreSQL rerun is claimed. Verify parent/tree/blob,
observe exact-SHA Docs creation/queue/start and STOP.

**Next executable stage.** Resume the registered writer implementation, message
`feat(topology): add protected installed-unit lifecycle operations`, with the exact
nine CREATE/seven MODIFY allowlist above and the explicit company-contract
substitution in the prerequisite resumption rule. Use the newly accepted
`TopologyInstalledUnitCompanyEvidenceContract`; no old company provider/source-pin
change is needed. Recheck live migration inventory before creating005. Preserve
same-unit relocation scope, exact permissions, provisional SQL pins, issuer/context
binding, replay/audit, current-owner checks and sole final commit guard. Do not
start it within this acceptance stage. Different-unit replacement, public Package C,
manufacturer-kind qualification, dependent tasks, frontend, simulation/P3, SITE,
release and deployment remain separately pending.

### HAPI-599 Package B — close writer equipment-type reference integrity registration — 2026-10-28

**Owner Next; source review before runtime mutation.** Exact main
`ce886b328e56f4c13780fd4b7d76f0f6a3dba53f` has Docs693/run `40753082049`
SUCCESS. Its unchanged runtime retains independently accepted Full962/Fast262.
Company evidence is accepted; no company-provider work is repeated. The remaining
writer review finds a concrete unregistered schema requirement, rather than a CI
failure or a missing local environment.

V20261028_002 defines immutable unit `equipment_kind_id` as nonempty varchar80
but has no equipment-type FK. HMR-054 retired legacy equipment-kind enum columns;
current classification is `hidra_topology_equipment_type.id`, as already selected
in this writer's registration. V20260929_001 constrains existing Equipment and
EquipmentTypeVersion references, not the later installed-unit table. The accepted
position helper004 locks equipment/facility, not equipment-type catalog rows.
The writer requires SELECT-only catalog access and had reserved005 for receipts
without explicitly registering the required existing-unit reference constraint.

**Concrete race and chosen correction.** Registration of a standalone unit can
read an unused type, then race a separate transaction deleting that type. ACCESS
SHARE does not exclude ordinary row changes; an application existence/final check
alone cannot retain the reference through physical commit. Granting type UPDATE
just to obtain a stronger lock would violate the registered source privilege
boundary. Do not create an unregistered helper or fabricate a type generation.
Use the database's native owned-reference integrity instead. Before runtime work,
make this additive schema requirement explicit in the existing005 scope.

**This one corrective registration stage.** Exact message:
`docs(topology): pin installed-unit equipment-type reference integrity`.
CREATE/DELETE none; MODIFY only this phase, append-only. No SQL migration, unit,
provider, writer or test source is created in this stage. This closes technical
scope before changing an existing relation, following AGENTS file-scope controls;
it does not implement or accept the protected writer.

**Amendment to the next writer implementation, not a new package/provider.** Keep
the exact nine CREATE/seven MODIFY allowlist and message
`feat(topology): add protected installed-unit lifecycle operations`. In the already
listed `V20261028_005__topology_installed_unit_protected_operations.sql`, add:

```sql
ALTER TABLE public.hidra_topology_installed_unit
    ADD CONSTRAINT fk_installed_unit_equipment_type
    FOREIGN KEY (equipment_kind_id)
    REFERENCES public.hidra_topology_equipment_type(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE;
```

The constraint is immediately validated, not NOT VALID or deferred. Existing
orphan kinds must cause migration failure and explicit evidence review; no silent
delete, inferred kind remap, manufactured catalog seed or history rewrite. Preserve
all185 prior migration bytes. This is an owned Topology reference, not a cross-module
FK, manufacturer-kind compatibility rule, ACTIVE-only eligibility policy or Assets
ownership change. Existing domain evidence classifications remain unchanged.

The protected adapter must verify the actual validated, non-deferrable FK identity,
child/parent relation and key columns, RESTRICT actions and enabled native referential
triggers, alongside its type catalog guards. Application comparisons still bind
the unit's exact kind ID to the selected position's actual equipmentTypeId. Runtime
credentials retain SELECT-only catalog access; the FK retains parent-key integrity
for the owned write. No late lock, owner helper change or additional public API.

Use only the already registered new lifecycle PostgreSQL suite for fresh-chain
constraint validation, unknown-type rejection with SQLSTATE23503, referenced-type
deletion/key-change rejection, both deletion/registration transaction orders and
rollback. Verify the actual restricted publisher can insert a valid reference
without type UPDATE/DELETE grants, and that disabling/substituting the FK or its
native guards is rejected before disclosure/commit. No runtime result is claimed
before those tests exist and hosted CI executes them.

In the allowed ownership JSON, retain existing source evidence and add actual005
constraint provenance to the unit relation as appropriate, plus the already planned
receipt ownership entry. The genuine catalog/dictionary follow-up remains the same
registered two-file refresh; capture new FK metadata from CI rather than inventing it.
No separate type-provider implementation or additional preliminary stage is selected.

**Available checks and next execution.** Confirmed current FK inventory/source
columns and absence of005; documentation/link, append-only scope and whitespace
checks precede guarded publication. No local PostgreSQL/Java execution or new case
success is claimed for this one-file stage. Observe exact-SHA Docs startup and STOP.
Next resumes the registered writer with this concrete005 requirement and accepted
company contract. HAPI-599 remains NOT_IMPLEMENTED/BLOCKED; writer, public Package C,
different-unit replacement, dependent tasks, qualification, release and deployment
remain pending.

### HAPI-599 Package B — protected installed-unit lifecycle implementation — 2026-10-28

**Selected bounded runtime stage.** Owner Next refreshes main
`1713955a61059e74b2a67ca5fba8fad378c812a8`, Docs694/run `40758860718`
SUCCESS. Unchanged prior runtime retains independently reviewed Full962/Fast262.
The accepted company prerequisite and explicit equipment-type FK amendment are
applied in this single registered stage, exact message:
`feat(topology): add protected installed-unit lifecycle operations`.

CREATE the nine registered paths: inbound typed lifecycle use case, protected
repository port, lifecycle service, admission coordinator, protected JDBC adapter,
V20261028_005 migration, two application unit suites and dedicated PostgreSQL suite.
MODIFY only the registered seven paths: protected executor, its unit suite, existing
station admission boundary guardrail, global architecture guardrail, reviewed
dictionary ownership JSON, CI inventory and this phase. DELETE none. No old provider,
Identity reader/permission mapping, domain model/private repository, datasource,
budget configuration, controller/OpenAPI, workflow or frozen registry change.
All185 earlier migration files and the physical dictionary remain unchanged.

**Implemented owner workflow.** Typed immutable commands cover REGISTER_UNIT,
READ_UNIT, RECORD_INSTALLATION, CLOSE_INSTALLATION, same-unit RELOCATE,
READ_INSTALLATION and READ_HISTORY. Their constructor rejects cross-operation
payloads, padded/oversized references, stale-revision shapes, non-microsecond
business instants and malformed intervals. Caller source/destination descriptors
are assertions, never authority. Private pre-sample storage hints are re-read after
fixed-order owner/storage fences; changed hints, foreign/missing identity, type,
company or position bindings reject without returning foreign payloads.

The compiled service prepares native authentication before opening its sole protected
credential route/physical transaction. The coordinator acquires Identity, accepted
SELECT-only company, optional known manufacturer, sorted unique positions, then
owned storage/type fences. All owner samples/contributions follow lock acquisition.
Native decisions must be PERMIT; company/manufacturer and position facts must agree
with exact request references and actual PID/XID/database role. Company need not
be a manufacturer; the same Party can provide both distinct contributions. UNKNOWN
metadata remains UNKNOWN. Existing recognized planned/inactive position classifications
are preserved as evidence, not relabeled operational readiness. Exact equipment-type
ID equality is classification identity, not manufacturer compatibility or engineering
qualification. No Assets state is touched by its optional neutral reference.

The actual issuer fence union must equal the compiled action-specific expectation;
overlapping Party/position sources deduplicate by strongest mode. Private tickets
bind issuer/request/thread and actual holder/connection; forged, reused, suspended,
foreign-thread and wrong-context use poisons the original transaction. Provider
requireCurrent checks and spare manufacturer tickets re-read current evidence before
final preparation without late LOCK statements. Native/manufacturer validity bounds
are retained as a minimum and enforced against one final PostgreSQL clock sample.
Time spent in the processing budget never extends source/grant/policy validity.

The new service/coordinator alone join the exact compiled caller sets. Coordinator
and executor independently pin all four complete parameterized SQL texts by SHA-256:
unit insert, installation insert, expected-revision closure and immutable receipt
insert. A one-use provisional permit bounds DML; no SQL-prefix exception, untrusted
callback or borrowed station permit. The new exact-caller technical
requireInstalledUnitFinalPreparation hook permits only this coordinator's checks in
FLUSHING, preserving the general requireAdmission OBSERVING rule. All old SQL pins
and processing policies remain; this owner uses the unchanged standard500ms budget.

**Storage, replay and commit.** Closure and adjacent replacement installation use
one transaction and the same physical unit, with source/destination facility grants.
Native exclusion/uniqueness and optimistic revision remain authoritative; conflict
rolls back closure, immutable history and receipt together. New recordedAt values
come from the real database sample, not caller time. Original identity and event
provenance are never overwritten. Exact request replay requires fresh authorization,
exact canonical bytes and verified existing result/ledger; it appends nothing,
never restores an old projection and explicitly marks its historical response.
Conflicting or foreign request identity fails closed.

Versioned canonical command/result bytes distinguish nulls and field boundaries,
use strict UTF-8 and immutable timestamp representations, enforce bounded payloads
and SHA-256 integrity, and reject trailing/noncanonical data. SQL literals needed
for frozen guard predicates use hex-encoded UTF-8, avoiding quote/backslash ambiguity.
Caller data cannot become a SQL template. Actor/binding/session, operation/request,
company/unit/installation, expected revision, command/result digests, database role,
PID/XID and sampled/expiry times are retained in the new immutable receipt.

Catalog and source images are observed under held fences before owner sampling,
compared again after managed flush and in the final SQL. These observations are
integrity witnesses, not constructible business authority. Catalog checks include
own/source role privileges, relation identity/kind/RLS/inheritance, old trigger
bodies, exact native temporal exclusions, validated keys, receipt guards and the
new FK/native referential triggers. Owner data images bind exact company/manufacturer
roots/generations, applicable position/facility rows/generations and the real Identity
mapping registry; no equipment generation is invented. Source fields unused by
unit-only operations do not add position locks or implied facility scope.

After exact provisional result/history verification, freeze retains owned row images,
source/catalog images, coordinates, role, origin and exact fences. The final scalar
statement rechecks strict time bounds, PID/XID/session/current role, catalogs, held
locks and all retained images. Synchronizations veto commit and invalidate tickets
without SQL; no SQL is permitted after sealing. The service returns materialized
records only after observed physical commit and preserves executor indeterminate
failure behavior. No after-commit compensation is introduced.

**Additive schema and privileges.** The new186th migration adds the validated,
non-deferrable fk_installed_unit_equipment_type with UPDATE/DELETE RESTRICT, and
the new immutable operation-request relation/guards. Orphan kinds cause migration
failure; no remap, fake type seed, historical cleanup or NOT VALID waiver. Native
referential integrity protects parent identity through commit without type DML.
The runtime keeps SELECT-only access to Party/position/type sources. Its private
publisher needs INSERT/UPDATE on the four owned relations for native strong locks;
immutable triggers and independent SQL pins prohibit identity/history/receipt rewrites.
Existing event append triggers retain invoker semantics, including required event
INSERT privilege. Ordinary credentials cannot mutate these owned relations or be
a publisher member. Roles/grants are synthetic test provisioning only; the migration
does not create production roles, broaden source helper grants or seed permissions.

Reviewed ownership JSON registers the actual receipt relation/source and adds005
FK provenance to the unit. The existing physical dictionary is intentionally retained
until hosted capture. Registered follow-up remains
`docs(topology): refresh protected installed-unit operations physical dictionary`,
limited to the actual generated dictionary and this phase. Initial CI may reveal
strict drift after capture or an actual runtime defect; repair real failures first.
Never infer physical catalog facts, alter the generator or waive strict comparison.

**Authored regression scope; runtime acceptance pending.** New unit suites contain
9 coordinator and4 service cases; existing architecture/executor suites each gain
one exact boundary case. The dedicated22-case PostgreSQL16 suite migrates full
Flyway and wires actual Spring/JPA protected execution with separate ordinary,
publisher and isolated helper owners. Only external directory/source transport is
synthetic. One case executes every operation with LOCAL/LDAP/AD/OIDC native producers;
others cover known/UNKNOWN manufacturer, company/type/facility mismatch, revoked
read/replay, conflict/stale request/revision and atomic relocation rollback. They
exercise real source privileges, ambient transaction, native NOWAIT, expired final
bounds, actual SQL after guard, role/generation changes after freeze, missing FK/
disabled trigger, both unmounted-kind race orders and SQLSTATE23503. Real worker,
suspension and swallowed savepoint-control failures must roll back earlier provisional
writes. Native qualification alone is warmed before each fixture; no lifecycle write
bypasses the protected executor or its standard budget. Existing provider/context
and platform indeterminate-commit tests remain mandatory.

Inventory changes only actual six suite hashes/case lists and three new classes;
all old timing, classification and scheduling provenance remains. New timings are
unmeasured baseline0. Active inventory:500 classes (379 unit/architecture,
121 PostgreSQL),3,735 expected exact cases, four unchanged historical exclusions,
ten required deterministic PostgreSQL shards. No case is skipped or reclassified.

**Actual local validation and limits.** All12 new/modified Java sources parse.
The new writer/domain/owner contract sources compile against minimal framework API
stubs with available Java17; five probes execute actual codec/canonical/pin logic.
These checks are limited interface/logic proof, not full Java21/Spring/JUnit or
PostgreSQL verification. Inventory, offline OpenAPI and125 validator self-tests pass
(35 inventory,53 full evidence,18 dictionary,19 docs). Focused Maven exits127:
`mvn: command not found`; no service environment is installed merely to reproduce CI.
Documentation/link, exact16-file scope, unchanged old migrations/providers, source
pin and whitespace checks precede guarded publication. No new runtime pass or
physical catalog capture is claimed locally.

Publish exact blobs/tree/parent with expected-head guard; observe exact-SHA Full/
Fast/Docs creation/queue/start and STOP. This writer is implemented, not CI-accepted.
Integrated HAPI-599 remains NOT_IMPLEMENTED/BLOCKED pending runtime/catalog and
independent Package B/C acceptance. Different-unit replacement, manufacturer-kind
qualification, public API, dependent tasks, frontend, simulation/P3, SITE, release
and deployment remain separately pending.

### HAPI-599 Package B — exact-head CI admission/architecture repair — 2026-10-29

**Failure evidence and bounded authorization.** Owner Fail selects repair of main
`9cefca5f0c21656a1762909301d03a08facc2e46`, not a dependent stage. Full963/run
`40771139283` and Fast263/run `40771139398` failed; Docs695/run `40771139280`
succeeded. Full unit/architecture job `123108335051` executed2,204 cases with one
failure: ForensicRemediationClosureTest.crossModulePrivateImportsRemainClosed
rejects the new coordinator's TopologyInstalledUnitAuthorizationContract import.
PostgreSQL shard8/job `123108335524` executed204 cases with6 failures/11 errors;
all17 failing cases belong to the new lifecycle suite. Successful paths stop in
company acquisition with suppressed SQLSTATE25000: admission SELECT requires
executeQuery for SELECT public.hidra_party_lock_installed_unit_manufacturer_evidence().
Later fault callbacks consequently are not reached. Nine other PostgreSQL shards
succeeded. These observations do not prove any later writer path or identify a
500ms timeout defect.

Exact repair message:
`fix(topology): execute installed-unit owner lock queries through admission`.
CREATE/DELETE none. MODIFY only the three existing adapters
JdbcTopologyInstalledUnitCompanyEvidenceAdapter,
JdbcTopologyInstalledUnitManufacturerEvidenceAdapter and
JdbcInstalledUnitPositionOwnershipAdapter at their registered Party/Topology
infrastructure persistence adapter paths; src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java;
.github/ci-test-inventory.json; this phase. This expands the earlier writer's
preserve-provider restriction only for the demonstrated execution-method integration
error. No contract, SQL text/function body, lock order/mode, grant, native issuer,
source pin, datasource enforcement or timing policy changes.

**Repair.** All three fixed helper SELECTs use JdbcTemplate.queryForObject with a
row mapper consuming their single void result, which dispatches executeQuery.
The same helper functions and subsequent ACCESS SHARE generation locks remain.
Company fails first in this head; manufacturer and position contain the identical
incompatible execute call and must be repaired before those paths can execute.
The protected datasource's admission restriction remains unchanged. The forensic
scanner admits only the exact installed-unit coordinator and exact public Identity
contract root/nested types. In the existing case, positive checks and sibling-root,
sibling-consumer and private-provider negatives preserve the boundary. Only that
suite's source hash changes in inventory; all500 active classes/3,735 cases and ten
PostgreSQL shards remain mandatory.

**Dictionary and acceptance hold.** The API lane captured a genuine migrated
PostgreSQL16.15 catalog:186 migrations,470 JPA mappings,532 relations, zero unresolved
owners. The strict comparison still fails as explicitly anticipated for005.
Source bundle SHA256 efd5680512a4a8627dfc4ea5ae27a1725089963f7bf5f646079f6d3699383ec4;
catalog e7a1694ba837e92349c3ccf0eef350ee45f2a660fd4b74f1c95ddebc5ba29821;
generated dictionary f22d09141f9ab4ce62369f598fb42b615d21cfc4006086f5559cb284eff417bc.
These are capture-log facts, not independent artifact verification or acceptance.
The physical dictionary remains unchanged in this runtime repair. After actual
runtime defects are cleared, execute the registered truthful artifact verification/
refresh under `docs(topology): refresh protected installed-unit operations physical dictionary`.
Do not alter the generator or waive strict comparison. No release publication,
Package B acceptance, Package C/API or dependent task is authorized by this repair.

**Local validation and publication rule.** Four changed Java sources parse with the
available Java17 compiler. All nine actual forensic source-check methods execute
successfully using a minimal assertion/annotation harness; this is limited source
verification, not Maven/JUnit runtime proof. Inventory, documentation/link validation,
offline OpenAPI and125 validator self-tests pass (35 inventory,53 full-evidence,
18 dictionary,19 docs). Focused Maven cannot run: exit127, mvn: command not found.
No environment is provisioned solely for local verification. Check bounded diff,
unchanged migration/dictionary/platform/security sources and whitespace, then publish
verified blobs/tree/parent directly to main with expected-head guard. Observe
applicable exact-SHA CI creation/start and STOP. Writer implementation remains
unaccepted pending runtime and dictionary gates; HAPI-599's integrated frozen
NOT_IMPLEMENTED/BLOCKED baseline remains. Further hosted failures select repair,
never an automatic budget increase, skipped test or dependent implementation.

### HAPI-599 Package B — observed work-cap query repair — 2026-10-29

**Exact-head failure.** Owner Fail selects main
`3d3c31d77b845e7d54358a228b51bda014080935`. Full964/run `40816904394` failed;
Fast264/run `40816904518` and Docs696/run `40816904848` succeeded. Unit/architecture
and nine PostgreSQL shards passed, establishing that the preceding exact export
and executeQuery fixes cleared their demonstrated gates. PostgreSQL shard8/job
`123259994582` executed204 cases,3 failures/11 errors, all14 in the new lifecycle
suite. Eleven error traces explicitly report the aggregate500ms cap, including
222 statements/507.7ms in PROVISIONAL,297 statements/509–532ms in FLUSHING,
298 statements/592–610ms in SEALED and343 statements/542ms for known manufacturer.
Two assertions could not reach finalGuard. The savepoint case reached its fault
callback but incorrectly expected RuntimeException: the datasource correctly
rejected setSavepoint with SQLException/SQLSTATE25000, savepoint/abort prohibited.
This is an assertion-type defect, separate from the observed work-cap overruns.

**Registered bounded repair.** Exact message:
`perf(topology): batch installed-unit admission catalog observations`.
CREATE/DELETE none. MODIFY only these seven existing paths:

- src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyInstalledUnitCompanyEvidenceAdapter.java
- src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.java
- src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcInstalledUnitPositionOwnershipAdapter.java
- src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcProtectedInstalledUnitRepositoryAdapter.java
- src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedInstalledUnitLifecyclePostgresIntegrationTest.java
- .github/ci-test-inventory.json
- doc/roadmap/TASKS_HPS-006.md

The narrow existing-provider amendment permits batching current observations only.
Their complete GUARD, OWNER_LOCK_GUARD, HELD and EVIDENCE literals remain byte-exact.
At acquisition, catalog/helper predicates share a scalar query; post-lock
observation also requires the existing held-fence predicate. Every consumed ticket
checks issuer/thread/holder/connection locally, validates trusted search path in a
separate fully qualified query before parsing predicates with existing unqualified
built-in names, then observes PID/XID/role, catalog/helper and held fences together. Read/current methods still
check before and after the evidence read; no observation is cached or waived.
Manufacturer's post-read/fence observations now also check trusted search path.
Company/position image and generation comparisons, all failure poisoning and SQL-free
synchronizations remain. No public contract, helper body, grant or lock acquisition
order/mode changes. The coordinator's initial qualification, fence union and full
before-DML/after-flush owner rechecks remain byte-exact.

Storage preserves the separate trusted search-path preflight and combines its
catalog/role and ticket-coordinate/owned-fence checks into one current query. Freeze compares owned-state, source and catalog
images in one statement, preserving all three comparisons and baseline fence equality.
Held-lock observation matches a VALUES relation/mode set against one pg_locks
reference and counts distinct required relation identities, rejecting empty/duplicate
requirements and accepting only the exact retained modes. Catalog imaging materializes
the required relation OIDs once per observation instead of repeating every lookup
across class/constraint/trigger/function subqueries. All original catalog fields,
role/member images, sorting, source and owned-state content remain. The final
statement still rechecks all retained evidence and one strict native clock sample;
no SQL is introduced after sealing. Four DML templates/hashes remain byte-exact.

**Regression and limit.** Correct only the existing swallowed-savepoint assertion to
SQLException, exact25000 and the rejection message; its outer execution rejection,
callback verification and no-effects checks remain. Add one native PostgreSQL case
for the optimized lock observation: all exact requirements succeed, wrong mode and
an additional unheld relation fail, and duplicate requirements reject before query.
Its separate administrative connection is isolated SQL evidence, not lifecycle
authority. All existing22 lifecycle cases remain mandatory; the suite now has23.
Inventory changes only its actual source hash/case list, retaining timings,
classification and ten-shard scheduling. Active inventory remains500 classes,
now3,736 cases; no skip, reclassification or workflow modification.

The observed500ms cap is a finite server work policy, independently reviewed in the
prior owner-requested policy record; it does not extend native evidence validity.
This repair targets avoidable query work and preserves that cap, custody/appointment
budgets, datasource enforcement and final authority deadlines. New runtime timing
and a successful complete lifecycle remain unproved until hosted CI; do not claim
that batching has already met the cap or that all later failures are known.

**Dictionary/acceptance hold.** The API lane's capture log reports186 migrations,
470 mappings,532 relations, zero unresolved owners on PostgreSQL16.15; reviewed
strict comparison remains pending. Source bundle efd5680512a4a8627dfc4ea5ae27a1725089963f7bf5f646079f6d3699383ec4;
catalog3cd3c3a55b73b62548f511211878c602779b9e02d29cf4c1dc183f5af9856e43;
generated dictionary a8381307b9838b1bb7112861e0e14b56774df114558ec37b52c356411b900c72.
These are log observations, not independently verified artifacts. Preserve the
physical dictionary until the registered genuine-catalog verification/refresh under
`docs(topology): refresh protected installed-unit operations physical dictionary`.
No generator, immutable migration or acceptance waiver. Writer and integrated
HAPI-599 remain unaccepted; Package C/API, dependent tasks, frontend, simulation/P3,
SITE, releases and deployment are not selected.

**Actual local validation.** Five changed Java sources parse under available Java17.
Actual writer, three changed owner adapters and their domain/public-contract sources
compile against minimal framework API stubs; five actual codec/canonical/pin probes
pass. Nine actual forensic source-check methods pass with a minimal assertion harness.
These are limited source/logic checks, not full Java21/Spring/JUnit/PostgreSQL proof.
Inventory, documentation/link validation, offline OpenAPI and125 validator self-tests
pass (35 inventory,53 full-evidence,18 dictionary,19 docs). Focused Maven exits127,
mvn: command not found. No environment is provisioned for local verification.
Check seven-file scope, preserved guards/pins/migrations/dictionary/budgets and
whitespace; publish verified tree/blobs/parent to main with expected-head guard,
observe applicable exact-SHA CI creation/start and STOP. Further failures require
repair before independent runtime/catalog acceptance or dependent work.

### HAPI-599 Package B — savepoint assertion compilation repair — 2026-10-29

Owner Fail selects main `67320ada19f68e7875ff43445150a02200d0dae6`.
Full965/run `40831498840` and Fast265/run `40831498878` failed;
Docs697/run `40831499085` passed. Full unit job `123306527088` and PostgreSQL
shard8/job `123306527382` both identify the same testCompile error at the lifecycle
suite's line1108: assertThat(SQLException) is ambiguous between Iterable and generic
assertion overloads, and IterableAssert has no hasMessageContaining. SQLException
implements Iterable<Throwable>. Every Maven test lane compiles this same source;
their failures do not establish ten independent runtime regressions. No lifecycle
runtime/performance acceptance is obtained from this head. The API/dictionary lane
also retains the separately pending strict catalog comparison.

Exact bounded repair message:
`fix(topology): disambiguate savepoint rejection message assertion`.
CREATE/DELETE none. MODIFY only
src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedInstalledUnitLifecyclePostgresIntegrationTest.java,
.github/ci-test-inventory.json and this phase. Assert on rejected.getMessage() as a
String and require the same savepoint/abort prohibited message. Preserve assertThrows
SQLException, exact SQLSTATE25000, the intentionally swallowed failure, real final
preparation, outer protected rejection, reached-callback verification and no-effects
assertions. No production/query/budget/migration/dictionary/workflow change. The
23 lifecycle cases,500 active classes,3,736 required cases and ten shards remain;
only the suite source hash changes in inventory.

Local syntax, inventory, documentation/link and whitespace checks pass. Focused Maven
cannot run: exit127, mvn: command not found. No Java21/Spring/JUnit/PostgreSQL pass
is invented; previous limited source harnesses did not exercise AssertJ overload
resolution. Publish exact three-file tree/blobs/parent with expected-head guard,
observe applicable exact-SHA CI creation/start and STOP. The query optimization's
runtime result and500ms compliance remain pending hosted execution. Writer/Package B
acceptance and the registered genuine-catalog dictionary refresh remain on hold;
no API, dependent stage, release or deployment is selected.

### HAPI-599 Package B — measured installed-unit work policy review — 2026-10-29

**Exact-head evidence.** Owner Fail selects main
`96ea1ce12500482a69ad964926611cc7577c9c6e`. Full966/run `40831922407` failed;
Fast266/run `40831922376` and Docs698/run `40831922403` passed. Unit/architecture,
static validation and nine PostgreSQL shards passed. Shard8/job `123307860212`
reports205 cases with1 failure/2 errors. All three belong to the dedicated lifecycle
suite:23 cases,1 failure/2 errors/zero skips, hence20 reported passes. Batching and
the compilation correction cleared most preceding failures, including the new exact
lock-mode regression and the corrected savepoint test, without accepting the whole
writer. missingNativeKindConstraintCannotBeSubstitutedByApplicationExistenceCheck
fails during its post-restoration registration at267 statements/585.455ms in FLUSHING;
allSevenOperationsCommitThroughEachGenuineNativeProducer fails on relocation at515
statements/503.187ms in FLUSHING. Both traces explicitly name aggregate500ms work
budget. sourceGenerationChangeAfterFreezeCannotPassFinalGuard fails because its
finalGuard callback is not reached; its original rejection is not visible in the
Mockito verification output. Do not assert its underlying reason is established.

**Reviewed policy amendment and scope.** The owner's earlier request asks whether
the500ms value is arbitrary and can be skipped. The existing policy review separates
the exact finite server work cap from native authority lifetime and from an accepted
industrial/HTTP SLA. Full964 and Full966 now establish that this new multi-owner,
six-requirement relocation family can exceed that cap even after batching. Repeated
removal of source checks would reduce assurance; retain the optimized implementation
and every owner recheck. Amend this family's earlier preserve-standard500ms
registration explicitly, applying the existing bounded startup-policy pattern used
for Custody/appointments. This is a documented family policy review based on measured
failures, not an unlimited timeout bypass or a claim of calibrated performance.

Exact message:
`fix(topology): bound installed-unit work independently of authority expiry`.
CREATE/DELETE none. MODIFY only these seven existing paths:

- src/main/java/dz/sh/hidra/platform/configuration/ProtectedExecutionBudgetProperties.java
- src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java
- src/test/java/dz/sh/hidra/platform/configuration/ProtectedExecutionBudgetPropertiesTest.java
- src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java
- src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedInstalledUnitLifecyclePostgresIntegrationTest.java
- .github/ci-test-inventory.json
- doc/roadmap/TASKS_HPS-006.md

Add immutable startup property
hidra.platform.persistence.execution.installed-unit-work-budget-ms, default1000ms,
integer1..10000, with malformed/out-of-range values failing startup. Default1000ms
is provisional two-times-standard development headroom for the measured503–585ms
attempts, not a measured end-to-end percentile, operator SLA or promise that every
remaining operation finishes. Preserve the existing one-/two-argument property
constructors with independent defaults. No request/HTTP numeric input, mutable
setter, runtime update or public numeric execution overload. Only the exact already
allowlisted InstalledUnitLifecycleApplicationService selects this budget through
the private executor policy selector. The public compiled-caller whitelist remains
byte-exact. Ordinary execute and all other standard families retain500ms; Custody
and appointment policies remain independent and unchanged.

Native/source/token/grant/policy validity, earliest future bounds, source status,
DENY precedence, exact relation/row fences, all owner current checks, pinned DML,
managed flush, final scalar database clock, commit observation and indeterminate
outcomes remain unchanged. Keep250ms lock/750ms statement/1000ms idle settings,
the datasource's finite deadline enforcement and all immutable migrations. The new
work budget never extends validUntil or accepts expiry in the final guard. Existing
real expiry, changed generation/role, replay, denial, rollback, misuse and four native
producer paths remain required; no retry, skip or weaker assertion.

**Regression/diagnostic changes.** Add two configuration cases for independent defaults/
overrides and malformed/unbounded rejection; preserve legacy constructor coverage.
The existing exact installed-unit executor case retains all four SQL pins, checks
default/custom family selection, sibling/unreviewed/standard500ms isolation and the
unchanged public route rejection. Add one finite-cap case proving installed-unit
work still expires/poisons and default Scope remains500ms, without sleeps. In the
existing generation-change PostgreSQL case, retain the actual outer rejection as
an AssertionError cause if the real final guard is not reached. This preserves
suppressed diagnostics rather than masking them with Mockito's invocation failure;
callback verification, required false final result and no-effects checks remain.
The suite still has23 cases. Inventory changes only these three actual suite hashes,
the renamed existing executor case and three new cases. Active500 classes now require
3,739 cases across the same ten shards; no scheduling/timing baseline change.

**Available local validation.** Four changed Java production/unit sources and the
changed PostgreSQL source parse under Java17. Actual budget configuration and all
nine configuration test methods execute against minimal Environment/assertion API
stubs. Extracted actual private selector and unchanged Scope deadline code execute
finite-cap/default/independent-family probes. These are limited logic/source proof,
not integrated executor, Maven/JUnit/Spring or PostgreSQL acceptance. Inventory,
documentation/link validation, offline OpenAPI and125 validator self-tests pass;
focused Maven exits127, mvn: command not found. No environment is provisioned solely
for local verification. Check seven-file scope, exact allowlist/unchanged runtime
security and bounded selector delta, then publish verified tree/blobs/parent with
expected-head guard, observe applicable exact-SHA CI creation/start and STOP.

Writer/Package B and integrated HAPI-599 acceptance remain held until actual runtime
and strict catalog gates pass. The existing genuine-catalog follow-up remains
`docs(topology): refresh protected installed-unit operations physical dictionary`;
no physical dictionary, generator, migration or workflow is changed here. New budget
success and the generation fault's exact underlying rejection remain hosted checks.
No Package C/API, dependent task, frontend, simulation/P3, SITE, release or deployment
is selected automatically.

### HAPI-599 Package B — isolate post-freeze administrative fault connections — 2026-10-29

**Exact-head failure.** Owner Fail selects main
`b9a09071c22e14323e2bde03d27a609fb8aa0477`. Full967/run `40833637056` failed;
Fast267/run `40833636981` and Docs699/run `40833636812` passed. Static, unit/
architecture and nine PostgreSQL shards passed. Shard8/job `123313270659` reports
205 cases with1 failure/zero errors/skips. The lifecycle suite reports23 cases with
1 failure/zero errors/skips, hence22 reported passes, including the all-seven-
operations/four-native-producers case, native-expiry and finite-budget regressions.
No new aggregate-budget failure is reported on this head; do not infer a production
latency percentile or overall acceptance from that result.

The retained original rejection identifies the remaining generation-change failure:
ProtectedInstalledUnitAdmissionCoordinator.finalGuard calls current, which rejects
before the repository finalGuard. Source inspection shows the post-freeze callback
uses admin().update through a new JdbcTemplate/DriverManagerDataSource while Spring
transaction synchronization is active. That managed access can bind a second JDBC
holder into the thread's resource map, which intentionally must match the admission
snapshot. It does not isolate the intended external committed generation fault from
the transaction-resource boundary. The analogous role-change callback uses the same
managed administrative path; its generic rejection alone does not prove the final
catalog predicate was reached. Preserve the production resource-map check.

**Bounded repair.** Exact message:
`test(topology): isolate post-freeze catalog fault connections`.
CREATE/DELETE none. MODIFY only
src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedInstalledUnitLifecyclePostgresIntegrationTest.java,
.github/ci-test-inventory.json and this phase. Both post-freeze generation and role
injections now open/close raw DriverManager connections, require autocommit and
perform the deliberate committed change without Spring-managed resource enlistment.
Generation UPDATE remains parameterized and must affect exactly one row. Each
callback snapshots and verifies the protected thread's resource map is unchanged.
The real freeze and final guard still run. Strengthen the role case to require the
same observed false real final result, reached finalGuard and full owned-effects
rollback as the generation case. Preserve the original outer rejection as assertion
cause if either case fails earlier; restore publisher privilege in finally. No test
is skipped or weakened, no fake authority or successful guard is substituted.

All production sources, resource/owner/native checks, independent1000ms installed-
unit work policy, other budgets, immutable migrations, dictionary and workflows
remain unchanged. Inventory changes only the actual suite source hash, retaining
all23 cases and timing/classification/shard assignments. Active500 classes/3,739
required cases and all ten PostgreSQL shards remain mandatory.

**Local validation and hold.** Changed Java source parses under available Java17;
inventory, documentation/link, three-file scope and whitespace checks pass. Focused
Maven exits127, mvn: command not found. Raw-connection fault isolation and final
predicate/rollback success require hosted PostgreSQL evidence; source inspection is
not runtime acceptance. Publish verified blobs/tree/parent directly to main with
expected-head guard, observe applicable exact-SHA CI creation/start and STOP.
Writer/Package B and integrated HAPI-599 remain held pending actual runtime and
strict dictionary gates. Retain the registered genuine-catalog follow-up under
`docs(topology): refresh protected installed-unit operations physical dictionary`;
no dependent task, Package C/API, frontend, simulation/P3, SITE, release or deployment
is selected here.

### HAPI-599 Package B — protected operations physical dictionary refresh — 2026-10-29

Owner Fail refreshes main `d8e3f65d78f0a88e756fb6841e96e9a743b47aef`.
Full968/run `40834985871` FAILED only API/dictionary job `123317559914` and the
dependent Full gate. Static, unit/architecture and all ten PostgreSQL shards
SUCCEEDED. Fast268/run `40834985887` and Docs700/run `40834985881` SUCCEEDED.
The API log reports genuine catalog capture followed by Physical dictionary drift;
its later API lane evidence is absent because strict comparison stops execution.
No runtime repair is selected on this head. Execute the previously registered
two-file follow-up, exact message:
`docs(topology): refresh protected installed-unit operations physical dictionary`.
MODIFY only doc/database/DATA_DICTIONARY.md and this phase. CREATE/DELETE none.

**Independent artifact verification.** Downloaded same-head/run/attempt1 schema
artifact12880396611. ZIP SHA256 matches GitHub metadata:
1215d651c7a39259307140182ec731eead20205d0393b73e85054b73e4e631aa.
The unchanged generator validates schema.json against current complete migration/JPA
inventory and reviewed ownership, then reproduces the retained generated Markdown
byte-for-byte. Captured PostgreSQL16.15 (Debian16.15-1.pgdg13+2), disposable hidra_test,
186 migrations,470 JPA mappings,532 catalog relations, zero unresolved owners.
Capture source remains the real d8e3f65d78f0a88e756fb6841e96e9a743b47aef, never
relabeled as this documentation commit. Source bundle SHA256
aligns with current unchanged sources: efd5680512a4a8627dfc4ea5ae27a1725089963f7bf5f646079f6d3699383ec4.
Catalog artifact SHA256 99bcf4c8fae9d8988c44f01fdaa56764549eb536b3d7b31e32193550568d793c;
rendered dictionary SHA256 462c3e030867f4ec9b80072a814da0c21ff380440f5d38bf8226e44f4da7bb64.
The original receipt truthfully has reviewed_dictionary_checked=false; its comparison
file records the failed old-document check. Neither historical receipt is rewritten
or promoted to a hosted strict pass.

Separately downloaded same-run PostgreSQL8 artifact12880696662, ZIP SHA256
1489be14768cb610a245f1a560ef8ad29cc957256270a7dd8708e7386384facc.
Verified all15 retained report hashes and exact source/run/attempt/inventory
provenance, and reran the unchanged inventory report checker:15 classes/205 cases,
zero failures/errors/skips. The lifecycle raw XML independently reports23 cases,
zero failures/errors/skips,65.037s suite total. It includes successful all-seven-
operations/four-native-producers and real final role/generation rejection cases.
Suite total is not an operation-latency percentile. This focused evidence does not
invent complete Full acceptance or an uncollected all500-class aggregate artifact.

**Actual physical delta.** Review confirms the only added relation section is the
immutable installed-unit operation-request receipt:19 columns,5 constraints and2
user triggers, all from the real migrated catalog. Existing installed-unit section
adds the validated, non-deferrable equipment-kind FK to equipment_type(id), with
UPDATE/DELETE RESTRICT, and source evidence for005/protected adapter. Equipment-type
section gains the real005 migration mention. Every other existing relation section
is unchanged; no relation is removed. Generation basis truthfully advances to186
migrations/532 relations and the actual capture/source bundle. No inferred metadata,
handwritten physical facts, JPA remap, old migration edit or role/grant change.

**Validation and narrowed hold.** The unchanged generator's --input/--source-sha/
--ownership/--check command now passes against this reviewed dictionary, yielding
reviewed_dictionary_checked=true locally for the genuine retained artifact.
Documentation/link, exact two-file scope, source/generator/workflow preservation and
whitespace checks pass. No new local Java/PostgreSQL runtime is claimed or required
for this catalog-only correction. The earlier runtime-failure hold is cleared by
the successful required lanes at d8e3f65; overall writer/Package B acceptance remains
pending successful strict API/dictionary/Full verification on the corrected head.
Frozen HAPI-599 integrated NOT_IMPLEMENTED/BLOCKED remains; Package C/API and other
dependent work are not selected.

Publish verified two-file blobs/tree/parent directly to main with expected-head
guard; observe applicable Docs CI creation/start and STOP. Existing workflow filters
exclude doc/** and Markdown-only pushes from Full/Fast, and connected GitHub tools
provide no workflow_dispatch operation. Require a manual HidraAPI CI dispatch on
this new main head with verification_mode=development, serial_oracle=false,
dependency_cache=cached. Do not touch runtime/workflow files to manufacture a push
trigger, rerun an old SHA as new-head proof or waive the strict gate. After that run
passes, independently review its complete retained evidence before Package B
acceptance or another registered stage. SITE, frontend, simulation/P3, release and
deployment remain unselected.

### HAPI-599 Package B — protected installed-unit lifecycle acceptance — 2026-10-29

**Owner Next; exact-head evidence.** Main
`b91e1488aaefb828ca9ea4a719e553516dc26540` has manually dispatched Full969/run
`40837033975` and Docs701/run `40836544519` completed SUCCESS. All ten PostgreSQL
shards, Java21 unit/architecture, static, API/dictionary and Full gate succeeded.
Development mode, attempt1, cached dependencies and serial_oracle=false are explicit;
the separate serial diagnostic is skipped by policy, not a skipped required test.
release_eligible=false, optimized_adoption_eligible=false and same_run_equivalence=false
remain explicit. No audit, industrial performance, release or deployment acceptance.
Fast268 passed on runtime/test parent d8e3f65; the dictionary-only head has no
applicable Fast push trigger. Full969 independently executes the complete required
unit/architecture partition on the corrected exact SHA; no missing Fast run is
fabricated or borrowed as an exact-head receipt.

**Independent retained-evidence review.** Downloaded same-run/attempt1 full bundle
artifact12880489832. ZIP SHA256 matches GitHub metadata:
a74f6c2e0a4407ab08a27352926568ba2fb15ad1ca2a0427302437641591e174.
The unchanged full-evidence aggregator reproduces gate.json byte-for-byte against
current source/inventory, checking all507 retained file hashes, source/run/attempt,
required lane membership, exact raw class/case identities and current/base API/
dictionary provenance. Reproduced gate SHA256
e78f99390b22f3baff5ad2bdb51ba36344aea2edfdfefad10e924b68fd6edb2d.
All500 active classes/3,739 exact cases reconcile with zero failures/errors/skips.
Four approved historical exclusions, ten required shards and all timing/classification
baselines remain unchanged. No unrestricted serial equivalence is claimed.

Focused actual raw reports, all zero failures/errors/skips:

- ProtectedInstalledUnitLifecyclePostgresIntegrationTest:23 cases,61.476s suite total.
- ProtectedInstalledUnitAdmissionCoordinatorTest:9 cases; InstalledUnitLifecycleApplicationServiceTest:4 cases.
- ProtectedExecutionBudgetPropertiesTest:9 cases; ProtectedTransactionExecutorTest:54 cases.
- TopologyInstalledUnitCompanyEvidencePostgresIntegrationTest:28 cases.
- TopologyInstalledUnitManufacturerEvidencePostgresIntegrationTest:16 cases.
- InstalledUnitPositionOwnershipPostgresIntegrationTest:23 cases.
- ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest:19 cases.

The lifecycle suite includes every seven-operation path through genuine native
LOCAL/LDAP/AD/OIDC producers, same-unit atomic relocation, exact replay, native
expiry, revoked/scoped authority, current company/manufacturer/position evidence,
constraint/privilege/trigger failures, immutable history, stale/conflicting commands,
source/destination overlap rollback, wrong final lock mode, both equipment-kind
race orders and thread/suspension/savepoint poisoning. The repaired post-freeze
role/generation cases require false from the actual final scalar guard and unchanged
owned effects; isolated administrative connections do not bypass the production
resource-map guard. Suite totals are not per-operation latency percentiles.

**Strict physical/API evidence.** Same-run receipt is checked=true on b91e148:
PostgreSQL16.15,186 migrations,470 JPA mappings,532 relations, zero unresolved owners.
Source bundle SHA256 efd5680512a4a8627dfc4ea5ae27a1725089963f7bf5f646079f6d3699383ec4;
catalog SHA256 2386787886111362e489f1bb8618f00a735590823912d3eeeda183ae09abcf9a;
checked render SHA256 2bc2a682ed748585cd3f8cd2ed0a1b5d397c2513955e5503288dca745300fe60.
The reviewed dictionary's real d8e3f65 capture remains truthful; the unchanged
comparison ignores only its validated capture-source line. API compatibility passed
against base98d77d88fc0551826427d32bb96ffc835bf27545. Current API SHA256
33d2fe80e5eb3c9d9d7b9a6f7ff47bec55c23a56a30bb78d4164829c8e53c3be;
base API SHA256 0b0e2cb9b489ba53dc64fe08e0a195fbdab02a19ed066c2b7b0eb3370a3a7676.
No installed-unit HTTP routes exist yet; passing existing API compatibility does
not invent Package C delivery.

**Bounded acceptance and hold update.** Accept the protected writer and its owner
composition for the seven implemented backend operations in the tested development
scope. Clear its implementation/runtime/catalog verification hold, including the
two-stage CI-first repair and genuine dictionary follow-up. Preserve all failed
historical runs and diagnostic limits; no claim that a passing run proves permanent
stability. Existing Package A and independently accepted owner contributions remain
accepted and are not repeated. The installed-unit work budget remains independently
bounded/configurable, default1000ms, with native authority expiry and all final
checks unchanged; no operational SLA follows from this configuration.

This accepts same-unit relocation, not atomic replacement by another unit, public
REST/browser qualification, manufacturer-to-kind engineering compatibility, station
configuration/incidence, Assets aggregate ownership or manufacturer characteristic
editing. HAPI-599 integrated NOT_IMPLEMENTED/BLOCKED remains in the frozen registry
pending remaining replacement/API qualification; the bounded writer is no longer
CI-blocked. This acceptance does not mark HAPI-600/601 or any dependent task complete.
Release/publication and deployment remain separately held and unselected.

**This one selected acceptance commit.** Exact message:
`docs(topology): accept protected installed-unit lifecycle operations`.
CREATE/DELETE none; MODIFY only this phase, append-only. Independent bundle/gate
reproduction, documentation/link, scope and whitespace checks precede guarded
publication. No local Maven/Java/PostgreSQL rerun is claimed. Publish verified
parent/tree/blob to main with expected-head guard, observe applicable exact-SHA
Docs creation/start and STOP. Documentation-only acceptance adds no runtime drift.

**Next registered stage, NOT EXECUTED.** Exact message:
`docs(topology): register installed-unit API and replacement preflight`.
CREATE/DELETE none; MODIFY only this phase. Re-read current owner policy, public
contracts and accepted seven-operation source. Determine the remaining atomic
replacement descriptor/permission/transaction obligation before exposing an HTTP
operation: RELOCATE's single unitId cannot authorize replacing a different unit,
and separate close/install requests cannot claim atomic replacement. Register exact
candidate contract/adapter/DTO/controller/test/migration scope only from that review.
Reconcile Package C's typed current/history/replay/error/company-scope requirements
with actual runtime seams and define the bounded execution sequence before code.
Do not implement an eighth operation, fabricate an owner contract or repeat accepted
writer work in this preflight. Other tasks, frontend, simulation/P3, SITE, release
and deployment remain unselected.

### HAPI-599 — API and atomic replacement source preflight — 2026-10-29

**Selected stage and live evidence.** Owner Next selects exactly
`docs(topology): register installed-unit API and replacement preflight`.
Main8394ee9224eae404d874c0677e1fd036d8c2e0b5 has Docs702/run40839951319 SUCCESS;
its unchanged runtime/dictionary retains independently accepted Full969/run40837033975
on b91e1488aaefb828ca9ea4a719e553516dc26540:500 classes/3,739 cases, zero failures,
errors or skips. CREATE/DELETE none; MODIFY only this phase, append-only. This
preflight changes no runtime contract, permission, schema, test, API, status or
accepted evidence. A is accepted; B's seven-operation writer is accepted, while
atomic replacement by a different unit remains. C is not implemented.

**Recovered source and concrete gaps.** Re-read owner Option A decisions01/02/03,
current InstalledUnitLifecycleUseCase, ProtectedInstalledUnitRepositoryPort,
InstalledUnitLifecycleApplicationService, ProtectedInstalledUnitAdmissionCoordinator,
JdbcProtectedInstalledUnitRepositoryAdapter, Identity's public
TopologyInstalledUnitAuthorizationContract, ProtectedTopologyInstalledUnitAuthorizationParticipant,
ProtectedOperationIdentityParticipant's installed-unit group and
JdbcProtectedAuthorizationEvidenceReader's installed-unit branch; migrations002/005,
current schema receipt, existing Topology REST DTO/mapper/controller/failure patterns,
HidraSecurityConfiguration, OpenAPI manifest, architecture guards and inventory.

- Command and Identity Request each carry one unitId. RELOCATE closes/reopens that
  same unit; Result.replacement is a replacement installation, not another physical
  unit. No REST adapter exists. Two separately committed close/install calls cannot
  satisfy owner-approved atomic replacement.
- The Identity issuer already builds one private claim group and common native
  sample from Request.requirements; its participant compares every exact descriptor
  and uses current fences/rechecks. Preserve that machinery. The private reader
  currently admits INSTALLED_UNIT resources only for REGISTER_UNIT/READ_UNIT and
  installation resources for the other five codes. It cannot authorize both unit
  identities under a distinct replacement permission without a bounded extension.
- 005's receipt has one unit/company FK and an operation check containing only
  REGISTER_UNIT, RECORD_INSTALLATION, CLOSE_INSTALLATION and RELOCATE. Its canonical
  command/result format is version1. It cannot truthfully retain another unit ID
  by reusing replacementInstallationId or reinterpreting old bytes.
- Coordinator failures are generic AccessDeniedException with suppressed causes;
  the executor preserves rejected/indeterminate physical outcomes. Current output
  cannot safely distinguish authorized absence, stale CAS/occupancy conflict and
  infrastructure failure for HTTP404/409/503. No controller may infer business
  status by scanning generic messages or exposing suppressed SQL/native provenance.
- Existing immutable unit/installation models, owned closure/insert SQL and native
  half-open exclusions can represent replacement without rewriting prior identity
  or events. Keep Party/type/position evidence and optional Assets reference meanings.
  Current field enums classify authored evidence; they do not prove engineering
  suitability, serial uniqueness, model provenance approval or permission authority.

**Bounded replacement semantics selected from OD-HAPI-599-03.** REPLACE is distinct
from RELOCATE. Replace one open source installation with another already registered
physical unit at the same exact mounting position/facility/company. Require distinct
old/new unit IDs and installation IDs, expected source revision, one microsecond-
precision effective instant strictly after source start and bounded source evidence.
Replacement unit and position must have the same actual equipment-type ID; both
units have the same actual company. No implicit registration, identity/model/serial
edit, cross-company transfer, different-position swap or engineering qualification.
The new half-open installation starts exactly at the old effective close, revision1,
and is open-ended. Existing native unit/position exclusion rejects any incompatible
replacement-unit occupancy, including future overlap. Old recordedAt/provenance and
immutable event history remain; new recordedAt comes from the retained native sample.
Closure, new installation/events and one canonical receipt commit or roll back together.

Use a distinct permission topology:installed-unit:replace. Its single native request
binds both physical units, both installations, company and one facility: company
CUSTOM topology-company:<id> on each unit, and company plus FACILITY on each
installation, six exact requirements total. No facility scope is invented for a
unit resource. All six must PERMIT, with DENY precedence and common native origin,
PID/XID/role/strict validity; no relocated permission, caller actor or borrowed claim.
Topology independently rechecks both stored units, the old installation and actual
position ownership. Acquire current known manufacturer contributions for both units,
retaining spare one-use reads and stable identity order/deduplication; UNKNOWN stays
UNKNOWN. Existing company/position/root fences remain, including exact final union.
No new Party provider, source lock helper, broad grant or generic callback is needed.

**Next executable B prerequisite — native descriptor extension only.** Exact message:
`feat(identity): authorize atomic installed-unit replacement`.
CREATE/DELETE none. MODIFY only these seven paths:

```text
src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyInstalledUnitAuthorizationContract.java
src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java
src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedTopologyInstalledUnitAuthorizationParticipantTest.java
src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest.java
src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java
.github/ci-test-inventory.json
doc/roadmap/TASKS_HPS-006.md
```

Add REPLACE and optional replacementUnitId to the existing public Request; retain
its seven-argument constructor for the original seven operations with null second
unit. REPLACE requires the six descriptors above and equal source/destination
facility; every other operation forbids replacementUnitId. Preserve old permission
codes, exact requirement lists, constructors and decisions. Extend only the reader's
installed-unit resource-type/permission binding for REPLACE; its unit-FACILITY ban
and all other owner families remain. Existing opaque issuer/participant/group,
native sampling, temporal collector, source fences and evaluation algorithm remain
byte-exact. No public generic list of permission strings or caller claim factory.

The existing boundary test pins reader SHA256
3b734e9fa7d78372a5555d551ed4e45428515f280fb4e973a76d9a734ea2fa56.
This explicitly reviewed one-branch extension supersedes that reader-only pin:
verify the complete bounded source delta, then pin the new actual bytes. Preserve
all J/provider pins and the existing boundary assertions; never remove the hash
check. Extend actual unit/native PostgreSQL suites for six requirements, distinct/
missing IDs, same facility, denial of either unit/installation scope, wrong resource
family, request substitution, source revocation/expiry and all four native producers.
Keep every original case. Admit actual source/case hashes only; no Topology writer,
DDL, production permission seed, controller or workflow change in this prerequisite.
Use useful available checks and AGENTS11.1 CI-first publication, then STOP at CI startup.
Require its exact-head hosted success before the replacement writer stage.

**Subsequent B writer candidate, not executable in this turn.** Intended message:
`feat(topology): add atomic installed-unit replacement`.
After the native prerequisite is accepted, revalidate/register this exact candidate
scope before mutation. CREATE:

```text
src/main/java/dz/sh/hidra/modules/topology/application/model/InstalledUnitLifecycleFailure.java
src/main/resources/db/migration/V20261029_001__topology_installed_unit_replacement.sql
```

MODIFY:

```text
src/main/java/dz/sh/hidra/modules/topology/application/port/in/InstalledUnitLifecycleUseCase.java
src/main/java/dz/sh/hidra/modules/topology/application/port/out/ProtectedInstalledUnitRepositoryPort.java
src/main/java/dz/sh/hidra/modules/topology/application/service/InstalledUnitLifecycleApplicationService.java
src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedInstalledUnitAdmissionCoordinator.java
src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcProtectedInstalledUnitRepositoryAdapter.java
src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java
src/test/java/dz/sh/hidra/modules/topology/application/service/ProtectedInstalledUnitAdmissionCoordinatorTest.java
src/test/java/dz/sh/hidra/modules/topology/application/service/InstalledUnitLifecycleApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedInstalledUnitLifecyclePostgresIntegrationTest.java
src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java
.github/database-dictionary-ownership.json
.github/ci-test-inventory.json
doc/roadmap/TASKS_HPS-006.md
```

The migration filename is currently free after the186-file chain ending005; recheck
live history before reserving/writing it. Preserve all old migration bytes. Add a
nullable replacement_unit_id and validated non-deferrable same-company unit FK,
with UPDATE/DELETE RESTRICT, to the existing receipt, and replace only its currently captured operation check
hidra_topology_installed_unit_operation_request_operation_check with a named check
admitting REPLACE. Require a non-null distinct replacement ID only for REPLACE and
null for legacy operations; no historical row rewrite/default fabricated identity.
Keep receipt immutability/origin functions and all existing trigger-body hashes.
Guard the new column/check/FK and native RI machinery in the real catalog before
admitting writes. No new production role/grant or source helper.

Append optional replacement unit fields to typed command/hint/snapshot/result with
retained old constructors; forbid them for legacy operations. Keep all original
seven canonical command/result encodings byte-exact as version1; use a separate
version2 envelope only for REPLACE, retaining strict decoding/bounds/canonical hashes
and historical version1 replay. Bind both unit IDs and exact descriptors in request
and response bytes. Retain all four existing SQL texts/hashes; add one complete fixed
replacement receipt insert template with its own independent coordinator/executor
pin. Reuse existing closure/installation statements, private credential route and
finite1000ms installed-unit policy. Do not widen the caller whitelist, allow SQL
prefixes or relax authority expiry/lock/statement/final-guard rules.

Typed failures must preserve INVALID/UNAUTHENTICATED/DENIED/NOT_FOUND/CONFLICT/
UNAVAILABLE/INDETERMINATE meaning without revealing foreign existence or database
provenance. NOT_FOUND may be raised only after fresh exact authority/owner checks;
CONFLICT only for authorized expected-revision/request/owned occupancy conflicts.
Catalog, native, privilege, origin and unknown SQL failures remain fail-closed;
never classify every integrity violation as409. Preserve original diagnostics
privately and executor indeterminate outcomes; do not query after poisoning/sealing
or convert indeterminate commit to success. Source-specific classification belongs
in the private adapter/application stage, not a REST cause-message parser.

Required new PostgreSQL proofs include all four native origins, either-unit/facility
scope denial, two different manufacturers/UNKNOWN, occupied replacement unit,
source CAS/expiry, racing replacements, failure after closure and after new insert,
no partial events/receipts, replay without projection restoration, changed second
unit/operation conflict, old version1 receipt replay, missing new FK/privileges and
actual final source/role/time rejection. Keep all23 accepted lifecycle cases and old
unit/platform cases. Capture genuine new schema in CI, then perform the usual
truthful dictionary follow-up in a separate two-document stage; never infer physical
metadata. Accept the remaining B replacement scope only from complete exact-head evidence.

**C transport candidate after B acceptance.** Intended implementation message:
`feat(topology): expose protected installed-unit lifecycle API`.
Candidate CREATE paths, each currently absent:

```text
src/main/java/dz/sh/hidra/modules/topology/api/rest/request/InstalledUnitLifecycleRequests.java
src/main/java/dz/sh/hidra/modules/topology/api/rest/response/InstalledUnitLifecycleResponses.java
src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/InstalledUnitLifecycleRestMapper.java
src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/InstalledUnitLifecycleController.java
src/main/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleApiExceptionHandler.java
src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleControllerTest.java
src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleRestMapperTest.java
src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleApiExceptionHandlerTest.java
src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/InstalledUnitLifecycleApiPostgresIntegrationTest.java
src/test/java/dz/sh/hidra/modules/topology/qualification/InstalledUnitLifecycleOperatorQualificationTest.java
```

Candidate MODIFY only inventory and this phase; no security permitAll, production
writer, schema or generic CRUD exposure. Reconcile this candidate with the accepted
B contract before selecting implementation. Route base follows current Topology
/api/v1/topology/installed-units. Proposed operation mapping:

| HTTP and relative path | Typed owner operation | Successful result |
|---|---|---|
| PUT /{unitId} | REGISTER_UNIT | 200 immutable unit/result; exact request replay explicit |
| GET /{unitId} | READ_UNIT | 200 authorized unit |
| POST /{unitId}/installations | RECORD_INSTALLATION | 200 installation/result |
| POST /{unitId}/installations/{installationId}/close | CLOSE_INSTALLATION | 200 closed installation/result |
| POST /{unitId}/installations/{installationId}/relocate | RELOCATE | 200 same-unit closure/new installation |
| POST /{unitId}/installations/{installationId}/replace | REPLACE | 200 both units and atomic installation result |
| GET /{unitId}/installations/{installationId} | READ_INSTALLATION | 200 authorized current installation |
| GET /{unitId}/installations/{installationId}/history | READ_HISTORY | 200 bounded immutable versions |

200 is deliberate: existing Result.replayed alone does not prove a resource was
newly inserted; no fabricated201 or insert status. Require explicit company on
every command/read, requestId on mutations, exact path/body consistency and source/
destination assertions as appropriate. No arbitrary operation selector, caller actor,
binding/session/role/permission, server recordedAt or authority-expiry fields. Strict
JSON rejects unknown fields, coercion, overflow, padded/over80-code-point IDs,
non-microsecond timestamps, malformed/ambiguous evidence and cross-operation fields;
retain current bounded UTF-8/payload limits rather than force legacy80-character IDs
into UUID36. UNKNOWN/value/provenance relationships remain explicit. Response DTOs
map exact authored evidence and expose unit/installation IDs, effective periods,
revision and replay state; no entities, raw receipts, native claims or SQL details.
History here is per installation, at most two immutable versions; no unbounded
unit-wide list/search/export is silently added. Replay needs current authorization.

Apply400 invalid structure,401 actual unauthenticated entry,403 denied scope,
404 only authorized unavailable target,409 proven business conflict and503 unavailable/
indeterminate completion, with fixed non-disclosing ProblemDetail codes. Preserve
native authentication and exact application authorization even in test profiles;
HTTP authorities alone cannot manufacture admission. Do not expose diagnostic
exception chains, stack traces, tokens, native coordinates or publisher identities.

Known/repeated manufacturer-scoped serial markings remain separate physical IDs,
never global uniqueness or automatic merge. Qualification must exercise repeated/
unknown/unverified serial evidence, explicit provenance and controlled human review
without silently upgrading metadata into engineering qualification. A new candidate-
search or merge workflow is not admitted: any need to disclose other unit IDs/counts
requires its own exact current authorization contract and source registration.

MockMvc DTO/mapper/error cases must be complemented by actual Spring/security/native
issuer/PostgreSQL HTTP tests for all eight operations, denial before disclosure,
stale/conflicting/replay behavior, rollback, historical reads, serial-policy limits
and Assets isolation. Register real class/case/source hashes only after tests exist.
After authoritative API capture, refresh doc/api/openapi.yaml and
.github/documentation-validation.json plus this phase in a separately registered
truthful snapshot follow-up; no handwritten or guessed OpenAPI/source SHA. The
existing snapshot has306 operations/282 paths/323 schemas and remains unchanged here.
C acceptance and integrated HAPI-599 closure require independently verified complete
Full/current-base API/catalog evidence and the actual replacement/API qualification
matrix, not only controller mocks or the earlier seven-operation acceptance.

**Validation and execution boundary.** The proposed187th migration is a reservation only;
no migration is created now. All186 published scripts, accepted domain/writer/owner
sources, four SQL pins, budgets, global source boundaries, API/dictionary artifacts,
CI workflows/inventory and frozen statuses are untouched in this one-document stage.
Source/path checks, documentation/link validation and whitespace checks precede
verified parent/tree/blob publication directly to main with expected-head guard.
No new Java/PostgreSQL pass is claimed. Observe applicable exact-SHA Docs startup
and STOP. Next selects only the seven-file native replacement prerequisite above;
no automatic B writer, C, HAPI-600/601, frontend, simulation/P3, SITE, release or deployment.

### HAPI-599 B — replacement permission/resource binding correction — 2026-10-29

**Owner Next; source-backed prerequisite correction before runtime mutation.**
Main177d5488b63bc3503480f27d126b4379e085796b has Docs703/run40856741712 SUCCESS;
unchanged accepted runtime retains Full969. Next selected the seven-file native
replacement prerequisite. Implementation inspection finds a contradiction in that
registration: six requirements cover two resource types, but one proposed replace
permission cannot be defined for both. Do not implement the contradictory contract,
weaken generic permission validation or silently write outside its file allowlist.
This single corrective registration changes only this phase, append-only, message:
`docs(identity): correct replacement permission resource bindings`.
CREATE/DELETE none. No runtime implementation or test/CI pass is claimed here.

**Exact invariant and affected source.** JdbcProtectedAuthorizationEvidenceReader
loads the permission by code, requires exactly one active row and compares its
resource_type to each AuthorizationEvaluationRequest.resourceType; otherwise it
returns PERMISSION_INELIGIBLE. This existing generic rule is shared by owner families
and must remain byte-exact. ProtectedOperationIdentityParticipant.qualifyInstalledUnitGroup
currently computes one permissionCode(request.operation()) for every requirement.
ProtectedTopologyInstalledUnitAuthorizationParticipant.evaluate independently
compares that same one-argument code. Changing the public Request and reader's
installed-unit admission branch alone cannot issue/verify the required mixed-resource
replacement group. The preceding preflight overlooked these two concrete call sites.

**Corrected bounded contract.** Retain one typed REPLACE operation and one genuine
native group/sample, with six exact required decisions. Use distinct permission
codes bound to their existing resource types:

| Permission code | Resource type | Required descriptors/scopes |
|---|---|---|
| topology:installed-unit:replace-unit | INSTALLED_UNIT | Source and replacement unit; company CUSTOM scope for each |
| topology:installed-unit:replace-installation | INSTALLED_UNIT_INSTALLATION | Source and replacement installation; company CUSTOM plus same FACILITY scope for each |

Both unit and installation permission components are necessary; either alone must
fail. Preserve source/replacement identity distinction, company binding and same-
facility replacement semantics. Supersede only the preceding proposal for a single
topology:installed-unit:replace code; no runtime permission with that code was added.
No wildcard resource type, duplicate permission code, resource-agnostic grant,
permission alias to RELOCATE or generic caller-supplied permission list is admitted.

Add a resource-specific permissionCode(operation,resourceType) overload in the
existing public contract. Derive its input from each exact Requirement, not caller
permission text. Reject unsupported operation/resource combinations. Existing
permissionCode(operation) and all seven old codes/requirements remain unchanged;
for the new REPLACE value, the one-argument method must reject rather than select
one incomplete component. Native group construction and public-participant evaluation
use the resource-specific overload; both still compare exact descriptor/permission
pairs. In the reader's installed-unit-family admission branch, validate the allowed
operation/resource combination before invoking the overload, so a legacy unit/
installation mismatch cannot become an exception-driven stream shortcut. Preserve
all generic permission-row eligibility, claim consumption, opaque-ticket ownership,
source/temporal/native sampling, fences, current rechecks and other operation families.
No additional authentication, role/grant migration, public factory or storage capability.

**Next executable native prerequisite — corrected nine-file scope.** Keep exact message
`feat(identity): authorize atomic installed-unit replacement`.
CREATE/DELETE none. MODIFY only:

```text
src/main/java/dz/sh/hidra/modules/identity/application/contract/topology/TopologyInstalledUnitAuthorizationContract.java
src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedOperationIdentityParticipant.java
src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedTopologyInstalledUnitAuthorizationParticipant.java
src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java
src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/ProtectedTopologyInstalledUnitAuthorizationParticipantTest.java
src/test/java/dz/sh/hidra/modules/identity/infrastructure/persistence/ProtectedTopologyInstalledUnitAuthorizationPostgresIntegrationTest.java
src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java
.github/ci-test-inventory.json
doc/roadmap/TASKS_HPS-006.md
```

This explicitly supersedes the prior seven-file allowlist and byte-exact preservation
of the two issuer/evaluator methods only for their fixed per-requirement code
selection. Their sampling/group/context/evaluation algorithms remain; no wider
Identity rewrite is registered. Keep the prior reviewed reader-pin amendment bounded
to its installed-unit-family admission branch, updating the actual source hash only
after complete delta review. All other reader/J/provider source pins remain mandatory.

Preserve every old case and constructor. Add meaningful unit/native PostgreSQL cases
for both permission/resource bindings and six descriptors, missing either component,
either-unit/installation policy denial, wrong permission-row resource_type, forged
or substituted descriptor/resource, malformed/oversized/same-unit requests, company/
facility shape, revocation/expiry and all four genuine native producers. Fixtures
must create distinct correctly typed permission rows and grants for both codes;
setting one permission's resource_type to a wildcard is not proof. Inventory records
actual source/case hashes only. Neither Topology replacement DML, additive migration,
receipt format, HTTP API, budget, schema/dictionary nor workflow is changed by this
native prerequisite. The previous B writer/C candidates remain subsequent, with the
corrected permission pair carried through their public contract and qualification.

**Validation and boundary.** Confirmed all nine existing paths, exact three affected
native code-selection/admission seams and the shared permission-row invariant.
Documentation/link, append-only single-file scope and whitespace checks precede
verified parent/tree/blob publication directly to main with expected-head guard.
Observe applicable exact-SHA Docs creation/start and STOP. No local Java/PostgreSQL
rerun is needed for this registration. Next selects the corrected native prerequisite
only; its implementation/CI acceptance remains pending. Accepted A/seven-operation B
writer remain accepted; integrated HAPI-599/B replacement/C and all dependent tasks,
frontend, simulation/P3, SITE, release and deployment remain open/unselected.

### HAPI-599 B — native atomic replacement authorization implementation — 2026-10-29

**Selected bounded stage.** Owner Next refreshes main
`e92b641697f5641ae7e9295d2eb0f7f544fb0d07`, Docs704/run40857711668 SUCCESS.
Unchanged accepted runtime retains Full969; the corrected nine-file registration
now executes under exact message:
`feat(identity): authorize atomic installed-unit replacement`.
CREATE/DELETE none; MODIFY exactly the nine registered paths. This delivers the
Identity prerequisite only, not replacement DML, a schema change or a public API.

**Implemented typed descriptor and permissions.** Append REPLACE to the public
operation enum and replacementUnitId to Request, retaining the seven-argument
constructor for all seven accepted operations. Replacement requires distinct unit
IDs, distinct installation IDs and equal non-null source/destination facility;
existing exact bounded reference validation applies to every added descriptor.
Legacy operations reject a second unit. Canonical immutable requirements contain
two INSTALLED_UNIT company targets and two INSTALLED_UNIT_INSTALLATION targets,
each installation requiring company plus facility, exactly six decisions. Identical
ID text across different resource types does not collapse separate targets.

The resource-specific permissionCode overload validates its operation/resource
combination and returns topology:installed-unit:replace-unit or
topology:installed-unit:replace-installation for the two respective types. The
one-argument method still returns every old code unchanged and rejects REPLACE,
preventing selection of an incomplete single component. No generic replace alias,
wildcard or caller-provided permission array. Public descriptor/scalar construction
still creates no admission or claim authority.

**Minimal native integration.** The actual native group builder chooses each
child's fixed code from its exact Requirement.resourceType. The public participant
independently compares that expected code, resource ID/type, company/facility and
common native origin. Its evaluation algorithm, deny precedence and materialized
contribution semantics remain. The existing reader's installed-unit-only admission
branch validates allowed operation/resource combinations before resolving the code;
all generic permission row cardinality/activity/exact resource_type checks remain
byte-exact. Unit FACILITY scope remains prohibited. All other reader families,
opaque group/context/claim consumption, one native sample, owner/thread/holder/PID/
XID binding, temporal collector, native source lifetime, current rechecks, fences
and poisoning/commit veto mechanisms remain unchanged. Beyond header dates, the
issuer/evaluator production changes are exactly their two code-selection call sites.

The admitted reader delta is pinned to its actual SHA256
4cb50a86bf8e307a742fb64e29fdfcccab24cb793e3987a1d2234a7dd004f27d,
replacing the explicitly superseded3b734e9f pin. The existing guardrail assertion
remains active; every J/provider hash is preserved. No production role, grant,
permission seed, schema, migration, datasource, budget or workflow is changed.
Topology's accepted seven-operation writer and its version1 canonical receipts are
byte-exact; the separate physical replacement writer is still pending.

**Authored regression evidence; hosted runtime pending.** Preserve all original
unit6/native19/guardrail9 case identities. Add five unit cases: the two code bindings
and unsupported/single-code rejection, six immutable distinct targets, malformed
replacement and forbidden legacy second-unit descriptors, retained legacy constructor
semantics and denial of any one of the six targets. Unit suite now11 cases.

Add six actual PostgreSQL cases, suite now25: mixed permission types through genuine
LOCAL/LDAP/AD/OIDC producers, missing either component or facility, denial of either
unit/installation identity, wrong permission-row resource_type, request substitutions
with swallowed-failure rollback and rechecks of both revoked/shortened components.
Negative grant/type/policy cases retain positive controls after restoring valid
fixtures. Existing real reader calls are observed, never replaced with fabricated
graphs or decisions. Directory coverage delegates to the actual
LdapCredentialVerificationAdapter and verification registry with only external LDAP
transport mocked, following the already accepted lifecycle fixture; source capsules,
native completion and private issuance fences remain real. Public native evidence
is checked for all six exact descriptor/code bindings and actual authentication type.
Company/unit ownership and business replacement are deliberately not claimed from
this Identity-only fixture. No concurrency/expiry test is removed or relaxed.

Inventory changes only the three modified suite hashes and eleven actual added
case names; all other cases, timing/classification/shard assignments and four
historical exclusions remain unchanged. Active500 classes now require3,750 exact
cases across the same ten PostgreSQL shards. No skipped or reclassified case.

**Available local validation.** All seven modified Java sources parse under available
Java17. Twenty-three probes compile and execute actual prior/current public-contract
sources in isolated class loaders, comparing original operation codes/requirements,
same-facility relocation and constructor compatibility, plus new six-target/two-code
and invalid-descriptor behavior. This is limited pure contract proof, not integrated
Java21/Spring/JUnit/native PostgreSQL verification. Nine actual forensic source-check
methods pass under a minimal assertion harness. Inventory, offline OpenAPI and88
validator self-tests pass (35 inventory,53 full evidence), with documentation/link,
exact nine-file scope, pin preservation and whitespace checks before publication.
Focused Maven exits127, mvn: command not found; no environment is provisioned solely
for local tests. No new runtime pass or catalog capture is invented.

Publish verified blobs/tree/parent directly to main with expected-head guard;
observe applicable exact-SHA Full/Fast/Docs creation/start and STOP. This native
replacement prerequisite is implemented, not CI-accepted. After exact-head success
and independent evidence review, revalidate/register the existing B replacement
writer candidate before its runtime mutation. B replacement, C/API and integrated
HAPI-599 remain pending; accepted A and seven-operation B writer remain accepted.
HAPI-600/601, frontend, simulation/P3, SITE, release and deployment are unselected.

### HAPI-599 B — accepted native replacement prerequisite and writer registration — 2026-10-29

**Selected single registration stage.** Owner Next refreshes main
0bc010986868cf336fcdc0a03017b0513737eb87, tree
bb5c32ba4b9db537767e333484fb920e8995dbc2. Full970/run40859834953,
Fast/run40859834846 and Docs/run40859834836 are SUCCESS on that exact SHA.
The preceding implementation requires exact-head success and independent evidence
review before revalidating/registering the physical writer. This stage satisfies
that gate and makes the existing candidate executable; it does not implement DML.
Exact registration message: `docs(topology): register atomic installed-unit replacement writer`.
MODIFY only this phase, append-only; CREATE/DELETE none.

**Independently checked hosted evidence.** Downloaded genuine aggregate artifact
12891327674, hidra-full-ci-evidence-0bc010986868cf336fcdc0a03017b0513737eb87-40859834953-1.
ZIP SHA2564555078f0151448cb168dab1d92037e8504d3b806a86c2fff29138f796bc7d53
matches the GitHub artifact digest. The unchanged repository aggregator independently
reproduces gate.json exactly, SHA256
53b3de8fe75d466a57827dbac173949286b4a81e1df50cd4a2a2f9a2e1ecd9d2.
All500 active classes and3,750 exact cases are present: zero errors, failures or skips,
all ten PostgreSQL lanes, attempt1, cached dependencies. This is development-
partitioned evidence, serial_oracle=false, same_run_equivalence=false and
release_eligible=false; no serial-oracle or release qualification is inferred.

Actual focused reports: native installed-unit authorization25 cases/53.119s,
public authorization participant11/0.159s, admission boundary9/0.310s,
accepted seven-operation writer23/65.157s and protected executor54/1.038s,
all zero errors/failures/skips. Native suite includes all four genuine producers,
both permission components, six descriptor bindings, wrong permission-row types,
missing grants, denial, substitution rollback and revocation/expiry. This accepts
the bounded Identity replacement prerequisite, preserving the prior A and seven-
operation B acceptance; it does not establish physical replacement or transport.

Hosted PostgreSQL16.15 catalog remains186 migrations/470 JPA mappings/532 relations,
zero unresolved owners, reviewed dictionary checked. Current catalog confirms the
receipt has no replacement_unit_id and its validated operation check is exactly
hidra_topology_installed_unit_operation_request_operation_check, permitting only
REGISTER_UNIT, RECORD_INSTALLATION, CLOSE_INSTALLATION and RELOCATE. The current
receipt unit/company FK remains nondeferrable with existing NO ACTION behavior;
do not rewrite it when adding the separate replacement FK. The accepted native
permission pair supersedes every earlier single replace-code proposal.

**Revalidated live implementation seams.** The Topology Command enum still has the
original seven operations; Request bridging still uses its compatible seven-argument
Identity constructor. Hint/Snapshot/Result each expose one physical unit. Coordinator
acquisition and spare final read cover one manufacturer; they must cover both stored
units, using stable manufacturer identity ordering and deduplication with separate
one-use spare reads. Current position/company/native origin checks, final fence union
and authority lifetime remain mandatory. UNKNOWN manufacturer remains UNKNOWN.
Adapter observation, state/final-guard images and replay verification currently bind
one unit. Extend those exact images to both stored unit identities for REPLACE so
an omitted/replaced second unit cannot pass a final guard or receipt replay.

All four existing mutation SQL strings are fixed; coordinator and executor require
exact hashes. Existing binary command/result envelopes write version1 and decode
strictly with canonical re-encoding, a1MiB total bound and bounded UTF-8 fields.
Preserve all seven old command/result bytes and historical receipt replay. Add only
a distinct version2 encoding for REPLACE, binding both full physical-unit identities
and installation descriptors in canonical command/result bytes. Retain old public
constructors with null optional replacement unit; forbid second-unit descriptors
for legacy operations. No decoder fallback, hash bypass or caller-supplied SQL.

**Next executable B writer — exact message and allowlist.**
`feat(topology): add atomic installed-unit replacement`.
CREATE exactly:

```text
src/main/java/dz/sh/hidra/modules/topology/application/model/InstalledUnitLifecycleFailure.java
src/main/resources/db/migration/V20261029_001__topology_installed_unit_replacement.sql
```

MODIFY exactly:

```text
src/main/java/dz/sh/hidra/modules/topology/application/port/in/InstalledUnitLifecycleUseCase.java
src/main/java/dz/sh/hidra/modules/topology/application/port/out/ProtectedInstalledUnitRepositoryPort.java
src/main/java/dz/sh/hidra/modules/topology/application/service/InstalledUnitLifecycleApplicationService.java
src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedInstalledUnitAdmissionCoordinator.java
src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcProtectedInstalledUnitRepositoryAdapter.java
src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java
src/test/java/dz/sh/hidra/modules/topology/application/service/ProtectedInstalledUnitAdmissionCoordinatorTest.java
src/test/java/dz/sh/hidra/modules/topology/application/service/InstalledUnitLifecycleApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/ProtectedInstalledUnitLifecyclePostgresIntegrationTest.java
src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java
.github/database-dictionary-ownership.json
.github/ci-test-inventory.json
doc/roadmap/TASKS_HPS-006.md
```

DELETE none. All13 MODIFY paths exist; both CREATE paths are absent. The migration
filename is free after186 published files ending V20261028_005; recheck immediately
before writing and preserve every published migration byte. No production Identity,
Party provider, domain-model, REST, OpenAPI, workflow or budget change is authorized.

**Admitted behavior and storage delta.** REPLACE closes one open source installation
and starts a distinct registered physical unit at the same exact position/facility/
company, at one microsecond-precision effective instant strictly after source start.
Require distinct unit/installation IDs, exact expected source revision, same actual
equipment-type identity for both units and position, and bounded source evidence.
Replacement is open-ended revision1. Retain original source provenance/recordedAt;
new recordedAt uses the common native sample. No implicit registration, serial/model
edit, cross-company move, different-position swap or Assets qualification. Existing
half-open exclusion constraints reject any incompatible current/future occupancy.
Closure, replacement insert, immutable events and one receipt commit or roll back
together. Fresh native authorization includes both typed permission components and
all six exact requirements before any result disclosure, including receipt replay.

Add nullable replacement_unit_id varchar(80) to the existing receipt, one validated
nondeferrable composite FK to installed_unit(id,company_party_id) with ON UPDATE/
DELETE RESTRICT, and a named operation/descriptor check requiring non-null distinct
replacement ID for REPLACE and null for old operations. Replace only the captured
operation check to admit REPLACE; preserve all existing checks, FK semantics, primary
key and immutable/origin trigger/function bytes. Do not fabricate historical IDs.
Catalog admission and final guard must validate the actual new column type/nullability,
FK keys/actions/validation/deferrability, its exact native RI triggers and check
semantics, alongside all existing source/privilege/origin/constraint guards.

Retain four old SQL strings/pins byte-exact. Add one complete fixed replacement
receipt INSERT and independently pin its actual hash in coordinator and executor;
no prefixes, wildcard shape or new caller. Reuse reviewed closure/installation DML
and the existing installed-unit private credential route and finite1000ms work cap.
Native authority expiry, statement/lock bounds, final SQL ordering, rollback poisoning
and indeterminate commit handling remain unchanged. Do not increase the budget based
on unmeasured replacement behavior.

Typed failure categories retain INVALID/UNAUTHENTICATED/DENIED/NOT_FOUND/CONFLICT/
UNAVAILABLE/INDETERMINATE. NOT_FOUND requires fresh exact authority/owner checks;
CONFLICT requires a proven authorized revision, request or owned occupancy conflict.
Catalog/origin/privilege/unknown SQL errors fail closed, preserving private diagnostics.
Do not map all integrity failures to business conflicts or perform queries after
poisoning/sealing. Preserve protected executor indeterminate outcomes. No REST cause-
message parsing or success conversion is admitted.

**Required verification and publication hold.** Keep all23 accepted writer cases
and existing unit/executor cases. Add genuine PostgreSQL coverage for all four native
origins, either unit/facility denied, both known manufacturers/different manufacturers/
UNKNOWN, occupied replacement unit including future overlap, source revision and
expiry, racing replacements, injected failures after closure and after insertion,
zero partial events/receipts, idempotent replay without restoring projections, changed
second-unit/operation conflict, old version1 receipt compatibility, missing new FK/
privileges and actual final source/role/time rejection. Unit checks cover descriptor
shapes, old constructors, exact bytes, two-unit owner facts, typed error handling and
the fifth fixed SQL pin. Register only real added case/source hashes; retain ten shards.

Use available tooling and the focused lifecycle/coordinator/service/executor suites
under AGENTS11.1. Missing Maven/services are reported, not provisioned solely for local
verification. Publish the bounded writer directly to main with expected-head guard,
verify parent/tree/blobs and observe applicable Full/Fast/Docs startup, then STOP.
Schema dictionary publication remains on hold for genuine hosted187-migration catalog
capture and its separately registered truthful dictionary follow-up; no physical metadata
is inferred. Full CI, dictionary reconciliation and independent replacement evidence
are required before B replacement acceptance. C/API, integrated HAPI-599, HAPI-600/601,
frontend, simulation/P3, SITE, release and deployment remain pending/unselected.

This registration runs documentation/link, append-only scope and whitespace checks,
then verified single-file publication with expected-head guard and Docs startup.
No new runtime test pass or physical replacement implementation is claimed here.
Next executes the exact15-file B writer scope above; it does not repeat registration
or automatically implement C. An actual newly discovered scope contradiction must
be documented before any extra file mutation.

### HAPI-599 B — atomic installed-unit replacement writer implementation — 2026-10-29

**Selected registered runtime stage.** Owner Next refreshes main
cfadeedf2f8f64eef9fba9c85d8ca4e61b60dc60, tree
df6c1427efe61388cf5fe6ed80add546b688f755, Docs/run40861524936 SUCCESS.
Its unchanged runtime retains independently verified Full970 acceptance of the native
replacement prerequisite. Execute exact message
`feat(topology): add atomic installed-unit replacement` and the registered15 paths:
CREATE2, MODIFY13, DELETE none. This implements the physical writer, not C transport
or integrated HAPI-599 acceptance.

**Atomic typed operation.** Append REPLACE and replacementUnitId to Command;
retain its original13-argument constructor. Require different physical units and
installation IDs, equal exact source/destination position and facility, bounded
references/evidence, expected source revision and one microsecond-precision close/
start instant. Legacy operations reject a second unit. The source must be open and
the instant strictly after its effective start. Extend Hint/Snapshot/Result with
optional replacement unit facts and compatible old constructors; replay retains
both units. Both stored units must belong to the exact company and have the actual
position's equipment-type identity. No implicit registration, serial/model edit,
foreign-company transfer, different-position swap or engineering/Assets qualification.

The coordinator bridges to the accepted two-code/six-requirement native Request;
one genuine native authorization sample and exact owner contributions still govern
all effects and disclosure. Acquire known manufacturer tickets in sorted identity
order, deduplicating shared manufacturers and retaining separate one-use spare
reads for final preparation. Verify each actual contribution's native coordinates
and validity; take the minimum bound across both manufacturers and native authority.
UNKNOWN remains UNKNOWN. Position/company checks, current checks, opaque admissions,
exact fence union and poisoning stay active. Both units and both manufacturer source
identities are included in storage observation and final state/source images.

The existing closure CAS and insertion execute in one provisional window. New
installation starts at the source close, is open-ended revision1 and uses the common
native sampledAt for recordedAt; old provenance/recordedAt and immutable revisions
remain. Existing unit/position exclusion constraints reject incompatible current or
future occupancy. Closure, replacement insertion, event revisions and exactly one
receipt commit together or roll back. Replay requires fresh exact authorization,
checks immutable history and never restores a later changed effective projection.

**Canonical compatibility and bounded SQL.** Every legacy command/result retains
version1 bytes, field order, strict UTF-8/bounds, hashes and historical replay. REPLACE
alone uses version2, explicitly binding the second unit in command bytes and both
full unit facts in the result. Unknown versions, incomplete/noncanonical/trailing
payloads fail closed. Existing four mutation SQL texts and their hashes are unchanged.
Add only the complete replacement receipt INSERT, SHA256
1503d363ee40899cad0081f42e84f728d974409236ebc5fe96e631e571330495,
independently pinned in coordinator and executor. No caller/credential route, SQL
prefix, budget, authority expiry, lock/statement policy or final-statement relaxation.
The accepted installed-unit1000ms cap and other families' budgets remain unchanged.

**Additive schema and catalog guards.** The free filename is now the187th migration,
V20261029_001__topology_installed_unit_replacement.sql. It adds nullable
replacement_unit_id varchar(80) to the immutable receipt and the validated
nondeferrable fk_installed_request_replacement_unit_company composite FK to
installed_unit(id,company_party_id), ON UPDATE/DELETE RESTRICT. Replace only the
previous operation check with ck_installed_request_operation admitting REPLACE;
ck_installed_request_replacement_unit uses an unambiguous CASE requiring a distinct
non-null replacement ID for REPLACE and null for every old operation. Existing
receipt FK/actions/checks, all186 published migration bytes and trigger/function
bodies remain unchanged; no historical row rewrite or production role/grant seed.

Catalog and sole final guard verify actual appended column type/position/nullability,
FK keys/actions/validation/deferrability and all four exact native RI functions,
plus both compiled check expressions. Only whitespace/parentheses are normalized
for these fixed ANY/CASE expressions, whose token sequences and CASE structure
remain exact. No arbitrary predicate is accepted. Existing privilege/catalog/trigger/
origin invariants remain. The ownership manifest adds genuine migration provenance
for the existing receipt; reviewed dictionary/schema/OpenAPI snapshots stay unchanged.
Their next refresh awaits an actual hosted187-migration catalog, not inferred metadata.

**Typed owner failures.** InstalledUnitLifecycleFailure exposes fixed INVALID,
UNAUTHENTICATED, DENIED, NOT_FOUND, CONFLICT, UNAVAILABLE and INDETERMINATE categories
with non-disclosing messages and retained private causes. NOT_FOUND is emitted for
an absent installation only after fresh exact native and owner checks. Proven stale
revision/owned request/occupancy conflicts retain CONFLICT. Only the two exact native
occupancy exclusion constraint diagnostics receive SQL conflict classification;
unknown SQL/integrity/catalog/origin/privilege failures remain unavailable/fail-closed.
Receipt hashes/canonical result validity are checked before classifying a changed
owned request. No query follows poisoning; executor indeterminate commit outcome
always dominates an earlier business meaning and cannot return success.

**Authored verification; authoritative CI pending.** Preserve all existing case
identities, adding25 exact cases in the same four suites. Coordinator now14 cases,
service8, executor55 and native lifecycle38 (all23 original lifecycle cases retained).
New PostgreSQL cases exercise four genuine producers, both permissions/facility,
either unit/installation denial, current/future occupancy, source revision and expiry,
fault injection specifically after closure and after new insertion, zero partial
projection/events/receipts, version1 replay and replacement replay after later close,
changed second unit/operation, missing FK/privilege, actual final source/role/time
rejection, distinct/shared manufacturers with independent spare reads, UNKNOWN,
foreign company/different equipment type, competing replacements and authorized
absence versus revoked authority. Actual native issuers/owner adapters/storage/commit
machinery execute; only external transports and explicit failure points are mocked.
The JdbcTemplate spy delegates real calls except the selected injected failure.
Manufacturer fixtures clean their temporary role assignments to preserve old tests.

Inventory changes only four suite hashes and actual added case names: active500
classes/3,775 exact cases, same ten PostgreSQL shard assignments, timings/classification,
all old cases and four historical exclusions. No workflow change, omitted or skipped case.

**Available local evidence and limits.** Modified Java syntax checks and compilation
of actual writer/domain/public owner contracts against existing minimal framework
API stubs are useful bounded checks, not Spring/Java21/JUnit/PostgreSQL runtime proof.
Seventeen isolated actual prior/current codec probes pass: all seven legacy command
bytes and legacy result bytes unchanged, old constructor compatibility, replacement
version2/second-unit binding/roundtrip/replay and malformed receipt rejection.
Nine forensic source checks pass under the existing minimal assertion harness.
The35 inventory and53 full-evidence validator self-tests pass; offline OpenAPI
snapshot validation passes at306 operations/282 paths/323 schemas, unchanged.
Focused Maven exits127, mvn: command not found; no environment is provisioned solely
for local verification. Documentation/link, inventory, exact scope/SQL-pin/migration
preservation and whitespace checks precede publication. No hosted pass or physical
catalog capture for this new writer is claimed yet.

Publish verified tree/blobs/parent directly to main with expected-head guard; observe
applicable Full/Fast/Docs creation/start and STOP. B replacement is implemented,
not accepted. Keep dictionary publication on hold until genuine catalog capture and
the separately registered truthful dictionary follow-up; accept B only after complete
exact-head evidence review. C/API and integrated HAPI-599 remain pending. HAPI-600/601,
frontend, simulation/P3, SITE, release and deployment remain unselected.

### HAPI-599 B — exact-head replacement CI repair envelope — 2026-10-29

**Owner Fail; bounded two-stage repair only.** Main122df88dd07524cfc6295b4124f1dd76ada9b2bf,
tree0fdf0a7e7d7880129570344c70fa8e71ea7b8931, has Full971/run40864071179 FAILURE;
Fast/run40864071144 and Docs/run40864071168 SUCCESS. Static and Java21 unit/architecture
jobs pass; nine PostgreSQL shards pass. Shard8 reports220 cases, zero failures/skips
and one error. The actual lifecycle report has38 cases,37 passing and one error,
denialOfEitherPhysicalUnitOrInstallationRejectsEntireReplacement, at its restored
positive control (line1182), not the preceding expected denial/rollback assertion.
No aggregate or complete replacement acceptance is claimed from this failed run.

**Source-backed cause and minimal fix.** The new fixture writes status=INACTIVE to
Identity authorization_policy. PolicyStatus permits DRAFT, ACTIVE, DISABLED, RETIRED;
INACTIVE is malformed evidence. Reader.state uses Enum.valueOf(PolicyStatus,...),
and the native group reader deliberately rejects corrupt evidence, producing
UNAVAILABLE through protected execution rollback. Preserve that fail-closed behavior.
Restore the test policy using a parameter bound to PolicyStatus.DISABLED.name(),
then retain the real authorized positive control and all four denial targets.
No production Identity/Topology, exception mapping, reader pin, deadline, SQL,
constraint, migration or workflow change is necessary or admitted for this defect.

**Two exact commits, one final guarded branch advancement.** The current owner Fail
repairs both demonstrated blockers in the existing CI-first repair order. First:
`fix(topology): restore valid disabled policy fixture`.
MODIFY only the lifecycle PostgreSQL test, its existing .github/ci-test-inventory.json
source hash and this append-only phase. No cases are removed/added/renamed; active500
classes/3,775 exact cases and ten shards remain. CREATE/DELETE none.

Second, the already required truthful dictionary follow-up:
`docs(database): refresh installed-unit replacement dictionary`.
MODIFY only doc/database/DATA_DICTIONARY.md and this append-only phase; no manifest,
source, migration, CI policy, OpenAPI or schema mutation. Both stages are prepared
and reviewed before publication; retain separate commits and verify each tree/parent/
blob set. Advance main once to their final head with expected122df88 guard and
observe applicable exact-head Full/Fast/Docs creation/start, then STOP. This envelope
is CI repair plus the registered derived-artifact follow-up, not later roadmap work.

**Genuine catalog blocker and evidence.** The API/dictionary job migrated187 scripts
and captured PostgreSQL16.15/470 mappings/532 relations, zero unresolved owners.
It failed only the reviewed dictionary equality check; its schema ZIP artifact
12893101259 has verified SHA256
7c81a2a7fdda656bd3c0c9a457813c94f759a77d696b52fa7f03acee44c58796.
Actual catalog/source capture SHA is122df88dd07524cfc6295b4124f1dd76ada9b2bf;
source bundle3d52c5026c50b48591e8f34e8905bc92b60ebcf31176512abdc60fc6e273aafb,
catalog JSON9f7c6ddf872313f84a6c251ef6f52ee80e2bb4190712a0b1bdbd9cd0dbf0062a,
rendered dictionary63ffec73e5342133ec801d02d41c18c5a7d1b3d8ab213d6b42f69990920dc436.
The unchanged generator must validate current migration/JPA source inventory and
ownership against that capture, reproduce the artifact Markdown byte-exact, and
check the replacement reviewed dictionary before stage2. The fixture-only repair
cannot invalidate that schema source bundle. No physical facts are guessed.

Shard8 diagnostic ZIP12893605939 SHA256
2496c2c5448b2011f8e4181fd2bf68a5df53ba31fd0816c0ff163d861c99bc73
is independently verified; its raw Surefire report confirms the exact failing case,
private cause chain and37 passing lifecycle cases. It is diagnostic evidence, not
passing lane evidence. Local Java syntax, inventory/enum-source and whitespace checks
are useful; Maven remains absent (mvn: command not found), so corrected native runtime
acceptance awaits the final head's hosted CI. No local environment is provisioned.

**Hold update.** This first commit repairs the fixture only. Dictionary publication
hold is satisfied only by stage2's validated real capture; B replacement acceptance
still requires complete exact-head Full success and independent evidence review.
C/API, integrated HAPI-599, HAPI-600/601, frontend, simulation/P3, SITE, release and
deployment remain pending/unselected. No task is closed by fixture repair or schema
capture alone.

### HAPI-599 B — truthful replacement dictionary follow-up — 2026-10-29

**Second registered repair stage.** The verified fixture repair object is
b1a7a3d28888f93c9899f05dd259f0e1974cf8f3, tree
43f5c1780a42dd1808dac1d958f4cfc37b89472e, parent122df88dd07524cfc6295b4124f1dd76ada9b2bf.
Main remains at the expected parent until this prepared two-stage envelope is complete.
Exact message: `docs(database): refresh installed-unit replacement dictionary`.
MODIFY exactly doc/database/DATA_DICTIONARY.md and this append-only phase;
CREATE/DELETE none. No production, migration, ownership manifest, documentation
manifest, test, OpenAPI or workflow change in this second commit.

**Actual generation and review.** Validate source122df88dd07524cfc6295b4124f1dd76ada9b2bf
schema.json from genuine artifact12893101259 using the unchanged generator and
current .github/database-dictionary-ownership.json. All migration/JPA source hashes
match the captured inventory; the fixture repair changes no schema inputs. Re-rendered
Markdown matches the hosted DATA_DICTIONARY.md byte-exact, SHA256
63ffec73e5342133ec801d02d41c18c5a7d1b3d8ab213d6b42f69990920dc436.
The actual migrated PostgreSQL16.15 catalog contains187 migrations,470 JPA mappings,
532 relations and zero unresolved owners. Its source bundle and catalog/ZIP hashes
are recorded in the preceding repair stage; capture source remains the genuine
runtime commit rather than relabeling it as this documentation commit.

The complete dictionary delta has43 unified-diff lines: capture/source-bundle/count
metadata, new migration mentions on installed_unit and operation_request, the new
replacement_unit_id column and its actual validated composite FK/RESTRICT actions,
new operation and CASE descriptor checks replacing the previous operation check.
Other relations and existing trigger/function evidence are unchanged. No guessed
physical field, fabricated catalog capture or edited generated predicate.

Run generator --input/--source-sha/--ownership/--check against the replacement reviewed
dictionary, requiring actual captured-source equality and reviewed_dictionary_checked=true;
then documentation/link, exact two-file scope and whitespace checks. The generator
also independently reproduces the artifact render; no local PostgreSQL provisioning
or new runtime pass is claimed. The prior one-case fixture error still requires the
final repaired head's hosted success. All3,775 exact tests and ten shards remain mandatory.

**Updated hold.** The derived dictionary refresh obligation is now satisfied from
real catalog evidence; remove that artifact-publication hold only. B replacement
acceptance remains on hold for full exact-head CI and independent evidence review.
Advance main once to this final descendant using the original122df88 expected-head
guard, confirm Full/Fast/Docs creation/start on that SHA, then STOP. No wait for
completion and no automatic C/API or task closure. Integrated HAPI-599 and all later
roadmap, frontend, simulation/P3, SITE, release and deployment work remain unselected.

### HAPI-599 Package B — atomic replacement acceptance and Package C API implementation registration — 2026-10-29

**Selected one-stage owner Next, exact acceptance source.** Main
`99704afede97f28ff33562c6ed56441f87b8b771` (replacement
implementation `122df88dd07524cfc6295b4124f1dd76ada9b2bf`,
test-fixture repair `b1a7a3d28888f93c9899f05dd259f0e1974cf8f3`,
and genuine replacement dictionary `99704afede97f28ff33562c6ed56441f87b8b771`).
Documentation #708/run `40866014609`, Fast Feedback #271/run
`40866014613`, and Full #972/run `40866014637` completed
SUCCESS on this identical source. The Full final-gate job
`123425605540` confirms the same source SHA/run/attempt1, 500
classes and 3,775 cases, **zero failures/errors/skips**, all ten
PostgreSQL shards, unit/architecture, static and API/dictionary lanes
successful. PostgreSQL shard8/job `123420858108` verifies its 15
classes, 220 cases and zero failures/errors/skips. The artifact
`hidra-full-ci-evidence-99704afede97f28ff33562c6ed56441f87b8b771-40866014637-1`
is uploaded as artifact ID `12894123153`; the hosted log records ZIP
SHA256 `361712cf24735c07f5266c56ca6cf2015f7d6d89f3fd451e51c656ced92ab3f3`.
The final gate reports development-partitioned/cached,
`serial_oracle=false`, `release_eligible=false`,
`optimized_adoption_eligible=false` and `same_run_equivalence=false`.
This is source-linked hosted CI, not local operational/serial proof.

**Independent scope review and result.** At this exact SHA,
`InstalledUnitLifecycleUseCase` exports eight typed operations including
REPLACE, preserving the original constructor; REPLACE binds two distinct
units/installation identifiers, one company and equal actual source/
destination positions with microsecond-precise close/start.
`ProtectedInstalledUnitAdmissionCoordinator` acquires current Identity,
Party manufacturer and Topology position contributions, retains source
fences and performs final/current guards. The application service
executes the protected physical transaction and converts only typed
failures; an indeterminate commit is not reported as success. The
JDBC repository checks the additive receipt FK/check/catalog, a fixed
fifth replacement receipt SQL, matching equipment kind, canonical
version2 while retaining version1 replay, current state and atomic
closure/insertion/events/receipt with rollback. The actual PostgreSQL
suite contains `atomicReplacementCommitsAndReplaysThroughAllFourGenuineNativeProducers`,
`replacementDeniesMissingEitherPermissionComponentOrFacilityWithoutWrites`,
`denialOfEitherPhysicalUnitOrInstallationRejectsEntireReplacement`,
`replacementRejectsCurrentAndFutureOccupancyAndRollsBackSourceClosure`,
`replacementFailureAfterClosureOrAfterNewInsertionRollsBackAllEvidence`,
`replacementReplayDoesNotRestoreLaterClosedProjectionAndLegacyReceiptsStayVersionOne`,
`replacementRejectsMissingNewForeignKeyOrPublisherPrivilege` and
`replacementFinalGuardRejectsActualSourceRoleAndTimeChanges`.
The earlier #971 one-case invalid-policy fixture failure is repaired
with the defined `DISABLED` policy status; its failed run remains historical
evidence, not suppressed. Fresh Flyway/dictionary validation succeeds on
the corrected 187-migration source; no migration was rewritten.

**Bounded acceptance.** ACCEPT Package B's atomic cross-unit replacement
implementation and prior seven-operation lifecycle in the tested
development-only software scope. Clear the replacement writer,
fixture and physical-dictionary acceptance hold. Do NOT assert a
deployed permission seed, production use, industrial suitability,
serial uniqueness, equipment-model validation, standalone public
REST or a completed operator workflow. Package A and previously
accepted Party/Identity/position source contracts retain their own
acceptance; no reimplementation. HAPI-599 integrated remains
NOT_IMPLEMENTED/BLOCKED in the frozen ROADMAP/PHASES until Package C
public API/qualification and follow-up verification are accepted.
No HAPI-600/601, simulation/P3, OT actuation, frontend, SITE, release
or deployment is authorized by this review.

**Exact next executable Package C transport stage — REGISTERED, not executed.**
The present docs-only acceptance/registration commit message is
`docs(topology): accept atomic replacement and register installed-unit API`.
CREATE none; MODIFY only `doc/roadmap/TASKS_HPS-006.md`;
DELETE none. Next runtime implementation message:
`feat(topology): expose protected installed-unit lifecycle API`.

Next runtime stage CREATE exactly these 10 paths (all verified absent on
this main SHA):

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/InstalledUnitLifecycleRequests.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/InstalledUnitLifecycleResponses.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/InstalledUnitLifecycleRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/InstalledUnitLifecycleController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleApiExceptionHandler.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleRestMapperTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/InstalledUnitLifecycleApiPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/qualification/InstalledUnitLifecycleOperatorQualificationTest.java`

MODIFY only `.github/ci-test-inventory.json` (actual class/cases/hash,
preserve ten shards) and this append-only phase; DELETE none.
Do not edit the accepted installed-unit Domain/Application/Identity/
Party/Assets/executor/JPA/persistence/migration sources, native SQL pins,
security filter rules, budgets, dictionary, OpenAPI snapshots or CI.
If source reveals a necessary additional existing file, register its exact
scope before mutation; never bypass the allowlist.

**C REST contract and invariants.** Reconcile to the accepted B
`InstalledUnitLifecycleUseCase` and `InstalledUnitLifecycleFailure.Kind`;
use exact private typed adapter/DTO mapping, not persistence entities.
Base: `/api/v1/topology/installed-units`. Expose only:
`PUT /{unitId}` REGISTER_UNIT;
`GET /{unitId}` READ_UNIT;
`POST /{unitId}/installations` RECORD_INSTALLATION;
`POST /{unitId}/installations/{installationId}/close` CLOSE_INSTALLATION;
`POST /{unitId}/installations/{installationId}/relocate` RELOCATE;
`POST /{unitId}/installations/{installationId}/replace` REPLACE;
`GET /{unitId}/installations/{installationId}` READ_INSTALLATION;
`GET /{unitId}/installations/{installationId}/history` READ_HISTORY.
Return 200 for successful owner results including explicit replay; do
not claim 201 from replay or invention. Every request binds exact
company; every mutation supplies requestId and matching exact path,
body, source/destination/evidence/version constraints. Reject unknown
JSON fields, coercion, padded/over-80-codepoint IDs, timestamp precision
outside microseconds, invalid serial/manufacturer evidence semantics,
cross-operation fields, fabricated actor/permission/native claims and
unbounded history/disclosure. Current/history reads remain current-
authorized and company/resource-scoped. The history route is exact
installation history (at most two immutable versions), not a unit-wide
search/export. UNKNOWN and unverified serial/model/manufacturer evidence
remain explicit and nonqualifying; no cross-unit serial merge, search
or automatic engineering qualification.

**Error/security acceptance.** Use a controller-scoped non-disclosing
ProblemDetail handler, matching existing Topology REST patterns:
400 INVALID/parse, 401 genuinely unauthenticated,
403 DENIED, 404 only freshly authorized NOT_FOUND,
409 source-proven CONFLICT, 503 UNAVAILABLE/INDETERMINATE.
Never inspect private cause messages, expose exception chains, permit
anonymous scope, derive authority from caller DTOs or treat HTTP
authenticated status as native permission. Never change existing
security rules to `permitAll`. Native admission still precedes
protected existence/history/replay disclosure and every mutation
commits or rolls back as one transaction. No controller-managed ambient
protected transaction or second writer route.

**C acceptance cases and deferred derived artifacts.**
Require DTO/mapper/ProblemDetail/MockMvc negative coverage plus actual
Spring/security/native four-producer PostgreSQL HTTP coverage for
all eight operations, company/facility and either-unit denial,
authenticated missing versus unauthorized existence, exact replay
and stale/conflict, simultaneous replacement, absence of partial
events/receipts after injected failure, bounded two-version history,
repeated/unknown/unverified serial provenance, Assets separation,
and typed 503 indeterminate. Include a controlled operator
qualification matrix checking negative claims; it does not establish
industrial calibration or approve a human decision workflow.
Use exact-source inventory/case hashes and architecture tests.
After runtime/API capture, separately register a truthful generated
`doc/api/openapi.yaml` and `.github/documentation-validation.json`
snapshot follow-up with source/ZIP/provenance proof; do not hand-edit
snapshots or let an expected documentation mismatch waive CI.
CI-first publication then exact-SHA Full/Fast/Docs creation/start and
STOP. Separate independent API evidence acceptance is required before
integrated HAPI-599 status change. This docs stage requires only
append-only scope/source checking, whitespace/link validation and
guarded documentation CI startup, not new runtime execution.

### HAPI-599 Package C — protected installed-unit REST implementation — 2026-10-29

**Selected exact registered stage.** Docs #709/run `40868785249` on parent
`30506289c0ea44119c8326da35505c7444421dde` completed SUCCESS;
the unchanged accepted runtime baseline Full #972, Fast #271 and Docs
#708 all passed on `99704afede97f28ff33562c6ed56441f87b8b771`.
This stage has exact message
`feat(topology): expose protected installed-unit lifecycle API`;
CREATE the previously registered **10** Java production/test paths, MODIFY
only `.github/ci-test-inventory.json` and this append-only phase;
DELETE none. No existing Party, Identity, Assets, Topology Domain/
Application/persistence, SQL pin, migration, security configuration,
workflow, snapshots or frozen ROADMAP/PHASES paths are changed.

**Actual implementation.** The new transport-only
`InstalledUnitLifecycleController` maps the eight exact registered
routes under `/api/v1/topology/installed-units`:
PUT unit registration; GET unit; POST installation registration;
POST close; POST relocate; POST two-unit atomic replacement;
GET an exact installation; and GET its bounded immutable history.
It calls only the existing `InstalledUnitLifecycleUseCase.execute`
and requires a genuine non-anonymous Spring authentication before
asking the existing owner to perform native Identity and owner checks.
No native authorization is minted from input, and no alternate DML
writer, persistence repository or controllable transaction is exposed.

The request class defines five operation-specific strict JSON bodies
via the repository's Jackson3 custom `ValueDeserializer` pattern:
unknown JSON keys, numeric/string coercion, absent mandatory IDs,
padded or overlong references, noncanonical UTC instants or finer-than-
microsecond precision, forbidden native/actor descriptors, invalid
verification classifications and value/provenance mismatches reject.
The application-bound exact company and source/destination position
descriptors are supplied explicitly; read routes require exact company,
and installation/history require actual position and facility
descriptors. REPLACE includes a distinct physical replacementUnitId
and a separate new installation ID; equal position/facility and
one effective instant are bound to the already accepted typed
application command. Every write requires a requestId; all responses
use transport DTOs without JPA entities, native coordinates, SQL
details or implied engineering qualification. Typed ProblemDetail
maps INVALID to 400, UNAUTHENTICATED to 401, DENIED 403, authorized
NOT_FOUND 404, proven CONFLICT 409 and unavailable/indeterminate 503;
domain invalid values are also non-disclosing 400. Successful owner
results are HTTP 200 and keep their explicit replay indicator.

**Actual synthetic coverage authored; not yet hosted-pass claims.**
Five new test classes contain **20 actual @Test methods**:
- `InstalledUnitLifecycleControllerTest` — five MockMvc
  route/descriptor/unauthenticated/denial and input-rejection cases.
- `InstalledUnitLifecycleRestMapperTest` — five command,
  scope, replacement and immutable-response cases.
- `InstalledUnitLifecycleApiExceptionHandlerTest` — three
  typed error and private cause sanitization cases.
- `InstalledUnitLifecycleApiPostgresIntegrationTest` — three
  fresh-schema Flyway/Testcontainers checks for replacement receipt
  column, validated FK/check and immutable trigger. **This class
  currently verifies only the PostgreSQL catalog, not genuine
  authenticated native HTTP workflow.**
- `InstalledUnitLifecycleOperatorQualificationTest` — four
  controlled serial/provenance/UNKNOWN/Assets-reference semantics
  cases. These do **not** constitute industrial engineering
  qualification or a real operator decision workflow.

The inventory adds these five source-hashed classes in sorted order,
retains the previously registered 504 entries and ten PostgreSQL
shards, adds twenty named cases without removing any existing case,
and records new baseline timings as unmeasured=0. Actual content was
fetched from the five submitted Java blobs and SHA-256 computed using
a separately verified reference algorithm; no test result or CI
duration is fabricated. Existing accepted Package B native
PostgreSQL and executor tests remain intact.

**Validation and explicit acceptance hold.** Registered
paths and actual source, ID/class names, method declarations,
immutable migration/Flyway chain, previous inventory entries,
and current Package B contracts were source-inspected. Local
`mvn` and Docker are unavailable; the new Java/Jackson/Spring
compilation and tests have **not** been run locally. New source
blobs and inventory checks are preparation checks, not native
HTTP runtime evidence. Publish under AGENTS11.1 CI-first policy
only with verified tree/parent/blob/scope and expected-head guard;
observe new exact-SHA Full/Fast/Docs creation/start, then STOP.

**Package C qualification is NOT ACCEPTED by code authorship or
any green aggregate CI alone.** The registered requirement to
demonstrate genuine Spring-security/native-issuer PostgreSQL HTTP
success and denial for all eight operations/four producers, replay,
history, rollback and appropriate authority/facility revocation is
still outstanding in the new API-specific integration suite.
It must be registered as a bounded focused continuation or
repair once exact-SHA CI reveals any compilation or test defects.
Do not represent schema-only testing or MockMvc mocks as this
end-to-end acceptance. After real API capture, register and execute
the separate truthful `doc/api/openapi.yaml` and
`.github/documentation-validation.json` generated snapshot
follow-up, preserving actual source/manifest evidence and strict
compatibility checks; do not infer or handwrite OpenAPI metadata.
No HAPI-599 status closure, HAPI-600/601, frontend,
simulation/P3, production/SITE or deployment work follows
automatically from this source stage.

### HAPI-599 Package C — restore REST DTO boundary and negative test verification — 2026-10-29

**Owner Fail; exact-head verification.** Main
`186b6aa937cf3192dc7513caa7c38c6b3b094ee6` is the
preceding Package C implementation SHA. Full #973/run
`40871493395` and Fast #272/run `40871493349` both
FAILED; Documentation #710/run `40871493422` SUCCEEDED.
In Full #973 static validation and **all ten PostgreSQL shards**
SUCCEEDED. Java21 unit/architecture, API-contract canonical
OpenAPI comparison and the dependent Full gate FAILED.
No accepted replacement-writer regression was reported.
The API job reached runtime OpenAPI generation but stops at
`Runtime OpenAPI differs from canonical snapshot`; consequently
its later physical dictionary/compatibility artifact is not
completed. That is an independent outstanding generated
snapshot follow-up, not evidence of physical dictionary drift.

**Source-linked failures, scope, correction.**
Unit/Fast raw logs show HRA-102
`ForensicRemediationClosureTest.directWireDomainRepresentationLeakageRemainsAbsent`
rejecting the REST Request class directly referencing non-enum Domain
`InstalledUnit`. This repair replaces the wire-facing
`InstalledUnit.Evidence` fields with a strict, transport-owned
`InstalledUnitLifecycleRequests.Evidence` record carrying
value/classification/provenance, while the existing REST mapper
alone constructs the actual immutable Domain
`InstalledUnit.Evidence`. All three original known/unknown
classifications, source validation, current Party authorization,
company and physical identity constraints remain. The HRA-102
architecture guard is preserved unchanged.

`InstalledUnitLifecycleControllerTest.anonymousAndUntrustedPathNeverDiscloseProtectedUnit`
reported expected 400/actual 200 for a MockMvc URL literal containing
`%20company`; test request creation did not provide the decoded
padded `@RequestParam` value to Spring. The same negative test now
supplies `.param("companyPartyId"," company")`, exercising
the actual path-to-owner mapper's strict whitespace check.
The test continues to require 400 **before** owner calls; there
is no validator relaxation. The separate
`deniedAndIndeterminateOutcomesRevealNoPrivateEvidence`
case raised the previously stubbed DENIED exception while attempting
to restub via `when(...).thenThrow`; it now uses
`doThrow(...).when(...)` without invoking the former stub
and still asserts both 403 DENIED and 503 INDETERMINATE with
non-disclosing bodies. The qualification test expecting
`IllegalArgumentException` is corrected to the actual
`InvalidTopologyValueException` domain invariant for UNKNOWN
evidence with invented value/provenance. No domain code,
exception subtype or test assertion is removed.

**One bounded runtime repair stage.** Exact message:
`fix(topology): restore installed-unit REST transport boundaries`.
CREATE/DELETE none; MODIFY exactly:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/InstalledUnitLifecycleRequests.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/InstalledUnitLifecycleRestMapper.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/InstalledUnitLifecycleRestMapperTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/qualification/InstalledUnitLifecycleOperatorQualificationTest.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-006.md` (this append-only record)

Update only the three modified test-source SHA256 values in the
existing 509-class inventory. Preserve all original 20 new
cases and prior case names/locations, ten PostgreSQL shards,
full inventory acceptance and all five existing new tests.
The protected application, Domain, Identity, Party, Assets,
SQL pins, migrations, architecture checks, CI workflows,
physical dictionary, OpenAPI snapshot and its strict validator
remain untouched.

**Available validation and limitations.** Read and
compared current source to the four actual failed case
messages; the prior published Java code had already compiled
on Java21 in CI #973 and all ten PostgreSQL shards passed,
but the corrected source is not yet built/tested. Source-level
prepublication checks cover the exact bounded changes,
architecture import removal, retained request and
failure semantics and hash/case membership; these are NOT
passing Maven/JUnit/PostgreSQL runtime tests. Local Maven,
Docker and PostgreSQL runtime are unavailable; AGENTS11.1
CI-first applies. Commit reviewed blobs/tree/parent with
expected-head guard; observe applicable exact-SHA CI
creation/queue/start and STOP. Further failures select
responsible focused repairs before Package C acceptance.

**Explicit remaining Package C hold.** The new eight-route
runtime OpenAPI still requires the separately registered,
genuinely generated exact-SHA `doc/api/openapi.yaml` and
`.github/documentation-validation.json` refresh. Do not
hand-edit that artifact or weaken
`.github/scripts/validate_openapi_snapshot.py`.
Full's API-contract lane cannot pass on the stale
snapshot; this repair does **not** claim an all-green
Full run. The genuine authenticated multi-native-source
PostgreSQL HTTP behavior/denial/rollback qualification
registered for Package C also remains unimplemented:
schema-only PostgreSQL and MockMvc controller tests are not
an end-to-end replacement. Integrated HAPI-599 stays
NOT_IMPLEMENTED/BLOCKED; no HAPI-600/601, frontend,
simulation/P3, OT action, SITE or deployment is selected.

### HAPI-599 Package C — exact generated OpenAPI snapshot repair — 2026-10-29

**Owner Fail — selected bounded generated-artifact gate.** Main/source
`16524872e26292ff42af56d6e0a8f296cdf5c44b`. Full
#974/run `40873886180` FAILED only the API-contract OpenAPI
snapshot equality job and dependent full evidence gate; static,
Java21 unit/architecture and all ten PostgreSQL shards SUCCEEDED.
Fast #273/run `40873885994` and Docs #711/run
`40873886032` SUCCEEDED. The API-contract job
`123447620695` source application build step8 and deterministic
OpenAPI publication step9 both completed SUCCESS. Snapshot
equality step10 failed; physical dictionary capture, actual
base-runtime compatibility and final API evidence sealing were
skipped, not successful. The checked physical dictionary therefore
has no new validation claim from this failed API job.

**Exact owner-approved generated snapshot correction.** Use the
actual step9 `openapi/hidra-api.json` member from retained
`hidra-ci-diagnostics-16524872e26292ff42af56d6e0a8f296cdf5c44b-40873886180-1-api-contract`
artifact ID **12896792725**. The downloaded genuine ZIP
SHA256 is
`f81447e408bd77ad5e1d6a4a2bcae81126a9e4e46a7f728b6833d7bb3a838581`
and exact generated JSON member SHA256 is
`8c1df6418651530fae79a1884f5224014e4f5efe9d22fcab2bc05f95d2a226bf`.
Canonical `json.dumps(sort_keys=True,separators=(',',':'),allow_nan=False)`
of the actual artifact, with one trailing newline,
produces source-bound snapshot SHA256
`0c2b6876503aacb64c49bf113b9b9bcb35f7d64a4ce89c9519160758ecc053a3`
and Git blob SHA
`b9530693154ab0f19af24c4671097ad5c42c85d7`.
Source stamp remains actual `16524872e26292ff42af56d6e0a8f296cdf5c44b`,
not the new docs-only commit SHA; the unchanged comparator
excludes only that captured source field.

**Independent structural comparison.** The new generated
OpenAPI3.1 contract contains **289 paths, 314 operations and
333 schemas** versus the prior reviewed **282/306/323**.
Seven protected `/api/v1/topology/installed-units` path
objects contain eight registered operations; ten
`InstalledUnit*` request/response component schemas are
new. Removing only those seven paths and ten schemas,
and restoring the prior reviewed source SHA
`1ebdaec104c25d695f615a636cfd54f209acdea8`,
gives exact canonical SHA256
`51b183be7ee56f6137921847e5f985d0e27dcbd6277dd2178c6580b1c40095f1`.
All preceding operations, schemas, security schemes,
security requirements and non-schema components are
byte-canonical identical. This is additive comparison
to the current accepted snapshot, **not** hosted
base-runtime compatibility approval.

**One registered artifact repair stage.** Exact commit message:
`docs(topology): refresh installed-unit lifecycle OpenAPI snapshot`.
CREATE/DELETE none; MODIFY only:
- `doc/api/openapi.yaml` — actual canonical JSON
  generated by the above hosted source and ZIP, no manual edits
- `.github/documentation-validation.json` — source stamp,
  canonical SHA256, 289/314/333 counts, complete exact
  generation-step provenance with explicit Full #974/API-job
  failure and all previous review provenance retained
- `doc/roadmap/TASKS_HPS-006.md` — this append-only evidence

No Domain/Application/REST/Identity/Party/Assets/persistence/
Flyway, `doc/database/DATA_DICTIONARY.md`, snapshots
other than reviewed OpenAPI, architecture guards, security
rules, CI scripts, workflows, tests or test inventory
may change. The reviewed parser/security/$ref checks
in `.github/scripts/validate_openapi_snapshot.py`
and the current-base backward compatibility check
remain mandatory, unmodified. Do not invent a successful
API validation or skip a physical dictionary check.

**Stage acceptance/STOP.** Validate source artifact ZIP and
member hashes, old-contract structural equality and exact
canonical blob, manifest digest/count/provenance and
preservation of other manifest fields. Apply expected-main
guard, verify sole three-file diff/parent/tree/blobs/message,
publish directly to existing main; confirm new exact-SHA
Docs/Full CI queued/started, then STOP. New hosted
snapshot equality, actual physical dictionary and
backward-compatibility results remain pending until
that run finishes. Package C also retains the
genuine authenticated native multi-producer PostgreSQL
HTTP end-to-end/rollback/denial qualification gate.
Integrated HAPI-599 stays NOT_IMPLEMENTED/BLOCKED;
do not advance HAPI-600/601, P3, frontend,
industrial SITE or deployment.

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
