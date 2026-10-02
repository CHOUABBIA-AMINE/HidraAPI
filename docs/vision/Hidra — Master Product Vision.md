# Hidra — Master Product Vision

```text
Document code : HIDRA-VISION-MASTER
Repository    : HidraAPI
Namespace     : dz.sh.hidra
Product       : Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics
Document type : Master product vision
Version       : 1.5 Master
Status        : Architecture baseline
UpdatedOn     : 2026-06-10
Target repository : HidraAPI
```

---

## 1. Executive Summary & Product Mandate

**Hidra** — *Hydrocarbon Intelligence for Data, Risk, and Analytics*.

> **AI-Driven Operational Intelligence and Simulation Platform for Hydrocarbon Transportation Systems**

### 1.1 The Present State

Sonatrach operates one of the largest hydrocarbon transportation networks in Africa. Thousands of kilometers of pipelines. Hundreds of measurement points. Dozens of compression and pumping stations. Millions of operational decisions are made every year under pressure, under risk, and without a unified system of operational truth.

Today, operational data is fragmented across SCADA systems, historians, spreadsheets, and manual reports. Decisions are made on incomplete information. Deviations are detected late. Incidents repeat. Leaks remain difficult to localize quickly. Custody volumes require time-consuming reconciliation. Risk often becomes visible only when the consequence is already operationally expensive.

**Hidra exists to eliminate this fragmentation.**

### 1.2 The Strategic Mandate

Hidra is Sonatrach’s unified operational intelligence platform for hydrocarbon transportation systems.

Its mandate is explicit: **replace fragmented, reactive operational workflows with a single, automated, intelligent, and auditable system of operational truth**. Hidra provides trusted telemetry validation, topology-aware supervision, simulation-driven engineering support, governed workflows, risk visibility, controlled integration, and explainable intelligence.

Hidra does not add another layer of software sprawl. It becomes the operational intelligence layer operating above field-level automation and control systems.

### 1.3 Core Value Proposition

Hidra unifies topology, telemetry, workflow validation, planning, monitoring, alarm management, leak detection, incident management, network integrity, asset management, custody transfer, HSE management, simulation, integration, risk, analytics, reporting, environmental monitoring, OT cybersecurity, and AI agents into **one coherent system of operational truth**.

- Every trusted reading is structurally validated, quality-qualified, and traceable to its source, context, and approval path.
- Every operational deviation is contextualized against topology, planning, and monitored limits.
- Every workflow decision is auditable.
- Every risk signal is explainable.
- Every simulation result is traceable to assumptions, models, and inputs.
- Every custody record is defensible.
- Every recommendation remains human-governed.

**Hidra transforms raw hydrocarbon operational data into trusted, validated, auditable, risk-aware intelligence for Sonatrach.**

### 1.4 The Future State

With Hidra, supervisors no longer wait for disconnected morning reports to understand network state. The system knows. Planners do not rely on empirical guesswork alone; Hidra Simulation Core calculates capacity and operational scenarios. Integrity and asset teams act on early signals before abnormal conditions mature into failures. Custody disputes are reduced through validated, auditable, point-of-metering records.

The future of Sonatrach pipeline operations is built on trusted, self-correcting, workflow-governed data.

---

## 2. The Strategic Conducting Wire

> Hidra transforms hydrocarbon operational data into trusted, validated, auditable, risk-aware intelligence for Sonatrach pipeline operations.

This sentence is the strategic center of the product. Every module, database schema, integration, validation rule, workflow step, and engineering decision must serve it. Capabilities that do not directly strengthen trusted hydrocarbon intelligence must be postponed, narrowed, or rejected.

---

## 3. Core Architectural & Product Principles

### 3.1 Trusted Data Before Intelligence

Analytics, forecasting, simulation recommendations, anomaly detection, and AI agents are only as reliable as the data beneath them. Hidra builds intelligence on validated, traceable, workflow-governed operational truth — never on raw ingestion alone.

### 3.2 One System, Multiple Phases

Hidra is one product vision. All modules described in this document belong to the architecture now. Phases define sequencing and dependency order, not legitimacy or importance.

### 3.3 Auditability by Design

Every operational decision must be traceable without exception:

```text
who acted
when they acted
what object was affected
what value changed
why the change happened
what workflow state was involved
what request/correlation context existed
```

### 3.4 Hidra Owns Its Simulation Engine

Hidra does not delegate runtime calculation to external engineering tools. **Hidra Simulation Core** is the internal system of calculation for pipeline networks. Commercial tools such as HYSYS, PIPESIM, OLGA, UniSim, and Synergi are benchmark references and validation baselines — not runtime dependencies.

### 3.5 Explainability First

Every risk score, anomaly indication, simulation-driven recommendation, and AI output must expose its data lineage, assumptions, and reasoning path. Hidra amplifies human decision-making; it does not hide it.

### 3.6 Safety and Compliance by Design

Alarm management, leak detection, HSE governance, custody accounting, environmental monitoring, and OT cybersecurity are not accessories. They are first-class platform concerns built into the architecture from the start.

### 3.7 Modular Monolith Strategic Architecture

Hidra is a modular monolith with strong internal boundaries. Modules communicate through explicit contracts, application services, ports, events, and governed read models. Deployment remains unified until scale, throughput, ownership, or lifecycle demands justify extraction.

### 3.8 Topology as the Operational Map

Topology is not just an asset registry. It is the canonical operational graph onto which telemetry, plans, monitoring rules, incidents, integrity assessments, simulation results, and analytics insights are mapped.

### 3.9 Workflow Coordinates, Domains Own

Workflow owns decisions, tasks, approvals, reasons, delegations, and escalations. It does not own telemetry values, topology state, planning facts, monitoring rules, or incident lifecycle data. Domains own facts. Workflow governs decisions over those facts.

### 3.10 Human-in-the-Loop Decision Governance

Hidra may recommend, alert, simulate, explain, forecast, and prioritize. It must not automatically actuate industrial equipment or silently replace accountable operational judgment.

### 3.11 Catalog-Backed Business Language

Business taxonomies must be catalog-backed, multilingual, and evolvable. Java enums are reserved for technical lifecycle states and internal machine state transitions where appropriate.

---

## 4. Product Personas

| Persona | Primary need | Hidra value |
|---|---|---|
| Operator | Observe or enter operational facts and measurements | Clear asset context and reduced ambiguity |
| Validator | Approve, reject, correct, or escalate trusted readings and decisions | Traceable workflow decisions and reasons |
| Planner | Define targets, nominations, and compare actuals | Trusted actuals and versioned plans |
| Supervisor | Monitor deviations, incidents, alarms, and follow-up | Operational visibility and accountability |
| Integrity engineer | Assess structural degradation and operational envelope | Contextualized risk and explainable integrity signals |
| Asset engineer | Schedule and track maintenance actions | Direct bridge from integrity findings to execution |
| HSE stakeholder | Track exposure, permits, incidents, and safety evidence | Unified safety and compliance posture |
| Simulation user | Run engineering what-if scenarios | Topology-driven, explainable simulation |
| Integration administrator | Connect SCADA, historians, IAM, files, and channels | Controlled mappings, retries, and external references |
| Auditor | Prove who did what and why | Append-only evidence and searchable traceability |
| Executive manager | Understand operational health and risk | KPIs, reports, trends, and governed intelligence |

---

## 5. Capability Maturity Semantics

The same capability may appear across multiple phases without duplication. In Hidra, that repetition means the capability is **maturing**, not being redefined.

Each capability evolves through one or more of these states:

```text
Prepare        -> references, interfaces, ports, audit fields, future-proof design
Operate        -> real operational workflow for real users
Industrialize  -> reliability, retry, audit, supportability, governance, connectors
Intelligize    -> explainable insights, analytics, projections, optimization readiness
```

Examples:

| Capability | Early phase meaning | Later phase meaning |
|---|---|---|
| Audit | Audit readiness and event preservation | Searchable evidence, integration audit, compliance analytics |
| Risk | Initial operational signals | Cross-layer historical risk analytics |
| Integration | References and readiness | Production-grade connectors and governance |
| Simulation | Explicitly absent early | Internal system of calculation and scenario engine |
| Reporting | Basic operational read views | Trusted reporting and executive insight |
| Digital twin | Not delivered early | Readiness built from trusted data and simulation inputs |

---

## 6. Process Architecture Blueprint

Hidra is structured into two complementary process layers:

1. **Core Operational Processes** — the execution engine of the platform; these model, measure, supervise, protect, maintain, and account for the physical hydrocarbon transportation network.
2. **Transversal Governance Processes** — the systemic backbone; these provide identity, workflow, audit, document control, configuration, simulation, risk synthesis, reporting, intelligence, and platform-wide governance.

---

## 7. Core Operational Processes

Core processes follow the operational lifecycle of a midstream pipeline system:

```text
[Topology] -> [Telemetry] -> [Planning] -> [Monitoring] -> [Alarm Management / Leak Detection] -> [Incident Management] -> [Network Integrity / Asset Management] -> [Custody Transfer] -> [HSE Management] -> [Integration]
```

| # | Process Module | Functional Mission | Boundary Rules & Isolation |
|---|---|---|---|
| 1 | **Topology** | Model, version, and visualize the pipeline infrastructure — segments, stations, equipment, measurement points, elevation, and network connections. | Canonical network state. All other modules reference topology IDs and structure for asset context. |
| 2 | **Telemetry** | Ingest, contextualize, validate, and qualify sensor readings and operational measurements. | Owns sources, devices, points, readings, quality, and state. It transforms raw measurements into trusted operational records. |
| 3 | **Planning** | Define targets, dispatch schedules, capacity plans, and operational expectations. | Owns planned state. Feeds planned-vs-actual comparison. Does not own actual telemetry facts. |
| 4 | **Monitoring** | Evaluate trusted telemetry against thresholds, plans, envelopes, and statistical rules. | Owns deviation detection and monitored state interpretation. Does not own alarm lifecycle. |
| 5 | **Alarm Management** | Rationalize, prioritize, acknowledge, shelve, resolve, and analyze alarms in alignment with ISA-18.2. | Owns the lifecycle of alarms emitted from monitoring logic. Does not define monitored limits. |
| 6 | **Leak Detection** | Detect, classify, locate, and escalate suspected or confirmed leak events using pressure balance, flow balance, statistics, and simulation-backed methods. | Owns leak hypotheses and leak cases. Converts high-severity signals into incident-ready operational evidence. |
| 7 | **Incident Management** | Govern operational problems from triage to closure, including investigation, action tracking, RCA, and remediation follow-up. | Owns incident lifecycle. May reference telemetry, alarms, leaks, documents, and audit evidence. |
| 8 | **Network Integrity** | Assess long-term asset health, degradation, MAOP-related safety context, operating envelope, and structural risk. | Diagnoses integrity condition and risk. Does not own maintenance execution. |
| 9 | **Asset Management** | Govern equipment lifecycle, inspections, maintenance schedules, work orders, and corrective actions. | Executes action on integrity and telemetry findings. Integrity diagnoses; assets act. |
| 10 | **Custody Transfer** | Validate, reconcile, and account for hydrocarbon delivery volumes at fiscal and custody transfer points. | Produces audit-ready commercial records from trusted measurements and contractual context. |
| 11 | **HSE Management** | Govern permit-to-work, safety observations, compliance events, HSE audits, and safety obligations. | Owns safety and compliance obligations. Coordinates with incidents but does not replace incident resolution. |
| 12 | **Integration** | Provide the controlled connectivity gateway to SCADA, historians, OPC UA, MQTT, IAM, files, ERP/EAM references, and external systems. | Owns connectors, mappings, retries, dead-letter handling, and external references. It must not bypass domain validation. |

---

## 8. Transversal Governance Processes

Transversal processes form a structured **foundation-to-intelligence** stack:

```text
[Identity / Organization] -> [Workflow / Audit] -> [Documents / Configuration / Notification] -> [Simulation / Risk] -> [Analytics / Reporting / Environment / OT Security / AI Agents]
```

| # | Process Module | Functional Mission | Boundary Rules & Isolation |
|---|---|---|---|
| 1 | **Identity** | Manage users, roles, permissions, authorities, and authenticated actor identity. | Root of actor truth. Every change in Hidra must bind to an accountable identity. |
| 2 | **Organization** | Model employees, units, departments, operational regions, and responsibility structure. | Provides business responsibility context and jurisdiction. |
| 3 | **Workflow** | Orchestrate approval, rejection, correction, delegation, escalation, and decision state machines. | Owns decision process, not domain facts. |
| 4 | **Audit** | Preserve append-only, tamper-evident evidence of operational and governance events. | Immutable proof layer. Records evidence; does not own business decisions. |
| 5 | **Document Management** | Govern controlled documents such as P&IDs, as-builts, certificates, procedures, and HSE evidence. | Links unstructured artifacts to assets, incidents, integrity findings, and compliance records. |
| 6 | **Configuration & Change Management** | Track, approve, and audit changes to thresholds, setpoints, topology parameters, and operational configuration. | Treats configuration change as a governed business event. |
| 7 | **Notification** | Deliver operational alerts, tasks, escalations, alarms, and workflow messages through channels. | Delivery engine only. Does not own business rule evaluation. |
| 8 | **Simulation** | Execute engineering calculations and scenario runs using Hidra Simulation Core. | Calculation oracle for planning, monitoring, integrity, and leak detection; never direct industrial control. |
| 9 | **Risk** | Synthesize cross-domain risk indicators from telemetry, monitoring, incidents, integrity, leak detection, HSE, and simulation. | Produces unified risk posture without owning source facts. |
| 10 | **Analytics** | Produce KPIs, trends, anomaly candidates, performance insight, and derived analytical views. | Strictly read-only over trusted history. |
| 11 | **Reporting** | Generate reports, exports, schedules, and formal operational documentation. | Structured output layer over trusted read models. |
| 12 | **Environmental Monitoring** | Track emissions, methane, flare indicators, and environmental impact metrics. | Supports compliance, reporting, and HSE evidence. |
| 13 | **OT Cybersecurity** | Detect threats, anomalous industrial behavior, and posture degradation across OT-connected environments. | Distinct from identity. Protects the industrial interface layer. |
| 14 | **AI Agents** | Deliver autonomous assistance, anomaly correlation, risk support, simulation-driven recommendations, and contextual insight. | Must remain explainable, traceable, and human-governed. |

---

## 9. Process Interaction Map

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                          TRANSVERSAL GOVERNANCE SPINE                       ║
║  Identity · Organization · Workflow · Audit · Document Management           ║
║  Configuration & Change Management · Notification · Simulation · Risk       ║
║  Analytics · Reporting · Environmental Monitoring · OT Cybersecurity        ║
║  AI Agents                                                                   ║
╚══════════════════════════╤═══════════════════════════════════════════════════╝
                           │ supports all operational processes
╔══════════════════════════╧═══════════════════════════════════════════════════╗
║                           CORE OPERATIONAL FLOW                              ║
║                                                                              ║
║  Topology ──► Telemetry ──► Workflow ──► Planning                           ║
║                               │                     └──► Monitoring          ║
║                               │                            │                 ║
║                               │                 ┌──────────┼──────────┐      ║
║                               │                 ▼          ▼          ▼      ║
║                               │            Alarm Mgmt   Leak Detection  │    ║
║                               │                 │          │           ▼     ║
║                               │                 └──────────┴──────► Incidents║
║                               │                                       │      ║
║                               │                    ┌──────────────────┴──┐   ║
║                               │                    ▼                     ▼   ║
║                               │              Network Integrity        HSE    ║
║                               │                    │                         ║
║                               │                    ▼                         ║
║                               │              Asset Management                ║
║                               │                                              ║
║                               └──────────────► Custody Transfer              ║
║                                                                              ║
║                         Integration ◄──► external industrial systems         ║
╚══════════════════════════════════════════════════════════════════════════════╝
```

---

## 10. Clean Architecture Module Naming Standards

All Java packages follow the canonical root:

```text
dz.sh.hidra.modules.<module>
```

Canonical module names:

```text
kernel              - stable IDs, value objects, base primitives, domain-event foundations
platform            - cross-cutting technical concerns: config, persistence support, security plumbing, observability
identity            - authenticated actors, roles, permissions, authorities
organization        - employees, units, regional scope, responsibility
topology            - pipelines, nodes, stations, equipment, graph structure
telemetry           - sources, devices, points, readings, ingestion, quality/state
workflow            - tasks, decisions, approval routes, delegation, escalation
planning            - plans, periods, nominations, targets, planned-vs-actual baselines
monitoring          - rules, thresholds, envelopes, deviation detection
alarms              - alarm lifecycle, shelving, acknowledgement, priority, performance
leakdetection       - leak hypotheses, localization, leak event evidence
incidents           - incident lifecycle, triage, RCA, remediation
integrity           - degradation, envelope compliance, structural risk
assets              - maintenance schedules, inspections, work orders, lifecycle execution
custody             - fiscal metering, reconciliation, commercial records
hse                 - permit-to-work, safety events, compliance evidence
integration         - connectors, mappings, jobs, retries, external references
audit               - append-only evidence, before/after traceability
documents           - controlled documents, attachments, certificates, procedures
configuration       - governed configuration and change history
notification        - templates, channels, delivery orchestration
simulation          - Hidra Simulation Core, scenarios, runs, solver metadata, results
risk                - risk signals, indicators, cross-domain synthesis
analytics           - KPI calculation, trends, projections, anomaly candidates
reporting           - report definitions, schedules, exports
environment         - emissions, flare indicators, environmental metrics
otsecurity          - OT/ICS security posture, anomaly and threat visibility
agents              - explainable AI assistance and recommendation orchestration
```

---

## 11. Data Dependency Execution Phases

Modules are delivered in sequence because **intelligence depends on trust**.

```text
[Phase 1: Data Trust] -> [Phase 2: Live Operations] -> [Phase 3: Safety, Simulation & Integrity] -> [Phase 4: Commercial & Integration] -> [Phase 5: Intelligence & Compliance]
```

### Phase 1 — Trusted Operational Data Foundation

**Gate Criterion:** Telemetry can be ingested, tied to topology, validated, workflow-governed, and fully traceable.

**Modules Delivered:**
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

### Phase 2 — Live Operations

**Gate Criterion:** Trusted readings can be compared to plans, monitored in real time, surfaced as alarms, and followed operationally.

**Modules Delivered:**
```text
planning
monitoring
alarms
incidents
risk (signals)
audit (hardening)
notification (basic)
configuration (basic)
```

### Phase 3 — Safety, Simulation, and Integrity

**Gate Criterion:** Hidra Simulation Core supports operationally relevant scenarios, leak detection is governed, integrity is assessed, and safety/compliance are tracked.

**Modules Delivered:**
```text
leakdetection
hse
simulation
integrity
assets
documents (basic)
notification (full)
risk (simulation-driven)
```

### Phase 4 — Commercial Accounting and Enterprise Integration

**Gate Criterion:** Custody records are auditable and Hidra exchanges data with enterprise and industrial systems without bypassing validation.

**Modules Delivered:**
```text
custody
integration
documents (full)
configuration (full)
```

### Phase 5 — Intelligence and Compliance

**Gate Criterion:** Trusted history produces explainable analytics, reports, environmental insights, OT security visibility, and AI-assisted intelligence.

**Modules Delivered:**
```text
analytics
reporting
environment
otsecurity
agents
```

---

## 12. Phase × Process Coverage Matrix

| Process | Phase 1: Foundation | Phase 2: Operations | Phase 3: Physics & Safety | Phase 4: Integration | Phase 5: Intelligence |
|---|---|---|---|---|---|
| Topology | ✅ Core graph setup | Operational mapping | Simulation mesh input | External references | Digital twin readiness |
| Telemetry | ✅ Ingestion & validation | Monitoring feed | Simulation calibration | Industrial ingestion | Analytics input |
| Workflow | ✅ Validation flow | Operational escalation | Simulation governance | Integration-safe approvals | Workflow analytics |
| Planning | — | ✅ Targets & versions | Simulation-informed | External sync if needed | Forecasting input |
| Monitoring | — | ✅ Thresholds & deviations | Look-ahead support | External event exchange | Anomaly input |
| Alarm Management | — | ✅ Alarm lifecycle | Correlative support | Channel integration | AI-assisted rationalization |
| Leak Detection | — | Signal preparation | ✅ Full leak workflow | Hardened exchange | AI-enhanced correlation |
| Incident Management | — | ✅ Incident lifecycle | Simulation post-mortem | Enterprise references | RCA analytics |
| Network Integrity | — | Early indicators | ✅ Full structural assessment | Hardening | Risk analytics |
| Asset Management | — | — | ✅ Maintenance execution | EAM sync readiness | Predictive maintenance support |
| Custody Transfer | — | — | — | ✅ Fiscal records | Commercial analytics |
| HSE Management | — | — | ✅ Safety workflows | Regulatory exchange | Compliance analytics |
| Integration | — | — | Readiness / benchmarks | ✅ Full connectors | Hardened governance |
| Identity | ✅ Core | Hardening | — | IAM federation | Security KPIs |
| Organization | ✅ Core | Responsibility hardening | — | External references | Org insight |
| Audit | ✅ Readiness | ✅ Hardened evidence | Simulation / leak trace | Integration audit | Compliance analytics |
| Document Management | — | — | ✅ Basic linkage | ✅ Full control | Regulatory reporting |
| Configuration Mgmt | — | ✅ Basic governance | Extended controls | ✅ Full change workflow | Policy analytics |
| Notification | — | ✅ Basic routing | Full multi-channel | Channel integration | Intelligent routing |
| Simulation | — | — | ✅ Native core | Benchmark exchange | Optimization inputs |
| Risk | — | ✅ Signals | Simulation-driven synthesis | — | ✅ Full analytics |
| Analytics | — | Basic read views | Simulation metrics | Integration history | ✅ Full explainable analytics |
| Reporting | — | — | — | Operational exports | ✅ Executive reporting |
| Environmental Monitoring | — | — | — | — | ✅ Emissions/compliance |
| OT Cybersecurity | — | — | — | — | ✅ OT protection visibility |
| AI Agents | — | — | — | — | ✅ Contextual assistance |

---

## 13. Data Vision

Hidra separates five major categories of data:

```text
transactional data
telemetry / time-series data
audit data
outbox / event data
projection / reporting / analytics data
```

### 13.1 Transactional Data

Transactional data is the initial system of record for operational modules such as identity, organization, topology, telemetry metadata, workflow, planning, monitoring, incidents, custody, HSE, configuration, and simulation scenario metadata.

### 13.2 Telemetry and Time-Series Data

Validated readings may begin in the transactional store with historian references. Dedicated time-series storage may be introduced when data retention, ingestion rate, or analytical performance requires it.

### 13.3 Audit Data

Audit data is append-only. It must preserve actor, action, target, timestamp, correlation, reason, workflow context, and before/after traceability wherever applicable.

### 13.4 Outbox and Event Data

Outbox records and durable event publication support reliable internal and external communication without coupling operational transactions to delivery infrastructure.

### 13.5 Projections, Reporting, and Analytics Data

Dashboards, KPI stores, analytical projections, and reporting read models are derived data. They may be rebuilt and must never become the source of operational truth.

---

## 14. Security, Observability, and Governance

### 14.1 Security Vision

Hidra security must support internal authentication, future enterprise IAM integration, RBAC, policy-driven authorization, organization-scoped permissions, authority codes, groups, segregation of duties, and auditable permission decisions.

Authorization must remain centralized and explicit. Business access rules must not be scattered across controllers or adapters.

### 14.2 Observability Vision

Hidra must produce structured logs, metrics, traces, correlation IDs, request IDs, actor IDs, module names, operation names, health checks, readiness checks, integration job monitoring, ingestion monitoring, and audit visibility.

Sensitive data must never be logged.

### 14.3 Governance Vision

Governance is not a compliance afterthought. It is a product requirement. Every state-changing decision that affects trusted operational truth must be explainable, traceable, recoverable, and attributable.

---

## 15. Quantifiable Success Invariants

Hidra succeeds when:

1. **Topology Dependability** — the network topology is the reliable, versioned source of physical truth.
2. **Telemetry Authenticity** — trusted measurements are validated before they enter high-level decision workflows.
3. **Decision Traceability** — every workflow decision, configuration change, or operational override is auditable.
4. **Planning Accountability** — planned targets are continuously compared with validated actuals.
5. **Proactive Monitoring** — operational deviations are detected before they become critical failures.
6. **Alarm Rationalization** — alarms are governed and performance-tracked in line with ISA-18.2 principles.
7. **Deterministic Leak Handling** — leak signals are processed, verified, and escalated with full traceability.
8. **Closed-Loop Incident Governance** — incidents move from detection to closure with accountable follow-up.
9. **Integrity-to-Execution Linkage** — integrity findings result in governed asset actions and maintenance work.
10. **Defensible Custody Accounting** — custody events produce validated, auditable, dispute-resistant records.
11. **Sovereign Simulation Capability** — Hidra Simulation Core produces explainable engineering results validated against references.
12. **Explainable Intelligence Delivery** — analytics, risk, and AI outputs reveal their reasoning and source lineage.
13. **Environmental Visibility** — emissions and environmental indicators are measurable and reportable.
14. **OT Protection Awareness** — cybersecurity posture and anomalies are visible across the industrial interface layer.

---

## 16. System Boundaries (What Hidra Is Not)

To preserve focus and prevent scope creep, Hidra is **not**:

- A SCADA control system
- A valve or pump actuation platform
- A full historian replacement
- A generic BPM engine
- A generic enterprise data lake
- A black-box AI decision engine
- A commercial simulator wrapper
- A default microservices platform
- A dashboard-only product
- A full ERP or EAM replacement
- A complete digital twin by default

Hidra may integrate with these domains, prepare for them, or complement them — but it must not pretend to own them prematurely.

---

## 17. Open Product Decisions

These decisions should be made deliberately before the related phase is implemented:

1. Which operational networks are first priority for simulation: gas, crude oil, multiproduct, or multiphase?
2. What simulation accuracy and runtime tolerance must Hidra Simulation Core achieve before operational trust is granted?
3. Which benchmark tools and benchmark cases will validate Hidra Simulation Core?
4. Which SCADA and historian connectors are first priority in Phase 4?
5. Which KPIs are mandatory for executive, operational, and engineering dashboards?
6. Which workflows require strict segregation of duties?
7. Which audit fields are legally or operationally mandatory for Sonatrach and TRC?
8. When, if ever, should a module be extracted from the modular monolith?
9. What environmental indicators are mandatory in the first compliance release?
10. What OT security visibility is required before external industrial connectivity is considered production-ready?

---

## 18. High-Level Guiding Test

When evaluating any feature request, architecture decision, integration, or module design, ask:

> **Does this directly help Hidra generate trusted, validated, auditable, and risk-aware hydrocarbon intelligence for Sonatrach pipeline operations?**

- **If yes:** it belongs in architectural review and phase planning.
- **If no:** narrow it, postpone it, or reject it.

---

*Hidra Master Product Vision — v1.5 Master — 2026-06-10*