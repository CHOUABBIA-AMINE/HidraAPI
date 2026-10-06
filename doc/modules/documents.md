# Documents Module

## Status

CURRENT — canonical HPR-P2-004 module document.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

Package root: `dz.sh.hidra.modules.documents`

This document describes source-visible current state. It does not promote legacy `docs/**` material to authority and does not substitute for the canonical API/database contracts created by later P2 tasks.

## Responsibility

Owns documents, document versions, storage-object metadata and links from documents to governed targets.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types:

- `Document`
- `DocumentStorageObject`
- `DocumentTargetLink`
- `DocumentVersion`

Domain policies: `DocumentsBoundaryPolicy`

Domain services: `DocumentStorageMetadataGuard`

Canonical semantic context: `../domain/DOMAIN_OWNERSHIP.md`.

## Application and API Surface

Current inbound/use-case ports:

- `DownloadDocumentVersionContentUseCase`
- `LinkDocumentToTargetUseCase`
- `RegisterDocumentUseCase`
- `UploadDocumentBinaryVersionUseCase`
- `UploadDocumentVersionUseCase`

Current application services:

- `DocumentContentTransferService`
- `DocumentsApplicationService`

Current API/controller classes:

- `DocumentsController`
- `SpringDocumentsController`

These class inventories establish implemented adapters/use-case surfaces. Exact HTTP paths, request/response schemas, authentication requirements and compatibility semantics are HPR-P2-005 scope.

## Persistence

Current JPA persistence entity count: **11**.

Persistence entities:

- `DocumentAccessGrantJpaEntity`
- `DocumentCatalogEntryJpaEntity`
- `DocumentCatalogTranslationJpaEntity`
- `DocumentExternalReferenceJpaEntity`
- `DocumentExtractionRecordJpaEntity`
- `DocumentJpaEntity`
- `DocumentRetentionRecordJpaEntity`
- `DocumentReviewReferenceJpaEntity`
- `DocumentStorageObjectJpaEntity`
- `DocumentTargetLinkJpaEntity`
- `DocumentVersionJpaEntity`

Current persistence repository-adapter classes:

- `JpaDocumentRepositoryAdapter`
- `JpaDocumentStorageObjectRepositoryAdapter`
- `JpaDocumentTargetLinkRepositoryAdapter`
- `JpaDocumentVersionRepositoryAdapter`

Table/schema ownership and the generated data dictionary are HPR-P2-006 scope; class presence is not used here to invent database constraints or retention policy.

## Cross-Module Boundary

Exported contracts owned by this module:

- No exported `application.contract.<consumer>` contract was found in this module at the verification baseline.

Current outbound application ports used to reach persistence or collaborating capabilities:

- `DocumentAuditEventPort`
- `DocumentBinaryStoragePort`
- `DocumentIdentityReferencePort`
- `DocumentIntegrationReferencePort`
- `DocumentRepositoryPort`
- `DocumentStorageObjectRepositoryPort`
- `DocumentTargetLinkRepositoryPort`
- `DocumentTargetReferencePort`
- `DocumentVersionRepositoryPort`
- `DocumentWorkflowReferencePort`

Cross-module collaboration must preserve the canonical architecture rule: no direct import of another module's private domain, infrastructure or non-exported application packages.

## Current-State Limits

- File/class presence documents implementation structure, not proof that every business workflow, external dependency or production integration is exercised.
- HPR-P2-004 does not resolve legacy HMR/HMSR semantic obligations; HPR-P2-007/008 remain the reconciliation/remediation authority.
- `agents`, `environment` and `otsecurity` are not current implemented module roots and are not implied by this document.
- Storage abstraction and current adapters do not establish an enterprise records-retention policy; retention governance remains later data-governance scope.
