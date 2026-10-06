# Workflow Module

## Status

CURRENT — canonical HPR-P2-004 module document.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

Package root: `dz.sh.hidra.modules.workflow`

This document describes source-visible current state. It does not promote legacy `docs/**` material to authority and does not substitute for the canonical API/database contracts created by later P2 tasks.

## Responsibility

Owns workflow definitions, steps/transitions, workflow instances/tasks/actions and workflow state history.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types:

- `WorkflowAction`
- `WorkflowDefinition`
- `WorkflowInstance`
- `WorkflowStateHistory`
- `WorkflowStep`
- `WorkflowStepAssignmentRule`
- `WorkflowTask`
- `WorkflowTransition`

Domain policies: `WorkflowBoundaryPolicy`

Domain services: `WorkflowDecisionGuard`

Canonical semantic context: `../domain/DOMAIN_OWNERSHIP.md`.

## Application and API Surface

Current inbound/use-case ports:

- `CreateWorkflowTaskUseCase`
- `ExecuteWorkflowTargetTransitionUseCase`
- `ExecuteWorkflowTransitionUseCase`
- `RecordWorkflowActionUseCase`
- `StartWorkflowInstanceUseCase`
- `WorkflowQueryUseCase`

Current application services:

- `AlarmSuppressionWorkflowContractAdapter`
- `OrganizationResponsibilityWorkflowContractAdapter`
- `PlanningWorkflowContractAdapter`
- `ReportingWorkflowApprovalQueryService`
- `WorkflowApplicationService`
- `WorkflowTargetTransitionApplicationService`
- `WorkflowTransitionApplicationService`

Current API/controller classes:

- `SpringWorkflowController`
- `WorkflowController`
- `WorkflowQueryController`
- `WorkflowTransitionController`

These class inventories establish implemented adapters/use-case surfaces. Exact HTTP paths, request/response schemas, authentication requirements and compatibility semantics are HPR-P2-005 scope.

## Persistence

Current JPA persistence entity count: **17**.

Persistence entities:

- `WorkflowActionJpaEntity`
- `WorkflowAssignmentJpaEntity`
- `WorkflowAuditOutboxReferenceJpaEntity`
- `WorkflowCatalogEntryJpaEntity`
- `WorkflowCatalogTranslationJpaEntity`
- `WorkflowCommentJpaEntity`
- `WorkflowDefinitionJpaEntity`
- `WorkflowDefinitionTargetBindingJpaEntity`
- `WorkflowDelegationJpaEntity`
- `WorkflowEscalationRuleJpaEntity`
- `WorkflowInstanceJpaEntity`
- `WorkflowSlaPolicyJpaEntity`
- `WorkflowStateHistoryJpaEntity`
- `WorkflowStepAssignmentRuleJpaEntity`
- `WorkflowStepJpaEntity`
- `WorkflowTaskJpaEntity`
- `WorkflowTransitionJpaEntity`

Current persistence repository-adapter classes:

- `JpaWorkflowActionRepositoryAdapter`
- `JpaWorkflowDefinitionRepositoryAdapter`
- `JpaWorkflowInstanceRepositoryAdapter`
- `JpaWorkflowStateHistoryRepositoryAdapter`
- `JpaWorkflowStepAssignmentRuleRepositoryAdapter`
- `JpaWorkflowStepRepositoryAdapter`
- `JpaWorkflowTaskRepositoryAdapter`
- `JpaWorkflowTransitionRepositoryAdapter`

Table/schema ownership and the generated data dictionary are HPR-P2-006 scope; class presence is not used here to invent database constraints or retention policy.

## Cross-Module Boundary

Exported contracts owned by this module:

- consumer `alarm`: `AlarmSuppressionWorkflowContract`
- consumer `organization`: `OrganizationResponsibilityWorkflowContract`
- consumer `planning`: `PlanningWorkflowContract`
- consumer `reporting`: `ReportingWorkflowApprovalContract`

Current outbound application ports used to reach persistence or collaborating capabilities:

- `WorkflowActionRepositoryPort`
- `WorkflowAuditEventPort`
- `WorkflowDefinitionRepositoryPort`
- `WorkflowEligibilityLookupPort`
- `WorkflowIdentitySnapshotPort`
- `WorkflowInstanceRepositoryPort`
- `WorkflowNotificationRequestPort`
- `WorkflowOrganizationSnapshotPort`
- `WorkflowStateHistoryRepositoryPort`
- `WorkflowStepAssignmentRuleRepositoryPort`
- `WorkflowStepRepositoryPort`
- `WorkflowTargetMutationPort`
- `WorkflowTaskRepositoryPort`
- `WorkflowTransitionRepositoryPort`

Cross-module collaboration must preserve the canonical architecture rule: no direct import of another module's private domain, infrastructure or non-exported application packages.

## Current-State Limits

- File/class presence documents implementation structure, not proof that every business workflow, external dependency or production integration is exercised.
- HPR-P2-004 does not resolve legacy HMR/HMSR semantic obligations; HPR-P2-007/008 remain the reconciliation/remediation authority.
- `agents`, `environment` and `otsecurity` are not current implemented module roots and are not implied by this document.
