# Hidra V4 Simulation and Optimization Scope

```text
Document code : HIDRA-V4-SIMULATION-OPTIMIZATION-SCOPE
Task          : HIDRA-V4-SCOPE — docs(vision): define simulation-aware hydrocarbon optimization scope
Repository    : HidraAPI
Namespace     : dz.sh.hidra
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Document type : Product vision and scope definition
Author        : Abir MEDJERAB
CreatedOn     : 2025-06-26
UpdatedOn     : 2026-06-08
Status        : Draft for discussion
```

---

## 1. Purpose

This document defines the **Hidra V4** product scope for simulation-aware hydrocarbon optimization.

Hidra V4 extends the platform from trusted operational data, topology, telemetry, workflow, planning, monitoring, integrity, and analytics toward:

```text
network simulation-aware hydrocarbon optimization
```

The scope is aligned with the current HidraAPI repository structure:

```text
src/main/java/dz/sh/hidra
src/main/java/dz/sh/hidra/kernel
src/main/java/dz/sh/hidra/platform
src/main/java/dz/sh/hidra/modules
```

The correct Java namespace is:

```text
dz.sh.hidra
```

Do not use older or externalized namespace forms such as:

```text
dz.sonatrach.hidra
dz.hidra
com.hidra
```

---

## 2. V4 Product Definition

### 2.1 V4 name

```text
Hidra V4 — Simulation-Aware Hydrocarbon Optimization
```

### 2.2 V4 objective

Hidra V4 must allow Sonatrach engineers, planners, integrity teams, and operations teams to define, run, audit, and consume simulation scenarios over hydrocarbon pipeline networks.

V4 introduces explicit support for:

```text
industrial simulator integration
scenario lifecycle management
asynchronous simulation execution
simulation result traceability
what-if analysis
look-ahead operational assessment
simulation-informed planning and risk decisions
```

Hidra remains:

```text
system of intelligence
system of coordination
system of governance
system of audit
```

External simulators remain:

```text
systems of calculation
systems of hydraulic / thermodynamic computation
systems of process and network simulation
```

Hidra V4 must not reimplement HYSYS, PIPESIM, OLGA, UniSim, Synergi, AVEVA, or other engineering calculation engines.

---

## 3. Design Principles

### 3.1 Simulator-agnostic core

The Hidra domain must not hardcode vendor-specific simulator concepts.

External simulators must be exposed through stable application ports and infrastructure adapters.

Examples of external engines:

```text
Aspen HYSYS / Aspen Hydraulics
SLB PIPESIM
SLB OLGA
Honeywell UniSim
AVEVA Process Simulation
DNV Synergi Gas
DNV Synergi Liquid
DNV Synergi Pipeline Simulator
```

The core model should speak in Hidra concepts:

```text
SimulationEngine
SimulationScenario
ScenarioInput
ScenarioRun
ScenarioResult
SimulationRecommendation
TopologyMapping
BoundaryCondition
OperatingConstraint
```

It should not speak directly in vendor-only concepts as first-class core aggregates.

### 3.2 Topology-driven simulation

Hidra topology is the primary source of network structure.

Simulation integration must map from Hidra topology assets to simulator-specific nodes, branches, pipes, facilities, and equipment through adapter-owned mapping rules.

Topology remains owned by:

```text
src/main/java/dz/sh/hidra/modules/topology
```

Simulation must consume topology references and topology projections, not take ownership of topology aggregates.

### 3.3 Scenario-first, not file-first

Simulation work must be modeled as business scenarios.

Files, decks, cases, worksheets, COM endpoints, and proprietary formats are adapter implementation details.

Correct product model:

```text
SimulationScenario -> ScenarioRun -> ScenarioResult
```

Incorrect product model:

```text
UploadedFile -> VendorCaseFile -> RawOutputOnly
```

Files may exist, but they must support scenarios; they must not become the primary business abstraction.

### 3.4 Explainable and audited runs

Every simulation run must be traceable.

The audit trail must answer:

```text
Who created the scenario?
Why was it created?
Which topology scope was used?
Which telemetry or operating state initialized it?
Which simulator engine and version ran it?
Which assumptions and constraints were used?
When did the run start and finish?
What was the run status?
Which results were produced?
Which decision or workflow consumed the result?
```

### 3.5 Separation of concerns

External simulators do:

```text
hydraulic calculations
thermodynamic calculations
transient calculations
flow assurance calculations
surge / slugging / leak calculations
```

Hidra does:

```text
scenario orchestration
audit and governance
topology and operating-state binding
result normalization
decision support
workflow integration
planning / monitoring / integrity consumption
```

### 3.6 Asynchronous execution

Simulation runs may be long-running.

Hidra must treat them as asynchronous jobs with:

```text
PENDING
QUEUED
RUNNING
CANCELLING
CANCELLED
FAILED
COMPLETED
EXPIRED
```

V4 must support:

```text
progress tracking
retry policy
cancellation
run timeout
engine availability checks
license/resource constraints
safe failure handling
```

### 3.7 Safety and governance

Simulation outputs must not directly operate the real pipeline.

Simulation-driven recommendations are proposals only.

Operational changes must go through:

```text
workflow
authorization
audit
human approval
operational procedures
```

---

## 4. Existing HidraAPI Baseline

V4 must build on the current modular monolith baseline.

Existing implemented / planned foundation modules:

```text
kernel
platform
identity
organization
topology
telemetry
workflow
planning
monitoring
incidents
integration
analytics
notification
```

Correct source paths:

```text
src/main/java/dz/sh/hidra/kernel
src/main/java/dz/sh/hidra/platform
src/main/java/dz/sh/hidra/modules/identity
src/main/java/dz/sh/hidra/modules/organization
src/main/java/dz/sh/hidra/modules/topology
src/main/java/dz/sh/hidra/modules/telemetry
src/main/java/dz/sh/hidra/modules/workflow
src/main/java/dz/sh/hidra/modules/planning
src/main/java/dz/sh/hidra/modules/monitoring
src/main/java/dz/sh/hidra/modules/incidents
src/main/java/dz/sh/hidra/modules/integration
src/main/java/dz/sh/hidra/modules/analytics
src/main/java/dz/sh/hidra/modules/notification
```

Correct test paths:

```text
src/test/java/dz/sh/hidra/kernel
src/test/java/dz/sh/hidra/platform
src/test/java/dz/sh/hidra/modules/<module-name>
```

Correct resources path:

```text
src/main/resources
src/main/resources/db/migration
```

---

## 5. V4 Bounded Context Alignment

### 5.1 New conceptual context: Simulation

V4 introduces a conceptual **Simulation** bounded context.

Initial implementation should not create a broad cross-cutting dumping ground.

Preferred placement for the first implementation:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation
src/main/java/dz/sh/hidra/modules/integration/simulation
```

Rationale:

```text
analytics/simulation owns scenario result analysis, KPIs, comparison, optimization insight.
integration/simulation owns external simulator connectors, engine adapters, execution clients.
```

A future dedicated module may be created only if the simulation domain becomes large enough:

```text
src/main/java/dz/sh/hidra/modules/simulation
```

Do not create it prematurely unless roadmap approval explicitly says so.

### 5.2 Context ownership

| Context / Module | V4 responsibility | Owns | Does not own |
|---|---|---|---|
| `topology` | Network structure and topology references | pipeline systems, pipelines, facilities, nodes, segments, appurtenances, connections, equipment | simulator adapters, scenario execution |
| `telemetry` | Operating measurements and states | telemetry points, readings, quality, validated operating state projections | simulation engines |
| `workflow` | Approval and governance flow | approvals, decisions, escalations, authorization workflow | hydraulic calculations |
| `planning` | Planning consumption of scenario results | planning scenarios, targets, feasibility reviews | simulator execution internals |
| `monitoring` | Look-ahead and operational risk views | monitoring alerts, look-ahead risk projections | external engine connectors |
| `incidents` | Troubleshooting and post-incident analysis | incident-linked scenarios, evidence, investigation links | simulator-specific APIs |
| `integration` | External systems and simulator adapters | engine registry, connector config, execution adapters, mapping adapters | business scenario ownership |
| `analytics` | Result analysis and optimization insight | scenario results, KPIs, comparisons, optimization recommendations | vendor-specific runtime clients |
| `identity` | Actor identity and permissions | users, roles, permissions | simulation scenario data |
| `organization` | Responsibility and ownership structure | employees, organization units, assignments | simulator engines |
| `platform` | Technical infrastructure | async execution, observability, exception handling, persistence support | business simulation concepts |
| `kernel` | Generic primitives only | IDs, DDD markers, pagination, application contracts | simulation domain models |

---

## 6. Proposed Package Structure

### 6.1 Analytics simulation packages

Initial analytics-side packages:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/model
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/value
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/service
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/command
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/query
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/dto
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/out
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/service
src/main/java/dz/sh/hidra/modules/analytics/simulation/api/rest/controller
src/main/java/dz/sh/hidra/modules/analytics/simulation/api/rest/request
src/main/java/dz/sh/hidra/modules/analytics/simulation/api/rest/response
src/main/java/dz/sh/hidra/modules/analytics/simulation/api/rest/mapper
src/main/java/dz/sh/hidra/modules/analytics/simulation/infrastructure/persistence/entity
src/main/java/dz/sh/hidra/modules/analytics/simulation/infrastructure/persistence/mapper
src/main/java/dz/sh/hidra/modules/analytics/simulation/infrastructure/persistence/repository
```

Analytics-side simulation owns:

```text
SimulationScenario
ScenarioInput
ScenarioRun
ScenarioResult
SimulationKpi
SimulationRecommendation
ScenarioComparison
ModelValidationReport
```

### 6.2 Integration simulation packages

Initial integration-side packages:

```text
src/main/java/dz/sh/hidra/modules/integration/simulation/domain/model
src/main/java/dz/sh/hidra/modules/integration/simulation/domain/value
src/main/java/dz/sh/hidra/modules/integration/simulation/application/command
src/main/java/dz/sh/hidra/modules/integration/simulation/application/query
src/main/java/dz/sh/hidra/modules/integration/simulation/application/dto
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/in
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/out
src/main/java/dz/sh/hidra/modules/integration/simulation/application/service
src/main/java/dz/sh/hidra/modules/integration/simulation/api/rest/controller
src/main/java/dz/sh/hidra/modules/integration/simulation/api/rest/request
src/main/java/dz/sh/hidra/modules/integration/simulation/api/rest/response
src/main/java/dz/sh/hidra/modules/integration/simulation/api/rest/mapper
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/adapter/hysys
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/adapter/pipesim
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/adapter/olga
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/adapter/unisim
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/adapter/aveva
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/adapter/synergi
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/persistence/entity
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/persistence/mapper
src/main/java/dz/sh/hidra/modules/integration/simulation/infrastructure/persistence/repository
```

Integration-side simulation owns:

```text
SimulationEngineRegistration
SimulationEngineCapability
EngineConnectionProfile
EngineExecutionPolicy
TopologyToEngineMapping
SimulationEnginePort
SimulatorExecutionClient
SimulatorResultReader
```

### 6.3 Package rules

Do not create:

```text
src/main/java/dz/sh/hidra/simulation
src/main/java/dz/sh/hidra/common/simulation
src/main/java/dz/sh/hidra/shared/simulation
src/main/java/dz/sh/hidra/modules/shared
src/main/java/dz/sh/hidra/modules/common
src/main/java/dz/sh/hidra/modules/helper
src/main/java/dz/sh/hidra/modules/helpers
src/main/java/dz/sh/hidra/modules/misc
```

Do not place simulator adapters in:

```text
src/main/java/dz/sh/hidra/modules/topology
src/main/java/dz/sh/hidra/modules/telemetry
src/main/java/dz/sh/hidra/platform
src/main/java/dz/sh/hidra/kernel
```

---

## 7. Core Domain Concepts

### 7.1 SimulationEngine

Represents a configured external calculation engine.

Examples:

```text
HYSYS
PIPESIM
OLGA
UNISIM
AVEVA_PROCESS_SIMULATION
SYNERGI_GAS
SYNERGI_LIQUID
SYNERGI_PIPELINE_SIMULATOR
```

Recommended package:

```text
src/main/java/dz/sh/hidra/modules/integration/simulation/domain/model/SimulationEngineRegistration.java
```

Responsibilities:

```text
engine identity
vendor
engine family
steady-state / dynamic capability
connection profile reference
license / host / runtime constraints
availability status
```

### 7.2 SimulationScenario

Represents the business scenario to evaluate.

Recommended package:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/model/SimulationScenario.java
```

Responsibilities:

```text
scenario objective
scenario scope
topology subset reference
time horizon
assumptions
boundary conditions
constraints
requested engine capability
owner / requester reference
audit metadata
```

### 7.3 ScenarioInput

Represents normalized inputs used to prepare a simulation run.

Recommended package:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/model/ScenarioInput.java
```

Contains references to:

```text
topology assets
telemetry operating state
fluid description
boundary conditions
equipment constraints
operating rules
```

### 7.4 ScenarioRun

Represents one execution attempt for a scenario.

Recommended package:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/model/ScenarioRun.java
```

Run status should be a lifecycle enum:

```text
ScenarioRunStatus
```

Allowed values:

```text
PENDING
QUEUED
RUNNING
CANCELLING
CANCELLED
FAILED
COMPLETED
EXPIRED
```

This is a lifecycle status enum and may remain a Java enum.

### 7.5 ScenarioResult

Represents structured simulation outputs linked back to Hidra topology.

Recommended package:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/model/ScenarioResult.java
```

Result data examples:

```text
pressure profile
flow profile
temperature profile
velocity profile
linepack
utilization
constraint violations
bottlenecks
surge indicators
slugging indicators
leak/integrity indicators
KPI summary
```

### 7.6 SimulationRecommendation

Represents a decision-support proposal derived from simulation results.

Recommended package:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/model/SimulationRecommendation.java
```

Rules:

```text
recommendations are not commands to operate the real network
recommendations must be routed through workflow for approval
recommendations must retain traceability to scenario, run, and result
```

---

## 8. Ports and Services

### 8.1 Inbound ports

Analytics simulation inbound ports:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/CreateSimulationScenarioUseCase.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/GetSimulationScenarioUseCase.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/ListSimulationScenariosUseCase.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/StartScenarioRunUseCase.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/CancelScenarioRunUseCase.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/GetScenarioRunStatusUseCase.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/GetScenarioResultUseCase.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/in/CompareScenarioResultsUseCase.java
```

Integration simulation inbound ports:

```text
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/in/RegisterSimulationEngineUseCase.java
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/in/GetSimulationEngineUseCase.java
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/in/ListSimulationEnginesUseCase.java
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/in/TestSimulationEngineConnectionUseCase.java
```

### 8.2 Outbound ports

Analytics simulation outbound ports:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/out/SimulationScenarioRepository.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/out/ScenarioRunRepository.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/out/ScenarioResultRepository.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/out/TopologyProjectionPort.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/out/TelemetryStateProjectionPort.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/port/out/WorkflowApprovalPort.java
```

Integration simulation outbound ports:

```text
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/out/SimulationEngineRegistryRepository.java
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/out/SimulationEnginePort.java
src/main/java/dz/sh/hidra/modules/integration/simulation/application/port/out/EngineConnectionSecretPort.java
```

### 8.3 Application and domain services

Analytics simulation services:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/service/SimulationScenarioApplicationService.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/application/service/ScenarioRunApplicationService.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/service/ScenarioPlannerService.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/service/SimulationResultAnalysisService.java
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/service/SimulationRecommendationPolicy.java
```

Integration simulation services:

```text
src/main/java/dz/sh/hidra/modules/integration/simulation/application/service/SimulationEngineRegistryApplicationService.java
src/main/java/dz/sh/hidra/modules/integration/simulation/application/service/SimulationOrchestratorService.java
src/main/java/dz/sh/hidra/modules/integration/simulation/domain/service/SimulationEngineCapabilityPolicy.java
src/main/java/dz/sh/hidra/modules/integration/simulation/domain/service/SimulationExecutionPolicy.java
```

---

## 9. API Scope

### 9.1 Engine registry API

Recommended base path:

```text
/api/v1/integration/simulation/engines
```

Example endpoints:

```text
POST   /api/v1/integration/simulation/engines
GET    /api/v1/integration/simulation/engines/{engineId}
GET    /api/v1/integration/simulation/engines
POST   /api/v1/integration/simulation/engines/{engineId}/connection-tests
```

Controller path:

```text
src/main/java/dz/sh/hidra/modules/integration/simulation/api/rest/controller/SimulationEngineController.java
```

### 9.2 Scenario API

Recommended base path:

```text
/api/v1/analytics/simulation/scenarios
```

Example endpoints:

```text
POST   /api/v1/analytics/simulation/scenarios
GET    /api/v1/analytics/simulation/scenarios/{scenarioId}
GET    /api/v1/analytics/simulation/scenarios
POST   /api/v1/analytics/simulation/scenarios/{scenarioId}/runs
GET    /api/v1/analytics/simulation/scenarios/{scenarioId}/runs/{runId}
POST   /api/v1/analytics/simulation/scenarios/{scenarioId}/runs/{runId}/cancellations
GET    /api/v1/analytics/simulation/scenarios/{scenarioId}/runs/{runId}/results
```

Controller path:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/api/rest/controller/SimulationScenarioController.java
```

### 9.3 OpenAPI rules

Follow HidraAPI OpenAPI policy:

```text
Domain does not import Swagger/OpenAPI.
REST request/response records carry @Schema.
Controllers carry @Tag, @Operation, @ApiResponses, and @Parameter.
```

Do not place OpenAPI annotations in:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain
src/main/java/dz/sh/hidra/modules/integration/simulation/domain
```

---

## 10. Minimal Viable V4 Features

### 10.1 Engine registry

Hidra must support registering external simulation engines.

Minimum data:

```text
engine id
engine code
engine nameAr / nameFr / nameEn
vendor
engine type
capabilities
connection profile reference
execution policy
status
createdAt
updatedAt
```

Engine type examples:

```text
STEADY_STATE_NETWORK
DYNAMIC_TRANSIENT
PROCESS_SIMULATION
FLOW_ASSURANCE
LEAK_DETECTION
TRAINING_SIMULATOR
```

Engine type is user-facing reference data if exposed to UI and should eventually be catalog-backed.

### 10.2 Scenario management

Hidra must support scenario creation and lifecycle management.

Scenario objectives:

```text
CAPACITY_CHECK
PRESSURE_ENVELOPE
DEBOTTLENECKING
LOOK_AHEAD_MONITORING
STARTUP_SHUTDOWN_STUDY
SURGE_STUDY
SLUGGING_STUDY
LEAK_STUDY
INCIDENT_REPLAY
PLANNING_FEASIBILITY
```

Scenario objective is user-facing reference data if exposed to UI and should eventually be catalog-backed.

### 10.3 Scenario execution

Hidra must trigger simulator execution asynchronously through integration adapters.

Execution responsibilities:

```text
prepare engine-specific input
submit job
track status
collect logs
collect raw outputs
normalize results
store structured result
emit domain/application events
```

### 10.4 Result visualization and consumption

Hidra must expose simulation results for:

```text
topology overlay
planning feasibility review
monitoring look-ahead risk
integrity investigation
incident analysis
analytics comparison
model validation
```

### 10.5 Audit and governance

Every scenario and run must keep audit metadata.

At minimum:

```text
createdBy
createdAt
purpose
decisionContextReference
engineReference
inputVersion
topologySnapshotReference
telemetrySnapshotReference
runStartedAt
runCompletedAt
runStatus
failureReason
resultVersion
```

---

## 11. Extended V4+ Features

V4+ may add:

```text
look-ahead monitoring runs
integrity and leak transient studies
training and incident replay
co-simulation across power, gas, liquids, and multi-energy networks
model validation against historical telemetry
optimization recommendations based on scenario comparison
automated scenario scheduling
```

These features must remain governed by workflow and human authorization.

---

## 12. Out of Scope for V4

Hidra V4 must not include:

```text
building a new hydraulic / thermodynamic physics engine
replacing HYSYS, PIPESIM, OLGA, UniSim, AVEVA, or Synergi
embedding full simulator desktop UI inside Hidra
direct automated control actions on real pipelines based only on simulation outputs
bypassing operations procedures or authorization workflows
owning simulator licensing or installation
storing simulator secrets directly in domain models
vendor-specific APIs in domain packages
```

---

## 13. Dependency Rules

### 13.1 Allowed dependencies

Analytics simulation may depend on:

```text
kernel primitives
analytics module contracts
topology projections / references
telemetry projections / references
workflow approval ports
integration simulation ports
```

Integration simulation may depend on:

```text
kernel primitives
integration module contracts
platform technical configuration where appropriate
external simulator client libraries in infrastructure adapters only
```

### 13.2 Forbidden dependencies

Forbidden:

```text
analytics simulation domain -> integration simulation infrastructure
analytics simulation domain -> topology domain model aggregates
analytics simulation domain -> telemetry domain model aggregates
integration simulation domain -> vendor SDKs / COM APIs
integration simulation domain -> analytics scenario aggregates
external simulator adapters -> topology aggregate mutation
```

Correct pattern:

```text
analytics simulation application service
  -> SimulationEnginePort
  -> integration simulation adapter
  -> external simulator
```

Incorrect pattern:

```text
topology domain model
  -> HysysClient
  -> external simulator
```

---

## 14. Persistence Scope

Suggested migration area:

```text
src/main/resources/db/migration
```

Suggested tables:

```text
hidra_sim_engine
hidra_sim_engine_capability
hidra_sim_engine_connection_profile
hidra_sim_topology_mapping
hidra_sim_scenario
hidra_sim_scenario_input
hidra_sim_scenario_run
hidra_sim_scenario_result
hidra_sim_result_point
hidra_sim_recommendation
```

Persistence rules:

```text
store normalized scenario and result metadata in Hidra
store raw simulator files only as external artifact references or controlled blobs when required
never store secrets in domain tables
link results back to topology asset IDs and snapshot references
keep audit timestamps and actor references
```

---

## 15. Events

Recommended event names:

```text
SimulationEngineRegisteredEvent
SimulationScenarioCreatedEvent
ScenarioRunRequestedEvent
ScenarioRunStartedEvent
ScenarioRunCompletedEvent
ScenarioRunFailedEvent
ScenarioRunCancelledEvent
ScenarioResultStoredEvent
SimulationRecommendationCreatedEvent
SimulationRecommendationSubmittedForApprovalEvent
```

Event package examples:

```text
src/main/java/dz/sh/hidra/modules/analytics/simulation/domain/event
src/main/java/dz/sh/hidra/modules/integration/simulation/domain/event
```

Event rules:

```text
domain events carry IDs and references, not full external simulator payloads
large raw result payloads must be referenced, not embedded in events
workflow consumes recommendation events for approval
monitoring consumes look-ahead result events for warnings
```

---

## 16. Security and Authorization

V4 must define permissions such as:

```text
SIM_ENGINE_READ
SIM_ENGINE_REGISTER
SIM_ENGINE_TEST_CONNECTION
SIM_SCENARIO_CREATE
SIM_SCENARIO_READ
SIM_SCENARIO_UPDATE
SIM_SCENARIO_DELETE
SIM_RUN_START
SIM_RUN_CANCEL
SIM_RESULT_READ
SIM_RECOMMENDATION_CREATE
SIM_RECOMMENDATION_SUBMIT
SIM_RECOMMENDATION_APPROVE
```

Permissions belong to:

```text
src/main/java/dz/sh/hidra/modules/identity
```

Simulation modules must reference actors and permissions through identity/application contracts and security context, not by importing identity domain aggregates.

---

## 17. Trilingual and Catalog Rules

User-facing simulation labels must be trilingual:

```text
nameAr
nameFr
nameEn
descriptionAr
descriptionFr
descriptionEn
```

Catalog-backed candidates:

```text
engine type
engine capability
scenario objective
scenario type
simulation method
boundary condition type
constraint type
result metric type
recommendation type
failure category
```

Lifecycle statuses may remain enums:

```text
SimulationEngineStatus
SimulationScenarioStatus
ScenarioRunStatus
RecommendationStatus
```

Do not model user-facing reference data as Java enums if it must be displayed, translated, extended, or managed by administrators.

---

## 18. Implementation Order Recommendation

V4 should be implemented after the foundation modules stabilize.

Recommended order:

```text
1. integration simulation engine registry
2. analytics simulation scenario skeleton
3. topology-to-engine mapping model
4. scenario input model
5. asynchronous scenario run lifecycle
6. first simulator adapter proof of concept
7. normalized result storage
8. result-to-topology overlay API
9. workflow approval for recommendations
10. monitoring/planning/integrity consumption
```

The first adapter should be selected by Sonatrach stakeholders based on available licenses and integration options.

Do not implement all vendors at once.

---

## 19. Validation Checklist

Before starting implementation, confirm:

```text
[ ] official simulator tools in use at Sonatrach
[ ] allowed integration methods for each simulator
[ ] license and runtime constraints
[ ] first high-value scenario type
[ ] owner roles: operations, integrity, planning, flow assurance, IT
[ ] security model for scenario creation and run execution
[ ] audit and safety requirements
[ ] data retention policy for raw simulator outputs
[ ] topology snapshot strategy
[ ] telemetry snapshot strategy
```

---

## 20. Open Questions

```text
1. Which simulators are officially approved for Hidra integration first?
2. Is the first priority steady-state capacity analysis or dynamic transient analysis?
3. Which integration style is permitted: COM, file-based decks, REST, Python wrappers, or vendor SDKs?
4. Where will simulation runs execute: engineer workstation, simulation server, containerized worker, or vendor platform?
5. Which topology subset should be modeled first: one pipeline, one pipeline system, one region, or a full transportation network?
6. How should simulation results be validated against telemetry?
7. Which recommendations require workflow approval before operational use?
8. Which raw outputs must be retained for audit and for how long?
```

---

## 21. Final Scope Statement

Hidra V4 adds simulation-aware optimization to Hidra without turning Hidra into a simulator.

The correct architectural stance is:

```text
Hidra orchestrates, governs, audits, compares, and explains simulation work.
External simulators calculate hydraulic, thermodynamic, and transient behavior.
```

The implementation must remain aligned with:

```text
dz.sh.hidra namespace
HidraAPI modular monolith
DDD and hexagonal architecture
API-boundary OpenAPI policy
trilingual business labels
catalog-backed user-facing classifications
strict module ownership
```
