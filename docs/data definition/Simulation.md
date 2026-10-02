# HIDRA Simulation Data Definition Document

```text
Document code : HIDRA-SIMULATION-DDD
Module        : simulation
Namespace     : dz.sh.hidra.modules.simulation
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC Digitalization Initiative
Author        : Abir MEDJERAB
Status        : Target DDD, architecture-aligned
CreatedOn     : 2026-06-11
Version       : 1.0
```

---

## 1. Purpose

The **simulation** module is the decision-support bounded context for running hydraulic, operational, optimization, and what-if scenarios over a trusted pipeline topology and trusted operational data.

Simulation answers:

```text
What happens if this operating plan is applied?
What happens if a station, segment, pump, compressor, route, or delivery point changes state?
Which operating configuration best satisfies objectives and constraints?
Which constraints are violated?
Which candidate network configuration is recommended?
What assumptions, inputs, model version, and solver profile produced the result?
Can the recommendation be traced back to topology, telemetry, planning, and actor context?
```

Simulation **does not own the real network**. It owns scenarios, model inputs, simulation runs, result sets, optimization candidates, explanations, and recommendations.

The correct rule is:

```text
Simulation recommends.
Topology owns the approved network state.
Workflow approves.
Audit proves.
```

---

## 2. Source-of-truth positioning

No implemented `simulation` Java module was found during repository inspection. This document is therefore a **target data definition** grounded in the Hidra architecture and the earlier module ownership decisions.

Simulation belongs after trusted topology, telemetry, planning, and monitoring readiness:

```text
Topology
  -> Telemetry
      -> Planning
          -> Monitoring
              -> Simulation
```

Simulation is also a bridge toward future digital twin capability, but it must remain operationally safe: it may recommend actions and propose topology/operating candidates, but it must not directly actuate SCADA, PLC, RTU, SIS, ESD, valves, pumps, compressors, or field equipment.

---

## 3. Scope

### 3.1 Simulation owns

```text
Simulation models
Model versions
Simulation scenarios
Scenario assumptions
Scenario input snapshots
Hydraulic/operational input datasets
Simulation constraints
Simulation objectives
Simulation runs
Run steps and solver traces
Simulation result summaries
Result time-series references
Node/segment/facility/equipment result values
Constraint evaluation results
Optimization candidates
Candidate topology/operating changes
Candidate comparison scores
Recommendations
Validation findings
Sensitivity analyses
Simulation evidence links
Simulation catalogs and translations
```

### 3.2 Simulation does not own

```text
Physical topology
Official topology snapshots
Pipeline systems, pipelines, segments, stations, facilities, or equipment
Telemetry readings
Trusted telemetry readings
Operational plans and nominations
Monitoring rules and thresholds
Alarm lifecycle
Incident lifecycle
Asset maintenance execution
Network integrity assessments
Custody transfer records
Workflow approval state
Audit ledger
Notification delivery
SCADA commands or field actuation
```

---

## 4. Ownership boundary with key modules

| Concept | Owner | Simulation role |
|---|---|---|
| Real pipeline network | Topology | Reads topology snapshots as input |
| Candidate network configuration | Simulation first | Produces as recommendation candidate |
| Approved network configuration | Topology | Receives approved proposal and creates official topology version |
| Operational facts | Telemetry | Consumes trusted readings as input snapshots |
| Expected operating plan | Planning | Uses plan targets as scenario baseline |
| Deviation state | Monitoring | Uses monitoring state as input/context |
| Formal alarm lifecycle | Alarm Management | May reference alarms, does not own them |
| Incident response | Incident Management | May simulate incident scenarios, does not manage incident lifecycle |
| Maintenance execution | Asset Management | May simulate maintenance unavailability, does not execute work orders |
| Integrity condition | Network Integrity | May use defect/constraint data, does not assess integrity ownership |
| Approval | Workflow | Workflow approves scenario publication or candidate adoption |
| Evidence | Audit | Audit records decisions and changes |
| Messages | Notification | Notification sends result/approval notices |
| Runtime settings | Configuration | Provides solver/runtime settings only |

---

## 5. Core lifecycle

### 5.1 Standard simulation lifecycle

```text
DRAFT
  -> READY
      -> QUEUED
          -> RUNNING
              -> COMPLETED
              -> FAILED
              -> CANCELLED
              -> EXPIRED
```

### 5.2 Optimization-to-topology lifecycle

```text
Active Topology Snapshot
  -> Simulation Scenario
      -> Simulation Run
          -> Optimized Network Configuration Candidate
              -> Simulation Recommendation
                  -> Topology Change Proposal
                      -> Workflow Approval
                          -> New Topology Snapshot / Topology Version
```

### 5.3 Important rule

A simulation result is not operational truth.

```text
A result becomes operationally official only when the owning module accepts it.
```

For network configuration:

```text
SimulationNetworkConfigurationCandidate
  -> TopologyChangeProposal
      -> Workflow approval
          -> TopologySnapshot / TopologyVersion
```

---

## 6. Aggregate model

### 6.1 Aggregates

```text
SimulationModel
SimulationScenario
SimulationRun
SimulationOptimizationCandidate
SimulationRecommendation
```

### 6.2 Entity list

```text
SimulationModel
SimulationModelVersion
SimulationScenario
SimulationScenarioAssumption
SimulationInputSnapshot
SimulationInputDataset
SimulationConstraint
SimulationObjective
SimulationRun
SimulationRunStep
SimulationSolverTrace
SimulationResultSummary
SimulationResultValue
SimulationResultSeriesReference
SimulationConstraintEvaluation
SimulationOptimizationCandidate
SimulationCandidateChange
SimulationCandidateOperatingCondition
SimulationCandidateScore
SimulationRecommendation
SimulationValidationFinding
SimulationSensitivityAnalysis
SimulationEvidenceLink
SimulationCatalogEntry
SimulationCatalogTranslation
```

---

## 7. Entity definitions

### 7.1 SimulationModel

Represents a reusable simulation model definition.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationModelId | Yes | Stable model identifier |
| code | String | Yes | Unique business code |
| nameAr | String | No | Arabic name |
| nameFr | String | Yes | French name |
| nameEn | String | No | English name |
| modelTypeId | CatalogRef | Yes | HYDRAULIC, OPTIMIZATION, WHAT_IF, INCIDENT_RESPONSE, etc. |
| topologyScopeType | String | Yes | PIPELINE_SYSTEM, PIPELINE, SEGMENT_GROUP, FACILITY_NETWORK |
| topologyScopeId | String | No | Optional default topology scope |
| status | String | Yes | DRAFT, ACTIVE, RETIRED |
| description | String | No | Model purpose |
| createdAt | Instant | Yes | Creation time |
| updatedAt | Instant | Yes | Last update time |

### 7.2 SimulationModelVersion

Immutable version of a model configuration.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationModelVersionId | Yes | Stable identifier |
| modelId | SimulationModelId | Yes | Parent model |
| versionNumber | Integer | Yes | Monotonic version |
| solverProfileId | CatalogRef | Yes | Solver profile/catalog reference |
| modelDefinitionHash | String | Yes | Hash of model definition payload |
| compatibleTopologyVersion | String | No | Optional topology compatibility marker |
| status | String | Yes | DRAFT, ACTIVE, RETIRED |
| activatedAt | Instant | No | Activation time |
| retiredAt | Instant | No | Retirement time |
| createdAt | Instant | Yes | Creation time |

Rule:

```text
Completed simulation runs must always reference a model version, never a mutable model definition.
```

### 7.3 SimulationScenario

Defines a what-if or optimization case before execution.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationScenarioId | Yes | Stable identifier |
| code | String | Yes | Unique scenario code |
| nameAr | String | No | Arabic label |
| nameFr | String | Yes | French label |
| nameEn | String | No | English label |
| scenarioTypeId | CatalogRef | Yes | WHAT_IF, OPTIMIZATION, CAPACITY, SHUTDOWN, LEAK_RESPONSE, etc. |
| modelId | SimulationModelId | Yes | Simulation model |
| modelVersionId | SimulationModelVersionId | Yes | Model version |
| topologySnapshotId | String | Yes | Input topology snapshot reference |
| planningReferenceId | String | No | Optional plan/revision reference |
| monitoringContextId | String | No | Optional monitoring/evaluation context |
| status | String | Yes | DRAFT, READY, LOCKED, ARCHIVED |
| createdByActorId | String | Yes | Actor snapshot source |
| createdByDisplayNameSnapshot | String | Yes | Actor display snapshot |
| createdAt | Instant | Yes | Creation time |
| updatedAt | Instant | Yes | Last update time |

Rule:

```text
A scenario must be locked before execution to freeze inputs and assumptions.
```

### 7.4 SimulationScenarioAssumption

Stores explicit assumptions used by a scenario.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationAssumptionId | Yes | Stable identifier |
| scenarioId | SimulationScenarioId | Yes | Parent scenario |
| assumptionTypeId | CatalogRef | Yes | DEMAND, SUPPLY, EQUIPMENT_STATE, PRODUCT_PROPERTY, etc. |
| targetType | String | No | FACILITY, SEGMENT, EQUIPMENT, NODE, PRODUCT |
| targetId | String | No | Referenced target |
| parameterCode | String | Yes | Assumption parameter |
| valueType | String | Yes | DECIMAL, STRING, BOOLEAN, DATE_TIME, JSON |
| valueText | String | Yes | Stored normalized value |
| unitCode | String | No | Unit if numeric |
| confidenceLevelId | CatalogRef | No | LOW, MEDIUM, HIGH |
| sourceNote | String | No | Source/explanation |
| createdAt | Instant | Yes | Creation time |

### 7.5 SimulationInputSnapshot

Immutable snapshot reference set used for a run.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationInputSnapshotId | Yes | Stable identifier |
| scenarioId | SimulationScenarioId | Yes | Parent scenario |
| topologySnapshotId | String | Yes | Topology input |
| telemetrySnapshotReference | String | No | Trusted telemetry input window/reference |
| planningSnapshotReference | String | No | Plan/revision input |
| monitoringSnapshotReference | String | No | Monitoring state input |
| integritySnapshotReference | String | No | Optional integrity constraints |
| assetAvailabilitySnapshotReference | String | No | Optional asset constraints |
| capturedAt | Instant | Yes | Snapshot capture time |
| captureHash | String | Yes | Input set hash |

Rule:

```text
Simulation must run on snapshots, not live mutable records.
```

### 7.6 SimulationInputDataset

A concrete dataset attached to an input snapshot.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationInputDatasetId | Yes | Stable identifier |
| inputSnapshotId | SimulationInputSnapshotId | Yes | Parent snapshot |
| datasetTypeId | CatalogRef | Yes | TOPOLOGY, TELEMETRY, PLAN, PRODUCT_PROPERTY, EQUIPMENT_STATE |
| sourceModule | String | Yes | topology, telemetry, planning, etc. |
| sourceReference | String | Yes | External source reference |
| recordCount | Long | No | Dataset size |
| checksum | String | No | Integrity checksum |
| createdAt | Instant | Yes | Creation time |

### 7.7 SimulationConstraint

Constraint that must be checked during simulation or optimization.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationConstraintId | Yes | Stable identifier |
| scenarioId | SimulationScenarioId | Yes | Parent scenario |
| constraintTypeId | CatalogRef | Yes | PRESSURE_MAX, PRESSURE_MIN, FLOW_MAX, CAPACITY, HSE, INTEGRITY, COMMERCIAL |
| targetType | String | No | PIPELINE, SEGMENT, FACILITY, EQUIPMENT, NODE |
| targetId | String | No | Target reference |
| expressionText | String | Yes | Human-readable constraint |
| limitValue | Decimal | No | Numeric limit |
| unitCode | String | No | Unit |
| severityId | CatalogRef | Yes | INFO, WARNING, BLOCKING |
| active | Boolean | Yes | Active flag |
| createdAt | Instant | Yes | Creation time |

### 7.8 SimulationObjective

Optimization objective.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationObjectiveId | Yes | Stable identifier |
| scenarioId | SimulationScenarioId | Yes | Parent scenario |
| objectiveTypeId | CatalogRef | Yes | MAX_THROUGHPUT, MIN_ENERGY, MIN_PRESSURE_LOSS, MIN_RISK, BALANCE_DELIVERY |
| weight | Decimal | Yes | Optimization weight |
| priorityOrder | Integer | Yes | Objective order |
| targetType | String | No | Optional target type |
| targetId | String | No | Optional target reference |
| expressionText | String | No | Objective description/expression |
| createdAt | Instant | Yes | Creation time |

### 7.9 SimulationRun

Execution of a locked scenario.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationRunId | Yes | Stable identifier |
| scenarioId | SimulationScenarioId | Yes | Scenario |
| modelVersionId | SimulationModelVersionId | Yes | Model version |
| inputSnapshotId | SimulationInputSnapshotId | Yes | Frozen input snapshot |
| runTypeId | CatalogRef | Yes | WHAT_IF, OPTIMIZATION, VALIDATION, BACKTEST |
| status | String | Yes | QUEUED, RUNNING, COMPLETED, FAILED, CANCELLED |
| requestedByActorId | String | Yes | Actor reference |
| requestedByDisplayNameSnapshot | String | Yes | Actor snapshot |
| queuedAt | Instant | Yes | Queue time |
| startedAt | Instant | No | Start time |
| completedAt | Instant | No | End time |
| durationMillis | Long | No | Execution duration |
| solverProfileId | CatalogRef | Yes | Solver profile used |
| correlationId | String | No | Correlation id |
| failureReason | String | No | Failure reason |
| createdAt | Instant | Yes | Creation time |

Rule:

```text
A run is immutable after completion except for publication/archival metadata.
```

### 7.10 SimulationRunStep

Execution step inside a run.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationRunStepId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent run |
| stepOrder | Integer | Yes | Execution order |
| stepCode | String | Yes | LOAD_INPUTS, VALIDATE, SOLVE, OPTIMIZE, EVALUATE, PERSIST_RESULTS |
| status | String | Yes | PENDING, RUNNING, COMPLETED, FAILED |
| startedAt | Instant | No | Start time |
| completedAt | Instant | No | End time |
| message | String | No | Diagnostic message |

### 7.11 SimulationSolverTrace

Diagnostic trace for solver execution.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationSolverTraceId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent run |
| iterationNumber | Integer | No | Solver iteration |
| traceLevel | String | Yes | INFO, WARNING, ERROR, DEBUG |
| metricCode | String | No | Solver metric |
| metricValue | Decimal | No | Metric value |
| message | String | No | Trace text |
| recordedAt | Instant | Yes | Trace time |

### 7.12 SimulationResultSummary

High-level result of a run.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationResultSummaryId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent run |
| feasible | Boolean | Yes | Whether scenario satisfies blocking constraints |
| objectiveScore | Decimal | No | Aggregate score |
| constraintViolationCount | Integer | Yes | Number of violations |
| warningCount | Integer | Yes | Number of warnings |
| resultStatusId | CatalogRef | Yes | FEASIBLE, INFEASIBLE, PARTIAL, FAILED |
| summaryText | String | No | Human-readable summary |
| createdAt | Instant | Yes | Creation time |

### 7.13 SimulationResultValue

Point result for node, segment, facility, equipment, or other simulated target.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationResultValueId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent run |
| targetType | String | Yes | NODE, SEGMENT, FACILITY, EQUIPMENT, PIPELINE, SYSTEM |
| targetId | String | Yes | Target reference |
| metricCode | String | Yes | FLOW, PRESSURE, TEMPERATURE, ENERGY, VELOCITY, UTILIZATION |
| value | Decimal | Yes | Result value |
| unitCode | String | No | Unit |
| timeOffsetSeconds | Long | No | Optional simulation-time offset |
| recordedAt | Instant | Yes | Persistence time |

### 7.14 SimulationResultSeriesReference

Reference to external or large result series storage.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationResultSeriesReferenceId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent run |
| seriesTypeId | CatalogRef | Yes | PRESSURE_PROFILE, FLOW_PROFILE, TEMPERATURE_PROFILE, ENERGY_PROFILE |
| targetType | String | No | Optional target type |
| targetId | String | No | Optional target reference |
| storageLocation | String | Yes | Object/time-series storage reference |
| checksum | String | No | Integrity checksum |
| createdAt | Instant | Yes | Creation time |

### 7.15 SimulationConstraintEvaluation

Outcome of a constraint check.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationConstraintEvaluationId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent run |
| constraintId | SimulationConstraintId | Yes | Evaluated constraint |
| status | String | Yes | SATISFIED, VIOLATED, NOT_EVALUATED |
| observedValue | Decimal | No | Observed value |
| limitValue | Decimal | No | Limit value |
| unitCode | String | No | Unit |
| severityId | CatalogRef | Yes | INFO, WARNING, BLOCKING |
| explanation | String | No | Explanation |
| createdAt | Instant | Yes | Creation time |

### 7.16 SimulationOptimizationCandidate

A candidate configuration produced by optimization.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationCandidateId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent optimization run |
| candidateNumber | Integer | Yes | Rank/order |
| candidateStatus | String | Yes | GENERATED, SELECTED, DISCARDED, PROPOSED_TO_TOPOLOGY |
| feasible | Boolean | Yes | Whether blocking constraints satisfied |
| objectiveScore | Decimal | No | Score |
| rank | Integer | No | Candidate rank |
| summaryText | String | No | Summary |
| selectedByActorId | String | No | Actor selecting candidate |
| selectedAt | Instant | No | Selection time |
| createdAt | Instant | Yes | Creation time |

Rule:

```text
A candidate is a recommendation candidate, not an official topology version.
```

### 7.17 SimulationCandidateChange

Proposed topology or operating change inside a candidate.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationCandidateChangeId | Yes | Stable identifier |
| candidateId | SimulationCandidateId | Yes | Parent candidate |
| changeTypeId | CatalogRef | Yes | ROUTE_CHANGE, EQUIPMENT_STATE, FLOW_TARGET, PRESSURE_TARGET, SEGMENT_STATUS, FACILITY_MODE |
| targetType | String | Yes | PIPELINE, SEGMENT, FACILITY, EQUIPMENT, NODE, CONNECTION |
| targetId | String | Yes | Target reference |
| beforeValue | String | No | Snapshot before value |
| afterValue | String | Yes | Candidate value |
| unitCode | String | No | Unit if numeric |
| requiresTopologyChange | Boolean | Yes | Whether topology proposal needed |
| requiresOperationalProcedure | Boolean | Yes | Whether field procedure is needed |
| safetyCritical | Boolean | Yes | Safety marker |
| explanation | String | No | Reason |
| createdAt | Instant | Yes | Creation time |

Important safety rule:

```text
SimulationCandidateChange is descriptive only.
It must not execute field commands.
```

### 7.18 SimulationCandidateOperatingCondition

Candidate expected operating state if adopted.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationCandidateOperatingConditionId | Yes | Stable identifier |
| candidateId | SimulationCandidateId | Yes | Parent candidate |
| targetType | String | Yes | SEGMENT, NODE, FACILITY, EQUIPMENT |
| targetId | String | Yes | Target reference |
| metricCode | String | Yes | FLOW, PRESSURE, TEMPERATURE, ENERGY, UTILIZATION |
| expectedValue | Decimal | Yes | Expected value |
| unitCode | String | No | Unit |
| timeOffsetSeconds | Long | No | Optional time offset |
| createdAt | Instant | Yes | Creation time |

### 7.19 SimulationCandidateScore

Detailed score component for a candidate.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationCandidateScoreId | Yes | Stable identifier |
| candidateId | SimulationCandidateId | Yes | Parent candidate |
| objectiveId | SimulationObjectiveId | No | Related objective |
| scoreCode | String | Yes | ENERGY, THROUGHPUT, RISK, CONSTRAINT_MARGIN, COST |
| scoreValue | Decimal | Yes | Score value |
| weight | Decimal | No | Weight used |
| rankContribution | Decimal | No | Ranking contribution |
| explanation | String | No | Explanation |

### 7.20 SimulationRecommendation

Human-facing recommendation derived from a run or candidate.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationRecommendationId | Yes | Stable identifier |
| runId | SimulationRunId | Yes | Parent run |
| candidateId | SimulationCandidateId | No | Optional candidate |
| recommendationTypeId | CatalogRef | Yes | ADOPT_CONFIGURATION, REVIEW_CONSTRAINT, REJECT_SCENARIO, RUN_ADDITIONAL_CASE |
| recommendationStatus | String | Yes | DRAFT, PUBLISHED, SENT_TO_WORKFLOW, ACCEPTED, REJECTED, SUPERSEDED |
| title | String | Yes | Recommendation title |
| description | String | Yes | Recommendation text |
| confidenceLevelId | CatalogRef | No | LOW, MEDIUM, HIGH |
| targetModule | String | No | topology, planning, monitoring, etc. |
| targetProposalReference | String | No | Proposal id once exported to target module |
| publishedByActorId | String | No | Publisher actor |
| publishedAt | Instant | No | Publication time |
| createdAt | Instant | Yes | Creation time |

### 7.21 SimulationValidationFinding

Validation issue found before or after a run.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationValidationFindingId | Yes | Stable identifier |
| scenarioId | SimulationScenarioId | No | Scenario reference |
| runId | SimulationRunId | No | Run reference |
| findingTypeId | CatalogRef | Yes | MISSING_INPUT, INCONSISTENT_TOPOLOGY, INVALID_UNIT, SOLVER_WARNING |
| severityId | CatalogRef | Yes | INFO, WARNING, ERROR, BLOCKING |
| targetType | String | No | Target type |
| targetId | String | No | Target id |
| message | String | Yes | Finding message |
| resolved | Boolean | Yes | Resolution flag |
| resolvedAt | Instant | No | Resolution time |
| createdAt | Instant | Yes | Creation time |

### 7.22 SimulationSensitivityAnalysis

Stores comparison of parameter sensitivity runs.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationSensitivityAnalysisId | Yes | Stable identifier |
| scenarioId | SimulationScenarioId | Yes | Parent scenario |
| baseRunId | SimulationRunId | Yes | Base run |
| parameterCode | String | Yes | Varied parameter |
| parameterRangeText | String | Yes | Range description |
| resultMetricCode | String | Yes | Observed metric |
| sensitivityScore | Decimal | No | Sensitivity score |
| summaryText | String | No | Summary |
| createdAt | Instant | Yes | Creation time |

### 7.23 SimulationEvidenceLink

Evidence link to documents, audit, workflow, topology, telemetry, planning, monitoring, or external files.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationEvidenceLinkId | Yes | Stable identifier |
| ownerType | String | Yes | SCENARIO, RUN, CANDIDATE, RECOMMENDATION |
| ownerId | String | Yes | Owner id |
| evidenceType | String | Yes | DOCUMENT, AUDIT_EVENT, WORKFLOW, EXTERNAL_FILE, TOPOLOGY_SNAPSHOT |
| evidenceReference | String | Yes | Reference id |
| label | String | No | Human label |
| createdAt | Instant | Yes | Creation time |

### 7.24 SimulationCatalogEntry

Controlled vocabulary for simulation business taxonomies.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationCatalogEntryId | Yes | Stable identifier |
| catalogName | String | Yes | Catalog family |
| code | String | Yes | Code |
| active | Boolean | Yes | Active flag |
| sortOrder | Integer | Yes | Display order |
| systemDefined | Boolean | Yes | Whether system-protected |
| createdAt | Instant | Yes | Creation time |
| updatedAt | Instant | Yes | Last update time |

### 7.25 SimulationCatalogTranslation

Localized label for catalog entries.

| Field | Type | Required | Notes |
|---|---|---:|---|
| id | SimulationCatalogTranslationId | Yes | Stable identifier |
| catalogEntryId | SimulationCatalogEntryId | Yes | Parent catalog entry |
| locale | String | Yes | ar, fr, en |
| name | String | Yes | Localized name |
| description | String | No | Localized description |
| createdAt | Instant | Yes | Creation time |
| updatedAt | Instant | Yes | Last update time |

---

## 8. Business catalogs

Recommended catalog families:

```text
SIMULATION_MODEL_TYPE
SIMULATION_SCENARIO_TYPE
SIMULATION_RUN_TYPE
SIMULATION_SOLVER_PROFILE
SIMULATION_CONSTRAINT_TYPE
SIMULATION_OBJECTIVE_TYPE
SIMULATION_RESULT_STATUS
SIMULATION_CANDIDATE_STATUS
SIMULATION_CHANGE_TYPE
SIMULATION_RECOMMENDATION_TYPE
SIMULATION_RECOMMENDATION_STATUS
SIMULATION_FINDING_TYPE
SIMULATION_SEVERITY
SIMULATION_CONFIDENCE_LEVEL
SIMULATION_DATASET_TYPE
SIMULATION_SERIES_TYPE
SIMULATION_ASSUMPTION_TYPE
```

Technical lifecycle states may use constrained enums/check constraints. Business classifications should be catalog-backed.

---

## 9. Recommended table names

```text
hidra_simulation_model
hidra_simulation_model_version
hidra_simulation_scenario
hidra_simulation_scenario_assumption
hidra_simulation_input_snapshot
hidra_simulation_input_dataset
hidra_simulation_constraint
hidra_simulation_objective
hidra_simulation_run
hidra_simulation_run_step
hidra_simulation_solver_trace
hidra_simulation_result_summary
hidra_simulation_result_value
hidra_simulation_result_series_reference
hidra_simulation_constraint_evaluation
hidra_simulation_optimization_candidate
hidra_simulation_candidate_change
hidra_simulation_candidate_operating_condition
hidra_simulation_candidate_score
hidra_simulation_recommendation
hidra_simulation_validation_finding
hidra_simulation_sensitivity_analysis
hidra_simulation_evidence_link
hidra_simulation_catalog_entry
hidra_simulation_catalog_translation
```

---

## 10. Ports and integration contracts

### 10.1 Inbound ports

```text
CreateSimulationModelUseCase
ActivateSimulationModelVersionUseCase
CreateSimulationScenarioUseCase
LockSimulationScenarioUseCase
AddSimulationAssumptionUseCase
AddSimulationConstraintUseCase
AddSimulationObjectiveUseCase
ValidateSimulationScenarioUseCase
StartSimulationRunUseCase
CancelSimulationRunUseCase
RecordSimulationRunResultUseCase
PublishSimulationRecommendationUseCase
SelectSimulationCandidateUseCase
SubmitCandidateToTopologyUseCase
GetSimulationScenarioUseCase
GetSimulationRunUseCase
SearchSimulationResultsUseCase
CompareSimulationCandidatesUseCase
```

### 10.2 Outbound ports

```text
TopologySnapshotLookupPort
TelemetryTrustedReadingSnapshotPort
PlanningSnapshotLookupPort
MonitoringContextLookupPort
AssetAvailabilityLookupPort
IntegrityConstraintLookupPort
SimulationSolverPort
SimulationResultStoragePort
TopologyChangeProposalPort
WorkflowStartPort
AuditEventPort
DocumentReferencePort
NotificationRequestPort
```

### 10.3 Forbidden dependencies

```text
Do not import topology.domain models directly.
Do not import telemetry.domain models directly.
Do not write topology tables from simulation.
Do not write telemetry readings from simulation.
Do not write planning plans from simulation.
Do not bypass workflow when adopting a candidate.
Do not bypass audit for published recommendations or topology proposal submission.
Do not send SCADA/PLC/RTU/SIS/ESD commands.
```

---

## 11. Events

Recommended domain events:

```text
SimulationModelCreated
SimulationModelVersionActivated
SimulationScenarioCreated
SimulationScenarioLocked
SimulationScenarioValidated
SimulationRunQueued
SimulationRunStarted
SimulationRunCompleted
SimulationRunFailed
SimulationOptimizationCandidateGenerated
SimulationCandidateSelected
SimulationRecommendationPublished
SimulationCandidateSubmittedToTopology
SimulationRecommendationRejected
```

---

## 12. API surface candidate

```text
/api/v1/simulations/models
/api/v1/simulations/models/{modelId}/versions
/api/v1/simulations/scenarios
/api/v1/simulations/scenarios/{scenarioId}
/api/v1/simulations/scenarios/{scenarioId}/assumptions
/api/v1/simulations/scenarios/{scenarioId}/constraints
/api/v1/simulations/scenarios/{scenarioId}/objectives
/api/v1/simulations/scenarios/{scenarioId}/validate
/api/v1/simulations/scenarios/{scenarioId}/lock
/api/v1/simulations/runs
/api/v1/simulations/runs/{runId}
/api/v1/simulations/runs/{runId}/steps
/api/v1/simulations/runs/{runId}/results
/api/v1/simulations/runs/{runId}/candidates
/api/v1/simulations/candidates/{candidateId}
/api/v1/simulations/candidates/{candidateId}/select
/api/v1/simulations/candidates/{candidateId}/submit-to-topology
/api/v1/simulations/recommendations
/api/v1/simulations/recommendations/{recommendationId}/publish
/api/v1/simulations/catalogs/{catalogName}
```

---

## 13. Validation rules and invariants

```text
A scenario cannot run unless it references a topology snapshot.
A scenario cannot run unless it is locked.
A run must reference an immutable model version.
A run must reference an immutable input snapshot.
A completed run cannot be modified.
An optimization candidate cannot become official topology state directly.
A candidate submitted to topology must create a topology change proposal, not update topology tables.
A candidate change marked safetyCritical must require workflow review before adoption.
A published recommendation must emit audit-ready evidence.
A failed run must retain failure diagnostics.
A result value must specify target type, target id, metric code, and unit when applicable.
A constraint evaluation must preserve the observed value and limit when numeric.
```

---

## 14. Safety policy

Simulation is decision-support only.

```text
Simulation must not actuate field equipment.
Simulation must not write SCADA commands.
Simulation must not write PLC/RTU/SIS/ESD commands.
Simulation must not directly open/close valves.
Simulation must not directly start/stop pumps or compressors.
Simulation must not bypass human approval for operationally material changes.
```

The operational adoption path is always:

```text
Recommendation
  -> Proposal in owning module
      -> Workflow approval
          -> Audit evidence
              -> Human/authorized operational execution outside Hidra direct control
```

---

## 15. Relationship with Configuration

Configuration may own runtime settings for simulation execution:

```text
default solver profile
maximum run duration
maximum candidate count
default optimization tolerance
feature flag: enable optimization
result retention profile
```

Configuration must not own:

```text
simulation scenario
simulation run
simulation result
optimized route
candidate network configuration
candidate equipment state
candidate station configuration
topology change proposal
approved topology snapshot
```

Rule:

```text
Configuration configures the simulation engine.
Simulation owns the case and result.
Topology owns the approved network configuration.
```

---

## 16. Relationship with Topology

Simulation consumes topology snapshots and may produce candidate changes.

Simulation does not own:

```text
PipelineSystem
Pipeline
PipelineSegment
TopologyNode
TopologyConnection
Facility
Equipment
MeasurementLocation
TopologySnapshot
TopologyVersion
```

When simulation identifies an optimal network configuration, it must export it to topology as a proposal:

```text
SimulationOptimizationCandidate
  -> SimulationCandidateChange[]
      -> TopologyChangeProposal
          -> TopologyChangeSet
              -> TopologySnapshot / TopologyVersion after approval
```

---

## 17. Relationship with Planning and Monitoring

Planning provides expected operating targets. Monitoring provides actual/deviation context.

Simulation may answer:

```text
Will this plan violate constraints?
Can this flow target be achieved with current topology?
What alternative route minimizes risk/energy/deviation?
What pressure/flow behavior should be expected under this candidate?
```

But:

```text
Planning owns approved operational plans.
Monitoring owns deviation detection and operational state.
Simulation owns scenario results and recommendations.
```

---

## 18. First implementation slice

Recommended first slice:

```text
SimulationModel
SimulationModelVersion
SimulationScenario
SimulationScenarioAssumption
SimulationInputSnapshot
SimulationConstraint
SimulationObjective
SimulationRun
SimulationResultSummary
SimulationResultValue
SimulationConstraintEvaluation
SimulationOptimizationCandidate
SimulationCandidateChange
SimulationRecommendation
SimulationCatalogEntry
SimulationCatalogTranslation
```

Defer later:

```text
advanced solver traces
large result series storage
sensitivity analysis
multi-candidate ranking dashboard
full digital twin runtime
real-time closed-loop optimization
```

---

## 19. Final ownership statement

```text
Simulation is the sandbox for operational alternatives.
Topology is the authority for the network.
Planning is the authority for expected operation.
Telemetry is the authority for measured facts.
Monitoring is the authority for deviations.
Workflow is the authority for approval flow.
Audit is the authority for evidence.
```

The most important implementation rule is:

```text
An optimal network configuration is born in Simulation,
but becomes official only in Topology.
```
