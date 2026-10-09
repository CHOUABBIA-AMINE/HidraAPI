# Telemetry Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.telemetry`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns telemetry sources, telemetry points, raw telemetry readings and trusted telemetry readings.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [TelemetryPoint](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java)
- [TelemetryReading](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java)
- [TelemetrySource](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java)
- [TrustedTelemetryReading](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java)

Domain policies:

- [TelemetryBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryBoundaryPolicy.java)
- [TelemetryTrustPolicy](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java)

Domain services:

- [TelemetryReadingValueValidator](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateTelemetrySourceUseCase](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java)
- [RegisterTelemetryPointUseCase](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java)
- [TelemetryQueryUseCase](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java)
- [TrustTelemetryReadingUseCase](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TrustTelemetryReadingUseCase.java)

Current application services:

- [MonitoringTelemetryPointReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java)
- [MonitoringTrustedReadingReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java)
- [PlanningTelemetryPointReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java)
- [TelemetryPointApplicationService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java)
- [TelemetrySourceApplicationService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java)
- [TrustedTelemetryReadingApplicationService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java)

Current API/controller classes:

- [SpringTelemetryController](../../src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java)
- [TelemetryController](../../src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryController.java)
- [TelemetryQueryController](../../src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **16** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [TelemetryCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java)
- [TelemetryCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogTranslationJpaEntity.java)
- [TelemetryDeviceJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryDeviceJpaEntity.java)
- [TelemetryExternalTagMappingJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryExternalTagMappingJpaEntity.java)
- [TelemetryIngestionBatchJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryIngestionBatchJpaEntity.java)
- [TelemetryPointBindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java)
- [TelemetryPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java)
- [TelemetryPointStateSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java)
- [TelemetryQualityAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryQualityAssessmentJpaEntity.java)
- [TelemetryQuarantineRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryQuarantineRecordJpaEntity.java)
- [TelemetryReadingJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java)
- [TelemetrySourceEndpointJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java)
- [TelemetrySourceJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java)
- [TelemetryUnitJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryUnitJpaEntity.java)
- [TelemetryValidationRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryValidationRuleJpaEntity.java)
- [TrustedTelemetryReadingJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaTelemetryPointRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java)
- [JpaTelemetryReadingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java)
- [JpaTelemetrySourceRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java)
- [JpaTelemetryTrustEvidenceAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryTrustEvidenceAdapter.java)
- [JpaTrustedTelemetryReadingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [MonitoringTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java)
- [MonitoringTrustedReadingReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTrustedReadingReferenceContract.java)
- [PlanningTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java)
- [PlanningUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java)

Imported scalar contracts supplied by collaborating owners:

- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)

Outbound application ports (persistence and collaborating capabilities):

- [TelemetryPointRepositoryPort](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java)
- [TelemetryReadingRepositoryPort](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java)
- [TelemetrySourceRepositoryPort](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java)
- [TelemetryTrustEvidencePort](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java)
- [TrustedTelemetryReadingRepositoryPort](../../src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Telemetry owns acquisition source/point, raw values and assessed trust. The trust use case requires matching PASSED evidence and accepted trust level, eligible point/provenance and explicit selection for ambiguous bindings; raw storage alone is not trust.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| TelemetryPoint | [HMSR-005 reconciled rule](../domain/SEMANTIC_DECISIONS.md#telemetry-telemetrypoint) |
| TelemetrySource | [HMSR-033 reconciled rule](../domain/SEMANTIC_DECISIONS.md#telemetry-telemetrysource) |
| TelemetryReading | [HMSR-037 reconciled rule](../domain/SEMANTIC_DECISIONS.md#telemetry-telemetryreading) |
| TrustedTelemetryReading | [HMSR-062 reconciled rule](../domain/SEMANTIC_DECISIONS.md#telemetry-trustedtelemetryreading) |
