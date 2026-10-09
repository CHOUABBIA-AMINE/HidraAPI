# Planning Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.planning`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns planning periods, operational plans, plan revisions, plan targets and nominations.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [Nomination](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java)
- [OperationalPlan](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java)
- [PlanRevision](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java)
- [PlanTarget](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java)
- [PlanningPeriod](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java)

Domain policies:

- [PlanningBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/planning/domain/policy/PlanningBoundaryPolicy.java)

Domain services:

- [PlanningPeriodValidator](../../src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateOperationalPlanUseCase](../../src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java)
- [CreatePlanningPeriodUseCase](../../src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java)
- [PlanningApprovalUseCase](../../src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningApprovalUseCase.java)
- [PlanningQueryUseCase](../../src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java)
- [UpdatePlanRevisionUseCase](../../src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java)

Current application services:

- [DocumentsPlanningTargetLookup](../../src/main/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookup.java)
- [MonitoringPlanTargetReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/planning/application/service/MonitoringPlanTargetReferenceQueryService.java)
- [OperationalPlanApplicationService](../../src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java)
- [PlanRevisionUpdateApplicationService](../../src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java)
- [PlanningApprovalApplicationService](../../src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationService.java)
- [PlanningPeriodApplicationService](../../src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java)
- [PlanningQueryApplicationService](../../src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationService.java)
- [PlanningWorkflowTargetLookup](../../src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningWorkflowTargetLookup.java)

Current API/controller classes:

- [PlanRevisionCommandController](../../src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java)
- [PlanningApprovalController](../../src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanningApprovalController.java)
- [PlanningController](../../src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanningController.java)
- [PlanningQueryController](../../src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanningQueryController.java)
- [SpringPlanningController](../../src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/SpringPlanningController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **16** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ExpectedFlowStateJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ExpectedFlowStateJpaEntity.java)
- [ForecastPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ForecastPointJpaEntity.java)
- [ForecastSeriesJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ForecastSeriesJpaEntity.java)
- [NominationJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java)
- [NominationScheduleLineJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java)
- [OperationalPlanJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java)
- [PlanActualReviewSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanActualReviewSnapshotJpaEntity.java)
- [PlanApprovalReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanApprovalReferenceJpaEntity.java)
- [PlanConstraintJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanConstraintJpaEntity.java)
- [PlanRevisionJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java)
- [PlanScenarioJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanScenarioJpaEntity.java)
- [PlanTargetJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java)
- [PlannedOperationWindowJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlannedOperationWindowJpaEntity.java)
- [PlanningCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningCatalogEntryJpaEntity.java)
- [PlanningCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningCatalogTranslationJpaEntity.java)
- [PlanningPeriodJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaNominationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java)
- [JpaOperationalPlanRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java)
- [JpaPlanRevisionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java)
- [JpaPlanTargetRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java)
- [JpaPlanningCatalogEligibilityAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningCatalogEligibilityAdapter.java)
- [JpaPlanningPeriodRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java)
- [JpaPlanningQueryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningQueryAdapter.java)
- [NominationReferenceValidation](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/NominationReferenceValidation.java)
- [PlanTargetReferenceValidation](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/PlanTargetReferenceValidation.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [MonitoringPlanTargetReferenceContract](../../src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java)

Imported scalar contracts supplied by collaborating owners:

- [PlanningProductReferenceContract](../../src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java)
- [DocumentsOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/target/DocumentsOwnedTargetLookup.java)
- [PlanningCreatorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/planning/PlanningCreatorContract.java)
- [PlanningResponsibleUnitContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/planning/PlanningResponsibleUnitContract.java)
- [PlanningPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/planning/PlanningPartyReferenceContract.java)
- [PlanningTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java)
- [PlanningUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java)
- [PlanningTargetTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java)
- [PlanningTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java)
- [PlanningWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java)
- [WorkflowOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java)

Outbound application ports (persistence and collaborating capabilities):

- [NominationRepositoryPort](../../src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java)
- [OperationalPlanRepositoryPort](../../src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java)
- [PlanRevisionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java)
- [PlanTargetRepositoryPort](../../src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java)
- [PlanningCatalogEligibilityPort](../../src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningCatalogEligibilityPort.java)
- [PlanningPeriodRepositoryPort](../../src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java)
- [PlanningQueryPort](../../src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Planning owns planning horizons, plans/revisions, nominations and targets. Same-owner parent contexts and current revision pointers are validated. PlanTarget value policies, approved Custody products and Telemetry unit roles/pairs are explicit actual-ID facts, never inferred from codes.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| PlanningPeriod | [HMSR-006 reconciled rule](../domain/SEMANTIC_DECISIONS.md#planning-planningperiod) |
| PlanRevision | [HMSR-076 reconciled rule](../domain/SEMANTIC_DECISIONS.md#planning-planrevision) |
| OperationalPlan | [HMSR-077 reconciled rule](../domain/SEMANTIC_DECISIONS.md#planning-operationalplan) |
| Nomination | [HMSR-094 reconciled rule](../domain/SEMANTIC_DECISIONS.md#planning-nomination) |
| PlanTarget | [HMSR-111 reconciled rule](../domain/SEMANTIC_DECISIONS.md#planning-plantarget) |
