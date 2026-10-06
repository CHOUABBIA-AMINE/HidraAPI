# Planning Module

## Status

CURRENT — canonical HPR-P2-004 module document.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

Package root: `dz.sh.hidra.modules.planning`

This document describes source-visible current state. It does not promote legacy `docs/**` material to authority and does not substitute for the canonical API/database contracts created by later P2 tasks.

## Responsibility

Owns planning periods, operational plans, plan revisions, plan targets and nominations.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types:

- `Nomination`
- `OperationalPlan`
- `PlanRevision`
- `PlanTarget`
- `PlanningPeriod`

Domain policies: `PlanningBoundaryPolicy`

Domain services: `PlanningPeriodValidator`

Canonical semantic context: `../domain/DOMAIN_OWNERSHIP.md`.

## Application and API Surface

Current inbound/use-case ports:

- `CreateOperationalPlanUseCase`
- `CreatePlanningPeriodUseCase`
- `PlanningApprovalUseCase`
- `PlanningQueryUseCase`
- `UpdatePlanRevisionUseCase`

Current application services:

- `OperationalPlanApplicationService`
- `PlanRevisionUpdateApplicationService`
- `PlanningApprovalApplicationService`
- `PlanningPeriodApplicationService`
- `PlanningQueryApplicationService`

Current API/controller classes:

- `PlanRevisionCommandController`
- `PlanningApprovalController`
- `PlanningController`
- `PlanningQueryController`
- `SpringPlanningController`

These class inventories establish implemented adapters/use-case surfaces. Exact HTTP paths, request/response schemas, authentication requirements and compatibility semantics are HPR-P2-005 scope.

## Persistence

Current JPA persistence entity count: **16**.

Persistence entities:

- `ExpectedFlowStateJpaEntity`
- `ForecastPointJpaEntity`
- `ForecastSeriesJpaEntity`
- `NominationJpaEntity`
- `NominationScheduleLineJpaEntity`
- `OperationalPlanJpaEntity`
- `PlanActualReviewSnapshotJpaEntity`
- `PlanApprovalReferenceJpaEntity`
- `PlanConstraintJpaEntity`
- `PlanRevisionJpaEntity`
- `PlanScenarioJpaEntity`
- `PlanTargetJpaEntity`
- `PlannedOperationWindowJpaEntity`
- `PlanningCatalogEntryJpaEntity`
- `PlanningCatalogTranslationJpaEntity`
- `PlanningPeriodJpaEntity`

Current persistence repository-adapter classes:

- `JpaNominationRepositoryAdapter`
- `JpaOperationalPlanRepositoryAdapter`
- `JpaPlanRevisionRepositoryAdapter`
- `JpaPlanTargetRepositoryAdapter`
- `JpaPlanningPeriodRepositoryAdapter`
- `JpaPlanningQueryAdapter`

Table/schema ownership and the generated data dictionary are HPR-P2-006 scope; class presence is not used here to invent database constraints or retention policy.

## Cross-Module Boundary

Exported contracts owned by this module:

- No exported `application.contract.<consumer>` contract was found in this module at the verification baseline.

Current outbound application ports used to reach persistence or collaborating capabilities:

- `NominationRepositoryPort`
- `OperationalPlanRepositoryPort`
- `PlanRevisionRepositoryPort`
- `PlanTargetRepositoryPort`
- `PlanningPeriodRepositoryPort`
- `PlanningQueryPort`

Cross-module collaboration must preserve the canonical architecture rule: no direct import of another module's private domain, infrastructure or non-exported application packages.

## Current-State Limits

- File/class presence documents implementation structure, not proof that every business workflow, external dependency or production integration is exercised.
- HPR-P2-004 does not resolve legacy HMR/HMSR semantic obligations; HPR-P2-007/008 remain the reconciliation/remediation authority.
- `agents`, `environment` and `otsecurity` are not current implemented module roots and are not implied by this document.
