# Risk Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.risk`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns risk registers, risk assessments, risk-matrix cells and risk evidence links.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [RiskAssessment](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java)
- [RiskEvidenceLink](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java)
- [RiskMatrixCell](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java)
- [RiskRegister](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java)

Domain policies:

- [RiskBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/risk/domain/policy/RiskBoundaryPolicy.java)

Domain services:

- [RiskScoreCalculator](../../src/main/java/dz/sh/hidra/modules/risk/domain/service/RiskScoreCalculator.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [AddRiskEvidenceUseCase](../../src/main/java/dz/sh/hidra/modules/risk/application/port/in/AddRiskEvidenceUseCase.java)
- [CreateRiskAssessmentUseCase](../../src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java)
- [CreateRiskRegisterUseCase](../../src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java)
- [RiskAssessmentGovernanceUseCase](../../src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java)

Current application services:

- [RiskApplicationService](../../src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java)
- [RiskAssessmentGovernanceService](../../src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java)
- [RiskEvidenceRegistry](../../src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java)

Current API/controller classes:

- [RiskAssessmentGovernanceController](../../src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java)
- [RiskController](../../src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskController.java)
- [SpringRiskController](../../src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **25** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ResidualRiskAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/ResidualRiskAssessmentJpaEntity.java)
- [RiskAcceptanceJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAcceptanceJpaEntity.java)
- [RiskAggregationSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAggregationSnapshotJpaEntity.java)
- [RiskAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java)
- [RiskAssessmentScopeJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScopeJpaEntity.java)
- [RiskAssessmentScoringJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScoringJpaEntity.java)
- [RiskCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskCatalogEntryJpaEntity.java)
- [RiskCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskCatalogTranslationJpaEntity.java)
- [RiskConsequenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskConsequenceJpaEntity.java)
- [RiskControlJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskControlJpaEntity.java)
- [RiskEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java)
- [RiskExposureJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskExposureJpaEntity.java)
- [RiskLikelihoodJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskLikelihoodJpaEntity.java)
- [RiskMatrixCellJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java)
- [RiskMatrixJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixJpaEntity.java)
- [RiskMitigationMeasureJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMitigationMeasureJpaEntity.java)
- [RiskRatingJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRatingJpaEntity.java)
- [RiskRegisterJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java)
- [RiskReviewJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskReviewJpaEntity.java)
- [RiskScenarioJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskScenarioJpaEntity.java)
- [RiskScoreJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskScoreJpaEntity.java)
- [RiskSourceJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskSourceJpaEntity.java)
- [RiskThreatJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskThreatJpaEntity.java)
- [RiskTreatmentActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskTreatmentActionJpaEntity.java)
- [RiskTreatmentPlanJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskTreatmentPlanJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaRiskAssessmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java)
- [JpaRiskEvidenceLinkRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java)
- [JpaRiskMatrixCellRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java)
- [JpaRiskRegisterRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)

Imported scalar contracts supplied by collaborating owners:

- [RiskAssessmentAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskAssessmentAuditContract.java)
- [RiskRegisterAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java)
- [RiskActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java)
- [RiskOrganizationReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java)
- [RiskTopologyScopeReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java)
- [RiskAssessmentApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/RiskAssessmentApprovalContract.java)
- [WorkflowOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java)

Outbound application ports (persistence and collaborating capabilities):

- [RiskAlarmEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAlarmEvidenceLookupPort.java)
- [RiskAssessmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java)
- [RiskAssetEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssetEvidenceLookupPort.java)
- [RiskAuditEventPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAuditEventPort.java)
- [RiskDocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskDocumentReferencePort.java)
- [RiskEvidenceLinkRepositoryPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java)
- [RiskEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLookupPort.java)
- [RiskHseEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskHseEvidenceLookupPort.java)
- [RiskIncidentEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskIncidentEvidenceLookupPort.java)
- [RiskIntegrityEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskIntegrityEvidenceLookupPort.java)
- [RiskMatrixCellRepositoryPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java)
- [RiskMonitoringEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java)
- [RiskNotificationPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskNotificationPort.java)
- [RiskRegisterRepositoryPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java)
- [RiskSimulationEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java)
- [RiskTelemetryEvidenceLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskTelemetryEvidenceLookupPort.java)
- [RiskTopologyLookupPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskTopologyLookupPort.java)
- [RiskWorkflowPort](../../src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskWorkflowPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Risk owns registers, scoring/assessment governance and evidence links. Structured owner scopes are required even for drafts, scoring uses the selected cell, and actual Workflow/Audit evidence governs approval. Evidence provider identity must be unambiguous.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| RiskMatrixCell | [HMSR-032 reconciled rule](../domain/SEMANTIC_DECISIONS.md#risk-riskmatrixcell) |
| RiskRegister | [HMSR-058 reconciled rule](../domain/SEMANTIC_DECISIONS.md#risk-riskregister) |
| RiskAssessment | [HMSR-069 reconciled rule](../domain/SEMANTIC_DECISIONS.md#risk-riskassessment) |
| RiskEvidenceLink | [HMSR-091 reconciled rule](../domain/SEMANTIC_DECISIONS.md#risk-riskevidencelink) |
