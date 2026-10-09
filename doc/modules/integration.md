# Integration Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.integration`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns external-system registration, integration exchange messages, job-run state and dead-letter records.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [ExternalSystem](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java)
- [IntegrationDeadLetterRecord](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java)
- [IntegrationExchangeMessage](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java)
- [IntegrationJobRun](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java)

Domain policies:

- [IntegrationBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/integration/domain/policy/IntegrationBoundaryPolicy.java)

Domain services:

- [IntegrationPayloadSafetyGuard](../../src/main/java/dz/sh/hidra/modules/integration/domain/service/IntegrationPayloadSafetyGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [RecordExchangeMessageUseCase](../../src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java)
- [RegisterExternalSystemUseCase](../../src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java)
- [StartIntegrationJobRunUseCase](../../src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java)

Current application services:

- [IntegrationApplicationService](../../src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java)

Current API/controller classes:

- [IntegrationController](../../src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/IntegrationController.java)
- [SpringIntegrationController](../../src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/SpringIntegrationController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **24** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ConnectorInstanceJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ConnectorInstanceJpaEntity.java)
- [ExternalEndpointJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalEndpointJpaEntity.java)
- [ExternalObjectReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalObjectReferenceJpaEntity.java)
- [ExternalSystemJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java)
- [IntegrationCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationCatalogEntryJpaEntity.java)
- [IntegrationCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationCatalogTranslationJpaEntity.java)
- [IntegrationDataContractJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDataContractJpaEntity.java)
- [IntegrationDeadLetterRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java)
- [IntegrationExchangeMessageJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java)
- [IntegrationFieldMappingJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationFieldMappingJpaEntity.java)
- [IntegrationHealthSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationHealthSnapshotJpaEntity.java)
- [IntegrationInboundRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationInboundRecordJpaEntity.java)
- [IntegrationJobDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobDefinitionJpaEntity.java)
- [IntegrationJobRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java)
- [IntegrationJobRunStepJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java)
- [IntegrationMappingProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationMappingProfileJpaEntity.java)
- [IntegrationOutboundRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationOutboundRecordJpaEntity.java)
- [IntegrationReconciliationIssueJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationReconciliationIssueJpaEntity.java)
- [IntegrationReconciliationRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationReconciliationRunJpaEntity.java)
- [IntegrationRetryAttemptJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationRetryAttemptJpaEntity.java)
- [IntegrationRetryPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationRetryPolicyJpaEntity.java)
- [IntegrationSchemaVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationSchemaVersionJpaEntity.java)
- [IntegrationSyncCursorJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationSyncCursorJpaEntity.java)
- [IntegrationTransformationRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationTransformationRuleJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaExternalSystemRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java)
- [JpaIntegrationDeadLetterRecordRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java)
- [JpaIntegrationExchangeMessageRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java)
- [JpaIntegrationJobRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [IntegrationResolverContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/IntegrationResolverContract.java)
- [IntegrationOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [ExternalConnectorPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalConnectorPort.java)
- [ExternalSystemRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java)
- [IntegrationAuditPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java)
- [IntegrationDeadLetterRecordRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java)
- [IntegrationExchangeMessageRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java)
- [IntegrationJobRunRepositoryPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java)
- [IntegrationNotificationPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationNotificationPort.java)
- [IntegrationWorkflowPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationWorkflowPort.java)
- [TargetModuleExportPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleExportPort.java)
- [TargetModuleImportPort](../../src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Integration owns external-system configuration, exchange/run and failure evidence. Run numbers are server/database-owned, optional correlated references are validated, and manual dead-letter provenance is authenticated and immutable.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| IntegrationJobRun | [HMSR-014 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integration-integrationjobrun) |
| ExternalSystem | [HMSR-056 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integration-externalsystem) |
| IntegrationExchangeMessage | [HMSR-067 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integration-integrationexchangemessage) |
| IntegrationDeadLetterRecord | [HMSR-084 reconciled rule](../domain/SEMANTIC_DECISIONS.md#integration-integrationdeadletterrecord) |
