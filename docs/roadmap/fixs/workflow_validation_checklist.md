# Workflow Module Validation Checklist — WF-026 Final Gate

```text
Roadmap file : docs/roadmap/workflow_validation_checklist.md
Related file : docs/roadmap/workflow.md
Roadmap code : WF
Scope        : Final validation gate for workflow after WF-002 through WF-025
Repository   : HidraAPI
Namespace    : dz.sh.hidra
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-08
Status       : Final validation checklist; documentation only
```

---

## 1. Purpose

This checklist validates the workflow module after the generated WF implementation path.

It is a roadmap and validation artifact only. It must not contain raw compiler output, unresolved stack traces, or build logs. If validation fails, convert the failure into a finding and a corrective task rather than pasting the raw log into this document.

Expected completed range:

```text
WF-002  chore(workflow): add workflow package skeleton
WF-003  feat(workflow): add workflow domain value objects
WF-004  feat(workflow): add workflow catalog domain models
WF-005  feat(workflow): add workflow core domain models
WF-006  feat(workflow): add workflow domain policies
WF-007  feat(workflow): add workflow domain services
WF-008  feat(workflow): add workflow application commands and queries
WF-009  feat(workflow): add workflow application DTOs
WF-010  feat(workflow): add workflow application ports
WF-011  feat(workflow): add workflow application services
WF-012  feat(workflow): add workflow infrastructure configuration
WF-013  db(workflow): add workflow catalog and core tables migration
WF-014  feat(workflow): add workflow persistence entities and repositories
WF-015  feat(workflow): add workflow persistence mappers and adapters
WF-016  feat(workflow): add workflow telemetry target lookup adapter
WF-017  feat(workflow): add workflow REST request DTOs
WF-018  feat(workflow): add workflow REST response DTOs
WF-019  feat(workflow): add workflow REST mapper
WF-020  feat(workflow): add workflow REST controllers
WF-021  test(workflow): add workflow domain tests
WF-022  test(workflow): add workflow application service tests
WF-023  test(workflow): add workflow persistence tests
WF-024  test(workflow): add workflow REST mapper and controller tests
WF-025  test(workflow): add workflow application boot smoke test
WF-026  chore(workflow): add workflow validation checklist
```

Numbering note:

```text
docs/roadmap/workflow.md may still contain earlier planning numbers for later tasks.
This checklist validates the executed WF-002 through WF-025 baseline and closes it with WF-026.
Do not renumber production code or tests just to match older planning rows.
```

---

## 2. Workflow V1 scope

Workflow owns the approval and decision process around operational records.

Workflow V1 owns:

```text
workflow definitions
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
workflow controlled vocabularies
workflow catalog translations
workflow target references
workflow actor snapshots
workflow organization snapshots
workflow audit event port
```

Workflow V1 target scope:

```text
TELEMETRY_READING only
```

Workflow answers:

```text
Which workflow definition governs an operational decision?
Which step is active?
Who must act?
Which decision was applied?
Which task was claimed, completed, delegated, or escalated?
Which reason/comment/note was captured?
Which audit-ready event should be emitted?
```

Workflow does not own:

```text
telemetry reading state
telemetry reading value
telemetry ingestion
physical topology master data
identity users or credentials
organization hierarchy behavior
planning
monitoring
incidents
audit hardening implementation
integration
analytics/reporting
notification
SCADA control
digital twin
AI forecasting
```

Critical V1 boundary:

```text
Workflow may validate and reference a telemetry reading target.
Workflow must not directly update telemetry reading state or value.
Telemetry remains the owner of reading state, value, and ingestion lifecycle.
```

---

## 3. Expected files

Roadmap and documentation:

```text
docs/roadmap/workflow.md
docs/roadmap/workflow_validation_checklist.md
```

Workflow production source roots:

```text
src/main/java/dz/sh/hidra/modules/workflow
src/main/java/dz/sh/hidra/modules/workflow/domain
src/main/java/dz/sh/hidra/modules/workflow/application
src/main/java/dz/sh/hidra/modules/workflow/infrastructure
src/main/java/dz/sh/hidra/modules/workflow/api
```

Workflow test source roots:

```text
src/test/java/dz/sh/hidra/modules/workflow
src/test/java/dz/sh/hidra/modules/workflow/domain
src/test/java/dz/sh/hidra/modules/workflow/application
src/test/java/dz/sh/hidra/modules/workflow/infrastructure
src/test/java/dz/sh/hidra/modules/workflow/api
```

Workflow migration:

```text
src/main/resources/db/migration/V023__create_workflow_catalog_and_core_tables.sql
```

If the repository later splits WF-013 into multiple migrations, update this checklist with the exact migration filenames.

---

## 4. Expected package structure

Allowed workflow packages:

```text
dz.sh.hidra.modules.workflow
dz.sh.hidra.modules.workflow.domain
dz.sh.hidra.modules.workflow.domain.model
dz.sh.hidra.modules.workflow.domain.value
dz.sh.hidra.modules.workflow.domain.policy
dz.sh.hidra.modules.workflow.domain.service
dz.sh.hidra.modules.workflow.application
dz.sh.hidra.modules.workflow.application.command
dz.sh.hidra.modules.workflow.application.query
dz.sh.hidra.modules.workflow.application.dto
dz.sh.hidra.modules.workflow.application.port.in
dz.sh.hidra.modules.workflow.application.port.out
dz.sh.hidra.modules.workflow.application.service
dz.sh.hidra.modules.workflow.infrastructure
dz.sh.hidra.modules.workflow.infrastructure.configuration
dz.sh.hidra.modules.workflow.infrastructure.persistence
dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper
dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
dz.sh.hidra.modules.workflow.infrastructure.telemetry
dz.sh.hidra.modules.workflow.api
dz.sh.hidra.modules.workflow.api.rest
dz.sh.hidra.modules.workflow.api.rest.request
dz.sh.hidra.modules.workflow.api.rest.response
dz.sh.hidra.modules.workflow.api.rest.mapper
dz.sh.hidra.modules.workflow.api.rest.controller
```

Forbidden package names:

```text
shared
sharedkernel
common
core
utils
helper
helpers
misc
identityaccess
bridge
legacy
deprecated
compatibility
```

Boundary rules:

```text
workflow domain must not import telemetry, topology, identity, organization, planning, monitoring, incidents, audit, integration, analytics, reporting, or notification modules
workflow application must not import telemetry infrastructure, telemetry persistence, REST controllers, or JPA entities
workflow infrastructure telemetry adapter may depend on telemetry application inbound ports only
workflow REST controllers must depend on application inbound ports and WorkflowRestMapper only
workflow persistence adapters must depend on workflow application outbound ports, workflow domain, workflow JPA repositories, and WorkflowPersistenceMapper
workflow must not write telemetry state directly
```

Allowed cross-module telemetry integration:

```text
dz.sh.hidra.modules.workflow.infrastructure.telemetry.WorkflowTelemetryTargetLookupAdapter
```

That adapter may call telemetry application contracts only:

```text
dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryReadingUseCase
dz.sh.hidra.modules.telemetry.application.query.GetTelemetryReadingByIdQuery
dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingDto
dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId
```

Forbidden telemetry integration from workflow:

```text
dz.sh.hidra.modules.telemetry.infrastructure.*
dz.sh.hidra.modules.telemetry.infrastructure.persistence.*
dz.sh.hidra.modules.telemetry.api.*
TelemetryReadingJpaEntity
TelemetryReadingJpaRepository
TelemetryReadingPersistenceAdapter
direct SQL updates to telemetry tables
```

---

## 5. Entity, class, enum, and catalog validation

Business taxonomy must be catalog-backed, not Java enum-backed.

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

Forbidden business taxonomy enums:

```text
WorkflowType
WorkflowReasonType
WorkflowPriorityType
WorkflowTargetType
WorkflowDelegationReasonType
WorkflowEscalationReasonType
WorkflowCancellationReasonType
```

Expected catalog/reference value objects:

```text
WorkflowTypeReference
WorkflowTargetTypeReference
WorkflowReasonReference
WorkflowPriorityReference
```

Expected catalog domain models:

```text
WorkflowTypeCatalog
WorkflowTypeTranslation
```

Expected multilingual value object:

```text
WorkflowLocalizedName
```

Expected neutral snapshot/reference value objects:

```text
WorkflowTargetReference
WorkflowActorReference
WorkflowOrganizationReference
WorkflowCorrelationId
```

Validation commands:

```bash
grep -R "enum WorkflowType\|enum WorkflowReasonType\|enum WorkflowPriorityType\|enum WorkflowTargetType" src/main/java/dz/sh/hidra/modules/workflow && exit 1 || true
grep -R "sharedkernel\|identityaccess\|dz.sonatrach" src/main/java/dz/sh/hidra/modules/workflow src/test/java/dz/sh/hidra/modules/workflow && exit 1 || true
```

---

## 6. Domain validation

Expected domain value objects:

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
WorkflowCatalogId
WorkflowCatalogTranslationId
WorkflowCode
WorkflowName
WorkflowLocalizedName
WorkflowDescription
WorkflowVersion
WorkflowTargetReference
WorkflowActorReference
WorkflowOrganizationReference
WorkflowTypeReference
WorkflowReasonReference
WorkflowPriorityReference
WorkflowTargetTypeReference
WorkflowDueDate
WorkflowCommentText
WorkflowDecisionNote
WorkflowCorrelationId
```

Expected domain models:

```text
WorkflowTypeCatalog
WorkflowTypeTranslation
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
```

Expected policies:

```text
WorkflowDefinitionPolicy
WorkflowTransitionPolicy
WorkflowAssignmentPolicy
WorkflowDecisionPolicy
WorkflowDelegationPolicy
WorkflowEscalationPolicy
WorkflowTargetPolicy
```

Expected domain services:

```text
WorkflowDefinitionDomainService
WorkflowInstanceDomainService
WorkflowTaskDomainService
WorkflowDecisionDomainService
WorkflowAssignmentDomainService
WorkflowEscalationDomainService
```

Domain rules to verify:

```text
workflow definitions start as DRAFT
ACTIVE definitions can start workflow instances
definitions cannot activate without valid steps
step code uniqueness is enforced per definition
transition endpoints must belong to the definition
transition reason/comment requirements are enforced
workflow target policy accepts TELEMETRY_READING only in V1
tasks can be claimed once unless state allows reassignment through service rules
closed tasks cannot be claimed again
task completion must use a closed task status
state history is recorded when an instance moves
domain objects remain persistence-free and Spring-free
```

Validation commands:

```bash
grep -R "org.springframework\|jakarta.persistence\|jakarta.validation" src/main/java/dz/sh/hidra/modules/workflow/domain && exit 1 || true
mvn -q -Dtest='dz.sh.hidra.modules.workflow.domain.**.*Test' test
```

---

## 7. Application validation

Expected command/query packages:

```text
src/main/java/dz/sh/hidra/modules/workflow/application/command
src/main/java/dz/sh/hidra/modules/workflow/application/query
```

Expected DTO package:

```text
src/main/java/dz/sh/hidra/modules/workflow/application/dto
```

Expected inbound ports:

```text
CreateWorkflowDefinitionUseCase
ActivateWorkflowDefinitionUseCase
DeactivateWorkflowDefinitionUseCase
GetWorkflowDefinitionUseCase
ListWorkflowDefinitionsUseCase
CreateWorkflowStepUseCase
CreateWorkflowTransitionUseCase
StartWorkflowInstanceUseCase
CancelWorkflowInstanceUseCase
GetWorkflowInstanceUseCase
ListWorkflowInstancesUseCase
GetWorkflowTimelineUseCase
AssignWorkflowTaskUseCase
ClaimWorkflowTaskUseCase
ApproveWorkflowTaskUseCase
RejectWorkflowTaskUseCase
RequestWorkflowCorrectionUseCase
DelegateWorkflowTaskUseCase
EscalateWorkflowTaskUseCase
CommentWorkflowTaskUseCase
GetWorkflowTaskUseCase
ListWorkflowTasksUseCase
ListMyWorkflowTasksUseCase
GetWorkflowCatalogTypeUseCase
ListWorkflowCatalogTypesUseCase
ResolveWorkflowCatalogTypeUseCase
```

Expected outbound ports:

```text
WorkflowDefinitionRepositoryPort
WorkflowInstanceRepositoryPort
WorkflowTaskRepositoryPort
WorkflowCatalogRepositoryPort
WorkflowTargetLookupPort
WorkflowActorLookupPort
WorkflowAuthorityLookupPort
WorkflowAuditEventPort
```

Expected application services:

```text
WorkflowCatalogApplicationService
WorkflowDefinitionApplicationService
WorkflowInstanceApplicationService
WorkflowTaskApplicationService
```

Application rules to verify:

```text
application services implement inbound ports
application services use outbound ports, not JPA repositories
application services use domain services for business state transitions
application services return DTOs, not domain models, to API layer
application services do not import REST requests/responses
workflow start resolves and validates target before persisting instance
workflow start records workflow-start audit event through WorkflowAuditEventPort
task decisions record workflow-action audit event
task changes record workflow-task-changed audit event
```

Validation commands:

```bash
grep -R "jakarta.persistence\|org.springframework.web\|RestController\|RequestMapping" src/main/java/dz/sh/hidra/modules/workflow/application && exit 1 || true
mvn -q -Dtest='dz.sh.hidra.modules.workflow.application.service.**.*Test' test
```

---

## 8. Persistence and database validation

Expected migration:

```text
src/main/resources/db/migration/V023__create_workflow_catalog_and_core_tables.sql
```

Expected tables:

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

Expected JPA entity package:

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity
```

Expected repository package:

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository
```

Expected mapper and adapter package:

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter
```

Persistence rules to verify:

```text
JPA entities are infrastructure-only
JPA entities are not exposed to domain, application, or REST
repositories are Spring Data interfaces only
persistence adapters implement workflow outbound repository ports
persistence mapper converts domain models to JPA entities and back
workflow persistence uses neutral reference/snapshot fields for actors, organizations, and targets
workflow persistence does not create foreign-key dependencies into telemetry, identity, organization, or topology tables
```

Important attention points:

```text
WorkflowPersistenceMapper.toEntity(WorkflowTask) must match WorkflowTaskJpaEntity constructor arity exactly.
WorkflowRestMapper must import WorkflowStepDto.
Run full compile after merging all WF artifacts because local isolated ZIP validation cannot replace repository-wide compilation.
```

Validation commands:

```bash
grep -R "dz.sh.hidra.modules.telemetry.infrastructure\|dz.sh.hidra.modules.telemetry.api" src/main/java/dz/sh/hidra/modules/workflow && exit 1 || true
grep -R "Workflow.*JpaEntity" src/main/java/dz/sh/hidra/modules/workflow/domain src/main/java/dz/sh/hidra/modules/workflow/application src/main/java/dz/sh/hidra/modules/workflow/api && exit 1 || true
mvn -q -Dtest='dz.sh.hidra.modules.workflow.infrastructure.persistence.**.*Test' test
```

---

## 9. Telemetry target lookup validation

Expected adapter:

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/WorkflowTelemetryTargetLookupAdapter.java
```

Adapter validation:

```text
implements WorkflowTargetLookupPort
supports TELEMETRY_READING targets only
uses GetTelemetryReadingUseCase
uses GetTelemetryReadingByIdQuery
uses TelemetryReadingId
does not import telemetry infrastructure
does not import telemetry persistence
does not import telemetry REST
does not write telemetry state
```

Validation command:

```bash
grep -R "dz.sh.hidra.modules.telemetry.infrastructure\|dz.sh.hidra.modules.telemetry.api" src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry && exit 1 || true
```

---

## 10. REST/API validation

Expected request DTO package:

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request
```

Expected response DTO package:

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response
```

Expected mapper:

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapper.java
```

Expected controllers:

```text
WorkflowCatalogController
WorkflowDefinitionController
WorkflowInstanceController
WorkflowTaskController
```

Expected base path:

```text
/api/v1/workflow
```

Expected endpoint families:

```text
/catalog-types
/definitions
/definitions/{definitionId}/steps
/definitions/{definitionId}/transitions
/instances
/instances/{instanceId}
/instances/{instanceId}/timeline
/tasks
/tasks/my
/tasks/{taskId}
/tasks/{taskId}/assign
/tasks/{taskId}/claim
/instances/{instanceId}/tasks/{taskId}/approve
/instances/{instanceId}/tasks/{taskId}/reject
/instances/{instanceId}/tasks/{taskId}/correction-request
/tasks/{taskId}/delegate
/tasks/{taskId}/escalate
/instances/{instanceId}/tasks/{taskId}/comments
/instances/{instanceId}/comments
```

REST rules to verify:

```text
request DTOs use jakarta.validation annotations only at the API boundary
response DTOs do not use validation annotations
REST mapper converts request DTOs to application commands/queries
REST mapper converts application DTOs to response DTOs
controllers are thin and delegate business work to inbound ports
controllers do not use repositories or persistence mappers
controllers do not directly construct domain aggregates except through mapper-created command/query value objects
controllers use @Operation summaries
controllers use @Tag(name = "Workflow")
```

Validation commands:

```bash
grep -R "JpaRepository\|EntityManager\|WorkflowPersistenceMapper" src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller && exit 1 || true
grep -R "jakarta.validation" src/main/java/dz/sh/hidra/modules/workflow/api/rest/response && exit 1 || true
mvn -q -Dtest='dz.sh.hidra.modules.workflow.api.rest.**.*Test' test
```

---

## 11. Spring wiring validation

Expected configuration files:

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/WorkflowConfiguration.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/WorkflowMapperConfiguration.java
```

Expected boot smoke test:

```text
src/test/java/dz/sh/hidra/modules/workflow/WorkflowApplicationBootSmokeTest.java
```

Spring wiring rules:

```text
WorkflowConfiguration wires policies, domain services, application services, and fallback audit port
WorkflowMapperConfiguration exposes WorkflowRestMapper and WorkflowPersistenceMapper beans
repository adapters are Spring components/repositories
telemetry target lookup adapter is a Spring component
controllers are registered as Spring REST controllers
boot smoke test must use real HidraApplication context
boot smoke test must use PostgreSQL Testcontainers
boot smoke test must not disable Flyway or JPA just to pass
```

Validation commands:

```bash
mvn -q test -Dtest=WorkflowApplicationBootSmokeTest
```

Docker note:

```text
WorkflowApplicationBootSmokeTest requires a Docker-compatible runtime because it uses PostgreSQL Testcontainers.
If Docker is unavailable, mark the boot-smoke result as not run and do not claim final boot validation passed.
```

---

## 12. Test validation

Expected test families:

```text
domain tests
application service tests
persistence mapper/adapter tests
REST mapper/controller tests
application boot smoke test
```

Expected test roots:

```text
src/test/java/dz/sh/hidra/modules/workflow/domain
src/test/java/dz/sh/hidra/modules/workflow/application/service
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence
src/test/java/dz/sh/hidra/modules/workflow/api/rest
src/test/java/dz/sh/hidra/modules/workflow/WorkflowApplicationBootSmokeTest.java
```

Test commands:

```bash
mvn -q -Dtest='dz.sh.hidra.modules.workflow.domain.**.*Test' test
mvn -q -Dtest='dz.sh.hidra.modules.workflow.application.service.**.*Test' test
mvn -q -Dtest='dz.sh.hidra.modules.workflow.infrastructure.persistence.**.*Test' test
mvn -q -Dtest='dz.sh.hidra.modules.workflow.api.rest.**.*Test' test
mvn -q test -Dtest=WorkflowApplicationBootSmokeTest
```

Repository-wide commands:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

---

## 13. Architecture guardrails

Forbidden imports in workflow domain:

```text
org.springframework.*
jakarta.persistence.*
jakarta.validation.*
dz.sh.hidra.modules.telemetry.*
dz.sh.hidra.modules.topology.*
dz.sh.hidra.modules.identity.*
dz.sh.hidra.modules.organization.*
dz.sh.hidra.modules.audit.*
```

Forbidden imports in workflow application:

```text
org.springframework.web.*
jakarta.persistence.*
dz.sh.hidra.modules.workflow.api.*
dz.sh.hidra.modules.workflow.infrastructure.*
```

Allowed import exception in workflow infrastructure telemetry:

```text
dz.sh.hidra.modules.telemetry.application.*
dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId
```

Forbidden dependencies from workflow to future modules:

```text
planning
monitoring
incidents
integration
analytics
reporting
notification
```

Architecture search commands:

```bash
grep -R "dz.sh.hidra.modules.planning\|dz.sh.hidra.modules.monitoring\|dz.sh.hidra.modules.incidents\|dz.sh.hidra.modules.integration\|dz.sh.hidra.modules.analytics\|dz.sh.hidra.modules.reporting\|dz.sh.hidra.modules.notification" src/main/java/dz/sh/hidra/modules/workflow src/test/java/dz/sh/hidra/modules/workflow && exit 1 || true
grep -R "sharedkernel\|identityaccess\|dz.sonatrach" src/main/java/dz/sh/hidra/modules/workflow src/test/java/dz/sh/hidra/modules/workflow && exit 1 || true
```

---

## 14. Acceptance criteria

Workflow is accepted only when all are true:

```text
all WF-002 through WF-025 artifacts are merged
docs/roadmap/workflow_validation_checklist.md exists
mvn -q -DskipTests compile passes
mvn -q test passes
mvn -q clean verify passes
WorkflowApplicationBootSmokeTest passes with PostgreSQL Testcontainers
workflow domain imports no Spring/JPA/validation/cross-module implementation classes
workflow application imports no REST/JPA/infrastructure classes
workflow infrastructure telemetry adapter imports no telemetry infrastructure/persistence/API classes
workflow REST controllers are thin and delegate through inbound ports and WorkflowRestMapper
workflow has no direct telemetry state write path
workflow V1 remains TELEMETRY_READING-only
```

---

## 15. Reject criteria

Reject the workflow baseline if any of these are true:

```text
workflow domain imports Spring, JPA, validation, telemetry, topology, identity, organization, or audit implementation
workflow application imports REST controllers, request/response DTOs, JPA entities, or JPA repositories
workflow infrastructure writes telemetry reading state directly
workflow uses Java enums for business taxonomy that should be catalog-backed
workflow REST controllers call repositories directly
workflow boot smoke test disables Flyway/JPA/security just to pass
migrations are missing workflow tables
WorkflowApplicationBootSmokeTest is skipped or not run but final validation is claimed as passed
mvn -q -DskipTests compile fails
mvn -q test fails
```

---

## 16. Known attention points before final merge

These points must be explicitly checked after all local ZIPs are copied into the real repository workspace:

```text
1. WorkflowRestMapper must include the WorkflowStepDto import.
2. WorkflowPersistenceMapper.toEntity(WorkflowTask) must match WorkflowTaskJpaEntity constructor parameters.
3. WorkflowMapperConfiguration must not conflict with any existing workflow mapper bean methods in WorkflowConfiguration.
4. WorkflowTelemetryTargetLookupAdapter must be present so WorkflowTargetLookupPort is satisfied.
5. The no-op WorkflowAuditEventPort is acceptable for V1 readiness but must be replaced by audit hardening later.
6. Workflow boot smoke test requires Docker/Testcontainers.
7. Full Maven validation has not passed until it is run in the merged repository workspace.
```

If any attention point fails, create a correction task before declaring workflow complete.

---

## 17. Final validation record

Fill this table after running validation in the real repository workspace:

| Check | Command or evidence | Result | Notes |
|---|---|---:|---|
| File exists | `test -f docs/roadmap/workflow_validation_checklist.md` | Pending |  |
| Compile | `mvn -q -DskipTests compile` | Pending |  |
| Domain tests | `mvn -q -Dtest='dz.sh.hidra.modules.workflow.domain.**.*Test' test` | Pending |  |
| Application service tests | `mvn -q -Dtest='dz.sh.hidra.modules.workflow.application.service.**.*Test' test` | Pending |  |
| Persistence tests | `mvn -q -Dtest='dz.sh.hidra.modules.workflow.infrastructure.persistence.**.*Test' test` | Pending |  |
| REST tests | `mvn -q -Dtest='dz.sh.hidra.modules.workflow.api.rest.**.*Test' test` | Pending |  |
| Boot smoke | `mvn -q test -Dtest=WorkflowApplicationBootSmokeTest` | Pending | Docker required |
| Full tests | `mvn -q test` | Pending |  |
| Full verify | `mvn -q clean verify` | Pending |  |

---

## 18. Next gate

After WF-026 is validated and the workflow module is accepted, the next implementation lane can begin only if the V1 baseline remains green.

Recommended next gate:

```text
Planning module roadmap and package skeleton, only after workflow validation passes.
```
