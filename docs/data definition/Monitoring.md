# HIDRA Monitoring Module — Data Definition Document

```text
Document code : HIDRA-MONITORING-DDD
Repository    : HidraAPI
Module        : monitoring
Package root  : dz.sh.hidra.modules.monitoring
Table prefix  : hidra_monitoring_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `monitoring` module owns operational evaluation, actual-vs-expected comparison, deviation detection, operational state snapshots, monitoring acknowledgements, alert candidates, and risk signals.

Monitoring detects and explains deviations. It does not own the formal alarm lifecycle, incident lifecycle, leak-detection case lifecycle, notification delivery, or SCADA/OT actuation.

---

## 2. Canonical implementation identity

```text
Module name   : monitoring
Package root  : dz.sh.hidra.modules.monitoring
Table prefix  : hidra_monitoring_*
```

Forbidden table prefixes:

```text
hidra_alarm_*
hidra_incident_*
hidra_leak_detection_*
hidra_telemetry_*
hidra_planning_*
```

---

## 3. Ownership

Monitoring owns:

```text
MonitoringRule
MonitoringThreshold
MonitoringEvaluation
OperationalState
OperationalStateSnapshot
PlanActualDeviation
MonitoringAlertCandidate
MonitoringAcknowledgement
RiskSignal
MonitoringCatalogEntry
MonitoringCatalogTranslation
```

Monitoring does not own:

```text
Alarm
Incident
LeakCandidate
LeakDetectionCase
TelemetryReading
TrustedTelemetryReading
OperationalPlan
PlanTarget
NotificationMessage
SCADA command
WorkflowTask
AuditRecord
```

---

## 4. Missing-module blockers

`alarm management` and `incident management` are blocked until explicit DDD files exist.

Monitoring may produce:

```text
MonitoringAlertCandidate
RiskSignal
DeviationDetectedEvent
```

Monitoring must not create:

```text
hidra_alarm_*
hidra_incident_*
dz.sh.hidra.modules.alarm*
dz.sh.hidra.modules.incident*
```

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| MonitoringRule | `hidra_monitoring_rule` | Rule for operational evaluation. |
| MonitoringThreshold | `hidra_monitoring_threshold` | Threshold bound to rule/scope. |
| MonitoringEvaluation | `hidra_monitoring_evaluation` | Evaluation execution. |
| OperationalState | `hidra_monitoring_operational_state` | Current interpreted operational state. |
| OperationalStateSnapshot | `hidra_monitoring_operational_state_snapshot` | Historical state snapshot. |
| PlanActualDeviation | `hidra_monitoring_plan_actual_deviation` | Actual-vs-expected deviation. |
| MonitoringAlertCandidate | `hidra_monitoring_alert_candidate` | Candidate for future alarm management. |
| MonitoringAcknowledgement | `hidra_monitoring_acknowledgement` | Operator acknowledgement of monitoring result. |
| RiskSignal | `hidra_monitoring_risk_signal` | Monitoring-derived risk signal. |
| MonitoringCatalogEntry | `hidra_monitoring_catalog_entry` | Monitoring-owned catalog. |
| MonitoringCatalogTranslation | `hidra_monitoring_catalog_translation` | Multilingual catalog labels. |

---

## 6. Telemetry and planning boundary

```text
Telemetry owns measured facts.
Planning owns expected values.
Monitoring compares actual values to expected values and produces deviation intelligence.
```

Monitoring stores references and snapshots, not telemetry or planning source records.

---

## 7. Leak-detection boundary

Monitoring may provide evidence and alert candidates. Leak Detection owns leak suspicion, localization, verification, and leak case lifecycle.

---

## 8. Documentation and annotation rule

Domain, application, and infrastructure monitoring models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in monitoring API request/response models.
