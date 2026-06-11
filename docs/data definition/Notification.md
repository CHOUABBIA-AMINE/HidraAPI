# HIDRA Notification Data Definition Document

```text
Document code : HIDRA-NOTIFICATION-DDD
Module        : notification
Namespace     : dz.sh.hidra.modules.notification
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC Digitalization Initiative
Author        : Abir MEDJERAB
Status        : Target DDD, repository-aligned to macro-architecture
CreatedOn     : 2026-06-11
Version       : 1.0
```

---

## 1. Purpose

The **notification** module is the controlled communication and delivery-tracking bounded context of Hidra.

It answers:

```text
Who must be notified?
Why is the notification being sent?
Which business event triggered it?
Which channel should be used?
Which template and locale should be used?
Was the message scheduled, sent, delivered, failed, retried, suppressed, or expired?
Can delivery be traced by target object, actor, recipient, channel, and correlation id?
```

Notification is not the owner of the operational event. It is the owner of message preparation, routing, scheduling, dispatch tracking, retry state, and delivery evidence.

The correct rule is:

```text
Business module detects or decides something
  -> business module emits notification request/event
      -> notification resolves recipients, template, channel, schedule, and delivery policy
          -> integration/technical gateway sends through email/SMS/messaging provider
              -> notification records delivery status and retry outcome
```

---

## 2. Source-of-truth positioning

No implemented `notification` Java module was found during repository inspection. The design is therefore a **target data definition** grounded in the Hidra macro architecture.

The macro architecture defines notification as a supporting context for:

```text
notification templates
channels
scheduling and delivery
delivery tracking and status
```

This DDD expands that definition into a precise domain model for operational notification in hydrocarbon pipeline operations.

---

## 3. Scope

### 3.1 Notification owns

```text
Notification templates
Template versions
Localized template content
Notification channels
Recipient profiles and recipient references
Recipient groups / distribution lists
Notification preferences
Notification policies
Notification requests
Notification messages
Notification batches
Delivery attempts
Delivery status history
Retry scheduling
Suppression rules
Quiet-hour rules
Escalation notification routing
Digest schedules
Acknowledgement links, when notification acknowledgement is required
Notification evidence references
Notification catalog entries and translations
```

### 3.2 Notification does not own

```text
Telemetry readings
Telemetry quality/state validation
Monitoring thresholds
Monitoring deviations
Alarm lifecycle and severity decision
Incident lifecycle and response actions
Leak detection cases
Planning approvals
Workflow routing and decisions
Audit ledger storage
HSE cases
Asset maintenance work orders
Custody transfer tickets
Topology assets
Users, roles, groups, permissions
Organization units, employees, positions
External email/SMS/push provider implementation
Business KPI/reporting content
```

### 3.3 Strict ownership rule

```text
Notification delivers communication.
It does not decide operational truth, severity, approval, assignment, closure, or compliance.
```

Examples:

```text
Alarm Management decides alarm severity.
Incident Management decides incident status.
Workflow decides approval state.
HSE decides HSE case status.
Audit stores immutable evidence.
Notification only communicates and tracks delivery.
```

---

## 4. Core business language

| Term | Meaning |
|---|---|
| Notification Request | A request from a business module to notify one or more recipients. |
| Notification Message | A concrete message generated from a request, template, recipient, locale, and channel. |
| Notification Channel | A communication path such as email, SMS, web notification, mobile push, Teams, webhook, or future channel. |
| Template | Reusable message structure with subject/body/content variables. |
| Template Version | Immutable version of a template used for traceability. |
| Recipient | Person, role, organization unit, distribution list, or external address resolved for delivery. |
| Delivery Attempt | One attempt to send a concrete message through a channel. |
| Delivery Status | Current send/delivery state of a message. |
| Suppression | Rule or state preventing a message from being sent. |
| Quiet Hours | Time window during which non-critical notifications are delayed or suppressed. |
| Digest | Grouping several messages into one scheduled summary. |
| Acknowledgement | Optional recipient confirmation that message was seen/accepted. |

---

## 5. Aggregates and entities

### 5.1 `NotificationTemplate`

**Aggregate root.** Defines a reusable notification template.

Fields:

```text
id
code
nameAr
nameFr
nameEn
templateTypeId
categoryId
defaultChannelId
status
currentVersion
systemDefined
createdAt
updatedAt
```

Rules:

```text
Template code must be unique.
Only ACTIVE templates can be used for new messages.
A template version is immutable after activation.
Template business type/category must be catalog-backed, not hard-coded enum business taxonomy.
```

Recommended statuses:

```text
DRAFT
ACTIVE
INACTIVE
RETIRED
```

---

### 5.2 `NotificationTemplateVersion`

Immutable template version used for reproducible message generation.

Fields:

```text
id
templateId
versionNumber
status
subjectTemplate
bodyTemplate
contentFormat
variableSchemaJson
createdByActorId
createdByDisplayNameSnapshot
createdAt
activatedAt
retiredAt
```

Rules:

```text
A sent message must reference the exact template version used.
Activated versions cannot be edited in place.
New wording requires a new version.
Variable schema must define all required placeholders.
```

Formats:

```text
PLAIN_TEXT
HTML
MARKDOWN
JSON
```

---

### 5.3 `NotificationTemplateTranslation`

Localized content for a template version.

Fields:

```text
id
templateVersionId
locale
subject
body
shortText
createdAt
updatedAt
```

Rules:

```text
French content is mandatory for operational Hidra screens.
Arabic and English are optional but recommended.
A message uses recipient locale first, then organization default, then system default.
```

Recommended locales:

```text
ar-DZ
fr-DZ
en-US
```

---

### 5.4 `NotificationChannel`

Defines supported communication channels.

Fields:

```text
id
code
nameAr
nameFr
nameEn
channelType
active
providerReference
supportsDeliveryReceipt
supportsReadReceipt
supportsHtml
supportsAttachments
maxPayloadSize
createdAt
updatedAt
```

Channel types:

```text
EMAIL
SMS
WEB
MOBILE_PUSH
MESSAGING_APP
WEBHOOK
VOICE
```

Rules:

```text
Channel defines notification semantics.
Provider implementation belongs to infrastructure/integration gateway.
Credentials and secrets must not be stored in notification tables.
```

---

### 5.5 `NotificationRecipientProfile`

Notification-specific profile for a recipient reference.

Fields:

```text
id
recipientType
recipientReferenceId
recipientCodeSnapshot
recipientDisplayNameSnapshot
preferredLocale
active
createdAt
updatedAt
```

Recipient types:

```text
ACTOR
EMPLOYEE
ORGANIZATION_UNIT
ROLE
GROUP
EXTERNAL_CONTACT
DISTRIBUTION_LIST
```

Rules:

```text
Notification may store recipient snapshots for delivery evidence.
Identity remains owner of users/roles/permissions.
Organization remains owner of employees, positions, and units.
```

---

### 5.6 `NotificationContactPoint`

Notification delivery address/channel for a recipient profile.

Fields:

```text
id
recipientProfileId
channelId
addressValue
addressLabel
verified
primaryForChannel
active
validFrom
validTo
createdAt
updatedAt
```

Examples:

```text
email address
mobile number
web user id
messaging id
webhook endpoint reference
```

Rules:

```text
A contact point must not reveal secrets.
Sensitive contact values should be masked in logs and audit projections.
Historical sent messages keep snapshot values necessary for evidence.
```

---

### 5.7 `NotificationRecipientGroup`

Managed distribution list or notification group.

Fields:

```text
id
code
nameAr
nameFr
nameEn
groupTypeId
active
createdAt
updatedAt
```

Rules:

```text
A group can target roles, org units, actors, employees, or external contacts.
Membership resolution occurs at send time unless the request explicitly freezes recipients.
```

---

### 5.8 `NotificationRecipientGroupMember`

Member of a recipient group.

Fields:

```text
id
groupId
memberType
memberReferenceId
memberLabelSnapshot
active
validFrom
validTo
createdAt
updatedAt
```

Rules:

```text
Membership must be time-bounded when based on temporary operational duty.
Invalid/expired members are not resolved for new sends.
```

---

### 5.9 `NotificationPreference`

Recipient preference for non-critical notifications.

Fields:

```text
id
recipientProfileId
channelId
categoryId
enabled
quietHoursEnabled
quietHoursStart
quietHoursEnd
timezone
maxFrequencyPerHour
createdAt
updatedAt
```

Rules:

```text
Critical safety/operational notifications can override preferences if policy allows.
Preferences do not override legal, safety, or emergency notification obligations.
```

---

### 5.10 `NotificationPolicy`

Rules controlling delivery behavior for a notification category or source module.

Fields:

```text
id
code
nameAr
nameFr
nameEn
sourceModule
categoryId
priorityId
defaultTemplateId
defaultChannelId
recipientResolutionMode
allowPreferenceOverride
allowQuietHourDelay
requiresAcknowledgement
maxRetryCount
retryPolicyId
active
createdAt
updatedAt
```

Rules:

```text
Policy decides delivery behavior, not business severity.
Business severity comes from the source module.
```

Recipient resolution modes:

```text
FIXED_GROUP
SOURCE_ACTOR
TARGET_RESPONSIBLE_UNIT
ROLE_IN_SCOPE
EXPLICIT_RECIPIENTS
ESCALATION_CHAIN
```

---

### 5.11 `NotificationRequest`

Request to notify recipients about a source event or target object.

Fields:

```text
id
sourceModule
sourceEventType
sourceEventId
targetType
targetId
targetCodeSnapshot
targetLabelSnapshot
categoryId
priorityId
policyId
templateId
templateVersionId
requestedByActorId
requestedByDisplayNameSnapshot
requestedAt
correlationId
requestId
status
expiresAt
createdAt
updatedAt
```

Rules:

```text
Every request must reference a source module and target object or explicit communication reason.
Notification must not mutate the source business object.
A request may generate zero, one, or many messages.
```

Statuses:

```text
RECEIVED
VALIDATED
RECIPIENTS_RESOLVED
MESSAGES_CREATED
SCHEDULED
COMPLETED
PARTIALLY_FAILED
FAILED
CANCELLED
EXPIRED
SUPPRESSED
```

---

### 5.12 `NotificationRequestRecipient`

Resolved recipient for a notification request.

Fields:

```text
id
requestId
recipientType
recipientReferenceId
recipientDisplayNameSnapshot
recipientLocale
resolvedFromType
resolvedFromReferenceId
resolutionStatus
createdAt
```

Resolution statuses:

```text
RESOLVED
NO_CONTACT_POINT
PREFERENCE_DISABLED
SUPPRESSED
INVALID
```

---

### 5.13 `NotificationMessage`

Concrete message for one recipient and one channel.

Fields:

```text
id
requestId
recipientId
channelId
templateId
templateVersionId
locale
subjectRendered
bodyRendered
shortTextRendered
payloadHash
priorityId
status
scheduledAt
expiresAt
createdAt
updatedAt
```

Rules:

```text
Rendered payload must be traceable to template version and variables.
Payload may be stored fully or by secure content reference depending on sensitivity.
Message status belongs to notification.
Business status belongs to source module.
```

Statuses:

```text
DRAFT
READY
SCHEDULED
DISPATCHING
SENT
DELIVERED
READ
ACKNOWLEDGED
FAILED
RETRY_PENDING
SUPPRESSED
CANCELLED
EXPIRED
```

---

### 5.14 `NotificationMessageVariable`

Resolved variable used in rendering a message.

Fields:

```text
id
messageId
variableName
valueType
valueSnapshot
masked
createdAt
```

Rules:

```text
Required template variables must be present before message is READY.
Sensitive values must be masked or stored as redacted snapshots.
```

---

### 5.15 `NotificationBatch`

Groups messages generated together.

Fields:

```text
id
requestId
batchType
status
messageCount
successCount
failureCount
createdAt
completedAt
```

Batch types:

```text
IMMEDIATE
SCHEDULED
DIGEST
RETRY
ESCALATION
```

---

### 5.16 `NotificationDeliveryAttempt`

One attempt to deliver a message through a channel/provider.

Fields:

```text
id
messageId
attemptNumber
channelId
providerReference
providerMessageId
attemptStatus
attemptedAt
completedAt
failureCode
failureMessage
nextRetryAt
correlationId
createdAt
```

Attempt statuses:

```text
STARTED
SENT
DELIVERED
FAILED_TEMPORARY
FAILED_PERMANENT
TIMEOUT
CANCELLED
```

Rules:

```text
Attempts are append-only.
A message can have multiple attempts.
A permanent failure must stop retry scheduling unless manually requeued.
```

---

### 5.17 `NotificationStatusHistory`

Append-only status transitions for a message or request.

Fields:

```text
id
entityType
entityId
fromStatus
toStatus
reasonId
reasonText
changedByActorId
changedByDisplayNameSnapshot
changedAt
correlationId
```

Rules:

```text
Status history is append-only.
It supports operational traceability but does not replace Audit.
Critical transitions should also emit audit events.
```

---

### 5.18 `NotificationRetryPolicy`

Configures retry behavior.

Fields:

```text
id
code
nameAr
nameFr
nameEn
maxAttempts
initialDelaySeconds
maxDelaySeconds
backoffStrategy
retryOnTemporaryFailure
active
createdAt
updatedAt
```

Backoff strategies:

```text
FIXED
LINEAR
EXPONENTIAL
MANUAL_ONLY
```

---

### 5.19 `NotificationSuppressionRule`

Rule preventing or delaying delivery.

Fields:

```text
id
code
sourceModule
categoryId
priorityId
channelId
recipientType
recipientReferenceId
reasonId
active
validFrom
validTo
createdAt
updatedAt
```

Rules:

```text
Suppression must be explicit and traceable.
Critical safety notifications should not be suppressible unless allowed by approved policy.
```

---

### 5.20 `NotificationSchedule`

Represents scheduled delivery, reminders, or digests.

Fields:

```text
id
requestId
messageId
scheduleType
scheduledAt
timezone
recurrenceRule
status
createdAt
updatedAt
```

Schedule types:

```text
ONE_TIME
REMINDER
DIGEST
ESCALATION
RETRY
```

---

### 5.21 `NotificationAcknowledgement`

Recipient acknowledgement for a message when required.

Fields:

```text
id
messageId
recipientId
acknowledgementStatus
acknowledgedByActorId
acknowledgedByDisplayNameSnapshot
acknowledgedAt
commentText
createdAt
```

Statuses:

```text
REQUIRED
ACKNOWLEDGED
DECLINED
EXPIRED
NOT_REQUIRED
```

Rules:

```text
Acknowledgement proves communication receipt/acceptance only.
It does not approve workflow or close an incident.
```

---

### 5.22 `NotificationEvidenceLink`

Reference linking notification evidence to audit, workflow, incidents, alarms, or other target modules.

Fields:

```text
id
notificationRequestId
messageId
evidenceType
referenceModule
referenceType
referenceId
referenceCodeSnapshot
createdAt
```

Examples:

```text
WorkflowActionReference
AuditEventReference
IncidentReference
AlarmReference
HseCaseReference
IntegrationMessageReference
```

---

### 5.23 `NotificationCatalogEntry`

Controlled vocabulary for notification domain values.

Fields:

```text
id
catalogName
code
active
sortOrder
systemDefined
createdAt
updatedAt
```

Catalog examples:

```text
NOTIFICATION_CATEGORY
NOTIFICATION_PRIORITY
TEMPLATE_TYPE
MESSAGE_REASON
SUPPRESSION_REASON
CHANNEL_PROVIDER_TYPE
GROUP_TYPE
```

---

### 5.24 `NotificationCatalogTranslation`

Localized labels for notification catalog entries.

Fields:

```text
id
catalogEntryId
locale
name
description
createdAt
updatedAt
```

Rules:

```text
Do not hard-code business labels in Java enums.
Use catalogs for business taxonomy and technical enums only for lifecycle/status values.
```

---

## 6. Relationships

```text
NotificationTemplate
  └── NotificationTemplateVersion
        └── NotificationTemplateTranslation

NotificationChannel
  └── NotificationContactPoint

NotificationRecipientProfile
  ├── NotificationContactPoint
  └── NotificationPreference

NotificationRecipientGroup
  └── NotificationRecipientGroupMember

NotificationPolicy
  ├── defaultTemplateId -> NotificationTemplate
  ├── defaultChannelId  -> NotificationChannel
  └── retryPolicyId     -> NotificationRetryPolicy

NotificationRequest
  ├── NotificationRequestRecipient
  ├── NotificationMessage
  │     ├── NotificationMessageVariable
  │     ├── NotificationDeliveryAttempt
  │     ├── NotificationStatusHistory
  │     └── NotificationAcknowledgement
  ├── NotificationBatch
  ├── NotificationSchedule
  └── NotificationEvidenceLink
```

---

## 7. Cross-module boundaries

### 7.1 With Alarm Management

```text
Alarm Management owns alarm severity, lifecycle, acknowledgement, shelving, suppression, escalation, and closure.
Notification owns sending alarm-related messages and tracking delivery.
```

Allowed reference:

```text
sourceModule = alarms
targetType = ALARM
targetId = alarmId
```

Forbidden:

```text
notification.alarmStatus
notification.alarmSeverityDecision
notification.alarmAcknowledgementAsBusinessState
```

---

### 7.2 With Incident Management

```text
Incident Management owns incident status, assignment, timeline, response actions, RCA, and closure.
Notification owns communication about incident events.
```

Examples:

```text
Incident opened -> notify supervisor
Incident escalated -> notify duty manager
Incident resolved -> notify stakeholders
```

Forbidden:

```text
Notification must not close, reopen, assign, or resolve incidents.
```

---

### 7.3 With Workflow

```text
Workflow owns task routing, approval, rejection, correction, delegation, escalation, and decision state.
Notification owns message delivery for workflow tasks and reminders.
```

Examples:

```text
WorkflowTaskAssigned -> notify actor
WorkflowTaskOverdue -> send reminder or escalation message
WorkflowTaskApproved -> notify requester
```

Important:

```text
Notification acknowledgement is not workflow approval.
```

---

### 7.4 With Audit

```text
Audit owns immutable evidence of critical actions.
Notification owns operational delivery state.
```

Critical notification actions should publish audit-ready events:

```text
notification request received
message generated
message sent
message failed permanently
message acknowledged
suppression applied
manual resend requested
```

---

### 7.5 With Integration

```text
Notification owns channel semantics and delivery lifecycle.
Integration/infrastructure owns external gateway adapters when provider connection is externalized.
```

Examples:

```text
SMTP provider
SMS gateway
Teams/Slack/Messaging adapter
Webhook gateway
```

Secrets stay outside notification tables.
Notification stores only provider references, provider message ids, statuses, and evidence.

---

### 7.6 With Identity and Organization

```text
Identity owns user/security identity.
Organization owns employees, positions, reporting lines, and organization units.
Notification stores recipient snapshots and resolves recipients through outbound ports.
```

Allowed outbound ports:

```text
NotificationActorLookupPort
NotificationOrganizationLookupPort
NotificationRecipientResolutionPort
NotificationAuthorityLookupPort
```

Forbidden imports:

```text
dz.sh.hidra.modules.identity.domain.*
dz.sh.hidra.modules.organization.domain.*
```

---

### 7.7 With Reporting and Analytics

```text
Reporting may export notification delivery summaries.
Analytics may analyze delivery latency or failure trends.
Notification remains owner of delivery data.
```

---

## 8. Core lifecycle flows

### 8.1 Immediate critical notification

```text
1. Source module emits notification request.
2. Notification validates policy and category.
3. Notification resolves recipients.
4. Notification chooses template, locale, and channel.
5. Notification renders message.
6. Notification dispatches message through channel adapter.
7. Notification records attempt and status.
8. Notification emits audit-ready delivery evidence for critical categories.
```

### 8.2 Scheduled reminder

```text
1. Workflow task has due date.
2. Source module or scheduler requests reminder.
3. Notification creates scheduled message.
4. Scheduler dispatches message at scheduled time.
5. Delivery attempt is tracked.
6. If failed temporarily, retry policy is applied.
```

### 8.3 Escalation notification

```text
1. Source module decides escalation is required.
2. Notification receives escalation request.
3. Notification resolves escalation recipients.
4. Notification applies priority and channel policy.
5. Notification sends message.
6. Notification tracks delivery and acknowledgement if required.
```

### 8.4 Digest notification

```text
1. Multiple eligible events are collected for recipient/category.
2. Digest schedule groups them.
3. Notification renders a summary message.
4. One digest message is sent.
5. Source business objects are not modified by digest delivery.
```

---

## 9. State models

### 9.1 Notification request status

```text
RECEIVED
VALIDATED
RECIPIENTS_RESOLVED
MESSAGES_CREATED
SCHEDULED
COMPLETED
PARTIALLY_FAILED
FAILED
CANCELLED
EXPIRED
SUPPRESSED
```

### 9.2 Notification message status

```text
DRAFT
READY
SCHEDULED
DISPATCHING
SENT
DELIVERED
READ
ACKNOWLEDGED
FAILED
RETRY_PENDING
SUPPRESSED
CANCELLED
EXPIRED
```

### 9.3 Delivery attempt status

```text
STARTED
SENT
DELIVERED
FAILED_TEMPORARY
FAILED_PERMANENT
TIMEOUT
CANCELLED
```

---

## 10. Recommended persistence tables

Initial tables:

```text
hidra_notification_catalog_entry
hidra_notification_catalog_translation
hidra_notification_template
hidra_notification_template_version
hidra_notification_template_translation
hidra_notification_channel
hidra_notification_recipient_profile
hidra_notification_contact_point
hidra_notification_recipient_group
hidra_notification_recipient_group_member
hidra_notification_preference
hidra_notification_policy
hidra_notification_retry_policy
hidra_notification_suppression_rule
hidra_notification_request
hidra_notification_request_recipient
hidra_notification_message
hidra_notification_message_variable
hidra_notification_batch
hidra_notification_delivery_attempt
hidra_notification_status_history
hidra_notification_schedule
hidra_notification_acknowledgement
hidra_notification_evidence_link
```

Indexes:

```text
idx_notification_request_source        (source_module, source_event_type, source_event_id)
idx_notification_request_target        (target_type, target_id)
idx_notification_request_correlation   (correlation_id)
idx_notification_message_status        (status)
idx_notification_message_schedule      (scheduled_at, status)
idx_notification_attempt_message       (message_id, attempt_number)
idx_notification_recipient_reference   (recipient_type, recipient_reference_id)
idx_notification_template_code_version (template_id, version_number)
```

---

## 11. Application ports

### 11.1 Inbound ports

```text
CreateNotificationRequestUseCase
ScheduleNotificationUseCase
CancelNotificationRequestUseCase
RetryNotificationMessageUseCase
AcknowledgeNotificationMessageUseCase
ResolveNotificationRecipientsUseCase
ManageNotificationTemplateUseCase
ManageNotificationPolicyUseCase
ManageNotificationPreferenceUseCase
QueryNotificationDeliveryUseCase
```

### 11.2 Outbound ports

```text
NotificationActorLookupPort
NotificationOrganizationLookupPort
NotificationRecipientResolutionPort
NotificationTemplateRenderingPort
NotificationDeliveryGatewayPort
NotificationAuditEventPort
NotificationIntegrationPort
NotificationClockPort
```

---

## 12. Domain events

```text
NotificationRequestReceived
NotificationRecipientsResolved
NotificationMessageCreated
NotificationMessageScheduled
NotificationMessageDispatched
NotificationMessageSent
NotificationMessageDelivered
NotificationMessageFailed
NotificationMessageRetryScheduled
NotificationMessageSuppressed
NotificationMessageAcknowledged
NotificationRequestCompleted
NotificationRequestFailed
NotificationTemplateActivated
NotificationPolicyChanged
```

Rules:

```text
Events must include correlation id and source reference.
Critical events should be forwarded to audit through NotificationAuditEventPort.
```

---

## 13. REST surface proposal

Base path:

```text
/api/v1/notifications
```

Endpoint groups:

```text
GET    /api/v1/notifications/templates
POST   /api/v1/notifications/templates
POST   /api/v1/notifications/templates/{templateId}/versions
POST   /api/v1/notifications/templates/{templateId}/activate

GET    /api/v1/notifications/channels
POST   /api/v1/notifications/channels

GET    /api/v1/notifications/policies
POST   /api/v1/notifications/policies

POST   /api/v1/notifications/requests
GET    /api/v1/notifications/requests/{requestId}
GET    /api/v1/notifications/requests/{requestId}/messages
POST   /api/v1/notifications/requests/{requestId}/cancel

GET    /api/v1/notifications/messages/{messageId}
POST   /api/v1/notifications/messages/{messageId}/retry
POST   /api/v1/notifications/messages/{messageId}/acknowledge
GET    /api/v1/notifications/messages/{messageId}/attempts

GET    /api/v1/notifications/my/preferences
PUT    /api/v1/notifications/my/preferences

GET    /api/v1/notifications/catalogs/{catalogName}
```

---

## 14. Package proposal

```text
dz.sh.hidra.modules.notification
  api
    rest
      controller
      mapper
      request
      response
  application
    command
    query
    dto
    port
      in
      out
    service
  domain
    model
    value
    event
    policy
    service
    exception
  infrastructure
    configuration
    persistence
      entity
      repository
      mapper
      adapter
    delivery
      email
      sms
      web
      webhook
    scheduler
    messaging
```

Forbidden package names:

```text
shared
common
core
utils
helper
helpers
misc
```

---

## 15. Decision rules

### 15.1 Where to place a concept

| Concept | Owner |
|---|---|
| Message template | Notification |
| Email/SMS/web channel definition | Notification |
| SMTP/SMS provider connector implementation | Notification infrastructure or Integration adapter |
| Alarm severity | Alarm Management |
| Incident status | Incident Management |
| Workflow task state | Workflow |
| User permission | Identity |
| Employee contact master data | Organization or Identity/Contacts source, depending on implementation |
| Delivery attempt | Notification |
| Immutable evidence of critical action | Audit |
| Message delivery dashboard | Reporting/Analytics as derived consumer |

### 15.2 Critical safety rule

```text
Notification may inform people.
Notification must not directly actuate equipment, override SCADA, or execute operational control actions.
```

---

## 16. Acceptance criteria

Notification is accepted when:

```text
templates are versioned and localized
messages are traceable to exact template version
requests reference source module, source event, target object, actor, and correlation id
recipients are resolved through ports, not foreign domain imports
delivery attempts are append-only
retry behavior is controlled by policy
suppression is explicit and traceable
critical notifications can emit audit-ready events
business modules remain owners of business state
secrets are not stored in notification tables
```

---

## 17. Recommended implementation order

```text
NOTIF-001 package skeleton
NOTIF-002 catalog entries and translations
NOTIF-003 template and template version model
NOTIF-004 channel model
NOTIF-005 recipient profile and contact point model
NOTIF-006 notification policy and retry policy model
NOTIF-007 notification request model
NOTIF-008 message and message variable model
NOTIF-009 delivery attempt and status history model
NOTIF-010 scheduler and retry application services
NOTIF-011 delivery gateway outbound port
NOTIF-012 email/web stub adapters
NOTIF-013 acknowledgement model
NOTIF-014 audit event port
NOTIF-015 REST API
NOTIF-016 architecture tests
```

---

## 18. Final rule

```text
The source module owns why a notification is needed.
Notification owns how, when, to whom, through which channel, and with what delivery result.
```
