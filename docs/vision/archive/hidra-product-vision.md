# Hidra Product Vision

## 1. Document Status

| Field | Value |
|---|---|
| Product | Hidra |
| Meaning | Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Document type | Product vision |
| Version | 0.3 Draft |
| Status | For discussion |
| Target repository | HidraAPI |
| Reference repositories | HyFloAPI, NGHyFloAPI |
| Canonical package root | `dz.sh.hidra` |
| Foundation module name | `kernel` |
| Identity module name | `identity` |
| V1 scope document | `docs/vision/hidra-v1-scope.md` |

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

## 5. Product Principles

### 5.1 Trusted Data Before Intelligence

Analytics, AI, forecasting, reporting, and digital twin capabilities must be built only after topology, telemetry, validation workflow, audit readiness, and operational monitoring are reliable.

### 5.2 Modular but Not Distributed Too Early

The first target architecture is a modular monolith.

Modules must be cleanly separated, but deployment remains simple until business boundaries and operational load justify extraction.

### 5.3 Auditability by Design

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

---

## 6. Core Product Pillars

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

## 7. Hidra V1 Scope

The full V1 scope is defined in:

```text
docs/vision/hidra-v1-scope.md
```

Hidra V1 is:

```text
Trusted Operational Data Foundation
```

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

V1 objective:

```text
Model infrastructure, collect and contextualize telemetry readings, validate them through workflow,
and preserve audit-ready decision traceability.
```

V1 explicitly excludes:

```text
full SCADA control
full historian replacement
planning implementation
monitoring implementation
incident lifecycle implementation
full audit hardening
integration platform implementation
analytics/reporting platform
notification delivery platform
digital twin
AI forecasting
microservices extraction
```

---

## 8. Post-V1 Scope

Post-V1 is split into horizons:

### V1.1 / Horizon 2 — Operations

```text
planning
monitoring
alerts
incidents
risk signals
audit hardening
basic dashboards
```

### V2 / Horizon 3 — Integration

```text
integration
SCADA
historian
OPC UA
MQTT
enterprise IAM
notification channels
file import/export
external references
```

### V3 / Horizon 4 — Intelligence

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

## 9. First Implementation Order

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

## 10. Success Criteria

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

## 11. Guiding Question

When evaluating any future feature, ask:

> Does this help Hidra create trusted hydrocarbon intelligence from data, risk, and analytics?

If yes, it belongs in the product vision.

If no, postpone it.
