# Simulation Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.simulation`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns simulation models/scenarios/runs, optimization candidates/changes and human-facing simulation recommendations.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [SimulationCandidateChange](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java)
- [SimulationModel](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java)
- [SimulationOptimizationCandidate](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java)
- [SimulationRecommendation](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java)
- [SimulationRun](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java)
- [SimulationScenario](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java)

Domain policies:

- [SimulationSafetyPolicy](../../src/main/java/dz/sh/hidra/modules/simulation/domain/policy/SimulationSafetyPolicy.java)

Domain services:

- [SimulationSafetyGuard](../../src/main/java/dz/sh/hidra/modules/simulation/domain/service/SimulationSafetyGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateSimulationModelUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java)
- [CreateSimulationScenarioUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java)
- [PublishSimulationRecommendationUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java)
- [QueueSimulationRunUseCase](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java)

Current application services:

- [SimulationApplicationService](../../src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java)

Current API/controller classes:

- [SimulationController](../../src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SimulationController.java)
- [SpringSimulationController](../../src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **25** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [SimulationCandidateChangeJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java)
- [SimulationCandidateOperatingConditionJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateOperatingConditionJpaEntity.java)
- [SimulationCandidateScoreJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateScoreJpaEntity.java)
- [SimulationCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCatalogEntryJpaEntity.java)
- [SimulationCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCatalogTranslationJpaEntity.java)
- [SimulationConstraintEvaluationJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationConstraintEvaluationJpaEntity.java)
- [SimulationConstraintJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationConstraintJpaEntity.java)
- [SimulationEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationEvidenceLinkJpaEntity.java)
- [SimulationInputDatasetJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationInputDatasetJpaEntity.java)
- [SimulationInputSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationInputSnapshotJpaEntity.java)
- [SimulationModelJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java)
- [SimulationModelVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java)
- [SimulationObjectiveJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationObjectiveJpaEntity.java)
- [SimulationOptimizationCandidateJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationOptimizationCandidateJpaEntity.java)
- [SimulationRecommendationJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java)
- [SimulationResultSeriesReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationResultSeriesReferenceJpaEntity.java)
- [SimulationResultSummaryJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationResultSummaryJpaEntity.java)
- [SimulationResultValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationResultValueJpaEntity.java)
- [SimulationRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java)
- [SimulationRunStepJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java)
- [SimulationScenarioAssumptionJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java)
- [SimulationScenarioJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java)
- [SimulationSensitivityAnalysisJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationSensitivityAnalysisJpaEntity.java)
- [SimulationSolverTraceJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationSolverTraceJpaEntity.java)
- [SimulationValidationFindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationValidationFindingJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaSimulationCandidateChangeRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java)
- [JpaSimulationModelRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java)
- [JpaSimulationOptimizationCandidateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationOptimizationCandidateRepositoryAdapter.java)
- [JpaSimulationRecommendationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java)
- [JpaSimulationRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java)
- [JpaSimulationScenarioRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [SimulationRecommendationAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [SimulationTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java)
- [SimulationTopologyTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [AssetAvailabilityLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/AssetAvailabilityLookupPort.java)
- [AuditEventPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/AuditEventPort.java)
- [DocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/DocumentReferencePort.java)
- [IntegrityConstraintLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/IntegrityConstraintLookupPort.java)
- [MonitoringContextLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java)
- [NotificationRequestPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/NotificationRequestPort.java)
- [PlanningSnapshotLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java)
- [SimulationCandidateChangeRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java)
- [SimulationModelRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java)
- [SimulationOptimizationCandidateRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationOptimizationCandidateRepositoryPort.java)
- [SimulationRecommendationRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java)
- [SimulationResultStoragePort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationResultStoragePort.java)
- [SimulationRunRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java)
- [SimulationScenarioRepositoryPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java)
- [SimulationSolverPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationSolverPort.java)
- [TelemetryTrustedReadingSnapshotPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TelemetryTrustedReadingSnapshotPort.java)
- [TopologyChangeProposalPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java)
- [TopologySnapshotLookupPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java)
- [WorkflowStartPort](../../src/main/java/dz/sh/hidra/modules/simulation/application/port/out/WorkflowStartPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Simulation owns model/scenario/run and advisory candidate/recommendation evidence. Completed runs cannot be rewritten; recommendation publication is audited. Source-visible solver availability metadata does not prove solver execution or field control.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| SimulationModel | [HMSR-009 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationmodel) |
| SimulationScenario | [HMSR-038 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationscenario) |
| SimulationRun | [HMSR-055 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationrun) |
| SimulationOptimizationCandidate | [HMSR-066 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationoptimizationcandidate) |
| SimulationCandidateChange | [HMSR-092 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationcandidatechange) |
| SimulationRecommendation | [HMSR-093 reconciled rule](../domain/SEMANTIC_DECISIONS.md#simulation-simulationrecommendation) |
