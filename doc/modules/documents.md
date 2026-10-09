# Documents Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.documents`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns documents, document versions, storage-object metadata and links from documents to governed targets.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [Document](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java)
- [DocumentStorageObject](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java)
- [DocumentTargetLink](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java)
- [DocumentVersion](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java)

Domain policies:

- [DocumentsBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/documents/domain/policy/DocumentsBoundaryPolicy.java)

Domain services:

- [DocumentStorageMetadataGuard](../../src/main/java/dz/sh/hidra/modules/documents/domain/service/DocumentStorageMetadataGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [DownloadDocumentVersionContentUseCase](../../src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java)
- [LinkDocumentToTargetUseCase](../../src/main/java/dz/sh/hidra/modules/documents/application/port/in/LinkDocumentToTargetUseCase.java)
- [RegisterDocumentUseCase](../../src/main/java/dz/sh/hidra/modules/documents/application/port/in/RegisterDocumentUseCase.java)
- [UploadDocumentBinaryVersionUseCase](../../src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentBinaryVersionUseCase.java)
- [UploadDocumentVersionUseCase](../../src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java)

Current application services:

- [AuditDocumentReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryService.java)
- [DocumentContentTransferService](../../src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java)
- [DocumentTargetLookupService](../../src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupService.java)
- [DocumentsApplicationService](../../src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java)
- [ReportingDocumentReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryService.java)

Current API/controller classes:

- [DocumentsController](../../src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/DocumentsController.java)
- [SpringDocumentsController](../../src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **11** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [DocumentAccessGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentAccessGrantJpaEntity.java)
- [DocumentCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogEntryJpaEntity.java)
- [DocumentCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogTranslationJpaEntity.java)
- [DocumentExternalReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExternalReferenceJpaEntity.java)
- [DocumentExtractionRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExtractionRecordJpaEntity.java)
- [DocumentJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java)
- [DocumentRetentionRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentRetentionRecordJpaEntity.java)
- [DocumentReviewReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentReviewReferenceJpaEntity.java)
- [DocumentStorageObjectJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java)
- [DocumentTargetLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java)
- [DocumentVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaDocumentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentRepositoryAdapter.java)
- [JpaDocumentStorageObjectRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java)
- [JpaDocumentTargetLinkRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java)
- [JpaDocumentVersionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java)
- [JpaDocumentsCatalogEligibilityAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentsCatalogEligibilityAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [AuditDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/AuditDocumentReferenceContract.java)
- [ReportingDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java)
- [DocumentsOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/target/DocumentsOwnedTargetLookup.java)

Imported scalar contracts supplied by collaborating owners:

- [DocumentsActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [DocumentsApprovalReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/documents/DocumentsApprovalReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [DocumentAuditEventPort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentAuditEventPort.java)
- [DocumentBinaryStoragePort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java)
- [DocumentIdentityReferencePort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java)
- [DocumentIntegrationReferencePort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java)
- [DocumentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java)
- [DocumentStorageObjectRepositoryPort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java)
- [DocumentTargetLinkRepositoryPort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java)
- [DocumentTargetLookupPort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java)
- [DocumentTargetReferencePort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java)
- [DocumentVersionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java)
- [DocumentWorkflowReferencePort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java)
- [DocumentsCatalogEligibilityPort](../../src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentsCatalogEligibilityPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Documents owns stable document identity, content versions, storage metadata and neutral target links. Current/version parenting is local; actor/target snapshots come from owners. Binary upload cleanup distinguishes confirmed rollback from unknown commit outcome.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| DocumentStorageObject | [HMSR-008 reconciled rule](../domain/SEMANTIC_DECISIONS.md#documents-documentstorageobject) |
| Document | [HMSR-079 reconciled rule](../domain/SEMANTIC_DECISIONS.md#documents-document) |
| DocumentVersion | [HMSR-080 reconciled rule](../domain/SEMANTIC_DECISIONS.md#documents-documentversion) |
| DocumentTargetLink | [HMSR-098 reconciled rule](../domain/SEMANTIC_DECISIONS.md#documents-documenttargetlink) |
