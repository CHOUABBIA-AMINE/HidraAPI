# Alarm Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.alarm`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns operational alarm records, acknowledgement/closure evidence, shelving and suppression lifecycle state.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [Alarm](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java)
- [AlarmAcknowledgement](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java)
- [AlarmClosure](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java)
- [AlarmLifecycleEvent](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java)
- [AlarmShelving](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java)

Domain policies:

- [AlarmBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmBoundaryPolicy.java)
- [AlarmShelvingPolicy](../../src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmShelvingPolicy.java)
- [AlarmSuppressionPolicy](../../src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java)

Domain services:

- [AlarmLifecycleGuard](../../src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [AcknowledgeAlarmUseCase](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AcknowledgeAlarmUseCase.java)
- [AlarmQueryUseCase](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmQueryUseCase.java)
- [AlarmSuppressionQueryUseCase](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java)
- [CloseAlarmUseCase](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/in/CloseAlarmUseCase.java)
- [ManageAlarmShelvingUseCase](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java)
- [ManageAlarmSuppressionUseCase](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java)
- [RaiseAlarmUseCase](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/in/RaiseAlarmUseCase.java)

Current application services:

- [AlarmApplicationService](../../src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java)
- [AlarmShelvingApplicationService](../../src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java)
- [AlarmSuppressionApprovalService](../../src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java)

Current API/controller classes:

- [AlarmController](../../src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmController.java)
- [AlarmQueryController](../../src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmQueryController.java)
- [AlarmSuppressionController](../../src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionController.java)
- [SpringAlarmController](../../src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **12** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [AlarmAcknowledgementJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java)
- [AlarmCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogEntryJpaEntity.java)
- [AlarmCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogTranslationJpaEntity.java)
- [AlarmClosureJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java)
- [AlarmCommentJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCommentJpaEntity.java)
- [AlarmEscalationJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEscalationJpaEntity.java)
- [AlarmEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEvidenceLinkJpaEntity.java)
- [AlarmJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java)
- [AlarmLifecycleEventJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmLifecycleEventJpaEntity.java)
- [AlarmRuleBindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmRuleBindingJpaEntity.java)
- [AlarmShelvingJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java)
- [AlarmSuppressionJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaAlarmAcknowledgementRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java)
- [JpaAlarmClosureRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java)
- [JpaAlarmLifecycleEventRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmLifecycleEventRepositoryAdapter.java)
- [JpaAlarmRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmRepositoryAdapter.java)
- [JpaAlarmShelvingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [AlarmSuppressionAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [AlarmSuppressionWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/AlarmSuppressionWorkflowContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [AlarmAcknowledgementRepositoryPort](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java)
- [AlarmClosureRepositoryPort](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java)
- [AlarmLifecycleActorPort](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleActorPort.java)
- [AlarmLifecycleEventRepositoryPort](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleEventRepositoryPort.java)
- [AlarmRepositoryPort](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java)
- [AlarmShelvingRepositoryPort](../../src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Alarm owns formal lifecycle and append-only lifecycle events. Creation, acknowledgement, closure and shelving synchronize parent state and evidence transactionally; suppression and shelving share parent locking. Normal closure uses clear evidence or explicit cancellation.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| Alarm | [HMSR-117 reconciled rule](../domain/SEMANTIC_DECISIONS.md#alarm-alarm) |
| AlarmAcknowledgement | [HMSR-121 reconciled rule](../domain/SEMANTIC_DECISIONS.md#alarm-alarmacknowledgement) |
| AlarmClosure | [HMSR-122 reconciled rule](../domain/SEMANTIC_DECISIONS.md#alarm-alarmclosure) |
| AlarmShelving | [HMSR-123 reconciled rule](../domain/SEMANTIC_DECISIONS.md#alarm-alarmshelving) |

[AlarmLifecycleEvent](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java) is the additional current model outside the 123-review catalogue; it carries lifecycle evidence without an invented HMSR identifier.
