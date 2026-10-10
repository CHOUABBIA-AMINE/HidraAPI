# Custody Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.custody`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns custody measurement periods, custody-transfer tickets and custody discrepancy records.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [CustodyDiscrepancy](../../src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java)
- [CustodyMeasurementPeriod](../../src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java)
- [CustodyTransferTicket](../../src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java)

Domain policies:

- [CustodyBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/custody/domain/policy/CustodyBoundaryPolicy.java)

Domain services:

- [CustodyDifferenceCalculator](../../src/main/java/dz/sh/hidra/modules/custody/domain/service/CustodyDifferenceCalculator.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateCustodyTransferTicketUseCase](../../src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java)
- [OpenCustodyDiscrepancyUseCase](../../src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java)
- [OpenCustodyMeasurementPeriodUseCase](../../src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java)

Current application services:

- [CustodyApplicationService](../../src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java)

Current API/controller classes:

- [CustodyController](../../src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/CustodyController.java)
- [SpringCustodyController](../../src/main/java/dz/sh/hidra/modules/custody/api/rest/controller/SpringCustodyController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **20** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [CustodyAgreementJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyAgreementJpaEntity.java)
- [CustodyAgreementPartyJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyAgreementPartyJpaEntity.java)
- [CustodyApprovalReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyApprovalReferenceJpaEntity.java)
- [CustodyBatchJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyBatchJpaEntity.java)
- [CustodyCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyCatalogEntryJpaEntity.java)
- [CustodyCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyCatalogTranslationJpaEntity.java)
- [CustodyCorrectionFactorJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyCorrectionFactorJpaEntity.java)
- [CustodyDiscrepancyJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java)
- [CustodyDocumentReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDocumentReferenceJpaEntity.java)
- [CustodyMeasurementPeriodJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java)
- [CustodyMeasurementSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementSnapshotJpaEntity.java)
- [CustodyMeterRunSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeterRunSnapshotJpaEntity.java)
- [CustodyMeteringSystemJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeteringSystemJpaEntity.java)
- [CustodyQualityCertificateJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyQualityCertificateJpaEntity.java)
- [CustodyQualitySampleJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyQualitySampleJpaEntity.java)
- [CustodyQuantityCalculationJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyQuantityCalculationJpaEntity.java)
- [CustodyReconciliationJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyReconciliationJpaEntity.java)
- [CustodyTicketLineJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTicketLineJpaEntity.java)
- [CustodyTransferPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferPointJpaEntity.java)
- [CustodyTransferTicketJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java)

Persistence repository adapters and reference validators:

- [CustodyTransferTicketReferenceValidation](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyTransferTicketReferenceValidation.java)
- [JpaCustodyDiscrepancyRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java)
- [JpaCustodyMeasurementPeriodRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java)
- [JpaCustodyTransferTicketRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [PlanningProductReferenceContract](../../src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java)

Imported scalar contracts supplied by collaborating owners:

- [CustodyTicketAuditReferenceContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/CustodyTicketAuditReferenceContract.java)
- [CustodyTransferTicketActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [CustodyTransferTicketWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [CustodyAuditReferencePort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyAuditReferencePort.java)
- [CustodyDiscrepancyRepositoryPort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java)
- [CustodyDocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDocumentReferencePort.java)
- [CustodyMeasurementPeriodRepositoryPort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java)
- [CustodyPartyReferencePort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyPartyReferencePort.java)
- [CustodyPlanningReferencePort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyPlanningReferencePort.java)
- [CustodyTelemetrySnapshotPort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTelemetrySnapshotPort.java)
- [CustodyTopologyReferencePort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTopologyReferencePort.java)
- [CustodyTransferTicketRepositoryPort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java)
- [CustodyWorkflowReferencePort](../../src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyWorkflowReferencePort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Custody owns official transfer and reconciliation evidence. Measurement agreement/point correlation is local composite integrity; optional ticket/discrepancy provenance remains optional and fresh cross-module evidence follows owner contracts.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| CustodyMeasurementPeriod | [HMSR-020 reconciled rule](../domain/SEMANTIC_DECISIONS.md#custody-custodymeasurementperiod) |
| CustodyDiscrepancy | [HMSR-029 reconciled rule](../domain/SEMANTIC_DECISIONS.md#custody-custodydiscrepancy) |
| CustodyTransferTicket | [HMSR-082 reconciled rule](../domain/SEMANTIC_DECISIONS.md#custody-custodytransferticket) |

## Qualified Gas Source Revisions

HPR-P25-002C3C introduces [immutable supplied gas sources](../../src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyGasFluidRevision.java) and the exported [exact qualified query](../../src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationGasFluidRevisionContract.java). Source declarations retain actual product snapshots, ordered mole fractions, independently immutable method applicability, evidence, validity and exact governance binding. No fraction normalization, physical default or numerical property algorithm is supplied.

The [JDBC store](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JdbcCustodyGasFluidRevisionRepositoryAdapter.java) verifies canonical bytes and computed digests on every read. Qualification pins an explicit record and reattests the actual final Workflow action and current product/configuration. Withdrawal yields unavailable; corrupted storage raises an integrity error. Synthetic source origin remains synthetic after approval. There is no public write endpoint, GZ2 data or operational eligibility claim. Runtime acceptance remains pending in the execution memories until actual PostgreSQL evidence is verified.
