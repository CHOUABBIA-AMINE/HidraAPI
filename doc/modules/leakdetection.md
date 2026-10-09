# Leak Detection Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.leakdetection`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns leak candidates, leak-detection cases and escalation references.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [LeakCandidate](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java)
- [LeakDetectionCase](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java)
- [LeakEscalationReference](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java)

Domain policies:

- [LeakDetectionBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/policy/LeakDetectionBoundaryPolicy.java)

Domain services:

- [LeakConfidenceClassifier](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/service/LeakConfidenceClassifier.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateLeakCandidateUseCase](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/port/in/CreateLeakCandidateUseCase.java)
- [EscalateLeakCaseUseCase](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/port/in/EscalateLeakCaseUseCase.java)
- [LeakDetectionQueryUseCase](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/port/in/LeakDetectionQueryUseCase.java)
- [OpenLeakCaseUseCase](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/port/in/OpenLeakCaseUseCase.java)

Current application services:

- [LeakDetectionApplicationService](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/service/LeakDetectionApplicationService.java)
- [LeakDetectionQueryApplicationService](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/service/LeakDetectionQueryApplicationService.java)

Current API/controller classes:

- [LeakDetectionQueryController](../../src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/controller/LeakDetectionQueryController.java)
- [LeakdetectionController](../../src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/controller/LeakdetectionController.java)
- [SpringLeakdetectionController](../../src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/controller/SpringLeakdetectionController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **14** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [LeakCandidateJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java)
- [LeakCaseStatusHistoryJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCaseStatusHistoryJpaEntity.java)
- [LeakDetectionCaseJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java)
- [LeakDetectionMethodCatalogJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionMethodCatalogJpaEntity.java)
- [LeakDetectionMethodTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionMethodTranslationJpaEntity.java)
- [LeakDetectionProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionProfileJpaEntity.java)
- [LeakDetectionRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionRuleJpaEntity.java)
- [LeakDetectionRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionRunJpaEntity.java)
- [LeakDismissalReasonJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDismissalReasonJpaEntity.java)
- [LeakEscalationReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java)
- [LeakEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEvidenceLinkJpaEntity.java)
- [LeakLocalizationEstimateJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakLocalizationEstimateJpaEntity.java)
- [LeakSeverityAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakSeverityAssessmentJpaEntity.java)
- [LeakVerificationActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakVerificationActionJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaLeakCandidateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakCandidateRepositoryAdapter.java)
- [JpaLeakDetectionCaseRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakDetectionCaseRepositoryAdapter.java)
- [JpaLeakEscalationReferenceRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakEscalationReferenceRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [LeakDetectionOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/leakdetection/LeakDetectionOrganizationUnitReferenceContract.java)
- [LeakDetectionTopologyAssetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [LeakCandidateRepositoryPort](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakCandidateRepositoryPort.java)
- [LeakDetectionCaseRepositoryPort](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakDetectionCaseRepositoryPort.java)
- [LeakEscalationReferenceRepositoryPort](../../src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakEscalationReferenceRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

LeakDetection owns suspected candidates, controlled cases and escalation evidence. Classification uses confidence and coherent run/profile provenance; topology/optional Organization evidence comes from owners, while escalation remains a neutral target reference.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| LeakCandidate | [HMSR-015 reconciled rule](../domain/SEMANTIC_DECISIONS.md#leakdetection-leakcandidate) |
| LeakDetectionCase | [HMSR-060 reconciled rule](../domain/SEMANTIC_DECISIONS.md#leakdetection-leakdetectioncase) |
| LeakEscalationReference | [HMSR-071 reconciled rule](../domain/SEMANTIC_DECISIONS.md#leakdetection-leakescalationreference) |
