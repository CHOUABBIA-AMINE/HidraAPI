# Hidra Master Vision

**Hydrocarbon Intelligence for Data, Risk, and Analytics**

| Field | Value |
|---|---|
| Product | Hidra |
| Meaning | Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Target repository | HidraAPI |
| Canonical namespace | `dz.sh.hidra` |
| Architecture style | Domain-Driven Design, Hexagonal Architecture, Modular Monolith First |
| Document type | Master Product Vision |
| Version | Final authored vision based on product, phase, and architecture documents |
| Status | Corrected and enhanced vision baseline |
| Owner | Sonatrach / TRC Digitalization Initiative |
| Authoring standard | Normalized HidraAPI terminology: `kernel`, `identity`, `dz.sh.hidra` |

---

## 1. Executive Vision

Hidra is Sonatrach TRC's hydrocarbon intelligence platform for pipeline operations. Its mission is to transform operational data into trusted, validated, auditable, risk-aware intelligence that can support daily operations, engineering studies, integration with industrial systems, and future explainable intelligence.

Hidra must not be understood as a database, a dashboard, or a simulator alone. It is a progressive operational intelligence platform with five maturity layers:

1. **Trusted operational truth** — reliable actors, organization, topology, telemetry, workflow, and audit-ready decisions.
2. **Operational supervision** — planning, monitoring, incidents, hardened audit, and basic operational read views.
3. **Simulation-driven calculation** — internal network simulation and what-if scenario capability driven by Hidra topology and telemetry.
4. **Industrial integration** — controlled, auditable connectivity with SCADA, historians, IAM, notification channels, files, and external systems.
5. **Explainable intelligence** — analytics, KPIs, trends, forecasting readiness, anomaly readiness, risk analytics, reporting, and digital twin readiness.

The final Hidra vision is therefore:

> Hidra creates a trusted digital operating layer for hydrocarbon pipeline operations by connecting topology, telemetry, workflow, planning, monitoring, incidents, simulation, integration, audit, reporting, and analytics into one coherent, auditable, risk-aware system.

---

## 2. Why the Vision Needed Correction

The source documents contain repeated capabilities across several phases. That repetition is not wrong; it means some capabilities mature over time. The corrected vision therefore does not simply copy each source section into one large document. It reclassifies repeated concepts into a maturity model.

For example:

| Capability | Incorrect interpretation | Correct vision interpretation |
|---|---|---|
| Audit | Appears in many versions, so it is duplicated | V1 provides audit readiness; V1.1 hardens audit; V2 audits integrations; V3 uses audit evidence in analytics |
| Dashboards | Appears before analytics, so it conflicts with V3 | V1.1 provides basic operational read views; V3 provides analytics dashboards and advanced reporting |
| Risk | Appears in V1.1 and V3 | V1.1 detects operational risk signals; V3 performs risk analytics over trusted history |
| Integration | Appears as readiness earlier and V2 later | Earlier versions preserve references and ports; V2 owns production integration lifecycle |
| Digital twin | Mentioned as readiness and non-goal | V1/V1.1 do not deliver a digital twin; V1.2 adds simulation core; V3 prepares digital twin readiness from trusted data and simulation inputs |
| Simulation | Mentioned as out-of-scope in V1, then V1.2 scope | V1 excludes simulation; V1.2 intentionally introduces Hidra Simulation Core as a new calculation layer |
| Notification | Appears as channel readiness and later module | Business modules produce notification needs; notification module delivers messages and tracks delivery, without owning workflow or incident rules |

This document resolves those overlaps by assigning every capability to one of four states:

```text
Prepare    -> minimal references, interfaces, audit fields, or future-proof design
Operate    -> core business workflow works for real operational users
Industrialize -> robust integration, reliability, retry, governance, and supportability
Intelligize -> explainable insight, analytics, projections, optimization, and forecasting readiness
```

---

## 3. Product Identity

### 3.1 Name and Meaning

**Hidra** means **Hydrocarbon Intelligence for Data, Risk, and Analytics**.

The name should guide product decisions:

- **Hydrocarbon**: the system is not generic IT workflow software; it is designed around pipeline operations.
- **Intelligence**: the system must eventually explain, anticipate, and support decisions, but only after data is trusted.
- **Data**: telemetry, topology, workflow decisions, plans, incidents, audit, and integrations are first-class product objects.
- **Risk**: deviations, abnormal operation, repeated issues, incidents, and integrity signals must be visible and explainable.
- **Analytics**: analytics and reports are built on validated, traceable, trusted history, not raw untrusted ingestion.

### 3.2 Conducting Wire

> Hidra transforms hydrocarbon operational data into trusted, validated, auditable, risk-aware intelligence for Sonatrach.

Every feature must support this sentence. If a feature does not contribute to trusted hydrocarbon intelligence, it should be postponed, narrowed, or rejected.

---

## 4. Product Thesis

Pipeline operations suffer when operational data is fragmented, untrusted, manually validated, disconnected from topology, and difficult to audit. Hidra solves this by creating a progressive operational intelligence platform:

1. **First, trust the facts**: know which infrastructure exists, where readings are taken, who acted, and which reading is validated.
2. **Then, supervise operations**: compare actuals to plans, detect deviations, manage incidents, and harden audit.
3. **Then, calculate and simulate**: run what-if scenarios and network calculations using Hidra's own topology and telemetry.
4. **Then, integrate**: connect industrial and enterprise systems without bypassing domain validation.
5. **Then, generate intelligence**: produce explainable KPIs, trends, risk analytics, reports, and forecasting readiness.

This sequencing is fundamental. Hidra must not jump directly to AI, digital twin, or dashboards before trusted data and operational ownership are established.

---

## 5. Strategic Principles

### 5.1 Trust Before Intelligence

Analytics, forecasting, simulation recommendations, risk analytics, and digital twin readiness must depend on trusted topology, validated telemetry, traceable workflows, and auditable decisions.

### 5.2 Topology as the Operational Map

Topology is not just a static asset registry. It is the canonical operational graph onto which telemetry, planning, monitoring, simulation results, incident context, and analytics insights are mapped.

### 5.3 Workflow Coordinates, Domains Own

Workflow owns validation process state: tasks, decisions, actors, reasons, delegations, escalations, and timeline. It does not own telemetry values, topology assets, planning rules, monitoring logic, or incident state.

### 5.4 Human-in-the-Loop Decision Governance

Hidra can recommend, explain, alert, simulate, forecast, and highlight risk. It must not directly actuate industrial equipment or close operational decisions without a controlled human or approved workflow.

### 5.5 Modular Monolith First

HidraAPI starts as a modular monolith. The modular monolith is not a shortcut; it is a deliberate architecture that keeps deployment simple while enforcing internal boundaries.

Future extraction is allowed only when justified by load, lifecycle, team ownership, or operational constraints.

### 5.6 Catalog-Backed Business Language

Business taxonomies must be catalog-backed and multilingual. Java enums are reserved for technical lifecycle states such as statuses and decisions that are internal to state machines.

### 5.7 Auditability by Design

Every operational decision must answer:

```text
who acted
when they acted
what object was affected
what changed
why it changed
what workflow or operational state was involved
what correlation/request context existed
```

### 5.8 Calculation Without Unsafe Control

V1.2 introduces internal simulation and calculation, but simulation results remain decision support. They do not automatically control valves, pumps, or SCADA actions.

---

## 6. Product Personas

| Persona | Primary need | Hidra value |
|---|---|---|
| Operator | Enter, check, or observe readings and operational facts | Clear operational context and reduced ambiguity |
| Validator | Approve, reject, correct, or escalate readings | Traceable workflow decisions and reasons |
| Planner | Define expected flow/targets and compare actuals | Trusted actuals and plan versioning |
| Supervisor | Monitor deviations, delays, incidents, and risk | Operational visibility and accountability |
| HSE / Risk stakeholder | Understand exposure and repeated abnormality | Risk signals and incident evidence |
| Engineer / Simulation user | Run what-if network scenarios | Topology-driven simulation and explainable results |
| Integration administrator | Connect SCADA, historian, IAM, files, channels | Controlled mappings, retries, and external references |
| Auditor | Prove who did what and why | Append-only evidence and searchable audit trail |
| Executive manager | Understand operational health | KPIs, trends, reports, and risk analytics |

---

## 7. Vision Version Model

Hidra evolves through a sequence of phases. Each phase has a different product intent; it is not a pile of modules.

| Version | Name | Strategic question | Product state at exit |
|---|---|---|---|
| V1 | Trusted Operational Data Foundation | Can we trust the operational facts and decisions? | Validated readings tied to topology, actors, workflow, and audit-ready decisions |
| V1.1 | Operations | Can operations use trusted data every day? | Plans, monitoring, alerts/incidents, hardened audit, basic read views |
| V1.2 | Simulation and Calculation | Can Hidra calculate network behavior and support what-if decisions? | Internal simulation core, scenarios, runs, results, topology-driven calculations |
| V2 | Integration | Can Hidra safely exchange data with industrial and enterprise systems? | Controlled external systems, connectors, mappings, retry/dead-letter, integration audit |
| V3 | Intelligence | Can Hidra generate explainable insight from trusted history? | KPIs, trends, reports, risk analytics, forecasting readiness, digital twin readiness |

---

## 8. V1 — Trusted Operational Data Foundation

### 8.1 Intent

V1 establishes trusted operational truth. It is the foundation phase, not the final product.

V1 must answer:

```text
Who is the actor?
Which organization unit is responsible?
What infrastructure exists?
Where is the reading taken?
Which source/device/point produced the reading?
What value, timestamp, quality, and state were recorded?
Who validated or rejected it?
Why was it approved, rejected, corrected, delegated, or escalated?
Can the decision be proven later?
```

### 8.2 V1 Owns

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

### 8.3 V1 Product Outcome

At the end of V1, Hidra can say:

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

### 8.4 V1 Non-Goals

V1 must not attempt:

```text
full SCADA real-time control
automatic valve or pump control
full historian replacement
planning implementation
monitoring implementation
incident lifecycle implementation
advanced AI decision automation
full digital twin simulation
microservices decomposition
enterprise data lake replacement
complex optimization engines
full production analytics platform
full notification delivery platform
full audit hardening
```

### 8.5 V1 Acceptance Gate

V1 is accepted when:

```text
kernel and platform baseline are stable
identity, organization, topology, telemetry, and workflow compile and pass tests
telemetry reading can be referenced by workflow
workflow can create validation tasks for telemetry readings
workflow can approve, reject, request correction, delegate, and escalate
workflow records actor snapshot, organization snapshot, reason, comment, and timestamp
telemetry remains owner of reading state and value
audit-ready event port exists
business taxonomies are catalog-backed
user-facing labels are multilingual
full repository tests pass
```

---

## 9. V1.1 — Operations

### 9.1 Intent

V1.1 converts trusted data into daily operational supervision. It does not introduce AI or full external integration. It makes trusted readings useful for planning, monitoring, alerts/incidents, and auditable operational follow-up.

V1.1 must answer:

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

### 9.2 V1.1 Owns

```text
planning
monitoring
incidents
audit hardening
basic dashboards / operational read views
risk signals
```

### 9.3 Planning Vision

Planning owns expected operational targets: flow plans, planning periods, operational targets, versions, approvals, and plan status. It references topology and validated telemetry but does not own topology or telemetry readings.

Planning must make approved plans immutable and create revisions instead of destructive edits.

### 9.4 Monitoring Vision

Monitoring compares validated telemetry to planning targets, thresholds, and monitoring rules. It produces operational state, alert candidates, acknowledgements, and initial risk signals.

Monitoring does not own incidents, notification delivery, or analytics models.

### 9.5 Incident Vision

Incidents manage operational problems from detection to closure. They own incident classification, severity, status, timeline entries, response actions, impact assessment, root-cause analysis, attachments, and resolution.

Incidents may reference monitoring alerts, topology, telemetry, workflow, actors, organization units, and audit evidence.

### 9.6 Audit Hardening Vision

Audit hardening converts V1 audit readiness into durable audit evidence. It owns audit events, actors, targets, actions, before/after values, correlation, search projections, masking rules, and traceability.

Audit does not own business decisions; it records evidence of them.

### 9.7 V1.1 Non-Goals

V1.1 must not attempt:

```text
SCADA production integration
historian replacement
OPC UA/MQTT production connectors
enterprise-wide integration hub
advanced analytics
AI forecasting
digital twin
notification delivery platform beyond readiness
microservices extraction
```

### 9.8 V1.1 Acceptance Gate

V1.1 is accepted when:

```text
validated telemetry can be compared to plans
monitoring can detect threshold and planned-vs-actual deviations
alerts or alert candidates can be created
incidents can be opened, tracked, and closed
audit evidence is hardened for workflow and operational state changes
basic operational read views exist
full tests pass
```

---

## 10. V1.2 — Simulation and Calculation

### 10.1 Intent

V1.2 introduces Hidra as a system of calculation. It adds an internal hydraulic/network simulation capability dedicated to Sonatrach pipeline operations.

This is not a generic process simulator and not a complete digital twin. It is a controlled simulation core used for operationally relevant what-if, capacity, pressure, flow, safety margin, integrity, and planning decisions.

### 10.2 Strategic Shift

Before V1.2:

```text
Hidra = system of intelligence and coordination.
```

With V1.2:

```text
Hidra = system of intelligence + system of coordination + system of calculation.
```

### 10.3 V1.2 Owns

```text
simulation scenario lifecycle
scenario inputs
scenario runs
scenario results
solver/model version metadata
calculation traceability
simulation result mapping to topology
simulation recommendations
validation benchmark suites
```

### 10.4 Simulation Design Principles

V1.2 simulation must be:

```text
Hidra-owned: core models and algorithms belong inside Hidra Simulation Core
topology-driven: simulation graph is derived from Hidra topology
scenario-first: every run has purpose, inputs, constraints, and objective
explainable: outputs link to inputs, solver version, and assumptions
auditable: run owner, time, network, boundary conditions, and results are traceable
asynchronous: runs support status, progress, cancellation, retry, and errors
bounded: start with operationally relevant steady-state cases before dynamic complexity
benchmarkable: commercial tools are references, not runtime dependencies
```

### 10.5 Commercial Tools Role

Tools such as Aspen HYSYS/Hydraulics, PIPESIM, OLGA, UniSim, AVEVA/PIPEPHASE heritage, and DNV Synergi may be used as benchmark references and validation comparators. Hidra must not depend on them as runtime engines for V1.2.

### 10.6 V1.2 Functional Scope

Minimal viable simulation core:

```text
network model and steady-state solver
scenario management
scenario execution
result persistence
pressure/flow/temperature result mapping where relevant
comparison between simulated values and trusted telemetry
result overlay on topology visualization
audit and governance of simulation-driven recommendations
```

Extended post-V1.2 scope:

```text
multiphase and thermal effects
time-varying or quasi-dynamic simulation
transient solver for selected high-value cases
advanced optimization and debottlenecking proposals
```

### 10.7 V1.2 Non-Goals

V1.2 must not attempt:

```text
full-featured process simulation of entire processing plants
replication of every commercial simulator feature
training simulator GUI parity
direct automated control based on simulation output
replacement of all engineering tools in one step
closed-loop optimization
```

### 10.8 V1.2 Acceptance Gate

V1.2 is accepted when:

```text
simulation scenarios can be created and versioned
scenarios are tied to topology subsets and objectives
steady-state runs can execute asynchronously
runs preserve solver/model version and assumptions
results are linked to topology elements
results can be compared to telemetry snapshots
selected benchmark cases are validated against reference tools
simulation recommendations remain workflow-governed and auditable
```

---

## 11. V2 — Integration

### 11.1 Intent

V2 connects Hidra to industrial and enterprise systems without corrupting domain ownership. It industrializes external data exchange, external references, mappings, retries, and auditability.

V2 must answer:

```text
Which external system produced this data?
Was the import successful?
Was the external reference preserved?
Can failed ingestion be retried?
Can data be mapped safely?
Can Hidra exchange information without bypassing validation?
Can enterprise IAM and notification channels be connected?
```

### 11.2 V2 Owns

```text
integration context
external systems
connector configurations
ingestion jobs
integration mappings
external references
retry policies
dead-letter records
integration runs
integration errors
integration audit
```

### 11.3 SCADA and Historian Position

V2 may ingest and map data from SCADA and historian systems, but it must remain controlled and read-oriented unless explicitly approved otherwise.

Allowed:

```text
read-only data ingestion
external tag mapping
source/device/point mapping
timestamp and quality mapping
raw payload preservation
failed ingestion traceability
historian reference registry
batch backfill
```

Forbidden:

```text
SCADA control commands
valve or pump actuation
unvalidated direct writes into trusted operational state
historian replacement
```

### 11.4 Enterprise IAM and Notification Position

V2 may connect identity to enterprise IAM and connect notification channels such as email, SMS readiness, webhooks, or future push channels.

Notification must not own workflow, monitoring, alert, or incident rules. It delivers messages requested by producer modules.

### 11.5 File Import/Export Position

Controlled CSV/Excel import/export is allowed only with validation preview, mapping templates, error capture, audit trail, and trusted source markers.

Silent imports and imports bypassing validation are rejected.

### 11.6 V2 Non-Goals

V2 must not attempt:

```text
SCADA control
automatic industrial actuation
full historian replacement
enterprise data lake replacement
complex streaming platform unless required by load
AI analytics
digital twin simulation
microservices decomposition by default
```

### 11.7 V2 Acceptance Gate

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

---

## 12. V3 — Intelligence

### 12.1 Intent

V3 turns trusted operational history into explainable intelligence. It depends on V1 trusted data, V1.1 operations, V1.2 calculation, and V2 reliable integration.

V3 must answer:

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

### 12.2 V3 Owns

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
projection/read-model optimization
digital twin readiness
```

### 12.3 Analytics Vision

Analytics creates derived insight from trusted operational data. It owns analytic projections, KPI definitions, KPI snapshots, trend views, anomaly candidates, risk insights, operational insights, and projection refresh runs.

Analytics must not modify source-of-truth operational state.

### 12.4 Reporting Vision

Reporting produces trusted operational reports and exports. It owns report definitions, report runs, report projections, report exports, and report schedules.

Reporting uses trusted read models, validated telemetry, planning results, monitoring evaluations, incident history, audit summaries, and analytics projections.

### 12.5 Forecasting and Anomaly Readiness

Forecasting and anomaly detection are allowed as readiness and explainable support capabilities. They must not become black-box decision automation.

Allowed:

```text
forecast input preparation
clean feature datasets
baseline forecast models
forecast confidence metadata
manual review of forecast outputs
explainable anomaly candidates
threshold and statistical comparison
human review workflow for anomaly confirmation
```

Forbidden without explicit approval:

```text
automatic operational decisions
automatic control actions
black-box risk scoring
unexplainable automated decisions
direct incident closure or escalation without confirmation
```

### 12.6 Digital Twin Readiness

V3 may prepare digital twin readiness using trusted topology, validated historical telemetry, asset state history, incident history, planning history, monitoring history, integration references, and simulation inputs/results.

Digital twin readiness is not the same as delivering a full digital twin. A full digital twin requires explicit approval, scope, ownership, performance requirements, and governance.

### 12.7 V3 Acceptance Gate

V3 is accepted when:

```text
analytics projections are based on trusted data
reports clearly identify data freshness and validation status
KPIs are explainable
trends exclude or mark untrusted data
forecasting inputs are traceable
risk analytics explains why a risk signal exists
digital twin readiness uses trusted history only
analytics does not modify source-of-truth state
full tests pass
```

---

## 13. Unified Capability Maturity Map

| Capability | V1 | V1.1 | V1.2 | V2 | V3 |
|---|---|---|---|---|---|
| Identity and actors | Internal actor model | Operational responsibility usage | Scenario/run ownership | IAM integration readiness | Access analytics/security KPIs |
| Organization | Employees/units/responsibility | Follow-up ownership | Simulation scenario ownership | External references to org scopes | Org performance/risk insight |
| Topology | Canonical network/asset model | Used by monitoring and incidents | Simulation graph source | External asset mapping | Digital twin/topology analytics readiness |
| Telemetry | Sources/devices/points/readings/state | Trusted actuals for monitoring | Initialization/calibration snapshots | SCADA/historian ingestion | Historical trend/forecast inputs |
| Workflow | Validation of telemetry readings | Operational workflows and evidence | Governance of simulation recommendations | Integration does not bypass workflow | Workflow delay/bottleneck analytics |
| Planning | Prepared only | Plan creation/versioning/comparison | Feasibility checks via simulation | External plan import/export if needed | Plan performance analytics |
| Monitoring | Prepared only | Threshold/deviation/risk signals | Look-ahead monitoring from simulation | External alert exchange if needed | Risk analytics/anomaly readiness |
| Incidents | Out of scope | Incident lifecycle | Simulation in incident analysis | External incident references | Incident trend/root-cause support |
| Audit | Readiness and event ports | Hardened searchable evidence | Simulation-run audit | Integration audit | Audit completeness KPIs |
| Simulation | Explicitly out of scope | Requirements may emerge | Internal Simulation Core | Import/export for validation | Digital twin readiness and optimization inputs |
| Integration | References/ports only | Still mostly out of scope | Benchmark import/export optional | Production integration context | Analytics consumes integration references |
| Notification | Out of scope/readiness | Basic readiness | Scenario/run notifications optional | Channel delivery integration | Notification effectiveness analytics |
| Analytics | Out of scope | Basic operational read views only | Simulation result analytics foundation | Integration history available | Full explainable intelligence |
| Reporting | Out of scope | Basic dashboards/read views | Simulation reports possible | Export channels | Trusted reporting platform |
| Digital twin | Non-goal | Non-goal | Simulation foundation | Integration foundation | Readiness, not automatic full twin |

---

## 14. Architecture Vision

### 14.1 Architecture Style

Hidra uses:

```text
Domain-Driven Design
Hexagonal Architecture
Modular Monolith First
Event-aware internal architecture
API-first external integration
Clear separation of API, application, domain, infrastructure, platform, and kernel layers
```

### 14.2 Canonical Naming

Canonical Java root:

```text
dz.sh.hidra
```

Canonical foundation module:

```text
kernel
```

Canonical identity module:

```text
identity
```

Deprecated names must not be used in implementation:

```text
dz.sonatrach.hidra
sharedkernel
identityaccess
```

### 14.3 Module Structure

Each business module follows:

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
  <external-adapter>
```

### 14.4 Dependency Rules

Allowed direction:

```text
api -> application -> domain
application -> ports
infrastructure -> ports/domain mappings
modules -> kernel/platform where appropriate
```

Forbidden:

```text
controller directly accesses repository
domain depends on infrastructure
domain depends on Spring Web or JPA
application depends on API layer
modules share persistence entities
kernel becomes a dumping ground
workflow owns another domain's business rules
analytics modifies source-of-truth operational state
integration bypasses application/domain validation
```

### 14.5 Bounded Contexts

| Context | Responsibility | Owns | Does not own |
|---|---|---|---|
| kernel | Stable primitives | IDs, pagination, base markers | Business aggregates |
| platform | Technical foundation | Config, security plumbing, observability | Business rules |
| identity | Authentication and authorization | Users, roles, permissions | Employee hierarchy |
| organization | Operational responsibility | Employees, units, positions | Login credentials |
| topology | Physical network | Pipelines, equipment, measurement points | Telemetry values |
| telemetry | Operational measurement facts | Readings, sources, devices, quality/state | Incident lifecycle |
| workflow | Approval/validation process | Tasks, decisions, workflow state | Telemetry values/business state |
| planning | Expected operation | Plans, targets, versions | Raw telemetry ingestion |
| monitoring | State interpretation | Rules, thresholds, alert candidates | Incident resolution |
| incidents | Operational incident lifecycle | Incident state/timeline/resolution | Alert rule evaluation |
| audit | Evidence | Append-only audit events | Business decisions |
| simulation | Calculation/scenarios | Scenarios, runs, solver versions, results | Topology source data or SCADA control |
| integration | External system exchange | Connectors, mappings, retries | Domain truth |
| analytics | Derived intelligence | KPIs, trends, risk insights | Source-of-truth mutation |
| reporting | Reports/exports | Report definitions/runs/exports | Business state |
| notification | Delivery | Templates, channels, delivery status | Workflow/incident rules |

---

## 15. Data Vision

Hidra separates five data categories:

```text
transactional data
telemetry/time-series data
audit data
outbox/event data
projection/reporting/analytics data
```

### 15.1 Transactional Data

PostgreSQL is the initial transactional source of truth for identity, organization, topology, telemetry baseline, workflow, planning, monitoring, incidents, audit, integration configuration, and simulation scenario metadata.

### 15.2 Telemetry and Time-Series Data

Validated readings can start in PostgreSQL with historian references. Dedicated time-series storage may be introduced later when volume, retention, or analytics performance requires it.

### 15.3 Audit Data

Audit data is append-only and must preserve actor, action, target, timestamp, correlation, request context, before/after values, reason, and workflow state.

### 15.4 Outbox Data

Outbox records support reliable event publication without coupling domain transactions to external delivery.

### 15.5 Projections and Analytics Data

Read models, dashboards, reports, and analytics projections are derived. They can be rebuilt and must not become source of business truth.

---

## 16. Security, Observability, and Governance Vision

### 16.1 Security

Hidra security must support internal authentication, future enterprise IAM, RBAC, ABAC readiness, organization-scoped permissions, authority codes, groups, segregation of duties, and auditable permission decisions.

Authorization must be policy-driven and centralized, not scattered across controllers.

### 16.2 Observability

Hidra must produce structured logs, metrics, traces, correlation IDs, request IDs, actor IDs, module names, operation names, health checks, readiness checks, integration job monitoring, ingestion monitoring, and audit visibility.

Sensitive data must never be logged.

### 16.3 Governance

Governance is not only a compliance feature. It is a product requirement. Every state-changing decision that affects operational truth must be explainable, traceable, and recoverable.

---

## 17. What Hidra Is Not

Hidra is not:

```text
a generic BPM engine
a generic data lake
a full SCADA replacement
a valve/pump control system
a full historian replacement
a black-box AI decision engine
a microservices platform by default
a dashboard-only product
a complete digital twin in V1/V1.1/V1.2
```

Hidra may integrate with or prepare for some of these domains, but it must not pretend to own them prematurely.

---

## 18. Final Roadmap Gates

### Gate 1 — V1 Accepted

```text
trusted topology
trusted telemetry
workflow validation
identity and organization context
audit-ready decision trail
full tests pass
```

### Gate 2 — V1.1 Accepted

```text
planning
monitoring
incidents
audit hardening
basic operational dashboards
validated readings used in daily supervision
```

### Gate 3 — V1.2 Accepted

```text
Hidra Simulation Core exists
steady-state scenario runs execute
simulation results map to topology
simulation results compare to telemetry snapshots
benchmarks against reference tools exist
simulation recommendations remain workflow-governed
```

### Gate 4 — V2 Accepted

```text
external systems are registered
connectors and mappings are controlled
ingestion jobs are traceable
retries and dead-letter handling exist
SCADA/historian/IAM/notification integration is controlled
validation is not bypassed
```

### Gate 5 — V3 Accepted

```text
analytics use trusted history
reports expose freshness and validation status
KPIs are explainable
risk analytics is traceable
forecasting/anomaly outputs are human-reviewable
digital twin readiness is based on trusted topology, telemetry, simulation, and integration history
```

---

## 19. Open Product Decisions

These decisions should be made deliberately before implementation of the related phase:

1. Which operational networks and products are the first priority for simulation: gas, crude oil, multiproduct, or multiphase segments?
2. What accuracy and runtime tolerance must Hidra Simulation Core meet before it can be operationally trusted?
3. Which commercial tools and benchmark cases will be used to validate Hidra Simulation Core?
4. Which SCADA/historian connectors matter first in V2?
5. Which KPIs are mandatory for V3 executive and operational dashboards?
6. Which workflows require strict segregation of duties?
7. Which audit fields are legally or operationally mandatory for Sonatrach/TRC?
8. When, if ever, should a module be extracted from the modular monolith into a service?

---

## 20. Final Vision Statement

Hidra is the trusted operational intelligence layer for hydrocarbon pipeline operations.

It starts by making operational facts trustworthy. It then turns those facts into supervision, workflow, incidents, and audit. It adds simulation to calculate and reason about network behavior. It integrates with industrial and enterprise systems without losing ownership or traceability. Finally, it transforms validated history into explainable intelligence, reports, risk analytics, and digital twin readiness.

Hidra should evolve carefully, in sequence, with every phase strengthening the next:

```text
trusted data -> operations -> simulation -> integration -> intelligence
```

That is the corrected Hidra vision.
