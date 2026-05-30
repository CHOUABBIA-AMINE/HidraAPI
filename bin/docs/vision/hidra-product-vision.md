# Hidra Product Vision

## 1. Document Status

| Field | Value |
|---|---|
| Product | Hidra |
| Meaning | Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Document type | Product vision |
| Version | 0.1 Draft |
| Status | For discussion |
| Target repository | HidraAPI |
| Reference repositories | HyFloAPI, NGHyFloAPI |

---

## 2. Vision Statement

Hidra is a hydrocarbon intelligence platform designed for Sonatrach pipeline operations.

Hidra means **Hydrocarbon Intelligence for Data, Risk, and Analytics**.

Its purpose is to create trusted operational truth by connecting pipeline topology, hydrocarbon flow data, telemetry, validation workflows, planning, monitoring, alerts, incidents, risk, audit, and analytics into one coherent operational intelligence system.

Hidra does not simply store operational data. It transforms data into validated, traceable, risk-aware, and actionable intelligence.

---

## 3. Conducting Wire

> Hidra transforms hydrocarbon operational data into trusted, validated, auditable, risk-aware intelligence for Sonatrach.

This sentence is the strategic center of the product.

Every feature, module, architecture decision, validation rule, and technical standard must support this mission.

---

## 4. Why Hidra Exists

Sonatrach pipeline operations require reliable answers to operational questions such as:

- What infrastructure exists?
- Where is hydrocarbon flow measured?
- What data is being collected?
- Is the data complete?
- Is the value normal?
- Who entered, received, corrected, or validated the data?
- Was the value approved or rejected?
- Does actual flow match the plan?
- Is there a threshold violation?
- Is there an operational risk?
- Is there an alert or incident?
- What decision was made?
- Can the decision be proven later?
- What can analytics reveal from trusted history?

Hidra exists to answer these questions in a structured, secure, auditable, risk-aware, and operationally useful way.

---

## 5. Product Identity

Hidra is not only:

- a CRUD application
- a Java backend
- a telemetry database
- a dashboard tool
- a reporting module
- a SCADA replacement
- an ERP module

Hidra is:

> an enterprise hydrocarbon intelligence platform focused on data, risk, analytics, and Sonatrach pipeline operations.

It complements industrial systems such as SCADA, historians, telemetry gateways, reporting tools, and enterprise identity systems. It does not replace them immediately.

---

## 6. Repository Roles

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

## 7. Product Principles

### 7.1 Business First

Hidra must be guided by pipeline operations, data trust, risk awareness, and analytics value, not by frameworks, packages, or technical fashion.

Architecture exists to protect business truth.

### 7.2 Trusted Data Before Intelligence

Analytics, AI, forecasting, and digital twin capabilities must be built only after topology, telemetry, validation, workflow, and audit are reliable.

### 7.3 Auditability by Design

Every operational decision must be traceable:

- who acted
- when they acted
- what object was affected
- what value changed
- why the change happened
- what workflow state was involved
- what correlation/request context existed

### 7.4 Risk Awareness by Design

Hidra must not only say what happened. It must help evaluate whether the situation creates operational risk.

Risk may come from:

- abnormal flow values
- missing readings
- delayed validation
- repeated corrections
- topology inconsistency
- equipment status changes
- threshold violations
- unresolved incidents
- planning deviations

### 7.5 Modular but Not Distributed Too Early

The first target architecture should be a modular monolith.

Modules must be cleanly separated, but deployment should remain simple until business boundaries and operational load justify extraction.

### 7.6 Integration-Ready, Not Integration-Dependent

Hidra must be ready for SCADA, historian, OPC UA, MQTT, REST, file import/export, and enterprise IAM integration, but the core domain must remain usable even before all external integrations exist.

### 7.7 Sonatrach-Specific Value

Hidra must not blindly copy Siemens, Honeywell, ABB, AVEVA, PI System, Ignition, or Kepware.

It should learn from industrial leaders while remaining focused on Sonatrach operational reality.

---

## 8. Core Product Pillars

| Pillar | Purpose |
|---|---|
| Topology | Model pipeline infrastructure, segments, stations, equipment, and measurement locations |
| Data | Capture, structure, validate, and preserve operational hydrocarbon data |
| Telemetry | Capture or ingest flow readings and sensor measurements |
| Validation | Confirm, correct, approve, or reject operational data |
| Planning | Define expected flow targets and operational plans |
| Monitoring | Compare actual state against expected values and thresholds |
| Risk | Detect operational exposure, deviations, weak signals, and unresolved problems |
| Alerts | Detect and escalate deviations requiring attention |
| Incidents | Manage operational problems from detection to resolution |
| Audit | Preserve traceability and decision proof |
| Analytics | Turn trusted history into insights, KPIs, trends, and future intelligence |
| Integration | Connect Hidra to SCADA, historians, IAM, notification, and external systems |

---

## 9. Target Users

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

## 10. Operational Story

The future Hidra operational story is:

1. Sonatrach pipeline topology is modeled accurately.
2. Hydrocarbon operational data is entered manually or ingested automatically.
3. Each reading is associated with a pipeline, segment, station, equipment, measurement location, timestamp, source, unit, and quality state.
4. The reading enters a validation lifecycle.
5. Authorized actors validate, correct, approve, or reject the reading.
6. Validated flow is compared against operational plans, thresholds, and risk rules.
7. Deviations create alerts, monitoring events, or risk signals.
8. Serious deviations become incidents.
9. Incidents are tracked through classification, response, escalation, root cause, and resolution.
10. Every action is audited.
11. Trusted history feeds dashboards, KPIs, analytics, forecasting, and digital twin readiness.

---

## 11. In-Scope Capabilities

### 11.1 Foundation Scope

- Identity and access management
- Organization and employee structure
- Role, permission, authority, and group model
- Pipeline topology model
- Infrastructure and equipment registry
- Flow reading model
- Validation lifecycle
- Audit trail
- API and validation standards
- Architecture and testing standards

### 11.2 Operational Scope

- Flow plans
- Planned vs actual comparison
- Thresholds
- Monitoring rules
- Alerts
- Incident lifecycle
- Risk indicators
- Workflow approvals
- Notifications
- Operational dashboards

### 11.3 Integration Scope

- SCADA integration readiness
- Historian integration readiness
- OPC UA readiness
- MQTT readiness
- REST APIs
- File import/export
- Batch ingestion
- External reference tracking

### 11.4 Intelligence Scope

- KPI projections
- Trend analysis
- Anomaly readiness
- Forecasting readiness
- Digital twin readiness
- Operational intelligence reports
- Risk analytics

---

## 12. Out of Scope for the First Version

The first implementation should not attempt to deliver everything.

The following are future capabilities, not initial blockers:

- full real-time SCADA control
- replacing existing industrial control systems
- automatic control of valves or pumps
- advanced AI decision automation
- full digital twin simulation
- microservices decomposition
- enterprise-wide data lake replacement
- complex optimization engines

---

## 13. Success Criteria

Hidra succeeds when:

- topology is reliable
- operational data is trusted
- validation is traceable
- plans can be compared to actual values
- deviations and risk signals are detected
- alerts and incidents are managed
- audit evidence is complete
- users trust the data
- supervisors can make faster decisions
- historical data supports operational improvement
- analytics are based on validated operational truth

---

## 14. Strategic Roadmap Horizons

### Horizon 1 — Foundation

Goal: create trusted operational data.

Focus:

- identity
- organization
- topology
- telemetry
- validation
- audit

### Horizon 2 — Operations

Goal: support daily pipeline operations.

Focus:

- planning
- monitoring
- alerts
- incidents
- workflow
- risk signals
- dashboards

### Horizon 3 — Integration

Goal: connect to industrial systems.

Focus:

- SCADA
- historian
- OPC UA
- MQTT
- enterprise IAM
- notification channels

### Horizon 4 — Intelligence

Goal: transform trusted history into insight.

Focus:

- KPIs
- trends
- forecasting
- anomaly detection
- root cause support
- risk analytics
- digital twin readiness

---

## 15. Guiding Question

When evaluating any future feature, ask:

> Does this help Hidra create trusted hydrocarbon intelligence from data, risk, and analytics?

If yes, it belongs in the product vision.

If no, postpone it.

---

## 16. Open Discussion Questions

1. Should Hidra focus first on manual validated readings, automated SCADA ingestion, or both?
2. Which topology level is most important for the first version: pipeline, segment, station, equipment, or measurement point?
3. What is the exact validation lifecycle used in current operations?
4. Who are the real validation actors and decision authorities?
5. What is the minimum dashboard that would create immediate operational value?
6. Which integrations are urgent, and which are future readiness only?
7. What data must be auditable for compliance and internal accountability?
8. Should risk indicators be part of monitoring, incidents, or a separate risk context?
