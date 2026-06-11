# HIDRA — Workflow Module Data Definition Document

```text
Document code : HIDRA-WORKFLOW-DDD
Module        : workflow
Package       : dz.sh.hidra.modules.workflow
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author        : Abir MEDJERAB
CreatedOn     : 2025-06-26
UpdatedOn     : 2026-06-11
Status        : Repository-aligned data definition document
Evidence      : HidraAPI workflow roadmap + implemented workflow persistence baseline
```

---

## 1. Purpose

The `workflow` module is the Hidra operational process layer.

It owns human and organizational process execution around:

```text
validation
approval
rejection
correction request
delegation
escalation
task assignment
comments
workflow timeline
audit-ready decision evidence
```

Workflow is **not** a generic BPM engine. It is a controlled Hidra process module for operational decisions over domain targets such as telemetry readings, plans, alarms, incidents, integrity cases, maintenance work orders, and other future Hidra objects.

The first supported scenario remains:

```text
TELEMETRY_READING validation workflow
```

---

## 2. Source-of-truth precedence

When defining workflow data, use this precedence:

```text
1. Current HidraAPI workflow roadmap
2. Current HidraAPI workflow persistence entities and migration
3. Hidra modular architecture rules
4. Target DDD corrections and safe future extensions
```

Workflow is already better defined than many later operational modules. Therefore, this document keeps the implemented baseline and adds only controlled target improvements where they strengthen governance without turning workflow into a generic BPM platform.

---

## 3. Ownership boundary

### 3.1 Workflow owns

```text
workflow definitions
workflow definition versions
workflow steps
workflow transitions
workflow instances
workflow tasks
workflow assignments
workflow actions
workflow delegations
workflow escalation rules
workflow comments
workflow state history
workflow target references
actor snapshots
organization snapshots
workflow decision reasons
workflow priorities
workflow audit-ready event payloads
```

### 3.2 Workflow does not own

```text
telemetry readings
telemetry values
telemetry correction values
topology assets
organization units
employees
identity users, roles, groups, credentials
planning targets
monitoring thresholds
alarm lifecycle
incident lifecycle
maintenance execution facts
risk scores
audit storage
notification delivery
analytics models
reporting artifacts
SCADA / PLC / RTU / OT control actions
```

### 3.3 Boundary rule

```text
Workflow owns the process.
The target module owns the business fact.
Audit owns durable evidence storage.
Notification owns message delivery.
Identity owns security identity.
Organization owns real people and structure.
```

Example:

```text
TelemetryReading value/state   -> telemetry
TelemetryReading validation task -> workflow
Approval/rejection reason       -> workflow
Final reading state mutation    -> telemetry through a public port/event
Audit record persistence        -> audit
```

---

## 4. Implemented baseline

The repository baseline contains these core workflow persistence tables and corresponding JPA entities:

```text
hidra_workflow_type_catalog
hidra_workflow_type_translation
hidra_workflow_definition
hidra_workflow_step
hidra_workflow_transition
hidra_workflow_instance
hidra_workflow_task
hidra_workflow_assignment
hidra_workflow_action
hidra_workflow_delegation
hidra_workflow_escalation_rule
hidra_workflow_comment
hidra_workflow_state_history
```

This document treats the above as the current baseline and keeps their names stable.

---

## 5. Conceptual model

```text
WorkflowTypeCatalog
  -> WorkflowTypeTranslation

WorkflowDefinition
  -> WorkflowStep
  -> WorkflowTransition
  -> WorkflowEscalationRule

WorkflowInstance
  -> WorkflowTask
      -> WorkflowAssignment
      -> WorkflowAction
      -> WorkflowDelegation
      -> WorkflowComment
  -> WorkflowStateHistory
```

Target addition for stronger future governance:

```text
WorkflowDefinitionTargetBinding
WorkflowStepAssignmentRule
WorkflowSlaPolicy
WorkflowAuditOutboxReference
```

These target additions are optional for first implementation but recommended before workflow is reused across planning, alarms, incidents, integrity, assets, HSE, custody, and integration.

---

## 6. Entity definitions

---

# 6.1 WorkflowCatalogEntry

## Ownership

Workflow owns controlled vocabulary entries used by workflow definitions, instances, tasks, actions, decisions, reasons, target types, delegation reasons, escalation reasons, and priorities.

## Physical table

```text
hidra_workflow_type_catalog
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable catalog entry identifier. |
| catalogName | string(80) | yes | Catalog namespace. Example: `WORKFLOW_TYPE`, `REASON`, `PRIORITY`, `TARGET_TYPE`. |
| code | string(120) | yes | Stable language-neutral code. |
| active | boolean | yes | Whether the entry can be selected. |
| sortOrder | integer | yes | Display order; must be non-negative. |
| systemDefined | boolean | yes | Whether entry is system-seeded. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Rules

```text
(catalogName, code) must be unique.
User-facing workflow taxonomy values must be catalog-backed.
Business reason/type/priority values must not be Java enums.
Technical lifecycle states may remain constrained enums/check values.
```

## Recommended catalog names

```text
WORKFLOW_TYPE
WORKFLOW_TARGET_TYPE
WORKFLOW_REASON
WORKFLOW_PRIORITY
WORKFLOW_ESCALATION_REASON
WORKFLOW_DELEGATION_REASON
WORKFLOW_ASSIGNMENT_MODE
WORKFLOW_STEP_TYPE
WORKFLOW_OUTCOME_CATEGORY
```

---

# 6.2 WorkflowCatalogTranslation

## Ownership

Workflow owns multilingual labels for workflow catalog entries.

## Physical table

```text
hidra_workflow_type_translation
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable translation identifier. |
| typeId | string(80) | yes | Reference to workflow catalog entry. |
| locale | string(10) | yes | Locale code: `ar`, `fr`, `en`, etc. |
| name | string(160) | yes | Localized display name. |
| description | string(500) | no | Localized description. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Rules

```text
(typeId, locale) must be unique.
French label is mandatory for user-facing workflow taxonomy.
Arabic and English are strongly recommended.
No single-language workflow taxonomy should be accepted for production use.
```

---

# 6.3 WorkflowDefinition

## Ownership

Workflow owns workflow definition identity, version, type, status, and localized names.

A workflow definition is a reusable process template.

## Physical table

```text
hidra_workflow_definition
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable workflow definition identifier. |
| code | string(120) | yes | Business code of workflow definition. |
| nameAr | string(160) | no | Arabic workflow name. Target: should become required for production. |
| nameFr | string(160) | yes | French workflow name. |
| nameEn | string(160) | no | English workflow name. |
| typeId | string(80) | yes | Catalog reference for workflow type. |
| status | enum/string(40) | yes | `DRAFT`, `ACTIVE`, `INACTIVE`, `RETIRED`. |
| version | integer | yes | Definition version; minimum 1. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Rules

```text
(code, version) must be unique.
Only ACTIVE definitions can start new instances.
DRAFT definitions can be edited.
ACTIVE definitions should be immutable except for controlled retirement/deactivation.
Changing an active definition must create a new version.
RETIRED definitions cannot start new instances.
```

## Target correction

Current implementation stores `version` directly on `WorkflowDefinition`. This is acceptable for the first baseline.

For stronger governance, introduce a separate version entity later:

```text
WorkflowDefinition
  -> WorkflowDefinitionVersion
      -> WorkflowStep
      -> WorkflowTransition
      -> WorkflowEscalationRule
```

This prevents accidental mixing between active historical instances and modified definitions.

---

# 6.4 WorkflowStep

## Ownership

Workflow owns the ordered steps inside a workflow definition.

## Physical table

```text
hidra_workflow_step
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable step identifier. |
| definitionId | string(80) | yes | Owning workflow definition. |
| code | string(120) | yes | Stable step code inside the definition. |
| nameAr | string(160) | no | Arabic step name. Target: should become required for production. |
| nameFr | string(160) | yes | French step name. |
| nameEn | string(160) | no | English step name. |
| stepOrder | integer | yes | Step order; must be non-negative. |
| mandatory | boolean | yes | Whether the step is mandatory. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Rules

```text
(definitionId, code) must be unique.
(definitionId, stepOrder) must be unique.
Step order must be >= 0.
A mandatory step cannot be skipped unless transition policy explicitly allows return/cancel/escalation.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| stepTypeId | string(80) | no | Catalog reference for step type: REVIEW, APPROVAL, VALIDATION, CORRECTION, CLOSURE. |
| defaultAssignmentRuleId | string(80) | no | Default assignment rule for this step. |
| slaPolicyId | string(80) | no | SLA/due-date policy for this step. |
| allowClaim | boolean | no | Whether eligible actors can claim task. |
| allowDelegation | boolean | no | Whether delegation is allowed at this step. |
| allowEscalation | boolean | no | Whether escalation is allowed at this step. |

---

# 6.5 WorkflowTransition

## Ownership

Workflow owns allowed transitions between steps based on workflow decisions.

## Physical table

```text
hidra_workflow_transition
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable transition identifier. |
| definitionId | string(80) | yes | Owning workflow definition. |
| fromStepId | string(80) | yes | Source step. |
| toStepId | string(80) | yes | Destination step. |
| decision | enum/string(40) | yes | Decision that triggers transition. |
| reasonRequired | boolean | yes | Whether a reason is required. |
| commentRequired | boolean | yes | Whether a comment is required. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Allowed decision values

```text
APPROVE
REJECT
REQUEST_CORRECTION
CORRECT
RETURN
DELEGATE
ESCALATE
CANCEL
COMMENT
```

## Rules

```text
(definitionId, fromStepId, decision) must be unique.
fromStepId must not equal toStepId.
REJECT, REQUEST_CORRECTION, RETURN, DELEGATE, ESCALATE, CANCEL usually require a reason.
REQUEST_CORRECTION should require a comment/note explaining the correction expected.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| conditionExpression | string/text | no | Controlled condition expression, if transition is conditional. |
| requiredPermissionCode | string(120) | no | Permission needed to execute transition. |
| targetModuleCallback | string(120) | no | Named callback/event to target module after transition. Do not store class names. |

---

# 6.6 WorkflowInstance

## Ownership

Workflow owns the running instance of a workflow definition over a neutral target reference.

Workflow does **not** own the target object.

## Physical table

```text
hidra_workflow_instance
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable workflow instance identifier. |
| definitionId | string(80) | yes | Workflow definition used. |
| definitionVersion | integer | yes | Definition version snapshot at instance start. |
| targetModule | string(80) | yes | Target module name, e.g. `telemetry`, `planning`, `incidents`. |
| targetTypeId | string(80) | yes | Catalog reference for target type. |
| targetId | string(120) | yes | Identifier of target object in its owning module. |
| targetCodeSnapshot | string(120) | no | Target business code snapshot. |
| targetLabelSnapshot | string(240) | no | Target display label snapshot. |
| status | enum/string(40) | yes | Instance lifecycle status. |
| currentStepId | string(80) | no | Current workflow step. |
| startedByActorId | string(80) | yes | Actor who started the workflow. |
| startedByUsernameSnapshot | string(120) | no | Username snapshot at start. |
| startedByDisplayNameSnapshot | string(160) | yes | Actor display-name snapshot at start. |
| startedByRoleCodeSnapshot | string(80) | no | Role code snapshot at start. |
| startedAt | instant | yes | Start timestamp. |
| completedAt | instant | no | Completion timestamp. |
| cancelledAt | instant | no | Cancellation timestamp. |
| correlationId | string(120) | no | Correlation ID for traceability. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Allowed status values

```text
DRAFT
STARTED
IN_PROGRESS
WAITING
COMPLETED
CANCELLED
FAILED
```

## Rules

```text
Only one open workflow instance should exist for the same target and active process purpose.
completedAt must be >= startedAt.
cancelledAt must be >= startedAt.
Workflow instance must preserve target snapshot fields because target labels can change later.
Workflow instance must not import or persist target module data beyond reference and snapshot.
```

## Important correction

The current unique constraint includes `status`:

```text
(targetModule, targetTypeId, targetId, status)
```

This allows multiple open instances with different non-terminal statuses for the same target.

Recommended target rule:

```text
At most one non-terminal workflow instance per target + workflow purpose.
```

Use a partial unique index where supported:

```sql
UNIQUE(target_module, target_type_id, target_id, workflow_purpose_id)
WHERE status IN ('DRAFT', 'STARTED', 'IN_PROGRESS', 'WAITING')
```

If the database does not support partial indexes, enforce this rule in application/domain policy.

---

# 6.7 WorkflowTask

## Ownership

Workflow owns tasks created inside a workflow instance.

A task is the actionable unit assigned to an actor, role, or organization unit.

## Physical table

```text
hidra_workflow_task
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable task identifier. |
| instanceId | string(80) | yes | Owning workflow instance. |
| stepId | string(80) | yes | Workflow step this task represents. |
| status | enum/string(40) | yes | Task lifecycle status. |
| assignedActorId | string(80) | no | Direct assigned actor. |
| assignedActorUsernameSnapshot | string(120) | no | Assigned actor username snapshot. |
| assignedActorDisplayNameSnapshot | string(160) | no | Assigned actor display name snapshot. |
| assignedOrganizationUnitId | string(80) | no | Assigned organization unit. |
| assignedOrganizationUnitNameSnapshot | string(160) | no | Assigned organization unit snapshot. |
| assignedRoleCodeSnapshot | string(80) | no | Assigned role/authority code snapshot. |
| priorityId | string(80) | no | Catalog reference for workflow priority. |
| dueAt | instant | no | Task due date. |
| claimedByActorId | string(80) | no | Actor who claimed task. |
| claimedByUsernameSnapshot | string(120) | no | Claiming actor username snapshot. |
| claimedByDisplayNameSnapshot | string(160) | no | Claiming actor display name snapshot. |
| claimedByRoleCodeSnapshot | string(80) | no | Claiming actor role snapshot. |
| claimedAt | instant | no | Claim timestamp. |
| completedByActorId | string(80) | no | Actor who completed the task. |
| completedByUsernameSnapshot | string(120) | no | Completing actor username snapshot. |
| completedByDisplayNameSnapshot | string(160) | no | Completing actor display name snapshot. |
| completedByRoleCodeSnapshot | string(80) | no | Completing actor role snapshot. |
| completedAt | instant | no | Completion timestamp. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Allowed status values

```text
OPEN
CLAIMED
IN_REVIEW
APPROVED
REJECTED
RETURNED
DELEGATED
ESCALATED
CANCELLED
EXPIRED
```

## Rules

```text
A task must have at least one assignment target: actor or organization unit.
If claimedByActorId exists, claimedAt must exist.
If completedByActorId exists, completedAt must exist.
completedAt must be >= createdAt.
Only eligible actors may claim or complete a task.
Completed tasks are immutable except for audit-safe annotations.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| assignmentModeId | string(80) | no | Catalog reference: DIRECT_ACTOR, ROLE_POOL, ORG_UNIT_POOL, MIXED. |
| taskLabelSnapshot | string(240) | no | User-facing task label at creation time. |
| slaStatus | enum/string | no | NORMAL, DUE_SOON, OVERDUE, BREACHED. |
| escalatedAt | instant | no | Last escalation timestamp. |
| delegatedAt | instant | no | Last delegation timestamp. |
| expiresAt | instant | no | Expiry timestamp if different from due date. |

---

# 6.8 WorkflowAssignment

## Ownership

Workflow owns explicit assignment records attached to workflow tasks.

## Physical table

```text
hidra_workflow_assignment
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable assignment identifier. |
| taskId | string(80) | yes | Owning workflow task. |
| actorId | string(80) | no | Assigned actor reference. |
| actorUsernameSnapshot | string(120) | no | Assigned actor username snapshot. |
| actorDisplayNameSnapshot | string(160) | no | Assigned actor display name snapshot. |
| roleCodeSnapshot | string(80) | no | Assigned role code snapshot. |
| organizationUnitId | string(80) | no | Assigned organization unit reference. |
| organizationUnitNameSnapshot | string(160) | no | Organization unit name snapshot. |
| status | enum/string(40) | yes | Assignment lifecycle status. |
| assignedAt | instant | yes | Assignment timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Allowed status values

```text
ASSIGNED
CLAIMED
RELEASED
COMPLETED
DELEGATED
ESCALATED
CANCELLED
```

## Rules

```text
At least actorId or organizationUnitId must exist.
updatedAt must be >= assignedAt.
Assignment snapshots must be preserved even if identity/organization changes later.
An actor assignment does not create an identity role or organization position.
```

---

# 6.9 WorkflowAction

## Ownership

Workflow owns recorded user/system actions taken against workflow instances and tasks.

Actions are the core decision evidence.

## Physical table

```text
hidra_workflow_action
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable action identifier. |
| instanceId | string(80) | yes | Workflow instance reference. |
| taskId | string(80) | no | Workflow task reference, if task-scoped. |
| actionType | enum/string(40) | yes | Action performed. |
| decision | enum/string(40) | no | Decision outcome, when action is decision-bearing. |
| reasonId | string(80) | no | Catalog reference for decision reason. |
| decisionNote | string(2000) | no | Decision note. |
| commentText | string(2000) | no | Inline comment text. |
| actorId | string(80) | yes | Actor who acted. |
| actorUsernameSnapshot | string(120) | no | Actor username snapshot. |
| actorDisplayNameSnapshot | string(160) | yes | Actor display name snapshot. |
| actorRoleCodeSnapshot | string(80) | no | Actor role snapshot. |
| organizationUnitId | string(80) | no | Organization unit reference at action time. |
| organizationUnitNameSnapshot | string(160) | no | Organization unit name snapshot. |
| organizationRoleCodeSnapshot | string(80) | no | Organization role/position snapshot. |
| correlationId | string(120) | no | Correlation ID. |
| actedAt | instant | yes | Action timestamp. |

## Allowed action types

```text
START
ASSIGN
CLAIM
APPROVE
REJECT
REQUEST_CORRECTION
CORRECT
RETURN
DELEGATE
ESCALATE
CANCEL
COMMENT
COMPLETE
```

## Rules

```text
Actions are append-only decision records.
Decision-bearing actions must follow configured transitions.
REJECT, REQUEST_CORRECTION, RETURN, DELEGATE, ESCALATE, CANCEL require reasonId.
REQUEST_CORRECTION requires commentText.
Action must preserve actor and organization snapshots.
Workflow action does not mutate the target module directly; it emits event/port request.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| actionSequence | long | yes | Monotonic sequence inside instance for deterministic timeline. |
| sourceSystem | string(80) | no | UI, API, SYSTEM, IMPORT, SCHEDULER. |
| ipAddressHash | string(128) | no | Optional privacy-safe access evidence. |
| userAgentHash | string(128) | no | Optional privacy-safe client evidence. |

---

# 6.10 WorkflowDelegation

## Ownership

Workflow owns delegation of tasks from one actor to another actor or organization unit.

## Physical table

```text
hidra_workflow_delegation
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable delegation identifier. |
| taskId | string(80) | yes | Delegated task. |
| fromActorId | string(80) | yes | Actor delegating the task. |
| fromActorUsernameSnapshot | string(120) | no | Source actor username snapshot. |
| fromActorDisplayNameSnapshot | string(160) | yes | Source actor display name snapshot. |
| fromActorRoleCodeSnapshot | string(80) | no | Source actor role snapshot. |
| toActorId | string(80) | no | Target actor. |
| toActorUsernameSnapshot | string(120) | no | Target actor username snapshot. |
| toActorDisplayNameSnapshot | string(160) | no | Target actor display name snapshot. |
| toActorRoleCodeSnapshot | string(80) | no | Target actor role snapshot. |
| toOrganizationUnitId | string(80) | no | Target organization unit. |
| toOrganizationUnitNameSnapshot | string(160) | no | Target organization unit snapshot. |
| toOrganizationRoleCodeSnapshot | string(80) | no | Target organization role snapshot. |
| reasonId | string(80) | yes | Catalog reference for delegation reason. |
| delegatedAt | instant | yes | Delegation timestamp. |

## Rules

```text
Delegation must have a reason.
Delegation must preserve from/to actor and organization snapshots.
Delegation must not change the target module object.
A task cannot be delegated to an ineligible actor or incompatible organization unit.
A task should not be delegated back and forth indefinitely; enforce maximum delegation depth per policy.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| delegationStatus | enum/string | yes | REQUESTED, ACCEPTED, DECLINED, EFFECTIVE, CANCELLED. |
| acceptedAt | instant | no | Acceptance timestamp if delegation requires acceptance. |
| validUntil | instant | no | Delegation validity end. |
| delegationDepth | integer | no | Number of chained delegations. |

---

# 6.11 WorkflowEscalationRule

## Ownership

Workflow owns escalation rules configured for workflow definition steps.

## Physical table

```text
hidra_workflow_escalation_rule
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable escalation rule identifier. |
| definitionId | string(80) | yes | Workflow definition reference. |
| stepId | string(80) | yes | Step to which the rule applies. |
| afterDurationSeconds | integer | yes | Delay before escalation. |
| escalateToActorId | string(80) | no | Target actor. |
| escalateToActorUsernameSnapshot | string(120) | no | Target actor username snapshot. |
| escalateToActorDisplayNameSnapshot | string(160) | no | Target actor display name snapshot. |
| escalateToActorRoleCodeSnapshot | string(80) | no | Target actor role snapshot. |
| escalateToOrganizationUnitId | string(80) | no | Target organization unit. |
| escalateToOrganizationUnitNameSnapshot | string(160) | no | Target organization unit snapshot. |
| escalateToOrganizationRoleCodeSnapshot | string(80) | no | Target organization role snapshot. |
| active | boolean | yes | Whether rule is active. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Rules

```text
afterDurationSeconds must be > 0.
Escalation target must be actor or organization unit/role.
Active escalation rules apply only to active definitions/steps.
Escalation creates workflow action/state history; notification delivery is not owned by workflow.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| escalationReasonId | string(80) | no | Catalog reference for escalation reason. |
| repeatable | boolean | no | Whether escalation may repeat. |
| maxRepeatCount | integer | no | Maximum repeats. |
| escalationLevel | integer | no | Level 1, 2, 3... |
| businessHoursCalendarId | string(80) | no | Calendar reference for SLA timing, if needed. |

---

# 6.12 WorkflowComment

## Ownership

Workflow owns process comments attached to workflow instances or tasks.

## Physical table

```text
hidra_workflow_comment
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable comment identifier. |
| instanceId | string(80) | yes | Workflow instance reference. |
| taskId | string(80) | no | Optional task reference. |
| actorId | string(80) | yes | Commenting actor. |
| actorUsernameSnapshot | string(120) | no | Actor username snapshot. |
| actorDisplayNameSnapshot | string(160) | yes | Actor display name snapshot. |
| actorRoleCodeSnapshot | string(80) | no | Actor role snapshot. |
| commentText | string/text | yes | Comment text. |
| commentedAt | instant | yes | Comment timestamp. |

## Rules

```text
Comments are append-only.
Comments must not be used to store structured business values owned by target modules.
Comments may support workflow decisions but do not replace reasonId when a reason is mandatory.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| visibility | enum/string | no | INTERNAL, TARGET_OWNER, AUDIT_ONLY. |
| parentCommentId | string(80) | no | Threading support if needed. |
| editedAt | instant | no | Only if comment editing is explicitly permitted. Prefer append-only correction comment. |

---

# 6.13 WorkflowStateHistory

## Ownership

Workflow owns state transition history for workflow instances and tasks.

## Physical table

```text
hidra_workflow_state_history
```

## Current implemented fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable state-history identifier. |
| instanceId | string(80) | yes | Workflow instance reference. |
| taskId | string(80) | no | Workflow task reference, if task-scoped. |
| fromStepId | string(80) | no | Previous step. |
| toStepId | string(80) | no | New step. |
| fromStatus | enum/string | no | Previous status. |
| toStatus | enum/string | yes | New status. |
| actorId | string(80) | yes | Actor causing state change. |
| actorUsernameSnapshot | string(120) | no | Actor username snapshot. |
| actorDisplayNameSnapshot | string(160) | yes | Actor display name snapshot. |
| actorRoleCodeSnapshot | string(80) | no | Actor role snapshot. |
| changedAt | instant | yes | Change timestamp. |

## Rules

```text
State history is append-only.
Every lifecycle transition must produce state history.
Every state history row must preserve actor snapshot.
State history must be enough to reconstruct workflow timeline.
```

## Target additions

| Field | Type | Required | Description |
|---|---:|:---:|---|
| actionId | string(80) | no | Link to action that caused the state change. |
| reasonId | string(80) | no | Reason reference for state change. |
| correlationId | string(120) | no | Correlation ID. |

---

# 6.14 WorkflowDefinitionTargetBinding — target addition

## Purpose

Defines which target modules/types a workflow definition may apply to.

Without this, any workflow definition could theoretically start against any target type if application policy is weak.

## Proposed table

```text
hidra_workflow_definition_target_binding
```

## Fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable binding identifier. |
| definitionId | string(80) | yes | Workflow definition. |
| targetModule | string(80) | yes | Allowed target module. |
| targetTypeId | string(80) | yes | Allowed target type catalog reference. |
| workflowPurposeId | string(80) | yes | Purpose such as VALIDATION, APPROVAL, CLOSURE. |
| active | boolean | yes | Whether binding is active. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Rules

```text
(definitionId, targetModule, targetTypeId, workflowPurposeId) must be unique.
Workflow instance can start only if an active binding exists.
```

---

# 6.15 WorkflowStepAssignmentRule — target addition

## Purpose

Defines reusable assignment policy for a workflow step.

## Proposed table

```text
hidra_workflow_step_assignment_rule
```

## Fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable rule identifier. |
| definitionId | string(80) | yes | Workflow definition. |
| stepId | string(80) | yes | Workflow step. |
| assignmentModeId | string(80) | yes | Catalog reference for assignment mode. |
| actorId | string(80) | no | Direct actor, if applicable. |
| roleCode | string(80) | no | Identity role/permission code snapshot/requirement. |
| organizationUnitId | string(80) | no | Organization unit target. |
| organizationRoleCode | string(80) | no | Organization role/position requirement. |
| targetOwnerMode | string(80) | no | Assign to owner of target object via lookup port. |
| active | boolean | yes | Whether rule is active. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

## Rules

```text
Assignment rule must resolve to at least one eligible actor or candidate pool.
Workflow may look up eligibility through ports, not direct imports.
```

---

# 6.16 WorkflowSlaPolicy — target addition

## Purpose

Controls due dates and escalation timing without hardcoding durations in workflow steps.

## Proposed table

```text
hidra_workflow_sla_policy
```

## Fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable SLA policy identifier. |
| code | string(120) | yes | Business code. |
| nameAr | string(160) | no | Arabic name. |
| nameFr | string(160) | yes | French name. |
| nameEn | string(160) | no | English name. |
| durationSeconds | integer | yes | Allowed duration. |
| calendarMode | string(40) | yes | ABSOLUTE_TIME, BUSINESS_HOURS. |
| warningBeforeSeconds | integer | no | Warning threshold. |
| active | boolean | yes | Whether policy is active. |
| createdAt | instant | yes | Creation timestamp. |
| updatedAt | instant | yes | Last update timestamp. |

---

# 6.17 WorkflowAuditOutboxReference — target addition

## Purpose

Tracks audit-ready events emitted by workflow without making workflow the audit storage owner.

## Proposed table

```text
hidra_workflow_audit_outbox_reference
```

## Fields

| Field | Type | Required | Description |
|---|---:|:---:|---|
| id | string(80) | yes | Stable reference identifier. |
| instanceId | string(80) | yes | Workflow instance reference. |
| taskId | string(80) | no | Task reference. |
| actionId | string(80) | no | Action reference. |
| eventType | string(120) | yes | Audit-ready event type. |
| outboxEventId | string(120) | no | Platform outbox event reference. |
| emittedAt | instant | yes | Emission timestamp. |
| status | string(40) | yes | PENDING, EMITTED, FAILED. |
| failureReason | string(1000) | no | Failure detail if emission failed. |

## Rule

```text
This table references audit/outbox emission only.
It must not become the audit evidence store.
```

---

## 7. Main workflows

### 7.1 Telemetry reading validation

```text
TelemetryReading exists
  -> WorkflowInstance starts for target TELEMETRY_READING
      -> WorkflowTask assigned to validator
          -> actor claims or receives task
              -> actor approves/rejects/requests correction/delegates/escalates/comments
                  -> WorkflowAction recorded
                  -> WorkflowStateHistory recorded
                  -> WorkflowAuditEventPort emits audit-ready event
                  -> telemetry remains owner of reading state/value
```

### 7.2 Planning approval

```text
OperationalPlan or PlanRevision submitted
  -> WorkflowInstance starts for target PLANNING_PLAN or PLAN_REVISION
      -> planning owner reviews
          -> approved plan becomes immutable in planning
          -> rejected plan returns to planning with reason
```

Workflow stores the process decision. Planning stores the plan lifecycle state.

### 7.3 Incident closure approval

```text
IncidentResolution proposed
  -> WorkflowInstance starts for target INCIDENT
      -> responsible authority reviews closure evidence
          -> workflow records approval/rejection
          -> incidents module closes or reopens incident through its own command/event
```

Workflow does not own incident status.

---

## 8. Relationship rules

```text
WorkflowCatalogEntry 1 -> N WorkflowCatalogTranslation
WorkflowDefinition 1 -> N WorkflowStep
WorkflowDefinition 1 -> N WorkflowTransition
WorkflowDefinition 1 -> N WorkflowEscalationRule
WorkflowDefinition 1 -> N WorkflowInstance
WorkflowInstance 1 -> N WorkflowTask
WorkflowInstance 1 -> N WorkflowAction
WorkflowInstance 1 -> N WorkflowComment
WorkflowInstance 1 -> N WorkflowStateHistory
WorkflowTask 1 -> N WorkflowAssignment
WorkflowTask 1 -> N WorkflowAction
WorkflowTask 1 -> N WorkflowDelegation
WorkflowTask 1 -> N WorkflowComment
WorkflowTask 1 -> N WorkflowStateHistory
```

---

## 9. Module references

### 9.1 Target reference

Workflow references target module objects using neutral references:

```text
targetModule
targetTypeId
targetId
targetCodeSnapshot
targetLabelSnapshot
```

Allowed examples:

```text
telemetry / TELEMETRY_READING / readingId
planning / OPERATIONAL_PLAN / planId
monitoring / ALERT_CANDIDATE / alertCandidateId
alarms / ALARM / alarmId
incidents / INCIDENT / incidentId
integrity / INTEGRITY_CASE / integrityCaseId
assets / WORK_ORDER / workOrderId
hse / HSE_CASE / hseCaseId
custody / CUSTODY_BATCH / batchId
```

### 9.2 Actor reference

Workflow references actors using:

```text
actorId
usernameSnapshot
displayNameSnapshot
roleCodeSnapshot
```

Workflow does not own users, roles, permissions, groups, credentials, or employee identities.

### 9.3 Organization reference

Workflow references organization context using:

```text
organizationUnitId
organizationUnitNameSnapshot
organizationRoleCodeSnapshot
```

Workflow does not own organization units, positions, reporting lines, administrative addresses, or employee master data.

---

## 10. Validation rules and invariants

### 10.1 Definition rules

```text
A workflow definition must have a code, type, status, and version.
Workflow definition code + version must be unique.
ACTIVE definitions cannot be edited structurally.
New structural changes create a new version.
A definition must contain at least one step before activation.
A definition must contain valid transitions before activation.
```

### 10.2 Step rules

```text
Step code must be unique per definition.
Step order must be unique per definition.
Step order must be non-negative.
Mandatory steps cannot be skipped without explicit transition policy.
```

### 10.3 Transition rules

```text
Transition source and destination steps must belong to the same definition.
fromStepId cannot equal toStepId.
Only configured decisions are allowed from a given step.
Reason/comment requirements must be enforced during action execution.
```

### 10.4 Instance rules

```text
Instance must reference an ACTIVE definition/version.
Instance target must be allowed by WorkflowDefinitionTargetBinding or equivalent policy.
Only one active workflow instance should exist for the same target and purpose.
Completed or cancelled instances are immutable.
Instance must preserve target snapshot at start.
```

### 10.5 Task rules

```text
Task must belong to one instance and one step.
Task must have an assignment target.
Task cannot be completed by an unauthorized or ineligible actor.
Claimed tasks require claimedByActorId and claimedAt.
Completed tasks require completedByActorId and completedAt.
```

### 10.6 Action rules

```text
Action must preserve actor snapshot.
Decision-bearing action must follow a configured transition.
Reject/correction/return/delegation/escalation/cancel decisions require reason.
Request correction requires comment.
Actions are append-only and must not be physically deleted.
```

### 10.7 Delegation rules

```text
Delegation must have a source actor.
Delegation must have a target actor or organization unit.
Delegation requires a reason.
Delegation cannot bypass eligibility rules.
Delegation must preserve source and target snapshots.
```

### 10.8 Escalation rules

```text
Escalation delay must be positive.
Escalation target must resolve to an actor or organization unit/role.
Escalation creates action/history records.
Escalation may request notification, but notification delivery is not workflow-owned.
```

---

## 11. Data lifecycle

### 11.1 Definition lifecycle

```text
DRAFT
  -> ACTIVE
      -> INACTIVE
          -> RETIRED
```

Rules:

```text
DRAFT can be modified.
ACTIVE can start instances.
INACTIVE cannot start new instances but may support existing instances.
RETIRED is historical only.
```

### 11.2 Instance lifecycle

```text
DRAFT
  -> STARTED
      -> IN_PROGRESS
          -> WAITING
          -> COMPLETED
          -> FAILED
      -> CANCELLED
```

### 11.3 Task lifecycle

```text
OPEN
  -> CLAIMED
      -> IN_REVIEW
          -> APPROVED
          -> REJECTED
          -> RETURNED
          -> DELEGATED
          -> ESCALATED
          -> CANCELLED
          -> EXPIRED
```

---

## 12. Integration events

Workflow should publish or emit audit-ready events such as:

```text
WorkflowInstanceStarted
WorkflowTaskAssigned
WorkflowTaskClaimed
WorkflowTaskApproved
WorkflowTaskRejected
WorkflowCorrectionRequested
WorkflowTaskDelegated
WorkflowTaskEscalated
WorkflowCommentAdded
WorkflowInstanceCompleted
WorkflowInstanceCancelled
WorkflowStateChanged
```

### Event payload minimum

```text
eventId
correlationId
workflowInstanceId
workflowTaskId
targetModule
targetTypeId
targetId
actionType
decision
reasonId
commentText/decisionNote if applicable
actorId
actorDisplayNameSnapshot
actorRoleCodeSnapshot
organizationUnitId
organizationUnitNameSnapshot
actedAt
```

---

## 13. Recommended indexes

```sql
CREATE INDEX idx_workflow_catalog_name_code
    ON hidra_workflow_type_catalog (catalog_name, code);

CREATE INDEX idx_workflow_definition_code_version
    ON hidra_workflow_definition (code, version);

CREATE INDEX idx_workflow_instance_target
    ON hidra_workflow_instance (target_module, target_type_id, target_id);

CREATE INDEX idx_workflow_instance_status
    ON hidra_workflow_instance (status);

CREATE INDEX idx_workflow_task_instance_status
    ON hidra_workflow_task (instance_id, status);

CREATE INDEX idx_workflow_task_assigned_actor
    ON hidra_workflow_task (assigned_actor_id, status);

CREATE INDEX idx_workflow_task_assigned_org
    ON hidra_workflow_task (assigned_organization_unit_id, status);

CREATE INDEX idx_workflow_task_due_at
    ON hidra_workflow_task (due_at);

CREATE INDEX idx_workflow_action_instance_time
    ON hidra_workflow_action (instance_id, acted_at);

CREATE INDEX idx_workflow_state_history_instance_time
    ON hidra_workflow_state_history (instance_id, changed_at);
```

Recommended partial index where supported:

```sql
CREATE UNIQUE INDEX uk_workflow_open_instance_target_purpose
    ON hidra_workflow_instance (target_module, target_type_id, target_id)
    WHERE status IN ('DRAFT', 'STARTED', 'IN_PROGRESS', 'WAITING');
```

If multiple workflow purposes are allowed per target, include `workflow_purpose_id` in that index.

---

## 14. Mermaid ER diagram

```mermaid
erDiagram
    WORKFLOW_CATALOG_ENTRY ||--o{ WORKFLOW_CATALOG_TRANSLATION : translates

    WORKFLOW_DEFINITION ||--o{ WORKFLOW_STEP : contains
    WORKFLOW_DEFINITION ||--o{ WORKFLOW_TRANSITION : defines
    WORKFLOW_DEFINITION ||--o{ WORKFLOW_ESCALATION_RULE : configures
    WORKFLOW_DEFINITION ||--o{ WORKFLOW_INSTANCE : starts

    WORKFLOW_STEP ||--o{ WORKFLOW_TASK : creates
    WORKFLOW_STEP ||--o{ WORKFLOW_TRANSITION : from
    WORKFLOW_STEP ||--o{ WORKFLOW_TRANSITION : to

    WORKFLOW_INSTANCE ||--o{ WORKFLOW_TASK : contains
    WORKFLOW_INSTANCE ||--o{ WORKFLOW_ACTION : records
    WORKFLOW_INSTANCE ||--o{ WORKFLOW_COMMENT : comments
    WORKFLOW_INSTANCE ||--o{ WORKFLOW_STATE_HISTORY : history

    WORKFLOW_TASK ||--o{ WORKFLOW_ASSIGNMENT : assigned_to
    WORKFLOW_TASK ||--o{ WORKFLOW_ACTION : acted_on
    WORKFLOW_TASK ||--o{ WORKFLOW_DELEGATION : delegated
    WORKFLOW_TASK ||--o{ WORKFLOW_COMMENT : commented
    WORKFLOW_TASK ||--o{ WORKFLOW_STATE_HISTORY : status_history

    WORKFLOW_CATALOG_ENTRY ||--o{ WORKFLOW_DEFINITION : type
    WORKFLOW_CATALOG_ENTRY ||--o{ WORKFLOW_INSTANCE : target_type
    WORKFLOW_CATALOG_ENTRY ||--o{ WORKFLOW_TASK : priority
    WORKFLOW_CATALOG_ENTRY ||--o{ WORKFLOW_ACTION : reason
    WORKFLOW_CATALOG_ENTRY ||--o{ WORKFLOW_DELEGATION : reason
```

---

## 15. Boundary decision tests

Use these tests when deciding whether a field/entity belongs in workflow.

| Question | Owner |
|---|---|
| Who must review this operational object? | workflow |
| Who approved/rejected/requested correction? | workflow |
| What reason/comment explains the decision? | workflow |
| What task is assigned, claimed, delegated, escalated, or completed? | workflow |
| What is the reading value? | telemetry |
| What is the plan target? | planning |
| What is the monitoring threshold? | monitoring |
| What is the alarm state? | alarm management |
| What is the incident response action? | incident management |
| What is the asset maintenance execution record? | asset management |
| What is the immutable audit evidence store? | audit |
| Which user has which role/permission? | identity |
| Which employee belongs to which unit? | organization |

---

## 16. Implementation guidance

### 16.1 Package ownership

```text
dz.sh.hidra.modules.workflow.domain.model
    WorkflowDefinition
    WorkflowStep
    WorkflowTransition
    WorkflowInstance
    WorkflowTask
    WorkflowAssignment
    WorkflowAction
    WorkflowDelegation
    WorkflowEscalationRule
    WorkflowComment
    WorkflowStateHistory
    WorkflowTypeCatalog
    WorkflowTypeTranslation

 dz.sh.hidra.modules.workflow.application.port.out
    WorkflowTargetLookupPort
    WorkflowActorLookupPort
    WorkflowAuthorityLookupPort
    WorkflowAuditEventPort

 dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
    Workflow*JpaEntity
```

### 16.2 Allowed integration style

```text
workflow -> outbound port -> adapter -> target module public API/contract
```

Forbidden:

```text
workflow imports telemetry.domain
workflow imports topology.domain
workflow imports organization.domain
workflow imports identity.domain
workflow writes target module tables
workflow writes audit tables directly
workflow sends notification directly as business logic
```

---

## 17. Final model

```text
WorkflowDefinition
  -> WorkflowStep
  -> WorkflowTransition
  -> WorkflowEscalationRule
  -> WorkflowInstance
      -> WorkflowTask
          -> WorkflowAssignment
          -> WorkflowAction
          -> WorkflowDelegation
          -> WorkflowComment
      -> WorkflowStateHistory

WorkflowCatalogEntry
  -> WorkflowCatalogTranslation
```

Target governance extensions:

```text
WorkflowDefinitionTargetBinding
WorkflowStepAssignmentRule
WorkflowSlaPolicy
WorkflowAuditOutboxReference
```

---

## 18. Final rule

```text
Workflow must be powerful enough to prove who decided what, when, why, and under which organizational context.

Workflow must not become the owner of the business object being approved.
```
