# Domain / Persistence Mirror Classification

**Recorded:** 2026-09-28  
**Roadmap:** HRA-060  
**Live main:** `26fcc45504a3a25f4631af261462d81d718eb1cf`  
**Historical baseline:** 393 exact behaviorless domain/JPA mirrors

## 1. Purpose

HRA-060 makes the domain/persistence split deliberate before the consolidated HRA-061 refactor.
It does not change Java behavior, database schema, Flyway migrations, or REST contracts.

The classification asks one question per exact domain/JPA mirror:

> Is this type currently acting as a real domain concept, as persistence/read scaffolding, or as an
> inbound/outbound boundary model?

## 2. Baseline reconciliation

The forensic baseline recorded **393 exact behaviorless domain/JPA clones**.

A deterministic replay over the later source snapshot used for repository remediation finds
**394 normalization-only exact mirrors**. Comparing the HRA-001 baseline commit
`c0e3c5d2bddd359fcd7dcb8ec77687fd2ecf3c9d` to the HRA-072 source-era head
`335d6f631630c79e4595aaadb604cf61ae40bd49` shows only one domain/JPA-shape file changed:
`organization.infrastructure.persistence.entity.EmployeeJpaEntity`. `Employee` is not part of
the normalization-only cohort. The one-pair difference is therefore a detector-methodology
reconciliation, not evidence of a hidden production model change.

To avoid silently omitting a candidate, HRA-060 classifies **all 394 replayed candidates**. This
strictly covers the historical 393-pair cohort plus one conservative extra candidate.

HRA-051 subsequently added constructor invariants to many of these records. That does not change the
HRA-060 population: the purpose here is architectural ownership, not re-counting whether a record now
contains validation code.

## 3. Classification rules

### REAL_DOMAIN

Classify a mirror as `REAL_DOMAIN` when live application/domain code actively uses the domain
representation, including any of these signals:

- an application service directly constructs, consumes, or returns the domain model;
- a domain service/policy consumes it;
- the model's repository port is actively injected/used by an application service;
- current orchestration depends on the model's identity/state rather than merely declaring a
  repository abstraction.

**HRA-061 disposition:** KEEP the framework-independent domain record and KEEP the JPA entity
separate. HRA-061 must not collapse the domain record into JPA.

### READ_PERSISTENCE_MODEL

Classify a mirror as `READ_PERSISTENCE_MODEL` when static usage is limited to repository plumbing:

- the domain record is referenced by its repository-port declaration;
- infrastructure adapter/repository/mapper code references it;
- no current application service, domain service/policy, or API boundary consumes the domain type;
- no current application service actively uses the corresponding repository port.

These are redundant persistence/read scaffolding candidates rather than proven business-domain
objects.

**HRA-061 disposition:** simplification is approved only after exact-head revalidation confirms the
same no-consumer condition. HRA-061 may remove redundant domain-mirror translation and unused
repository plumbing for such pairs, but it must:

- keep JPA entities in infrastructure;
- never add JPA annotations to framework-independent domain records;
- never change table/column/Flyway semantics;
- never remove a type merely because of a filename or zero static references if reflection,
  configuration, serialization, SQL, or generated-code evidence says otherwise;
- reclassify a pair to `REAL_DOMAIN` and leave it split if a live application/domain consumer is
  found.

### BOUNDARY_MODEL

Classify a mirror as `BOUNDARY_MODEL` when the domain record itself is directly exposed through a
current API or application input/output boundary without substantive domain ownership.

**HRA-061 disposition:** introduce/retain an application/API-owned boundary representation before
removing any domain mirror. Do not expose JPA entities to the boundary.

After HRA-100 and HRA-101 removed the two audited web/domain leaks, the current HRA-060 cohort has
**zero BOUNDARY_MODEL pairs**.

## 4. Classification totals

| Disposition | Count | HRA-061 action |
|---|---:|---|
| REAL_DOMAIN | 51 | Keep domain/JPA split. |
| READ_PERSISTENCE_MODEL | 343 | Approved simplification candidates, subject to exact-head no-consumer revalidation. |
| BOUNDARY_MODEL | 0 | None in the current cohort. |
| **Total replayed cohort** | **394** | Covers the historical 393 plus one conservative replay candidate. |

## 5. Module matrix

| Module | REAL_DOMAIN | READ_PERSISTENCE_MODEL | BOUNDARY_MODEL | Total |
|---|---:|---:|---:|---:|
| alarm | 3 | 8 | 0 | 11 |
| analytics | 3 | 21 | 0 | 24 |
| assets | 1 | 22 | 0 | 23 |
| audit | 2 | 11 | 0 | 13 |
| configuration | 0 | 13 | 0 | 13 |
| custody | 1 | 17 | 0 | 18 |
| documents | 2 | 7 | 0 | 9 |
| hse | 2 | 13 | 0 | 15 |
| identity | 6 | 12 | 0 | 18 |
| incident | 2 | 10 | 0 | 12 |
| integration | 1 | 20 | 0 | 21 |
| integrity | 2 | 18 | 0 | 20 |
| leakdetection | 1 | 11 | 0 | 12 |
| monitoring | 1 | 9 | 0 | 10 |
| notification | 1 | 20 | 0 | 21 |
| party | 1 | 28 | 0 | 29 |
| planning | 5 | 11 | 0 | 16 |
| reporting | 1 | 18 | 0 | 19 |
| risk | 2 | 20 | 0 | 22 |
| simulation | 2 | 19 | 0 | 21 |
| telemetry | 2 | 12 | 0 | 14 |
| topology | 5 | 14 | 0 | 19 |
| workflow | 5 | 9 | 0 | 14 |

## 6. Pair-by-pair REAL_DOMAIN disposition

- **alarm (3):** `AlarmAcknowledgement`, `AlarmClosure`, `AlarmShelving`
- **analytics (3):** `AnalyticsDataset`, `AnalyticsProjectionRun`, `MetricEvaluationRun`
- **assets (1):** `AssetConditionRecord`
- **audit (2):** `AuditAccessRecord`, `AuditExportRequest`
- **custody (1):** `CustodyDiscrepancy`
- **documents (2):** `DocumentStorageObject`, `DocumentTargetLink`
- **hse (2):** `HseClosure`, `HseCorrectivePreventiveAction`
- **identity (6):** `AuthenticationEvent`, `IdentityProvider`, `LoginSession`, `Permission`, `Role`, `RolePermissionGrant`
- **incident (2):** `IncidentClosure`, `IncidentResponseAction`
- **integration (1):** `IntegrationExchangeMessage`
- **integrity (2):** `IntegrityAssessment`, `IntegrityProgram`
- **leakdetection (1):** `LeakEscalationReference`
- **monitoring (1):** `MonitoringRule`
- **notification (1):** `NotificationRequest`
- **party (1):** `PartyRoleAssignment`
- **planning (5):** `Nomination`, `OperationalPlan`, `PlanRevision`, `PlanTarget`, `PlanningPeriod`
- **reporting (1):** `ReportRequest`
- **risk (2):** `RiskEvidenceLink`, `RiskRegister`
- **simulation (2):** `SimulationModel`, `SimulationRecommendation`
- **telemetry (2):** `TelemetryPoint`, `TelemetrySource`
- **topology (5):** `Equipment`, `Facility`, `Pipeline`, `PipelineSystem`, `TopologyConnection`
- **workflow (5):** `WorkflowAction`, `WorkflowStateHistory`, `WorkflowStep`, `WorkflowStepAssignmentRule`, `WorkflowTransition`

## 7. Pair-by-pair READ_PERSISTENCE_MODEL disposition

- **alarm (8):** `AlarmCatalogEntry`, `AlarmCatalogTranslation`, `AlarmComment`, `AlarmEscalation`, `AlarmEvidenceLink`, `AlarmLifecycleEvent`, `AlarmRuleBinding`, `AlarmSuppression`
- **analytics (21):** `AnalyticsAccessPolicy`, `AnalyticsCatalogEntry`, `AnalyticsCatalogTranslation`, `AnalyticsDataSourceReference`, `AnalyticsDatasetLineage`, `AnalyticsFeatureSet`, `AnalyticsFeatureValue`, `AnalyticsInsightEvidence`, `AnalyticsModel`, `AnalyticsModelRun`, `AnalyticsModelVersion`, `AnalyticsProjectionDefinition`, `AnalyticsProjectionSnapshot`, `AnalyticsSubjectArea`, `KpiBand`, `KpiDefinition`, `KpiEvaluation`, `MetricDefinition`, `MetricDefinitionVersion`, `TrendAnalysis`, `TrendPoint`
- **assets (22):** `AssetCatalogEntry`, `AssetCatalogTranslation`, `AssetDocumentReference`, `AssetInstallation`, `AssetLifecycleEvent`, `AssetManufacturerReference`, `AssetMeterReadingReference`, `AssetModel`, `AssetSerialIdentity`, `AssetServiceContractReference`, `AssetSparePartCompatibility`, `AssetTechnicalAttributeDefinition`, `AssetTechnicalAttributeValue`, `AssetType`, `AssetTypeTranslation`, `AssetWarranty`, `MaintenanceExecutionRecord`, `MaintenancePlan`, `MaintenanceStrategy`, `MaintenanceTaskTemplate`, `MaintenanceWorkOrderTask`, `SparePart`
- **audit (11):** `AuditActionReference`, `AuditActorSnapshot`, `AuditCatalogEntry`, `AuditCatalogTranslation`, `AuditCorrelationContext`, `AuditDecisionContext`, `AuditEvidenceLink`, `AuditIntegritySeal`, `AuditRetentionPolicy`, `AuditSearchProjection`, `AuditTargetReference`
- **configuration (13):** `ConfigurationCatalogEntry`, `ConfigurationCatalogTranslation`, `ConfigurationChangeRequest`, `ConfigurationDefinitionVersion`, `ConfigurationDeployment`, `ConfigurationExternalReference`, `ConfigurationNamespace`, `ConfigurationProfile`, `ConfigurationProfileEntry`, `ConfigurationValidationRule`, `FeatureFlagRule`, `ResolvedConfigurationSnapshot`, `ScopedConfigurationOverride`
- **custody (17):** `CustodyAgreement`, `CustodyAgreementParty`, `CustodyApprovalReference`, `CustodyBatch`, `CustodyCatalogEntry`, `CustodyCatalogTranslation`, `CustodyCorrectionFactor`, `CustodyDocumentReference`, `CustodyMeasurementSnapshot`, `CustodyMeterRunSnapshot`, `CustodyMeteringSystem`, `CustodyQualityCertificate`, `CustodyQualitySample`, `CustodyQuantityCalculation`, `CustodyReconciliation`, `CustodyTicketLine`, `CustodyTransferPoint`
- **documents (7):** `DocumentAccessGrant`, `DocumentCatalogEntry`, `DocumentCatalogTranslation`, `DocumentExternalReference`, `DocumentExtractionRecord`, `DocumentRetentionRecord`, `DocumentReviewReference`
- **hse (13):** `ComplianceAssessment`, `ComplianceObligation`, `EmergencyDrill`, `EnvironmentalEvent`, `HazardReport`, `HseCaseEvidenceLink`, `HseCaseStatusHistory`, `HseCatalogEntry`, `HseCatalogTranslation`, `HseImpactAssessment`, `HseInspection`, `NearMissReport`, `SafetyObservation`
- **identity (12):** `AttributeDefinition`, `AuthorizationPolicy`, `AuthorizationPolicyRule`, `AuthorizationPolicyVersion`, `ExternalGroupMapping`, `ExternalIdentity`, `ExternalPermissionMapping`, `Group`, `IdentitySynchronizationJob`, `IdentitySynchronizationRecord`, `SubjectSecurityAttribute`, `UserGroupMembership`
- **incident (10):** `IncidentAssignment`, `IncidentAttachmentReference`, `IncidentCatalogEntry`, `IncidentCatalogTranslation`, `IncidentEscalation`, `IncidentEvidenceLink`, `IncidentImpactAssessment`, `IncidentResolution`, `IncidentRootCauseAnalysis`, `IncidentTimelineEntry`
- **integration (20):** `ConnectorInstance`, `ExternalEndpoint`, `ExternalObjectReference`, `IntegrationCatalogEntry`, `IntegrationCatalogTranslation`, `IntegrationDataContract`, `IntegrationFieldMapping`, `IntegrationHealthSnapshot`, `IntegrationInboundRecord`, `IntegrationJobDefinition`, `IntegrationJobRunStep`, `IntegrationMappingProfile`, `IntegrationOutboundRecord`, `IntegrationReconciliationIssue`, `IntegrationReconciliationRun`, `IntegrationRetryAttempt`, `IntegrationRetryPolicy`, `IntegrationSchemaVersion`, `IntegrationSyncCursor`, `IntegrationTransformationRule`
- **integrity (18):** `CathodicProtectionMeasurement`, `CathodicProtectionSurvey`, `CoatingConditionObservation`, `CorrosionFeature`, `DefectAssessment`, `DefectMeasurement`, `InspectionCampaign`, `InspectionFinding`, `InspectionRun`, `IntegrityAssessmentScope`, `IntegrityCaseStatusHistory`, `IntegrityCatalogEntry`, `IntegrityCatalogTranslation`, `IntegrityEvidenceLink`, `IntegrityRecommendation`, `IntegrityThreat`, `RemainingLifeEstimate`, `WallThicknessMeasurement`
- **leakdetection (11):** `LeakCaseStatusHistory`, `LeakDetectionMethodCatalog`, `LeakDetectionMethodTranslation`, `LeakDetectionProfile`, `LeakDetectionRule`, `LeakDetectionRun`, `LeakDismissalReason`, `LeakEvidenceLink`, `LeakLocalizationEstimate`, `LeakSeverityAssessment`, `LeakVerificationAction`
- **monitoring (9):** `MonitoringAcknowledgement`, `MonitoringAlertCandidate`, `MonitoringCatalogEntry`, `MonitoringCatalogTranslation`, `MonitoringEvaluation`, `MonitoringThreshold`, `OperationalState`, `OperationalStateSnapshot`, `RiskSignal`
- **notification (20):** `NotificationAcknowledgement`, `NotificationBatch`, `NotificationCatalogEntry`, `NotificationCatalogTranslation`, `NotificationChannel`, `NotificationContactPoint`, `NotificationEvidenceLink`, `NotificationMessageVariable`, `NotificationPolicy`, `NotificationPreference`, `NotificationRecipientGroup`, `NotificationRecipientGroupMember`, `NotificationRecipientProfile`, `NotificationRequestRecipient`, `NotificationRetryPolicy`, `NotificationSchedule`, `NotificationStatusHistory`, `NotificationSuppressionRule`, `NotificationTemplateTranslation`, `NotificationTemplateVersion`
- **party (28):** `ContractorQualification`, `ManufacturerProfile`, `OperatorProfile`, `OwnerProfile`, `PartyAddress`, `PartyBankReference`, `PartyCatalogEntry`, `PartyCatalogTranslation`, `PartyCertification`, `PartyComplianceStatus`, `PartyContactPerson`, `PartyContactPoint`, `PartyDocumentReference`, `PartyExternalReference`, `PartyLegalProfile`, `PartyOwnershipLink`, `PartyQualification`, `PartyRegistration`, `PartyRelationship`, `PartyRiskSnapshot`, `PartyRole`, `PartyRoleTranslation`, `PartyStatusHistory`, `PartyTaxIdentifier`, `PartyType`, `PartyTypeTranslation`, `SupplierQualification`, `VendorQualification`
- **planning (11):** `ExpectedFlowState`, `ForecastPoint`, `ForecastSeries`, `NominationScheduleLine`, `PlanActualReviewSnapshot`, `PlanApprovalReference`, `PlanConstraint`, `PlanScenario`, `PlannedOperationWindow`, `PlanningCatalogEntry`, `PlanningCatalogTranslation`
- **reporting (18):** `ReportAccessPolicy`, `ReportCatalogEntry`, `ReportCatalogTranslation`, `ReportChartResult`, `ReportDataSourceBinding`, `ReportDistributionRecord`, `ReportDistributionTarget`, `ReportInputSnapshot`, `ReportParameterDefinition`, `ReportParameterValue`, `ReportPublication`, `ReportSchedule`, `ReportScheduleParameter`, `ReportSectionDefinition`, `ReportSectionResult`, `ReportTableResult`, `ReportTemplate`, `ReportTemplateVersion`
- **risk (20):** `ResidualRiskAssessment`, `RiskAcceptance`, `RiskAggregationSnapshot`, `RiskAssessmentScope`, `RiskCatalogEntry`, `RiskCatalogTranslation`, `RiskConsequence`, `RiskControl`, `RiskExposure`, `RiskLikelihood`, `RiskMatrix`, `RiskMitigationMeasure`, `RiskRating`, `RiskReview`, `RiskScenario`, `RiskScore`, `RiskSource`, `RiskThreat`, `RiskTreatmentAction`, `RiskTreatmentPlan`
- **simulation (19):** `SimulationCandidateOperatingCondition`, `SimulationCandidateScore`, `SimulationCatalogEntry`, `SimulationCatalogTranslation`, `SimulationConstraint`, `SimulationConstraintEvaluation`, `SimulationEvidenceLink`, `SimulationInputDataset`, `SimulationInputSnapshot`, `SimulationModelVersion`, `SimulationObjective`, `SimulationResultSeriesReference`, `SimulationResultSummary`, `SimulationResultValue`, `SimulationRunStep`, `SimulationScenarioAssumption`, `SimulationSensitivityAnalysis`, `SimulationSolverTrace`, `SimulationValidationFinding`
- **telemetry (12):** `TelemetryCatalogEntry`, `TelemetryCatalogTranslation`, `TelemetryDevice`, `TelemetryExternalTagMapping`, `TelemetryIngestionBatch`, `TelemetryPointBinding`, `TelemetryPointStateSnapshot`, `TelemetryQualityAssessment`, `TelemetryQuarantineRecord`, `TelemetrySourceEndpoint`, `TelemetryUnit`, `TelemetryValidationRule`
- **topology (14):** `EquipmentAttributeDefinition`, `EquipmentAttributeValue`, `EquipmentType`, `EquipmentTypeVersion`, `FacilityAttributeDefinition`, `FacilityAttributeValue`, `FacilityNodeBinding`, `FacilityType`, `FacilityTypeVersion`, `MeasurementLocation`, `PipelineSegment`, `PipelineSystemFacility`, `TopologyNode`, `TopologySnapshot`
- **workflow (9):** `WorkflowAssignment`, `WorkflowAuditOutboxReference`, `WorkflowCatalogEntry`, `WorkflowCatalogTranslation`, `WorkflowComment`, `WorkflowDefinitionTargetBinding`, `WorkflowDelegation`, `WorkflowEscalationRule`, `WorkflowSlaPolicy`

## 8. Boundary-model disposition

No replayed pair is classified as `BOUNDARY_MODEL` on live main.

Two source-era pairs would previously have met the boundary condition:

- `identity.LoginSession` — HRA-101 moved authentication web/session representation behind
  application-owned `AuthenticatedPrincipalInput` / `AuthenticationResult`; the domain model
  remains actively used by identity application services, so it is now `REAL_DOMAIN`.
- `planning.PlanRevision` — HRA-100 removed the domain record from the REST update boundary; the
  domain model remains actively used by planning application services, so it is now
  `REAL_DOMAIN`.

## 9. HRA-061 execution gate

HRA-061 is the single consolidated implementation task authorized by this classification.

Before editing Java code, HRA-061 must replay the classifier on its exact `main` head and verify:

```text
replayed normalization-only exact cohort  394
REAL_DOMAIN                                51
READ_PERSISTENCE_MODEL                   343
BOUNDARY_MODEL                              0
```

For each of the 343 `READ_PERSISTENCE_MODEL` pairs, HRA-061 must additionally confirm that:

1. the domain type still has no application-service/domain-service/API consumer;
2. the corresponding repository port is still not actively used by an application service;
3. no reflection/configuration/serialization/generated-code evidence requires the domain mirror;
4. persistence schema ownership remains unchanged.

If any condition fails, HRA-061 must retain the domain/JPA split for that pair and record the
reclassification rather than forcing consolidation.

The 51 `REAL_DOMAIN` pairs are explicitly outside HRA-061 simplification scope.
