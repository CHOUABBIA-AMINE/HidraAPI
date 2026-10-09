# Integrity Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.integrity`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns integrity programs, integrity assessments, pipeline defects and integrity cases.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [IntegrityAssessment](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java)
- [IntegrityCase](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java)
- [IntegrityProgram](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java)
- [PipelineDefect](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java)

Domain policies:

- [IntegrityBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/integrity/domain/policy/IntegrityBoundaryPolicy.java)

Domain services:

- [RemainingLifeClassifier](../../src/main/java/dz/sh/hidra/modules/integrity/domain/service/RemainingLifeClassifier.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateIntegrityAssessmentUseCase](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java)
- [CreateIntegrityProgramUseCase](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java)
- [OpenIntegrityCaseUseCase](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java)

Current application services:

- [IntegrityApplicationService](../../src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java)

Current API/controller classes:

- [IntegrityController](../../src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/IntegrityController.java)
- [SpringIntegrityController](../../src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/SpringIntegrityController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **22** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [CathodicProtectionMeasurementJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/CathodicProtectionMeasurementJpaEntity.java)
- [CathodicProtectionSurveyJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/CathodicProtectionSurveyJpaEntity.java)
- [CoatingConditionObservationJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/CoatingConditionObservationJpaEntity.java)
- [CorrosionFeatureJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/CorrosionFeatureJpaEntity.java)
- [DefectAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/DefectAssessmentJpaEntity.java)
- [DefectMeasurementJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/DefectMeasurementJpaEntity.java)
- [InspectionCampaignJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/InspectionCampaignJpaEntity.java)
- [InspectionFindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/InspectionFindingJpaEntity.java)
- [InspectionRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/InspectionRunJpaEntity.java)
- [IntegrityAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java)
- [IntegrityAssessmentScopeJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentScopeJpaEntity.java)
- [IntegrityCaseJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java)
- [IntegrityCaseStatusHistoryJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseStatusHistoryJpaEntity.java)
- [IntegrityCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCatalogEntryJpaEntity.java)
- [IntegrityCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCatalogTranslationJpaEntity.java)
- [IntegrityEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityEvidenceLinkJpaEntity.java)
- [IntegrityProgramJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java)
- [IntegrityRecommendationJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityRecommendationJpaEntity.java)
- [IntegrityThreatJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityThreatJpaEntity.java)
- [PipelineDefectJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java)
- [RemainingLifeEstimateJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/RemainingLifeEstimateJpaEntity.java)
- [WallThicknessMeasurementJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/WallThicknessMeasurementJpaEntity.java)

Persistence repository adapters and reference validators:

- [IntegrityAssessmentReferenceValidation](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/IntegrityAssessmentReferenceValidation.java)
- [IntegrityCaseReferenceValidation](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/IntegrityCaseReferenceValidation.java)
- [JpaIntegrityAssessmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityAssessmentRepositoryAdapter.java)
- [JpaIntegrityCaseRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityCaseRepositoryAdapter.java)
- [JpaIntegrityProgramRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityProgramRepositoryAdapter.java)
- [JpaPipelineDefectRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaPipelineDefectRepositoryAdapter.java)
- [MaintenanceRecommendationReferenceQueryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/MaintenanceRecommendationReferenceQueryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [MaintenanceRecommendationReferenceContract](../../src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/MaintenanceRecommendationReferenceContract.java)

Imported scalar contracts supplied by collaborating owners:

- [IntegrityAssessmentActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityAssessmentActorReferenceContract.java)
- [IntegrityCaseActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityCaseActorReferenceContract.java)
- [IntegrityOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/integrity/IntegrityOrganizationUnitReferenceContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [IntegrityCaseTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java)
- [IntegrityAssessmentWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityAssessmentWorkflowReferenceContract.java)
- [IntegrityCaseWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityCaseWorkflowReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [IntegrityAssessmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssessmentRepositoryPort.java)
- [IntegrityAssetsRecommendationPort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java)
- [IntegrityAuditReferencePort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAuditReferencePort.java)
- [IntegrityCaseRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityCaseRepositoryPort.java)
- [IntegrityDocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityDocumentReferencePort.java)
- [IntegrityHseReferencePort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityHseReferencePort.java)
- [IntegrityIncidentReferencePort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityIncidentReferencePort.java)
- [IntegrityProgramRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java)
- [IntegrityTopologyLookupPort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityTopologyLookupPort.java)
- [IntegrityWorkflowReferencePort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityWorkflowReferencePort.java)
- [PipelineDefectRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Integrity owns programs, assessments, defects and cases; Assets owns maintenance execution and Topology physical identity. Fresh approved taxonomy and owner evidence are validated while optional defect and unchanged historical references remain legal.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| PipelineDefect | [HMSR-021 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integrity-pipelinedefect) |
| IntegrityProgram | [HMSR-059 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integrity-integrityprogram) |
| IntegrityAssessment | [HMSR-085 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integrity-integrityassessment) |
| IntegrityCase | [HMSR-115 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integrity-integritycase) |
