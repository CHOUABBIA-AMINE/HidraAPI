# HidraAPI Target Model Semantic Review Roadmap

**Status:** Active — HMS-005 completed; HMSR-001 approved, HMSR-002 reviewed as REVISE, HMSR-003 approved, HMSR-004 reviewed as REVISE, HMSR-005 reviewed as REVISE, HMSR-006 reviewed as REVISE, HMSR-007 is the next interactive model review.

**Repository:** `CHOUABBIA-AMINE/HidraAPI`  
**Roadmap:** `docs/roadmap/model-semantic-review.md`  
**Roadmap code:** `HMS`  
**Created:** 2026-10-02  
**Live baseline at creation:** `5e289aa31a5d622e50176d69fd858c3ea0b4aa34`  
**Catalogue source:** `docs/roadmap/model-field-catalogue.md`  
**Pinned catalogue model source:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Catalogue scope:** 24 modules, 123 direct domain models, 1,867 declared fields/components  
**Execution mode:** exactly one roadmap task/commit at a time; never continue automatically.

## 1. Purpose

Establish an authoritative semantic baseline for the 123 catalogued HidraAPI domain models before legacy-source classification, source-to-target mapping, or provisioning decisions continue.

The review sequence is dependency-driven:

```text
models with no confirmed model dependencies
    ↓
models whose confirmed dependencies have already been reviewed
    ↓
progressively deeper dependent models
    ↓
terminal/consumer models that are not prerequisites of other models
```

Within the same dependency level, models with more confirmed downstream dependents are reviewed first. Cyclic dependencies are never forced into an artificial linear order; strongly connected components are explicitly grouped and resolved.

## 2. Why this roadmap exists

HMC-001 through HMC-028 produced and validated a model/field/type catalogue, but that catalogue intentionally records Java declarations only. It does not establish:

- semantic meaning of `...Id` fields;
- persistence foreign keys;
- cross-module ownership;
- mandatory/optional relationship meaning;
- snapshot/reference-only semantics;
- lifecycle rules;
- multilingual modeling correctness;
- whether a model should be retained, revised, deferred, or removed.

HDP-004 and HDP-005 therefore remain paused until this target-model semantic review produces an approved baseline.

## 3. Authoritative evidence

Dependency and semantic decisions must use current/live repository evidence where available:

1. verified HMC catalogue and its pinned model source;
2. current domain model source;
3. JPA mappings/entities;
4. current Flyway migrations and foreign keys;
5. application ports/contracts;
6. enums/value types;
7. module roadmaps and architectural boundaries.

A Java field type alone is insufficient. For example, a `String organizationUnitId` may represent a real dependency even though the declared Java type is only `String`.

Do not infer a dependency merely from similar names. Every edge must have evidence and a classification.

## 4. Dependency edge classifications

Every confirmed model-to-model edge must be classified as one of:

| Classification | Meaning |
|---|---|
| Persistence dependency | Backed by target persistence FK/constraint or equivalent owned persistence relation. |
| Domain reference | Explicit domain relationship not necessarily represented by a database FK. |
| Cross-module reference | Stable reference into another bounded context. |
| Optional reference | Semantically optional dependency. |
| Snapshot/reference-only | Snapshot/code/name field that must not be treated as an owning dependency. |
| Value/catalog dependency | Reference to controlled value/catalog semantics rather than aggregate ownership. |
| Unresolved | Evidence is insufficient or contradictory; interactive decision required. |

Snapshot fields such as `...NameSnapshot` or `...CodeSnapshot` are not dependencies unless repository evidence establishes otherwise.

## 5. Graph and ordering rules

For each of the 123 models record:

- module;
- model;
- confirmed upstream dependencies;
- dependency evidence;
- dependency classification;
- confirmed downstream dependents;
- inbound dependent count;
- outbound dependency count;
- dependency depth;
- strongly connected component, if any;
- review level;
- review readiness;
- unresolved edges.

Ordering algorithm:

1. validate all dependency edges first;
2. collapse strongly connected components for ordering;
3. topologically order the resulting graph;
4. Level 0 contains models with no confirmed upstream model dependency;
5. Level N contains models whose confirmed upstream dependencies are in prior levels;
6. within a level, sort by downstream dependent count descending;
7. use module then model name only as deterministic tie-breakers;
8. terminal/consumer models naturally appear toward the end because they have few or no downstream dependents.

## 6. Interactive model-review protocol

HMS-005 will generate the authoritative review register and assign one task code per model:

```text
HMSR-001
HMSR-002
...
HMSR-123
```

The codes and exact commit messages are generated only after dependency ordering is validated. Do not preassign model codes before HMS-005.

Each interactive model session must present:

- model identity and bounded context;
- dependency level and why it appears there;
- confirmed upstream dependencies;
- confirmed downstream dependents;
- every declared field and type;
- intended business meaning of each field;
- `...Id`, code, snapshot and enum/reference semantics;
- mandatory vs optional semantics;
- lifecycle/status semantics;
- multilingual handling;
- bounded-context ownership;
- persistence/FK consistency;
- relevant pipeline/SONATRACH operational interpretation;
- unresolved questions requiring user decision.

Allowed model decisions:

```text
APPROVED
REVISE
DEFER
REMOVE
```

A review task records the decision and evidence. It must not silently change production code. Any approved production-model correction requires its own appropriate module roadmap/task before the semantic baseline can treat that correction as implemented.

## 7. Task roadmap

| Code | Exact commit message | Deliverable / acceptance | Status |
|---|---|---|---|
| HMS-001 | `docs(model-review): establish target model semantic review roadmap` | Create this roadmap and amend HDP so HDP-004 is explicitly paused behind the semantic-review prerequisite. No dependency analysis or production code changes. | **Completed** |
| HMS-002 | `docs(model-review): inventory target model dependency evidence` | Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains evidence/provenance; no review order yet. | **Completed** — 123/123 subjects reconciled; 627 candidate relationship fields inventoried; JPA/ports/Flyway/custom-type evidence registered; no classification/order performed. |
| HMS-003 | `docs(model-review): classify target model dependencies` | Resolve candidate edges into the dependency classifications in section 4; separate true dependencies from snapshots/codes and record unresolved edges. | **Completed** — 627/627 candidates classified; 165 unambiguous subject-model edges admitted provisionally; 134 unresolved candidates retained for HMS-004. |
| HMS-004 | `docs(model-review): validate dependency graph and cycles` | Reconcile graph against persistence/contracts, identify strongly connected components, missing targets, contradictory edges and cross-module boundary concerns. | **Completed** — 123 nodes, 181 validated subject edges, 5 cyclic SCCs, 0 invalid subject targets; external read-persistence prerequisites retained outside the 123-node graph. |
| HMS-005 | `docs(model-review): generate dependency ordered model review register` | Compute deterministic review levels/order and generate the 123-model review register with `HMSR-001…HMSR-123` codes and exact per-model commit messages. | **Completed** — 123/123 models ordered across Levels 0–8; 5 SCCs collapsed for ordering; exact HMSR codes/commit messages generated. |
| HMS-006 | `docs(model-review): reconcile interactive model decisions` | After all HMSR tasks are resolved, reconcile APPROVED/REVISE/DEFER/REMOVE decisions, outstanding corrections, unresolved cycles and dependency impacts. | Planned |
| HMS-007 | `docs(model-review): finalize approved target model semantic baseline` | Publish the final target-model semantic baseline only when every model has a resolved disposition and any required model corrections are implemented or explicitly deferred. | Planned |
| HMS-008 | `docs(data-provisioning): align provisioning roadmap to semantic model baseline` | Amend HDP target assumptions, record the approved HMS baseline, and determine whether HDP-004 may resume. Does not itself classify source data or start HDP-005. | Planned |

## 8. HMSR review tasks

HMS-005 generated the authoritative interactive review register from the validated 123-node graph.

Ordering rules applied:

1. collapse the five validated cyclic SCCs;
2. compute prerequisite-first dependency level on the collapsed graph;
3. models with no confirmed subject-model prerequisites are Level 0;
4. a later level is assigned only after all confirmed subject-model prerequisites are in earlier levels or the same collapsed SCC;
5. within each level, sort by transitive downstream dependent count descending, then direct dependent count descending, then module/model name;
6. unresolved/non-subject references do not become graph edges, but their count is carried into the interactive review as an external/unresolved-reference warning;
7. SCC members share the same dependency level and must be semantically reconciled together even though each receives its own HMSR decision task.

### 8.1 Review-level summary

| Level | Models |
|---:|---:|
| 0 | 34 |
| 1 | 16 |
| 2 | 13 |
| 3 | 10 |
| 4 | 20 |
| 5 | 17 |
| 6 | 6 |
| 7 | 4 |
| 8 | 3 |
| **Total** | **123** |

### 8.2 Authoritative 123-model interactive review register

| Review Code | Level | Module | Model | SCC | Confirmed upstream dependencies | Direct dependents | Transitive dependents | Unresolved/non-subject refs | Decision | Status | Exact commit message |
|---|---:|---|---|---|---|---:|---:|---:|---|---|---|
| HMSR-001 | 0 | organization | OrganizationUnitType | — | — | 1 | 52 | 0 | APPROVED | Completed | `docs(model-review): review organization OrganizationUnitType` |
| HMSR-002 | 0 | workflow | WorkflowDefinition | — | — | 4 | 36 | 1 | REVISE | Completed | `docs(model-review): review workflow WorkflowDefinition` |
| HMSR-003 | 0 | organization | AdministrativeState | — | — | 1 | 17 | 0 | APPROVED | Completed | `docs(model-review): review organization AdministrativeState` |
| HMSR-004 | 0 | party | Party | — | — | 5 | 14 | 0 | REVISE | Completed | `docs(model-review): review party Party` |
| HMSR-005 | 0 | telemetry | TelemetryPoint | — | — | 5 | 9 | 3 | REVISE | Completed | `docs(model-review): review telemetry TelemetryPoint` |
| HMSR-006 | 0 | planning | PlanningPeriod | — | — | 1 | 9 | 0 | REVISE | Completed | `docs(model-review): review planning PlanningPeriod` |
| HMSR-007 | 0 | identity | Role | — | — | 6 | 6 | 0 | — | Planned | `docs(model-review): review identity Role` |
| HMSR-008 | 0 | documents | DocumentStorageObject | — | — | 2 | 6 | 1 | — | Planned | `docs(model-review): review documents DocumentStorageObject` |
| HMSR-009 | 0 | simulation | SimulationModel | — | — | 1 | 5 | 1 | — | Planned | `docs(model-review): review simulation SimulationModel` |
| HMSR-010 | 0 | identity | IdentityProvider | — | — | 4 | 4 | 0 | — | Planned | `docs(model-review): review identity IdentityProvider` |
| HMSR-011 | 0 | identity | Permission | — | — | 3 | 3 | 0 | — | Planned | `docs(model-review): review identity Permission` |
| HMSR-012 | 0 | notification | NotificationTemplate | — | — | 2 | 3 | 2 | — | Planned | `docs(model-review): review notification NotificationTemplate` |
| HMSR-013 | 0 | reporting | ReportDefinition | — | — | 2 | 3 | 1 | — | Planned | `docs(model-review): review reporting ReportDefinition` |
| HMSR-014 | 0 | integration | IntegrationJobRun | — | — | 2 | 2 | 1 | — | Planned | `docs(model-review): review integration IntegrationJobRun` |
| HMSR-015 | 0 | leakdetection | LeakCandidate | — | — | 2 | 2 | 2 | — | Planned | `docs(model-review): review leakdetection LeakCandidate` |
| HMSR-016 | 0 | organization | OperationalScope | — | — | 1 | 2 | 0 | — | Planned | `docs(model-review): review organization OperationalScope` |
| HMSR-017 | 0 | analytics | AnalyticsDataset | — | — | 1 | 1 | 0 | — | Planned | `docs(model-review): review analytics AnalyticsDataset` |
| HMSR-018 | 0 | analytics | MetricEvaluationRun | — | — | 1 | 1 | 1 | — | Planned | `docs(model-review): review analytics MetricEvaluationRun` |
| HMSR-019 | 0 | configuration | ConfigurationDefinition | — | — | 1 | 1 | 1 | — | Planned | `docs(model-review): review configuration ConfigurationDefinition` |
| HMSR-020 | 0 | custody | CustodyMeasurementPeriod | — | — | 1 | 1 | 2 | — | Planned | `docs(model-review): review custody CustodyMeasurementPeriod` |
| HMSR-021 | 0 | integrity | PipelineDefect | — | — | 1 | 1 | 1 | — | Planned | `docs(model-review): review integrity PipelineDefect` |
| HMSR-022 | 0 | organization | Position | — | — | 1 | 1 | 0 | — | Planned | `docs(model-review): review organization Position` |
| HMSR-023 | 0 | organization | Shift | — | — | 1 | 1 | 0 | — | Planned | `docs(model-review): review organization Shift` |
| HMSR-024 | 0 | topology | PipelineSystem | — | — | 1 | 1 | 0 | — | Planned | `docs(model-review): review topology PipelineSystem` |
| HMSR-025 | 0 | analytics | AnalyticsInsight | — | — | 0 | 0 | 3 | — | Planned | `docs(model-review): review analytics AnalyticsInsight` |
| HMSR-026 | 0 | analytics | AnalyticsProjectionRun | — | — | 0 | 0 | 1 | — | Planned | `docs(model-review): review analytics AnalyticsProjectionRun` |
| HMSR-027 | 0 | analytics | DigitalTwinReadinessAssessment | — | — | 0 | 0 | 0 | — | Planned | `docs(model-review): review analytics DigitalTwinReadinessAssessment` |
| HMSR-028 | 0 | configuration | FeatureFlag | — | — | 0 | 0 | 0 | — | Planned | `docs(model-review): review configuration FeatureFlag` |
| HMSR-029 | 0 | custody | CustodyDiscrepancy | — | — | 0 | 0 | 1 | — | Planned | `docs(model-review): review custody CustodyDiscrepancy` |
| HMSR-030 | 0 | organization | OrganizationContactPoint | — | — | 0 | 0 | 0 | — | Planned | `docs(model-review): review organization OrganizationContactPoint` |
| HMSR-031 | 0 | organization | ReportingLine | — | — | 0 | 0 | 0 | — | Planned | `docs(model-review): review organization ReportingLine` |
| HMSR-032 | 0 | risk | RiskMatrixCell | — | — | 0 | 0 | 2 | — | Planned | `docs(model-review): review risk RiskMatrixCell` |
| HMSR-033 | 0 | telemetry | TelemetrySource | — | — | 0 | 0 | 0 | — | Planned | `docs(model-review): review telemetry TelemetrySource` |
| HMSR-034 | 0 | topology | TopologyConnection | — | — | 0 | 0 | 3 | — | Planned | `docs(model-review): review topology TopologyConnection` |
| HMSR-035 | 1 | organization | OrganizationUnit | SCC-01 | organization.OrganizationUnit, organization.OrganizationUnitType | 22 | 51 | 0 | — | Planned | `docs(model-review): review organization OrganizationUnit` |
| HMSR-036 | 1 | organization | AdministrativeDistrict | — | organization.AdministrativeState | 1 | 16 | 0 | — | Planned | `docs(model-review): review organization AdministrativeDistrict` |
| HMSR-037 | 1 | telemetry | TelemetryReading | — | telemetry.TelemetryPoint | 2 | 6 | 2 | — | Planned | `docs(model-review): review telemetry TelemetryReading` |
| HMSR-038 | 1 | simulation | SimulationScenario | — | simulation.SimulationModel | 1 | 4 | 3 | — | Planned | `docs(model-review): review simulation SimulationScenario` |
| HMSR-039 | 1 | notification | NotificationRequest | — | notification.NotificationTemplate | 1 | 2 | 4 | — | Planned | `docs(model-review): review notification NotificationRequest` |
| HMSR-040 | 1 | organization | ResponsibilityAssignment | — | organization.OperationalScope | 1 | 1 | 1 | — | Planned | `docs(model-review): review organization ResponsibilityAssignment` |
| HMSR-041 | 1 | topology | Facility | — | party.Party | 1 | 1 | 0 | — | Planned | `docs(model-review): review topology Facility` |
| HMSR-042 | 1 | analytics | AnalyticsDatasetVersion | — | analytics.AnalyticsDataset | 0 | 0 | 0 | — | Planned | `docs(model-review): review analytics AnalyticsDatasetVersion` |
| HMSR-043 | 1 | analytics | MetricValue | — | analytics.MetricEvaluationRun | 0 | 0 | 3 | — | Planned | `docs(model-review): review analytics MetricValue` |
| HMSR-044 | 1 | configuration | ConfigurationValue | — | configuration.ConfigurationDefinition | 0 | 0 | 1 | — | Planned | `docs(model-review): review configuration ConfigurationValue` |
| HMSR-045 | 1 | identity | ExternalRoleMapping | — | identity.IdentityProvider, identity.Role | 0 | 0 | 0 | — | Planned | `docs(model-review): review identity ExternalRoleMapping` |
| HMSR-046 | 1 | identity | GroupRoleGrant | — | identity.Role | 0 | 0 | 2 | — | Planned | `docs(model-review): review identity GroupRoleGrant` |
| HMSR-047 | 1 | identity | RolePermissionGrant | — | identity.Permission, identity.Role | 0 | 0 | 0 | — | Planned | `docs(model-review): review identity RolePermissionGrant` |
| HMSR-048 | 1 | monitoring | MonitoringRule | — | telemetry.TelemetryPoint | 0 | 0 | 0 | — | Planned | `docs(model-review): review monitoring MonitoringRule` |
| HMSR-049 | 1 | party | PartyRoleAssignment | — | identity.Role, party.Party | 0 | 0 | 0 | — | Planned | `docs(model-review): review party PartyRoleAssignment` |
| HMSR-050 | 1 | topology | Pipeline | — | topology.PipelineSystem | 0 | 0 | 0 | — | Planned | `docs(model-review): review topology Pipeline` |
| HMSR-051 | 2 | workflow | WorkflowStep | SCC-02 | workflow.WorkflowDefinition, workflow.WorkflowStepAssignmentRule | 5 | 35 | 0 | — | Planned | `docs(model-review): review workflow WorkflowStep` |
| HMSR-052 | 2 | workflow | WorkflowStepAssignmentRule | SCC-02 | organization.OrganizationUnit, workflow.WorkflowDefinition, workflow.WorkflowStep | 1 | 35 | 0 | — | Planned | `docs(model-review): review workflow WorkflowStepAssignmentRule` |
| HMSR-053 | 2 | organization | AdministrativeLocality | — | organization.AdministrativeDistrict | 2 | 15 | 0 | — | Planned | `docs(model-review): review organization AdministrativeLocality` |
| HMSR-054 | 2 | assets | MaintainableAsset | SCC-03 | assets.MaintainableAsset, organization.OrganizationUnit, party.Party | 3 | 3 | 4 | — | Planned | `docs(model-review): review assets MaintainableAsset` |
| HMSR-055 | 2 | simulation | SimulationRun | — | simulation.SimulationScenario | 2 | 3 | 2 | — | Planned | `docs(model-review): review simulation SimulationRun` |
| HMSR-056 | 2 | integration | ExternalSystem | — | organization.OrganizationUnit | 2 | 2 | 0 | — | Planned | `docs(model-review): review integration ExternalSystem` |
| HMSR-057 | 2 | reporting | ReportRequest | — | organization.OrganizationUnit, reporting.ReportDefinition | 1 | 2 | 1 | — | Planned | `docs(model-review): review reporting ReportRequest` |
| HMSR-058 | 2 | risk | RiskRegister | — | organization.OrganizationUnit | 1 | 2 | 0 | — | Planned | `docs(model-review): review risk RiskRegister` |
| HMSR-059 | 2 | integrity | IntegrityProgram | — | organization.OrganizationUnit | 1 | 1 | 0 | — | Planned | `docs(model-review): review integrity IntegrityProgram` |
| HMSR-060 | 2 | leakdetection | LeakDetectionCase | — | leakdetection.LeakCandidate, organization.OrganizationUnit | 1 | 1 | 0 | — | Planned | `docs(model-review): review leakdetection LeakDetectionCase` |
| HMSR-061 | 2 | notification | NotificationMessage | — | notification.NotificationRequest, notification.NotificationTemplate | 1 | 1 | 3 | — | Planned | `docs(model-review): review notification NotificationMessage` |
| HMSR-062 | 2 | telemetry | TrustedTelemetryReading | — | telemetry.TelemetryPoint, telemetry.TelemetryReading | 1 | 1 | 3 | — | Planned | `docs(model-review): review telemetry TrustedTelemetryReading` |
| HMSR-063 | 2 | topology | Equipment | — | party.Party, topology.Facility | 0 | 0 | 2 | — | Planned | `docs(model-review): review topology Equipment` |
| HMSR-064 | 3 | workflow | WorkflowInstance | — | workflow.WorkflowDefinition, workflow.WorkflowStep | 18 | 32 | 1 | — | Planned | `docs(model-review): review workflow WorkflowInstance` |
| HMSR-065 | 3 | organization | Employee | — | organization.AdministrativeLocality | 6 | 14 | 1 | — | Planned | `docs(model-review): review organization Employee` |
| HMSR-066 | 3 | simulation | SimulationOptimizationCandidate | — | simulation.SimulationRun | 2 | 2 | 0 | — | Planned | `docs(model-review): review simulation SimulationOptimizationCandidate` |
| HMSR-067 | 3 | integration | IntegrationExchangeMessage | — | integration.ExternalSystem, integration.IntegrationJobRun | 1 | 1 | 2 | — | Planned | `docs(model-review): review integration IntegrationExchangeMessage` |
| HMSR-068 | 3 | reporting | ReportRun | — | reporting.ReportDefinition, reporting.ReportRequest | 1 | 1 | 0 | — | Planned | `docs(model-review): review reporting ReportRun` |
| HMSR-069 | 3 | risk | RiskAssessment | — | risk.RiskRegister | 1 | 1 | 10 | — | Planned | `docs(model-review): review risk RiskAssessment` |
| HMSR-070 | 3 | assets | AssetConditionRecord | — | assets.MaintainableAsset | 0 | 0 | 0 | — | Planned | `docs(model-review): review assets AssetConditionRecord` |
| HMSR-071 | 3 | leakdetection | LeakEscalationReference | — | leakdetection.LeakCandidate, leakdetection.LeakDetectionCase | 0 | 0 | 0 | — | Planned | `docs(model-review): review leakdetection LeakEscalationReference` |
| HMSR-072 | 3 | notification | NotificationDeliveryAttempt | — | notification.NotificationMessage | 0 | 0 | 2 | — | Planned | `docs(model-review): review notification NotificationDeliveryAttempt` |
| HMSR-073 | 3 | workflow | WorkflowTransition | — | workflow.WorkflowDefinition, workflow.WorkflowStep | 0 | 0 | 0 | — | Planned | `docs(model-review): review workflow WorkflowTransition` |
| HMSR-074 | 4 | incident | Incident | — | organization.OrganizationUnit, workflow.WorkflowInstance | 6 | 11 | 3 | — | Planned | `docs(model-review): review incident Incident` |
| HMSR-075 | 4 | identity | User | — | organization.Employee | 8 | 8 | 1 | — | Planned | `docs(model-review): review identity User` |
| HMSR-076 | 4 | planning | PlanRevision | SCC-04 | planning.OperationalPlan, planning.PlanRevision, workflow.WorkflowInstance | 4 | 8 | 2 | — | Planned | `docs(model-review): review planning PlanRevision` |
| HMSR-077 | 4 | planning | OperationalPlan | SCC-04 | organization.OrganizationUnit, planning.PlanRevision, planning.PlanningPeriod | 1 | 8 | 1 | — | Planned | `docs(model-review): review planning OperationalPlan` |
| HMSR-078 | 4 | workflow | WorkflowTask | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowStep | 4 | 6 | 1 | — | Planned | `docs(model-review): review workflow WorkflowTask` |
| HMSR-079 | 4 | documents | Document | SCC-05 | documents.DocumentVersion | 4 | 5 | 3 | — | Planned | `docs(model-review): review documents Document` |
| HMSR-080 | 4 | documents | DocumentVersion | SCC-05 | documents.Document, documents.DocumentStorageObject, documents.DocumentVersion, workflow.WorkflowInstance | 3 | 5 | 2 | — | Planned | `docs(model-review): review documents DocumentVersion` |
| HMSR-081 | 4 | assets | MaintenanceWorkOrder | — | assets.MaintainableAsset, organization.OrganizationUnit, workflow.WorkflowInstance | 1 | 1 | 3 | — | Planned | `docs(model-review): review assets MaintenanceWorkOrder` |
| HMSR-082 | 4 | custody | CustodyTransferTicket | — | custody.CustodyMeasurementPeriod, workflow.WorkflowInstance | 0 | 0 | 5 | — | Planned | `docs(model-review): review custody CustodyTransferTicket` |
| HMSR-083 | 4 | hse | PermitToWork | — | workflow.WorkflowInstance | 0 | 0 | 0 | — | Planned | `docs(model-review): review hse PermitToWork` |
| HMSR-084 | 4 | integration | IntegrationDeadLetterRecord | — | integration.ExternalSystem, integration.IntegrationExchangeMessage, integration.IntegrationJobRun | 0 | 0 | 2 | — | Planned | `docs(model-review): review integration IntegrationDeadLetterRecord` |
| HMSR-085 | 4 | integrity | IntegrityAssessment | — | integrity.IntegrityProgram, workflow.WorkflowInstance | 0 | 0 | 2 | — | Planned | `docs(model-review): review integrity IntegrityAssessment` |
| HMSR-086 | 4 | organization | EmployeeAddress | — | organization.AdministrativeLocality, organization.Employee | 0 | 0 | 0 | — | Planned | `docs(model-review): review organization EmployeeAddress` |
| HMSR-087 | 4 | organization | EmployeeAssignment | — | organization.Employee, organization.OrganizationUnit, organization.Position | 0 | 0 | 0 | — | Planned | `docs(model-review): review organization EmployeeAssignment` |
| HMSR-088 | 4 | organization | OrganizationDelegation | — | organization.Employee, organization.ResponsibilityAssignment | 0 | 0 | 0 | — | Planned | `docs(model-review): review organization OrganizationDelegation` |
| HMSR-089 | 4 | organization | OrganizationHierarchySnapshot | — | organization.Employee | 0 | 0 | 0 | — | Planned | `docs(model-review): review organization OrganizationHierarchySnapshot` |
| HMSR-090 | 4 | organization | ShiftAssignment | — | organization.Employee, organization.OrganizationUnit, organization.Shift | 0 | 0 | 0 | — | Planned | `docs(model-review): review organization ShiftAssignment` |
| HMSR-091 | 4 | risk | RiskEvidenceLink | — | risk.RiskAssessment | 0 | 0 | 1 | — | Planned | `docs(model-review): review risk RiskEvidenceLink` |
| HMSR-092 | 4 | simulation | SimulationCandidateChange | — | simulation.SimulationOptimizationCandidate | 0 | 0 | 0 | — | Planned | `docs(model-review): review simulation SimulationCandidateChange` |
| HMSR-093 | 4 | simulation | SimulationRecommendation | — | simulation.SimulationOptimizationCandidate, simulation.SimulationRun | 0 | 0 | 0 | — | Planned | `docs(model-review): review simulation SimulationRecommendation` |
| HMSR-094 | 5 | planning | Nomination | — | party.Party, planning.PlanRevision | 1 | 6 | 2 | — | Planned | `docs(model-review): review planning Nomination` |
| HMSR-095 | 5 | workflow | WorkflowAction | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowTask | 2 | 4 | 1 | — | Planned | `docs(model-review): review workflow WorkflowAction` |
| HMSR-096 | 5 | hse | HseCase | — | incident.Incident, organization.OrganizationUnit, workflow.WorkflowInstance | 3 | 3 | 3 | — | Planned | `docs(model-review): review hse HseCase` |
| HMSR-097 | 5 | audit | AuditExportRequest | — | documents.Document, workflow.WorkflowInstance | 1 | 1 | 2 | — | Planned | `docs(model-review): review audit AuditExportRequest` |
| HMSR-098 | 5 | documents | DocumentTargetLink | — | documents.Document, documents.DocumentVersion | 0 | 0 | 0 | — | Planned | `docs(model-review): review documents DocumentTargetLink` |
| HMSR-099 | 5 | identity | AuthenticationEvent | — | identity.IdentityProvider, identity.User | 0 | 0 | 1 | — | Planned | `docs(model-review): review identity AuthenticationEvent` |
| HMSR-100 | 5 | identity | AuthorizationDecision | — | identity.User | 0 | 0 | 2 | — | Planned | `docs(model-review): review identity AuthorizationDecision` |
| HMSR-101 | 5 | identity | AuthorizationDelegationGrant | — | identity.Permission, identity.Role, identity.User | 0 | 0 | 3 | — | Planned | `docs(model-review): review identity AuthorizationDelegationGrant` |
| HMSR-102 | 5 | identity | HidraPrincipal | — | identity.IdentityProvider, identity.User | 0 | 0 | 0 | — | Planned | `docs(model-review): review identity HidraPrincipal` |
| HMSR-103 | 5 | identity | LocalCredential | — | identity.User | 0 | 0 | 0 | — | Planned | `docs(model-review): review identity LocalCredential` |
| HMSR-104 | 5 | identity | LoginSession | — | identity.IdentityProvider, identity.User | 0 | 0 | 1 | — | Planned | `docs(model-review): review identity LoginSession` |
| HMSR-105 | 5 | identity | UserPermissionGrant | — | identity.Permission, identity.User | 0 | 0 | 1 | — | Planned | `docs(model-review): review identity UserPermissionGrant` |
| HMSR-106 | 5 | identity | UserRoleGrant | — | identity.Role, identity.User | 0 | 0 | 1 | — | Planned | `docs(model-review): review identity UserRoleGrant` |
| HMSR-107 | 5 | incident | IncidentClosure | — | incident.Incident, workflow.WorkflowInstance | 0 | 0 | 0 | — | Planned | `docs(model-review): review incident IncidentClosure` |
| HMSR-108 | 5 | incident | IncidentRelatedIncident | — | incident.Incident | 0 | 0 | 0 | — | Planned | `docs(model-review): review incident IncidentRelatedIncident` |
| HMSR-109 | 5 | incident | IncidentResponseAction | — | incident.Incident, organization.OrganizationUnit | 0 | 0 | 0 | — | Planned | `docs(model-review): review incident IncidentResponseAction` |
| HMSR-110 | 5 | reporting | ReportOutputArtifact | — | documents.Document, documents.DocumentStorageObject, reporting.ReportRun | 0 | 0 | 2 | — | Planned | `docs(model-review): review reporting ReportOutputArtifact` |
| HMSR-111 | 6 | planning | PlanTarget | — | planning.Nomination, planning.PlanRevision, telemetry.TelemetryPoint | 2 | 5 | 2 | — | Planned | `docs(model-review): review planning PlanTarget` |
| HMSR-112 | 6 | audit | AuditEvent | — | organization.OrganizationUnit, workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowTask | 2 | 2 | 3 | — | Planned | `docs(model-review): review audit AuditEvent` |
| HMSR-113 | 6 | hse | HseClosure | — | hse.HseCase, workflow.WorkflowInstance | 0 | 0 | 0 | — | Planned | `docs(model-review): review hse HseClosure` |
| HMSR-114 | 6 | hse | HseCorrectivePreventiveAction | — | assets.MaintenanceWorkOrder, hse.HseCase, organization.OrganizationUnit, workflow.WorkflowTask | 0 | 0 | 0 | — | Planned | `docs(model-review): review hse HseCorrectivePreventiveAction` |
| HMSR-115 | 6 | integrity | IntegrityCase | — | hse.HseCase, incident.Incident, integrity.PipelineDefect, organization.OrganizationUnit, workflow.WorkflowInstance | 0 | 0 | 1 | — | Planned | `docs(model-review): review integrity IntegrityCase` |
| HMSR-116 | 6 | workflow | WorkflowStateHistory | — | workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowStep, workflow.WorkflowTask | 0 | 0 | 1 | — | Planned | `docs(model-review): review workflow WorkflowStateHistory` |
| HMSR-117 | 7 | alarm | Alarm | — | incident.Incident, organization.OrganizationUnit, planning.PlanTarget, telemetry.TelemetryReading, workflow.WorkflowInstance | 3 | 3 | 4 | — | Planned | `docs(model-review): review alarm Alarm` |
| HMSR-118 | 7 | audit | AuditAccessRecord | — | audit.AuditEvent, audit.AuditExportRequest | 0 | 0 | 0 | — | Planned | `docs(model-review): review audit AuditAccessRecord` |
| HMSR-119 | 7 | audit | AuditBeforeAfterValue | — | audit.AuditEvent | 0 | 0 | 0 | — | Planned | `docs(model-review): review audit AuditBeforeAfterValue` |
| HMSR-120 | 7 | monitoring | PlanActualDeviation | — | planning.PlanTarget, telemetry.TelemetryPoint, telemetry.TrustedTelemetryReading | 0 | 0 | 4 | — | Planned | `docs(model-review): review monitoring PlanActualDeviation` |
| HMSR-121 | 8 | alarm | AlarmAcknowledgement | — | alarm.Alarm, organization.OrganizationUnit | 0 | 0 | 0 | — | Planned | `docs(model-review): review alarm AlarmAcknowledgement` |
| HMSR-122 | 8 | alarm | AlarmClosure | — | alarm.Alarm, workflow.WorkflowInstance | 0 | 0 | 0 | — | Planned | `docs(model-review): review alarm AlarmClosure` |
| HMSR-123 | 8 | alarm | AlarmShelving | — | alarm.Alarm | 0 | 0 | 0 | — | Planned | `docs(model-review): review alarm AlarmShelving` |

Each HMSR code is an independent interactive review commit. Execute only the model explicitly authorized by the user. A model in an SCC may be reviewed individually, but its final semantic acceptance must remain consistent with the other members of that SCC.

## 9. Completion gate

The semantic baseline is complete only when:

- all 123 catalogued models appear exactly once in the review register;
- every dependency edge has evidence/classification or an explicit unresolved decision;
- graph cycles are identified and resolved/grouped;
- every model has an interactive decision;
- model corrections required by REVISE/REMOVE decisions are implemented through their owning module roadmaps or explicitly deferred;
- dependency impacts are recalculated after corrections;
- HMS-007 is complete.

Only HMS-008 may then decide whether HDP-004 can resume.

## 10. HMS-002 — Candidate dependency evidence inventory

**Task:** HMS-002 — inventory target model dependency evidence  
**Live repository baseline:** `efa8b9033a64d509b9439c7f720ea7cf5ea6c9a7`  
**Catalogue baseline:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Subject set:** 123 / 123 catalogue models  
**Live scoped reconciliation:** 123 present, 0 missing, 0 extra  
**Purpose:** evidentiary inventory only; no dependency classification, graph ordering, review levels, cycle decisions, or HMSR codes are produced here.

### 10.1 Evidence policy

A field is recorded as a candidate relationship field when its catalogue name ends in an ID/reference-ID form or when its declared type directly names one of the 123 subject models. This is deliberately broader than a confirmed dependency.

For every subject model this inventory retains the HMC declaration evidence, current live domain path/blob SHA, source-drift flag, matching JPA entity path/blob when present, the module application-port corpus, the module Flyway corpus, and non-scalar/custom domain component types with support-source paths where mechanically resolvable.

Field-name similarity is not a dependency decision. Snapshot/code/name fields, catalog IDs, actor IDs, polymorphic targets, workflow references and external references remain unclassified until HMS-003.

### 10.2 Reconciliation summary

| Measure | Result |
|---|---:|
| Subject models | 123 |
| Live domain paths present | 123 |
| Subject domain blobs changed since HMC pinned source | 0 |
| Matching subject JPA entities present | 122 |
| Candidate relationship fields inventoried | 627 |
| Direct subject-model typed candidate fields | 0 |
| Custom/non-scalar component types inventoried | 161 |
| Current Flyway files | 40 |
| Code-search hits for FOREIGN KEY in migration paths | 6 |
| Code-search hits for REFERENCES in migration paths | 0 |

These migration search counts are evidence signals only. HMS-003 must inspect relevant SQL definitions directly before deciding persistence dependency semantics.

### 10.3 Module evidence corpus

| Module | Subject models | App in/out ports | Flyway files | Domain support files |
|---|---:|---:|---:|---:|
| alarm | 4 | 13 | 3 | 27 |
| analytics | 7 | 28 | 1 | 24 |
| assets | 3 | 16 | 1 | 26 |
| audit | 4 | 15 | 3 | 18 |
| configuration | 3 | 13 | 1 | 23 |
| custody | 3 | 15 | 1 | 22 |
| documents | 4 | 17 | 1 | 21 |
| hse | 4 | 17 | 1 | 24 |
| identity | 15 | 27 | 2 | 47 |
| incident | 4 | 10 | 1 | 16 |
| integration | 4 | 15 | 1 | 37 |
| integrity | 4 | 16 | 4 | 21 |
| leakdetection | 3 | 9 | 1 | 24 |
| monitoring | 2 | 7 | 1 | 23 |
| notification | 4 | 18 | 1 | 27 |
| organization | 17 | 31 | 7 | 34 |
| party | 2 | 6 | 1 | 32 |
| planning | 5 | 13 | 1 | 24 |
| reporting | 4 | 21 | 1 | 29 |
| risk | 4 | 22 | 1 | 19 |
| simulation | 6 | 25 | 1 | 22 |
| telemetry | 4 | 9 | 1 | 23 |
| topology | 5 | 10 | 1 | 26 |
| workflow | 8 | 22 | 1 | 24 |

### 10.4 Per-model evidence register

#### alarm.Alarm

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java` @ `8561b8257e7402d8853aa466bc2a03f2efb8c5c7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java` @ `80729f1c1d4f897fc4a14cea7929645bb81e67fb`.
- Candidate relationship fields (15): `id:String`, `alarmTypeId:String`, `severityId:String`, `priorityId:String`, `sourceReferenceId:String`, `monitoringAlertCandidateId:String`, `monitoringEvaluationId:String`, `telemetryReadingId:String`, `planningTargetId:String`, `topologyAssetId:String`, `acknowledgedByActorId:String`, `owningOrganizationUnitId:String`, `workflowInstanceId:String`, `incidentId:String`, `correlationId:String`.
- Custom component types: `AlarmSourceType` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`; `AlarmState` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### alarm.AlarmAcknowledgement

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java` @ `bd31d48a5c0fb636324d5d0264226a1933b54dc0` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java` @ `90828ba83daa08d8d76af9a5dec6fdbfb1152eaf`.
- Candidate relationship fields (5): `id:String`, `alarmId:String`, `acknowledgedByActorId:String`, `organizationUnitId:String`, `correlationId:String`.
- Custom component types: none.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### alarm.AlarmClosure

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java` @ `0630ea0024d45b2f7fd453e58bde143d5ef1b472` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java` @ `4b4a794aaa65026ce7ee8da211a198b358adca85`.
- Candidate relationship fields (6): `id:String`, `alarmId:String`, `closureReasonId:String`, `closedByActorId:String`, `reviewWorkflowInstanceId:String`, `correlationId:String`.
- Custom component types: `AlarmClosureType` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### alarm.AlarmShelving

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java` @ `9f90489e42cca1d8b8a057d176aab1b383986a34` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java` @ `b98f96b2f8068a831210ed728300f83c6ff9686e`.
- Candidate relationship fields (6): `id:String`, `alarmId:String`, `shelvingReasonId:String`, `shelvedByActorId:String`, `unshelvedByActorId:String`, `correlationId:String`.
- Custom component types: `AlarmShelvingStatus` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### analytics.AnalyticsDataset

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java` @ `314f2d95fdc1fdbb9ade52611bdf84c1c1bd372f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetJpaEntity.java` @ `33ab5133e66774c59be8e9786aa918b7b861c1c0`.
- Candidate relationship fields (2): `id:String`, `subjectAreaId:String`.
- Custom component types: `AnalyticsDatasetType` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsDatasetType.java`; `AnalyticsRefreshMode` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRefreshMode.java`; `AnalyticsLineageStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsLineageStatus.java`; `AnalyticsQualityStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.AnalyticsDatasetVersion

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java` @ `3003674c5b624efe83d53dc3c9a10f7c29720937` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java` @ `32d1f3331335d20629914b65d5fdee71769e227b`.
- Candidate relationship fields (3): `id:String`, `datasetId:String`, `publishedByActorId:String`.
- Custom component types: none.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.AnalyticsInsight

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java` @ `88779a67538a5bb136770e27bd21692e6c85e0cd` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java` @ `d3ba714dc4b00549e03888525362378667dcb3fb`.
- Candidate relationship fields (7): `id:String`, `subjectAreaId:String`, `scopeId:String`, `severityId:String`, `sourceProjectionSnapshotId:String`, `sourceTrendAnalysisId:String`, `sourceModelRunId:String`.
- Custom component types: `AnalyticsInsightStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.AnalyticsProjectionRun

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java` @ `dbfdca2e9dede8bf7feb02f677d4698121ed0de3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java` @ `b8d2561d08d0b31ac35126d6d1f55375ca4a5c61`.
- Candidate relationship fields (3): `id:String`, `projectionDefinitionId:String`, `correlationId:String`.
- Custom component types: `AnalyticsRunStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`; `AnalyticsRunMode` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunMode.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.DigitalTwinReadinessAssessment

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java` @ `02656c55474f5c3f49631257efcfc0e1a9645cbd` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java` @ `35213a2e8dc5ec0053e5d0e649dcbd01df6e997a`.
- Candidate relationship fields (3): `id:String`, `scopeId:String`, `topologySnapshotId:String`.
- Custom component types: `DigitalTwinReadinessStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.MetricEvaluationRun

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java` @ `1f94a0756c5e17a7e87a7975ac5cbfc14770a48f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java` @ `ed642071646edf73fcdc5adeae611c7203ea12f7`.
- Candidate relationship fields (4): `id:String`, `metricDefinitionVersionId:String`, `scopeId:String`, `correlationId:String`.
- Custom component types: `AnalyticsRunStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.MetricValue

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java` @ `827d8dccdba064b5059febcc852eb37afa0fad84` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java` @ `83d13b3e0440dcdc633cc374ad6a7e48bd82f3aa`.
- Candidate relationship fields (6): `id:String`, `metricEvaluationRunId:String`, `metricDefinitionId:String`, `metricDefinitionVersionId:String`, `scopeId:String`, `unitId:String`.
- Custom component types: `AnalyticsQualityStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### assets.AssetConditionRecord

- Domain: `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java` @ `75e2aa4dccaa2d545c7395f67179493ce56fd92e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetConditionRecordJpaEntity.java` @ `e0ea28f10afeab2e9c25beb51bd62892ad81db3b`.
- Candidate relationship fields (5): `id:String`, `maintainableAssetId:String`, `conditionTypeId:String`, `sourceReferenceId:String`, `observedByActorId:String`.
- Custom component types: `AssetConditionStatus` → `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`.
- Application-contract corpus: 16 current port files in module `assets`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`.

#### assets.MaintainableAsset

- Domain: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java` @ `198eb3abf9e9e16388b2b5587341feae9b585f1b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java` @ `f97564c21814b38dbfa60517daf745de417c5007`.
- Candidate relationship fields (10): `id:String`, `assetTypeId:String`, `topologyAssetId:String`, `parentAssetId:String`, `criticalityId:String`, `ownerOrganizationUnitId:String`, `manufacturerPartyId:String`, `modelId:String`, `serialIdentityId:String`, `createdByActorId:String`.
- Custom component types: `AssetLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetLifecycleStatus.java`.
- Application-contract corpus: 16 current port files in module `assets`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`.

#### assets.MaintenanceWorkOrder

- Domain: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java` @ `2a2c37d51402a1587938c0f9d9e72e219955f326` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java` @ `16ce1508ecc09b71694ff850cd3315122e7147b1`.
- Candidate relationship fields (10): `id:String`, `maintainableAssetId:String`, `maintenancePlanId:String`, `sourceRecommendationId:String`, `workOrderTypeId:String`, `priorityId:String`, `assignedOrganizationUnitId:String`, `assignedActorId:String`, `workflowInstanceId:String`, `createdByActorId:String`.
- Custom component types: `MaintenanceWorkOrderStatus` → `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`.
- Application-contract corpus: 16 current port files in module `assets`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`.

#### audit.AuditAccessRecord

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java` @ `33aecb5141eca64d2ebf8ba7c04c141a24858dd8` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java` @ `4e388681514ddb1abb95307c88fec506c9675a28`.
- Candidate relationship fields (5): `id:String`, `actorId:String`, `auditEventId:String`, `exportRequestId:String`, `correlationId:String`.
- Custom component types: `AuditAccessType` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditAccessType.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### audit.AuditBeforeAfterValue

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java` @ `72e93122a1ee2d61474dfd04d3c2f2f621ad54d8` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java` @ `85ce0b4e1e8cfe3701dbdfa78483f969abe675da`.
- Candidate relationship fields (3): `id:String`, `auditEventId:String`, `maskReasonId:String`.
- Custom component types: `AuditValueType` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditValueType.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### audit.AuditEvent

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java` @ `dc22f110ae5fec740db31dd2b3130d92635cae6b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java` @ `d7a3fa2d6562ec12adf58c900a2487233833dba5`.
- Candidate relationship fields (16): `id:String`, `eventTypeId:String`, `eventCategoryId:String`, `severityId:String`, `sourceEventId:String`, `actorId:String`, `organizationUnitId:String`, `targetId:String`, `reasonId:String`, `workflowInstanceId:String`, `workflowTaskId:String`, `workflowActionId:String`, `requestId:String`, `correlationId:String`, `causationId:String`, `retentionPolicyId:String`.
- Custom component types: `AuditEventStatus` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`; `AuditActorType` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`; `AuditOperation` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditOperation.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### audit.AuditExportRequest

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java` @ `1f14b4388228903d60e18a6f2bb26cb9eabf8d45` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java` @ `72b4ec82b944ac811111db094a4e38e5d92a9859`.
- Candidate relationship fields (5): `id:String`, `requestedByActorId:String`, `purposeId:String`, `workflowInstanceId:String`, `resultDocumentReferenceId:String`.
- Custom component types: `AuditExportStatus` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditExportStatus.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### configuration.ConfigurationDefinition

- Domain: `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java` @ `7bc10ae709f60531d38536afe23e27421fdb6653` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java` @ `943aee9d199274aaaf2befaf176088fbb378dfbf`.
- Candidate relationship fields (2): `id:String`, `namespaceId:String`.
- Custom component types: `ConfigurationValueType` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueType.java`; `ConfigurationSensitivity` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationSensitivity.java`; `ConfigurationDefinitionStatus` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationDefinitionStatus.java`.
- Application-contract corpus: 13 current port files in module `configuration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`.

#### configuration.ConfigurationValue

- Domain: `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java` @ `812bc82ab6296bfd2fcd70c9e65689162d2d3562` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java` @ `43c40fab84de4a4e801c0e92b2307ee031ffb643`.
- Candidate relationship fields (4): `id:String`, `definitionId:String`, `definitionVersionId:String`, `createdByActorId:String`.
- Custom component types: `ConfigurationValueStatus` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueStatus.java`.
- Application-contract corpus: 13 current port files in module `configuration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`.

#### configuration.FeatureFlag

- Domain: `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java` @ `f4a193733e0b7dbd79b1806146be29389ff5db0c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java` @ `873a934706115d50e06c5ae3a21caa9bc32449b9`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `FeatureFlagStatus` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagStatus.java`; `FeatureFlagEvaluationStrategy` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagEvaluationStrategy.java`.
- Application-contract corpus: 13 current port files in module `configuration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`.

#### custody.CustodyDiscrepancy

- Domain: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java` @ `6ae7cd8844236dfce1eca3882b1a4645dc77acd7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java` @ `43aa26e563b8249de8c2a361844f912b28b9440e`.
- Candidate relationship fields (5): `id:String`, `reconciliationId:String`, `discrepancyTypeId:String`, `quantityUnitId:String`, `assignedActorId:String`.
- Custom component types: `CustodyDiscrepancyStatus` → `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`.
- Application-contract corpus: 15 current port files in module `custody`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`.

#### custody.CustodyMeasurementPeriod

- Domain: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java` @ `fe88fc8be06e024265bebc94aca3ad23a2ee15a9` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java` @ `9f298c3ee9072d632568fa681b28bceeaaa0455f`.
- Candidate relationship fields (5): `id:String`, `agreementId:String`, `transferPointId:String`, `lockedByActorId:String`, `approvedByActorId:String`.
- Custom component types: `CustodyPeriodStatus` → `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`.
- Application-contract corpus: 15 current port files in module `custody`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`.

#### custody.CustodyTransferTicket

- Domain: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java` @ `24e258556cd4210b88cfc9196f8efda0080b78ca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java` @ `38ee89aac2ceef4f04e662b7a888f7c43c029ffa`.
- Candidate relationship fields (10): `id:String`, `measurementPeriodId:String`, `agreementId:String`, `transferPointId:String`, `batchId:String`, `quantityCalculationId:String`, `issuedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`, `auditReferenceId:String`.
- Custom component types: `CustodyTicketStatus` → `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`.
- Application-contract corpus: 15 current port files in module `custody`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`.

#### documents.Document

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java` @ `2374c7764b911fc2dfc718986281f3265dfe6b34` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java` @ `2d8c9637193170abb66c087d1d3de7800ead01e3`.
- Candidate relationship fields (7): `id:String`, `documentTypeId:String`, `documentCategoryId:String`, `classificationId:String`, `currentVersionId:String`, `ownerTargetId:String`, `createdByActorId:String`.
- Custom component types: `DocumentStatus` → `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### documents.DocumentStorageObject

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java` @ `88c69e394ae5447cad06ea441fad9f8ac19efc11` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java` @ `2baafe5cbc3a8f0db2276569f477145ff8aad488`.
- Candidate relationship fields (2): `id:String`, `storageProviderId:String`.
- Custom component types: `DocumentStorageStatus` → `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### documents.DocumentTargetLink

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java` @ `41812389c9061c3cb7f68dc21739aeb03802218b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java` @ `c3b536a5af492ae7c13fbbdf73846f9e6a3ef7b9`.
- Candidate relationship fields (6): `id:String`, `documentId:String`, `documentVersionId:String`, `targetId:String`, `linkRoleId:String`, `linkedByActorId:String`.
- Custom component types: none.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### documents.DocumentVersion

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java` @ `12e02c7d4cdda7c0ffd96045789267a38613a45e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java` @ `03feeb66d6eca23929cc10a8d8940f218d71df0f`.
- Candidate relationship fields (6): `id:String`, `documentId:String`, `storageObjectId:String`, `uploadedByActorId:String`, `approvedByWorkflowInstanceId:String`, `supersededByVersionId:String`.
- Custom component types: `DocumentVersionStatus` → `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### hse.HseCase

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java` @ `fb37bfead66085fce4337a152f8267a5b8677b64` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java` @ `a60ec5fb350424d380fa9ca41de84ee4cbd621ba`.
- Candidate relationship fields (10): `id:String`, `caseTypeId:String`, `severityId:String`, `priorityId:String`, `incidentReferenceId:String`, `targetId:String`, `reportedByActorId:String`, `responsibleOrganizationUnitId:String`, `workflowInstanceId:String`, `auditReferenceId:String`.
- Custom component types: `HseCaseStatus` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`; `HseCaseSourceType` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### hse.HseClosure

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java` @ `71115680f505afbdca2ede7eaf9b9f273e05338c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java` @ `7ec97b74a4c1f391b03dfa0f882c8a43bfebcaa9`.
- Candidate relationship fields (4): `id:String`, `hseCaseId:String`, `closedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: none.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### hse.HseCorrectivePreventiveAction

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java` @ `7a28512cb5f04e8065d1c58868a752b78950972d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java` @ `6ac99c9ab5a65c47574f00307a76ce2e5bc4fc25`.
- Candidate relationship fields (8): `id:String`, `hseCaseId:String`, `actionTypeId:String`, `ownerActorId:String`, `ownerOrganizationUnitId:String`, `verifiedByActorId:String`, `linkedWorkOrderId:String`, `workflowTaskId:String`.
- Custom component types: `CapaStatus` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/CapaStatus.java`.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### hse.PermitToWork

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/PermitToWork.java` @ `554628dcc37994794fe5bbed3fd041b61c0e98d2` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/PermitToWorkJpaEntity.java` @ `45b89dacbe0cccc52e3cca0049b83314ed4dca93`.
- Candidate relationship fields (6): `id:String`, `permitTypeId:String`, `targetId:String`, `requestedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: `PermitStatus` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/PermitStatus.java`.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### identity.AuthenticationEvent

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java` @ `f8b05a2ca852fdf11efc441fceffc3547c4d75dd` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthenticationEventJpaEntity.java` @ `905d5df225750a5774645e2e9f4555d1c23a33e7`.
- Candidate relationship fields (5): `id:String`, `userId:String`, `identityProviderId:String`, `externalIdentityId:String`, `correlationId:String`.
- Custom component types: `AuthenticationEventType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`; `AuthenticationProtocol` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.AuthorizationDecision

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java` @ `91850487114b970c89036c6b148284dc1d62d8ca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java` @ `36e425623f3a0b9a4574eecc911c6a2d253a405d`.
- Candidate relationship fields (7): `id:String`, `userId:String`, `resourceReferenceId:String`, `matchedGrantIds:String`, `matchedPolicyRuleIds:String`, `correlationId:String`, `requestId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `AuthorizationDecisionValue` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.AuthorizationDelegationGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java` @ `f423d478d54f189214516a6ecc09d3d17fc679d2` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java` @ `0a055cfc35b63f58131e78525be8d85a8baee28e`.
- Candidate relationship fields (6): `id:String`, `delegatorUserId:String`, `delegateUserId:String`, `permissionId:String`, `roleId:String`, `approvedByWorkflowId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `DelegationStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/DelegationStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.ExternalRoleMapping

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java` @ `cb238fc3b378f4a36b7302934df58f77dddd4ff0` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java` @ `a0d147a2ca914e1f4b1a31c55d602ef869486a3f`.
- Candidate relationship fields (3): `id:String`, `identityProviderId:String`, `roleId:String`.
- Custom component types: `ExternalMappingMode` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`; `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `ExternalMappingStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.GroupRoleGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java` @ `b10324fff0d134b2e44e9abd55c2f4b03210036d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java` @ `813fa39de9412e16581aa02b120872d126a74aa1`.
- Candidate relationship fields (4): `id:String`, `groupId:String`, `roleId:String`, `approvedByWorkflowId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.HidraPrincipal

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java` @ `240c6d4972cbb2bd1f2b5e6252e9ddd2a72d929c` — unchanged from HMC pinned blob.
- JPA evidence: no matching `HidraPrincipalJpaEntity` path in the expected live module entity directory.
- Candidate relationship fields (2): `userId:String`, `identityProviderId:String`.
- Custom component types: `ProviderType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.IdentityProvider

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java` @ `46f0e278976c77d21242f5f9bb1dd7b1f1544abe` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java` @ `dd3114b2b3121de20446647bcc0dacbf73a95b4e`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ProviderType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`; `IdentityProviderStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.LocalCredential

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java` @ `f0347e0e37f72c39e9479c1e102c5ef81cc244a4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java` @ `5d20e1c5eb228d94e9a49e688bba0e2ed78c667b`.
- Candidate relationship fields (2): `id:String`, `userId:String`.
- Custom component types: none.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.LoginSession

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java` @ `4ed254552fec6e0fa56584f3707da10dba71e31d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java` @ `f67e2a9077238d2792ba6207aa65f5667d72906a`.
- Candidate relationship fields (5): `id:String`, `userId:String`, `identityProviderId:String`, `externalIdentityId:String`, `correlationId:String`.
- Custom component types: `LoginSessionStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.Permission

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java` @ `3b3c2bd4493622f8217e8ee05b24a6375541d23b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java` @ `83a344f5dc58de25f6af4cb122be836e6f241a9d`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `PermissionStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.Role

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java` @ `71fc4998af6e19b0a584ee48940b7aaf75730cc4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java` @ `98a08494d43d48d04391373fabd760c088418197`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `RoleType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleType.java`; `RoleStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.RolePermissionGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java` @ `9ea5ff60c90022f6c649af2c2ce2af8c354f7424` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java` @ `c5c7270ee60d3962db41277558931420e3415e6e`.
- Candidate relationship fields (3): `id:String`, `roleId:String`, `permissionId:String`.
- Custom component types: `GrantEffect` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.User

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java` @ `11461447721c0b9144f9f467687ef940f8a7b451` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java` @ `80f8ed4253d1ce7069c564773f4a72bc20191ff3`.
- Candidate relationship fields (2): `id:String`, `employeeReferenceId:String`.
- Custom component types: `UserType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`; `UserStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.UserPermissionGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java` @ `d8648c3ce5e11b1b9ad18bad3499a7226c54aba8` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java` @ `beb596fe855e45cac574a289aa9991c81402aa49`.
- Candidate relationship fields (4): `id:String`, `userId:String`, `permissionId:String`, `approvedByWorkflowId:String`.
- Custom component types: `GrantEffect` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`; `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.UserRoleGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java` @ `4a72ae40d58752450f1ce162dec149058d671e41` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java` @ `03b47e1aa772cb38d7c5eeeaa66fc56b28af6b74`.
- Candidate relationship fields (4): `id:String`, `userId:String`, `roleId:String`, `approvedByWorkflowId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### incident.Incident

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java` @ `a35109045ba76d3f328b4d9ef0e7badd803a82ce` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java` @ `56e000a910c8403ce9023d101bd575dce4ec7a1e`.
- Candidate relationship fields (10): `id:String`, `classificationId:String`, `severityId:String`, `priorityId:String`, `sourceReferenceId:String`, `topologyAssetId:String`, `responsibleOrganizationUnitId:String`, `responsibleActorId:String`, `workflowInstanceId:String`, `createdByActorId:String`.
- Custom component types: `IncidentStatus` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentStatus.java`; `IncidentSourceType` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentSourceType.java`.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### incident.IncidentClosure

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java` @ `f29b03eac51b1ce40872e3917279ff85b49cba21` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java` @ `4a95b3013bfdd7a5439a6ac3ba8b33ae36c07936`.
- Candidate relationship fields (4): `id:String`, `incidentId:String`, `closedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: none.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### incident.IncidentRelatedIncident

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java` @ `962ebb8edbe7589a32047e72de0909868bf3b8e7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java` @ `592ab3c8abac06291c5e199a75a64df9098bb44a`.
- Candidate relationship fields (5): `id:String`, `incidentId:String`, `relatedIncidentId:String`, `relationshipTypeId:String`, `createdByActorId:String`.
- Custom component types: none.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### incident.IncidentResponseAction

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java` @ `abee396c20ef861bdb1be5d79eb9e21beef4c678` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java` @ `9aa37cec25de424b34809a26af6ecf8f516fc39c`.
- Candidate relationship fields (6): `id:String`, `incidentId:String`, `actionTypeId:String`, `targetReferenceId:String`, `performedByActorId:String`, `organizationUnitId:String`.
- Custom component types: `ResponseActionStatus` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseActionStatus.java`; `ResponseTargetType` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseTargetType.java`.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### integration.ExternalSystem

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java` @ `a4874f7c6a858818cd46b1216dc3e977b1d07011` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java` @ `c01c2dc5cd367f4905c6b5411fc853d8bb5cc607`.
- Candidate relationship fields (3): `id:String`, `systemTypeId:String`, `ownerOrganizationUnitId:String`.
- Custom component types: `IntegrationEnvironment` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationEnvironment.java`; `IntegrationCriticality` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationCriticality.java`; `ExternalSystemStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integration.IntegrationDeadLetterRecord

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java` @ `8209f48143e53f3e676bea615d0c5f1205e225a1` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java` @ `a0b629c9b2a742e3978b4a46923607a5186b42c9`.
- Candidate relationship fields (7): `id:String`, `externalSystemId:String`, `jobRunId:String`, `exchangeMessageId:String`, `inboundRecordId:String`, `outboundRecordId:String`, `resolvedByActorId:String`.
- Custom component types: `DeadLetterStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/DeadLetterStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integration.IntegrationExchangeMessage

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java` @ `34cf7dec9de05e43d89deb1be137b96b67c0556c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java` @ `a985eabf0834e3ccd9b5d7aa03583dc2a35eecf1`.
- Candidate relationship fields (8): `id:String`, `jobRunId:String`, `externalSystemId:String`, `endpointId:String`, `messageTypeId:String`, `externalMessageId:String`, `payloadFormatId:String`, `correlationId:String`.
- Custom component types: `IntegrationDirection` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationDirection.java`; `PayloadStorageMode` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/PayloadStorageMode.java`; `ExchangeMessageStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExchangeMessageStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integration.IntegrationJobRun

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java` @ `64ae0983e5dc963e080178ca286eaee0c1356999` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java` @ `a7643063fb76fb729ed92ef9468b602db851c484`.
- Candidate relationship fields (4): `id:String`, `jobDefinitionId:String`, `triggeredByActorId:String`, `correlationId:String`.
- Custom component types: `JobTriggerType` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobTriggerType.java`; `JobRunStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobRunStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integrity.IntegrityAssessment

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java` @ `237361a88167caf6979cf4d0d61390b8dbc3b4ce` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java` @ `b00bf9108daa55fea001df2a5b37c0e23757633c`.
- Candidate relationship fields (9): `id:String`, `programId:String`, `assessmentTypeId:String`, `methodologyId:String`, `assessedByActorId:String`, `reviewedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`, `auditReferenceId:String`.
- Custom component types: `IntegrityAssessmentStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### integrity.IntegrityCase

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java` @ `c1ccaeb1ebb3c547027b4a88a18699f3be4577e9` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java` @ `cd2aabac980bd8218a2139e961978aff1b68c851`.
- Candidate relationship fields (10): `id:String`, `caseTypeId:String`, `severityId:String`, `topologyAssetId:String`, `primaryDefectId:String`, `sourceIncidentId:String`, `sourceHseCaseId:String`, `responsibleOrganizationUnitId:String`, `workflowInstanceId:String`, `openedByActorId:String`.
- Custom component types: `IntegrityCaseStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### integrity.IntegrityProgram

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java` @ `213b41c6281f0a8d1d82ef7f86c2e7ddbe0f57cc` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java` @ `6946abecc283e4b792693397f7e11de1448771ca`.
- Candidate relationship fields (4): `id:String`, `programTypeId:String`, `ownerOrganizationUnitId:String`, `createdByActorId:String`.
- Custom component types: `IntegrityProgramStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityProgramStatus.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### integrity.PipelineDefect

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java` @ `a6004398f0caa5647ca4f681c1a584da84d6ca30` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java` @ `2e829b62dce56f5737c19ac0694aa3f55b73eb56`.
- Candidate relationship fields (4): `id:String`, `defectTypeId:String`, `topologyAssetId:String`, `sourceFindingId:String`.
- Custom component types: `ThreatType` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/ThreatType.java`; `DefectStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/DefectStatus.java`; `FindingSeverity` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/FindingSeverity.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### leakdetection.LeakCandidate

- Domain: `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java` @ `5d92f187ec860004a81803b06436d8cb7532b27d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java` @ `a999ec72982f8323fb440c619d2a0613fe34ab1d`.
- Candidate relationship fields (5): `id:String`, `runId:String`, `profileId:String`, `topologyAssetId:String`, `correlationId:String`.
- Custom component types: `LeakSeverityLevel` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakSeverityLevel.java`; `LeakCandidateStatus` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakCandidateStatus.java`.
- Application-contract corpus: 9 current port files in module `leakdetection`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`.

#### leakdetection.LeakDetectionCase

- Domain: `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java` @ `2e742da22a0710f1a0016005922e5568cd367752` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java` @ `16fd6728431a9436632dd4254f0f7c2e637f6115`.
- Candidate relationship fields (8): `id:String`, `primaryCandidateId:String`, `topologyAssetId:String`, `owningOrganizationUnitId:String`, `openedByActorId:String`, `closedByActorId:String`, `closureReasonId:String`, `correlationId:String`.
- Custom component types: `LeakDetectionCaseStatus` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakDetectionCaseStatus.java`; `LeakSeverityLevel` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakSeverityLevel.java`.
- Application-contract corpus: 9 current port files in module `leakdetection`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`.

#### leakdetection.LeakEscalationReference

- Domain: `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java` @ `8d2ba4d58f15e6c6a1d606e22717ec9825eb2341` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java` @ `afc859e914c8e4b202c30170c0320a53b1af30e2`.
- Candidate relationship fields (6): `id:String`, `caseId:String`, `candidateId:String`, `targetReferenceId:String`, `escalatedByActorId:String`, `correlationId:String`.
- Custom component types: `LeakEscalationTargetType` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationTargetType.java`; `LeakEscalationStatus` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationStatus.java`.
- Application-contract corpus: 9 current port files in module `leakdetection`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`.

#### monitoring.MonitoringRule

- Domain: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java` @ `7b31d59e3c112969ba4df68a25d6044f4f29cd4d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java` @ `c029be86d255ce6069ec8ab276ecc66bd2c02830`.
- Candidate relationship fields (6): `id:String`, `evaluationFrequencyId:String`, `topologyAssetId:String`, `telemetryPointId:String`, `planningTargetTypeId:String`, `createdByActorId:String`.
- Custom component types: `MonitoringRuleType` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`; `MonitoringLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`.
- Application-contract corpus: 7 current port files in module `monitoring`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_007__create_monitoring_tables.sql`.

#### monitoring.PlanActualDeviation

- Domain: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java` @ `4fdceb2db374bca1f6529c730c732a437c53f3e4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java` @ `f28548a5d371da11b67f49e69c7d9c20d0bed373`.
- Candidate relationship fields (8): `id:String`, `evaluationId:String`, `planTargetId:String`, `expectedFlowStateId:String`, `trustedTelemetryReadingId:String`, `telemetryPointId:String`, `topologyAssetId:String`, `unitId:String`.
- Custom component types: `DeviationSeverity` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`; `DeviationStatus` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`.
- Application-contract corpus: 7 current port files in module `monitoring`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_007__create_monitoring_tables.sql`.

#### notification.NotificationDeliveryAttempt

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java` @ `c97afd5095da074816855d1f4fd8b497fd89fb4e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java` @ `f560b13198152a5f24da4c6a6fa4f6a5a8dfbfde`.
- Candidate relationship fields (5): `id:String`, `messageId:String`, `channelId:String`, `providerMessageId:String`, `correlationId:String`.
- Custom component types: `DeliveryAttemptStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### notification.NotificationMessage

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java` @ `fd32797447789db1c5ac2ef8b694b6cfe6398a2f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java` @ `e360119d113f4a350a2bc5f5dab3b260341b792b`.
- Candidate relationship fields (7): `id:String`, `requestId:String`, `recipientId:String`, `channelId:String`, `templateId:String`, `templateVersionId:String`, `priorityId:String`.
- Custom component types: `NotificationMessageStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### notification.NotificationRequest

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java` @ `d4a1913539101ad6b6b90c77bb34c5b2b7218f18` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java` @ `8867bd492ff64897422575dd8e8061582ababddb`.
- Candidate relationship fields (11): `id:String`, `sourceEventId:String`, `targetId:String`, `categoryId:String`, `priorityId:String`, `policyId:String`, `templateId:String`, `templateVersionId:String`, `requestedByActorId:String`, `correlationId:String`, `requestId:String`.
- Custom component types: `NotificationRequestStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### notification.NotificationTemplate

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java` @ `d24522d17909702e81e02a73e4ba1c8179cc91b4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java` @ `6f8ff529cff2f1e096b96a9461952a3f8438f899`.
- Candidate relationship fields (4): `id:String`, `templateTypeId:String`, `categoryId:String`, `defaultChannelId:String`.
- Custom component types: `NotificationTemplateStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### organization.AdministrativeDistrict

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java` @ `83182d46789eb3309c6b00c96a29affa6eb5f8d1` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java` @ `031d2e1dc5e35689c8fa20bcd377f87ded6439bc`.
- Candidate relationship fields (2): `id:String`, `stateId:String`.
- Custom component types: none.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.AdministrativeLocality

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java` @ `3b25c187eb2c09c9d554044cddba11b2bf345dec` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java` @ `54c8ab2e7ff65d75c7dbfe416523c4242073280d`.
- Candidate relationship fields (2): `id:String`, `districtId:String`.
- Custom component types: none.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.AdministrativeState

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java` @ `4aa38d466386dd1437b40d2ccfff7c40604c869d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java` @ `85a2149b6a15666ada8b15383fed391f71e21611`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: none.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.Employee

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/Employee.java` @ `5274853a4ffc75caeac5ceb6f671542964e31063` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java` @ `115d5031d065cca1bbdbceef4e56382609134c09`.
- Candidate relationship fields (2): `id:String`, `birthLocalityId:String`.
- Custom component types: `EmployeeType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeType.java`; `EmployeeStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.EmployeeAddress

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java` @ `549ce43942f6457c2dc81c02a5f3dddb015b45ea` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java` @ `f4bbe0f7b2f9a880ab02498cbc80138bbd60e4bf`.
- Candidate relationship fields (3): `id:String`, `employeeId:String`, `localityId:String`.
- Custom component types: `AddressType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AddressType.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.EmployeeAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java` @ `fb8baa91856a0f1daf71934560cf47b6b702cdca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java` @ `2c6d6f32493d976dd6ffaad3d2503225d891c624`.
- Candidate relationship fields (4): `id:String`, `employeeId:String`, `organizationUnitId:String`, `positionId:String`.
- Custom component types: `AssignmentType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentType.java`; `AssignmentStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OperationalScope

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OperationalScope.java` @ `48a242b4faec7f67b56fe14a112aea2162b9bd49` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OperationalScopeJpaEntity.java` @ `4e41c19859fee3294339a508f3c5f9e6c0e12c76`.
- Candidate relationship fields (2): `id:Long`, `targetId:String`.
- Custom component types: `OperationalScopeType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationContactPoint

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java` @ `2b3225bbdd485a8987962e0827d4781a665e0e31` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationContactPointJpaEntity.java` @ `c6ab8ebed5109addb3cc9847d973a7fa98540541`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ContactPointType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointType.java`; `ContactPointTargetReference` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointTargetReference.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationDelegation

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java` @ `254f4fa04b6a40b3cc730e8c01838d0b1cc585e5` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java` @ `0e7641e5e5d4af8f72f54553d5669588df0255cd`.
- Candidate relationship fields (4): `id:String`, `delegatorEmployeeId:String`, `delegateEmployeeId:String`, `responsibilityAssignmentId:String`.
- Custom component types: `DelegationStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/DelegationStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationHierarchySnapshot

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java` @ `06c94c9e4a22d99667a885a87fc86375bce10c9d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java` @ `97839e5c16306813fb0f788f47a6abf211225df9`.
- Candidate relationship fields (2): `id:String`, `capturedByEmployeeId:String`.
- Custom component types: `HierarchySnapshotStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/HierarchySnapshotStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationUnit

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java` @ `a16aae695395900e2fdf94d2dfb9333e61d7b74c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java` @ `f5a6ceff2d44ee5c412f43bde2a4ee05ba36a605`.
- Candidate relationship fields (3): `id:String`, `unitTypeId:String`, `parentUnitId:String`.
- Custom component types: `OrganizationUnitStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationUnitType

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java` @ `479c1fb239c84f2b0bbce2b2cbaa4f076c66272d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java` @ `a5316df16a431d9023039366b7a9cbe0afdc78b5`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `OrganizationUnitKind` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitKind.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.Position

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java` @ `308e1640335ca148078e96cff16f853a001cd799` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java` @ `fbd5f0d3a9ad72abe7356a42fc6f531259e342ec`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `PositionLevel` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionLevel.java`; `PositionStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.ReportingLine

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java` @ `e9142856cb5f67799fe1adc7d660a2eb0fae368c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java` @ `04005d00a82726ac789db2d65f1e4028ca0463f1`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ReportingLineType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`; `ReportingSubjectReference` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingSubjectReference.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.ResponsibilityAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java` @ `885f4eb94a7cc187edc47fc31f457cebb9a9d18e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java` @ `eb692ec98f0bc8747302be026d876869a704e844`.
- Candidate relationship fields (3): `id:String`, `assigneeId:String`, `scopeId:Long`.
- Custom component types: `ResponsibilityType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityType.java`; `ResponsibilityAssigneeType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityAssigneeType.java`; `AssignmentStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.Shift

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java` @ `89053e407bce55c2f647f8e7979b4c8ba095d47f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java` @ `25467d09ebaf35d9623e69efffb3521f18d41dea`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ShiftType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftType.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.ShiftAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java` @ `4ecb5a4eb14e70a5b671bed782ca40df0bcf12a2` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java` @ `deae79e8aee97258fc4d69dca55b3d9974775851`.
- Candidate relationship fields (4): `id:String`, `employeeId:String`, `shiftId:String`, `organizationUnitId:String`.
- Custom component types: `ShiftAssignmentStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### party.Party

- Domain: `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java` @ `dc33cbf1a1d0a8264d13eacf0cd3a2e1f5d67d12` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java` @ `83de397fc16582bc6567adbefde1de9a852f1822`.
- Candidate relationship fields (2): `id:String`, `partyTypeId:String`.
- Custom component types: `PartyStatus` → `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyStatus.java`.
- Application-contract corpus: 6 current port files in module `party`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_003__create_party_tables.sql`.

#### party.PartyRoleAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java` @ `0ca1a3a47f45c97fec78997ed6a810fcbfe60600` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java` @ `92f815a2082169178d71cea72569edb73afce884`.
- Candidate relationship fields (3): `id:String`, `partyId:String`, `roleId:String`.
- Custom component types: `PartyRoleAssignmentStatus` → `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`.
- Application-contract corpus: 6 current port files in module `party`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_003__create_party_tables.sql`.

#### planning.Nomination

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java` @ `99ce28cecd9d178ebd4174bf331ba25a9eb1af73` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java` @ `2489a6d3c82c585c2542dda287449de43802ca7e`.
- Candidate relationship fields (12): `id:String`, `revisionId:String`, `scenarioId:String`, `nominationTypeId:String`, `productTypeId:String`, `quantityUnitId:String`, `rateUnitId:String`, `sourceAssetId:String`, `destinationAssetId:String`, `shipperPartyId:String`, `counterpartyId:String`, `contractReferenceId:String`.
- Custom component types: `NominationStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.OperationalPlan

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java` @ `3ed411d3fa31f6e3109c63d32fdf077b63c1ea4f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java` @ `5825cc10f5e79c2aa0b41ab8efa69811eb0d0231`.
- Candidate relationship fields (9): `id:String`, `periodId:String`, `planTypeId:String`, `productTypeId:String`, `topologyScopeId:String`, `responsibleOrganizationUnitId:String`, `currentRevisionId:String`, `approvedRevisionId:String`, `createdByActorId:String`.
- Custom component types: `OperationalPlanStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.PlanRevision

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java` @ `945ebc3896d97eb9123a48f21f86365af72078db` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java` @ `8a1459b799113a99ee710287baa7db3a55eb42a2`.
- Candidate relationship fields (7): `id:String`, `planId:String`, `changeReasonCodeId:String`, `baseRevisionId:String`, `submittedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: `PlanRevisionStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.PlanTarget

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java` @ `1f653e56a356e09334e3fed6bb5fc421a9be2df7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java` @ `13a87e326601990e05844ba4f1c567bbf3977c96`.
- Candidate relationship fields (8): `id:String`, `revisionId:String`, `scenarioId:String`, `nominationId:String`, `targetTypeId:String`, `topologyAssetId:String`, `telemetryPointId:String`, `unitId:String`.
- Custom component types: `PlanTargetStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.PlanningPeriod

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java` @ `8323d0959d638912e620cc2263cdc83928129381` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java` @ `7308a0a4b791636ff8e204aa5af9c66d650fe136`.
- Candidate relationship fields (3): `id:String`, `periodTypeId:String`, `createdByActorId:String`.
- Custom component types: `PlanningPeriodStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### reporting.ReportDefinition

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java` @ `a49e35df1e54133b727eaaad14a04ca5af31e686` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java` @ `f993e34f6e6632b8cbb4b6f1293933871152ba05`.
- Candidate relationship fields (3): `id:String`, `reportCategoryId:String`, `currentTemplateVersionId:String`.
- Custom component types: none.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### reporting.ReportOutputArtifact

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java` @ `b31cb9be178fae822e3ea9d88dddd5f45a023327` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java` @ `b16797af106a882e67e2d7cd1a031d684a9a3b82`.
- Candidate relationship fields (4): `id:String`, `reportRunId:String`, `storageObjectReferenceId:String`, `documentReferenceId:String`.
- Custom component types: `ReportArtifactType` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`; `ReportFormat` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### reporting.ReportRequest

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java` @ `6b87d76de895a0c3f81799bb5caead7723aaef76` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java` @ `5b468d6f32073e27923ce7181e0d8754e017e5b6`.
- Candidate relationship fields (6): `id:String`, `reportDefinitionId:String`, `requestedByActorId:String`, `organizationUnitId:String`, `correlationId:String`, `workflowReferenceId:String`.
- Custom component types: `ReportRequestStatus` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### reporting.ReportRun

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java` @ `fe40b3c4eed61171f9bd2bb42639776e2431f6a4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java` @ `ffb151945f1f099bbbd2aa0afed41284263acee4`.
- Candidate relationship fields (5): `id:String`, `reportRequestId:String`, `reportDefinitionId:String`, `templateVersionId:String`, `correlationId:String`.
- Custom component types: `ReportRunStatus` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`; `ReportRunMode` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### risk.RiskAssessment

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java` @ `38f55d8d5806bf60c5c616225819699ab042cec3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java` @ `359df73749c144ad1529ef4902298f447cb92635`.
- Candidate relationship fields (18): `id:String`, `riskRegisterId:String`, `assessmentTypeId:String`, `methodologyId:String`, `scopeId:String`, `riskScenarioId:String`, `assessedByActorId:String`, `reviewedByActorId:String`, `approvedByActorId:String`, `inherentLikelihoodId:String`, `inherentConsequenceId:String`, `inherentRatingId:String`, `residualLikelihoodId:String`, `residualConsequenceId:String`, `residualRatingId:String`, `confidenceLevelId:String`, `workflowReferenceId:String`, `auditReferenceId:String`.
- Custom component types: `RiskAssessmentStatus` → `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### risk.RiskEvidenceLink

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java` @ `aae26edcd53e6ca3424f914116e97eb947402e8b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java` @ `2b68ae2a3e2733ea901485eb90ca1ff37e59f3db`.
- Candidate relationship fields (3): `id:String`, `riskAssessmentId:String`, `evidenceId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### risk.RiskMatrixCell

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java` @ `068add9a3c599c6b67905daea2abc92b149b9bea` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java` @ `fc1c06b4f727cad793db278def33d4f6216c2f91`.
- Candidate relationship fields (5): `id:String`, `riskMatrixId:String`, `likelihoodLevelId:String`, `consequenceLevelId:String`, `ratingId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### risk.RiskRegister

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java` @ `b2ae8d0bf2d5fbe24b72a73d3c3cf1b9d7345692` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java` @ `9f183aea285be50527785a984c1d7fb60dd1710c`.
- Candidate relationship fields (6): `id:String`, `registerTypeId:String`, `ownerOrganizationUnitId:String`, `scopeId:String`, `reviewFrequencyId:String`, `createdByActorId:String`.
- Custom component types: `RiskRegisterStatus` → `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### simulation.SimulationCandidateChange

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java` @ `93afc7b98b75066b6e1bdda180216be8802d295a` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java` @ `9a8dd011ddee3892b27fcd6c500bd13bfea0faff`.
- Candidate relationship fields (4): `id:String`, `candidateId:String`, `changeTypeId:String`, `targetId:String`.
- Custom component types: none.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationModel

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java` @ `dce60115dea1a8bd17bc88eb9d3373e741b70e02` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java` @ `689bc928fa80bbe5dbd10cb989dcd17a33809c3d`.
- Candidate relationship fields (3): `id:String`, `modelTypeId:String`, `topologyScopeId:String`.
- Custom component types: `SimulationModelStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationOptimizationCandidate

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java` @ `be3b50e6985efcdb9b2a36d5e7a1f3e8c5966761` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationOptimizationCandidateJpaEntity.java` @ `de1c3a03dfca225085d0f47a68c67b9911ea06f0`.
- Candidate relationship fields (3): `id:String`, `runId:String`, `selectedByActorId:String`.
- Custom component types: `SimulationCandidateStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationCandidateStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationRecommendation

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java` @ `1d25c0ad99d79e2925ba06b21d5fd41e53b225c5` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java` @ `d745ff231a0b0d03bfbde20a312aaff5de1367ef`.
- Candidate relationship fields (6): `id:String`, `runId:String`, `candidateId:String`, `recommendationTypeId:String`, `confidenceLevelId:String`, `publishedByActorId:String`.
- Custom component types: `SimulationRecommendationStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationRun

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java` @ `36241f71e8b2d87446bf94e4309d09bb407b5cf1` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java` @ `94ae34ad23a86a502a0f35f3856fb577cf063f6c`.
- Candidate relationship fields (8): `id:String`, `scenarioId:String`, `modelVersionId:String`, `inputSnapshotId:String`, `runTypeId:String`, `requestedByActorId:String`, `solverProfileId:String`, `correlationId:String`.
- Custom component types: `SimulationRunStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationScenario

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java` @ `37d1ec54ffacf2307dcce317eeff593d8481f481` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java` @ `ecfd2be18aa9e5de65300466fc75a2d881dbb76d`.
- Candidate relationship fields (8): `id:String`, `scenarioTypeId:String`, `modelId:String`, `modelVersionId:String`, `topologySnapshotId:String`, `planningReferenceId:String`, `monitoringContextId:String`, `createdByActorId:String`.
- Custom component types: `SimulationScenarioStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### telemetry.TelemetryPoint

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java` @ `d400e3d0b5367524b48c4989499815c1085e78ca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java` @ `6644f6abe45b298954e2e4c40156428ef4750f55`.
- Candidate relationship fields (6): `id:String`, `deviceId:String`, `pointTypeId:String`, `signalTypeId:String`, `unitId:String`, `defaultAggregationMethodId:String`.
- Custom component types: `TelemetryLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### telemetry.TelemetryReading

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java` @ `9c5d8fd95608a7a530ebaa9113e1773dcfb227de` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java` @ `a935d92413bb2cb0382629894d246d9ad32b5e6e`.
- Candidate relationship fields (6): `id:String`, `pointId:String`, `qualityCodeId:String`, `ingestionBatchId:String`, `correlationId:String`, `externalTagMappingId:String`.
- Custom component types: `ReadingState` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### telemetry.TelemetrySource

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java` @ `bbcd387202fd55934d16b71bd37423dfb68dd74c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java` @ `9bb82fdd06808778dcc6d797878e4ef86f15b162`.
- Candidate relationship fields (3): `id:String`, `sourceTypeId:String`, `protocolId:String`.
- Custom component types: `TelemetryLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### telemetry.TrustedTelemetryReading

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java` @ `e5c6e50f904d1669ab18e0f1c88a3228a5ca6b1e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java` @ `de6fb059056dbb5fd2782fb4625b1367a446c48a`.
- Candidate relationship fields (9): `id:String`, `readingId:String`, `pointId:String`, `unitId:String`, `qualityCodeId:String`, `qualityAssessmentId:String`, `topologyAssetId:String`, `topologySnapshotId:String`, `ingestionBatchId:String`.
- Custom component types: `TrustLevel` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### topology.Equipment

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java` @ `7e220479a4213de3e3661ffafc436decda11ee4f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java` @ `d49ee792cf7798f6457071411f17222a66a9fa3f`.
- Candidate relationship fields (6): `id:String`, `facilityId:String`, `nodeId:String`, `pipelineSegmentId:String`, `equipmentTypeId:String`, `manufacturerPartyId:String`.
- Custom component types: `EquipmentKind` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentKind.java`; `EquipmentStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.Facility

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java` @ `794e17a24b1f1c7dd1072c8c17f7392b15817f42` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java` @ `11bfe6b0486dbeab86c7381469b05f33d3428df4`.
- Candidate relationship fields (3): `id:String`, `facilityTypeId:String`, `ownerPartyId:String`.
- Custom component types: `FacilityKind` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`; `FacilityStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.Pipeline

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java` @ `c06d25c7ce730482591880d7ebd28b384599843d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java` @ `8f7f37a2ecd11c666a032cf5a2f4528391cb8158`.
- Candidate relationship fields (2): `id:String`, `pipelineSystemId:String`.
- Custom component types: `PipelineType` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`; `TopologyStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.PipelineSystem

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java` @ `c461146a5836af0885e20cd71290f08ad9959376` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java` @ `fe2cc949360225ad8ff372a79e018d25e61bbd06`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `PipelineSystemType` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`; `TopologyStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.TopologyConnection

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java` @ `d4b5fd62f38624207c219f6d31c35f812651cef5` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java` @ `8419cd1024473db4d412737de0e38ac2ce994be4`.
- Candidate relationship fields (4): `id:String`, `fromNodeId:String`, `toNodeId:String`, `pipelineSegmentId:String`.
- Custom component types: `ConnectionType` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`; `FlowDirection` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`; `TopologyStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### workflow.WorkflowAction

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java` @ `392bbe7bcf396959b50785c68d94bc833493aa8c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java` @ `704015cf8374c3332b32525364e127bae5a08dec`.
- Candidate relationship fields (7): `id:String`, `instanceId:String`, `taskId:String`, `reasonId:String`, `actorId:String`, `organizationUnitId:String`, `correlationId:String`.
- Custom component types: `WorkflowActionType` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`; `WorkflowDecision` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowDefinition

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java` @ `b52929723c976d53100711969eb7bf1d26f4cb41` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java` @ `a09108eba1e916d73c4be59b1185d33e0117453a`.
- Candidate relationship fields (2): `id:String`, `typeId:String`.
- Custom component types: `WorkflowDefinitionStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowInstance

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java` @ `f26f949eb9859ba804257b8fd2653a37f479811d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java` @ `893178f7cb42814b01b2db30c7075d5846cae48e`.
- Candidate relationship fields (8): `id:String`, `definitionId:String`, `workflowPurposeId:String`, `targetTypeId:String`, `targetId:String`, `currentStepId:String`, `startedByActorId:String`, `correlationId:String`.
- Custom component types: `WorkflowInstanceStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowStateHistory

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java` @ `0811ba1b2d39b7ed690529bb4ef2d1af3968694f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java` @ `48e810a73bb2427bb3dc0ff3fb6b2010e730b717`.
- Candidate relationship fields (9): `id:String`, `instanceId:String`, `taskId:String`, `fromStepId:String`, `toStepId:String`, `actorId:String`, `actionId:String`, `reasonId:String`, `correlationId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowStep

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java` @ `7f722c0440627694507a8631733833606b70c6d3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java` @ `d15b68b818d3557b79263c5101398c6f617a9c07`.
- Candidate relationship fields (5): `id:String`, `definitionId:String`, `stepTypeId:String`, `defaultAssignmentRuleId:String`, `slaPolicyId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowStepAssignmentRule

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java` @ `6fe60cfd174defc97882393a23a3c80c590491b6` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java` @ `88f95b31c6dcc1a302f0745fb74eb97f8f153ab1`.
- Candidate relationship fields (6): `id:String`, `definitionId:String`, `stepId:String`, `assignmentModeId:String`, `actorId:String`, `organizationUnitId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowTask

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java` @ `750267c4bd9cfbd8c7799abe6f6b551b17599964` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java` @ `434886986e04383c82734ec54a382bfb4bb50b10`.
- Candidate relationship fields (9): `id:String`, `instanceId:String`, `stepId:String`, `assignedActorId:String`, `assignedOrganizationUnitId:String`, `priorityId:String`, `claimedByActorId:String`, `completedByActorId:String`, `assignmentModeId:String`.
- Custom component types: `WorkflowTaskStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`; `WorkflowSlaStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowSlaStatus.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowTransition

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java` @ `9b16c1afaccb8609d5fdcf53f3965469342898c3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java` @ `4ef8685872022dbdd289598201c662acbb791b56`.
- Candidate relationship fields (4): `id:String`, `definitionId:String`, `fromStepId:String`, `toStepId:String`.
- Custom component types: `WorkflowDecision` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

### 10.5 HMS-002 outcome

- All 123 catalogue models have a current live path/blob evidence record.
- Candidate ID/reference fields and direct model-typed fields are inventoried without assigning dependency meaning.
- Matching JPA-entity presence is recorded without inferring an ORM relationship from scalar IDs.
- Application-port, Flyway, and domain support-type evidence corpora are identified for every module.
- Source drift relative to the HMC pinned model blobs is explicitly surfaced for HMS-003.
- No topological order, dependency depth, downstream dependent count, strongly connected component, or interactive review code has been computed.
- No production Java, JPA, Flyway, application contract, enum/value type, or database data was modified.

## 11. HMS-003 — Dependency classification register

**Task:** HMS-003 — classify target model dependencies  
**Classification basis:** HMS-002 evidence register plus deterministic subject-model resolution rules.  
**Candidates classified:** 627 / 627  
**Subject-model graph edges admitted:** 165  
**Unresolved candidates retained:** 134  
**Ordering/cycle analysis:** not performed in this task.

### 11.1 Classification rules

- `Self identifier` is not a graph edge.
- `Domain reference` is a same-module reference to one of the 123 subject models.
- `Cross-module reference` becomes a graph edge only when it resolves to one specific subject model. Actor references and polymorphic targets remain non-edge cross-module references.
- `Snapshot/reference-only` is explicitly excluded from graph ownership/dependency ordering.
- `Value/catalog dependency` is a controlled taxonomy/value reference, not one of the 123 subject-model edges.
- `Unresolved` means the target is absent from the subject set, ambiguous, or evidence is insufficient. HMS-004 must inspect these cases before graph validation.
- HMS-003 does not claim persistence-FK status unless HMS-004 confirms it from current schema evidence.

### 11.2 Classification summary

| Classification | Candidate fields |
|---|---:|
| Self identifier | 122 |
| Domain reference | 112 |
| Cross-module reference | 149 |
| Snapshot/reference-only | 36 |
| Value/catalog dependency | 74 |
| Unresolved | 134 |
| **Total** | **627** |

Confirmed subject-model graph edges at this stage: **165**. These are semantic candidate edges for HMS-004 validation, not yet the final dependency graph.

### 11.3 Per-model classification

#### alarm.Alarm

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| alarmTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| severityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| priorityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| sourceReferenceId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| monitoringAlertCandidateId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| monitoringEvaluationId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| telemetryReadingId | String | Cross-module reference | telemetry.TelemetryReading | Yes | Unambiguous reference to a subject model in another bounded context. |
| planningTargetId | String | Cross-module reference | planning.PlanTarget | Yes | Unambiguous reference to a subject model in another bounded context. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| acknowledgedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| owningOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| incidentId | String | Cross-module reference | incident.Incident | Yes | Unambiguous reference to a subject model in another bounded context. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### alarm.AlarmAcknowledgement

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| alarmId | String | Domain reference | alarm.Alarm | Yes | Unambiguous same-module subject-model reference. |
| acknowledgedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| organizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### alarm.AlarmClosure

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| alarmId | String | Domain reference | alarm.Alarm | Yes | Unambiguous same-module subject-model reference. |
| closureReasonId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| closedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| reviewWorkflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### alarm.AlarmShelving

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| alarmId | String | Domain reference | alarm.Alarm | Yes | Unambiguous same-module subject-model reference. |
| shelvingReasonId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| shelvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| unshelvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### analytics.AnalyticsDataset

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| subjectAreaId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### analytics.AnalyticsDatasetVersion

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| datasetId | String | Domain reference | analytics.AnalyticsDataset | Yes | Unambiguous same-module subject-model reference. |
| publishedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### analytics.AnalyticsInsight

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| subjectAreaId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| scopeId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| severityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| sourceProjectionSnapshotId | String | Snapshot/reference-only | — | No | Snapshot/reference identity; not treated as ownership dependency in HMS-003. |
| sourceTrendAnalysisId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| sourceModelRunId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |

#### analytics.AnalyticsProjectionRun

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| projectionDefinitionId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### analytics.DigitalTwinReadinessAssessment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| scopeId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| topologySnapshotId | String | Snapshot/reference-only | — | No | Snapshot/reference identity; not treated as ownership dependency in HMS-003. |

#### analytics.MetricEvaluationRun

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| metricDefinitionVersionId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| scopeId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### analytics.MetricValue

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| metricEvaluationRunId | String | Domain reference | analytics.MetricEvaluationRun | Yes | Unambiguous same-module subject-model reference. |
| metricDefinitionId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| metricDefinitionVersionId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| scopeId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| unitId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### assets.AssetConditionRecord

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| maintainableAssetId | String | Domain reference | assets.MaintainableAsset | Yes | Unambiguous same-module subject-model reference. |
| conditionTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| sourceReferenceId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| observedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### assets.MaintainableAsset

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| assetTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| parentAssetId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| criticalityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| ownerOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| manufacturerPartyId | String | Cross-module reference | party.Party | Yes | Unambiguous reference to a subject model in another bounded context. |
| modelId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| serialIdentityId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### assets.MaintenanceWorkOrder

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| maintainableAssetId | String | Domain reference | assets.MaintainableAsset | Yes | Unambiguous same-module subject-model reference. |
| maintenancePlanId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| sourceRecommendationId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| workOrderTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| priorityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| assignedOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| assignedActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### audit.AuditAccessRecord

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| actorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| auditEventId | String | Domain reference | audit.AuditEvent | Yes | Unambiguous same-module subject-model reference. |
| exportRequestId | String | Domain reference | audit.AuditExportRequest | Yes | Unambiguous same-module subject-model reference. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### audit.AuditBeforeAfterValue

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| auditEventId | String | Domain reference | audit.AuditEvent | Yes | Unambiguous same-module subject-model reference. |
| maskReasonId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### audit.AuditEvent

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| eventTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| eventCategoryId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| severityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| sourceEventId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| actorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| organizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| reasonId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| workflowTaskId | String | Cross-module reference | workflow.WorkflowTask | Yes | Unambiguous reference to a subject model in another bounded context. |
| workflowActionId | String | Cross-module reference | workflow.WorkflowAction | Yes | Unambiguous reference to a subject model in another bounded context. |
| requestId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| causationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| retentionPolicyId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### audit.AuditExportRequest

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| requestedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| purposeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| resultDocumentReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### configuration.ConfigurationDefinition

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| namespaceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### configuration.ConfigurationValue

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| definitionId | String | Domain reference | configuration.ConfigurationDefinition | Yes | Unambiguous same-module subject-model reference. |
| definitionVersionId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### configuration.FeatureFlag

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### custody.CustodyDiscrepancy

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| reconciliationId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| discrepancyTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| quantityUnitId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| assignedActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### custody.CustodyMeasurementPeriod

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| agreementId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| transferPointId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| lockedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| approvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### custody.CustodyTransferTicket

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| measurementPeriodId | String | Domain reference | custody.CustodyMeasurementPeriod | Yes | Unambiguous same-module subject-model reference. |
| agreementId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| transferPointId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| batchId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| quantityCalculationId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| issuedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| approvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| auditReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### documents.Document

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| documentTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| documentCategoryId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| classificationId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| currentVersionId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| ownerTargetId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### documents.DocumentStorageObject

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| storageProviderId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |

#### documents.DocumentTargetLink

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| documentId | String | Domain reference | documents.Document | Yes | Unambiguous same-module subject-model reference. |
| documentVersionId | String | Domain reference | documents.DocumentVersion | Yes | Unambiguous same-module subject-model reference. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| linkRoleId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| linkedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### documents.DocumentVersion

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| documentId | String | Domain reference | documents.Document | Yes | Unambiguous same-module subject-model reference. |
| storageObjectId | String | Domain reference | documents.DocumentStorageObject | Yes | Unambiguous same-module subject-model reference. |
| uploadedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| approvedByWorkflowInstanceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| supersededByVersionId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### hse.HseCase

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| caseTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| severityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| priorityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| incidentReferenceId | String | Cross-module reference | incident.Incident | Yes | Unambiguous reference to a subject model in another bounded context. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| reportedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| responsibleOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| auditReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### hse.HseClosure

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| hseCaseId | String | Domain reference | hse.HseCase | Yes | Unambiguous same-module subject-model reference. |
| closedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |

#### hse.HseCorrectivePreventiveAction

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| hseCaseId | String | Domain reference | hse.HseCase | Yes | Unambiguous same-module subject-model reference. |
| actionTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| ownerActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| ownerOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| verifiedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| linkedWorkOrderId | String | Cross-module reference | assets.MaintenanceWorkOrder | Yes | Unambiguous reference to a subject model in another bounded context. |
| workflowTaskId | String | Cross-module reference | workflow.WorkflowTask | Yes | Unambiguous reference to a subject model in another bounded context. |

#### hse.PermitToWork

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| permitTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| requestedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| approvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |

#### identity.AuthenticationEvent

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| userId | String | Domain reference | identity.User | Yes | Unambiguous same-module subject-model reference. |
| identityProviderId | String | Domain reference | identity.IdentityProvider | Yes | Unambiguous same-module subject-model reference. |
| externalIdentityId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### identity.AuthorizationDecision

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| userId | String | Domain reference | identity.User | Yes | Unambiguous same-module subject-model reference. |
| resourceReferenceId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| matchedGrantIds | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| matchedPolicyRuleIds | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| requestId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### identity.AuthorizationDelegationGrant

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| delegatorUserId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| delegateUserId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| permissionId | String | Domain reference | identity.Permission | Yes | Unambiguous same-module subject-model reference. |
| roleId | String | Domain reference | identity.Role | Yes | Unambiguous same-module subject-model reference. |
| approvedByWorkflowId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### identity.ExternalRoleMapping

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| identityProviderId | String | Domain reference | identity.IdentityProvider | Yes | Unambiguous same-module subject-model reference. |
| roleId | String | Domain reference | identity.Role | Yes | Unambiguous same-module subject-model reference. |

#### identity.GroupRoleGrant

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| groupId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| roleId | String | Domain reference | identity.Role | Yes | Unambiguous same-module subject-model reference. |
| approvedByWorkflowId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### identity.HidraPrincipal

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| userId | String | Domain reference | identity.User | Yes | Unambiguous same-module subject-model reference. |
| identityProviderId | String | Domain reference | identity.IdentityProvider | Yes | Unambiguous same-module subject-model reference. |

#### identity.IdentityProvider

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### identity.LocalCredential

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| userId | String | Domain reference | identity.User | Yes | Unambiguous same-module subject-model reference. |

#### identity.LoginSession

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| userId | String | Domain reference | identity.User | Yes | Unambiguous same-module subject-model reference. |
| identityProviderId | String | Domain reference | identity.IdentityProvider | Yes | Unambiguous same-module subject-model reference. |
| externalIdentityId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### identity.Permission

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### identity.Role

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### identity.RolePermissionGrant

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| roleId | String | Domain reference | identity.Role | Yes | Unambiguous same-module subject-model reference. |
| permissionId | String | Domain reference | identity.Permission | Yes | Unambiguous same-module subject-model reference. |

#### identity.User

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| employeeReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### identity.UserPermissionGrant

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| userId | String | Domain reference | identity.User | Yes | Unambiguous same-module subject-model reference. |
| permissionId | String | Domain reference | identity.Permission | Yes | Unambiguous same-module subject-model reference. |
| approvedByWorkflowId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### identity.UserRoleGrant

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| userId | String | Domain reference | identity.User | Yes | Unambiguous same-module subject-model reference. |
| roleId | String | Domain reference | identity.Role | Yes | Unambiguous same-module subject-model reference. |
| approvedByWorkflowId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### incident.Incident

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| classificationId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| severityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| priorityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| sourceReferenceId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| responsibleOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| responsibleActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### incident.IncidentClosure

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| incidentId | String | Domain reference | incident.Incident | Yes | Unambiguous same-module subject-model reference. |
| closedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |

#### incident.IncidentRelatedIncident

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| incidentId | String | Domain reference | incident.Incident | Yes | Unambiguous same-module subject-model reference. |
| relatedIncidentId | String | Domain reference | incident.Incident | Yes | Unambiguous same-module subject-model reference. |
| relationshipTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### incident.IncidentResponseAction

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| incidentId | String | Domain reference | incident.Incident | Yes | Unambiguous same-module subject-model reference. |
| actionTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| targetReferenceId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| performedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| organizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |

#### integration.ExternalSystem

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| systemTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| ownerOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |

#### integration.IntegrationDeadLetterRecord

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| externalSystemId | String | Domain reference | integration.ExternalSystem | Yes | Unambiguous same-module subject-model reference. |
| jobRunId | String | Domain reference | integration.IntegrationJobRun | Yes | Unambiguous same-module subject-model reference. |
| exchangeMessageId | String | Domain reference | integration.IntegrationExchangeMessage | Yes | Unambiguous same-module subject-model reference. |
| inboundRecordId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| outboundRecordId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| resolvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### integration.IntegrationExchangeMessage

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| jobRunId | String | Domain reference | integration.IntegrationJobRun | Yes | Unambiguous same-module subject-model reference. |
| externalSystemId | String | Domain reference | integration.ExternalSystem | Yes | Unambiguous same-module subject-model reference. |
| endpointId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| messageTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| externalMessageId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| payloadFormatId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### integration.IntegrationJobRun

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| jobDefinitionId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| triggeredByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### integrity.IntegrityAssessment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| programId | String | Domain reference | integrity.IntegrityProgram | Yes | Unambiguous same-module subject-model reference. |
| assessmentTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| methodologyId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| assessedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| reviewedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| approvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| auditReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### integrity.IntegrityCase

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| caseTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| severityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| primaryDefectId | String | Domain reference | integrity.PipelineDefect | Yes | Unambiguous same-module subject-model reference. |
| sourceIncidentId | String | Cross-module reference | incident.Incident | Yes | Unambiguous reference to a subject model in another bounded context. |
| sourceHseCaseId | String | Cross-module reference | hse.HseCase | Yes | Unambiguous reference to a subject model in another bounded context. |
| responsibleOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |
| openedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### integrity.IntegrityProgram

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| programTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| ownerOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### integrity.PipelineDefect

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| defectTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| sourceFindingId | String | Unresolved | — | No | Finding target is not one of the 123 subject models. |

#### leakdetection.LeakCandidate

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| runId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| profileId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### leakdetection.LeakDetectionCase

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| primaryCandidateId | String | Domain reference | leakdetection.LeakCandidate | Yes | Primary leak candidate reference. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| owningOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| openedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| closedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| closureReasonId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### leakdetection.LeakEscalationReference

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| caseId | String | Domain reference | leakdetection.LeakDetectionCase | Yes | Unambiguous same-module subject-model reference. |
| candidateId | String | Domain reference | leakdetection.LeakCandidate | Yes | Unambiguous same-module subject-model reference. |
| targetReferenceId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| escalatedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### monitoring.MonitoringRule

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| evaluationFrequencyId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| telemetryPointId | String | Cross-module reference | telemetry.TelemetryPoint | Yes | Unambiguous reference to a subject model in another bounded context. |
| planningTargetTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### monitoring.PlanActualDeviation

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| evaluationId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| planTargetId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| expectedFlowStateId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| trustedTelemetryReadingId | String | Cross-module reference | telemetry.TrustedTelemetryReading | Yes | Unambiguous reference to a subject model in another bounded context. |
| telemetryPointId | String | Cross-module reference | telemetry.TelemetryPoint | Yes | Unambiguous reference to a subject model in another bounded context. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| unitId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### notification.NotificationDeliveryAttempt

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| messageId | String | Domain reference | notification.NotificationMessage | Yes | Unambiguous same-module subject-model reference. |
| channelId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| providerMessageId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### notification.NotificationMessage

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| requestId | String | Domain reference | notification.NotificationRequest | Yes | Unambiguous same-module subject-model reference. |
| recipientId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| channelId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| templateId | String | Domain reference | notification.NotificationTemplate | Yes | Unambiguous same-module subject-model reference. |
| templateVersionId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| priorityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### notification.NotificationRequest

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| sourceEventId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| categoryId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| priorityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| policyId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| templateId | String | Domain reference | notification.NotificationTemplate | Yes | Unambiguous same-module subject-model reference. |
| templateVersionId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| requestedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| requestId | String | Domain reference | notification.NotificationRequest | Yes | Unambiguous same-module subject-model reference. |

#### notification.NotificationTemplate

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| templateTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| categoryId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| defaultChannelId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### organization.AdministrativeDistrict

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| stateId | String | Domain reference | organization.AdministrativeState | Yes | Unambiguous same-module subject-model reference. |

#### organization.AdministrativeLocality

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| districtId | String | Domain reference | organization.AdministrativeDistrict | Yes | Unambiguous same-module subject-model reference. |

#### organization.AdministrativeState

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### organization.Employee

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| birthLocalityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### organization.EmployeeAddress

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| employeeId | String | Domain reference | organization.Employee | Yes | Unambiguous same-module subject-model reference. |
| localityId | String | Domain reference | organization.AdministrativeLocality | Yes | Unambiguous same-module subject-model reference. |

#### organization.EmployeeAssignment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| employeeId | String | Domain reference | organization.Employee | Yes | Unambiguous same-module subject-model reference. |
| organizationUnitId | String | Domain reference | organization.OrganizationUnit | Yes | Unambiguous same-module subject-model reference. |
| positionId | String | Domain reference | organization.Position | Yes | Unambiguous same-module subject-model reference. |

#### organization.OperationalScope

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | Long | Self identifier | — | No | Primary identity of the current model. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |

#### organization.OrganizationContactPoint

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### organization.OrganizationDelegation

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| delegatorEmployeeId | String | Domain reference | organization.Employee | Yes | Unambiguous same-module subject-model reference. |
| delegateEmployeeId | String | Domain reference | organization.Employee | Yes | Unambiguous same-module subject-model reference. |
| responsibilityAssignmentId | String | Domain reference | organization.ResponsibilityAssignment | Yes | Unambiguous same-module subject-model reference. |

#### organization.OrganizationHierarchySnapshot

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| capturedByEmployeeId | String | Domain reference | organization.Employee | Yes | Unambiguous same-module subject-model reference. |

#### organization.OrganizationUnit

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| unitTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| parentUnitId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### organization.OrganizationUnitType

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### organization.Position

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### organization.ReportingLine

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### organization.ResponsibilityAssignment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| assigneeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| scopeId | Long | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |

#### organization.Shift

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### organization.ShiftAssignment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| employeeId | String | Domain reference | organization.Employee | Yes | Unambiguous same-module subject-model reference. |
| shiftId | String | Domain reference | organization.Shift | Yes | Unambiguous same-module subject-model reference. |
| organizationUnitId | String | Domain reference | organization.OrganizationUnit | Yes | Unambiguous same-module subject-model reference. |

#### party.Party

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| partyTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### party.PartyRoleAssignment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| partyId | String | Domain reference | party.Party | Yes | Unambiguous same-module subject-model reference. |
| roleId | String | Cross-module reference | identity.Role | Yes | Unambiguous reference to a subject model in another bounded context. |

#### planning.Nomination

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| revisionId | String | Domain reference | planning.PlanRevision | Yes | Unambiguous same-module subject-model reference. |
| scenarioId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| nominationTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| productTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| quantityUnitId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| rateUnitId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| sourceAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| destinationAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| shipperPartyId | String | Cross-module reference | party.Party | Yes | Unambiguous reference to a subject model in another bounded context. |
| counterpartyId | String | Cross-module reference | party.Party | Yes | Unambiguous reference to a subject model in another bounded context. |
| contractReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### planning.OperationalPlan

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| periodId | String | Domain reference | planning.PlanningPeriod | Yes | Unambiguous same-module subject-model reference. |
| planTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| productTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| topologyScopeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| responsibleOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| currentRevisionId | String | Domain reference | planning.PlanRevision | Yes | Planning revision reference. |
| approvedRevisionId | String | Domain reference | planning.PlanRevision | Yes | Planning revision reference. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### planning.PlanRevision

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| planId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| changeReasonCodeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| baseRevisionId | String | Domain reference | planning.PlanRevision | Yes | Planning revision reference. |
| submittedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| approvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| workflowInstanceId | String | Cross-module reference | workflow.WorkflowInstance | Yes | Unambiguous reference to a subject model in another bounded context. |

#### planning.PlanTarget

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| revisionId | String | Domain reference | planning.PlanRevision | Yes | Unambiguous same-module subject-model reference. |
| scenarioId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| nominationId | String | Domain reference | planning.Nomination | Yes | Unambiguous same-module subject-model reference. |
| targetTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| telemetryPointId | String | Cross-module reference | telemetry.TelemetryPoint | Yes | Unambiguous reference to a subject model in another bounded context. |
| unitId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### planning.PlanningPeriod

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| periodTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### reporting.ReportDefinition

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| reportCategoryId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| currentTemplateVersionId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### reporting.ReportOutputArtifact

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| reportRunId | String | Domain reference | reporting.ReportRun | Yes | Unambiguous same-module subject-model reference. |
| storageObjectReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| documentReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### reporting.ReportRequest

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| reportDefinitionId | String | Domain reference | reporting.ReportDefinition | Yes | Unambiguous same-module subject-model reference. |
| requestedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| organizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| workflowReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### reporting.ReportRun

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| reportRequestId | String | Domain reference | reporting.ReportRequest | Yes | Unambiguous same-module subject-model reference. |
| reportDefinitionId | String | Domain reference | reporting.ReportDefinition | Yes | Unambiguous same-module subject-model reference. |
| templateVersionId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### risk.RiskAssessment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| riskRegisterId | String | Domain reference | risk.RiskRegister | Yes | Unambiguous same-module subject-model reference. |
| assessmentTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| methodologyId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| scopeId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| riskScenarioId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| assessedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| reviewedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| approvedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| inherentLikelihoodId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| inherentConsequenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| inherentRatingId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| residualLikelihoodId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| residualConsequenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| residualRatingId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| confidenceLevelId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| workflowReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| auditReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### risk.RiskEvidenceLink

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| riskAssessmentId | String | Domain reference | risk.RiskAssessment | Yes | Unambiguous same-module subject-model reference. |
| evidenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### risk.RiskMatrixCell

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| riskMatrixId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| likelihoodLevelId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| consequenceLevelId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| ratingId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### risk.RiskRegister

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| registerTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| ownerOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| scopeId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| reviewFrequencyId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### simulation.SimulationCandidateChange

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| candidateId | String | Domain reference | simulation.SimulationOptimizationCandidate | Yes | Unambiguous same-module subject-model reference. |
| changeTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |

#### simulation.SimulationModel

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| modelTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| topologyScopeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### simulation.SimulationOptimizationCandidate

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| runId | String | Domain reference | simulation.SimulationRun | Yes | Unambiguous same-module subject-model reference. |
| selectedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### simulation.SimulationRecommendation

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| runId | String | Domain reference | simulation.SimulationRun | Yes | Unambiguous same-module subject-model reference. |
| candidateId | String | Domain reference | simulation.SimulationOptimizationCandidate | Yes | Unambiguous same-module subject-model reference. |
| recommendationTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| confidenceLevelId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| publishedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### simulation.SimulationRun

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| scenarioId | String | Domain reference | simulation.SimulationScenario | Yes | Unambiguous same-module subject-model reference. |
| modelVersionId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| inputSnapshotId | String | Snapshot/reference-only | — | No | Snapshot/reference identity; not treated as ownership dependency in HMS-003. |
| runTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| requestedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| solverProfileId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### simulation.SimulationScenario

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| scenarioTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| modelId | String | Domain reference | simulation.SimulationModel | Yes | Unambiguous same-module subject-model reference. |
| modelVersionId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| topologySnapshotId | String | Snapshot/reference-only | — | No | Snapshot/reference identity; not treated as ownership dependency in HMS-003. |
| planningReferenceId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| monitoringContextId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| createdByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### telemetry.TelemetryPoint

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| deviceId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| pointTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| signalTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| unitId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| defaultAggregationMethodId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### telemetry.TelemetryReading

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| pointId | String | Domain reference | telemetry.TelemetryPoint | Yes | Unambiguous same-module subject-model reference. |
| qualityCodeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| ingestionBatchId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |
| externalTagMappingId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |

#### telemetry.TelemetrySource

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| sourceTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| protocolId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### telemetry.TrustedTelemetryReading

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| readingId | String | Domain reference | telemetry.TelemetryReading | Yes | Unambiguous same-module subject-model reference. |
| pointId | String | Domain reference | telemetry.TelemetryPoint | Yes | Unambiguous same-module subject-model reference. |
| unitId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| qualityCodeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| qualityAssessmentId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| topologySnapshotId | String | Snapshot/reference-only | — | No | Snapshot/reference identity; not treated as ownership dependency in HMS-003. |
| ingestionBatchId | String | Unresolved | — | No | Reference target is outside or absent from the 123 subject-model set; preserve for HMS-004 review. |

#### topology.Equipment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| facilityId | String | Domain reference | topology.Facility | Yes | Unambiguous same-module subject-model reference. |
| nodeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| pipelineSegmentId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| equipmentTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| manufacturerPartyId | String | Cross-module reference | party.Party | Yes | Unambiguous reference to a subject model in another bounded context. |

#### topology.Facility

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| facilityTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| ownerPartyId | String | Cross-module reference | party.Party | Yes | Unambiguous reference to a subject model in another bounded context. |

#### topology.Pipeline

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| pipelineSystemId | String | Domain reference | topology.PipelineSystem | Yes | Unambiguous same-module subject-model reference. |

#### topology.PipelineSystem

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |

#### topology.TopologyConnection

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| fromNodeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| toNodeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| pipelineSegmentId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### workflow.WorkflowAction

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| instanceId | String | Domain reference | workflow.WorkflowInstance | Yes | Unambiguous same-module subject-model reference. |
| taskId | String | Domain reference | workflow.WorkflowTask | Yes | Unambiguous same-module subject-model reference. |
| reasonId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| actorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| organizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### workflow.WorkflowDefinition

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| typeId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |

#### workflow.WorkflowInstance

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| definitionId | String | Domain reference | workflow.WorkflowDefinition | Yes | Unambiguous same-module subject-model reference. |
| workflowPurposeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| targetTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| targetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion type/module metadata; no single subject-model edge. |
| currentStepId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| startedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### workflow.WorkflowStateHistory

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| instanceId | String | Domain reference | workflow.WorkflowInstance | Yes | Unambiguous same-module subject-model reference. |
| taskId | String | Domain reference | workflow.WorkflowTask | Yes | Unambiguous same-module subject-model reference. |
| fromStepId | String | Domain reference | workflow.WorkflowStep | Yes | Unambiguous same-module subject-model reference. |
| toStepId | String | Domain reference | workflow.WorkflowStep | Yes | Unambiguous same-module subject-model reference. |
| actorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| actionId | String | Domain reference | workflow.WorkflowAction | Yes | Unambiguous same-module subject-model reference. |
| reasonId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### workflow.WorkflowStep

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| definitionId | String | Domain reference | workflow.WorkflowDefinition | Yes | Unambiguous same-module subject-model reference. |
| stepTypeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| defaultAssignmentRuleId | String | Domain reference | workflow.WorkflowStepAssignmentRule | Yes | Unambiguous same-module subject-model reference. |
| slaPolicyId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### workflow.WorkflowStepAssignmentRule

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| definitionId | String | Domain reference | workflow.WorkflowDefinition | Yes | Unambiguous same-module subject-model reference. |
| stepId | String | Domain reference | workflow.WorkflowStep | Yes | Unambiguous same-module subject-model reference. |
| assignmentModeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |
| actorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| organizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |

#### workflow.WorkflowTask

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| instanceId | String | Domain reference | workflow.WorkflowInstance | Yes | Unambiguous same-module subject-model reference. |
| stepId | String | Domain reference | workflow.WorkflowStep | Yes | Unambiguous same-module subject-model reference. |
| assignedActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| assignedOrganizationUnitId | String | Cross-module reference | organization.OrganizationUnit | Yes | Unambiguous reference to a subject model in another bounded context. |
| priorityId | String | Unresolved | — | No | No defensible single subject-model target from HMS-002 evidence. |
| claimedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| completedByActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |
| assignmentModeId | String | Value/catalog dependency | CATALOG_OR_VALUE | No | Controlled classification/value reference; not a subject-model edge. |

#### workflow.WorkflowTransition

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| definitionId | String | Domain reference | workflow.WorkflowDefinition | Yes | Unambiguous same-module subject-model reference. |
| fromStepId | String | Domain reference | workflow.WorkflowStep | Yes | Unambiguous same-module subject-model reference. |
| toStepId | String | Domain reference | workflow.WorkflowStep | Yes | Unambiguous same-module subject-model reference. |

### 11.4 HMS-003 outcome

- Every HMS-002 candidate field has one explicit classification.
- Only unambiguous references to one of the 123 subject models are admitted as provisional graph edges.
- Catalog/value references, actor identities, technical references, snapshots, and polymorphic targets are kept out of dependency ordering.
- Ambiguous/absent-target references remain `Unresolved`; none are silently guessed.
- No dependency depth, topological level, strongly connected component, downstream dependent count, or HMSR review code has been calculated.
- No production code, JPA mapping, Flyway migration, application contract, enum/value type, or database data was modified.

## 12. HMS-004 — Validated dependency graph and cycle evidence

**Task:** HMS-004 — validate dependency graph and cycles  
**Validation baseline:** HMS-003 classification register on `e10963a599e4fd0da8fbe6b17beaa53213ef07f9`  
**Subject nodes:** 123  
**HMS-003 provisional subject edges:** 165  
**HMS-004 corrections:** 1 removed, 17 added  
**Validated subject edges:** 181  
**Same-module edges:** 122  
**Cross-module stable/domain edges:** 59  
**Strongly connected components:** 120 total; 5 cyclic components  
**Remaining HMS-003 unresolved candidates not admitted to the 123-node graph:** 120

### 12.1 Validation sources and boundary rules

HMS-004 reconciles the provisional graph against live repository evidence including:

- `docs/architecture/scalar-reference-integrity-inventory.md`;
- `docs/architecture/domain-persistence-mirror-classification.md`;
- current module data definitions and roadmaps;
- current JPA entity mappings;
- current application/integration contracts;
- current invariant/migration evidence.

The repository architecture explicitly distinguishes 123 retained domain-review subjects from **343 READ_PERSISTENCE_MODEL mirrors**. Those 343 persistence/read models remain outside the HMS interactive review population. References to them are recorded as external prerequisites/evidence; they are not silently promoted to HMS subject nodes.

Cross-module stable references remain scalar across bounded contexts and must not be interpreted as cross-module database foreign keys. Historical snapshots, actor identifiers, technical correlation/request identifiers and polymorphic targets remain non-relational graph exclusions unless a concrete subject-model relationship is explicitly proven.

### 12.2 HMS-003 corrections

| Action | Source | Field | Validated target | Evidence/result |
|---|---|---|---|---|
| REMOVE | notification.NotificationRequest | requestId | — | Technical request/correlation identifier, not a NotificationRequest self-reference. |
| ADD | assets.MaintainableAsset | parentAssetId | assets.MaintainableAsset | Repository invariant explicitly defines direct self-reference. |
| ADD | audit.AuditExportRequest | resultDocumentReferenceId | documents.Document | Audit definition identifies Documents-owned export artifact. |
| ADD | documents.Document | currentVersionId | documents.DocumentVersion | Documents definition identifies current active/approved version. |
| ADD | documents.DocumentVersion | approvedByWorkflowInstanceId | workflow.WorkflowInstance | Documents definition identifies workflow approval instance. |
| ADD | documents.DocumentVersion | supersededByVersionId | documents.DocumentVersion | Documents definition/invariant identifies version self-reference. |
| ADD | identity.AuthorizationDelegationGrant | delegatorUserId | identity.User | Scalar-reference integrity inventory explicitly maps to identity.User. |
| ADD | identity.AuthorizationDelegationGrant | delegateUserId | identity.User | Scalar-reference integrity inventory explicitly maps to identity.User. |
| ADD | identity.User | employeeReferenceId | organization.Employee | Identity definition identifies organization-owned Employee reference. |
| ADD | monitoring.PlanActualDeviation | planTargetId | planning.PlanTarget | Scalar-reference integrity inventory explicitly maps to planning.PlanTarget. |
| ADD | organization.Employee | birthLocalityId | organization.AdministrativeLocality | Organization roadmap and migration define optional locality reference. |
| ADD | organization.OrganizationUnit | unitTypeId | organization.OrganizationUnitType | Organization roadmap explicitly defines this reference. |
| ADD | organization.OrganizationUnit | parentUnitId | organization.OrganizationUnit | Organization roadmap/invariant explicitly defines hierarchy self-reference. |
| ADD | organization.ResponsibilityAssignment | scopeId | organization.OperationalScope | Organization roadmap/provisioning evidence defines scope identity. |
| ADD | planning.PlanRevision | planId | planning.OperationalPlan | Planning definition explicitly defines parent operational plan. |
| ADD | reporting.ReportOutputArtifact | storageObjectReferenceId | documents.DocumentStorageObject | Reporting integration contract validates Documents storage object availability. |
| ADD | reporting.ReportOutputArtifact | documentReferenceId | documents.Document | Scalar-reference integrity policy identifies Documents-owned document reference. |
| ADD | workflow.WorkflowInstance | currentStepId | workflow.WorkflowStep | Workflow definition explicitly defines current step reference. |

### 12.3 Validated subject-edge register

| Source model | Field | Target model | Scope |
|---|---|---|---|
| alarm.AlarmAcknowledgement | alarmId | alarm.Alarm | Same module |
| alarm.AlarmAcknowledgement | organizationUnitId | organization.OrganizationUnit | Cross module |
| alarm.AlarmClosure | alarmId | alarm.Alarm | Same module |
| alarm.AlarmClosure | reviewWorkflowInstanceId | workflow.WorkflowInstance | Cross module |
| alarm.Alarm | incidentId | incident.Incident | Cross module |
| alarm.Alarm | owningOrganizationUnitId | organization.OrganizationUnit | Cross module |
| alarm.Alarm | planningTargetId | planning.PlanTarget | Cross module |
| alarm.AlarmShelving | alarmId | alarm.Alarm | Same module |
| alarm.Alarm | telemetryReadingId | telemetry.TelemetryReading | Cross module |
| alarm.Alarm | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| analytics.AnalyticsDatasetVersion | datasetId | analytics.AnalyticsDataset | Same module |
| analytics.MetricValue | metricEvaluationRunId | analytics.MetricEvaluationRun | Same module |
| assets.AssetConditionRecord | maintainableAssetId | assets.MaintainableAsset | Same module |
| assets.MaintainableAsset | manufacturerPartyId | party.Party | Cross module |
| assets.MaintainableAsset | ownerOrganizationUnitId | organization.OrganizationUnit | Cross module |
| assets.MaintainableAsset | parentAssetId | assets.MaintainableAsset | Same module |
| assets.MaintenanceWorkOrder | assignedOrganizationUnitId | organization.OrganizationUnit | Cross module |
| assets.MaintenanceWorkOrder | maintainableAssetId | assets.MaintainableAsset | Same module |
| assets.MaintenanceWorkOrder | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| audit.AuditAccessRecord | auditEventId | audit.AuditEvent | Same module |
| audit.AuditAccessRecord | exportRequestId | audit.AuditExportRequest | Same module |
| audit.AuditBeforeAfterValue | auditEventId | audit.AuditEvent | Same module |
| audit.AuditEvent | organizationUnitId | organization.OrganizationUnit | Cross module |
| audit.AuditEvent | workflowActionId | workflow.WorkflowAction | Cross module |
| audit.AuditEvent | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| audit.AuditEvent | workflowTaskId | workflow.WorkflowTask | Cross module |
| audit.AuditExportRequest | resultDocumentReferenceId | documents.Document | Cross module |
| audit.AuditExportRequest | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| configuration.ConfigurationValue | definitionId | configuration.ConfigurationDefinition | Same module |
| custody.CustodyTransferTicket | measurementPeriodId | custody.CustodyMeasurementPeriod | Same module |
| custody.CustodyTransferTicket | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| documents.Document | currentVersionId | documents.DocumentVersion | Same module |
| documents.DocumentTargetLink | documentId | documents.Document | Same module |
| documents.DocumentTargetLink | documentVersionId | documents.DocumentVersion | Same module |
| documents.DocumentVersion | approvedByWorkflowInstanceId | workflow.WorkflowInstance | Cross module |
| documents.DocumentVersion | documentId | documents.Document | Same module |
| documents.DocumentVersion | storageObjectId | documents.DocumentStorageObject | Same module |
| documents.DocumentVersion | supersededByVersionId | documents.DocumentVersion | Same module |
| hse.HseCase | incidentReferenceId | incident.Incident | Cross module |
| hse.HseCase | responsibleOrganizationUnitId | organization.OrganizationUnit | Cross module |
| hse.HseCase | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| hse.HseClosure | hseCaseId | hse.HseCase | Same module |
| hse.HseClosure | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| hse.HseCorrectivePreventiveAction | hseCaseId | hse.HseCase | Same module |
| hse.HseCorrectivePreventiveAction | linkedWorkOrderId | assets.MaintenanceWorkOrder | Cross module |
| hse.HseCorrectivePreventiveAction | ownerOrganizationUnitId | organization.OrganizationUnit | Cross module |
| hse.HseCorrectivePreventiveAction | workflowTaskId | workflow.WorkflowTask | Cross module |
| hse.PermitToWork | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| identity.AuthenticationEvent | identityProviderId | identity.IdentityProvider | Same module |
| identity.AuthenticationEvent | userId | identity.User | Same module |
| identity.AuthorizationDecision | userId | identity.User | Same module |
| identity.AuthorizationDelegationGrant | delegateUserId | identity.User | Same module |
| identity.AuthorizationDelegationGrant | delegatorUserId | identity.User | Same module |
| identity.AuthorizationDelegationGrant | permissionId | identity.Permission | Same module |
| identity.AuthorizationDelegationGrant | roleId | identity.Role | Same module |
| identity.ExternalRoleMapping | identityProviderId | identity.IdentityProvider | Same module |
| identity.ExternalRoleMapping | roleId | identity.Role | Same module |
| identity.GroupRoleGrant | roleId | identity.Role | Same module |
| identity.HidraPrincipal | identityProviderId | identity.IdentityProvider | Same module |
| identity.HidraPrincipal | userId | identity.User | Same module |
| identity.LocalCredential | userId | identity.User | Same module |
| identity.LoginSession | identityProviderId | identity.IdentityProvider | Same module |
| identity.LoginSession | userId | identity.User | Same module |
| identity.RolePermissionGrant | permissionId | identity.Permission | Same module |
| identity.RolePermissionGrant | roleId | identity.Role | Same module |
| identity.User | employeeReferenceId | organization.Employee | Cross module |
| identity.UserPermissionGrant | permissionId | identity.Permission | Same module |
| identity.UserPermissionGrant | userId | identity.User | Same module |
| identity.UserRoleGrant | roleId | identity.Role | Same module |
| identity.UserRoleGrant | userId | identity.User | Same module |
| incident.IncidentClosure | incidentId | incident.Incident | Same module |
| incident.IncidentClosure | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| incident.IncidentRelatedIncident | incidentId | incident.Incident | Same module |
| incident.IncidentRelatedIncident | relatedIncidentId | incident.Incident | Same module |
| incident.IncidentResponseAction | incidentId | incident.Incident | Same module |
| incident.IncidentResponseAction | organizationUnitId | organization.OrganizationUnit | Cross module |
| incident.Incident | responsibleOrganizationUnitId | organization.OrganizationUnit | Cross module |
| incident.Incident | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| integration.ExternalSystem | ownerOrganizationUnitId | organization.OrganizationUnit | Cross module |
| integration.IntegrationDeadLetterRecord | exchangeMessageId | integration.IntegrationExchangeMessage | Same module |
| integration.IntegrationDeadLetterRecord | externalSystemId | integration.ExternalSystem | Same module |
| integration.IntegrationDeadLetterRecord | jobRunId | integration.IntegrationJobRun | Same module |
| integration.IntegrationExchangeMessage | externalSystemId | integration.ExternalSystem | Same module |
| integration.IntegrationExchangeMessage | jobRunId | integration.IntegrationJobRun | Same module |
| integrity.IntegrityAssessment | programId | integrity.IntegrityProgram | Same module |
| integrity.IntegrityAssessment | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| integrity.IntegrityCase | primaryDefectId | integrity.PipelineDefect | Same module |
| integrity.IntegrityCase | responsibleOrganizationUnitId | organization.OrganizationUnit | Cross module |
| integrity.IntegrityCase | sourceHseCaseId | hse.HseCase | Cross module |
| integrity.IntegrityCase | sourceIncidentId | incident.Incident | Cross module |
| integrity.IntegrityCase | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| integrity.IntegrityProgram | ownerOrganizationUnitId | organization.OrganizationUnit | Cross module |
| leakdetection.LeakDetectionCase | owningOrganizationUnitId | organization.OrganizationUnit | Cross module |
| leakdetection.LeakDetectionCase | primaryCandidateId | leakdetection.LeakCandidate | Same module |
| leakdetection.LeakEscalationReference | candidateId | leakdetection.LeakCandidate | Same module |
| leakdetection.LeakEscalationReference | caseId | leakdetection.LeakDetectionCase | Same module |
| monitoring.MonitoringRule | telemetryPointId | telemetry.TelemetryPoint | Cross module |
| monitoring.PlanActualDeviation | planTargetId | planning.PlanTarget | Cross module |
| monitoring.PlanActualDeviation | telemetryPointId | telemetry.TelemetryPoint | Cross module |
| monitoring.PlanActualDeviation | trustedTelemetryReadingId | telemetry.TrustedTelemetryReading | Cross module |
| notification.NotificationDeliveryAttempt | messageId | notification.NotificationMessage | Same module |
| notification.NotificationMessage | requestId | notification.NotificationRequest | Same module |
| notification.NotificationMessage | templateId | notification.NotificationTemplate | Same module |
| notification.NotificationRequest | templateId | notification.NotificationTemplate | Same module |
| organization.AdministrativeDistrict | stateId | organization.AdministrativeState | Same module |
| organization.AdministrativeLocality | districtId | organization.AdministrativeDistrict | Same module |
| organization.EmployeeAddress | employeeId | organization.Employee | Same module |
| organization.EmployeeAddress | localityId | organization.AdministrativeLocality | Same module |
| organization.EmployeeAssignment | employeeId | organization.Employee | Same module |
| organization.EmployeeAssignment | organizationUnitId | organization.OrganizationUnit | Same module |
| organization.EmployeeAssignment | positionId | organization.Position | Same module |
| organization.Employee | birthLocalityId | organization.AdministrativeLocality | Same module |
| organization.OrganizationDelegation | delegateEmployeeId | organization.Employee | Same module |
| organization.OrganizationDelegation | delegatorEmployeeId | organization.Employee | Same module |
| organization.OrganizationDelegation | responsibilityAssignmentId | organization.ResponsibilityAssignment | Same module |
| organization.OrganizationHierarchySnapshot | capturedByEmployeeId | organization.Employee | Same module |
| organization.OrganizationUnit | parentUnitId | organization.OrganizationUnit | Same module |
| organization.OrganizationUnit | unitTypeId | organization.OrganizationUnitType | Same module |
| organization.ResponsibilityAssignment | scopeId | organization.OperationalScope | Same module |
| organization.ShiftAssignment | employeeId | organization.Employee | Same module |
| organization.ShiftAssignment | organizationUnitId | organization.OrganizationUnit | Same module |
| organization.ShiftAssignment | shiftId | organization.Shift | Same module |
| party.PartyRoleAssignment | partyId | party.Party | Same module |
| party.PartyRoleAssignment | roleId | identity.Role | Cross module |
| planning.Nomination | counterpartyId | party.Party | Cross module |
| planning.Nomination | revisionId | planning.PlanRevision | Same module |
| planning.Nomination | shipperPartyId | party.Party | Cross module |
| planning.OperationalPlan | approvedRevisionId | planning.PlanRevision | Same module |
| planning.OperationalPlan | currentRevisionId | planning.PlanRevision | Same module |
| planning.OperationalPlan | periodId | planning.PlanningPeriod | Same module |
| planning.OperationalPlan | responsibleOrganizationUnitId | organization.OrganizationUnit | Cross module |
| planning.PlanRevision | baseRevisionId | planning.PlanRevision | Same module |
| planning.PlanRevision | planId | planning.OperationalPlan | Same module |
| planning.PlanRevision | workflowInstanceId | workflow.WorkflowInstance | Cross module |
| planning.PlanTarget | nominationId | planning.Nomination | Same module |
| planning.PlanTarget | revisionId | planning.PlanRevision | Same module |
| planning.PlanTarget | telemetryPointId | telemetry.TelemetryPoint | Cross module |
| reporting.ReportOutputArtifact | documentReferenceId | documents.Document | Cross module |
| reporting.ReportOutputArtifact | reportRunId | reporting.ReportRun | Same module |
| reporting.ReportOutputArtifact | storageObjectReferenceId | documents.DocumentStorageObject | Cross module |
| reporting.ReportRequest | organizationUnitId | organization.OrganizationUnit | Cross module |
| reporting.ReportRequest | reportDefinitionId | reporting.ReportDefinition | Same module |
| reporting.ReportRun | reportDefinitionId | reporting.ReportDefinition | Same module |
| reporting.ReportRun | reportRequestId | reporting.ReportRequest | Same module |
| risk.RiskAssessment | riskRegisterId | risk.RiskRegister | Same module |
| risk.RiskEvidenceLink | riskAssessmentId | risk.RiskAssessment | Same module |
| risk.RiskRegister | ownerOrganizationUnitId | organization.OrganizationUnit | Cross module |
| simulation.SimulationCandidateChange | candidateId | simulation.SimulationOptimizationCandidate | Same module |
| simulation.SimulationOptimizationCandidate | runId | simulation.SimulationRun | Same module |
| simulation.SimulationRecommendation | candidateId | simulation.SimulationOptimizationCandidate | Same module |
| simulation.SimulationRecommendation | runId | simulation.SimulationRun | Same module |
| simulation.SimulationRun | scenarioId | simulation.SimulationScenario | Same module |
| simulation.SimulationScenario | modelId | simulation.SimulationModel | Same module |
| telemetry.TelemetryReading | pointId | telemetry.TelemetryPoint | Same module |
| telemetry.TrustedTelemetryReading | pointId | telemetry.TelemetryPoint | Same module |
| telemetry.TrustedTelemetryReading | readingId | telemetry.TelemetryReading | Same module |
| topology.Equipment | facilityId | topology.Facility | Same module |
| topology.Equipment | manufacturerPartyId | party.Party | Cross module |
| topology.Facility | ownerPartyId | party.Party | Cross module |
| topology.Pipeline | pipelineSystemId | topology.PipelineSystem | Same module |
| workflow.WorkflowAction | instanceId | workflow.WorkflowInstance | Same module |
| workflow.WorkflowAction | organizationUnitId | organization.OrganizationUnit | Cross module |
| workflow.WorkflowAction | taskId | workflow.WorkflowTask | Same module |
| workflow.WorkflowInstance | currentStepId | workflow.WorkflowStep | Same module |
| workflow.WorkflowInstance | definitionId | workflow.WorkflowDefinition | Same module |
| workflow.WorkflowStateHistory | actionId | workflow.WorkflowAction | Same module |
| workflow.WorkflowStateHistory | fromStepId | workflow.WorkflowStep | Same module |
| workflow.WorkflowStateHistory | instanceId | workflow.WorkflowInstance | Same module |
| workflow.WorkflowStateHistory | taskId | workflow.WorkflowTask | Same module |
| workflow.WorkflowStateHistory | toStepId | workflow.WorkflowStep | Same module |
| workflow.WorkflowStepAssignmentRule | definitionId | workflow.WorkflowDefinition | Same module |
| workflow.WorkflowStepAssignmentRule | organizationUnitId | organization.OrganizationUnit | Cross module |
| workflow.WorkflowStepAssignmentRule | stepId | workflow.WorkflowStep | Same module |
| workflow.WorkflowStep | defaultAssignmentRuleId | workflow.WorkflowStepAssignmentRule | Same module |
| workflow.WorkflowStep | definitionId | workflow.WorkflowDefinition | Same module |
| workflow.WorkflowTask | assignedOrganizationUnitId | organization.OrganizationUnit | Cross module |
| workflow.WorkflowTask | instanceId | workflow.WorkflowInstance | Same module |
| workflow.WorkflowTask | stepId | workflow.WorkflowStep | Same module |
| workflow.WorkflowTransition | definitionId | workflow.WorkflowDefinition | Same module |
| workflow.WorkflowTransition | fromStepId | workflow.WorkflowStep | Same module |
| workflow.WorkflowTransition | toStepId | workflow.WorkflowStep | Same module |

### 12.4 Cycle / strongly connected component register

| SCC | Members | Interpretation for HMS-005 |
|---|---|---|
| SCC-01 | organization.OrganizationUnit | Hierarchy self-reference through parentUnitId; collapse as one review unit. |
| SCC-02 | workflow.WorkflowStep, workflow.WorkflowStepAssignmentRule | Bidirectional workflow configuration: step default rule and rule step owner; review as one SCC. |
| SCC-03 | planning.OperationalPlan, planning.PlanRevision | Plan owns current/approved revision while revision points to parent plan; review as one SCC. |
| SCC-04 | assets.MaintainableAsset | Hierarchy self-reference through parentAssetId; collapse as one review unit. |
| SCC-05 | documents.Document, documents.DocumentVersion | Document points to current version while version points to document; version also has supersession self-reference; review as one SCC. |

### 12.5 Cross-module boundary concerns

The validated graph contains **59 cross-module subject edges**. These represent semantic/stable references only. They do not authorize cross-module database foreign keys or aggregate ownership transfer.

| Module boundary | Edge count |
|---|---:|
| audit → workflow | 4 |
| hse → workflow | 4 |
| monitoring → telemetry | 3 |
| workflow → organization | 3 |
| alarm → organization | 2 |
| alarm → workflow | 2 |
| assets → organization | 2 |
| hse → organization | 2 |
| incident → organization | 2 |
| incident → workflow | 2 |
| integrity → organization | 2 |
| integrity → workflow | 2 |
| planning → party | 2 |
| reporting → documents | 2 |
| topology → party | 2 |
| alarm → incident | 1 |
| alarm → planning | 1 |
| alarm → telemetry | 1 |
| assets → party | 1 |
| assets → workflow | 1 |
| audit → documents | 1 |
| audit → organization | 1 |
| custody → workflow | 1 |
| documents → workflow | 1 |
| hse → assets | 1 |
| hse → incident | 1 |
| identity → organization | 1 |
| integration → organization | 1 |
| integrity → hse | 1 |
| integrity → incident | 1 |
| leakdetection → organization | 1 |
| monitoring → planning | 1 |
| party → identity | 1 |
| planning → organization | 1 |
| planning → telemetry | 1 |
| planning → workflow | 1 |
| reporting → organization | 1 |
| risk → organization | 1 |

### 12.6 Out-of-subject prerequisites and unresolved references

The 123-node HMS graph is intentionally a **domain-review graph**, not a complete persistence-schema graph. Repository remediation previously classified **343** additional mirrors as `READ_PERSISTENCE_MODEL`. Current subject models legitimately reference some of those persistence/read concepts, for example `TopologyNode`, `PipelineSegment`, `RiskMatrix`, `CustodyAgreement`, `ExpectedFlowState`, `MetricDefinition`, and `SimulationInputSnapshot`.

Those references do not become HMS review nodes. HMS-005 must carry an **external prerequisite flag** on affected subject models so a model is not described as semantically dependency-free merely because its prerequisite is outside the 123-node review set.

After the 17 graph additions above, **120 HMS-003 unresolved candidates** remain outside the subject graph. They are not silently guessed. Their distribution is:

| Module | Remaining unresolved candidate fields |
|---|---:|
| alarm | 4 |
| analytics | 8 |
| assets | 6 |
| audit | 4 |
| configuration | 2 |
| custody | 8 |
| documents | 3 |
| hse | 3 |
| identity | 9 |
| incident | 3 |
| integration | 5 |
| integrity | 4 |
| leakdetection | 2 |
| monitoring | 3 |
| notification | 11 |
| organization | 1 |
| planning | 6 |
| reporting | 2 |
| risk | 13 |
| simulation | 6 |
| telemetry | 8 |
| topology | 5 |
| workflow | 4 |

Representative non-subject prerequisites confirmed by repository evidence include:

- `topology.TopologyConnection.fromNodeId/toNodeId` → persistence/read `TopologyNode`;
- `topology.Equipment.pipelineSegmentId` and `topology.TopologyConnection.pipelineSegmentId` → persistence/read `PipelineSegment`;
- `risk.RiskMatrixCell.riskMatrixId` → persistence/read `RiskMatrix`;
- `custody.CustodyMeasurementPeriod.agreementId` / `CustodyTransferTicket.agreementId` → persistence/read `CustodyAgreement`;
- `monitoring.PlanActualDeviation.expectedFlowStateId` → persistence/read `ExpectedFlowState`;
- analytics metric-definition IDs → persistence/read `MetricDefinition` / `MetricDefinitionVersion`;
- `simulation.SimulationRun.inputSnapshotId` → historical persistence/read `SimulationInputSnapshot`.

### 12.7 Graph validation reconciliation

```text
subject nodes                              = 123
HMS-003 provisional subject edges         = 165
false subject edges removed               = 1
repository-supported subject edges added  = 17
validated subject edges                   = 181
same-module subject edges                 = 122
cross-module subject edges                = 59
invalid/missing subject targets           = 0
cyclic SCCs                               = 5
remaining unresolved non-graph candidates = 120
models with zero subject-graph dependencies = 34
models with zero subject-graph dependents   = 59
```

The two zero-degree counts above are structural diagnostics only. They are **not** the HMS review order because HMS-005 must first collapse SCCs and account for external prerequisite flags.

### 12.8 HMS-004 outcome

- The 123-node subject graph is internally target-valid: every admitted edge resolves to one HMS subject model.
- One false technical self-edge was removed and seventeen repository-supported edges were added.
- Five cyclic SCCs are explicitly identified and must be collapsed before ordering.
- Cross-module stable references are retained semantically without creating persistence coupling.
- The 343 READ_PERSISTENCE_MODEL mirrors remain outside the HMS interactive population; affected subject models must carry external-prerequisite flags.
- Remaining ambiguous/non-subject candidates remain visible and are not guessed.
- No review level, topological rank, downstream-dependent priority, or HMSR code has been generated.
- No production Java, JPA, Flyway, application contract, enum/value type, or database content was modified.

## 13. HMS-005 outcome

```text
review models                     = 123
dependency levels                 = 9 (0..8)
cyclic SCCs collapsed for order   = 5
unique HMSR codes                 = 123
unique model rows                 = 123
first review                      = HMSR-001 — organization.OrganizationUnitType
last review                       = HMSR-123 — alarm.AlarmShelving
```

No model decision was made by HMS-005. No production Java, JPA mapping, Flyway migration, application contract, enum/value type, or database data was modified.

## 14. HMSR-001 — organization.OrganizationUnitType review

**Decision:** APPROVED  
**Review code:** HMSR-001  
**Dependency level:** 0  
**Bounded context:** organization  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `organization.OrganizationUnit` through `unitTypeId`  
**Transitive dependents:** 52  
**Unresolved/non-subject references:** 0

### 14.1 Semantic role and ordering rationale

`OrganizationUnitType` is the Organization-owned catalog for classifying internal organizational units. It is foundational because it has no upstream subject-model dependency, while `OrganizationUnit` requires a valid `unitTypeId`; the validated same-module FK therefore makes this model a prerequisite for the organization hierarchy and its downstream consumers.

The model classifies people/responsibility structures such as company, division, region, area, station-as-organization-unit, team and department. `STATION_UNIT` is an organizational/responsibility concept and must not be confused with a physical station/facility owned by Topology.

### 14.2 Field semantics

| Field | Type | Mandatory / optional | Approved meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable persistence/catalog identifier; primary identity of this model. |
| `code` | `String` | Mandatory | Language-neutral stable Organization business code. Domain construction normalizes through `OrganizationCode` by trimming and upper-casing with `Locale.ROOT`. |
| `kind` | `OrganizationUnitKind` | Mandatory | Governed broad organizational classification: `COMPANY`, `DIVISION`, `REGION`, `AREA`, `STATION_UNIT`, `TEAM`, `DEPARTMENT`, or `OTHER`. |
| `nameAr` | `String` | Optional | Arabic display name stored on the owning entity. |
| `nameFr` | `String` | Optional | French display name stored on the owning entity. |
| `nameEn` | `String` | Optional | English display name stored on the owning entity. |
| `descriptionAr` | `String` | Optional | Arabic descriptive text stored on the owning entity. |
| `descriptionFr` | `String` | Optional | French descriptive text stored on the owning entity. |
| `descriptionEn` | `String` | Optional | English descriptive text stored on the owning entity. |
| `active` | `boolean` | Mandatory persisted state | Catalog availability/lifecycle flag; deactivation preserves the catalog identity instead of implying deletion. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp; JPA maps it `nullable = false`. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp; JPA maps it `nullable = false`. |

Blank multilingual text is normalized to `null`. The repository migration policy explicitly forbids inventing missing translations, so nullable language fields are consistent with the approved migration semantics.

### 14.3 Multilingual decision

The live Organization roadmap is authoritative over older planning/data-definition text: Organization uses same-entity Arabic/French/English storage only. For `OrganizationUnitType`, the canonical fields are `nameAr/nameFr/nameEn` and `descriptionAr/descriptionFr/descriptionEn`.

The former `OrganizationUnitTypeTranslation` production model was retired by ORG-038, the translation table was retired by `V20260927_003__retire_organization_unit_type_translation_table.sql`, and ORG-040 guardrails prohibit reintroducing a separate Organization translation model/table. Historical documentation that still lists the old translation entity is therefore not current target architecture.

### 14.4 Persistence and dependency consistency

- Domain and JPA shapes agree on all 12 declared components.
- `hidra_org_unit_type.id` is the persistence identity.
- `code`, `kind`, `active`, `created_at`, and `updated_at` are non-null in JPA/persistence.
- Embedded Arabic/French/English names and descriptions are nullable and mapped directly on `hidra_org_unit_type`.
- `organization.OrganizationUnit.unitTypeId` is a confirmed same-module dependency and is protected by `fk_org_unit_type` to `hidra_org_unit_type(id)` with `ON DELETE RESTRICT`.
- No cross-module dependency or database ownership is introduced by this model.

### 14.5 Application and lifecycle interpretation

The outbound repository port supports `save` and `findById`. No current unit-type REST administration contract is required to establish the target semantic baseline. `active` represents whether a catalog type is currently selectable/usable while preserving historical references to the same stable type identity.

For SONATRACH/TRC operations, this catalog describes the organizational classification of responsibility structures. A station organization unit may represent the team/organizational responsibility associated with a station, while the physical station remains a Topology asset.

### 14.6 Review conclusion

The current `OrganizationUnitType` model is semantically coherent with the Organization bounded context, current Flyway/JPA state, same-module reference integrity, and the explicit three-language same-entity policy.

No unresolved semantic question requires a model change for HMSR-001. No production Java, JPA, Flyway, application contract, enum/value type, or database data is changed by this review.

## 15. HMSR-002 — workflow.WorkflowDefinition review

**Decision:** REVISE  
**Review code:** HMSR-002  
**Dependency level:** 0  
**Bounded context:** workflow  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 4 — `workflow.WorkflowInstance`, `workflow.WorkflowStep`, `workflow.WorkflowStepAssignmentRule`, `workflow.WorkflowTransition`  
**Transitive dependents:** 36  
**Unresolved/non-subject references:** 1 — `typeId`, resolved by current persistence evidence to the Workflow-owned catalog/read model rather than an HMS subject model

### 15.1 Semantic role and ordering rationale

`WorkflowDefinition` is the Workflow-owned reusable process-template aggregate. It defines stable workflow identity, business code, localized names, workflow type, lifecycle status, and an integer definition version. It is Level 0 because none of its prerequisites are another one of the 123 HMS subject models. The four direct subject dependents all reference it by `definitionId`, so its semantics must be settled before those models are reviewed.

Workflow remains the process owner only. The target business fact remains owned by the relevant operational bounded context, while Identity owns security identity, Organization owns organizational structure, Audit owns durable evidence, and Notification owns delivery.

### 15.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable workflow-definition identifier and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral business code for the definition family. Together with `version`, current Workflow semantics require uniqueness. |
| `nameAr` | `String` | Optional in current persistence | Arabic display name. Current Workflow data definition identifies Arabic as a target production requirement, but live JPA remains nullable. |
| `nameFr` | `String` | Mandatory | French display name. Workflow policy explicitly makes French mandatory for direct localized names; JPA is `nullable = false`. |
| `nameEn` | `String` | Optional in current persistence | English display name. Current Workflow data definition recommends/targets multilingual production coverage while live JPA remains nullable. |
| `typeId` | `String` | Mandatory | Workflow-owned controlled-vocabulary reference for workflow type. Current HRA-111 persistence evidence resolves it to `hidra_workflow_type_catalog(id)`; `WorkflowCatalogEntry` is a retained read/persistence model, not one of the 123 HMS subjects. |
| `status` | `WorkflowDefinitionStatus` | Mandatory | Lifecycle state: `DRAFT`, `ACTIVE`, `INACTIVE`, or `RETIRED`. Only `ACTIVE` definitions may start new instances. |
| `version` | `int` | Mandatory | Definition version. Current Workflow data definition requires a minimum value of 1. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp; JPA and schema are non-null. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp; JPA and schema are non-null. |

The current record normalizes blank text to `null` after required-field guards. `id`, `code`, `typeId`, and `status` already fail fast in the domain constructor.

### 15.3 Catalog dependency resolution

HMS-003 left `typeId` unresolved because the 123-subject graph did not contain an unambiguous subject target. Stronger repository evidence now resolves its semantics without adding an HMS graph edge:

- `WorkflowDefinitionJpaEntity.typeId` maps to `hidra_workflow_definition.type_id`;
- HRA-111 adds and validates `fk_hra111_workflow_008`;
- that FK points to `hidra_workflow_type_catalog(id)`;
- repository mirror classification retains `WorkflowCatalogEntry` as a Workflow READ_PERSISTENCE_MODEL outside the 123 HMS subject set.

Therefore `typeId` is a **Workflow-owned value/catalog dependency**, not an unresolved subject-model dependency. The register's combined unresolved/non-subject count remains 1 because the dependency is deliberately outside the HMS subject population.

### 15.4 Lifecycle and versioning semantics

The current baseline semantics are:

- only `ACTIVE` definitions can start instances; the domain helper `canStartInstance()` already expresses this;
- `DRAFT` definitions may be edited;
- `ACTIVE` definitions are intended to be immutable except for controlled retirement/deactivation;
- changing an active definition requires a new version;
- `RETIRED` definitions cannot start new instances;
- the current direct `version` field is acceptable for the first baseline; the data definition's possible future `WorkflowDefinitionVersion` aggregate is an enhancement, not a prerequisite for this HMS decision.

### 15.5 Persistence and dependency consistency

The live domain and JPA models have the same 10 declared components. The initial Workflow migration persists all 10 fields and makes `id`, `code`, `name_fr`, `type_id`, `status`, `version`, `created_at`, and `updated_at` non-null.

Validated subject dependencies point **into** this model from:

- `WorkflowInstance.definitionId`;
- `WorkflowStep.definitionId`;
- `WorkflowStepAssignmentRule.definitionId`;
- `WorkflowTransition.definitionId`.

The catalog reference `typeId` is protected by a same-module FK to `hidra_workflow_type_catalog`. No cross-module database ownership is introduced.

### 15.6 Required revisions

The semantic shape is sound, but the live implementation does not yet enforce all repository-defined WorkflowDefinition invariants strongly enough for an APPROVED target baseline.

1. **Definition version minimum:** the Workflow data definition requires `version >= 1`, but the current domain constructor accepts zero or negative values and the live schema only declares `version integer NOT NULL` without a minimum-value check.
2. **French name requirement at the domain boundary:** Workflow policy and JPA require `nameFr`, but the current domain constructor can normalize a blank French name to `null`, allowing invalid state to exist until persistence rejects it.
3. **Definition identity/version uniqueness:** the Workflow data definition requires `(code, version)` to be unique. The live migration provides an index on `code` only; no repository evidence was found for a database unique constraint or an application uniqueness guard on `(code, version)`. The data-definition example named `idx_workflow_definition_code_version` is an ordinary index, not a uniqueness constraint, so it does not satisfy the stated rule.
4. **Activation/version governance:** the approved semantic baseline must preserve the rule that an ACTIVE definition is not edited in place and a business change creates a new version. Any later Workflow correction task must enforce this at the appropriate application/domain boundary rather than relying on record immutability alone.

These are correction requirements for a later explicitly authorized Workflow roadmap task. HMSR-002 does not modify production Java, JPA, Flyway, application contracts, tests, or database data.

### 15.7 Multilingual and operational interpretation

Workflow direct names use `nameAr/nameFr/nameEn`. Current persistence makes French mandatory while Arabic and English remain nullable; the Workflow data definition explicitly flags stronger production multilingual coverage as a target direction. This review does not invent a new storage model or translation structure.

For SONATRACH/TRC operations, a workflow definition represents a governed reusable approval/validation route for operational decisions such as telemetry validation, planning approval, alarm/incident review, integrity actions, maintenance, HSE, custody, and other Hidra processes. It does not own or mutate the underlying operational fact merely by defining the process.

### 15.8 Review conclusion

**REVISE.** `WorkflowDefinition` remains the correct Workflow aggregate and its fields/dependency direction are appropriate, but the target semantic baseline cannot mark it APPROVED while the documented version, French-name, uniqueness, and active-definition versioning rules are not consistently enforced.

The later HMS reconciliation must retain these four correction obligations until an explicitly authorized Workflow implementation task resolves them or the product owner explicitly changes the target semantics.

## 16. HMSR-003 — organization.AdministrativeState review

**Decision:** APPROVED  
**Review code:** HMSR-003  
**Dependency level:** 0  
**Bounded context:** organization  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `organization.AdministrativeDistrict` through `stateId`  
**Transitive dependents:** 17  
**Unresolved/non-subject references:** 0

### 16.1 Semantic role and ordering rationale

`AdministrativeState` is the Organization-owned reference model for an Algerian administrative state/wilaya. It is the root of the Organization administrative-geography chain:

```text
AdministrativeState
  -> AdministrativeDistrict
      -> AdministrativeLocality
          -> EmployeeAddress
```

It is Level 0 because it has no upstream dependency on another HMS subject model. `AdministrativeDistrict.stateId` depends directly on it, so the state reference must be established before district, locality, and address semantics are reviewed.

The current repository places this administrative geography inside the Organization bounded context and uses it to normalize employee/address geography. HMSR-003 does not redefine that ownership boundary.

### 16.2 Field semantics

| Field | Type | Mandatory / optional | Approved meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable persistence/reference identifier; primary identity of the state/wilaya record. |
| `code` | `String` | Mandatory | Stable language-neutral administrative business/reference code. Domain construction routes it through `OrganizationCode`, which trims and upper-cases with `Locale.ROOT`. |
| `nameAr` | `String` | Optional | Arabic state/wilaya display name stored on the owning entity. |
| `nameFr` | `String` | Optional | French state/wilaya display name stored on the owning entity. |
| `nameEn` | `String` | Optional | English state/wilaya display name stored on the owning entity. |
| `active` | `boolean` | Mandatory persisted state | Reference-data availability/lifecycle flag; preserves stable identity when a row is not currently selectable. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp; JPA and schema map it as non-null. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp; JPA and schema map it as non-null. |

The domain constructor rejects blank IDs and codes, normalizes the code through the canonical Organization code value policy, trims multilingual names, and converts blank names to `null`.

### 16.3 Multilingual decision

The live Organization roadmap explicitly records `AdministrativeState.nameAr/nameFr/nameEn` as the canonical same-entity multilingual representation. No separate translation aggregate/table is part of the target Organization model for this reference.

All three name fields are nullable in the current domain, JPA mapping, and schema. Current Organization evidence does not establish a model-level requirement that any specific language label must be non-null, so HMSR-003 does not invent one.

### 16.4 Persistence and dependency consistency

The domain record and JPA entity agree on all eight declared components.

Persistence evidence confirms:

- `hidra_org_administrative_state.id` is the primary key;
- `code`, `active`, `created_at`, and `updated_at` are non-null;
- `name_ar`, `name_fr`, and `name_en` are nullable;
- the base migration indexes state `code`, lifecycle state, and audit timestamps;
- `organization.AdministrativeDistrict.stateId` is protected by `fk_org_district_state` to `hidra_org_administrative_state(id)` with `ON DELETE RESTRICT`.

This is a same-module ownership/reference relationship. No cross-module dependency or cross-module database foreign key is introduced by `AdministrativeState`.

### 16.5 Code and identity policy

`AdministrativeState.code` is one of the Organization reference/master-data codes explicitly adopted by the canonical `OrganizationCode` policy. Focused tests verify normalization for values such as an Algerian wilaya code and verify rejection of blank code input.

Repository evidence does not establish a separate `AdministrativeState` code-uniqueness invariant in the current domain/application/schema contract. The schema currently provides a normal code index, not a unique constraint. Because HMS reviews must remain evidence-based, this review does not infer or invent uniqueness solely from the field name or reference-data role. A later data-provisioning or Organization governance task may impose a stronger uniqueness rule only with explicit repository/business evidence.

### 16.6 Application and data-provisioning interpretation

The current outbound repository port exposes `save` and `findById`; there is no current dedicated AdministrativeState REST administration contract that changes the model semantics.

For provisioning, `AdministrativeState` is a target reference dataset and the root parent for Algerian district/locality normalization. Data provisioning must use approved authoritative state/wilaya identifiers, codes, and multilingual names; HMSR-003 does not invent reference rows or geographical values.

For SONATRACH/TRC operations, this administrative geography is personnel/organization address-reference data. It must not be confused with physical pipeline topology, facilities, operational regions, or organization units merely because similar geographic names may occur.

### 16.7 Review conclusion

**APPROVED.** The current `AdministrativeState` model is semantically coherent with the Organization bounded context, canonical code normalization, same-entity multilingual policy, persistence shape, and the validated administrative-geography dependency chain.

No unresolved semantic question requires a model change for HMSR-003. No production Java, JPA, Flyway, application contract, test, enum/value type, or database data is changed by this review.

## 17. HMSR-004 — party.Party review

**Decision:** REVISE  
**Review code:** HMSR-004  
**Dependency level:** 0  
**Bounded context:** party  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 5 — `party.PartyRoleAssignment`, `planning.Nomination`, `topology.Facility`, `topology.Equipment`, `assets.MaintainableAsset`  
**Transitive dependents:** 14  
**Unresolved subject references:** 0

### 17.1 Semantic role and ordering rationale

`Party` is the Party-owned master record for an external legal entity or recognized business actor referenced across Hidra. It is the identity anchor for suppliers, vendors, contractors, manufacturers, customers, shippers, owners, operators, joint-venture partners, inspection/certification bodies and other counterparties.

It is Level 0 because none of its prerequisites are another HMS subject model. Its `partyTypeId` prerequisite is a Party-owned catalog/read-persistence model outside the 123 HMS subject set, while five reviewed subject models depend directly on `Party`.

The boundary remains explicit:

- Party owns external/counterparty master identity and business-role eligibility;
- Organization owns internal employees, units, positions and reporting structures;
- Identity owns users, credentials, roles and permissions;
- Assets, Topology, Planning, Custody and other bounded contexts store only Party references/snapshots and do not own Party master data.

### 17.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable Party identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable Party business code. The active Party DDD requires this code to be globally unique. |
| `partyTypeId` | `String` | Mandatory | Party-owned controlled-vocabulary reference to Party type/nature, such as legal entity, natural person, public authority, affiliate, JV, consortium, laboratory, certification body or regulator. |
| `legalName` | `String` | Mandatory | Official legal name of the Party. This is required by the active Party DDD and live persistence schema. |
| `tradeName` | `String` | Optional | Commercial/common trading name when different from the legal name. |
| `shortName` | `String` | Optional | Short display name. |
| `countryCode` | `String` | Mandatory | Country of legal establishment. |
| `jurisdictionCode` | `String` | Optional | Legal jurisdiction or registry-area identifier. |
| `status` | `PartyStatus` | Mandatory | Party lifecycle state: `DRAFT`, `ACTIVE`, `SUSPENDED`, `BLOCKED`, or `RETIRED`. |
| `primaryRoleCodeSnapshot` | `String` | Optional snapshot | Display/convenience snapshot of a main role; authoritative role membership remains `PartyRoleAssignment`. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The current domain constructor already rejects blank `id`, `code`, `partyTypeId`, and `countryCode`, rejects null `status`, trims String values, and converts blank optional text to `null`.

### 17.3 Party-type dependency resolution

HMS-003 correctly classified `partyTypeId` as a value/catalog dependency rather than a 123-subject graph edge.

Stronger repository evidence confirms:

- `PartyJpaEntity.partyTypeId` maps to `hidra_party_party.party_type_id`;
- HRA-111 installs and validates `fk_hra111_party_016`;
- the FK targets `hidra_party_type(id)` with `ON DELETE RESTRICT`;
- `PartyType` is retained as a Party READ_PERSISTENCE_MODEL outside the 123 HMS subject population.

Therefore the Party subject has no upstream HMS-model dependency, while still having a concrete same-module catalog prerequisite.

### 17.4 Downstream dependency and boundary evidence

The validated HMS graph records five direct subject dependents:

- `party.PartyRoleAssignment.partyId -> party.Party`;
- `planning.Nomination.shipperPartyId -> party.Party`;
- `planning.Nomination.counterpartyId -> party.Party`;
- `topology.Facility.ownerPartyId -> party.Party`;
- `topology.Equipment.manufacturerPartyId -> party.Party`;
- `assets.MaintainableAsset.manufacturerPartyId -> party.Party`.

The two Nomination fields are two graph edges from one dependent model, which is why the register reports five direct dependent models rather than six.

Cross-module Party references remain scalar stable references/snapshots. They do not authorize cross-module database foreign keys or ownership transfer. The Party DDD explicitly forbids other bounded contexts from embedding Party domain objects.

### 17.5 Lifecycle and role semantics

`PartyStatus` is a Party lifecycle vocabulary, distinct from role-assignment status. The current helper `selectableForNewReference()` returns true only for `ACTIVE` parties, which provides a safe default for new references.

The Party DDD further establishes that:

- a Party has exactly one party type;
- a Party may hold multiple active roles;
- authoritative roles live in `PartyRoleAssignment`, not in `primaryRoleCodeSnapshot`;
- blocked Parties must not be selected for new operational references unless an explicitly authorized Workflow override applies;
- Party must not duplicate internal Employee or Identity User ownership.

Any authorized exception to the default selection rule belongs at an application/Workflow authorization boundary; it must not make the display snapshot authoritative.

### 17.6 Persistence consistency

The live domain and JPA models expose the same 12 components. The base migration persists the same shape and makes `id`, `code`, `party_type_id`, `legal_name`, `country_code`, `status`, `created_at`, and `updated_at` non-null.

The database currently has ordinary indexes for Party `code`, `party_type_id`, `status`, and audit timestamps. No current migration or repository contract found during HMSR-004 establishes Party-code uniqueness.

The outbound Party repository port exposes only `save` and `findById`; the Spring Data repository likewise exposes the inherited ID-based operations and no Party-code existence/lookup contract.

### 17.7 Required revisions

The aggregate role and field set are correct, but two active Party DDD requirements are not enforced consistently enough for an APPROVED target baseline.

1. **Required legal name:** the active Party DDD declares `legalName` required and the JPA/schema column is non-null, but the current domain constructor does not reject null/blank `legalName`. It normalizes blank input to `null`, allowing invalid Party state to exist until persistence failure.
2. **Unique Party code:** the active Party DDD explicitly states `Party code must be unique`. The live schema has only a non-unique index on `code`, `PartyRepositoryPort` has no `findByCode`/`existsByCode` capability, and `PartyApplicationService.registerParty` performs no uniqueness check. The invariant therefore has no demonstrated application/database enforcement.

These corrections belong to a later explicitly authorized Party implementation task. Domain construction should enforce locally decidable required state such as `legalName`; global code uniqueness should be enforced at the application/database boundary rather than through a repository lookup inside the domain record.

HMSR-004 does not change production Java, JPA, Flyway, application/API contracts, tests, or database data.

### 17.8 Operational interpretation

For SONATRACH/TRC operations, `Party` is the shared counterparty/master-data identity for external organizations or recognized business actors. A manufacturer referenced by equipment, an owner referenced by a facility, or a shipper/counterparty referenced by a nomination must resolve to the same Party master identity while the consuming module retains only its stable reference and relevant snapshots.

Party role does not imply contractual entitlement, Identity authorization, Workflow approval, financial account state, procurement eligibility, or OT-control authority. Those remain owned by their respective bounded contexts and governance mechanisms.

### 17.9 Review conclusion

**REVISE.** The current `Party` aggregate, field meanings, catalog prerequisite and cross-module reference direction are semantically appropriate, but the target baseline cannot mark the model APPROVED while mandatory `legalName` and globally unique `code` are not consistently enforced.

HMS reconciliation must retain these two correction obligations until an explicitly authorized Party implementation task resolves them or the target Party semantics are explicitly changed.

## 18. HMSR-005 — telemetry.TelemetryPoint review

**Decision:** REVISE  
**Review code:** HMSR-005  
**Dependency level:** 0  
**Bounded context:** telemetry  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 5 — `telemetry.TelemetryReading`, `monitoring.MonitoringRule`, `telemetry.TrustedTelemetryReading`, `planning.PlanTarget`, `monitoring.PlanActualDeviation`  
**Transitive dependents:** 9  
**Unresolved/non-subject references:** 3 — all three now semantically resolved to Telemetry-owned non-subject persistence/read models

### 18.1 Semantic role and ordering rationale

`TelemetryPoint` is the canonical Hidra telemetry tag/signal identity. It is the stable point referenced by raw readings, trusted readings, monitoring rules, planning targets, planned-versus-actual deviation logic, external tag mappings, topology bindings, quality assessment and latest-state projections.

It is Level 0 because none of its prerequisites are another one of the 123 HMS subject models. Its configuration prerequisites — device, unit and telemetry catalogs — are Telemetry-owned persistence/read models outside the HMS subject population.

Telemetry owns acquisition/source/device/point/reading/quality/trust semantics. It does not own physical topology, monitoring interpretation, alarms, incidents, workflow approval, audit storage, hydraulic simulation, risk scoring or custody fiscal records.

### 18.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable TelemetryPoint identity and persistence primary key. |
| `deviceId` | `String` | Mandatory | Parent Telemetry-owned device reference. |
| `code` | `String` | Mandatory | Canonical point/tag code; target semantics require uniqueness within the parent device. |
| `nameAr` | `String` | Optional | Arabic point display label. |
| `nameFr` | `String` | Mandatory | French point display label in the current Telemetry DDD and persistence schema. |
| `nameEn` | `String` | Optional | English point display label. |
| `pointTypeId` | `String` | Mandatory | Telemetry catalog reference in catalog family `POINT_TYPE`. |
| `signalTypeId` | `String` | Mandatory | Telemetry catalog reference in catalog family `SIGNAL_TYPE`; governs compatible reading value shape. |
| `unitId` | `String` | Conditionally required | Telemetry unit reference. Optional structurally, but required for numeric engineering measurements unless the point type explicitly exempts it. |
| `defaultAggregationMethodId` | `String` | Optional | Telemetry catalog reference in catalog family `AGGREGATION_METHOD`. |
| `samplingPeriodSeconds` | `Integer` | Optional | Expected source sampling period in seconds. |
| `externalReference` | `String` | Optional / legacy-simple reference | External source/tag reference; rich/historical aliases belong in `TelemetryExternalTagMapping`. |
| `deadbandValue` | `BigDecimal` | Optional | Point-level deadband/noise-filter threshold. |
| `minOperationalValue` | `BigDecimal` | Optional | Engineering/physical minimum used by telemetry validation when applicable. |
| `maxOperationalValue` | `BigDecimal` | Optional | Engineering/physical maximum used by telemetry validation when applicable. |
| `status` | `TelemetryLifecycleStatus` | Mandatory | Technical lifecycle state. Only ACTIVE points are eligible to produce trusted readings. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The current domain constructor already rejects blank `id`, `deviceId`, `code`, `pointTypeId`, and `signalTypeId`, rejects null `status`, trims String values, and converts blank optional text/references to `null`.

### 18.3 Resolution of the three non-subject references

The HMSR register carried three unresolved/non-subject references for this model. Current repository evidence resolves all three without creating subject-graph edges:

1. `deviceId -> TelemetryDevice`
   - `TelemetryDevice` is Telemetry-owned;
   - HRA mirror classification retains it as a `READ_PERSISTENCE_MODEL`, outside the 123 HMS subjects;
   - HRA-111 installs `fk_hra111_telemetry_009` from `hidra_telemetry_point.device_id` to `hidra_telemetry_device(id)`.

2. `unitId -> TelemetryUnit`
   - the live repository contains `TelemetryUnitJpaEntity` backed by `hidra_telemetry_unit`;
   - the Telemetry DDD defines the cardinality `TelemetryUnit -> TelemetryPoint`;
   - `TelemetryUnit` is retained as a Telemetry `READ_PERSISTENCE_MODEL`, outside the 123 HMS subjects.

3. `defaultAggregationMethodId -> TelemetryCatalogEntry`
   - the Telemetry DDD explicitly identifies catalog family `AGGREGATION_METHOD`;
   - the live catalog persistence model is `TelemetryCatalogEntryJpaEntity` backed by `hidra_telemetry_type_catalog`;
   - `TelemetryCatalogEntry` is a Telemetry `READ_PERSISTENCE_MODEL`, outside the 123 HMS subjects.

The existing `pointTypeId` and `signalTypeId` classifications remain value/catalog dependencies to `TelemetryCatalogEntry`, not subject-model dependencies.

### 18.4 Downstream dependency evidence

The validated HMS graph has five direct dependent subject models:

- `TelemetryReading.pointId -> TelemetryPoint`;
- `TrustedTelemetryReading.pointId -> TelemetryPoint`;
- `MonitoringRule.telemetryPointId -> TelemetryPoint`;
- `PlanTarget.telemetryPointId -> TelemetryPoint`;
- `PlanActualDeviation.telemetryPointId -> TelemetryPoint`.

Other Telemetry persistence/read models such as external tag mappings, bindings, quality assessments and point-state snapshots also reference the point, but they are outside the 123 HMS subject set and therefore do not change the subject graph level.

### 18.5 Persistence and reference integrity

The live domain and JPA shapes agree on all 18 declared components.

The base Telemetry migration makes `device_id`, `code`, `name_fr`, `point_type_id`, `signal_type_id`, `status`, `created_at`, and `updated_at` non-null.

HRA-111 adds same-module FKs for:

- `device_id -> hidra_telemetry_device(id)`;
- `point_type_id -> hidra_telemetry_type_catalog(id)`;
- `signal_type_id -> hidra_telemetry_type_catalog(id)`.

These FKs establish row existence, but they do not by themselves prove the catalog family or active-state semantics required by the DDD.

The current schema has normal indexes on `device_id`, `code`, `point_type_id`, `signal_type_id`, `unit_id`, and `default_aggregation_method_id`. It does not currently enforce the documented unique `(device_id, code)` key, and no current FK was found from optional `unit_id` or `default_aggregation_method_id` to their resolved Telemetry-owned targets.

### 18.6 Required revisions

The current field set and ownership are appropriate, but the implementation does not yet enforce all repository-defined TelemetryPoint semantics strongly enough for an APPROVED target baseline.

1. **Mandatory French point name:** `nameFr` is required by the Telemetry DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank text to `null`.
2. **Point-code uniqueness:** the Telemetry DDD requires point code uniqueness per device and explicitly recommends unique `(device_id, code)`. The live migration currently has separate non-unique indexes only, and the point repository/application service provides no demonstrated uniqueness check.
3. **Catalog-family validation:** `pointTypeId`, `signalTypeId`, and `defaultAggregationMethodId` have specific catalog-family meanings (`POINT_TYPE`, `SIGNAL_TYPE`, `AGGREGATION_METHOD`). Existing generic catalog FKs, where present, prove only that a catalog row exists; the current point registration service does not demonstrate family/active-entry validation.
4. **Unit reference integrity:** when `unitId` is present it semantically targets `TelemetryUnit`, but current point persistence does not demonstrate FK/application validation of that optional reference.
5. **Signal/unit compatibility:** the DDD requires `signalTypeId` to determine compatible numeric/text/boolean reading shape and requires `unitId` for numeric engineering measurements unless explicitly exempted by point type. The current point registration service simply persists the supplied IDs and does not demonstrate this conditional validation.

These rules should be enforced at the appropriate domain/application/database boundary. Repository lookups and catalog-family checks must not be pushed into the domain record constructor.

HMSR-005 does not change production Java, JPA, Flyway, application/API contracts, tests, or database data.

### 18.7 Lifecycle and operational interpretation

The current registration service creates new points in `PLANNED` state. Historical/inactive points remain valid identities for retained telemetry history, while only ACTIVE points should participate in trusted-reading production.

For SONATRACH/TRC operations, a TelemetryPoint represents the canonical digital identity of an acquired signal/tag — for example pressure, flow, temperature, valve state or equipment status — not the physical instrument/equipment asset itself. Physical asset association belongs through TelemetryPointBinding/topology references, preserving the Telemetry/Topology boundary.

### 18.8 Review conclusion

**REVISE.** `TelemetryPoint` has the correct bounded-context ownership, field shape, dependency direction and downstream role, and the three previously unresolved non-subject references are now semantically resolved. However, the target baseline cannot mark it APPROVED while the documented French-name, per-device code uniqueness, catalog-family, optional-reference integrity and signal/unit compatibility rules are not consistently enforced.

HMS reconciliation must retain these correction obligations until an explicitly authorized Telemetry implementation task resolves them or the target Telemetry semantics are explicitly changed.

## 19. HMSR-006 — planning.PlanningPeriod review

**Decision:** REVISE  
**Review code:** HMSR-006  
**Dependency level:** 0  
**Bounded context:** planning  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `planning.OperationalPlan` through `periodId`  
**Transitive dependents:** 9  
**Unresolved/non-subject references:** 0

### 19.1 Semantic role and ordering rationale

`PlanningPeriod` is the Planning-owned planning-horizon reference for daily, weekly, monthly, campaign and operational-window planning. It establishes the temporal container in which operational plans are created and reviewed.

It is Level 0 because it has no upstream dependency on another HMS subject model. `OperationalPlan.periodId` depends directly on it, so period semantics must be settled before plan/revision/nomination/target review proceeds.

Planning owns expected operational state only. Telemetry owns actual readings, Monitoring owns deviation semantics, Workflow owns approval process state, Topology owns physical assets, Organization owns internal responsibility structures, and Identity/platform owns actor identity.

### 19.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable PlanningPeriod identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable business code for the planning horizon; current Planning DDD requires uniqueness. |
| `nameAr` | `String` | Optional | Arabic period display label. |
| `nameFr` | `String` | Mandatory in target/persistence semantics | French period display label. The Planning DDD and JPA/schema mark it required. |
| `nameEn` | `String` | Optional | English period display label. |
| `periodTypeId` | `String` | Mandatory | Planning-owned catalog reference for the planning-horizon type; semantically belongs to `PERIOD_TYPE`. |
| `periodStart` | `Instant` | Mandatory | Inclusive start instant of the horizon. |
| `periodEnd` | `Instant` | Mandatory | Exclusive end instant of the horizon. |
| `timeZone` | `String` | Mandatory | IANA planning time-zone identifier. Current default is `Africa/Algiers`. |
| `status` | `PlanningPeriodStatus` | Mandatory | Technical lifecycle: `OPEN`, `LOCKED`, `CLOSED`, or `CANCELLED`. |
| `createdByActorId` | `String` | Mandatory reference | Creator actor identity. This is an Identity/platform actor reference, not an HMS subject-model edge. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The current domain constructor already rejects blank `id`, `code`, `periodTypeId`, and `createdByActorId`, rejects null start/end/status values, trims strings, and defaults the creation service to `PlanningPeriodStatus.OPEN`.

### 19.3 Dependency and catalog evidence

HMS-003 correctly classified:

- `periodTypeId` as a value/catalog dependency rather than a subject-model graph edge;
- `createdByActorId` as an Identity actor reference, with no fake `identity.User` subject edge.

Stronger persistence evidence confirms `periodTypeId` is Planning-owned:

- `PlanningPeriodJpaEntity.periodTypeId` maps to `hidra_planning_period.period_type_id`;
- HRA-111 installs `fk_hra111_planning_017`;
- that FK targets `hidra_planning_catalog_entry(id)` with `ON DELETE RESTRICT`;
- `PlanningCatalogEntry` is retained as a Planning read/persistence model outside the 123 HMS subject set.

Therefore `PlanningPeriod` has no upstream HMS subject dependency.

### 19.4 Period-type vocabulary conflict

The live Planning DDD contains a semantic inconsistency that must not be guessed away during provisioning:

- the `PlanningPeriod.periodTypeId` field description lists `DAILY`, `WEEKLY`, `MONTHLY`, `CAMPAIGN`, `OPERATION_WINDOW`;
- the same document's canonical `PERIOD_TYPE` catalog table lists `DAY`, `WEEK`, `MONTH`, `CAMPAIGN`, `OPERATION_WINDOW`.

No current migration/seed evidence found during HMSR-006 resolves which code family is authoritative. The eventual correction must select one canonical code vocabulary and align DDD, catalog provisioning, API behavior and existing data consistently. HMSR-006 does not invent the answer.

### 19.5 Temporal semantics

The target Planning DDD is explicit:

```text
periodStart < periodEnd
```

and describes `periodEnd` as exclusive.

The current domain/HRA-051 invariant is weaker:

```text
periodStart <= periodEnd
```

because it rejects only `periodEnd.isBefore(periodStart)`. A zero-length period with equal start/end is therefore currently accepted by the domain even though it violates the target PlanningPeriod contract.

This is a real target-model semantic contradiction, not merely a persistence detail.

### 19.6 Persistence and application consistency

The live domain and JPA models expose the same 13 components.

The base Planning migration makes `id`, `code`, `name_fr`, `period_type_id`, `period_start`, `period_end`, `time_zone`, `status`, `created_by_actor_id`, `created_at`, and `updated_at` non-null.

The application service:

- creates periods as `OPEN`;
- defaults `timeZone` to `Africa/Algiers` only when the command value is null;
- persists directly through `PlanningPeriodRepositoryPort.save`;
- performs no demonstrated code-uniqueness check;
- performs no demonstrated `PERIOD_TYPE` catalog-family/active-entry validation;
- performs no demonstrated IANA time-zone validation.

The repository port/Spring Data repository expose ID-based save/find operations only; no `findByCode`/`existsByCode` capability is currently present.

### 19.7 Required revisions

The model's role and dependency direction are correct, but the target baseline cannot be APPROVED until these evidence-backed inconsistencies are resolved:

1. **Strict non-zero interval:** align the live invariant with the Planning DDD's `periodStart < periodEnd`; equality must not silently represent a valid planning horizon unless the target semantics are explicitly changed.
2. **Unique period code:** the Planning DDD and required-constraints section explicitly require `unique(hidra_planning_period.code)`, while the live schema currently has only a normal index and the application/repository contract shows no uniqueness guard.
3. **Canonical PERIOD_TYPE vocabulary:** resolve `DAILY/WEEKLY/MONTHLY` versus `DAY/WEEK/MONTH` before catalog/data provisioning. Do not map or seed by guesswork.
4. **Catalog-family and active-entry validation:** the HRA-111 FK proves only that `periodTypeId` points to some planning catalog row. Creation must ensure it belongs to the intended `PERIOD_TYPE` family and satisfies the applicable active/reference policy.
5. **Time-zone validity:** `timeZone` is a required IANA zone. The current service defaults only null values; blank input is normalized by the domain to null and arbitrary invalid zone strings can reach persistence because the column is plain varchar. Validation/defaulting semantics must be made deterministic.
6. **Required French label boundary:** the Planning DDD and persistence contract require `nameFr`, while current domain creation can normalize blank/null to `null`. The eventual correction must ensure the required French label is enforced at an appropriate input/application/persistence boundary, without contradicting HRA's prohibition on blindly promoting all generic text to domain-constructor invariants.
7. **Closed-period plan governance:** the Planning DDD states that closed periods cannot receive new plan revisions unless reopened by a workflow-approved action. This lifecycle rule must remain visible to the later `OperationalPlan`/`PlanRevision` reviews and be enforced at the owning application/workflow boundary rather than inferred by clients.

HMSR-006 does not change production Java, JPA, Flyway, API/application contracts, tests, or database data.

### 19.8 Operational interpretation

For SONATRACH/TRC operations, a PlanningPeriod is the governed temporal frame for a transport program — for example a daily dispatch horizon, weekly program, monthly transport program, campaign, or special operational window.

The stored time zone is operationally significant because schedule interpretation, cut-off times, nominations, planned targets and approvals must resolve the same local planning horizon while timestamps remain stored as instants. The default `Africa/Algiers` is consistent with the current application intent, but the field still requires valid and explicit semantics for non-default cases.

A PlanningPeriod does not itself own telemetry measurements, topology state, workflow tasks or monitoring deviations.

### 19.9 Review conclusion

**REVISE.** `PlanningPeriod` is the correct foundational Planning model and its subject dependency direction is sound, but the target semantic baseline has unresolved enforcement and definition conflicts around interval strictness, code uniqueness, period-type vocabulary, catalog validation, time-zone validity, required French labeling and closed-period governance.

HMS reconciliation must retain these obligations until an explicitly authorized Planning implementation/documentation task resolves them or the target semantics are explicitly changed.

## 20. Current next task

```text
HMSR-007 — identity.Role
```

Exact commit message:

```text
docs(model-review): review identity Role
```

Start HMSR-007 only after HMSR-006 is committed and reported. Do not start HMSR-008 automatically. HMS-006 final reconciliation remains blocked until all 123 HMSR tasks are resolved.
