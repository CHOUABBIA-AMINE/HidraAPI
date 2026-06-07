# Hidra Product Vision

## 1. Document Status

| Field | Value |
|---|---|
| Product | Hidra |
| Meaning | Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Document type | Product vision |
| Version | 0.2 Draft |
| Status | For discussion |
| Target repository | HidraAPI |
| Reference repositories | HyFloAPI, NGHyFloAPI |
| Canonical package root | `dz.sh.hidra` |
| Foundation module name | `kernel` |
| Identity module name | `identity` |

---

## 2. Vision Statement

Hidra is a hydrocarbon intelligence platform designed for Sonatrach pipeline operations.

Hidra means **Hydrocarbon Intelligence for Data, Risk, and Analytics**.

Its purpose is to create trusted operational truth by connecting pipeline topology, hydrocarbon operational data, telemetry, validation workflow, planning, monitoring, alerts, incidents, risk, audit, integration, reporting, and analytics into one coherent operational intelligence system.

Hidra does not simply store operational data. It transforms data into validated, traceable, risk-aware, and actionable intelligence.

---

## 3. Conducting Wire

> Hidra transforms hydrocarbon operational data into trusted, validated, auditable, risk-aware intelligence for Sonatrach.

This sentence is the strategic center of the product.

Every feature, module, architecture decision, validation rule, and technical standard must support this mission.

---

## 4. Naming Standard

The implemented HidraAPI module names are:

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
audit
integration
analytics
reporting
notification
```


The canonical Java package root is:

```text
dz.sh.hidra
```

---

## 5. Why Hidra Exists

Sonatrach pipeline operations require reliable answers to operational questions such as:

```text
What infrastructure exists?
Where is hydrocarbon flow measured?
What data is being collected?
Is the data complete?
Is the value normal?
Who entered, received, corrected, or validated the data?
Was the value approved or rejected?
Does actual flow match the plan?
Is there a threshold violation?
Is there an operational risk?
Is there an alert or incident?
What decision was made?
Can the decision be proven later?
What can analytics reveal from trusted history?
```

Hidra exists to answer these questions in a structured, secure, auditable, risk-aware, and operationally useful way.

---

## 6. Product Identity

Hidra is not only:

```text
a CRUD application
a Java backend
a telemetry database
a dashboard tool
a reporting module
a SCADA replacement
an ERP module
```

Hidra is:

> an enterprise hydrocarbon intelligence platform focused on data, risk, analytics, and Sonatrach pipeline operations.

It complements industrial systems such as SCADA, historians, telemetry gateways, reporting tools, and enterprise identity systems. It does not replace them immediately.

---

## 7. Repository Roles

| Repository | Role | Mutation policy |
|---|---|---|
| HyFloAPI | Legacy HyFlo business memory and domain reference | Read-only reference |
| NGHyFloAPI | New-generation HyFlo architecture laboratory, standards reference, roadmap lessons, and implementation cautions | Read-only reference |
| HidraAPI | Clean target repository for Hidra backend/API implementation | Mutable target |

HyFloAPI helps answer:

> What did we already understand about Sonatrach hydrocarbon flow operations?

NGHyFloAPI helps answer:

> What did we learn about how to build the new generation correctly?

HidraAPI answers:

> What should the future Hidra backend and platform foundation become?

---

## 8. Product Principles

### 8.1 Business First

Hidra must be guided by pipeline operations, data trust, risk awareness, and analytics value, not by frameworks, packages, or technical fashion.

Architecture exists to protect business truth.

### 8.2 Trusted Data Before Intelligence

Analytics, AI, forecasting, reporting, and digital twin capabilities must be built only after topology, telemetry, validation workflow, audit readiness, and operational monitoring are reliable.

### 8.3 Auditability by Design

Every operational decision must be traceable:

```text
who acted
when they acted
what object was affected
what value changed
why the change happened
what workflow state was involved
what correlation/request context existed
```

### 8.4 Risk Awareness by Design

Hidra must not only say what happened. It must help evaluate whether the situation creates operational risk.

Risk may come from:

```text
abnormal flow values
missing readings
delayed validation
repeated corrections
topology inconsistency
equipment status changes
threshold violations
unresolved incidents
planning deviations
```

### 8.5 Modular but Not Distributed Too Early

The first target architecture is a modular monolith.

Modules must be cleanly separated, but deployment remains simple until business boundaries and operational load justify extraction.

### 8.6 Integration-Ready, Not Integration-Dependent

Hidra must be ready for SCADA, historian, OPC UA, MQTT, REST, file import/export, and enterprise IAM integration, but the core domain must remain usable before all external integrations exist.

### 8.7 Sonatrach-Specific Value

Hidra must learn from industrial leaders while remaining focused on Sonatrach operational reality.

---

## 9. Core Product Pillars

| Pillar | Purpose |
|---|---|
| Identity | Manage users, roles, permissions, authorities, groups, and access readiness |
| Organization | Model employees, units, regions, departments, assignments, and responsibility structure |
| Topology | Model pipeline infrastructure, segments, stations, equipment, and measurement locations |
| Telemetry | Capture or ingest readings and sensor measurements as operational facts |
| Workflow | Orchestrate validation, approval, rejection, correction, delegation, and escalation |
| Planning | Define expected flow targets and operational plans |
| Monitoring | Compare validated actual state against expected values and thresholds |
| Incidents | Manage operational problems from detection to resolution |
| Audit | Preserve traceability and decision proof |
| Risk | Detect operational exposure, deviations, weak signals, and unresolved problems |
| Integration | Connect Hidra to SCADA, historians, IAM, notification, and external systems |
| Analytics | Turn trusted history into insights, KPIs, trends, and future intelligence |
| Reporting | Produce operational reports and exports from trusted data |
| Notification | Deliver task, alert, incident, and escalation notifications |

---

## 10. Target Users

| User type | Main needs |
|---|---|
| Operator | Enter, review, or monitor hydrocarbon flow data |
| Validator | Validate, correct, approve, or reject readings |
| Planner | Define expected flow plans and operational targets |
| Supervisor | Monitor deviations, alerts, incidents, and operational performance |
| Risk / HSE stakeholder | Understand exposure, abnormal situations, and unresolved operational risk |
| Administrator | Manage users, roles, permissions, organizational structure, and configuration |
| Auditor / Compliance user | Review traceability, decisions, and historical evidence |
| Integration administrator | Configure external systems, ingestion jobs, mappings, and synchronization |
| Executive / Manager | View KPIs, trends, summaries, and operational intelligence |

---

## 11. Operational Story

The future Hidra operational story is:

```text
1. Sonatrach pipeline topology is modeled accurately.
2. Hydrocarbon operational data is entered manually or ingested automatically.
3. Each reading is associated with a pipeline, segment, station, equipment, measurement location, timestamp, source, unit, and quality state.
4. Telemetry stores the reading as an operational fact and protects reading invariants.
5. Workflow orchestrates validation, correction, approval, rejection, delegation, and escalation tasks around the reading.
6. Authorized actors perform workflow decisions.
7. Audit records actor, target, decision, before/after values, reason, workflow state, correlation id, and request id.
8. Validated/approved flow is compared against operational plans, thresholds, and risk rules.
9. Deviations create monitoring events, alerts, or risk signals.
10. Serious deviations become incidents.
11. Incidents are tracked through classification, response, escalation, root cause, and resolution.
12. Trusted history feeds dashboards, KPIs, analytics, forecasting, and digital twin readiness.
```

---

## 12. In-Scope Capabilities

### 12.1 Foundation Scope

```text
kernel primitives
platform foundation
identity and access management
organization and employee structure
role, permission, authority, and group model
pipeline topology model
infrastructure and equipment registry
telemetry reading model
workflow-based validation lifecycle
audit event readiness
API and validation standards
architecture and testing standards
```

### 12.2 Operational Scope

```text
workflow approvals
flow plans
planned vs actual comparison
thresholds
monitoring rules
alerts
incident lifecycle
risk indicators
notifications
operational dashboards
```

### 12.3 Integration Scope

```text
SCADA integration readiness
historian integration readiness
OPC UA readiness
MQTT readiness
REST APIs
file import/export
batch ingestion
external reference tracking
```

### 12.4 Intelligence Scope

```text
KPI projections
trend analysis
anomaly readiness
forecasting readiness
digital twin readiness
operational intelligence reports
risk analytics
```

---

## 13. Out of Scope for the First Version

The first implementation should not attempt to deliver everything.

Future capabilities, not initial blockers:

```text
full real-time SCADA control
replacing existing industrial control systems
automatic control of valves or pumps
advanced AI decision automation
full digital twin simulation
microservices decomposition
enterprise-wide data lake replacement
complex optimization engines
```

---

## 14. Success Criteria

Hidra succeeds when:

```text
topology is reliable
operational data is trusted
workflow validation is traceable
plans can be compared to actual values
deviations and risk signals are detected
alerts and incidents are managed
audit evidence is complete
users trust the data
supervisors can make faster decisions
historical data supports operational improvement
analytics are based on validated operational truth
```

---

## 15. Strategic Roadmap Horizons

### Horizon 1 — Foundation

Goal: create trusted operational data.

Focus:

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

### Horizon 2 — Operations

Goal: support daily pipeline operations.

Focus:

```text
planning
monitoring
alerts
incidents
risk signals
audit hardening
dashboards
```

### Horizon 3 — Integration

Goal: connect to industrial systems.

Focus:

```text
integration
SCADA
historian
OPC UA
MQTT
enterprise IAM
notification channels
```

### Horizon 4 — Intelligence

Goal: transform trusted history into insight.

Focus:

```text
analytics
reporting
KPIs
trends
forecasting
anomaly detection
root cause support
risk analytics
digital twin readiness
```

---

## 16. First Implementation Order

```text
1. repository skeleton
2. kernel
3. platform foundation
4. identity
5. organization
6. topology
7. telemetry
8. workflow
9. planning
10. monitoring
11. incidents
12. audit hardening
13. integration
14. analytics/reporting
15. notification
```

---

## 17. Guiding Question

When evaluating any future feature, ask:

> Does this help Hidra create trusted hydrocarbon intelligence from data, risk, and analytics?

If yes, it belongs in the product vision.

If no, postpone it.

---

## 18. Open Discussion Questions

```text
1. Should Hidra focus first on manual validated readings, automated SCADA ingestion, or both?
2. Which topology level is most important for the first version: pipeline, segment, station, equipment, or measurement point?
3. What is the exact validation workflow used in current operations?
4. Who are the real validation actors and decision authorities?
5. What is the minimum dashboard that would create immediate operational value?
6. Which integrations are urgent, and which are future readiness only?
7. What data must be auditable for compliance and internal accountability?
8. Should risk indicators be part of monitoring, incidents, analytics, or a separate later risk context?
