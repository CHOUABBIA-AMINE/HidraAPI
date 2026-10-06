# HidraAPI Domain Ownership

## Status

CURRENT ownership anchors derived from the live domain model inventory.

## Rule

Each business module owns its own domain model and source-of-truth semantics. Cross-module consumers use explicit application contracts, stable IDs, references or snapshots; they do not become owners of the provider's aggregate.

The table records current ownership anchors, not exhaustive per-module documentation. HPR-P2-004 remains responsible for a full current-state document for every module.

| Module | Current ownership anchors evidenced by domain model types |
|---|---|
| alarm | `Alarm`, `AlarmAcknowledgement`, `AlarmClosure`, `AlarmShelving` |
| analytics | `AnalyticsDataset`, `AnalyticsDatasetVersion`, `AnalyticsInsight`, `AnalyticsProjectionRun`, `DigitalTwinReadinessAssessment`, `MetricEvaluationRun`, `MetricValue` |
| assets | `MaintainableAsset`, `AssetConditionRecord`, `MaintenanceWorkOrder` |
| audit | `AuditEvent`, `AuditAccessRecord`, `AuditBeforeAfterValue`, `AuditExportRequest` |
| configuration | `ConfigurationDefinition`, `ConfigurationValue`, `FeatureFlag` |
| custody | `CustodyMeasurementPeriod`, `CustodyTransferTicket`, `CustodyDiscrepancy` |
| documents | `Document`, `DocumentVersion`, `DocumentStorageObject`, `DocumentTargetLink` |
| hse | `HseCase`, `HseClosure`, `HseCorrectivePreventiveAction`, `PermitToWork` |
| identity | `User`, `Role`, `Permission`, credentials/provider/session/grant and authorization-decision domain records |
| incident | `Incident`, `IncidentClosure`, `IncidentRelatedIncident`, `IncidentResponseAction` |
| integration | `ExternalSystem`, `IntegrationExchangeMessage`, `IntegrationJobRun`, `IntegrationDeadLetterRecord` |
| integrity | `IntegrityProgram`, `IntegrityAssessment`, `PipelineDefect`, `IntegrityCase` |
| leakdetection | `LeakCandidate`, `LeakDetectionCase`, `LeakEscalationReference` |
| monitoring | `MonitoringRule`, `PlanActualDeviation` |
| notification | `NotificationTemplate`, `NotificationRequest`, `NotificationMessage`, `NotificationDeliveryAttempt` |
| organization | organization units/types, positions, employees/assignments, reporting lines, shifts, responsibilities, operational scopes and geographic administrative references |
| party | `Party`, `PartyRoleAssignment` |
| planning | `PlanningPeriod`, `OperationalPlan`, `PlanRevision`, `PlanTarget`, `Nomination` |
| reporting | `ReportDefinition`, `ReportRequest`, `ReportRun`, `ReportOutputArtifact` |
| risk | `RiskAssessment`, `RiskEvidenceLink`, `RiskMatrixCell`, `RiskRegister` |
| simulation | `SimulationModel`, `SimulationScenario`, `SimulationRun`, candidates/changes and `SimulationRecommendation` |
| telemetry | `TelemetrySource`, `TelemetryPoint`, `TelemetryReading`, `TrustedTelemetryReading` |
| topology | `PipelineSystem`, `Pipeline`, `Facility`, `Equipment`, `TopologyConnection` |
| workflow | workflow definition/step/transition/instance/task/action/state-history and assignment-rule domain records |

## Ownership Boundaries Confirmed by Current Policies

- topology treats telemetry/assets/integrity/HSE/custody persistence prefixes as foreign.
- assets explicitly reports that it does **not** own physical topology identity and **does** own maintenance work orders.
- integrity explicitly may not create a maintenance work order directly.
- simulation may not write foreign operational module tables and may not actuate field equipment.
- analytics may not mutate source-of-truth operational state and must not export foreign aggregates/JPA entities as payloads.

## Not Current Module Ownership

`agents`, `environment` and `otsecurity` are not current source module roots. HPR-P2-003 does not assign implemented domain ownership to them.
