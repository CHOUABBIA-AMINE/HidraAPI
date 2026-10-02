# Hidra Unified Vision - All Phases and Architecture

```text
Product    : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Repository : HidraAPI
Namespace  : dz.sh.hidra
Document   : Unified product vision, release vision, and architecture synthesis
Sources    : Product vision, V1, V1.1, V1.2, V2, V3, macro architecture, micro architecture
Author     : Abir MEDJERAB
Prepared   : 2026-06-09
Status     : Consolidated vision baseline for review
```

---

## 1. Correction and Scope of This Consolidated Document

This document corrects the earlier merge. The earlier version merged the product vision and architecture files, but it did not explicitly include all release-phase vision documents from the repository vision folder.

This consolidated version inspects and merges the full available vision set:

| Source | Repository path | Role in this unified document |
|---|---|---|
| Product vision | `docs/vision/hidra-product-vision.md` | Strategic identity, conducting wire, product principles, pillars, first implementation order |
| V1 scope | `docs/vision/hidra-v1-scope.md` | Trusted operational data foundation, V1 modules, data ownership, non-goals, acceptance criteria |
| V1.1 scope | `docs/vision/hidra-v1.1-scope.md` | Operations horizon: planning, monitoring, incidents, audit hardening, basic dashboards, risk signals |
| V1.2 scope | `docs/vision/Hidra-v1.2-scope.md` | Internal network simulator / Simulation Core, scenario execution, hydraulic optimization, simulation governance |
| V2 scope | `docs/vision/hidra-v2-scope.md` | Integration horizon: external systems, SCADA/historian readiness, IAM, notifications, file import/export |
| V3 scope | `docs/vision/hidra-v3-scope.md` | Intelligence horizon: analytics, reporting, KPIs, trends, forecasting readiness, risk analytics, digital twin readiness |
| Macro architecture | `docs/architecture/hidra-macro-architecture.md` | System-level architecture, bounded contexts, dependency rules, data/security/integration/observability/deployment |
| Micro architecture | `docs/architecture/hidra-micro-architecture.md` | Module internals, layer rules, package structure, DDD building blocks, testing and validation standards |
| V1.2 macro architecture | `docs/architecture/hidra-macro-architecture-v1.2.md` | V1.2 architecture refinement and simulation-aware architecture direction |
| V1.2 micro architecture | `docs/architecture/hidra-micro-architecture-v1.2.md` | V1.2 package normalization, module structure, and simulation-era micro-architecture rules |

Two important normalizations are applied throughout this document:

```text
dz.sonatrach.hidra  -> dz.sh.hidra
sharedkernel        -> kernel
identityaccess      -> identity
```

The reason is simple: the current HidraAPI implementation standard uses `dz.sh.hidra`, the foundation module is `kernel`, and the identity module is `identity`. Older conceptual text is interpreted through this current canonical naming.

---

## 2. One Unified Vision Statement

Hidra is a hydrocarbon intelligence platform for Sonatrach pipeline operations.

Hidra means:

```text
Hydrocarbon Intelligence for Data, Risk, and Analytics
```

The platform exists to transform operational data into trusted, validated, auditable, risk-aware, explainable, and actionable intelligence.

Hidra is not just a CRUD application, telemetry database, dashboard, SCADA replacement, historian replacement, Java backend, or reporting module. It is a staged enterprise platform that begins with trusted operational data, then expands into operations, simulation, integration, analytics, reporting, and future digital twin readiness.

The strategic conducting wire is:

```text
Hidra transforms hydrocarbon operational data into trusted, validated, auditable,
risk-aware intelligence for Sonatrach.
```

Every module, API, database table, workflow, integration adapter, dashboard, report, and analytics capability must support this sentence.

---

## 3. Strategic Product Doctrine

### 3.1 Trust Before Intelligence

Hidra must not jump directly to AI, dashboards, forecasting, optimization, or digital twin features. The repository vision is clear: analytics and intelligence are useful only after topology, telemetry, validation workflow, and audit readiness become reliable.

The correct product sequence is:

```text
trusted data
validated data
auditable decisions
operational monitoring
risk awareness
simulation and optimization where justified
integration with external systems
analytics and intelligence
```

### 3.2 Modular Monolith Before Microservices

The architecture is intentionally modular monolith first. This is not a lack of ambition; it is a correctness strategy.

Reasons:

```text
business boundaries are still being refined
operational workflows cross several contexts
validation and audit need strong consistency
deployment must remain simple during early phases
team velocity is higher with one deployable unit
modularity can be enforced with package rules and tests
```

Potential future extraction is allowed only after real evidence of load, lifecycle independence, ownership separation, or operational need. Possible extraction candidates include telemetry ingestion, notification delivery, analytics processing, reporting projections, and specialized integration connectors.

### 3.3 Domain Ownership Is Non-Negotiable

Each module owns its own business truth. Other modules may reference it through identifiers, snapshots, application ports, or integration contracts, but must not import or mutate foreign internals.

Examples:

```text
Telemetry owns readings, measurement values, quality, and reading state.
Workflow owns validation, approval, task, decision, delegation, escalation, and process traceability.
Topology owns physical and operational network structure.
Planning owns expected operational targets.
Monitoring owns interpretation of trusted actuals against plans, thresholds, and rules.
Incidents own operational problem lifecycle.
Audit owns durable evidence, not business decisions.
Analytics owns derived insight, not source-of-truth state.
Integration owns external connection mechanics, not business state.
```

### 3.4 Auditability by Design

Every important operational decision must answer:

```text
who acted
when they acted
what object was affected
what value or state changed
why the change happened
which workflow or business state was involved
which request/correlation context existed
whether the evidence can be proven later
```

V1 introduces audit readiness. V1.1 hardens audit into durable audit evidence.

### 3.5 Simulation Is Powerful, But Must Remain Governed

The V1.2 vision adds an internal network simulator and Hidra Simulation Core. This is a major evolution: Hidra becomes not only a system of intelligence and coordination, but also a system of calculation for pipeline scenarios.

However, simulation must not bypass validation, workflow, audit, or human responsibility. Simulation results are decision support, not automatic industrial control.

---

## 4. Versioned Product Vision Roadmap

The merged roadmap is:

```text
V1    - Trusted Operational Data Foundation
V1.1  - Operations
V1.2  - Internal Network Simulator and Simulation-Driven Optimization
V2    - Integration
V3    - Intelligence
```

This order reconciles the source documents. The product vision originally grouped post-V1 into V1.1, V2, and V3. The later repository files introduce V1.2 as a dedicated simulation phase. Therefore this unified document treats V1.2 as an inserted bridge between operations and integration/intelligence.

### Version Dependency Summary

| Version | Name | Starts after | Main outcome |
|---|---|---|---|
| V1 | Trusted Operational Data Foundation | project foundation | trusted topology, telemetry, workflow validation, audit-ready decisions |
| V1.1 | Operations | accepted V1 | planning, monitoring, incidents, audit hardening, risk signals, basic operational visibility |
| V1.2 | Internal Network Simulator | stable V1.1 operations baseline | Hidra-owned simulation scenarios, runs, results, and optimization support |
| V2 | Integration | accepted operations baseline; if V1.2 is adopted, accepted simulation baseline too | controlled external system integration without bypassing domain ownership |
| V3 | Intelligence | accepted V2 | explainable analytics, reporting, KPIs, trends, risk analytics, forecasting readiness, digital twin readiness |

---

## 5. Hidra V1 - Trusted Operational Data Foundation

### 5.1 V1 Objective

V1 establishes the trusted operational data foundation. It answers the first essential questions:

```text
Who is the actor?
Which organization unit is responsible?
What pipeline infrastructure exists?
Where is the measurement taken?
Which telemetry source, device, and point produced the reading?
What was the reading value?
What is its quality and state?
Who validated, rejected, corrected, delegated, or escalated it?
Why was the decision made?
Can the decision be proven later?
```

At the end of V1, Hidra should prove:

```text
This reading belongs to this telemetry point.
This telemetry point is bound to this topology asset.
This reading came from this source/device.
This reading has this value, timestamp, quality, and state.
This actor or organization unit reviewed it.
This workflow decision was taken.
This reason/comment was recorded.
The system is audit-ready for the decision trail.
```

### 5.2 V1 Modules

V1 includes:

```text
kernel
platform
identity
organization
topology
telemetry
workflow
audit readiness
```

### 5.3 V1 Module Responsibilities

#### kernel

Provides stable cross-context primitives:

```text
identifiers
base value objects
pagination primitives
command/query markers
entity/aggregate markers
correlation/request references
```

It must not become a utility dumping ground.

#### platform

Provides technical foundation:

```text
configuration
exception handling
observability
security plumbing
persistence support
API conventions
testing conventions
```

It must not contain business rules.

#### identity

Owns the access model:

```text
users
roles
permissions
authorities
groups
access readiness
actor identity references
```

V1 excludes advanced federation, full IAM governance, and complex ABAC policy engines.

#### organization

Owns the human and responsibility structure:

```text
employees
organization units
departments/regions
positions
assignments
responsibility structure
actor-to-organization context
```

V1 excludes HR replacement, payroll, performance management, and complex HR workflows.

#### topology

Owns physical and operational infrastructure:

```text
pipeline systems
pipelines
facilities
nodes
segments
equipment
connections
appurtenances
catalog-backed topology types
multilingual topology labels
```

V1 topology answers: what infrastructure exists, where readings attach, and which asset context telemetry refers to.

#### telemetry

Owns operational measurement facts:

```text
telemetry sources
telemetry devices
telemetry points/tags
point-to-topology binding
readings
ingestion batches
reading value/state/quality
catalog-backed telemetry types
multilingual telemetry labels
```

Telemetry records what happened. Monitoring later interprets whether it is normal or risky.

#### workflow

Owns validation and approval process machinery:

```text
workflow definitions
workflow instances
workflow tasks
approval/rejection/correction request
delegation
escalation
workflow actions
workflow target reference
actor snapshot
organization snapshot
reason/comment
workflow timeline
WorkflowAuditEventPort
```

V1 workflow target:

```text
TELEMETRY_READING
```

Workflow must not own telemetry state or corrected reading values.

#### audit readiness

V1 defines audit-ready event payloads and ports but does not yet implement full audit hardening.

V1 audit readiness includes:

```text
WorkflowAuditEventPort
actor/action/target/reason/timestamp/correlation fields
traceable workflow decisions
audit-ready data structures
```

### 5.4 V1 Data Ownership

| Data | Owner in V1 |
|---|---|
| User, role, permission, authority | identity |
| Employee, unit, assignment | organization |
| Pipeline, facility, equipment, topology asset | topology |
| Source, device, point, reading, ingestion batch | telemetry |
| Validation/approval task, decision reason, workflow action | workflow |
| Audit-ready event port and payload | workflow/application boundary until audit hardening |
| Validated reading state | telemetry, triggered by workflow decision result |

Critical V1 rule:

```text
Workflow must not directly own or mutate telemetry readings.
Telemetry owns reading facts and state.
Workflow owns decision process.
```

### 5.5 V1 Acceptance Criteria

V1 is accepted when:

```text
kernel/platform baseline is stable
identity compiles and tests pass
organization compiles and tests pass
topology compiles and tests pass
telemetry compiles and tests pass
workflow compiles and tests pass
telemetry readings can be referenced by workflow
workflow can create validation tasks for telemetry readings
workflow can approve, reject, request correction, delegate, and escalate
workflow records actor snapshot, organization snapshot, reason, comment, and timestamp
telemetry remains owner of reading state/value
audit-ready event port exists
full tests pass
no forbidden module imports exist
business taxonomies are catalog-backed
user-facing labels are multilingual
```

### 5.6 V1 Explicit Non-Goals

V1 must not attempt:

```text
SCADA real-time control
automatic valve or pump control
full historian replacement
advanced AI decision automation
full digital twin simulation
microservices decomposition
enterprise data lake replacement
complex optimization engines
full production analytics platform
full notification delivery platform
full audit hardening
full incident management
full monitoring/risk engine
```

V1 proves trusted operational data first.

---

## 6. Hidra V1.1 - Operations

### 6.1 V1.1 Objective

V1.1 turns workflow-validated telemetry into daily operational supervision.

It answers:

```text
What was planned?
What actually happened?
Is the actual value within acceptable limits?
Is there a deviation?
Is the deviation operationally important?
Should the deviation become an alert?
Should the alert become an incident?
Who acknowledged it?
Who is responsible for follow-up?
Can the operational decision be audited?
```

### 6.2 Preconditions

Do not start V1.1 before V1 is accepted.

V1.1 requires:

```text
topology baseline passes
telemetry baseline passes
workflow baseline passes
telemetry reading can be workflow-validated
workflow decisions are audit-ready
full test suite passes
```

### 6.3 Included Modules and Capabilities

V1.1 includes:

```text
planning
monitoring
incidents
audit hardening
basic dashboards
risk signals
```

Recommended order:

```text
1. planning
2. monitoring
3. incidents
4. audit hardening
5. basic dashboards / operational read views
```

### 6.4 Planning Scope

Planning defines expected hydrocarbon operational targets.

Planning owns:

```text
FlowPlan
PlanningPeriod
OperationalTarget
PlanVersion
PlanApproval
PlanStatus
PlanTargetReference
```

Planning references topology assets, validated telemetry actuals, workflow approval references, actor snapshots, and organization unit snapshots.

Planning does not own telemetry readings, topology assets, workflow tasks, monitoring alerts, incident lifecycle, or analytics projections.

Acceptance criteria:

```text
plans can be created and versioned
plans can target topology assets
plans can define expected values and units
approved plans are immutable
revision creates a new version
actual comparison uses validated/approved telemetry only
```

### 6.5 Monitoring Scope

Monitoring evaluates operational state using validated telemetry, planning targets, thresholds, and monitoring rules.

Monitoring owns:

```text
MonitoringRule
Threshold
OperationalState
MonitoringEvaluation
AlertCandidate
AlertRule
AlertAcknowledgement
RiskSignal
```

Monitoring references validated telemetry readings, planning targets, topology assets, actor snapshots, and organization unit snapshots.

Acceptance criteria:

```text
rules can be configured
thresholds include scope, unit, severity, and effective period
validated telemetry can be evaluated
planned-vs-actual deviations can be detected
alerts or alert candidates can be produced
acknowledgement tracks actor and time
```

### 6.6 Incidents Scope

Incidents manage operational problems from detection to resolution.

Incidents own:

```text
Incident
IncidentClassification
IncidentSeverity
IncidentStatus
IncidentTimelineEntry
ResponseAction
ImpactAssessment
RootCauseAnalysis
IncidentResolution
IncidentAttachmentReference
```

Incidents reference monitoring alerts, telemetry readings, topology assets, actor snapshots, organization unit snapshots, workflow references, and audit references.

Acceptance criteria:

```text
incident can be created from monitoring/alert context
incident status lifecycle is controlled
timeline is append-only
response actions are tracked
root cause can be recorded
closure requires resolution
actor and organization snapshots are preserved
```

### 6.7 Audit Hardening Scope

Audit hardening turns V1 audit readiness into durable audit evidence.

Audit hardening owns:

```text
AuditEvent
AuditActor
AuditTarget
AuditAction
AuditDecision
AuditBeforeAfterValue
AuditCorrelation
AuditSearchProjection
```

Acceptance criteria:

```text
audit events are append-only
workflow decisions are auditable
domain state changes can publish audit events
before/after values can be captured where applicable
sensitive values can be masked
audit events are searchable by actor, target, action, time, and correlation id
```

### 6.8 Basic Dashboards and Read Views

Allowed V1.1 read views:

```text
planning vs actual summary
current monitoring state
open alerts
open incidents
validation delay summary
incident closure status
```

Not allowed in V1.1:

```text
advanced analytics
forecasting
AI anomaly detection
digital twin dashboards
complex BI export platform
```

### 6.9 V1.1 Non-Goals

V1.1 must not attempt:

```text
SCADA production integration
historian replacement
OPC UA/MQTT production connectors
enterprise-wide integration hub
advanced analytics
AI forecasting
digital twin
full notification delivery platform
microservices extraction
```

### 6.10 V1.1 Version Gate

V1.1 is complete when:

```text
validated telemetry can be compared to plans
monitoring detects threshold and planned-vs-actual deviations
alerts or alert candidates can be created
incidents can be opened, tracked, and closed
audit evidence is hardened for workflow and operational state changes
basic operational read views exist
full tests pass
```

---

## 7. Hidra V1.2 - Internal Network Simulator and Simulation-Driven Optimization

### 7.1 Why V1.2 Exists

V1.2 extends Hidra from operational intelligence and integrity monitoring into native simulation-driven hydrocarbon optimization.

It introduces Hidra Simulation Core: an internal hydraulic and network simulation engine dedicated to Sonatrach pipeline operations.

With V1.2, Hidra becomes:

```text
system of intelligence
system of coordination
system of calculation
```

External commercial tools remain useful as benchmarks and validation references, but Hidra Simulation Core is the intended primary engine for production-grade internal calculations after the V1.2 scope is accepted.

### 7.2 V1.2 Design Principles

V1.2 is governed by these principles:

```text
Hidra-owned physics and algorithms
topology-driven simulation
scenario-first modeling
explainable and audited simulations
bounded complexity
non-blocking asynchronous runs
separation of simulation core from business consumers
validation against leading commercial tools
```

### 7.3 External Tools Are Benchmarks, Not Runtime Engines

V1.2 recognizes commercial tools such as Aspen HYSYS/Aspen Hydraulics, PIPESIM, OLGA, Honeywell UniSim, AVEVA/PIPEPHASE heritage, and DNV Synergi/Pipeline Simulator as validation and benchmark references.

V1.2 may import/export data to simplify comparison, but Hidra must not depend on these tools as runtime engines.

### 7.4 Simulation Bounded Context

V1.2 introduces a conceptual Simulation bounded context.

Purpose:

```text
own scenario lifecycle
own simulation run lifecycle
own solvers and physics models
map results to topology and time
expose clean APIs to planning, monitoring, integrity, incidents, analytics, and integration
```

Key domain concepts:

```text
SimulationScenario
ScenarioInput
ScenarioRun
ScenarioResult
SimulationModelVersion
Recommendation
```

The canonical package should be:

```text
dz.sh.hidra.modules.simulation
```

Older V1.2 text that references `dz.sonatrach.hidra.modules` is normalized to `dz.sh.hidra.modules`.

### 7.5 Simulation Interactions

Simulation depends on:

```text
topology for canonical network graph, equipment, elevation, and network structure
telemetry for current operating point snapshots and calibration
platform for persistence, jobs, observability, and security
kernel for stable primitives
```

Simulation is consumed by:

```text
planning for feasibility and capacity checks
monitoring for look-ahead operational risk
integrity for consequences of defects, failures, or leak scenarios
incidents for analysis and post-mortems
analytics for trends, solver validation, and optimization insights
integration for validation import/export with external tools
```

### 7.6 Minimal V1.2 Scope

V1.2 minimal viable Simulation Core includes:

```text
network model and steady-state solver
scenario management
asynchronous scenario execution
scenario result persistence
result visualization and comparison
simulation audit and governance
links between simulation runs, users, decisions, workflows, and topology
```

### 7.7 Extended Post-V1.2 Features

Post-V1.2 extensions may include:

```text
multiphase and thermal effects
time-varying or quasi-dynamic simulation
transient solver for critical segments
advanced optimization and debottlenecking proposals
```

### 7.8 V1.2 Non-Goals

V1.2 must not attempt:

```text
full-featured process simulator for complete processing plants
replication of every commercial simulator feature
training simulator replacement
automatic control actions on real pipelines
replacement of all engineering workflows in one step
black-box simulation recommendations without auditability
```

### 7.9 V1.2 Open Questions

The V1.2 source raises key stakeholder questions:

```text
Which network types and operating regimes are highest priority?
What accuracy and performance tolerances are acceptable?
Which commercial tools and scenarios should be benchmark references?
Should V1.2 stop at steady-state or include simple time-varying sequences?
How are responsibilities split between operations, flow assurance, process engineering, and integrity teams?
```

### 7.10 V1.2 Architectural Consequence

V1.2 adds a Simulation capability alongside analytics and integration. The modular monolith remains valid, but a new module appears:

```text
dz.sh.hidra.modules.simulation
```

Simulation internals must not leak into planning, monitoring, incidents, or analytics. Consumers call Simulation APIs; they do not depend on solver internals.

---

## 8. Hidra V2 - Integration

### 8.1 V2 Objective

V2 connects Hidra to industrial and enterprise systems without corrupting domain ownership.

V2 answers:

```text
Which external system produced this data?
Was the import successful?
Was the external reference preserved?
Can failed ingestion be retried?
Can data be mapped safely?
Can Hidra exchange data without bypassing validation?
Can enterprise IAM and notification channels be connected?
```

### 8.2 Preconditions

V2 starts after the accepted operations baseline.

Required baseline:

```text
trusted operational data
workflow validation
planning
monitoring
incidents
audit hardening
basic operational visibility
full tests pass
```

If the V1.2 Simulation Core is adopted before V2, V2 must also preserve simulation scenario references, run references, and benchmark/export traceability.

### 8.3 Included Capabilities

V2 includes:

```text
integration context
SCADA integration readiness
historian integration
OPC UA readiness
MQTT readiness
REST integration APIs
file import/export
batch ingestion jobs
enterprise IAM integration
notification channel integration
external references
mapping rules
retry/dead-letter handling
integration audit
```

### 8.4 Integration Context Ownership

Integration owns:

```text
ExternalSystem
ConnectorConfiguration
IngestionJob
IntegrationMapping
ExternalReference
RetryPolicy
DeadLetterRecord
IntegrationRun
IntegrationRunStatus
IntegrationError
```

Integration references telemetry source and ingestion batches, identity actors, organization units, audit events, and notification delivery references.

Integration does not own telemetry readings, workflow decisions, topology assets, monitoring rules, incidents, or analytics projections.

### 8.5 SCADA and Historian Scope

Allowed V2 SCADA scope:

```text
read-only data ingestion
external tag reference mapping
source/device/point mapping
timestamp and quality mapping
external system metadata
raw payload preservation
failed ingestion traceability
```

Not allowed:

```text
SCADA control commands
valve or pump actuation
replacement of industrial control systems
unvalidated direct write into trusted operational state
```

Historian integration may support tag mapping, batch backfill, external reference tracking, timestamp and quality preservation, and historian value import. It must not become a full historian replacement.

### 8.6 Enterprise IAM, Notification, and File Exchange

V2 may add:

```text
external identity provider references
role/group mapping
user synchronization readiness
email channel
SMS readiness
webhook/push readiness
delivery tracking
delivery failure audit
CSV/Excel import/export
validated import preview
mapping templates
import error traceability
```

Notification does not own workflow, monitoring, alert, or incident business rules. It delivers messages requested by those modules.

### 8.7 V2 Acceptance Criteria

V2 is accepted when:

```text
external systems can be registered
connector configurations can be managed
ingestion jobs can be started and tracked
external references are preserved
failed records are traceable
retries are controlled
dead-letter records exist
integration actions are auditable
telemetry ingestion still goes through telemetry rules
workflow validation is not bypassed
notification channels can be called by producer modules
```

### 8.8 V2 Non-Goals

V2 must not attempt:

```text
SCADA control
automatic industrial actuation
full historian replacement
enterprise data lake replacement
complex streaming platform unless justified by load
AI analytics
digital twin simulation
microservices decomposition by default
```

---

## 9. Hidra V3 - Intelligence

### 9.1 V3 Objective

V3 transforms trusted operational history into explainable intelligence.

It answers:

```text
What patterns exist in historical operation?
Which assets are repeatedly abnormal?
Where do deviations occur most often?
Which incidents repeat?
Which workflows are delayed?
Which operational risks are increasing?
What KPIs summarize operational health?
What can be forecast from trusted history?
What should supervisors focus on?
```

### 9.2 Preconditions

Do not start V3 before V2 is accepted.

V3 requires:

```text
trusted operational data
workflow validation
planning
monitoring
incidents
audit hardening
integration
external references preserved
full tests pass
```

If V1.2 Simulation Core is part of the accepted baseline, V3 also consumes simulation scenario history and result histories for analytics and digital twin readiness.

### 9.3 Included Capabilities

V3 includes:

```text
analytics
reporting
KPIs
trend analysis
forecasting readiness
anomaly detection readiness
root cause support
risk analytics
advanced dashboards
digital twin readiness
projection/read-model optimization
```

### 9.4 Analytics Scope

Analytics owns derived insight:

```text
AnalyticProjection
KpiDefinition
KpiSnapshot
TrendView
AnomalyCandidate
RiskInsight
OperationalInsight
ProjectionRefreshRun
```

Analytics references validated telemetry, workflow history, planning history, monitoring events, incidents, audit evidence, integration references, topology context, and simulation results if V1.2 is implemented.

Analytics must not modify source-of-truth operational state.

### 9.5 Reporting Scope

Reporting owns:

```text
ReportDefinition
ReportRun
ReportProjection
ReportExport
ReportSchedule
```

Reporting uses trusted read models, validated telemetry, planning results, monitoring evaluations, incident history, audit summaries, analytics projections, and simulation study outputs when applicable.

Reporting does not own business state, telemetry readings, workflow decisions, or incident lifecycle.

### 9.6 KPI and Trend Scope

V3 KPIs may include:

```text
validation delay
reading rejection rate
correction frequency
missing reading rate
planned-vs-actual deviation
threshold violation count
incident frequency
incident closure time
repeated asset deviation
workflow bottleneck
data freshness
audit completeness
simulation error or convergence rate if V1.2 exists
scenario recommendation acceptance rate if V1.2 exists
```

Trend analysis may include flow, pressure, quality, deviation, incident, validation performance, asset abnormality, and simulation-calibration trends.

### 9.7 Forecasting and Anomaly Readiness

Allowed:

```text
forecast input preparation
clean feature datasets
forecast baseline models
forecast confidence metadata
manual review of forecast outputs
anomaly candidates
explainable anomaly flags
threshold + statistical comparison
human review workflow for anomaly confirmation
```

Not allowed:

```text
automatic operational decisions
automatic control actions
black-box risk scoring without explanation
incident closure or escalation without human/process confirmation
```

### 9.8 Digital Twin Readiness

Digital twin readiness may include:

```text
trusted topology context
validated historical telemetry
asset state history
incident history
planning history
monitoring history
integration references
simulation input preparation
simulation scenario history
```

Full simulation engine is not V3 if V1.2 already owns it. If V1.2 is not implemented, V3 may only prepare digital twin inputs, not implement the solver.

### 9.9 V3 Acceptance Criteria

V3 is accepted when:

```text
analytics projections are based on trusted data
reports identify data freshness and validation status
KPIs are explainable
trends exclude or mark untrusted data
forecasting inputs are traceable
risk analytics can explain why a risk signal exists
digital twin readiness uses trusted history only
analytics does not modify source-of-truth state
full tests pass
```

---

## 10. Unified Macro Architecture

### 10.1 Architecture Style

Hidra uses:

```text
Domain-Driven Design
Hexagonal Architecture
Modular Monolith first
Event-aware internal architecture
API-first external integration
Clear separation of API, application, domain, infrastructure, platform, and kernel layers
```

### 10.2 Canonical Bounded Contexts

| Context | Responsibility | Owns | Does not own |
|---|---|---|---|
| kernel | stable primitives | IDs, pagination, shared abstractions | business aggregates |
| platform | technical foundation | configuration, exceptions, observability, security plumbing | business concepts |
| identity | access model | users, roles, permissions, authorities, groups | employee hierarchy |
| organization | responsibility structure | employees, units, assignments | login credentials |
| topology | physical network | pipelines, assets, equipment, topology versions | telemetry values |
| telemetry | measurement facts | readings, quality, state, ingestion batches | incident lifecycle |
| workflow | validation process | workflow instances, tasks, decisions, delegation, escalation | telemetry business state |
| planning | expected operational state | flow plans, targets, versions | raw telemetry ingestion |
| monitoring | operational interpretation | rules, thresholds, evaluations, alerts, risk signals | incident resolution |
| incidents | problem lifecycle | incident state, timeline, response, closure | raw alert rules |
| audit | durable evidence | audit events and traceability | business decisions |
| simulation | scenario calculation | scenarios, runs, solvers, results | planning/monitoring business rules |
| integration | external exchange | connectors, mappings, jobs, dead-letter records | domain ownership |
| analytics | insight | KPIs, projections, trends, risk insight | source-of-truth mutation |
| reporting | operational reports | report definitions/projections/exports | source data ownership |
| notification | delivery | channels, templates, delivery tracking | alert/workflow business rules |

Simulation is added by the V1.2 vision. It is not present in the earlier V1 implementation order, but it is now part of the consolidated forward architecture.

### 10.3 Dependency Rules

Allowed direction:

```text
api -> application -> domain
application -> port.in / port.out
infrastructure -> ports and domain mapping
modules -> kernel
platform -> kernel
```

Forbidden patterns:

```text
controllers directly access repositories
domain depends on Spring, JPA, or web frameworks
application depends on API layer types
modules share JPA entities
kernel becomes a utility dumping ground
workflow owns telemetry, planning, incident, or topology business rules
simulation internals leak into planning or monitoring
analytics modifies source-of-truth operational state
integration adapters bypass application/domain rules
```

### 10.4 Data Architecture

Hidra distinguishes:

```text
transactional data
telemetry/time-series data
audit data
outbox/event data
projection/read-model data
simulation scenario and result data
```

Transactional and audit data are the source of truth. Projections, dashboards, analytics views, and reports are derived.

Simulation data must be reproducible: every scenario run should record its scenario, inputs, topology version, fluid/model assumptions, solver version, actor, timestamp, logs, and output metadata.

### 10.5 Integration Architecture

Integration is port/adapter based and owned by the integration context.

Supported integration types:

```text
REST APIs
SCADA adapters
historian adapters
OPC UA readiness
MQTT readiness
CSV/Excel import/export
enterprise IAM integration
notification gateways
external analytics export
simulation benchmark import/export
```

Business modules must not talk directly to external systems. They call outbound ports implemented by adapters.

### 10.6 Security Architecture

Security supports:

```text
authentication through internal or external IAM
RBAC
ABAC readiness
organization-scoped permissions
role and permission catalog
authority model
group assignment
segregation of duties
auditability of security decisions
```

Authorization is policy-driven, not scattered across controllers.

### 10.7 Observability Architecture

Hidra must provide:

```text
structured logs
correlation IDs
request IDs
actor IDs
organization IDs where applicable
module names
operation names
metrics
health checks
readiness checks
ingestion monitoring
workflow transition monitoring
integration job monitoring
simulation run monitoring
audit visibility
```

Sensitive data must never be logged.

### 10.8 Deployment Architecture

Initial deployment targets:

```text
local development
test
staging
production
```

Deployment requirements:

```text
profile-based configuration
Flyway migrations
secrets management
health and readiness endpoints
backup and restore
logs and metrics export
container readiness
CI validation
```

---

## 11. Unified Micro Architecture

### 11.1 Canonical Package Root

The canonical Java package root is:

```text
dz.sh.hidra
```

Forbidden/discouraged roots:

```text
dz.sonatrach
sharedkernel
common
core
utils
helpers
misc
```

### 11.2 Standard Module Shape

Every business module follows:

```text
api
  rest
    controller
    request
    response
    mapper
application
  command
  query
  dto
  port
    in
    out
  service
domain
  model
  value
  event
  policy
  service
  exception
infrastructure
  configuration
  persistence
    entity
    repository
    mapper
    adapter
  adapter or external-module-adapter
```

For simulation, the structure is the same, with solver internals contained under infrastructure or dedicated simulation subpackages and exposed only through application/domain contracts.

### 11.3 Layer Rules

API layer:

```text
owns REST controllers, requests, responses, and API mappers
calls inbound ports
must not call repositories
must not contain business rules
```

Application layer:

```text
orchestrates use cases
uses commands and queries
uses domain services and outbound ports
returns application DTOs
must not import REST DTOs or JPA entities
```

Domain layer:

```text
owns business invariants
owns aggregates, entities, value objects, policies, and domain services
must not import Spring, JPA, REST, or foreign module internals
```

Infrastructure layer:

```text
implements outbound ports
contains JPA entities, repositories, persistence mappers, adapters, external integrations
may depend on application port interfaces and domain mapping
must not leak entities outside its module
```

### 11.4 Testing Standards

Expected test types:

```text
domain unit tests
application service tests with in-memory ports
persistence mapper and repository adapter tests
REST mapper/controller tests
architecture guardrail tests
boot smoke tests with Testcontainers when database wiring is involved
simulation numerical validation tests from V1.2 onward
```

### 11.5 Validation Standards

API validation:

```text
jakarta.validation annotations on REST request DTOs
clear OpenAPI schema annotations
no validation annotations on response DTOs unless justified
```

Domain validation:

```text
factory methods enforce invariants
invalid value objects throw domain exceptions
business rule violations throw business exceptions
catalog-backed taxonomies are preferred over business enums
```

Architecture validation:

```text
no Spring/JPA in domain
no API DTOs in application
no repositories in controllers
no cross-module domain imports
no JPA entity sharing
no analytics mutation of operational truth
no integration bypass of domain/application validation
```

---

## 12. Unified Operational Data Lifecycle

The complete Hidra lifecycle is:

```text
1. identity establishes actor identity and authorization context
2. organization establishes responsibility and operational ownership
3. topology establishes physical asset context
4. telemetry captures readings and measurement facts
5. workflow validates readings through auditable decisions
6. planning defines expected operational targets
7. monitoring compares trusted actuals to plans and thresholds
8. incidents manage detected operational problems
9. audit preserves durable evidence of decisions and state changes
10. simulation models scenarios, capacities, envelopes, and what-if decisions
11. integration exchanges data with selected external systems without bypassing validation
12. analytics derives KPIs, trends, forecasts, and risk insights from trusted history
13. reporting exports trusted operational views
14. notification delivers messages requested by business modules
```

This lifecycle must be ordered by trust, not by UI convenience.

---

## 13. Product Release Gates

### V1 Gate

```text
trusted topology, telemetry, workflow validation, and audit-ready decision trail exist
workflow can validate TELEMETRY_READING targets
all V1 modules compile and test
no forbidden imports
```

### V1.1 Gate

```text
plans exist and can be compared with validated telemetry
monitoring detects deviations
incidents can be opened, tracked, and closed
audit events are hardened and searchable
basic operational views exist
```

### V1.2 Gate

```text
simulation scenarios can be defined
steady-state solver can run selected Sonatrach network cases
results are persisted, traceable, and linked to topology
simulation runs are asynchronous and auditable
benchmark/cross-check process exists against selected commercial tools
simulation recommendations do not bypass workflow or human responsibility
```

### V2 Gate

```text
selected external systems are registered
connectors and mappings are controlled
external references are preserved
failed integration events are retryable and auditable
SCADA/historian integration remains read-only unless separately approved
workflow validation is not bypassed
```

### V3 Gate

```text
analytics projections use trusted data only
KPIs and trends are explainable
reports show freshness and validation status
forecasting inputs are traceable
risk analytics explain their signals
source-of-truth state is never mutated by analytics
```

---

## 14. Open Decisions Requiring Stakeholder Confirmation

The source documents expose several decisions that should be confirmed by Sonatrach stakeholders:

```text
Which pipeline network types are highest priority for Simulation Core?
What accuracy tolerance is required for V1.2 solver acceptance?
Which commercial tools and reference cases should benchmark Hidra Simulation Core?
Should V1.2 stop at steady-state or include time-varying sequences?
Which operational dashboards are mandatory for V1.1?
Which external systems are first for V2 integration?
Which IAM provider and notification channels matter first?
Which KPIs define V3 success?
Which data can be used for forecasting and anomaly readiness?
When, if ever, should modules be extracted from modular monolith to services?
```

---

## 15. Final Unified Position

Hidra should be understood as a staged hydrocarbon intelligence platform.

The product does not begin with dashboards, AI, or simulation. It begins with trusted operational truth. V1 builds the reliable foundation: identity, organization, topology, telemetry, workflow, and audit readiness. V1.1 turns that trusted foundation into daily operational management through planning, monitoring, incidents, audit hardening, dashboards, and risk signals. V1.2 adds Hidra-owned simulation and optimization capabilities, making Hidra a system of calculation for pipeline scenarios while preserving workflow, audit, and human governance. V2 connects Hidra to external industrial and enterprise systems without corrupting domain ownership. V3 turns trusted history into explainable intelligence, reporting, KPIs, trends, forecasting readiness, risk analytics, and digital twin readiness.

The architecture that supports this vision is a strict modular monolith, DDD, hexagonal architecture, catalog-backed business taxonomies, explicit bounded contexts, stable kernel primitives, infrastructure adapters, and strong validation rules.

The final strategic sentence remains:

```text
Hidra transforms hydrocarbon operational data into trusted, validated, auditable,
risk-aware intelligence for Sonatrach.
```
