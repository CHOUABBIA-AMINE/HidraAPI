# Monitoring Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.monitoring`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns monitoring rules and plan-versus-actual deviation records.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [MonitoringRule](../../src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java)
- [PlanActualDeviation](../../src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java)

Domain policies:

- [MonitoringBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/monitoring/domain/policy/MonitoringBoundaryPolicy.java)

Domain services:

- [DeviationSeverityClassifier](../../src/main/java/dz/sh/hidra/modules/monitoring/domain/service/DeviationSeverityClassifier.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateMonitoringRuleUseCase](../../src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java)
- [MonitoringQueryUseCase](../../src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java)
- [RecordDeviationUseCase](../../src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java)

Current application services:

- [DeviationApplicationService](../../src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java)
- [DeviationReferenceValidation](../../src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java)
- [MonitoringRuleApplicationService](../../src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java)

Current API/controller classes:

- [MonitoringController](../../src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringController.java)
- [MonitoringQueryController](../../src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringQueryController.java)
- [SpringMonitoringController](../../src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/SpringMonitoringController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **11** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [MonitoringAcknowledgementJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringAcknowledgementJpaEntity.java)
- [MonitoringAlertCandidateJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringAlertCandidateJpaEntity.java)
- [MonitoringCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringCatalogEntryJpaEntity.java)
- [MonitoringCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringCatalogTranslationJpaEntity.java)
- [MonitoringEvaluationJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringEvaluationJpaEntity.java)
- [MonitoringRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java)
- [MonitoringThresholdJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringThresholdJpaEntity.java)
- [OperationalStateJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/OperationalStateJpaEntity.java)
- [OperationalStateSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/OperationalStateSnapshotJpaEntity.java)
- [PlanActualDeviationJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java)
- [RiskSignalJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/RiskSignalJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaMonitoringRuleRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java)
- [JpaPlanActualDeviationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java)
- [PlanActualDeviationReferenceValidation](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/PlanActualDeviationReferenceValidation.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [MonitoringPlanTargetReferenceContract](../../src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [MonitoringTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java)
- [MonitoringTrustedReadingReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTrustedReadingReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [MonitoringRuleRepositoryPort](../../src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java)
- [PlanActualDeviationRepositoryPort](../../src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Monitoring owns rules/evaluations and actual-versus-planned deviation intelligence. Planning supplies target scalar evidence and Telemetry measured/trusted facts; optional evaluation context is locked and checked without replacing preserved historical snapshots.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| MonitoringRule | [HMSR-048 reconciled rule](../domain/SEMANTIC_DECISIONS.md#monitoring-monitoringrule) |
| PlanActualDeviation | [HMSR-120 reconciled rule](../domain/SEMANTIC_DECISIONS.md#monitoring-planactualdeviation) |
