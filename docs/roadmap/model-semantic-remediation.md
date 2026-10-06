> **WARNING: SUPERSEDED DOCUMENT**
> This document is no longer authoritative for execution. The single source of truth for platform finalization and target architecture is now `doc/roadmap/ULTIMATE_ROADMAP.md`.

# HidraAPI Model Semantic Remediation Roadmap

**Status:** Active — HMR-001 and HMR-002 completed; 101 model-remediation tasks are executable and 3 remain blocked on explicit owner-contract prerequisites. HMR-003 is the next executable task.  
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
| HMR-002 | `docs(model-remediation): prepare executable remediation register` | Re-read all 104 REVISE sections against live main; populate exact per-task file allowlists, validations, prerequisites, already-fixed evidence, SCC ordering, and additive migration needs. Documentation only. | **Completed** — 104/104 REVISE tasks reconciled against live tree; 101 executable tasks marked Planned and 3 tasks remain Blocked only where a required exported owner lookup contract was not found. |
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
| HMR-003 | HMSR-002 | workflow | WorkflowDefinition | — | — | `fix(workflow): remediate semantic review WorkflowDefinition` | **Completed** |
| HMR-004 | HMSR-004 | party | Party | — | — | `fix(party): remediate semantic review Party` | **Completed** |
| HMR-005 | HMSR-005 | telemetry | TelemetryPoint | — | — | `fix(telemetry): remediate semantic review TelemetryPoint` | **Completed** |
| HMR-006 | HMSR-006 | planning | PlanningPeriod | — | — | `fix(planning): remediate semantic review PlanningPeriod` | **Completed** |
| HMR-007 | HMSR-007 | identity | Role | — | — | `fix(identity): remediate semantic review Role` | **Completed** |
| HMR-008 | HMSR-008 | documents | DocumentStorageObject | — | — | `fix(documents): remediate semantic review DocumentStorageObject` | **Completed** |
| HMR-009 | HMSR-009 | simulation | SimulationModel | — | — | `fix(simulation): remediate semantic review SimulationModel` | **Blocked — HMR-009A required: SimulationModel creation needs a Topology-owned `(scopeType, scopeId)` validation contract, but the live Simulation `TopologySnapshotLookupPort` is unimplemented/insufficient and the only exported Topology operational-scope contract is Organization-specific with a mismatched vocabulary (`FACILITY`/`EQUIPMENT` instead of `SEGMENT_GROUP`/`FACILITY_NETWORK`).** |
| HMR-010 | HMSR-010 | identity | IdentityProvider | — | — | `fix(identity): remediate semantic review IdentityProvider` | **Completed** |
| HMR-011 | HMSR-011 | identity | Permission | — | — | `fix(identity): remediate semantic review Permission` | **Completed** |
| HMR-012 | HMSR-012 | notification | NotificationTemplate | — | — | `fix(notification): remediate semantic review NotificationTemplate` | **Completed** |
| HMR-013 | HMSR-013 | reporting | ReportDefinition | — | — | `fix(reporting): remediate semantic review ReportDefinition` | **Completed** |
| HMR-014 | HMSR-014 | integration | IntegrationJobRun | — | — | `fix(integration): remediate semantic review IntegrationJobRun` | **Completed** |
| HMR-015 | HMSR-015 | leakdetection | LeakCandidate | — | — | `fix(leakdetection): remediate semantic review LeakCandidate` | **Completed** |
| HMR-016 | HMSR-018 | analytics | MetricEvaluationRun | — | — | `fix(analytics): remediate semantic review MetricEvaluationRun` | **Completed** |
| HMR-017 | HMSR-019 | configuration | ConfigurationDefinition | — | — | `fix(configuration): remediate semantic review ConfigurationDefinition` | **Completed** |
| HMR-018 | HMSR-020 | custody | CustodyMeasurementPeriod | — | — | `fix(custody): remediate semantic review CustodyMeasurementPeriod` | **Completed** |
| HMR-019 | HMSR-021 | integrity | PipelineDefect | — | — | `fix(integrity): remediate semantic review PipelineDefect` | **Completed** |
| HMR-020 | HMSR-022 | organization | Position | — | — | `fix(organization): remediate semantic review Position` | **Completed** |
| HMR-021 | HMSR-023 | organization | Shift | — | — | `fix(organization): remediate semantic review Shift` | **Completed** |
| HMR-022 | HMSR-024 | topology | PipelineSystem | — | — | `fix(topology): remediate semantic review PipelineSystem` | Planned |
| HMR-023 | HMSR-025 | analytics | AnalyticsInsight | — | — | `fix(analytics): remediate semantic review AnalyticsInsight` | Planned |
| HMR-024 | HMSR-026 | analytics | AnalyticsProjectionRun | — | — | `fix(analytics): remediate semantic review AnalyticsProjectionRun` | Planned |
| HMR-025 | HMSR-027 | analytics | DigitalTwinReadinessAssessment | — | — | `fix(analytics): remediate semantic review DigitalTwinReadinessAssessment` | Planned |
| HMR-026 | HMSR-028 | configuration | FeatureFlag | — | — | `fix(configuration): remediate semantic review FeatureFlag` | Planned |
| HMR-027 | HMSR-029 | custody | CustodyDiscrepancy | — | — | `fix(custody): remediate semantic review CustodyDiscrepancy` | Planned |
| HMR-028 | HMSR-031 | organization | ReportingLine | — | — | `fix(organization): remediate semantic review ReportingLine` | Planned |
| HMR-029 | HMSR-032 | risk | RiskMatrixCell | — | — | `fix(risk): remediate semantic review RiskMatrixCell` | Planned |
| HMR-030 | HMSR-033 | telemetry | TelemetrySource | — | — | `fix(telemetry): remediate semantic review TelemetrySource` | Planned |
| HMR-031 | HMSR-034 | topology | TopologyConnection | — | — | `fix(topology): remediate semantic review TopologyConnection` | Planned |

### 8.2 Dependency level 1

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-032 | HMSR-035 | organization | OrganizationUnit | SCC-01 | organization.OrganizationUnit, organization.OrganizationUnitType | `fix(organization): remediate semantic review OrganizationUnit` | Planned |
| HMR-033 | HMSR-037 | telemetry | TelemetryReading | — | telemetry.TelemetryPoint | `fix(telemetry): remediate semantic review TelemetryReading` | Planned |
| HMR-034 | HMSR-038 | simulation | SimulationScenario | — | simulation.SimulationModel | `fix(simulation): remediate semantic review SimulationScenario` | Planned |
| HMR-035 | HMSR-039 | notification | NotificationRequest | — | notification.NotificationTemplate | `fix(notification): remediate semantic review NotificationRequest` | Planned |
| HMR-036 | HMSR-041 | topology | Facility | — | party.Party | `fix(topology): remediate semantic review Facility` | Blocked — owner lookup contract prerequisite unresolved |
| HMR-037 | HMSR-042 | analytics | AnalyticsDatasetVersion | — | analytics.AnalyticsDataset | `fix(analytics): remediate semantic review AnalyticsDatasetVersion` | Planned |
| HMR-038 | HMSR-043 | analytics | MetricValue | — | analytics.MetricEvaluationRun | `fix(analytics): remediate semantic review MetricValue` | Planned |
| HMR-039 | HMSR-044 | configuration | ConfigurationValue | — | configuration.ConfigurationDefinition | `fix(configuration): remediate semantic review ConfigurationValue` | Planned |
| HMR-040 | HMSR-048 | monitoring | MonitoringRule | — | telemetry.TelemetryPoint | `fix(monitoring): remediate semantic review MonitoringRule` | Planned |
| HMR-041 | HMSR-049 | party | PartyRoleAssignment | — | party.Party | `fix(party): remediate semantic review PartyRoleAssignment` | Planned |
| HMR-042 | HMSR-050 | topology | Pipeline | — | topology.PipelineSystem | `fix(topology): remediate semantic review Pipeline` | Planned |

### 8.3 Dependency level 2

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-043 | HMSR-051 | workflow | WorkflowStep | SCC-02 | workflow.WorkflowDefinition, workflow.WorkflowStepAssignmentRule | `fix(workflow): remediate semantic review WorkflowStep` | Planned |
| HMR-044 | HMSR-052 | workflow | WorkflowStepAssignmentRule | SCC-02 | organization.OrganizationUnit, workflow.WorkflowDefinition, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowStepAssignmentRule` | Planned |
| HMR-045 | HMSR-054 | assets | MaintainableAsset | SCC-03 | assets.MaintainableAsset, organization.OrganizationUnit, party.Party | `fix(assets): remediate semantic review MaintainableAsset` | Planned |
| HMR-046 | HMSR-055 | simulation | SimulationRun | — | simulation.SimulationScenario | `fix(simulation): remediate semantic review SimulationRun` | Planned |
| HMR-047 | HMSR-056 | integration | ExternalSystem | — | organization.OrganizationUnit | `fix(integration): remediate semantic review ExternalSystem` | Planned |
| HMR-048 | HMSR-057 | reporting | ReportRequest | — | organization.OrganizationUnit, reporting.ReportDefinition | `fix(reporting): remediate semantic review ReportRequest` | Planned |
| HMR-049 | HMSR-058 | risk | RiskRegister | — | organization.OrganizationUnit | `fix(risk): remediate semantic review RiskRegister` | Planned |
| HMR-050 | HMSR-059 | integrity | IntegrityProgram | — | organization.OrganizationUnit | `fix(integrity): remediate semantic review IntegrityProgram` | Planned |
| HMR-051 | HMSR-060 | leakdetection | LeakDetectionCase | — | leakdetection.LeakCandidate, organization.OrganizationUnit | `fix(leakdetection): remediate semantic review LeakDetectionCase` | Completed — Batch 1 |
| HMR-052 | HMSR-061 | notification | NotificationMessage | — | notification.NotificationRequest, notification.NotificationTemplate | `fix(notification): remediate semantic review NotificationMessage` | Planned |
| HMR-053 | HMSR-062 | telemetry | TrustedTelemetryReading | — | telemetry.TelemetryPoint, telemetry.TelemetryReading | `fix(telemetry): remediate semantic review TrustedTelemetryReading` | Planned |
| HMR-054 | HMSR-063 | topology | Equipment | — | party.Party, topology.Facility | `fix(topology): remediate semantic review Equipment` | Blocked — owner lookup contract prerequisite unresolved |

### 8.4 Dependency level 3

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-055 | HMSR-064 | workflow | WorkflowInstance | — | workflow.WorkflowDefinition, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowInstance` | Planned |
| HMR-056 | HMSR-067 | integration | IntegrationExchangeMessage | — | integration.ExternalSystem, integration.IntegrationJobRun | `fix(integration): remediate semantic review IntegrationExchangeMessage` | Planned |
| HMR-057 | HMSR-068 | reporting | ReportRun | — | reporting.ReportDefinition, reporting.ReportRequest | `fix(reporting): remediate semantic review ReportRun` | Planned |
| HMR-058 | HMSR-069 | risk | RiskAssessment | — | risk.RiskRegister | `fix(risk): remediate semantic review RiskAssessment` | Planned |
| HMR-059 | HMSR-071 | leakdetection | LeakEscalationReference | — | leakdetection.LeakCandidate, leakdetection.LeakDetectionCase | `fix(leakdetection): remediate semantic review LeakEscalationReference` | Completed — Batch 1 |
| HMR-060 | HMSR-072 | notification | NotificationDeliveryAttempt | — | notification.NotificationMessage | `fix(notification): remediate semantic review NotificationDeliveryAttempt` | Planned |
| HMR-061 | HMSR-073 | workflow | WorkflowTransition | — | workflow.WorkflowDefinition, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowTransition` | Planned |

### 8.5 Dependency level 4

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-062 | HMSR-074 | incident | Incident | — | organization.OrganizationUnit, workflow.WorkflowInstance | `fix(incident): remediate semantic review Incident` | Planned |
| HMR-063 | HMSR-075 | identity | User | — | organization.Employee | `fix(identity): remediate semantic review User` | Planned |
| HMR-064 | HMSR-076 | planning | PlanRevision | SCC-04 | planning.OperationalPlan, planning.PlanRevision, workflow.WorkflowInstance | `fix(planning): remediate semantic review PlanRevision` | Planned |
| HMR-065 | HMSR-077 | planning | OperationalPlan | SCC-04 | organization.OrganizationUnit, planning.PlanRevision, planning.PlanningPeriod | `fix(planning): remediate semantic review OperationalPlan` | Planned |
| HMR-066 | HMSR-078 | workflow | WorkflowTask | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowStep | `fix(workflow): remediate semantic review WorkflowTask` | Planned |
| HMR-067 | HMSR-079 | documents | Document | SCC-05 | documents.DocumentVersion | `fix(documents): remediate semantic review Document` | Planned |
| HMR-068 | HMSR-080 | documents | DocumentVersion | SCC-05 | documents.Document, documents.DocumentStorageObject, documents.DocumentVersion, workflow.WorkflowInstance | `fix(documents): remediate semantic review DocumentVersion` | Planned |
| HMR-069 | HMSR-081 | assets | MaintenanceWorkOrder | — | assets.MaintainableAsset, organization.OrganizationUnit, workflow.WorkflowInstance | `fix(assets): remediate semantic review MaintenanceWorkOrder` | Planned |
| HMR-070 | HMSR-082 | custody | CustodyTransferTicket | — | custody.CustodyMeasurementPeriod, workflow.WorkflowInstance | `fix(custody): remediate semantic review CustodyTransferTicket` | Planned |
| HMR-071 | HMSR-084 | integration | IntegrationDeadLetterRecord | — | integration.ExternalSystem, integration.IntegrationExchangeMessage, integration.IntegrationJobRun | `fix(integration): remediate semantic review IntegrationDeadLetterRecord` | Planned |
| HMR-072 | HMSR-085 | integrity | IntegrityAssessment | — | integrity.IntegrityProgram, workflow.WorkflowInstance | `fix(integrity): remediate semantic review IntegrityAssessment` | Planned |
| HMR-073 | HMSR-087 | organization | EmployeeAssignment | — | organization.Employee, organization.OrganizationUnit, organization.Position | `fix(organization): remediate semantic review EmployeeAssignment` | Completed — Batch 1 |
| HMR-074 | HMSR-088 | organization | OrganizationDelegation | — | organization.Employee, organization.ResponsibilityAssignment | `fix(organization): remediate semantic review OrganizationDelegation` | Completed — Batch 1 |
| HMR-075 | HMSR-089 | organization | OrganizationHierarchySnapshot | — | organization.Employee | `fix(organization): remediate semantic review OrganizationHierarchySnapshot` | Completed — Batch 1 |
| HMR-076 | HMSR-090 | organization | ShiftAssignment | — | organization.Employee, organization.OrganizationUnit, organization.Shift | `fix(organization): remediate semantic review ShiftAssignment` | Planned |
| HMR-077 | HMSR-091 | risk | RiskEvidenceLink | — | risk.RiskAssessment | `fix(risk): remediate semantic review RiskEvidenceLink` | Planned |
| HMR-078 | HMSR-092 | simulation | SimulationCandidateChange | — | simulation.SimulationOptimizationCandidate | `fix(simulation): remediate semantic review SimulationCandidateChange` | Planned |
| HMR-079 | HMSR-093 | simulation | SimulationRecommendation | — | simulation.SimulationOptimizationCandidate, simulation.SimulationRun | `fix(simulation): remediate semantic review SimulationRecommendation` | Planned |

### 8.6 Dependency level 5

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-080 | HMSR-094 | planning | Nomination | — | party.Party, planning.PlanRevision | `fix(planning): remediate semantic review Nomination` | Blocked — owner lookup contract prerequisite unresolved |
| HMR-081 | HMSR-095 | workflow | WorkflowAction | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowTask | `fix(workflow): remediate semantic review WorkflowAction` | Planned |
| HMR-082 | HMSR-096 | hse | HseCase | — | incident.Incident, organization.OrganizationUnit, workflow.WorkflowInstance | `fix(hse): remediate semantic review HseCase` | Planned |
| HMR-083 | HMSR-097 | audit | AuditExportRequest | — | documents.Document, workflow.WorkflowInstance | `fix(audit): remediate semantic review AuditExportRequest` | Planned |
| HMR-084 | HMSR-098 | documents | DocumentTargetLink | — | documents.Document, documents.DocumentVersion | `fix(documents): remediate semantic review DocumentTargetLink` | Planned |
| HMR-085 | HMSR-100 | identity | AuthorizationDecision | — | identity.User | `fix(identity): remediate semantic review AuthorizationDecision` | Planned |
| HMR-086 | HMSR-101 | identity | AuthorizationDelegationGrant | — | identity.Permission, identity.Role, identity.User | `fix(identity): remediate semantic review AuthorizationDelegationGrant` | Planned |
| HMR-087 | HMSR-104 | identity | LoginSession | — | identity.IdentityProvider, identity.User | `fix(identity): remediate semantic review LoginSession` | Planned |
| HMR-088 | HMSR-105 | identity | UserPermissionGrant | — | identity.Permission, identity.User | `fix(identity): remediate semantic review UserPermissionGrant` | Planned |
| HMR-089 | HMSR-106 | identity | UserRoleGrant | — | identity.Role, identity.User | `fix(identity): remediate semantic review UserRoleGrant` | Planned |
| HMR-090 | HMSR-107 | incident | IncidentClosure | — | incident.Incident, workflow.WorkflowInstance | `fix(incident): remediate semantic review IncidentClosure` | Planned |
| HMR-091 | HMSR-108 | incident | IncidentRelatedIncident | — | incident.Incident | `fix(incident): remediate semantic review IncidentRelatedIncident` | Planned |
| HMR-092 | HMSR-109 | incident | IncidentResponseAction | — | incident.Incident, organization.OrganizationUnit | `fix(incident): remediate semantic review IncidentResponseAction` | Planned |
| HMR-093 | HMSR-110 | reporting | ReportOutputArtifact | — | documents.Document, documents.DocumentStorageObject, reporting.ReportRun | `fix(reporting): remediate semantic review ReportOutputArtifact` | Planned |

### 8.7 Dependency level 6

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-094 | HMSR-111 | planning | PlanTarget | — | planning.Nomination, planning.PlanRevision, telemetry.TelemetryPoint | `fix(planning): remediate semantic review PlanTarget` | Planned |
| HMR-095 | HMSR-112 | audit | AuditEvent | — | organization.OrganizationUnit, workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowTask | `fix(audit): remediate semantic review AuditEvent` | Planned |
| HMR-096 | HMSR-113 | hse | HseClosure | — | hse.HseCase, workflow.WorkflowInstance | `fix(hse): remediate semantic review HseClosure` | Planned |
| HMR-097 | HMSR-114 | hse | HseCorrectivePreventiveAction | — | assets.MaintenanceWorkOrder, hse.HseCase, organization.OrganizationUnit, workflow.WorkflowTask | `fix(hse): remediate semantic review HseCorrectivePreventiveAction` | Planned |
| HMR-098 | HMSR-115 | integrity | IntegrityCase | — | hse.HseCase, incident.Incident, integrity.PipelineDefect, organization.OrganizationUnit, workflow.WorkflowInstance | `fix(integrity): remediate semantic review IntegrityCase` | Planned |
| HMR-099 | HMSR-116 | workflow | WorkflowStateHistory | — | workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowStep, workflow.WorkflowTask | `fix(workflow): remediate semantic review WorkflowStateHistory` | Planned |

### 8.8 Dependency level 7

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-100 | HMSR-117 | alarm | Alarm | — | incident.Incident, organization.OrganizationUnit, planning.PlanTarget, telemetry.TelemetryReading, workflow.WorkflowInstance | `fix(alarm): remediate semantic review Alarm` | Planned |
| HMR-101 | HMSR-118 | audit | AuditAccessRecord | — | audit.AuditEvent, audit.AuditExportRequest | `fix(audit): remediate semantic review AuditAccessRecord` | Planned |
| HMR-102 | HMSR-119 | audit | AuditBeforeAfterValue | — | audit.AuditEvent | `fix(audit): remediate semantic review AuditBeforeAfterValue` | Planned |
| HMR-103 | HMSR-120 | monitoring | PlanActualDeviation | — | planning.PlanTarget, telemetry.TelemetryPoint, telemetry.TrustedTelemetryReading | `fix(monitoring): remediate semantic review PlanActualDeviation` | Planned |

### 8.9 Dependency level 8

| HMR code | HMSR source | Module | Model | SCC | Upstream HMS dependencies | Exact commit message | Status |
|---|---|---|---|---|---|---|---|
| HMR-104 | HMSR-121 | alarm | AlarmAcknowledgement | — | alarm.Alarm, organization.OrganizationUnit | `fix(alarm): remediate semantic review AlarmAcknowledgement` | Planned |
| HMR-105 | HMSR-122 | alarm | AlarmClosure | — | alarm.Alarm, workflow.WorkflowInstance | `fix(alarm): remediate semantic review AlarmClosure` | Planned |
| HMR-106 | HMSR-123 | alarm | AlarmShelving | — | alarm.Alarm | `fix(alarm): remediate semantic review AlarmShelving` | Planned |

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
HMR-003 — workflow.WorkflowDefinition
```

Exact commit message:

```text
fix(workflow): remediate semantic review WorkflowDefinition
```

HMR-003 is now executable under the exact allowlist and validation contract in section 12. Do not start HMR-004 automatically.

## 12. HMR-002 executable remediation register

**Prepared against live main:** `91f6841210e6d3ad65a1548f4911109354034bb3`  
**Tasks reconciled:** 104 / 104  
**Planned:** 101  
**Blocked on missing exported owner lookup prerequisite:** 3  
**Already-fixed / skipped by newer production evidence:** 0

No production source, test, migration, API, database data, or provisioning artifact is changed by HMR-002.

### 12.1 Common validation contract

Every executable HMR model task must run, at minimum:

```text
./mvnw -q -DskipTests compile
./mvnw -q -Dtest=<Model>SemanticRemediationTest test
./mvnw -q test
./mvnw -q clean verify
```

A task that creates an additive Flyway migration must additionally demonstrate the repository's existing fresh-PostgreSQL/Testcontainers migration replay path if one exists for that module or add the exact migration regression to its dedicated semantic remediation test.

### 12.2 Per-task executable scope

The following lists are **write allowlists**, not mandatory-change lists. A production task may touch fewer files, but it may not touch files outside its registered list without first amending this roadmap in a separate documentation task.


#### HMR-003 — workflow.WorkflowDefinition

- Source review: `HMSR-002`
- Exact commit: `fix(workflow): remediate semantic review WorkflowDefinition`
- Status: **Completed** — domain now rejects blank `nameFr` and `version < 1`; adapter fails fast on duplicate `(code, version)` and structural mutation of an existing ACTIVE definition; additive Flyway adds the race-safe unique index and database version check; semantic tests cover all four obligations. Maven validation could not be executed in this connector-only environment because the repository cannot be cloned from the execution container (`Could not resolve host: github.com`).
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionTargetBindingJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowDefinitionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Definition version minimum:** the Workflow data definition requires `version >= 1`, but the current domain constructor accepts zero or negative values and the live schema only declares `version integer NOT NULL` without a minimum-value check.
  2. **French name requirement at the domain boundary:** Workflow policy and JPA require `nameFr`, but the current domain constructor can normalize a blank French name to `null`, allowing invalid state to exist until persistence rejects it.
  3. **Definition identity/version uniqueness:** the Workflow data definition requires `(code, version)` to be unique. The live migration provides an index on `code` only; no repository evidence was found for a database unique constraint or an application uniqueness guard on `(code, version)`. The data-definition example named `idx_workflow_definition_code_version` is an ordinary index, not a uniqueness constraint, so it does not satisfy the stated rule.
  4. **Activation/version governance:** the approved semantic baseline must preserve the rule that an ACTIVE definition is not edited in place and a business change creates a new version. Any later Workflow correction task must enforce this at the appropriate application/domain boundary rather than relying on record immutability alone.

#### HMR-004 — party.Party

- Source review: `HMSR-004`
- Exact commit: `fix(party): remediate semantic review Party`
- Status: **Completed** — domain now rejects blank `legalName`; registration fails fast when `code` already exists; the repository exposes code existence lookup; additive Flyway enforces race-safe unique Party code; semantic tests cover both obligations.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_004__hmr_004_party_party.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Party.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/party/api/PartyApi.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/PartyRestApi.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/controller/PartyController.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/controller/SpringPartyController.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/mapper/PartyGeneratedRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/mapper/PartyRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/request/AssignPartyRoleRequest.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/request/RegisterPartyRequest.java`
  - `src/main/java/dz/sh/hidra/modules/party/api/rest/response/PartyResponse.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/command/AssignPartyRoleCommand.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/command/RegisterPartyCommand.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/dto/PartySummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/mapper/PartyApplicationMapper.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/port/in/AssignPartyRoleUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/port/in/RegisterPartyUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRoleAssignmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/service/PartyApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/service/PartyRoleAssignmentApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/exception/BlockedPartySelectionException.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/exception/InvalidPartyValueException.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/exception/PartyDomainException.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/policy/PartyBoundaryPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/service/PartySelectionService.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyAddressType.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyCatalogStatus.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyCertificationStatus.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyContactPersonStatus.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyContactPointType.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyId.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRegistrationType.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRelationshipType.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyStatus.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/PartyInfrastructure.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/configuration/PartyModuleConfiguration.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/ExternalPartyMasterDataClient.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/NoopExternalPartyMasterDataClient.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/PartyPersistence.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRoleAssignmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyAddressJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyBankReferenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCertificationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyComplianceStatusJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPersonJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPointJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyDocumentReferenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyExternalReferenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyLegalProfileJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyOwnershipLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyQualificationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRegistrationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRelationshipJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRiskSnapshotJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyStatusHistoryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTaxIdentifierJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/mapper/PartyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyAddressJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyBankReferenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCatalogEntryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCatalogTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCertificationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyComplianceStatusJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyContactPersonJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyContactPointJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyDocumentReferenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyExternalReferenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyLegalProfileJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyOwnershipLinkJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyQualificationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRegistrationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRelationshipJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRiskSnapshotJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleAssignmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyStatusHistoryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTaxIdentifierJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTypeJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTypeTranslationJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_004__hmr_004_party_party.sql`
  - `src/test/java/dz/sh/hidra/modules/party/semantic/PartySemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PartySemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Required legal name:** the active Party DDD declares `legalName` required and the JPA/schema column is non-null, but the current domain constructor does not reject null/blank `legalName`. It normalizes blank input to `null`, allowing invalid Party state to exist until persistence failure.
  2. **Unique Party code:** the active Party DDD explicitly states `Party code must be unique`. The live schema has only a non-unique index on `code`, `PartyRepositoryPort` has no `findByCode`/`existsByCode` capability, and `PartyApplicationService.registerParty` performs no uniqueness check. The invariant therefore has no demonstrated application/database enforcement.

#### HMR-005 — telemetry.TelemetryPoint

- Source review: `HMSR-005`
- Exact commit: `fix(telemetry): remediate semantic review TelemetryPoint`
- Status: **Planned — HMR-005A completed.** Telemetry-owned compatibility metadata is now defined without hard-coded business taxonomy values. `SIGNAL_TYPE` catalog entries will carry nullable technical `valueShape` metadata constrained to `NUMERIC`, `TEXT`, or `BOOLEAN`; registration fails closed when a referenced SIGNAL_TYPE lacks that metadata. `POINT_TYPE` catalog entries will carry `numericUnitExempt` (default false); a NUMERIC signal requires an active `TelemetryUnit` unless the referenced POINT_TYPE explicitly sets that flag true. Non-SIGNAL_TYPE rows must keep `valueShape` null, and non-POINT_TYPE rows must keep `numericUnitExempt=false`. HMR-005 may now implement these rules additively.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryCatalogEntryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointBindingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointStateSnapshotJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TelemetryPointSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Mandatory French point name:** `nameFr` is required by the Telemetry DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank text to `null`.
  2. **Point-code uniqueness:** the Telemetry DDD requires point code uniqueness per device and explicitly recommends unique `(device_id, code)`. The live migration currently has separate non-unique indexes only, and the point repository/application service provides no demonstrated uniqueness check.
  3. **Catalog-family validation:** `pointTypeId`, `signalTypeId`, and `defaultAggregationMethodId` have specific catalog-family meanings (`POINT_TYPE`, `SIGNAL_TYPE`, `AGGREGATION_METHOD`). Existing generic catalog FKs, where present, prove only that a catalog row exists; the current point registration service does not demonstrate family/active-entry validation.
  4. **Unit reference integrity:** when `unitId` is present it semantically targets `TelemetryUnit`, but current point persistence does not demonstrate FK/application validation of that optional reference.
  5. **Signal/unit compatibility:** the DDD requires `signalTypeId` to determine compatible numeric/text/boolean reading shape and requires `unitId` for numeric engineering measurements unless explicitly exempted by point type. The current point registration service simply persists the supplied IDs and does not demonstrate this conditional validation.

#### HMR-005A — telemetry point signal/unit compatibility prerequisite

- Source: HMR-005 / HMSR-005 obligation 5.
- Exact commit: `docs(telemetry): define point signal-unit compatibility metadata`
- Status: **Completed** — defined Telemetry-owned compatibility metadata, fail-closed behavior, and the exact HMR-005 allowlist extension; no production mutation or catalog seed was performed.
- Type: documentation/design prerequisite only; no production mutation.
- Purpose: define the minimum Telemetry-owned metadata required to classify a SIGNAL_TYPE entry as numeric/text/boolean and to state whether a POINT_TYPE explicitly exempts numeric points from requiring a unit.
- Required evidence: live `docs/data definition/Telemetry.md`, live telemetry catalog/unit domain/JPA schema, existing seeds/provisioning evidence, and HMR-005/HMSR-005.
- Allowed files:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
- Acceptance:
  1. define metadata structurally without inventing concrete SIGNAL_TYPE or POINT_TYPE business entries;
  2. specify fail-closed behavior when required metadata is missing;
  3. register the exact production files/migration/tests that HMR-005 may modify after the design is approved;
  4. keep catalog-family ownership inside Telemetry;
  5. do not seed or infer business values in this documentation task.
- On completion, HMR-005 returns to Planned with its amended exact allowlist and validation commands.


#### HMR-006 — planning.PlanningPeriod

- Source review: `HMSR-006`
- Exact commit: `fix(planning): remediate semantic review PlanningPeriod`
- Status: **Completed** — strict interval, required French label, deterministic valid IANA time zone, unique code, active PERIOD_TYPE validation, and database constraints are enforced. Canonical PERIOD_TYPE vocabulary is reconciled to DAY/WEEK/MONTH/CAMPAIGN/OPERATION_WINDOW. CLOSED-period revision governance is exposed by the domain and explicitly carried into the later OperationalPlan/PlanRevision application remediation where the revision write actually occurs.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 7
- Additive Flyway: `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreatePlanningPeriodRequest.java`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningPeriodResponse.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/command/CreatePlanningPeriodCommand.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/dto/PlanningPeriodSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningPeriodJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanningPeriodSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Strict non-zero interval:** align the live invariant with the Planning DDD's `periodStart < periodEnd`; equality must not silently represent a valid planning horizon unless the target semantics are explicitly changed.
  2. **Unique period code:** the Planning DDD and required-constraints section explicitly require `unique(hidra_planning_period.code)`, while the live schema currently has only a normal index and the application/repository contract shows no uniqueness guard.
  3. **Canonical PERIOD_TYPE vocabulary:** resolve `DAILY/WEEKLY/MONTHLY` versus `DAY/WEEK/MONTH` before catalog/data provisioning. Do not map or seed by guesswork.
  4. **Catalog-family and active-entry validation:** the HRA-111 FK proves only that `periodTypeId` points to some planning catalog row. Creation must ensure it belongs to the intended `PERIOD_TYPE` family and satisfies the applicable active/reference policy.
  5. **Time-zone validity:** `timeZone` is a required IANA zone. The current service defaults only null values; blank input is normalized by the domain to null and arbitrary invalid zone strings can reach persistence because the column is plain varchar. Validation/defaulting semantics must be made deterministic.
  6. **Required French label boundary:** the Planning DDD and persistence contract require `nameFr`, while current domain creation can normalize blank/null to `null`. The eventual correction must ensure the required French label is enforced at an appropriate input/application/persistence boundary, without contradicting HRA's prohibition on blindly promoting all generic text to domain-constructor invariants.
  7. **Closed-period plan governance:** the Planning DDD states that closed periods cannot receive new plan revisions unless reopened by a workflow-approved action. This lifecycle rule must remain visible to the later `OperationalPlan`/`PlanRevision` reviews and be enforced at the owning application/workflow boundary rather than inferred by clients.

#### HMR-007 — identity.Role

- Source review: `HMSR-007`
- Exact commit: `fix(identity): remediate semantic review Role`
- Status: **Completed** — the general create-role application path normalizes the requested code, fails fast when `RoleRepositoryPort.findByCode` already resolves it, the focused application-service test proves no save occurs on duplicates, and additive Flyway enforces race-safe uniqueness on `hidra_identity_role(code)`.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_007__hmr_007_identity_role.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/ExternalRoleMappingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/GroupRoleGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RoleRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleType.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaExternalRoleMappingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaGroupRoleGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRolePermissionGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/ExternalRoleMappingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/GroupRoleGrantJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RoleJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RolePermissionGrantJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_007__hmr_007_identity_role.sql`
  - `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/RoleSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RoleSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique Role code:** the Identity DDD explicitly defines `Role.code` as unique and recommends `uk_identity_role_code`. The live Flyway schema has only a non-unique index `ix_hidra_identity_role_code`; no later unique constraint was found. The general create-role application path does not call `RoleRepositoryPort.findByCode` before save. `RoleJpaRepository.findFirstByCode` also tolerates duplicate rows rather than making duplicates impossible.

#### HMR-007A — identity Role uniqueness application scope amendment

- Source: HMR-007 / HMSR-007.
- Exact commit: `docs(model-remediation): amend Role uniqueness remediation scope`
- Status: **Completed**
- Type: documentation-only scope correction; no production mutation.
- Evidence:
  - `IdentityAdministrationCommandApplicationService#createRole` is the general create-role path and currently saves without checking `RoleRepositoryPort.findByCode`.
  - `IdentityAdministrationCommandApplicationServiceTest` is the existing focused unit test for that path.
- HMR-007 exact write allowlist is amended to additionally authorize:
  - `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java`
  - `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java`
- Acceptance:
  1. HMR-007 must fail fast in the application path when `Role.code` already exists;
  2. HMR-007 must still add race-safe database uniqueness on `hidra_identity_role(code)`;
  3. HMR-007 must not broaden into unrelated Identity role/grant semantics.


#### HMR-008 — documents.DocumentStorageObject

- Source review: `HMSR-008`
- Exact commit: `fix(documents): remediate semantic review DocumentStorageObject`
- Status: **Completed** — storage-provider identity is configurable and validated against an active `DOCUMENT_STORAGE_PROVIDER` entry before persistence; binary/storage-object metadata fails closed on missing checksum/integrity/creation data; object keys reject URI/signed-credential material; encryption-key metadata rejects credential-bearing values; additive database checks enforce nonnegative length, required metadata, and opaque object keys; failed provider validation deletes the newly written binary.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
  - `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentStorageObjectSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Storage-provider catalog identity mismatch / missing validation:** HRA-111 requires `storageProviderId` to resolve to `hidra_documents_catalog_entry(id)`, and the DDD assigns it to `DOCUMENT_STORAGE_PROVIDER`. The current local adapter returns the hard-coded value `local-filesystem`. No current seed/catalog evidence found during HMSR-008 proves that `local-filesystem` is an existing catalog-entry **ID** or that it belongs to the `DOCUMENT_STORAGE_PROVIDER` family. The upload path performs no catalog-family/active-entry validation before persistence.
  2. **Required storage-integrity metadata contract:** the DDD and schema require `contentType`, `checksumAlgorithm` and `checksumValue`. The current local adapter/service produce them, but `DocumentBinaryStoragePort.StoredBinary` has no constructor validation and the domain record itself does not reject null/blank checksum/content-type values. A future storage adapter can therefore satisfy the Java interface while producing semantically invalid metadata that fails only at persistence or propagates invalid state.
  3. **Required creation metadata boundary:** `createdAt` is required by the DDD/JPA/schema but is not guarded by the domain constructor. The current service supplies it, so this is not a current local-upload failure, but the target baseline needs one deliberate enforcement boundary rather than relying on every caller to remember the persistence constraint.
  4. **Secret/opaque-object-key policy:** the DDD explicitly says object keys must not contain credentials or signed URLs and encryption-key metadata must be reference-only. The current local adapter is safe because it derives `objectKey` from an opaque generated ID, but the outbound storage contract does not state or validate the same guarantee for other providers. Any S3/MinIO/SharePoint/DMS adapter must return stable non-secret metadata rather than pre-signed/credential-bearing values.

#### HMR-008A — documents storage-contract scope amendment

- Source: HMR-008 / HMSR-008.
- Exact commit: `docs(model-remediation): amend DocumentStorageObject remediation scope`
- Status: **Completed**
- Type: documentation-only scope correction; no production mutation.
- Evidence:
  - `DocumentBinaryStoragePort.StoredBinary` has no constructor validation for provider identity, object key, checksum algorithm/value, or opaque secret-free metadata.
  - `DocumentContentTransferService` trusts the returned binary metadata before creating `DocumentStorageObject`.
  - `LocalDocumentBinaryStorageAdapter` emits hard-coded `storageProviderId = "local-filesystem"`; HMSR-008 found no evidence that this literal is an existing Documents catalog-entry ID in the `DOCUMENT_STORAGE_PROVIDER` family.
- HMR-008 exact write allowlist is amended to additionally authorize:
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
  - `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- Acceptance:
  1. storage adapters must return nonblank provider identity, opaque non-secret object key, checksum algorithm/value, and valid nonnegative length;
  2. object keys and encryption-key metadata must reject credential-bearing/signed-URL material rather than merely documenting a convention;
  3. local storage must no longer rely on an unverified hard-coded catalog identity; its configured provider ID is still validated against active `DOCUMENT_STORAGE_PROVIDER` membership before persistence;
  4. HMR-008 remains responsible for domain `contentType`/checksum/`createdAt` integrity and additive persistence constraints;
  5. no storage-provider catalog seed/value may be invented in HMR-008.


#### HMR-009 — simulation.SimulationModel

- Source review: `HMSR-009`
- Exact commit: `fix(simulation): remediate semantic review SimulationModel`
- Status: **Completed** — unique code is enforced application-side and by database index; French name, governed topology scope type, and timestamps are intrinsic domain invariants; model type resolves to an active `SIMULATION_MODEL_TYPE`; supplied topology scope pairs use the dedicated Topology-owned Simulation contract; `PIPELINE_SYSTEM`/`PIPELINE` must exist and be ACTIVE, while supplied `SEGMENT_GROUP`/`FACILITY_NETWORK` references fail closed until Topology owns those representations.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationModelRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationModelResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationModelCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationModelSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationModelSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique model code:** the Simulation target definition explicitly marks `code` unique. The live schema has only non-unique `ix_hidra_simulation_model_code`, and the repository/application create path has no demonstrated uniqueness guard.
  2. **Mandatory French name:** `nameFr` is required by target semantics and `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank input to `null`.
  3. **Mandatory and governed topology scope type:** `topologyScopeType` is required by target semantics and JPA/schema but is not guarded by the domain/application path; no demonstrated validation restricts it to the documented scope vocabulary.
  4. **Topology scope pair validation:** when `topologyScopeId` is supplied, the application must validate `topologyScopeType + topologyScopeId` through a Topology-owned contract. No single subject target may be guessed and no current validation is demonstrated.
  5. **Model-type catalog-family validation:** HRA-111 proves only that `modelTypeId` points to some Simulation catalog row. Creation does not demonstrate that it belongs to `SIMULATION_MODEL_TYPE` or satisfies the applicable active/reference policy.
  6. **Required timestamps:** `createdAt` and `updatedAt` are required by persistence semantics but not guarded by the domain constructor. The current service supplies both; a deliberate enforcement boundary must remain part of the final baseline.

#### HMR-009A — simulation topology-scope owner-contract prerequisite

- Source: HMR-009 / HMSR-009 obligations 3 and 4 plus live owner-contract evidence.
- Exact commit: `docs(model-remediation): register SimulationModel topology owner-contract prerequisite`
- Status: **Completed** — live Topology evidence confirms authoritative `PipelineSystem` and `Pipeline` targets exist, while no `SegmentGroup` or `FacilityNetwork` aggregate/model exists. A dedicated Simulation-facing Topology application contract is therefore specified: it resolves `(scopeType, scopeId)` for currently authoritative target types and fails closed with an explicit unsupported result for `SEGMENT_GROUP` / `FACILITY_NETWORK` until Topology gains owned representations. No alias to `FACILITY`, `EQUIPMENT`, or another type is permitted.
- Type: documentation/architecture prerequisite; no production mutation.
- Purpose: define a Topology-owned public reference contract that can validate the exact Simulation scope vocabulary without importing Topology domain/persistence models into Simulation.
- Required evidence:
  - `docs/data definition/Simulation.md`
  - live `SimulationApplicationService#createSimulationModel`
  - live `TopologySnapshotLookupPort`
  - live `TopologyOperationalScopeTargetContract` and its implementation
  - live Topology domain/repository evidence for candidate scope concepts
- Required decisions:
  1. preserve or explicitly revise Simulation's documented scope vocabulary `PIPELINE_SYSTEM`, `PIPELINE`, `SEGMENT_GROUP`, `FACILITY_NETWORK`; do not map these to `FACILITY` or `EQUIPMENT` by guesswork;
  2. define the exact Topology-owned public input/contract needed to resolve `(scopeType, scopeId)` and return fail-closed existence/eligibility evidence;
  3. define adapter ownership so Simulation depends only on a public Topology application contract, never Topology repositories/domain/persistence directly;
  4. amend HMR-009's exact write allowlist to include `SimulationApplicationService.java`, the Simulation topology lookup port/adapter, the Topology public contract/service files if required, and focused tests;
  5. keep HMR-009 obligations 1, 2, 5, and 6 executable without inventing catalog or topology values.
- HMR-009 remains blocked until HMR-009A is completed.


#### HMR-010 — identity.IdentityProvider

- Source review: `HMSR-010`
- Exact commit: `fix(identity): remediate semantic review IdentityProvider`
- Status: **Completed** — provider code uniqueness is enforced before persistence and by a unique database index; provider name and timestamps are intrinsic domain invariants; active OIDC providers require an issuer and active OIDC issuer resolution is uniqueness-safe; at most one ACTIVE LOCAL provider and one ACTIVE LDAP/ACTIVE_DIRECTORY provider candidate may exist while inactive/deprecated history remains allowed.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/ExternalIdentityProviderClient.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/NoopExternalIdentityProviderClient.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/IdentityProviderJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IdentityProviderSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique provider code:** the Identity DDD explicitly requires `IdentityProvider.code` to be unique and recommends `uk_identity_provider_code`. The live schema has only non-unique `ix_hidra_identity_provider_code`; the application repository port has no code lookup/uniqueness contract.
  2. **Required provider name:** `name` is required by the Identity DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank values and normalizes blank input to `null`.
  3. **Unambiguous OIDC issuer resolution:** the live OIDC converter calls an `Optional` repository method keyed by `(ProviderType.OIDC, issuerUri)`, which assumes at most one matching provider. The schema has no demonstrated uniqueness constraint for that lookup. The current runtime therefore needs either explicit provisioning/application validation for an unambiguous active OIDC issuer mapping or a deliberately different provider-selection contract.
  4. **Provider-specific activation validation:** provider-specific fields are intentionally optional at the generic record level, but an ACTIVE provider must have the metadata required by its runtime path. At minimum, an OIDC provider used by the current converter requires a usable issuer URI; LOCAL and LDAP/AD active-provider cardinality must remain governed so ambiguous configuration fails closed. These checks belong in provider administration/provisioning/runtime validation rather than as unconditional constructor rules across all provider types.
  5. **Required timestamps:** `createdAt` and `updatedAt` are persistence-required but not guarded by the domain constructor. The final baseline must retain one deliberate enforcement boundary for provider creation/update metadata.

#### HMR-011 — identity.Permission

- Source review: `HMSR-011`
- Exact commit: `fix(identity): remediate semantic review Permission`
- Status: **Completed** — permission code uniqueness is enforced before persistence and by database index; canonical three-part lower-case codes and required domain/resource/action/timestamps are intrinsic invariants; `resourceType` is required consistently; only ACTIVE permissions contribute to effective grants or receive new active grants/delegations/mappings; disabled/deprecated permissions affect new resolutions and newly issued tokens, while already-issued stateless JWTs expire under the configured token TTL.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 7
- Additive Flyway: `src/main/resources/db/migration/V20261004_011__hmr_011_identity_permission.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/EvaluatePermissionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/PermissionDecisionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/dto/PermissionDecisionDto.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/PermissionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/query/EvaluatePermissionQuery.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaPermissionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRolePermissionGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalPermissionMappingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/ExternalPermissionMappingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/PermissionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RolePermissionGrantJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityEffectivePermissionSourceAdapter.java`
  - `src/main/resources/db/migration/V20261004_011__hmr_011_identity_permission.sql`
  - `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityControllerPermissionScopeApiTest.java`
  - `src/test/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapperPermissionScopeTest.java`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/PermissionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PermissionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique permission code:** enforce the DDD-defined global uniqueness of `Permission.code` at the application/database boundary.
  2. **Permission lifecycle enforcement:** effective authorization must not continue treating `DISABLED` or `DEPRECATED` permissions as active merely because their grants remain active.
  3. **Grant-time lifecycle validation:** define whether new grants/delegations may target non-ACTIVE permissions and enforce that rule consistently.
  4. **Permission-code documentation reconciliation:** update stale uppercase/dot and two-part Identity DDD examples to the live three-part lower-case colon standard, or explicitly change the live standard through an authorized decision.
  5. **`resourceType` contract reconciliation:** resolve the current DDD/schema optionality versus the live administration/route naming requirement for a non-empty resource component.
  6. **Required domain fields:** retain an explicit enforcement boundary for `permissionDomain`, `action`, `createdAt`, and `updatedAt` so alternate callers cannot persist semantically invalid Permission state.
  7. **Issued-token lifecycle policy:** define the expected effect of disabling/deprecating a permission on already-issued Hidra JWT scope claims; any immediate-revocation requirement must be implemented deliberately rather than assumed.

#### HMR-012 — notification.NotificationTemplate

- Source review: `HMSR-012`
- Exact commit: `fix(notification): remediate semantic review NotificationTemplate`
- Status: **Completed** — template code uniqueness is enforced at persistence and database boundaries; ACTIVE templates require a governed current ACTIVE version; request/message template selections fail closed on inactive templates or mismatched/ineligible versions; template/category catalog family membership and active default-channel integrity are enforced; French label/timestamps are intrinsic domain invariants.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 8
- Additive Flyway: `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateVersionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationTemplateSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique template code:** enforce the DDD-defined uniqueness of `NotificationTemplate.code` at the application/database boundary.
  2. **ACTIVE-only template selection:** request/message creation must validate that a template used for new message generation is ACTIVE; the current helper is not wired into orchestration.
  3. **Exact template-version integrity:** validate that `templateVersionId` exists, belongs to the selected `templateId`, and is eligible under the version lifecycle before rendering/sending.
  4. **`currentVersion` governance:** define and enforce the exact meaning of `currentVersion` and its consistency with template-version rows and template lifecycle.
  5. **Catalog-family validation:** `templateTypeId` must resolve to `TEMPLATE_TYPE`; when present, `categoryId` must resolve to `NOTIFICATION_CATEGORY`, not merely to any catalog row.
  6. **Optional channel-reference integrity:** when `defaultChannelId` is present and used, it must resolve to the intended NotificationChannel and satisfy the applicable active/availability policy.
  7. **French label persistence boundary:** reconcile the persistence-required `nameFr` with the domain constructor's nullable behavior without confusing label semantics with version-content localization.
  8. **Required timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required but not guarded by the record constructor.

#### HMR-013 — reporting.ReportDefinition

- Source review: `HMSR-013`
- Exact commit: `fix(reporting): remediate semantic review ReportDefinition`
- Status: **Completed** — `ReportDefinitionStatus` is now the sole persisted lifecycle representation; legacy `active()` is a derived compatibility projection and new definitions created through the existing service enter DRAFT. Code uniqueness, REPORT_CATEGORY family/activity, known owner-module values, French name/timestamps, current-template-version ownership/lifecycle, ACTIVE-only requests, restricted-policy scope gates, and approval-required run queueing are fail-closed. HRA-080 remains consistent because ReportDefinitionStatus is now independently persisted.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 11
- Additive Flyway: `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/CreateReportDefinitionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportDefinitionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/command/CreateReportDefinitionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportDefinitionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportDefinitionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Definition lifecycle representation:** reconcile the DDD/HRA-retained `ReportDefinitionStatus {DRAFT, ACTIVE, RETIRED}` with the live boolean `active` model/schema. The creation path must not silently skip DRAFT unless the target lifecycle is explicitly changed.
  2. **Unique definition code:** enforce global uniqueness of `ReportDefinition.code` at the application/database boundary.
  3. **ACTIVE-only request use:** `requestReport()` must verify that the selected definition is eligible for new requests under the chosen lifecycle representation.
  4. **Restricted-report access policy:** restricted definitions must be validated through explicit Reporting authorization/access-policy rules before request/access/generation paths proceed.
  5. **Approval governance:** definitions with `requiresApproval = true` must not be queued before the relevant request has Workflow-backed approval.
  6. **Template-version integrity:** `currentTemplateVersionId` and queued `templateVersionId` must resolve to valid Reporting template versions whose parent templates belong to the selected definition and satisfy the applicable lifecycle rule.
  7. **Category-family validation:** `reportCategoryId` must resolve specifically to an active/usable `REPORT_CATEGORY` entry, not merely to any Reporting catalog row.
  8. **Known owner module:** `ownerModule` must be validated against the authoritative Hidra module catalog/contract.
  9. **Required French name:** reconcile DDD/JPA/schema-required `nameFr` with the domain constructor's nullable behavior at a deliberate validation boundary.
  10. **Required timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`.
  11. **HRA-080 documentation consistency:** final reconciliation must correct the architecture statement that ReportDefinition status values are persisted independently if the final model does not actually persist `ReportDefinitionStatus`.

#### HMR-014 — integration.IntegrationJobRun

- Source review: `HMSR-014`
- Exact commit: `fix(integration): remediate semantic review IntegrationJobRun`
- Status: **Completed** — run numbers are database-allocated per job through a concurrency-safe sequence trigger and unique key; new-run persistence validates active job definitions, current JOB_TYPE metadata, automated connector readiness, IMPORT/SYNC mapping prerequisites, and MANUAL actor/permission governance; counters and completion ordering are guarded in domain/database; terminal lifecycle transitions are monotonic at repository/database boundaries; audit timestamps remain explicitly enforced at persistence.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 8
- Additive Flyway: `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunStepJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrationJobRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Authoritative monotonic run number:** stop treating `runNumber` as an unchecked caller-owned value; allocate/validate it per job in a concurrency-safe application/database design.
  2. **Job-definition eligibility:** before starting a run, load/validate the parent job definition and enforce its `active` state plus applicable connector/mapping/target prerequisites.
  3. **Manual-run governance:** enforce `manualRunAllowed` for `MANUAL` triggers and define/enforce actor provenance for manual execution.
  4. **Non-negative counters:** enforce non-negative `received`, `mapped`, `accepted`, `rejected`, `deadLetter`, and `retry` counts.
  5. **Counter consistency:** enforce the DDD aggregate-count rule, while preserving an explicit exception only where multi-record expansion is intentionally modeled.
  6. **Completion-time ordering:** reject `completedAt < startedAt`.
  7. **Monotonic lifecycle transitions:** terminal runs must not return to `RUNNING`; completion/failure/cancellation orchestration must update state through a transition-aware boundary.
  8. **Required audit timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required.

#### HMR-015 — leakdetection.LeakCandidate

- Source review: `HMSR-015`
- Exact commit: `fix(leakdetection): remediate semantic review LeakCandidate`
- Status: **Completed** — required topology type and typed owner validation are enforced through the dedicated Topology-owned Leak Detection contract; persisted code/name snapshots come from Topology and caller code mismatches fail closed. Runless candidates remain valid, while candidate creation requires an ACTIVE profile and any supplied run must exist, match the profile, and be RUNNING or COMPLETED; additive Flyway adds the nullable same-module run FK and database provenance trigger. Candidate severity is classifier-derived from confidence and contradictory persisted pairs are rejected. Required audit timestamps are intrinsic domain invariants. External CPM/gRPC candidate creation remains gated behind deferred EXT-011/EXT-012 because the live repository does not yet define a stable anomaly identity/uniqueness scope; HMR-015 deliberately does not invent a candidate-number uniqueness rule.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 7
- Additive Flyway: `src/main/resources/db/migration/V20261004_015__hmr_015_leakdetection_leak_candidate.sql`
- Owner-contract prerequisite: **HMR-015A completed.** Use the dedicated Topology-owned `LeakDetectionTopologyAssetContract`; do not use `LeakDetectionExternalReferenceResolver`/`NoopLeakDetectionExternalReferenceResolver` as authority and do not introduce a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/LeakDetection.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/request/CreateLeakCandidateRequest.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/response/LeakCandidateResponse.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/command/CreateLeakCandidateCommand.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/dto/LeakCandidateSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/dto/LeakCandidateView.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/in/CreateLeakCandidateUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakCandidateRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/service/LeakDetectionApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakCandidateStatus.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakCandidateRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/mapper/LeakDetectionPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakCandidateJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaLeakDetectionTopologyAssetContractAdapter.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
  - `src/main/resources/db/migration/V20261004_015__hmr_015_leakdetection_leak_candidate.sql`
  - `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakCandidateSemanticRemediationTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaLeakDetectionTopologyAssetContractAdapterTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=LeakCandidateSemanticRemediationTest,JpaLeakDetectionTopologyAssetContractAdapterTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Required topology type:** enforce persistence-required `topologyAssetType` at a deliberate domain/application boundary.
  2. **Typed Topology validation:** validate `topologyAssetType + topologyAssetId` through a Topology-owned contract and govern the code/name snapshots without creating cross-module ownership.
  3. **Run reference integrity:** define whether runless candidates are valid; when `runId` is present, validate it against `LeakDetectionRun` and add/retain an appropriate integrity mechanism.
  4. **Profile/run provenance consistency:** when a run is supplied, enforce that its profile matches candidate `profileId` and that the relevant profile/run lifecycle permits candidate generation.
  5. **External-compute idempotency:** define the stable candidate/anomaly identity and uniqueness scope before CPM/gRPC ingestion so reconnect/replay cannot create uncontrolled duplicates.
  6. **Confidence/severity authority:** explicitly define whether severity is always derived from confidence or can be independently assessed, then prevent contradictory persisted pairs under the chosen rule.
  7. **Required audit timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required.

#### HMR-015A — LeakCandidate Topology owner-contract prerequisite

- Source: HMR-015 / HMSR-015 obligations 1 and 2 plus live Topology owner evidence.
- Exact commit: `docs(model-remediation): register LeakCandidate topology owner-contract prerequisite`
- Status: **Completed** — live Topology evidence confirms Leak Detection may reference five owner concepts named by its DDD: Pipeline, PipelineSegment, Facility, TopologyNode, and Equipment. A dedicated Leak Detection-facing Topology application contract is specified for those exact target types; it returns owner-native ID/code/name evidence and distinguishes unsupported from missing targets. No lifecycle eligibility rule is invented because HMSR-015 does not establish one.
- Type: documentation/architecture prerequisite; no production mutation.
- Purpose: define a Topology-owned public reference contract that lets Leak Detection validate `topologyAssetType + topologyAssetId` and govern code/name snapshots without importing Topology domain/persistence models or creating a cross-module database FK.
- Required evidence:
  - `docs/data definition/LeakDetection.md`
  - live `LeakCandidate`, `LeakDetectionApplicationService`, and Leak Detection external-reference resolver evidence
  - live `TopologyOperationalScopeTargetContract` / `TopologyOperationalScopeTargetQueryService`
  - live `SimulationTopologyScopeContract` / `TopologySimulationScopeQueryService`
  - live Topology JPA/entity evidence for Pipeline, PipelineSegment, Facility, TopologyNode, and Equipment
  - live architecture export guardrails
- Required decisions:
  1. the contract supports exactly `PIPELINE`, `PIPELINE_SEGMENT`, `FACILITY`, `TOPOLOGY_NODE`, and `EQUIPMENT`, because those are the Topology objects explicitly named by the Leak Detection DDD; `PIPELINE_SYSTEM`, `MEASUREMENT_LOCATION`, and any other type are unsupported until Leak Detection semantics explicitly adopt them;
  2. input is `(assetType, assetId)`; resolution returns `supported`, `exists`, owner-native `id`, current `code`, and current display `name` when available;
  3. HMR-015 must fail closed on unsupported or missing targets; when Topology supplies a code, a caller-supplied code mismatch must be rejected and the persisted code/name snapshots must be owner-governed rather than blindly trusted;
  4. the contract lives under `topology.application.contract.leakdetection`; its implementation remains Topology-owned and may use Topology persistence internally, while Leak Detection depends only on the public contract;
  5. HMR-015 must not use `LeakDetectionExternalReferenceResolver` or its current no-op implementation as authoritative topology validation;
  6. the cross-module package `dz.sh.hidra.modules.topology.application.contract.leakdetection` must be registered in both repository architecture guardrails;
  7. no cross-module database FK is permitted for the polymorphic Topology reference;
  8. HMR-015's exact allowlist and focused validation are amended above to authorize the application service, Topology contract/adapter, architecture guardrails, and focused owner-contract test needed to execute obligations 1 and 2.
- HMR-015 remains the current next production task. Do not start HMR-016 automatically.


#### HMR-016 — analytics.MetricEvaluationRun

- Source review: `HMSR-018`
- Exact commit: `fix(analytics): remediate semantic review MetricEvaluationRun`
- Status: **Completed** — nonblank scope type is a domain invariant; current Analytics starts validate owner-backed Topology/Organization scopes through dedicated public contracts and fail closed for unsupported types; all current supported scopes require IDs. Metric-definition versions must exist and contain the requested evaluation period. The use case exposes governed terminal finalization for COMPLETED, COMPLETED_WITH_WARNINGS, FAILED and CANCELLED, preserves counters/diagnostics/correlation, requires completion timestamps, and prevents already-terminal outcomes from being changed or reopened. FAILED runs retain diagnostic or correlation evidence. No cross-module scope FK or unsupported scope owner was invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
- Owner-contract prerequisite: **HMR-016A completed.** Use dedicated Analytics-facing owner contracts for Topology and Organization behind an Analytics-owned resolver. Do not import foreign repositories/domain internals and do not create cross-module database FKs.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/MetricEvaluationRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/dto/MetricEvaluationRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/command/FinalizeMetricEvaluationRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/reference/AuthoritativeMetricEvaluationScopeResolverAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapter.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricEvaluationRunJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MetricEvaluationRunSemanticRemediationTest,JpaAnalyticsOrganizationScopeContractAdapterTest,JpaAnalyticsTopologyScopeContractAdapterTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Required scope discriminator:** enforce nonblank `scopeType` at a deliberate domain/application boundary so invalid runs do not survive until database persistence.
  2. **Typed scope validation:** validate the `scopeType + scopeId` pair through a neutral Analytics/owning-module lookup contract while preserving foreign-module ownership and allowing explicitly identifier-less scope types where defined.
  3. **Metric-version eligibility:** before starting calculation, resolve `metricDefinitionVersionId` and enforce whatever version-validity/active-calculation rule is authoritative for the requested period; FK existence alone is insufficient.
  4. **Lifecycle completion/failure orchestration:** provide a governed path for terminal run outcomes so `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, and `CANCELLED` can preserve `completedAt`, diagnostics, counters and correlation/audit evidence.
  5. **Failed-run auditability:** ensure failure execution records retain enough diagnostic/correlation evidence to satisfy the DDD rule without inventing unsupported constructor-level text requirements.

#### HMR-016A — MetricEvaluationRun scope and lifecycle prerequisite

- Source: HMR-016 / HMSR-018 obligations 2, 4 and 5 plus live Analytics, Topology, Organization and architecture evidence.
- Exact commit: `docs(model-remediation): register MetricEvaluationRun scope and lifecycle prerequisite`
- Status: **Completed** — the missing write scope and owner boundaries are now registered; no production mutation is part of HMR-016A.
- Type: documentation/architecture prerequisite.
- Purpose: authorize the exact Analytics-owned neutral scope resolver, owner-facing contracts, application finalization path and guardrail exports required to satisfy HMSR-018 without importing foreign internals or inventing unsupported scope semantics.
- Required decisions:
  1. `MetricEvaluationScopeResolverPort` is Analytics-owned and receives `scopeType + scopeId`; it reports whether a type is supported, whether an ID is required, and whether the owner target resolves. Unsupported types fail closed.
  2. Current owner-backed support is limited to evidence-backed types:
     - Topology: `PIPELINE_SYSTEM`, `PIPELINE`, `PIPELINE_SEGMENT`, `FACILITY`, `EQUIPMENT`;
     - Organization: `ORGANIZATION_UNIT`.
     Other DDD examples such as `PRODUCT`, `NETWORK`, `STATION`, `MEASUREMENT_POINT`, `CUSTODY_TRANSFER_POINT`, `HSE_SITE` and `RISK_AREA` remain fail-closed until an authoritative owner contract is separately established.
  3. No identifier-less Analytics scope type is established by current repository evidence. HMR-016 must therefore require `scopeId` for every currently supported type while keeping the neutral resolver capable of representing a future explicitly targetless type without changing the run model.
  4. Topology exposes a dedicated `topology.application.contract.analytics.AnalyticsTopologyScopeContract`; Organization exposes a dedicated `organization.application.contract.analytics.AnalyticsOrganizationScopeContract`. Analytics depends only on those public owner contracts through its infrastructure resolver. The architecture guardrails must register both exported packages.
  5. No cross-module database FK is permitted for `scopeId`.
  6. Metric-version eligibility is resolved inside Analytics before a new run is persisted: the version must exist and the requested period must lie within its optional `validFrom` / `validTo` interval, using inclusive boundaries. The review establishes no separate version-status field, so HMR-016 must not invent one.
  7. `MetricEvaluationUseCase` is amended with one governed finalization operation backed by `FinalizeMetricEvaluationRunCommand`. The command may target only `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED` or `CANCELLED` and carries counters/diagnostics/correlation evidence needed to preserve the existing run fields.
  8. HMR-016 must not invent a complete transition matrix. It must only prevent changing/reopening an already terminal run and prevent finalization to a non-terminal status.
  9. Terminal finalization sets/preserves `completedAt`; FAILED finalization must retain at least diagnostic or correlation evidence, while no individual error text/code is made universally mandatory.
  10. The HMR-016 allowlist and focused validation above are amended to authorize exactly the application service/use-case/command, neutral resolver, owner contracts/adapters, architecture guardrails and owner-contract tests needed for these obligations.
- HMR-016 remains the current next production task. Do not start HMR-017 automatically.


#### HMR-017 — configuration.ConfigurationDefinition

- Source review: `HMSR-019`
- Exact commit: `fix(configuration): remediate semantic review ConfigurationDefinition`
- Status: **Completed** — `ConfigurationDefinition.defaultValue` now passes through the same existing `ConfigurationValueGuard.ensureNoSecretMaterial(...)` boundary used for effective raw values at every repository save, covering create and update without inventing a vault URI/reference syntax or new detector. `SECRET_REFERENCE_ONLY` therefore remains reference-only with respect to the established Configuration secret-material policy.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: HMR-042A registered — a new immutable migration is required to introduce/backfill the Pipeline classification catalog and replace the legacy enum/string column.
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Configuration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/CreateConfigurationDefinitionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/ConfigurationDefinitionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/command/CreateConfigurationDefinitionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/dto/ConfigurationDefinitionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateConfigurationDefinitionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationDefinitionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationDefinitionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationDefinitionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/mapper/ConfigurationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationDefinitionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationDefinitionVersionJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationDefinitionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ConfigurationDefinitionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Apply the secret-reference boundary to definition defaults:** `ConfigurationDefinition.defaultValue` must not provide a path for persisting secret material that is forbidden for `ConfigurationValue.rawValue`. At minimum, creation/update of `SECRET_REFERENCE_ONLY` definitions and any general definition-default validation must consistently preserve reference-only secret semantics.

#### HMR-018 — custody.CustodyMeasurementPeriod

- Source review: `HMSR-020`
- Exact commit: `fix(custody): remediate semantic review CustodyMeasurementPeriod`
- Status: **Completed** — every `CustodyMeasurementPeriod` save resolves the selected Custody-owned `CustodyAgreement` and requires its mandatory `transferPointId` to equal the period `transferPointId`. PostgreSQL independently enforces the same relationship through a composite `(agreement_id, transfer_point_id)` foreign key to the agreement row. No agreement-status, agreement-validity, or period-transition rule was invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Custody.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyMeasurementPeriodRequest.java`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyMeasurementPeriodResponse.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyMeasurementPeriodSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyMeasurementPeriodJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
  - `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=CustodyMeasurementPeriodSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce agreement/transfer-point coherence when opening or persisting a measurement period.** A `CustodyMeasurementPeriod` must not reference a `CustodyAgreement` whose mandatory `transferPointId` identifies a different transfer point from the period's own `transferPointId`. The correction must fail closed at an appropriate Custody-owned application/domain/persistence boundary; HMSR-020 does not prescribe a repository-port shape or a composite database constraint without a separately authorized implementation task.

#### HMR-019 — integrity.PipelineDefect

- Source review: `HMSR-021`
- Exact commit: `fix(integrity): remediate semantic review PipelineDefect`
- Status: **Completed** — when `PipelineDefect.sourceFindingId` is non-null, Integrity persistence now requires the referenced `InspectionFinding` to exist before save, and PostgreSQL independently enforces the same nullable same-module provenance through an additive FK. Null provenance remains valid. No reciprocal `InspectionFinding.linkedDefectId`, finding-status, Topology, defect-number uniqueness, lifecycle, or timing rule was invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_019__hmr_019_integrity_pipeline_defect.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Integrity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaPipelineDefectRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/PipelineDefectJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_019__hmr_019_integrity_pipeline_defect.sql`
  - `src/test/java/dz/sh/hidra/modules/integrity/semantic/PipelineDefectSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PipelineDefectSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect non-null source-finding provenance:** when `PipelineDefect.sourceFindingId` is supplied, it must resolve to an existing Integrity-owned `InspectionFinding` rather than allowing a dangling same-module provenance ID. The correction may be enforced through an appropriate Integrity application/persistence boundary and/or additive FK in a separately authorized implementation task; HMSR-021 does not prescribe the implementation shape here.

#### HMR-020 — organization.Position

- Source review: `HMSR-022`
- Exact commit: `fix(organization): remediate semantic review Position`
- Status: **Completed** — canonical `Position.level` remains mandatory; JPA marks `level` non-null and the additive migration always blocks new NULL levels. On the greenfield baseline (no legacy-null rows) it upgrades the column to physical `NOT NULL`; on historical compatibility-test paths containing pre-existing NULL levels, it retains an unvalidated `CHECK (level IS NOT NULL)` so no semantic level is guessed while all new/updated rows must satisfy the canonical invariant. No code uniqueness, multilingual completeness, assignment-status eligibility, authorization, or lifecycle rule was invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_020__hmr_020_organization_position.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/PositionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionLevel.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaPositionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/PositionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_020__hmr_020_organization_position.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/PositionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PositionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Align Position level nullability across domain and persistence.** Because the canonical Position domain model requires `PositionLevel`, the JPA mapping and final schema must not admit `NULL` `level` values unless a separately approved semantic change makes Position level optional. The correction must use an additive migration and appropriate persistence/JPA alignment in a separately authorized Organization task; HMSR-022 does not modify applied migrations or production code.

#### HMR-021 — organization.Shift

- Source review: `HMSR-023`
- Exact commit: `fix(organization): remediate semantic review Shift`
- Status: **Completed** — canonical `Shift` construction now requires nonblank `startTime`, `endTime`, and `timezone`, matching the existing non-null JPA/schema contract. The additive migration also rejects blank schedule/timezone strings so database state remains reconstructible by the domain model. No clock-format parsing, timezone vocabulary, start/end ordering, overnight-shift rule, type-specific schedule rule, code uniqueness, assignment eligibility, or lifecycle transition was invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_021__hmr_021_organization_shift.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftAssignmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftType.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftAssignmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ShiftAssignmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ShiftJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_021__hmr_021_organization_shift.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ShiftSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Align `startTime`, `endTime`, and `timezone` nullability across the Shift domain and persistence contract.** The current final schema/JPA contract requires all three values, while canonical domain construction permits them to normalize to `null`. A separately authorized Organization correction task must either enforce the established non-null persistence semantics before save/domain construction, or explicitly change the target persistence semantics if a documented business decision makes any field optional. HMSR-023 does not prescribe clock parsing, timezone syntax, overnight handling, or type-specific scheduling rules.

#### HMR-022 — topology.PipelineSystem

- Source review: `HMSR-024`
- Exact commit: `fix(topology): remediate semantic review PipelineSystem`
- Status: **Completed** — dedicated Topology-owned PipelineSystem classification catalog/reference implemented; the six evidence-backed legacy codes are migrated, the enum-style `system_type` representation is retired, and missing/unknown create classification now fails closed without silent `TRANSPORT` defaulting.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
- Owner-contract prerequisite: No cross-module owner contract is required; the classification catalog remains Topology-owned. HMR-022A is the local architecture/persistence prerequisite.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemTypeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PipelineSystemSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Reconcile PipelineSystem business classification with the accepted Topology catalog policy.** Replace or explicitly redesign the current fixed `PipelineSystemType/system_type` representation so the final PipelineSystem contract is consistent with the repository's catalog-backed business-type architecture. The correction must define the authoritative catalog/reference semantics rather than blindly assuming `ProductTypeReference`; domain, application, REST, JPA and additive Flyway changes must remain aligned in the separately authorized Topology task.
  2. **Remove the unsupported silent `TRANSPORT` inference.** Missing PipelineSystem classification must not be converted to `TRANSPORT` unless an explicit Topology business rule establishes that default. The corrected write boundary should fail closed or use the explicitly approved classification/catalog contract.

#### HMR-022A — PipelineSystem classification-catalog prerequisite

- Source: HMR-022 / HMSR-024 plus live Topology production and Flyway evidence.
- Exact commit: `docs(model-remediation): register PipelineSystem classification catalog prerequisite`
- Status: **Completed** — the missing local catalog/persistence scope is registered; no production mutation is part of HMR-022A.
- Type: documentation/architecture prerequisite.
- Purpose: reconcile HMSR-024 with the actual repository rather than the stale historical Topology roadmap claims that generic type-catalog migrations/classes already exist.
- Live evidence:
  1. the production Topology tree contains no `TopologyCatalog*` domain/application/repository implementation and no `*TypeReference` production classes;
  2. the migration directory contains only the base Topology migration `V20260611_004__create_topology_tables.sql`; the historical roadmap references to `V003/V004/V005` catalog migrations are not live repository artifacts;
  3. concrete FacilityType/EquipmentType persistence exists, but neither concept semantically represents PipelineSystem transportation-system classification;
  4. `PipelineSystemType` remains a fixed enum persisted in `hidra_topology_pipeline_system.system_type`;
  5. the current create service silently maps missing classification to `TRANSPORT`.
- Required decisions:
  1. PipelineSystem classification is a **dedicated Topology-owned catalog**, not `ProductType`, `FacilityType`, or another existing business concept.
  2. Reuse the existing `PipelineSystemType.java` path as the domain **catalog reference value** rather than keeping it as an enum. The reference carries stable catalog `id`, `code`, and optional Arabic/French/English labels; `id` and `code` are mandatory, labels are optional.
  3. The new persistence catalog table is `hidra_topology_pipeline_system_type`, backed by `PipelineSystemTypeJpaEntity` and `PipelineSystemTypeJpaRepository`.
  4. Initial catalog entries are derived only from the six existing canonical enum codes: `TRANSPORT`, `GATHERING`, `DISTRIBUTION`, `EXPORT`, `IMPORT`, `MIXED`. Their initial catalog IDs equal their canonical codes so migration does not invent a second identity mapping. No localized labels are guessed; label columns remain nullable until authoritative master data is provisioned.
  5. The additive migration creates the catalog, preflights any existing `system_type` values against those six codes, backfills `system_type_id`, creates the same-module FK, makes the new reference mandatory, and retires the old enum-style `system_type` column. Existing migrations remain immutable.
  6. REST/application create input becomes a required stable `systemTypeCode` string. Missing/blank or unknown codes fail closed; no default classification is permitted.
  7. `PipelineSystemApplicationService` resolves the supplied code through `PipelineSystemRepositoryPort`; persistence independently validates the resolved catalog reference before save.
  8. Summary/response contracts expose the catalog reference semantics rather than a Java enum. Optional labels may be null until authoritative translations exist.
  9. HMR-022 must not recreate the historical generic Topology catalog architecture, add unrelated catalog tables, or claim those stale roadmap migrations/classes exist.
  10. HMR-022's allowlist is amended above to authorize exactly the new PipelineSystem classification entity/repository and additive migration required for this correction.
- HMR-022 remains the next production remediation. Do not start HMR-023 automatically.


#### HMR-023 — analytics.AnalyticsInsight

- Source review: `HMSR-025`
- Exact commit: `fix(analytics): remediate semantic review AnalyticsInsight`
- Status: **Completed** — required classification/scope semantics enforced; INSIGHT_TYPE and ANALYTICS_SEVERITY catalog references fail closed; populated projection-snapshot, trend-analysis and model-run lineage is protected in application persistence and by additive same-module database constraints.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/request/CreateAnalyticsInsightRequest.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsInsightResponse.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/command/CreateAnalyticsInsightCommand.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsInsightSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightEvidenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AnalyticsInsightSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require a nonblank, governed insight classification at the domain/application boundary.** `insightType` must not normalize to null and must resolve according to the Analytics-owned `INSIGHT_TYPE` catalog contract rather than accepting arbitrary free text.
  2. **Require nonblank `scopeType` consistently with the analytical-scope contract and final persistence schema.** HMSR-025 does not prescribe which scope types require `scopeId`; that mapping needs its own owner-resolved scope policy.
  3. **Protect optional severity semantics.** When `severityId` is supplied, it must resolve to the appropriate Analytics severity catalog entry/family rather than allowing a dangling or unrelated catalog ID.
  4. **Protect populated direct source lineage.** Each non-null `sourceProjectionSnapshotId`, `sourceTrendAnalysisId`, and `sourceModelRunId` must resolve to the corresponding Analytics-owned source record. The correction may use application validation, additive same-module FKs where appropriate, or another fail-closed Analytics-owned boundary in a separately authorized implementation task.

#### HMR-024 — analytics.AnalyticsProjectionRun

- Source review: `HMSR-026`
- Exact commit: `fix(analytics): remediate semantic review AnalyticsProjectionRun`
- Status: **Completed** — FAILED runs retain diagnostics, successful runs retain source watermarks, and every persisted run captures an immutable projection-definition computation version.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql` — authorized by HMR-024A.
- Owner-contract prerequisite: No cross-module owner contract is required; HMR-024A is the local reproducibility/persistence prerequisite.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsProjectionRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsProjectionRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/mapper/AnalyticsApplicationMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsProjectionRunJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AnalyticsProjectionRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Preserve terminal failure diagnostics.** A run persisted as `FAILED` must retain meaningful error context, as required by Analytics DDD. The implementation task must define the accepted error-code/message contract rather than silently allowing both to be absent.
  2. **Preserve successful-run source lineage.** Statuses classified by Analytics as successful must require a nonblank `sourceWatermark`, consistent with the DDD. The implementation task must explicitly define the successful-status set rather than relying on implicit interpretation.
  3. **Make projection runs reproducible across definition changes.** AnalyticsProjectionRun must durably identify the immutable computation/definition version that produced the run, or an equivalent immutable execution snapshot/audit version, because Analytics DDD requires definition versioning when formula changes and rebuildability from source truth/history.


#### HMR-024A — AnalyticsProjectionRun reproducibility prerequisite

- Source: HMR-024 / HMSR-026 plus live Analytics persistence evidence.
- Exact commit: `docs(model-remediation): register AnalyticsProjectionRun reproducibility prerequisite`
- Status: **Completed** — the missing durable computation-version persistence scope is registered; no production mutation is part of HMR-024A.
- Type: documentation/architecture prerequisite.
- Purpose: resolve HMR-024's durable projection-definition version obligation without overloading `correlationId`, `sourceWatermark`, or another field with unrelated semantics.
- Live evidence:
  1. `hidra_analytics_projection_run` persists only `projection_definition_id`; it has no immutable computation/version reference;
  2. `hidra_analytics_projection_definition` contains mutable computation fields including `projection_type`, `calculation_policy`, `refresh_policy`, `retention_policy`, `active`, and `updated_at`;
  3. no live `AnalyticsProjectionDefinitionVersion` table/model or equivalent durable run snapshot exists;
  4. HMR-024 was registered with no additive migration, so obligation 3 cannot be completed honestly under the original allowlist;
  5. `sourceWatermark` is reserved for source-read lineage and `correlationId` remains a technical correlation identity; neither may be repurposed as the definition version.
- Required decisions:
  1. The successful projection-run statuses are exactly `COMPLETED` and `COMPLETED_WITH_WARNINGS`; both require a nonblank `sourceWatermark`.
  2. A `FAILED` run must retain at least one nonblank diagnostic field: `errorCode` or `errorMessage`. HMR-024 does not require both.
  3. Each persisted run must carry a dedicated immutable `projectionDefinitionVersion` value. It is a reproducibility fingerprint/reference, not a new business aggregate identity.
  4. On first persistence, the repository adapter resolves the referenced definition and derives `projectionDefinitionVersion` from the definition's `updated_at` plus a deterministic fingerprint of the computation-relevant definition fields. Subsequent saves preserve the run's original captured value even if the definition changes.
  5. The additive migration `V20261004_024__hmr_024_analytics_projection_run.sql` adds the dedicated `projection_definition_version` column, backfills greenfield-existing rows from their currently referenced definition, makes the value mandatory, and adds fail-closed checks for FAILED diagnostics and successful source watermarks. Existing migrations remain immutable.
  6. HMR-024 may amend `AnalyticsProjectionRun`, its JPA entity/repository/adapter/persistence mapper, `AnalyticsApplicationMapper`, summary/response contracts, focused tests, Analytics DDD, and the new additive migration only as registered above.
  7. The existing application start path may continue constructing a RUNNING domain object without a captured version; the persistence adapter captures the version before durable save and returns the enriched persisted model. No new lifecycle transition use case is invented.
  8. HMR-024 must not create an `AnalyticsProjectionDefinitionVersion` aggregate/table, repurpose technical fields, invent run-mode rules, or add unrelated timestamp/counter invariants.
- HMR-024 remains the next production remediation. Do not start HMR-025 automatically.


#### HMR-025 — analytics.DigitalTwinReadinessAssessment

- Source review: `HMSR-027`
- Exact commit: `fix(analytics): remediate semantic review DigitalTwinReadinessAssessment`
- Status: **Completed** — scopeType is required/nonblank and DigitalTwinReadinessStatus is the single authoritative bounded readiness vocabulary across domain/JPA/schema; the conflicting READINESS_STATUS catalog claim is retired.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/DigitalTwinReadinessAssessmentJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DigitalTwinReadinessAssessmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require a nonblank analytical scope discriminator.** `scopeType` must be consistently mandatory at the domain/application boundary, matching the Analytics scope contract and final JPA/schema requiredness. HMSR-027 does not prescribe which scope types require `scopeId`.
  2. **Reconcile readiness-status representation.** Resolve the contradiction between DDD `READINESS_STATUS` catalog vocabulary and the current fixed `DigitalTwinReadinessStatus` enum/JPA representation. The implementation task must establish one authoritative controlled-vocabulary strategy across domain, persistence and future API/application contracts rather than leaving two competing semantics.

#### HMR-026 — configuration.FeatureFlag

- Source review: `HMSR-028`
- Exact commit: `fix(configuration): remediate semantic review FeatureFlag`
- Status: **Completed** — owningModule is required/nonblank at the application command, domain aggregate, and final database boundary.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_026__hmr_026_configuration_feature_flag.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Configuration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/CreateFeatureFlagRequest.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/FeatureFlagResponse.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/command/CreateFeatureFlagCommand.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/dto/FeatureFlagSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateFeatureFlagUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/FeatureFlagRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagEvaluationStrategy.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagStatus.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaFeatureFlagRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagRuleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/mapper/ConfigurationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/FeatureFlagJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/FeatureFlagRuleJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_026__hmr_026_configuration_feature_flag.sql`
  - `src/test/java/dz/sh/hidra/modules/configuration/semantic/FeatureFlagSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=FeatureFlagSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require a nonblank owning module consistently at the domain/application boundary.** `FeatureFlag.owningModule` must not normalize to null because the final persistence contract requires it and it provides the bounded-context ownership/routing identity for the governed runtime toggle. The separately authorized Configuration correction task should align domain/application validation with JPA/schema semantics without turning Configuration into the owner of the target module's business taxonomy.

#### HMR-027 — custody.CustodyDiscrepancy

- Source review: `HMSR-029`
- Exact commit: `fix(custody): remediate semantic review CustodyDiscrepancy`
- Status: **Completed** — optional quantityUnitId now has additive same-module FK protection to CustodyCatalogEntry; null remains allowed and no catalog-family rule is invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Custody.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyDiscrepancyRequest.java`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyDiscrepancyResponse.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyDiscrepancySummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyDiscrepancyJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
  - `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=CustodyDiscrepancySemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated quantity-unit references.** When `CustodyDiscrepancy.quantityUnitId` is non-null, it must resolve to the Custody-owned controlled-value/catalog target rather than allowing a dangling ID. A separately authorized Custody correction may enforce this through application validation, an additive nullable same-module FK, or another fail-closed Custody-owned boundary. HMSR-029 does not prescribe a catalog-family name that current DDD evidence does not define.

#### HMR-028 — organization.ReportingLine

- Source review: `HMSR-031`
- Exact commit: `fix(organization): remediate semantic review ReportingLine`
- Status: **Completed** — reporting-line classification is catalog-backed, future typed subjects fail closed, employee ACTIVE eligibility is enforced, employee-source active LINE cardinality is protected, and active LINE cycles are rejected across generalized typed subjects.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_028__hmr_028_organization_reporting_line.sql` — authorized by HMR-028A.
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ReportingLineRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaReportingLineRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ReportingLineJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ReportingLineTypeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_028__hmr_028_organization_reporting_line.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ReportingLinePersistenceMapperTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/ReportingLineSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportingLineSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Reconcile ReportingLineType with the accepted catalog architecture.** Replace or explicitly redesign the current fixed `ReportingLineType` enum/string persistence according to the existing `TYPE_ENUM_TO_CATALOG` decision, including an authoritative reporting-line type catalog/reference and any separate policy behavior.
  2. **Protect typed reporting-subject existence on future writes/provisioning.** A supplied `EMPLOYEE`, `POSITION`, or `ORGANIZATION_UNIT` reference must resolve to an existing Organization-owned subject before a new reporting line becomes authoritative. ORG-046 migration-time preflight alone is insufficient for later rows.
  3. **Reconcile and implement the documented matrix-reporting policy against the generalized typed-subject model.** Resolve primary-LINE representation, line-type vocabulary, employee lifecycle eligibility, allowed multiplicity, and LINE-cycle semantics without regressing to an unjustified employee-only model.


#### HMR-028A — ReportingLine catalog and generalized matrix-policy prerequisite

- Source: HMR-028 / HMSR-031 plus live Organization roadmap, ORG-042/ORG-046 and controlled-vocabulary audit evidence.
- Exact commit: `docs(model-remediation): register ReportingLine catalog and matrix-policy prerequisite`
- Status: **Completed** — the missing catalog/migration and policy persistence scope is registered; no production Java/JPA/Flyway mutation is part of HMR-028A.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. `ReportingLineType` is still a fixed Java enum persisted directly in `reporting_line_type`;
  2. the accepted controlled-vocabulary audit classifies it as `TYPE_ENUM_TO_CATALOG / REPLACE_FIRST_THEN_DELETE`;
  3. Organization has no reporting-line-type catalog table/entity/repository on live main;
  4. the target Organization roadmap vocabulary is `LINE`, `OPERATIONAL`, `FUNCTIONAL`, `ADMINISTRATIVE`, `TECHNICAL`, and `DOTTED_LINE`, while the live enum also contains legacy `TEMPORARY` and lacks three target codes;
  5. ORG-046 protects discriminator values and preflights existing subject references but does not provide ongoing polymorphic subject-existence protection;
  6. no active ReportingLine service/policy implementation currently enforces primary-LINE cardinality, employee eligibility, or LINE-cycle rules;
  7. the original HMR-028 envelope authorizes no migration and no reporting-line-type catalog persistence types, so the three HMSR-031 obligations cannot be completed honestly under that envelope.
- Required decisions:
  1. Create the dedicated Organization-owned `hidra_org_reporting_line_type` catalog with stable `id`/unique `code`, optional embedded `name_ar/name_fr/name_en`, `active`, and audit timestamps. HMR-028 must not create a separate translation table and must not invent translations.
  2. Seed only the roadmap-authoritative codes: `LINE`, `OPERATIONAL`, `FUNCTIONAL`, `ADMINISTRATIVE`, `TECHNICAL`, `DOTTED_LINE`.
  3. Legacy persisted `reporting_line_type` values are backfilled by exact code. Any unmappable value — including legacy `TEMPORARY` — fails migration preflight; HMR-028 must not silently map or rename it.
  4. Replace the enum contract with an extensible `ReportingLineType` catalog-reference value carrying stable identity/code and optional embedded localized labels. Keep reporting behavior in explicit domain methods/policy semantics, not as catalog-label inference.
  5. `LINE` is the canonical primary/hierarchical reporting-line category. No separate `primaryLine` field is added. The one-active-primary rule applies only when the source subject type is `EMPLOYEE`; HMR-028 does not invent the same cardinality for POSITION or ORGANIZATION_UNIT sources.
  6. Multiple active `FUNCTIONAL`, `ADMINISTRATIVE`, `TECHNICAL`, and `DOTTED_LINE` relations are allowed. HMR-028 adds no multiplicity rule for `OPERATIONAL` because the governing roadmap does not define one.
  7. Every source and target reference must resolve to the Organization-owned table selected by its existing `ReportingSubjectType`: EMPLOYEE -> `hidra_org_employee`, POSITION -> `hidra_org_position`, ORGANIZATION_UNIT -> `hidra_org_unit`. Validation must fail closed on future saves/provisioning.
  8. Employee lifecycle eligibility applies only to EMPLOYEE references. A new authoritative reporting line may use an employee source or employee target only when that employee is `ACTIVE`; `REGISTERED`, `SUSPENDED`, `RETIRED`, and `TERMINATED` are not eligible for new reporting authority. Position/unit references receive no invented employee-lifecycle rule.
  9. Active `LINE` relations must not introduce a directed cycle across the generalized typed-subject graph. Cycle identity is the pair `(subjectType, targetId)`; the rule is not reduced to employee-only edges.
  10. The additive migration `V20261004_028__hmr_028_organization_reporting_line.sql` may create/backfill the catalog reference, retire the old enum-style column after successful backfill, and install same-module database trigger/index/check protection needed for subject existence, employee ACTIVE eligibility, employee-source LINE cardinality, and LINE-cycle prevention.
  11. The repository adapter/repository remain the Java fail-closed write boundary. HMR-028 may use native same-module queries there and does not need to invent a new inbound application use case.
  12. HMR-028 may add only `ReportingLineTypeJpaEntity`, `ReportingLineTypeJpaRepository`, and the authorized additive migration beyond the original allowlist. No unrelated Organization production type is authorized.
- HMR-028 remains the next production remediation. Do not start HMR-029 automatically.


#### HMR-029 — risk.RiskMatrixCell

- Source review: `HMSR-032`
- Exact commit: `fix(risk): remediate semantic review RiskMatrixCell`
- Status: **Completed** — matrix-coordinate uniqueness, nonnegative score semantics, and exact likelihood/consequence Risk catalog-family validation are enforced at Java and PostgreSQL boundaries.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskMatrixCellSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce matrix-coordinate uniqueness.** The combination `(riskMatrixId, likelihoodLevelId, consequenceLevelId)` must identify at most one RiskMatrixCell. A separately authorized Risk correction should implement this with an additive unique database constraint/index and aligned application behavior.
  2. **Enforce nonnegative score values.** `scoreValue < 0` must be rejected at an appropriate fail-fast domain/application boundary and/or by an additive database CHECK, consistent with the explicit Risk DDD invariant.
  3. **Protect catalog-family semantics for likelihood and consequence levels.** A populated `likelihoodLevelId` must resolve to the `RISK_LIKELIHOOD_LEVEL` Risk catalog family and `consequenceLevelId` to `RISK_CONSEQUENCE_LEVEL`; generic row-existence FKs alone do not establish the required taxonomy.

#### HMR-030 — telemetry.TelemetrySource

- Source review: `HMSR-033`
- Exact commit: `fix(telemetry): remediate semantic review TelemetrySource`
- Status: **Completed** — source code uniqueness, active SOURCE_TYPE/PROTOCOL family validation, mandatory French naming, secret-material exclusion, and the source-specific lifecycle subset are enforced across creation/repository/database boundaries.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceEndpointJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TelemetrySourceSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce unique TelemetrySource code.** The DDD-defined unique source code must be protected at the persistence boundary, preferably with an additive unique constraint/index and aligned application behavior.
  2. **Validate source type and protocol catalog semantics.** `sourceTypeId` must resolve to an active `SOURCE_TYPE` entry and `protocolId` to an active `PROTOCOL` entry; generic FK row existence is insufficient.
  3. **Require the DDD-mandated French source label.** `nameFr` must not normalize to null at the canonical source creation/domain boundary while the target DDD and schema require it.
  4. **Prevent secret material in source endpoint/external-reference fields.** The source write boundary must reject or sanitize actual secrets instead of persisting them in `endpointUri` or `externalReference`; credential material belongs outside telemetry persistence.
  5. **Reconcile TelemetrySource lifecycle vocabulary.** Establish whether `PLANNED` and `MAINTENANCE` are valid source states or are only valid for other Telemetry entities, and align source domain/application/persistence/API semantics accordingly.

#### HMR-031 — topology.TopologyConnection

- Source review: `HMSR-034`
- Exact commit: `fix(topology): remediate semantic review TopologyConnection`
- Status: **Completed** — TopologyConnection now uses catalog-backed ConnectionTypeReference semantics, rejects self-loops at domain/database boundaries, and protects populated PipelineSegment references.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyConnectionException.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/ConnectionTypeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TopologyConnectionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Restore/reconcile catalog-backed connection-type semantics.** Replace the live fixed `ConnectionType` enum/string persistence with the accepted `ConnectionTypeReference`/catalog architecture, or explicitly revise the governing Topology architecture if that earlier decision is no longer intended. Domain, JPA, Flyway, API/application contracts and migration strategy must converge on one representation.
  2. **Enforce the no-self-connection domain rule on authoritative writes.** `fromNodeId` and `toNodeId` must not resolve to the same node when a TopologyConnection becomes authoritative. The existing unused validator is insufficient by itself.
  3. **Protect populated PipelineSegment references.** When `pipelineSegmentId` is non-null, it must resolve to the retained Topology PipelineSegment target through an additive nullable FK, application validation, guarded provisioning path, or equivalent fail-closed mechanism.


#### HMR-031A — TopologyConnection connection-type catalog prerequisite

- Source: HMR-031 / HMSR-034 plus the accepted Topology controlled-vocabulary architecture and live HMR-022 catalog pattern.
- Exact commit: `docs(model-remediation): register TopologyConnection catalog prerequisite`
- Status: **Completed** — the missing connection-type reference/catalog persistence scope is explicitly registered; no production Java/JPA/Flyway mutation is part of HMR-031A.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. `TopologyConnection` and `TopologyConnectionJpaEntity` still use the fixed `ConnectionType` enum and the scalar `connection_type` column;
  2. the accepted Topology architecture requires `TopologyConnection -> ConnectionTypeReference`;
  3. the controlled-vocabulary audit classifies `ConnectionType` as `REPLACE_FIRST_THEN_DELETE`;
  4. live main has no `ConnectionTypeReference`, `ConnectionTypeJpaEntity`, `ConnectionTypeJpaRepository`, or `hidra_topology_connection_type` table;
  5. HMR-031 already authorizes an additive migration, but its original Java allowlist cannot introduce the catalog-reference types required to complete obligation 1;
  6. the existing HMR-022 PipelineSystem catalog remediation establishes the accepted local pattern: stable id/code, optional embedded Arabic/French/English labels, same-module FK, fail-closed legacy preflight, and no invented translations.
- Required decisions:
  1. Create `ConnectionTypeReference` as the canonical Topology domain catalog-reference value carrying mandatory `id`, mandatory `code`, and optional embedded `nameAr/nameFr/nameEn`.
  2. Create `hidra_topology_connection_type`, `ConnectionTypeJpaEntity`, and `ConnectionTypeJpaRepository` using the same local catalog pattern as HMR-022. No separate translation table is created by HMR-031.
  3. Seed only the five codes that are proven by the live legacy enum: `PIPELINE_SEGMENT`, `DIRECT_LINK`, `VIRTUAL_LINK`, `TRANSFER_LINK`, `MEASUREMENT_LINK`. Seed IDs equal codes. Localized labels remain NULL because no authoritative translations are established.
  4. The additive migration `V20261004_031__hmr_031_topology_topology_connection.sql` preflights every existing `connection_type` value. Any value outside the five proven seed codes fails closed; no code is renamed or inferred.
  5. The migration adds required `connection_type_id`, backfills it by exact code, adds an `ON DELETE RESTRICT` same-module FK, makes the reference mandatory, and retires the old scalar `connection_type` column after successful validation.
  6. `TopologyConnection` moves to `ConnectionTypeReference`. The old `ConnectionType` enum may remain only as a deprecated compatibility surface if existing compilation guardrails require it; it must not remain canonical persistence/domain state after HMR-031.
  7. The no-self-connection invariant is enforced in the canonical `TopologyConnection` constructor and by the additive database CHECK. The existing `TopologyConnectionValidator` remains aligned and must not be the sole enforcement point.
  8. `pipelineSegmentId` remains optional. When populated, the repository/database boundary must verify `hidra_topology_pipeline_segment(id)`; the migration adds a nullable `ON DELETE RESTRICT` FK.
  9. HMR-031 does not invent connection-code uniqueness, graph-edge uniqueness, capacity sign/pairing rules, capacity-unit ownership, or a requirement that `PIPELINE_SEGMENT` classification must carry `pipelineSegmentId`.
  10. HMR-031 may add/modify only the newly registered reference/catalog files plus the original HMR-031 allowlist. No unrelated Topology catalog rollout is authorized.
- HMR-031 remains the next production remediation. Do not start HMR-032 automatically.


#### HMR-032 — organization.OrganizationUnit

- Source review: `HMSR-035`
- Exact commit: `fix(organization): remediate semantic review OrganizationUnit`
- Status: **Completed** — multi-record hierarchy cycles are rejected, new/reclassified units require an active OrganizationUnitType, historical inactive type references remain preservable when unchanged, and validFrom is mandatory across REST/application/JPA/schema boundaries.
- SCC: SCC-01
- Recorded upstream HMS dependencies: organization.OrganizationUnit, organization.OrganizationUnitType
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_032__hmr_032_organization_organization_unit.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/api/rest/request/CreateOrganizationUnitRequest.java`
  - `src/main/java/dz/sh/hidra/modules/organization/api/rest/response/OrganizationUnitResponse.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/command/CreateOrganizationUnitCommand.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/dto/OrganizationUnitSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/in/CreateOrganizationUnitUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitTypeRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/OrganizationUnitApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitKind.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitStatus.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitTypeRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationUnitJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationUnitTypeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_032__hmr_032_organization_organization_unit.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/application/command/CreateOrganizationUnitCommandTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationUnitSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=OrganizationUnitSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Prevent multi-record OrganizationUnit hierarchy cycles.** Authoritative hierarchy mutations and provisioning must reject any parent assignment that would create a cycle, not only direct self-parenting. Repository traversal/application policy or an equivalent fail-closed database/provisioning mechanism is required.
  2. **Reject inactive OrganizationUnitType selection for new/changed units.** `unitTypeId` existence is already protected by FK, but authoritative writes must also enforce the approved `OrganizationUnitType.active` selectable/usable semantics while preserving historical references to deactivated types.
  3. **Align mandatory `validFrom` semantics across persistence and boundaries.** JPA/final schema and create/update contracts must not admit an OrganizationUnit without the effective start required by the canonical domain; use an additive migration and compatible boundary validation in a separately authorized Organization correction task.

#### HMR-033 — telemetry.TelemetryReading

- Source review: `HMSR-037`
- Exact commit: `fix(telemetry): remediate semantic review TelemetryReading`
- Status: **Completed** — optional batch/mapping references are protected, qualityCodeId is constrained to QUALITY_CODE, and raw-reading value shape is enforced consistently with the rejected/quarantined null-value exception.
- SCC: —
- Recorded upstream HMS dependencies: telemetry.TelemetryPoint
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/exception/TelemetryReadingValidationException.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryReadingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TelemetryReadingSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated ingestion-batch references.** When `ingestionBatchId` is supplied, it must resolve to the Telemetry-owned ingestion batch rather than allowing a dangling traceability ID. An additive nullable same-module FK, application validation, or equivalent fail-closed mechanism may be used.
  2. **Protect populated external-tag-mapping references.** When `externalTagMappingId` is supplied, it must resolve to the Telemetry-owned external tag mapping rather than allowing a dangling mapping-evidence ID.
  3. **Enforce quality-code catalog-family semantics.** `qualityCodeId` must resolve specifically to the `QUALITY_CODE` Telemetry catalog family; the existing generic catalog FK only proves row existence.
  4. **Establish one authoritative raw-reading value-shape policy.** Reconcile the DDD's exactly-one rule and its explicit null/state exception, then enforce that contract consistently at the raw-reading creation/ingestion boundary rather than leaving `TelemetryReadingValueValidator` unused and stricter than the documented exception model.

#### HMR-034 — simulation.SimulationScenario

- Source review: `HMSR-038`
- Exact commit: `fix(simulation): remediate semantic review SimulationScenario`
- Status: **Completed** — unique code, scenario-type family, model/version parent consistency, mandatory French/creator snapshot fields, and fail-closed execution-time owner-reference validation are enforced.
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationModel
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationScenarioRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationScenarioResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationScenarioCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationScenarioSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioAssumptionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationScenarioSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce unique scenario code.** Protect the DDD-defined unique `SimulationScenario.code` at the persistence boundary and align application behavior.
  2. **Enforce scenario-type catalog-family semantics.** `scenarioTypeId` must resolve specifically to `SIMULATION_SCENARIO_TYPE`, not merely any Simulation catalog row.
  3. **Enforce model/model-version parent consistency.** The selected `modelVersionId` must belong to the selected `modelId`.
  4. **Require DDD-mandated nonblank `nameFr` and `createdByDisplayNameSnapshot`.** Canonical construction must not admit values that violate the stated scenario contract and final schema.
  5. **Fail closed on scenario input references at the appropriate lifecycle boundary.** Before READY/LOCKED/execution, the required Topology snapshot and any populated Planning/Monitoring references must resolve through their existing owner-module lookup ports or equivalent boundary validation.

#### HMR-034A — SimulationScenario owner-reference validation prerequisite

- Source: HMR-034 / HMSR-038 obligation 5 plus live Simulation application/port evidence.
- Exact commit: `docs(model-remediation): register SimulationScenario reference prerequisite`
- Status: **Completed** — the missing authoritative owner-reference validation scope is explicitly registered; no production Java/JPA/Flyway mutation is part of HMR-034A.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. `SimulationApplicationService.createSimulationScenario(...)` is the authoritative scenario creation path and is not present in the original HMR-034 write allowlist.
  2. Simulation already declares `TopologySnapshotLookupPort`, `PlanningSnapshotLookupPort`, and `MonitoringContextLookupPort`, each exposing `available(String referenceId)`, but no production implementation of those ports is present on live `main`.
  3. `SimulationExternalReferenceResolver` exposes topology/planning/monitoring availability methods, but the only live implementation is `NoopSimulationExternalReferenceResolver`, which returns `true` unconditionally and therefore cannot satisfy the fail-closed HMSR obligation.
  4. The repository architecture classifies cross-module stable references as owner-validated through exported application contracts and explicitly forbids cross-module database foreign keys.
  5. The Simulation DDD defines `topologySnapshotId` as a required Topology snapshot reference, `planningReferenceId` as an optional plan/revision reference, and `monitoringContextId` as an optional monitoring/evaluation context; no narrower target meaning is established and none may be invented by HMR-034.
- Required decisions:
  1. HMR-034 must validate scenario input references at the application lifecycle boundary before a scenario can become READY/LOCKED/executable; DRAFT construction alone is not sufficient evidence of readiness.
  2. The existing Simulation lookup ports remain the consumer-side contracts. HMR-034 may wire them into `SimulationApplicationService`; it must not perform repository lookups in the domain constructor.
  3. Owner-side validation must remain behind deliberate exported application contracts. HMR-034 must not import Topology/Planning/Monitoring domain models, repositories, JPA entities, or infrastructure adapters and must not add cross-module database foreign keys.
  4. The unconditional no-op resolver is not acceptable for authoritative validation and must not be used to satisfy obligation 5.
  5. `planningReferenceId` remains the already documented opaque plan/revision reference and `monitoringContextId` remains the already documented opaque monitoring/evaluation reference; HMR-034 must not invent a narrower ownership subtype without owner-module evidence.
  6. HMR-034 production scope is expanded only as necessary to include the authoritative application service and the existing Simulation lookup-port/integration wiring required to enforce obligation 5. Any required owner-module exported contract that is absent on live `main` must be introduced as a narrow Simulation-facing availability contract, not as shared/domain leakage.
  7. The original HMR-034 semantic obligations 1-4, exact commit message, additive migration rule, and original allowlist remain unchanged.
- Newly authorized HMR-034 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- HMR-034 remains the next production remediation after this prerequisite commit is observed. Do not start HMR-035 automatically.


#### HMR-035 — notification.NotificationRequest

- Source review: `HMSR-039`
- Exact commit: `fix(notification): remediate semantic review NotificationRequest`
- Status: **Completed** — source context, category/priority catalog families, and populated policy/template/template-version references are enforced at write and database boundaries.
- SCC: —
- Recorded upstream HMS dependencies: notification.NotificationTemplate
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationRequestResponse.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationRequestSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestRecipientJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationRequestSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require nonblank source-module and source-event-type semantics at the authoritative domain/application boundary.** `sourceModule` and `sourceEventType` must not normalize to null while the DDD and final persistence contract require the source event context.
  2. **Enforce Notification category/priority catalog semantics.** Mandatory `categoryId` must resolve specifically to `NOTIFICATION_CATEGORY`; when optional `priorityId` is populated, it must resolve to `NOTIFICATION_PRIORITY`. Generic category row existence and an unprotected priority ID are insufficient.
  3. **Protect populated NotificationPolicy references.** When `policyId` is supplied, it must resolve to the Notification-owned policy target through an additive nullable same-module FK, application validation, or equivalent fail-closed boundary.
  4. **Protect populated NotificationTemplate references.** When `templateId` is supplied, it must resolve to the reviewed NotificationTemplate subject rather than allowing a dangling HMS dependency.
  5. **Protect populated NotificationTemplateVersion references.** When `templateVersionId` is supplied, it must resolve to the retained immutable template-version target; HMSR-039 does not additionally impose parent-pair semantics absent stronger DDD evidence.

#### HMR-036 — topology.Facility

- Source review: `HMSR-041`
- Exact commit: `fix(topology): remediate semantic review Facility`
- Status: **Completed** — populated Facility ownerPartyId is validated fail-closed through the Party-owned Topology application contract; snapshots remain non-authoritative and no cross-module FK is introduced.
- SCC: —
- Recorded upstream HMS dependencies: party.Party
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_036__hmr_036_topology_facility.sql`
- Owner-contract prerequisite: No suitable exported owner lookup found in live tree for party; task must introduce/authorize an owner contract by roadmap amendment before cross-module validation changes.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/RegisterFacilityRequest.java`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/command/RegisterFacilityCommand.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/dto/FacilitySummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityNodeBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeDefinitionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeValueJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityNodeBindingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeVersionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_036__hmr_036_topology_facility.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=FacilitySemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate populated Facility owner Party references through the Party owner boundary.** When `ownerPartyId` is supplied, Topology must fail closed unless that Party identity exists under the Party bounded context. Do not introduce a cross-module database FK. A separately authorized Topology/Party correction should use a deliberate Party lookup/contract or equivalent owner-controlled validation boundary and preserve snapshots as non-authoritative evidence.

#### HMR-036A — Facility Party-owner lookup prerequisite

- Source: HMR-036 / HMSR-041 plus live Party/Topology architecture evidence.
- Exact commit: `docs(model-remediation): register Facility owner lookup prerequisite`
- Status: **Completed** — the missing Party-owned validation contract and minimal HMR-036 scope expansion are explicitly registered; HMR-036A itself changes documentation only.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. `Facility.ownerPartyId` is an optional neutral Party identity reference and the Facility DDD snapshots remain non-authoritative evidence.
  2. HMR-036 requires populated `ownerPartyId` to fail closed unless the Party identity exists under the Party bounded context, while explicitly forbidding a cross-module database foreign key.
  3. No Party-exported contract for Topology exists on live `main`.
  4. Party already owns `Party` and `PartyRepositoryPort.findById(String id)`, so existence can be resolved by the owner module without exposing Party persistence or domain types.
  5. The repository already uses deliberate consumer-specific owner contracts under `<owner>.application.contract.<consumer>`; consuming application services import those contracts while remaining independent of the owner module's domain and infrastructure.
- Required decisions:
  1. Party must export one narrow Topology-facing application contract whose only required semantic is whether a Party identity exists for a supplied Party ID.
  2. The contract must expose only neutral values and must not return `Party`, JPA entities, repositories, or persistence DTOs.
  3. The Party-side implementation must resolve existence through Party application/repository boundaries and fail closed for null/blank/unknown IDs.
  4. `FacilityApplicationService` is the authoritative Topology creation boundary for HMR-036 and may depend on the Party-owned exported application contract.
  5. When `ownerPartyId` is null, no Party lookup is required. When populated, registration must fail closed unless Party confirms existence.
  6. `ownerPartyCodeSnapshot` and `ownerPartyNameSnapshot` remain descriptive snapshots only; HMR-036 must not use them as identity proof or infer Party existence from them.
  7. No cross-module database FK, Party domain import, Party repository import, or Party infrastructure import is authorized.
- Newly authorized HMR-036 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java`
- Newly authorized HMR-036 test file:
  - `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java`
- HMR-036 remains the next production remediation after this prerequisite commit is observed. Do not start HMR-037 automatically.


#### HMR-036B — Facility Party-contract guardrail registration prerequisite

- Source: HMR-036 CI #498 architecture failures plus the completed HMR-036A export decision.
- Exact commit: `docs(model-remediation): register Facility Party contract guardrail prerequisite`
- Status: **Completed** — the two architecture guardrail registries are authorized to recognize the already approved Party-owned Topology contract package; HMR-036B itself changes documentation only.
- Type: documentation/architecture prerequisite.
- Live CI evidence:
  1. `ForensicRemediationClosureTest.crossModulePrivateImportsRemainClosed` rejected `topology.application.service.FacilityApplicationService -> party.application.contract.topology.TopologyPartyReferenceContract`.
  2. `ArchitectureGuardrailTest.businessModulesMustNotReachIntoOtherModuleInternals` rejected the same dependency.
  3. Both tests maintain explicit allowlists of deliberately exported `application.contract.<consumer>` packages; the new Party-to-Topology contract was absent from those registries.
  4. The dependency itself matches the repository's established owner-exported contract pattern and was already authorized by HMR-036A.
- Required decisions:
  1. Do not weaken either guardrail algorithm or introduce wildcard exceptions.
  2. Register exactly `dz.sh.hidra.modules.party.application.contract.topology` as an exported cross-module package in both existing guardrail registries.
  3. No transitional dependency exception is permitted; this is a deliberate exported owner contract.
  4. Do not alter any other guardrail rule, package, or allowlist entry.
- Newly authorized HMR-036 repair files:
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-036 remains the current remediation until the guardrail registration repair is committed and green. Do not start HMR-037 automatically.


#### HMR-037 — analytics.AnalyticsDatasetVersion

- Source review: `HMSR-042`
- Exact commit: `fix(analytics): remediate semantic review AnalyticsDatasetVersion`
- Status: **Completed** — once an AnalyticsDatasetVersion is persisted as published, changed subsequent saves fail closed at the repository adapter boundary while identical re-saves are no-ops and the initial publish transition remains allowed.
- SCC: —
- Recorded upstream HMS dependencies: analytics.AnalyticsDataset
- HMSR correction count: 1
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsDatasetVersionJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AnalyticsDatasetVersionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce immutability of published AnalyticsDatasetVersion state.** Once a dataset version is published, subsequent writes must not mutate its persisted release content/metadata contrary to the Analytics DDD. The production correction must choose an architecture-consistent fail-closed mechanism without rewriting this HMSR documentation task into implementation work.

#### HMR-038 — analytics.MetricValue

- Source review: `HMSR-043`
- Exact commit: `fix(analytics): remediate semantic review MetricValue`
- Status: **Completed** — canonical MetricValue construction now rejects null/blank scopeType before persistence, preserving the analytical scope namespace required to interpret scopeId.
- SCC: —
- Recorded upstream HMS dependencies: analytics.MetricEvaluationRun
- HMSR correction count: 1
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricValueJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MetricValueSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require nonblank MetricValue scopeType at the authoritative domain/application boundary.** The canonical model must not normalize `scopeType` to null when final persistence requires it and the Analytics scope-reference pattern depends on it to interpret `scopeId`.

#### HMR-039 — configuration.ConfigurationValue

- Source review: `HMSR-044`
- Exact commit: `fix(configuration): remediate semantic review ConfigurationValue`
- Status: **Completed** — environment is mandatory/nonblank across request, command and domain boundaries; populated definitionVersionId is protected by an additive same-module FK.
- SCC: —
- Recorded upstream HMS dependencies: configuration.ConfigurationDefinition
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_039__hmr_039_configuration_configuration_value.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Configuration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/SetConfigurationValueRequest.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/ConfigurationValueResponse.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/command/SetConfigurationValueCommand.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/dto/ConfigurationValueSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/SetConfigurationValueUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationValueRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/exception/InvalidConfigurationValueException.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/service/ConfigurationValueGuard.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueStatus.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueType.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationValueRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/mapper/ConfigurationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationValueJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_039__hmr_039_configuration_configuration_value.sql`
  - `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationValueSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ConfigurationValueSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require nonblank ConfigurationValue environment at the authoritative domain/application boundary.** The canonical model must not normalize environment to null while final persistence requires it.
  2. **Protect populated ConfigurationDefinitionVersion references.** When `definitionVersionId` is supplied, it must resolve to the Configuration-owned version target through an additive nullable same-module FK, application validation, or equivalent fail-closed boundary.

#### HMR-040 — monitoring.MonitoringRule

- Source review: `HMSR-048`
- Exact commit: `fix(monitoring): remediate semantic review MonitoringRule`
- Status: **Completed** — populated telemetryPointId is validated fail-closed through the Telemetry-owned Monitoring application contract; no cross-module database FK is introduced.
- SCC: —
- Recorded upstream HMS dependencies: telemetry.TelemetryPoint
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
- Owner-contract prerequisite: HMR-040A registered — live preflight proved TelemetryQueryUseCase does not expose TelemetryPoint existence and no Telemetry-owned Monitoring contract exists.
- Exact write allowlist:
  - `docs/data definition/Monitoring.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringRuleJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
  - `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MonitoringRuleSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate populated MonitoringRule telemetryPointId through the Telemetry owner boundary.** When `telemetryPointId` is supplied, Monitoring must fail closed unless the referenced TelemetryPoint exists under the Telemetry bounded context. Do not add a cross-module database FK.

#### HMR-040A — MonitoringRule TelemetryPoint owner-contract prerequisite

- Source: HMR-040 / HMSR-048 plus live Telemetry/Monitoring architecture evidence.
- Exact commit: `docs(model-remediation): register MonitoringRule telemetry owner prerequisite`
- Status: **Completed** — the missing Telemetry-owned Monitoring reference contract, owner-side implementation, and exact guardrail registrations are authorized; HMR-040A itself changes documentation only.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. HMR-040 requires populated `MonitoringRule.telemetryPointId` to fail closed unless the Telemetry-owned point exists.
  2. The previously recorded candidate `telemetry.application.port.in.TelemetryQueryUseCase` exposes reading/time-series and quality-code queries only; it has no TelemetryPoint existence operation.
  3. `TelemetryPointRepositoryPort.findById(String id)` exists inside Telemetry and can resolve owner truth, but it is an internal outbound port and must not be imported by Monitoring.
  4. No `telemetry.application.contract.monitoring` package or equivalent exported Telemetry-to-Monitoring contract exists on live `main`.
  5. Repository architecture requires cross-module consumers to depend only on deliberate owner-exported `application.contract.<consumer>` packages, and both architecture guardrails maintain explicit exported-package registries.
- Required decisions:
  1. Telemetry must export a narrow Monitoring-facing application contract whose only required semantic is whether a TelemetryPoint exists for a supplied point ID.
  2. The contract must expose neutral scalar values only and must not return `TelemetryPoint`, repository types, JPA entities, or persistence DTOs.
  3. A Telemetry-owned application query service must implement the contract using `TelemetryPointRepositoryPort.findById(...)` and fail closed for null/blank/unknown IDs.
  4. `MonitoringRuleApplicationService` may depend on this exported contract and must validate only populated `telemetryPointId`; null remains allowed by the reviewed HMR obligation.
  5. No cross-module database FK from Monitoring to Telemetry is authorized.
  6. Register exactly `dz.sh.hidra.modules.telemetry.application.contract.monitoring` in the existing exported-package registries of `ArchitectureGuardrailTest` and `ForensicRemediationClosureTest`; do not weaken algorithms or add wildcard/transitional exceptions.
- Newly authorized HMR-040 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- Newly authorized HMR-040 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-040 remains the current production remediation after this prerequisite is observed. Do not start HMR-041 automatically.


#### HMR-041 — party.PartyRoleAssignment

- Source review: `HMSR-049`
- Exact commit: `fix(party): remediate semantic review PartyRoleAssignment`
- Status: **Completed** — duplicate ACTIVE assignments for the same Party/PartyRole fail at application pre-check and are race-safe under a PostgreSQL partial unique index; historical non-ACTIVE rows remain unaffected.
- SCC: —
- Recorded upstream HMS dependencies: party.Party
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_041__hmr_041_party_party_role_assignment.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Party.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRoleAssignmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/service/PartyRoleAssignmentApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java`
  - `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRoleAssignmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/mapper/PartyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleAssignmentJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_041__hmr_041_party_party_role_assignment.sql`
  - `src/test/java/dz/sh/hidra/modules/party/semantic/PartyRoleAssignmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PartyRoleAssignmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Prevent duplicate ACTIVE PartyRoleAssignment rows for the same Party and PartyRole.** The production correction must enforce the explicit Party DDD invariant at an authoritative fail-closed boundary and define a concurrency-safe strategy. It may use application lookup/locking, a partial unique database constraint, or another architecture-consistent approach, while preserving historical assignments and without inventing broader overlap rules absent DDD evidence.

#### HMR-042 — topology.Pipeline

- Source review: `HMSR-050`
- Exact commit: `fix(topology): remediate semantic review Pipeline`
- Status: **Completed** — Pipeline classification now uses an open Topology-owned catalog reference with seven migrated compatibility codes, FK-backed persistence, validated legacy backfill and stable-code visualization; TopologyStatus remains the lifecycle enum.
- SCC: —
- Recorded upstream HMS dependencies: topology.PipelineSystem
- HMSR correction count: 1
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
  - `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSegmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PipelineSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Replace Pipeline's fixed `PipelineType` business taxonomy with the repository-approved catalog-reference architecture.** The correction must reconcile domain, persistence, Flyway/data migration, API/read contracts and existing values with the accepted Topology type-catalog design, using stable catalog codes/localized labels rather than a fixed business enum. Preserve `TopologyStatus` as a lifecycle enum unless separate evidence changes that classification.

#### HMR-042A — Pipeline classification catalog prerequisite

- Source: HMR-042 / HMSR-050 plus live Pipeline/PipelineSystem catalog evidence.
- Exact commit: `docs(model-remediation): register Pipeline catalog prerequisite`
- Status: **Completed** — the missing Pipeline classification catalog persistence/migration surface and minimal downstream compatibility scope are authorized; HMR-042A itself changes documentation only.
- Type: documentation/architecture/schema prerequisite.
- Live evidence:
  1. `Pipeline.pipelineType` is still the fixed enum `PipelineType` with values `CRUDE_OIL`, `CONDENSATE`, `NATURAL_GAS`, `LPG`, `MULTI_PRODUCT`, `WATER`, `OTHER`.
  2. `PipelineJpaEntity` still persists that enum directly in legacy column `pipeline_type`.
  3. No `PipelineTypeJpaEntity`, `PipelineTypeJpaRepository`, `hidra_topology_pipeline_type`, or `pipeline_type_id` exists on live `main`.
  4. The repository-approved Topology catalog pattern already exists for `PipelineSystemType` and `ConnectionTypeReference`: dedicated catalog table/entity/repository, domain reference object, code lookup at the application/repository boundary, FK-backed persisted ID, and migration/backfill from former enum/string values.
  5. HMR-042 explicitly requires Flyway/data migration, but its original registration had no additive migration authorization.
  6. `PipelineType` is consumed by the topology map visualization adapter and existing topology application tests outside the original HMR-042 allowlist; those callers must migrate to stable catalog-code semantics to keep the repository compiling without reintroducing enum coupling.
- Required decisions:
  1. Convert `PipelineType` from a closed business enum into the open Pipeline classification reference object, preserving the seven existing codes only as compatibility constants/seeded references, not as an exhaustive taxonomy.
  2. Introduce a dedicated Topology-owned `hidra_topology_pipeline_type` catalog following the proven PipelineSystemType/ConnectionType catalog shape: stable `id`, unique stable `code`, optional `name_ar/name_fr/name_en`, `active`, and timestamps.
  3. Seed exactly the seven codes already present in the former enum. Do not invent localized labels or new classifications.
  4. Add an immutable HMR-042 Flyway migration that validates legacy values, backfills `pipeline_type_id`, adds/validates the same-module FK, indexes the reference, and removes the legacy `pipeline_type` column only after successful backfill.
  5. Pipeline persistence must store the catalog FK/reference, not an enum string.
  6. Repository/application reads expose stable catalog code and optional localized labels. Existing lifecycle `TopologyStatus` remains an enum.
  7. Visualization must emit the Pipeline type stable code, not enum-name reflection.
  8. No generic/shared catalog abstraction is authorized; keep the correction Topology/Pipeline-specific.
- Newly authorized HMR-042 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
  - `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- Newly authorized HMR-042 compatibility test file:
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`
- HMR-042 remains the current production remediation after this prerequisite is observed. Do not start HMR-043 automatically.


#### HMR-043 — workflow.WorkflowStep

- Source review: `HMSR-051`
- Exact commit: `fix(workflow): remediate semantic review WorkflowStep`
- Status: **Completed** — non-negative order, per-definition code/order uniqueness, and populated defaultAssignmentRuleId integrity are enforced at domain/repository/database boundaries while preserving the nullable SCC-02 reference.
- SCC: SCC-02
- Recorded upstream HMS dependencies: workflow.WorkflowDefinition, workflow.WorkflowStepAssignmentRule
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowStepSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce `stepOrder >= 0`.** The authoritative domain/application/database boundary must reject negative workflow-step ordering.
  2. **Enforce uniqueness of `(definitionId, code)`.** Two steps in the same WorkflowDefinition must not share the same stable step code.
  3. **Enforce uniqueness of `(definitionId, stepOrder)`.** Two steps in the same WorkflowDefinition must not occupy the same deterministic order position.
  4. **Protect populated `defaultAssignmentRuleId` references.** When supplied, the reference must resolve to WorkflowStepAssignmentRule through a nullable same-module FK, application validation, or another fail-closed mechanism that preserves SCC-02.

#### HMR-044 — workflow.WorkflowStepAssignmentRule

- Source review: `HMSR-052`
- Exact commit: `fix(workflow): remediate semantic review WorkflowStepAssignmentRule`
- Status: **Completed** — assignmentModeId must belong specifically to WORKFLOW_ASSIGNMENT_MODE and every rule must provide at least one concrete actor/candidate-pool strategy before persistence.
- SCC: SCC-02
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowDefinition, workflow.WorkflowStep
- HMSR correction count: 2
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: HMR-045A registered — live preflight proved no Assets-facing Topology, Organization, or Party owner contracts exist and the actual registration/update orchestration file was outside the original allowlist.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowStepAssignmentRuleSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce `WORKFLOW_ASSIGNMENT_MODE` catalog-family semantics for `assignmentModeId`.** Generic Workflow catalog-row existence is insufficient; the selected entry must belong to the documented assignment-mode family.
  2. **Require every WorkflowStepAssignmentRule to provide at least one resolvable actor or candidate-pool strategy.** The authoritative domain/application boundary must reject rules where `actorId`, `roleCode`, `organizationUnitId`, `organizationRoleCode`, and `targetOwnerMode` are all absent. Mode-specific eligibility may be resolved through owner-controlled ports, but Workflow must not persist a rule that cannot resolve any candidate source.

#### HMR-045 — assets.MaintainableAsset

- Source review: `HMSR-054`
- Exact commit: `fix(assets): remediate semantic review MaintainableAsset`
- Status: **Completed** — mandatory typed Topology references and populated OrganizationUnit/Party references now resolve through owner-exported Assets contracts; populated parent/model/serial references fail closed and are protected by nullable same-module FKs.
- SCC: SCC-03
- Recorded upstream HMS dependencies: assets.MaintainableAsset, organization.OrganizationUnit, party.Party
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
- Owner-contract prerequisite: HMR-047A registered — live preflight proved no Integration-facing Organization owner contract exists and the authoritative Integration registration service was outside the original write scope.
- Exact write allowlist:
  - `docs/data definition/Assets.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/RegisterMaintainableAssetRequest.java`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/UpdateMaintainableAssetRequest.java`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintainableAssetResponse.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/command/RegisterMaintainableAssetCommand.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintainableAssetSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/exception/MaintainableAssetConflictException.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintainableAssetJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
  - `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MaintainableAssetSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate the mandatory typed Topology reference.** `topologyAssetTypeCode + topologyAssetId` must resolve through a Topology-owned contract before registration/update paths persist a new linkage; do not add a cross-module FK.
  2. **Protect populated `parentAssetId`.** Preserve SCC-03 and the existing direct-self guard, while failing closed when a supplied parent MaintainableAsset does not exist.
  3. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Do not introduce a cross-module database FK.
  4. **Validate populated `manufacturerPartyId` through the Party owner boundary.** Do not introduce a cross-module database FK or invent manufacturer-role eligibility.
  5. **Protect populated `modelId` and `serialIdentityId` same-module references.** Use nullable same-module FKs, application validation, or equivalent fail-closed integrity.

#### HMR-045A — MaintainableAsset multi-owner contract prerequisite

- Source: HMR-045 / HMSR-054 plus live Assets/Topology/Organization/Party boundary evidence.
- Exact commit: `docs(model-remediation): register MaintainableAsset owner prerequisites`
- Status: **Completed** — the missing owner-exported contracts, Assets orchestration file, focused owner-contract tests, and exact guardrail registrations are authorized; HMR-045A itself changes documentation only.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. HMR-045 requires fail-closed validation of the mandatory typed Topology reference, optional OrganizationUnit owner, and optional manufacturer Party before a new linkage is persisted.
  2. No `topology.application.contract.assets`, `organization.application.contract.assets`, or `party.application.contract.assets` package exists on live `main`.
  3. The recorded Organization candidates are internal application ports and are not deliberate Assets-facing exports.
  4. Topology already has owner repositories/services capable of resolving PipelineSystem, Pipeline, Facility, and Equipment identities, but its existing exported contracts are consumer-specific and must not be repurposed by importing another consumer's contract package.
  5. Party already proves the owner-side existence pattern through `PartyRepositoryPort.findById(...)`, but its current exported contract is Topology-specific.
  6. Organization can resolve OrganizationUnit existence through `OrganizationUnitRepositoryPort.findById(...)`, but no Assets-facing exported contract exists.
  7. The authoritative MaintainableAsset registration/update orchestration is `AssetsApplicationService`, which is outside the original HMR-045 write allowlist.
  8. `modelId` and `serialIdentityId` point to existing same-module tables `hidra_asset_model` and `hidra_asset_serial_identity`; `parentAssetId` points to `hidra_asset_maintainable_asset`.
- Required decisions:
  1. Topology must export a narrow Assets-facing contract that validates a typed Topology identity using neutral scalar `topologyAssetTypeCode + topologyAssetId` input and returns only neutral existence/current-display evidence; no Topology domain/JPA/repository type may cross the boundary.
  2. Topology owner implementation may resolve only Topology entity types already supported by the live owner model; do not invent new asset-type vocabularies. Unsupported type codes fail closed.
  3. Organization must export a narrow Assets-facing `OrganizationUnit` existence contract implemented through `OrganizationUnitRepositoryPort.findById(...)`. HMR-045 requires existence only; do not invent lifecycle/ACTIVE eligibility.
  4. Party must export a narrow Assets-facing Party existence contract implemented through `PartyRepositoryPort.findById(...)`. Do not invent manufacturer-role eligibility.
  5. `AssetsApplicationService` must validate the mandatory Topology reference and populated OrganizationUnit/Party references before registration; update paths must revalidate whenever a path can persist a new linkage. Existing updates that do not change linkage fields must not invent unrelated owner lookups.
  6. Preserve SCC-03 for `parentAssetId`: direct self-reference remains rejected by the domain, populated parent IDs must exist, and database integrity may use a nullable same-table FK.
  7. HMR-045 migration may add nullable same-module FKs:
     - `parent_asset_id -> hidra_asset_maintainable_asset(id)`
     - `model_id -> hidra_asset_model(id)`
     - `serial_identity_id -> hidra_asset_serial_identity(id)`
     No Topology, Organization, or Party cross-module FK is authorized.
  8. Register exactly these new exported packages in both architecture guardrail registries:
     - `dz.sh.hidra.modules.topology.application.contract.assets`
     - `dz.sh.hidra.modules.organization.application.contract.assets`
     - `dz.sh.hidra.modules.party.application.contract.assets`
     Do not weaken algorithms or add wildcard/transitional exceptions.
- Newly authorized HMR-045 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- Newly authorized HMR-045 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-045 remains the current production remediation after this prerequisite is observed. Do not start HMR-046 automatically.


#### HMR-046 — simulation.SimulationRun

- Source review: `HMSR-055`
- Exact commit: `fix(simulation): remediate semantic review SimulationRun`
- Status: **Completed** — queueing now requires a LOCKED scenario plus SIMULATION_RUN_TYPE and SIMULATION_SOLVER_PROFILE family membership, and persisted COMPLETED run state is immutable under repository writes.
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationScenario
- HMSR correction count: 4
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/QueueSimulationRunRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/QueueSimulationRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStepStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunStepJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require the referenced SimulationScenario to be `LOCKED` before a SimulationRun is queued/executed.** Scenario existence alone is insufficient; enforce lifecycle eligibility at the authoritative Simulation application boundary.
  2. **Enforce `SIMULATION_RUN_TYPE` catalog-family semantics for `runTypeId`.** The referenced catalog row must belong to the documented run-type family.
  3. **Enforce `SIMULATION_SOLVER_PROFILE` catalog-family semantics for `solverProfileId`.** The referenced catalog row must belong to the documented solver-profile family.
  4. **Enforce immutability of COMPLETED SimulationRun business state.** Once completed, ordinary run fields must not be changed through repository/application writes, except separately modeled publication/archival metadata authorized by the DDD.

#### HMR-046A — SimulationRun application-boundary prerequisite

- Source: HMR-046 / HMSR-055 plus live Simulation queue/catalog evidence.
- Exact commit: `docs(model-remediation): register SimulationRun application prerequisite`
- Status: **Completed** — the authoritative queue application service is added to the HMR-046 write scope; HMR-046A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. HMR-046 requires a SimulationScenario to be LOCKED before a run is queued/executed.
  2. The actual authoritative queue path is `SimulationApplicationService.queueSimulationRun(...)`; it already resolves the scenario and invokes `scenario.executable()`, but `SimulationApplicationService.java` is absent from the original HMR-046 allowlist.
  3. HMR-046 also requires `runTypeId` to belong to `SIMULATION_RUN_TYPE` and `solverProfileId` to belong to `SIMULATION_SOLVER_PROFILE`; these checks belong at the same queue boundary and can be backed by native catalog-family queries in the already-authorized `SimulationRunJpaRepository` / adapter / port.
  4. No cross-module owner contract is needed: SimulationScenario and Simulation catalog rows are Simulation-owned.
  5. COMPLETED-run immutability can be enforced at the already-authorized SimulationRun repository adapter by comparing the persisted completed run before save, analogous to the HMR-037 published-version guard.
- Required decisions:
  1. Add `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java` to the HMR-046 write allowlist.
  2. Queue execution must fail closed unless the referenced SimulationScenario exists and has status `LOCKED`.
  3. Queue execution must fail closed unless `runTypeId` belongs specifically to `SIMULATION_RUN_TYPE`.
  4. Queue execution must fail closed unless `solverProfileId` belongs specifically to `SIMULATION_SOLVER_PROFILE`.
  5. Do not invent additional run/scenario lifecycle transitions beyond the reviewed obligations.
  6. Once an existing persisted SimulationRun is `COMPLETED`, an identical re-save may be treated as a no-op, but any ordinary business-state mutation must fail closed.
  7. No Flyway migration is required for HMR-046 unless live evidence later proves a database-level correction is necessary; existing same-module FKs/catalog tables already exist.
- Newly authorized HMR-046 production file:
  - `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- HMR-046 remains the current production remediation after this prerequisite is observed. Do not start HMR-047 automatically.


#### HMR-047 — integration.ExternalSystem

- Source review: `HMSR-056`
- Exact commit: `fix(integration): remediate semantic review ExternalSystem`
- Status: **Completed** — ExternalSystem code uniqueness is deterministic at application level and race-safe in PostgreSQL, systemTypeId is constrained to EXTERNAL_SYSTEM_TYPE, and populated ownerOrganizationUnitId resolves through an Organization-owned Integration contract.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
- Owner-contract prerequisite: HMR-048A registered — live preflight proved the Reporting request/queue service and deliberate Identity/Workflow/Organization contracts needed by the reviewed obligations were missing from the original execution scope.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/RegisterExternalSystemRequest.java`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/ExternalSystemResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/command/RegisterExternalSystemCommand.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/dto/ExternalSystemSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/ExternalSystemJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ExternalSystemSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce uniqueness of `ExternalSystem.code`.** Implement the explicit DDD invariant with a concurrency-safe authoritative mechanism, preferably the DDD-specified unique database index/constraint plus appropriate application conflict handling.
  2. **Enforce `EXTERNAL_SYSTEM_TYPE` catalog-family semantics for `systemTypeId`.** Generic Integration catalog-row existence is insufficient; the selected row must belong to the documented external-system-type family.
  3. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Do not add a cross-module database FK; fail closed through an Organization-owned lookup/application contract or equivalent boundary.

#### HMR-047A — ExternalSystem registration/Organization owner prerequisite

- Source: HMR-047 / HMSR-056 plus live Integration/Organization boundary evidence.
- Exact commit: `docs(model-remediation): register ExternalSystem owner prerequisite`
- Status: **Completed** — the missing Integration registration service, Organization-owned Integration lookup contract, owner-side implementation/test, and architecture guardrail registrations are authorized; HMR-047A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. HMR-047 requires deterministic application conflict handling for unique `ExternalSystem.code`, family validation for `systemTypeId`, and fail-closed validation of populated `ownerOrganizationUnitId`.
  2. The authoritative registration path is `IntegrationApplicationService.registerExternalSystem(...)`, but `IntegrationApplicationService.java` is absent from the original HMR-047 allowlist.
  3. No `organization.application.contract.integration` package or equivalent deliberate Organization-to-Integration export exists on live `main`.
  4. The previously recorded Organization candidates are internal ports/use cases and must not be imported directly by Integration under the repository cross-module guardrails.
  5. `OrganizationUnitRepositoryPort.findById(...)` can resolve owner truth inside Organization and is sufficient for the reviewed existence-only obligation; HMR-047 does not authorize inventing Organization lifecycle or role eligibility.
  6. `hidra_integration_catalog_entry` already exists and can support `EXTERNAL_SYSTEM_TYPE` family checks from the Integration repository boundary.
  7. The registered HMR-047 Flyway migration remains sufficient for the concurrency-safe unique-code database constraint/index; no cross-module Organization FK is authorized.
- Required decisions:
  1. Add `IntegrationApplicationService.java` to the HMR-047 write allowlist.
  2. Organization must export a narrow Integration-facing OrganizationUnit existence contract returning neutral scalar/existence evidence only.
  3. The Organization implementation must use `OrganizationUnitRepositoryPort.findById(...)`; null/blank/unknown IDs fail closed when validation is requested.
  4. `IntegrationApplicationService.registerExternalSystem(...)` must reject an already-used ExternalSystem code before save for deterministic application semantics.
  5. The concurrency-safe authoritative uniqueness remains the HMR-047 database unique constraint/index; the pre-check is not a substitute for it.
  6. `IntegrationApplicationService.registerExternalSystem(...)` must reject a `systemTypeId` that does not belong specifically to `EXTERNAL_SYSTEM_TYPE`.
  7. Populated `ownerOrganizationUnitId` must resolve through the Organization-owned Integration contract before save.
  8. No Organization cross-module database FK is authorized.
  9. Register exactly `dz.sh.hidra.modules.organization.application.contract.integration` in both existing architecture exported-package registries without weakening their algorithms.
- Newly authorized HMR-047 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- Newly authorized HMR-047 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-047 remains the current production remediation after this prerequisite is observed. Do not start HMR-048 automatically.


#### HMR-048 — reporting.ReportRequest

- Source review: `HMSR-057`
- Exact commit: `fix(reporting): remediate semantic review ReportRequest`
- Status: **Completed** — request creation now requires an ACTIVE ReportDefinition, restricted requests require matching Reporting policy plus Identity authorization, populated OrganizationUnit references resolve through Organization, and approval-required queueing requires ReportRequest APPROVED state plus Workflow-owned approval evidence.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, reporting.ReportDefinition
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
- Owner-contract prerequisite: HMR-049A registered — live preflight proved the Risk create service, Risk-facing Organization/Topology scope contracts, and Risk-specific Audit contract were missing from the original execution scope.
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/RequestReportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRequestResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRequestSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRequestJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportRequestSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Reject new requests against inactive ReportDefinition rows.** The authoritative request path must load/validate definition eligibility before creating ReportRequest.
  2. **Enforce access validation for restricted report definitions before request creation.** Use Reporting/Identity access-policy contracts rather than bypassing authorization.
  3. **Prevent queueing approval-required requests before workflow approval.** The queue path must validate the ReportRequest/ReportDefinition approval state through the appropriate Workflow contract before creating a queued ReportRun.
  4. **Validate populated `organizationUnitId` through the Organization owner boundary.** Do not add a cross-module database FK.

#### HMR-048A — ReportRequest access/approval/Organization prerequisite

- Source: HMR-048 / HMSR-057 plus live Reporting/Identity/Workflow/Organization boundary evidence.
- Exact commit: `docs(model-remediation): register ReportRequest owner prerequisites`
- Status: **Completed** — the missing Reporting application orchestration file, consumer-specific Identity/Workflow/Organization contracts, owner-side implementations/tests, and architecture guardrail registrations are authorized; HMR-048A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. HMR-048 requires inactive ReportDefinition rejection, restricted-report access validation, approval gating before queueing, and OrganizationUnit owner validation.
  2. The authoritative request and queue paths are `ReportingApplicationService.requestReport(...)` and `queueReportRun(...)`, but `ReportingApplicationService.java` is outside the original HMR-048 allowlist.
  3. No `identity.application.contract.reporting`, `workflow.application.contract.reporting`, or `organization.application.contract.reporting` package exists on live `main`.
  4. Reporting already owns `ReportAccessPolicy` persistence metadata, but the DDD states Identity evaluates permissions. Reporting must not replace Identity authorization with ad hoc local policy-only checks.
  5. Workflow already demonstrates the deliberate consumer-specific export pattern through `workflow.application.contract.planning.PlanningWorkflowContract`; Reporting requires its own narrow approval contract rather than importing Workflow private application packages.
  6. Organization can resolve OrganizationUnit existence via `OrganizationUnitRepositoryPort.findById(...)`; HMR-048 requires existence only and does not authorize inventing lifecycle/eligibility semantics.
  7. ReportDefinition and ReportRequest are Reporting-owned and may be loaded through existing Reporting repositories; no cross-module DB FK is required for Identity, Workflow, or Organization.
- Required decisions:
  1. Add `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java` to the HMR-048 write allowlist.
  2. Identity must export a narrow Reporting authorization contract that evaluates whether the requesting actor is allowed to request the specific restricted report under the Reporting access-policy context. The contract must return neutral allow/deny evidence only and must not leak Identity domain/JPA types.
  3. Reporting remains owner of ReportAccessPolicy metadata. The Reporting request path may assemble neutral policy scope inputs, but the authorization decision must come from Identity for restricted definitions.
  4. Workflow must export a narrow Reporting approval contract that can answer whether a populated workflow reference represents an approved workflow state suitable for queueing this report request. Do not import Workflow internal repositories/domain types into Reporting.
  5. Organization must export a narrow Reporting-facing OrganizationUnit existence contract implemented via `OrganizationUnitRepositoryPort.findById(...)`.
  6. `requestReport(...)` must load the selected ReportDefinition and fail closed unless it is ACTIVE.
  7. For restricted definitions, `requestReport(...)` must fail closed unless the Identity-owned Reporting access contract allows the request.
  8. Populated `organizationUnitId` must resolve through the Organization-owned Reporting contract before the ReportRequest is persisted.
  9. `queueReportRun(...)` must load both ReportRequest and ReportDefinition. If the definition requires approval, the request must be APPROVED, `workflowReferenceId` must be nonblank, and the Workflow-owned Reporting approval contract must confirm approval before a QUEUED ReportRun is created.
  10. No cross-module Identity/Workflow/Organization database FK is authorized.
  11. Register exactly these exported packages in both existing architecture guardrail registries:
      - `dz.sh.hidra.modules.identity.application.contract.reporting`
      - `dz.sh.hidra.modules.workflow.application.contract.reporting`
      - `dz.sh.hidra.modules.organization.application.contract.reporting`
      Do not weaken guardrail algorithms or add wildcard/transitional exceptions.
- Newly authorized HMR-048 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- Newly authorized HMR-048 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-048 remains the current production remediation after this prerequisite is observed. Do not start HMR-049 automatically.


#### HMR-049 — risk.RiskRegister

- Source review: `HMSR-058`
- Exact commit: `fix(risk): remediate semantic review RiskRegister`
- Status: **Completed** — registerTypeId is constrained to active RISK_REGISTER_TYPE, populated ownerOrganizationUnitId resolves through Organization, typed scopes fail closed through registered Organization/Topology owner contracts, reviewFrequencyId remains explicitly unresolved/opaque, and RiskRegister creation is transactionally recorded through an Audit-owned RISK_REGISTER_CREATED contract.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskRegisterRequest.java`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskRegisterResponse.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskRegisterCommand.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskRegisterSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskRegisterJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskRegisterSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce Risk register-type family eligibility.** A mandatory `registerTypeId` must resolve to an eligible `RiskCatalogEntry` in the `RISK_REGISTER_TYPE` family; generic FK row existence is insufficient.
  2. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Preserve snapshot semantics and do not add a cross-module database FK.
  3. **Make typed scope identity fail closed.** Preserve `scopeType + scopeId` as a non-relational typed namespace, require a semantically complete pair at the authoritative write boundary, and resolve supported types through their owning contracts rather than inventing one relational target.
  4. **Resolve `reviewFrequencyId` controlled-value semantics.** Current DDD does not identify a defensible review-frequency catalog family; do not map it to `RISK_REVIEW_TYPE` without stronger evidence.
  5. **Honor the explicit register creation/update audit-event contract.** Integrate the authoritative write path with the existing Risk audit/outbox architecture when production reconciliation is authorized.

#### HMR-049A — RiskRegister owner/scope/audit prerequisite

- Source: HMR-049 / HMSR-058 plus live Risk/Organization/Topology/Audit evidence.
- Exact commit: `docs(model-remediation): register RiskRegister owner prerequisites`
- Status: **Completed** — the missing Risk application orchestration file, deliberate owner contracts, Audit contract/adapter, focused tests, and architecture guardrail registrations are authorized; HMR-049A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. The authoritative RiskRegister creation path is `RiskApplicationService.createRiskRegister(...)`, but `RiskApplicationService.java` is absent from the original HMR-049 allowlist.
  2. No `organization.application.contract.risk`, `topology.application.contract.risk`, or `audit.application.contract.risk` package exists on live `main`.
  3. `RiskRegister.registerTypeId` is a same-module Risk catalog reference, but generic row existence is insufficient; family membership must be `RISK_REGISTER_TYPE`.
  4. `ownerOrganizationUnitId` is cross-module and must remain free of a database FK.
  5. The repository has authoritative owner evidence for these Risk scope types only:
     - `ORGANIZATION_UNIT` -> OrganizationUnit repository;
     - `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, `EQUIPMENT` -> Topology repositories/contracts.
     Broader Risk DDD examples such as `PIPELINE_SEGMENT`, `OPERATIONAL_PLAN`, `INCIDENT`, `INTEGRITY_CASE`, `HSE_CASE`, and `SIMULATION_SCENARIO` do not yet have Risk-facing owner contracts and must not be silently accepted.
  6. The legacy generic module event/outbox path was explicitly rejected/removed by ADR 0006 / HRA-031B. The current repository pattern for business audit integration is consumer-specific Audit-owned application contracts such as `audit.application.contract.alarm` and `audit.application.contract.organization`, adapted to `RecordAuditEventUseCase`.
  7. `reviewFrequencyId` has no defensible catalog-family owner in current DDD. `RISK_REVIEW_TYPE` is not evidence for review frequency and must not be substituted.
- Required decisions:
  1. Add `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java` to HMR-049 production scope.
  2. Organization must export a narrow Risk-facing contract for:
     - OrganizationUnit existence;
     - resolution of `ORGANIZATION_UNIT` scope identity.
     It may use `OrganizationUnitRepositoryPort.findById(...)`; do not invent lifecycle eligibility beyond existence.
  3. Topology must export a narrow Risk-facing typed scope contract supporting only the owner-backed types currently evidenced: `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, and `EQUIPMENT`. Unsupported type codes fail closed.
  4. Risk creation must require nonblank `scopeType` and `scopeId` as a complete pair and resolve the pair through the corresponding owner contract before persistence.
  5. Scope types without a registered owner contract must fail closed; do not infer or create cross-module relational targets.
  6. `registerTypeId` must belong specifically to Risk catalog family `RISK_REGISTER_TYPE`, enforced through Risk repository/catalog lookup before save.
  7. Populated `ownerOrganizationUnitId` must resolve through the Organization-owned Risk contract before save.
  8. Preserve `reviewFrequencyId` as nullable opaque controlled-value state for this HMR. Do not validate it against `RISK_REVIEW_TYPE`, do not create a new catalog family, and document the unresolved semantic owner for later remediation/closure review.
  9. Audit must export a Risk-specific `RiskRegisterAuditContract` owned by the Audit module, with an Audit infrastructure adapter delegating to the existing `RecordAuditEventUseCase`. HMR-049 must record RiskRegister creation after successful persistence using neutral scalar/snapshot data.
  10. Do not recreate the removed generic platform outbox/event-publisher architecture.
  11. HMR-049 migration may harden same-table/domain-enforceable invariants only. No Organization/Topology/Audit cross-module FK is authorized.
  12. Register exactly these new exported packages in both architecture guardrail registries:
      - `dz.sh.hidra.modules.organization.application.contract.risk`
      - `dz.sh.hidra.modules.topology.application.contract.risk`
      - `dz.sh.hidra.modules.audit.application.contract.risk`
      Do not add wildcard/transitional exemptions.
- Newly authorized HMR-049 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- Newly authorized HMR-049 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-049 remains the current production remediation after this prerequisite is observed. Do not start HMR-050 automatically.


#### HMR-049B — RiskRegister Audit taxonomy prerequisite

- Source: HMR-049 / HMR-049A plus live Audit catalog provisioning evidence.
- Exact commit: `docs(model-remediation): register RiskRegister audit taxonomy prerequisite`
- Status: **Completed** — the missing Audit-owned taxonomy provisioning needed by the RiskRegister creation audit contract is authorized; HMR-049B itself changes documentation only.
- Type: documentation/audit-taxonomy prerequisite.
- Live evidence:
  1. The current Audit integration pattern resolves active `EVENT_TYPE` and `EVENT_CATEGORY` rows before calling `RecordAuditEventUseCase`.
  2. `EVENT_CATEGORY/BUSINESS` is already provisioned by existing Audit migrations.
  3. No active `EVENT_TYPE/RISK_REGISTER_CREATED` exists in repository migrations.
  4. Reusing `ALARM_SUPPRESSION_EXPIRED`, Organization responsibility event types, or another unrelated code would corrupt Audit taxonomy semantics.
  5. HMR-049A deliberately prohibited using the Risk migration to mutate unrelated Audit-owned taxonomy.
- Required decisions:
  1. Add one Audit-owned additive migration:
     `src/main/resources/db/migration/V20261005_001__provision_risk_register_created_audit_taxonomy.sql`.
  2. The migration may insert only the missing active `EVENT_TYPE/RISK_REGISTER_CREATED` row, idempotently, using the same repository-approved pattern as the Alarm and Organization audit-taxonomy migrations.
  3. Do not duplicate `EVENT_CATEGORY/BUSINESS`; the adapter must continue to resolve the existing active category.
  4. HMR-049's Audit adapter must fail closed when either required active Audit taxonomy row is unavailable.
  5. No generic platform outbox or new Audit business vocabulary beyond `RISK_REGISTER_CREATED` is authorized.
- Newly authorized HMR-049 production file:
  - `src/main/resources/db/migration/V20261005_001__provision_risk_register_created_audit_taxonomy.sql`
- HMR-049 remains the current production remediation after this prerequisite is observed. Do not start HMR-050 automatically.


#### HMR-050 — integrity.IntegrityProgram

- Source review: `HMSR-059`
- Exact commit: `fix(integrity): remediate semantic review IntegrityProgram`
- Status: **Completed** — exact-current revalidation found the HRA-111 same-module `program_type_id -> IntegrityCatalogEntry` FK already present, so the legacy `V20261004_050` migration is not created out-of-order. `INTEGRITY_PROGRAM_TYPE` is now the explicit active catalog family, and populated ownerOrganizationUnitId values fail closed through the Organization-owned Integrity contract while the supplied organization-name snapshot is preserved.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit
- HMSR correction count: 2
- Additive Flyway: **Not required after HPR-P2-008 exact-current revalidation.** Historical registered filename was `src/main/resources/db/migration/V20261004_050__hmr_050_integrity_integrity_program.sql`; it is intentionally not created because the required same-module FK already exists and that legacy version would sort before the current applied tail.
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Integrity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/CreateIntegrityProgramRequest.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityProgramResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/command/CreateIntegrityProgramCommand.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityProgramSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityProgramStatus.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityProgramRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityProgramJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_050__hmr_050_integrity_integrity_program.sql`
  - `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityProgramSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrityProgramSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Resolve `programTypeId` controlled-value semantics.** Keep the same-module `IntegrityCatalogEntry` FK, but explicitly define the program-type catalog family/eligibility semantics before treating arbitrary catalog-row existence as sufficient.
  2. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Preserve the organization-name snapshot and do not add a cross-module database FK.

#### HMR-051 — leakdetection.LeakDetectionCase

- Source review: `HMSR-060`
- Exact commit: `fix(leakdetection): remediate semantic review LeakDetectionCase`
- Status: **Completed — implementation and focused tests added; final batch CI is the integration gate.**
- SCC: —
- Recorded upstream HMS dependencies: leakdetection.LeakCandidate, organization.OrganizationUnit
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_051__hmr_051_leakdetection_leak_detection_case.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/LeakDetection.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakDetectionCaseRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakDetectionCaseStatus.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakDetectionCaseRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/mapper/LeakDetectionPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakDetectionCaseJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_051__hmr_051_leakdetection_leak_detection_case.sql`
  - `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakDetectionCaseSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=LeakDetectionCaseSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate the mandatory typed Topology asset reference through a Topology-owned boundary.** Preserve `topologyAssetType + topologyAssetId` as a cross-module typed identity and `topologyAssetCode` as snapshot metadata; do not add a cross-module database FK.
  2. **Validate populated `owningOrganizationUnitId` through the Organization owner boundary.** Do not add a cross-module database FK.

- Exact-current execution: Topology and optional Organization references validated on every case save; snapshot preserved; no migration because primary-candidate FK already exists; owner contract and architecture export added.

#### HMR-052 — notification.NotificationMessage

- Source review: `HMSR-061`
- Exact commit: `fix(notification): remediate semantic review NotificationMessage`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: notification.NotificationRequest, notification.NotificationTemplate
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_052__hmr_052_notification_notification_message.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/CreateNotificationMessageRequest.java`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationMessageResponse.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/command/CreateNotificationMessageCommand.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationMessageSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageVariableJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_052__hmr_052_notification_notification_message.sql`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationMessageSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce request/recipient consistency.** A `recipientId` must identify a NotificationRequestRecipient owned by the same `requestId` carried by the message; independent FK existence is insufficient.
  2. **Enforce exact template-version traceability before READY/SCHEDULED/dispatch for template-rendered messages.** Resolve `templateVersionId`, preserve its relationship to `templateId`, and prevent a rendered/template-backed message from progressing without the exact immutable version used.
  3. **Enforce required template variables before READY.** Validate the selected template version's required variable contract against the message-owned variable set before the message can enter a sendable state.
  4. **Validate populated `priorityId` against the `NOTIFICATION_PRIORITY` family and eligibility semantics.**

#### HMR-053 — telemetry.TrustedTelemetryReading

- Source review: `HMSR-062`
- Exact commit: `fix(telemetry): remediate semantic review TrustedTelemetryReading`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: telemetry.TelemetryPoint, telemetry.TelemetryReading
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_053__hmr_053_telemetry_trusted_telemetry_reading.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_053__hmr_053_telemetry_trusted_telemetry_reading.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TrustedTelemetryReadingSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce reading/point/assessment consistency.** The trusted reading, source TelemetryReading and justifying TelemetryQualityAssessment must refer to the same reading/point identity.
  2. **Implement the DDD trust gate at an authoritative Telemetry application boundary.** A trusted reading must be created only from a passing quality assessment with an explicitly defined acceptable TrustLevel; a bare repository save is insufficient as the semantic creation contract.
  3. **Enforce ACTIVE TelemetryPoint eligibility before producing a trusted reading.**
  4. **Validate `qualityCodeId` against the Telemetry `QUALITY_CODE` family and its lifecycle/eligibility semantics.**
  5. **Close same-module provenance/reference integrity for populated `unitId` and `ingestionBatchId`, and preserve the active TelemetryPointBinding snapshot coherently at trust time.** Do not introduce cross-module Topology FKs.

#### HMR-054 — topology.Equipment

- Source review: `HMSR-063`
- Exact commit: `fix(topology): remediate semantic review Equipment`
- Status: **Blocked — owner lookup contract prerequisite unresolved**
- SCC: —
- Recorded upstream HMS dependencies: party.Party, topology.Facility
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_054__hmr_054_topology_equipment.sql`
- Owner-contract prerequisite: No suitable exported owner lookup found in live tree for party; task must introduce/authorize an owner contract by roadmap amendment before cross-module validation changes.
- Exact write allowlist:
  - `docs/data definition/Topology.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/topology.md`
  - `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentKind.java`
  - `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeDefinitionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeValueJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_054__hmr_054_topology_equipment.sql`
  - `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=EquipmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Remove the duplicated EquipmentKind business-taxonomy source of truth.** Reconcile `Equipment.equipmentKind` and `EquipmentType.equipmentKind` with the roadmap's catalog-backed EquipmentType architecture rather than preserving fixed enum classification in parallel.
  2. **Protect populated same-module attachment references.** `facilityId`, `nodeId` and `pipelineSegmentId` should fail closed against their Topology-owned targets using the appropriate same-module FK and/or application validation strategy.
  3. **Validate populated `manufacturerPartyId` through a Party-owned application/lookup boundary.** Preserve manufacturer snapshots and do not introduce a cross-module database FK.

#### HMR-055 — workflow.WorkflowInstance

- Source review: `HMSR-064`
- Exact commit: `fix(workflow): remediate semantic review WorkflowInstance`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: workflow.WorkflowDefinition, workflow.WorkflowStep
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261004_055__hmr_055_workflow_workflow_instance.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_055__hmr_055_workflow_workflow_instance.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowInstanceSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Resolve the definition before start and enforce ACTIVE/version consistency.** `definitionId` must identify an ACTIVE WorkflowDefinition and `definitionVersion` must match the definition version being instantiated.
  2. **Require an active WorkflowDefinitionTargetBinding for the exact definition + target module/type + workflow purpose combination before start.**
  3. **Reconcile workflow-purpose controlled-value semantics and nullability.** Purpose participates in binding and non-terminal uniqueness but its exact catalog-family contract is not currently explicit; do not invent one.
  4. **Validate target type and target identity correctly.** Enforce `WORKFLOW_TARGET_TYPE` family eligibility and validate `targetModule + targetTypeId + targetId` through an owner-controlled target lookup while preserving snapshots; do not add cross-module DB FKs.
  5. **Protect current-step and non-terminal-instance integrity.** A populated `currentStepId` must resolve to a WorkflowStep belonging to the selected definition, and the DDD rule of at most one non-terminal instance per target + purpose must be enforced transactionally/database-safe.
  6. **Validate the mandatory starter actor through the Identity-owned actor/authority boundary while preserving actor snapshots.**

#### HMR-056 — integration.IntegrationExchangeMessage

- Source review: `HMSR-067`
- Exact commit: `fix(integration): remediate semantic review IntegrationExchangeMessage`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: integration.ExternalSystem, integration.IntegrationJobRun
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_056__hmr_056_integration_integration_exchange_message.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_056__hmr_056_integration_integration_exchange_message.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrationExchangeMessageSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated `jobRunId` as a same-module IntegrationJobRun reference.** Preserve optionality, but prevent dangling run identities.
  2. **Protect and validate populated `endpointId`.** Resolve the Integration-owned endpoint and ensure it belongs to the same `externalSystemId` recorded by the message.
  3. **Resolve message-type and payload-format controlled-value semantics.** Generic IntegrationCatalogEntry existence is insufficient unless the repository explicitly defines the allowed family/eligibility contract; HMSR-067 does not invent missing family names.

#### HMR-057 — reporting.ReportRun

- Source review: `HMSR-068`
- Exact commit: `fix(reporting): remediate semantic review ReportRun`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: reporting.ReportDefinition, reporting.ReportRequest
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_057__hmr_057_reporting_report_run.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/QueueReportRunRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRunJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_057__hmr_057_reporting_report_run.sql`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Correct the erroneous HRA-111 foreign key for `reportRequestId`.** `hidra_reporting_run.report_request_id` must reference `hidra_reporting_request.id`, not `hidra_reporting_catalog_entry.id`.
  2. **Enforce request/definition consistency before queueing.** The selected ReportRequest must belong to the same ReportDefinition recorded by the run.
  3. **Enforce template-version lineage before queueing.** The exact ReportTemplateVersion must resolve through its ReportTemplate to the same ReportDefinition as the request/run.
  4. **Queue only an eligible request with all required parameters present.** Reuse the previously recorded HMSR-057 request approval/access semantics; do not bypass them from the run-queue path.
  5. **Enforce the explicit terminal-state invariants.** COMPLETED requires `completedAt`; FAILED requires `failureReason` at the authoritative domain/transition boundary.

#### HMR-058 — risk.RiskAssessment

- Source review: `HMSR-069`
- Exact commit: `fix(risk): remediate semantic review RiskAssessment`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: risk.RiskRegister
- HMSR correction count: 5
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskAssessmentRequest.java`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskAssessmentResponse.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskAssessmentCommand.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskAssessmentSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/ResidualRiskAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScopeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/ResidualRiskAssessmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScopeJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskAssessmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce exact catalog-family eligibility for `assessmentTypeId` and `methodologyId`.** Generic RiskCatalogEntry existence is insufficient; use `RISK_ASSESSMENT_TYPE` and `RISK_METHODLOGY`.
  2. **Implement the mandatory assessment-scope contract.** A RiskAssessment must have at least one authoritative `RiskAssessmentScope`; reconcile the parent scalar `scopeId` as convenience/primary-scope state rather than treating it as a substitute for the required child scope set.
  3. **Protect populated scoring/confidence references.** Likelihood, consequence, rating and confidence IDs must resolve to the correct Risk catalog families, and scored tuples must be produced through the applicable Risk scoring/matrix policy.
  4. **Enforce evidence-before-approval and approval workflow/actor semantics.** Approval must verify evidence, resolve the authorized actor/workflow context, record approval metadata and emit audit-ready evidence without cross-module DB FKs.
  5. **Enforce approved-assessment immutability.** APPROVED/ACTIVE assessments must not be modified in place except through the DDD's review/revision path.

#### HMR-059 — leakdetection.LeakEscalationReference

- Source review: `HMSR-071`
- Exact commit: `fix(leakdetection): remediate semantic review LeakEscalationReference`
- Status: **Completed — implementation and focused tests added; final batch CI is the integration gate.**
- SCC: —
- Recorded upstream HMS dependencies: leakdetection.LeakCandidate, leakdetection.LeakDetectionCase
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_059__hmr_059_leakdetection_leak_escalation_reference.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/LeakDetection.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakEscalationReferenceRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakEscalationReferenceRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/mapper/LeakDetectionPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakEscalationReferenceJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_059__hmr_059_leakdetection_leak_escalation_reference.sql`
  - `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakEscalationReferenceSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=LeakEscalationReferenceSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated `candidateId` as a same-module LeakCandidate reference.** Preserve optionality, but prevent dangling candidate identities using the appropriate same-module FK and/or authoritative application validation. Do not invent a case-primary-candidate equality rule without stronger DDD evidence.

- Exact-current execution: Optional candidate validated before save and protected by V20261006_001 nullable same-module FK with fail-closed orphan preflight; no case-primary equality rule.

#### HMR-060 — notification.NotificationDeliveryAttempt

- Source review: `HMSR-072`
- Exact commit: `fix(notification): remediate semantic review NotificationDeliveryAttempt`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: notification.NotificationMessage
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationDeliveryAttemptResponse.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationDeliveryAttemptSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationDeliveryAttemptJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationDeliveryAttemptSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce message/channel consistency.** The attempt's `channelId` must match the referenced NotificationMessage's selected channel unless an explicitly modeled future failover rule authorizes otherwise.
  2. **Enforce append-only delivery-attempt persistence.** Existing attempt evidence must not be mutable through a generic save/update path.
  3. **Prevent automatic retry scheduling after permanent failure/cancellation.** `FAILED_PERMANENT` and `CANCELLED` attempts must not carry an automatic `nextRetryAt`; manual requeue must be represented through the explicit Notification retry workflow.

#### HMR-061 — workflow.WorkflowTransition

- Source review: `HMSR-073`
- Exact commit: `fix(workflow): remediate semantic review WorkflowTransition`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: workflow.WorkflowDefinition, workflow.WorkflowStep
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_061__hmr_061_workflow_workflow_transition.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionController.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ExecuteWorkflowTransitionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionExecutionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/ExecuteWorkflowTransitionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionExecutionDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionConflictException.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionDeniedException.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTransitionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_061__hmr_061_workflow_workflow_transition.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowTransitionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce definition/step composition at transition configuration time.** Both source and destination steps must belong to the transition's WorkflowDefinition.
  2. **Enforce `fromStepId != toStepId` as a configuration/domain invariant**, not only as an execution-time guard.
  3. **Enforce uniqueness of `definitionId + fromStepId + decision`.** Prevent ambiguous duplicate configured decisions from one source step.
  4. **Prevent unsupported condition/callback configuration from becoming executable ACTIVE workflow configuration until governed evaluators/contracts exist**, or implement those contracts explicitly. Preserve the current fail-closed runtime behavior.

#### HMR-062 — incident.Incident

- Source review: `HMSR-074`
- Exact commit: `fix(incident): remediate semantic review Incident`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowInstance
- HMSR correction count: 5
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Incident.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/incident/api/IncidentApi.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/IncidentRestApi.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentController.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentQueryController.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/SpringIncidentController.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/mapper/IncidentGeneratedRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/mapper/IncidentRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/CloseIncidentRequest.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/OpenIncidentRequest.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/RecordIncidentResponseActionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/response/IncidentResponse.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/command/CloseIncidentCommand.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/command/OpenIncidentCommand.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/command/RecordIncidentResponseActionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/dto/IncidentSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/mapper/IncidentApplicationMapper.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/in/CloseIncidentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/in/IncidentQueryUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/in/OpenIncidentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/in/RecordIncidentResponseActionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentClosureRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRelatedIncidentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentResponseActionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/exception/IncidentDomainException.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/exception/IncidentLifecycleViolationException.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/exception/InvalidIncidentValueException.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/policy/IncidentBoundaryPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/service/IncidentLifecycleGuard.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentEvidenceType.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentId.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentSourceType.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/IncidentInfrastructure.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/configuration/IncidentModuleConfiguration.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/IncidentExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/NoopIncidentExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/IncidentPersistence.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRelatedIncidentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentResponseActionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAssignmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAttachmentReferenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEscalationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEvidenceLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentImpactAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResolutionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRootCauseAnalysisJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentTimelineEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/mapper/IncidentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentAssignmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentAttachmentReferenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentCatalogEntryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentCatalogTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentClosureJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentEscalationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentEvidenceLinkJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentImpactAssessmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRelatedIncidentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentResolutionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentResponseActionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRootCauseAnalysisJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentTimelineEntryJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IncidentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce Incident catalog-family eligibility.** Validate `classificationId`, `severityId`, and populated `priorityId` against `INCIDENT_CLASSIFICATION`, `INCIDENT_SEVERITY`, and `INCIDENT_PRIORITY`.
  2. **Validate cross-context identities through owner boundaries.** Validate mandatory creator identity and populated responsible Organization/actor/workflow references while preserving snapshots; do not add cross-module DB FKs.
  3. **Validate populated typed Topology asset identity through a Topology-owned contract** while preserving asset snapshots.
  4. **Enforce the explicit Incident temporal/state invariants:** `detectedAt <= reportedAt` unless an explicit estimated-time model exists, `closedAt => CLOSED`, and `resolvedAt => RESOLVED/CLOSED`.
  5. **Require responsible-owner snapshot before CLOSED state** and ensure the closure workflow cannot bypass that Incident-owned invariant.

#### HMR-063 — identity.User

- Source review: `HMSR-075`
- Exact commit: `fix(identity): remediate semantic review User`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: organization.Employee
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_063__hmr_063_identity_user.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
  - `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/UserResponse.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/dto/UserSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserGroupMembershipJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_063__hmr_063_identity_user.sql`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=UserSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce non-blank username at the authoritative Identity domain/application boundary.** Do not rely on the JPA/Flyway NOT NULL failure as the business validation mechanism.
  2. **Enforce DDD-declared username and nullable-email uniqueness in PostgreSQL.** Add concurrency-safe uniqueness matching `uk_identity_user_username` and unique-if-present email semantics; optional application duplicate checks may supplement but not replace database protection.
  3. **Validate populated `employeeReferenceId` through an Organization-owned Employee lookup/application contract.** Preserve the scalar reference and do not add a cross-module database FK.

#### HMR-064 — planning.PlanRevision

- Source review: `HMSR-076`
- Exact commit: `fix(planning): remediate semantic review PlanRevision`
- Status: **Planned**
- SCC: SCC-04
- Recorded upstream HMS dependencies: planning.OperationalPlan, planning.PlanRevision, workflow.WorkflowInstance
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_064__hmr_064_planning_plan_revision.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_064__hmr_064_planning_plan_revision.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
  - `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanRevisionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce revision-number semantics.** Require a positive revision number and add concurrency-safe PostgreSQL uniqueness for `(plan_id, revision_number)`.
  2. **Protect populated base-revision lineage.** Fail closed when `baseRevisionId` is populated, using a same-module FK or another explicitly justified same-module existence boundary; retain the existing no-self-reference rule.
  3. **Enforce the `REVISION_REASON` catalog family.** Validate populated `changeReasonCodeId` against an eligible/active Planning catalog entry from that exact family.
  4. **Enforce approved-revision immutability.** The metadata-update path must reject changes to an approved revision; supersession/post-approval change must proceed through creation of a new revision as defined by the Planning DDD.

#### HMR-065 — planning.OperationalPlan

- Source review: `HMSR-077`
- Exact commit: `fix(planning): remediate semantic review OperationalPlan`
- Status: **Planned**
- SCC: SCC-04
- Recorded upstream HMS dependencies: organization.OrganizationUnit, planning.PlanRevision, planning.PlanningPeriod
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261004_065__hmr_065_planning_operational_plan.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreateOperationalPlanRequest.java`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/OperationalPlanResponse.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/command/CreateOperationalPlanCommand.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/dto/OperationalPlanSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/OperationalPlanJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_065__hmr_065_planning_operational_plan.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=OperationalPlanSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce DDD-required `nameFr` and `topologyScopeType` before persistence.**
  2. **Enforce global plan-code uniqueness in PostgreSQL** for `hidra_planning_operational_plan.code`.
  3. **Enforce `PLAN_TYPE` catalog-family eligibility** for mandatory `planTypeId`; the existing generic catalog FK is not sufficient.
  4. **Validate mandatory Topology operational-scope identity through a Topology-owned contract** while preserving scope snapshots and without a cross-module DB FK.
  5. **Validate cross-context ownership references through owner-controlled contracts:** mandatory creator actor and populated responsible Organization unit; do not introduce cross-module FKs.
  6. **Protect populated current/approved revision pointers and their parent-plan correlation.** Both IDs must reference existing Planning revisions belonging to the same OperationalPlan; use same-module persistence/application integrity without weakening the SCC boundary.

#### HMR-066 — workflow.WorkflowTask

- Source review: `HMSR-078`
- Exact commit: `fix(workflow): remediate semantic review WorkflowTask`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowStep
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_066__hmr_066_workflow_workflow_task.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTaskRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTaskCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_066__hmr_066_workflow_workflow_task.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowTaskSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce assignment-target presence.** An actionable WorkflowTask must have at least one assignment target: actor or organization unit.
  2. **Validate actor/organization assignment and execution eligibility through owner-controlled contracts.** Preserve scalar IDs/snapshots and do not add cross-module database FKs.
  3. **Enforce explicit claim/completion invariants and terminal immutability.** Couple claimed actor/time, completed actor/time, require `completedAt >= createdAt`, and prevent mutation of completed tasks outside explicitly audit-safe annotations.
  4. **Enforce the `WORKFLOW_PRIORITY` catalog family** for populated `priorityId`, including active/eligible semantics.
  5. **Enforce the `WORKFLOW_ASSIGNMENT_MODE` catalog family** for populated `assignmentModeId`, including active/eligible semantics.

#### HMR-067 — documents.Document

- Source review: `HMSR-079`
- Exact commit: `fix(documents): remediate semantic review Document`
- Status: **Planned**
- SCC: SCC-05
- Recorded upstream HMS dependencies: documents.DocumentVersion
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_067__hmr_067_documents_document.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/documents/api/DocumentsApi.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsContentApiExceptionHandler.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsRestApi.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/DocumentsController.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsController.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsGeneratedRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/LinkDocumentToTargetRequest.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/RegisterDocumentRequest.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentBinaryVersionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentResponse.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/command/LinkDocumentToTargetCommand.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/command/RegisterDocumentCommand.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentBinaryVersionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/mapper/DocumentsApplicationMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/LinkDocumentToTargetUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/RegisterDocumentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentBinaryVersionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentAuditEventPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentContentTransferException.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsBoundaryViolationException.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsDomainException.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/exception/InvalidDocumentValueException.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/policy/DocumentsBoundaryPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/service/DocumentStorageMetadataGuard.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentAccessLevel.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExternalSyncStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionType.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentId.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentPrincipalType.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentReviewStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/DocumentsInfrastructure.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/configuration/DocumentsModuleConfiguration.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/DocumentsExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/NoopDocumentsExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsPersistence.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentAccessGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExternalReferenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExtractionRecordJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentRetentionRecordJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentReviewReferenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentAccessGrantJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogEntryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExternalReferenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExtractionRecordJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentRetentionRecordJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentReviewReferenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/DocumentStorageAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/NoopDocumentStorageAdapter.java`
  - `src/main/resources/db/migration/V20261004_067__hmr_067_documents_document.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce DDD-required `titleFr` and `createdByDisplayNameSnapshot` before persistence.**
  2. **Enforce PostgreSQL uniqueness for `Document.code`.**
  3. **Enforce exact catalog-family eligibility** for `documentTypeId`, `classificationId`, and populated `documentCategoryId`, including active/eligible semantics.
  4. **Protect `currentVersionId` and same-document membership.** When populated, it must reference an existing DocumentVersion belonging to the same Document.
  5. **Validate creator and populated owner-target references through owner-controlled contracts** while preserving scalar IDs/snapshots and avoiding cross-module database FKs.

#### HMR-068 — documents.DocumentVersion

- Source review: `HMSR-080`
- Exact commit: `fix(documents): remediate semantic review DocumentVersion`
- Status: **Planned**
- SCC: SCC-05
- Recorded upstream HMS dependencies: documents.Document, documents.DocumentStorageObject, documents.DocumentVersion, workflow.WorkflowInstance
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_068__hmr_068_documents_document_version.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_068__hmr_068_documents_document_version.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentVersionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required textual upload metadata before persistence:** `mimeType`, `originalFilename`, `checksumAlgorithm`, `checksumValue`, and `uploadedByDisplayNameSnapshot`.
  2. **Enforce version-number semantics:** require `versionNumber >= 1` and add concurrency-safe PostgreSQL uniqueness for `(document_id, version_number)`.
  3. **Protect populated supersession lineage.** `supersededByVersionId` must resolve to an existing Documents-owned version while retaining the existing self-reference prohibition.
  4. **Validate optional Workflow approval references through a Workflow-owned contract** when approval metadata is populated; do not introduce a cross-module Workflow FK.
  5. **Validate mandatory uploader identity through an Identity-owned contract** while preserving the uploader display snapshot and avoiding a cross-module Identity FK.

#### HMR-069 — assets.MaintenanceWorkOrder

- Source review: `HMSR-081`
- Exact commit: `fix(assets): remediate semantic review MaintenanceWorkOrder`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: assets.MaintainableAsset, organization.OrganizationUnit, workflow.WorkflowInstance
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_069__hmr_069_assets_maintenance_work_order.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Assets.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/CreateMaintenanceWorkOrderRequest.java`
  - `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintenanceWorkOrderResponse.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/command/CreateMaintenanceWorkOrderCommand.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintenanceWorkOrderSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
  - `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderTaskJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_069__hmr_069_assets_maintenance_work_order.sql`
  - `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MaintenanceWorkOrderSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce required work-order title before persistence.**
  2. **Protect populated `maintenancePlanId` as a same-module Assets reference** through a same-module FK or explicitly justified fail-closed Assets application boundary.
  3. **Validate cross-context provenance/assignment identities through owner-controlled contracts:** populated Integrity recommendation, Organization unit, assigned actor, and creator actor references; do not introduce cross-module database FKs.
  4. **Validate populated `workflowInstanceId` through a Workflow-owned application contract** and preserve it as a scalar cross-module reference.

#### HMR-070 — custody.CustodyTransferTicket

- Source review: `HMSR-082`
- Exact commit: `fix(custody): remediate semantic review CustodyTransferTicket`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: custody.CustodyMeasurementPeriod, workflow.WorkflowInstance
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_070__hmr_070_custody_custody_transfer_ticket.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Custody.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/CreateCustodyTransferTicketRequest.java`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyTransferTicketResponse.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/command/CreateCustodyTransferTicketCommand.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyTransferTicketSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyTransferTicketJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_070__hmr_070_custody_custody_transfer_ticket.sql`
  - `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=CustodyTransferTicketSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated same-module evidence references.** `batchId` and `quantityCalculationId` must fail closed to existing Custody-owned records.
  2. **Validate populated Identity/Workflow references through owner-controlled contracts.** This covers issuer/approver actor IDs and `workflowInstanceId`; do not introduce cross-module database FKs.
  3. **Preserve Audit ownership for `auditReferenceId` and validate/populate it through an Audit-owned application/publication boundary** rather than direct persistence coupling.

#### HMR-071 — integration.IntegrationDeadLetterRecord

- Source review: `HMSR-084`
- Exact commit: `fix(integration): remediate semantic review IntegrationDeadLetterRecord`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: integration.ExternalSystem, integration.IntegrationExchangeMessage, integration.IntegrationJobRun
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_071__hmr_071_integration_integration_dead_letter_record.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_071__hmr_071_integration_integration_dead_letter_record.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrationDeadLetterRecordSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required failure evidence before persistence.** `failureStage` and `reasonMessage` must be non-blank in addition to the already-enforced `reasonCode` and `status`.
  2. **Protect populated optional same-module evidence references.** `jobRunId`, `exchangeMessageId`, `inboundRecordId`, and `outboundRecordId` must fail closed to existing Integration-owned records.
  3. **Enforce the explicit manual-resolution trio.** When manual resolution is recorded, `resolvedByActorId`, `resolvedAt`, and `resolutionComment` must all be present; resolver identity should be validated through the Identity/security boundary without a cross-module FK.

#### HMR-072 — integrity.IntegrityAssessment

- Source review: `HMSR-085`
- Exact commit: `fix(integrity): remediate semantic review IntegrityAssessment`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: integrity.IntegrityProgram, workflow.WorkflowInstance
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_072__hmr_072_integrity_integrity_assessment.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Integrity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/CreateIntegrityAssessmentRequest.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityAssessmentResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/command/CreateIntegrityAssessmentCommand.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityAssessmentSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssessmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityAssessmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentScopeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentScopeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_072__hmr_072_integrity_integrity_assessment.sql`
  - `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityAssessmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrityAssessmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated `programId` as a same-module Integrity reference.** When present, it must resolve to an existing `IntegrityProgram`.
  2. **Validate populated actor identity through the Identity/security owner boundary.** The live creation path currently accepts `assessedByActorId` directly; future review/approval paths must follow the same ownership rule. Do not add cross-module Identity FKs.
  3. **Validate populated `workflowInstanceId` through a Workflow-owned application contract** and verify the intended Integrity-assessment context; do not introduce a cross-module Workflow FK.

#### HMR-073 — organization.EmployeeAssignment

- Source review: `HMSR-087`
- Exact commit: `fix(organization): remediate semantic review EmployeeAssignment`
- Status: **Completed — implementation and focused tests added; final batch CI is the integration gate.**
- SCC: —
- Recorded upstream HMS dependencies: organization.Employee, organization.OrganizationUnit, organization.Position
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_073__hmr_073_organization_employee_assignment.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAssignmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/EmployeeAssignmentApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeAssignmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/EmployeeAssignmentJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_073__hmr_073_organization_employee_assignment.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/EmployeeAssignmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=EmployeeAssignmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce the disabled-unit assignment rule in the authoritative application boundary.** Before creating a new EmployeeAssignment, resolve the target `OrganizationUnit` and reject assignment when the unit is disabled/inactive. Preserve same-module ownership and do not move this cross-record lifecycle lookup into the domain record constructor.

- Exact-current execution: Assignment service resolves same-module OrganizationUnit and rejects missing or non-ACTIVE units before save; existing employee/unit/position FKs retained; no migration.

#### HMR-074 — organization.OrganizationDelegation

- Source review: `HMSR-088`
- Exact commit: `fix(organization): remediate semantic review OrganizationDelegation`
- Status: **Completed — implementation and focused tests added; final batch CI is the integration gate.**
- SCC: —
- Recorded upstream HMS dependencies: organization.Employee, organization.ResponsibilityAssignment
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_074__hmr_074_organization_organization_delegation.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationDelegationRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationDelegationRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationDelegationJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_074__hmr_074_organization_organization_delegation.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationDelegationSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=OrganizationDelegationSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Align persistence nullability for `responsibilityAssignmentId` with the canonical domain contract.** Add an additive Flyway correction that fails closed on any existing null rows before setting `hidra_org_delegation.responsibility_assignment_id` to `NOT NULL`, and update the JPA mapping to `nullable = false`. Preserve the existing same-module FK.

- Exact-current execution: JPA responsibility_assignment_id is mandatory; V20261006_002 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added.

#### HMR-075 — organization.OrganizationHierarchySnapshot

- Source review: `HMSR-089`
- Exact commit: `fix(organization): remediate semantic review OrganizationHierarchySnapshot`
- Status: **Completed — implementation and focused tests added; final batch CI is the integration gate.**
- SCC: —
- Recorded upstream HMS dependencies: organization.Employee
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_075__hmr_075_organization_organization_hierarchy_snapshot.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationHierarchySnapshotRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationHierarchySnapshotRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationHierarchySnapshotJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_075__hmr_075_organization_organization_hierarchy_snapshot.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationHierarchySnapshotSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=OrganizationHierarchySnapshotSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Align persistence nullability for mandatory `capturedByEmployeeId` with the canonical domain contract.** Add an additive Flyway correction that fails closed on any existing null rows before setting `hidra_org_hierarchy_snapshot.captured_by_employee_id` to `NOT NULL`, and update the JPA mapping to `nullable = false`. Preserve the existing same-module FK.

- Exact-current execution: JPA captured_by_employee_id is mandatory; V20261006_003 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added.

#### HMR-076 — organization.ShiftAssignment

- Source review: `HMSR-090`
- Exact commit: `fix(organization): remediate semantic review ShiftAssignment`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: organization.Employee, organization.OrganizationUnit, organization.Shift
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_076__hmr_076_organization_shift_assignment.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Organization.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/organization.md`
  - `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftAssignmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
  - `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftAssignmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ShiftAssignmentJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_076__hmr_076_organization_shift_assignment.sql`
  - `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftAssignmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ShiftAssignmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Align persistence nullability for mandatory `organizationUnitId` with the canonical domain contract.** Add an additive Flyway correction that fails closed on any existing null rows before setting `hidra_org_shift_assignment.organization_unit_id` to `NOT NULL`, and update the JPA mapping to `nullable = false`. Preserve the existing same-module FK.

#### HMR-077 — risk.RiskEvidenceLink

- Source review: `HMSR-091`
- Exact commit: `fix(risk): remediate semantic review RiskEvidenceLink`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: risk.RiskAssessment
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_077__hmr_077_risk_risk_evidence_link.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_077__hmr_077_risk_risk_evidence_link.sql`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskEvidenceLinkSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce the complete external evidence identity tuple before persistence.** `evidenceModule`, `evidenceType`, and `evidenceId` must be nonblank.
  2. **Validate external evidence through owner-controlled Risk lookup/reference ports before saving the link.** Supported module/type combinations must resolve to existing/eligible source evidence; preserve scalar IDs and snapshots and do not introduce cross-module database FKs.

#### HMR-078 — simulation.SimulationCandidateChange

- Source review: `HMSR-092`
- Exact commit: `fix(simulation): remediate semantic review SimulationCandidateChange`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationOptimizationCandidate
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_078__hmr_078_simulation_simulation_candidate_change.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCandidateChangeJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_078__hmr_078_simulation_simulation_candidate_change.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationCandidateChangeSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required candidate-change values before persistence.** `targetType` and `afterValue` must be nonblank in addition to the already-enforced `targetId`.
  2. **Validate `changeTypeId` against the active/eligible exact `SIMULATION_CHANGE_TYPE` catalog family**, not only generic Simulation catalog existence.
  3. **Validate the Topology-owned target through a Topology-owned lookup/application boundary** for supported `targetType`/`targetId` combinations while preserving scalar references and avoiding cross-module database FKs.

#### HMR-079 — simulation.SimulationRecommendation

- Source review: `HMSR-093`
- Exact commit: `fix(simulation): remediate semantic review SimulationRecommendation`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationOptimizationCandidate, simulation.SimulationRun
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_079__hmr_079_simulation_simulation_recommendation.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/PublishSimulationRecommendationRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRecommendationResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/PublishSimulationRecommendationCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRecommendationSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRecommendationJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_079__hmr_079_simulation_simulation_recommendation.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationRecommendationSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required recommendation content before persistence:** `title`, `description`, and `createdAt` must be valid at the domain/application boundary.
  2. **Validate `recommendationTypeId` against the active/eligible exact `SIMULATION_RECOMMENDATION_TYPE` family**, not merely generic Simulation catalog existence.
  3. **When `confidenceLevelId` is supplied, validate it against the active/eligible exact `SIMULATION_CONFIDENCE_LEVEL` family.**
  4. **When `candidateId` is supplied, validate the optional same-module candidate reference fail-closed and add/retain appropriate same-module persistence protection consistent with repository integrity architecture.**
  5. **Make publication emit audit-ready evidence through the Audit-owned/application boundary** as required by the Simulation DDD; do not introduce cross-module database coupling.

#### HMR-080 — planning.Nomination

- Source review: `HMSR-094`
- Exact commit: `fix(planning): remediate semantic review Nomination`
- Status: **Blocked — owner lookup contract prerequisite unresolved**
- SCC: —
- Recorded upstream HMS dependencies: party.Party, planning.PlanRevision
- HMSR correction count: 7
- Additive Flyway: `src/main/resources/db/migration/V20261004_080__hmr_080_planning_nomination.sql`
- Owner-contract prerequisite: No suitable exported owner lookup found in live tree for party; task must introduce/authorize an owner contract by roadmap amendment before cross-module validation changes.
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationScheduleLineJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_080__hmr_080_planning_nomination.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NominationSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce `quantity > 0`**, not merely non-null quantity.
  2. **Enforce strict `periodStart < periodEnd`**, rejecting zero-duration nominations.
  3. **Enforce required `createdAt` and `updatedAt` before persistence.**
  4. **Enforce unique `(revisionId, code)` nomination identity** using application/database integrity consistent with the DDD.
  5. **Validate `nominationTypeId` against the active/eligible exact `NOMINATION_TYPE` family**, not merely generic Planning catalog existence.
  6. **Reconcile product/unit reference ownership** for `productTypeId`, `quantityUnitId`, and `rateUnitId`; the DDD does not establish corresponding Planning catalog families, so the existing generic Planning-catalog FK shape is not by itself sufficient semantic proof.
  7. **Validate supplied Topology and Party references through owner-controlled contracts**, preserving scalar/snapshot fields and avoiding cross-module database FKs; separately reconcile the optional `scenarioId` graph edge during HMS-006 before production changes.

#### HMR-081 — workflow.WorkflowAction

- Source review: `HMSR-095`
- Exact commit: `fix(workflow): remediate semantic review WorkflowAction`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowTask
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261004_081__hmr_081_workflow_workflow_action.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_081__hmr_081_workflow_workflow_action.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowActionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Close the generic decision-action bypass.** Decision-bearing actions must pass through the configured-transition authority; a generic action endpoint must not independently persist workflow decisions.
  2. **Validate supplied `taskId` fail-closed and verify task-to-instance ownership** for task-scoped actions.
  3. **Enforce DDD conditional evidence rules on every authoritative write path:** required reasons for reject/correction/return/delegation/escalation/cancel and required comment for request-correction.
  4. **Enforce required `actorDisplayNameSnapshot` before persistence** so actor evidence fails fast rather than at JPA/database time.
  5. **Make `actionSequence` Workflow-owned, monotonic, race-safe, and protected per instance**, rather than caller-controlled on the generic path.
  6. **Preserve Identity/Organization boundary ownership:** scalar actor/organization references and snapshots only, with owner-controlled validation where required; never introduce cross-module database FKs.

#### HMR-082 — hse.HseCase

- Source review: `HMSR-096`
- Exact commit: `fix(hse): remediate semantic review HseCase`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: incident.Incident, organization.OrganizationUnit, workflow.WorkflowInstance
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_082__hmr_082_hse_hse_case.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): incident:src/main/java/dz/sh/hidra/modules/incident/application/port/in/IncidentQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Hse.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/CloseHseCaseRequest.java`
  - `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/OpenHseCaseRequest.java`
  - `src/main/java/dz/sh/hidra/modules/hse/api/rest/response/HseCaseResponse.java`
  - `src/main/java/dz/sh/hidra/modules/hse/application/command/CloseHseCaseCommand.java`
  - `src/main/java/dz/sh/hidra/modules/hse/application/command/OpenHseCaseCommand.java`
  - `src/main/java/dz/sh/hidra/modules/hse/application/dto/HseCaseSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/hse/application/port/in/CloseHseCaseUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/hse/application/port/in/OpenHseCaseUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCaseRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java`
  - `src/main/java/dz/sh/hidra/modules/hse/domain/service/HseCaseClosureGuard.java`
  - `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`
  - `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseEvidenceLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseStatusHistoryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseEvidenceLinkJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseStatusHistoryJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_082__hmr_082_hse_hse_case.sql`
  - `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCaseSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=HseCaseSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Make HSE closure application-authoritative:** load the referenced `HseCase` and invoke `HseCaseClosureGuard` before creating closure evidence.
  2. **Keep case lifecycle and closure evidence consistent:** successful closure must transition the owning case to `CLOSED`, stamp the appropriate closure timestamp, and persist the corresponding HSE-owned status-history evidence through the established HSE lifecycle pattern.
  3. **Preserve cross-context ownership:** Incident, Organization, Workflow, Audit, and polymorphic target references remain scalar/snapshot references; use owner-controlled lookup/application contracts where live validation is required and never introduce cross-module database FKs.
  4. **Reconcile HSE catalog-family semantics during HMS-006/future authorized work:** generic catalog-row existence already protects mandatory case type/severity references, but exact family membership must not be invented until authoritative HSE catalog-family evidence exists.

#### HMR-083 — audit.AuditExportRequest

- Source review: `HMSR-097`
- Exact commit: `fix(audit): remediate semantic review AuditExportRequest`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: documents.Document, workflow.WorkflowInstance
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_083__hmr_083_audit_audit_export_request.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RequestAuditExportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditExportRequestResponse.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditExportRequestSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditExportRequestJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_083__hmr_083_audit_audit_export_request.sql`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditExportRequestSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce required `filterJson` and `format` before persistence**, not only through DB `NOT NULL`.
  2. **Sanitize and policy-check `filterJson` before persistence/export execution**, preserving the DDD masking rule for sensitive values.
  3. **Validate `purposeId` against the active/eligible exact Audit `EXPORT_PURPOSE` catalog family** and add appropriate same-module integrity protection consistent with repository architecture.
  4. **Preserve Workflow/Documents ownership:** validate optional workflow approval and result-document references through owner-controlled contracts where required; never introduce cross-module database FKs.
  5. **Make audit export activity itself auditable**, including request/access/export execution evidence through Audit-owned application paths while avoiding recursive evidence creation.

#### HMR-084 — documents.DocumentTargetLink

- Source review: `HMSR-098`
- Exact commit: `fix(documents): remediate semantic review DocumentTargetLink`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: documents.Document, documents.DocumentVersion
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_084__hmr_084_documents_document_target_link.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_084__hmr_084_documents_document_target_link.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentTargetLinkSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce required `targetModule` before persistence**, matching the DDD and database contract.
  2. **When `documentVersionId` is supplied, validate it fail-closed and verify that the version belongs to `documentId`; add appropriate same-module persistence protection consistent with repository architecture.**
  3. **Validate `linkRoleId` against the active/eligible exact `DOCUMENT_LINK_ROLE` catalog family**, not merely generic Documents catalog-row existence.
  4. **Use the Documents-owned `DocumentTargetLookupPort` (or equivalent owner-controlled application contract) to validate polymorphic targets where live existence validation is required**, while preserving neutral scalar references and avoiding cross-module database FKs.

#### HMR-085 — identity.AuthorizationDecision

- Source review: `HMSR-100`
- Exact commit: `fix(identity): remediate semantic review AuthorizationDecision`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: identity.User
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDecisionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDecisionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/AuthorizationDecisionJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDecisionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuthorizationDecisionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Replace the unconditional `NO_GRANT_MATCHED` decision path with the governing Identity authorization evaluation order**, including direct/inherited grants, mapped external authority where allowed, scope/time validity, ABAC policy, and explicit deny precedence.
  2. **Populate applicable decision-explanation evidence** (`reasonCode`/`reasonMessage`, matched grants/policy rules/external claims as appropriate) so persisted high-risk decisions are truthful and explainable.
  3. **Honor the established optional authorization-decision persistence policy/configuration** rather than unconditionally persisting every evaluated request if `authorizationDecisionPersistenceEnabled` remains the authoritative switch.

#### HMR-086 — identity.AuthorizationDelegationGrant

- Source review: `HMSR-101`
- Exact commit: `fix(identity): remediate semantic review AuthorizationDelegationGrant`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: identity.Permission, identity.Role, identity.User
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_086__hmr_086_identity_authorization_delegation_grant.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDelegationGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDelegationGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/AuthorizationDelegationGrantJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_086__hmr_086_identity_authorization_delegation_grant.sql`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDelegationGrantSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuthorizationDelegationGrantSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Add the DDD-required delegation `reason` to the canonical target model and persistence/application contracts.**
  2. **Make `validTo` mandatory for delegation grants** while retaining `validFrom <= validTo`.
  3. **Reconcile the unsupported `SUSPENDED` delegation status** against the governing Identity DDD; do not rely on it without an explicit contract.
  4. **Fail closed for supplied optional `roleId`/`permissionId` references** through Identity-owned application validation and appropriate same-module persistence integrity, without inventing an XOR/at-least-one rule absent from the DDD.

#### HMR-087 — identity.LoginSession

- Source review: `HMSR-104`
- Exact commit: `fix(identity): remediate semantic review LoginSession`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: identity.IdentityProvider, identity.User
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=LoginSessionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Add/preserve the DDD-required `sessionType` on the canonical LoginSession model and persistence contract**, reusing the established authentication-protocol vocabulary rather than inventing an unnecessary taxonomy.
  2. **Propagate `externalIdentityId` for externally authenticated sessions when an ExternalIdentity actually participated**, preserving optionality for LOCAL/system paths.
  3. **Add/preserve an explicit `endedAt` lifecycle timestamp and stop using `lastSeenAt` as a substitute for session termination time**; keep activity tracking separate if still required.

#### HMR-088 — identity.UserPermissionGrant

- Source review: `HMSR-105`
- Exact commit: `fix(identity): remediate semantic review UserPermissionGrant`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: identity.Permission, identity.User
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/UserPermissionGrantSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=UserPermissionGrantSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce nonblank `grantReason` before persistence**, matching the DDD-required reason contract.
  2. **Require a bounded `validTo` for direct UserPermissionGrant records**, including emergency/break-glass grants, while retaining `validFrom <= validTo`.
  3. **Reconcile the unsupported `SUSPENDED` state for UserPermissionGrant** against the governing Identity DDD; do not rely on that state without an explicit contract.

#### HMR-089 — identity.UserRoleGrant

- Source review: `HMSR-106`
- Exact commit: `fix(identity): remediate semantic review UserRoleGrant`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: identity.Role, identity.User
- HMSR correction count: 1
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Identity.md`
  - `docs/roadmap/identity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/identity/semantic/UserRoleGrantSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=UserRoleGrantSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce the DDD active-user grant eligibility rule in the authoritative UserRoleGrant creation flow.** Ordinary role grants must require an ACTIVE User; any emergency/break-glass exception must be an explicitly controlled flow supported by repository policy rather than an implicit bypass.

#### HMR-090 — incident.IncidentClosure

- Source review: `HMSR-107`
- Exact commit: `fix(incident): remediate semantic review IncidentClosure`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: incident.Incident, workflow.WorkflowInstance
- HMSR correction count: 4
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Incident.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentClosureRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/mapper/IncidentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentClosureJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentClosureSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IncidentClosureSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce nonblank `closureSummary` before persistence.**
  2. **Make the close use case load and validate the owning Incident, permitting closure only from `RESOLVED`.**
  3. **Persist the Incident lifecycle transition to `CLOSED` with its `closedAt` in the same coherent transaction as the IncidentClosure record.**
  4. **Enforce the DDD closure preconditions for resolution and evidence:** require the necessary IncidentResolution/evidence state and the required verification/review confirmations; keep severity-specific evidence policy evidence-backed rather than inventing thresholds.

#### HMR-091 — incident.IncidentRelatedIncident

- Source review: `HMSR-108`
- Exact commit: `fix(incident): remediate semantic review IncidentRelatedIncident`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: incident.Incident
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_091__hmr_091_incident_incident_related_incident.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Incident.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRelatedIncidentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRelatedIncidentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/mapper/IncidentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRelatedIncidentJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_091__hmr_091_incident_incident_related_incident.sql`
  - `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentRelatedIncidentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IncidentRelatedIncidentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Correct the erroneous HRA-111 FK so `related_incident_id` references `hidra_incident(id)`, not `hidra_incident_catalog_entry(id)`.**
  2. **Enforce the no-self-link invariant before persistence.**
  3. **Validate `relationshipTypeId` as a member of the `RELATED_INCIDENT_RELATIONSHIP_TYPE` catalog family, not merely as any existing incident catalog row.**
  4. **Fail closed on missing `createdAt`, and establish an explicit duplicate/inverse relationship policy consistent with the DDD before adding any uniqueness constraint.**

#### HMR-092 — incident.IncidentResponseAction

- Source review: `HMSR-109`
- Exact commit: `fix(incident): remediate semantic review IncidentResponseAction`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: incident.Incident, organization.OrganizationUnit
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Incident.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/RecordIncidentResponseActionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/command/RecordIncidentResponseActionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/in/RecordIncidentResponseActionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentResponseActionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentResponseActionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/mapper/IncidentPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentResponseActionJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentResponseActionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IncidentResponseActionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Load and validate the owning Incident before recording an action, rejecting creation when `Incident.canReceiveResponseAction()` is false.**
  2. **Enforce nonblank `description` before persistence.**
  3. **Validate `actionTypeId` as a member of the `RESPONSE_ACTION_TYPE` catalog family, not merely as any existing Incident catalog row.**

#### HMR-093 — reporting.ReportOutputArtifact

- Source review: `HMSR-110`
- Exact commit: `fix(reporting): remediate semantic review ReportOutputArtifact`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: documents.Document, documents.DocumentStorageObject, reporting.ReportRun
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: Existing candidate owner contract(s): documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportOutputArtifactResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportOutputArtifactSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportOutputArtifactSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Correct HRA-111 so `report_run_id` references `hidra_reporting_run(id)`, not `hidra_reporting_catalog_entry(id)`.**
  2. **Make artifact generation fail closed when the referenced ReportRun does not exist.**
  3. **Enforce the DDD invariant that every artifact references at least one Documents storage object or Document metadata record; validate supplied cross-module references through Documents-owned contracts without adding cross-module DB FKs.**

#### HMR-094 — planning.PlanTarget

- Source review: `HMSR-111`
- Exact commit: `fix(planning): remediate semantic review PlanTarget`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: planning.Nomination, planning.PlanRevision, telemetry.TelemetryPoint
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_094__hmr_094_planning_plan_target.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): telemetry:src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_094__hmr_094_planning_plan_target.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanTargetSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce target-type-dependent value shape:** numeric target types require `targetValue` and `unitId`; state/text target types must carry the representation required by Planning target-type policy.
  2. **Validate `targetTypeId` as a member of the Planning target-type catalog family, not merely any existing Planning catalog row.**
  3. **Fail closed on missing `topologyAssetType` before persistence; validate supplied topology references through Topology-owned public contracts when required.**
  4. **For supplied optional `nominationId` / `scenarioId`, validate same-module existence and revision compatibility instead of allowing cross-revision references.**
  5. **For supplied `telemetryPointId`, preserve the cross-module scalar boundary and validate through Telemetry-owned contracts when the use case requires a live point.**

#### HMR-095 — audit.AuditEvent

- Source review: `HMSR-112`
- Exact commit: `fix(audit): remediate semantic review AuditEvent`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowTask
- HMSR correction count: 4
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RecordAuditEventRequest.java`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditEventResponse.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/command/RecordAuditEventCommand.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditEventSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditEventJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditEventSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on blank/null `sourceModule`, `targetModule`, and `targetType` before persistence.**
  2. **Validate mandatory `eventTypeId` / `eventCategoryId` and supplied optional `severityId` / `reasonId` against their correct Audit catalog families, not merely any Audit catalog row.**
  3. **Make AuditEvent persistence genuinely append-only so an existing event cannot be silently overwritten or mutated outside explicitly governed evidence-preserving lifecycle behavior.**
  4. **Sanitize/redact audit free-text/structured payload inputs and enforce an evidence-backed size limit for `payloadJson` before persistence; secrets and credential material must never enter the ledger.**

#### HMR-096 — hse.HseClosure

- Source review: `HMSR-113`
- Exact commit: `fix(hse): remediate semantic review HseClosure`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: hse.HseCase, workflow.WorkflowInstance
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_096__hmr_096_hse_hse_closure.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Hse.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseClosureJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_096__hmr_096_hse_hse_closure.sql`
  - `src/test/java/dz/sh/hidra/modules/hse/semantic/HseClosureSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=HseClosureSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Load the referenced `HseCase` and invoke `HseCaseClosureGuard` before creating closure evidence.**
  2. **Keep case lifecycle and closure evidence transactionally consistent:** successful closure must transition the owning HSE case to `CLOSED`, stamp its closure timestamp, and persist corresponding HSE status-history evidence.
  3. **Preserve cross-module ownership:** Workflow and Identity fields remain scalar/snapshot references with owner-controlled validation where required and no cross-module database FKs.

#### HMR-097 — hse.HseCorrectivePreventiveAction

- Source review: `HMSR-114`
- Exact commit: `fix(hse): remediate semantic review HseCorrectivePreventiveAction`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: assets.MaintenanceWorkOrder, hse.HseCase, organization.OrganizationUnit, workflow.WorkflowTask
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_097__hmr_097_hse_hse_corrective_preventive_action.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): assets:src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsAuditReferencePort.java; assets:src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsDocumentReferencePort.java; assets:src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsPartyReferencePort.java; assets:src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTelemetryReferencePort.java; assets:src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTopologyLookupPort.java; assets:src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsWorkflowReferencePort.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Hse.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCorrectivePreventiveActionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCorrectivePreventiveActionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCorrectivePreventiveActionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_097__hmr_097_hse_hse_corrective_preventive_action.sql`
  - `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCorrectivePreventiveActionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=HseCorrectivePreventiveActionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Load/fail closed on the mandatory owning `HseCase` before CAPA creation, and apply only explicitly established HSE case-lifecycle eligibility rules.**
  2. **Validate `actionTypeId` against the intended HSE CAPA action-type catalog family, not merely any HSE catalog row.**
  3. **Preserve cross-module ownership for Organization, Assets, Workflow, and Identity references: validate through owner-controlled contracts when required and do not introduce cross-module database FKs.**

#### HMR-098 — integrity.IntegrityCase

- Source review: `HMSR-115`
- Exact commit: `fix(integrity): remediate semantic review IntegrityCase`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: hse.HseCase, incident.Incident, integrity.PipelineDefect, organization.OrganizationUnit, workflow.WorkflowInstance
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_098__hmr_098_integrity_integrity_case.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): hse:src/main/java/dz/sh/hidra/modules/hse/application/port/in/HseQueryUseCase.java; hse:src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAlarmReferencePort.java; hse:src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAuditReferencePort.java; hse:src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseDocumentReferencePort.java; hse:src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseIncidentReferencePort.java; hse:src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseLeakDetectionReferencePort.java; hse:src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseTopologyReferencePort.java; hse:src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseWorkflowReferencePort.java; incident:src/main/java/dz/sh/hidra/modules/incident/application/port/in/IncidentQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Integrity.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/OpenIntegrityCaseRequest.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityCaseResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/command/OpenIntegrityCaseCommand.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityCaseSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityCaseRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityCaseRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseStatusHistoryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseStatusHistoryJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_098__hmr_098_integrity_integrity_case.sql`
  - `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityCaseSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrityCaseSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate a supplied optional `primaryDefectId` as an existing same-module `PipelineDefect`, preserving optionality and applying topology compatibility only where explicitly defined by Integrity policy.**
  2. **Validate `caseTypeId` through the intended Integrity case-type catalog family once that authoritative family definition is established; generic catalog-row existence alone is insufficient.**
  3. **Preserve Topology/Incident/HSE/Organization/Workflow/Identity ownership boundaries:** use owner-controlled public reference contracts where live validation is required and never introduce cross-module database FKs.

#### HMR-099 — workflow.WorkflowStateHistory

- Source review: `HMSR-116`
- Exact commit: `fix(workflow): remediate semantic review WorkflowStateHistory`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowStep, workflow.WorkflowTask
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStateHistoryJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowStateHistorySemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on blank/null `toStatus` and `actorDisplayNameSnapshot` before persistence.**
  2. **Make WorkflowStateHistory persistence truly append-only so an existing history row cannot be overwritten or mutated through the generic save contract.**
  3. **For any future history producer outside the authoritative transition service, validate supplied optional task/step/action/reason references through Workflow-owned contracts and preserve instance/definition coherence without making optional references universally mandatory.**

#### HMR-100 — alarm.Alarm

- Source review: `HMSR-117`
- Exact commit: `fix(alarm): remediate semantic review Alarm`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: incident.Incident, organization.OrganizationUnit, planning.PlanTarget, telemetry.TelemetryReading, workflow.WorkflowInstance
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_100__hmr_100_alarm_alarm.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/AlarmApi.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmRestApi.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmQueryController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmGeneratedRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/AcknowledgeAlarmRequest.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/CloseAlarmRequest.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/RaiseAlarmRequest.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/response/AlarmResponse.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/AcknowledgeAlarmCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/CloseAlarmCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/CreateAlarmSuppressionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/EvaluateAlarmSuppressionExpiryCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/RaiseAlarmCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/ReleaseAlarmSuppressionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionDto.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionPageDto.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/mapper/AlarmApplicationMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AcknowledgeAlarmUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmQueryUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/CloseAlarmUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/RaiseAlarmUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/query/AlarmSuppressionQuery.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmDomainException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmLifecycleViolationException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/InvalidAlarmValueException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmBoundaryPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmCommentVisibility.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEvidenceType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmId.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmLifecycleEventType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionScopeType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/AlarmInfrastructure.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/configuration/AlarmModuleConfiguration.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/AlarmExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/NoopAlarmExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmPersistence.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCommentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEscalationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEvidenceLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmLifecycleEventJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmRuleBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogEntryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCommentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEscalationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEvidenceLinkJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmLifecycleEventJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmRuleBindingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/query/JpaAlarmQueryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJob.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`
  - `src/main/resources/db/migration/V20261004_100__hmr_100_alarm_alarm.sql`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmControllerActorAttributionTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate Alarm catalog-family membership:** `alarmTypeId` → `ALARM_TYPE`, `severityId` → `ALARM_SEVERITY`, supplied `priorityId` → `ALARM_PRIORITY`.
  2. **Fail closed on missing required title/message data before persistence; the live schema currently requires `titleFr`.**
  3. **Record exactly one append-only lifecycle event for Alarm creation/RAISED state.**
  4. **Make acknowledgement a transactional Alarm lifecycle operation:** load/fail closed on Alarm, reject closed alarms, persist acknowledgement, update Alarm acknowledgement/state fields, and emit exactly one lifecycle event.
  5. **Make closure a transactional Alarm lifecycle operation:** load/fail closed on Alarm, enforce clear-before-close unless cancelled, persist closure, update Alarm closed state/timestamps, and emit exactly one lifecycle event.

#### HMR-101 — audit.AuditAccessRecord

- Source review: `HMSR-118`
- Exact commit: `fix(audit): remediate semantic review AuditAccessRecord`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: audit.AuditEvent, audit.AuditExportRequest
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditAccessRecordResponse.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditAccessRecordSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditAccessRecordSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Make AuditAccessRecord persistence genuinely append-only so an existing record ID cannot be overwritten through generic save semantics.**
  2. **Fail closed on a supplied unknown `auditEventId` while preserving its documented optionality.**
  3. **Fail closed on a supplied unknown `exportRequestId` while preserving its documented optionality.**

#### HMR-102 — audit.AuditBeforeAfterValue

- Source review: `HMSR-119`
- Exact commit: `fix(audit): remediate semantic review AuditBeforeAfterValue`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: audit.AuditEvent
- HMSR correction count: 4
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditBeforeAfterValueSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on blank/null `fieldPath` before persistence.**
  2. **Enforce Audit sensitive-data policy:** masked/sensitive before/after evidence must not retain raw text; use hashes or masked/reference-only representations as appropriate.
  3. **Validate supplied `maskReasonId` against the Audit `MASK_REASON` catalog family while preserving optionality.**
  4. **Make AuditBeforeAfterValue persistence genuinely append-only so an existing evidence row cannot be overwritten through generic save semantics.**

#### HMR-103 — monitoring.PlanActualDeviation

- Source review: `HMSR-120`
- Exact commit: `fix(monitoring): remediate semantic review PlanActualDeviation`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: planning.PlanTarget, telemetry.TelemetryPoint, telemetry.TrustedTelemetryReading
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_103__hmr_103_monitoring_plan_actual_deviation.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): planning:src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java; planning:src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java; telemetry:src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java
- Exact write allowlist:
  - `docs/data definition/Monitoring.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/PlanActualDeviationJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_103__hmr_103_monitoring_plan_actual_deviation.sql`
  - `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanActualDeviationSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on the mandatory `planTargetId` through a Planning-owned public reference/lookup contract before recording a deviation; do not introduce a cross-module FK.**
  2. **Validate supplied optional `trustedTelemetryReadingId` / `telemetryPointId` through Telemetry-owned contracts when the comparison relies on them as live evidence, preserving optionality and avoiding cross-module FKs.**
  3. **Validate a supplied optional same-module `evaluationId` and preserve Monitoring evaluation/deviation coherence.**

#### HMR-104 — alarm.AlarmAcknowledgement

- Source review: `HMSR-121`
- Exact commit: `fix(alarm): remediate semantic review AlarmAcknowledgement`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: alarm.Alarm, organization.OrganizationUnit
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmAcknowledgementSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Load/fail closed on the owning `Alarm` and reject lifecycle-ineligible acknowledgement, including acknowledgement after closure.**
  2. **Persist acknowledgement evidence and update the owning Alarm's acknowledgement/state snapshot fields (`currentState`, `acknowledgedAt`, `acknowledgedByActorId`, `lastUpdatedAt`) atomically.**
  3. **Record exactly one corresponding append-only `AlarmLifecycleEvent` for the acknowledgement transition in the same transactional lifecycle operation.**

#### HMR-105 — alarm.AlarmClosure

- Source review: `HMSR-122`
- Exact commit: `fix(alarm): remediate semantic review AlarmClosure`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: alarm.Alarm, workflow.WorkflowInstance
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_105__hmr_105_alarm_alarm_closure.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_105__hmr_105_alarm_alarm_closure.sql`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmClosureSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Make `closeAlarm(...)` load/fail closed on the owning Alarm, enforce clear-before-close unless cancelled, update the Alarm lifecycle fields, persist `AlarmClosure`, and emit exactly one closure lifecycle event transactionally.**
  2. **Enforce the DDD's one-closure-record-per-Alarm invariant.**
  3. **Preserve Workflow and Identity ownership boundaries:** validate current references through owner-controlled contracts when required and do not add cross-module database FKs.

#### HMR-106 — alarm.AlarmShelving

- Source review: `HMSR-123`
- Exact commit: `fix(alarm): remediate semantic review AlarmShelving`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: alarm.Alarm
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_106__hmr_106_alarm_alarm_shelving.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_106__hmr_106_alarm_alarm_shelving.sql`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmShelvingSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce Alarm lifecycle eligibility before shelving, including the DDD prohibition on shelving a closed alarm.**
  2. **Validate `shelvingReasonId` against the Alarm-owned `SHELVING_REASON` catalog family, not merely generic catalog-row existence.**
  3. **Enforce `shelvedUntil > shelvedAt` as an intrinsic model/persistence invariant and enforce only one ACTIVE shelving per Alarm with concurrency-safe protection.**
  4. **Make shelving and unshelving transactional Alarm lifecycle operations that synchronize the owning Alarm state/timestamps and emit exactly one corresponding append-only lifecycle event.**
  5. **Implement deterministic automatic expiry/unshelving at `shelvedUntil`, preserving shelving status/time evidence and Alarm lifecycle consistency.**

### 12.3 SCC execution constraints

- SCC-01 OrganizationUnit: preserve hierarchy self-reference; do not break it into a false external dependency.
- SCC-02 WorkflowStep / WorkflowStepAssignmentRule: implement nullable/default-rule integrity in an order that permits the mutual relationship and fresh Flyway replay.
- SCC-03 MaintainableAsset: preserve parent-asset self-reference and direct-self prohibition.
- SCC-04 OperationalPlan / PlanRevision: preserve plan/revision mutual references and enforce same-plan correlation when pointers are populated.
- SCC-05 Document / DocumentVersion: preserve document/version ownership and current/supersession lineage without rewriting history.

### 12.4 Already-fixed evidence rule

HMR-002 found no production commit after the HMSR reviews: live `main` advanced only through HMS-006/HMR documentation commits. Therefore no REVISE obligation is marked Skipped as already implemented.

A later HMR task may become Skipped only if stronger live evidence appears before execution and the roadmap is updated with the exact implementing commit/file/test evidence.

### 12.5 Batch execution protocol

Model semantic remediation may use a **batch execution envelope** to reduce repetitive
`next -> CI -> next -> CI` cycles without collapsing semantic traceability.

A batch contains **2 to 4 HMR codes** and is valid only when all of the following are true:

- every HMR remains an independent roadmap record with its own HMSR source and obligations;
- every HMR keeps its exact write allowlist, migration authorization, focused test and exact commit message;
- every HMR is committed separately; batching never means squashing;
- the HMRs are dependency-safe and normally consecutive in the registered execution order;
- one green exact-head CI/status check gates the batch;
- the commits are chained in HMR order and the branch is advanced once to the final batch head when supported;
- one full post-batch CI/status observation verifies the final head;
- any prerequisite, SCC issue, owner-contract gap, migration-order conflict or larger-than-recorded semantic redesign splits the affected HMR out before mutation;
- catalog-model redesigns, cross-module lifecycle orchestration, unresolved owner contracts and other high-risk corrections remain solo unless this roadmap explicitly says otherwise.

A failed batch CI does not invalidate the individual HMR commit boundaries. Diagnose the failure
against the commit chain, repair only the responsible HMR(s), and do not start another batch until
the repaired final head is green.

#### Registered near-term execution envelopes

The following are execution registrations only; they do not change any HMR's semantic content:

| Envelope | HMR codes | Mode | Rationale |
|---|---|---|---|
| HMRB-001 | HMR-021 | Solo | **Completed.** Shift requiredness aligned independently before the PipelineSystem classification redesign. |
| HMRB-002 | HMR-022 | Solo | **Completed.** Dedicated PipelineSystem classification catalog/reference implemented and silent `TRANSPORT` default removed. |
| HMRB-003 | HMR-023 | Solo | **Completed.** AnalyticsInsight catalog, scope requiredness, optional severity-family integrity, and populated direct-source lineage are protected. |
| HMRB-004 | HMR-024 | Solo | **Completed.** Terminal diagnostics, successful-run source lineage, and immutable projection-definition computation lineage are enforced. |
| HMRB-005 | HMR-025 | Solo | **Completed.** Required analytical scope and the authoritative bounded readiness-status representation are aligned. |
| HMRB-006 | HMR-026, HMR-027 | Batch | **Completed.** FeatureFlag ownership requiredness and CustodyDiscrepancy optional quantity-unit integrity are remediated in separate commits. |
| HMRB-007 | HMR-038, HMR-039 | Batch | **Completed.** MetricValue scopeType requiredness and ConfigurationValue environment/version integrity completed in separate commits. |
| HMRB-008 | HMR-040 | Solo | **Completed.** MonitoringRule populated telemetryPointId now resolves through a deliberate Telemetry-owned Monitoring contract with exact guardrail registration. |
| HMRB-009 | HMR-041 | Solo | **Completed.** ACTIVE PartyRoleAssignment uniqueness is protected by application pre-check plus a concurrency-safe partial unique index. |
| HMRB-010 | HMR-042 | Solo | **Completed.** Pipeline fixed taxonomy is replaced by the Topology-owned PipelineType catalog reference architecture with migrated legacy values. |
| HMRB-011 | HMR-043, HMR-044 | Coordinated Batch | **Completed.** SCC-02 WorkflowStep ordering/reference integrity and WorkflowStepAssignmentRule family/candidate-source integrity completed in separate commits. |
| HMRB-012 | HMR-045 | Solo | **Completed.** MaintainableAsset external ownership and SCC-03/same-module reference integrity are enforced through deliberate owner contracts and Assets-owned FKs. |
| HMRB-013 | HMR-046 | Solo | **Completed.** SimulationRun queue eligibility, catalog-family semantics and completed-run immutability are enforced at application/repository boundaries. |
| HMRB-014 | HMR-047 | Solo | **Planned.** Concurrency-safe code uniqueness plus Organization owner validation. |
| HMRB-015 | HMR-048 | Solo | **Planned.** Reporting access, approval/workflow and Organization-owner lifecycle orchestration. |
| HMRB-016 | HMR-049 | Solo | **Planned.** Risk typed scope, unresolved review-frequency semantics and audit/outbox behavior. |
| HMRB-017 | HMR-050 | Solo | **Completed.** Active `INTEGRITY_PROGRAM_TYPE` family enforcement and Organization-owned unit validation are implemented without an out-of-order migration; the pre-existing HRA-111 same-module FK is retained. |
| HMRB-018 | HMR-051 | Solo | **Completed in user-authorized Batch 1.** Topology and optional Organization references validated on every case save; snapshot preserved; no migration because primary-candidate FK already exists; owner contract and architecture export added.
| HMRB-019 | HMR-052 | Solo | **Planned.** Notification recipient/template-version/required-variable readiness invariants. |
| HMRB-020 | HMR-053 | Solo | **Planned.** Telemetry trust gate, provenance consistency and ACTIVE-point eligibility. |
| HMRB-021 | HMR-054 | Solo | **Blocked until prerequisite.** Equipment catalog reconciliation plus unresolved Party owner lookup. |
| HMRB-022 | HMR-055 | Solo | **Planned.** Workflow start eligibility, target ownership, current-step coherence and non-terminal uniqueness. |
| HMRB-023 | HMR-056 | Solo | **Planned.** Integration message same-module reference/coherence and unresolved controlled-value families. |
| HMRB-024 | HMR-057 | Solo | **Planned.** Reporting run FK correction, definition/template lineage, queue eligibility and terminal invariants. |
| HMRB-025 | HMR-058 | Solo | **Planned.** Risk scope set, scoring policy, approval evidence/workflow and immutability. |
| HMRB-026 | HMR-059, HMR-060 | Batch | **Planned.** Narrow same-module reference protection plus append-only/retry-state integrity; no owner contract prerequisite or SCC. |
| HMRB-027 | HMR-061 | Solo | **Planned.** Workflow transition composition, uniqueness and executable-condition governance. |
| HMRB-028 | HMR-062 | Solo | **Planned.** Incident catalog, cross-context identity, Topology scope and lifecycle invariants. |
| HMRB-029 | HMR-063 | Solo | **Planned.** Identity username/email concurrency-safe uniqueness plus Organization employee validation. |
| HMRB-030 | HMR-064, HMR-065 | Coordinated Batch | **Planned, conditional preflight.** SCC-04 PlanRevision/OperationalPlan pair; chain in SCC-safe migration/application order and split if owner-contract or migration conflict appears. |
| HMRB-031 | HMR-066 | Solo | **Planned.** Workflow task assignment/claim/completion lifecycle plus owner-controlled eligibility. |
| HMRB-032 | HMR-067, HMR-068 | Coordinated Batch | **Planned, conditional preflight.** SCC-05 Document/DocumentVersion pair; preserve current-version/supersession ordering and split if Identity/Workflow owner-contract gaps appear. |
| HMRB-033 | HMR-069 | Solo | **Planned.** Assets work-order same-module and multiple cross-context provenance references. |
| HMRB-034 | HMR-070 | Solo | **Planned.** Custody ticket evidence plus Identity/Workflow/Audit ownership. |
| HMRB-035 | HMR-071 | Solo | **Planned.** Integration dead-letter evidence, same-module references and manual-resolution identity. |
| HMRB-036 | HMR-072 | Solo | **Planned.** Integrity assessment same-module plus Identity/Workflow owner validation. |
| HMRB-037 | HMR-073, HMR-074, HMR-075, HMR-076 | Batch | **Planned.** Four consecutive narrow Organization corrections: one disabled-unit lifecycle guard and three mandatory-nullability alignments. |
| HMRB-038 | HMR-077 | Solo | **Planned.** Risk external evidence tuple plus owner-controlled polymorphic evidence validation. |
| HMRB-039 | HMR-078 | Solo | **Planned.** Simulation candidate-change catalog plus Topology target validation with no concrete owner contract yet registered. |
| HMRB-040 | HMR-079 | Solo | **Planned.** Simulation recommendation catalog/reference integrity plus Audit publication evidence. |
| HMRB-041 | HMR-080 | Solo | **Blocked until prerequisite.** Nomination has unresolved Party lookup and broader product/unit ownership reconciliation. |
| HMRB-042 | HMR-081 | Solo | **Planned.** Workflow decision-authority, task ownership, actor evidence and race-safe action sequencing. |
| HMRB-043 | HMR-082 | Solo | **Planned.** HSE case closure lifecycle and multi-owner references. |
| HMRB-044 | HMR-083 | Solo | **Planned.** Audit export sanitization, purpose family, Workflow/Documents ownership and self-auditing behavior. |
| HMRB-045 | HMR-084 | Solo | **Planned.** Documents polymorphic target validation with no concrete upstream owner registered. |
| HMRB-046 | HMR-085 | Solo | **Planned.** High-risk authorization evaluation order, explainability and conditional decision persistence. |
| HMRB-047 | HMR-086, HMR-087, HMR-088, HMR-089 | Batch | **Planned.** Four consecutive same-module Identity corrections with no cross-module owner prerequisite; preserve one commit/test contract per HMR. |
| HMRB-048 | HMR-090 | Solo | **Planned.** Incident closure transaction and evidence/verification preconditions. |
| HMRB-049 | HMR-091 | Solo | **Planned.** Incident relationship FK correction, family semantics and duplicate/inverse policy. |
| HMRB-050 | HMR-092 | Solo | **Planned.** Incident response-action lifecycle eligibility and catalog family. |
| HMRB-051 | HMR-093 | Solo | **Planned.** Reporting artifact FK correction plus Documents-owned evidence references. |
| HMRB-052 | HMR-094 | Solo | **Planned.** Planning target value-shape, revision compatibility, Topology and Telemetry validation. |
| HMRB-053 | HMR-095 | Solo | **Planned.** AuditEvent append-only ledger, catalog families and sensitive-payload redaction/limits. |
| HMRB-054 | HMR-096 | Solo | **Planned.** HSE closure transactional lifecycle with Workflow/Identity ownership. |
| HMRB-055 | HMR-097 | Solo | **Planned.** HSE CAPA lifecycle plus Assets/Organization/Workflow/Identity ownership. |
| HMRB-056 | HMR-098 | Solo | **Planned.** Integrity case family definition and multi-module owner boundaries. |
| HMRB-057 | HMR-099 | Solo | **Planned.** Workflow history append-only evidence and reference coherence. |
| HMRB-058 | HMR-100 | Solo | **Planned.** Alarm aggregate lifecycle establishment; creation/acknowledgement/closure event atomicity. |
| HMRB-059 | HMR-101, HMR-102 | Batch | **Planned.** Consecutive Audit evidence records; append-only semantics plus bounded same-module/catalog validation. |
| HMRB-060 | HMR-103 | Solo | **Planned.** Monitoring deviation requires Planning/Telemetry owner validation and same-module evaluation coherence. |
| HMRB-061 | HMR-104 | Solo | **Planned.** Alarm acknowledgement transactional lifecycle; high-risk lifecycle orchestration remains isolated. |
| HMRB-062 | HMR-105 | Solo | **Planned.** Alarm closure transactional lifecycle, one-closure invariant and Workflow ownership. |
| HMRB-063 | HMR-106 | Solo | **Planned.** Alarm shelving lifecycle, concurrency-safe one-ACTIVE invariant and deterministic automatic expiry. |
| HMRB-064 | HMR-107 | Solo | **Blocked until R2.** Full exact-SHA build/test/Flyway/OpenAPI/architecture verification after every HMR-003…HMR-106 outcome is resolved. |
| HMRB-065 | HMR-108 | Solo | **Blocked until HMR-107.** Documentation-only programme closure and HMS-007 gate decision. |

This table is the full remaining execution plan, not a blanket authorization to mutate future HMRs without preflight. Before each envelope starts, re-read the live HMR records and exact head. A planned multi-HMR envelope is automatically split before mutation if live evidence reveals an unregistered prerequisite, owner-contract gap, SCC/migration conflict, or materially larger semantic redesign.

Carry-over blocker: HMR-009 remains unresolved in the production register and must be resolved (or formally deferred/skipped under Gate R2 rules) before HMR-107 can run. HMR-054 and HMR-080 likewise remain blocked at their planned positions until their owner-contract prerequisites are registered and completed.

Additional batches may be registered or an existing planned envelope may be split immediately before execution when newer live repository evidence requires it. Prefer the plan above unless live evidence invalidates its compatibility assumptions.

### 12.6 Current next execution

HMRB-018 — HMR-051

Mode: Solo

HMR-050 is completed under HPR-P2-008 exact-current revalidation. Execute HMR-051 only after the HMR-050 head is green and exact next-task preflight passes; stop before HMRB-019.
