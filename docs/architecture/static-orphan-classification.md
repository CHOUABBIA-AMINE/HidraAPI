# HRA-040 — Static orphan candidate classification

**Status:** Completed classification (2026-09-28). HRA-040 is documentation-only; physical deletion is reserved for HRA-041.

## Audit basis

- User-supplied `src.zip`, SHA-256 `f91526a4c802cd4ae839afe22adbd5f82f83b7db8808a0d41c3cd7b2c671b338`.
- Forensic baseline: exactly **210** classified production types with zero incoming Java references: **154 data records, 52 value objects, 3 enums, 1 domain model**.
- HRA-031B already removed the **100** baseline orphan event records after live consumer verification under ADR-0006.
- `KEEP` requires positive repository evidence beyond the declaration itself.
- `DELETE` means eligible for HRA-041 only after an exact-head consumer recheck.

## Evidence codes

| Code | Disposition | Evidence / rule |
|---|---|---|
| `K-KERNEL` | KEEP | `docs/data definition/Kernel.md` and `docs/roadmap/kernel.md` explicitly define the primitive. |
| `K-ASSET-TOPO` | KEEP | `docs/data definition/Assets.md` explicitly requires `AssetTopologyReference` as the neutral Topology boundary. |
| `K-ALARM-TOPO` | KEEP | `docs/data definition/Alarm.md` §6.1 explicitly defines `TopologyAssetReference`. |
| `K-ALARM-ACTOR` | KEEP | `docs/data definition/Alarm.md` §6.2 explicitly defines `ActorSnapshot`. |
| `K-ALARM-ORG` | KEEP | `docs/data definition/Alarm.md` §6.3 explicitly defines `OrganizationUnitSnapshot`. |
| `K-WF-TARGET` | KEEP | `docs/roadmap/workflow.md` lists `WorkflowTargetReference`; Kernel data definition cites it as a module-owned value object. |
| `K-GEO` | KEEP | `docs/data definition/Kernel.md` §17.4 defines `GeoPoint` and assigns GIS/topology ownership to Topology. |
| `K-HRA080` | KEEP | HRA-080 explicitly reserves `ReportDefinitionStatus` for semantic duplicate-enum review. |
| `D-EVT` | DELETE | Every one of the 100 zero-incoming module event records from the forensic orphan table; already removed by HRA-031B. |
| `D-QRY` | DELETE | Zero-incoming application query carrier; no active port/controller/service consumes the type. |
| `D-PRJ` | DELETE | Zero-incoming infrastructure projection; no query adapter/repository/API consumer. |
| `D-BND` | DELETE | Zero-incoming DTO/View absent from active port and REST signatures. |
| `D-DOM` | DELETE | Zero-incoming domain model with no consumer. |
| `D-VO` | DELETE | Zero-incoming value object with no authoritative typed-consumer/documented-retention evidence found by the HRA-040 repository check. |
| `D-ENUM` | DELETE | Zero-incoming enum with no authoritative retention evidence. |

## Reconciliation

| Disposition | Count |
|---|---:|
| KEEP | 11 |
| DELETE | 199 |
| **Total** | **210** |

| Baseline category | KEEP | DELETE | Total |
|---|---:|---:|---:|
| `data_record` | 0 | 154 | 154 |
| `value_object` | 10 | 42 | 52 |
| `enum` | 1 | 2 | 3 |
| `domain_model` | 0 | 1 | 1 |

The 199 DELETE candidates consist of the 100 `D-EVT` event records already removed by HRA-031B plus the 99 live non-event candidates listed below.

## KEEP — 11 evidence-backed candidates

- `root.TimeRange` — `K-KERNEL`
- `root.CorrelationId` — `K-KERNEL`
- `root.DateRange` — `K-KERNEL`
- `root.RequestId` — `K-KERNEL`
- `assets.AssetTopologyReference` — `K-ASSET-TOPO`
- `alarm.TopologyAssetReference` — `K-ALARM-TOPO`
- `alarm.ActorSnapshot` — `K-ALARM-ACTOR`
- `alarm.OrganizationUnitSnapshot` — `K-ALARM-ORG`
- `reporting.ReportDefinitionStatus` — `K-HRA080`
- `workflow.WorkflowTargetReference` — `K-WF-TARGET`
- `topology.GeoPoint` — `K-GEO`

## DELETE — 54 non-event data records

The 24 `*Query` carriers use `D-QRY`; infrastructure `*Projection` records use `D-PRJ`; DTO/View records use `D-BND`.

- `assets.FindMaintainableAssetByIdQuery`
- `assets.AssetsMaintenanceDashboardProjection`
- `alarm.FindAlarmByIdQuery`
- `alarm.AlarmLifecycleEventDto`
- `alarm.ActiveAlarmDashboardProjection`
- `leakdetection.FindLeakCaseByIdQuery`
- `leakdetection.LeakCaseView`
- `leakdetection.LeakCandidateView`
- `leakdetection.LeakDetectionDashboardProjection`
- `documents.FindDocumentByIdQuery`
- `documents.DocumentSearchProjection`
- `hse.FindHseCaseByIdQuery`
- `hse.HseOperationsDashboardProjection`
- `configuration.FindConfigurationValueByIdQuery`
- `configuration.ResolvedConfigurationProjection`
- `identity.UserSecurityProjection`
- `planning.FindOperationalPlanByIdQuery`
- `planning.ApprovedPlanBaselineProjection`
- `notification.FindNotificationMessageByIdQuery`
- `notification.NotificationDeliveryDashboardProjection`
- `risk.FindRiskAssessmentByIdQuery`
- `risk.RiskPostureProjection`
- `telemetry.FindTelemetryPointByIdQuery`
- `telemetry.TelemetryPointLatestStateProjection`
- `integrity.FindIntegrityCaseByIdQuery`
- `integrity.IntegrityDashboardProjection`
- `reporting.GetReportRunQuery`
- `reporting.ReportRunDashboardProjection`
- `workflow.FindWorkflowInstanceByIdQuery`
- `workflow.WorkflowInboxProjection`
- `analytics.GetAnalyticsInsightQuery`
- `analytics.AnalyticsDashboardProjection`
- `audit.FindAuditEventByIdQuery`
- `audit.AuditSearchResultProjection`
- `topology.FindTopologyAssetByIdQuery`
- `topology.TopologyAssetReferenceDto`
- `topology.TopologyAssetReferenceProjection`
- `topology.TopologyGraphProjection`
- `monitoring.FindDeviationByIdQuery`
- `monitoring.MonitoringOperationalDashboardProjection`
- `custody.FindCustodyTransferTicketByIdQuery`
- `custody.CustodyOperationsDashboardProjection`
- `simulation.FindSimulationRunByIdQuery`
- `simulation.SimulationRunDashboardProjection`
- `organization.FindEmployeeByIdQuery`
- `organization.FindOrganizationUnitByIdQuery`
- `organization.EmployeeOrganizationProjection`
- `incident.FindIncidentByIdQuery`
- `incident.IncidentResponseActionDto`
- `incident.IncidentOperationsDashboardProjection`
- `integration.FindIntegrationJobRunByIdQuery`
- `integration.IntegrationOperationsDashboardProjection`
- `party.FindPartyByIdQuery`
- `party.PartyReferenceProjection`

## DELETE — 1 domain model

- `topology.TopologyProjection` — `D-DOM`; zero incoming references, no runtime consumer, and projection-shaped visualization/routing state has no active domain use.

## DELETE — 2 enums

- `custody.CustodyEvidenceType` — `D-ENUM`
- `incident.TimelineEntryKind` — `D-ENUM`

`reporting.ReportDefinitionStatus` is not in this list because HRA-080 explicitly owns its semantic duplicate-enum decision.

## DELETE — 42 value objects

- `assets.PartyReferenceSnapshot`
- `leakdetection.LeakConfidenceScore`
- `leakdetection.LeakTopologyScopeReference`
- `documents.DocumentStoragePointer`
- `documents.DocumentTargetReference`
- `hse.IncidentReferenceSnapshot`
- `hse.ExternalTargetReference`
- `configuration.ConfigurationExternalTargetReference`
- `configuration.ConfigurationScope`
- `identity.EmailAddress`
- `identity.IdentityCode`
- `planning.PlannedValue`
- `planning.TopologyScopeReference`
- `notification.NotificationRecipientReference`
- `notification.NotificationTargetReference`
- `risk.RiskScopeReference`
- `risk.RiskScoreValue`
- `telemetry.TopologyBindingSnapshot`
- `telemetry.TelemetryValueShape`
- `telemetry.TelemetryCode`
- `integrity.EngineeringMeasurement`
- `integrity.TopologyAssetReference`
- `reporting.ReportScopeReference`
- `reporting.ReportArtifactReference`
- `workflow.WorkflowActorSnapshot`
- `analytics.AnalyticsSourceReference`
- `analytics.AnalyticsScopeReference`
- `audit.AuditActorSnapshotValue`
- `audit.AuditTargetSnapshotValue`
- `topology.TopologyAssetReference`
- `monitoring.MonitoringComparedValue`
- `monitoring.MonitoringScopeReference`
- `custody.CustodyPartyReference`
- `custody.CustodyTopologyReference`
- `simulation.SimulationTopologyReference`
- `simulation.SimulationTargetReference`
- `incident.IncidentReferenceSnapshot`
- `incident.IncidentTopologySnapshot`
- `integration.IntegrationTargetReference`
- `integration.IntegrationSourceReference`
- `party.PartyCode`
- `party.PartyReferenceSnapshot`

## HRA-041 guardrail

HRA-041 may delete only candidates classified **DELETE** that still have zero live consumers on its exact execution head. If any candidate has gained a real consumer, HRA-041 must retain it and update this classification instead of breaking the consumer.

The 100 `D-EVT` rows are historical reconciliation entries already removed by HRA-031B and require no second deletion. The 11 KEEP candidates are explicitly out of scope for HRA-041.
