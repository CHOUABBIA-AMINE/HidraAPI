# Assets Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.assets`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns maintainable-asset records, asset-condition evidence and maintenance work-order state.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [AssetConditionRecord](../../src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java)
- [MaintainableAsset](../../src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java)
- [MaintenanceWorkOrder](../../src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java)

Domain policies:

- [AssetsBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/assets/domain/policy/AssetsBoundaryPolicy.java)

Domain services:

- [MaintenancePriorityClassifier](../../src/main/java/dz/sh/hidra/modules/assets/domain/service/MaintenancePriorityClassifier.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateMaintenanceWorkOrderUseCase](../../src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java)
- [RecordAssetConditionUseCase](../../src/main/java/dz/sh/hidra/modules/assets/application/port/in/RecordAssetConditionUseCase.java)
- [RegisterMaintainableAssetUseCase](../../src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java)
- [UpdateMaintainableAssetUseCase](../../src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java)

Current application services:

- [AssetsApplicationService](../../src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java)
- [HseWorkOrderReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/assets/application/service/HseWorkOrderReferenceQueryService.java)

Current API/controller classes:

- [AssetsController](../../src/main/java/dz/sh/hidra/modules/assets/api/rest/controller/AssetsController.java)
- [SpringAssetsController](../../src/main/java/dz/sh/hidra/modules/assets/api/rest/controller/SpringAssetsController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **25** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [AssetCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetCatalogEntryJpaEntity.java)
- [AssetCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetCatalogTranslationJpaEntity.java)
- [AssetConditionRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetConditionRecordJpaEntity.java)
- [AssetDocumentReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetDocumentReferenceJpaEntity.java)
- [AssetInstallationJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetInstallationJpaEntity.java)
- [AssetLifecycleEventJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetLifecycleEventJpaEntity.java)
- [AssetManufacturerReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetManufacturerReferenceJpaEntity.java)
- [AssetMeterReadingReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetMeterReadingReferenceJpaEntity.java)
- [AssetModelJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetModelJpaEntity.java)
- [AssetSerialIdentityJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetSerialIdentityJpaEntity.java)
- [AssetServiceContractReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetServiceContractReferenceJpaEntity.java)
- [AssetSparePartCompatibilityJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetSparePartCompatibilityJpaEntity.java)
- [AssetTechnicalAttributeDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetTechnicalAttributeDefinitionJpaEntity.java)
- [AssetTechnicalAttributeValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetTechnicalAttributeValueJpaEntity.java)
- [AssetTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetTypeJpaEntity.java)
- [AssetTypeTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetTypeTranslationJpaEntity.java)
- [AssetWarrantyJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetWarrantyJpaEntity.java)
- [MaintainableAssetJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java)
- [MaintenanceExecutionRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceExecutionRecordJpaEntity.java)
- [MaintenancePlanJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenancePlanJpaEntity.java)
- [MaintenanceStrategyJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceStrategyJpaEntity.java)
- [MaintenanceTaskTemplateJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceTaskTemplateJpaEntity.java)
- [MaintenanceWorkOrderJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java)
- [MaintenanceWorkOrderTaskJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java)
- [SparePartJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/SparePartJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaAssetConditionRecordRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaAssetConditionRecordRepositoryAdapter.java)
- [JpaMaintainableAssetRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java)
- [JpaMaintenanceWorkOrderRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java)
- [MaintenanceWorkOrderReferenceValidation](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/MaintenanceWorkOrderReferenceValidation.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [HseWorkOrderReferenceContract](../../src/main/java/dz/sh/hidra/modules/assets/application/contract/hse/HseWorkOrderReferenceContract.java)

Imported scalar contracts supplied by collaborating owners:

- [MaintenanceWorkOrderActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/MaintenanceWorkOrderActorReferenceContract.java)
- [MaintenanceRecommendationReferenceContract](../../src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/MaintenanceRecommendationReferenceContract.java)
- [AssetsOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java)
- [AssetsPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [AssetsTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java)
- [MaintenanceWorkOrderWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/MaintenanceWorkOrderWorkflowReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [AssetConditionRecordRepositoryPort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetConditionRecordRepositoryPort.java)
- [AssetsAuditReferencePort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsAuditReferencePort.java)
- [AssetsDocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsDocumentReferencePort.java)
- [AssetsIntegrityRecommendationPort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsIntegrityRecommendationPort.java)
- [AssetsPartyReferencePort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsPartyReferencePort.java)
- [AssetsTelemetryReferencePort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTelemetryReferencePort.java)
- [AssetsTopologyLookupPort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTopologyLookupPort.java)
- [AssetsWorkflowReferencePort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsWorkflowReferencePort.java)
- [MaintainableAssetRepositoryPort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java)
- [MaintenanceWorkOrderRepositoryPort](../../src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Assets owns maintainability and maintenance work orders, while Topology retains physical identity. Required local parents and fresh owner references are validated; optional evidence and valid unchanged historical snapshots are preserved.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| MaintainableAsset | [HMSR-054 reconciled rule](../domain/SEMANTIC_DECISIONS.md#assets-maintainableasset) |
| AssetConditionRecord | [HMSR-070 reconciled rule](../domain/SEMANTIC_DECISIONS.md#assets-assetconditionrecord) |
| MaintenanceWorkOrder | [HMSR-081 reconciled rule](../domain/SEMANTIC_DECISIONS.md#assets-maintenanceworkorder) |
