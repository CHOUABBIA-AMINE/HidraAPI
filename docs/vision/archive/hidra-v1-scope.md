# Hidra V1 Scope and Post-V1 Scope

```text
Document code : HIDRA-V1-SCOPE
Task          : HIDRA-V1-SCOPE — docs(vision): define Hidra V1 and post-V1 scope
Repository    : HidraAPI
Namespace     : dz.sh.hidra
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Document type : Product scope definition
Author        : Abir MEDJERAB
CreatedOn     : 2025-06-26
UpdatedOn     : 2026-06-07
Status        : Draft for discussion
```

---

## 1. Purpose

This document defines the scope of **Hidra V1** and the scope after V1.

It exists to prevent scope creep and to separate:

```text
what Hidra V1 must deliver
what Hidra V1 must prepare for
what Hidra V1 must not try to deliver
what belongs after V1
```

The core decision:

```text
Hidra V1 = Trusted Operational Data Foundation
```

Hidra V1 is not the complete final Hidra platform.

---

## 2. Product principle

Hidra's vision is to transform hydrocarbon operational data into trusted, validated, auditable, risk-aware intelligence for Sonatrach.

The correct sequence is:

```text
trusted data
validated data
auditable decisions
operational monitoring
risk awareness
analytics and intelligence
```

Therefore V1 must focus on trust before intelligence.

---

## 3. Current repository baseline

Based on the HidraAPI implementation path so far, the repository has already reached or prepared:

```text
kernel
platform foundation
identity
organization
topology
telemetry
telemetry corrected baseline
workflow detailed roadmap
module order correction
module naming correction
```

The next implementation module after telemetry is:

```text
workflow
```

The dependency-driven architecture order is:

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

## 4. Hidra V1 definition

### 4.1 V1 name

```text
Hidra V1 — Trusted Operational Data Foundation
```

### 4.2 V1 objective

Hidra V1 must let Sonatrach establish trusted operational data by answering:

```text
Who is the actor?
Which organization unit is responsible?
What pipeline infrastructure exists?
Where is the measurement taken?
Which telemetry source, device, and point produced the reading?
What was the reading value?
What is its quality/state?
Who validated it?
Who rejected it?
Who requested correction?
Why was it approved, rejected, delegated, escalated, or corrected?
Can the decision be proven later?
```

### 4.3 V1 product outcome

At the end of V1, Hidra should be able to say:

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

---

## 5. Hidra V1 modules

Hidra V1 includes:

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

### 5.1 kernel

Scope:

```text
stable IDs
base value objects
domain/application markers
pagination primitives
correlation/request references
```

Out of scope:

```text
business aggregates
module-specific DTOs
JPA entities
controllers
utility dumping ground
```

### 5.2 platform

Scope:

```text
configuration
exception handling
observability
security plumbing
persistence support
API conventions
testing conventions
```

Out of scope:

```text
business rules
business aggregates
module-specific workflows
```

### 5.3 identity

Scope:

```text
users
roles
permissions
authorities
groups
access readiness
actor identity reference
```

Out of scope for V1:

```text
advanced IAM federation
enterprise-wide SSO hardening
complex ABAC policy engine
full identity governance
```

### 5.4 organization

Scope:

```text
employees
organization units
departments/regions
positions
assignments
responsibility structure
actor-to-organization context
```

Out of scope for V1:

```text
full HR system replacement
payroll
performance management
complex HR workflows
```

### 5.5 topology

Scope:

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

V1 topology answers:

```text
What infrastructure exists?
Where is the reading attached?
What asset context does telemetry refer to?
```

Out of scope for V1:

```text
full GIS system
3D topology modeling
hydraulic simulation
digital twin topology engine
```

### 5.6 telemetry

Scope:

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

V1 telemetry answers:

```text
What reading was received?
From which source/device/point?
When was it produced and received?
What value was recorded?
What quality/state does it have?
```

Out of scope for V1:

```text
high-volume streaming engine
full historian replacement
full SCADA control integration
advanced anomaly detection
```

### 5.7 workflow

Scope:

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

V1 workflow answers:

```text
Who must review this reading?
Who acted?
What decision was made?
Why was the decision made?
What workflow state changed?
```

Out of scope for V1 workflow:

```text
generic BPM engine
parallel approval graph
workflow designer UI
workflow over incidents/planning/risk
workflow-owned telemetry state
workflow-owned corrected reading values
```

### 5.8 audit readiness

V1 includes audit readiness, not full audit hardening.

V1 audit readiness means:

```text
define audit-ready event payloads
define WorkflowAuditEventPort
preserve actor/action/target/reason/timestamp/correlation fields
keep workflow decisions traceable
```

V1 does not include:

```text
full audit search
audit dashboard
audit compliance suite
hash-chain audit hardening
complete audit bounded context implementation
```

---

## 6. V1 data ownership

| Data | Owner in V1 |
|---|---|
| User, role, permission, authority | identity |
| Employee, unit, assignment | organization |
| Pipeline, facility, equipment, topology asset | topology |
| Source, device, point, reading, ingestion batch | telemetry |
| Validation/approval task, workflow action, decision reason | workflow |
| Audit-ready event port/payload | workflow/application boundary until audit hardening |
| Validated reading state | telemetry, triggered by workflow decision result |

Important rule:

```text
Workflow must not directly own or mutate telemetry readings.
Telemetry owns reading facts and reading state.
Workflow owns decision process.
```

---

## 7. V1 acceptance criteria

Hidra V1 is accepted when:

```text
kernel/platform baseline is stable
identity module compiles and tests pass
organization module compiles and tests pass
topology module compiles and tests pass
telemetry module compiles and tests pass
workflow module compiles and tests pass
telemetry reading can be referenced by workflow
workflow can create validation task for telemetry reading
workflow can approve/reject/request correction/delegate/escalate
workflow records actor snapshot, organization snapshot, reason, comment, and timestamp
telemetry remains owner of reading state/value
audit-ready event port exists
full mvn -q test passes
no forbidden module imports exist
business taxonomies are catalog-backed
user-facing labels are multilingual
```

---

## 8. V1 explicit non-goals

V1 must not attempt:

```text
full SCADA real-time control
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

Reason:

```text
V1 must prove trusted operational data first.
```

---

## 9. Post-V1 overview

After V1, Hidra moves from trusted data foundation to operational intelligence expansion.

Post-V1 is split into:

```text
V1.1 / Horizon 2 — Operations
V2 / Horizon 3 — Integration
V3 / Horizon 4 — Intelligence
```

---

## 10. V1.1 / Horizon 2 — Operations

Goal:

```text
Support daily pipeline operations using trusted workflow-validated data.
```

Modules and capabilities:

```text
planning
monitoring
incidents
audit hardening
basic dashboards
risk signals
```

Planning scope:

```text
flow plans
planning periods
planned vs actual comparison
plan versioning
plan approval status
```

Monitoring scope:

```text
thresholds
monitoring rules
operational state
deviation detection
risk signals
basic alerts
```

Incidents scope:

```text
incident creation
classification
response action
timeline
root cause
resolution
incident status
```

Audit hardening scope:

```text
append-only audit store
audit event search
workflow decision audit
domain state-change audit
before/after values
correlation/request IDs
actor and target traceability
```

---

## 11. V2 / Horizon 3 — Integration

Goal:

```text
Connect Hidra to industrial and enterprise systems.
```

Scope:

```text
integration context
SCADA integration readiness
historian integration
OPC UA readiness
MQTT readiness
file import/export
batch ingestion jobs
enterprise IAM integration
notification channels
external references
retry/dead-letter handling
```

V2 non-goal:

```text
Hidra still does not replace SCADA or industrial control systems.
```

---

## 12. V3 / Horizon 4 — Intelligence

Goal:

```text
Transform trusted history into insight.
```

Scope:

```text
analytics
reporting
KPIs
trend analysis
forecasting
anomaly detection
root cause support
risk analytics
digital twin readiness
advanced dashboards
```

V3 rule:

```text
Analytics must not modify source-of-truth state.
```

---

## 13. Scope boundary summary

| Capability | V1 | V1.1 | V2 | V3 |
|---|---:|---:|---:|---:|
| kernel/platform foundation | Yes | Maintain | Maintain | Maintain |
| identity | Yes | Harden | Enterprise IAM integration | Maintain |
| organization | Yes | Harden | Integrate if needed | Maintain |
| topology | Yes | Extend | Integrate references | Digital twin readiness |
| telemetry | Yes | Use for monitoring | Industrial ingestion | Analytics input |
| workflow | Yes | Extend to planning/incidents | Notify/integrate | Process analytics |
| audit readiness | Yes | Harden | Integrate | Compliance analytics |
| planning | No | Yes | Integrate | Forecasting input |
| monitoring | No | Yes | Integrate | Anomaly input |
| incidents | No | Yes | Integrate | Root cause analytics |
| integration | Minimal readiness | Limited | Yes | Harden |
| analytics/reporting | No | Basic dashboards only | Export-ready | Yes |
| notification | No | Basic internal readiness | Channels | Smart notifications |

---

## 14. Version gate

V1 gate:

```text
Trusted operational reading can be created, contextualized, workflow-validated, and traceable.
```

V1.1 gate:

```text
Validated readings can be compared with plans, monitored against thresholds, and escalated into incidents.
```

V2 gate:

```text
Hidra exchanges data reliably with industrial and enterprise systems.
```

V3 gate:

```text
Hidra produces actionable intelligence from trusted historical data.
```

---

## 15. Immediate next implementation task

The immediate implementation path after this document is:

```text
WF-002 — chore(workflow): add workflow package skeleton
```

Do not start planning, monitoring, incidents, audit hardening, integration, analytics, reporting, or notification before the workflow baseline passes.

---

## 16. Discussion questions

Before freezing Hidra V1 scope, confirm:

```text
1. Is TELEMETRY_READING the only workflow target in V1?
2. Should V1 include only sequential approval?
3. Should V1 task assignment support actor and organization unit only?
4. Should workflow decision trigger telemetry state update through telemetry service only?
5. Should correction request include reason/comment only?
6. Should WorkflowAuditEventPort use no-op/logging adapter until audit hardening?
7. Is basic dashboard excluded from V1 and moved to V1.1?
8. Is SCADA integration excluded from V1 and moved to V2?
```
