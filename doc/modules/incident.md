# Incident Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.incident`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns incident lifecycle records, closure evidence, related-incident links and incident response actions.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [Incident](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java)
- [IncidentClosure](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java)
- [IncidentRelatedIncident](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java)
- [IncidentResponseAction](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java)

Domain policies:

- [IncidentBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/incident/domain/policy/IncidentBoundaryPolicy.java)

Domain services:

- [IncidentLifecycleGuard](../../src/main/java/dz/sh/hidra/modules/incident/domain/service/IncidentLifecycleGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CloseIncidentUseCase](../../src/main/java/dz/sh/hidra/modules/incident/application/port/in/CloseIncidentUseCase.java)
- [IncidentQueryUseCase](../../src/main/java/dz/sh/hidra/modules/incident/application/port/in/IncidentQueryUseCase.java)
- [OpenIncidentUseCase](../../src/main/java/dz/sh/hidra/modules/incident/application/port/in/OpenIncidentUseCase.java)
- [RecordIncidentResponseActionUseCase](../../src/main/java/dz/sh/hidra/modules/incident/application/port/in/RecordIncidentResponseActionUseCase.java)

Current application services:

- [IncidentApplicationService](../../src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentApplicationService.java)
- [IncidentQueryApplicationService](../../src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationService.java)

Current API/controller classes:

- [IncidentController](../../src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentController.java)
- [IncidentQueryController](../../src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentQueryController.java)
- [SpringIncidentController](../../src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/SpringIncidentController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **14** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [IncidentAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAssignmentJpaEntity.java)
- [IncidentAttachmentReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAttachmentReferenceJpaEntity.java)
- [IncidentCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogEntryJpaEntity.java)
- [IncidentCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogTranslationJpaEntity.java)
- [IncidentClosureJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java)
- [IncidentEscalationJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEscalationJpaEntity.java)
- [IncidentEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEvidenceLinkJpaEntity.java)
- [IncidentImpactAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentImpactAssessmentJpaEntity.java)
- [IncidentJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java)
- [IncidentRelatedIncidentJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java)
- [IncidentResolutionJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResolutionJpaEntity.java)
- [IncidentResponseActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java)
- [IncidentRootCauseAnalysisJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRootCauseAnalysisJpaEntity.java)
- [IncidentTimelineEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentTimelineEntryJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaIncidentClosureEvidenceAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureEvidenceAdapter.java)
- [JpaIncidentClosureRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureRepositoryAdapter.java)
- [JpaIncidentRelatedIncidentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRelatedIncidentRepositoryAdapter.java)
- [JpaIncidentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRepositoryAdapter.java)
- [JpaIncidentResponseActionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentResponseActionRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [IncidentActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/incident/IncidentActorContract.java)
- [IncidentOrganizationContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/incident/IncidentOrganizationContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [IncidentTopologyContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java)
- [IncidentWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/incident/IncidentWorkflowContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [IncidentClosureEvidencePort](../../src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentClosureEvidencePort.java)
- [IncidentClosureRepositoryPort](../../src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentClosureRepositoryPort.java)
- [IncidentReferencePolicyPort](../../src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentReferencePolicyPort.java)
- [IncidentRelatedIncidentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRelatedIncidentRepositoryPort.java)
- [IncidentRepositoryPort](../../src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRepositoryPort.java)
- [IncidentResponseActionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentResponseActionRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Incident owns governed response and closure, distinct from Alarm. Resolution, persisted policy evidence and required owner approval precede atomic closure; relationship and response-action rules preserve incident identity and serialize against closing.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| Incident | [HMSR-074 reconciled rule](../domain/SEMANTIC_DECISIONS.md#incident-incident) |
| IncidentClosure | [HMSR-107 reconciled rule](../domain/SEMANTIC_DECISIONS.md#incident-incidentclosure) |
| IncidentRelatedIncident | [HMSR-108 reconciled rule](../domain/SEMANTIC_DECISIONS.md#incident-incidentrelatedincident) |
| IncidentResponseAction | [HMSR-109 reconciled rule](../domain/SEMANTIC_DECISIONS.md#incident-incidentresponseaction) |
