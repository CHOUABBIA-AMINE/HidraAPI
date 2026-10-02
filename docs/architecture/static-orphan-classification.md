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
| `K-LD-QUERY` | KEEP | Exact-head HRA-041 verification found active consumers in `LeakDetectionQueryUseCase`, `LeakDetectionQueryController`, and `LeakDetectionQueryApplicationService`. |
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
| KEEP | 13 |
| DELETE | 197 |
| **Total** | **210** |

| Baseline category | KEEP | DELETE | Total |
|---|---:|---:|---:|
| `data_record` | 2 | 152 | 154 |
| `value_object` | 10 | 42 | 52 |
| `enum` | 1 | 2 | 3 |
| `domain_model` | 0 | 1 | 1 |

The 197 DELETE candidates consist of the 100 `D-EVT` event records already removed by HRA-031B plus the 97 live non-event candidates removed by HRA-041. Exact-head HRA-041 verification reclassified two former DELETE candidates as KEEP because they have active consumers.

## KEEP — 13 evidence-backed candidates

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
- `leakdetection.LeakCaseView` — `K-LD-QUERY`
- `leakdetection.LeakCandidateView` — `K-LD-QUERY`

## DELETE — 52 non-event data records

The 24 `*Query` carriers use `D-QRY`; infrastructure `*Projection` records use `D-PRJ`; DTO/View records use `D-BND`.

- `assets.FindMaintainableAssetByIdQuery`
- `assets.AssetsMaintenanceDashboardProjection`
- `alarm.FindAlarmByIdQuery`
- `alarm.AlarmLifecycleEventDto`
- `alarm.ActiveAlarmDashboardProjection`
- `leakdetection.FindLeakCaseByIdQuery`
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

HRA-041 revalidated all 99 then-live DELETE candidates against exact head `147e8244e7fd7ec1ca3b27d681b8092002a847ef`. `LeakCaseView` and `LeakCandidateView` had active Java consumers and were reclassified KEEP. The remaining 97 candidates had no live Java consumer and were removed.

The 100 `D-EVT` rows are historical reconciliation entries already removed by HRA-031B. Combined with the 97 HRA-041 removals, every one of the 197 final DELETE dispositions has now been physically removed. The 13 KEEP candidates remain present.


## HRA-120 closure replay

The 2026-09-29 HRA-120 source replay rechecked the HRA-040 candidate families after all accepted
cleanup tracks through HRA-111.

- 11 of the 13 KEEP candidates remain zero-incoming and retain the evidence above.
- `LeakCaseView` and `LeakCandidateView` now have active query/controller/service consumers and are
  no longer zero-incoming.
- all 197 DELETE dispositions remain absent.
- one new zero-incoming domain-value candidate is present: `topology.ProjectionType`.

`topology.ProjectionType` is classified **DELETE_RESIDUAL** for closure accounting: the enum has no
incoming Java reference and no exact retained-type decision, and its values align with the retired
Topology projection path. HRA-120 does not delete production types; removal requires an explicitly
authorized code task. This classification ensures the closure replay leaves no zero-incoming candidate
unclassified while preserving evidence-before-deletion discipline.


## HRA-122 residual removal

HRA-122 revalidated the HRA-120 `DELETE_RESIDUAL` disposition against exact execution head
`9d62c46e1a14b1f866f6d48f565322411fb3d960` before deletion.

Repository-wide search found:

- no Java import or fully-qualified reference to
  `dz.sh.hidra.modules.topology.domain.value.ProjectionType`;
- no second Java use of the enum simple name beyond its own declaration;
- no configuration/serialization/reflection string reference to the fully-qualified type; and
- no reuse of the complete `GRAPH, MAP, ROUTING, SIMULATION_INPUT, VISUALIZATION` vocabulary
  outside the enum itself.

The unrelated Analytics `projectionType` state remains a string-owned Analytics contract and is
not a consumer of the Topology enum.

**Disposition:** `topology.ProjectionType` — `DELETE_RESIDUAL → REMOVED_HRA_122`.

`ProjectionType.java` is therefore removed. The historical HRA-120 classification above is
retained as evidence of why deletion was authorized rather than rewritten retroactively.
