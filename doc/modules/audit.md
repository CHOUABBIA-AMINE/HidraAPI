# Audit Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.audit`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns audit events, access records, before/after values and audit-export requests/evidence.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [AuditAccessRecord](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java)
- [AuditBeforeAfterValue](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java)
- [AuditEvent](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java)
- [AuditExportRequest](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java)

Domain policies:

- [AuditBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java)

Domain services:

- [AuditSensitiveDataGuard](../../src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [RecordAuditAccessUseCase](../../src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditAccessUseCase.java)
- [RecordAuditEventUseCase](../../src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java)
- [RequestAuditExportUseCase](../../src/main/java/dz/sh/hidra/modules/audit/application/port/in/RequestAuditExportUseCase.java)

Current application services:

- [AuditApplicationService](../../src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java)
- [AuditInputPolicy](../../src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java)
- [CustodyTicketAuditReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/audit/application/service/CustodyTicketAuditReferenceQueryService.java)

Current API/controller classes:

- [AuditController](../../src/main/java/dz/sh/hidra/modules/audit/api/rest/controller/AuditController.java)
- [SpringAuditController](../../src/main/java/dz/sh/hidra/modules/audit/api/rest/controller/SpringAuditController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **15** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [AuditAccessRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java)
- [AuditActionReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditActionReferenceJpaEntity.java)
- [AuditActorSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditActorSnapshotJpaEntity.java)
- [AuditBeforeAfterValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java)
- [AuditCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditCatalogEntryJpaEntity.java)
- [AuditCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditCatalogTranslationJpaEntity.java)
- [AuditCorrelationContextJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditCorrelationContextJpaEntity.java)
- [AuditDecisionContextJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditDecisionContextJpaEntity.java)
- [AuditEventJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java)
- [AuditEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEvidenceLinkJpaEntity.java)
- [AuditExportRequestJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java)
- [AuditIntegritySealJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditIntegritySealJpaEntity.java)
- [AuditRetentionPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditRetentionPolicyJpaEntity.java)
- [AuditSearchProjectionJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditSearchProjectionJpaEntity.java)
- [AuditTargetReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditTargetReferenceJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaAuditAccessRecordRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java)
- [JpaAuditBeforeAfterValueRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java)
- [JpaAuditCatalogEligibilityAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java)
- [JpaAuditEventRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java)
- [JpaAuditExportRequestRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [AlarmSuppressionAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java)
- [CustodyTicketAuditReferenceContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/CustodyTicketAuditReferenceContract.java)
- [OrganizationResponsibilityAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/organization/OrganizationResponsibilityAuditContract.java)
- [RiskAssessmentAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskAssessmentAuditContract.java)
- [RiskRegisterAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java)
- [SimulationRecommendationAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java)

Imported scalar contracts supplied by collaborating owners:

- [AuditDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/AuditDocumentReferenceContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)

Outbound application ports (persistence and collaborating capabilities):

- [AuditAccessRecordRepositoryPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java)
- [AuditBeforeAfterValueRepositoryPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java)
- [AuditCatalogEligibilityPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java)
- [AuditDocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditDocumentReferencePort.java)
- [AuditEventRepositoryPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java)
- [AuditExportRequestRepositoryPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java)
- [AuditIdentitySnapshotPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditIdentitySnapshotPort.java)
- [AuditOrganizationSnapshotPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditOrganizationSnapshotPort.java)
- [AuditRetentionPolicyPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditRetentionPolicyPort.java)
- [AuditSourceEventConsumerPort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditSourceEventConsumerPort.java)
- [AuditWorkflowReferencePort](../../src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditWorkflowReferencePort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Audit owns append-only event/access/before-after evidence and controlled export requests. Insert-only writes and sanitation preserve evidence integrity; owner-reference existence does not itself prove approval or authorize data export.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| AuditExportRequest | [HMSR-097 reconciled rule](../domain/SEMANTIC_DECISIONS.md#audit-auditexportrequest) |
| AuditEvent | [HMSR-112 reconciled rule](../domain/SEMANTIC_DECISIONS.md#audit-auditevent) |
| AuditAccessRecord | [HMSR-118 reconciled rule](../domain/SEMANTIC_DECISIONS.md#audit-auditaccessrecord) |
| AuditBeforeAfterValue | [HMSR-119 reconciled rule](../domain/SEMANTIC_DECISIONS.md#audit-auditbeforeaftervalue) |
