# Notification Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.notification`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns notification templates, requests, messages and delivery-attempt state.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [NotificationDeliveryAttempt](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java)
- [NotificationMessage](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java)
- [NotificationRequest](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java)
- [NotificationTemplate](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java)

Domain policies:

- [NotificationBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/notification/domain/policy/NotificationBoundaryPolicy.java)

Domain services:

- [NotificationPayloadGuard](../../src/main/java/dz/sh/hidra/modules/notification/domain/service/NotificationPayloadGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateNotificationMessageUseCase](../../src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java)
- [ReceiveNotificationRequestUseCase](../../src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java)
- [RecordDeliveryAttemptUseCase](../../src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java)

Current application services:

- [NotificationApplicationService](../../src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java)

Current API/controller classes:

- [NotificationController](../../src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/NotificationController.java)
- [SpringNotificationController](../../src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **24** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [NotificationAcknowledgementJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationAcknowledgementJpaEntity.java)
- [NotificationBatchJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationBatchJpaEntity.java)
- [NotificationCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationCatalogEntryJpaEntity.java)
- [NotificationCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationCatalogTranslationJpaEntity.java)
- [NotificationChannelJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationChannelJpaEntity.java)
- [NotificationContactPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationContactPointJpaEntity.java)
- [NotificationDeliveryAttemptJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java)
- [NotificationEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationEvidenceLinkJpaEntity.java)
- [NotificationMessageJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java)
- [NotificationMessageVariableJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java)
- [NotificationPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationPolicyJpaEntity.java)
- [NotificationPreferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationPreferenceJpaEntity.java)
- [NotificationRecipientGroupJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRecipientGroupJpaEntity.java)
- [NotificationRecipientGroupMemberJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRecipientGroupMemberJpaEntity.java)
- [NotificationRecipientProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRecipientProfileJpaEntity.java)
- [NotificationRequestJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java)
- [NotificationRequestRecipientJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java)
- [NotificationRetryPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRetryPolicyJpaEntity.java)
- [NotificationScheduleJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationScheduleJpaEntity.java)
- [NotificationStatusHistoryJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationStatusHistoryJpaEntity.java)
- [NotificationSuppressionRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationSuppressionRuleJpaEntity.java)
- [NotificationTemplateJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java)
- [NotificationTemplateTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java)
- [NotificationTemplateVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaNotificationDeliveryAttemptRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java)
- [JpaNotificationMessageRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java)
- [JpaNotificationRequestRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java)
- [JpaNotificationTemplateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

No source class is present in this category at the verified parent.

Outbound application ports (persistence and collaborating capabilities):

- [NotificationActorLookupPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationActorLookupPort.java)
- [NotificationAsyncPushPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationAsyncPushPort.java)
- [NotificationAuditEventPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationAuditEventPort.java)
- [NotificationAuthorityLookupPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationAuthorityLookupPort.java)
- [NotificationDeliveryAttemptRepositoryPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java)
- [NotificationDeliveryGatewayPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryGatewayPort.java)
- [NotificationIntegrationGatewayPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java)
- [NotificationMessageRepositoryPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java)
- [NotificationOrganizationLookupPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationOrganizationLookupPort.java)
- [NotificationProviderGatewayPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationProviderGatewayPort.java)
- [NotificationRecipientResolutionPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRecipientResolutionPort.java)
- [NotificationRequestRepositoryPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java)
- [NotificationTemplateRepositoryPort](../../src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Notification owns template/version governance, composition and delivery attempts. Recipient/channel/render inputs are checked before dispatch; delivery attempts are insert-only evidence and permanent failure cannot schedule automatic retry.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| NotificationTemplate | [HMSR-012 reconciled rule](../domain/SEMANTIC_DECISIONS.md#notification-notificationtemplate) |
| NotificationRequest | [HMSR-039 reconciled rule](../domain/SEMANTIC_DECISIONS.md#notification-notificationrequest) |
| NotificationMessage | [HMSR-061 reconciled rule](../domain/SEMANTIC_DECISIONS.md#notification-notificationmessage) |
| NotificationDeliveryAttempt | [HMSR-072 reconciled rule](../domain/SEMANTIC_DECISIONS.md#notification-notificationdeliveryattempt) |

## T27 recipient-scoped Workflow assignment inbox (implementation under CI verification)

The opted-in `T27_SCOPED_REPORT_APPROVAL_INBOX_V1` Workflow task creation
can publish the exact `WORKFLOW_REPORT_REVIEW_TASK_ASSIGNED_V1` event into
one durable Notification-owned WEB inbox item, in the same source transaction.
The Reporting owner callback validates a SUBMITTED report request, fixed
company/facility scope and current starter authority. A failed Notification
write rolls back Workflow task creation. Legacy request/message/delivery-attempt
APIs and provider gateways remain separate and unchanged.

The three native routes are `GET /api/v1/notification/companies/{companyId}/facilities/{facilityId}/inbox`,
`GET .../inbox/{notificationId}` and bodyless
`POST .../inbox/{notificationId}/acknowledgement`. They require the current
signed LOCAL actor, live paired Notification COMPANY/FACILITY grants and
active Topology owner association; acknowledgement additionally requires
`notification:inbox:acknowledge`. Read queries and acknowledgement writes
are scoped to that authenticated recipient; no total count, recipient PII,
Workflow or Reporting identifier, transport payload or provider metadata is
exposed. Exclusive descending keyset pagination uses bounded opaque cursors.

`hidra_notification_t27_inbox_item` is immutable and unique by source
event/task. `hidra_notification_t27_inbox_acknowledgement` is an immutable
single-recipient receipt with an original-recipient composite FK and
PostgreSQL chronology proof. Duplicate assignment and acknowledgement
must return their original records. WEB DELIVERED confirms durable availability
in HidraAPI, not receipt on a remote device or an authorized Workflow
decision. No OT command, automatic decision or background transport is added.

These descriptions are implementation-scope assertions only; full
fresh-schema PostgreSQL and signed HTTP CI acceptance must pass on the
published source SHA before T27 is marked COMPLETED.
