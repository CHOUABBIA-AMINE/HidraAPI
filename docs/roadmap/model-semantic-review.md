# HidraAPI Target Model Semantic Review Roadmap

**Status:** Active — HMS-005 completed; HMSR-001 approved, HMSR-002 reviewed as REVISE, HMSR-003 approved, HMSR-004 reviewed as REVISE, HMSR-005 reviewed as REVISE, HMSR-006 reviewed as REVISE, HMSR-007 reviewed as REVISE, HMSR-008 reviewed as REVISE, HMSR-009 reviewed as REVISE, HMSR-010 reviewed as REVISE, HMSR-011 reviewed as REVISE, HMSR-012 reviewed as REVISE, HMSR-013 reviewed as REVISE, HMSR-014 reviewed as REVISE, HMSR-015 reviewed as REVISE, HMSR-016 approved, HMSR-017 approved, HMSR-018 reviewed as REVISE, HMSR-019 reviewed as REVISE, HMSR-020 reviewed as REVISE, HMSR-021 reviewed as REVISE, HMSR-022 reviewed as REVISE, HMSR-023 reviewed as REVISE, HMSR-024 reviewed as REVISE, HMSR-025 reviewed as REVISE, HMSR-026 reviewed as REVISE, HMSR-027 reviewed as REVISE, HMSR-028 reviewed as REVISE, HMSR-029 reviewed as REVISE, HMSR-030 approved, HMSR-031 reviewed as REVISE, HMSR-032 reviewed as REVISE, HMSR-033 reviewed as REVISE, HMSR-034 reviewed as REVISE, HMSR-035 reviewed as REVISE, HMSR-036 is the next interactive model review.

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
| HMS-004 | `docs(model-review): validate dependency graph and cycles` | Reconcile graph against persistence/contracts, identify strongly connected components, missing targets, contradictory edges and cross-module boundary concerns. | **Completed** — originally 123 nodes / 181 validated subject edges / 5 cyclic SCCs / 0 invalid subject targets. HMSR-007 later proved `party.PartyRoleAssignment.roleId -> identity.Role` false using stronger Party DDD and HRA-111 FK evidence; current reconciled graph is 180 subject edges pending HMS-006 final reconciliation. |
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
| HMSR-007 | 0 | identity | Role | — | — | 5 | 5 | 0 | REVISE | Completed | `docs(model-review): review identity Role` |
| HMSR-008 | 0 | documents | DocumentStorageObject | — | — | 2 | 6 | 1 | REVISE | Completed | `docs(model-review): review documents DocumentStorageObject` |
| HMSR-009 | 0 | simulation | SimulationModel | — | — | 1 | 5 | 1 | REVISE | Completed | `docs(model-review): review simulation SimulationModel` |
| HMSR-010 | 0 | identity | IdentityProvider | — | — | 4 | 4 | 0 | REVISE | Completed | `docs(model-review): review identity IdentityProvider` |
| HMSR-011 | 0 | identity | Permission | — | — | 3 | 3 | 0 | REVISE | Completed | `docs(model-review): review identity Permission` |
| HMSR-012 | 0 | notification | NotificationTemplate | — | — | 2 | 3 | 2 | REVISE | Completed | `docs(model-review): review notification NotificationTemplate` |
| HMSR-013 | 0 | reporting | ReportDefinition | — | — | 2 | 3 | 1 | REVISE | Completed | `docs(model-review): review reporting ReportDefinition` |
| HMSR-014 | 0 | integration | IntegrationJobRun | — | — | 2 | 2 | 1 | REVISE | Completed | `docs(model-review): review integration IntegrationJobRun` |
| HMSR-015 | 0 | leakdetection | LeakCandidate | — | — | 2 | 2 | 2 | REVISE | Completed | `docs(model-review): review leakdetection LeakCandidate` |
| HMSR-016 | 0 | organization | OperationalScope | — | — | 1 | 2 | 0 | APPROVED | Completed | `docs(model-review): review organization OperationalScope` |
| HMSR-017 | 0 | analytics | AnalyticsDataset | — | — | 1 | 1 | 0 | APPROVED | Completed | `docs(model-review): review analytics AnalyticsDataset` |
| HMSR-018 | 0 | analytics | MetricEvaluationRun | — | — | 1 | 1 | 1 | REVISE | Completed | `docs(model-review): review analytics MetricEvaluationRun` |
| HMSR-019 | 0 | configuration | ConfigurationDefinition | — | — | 1 | 1 | 1 | REVISE | Completed | `docs(model-review): review configuration ConfigurationDefinition` |
| HMSR-020 | 0 | custody | CustodyMeasurementPeriod | — | — | 1 | 1 | 2 | REVISE | Completed | `docs(model-review): review custody CustodyMeasurementPeriod` |
| HMSR-021 | 0 | integrity | PipelineDefect | — | — | 1 | 1 | 1 | REVISE | Completed | `docs(model-review): review integrity PipelineDefect` |
| HMSR-022 | 0 | organization | Position | — | — | 1 | 1 | 0 | REVISE | Completed | `docs(model-review): review organization Position` |
| HMSR-023 | 0 | organization | Shift | — | — | 1 | 1 | 0 | REVISE | Completed | `docs(model-review): review organization Shift` |
| HMSR-024 | 0 | topology | PipelineSystem | — | — | 1 | 1 | 0 | REVISE | Completed | `docs(model-review): review topology PipelineSystem` |
| HMSR-025 | 0 | analytics | AnalyticsInsight | — | — | 0 | 0 | 3 | REVISE | Completed | `docs(model-review): review analytics AnalyticsInsight` |
| HMSR-026 | 0 | analytics | AnalyticsProjectionRun | — | — | 0 | 0 | 1 | REVISE | Completed | `docs(model-review): review analytics AnalyticsProjectionRun` |
| HMSR-027 | 0 | analytics | DigitalTwinReadinessAssessment | — | — | 0 | 0 | 0 | REVISE | Completed | `docs(model-review): review analytics DigitalTwinReadinessAssessment` |
| HMSR-028 | 0 | configuration | FeatureFlag | — | — | 0 | 0 | 0 | REVISE | Completed | `docs(model-review): review configuration FeatureFlag` |
| HMSR-029 | 0 | custody | CustodyDiscrepancy | — | — | 0 | 0 | 1 | REVISE | Completed | `docs(model-review): review custody CustodyDiscrepancy` |
| HMSR-030 | 0 | organization | OrganizationContactPoint | — | — | 0 | 0 | 0 | APPROVED | Completed | `docs(model-review): review organization OrganizationContactPoint` |
| HMSR-031 | 0 | organization | ReportingLine | — | — | 0 | 0 | 0 | REVISE | Completed | `docs(model-review): review organization ReportingLine` |
| HMSR-032 | 0 | risk | RiskMatrixCell | — | — | 0 | 0 | 2 | REVISE | Completed | `docs(model-review): review risk RiskMatrixCell` |
| HMSR-033 | 0 | telemetry | TelemetrySource | — | — | 0 | 0 | 0 | REVISE | Completed | `docs(model-review): review telemetry TelemetrySource` |
| HMSR-034 | 0 | topology | TopologyConnection | — | — | 0 | 0 | 3 | REVISE | Completed | `docs(model-review): review topology TopologyConnection` |
| HMSR-035 | 1 | organization | OrganizationUnit | SCC-01 | organization.OrganizationUnit, organization.OrganizationUnitType | 22 | 51 | 0 | REVISE | Completed | `docs(model-review): review organization OrganizationUnit` |
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
| HMSR-049 | 1 | party | PartyRoleAssignment | — | party.Party | 0 | 0 | 0 | — | Planned | `docs(model-review): review party PartyRoleAssignment` |
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
| subjectAreaId | String | Domain reference | analytics.AnalyticsSubjectArea (read/persistence model) | No | HMSR-017 stronger Analytics DDD + HRA-111 evidence resolves this required same-module reference to `hidra_analytics_subject_area`; target is outside the 123 HMS subject set. |

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
| subjectAreaId | String | Domain reference | analytics.AnalyticsSubjectArea (read/persistence model) | No | HMSR-025 stronger Analytics DDD + HRA-111 evidence resolves this required same-module reference to `hidra_analytics_subject_area`; target is outside the 123 HMS subject set. |
| scopeId | String | Cross-module reference | POLYMORPHIC_ANALYTICAL_SCOPE | No | HMSR-025 Analytics DDD confirms `scopeType + scopeId` as a neutral analytical-scope reference; no single HMS subject edge or cross-module FK is appropriate. |
| severityId | String | Value/catalog dependency | analytics.AnalyticsCatalogEntry (read/persistence model) | No | HMSR-025 Analytics DDD identifies `ANALYTICS_SEVERITY` as Analytics-owned catalog vocabulary; optional catalog reference, no HMS graph edge. |
| sourceProjectionSnapshotId | String | Optional domain reference | analytics.AnalyticsProjectionSnapshot (read/persistence model) | No | HMSR-025 stronger Analytics DDD + persistence evidence resolves the optional source snapshot reference; target is outside the 123 HMS subject set. |
| sourceTrendAnalysisId | String | Optional domain reference | analytics.TrendAnalysis (read/persistence model) | No | HMSR-025 stronger Analytics DDD + persistence evidence resolves the optional source-trend reference; target is outside the 123 HMS subject set. |
| sourceModelRunId | String | Optional domain reference | analytics.AnalyticsModelRun (read/persistence model) | No | HMSR-025 stronger Analytics DDD + persistence evidence resolves the optional source-model-run reference; target is outside the 123 HMS subject set. |

#### analytics.AnalyticsProjectionRun

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| projectionDefinitionId | String | Domain reference | analytics.AnalyticsProjectionDefinition (read/persistence model) | No | HMSR-026 stronger Analytics DDD + HRA-111 evidence resolves this required same-module reference to `hidra_analytics_projection_definition`; target is outside the 123 HMS subject set. |
| correlationId | String | Snapshot/reference-only | TECHNICAL_REFERENCE | No | Technical correlation/request/reference identity. |

#### analytics.DigitalTwinReadinessAssessment

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| scopeId | String | Cross-module reference | POLYMORPHIC_ANALYTICAL_SCOPE | No | HMSR-027 Analytics DDD confirms `scopeType + scopeId` as a neutral analytical-scope reference; no single HMS subject edge or cross-module FK is appropriate. |
| topologySnapshotId | String | Historical snapshot reference | topology.TopologySnapshot (read/persistence model) | No | HMSR-027 stronger Analytics DDD + scalar-reference inventory evidence resolves this required historical reference to the Topology-owned snapshot; preserve snapshot identity semantics and do not add a cross-module DB FK. |

#### analytics.MetricEvaluationRun

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| metricDefinitionVersionId | String | Domain reference | analytics.MetricDefinitionVersion (read/persistence model) | No | HMSR-018 stronger Analytics DDD + HRA-111 evidence resolves this required same-module reference to `hidra_analytics_metric_definition_version`; target is outside the 123 HMS subject set. |
| scopeId | String | Cross-module reference | POLYMORPHIC_ANALYTICAL_SCOPE | No | HMSR-018 Analytics DDD confirms `scopeType + scopeId` may target Topology, Organization, Product, or a module-defined analytical scope; no single HMS subject edge or cross-module FK is appropriate. |
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
| namespaceId | String | Domain reference | configuration.ConfigurationNamespace (read/persistence model) | No | HMSR-019 stronger Configuration DDD + HRA-111 evidence resolves this required same-module reference to `hidra_configuration_namespace`; target is outside the 123 HMS subject set. |

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
| reconciliationId | String | Domain reference | custody.CustodyReconciliation (read/persistence model) | No | HMSR-029 stronger Custody DDD + HRA-111 evidence resolves this required same-module reference to `hidra_custody_reconciliation`; target is outside the 123 HMS subject set. |
| discrepancyTypeId | String | Value/catalog dependency | custody.CustodyCatalogEntry (read/persistence model) | No | HMSR-029 HRA-111 evidence resolves this required same-module controlled-value reference to `hidra_custody_catalog_entry`; target is outside the HMS subject set. |
| quantityUnitId | String | Optional value/catalog dependency | custody.CustodyCatalogEntry (read/persistence model) | No | HMSR-029 stronger same-module quantity-unit persistence pattern resolves this optional controlled-value reference to the Custody catalog; no HMS graph edge. |
| assignedActorId | String | Cross-module reference | IDENTITY_ACTOR | No | Actor reference; no Actor subject model in the 123-model set. |

#### custody.CustodyMeasurementPeriod

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| agreementId | String | Domain reference | custody.CustodyAgreement (read/persistence model) | No | HMSR-020 stronger Custody DDD + HRA-111 evidence resolves this required same-module reference to `hidra_custody_agreement`; target is outside the 123 HMS subject set. |
| transferPointId | String | Domain reference | custody.CustodyTransferPoint (read/persistence model) | No | HMSR-020 stronger Custody DDD + HRA-111 evidence resolves this required same-module reference to `hidra_custody_transfer_point`; target is outside the 123 HMS subject set. |
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
| storageProviderId | String | Value/catalog dependency | DOCUMENT_STORAGE_PROVIDER | No | HMSR-008 stronger Documents DDD + HRA-111 evidence resolves this to Documents-owned `DocumentCatalogEntry` / `hidra_documents_catalog_entry`, outside the 123 HMS subject set. |

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
| jobDefinitionId | String | Domain reference | integration.IntegrationJobDefinition (read/persistence model) | No | HMSR-014 stronger Integration DDD + HRA-111 evidence resolves this to `hidra_integration_job_definition`; target is outside the 123 HMS subject set. |
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
| defectTypeId | String | Value/catalog dependency | integrity.IntegrityCatalogEntry (read/persistence model) | No | HMSR-021 stronger HRA-111 evidence resolves this required same-module catalog reference to `hidra_integrity_catalog_entry`; target is outside the 123 HMS subject set. |
| topologyAssetId | String | Cross-module reference | POLYMORPHIC | No | Target is selected by companion `topologyAssetTypeCode`; Integrity retains a neutral Topology reference and no single subject-model edge. |
| sourceFindingId | String | Optional domain reference | integrity.InspectionFinding (read/persistence model) | No | HMSR-021 stronger Integrity DDD + persistence evidence resolves this optional same-module provenance reference to `hidra_integrity_inspection_finding`; target is outside the 123 HMS subject set and no FK currently protects the non-null value. |

#### leakdetection.LeakCandidate

| Field | Declared type | Classification | Resolved target | Graph edge | Notes |
|---|---|---|---|:---:|---|
| id | String | Self identifier | — | No | Primary identity of the current model. |
| runId | String | Domain reference | leakdetection.LeakDetectionRun (read/persistence model) | No | HMSR-015 stronger Leak Detection DDD/persistence evidence resolves this optional reference to `hidra_leak_detection_run`; target is outside the 123 HMS subject set and currently has no candidate-side FK. |
| profileId | String | Domain reference | leakdetection.LeakDetectionProfile (read/persistence model) | No | HMSR-015 stronger Leak Detection DDD + HRA-111 evidence resolves this required reference to `hidra_leak_detection_profile`; target is outside the 123 HMS subject set. |
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
| templateTypeId | String | Value/catalog dependency | TEMPLATE_TYPE | No | HMSR-012 stronger Notification DDD + HRA-111 evidence resolves this to Notification-owned `NotificationCatalogEntry` / `hidra_notification_catalog_entry`, outside the 123 HMS subject set. |
| categoryId | String | Value/catalog dependency | NOTIFICATION_CATEGORY | No | HMSR-012 stronger Notification DDD resolves this to the Notification-owned `NOTIFICATION_CATEGORY` catalog family; no HMS subject edge. |
| defaultChannelId | String | Domain reference | notification.NotificationChannel (read/persistence model) | No | HMSR-012 stronger Notification DDD/persistence evidence resolves the optional default channel to Notification-owned `hidra_notification_channel`; target is outside the 123 HMS subject set. |

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
| targetId | String | Typed/polymorphic domain reference | GOVERNED_OPERATIONAL_SCOPE_TARGET | No | HMSR-016 confirms the target namespace is selected by `OperationalScopeType`; GLOBAL has no target, ORGANIZATION_UNIT resolves locally, Topology-owned types resolve through the exported Topology contract, and CUSTOM is rejected. No single HMS subject edge or cross-module DB FK. |

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
| unitTypeId | String | Domain reference | organization.OrganizationUnitType | Yes | HMSR-035 aligns HMS-003 with stronger HMSR-001 + ORG-046 evidence: required same-module subject reference protected by `fk_org_unit_type`. |
| parentUnitId | String | Optional self domain reference | organization.OrganizationUnit | Yes | HMSR-035 aligns HMS-003 with the validated SCC/self-reference graph: optional parent hierarchy reference protected by `fk_org_unit_parent`; whole-hierarchy cycle prevention remains a semantic obligation. |

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
| source | ReportingSubjectReference | Typed polymorphic domain reference | organization.Employee / organization.Position / organization.OrganizationUnit | No | HMSR-031 stronger Organization roadmap/domain evidence resolves the governed same-module subject set; target is selected by ReportingSubjectType, so no single HMS graph edge is added. |
| target | ReportingSubjectReference | Typed polymorphic domain reference | organization.Employee / organization.Position / organization.OrganizationUnit | No | HMSR-031 stronger Organization roadmap/domain evidence resolves the governed same-module subject set; target is selected by ReportingSubjectType, so no single HMS graph edge is added. |

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
| roleId | String | Value/catalog dependency | PARTY_ROLE_CATALOG | No | HMSR-007 stronger Party DDD + HRA-111 evidence resolves this to Party-owned `PartyRole` (`hidra_party_role`), not `identity.Role`; no HMS subject edge. |

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
| reportCategoryId | String | Value/catalog dependency | REPORT_CATEGORY | No | HMSR-013 stronger Reporting DDD + HRA-111 evidence resolves this to Reporting-owned `ReportCatalogEntry` / `hidra_reporting_catalog_entry`, outside the 123 HMS subject set. |
| currentTemplateVersionId | String | Domain reference | reporting.ReportTemplateVersion (read/persistence model) | No | HMSR-013 stronger Reporting DDD/persistence evidence resolves this to the current Reporting template-version record; target is outside the 123 HMS subject set and no cross-module edge is introduced. |

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
| riskMatrixId | String | Domain reference | risk.RiskMatrix (read/persistence model) | No | HMSR-032 stronger Risk DDD + HRA-111 evidence resolves the required same-module matrix reference; target is outside the 123 HMS subject set. |
| likelihoodLevelId | String | Value/catalog dependency | risk.RiskCatalogEntry (RISK_LIKELIHOOD_LEVEL) | No | HRA-111 protects row existence in the Risk catalog; HMSR-032 records the DDD-required likelihood-level catalog family. |
| consequenceLevelId | String | Value/catalog dependency | risk.RiskCatalogEntry (RISK_CONSEQUENCE_LEVEL) | No | HRA-111 protects row existence in the Risk catalog; HMSR-032 records the DDD-required consequence-level catalog family. |
| ratingId | String | Domain/catalog reference | risk.RiskRating (read/persistence model) | No | HMSR-032 stronger Risk DDD + HRA-111 evidence resolves the required rating reference; target is outside the 123 HMS subject set. |

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
| modelTypeId | String | Value/catalog dependency | SIMULATION_MODEL_TYPE | No | HMSR-009 stronger Simulation DDD + HRA-111 evidence resolves this to Simulation-owned `SimulationCatalogEntry` / `hidra_simulation_catalog_entry`, outside the 123 HMS subject set. |
| topologyScopeId | String | Cross-module reference | POLYMORPHIC_TOPOLOGY_SCOPE | No | HMSR-009 stronger Simulation DDD resolves the target namespace through companion `topologyScopeType`; no single HMS subject target and no cross-module FK. |

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
| sourceTypeId | String | Value/catalog dependency | telemetry.TelemetryCatalogEntry (SOURCE_TYPE) | No | HMSR-033 stronger Telemetry DDD + HRA-111 evidence resolves this required source-type reference to `hidra_telemetry_type_catalog`; family and active-entry semantics remain to be enforced. |
| protocolId | String | Value/catalog dependency | telemetry.TelemetryCatalogEntry (PROTOCOL) | No | HMSR-033 stronger Telemetry DDD + HRA-111 evidence resolves this required protocol reference to `hidra_telemetry_type_catalog`; family and active-entry semantics remain to be enforced. |

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
| fromNodeId | String | Domain reference | topology.TopologyNode (read/persistence model) | No | HMSR-034 stronger HMS-005 + scalar-integrity + HRA-111 evidence resolves the required source-node reference; target is outside the 123 HMS subject set. |
| toNodeId | String | Domain reference | topology.TopologyNode (read/persistence model) | No | HMSR-034 stronger HMS-005 + scalar-integrity + HRA-111 evidence resolves the required target-node reference; target is outside the 123 HMS subject set. |
| pipelineSegmentId | String | Optional domain reference | topology.PipelineSegment (read/persistence model) | No | HMS-005 explicitly confirms this optional same-module prerequisite; no HMS graph edge. Populated-reference integrity is not currently protected. |

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
**HMS-004 corrections at completion:** 1 removed, 17 added  
**HMSR-007 late correction:** 1 additional false cross-module edge removed (`party.PartyRoleAssignment.roleId -> identity.Role`)  
**Current reconciled subject edges:** 180  
**Same-module edges:** 122  
**Cross-module stable/domain edges:** 58  
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
false subject edges removed in HMS-004    = 1
repository-supported subject edges added  = 17
late false edge removed in HMSR-007       = 1
current reconciled subject edges          = 180
same-module subject edges                 = 122
cross-module subject edges                = 58
invalid/missing subject targets           = 0
cyclic SCCs                               = 5
remaining unresolved non-graph candidates = 120
models with zero subject-graph dependencies = 34
models with zero subject-graph dependents   = 59
```

The two zero-degree counts above are structural diagnostics only. They are **not** the HMS review order because HMS-005 must first collapse SCCs and account for external prerequisite flags.

### 12.8 HMS-004 outcome

- The 123-node subject graph is internally target-valid: every admitted edge resolves to one HMS subject model.
- HMS-004 removed one false technical self-edge and added seventeen repository-supported edges; HMSR-007 later removed the false cross-module `party.PartyRoleAssignment.roleId -> identity.Role` edge after stronger Party DDD and HRA-111 FK evidence proved `roleId` targets Party-owned `PartyRole`.
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

## 20. HMSR-007 — identity.Role review

**Decision:** REVISE  
**Review code:** HMSR-007  
**Dependency level:** 0  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 5 — `identity.ExternalRoleMapping`, `identity.GroupRoleGrant`, `identity.RolePermissionGrant`, `identity.AuthorizationDelegationGrant`, `identity.UserRoleGrant`  
**Transitive dependents:** 5  
**Unresolved/non-subject references:** 0

### 20.1 Semantic role and ordering rationale

`Role` is the Identity-owned reusable authorization package that groups permissions for assignment to users, groups, external mappings and delegation paths. It is a security/authorization concept, not an Organization position/responsibility and not a Party business role.

It remains Level 0 because it has no upstream HMS subject-model dependency. Its five confirmed subject dependents all consume the role identity through explicit Identity-owned grant/mapping relationships.

The Identity boundary remains:

- Identity owns users, security groups, roles, permissions, grants, provider mappings and authorization decisions;
- Organization owns employees, positions, units and reporting structures;
- Party owns external counterparties and **PartyRole** business classifications;
- Workflow may approve sensitive grants but does not own Role;
- Audit records evidence but does not own Role truth.

### 20.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable Identity Role identifier and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique role business/security code. |
| `nameAr` | `String` | Optional | Arabic display label. |
| `nameFr` | `String` | Optional | French display label. |
| `nameEn` | `String` | Optional | English display label. |
| `description` | `String` | Optional | Human-readable role purpose. |
| `roleType` | `RoleType` | Mandatory | Identity role classification: `BUSINESS`, `SYSTEM`, `ADMIN`, `EXTERNAL_MAPPED`, or `BREAK_GLASS`. |
| `status` | `RoleStatus` | Mandatory | Role lifecycle: `ACTIVE`, `DISABLED`, or `DEPRECATED`. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The domain constructor already rejects blank `id` and `code`, rejects null `roleType` and `status`, trims labels/description, and converts blank optional text to `null`.

The current Identity DDD explicitly marks all three localized names optional. HMSR-007 therefore does not invent a mandatory French-name rule for Role.

### 20.3 Role type and lifecycle semantics

`RoleType` is a stable Identity authorization classification and is correctly modeled as an enum in the current target:

- `BUSINESS` — reusable business authorization role;
- `SYSTEM` — platform/system role;
- `ADMIN` — administrative authority package;
- `EXTERNAL_MAPPED` — internally governed role intended for explicit external-claim mapping;
- `BREAK_GLASS` — emergency/high-governance role classification.

`RoleStatus` separates lifecycle availability from role type. Current administrator security evidence explicitly requires the persisted `HIDRA_ADMIN` role to be `ACTIVE` and of type `ADMIN` before it can enable an administrator grant.

HMSR-007 found no repository evidence requiring a different enum/catalog representation for these role/status values.

### 20.4 Confirmed downstream dependency evidence

The corrected subject graph retains five direct Role dependents:

- `ExternalRoleMapping.roleId -> identity.Role`;
- `GroupRoleGrant.roleId -> identity.Role`;
- `RolePermissionGrant.roleId -> identity.Role`;
- `AuthorizationDelegationGrant.roleId -> identity.Role` when role-based delegation is used;
- `UserRoleGrant.roleId -> identity.Role`.

These are Identity-owned authorization relationships and remain valid subject edges.

### 20.5 Late graph correction — Party business role is not Identity Role

HMSR-007 found a false cross-module edge that survived HMS-004:

```text
party.PartyRoleAssignment.roleId -> identity.Role
```

Stronger repository evidence disproves it:

- the Party DDD explicitly owns `PartyRole` and `PartyRoleAssignment`;
- the Party DDD explicitly says Identity `Role` remains in Identity and Party is not Identity;
- `PartyRoleJpaEntity` persists the Party-owned role catalog in `hidra_party_role`;
- HRA-111 installs `fk_hra111_party_023` from `hidra_party_role_assignment.role_id` to `hidra_party_role(id)`;
- the Party assignment application service accepts the Party role ID without any Identity contract/repository dependency.

Therefore `PartyRoleAssignment.roleId` is a **Party-owned value/catalog dependency**, not a cross-module dependency on `identity.Role`.

This HMSR task corrects the roadmap classification, removes that validated-edge row, updates `HMSR-049` to depend only on `party.Party`, and updates Role's direct/transitive dependent counts from 6 to 5.

The fixed edge does not change the frozen HMSR task codes or dependency levels: `identity.Role` remains Level 0 and `PartyRoleAssignment` remains Level 1 because it still depends on `party.Party`. HMS-006 must nevertheless recompute/reconcile final graph totals; the current reconciled count is 180 subject edges, with 122 same-module and 58 cross-module edges.

### 20.6 Persistence and application consistency

The live Role domain and JPA models agree on all 10 declared components.

The base Identity migration makes `id`, `code`, `role_type`, `status`, `created_at`, and `updated_at` non-null; localized names and description are nullable.

`RoleRepositoryPort` exposes:

```text
save
findById
findByCode
```

and the Spring Data repository implements code lookup through `findFirstByCode`.

The controlled local-administrator bootstrap uses `findByCode("HIDRA_ADMIN")` before creating the bootstrap administrator role and refuses an existing non-ACTIVE administrator role.

However, the general `IdentityAdministrationCommandApplicationService.createRole` path constructs and saves a new Role without checking `findByCode`.

### 20.7 Required revision

The Role model is otherwise semantically coherent, but one active Identity DDD invariant is not consistently enforced:

1. **Unique Role code:** the Identity DDD explicitly defines `Role.code` as unique and recommends `uk_identity_role_code`. The live Flyway schema has only a non-unique index `ix_hidra_identity_role_code`; no later unique constraint was found. The general create-role application path does not call `RoleRepositoryPort.findByCode` before save. `RoleJpaRepository.findFirstByCode` also tolerates duplicate rows rather than making duplicates impossible.

Global uniqueness belongs at the application/database boundary; it must not be implemented as a repository lookup inside the domain record constructor.

HMSR-007 does not change production Java, JPA, Flyway, application/API contracts, tests, or database data.

### 20.8 Authorization and operational interpretation

For Hidra/SONATRACH TRC, Identity Role represents a governed authorization package such as an operational reviewer, administrator, telemetry approver or emergency-access role. It must remain distinct from:

- an Organization position such as department head or dispatcher;
- a Party business role such as supplier, manufacturer, owner or shipper;
- a Workflow assignment rule;
- an operational responsibility/scope record.

Authorization must be based on role/permission/grant semantics and current scope/status validity rather than hard-coded role-name checks. External provider claims must map explicitly into internal Identity roles; they do not become authoritative roles by themselves.

### 20.9 Review conclusion

**REVISE.** `identity.Role` has the correct bounded-context ownership, fields, type/status vocabulary and five true subject dependents. HMSR-007 also removes one false Party-to-Identity role edge. The remaining blocker to APPROVED is the repository-defined unique Role-code invariant, which is not enforced consistently by the live create path and database schema.

HMS reconciliation must retain the Role-code uniqueness correction and the late graph correction until an explicitly authorized Identity implementation task resolves the uniqueness invariant and HMS-006 recomputes the final dependency totals.

## 21. HMSR-008 — documents.DocumentStorageObject review

**Decision:** REVISE  
**Review code:** HMSR-008  
**Dependency level:** 0  
**Bounded context:** documents  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `documents.DocumentVersion` and `reporting.ReportOutputArtifact`  
**Transitive dependents:** 6  
**Unresolved/non-subject references:** 1 — `storageProviderId`, now semantically resolved to a Documents-owned catalog prerequisite outside the 123 HMS subjects

### 21.1 Semantic role and ordering rationale

`DocumentStorageObject` is the Documents-owned metadata pointer to one physical binary object in a storage backend. It carries storage-provider identity, opaque object location metadata, size/content metadata, checksum/integrity metadata, encryption-reference metadata and storage lifecycle state.

It is Level 0 because none of its prerequisites are another HMS subject model. Its only upstream semantic prerequisite is the Documents-owned storage-provider catalog, which is outside the 123 HMS subject population. `DocumentVersion` depends on the storage object through `storageObjectId`, and Reporting may reference a storage object for generated output artifacts.

The bounded-context rule remains explicit: Documents owns document/storage metadata, while the binary bytes live in object storage, DMS, archive or controlled file storage. Storage credentials, secret key material and signed URLs must not become relational business metadata.

### 21.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable storage-object identifier and persistence primary key. |
| `storageProviderId` | `String` | Mandatory | Documents-owned storage-provider catalog reference; semantically belongs to `DOCUMENT_STORAGE_PROVIDER`. |
| `bucketOrContainer` | `String` | Optional | Backend bucket, container, share, archive container or equivalent logical location. |
| `objectKey` | `String` | Mandatory | Provider-specific opaque object key/path. The Documents DDD explicitly forbids credentials or signed URLs in this field. |
| `objectUri` | `String` | Optional | Optional non-secret URI/reference to the stored object. |
| `encrypted` | `boolean` | Mandatory persisted state | Whether the object is stored encrypted. |
| `encryptionKeyReference` | `String` | Optional | External key reference only; never encryption key material or secret credentials. |
| `contentLengthBytes` | `long` | Mandatory | Stored binary length in bytes. |
| `contentType` | `String` | Mandatory | Media/content type persisted with the storage object. |
| `checksumAlgorithm` | `String` | Mandatory | Algorithm used for integrity checksum, currently supplied as `SHA-256` by the local adapter. |
| `checksumValue` | `String` | Mandatory | Integrity checksum value for the stored object. |
| `storageStatus` | `DocumentStorageStatus` | Mandatory | Storage lifecycle: `AVAILABLE`, `QUARANTINED`, `ARCHIVED`, `MISSING`, or `DELETED_LOGICAL`. |
| `createdAt` | `Instant` | Mandatory in persistence | Storage-object metadata creation timestamp. |
| `verifiedAt` | `Instant` | Optional | Last integrity-verification timestamp. |

The current domain constructor already rejects blank `id`, `storageProviderId`, and `objectKey`, rejects null `storageStatus`, trims string values and converts blank optional strings to `null`.

### 21.3 Storage-provider dependency resolution

HMS-003 left `storageProviderId` unresolved because no one of the 123 subject models was a defensible target. Stronger repository evidence now resolves the semantic target without adding a subject-graph edge:

- the Documents DDD identifies `storageProviderId` as a catalog/provider reference;
- `DOCUMENT_STORAGE_PROVIDER` is a recommended Documents catalog family;
- `DocumentCatalogEntry` is retained as a Documents read/persistence model outside the HMS subject set;
- HRA-111 installs `fk_hra111_documents_014`;
- that FK points `hidra_documents_storage_object.storage_provider_id` to `hidra_documents_catalog_entry(id)` with `ON DELETE RESTRICT`.

Therefore `storageProviderId` is a **Documents-owned value/catalog dependency**, not an unresolved subject-model relationship.

### 21.4 Persistence and binary-storage boundary

The live domain and JPA models agree on all 14 declared components.

The base Documents migration makes these columns non-null:

```text
id
storage_provider_id
object_key
encrypted
content_length_bytes
content_type
checksum_algorithm
checksum_value
storage_status
created_at
```

while bucket/container, object URI, encryption key reference and verification timestamp remain nullable.

The current `DocumentContentTransferService` obtains physical-storage metadata from `DocumentBinaryStoragePort.StoredBinary`, creates `DocumentStorageObject` with status `AVAILABLE`, defaults a missing upload content type to `application/octet-stream`, and persists the storage object before creating the corresponding DocumentVersion.

The local filesystem adapter currently:

- uses provider value `local-filesystem`;
- stores objects under a controlled Documents root;
- uses the generated storage-object/reference ID as the object key;
- calculates byte length while copying;
- calculates a SHA-256 checksum;
- emits no encryption key reference because local storage is currently unencrypted.

Those implementation details are valid local-adapter behavior, but they do not by themselves establish the catalog identity contract required by the database FK.

### 21.5 Required revisions

The model role and dependency direction are correct, but the live implementation does not consistently enforce all repository-defined storage semantics strongly enough for APPROVED status.

1. **Storage-provider catalog identity mismatch / missing validation:** HRA-111 requires `storageProviderId` to resolve to `hidra_documents_catalog_entry(id)`, and the DDD assigns it to `DOCUMENT_STORAGE_PROVIDER`. The current local adapter returns the hard-coded value `local-filesystem`. No current seed/catalog evidence found during HMSR-008 proves that `local-filesystem` is an existing catalog-entry **ID** or that it belongs to the `DOCUMENT_STORAGE_PROVIDER` family. The upload path performs no catalog-family/active-entry validation before persistence.
2. **Required storage-integrity metadata contract:** the DDD and schema require `contentType`, `checksumAlgorithm` and `checksumValue`. The current local adapter/service produce them, but `DocumentBinaryStoragePort.StoredBinary` has no constructor validation and the domain record itself does not reject null/blank checksum/content-type values. A future storage adapter can therefore satisfy the Java interface while producing semantically invalid metadata that fails only at persistence or propagates invalid state.
3. **Required creation metadata boundary:** `createdAt` is required by the DDD/JPA/schema but is not guarded by the domain constructor. The current service supplies it, so this is not a current local-upload failure, but the target baseline needs one deliberate enforcement boundary rather than relying on every caller to remember the persistence constraint.
4. **Secret/opaque-object-key policy:** the DDD explicitly says object keys must not contain credentials or signed URLs and encryption-key metadata must be reference-only. The current local adapter is safe because it derives `objectKey` from an opaque generated ID, but the outbound storage contract does not state or validate the same guarantee for other providers. Any S3/MinIO/SharePoint/DMS adapter must return stable non-secret metadata rather than pre-signed/credential-bearing values.

These corrections belong at the appropriate application/storage-adapter/database boundary. HMSR-008 does not require secret-detection heuristics inside the domain record, and it does not invent storage-provider catalog rows.

No production Java, JPA, Flyway, application/API contract, tests, catalog data or binary-storage data is changed by this review.

### 21.6 Lifecycle and integrity interpretation

`DocumentStorageStatus` is correctly represented as a stable technical enum:

- `AVAILABLE` — binary is expected to be retrievable;
- `QUARANTINED` — binary is retained but unavailable to normal consumers pending security/integrity handling;
- `ARCHIVED` — moved/retained in archival storage semantics;
- `MISSING` — metadata exists but physical content cannot currently be resolved;
- `DELETED_LOGICAL` — logically deleted according to Documents retention/governance rules.

Current download orchestration correctly requires both metadata status `AVAILABLE` and physical-adapter availability before returning content.

Checksum metadata is evidence for storage integrity; it does not by itself replace document-version approval, audit evidence, digital signatures, retention policy or workflow governance.

### 21.7 Operational interpretation

For SONATRACH/TRC operations, `DocumentStorageObject` may point to the physical bytes of pipeline drawings, inspection reports, certificates, operating procedures, maintenance manuals, HSE evidence, custody documentation or generated reports.

The storage object is deliberately infrastructure-facing metadata. Business modules should reference Document/DocumentVersion identities rather than learn bucket names, filesystem paths or provider credentials. Reporting may retain a stable storage-object reference for generated artifacts, but Documents remains owner of storage metadata.

### 21.8 Review conclusion

**REVISE.** `DocumentStorageObject` is the correct Documents-owned physical-storage metadata model and its two subject dependents are appropriate. The previously unresolved `storageProviderId` is now resolved as a Documents-owned catalog prerequisite outside the subject graph.

The target baseline cannot mark it APPROVED until storage-provider catalog identity/family semantics and required integrity-metadata enforcement are made consistent across the DDD, database FK, storage port and adapters. HMS reconciliation must retain these obligations for an explicitly authorized Documents correction task.

## 22. HMSR-009 — simulation.SimulationModel review

**Decision:** REVISE  
**Review code:** HMSR-009  
**Dependency level:** 0  
**Bounded context:** simulation  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `simulation.SimulationScenario` through `modelId`  
**Transitive dependents:** 5  
**Unresolved/non-subject references:** 1 — `topologyScopeId`, now resolved as a typed/polymorphic Topology reference with no single HMS subject target

### 22.1 Semantic role and ordering rationale

`SimulationModel` is the Simulation-owned reusable model-family definition. It identifies a simulation model by business code, localized names, model type, default Topology scope and technical lifecycle state. Executable immutable configuration belongs to `SimulationModelVersion`; scenarios reference the stable model identity and, when frozen for execution, a specific model version.

It is Level 0 because none of its prerequisites are another HMS subject model. `modelTypeId` is a Simulation-owned catalog prerequisite outside the 123 HMS subjects, while `topologyScopeId` is a typed cross-module reference selected by `topologyScopeType`.

Simulation remains decision-support only. It does not own physical Topology, measured Telemetry facts, approved Planning state, Monitoring deviation truth, Workflow approval state or Audit evidence.

### 22.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable SimulationModel identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique business code for the reusable model definition. |
| `nameAr` | `String` | Optional | Arabic model display name. |
| `nameFr` | `String` | Mandatory | French model display name in the target Simulation contract and persistence schema. |
| `nameEn` | `String` | Optional | English model display name. |
| `modelTypeId` | `String` | Mandatory | Simulation-owned catalog reference in family `SIMULATION_MODEL_TYPE`. |
| `topologyScopeType` | `String` | Mandatory | Discriminator for the default Topology target namespace; target vocabulary includes `PIPELINE_SYSTEM`, `PIPELINE`, `SEGMENT_GROUP`, `FACILITY_NETWORK`. |
| `topologyScopeId` | `String` | Optional | Optional Topology scope reference whose target meaning is selected by `topologyScopeType`. |
| `status` | `SimulationModelStatus` | Mandatory | Technical lifecycle: `DRAFT`, `ACTIVE`, or `RETIRED`. |
| `description` | `String` | Optional | Human-readable model purpose. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The current constructor already rejects blank `id`, `code`, and `modelTypeId`, rejects null `status`, trims textual values and converts blank optional strings to `null`.

### 22.3 Model-type dependency resolution

Current repository evidence resolves `modelTypeId` to the Simulation-owned `SIMULATION_MODEL_TYPE` catalog:

- the Simulation data definition identifies `SIMULATION_MODEL_TYPE` as the model classification family;
- `SimulationCatalogEntry` is a Simulation read/persistence model outside the 123 HMS subjects;
- HRA-111 installs `fk_hra111_simulation_016`;
- that FK points `hidra_simulation_model.model_type_id` to `hidra_simulation_catalog_entry(id)` with `ON DELETE RESTRICT`.

Thus `modelTypeId` is a value/catalog dependency, not a subject edge. The FK proves row existence only; it does not prove that the row belongs to `SIMULATION_MODEL_TYPE` or is usable.

### 22.4 Topology-scope reference resolution

The Simulation target definition gives `topologyScopeId` the missing discriminator semantics through the pair `topologyScopeType + topologyScopeId`. Because the discriminator can identify different Topology namespaces, this is a typed/polymorphic cross-module reference, not one direct edge to `PipelineSystem`, `Pipeline`, `Facility`, or another single HMS subject.

It therefore remains outside the 123-node graph. Simulation must not introduce a cross-module database FK for this typed reference; validation belongs through a Topology-owned lookup/contract that interprets both fields together.

### 22.5 Model/version boundary

The target design distinguishes stable model identity from immutable executable configuration. `SimulationModelVersion` owns solver profile, model-definition hash, compatibility marker and version lifecycle. Completed runs must reference an immutable model version rather than only the mutable model definition.

`SimulationModelVersion` is outside the 123 HMS subjects, so it does not add a graph edge. `SimulationScenario` remains the one direct HMS dependent through `modelId`.

### 22.6 Persistence and application consistency

The live domain and JPA models agree on all 12 declared components. The base migration makes `id`, `code`, `name_fr`, `model_type_id`, `topology_scope_type`, `status`, `created_at`, and `updated_at` non-null.

The current `SimulationApplicationService.createSimulationModel` creates a generated ID, copies command values, initializes status to `DRAFT`, supplies timestamps and saves directly through `SimulationModelRepositoryPort`.

The repository port exposes only `save` and `findById`. No code uniqueness lookup, Simulation catalog validation or Topology-scope validation contract is used by the current create path.

### 22.7 Required revisions

The aggregate role and dependency direction are correct, but the target semantics are not enforced consistently enough for APPROVED status.

1. **Unique model code:** the Simulation target definition explicitly marks `code` unique. The live schema has only non-unique `ix_hidra_simulation_model_code`, and the repository/application create path has no demonstrated uniqueness guard.
2. **Mandatory French name:** `nameFr` is required by target semantics and `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank input to `null`.
3. **Mandatory and governed topology scope type:** `topologyScopeType` is required by target semantics and JPA/schema but is not guarded by the domain/application path; no demonstrated validation restricts it to the documented scope vocabulary.
4. **Topology scope pair validation:** when `topologyScopeId` is supplied, the application must validate `topologyScopeType + topologyScopeId` through a Topology-owned contract. No single subject target may be guessed and no current validation is demonstrated.
5. **Model-type catalog-family validation:** HRA-111 proves only that `modelTypeId` points to some Simulation catalog row. Creation does not demonstrate that it belongs to `SIMULATION_MODEL_TYPE` or satisfies the applicable active/reference policy.
6. **Required timestamps:** `createdAt` and `updatedAt` are required by persistence semantics but not guarded by the domain constructor. The current service supplies both; a deliberate enforcement boundary must remain part of the final baseline.

These corrections belong at the appropriate domain/application/database boundary. Repository lookups for catalog or Topology references must remain outside the domain record constructor.

HMSR-009 does not modify production Java, JPA, Flyway, API/application contracts, tests, catalog data or Topology data.

### 22.8 Lifecycle and operational interpretation

`SimulationModelStatus` is correctly represented as a technical lifecycle enum: `DRAFT`, `ACTIVE`, `RETIRED`. Historical model identities remain valid after retirement, while new scenarios should use governed active model/version combinations.

For SONATRACH/TRC pipeline operations, a SimulationModel may represent hydraulic, optimization, capacity/what-if or incident-response analytical behavior over a pipeline system, pipeline, segment group or facility network. It never becomes the authority for the physical network.

An optimized configuration remains a Simulation recommendation/candidate until exported to the owning module, approved through Workflow where required, recorded by Audit and executed through authorized operational procedures outside direct Simulation control.

### 22.9 Review conclusion

**REVISE.** `SimulationModel` is the correct foundational Simulation model and its dependency direction is sound. `modelTypeId` is now resolved to `SIMULATION_MODEL_TYPE`, while `topologyScopeId` is resolved as a typed/polymorphic Topology reference with no single HMS target.

The target baseline cannot mark it APPROVED while model-code uniqueness, required French name, topology-scope discriminator/pair validation and model-type catalog-family validation remain unenforced. HMS reconciliation must retain these obligations until an explicitly authorized Simulation correction task resolves them or the target semantics are explicitly changed.

## 23. HMSR-010 — identity.IdentityProvider review

**Decision:** REVISE  
**Review code:** HMSR-010  
**Dependency level:** 0  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 4 — `identity.ExternalRoleMapping`, `identity.AuthenticationEvent`, `identity.HidraPrincipal`, `identity.LoginSession`  
**Transitive dependents:** 4  
**Unresolved/non-subject references:** 0

### 23.1 Semantic role and ordering rationale

`IdentityProvider` is the Identity-owned configuration/master record for one authentication or external identity source. It represents providers such as LOCAL, LDAP, Active Directory, OIDC, OAuth2, SAML2, Keycloak, Azure AD / Entra ID and Okta while preserving Hidra-owned authorization as the final authority.

It is Level 0 because it has no upstream dependency on another HMS subject model. Its subject dependents use the provider identity for external-role mapping, normalized principals, login-session provenance and authentication-event traceability.

Several Identity read/persistence models outside the 123 HMS subjects also depend on it, including `ExternalIdentity`, `ExternalGroupMapping`, `ExternalPermissionMapping` and `IdentitySynchronizationJob`. Those dependencies do not change the HMS subject graph.

### 23.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable provider identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable provider business/configuration code; current Identity DDD requires uniqueness. |
| `name` | `String` | Mandatory | Human-readable provider display name. |
| `providerType` | `ProviderType` | Mandatory | Provider technology/source classification: `LOCAL`, `LDAP`, `ACTIVE_DIRECTORY`, `OIDC`, `OAUTH2`, `SAML2`, `KEYCLOAK`, `AZURE_AD`, or `OKTA`. |
| `issuerUri` | `String` | Provider-specific optional | Issuer URI for OIDC/SAML-style providers; current OIDC normalization resolves providers by issuer. |
| `authorizationEndpoint` | `String` | Provider-specific optional | OIDC/OAuth authorization endpoint metadata. |
| `tokenEndpoint` | `String` | Provider-specific optional | OIDC/OAuth token endpoint metadata. |
| `jwksUri` | `String` | Provider-specific optional | JWK-set endpoint metadata. |
| `directoryBaseDn` | `String` | Provider-specific optional | LDAP/AD directory base DN metadata. |
| `userSearchBase` | `String` | Provider-specific optional | LDAP/AD user-search base metadata. |
| `groupSearchBase` | `String` | Provider-specific optional | LDAP/AD group-search base metadata. |
| `usernameAttribute` | `String` | Provider-specific optional | Provider attribute used for username mapping. |
| `emailAttribute` | `String` | Provider-specific optional | Provider attribute used for email mapping. |
| `displayNameAttribute` | `String` | Provider-specific optional | Provider attribute used for display-name mapping. |
| `externalIdAttribute` | `String` | Provider-specific optional | Stable external identity attribute such as objectGUID/objectId/sub/NameID. |
| `groupMembershipAttribute` | `String` | Provider-specific optional | External group/role membership attribute such as `memberOf`, `groups`, or `roles`. |
| `syncEnabled` | `boolean` | Mandatory persisted state | Whether synchronization is enabled for the provider. |
| `justInTimeProvisioningEnabled` | `boolean` | Mandatory persisted state | Whether login-time creation/update may be performed under provider/mapping policy. |
| `status` | `IdentityProviderStatus` | Mandatory | Provider lifecycle: `ACTIVE`, `INACTIVE`, `FAILED`, or `DEPRECATED`. |
| `metadata` | JSON text / `jsonb` persistence | Optional | Non-secret provider metadata and mapping hints only. |
| `secretReference` | `String` | Optional | Reference to a vault/platform secret; never the secret value itself. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The domain constructor already rejects blank `id` and `code`, rejects null `providerType` and `status`, trims textual values, and converts blank optional text to `null`.

### 23.3 Provider taxonomy versus authentication protocol

The existing provider taxonomy is intentional and must not be duplicated.

`ProviderType` identifies the configured source technology and distinguishes, for example, LDAP from Active Directory and OIDC from vendor-specific providers. `AuthenticationProtocol` is a separate authentication-event/session classification.

The current session lifecycle maps:

- `LOCAL -> LOCAL`;
- `LDAP` and `ACTIVE_DIRECTORY -> LDAP`;
- `OIDC`, `KEYCLOAK`, `AZURE_AD`, and `OKTA -> OIDC`;
- `OAUTH2 -> OAUTH2`;
- `SAML2 -> SAML2`.

This is semantically coherent and is not a duplicate-enum defect.

### 23.4 Active-provider runtime semantics

The Identity DDD requires external login/synchronization to use only ACTIVE providers. Current authentication paths enforce that rule fail-closed:

- LOCAL authentication requires exactly one ACTIVE `ProviderType.LOCAL` provider and rejects none/multiple candidates;
- LDAP/AD authentication requires exactly one ACTIVE provider among `LDAP` / `ACTIVE_DIRECTORY` and rejects none/multiple candidates;
- OIDC normalization resolves `ProviderType.OIDC + issuerUri`, then requires the provider to be ACTIVE;
- the OIDC path also requires a LINKED `ExternalIdentity` and ACTIVE Hidra `User`;
- external roles/groups/scopes do not directly become Hidra authorization; effective permissions remain Identity-owned.

The exactly-one active LOCAL/directory behavior is a runtime/deployment governance rule, not evidence for making provider type globally unique in the table. Inactive/deprecated historical provider records may coexist.

### 23.5 Persistence and dependency consistency

The live domain and JPA models agree on all 23 declared components.

The base Identity migration makes `id`, `code`, `name`, `provider_type`, `sync_enabled`, `just_in_time_provisioning_enabled`, `status`, `created_at`, and `updated_at` non-null. Provider-specific metadata fields remain nullable.

The schema currently creates ordinary indexes for provider `code`, `status`, and timestamps; no unique constraint for provider code was found.

`IdentityProviderRepositoryPort` exposes only `save` and `findById`. The infrastructure repository additionally exposes `findByProviderTypeAndIssuerUri` for OIDC resolution.

HRA-111 protects provider references for same-module persistence models, including external mappings and synchronization records, with FKs to `hidra_identity_provider(id)`. The four HMS subject dependents remain the graph-facing dependencies recorded by the review register.

### 23.6 Secret-safety boundary

The Identity DDD is explicit:

```text
Provider configuration must not contain secret values.
```

`secretReference` is therefore pointer metadata only. LDAP bind passwords, OAuth/OIDC client secrets, tokens, private keys, certificate private material and similar credentials belong in vault/platform secret management.

The current authentication deployment runbook follows that policy by keeping sensitive runtime inputs outside the repository/database. HMSR-010 does not redefine `secretReference` as credential storage and does not authorize secret material inside `metadata`.

Secret safety is primarily a provisioning/platform governance concern; arbitrary secret detection must not be guessed from string contents inside the domain record.

### 23.7 Required revisions

The model ownership and provider taxonomy are sound, but the target baseline cannot be APPROVED until these evidence-backed gaps are resolved:

1. **Unique provider code:** the Identity DDD explicitly requires `IdentityProvider.code` to be unique and recommends `uk_identity_provider_code`. The live schema has only non-unique `ix_hidra_identity_provider_code`; the application repository port has no code lookup/uniqueness contract.
2. **Required provider name:** `name` is required by the Identity DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank values and normalizes blank input to `null`.
3. **Unambiguous OIDC issuer resolution:** the live OIDC converter calls an `Optional` repository method keyed by `(ProviderType.OIDC, issuerUri)`, which assumes at most one matching provider. The schema has no demonstrated uniqueness constraint for that lookup. The current runtime therefore needs either explicit provisioning/application validation for an unambiguous active OIDC issuer mapping or a deliberately different provider-selection contract.
4. **Provider-specific activation validation:** provider-specific fields are intentionally optional at the generic record level, but an ACTIVE provider must have the metadata required by its runtime path. At minimum, an OIDC provider used by the current converter requires a usable issuer URI; LOCAL and LDAP/AD active-provider cardinality must remain governed so ambiguous configuration fails closed. These checks belong in provider administration/provisioning/runtime validation rather than as unconditional constructor rules across all provider types.
5. **Required timestamps:** `createdAt` and `updatedAt` are persistence-required but not guarded by the domain constructor. The final baseline must retain one deliberate enforcement boundary for provider creation/update metadata.

HMSR-010 does not change production Java, JPA, Flyway, authentication runtime, API/application contracts, tests, provider records or secret configuration.

### 23.8 Operational and security interpretation

For SONATRACH/TRC, `IdentityProvider` identifies the trusted authentication source used to establish who the user is. It does **not** transfer authorization authority to the external provider.

LDAP/AD groups, OIDC roles, Keycloak realm roles, Azure/Entra claims or Okta claims must be mapped explicitly into Hidra Identity structures. Hidra roles, permissions, scope validity and authorization decisions remain locally governed and auditable.

Provider failure or ambiguity must fail closed; it must never fall back silently from an external provider to LOCAL authentication.

### 23.9 Review conclusion

**REVISE.** `IdentityProvider` has the correct Identity ownership, 23-field shape, provider/status vocabulary, secret-reference semantics and runtime authorization boundary. Its four HMS subject dependencies are coherent.

The target baseline cannot mark it APPROVED while provider-code uniqueness, mandatory provider name and unambiguous/provider-specific activation rules are not consistently enforced across persistence/provisioning/runtime boundaries. HMS reconciliation must retain these obligations until an explicitly authorized Identity correction task resolves them or the target semantics are explicitly changed.

## 24. HMSR-011 — identity.Permission review

**Decision:** REVISE  
**Review code:** HMSR-011  
**Dependency level:** 0  
**Bounded context:** identity  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 3 — `identity.RolePermissionGrant`, `identity.AuthorizationDelegationGrant`, `identity.UserPermissionGrant`  
**Transitive dependents:** 3  
**Unresolved/non-subject references:** 0

### 24.1 Semantic role and ordering rationale

`Permission` is the Identity-owned atomic business authorization capability. Roles, direct user grants, delegation and policy evaluation may reference a Permission, but no other bounded context owns or redefines the permission truth.

It is Level 0 because it has no upstream HMS subject-model dependency. Its three direct HMS dependents consume the permission identity through Identity-owned grant/delegation models.

The platform route-security layer derives required permission codes, but that technical layer does not own Permission business meaning. Identity remains the authority for persisted permission lifecycle and effective grants.

### 24.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable Permission identifier and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique authorization code. Current live Identity architecture standard is lower-case `<context>:<resource>:<action>`. |
| `nameAr` | `String` | Optional | Arabic display label. |
| `nameFr` | `String` | Optional | French display label. |
| `nameEn` | `String` | Optional | English display label. |
| `description` | `String` | Optional | Human-readable explanation of the capability. |
| `permissionDomain` | `String` | Mandatory | Identity permission context/module token, aligned with the first code component. |
| `resourceType` | `String` | Current live create-path mandatory | Resource/business capability token aligned with the second code component. |
| `action` | `String` | Mandatory | Action token aligned with the third code component. |
| `sensitive` | `boolean` | Mandatory persisted state | Marks capabilities requiring stronger grant/approval governance. |
| `status` | `PermissionStatus` | Mandatory | Permission lifecycle: `ACTIVE`, `DISABLED`, or `DEPRECATED`. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current domain constructor rejects blank `id` and `code` and null `status`, trims textual values and normalizes blank optional text to `null`.

### 24.3 Canonical permission-code standard

The live Identity roadmap defines the current permission code contract as:

```text
<context>:<resource>:<action>
```

with exactly three non-empty lower-case parts and hyphens allowed inside multi-word parts.

This convention is independently confirmed by:

- `IdentityAdministrationCommandApplicationService`, which validates exactly three lower-case colon-separated parts;
- `HidraRoutePermissionNaming`, which derives `<module>:<resource>:<action>` for backend route enforcement;
- `HidraRoutePermissionCatalogService`, which publishes `permissionFormat = <module>:<resource>:<action>`;
- `V20260929_004__provision_organization_scope_permissions.sql`, which provisions codes such as `organization:operational-scope:register`.

The older Identity data-definition examples using uppercase dot notation such as `TOPOLOGY.FACILITY.CREATE`, plus two-part examples such as `ALARM.ACKNOWLEDGE` and `INCIDENT.OPEN`, are inconsistent with the current live Identity roadmap/runtime standard. Data provisioning must not use those examples as the canonical format without reconciliation.

### 24.4 `resourceType` semantic conflict

The current live create path requires `permissionDomain`, `resourceType`, and `action`, consistent with the mandatory three-part permission-code policy.

However, `docs/data definition/Identity.md` still marks `resourceType` optional and illustrates permissions with only two semantic parts. JPA/schema also currently allow `resource_type` to be null.

This is a target-model documentation/persistence contradiction. HMSR-011 does not guess whether future permissions may intentionally be resource-less. The final baseline must explicitly choose one rule and align the Identity DDD, application command validation, route naming, schema nullability and provisioning data.

### 24.5 Persistence and uniqueness consistency

The live domain and JPA models agree on all 13 declared components.

The base Identity migration makes `id`, `code`, `permission_domain`, `action`, `sensitive`, `status`, `created_at`, and `updated_at` non-null; labels, description and `resource_type` remain nullable.

The Identity DDD explicitly requires `Permission.code` uniqueness and recommends `uk_identity_permission_code`. The live schema currently has only non-unique `ix_hidra_identity_permission_code`.

`PermissionRepositoryPort` exposes only `save` and `findById`, and the Spring Data repository exposes no code lookup/uniqueness method. The current create path therefore has no demonstrated application-level duplicate-code guard.

### 24.6 Permission lifecycle and effective authorization

`PermissionStatus` is intentionally distinct from `RoleStatus` even though both currently expose `ACTIVE`, `DISABLED`, and `DEPRECATED`. The repository's duplicate-enum review explicitly keeps them separate because permission and role lifecycle semantics may evolve independently.

The live effective-permission query currently does **not** apply `Permission.status`:

- it loads every `PermissionJpaEntity` row into an ID-to-code map;
- it filters grant rows by grant status/time only;
- active `UserPermissionGrant` and `RolePermissionGrant` rows can therefore contribute the code of a `DISABLED` or `DEPRECATED` Permission;
- grant creation's `requirePermission` verifies only that the permission ID exists, not that the Permission is ACTIVE.

This makes the stored Permission lifecycle ineffective at the current authorization-resolution boundary. Because Hidra-issued JWTs also carry the resolved permission codes as scope claims, the final correction must define how permission disable/deprecation interacts with already-issued access tokens and token TTL/revalidation policy; HMSR-011 does not invent an immediate-revocation design.

### 24.7 Domain/application required-field consistency

`permissionDomain` and `action` are required by the active Identity DDD and are non-null in persistence. The current administration create path enforces both before constructing the model, but the domain constructor itself accepts blank/null values and normalizes them to `null`.

That is not necessarily a requirement to move repository/application validation into the record constructor. The final correction must simply establish one deliberate authoritative enforcement boundary so invalid Permission state cannot be created through alternate domain callers.

`createdAt` and `updatedAt` are likewise persistence-required and are supplied by the current administration service but not guarded by the record constructor.

### 24.8 Sensitive-permission interpretation

`sensitive = true` classifies a capability as requiring stronger governance when it is granted or exercised. It does not itself grant access and must not be treated as a replacement for Workflow approval, scoped grants, expiry, explicit deny, or Audit evidence.

For SONATRACH/TRC operations, examples may include high-impact capabilities such as approving trusted telemetry, changing operational plans, acknowledging critical alarms, authorizing integrity actions, or administering security. Permission identity remains an authorization capability, not an Organization responsibility, Workflow task, or operational asset.

### 24.9 Required revisions

The target baseline cannot be APPROVED until these evidence-backed issues are reconciled:

1. **Unique permission code:** enforce the DDD-defined global uniqueness of `Permission.code` at the application/database boundary.
2. **Permission lifecycle enforcement:** effective authorization must not continue treating `DISABLED` or `DEPRECATED` permissions as active merely because their grants remain active.
3. **Grant-time lifecycle validation:** define whether new grants/delegations may target non-ACTIVE permissions and enforce that rule consistently.
4. **Permission-code documentation reconciliation:** update stale uppercase/dot and two-part Identity DDD examples to the live three-part lower-case colon standard, or explicitly change the live standard through an authorized decision.
5. **`resourceType` contract reconciliation:** resolve the current DDD/schema optionality versus the live administration/route naming requirement for a non-empty resource component.
6. **Required domain fields:** retain an explicit enforcement boundary for `permissionDomain`, `action`, `createdAt`, and `updatedAt` so alternate callers cannot persist semantically invalid Permission state.
7. **Issued-token lifecycle policy:** define the expected effect of disabling/deprecating a permission on already-issued Hidra JWT scope claims; any immediate-revocation requirement must be implemented deliberately rather than assumed.

HMSR-011 does not change production Java, JPA, Flyway, security runtime, API/application contracts, tests, permission rows, grants or token configuration.

### 24.10 Review conclusion

**REVISE.** `identity.Permission` is the correct foundational Identity authorization capability and its three subject dependency directions are sound. The current three-part lower-case permission naming standard is well supported by live roadmap/runtime/provisioning evidence.

The target baseline cannot mark Permission APPROVED while code uniqueness is unenforced, Permission lifecycle status is ignored by effective authorization, and the DDD/schema remain inconsistent with the current three-part/resource-required permission standard. HMS reconciliation must retain these obligations until an explicitly authorized Identity correction task resolves them or the target semantics are explicitly changed.

## 25. HMSR-012 — notification.NotificationTemplate review

**Decision:** REVISE  
**Review code:** HMSR-012  
**Dependency level:** 0  
**Bounded context:** notification  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `notification.NotificationRequest` and `notification.NotificationMessage`  
**Transitive dependents:** 3  
**Unresolved/non-subject references:** 2 — `categoryId` and `defaultChannelId`, both now semantically resolved to Notification-owned non-subject targets

### 25.1 Semantic role and ordering rationale

`NotificationTemplate` is the Notification-owned aggregate root for reusable message-template identity and governance. It carries stable template identity, business classification, optional default channel, lifecycle state and a current-version marker; immutable message content belongs to `NotificationTemplateVersion` and localized version content belongs to `NotificationTemplateTranslation`.

It is Level 0 because none of its prerequisites are another HMS subject model. `NotificationRequest` and `NotificationMessage` are the two direct HMS subject dependents. `NotificationTemplateVersion`, `NotificationCatalogEntry` and `NotificationChannel` are Notification read/persistence models outside the 123 HMS subject population.

The source business module owns why a notification exists. Notification owns template selection, rendering, channel semantics and delivery evidence; it must not mutate the source business object's state.

### 25.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable NotificationTemplate identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique business code for the reusable template. |
| `nameAr` | `String` | Optional | Arabic template display label. |
| `nameFr` | `String` | Persistence-required | French template display label; JPA/schema require it although the aggregate DDD does not separately state field nullability. |
| `nameEn` | `String` | Optional | English template display label. |
| `templateTypeId` | `String` | Mandatory | Notification catalog reference in family `TEMPLATE_TYPE`. |
| `categoryId` | `String` | Optional | Notification catalog reference in family `NOTIFICATION_CATEGORY`. |
| `defaultChannelId` | `String` | Optional | Default Notification-owned channel reference; concrete request/policy logic may override it. |
| `status` | `NotificationTemplateStatus` | Mandatory | Template lifecycle: `DRAFT`, `ACTIVE`, `INACTIVE`, or `RETIRED`. |
| `currentVersion` | `Integer` | Optional | Denormalized/current template version number marker; it is not a model ID or HMS dependency edge. |
| `systemDefined` | `boolean` | Mandatory persisted state | Marks templates delivered/owned as system-defined reference configuration. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current constructor rejects blank `id`, `code`, and `templateTypeId`, rejects null `status`, trims textual values and converts blank optional text to `null`. `usableForNewMessages()` correctly returns true only for `ACTIVE` templates.

### 25.3 Resolution of the two previously unresolved references

Current repository evidence resolves both HMSR non-subject references without adding subject-graph edges.

1. **`categoryId -> NOTIFICATION_CATEGORY`**
   - the Notification DDD defines `NotificationCatalogEntry` as the controlled-vocabulary persistence model;
   - `NOTIFICATION_CATEGORY` is an explicit Notification catalog family;
   - template business type/category must be catalog-backed rather than hard-coded enum taxonomy;
   - `NotificationCatalogEntry` is outside the 123 HMS subject set.

2. **`defaultChannelId -> NotificationChannel`**
   - the Notification DDD defines `NotificationChannel` as the owner of channel semantics such as EMAIL, SMS, WEB, MOBILE_PUSH, MESSAGING_APP, WEBHOOK and VOICE;
   - the live persistence model stores channels in `hidra_notification_channel`;
   - the same DDD explicitly uses `defaultChannelId -> NotificationChannel` for NotificationPolicy, supporting the same identifier semantics for template defaults;
   - `NotificationChannel` is outside the 123 HMS subject set.

`templateTypeId` is also sharpened from generic catalog/value semantics to the `TEMPLATE_TYPE` catalog family. HRA-111 installs `fk_hra111_notification_023` from `hidra_notification_template.template_type_id` to `hidra_notification_catalog_entry(id)`.

### 25.4 Template/version boundary

The active Notification DDD requires:

```text
Template code must be unique.
Only ACTIVE templates can be used for new messages.
A template version is immutable after activation.
A sent message must reference the exact template version used.
New wording requires a new version.
```

`NotificationTemplateVersion` is a separate Notification persistence/read model with `templateId`, `versionNumber`, lifecycle status and immutable rendered-source content. HRA-111 protects `NotificationTemplateVersion.templateId -> NotificationTemplate.id` with `ON DELETE RESTRICT`.

`currentVersion` on the aggregate is an integer version marker, not a foreign-key identifier. The repository currently contains no demonstrated application service that governs creation/activation of template versions or synchronizes `currentVersion` with an ACTIVE version. Before provisioning/editing template data, the target contract must explicitly define whether this field means latest version, currently active version, or another governed version marker and which status combinations are legal.

### 25.5 Persistence and reference integrity

The live domain and JPA models agree on all 13 declared components.

The base Notification migration makes `id`, `code`, `name_fr`, `template_type_id`, `status`, `system_defined`, `created_at`, and `updated_at` non-null. `category_id`, `default_channel_id`, and `current_version` are nullable.

The schema has ordinary indexes on template code, type, category, default channel, status and audit timestamps. No unique constraint on template `code` was found.

HRA-111 enforces only the mandatory `template_type_id` reference for this row. No current database FK was found for optional `category_id` or `default_channel_id`, and the live application layer does not expose a template-management service that demonstrates catalog-family/channel validation for those optional references.

### 25.6 Runtime use of templates and versions

The current `NotificationApplicationService` creates `NotificationRequest` and `NotificationMessage` directly from incoming command IDs. It does not inject `NotificationTemplateRepositoryPort` or a template-version repository and therefore does not demonstrate these DDD rules at the request/message creation boundary:

- selected template exists;
- selected template is `ACTIVE`; 
- `templateTypeId` belongs to `TEMPLATE_TYPE`; 
- optional `categoryId` belongs to `NOTIFICATION_CATEGORY`; 
- optional `defaultChannelId` resolves to a usable NotificationChannel when a default is relied upon;
- supplied `templateVersionId` exists and belongs to the supplied `templateId`; 
- the selected version is eligible for reproducible message generation.

`NotificationTemplate.usableForNewMessages()` expresses the ACTIVE-only rule correctly, but no current message/request orchestration path found during HMSR-012 calls that helper.

### 25.7 Multilingual interpretation

The aggregate stores multilingual **template labels** (`nameAr/nameFr/nameEn`), while actual localized message content belongs to `NotificationTemplateTranslation` for a specific template version.

The DDD explicitly states that French template-version content is mandatory for operational Hidra screens, with Arabic and English optional/recommended. This should not be confused with the aggregate label fields. The live JPA/schema independently require `NotificationTemplate.nameFr`; the domain constructor does not enforce that persistence requirement, so the final baseline should deliberately reconcile the label nullability boundary rather than infer content-localization rules from it.

### 25.8 Required revisions

The model's ownership and graph placement are correct, but the target baseline cannot be APPROVED until these evidence-backed gaps are reconciled:

1. **Unique template code:** enforce the DDD-defined uniqueness of `NotificationTemplate.code` at the application/database boundary.
2. **ACTIVE-only template selection:** request/message creation must validate that a template used for new message generation is ACTIVE; the current helper is not wired into orchestration.
3. **Exact template-version integrity:** validate that `templateVersionId` exists, belongs to the selected `templateId`, and is eligible under the version lifecycle before rendering/sending.
4. **`currentVersion` governance:** define and enforce the exact meaning of `currentVersion` and its consistency with template-version rows and template lifecycle.
5. **Catalog-family validation:** `templateTypeId` must resolve to `TEMPLATE_TYPE`; when present, `categoryId` must resolve to `NOTIFICATION_CATEGORY`, not merely to any catalog row.
6. **Optional channel-reference integrity:** when `defaultChannelId` is present and used, it must resolve to the intended NotificationChannel and satisfy the applicable active/availability policy.
7. **French label persistence boundary:** reconcile the persistence-required `nameFr` with the domain constructor's nullable behavior without confusing label semantics with version-content localization.
8. **Required timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required but not guarded by the record constructor.

HMSR-012 does not change production Java, JPA, Flyway, application/API contracts, tests, catalogs, channels, templates or template-version data.

### 25.9 Operational interpretation

For SONATRACH/TRC operations, NotificationTemplate may govern reusable communications for alarms, incidents, workflow tasks, planning events, integrity events or operational reminders. The template controls communication wording and default delivery semantics only.

Using a template must never imply approval, alarm acknowledgement, incident closure, SCADA actuation or any other source-domain state transition. Notification acknowledgement remains communication evidence, not business approval.

### 25.10 Review conclusion

**REVISE.** `NotificationTemplate` is the correct foundational Notification aggregate and its two subject dependency directions are sound. `templateTypeId`, `categoryId` and `defaultChannelId` are now semantically resolved to Notification-owned non-subject prerequisites.

The target baseline cannot mark it APPROVED while template-code uniqueness, ACTIVE-only selection, exact template/version integrity, `currentVersion` governance, catalog-family validation and optional channel-reference integrity remain unenforced or ambiguous. HMS reconciliation must retain these obligations until an explicitly authorized Notification correction task resolves them or the target semantics are explicitly changed.

## 26. HMSR-013 — reporting.ReportDefinition review

**Decision:** REVISE  
**Review code:** HMSR-013  
**Dependency level:** 0  
**Bounded context:** reporting  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `reporting.ReportRequest` and `reporting.ReportRun`  
**Transitive dependents:** 3  
**Unresolved/non-subject references:** 1 — `currentTemplateVersionId`, now semantically resolved to Reporting-owned `ReportTemplateVersion` outside the 123 HMS subject set

### 26.1 Semantic role and ordering rationale

`ReportDefinition` is the Reporting-owned reusable formal report type. It defines stable report identity, multilingual display labels, report category, source/owner module, access/approval characteristics and the currently selected template-version reference used to support reproducible formal output.

It is Level 0 because none of its prerequisites are another HMS subject model. `ReportRequest` and `ReportRun` are its two direct HMS subject dependents. `ReportCatalogEntry`, `ReportTemplate`, `ReportTemplateVersion` and `ReportAccessPolicy` are Reporting read/persistence models outside the 123 HMS subject population.

Reporting owns formal output definition, execution, reproducibility, publication and distribution metadata. It must not become the owner of source operational truth from Telemetry, Planning, Risk, Custody, HSE, Integrity or other modules.

### 26.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable ReportDefinition identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique business code for the formal report type. |
| `nameAr` | `String` | Optional | Arabic report-definition display name. |
| `nameFr` | `String` | Mandatory | French report-definition display name; explicitly required by Reporting DDD and persistence. |
| `nameEn` | `String` | Optional | English report-definition display name. |
| `reportCategoryId` | `String` | Mandatory | Reporting-owned catalog reference in family `REPORT_CATEGORY`. |
| `ownerModule` | `String` | Mandatory | Stable Hidra module code identifying the business/source owner of the report definition; must resolve to a known Hidra module. |
| `description` | `String` | Optional | Human-readable definition purpose; current creation service applies the reporting secret-material guard. |
| `active` | `boolean` | Current persisted lifecycle proxy | Current implementation uses this boolean to determine `usableForNewRequests()`, but it cannot represent the target DDD's full definition lifecycle. |
| `currentTemplateVersionId` | `String` | Optional | Current Reporting-owned template-version reference used by the definition; target is `ReportTemplateVersion`, not an HMS subject. |
| `requiresApproval` | `boolean` | Mandatory persisted state | Whether requests/runs require Workflow-backed approval before queueing. |
| `restricted` | `boolean` | Mandatory persisted state | Whether explicit Reporting access policy/authorization is required. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current domain constructor rejects blank `id`, `code`, and `reportCategoryId`, trims string values, and converts blank optional text to `null`. It does not guard `nameFr`, `ownerModule`, timestamps or template-version consistency.

### 26.3 Reporting category dependency resolution

Current repository evidence sharpens `reportCategoryId` from a generic catalog dependency to the Reporting-owned `REPORT_CATEGORY` family:

- Reporting DDD defines `ReportCatalogEntry` as the controlled Reporting vocabulary;
- `REPORT_CATEGORY` is an explicit Reporting catalog name;
- HRA-111 installs `fk_hra111_reporting_014`; 
- that FK points `hidra_reporting_report_definition.report_category_id` to `hidra_reporting_catalog_entry(id)` with `ON DELETE RESTRICT`;
- `ReportCatalogEntry` is outside the 123 HMS subject set.

The FK proves only that a catalog row exists. It does not prove that the row belongs to `REPORT_CATEGORY` or is active/usable.

### 26.4 `currentTemplateVersionId` resolution

The previously unresolved `currentTemplateVersionId` is semantically resolved to Reporting-owned `ReportTemplateVersion`.

Repository evidence establishes the chain:

```text
ReportDefinition
  -> currentTemplateVersionId -> ReportTemplateVersion
ReportTemplateVersion
  -> reportTemplateId -> ReportTemplate
ReportTemplate
  -> reportDefinitionId -> ReportDefinition
```

`ReportTemplate` and `ReportTemplateVersion` are Reporting read/persistence models outside the 123 HMS subject population, so no HMS graph edge is added.

The base schema indexes `current_template_version_id` but no current FK was found from `hidra_reporting_report_definition.current_template_version_id` to `hidra_reporting_report_template_version(id)`. HRA-111 does protect `ReportTemplateVersion.reportTemplateId -> ReportTemplate.id` and `ReportTemplate.reportDefinitionId -> ReportDefinition.id`.

Therefore the final application/database contract must validate not only that the referenced template version exists, but that its parent template belongs to the same ReportDefinition.

### 26.5 Lifecycle contradiction — boolean `active` versus retained definition status

The active Reporting DDD defines the ReportDefinition lifecycle as:

```text
DRAFT -> ACTIVE -> RETIRED
```

with DRAFT editable, ACTIVE usable for requests, and RETIRED unavailable for new requests.

The repository also retains `ReportDefinitionStatus { DRAFT, ACTIVE, RETIRED }`. HRA-080 explicitly decided to KEEP `ReportDefinitionStatus` separate from `ReportTemplateVersionStatus`, stating that definition and template-version lifecycles are distinct.

However, the live `ReportDefinition` record, JPA entity and `hidra_reporting_report_definition` table contain only `boolean active`; no `ReportDefinitionStatus` component/column is present. Repository search found no active Java use of `ReportDefinitionStatus` beyond the enum declaration and architecture documentation.

`ReportingApplicationService.createReportDefinition()` also creates every new definition with `active = true`, so the current creation path skips the documented DRAFT phase entirely and cannot distinguish RETIRED from a generic inactive definition.

This is a direct target-model semantic contradiction. The final correction must explicitly choose and migrate to one authoritative lifecycle representation; it must not leave the retained enum and persisted boolean as conflicting sources of intent.

### 26.6 Request, approval and restricted-access governance

The Reporting DDD requires:

```text
inactive definitions cannot be used for new report requests
restricted reports require explicit access policy
reports requiring approval cannot be queued before workflow approval
```

The current `ReportingApplicationService` does not demonstrate those rules:

- `requestReport()` creates a request directly from `command.reportDefinitionId()` without loading the ReportDefinition or calling `usableForNewRequests()`;
- it does not validate `restricted` definitions through a Reporting authorization/access-policy port;
- `queueReportRun()` accepts `reportDefinitionId` and `templateVersionId` directly and does not load the definition/request to enforce `requiresApproval` or Workflow approval state;
- the current service has no injected `ReportAccessPolicy` or `ReportWorkflowPort` collaboration in these paths.

These are application/workflow/authorization responsibilities, not constructor-only invariants.

### 26.7 Persistence and creation consistency

The live domain and JPA models agree on all 14 declared components.

The base Reporting migration makes `id`, `code`, `name_fr`, `report_category_id`, `owner_module`, `active`, `requires_approval`, `restricted`, `created_at`, and `updated_at` non-null. The current template-version reference is nullable.

The schema has ordinary indexes on definition `code`, category, active state, current template version and timestamps. No unique constraint on `ReportDefinition.code` was found.

`ReportDefinitionRepositoryPort` and `ReportDefinitionJpaRepository` expose no code lookup/uniqueness method. The creation service saves directly without a duplicate-code check.

`nameFr` and `ownerModule` are persistence-required, but the domain constructor accepts blank/null values and normalizes them to `null`. The creation service performs no demonstrated known-module validation for `ownerModule` and no Reporting catalog-family validation for `reportCategoryId`.

### 26.8 Reproducibility and template-version governance

The Reporting DDD requires report runs to preserve the exact template version used and states that active template versions cannot be edited directly. Reproducibility requires definition/version identity, template-version identity, parameters, input snapshots, source contract versions, actor/correlation context, artifact checksum and generation timestamp.

`ReportDefinition.currentTemplateVersionId` may provide a default/current choice, but a completed `ReportRun.templateVersionId` is the historical execution reference. A later template change must never rewrite the version associated with an already executed run.

The current queue path accepts `templateVersionId` directly and does not demonstrate that the version is ACTIVE/eligible or belongs to the requested definition. That relationship must be validated through the Reporting-owned template/version chain before execution.

### 26.9 Required revisions

The target baseline cannot be APPROVED until these evidence-backed issues are reconciled:

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

HMSR-013 does not change production Java, JPA, Flyway, Workflow/authorization integration, API/application contracts, tests, catalogs, report definitions or template-version data.

### 26.10 Operational interpretation

For SONATRACH/TRC operations, a ReportDefinition represents a governed formal output such as a Daily Operations Report, Monthly Throughput Report, Plan-vs-Actual Report, Incident Register, Custody Transfer Statement, HSE Compliance Report or Risk Register Report.

The definition controls formal Reporting behavior and governance. It does not own or recalculate the source operational facts. Analytics calculates analytical results, source modules own business state, Documents manages file/document metadata, Notification delivers report-ready messages, Workflow governs approvals where required, and Audit records critical evidence.

### 26.11 Review conclusion

**REVISE.** `ReportDefinition` is the correct foundational Reporting aggregate and its two HMS dependency directions are sound. `reportCategoryId` is resolved to `REPORT_CATEGORY`, and `currentTemplateVersionId` is resolved to Reporting-owned `ReportTemplateVersion` outside the HMS graph.

The target baseline cannot mark it APPROVED while the definition lifecycle is internally contradictory, unique code and required-field semantics are unenforced, and request/access/approval/template-version governance is absent from the current orchestration paths. HMS reconciliation must retain these obligations until an explicitly authorized Reporting correction task resolves them or the target semantics are explicitly changed.

## 27. HMSR-014 — integration.IntegrationJobRun review

**Decision:** REVISE  
**Review code:** HMSR-014  
**Dependency level:** 0  
**Bounded context:** integration  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `integration.IntegrationExchangeMessage` and `integration.IntegrationDeadLetterRecord`  
**Transitive dependents:** 2  
**Unresolved/non-subject references:** 1 — `jobDefinitionId`, now semantically resolved to Integration-owned `IntegrationJobDefinition` outside the 123 HMS subject set

### 27.1 Semantic role and ordering rationale

`IntegrationJobRun` is the Integration-owned execution record for one run of a repeatable import, export, synchronization, replay, reconciliation or health-oriented integration job. It preserves execution provenance, trigger type, correlation identity, lifecycle state, processing counters and failure evidence.

It is Level 0 because its upstream business prerequisite, `IntegrationJobDefinition`, is a retained Integration read/persistence model outside the 123 HMS subject population. The two direct HMS dependents are `IntegrationExchangeMessage` and `IntegrationDeadLetterRecord`.

Other Integration persistence/read models outside the subject population also reference a job run, including `IntegrationJobRunStep`, retry evidence, inbound/outbound processing records and synchronization cursor/history structures. Those relationships do not change the HMS subject graph.

Integration owns transport/exchange execution evidence. The target business module remains the source of truth for accepted Hidra business facts.

### 27.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable job-run identity and persistence primary key. |
| `jobDefinitionId` | `String` | Mandatory | Parent Integration job-definition reference; resolved to `IntegrationJobDefinition`. |
| `runNumber` | `long` | Mandatory | Monotonic execution sequence number within the parent job definition. |
| `triggerType` | `JobTriggerType` | Mandatory | Trigger source: `SCHEDULED`, `MANUAL`, `EVENT`, `RETRY`, or `REPLAY`. |
| `triggeredByActorId` | `String` | Conditional/optional | Actor reference for manual-trigger provenance; not an HMS `identity.User` edge. |
| `status` | `JobRunStatus` | Mandatory | Run lifecycle: `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_ERRORS`, `FAILED`, or `CANCELLED`. |
| `correlationId` | `String` | Optional technical reference | End-to-end technical/business correlation identity. |
| `startedAt` | `Instant` | Mandatory | Execution start timestamp. |
| `completedAt` | `Instant` | Optional until completion | Completion timestamp; when present it must not precede `startedAt`. |
| `receivedCount` | `long` | Mandatory | Number of records/messages received for processing. |
| `mappedCount` | `long` | Mandatory | Number successfully mapped/transformed to the target contract stage. |
| `acceptedCount` | `long` | Mandatory | Number accepted by the target module/external destination. |
| `rejectedCount` | `long` | Mandatory | Number rejected. |
| `deadLetterCount` | `long` | Mandatory | Number routed to dead-letter handling. |
| `retryCount` | `long` | Mandatory | Number of retry attempts. |
| `failureReason` | `String` | Optional | Failure diagnostic/evidence text when applicable. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current domain constructor rejects blank `id` and `jobDefinitionId`, rejects null `triggerType` and `status`, requires `startedAt`, trims scalar text/reference fields and exposes `terminalStatus()` for completed/failed/cancelled states.

### 27.3 Job-definition dependency resolution

HMS-003 left `jobDefinitionId` unresolved because `IntegrationJobDefinition` is outside the 123 HMS subject set. Stronger repository evidence resolves the target without adding an HMS edge:

- Integration DDD defines `IntegrationJobDefinition ||--o{ IntegrationJobRun`; 
- `IntegrationJobDefinitionJpaEntity` persists the parent in `hidra_integration_job_definition`;
- HRA-111 installs `fk_hra111_integration_022`; 
- that FK points `hidra_integration_job_run.job_definition_id` to `hidra_integration_job_definition(id)` with `ON DELETE RESTRICT`;
- repository mirror classification retains `IntegrationJobDefinition` as an Integration read/persistence model outside the HMS subject population.

Therefore `jobDefinitionId` is a same-module domain reference to a non-subject prerequisite, not an unresolved or cross-module reference.

### 27.4 Job-definition eligibility and trigger governance

The Integration DDD gives `IntegrationJobDefinition` the configuration needed to decide whether a run may start, including connector, mapping profile, job type, direction, schedule, `manualRunAllowed`, retry policy and `active` state. It also states that automated jobs require an active connector instance and import/sync jobs require an appropriate mapping/target path.

The current `IntegrationApplicationService.startIntegrationJobRun()` does not load `IntegrationJobDefinition` at all. It receives `jobDefinitionId`, `runNumber`, trigger type and actor/correlation metadata from the command, constructs a `RUNNING` job run and saves it directly.

Consequently, the current start path does not demonstrate validation that:

- the referenced job definition is active/eligible to run;
- a `MANUAL` trigger is allowed by `manualRunAllowed`;
- automated execution has the required connector readiness;
- job-type-specific mapping/public-target prerequisites are satisfied;
- manual-trigger provenance has the actor identity required by the intended operational policy.

The database FK proves only that a job-definition row exists. It does not prove current run eligibility.

### 27.5 Run-number authority

The Integration DDD defines `runNumber` as a **monotonic run number per job**.

The current API request and application command both accept `runNumber` from the caller, and the start service persists that value unchanged. `IntegrationJobRunRepositoryPort` exposes only `save` and `findById`; the Spring Data repository exposes no next-run/max-run or job/run-number lookup. The live migration provides an ordinary index on `job_definition_id` but no uniqueness/ordering constraint for `(job_definition_id, run_number)`.

Therefore the sequence is currently client-controlled and race-prone rather than authoritative. The final correction must establish a concurrency-safe owner for run-number allocation and prevent duplicate/out-of-order sequence identities for the same job.

### 27.6 Counter and temporal invariants

The active Integration DDD explicitly requires:

```text
Counts must be non-negative.
completedAt must be greater than or equal to startedAt.
acceptedCount + rejectedCount + deadLetterCount must not exceed receivedCount
unless explicitly modeled as multi-record expansion.
```

The current constructor validates none of the six counters and does not compare `completedAt` with `startedAt`. The live table stores all counters as `bigint NOT NULL` but contains no demonstrated CHECK constraints for non-negativity, temporal ordering or aggregate count consistency.

`startIntegrationJobRun()` initializes all counters to zero and `completedAt` to null, which is valid for a new run, but persisted data reconstructed or updated through another path can still violate the target invariants.

### 27.7 Lifecycle monotonicity

The Integration DDD states:

```text
JobRun status is monotonic.
A terminal JobRun cannot return to RUNNING.
```

`JobRunStatus` correctly represents `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_ERRORS`, `FAILED`, and `CANCELLED`, and `IntegrationJobRun.terminalStatus()` correctly identifies terminal states.

However, `terminalStatus()` is observational only. The record has no transition operation that compares previous and next status, and the current public application service contains only the start path; no repository evidence found during HMSR-014 demonstrates controlled completion/failure/cancellation transitions with counter/timestamp updates.

The final lifecycle implementation must enforce transition monotonicity at an application/domain boundary that has both prior and requested state. Constructor validation alone cannot prove a transition is legal.

### 27.8 Persistence consistency

The live domain and JPA models agree on all 18 declared components. The base Integration migration makes `id`, `job_definition_id`, `run_number`, `trigger_type`, `status`, `started_at`, all six counters, `created_at`, and `updated_at` non-null.

HRA-111 protects the parent job-definition reference. The schema also indexes status, actor, correlation ID and audit timestamps for operational querying.

No current database constraint found during HMSR-014 enforces run-number monotonicity, non-negative counters, completion-time ordering or terminal-state transition policy.

### 27.9 Required revisions

The target baseline cannot be APPROVED until these evidence-backed execution invariants are enforced:

1. **Authoritative monotonic run number:** stop treating `runNumber` as an unchecked caller-owned value; allocate/validate it per job in a concurrency-safe application/database design.
2. **Job-definition eligibility:** before starting a run, load/validate the parent job definition and enforce its `active` state plus applicable connector/mapping/target prerequisites.
3. **Manual-run governance:** enforce `manualRunAllowed` for `MANUAL` triggers and define/enforce actor provenance for manual execution.
4. **Non-negative counters:** enforce non-negative `received`, `mapped`, `accepted`, `rejected`, `deadLetter`, and `retry` counts.
5. **Counter consistency:** enforce the DDD aggregate-count rule, while preserving an explicit exception only where multi-record expansion is intentionally modeled.
6. **Completion-time ordering:** reject `completedAt < startedAt`.
7. **Monotonic lifecycle transitions:** terminal runs must not return to `RUNNING`; completion/failure/cancellation orchestration must update state through a transition-aware boundary.
8. **Required audit timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required.

HMSR-014 does not change production Java, JPA, Flyway, API/application contracts, tests, job definitions, run records, counters or integration data.

### 27.10 Operational and OT-safety interpretation

For SONATRACH/TRC, an IntegrationJobRun may represent a historian/SCADA data import, CMMS/ERP synchronization, laboratory/custody file exchange, reporting export, replay or reconciliation execution. Its counters and timestamps are operational evidence of data movement, not the underlying pipeline business facts.

The Integration DDD explicitly prohibits direct control actuation: Integration jobs must not execute commands that change PLC/RTU/SCADA state or issue valve, pump or compressor control payloads. A successful IntegrationJobRun therefore means the governed data-exchange execution completed under its integration contract; it does not authorize or prove a physical control action.

### 27.11 Review conclusion

**REVISE.** `IntegrationJobRun` is the correct Integration execution model, its 18-field shape and status/trigger vocabularies are semantically appropriate, and `jobDefinitionId` is now resolved to the Integration-owned `IntegrationJobDefinition` read/persistence model outside the HMS graph.

The target baseline cannot mark it APPROVED while run-number authority, parent-job eligibility, counter invariants, completion-time ordering and lifecycle monotonicity remain unenforced. HMS reconciliation must retain these obligations until an explicitly authorized Integration correction task resolves them or the target semantics are explicitly changed.

## 28. HMSR-015 — leakdetection.LeakCandidate review

**Decision:** REVISE  
**Review code:** HMSR-015  
**Dependency level:** 0  
**Bounded context:** leakdetection  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `leakdetection.LeakDetectionCase` and `leakdetection.LeakEscalationReference`  
**Transitive dependents:** 2  
**Unresolved/non-subject references:** 2 — `runId` and `profileId`, both now semantically resolved to Leak Detection-owned read/persistence models outside the 123 HMS subject set

### 28.1 Semantic role and boundary

`LeakCandidate` is the Leak Detection-owned record of a suspected hydrocarbon leak. It captures the detection/profile provenance, typed Topology target, occurrence/evidence timing, confidence/severity classification, lifecycle state, human-readable summary and correlation identity used to drive controlled verification and case escalation.

It is Level 0 because its upstream configuration/execution prerequisites — `LeakDetectionProfile` and optional `LeakDetectionRun` — are retained Leak Detection read/persistence models outside the HMS subject population. `LeakDetectionCase` and `LeakEscalationReference` are its two direct HMS subject dependents.

Leak Detection is decision support only. It may create candidates, estimate location, link evidence, request verification and escalate by neutral references; it must not directly actuate valves, pumps, compressors, PLCs, RTUs, SCADA, ESD or SIS.

### 28.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable candidate identity and persistence primary key. |
| `runId` | `String` | Optional | Leak Detection run that produced/aggregated the candidate; resolved to `LeakDetectionRun`. |
| `profileId` | `String` | Mandatory | Detection profile used for the candidate; resolved to `LeakDetectionProfile`. |
| `candidateNumber` | `String` | Mandatory | External/business candidate number or anomaly identity used to identify the suspected event. Exact uniqueness/idempotency scope is not yet defined. |
| `topologyAssetType` | `String` | Mandatory in persistence | Discriminator identifying the Topology target namespace. |
| `topologyAssetId` | `String` | Mandatory | Stable cross-module Topology reference selected together with `topologyAssetType`; no single HMS subject target. |
| `topologyAssetCode` | `String` | Mandatory | Snapshot of the referenced Topology asset business code. |
| `topologyAssetNameSnapshot` | `String` | Optional | Human-readable Topology name snapshot. |
| `suspectedAt` | `Instant` | Mandatory | Time at which the leak suspicion applies. |
| `firstEvidenceAt` | `Instant` | Optional | Time of first retained supporting evidence when known. |
| `confidenceScore` | `BigDecimal` | Mandatory | Detection confidence consumed by `LeakConfidenceClassifier`; current repository does not define a numeric min/max contract. |
| `severityLevel` | `LeakSeverityLevel` | Mandatory | `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`, or `UNKNOWN`. Current create path derives it from confidence. |
| `status` | `LeakCandidateStatus` | Mandatory | Candidate lifecycle: `NEW`, `UNDER_REVIEW`, `VERIFIED`, `DISMISSED`, `ESCALATED`, `CLOSED`. |
| `summary` | `String` | Optional | Human-readable candidate summary. |
| `correlationId` | `String` | Optional technical reference | Correlation identity for traceability across detection/evidence/escalation flows. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current constructor rejects blank `id`, `profileId`, `candidateNumber`, `topologyAssetId`, and `topologyAssetCode`; requires `suspectedAt`, `confidenceScore`, `severityLevel`, and `status`; trims scalar references/text; and exposes `stillOpen()` for `NEW`, `UNDER_REVIEW`, and `VERIFIED` states.

### 28.3 Resolution of `profileId` and `runId`

`profileId` is now definitively resolved:

- `LeakDetectionProfileJpaEntity` persists the configuration in `hidra_leak_detection_profile`;
- HRA-111 installs `fk_hra111_leakdetection_001`; 
- the FK points `hidra_leak_detection_candidate.profile_id` to `hidra_leak_detection_profile(id)` with `ON DELETE RESTRICT`;
- repository mirror classification retains `LeakDetectionProfile` outside the 123 HMS subjects.

`runId` is also semantically resolvable to `LeakDetectionRun`: the Leak Detection entity catalogue owns `LeakDetectionRun`, the candidate column is explicitly `run_id`, and `LeakDetectionRunJpaEntity` persists run identity/profile/method/evaluation provenance in `hidra_leak_detection_run`. `LeakDetectionRun` is likewise outside the subject population.

However, unlike `profileId`, the current candidate table has no demonstrated HRA-111 FK from `run_id` to `hidra_leak_detection_run(id)`. Because `runId` is nullable, the final design must explicitly decide whether runless/manual/external candidates are valid and, when a run is supplied, enforce reference integrity deliberately.

### 28.4 Typed Topology reference

`topologyAssetType + topologyAssetId` is a polymorphic cross-module Topology reference. The Leak Detection DDD intentionally forbids owning Pipeline, Facility, PipelineSegment, TopologyNode, Equipment or other Topology business objects.

The candidate therefore must keep only the stable reference plus code/name snapshots. No single subject-model graph edge or cross-module database FK should be invented.

Current persistence requires `topology_asset_type`, `topology_asset_id`, and `topology_asset_code` to be non-null. The domain constructor guards asset ID and code but does **not** guard `topologyAssetType`; blank input is normalized to null and can survive domain construction until persistence failure.

The create service also performs no demonstrated Topology contract lookup validating that the type/id pair resolves to the intended asset or that the supplied code snapshot corresponds to that asset.

### 28.5 Profile/run consistency

`LeakDetectionRunJpaEntity` itself carries `profileId`. When both `runId` and candidate `profileId` are supplied, they describe one detection provenance chain.

The current `LeakDetectionApplicationService.createLeakCandidate()` does not inject profile/run repositories or another validation port. It therefore does not demonstrate that:

- the required `profileId` exists beyond the database FK at save time;
- a supplied `runId` exists;
- the run belongs to the same profile as the candidate;
- the profile/run is in a lifecycle state eligible to generate new candidates.

The target baseline needs an explicit application/persistence rule for these provenance relationships rather than relying on scalar IDs.

### 28.6 External compute identity and idempotency

The extended-capability audit explicitly says leak-detection configuration/run persistence must reconcile **external compute identity and idempotency**. The deferred CPM/gRPC roadmap further requires stable anomaly identity/correlation and states that duplicate streamed anomalies must not create uncontrolled duplicate candidates.

The current `CreateLeakCandidateRequest` and command accept `candidateNumber`, `runId`, `profileId`, and correlation data, but `LeakCandidateRepositoryPort` exposes no candidate-number/external-identity lookup and the base migration has no demonstrated unique candidate-number/idempotency constraint.

The repository evidence does not define whether `candidateNumber` is globally unique, unique per run/profile, or merely a display/business number. HMSR-015 therefore does **not** invent a uniqueness key. It records the idempotency identity as unresolved semantics that must be settled before an external compute adapter is allowed to create candidates reliably.

### 28.7 Confidence and severity

`LeakDetectionApplicationService` derives `severityLevel` with `LeakConfidenceClassifier`: confidence at or above 0.90 maps to CRITICAL, 0.75 to HIGH, 0.50 to MEDIUM, otherwise LOW; null maps to UNKNOWN at classifier level, while `LeakCandidate` itself requires a non-null confidence score.

No current Leak Detection DDD or schema evidence found during HMSR-015 defines a mandatory numeric confidence range such as `[0,1]`. The semantic review therefore does not invent one. If the future CPM/API contract defines normalized probability/confidence bounds, that contract must be made explicit and then enforced consistently.

Because both confidence and severity are persisted, alternate creation/update paths must not silently allow contradictory pairs if severity remains defined as classifier-derived rather than independently assessed. The final design must state which field is authoritative.

### 28.8 Lifecycle and downstream use

`LeakCandidateStatus` provides `NEW`, `UNDER_REVIEW`, `VERIFIED`, `DISMISSED`, `ESCALATED`, and `CLOSED`. The aggregate helper treats NEW/UNDER_REVIEW/VERIFIED as still open.

`LeakDetectionCase.primaryCandidateId` is FK-backed to the candidate and `LeakEscalationReference` may also retain a candidate reference. A candidate is suspicion/evidence, not an Alarm or Incident. Escalation must remain through the neutral Leak Detection escalation model so downstream modules retain ownership of their own lifecycle.

The current public application service demonstrates candidate creation, case opening and case escalation, but no candidate-status transition orchestration was found during HMSR-015. The final lifecycle implementation should prevent arbitrary state rewrites once transition semantics are formalized.

### 28.9 Required revisions

The target baseline cannot be APPROVED until these evidence-backed gaps are resolved:

1. **Required topology type:** enforce persistence-required `topologyAssetType` at a deliberate domain/application boundary.
2. **Typed Topology validation:** validate `topologyAssetType + topologyAssetId` through a Topology-owned contract and govern the code/name snapshots without creating cross-module ownership.
3. **Run reference integrity:** define whether runless candidates are valid; when `runId` is present, validate it against `LeakDetectionRun` and add/retain an appropriate integrity mechanism.
4. **Profile/run provenance consistency:** when a run is supplied, enforce that its profile matches candidate `profileId` and that the relevant profile/run lifecycle permits candidate generation.
5. **External-compute idempotency:** define the stable candidate/anomaly identity and uniqueness scope before CPM/gRPC ingestion so reconnect/replay cannot create uncontrolled duplicates.
6. **Confidence/severity authority:** explicitly define whether severity is always derived from confidence or can be independently assessed, then prevent contradictory persisted pairs under the chosen rule.
7. **Required audit timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required.

HMSR-015 does not change production Java, JPA, Flyway, Topology contracts, CPM/gRPC integration, API/application contracts, tests, profile/run records or candidate data.

### 28.10 Operational interpretation

For SONATRACH/TRC operations, a LeakCandidate is an operational-intelligence suspicion requiring verification, not proof of a physical leak and not authority to actuate the pipeline. Confidence, severity, location/scope snapshots and evidence help operators prioritize investigation and escalation.

Any alarm, incident, workflow or notification consequence must be created through the owning module's governed interface/reference path. Leak Detection itself remains non-actuating decision support.

### 28.11 Review conclusion

**REVISE.** `LeakCandidate` is the correct Leak Detection suspicion aggregate and its two HMS downstream dependencies are sound. `profileId` and `runId` are now semantically resolved to Leak Detection-owned non-subject prerequisites, while the Topology reference remains correctly polymorphic.

The target baseline cannot mark it APPROVED while typed-Topology validation, run/profile provenance integrity, external-compute idempotency identity, and required field enforcement remain unresolved. HMS reconciliation must retain these obligations until an explicitly authorized Leak Detection correction task resolves them or the target semantics are explicitly changed.

## 29. HMSR-016 — organization.OperationalScope review

**Decision:** APPROVED  
**Review code:** HMSR-016  
**Dependency level:** 0  
**Bounded context:** organization  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `organization.ResponsibilityAssignment` through `scopeId`  
**Transitive dependents:** 2  
**Unresolved/non-subject references:** 0

### 29.1 Semantic role and authoritative current design

`OperationalScope` is the Organization-owned canonical registry identity for one validated operational-responsibility target. It is deliberately distinct from the target object itself.

The authoritative current Organization roadmap, verified after ORG-033, defines:

```text
OperationalScopeReference
  type
  targetId
  canonical owner-target registration input

OperationalScope
  id: positive generated Long registry identity
  type
  targetId

ResponsibilityAssignment
  scopeId -> OperationalScope.id
```

This current roadmap/live implementation supersedes older planning examples when they conflict. ADR-0005 remains the accepted architectural decision for ownership/integrity principles, but the Organization roadmap explicitly records that its original wording about storing the typed pair directly on each assignment is stale relative to the implemented registry model.

### 29.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `Long` | Mandatory for persisted domain identity | Positive database-generated registry identifier. It is intentionally not the owner target's native ID and is explicitly separate from Organization's String/UUID identifier policy. |
| `type` | `OperationalScopeType` | Mandatory | Governed target namespace discriminator: `GLOBAL`, `ORGANIZATION_UNIT`, `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, `EQUIPMENT`, or `CUSTOM`. `CUSTOM` is currently ungoverned and rejected. |
| `targetId` | `String` | Null only for GLOBAL | Stable owner-native target identifier. Entity-backed scope types require a nonblank normalized value. |

The `OperationalScope` compact constructor correctly requires a positive registry ID and non-null type, enforces target-less `GLOBAL`, rejects `CUSTOM`, and requires a target ID for governed entity-backed types.

Pre-persistence registration does not misuse a fake or temporary registry ID. `OperationalScopeReference(type, targetId)` is the separate canonical value object for validated owner references before persistence; `OperationalScope` is used only after the database-generated ID exists.

### 29.3 Type and owner-resolution semantics

`OperationalScopeType` correctly separates target namespaces:

- `GLOBAL` — unique target-less Organization-wide applicability marker;
- `ORGANIZATION_UNIT` — resolved inside Organization;
- `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, `EQUIPMENT` — resolved through Topology's exported `TopologyOperationalScopeTargetContract`; 
- `CUSTOM` — intentionally rejected until a separately approved owner namespace/contract exists.

`AuthoritativeOperationalScopeTargetResolverAdapter` supports exactly the governed entity-backed types. It resolves Organization units through `OrganizationUnitRepositoryPort` and Topology-owned targets only through the deliberate public Topology application contract. No Topology domain model, JPA repository or infrastructure implementation crosses into Organization.

`OperationalScopeRegistrationValidator` fails closed when a resolver is unsupported, the target is missing, the resolver returns a mismatched type/ID, or the target is not currently assignable. GLOBAL bypasses owner lookup because it has no target by design.

### 29.4 Persistence identity and uniqueness

`V20260927_001__add_operational_scope_registry.sql` establishes the canonical registry:

```text
id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
scope_type varchar(80) NOT NULL
target_id varchar(120)
```

Database checks match domain shape:

- `id > 0`; 
- GLOBAL requires `target_id IS NULL`; 
- governed entity-backed types require nonblank `target_id`; 
- CUSTOM is not admitted by the table check.

Uniqueness is correct and concurrency-safe at the persistence boundary:

- one registry row per `(scope_type, target_id)` for entity-backed scopes;
- one singleton GLOBAL row;
- `JpaOperationalScopeRegistryRepositoryAdapter.register()` first reuses an existing canonical row, then relies on the unique index as the race guard and reloads the canonical row after a uniqueness race.

That is the appropriate place for global registry uniqueness; no repository lookup is pushed into the domain record.

### 29.5 Responsibility dependency and final greenfield schema

The validated HMS graph correctly contains:

```text
ResponsibilityAssignment.scopeId -> OperationalScope
```

`ResponsibilityAssignment` is the sole direct HMS subject dependent. `OrganizationDelegation` is downstream through `ResponsibilityAssignment`, which accounts for the registered transitive dependency count without creating another direct edge to OperationalScope.

The registry FK uses `ON DELETE RESTRICT`, preserving responsibility history. ORG-032 final greenfield migration `V20260929_006__retire_legacy_operational_scope_columns.sql` removes the obsolete embedded scope columns and makes canonical `ResponsibilityAssignment.scope_id` mandatory after a fail-closed preflight.

The final Organization model therefore has one authoritative responsibility-scope identity path rather than parallel embedded tuples.

### 29.6 Current display, multilingual data and owner lifetime

OperationalScope intentionally stores **no** writable current code/name fields and therefore has no embedded Arabic/French/English label fields to review.

Current code, multilingual name, status and assignability belong to the target-owning bounded context and are resolved on read through the owner resolver. This avoids stale duplicated Topology/Organization display state inside the registry.

When an owner later becomes retired/unassignable, historical scope/assignment rows are preserved. New assignment is blocked and reconciliation reports the owner-lifecycle issue rather than deleting history. ORG-033 end-to-end evidence covers multi-scope responsibility and owner-retirement preservation.

### 29.7 Authorization, Workflow and Audit boundary

The registry itself represents target identity; it does not confer authority.

The completed Organization correction sequence places permission checks, Workflow approval, Audit append, overlap/idempotency and effective-date rules in the responsibility application layer. This keeps `OperationalScope` focused on canonical target identity while preserving the required governance around assigning responsibility.

### 29.8 Schema and architecture verification status

The authoritative Organization roadmap records the operational-scope correction sequence as complete:

- ORG-023 completed under the greenfield/no-deployed-legacy-data decision;
- ORG-027 authorization, Workflow and Audit governance completed with green exact-SHA CI;
- ORG-028 through ORG-033 completed with green exact-SHA CI evidence;
- issue #130 acceptance matrix has no unresolved applicable item and closure is authorized;
- HRA-091 exposes the deliberate Topology operational-scope resolution contract and architecture guardrails allow only that public contract.

Those recorded historical CI results are evidence for the completed Organization correction sequence. HMSR-016 itself does not rerun those old builds and does not claim new CI success from them.

### 29.9 Non-blocking documentation note

ADR-0005 remains authoritative for separation of ownership, typed references, GLOBAL semantics, rejection of ungoverned CUSTOM, owner resolution, history preservation and cross-module integrity principles.

Its older representation of the typed owner pair directly on each responsibility assignment is explicitly marked stale by the current Organization roadmap. A future ADR documentation correction would improve architectural consistency, but this is **not** a semantic defect in the live OperationalScope model because the current roadmap and implementation consistently use the generated registry-ID design.

### 29.10 Review conclusion

**APPROVED.** `OperationalScope` has a coherent three-field identity model, correct separation between pre-persistence owner reference and persisted registry identity, fail-closed owner resolution, correct GLOBAL/CUSTOM semantics, database-enforced shape and uniqueness, idempotent/race-safe registration, deliberate cross-module boundaries, and a single canonical downstream responsibility reference.

No production correction obligation is created by HMSR-016. The current live Organization implementation is suitable as the target semantic baseline for later responsibility/delegation model reviews and data provisioning.

## 30. HMSR-017 — analytics.AnalyticsDataset review

**Decision:** APPROVED  
**Review code:** HMSR-017  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `analytics.AnalyticsDatasetVersion` through `datasetId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 0

### 30.1 Semantic role and ordering rationale

`AnalyticsDataset` is the Analytics-owned metadata aggregate for one curated analytical dataset family. It defines stable identity, localized labels, analytical subject area, dataset/refresh classifications, lineage and quality state, schema/provenance metadata and an optional validity interval.

It is Level 0 because its only domain prerequisite, `AnalyticsSubjectArea`, is retained as an Analytics read/persistence model outside the 123 HMS subject population. `AnalyticsDatasetVersion` is the sole direct HMS subject dependent through `datasetId`.

Analytics remains a derived/read-oriented bounded context. The dataset metadata does not own or mutate Telemetry, Topology, Planning, Monitoring, Alarm, Incident, Integrity, Custody, HSE, Risk, Audit or other source-domain truth.

### 30.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable dataset identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable dataset business code. The current Analytics DDD does not state a uniqueness invariant for dataset code, so HMSR-017 does not invent one. |
| `nameAr` | `String` | Optional | Arabic dataset display label. |
| `nameFr` | `String` | Persistence-required text | French dataset display label. JPA/schema require it, while HRA explicitly classifies generic names/labels as persistence-only text rather than automatic constructor invariants. |
| `nameEn` | `String` | Optional | English dataset display label. |
| `subjectAreaId` | `String` | Mandatory | Same-module reference to Analytics-owned `AnalyticsSubjectArea`. |
| `datasetType` | `AnalyticsDatasetType` | Mandatory | Stable dataset kind: `SNAPSHOT`, `TIME_SERIES`, `AGGREGATE`, `FEATURE_SET`, `TRAINING_DATASET`, `VALIDATION_DATASET`, `DASHBOARD_VIEW`, or `DIGITAL_TWIN_READINESS_VIEW`. |
| `refreshMode` | `AnalyticsRefreshMode` | Mandatory | Refresh policy classification: `MANUAL`, `SCHEDULED`, `EVENT_DRIVEN`, `INCREMENTAL`, or `FULL_REBUILD`. |
| `lineageStatus` | `AnalyticsLineageStatus` | Mandatory | Current lineage completeness state: `DRAFT`, `COMPLETE`, `PARTIAL`, `BROKEN`, or `UNKNOWN`. |
| `qualityStatus` | `AnalyticsQualityStatus` | Mandatory | Current analytical data-quality state: `UNKNOWN`, `READY`, `WARNING`, `FAILED`, or `REJECTED`. |
| `schemaVersion` | `String` | Optional | Dataset schema-version descriptor. |
| `createdFrom` | `String` | Optional provenance text | High-level creation/provenance descriptor; it does not replace version-level source lineage. |
| `validFrom` | `Instant` | Optional | Dataset metadata validity start. |
| `validTo` | `Instant` | Optional | Dataset metadata validity end; when both endpoints exist, must not precede `validFrom`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. HRA intentionally keeps generic audit-timestamp nullability at persistence/application boundaries. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor correctly enforces `id`, `code`, `subjectAreaId`, all four enum/state fields, and `validFrom <= validTo` when both endpoints are present. It normalizes textual values consistently.

### 30.3 Subject-area dependency resolution

HMS-003 classified `subjectAreaId` generically as `CATALOG_OR_VALUE`. Stronger current repository evidence resolves the target precisely without adding an HMS subject edge:

- Analytics DDD defines `AnalyticsSubjectArea` as a main Analytics aggregate/reference concept;
- `AnalyticsSubjectAreaJpaEntity` persists it in `hidra_analytics_subject_area`;
- repository mirror classification retains `AnalyticsSubjectArea` as a `READ_PERSISTENCE_MODEL` outside the 123 HMS subjects;
- HRA-111 installs `fk_hra111_analytics_004`; 
- that FK points `hidra_analytics_dataset.subject_area_id` to `hidra_analytics_subject_area(id)` with `ON DELETE RESTRICT`.

Therefore `subjectAreaId` is a **same-module domain reference to a non-subject Analytics model**, not a generic catalog dependency and not a subject-graph edge.

The Analytics DDD requires unique subject-area codes and states that a subject area's `ownerModule` identifies analytical stewardship rather than transactional ownership. Those rules belong to `AnalyticsSubjectArea`, not to `AnalyticsDataset`.

### 30.4 Dataset/version/lineage ownership

The DDD deliberately separates dataset-family metadata from immutable released data:

```text
AnalyticsDataset
  -> AnalyticsDatasetVersion
       -> AnalyticsDatasetLineage
```

`AnalyticsDatasetVersion` owns `versionNumber`, schema/data hashes, row count, period, quality score and publication state. HRA-111 protects `AnalyticsDatasetVersion.datasetId -> AnalyticsDataset.id` with `ON DELETE RESTRICT`.

`AnalyticsDatasetLineage` owns source-module/object/version/snapshot provenance for a specific dataset version, and HRA-111 protects `datasetVersionId -> AnalyticsDatasetVersion.id`.

This separation correctly satisfies the Analytics DDD rule that published dataset versions preserve reproducibility and lineage. `AnalyticsDataset.createdFrom` is not treated as a replacement for version-level lineage.

The DDD rule that training datasets are immutable after publication is therefore governed through the dataset-version publication boundary. HMSR-017 does not invent an aggregate-level publication flag that the current model does not define.

### 30.5 Creation defaults and lifecycle interpretation

`AnalyticsApplicationService.createAnalyticsDataset()` creates new datasets with:

```text
lineageStatus = DRAFT
qualityStatus = UNKNOWN
validFrom = null
validTo = null
```

These defaults are conservative and semantically compatible with a newly registered derived dataset whose materialized/versioned content and lineage have not yet been published.

The current Analytics DDD does not define a separate `AnalyticsDatasetStatus` lifecycle for this aggregate. Publication/immutability state is represented on `AnalyticsDatasetVersion`, while lineage and quality are represented explicitly on the dataset metadata. HMSR-017 therefore does not introduce a missing-status defect.

### 30.6 Persistence consistency

The live domain and JPA models agree on all 16 declared components.

The base Analytics migration makes `id`, `code`, `name_fr`, `subject_area_id`, `dataset_type`, `refresh_mode`, `lineage_status`, `quality_status`, `created_at`, and `updated_at` non-null. Optional schema/provenance/validity fields remain nullable.

The database has ordinary indexes on dataset code, subject area and audit timestamps. The Analytics DDD does **not** state that dataset code is unique, so the absence of a unique code constraint is not treated as a semantic defect.

HRA's exact-head invariant classification intentionally keeps generic names/labels such as `nameFr` and audit timestamps as persistence-only concerns unless a later domain-specific rule says otherwise. HMSR-017 therefore does not repeat the earlier pattern of incorrectly promoting every `NOT NULL` display field into a domain-constructor invariant.

### 30.7 Enum versus Analytics catalog note

The Analytics DDD lists `DATASET_TYPE` and `REFRESH_MODE` among example `AnalyticsCatalogEntry` families, while the live `AnalyticsDataset` model and schema use the strongly typed enums `AnalyticsDatasetType` and `AnalyticsRefreshMode` directly.

No current dataset FK, application lookup, or seed evidence found during HMSR-017 makes `hidra_analytics_catalog_entry` authoritative for these two fields. The executable model and explicit dataset value lists are internally consistent.

For later data provisioning, this means `DATASET_TYPE` / `REFRESH_MODE` catalog rows must **not** be assumed to be required relational parents of `AnalyticsDataset` unless an explicit Analytics design change establishes that contract. This is a documentation/provisioning caution, not a production-model correction obligation.

### 30.8 Multilingual and operational interpretation

Dataset labels are display metadata only. Current authoritative analytical facts remain in source modules and versioned dataset content/lineage.

For SONATRACH/TRC, an AnalyticsDataset may represent curated trusted telemetry windows, plan-versus-actual aggregates, pipeline performance views, integrity trends, custody reconciliation sets, HSE/risk analytical views, or future digital-twin-readiness inputs. The dataset is always derived evidence; it does not become the operational source of truth.

Analytics may use the dataset to compute metrics, projections, KPI values, trends and advisory insights, but any operational decision/action remains owned by the relevant business module.

### 30.9 Review conclusion

**APPROVED.** `AnalyticsDataset` has a coherent 16-field model, correct Level-0 placement, correct `AnalyticsSubjectArea` same-module non-subject dependency, correct validity ordering, conservative creation defaults, and a clean separation between dataset metadata and version-level publication/lineage.

No production correction obligation is created by HMSR-017. The only retained note is for future provisioning/documentation: do not treat the example `DATASET_TYPE` / `REFRESH_MODE` catalog families as relational parents of the enum-backed dataset fields without a separately authorized design decision.

## 31. HMSR-018 — analytics.MetricEvaluationRun review

**Decision:** REVISE  
**Review code:** HMSR-018  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `analytics.MetricValue` through `metricEvaluationRunId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 1 — `metricDefinitionVersionId`, now semantically resolved to Analytics-owned `MetricDefinitionVersion` outside the 123 HMS subject set

### 31.1 Semantic role and ordering rationale

`MetricEvaluationRun` is the Analytics-owned execution record for calculating one versioned metric over a defined time period and analytical scope. It carries the exact metric-definition version, execution lifecycle, scope discriminator/reference, run timing, processing counters, diagnostic context and correlation identity.

It is Level 0 because its required upstream metric-definition version is a retained Analytics read/persistence model outside the HMS subject population. `MetricValue` is the one direct HMS subject dependent through `metricEvaluationRunId`.

Analytics remains a derived/read-oriented bounded context. A metric evaluation may calculate operational indicators from trusted source history, but it must not mutate the source modules or convert derived values into operational truth.

### 31.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable run identity and persistence primary key. |
| `metricDefinitionVersionId` | `String` | Mandatory | Exact Analytics-owned metric-definition version used for calculation. |
| `runStatus` | `AnalyticsRunStatus` | Mandatory | Run lifecycle: `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, or `CANCELLED`. |
| `periodStart` | `Instant` | Mandatory | Evaluation-period start. |
| `periodEnd` | `Instant` | Mandatory | Evaluation-period end; current domain rule allows equality and rejects `periodEnd < periodStart`. |
| `scopeType` | `String` | Mandatory in persistence and DDD semantics | Discriminator identifying the governed analytical scope namespace. |
| `scopeId` | `String` | Scope-dependent optional | Stable target reference selected by `scopeType`; some module-defined/global scopes may legitimately be identifier-less. |
| `startedAt` | `Instant` | Mandatory | Execution start timestamp. |
| `completedAt` | `Instant` | Optional | Completion timestamp once the run reaches an applicable terminal state. |
| `recordsRead` | `Long` | Optional | Number of source records read when the calculation engine records this metric. |
| `recordsProduced` | `Long` | Optional | Number of derived result records produced. |
| `errorCode` | `String` | Optional | Machine-readable diagnostic code for failed/warning execution. |
| `errorMessage` | `String` | Optional | Human-readable diagnostic context. |
| `correlationId` | `String` | Optional technical reference | End-to-end trace/correlation identity. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Run creation timestamp. |

The current constructor correctly enforces `id`, `metricDefinitionVersionId`, `runStatus`, `periodStart`, `periodEnd`, `startedAt`, and `periodStart <= periodEnd`, and normalizes textual values.

### 31.3 Metric-definition-version dependency resolution

HMS-003 left `metricDefinitionVersionId` unresolved because `MetricDefinitionVersion` is outside the 123 HMS subject set. Stronger current evidence resolves the target without adding a graph edge:

- Analytics DDD defines `MetricDefinitionVersion` as the versioned metric formula/calculation policy;
- `MetricDefinitionVersionJpaEntity` persists it in `hidra_analytics_metric_definition_version`;
- repository mirror classification retains `MetricDefinitionVersion` as an Analytics `READ_PERSISTENCE_MODEL` outside the HMS subjects;
- HRA-111 installs `fk_hra111_analytics_020`; 
- that FK points `hidra_analytics_metric_evaluation_run.metric_definition_version_id` to `hidra_analytics_metric_definition_version(id)` with `ON DELETE RESTRICT`.

Therefore the run correctly references an immutable/versioned calculation definition rather than only a mutable metric identity.

The DDD also gives metric-definition versions `validFrom/validTo` and states that validity periods must not overlap for active calculation use. The current run service does not load the version before starting a run, so the database FK proves existence only; it does not prove the version is eligible for the requested evaluation period.

### 31.4 Analytical scope semantics

The Analytics DDD explicitly states:

```text
scope must reference a topology asset, organization unit, product, or module-defined analytical scope
```

and the general Analytics scope section lists examples including `NETWORK`, `PIPELINE_SYSTEM`, `PIPELINE`, `PIPELINE_SEGMENT`, `STATION`, `FACILITY`, `EQUIPMENT`, `MEASUREMENT_POINT`, `ORGANIZATION_UNIT`, `PRODUCT`, `CUSTODY_TRANSFER_POINT`, `HSE_SITE`, and `RISK_AREA`.

`scopeType + scopeId` is therefore a typed/polymorphic reference pair. It must not be collapsed into one HMS subject edge or cross-module database FK.

However, the live implementation currently has two consistency gaps:

- `scope_type` is `NOT NULL` in JPA/schema and is semantically required to interpret the scope, but `MetricEvaluationRun` does not reject null/blank `scopeType`; 
- `AnalyticsApplicationService.runMetricEvaluation()` accepts `scopeType/scopeId` directly and performs no demonstrated scope-type vocabulary or target-resolution validation.

The final design needs a neutral lookup/validation contract appropriate to the selected scope namespace while preserving foreign-module ownership.

### 31.5 Run creation and lifecycle coverage

`AnalyticsApplicationService.runMetricEvaluation()` currently creates a run with:

```text
runStatus = RUNNING
startedAt = now
completedAt = null
recordsRead = 0
recordsProduced = 0
errorCode = null
errorMessage = null
createdAt = now
```

Those are coherent start-state defaults.

Repository search during HMSR-018 found no current application completion/failure/cancellation path for `MetricEvaluationRun`. The DDD requires failed runs to be auditable, but the current public orchestration only demonstrates run creation and therefore does not yet demonstrate how `FAILED`, `COMPLETED_WITH_WARNINGS`, `COMPLETED`, or `CANCELLED` are reached while preserving diagnostics and completion metadata.

HMSR-018 does **not** invent a transition matrix that the Analytics DDD does not define. It records only the missing lifecycle orchestration needed to make the existing status/diagnostic fields operationally meaningful.

### 31.6 Persistence consistency and deliberately unasserted invariants

The live domain and JPA models agree on all 15 declared components. The base Analytics migration makes `id`, `metric_definition_version_id`, `run_status`, `period_start`, `period_end`, `scope_type`, `started_at`, and `created_at` non-null. `scope_id`, completion fields, counters and diagnostics are nullable.

HRA-051 already enforces the only explicitly approved local temporal rule for this model: `periodStart <= periodEnd`.

No current Analytics source reviewed by HMSR-018 defines either of the following as target invariants:

- `completedAt >= startedAt`; 
- non-negative `recordsRead` / `recordsProduced`.

Those constraints may be reasonable future decisions, but they are **not** added to the semantic baseline by this review because the repository evidence does not currently establish them.

Likewise, HRA intentionally keeps generic audit-timestamp nullability at the persistence/application boundary, so `createdAt` is not promoted into an additional constructor correction obligation merely because the column is `NOT NULL`.

### 31.7 Failed-run auditability

The DDD's explicit rule is that a failed run must be auditable. The model already contains the necessary evidence carriers: run status, start/completion timestamps, error code/message, correlation ID and stable run identity.

The gap is orchestration rather than field shape: no current service path found during HMSR-018 demonstrates transition to `FAILED` while preserving diagnostic context. The correction should therefore live at the application/execution boundary, not as a speculative constructor rule requiring particular error strings for every failed instance.

### 31.8 Required revisions

The target baseline cannot be APPROVED until these evidence-backed execution rules are made explicit and enforceable:

1. **Required scope discriminator:** enforce nonblank `scopeType` at a deliberate domain/application boundary so invalid runs do not survive until database persistence.
2. **Typed scope validation:** validate the `scopeType + scopeId` pair through a neutral Analytics/owning-module lookup contract while preserving foreign-module ownership and allowing explicitly identifier-less scope types where defined.
3. **Metric-version eligibility:** before starting calculation, resolve `metricDefinitionVersionId` and enforce whatever version-validity/active-calculation rule is authoritative for the requested period; FK existence alone is insufficient.
4. **Lifecycle completion/failure orchestration:** provide a governed path for terminal run outcomes so `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, and `CANCELLED` can preserve `completedAt`, diagnostics, counters and correlation/audit evidence.
5. **Failed-run auditability:** ensure failure execution records retain enough diagnostic/correlation evidence to satisfy the DDD rule without inventing unsupported constructor-level text requirements.

HMSR-018 does not change production Java, JPA, Flyway, scope contracts, metric-definition versions, API/application contracts, tests, evaluation runs or metric values.

### 31.9 Operational interpretation

For SONATRACH/TRC, a `MetricEvaluationRun` may calculate pipeline utilization, telemetry availability, pressure deviation, incident closure duration, alarm acknowledgement time, energy-per-volume, custody variance, HSE case rates or similar derived indicators over a governed operational scope and period.

The run is analytical evidence only. It cannot acknowledge alarms, modify plans, close incidents, alter risk ratings, change Topology, validate Telemetry, or trigger SCADA/PLC/RTU/SIS/ESD action.

### 31.10 Review conclusion

**REVISE.** `MetricEvaluationRun` has the correct 15-field execution shape, correct Level-0 placement, correct direct dependency on `MetricValue`, and a now-resolved same-module dependency on non-subject `MetricDefinitionVersion`. Its period-order invariant and start-state defaults are coherent.

The target baseline cannot mark it APPROVED while the required scope discriminator/pair is unchecked, metric-version eligibility is not validated before calculation, and the public application contract does not demonstrate terminal/failure orchestration needed for auditability. HMS reconciliation must retain these obligations until an explicitly authorized Analytics correction task resolves them or the target semantics are explicitly changed.

## 32. HMSR-019 — configuration.ConfigurationDefinition review

**Decision:** REVISE  
**Review code:** HMSR-019  
**Dependency level:** 0  
**Bounded context:** configuration  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `configuration.ConfigurationValue` through `definitionId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 1 — `namespaceId`, now semantically resolved to Configuration-owned `ConfigurationNamespace` outside the 123 HMS subject set

### 32.1 Semantic role and ordering rationale

`ConfigurationDefinition` is the Configuration-owned definition of one governed runtime setting. It establishes the namespace/key identity, multilingual display metadata, value type, sensitivity, lifecycle state, scoping/approval flags, optional default value and descriptive/audit metadata.

It is Level 0 because its required namespace prerequisite, `ConfigurationNamespace`, is retained as a Configuration read/persistence model outside the HMS subject population. `ConfigurationValue` is the one direct HMS subject dependent through `definitionId`.

The Configuration DDD explicitly limits this bounded context to governed runtime settings, feature flags, scoped overrides, activation metadata, operator preferences and technical references. Module-owned business taxonomies remain owned by their source modules.

### 32.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable definition identity and persistence primary key. |
| `namespaceId` | `String` | Mandatory | Same-module reference to the owning `ConfigurationNamespace`. |
| `key` | `String` | Mandatory | Governed setting key within the selected namespace. No current DDD evidence establishes a uniqueness scope, so HMSR-019 does not invent one. |
| `displayNameFr` | `String` | Persistence-required text | French display label. JPA/schema require it; HRA keeps generic display labels at persistence/application boundaries unless a specific domain rule states otherwise. |
| `displayNameAr` | `String` | Optional | Arabic display label. |
| `displayNameEn` | `String` | Optional | English display label. |
| `valueType` | `ConfigurationValueType` | Mandatory | Setting value kind: `STRING`, `NUMBER`, `BOOLEAN`, `DATE`, `DURATION`, `JSON`, `REFERENCE`, or `LIST`. |
| `sensitivity` | `ConfigurationSensitivity` | Mandatory | Sensitivity policy: `PUBLIC`, `INTERNAL`, `RESTRICTED`, or `SECRET_REFERENCE_ONLY`. |
| `status` | `ConfigurationDefinitionStatus` | Mandatory | Definition lifecycle: `DRAFT`, `ACTIVE`, `DEPRECATED`, or `RETIRED`. |
| `scoped` | `boolean` | Mandatory persisted state | Indicates that governed scoped override behavior may apply. |
| `requiresApproval` | `boolean` | Mandatory persisted state | Indicates that changes to values governed by this definition require approval workflow/governance. |
| `defaultValue` | `String` | Optional | Default setting value metadata. It remains subject to the Configuration module's no-secret-material boundary. |
| `description` | `String` | Optional | Human-readable definition description. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The current constructor correctly enforces nonblank `id`, `namespaceId`, and `key`, plus non-null `valueType`, `sensitivity`, and `status`. It normalizes all textual values and exposes `secretReferenceOnly()` as a semantic helper.

### 32.3 Namespace dependency resolution

HMS-003 left `namespaceId` unresolved because `ConfigurationNamespace` is outside the 123 HMS subject set. Stronger repository evidence resolves the target without adding a subject edge:

- the active Configuration DDD lists `ConfigurationNamespace` as an owned entity and `ConfigurationDefinition` as a definition of a governed setting;
- `ConfigurationNamespaceJpaEntity` persists namespace identity in `hidra_configuration_namespace`;
- repository mirror classification retains `ConfigurationNamespace` as a Configuration `READ_PERSISTENCE_MODEL` outside the HMS subject population;
- HRA-111 installs `fk_hra111_configuration_003`; 
- that FK points `hidra_configuration_definition.namespace_id` to `hidra_configuration_namespace(id)` with `ON DELETE RESTRICT`.

Therefore `namespaceId` is a required same-module domain reference to a non-subject Configuration model, not an unresolved scalar and not an HMS graph edge.

The FK proves namespace-row existence. The current Configuration DDD does not state an additional namespace-status eligibility rule for definition creation, so HMSR-019 does not invent one.

### 32.4 Definition/version/value boundary

The Configuration entity catalogue separates:

```text
ConfigurationDefinition
ConfigurationDefinitionVersion
ConfigurationValue
```

`ConfigurationDefinitionVersion` is retained as a read/persistence model outside the HMS subjects and owns version number, schema JSON, version-specific default value, validation summary, author/time and active marker. HRA-111 protects `ConfigurationDefinitionVersion.definitionId -> ConfigurationDefinition.id`.

`ConfigurationValue` is the direct HMS dependent and stores the effective environment-specific value, optional definition-version reference, secret-reference field, lifecycle state and effective interval.

This split is semantically coherent: stable definition identity remains separate from versioned schema/default metadata and from effective deployed values. HMSR-019 does not add a subject edge to `ConfigurationDefinitionVersion` because it is outside the 123-model review population.

### 32.5 Configuration ownership boundary

`ConfigurationApplicationService.createConfigurationDefinition()` invokes `ConfigurationValueGuard.ensureAllowedDefinition(command.key())` before saving the definition. That guard delegates to `ConfigurationBoundaryPolicy.isForbiddenBusinessTaxonomy(...)` and rejects known module-owned concepts such as facility/equipment types, telemetry quality codes, workflow definitions, monitoring thresholds, alarm severity, incident classification, HSE obligations, custody calculation formula, asset maintenance strategy, identity permission and organization hierarchy.

This aligns with the DDD's core rule that Configuration must not become a generic shared business-taxonomy module.

The current policy is intentionally a boundary guard, not evidence that the listed strings form a complete business taxonomy catalogue. HMSR-019 therefore does not expand that heuristic list.

### 32.6 Secret-reference boundary and default-value gap

The live Configuration code establishes an explicit secret boundary:

- `ConfigurationSensitivity` contains `SECRET_REFERENCE_ONLY`; 
- `ConfigurationDefinition.secretReferenceOnly()` identifies definitions with that policy;
- `ConfigurationValueGuard` is documented as guarding configuration values against secret-value misuse;
- `ConfigurationValueGuard.ensureNoSecretMaterial(rawValue)` throws when persisted configuration value text appears to contain secret material;
- `ConfigurationValue` has a dedicated `secretReference` field separate from `rawValue`/`jsonValue`.

However, `createConfigurationDefinition()` validates only the definition key through `ensureAllowedDefinition(...)`. It accepts `command.defaultValue()` and persists it directly into `ConfigurationDefinition.defaultValue` without applying the module's existing no-secret-material guard.

This permits the definition-level default path to bypass the same Configuration secret boundary that the effective-value path tries to enforce. The risk is especially direct for `SECRET_REFERENCE_ONLY` definitions: the model exposes the sensitivity but does not prevent an actual secret-like default from being persisted in the ordinary `default_value` column.

The correction should preserve the distinction between a **secret reference** and secret material. HMSR-019 does not require a specific vault URI format or secret-detection algorithm; it requires the existing secret-reference-only policy to apply consistently to definition defaults.

### 32.7 Persistence consistency and deliberately unasserted rules

The live domain and JPA models agree on all 15 declared components. The base Configuration migration makes `id`, `namespace_id`, `key`, `display_name_fr`, `value_type`, `sensitivity`, `status`, `scoped`, `requires_approval`, `created_at`, and `updated_at` non-null. `default_value` and `description` remain nullable.

The schema has an ordinary index on `namespace_id`, but no current repository evidence reviewed by HMSR-019 states that `key` is globally unique or unique within a namespace. No such uniqueness obligation is invented.

Likewise, the active Configuration DDD does not define a value-type parser/validator for `defaultValue`, a mandatory activation transition matrix, or a namespace-status prerequisite for creation. Those may be future design decisions, but they are not added to the target semantic baseline without source evidence.

Generic display-name and audit-timestamp nullability remain persistence/application concerns under the existing HRA invariant policy and are not promoted into new constructor obligations merely because the columns are `NOT NULL`.

### 32.8 Required revision

The model shape, dependency direction and ownership boundary are otherwise coherent. One evidence-backed correction remains:

1. **Apply the secret-reference boundary to definition defaults:** `ConfigurationDefinition.defaultValue` must not provide a path for persisting secret material that is forbidden for `ConfigurationValue.rawValue`. At minimum, creation/update of `SECRET_REFERENCE_ONLY` definitions and any general definition-default validation must consistently preserve reference-only secret semantics.

HMSR-019 does not change production Java, JPA, Flyway, secret stores, namespace data, API/application contracts, tests, configuration definitions, versions or values.

### 32.9 Operational interpretation

For SONATRACH/TRC, a ConfigurationDefinition may govern runtime limits, polling/refresh behavior, operational UI preferences, integration tuning, feature/runtime parameters or other controlled technical settings. It must not redefine the business taxonomy or become a storage location for credentials.

`requiresApproval` represents governance metadata only. It does not itself constitute Workflow approval, and any actual change-approval/deployment process remains governed by the corresponding Configuration change/deployment and Workflow/Audit mechanisms.

### 32.10 Review conclusion

**REVISE.** `ConfigurationDefinition` has a coherent 15-field model, correct Level-0 placement, correct `ConfigurationNamespace` same-module non-subject dependency, appropriate DRAFT creation, and an explicit boundary against absorbing module-owned business taxonomies.

The target baseline cannot mark it APPROVED while definition-level `defaultValue` can bypass the module's established no-secret-material / secret-reference-only boundary. HMS reconciliation must retain this obligation until an explicitly authorized Configuration correction task resolves it or the target semantics are explicitly changed.

## 33. HMSR-020 — custody.CustodyMeasurementPeriod review

**Decision:** REVISE  
**Review code:** HMSR-020  
**Dependency level:** 0  
**Bounded context:** custody  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `custody.CustodyTransferTicket` through `measurementPeriodId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 2 — `agreementId` and `transferPointId`, now semantically resolved to Custody-owned read/persistence models outside the 123 HMS subject set

### 33.1 Semantic role and ordering rationale

`CustodyMeasurementPeriod` is the Custody-owned fiscal/official time window under which accepted transfer evidence is grouped and governed. It binds a period code and closed time interval to one Custody agreement and one official Custody transfer point, while carrying lifecycle state plus optional lock/approval audit metadata.

It is Level 0 because its required domain prerequisites, `CustodyAgreement` and `CustodyTransferPoint`, are retained Custody read/persistence models outside the HMS subject population. `CustodyTransferTicket` is the one direct HMS subject dependent through `measurementPeriodId`.

The Custody DDD keeps this model on the official-transfer side of the architecture boundary: Telemetry owns observed readings, Planning owns expected state, Custody owns officially transferred/accepted evidence, and Finance/ERP ownership remains outside Custody.

### 33.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable measurement-period identity and persistence primary key. |
| `periodCode` | `String` | Mandatory | Business code identifying the fiscal/official measurement period. Current Custody DDD/schema do not state a uniqueness scope, so HMSR-020 does not invent one. |
| `agreementId` | `String` | Mandatory | Same-module reference to the governing `CustodyAgreement`. |
| `transferPointId` | `String` | Mandatory | Same-module reference to the official `CustodyTransferPoint` for the period. |
| `periodStart` | `Instant` | Mandatory | Start instant of the measurement period. |
| `periodEnd` | `Instant` | Mandatory | End instant of the measurement period. Current repository evidence enforces only that it is not before `periodStart`. |
| `status` | `CustodyPeriodStatus` | Mandatory | Period lifecycle state: `OPEN`, `LOCKED`, `CALCULATED`, `APPROVED`, `CLOSED`, `REOPENED`, or `CANCELLED`. |
| `lockedByActorId` | `String` | Optional cross-module actor reference | Identity/platform actor that performed the lock action when such lifecycle metadata exists. |
| `lockedAt` | `Instant` | Optional | Lock timestamp. |
| `approvedByActorId` | `String` | Optional cross-module actor reference | Identity/platform actor associated with approval when such lifecycle metadata exists. |
| `approvedAt` | `Instant` | Optional | Approval timestamp. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The current compact constructor correctly rejects blank `id`, `periodCode`, `agreementId`, and `transferPointId`; requires non-null `periodStart`, `periodEnd`, and `status`; enforces `periodStart <= periodEnd`; and normalizes textual IDs. The creation service conservatively opens new periods as `OPEN` with lock/approval metadata unset.

### 33.3 Agreement and transfer-point dependency resolution

HMS-003 left `agreementId` and `transferPointId` unresolved because their targets are outside the 123 HMS subject set. Stronger current repository evidence resolves both without adding subject-model graph edges:

- the Custody DDD explicitly owns `CustodyAgreement`, `CustodyTransferPoint`, and `CustodyMeasurementPeriod`;
- the DDD describes `CustodyAgreement` as the agreement governing custody transfer and `CustodyTransferPoint` as the official transfer point;
- repository mirror classification retains both targets as Custody read/persistence models outside the HMS subject population;
- HRA-111 installs `fk_hra111_custody_012` for `hidra_custody_measurement_period.agreement_id -> hidra_custody_agreement(id)`;
- HRA-111 installs `fk_hra111_custody_013` for `hidra_custody_measurement_period.transfer_point_id -> hidra_custody_transfer_point(id)`;
- both constraints use `ON DELETE RESTRICT`.

Therefore both fields are required same-module domain references to non-subject Custody models. They remain outside the HMS graph while their row existence is protected at the database boundary.

`lockedByActorId` and `approvedByActorId` remain cross-module Identity/platform actor references. They are not converted into Custody ownership or cross-module database foreign keys.

### 33.4 Measurement-period downstream boundary

The validated HMS graph contains one direct subject-model edge:

```text
CustodyTransferTicket.measurementPeriodId
    -> CustodyMeasurementPeriod.id
```

HRA-111 also protects several Custody read/persistence-model references to the period, including batch, measurement snapshot, meter-run snapshot, quality sample, quantity calculation, reconciliation, and transfer ticket records.

That broader persistence fan-out is semantically appropriate: a fiscal/official period acts as a stable evidence container. It does not change the HMS register's direct-subject dependent count because those additional models are outside the 123-subject population.

### 33.5 Agreement/transfer-point consistency gap

Current persistence verifies the two required references independently, but stronger same-module evidence establishes a relationship between them:

```text
CustodyAgreement.transferPointId
    -> CustodyTransferPoint.id

CustodyMeasurementPeriod.agreementId
    -> CustodyAgreement.id

CustodyMeasurementPeriod.transferPointId
    -> CustodyTransferPoint.id
```

`CustodyAgreement.transferPointId` is mandatory in JPA/schema and HRA-111 protects it with a same-module FK. However, `OpenCustodyMeasurementPeriodCommand` accepts `agreementId` and `transferPointId` independently, and `CustodyApplicationService.openMeasurementPeriod()` constructs/saves the period without demonstrating that the selected agreement governs the selected transfer point.

The database also enforces only independent FK existence. It can therefore accept an internally inconsistent period whose `agreementId` names an agreement for transfer point A while `transferPointId` names transfer point B.

For an official custody period, that breaks the repository's own relationship structure: the period would claim two incompatible same-module ownership facts at once.

### 33.6 Temporal and lifecycle semantics deliberately not invented

The current domain invariant is:

```text
periodStart <= periodEnd
```

The Custody DDD does not state a stricter exclusive-end or non-zero-duration rule for this model, so HMSR-020 does not import PlanningPeriod semantics or require `periodStart < periodEnd`.

Likewise, current repository evidence does not define a complete allowed transition matrix among `OPEN`, `LOCKED`, `CALCULATED`, `APPROVED`, `CLOSED`, `REOPENED`, and `CANCELLED`. The model exposes `closedLifecycle()` for `CLOSED`/`CANCELLED`, but no current source proves exact transition prerequisites.

The presence of `lockedByActorId + lockedAt` and `approvedByActorId + approvedAt` strongly identifies lifecycle audit metadata, but no active Custody DDD/application contract reviewed here states the exact pair-presence rules for every status. HMSR-020 therefore does not invent constructor constraints such as “LOCKED must always carry both lock fields” or “APPROVED must always carry both approval fields.”

Similarly, `CustodyAgreement` has status and validity dates and `CustodyTransferPoint` has status/effective dates, but the active Custody DDD does not specify the exact eligibility/temporal-containment policy required when opening a period. HMSR-020 records no unproven ACTIVE-only or date-containment rule.

### 33.7 Persistence and application consistency

The live domain and JPA models agree on all 13 declared components.

The base Custody migration makes `id`, `period_code`, `agreement_id`, `transfer_point_id`, `period_start`, `period_end`, `status`, `created_at`, and `updated_at` non-null. Lock/approval actor and timestamp fields are nullable.

The application boundary accepts exactly the five inputs needed to open a period:

```text
periodCode
agreementId
transferPointId
periodStart
periodEnd
```

and creates `status = OPEN` with null lock/approval metadata. That creation default is conservative.

No current schema evidence establishes global or scoped uniqueness for `periodCode`, and no current Custody DDD rule states one, so no uniqueness obligation is added.

Generic `createdAt`/`updatedAt` requirements remain persistence/application-boundary concerns under the existing HRA invariant policy rather than new constructor obligations.

### 33.8 Required revision

The model shape, time invariant, ownership boundary, and creation default are otherwise coherent. One evidence-backed correction remains:

1. **Enforce agreement/transfer-point coherence when opening or persisting a measurement period.** A `CustodyMeasurementPeriod` must not reference a `CustodyAgreement` whose mandatory `transferPointId` identifies a different transfer point from the period's own `transferPointId`. The correction must fail closed at an appropriate Custody-owned application/domain/persistence boundary; HMSR-020 does not prescribe a repository-port shape or a composite database constraint without a separately authorized implementation task.

HMSR-020 does not change production Java, JPA, Flyway, API/application contracts, tests, Custody agreements/points/periods, or provisioned data.

### 33.9 SONATRACH/TRC operational interpretation

For SONATRACH/TRC pipeline operations, the measurement period is the governed fiscal/official window against which accepted metering, quantity, quality, reconciliation, and transfer-ticket evidence can be associated.

The period is not a telemetry sampling interval and does not own raw SCADA/telemetry truth. Its agreement and official transfer point must identify one coherent custody-transfer context before downstream official evidence is attached.

Locking, calculation, approval, closing, reopening, and cancellation are governance/lifecycle concepts; their detailed transition and authorization rules must come from explicit Custody/Workflow/Audit contracts rather than being guessed during semantic review.

### 33.10 Review conclusion

**REVISE.** `CustodyMeasurementPeriod` has a coherent 13-field model, correct Level-0 placement, correct temporal-order guard, conservative OPEN creation, and correctly resolvable non-subject dependencies on `CustodyAgreement` and `CustodyTransferPoint`.

The target baseline cannot mark it APPROVED while agreement existence and transfer-point existence are checked independently but their required same-module relationship is not checked. HMS reconciliation must retain the agreement/transfer-point coherence obligation until an explicitly authorized Custody correction task resolves it or the target semantics are explicitly changed.

## 34. HMSR-021 — integrity.PipelineDefect review

**Decision:** REVISE  
**Review code:** HMSR-021  
**Dependency level:** 0  
**Bounded context:** integrity  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `integrity.IntegrityCase` through `primaryDefectId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 1 — `sourceFindingId`, now semantically resolved to Integrity-owned `InspectionFinding` outside the 123 HMS subject set

### 34.1 Semantic role and ordering rationale

`PipelineDefect` is the Integrity-owned engineering record for a detected pipeline/topology defect. It carries stable defect identity, Integrity classification and lifecycle state, a neutral Topology asset reference, optional location descriptors, detection/closure timing, optional source-finding provenance and audit metadata.

It is Level 0 because none of its prerequisites are another HMS subject model. Its same-module catalog and source-finding prerequisites are retained read/persistence models outside the 123-model subject population, while its Topology target is deliberately represented by a neutral cross-module typed reference.

`IntegrityCase` is the one direct HMS subject dependent through optional `primaryDefectId`.

The Integrity DDD is explicit that Integrity owns technical condition, engineering assessment and defect records, while Topology owns the physical pipeline/facility/equipment identity and Assets owns maintenance execution.

### 34.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable PipelineDefect identity and persistence primary key. |
| `defectNumber` | `String` | Mandatory | Integrity business identifier for the defect record. Current DDD/schema do not state a uniqueness scope, so HMSR-021 does not invent one. |
| `defectTypeId` | `String` | Mandatory | Integrity-owned catalog reference classifying the defect. |
| `threatType` | `ThreatType` | Optional | Engineering threat family: corrosion, mechanical/third-party damage, ground movement, fatigue, coating/CP failure, manufacturing, construction, or unknown. |
| `status` | `DefectStatus` | Mandatory | Defect lifecycle state: `OPEN`, `UNDER_ASSESSMENT`, `MONITORED`, `RECOMMENDED_FOR_REPAIR`, `REPAIRED`, `CLOSED`, or `DISMISSED`. |
| `severity` | `FindingSeverity` | Optional | Current engineering severity classification: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`, or `UNKNOWN`. |
| `topologyAssetTypeCode` | `String` | Mandatory | Neutral discriminator identifying the referenced Topology asset namespace/type. |
| `topologyAssetId` | `String` | Mandatory | Stable neutral identifier of the referenced Topology-owned asset. |
| `topologyAssetCodeSnapshot` | `String` | Optional snapshot | Non-authoritative historical/display code snapshot for the referenced Topology asset. |
| `kilometerPoint` | `BigDecimal` | Optional | Linear-reference location descriptor when applicable to the referenced asset. |
| `latitude` | `BigDecimal` | Optional | Geographic latitude descriptor. |
| `longitude` | `BigDecimal` | Optional | Geographic longitude descriptor. |
| `description` | `String` | Optional | Engineering description of the defect. |
| `detectedAt` | `Instant` | Mandatory | Detection/recognition timestamp for the defect. |
| `closedAt` | `Instant` | Optional | Closure timestamp when lifecycle processing records one. |
| `sourceFindingId` | `String` | Optional | Same-module provenance reference to the originating `InspectionFinding`, when a defect is derived from an inspection finding. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor correctly rejects blank `id`, `defectNumber`, `defectTypeId`, `topologyAssetTypeCode`, and `topologyAssetId`; requires non-null `status` and `detectedAt`; normalizes textual values; and exposes `openLifecycle()` as false only for `CLOSED` and `DISMISSED`.

### 34.3 Defect-type dependency resolution

HMS-003 classified `defectTypeId` only as a generic catalog/value dependency. Stronger current persistence evidence resolves its same-module owner without adding an HMS graph edge:

- Integrity DDD owns `IntegrityCatalogEntry`;
- `PipelineDefectJpaEntity.defectTypeId` maps to `hidra_integrity_pipeline_defect.defect_type_id`;
- repository mirror classification retains `IntegrityCatalogEntry` as an Integrity read/persistence model outside the 123 HMS subjects;
- HRA-111 installs `fk_hra111_integrity_019`;
- that FK points `defect_type_id` to `hidra_integrity_catalog_entry(id)` with `ON DELETE RESTRICT`.

The FK proves catalog-row existence only. The current Integrity DDD reviewed here does not define a defect-type catalog family name or active-entry eligibility rule, so HMSR-021 does not invent one.

### 34.4 Neutral Topology reference boundary

The Integrity DDD explicitly requires Topology references to remain neutral:

```text
topologyAssetTypeCode
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetNameSnapshot
```

and forbids Integrity from importing Topology domain models, JPA entities or repositories.

`PipelineDefect` follows that ownership rule with the typed `topologyAssetTypeCode + topologyAssetId` pair plus an optional code snapshot. No cross-module database FK is appropriate.

The current PipelineDefect constructor ensures the discriminator and ID are nonblank. The repository does not currently expose a PipelineDefect creation/update use case through `IntegrityApplicationService` or the Integrity REST controller, so HMSR-021 does not invent a Topology lookup contract or allowed type-code vocabulary solely from the stored pair.

The snapshot is non-authoritative and must not become an alternative source of Topology truth.

### 34.5 Source-finding provenance resolution and integrity gap

HMS-003 left `sourceFindingId` unresolved because `InspectionFinding` is outside the 123 HMS subject set. Stronger evidence resolves the target:

- the Integrity DDD owns both `InspectionFinding` and `PipelineDefect`;
- `InspectionFindingJpaEntity` persists in `hidra_integrity_inspection_finding`;
- repository mirror classification retains `InspectionFinding` as an Integrity read/persistence model outside the HMS subject population;
- `PipelineDefectJpaEntity.sourceFindingId` persists as nullable `source_finding_id`;
- the base migration creates an ordinary index on `source_finding_id`.

No live Flyway constraint reviewed by HMSR-021 links:

```text
hidra_integrity_pipeline_defect.source_finding_id
    -> hidra_integrity_inspection_finding.id
```

and no PipelineDefect application write service currently demonstrates a fail-closed existence check before persistence.

Therefore a non-null `sourceFindingId` can currently become dangling provenance even though its semantic target is a same-module Integrity record.

`InspectionFinding` also carries optional `linkedDefectId`, but current DDD evidence does not define whether the two optional links must always be reciprocal, one-to-one or independently usable. HMSR-021 therefore does not invent bidirectional synchronization semantics.

### 34.6 Downstream IntegrityCase boundary

The validated HMS graph contains the direct subject-model edge:

```text
IntegrityCase.primaryDefectId
    -> PipelineDefect.id
```

`primaryDefectId` is optional on IntegrityCase, so the graph records a semantic dependency without making PipelineDefect depend on IntegrityCase.

Persistence-only Integrity models such as defect assessments, measurements, remaining-life estimates and recommendations also use defect references where applicable. These downstream records reinforce PipelineDefect as the stable defect identity; they do not change its Level-0 placement.

### 34.7 Lifecycle, location and temporal rules deliberately not invented

The live enum establishes seven defect lifecycle labels, but the active Integrity DDD does not define a complete allowed transition matrix or exact prerequisites for `REPAIRED`, `CLOSED`, or `DISMISSED`. `openLifecycle()` therefore records the currently implemented open/terminal distinction without HMSR-021 inventing additional transitions.

Likewise, the repository does not state an explicit invariant coupling `closedAt` to a particular status or a documented `detectedAt <= closedAt` rule for PipelineDefect. HMSR-021 does not promote an intuitive timing rule into the semantic baseline without repository evidence.

`kilometerPoint`, `latitude`, and `longitude` are optional location descriptors. Current Integrity DDD evidence does not define their numeric ranges, CRS, linear-reference system, or mandatory pairing. Data-provisioning work must still preserve source coordinate/CRS semantics and reject invalid source geography rather than guessing, but HMSR-021 does not invent model-level coordinate rules.

### 34.8 Persistence and application consistency

The live domain and JPA models agree on all 18 declared components.

The base Integrity migration makes `id`, `defect_number`, `defect_type_id`, `status`, `topology_asset_type_code`, `topology_asset_id`, `detected_at`, `created_at`, and `updated_at` non-null. Threat, severity, location, description, closure and source-finding fields remain nullable.

The schema contains ordinary indexes for defect type, status, topology asset ID, source finding and audit timestamps. No current repository evidence establishes uniqueness for `defectNumber`, so HMSR-021 does not add such an obligation.

`PipelineDefectRepositoryPort` provides save/find-by-ID persistence, but the active `IntegrityApplicationService` and Integrity REST controller expose program, assessment and case operations only. This absence is application-capability evidence, not by itself a reason to remove the model or fabricate creation semantics.

### 34.9 Required revision

The model role, ownership direction, field shape and Topology-neutral reference pattern are coherent. One evidence-backed correction remains:

1. **Protect non-null source-finding provenance:** when `PipelineDefect.sourceFindingId` is supplied, it must resolve to an existing Integrity-owned `InspectionFinding` rather than allowing a dangling same-module provenance ID. The correction may be enforced through an appropriate Integrity application/persistence boundary and/or additive FK in a separately authorized implementation task; HMSR-021 does not prescribe the implementation shape here.

HMSR-021 does not modify production Java, JPA, Flyway, APIs, application contracts, tests, defects, findings or provisioned data.

### 34.10 SONATRACH/TRC operational interpretation

For SONATRACH/TRC pipeline integrity, PipelineDefect represents an engineering defect record tied to an authoritative Topology asset without taking ownership of the physical network object. Kilometer point and geographic coordinates are supporting localization descriptors; inspection-finding provenance helps preserve how the defect entered the Integrity evidence chain.

Threat, severity and lifecycle state support engineering prioritization and follow-up, while actual maintenance execution remains in Assets and operational incident/HSE lifecycle remains in their owning bounded contexts.

A recorded source finding must remain traceable to real Integrity evidence if populated; otherwise the defect's engineering provenance becomes unreliable.

### 34.11 Review conclusion

**REVISE.** `PipelineDefect` has a coherent 18-field model, correct Level-0 placement, correct neutral Topology ownership boundary, valid same-module defect-type catalog ownership, and a resolvable optional source-finding reference.

The target baseline cannot mark it APPROVED while a non-null `sourceFindingId` can persist without demonstrated referential protection to `InspectionFinding`. HMS reconciliation must retain this provenance-integrity obligation until an explicitly authorized Integrity correction task resolves it or the target semantics are explicitly changed.

## 35. HMSR-022 — organization.Position review

**Decision:** REVISE  
**Review code:** HMSR-022  
**Dependency level:** 0  
**Bounded context:** organization  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `organization.EmployeeAssignment` through `positionId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 0

### 35.1 Semantic role and ordering rationale

`Position` is the Organization-owned operational function/catalog identity used when assigning an employee to an organization unit. It is not an Identity `Role`, does not grant permissions by itself, and does not own operational-scope responsibility.

It is Level 0 because it has no upstream HMS subject-model dependency. `EmployeeAssignment` is its sole direct HMS subject dependent through mandatory `positionId`.

The current Organization architecture is explicit that:

- Identity owns users, roles, permissions and access policy;
- Organization owns employees, units, positions, assignments and reporting lines;
- `EmployeeAssignment` is the employee-unit-position/tenure association;
- operational authority belongs separately to `ResponsibilityAssignment`, so holding a Position must not be treated as automatic authorization.

### 35.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable Position identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral Organization business code. It is normalized through `OrganizationCode`. |
| `titleAr` | `String` | Optional localized text | Arabic position title. |
| `titleFr` | `String` | Optional localized text | French position title. |
| `titleEn` | `String` | Optional localized text | English position title. |
| `level` | `PositionLevel` | Mandatory in the domain model | Organizational/function level: `EXECUTIVE`, `MANAGER`, `SUPERVISOR`, `OPERATOR`, `ENGINEER`, `TECHNICIAN`, or `ADMINISTRATIVE`. |
| `descriptionAr` | `String` | Optional localized text | Arabic position description. |
| `descriptionFr` | `String` | Optional localized text | French position description. |
| `descriptionEn` | `String` | Optional localized text | English position description. |
| `status` | `PositionStatus` | Mandatory | Position lifecycle state: `ACTIVE`, `INACTIVE`, or `DEPRECATED`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor already:

- requires nonblank `id`;
- requires and canonicalizes `code` through `OrganizationCode`;
- requires non-null `level`;
- requires non-null `status`;
- normalizes all localized title/description fields by trimming and converting blanks to `null`.

### 35.3 Multilingual ownership and storage model

The current authoritative Organization roadmap defines same-entity multilingual storage:

```text
Arabic  -> *Ar
French  -> *Fr
English -> *En
```

`Position` complies with that policy through complete title and description triplets:

```text
titleAr / titleFr / titleEn
descriptionAr / descriptionFr / descriptionEn
```

Repository guardrails explicitly verify these triplets in both the domain record and `PositionJpaEntity`. The embedded-multilingual migration added `description_ar`, `description_fr`, and `description_en`, and the later compatibility-retirement migration removed the legacy ambiguous `description` column only after fail-closed parity checks.

Current Organization tests also prevent the retired compatibility column from reappearing. Therefore HMSR-022 finds no multilingual-model revision obligation.

The repository does not state that all three localized titles/descriptions must be non-null for every row. HMSR-022 therefore does not invent a three-language completeness constraint at construction time.

### 35.4 Position code semantics

`Position.code` is a stable Organization business code and is validated by `OrganizationCode`, which:

- rejects null/blank input;
- trims whitespace;
- normalizes letters to upper case with `Locale.ROOT`;
- deliberately does not invent a restrictive character set because existing authoritative Organization codes may use stable separators.

The Organization roadmap explicitly requires a valid position code, and the current domain satisfies that requirement.

No current Position-specific DDD, schema or application contract reviewed here establishes a uniqueness scope for `Position.code`. The base schema contains a normal index on `hidra_org_position(code)`, not a unique constraint. HMSR-022 therefore does not invent global or scoped code uniqueness.

### 35.5 EmployeeAssignment dependency and authorization boundary

The validated HMS graph contains:

```text
EmployeeAssignment.positionId
    -> Position.id
```

`EmployeeAssignment.positionId` is mandatory in both domain and JPA state.

ORG-046/HRA internal-reference hardening installs and preserves:

```text
fk_org_employee_assignment_position
  FOREIGN KEY (position_id)
  REFERENCES hidra_org_position(id)
  ON DELETE RESTRICT
```

so an assignment cannot point to a missing Position at the database boundary.

This relationship means Position supplies assignment/function identity only. The accepted Organization architecture explicitly separates operational responsibility into `ResponsibilityAssignment`; position membership must not be interpreted as an implicit asset authorization or Identity role.

No current repository rule reviewed by HMSR-022 states that only `ACTIVE` positions may be assigned, nor defines exact Position lifecycle transitions. Those rules are therefore not invented here.

### 35.6 Domain/persistence inconsistency — mandatory level versus nullable storage

The strongest confirmed defect is a direct domain/persistence mismatch.

The authoritative domain constructor states:

```text
if (level == null) {
    throw new InvalidOrganizationValueException("Position level is required.");
}
```

but the persistence mapping is:

```text
@Column(name = "level", length = 80)
private PositionLevel level;
```

with no `nullable = false`, and the base schema defines:

```text
level varchar(80)
```

without `NOT NULL`.

No later Organization migration reviewed by HMSR-022 makes `hidra_org_position.level` non-null.

This permits database state that the canonical domain model cannot reconstruct: `OrganizationPersistenceMapper.toDomain()` must call the Position constructor, which rejects a null level.

The current authoritative Organization roadmap also records the project as greenfield with no deployed legacy database/legacy rows for the Organization migration workstream. HMSR-022 therefore found no repository-backed legacy-null compatibility requirement that would justify retaining nullable persistence while the domain declares the field mandatory.

### 35.7 Persistence/application consistency otherwise

The live Position domain and JPA models agree on all 12 declared components.

The final multilingual persistence shape is consistent with the current domain:

- `id`, `code`, `status`, `created_at`, and `updated_at` are non-null in the base table;
- title and description language fields remain nullable;
- the legacy unqualified `description` column is retired;
- `level` is the sole reviewed field where the canonical domain's mandatory semantics are weaker at the persistence boundary.

`PositionRepositoryPort` exposes save/find-by-ID operations, but current Organization public inbound-use-case inventory does not expose Position creation as an active standalone REST/application operation. That absence does not make the model invalid because Position remains a referenced Organization master/catalog model used by EmployeeAssignment.

Generic `createdAt`/`updatedAt` nullability remains a persistence/application-boundary concern under the HRA invariant classification and is not promoted into a new constructor rule.

### 35.8 Required revision

One evidence-backed correction is required:

1. **Align Position level nullability across domain and persistence.** Because the canonical Position domain model requires `PositionLevel`, the JPA mapping and final schema must not admit `NULL` `level` values unless a separately approved semantic change makes Position level optional. The correction must use an additive migration and appropriate persistence/JPA alignment in a separately authorized Organization task; HMSR-022 does not modify applied migrations or production code.

No additional revision is recorded for title completeness, description completeness, code uniqueness, status transitions, assignment eligibility, authorization or audit timestamps because current repository evidence does not prove those stronger rules.

### 35.9 SONATRACH/TRC operational interpretation

For SONATRACH/TRC operations, Position represents the organizational function an employee holds — for example operator, engineer, supervisor or manager — within an Organization assignment. It is distinct from a security role and from an operational responsibility mandate.

A Position's level is part of its canonical organizational classification. Persisting a Position without that level while the domain declares it mandatory creates an unusable master-data row and weakens assignment semantics.

Multilingual titles/descriptions support Arabic, French and English user-facing organization data directly on the Position entity, consistent with the current Organization storage policy.

### 35.10 Review conclusion

**REVISE.** `Position` has a coherent 12-field model, correct Level-0 placement, correct same-entity multilingual design, valid code normalization, correct separation from Identity authorization, and a database-protected downstream `EmployeeAssignment.positionId` relationship.

The target baseline cannot mark it APPROVED while `Position.level` is mandatory in the canonical domain but nullable in JPA/schema. HMS reconciliation must retain this nullability-alignment obligation until an explicitly authorized Organization correction task resolves it or the Position semantics are explicitly changed.

## 36. HMSR-023 — organization.Shift review

**Decision:** REVISE  
**Review code:** HMSR-023  
**Dependency level:** 0  
**Bounded context:** organization  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `organization.ShiftAssignment` through `shiftId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 0

### 36.1 Semantic role and ordering rationale

`Shift` is the Organization-owned reusable work-shift definition referenced by employee shift assignments. It owns language-neutral shift identity/code, multilingual display names, shift classification, configured schedule/timezone fields, lifecycle availability and audit metadata.

It is Level 0 because it has no upstream HMS subject-model dependency. `ShiftAssignment` is its sole direct HMS subject dependent through mandatory `shiftId`.

The Organization DDD explicitly owns internal Sonatrach/TRC shifts and shift assignments. Shift does not belong to Identity, Planning, Workflow or Topology merely because those modules may use time/shift context.

### 36.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable Shift identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral Organization business code normalized through `OrganizationCode`. |
| `nameAr` | `String` | Optional localized text | Arabic shift name. |
| `nameFr` | `String` | Optional localized text | French shift name. |
| `nameEn` | `String` | Optional localized text | English shift name. |
| `shiftType` | `ShiftType` | Mandatory | Shift classification: `DAY`, `NIGHT`, `ROTATION`, `ON_CALL`, or `CUSTOM`. |
| `startTime` | `String` | Persistence-required schedule text | Configured shift start time. |
| `endTime` | `String` | Persistence-required schedule text | Configured shift end time. |
| `timezone` | `String` | Persistence-required schedule text | Timezone used to interpret the configured shift schedule. |
| `active` | `boolean` | Mandatory primitive state | Whether the Shift definition is currently active. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor currently:

- requires nonblank `id`;
- requires and canonicalizes `code` through `OrganizationCode`;
- requires non-null `shiftType`;
- normalizes `nameAr/nameFr/nameEn`;
- normalizes `startTime/endTime/timezone`, converting blanks to `null`;
- carries primitive `active` without a nullable state.

### 36.3 Multilingual ownership and storage

The authoritative Organization multilingual policy is same-entity storage:

```text
Arabic  -> *Ar
French  -> *Fr
English -> *En
```

`Shift` complies through `nameAr/nameFr/nameEn`.

The additive multilingual migration introduced these three columns without guessing the language of the old `name` column. The later compatibility-retirement migration removed legacy `name` only after fail-closed parity against the runtime projection:

```text
nameEn -> nameFr -> nameAr -> code
```

Current Organization guardrail tests prevent the retired compatibility column from returning and verify the complete multilingual triplet in domain/JPA state.

No repository evidence reviewed by HMSR-023 states that all three names must be populated simultaneously, so no three-language constructor requirement is invented.

### 36.4 Shift code and type semantics

`Shift.code` follows the common Organization stable-code policy:

- null/blank values are rejected;
- whitespace is trimmed;
- letters are upper-cased with `Locale.ROOT`;
- no unsupported character-set restriction is invented.

The base schema has a normal index on `hidra_org_shift(code)`, not a unique constraint. No current Shift-specific DDD/application rule establishes global or scoped code uniqueness, so HMSR-023 does not invent one.

`ShiftType` is a bounded Organization enum with five current values. No current evidence proves additional type-specific schedule rules such as different mandatory fields for `ON_CALL` or `CUSTOM`, so none are added.

### 36.5 ShiftAssignment dependency

The validated HMS graph contains:

```text
ShiftAssignment.shiftId
    -> Shift.id
```

`ShiftAssignment.shiftId` is mandatory in both domain and JPA state.

ORG-046 installs:

```text
fk_org_shift_assignment_shift
  FOREIGN KEY (shift_id)
  REFERENCES hidra_org_shift(id)
  ON DELETE RESTRICT
```

so an assignment cannot reference a missing Shift at the database boundary.

The current repository does not define an evidence-backed rule that only `active = true` shifts may receive assignments, nor a transition model for deactivation versus existing assignments. HMSR-023 therefore does not invent one.

### 36.6 Confirmed domain/persistence schedule-nullability mismatch

The strongest confirmed defect is direct inconsistency between canonical domain construction and final persistence requirements.

`ShiftJpaEntity` declares:

```text
@Column(name = "start_time", nullable = false)
@Column(name = "end_time", nullable = false)
@Column(name = "timezone", nullable = false)
```

and the base schema defines:

```text
start_time varchar(20) NOT NULL
end_time   varchar(20) NOT NULL
timezone   varchar(80) NOT NULL
```

No later Organization migration relaxes those constraints.

However, the domain constructor performs only:

```text
startTime = normalize(startTime);
endTime = normalize(endTime);
timezone = normalize(timezone);
```

where null/blank values normalize to `null`.

Therefore canonical domain state can be constructed successfully with any or all of `startTime`, `endTime`, and `timezone` absent, but saving that state through the current JPA/schema boundary must fail.

This is not merely a localized-label or audit-timestamp difference: these fields define the configured schedule/timezone of the Shift record and are explicitly required by current persistence.

The authoritative Organization roadmap records the current project path as greenfield with no deployed legacy database rows requiring preservation of null schedule values. HMSR-023 found no repository-backed compatibility reason for the mismatch.

### 36.7 Scheduling semantics deliberately not invented

Although the persistence boundary requires the three schedule strings to be present, current repository evidence does not define:

- whether times must use `HH:mm`, `HH:mm:ss`, or another syntax;
- whether `startTime < endTime`;
- how overnight shifts crossing midnight are represented;
- whether `ROTATION`, `ON_CALL`, or `CUSTOM` use different schedule rules;
- whether timezone must be an IANA `ZoneId`;
- whether daylight-saving behavior matters for non-Algerian deployments.

The Shift JavaDoc explicitly says scheduling-policy validation remains outside its storage-focused multilingual correction. HMSR-023 therefore records only the proven nullability inconsistency and does not invent schedule parsing or ordering rules.

### 36.8 Persistence/application consistency otherwise

The live domain and JPA models agree on all 12 declared components.

Final persistence is aligned for:

- `id`;
- normalized `code`;
- multilingual names;
- mandatory `shiftType`;
- primitive `active`;
- audit timestamps.

`ShiftRepositoryPort` provides save/find-by-ID persistence. Current public Organization inbound-use-case inventory does not expose a standalone Shift creation/update operation. That absence does not invalidate the model because Shift remains Organization-owned master/reference data consumed by `ShiftAssignment`.

Generic `createdAt`/`updatedAt` domain nullability remains classified as persistence-only audit metadata and is not promoted into a new constructor rule.

### 36.9 Required revision

One evidence-backed correction is required:

1. **Align `startTime`, `endTime`, and `timezone` nullability across the Shift domain and persistence contract.** The current final schema/JPA contract requires all three values, while canonical domain construction permits them to normalize to `null`. A separately authorized Organization correction task must either enforce the established non-null persistence semantics before save/domain construction, or explicitly change the target persistence semantics if a documented business decision makes any field optional. HMSR-023 does not prescribe clock parsing, timezone syntax, overnight handling, or type-specific scheduling rules.

HMSR-023 does not modify production Java, JPA, Flyway, APIs, application contracts, tests, shifts, shift assignments or provisioned data.

### 36.10 SONATRACH/TRC operational interpretation

For SONATRACH/TRC operations, Shift is reusable organization master data describing work periods used to assign personnel operationally. A Shift definition whose schedule or timezone is missing cannot satisfy the current persistence contract and risks ambiguous interpretation of employee shift assignments.

The model remains organizational rather than authorization-oriented: being assigned to a shift does not itself grant Identity permissions or operational-scope responsibility.

Multilingual shift names belong directly on the Shift entity in Arabic, French and English under the current Organization policy.

### 36.11 Review conclusion

**REVISE.** `Shift` has a coherent 12-field ownership model, correct Level-0 placement, valid same-entity multilingual design, normalized stable code, bounded type vocabulary and a database-protected downstream `ShiftAssignment.shiftId` reference.

The target baseline cannot mark it APPROVED while the canonical domain permits absent `startTime`, `endTime`, or `timezone` values that the final JPA/schema contract rejects. HMS reconciliation must retain this schedule-nullability alignment obligation until an explicitly authorized Organization correction task resolves it or the Shift persistence semantics are explicitly changed.

## 37. HMSR-024 — topology.PipelineSystem review

**Decision:** REVISE  
**Review code:** HMSR-024  
**Dependency level:** 0  
**Bounded context:** topology  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `topology.Pipeline` through `pipelineSystemId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 0

### 37.1 Semantic role and ordering rationale

`PipelineSystem` is the Topology-owned logical transportation-system aggregate that groups pipelines and participates in broader system/facility associations. It is part of the canonical physical/logical network backbone used by downstream operational, telemetry, simulation, risk, visualization and responsibility-scope use cases.

It is Level 0 because it has no confirmed upstream HMS subject-model dependency. `Pipeline` is the one direct HMS subject dependent through mandatory `pipelineSystemId`.

Topology DDD correctly owns PipelineSystem and explicitly excludes organization, identity, telemetry values, workflow approvals, integrity findings, HSE cases and other foreign bounded-context state.

### 37.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable PipelineSystem identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral topology business code. |
| `nameAr` | `String` | Optional localized text | Arabic system name. |
| `nameFr` | `String` | Optional localized text | French system name. |
| `nameEn` | `String` | Optional localized text | English system name. |
| `systemType` | `PipelineSystemType` | Mandatory in current live model | Current fixed classification enum: `TRANSPORT`, `GATHERING`, `DISTRIBUTION`, `EXPORT`, `IMPORT`, or `MIXED`. This is the principal semantic inconsistency identified by HMSR-024. |
| `status` | `TopologyStatus` | Mandatory | Lifecycle state: `DRAFT`, `ACTIVE`, `SUSPENDED`, `RETIRED`, or `ARCHIVED`. |
| `description` | `String` | Optional | Free-form system description. |
| `commissionedAt` | `Instant` | Optional | Commissioning timestamp when known. |
| `retiredAt` | `Instant` | Optional | Retirement timestamp when known. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The current compact constructor already rejects blank `id` and `code`, requires non-null `systemType` and `status`, and normalizes textual values.

### 37.3 Topology ownership and downstream Pipeline boundary

The validated HMS graph contains:

```text
Pipeline.pipelineSystemId
    -> PipelineSystem.id
```

`Pipeline.pipelineSystemId` is mandatory in domain and JPA state. HRA-111 installs `fk_hra111_topology_020`, linking:

```text
hidra_topology_pipeline.pipeline_system_id
    -> hidra_topology_pipeline_system.id
```

with `ON DELETE RESTRICT`.

Topology persistence also contains non-HMS-subject association models such as `PipelineSystemFacility` that point to PipelineSystem. Those persistence dependents reinforce PipelineSystem as stable topology master identity but do not change the HMS direct-subject dependent count.

### 37.4 Accepted catalog policy versus live PipelineSystem classification

The active Topology roadmap states:

```text
Topology business taxonomy concepts are no longer roadmap-accepted as Java enums.
```

and records the catalog correction sequence as completed. The same roadmap says business type concepts are modeled through catalog references with stable codes/localized labels and that REST create/list requests use stable `typeCode` fields.

The current architecture domain schema also represents PipelineSystem with a catalog-style reference:

```text
PipelineSystem
  ...
  ProductTypeReference productType
  TopologyStatus status
```

and the controlled-vocabulary correction history classifies `TopologyStatus` as an intentional lifecycle enum while requiring business-type enums to be replaced by catalog/reference semantics.

However, live PipelineSystem remains:

```text
PipelineSystemType systemType
```

where `PipelineSystemType` is a Java enum containing:

```text
TRANSPORT
GATHERING
DISTRIBUTION
EXPORT
IMPORT
MIXED
```

The JPA entity persists this as an enumerated string in mandatory `system_type`, and the base Flyway table still defines:

```text
system_type varchar(80) NOT NULL
```

No live migration reviewed by HMSR-024 replaces this field with a catalog FK/reference.

### 37.5 Application/API inconsistency with the accepted type-code contract

The unfinished taxonomy shape reaches every active write/read boundary:

- `CreatePipelineSystemCommand` accepts `PipelineSystemType systemType`;
- `CreatePipelineSystemRequest` exposes the domain enum directly;
- `PipelineSystemSummaryDto` exposes the same enum;
- `PipelineSystemResponse` exposes the same enum;
- `PipelineSystemApplicationService` constructs the aggregate using that enum.

This conflicts with the accepted Topology roadmap's general post-correction contract that create/list inputs use stable catalog `typeCode` values and responses expose catalog/localized type semantics rather than fixed business taxonomy enums.

HMSR-024 does **not** assume that the correct replacement is necessarily the existing `ProductTypeReference` despite the architecture diagram. The live enum values describe transportation-system classification, while "product type" usually represents transported hydrocarbon/product semantics. Current repository evidence is contradictory enough that a dedicated Topology correction must decide whether PipelineSystem needs its own catalog-backed system classification, an existing approved catalog, or an explicitly revised architecture contract.

What is proven is that the current fixed Java enum cannot simultaneously be the final model while the accepted Topology policy says business type concepts are catalog-backed and the catalog correction sequence is complete.

### 37.6 Unsupported silent TRANSPORT default

`PipelineSystemApplicationService.createPipelineSystem()` currently applies:

```text
command.systemType() == null
    ? PipelineSystemType.TRANSPORT
    : command.systemType()
```

The domain itself says `systemType` is mandatory, but no Topology DDD rule, roadmap rule, migration rule or application contract reviewed by HMSR-024 states that an omitted classification semantically means `TRANSPORT`.

Therefore the service can silently invent a business classification instead of failing closed or resolving an explicitly supplied catalog/reference value.

This default is especially problematic while the type model itself is unresolved: data provisioned or created through this path could encode `TRANSPORT` simply because the caller supplied no type, not because authoritative source data established that classification.

### 37.7 Multilingual and lifecycle semantics

`nameAr/nameFr/nameEn` provide an embedded multilingual system name. Current repository evidence does not require every language to be non-null, so HMSR-024 does not invent a completeness rule.

`TopologyStatus` is explicitly distinguished from business taxonomy in the correction audit and retained as a lifecycle enum. New systems are created as `DRAFT`, which is conservative and consistent with the current status vocabulary.

The repository does not define a complete PipelineSystem status-transition matrix, so HMSR-024 does not invent one.

### 37.8 Commissioning/retirement semantics deliberately not invented

`commissionedAt` and `retiredAt` are optional in both domain and persistence.

Although chronological rules may be intuitively desirable, the repository-wide invariant classification lists five approved Topology temporal invariants and does not include a PipelineSystem `commissionedAt <= retiredAt` rule. HMSR-024 therefore does not promote that assumption into the semantic baseline.

Likewise, no evidence reviewed here requires `retiredAt` whenever status is `RETIRED`, forbids it for other statuses, or requires `commissionedAt` before activation.

### 37.9 Code and persistence semantics deliberately not invented

The base schema indexes `PipelineSystem.code` but does not declare it unique. Current active Topology DDD/roadmap evidence reviewed here does not state the exact uniqueness scope for PipelineSystem code, so HMSR-024 does not invent one.

The live domain and JPA models otherwise agree on all 12 current components, and generic `createdAt`/`updatedAt` requirements remain persistence/application-boundary audit concerns under the HRA invariant policy.

### 37.10 Required revisions

Two evidence-backed obligations remain:

1. **Reconcile PipelineSystem business classification with the accepted Topology catalog policy.** Replace or explicitly redesign the current fixed `PipelineSystemType/system_type` representation so the final PipelineSystem contract is consistent with the repository's catalog-backed business-type architecture. The correction must define the authoritative catalog/reference semantics rather than blindly assuming `ProductTypeReference`; domain, application, REST, JPA and additive Flyway changes must remain aligned in the separately authorized Topology task.

2. **Remove the unsupported silent `TRANSPORT` inference.** Missing PipelineSystem classification must not be converted to `TRANSPORT` unless an explicit Topology business rule establishes that default. The corrected write boundary should fail closed or use the explicitly approved classification/catalog contract.

HMSR-024 does not modify production Java, JPA, Flyway, REST/application contracts, tests, topology master data or provisioned data.

### 37.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, PipelineSystem is a high-level network grouping used to organize pipelines and associated facilities. Whether a system is transport, gathering, export, import, distribution or mixed is operational master-data classification, not merely a technical implementation detail.

Such classification should be authoritative and traceable. Silently classifying an unspecified system as `TRANSPORT` can misrepresent network semantics, while hard-coding an evolving business classification into a Java enum conflicts with Hidra's accepted catalog-governance direction.

Topology remains the owner of this network identity and classification; other bounded contexts should consume stable topology references rather than duplicate or redefine the system taxonomy.

### 37.12 Review conclusion

**REVISE.** `PipelineSystem` has a coherent Topology ownership role, correct Level-0 placement, stable 12-field current representation, appropriate multilingual names, intentional lifecycle status and a database-protected downstream Pipeline relationship.

The target baseline cannot mark it APPROVED while live domain/JPA/API/schema still use fixed `PipelineSystemType/system_type` business taxonomy despite the accepted completed catalog policy, and while the create service silently invents `TRANSPORT` when classification is absent.

HMS reconciliation must retain these obligations until a separately authorized Topology correction reconciles the classification model and removes unsupported defaulting, or the accepted Topology catalog architecture is explicitly revised with stronger repository evidence.

## 38. HMSR-025 — analytics.AnalyticsInsight review

**Decision:** REVISE  
**Review code:** HMSR-025  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 0  
**Transitive dependents:** 0  
**Unresolved/non-subject references:** 3 — all three source references are now semantically resolved to Analytics-owned read/persistence models outside the 123 HMS subject set

### 38.1 Semantic role and ordering rationale

`AnalyticsInsight` is the Analytics-owned derived finding/observation used to surface advisory analytical interpretation without taking ownership of operational action, incidents, risk acceptance, HSE response, integrity disposition, planning decisions or source-of-truth data.

It is Level 0 because none of its prerequisites are another HMS subject model. Its subject area, optional severity taxonomy and optional source objects are retained Analytics read/persistence models outside the 123-model HMS subject population, while `scopeType + scopeId` is a neutral polymorphic analytical-scope reference.

The active Analytics DDD is explicit that insights are advisory, do not open incidents directly, may be consumed by owning business modules after review, and must remain traceable.

### 38.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable AnalyticsInsight identity and persistence primary key. |
| `insightType` | `String` | Persistence-required analytical taxonomy code | Analytics-owned insight classification. The DDD lists examples and identifies `INSIGHT_TYPE` as an Analytics catalog vocabulary. |
| `subjectAreaId` | `String` | Mandatory | Same-module reference to AnalyticsSubjectArea. |
| `scopeType` | `String` | Persistence-required discriminator | Neutral analytical-scope discriminator such as pipeline system, pipeline, facility, organization unit, product or other approved scope. |
| `scopeId` | `String` | Optional | Stable target ID selected by `scopeType`; may be absent for scope kinds whose semantics do not require an object ID. |
| `title` | `String` | Persistence-required text | Human-readable insight title. |
| `summary` | `String` | Persistence-required text | Human-readable analytical finding summary. |
| `severityId` | `String` | Optional catalog reference | Analytics-owned severity reference; DDD catalog vocabulary includes `ANALYTICS_SEVERITY`. |
| `confidenceScore` | `BigDecimal` | Optional | Analytical confidence score. Current repository evidence does not define a numeric range. |
| `sourceProjectionSnapshotId` | `String` | Optional same-module source reference | Direct lineage/provenance reference to AnalyticsProjectionSnapshot. |
| `sourceTrendAnalysisId` | `String` | Optional same-module source reference | Direct lineage/provenance reference to TrendAnalysis. |
| `sourceModelRunId` | `String` | Optional same-module source reference | Direct lineage/provenance reference to AnalyticsModelRun. |
| `status` | `AnalyticsInsightStatus` | Mandatory | Insight lifecycle state: `DRAFT`, `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `DISMISSED`, or `ARCHIVED`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor currently rejects blank `id` and `subjectAreaId`, requires non-null `status`, normalizes textual fields and exposes `advisoryOnly() == true`.

### 38.3 Subject-area dependency resolution

HMS-003 treated `subjectAreaId` generically as a catalog/value dependency. Stronger evidence resolves it to the Analytics-owned `AnalyticsSubjectArea` read/persistence model:

- Analytics DDD owns AnalyticsSubjectArea;
- `AnalyticsInsightJpaEntity.subjectAreaId` maps to mandatory `hidra_analytics_insight.subject_area_id`;
- repository mirror classification retains AnalyticsSubjectArea as an Analytics read/persistence model outside the HMS subject set;
- HRA-111 installs `fk_hra111_analytics_010`, linking `hidra_analytics_insight.subject_area_id -> hidra_analytics_subject_area(id)` with `ON DELETE RESTRICT`.

No HMS graph edge is added because AnalyticsSubjectArea is outside the 123 subject models.

### 38.4 Analytical scope semantics

The Analytics DDD defines neutral analytical scopes and gives examples including network, pipeline system, pipeline, segment, facility, equipment, measurement point, organization unit, product, custody transfer point, HSE site and risk area.

Therefore:

```text
scopeType + scopeId
```

is a polymorphic analytical reference, not an Analytics-owned aggregate FK.

The schema deliberately makes `scope_type` mandatory while `scope_id` is nullable. HMSR-025 does not invent which scope types require IDs or a cross-module database FK. Those type/ID rules require an explicit owner-resolved analytical-scope contract.

### 38.5 Insight-type catalog semantics and current gap

The active Analytics DDD defines `AnalyticsCatalogEntry` as controlled vocabulary for Analytics business taxonomy and explicitly lists:

```text
INSIGHT_TYPE
ANALYTICS_SEVERITY
```

among catalog examples.

It also enumerates example insight classifications such as recurring deviation, performance degradation, data-quality issue, risk-increase pattern, asset-reliability pattern, integrity-degradation pattern, custody-variance pattern, HSE trend and digital-twin-readiness gap.

The live model nevertheless stores `insightType` as an unrestricted String. The create request and command also accept an unrestricted String, and `AnalyticsApplicationService.createAnalyticsInsight()` persists it without demonstrating resolution against an `INSIGHT_TYPE` catalog entry/code.

The schema requires `insight_type NOT NULL`, while the domain constructor merely normalizes it. A null/blank insight type can therefore survive domain construction and fail only at persistence.

This is stronger than a generic text concern: the DDD explicitly defines insight type as controlled Analytics taxonomy.

### 38.6 Severity catalog semantics

`severityId` is nullable, so absence is currently allowed. When present, however, Analytics DDD evidence identifies severity as Analytics-owned controlled vocabulary through `ANALYTICS_SEVERITY`.

The base schema currently provides only an index on `hidra_analytics_insight.severity_id`. HRA-111 does not add an FK because its authorized scope covered mandatory same-module references.

No current create-path validation demonstrates that a supplied `severityId` resolves to an AnalyticsCatalogEntry, much less the intended severity catalog family.

HMSR-025 therefore does not make severity mandatory, but it records that a non-null severity reference must not be allowed to dangle or point to an unrelated Analytics catalog family.

### 38.7 Source lineage resolution and referential gap

HMS-003 left two source fields unresolved and treated the projection-snapshot field as snapshot/reference-only. Stronger repository evidence resolves all three to concrete same-module source records outside the HMS subject set:

```text
sourceProjectionSnapshotId
    -> analytics.AnalyticsProjectionSnapshot
    -> hidra_analytics_projection_snapshot.id

sourceTrendAnalysisId
    -> analytics.TrendAnalysis
    -> hidra_analytics_trend_analysis.id

sourceModelRunId
    -> analytics.AnalyticsModelRun
    -> hidra_analytics_model_run.id
```

All three target models are retained as Analytics read/persistence models. The base schema creates indexes for the three source columns but no FK. HRA-111 does not cover them because they are optional.

The current application service forwards caller-provided values directly into AnalyticsInsight without demonstrating existence checks.

The DDD also provides the broader `AnalyticsInsightEvidence` structure for heterogeneous evidence and HRA-111 protects its mandatory `analyticsInsightId -> AnalyticsInsight.id` relationship. That evidence model means HMSR-025 does **not** require one of the three direct source IDs to be non-null for every insight: an insight may have other valid evidence forms.

What is required is that any direct source ID that **is** populated remains traceable to the concrete Analytics source it claims to reference.

### 38.8 Domain/persistence requiredness gap

The final JPA/schema contract requires:

```text
insight_type NOT NULL
subject_area_id NOT NULL
scope_type NOT NULL
title NOT NULL
summary NOT NULL
status NOT NULL
```

The domain already requires `subjectAreaId` and `status`.

HMSR-025 promotes two additional requiredness obligations because repository semantics independently prove they are structured business identifiers/discriminators:

- `insightType` is controlled Analytics business taxonomy;
- `scopeType` selects the analytical-scope namespace.

The constructor currently permits both to normalize to `null`.

By contrast, `title` and `summary` remain generic human-readable payload. Under the established HRA invariant policy, database `NOT NULL` alone does not convert generic titles/summaries into constructor-level business invariants. HMSR-025 therefore does not invent domain guards for those fields.

### 38.9 Advisory lifecycle semantics deliberately not invented

The DDD states that an insight is advisory and may be referenced by business modules after review. The status enum provides `DRAFT`, `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `DISMISSED`, and `ARCHIVED`.

The current create service creates insights as `OPEN`. No active Analytics DDD rule reviewed here states whether creation must instead start at `DRAFT`, nor defines the complete allowed transition matrix. HMSR-025 therefore does not classify `OPEN` creation as defective and does not invent transition rules.

Similarly, no current evidence defines an allowed numeric range for `confidenceScore`; HMSR-025 does not assume 0..1 merely from the field name.

### 38.10 Required revisions

The reviewed model remains semantically useful, but four evidence-backed corrections are required:

1. **Require a nonblank, governed insight classification at the domain/application boundary.** `insightType` must not normalize to null and must resolve according to the Analytics-owned `INSIGHT_TYPE` catalog contract rather than accepting arbitrary free text.

2. **Require nonblank `scopeType` consistently with the analytical-scope contract and final persistence schema.** HMSR-025 does not prescribe which scope types require `scopeId`; that mapping needs its own owner-resolved scope policy.

3. **Protect optional severity semantics.** When `severityId` is supplied, it must resolve to the appropriate Analytics severity catalog entry/family rather than allowing a dangling or unrelated catalog ID.

4. **Protect populated direct source lineage.** Each non-null `sourceProjectionSnapshotId`, `sourceTrendAnalysisId`, and `sourceModelRunId` must resolve to the corresponding Analytics-owned source record. The correction may use application validation, additive same-module FKs where appropriate, or another fail-closed Analytics-owned boundary in a separately authorized implementation task.

HMSR-025 does not modify production Java, JPA, Flyway, API/application contracts, tests, analytics records or provisioned data.

### 38.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, an AnalyticsInsight is an advisory derived observation over trusted operational history. It may highlight recurring deviations, declining performance, data-quality concerns, reliability patterns, integrity degradation, custody variance, HSE trends or readiness gaps, but it must not become an automatic incident, risk decision, HSE action or planning instruction.

Its classification, scope and provenance therefore need to be trustworthy. An arbitrary type, missing scope namespace, unrelated severity code or dangling source reference would make the analytical finding difficult to govern, review and audit.

The owning operational module remains responsible for any subsequent business decision.

### 38.12 Review conclusion

**REVISE.** `AnalyticsInsight` has a coherent advisory role, correct Level-0 placement, appropriate subject-area ownership, neutral analytical scope concept, explicit lifecycle status and resolvable same-module source lineage.

The target baseline cannot mark it APPROVED while controlled `insightType` and mandatory `scopeType` can be absent at the domain boundary, while `insightType` is accepted as unrestricted text despite the explicit `INSIGHT_TYPE` catalog vocabulary, and while populated optional severity/source references lack demonstrated fail-closed integrity.

HMS reconciliation must retain these obligations until an explicitly authorized Analytics correction task resolves them or the target semantics are explicitly revised with stronger repository evidence.

## 39. HMSR-026 — analytics.AnalyticsProjectionRun review

**Decision:** REVISE  
**Review code:** HMSR-026  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 0  
**Transitive dependents:** 0  
**Unresolved/non-subject references:** 1 — `projectionDefinitionId`, now semantically resolved to Analytics-owned `AnalyticsProjectionDefinition` outside the 123 HMS subject set

### 39.1 Semantic role and ordering rationale

`AnalyticsProjectionRun` is the Analytics-owned execution record for one materialized analytical projection computation. It captures which projection definition was run, run mode/status, optional analytical period, execution timing, source watermark, record counts, diagnostic error context and technical correlation identity.

It is Level 0 because its required projection-definition prerequisite is a retained Analytics read/persistence model outside the 123-model HMS subject population. It has no direct HMS subject dependent.

The active Analytics DDD states that projection definitions specify analytical computation, projection runs execute those definitions, and projection snapshots publish resulting states. Analytics remains derived/read-oriented and must not mutate source-of-truth operational state.

### 39.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable AnalyticsProjectionRun identity and persistence primary key. |
| `projectionDefinitionId` | `String` | Mandatory | Same-module reference to the AnalyticsProjectionDefinition being executed. |
| `runStatus` | `AnalyticsRunStatus` | Mandatory | Execution lifecycle state: `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, or `CANCELLED`. |
| `runMode` | `AnalyticsRunMode` | Mandatory | Execution mode: `FULL_REBUILD`, `INCREMENTAL`, `BACKFILL`, `MANUAL_RECOMPUTE`, or `SCHEDULED`. |
| `periodStart` | `Instant` | Optional | Start of the analytical period being processed, when the projection uses a bounded period. |
| `periodEnd` | `Instant` | Optional | End of the analytical period; current domain correctly rejects values before `periodStart` when both are present. |
| `startedAt` | `Instant` | Mandatory | Run execution start timestamp. |
| `completedAt` | `Instant` | Optional | Completion timestamp when recorded. Current DDD does not define exact status/timestamp pairing rules. |
| `sourceWatermark` | `String` | Optional in current model | Source-read watermark used for reproducibility/incremental lineage; the DDD requires successful runs to record it. |
| `recordsRead` | `Long` | Optional | Number of source records read. The current start path initializes it to zero. |
| `recordsWritten` | `Long` | Optional | Number of projection records written. The current start path initializes it to zero. |
| `errorCode` | `String` | Optional | Machine-oriented diagnostic error code. |
| `errorMessage` | `String` | Optional | Human-readable diagnostic context. |
| `correlationId` | `String` | Optional technical reference | Cross-request/job correlation identity; not a business-model dependency. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |

The compact constructor already requires nonblank `id` and `projectionDefinitionId`, non-null `runStatus`, `runMode`, and `startedAt`, and enforces `periodStart <= periodEnd` when both bounds are present.

### 39.3 Projection-definition dependency resolution

HMS-003 left `projectionDefinitionId` unresolved. Stronger live evidence resolves it to Analytics-owned `AnalyticsProjectionDefinition`:

- Analytics DDD owns AnalyticsProjectionDefinition as projection computation specification;
- repository mirror classification retains AnalyticsProjectionDefinition as an Analytics read/persistence model outside the HMS subject set;
- `AnalyticsProjectionRunJpaEntity.projectionDefinitionId` maps to mandatory `hidra_analytics_projection_run.projection_definition_id`;
- HRA-111 installs `fk_hra111_analytics_028`, linking `hidra_analytics_projection_run.projection_definition_id -> hidra_analytics_projection_definition(id)` with `ON DELETE RESTRICT`.

Therefore `projectionDefinitionId` is a required same-module domain reference to a non-HMS model. No HMS graph edge is added.

The current application run path does not explicitly look up the definition before save, but the database FK provides fail-closed row-existence protection. HMSR-026 does not invent a duplicate application existence check solely because the lookup is absent.

### 39.4 Current start-run behavior

`AnalyticsApplicationService.runProjection()` creates a new run with:

```text
runStatus      = RUNNING
startedAt      = now
completedAt    = null
sourceWatermark = null
recordsRead    = 0
recordsWritten = 0
errorCode      = null
errorMessage   = null
createdAt      = now
```

That initial state is semantically conservative for a run that has just started. The current command supplies `projectionDefinitionId`, `runMode`, optional period bounds and optional `correlationId`.

The live application surface does not currently expose a projection-run completion/failure transition use case. That absence does not remove the semantic obligations of the persisted run model: terminal AnalyticsProjectionRun rows can still exist through persistence/evolution, and the DDD explicitly specifies terminal-state evidence requirements.

### 39.5 Failed-run diagnostic obligation

Analytics DDD states:

```text
failed run must retain error context
```

The live record nevertheless permits:

```text
runStatus = FAILED
errorCode = null
errorMessage = null
```

because neither constructor nor persistence schema couples FAILED status to diagnostic fields.

The DDD does not state whether error context means `errorCode`, `errorMessage`, or both. HMSR-026 therefore does not invent an exact field combination, but the final model/application lifecycle must ensure a failed run cannot be persisted without meaningful retained error context.

### 39.6 Successful-run watermark obligation

Analytics DDD separately states:

```text
successful run must record source watermark
```

The current model permits terminal successful state with `sourceWatermark = null`. No database constraint or current transition path demonstrates a fail-closed watermark rule.

The enum includes `COMPLETED` and `COMPLETED_WITH_WARNINGS`; repository evidence does not separately define which statuses the phrase "successful run" covers. The correction must explicitly map successful terminal status semantics and enforce a nonblank source watermark for the statuses treated as successful rather than HMSR-026 guessing the mapping.

This is an analytical-lineage requirement, not generic text nullability: the source watermark is specifically required by the DDD for successful run reproducibility.

### 39.7 Projection-definition version/reproducibility gap

The strongest structural lineage issue is between the projection-definition rule and the run reference.

Analytics DDD states:

```text
projection definition must be versioned when formula changes
projection must be rebuildable from source truth and audit history
```

But the live repository contains no `AnalyticsProjectionDefinitionVersion` model/table/reference and no `projectionDefinitionVersionId` on AnalyticsProjectionRun. The run stores only:

```text
projectionDefinitionId
    -> hidra_analytics_projection_definition.id
```

while the definition row itself carries mutable computation-related fields such as `projectionType`, `calculationPolicy`, `refreshPolicy`, `retentionPolicy`, and `updatedAt`.

Therefore the current run record cannot, by its own persisted reference, identify which immutable projection-definition/formula version produced a historical result after the definition changes.

HMSR-026 does not prescribe that the correction must use a model literally named `AnalyticsProjectionDefinitionVersion`. A separately authorized Analytics task may satisfy the DDD through an immutable version record, immutable execution snapshot, audit-backed version identity, or another explicit reproducibility contract. What is required is durable run-to-computation-version lineage.

### 39.8 ProjectionSnapshot downstream persistence relationship

Although HMSR-026 has zero direct HMS subject dependents, retained Analytics persistence includes `AnalyticsProjectionSnapshot`.

HRA-111 protects:

```text
hidra_analytics_projection_snapshot.projection_run_id
    -> hidra_analytics_projection_run.id
```

through `fk_hra111_analytics_030` with `ON DELETE RESTRICT`.

The snapshot also references the projection definition independently. This reinforces the run as stable execution identity, but does not solve the formula-version lineage gap because both rows currently point only to the unversioned definition identity.

### 39.9 Run-mode, period, counters and timestamps deliberately not invented

The five `AnalyticsRunMode` values match the DDD exactly. No repository evidence reviewed by HMSR-026 defines mode-specific required period bounds, so the review does not require periods for BACKFILL, SCHEDULED, or any other mode.

The existing `periodStart <= periodEnd` invariant is already enforced. Equality is permitted by the repository-wide invariant policy.

No current Analytics rule states that `recordsRead` or `recordsWritten` must be nonnegative, mandatory at terminal state, or related by a particular inequality. HMSR-026 does not invent those constraints.

Likewise, the DDD does not explicitly state that terminal statuses require `completedAt`, that `completedAt >= startedAt`, or that CANCELLED carries diagnostics. Those potentially useful lifecycle rules are not promoted without stronger evidence.

The DDD says projection runs should be idempotent "where possible", but does not define an idempotency key or uniqueness contract. HMSR-026 therefore does not invent correlation-ID uniqueness or a compound run key.

### 39.10 Required revisions

Three evidence-backed correction obligations remain:

1. **Preserve terminal failure diagnostics.** A run persisted as `FAILED` must retain meaningful error context, as required by Analytics DDD. The implementation task must define the accepted error-code/message contract rather than silently allowing both to be absent.

2. **Preserve successful-run source lineage.** Statuses classified by Analytics as successful must require a nonblank `sourceWatermark`, consistent with the DDD. The implementation task must explicitly define the successful-status set rather than relying on implicit interpretation.

3. **Make projection runs reproducible across definition changes.** AnalyticsProjectionRun must durably identify the immutable computation/definition version that produced the run, or an equivalent immutable execution snapshot/audit version, because Analytics DDD requires definition versioning when formula changes and rebuildability from source truth/history.

HMSR-026 does not modify production Java, JPA, Flyway, REST/application contracts, tests, projection definitions/runs/snapshots, or provisioned data.

### 39.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, AnalyticsProjectionRun is execution evidence for derived operational-intelligence views such as hourly summaries, pipeline/station performance, custody variance, risk evolution or HSE performance.

A historical projection result must be explainable and reproducible: operators and auditors need to know which computation definition produced it, what source boundary/watermark was processed, and why a failed execution failed.

These controls remain analytical lineage and governance concerns. They do not grant Analytics ownership over the underlying telemetry, topology, planning, custody, HSE, risk or other operational source facts.

### 39.12 Review conclusion

**REVISE.** `AnalyticsProjectionRun` has a coherent 15-field execution model, correct Level-0 placement, correct run-mode vocabulary, an already-enforced period-order invariant, conservative RUNNING creation state, and a database-protected required reference to AnalyticsProjectionDefinition.

The target baseline cannot mark it APPROVED while failed runs may omit the DDD-required error context, successful runs may omit the DDD-required source watermark, and historical runs cannot identify an immutable projection-definition/formula version despite the explicit DDD versioning/rebuildability requirement.

HMS reconciliation must retain these three obligations until an explicitly authorized Analytics correction task resolves them or the target Analytics DDD is explicitly revised with stronger repository evidence.

## 40. HMSR-027 — analytics.DigitalTwinReadinessAssessment review

**Decision:** REVISE  
**Review code:** HMSR-027  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 0  
**Transitive dependents:** 0  
**Unresolved/non-subject references:** 0

### 40.1 Semantic role and ordering rationale

`DigitalTwinReadinessAssessment` is the Analytics-owned derived assessment of whether a topology area, station, segment or asset has enough trusted data, topology completeness, model availability and lineage quality to support future digital-twin scenarios.

It is explicitly **not** a digital-twin runtime, simulation solver or topology owner. The domain method `runtimeDigitalTwin()` correctly returns `false`, and the Analytics DDD states that readiness assessment must not mutate Topology, Telemetry or Simulation models.

It is Level 0 because its only externally meaningful references are neutral analytical scope identity and a historical Topology snapshot reference, neither of which is another HMS subject-model dependency.

### 40.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable readiness-assessment identity and persistence primary key. |
| `scopeType` | `String` | Persistence-required analytical discriminator | Identifies the neutral analytical scope namespace/type being assessed. |
| `scopeId` | `String` | Optional polymorphic scope ID | Stable target ID interpreted under `scopeType`; current Analytics scope policy allows neutral cross-module references. |
| `topologySnapshotId` | `String` | Mandatory historical snapshot reference | Identifies the Topology-owned historical snapshot used as assessment context. |
| `assessmentPeriodStart` | `Instant` | Mandatory | Start of the trusted historical assessment window. |
| `assessmentPeriodEnd` | `Instant` | Mandatory | End of the trusted historical assessment window. |
| `telemetryCompletenessScore` | `BigDecimal` | Optional | Derived telemetry-coverage completeness score. |
| `telemetryQualityScore` | `BigDecimal` | Optional | Derived trusted-telemetry quality score. |
| `topologyCompletenessScore` | `BigDecimal` | Optional | Derived topology completeness score. |
| `modelAvailabilityScore` | `BigDecimal` | Optional | Derived availability/readiness score for analytical/model prerequisites. |
| `lineageCompletenessScore` | `BigDecimal` | Optional | Derived data-lineage completeness score. |
| `overallReadinessScore` | `BigDecimal` | Optional | Derived overall readiness score. |
| `readinessStatus` | `DigitalTwinReadinessStatus` | Mandatory in current model | Current fixed readiness classification: `NOT_READY`, `PARTIAL`, `READY`, `ADVANCED`, or `UNKNOWN`. |
| `assessedAt` | `Instant` | Mandatory | Timestamp at which readiness was assessed. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |

The compact constructor already requires nonblank `id` and `topologySnapshotId`, non-null assessment period bounds, non-null `readinessStatus`, and non-null `assessedAt`. It normalizes `scopeType`, `scopeId`, and `topologySnapshotId`.

### 40.3 Analytical-scope semantics

The Analytics DDD defines neutral analytical scopes and lists examples such as network, pipeline system, pipeline, pipeline segment, station, facility, equipment, measurement point, organization unit, product, custody transfer point, HSE site and risk area.

It states that scope fields should use:

```text
scopeType
scopeId
...
topologySnapshotId when applicable
```

Therefore `scopeType + scopeId` is a neutral analytical reference rather than an Analytics-owned FK. `scopeId` is not promoted into a subject-model graph edge and no cross-module database FK is appropriate.

The schema deliberately allows `scope_id` to be nullable, so HMSR-027 does not invent which scope kinds require an object ID.

### 40.4 Historical Topology snapshot boundary

The readiness lifecycle in Analytics DDD begins with:

```text
TopologySnapshot selected
  -> Trusted telemetry coverage checked
    -> Model availability checked
      -> Data lineage checked
        -> DigitalTwinReadinessAssessment created
```

The scalar-reference integrity inventory explicitly classifies:

```text
analytics.DigitalTwinReadinessAssessment.topologySnapshotId
    -> topology.TopologySnapshot
```

as historical/snapshot ownership rather than a live relational dependency.

That classification is correct. Analytics must preserve the immutable/historical snapshot identity and must not create a cross-module database FK into Topology.

A concrete Analytics-owned `TopologySnapshotLookupPort.available(referenceId)` also exists. No live `AssessDigitalTwinReadiness` application use case currently consumes that port, so HMSR-027 does not invent or implement a new write workflow during semantic review. When such a write path is implemented, the existing cross-module lookup boundary is the appropriate fail-closed mechanism for required snapshot availability rather than a database FK.

### 40.5 Confirmed scopeType domain/persistence inconsistency

The final persistence contract requires:

```text
scope_type varchar(80) NOT NULL
```

and `DigitalTwinReadinessAssessmentJpaEntity.scopeType` is mapped with `nullable = false`.

The domain constructor, however, performs only:

```text
scopeType = normalize(scopeType);
```

so null or blank input becomes `null` without rejection.

This is not merely generic display text. `scopeType` is the discriminator that gives semantic meaning to the polymorphic scope and is explicitly part of the Analytics analytical-scope contract. A readiness assessment without a scope namespace is semantically ambiguous and cannot be persisted under the final schema.

### 40.6 Readiness-status vocabulary inconsistency

The same Analytics DDD defines `AnalyticsCatalogEntry` as controlled vocabulary for Analytics business taxonomy and explicitly includes:

```text
READINESS_STATUS
```

among the catalog examples.

The live readiness model instead stores:

```text
DigitalTwinReadinessStatus readinessStatus
```

using a fixed Java enum persisted directly as `readiness_status`.

The enum values themselves are coherent readiness classifications, but the repository currently contains two competing representations for the same conceptual vocabulary:

- DDD-controlled Analytics catalog semantics for `READINESS_STATUS`;
- fixed Java enum/JPA string semantics in `DigitalTwinReadinessAssessment`.

HMSR-027 does not assume the correction must blindly replace the enum with a catalog-entry ID. A separately authorized Analytics correction must decide the authoritative representation: either make readiness classification catalog-backed/stable-code governed as the DDD indicates, or explicitly revise the DDD/catalog contract to establish `DigitalTwinReadinessStatus` as an intentional bounded enum exception.

The target semantic baseline cannot keep both interpretations unresolved.

### 40.7 Score semantics deliberately not invented

The model carries five component scores plus one overall readiness score, but current Analytics DDD does not define:

- a numeric range such as 0..1 or 0..100;
- mandatory presence of every component score;
- weighting or aggregation formula for `overallReadinessScore`;
- threshold bands mapping scores to `NOT_READY/PARTIAL/READY/ADVANCED`;
- whether `UNKNOWN` requires null scores;
- precision beyond the current persistence `numeric(10,6)` representation.

HMSR-027 therefore does not invent score validation or score/status consistency rules.

Those rules should be added only when an authoritative Analytics scoring policy/formula exists.

### 40.8 Assessment-period semantics deliberately not invented

Both assessment-period bounds are mandatory in the current domain and persistence model.

Unlike the twelve Analytics temporal invariants explicitly approved by the repository-wide invariant classification, that classification does **not** include a `DigitalTwinReadinessAssessment.assessmentPeriodStart <= assessmentPeriodEnd` rule.

The Digital Twin readiness DDD section also does not state period ordering explicitly. HMSR-027 therefore does not infer a new temporal invariant solely from field names.

If an assessment-window ordering rule is intended, it should be established by a dedicated Analytics semantic/business rule rather than silently introduced here.

### 40.9 Persistence/application consistency otherwise

The live domain and JPA representations agree on all 15 declared components.

The base Analytics schema requires:

```text
id
scope_type
topology_snapshot_id
assessment_period_start
assessment_period_end
readiness_status
assessed_at
created_at
```

and keeps the six score fields plus `scope_id` nullable.

No active inbound application command/use case or REST endpoint for readiness assessment exists in the current implementation, despite such contracts being recommended by the DDD. This is a capability gap, not evidence that the domain model should be removed.

Generic `createdAt` requiredness remains a persistence/audit concern and is not promoted into a constructor rule.

### 40.10 Required revisions

Two evidence-backed correction obligations remain:

1. **Require a nonblank analytical scope discriminator.** `scopeType` must be consistently mandatory at the domain/application boundary, matching the Analytics scope contract and final JPA/schema requiredness. HMSR-027 does not prescribe which scope types require `scopeId`.

2. **Reconcile readiness-status representation.** Resolve the contradiction between DDD `READINESS_STATUS` catalog vocabulary and the current fixed `DigitalTwinReadinessStatus` enum/JPA representation. The implementation task must establish one authoritative controlled-vocabulary strategy across domain, persistence and future API/application contracts rather than leaving two competing semantics.

HMSR-027 does not modify production Java, JPA, Flyway, API/application contracts, tests, readiness assessments, Topology snapshots or provisioned data.

### 40.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, this assessment is an analytical readiness view answering whether a defined operational scope has sufficiently complete topology, trusted telemetry, model availability and traceable lineage to support later digital-twin scenarios.

It is not authorization to run a digital twin, does not certify a simulation model, and must not mutate the physical-network or operational source-of-truth contexts.

The assessed scope must be unambiguous, and readiness classification must have one governed meaning across analytics, reporting and future digital-twin-readiness consumers.

### 40.12 Review conclusion

**REVISE.** `DigitalTwinReadinessAssessment` has a coherent 15-field analytical role, correct Level-0 placement, correct non-runtime boundary, appropriate historical Topology-snapshot semantics, and deliberately optional component/overall scores.

The target baseline cannot mark it APPROVED while `scopeType` is mandatory in DDD/persistence semantics but optional at domain construction, and while `readinessStatus` simultaneously exists as a fixed Java enum and as an explicitly named Analytics catalog vocabulary without a reconciled authoritative representation.

HMS reconciliation must retain these two obligations until an explicitly authorized Analytics correction task resolves them or the target Analytics DDD is explicitly revised with stronger repository evidence.

## 41. HMSR-028 — configuration.FeatureFlag review

**Decision:** REVISE  
**Review code:** HMSR-028  
**Dependency level:** 0  
**Bounded context:** configuration  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0

### 41.1 Semantic role and ordering rationale

`FeatureFlag` is the Configuration-owned governed runtime toggle definition. It provides stable flag identity/code, multilingual display names, owning-module attribution, lifecycle status, evaluation strategy, default enablement, description and audit timestamps.

It is Level 0 because it has no upstream HMS subject-model dependency. The retained Configuration read/persistence model `FeatureFlagRule` depends on it, but `FeatureFlagRule` is outside the 123 HMS subject set and therefore does not create an HMS graph edge.

The active Configuration DDD explicitly owns feature flags and feature-flag rules while forbidding Configuration from becoming a generic holder for other modules' business taxonomies.

### 41.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable FeatureFlag identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral feature-flag code. |
| `nameFr` | `String` | Persistence-required localized label | French display name. |
| `nameAr` | `String` | Optional localized label | Arabic display name. |
| `nameEn` | `String` | Optional localized label | English display name. |
| `owningModule` | `String` | Persistence-required ownership discriminator | Identifies the Hidra module/business capability whose runtime behavior the flag governs. |
| `status` | `FeatureFlagStatus` | Mandatory | Lifecycle state: `DRAFT`, `ACTIVE`, `PAUSED`, or `RETIRED`. |
| `evaluationStrategy` | `FeatureFlagEvaluationStrategy` | Mandatory | Evaluation strategy: `BOOLEAN`, `PERCENTAGE`, `RULE_BASED`, `ALLOW_LIST`, or `DENY_LIST`. |
| `defaultEnabled` | `boolean` | Mandatory primitive state | Default evaluation result before/without applicable rule refinement. |
| `description` | `String` | Optional | Human-readable purpose/behavior description. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor already rejects blank `id` and `code`, requires non-null `status` and `evaluationStrategy`, normalizes textual fields, and exposes `canEvaluate()` only when `status == ACTIVE`.

### 41.3 FeatureFlagRule downstream persistence boundary

The Configuration DDD retains `FeatureFlagRule` as the rule model governing scoped/rule-based enablement.

Persistence stores:

```text
FeatureFlagRule.featureFlagId
    -> FeatureFlag.id
```

and HRA-111 installs:

```text
fk_hra111_configuration_004
  FOREIGN KEY (feature_flag_id)
  REFERENCES hidra_configuration_feature_flag(id)
  ON DELETE RESTRICT
```

Therefore rule rows cannot reference a missing FeatureFlag at the database boundary.

Because `FeatureFlagRule` is a retained read/persistence model outside the HMS subject population, this relationship does not change the HMSR-028 Level-0 ordering or direct-subject-dependent count.

### 41.4 Creation lifecycle semantics

`ConfigurationApplicationService.createFeatureFlag()` creates new flags with:

```text
status = DRAFT
```

and passes through the requested evaluation strategy and default-enabled value.

That is a conservative creation state: a newly defined runtime toggle is not immediately evaluable because `FeatureFlag.canEvaluate()` returns true only for `ACTIVE`.

Current repository evidence does not define a complete transition matrix among DRAFT, ACTIVE, PAUSED and RETIRED, so HMSR-028 does not invent one.

### 41.5 Confirmed owning-module domain/persistence inconsistency

The strongest evidence-backed defect is `owningModule`.

The final JPA/schema contract requires:

```text
owning_module varchar(80) NOT NULL
```

and `FeatureFlagJpaEntity.owningModule` is mapped with `nullable = false`.

The application create command/request carries `owningModule`, and the summary/response surfaces it as core feature-flag identity context.

The canonical domain constructor, however, performs only:

```text
owningModule = normalize(owningModule);
```

so null or blank input becomes `null` without rejection.

This field is not merely a display label. It identifies the module whose runtime behavior is governed by the Configuration-owned flag and preserves the bounded-context rule that Configuration provides the toggle mechanism without taking ownership of the target module's business semantics.

The current create path performs no separate nonblank validation before persistence. A flag can therefore be valid at the domain layer but fail at the final persistence boundary because its ownership context is absent.

### 41.6 Multilingual-label semantics deliberately not over-promoted

The base schema/JPA contract requires `nameFr` and keeps `nameAr`/`nameEn` nullable.

The current Configuration DDD confirms Configuration-owned multilingual catalog labels but does not state a FeatureFlag-specific domain invariant requiring all localized names or even explicitly elevate `nameFr` to constructor-level semantic identity.

Under the existing HMS/HRA policy, persistence `NOT NULL` on a generic display label alone is not enough to invent a new domain invariant. HMSR-028 therefore does not record a FeatureFlag `nameFr` constructor correction.

The persistence/application boundary must still supply a value compatible with the final schema.

### 41.7 Code, strategy and rule semantics deliberately not invented

The base schema creates an ordinary index on `FeatureFlag.code`, not a unique constraint. No active Configuration DDD/application contract reviewed here defines global or module-scoped code uniqueness, so HMSR-028 does not invent it.

Likewise, current evidence does not define detailed strategy-specific rules such as:

- `BOOLEAN` forbidding FeatureFlagRule rows;
- `PERCENTAGE` requiring a particular percentage field or range;
- `RULE_BASED` requiring at least one active rule;
- `ALLOW_LIST`/`DENY_LIST` requiring a specific scope/list representation;
- relationships between `defaultEnabled` and each strategy.

Those semantics may be desirable, but the current Configuration DDD only establishes that feature-flag rules are allowed; it does not define a complete evaluation algorithm. HMSR-028 does not manufacture one.

### 41.8 Status and evaluation strategy vocabulary

`FeatureFlagStatus` and `FeatureFlagEvaluationStrategy` are bounded behavior/lifecycle concepts directly used by the feature-flag aggregate.

No current Configuration DDD evidence classifies either vocabulary as a catalog-owned business taxonomy or requires replacement by `ConfigurationCatalogEntry`.

Therefore HMSR-028 retains both enums and does not infer a catalog refactor solely because Configuration also owns catalog infrastructure.

### 41.9 Persistence/application consistency otherwise

The live domain and JPA representations agree on all 12 declared components.

The final schema requires:

```text
id
code
name_fr
owning_module
status
evaluation_strategy
default_enabled
created_at
updated_at
```

while Arabic/English names and description remain nullable.

`FeatureFlagRepositoryPort` provides save/find-by-ID operations. The create use case is active and constructs a DRAFT flag before persistence.

Generic audit timestamp requiredness remains a persistence/application-boundary concern and is not promoted into a new constructor invariant.

### 41.10 Required revision

One evidence-backed correction obligation remains:

1. **Require a nonblank owning module consistently at the domain/application boundary.** `FeatureFlag.owningModule` must not normalize to null because the final persistence contract requires it and it provides the bounded-context ownership/routing identity for the governed runtime toggle. The separately authorized Configuration correction task should align domain/application validation with JPA/schema semantics without turning Configuration into the owner of the target module's business taxonomy.

HMSR-028 does not modify production Java, JPA, Flyway, API/application contracts, tests, feature flags/rules or provisioned data.

### 41.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, FeatureFlag is a governed mechanism for activating or pausing application capabilities safely without redefining operational business taxonomies.

A flag should remain attributable to the Hidra module whose runtime behavior it controls. An unattributed feature flag is operationally ambiguous: operators and maintainers cannot reliably determine the capability owner or interpret downstream configuration/rule behavior.

Feature flags must remain software/runtime governance controls; they are not a substitute for safety interlocks, operating procedures, workflow approvals or physical pipeline-control authorization.

### 41.12 Review conclusion

**REVISE.** `FeatureFlag` has a coherent 12-field Configuration-owned model, correct Level-0 placement, conservative DRAFT creation state, appropriate lifecycle/evaluation enums, and database-protected downstream FeatureFlagRule references.

The target baseline cannot mark it APPROVED while `owningModule` is required by the final persistence/application shape but may disappear during canonical domain construction.

HMS reconciliation must retain this ownership-requiredness obligation until an explicitly authorized Configuration correction task resolves it or stronger repository evidence explicitly changes the final FeatureFlag ownership semantics.

## 42. HMSR-029 — custody.CustodyDiscrepancy review

**Decision:** REVISE  
**Review code:** HMSR-029  
**Dependency level:** 0  
**Bounded context:** custody  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 1 — the former unresolved `reconciliationId` is now resolved; optional `quantityUnitId` remains a non-subject catalog dependency

### 42.1 Semantic role and ordering rationale

`CustodyDiscrepancy` is the Custody-owned exception/deviation record attached to an official custody reconciliation. It records the discrepancy identity/type, optional quantity difference and unit, investigation/resolution narrative, optional assigned actor, lifecycle status and timestamps.

It is Level 0 because its required same-module prerequisites — `CustodyReconciliation` and `CustodyCatalogEntry` — are retained Custody read/persistence models outside the 123 HMS subject set. It has no direct HMS subject dependent.

The active Custody DDD keeps this model inside official accepted-transfer governance: Custody owns reconciliation and discrepancies while Telemetry owns measurement truth, Planning owns expected state, and Finance/ERP remains outside Custody.

### 42.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable discrepancy identity and persistence primary key. |
| `discrepancyNumber` | `String` | Mandatory | Business identifier for the discrepancy record. No repository evidence establishes a uniqueness scope. |
| `reconciliationId` | `String` | Mandatory | Same-module reference to the CustodyReconciliation that produced/owns the discrepancy context. |
| `discrepancyTypeId` | `String` | Mandatory | Custody-owned controlled classification reference backed by CustodyCatalogEntry. |
| `status` | `CustodyDiscrepancyStatus` | Mandatory | Lifecycle state: `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `REJECTED`, `RESOLVED`, `CLOSED`, or `CANCELLED`. |
| `differenceQuantity` | `BigDecimal` | Optional | Quantity difference associated with the discrepancy when applicable. |
| `quantityUnitId` | `String` | Optional | Custody-owned controlled unit reference associated with `differenceQuantity` when supplied. |
| `description` | `String` | Optional | Human-readable discrepancy description. |
| `rootCauseText` | `String` | Optional | Recorded root-cause analysis text. |
| `resolutionText` | `String` | Optional | Recorded resolution narrative. |
| `assignedActorId` | `String` | Optional cross-module actor reference | Identity/platform actor assigned to discrepancy handling when applicable. |
| `openedAt` | `Instant` | Mandatory | Discrepancy opening timestamp. |
| `resolvedAt` | `Instant` | Optional | Resolution timestamp when recorded. |
| `closedAt` | `Instant` | Optional | Closure timestamp when recorded. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor already rejects blank `id`, `discrepancyNumber`, `reconciliationId`, and `discrepancyTypeId`; requires non-null `status` and `openedAt`; enforces `openedAt <= closedAt` when a closure timestamp exists; and normalizes textual identifiers/narrative fields.

### 42.3 Reconciliation dependency resolution

HMS-003 left `reconciliationId` unresolved. Stronger current evidence resolves it unambiguously:

- Custody DDD owns both CustodyReconciliation and CustodyDiscrepancy;
- `CustodyReconciliation` is retained as a Custody read/persistence model outside the HMS subject set;
- `CustodyDiscrepancyJpaEntity.reconciliationId` maps to mandatory `hidra_custody_discrepancy.reconciliation_id`;
- HRA-111 installs `fk_hra111_custody_011`;
- that FK links `reconciliation_id -> hidra_custody_reconciliation(id)` with `ON DELETE RESTRICT`.

Therefore `reconciliationId` is a required same-module domain reference to a non-HMS Custody model. No subject-model graph edge is added.

The active open-discrepancy service does not explicitly query the reconciliation before save, but the database FK already provides fail-closed row-existence protection. HMSR-029 does not require duplicate application existence validation solely because the service does not perform a lookup.

### 42.4 Discrepancy-type controlled-value semantics

HMS-003 classified `discrepancyTypeId` generically as a catalog/value dependency. HRA-111 now proves its concrete same-module persistence target:

```text
hidra_custody_discrepancy.discrepancy_type_id
    -> hidra_custody_catalog_entry.id
```

through `fk_hra111_custody_010` with `ON DELETE RESTRICT`.

That is sufficient to resolve the field to CustodyCatalogEntry without adding an HMS graph edge.

Current Custody DDD does not name a specific catalog family/code such as `DISCREPANCY_TYPE`, nor define active-entry eligibility. HMSR-029 therefore does not invent a catalog-name rule beyond the proven Custody-owned catalog target.

### 42.5 Optional quantity-unit integrity gap

`quantityUnitId` is nullable in domain/JPA/schema, so HMSR-029 does not make it mandatory.

However, stronger same-module persistence evidence identifies the intended controlled-value owner:

- HMS-003 already classified `quantityUnitId` as a catalog/value dependency;
- Custody owns `CustodyCatalogEntry`;
- other canonical Custody quantity fields such as `CustodyQuantityCalculation.quantityUnitId` and `CustodyTicketLine.quantityUnitId` are HRA-111-protected against `hidra_custody_catalog_entry(id)`;
- `CustodyDiscrepancy.quantity_unit_id` is indexed as a reference-like field;
- the active `OpenCustodyDiscrepancyCommand` accepts `quantityUnitId` and `CustodyApplicationService.openDiscrepancy()` persists it directly.

Unlike the required quantity-unit references protected by HRA-111, the optional discrepancy `quantity_unit_id` has no FK and no demonstrated application lookup/validation.

Therefore a populated discrepancy can carry a dangling quantity-unit catalog ID even though the repository's Custody quantity-unit pattern identifies the controlled-value owner.

HMSR-029 does not invent a required pairing rule between `differenceQuantity` and `quantityUnitId` because the current DDD does not state whether one may legitimately appear without the other.

### 42.6 Actor-reference boundary

`assignedActorId` remains an optional cross-module actor reference.

There is no Actor subject model in the 123-model HMS population, and Custody must not own Identity state. No cross-module database FK should be introduced.

Current repository evidence also does not define an actor-status/eligibility contract for discrepancy assignment, so HMSR-029 does not invent one.

### 42.7 Lifecycle and temporal rules deliberately not invented

The current status vocabulary contains seven lifecycle labels and the domain already enforces:

```text
openedAt <= closedAt
```

when `closedAt` is present.

The repository-wide invariant classification explicitly retains that invariant but does not define:

- `openedAt <= resolvedAt`;
- `resolvedAt <= closedAt`;
- RESOLVED requiring `resolvedAt`;
- CLOSED requiring both `resolvedAt` and `closedAt`;
- root-cause/resolution text requirements by status;
- allowed status transition sequences.

HMSR-029 therefore does not invent those rules.

The create service conservatively opens a discrepancy as `OPEN`, with root cause, resolution, resolved timestamp and closed timestamp unset.

### 42.8 Quantity and numbering semantics deliberately not invented

Current Custody DDD does not state whether `differenceQuantity` may be signed, must be nonnegative absolute variance, or must match the parent reconciliation's quantity unit.

Likewise, no current schema/DDD evidence establishes global or reconciliation-scoped uniqueness for `discrepancyNumber`.

HMSR-029 records no such obligations.

### 42.9 Persistence/application consistency otherwise

The live domain and JPA representations agree on all 16 declared components.

The base schema requires:

```text
id
discrepancy_number
reconciliation_id
discrepancy_type_id
status
opened_at
created_at
updated_at
```

and keeps quantity, unit, narrative, actor, resolution and closure fields nullable.

The active application path creates `status = OPEN` and defaults `openedAt` to the current instant when the caller omits it. That behavior is conservative and consistent with the mandatory opening timestamp.

Generic audit timestamps remain persistence/application-boundary concerns rather than additional domain constructor invariants.

### 42.10 Required revision

One evidence-backed correction obligation remains:

1. **Protect populated quantity-unit references.** When `CustodyDiscrepancy.quantityUnitId` is non-null, it must resolve to the Custody-owned controlled-value/catalog target rather than allowing a dangling ID. A separately authorized Custody correction may enforce this through application validation, an additive nullable same-module FK, or another fail-closed Custody-owned boundary. HMSR-029 does not prescribe a catalog-family name that current DDD evidence does not define.

HMSR-029 does not modify production Java, JPA, Flyway, API/application contracts, tests, discrepancies, reconciliations, catalogs or provisioned data.

### 42.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC custody-transfer operations, a discrepancy records a governed mismatch or exception discovered during official reconciliation. Its parent reconciliation and discrepancy classification must remain traceable, while any recorded quantity variance must use a valid governed unit when a unit reference is supplied.

The discrepancy lifecycle supports investigation and resolution without making Custody the owner of Identity actor state, telemetry source truth, planning expectations or financial posting.

A dangling quantity-unit reference would weaken the auditability and interpretability of an official custody variance.

### 42.12 Review conclusion

**REVISE.** `CustodyDiscrepancy` has a coherent 16-field Custody-owned model, correct Level-0 placement, a resolved and database-protected parent reconciliation, a database-protected required discrepancy-type catalog reference, conservative OPEN creation semantics, and the approved `openedAt <= closedAt` invariant.

The target baseline cannot mark it APPROVED while a populated optional `quantityUnitId` can persist without demonstrated referential protection to the Custody-controlled quantity-unit catalog target.

HMS reconciliation must retain this optional quantity-unit integrity obligation until an explicitly authorized Custody correction task resolves it or stronger repository evidence explicitly changes the quantity-unit semantics.

## 43. HMSR-030 — organization.OrganizationContactPoint review

**Decision:** APPROVED  
**Review code:** HMSR-030  
**Dependency level:** 0  
**Bounded context:** organization  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0

### 43.1 Semantic role and ordering rationale

`OrganizationContactPoint` is the Organization-owned operational contact-channel model for employees and organization units. It is the canonical Organization representation for operational phone, mobile, email, radio, office and emergency contact data.

It is Level 0 because the contact model does not own another HMS subject model through a graph edge. Its target is represented through the typed value object `ContactPointTargetReference`, whose discriminator is restricted to Organization-owned `EMPLOYEE` and `ORGANIZATION_UNIT` targets.

The Organization roadmap explicitly makes OrganizationContactPoint the canonical new-write path for employee EMAIL/MOBILE/PHONE data, while direct Employee contact fields remain compatibility-only until their separate cutover is complete.

### 43.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable contact-point identity and persistence primary key. |
| `contactPointType` | `ContactPointType` | Mandatory | Governed operational channel: `PHONE`, `MOBILE`, `EMAIL`, `RADIO`, `OFFICE`, or `EMERGENCY`. |
| `target` | `ContactPointTargetReference` | Mandatory | Typed Organization-owned target consisting of `ContactPointTargetType + targetId`. |
| `label` | `String` | Optional | Operator/business label; current roadmap intentionally treats it as single free text rather than auto-generated multilingual state. |
| `value` | `String` | Mandatory | Operational contact value such as phone number, email address or radio call sign. |
| `primaryContact` | `boolean` | Mandatory primitive state | Whether this contact is marked primary for the target. |
| `emergencyContact` | `boolean` | Mandatory primitive state | Whether this channel is intended for emergency use. |
| `active` | `boolean` | Mandatory primitive state | Whether the contact point is active. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The canonical constructor normalizes `id`, `label`, and `value`, rejects a missing ID, missing contact type, missing target, and blank/null contact value.

### 43.3 Governed target and existence validation

The accepted Organization architecture replaced raw textual target state with the canonical value:

```text
OrganizationContactPoint.target
    -> ContactPointTargetReference
       - ContactPointTargetType type
       - String targetId
```

`ContactPointTargetType` permits only `EMPLOYEE` and `ORGANIZATION_UNIT`, and `ContactPointTargetReference` requires both the governed type and a nonblank target ID. This prevents contact points from being attached directly to Identity users, Topology assets, external parties or arbitrary string namespaces.

The current create path invokes `OrganizationContactPointTargetValidator` before save. The validator resolves `EMPLOYEE` through `EmployeeRepositoryPort` and `ORGANIZATION_UNIT` through `OrganizationUnitRepositoryPort`, rejecting a missing target before persistence. Focused tests cover both target kinds and missing-target rejection.

The deprecated textual constructor/accessors remain narrow migration/source compatibility bridges. The canonical domain state remains the typed reference, so these bridges are not an HMSR-030 model defect.

### 43.4 Persistence discriminator integrity

JPA persists `targetType` using `EnumType.STRING` into mandatory `target_type`, while `target_id` remains mandatory.

ORG-046 adds:

```text
CHECK (target_type IN ('EMPLOYEE', 'ORGANIZATION_UNIT'))
```

and its migration preflight rejects unsupported target types and pre-existing orphan typed targets. The migration intentionally does not create a polymorphic foreign key. Current runtime writes are protected through the application validator, while the database protects the finite discriminator vocabulary.

This is consistent with the Organization typed-reference architecture and the repository scalar-reference policy for typed/non-relational identifiers.

### 43.5 Contact type and multilingual semantics

`ContactPointType` is a bounded Organization contact-channel vocabulary rather than an independently governed multilingual business taxonomy. Current evidence does not require replacing it with a catalog.

The Organization multilingual review explicitly treats `OrganizationContactPoint.label` as a single operator/business free-text label and declined automatic `Ar/Fr/En` triplication without clarified semantics. The contact value itself is operational data rather than translatable content.

No multilingual or catalog revision is required.

### 43.6 Rules deliberately not invented

Current repository evidence does not define email syntax, E.164 telephone formatting, radio call-sign structure, one-primary-contact uniqueness, target/contact-type uniqueness, inactive-primary restrictions, mandatory emergency contacts, or coupling between `ContactPointType.EMERGENCY` and `emergencyContact`.

The base schema and Organization roadmap also do not define a broader contact lifecycle transition model. HMSR-030 therefore does not promote those plausible policies into required invariants.

### 43.7 Persistence/application consistency

The domain has 10 canonical components. JPA flattens the target value object into `target_type + target_id` while preserving the same semantics. The base schema requires identity, contact type, target discriminator/ID, value, three boolean flags and audit timestamps; only `label` is nullable.

The canonical application write path generates an Organization-owned ID, validates target existence, constructs the domain object, and persists it transactionally. Tests and architecture guardrails protect the typed target representation and enum-string persistence.

### 43.8 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, OrganizationContactPoint provides governed operational communication coordinates for employees and organization units: telephone/mobile contacts, email addresses, radio channels or call signs, office contacts and emergency communication channels.

Keeping targets inside the Organization bounded context prevents accidental coupling to Identity accounts, Topology assets or external party masters. This model is contact/master data; it does not grant permissions, assign operational responsibility or define notification-delivery policy.

### 43.9 Review conclusion

**APPROVED.** `OrganizationContactPoint` has a coherent 10-field Organization-owned model, correct Level-0 placement, bounded contact-channel vocabulary, typed Organization-only target semantics, fail-closed application target validation, database discriminator protection, correct single-label multilingual treatment, and aligned domain/JPA/schema/application behavior.

No evidence-backed production-model correction is required by HMSR-030. Format validation, primary-contact uniqueness, emergency-contact coupling and additional lifecycle semantics remain intentionally unspecified until an authoritative Organization requirement establishes them.

## 44. HMSR-031 — organization.ReportingLine review

**Decision:** REVISE  
**Review code:** HMSR-031  
**Dependency level:** 0  
**Bounded context:** organization  
**Confirmed upstream subject dependencies:** none — source/target are governed same-module polymorphic references, not single graph edges  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0

### 44.1 Semantic role and ordering rationale

`ReportingLine` is the Organization-owned effective-dated relationship used for simple and matrix reporting among employees, positions, and organization units. It does not own Identity users, Topology assets, or other bounded-context subjects.

It remains Level 0 in the HMS graph because `source` and `target` are typed polymorphic references whose concrete target is selected at runtime by `ReportingSubjectType`. HMSR-031 resolves the governed target set to `Employee`, `Position`, or `OrganizationUnit` without creating three artificial graph edges.

### 44.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable reporting-line identity and persistence primary key. |
| `reportingLineType` | `ReportingLineType` | Mandatory in the live model | Current reporting classification. The representation is not accepted as final because repository vocabulary-audit evidence requires a catalog/reference replacement. |
| `source` | `ReportingSubjectReference` | Mandatory | Typed source reporting subject: employee, position, or organization unit. |
| `target` | `ReportingSubjectReference` | Mandatory | Typed target reporting subject: employee, position, or organization unit. |
| `validFrom` | `Instant` | Mandatory | Inclusive effective start. |
| `validTo` | `Instant` | Optional | Exclusive effective end. |
| `active` | `boolean` | Mandatory primitive state | Whether the relation is active. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor already requires nonblank `id`, non-null line type/source/target, rejects an identical typed source and target, requires `validFrom`, and enforces `validTo > validFrom` when an end exists.

### 44.3 Governed source/target references

The accepted Organization correction replaced raw subject discriminator strings with:

```text
ReportingSubjectReference
  type: ReportingSubjectType
  targetId: String

ReportingSubjectType
  EMPLOYEE
  POSITION
  ORGANIZATION_UNIT
```

JPA persists the governed discriminator values using `EnumType.STRING`. ORG-046 adds database CHECK constraints limiting both `source_type` and `target_type` to the same three values.

This typed-reference direction is semantically correct and should be retained.

### 44.4 Subject-existence integrity gap

The ReportingLine JavaDoc explicitly delegates referenced-object existence to an application/policy boundary because validation requires repositories.

ORG-046 migration preflight verifies that reporting-line rows existing at migration time reference real employees, positions, or organization units. However, the installed database constraints only protect the discriminator values; they do not provide ongoing polymorphic row-existence protection for future inserts.

Unlike `OrganizationContactPoint`, the current live repository has no `ReportingLineApplicationService`, reporting-subject validator, or active inbound reporting-line write use case that performs owner-repository existence checks before save. `ReportingLineRepositoryPort` remains available as persistence plumbing.

Therefore the final reporting-line write/provisioning boundary is not yet fail-closed against a valid discriminator paired with a nonexistent subject ID.

HMSR-031 does not require an impossible single polymorphic foreign key. The correction may use an Organization-owned application validator, guarded persistence path, database trigger/constraint strategy if justified, or equivalent fail-closed mechanism.

### 44.5 ReportingLineType catalog contradiction

Repository controlled-vocabulary audit evidence explicitly classified `ReportingLineType` as:

```text
TYPE_ENUM_TO_CATALOG
REPLACE_FIRST_THEN_DELETE
```

and states that reporting-line business classification should be replaced with a catalog reference plus explicit policy behavior.

No `ReportingLineTypeReference`, reporting-line type catalog model/table, or replacement field exists on live main. Instead the current domain/JPA contract uses the fixed enum:

```text
FUNCTIONAL
ADMINISTRATIVE
OPERATIONAL
TEMPORARY
```

persisted directly in `reporting_line_type`.

This also conflicts with the active Organization roadmap's documented target vocabulary, which includes `LINE`, `OPERATIONAL`, `FUNCTIONAL`, `ADMINISTRATIVE`, `TECHNICAL`, and `DOTTED_LINE`.

HMSR-031 therefore cannot treat the live fixed enum as the final semantic baseline. A separate Organization correction must establish the authoritative catalog/reference representation and migrate domain/JPA/application/schema contracts additively.

### 44.6 Matrix-reporting policy reconciliation gap

The active Organization roadmap states that ReportingLine must support simple and matrix reporting and records rules including:

- one active primary LINE reporting line per employee;
- multiple active FUNCTIONAL reporting lines;
- multiple ADMINISTRATIVE, TECHNICAL, or DOTTED_LINE lines;
- LINE reporting cycles are forbidden;
- disabled employees cannot receive new reporting lines or be assigned as manager;
- reporting lines require an effective start date.

The newer typed-subject model intentionally generalizes relationships beyond employee-to-manager pairs to employees, positions, and organization units. The live record also has no `primaryLine` component, the live type vocabulary lacks `LINE`, `TECHNICAL`, and `DOTTED_LINE`, and no current ReportingLine policy/application service implements the roadmap cardinality, cycle, or employee-lifecycle rules.

This is a semantic reconciliation problem, not permission to blindly restore an older employee-only shape. The correction must decide how the documented employee reporting rules map onto the generalized typed-subject model and which rules apply to position/unit relations.

### 44.7 Rules already sound and rules not invented

The current half-open effective-period invariant (`validTo > validFrom`) is already aligned with Organization invariant policy. Identical typed source/target self-reporting is also correctly rejected in the record.

HMSR-031 does not invent code/name snapshots, cross-module subjects, direct Identity/Topology references, generic uniqueness constraints, or a rule that all reporting relations must be acyclic regardless of reporting-line type. Cycle/cardinality behavior must follow the reconciled Organization reporting policy rather than generic graph intuition.

### 44.8 Persistence consistency

The domain has nine canonical components. JPA flattens source and target references to `source_type/source_id` and `target_type/target_id`; the base schema requires type/ID pairs, line type, `valid_from`, active state, and audit timestamps.

ORG-046 protects finite source/target type vocabularies and preflights pre-existing orphan rows, but does not convert the polymorphic IDs into ordinary FKs. That persistence shape is acceptable only when ongoing writes/provisioning perform equivalent fail-closed target resolution.

### 44.9 Required revisions

Three evidence-backed obligations remain:

1. **Reconcile ReportingLineType with the accepted catalog architecture.** Replace or explicitly redesign the current fixed `ReportingLineType` enum/string persistence according to the existing `TYPE_ENUM_TO_CATALOG` decision, including an authoritative reporting-line type catalog/reference and any separate policy behavior.

2. **Protect typed reporting-subject existence on future writes/provisioning.** A supplied `EMPLOYEE`, `POSITION`, or `ORGANIZATION_UNIT` reference must resolve to an existing Organization-owned subject before a new reporting line becomes authoritative. ORG-046 migration-time preflight alone is insufficient for later rows.

3. **Reconcile and implement the documented matrix-reporting policy against the generalized typed-subject model.** Resolve primary-LINE representation, line-type vocabulary, employee lifecycle eligibility, allowed multiplicity, and LINE-cycle semantics without regressing to an unjustified employee-only model.

HMSR-031 does not modify production Java, JPA, Flyway, API/application contracts, tests, reporting-line rows, or provisioned data.

### 44.10 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, reporting lines represent administrative, operational, functional, technical, or matrix-reporting authority/coordination relationships across personnel and organization structure. Their type and effective period influence how hierarchy and management context are interpreted.

A reporting line must therefore point to real Organization subjects, use governed/evolvable reporting classifications, and apply explicit matrix-reporting rules. It must not silently become an Identity authorization grant or a Topology ownership relation.

### 44.11 Review conclusion

**REVISE.** `ReportingLine` has a sound typed-source/target foundation, correct Organization ownership, a valid half-open effective period, and direct self-reporting protection.

The target baseline cannot mark it APPROVED while the fixed `ReportingLineType` enum contradicts the repository's catalog-refactor decision, future typed references lack ongoing fail-closed existence protection, and the active roadmap's matrix/primary/cycle/lifecycle semantics remain unreconciled with the generalized typed-subject shape.

HMS reconciliation must retain these obligations until an explicitly authorized Organization correction task resolves them or the governing Organization roadmap/catalog decisions are explicitly revised with stronger evidence.

## 45. HMSR-032 — risk.RiskMatrixCell review

**Decision:** REVISE  
**Review code:** HMSR-032  
**Dependency level:** 0  
**Bounded context:** risk  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 2 in the original register; both are now resolved to retained Risk read/persistence models outside the 123 HMS subject set

### 45.1 Semantic role and ordering rationale

`RiskMatrixCell` is the Risk-owned scoring lookup for one likelihood/consequence pair within a specific RiskMatrix version. It supplies the score, rating and governance flags used when Risk assessments are scored.

It remains Level 0 because its required references resolve to Risk-owned `RiskMatrix`, `RiskCatalogEntry`, and `RiskRating` read/persistence models outside the 123-model HMS subject population.

### 45.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable matrix-cell identity and persistence primary key. |
| `riskMatrixId` | `String` | Mandatory | Same-module reference to the RiskMatrix/version that owns the cell. |
| `likelihoodLevelId` | `String` | Mandatory | Controlled Risk catalog entry representing a likelihood level. |
| `consequenceLevelId` | `String` | Mandatory | Controlled Risk catalog entry representing a consequence level. |
| `scoreValue` | `BigDecimal` | Mandatory | Numeric score for the matrix coordinate. Risk DDD explicitly requires it to be non-negative. |
| `ratingId` | `String` | Mandatory | Reference to RiskRating such as LOW/MEDIUM/HIGH/CRITICAL. |
| `colorCode` | `String` | Optional | Presentation/display color associated with the matrix cell. |
| `requiresTreatment` | `boolean` | Mandatory primitive state | Whether this cell/rating requires risk treatment. |
| `requiresApproval` | `boolean` | Mandatory primitive state | Whether this cell requires governed approval. |
| `requiresExecutiveAcceptance` | `boolean` | Mandatory primitive state | Whether this cell requires executive acceptance/escalation. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The canonical constructor already rejects blank IDs/references and a null score, normalizes textual IDs/color, and exposes `treatmentOrApprovalRequired()`.

### 45.3 Parent matrix and rating dependency resolution

HMS-003 left `riskMatrixId` and `ratingId` unresolved. HRA-111 now resolves both concretely:

```text
hidra_risk_matrix_cell.risk_matrix_id
    -> hidra_risk_matrix.id
    via fk_hra111_risk_027

hidra_risk_matrix_cell.rating_id
    -> hidra_risk_rating.id
    via fk_hra111_risk_026
```

Both constraints use `ON DELETE RESTRICT` and were validated against existing rows. RiskMatrix and RiskRating are retained Risk read/persistence models outside the HMS subject set, so no HMS graph edge is added.

The DDD requirement that `ratingId` be catalog-backed is therefore satisfied at the row-reference level by the dedicated RiskRating catalog-style model.

### 45.4 Likelihood and consequence catalog semantics

Risk DDD explicitly defines Risk catalog families:

```text
RISK_LIKELIHOOD_LEVEL
RISK_CONSEQUENCE_LEVEL
```

and the matrix-cell fields carry those exact semantics.

HRA-111 installs:

```text
consequence_level_id -> hidra_risk_catalog_entry.id
likelihood_level_id  -> hidra_risk_catalog_entry.id
```

through `fk_hra111_risk_024` and `fk_hra111_risk_025`.

Those FKs correctly prevent dangling catalog IDs, but they do not demonstrate catalog-family validation. A `likelihoodLevelId` could still reference an unrelated RiskCatalogEntry unless the write/provisioning boundary checks `catalogName = RISK_LIKELIHOOD_LEVEL`; the same issue applies to consequence level.

No current RiskMatrixCell application service or dedicated write policy was found that performs this family validation.

### 45.5 Missing matrix-cell uniqueness invariant

Risk DDD explicitly requires:

```text
unique cell per matrix, likelihood, consequence
```

The base schema has separate indexes for `risk_matrix_id`, `likelihood_level_id`, and `consequence_level_id`, but no unique constraint or unique index on the three-column matrix coordinate.

Repository search also found no application/domain uniqueness policy for RiskMatrixCell.

Therefore two different rows can currently represent the same matrix coordinate and potentially carry conflicting scores/ratings.

This is a master-data integrity defect because scoring must resolve deterministically to one cell for a given matrix version and likelihood/consequence pair.

### 45.6 Missing nonnegative-score invariant

Risk DDD explicitly states:

```text
scoreValue must be non-negative
```

The domain constructor only checks `scoreValue != null`. The JPA mapping and `numeric(18,6) NOT NULL` schema likewise permit negative values, and no later CHECK constraint was found.

Therefore a semantically invalid negative risk score can be constructed and persisted.

This obligation is stronger than a generic numeric-validation preference because the active Risk DDD states the rule directly.

### 45.7 Governance booleans and rating policy deliberately not over-inferred

`requiresTreatment`, `requiresApproval`, and `requiresExecutiveAcceptance` are stored directly on the matrix cell. `RiskRating` separately carries `requiresTreatment` and `requiresApproval` properties.

Current repository evidence does not state that the cell booleans must always equal the referenced RiskRating values. They may represent matrix-specific escalation policy beyond the reusable rating classification.

HMSR-032 therefore does not invent equality constraints between cell and rating flags, nor does it infer executive-acceptance thresholds from rating severity.

`colorCode` is presentation metadata; no authoritative format such as hex RGB is defined, so no format invariant is added.

### 45.8 Matrix completeness and immutability boundary

Risk DDD states that an approved matrix must contain complete cells and an active matrix version must be immutable.

Those are aggregate/matrix-level lifecycle rules rather than locally decidable RiskMatrixCell constructor invariants. HMSR-032 records no cell-level mutation rule beyond the explicit cell invariants above; the future RiskMatrix review/correction must own completeness and active-version immutability.

### 45.9 Required revisions

Three evidence-backed correction obligations remain:

1. **Enforce matrix-coordinate uniqueness.** The combination `(riskMatrixId, likelihoodLevelId, consequenceLevelId)` must identify at most one RiskMatrixCell. A separately authorized Risk correction should implement this with an additive unique database constraint/index and aligned application behavior.

2. **Enforce nonnegative score values.** `scoreValue < 0` must be rejected at an appropriate fail-fast domain/application boundary and/or by an additive database CHECK, consistent with the explicit Risk DDD invariant.

3. **Protect catalog-family semantics for likelihood and consequence levels.** A populated `likelihoodLevelId` must resolve to the `RISK_LIKELIHOOD_LEVEL` Risk catalog family and `consequenceLevelId` to `RISK_CONSEQUENCE_LEVEL`; generic row-existence FKs alone do not establish the required taxonomy.

HMSR-032 does not modify production Java, JPA, Flyway, API/application contracts, tests, risk matrices/cells/catalogs, or provisioned data.

### 45.10 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, RiskMatrixCell is governed risk-methodology master data. A single matrix version must map each likelihood/consequence coordinate deterministically to one nonnegative score and one controlled rating.

Duplicate coordinates, negative scores, or likelihood/consequence IDs drawn from the wrong taxonomy would make inherent/residual risk calculations inconsistent and could alter treatment, approval or executive-acceptance decisions.

The cell defines scoring policy; it does not own the operational event, pipeline condition, HSE case, integrity defect, or other source fact being assessed.

### 45.11 Review conclusion

**REVISE.** `RiskMatrixCell` has a coherent 12-field Risk-owned role, correct Level-0 placement, required reference validation, and database-protected parent matrix/rating/catalog row existence.

The target baseline cannot mark it APPROVED while the explicit DDD uniqueness and nonnegative-score invariants are unenforced and while likelihood/consequence catalog references are not demonstrably restricted to their required Risk catalog families.

HMS reconciliation must retain these obligations until an explicitly authorized Risk correction task resolves them or stronger repository evidence explicitly changes the Risk matrix semantics.

## 46. HMSR-033 — telemetry.TelemetrySource review

**Decision:** REVISE  
**Review code:** HMSR-033  
**Dependency level:** 0  
**Bounded context:** telemetry  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0

### 46.1 Semantic role and ordering rationale

`TelemetrySource` is the Telemetry-owned acquisition-source model representing systems such as SCADA, historians, OPC-UA servers, MQTT brokers, API/import feeds, manual sources, and edge gateways.

It is Level 0 because its taxonomy references resolve to Telemetry-owned catalog rows outside the 123 HMS subject set. Downstream persistence models such as TelemetryDevice and TelemetrySourceEndpoint reference it, but they are not direct HMS subject dependents in this roadmap.

Telemetry DDD explicitly owns acquisition-source metadata while keeping secrets outside telemetry persistence and keeping physical-network ownership in Topology.

### 46.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable source identity and persistence primary key. |
| `code` | `String` | Mandatory and DDD-unique | Stable source code such as a SCADA/historian acquisition source identifier. |
| `nameAr` | `String` | Optional localized label | Arabic source name. |
| `nameFr` | `String` | DDD/persistence-required localized label | French source name. |
| `nameEn` | `String` | Optional localized label | English source name. |
| `sourceTypeId` | `String` | Mandatory controlled reference | Must resolve to an active Telemetry catalog entry in family `SOURCE_TYPE`. |
| `protocolId` | `String` | Mandatory controlled reference | Must resolve to an active Telemetry catalog entry in family `PROTOCOL`. |
| `endpointUri` | `String` | Optional legacy/simple endpoint | Endpoint URI for simple source configuration; must not contain secret material. |
| `externalReference` | `String` | Optional external identifier | SCADA/historian/integration source reference; must not contain secret material. |
| `status` | `TelemetryLifecycleStatus` | Mandatory | Source lifecycle state. Current source-specific DDD vocabulary and shared enum are not fully reconciled. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The current constructor already rejects blank `id`, `code`, `sourceTypeId`, and `protocolId`, requires non-null status, and normalizes textual fields.

### 46.3 Source-type and protocol dependency resolution

Telemetry DDD defines:

```text
sourceTypeId -> TelemetryCatalogEntry where catalogName = SOURCE_TYPE
protocolId   -> TelemetryCatalogEntry where catalogName = PROTOCOL
```

HRA-111 installs database FKs:

```text
hidra_telemetry_source.source_type_id
    -> hidra_telemetry_type_catalog.id
    via fk_hra111_telemetry_022

hidra_telemetry_source.protocol_id
    -> hidra_telemetry_type_catalog.id
    via fk_hra111_telemetry_021
```

so dangling IDs are prevented. These catalog rows are retained Telemetry read/persistence models outside the HMS subject set; no HMS graph edge is added.

However, generic FK existence does not prove that the referenced row belongs to the required catalog family or is active.

### 46.4 Missing catalog-family and active-entry validation

TelemetrySource DDD states explicitly:

```text
sourceTypeId must reference an active SOURCE_TYPE catalog entry.
protocolId must reference an active PROTOCOL catalog entry.
```

The current create command/request accepts raw IDs, and `TelemetrySourceApplicationService.createTelemetrySource()` passes them directly into the aggregate before persistence.

No current Telemetry catalog lookup/validation boundary was found for source creation. Therefore a valid catalog-row ID from the wrong family, or an inactive row, can satisfy the FK while violating source semantics.

### 46.5 Missing source-code uniqueness enforcement

The Telemetry DDD explicitly says:

```text
code must be unique.
```

The Telemetry roadmap's target metadata shape also describes source `code unique`.

The live schema currently has an ordinary index on `hidra_telemetry_source(code)` rather than a unique constraint/index, and the repository port exposes no code-existence lookup used by the create service.

Therefore two TelemetrySource rows can currently carry the same business code.

### 46.6 Required French source label mismatch

The source-specific DDD field table marks `nameFr` as required. JPA and the base migration also declare `name_fr NOT NULL`.

The canonical domain constructor nevertheless treats `nameFr` like the optional labels and normalizes null/blank input to `null`. The active create service does not add a separate guard.

Unlike a generic persistence-only label inference, this obligation is supported directly by the TelemetrySource DDD itself.

### 46.7 Secret-material persistence gap

TelemetrySource DDD explicitly requires:

```text
Secrets must never be stored in endpointUri or externalReference.
```

The active source-creation path accepts both strings and persists them directly. No Telemetry secret-material guard, sanitization policy, or external credential-reference enforcement was found for this write path.

HMSR-033 does not invent a password-detection regex, URI syntax, or vault-reference format. The future correction must fail closed against actual secret material while preserving legitimate sanitized endpoint URIs and external identifiers.

### 46.8 Source lifecycle vocabulary reconciliation

The source-specific DDD lists source lifecycle values:

```text
DRAFT
ACTIVE
INACTIVE
SUSPENDED
RETIRED
```

The shared live `TelemetryLifecycleStatus` enum also contains `PLANNED` and `MAINTENANCE`. Those additional values are supported elsewhere in Telemetry, notably device semantics, but the Telemetry roadmap conceptually distinguishes source/device/point lifecycle enums.

As currently modeled, a TelemetrySource can therefore be persisted in `PLANNED` or `MAINTENANCE` even though the source-specific DDD does not define those states.

HMSR-033 does not choose whether those states should be added to the source DDD or excluded from source state. A separate Telemetry correction must establish one authoritative source lifecycle contract.

### 46.9 ACTIVE-source ingestion rule

The DDD states that only ACTIVE sources can ingest telemetry.

The current repository does not expose the future high-velocity ingestion application contract yet; the extended-capability roadmap keeps that work deferred. Therefore HMSR-033 records this as a boundary rule that must be preserved when ingestion is implemented, rather than inventing a missing ingestion service solely to satisfy this review.

The current create path correctly creates new sources in `DRAFT`, so source creation itself does not violate the ACTIVE-only ingestion rule.

### 46.10 Multilingual and endpoint ownership semantics

The explicit `nameAr/nameFr/nameEn` fields are consistent with Telemetry's multilingual roadmap. Source type and protocol remain catalog-backed user-facing taxonomies, which is also consistent with the roadmap's prohibition on fixed Java enums for those business classifications.

`endpointUri` is documented as a legacy/simple endpoint field. Multi-endpoint configuration belongs to retained `TelemetrySourceEndpoint`, which references TelemetrySource and has its own protocol/credential-reference semantics. HMSR-033 does not remove `endpointUri` merely because the richer endpoint model exists.

### 46.11 Required revisions

Five evidence-backed correction obligations remain:

1. **Enforce unique TelemetrySource code.** The DDD-defined unique source code must be protected at the persistence boundary, preferably with an additive unique constraint/index and aligned application behavior.

2. **Validate source type and protocol catalog semantics.** `sourceTypeId` must resolve to an active `SOURCE_TYPE` entry and `protocolId` to an active `PROTOCOL` entry; generic FK row existence is insufficient.

3. **Require the DDD-mandated French source label.** `nameFr` must not normalize to null at the canonical source creation/domain boundary while the target DDD and schema require it.

4. **Prevent secret material in source endpoint/external-reference fields.** The source write boundary must reject or sanitize actual secrets instead of persisting them in `endpointUri` or `externalReference`; credential material belongs outside telemetry persistence.

5. **Reconcile TelemetrySource lifecycle vocabulary.** Establish whether `PLANNED` and `MAINTENANCE` are valid source states or are only valid for other Telemetry entities, and align source domain/application/persistence/API semantics accordingly.

HMSR-033 does not modify production Java, JPA, Flyway, API/application contracts, tests, telemetry source/catalog data, or provisioned data.

### 46.12 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, TelemetrySource identifies an authoritative acquisition origin such as SCADA, historian, OPC gateway, MQTT broker, API feed, or manual/import channel.

A source must be uniquely identifiable, classified with the correct governed source/protocol taxonomies, and free of embedded credentials. Incorrect taxonomy or duplicated source identity can contaminate provenance; embedded secrets create an operational-security exposure.

Source lifecycle also matters operationally because only sources authorized as ACTIVE should later contribute ingestable telemetry.

### 46.13 Review conclusion

**REVISE.** `TelemetrySource` has a coherent 12-field Telemetry-owned acquisition role, correct Level-0 placement, catalog-backed source/protocol references, multilingual naming shape, conservative DRAFT creation, and database-protected catalog row existence.

The target baseline cannot mark it APPROVED while source-code uniqueness, catalog family/active eligibility, required French label semantics, secret-material exclusion, and source-specific lifecycle vocabulary remain inconsistent or unenforced.

HMS reconciliation must retain these obligations until an explicitly authorized Telemetry correction task resolves them or the governing Telemetry DDD/roadmap is explicitly revised with stronger evidence.

## 47. HMSR-034 — topology.TopologyConnection review

**Decision:** REVISE  
**Review code:** HMSR-034  
**Dependency level:** 0  
**Bounded context:** topology  
**Confirmed upstream HMS subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Non-subject/read-persistence prerequisites:** 3 — two TopologyNode references and one optional PipelineSegment reference

### 47.1 Semantic role and ordering rationale

`TopologyConnection` is the Topology-owned explicit graph edge between two topology nodes. It carries the connection classification, directionality, optional pipeline-segment association, optional nominal-capacity metadata, lifecycle state and audit timestamps.

The active Topology roadmap distinguishes `PipelineSegment` as the linear physical pipe asset between two nodes and `TopologyConnection` as the explicit graph edge between nodes. This distinction is retained.

It remains HMS Level 0 because HMS-005 already classifies `TopologyNode` and `PipelineSegment` as retained Topology read/persistence prerequisites outside the 123-model HMS subject graph. Resolving these references therefore does not add an HMS graph edge.

### 47.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable topology-connection identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable connection code. Current evidence does not establish a uniqueness scope. |
| `fromNodeId` | `String` | Mandatory | Source/end-A TopologyNode reference. |
| `toNodeId` | `String` | Mandatory | Target/end-B TopologyNode reference. |
| `connectionType` | `ConnectionType` in live code | Mandatory, but representation requires revision | Business classification of the graph edge; accepted Topology architecture requires a catalog-backed `ConnectionTypeReference`. |
| `flowDirection` | `FlowDirection` | Mandatory | Technical graph/flow direction: `DIRECTED`, `BIDIRECTIONAL`, or `UNKNOWN`. |
| `pipelineSegmentId` | `String` | Optional | Optional association to retained Topology PipelineSegment master data. |
| `nominalCapacity` | `BigDecimal` | Optional | Optional nominal-capacity metadata. No current authoritative range/sign rule was found. |
| `capacityUnitCode` | `String` | Optional | Unit code associated with nominal capacity when supplied; current evidence does not define a governed unit-reference model or mandatory pairing rule. |
| `status` | `TopologyStatus` | Mandatory | Technical lifecycle state: `DRAFT`, `ACTIVE`, `SUSPENDED`, `RETIRED`, or `ARCHIVED`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The canonical record already rejects blank `id`, `code`, `fromNodeId`, and `toNodeId`, and requires non-null connection type, flow direction and lifecycle status.

### 47.3 Node dependency resolution and integrity

HMS-005 already records:

```text
topology.TopologyConnection.fromNodeId/toNodeId
    -> persistence/read TopologyNode
```

and the scalar-reference integrity inventory resolves both fields to `topology.TopologyNode`.

HRA-111 installs:

```text
fk_hra111_topology_001
  from_node_id -> hidra_topology_node(id)

fk_hra111_topology_002
  to_node_id -> hidra_topology_node(id)
```

both with `ON DELETE RESTRICT` and validated against existing rows.

Therefore both required node references are now semantically and relationally resolved. No production correction is required merely for node existence.

### 47.4 Self-connection invariant enforcement gap

The repository contains `TopologyConnectionValidator`, which states the domain rule:

```text
Topology connection must not connect a node to itself.
```

However, the canonical `TopologyConnection` constructor does not reject `fromNodeId == toNodeId`, the base schema has no corresponding CHECK constraint, and repository search found no active application write service invoking `TopologyConnectionValidator`.

Consequently, the domain rule exists but is not guaranteed by the model/persistence boundary. A self-loop can be constructed and, through a direct repository/persistence path, stored while satisfying both node foreign keys.

HMSR-034 does not prescribe whether the invariant belongs in the record constructor, an authoritative domain/application policy, an additive database CHECK, or a combination. It requires only that authoritative writes fail closed.

### 47.5 Connection-type catalog regression

The active Topology roadmap states that **Connection type** is a catalog-backed controlled vocabulary and that business type concepts must use catalog references rather than fixed Java enum fields.

The accepted architecture artifacts specifically show:

```text
TopologyConnection -> ConnectionTypeReference
```

and the COR2-007 controlled-vocabulary audit classifies `ConnectionType` as `REPLACE_FIRST_THEN_DELETE`, while `ConnectionTypeReference` is the intended retained reference object.

Live `main`, however, has no `ConnectionTypeReference.java`. `TopologyConnection` and `TopologyConnectionJpaEntity` both use the fixed Java enum:

```text
PIPELINE_SEGMENT
DIRECT_LINK
VIRTUAL_LINK
TRANSFER_LINK
MEASUREMENT_LINK
```

and persistence stores it directly in `connection_type varchar(80) NOT NULL` using `EnumType.STRING`. The consolidated current Topology migration contains no connection-type catalog table/reference column for this model.

This is a direct contradiction between accepted Topology controlled-vocabulary architecture and the current production model/persistence shape. The semantic baseline cannot preserve both as authoritative.

### 47.6 Optional PipelineSegment reference integrity gap

HMS-005 explicitly identifies:

```text
topology.TopologyConnection.pipelineSegmentId
    -> persistence/read PipelineSegment
```

so the optional field is no longer semantically unresolved.

The base schema indexes `pipeline_segment_id`, but HRA-111 does not add an FK for this nullable field, and no active TopologyConnection write service or lookup validator was found that checks a populated segment ID before persistence.

Therefore a non-null `pipelineSegmentId` can currently be stored without demonstrated fail-closed proof that the referenced PipelineSegment exists.

HMSR-034 does not make the segment association mandatory and does not infer that every `PIPELINE_SEGMENT` connection type must carry one, because current authoritative evidence does not state that coupling.

### 47.7 FlowDirection and lifecycle semantics

`FlowDirection` behaves as technical graph semantics rather than a user-maintained business taxonomy, and current evidence does not classify it for catalog replacement. HMSR-034 therefore retains the enum.

`TopologyStatus` was explicitly retained as a lifecycle enum by the controlled-vocabulary audit. No status-transition matrix specific to TopologyConnection is defined by current evidence, so none is invented.

### 47.8 Capacity semantics deliberately not invented

Current repository evidence does not establish:

- that `nominalCapacity` must be positive or nonnegative;
- that `nominalCapacity` and `capacityUnitCode` must always appear together;
- a canonical capacity-unit catalog/owner for this field;
- conversion semantics or base units;
- connection-type-specific capacity rules.

HMSR-034 therefore records no capacity invariant beyond the existing optional representation.

### 47.9 Code and graph uniqueness deliberately not invented

The base schema contains only an ordinary index on `code`, and no current Topology DDD/roadmap evidence reviewed here states a global or scoped connection-code uniqueness invariant.

Likewise, current evidence does not define uniqueness for `(fromNodeId, toNodeId, connectionType)`, nor whether parallel logical edges between the same nodes are allowed. Such rules are not inferred from generic graph modeling.

### 47.10 Required revisions

Three evidence-backed correction obligations remain:

1. **Restore/reconcile catalog-backed connection-type semantics.** Replace the live fixed `ConnectionType` enum/string persistence with the accepted `ConnectionTypeReference`/catalog architecture, or explicitly revise the governing Topology architecture if that earlier decision is no longer intended. Domain, JPA, Flyway, API/application contracts and migration strategy must converge on one representation.

2. **Enforce the no-self-connection domain rule on authoritative writes.** `fromNodeId` and `toNodeId` must not resolve to the same node when a TopologyConnection becomes authoritative. The existing unused validator is insufficient by itself.

3. **Protect populated PipelineSegment references.** When `pipelineSegmentId` is non-null, it must resolve to the retained Topology PipelineSegment target through an additive nullable FK, application validation, guarded provisioning path, or equivalent fail-closed mechanism.

HMSR-034 does not modify production Java, JPA, Flyway, API/application contracts, tests, topology master data, or provisioned data.

### 47.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, TopologyConnection is the governed graph edge used to represent network connectivity between physical/logical topology nodes. It is distinct from the pipe-segment asset itself even when a connection is associated with a PipelineSegment.

Node existence, valid edge classification and prevention of unintended self-connections are important for map rendering, routing, simulation snapshots, telemetry anchoring and downstream network reasoning. A dangling segment link or classification drift would weaken network traceability.

### 47.12 Review conclusion

**REVISE.** `TopologyConnection` has a coherent 12-field Topology-owned graph role, required node references protected by HRA-111, correct technical flow/lifecycle enums, and a clear distinction from PipelineSegment.

The target baseline cannot mark it APPROVED while the live fixed `ConnectionType` representation contradicts the accepted catalog-reference architecture, the explicit no-self-loop rule is not enforced on authoritative writes, and populated optional `pipelineSegmentId` values lack demonstrated referential protection.

HMS reconciliation must retain these obligations until an explicitly authorized Topology correction task resolves them or the governing Topology architecture is explicitly revised with stronger evidence.

## 48. HMSR-035 — organization.OrganizationUnit review

**Decision:** REVISE  
**Review code:** HMSR-035  
**Dependency level:** 1  
**Bounded context:** organization  
**Strongly connected component:** SCC-01 — hierarchy self-reference through `parentUnitId`  
**Confirmed upstream subject dependencies:** `organization.OrganizationUnitType` plus the OrganizationUnit self-reference used for hierarchy ordering  
**Confirmed direct HMS subject dependents:** 22  
**Transitive HMS subject dependents:** 51  
**Unresolved/non-subject references:** 0

### 48.1 Semantic role and ordering rationale

`OrganizationUnit` is the Organization-owned internal structure node for company/division/region/area/station-as-organization-unit/team/department hierarchy. It owns organizational identity and hierarchy only; operational responsibility is modeled separately through `OperationalScope` / `ResponsibilityAssignment`, and physical stations/facilities remain Topology-owned.

Its high downstream fan-out makes this model foundational for workflow, assets, integration, reporting, risk, integrity, leak-detection and other bounded contexts that carry stable OrganizationUnit references.

It appears at Level 1 because `OrganizationUnitType` was reviewed and approved first, while `parentUnitId` creates the validated OrganizationUnit hierarchy self-reference collapsed as SCC-01 for ordering.

### 48.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable OrganizationUnit identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral Organization business code normalized through `OrganizationCode`. |
| `nameAr` | `String` | Optional localized text | Arabic unit display name. |
| `nameFr` | `String` | Optional localized text | French unit display name. |
| `nameEn` | `String` | Optional localized text | English unit display name. |
| `unitTypeId` | `String` | Mandatory | Same-module subject reference to `OrganizationUnitType`. |
| `parentUnitId` | `String` | Optional | Same-model hierarchy reference to the parent OrganizationUnit. |
| `status` | `OrganizationUnitStatus` | Mandatory | Lifecycle state: `ACTIVE`, `INACTIVE`, `MERGED`, or `CLOSED`. |
| `validFrom` | `Instant` | Mandatory in the canonical domain | Effective-period start. |
| `validTo` | `Instant` | Optional | Exclusive effective-period end; when present it must be strictly after `validFrom`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The constructor already requires nonblank ID/code/unit type, non-null status and `validFrom`, normalizes multilingual values and IDs, rejects direct self-parenting, and enforces the half-open effective interval with `validTo > validFrom` when an end is present.

### 48.3 Unit-type dependency and the HMS-003 correction

HMS-003 previously classified `unitTypeId` as a generic catalog/value reference with no graph edge. Stronger evidence now makes that classification obsolete:

- HMSR-001 approved `OrganizationUnitType` as the Organization-owned subject model that classifies OrganizationUnit;
- the validated HMS graph already records `OrganizationUnit.unitTypeId -> OrganizationUnitType` as a subject dependency;
- ORG-046 installs `fk_org_unit_type`, linking `hidra_org_unit.unit_type_id -> hidra_org_unit_type(id)` with `ON DELETE RESTRICT`.

HMSR-035 therefore records `unitTypeId` as a required same-module domain reference and subject-model graph edge.

The database FK correctly protects existence. It does not, however, prove that the selected unit type is currently selectable.

### 48.4 Inactive unit-type selection gap

HMSR-001 already approved the meaning of `OrganizationUnitType.active`: deactivation preserves historical identity while indicating whether the type is currently selectable/usable.

`OrganizationUnitApplicationService` currently depends only on `OrganizationUnitRepositoryPort`; it does not resolve `unitTypeId` through `OrganizationUnitTypeRepositoryPort` before creating a new unit. The FK therefore accepts an existing but inactive type.

This permits a new authoritative OrganizationUnit to be created using a type that the approved catalog semantics mark as unavailable for new selection.

HMSR-035 does not require historical units to stop referencing a deactivated type. The correction applies to authoritative new/changed unit classification, while historical references remain valid.

### 48.5 Parent hierarchy reference and cycle integrity

HMS-004 validated the OrganizationUnit hierarchy self-reference and collapsed it into SCC-01. ORG-046 installs:

```text
fk_org_unit_parent
  parent_unit_id -> hidra_org_unit(id)
  ON DELETE RESTRICT
```

so a non-null parent must exist.

The canonical record also rejects the simplest cycle:

```text
parentUnitId == id
```

but the Organization roadmap explicitly requires:

```text
Organization hierarchy cannot contain cycles.
```

and separately states that hierarchy-cycle detection across multiple records belongs to the application/database boundary rather than the domain constructor.

No current OrganizationUnit application policy/service traverses parent ancestry, and the self-FK cannot reject a longer cycle such as A -> B -> C -> A. A provisioning/reparenting path can therefore create a cyclic hierarchy while satisfying row-existence constraints.

The correction must fail closed for hierarchy mutations/provisioning without moving repository traversal into the domain record.

### 48.6 Effective-period persistence mismatch

The current Organization roadmap's required domain baseline states that effective-dated models require `validFrom`, and the OrganizationUnit constructor enforces that rule.

JPA nevertheless maps:

```text
@Column(name = "valid_from", nullable = true)
```

and the base schema defines nullable `valid_from timestamp with time zone`. No later Organization migration makes the column non-null.

This allows persisted rows that the canonical domain cannot reconstruct through the persistence mapper. The create request/command documentation also describes `validFrom` as optional even though canonical domain construction rejects its absence.

The final OrganizationUnit contract must therefore converge on the already-established mandatory effective-start semantics across domain, application/API requiredness, JPA and the final schema.

### 48.7 Multilingual semantics

`nameAr/nameFr/nameEn` follow the current Organization same-entity multilingual policy. The three fields are nullable in domain and JPA, and current Organization migration policy does not authorize inventing missing translations.

No evidence requires all three names, or French specifically, to be constructor-level mandatory for OrganizationUnit. HMSR-035 therefore records no multilingual correction.

### 48.8 Operational-scope ownership

Current Organization architecture deliberately separates organizational identity from operational responsibility. Historical `operational_scope_*` columns on `hidra_org_unit` were retired by `V20260929_006__retire_legacy_operational_scope_columns.sql`, and the canonical Java/JPA model no longer carries them.

This is the correct target shape. A `STATION_UNIT` represents the people/responsibility structure associated with an operational station; it must not be treated as the physical Topology Facility itself. Operational authority belongs to the governed scope/responsibility models rather than OrganizationUnit identity.

### 48.9 Code and lifecycle rules deliberately not invented

`OrganizationCode` already supplies the common Organization normalization rule for unit code. The final schema has an ordinary code index, not a unique constraint, and current authoritative evidence reviewed here does not establish a specific OrganizationUnit code uniqueness scope. HMSR-035 therefore does not invent one.

`OrganizationUnitStatus` is explicitly retained as a lifecycle enum by the controlled-vocabulary audit. Current evidence does not define a complete transition matrix among ACTIVE, INACTIVE, MERGED and CLOSED, nor does it prove that newly created units must start in a state other than the caller/default ACTIVE behavior. No transition rule is invented.

The separate roadmap rule that disabled units cannot receive new employee assignments belongs to EmployeeAssignment/application policy and does not require removing INACTIVE/MERGED/CLOSED units from historical hierarchy references.

### 48.10 Persistence/application consistency otherwise

The live domain and JPA representations agree on the same 12 canonical components. The final schema has removed the obsolete operational-scope compatibility columns. `unitTypeId` and `parentUnitId` receive same-module FK protection, code/status/audit fields are persisted directly, and multilingual names remain nullable.

`OrganizationUnitApplicationService` generates an Organization-owned ID, uses canonical `OrganizationCode`, creates no legacy operational-scope state, and saves transactionally. The remaining gaps are the cross-row/catalog eligibility and persistence-requiredness issues documented above.

### 48.11 Required revisions

Three evidence-backed correction obligations remain:

1. **Prevent multi-record OrganizationUnit hierarchy cycles.** Authoritative hierarchy mutations and provisioning must reject any parent assignment that would create a cycle, not only direct self-parenting. Repository traversal/application policy or an equivalent fail-closed database/provisioning mechanism is required.

2. **Reject inactive OrganizationUnitType selection for new/changed units.** `unitTypeId` existence is already protected by FK, but authoritative writes must also enforce the approved `OrganizationUnitType.active` selectable/usable semantics while preserving historical references to deactivated types.

3. **Align mandatory `validFrom` semantics across persistence and boundaries.** JPA/final schema and create/update contracts must not admit an OrganizationUnit without the effective start required by the canonical domain; use an additive migration and compatible boundary validation in a separately authorized Organization correction task.

HMSR-035 does not modify production Java, JPA, Flyway, API/application contracts, tests, organization hierarchy data, or provisioned data.

### 48.12 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, OrganizationUnit represents the internal responsibility/people hierarchy: company, divisions, regions, areas, station organizational units, departments and teams.

Acyclic hierarchy is critical because workflow routing, reporting, responsibility assignment, risk ownership and operational escalation depend on stable organizational ancestry. Type availability must govern new master-data selection without erasing historical classifications, and effective dates must remain reconstructable.

A station OrganizationUnit remains distinct from the physical station/facility in Topology.

### 48.13 Review conclusion

**REVISE.** `OrganizationUnit` has a coherent 12-field Organization-owned hierarchy role, correct same-entity multilingual shape, correct separation from operational-scope responsibility, normalized stable code, a database-protected type reference, a database-protected parent reference, direct self-parent protection, and an already-enforced half-open domain interval.

The target baseline cannot mark it APPROVED while longer hierarchy cycles remain possible, inactive unit types can be selected for new units despite the approved catalog semantics, and persistence can store `validFrom = NULL` even though the canonical domain requires an effective start.

HMS reconciliation must retain these obligations until an explicitly authorized Organization correction task resolves them or stronger repository evidence explicitly changes the governing Organization semantics.

## 49. Current next task

```text
HMSR-036 — organization.AdministrativeDistrict
```

Exact commit message:

```text
docs(model-review): review organization AdministrativeDistrict
```

Start HMSR-036 only after HMSR-035 is committed and reported. Do not start HMSR-037 automatically. HMS-006 final reconciliation remains blocked until all 123 HMSR tasks are resolved.
