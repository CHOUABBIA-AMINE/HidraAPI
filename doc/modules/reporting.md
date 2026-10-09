# Reporting Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.reporting`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns report definitions, report requests, report runs and generated report-output artifact metadata.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [ReportDefinition](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java)
- [ReportOutputArtifact](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java)
- [ReportRequest](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java)
- [ReportRun](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java)

Domain policies:

- [ReportingBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/reporting/domain/policy/ReportingBoundaryPolicy.java)

Domain services:

- [ReportReproducibilityGuard](../../src/main/java/dz/sh/hidra/modules/reporting/domain/service/ReportReproducibilityGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateReportDefinitionUseCase](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java)
- [GenerateReportArtifactUseCase](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/in/GenerateReportArtifactUseCase.java)
- [QueueReportRunUseCase](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java)
- [RequestReportUseCase](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/in/RequestReportUseCase.java)

Current application services:

- [ReportingApplicationService](../../src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java)

Current API/controller classes:

- [ReportingController](../../src/main/java/dz/sh/hidra/modules/reporting/api/rest/controller/ReportingController.java)
- [SpringReportingController](../../src/main/java/dz/sh/hidra/modules/reporting/api/rest/controller/SpringReportingController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **22** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ReportAccessPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportAccessPolicyJpaEntity.java)
- [ReportCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportCatalogEntryJpaEntity.java)
- [ReportCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportCatalogTranslationJpaEntity.java)
- [ReportChartResultJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportChartResultJpaEntity.java)
- [ReportDataSourceBindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDataSourceBindingJpaEntity.java)
- [ReportDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java)
- [ReportDistributionRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDistributionRecordJpaEntity.java)
- [ReportDistributionTargetJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDistributionTargetJpaEntity.java)
- [ReportInputSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportInputSnapshotJpaEntity.java)
- [ReportOutputArtifactJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java)
- [ReportParameterDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportParameterDefinitionJpaEntity.java)
- [ReportParameterValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportParameterValueJpaEntity.java)
- [ReportPublicationJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportPublicationJpaEntity.java)
- [ReportRequestJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java)
- [ReportRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java)
- [ReportScheduleJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportScheduleJpaEntity.java)
- [ReportScheduleParameterJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportScheduleParameterJpaEntity.java)
- [ReportSectionDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportSectionDefinitionJpaEntity.java)
- [ReportSectionResultJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportSectionResultJpaEntity.java)
- [ReportTableResultJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportTableResultJpaEntity.java)
- [ReportTemplateJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportTemplateJpaEntity.java)
- [ReportTemplateVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportTemplateVersionJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaReportDefinitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java)
- [JpaReportOutputArtifactRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java)
- [JpaReportQueueEvidenceAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportQueueEvidenceAdapter.java)
- [JpaReportRequestRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java)
- [JpaReportRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [ReportingDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java)
- [ReportingAccessAuthorizationContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java)
- [ReportingOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java)
- [ReportingWorkflowApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [ReportAnalyticsProjectionPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportAnalyticsProjectionPort.java)
- [ReportAuditEventPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportAuditEventPort.java)
- [ReportAuthorizationPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportAuthorizationPort.java)
- [ReportDefinitionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java)
- [ReportDocumentPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDocumentPort.java)
- [ReportIntegrationPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportIntegrationPort.java)
- [ReportNotificationPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportNotificationPort.java)
- [ReportOutputArtifactRepositoryPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java)
- [ReportQueueEvidencePort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportQueueEvidencePort.java)
- [ReportRequestRepositoryPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java)
- [ReportRunRepositoryPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java)
- [ReportSourceDataPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportSourceDataPort.java)
- [ReportStoragePort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportStoragePort.java)
- [ReportTelemetryProjectionPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportTelemetryProjectionPort.java)
- [ReportTopologySnapshotPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportTopologySnapshotPort.java)
- [ReportWorkflowPort](../../src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportWorkflowPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Reporting owns formal report definition/request/run/artifact evidence. Queue eligibility, template/parameters, restricted access and required Workflow approval are checked on the application path; Documents retains binary/document ownership.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| ReportDefinition | [HMSR-013 reconciled rule](../domain/SEMANTIC_DECISIONS.md#reporting-reportdefinition) |
| ReportRequest | [HMSR-057 reconciled rule](../domain/SEMANTIC_DECISIONS.md#reporting-reportrequest) |
| ReportRun | [HMSR-068 reconciled rule](../domain/SEMANTIC_DECISIONS.md#reporting-reportrun) |
| ReportOutputArtifact | [HMSR-110 reconciled rule](../domain/SEMANTIC_DECISIONS.md#reporting-reportoutputartifact) |
