# Party Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.party`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns party identity and party-role assignments, with supporting party profile/reference persistence.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [Party](../../src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java)
- [PartyRoleAssignment](../../src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java)

Domain policies:

- [PartyBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/party/domain/policy/PartyBoundaryPolicy.java)

Domain services:

- [PartySelectionService](../../src/main/java/dz/sh/hidra/modules/party/domain/service/PartySelectionService.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [AssignPartyRoleUseCase](../../src/main/java/dz/sh/hidra/modules/party/application/port/in/AssignPartyRoleUseCase.java)
- [RegisterPartyUseCase](../../src/main/java/dz/sh/hidra/modules/party/application/port/in/RegisterPartyUseCase.java)

Current application services:

- [AssetsPartyReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java)
- [PartyApplicationService](../../src/main/java/dz/sh/hidra/modules/party/application/service/PartyApplicationService.java)
- [PartyRoleAssignmentApplicationService](../../src/main/java/dz/sh/hidra/modules/party/application/service/PartyRoleAssignmentApplicationService.java)
- [TopologyPartyReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java)

Current API/controller classes:

- [PartyController](../../src/main/java/dz/sh/hidra/modules/party/api/rest/controller/PartyController.java)
- [SpringPartyController](../../src/main/java/dz/sh/hidra/modules/party/api/rest/controller/SpringPartyController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **30** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ContractorQualificationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/ContractorQualificationJpaEntity.java)
- [ManufacturerProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/ManufacturerProfileJpaEntity.java)
- [OperatorProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/OperatorProfileJpaEntity.java)
- [OwnerProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/OwnerProfileJpaEntity.java)
- [PartyAddressJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyAddressJpaEntity.java)
- [PartyBankReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyBankReferenceJpaEntity.java)
- [PartyCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogEntryJpaEntity.java)
- [PartyCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogTranslationJpaEntity.java)
- [PartyCertificationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCertificationJpaEntity.java)
- [PartyComplianceStatusJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyComplianceStatusJpaEntity.java)
- [PartyContactPersonJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPersonJpaEntity.java)
- [PartyContactPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPointJpaEntity.java)
- [PartyDocumentReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyDocumentReferenceJpaEntity.java)
- [PartyExternalReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyExternalReferenceJpaEntity.java)
- [PartyJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java)
- [PartyLegalProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyLegalProfileJpaEntity.java)
- [PartyOwnershipLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyOwnershipLinkJpaEntity.java)
- [PartyQualificationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyQualificationJpaEntity.java)
- [PartyRegistrationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRegistrationJpaEntity.java)
- [PartyRelationshipJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRelationshipJpaEntity.java)
- [PartyRiskSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRiskSnapshotJpaEntity.java)
- [PartyRoleAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java)
- [PartyRoleJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleJpaEntity.java)
- [PartyRoleTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleTranslationJpaEntity.java)
- [PartyStatusHistoryJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyStatusHistoryJpaEntity.java)
- [PartyTaxIdentifierJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTaxIdentifierJpaEntity.java)
- [PartyTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeJpaEntity.java)
- [PartyTypeTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeTranslationJpaEntity.java)
- [SupplierQualificationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/SupplierQualificationJpaEntity.java)
- [VendorQualificationJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/VendorQualificationJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaPartyRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRepositoryAdapter.java)
- [JpaPartyRoleAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRoleAssignmentRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [AssetsPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java)
- [PlanningPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/planning/PlanningPartyReferenceContract.java)
- [TopologyPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java)

Imported scalar contracts supplied by collaborating owners:

No source class is present in this category at the verified parent.

Outbound application ports (persistence and collaborating capabilities):

- [PartyRepositoryPort](../../src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRepositoryPort.java)
- [PartyRoleAssignmentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRoleAssignmentRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Party owns external counterparty identity and effective-dated business roles. Unique master/active assignment identity is preserved without treating Party roles as authorization or transferring ownership to consumer snapshots.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| Party | [HMSR-004 reconciled rule](../domain/SEMANTIC_DECISIONS.md#party-party) |
| PartyRoleAssignment | [HMSR-049 reconciled rule](../domain/SEMANTIC_DECISIONS.md#party-partyroleassignment) |
