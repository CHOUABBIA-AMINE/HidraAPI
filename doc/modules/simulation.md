# Simulation Module

## Status

CURRENT — canonical HPR-P2-004 module document.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

Package root: `dz.sh.hidra.modules.simulation`

This document describes source-visible current state. It does not promote legacy `docs/**` material to authority and does not substitute for the canonical API/database contracts created by later P2 tasks.

## Responsibility

Owns simulation models/scenarios/runs, optimization candidates/changes and human-facing simulation recommendations.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types:

- `SimulationCandidateChange`
- `SimulationModel`
- `SimulationOptimizationCandidate`
- `SimulationRecommendation`
- `SimulationRun`
- `SimulationScenario`

Domain policies: `SimulationSafetyPolicy`

Domain services: `SimulationSafetyGuard`

Canonical semantic context: `../domain/SIMULATION_ANALYTICS_AI.md`.

## Application and API Surface

Current inbound/use-case ports:

- `CreateSimulationModelUseCase`
- `CreateSimulationScenarioUseCase`
- `PublishSimulationRecommendationUseCase`
- `QueueSimulationRunUseCase`

Current application services:

- `SimulationApplicationService`

Current API/controller classes:

- `SimulationController`
- `SpringSimulationController`

These class inventories establish implemented adapters/use-case surfaces. Exact HTTP paths, request/response schemas, authentication requirements and compatibility semantics are HPR-P2-005 scope.

## Persistence

Current JPA persistence entity count: **25**.

Persistence entities:

- `SimulationCandidateChangeJpaEntity`
- `SimulationCandidateOperatingConditionJpaEntity`
- `SimulationCandidateScoreJpaEntity`
- `SimulationCatalogEntryJpaEntity`
- `SimulationCatalogTranslationJpaEntity`
- `SimulationConstraintEvaluationJpaEntity`
- `SimulationConstraintJpaEntity`
- `SimulationEvidenceLinkJpaEntity`
- `SimulationInputDatasetJpaEntity`
- `SimulationInputSnapshotJpaEntity`
- `SimulationModelJpaEntity`
- `SimulationModelVersionJpaEntity`
- `SimulationObjectiveJpaEntity`
- `SimulationOptimizationCandidateJpaEntity`
- `SimulationRecommendationJpaEntity`
- `SimulationResultSeriesReferenceJpaEntity`
- `SimulationResultSummaryJpaEntity`
- `SimulationResultValueJpaEntity`
- `SimulationRunJpaEntity`
- `SimulationRunStepJpaEntity`
- `SimulationScenarioAssumptionJpaEntity`
- `SimulationScenarioJpaEntity`
- `SimulationSensitivityAnalysisJpaEntity`
- `SimulationSolverTraceJpaEntity`
- `SimulationValidationFindingJpaEntity`

Current persistence repository-adapter classes:

- `JpaSimulationCandidateChangeRepositoryAdapter`
- `JpaSimulationModelRepositoryAdapter`
- `JpaSimulationOptimizationCandidateRepositoryAdapter`
- `JpaSimulationRecommendationRepositoryAdapter`
- `JpaSimulationRunRepositoryAdapter`
- `JpaSimulationScenarioRepositoryAdapter`

Table/schema ownership and the generated data dictionary are HPR-P2-006 scope; class presence is not used here to invent database constraints or retention policy.

## Cross-Module Boundary

Exported contracts owned by this module:

- No exported `application.contract.<consumer>` contract was found in this module at the verification baseline.

Current outbound application ports used to reach persistence or collaborating capabilities:

- `AssetAvailabilityLookupPort`
- `AuditEventPort`
- `DocumentReferencePort`
- `IntegrityConstraintLookupPort`
- `MonitoringContextLookupPort`
- `NotificationRequestPort`
- `PlanningSnapshotLookupPort`
- `SimulationCandidateChangeRepositoryPort`
- `SimulationModelRepositoryPort`
- `SimulationOptimizationCandidateRepositoryPort`
- `SimulationRecommendationRepositoryPort`
- `SimulationResultStoragePort`
- `SimulationRunRepositoryPort`
- `SimulationScenarioRepositoryPort`
- `SimulationSolverPort`
- `TelemetryTrustedReadingSnapshotPort`
- `TopologyChangeProposalPort`
- `TopologySnapshotLookupPort`
- `WorkflowStartPort`

Cross-module collaboration must preserve the canonical architecture rule: no direct import of another module's private domain, infrastructure or non-exported application packages.

## Current-State Limits

- File/class presence documents implementation structure, not proof that every business workflow, external dependency or production integration is exercised.
- HPR-P2-004 does not resolve legacy HMR/HMSR semantic obligations; HPR-P2-007/008 remain the reconciliation/remediation authority.
- `agents`, `environment` and `otsecurity` are not current implemented module roots and are not implied by this document.
- Simulation is decision-support only at the current semantic baseline; field actuation is prohibited by the simulation safety boundary.
