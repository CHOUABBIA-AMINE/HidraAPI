# HidraAPI Permanent Semantic Decisions

## Status and applicability

CURRENT — HPR-P2-009 canonical semantic transfer, verified against source parent
`44d4fe773d69ed51dd90820140c8d9e7aee6cba2` on 2026-10-09. Business ownership follows the owning module;
named human approval authorities are not invented. Metadata is inherited from
[the domain index](README.md). Current rules below describe source-visible behavior,
not an independent production-data certification or physical survivability result.

## Authority and provenance

This register and [module documents](../modules/README.md) hold durable semantics.
[The Ultimate Roadmap](../roadmap/ULTIMATE_ROADMAP.md) controls execution;
[reconciliation](../model-remediation/RECONCILIATION.md) retains completion evidence.
The [legacy detailed reviews](../../docs/roadmap/model-semantic-review.md) and
[legacy remediation roadmap](../../docs/roadmap/model-semantic-remediation.md)
remain unchanged historical evidence. Their verdicts are provenance, not live task status.

The historical catalogue has 123 subjects: 19 APPROVED and 104 REVISE, mapped to
104 subject HMRs (HMR-003..106). HMR-001/002 were supporting work. The later closed
57-row reconciliation (HMR-050..106) is a separate execution population. All 104
REVISE subjects now have dedicated semantic tests in current source; these files
were inspected, not executed again by this documentation task. Prior cumulative
production CI #604 passed on `617c2eec812e3a5734957ee9fa0360f6f5613032`.

Current inventories contain 24 modules, 148 domain/model Java files (124 model types plus 24 package descriptors), 470 JPA entity
files, 70 contract Java files excluding package-info, 63 exported packages and 139
unique Flyway versions. AlarmLifecycleEvent is the one additional model beyond the historical catalogue; it appears in the Alarm inventory without
invented review identifiers. Inventory presence alone does not establish semantics.

## Reading the decisions

Each subject has a durable rule and direct evidence links below. The domain record
establishes intrinsic shape; JPA/migration evidence establishes owned persistence;
application validators and tests identify where the referenced enforcement occurs.
A rule checked by a use case is not automatically guaranteed by every repository
adapter. Scalar owner contracts are not cross-module database foreign keys.
The supporting test links describe evidence coverage, not a new passing test run.

Across subjects, preserve nullable references, valid unchanged historical mappings,
canonical owner snapshots and the exact fresh-reference eligibility actually coded.
A catalog name or code is not sufficient to approve an actual-ID policy mapping.
Approved metadata for PlanTarget/Nomination, Integrity cases and HSE CAPA remains
an owner decision; no empty metadata table creates business approval. Where a
migration fails closed on historical data, owner reconciliation precedes deployment.
No guessed business values or silent historical repairs are authorized.

## Historical subject traceability

| Review | Subject / canonical decision | Historical verdict | Subject HMR |
|---|---|---|---|
| HMSR-001 | [organization.OrganizationUnitType](#organization-organizationunittype) | APPROVED | — |
| HMSR-002 | [workflow.WorkflowDefinition](#workflow-workflowdefinition) | REVISE | HMR-003 |
| HMSR-003 | [organization.AdministrativeState](#organization-administrativestate) | APPROVED | — |
| HMSR-004 | [party.Party](#party-party) | REVISE | HMR-004 |
| HMSR-005 | [telemetry.TelemetryPoint](#telemetry-telemetrypoint) | REVISE | HMR-005 |
| HMSR-006 | [planning.PlanningPeriod](#planning-planningperiod) | REVISE | HMR-006 |
| HMSR-007 | [identity.Role](#identity-role) | REVISE | HMR-007 |
| HMSR-008 | [documents.DocumentStorageObject](#documents-documentstorageobject) | REVISE | HMR-008 |
| HMSR-009 | [simulation.SimulationModel](#simulation-simulationmodel) | REVISE | HMR-009 |
| HMSR-010 | [identity.IdentityProvider](#identity-identityprovider) | REVISE | HMR-010 |
| HMSR-011 | [identity.Permission](#identity-permission) | REVISE | HMR-011 |
| HMSR-012 | [notification.NotificationTemplate](#notification-notificationtemplate) | REVISE | HMR-012 |
| HMSR-013 | [reporting.ReportDefinition](#reporting-reportdefinition) | REVISE | HMR-013 |
| HMSR-014 | [integration.IntegrationJobRun](#integration-integrationjobrun) | REVISE | HMR-014 |
| HMSR-015 | [leakdetection.LeakCandidate](#leakdetection-leakcandidate) | REVISE | HMR-015 |
| HMSR-016 | [organization.OperationalScope](#organization-operationalscope) | APPROVED | — |
| HMSR-017 | [analytics.AnalyticsDataset](#analytics-analyticsdataset) | APPROVED | — |
| HMSR-018 | [analytics.MetricEvaluationRun](#analytics-metricevaluationrun) | REVISE | HMR-016 |
| HMSR-019 | [configuration.ConfigurationDefinition](#configuration-configurationdefinition) | REVISE | HMR-017 |
| HMSR-020 | [custody.CustodyMeasurementPeriod](#custody-custodymeasurementperiod) | REVISE | HMR-018 |
| HMSR-021 | [integrity.PipelineDefect](#integrity-pipelinedefect) | REVISE | HMR-019 |
| HMSR-022 | [organization.Position](#organization-position) | REVISE | HMR-020 |
| HMSR-023 | [organization.Shift](#organization-shift) | REVISE | HMR-021 |
| HMSR-024 | [topology.PipelineSystem](#topology-pipelinesystem) | REVISE | HMR-022 |
| HMSR-025 | [analytics.AnalyticsInsight](#analytics-analyticsinsight) | REVISE | HMR-023 |
| HMSR-026 | [analytics.AnalyticsProjectionRun](#analytics-analyticsprojectionrun) | REVISE | HMR-024 |
| HMSR-027 | [analytics.DigitalTwinReadinessAssessment](#analytics-digitaltwinreadinessassessment) | REVISE | HMR-025 |
| HMSR-028 | [configuration.FeatureFlag](#configuration-featureflag) | REVISE | HMR-026 |
| HMSR-029 | [custody.CustodyDiscrepancy](#custody-custodydiscrepancy) | REVISE | HMR-027 |
| HMSR-030 | [organization.OrganizationContactPoint](#organization-organizationcontactpoint) | APPROVED | — |
| HMSR-031 | [organization.ReportingLine](#organization-reportingline) | REVISE | HMR-028 |
| HMSR-032 | [risk.RiskMatrixCell](#risk-riskmatrixcell) | REVISE | HMR-029 |
| HMSR-033 | [telemetry.TelemetrySource](#telemetry-telemetrysource) | REVISE | HMR-030 |
| HMSR-034 | [topology.TopologyConnection](#topology-topologyconnection) | REVISE | HMR-031 |
| HMSR-035 | [organization.OrganizationUnit](#organization-organizationunit) | REVISE | HMR-032 |
| HMSR-036 | [organization.AdministrativeDistrict](#organization-administrativedistrict) | APPROVED | — |
| HMSR-037 | [telemetry.TelemetryReading](#telemetry-telemetryreading) | REVISE | HMR-033 |
| HMSR-038 | [simulation.SimulationScenario](#simulation-simulationscenario) | REVISE | HMR-034 |
| HMSR-039 | [notification.NotificationRequest](#notification-notificationrequest) | REVISE | HMR-035 |
| HMSR-040 | [organization.ResponsibilityAssignment](#organization-responsibilityassignment) | APPROVED | — |
| HMSR-041 | [topology.Facility](#topology-facility) | REVISE | HMR-036 |
| HMSR-042 | [analytics.AnalyticsDatasetVersion](#analytics-analyticsdatasetversion) | REVISE | HMR-037 |
| HMSR-043 | [analytics.MetricValue](#analytics-metricvalue) | REVISE | HMR-038 |
| HMSR-044 | [configuration.ConfigurationValue](#configuration-configurationvalue) | REVISE | HMR-039 |
| HMSR-045 | [identity.ExternalRoleMapping](#identity-externalrolemapping) | APPROVED | — |
| HMSR-046 | [identity.GroupRoleGrant](#identity-grouprolegrant) | APPROVED | — |
| HMSR-047 | [identity.RolePermissionGrant](#identity-rolepermissiongrant) | APPROVED | — |
| HMSR-048 | [monitoring.MonitoringRule](#monitoring-monitoringrule) | REVISE | HMR-040 |
| HMSR-049 | [party.PartyRoleAssignment](#party-partyroleassignment) | REVISE | HMR-041 |
| HMSR-050 | [topology.Pipeline](#topology-pipeline) | REVISE | HMR-042 |
| HMSR-051 | [workflow.WorkflowStep](#workflow-workflowstep) | REVISE | HMR-043 |
| HMSR-052 | [workflow.WorkflowStepAssignmentRule](#workflow-workflowstepassignmentrule) | REVISE | HMR-044 |
| HMSR-053 | [organization.AdministrativeLocality](#organization-administrativelocality) | APPROVED | — |
| HMSR-054 | [assets.MaintainableAsset](#assets-maintainableasset) | REVISE | HMR-045 |
| HMSR-055 | [simulation.SimulationRun](#simulation-simulationrun) | REVISE | HMR-046 |
| HMSR-056 | [integration.ExternalSystem](#integration-externalsystem) | REVISE | HMR-047 |
| HMSR-057 | [reporting.ReportRequest](#reporting-reportrequest) | REVISE | HMR-048 |
| HMSR-058 | [risk.RiskRegister](#risk-riskregister) | REVISE | HMR-049 |
| HMSR-059 | [integrity.IntegrityProgram](#integrity-integrityprogram) | REVISE | HMR-050 |
| HMSR-060 | [leakdetection.LeakDetectionCase](#leakdetection-leakdetectioncase) | REVISE | HMR-051 |
| HMSR-061 | [notification.NotificationMessage](#notification-notificationmessage) | REVISE | HMR-052 |
| HMSR-062 | [telemetry.TrustedTelemetryReading](#telemetry-trustedtelemetryreading) | REVISE | HMR-053 |
| HMSR-063 | [topology.Equipment](#topology-equipment) | REVISE | HMR-054 |
| HMSR-064 | [workflow.WorkflowInstance](#workflow-workflowinstance) | REVISE | HMR-055 |
| HMSR-065 | [organization.Employee](#organization-employee) | APPROVED | — |
| HMSR-066 | [simulation.SimulationOptimizationCandidate](#simulation-simulationoptimizationcandidate) | APPROVED | — |
| HMSR-067 | [integration.IntegrationExchangeMessage](#integration-integrationexchangemessage) | REVISE | HMR-056 |
| HMSR-068 | [reporting.ReportRun](#reporting-reportrun) | REVISE | HMR-057 |
| HMSR-069 | [risk.RiskAssessment](#risk-riskassessment) | REVISE | HMR-058 |
| HMSR-070 | [assets.AssetConditionRecord](#assets-assetconditionrecord) | APPROVED | — |
| HMSR-071 | [leakdetection.LeakEscalationReference](#leakdetection-leakescalationreference) | REVISE | HMR-059 |
| HMSR-072 | [notification.NotificationDeliveryAttempt](#notification-notificationdeliveryattempt) | REVISE | HMR-060 |
| HMSR-073 | [workflow.WorkflowTransition](#workflow-workflowtransition) | REVISE | HMR-061 |
| HMSR-074 | [incident.Incident](#incident-incident) | REVISE | HMR-062 |
| HMSR-075 | [identity.User](#identity-user) | REVISE | HMR-063 |
| HMSR-076 | [planning.PlanRevision](#planning-planrevision) | REVISE | HMR-064 |
| HMSR-077 | [planning.OperationalPlan](#planning-operationalplan) | REVISE | HMR-065 |
| HMSR-078 | [workflow.WorkflowTask](#workflow-workflowtask) | REVISE | HMR-066 |
| HMSR-079 | [documents.Document](#documents-document) | REVISE | HMR-067 |
| HMSR-080 | [documents.DocumentVersion](#documents-documentversion) | REVISE | HMR-068 |
| HMSR-081 | [assets.MaintenanceWorkOrder](#assets-maintenanceworkorder) | REVISE | HMR-069 |
| HMSR-082 | [custody.CustodyTransferTicket](#custody-custodytransferticket) | REVISE | HMR-070 |
| HMSR-083 | [hse.PermitToWork](#hse-permittowork) | APPROVED | — |
| HMSR-084 | [integration.IntegrationDeadLetterRecord](#integration-integrationdeadletterrecord) | REVISE | HMR-071 |
| HMSR-085 | [integrity.IntegrityAssessment](#integrity-integrityassessment) | REVISE | HMR-072 |
| HMSR-086 | [organization.EmployeeAddress](#organization-employeeaddress) | APPROVED | — |
| HMSR-087 | [organization.EmployeeAssignment](#organization-employeeassignment) | REVISE | HMR-073 |
| HMSR-088 | [organization.OrganizationDelegation](#organization-organizationdelegation) | REVISE | HMR-074 |
| HMSR-089 | [organization.OrganizationHierarchySnapshot](#organization-organizationhierarchysnapshot) | REVISE | HMR-075 |
| HMSR-090 | [organization.ShiftAssignment](#organization-shiftassignment) | REVISE | HMR-076 |
| HMSR-091 | [risk.RiskEvidenceLink](#risk-riskevidencelink) | REVISE | HMR-077 |
| HMSR-092 | [simulation.SimulationCandidateChange](#simulation-simulationcandidatechange) | REVISE | HMR-078 |
| HMSR-093 | [simulation.SimulationRecommendation](#simulation-simulationrecommendation) | REVISE | HMR-079 |
| HMSR-094 | [planning.Nomination](#planning-nomination) | REVISE | HMR-080 |
| HMSR-095 | [workflow.WorkflowAction](#workflow-workflowaction) | REVISE | HMR-081 |
| HMSR-096 | [hse.HseCase](#hse-hsecase) | REVISE | HMR-082 |
| HMSR-097 | [audit.AuditExportRequest](#audit-auditexportrequest) | REVISE | HMR-083 |
| HMSR-098 | [documents.DocumentTargetLink](#documents-documenttargetlink) | REVISE | HMR-084 |
| HMSR-099 | [identity.AuthenticationEvent](#identity-authenticationevent) | APPROVED | — |
| HMSR-100 | [identity.AuthorizationDecision](#identity-authorizationdecision) | REVISE | HMR-085 |
| HMSR-101 | [identity.AuthorizationDelegationGrant](#identity-authorizationdelegationgrant) | REVISE | HMR-086 |
| HMSR-102 | [identity.HidraPrincipal](#identity-hidraprincipal) | APPROVED | — |
| HMSR-103 | [identity.LocalCredential](#identity-localcredential) | APPROVED | — |
| HMSR-104 | [identity.LoginSession](#identity-loginsession) | REVISE | HMR-087 |
| HMSR-105 | [identity.UserPermissionGrant](#identity-userpermissiongrant) | REVISE | HMR-088 |
| HMSR-106 | [identity.UserRoleGrant](#identity-userrolegrant) | REVISE | HMR-089 |
| HMSR-107 | [incident.IncidentClosure](#incident-incidentclosure) | REVISE | HMR-090 |
| HMSR-108 | [incident.IncidentRelatedIncident](#incident-incidentrelatedincident) | REVISE | HMR-091 |
| HMSR-109 | [incident.IncidentResponseAction](#incident-incidentresponseaction) | REVISE | HMR-092 |
| HMSR-110 | [reporting.ReportOutputArtifact](#reporting-reportoutputartifact) | REVISE | HMR-093 |
| HMSR-111 | [planning.PlanTarget](#planning-plantarget) | REVISE | HMR-094 |
| HMSR-112 | [audit.AuditEvent](#audit-auditevent) | REVISE | HMR-095 |
| HMSR-113 | [hse.HseClosure](#hse-hseclosure) | REVISE | HMR-096 |
| HMSR-114 | [hse.HseCorrectivePreventiveAction](#hse-hsecorrectivepreventiveaction) | REVISE | HMR-097 |
| HMSR-115 | [integrity.IntegrityCase](#integrity-integritycase) | REVISE | HMR-098 |
| HMSR-116 | [workflow.WorkflowStateHistory](#workflow-workflowstatehistory) | REVISE | HMR-099 |
| HMSR-117 | [alarm.Alarm](#alarm-alarm) | REVISE | HMR-100 |
| HMSR-118 | [audit.AuditAccessRecord](#audit-auditaccessrecord) | REVISE | HMR-101 |
| HMSR-119 | [audit.AuditBeforeAfterValue](#audit-auditbeforeaftervalue) | REVISE | HMR-102 |
| HMSR-120 | [monitoring.PlanActualDeviation](#monitoring-planactualdeviation) | REVISE | HMR-103 |
| HMSR-121 | [alarm.AlarmAcknowledgement](#alarm-alarmacknowledgement) | REVISE | HMR-104 |
| HMSR-122 | [alarm.AlarmClosure](#alarm-alarmclosure) | REVISE | HMR-105 |
| HMSR-123 | [alarm.AlarmShelving](#alarm-alarmshelving) | REVISE | HMR-106 |

## Alarm decisions

Canonical owner summary: [alarm module](../modules/alarm.md#permanent-semantic-decisions).

### alarm Alarm

Formal Alarm creation uses required French title and exact catalog families, writing exactly one initial raised event atomically. New writes cannot fabricate pre-existing lifecycle history; existing snapshot/history and used taxonomy are protected.

Evidence: [Alarm](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java); [AlarmJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java); [JpaAlarmRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmRepositoryAdapter.java); [AlarmSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java); [V20261008_022__hmr_100_alarm_lifecycle_integrity](../../src/main/resources/db/migration/V20261008_022__hmr_100_alarm_lifecycle_integrity.sql).

### alarm AlarmAcknowledgement

Alarm acknowledgement checks existing nonterminal alarm under the parent lock and atomically updates acknowledgement state with one action event. Multiple historical acknowledgements remain legal; replay is immutable and older evidence cannot move snapshot time backward.

Evidence: [AlarmAcknowledgement](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java); [AlarmAcknowledgementJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java); [JpaAlarmAcknowledgementRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java); [AlarmAcknowledgementSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java).

### alarm AlarmClosure

Normal Alarm close requires CLEARED state or clearedAt evidence; explicit cancellation is allowed without mandatory review workflow. Closure, terminal alarm fields and one event are atomic and unique; ESCALATED alone does not authorize close.

Evidence: [AlarmClosure](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java); [AlarmClosureJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java); [JpaAlarmClosureRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java); [AlarmClosureSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java); [V20261008_023__hmr_105_alarm_closure_integrity](../../src/main/resources/db/migration/V20261008_023__hmr_105_alarm_closure_integrity.sql).

### alarm AlarmShelving

Alarm shelving requires strict bounded interval, exact reason family and one ACTIVE open shelf. Parent locking serializes shelving with suppression; finish/expiry restores durable prior evidence without reopening terminal/cleared alarm or moving time backward, and appends one finish event at contractual due time.

Evidence: [AlarmShelving](../../src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java); [AlarmShelvingJpaEntity](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java); [JpaAlarmShelvingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java); [AlarmShelvingSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java); [V20261008_024__hmr_106_alarm_shelving_integrity](../../src/main/resources/db/migration/V20261008_024__hmr_106_alarm_shelving_integrity.sql).

## Analytics decisions

Canonical owner summary: [analytics module](../modules/analytics.md#permanent-semantic-decisions).

### analytics AnalyticsDataset

Analytics datasets own curated dataset identity, lineage/quality metadata and ordered optional validity. They describe analytical source metadata rather than operational ownership.

Evidence: [AnalyticsDataset](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java); [AnalyticsDatasetJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetJpaEntity.java); [JpaAnalyticsDatasetRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetRepositoryAdapter.java); [AnalyticsDatasetVersionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java).

### analytics MetricEvaluationRun

Metric evaluations require scope type, nonnegative counters and ordered period/run timing. Terminal completion and failure evidence are governed; definition-version applicability and owner scope are validated during evaluation.

Evidence: [MetricEvaluationRun](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java); [MetricEvaluationRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java); [JpaMetricEvaluationRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java); [MetricEvaluationRunSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java); [V20261004_016__hmr_016_analytics_metric_evaluation_run](../../src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql).

### analytics AnalyticsInsight

Analytics insights are advisory. Governed insight type, exact severity family and populated source-lineage references are validated; missing scope/classification cannot be invented by the application.

Evidence: [AnalyticsInsight](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java); [AnalyticsInsightJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java); [JpaAnalyticsInsightRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java); [AnalyticsInsightSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java); [V20261004_023__hmr_023_analytics_analytics_insight](../../src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql).

### analytics AnalyticsProjectionRun

Projection runs capture definition version on first persistence and preserve it thereafter. Successful terminal outcomes require source watermark; FAILED requires diagnostic evidence.

Evidence: [AnalyticsProjectionRun](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java); [AnalyticsProjectionRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java); [JpaAnalyticsProjectionRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java); [AnalyticsProjectionRunSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java); [V20261004_024__hmr_024_analytics_projection_run](../../src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql).

### analytics DigitalTwinReadinessAssessment

Digital-twin readiness requires scope type and a bounded readiness-status enum while scope ID remains optional. The domain explicitly reports no runtime digital twin.

Evidence: [DigitalTwinReadinessAssessment](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java); [DigitalTwinReadinessAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java); [JpaDigitalTwinReadinessAssessmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java); [DigitalTwinReadinessAssessmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java); [V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment](../../src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql).

### analytics AnalyticsDatasetVersion

Published dataset versions are immutable after persistence. Identical replay is a no-op; an unpublished version may transition to published without rewriting a published version.

Evidence: [AnalyticsDatasetVersion](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java); [AnalyticsDatasetVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java); [JpaAnalyticsDatasetVersionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java); [AnalyticsDatasetVersionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java).

### analytics MetricValue

MetricValue requires normalized nonblank scopeType with metric/run/version, period and quality provenance. A metric result does not acquire ownership of its evaluated operational scope.

Evidence: [MetricValue](../../src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java); [MetricValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java); [JpaMetricValueRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java); [MetricValueSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java).

## Assets decisions

Canonical owner summary: [assets module](../modules/assets.md#permanent-semantic-decisions).

### assets MaintainableAsset

MaintainableAsset represents maintenance identity, not physical Topology ownership. Registration requires an owner-resolved typed topology target and local parent integrity; self-parenting is rejected.

Evidence: [MaintainableAsset](../../src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java); [MaintainableAssetJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java); [JpaMaintainableAssetRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java); [MaintainableAssetSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java); [V20261004_045__hmr_045_assets_maintainable_asset](../../src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql).

### assets AssetConditionRecord

AssetConditionRecord is an Assets-owned dated observation for a MaintainableAsset, with required condition status and optional source/observer metadata. It is observation evidence rather than automatic maintenance authorization.

Evidence: [AssetConditionRecord](../../src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java); [AssetConditionRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetConditionRecordJpaEntity.java); [JpaAssetConditionRecordRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaAssetConditionRecordRepositoryAdapter.java).

### assets MaintenanceWorkOrder

Maintenance work orders require title and existing populated local plan reference. Fresh actor/unit/recommendation/workflow evidence is owner-validated; unchanged historical references remain, and plan existence does not invent asset equality.

Evidence: [MaintenanceWorkOrder](../../src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java); [MaintenanceWorkOrderJpaEntity](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java); [JpaMaintenanceWorkOrderRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java); [MaintenanceWorkOrderReferenceValidation](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/MaintenanceWorkOrderReferenceValidation.java); [MaintenanceWorkOrderSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java); [V20261008_011__hmr_069_assets_maintenance_work_order](../../src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql).

## Audit decisions

Canonical owner summary: [audit module](../modules/audit.md#permanent-semantic-decisions).

### audit AuditExportRequest

Audit exports require purpose and valid sanitized bounded JSON criteria. Sensitive nested data/credential patterns are rejected or redacted; supplied owners resolve, workflow existence is not approval, and the request writes access evidence with propagated failure.

Evidence: [AuditExportRequest](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java); [AuditExportRequestJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java); [JpaAuditExportRequestRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java); [AuditExportRequestSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java); [V20261007_006__hmr_083_audit_export_request](../../src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql).

### audit AuditEvent

Audit events require source/target module and target-type evidence, exact independent catalog families and sanitization through generic repository writes. Evidence inserts do not fall back to merge when insertion fails.

Evidence: [AuditEvent](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java); [AuditEventJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java); [JpaAuditEventRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java); [AuditEventSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java); [V20261007_007__hmr_095_audit_event](../../src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql).

### audit AuditAccessRecord

Audit access records are append-only access evidence. Optional AuditEvent/export references stay nullable but must exist when populated; insert failure cannot degrade into merge.

Evidence: [AuditAccessRecord](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java); [AuditAccessRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java); [JpaAuditAccessRecordRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java); [AuditAccessRecordSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java); [V20261007_008__hmr_101_audit_access_record](../../src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql).

### audit AuditBeforeAfterValue

Audit before/after evidence requires field path and parent event. Sensitive or masked raw text is rejected, hash-only masked evidence and unchanged values remain legal, optional mask reason resolves, and generic writes remain insert-only.

Evidence: [AuditBeforeAfterValue](../../src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java); [AuditBeforeAfterValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java); [JpaAuditBeforeAfterValueRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java); [AuditBeforeAfterValueSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java); [V20261007_009__hmr_102_audit_before_after_value](../../src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql).

## Configuration decisions

Canonical owner summary: [configuration module](../modules/configuration.md#permanent-semantic-decisions).

### configuration ConfigurationDefinition

Configuration definition defaults share the secret-material boundary with runtime values: reference-only secret settings cannot persist secret material. No unimplemented secret-reference syntax is invented.

Evidence: [ConfigurationDefinition](../../src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java); [ConfigurationDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java); [JpaConfigurationDefinitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationDefinitionRepositoryAdapter.java); [ConfigurationDefinitionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationDefinitionSemanticRemediationTest.java).

### configuration FeatureFlag

Feature flags require nonblank owningModule at command/domain/persistence boundaries. A flag is governed configuration metadata, not proof of the capability it names.

Evidence: [FeatureFlag](../../src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java); [FeatureFlagJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java); [JpaFeatureFlagRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaFeatureFlagRepositoryAdapter.java); [FeatureFlagSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/configuration/semantic/FeatureFlagSemanticRemediationTest.java); [V20261004_026__hmr_026_configuration_feature_flag](../../src/main/resources/db/migration/V20261004_026__hmr_026_configuration_feature_flag.sql).

### configuration ConfigurationValue

ConfigurationValue requires nonblank environment and existing populated definition-version reference, with ordered optional effective dates. Definition metadata and actual scoped value remain separate.

Evidence: [ConfigurationValue](../../src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java); [ConfigurationValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java); [JpaConfigurationValueRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationValueRepositoryAdapter.java); [ConfigurationValueSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationValueSemanticRemediationTest.java); [V20261004_039__hmr_039_configuration_configuration_value](../../src/main/resources/db/migration/V20261004_039__hmr_039_configuration_configuration_value.sql).

## Custody decisions

Canonical owner summary: [custody module](../modules/custody.md#permanent-semantic-decisions).

### custody CustodyMeasurementPeriod

Custody measurement periods reference existing agreements and the same transfer point as their agreement, enforced by local composite integrity; period end cannot precede start.

Evidence: [CustodyMeasurementPeriod](../../src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java); [CustodyMeasurementPeriodJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java); [JpaCustodyMeasurementPeriodRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java); [CustodyMeasurementPeriodSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java); [V20261004_018__hmr_018_custody_custody_measurement_period](../../src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql).

### custody CustodyDiscrepancy

Custody discrepancies may omit quantityUnitId; every populated quantity unit is locally validated. Optionality is preserved rather than adding an unsupported mandatory unit rule.

Evidence: [CustodyDiscrepancy](../../src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java); [CustodyDiscrepancyJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java); [JpaCustodyDiscrepancyRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java); [CustodyDiscrepancySemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java); [V20261004_027__hmr_027_custody_custody_discrepancy](../../src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql).

### custody CustodyTransferTicket

Custody tickets validate populated local batch/calculation evidence and fresh actor/workflow references. Audit reference must belong to the exact ticket; optional references and unchanged historical evidence remain legal.

Evidence: [CustodyTransferTicket](../../src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java); [CustodyTransferTicketJpaEntity](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java); [CustodyTransferTicketReferenceValidation](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyTransferTicketReferenceValidation.java); [JpaCustodyTransferTicketRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java); [CustodyTransferTicketSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java); [V20261008_012__hmr_070_custody_custody_transfer_ticket](../../src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql).

## Documents decisions

Canonical owner summary: [documents module](../modules/documents.md#permanent-semantic-decisions).

### documents DocumentStorageObject

Storage objects carry opaque binary location, nonnegative size, content type, checksum and creation time. Signed URLs/credential-bearing keys are rejected; encryption metadata is reference-only.

Evidence: [DocumentStorageObject](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java); [DocumentStorageObjectJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java); [JpaDocumentStorageObjectRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java); [DocumentStorageObjectSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java); [V20261004_008__hmr_008_documents_document_storage_object](../../src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql).

### documents Document

Document identity requires French title, canonical creator display and complete-or-absent owner tuple. Its current version must belong to the same document; owner resolution is unambiguous and classification uses exact families.

Evidence: [Document](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java); [DocumentJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java); [JpaDocumentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentRepositoryAdapter.java); [DocumentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java); [V20261007_003__hmr_067_documents_document](../../src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql); [DocumentContentTransferService](../../src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java).

### documents DocumentVersion

Document versions require positive numbering, upload/content/checksum metadata and non-self optional supersession. Supplied workflow/supersession references are validated and uploader display comes from Identity; version-number races are protected without renumbering history.

Evidence: [DocumentVersion](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java); [DocumentVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java); [JpaDocumentVersionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java); [DocumentVersionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java); [V20261007_004__hmr_068_documents_document_version](../../src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql).

### documents DocumentTargetLink

DocumentTargetLink requires target module/type/ID, owner-resolved canonical target snapshots and authenticated linking actor. Supplied document version belongs to the linked document; active exact link-role family is required.

Evidence: [DocumentTargetLink](../../src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java); [DocumentTargetLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java); [JpaDocumentTargetLinkRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java); [DocumentTargetLinkSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java); [V20261007_005__hmr_084_documents_document_target_link](../../src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql).

## Hse decisions

Canonical owner summary: [hse module](../modules/hse.md#permanent-semantic-decisions).

### hse PermitToWork

PermitToWork carries HSE-owned permit lifecycle and a bounded ordered validity interval. Neutral target/requester/approver/workflow references do not transfer ownership or establish field-system control.

Evidence: [PermitToWork](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/PermitToWork.java); [PermitToWorkJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/PermitToWorkJpaEntity.java); [JpaPermitToWorkRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaPermitToWorkRepositoryAdapter.java).

### hse HseCase

HSE case closure checks existing parent and authoritative lifecycle/attestations, preserving neutral references and unrelated fields. Closure does not become a generic direct overwrite of the case.

Evidence: [HseCase](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java); [HseCaseJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java); [HseCaseReferenceValidation](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/HseCaseReferenceValidation.java); [JpaHseCaseRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java); [HseCaseSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/hse/semantic/HseCaseSemanticRemediationTest.java); [V20261008_014__hmr_082_hse_case_lifecycle](../../src/main/resources/db/migration/V20261008_014__hmr_082_hse_case_lifecycle.sql).

### hse HseClosure

HSE closure delegates to the authoritative lifecycle, using canonical actor/server time and matching Workflow context. Evidence IDs cannot merge on replay; regulatory review remains optional where the contract permits it.

Evidence: [HseClosure](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java); [HseClosureJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java); [JpaHseClosureRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureRepositoryAdapter.java); [HseClosureSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/hse/semantic/HseClosureSemanticRemediationTest.java); [V20261008_015__hmr_096_hse_closure_atomic_evidence](../../src/main/resources/db/migration/V20261008_015__hmr_096_hse_closure_atomic_evidence.sql).

### hse HseCorrectivePreventiveAction

HSE CAPA uses approved policy metadata for exact fresh action family and owner-validated unit/work order/actor/workflow task. Unchanged inactive history is retained; no unsupported closed-parent exclusion or verifier-state rule is added.

Evidence: [HseCorrectivePreventiveAction](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java); [HseCorrectivePreventiveActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java); [JpaHseCorrectivePreventiveActionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCorrectivePreventiveActionRepositoryAdapter.java); [HseCorrectivePreventiveActionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/hse/semantic/HseCorrectivePreventiveActionSemanticRemediationTest.java); [V20261008_016__hmr_097_hse_capa_reference_catalog_integrity](../../src/main/resources/db/migration/V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql).

## Identity decisions

Canonical owner summary: [identity module](../modules/identity.md#permanent-semantic-decisions).

### identity Role

Identity Role is an authorization package with unique code. It is neither a Party business role nor an Organization position; grants have their own eligibility rules.

Evidence: [Role](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java); [RoleJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java); [JpaRoleRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java); [RoleSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/RoleSemanticRemediationTest.java); [V20261004_007__hmr_007_identity_role](../../src/main/resources/db/migration/V20261004_007__hmr_007_identity_role.sql).

### identity IdentityProvider

Identity provider code is unique and its name/audit timestamps are required. ACTIVE OIDC configuration requires issuer evidence; configuration metadata does not establish support for every named provider protocol.

Evidence: [IdentityProvider](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java); [IdentityProviderJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java); [JpaIdentityProviderRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java); [IdentityProviderSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java); [V20261004_010__hmr_010_identity_identity_provider](../../src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql).

### identity Permission

Permission is a canonical atomic authorization capability with unique lower-case `<context>:<resource>:<action>` code and required domain/resource/action/timestamps. Effective permission grants exclude inactive permissions.

Evidence: [Permission](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java); [PermissionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java); [JpaPermissionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaPermissionRepositoryAdapter.java); [PermissionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/PermissionSemanticRemediationTest.java); [V20261004_011__hmr_011_identity_permission](../../src/main/resources/db/migration/V20261004_011__hmr_011_identity_permission.sql).

### identity ExternalRoleMapping

ExternalRoleMapping relates an external provider role/claim to a Hidra Role. Mapping does not transfer authorization ownership to the provider or make external roles equivalent to Party business roles.

Evidence: [ExternalRoleMapping](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java); [ExternalRoleMappingJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java); [JpaExternalRoleMappingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaExternalRoleMappingRepositoryAdapter.java).

### identity GroupRoleGrant

GroupRoleGrant assigns a Role to an Identity security Group with optional neutral scope and ordered effective interval. It is not membership in an Organization unit.

Evidence: [GroupRoleGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java); [GroupRoleGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java); [JpaGroupRoleGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaGroupRoleGrantRepositoryAdapter.java).

### identity RolePermissionGrant

RolePermissionGrant associates Identity Role/Permission with explicit GRANT or DENY and ordered validity. Its optional condition is authorization evidence rather than foreign aggregate ownership.

Evidence: [RolePermissionGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java); [RolePermissionGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java); [JpaRolePermissionGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRolePermissionGrantRepositoryAdapter.java).

### identity User

Identity User requires nonblank unique username, unique populated email and owner-resolved optional Employee reference. It is security identity rather than the Organization employee master.

Evidence: [User](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java); [UserJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java); [JpaUserRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java); [UserSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java); [V20261006_010__hmr_063_identity_user_uniqueness](../../src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql).

### identity AuthenticationEvent

AuthenticationEvent records authentication outcome/protocol/time and optional security provenance. It is security evidence rather than credentials, access-token storage or operational actor ownership.

Evidence: [AuthenticationEvent](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java); [AuthenticationEventJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthenticationEventJpaEntity.java); [JpaAuthenticationEventRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthenticationEventRepositoryAdapter.java).

### identity AuthorizationDecision

Authorization decisions preserve participating grant/policy/constraint evidence at one evaluation instant. Explicit DENY dominates permits/unresolved policy; constraints restrict existing authority and cannot invent authority; unsupported expressions/obligations fail closed.

Evidence: [AuthorizationDecision](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java); [AuthorizationDecisionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java); [JpaAuthorizationDecisionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDecisionRepositoryAdapter.java); [AuthorizationDecisionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDecisionSemanticRemediationTest.java); [IdentityAuthorizationApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java).

### identity AuthorizationDelegationGrant

Authorization delegation requires reason and bounded validity. Supplied role/permission references exist; neither exclusivity nor a role-or-permission requirement is invented when both are optional.

Evidence: [AuthorizationDelegationGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java); [AuthorizationDelegationGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java); [JpaAuthorizationDelegationGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDelegationGrantRepositoryAdapter.java); [AuthorizationDelegationGrantSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDelegationGrantSemanticRemediationTest.java); [V20261006_011__hmr_086_delegation_contract](../../src/main/resources/db/migration/V20261006_011__hmr_086_delegation_contract.sql).

### identity HidraPrincipal

HidraPrincipal is normalized runtime authenticated identity, not a JPA aggregate. Stable user/name/authentication type are required and roles/permissions become immutable normalized sets; provider-independent identity remains Hidra-owned.

Evidence: [HidraPrincipal](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java).

### identity LocalCredential

LocalCredential persists only required one-way password hash and credential lifecycle metadata for a user. Plaintext password remains transient input and is not domain persistence evidence.

Evidence: [LocalCredential](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java); [LocalCredentialJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java); [JpaLocalCredentialRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLocalCredentialRepositoryAdapter.java).

### identity LoginSession

LoginSession stores metadata without tokens, with mandatory known authentication protocol. External identity provenance is exact; nullable local-provider references remain nullable, termination is idempotent and expiry does not rewrite last activity.

Evidence: [LoginSession](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java); [LoginSessionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java); [JpaLoginSessionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java); [LoginSessionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java); [V20261006_012__hmr_087_login_session_evidence](../../src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql).

### identity UserPermissionGrant

Direct UserPermissionGrant requires reason and validTo, including emergency access; SUSPENDED is unsupported. These bounds do not extend to ordinary role grants.

Evidence: [UserPermissionGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java); [UserPermissionGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java); [JpaUserPermissionGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java); [UserPermissionGrantSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/UserPermissionGrantSemanticRemediationTest.java); [V20261006_013__hmr_088_direct_permission_bounds](../../src/main/resources/db/migration/V20261006_013__hmr_088_direct_permission_bounds.sql).

### identity UserRoleGrant

The ordinary UserRoleGrant application assignment path requires an ACTIVE user before persistence. Optional reason/end may remain absent; direct-permission and delegation bounds do not silently become role-grant rules.

Evidence: [UserRoleGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java); [UserRoleGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java); [JpaUserRoleGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java); [UserRoleGrantSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/identity/semantic/UserRoleGrantSemanticRemediationTest.java); [IdentityAdministrationCommandApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java).

## Incident decisions

Canonical owner summary: [incident module](../modules/incident.md#permanent-semantic-decisions).

### incident Incident

Incident creation requires canonical actor evidence and exact catalog/time rules. Detection cannot follow reporting without an estimated-time model; lifecycle timestamps couple to their states and CLOSED requires responsible owner evidence.

Evidence: [Incident](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java); [IncidentJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java); [JpaIncidentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRepositoryAdapter.java); [IncidentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentSemanticRemediationTest.java); [V20261008_007__hmr_062_incident_reference_lifecycle_integrity](../../src/main/resources/db/migration/V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql).

### incident IncidentClosure

Incident closure requires RESOLVED parent, resolution and policy-required persisted evidence plus true confirmations. Required Workflow approval is owner-proven; canonical closer/server time and closure/parent update commit atomically, with one immutable decision.

Evidence: [IncidentClosure](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java); [IncidentClosureJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java); [JpaIncidentClosureRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureRepositoryAdapter.java); [IncidentClosureSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentClosureSemanticRemediationTest.java); [V20261008_010__hmr_090_incident_closure_governance](../../src/main/resources/db/migration/V20261008_010__hmr_090_incident_closure_governance.sql).

### incident IncidentRelatedIncident

Incident relationships require explicit governed relationship policy, existing incident IDs and distinct normalized endpoints. Symmetric inverse duplicates are guarded while directional relationships and optional comments remain meaningful.

Evidence: [IncidentRelatedIncident](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java); [IncidentRelatedIncidentJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java); [JpaIncidentRelatedIncidentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRelatedIncidentRepositoryAdapter.java); [IncidentRelatedIncidentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentRelatedIncidentSemanticRemediationTest.java); [V20261008_008__hmr_091_incident_relationship_integrity](../../src/main/resources/db/migration/V20261008_008__hmr_091_incident_relationship_integrity.sql).

### incident IncidentResponseAction

Incident response actions require existing non-DRAFT/nonterminal parent and nonblank description. Parent/action concurrency prevents actions after committed closure; recorded work is not a remote-control command.

Evidence: [IncidentResponseAction](../../src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java); [IncidentResponseActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java); [JpaIncidentResponseActionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentResponseActionRepositoryAdapter.java); [IncidentResponseActionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentResponseActionSemanticRemediationTest.java); [V20261008_009__hmr_092_incident_response_action_integrity](../../src/main/resources/db/migration/V20261008_009__hmr_092_incident_response_action_integrity.sql).

## Integration decisions

Canonical owner summary: [integration module](../modules/integration.md#permanent-semantic-decisions).

### integration IntegrationJobRun

Integration run numbers are database-allocated rather than caller-owned. Counters and completion timing are checked, terminal runs cannot reopen, and manual runs require eligible job and actor provenance.

Evidence: [IntegrationJobRun](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java); [IntegrationJobRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java); [JpaIntegrationJobRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java); [IntegrationJobRunSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java); [V20261004_014__hmr_014_integration_integration_job_run](../../src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql).

### integration ExternalSystem

ExternalSystem is Integration-owned registry/configuration. Its code is unique under concurrent persistence; optional Organization attribution is scalar rather than a cross-module FK.

Evidence: [ExternalSystem](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java); [ExternalSystemJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java); [JpaExternalSystemRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java); [ExternalSystemSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java); [V20261004_047__hmr_047_integration_external_system](../../src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql).

### integration IntegrationExchangeMessage

Integration exchange messages preserve external system and exact message/payload families. Optional job-run must exist, optional endpoint must belong to the external system; absent optional references remain legal.

Evidence: [IntegrationExchangeMessage](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java); [IntegrationExchangeMessageJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java); [JpaIntegrationExchangeMessageRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java); [IntegrationExchangeMessageSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java); [V20261007_010__hmr_056_integration_exchange_message](../../src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql).

### integration IntegrationDeadLetterRecord

Dead letters require failure stage/reason. Manual actor/time/comment form an all-or-none trio; fresh actor must match authenticated eligible identity, recorded manual provenance is immutable, and each supplied local evidence ID must exist.

Evidence: [IntegrationDeadLetterRecord](../../src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java); [IntegrationDeadLetterRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java); [JpaIntegrationDeadLetterRecordRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java); [IntegrationDeadLetterRecordSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java); [V20261007_011__hmr_071_integration_dead_letter_record](../../src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql).

## Integrity decisions

Canonical owner summary: [integrity module](../modules/integrity.md#permanent-semantic-decisions).

### integrity PipelineDefect

PipelineDefect may omit sourceFindingId; every populated source finding must exist in Integrity. This local provenance does not create foreign ownership.

Evidence: [PipelineDefect](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java); [PipelineDefectJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java); [JpaPipelineDefectRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaPipelineDefectRepositoryAdapter.java); [PipelineDefectSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integrity/semantic/PipelineDefectSemanticRemediationTest.java); [V20261004_019__hmr_019_integrity_pipeline_defect](../../src/main/resources/db/migration/V20261004_019__hmr_019_integrity_pipeline_defect.sql).

### integrity IntegrityProgram

Integrity programs require active exact INTEGRITY_PROGRAM_TYPE metadata for fresh classification. Populated Organization owner resolves through its contract and snapshots retain owner evidence.

Evidence: [IntegrityProgram](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java); [IntegrityProgramJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java); [JpaIntegrityProgramRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityProgramRepositoryAdapter.java); [IntegrityProgramSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityProgramSemanticRemediationTest.java).

### integrity IntegrityAssessment

Integrity assessments validate populated local program and fresh actor/workflow references, retain optionality and unchanged historical evidence, and propagate owner failure before persistence.

Evidence: [IntegrityAssessment](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java); [IntegrityAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java); [IntegrityAssessmentReferenceValidation](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/IntegrityAssessmentReferenceValidation.java); [JpaIntegrityAssessmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityAssessmentRepositoryAdapter.java); [IntegrityAssessmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityAssessmentSemanticRemediationTest.java); [V20261008_013__hmr_072_integrity_integrity_assessment](../../src/main/resources/db/migration/V20261008_013__hmr_072_integrity_integrity_assessment.sql).

### integrity IntegrityCase

Integrity cases require approved fresh taxonomy, typed owner-resolved topology, existing optional defect and owner-validated actor/unit/workflow. Unchanged historical classification/snapshots remain; temporal ordering does not invent CLOSED timestamp coupling or defect/topology equality.

Evidence: [IntegrityCase](../../src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java); [IntegrityCaseJpaEntity](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java); [IntegrityCaseReferenceValidation](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/IntegrityCaseReferenceValidation.java); [JpaIntegrityCaseRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityCaseRepositoryAdapter.java); [IntegrityCaseSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityCaseSemanticRemediationTest.java); [V20261008_017__hmr_098_integrity_case_catalog_field_policy](../../src/main/resources/db/migration/V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql); [V20261008_018__hmr_098_integrity_case_reference_integrity](../../src/main/resources/db/migration/V20261008_018__hmr_098_integrity_case_reference_integrity.sql).

## Leakdetection decisions

Canonical owner summary: [leakdetection module](../modules/leakdetection.md#permanent-semantic-decisions).

### leakdetection LeakCandidate

Leak candidates require typed topology identity and audit timestamps. Severity agrees with confidence classification; optional run provenance must match the profile and eligible run, while runless creation requires an active profile. Fresh topology snapshots come from its owner.

Evidence: [LeakCandidate](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java); [LeakCandidateJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java); [JpaLeakCandidateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakCandidateRepositoryAdapter.java); [LeakCandidateSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakCandidateSemanticRemediationTest.java); [V20261004_015__hmr_015_leakdetection_leak_candidate](../../src/main/resources/db/migration/V20261004_015__hmr_015_leakdetection_leak_candidate.sql).

### leakdetection LeakDetectionCase

Leak cases require supported typed topology evidence. Optional Organization ownership is validated only when populated, and historical snapshots are preserved without making ownership mandatory.

Evidence: [LeakDetectionCase](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java); [LeakDetectionCaseJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java); [JpaLeakDetectionCaseRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakDetectionCaseRepositoryAdapter.java); [LeakDetectionCaseSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakDetectionCaseSemanticRemediationTest.java).

### leakdetection LeakEscalationReference

Leak escalation keeps optional candidate nullable but validates every populated candidate locally. Existing candidate is sufficient; no unsupported equality with case primary candidate is imposed, and external target remains neutral.

Evidence: [LeakEscalationReference](../../src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java); [LeakEscalationReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java); [JpaLeakEscalationReferenceRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakEscalationReferenceRepositoryAdapter.java); [LeakEscalationReferenceSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakEscalationReferenceSemanticRemediationTest.java); [V20261006_001__hmr_059_leak_escalation_candidate_integrity](../../src/main/resources/db/migration/V20261006_001__hmr_059_leak_escalation_candidate_integrity.sql).

## Monitoring decisions

Canonical owner summary: [monitoring module](../modules/monitoring.md#permanent-semantic-decisions).

### monitoring MonitoringRule

MonitoringRule may omit its Telemetry point; every populated point is resolved by Telemetry. The consumer retains a scalar reference rather than a cross-module database FK.

Evidence: [MonitoringRule](../../src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java); [MonitoringRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java); [JpaMonitoringRuleRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java); [MonitoringRuleSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java); [V20261004_040__hmr_040_monitoring_monitoring_rule](../../src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql).

### monitoring PlanActualDeviation

PlanActualDeviation retains 20 fields, optional references and severity fallback. Mandatory Planning target resolves through its scalar export; fresh point/reading IDs agree, optional evaluation is locked with populated context compatibility, and unchanged historical snapshot/evidence is not refreshed.

Evidence: [PlanActualDeviation](../../src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java); [PlanActualDeviationJpaEntity](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java); [JpaPlanActualDeviationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java); [PlanActualDeviationReferenceValidation](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/PlanActualDeviationReferenceValidation.java); [PlanActualDeviationSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java); [V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity](../../src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql); [DeviationApplicationService](../../src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java).

## Notification decisions

Canonical owner summary: [notification module](../modules/notification.md#permanent-semantic-decisions).

### notification NotificationTemplate

Notification templates require French name and audit timestamps. An ACTIVE template selects an eligible active current version; every supplied default channel must be active, and wrong catalog families are rejected.

Evidence: [NotificationTemplate](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java); [NotificationTemplateJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java); [JpaNotificationTemplateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java); [NotificationTemplateSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java); [V20261004_012__hmr_012_notification_notification_template](../../src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql).

### notification NotificationRequest

Notification requests require source module/event identity and governed category/priority/policy/template references. Request provenance remains owned by the emitting context.

Evidence: [NotificationRequest](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java); [NotificationRequestJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java); [JpaNotificationRequestRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java); [NotificationRequestSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java); [V20261004_035__hmr_035_notification_notification_request](../../src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql).

### notification NotificationMessage

Notification composition checks recipient/channel/priority context before writing. READY/SCHEDULED templated messages require exact version and render-input evidence; manual content remains legal, and valid composition flushes before dispatch.

Evidence: [NotificationMessage](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java); [NotificationMessageJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java); [JpaNotificationMessageRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java); [NotificationMessageSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java); [V20261006_005__hmr_052_notification_message_composition](../../src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql); [V20261006_007__hmr_052_qualify_message_validator_parameter](../../src/main/resources/db/migration/V20261006_007__hmr_052_qualify_message_validator_parameter.sql).

### notification NotificationDeliveryAttempt

Delivery attempts are insert-only immutable evidence with matching message/channel. Permanent failure/cancellation cannot schedule automatic retry; duplicate attempt IDs cannot merge over first-writer evidence.

Evidence: [NotificationDeliveryAttempt](../../src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java); [NotificationDeliveryAttemptJpaEntity](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java); [JpaNotificationDeliveryAttemptRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java); [NotificationDeliveryAttemptSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java); [V20261006_006__hmr_060_notification_attempt_evidence](../../src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql).

## Organization decisions

Canonical owner summary: [organization module](../modules/organization.md#permanent-semantic-decisions).

### organization OrganizationUnitType

Organization unit classification uses normalized language-neutral codes and embedded Arabic/French/English text. A station unit denotes organizational responsibility, distinct from a physical Topology facility.

Evidence: [OrganizationUnitType](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java); [OrganizationUnitTypeJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java); [JpaOrganizationUnitTypeRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitTypeRepositoryAdapter.java); [OrganizationEmbeddedMultilingualIntegrityTest](../../src/test/java/dz/sh/hidra/modules/organization/OrganizationEmbeddedMultilingualIntegrityTest.java); [V20260927_003__retire_organization_unit_type_translation_table](../../src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql).

### organization AdministrativeState

AdministrativeState is the Organization-owned wilaya reference with normalized code and nullable embedded localized names; it is the parent of administrative districts.

Evidence: [AdministrativeState](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java); [AdministrativeStateJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java); [JpaAdministrativeStateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaAdministrativeStateRepositoryAdapter.java).

### organization OperationalScope

OperationalScope has an independent positive registry identity, distinct from its owner-native target ID. GLOBAL has no target; supported entity-backed targets resolve through owner contracts; CUSTOM requires separate approval.

Evidence: [OperationalScope](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OperationalScope.java); [OperationalScopeJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OperationalScopeJpaEntity.java); [OperationalScopeEvidenceInventoryTest](../../src/test/java/dz/sh/hidra/modules/organization/OperationalScopeEvidenceInventoryTest.java); [OperationalScopeTargetResolverPortTest](../../src/test/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPortTest.java).

### organization Position

Organization Position requires a level in domain and persistence. Position describes operational function and does not itself grant Identity permissions.

Evidence: [Position](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java); [PositionJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java); [JpaPositionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaPositionRepositoryAdapter.java); [PositionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/PositionSemanticRemediationTest.java); [V20261004_020__hmr_020_organization_position](../../src/main/resources/db/migration/V20261004_020__hmr_020_organization_position.sql).

### organization Shift

Shift start/end/timezone text is required and trimmed. The current rule rejects blank schedule metadata without inventing a time format or parsing opaque schedule text.

Evidence: [Shift](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java); [ShiftJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java); [JpaShiftRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftRepositoryAdapter.java); [ShiftSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftSemanticRemediationTest.java); [V20261004_021__hmr_021_organization_shift](../../src/main/resources/db/migration/V20261004_021__hmr_021_organization_shift.sql).

### organization OrganizationContactPoint

OrganizationContactPoint is the typed operational contact channel for employees or units. Each target type resolves against its corresponding Organization repository; it does not revive direct Employee contact ownership.

Evidence: [OrganizationContactPoint](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java); [OrganizationContactPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationContactPointJpaEntity.java); [JpaOrganizationContactPointRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationContactPointRepositoryAdapter.java); [OrganizationContactPointApplicationServiceTest](../../src/test/java/dz/sh/hidra/modules/organization/application/service/OrganizationContactPointApplicationServiceTest.java); [OrganizationContactPointTargetValidatorTest](../../src/test/java/dz/sh/hidra/modules/organization/application/service/OrganizationContactPointTargetValidatorTest.java).

### organization ReportingLine

Reporting lines use typed source/target subjects and catalog-backed line type. Fresh subjects are eligible, active line hierarchy and cycle rules are enforced, and self-links/invalid effective intervals are rejected.

Evidence: [ReportingLine](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java); [ReportingLineJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java); [JpaReportingLineRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaReportingLineRepositoryAdapter.java); [ReportingLineSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/ReportingLineSemanticRemediationTest.java); [V20261004_028__hmr_028_organization_reporting_line](../../src/main/resources/db/migration/V20261004_028__hmr_028_organization_reporting_line.sql).

### organization OrganizationUnit

Organization units require validFrom, eligible fresh unit type and acyclic hierarchy. A valid unchanged inactive historical type may remain; hierarchy ownership is distinct from operational scope responsibility.

Evidence: [OrganizationUnit](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java); [OrganizationUnitJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java); [JpaOrganizationUnitRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitRepositoryAdapter.java); [OrganizationUnitSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationUnitSemanticRemediationTest.java); [V20261004_032__hmr_032_organization_organization_unit](../../src/main/resources/db/migration/V20261004_032__hmr_032_organization_organization_unit.sql).

### organization AdministrativeDistrict

AdministrativeDistrict is the Organization-owned daira reference under its AdministrativeState, retaining normalized code and nullable embedded language labels.

Evidence: [AdministrativeDistrict](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java); [AdministrativeDistrictJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java); [JpaAdministrativeDistrictRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaAdministrativeDistrictRepositoryAdapter.java).

### organization ResponsibilityAssignment

Responsibility assignments store the operational-scope registry ID, not the owner target ID. Assignment locks the scope, checks authorization and eligible assignee/owner, rejects overlapping active periods and accepts adjacent half-open periods; exact replay is idempotent.

Evidence: [ResponsibilityAssignment](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java); [ResponsibilityAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java); [JpaResponsibilityAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaResponsibilityAssignmentRepositoryAdapter.java); [ResponsibilityAssignmentApplicationServiceTest](../../src/test/java/dz/sh/hidra/modules/organization/application/service/ResponsibilityAssignmentApplicationServiceTest.java); [ResponsibilityAssignmentTest](../../src/test/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignmentTest.java).

### organization AdministrativeLocality

AdministrativeLocality is the Organization-owned commune reference under its district; employee address/birth locality references do not transfer administrative geography ownership.

Evidence: [AdministrativeLocality](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java); [AdministrativeLocalityJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java); [JpaAdministrativeLocalityRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaAdministrativeLocalityRepositoryAdapter.java).

### organization Employee

Employee owns operational person identity and structured Arabic/Latin names; display names derive deterministically. Birth data is optional and chronologically checked; contacts belong to OrganizationContactPoint and Identity user linkage remains neutral.

Evidence: [Employee](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/Employee.java); [EmployeeJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java); [JpaEmployeeRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeRepositoryAdapter.java); [OrganizationRestMapperEmployeePersonalDataTest](../../src/test/java/dz/sh/hidra/modules/organization/api/rest/mapper/OrganizationRestMapperEmployeePersonalDataTest.java); [OrganizationApplicationMapperEmployeeDisplayNameTest](../../src/test/java/dz/sh/hidra/modules/organization/application/mapper/OrganizationApplicationMapperEmployeeDisplayNameTest.java).

### organization EmployeeAddress

EmployeeAddress is an Organization-owned typed, effective-dated address for an employee with locality reference and optional postal/street snapshots. Embedded labels do not become identity.

Evidence: [EmployeeAddress](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java); [EmployeeAddressJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java); [JpaEmployeeAddressRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeAddressRepositoryAdapter.java).

### organization EmployeeAssignment

EmployeeAssignment requires an existing ACTIVE unit for new assignment and keeps employee/position/unit ownership inside Organization; inactive/missing unit cannot persist through the assignment path.

Evidence: [EmployeeAssignment](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java); [EmployeeAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java); [JpaEmployeeAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeAssignmentRepositoryAdapter.java); [EmployeeAssignmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/EmployeeAssignmentSemanticRemediationTest.java).

### organization OrganizationDelegation

Organization delegation requires distinct employees, existing responsibility identity and a strict bounded interval; revokedAt cannot precede validFrom. Its optional reason is not given unsupported mandatory semantics.

Evidence: [OrganizationDelegation](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java); [OrganizationDelegationJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java); [JpaOrganizationDelegationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationDelegationRepositoryAdapter.java); [OrganizationDelegationSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationDelegationSemanticRemediationTest.java); [V20261006_002__hmr_074_responsibility_assignment_id_required](../../src/main/resources/db/migration/V20261006_002__hmr_074_responsibility_assignment_id_required.sql); [OrganizationMandatoryReferenceMigrationSupport](../../src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationMandatoryReferenceMigrationSupport.java).

### organization OrganizationHierarchySnapshot

Organization hierarchy snapshots require snapshot ID/code, capture time, capturing employee and lifecycle status. They preserve historical hierarchy payload/display rather than replacing the live hierarchy.

Evidence: [OrganizationHierarchySnapshot](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java); [OrganizationHierarchySnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java); [JpaOrganizationHierarchySnapshotRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationHierarchySnapshotRepositoryAdapter.java); [OrganizationHierarchySnapshotSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationHierarchySnapshotSemanticRemediationTest.java); [V20261006_003__hmr_075_captured_by_employee_id_required](../../src/main/resources/db/migration/V20261006_003__hmr_075_captured_by_employee_id_required.sql); [OrganizationMandatoryReferenceMigrationSupport](../../src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationMandatoryReferenceMigrationSupport.java).

### organization ShiftAssignment

ShiftAssignment requires employee, shift, unit and validFrom, with optional end strictly after start. It is an effective-dated Organization assignment rather than an Identity permission grant.

Evidence: [ShiftAssignment](../../src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java); [ShiftAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java); [JpaShiftAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftAssignmentRepositoryAdapter.java); [ShiftAssignmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftAssignmentSemanticRemediationTest.java); [V20261006_004__hmr_076_organization_unit_id_required](../../src/main/resources/db/migration/V20261006_004__hmr_076_organization_unit_id_required.sql); [OrganizationMandatoryReferenceMigrationSupport](../../src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationMandatoryReferenceMigrationSupport.java).

## Party decisions

Canonical owner summary: [party module](../modules/party.md#permanent-semantic-decisions).

### party Party

Party owns external counterparty identity, required legal name and unique code. Business Party roles are distinct from Identity authorization roles and Organization positions.

Evidence: [Party](../../src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java); [PartyJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java); [JpaPartyRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRepositoryAdapter.java); [PartySemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/party/semantic/PartySemanticRemediationTest.java); [V20261004_004__hmr_004_party_party](../../src/main/resources/db/migration/V20261004_004__hmr_004_party_party.sql).

### party PartyRoleAssignment

Party role assignments preserve effective-dated business classification and reject duplicate ACTIVE party/role assignment; the partial unique index also protects concurrent writes.

Evidence: [PartyRoleAssignment](../../src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java); [PartyRoleAssignmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java); [JpaPartyRoleAssignmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRoleAssignmentRepositoryAdapter.java); [PartyRoleAssignmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/party/semantic/PartyRoleAssignmentSemanticRemediationTest.java); [V20261004_041__hmr_041_party_party_role_assignment](../../src/main/resources/db/migration/V20261004_041__hmr_041_party_party_role_assignment.sql).

## Planning decisions

Canonical owner summary: [planning module](../modules/planning.md#permanent-semantic-decisions).

### planning PlanningPeriod

Planning periods require a strict positive interval, French name, valid IANA timezone and active PERIOD_TYPE classification when created. Blank application timezone defaults to Africa/Algiers; closed periods cannot admit new revisions.

Evidence: [PlanningPeriod](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java); [PlanningPeriodJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java); [JpaPlanningPeriodRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java); [PlanningPeriodSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java); [V20261004_006__hmr_006_planning_planning_period](../../src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql).

### planning PlanRevision

Plan revisions require positive number and non-self optional base lineage. Approved metadata is guarded; exact active revision-reason family is required when fresh. Updating the current revision checks expected token and current-plan pointer.

Evidence: [PlanRevision](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java); [PlanRevisionJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java); [JpaPlanRevisionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java); [PlanRevisionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java); [V20261007_001__hmr_064_planning_plan_revision](../../src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql).

### planning OperationalPlan

Operational plans require typed owner-resolved scope, French name and authenticated eligible creator. Optional unit is validated when supplied; current/approved revision pointers must exist and belong to that plan.

Evidence: [OperationalPlan](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java); [OperationalPlanJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java); [JpaOperationalPlanRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java); [OperationalPlanSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java); [V20261007_002__hmr_065_planning_operational_plan](../../src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql).

### planning Nomination

Nominations require positive quantity, strict period and audit timestamps, same-revision scenario and exact NOMINATION_TYPE. Fresh Custody product and Telemetry quantity/rate roles require approved actual-ID mappings and explicit unit pairs when rate unit is supplied; unchanged inactive history and snapshots remain.

Evidence: [Nomination](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java); [NominationJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java); [JpaNominationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java); [NominationReferenceValidation](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/NominationReferenceValidation.java); [NominationSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java); [V20261008_025__hmr_080_nomination_owner_reference_policies](../../src/main/resources/db/migration/V20261008_025__hmr_080_nomination_owner_reference_policies.sql); [V20261008_026__hmr_080_planning_nomination_integrity](../../src/main/resources/db/migration/V20261008_026__hmr_080_planning_nomination_integrity.sql); [PlanningProductReferenceContract](../../src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java); [PlanningUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java).

### planning PlanTarget

PlanTarget retains 22 fields and existing status behavior; validTo may equal validFrom but cannot precede it. Own revision and optional nomination/scenario are locked and same-revision. Exact TARGET_TYPE and an actual-ID approved value policy are required: NUMERIC requires targetValue and nonblank unitId; TEXT requires nonblank targetTextValue. No mutual exclusivity is invented. Fresh topology/point snapshots come from owners; valid unchanged mappings/snapshots remain even after classification becomes inactive.

Evidence: [PlanTarget](../../src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java); [PlanTargetJpaEntity](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java); [JpaPlanTargetRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java); [PlanTargetReferenceValidation](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/PlanTargetReferenceValidation.java); [PlanTargetSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java); [V20261008_019__hmr_094_planning_target_value_policy](../../src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql); [V20261008_020__hmr_094_planning_plan_target_integrity](../../src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql); [PlanningTargetValuePolicy](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/configuration/PlanningTargetValuePolicy.java); [MonitoringPlanTargetReferenceContract](../../src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java).

## Reporting decisions

Canonical owner summary: [reporting module](../modules/reporting.md#permanent-semantic-decisions).

### reporting ReportDefinition

Report definitions have explicit lifecycle, French name, known owner module and exact category family. Compatibility creation starts DRAFT; an active selected template version must belong to the definition.

Evidence: [ReportDefinition](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java); [ReportDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java); [JpaReportDefinitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java); [ReportDefinitionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java); [V20261004_013__hmr_013_reporting_report_definition](../../src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql).

### reporting ReportRequest

Report requests require an ACTIVE definition on the request path. Populated external IDs cannot be blank, and restricted requests use the existing access contract rather than importing Identity internals.

Evidence: [ReportRequest](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java); [ReportRequestJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java); [JpaReportRequestRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java); [ReportRequestSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java); [V20261004_048__hmr_048_reporting_report_request](../../src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql).

### reporting ReportRun

Report queuing validates request state, matching active definition, eligible template and required parameters. Required approval is confirmed by Workflow owner, restricted access uses persisted requester scope; completed/failed run evidence remains required.

Evidence: [ReportRun](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java); [ReportRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java); [JpaReportRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java); [ReportRunSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java); [V20261007_012__hmr_057_reporting_report_run](../../src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql); [ReportingApplicationService](../../src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java).

### reporting ReportOutputArtifact

Report artifacts require checksum and at least one Documents storage/document reference. Every supplied owner reference must exist; both references may coexist without an invented completed-run-only rule.

Evidence: [ReportOutputArtifact](../../src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java); [ReportOutputArtifactJpaEntity](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java); [JpaReportOutputArtifactRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java); [ReportOutputArtifactSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java); [V20261007_013__hmr_093_reporting_report_output_artifact](../../src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql).

## Risk decisions

Canonical owner summary: [risk module](../modules/risk.md#permanent-semantic-decisions).

### risk RiskMatrixCell

Risk matrix cells require nonnegative score, exact likelihood/consequence catalog families and unique matrix coordinates. The selected cell supplies scoring truth, not an invented arithmetic combination.

Evidence: [RiskMatrixCell](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java); [RiskMatrixCellJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java); [JpaRiskMatrixCellRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java); [RiskMatrixCellSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java); [V20261004_029__hmr_029_risk_risk_matrix_cell](../../src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql).

### risk RiskRegister

Risk registers require complete typed scope identity. reviewFrequency remains opaque and is not silently reclassified as a Risk review-type catalog; Audit taxonomy provisioning remains Risk-specific.

Evidence: [RiskRegister](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java); [RiskRegisterJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java); [JpaRiskRegisterRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java); [RiskRegisterSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java); [V20261004_049__hmr_049_risk_risk_register](../../src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql).

### risk RiskAssessment

Risk assessments require structured owner-resolved scopes even when unscored DRAFT. Governed scoring uses selected matrix-cell value; approval requires actual review/approver/Workflow decision/Audit receipt and cannot enter APPROVED through generic save.

Evidence: [RiskAssessment](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java); [RiskAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java); [JpaRiskAssessmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java); [RiskAssessmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java); [V20261008_005__hmr_058_risk_assessment_governance](../../src/main/resources/db/migration/V20261008_005__hmr_058_risk_assessment_governance.sql); [RiskAssessmentGovernanceService](../../src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java).

### risk RiskEvidenceLink

Risk evidence links require module/type/ID and exactly one supported owner provider. Owner repositories prove the exact evidence type; available canonical snapshots replace caller labels without requiring unavailable optional labels.

Evidence: [RiskEvidenceLink](../../src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java); [RiskEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java); [JpaRiskEvidenceLinkRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java); [RiskEvidenceLinkSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java); [V20261008_004__hmr_077_risk_evidence_identity_integrity](../../src/main/resources/db/migration/V20261008_004__hmr_077_risk_evidence_identity_integrity.sql).

## Simulation decisions

Canonical owner summary: [simulation module](../modules/simulation.md#permanent-semantic-decisions).

### simulation SimulationModel

Simulation models require French name, supported topology scope and audit timestamps. Model identity is distinct from versioned executable configuration; model presence does not prove solver execution.

Evidence: [SimulationModel](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java); [SimulationModelJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java); [JpaSimulationModelRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java); [SimulationModelSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java); [V20261004_009__hmr_009_simulation_simulation_model](../../src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql).

### simulation SimulationScenario

Simulation scenarios require French name and creator display snapshot; model/version parenting and catalog identity are governed. Compatibility reference fallbacks deny unresolved owners rather than claiming availability.

Evidence: [SimulationScenario](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java); [SimulationScenarioJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java); [JpaSimulationScenarioRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java); [SimulationScenarioSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java); [V20261004_034__hmr_034_simulation_simulation_scenario](../../src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql).

### simulation SimulationRun

Simulation runs require scenario/version/input/solver-profile and run-type provenance. Persisted completed runs cannot be mutated; solver-profile presence is not evidence of external solver execution.

Evidence: [SimulationRun](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java); [SimulationRunJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java); [JpaSimulationRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java); [SimulationRunSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java).

### simulation SimulationOptimizationCandidate

Simulation optimization candidates carry run provenance, ordering, feasibility/rank and optional human selection evidence. A feasible/selected candidate is not an operational actuation command.

Evidence: [SimulationOptimizationCandidate](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java); [SimulationOptimizationCandidateJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationOptimizationCandidateJpaEntity.java); [JpaSimulationOptimizationCandidateRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationOptimizationCandidateRepositoryAdapter.java).

### simulation SimulationCandidateChange

Simulation candidate changes require content, active exact change catalog and owner-resolved target. Unchanged retired historical classification is preserved; unsupported compatibility lookup fails closed.

Evidence: [SimulationCandidateChange](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java); [SimulationCandidateChangeJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java); [JpaSimulationCandidateChangeRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java); [SimulationCandidateChangeSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java); [V20261008_001__hmr_078_simulation_candidate_change_integrity](../../src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql).

### simulation SimulationRecommendation

Simulation recommendations require content and coherent run/candidate/catalog references. Publication uses its audited port, actual publication time, flushed record and real Audit receipt; generic save/replay cannot bypass it, and optional actor remains optional.

Evidence: [SimulationRecommendation](../../src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java); [SimulationRecommendationJpaEntity](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java); [JpaSimulationRecommendationRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java); [SimulationRecommendationSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java); [V20261008_002__hmr_079_simulation_recommendation_integrity](../../src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql).

## Telemetry decisions

Canonical owner summary: [telemetry module](../modules/telemetry.md#permanent-semantic-decisions).

### telemetry TelemetryPoint

TelemetryPoint owns canonical signal identity and requires a French name. Point/signal/unit compatibility is governed by Telemetry metadata; consumers resolve point existence/code through Telemetry exports.

Evidence: [TelemetryPoint](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java); [TelemetryPointJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java); [JpaTelemetryPointRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java); [TelemetryPointSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java); [V20261004_005__hmr_005_telemetry_telemetry_point](../../src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql).

### telemetry TelemetrySource

Telemetry sources require French name, unique code and exact source/protocol families. ACTIVE governs ingestion eligibility; recognized embedded secret material and unsupported source lifecycle values are rejected.

Evidence: [TelemetrySource](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java); [TelemetrySourceJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java); [JpaTelemetrySourceRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java); [TelemetrySourceSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java); [V20261004_030__hmr_030_telemetry_telemetry_source](../../src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql).

### telemetry TelemetryReading

Raw Telemetry readings carry point/quality/time/provenance evidence. At most one typed value may be populated; exactly one is required except REJECTED/QUARANTINED evidence may be null-valued.

Evidence: [TelemetryReading](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java); [TelemetryReadingJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java); [JpaTelemetryReadingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java); [TelemetryReadingSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java); [V20261004_033__hmr_033_telemetry_telemetry_reading](../../src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql).

### telemetry TrustedTelemetryReading

Trusted readings derive from matching raw/point/PASSED assessment evidence with MEDIUM, HIGH or CERTIFIED trust and ACTIVE point. Optional unit/batch and binding provenance is checked; multiple applicable bindings require selection and historical snapshots survive retirement.

Evidence: [TrustedTelemetryReading](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java); [TrustedTelemetryReadingJpaEntity](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java); [JpaTrustedTelemetryReadingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java); [TrustedTelemetryReadingSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java); [V20261006_008__hmr_053_trusted_telemetry_gate](../../src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql).

## Topology decisions

Canonical owner summary: [topology module](../modules/topology.md#permanent-semantic-decisions).

### topology PipelineSystem

PipelineSystem uses extensible catalog-backed system type identity rather than a closed enum. Creation requires an explicitly known type code; only authoritative former codes were migrated.

Evidence: [PipelineSystem](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java); [PipelineSystemJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java); [JpaPipelineSystemRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java); [PipelineSystemSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java); [V20261004_022__hmr_022_topology_pipeline_system](../../src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql).

### topology TopologyConnection

Topology connections use extensible connection-type catalogs, distinct endpoints and optional existing segment reference. Nullable localized labels remain nullable; self-loops do not become valid graph edges.

Evidence: [TopologyConnection](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java); [TopologyConnectionJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java); [JpaTopologyConnectionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java); [TopologyConnectionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java); [V20261004_031__hmr_031_topology_topology_connection](../../src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql).

### topology Facility

Facility is a physical Topology identity. Every populated owner Party reference must exist through Party evidence; optional ownership remains optional.

Evidence: [Facility](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java); [FacilityJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java); [JpaFacilityRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java); [FacilitySemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java).

### topology Pipeline

Pipeline belongs to a PipelineSystem and uses an extensible catalog-backed pipeline type. Its physical network identity remains Topology-owned.

Evidence: [Pipeline](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java); [PipelineJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java); [JpaPipelineRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java); [PipelineSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java); [V20261004_042__hmr_042_topology_pipeline](../../src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql).

### topology Equipment

Equipment type remains extensible catalog identity. Optional topology attachments must exist; fresh manufacturer Party references resolve through the owner, while unchanged historical manufacturer snapshots are retained.

Evidence: [Equipment](../../src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java); [EquipmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java); [JpaEquipmentRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java); [EquipmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java); [V20261006_009__hmr_054_equipment_catalog_and_attachments](../../src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql).

## Workflow decisions

Canonical owner summary: [workflow module](../modules/workflow.md#permanent-semantic-decisions).

### workflow WorkflowDefinition

Workflow definitions require French name and positive version; code/version is unique. Active definitions cannot undergo structural mutation; lifecycle deactivation remains allowed.

Evidence: [WorkflowDefinition](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java); [WorkflowDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java); [JpaWorkflowDefinitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java); [WorkflowDefinitionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java); [V20261004_003__hmr_003_workflow_workflow_definition](../../src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql).

### workflow WorkflowStep

Workflow steps require nonnegative order and governed per-definition uniqueness; optional default assignment-rule reference must resolve consistently.

Evidence: [WorkflowStep](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java); [WorkflowStepJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java); [JpaWorkflowStepRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java); [WorkflowStepSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java); [V20261004_043__hmr_043_workflow_workflow_step](../../src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql).

### workflow WorkflowStepAssignmentRule

Workflow assignment rules need a concrete candidate-source strategy, such as supported candidate pool data. A rule with no usable source is rejected rather than inventing an assignee.

Evidence: [WorkflowStepAssignmentRule](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java); [WorkflowStepAssignmentRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java); [JpaWorkflowStepAssignmentRuleRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java); [WorkflowStepAssignmentRuleSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java).

### workflow WorkflowInstance

Workflow start requires authenticated eligible actor, active matching definition/version, exact catalog/target binding and exactly one resolving target owner. Canonical actor/target snapshots replace caller evidence; current step belongs to the definition.

Evidence: [WorkflowInstance](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java); [WorkflowInstanceJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java); [JpaWorkflowInstanceRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java); [WorkflowInstanceSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java); [V20261006_014__hmr_055_workflow_instance](../../src/main/resources/db/migration/V20261006_014__hmr_055_workflow_instance.sql).

### workflow WorkflowTransition

Workflow transitions require distinct same-definition steps and supported active configuration. Execution uses authenticated actor/permission evidence and optimistic task version; stale or unauthorized transitions cannot advance state.

Evidence: [WorkflowTransition](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java); [WorkflowTransitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java); [JpaWorkflowTransitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java); [WorkflowTransitionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java); [V20261006_015__hmr_061_workflow_transition](../../src/main/resources/db/migration/V20261006_015__hmr_061_workflow_transition.sql).

### workflow WorkflowTask

Actionable Workflow tasks require explicit assignment. Actor eligibility, live unit membership, claimable step, task catalogs and paired/ordered claim-completion evidence are checked; username snapshot alone cannot authorize execution, and terminal tasks cannot be overwritten.

Evidence: [WorkflowTask](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java); [WorkflowTaskJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java); [JpaWorkflowTaskRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java); [WorkflowTaskSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java); [V20261006_016__hmr_066_workflow_task](../../src/main/resources/db/migration/V20261006_016__hmr_066_workflow_task.sql).

### workflow WorkflowAction

Workflow action evidence uses canonical actor, server time and server-owned instance sequence. Generic comments cannot forge lifecycle/decision evidence; supplied task must belong to instance and configured decision/action type must agree.

Evidence: [WorkflowAction](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java); [WorkflowActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java); [JpaWorkflowActionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java); [WorkflowActionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java); [V20261006_017__hmr_081_workflow_action](../../src/main/resources/db/migration/V20261006_017__hmr_081_workflow_action.sql).

### workflow WorkflowStateHistory

Workflow history is append-only transition evidence with mandatory destination status and actor display. Populated task/step references exist and agree with instance/definition/source context; optional references stay optional.

Evidence: [WorkflowStateHistory](../../src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java); [WorkflowStateHistoryJpaEntity](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java); [JpaWorkflowStateHistoryRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java); [WorkflowStateHistorySemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java); [V20261006_018__hmr_099_workflow_state_history](../../src/main/resources/db/migration/V20261006_018__hmr_099_workflow_state_history.sql).

## Unresolved and deferred boundaries

Operational datasets still require approved mapping/eligibility facts before
fail-closed migration deployment. No production-row approval is established here.
Future retention/provenance governance is HPR-P2-010; testing governance is HPR-P2-011.
AI inference, an agent runtime, executing runtime digital twin, field actuation,
TimescaleDB/PostGIS and industrial protocol capability require their separately
approved implementation/evidence gates. Current advisory output and source metadata
must not be promoted into those capabilities. No new unresolved business requirement
is resolved by copying a legacy verdict.
