# Workflow Module Roadmap — Detailed Execution Plan

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
Status       : Detailed draft for discussion
```

---

## 1. Purpose

This document is the executable roadmap for the `workflow` module.

It defines:

```text
module order
module scope
dependency rules
domain model
catalog and multilingual policy
database schema
REST surface
test strategy
task-by-task implementation order
exact files to create
exact files to update
exact files to delete
acceptance criteria
reject criteria
```

Workflow is the next module after telemetry.

Workflow orchestrates validation, approval, rejection, correction request, escalation, delegation, and task assignment processes.

Workflow is not a generic BPM engine.

Workflow is the Hidra operational process layer.

---

## 2. Correct implementation order

The correct dependency-driven order is:

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

Workflow follows telemetry because:

```text
telemetry owns measurement facts
workflow owns actor-driven validation/approval process
planning consumes validated/approved readings
monitoring consumes trusted telemetry and planning state
incidents consume monitoring/alert outcomes
audit hardening follows incidents, but workflow emits audit-ready events from v1
```

---

## 3. Naming standard

Use only:

```text
kernel
identity
dz.sh.hidra
```

Forbidden old aliases are not allowed in files, package names, docs, or roadmap tasks.

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
workflow organization snapshots
workflow comments
workflow due dates
workflow timelines
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

---

## 5. Bounded context decision

Validation is not a standalone module at this stage.

Telemetry owns:

```text
reading fact
reading value
reading quality
reading correction history
reading state
```

Workflow owns:

```text
who reviews the reading
who approves/rejects/corrects/delegates/escalates
workflow task state
decision reason
workflow action history
process timeline
audit-ready event emission
```

Audit hardening is later. Workflow defines `WorkflowAuditEventPort` now.

---

## 6. Dependency rules

Allowed foundation dependencies:

```text
java.*
jakarta.validation.* in API/request layer
org.springframework.* only in API, infrastructure, configuration, and tests
dz.sh.hidra.kernel.*
```

Allowed cross-module integration style:

```text
workflow application defines outbound ports
workflow infrastructure implements adapters
workflow references external modules by IDs and snapshots
```

Allowed outbound ports:

```text
WorkflowTargetLookupPort
WorkflowActorLookupPort
WorkflowAuthorityLookupPort
WorkflowAuditEventPort
```

Forbidden imports:

```text
dz.sh.hidra.modules.telemetry.domain
dz.sh.hidra.modules.telemetry.infrastructure
dz.sh.hidra.modules.topology.domain
dz.sh.hidra.modules.topology.infrastructure
dz.sh.hidra.modules.organization.domain
dz.sh.hidra.modules.identity.domain
dz.sh.hidra.modules.audit.domain
dz.sh.hidra.modules.planning
dz.sh.hidra.modules.monitoring
dz.sh.hidra.modules.incidents
dz.sh.hidra.modules.analytics
```

---

## 7. Package map

Production packages:

```text
src/main/java/dz/sh/hidra/modules/workflow/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/package-info.java
```

Test roots:

```text
src/test/java/dz/sh/hidra/modules/workflow/domain
src/test/java/dz/sh/hidra/modules/workflow/application
src/test/java/dz/sh/hidra/modules/workflow/infrastructure
src/test/java/dz/sh/hidra/modules/workflow/api
src/test/java/dz/sh/hidra/modules/workflow/WorkflowApplicationBootSmokeTest.java
```

---

## 8. Domain model summary

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
WorkflowAssignment
WorkflowAction
WorkflowDelegation
WorkflowEscalationRule
WorkflowComment
WorkflowStateHistory
WorkflowTypeCatalog
WorkflowTypeTranslation
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

Catalog-backed business references:

```text
WorkflowTypeReference
WorkflowReasonReference
WorkflowPriorityReference
WorkflowTargetTypeReference
WorkflowEscalationReasonReference
WorkflowDelegationReasonReference
```

Do not implement business reason/type/priority as Java enums.

---

## 9. Multilingual policy

Direct user-facing names use:

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

French is mandatory for direct localized names.

Reject single-language labels.

---

## 10. Workflow v1 business scenario

First supported target:

```text
TELEMETRY_READING
```

Scenario:

```text
1. Telemetry reading exists.
2. Workflow instance starts for target TELEMETRY_READING.
3. Workflow assigns validation task.
4. Validator claims or receives assignment.
5. Validator approves, rejects, requests correction, delegates, escalates, or comments.
6. Workflow records action, actor snapshot, organization snapshot, reason, comment, and timestamp.
7. Workflow emits audit-ready event through WorkflowAuditEventPort.
8. Telemetry remains owner of reading state/value changes.
```

---

## 11. Database schema summary

Migration:

```text
src/main/resources/db/migration/V023__create_workflow_catalog_and_core_tables.sql
```

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

ER view:

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

---

## 12. REST API summary

Base path:

```text
/api/v1/workflows
```

Controller groups:

```text
WorkflowCatalogController
WorkflowDefinitionController
WorkflowInstanceController
WorkflowTaskController
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

## 13. Global file manifest

### 13.1 All files expected by end of WF-026

```text
src/main/java/dz/sh/hidra/modules/workflow/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowStepId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTransitionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDelegationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowEscalationRuleId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCommentId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCatalogId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCatalogTranslationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCode.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowName.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowLocalizedName.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDescription.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowVersion.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActorReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowOrganizationReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTypeReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowReasonReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowPriorityReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetTypeReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDueDate.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCommentText.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecisionNote.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCorrelationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowEscalationStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTypeCatalog.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTypeTranslation.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAssignment.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDelegation.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowEscalationRule.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowComment.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDefinitionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowTransitionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowAssignmentPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDecisionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDelegationPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowEscalationPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowTargetPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDefinitionDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowInstanceDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowTaskDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDecisionDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowAssignmentDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowEscalationDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ActivateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/DeactivateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowStepCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTransitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CancelWorkflowInstanceCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/AssignWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ClaimWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ApproveWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/RejectWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/RequestWorkflowCorrectionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/DelegateWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/EscalateWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CommentWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowDefinitionQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowDefinitionsQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowInstanceQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowInstancesQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowTimelineQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowTaskQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowTasksQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListMyWorkflowTasksQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowCatalogTypeQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowCatalogTypesQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ResolveWorkflowCatalogTypeQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowDefinitionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowStepDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowAssignmentDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowDelegationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowEscalationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCommentDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTimelineDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCatalogDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCatalogTranslationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTypeReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowReasonReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowPriorityReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTargetReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActorReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowLocalizedNameDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowPageDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ActivateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/DeactivateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowDefinitionsUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowStepUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTransitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CancelWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowInstancesUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowTimelineUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowTasksUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListMyWorkflowTasksUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/AssignWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ClaimWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ApproveWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RejectWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RequestWorkflowCorrectionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/DelegateWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/EscalateWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CommentWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowCatalogTypeUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowCatalogTypesUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ResolveWorkflowCatalogTypeUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowCatalogRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTargetLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActorLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuthorityLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuditEventPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowDefinitionApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowInstanceApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTaskApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowCatalogApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/WorkflowConfiguration.java
src/main/resources/db/migration/V023__create_workflow_catalog_and_core_tables.sql
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTypeCatalogJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTypeTranslationJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAssignmentJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDelegationJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowEscalationRuleJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCommentJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTypeCatalogJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTypeTranslationJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowCatalogRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowDefinitionRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowInstanceRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowTaskRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/WorkflowTelemetryTargetLookupAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowLocalizedNameRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowTypeReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowTargetReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowActorReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowDefinitionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowStepRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTransitionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CancelWorkflowInstanceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/AssignWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ClaimWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ApproveWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RejectWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RequestWorkflowCorrectionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/DelegateWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/EscalateWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CommentWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowLocalizedNameResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTypeReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowReasonReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowPriorityReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTargetReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActorReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowDefinitionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowStepResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTimelineResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowCatalogResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowPageResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapper.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowCatalogController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowDefinitionController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowInstanceController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTaskController.java
src/test/java/dz/sh/hidra/modules/workflow/domain/support/WorkflowDomainTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowValueObjectTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowCatalogDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinitionDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstanceAndTaskDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDomainPolicyTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDomainServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/support/WorkflowApplicationServiceTestSupport.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowCatalogApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowDefinitionApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowInstanceApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTaskApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/support/WorkflowPersistenceTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapperTest.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowJpaRepositoryTest.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowPersistenceAdapterTest.java
src/test/java/dz/sh/hidra/modules/workflow/api/rest/support/WorkflowRestTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapperTest.java
src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowRestControllerTest.java
src/test/java/dz/sh/hidra/modules/workflow/WorkflowApplicationBootSmokeTest.java
docs/roadmap/workflow_validation_checklist.md
```

### 13.2 Existing files to update

```text
docs/roadmap/workflow.md
```

### 13.3 Files to delete

```text
None expected in the clean path.
Delete only incorrectly generated workflow files that violate this roadmap, especially:
- files under shared/common/core/utils/helper/helpers/misc
- workflow files containing old module aliases
- workflow business taxonomy enum files
- workflow classes importing forbidden cross-module domain/infrastructure packages
```

---

## 14. Task-by-task roadmap with file manifests

### WF-001 — docs(workflow): prepare workflow module roadmap

**Scope**

```text
Documentation only. This expanded roadmap is the executable module plan.
```

**Files to create**

```text
docs/roadmap/workflow.md
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-002 — chore(workflow): add workflow package skeleton

**Scope**

```text
Create only production package-info.java files. No tests, no classes, no behavior.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/package-info.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-003 — feat(workflow): add workflow domain value objects

**Scope**

```text
Add identifiers, localized names, references, status enums, and decision value objects. No aggregates.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowStepId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTransitionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDelegationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowEscalationRuleId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCommentId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCatalogId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCatalogTranslationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCode.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowName.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowLocalizedName.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDescription.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowVersion.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActorReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowOrganizationReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTypeReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowReasonReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowPriorityReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetTypeReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDueDate.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCommentText.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecisionNote.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCorrelationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowEscalationStatus.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-004 — feat(workflow): add workflow catalog domain models

**Scope**

```text
Add catalog-backed workflow type/reason/priority metadata models.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTypeCatalog.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTypeTranslation.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-005 — feat(workflow): add workflow core domain models

**Scope**

```text
Add aggregates/entities only. No application services, persistence, or REST.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAssignment.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDelegation.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowEscalationRule.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowComment.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-006 — feat(workflow): add workflow domain policies

**Scope**

```text
Add policies for definitions, transitions, decisions, assignments, delegation, escalation, and targets.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDefinitionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowTransitionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowAssignmentPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDecisionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDelegationPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowEscalationPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowTargetPolicy.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-007 — feat(workflow): add workflow domain services

**Scope**

```text
Add orchestration domain services that apply policies and produce next state.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDefinitionDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowInstanceDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowTaskDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDecisionDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowAssignmentDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowEscalationDomainService.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-008 — feat(workflow): add workflow application commands and queries

**Scope**

```text
Add command/query records only. No services.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ActivateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/DeactivateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowStepCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTransitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CancelWorkflowInstanceCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/AssignWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ClaimWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ApproveWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/RejectWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/RequestWorkflowCorrectionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/DelegateWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/EscalateWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CommentWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowDefinitionQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowDefinitionsQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowInstanceQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowInstancesQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowTimelineQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowTaskQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowTasksQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListMyWorkflowTasksQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowCatalogTypeQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowCatalogTypesQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ResolveWorkflowCatalogTypeQuery.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-009 — feat(workflow): add workflow application DTOs

**Scope**

```text
Add application DTOs for definitions, instances, tasks, actions, timeline, catalogs, and references.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowDefinitionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowStepDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowAssignmentDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowDelegationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowEscalationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCommentDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTimelineDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCatalogDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCatalogTranslationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTypeReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowReasonReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowPriorityReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTargetReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActorReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowLocalizedNameDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowPageDto.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-010 — feat(workflow): add workflow application ports

**Scope**

```text
Add inbound use-case ports and outbound repository/lookup/audit ports.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ActivateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/DeactivateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowDefinitionsUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowStepUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTransitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CancelWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowInstancesUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowTimelineUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowTasksUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListMyWorkflowTasksUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/AssignWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ClaimWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ApproveWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RejectWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RequestWorkflowCorrectionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/DelegateWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/EscalateWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CommentWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowCatalogTypeUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowCatalogTypesUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ResolveWorkflowCatalogTypeUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowCatalogRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTargetLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActorLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuthorityLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuditEventPort.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-011 — feat(workflow): add workflow application services

**Scope**

```text
Implement application service use cases using domain services and ports.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowDefinitionApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowInstanceApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTaskApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowCatalogApplicationService.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-012 — feat(workflow): add workflow infrastructure configuration

**Scope**

```text
Register workflow beans explicitly. Avoid ambiguous Spring beans with @Qualifier when needed.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/WorkflowConfiguration.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-013 — db(workflow): add workflow catalog and core tables migration

**Scope**

```text
Create Flyway migration. Adjust V023 only if repository migration sequence advanced.
```

**Files to create**

```text
src/main/resources/db/migration/V023__create_workflow_catalog_and_core_tables.sql
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-014 — feat(workflow): add workflow persistence entities and repositories

**Scope**

```text
Add JPA entities and Spring Data repositories. No adapters.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTypeCatalogJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTypeTranslationJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAssignmentJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDelegationJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowEscalationRuleJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCommentJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTypeCatalogJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTypeTranslationJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-015 — feat(workflow): add workflow persistence mappers and adapters

**Scope**

```text
Add mapper and repository port adapter implementations. Register mapper as bean.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowCatalogRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowDefinitionRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowInstanceRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowTaskRepositoryAdapter.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-016 — feat(workflow): add workflow telemetry target lookup adapter

**Scope**

```text
Validate TELEMETRY_READING target references through telemetry application ports only.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/WorkflowTelemetryTargetLookupAdapter.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-017 — feat(workflow): add workflow REST request DTOs

**Scope**

```text
Add request DTOs with Jakarta validation and OpenAPI schemas.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowLocalizedNameRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowTypeReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowTargetReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowActorReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowDefinitionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowStepRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTransitionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CancelWorkflowInstanceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/AssignWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ClaimWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ApproveWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RejectWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RequestWorkflowCorrectionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/DelegateWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/EscalateWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CommentWorkflowTaskRequest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-018 — feat(workflow): add workflow REST response DTOs

**Scope**

```text
Add response DTOs with localized labels and neutral references.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowLocalizedNameResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTypeReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowReasonReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowPriorityReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTargetReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActorReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowDefinitionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowStepResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTimelineResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowCatalogResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowPageResponse.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-019 — feat(workflow): add workflow REST mapper

**Scope**

```text
Map REST requests to commands/queries and application DTOs to responses.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapper.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-020 — feat(workflow): add workflow REST controllers

**Scope**

```text
Thin controllers only. Depend on inbound ports and REST mapper.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowCatalogController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowDefinitionController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowInstanceController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTaskController.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-021 — test(workflow): add workflow domain tests

**Scope**

```text
Test value objects, catalog, aggregates, policies, and domain services.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/domain/support/WorkflowDomainTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowValueObjectTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowCatalogDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinitionDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstanceAndTaskDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDomainPolicyTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDomainServiceTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-022 — test(workflow): add workflow application service tests

**Scope**

```text
Use in-memory fake ports. No Spring context.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/application/support/WorkflowApplicationServiceTestSupport.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowCatalogApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowDefinitionApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowInstanceApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTaskApplicationServiceTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-023 — test(workflow): add workflow persistence tests

**Scope**

```text
Test mapper, repository, and adapters.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/support/WorkflowPersistenceTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapperTest.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowJpaRepositoryTest.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowPersistenceAdapterTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-024 — test(workflow): add workflow REST mapper and controller tests

**Scope**

```text
Unit-style REST mapper/controller tests. No persistence.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/api/rest/support/WorkflowRestTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapperTest.java
src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowRestControllerTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-025 — test(workflow): add workflow application boot smoke test

**Scope**

```text
Verify real HidraApplication context loads with workflow beans.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/WorkflowApplicationBootSmokeTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```

### WF-026 — docs(workflow): finalize workflow validation checklist

**Scope**

```text
Finalize checklist and mark roadmap status.
```

**Files to create**

```text
docs/roadmap/workflow_validation_checklist.md
docs/roadmap/workflow.md
```

**Files to update**

```text
docs/roadmap/workflow.md
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```


---

## 15. Required validation commands

After production tasks:

```bash
mvn -q -DskipTests compile
```

After domain tests:

```bash
mvn -q -DskipITs test -Dtest='Workflow*DomainTest,Workflow*PolicyTest,Workflow*ServiceTest,WorkflowValueObjectTest'
```

After application tests:

```bash
mvn -q -DskipITs test -Dtest='Workflow*ApplicationServiceTest'
```

After persistence tests:

```bash
mvn -q -DskipITs test -Dtest='WorkflowPersistenceMapperTest,WorkflowJpaRepositoryTest,WorkflowPersistenceAdapterTest'
```

After REST tests:

```bash
mvn -q -DskipITs test -Dtest='WorkflowRestMapperTest,WorkflowRestControllerTest'
```

After boot smoke:

```bash
mvn -q -DskipITs test -Dtest='WorkflowApplicationBootSmokeTest'
```

Final baseline:

```bash
mvn -q clean compile
mvn -q test
```

---

## 16. Architecture guardrails

Reject if any workflow production file contains:

```text
dz.sh.hidra.modules.telemetry.domain
dz.sh.hidra.modules.telemetry.infrastructure
dz.sh.hidra.modules.topology.domain
dz.sh.hidra.modules.topology.infrastructure
dz.sh.hidra.modules.identity.domain
dz.sh.hidra.modules.organization.domain
```

Reject if any workflow file path contains:

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

Reject if business classifications are implemented as enums:

```text
WorkflowType
WorkflowReasonType
WorkflowPriorityType
WorkflowTargetType
DelegationReasonType
EscalationReasonType
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

---

## 17. Discussion points before WF-002

Decide before implementation:

```text
First target remains TELEMETRY_READING only?
Sequential approvals only in v1?
Should tasks assign to actor, organization unit, role snapshot, or all three?
Should workflow write telemetry state directly or emit decision result for telemetry service to apply?
Should correction request include proposed value or only reason/comment?
Should WorkflowAuditEventPort be no-op adapter until audit hardening?
```

Recommended v1 decisions:

```text
Target: TELEMETRY_READING only.
Approval: sequential.
Assignment: actor or organization unit snapshot.
Correction: workflow requests correction; telemetry owns corrected reading value.
Audit: define outbound event port now; full audit hardening after incidents.
Telemetry state: telemetry application service applies final reading state after workflow decision.
```

---

## 18. Next action

After this roadmap is accepted:

```text
WF-002 — chore(workflow): add workflow package skeleton
```
