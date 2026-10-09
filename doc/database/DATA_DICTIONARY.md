# HidraAPI Generated Persistence Data Dictionary

## Status

CURRENT — HPR-P2-013 regenerated persistence inventory; historical HPR-P2-006 generation retained below.

## Generation Basis

Current verified source parent: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, 2026-10-09.

- Flyway versioned migrations: **139**
- Current module JPA persistence entities: **470**
- Implemented business modules: **24**

This dictionary intentionally does not invent table/column definitions from Java class names. Exact physical table names, columns, SQL types, indexes, keys and constraints are authoritative in the ordered migration SQL. JPA entities provide the current application mapping/ownership cross-check and Hibernate validates those mappings at runtime.

## Flyway Migration Inventory

1. `V20260611_001__create_identity_tables.sql`
2. `V20260611_002__create_organization_tables.sql`
3. `V20260611_003__create_party_tables.sql`
4. `V20260611_004__create_topology_tables.sql`
5. `V20260611_005__create_telemetry_tables.sql`
6. `V20260611_006__create_planning_tables.sql`
7. `V20260611_007__create_monitoring_tables.sql`
8. `V20260611_008__create_alarm_tables.sql`
9. `V20260611_009__create_leakdetection_tables.sql`
10. `V20260611_010__create_incident_tables.sql`
11. `V20260611_011__create_risk_tables.sql`
12. `V20260611_012__create_hse_tables.sql`
13. `V20260611_013__create_integrity_tables.sql`
14. `V20260611_014__create_assets_tables.sql`
15. `V20260611_015__create_custody_tables.sql`
16. `V20260611_016__create_workflow_tables.sql`
17. `V20260611_017__create_audit_tables.sql`
18. `V20260611_018__create_documents_tables.sql`
19. `V20260611_019__create_integration_tables.sql`
20. `V20260611_020__create_configuration_tables.sql`
21. `V20260611_021__create_notification_tables.sql`
22. `V20260611_022__create_simulation_tables.sql`
23. `V20260611_023__create_analytics_tables.sql`
24. `V20260611_024__create_reporting_tables.sql`
25. `V20260915_001__create_identity_local_credential.sql`
26. `V20260927_001__add_operational_scope_registry.sql`
27. `V20260927_002__add_embedded_organization_multilingual_fields.sql`
28. `V20260927_003__retire_organization_unit_type_translation_table.sql`
29. `V20260927_004__enforce_organization_internal_reference_integrity.sql`
30. `V20260927_005__retire_organization_multilingual_compatibility_columns.sql`
31. `V20260928_001__add_employee_birth_data.sql`
32. `V20260928_002__backfill_employee_contact_points.sql`
33. `V20260929_001__enforce_same_module_reference_integrity_a.sql`
34. `V20260929_002__enforce_same_module_reference_integrity_b.sql`
35. `V20260929_003__harden_operational_scope_responsibility_concurrency.sql`
36. `V20260929_004__provision_organization_scope_permissions.sql`
37. `V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`
38. `V20260929_006__retire_legacy_operational_scope_columns.sql`
39. `V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`
40. `V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`
41. `V20261004_003__hmr_003_workflow_workflow_definition.sql`
42. `V20261004_004__hmr_004_party_party.sql`
43. `V20261004_005__hmr_005_telemetry_telemetry_point.sql`
44. `V20261004_006__hmr_006_planning_planning_period.sql`
45. `V20261004_007__hmr_007_identity_role.sql`
46. `V20261004_008__hmr_008_documents_document_storage_object.sql`
47. `V20261004_009__hmr_009_simulation_simulation_model.sql`
48. `V20261004_010__hmr_010_identity_identity_provider.sql`
49. `V20261004_011__hmr_011_identity_permission.sql`
50. `V20261004_012__hmr_012_notification_notification_template.sql`
51. `V20261004_013__hmr_013_reporting_report_definition.sql`
52. `V20261004_014__hmr_014_integration_integration_job_run.sql`
53. `V20261004_015__hmr_015_leakdetection_leak_candidate.sql`
54. `V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
55. `V20261004_018__hmr_018_custody_custody_measurement_period.sql`
56. `V20261004_019__hmr_019_integrity_pipeline_defect.sql`
57. `V20261004_020__hmr_020_organization_position.sql`
58. `V20261004_021__hmr_021_organization_shift.sql`
59. `V20261004_022__hmr_022_topology_pipeline_system.sql`
60. `V20261004_023__hmr_023_analytics_analytics_insight.sql`
61. `V20261004_024__hmr_024_analytics_projection_run.sql`
62. `V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
63. `V20261004_026__hmr_026_configuration_feature_flag.sql`
64. `V20261004_027__hmr_027_custody_custody_discrepancy.sql`
65. `V20261004_028__hmr_028_organization_reporting_line.sql`
66. `V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
67. `V20261004_030__hmr_030_telemetry_telemetry_source.sql`
68. `V20261004_031__hmr_031_topology_topology_connection.sql`
69. `V20261004_032__hmr_032_organization_organization_unit.sql`
70. `V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
71. `V20261004_034__hmr_034_simulation_simulation_scenario.sql`
72. `V20261004_035__hmr_035_notification_notification_request.sql`
73. `V20261004_039__hmr_039_configuration_configuration_value.sql`
74. `V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
75. `V20261004_041__hmr_041_party_party_role_assignment.sql`
76. `V20261004_042__hmr_042_topology_pipeline.sql`
77. `V20261004_043__hmr_043_workflow_workflow_step.sql`
78. `V20261004_045__hmr_045_assets_maintainable_asset.sql`
79. `V20261004_047__hmr_047_integration_external_system.sql`
80. `V20261004_048__hmr_048_reporting_report_request.sql`
81. `V20261004_049__hmr_049_risk_risk_register.sql`
82. `V20261005_001__provision_risk_register_created_audit_taxonomy.sql`
83. `V20261006_001__hmr_059_leak_escalation_candidate_integrity.sql`
84. `V20261006_002__hmr_074_responsibility_assignment_id_required.sql`
85. `V20261006_003__hmr_075_captured_by_employee_id_required.sql`
86. `V20261006_004__hmr_076_organization_unit_id_required.sql`
87. `V20261006_005__hmr_052_notification_message_composition.sql`
88. `V20261006_006__hmr_060_notification_attempt_evidence.sql`
89. `V20261006_007__hmr_052_qualify_message_validator_parameter.sql`
90. `V20261006_008__hmr_053_trusted_telemetry_gate.sql`
91. `V20261006_009__hmr_054_equipment_catalog_and_attachments.sql`
92. `V20261006_010__hmr_063_identity_user_uniqueness.sql`
93. `V20261006_011__hmr_086_delegation_contract.sql`
94. `V20261006_012__hmr_087_login_session_evidence.sql`
95. `V20261006_013__hmr_088_direct_permission_bounds.sql`
96. `V20261006_014__hmr_055_workflow_instance.sql`
97. `V20261006_015__hmr_061_workflow_transition.sql`
98. `V20261006_016__hmr_066_workflow_task.sql`
99. `V20261006_017__hmr_081_workflow_action.sql`
100. `V20261006_018__hmr_099_workflow_state_history.sql`
101. `V20261007_001__hmr_064_planning_plan_revision.sql`
102. `V20261007_002__hmr_065_planning_operational_plan.sql`
103. `V20261007_003__hmr_067_documents_document.sql`
104. `V20261007_004__hmr_068_documents_document_version.sql`
105. `V20261007_005__hmr_084_documents_document_target_link.sql`
106. `V20261007_006__hmr_083_audit_export_request.sql`
107. `V20261007_007__hmr_095_audit_event.sql`
108. `V20261007_008__hmr_101_audit_access_record.sql`
109. `V20261007_009__hmr_102_audit_before_after_value.sql`
110. `V20261007_010__hmr_056_integration_exchange_message.sql`
111. `V20261007_011__hmr_071_integration_dead_letter_record.sql`
112. `V20261007_012__hmr_057_reporting_report_run.sql`
113. `V20261007_013__hmr_093_reporting_report_output_artifact.sql`
114. `V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`
115. `V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
116. `V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql`
117. `V20261008_004__hmr_077_risk_evidence_identity_integrity.sql`
118. `V20261008_005__hmr_058_risk_assessment_governance.sql`
119. `V20261008_006__provision_risk_assessment_audit_taxonomy.sql`
120. `V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql`
121. `V20261008_008__hmr_091_incident_relationship_integrity.sql`
122. `V20261008_009__hmr_092_incident_response_action_integrity.sql`
123. `V20261008_010__hmr_090_incident_closure_governance.sql`
124. `V20261008_011__hmr_069_assets_maintenance_work_order.sql`
125. `V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
126. `V20261008_013__hmr_072_integrity_integrity_assessment.sql`
127. `V20261008_014__hmr_082_hse_case_lifecycle.sql`
128. `V20261008_015__hmr_096_hse_closure_atomic_evidence.sql`
129. `V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql`
130. `V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql`
131. `V20261008_018__hmr_098_integrity_case_reference_integrity.sql`
132. `V20261008_019__hmr_094_planning_target_value_policy.sql`
133. `V20261008_020__hmr_094_planning_plan_target_integrity.sql`
134. `V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`
135. `V20261008_022__hmr_100_alarm_lifecycle_integrity.sql`
136. `V20261008_023__hmr_105_alarm_closure_integrity.sql`
137. `V20261008_024__hmr_106_alarm_shelving_integrity.sql`
138. `V20261008_025__hmr_080_nomination_owner_reference_policies.sql`
139. `V20261008_026__hmr_080_planning_nomination_integrity.sql`

## Module Persistence Inventory

### alarm

JPA persistence entities: **12**

Primary module migration: `V20260611_008__create_alarm_tables.sql`

Migration filenames containing this module identifier (6): `V20260611_008__create_alarm_tables.sql`, `V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`, `V20261008_022__hmr_100_alarm_lifecycle_integrity.sql`, `V20261008_023__hmr_105_alarm_closure_integrity.sql`, `V20261008_024__hmr_106_alarm_shelving_integrity.sql`

Current JPA entity classes:

- `AlarmAcknowledgementJpaEntity`
- `AlarmCatalogEntryJpaEntity`
- `AlarmCatalogTranslationJpaEntity`
- `AlarmClosureJpaEntity`
- `AlarmCommentJpaEntity`
- `AlarmEscalationJpaEntity`
- `AlarmEvidenceLinkJpaEntity`
- `AlarmJpaEntity`
- `AlarmLifecycleEventJpaEntity`
- `AlarmRuleBindingJpaEntity`
- `AlarmShelvingJpaEntity`
- `AlarmSuppressionJpaEntity`

### analytics

JPA persistence entities: **28**

Primary module migration: `V20260611_023__create_analytics_tables.sql`

Migration filenames containing this module identifier (5): `V20260611_023__create_analytics_tables.sql`, `V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`, `V20261004_023__hmr_023_analytics_analytics_insight.sql`, `V20261004_024__hmr_024_analytics_projection_run.sql`, `V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`

Current JPA entity classes:

- `AnalyticsAccessPolicyJpaEntity`
- `AnalyticsCatalogEntryJpaEntity`
- `AnalyticsCatalogTranslationJpaEntity`
- `AnalyticsDataSourceReferenceJpaEntity`
- `AnalyticsDatasetJpaEntity`
- `AnalyticsDatasetLineageJpaEntity`
- `AnalyticsDatasetVersionJpaEntity`
- `AnalyticsFeatureSetJpaEntity`
- `AnalyticsFeatureValueJpaEntity`
- `AnalyticsInsightEvidenceJpaEntity`
- `AnalyticsInsightJpaEntity`
- `AnalyticsModelJpaEntity`
- `AnalyticsModelRunJpaEntity`
- `AnalyticsModelVersionJpaEntity`
- `AnalyticsProjectionDefinitionJpaEntity`
- `AnalyticsProjectionRunJpaEntity`
- `AnalyticsProjectionSnapshotJpaEntity`
- `AnalyticsSubjectAreaJpaEntity`
- `DigitalTwinReadinessAssessmentJpaEntity`
- `KpiBandJpaEntity`
- `KpiDefinitionJpaEntity`
- `KpiEvaluationJpaEntity`
- `MetricDefinitionJpaEntity`
- `MetricDefinitionVersionJpaEntity`
- `MetricEvaluationRunJpaEntity`
- `MetricValueJpaEntity`
- `TrendAnalysisJpaEntity`
- `TrendPointJpaEntity`

### assets

JPA persistence entities: **25**

Primary module migration: `V20260611_014__create_assets_tables.sql`

Migration filenames containing this module identifier (3): `V20260611_014__create_assets_tables.sql`, `V20261004_045__hmr_045_assets_maintainable_asset.sql`, `V20261008_011__hmr_069_assets_maintenance_work_order.sql`

Current JPA entity classes:

- `AssetCatalogEntryJpaEntity`
- `AssetCatalogTranslationJpaEntity`
- `AssetConditionRecordJpaEntity`
- `AssetDocumentReferenceJpaEntity`
- `AssetInstallationJpaEntity`
- `AssetLifecycleEventJpaEntity`
- `AssetManufacturerReferenceJpaEntity`
- `AssetMeterReadingReferenceJpaEntity`
- `AssetModelJpaEntity`
- `AssetSerialIdentityJpaEntity`
- `AssetServiceContractReferenceJpaEntity`
- `AssetSparePartCompatibilityJpaEntity`
- `AssetTechnicalAttributeDefinitionJpaEntity`
- `AssetTechnicalAttributeValueJpaEntity`
- `AssetTypeJpaEntity`
- `AssetTypeTranslationJpaEntity`
- `AssetWarrantyJpaEntity`
- `MaintainableAssetJpaEntity`
- `MaintenanceExecutionRecordJpaEntity`
- `MaintenancePlanJpaEntity`
- `MaintenanceStrategyJpaEntity`
- `MaintenanceTaskTemplateJpaEntity`
- `MaintenanceWorkOrderJpaEntity`
- `MaintenanceWorkOrderTaskJpaEntity`
- `SparePartJpaEntity`

### audit

JPA persistence entities: **15**

Primary module migration: `V20260611_017__create_audit_tables.sql`

Migration filenames containing this module identifier (10): `V20260611_017__create_audit_tables.sql`, `V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `V20261005_001__provision_risk_register_created_audit_taxonomy.sql`, `V20261007_006__hmr_083_audit_export_request.sql`, `V20261007_007__hmr_095_audit_event.sql`, `V20261007_008__hmr_101_audit_access_record.sql`, `V20261007_009__hmr_102_audit_before_after_value.sql`, `V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql`, `V20261008_006__provision_risk_assessment_audit_taxonomy.sql`

Current JPA entity classes:

- `AuditAccessRecordJpaEntity`
- `AuditActionReferenceJpaEntity`
- `AuditActorSnapshotJpaEntity`
- `AuditBeforeAfterValueJpaEntity`
- `AuditCatalogEntryJpaEntity`
- `AuditCatalogTranslationJpaEntity`
- `AuditCorrelationContextJpaEntity`
- `AuditDecisionContextJpaEntity`
- `AuditEventJpaEntity`
- `AuditEvidenceLinkJpaEntity`
- `AuditExportRequestJpaEntity`
- `AuditIntegritySealJpaEntity`
- `AuditRetentionPolicyJpaEntity`
- `AuditSearchProjectionJpaEntity`
- `AuditTargetReferenceJpaEntity`

### configuration

JPA persistence entities: **16**

Primary module migration: `V20260611_020__create_configuration_tables.sql`

Migration filenames containing this module identifier (3): `V20260611_020__create_configuration_tables.sql`, `V20261004_026__hmr_026_configuration_feature_flag.sql`, `V20261004_039__hmr_039_configuration_configuration_value.sql`

Current JPA entity classes:

- `ConfigurationCatalogEntryJpaEntity`
- `ConfigurationCatalogTranslationJpaEntity`
- `ConfigurationChangeRequestJpaEntity`
- `ConfigurationDefinitionJpaEntity`
- `ConfigurationDefinitionVersionJpaEntity`
- `ConfigurationDeploymentJpaEntity`
- `ConfigurationExternalReferenceJpaEntity`
- `ConfigurationNamespaceJpaEntity`
- `ConfigurationProfileEntryJpaEntity`
- `ConfigurationProfileJpaEntity`
- `ConfigurationValidationRuleJpaEntity`
- `ConfigurationValueJpaEntity`
- `FeatureFlagJpaEntity`
- `FeatureFlagRuleJpaEntity`
- `ResolvedConfigurationSnapshotJpaEntity`
- `ScopedConfigurationOverrideJpaEntity`

### custody

JPA persistence entities: **20**

Primary module migration: `V20260611_015__create_custody_tables.sql`

Migration filenames containing this module identifier (4): `V20260611_015__create_custody_tables.sql`, `V20261004_018__hmr_018_custody_custody_measurement_period.sql`, `V20261004_027__hmr_027_custody_custody_discrepancy.sql`, `V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`

Current JPA entity classes:

- `CustodyAgreementJpaEntity`
- `CustodyAgreementPartyJpaEntity`
- `CustodyApprovalReferenceJpaEntity`
- `CustodyBatchJpaEntity`
- `CustodyCatalogEntryJpaEntity`
- `CustodyCatalogTranslationJpaEntity`
- `CustodyCorrectionFactorJpaEntity`
- `CustodyDiscrepancyJpaEntity`
- `CustodyDocumentReferenceJpaEntity`
- `CustodyMeasurementPeriodJpaEntity`
- `CustodyMeasurementSnapshotJpaEntity`
- `CustodyMeterRunSnapshotJpaEntity`
- `CustodyMeteringSystemJpaEntity`
- `CustodyQualityCertificateJpaEntity`
- `CustodyQualitySampleJpaEntity`
- `CustodyQuantityCalculationJpaEntity`
- `CustodyReconciliationJpaEntity`
- `CustodyTicketLineJpaEntity`
- `CustodyTransferPointJpaEntity`
- `CustodyTransferTicketJpaEntity`

### documents

JPA persistence entities: **11**

Primary module migration: `V20260611_018__create_documents_tables.sql`

Migration filenames containing this module identifier (5): `V20260611_018__create_documents_tables.sql`, `V20261004_008__hmr_008_documents_document_storage_object.sql`, `V20261007_003__hmr_067_documents_document.sql`, `V20261007_004__hmr_068_documents_document_version.sql`, `V20261007_005__hmr_084_documents_document_target_link.sql`

Current JPA entity classes:

- `DocumentAccessGrantJpaEntity`
- `DocumentCatalogEntryJpaEntity`
- `DocumentCatalogTranslationJpaEntity`
- `DocumentExternalReferenceJpaEntity`
- `DocumentExtractionRecordJpaEntity`
- `DocumentJpaEntity`
- `DocumentRetentionRecordJpaEntity`
- `DocumentReviewReferenceJpaEntity`
- `DocumentStorageObjectJpaEntity`
- `DocumentTargetLinkJpaEntity`
- `DocumentVersionJpaEntity`

### hse

JPA persistence entities: **17**

Primary module migration: `V20260611_012__create_hse_tables.sql`

Migration filenames containing this module identifier (4): `V20260611_012__create_hse_tables.sql`, `V20261008_014__hmr_082_hse_case_lifecycle.sql`, `V20261008_015__hmr_096_hse_closure_atomic_evidence.sql`, `V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql`

Current JPA entity classes:

- `ComplianceAssessmentJpaEntity`
- `ComplianceObligationJpaEntity`
- `EmergencyDrillJpaEntity`
- `EnvironmentalEventJpaEntity`
- `HazardReportJpaEntity`
- `HseCaseEvidenceLinkJpaEntity`
- `HseCaseJpaEntity`
- `HseCaseStatusHistoryJpaEntity`
- `HseCatalogEntryJpaEntity`
- `HseCatalogTranslationJpaEntity`
- `HseClosureJpaEntity`
- `HseCorrectivePreventiveActionJpaEntity`
- `HseImpactAssessmentJpaEntity`
- `HseInspectionJpaEntity`
- `NearMissReportJpaEntity`
- `PermitToWorkJpaEntity`
- `SafetyObservationJpaEntity`

### identity

JPA persistence entities: **26**

Primary module migration: `V20260611_001__create_identity_tables.sql`

Migration filenames containing this module identifier (7): `V20260611_001__create_identity_tables.sql`, `V20260915_001__create_identity_local_credential.sql`, `V20261004_007__hmr_007_identity_role.sql`, `V20261004_010__hmr_010_identity_identity_provider.sql`, `V20261004_011__hmr_011_identity_permission.sql`, `V20261006_010__hmr_063_identity_user_uniqueness.sql`, `V20261008_004__hmr_077_risk_evidence_identity_integrity.sql`

Current JPA entity classes:

- `AttributeDefinitionJpaEntity`
- `AuthenticationEventJpaEntity`
- `AuthorizationDecisionJpaEntity`
- `AuthorizationDelegationGrantJpaEntity`
- `AuthorizationPolicyJpaEntity`
- `AuthorizationPolicyRuleJpaEntity`
- `AuthorizationPolicyVersionJpaEntity`
- `ExternalGroupMappingJpaEntity`
- `ExternalIdentityJpaEntity`
- `ExternalPermissionMappingJpaEntity`
- `ExternalRoleMappingJpaEntity`
- `GroupJpaEntity`
- `GroupRoleGrantJpaEntity`
- `IdentityProviderJpaEntity`
- `IdentitySynchronizationJobJpaEntity`
- `IdentitySynchronizationRecordJpaEntity`
- `LocalCredentialJpaEntity`
- `LoginSessionJpaEntity`
- `PermissionJpaEntity`
- `RoleJpaEntity`
- `RolePermissionGrantJpaEntity`
- `SubjectSecurityAttributeJpaEntity`
- `UserGroupMembershipJpaEntity`
- `UserJpaEntity`
- `UserPermissionGrantJpaEntity`
- `UserRoleGrantJpaEntity`

### incident

JPA persistence entities: **14**

Primary module migration: `V20260611_010__create_incident_tables.sql`

Migration filenames containing this module identifier (5): `V20260611_010__create_incident_tables.sql`, `V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql`, `V20261008_008__hmr_091_incident_relationship_integrity.sql`, `V20261008_009__hmr_092_incident_response_action_integrity.sql`, `V20261008_010__hmr_090_incident_closure_governance.sql`

Current JPA entity classes:

- `IncidentAssignmentJpaEntity`
- `IncidentAttachmentReferenceJpaEntity`
- `IncidentCatalogEntryJpaEntity`
- `IncidentCatalogTranslationJpaEntity`
- `IncidentClosureJpaEntity`
- `IncidentEscalationJpaEntity`
- `IncidentEvidenceLinkJpaEntity`
- `IncidentImpactAssessmentJpaEntity`
- `IncidentJpaEntity`
- `IncidentRelatedIncidentJpaEntity`
- `IncidentResolutionJpaEntity`
- `IncidentResponseActionJpaEntity`
- `IncidentRootCauseAnalysisJpaEntity`
- `IncidentTimelineEntryJpaEntity`

### integration

JPA persistence entities: **24**

Primary module migration: `V20260611_019__create_integration_tables.sql`

Migration filenames containing this module identifier (5): `V20260611_019__create_integration_tables.sql`, `V20261004_014__hmr_014_integration_integration_job_run.sql`, `V20261004_047__hmr_047_integration_external_system.sql`, `V20261007_010__hmr_056_integration_exchange_message.sql`, `V20261007_011__hmr_071_integration_dead_letter_record.sql`

Current JPA entity classes:

- `ConnectorInstanceJpaEntity`
- `ExternalEndpointJpaEntity`
- `ExternalObjectReferenceJpaEntity`
- `ExternalSystemJpaEntity`
- `IntegrationCatalogEntryJpaEntity`
- `IntegrationCatalogTranslationJpaEntity`
- `IntegrationDataContractJpaEntity`
- `IntegrationDeadLetterRecordJpaEntity`
- `IntegrationExchangeMessageJpaEntity`
- `IntegrationFieldMappingJpaEntity`
- `IntegrationHealthSnapshotJpaEntity`
- `IntegrationInboundRecordJpaEntity`
- `IntegrationJobDefinitionJpaEntity`
- `IntegrationJobRunJpaEntity`
- `IntegrationJobRunStepJpaEntity`
- `IntegrationMappingProfileJpaEntity`
- `IntegrationOutboundRecordJpaEntity`
- `IntegrationReconciliationIssueJpaEntity`
- `IntegrationReconciliationRunJpaEntity`
- `IntegrationRetryAttemptJpaEntity`
- `IntegrationRetryPolicyJpaEntity`
- `IntegrationSchemaVersionJpaEntity`
- `IntegrationSyncCursorJpaEntity`
- `IntegrationTransformationRuleJpaEntity`

### integrity

JPA persistence entities: **22**

Primary module migration: `V20260611_013__create_integrity_tables.sql`

Migration filenames containing this module identifier (22): `V20260611_013__create_integrity_tables.sql`, `V20260927_004__enforce_organization_internal_reference_integrity.sql`, `V20260929_001__enforce_same_module_reference_integrity_a.sql`, `V20260929_002__enforce_same_module_reference_integrity_b.sql`, `V20261004_019__hmr_019_integrity_pipeline_defect.sql`, `V20261006_001__hmr_059_leak_escalation_candidate_integrity.sql`, `V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`, `V20261008_002__hmr_079_simulation_recommendation_integrity.sql`, `V20261008_004__hmr_077_risk_evidence_identity_integrity.sql`, `V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql`, `V20261008_008__hmr_091_incident_relationship_integrity.sql`, `V20261008_009__hmr_092_incident_response_action_integrity.sql`, `V20261008_013__hmr_072_integrity_integrity_assessment.sql`, `V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql`, `V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql`, `V20261008_018__hmr_098_integrity_case_reference_integrity.sql`, `V20261008_020__hmr_094_planning_plan_target_integrity.sql`, `V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`, `V20261008_022__hmr_100_alarm_lifecycle_integrity.sql`, `V20261008_023__hmr_105_alarm_closure_integrity.sql`, `V20261008_024__hmr_106_alarm_shelving_integrity.sql`, `V20261008_026__hmr_080_planning_nomination_integrity.sql`

Current JPA entity classes:

- `CathodicProtectionMeasurementJpaEntity`
- `CathodicProtectionSurveyJpaEntity`
- `CoatingConditionObservationJpaEntity`
- `CorrosionFeatureJpaEntity`
- `DefectAssessmentJpaEntity`
- `DefectMeasurementJpaEntity`
- `InspectionCampaignJpaEntity`
- `InspectionFindingJpaEntity`
- `InspectionRunJpaEntity`
- `IntegrityAssessmentJpaEntity`
- `IntegrityAssessmentScopeJpaEntity`
- `IntegrityCaseJpaEntity`
- `IntegrityCaseStatusHistoryJpaEntity`
- `IntegrityCatalogEntryJpaEntity`
- `IntegrityCatalogTranslationJpaEntity`
- `IntegrityEvidenceLinkJpaEntity`
- `IntegrityProgramJpaEntity`
- `IntegrityRecommendationJpaEntity`
- `IntegrityThreatJpaEntity`
- `PipelineDefectJpaEntity`
- `RemainingLifeEstimateJpaEntity`
- `WallThicknessMeasurementJpaEntity`

### leakdetection

JPA persistence entities: **14**

Primary module migration: `V20260611_009__create_leakdetection_tables.sql`

Migration filenames containing this module identifier (2): `V20260611_009__create_leakdetection_tables.sql`, `V20261004_015__hmr_015_leakdetection_leak_candidate.sql`

Current JPA entity classes:

- `LeakCandidateJpaEntity`
- `LeakCaseStatusHistoryJpaEntity`
- `LeakDetectionCaseJpaEntity`
- `LeakDetectionMethodCatalogJpaEntity`
- `LeakDetectionMethodTranslationJpaEntity`
- `LeakDetectionProfileJpaEntity`
- `LeakDetectionRuleJpaEntity`
- `LeakDetectionRunJpaEntity`
- `LeakDismissalReasonJpaEntity`
- `LeakEscalationReferenceJpaEntity`
- `LeakEvidenceLinkJpaEntity`
- `LeakLocalizationEstimateJpaEntity`
- `LeakSeverityAssessmentJpaEntity`
- `LeakVerificationActionJpaEntity`

### monitoring

JPA persistence entities: **11**

Primary module migration: `V20260611_007__create_monitoring_tables.sql`

Migration filenames containing this module identifier (3): `V20260611_007__create_monitoring_tables.sql`, `V20261004_040__hmr_040_monitoring_monitoring_rule.sql`, `V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`

Current JPA entity classes:

- `MonitoringAcknowledgementJpaEntity`
- `MonitoringAlertCandidateJpaEntity`
- `MonitoringCatalogEntryJpaEntity`
- `MonitoringCatalogTranslationJpaEntity`
- `MonitoringEvaluationJpaEntity`
- `MonitoringRuleJpaEntity`
- `MonitoringThresholdJpaEntity`
- `OperationalStateJpaEntity`
- `OperationalStateSnapshotJpaEntity`
- `PlanActualDeviationJpaEntity`
- `RiskSignalJpaEntity`

### notification

JPA persistence entities: **24**

Primary module migration: `V20260611_021__create_notification_tables.sql`

Migration filenames containing this module identifier (5): `V20260611_021__create_notification_tables.sql`, `V20261004_012__hmr_012_notification_notification_template.sql`, `V20261004_035__hmr_035_notification_notification_request.sql`, `V20261006_005__hmr_052_notification_message_composition.sql`, `V20261006_006__hmr_060_notification_attempt_evidence.sql`

Current JPA entity classes:

- `NotificationAcknowledgementJpaEntity`
- `NotificationBatchJpaEntity`
- `NotificationCatalogEntryJpaEntity`
- `NotificationCatalogTranslationJpaEntity`
- `NotificationChannelJpaEntity`
- `NotificationContactPointJpaEntity`
- `NotificationDeliveryAttemptJpaEntity`
- `NotificationEvidenceLinkJpaEntity`
- `NotificationMessageJpaEntity`
- `NotificationMessageVariableJpaEntity`
- `NotificationPolicyJpaEntity`
- `NotificationPreferenceJpaEntity`
- `NotificationRecipientGroupJpaEntity`
- `NotificationRecipientGroupMemberJpaEntity`
- `NotificationRecipientProfileJpaEntity`
- `NotificationRequestJpaEntity`
- `NotificationRequestRecipientJpaEntity`
- `NotificationRetryPolicyJpaEntity`
- `NotificationScheduleJpaEntity`
- `NotificationStatusHistoryJpaEntity`
- `NotificationSuppressionRuleJpaEntity`
- `NotificationTemplateJpaEntity`
- `NotificationTemplateTranslationJpaEntity`
- `NotificationTemplateVersionJpaEntity`

### organization

JPA persistence entities: **18**

Primary module migration: `V20260611_002__create_organization_tables.sql`

Migration filenames containing this module identifier (12): `V20260611_002__create_organization_tables.sql`, `V20260927_002__add_embedded_organization_multilingual_fields.sql`, `V20260927_003__retire_organization_unit_type_translation_table.sql`, `V20260927_004__enforce_organization_internal_reference_integrity.sql`, `V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `V20260929_004__provision_organization_scope_permissions.sql`, `V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `V20261004_020__hmr_020_organization_position.sql`, `V20261004_021__hmr_021_organization_shift.sql`, `V20261004_028__hmr_028_organization_reporting_line.sql`, `V20261004_032__hmr_032_organization_organization_unit.sql`, `V20261006_004__hmr_076_organization_unit_id_required.sql`

Current JPA entity classes:

- `AdministrativeDistrictJpaEntity`
- `AdministrativeLocalityJpaEntity`
- `AdministrativeStateJpaEntity`
- `EmployeeAddressJpaEntity`
- `EmployeeAssignmentJpaEntity`
- `EmployeeJpaEntity`
- `OperationalScopeJpaEntity`
- `OrganizationContactPointJpaEntity`
- `OrganizationDelegationJpaEntity`
- `OrganizationHierarchySnapshotJpaEntity`
- `OrganizationUnitJpaEntity`
- `OrganizationUnitTypeJpaEntity`
- `PositionJpaEntity`
- `ReportingLineJpaEntity`
- `ReportingLineTypeJpaEntity`
- `ResponsibilityAssignmentJpaEntity`
- `ShiftAssignmentJpaEntity`
- `ShiftJpaEntity`

### party

JPA persistence entities: **30**

Primary module migration: `V20260611_003__create_party_tables.sql`

Migration filenames containing this module identifier (3): `V20260611_003__create_party_tables.sql`, `V20261004_004__hmr_004_party_party.sql`, `V20261004_041__hmr_041_party_party_role_assignment.sql`

Current JPA entity classes:

- `ContractorQualificationJpaEntity`
- `ManufacturerProfileJpaEntity`
- `OperatorProfileJpaEntity`
- `OwnerProfileJpaEntity`
- `PartyAddressJpaEntity`
- `PartyBankReferenceJpaEntity`
- `PartyCatalogEntryJpaEntity`
- `PartyCatalogTranslationJpaEntity`
- `PartyCertificationJpaEntity`
- `PartyComplianceStatusJpaEntity`
- `PartyContactPersonJpaEntity`
- `PartyContactPointJpaEntity`
- `PartyDocumentReferenceJpaEntity`
- `PartyExternalReferenceJpaEntity`
- `PartyJpaEntity`
- `PartyLegalProfileJpaEntity`
- `PartyOwnershipLinkJpaEntity`
- `PartyQualificationJpaEntity`
- `PartyRegistrationJpaEntity`
- `PartyRelationshipJpaEntity`
- `PartyRiskSnapshotJpaEntity`
- `PartyRoleAssignmentJpaEntity`
- `PartyRoleJpaEntity`
- `PartyRoleTranslationJpaEntity`
- `PartyStatusHistoryJpaEntity`
- `PartyTaxIdentifierJpaEntity`
- `PartyTypeJpaEntity`
- `PartyTypeTranslationJpaEntity`
- `SupplierQualificationJpaEntity`
- `VendorQualificationJpaEntity`

### planning

JPA persistence entities: **16**

Primary module migration: `V20260611_006__create_planning_tables.sql`

Migration filenames containing this module identifier (7): `V20260611_006__create_planning_tables.sql`, `V20261004_006__hmr_006_planning_planning_period.sql`, `V20261007_001__hmr_064_planning_plan_revision.sql`, `V20261007_002__hmr_065_planning_operational_plan.sql`, `V20261008_019__hmr_094_planning_target_value_policy.sql`, `V20261008_020__hmr_094_planning_plan_target_integrity.sql`, `V20261008_026__hmr_080_planning_nomination_integrity.sql`

Current JPA entity classes:

- `ExpectedFlowStateJpaEntity`
- `ForecastPointJpaEntity`
- `ForecastSeriesJpaEntity`
- `NominationJpaEntity`
- `NominationScheduleLineJpaEntity`
- `OperationalPlanJpaEntity`
- `PlanActualReviewSnapshotJpaEntity`
- `PlanApprovalReferenceJpaEntity`
- `PlanConstraintJpaEntity`
- `PlanRevisionJpaEntity`
- `PlanScenarioJpaEntity`
- `PlanTargetJpaEntity`
- `PlannedOperationWindowJpaEntity`
- `PlanningCatalogEntryJpaEntity`
- `PlanningCatalogTranslationJpaEntity`
- `PlanningPeriodJpaEntity`

### reporting

JPA persistence entities: **22**

Primary module migration: `V20260611_024__create_reporting_tables.sql`

Migration filenames containing this module identifier (6): `V20260611_024__create_reporting_tables.sql`, `V20261004_013__hmr_013_reporting_report_definition.sql`, `V20261004_028__hmr_028_organization_reporting_line.sql`, `V20261004_048__hmr_048_reporting_report_request.sql`, `V20261007_012__hmr_057_reporting_report_run.sql`, `V20261007_013__hmr_093_reporting_report_output_artifact.sql`

Current JPA entity classes:

- `ReportAccessPolicyJpaEntity`
- `ReportCatalogEntryJpaEntity`
- `ReportCatalogTranslationJpaEntity`
- `ReportChartResultJpaEntity`
- `ReportDataSourceBindingJpaEntity`
- `ReportDefinitionJpaEntity`
- `ReportDistributionRecordJpaEntity`
- `ReportDistributionTargetJpaEntity`
- `ReportInputSnapshotJpaEntity`
- `ReportOutputArtifactJpaEntity`
- `ReportParameterDefinitionJpaEntity`
- `ReportParameterValueJpaEntity`
- `ReportPublicationJpaEntity`
- `ReportRequestJpaEntity`
- `ReportRunJpaEntity`
- `ReportScheduleJpaEntity`
- `ReportScheduleParameterJpaEntity`
- `ReportSectionDefinitionJpaEntity`
- `ReportSectionResultJpaEntity`
- `ReportTableResultJpaEntity`
- `ReportTemplateJpaEntity`
- `ReportTemplateVersionJpaEntity`

### risk

JPA persistence entities: **25**

Primary module migration: `V20260611_011__create_risk_tables.sql`

Migration filenames containing this module identifier (7): `V20260611_011__create_risk_tables.sql`, `V20261004_029__hmr_029_risk_risk_matrix_cell.sql`, `V20261004_049__hmr_049_risk_risk_register.sql`, `V20261005_001__provision_risk_register_created_audit_taxonomy.sql`, `V20261008_004__hmr_077_risk_evidence_identity_integrity.sql`, `V20261008_005__hmr_058_risk_assessment_governance.sql`, `V20261008_006__provision_risk_assessment_audit_taxonomy.sql`

Current JPA entity classes:

- `ResidualRiskAssessmentJpaEntity`
- `RiskAcceptanceJpaEntity`
- `RiskAggregationSnapshotJpaEntity`
- `RiskAssessmentJpaEntity`
- `RiskAssessmentScopeJpaEntity`
- `RiskAssessmentScoringJpaEntity`
- `RiskCatalogEntryJpaEntity`
- `RiskCatalogTranslationJpaEntity`
- `RiskConsequenceJpaEntity`
- `RiskControlJpaEntity`
- `RiskEvidenceLinkJpaEntity`
- `RiskExposureJpaEntity`
- `RiskLikelihoodJpaEntity`
- `RiskMatrixCellJpaEntity`
- `RiskMatrixJpaEntity`
- `RiskMitigationMeasureJpaEntity`
- `RiskRatingJpaEntity`
- `RiskRegisterJpaEntity`
- `RiskReviewJpaEntity`
- `RiskScenarioJpaEntity`
- `RiskScoreJpaEntity`
- `RiskSourceJpaEntity`
- `RiskThreatJpaEntity`
- `RiskTreatmentActionJpaEntity`
- `RiskTreatmentPlanJpaEntity`

### simulation

JPA persistence entities: **25**

Primary module migration: `V20260611_022__create_simulation_tables.sql`

Migration filenames containing this module identifier (6): `V20260611_022__create_simulation_tables.sql`, `V20261004_009__hmr_009_simulation_simulation_model.sql`, `V20261004_034__hmr_034_simulation_simulation_scenario.sql`, `V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`, `V20261008_002__hmr_079_simulation_recommendation_integrity.sql`, `V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql`

Current JPA entity classes:

- `SimulationCandidateChangeJpaEntity`
- `SimulationCandidateOperatingConditionJpaEntity`
- `SimulationCandidateScoreJpaEntity`
- `SimulationCatalogEntryJpaEntity`
- `SimulationCatalogTranslationJpaEntity`
- `SimulationConstraintEvaluationJpaEntity`
- `SimulationConstraintJpaEntity`
- `SimulationEvidenceLinkJpaEntity`
- `SimulationInputDatasetJpaEntity`
- `SimulationInputSnapshotJpaEntity`
- `SimulationModelJpaEntity`
- `SimulationModelVersionJpaEntity`
- `SimulationObjectiveJpaEntity`
- `SimulationOptimizationCandidateJpaEntity`
- `SimulationRecommendationJpaEntity`
- `SimulationResultSeriesReferenceJpaEntity`
- `SimulationResultSummaryJpaEntity`
- `SimulationResultValueJpaEntity`
- `SimulationRunJpaEntity`
- `SimulationRunStepJpaEntity`
- `SimulationScenarioAssumptionJpaEntity`
- `SimulationScenarioJpaEntity`
- `SimulationSensitivityAnalysisJpaEntity`
- `SimulationSolverTraceJpaEntity`
- `SimulationValidationFindingJpaEntity`

### telemetry

JPA persistence entities: **16**

Primary module migration: `V20260611_005__create_telemetry_tables.sql`

Migration filenames containing this module identifier (5): `V20260611_005__create_telemetry_tables.sql`, `V20261004_005__hmr_005_telemetry_telemetry_point.sql`, `V20261004_030__hmr_030_telemetry_telemetry_source.sql`, `V20261004_033__hmr_033_telemetry_telemetry_reading.sql`, `V20261006_008__hmr_053_trusted_telemetry_gate.sql`

Current JPA entity classes:

- `TelemetryCatalogEntryJpaEntity`
- `TelemetryCatalogTranslationJpaEntity`
- `TelemetryDeviceJpaEntity`
- `TelemetryExternalTagMappingJpaEntity`
- `TelemetryIngestionBatchJpaEntity`
- `TelemetryPointBindingJpaEntity`
- `TelemetryPointJpaEntity`
- `TelemetryPointStateSnapshotJpaEntity`
- `TelemetryQualityAssessmentJpaEntity`
- `TelemetryQuarantineRecordJpaEntity`
- `TelemetryReadingJpaEntity`
- `TelemetrySourceEndpointJpaEntity`
- `TelemetrySourceJpaEntity`
- `TelemetryUnitJpaEntity`
- `TelemetryValidationRuleJpaEntity`
- `TrustedTelemetryReadingJpaEntity`

### topology

JPA persistence entities: **22**

Primary module migration: `V20260611_004__create_topology_tables.sql`

Migration filenames containing this module identifier (4): `V20260611_004__create_topology_tables.sql`, `V20261004_022__hmr_022_topology_pipeline_system.sql`, `V20261004_031__hmr_031_topology_topology_connection.sql`, `V20261004_042__hmr_042_topology_pipeline.sql`

Current JPA entity classes:

- `ConnectionTypeJpaEntity`
- `EquipmentAttributeDefinitionJpaEntity`
- `EquipmentAttributeValueJpaEntity`
- `EquipmentJpaEntity`
- `EquipmentTypeJpaEntity`
- `EquipmentTypeVersionJpaEntity`
- `FacilityAttributeDefinitionJpaEntity`
- `FacilityAttributeValueJpaEntity`
- `FacilityJpaEntity`
- `FacilityNodeBindingJpaEntity`
- `FacilityTypeJpaEntity`
- `FacilityTypeVersionJpaEntity`
- `MeasurementLocationJpaEntity`
- `PipelineJpaEntity`
- `PipelineSegmentJpaEntity`
- `PipelineSystemFacilityJpaEntity`
- `PipelineSystemJpaEntity`
- `PipelineSystemTypeJpaEntity`
- `PipelineTypeJpaEntity`
- `TopologyConnectionJpaEntity`
- `TopologyNodeJpaEntity`
- `TopologySnapshotJpaEntity`

### workflow

JPA persistence entities: **17**

Primary module migration: `V20260611_016__create_workflow_tables.sql`

Migration filenames containing this module identifier (8): `V20260611_016__create_workflow_tables.sql`, `V20261004_003__hmr_003_workflow_workflow_definition.sql`, `V20261004_043__hmr_043_workflow_workflow_step.sql`, `V20261006_014__hmr_055_workflow_instance.sql`, `V20261006_015__hmr_061_workflow_transition.sql`, `V20261006_016__hmr_066_workflow_task.sql`, `V20261006_017__hmr_081_workflow_action.sql`, `V20261006_018__hmr_099_workflow_state_history.sql`

Current JPA entity classes:

- `WorkflowActionJpaEntity`
- `WorkflowAssignmentJpaEntity`
- `WorkflowAuditOutboxReferenceJpaEntity`
- `WorkflowCatalogEntryJpaEntity`
- `WorkflowCatalogTranslationJpaEntity`
- `WorkflowCommentJpaEntity`
- `WorkflowDefinitionJpaEntity`
- `WorkflowDefinitionTargetBindingJpaEntity`
- `WorkflowDelegationJpaEntity`
- `WorkflowEscalationRuleJpaEntity`
- `WorkflowInstanceJpaEntity`
- `WorkflowSlaPolicyJpaEntity`
- `WorkflowStateHistoryJpaEntity`
- `WorkflowStepAssignmentRuleJpaEntity`
- `WorkflowStepJpaEntity`
- `WorkflowTaskJpaEntity`
- `WorkflowTransitionJpaEntity`

## Interpretation Rules

- This is a generated persistence/ownership dictionary, not a substitute for migration SQL.
- Entity-class presence does not imply a public API surface.
- A migration filename containing a module name is provenance, not proof that the migration affects only that module.
- Generic referential-integrity migrations may affect several modules even when no module name appears in the filename.
- Current schema state is the ordered result of all 139 migrations.
- Retention/archival classifications belong to HPR-P2-010 and are not invented here.

## HPR-P2-013 source refresh and historical applicability

Current repository verification: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, 2026-10-09. Source inventory contains
**139** unique versioned migrations and **470** module @Entity classes across
**24** modules, including **25** Risk entities. Current tail:
`V20261008_026__hmr_080_planning_nomination_integrity.sql`. The original HPR-P2-006 generation at
`aeb9008d74b90f102ab8706b9a23f1a6eb6cbe9c` recorded 82 migrations/469 entities;
that is preserved historical generation evidence, superseded for current inventory.

Retained P1 deployed/recovery evidence keeps its original deployed SHA, 82-migration
scope and measured RPO/RTO. This source refresh is not an assertion that all 139
migrations have been deployed or physically recovered. Database documentation
completion and exact-head CI do not establish current production-data acceptance.
Full P2 closure verification remains pending both CI workflows on the resulting
implementation commit; P3 remains DEFERRED. No schema/data/runtime change is made.
