# HidraAPI Model Semantic Remediation Roadmap

**Status:** Active — HMR-001 completed; HMR-002 is next.  
**Repository:** `CHOUABBIA-AMINE/HidraAPI`  
**Roadmap:** `docs/roadmap/model-semantic-remediation.md`  
**Roadmap code:** `HMR`  
**Created:** 2026-10-03  
**Semantic source:** `docs/roadmap/model-semantic-review.md`  
**Semantic reconciliation baseline:** `15f08048c7b1defdf27ff328f3c9e94ef02b5ebe`  
**Scope:** production remediation of the 104 HMSR models classified REVISE; 19 APPROVED models require no HMR production task unless later evidence changes their disposition.  
**Execution mode:** exactly one HMR commit code per user instruction; never continue automatically.

## 1. Purpose

Convert the completed HMS target-model semantic review into controlled, dependency-aware production remediation without losing bounded-context ownership, historical evidence, or roadmap traceability.

HMS-006 reconciled all 123 interactive reviews:

```text
APPROVED = 19
REVISE   = 104
DEFER    = 0
REMOVE   = 0
```

HMS-007 cannot finalize an approved semantic baseline while REVISE obligations remain unimplemented or not explicitly deferred. HMR is therefore the production-remediation prerequisite between HMS-006 and HMS-007.

## 2. Authoritative evidence and precedence

For every HMR production task, use current/live repository evidence in this order:

1. `AGENTS.md`;
2. this HMR roadmap;
3. the exact source HMSR section in `docs/roadmap/model-semantic-review.md`;
4. the relevant module roadmap/DDD;
5. live domain/value objects;
6. application use cases/services/ports;
7. JPA entities/mappers/repositories;
8. Flyway migrations and integrity migrations;
9. tests and architecture guards;
10. current database/provisioning constraints where relevant.

The source HMSR section remains authoritative for the semantic obligation. HMR must not silently broaden or replace it.

## 3. Mandatory execution rules

For every HMR task:

1. Recover exact live `main` and verify the task is eligible.
2. Check workflow runs and combined status once for the exact pre-task SHA. If CI is queued/in-progress, stop.
3. Read the source HMSR review and relevant module roadmap/DDD.
4. Confirm the exact file allowlist registered by HMR-002.
5. Modify only that allowlist.
6. Do not invent business rules, catalog families, cross-module ownership, lifecycle transitions, or data.
7. Prefer owner-controlled application/lookup contracts for cross-module validation; do not add cross-module database FKs unless repository architecture explicitly authorizes them.
8. Same-module FK/application integrity is allowed only where supported by the source review and module architecture.
9. Existing Flyway files are immutable unless a live repository rule explicitly says otherwise; schema corrections use additive migrations.
10. Add/update tests that prove the exact obligation and regression boundary.
11. Immediately before mutation, refetch `main`; reconcile if it changed.
12. Commit using the exact message in this roadmap.
13. Perform exactly one post-commit verification: pre→post diff, authorized files only, exact message, workflow runs and combined status once.
14. Update this roadmap task status in the same commit when its allowlist authorizes this roadmap file; otherwise use the repository's established roadmap-update mechanism.
15. Stop before the next HMR task.

## 4. Production-remediation principles

### 4.1 Bounded-context ownership

Cross-module references remain scalar/reference/snapshot semantics unless a documented owner contract says otherwise. Validation of a live foreign identity belongs behind the owning bounded context's public application/lookup contract.

### 4.2 Catalog-family integrity

A generic catalog FK proves row existence only. When HMSR requires a specific family, HMR must enforce family membership and applicable active/eligibility semantics at an evidence-backed boundary.

### 4.3 Same-module integrity

Required and optional same-module references must fail closed where HMSR identified dangling-reference risk. Use additive FKs, application validation, or both according to module architecture and cyclic/SCC constraints.

### 4.4 Lifecycle and history

Where HMSR identifies lifecycle/state inconsistency, HMR must keep aggregate state, timestamps, append-only lifecycle/history evidence, and transactional behavior coherent. Do not rewrite historical evidence.

### 4.5 Concurrency and uniqueness

When DDD/HMSR explicitly requires one-active, one-current, unique code/version, sequence, closure, or equivalent invariants, implementation must be safe under concurrent writes; an application pre-check alone is insufficient when races remain possible.

### 4.6 Greenfield posture

Repository evidence indicates HidraAPI is not deployed as a production database. HMR may use additive schema correction appropriate to the current greenfield migration strategy, but must still preserve deterministic full Flyway replay and must not rewrite already committed migrations without explicit authorization.

## 5. HMR gates

### Gate R0 — roadmap establishment

Satisfied by HMR-001 when this roadmap is created and HMS-007 is explicitly gated behind HMR completion.

### Gate R1 — executable register

HMR-002 must inspect all 104 REVISE models and populate, for every HMR-003…HMR-106 task:

- exact production/test/document file allowlist;
- exact validation commands;
- module-roadmap prerequisites;
- whether additive Flyway is required;
- whether a public owner lookup contract already exists or must be introduced first;
- any SCC/coordinated-order dependency;
- whether the HMSR obligation has already been implemented by newer live-main evidence.

No HMR production task may start until its row is changed from **Blocked pending HMR-002** to **Planned** with that evidence.

### Gate R2 — model remediation complete

All HMR-003…HMR-106 tasks must be Completed, Skipped with stronger live evidence, or explicitly Deferred with a documented owner/justification that satisfies the HMS completion gate.

### Gate R3 — exact-SHA verification

HMR-107 verifies full repository build/test/migration/OpenAPI/architecture gates required by the live project at the exact remediation head and checks that no unresolved REVISE obligation is accidentally omitted.

### Gate R4 — programme closure

HMR-108 closes this roadmap and records whether HMS-007 is unblocked. HMR-108 does not execute HMS-007.

## 6. Task roadmap

| Code | Exact commit message | Deliverable / acceptance | Status |
|---|---|---|---|
| HMR-001 | `docs(model-remediation): establish semantic remediation roadmap` | Create this roadmap from the completed HMS-006 reconciliation; reserve one production task per REVISE model; gate HMS-007 behind HMR; no production changes. | **Completed** |
| HMR-002 | `docs(model-remediation): prepare executable remediation register` | Re-read all 104 REVISE sections against live main; populate exact per-task file allowlists, validations, prerequisites, already-fixed evidence, SCC ordering, and additive migration needs. Documentation only. | Planned |
| HMR-107 | `test(model-remediation): verify semantic remediation closure` | After all model tasks resolve, run the repository-required full exact-SHA verification, validate Flyway replay/architecture/OpenAPI gates, and prove the 104-source obligation register has no unresolved accidental omissions. | Blocked — HMR-003…HMR-106 unresolved |
| HMR-108 | `docs(model-remediation): close semantic remediation programme` | Reconcile HMR outcomes, record completed/skipped/deferred obligations, and explicitly determine whether the HMS-007 completion gate is satisfied. No HMS-007 execution. | Blocked — HMR-107 required |

## 7. Mapping and numbering rule

HMR-003 through HMR-106 map one-to-one to the 104 HMSR models whose final decision is REVISE.

The ordering preserves the authoritative HMSR dependency order. APPROVED models are omitted from production remediation. A later task must not be pulled forward merely because its module is convenient to edit.

Each model task implements only the obligations recorded in its source HMSR section, as reconciled by newer live evidence during HMR-002.

## 8. Production task register

### 8.1 Dependency level 0

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-003 | HMSR-002 | workflow | WorkflowDefinition | — | — | `fix(workflow): remediate semantic review WorkflowDefinition` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-004 | HMSR-004 | party | Party | — | — | `fix(party): remediate semantic review Party` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-005 | HMSR-005 | telemetry | TelemetryPoint | — | — | `fix(telemetry): remediate semantic review TelemetryPoint` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-006 | HMSR-006 | planning | PlanningPeriod | — | — | `fix(planning): remediate semantic review PlanningPeriod` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-007 | HMSR-007 | identity | Role | — | — | `fix(identity): remediate semantic review Role` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-008 | HMSR-008 | documents | DocumentStorageObject | — | — | `fix(documents): remediate semantic review DocumentStorageObject` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-009 | HMSR-009 | simulation | SimulationModel | — | — | `fix(simulation): remediate semantic review SimulationModel` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-010 | HMSR-010 | identity | IdentityProvider | — | — | `fix(identity): remediate semantic review IdentityProvider` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-011 | HMSR-011 | identity | Permission | — | — | `fix(identity): remediate semantic review Permission` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-012 | HMSR-012 | notification | NotificationTemplate | — | — | `fix(notification): remediate semantic review NotificationTemplate` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-013 | HMSR-013 | reporting | ReportDefinition | — | — | `fix(reporting): remediate semantic review ReportDefinition` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-014 | HMSR-014 | integration | IntegrationJobRun | — | — | `fix(integration): remediate semantic review IntegrationJobRun` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-015 | HMSR-015 | leakdetection | LeakCandidate | — | — | `fix(leakdetection): remediate semantic review LeakCandidate` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-016 | HMSR-018 | analytics | MetricEvaluationRun | — | — | `fix(analytics): remediate semantic review MetricEvaluationRun` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-017 | HMSR-019 | configuration | ConfigurationDefinition | — | — | `fix(configuration): remediate semantic review ConfigurationDefinition` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-018 | HMSR-020 | custody | CustodyMeasurementPeriod | — | — | `fix(custody): remediate semantic review CustodyMeasurementPeriod` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-019 | HMSR-021 | integrity | PipelineDefect | — | — | `fix(integrity): remediate semantic review PipelineDefect` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-020 | HMSR-022 | organization | Position | — | — | `fix(organization): remediate semantic review Position` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-021 | HMSR-023 | organization | Shift | — | — | `fix(organization): remediate semantic review Shift` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-022 | HMSR-024 | topology | PipelineSystem | — | — | `fix(topology): remediate semantic review PipelineSystem` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-023 | HMSR-025 | analytics | AnalyticsInsight | — | — | `fix(analytics): remediate semantic review AnalyticsInsight` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-024 | HMSR-026 | analytics | AnalyticsProjectionRun | — | — | `fix(analytics): remediate semantic review AnalyticsProjectionRun` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-025 | HMSR-027 | analytics | DigitalTwinReadinessAssessment | — | — | `fix(analytics): remediate semantic review DigitalTwinReadinessAssessment` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-026 | HMSR-028 | configuration | FeatureFlag | — | — | `fix(configuration): remediate semantic review FeatureFlag` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-027 | HMSR-029 | custody | CustodyDiscrepancy | — | — | `fix(custody): remediate semantic review CustodyDiscrepancy` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-028 | HMSR-031 | organization | ReportingLine | — | — | `fix(organization): remediate semantic review ReportingLine` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-029 | HMSR-032 | risk | RiskMatrixCell | — | — | `fix(risk): remediate semantic review RiskMatrixCell` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-030 | HMSR-033 | telemetry | TelemetrySource | — | — | `fix(telemetry): remediate semantic review TelemetrySource` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-031 | HMSR-034 | topology | TopologyConnection | — | — | `fix(topology): remediate semantic review TopologyConnection` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.2 Dependency level 1

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-032 | HMSR-035 | organization | OrganizationUnit | SCC-01 | organization.OrganizationUnit, organization.OrganizationUnitType | `fix(organization): remediate semantic review OrganizationUnit` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-033 | HMSR-037 | telemetry | TelemetryReading | — | telemetry.TelemetryPoint | `fix(telemetry): remediate semantic review TelemetryReading` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-034 | HMSR-038 | simulation | SimulationScenario | — | simulation.SimulationModel | `fix(simulation): remediate semantic review SimulationScenario` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-035 | HMSR-039 | notification | NotificationRequest | — | notification.NotificationTemplate | `fix(notification): remediate semantic review NotificationRequest` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-036 | HMSR-041 | topology | Facility | — | party.Party | `fix(topology): remediate semantic review Facility` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-037 | HMSR-042 | analytics | AnalyticsDatasetVersion | — | analytics.AnalyticsDataset | `fix(analytics): remediate semantic review AnalyticsDatasetVersion` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-038 | HMSR-043 | analytics | MetricValue | — | analytics.MetricEvaluationRun | `fix(analytics): remediate semantic review MetricValue` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-039 | HMSR-044 | configuration | ConfigurationValue | — | configuration.ConfigurationDefinition | `fix(configuration): remediate semantic review ConfigurationValue` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-040 | HMSR-048 | monitoring | MonitoringRule | — | telemetry.TelemetryPoint | `fix(monitoring): remediate semantic review MonitoringRule` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-041 | HMSR-049 | party | PartyRoleAssignment | — | party.Party | `fix(party): remediate semantic review PartyRoleAssignment` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-042 | HMSR-050 | topology | Pipeline | — | topology.PipelineSystem | `fix(topology): remediate semantic review Pipeline` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.3 Dependency level 2

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-043 | HMSR-051 | workflow | WorkflowStep | SCC-02 | workflow.WorkflowDefinition, workflow.WorkflowStepAssignmentRule | `fix(workflow): remediate semantic review WorkflowStep` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-044 | HMSR-052 | workflow | WorkflowStepAssignmentRule | SCC-02 | organization.OrganizationUnit, workflow.WorkflowDefinition, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowStepAssignmentRule` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-045 | HMSR-054 | assets | MaintainableAsset | SCC-03 | assets.MaintainableAsset, organization.OrganizationUnit, party.Party | `fix(assets): remediate semantic review MaintainableAsset` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-046 | HMSR-055 | simulation | SimulationRun | — | simulation.SimulationScenario | `fix(simulation): remediate semantic review SimulationRun` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-047 | HMSR-056 | integration | ExternalSystem | — | organization.OrganizationUnit | `fix(integration): remediate semantic review ExternalSystem` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-048 | HMSR-057 | reporting | ReportRequest | — | organization.OrganizationUnit, reporting.ReportDefinition | `fix(reporting): remediate semantic review ReportRequest` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-049 | HMSR-058 | risk | RiskRegister | — | organization.OrganizationUnit | `fix(risk): remediate semantic review RiskRegister` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-050 | HMSR-059 | integrity | IntegrityProgram | — | organization.OrganizationUnit | `fix(integrity): remediate semantic review IntegrityProgram` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-051 | HMSR-060 | leakdetection | LeakDetectionCase | — | leakdetection.LeakCandidate, organization.OrganizationUnit | `fix(leakdetection): remediate semantic review LeakDetectionCase` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-052 | HMSR-061 | notification | NotificationMessage | — | notification.NotificationRequest, notification.NotificationTemplate | `fix(notification): remediate semantic review NotificationMessage` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-053 | HMSR-062 | telemetry | TrustedTelemetryReading | — | telemetry.TelemetryPoint, telemetry.TelemetryReading | `fix(telemetry): remediate semantic review TrustedTelemetryReading` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-054 | HMSR-063 | topology | Equipment | — | party.Party, topology.Facility | `fix(topology): remediate semantic review Equipment` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.4 Dependency level 3

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-055 | HMSR-064 | workflow | WorkflowInstance | — | workflow.WorkflowDefinition, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowInstance` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-056 | HMSR-067 | integration | IntegrationExchangeMessage | — | integration.ExternalSystem, integration.IntegrationJobRun | `fix(integration): remediate semantic review IntegrationExchangeMessage` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-057 | HMSR-068 | reporting | ReportRun | — | reporting.ReportDefinition, reporting.ReportRequest | `fix(reporting): remediate semantic review ReportRun` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-058 | HMSR-069 | risk | RiskAssessment | — | risk.RiskRegister | `fix(risk): remediate semantic review RiskAssessment` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-059 | HMSR-071 | leakdetection | LeakEscalationReference | — | leakdetection.LeakCandidate, leakdetection.LeakDetectionCase | `fix(leakdetection): remediate semantic review LeakEscalationReference` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-060 | HMSR-072 | notification | NotificationDeliveryAttempt | — | notification.NotificationMessage | `fix(notification): remediate semantic review NotificationDeliveryAttempt` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-061 | HMSR-073 | workflow | WorkflowTransition | — | workflow.WorkflowDefinition, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowTransition` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.5 Dependency level 4

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-062 | HMSR-074 | incident | Incident | — | organization.OrganizationUnit, workflow.WorkflowInstance | `fix(incident): remediate semantic review Incident` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-063 | HMSR-075 | identity | User | — | organization.Employee | `fix(identity): remediate semantic review User` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-064 | HMSR-076 | planning | PlanRevision | SCC-04 | planning.OperationalPlan, planning.PlanRevision, workflow.WorkflowInstance | `fix(planning): remediate semantic review PlanRevision` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-065 | HMSR-077 | planning | OperationalPlan | SCC-04 | organization.OrganizationUnit, planning.PlanRevision, planning.PlanningPeriod | `fix(planning): remediate semantic review OperationalPlan` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-066 | HMSR-078 | workflow | WorkflowTask | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowTask` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-067 | HMSR-079 | documents | Document | SCC-05 | documents.DocumentVersion | `fix(documents): remediate semantic review Document` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-068 | HMSR-080 | documents | DocumentVersion | SCC-05 | documents.Document, documents.DocumentStorageObject, documents.DocumentVersion, workflow.WorkflowInstance | `fix(documents): remediate semantic review DocumentVersion` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-069 | HMSR-081 | assets | MaintenanceWorkOrder | — | assets.MaintainableAsset, organization.OrganizationUnit, workflow.WorkflowInstance | `fix(assets): remediate semantic review MaintenanceWorkOrder` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-070 | HMSR-082 | custody | CustodyTransferTicket | — | custody.CustodyMeasurementPeriod, workflow.WorkflowInstance | `fix(custody): remediate semantic review CustodyTransferTicket` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-071 | HMSR-084 | integration | IntegrationDeadLetterRecord | — | integration.ExternalSystem, integration.IntegrationExchangeMessage, integration.IntegrationJobRun | `fix(integration): remediate semantic review IntegrationDeadLetterRecord` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-072 | HMSR-085 | integrity | IntegrityAssessment | — | integrity.IntegrityProgram, workflow.WorkflowInstance | `fix(integrity): remediate semantic review IntegrityAssessment` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-073 | HMSR-087 | organization | EmployeeAssignment | — | organization.Employee, organization.OrganizationUnit, organization.Position | `fix(organization): remediate semantic review EmployeeAssignment` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-074 | HMSR-088 | organization | OrganizationDelegation | — | organization.Employee, organization.ResponsibilityAssignment | `fix(organization): remediate semantic review OrganizationDelegation` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-075 | HMSR-089 | organization | OrganizationHierarchySnapshot | — | organization.Employee | `fix(organization): remediate semantic review OrganizationHierarchySnapshot` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-076 | HMSR-090 | organization | ShiftAssignment | — | organization.Employee, organization.OrganizationUnit, organization.Shift | `fix(organization): remediate semantic review ShiftAssignment` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-077 | HMSR-091 | risk | RiskEvidenceLink | — | risk.RiskAssessment | `fix(risk): remediate semantic review RiskEvidenceLink` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-078 | HMSR-092 | simulation | SimulationCandidateChange | — | simulation.SimulationOptimizationCandidate | `fix(simulation): remediate semantic review SimulationCandidateChange` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-079 | HMSR-093 | simulation | SimulationRecommendation | — | simulation.SimulationOptimizationCandidate, simulation.SimulationRun | `fix(simulation): remediate semantic review SimulationRecommendation` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.6 Dependency level 5

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-080 | HMSR-094 | planning | Nomination | — | party.Party, planning.PlanRevision | `fix(planning): remediate semantic review Nomination` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-081 | HMSR-095 | workflow | WorkflowAction | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowTask | `fix(workflow): remediate semantic review WorkflowAction` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-082 | HMSR-096 | hse | HseCase | — | incident.Incident, organization.OrganizationUnit, workflow.WorkflowInstance | `fix(hse): remediate semantic review HseCase` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-083 | HMSR-097 | audit | AuditExportRequest | — | documents.Document, workflow.WorkflowInstance | `fix(audit): remediate semantic review AuditExportRequest` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-084 | HMSR-098 | documents | DocumentTargetLink | — | documents.Document, documents.DocumentVersion | `fix(documents): remediate semantic review DocumentTargetLink` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-085 | HMSR-100 | identity | AuthorizationDecision | — | identity.User | `fix(identity): remediate semantic review AuthorizationDecision` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-086 | HMSR-101 | identity | AuthorizationDelegationGrant | — | identity.Permission, identity.Role, identity.User | `fix(identity): remediate semantic review AuthorizationDelegationGrant` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-087 | HMSR-104 | identity | LoginSession | — | identity.IdentityProvider, identity.User | `fix(identity): remediate semantic review LoginSession` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-088 | HMSR-105 | identity | UserPermissionGrant | — | identity.Permission, identity.User | `fix(identity): remediate semantic review UserPermissionGrant` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-089 | HMSR-106 | identity | UserRoleGrant | — | identity.Role, identity.User | `fix(identity): remediate semantic review UserRoleGrant` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-090 | HMSR-107 | incident | IncidentClosure | — | incident.Incident, workflow.WorkflowInstance | `fix(incident): remediate semantic review IncidentClosure` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-091 | HMSR-108 | incident | IncidentRelatedIncident | — | incident.Incident | `fix(incident): remediate semantic review IncidentRelatedIncident` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-092 | HMSR-109 | incident | IncidentResponseAction | — | incident.Incident, organization.OrganizationUnit | `fix(incident): remediate semantic review IncidentResponseAction` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-093 | HMSR-110 | reporting | ReportOutputArtifact | — | documents.Document, documents.DocumentStorageObject, reporting.ReportRun | `fix(reporting): remediate semantic review ReportOutputArtifact` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.7 Dependency level 6

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-094 | HMSR-111 | planning | PlanTarget | — | planning.Nomination, planning.PlanRevision, telemetry.TelemetryPoint | `fix(planning): remediate semantic review PlanTarget` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-095 | HMSR-112 | audit | AuditEvent | — | organization.OrganizationUnit, workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowTask | `fix(audit): remediate semantic review AuditEvent` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-096 | HMSR-113 | hse | HseClosure | — | hse.HseCase, workflow.WorkflowInstance | `fix(hse): remediate semantic review HseClosure` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-097 | HMSR-114 | hse | HseCorrectivePreventiveAction | — | assets.MaintenanceWorkOrder, hse.HseCase, organization.OrganizationUnit, workflow.WorkflowTask | `fix(hse): remediate semantic review HseCorrectivePreventiveAction` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-098 | HMSR-115 | integrity | IntegrityCase | — | hse.HseCase, incident.Incident, integrity.PipelineDefect, organization.OrganizationUnit, workflow.WorkflowInstance | `fix(integrity): remediate semantic review IntegrityCase` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-099 | HMSR-116 | workflow | WorkflowStateHistory | — | workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowStep, workflow.WorkflowTask | `fix(workflow): remediate semantic review WorkflowStateHistory` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.8 Dependency level 7

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-100 | HMSR-117 | alarm | Alarm | — | incident.Incident, organization.OrganizationUnit, planning.PlanTarget, telemetry.TelemetryReading, workflow.WorkflowInstance | `fix(alarm): remediate semantic review Alarm` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-101 | HMSR-118 | audit | AuditAccessRecord | — | audit.AuditEvent, audit.AuditExportRequest | `fix(audit): remediate semantic review AuditAccessRecord` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-102 | HMSR-119 | audit | AuditBeforeAfterValue | — | audit.AuditEvent | `fix(audit): remediate semantic review AuditBeforeAfterValue` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-103 | HMSR-120 | monitoring | PlanActualDeviation | — | planning.PlanTarget, telemetry.TelemetryPoint, telemetry.TrustedTelemetryReading | `fix(monitoring): remediate semantic review PlanActualDeviation` | **Blocked pending HMR-002 allowlist/validation registration** |

### 8.9 Dependency level 8

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-104 | HMSR-121 | alarm | AlarmAcknowledgement | — | alarm.Alarm, organization.OrganizationUnit | `fix(alarm): remediate semantic review AlarmAcknowledgement` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-105 | HMSR-122 | alarm | AlarmClosure | — | alarm.Alarm, workflow.WorkflowInstance | `fix(alarm): remediate semantic review AlarmClosure` | **Blocked pending HMR-002 allowlist/validation registration** |
| HMR-106 | HMSR-123 | alarm | AlarmShelving | — | alarm.Alarm | `fix(alarm): remediate semantic review AlarmShelving` | **Blocked pending HMR-002 allowlist/validation registration** |

## 9. SCC coordination

The five semantic SCCs remain valid and must not be broken merely to simplify implementation:

- **SCC-01 — organization.OrganizationUnit:** hierarchy self-reference.
- **SCC-02 — workflow.WorkflowStep / WorkflowStepAssignmentRule:** step/default-assignment-rule mutual relationship.
- **SCC-03 — assets.MaintainableAsset:** parent-asset self-reference.
- **SCC-04 — planning.OperationalPlan / PlanRevision:** plan/revision current-approved cycle.
- **SCC-05 — documents.Document / DocumentVersion:** document/current-version plus version ownership/supersession.

HMR-002 must record any same-transaction, migration-order, or composite-integrity requirements for these tasks before they become Planned.

## 10. Completion criteria

HMR is complete only when:

- HMR-002 has made every production task executable with exact allowlist and validation;
- every HMR-003…HMR-106 task is Completed, evidence-backed Skipped, or explicitly Deferred in a way accepted by the HMS semantic-baseline gate;
- cross-module ownership remains intact;
- no unauthorized cross-module DB FK has been introduced;
- all additive schema changes replay from an empty PostgreSQL database;
- required concurrency/uniqueness/lifecycle/history/catalog-family invariants are tested;
- HMR-107 exact-SHA verification is complete;
- HMR-108 records closure and whether HMS-007 is unblocked.

HMR completion does not itself execute HMS-007 or resume HDP-004.

## 11. Current next task

```text
HMR-002 — prepare executable remediation register
```

Exact commit message:

```text
docs(model-remediation): prepare executable remediation register
```

Do not start HMR-003 or any production remediation automatically.
