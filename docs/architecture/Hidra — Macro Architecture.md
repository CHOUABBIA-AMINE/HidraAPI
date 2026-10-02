# Hidra — Macro Architecture

```text
Document code      : HIDRA-ARCH-MACRO
Repository         : HidraAPI
Namespace          : dz.sh.hidra
Product            : Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics
Document type      : Macro architecture
Version            : 2.2
Status             : Enhanced architecture baseline aligned to Master Product Vision v1.5
UpdatedOn          : 2026-06-10
Target repository  : HidraAPI
Primary author     : Abir MEDJERAB
```

---

## 1. Purpose

This document defines the macro architecture of Hidra.

It translates the Master Product Vision into a system-wide architectural model that:

- defines the primary bounded contexts,
- clarifies domain ownership and cross-context interaction,
- establishes dependency, layering, module, database, and projection ownership rules,
- aligns runtime architecture with a modular monolith strategy,
- defines enforceable engineering guardrails for HidraAPI,
- and preserves a controlled path from trusted operational data to explainable intelligence.

This document is the top-level architectural reference for the Hidra backend.

Micro-architecture documents, implementation tasks, package structures, database schemas, APIs, workflows, module-specific roadmaps, and extraction decisions must conform to this macro model.

---

## 2. Architectural Position

Hidra is designed as a **modular monolith first** platform.

This is a deliberate architectural choice:

- one deployable unit,
- one controlled operational boundary,
- one primary integration surface,
- many strictly isolated domain modules,
- strict internal contracts,
- and an extraction path only when justified by real operational pressure.

Hidra is **not** microservices by default.

Distribution is an optimization step, not a starting assumption.

The architecture favors:

- strong bounded contexts,
- clean internal contracts,
- hexagonal application design,
- explicit ownership,
- event-aware coordination,
- schema and package isolation,
- audit-first state changes,
- topology-centered operational modeling,
- governed integration,
- and future extraction only when justified.

---

## 3. System Thesis

Hidra is the trusted operational intelligence layer for hydrocarbon transportation systems.

At macro level, the system evolves in one direction:

```text
trusted data -> live operations -> engineering calculation -> enterprise integration -> explainable intelligence
```

That sequence is architectural, not only roadmap-related.

No higher-order capability is allowed to bypass lower-layer trust requirements.

This means:

- analytics cannot outrank untrusted telemetry,
- AI cannot invent operational truth,
- reports cannot become source-of-truth data,
- integration cannot mutate domain state without domain validation,
- simulation cannot replace approved topology and trusted measurements,
- and workflow cannot own the business facts it governs.

The system must always preserve this guardrail:

```text
facts first -> supervision second -> calculation third -> integration fourth -> intelligence last
```

---

## 4. Capability Maturity Rule

The macro architecture is stable before every module is fully active.

A capability may exist architecturally before it is fully operational.  
To avoid confusion between architecture presence and production maturity, Hidra uses four capability states:

```text
Prepare        -> references, ports, identifiers, audit fields, package and schema readiness
Operate        -> real business workflow used by operational users
Industrialize  -> retries, supportability, observability, security hardening, connectors, reliability
Intelligize    -> explainable analytics, derived insight, optimization readiness, forecasting readiness
```

This rule applies to all modules and cross-cutting capabilities.

Examples:

| Capability | Prepare | Operate | Industrialize | Intelligize |
|---|---|---|---|---|
| Audit | Event ports, actor/correlation fields, audit-ready design | Append-only operational evidence | Searchability, masking, integration audit, retention controls | Audit completeness analytics |
| Notification | Producer intent, channel abstraction, delivery contracts | Basic operational delivery | Retries, delivery evidence, multi-channel governance | Notification effectiveness analytics |
| Integration | Import/export ports, external references, mapping contracts | Controlled import/export jobs | Connectors, retries, dead letters, operational supportability | Integration quality and latency analytics |
| Analytics | Basic operational read views and projections | Trusted KPI refresh and dashboards | Projection pipelines, freshness controls, hardened jobs | Explainable trends, anomaly readiness, risk analytics |
| Simulation | Requirements, contracts, topology hooks | Scenario creation and bounded calculations | Solver operations, benchmark suites, execution hardening | Optimization readiness and digital twin readiness inputs |
| Digital twin | Explicitly absent as delivered capability | Not applicable in early phases | Readiness through topology, telemetry, simulation, and history | Still not a full digital twin unless separately approved |

Architectural presence must never be confused with operational maturity.

---

## 5. System Shape

Hidra is organized into two macro process planes that remain stable across phases.

What changes by phase is not whether a module belongs to the architecture, but whether it is:

- prepared,
- operational,
- industrialized,
- or intelligized.

### 5.1 Core Operational Plane

Models, measures, supervises, protects, maintains, and accounts for the physical hydrocarbon transportation network.

### 5.2 Transversal Governance Plane

Provides identity, workflow, auditability, simulation, risk synthesis, configuration control, observability, reporting, integration support, security governance, and explainable intelligence across all operational domains.

---

## 6. Core Operational Plane

The core operational plane owns the physical and operational reality of the hydrocarbon transportation network.

```text
Topology
   -> Telemetry
      -> Planning
         -> Monitoring
            -> Alarm Management
            -> Leak Detection
               -> Incident Management
                  -> Network Integrity
                  -> Asset Management
                  -> Custody Transfer
                  -> HSE Management
                  -> Integration
```

The ordering is not merely visual. It expresses architectural dependency and maturity.

For example:

- telemetry values must be bound to topology;
- monitoring must evaluate trusted telemetry against approved planning and configuration;
- alarms must be raised from monitored deviations;
- leak detection must use topology, telemetry, simulation, and monitoring evidence;
- incidents must consolidate operational evidence without owning upstream facts;
- integrity must assess condition using topology, telemetry, incidents, and simulation;
- assets must execute maintenance actions without owning integrity diagnosis;
- custody must validate commercial measurements without owning generic telemetry ingestion;
- and HSE must own safety processes without replacing incident root-cause ownership.

---

## 7. Transversal Governance Plane

The governance plane coordinates, secures, traces, calculates, analyzes, reports, and amplifies trusted operational facts without replacing domain ownership.

```text
Identity
Organization
Workflow
Audit
Documents
Configuration
Notification
Simulation
Risk
Analytics
Reporting
Environment
OT Security
AI Agents
```

The governance plane must never become a hidden business-domain owner.

Its role is to provide:

- access control,
- organizational responsibility,
- approval and escalation,
- audit evidence,
- document control,
- governed configuration,
- notifications,
- engineering calculation support,
- cross-domain risk synthesis,
- analytics and reporting,
- environmental metrics,
- OT security visibility,
- observability and operability evidence,
- and explainable AI assistance.

---

## 8. Persona-to-Architecture Traceability

The macro architecture must remain accountable to operational personas, not only technical boundaries.

| Persona | Primary architectural dependencies |
|---|---|
| Operator | topology, telemetry, workflow, monitoring |
| Validator | workflow, telemetry, audit, organization |
| Planner | planning, topology, telemetry, simulation |
| Supervisor | monitoring, alarms, incidents, risk, reporting |
| Integrity engineer | integrity, telemetry, incidents, simulation, documents |
| Asset engineer | assets, integrity, workflow, documents |
| HSE stakeholder | hse, incidents, environment, audit |
| Simulation user | simulation, topology, telemetry, planning |
| Integration administrator | integration, identity, audit, notification |
| Auditor | audit, workflow, identity, reporting |
| Executive manager | analytics, reporting, risk, environment, otsecurity |

This traceability rule ensures architecture decisions remain tied to real operational users and not only internal technical structure.

---

## 9. Macro Bounded Context Map

### 9.1 Core operational contexts

| Context | Owns | Does not own | Depends on |
|---|---|---|---|
| **topology** | Pipelines, segments, nodes, stations, equipment, measurement locations, network graph structure, spatial versioning, topology visualization model, topology snapshots | Telemetry values, workflow decisions, incidents, simulation results | kernel, platform |
| **telemetry** | Sources, devices, telemetry points, readings, quality, validation state, ingestion context, trusted-reading lifecycle | Incident lifecycle, planning targets, alarm lifecycle, topology graph ownership | topology, workflow, audit |
| **planning** | Plans, periods, targets, nominations, revisions, expected operating state | Raw telemetry ingestion, alert lifecycle, topology ownership | topology, telemetry, workflow |
| **monitoring** | Threshold rules, operating envelopes, deviations, anomaly candidates, monitored state | Alarm lifecycle, incident resolution, telemetry source ownership | telemetry, planning, topology, configuration |
| **alarms** | Alarm lifecycle, prioritization, shelving, acknowledgement, alarm KPIs, operator response evidence | Threshold definition, incident ownership, monitoring rule ownership | monitoring, workflow, notification, audit |
| **leakdetection** | Leak hypotheses, leak evidence, localization logic, leak cases, hydraulic evidence correlation | Generic incident ownership, full integrity program, topology ownership | telemetry, topology, simulation, monitoring, audit |
| **incidents** | Incident lifecycle, severity, triage, timeline, RCA, remediation tracking, incident evidence bundle | Monitoring rules, raw telemetry ownership, leak algorithm ownership | alarms, leakdetection, topology, workflow, documents, audit |
| **integrity** | Structural condition, degradation, risk indicators, operating-envelope compliance, engineering condition assessment | Maintenance scheduling, work-order execution, asset service history | topology, telemetry, incidents, simulation, risk |
| **assets** | Maintenance schedules, inspections, work orders, lifecycle execution, equipment service history | Integrity diagnosis, leak calculations, topology graph ownership | topology, integrity, incidents, documents, workflow |
| **custody** | Metering validation, fiscal records, reconciliation, delivery accounting, contractual measurement evidence | General telemetry ingestion, planning ownership, commercial contract management outside measurement evidence | telemetry, topology, workflow, audit |
| **hse** | PTW, safety obligations, regulatory events, HSE evidence, safety compliance processes | Operational incident root-cause ownership, telemetry ingestion ownership | incidents, documents, workflow, audit, environment |
| **integration** | Connectors, external systems, mappings, retries, dead letters, import/export jobs, external references, adapter-level observability | Trusted domain truth, workflow decisions, source-of-truth mutation bypass, direct table mutation | explicit module import/export ports, platform, audit |

### 9.2 Transversal governance contexts

| Context | Owns | Does not own | Serves |
|---|---|---|---|
| **identity** | Users, roles, permissions, authorities, authentication context, technical security principal | Employee hierarchy, business assets, organizational responsibility | all modules |
| **organization** | Employees, departments, regions, responsibility structure, business assignment model | Login credentials, permission engine, authentication | all modules |
| **workflow** | Tasks, approvals, rejections, delegations, escalations, decision state machines, approval evidence, process state | Telemetry values, topology assets, plans, incidents, alarms, configuration values themselves | all modules |
| **audit** | Append-only trace evidence, before/after state evidence, actor/action/target proof, correlation proof | Business decisions themselves, mutable operational state | all modules |
| **documents** | Controlled files, procedures, P&IDs, as-builts, certificates, attachments, document version evidence | Incident state, asset state, topology source-of-truth | topology, incidents, assets, hse, integrity |
| **configuration** | Governed setpoints, thresholds, coefficients, runtime configuration approval, effective-dated configuration | Monitoring evaluations, domain facts, telemetry readings | monitoring, alarms, simulation, risk |
| **notification** | Delivery channels, templates, routing, dispatch status, delivery retry evidence | Workflow rules, monitoring rules, incident policy | alarms, incidents, workflow, integration |
| **simulation** | Scenarios, runs, solver metadata, results, assumptions, benchmark traceability, calculation lineage | Topology ownership, live control actions, operational truth | planning, monitoring, leakdetection, integrity, risk |
| **risk** | Cross-domain risk indicators, aggregated exposure views, risk synthesis, risk synthesis evidence | Source telemetry, incident state, structural diagnosis | monitoring, incidents, integrity, hse, simulation |
| **analytics** | KPIs, trends, anomaly candidates, analytic projections, derived insights, historical read models | Source-of-truth mutation, final operational decisions | all trusted historical data |
| **reporting** | Reports, export definitions, report runs, schedules, rendered report evidence | Analytic calculation logic, domain state mutation | analytics, audit, operational read models |
| **environment** | Emissions, flare indicators, methane and environmental metrics, environmental evidence | HSE workflow ownership, telemetry ingestion ownership | telemetry, custody, hse, reporting |
| **otsecurity** | OT anomaly indicators, industrial security posture, cyber event visibility, OT security evidence | Identity authorization, SCADA control logic, network control actions | integration, reporting, risk |
| **agents** | Explainable AI assistance, recommendation orchestration, pattern correlation, context assembly for explainable assistance | Autonomous final decision ownership, unsafe actuation, source-of-truth mutation, hidden policy substitution | analytics, risk, simulation, monitoring |

---

## 10. Topology as the Operational Backbone

Topology is not only one bounded context among others.

It is the **operational graph backbone** of Hidra.

Topology provides the structural model that allows telemetry, monitoring, leak detection, simulation, incidents, risk, and visualization to refer to the same physical network.

Topology owns:

- pipelines,
- pipeline segments,
- stations,
- nodes,
- edges,
- equipment,
- measurement locations,
- operational graph structure,
- spatial attributes,
- topology versions,
- topology snapshots,
- network visualization model,
- and topology-to-asset binding references.

Topology must support:

```text
physical network model
   -> operational graph
      -> versioned topology snapshot
         -> measurement-point binding
            -> visualization-ready network representation
```

Topology visualization is a first-class architectural concern.

It supports:

- pipeline route visualization,
- station and node visualization,
- segment-level operational state overlay,
- telemetry binding overlay,
- alarm and incident localization,
- leak hypothesis localization,
- integrity risk overlay,
- maintenance context overlay,
- simulation result overlay,
- and executive risk map projection.

Topology visualization must not mutate operational truth directly.

It consumes topology, telemetry, monitoring, alarm, incident, integrity, simulation, and risk projections.

---

## 11. Workflow as Controlled Decision Governance

Workflow is a transversal bounded context.

It governs decisions but does not own the facts being decided upon.

Workflow owns:

- task creation,
- task assignment,
- approvals,
- rejections,
- delegations,
- escalations,
- decision state machines,
- approval comments,
- approval evidence,
- due dates,
- responsibility routing,
- and workflow audit references.

Workflow does not own:

- telemetry values,
- telemetry quality rules,
- topology assets,
- monitoring thresholds,
- alarm definitions,
- incident severity rules,
- incident root-cause analysis content,
- maintenance logic,
- simulation results,
- configuration values,
- or domain-specific invariants.

A workflow task may reference another module's object, but it must not become that object.

Correct example:

```text
workflow task references IncidentId
incident module still owns incident severity and incident lifecycle
```

Incorrect example:

```text
workflow stores and mutates incident severity directly
```

Workflow may coordinate approval, but the target module must perform the domain-valid state transition.

---

## 12. Architectural Layers

Each bounded context follows the same macro layer model.

```text
API
  -> Application
      -> Domain
      -> Application Ports
  <- Infrastructure
```

### 12.1 API layer

Owns:

- REST controllers,
- request/response contracts,
- transport-level validation,
- API mappers,
- external contract exposure,
- pagination and filtering DTOs,
- and endpoint authorization annotations where appropriate.

It must never:

- access persistence directly,
- call `EntityManager`,
- expose JPA entities,
- contain business rules,
- publish domain events directly,
- or coordinate multi-module business processes without application use cases.

### 12.2 Application layer

Owns:

- use cases,
- commands and queries,
- input ports,
- output ports,
- transaction boundaries,
- application orchestration,
- cross-aggregate coordination inside the module,
- workflow invocation,
- audit invocation,
- event publication through ports,
- and idempotency boundaries where needed.

It may coordinate multiple domain objects but must not become a dumping ground for business rules.

### 12.3 Domain layer

Owns:

- entities,
- aggregates,
- value objects,
- domain services,
- invariants,
- policies,
- domain events,
- domain-specific error reasons,
- and business lifecycle rules.

It must remain free of framework concerns.

It must not import:

- Spring MVC,
- Spring Data JPA,
- Hibernate,
- Jackson annotations for transport concerns,
- database entities,
- message broker APIs,
- or infrastructure adapters.

### 12.4 Application ports

Application ports define what the module exposes or requires.

Input ports represent use cases that may be called by API or approved internal module clients.

Output ports represent dependencies that infrastructure implements.

Typical structure:

```text
application.port.in
application.port.out
```

Input ports should be intentionally narrow.

Output ports must express business intent, not technical implementation detail.

Correct:

```text
TrustedReadingRepository
AlarmNotificationPort
TopologySnapshotQueryPort
```

Incorrect:

```text
JdbcTelemetryDao
KafkaProducerService
JpaIncidentRepositoryFromDomain
```

### 12.5 Infrastructure layer

Owns:

- persistence adapters,
- ORM entities,
- Spring Data repositories,
- messaging adapters,
- file storage adapters,
- integration connectors,
- outbox implementations,
- external API clients,
- scheduler implementations,
- and technical implementations of application output ports.

Infrastructure implements contracts owned by the application layer.

It must not dictate domain design.

### 12.6 Shared foundations

```text
kernel
platform
```

`kernel` contains stable business and architectural primitives only.

Allowed examples:

```text
EntityId
AggregateRoot
DomainEvent
BusinessCode
DateRange
Money
Quantity
Percentage
GeoPoint
CorrelationId
Version
Result
ErrorReason
```

Forbidden in `kernel`:

```text
Spring configuration
JPA entities
repositories
HTTP clients
module-specific DTOs
utility dumping grounds
business services
```

`platform` contains technical cross-cutting support.

Allowed examples:

```text
security plumbing
transaction support
observability
logging correlation
outbox infrastructure
database migration support
Spring configuration
technical exception handling
persistence support
```

Forbidden in `platform`:

```text
business rules
domain entities
module-specific use cases
module-specific repositories
shared god services
generic helpers without clear ownership
```

Neither `kernel` nor `platform` may become an unbounded shared dumping area.

---

## 13. Canonical HidraAPI Package Model

Each business module must follow this package model unless a micro-architecture document justifies a narrow exception.

Example for `topology`:

```text
dz.sh.hidra.topology
  ├── api
  │   ├── rest
  │   ├── request
  │   ├── response
  │   └── mapper
  ├── application
  │   ├── command
  │   ├── query
  │   ├── usecase
  │   ├── service
  │   └── port
  │       ├── in
  │       └── out
  ├── domain
  │   ├── model
  │   ├── value
  │   ├── event
  │   ├── service
  │   ├── policy
  │   └── error
  └── infrastructure
      ├── persistence
      │   ├── entity
      │   ├── repository
      │   └── mapper
      ├── messaging
      ├── projection
      ├── scheduler
      └── adapter
```

Not every package must exist on day one.

Empty package skeletons may be created only when explicitly required by roadmap tasks.

No module may create ambiguous packages such as:

```text
shared
sharedkernel
common
core
utils
helper
helpers
misc
```

unless explicitly approved by an architecture decision record.

---

## 14. Module Public API Rules

A module may expose only controlled contracts.

Allowed public surfaces:

```text
application.port.in
published domain/application events
read-only projection interfaces
external REST API contracts
explicit import/export ports
```

Forbidden cross-module access:

```text
module A imports module B domain entities
module A imports module B infrastructure classes
module A imports module B JPA entities
module A calls module B repositories
module A writes module B tables
module A reuses module B internal mappers
module A depends on module B internal services
```

Cross-module calls must go through one of these patterns:

```text
application input port
published event
read model / projection
integration import/export port
```

If a class is not intentionally exposed as a module contract, another module must not import it.

---

## 15. Dependency Rules

### 15.1 Allowed dependencies

```text
api -> application
application -> domain
application -> application.port.out
infrastructure -> application.port.out
infrastructure -> application
modules -> kernel
modules -> platform
module client -> other module application.port.in, only when explicitly approved
read-only projection consumer -> trusted source projection
```

### 15.2 Forbidden dependencies

```text
domain -> infrastructure
domain -> Spring MVC / JPA / transport frameworks
api -> repository
controller -> entity manager
workflow -> ownership of other domain facts
analytics -> mutation of source-of-truth data
integration -> bypass of domain validation
monitoring -> ownership of alarm lifecycle
alarms -> ownership of monitoring thresholds
integrity -> ownership of maintenance execution
assets -> ownership of integrity diagnosis
agents -> autonomous final actuation
module -> another module's infrastructure
module -> another module's persistence entity
module -> another module's private application service
```

### 15.3 Allowed import examples

```java
import dz.sh.hidra.topology.application.port.in.CreatePipelineUseCase;
import dz.sh.hidra.telemetry.application.port.out.TrustedReadingRepository;
import dz.sh.hidra.kernel.identity.EntityId;
import dz.sh.hidra.platform.persistence.OutboxPublisher;
```

### 15.4 Forbidden import examples

```java
import dz.sh.hidra.telemetry.infrastructure.persistence.TelemetryJpaRepository;
import jakarta.persistence.Entity;
import dz.sh.hidra.incidents.infrastructure.persistence.entity.IncidentJpaEntity;
import dz.sh.hidra.incidents.domain.model.IncidentSeverity;
import dz.sh.hidra.monitoring.infrastructure.persistence.MonitoringRuleJpaRepository;
```

---

## 16. Friend-Orchestrator Rule

Some modules coordinate others without owning their facts.

Examples:

- `workflow` governs decisions over telemetry, planning, configuration, incidents, assets, and HSE;
- `risk` aggregates signals from monitoring, incidents, integrity, HSE, and simulation;
- `analytics` reads across trusted history but never rewrites source state;
- `integration` transports data across system boundaries but never becomes domain truth;
- `agents` orchestrate recommendations but do not perform final unsafe decisions.

A friend-orchestrator may:

```text
reference external module identifiers
request approved use cases
consume projections
listen to events
emit coordination events
attach audit evidence
```

A friend-orchestrator must not:

```text
own another module's aggregate state
mutate another module's tables
copy another module's lifecycle state as truth
reimplement another module's business rules
bypass workflow, audit, or validation
```

---

## 17. Database and Schema Ownership Rules

Because Hidra is a modular monolith, database boundaries are as important as package boundaries.

Initial deployment may use one database, but ownership must remain modular.

### 17.1 Schema ownership

Each module owns its schema or table namespace.

Preferred schema pattern:

```text
hidra_topology
hidra_telemetry
hidra_monitoring
hidra_alarms
hidra_incidents
hidra_integrity
hidra_assets
hidra_workflow
hidra_audit
```

Alternative table-prefix pattern, only when schema separation is not practical:

```text
topology_pipeline
telemetry_reading
monitoring_deviation
alarms_alarm
incidents_incident
```

### 17.2 Write ownership

Only the owning module may write its tables.

Forbidden:

```text
incidents writes telemetry_reading
workflow writes incidents_incident
integration writes monitoring_rule directly
analytics writes topology_pipeline
```

Allowed:

```text
integration calls telemetry import port
workflow requests incident approval transition
analytics builds its own projection from trusted events
reporting reads approved reporting projections
```

### 17.3 Cross-module foreign keys

Cross-module foreign keys should be avoided by default.

Prefer storing stable identifiers:

```text
Incident stores TopologySegmentId
Alarm stores DeviationId
WorkflowTask stores TargetReference(module, aggregateType, aggregateId)
```

Cross-module referential integrity should be enforced at application level unless an architecture decision explicitly approves a database-level foreign key.

### 17.4 Read models

Read models and projections are derived artifacts.

They may be rebuilt.

They must never become operational truth.

---

## 18. Interaction Model

Hidra uses four interaction styles.

### 18.1 Direct synchronous application calls

Used when:

- strong consistency is required,
- the caller needs an immediate response,
- the use case belongs to one transactional boundary,
- or an approved cross-module input port exists.

Examples:

```text
telemetry validation requesting workflow approval state
monitoring retrieving approved thresholds
custody validating a trusted measurement set
incident requesting topology localization details
```

### 18.2 Internal domain/application events

Used when:

- state change must notify other contexts,
- coupling should remain low,
- downstream reactions are asynchronous,
- downstream consistency may be eventual.

Examples:

```text
TelemetryReadingApproved
DeviationDetected
AlarmRaised
IncidentOpened
SimulationCompleted
MaintenanceActionScheduled
TopologyVersionPublished
ConfigurationApproved
```

### 18.3 Read-model and projection consumption

Used when:

- the consumer needs aggregated or shaped views,
- direct domain ownership must remain intact,
- reporting or analytics need query-optimized data,
- visualization needs operational overlays.

Examples:

```text
dashboard projections
KPI views
executive reporting
operational risk overview
topology visualization overlay
alarm heat map
```

### 18.4 Controlled import/export ports

Used when:

- integration imports external data into a trusted module,
- external systems require mapped data exports,
- data movement must be traceable, retryable, and auditable.

Examples:

```text
SCADA historian import
OPC UA telemetry import
ERP reference synchronization
EAM work-order export
file-based custody import
```

Integration may call only explicit import/export ports exposed by target modules.

---

## 19. Macro Operational Flow

### 19.1 Trusted-data flow

```text
external source
   -> integration adapter
      -> telemetry ingestion port
         -> source/device/point binding
            -> topology measurement-location binding
               -> quality qualification
                  -> workflow-governed validation
                     -> trusted reading
                        -> audit evidence
                           -> outbox event
```

### 19.2 Live-operations flow

```text
trusted reading
   -> planning comparison
   -> monitoring evaluation
      -> deviation candidate
         -> alarm lifecycle
         -> leak hypothesis
            -> incident
               -> audit evidence
```

### 19.3 Engineering flow

```text
topology snapshot + trusted telemetry + planning state
   -> simulation scenario
      -> run execution
         -> solver metadata
            -> results
               -> leakdetection / integrity / risk / planning support
                  -> explainable calculation lineage
```

### 19.4 Integrity-to-execution flow

```text
telemetry + incidents + simulation + historical evidence
   -> integrity assessment
      -> maintenance recommendation
         -> asset action / work order / inspection schedule
            -> workflow approval where required
               -> audit evidence
```

### 19.5 Intelligence flow

```text
trusted historical facts
   -> analytics projections
      -> risk synthesis
         -> reporting
            -> AI-assisted recommendations
               -> human-governed decision
```

### 19.6 Topology visualization flow

```text
topology snapshot
   -> network graph projection
      -> operational overlays
         -> telemetry state
         -> monitoring deviations
         -> alarms
         -> incidents
         -> leak hypotheses
         -> integrity indicators
         -> simulation results
         -> risk exposure
            -> visualization view
```

Visualization is a consumer of trusted projections.

It must not become the owner of topology, telemetry, alarm, incident, or risk state.

---

## 20. Macro Runtime Architecture

### 20.1 Deployment stance

Initial deployment remains one backend deployable unit:

- one Spring Boot application,
- one versioned codebase,
- one operational release line,
- one database boundary with schema isolation per module where practical,
- one controlled integration boundary,
- one audit model,
- and one security context.

This reduces:

- distributed complexity,
- operational overhead,
- contract drift,
- premature platform engineering,
- unnecessary service discovery,
- and avoidable distributed transaction problems.

### 20.2 Data platform stance

At macro level, Hidra separates five data categories:

```text
transactional data
telemetry / time-series data
audit data
outbox / event data
projection / analytics data
```

Initial storage strategy:

- PostgreSQL as primary transactional system of record,
- append-only audit storage inside the governed persistence model,
- outbox for reliable event publication,
- derived read models for dashboards, visualization, and reports,
- optional time-series specialization later if scale requires it.

Optional future specialized storage may include:

```text
time-series database for high-frequency telemetry
object storage for controlled documents
search index for reporting and audit exploration
graph projection for topology visualization
data warehouse/lakehouse for long-horizon analytics
```

Specialized stores are projections or technical optimizations unless explicitly designated otherwise.

They must not bypass source module ownership.

### 20.3 External boundary stance

External systems connect only through `integration`.

Examples:

- SCADA historians,
- OPC UA,
- MQTT,
- enterprise IAM,
- ERP / EAM references,
- file-based import/export,
- messaging channels,
- document systems,
- and reporting consumers.

External ingestion must be:

- traceable,
- retryable,
- mapped,
- validated,
- idempotent where required,
- auditable,
- and non-bypassing.

Hidra remains read-oriented toward industrial systems unless explicitly approved otherwise.

No module may directly actuate valves, pumps, PLCs, or control loops as part of normal architecture scope.

---

## 21. Cross-Cutting Architectural Policies

### 21.1 Audit-first state change policy

Every state-changing operation must produce:

- actor identity,
- time,
- target,
- action,
- reason/context,
- workflow reference where applicable,
- correlation token,
- before/after state evidence where relevant,
- source reference where imported,
- and module ownership reference.

Audit evidence must be append-only.

Audit may record what happened.

Audit must not decide what should happen.

### 21.2 No unsafe control policy

No module may directly actuate:

- valves,
- pumps,
- PLCs,
- compressors,
- control loops,
- safety systems,
- or industrial control commands

as part of normal architecture scope.

AI agents, analytics, simulation, monitoring, leak detection, and risk modules are decision-support capabilities, not autonomous control systems.

### 21.3 Explainability policy

Simulation, analytics, risk, and AI outputs must preserve:

- source references,
- model version,
- assumptions,
- input dataset references,
- calculation timestamp,
- decision lineage,
- confidence or uncertainty when relevant,
- and human approval where required.

### 21.4 Catalog-backed business language policy

Business taxonomies must be catalog-backed and multilingual.

This includes:

- alarm categories,
- incident categories,
- equipment types,
- safety classifications,
- risk classes,
- document types,
- topology object types,
- maintenance categories,
- and environmental metric types.

Hard-coded enums are reserved for technical lifecycle behavior only.

### 21.5 Derived-data policy

Read models, reports, analytics projections, visualization overlays, and AI context bundles are derived artifacts.

They may be rebuilt.

They must never become operational truth.

### 21.6 Idempotency policy

External imports, workflow callbacks, event consumers, and notification dispatches must support idempotency where repeated delivery is possible.

Idempotency must be based on stable business keys or technical correlation identifiers.

### 21.7 Outbox policy

State changes that publish events must use an outbox or equivalent reliable publication mechanism.

A state transition and its corresponding event publication intent must be committed atomically.

### 21.8 Multilingual policy

User-facing business labels and controlled catalogs must support multilingual representation.

Technical package names, Java types, database identifiers, event names, and API paths remain in English.

### 21.9 Readiness-is-not-delivery policy

Architectural readiness for a capability does not mean the product delivers that capability operationally.

In particular:

- digital twin readiness is not delivery of a full digital twin,
- notification readiness is not full channel industrialization,
- analytics read views are not full intelligence capability,
- integration ports are not production integration lifecycle,
- and visualization overlays are not ownership of operational state.

Any claim of delivery must match the capability maturity state actually reached.

---

## 22. Operational Governance Gates

A module is not production-ready unless it satisfies all applicable governance gates:

- ownership gate: source-of-truth responsibility is explicit;
- audit gate: state-changing actions produce accountable evidence;
- security gate: authorization is policy-driven and traceable;
- observability gate: logs, metrics, traces, and health signals exist;
- idempotency gate: repeated delivery paths are safe where applicable;
- integration gate: external exchange is mapped, validated, and retryable;
- explainability gate: simulation, risk, analytics, and AI outputs preserve lineage.

These gates apply even when a capability is otherwise functionally complete.

---

## 23. Module Implementation Placement Rules

### 23.1 Controller placement

Controllers belong in:

```text
dz.sh.hidra.<module>.api.rest
```

Controllers may call:

```text
dz.sh.hidra.<module>.application.port.in
```

Controllers must not call:

```text
repositories
EntityManager
JPA entities
external clients
other module infrastructure
```

### 23.2 Application service placement

Application services belong in:

```text
dz.sh.hidra.<module>.application.service
```

They implement input ports from:

```text
dz.sh.hidra.<module>.application.port.in
```

They may use:

```text
domain model
domain services
output ports
transaction annotations
audit ports
workflow ports
event publisher ports
```

### 23.3 Domain service placement

Domain services belong in:

```text
dz.sh.hidra.<module>.domain.service
```

They may use:

```text
entities
aggregates
value objects
domain policies
domain events
```

They must not use:

```text
repositories
Spring services
JPA entities
REST clients
workflow clients
audit clients
```

### 23.4 Repository port placement

Repository ports belong in:

```text
dz.sh.hidra.<module>.application.port.out
```

The port expresses business persistence needs.

Example:

```java
public interface PipelineRepository {
    Optional<Pipeline> findById(PipelineId id);
    void save(Pipeline pipeline);
}
```

### 23.5 JPA adapter placement

JPA adapters belong in:

```text
dz.sh.hidra.<module>.infrastructure.persistence
```

JPA entities belong in:

```text
dz.sh.hidra.<module>.infrastructure.persistence.entity
```

Spring Data repositories belong in:

```text
dz.sh.hidra.<module>.infrastructure.persistence.repository
```

Persistence mappers belong in:

```text
dz.sh.hidra.<module>.infrastructure.persistence.mapper
```

### 23.6 Mapper placement

API mappers:

```text
dz.sh.hidra.<module>.api.mapper
```

Persistence mappers:

```text
dz.sh.hidra.<module>.infrastructure.persistence.mapper
```

No generic global mapper package is allowed.

---

## 24. Phase Alignment

The macro architecture is stable across phases.

What changes by phase is activation maturity, not architectural legitimacy.

A later-phase module may be:

- architecturally present,
- prepared through ports and identifiers,
- partially operational for bounded use cases,
- or fully industrialized only in its target phase.

### Phase 1 — Data Trust

Active priority:

```text
kernel, platform, identity, organization, topology, telemetry, workflow, audit readiness
```

Primary architectural objective:

```text
establish trusted identity, topology, telemetry, workflow, and audit foundations
```

### Phase 2 — Live Operations

Active priority:

```text
planning, monitoring, alarms, incidents, risk signals, configuration basic, notification basic
```

Primary architectural objective:

```text
turn trusted telemetry into supervised operational state and managed operational response
```

### Phase 3 — Safety, Simulation, Integrity

Active priority:

```text
leakdetection, hse, simulation, integrity, assets, documents basic
```

Primary architectural objective:

```text
support safety, engineering calculation, structural condition, and maintenance execution
```

### Phase 4 — Commercial and Integration

Active priority:

```text
custody, integration, documents full, configuration full
```

Primary architectural objective:

```text
connect enterprise systems and commercial measurement evidence without bypassing domain truth
```

### Phase 5 — Intelligence and Compliance

Active priority:

```text
analytics, reporting, environment, otsecurity, agents
```

Primary architectural objective:

```text
deliver explainable analytics, compliance evidence, risk synthesis, and AI-assisted decision support
```

Later-phase contexts may exist architecturally before they are fully operationalized.

They must not bypass the maturity requirements of earlier phases.

---

## 25. Extraction Rules

A module may be extracted from the modular monolith only when at least one of the following becomes materially true:

- distinct scaling profile,
- independent operational lifecycle,
- isolated team ownership,
- strict security isolation requirement,
- protocol-specific runtime needs,
- unacceptable release coupling,
- specialized compute or runtime requirements,
- or regulatory isolation requirements.

Likely future extraction candidates:

```text
integration
simulation
analytics
reporting
agents
```

Extraction must preserve:

- bounded-context ownership,
- explicit contracts,
- auditability,
- idempotency,
- workflow integrity,
- event compatibility,
- security model consistency,
- and operational traceability.

Extraction is forbidden when the motivation is only architectural fashion.

---

## 26. Architecture Enforcement

The architecture must be enforced by code review, package rules, database ownership review, and automated tests where practical.

Recommended enforcement mechanisms:

- ArchUnit tests for forbidden imports,
- Maven or Gradle module boundaries,
- package visibility conventions,
- database migration ownership review,
- ADRs for boundary exceptions,
- CI checks for dependency drift,
- code review checklist,
- and module-specific micro-architecture documents.

Minimum ArchUnit-style rules:

```text
domain must not depend on infrastructure
domain must not depend on Spring MVC
domain must not depend on JPA
api must not depend on infrastructure persistence
modules must not import another module's infrastructure
modules must not import another module's persistence entity
modules must not write another module's schema
```

Every exception must be documented in an Architecture Decision Record.

---

## 27. High-Level Architecture Test

Every architectural proposal must pass this test:

> Does this macro design strengthen Hidra as a trusted, validated, auditable, topology-aware, workflow-governed, risk-aware operational intelligence platform for hydrocarbon transportation systems?

If yes, it may belong.

If no, it is either premature, misplaced, unsafe, or out of scope.

Additional test questions:

```text
Does it preserve source-of-truth ownership?
Does it respect topology as the operational graph backbone?
Does it keep workflow as governance rather than business ownership?
Does it keep integration as a controlled gateway?
Does it keep analytics, reporting, and AI as derived/explainable consumers?
Does it produce audit evidence for state changes?
Does it avoid unsafe control actions?
Can it be enforced in code and database ownership?
```

---

## 28. Open Product Decisions

These decisions should be made deliberately before implementation of the related phase:

1. Which operational networks are first priority for simulation: gas, crude oil, multiproduct, or multiphase?
2. What accuracy and runtime tolerance must Hidra Simulation Core achieve before operational trust is granted?
3. Which benchmark tools and benchmark cases will validate Hidra Simulation Core?
4. Which SCADA and historian connectors are first priority in Phase 4?
5. Which KPIs are mandatory for executive, operational, engineering, and audit dashboards?
6. Which workflows require strict segregation of duties?
7. Which audit fields are legally or operationally mandatory for Sonatrach and TRC?
8. Which environmental indicators are mandatory in the first compliance release?
9. What OT security visibility is required before industrial connectivity is considered production-ready?
10. Under what measurable conditions may a module be extracted from the modular monolith?
11. Which modules require dedicated time-series, search, or graph projections first?
12. Which AI-assisted recommendations require mandatory human approval before operational use?

---

## 29. Summary Architecture Statement

Hidra is a modular monolith with strong bounded contexts, a topology-centered operational model, workflow-governed decisions, audit-first traceability, controlled integration, simulation as an internal engineering calculation capability, risk and analytics as derived intelligence, reporting as explainable evidence, and AI agents as human-governed decision-support assistants.

Its macro architecture moves in one direction only:

```text
facts first -> supervision second -> calculation third -> integration fourth -> intelligence last
```

That order is the architectural guardrail of the entire platform.

The architecture is not only descriptive.

It is enforceable through:

```text
module ownership
package boundaries
application ports
schema ownership
event contracts
audit policy
workflow governance
integration gateways
derived-data rules
governance gates
and architecture tests
```

This document is the baseline for all HidraAPI module design, implementation tasks, package skeletons, roadmap execution, and future extraction decisions.