# Workflow Module Roadmap

```text
Roadmap file : docs/roadmap/workflow.md
Roadmap code : WF
First task   : WF-001 — docs(workflow): prepare workflow module roadmap
Repository   : HidraAPI
Namespace    : dz.sh.hidra
Module       : workflow
Package      : dz.sh.hidra.modules.workflow
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-07
Status       : Draft for discussion
```

---

## 1. Purpose

The workflow module orchestrates validation, approval, rejection, correction request, escalation, delegation, and task assignment processes across Hidra operational data.

Workflow is not a generic BPM engine.

Workflow is the Hidra process layer that answers:

```text
Who must review this operational object?
Who acted?
When did they act?
What decision did they make?
Why did they approve, reject, correct, delegate, or escalate?
What state did the process move to?
Can the decision be proven later?
```

Workflow exists because telemetry readings, future flow records, incidents, plans, and risk actions must move through controlled human and operational decisions before Hidra treats them as trusted operational truth.

---

## 2. Architecture alignment

The micro-architecture implementation order is:

```text
1. repository skeleton
2. kernel
3. platform foundation
4. identity
5. organization
6. topology
7. telemetry
8. workflow
9. planning
10. monitoring
11. incidents
12. audit hardening
13. integration
14. analytics/reporting
15. notification
```

Therefore workflow is the correct next module after telemetry.

Important correction:

```text
Audit event ports are defined in workflow.
Full audit hardening is not the next full module after workflow.
Full audit hardening is scheduled after incidents according to the architecture order.
```

---

## 3. Naming standard

Use only current repository names:

```text
kernel
identity
dz.sh.hidra
```


---

## 4. Workflow scope

Workflow owns:

```text
workflow definitions
workflow versions
workflow instances
workflow tasks
workflow actions
workflow assignments
approval steps
delegation records
escalation rules
transition rules
decision reasons
workflow state history
workflow target references
workflow actor references
workflow comments
workflow due dates
```

Workflow does not own:

```text
telemetry readings
telemetry ingestion
topology assets
organization units
users or identity credentials
flow calculations
planning targets
monitoring thresholds
alerts
incidents
risk scoring
analytics
notification delivery
audit storage
```

Workflow references other modules by stable identifiers and snapshots only.

---

## 5. Bounded context

### 5.1 Workflow context

Workflow models operational process execution.

It owns process state, tasks, assignments, transitions, and decisions.

### 5.2 Validation lifecycle

Validation lifecycle is not a separate module at this stage.

Telemetry owns reading facts, quality, correction history, and reading state.

Workflow orchestrates actor-driven process around those readings.

### 5.3 First target

For version 1, workflow supports:

```text
TELEMETRY_READING
```

Future targets:

```text
FLOW_RECORD
INCIDENT
PLAN_CHANGE
RISK_REVIEW
```

---

## 6. Dependency rules

Allowed framework/foundation imports:

```text
java.*
jakarta.validation.*
org.springframework.* only in api/infrastructure/configuration
dz.sh.hidra.kernel.*
```

Workflow may reference external modules through application ports and neutral references only:

```text
WorkflowTargetLookupPort
WorkflowActorLookupPort
WorkflowAuthorityLookupPort
WorkflowAuditEventPort
```

Forbidden workflow imports:

```text
dz.sh.hidra.modules.telemetry.domain.*
dz.sh.hidra.modules.telemetry.infrastructure.*
dz.sh.hidra.modules.topology.domain.*
dz.sh.hidra.modules.topology.infrastructure.*
dz.sh.hidra.modules.organization.domain.*
dz.sh.hidra.modules.identity.domain.*
dz.sh.hidra.modules.audit.domain.*
dz.sh.hidra.modules.planning.*
dz.sh.hidra.modules.monitoring.*
dz.sh.hidra.modules.incidents.*
dz.sh.hidra.modules.analytics.*
```

---

## 7. Package structure

Production packages:

```text
src/main/java/dz/sh/hidra/modules/workflow
src/main/java/dz/sh/hidra/modules/workflow/domain
src/main/java/dz/sh/hidra/modules/workflow/domain/model
src/main/java/dz/sh/hidra/modules/workflow/domain/value
src/main/java/dz/sh/hidra/modules/workflow/domain/policy
src/main/java/dz/sh/hidra/modules/workflow/domain/service
src/main/java/dz/sh/hidra/modules/workflow/application
src/main/java/dz/sh/hidra/modules/workflow/application/command
src/main/java/dz/sh/hidra/modules/workflow/application/query
src/main/java/dz/sh/hidra/modules/workflow/application/dto
src/main/java/dz/sh/hidra/modules/workflow/application/port/in
src/main/java/dz/sh/hidra/modules/workflow/application/port/out
src/main/java/dz/sh/hidra/modules/workflow/application/service
src/main/java/dz/sh/hidra/modules/workflow/infrastructure
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry
src/main/java/dz/sh/hidra/modules/workflow/api
src/main/java/dz/sh/hidra/modules/workflow/api/rest
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller
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
legacy
deprecated
compatibility
bridge
```

---

## 8. Domain model

Aggregates:

```text
WorkflowDefinition
WorkflowInstance
```

Entities:

```text
WorkflowStep
WorkflowTransition
WorkflowTask
WorkflowAction
WorkflowAssignment
WorkflowDelegation
WorkflowEscalationRule
WorkflowComment
WorkflowStateHistory
WorkflowReasonCatalog
WorkflowReasonTranslation
```

Value objects:

```text
WorkflowDefinitionId
WorkflowInstanceId
WorkflowTaskId
WorkflowActionId
WorkflowStepId
WorkflowTransitionId
WorkflowAssignmentId
WorkflowDelegationId
WorkflowEscalationRuleId
WorkflowCommentId
WorkflowCode
WorkflowName
WorkflowLocalizedName
WorkflowTargetReference
WorkflowActorReference
WorkflowOrganizationReference
WorkflowReasonReference
WorkflowDueDate
WorkflowPriority
WorkflowCommentText
WorkflowDecisionNote
WorkflowCorrelationId
```

Allowed technical enums:

```text
WorkflowDefinitionStatus
WorkflowInstanceStatus
WorkflowTaskStatus
WorkflowActionType
WorkflowDecision
WorkflowAssignmentStatus
WorkflowEscalationStatus
```

Business taxonomies must be catalog-backed:

```text
WorkflowTypeReference
WorkflowReasonReference
WorkflowPriorityReference
WorkflowTargetTypeReference
WorkflowEscalationReasonReference
WorkflowDelegationReasonReference
```

Catalog domain models:

```text
WorkflowTypeCatalog
WorkflowTypeTranslation
```

---

## 9. Multilingual policy

User-facing labels must support:

```text
nameAr
nameFr
nameEn
```

Catalog responses expose:

```text
id
code
label
locale
```

Reject single-language user-facing labels.

---

## 10. First workflow use case: telemetry reading validation

Business flow:

```text
1. Telemetry reading is received.
2. Workflow instance starts for target TELEMETRY_READING.
3. Workflow assigns review task to validator or validator group.
4. Validator reviews reading, quality, timestamp, source, and target context.
5. Validator approves, rejects, requests correction, or delegates.
6. Workflow records action, reason, actor, timestamp, and comment.
7. Workflow emits audit-ready event through WorkflowAuditEventPort.
8. Workflow completes or moves to next step.
9. Telemetry remains owner of reading value and reading state.
```

Target reference example:

```text
targetModule       = telemetry
targetTypeCode     = TELEMETRY_READING
targetId           = reading-001
targetCodeSnapshot = PT-001
targetLabelSnapshot = Pression ligne / 2026-06-07T10:00:00Z
```

Actor reference snapshot:

```text
actorId
actorUsernameSnapshot
actorDisplayNameSnapshot
organizationUnitId
organizationUnitNameSnapshot
roleCodeSnapshot
```

---

## 11. Application use cases

Definition use cases:

```text
CreateWorkflowDefinitionUseCase
ActivateWorkflowDefinitionUseCase
DeactivateWorkflowDefinitionUseCase
GetWorkflowDefinitionUseCase
ListWorkflowDefinitionsUseCase
CreateWorkflowStepUseCase
CreateWorkflowTransitionUseCase
```

Instance use cases:

```text
StartWorkflowInstanceUseCase
CancelWorkflowInstanceUseCase
GetWorkflowInstanceUseCase
ListWorkflowInstancesUseCase
GetWorkflowTimelineUseCase
```

Task use cases:

```text
AssignWorkflowTaskUseCase
ClaimWorkflowTaskUseCase
CompleteWorkflowTaskUseCase
ApproveWorkflowTaskUseCase
RejectWorkflowTaskUseCase
RequestWorkflowCorrectionUseCase
DelegateWorkflowTaskUseCase
EscalateWorkflowTaskUseCase
CommentWorkflowTaskUseCase
ListMyWorkflowTasksUseCase
ListWorkflowTasksUseCase
```

Catalog use cases:

```text
GetWorkflowCatalogTypeUseCase
ListWorkflowCatalogTypesUseCase
ResolveWorkflowCatalogTypeUseCase
```

---

## 12. Persistence schema

Tables:

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

Entity relationship view:

```text
hidra_workflow_type_catalog
  └── hidra_workflow_type_translation

hidra_workflow_definition
  ├── hidra_workflow_step
  ├── hidra_workflow_transition
  └── hidra_workflow_escalation_rule

hidra_workflow_instance
  ├── hidra_workflow_task
  │     ├── hidra_workflow_assignment
  │     ├── hidra_workflow_action
  │     ├── hidra_workflow_delegation
  │     └── hidra_workflow_comment
  └── hidra_workflow_state_history
```

Initial Flyway migration:

```text
V023__create_workflow_catalog_and_core_tables.sql
```

The migration number must be adjusted if the repository sequence advances.

---

## 13. REST API

Base path:

```text
/api/v1/workflows
```

Endpoint groups:

```text
/api/v1/workflows/catalog-types
/api/v1/workflows/definitions
/api/v1/workflows/definitions/{definitionId}/steps
/api/v1/workflows/definitions/{definitionId}/transitions
/api/v1/workflows/instances
/api/v1/workflows/instances/{instanceId}
/api/v1/workflows/instances/{instanceId}/timeline
/api/v1/workflows/tasks
/api/v1/workflows/tasks/my
/api/v1/workflows/tasks/{taskId}/claim
/api/v1/workflows/tasks/{taskId}/approve
/api/v1/workflows/tasks/{taskId}/reject
/api/v1/workflows/tasks/{taskId}/request-correction
/api/v1/workflows/tasks/{taskId}/delegate
/api/v1/workflows/tasks/{taskId}/escalate
/api/v1/workflows/tasks/{taskId}/comments
```

---

## 14. Prioritized implementation roadmap

```text
WF-001 — docs(workflow): prepare workflow module roadmap
WF-002 — chore(workflow): add workflow package skeleton
WF-003 — feat(workflow): add workflow domain value objects
WF-004 — feat(workflow): add workflow catalog domain models
WF-005 — feat(workflow): add workflow core domain models
WF-006 — feat(workflow): add workflow domain policies
WF-007 — feat(workflow): add workflow domain services
WF-008 — feat(workflow): add workflow application commands and queries
WF-009 — feat(workflow): add workflow application DTOs
WF-010 — feat(workflow): add workflow application ports
WF-011 — feat(workflow): add workflow application services
WF-012 — feat(workflow): add workflow infrastructure configuration
WF-013 — db(workflow): add workflow catalog and core tables migration
WF-014 — feat(workflow): add workflow persistence entities and repositories
WF-015 — feat(workflow): add workflow persistence mappers and adapters
WF-016 — feat(workflow): add workflow telemetry target lookup adapter
WF-017 — feat(workflow): add workflow REST request DTOs
WF-018 — feat(workflow): add workflow REST response DTOs
WF-019 — feat(workflow): add workflow REST mapper
WF-020 — feat(workflow): add workflow REST controllers
WF-021 — test(workflow): add workflow domain tests
WF-022 — test(workflow): add workflow application service tests
WF-023 — test(workflow): add workflow persistence tests
WF-024 — test(workflow): add workflow REST mapper and controller tests
WF-025 — test(workflow): add workflow application boot smoke test
WF-026 — docs(workflow): finalize workflow validation checklist
```

---

## 15. Hard reject criteria

Reject workflow implementation if:

```text
workflow imports telemetry domain classes
workflow imports topology domain classes
workflow imports identity domain classes
workflow imports organization domain classes
workflow owns telemetry readings
workflow owns topology assets
workflow owns audit storage
workflow stores business reason types as Java enums
workflow exposes single-language labels
workflow uses shared/common/core/utils/helper/misc packages
workflow controllers contain business logic
workflow application services use JPA repositories directly
workflow persistence adapters leak into domain/application
workflow boot smoke is weakened with test-only fallback beans
```

---

## 16. Discussion points before WF-002

```text
Should the first workflow target be only TELEMETRY_READING?
Should workflow create validation state in telemetry, or should telemetry expose state changes only after workflow decision?
Should workflow include correction value payloads, or only request correction and leave correction to telemetry?
Should audit be implemented immediately or only WorkflowAuditEventPort defined now?
Should actor authority be enforced now or after identity/organization read ports are hardened?
Should workflow support parallel approval steps in v1, or only sequential steps?
Should task assignment be to actor, role, organization unit, or all three?
```

Recommended v1 decisions:

```text
First target: TELEMETRY_READING only.
Approval model: sequential, not parallel.
Correction model: workflow requests correction; telemetry owns corrected reading value.
Assignment model: actor or organization unit snapshot, with authority lookup port.
Audit: define WorkflowAuditEventPort now; full audit hardening after incidents.
```

---

## 17. Next action

After discussion, start:

```text
WF-002 — chore(workflow): add workflow package skeleton
```
