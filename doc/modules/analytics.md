# Analytics Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.analytics`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns curated analytical datasets, metric/projection execution records, analytical insights and digital-twin readiness assessment data.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [AnalyticsDataset](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java)
- [AnalyticsDatasetVersion](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java)
- [AnalyticsInsight](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java)
- [AnalyticsProjectionRun](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java)
- [DigitalTwinReadinessAssessment](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java)
- [MetricEvaluationRun](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java)
- [MetricValue](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java)

Domain policies:

- [AnalyticsBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/analytics/domain/policy/AnalyticsBoundaryPolicy.java)

Domain services:

- [AnalyticsSourceGuard](../../src/main/java/dz/sh/hidra/modules/analytics/domain/service/AnalyticsSourceGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [AnalyticsDatasetUseCase](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java)
- [AnalyticsInsightUseCase](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java)
- [AnalyticsProjectionUseCase](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java)
- [MetricEvaluationUseCase](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java)

Current application services:

- [AnalyticsApplicationService](../../src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java)

Current API/controller classes:

- [AnalyticsController](../../src/main/java/dz/sh/hidra/modules/analytics/api/rest/controller/AnalyticsController.java)
- [SpringAnalyticsController](../../src/main/java/dz/sh/hidra/modules/analytics/api/rest/controller/SpringAnalyticsController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **28** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [AnalyticsAccessPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsAccessPolicyJpaEntity.java)
- [AnalyticsCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsCatalogEntryJpaEntity.java)
- [AnalyticsCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsCatalogTranslationJpaEntity.java)
- [AnalyticsDataSourceReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDataSourceReferenceJpaEntity.java)
- [AnalyticsDatasetJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetJpaEntity.java)
- [AnalyticsDatasetLineageJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetLineageJpaEntity.java)
- [AnalyticsDatasetVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java)
- [AnalyticsFeatureSetJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsFeatureSetJpaEntity.java)
- [AnalyticsFeatureValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsFeatureValueJpaEntity.java)
- [AnalyticsInsightEvidenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java)
- [AnalyticsInsightJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java)
- [AnalyticsModelJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsModelJpaEntity.java)
- [AnalyticsModelRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsModelRunJpaEntity.java)
- [AnalyticsModelVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsModelVersionJpaEntity.java)
- [AnalyticsProjectionDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionDefinitionJpaEntity.java)
- [AnalyticsProjectionRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java)
- [AnalyticsProjectionSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionSnapshotJpaEntity.java)
- [AnalyticsSubjectAreaJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsSubjectAreaJpaEntity.java)
- [DigitalTwinReadinessAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java)
- [KpiBandJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/KpiBandJpaEntity.java)
- [KpiDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/KpiDefinitionJpaEntity.java)
- [KpiEvaluationJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/KpiEvaluationJpaEntity.java)
- [MetricDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricDefinitionJpaEntity.java)
- [MetricDefinitionVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricDefinitionVersionJpaEntity.java)
- [MetricEvaluationRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java)
- [MetricValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java)
- [TrendAnalysisJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/TrendAnalysisJpaEntity.java)
- [TrendPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/TrendPointJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaAnalyticsDatasetRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetRepositoryAdapter.java)
- [JpaAnalyticsDatasetVersionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java)
- [JpaAnalyticsInsightRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java)
- [JpaAnalyticsProjectionRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java)
- [JpaDigitalTwinReadinessAssessmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java)
- [JpaMetricEvaluationRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java)
- [JpaMetricValueRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [AnalyticsOrganizationScopeContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java)
- [AnalyticsTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [AlarmHistoryLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AlarmHistoryLookupPort.java)
- [AnalyticsDatasetRepositoryPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetRepositoryPort.java)
- [AnalyticsDatasetVersionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java)
- [AnalyticsInsightRepositoryPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java)
- [AnalyticsProjectionRunRepositoryPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java)
- [AssetReliabilityLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AssetReliabilityLookupPort.java)
- [AuditHistoryLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AuditHistoryLookupPort.java)
- [CustodyHistoryLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/CustodyHistoryLookupPort.java)
- [DigitalTwinReadinessAssessmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java)
- [DocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java)
- [HseHistoryLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/HseHistoryLookupPort.java)
- [IncidentHistoryLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/IncidentHistoryLookupPort.java)
- [IntegrityAssessmentLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/IntegrityAssessmentLookupPort.java)
- [MetricEvaluationRunRepositoryPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java)
- [MetricEvaluationScopeResolverPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java)
- [MetricValueRepositoryPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java)
- [MonitoringEvaluationLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MonitoringEvaluationLookupPort.java)
- [PlanningActualComparisonLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/PlanningActualComparisonLookupPort.java)
- [ReportingFeedPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/ReportingFeedPort.java)
- [RiskAssessmentLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java)
- [SimulationDatasetPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/SimulationDatasetPort.java)
- [TopologySnapshotLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/TopologySnapshotLookupPort.java)
- [TrustedTelemetryLookupPort](../../src/main/java/dz/sh/hidra/modules/analytics/application/port/out/TrustedTelemetryLookupPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Analytics owns curated and derived evidence with read-only operational boundaries. Published dataset versions are immutable, execution results preserve source/version/watermark provenance, and insights remain advisory. Readiness is not runtime digital twin execution.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| AnalyticsDataset | [HMSR-017 reconciled rule](../domain/SEMANTIC_DECISIONS.md#analytics-analyticsdataset) |
| MetricEvaluationRun | [HMSR-018 reconciled rule](../domain/SEMANTIC_DECISIONS.md#analytics-metricevaluationrun) |
| AnalyticsInsight | [HMSR-025 reconciled rule](../domain/SEMANTIC_DECISIONS.md#analytics-analyticsinsight) |
| AnalyticsProjectionRun | [HMSR-026 reconciled rule](../domain/SEMANTIC_DECISIONS.md#analytics-analyticsprojectionrun) |
| DigitalTwinReadinessAssessment | [HMSR-027 reconciled rule](../domain/SEMANTIC_DECISIONS.md#analytics-digitaltwinreadinessassessment) |
| AnalyticsDatasetVersion | [HMSR-042 reconciled rule](../domain/SEMANTIC_DECISIONS.md#analytics-analyticsdatasetversion) |
| MetricValue | [HMSR-043 reconciled rule](../domain/SEMANTIC_DECISIONS.md#analytics-metricvalue) |
