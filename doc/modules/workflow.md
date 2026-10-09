# Workflow Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.workflow`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns workflow definitions, steps/transitions, workflow instances/tasks/actions and workflow state history.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [WorkflowAction](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java)
- [WorkflowDefinition](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java)
- [WorkflowInstance](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java)
- [WorkflowStateHistory](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java)
- [WorkflowStep](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java)
- [WorkflowStepAssignmentRule](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java)
- [WorkflowTask](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java)
- [WorkflowTransition](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java)

Domain policies:

- [WorkflowBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowBoundaryPolicy.java)

Domain services:

- [WorkflowDecisionGuard](../../src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDecisionGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateWorkflowTaskUseCase](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java)
- [ExecuteWorkflowTargetTransitionUseCase](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTargetTransitionUseCase.java)
- [ExecuteWorkflowTransitionUseCase](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java)
- [RecordWorkflowActionUseCase](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java)
- [StartWorkflowInstanceUseCase](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java)
- [WorkflowQueryUseCase](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java)

Current application services:

- [AlarmSuppressionWorkflowContractAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapter.java)
- [AuditWorkflowReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryService.java)
- [CustodyTransferTicketWorkflowReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/CustodyTransferTicketWorkflowReferenceQueryService.java)
- [DocumentsApprovalReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryService.java)
- [HseWorkflowReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/HseWorkflowReferenceQueryService.java)
- [IncidentWorkflowQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/IncidentWorkflowQueryService.java)
- [IntegrityAssessmentWorkflowReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/IntegrityAssessmentWorkflowReferenceQueryService.java)
- [IntegrityCaseWorkflowReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/IntegrityCaseWorkflowReferenceQueryService.java)
- [MaintenanceWorkOrderWorkflowReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java)
- [OrganizationResponsibilityWorkflowContractAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/OrganizationResponsibilityWorkflowContractAdapter.java)
- [PlanningWorkflowContractAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/PlanningWorkflowContractAdapter.java)
- [ReportingWorkflowApprovalQueryService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java)
- [RiskAssessmentApprovalService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalService.java)
- [WorkflowApplicationService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java)
- [WorkflowExecutionOwnership](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java)
- [WorkflowTargetTransitionApplicationService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java)
- [WorkflowTransitionApplicationService](../../src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java)

Current API/controller classes:

- [SpringWorkflowController](../../src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/SpringWorkflowController.java)
- [WorkflowController](../../src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowController.java)
- [WorkflowQueryController](../../src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowQueryController.java)
- [WorkflowTransitionController](../../src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **17** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [WorkflowActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java)
- [WorkflowAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAssignmentJpaEntity.java)
- [WorkflowAuditOutboxReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAuditOutboxReferenceJpaEntity.java)
- [WorkflowCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCatalogEntryJpaEntity.java)
- [WorkflowCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCatalogTranslationJpaEntity.java)
- [WorkflowCommentJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCommentJpaEntity.java)
- [WorkflowDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java)
- [WorkflowDefinitionTargetBindingJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java)
- [WorkflowDelegationJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDelegationJpaEntity.java)
- [WorkflowEscalationRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowEscalationRuleJpaEntity.java)
- [WorkflowInstanceJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java)
- [WorkflowSlaPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowSlaPolicyJpaEntity.java)
- [WorkflowStateHistoryJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java)
- [WorkflowStepAssignmentRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java)
- [WorkflowStepJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java)
- [WorkflowTaskJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java)
- [WorkflowTransitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaWorkflowActionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java)
- [JpaWorkflowConfigurationAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowConfigurationAdapter.java)
- [JpaWorkflowDefinitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java)
- [JpaWorkflowInstanceRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java)
- [JpaWorkflowStateHistoryRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java)
- [JpaWorkflowStepAssignmentRuleRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java)
- [JpaWorkflowStepRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java)
- [JpaWorkflowTaskRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java)
- [JpaWorkflowTransitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [AlarmSuppressionWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/AlarmSuppressionWorkflowContract.java)
- [MaintenanceWorkOrderWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/MaintenanceWorkOrderWorkflowReferenceContract.java)
- [AuditWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/AuditWorkflowReferenceContract.java)
- [CustodyTransferTicketWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java)
- [DocumentsApprovalReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/documents/DocumentsApprovalReferenceContract.java)
- [HseWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/hse/HseWorkflowReferenceContract.java)
- [IncidentWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/incident/IncidentWorkflowContract.java)
- [IntegrityAssessmentWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityAssessmentWorkflowReferenceContract.java)
- [IntegrityCaseWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityCaseWorkflowReferenceContract.java)
- [OrganizationResponsibilityWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/organization/OrganizationResponsibilityWorkflowContract.java)
- [PlanningWorkflowContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java)
- [ReportingWorkflowApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java)
- [RiskAssessmentApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/RiskAssessmentApprovalContract.java)
- [WorkflowOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java)

Imported scalar contracts supplied by collaborating owners:

- [RiskActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java)
- [WorkflowActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowActorContract.java)
- [WorkflowOrganizationContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/workflow/WorkflowOrganizationContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [WorkflowActionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java)
- [WorkflowAuditEventPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuditEventPort.java)
- [WorkflowConfigurationPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowConfigurationPort.java)
- [WorkflowDefinitionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java)
- [WorkflowEligibilityLookupPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java)
- [WorkflowIdentitySnapshotPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowIdentitySnapshotPort.java)
- [WorkflowInstanceRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java)
- [WorkflowNotificationRequestPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowNotificationRequestPort.java)
- [WorkflowOrganizationSnapshotPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowOrganizationSnapshotPort.java)
- [WorkflowStateHistoryRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java)
- [WorkflowStepAssignmentRuleRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java)
- [WorkflowStepRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java)
- [WorkflowTargetMutationPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTargetMutationPort.java)
- [WorkflowTaskRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java)
- [WorkflowTransitionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Workflow owns process definitions, tasks and append-only action/history evidence. Actor and target owner contracts, active definition/version/binding and supported transition configuration govern execution; caller snapshots or permissions cannot manufacture authority.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| WorkflowDefinition | [HMSR-002 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowdefinition) |
| WorkflowStep | [HMSR-051 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowstep) |
| WorkflowStepAssignmentRule | [HMSR-052 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowstepassignmentrule) |
| WorkflowInstance | [HMSR-064 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowinstance) |
| WorkflowTransition | [HMSR-073 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowtransition) |
| WorkflowTask | [HMSR-078 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowtask) |
| WorkflowAction | [HMSR-095 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowaction) |
| WorkflowStateHistory | [HMSR-116 reconciled rule](../domain/SEMANTIC_DECISIONS.md#workflow-workflowstatehistory) |
