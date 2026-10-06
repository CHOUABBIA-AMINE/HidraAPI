# HIDRA Leak Detection Module — Data Definition Document

```text
Document code : HIDRA-LEAK-DETECTION-DDD
Repository    : HidraAPI
Module        : leakdetection
Package root  : dz.sh.hidra.modules.leakdetection
Table prefix  : hidra_leak_detection_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `leakdetection` module detects, evaluates, localizes, and explains suspected hydrocarbon leaks using trusted operational evidence.

It is an operational intelligence and decision-support module. It is not a control system, not an alarm lifecycle module, not an incident lifecycle module, and not a SCADA actuation module.

---

## 2. Canonical implementation identity

```text
Module name   : leakdetection
Package root  : dz.sh.hidra.modules.leakdetection
Table prefix  : hidra_leak_detection_*
```

Forbidden table prefixes:

```text
hidra_leakdetection_*
hidra_alarm_*
hidra_incident_*
hidra_monitoring_*
hidra_topology_*
hidra_telemetry_*
```

---

## 3. Ownership

Leak Detection owns:

```text
LeakDetectionProfile
LeakDetectionMethodCatalog
LeakDetectionMethodTranslation
LeakDetectionRule
LeakDetectionRun
LeakCandidate
LeakEvidenceLink
LeakLocalizationEstimate
LeakSeverityAssessment
LeakVerificationAction
LeakDetectionCase
LeakCaseStatusHistory
LeakEscalationReference
LeakDismissalReason
```

Leak Detection does not own:

```text
Pipeline
Facility
PipelineSegment
TopologyNode
Equipment
TelemetryReading
TrustedTelemetryReading
MonitoringThreshold
MonitoringEvaluation
Alarm
Incident
MaintenanceWorkOrder
SCADA command
Valve actuation
Pump actuation
Compressor actuation
```

---

## 4. Safety rule

Leak Detection is decision support only.

Forbidden behavior:

```text
automatic valve closure
automatic pump shutdown
automatic compressor shutdown
automatic PLC command
automatic RTU command
automatic SCADA write-back
automatic ESD/SIS interaction
direct write to OT control systems
```

Allowed behavior:

```text
create leak candidate
raise leak suspicion
calculate confidence
estimate location
link evidence
request operator verification
escalate by neutral reference
emit audit-ready events
```

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| LeakDetectionProfile | `hidra_leak_detection_profile` | Detection configuration for a topology scope. |
| LeakDetectionMethodCatalog | `hidra_leak_detection_method` | Catalog of detection methods. |
| LeakDetectionMethodTranslation | `hidra_leak_detection_method_translation` | Multilingual method labels. |
| LeakDetectionRule | `hidra_leak_detection_rule` | Rule/parameter attached to a profile and method. |
| LeakDetectionRun | `hidra_leak_detection_run` | Detection evaluation run. |
| LeakCandidate | `hidra_leak_detection_candidate` | Suspected leak event. |
| LeakEvidenceLink | `hidra_leak_detection_evidence_link` | Evidence supporting/contradicting candidate. |
| LeakLocalizationEstimate | `hidra_leak_detection_localization_estimate` | Estimated leak location and uncertainty. |
| LeakSeverityAssessment | `hidra_leak_detection_severity_assessment` | Severity/confidence assessment. |
| LeakVerificationAction | `hidra_leak_detection_verification_action` | Operator/technical verification action. |
| LeakDetectionCase | `hidra_leak_detection_case` | Controlled lifecycle record grouping candidates. |
| LeakCaseStatusHistory | `hidra_leak_detection_case_status_history` | Append-only case status history. |
| LeakEscalationReference | `hidra_leak_detection_escalation_reference` | Neutral reference to alarm/incident/workflow/notification. |
| LeakDismissalReason | `hidra_leak_detection_dismissal_reason` | Catalog of dismissal reasons. |

---

## 6. Escalation rule

Allowed:

```text
LeakDetectionCase
  -> LeakEscalationReference
      -> neutral alarm/incident/workflow/notification reference
```

Forbidden until missing DDDs exist:

```text
Leak Detection creates alarm tables
Leak Detection creates incident tables
Leak Detection imports alarm or incident domain model
Leak Detection performs OT actuation
```

---

## 7. Reference model

Leak Detection references topology, telemetry, monitoring, alarm, incident, workflow, audit, organization, and actor concepts only through IDs and snapshots.

### 7.1 HMR-015 LeakCandidate semantic baseline

A new `LeakCandidate` may be runless. This preserves manual/internal suspicion capture without inventing
a synthetic `LeakDetectionRun`. Candidate creation nevertheless requires the referenced
`LeakDetectionProfile` to exist and be `ACTIVE`. When `runId` is supplied, the run must exist,
must reference the same `profileId`, and must be `RUNNING` or `COMPLETED`. A nullable
same-module foreign key protects the persisted run reference.

The typed Topology reference is owner-controlled through
`topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract`.

```text
Supported Leak Detection Topology target types:
PIPELINE
PIPELINE_SEGMENT
FACILITY
TOPOLOGY_NODE
EQUIPMENT
```

Unsupported or missing targets fail closed. The contract supplies the owner-native ID, current code,
and current display name when available. A caller-supplied code is treated only as a consistency hint:
if it conflicts with the current owner code, creation is rejected. Persisted code/name snapshots are
taken from Topology rather than trusted from caller input. No cross-module database foreign key is added.

For the candidate aggregate, `confidenceScore` is authoritative and `severityLevel` is derived by the
existing `LeakConfidenceClassifier`: CRITICAL from 0.90, HIGH from 0.75, MEDIUM from 0.50, otherwise
LOW. The repository does not define a confidence numeric range, so HMR-015 does not invent one.
Independent later engineering/operator severity assessment remains represented separately by
`LeakSeverityAssessment`; it does not make the candidate's initial derived pair contradictory.

`createdAt` and `updatedAt` are mandatory aggregate persistence invariants.

External-compute idempotency remains an explicit integration gate. The parked EXT-011/EXT-012 sequence
requires the future CPM/Protobuf contract to carry stable anomaly identity/correlation, but the live
repository does not establish whether `candidateNumber` is globally unique, profile-scoped, run-scoped,
or merely a business/display number. Therefore HMR-015 does not invent a uniqueness constraint.
No CPM/gRPC adapter may create candidates until that contract defines the stable anomaly identity and
its exact uniqueness/idempotency scope.

---

## 8. Documentation and annotation rule

Domain, application, and infrastructure leak-detection models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in leakdetection API request/response models.

### 7.2 HMR-051 LeakDetectionCase owner validation

Every case save validates the mandatory typed Topology identity through the existing
Topology-owned Leak Detection contract. Unsupported or missing targets fail before persistence.
`topologyAssetCode` remains the supplied historical/display snapshot; it is not overwritten
or required to equal the current owner code. A populated owning unit resolves through
`organization.application.contract.leakdetection.LeakDetectionOrganizationUnitReferenceContract`.
Ownership remains optional. Neither reference receives a cross-module database FK.

### 7.3 HMR-059 escalation candidate provenance

A populated escalation `candidateId` must resolve to an existing LeakCandidate.
The repository rejects missing candidates, and forward migration `V20261006_001`
adds a nullable same-module FK after an orphan preflight. Null remains allowed.
The candidate need not equal the case's primary candidate; no such rule is established.
Neutral external escalation targets retain scalar identity and snapshots.
