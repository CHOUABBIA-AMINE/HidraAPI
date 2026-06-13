# Hidra V1.1 Scope — Operations

```text
Document code : HIDRA-V1.1-SCOPE
Repository    : HidraAPI
Namespace     : dz.sh.hidra
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Document type : Product scope definition
Version       : V1.1
Phase         : Horizon 2 — Operations
Author        : Abir MEDJERAB
CreatedOn     : 2025-06-26
UpdatedOn     : 2026-06-07
Status        : Draft for discussion
```

---

## 1. Purpose

This document defines the scope of **Hidra V1.1**.

Hidra V1.1 starts only after Hidra V1 has established trusted operational data through:

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

Hidra V1.1 is the first operational expansion after V1.

It turns workflow-validated telemetry into daily operational supervision.

---

## 2. V1.1 name

```text
Hidra V1.1 — Operations
```

---

## 3. V1.1 objective

V1.1 must support daily Sonatrach pipeline operations using trusted, workflow-validated data.

V1.1 answers:

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

---

## 4. Required preconditions

Do not start V1.1 before V1 is accepted.

V1 acceptance requires:

```text
topology baseline passes
telemetry baseline passes
workflow baseline passes
telemetry reading can be workflow-validated
workflow decisions are audit-ready
full mvn -q test passes
```

V1.1 depends on trusted telemetry and workflow decisions.

---

## 5. Included modules and capabilities

V1.1 includes:

```text
planning
monitoring
incidents
audit hardening
basic dashboards
risk signals
```

V1.1 does not include full integration or analytics.

---

## 6. Module order inside V1.1

Recommended V1.1 implementation order:

```text
1. planning
2. monitoring
3. incidents
4. audit hardening
5. basic dashboards / operational read views
```

Reason:

```text
planning defines expected state
monitoring compares validated actual state to expected state and thresholds
incidents manage operational problems after detection
audit hardening records complete decision proof
dashboards expose operational state after the data is trusted
```

---

## 7. Planning scope

### 7.1 Planning purpose

Planning defines expected hydrocarbon operational targets.

Planning answers:

```text
What flow was expected?
For which pipeline, segment, station, or facility?
For which period?
Which unit?
Which plan version?
Who approved the plan?
```

### 7.2 Planning owns

```text
FlowPlan
PlanningPeriod
OperationalTarget
PlanVersion
PlanApproval
PlanStatus
PlanTargetReference
```

### 7.3 Planning references

```text
topology asset reference
validated telemetry actuals
workflow approval reference
actor snapshot
organization unit snapshot
```

### 7.4 Planning does not own

```text
telemetry readings
topology assets
workflow tasks
monitoring alerts
incident lifecycle
analytics projections
```

### 7.5 Planning acceptance criteria

Planning is accepted when:

```text
plans can be created and versioned
plans can target topology assets
plans can define expected values and units
approved plans are immutable
revision creates a new version
actual comparison uses validated/approved telemetry only
```

---

## 8. Monitoring scope

### 8.1 Monitoring purpose

Monitoring evaluates operational state using validated telemetry, planning targets, thresholds, and monitoring rules.

Monitoring answers:

```text
Is actual flow normal?
Is there a threshold violation?
Is there a planned-vs-actual deviation?
Is the deviation warning, critical, or informational?
Should an alert be created?
Is the alert acknowledged?
```

### 8.2 Monitoring owns

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

### 8.3 Monitoring references

```text
validated telemetry reading reference
planning target reference
topology asset reference
actor snapshot
organization unit snapshot
```

### 8.4 Monitoring does not own

```text
telemetry readings
flow plans
incident lifecycle
notification delivery
risk scoring engine
analytics models
```

### 8.5 Monitoring acceptance criteria

Monitoring is accepted when:

```text
rules can be configured
thresholds include scope, unit, severity, and effective period
validated telemetry can be evaluated
planned-vs-actual deviations can be detected
alerts or alert candidates can be produced
acknowledgement tracks actor and time
```

---

## 9. Incidents scope

### 9.1 Incidents purpose

Incidents manage operational problems from detection to resolution.

Incidents answer:

```text
What happened?
Where did it happen?
When was it detected?
What caused it?
Who is responsible?
What response actions were taken?
Was the incident resolved?
What evidence supports closure?
```

### 9.2 Incidents owns

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

### 9.3 Incidents references

```text
monitoring alert reference
telemetry reading reference
topology asset reference
actor snapshot
organization unit snapshot
workflow reference if incident requires approval
audit reference
```

### 9.4 Incidents does not own

```text
monitoring rules
telemetry readings
topology assets
notification channels
analytics root-cause models
```

### 9.5 Incidents acceptance criteria

Incidents are accepted when:

```text
incident can be created from monitoring/alert context
incident status lifecycle is controlled
timeline is append-only
response actions are tracked
root cause can be recorded
closure requires resolution
actor and organization snapshots are preserved
```

---

## 10. Audit hardening scope

### 10.1 Audit hardening purpose

Audit hardening turns V1 audit readiness into durable audit evidence.

Audit answers:

```text
Who acted?
What did they do?
Which object was affected?
What changed?
Why did it change?
Which workflow state was involved?
What correlation/request context existed?
Can the evidence be searched later?
```

### 10.2 Audit hardening owns

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

### 10.3 Audit hardening references

```text
workflow action reference
telemetry reading reference
planning target reference
monitoring event reference
incident reference
identity actor reference snapshot
organization unit reference snapshot
```

### 10.4 Audit hardening does not own

```text
business decisions
workflow routing
telemetry state
incident state
planning state
monitoring rules
```

### 10.5 Audit hardening acceptance criteria

Audit hardening is accepted when:

```text
audit events are append-only
workflow decisions are auditable
domain state changes can publish audit events
before/after values can be captured where applicable
sensitive values can be masked
audit events are searchable by actor, target, action, time, and correlation id
```

---

## 11. Basic dashboards and read views

V1.1 may include basic operational read views.

Allowed scope:

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

---

## 12. V1.1 explicit non-goals

V1.1 must not attempt:

```text
SCADA production integration
historian replacement
OPC UA/MQTT production connectors
enterprise-wide integration hub
advanced analytics
AI forecasting
digital twin
notification delivery platform beyond basic readiness
microservices extraction
```

---

## 13. V1.1 version gate

V1.1 is complete when:

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

## 14. Output of V1.1

At the end of V1.1, Hidra should support:

```text
trusted data
validated readings
operational plans
planned-vs-actual comparison
threshold monitoring
incident lifecycle
hardened audit trail
basic operational visibility
```

This prepares Hidra for V2 integration.
