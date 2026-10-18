# HPS-009 — Assets and Maintenance: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-009 — Assets and Maintenance

<a id="hapi-149"></a>
## HAPI-149 — scan assets domain models

Phase: HPS-009 — Assets and Maintenance

Legacy IDs: HMC-004

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `assets` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 3 assets model files.

### 2. Existing Implementation

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain assets, conditions, maintenance plans/orders and traceable integrity-driven work.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `assets`; primary phase owns this obligation. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 13. HMC-004 — Assets module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `assets`  
**Baseline model files:** 3  
**Scanned models:** 3  
**Declared fields/components:** 56  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Assets model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| AssetConditionRecord | record | 11 | `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java` | Extracted |
| MaintainableAsset | record | 25 | `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java` | Extracted |
| MaintenanceWorkOrder | record | 20 | `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java` | Extracted |

### Assets fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| AssetConditionRecord | 1 | id | String |
| AssetConditionRecord | 2 | maintainableAssetId | String |
| AssetConditionRecord | 3 | conditionStatus | AssetConditionStatus |
| AssetConditionRecord | 4 | conditionTypeId | String |
| AssetConditionRecord | 5 | sourceModule | String |
| AssetConditionRecord | 6 | sourceReferenceId | String |
| AssetConditionRecord | 7 | summary | String |
| AssetConditionRecord | 8 | conditionScore | BigDecimal |
| AssetConditionRecord | 9 | observedAt | Instant |
| AssetConditionRecord | 10 | observedByActorId | String |
| AssetConditionRecord | 11 | createdAt | Instant |
| MaintainableAsset | 1 | id | String |
| MaintainableAsset | 2 | assetNumber | String |
| MaintainableAsset | 3 | assetCode | String |
| MaintainableAsset | 4 | assetName | String |
| MaintainableAsset | 5 | assetTypeId | String |
| MaintainableAsset | 6 | topologyAssetTypeCode | String |
| MaintainableAsset | 7 | topologyAssetId | String |
| MaintainableAsset | 8 | topologyAssetCodeSnapshot | String |
| MaintainableAsset | 9 | topologyAssetNameSnapshot | String |
| MaintainableAsset | 10 | parentAssetId | String |
| MaintainableAsset | 11 | status | AssetLifecycleStatus |
| MaintainableAsset | 12 | criticalityId | String |
| MaintainableAsset | 13 | ownerOrganizationUnitId | String |
| MaintainableAsset | 14 | ownerOrganizationUnitNameSnapshot | String |
| MaintainableAsset | 15 | manufacturerPartyId | String |
| MaintainableAsset | 16 | manufacturerNameSnapshot | String |
| MaintainableAsset | 17 | modelId | String |
| MaintainableAsset | 18 | serialIdentityId | String |
| MaintainableAsset | 19 | registeredAt | Instant |
| MaintainableAsset | 20 | installedAt | Instant |
| MaintainableAsset | 21 | commissionedAt | Instant |
| MaintainableAsset | 22 | retiredAt | Instant |
| MaintainableAsset | 23 | createdByActorId | String |
| MaintainableAsset | 24 | createdAt | Instant |
| MaintainableAsset | 25 | updatedAt | Instant |
| MaintenanceWorkOrder | 1 | id | String |
| MaintenanceWorkOrder | 2 | workOrderNumber | String |
| MaintenanceWorkOrder | 3 | maintainableAssetId | String |
| MaintenanceWorkOrder | 4 | maintenancePlanId | String |
| MaintenanceWorkOrder | 5 | sourceRecommendationId | String |
| MaintenanceWorkOrder | 6 | workOrderTypeId | String |
| MaintenanceWorkOrder | 7 | priorityId | String |
| MaintenanceWorkOrder | 8 | status | MaintenanceWorkOrderStatus |
| MaintenanceWorkOrder | 9 | title | String |
| MaintenanceWorkOrder | 10 | description | String |
| MaintenanceWorkOrder | 11 | assignedOrganizationUnitId | String |
| MaintenanceWorkOrder | 12 | assignedActorId | String |
| MaintenanceWorkOrder | 13 | plannedStartAt | Instant |
| MaintenanceWorkOrder | 14 | plannedEndAt | Instant |
| MaintenanceWorkOrder | 15 | startedAt | Instant |
| MaintenanceWorkOrder | 16 | completedAt | Instant |
| MaintenanceWorkOrder | 17 | workflowInstanceId | String |
| MaintenanceWorkOrder | 18 | createdByActorId | String |
| MaintenanceWorkOrder | 19 | createdAt | Instant |
| MaintenanceWorkOrder | 20 | updatedAt | Instant |

### HMC-004 reconciliation

```text
baseline model files = 3
extracted models     = 3
exceptions           = 0
declared fields      = 56
zero-field models    = 0
```

The scan records only declarations under `assets/domain/model`. Assets domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, or database column metadata was mixed into this module scan.

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

- `b49641cb883c6bf95bb5b0bb1d79053913e2b97f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:132](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L132)
- [docs/roadmap/model-field-catalogue.md:522](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L522)
- [docs/roadmap/model-field-catalogue.md:601](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L601)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:132 | HMC-004 | docs(catalogue): scan assets domain models | Scan all 3 assets model files. | **Completed** — 3/3 models, 56 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-233"></a>
## HAPI-233 — remediate semantic review MaintainableAsset

Phase: HPS-009 — Assets and Maintenance

Legacy IDs: HMR-045

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `assets` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review MaintainableAsset

### 2. Existing Implementation

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Assets.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/RegisterMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/UpdateMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintainableAssetResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/RegisterMaintainableAssetCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintainableAssetSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/exception/MaintainableAssetConflictException.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintainableAssetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
- `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain assets, conditions, maintenance plans/orders and traceable integrity-driven work.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `assets`; primary phase owns this obligation. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/exception/MaintainableAssetConflictException.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java`, `src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`, `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintainableAssetJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Assets.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/RegisterMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/UpdateMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintainableAssetResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/RegisterMaintainableAssetCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintainableAssetSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/exception/MaintainableAssetConflictException.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintainableAssetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
- `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-045 — assets.MaintainableAsset

- Source review: `HMSR-054`
- Exact commit: `fix(assets): remediate semantic review MaintainableAsset`
- Status: **Completed** — mandatory typed Topology references and populated OrganizationUnit/Party references now resolve through owner-exported Assets contracts; populated parent/model/serial references fail closed and are protected by nullable same-module FKs.
- SCC: SCC-03
- Recorded upstream HMS dependencies: assets.MaintainableAsset, organization.OrganizationUnit, party.Party
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
- Owner-contract prerequisite: HMR-047A registered — live preflight proved no Integration-facing Organization owner contract exists and the authoritative Integration registration service was outside the original write scope.
- Exact write allowlist:
  - `docs/data definition/Assets.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/RegisterMaintainableAssetRequest.java`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/UpdateMaintainableAssetRequest.java`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintainableAssetResponse.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/command/RegisterMaintainableAssetCommand.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintainableAssetSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/exception/MaintainableAssetConflictException.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintainableAssetJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
  - `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MaintainableAssetSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate the mandatory typed Topology reference.** `topologyAssetTypeCode + topologyAssetId` must resolve through a Topology-owned contract before registration/update paths persist a new linkage; do not add a cross-module FK.
  2. **Protect populated `parentAssetId`.** Preserve SCC-03 and the existing direct-self guard, while failing closed when a supplied parent MaintainableAsset does not exist.
  3. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Do not introduce a cross-module database FK.
  4. **Validate populated `manufacturerPartyId` through the Party owner boundary.** Do not introduce a cross-module database FK or invent manufacturer-role eligibility.
  5. **Protect populated `modelId` and `serialIdentityId` same-module references.** Use nullable same-module FKs, application validation, or equivalent fail-closed integrity.

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
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `99f46122e239affec85ce5dd4881ec333de74ef1` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Assets.md:174](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Assets.md#L174)
- [docs/roadmap/model-semantic-remediation.md:201](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L201)
- [docs/roadmap/model-semantic-remediation.md:2492](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2492)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:201 | HMR-045 | HMSR-054 | assets | MaintainableAsset | SCC-03 | assets.MaintainableAsset, organization.OrganizationUnit, party.Party | fix(assets): remediate semantic review MaintainableAsset | Planned
~~~~~~~~

<a id="hapi-234"></a>
## HAPI-234 — register MaintainableAsset owner prerequisites

Phase: HPS-009 — Assets and Maintenance

Legacy IDs: HMR-045A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `assets` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register MaintainableAsset owner prerequisites

### 2. Existing Implementation

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain assets, conditions, maintenance plans/orders and traceable integrity-driven work.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `assets`; primary phase owns this obligation. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`, `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-045A — MaintainableAsset multi-owner contract prerequisite

- Source: HMR-045 / HMSR-054 plus live Assets/Topology/Organization/Party boundary evidence.
- Exact commit: `docs(model-remediation): register MaintainableAsset owner prerequisites`
- Status: **Completed** — the missing owner-exported contracts, Assets orchestration file, focused owner-contract tests, and exact guardrail registrations are authorized; HMR-045A itself changes documentation only.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. HMR-045 requires fail-closed validation of the mandatory typed Topology reference, optional OrganizationUnit owner, and optional manufacturer Party before a new linkage is persisted.
  2. No `topology.application.contract.assets`, `organization.application.contract.assets`, or `party.application.contract.assets` package exists on live `main`.
  3. The recorded Organization candidates are internal application ports and are not deliberate Assets-facing exports.
  4. Topology already has owner repositories/services capable of resolving PipelineSystem, Pipeline, Facility, and Equipment identities, but its existing exported contracts are consumer-specific and must not be repurposed by importing another consumer's contract package.
  5. Party already proves the owner-side existence pattern through `PartyRepositoryPort.findById(...)`, but its current exported contract is Topology-specific.
  6. Organization can resolve OrganizationUnit existence through `OrganizationUnitRepositoryPort.findById(...)`, but no Assets-facing exported contract exists.
  7. The authoritative MaintainableAsset registration/update orchestration is `AssetsApplicationService`, which is outside the original HMR-045 write allowlist.
  8. `modelId` and `serialIdentityId` point to existing same-module tables `hidra_asset_model` and `hidra_asset_serial_identity`; `parentAssetId` points to `hidra_asset_maintainable_asset`.
- Required decisions:
  1. Topology must export a narrow Assets-facing contract that validates a typed Topology identity using neutral scalar `topologyAssetTypeCode + topologyAssetId` input and returns only neutral existence/current-display evidence; no Topology domain/JPA/repository type may cross the boundary.
  2. Topology owner implementation may resolve only Topology entity types already supported by the live owner model; do not invent new asset-type vocabularies. Unsupported type codes fail closed.
  3. Organization must export a narrow Assets-facing `OrganizationUnit` existence contract implemented through `OrganizationUnitRepositoryPort.findById(...)`. HMR-045 requires existence only; do not invent lifecycle/ACTIVE eligibility.
  4. Party must export a narrow Assets-facing Party existence contract implemented through `PartyRepositoryPort.findById(...)`. Do not invent manufacturer-role eligibility.
  5. `AssetsApplicationService` must validate the mandatory Topology reference and populated OrganizationUnit/Party references before registration; update paths must revalidate whenever a path can persist a new linkage. Existing updates that do not change linkage fields must not invent unrelated owner lookups.
  6. Preserve SCC-03 for `parentAssetId`: direct self-reference remains rejected by the domain, populated parent IDs must exist, and database integrity may use a nullable same-table FK.
  7. HMR-045 migration may add nullable same-module FKs:
     - `parent_asset_id -> hidra_asset_maintainable_asset(id)`
     - `model_id -> hidra_asset_model(id)`
     - `serial_identity_id -> hidra_asset_serial_identity(id)`
     No Topology, Organization, or Party cross-module FK is authorized.
  8. Register exactly these new exported packages in both architecture guardrail registries:
     - `dz.sh.hidra.modules.topology.application.contract.assets`
     - `dz.sh.hidra.modules.organization.application.contract.assets`
     - `dz.sh.hidra.modules.party.application.contract.assets`
     Do not weaken algorithms or add wildcard/transitional exceptions.
- Newly authorized HMR-045 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- Newly authorized HMR-045 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-045 remains the current production remediation after this prerequisite is observed. Do not start HMR-046 automatically.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `c68cfab10f58ab3f3c320e7f253c616bee3c2a60` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:2533](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2533)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-263"></a>
## HAPI-263 — remediate semantic review MaintenanceWorkOrder

Phase: HPS-009 — Assets and Maintenance

Legacy IDs: HMR-069

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `assets` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review MaintenanceWorkOrder

### 2. Existing Implementation

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Assets.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/CreateMaintenanceWorkOrderRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintenanceWorkOrderResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/CreateMaintenanceWorkOrderCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintenanceWorkOrderSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/MaintenanceWorkOrderReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/MaintenanceWorkOrderActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/MaintenanceRecommendationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/MaintenanceRecommendationReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/MaintenanceWorkOrderWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/infrastructure/persistence/MaintenanceWorkOrderSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/MaintenanceWorkOrderActorReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/MaintenanceRecommendationReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/MaintenanceWorkOrderWorkflowReferenceContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `assets`; primary phase owns this obligation. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/MaintenanceWorkOrderReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Assets.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/CreateMaintenanceWorkOrderRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintenanceWorkOrderResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/CreateMaintenanceWorkOrderCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintenanceWorkOrderSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/MaintenanceWorkOrderReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/MaintenanceWorkOrderActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/MaintenanceRecommendationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/MaintenanceRecommendationReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/MaintenanceWorkOrderWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/infrastructure/persistence/MaintenanceWorkOrderSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/MaintenanceWorkOrderActorReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/MaintenanceRecommendationReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/MaintenanceWorkOrderWorkflowReferenceContractTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-069 — assets.MaintenanceWorkOrder

- Source review: `HMSR-081`
- Exact commit: `fix(assets): remediate semantic review MaintenanceWorkOrder`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: assets.MaintainableAsset, organization.OrganizationUnit, workflow.WorkflowInstance
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Assets.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/CreateMaintenanceWorkOrderRequest.java`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintenanceWorkOrderResponse.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/command/CreateMaintenanceWorkOrderCommand.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintenanceWorkOrderSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderTaskJpaRepository.java`
  - `src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql`
  - `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MaintenanceWorkOrderSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce required work-order title before persistence.**
  2. **Protect populated `maintenancePlanId` as a same-module Assets reference** through a same-module FK or explicitly justified fail-closed Assets application boundary.
  3. **Validate cross-context provenance/assignment identities through owner-controlled contracts:** populated Integrity recommendation, Organization unit, assigned actor, and creator actor references; do not introduce cross-module database FKs.
  4. **Validate populated `workflowInstanceId` through a Workflow-owned application contract** and preserve it as a scalar cross-module reference.

- Accepted Batch 16 exhaustive scope in canonical Ultimate Roadmap supersedes the original client-only scope.
- Execution: Required title, nullable local plan integrity and owner-controlled recommendation/unit/actor/Workflow context validation now guard authoritative transactional saves. Existing parent rows are pessimistically locked before historical-reference comparison; unchanged historical owner references are preserved and fresh or changed references fail closed. Forward 011 adds a validated plan FK and nonblank-title check without rewriting legacy rows. No unsupported type-family, assignment, temporal or uniqueness rules added. Changed production sources and 21 focused test signatures compiled with temporary framework/JUnit/Mockito APIs; 11 actual domain/reference behavior checks passed using controlled owner fixtures. Six real PostgreSQL cases prepared, including legacy rollback and concurrent parent deletion. Focused Maven invocation stopped before compilation/test execution at uncached offline Spring Boot 4.1.1 parent. Final-head CI pending.

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
- `src/test/java/dz/sh/hidra/modules/assets/infrastructure/persistence/MaintenanceWorkOrderSemanticPostgresIntegrationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java` — exact-head oracle suite 11 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/MaintenanceWorkOrderActorReferenceContractTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/MaintenanceRecommendationReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/MaintenanceWorkOrderWorkflowReferenceContractTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

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

- `b3c67ecce4e09758468e428d5534a0fbf3f8659a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:104](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L104)
- [doc/model-remediation/RECONCILIATION.md:2950](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L2950)
- [doc/model-remediation/RECONCILIATION.md:13716](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13716)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:5057](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L5057)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15456](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15456)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:103](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L103)
- [docs/data definition/Assets.md:192](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Assets.md#L192)
- [docs/roadmap/model-semantic-remediation.md:235](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L235)
- [docs/roadmap/model-semantic-remediation.md:3843](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3843)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:235 | HMR-069 | HMSR-081 | assets | MaintenanceWorkOrder | — | assets.MaintainableAsset, organization.OrganizationUnit, workflow.WorkflowInstance | fix(assets): remediate semantic review MaintenanceWorkOrder | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:104 | HMR-069 | HMSR-081 | assets.MaintenanceWorkOrder | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083.
doc/model-remediation/RECONCILIATION.md:13716 | HMR-069 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:103 | HMR-069 | HMSR-081 | [assets.MaintenanceWorkOrder](../domain/SEMANTIC_DECISIONS.md#assets-maintenanceworkorder) | [MaintenanceWorkOrderSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java) | [V20261008_011__hmr_069_assets_maintenance_work_order](../../src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql); [MaintenanceWorkOrderActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/MaintenanceWorkOrderActorReferenceContract.java); [MaintenanceRecommendationReferenceContract](../../src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/MaintenanceRecommendationReferenceContract.java); [AssetsOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java); [MaintenanceWorkOrderWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/MaintenanceWorkOrderWorkflowReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15456 | HMR-069 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-364"></a>
## HAPI-364 — review assets MaintainableAsset

Phase: HPS-009 — Assets and Maintenance

Legacy IDs: HMSR-054

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `assets` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review assets MaintainableAsset

### 2. Existing Implementation

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain assets, conditions, maintenance plans/orders and traceable integrity-driven work.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `assets`; primary phase owns this obligation. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 67. HMSR-054 — assets.MaintainableAsset review

**Decision:** REVISE  
**Review code:** HMSR-054  
**Dependency level:** 2  
**Bounded context:** assets  
**SCC:** SCC-03 through optional `parentAssetId` self-reference  
**Confirmed upstream subject dependencies:** optional `organization.OrganizationUnit`, optional `party.Party`, plus typed Topology ownership outside a single HMS edge  
**Confirmed direct HMS subject dependents:** 3  
**Transitive HMS subject dependents:** 3

### 67.1 Semantic role and ownership boundary

`MaintainableAsset` is the Assets-owned maintainability/lifecycle representation of a physical or logical asset whose topology identity remains owned by Topology.

The Assets DDD is explicit:

```text
Assets owns maintainability and maintenance lifecycle.
Topology owns the physical/logical topology object.
Assets references topology through a neutral reference.
```

This separation is correct and must be preserved during correction work.

### 67.2 Field semantics

The 25-field model contains stable asset identity (`id`, `assetNumber`, `assetCode`), display name, Assets-owned type/lifecycle state, typed Topology reference plus snapshots, optional parent hierarchy, criticality, Organization owner, Party manufacturer, AssetModel/AssetSerialIdentity references, lifecycle timestamps, actor provenance, and audit timestamps.

The canonical constructor already requires nonblank ID, asset number, asset code, asset type ID, topology asset type code, topology asset ID, status and `registeredAt`. It also rejects direct self-reference where `parentAssetId == id`.

### 67.3 AssetType integrity is already protected

`assetTypeId` is mandatory and resolves to the Assets-owned retained `AssetType` model.

HRA-111 installs and validates:

```text
asset_type_id -> hidra_asset_type.id
```

Therefore the mandatory same-module type identity is fail-closed at persistence.

Current evidence does not establish an additional catalog-family rule beyond the concrete AssetType target.

### 67.4 Typed Topology reference is mandatory but unvalidated

The domain requires both:

```text
topologyAssetTypeCode
topologyAssetId
```

and the Assets DDD defines them as the neutral Topology reference used to link maintainability state to a physical/logical topology object.

`AssetsApplicationService.registerMaintainableAsset()` accepts both values and persists the MaintainableAsset directly. No Topology-owned lookup/validation contract is invoked.

A cross-module database FK is intentionally inappropriate because `topologyAssetTypeCode + topologyAssetId` is polymorphic and Topology remains the owner.

Therefore the mandatory typed Topology reference can currently identify a nonexistent asset.

### 67.5 Self-parent reference integrity

`parentAssetId` is an optional self-reference to another MaintainableAsset and forms SCC-03.

The canonical constructor correctly rejects the trivial direct cycle:

```text
parentAssetId == id
```

but no nullable same-table FK or application existence lookup currently protects a populated parent ID.

HMSR-054 does not invent deep-cycle detection or tree-depth rules; current evidence supports only existence protection plus the already-implemented direct self-reference prohibition.

### 67.6 Organization owner boundary

`ownerOrganizationUnitId` is an optional unambiguous reference to `organization.OrganizationUnit`, accompanied by a name snapshot.

The registration service accepts and persists the supplied ID/snapshot without resolving the Organization-owned target.

Assets must not create a database FK to Organization. When populated, owner identity should be validated through an Organization-owned application/lookup contract or equivalent fail-closed boundary.

Current evidence does not require every MaintainableAsset to have an Organization owner, so HMSR-054 does not make the field mandatory.

### 67.7 Party manufacturer boundary

`manufacturerPartyId` is an optional unambiguous reference to `party.Party`, accompanied by a manufacturer-name snapshot.

The registration service persists the supplied value without Party-owner validation.

No cross-module database FK should be added. When populated, the Party identity must resolve through the Party owner boundary.

HMSR-054 does not invent manufacturer-role eligibility or require every maintainable asset to identify a manufacturer.

### 67.8 AssetModel and AssetSerialIdentity references

`modelId` and `serialIdentityId` resolve to retained Assets-owned models `AssetModel` and `AssetSerialIdentity`, respectively.

Both are optional, but current persistence has no demonstrated nullable FK and the registration service performs no lookup.

Therefore populated values can dangle even though their targets are within the Assets bounded context.

These references should be protected by nullable same-module FKs, application validation, or equivalent fail-closed integrity.

### 67.9 Criticality semantics remain conservative

`criticalityId` is optional. Current Assets DDD evidence does not define one exact catalog namespace/family for this field.

HMSR-054 therefore retains it as a value/catalog dependency and does not invent `ASSET_CRITICALITY` family enforcement without stronger repository evidence.

### 67.10 Lifecycle timestamps deliberately not over-specified

The model stores `registeredAt`, optional `installedAt`, optional `commissionedAt`, and optional `retiredAt`.

Current Assets DDD and invariant-classification evidence reviewed for HMSR-054 do not define ordering relationships among those four timestamps.

HMSR-054 therefore does not invent `registered <= installed <= commissioned <= retired`, status/timestamp coupling, or mandatory retirement timestamps.

### 67.11 Asset identity and display rules deliberately not invented

`assetNumber` and `assetCode` are mandatory, but the base schema does not establish uniqueness and the current Assets DDD does not explicitly state global or scoped uniqueness.

`assetName` is required by persistence but classified consistently with repository policy as generic human-readable text rather than an intrinsic constructor invariant absent stronger DDD evidence.

No new uniqueness or name-requiredness obligation is recorded.

### 67.12 Required revisions

Five evidence-backed production correction obligations remain:

1. **Validate the mandatory typed Topology reference.** `topologyAssetTypeCode + topologyAssetId` must resolve through a Topology-owned contract before registration/update paths persist a new linkage; do not add a cross-module FK.

2. **Protect populated `parentAssetId`.** Preserve SCC-03 and the existing direct-self guard, while failing closed when a supplied parent MaintainableAsset does not exist.

3. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Do not introduce a cross-module database FK.

4. **Validate populated `manufacturerPartyId` through the Party owner boundary.** Do not introduce a cross-module database FK or invent manufacturer-role eligibility.

5. **Protect populated `modelId` and `serialIdentityId` same-module references.** Use nullable same-module FKs, application validation, or equivalent fail-closed integrity.

HMSR-054 does not modify production Java, JPA, Flyway, application/API contracts, tests, asset data, topology data, Organization data, Party data, or provisioned data.

### 67.13 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, MaintainableAsset is the maintenance/lifecycle view used to plan and execute work on equipment and other maintainable physical assets while Topology remains the authoritative physical network owner.

Broken links to Topology, organizational ownership, manufacturer identity, model/serial master data, or parent assets weaken maintenance traceability and can propagate incorrect work-order context.

The correct architecture is therefore strong reference validation without collapsing bounded contexts or introducing cross-module database coupling.

### 67.14 Review conclusion

**REVISE.** `MaintainableAsset` has a coherent Assets/Topology ownership split, mandatory AssetType FK protection, correct direct self-reference guard, lifecycle status semantics, and appropriately neutral cross-module snapshots/references.

The target baseline cannot mark it APPROVED while mandatory Topology linkage and several optional but unambiguous owner/same-module references can be persisted without fail-closed target validation.

HMS reconciliation must retain these reference-integrity obligations until explicitly authorized Assets production corrections resolve them or stronger repository evidence changes the reference semantics.

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

- `b3a79f1e827fdaaa584614ae426d863e84a4d02f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:106](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L106)
- [docs/roadmap/model-semantic-review.md:249](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L249)
- [docs/roadmap/model-semantic-review.md:11294](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L11294)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:249 | HMSR-054 | 2 | assets | MaintainableAsset | SCC-03 | assets.MaintainableAsset, organization.OrganizationUnit, party.Party | 3 | 3 | 4 | REVISE | Completed | docs(model-review): review assets MaintainableAsset
doc/domain/SEMANTIC_DECISIONS.md:106 | HMSR-054 | [assets.MaintainableAsset](#assets-maintainableasset) | REVISE | HMR-045
~~~~~~~~

<a id="hapi-380"></a>
## HAPI-380 — review assets AssetConditionRecord

Phase: HPS-009 — Assets and Maintenance

Legacy IDs: HMSR-070

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `assets` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review assets AssetConditionRecord

### 2. Existing Implementation

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetConditionRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain assets, conditions, maintenance plans/orders and traceable integrity-driven work.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `assets`; primary phase owns this obligation. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetConditionRecordRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetConditionRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 83. HMSR-070 — assets.AssetConditionRecord review

**Decision:** APPROVED  
**Review code:** HMSR-070  
**Dependency level:** 3  
**Bounded context:** assets  
**Confirmed upstream subject dependencies:** `assets.MaintainableAsset`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 83.1 Semantic role

`AssetConditionRecord` is the Assets-owned operational condition observation for one MaintainableAsset. It records the asset, condition status, optional condition classification, optional source provenance, summary/score, observation time and optional observer actor.

Assets owns the condition record. It does not take ownership of external source facts or Identity actors.

### 83.2 Maintainable-asset integrity

`maintainableAssetId` is mandatory and unambiguously references `assets.MaintainableAsset`.

HRA-111 already protects:

```text
hidra_asset_condition_record.maintainable_asset_id
    -> hidra_asset_maintainable_asset.id
```

with same-module `ON DELETE RESTRICT` semantics.

The active `recordAssetCondition()` path persists the supplied asset ID and relies on that same-module integrity guard. No cross-module lookup or alternative ownership model is required.

### 83.3 Condition status

`AssetConditionStatus` defines:

```text
NORMAL
WATCH
DEGRADED
CRITICAL
UNKNOWN
```

These values describe the operational condition state of the record itself.

Current Assets DDD evidence does not classify this field as an enterprise/business taxonomy that must be catalog-backed, nor does it define a richer mutable lifecycle for a historical condition observation.

HMSR-070 therefore preserves the enum and does not invent a catalog migration.

### 83.4 Optional condition type

`conditionTypeId` is optional.

The current Assets DDD identifies AssetCatalogEntry as an Assets-owned controlled-vocabulary model, but it does not define an exact catalog name/family for AssetConditionRecord.conditionTypeId, and repository search found no stronger mapping or same-module FK.

That absence is insufficient by itself to declare the model semantically defective. The field may remain optional/unresolved until stronger Assets business evidence defines a controlled vocabulary.

HMSR-070 therefore does not manufacture an `ASSET_CONDITION_TYPE` family or require a database FK to a guessed target.

### 83.5 Source provenance

`sourceModule` and `sourceReferenceId` capture optional provenance for the observation.

Assets explicitly does not own Integrity findings, HSE cases, Telemetry readings or other foreign source records. Therefore these fields are appropriately neutral provenance identifiers rather than relational ownership links.

No cross-module database FK should be introduced.

Current DDD evidence also does not require both provenance fields to be present on every condition record, so HMSR-070 does not invent pair-presence rules.

### 83.6 Condition score

`conditionScore` is optional decimal metadata.

Current Assets DDD does not define a numerical scale, minimum/maximum range, normalization method, or relationship between the numeric score and `AssetConditionStatus`.

HMSR-070 therefore does not invent score bounds or status/score coupling.

### 83.7 Observation time and actor

`observedAt` is mandatory and enforced by the domain model. When omitted by the API/application command, the service uses the current time.

`observedByActorId` is optional and remains an external Identity actor reference.

Current Assets evidence does not require every condition observation to have a human actor because condition records may be generated from system/module sources. No cross-module Identity FK or mandatory-actor rule is appropriate.

The DDD also does not define an `observedAt <= createdAt` invariant, so HMSR-070 does not invent one.

### 83.8 Application creation semantics

`AssetsApplicationService.recordAssetCondition()`:

```text
creates a new Assets ID
defaults missing conditionStatus to UNKNOWN
copies optional condition type/provenance/summary/score
defaults missing observedAt to current time
persists through AssetConditionRecordRepositoryPort
```

Those semantics are consistent with the present Assets domain model.

The same-module FK prevents recording a condition for an unknown MaintainableAsset.

### 83.9 Review conclusion

**APPROVED.** `AssetConditionRecord` is a coherent 11-field Assets-owned historical condition observation. Its mandatory MaintainableAsset relationship is correctly protected by same-module FK; condition status is a defensible record-state enum; external source and actor identities remain correctly neutral; and current Assets evidence does not support inventing catalog-family, numeric-range, timestamp-ordering, or mandatory-provenance rules.

No production correction is required by HMSR-070.

HMSR-070 does not modify production Java, JPA, Flyway, API/application contracts, tests, Assets data, Identity data, foreign source-module data, or provisioned data.

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

- `1da09d617d8babeabd67a086c95eed9fd46c6327` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:122](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L122)
- [docs/roadmap/model-semantic-review.md:265](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L265)
- [docs/roadmap/model-semantic-review.md:13584](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L13584)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:265 | HMSR-070 | 3 | assets | AssetConditionRecord | — | assets.MaintainableAsset | 0 | 0 | 0 | APPROVED | Completed | docs(model-review): review assets AssetConditionRecord
doc/domain/SEMANTIC_DECISIONS.md:122 | HMSR-070 | [assets.AssetConditionRecord](#assets-assetconditionrecord) | APPROVED | —
~~~~~~~~

<a id="hapi-391"></a>
## HAPI-391 — review assets MaintenanceWorkOrder

Phase: HPS-009 — Assets and Maintenance

Legacy IDs: HMSR-081

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `assets` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review assets MaintenanceWorkOrder

### 2. Existing Implementation

Asset registration/update, condition recording and work-order creation have domain, persistence and focused verification. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Maintain assets, conditions, maintenance plans/orders and traceable integrity-driven work.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `assets`; primary phase owns this obligation. Assets owns maintenance; Topology owns physical incidence; Integrity owns engineering assessment. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`, `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 94. HMSR-081 — assets.MaintenanceWorkOrder review

**Decision:** REVISE  
**Review code:** HMSR-081  
**Dependency level:** 4  
**Bounded context:** assets  
**Confirmed upstream subject dependencies:** mandatory `assets.MaintainableAsset`, optional `organization.OrganizationUnit`, optional `workflow.WorkflowInstance`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 1

### 94.1 Semantic role

`MaintenanceWorkOrder` is the Assets-owned maintenance-execution instruction for a maintainable asset. It owns work-order identity/number, maintenance classification, lifecycle state, assignment, schedule/execution timestamps, optional maintenance-plan linkage, optional Integrity recommendation provenance, optional Workflow reference, creator identity, and audit timestamps.

Assets owns maintenance execution. Integrity may recommend maintenance but does not own the work order. Organization owns organization units. Identity owns actors. Workflow owns process state.

### 94.2 Maintainable-asset integrity

`maintainableAssetId` is mandatory and same-module.

HRA-111 already adds a fail-closed Assets-local foreign key:

```text
hidra_asset_maintenance_work_order.maintainable_asset_id
  -> hidra_asset_maintainable_asset.id
```

No further basic existence correction is required for the mandatory maintained asset.

### 94.3 Work-order type

`workOrderTypeId` is mandatory and HRA-111 already protects generic same-module existence through:

```text
hidra_asset_maintenance_work_order.work_order_type_id
  -> hidra_asset_catalog_entry.id
```

The current Assets DDD does not define an exact catalog-family name for this field. HMSR-081 therefore does not invent one.

The generic catalog FK is preserved. HMS-006 may add a family-level eligibility obligation only if a live Assets roadmap/DDD/catalog definition explicitly establishes the governing family.

### 94.4 Required title

The live JPA/Flyway schema persists `title` as `NOT NULL`.

The domain constructor does not reject null/blank title and normalizes blank values to null. `AssetsApplicationService.createMaintenanceWorkOrder()` copies `command.title()` directly.

The authoritative creation boundary must fail fast for the required work-order title rather than relying on persistence failure.

This obligation follows the live target persistence contract; HMSR-081 does not promote optional `description` into a domain invariant.

### 94.5 Optional maintenance-plan reference

`maintenancePlanId` is an optional same-module reference to Assets-owned maintenance planning data. The base schema creates an index, but HRA-111 does not add a foreign key for this optional edge and the reviewed creation service performs no lookup.

When populated, `maintenancePlanId` should fail closed to an existing Assets-owned maintenance plan. A same-module FK is architecturally appropriate, or an explicitly justified Assets application check may supplement it.

HMSR-081 does not invent additional rules such as requiring the plan to belong to the same maintainable asset because the current Assets DDD evidence reviewed here does not explicitly establish that correlation.

### 94.6 Integrity recommendation provenance

The Assets DDD explicitly establishes the boundary:

```text
IntegrityRecommendation
  -> application port/event
      -> Asset Management creates MaintenanceWorkOrder
```

`sourceRecommendationId` is therefore provenance to an Integrity-owned recommendation, not Assets-owned relational data.

When populated by an Integrity-driven creation path, the reference should be validated/resolved through an owner-controlled Integrity application/event contract. No cross-module Integrity database FK should be introduced.

### 94.7 Assignment and creator boundaries

`assignedOrganizationUnitId` is optional and Organization-owned. `assignedActorId` and `createdByActorId` are Identity-owned actor references.

The current create service copies all three values directly from the command, and repository evidence reviewed for this use case does not show owner-controlled Organization/Identity validation.

The authoritative creation/assignment boundary should validate populated organization and actor references through their owning contracts while keeping scalar IDs. No cross-module Organization or Identity database FKs should be introduced.

The current Assets evidence does not state that at least one assignee is mandatory, so HMSR-081 does not invent an assignment-target invariant.

### 94.8 Workflow boundary

`workflowInstanceId` is optional and Workflow-owned.

The live model correctly stores only the scalar Workflow reference; repository evidence does not show a cross-module DB FK, which is appropriate.

When populated, the authoritative Assets boundary should validate the referenced Workflow instance through a Workflow-owned application contract and ensure it targets the intended maintenance-work-order process/context. No cross-module Workflow FK should be introduced.

### 94.9 Lifecycle enum and timestamps

`MaintenanceWorkOrderStatus` contains:

```text
DRAFT
PLANNED
APPROVED
SCHEDULED
IN_PROGRESS
COMPLETED
VERIFIED
CLOSED
CANCELLED
REJECTED
```

These values represent the Assets-owned maintenance work-order lifecycle and are appropriate as a technical/domain enum.

The current Assets DDD and repository-wide invariant classification reviewed for this task do not establish explicit ordering rules among `plannedStartAt`, `plannedEndAt`, `startedAt`, and `completedAt` for this model. HMSR-081 therefore does not invent timestamp-order invariants.

Likewise, current evidence does not explicitly establish global uniqueness for `workOrderNumber`; no such database obligation is invented by this review.

### 94.10 Priority and unresolved taxonomy semantics

`priorityId` is optional. The base schema indexes it, but the reviewed Assets DDD does not define a precise catalog family or owner semantics for this field.

HMSR-081 therefore preserves it as unresolved Assets taxonomy/reference evidence and does not invent a catalog-family rule. HMS-006 may reconcile it if stronger module evidence becomes available.

### 94.11 Required revisions

Four evidence-backed obligations remain:

1. **Enforce required work-order title before persistence.**

2. **Protect populated `maintenancePlanId` as a same-module Assets reference** through a same-module FK or explicitly justified fail-closed Assets application boundary.

3. **Validate cross-context provenance/assignment identities through owner-controlled contracts:** populated Integrity recommendation, Organization unit, assigned actor, and creator actor references; do not introduce cross-module database FKs.

4. **Validate populated `workflowInstanceId` through a Workflow-owned application contract** and preserve it as a scalar cross-module reference.

HMSR-081 does not modify production Java, JPA, Flyway, API/application contracts, tests, Assets/Integrity/Organization/Identity/Workflow data, or provisioned data.

### 94.12 Review conclusion

**REVISE.** `MaintenanceWorkOrder` is a coherent Assets-owned maintenance execution model with correct maintainable-asset ownership, generic same-module work-order-type integrity, appropriate lifecycle enum semantics, and clean cross-context scalar references.

The target baseline cannot mark it APPROVED while required title validation is deferred to persistence, populated same-module maintenance-plan references are not fail-closed, and cross-context recommendation/assignment/creator/workflow references are accepted without owner-controlled validation.

HMS reconciliation must retain these obligations until explicitly authorized Assets production corrections resolve them or stronger repository evidence changes the governing Assets DDD.

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

- `68981488f1b8d0a0ecfcfc1dd6179371b5a35b1c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:133](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L133)
- [docs/roadmap/model-semantic-review.md:276](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L276)
- [docs/roadmap/model-semantic-review.md:15158](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L15158)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:276 | HMSR-081 | 4 | assets | MaintenanceWorkOrder | — | assets.MaintainableAsset, organization.OrganizationUnit, workflow.WorkflowInstance | 1 | 1 | 3 | REVISE | Completed | docs(model-review): review assets MaintenanceWorkOrder
doc/domain/SEMANTIC_DECISIONS.md:133 | HMSR-081 | [assets.MaintenanceWorkOrder](#assets-maintenanceworkorder) | REVISE | HMR-069
~~~~~~~~

<!-- task-register:payload:end -->
