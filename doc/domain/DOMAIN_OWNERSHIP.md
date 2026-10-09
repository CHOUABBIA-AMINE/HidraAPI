# HidraAPI Domain Ownership

## Status

CURRENT ownership anchors derived from the live domain model inventory.

## Rule

Each business module owns its own domain model and source-of-truth semantics. Cross-module consumers use explicit application contracts, stable IDs, references or snapshots; they do not become owners of the provider's aggregate.

The table records current ownership anchors, not exhaustive per-module documentation. The [module set](../modules/README.md) now provides refreshed current inventories and subject decision links for all 24 modules.

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

`agents`, `environment` and `otsecurity` are not current source module roots. Canonicalization does not assign implemented domain ownership to them.

## Reference, eligibility and provenance ownership

[Permanent decisions](SEMANTIC_DECISIONS.md) reconcile the historical subjects against
current source. [Architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java)
and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java)
agree on 63 exported packages. Provider identity/existence, lifecycle eligibility,
context compatibility and approval are different facts: a contract proving one does
not establish the others. In particular, Workflow existence is not proof of approval.

Internal parent existence and same-owner composite context are protected inside their
own module. Cross-module references use scalar contracts/providers rather than private
aggregate imports or cross-module database FKs. Current imports/exports are linked in
each module document; source truth remains authoritative over a stale inventory.

Owner-approved actual-ID policy facts include Planning target representation, Custody
product approval and Telemetry quantity/rate role/pair metadata for Nominations.
Integrity/HSE also use explicit taxonomy policy. Empty metadata is no business approval;
fail-closed migrations may block populated deployments pending owner reconciliation.
No business classification is inferred from similar field names, enum values or codes.

Authenticated actor evidence is server-derived where the supported write path requires
it, and canonical owner labels populate fresh snapshots. Per-subject guards preserve
unchanged historical references instead of globally refreshing evidence after retirement.
[Identity](../modules/identity.md), [Organization](../modules/organization.md) and
[Party](../modules/party.md) keep security identity, internal people/responsibility and
external counterparties separate. These boundaries do not invent universal fresh
eligibility or approval rules beyond the cited subject contracts.

Organization's localized unit/type/geography/position/shift labels are stored on their
owning entities in Arabic/French/English triplets. The retired separate unit-type
translation table is not a current model and cannot be reintroduced through a copied
legacy inventory. [Embedded multilingual guardrails](../../src/test/java/dz/sh/hidra/modules/organization/OrganizationEmbeddedMultilingualIntegrityTest.java)
and [the retirement migration](../../src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql)
provide the current evidence. Missing translations remain nullable where the owning
model allows them; canonicalization does not fabricate language content.
