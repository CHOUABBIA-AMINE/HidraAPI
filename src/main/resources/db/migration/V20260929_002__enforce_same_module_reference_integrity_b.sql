-- HIDRA repository-wide same-module reference integrity (batch B)
-- Roadmap: HRA-111
-- Generated from HRA-110 approved same-module ownership, reconciled on live main.
-- PostgreSQL NOT VALID + VALIDATE is intentional: new writes fail closed immediately and
-- existing rows are verified before Flyway can commit the migration.
-- Cross-module, historical, external, typed/polymorphic identifiers are deliberately excluded.

-- integrity: 24 HRA-111 same-module foreign keys
-- hidra_integrity_assessment_scope.assessment_id -> hidra_integrity_assessment.id
ALTER TABLE hidra_integrity_assessment_scope
    ADD CONSTRAINT fk_hra111_integrity_001
    FOREIGN KEY (assessment_id) REFERENCES hidra_integrity_assessment (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_assessment_scope VALIDATE CONSTRAINT fk_hra111_integrity_001;

-- hidra_integrity_assessment.assessment_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_assessment
    ADD CONSTRAINT fk_hra111_integrity_002
    FOREIGN KEY (assessment_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_assessment VALIDATE CONSTRAINT fk_hra111_integrity_002;

-- hidra_integrity_case_status_history.integrity_case_id -> hidra_integrity_case.id
ALTER TABLE hidra_integrity_case_status_history
    ADD CONSTRAINT fk_hra111_integrity_003
    FOREIGN KEY (integrity_case_id) REFERENCES hidra_integrity_case (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_case_status_history VALIDATE CONSTRAINT fk_hra111_integrity_003;

-- hidra_integrity_case.case_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_case
    ADD CONSTRAINT fk_hra111_integrity_004
    FOREIGN KEY (case_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_case VALIDATE CONSTRAINT fk_hra111_integrity_004;

-- hidra_integrity_catalog_translation.catalog_entry_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_catalog_translation
    ADD CONSTRAINT fk_hra111_integrity_005
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_catalog_translation VALIDATE CONSTRAINT fk_hra111_integrity_005;

-- hidra_integrity_cathodic_protection_measurement.survey_id -> hidra_integrity_cathodic_protection_survey.id
ALTER TABLE hidra_integrity_cathodic_protection_measurement
    ADD CONSTRAINT fk_hra111_integrity_006
    FOREIGN KEY (survey_id) REFERENCES hidra_integrity_cathodic_protection_survey (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_cathodic_protection_measurement VALIDATE CONSTRAINT fk_hra111_integrity_006;

-- hidra_integrity_cathodic_protection_survey.survey_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_cathodic_protection_survey
    ADD CONSTRAINT fk_hra111_integrity_007
    FOREIGN KEY (survey_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_cathodic_protection_survey VALIDATE CONSTRAINT fk_hra111_integrity_007;

-- hidra_integrity_coating_condition_observation.coating_condition_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_coating_condition_observation
    ADD CONSTRAINT fk_hra111_integrity_008
    FOREIGN KEY (coating_condition_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_coating_condition_observation VALIDATE CONSTRAINT fk_hra111_integrity_008;

-- hidra_integrity_corrosion_feature.corrosion_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_corrosion_feature
    ADD CONSTRAINT fk_hra111_integrity_009
    FOREIGN KEY (corrosion_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_corrosion_feature VALIDATE CONSTRAINT fk_hra111_integrity_009;

-- hidra_integrity_defect_assessment.assessment_method_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_defect_assessment
    ADD CONSTRAINT fk_hra111_integrity_010
    FOREIGN KEY (assessment_method_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_defect_assessment VALIDATE CONSTRAINT fk_hra111_integrity_010;

-- hidra_integrity_defect_assessment.defect_id -> hidra_integrity_pipeline_defect.id
ALTER TABLE hidra_integrity_defect_assessment
    ADD CONSTRAINT fk_hra111_integrity_011
    FOREIGN KEY (defect_id) REFERENCES hidra_integrity_pipeline_defect (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_defect_assessment VALIDATE CONSTRAINT fk_hra111_integrity_011;

-- hidra_integrity_defect_measurement.defect_id -> hidra_integrity_pipeline_defect.id
ALTER TABLE hidra_integrity_defect_measurement
    ADD CONSTRAINT fk_hra111_integrity_012
    FOREIGN KEY (defect_id) REFERENCES hidra_integrity_pipeline_defect (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_defect_measurement VALIDATE CONSTRAINT fk_hra111_integrity_012;

-- hidra_integrity_defect_measurement.measurement_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_defect_measurement
    ADD CONSTRAINT fk_hra111_integrity_013
    FOREIGN KEY (measurement_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_defect_measurement VALIDATE CONSTRAINT fk_hra111_integrity_013;

-- hidra_integrity_defect_measurement.unit_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_defect_measurement
    ADD CONSTRAINT fk_hra111_integrity_014
    FOREIGN KEY (unit_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_defect_measurement VALIDATE CONSTRAINT fk_hra111_integrity_014;

-- hidra_integrity_inspection_campaign.inspection_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_inspection_campaign
    ADD CONSTRAINT fk_hra111_integrity_015
    FOREIGN KEY (inspection_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_inspection_campaign VALIDATE CONSTRAINT fk_hra111_integrity_015;

-- hidra_integrity_inspection_finding.finding_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_inspection_finding
    ADD CONSTRAINT fk_hra111_integrity_016
    FOREIGN KEY (finding_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_inspection_finding VALIDATE CONSTRAINT fk_hra111_integrity_016;

-- hidra_integrity_inspection_finding.inspection_run_id -> hidra_integrity_inspection_run.id
ALTER TABLE hidra_integrity_inspection_finding
    ADD CONSTRAINT fk_hra111_integrity_017
    FOREIGN KEY (inspection_run_id) REFERENCES hidra_integrity_inspection_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_inspection_finding VALIDATE CONSTRAINT fk_hra111_integrity_017;

-- hidra_integrity_inspection_run.campaign_id -> hidra_integrity_inspection_campaign.id
ALTER TABLE hidra_integrity_inspection_run
    ADD CONSTRAINT fk_hra111_integrity_018
    FOREIGN KEY (campaign_id) REFERENCES hidra_integrity_inspection_campaign (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_inspection_run VALIDATE CONSTRAINT fk_hra111_integrity_018;

-- hidra_integrity_pipeline_defect.defect_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_pipeline_defect
    ADD CONSTRAINT fk_hra111_integrity_019
    FOREIGN KEY (defect_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_pipeline_defect VALIDATE CONSTRAINT fk_hra111_integrity_019;

-- hidra_integrity_program.program_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_program
    ADD CONSTRAINT fk_hra111_integrity_020
    FOREIGN KEY (program_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_program VALIDATE CONSTRAINT fk_hra111_integrity_020;

-- hidra_integrity_recommendation.recommendation_type_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_recommendation
    ADD CONSTRAINT fk_hra111_integrity_021
    FOREIGN KEY (recommendation_type_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_recommendation VALIDATE CONSTRAINT fk_hra111_integrity_021;

-- hidra_integrity_remaining_life_estimate.method_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_remaining_life_estimate
    ADD CONSTRAINT fk_hra111_integrity_022
    FOREIGN KEY (method_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_remaining_life_estimate VALIDATE CONSTRAINT fk_hra111_integrity_022;

-- hidra_integrity_remaining_life_estimate.remaining_life_unit_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_remaining_life_estimate
    ADD CONSTRAINT fk_hra111_integrity_023
    FOREIGN KEY (remaining_life_unit_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_remaining_life_estimate VALIDATE CONSTRAINT fk_hra111_integrity_023;

-- hidra_integrity_wall_thickness_measurement.thickness_unit_id -> hidra_integrity_catalog_entry.id
ALTER TABLE hidra_integrity_wall_thickness_measurement
    ADD CONSTRAINT fk_hra111_integrity_024
    FOREIGN KEY (thickness_unit_id) REFERENCES hidra_integrity_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integrity_wall_thickness_measurement VALIDATE CONSTRAINT fk_hra111_integrity_024;

-- assets: 24 HRA-111 same-module foreign keys
-- hidra_asset_catalog_translation.catalog_entry_id -> hidra_asset_catalog_entry.id
ALTER TABLE hidra_asset_catalog_translation
    ADD CONSTRAINT fk_hra111_assets_001
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_asset_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_catalog_translation VALIDATE CONSTRAINT fk_hra111_assets_001;

-- hidra_asset_condition_record.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_condition_record
    ADD CONSTRAINT fk_hra111_assets_002
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_condition_record VALIDATE CONSTRAINT fk_hra111_assets_002;

-- hidra_asset_document_reference.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_document_reference
    ADD CONSTRAINT fk_hra111_assets_003
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_document_reference VALIDATE CONSTRAINT fk_hra111_assets_003;

-- hidra_asset_installation.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_installation
    ADD CONSTRAINT fk_hra111_assets_004
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_installation VALIDATE CONSTRAINT fk_hra111_assets_004;

-- hidra_asset_lifecycle_event.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_lifecycle_event
    ADD CONSTRAINT fk_hra111_assets_005
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_lifecycle_event VALIDATE CONSTRAINT fk_hra111_assets_005;

-- hidra_asset_maintainable_asset.asset_type_id -> hidra_asset_type.id
ALTER TABLE hidra_asset_maintainable_asset
    ADD CONSTRAINT fk_hra111_assets_006
    FOREIGN KEY (asset_type_id) REFERENCES hidra_asset_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintainable_asset VALIDATE CONSTRAINT fk_hra111_assets_006;

-- hidra_asset_maintenance_execution_record.work_order_id -> hidra_asset_maintenance_work_order.id
ALTER TABLE hidra_asset_maintenance_execution_record
    ADD CONSTRAINT fk_hra111_assets_007
    FOREIGN KEY (work_order_id) REFERENCES hidra_asset_maintenance_work_order (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_execution_record VALIDATE CONSTRAINT fk_hra111_assets_007;

-- hidra_asset_maintenance_plan.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_maintenance_plan
    ADD CONSTRAINT fk_hra111_assets_008
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_plan VALIDATE CONSTRAINT fk_hra111_assets_008;

-- hidra_asset_maintenance_strategy.strategy_type_id -> hidra_asset_catalog_entry.id
ALTER TABLE hidra_asset_maintenance_strategy
    ADD CONSTRAINT fk_hra111_assets_009
    FOREIGN KEY (strategy_type_id) REFERENCES hidra_asset_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_strategy VALIDATE CONSTRAINT fk_hra111_assets_009;

-- hidra_asset_maintenance_task_template.task_type_id -> hidra_asset_catalog_entry.id
ALTER TABLE hidra_asset_maintenance_task_template
    ADD CONSTRAINT fk_hra111_assets_010
    FOREIGN KEY (task_type_id) REFERENCES hidra_asset_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_task_template VALIDATE CONSTRAINT fk_hra111_assets_010;

-- hidra_asset_maintenance_work_order_task.task_type_id -> hidra_asset_catalog_entry.id
ALTER TABLE hidra_asset_maintenance_work_order_task
    ADD CONSTRAINT fk_hra111_assets_011
    FOREIGN KEY (task_type_id) REFERENCES hidra_asset_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_work_order_task VALIDATE CONSTRAINT fk_hra111_assets_011;

-- hidra_asset_maintenance_work_order_task.work_order_id -> hidra_asset_maintenance_work_order.id
ALTER TABLE hidra_asset_maintenance_work_order_task
    ADD CONSTRAINT fk_hra111_assets_012
    FOREIGN KEY (work_order_id) REFERENCES hidra_asset_maintenance_work_order (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_work_order_task VALIDATE CONSTRAINT fk_hra111_assets_012;

-- hidra_asset_maintenance_work_order.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_maintenance_work_order
    ADD CONSTRAINT fk_hra111_assets_013
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_work_order VALIDATE CONSTRAINT fk_hra111_assets_013;

-- hidra_asset_maintenance_work_order.work_order_type_id -> hidra_asset_catalog_entry.id
ALTER TABLE hidra_asset_maintenance_work_order
    ADD CONSTRAINT fk_hra111_assets_014
    FOREIGN KEY (work_order_type_id) REFERENCES hidra_asset_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_work_order VALIDATE CONSTRAINT fk_hra111_assets_014;

-- hidra_asset_meter_reading_reference.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_meter_reading_reference
    ADD CONSTRAINT fk_hra111_assets_015
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_meter_reading_reference VALIDATE CONSTRAINT fk_hra111_assets_015;

-- hidra_asset_model.asset_type_id -> hidra_asset_type.id
ALTER TABLE hidra_asset_model
    ADD CONSTRAINT fk_hra111_assets_016
    FOREIGN KEY (asset_type_id) REFERENCES hidra_asset_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_model VALIDATE CONSTRAINT fk_hra111_assets_016;

-- hidra_asset_serial_identity.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_serial_identity
    ADD CONSTRAINT fk_hra111_assets_017
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_serial_identity VALIDATE CONSTRAINT fk_hra111_assets_017;

-- hidra_asset_service_contract_reference.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_service_contract_reference
    ADD CONSTRAINT fk_hra111_assets_018
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_service_contract_reference VALIDATE CONSTRAINT fk_hra111_assets_018;

-- hidra_asset_spare_part_compatibility.spare_part_id -> hidra_asset_spare_part.id
ALTER TABLE hidra_asset_spare_part_compatibility
    ADD CONSTRAINT fk_hra111_assets_019
    FOREIGN KEY (spare_part_id) REFERENCES hidra_asset_spare_part (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_spare_part_compatibility VALIDATE CONSTRAINT fk_hra111_assets_019;

-- hidra_asset_technical_attribute_definition.asset_type_id -> hidra_asset_type.id
ALTER TABLE hidra_asset_technical_attribute_definition
    ADD CONSTRAINT fk_hra111_assets_020
    FOREIGN KEY (asset_type_id) REFERENCES hidra_asset_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_technical_attribute_definition VALIDATE CONSTRAINT fk_hra111_assets_020;

-- hidra_asset_technical_attribute_value.attribute_definition_id -> hidra_asset_technical_attribute_definition.id
ALTER TABLE hidra_asset_technical_attribute_value
    ADD CONSTRAINT fk_hra111_assets_021
    FOREIGN KEY (attribute_definition_id) REFERENCES hidra_asset_technical_attribute_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_technical_attribute_value VALIDATE CONSTRAINT fk_hra111_assets_021;

-- hidra_asset_technical_attribute_value.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_technical_attribute_value
    ADD CONSTRAINT fk_hra111_assets_022
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_technical_attribute_value VALIDATE CONSTRAINT fk_hra111_assets_022;

-- hidra_asset_type_translation.asset_type_id -> hidra_asset_type.id
ALTER TABLE hidra_asset_type_translation
    ADD CONSTRAINT fk_hra111_assets_023
    FOREIGN KEY (asset_type_id) REFERENCES hidra_asset_type (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_type_translation VALIDATE CONSTRAINT fk_hra111_assets_023;

-- hidra_asset_warranty.maintainable_asset_id -> hidra_asset_maintainable_asset.id
ALTER TABLE hidra_asset_warranty
    ADD CONSTRAINT fk_hra111_assets_024
    FOREIGN KEY (maintainable_asset_id) REFERENCES hidra_asset_maintainable_asset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_warranty VALIDATE CONSTRAINT fk_hra111_assets_024;

-- custody: 33 HRA-111 same-module foreign keys
-- hidra_custody_agreement_party.agreement_id -> hidra_custody_agreement.id
ALTER TABLE hidra_custody_agreement_party
    ADD CONSTRAINT fk_hra111_custody_001
    FOREIGN KEY (agreement_id) REFERENCES hidra_custody_agreement (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_agreement_party VALIDATE CONSTRAINT fk_hra111_custody_001;

-- hidra_custody_agreement.agreement_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_agreement
    ADD CONSTRAINT fk_hra111_custody_002
    FOREIGN KEY (agreement_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_agreement VALIDATE CONSTRAINT fk_hra111_custody_002;

-- hidra_custody_agreement.transfer_point_id -> hidra_custody_transfer_point.id
ALTER TABLE hidra_custody_agreement
    ADD CONSTRAINT fk_hra111_custody_003
    FOREIGN KEY (transfer_point_id) REFERENCES hidra_custody_transfer_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_agreement VALIDATE CONSTRAINT fk_hra111_custody_003;

-- hidra_custody_batch.agreement_id -> hidra_custody_agreement.id
ALTER TABLE hidra_custody_batch
    ADD CONSTRAINT fk_hra111_custody_004
    FOREIGN KEY (agreement_id) REFERENCES hidra_custody_agreement (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_batch VALIDATE CONSTRAINT fk_hra111_custody_004;

-- hidra_custody_batch.measurement_period_id -> hidra_custody_measurement_period.id
ALTER TABLE hidra_custody_batch
    ADD CONSTRAINT fk_hra111_custody_005
    FOREIGN KEY (measurement_period_id) REFERENCES hidra_custody_measurement_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_batch VALIDATE CONSTRAINT fk_hra111_custody_005;

-- hidra_custody_batch.product_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_batch
    ADD CONSTRAINT fk_hra111_custody_006
    FOREIGN KEY (product_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_batch VALIDATE CONSTRAINT fk_hra111_custody_006;

-- hidra_custody_catalog_translation.catalog_entry_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_catalog_translation
    ADD CONSTRAINT fk_hra111_custody_007
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_catalog_translation VALIDATE CONSTRAINT fk_hra111_custody_007;

-- hidra_custody_correction_factor.factor_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_correction_factor
    ADD CONSTRAINT fk_hra111_custody_008
    FOREIGN KEY (factor_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_correction_factor VALIDATE CONSTRAINT fk_hra111_custody_008;

-- hidra_custody_correction_factor.quantity_calculation_id -> hidra_custody_quantity_calculation.id
ALTER TABLE hidra_custody_correction_factor
    ADD CONSTRAINT fk_hra111_custody_009
    FOREIGN KEY (quantity_calculation_id) REFERENCES hidra_custody_quantity_calculation (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_correction_factor VALIDATE CONSTRAINT fk_hra111_custody_009;

-- hidra_custody_discrepancy.discrepancy_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_discrepancy
    ADD CONSTRAINT fk_hra111_custody_010
    FOREIGN KEY (discrepancy_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_discrepancy VALIDATE CONSTRAINT fk_hra111_custody_010;

-- hidra_custody_discrepancy.reconciliation_id -> hidra_custody_reconciliation.id
ALTER TABLE hidra_custody_discrepancy
    ADD CONSTRAINT fk_hra111_custody_011
    FOREIGN KEY (reconciliation_id) REFERENCES hidra_custody_reconciliation (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_discrepancy VALIDATE CONSTRAINT fk_hra111_custody_011;

-- hidra_custody_measurement_period.agreement_id -> hidra_custody_agreement.id
ALTER TABLE hidra_custody_measurement_period
    ADD CONSTRAINT fk_hra111_custody_012
    FOREIGN KEY (agreement_id) REFERENCES hidra_custody_agreement (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_measurement_period VALIDATE CONSTRAINT fk_hra111_custody_012;

-- hidra_custody_measurement_period.transfer_point_id -> hidra_custody_transfer_point.id
ALTER TABLE hidra_custody_measurement_period
    ADD CONSTRAINT fk_hra111_custody_013
    FOREIGN KEY (transfer_point_id) REFERENCES hidra_custody_transfer_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_measurement_period VALIDATE CONSTRAINT fk_hra111_custody_013;

-- hidra_custody_measurement_snapshot.measurement_period_id -> hidra_custody_measurement_period.id
ALTER TABLE hidra_custody_measurement_snapshot
    ADD CONSTRAINT fk_hra111_custody_014
    FOREIGN KEY (measurement_period_id) REFERENCES hidra_custody_measurement_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_measurement_snapshot VALIDATE CONSTRAINT fk_hra111_custody_014;

-- hidra_custody_measurement_snapshot.measurement_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_measurement_snapshot
    ADD CONSTRAINT fk_hra111_custody_015
    FOREIGN KEY (measurement_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_measurement_snapshot VALIDATE CONSTRAINT fk_hra111_custody_015;

-- hidra_custody_measurement_snapshot.observed_unit_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_measurement_snapshot
    ADD CONSTRAINT fk_hra111_custody_016
    FOREIGN KEY (observed_unit_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_measurement_snapshot VALIDATE CONSTRAINT fk_hra111_custody_016;

-- hidra_custody_meter_run_snapshot.measurement_period_id -> hidra_custody_measurement_period.id
ALTER TABLE hidra_custody_meter_run_snapshot
    ADD CONSTRAINT fk_hra111_custody_017
    FOREIGN KEY (measurement_period_id) REFERENCES hidra_custody_measurement_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_meter_run_snapshot VALIDATE CONSTRAINT fk_hra111_custody_017;

-- hidra_custody_meter_run_snapshot.metering_system_id -> hidra_custody_metering_system.id
ALTER TABLE hidra_custody_meter_run_snapshot
    ADD CONSTRAINT fk_hra111_custody_018
    FOREIGN KEY (metering_system_id) REFERENCES hidra_custody_metering_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_meter_run_snapshot VALIDATE CONSTRAINT fk_hra111_custody_018;

-- hidra_custody_metering_system.transfer_point_id -> hidra_custody_transfer_point.id
ALTER TABLE hidra_custody_metering_system
    ADD CONSTRAINT fk_hra111_custody_019
    FOREIGN KEY (transfer_point_id) REFERENCES hidra_custody_transfer_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_metering_system VALIDATE CONSTRAINT fk_hra111_custody_019;

-- hidra_custody_quality_sample.measurement_period_id -> hidra_custody_measurement_period.id
ALTER TABLE hidra_custody_quality_sample
    ADD CONSTRAINT fk_hra111_custody_020
    FOREIGN KEY (measurement_period_id) REFERENCES hidra_custody_measurement_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_quality_sample VALIDATE CONSTRAINT fk_hra111_custody_020;

-- hidra_custody_quality_sample.product_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_quality_sample
    ADD CONSTRAINT fk_hra111_custody_021
    FOREIGN KEY (product_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_quality_sample VALIDATE CONSTRAINT fk_hra111_custody_021;

-- hidra_custody_quality_sample.sample_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_quality_sample
    ADD CONSTRAINT fk_hra111_custody_022
    FOREIGN KEY (sample_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_quality_sample VALIDATE CONSTRAINT fk_hra111_custody_022;

-- hidra_custody_quantity_calculation.measurement_period_id -> hidra_custody_measurement_period.id
ALTER TABLE hidra_custody_quantity_calculation
    ADD CONSTRAINT fk_hra111_custody_023
    FOREIGN KEY (measurement_period_id) REFERENCES hidra_custody_measurement_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_quantity_calculation VALIDATE CONSTRAINT fk_hra111_custody_023;

-- hidra_custody_quantity_calculation.quantity_unit_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_quantity_calculation
    ADD CONSTRAINT fk_hra111_custody_024
    FOREIGN KEY (quantity_unit_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_quantity_calculation VALIDATE CONSTRAINT fk_hra111_custody_024;

-- hidra_custody_reconciliation.agreement_id -> hidra_custody_agreement.id
ALTER TABLE hidra_custody_reconciliation
    ADD CONSTRAINT fk_hra111_custody_025
    FOREIGN KEY (agreement_id) REFERENCES hidra_custody_agreement (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_reconciliation VALIDATE CONSTRAINT fk_hra111_custody_025;

-- hidra_custody_reconciliation.measurement_period_id -> hidra_custody_measurement_period.id
ALTER TABLE hidra_custody_reconciliation
    ADD CONSTRAINT fk_hra111_custody_026
    FOREIGN KEY (measurement_period_id) REFERENCES hidra_custody_measurement_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_reconciliation VALIDATE CONSTRAINT fk_hra111_custody_026;

-- hidra_custody_ticket_line.line_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_ticket_line
    ADD CONSTRAINT fk_hra111_custody_027
    FOREIGN KEY (line_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_ticket_line VALIDATE CONSTRAINT fk_hra111_custody_027;

-- hidra_custody_ticket_line.product_type_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_ticket_line
    ADD CONSTRAINT fk_hra111_custody_028
    FOREIGN KEY (product_type_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_ticket_line VALIDATE CONSTRAINT fk_hra111_custody_028;

-- hidra_custody_ticket_line.quantity_unit_id -> hidra_custody_catalog_entry.id
ALTER TABLE hidra_custody_ticket_line
    ADD CONSTRAINT fk_hra111_custody_029
    FOREIGN KEY (quantity_unit_id) REFERENCES hidra_custody_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_ticket_line VALIDATE CONSTRAINT fk_hra111_custody_029;

-- hidra_custody_ticket_line.transfer_ticket_id -> hidra_custody_transfer_ticket.id
ALTER TABLE hidra_custody_ticket_line
    ADD CONSTRAINT fk_hra111_custody_030
    FOREIGN KEY (transfer_ticket_id) REFERENCES hidra_custody_transfer_ticket (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_ticket_line VALIDATE CONSTRAINT fk_hra111_custody_030;

-- hidra_custody_transfer_ticket.agreement_id -> hidra_custody_agreement.id
ALTER TABLE hidra_custody_transfer_ticket
    ADD CONSTRAINT fk_hra111_custody_031
    FOREIGN KEY (agreement_id) REFERENCES hidra_custody_agreement (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_transfer_ticket VALIDATE CONSTRAINT fk_hra111_custody_031;

-- hidra_custody_transfer_ticket.measurement_period_id -> hidra_custody_measurement_period.id
ALTER TABLE hidra_custody_transfer_ticket
    ADD CONSTRAINT fk_hra111_custody_032
    FOREIGN KEY (measurement_period_id) REFERENCES hidra_custody_measurement_period (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_transfer_ticket VALIDATE CONSTRAINT fk_hra111_custody_032;

-- hidra_custody_transfer_ticket.transfer_point_id -> hidra_custody_transfer_point.id
ALTER TABLE hidra_custody_transfer_ticket
    ADD CONSTRAINT fk_hra111_custody_033
    FOREIGN KEY (transfer_point_id) REFERENCES hidra_custody_transfer_point (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_transfer_ticket VALIDATE CONSTRAINT fk_hra111_custody_033;

-- workflow: 25 HRA-111 same-module foreign keys
-- hidra_workflow_action.instance_id -> hidra_workflow_instance.id
ALTER TABLE hidra_workflow_action
    ADD CONSTRAINT fk_hra111_workflow_001
    FOREIGN KEY (instance_id) REFERENCES hidra_workflow_instance (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_action VALIDATE CONSTRAINT fk_hra111_workflow_001;

-- hidra_workflow_assignment.task_id -> hidra_workflow_task.id
ALTER TABLE hidra_workflow_assignment
    ADD CONSTRAINT fk_hra111_workflow_002
    FOREIGN KEY (task_id) REFERENCES hidra_workflow_task (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_assignment VALIDATE CONSTRAINT fk_hra111_workflow_002;

-- hidra_workflow_audit_outbox_reference.instance_id -> hidra_workflow_instance.id
ALTER TABLE hidra_workflow_audit_outbox_reference
    ADD CONSTRAINT fk_hra111_workflow_003
    FOREIGN KEY (instance_id) REFERENCES hidra_workflow_instance (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_audit_outbox_reference VALIDATE CONSTRAINT fk_hra111_workflow_003;

-- hidra_workflow_comment.instance_id -> hidra_workflow_instance.id
ALTER TABLE hidra_workflow_comment
    ADD CONSTRAINT fk_hra111_workflow_004
    FOREIGN KEY (instance_id) REFERENCES hidra_workflow_instance (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_comment VALIDATE CONSTRAINT fk_hra111_workflow_004;

-- hidra_workflow_definition_target_binding.definition_id -> hidra_workflow_definition.id
ALTER TABLE hidra_workflow_definition_target_binding
    ADD CONSTRAINT fk_hra111_workflow_005
    FOREIGN KEY (definition_id) REFERENCES hidra_workflow_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_definition_target_binding VALIDATE CONSTRAINT fk_hra111_workflow_005;

-- hidra_workflow_definition_target_binding.target_type_id -> hidra_workflow_type_catalog.id
ALTER TABLE hidra_workflow_definition_target_binding
    ADD CONSTRAINT fk_hra111_workflow_006
    FOREIGN KEY (target_type_id) REFERENCES hidra_workflow_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_definition_target_binding VALIDATE CONSTRAINT fk_hra111_workflow_006;

-- hidra_workflow_definition_target_binding.workflow_purpose_id -> hidra_workflow_type_catalog.id
ALTER TABLE hidra_workflow_definition_target_binding
    ADD CONSTRAINT fk_hra111_workflow_007
    FOREIGN KEY (workflow_purpose_id) REFERENCES hidra_workflow_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_definition_target_binding VALIDATE CONSTRAINT fk_hra111_workflow_007;

-- hidra_workflow_definition.type_id -> hidra_workflow_type_catalog.id
ALTER TABLE hidra_workflow_definition
    ADD CONSTRAINT fk_hra111_workflow_008
    FOREIGN KEY (type_id) REFERENCES hidra_workflow_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_definition VALIDATE CONSTRAINT fk_hra111_workflow_008;

-- hidra_workflow_delegation.reason_id -> hidra_workflow_type_catalog.id
ALTER TABLE hidra_workflow_delegation
    ADD CONSTRAINT fk_hra111_workflow_009
    FOREIGN KEY (reason_id) REFERENCES hidra_workflow_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_delegation VALIDATE CONSTRAINT fk_hra111_workflow_009;

-- hidra_workflow_delegation.task_id -> hidra_workflow_task.id
ALTER TABLE hidra_workflow_delegation
    ADD CONSTRAINT fk_hra111_workflow_010
    FOREIGN KEY (task_id) REFERENCES hidra_workflow_task (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_delegation VALIDATE CONSTRAINT fk_hra111_workflow_010;

-- hidra_workflow_escalation_rule.definition_id -> hidra_workflow_definition.id
ALTER TABLE hidra_workflow_escalation_rule
    ADD CONSTRAINT fk_hra111_workflow_011
    FOREIGN KEY (definition_id) REFERENCES hidra_workflow_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_escalation_rule VALIDATE CONSTRAINT fk_hra111_workflow_011;

-- hidra_workflow_escalation_rule.step_id -> hidra_workflow_step.id
ALTER TABLE hidra_workflow_escalation_rule
    ADD CONSTRAINT fk_hra111_workflow_012
    FOREIGN KEY (step_id) REFERENCES hidra_workflow_step (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_escalation_rule VALIDATE CONSTRAINT fk_hra111_workflow_012;

-- hidra_workflow_instance.definition_id -> hidra_workflow_definition.id
ALTER TABLE hidra_workflow_instance
    ADD CONSTRAINT fk_hra111_workflow_013
    FOREIGN KEY (definition_id) REFERENCES hidra_workflow_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_instance VALIDATE CONSTRAINT fk_hra111_workflow_013;

-- hidra_workflow_instance.target_type_id -> hidra_workflow_type_catalog.id
ALTER TABLE hidra_workflow_instance
    ADD CONSTRAINT fk_hra111_workflow_014
    FOREIGN KEY (target_type_id) REFERENCES hidra_workflow_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_instance VALIDATE CONSTRAINT fk_hra111_workflow_014;

-- hidra_workflow_state_history.instance_id -> hidra_workflow_instance.id
ALTER TABLE hidra_workflow_state_history
    ADD CONSTRAINT fk_hra111_workflow_015
    FOREIGN KEY (instance_id) REFERENCES hidra_workflow_instance (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_state_history VALIDATE CONSTRAINT fk_hra111_workflow_015;

-- hidra_workflow_step_assignment_rule.assignment_mode_id -> hidra_workflow_type_catalog.id
ALTER TABLE hidra_workflow_step_assignment_rule
    ADD CONSTRAINT fk_hra111_workflow_016
    FOREIGN KEY (assignment_mode_id) REFERENCES hidra_workflow_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_step_assignment_rule VALIDATE CONSTRAINT fk_hra111_workflow_016;

-- hidra_workflow_step_assignment_rule.definition_id -> hidra_workflow_definition.id
ALTER TABLE hidra_workflow_step_assignment_rule
    ADD CONSTRAINT fk_hra111_workflow_017
    FOREIGN KEY (definition_id) REFERENCES hidra_workflow_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_step_assignment_rule VALIDATE CONSTRAINT fk_hra111_workflow_017;

-- hidra_workflow_step_assignment_rule.step_id -> hidra_workflow_step.id
ALTER TABLE hidra_workflow_step_assignment_rule
    ADD CONSTRAINT fk_hra111_workflow_018
    FOREIGN KEY (step_id) REFERENCES hidra_workflow_step (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_step_assignment_rule VALIDATE CONSTRAINT fk_hra111_workflow_018;

-- hidra_workflow_step.definition_id -> hidra_workflow_definition.id
ALTER TABLE hidra_workflow_step
    ADD CONSTRAINT fk_hra111_workflow_019
    FOREIGN KEY (definition_id) REFERENCES hidra_workflow_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_step VALIDATE CONSTRAINT fk_hra111_workflow_019;

-- hidra_workflow_task.instance_id -> hidra_workflow_instance.id
ALTER TABLE hidra_workflow_task
    ADD CONSTRAINT fk_hra111_workflow_020
    FOREIGN KEY (instance_id) REFERENCES hidra_workflow_instance (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_task VALIDATE CONSTRAINT fk_hra111_workflow_020;

-- hidra_workflow_task.step_id -> hidra_workflow_step.id
ALTER TABLE hidra_workflow_task
    ADD CONSTRAINT fk_hra111_workflow_021
    FOREIGN KEY (step_id) REFERENCES hidra_workflow_step (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_task VALIDATE CONSTRAINT fk_hra111_workflow_021;

-- hidra_workflow_transition.definition_id -> hidra_workflow_definition.id
ALTER TABLE hidra_workflow_transition
    ADD CONSTRAINT fk_hra111_workflow_022
    FOREIGN KEY (definition_id) REFERENCES hidra_workflow_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_transition VALIDATE CONSTRAINT fk_hra111_workflow_022;

-- hidra_workflow_transition.from_step_id -> hidra_workflow_step.id
ALTER TABLE hidra_workflow_transition
    ADD CONSTRAINT fk_hra111_workflow_023
    FOREIGN KEY (from_step_id) REFERENCES hidra_workflow_step (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_transition VALIDATE CONSTRAINT fk_hra111_workflow_023;

-- hidra_workflow_transition.to_step_id -> hidra_workflow_step.id
ALTER TABLE hidra_workflow_transition
    ADD CONSTRAINT fk_hra111_workflow_024
    FOREIGN KEY (to_step_id) REFERENCES hidra_workflow_step (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_transition VALIDATE CONSTRAINT fk_hra111_workflow_024;

-- hidra_workflow_type_translation.type_id -> hidra_workflow_type_catalog.id
ALTER TABLE hidra_workflow_type_translation
    ADD CONSTRAINT fk_hra111_workflow_025
    FOREIGN KEY (type_id) REFERENCES hidra_workflow_type_catalog (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_workflow_type_translation VALIDATE CONSTRAINT fk_hra111_workflow_025;

-- audit: 15 HRA-111 same-module foreign keys
-- hidra_audit_action_reference.action_type_id -> hidra_audit_catalog_entry.id
ALTER TABLE hidra_audit_action_reference
    ADD CONSTRAINT fk_hra111_audit_001
    FOREIGN KEY (action_type_id) REFERENCES hidra_audit_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_action_reference VALIDATE CONSTRAINT fk_hra111_audit_001;

-- hidra_audit_action_reference.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_action_reference
    ADD CONSTRAINT fk_hra111_audit_002
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_action_reference VALIDATE CONSTRAINT fk_hra111_audit_002;

-- hidra_audit_actor_snapshot.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_actor_snapshot
    ADD CONSTRAINT fk_hra111_audit_003
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_actor_snapshot VALIDATE CONSTRAINT fk_hra111_audit_003;

-- hidra_audit_before_after_value.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_before_after_value
    ADD CONSTRAINT fk_hra111_audit_004
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_before_after_value VALIDATE CONSTRAINT fk_hra111_audit_004;

-- hidra_audit_catalog_translation.catalog_entry_id -> hidra_audit_catalog_entry.id
ALTER TABLE hidra_audit_catalog_translation
    ADD CONSTRAINT fk_hra111_audit_005
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_audit_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_catalog_translation VALIDATE CONSTRAINT fk_hra111_audit_005;

-- hidra_audit_correlation_context.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_correlation_context
    ADD CONSTRAINT fk_hra111_audit_006
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_correlation_context VALIDATE CONSTRAINT fk_hra111_audit_006;

-- hidra_audit_decision_context.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_decision_context
    ADD CONSTRAINT fk_hra111_audit_007
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_decision_context VALIDATE CONSTRAINT fk_hra111_audit_007;

-- hidra_audit_event.event_category_id -> hidra_audit_catalog_entry.id
ALTER TABLE hidra_audit_event
    ADD CONSTRAINT fk_hra111_audit_008
    FOREIGN KEY (event_category_id) REFERENCES hidra_audit_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_event VALIDATE CONSTRAINT fk_hra111_audit_008;

-- hidra_audit_event.event_type_id -> hidra_audit_catalog_entry.id
ALTER TABLE hidra_audit_event
    ADD CONSTRAINT fk_hra111_audit_009
    FOREIGN KEY (event_type_id) REFERENCES hidra_audit_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_event VALIDATE CONSTRAINT fk_hra111_audit_009;

-- hidra_audit_evidence_link.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_evidence_link
    ADD CONSTRAINT fk_hra111_audit_010
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_evidence_link VALIDATE CONSTRAINT fk_hra111_audit_010;

-- hidra_audit_evidence_link.evidence_type_id -> hidra_audit_catalog_entry.id
ALTER TABLE hidra_audit_evidence_link
    ADD CONSTRAINT fk_hra111_audit_011
    FOREIGN KEY (evidence_type_id) REFERENCES hidra_audit_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_evidence_link VALIDATE CONSTRAINT fk_hra111_audit_011;

-- hidra_audit_export_request.purpose_id -> hidra_audit_catalog_entry.id
ALTER TABLE hidra_audit_export_request
    ADD CONSTRAINT fk_hra111_audit_012
    FOREIGN KEY (purpose_id) REFERENCES hidra_audit_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_export_request VALIDATE CONSTRAINT fk_hra111_audit_012;

-- hidra_audit_integrity_seal.seal_type_id -> hidra_audit_catalog_entry.id
ALTER TABLE hidra_audit_integrity_seal
    ADD CONSTRAINT fk_hra111_audit_013
    FOREIGN KEY (seal_type_id) REFERENCES hidra_audit_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_integrity_seal VALIDATE CONSTRAINT fk_hra111_audit_013;

-- hidra_audit_search_projection.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_search_projection
    ADD CONSTRAINT fk_hra111_audit_014
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_search_projection VALIDATE CONSTRAINT fk_hra111_audit_014;

-- hidra_audit_target_reference.audit_event_id -> hidra_audit_event.id
ALTER TABLE hidra_audit_target_reference
    ADD CONSTRAINT fk_hra111_audit_015
    FOREIGN KEY (audit_event_id) REFERENCES hidra_audit_event (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_audit_target_reference VALIDATE CONSTRAINT fk_hra111_audit_015;

-- documents: 16 HRA-111 same-module foreign keys
-- hidra_documents_access_grant.document_id -> hidra_documents_document.id
ALTER TABLE hidra_documents_access_grant
    ADD CONSTRAINT fk_hra111_documents_001
    FOREIGN KEY (document_id) REFERENCES hidra_documents_document (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_access_grant VALIDATE CONSTRAINT fk_hra111_documents_001;

-- hidra_documents_catalog_translation.catalog_entry_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_catalog_translation
    ADD CONSTRAINT fk_hra111_documents_002
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_catalog_translation VALIDATE CONSTRAINT fk_hra111_documents_002;

-- hidra_documents_document_version.document_id -> hidra_documents_document.id
ALTER TABLE hidra_documents_document_version
    ADD CONSTRAINT fk_hra111_documents_003
    FOREIGN KEY (document_id) REFERENCES hidra_documents_document (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_document_version VALIDATE CONSTRAINT fk_hra111_documents_003;

-- hidra_documents_document_version.storage_object_id -> hidra_documents_storage_object.id
ALTER TABLE hidra_documents_document_version
    ADD CONSTRAINT fk_hra111_documents_004
    FOREIGN KEY (storage_object_id) REFERENCES hidra_documents_storage_object (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_document_version VALIDATE CONSTRAINT fk_hra111_documents_004;

-- hidra_documents_document.classification_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_document
    ADD CONSTRAINT fk_hra111_documents_005
    FOREIGN KEY (classification_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_document VALIDATE CONSTRAINT fk_hra111_documents_005;

-- hidra_documents_document.document_type_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_document
    ADD CONSTRAINT fk_hra111_documents_006
    FOREIGN KEY (document_type_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_document VALIDATE CONSTRAINT fk_hra111_documents_006;

-- hidra_documents_external_reference.document_id -> hidra_documents_document.id
ALTER TABLE hidra_documents_external_reference
    ADD CONSTRAINT fk_hra111_documents_007
    FOREIGN KEY (document_id) REFERENCES hidra_documents_document (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_external_reference VALIDATE CONSTRAINT fk_hra111_documents_007;

-- hidra_documents_extraction_record.document_version_id -> hidra_documents_document_version.id
ALTER TABLE hidra_documents_extraction_record
    ADD CONSTRAINT fk_hra111_documents_008
    FOREIGN KEY (document_version_id) REFERENCES hidra_documents_document_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_extraction_record VALIDATE CONSTRAINT fk_hra111_documents_008;

-- hidra_documents_retention_record.document_id -> hidra_documents_document.id
ALTER TABLE hidra_documents_retention_record
    ADD CONSTRAINT fk_hra111_documents_009
    FOREIGN KEY (document_id) REFERENCES hidra_documents_document (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_retention_record VALIDATE CONSTRAINT fk_hra111_documents_009;

-- hidra_documents_retention_record.retention_class_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_retention_record
    ADD CONSTRAINT fk_hra111_documents_010
    FOREIGN KEY (retention_class_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_retention_record VALIDATE CONSTRAINT fk_hra111_documents_010;

-- hidra_documents_retention_record.retention_policy_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_retention_record
    ADD CONSTRAINT fk_hra111_documents_011
    FOREIGN KEY (retention_policy_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_retention_record VALIDATE CONSTRAINT fk_hra111_documents_011;

-- hidra_documents_review_reference.document_id -> hidra_documents_document.id
ALTER TABLE hidra_documents_review_reference
    ADD CONSTRAINT fk_hra111_documents_012
    FOREIGN KEY (document_id) REFERENCES hidra_documents_document (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_review_reference VALIDATE CONSTRAINT fk_hra111_documents_012;

-- hidra_documents_review_reference.review_type_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_review_reference
    ADD CONSTRAINT fk_hra111_documents_013
    FOREIGN KEY (review_type_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_review_reference VALIDATE CONSTRAINT fk_hra111_documents_013;

-- hidra_documents_storage_object.storage_provider_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_storage_object
    ADD CONSTRAINT fk_hra111_documents_014
    FOREIGN KEY (storage_provider_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_storage_object VALIDATE CONSTRAINT fk_hra111_documents_014;

-- hidra_documents_target_link.document_id -> hidra_documents_document.id
ALTER TABLE hidra_documents_target_link
    ADD CONSTRAINT fk_hra111_documents_015
    FOREIGN KEY (document_id) REFERENCES hidra_documents_document (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_target_link VALIDATE CONSTRAINT fk_hra111_documents_015;

-- hidra_documents_target_link.link_role_id -> hidra_documents_catalog_entry.id
ALTER TABLE hidra_documents_target_link
    ADD CONSTRAINT fk_hra111_documents_016
    FOREIGN KEY (link_role_id) REFERENCES hidra_documents_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_documents_target_link VALIDATE CONSTRAINT fk_hra111_documents_016;

-- integration: 32 HRA-111 same-module foreign keys
-- hidra_integration_catalog_translation.catalog_entry_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_catalog_translation
    ADD CONSTRAINT fk_hra111_integration_001
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_catalog_translation VALIDATE CONSTRAINT fk_hra111_integration_001;

-- hidra_integration_connector_instance.connector_type_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_connector_instance
    ADD CONSTRAINT fk_hra111_integration_002
    FOREIGN KEY (connector_type_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_connector_instance VALIDATE CONSTRAINT fk_hra111_integration_002;

-- hidra_integration_connector_instance.endpoint_id -> hidra_integration_external_endpoint.id
ALTER TABLE hidra_integration_connector_instance
    ADD CONSTRAINT fk_hra111_integration_003
    FOREIGN KEY (endpoint_id) REFERENCES hidra_integration_external_endpoint (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_connector_instance VALIDATE CONSTRAINT fk_hra111_integration_003;

-- hidra_integration_connector_instance.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_connector_instance
    ADD CONSTRAINT fk_hra111_integration_004
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_connector_instance VALIDATE CONSTRAINT fk_hra111_integration_004;

-- hidra_integration_data_contract.contract_type_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_data_contract
    ADD CONSTRAINT fk_hra111_integration_005
    FOREIGN KEY (contract_type_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_data_contract VALIDATE CONSTRAINT fk_hra111_integration_005;

-- hidra_integration_data_contract.payload_format_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_data_contract
    ADD CONSTRAINT fk_hra111_integration_006
    FOREIGN KEY (payload_format_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_data_contract VALIDATE CONSTRAINT fk_hra111_integration_006;

-- hidra_integration_dead_letter_record.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_dead_letter_record
    ADD CONSTRAINT fk_hra111_integration_007
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_dead_letter_record VALIDATE CONSTRAINT fk_hra111_integration_007;

-- hidra_integration_exchange_message.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_exchange_message
    ADD CONSTRAINT fk_hra111_integration_008
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_exchange_message VALIDATE CONSTRAINT fk_hra111_integration_008;

-- hidra_integration_exchange_message.message_type_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_exchange_message
    ADD CONSTRAINT fk_hra111_integration_009
    FOREIGN KEY (message_type_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_exchange_message VALIDATE CONSTRAINT fk_hra111_integration_009;

-- hidra_integration_exchange_message.payload_format_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_exchange_message
    ADD CONSTRAINT fk_hra111_integration_010
    FOREIGN KEY (payload_format_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_exchange_message VALIDATE CONSTRAINT fk_hra111_integration_010;

-- hidra_integration_external_endpoint.endpoint_type_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_external_endpoint
    ADD CONSTRAINT fk_hra111_integration_011
    FOREIGN KEY (endpoint_type_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_external_endpoint VALIDATE CONSTRAINT fk_hra111_integration_011;

-- hidra_integration_external_endpoint.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_external_endpoint
    ADD CONSTRAINT fk_hra111_integration_012
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_external_endpoint VALIDATE CONSTRAINT fk_hra111_integration_012;

-- hidra_integration_external_endpoint.protocol_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_external_endpoint
    ADD CONSTRAINT fk_hra111_integration_013
    FOREIGN KEY (protocol_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_external_endpoint VALIDATE CONSTRAINT fk_hra111_integration_013;

-- hidra_integration_external_object_reference.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_external_object_reference
    ADD CONSTRAINT fk_hra111_integration_014
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_external_object_reference VALIDATE CONSTRAINT fk_hra111_integration_014;

-- hidra_integration_external_system.system_type_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_external_system
    ADD CONSTRAINT fk_hra111_integration_015
    FOREIGN KEY (system_type_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_external_system VALIDATE CONSTRAINT fk_hra111_integration_015;

-- hidra_integration_field_mapping.mapping_profile_id -> hidra_integration_mapping_profile.id
ALTER TABLE hidra_integration_field_mapping
    ADD CONSTRAINT fk_hra111_integration_016
    FOREIGN KEY (mapping_profile_id) REFERENCES hidra_integration_mapping_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_field_mapping VALIDATE CONSTRAINT fk_hra111_integration_016;

-- hidra_integration_health_snapshot.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_health_snapshot
    ADD CONSTRAINT fk_hra111_integration_017
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_health_snapshot VALIDATE CONSTRAINT fk_hra111_integration_017;

-- hidra_integration_inbound_record.exchange_message_id -> hidra_integration_exchange_message.id
ALTER TABLE hidra_integration_inbound_record
    ADD CONSTRAINT fk_hra111_integration_018
    FOREIGN KEY (exchange_message_id) REFERENCES hidra_integration_exchange_message (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_inbound_record VALIDATE CONSTRAINT fk_hra111_integration_018;

-- hidra_integration_job_definition.connector_instance_id -> hidra_integration_connector_instance.id
ALTER TABLE hidra_integration_job_definition
    ADD CONSTRAINT fk_hra111_integration_019
    FOREIGN KEY (connector_instance_id) REFERENCES hidra_integration_connector_instance (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_job_definition VALIDATE CONSTRAINT fk_hra111_integration_019;

-- hidra_integration_job_definition.job_type_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_job_definition
    ADD CONSTRAINT fk_hra111_integration_020
    FOREIGN KEY (job_type_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_job_definition VALIDATE CONSTRAINT fk_hra111_integration_020;

-- hidra_integration_job_run_step.job_run_id -> hidra_integration_job_run.id
ALTER TABLE hidra_integration_job_run_step
    ADD CONSTRAINT fk_hra111_integration_021
    FOREIGN KEY (job_run_id) REFERENCES hidra_integration_job_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_job_run_step VALIDATE CONSTRAINT fk_hra111_integration_021;

-- hidra_integration_job_run.job_definition_id -> hidra_integration_job_definition.id
ALTER TABLE hidra_integration_job_run
    ADD CONSTRAINT fk_hra111_integration_022
    FOREIGN KEY (job_definition_id) REFERENCES hidra_integration_job_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_job_run VALIDATE CONSTRAINT fk_hra111_integration_022;

-- hidra_integration_mapping_profile.data_contract_id -> hidra_integration_data_contract.id
ALTER TABLE hidra_integration_mapping_profile
    ADD CONSTRAINT fk_hra111_integration_023
    FOREIGN KEY (data_contract_id) REFERENCES hidra_integration_data_contract (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_mapping_profile VALIDATE CONSTRAINT fk_hra111_integration_023;

-- hidra_integration_mapping_profile.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_mapping_profile
    ADD CONSTRAINT fk_hra111_integration_024
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_mapping_profile VALIDATE CONSTRAINT fk_hra111_integration_024;

-- hidra_integration_outbound_record.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_outbound_record
    ADD CONSTRAINT fk_hra111_integration_025
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_outbound_record VALIDATE CONSTRAINT fk_hra111_integration_025;

-- hidra_integration_reconciliation_issue.reconciliation_run_id -> hidra_integration_reconciliation_run.id
ALTER TABLE hidra_integration_reconciliation_issue
    ADD CONSTRAINT fk_hra111_integration_026
    FOREIGN KEY (reconciliation_run_id) REFERENCES hidra_integration_reconciliation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_reconciliation_issue VALIDATE CONSTRAINT fk_hra111_integration_026;

-- hidra_integration_reconciliation_run.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_reconciliation_run
    ADD CONSTRAINT fk_hra111_integration_027
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_reconciliation_run VALIDATE CONSTRAINT fk_hra111_integration_027;

-- hidra_integration_schema_version.data_contract_id -> hidra_integration_data_contract.id
ALTER TABLE hidra_integration_schema_version
    ADD CONSTRAINT fk_hra111_integration_028
    FOREIGN KEY (data_contract_id) REFERENCES hidra_integration_data_contract (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_schema_version VALIDATE CONSTRAINT fk_hra111_integration_028;

-- hidra_integration_sync_cursor.external_system_id -> hidra_integration_external_system.id
ALTER TABLE hidra_integration_sync_cursor
    ADD CONSTRAINT fk_hra111_integration_029
    FOREIGN KEY (external_system_id) REFERENCES hidra_integration_external_system (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_sync_cursor VALIDATE CONSTRAINT fk_hra111_integration_029;

-- hidra_integration_sync_cursor.job_definition_id -> hidra_integration_job_definition.id
ALTER TABLE hidra_integration_sync_cursor
    ADD CONSTRAINT fk_hra111_integration_030
    FOREIGN KEY (job_definition_id) REFERENCES hidra_integration_job_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_sync_cursor VALIDATE CONSTRAINT fk_hra111_integration_030;

-- hidra_integration_transformation_rule.mapping_profile_id -> hidra_integration_mapping_profile.id
ALTER TABLE hidra_integration_transformation_rule
    ADD CONSTRAINT fk_hra111_integration_031
    FOREIGN KEY (mapping_profile_id) REFERENCES hidra_integration_mapping_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_transformation_rule VALIDATE CONSTRAINT fk_hra111_integration_031;

-- hidra_integration_transformation_rule.rule_type_id -> hidra_integration_catalog_entry.id
ALTER TABLE hidra_integration_transformation_rule
    ADD CONSTRAINT fk_hra111_integration_032
    FOREIGN KEY (rule_type_id) REFERENCES hidra_integration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_integration_transformation_rule VALIDATE CONSTRAINT fk_hra111_integration_032;

-- configuration: 11 HRA-111 same-module foreign keys
-- hidra_configuration_catalog_translation.catalog_entry_id -> hidra_configuration_catalog_entry.id
ALTER TABLE hidra_configuration_catalog_translation
    ADD CONSTRAINT fk_hra111_configuration_001
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_configuration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_catalog_translation VALIDATE CONSTRAINT fk_hra111_configuration_001;

-- hidra_configuration_definition_version.definition_id -> hidra_configuration_definition.id
ALTER TABLE hidra_configuration_definition_version
    ADD CONSTRAINT fk_hra111_configuration_002
    FOREIGN KEY (definition_id) REFERENCES hidra_configuration_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_definition_version VALIDATE CONSTRAINT fk_hra111_configuration_002;

-- hidra_configuration_definition.namespace_id -> hidra_configuration_namespace.id
ALTER TABLE hidra_configuration_definition
    ADD CONSTRAINT fk_hra111_configuration_003
    FOREIGN KEY (namespace_id) REFERENCES hidra_configuration_namespace (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_definition VALIDATE CONSTRAINT fk_hra111_configuration_003;

-- hidra_configuration_feature_flag_rule.feature_flag_id -> hidra_configuration_feature_flag.id
ALTER TABLE hidra_configuration_feature_flag_rule
    ADD CONSTRAINT fk_hra111_configuration_004
    FOREIGN KEY (feature_flag_id) REFERENCES hidra_configuration_feature_flag (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_feature_flag_rule VALIDATE CONSTRAINT fk_hra111_configuration_004;

-- hidra_configuration_profile_entry.definition_id -> hidra_configuration_definition.id
ALTER TABLE hidra_configuration_profile_entry
    ADD CONSTRAINT fk_hra111_configuration_005
    FOREIGN KEY (definition_id) REFERENCES hidra_configuration_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_profile_entry VALIDATE CONSTRAINT fk_hra111_configuration_005;

-- hidra_configuration_profile_entry.profile_id -> hidra_configuration_profile.id
ALTER TABLE hidra_configuration_profile_entry
    ADD CONSTRAINT fk_hra111_configuration_006
    FOREIGN KEY (profile_id) REFERENCES hidra_configuration_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_profile_entry VALIDATE CONSTRAINT fk_hra111_configuration_006;

-- hidra_configuration_scoped_override.configuration_value_id -> hidra_configuration_value.id
ALTER TABLE hidra_configuration_scoped_override
    ADD CONSTRAINT fk_hra111_configuration_007
    FOREIGN KEY (configuration_value_id) REFERENCES hidra_configuration_value (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_scoped_override VALIDATE CONSTRAINT fk_hra111_configuration_007;

-- hidra_configuration_scoped_override.definition_id -> hidra_configuration_definition.id
ALTER TABLE hidra_configuration_scoped_override
    ADD CONSTRAINT fk_hra111_configuration_008
    FOREIGN KEY (definition_id) REFERENCES hidra_configuration_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_scoped_override VALIDATE CONSTRAINT fk_hra111_configuration_008;

-- hidra_configuration_validation_rule.definition_id -> hidra_configuration_definition.id
ALTER TABLE hidra_configuration_validation_rule
    ADD CONSTRAINT fk_hra111_configuration_009
    FOREIGN KEY (definition_id) REFERENCES hidra_configuration_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_validation_rule VALIDATE CONSTRAINT fk_hra111_configuration_009;

-- hidra_configuration_validation_rule.rule_type_id -> hidra_configuration_catalog_entry.id
ALTER TABLE hidra_configuration_validation_rule
    ADD CONSTRAINT fk_hra111_configuration_010
    FOREIGN KEY (rule_type_id) REFERENCES hidra_configuration_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_validation_rule VALIDATE CONSTRAINT fk_hra111_configuration_010;

-- hidra_configuration_value.definition_id -> hidra_configuration_definition.id
ALTER TABLE hidra_configuration_value
    ADD CONSTRAINT fk_hra111_configuration_011
    FOREIGN KEY (definition_id) REFERENCES hidra_configuration_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_configuration_value VALIDATE CONSTRAINT fk_hra111_configuration_011;

-- notification: 23 HRA-111 same-module foreign keys
-- hidra_notification_acknowledgement.message_id -> hidra_notification_message.id
ALTER TABLE hidra_notification_acknowledgement
    ADD CONSTRAINT fk_hra111_notification_001
    FOREIGN KEY (message_id) REFERENCES hidra_notification_message (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_acknowledgement VALIDATE CONSTRAINT fk_hra111_notification_001;

-- hidra_notification_acknowledgement.recipient_id -> hidra_notification_request_recipient.id
ALTER TABLE hidra_notification_acknowledgement
    ADD CONSTRAINT fk_hra111_notification_002
    FOREIGN KEY (recipient_id) REFERENCES hidra_notification_request_recipient (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_acknowledgement VALIDATE CONSTRAINT fk_hra111_notification_002;

-- hidra_notification_catalog_translation.catalog_entry_id -> hidra_notification_catalog_entry.id
ALTER TABLE hidra_notification_catalog_translation
    ADD CONSTRAINT fk_hra111_notification_003
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_catalog_translation VALIDATE CONSTRAINT fk_hra111_notification_003;

-- hidra_notification_contact_point.channel_id -> hidra_notification_channel.id
ALTER TABLE hidra_notification_contact_point
    ADD CONSTRAINT fk_hra111_notification_004
    FOREIGN KEY (channel_id) REFERENCES hidra_notification_channel (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_contact_point VALIDATE CONSTRAINT fk_hra111_notification_004;

-- hidra_notification_contact_point.recipient_profile_id -> hidra_notification_recipient_profile.id
ALTER TABLE hidra_notification_contact_point
    ADD CONSTRAINT fk_hra111_notification_005
    FOREIGN KEY (recipient_profile_id) REFERENCES hidra_notification_recipient_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_contact_point VALIDATE CONSTRAINT fk_hra111_notification_005;

-- hidra_notification_delivery_attempt.channel_id -> hidra_notification_channel.id
ALTER TABLE hidra_notification_delivery_attempt
    ADD CONSTRAINT fk_hra111_notification_006
    FOREIGN KEY (channel_id) REFERENCES hidra_notification_channel (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_delivery_attempt VALIDATE CONSTRAINT fk_hra111_notification_006;

-- hidra_notification_delivery_attempt.message_id -> hidra_notification_message.id
ALTER TABLE hidra_notification_delivery_attempt
    ADD CONSTRAINT fk_hra111_notification_007
    FOREIGN KEY (message_id) REFERENCES hidra_notification_message (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_delivery_attempt VALIDATE CONSTRAINT fk_hra111_notification_007;

-- hidra_notification_message_variable.message_id -> hidra_notification_message.id
ALTER TABLE hidra_notification_message_variable
    ADD CONSTRAINT fk_hra111_notification_008
    FOREIGN KEY (message_id) REFERENCES hidra_notification_message (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_message_variable VALIDATE CONSTRAINT fk_hra111_notification_008;

-- hidra_notification_message.channel_id -> hidra_notification_channel.id
ALTER TABLE hidra_notification_message
    ADD CONSTRAINT fk_hra111_notification_009
    FOREIGN KEY (channel_id) REFERENCES hidra_notification_channel (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_message VALIDATE CONSTRAINT fk_hra111_notification_009;

-- hidra_notification_message.recipient_id -> hidra_notification_request_recipient.id
ALTER TABLE hidra_notification_message
    ADD CONSTRAINT fk_hra111_notification_010
    FOREIGN KEY (recipient_id) REFERENCES hidra_notification_request_recipient (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_message VALIDATE CONSTRAINT fk_hra111_notification_010;

-- hidra_notification_message.request_id -> hidra_notification_request.id
ALTER TABLE hidra_notification_message
    ADD CONSTRAINT fk_hra111_notification_011
    FOREIGN KEY (request_id) REFERENCES hidra_notification_request (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_message VALIDATE CONSTRAINT fk_hra111_notification_011;

-- hidra_notification_policy.category_id -> hidra_notification_catalog_entry.id
ALTER TABLE hidra_notification_policy
    ADD CONSTRAINT fk_hra111_notification_012
    FOREIGN KEY (category_id) REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_policy VALIDATE CONSTRAINT fk_hra111_notification_012;

-- hidra_notification_preference.category_id -> hidra_notification_catalog_entry.id
ALTER TABLE hidra_notification_preference
    ADD CONSTRAINT fk_hra111_notification_013
    FOREIGN KEY (category_id) REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_preference VALIDATE CONSTRAINT fk_hra111_notification_013;

-- hidra_notification_preference.channel_id -> hidra_notification_channel.id
ALTER TABLE hidra_notification_preference
    ADD CONSTRAINT fk_hra111_notification_014
    FOREIGN KEY (channel_id) REFERENCES hidra_notification_channel (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_preference VALIDATE CONSTRAINT fk_hra111_notification_014;

-- hidra_notification_preference.recipient_profile_id -> hidra_notification_recipient_profile.id
ALTER TABLE hidra_notification_preference
    ADD CONSTRAINT fk_hra111_notification_015
    FOREIGN KEY (recipient_profile_id) REFERENCES hidra_notification_recipient_profile (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_preference VALIDATE CONSTRAINT fk_hra111_notification_015;

-- hidra_notification_recipient_group_member.group_id -> hidra_notification_recipient_group.id
ALTER TABLE hidra_notification_recipient_group_member
    ADD CONSTRAINT fk_hra111_notification_016
    FOREIGN KEY (group_id) REFERENCES hidra_notification_recipient_group (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_recipient_group_member VALIDATE CONSTRAINT fk_hra111_notification_016;

-- hidra_notification_recipient_group.group_type_id -> hidra_notification_catalog_entry.id
ALTER TABLE hidra_notification_recipient_group
    ADD CONSTRAINT fk_hra111_notification_017
    FOREIGN KEY (group_type_id) REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_recipient_group VALIDATE CONSTRAINT fk_hra111_notification_017;

-- hidra_notification_request_recipient.request_id -> hidra_notification_request.id
ALTER TABLE hidra_notification_request_recipient
    ADD CONSTRAINT fk_hra111_notification_018
    FOREIGN KEY (request_id) REFERENCES hidra_notification_request (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_request_recipient VALIDATE CONSTRAINT fk_hra111_notification_018;

-- hidra_notification_request.category_id -> hidra_notification_catalog_entry.id
ALTER TABLE hidra_notification_request
    ADD CONSTRAINT fk_hra111_notification_019
    FOREIGN KEY (category_id) REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_request VALIDATE CONSTRAINT fk_hra111_notification_019;

-- hidra_notification_suppression_rule.reason_id -> hidra_notification_catalog_entry.id
ALTER TABLE hidra_notification_suppression_rule
    ADD CONSTRAINT fk_hra111_notification_020
    FOREIGN KEY (reason_id) REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_suppression_rule VALIDATE CONSTRAINT fk_hra111_notification_020;

-- hidra_notification_template_translation.template_version_id -> hidra_notification_template_version.id
ALTER TABLE hidra_notification_template_translation
    ADD CONSTRAINT fk_hra111_notification_021
    FOREIGN KEY (template_version_id) REFERENCES hidra_notification_template_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_template_translation VALIDATE CONSTRAINT fk_hra111_notification_021;

-- hidra_notification_template_version.template_id -> hidra_notification_template.id
ALTER TABLE hidra_notification_template_version
    ADD CONSTRAINT fk_hra111_notification_022
    FOREIGN KEY (template_id) REFERENCES hidra_notification_template (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_template_version VALIDATE CONSTRAINT fk_hra111_notification_022;

-- hidra_notification_template.template_type_id -> hidra_notification_catalog_entry.id
ALTER TABLE hidra_notification_template
    ADD CONSTRAINT fk_hra111_notification_023
    FOREIGN KEY (template_type_id) REFERENCES hidra_notification_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_notification_template VALIDATE CONSTRAINT fk_hra111_notification_023;

-- simulation: 41 HRA-111 same-module foreign keys
-- hidra_simulation_candidate_change.candidate_id -> hidra_simulation_optimization_candidate.id
ALTER TABLE hidra_simulation_candidate_change
    ADD CONSTRAINT fk_hra111_simulation_001
    FOREIGN KEY (candidate_id) REFERENCES hidra_simulation_optimization_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_candidate_change VALIDATE CONSTRAINT fk_hra111_simulation_001;

-- hidra_simulation_candidate_change.change_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_candidate_change
    ADD CONSTRAINT fk_hra111_simulation_002
    FOREIGN KEY (change_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_candidate_change VALIDATE CONSTRAINT fk_hra111_simulation_002;

-- hidra_simulation_candidate_operating_condition.candidate_id -> hidra_simulation_optimization_candidate.id
ALTER TABLE hidra_simulation_candidate_operating_condition
    ADD CONSTRAINT fk_hra111_simulation_003
    FOREIGN KEY (candidate_id) REFERENCES hidra_simulation_optimization_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_candidate_operating_condition VALIDATE CONSTRAINT fk_hra111_simulation_003;

-- hidra_simulation_candidate_score.candidate_id -> hidra_simulation_optimization_candidate.id
ALTER TABLE hidra_simulation_candidate_score
    ADD CONSTRAINT fk_hra111_simulation_004
    FOREIGN KEY (candidate_id) REFERENCES hidra_simulation_optimization_candidate (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_candidate_score VALIDATE CONSTRAINT fk_hra111_simulation_004;

-- hidra_simulation_catalog_translation.catalog_entry_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_catalog_translation
    ADD CONSTRAINT fk_hra111_simulation_005
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_catalog_translation VALIDATE CONSTRAINT fk_hra111_simulation_005;

-- hidra_simulation_constraint_evaluation.constraint_id -> hidra_simulation_constraint.id
ALTER TABLE hidra_simulation_constraint_evaluation
    ADD CONSTRAINT fk_hra111_simulation_006
    FOREIGN KEY (constraint_id) REFERENCES hidra_simulation_constraint (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_constraint_evaluation VALIDATE CONSTRAINT fk_hra111_simulation_006;

-- hidra_simulation_constraint_evaluation.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_constraint_evaluation
    ADD CONSTRAINT fk_hra111_simulation_007
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_constraint_evaluation VALIDATE CONSTRAINT fk_hra111_simulation_007;

-- hidra_simulation_constraint_evaluation.severity_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_constraint_evaluation
    ADD CONSTRAINT fk_hra111_simulation_008
    FOREIGN KEY (severity_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_constraint_evaluation VALIDATE CONSTRAINT fk_hra111_simulation_008;

-- hidra_simulation_constraint.constraint_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_constraint
    ADD CONSTRAINT fk_hra111_simulation_009
    FOREIGN KEY (constraint_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_constraint VALIDATE CONSTRAINT fk_hra111_simulation_009;

-- hidra_simulation_constraint.scenario_id -> hidra_simulation_scenario.id
ALTER TABLE hidra_simulation_constraint
    ADD CONSTRAINT fk_hra111_simulation_010
    FOREIGN KEY (scenario_id) REFERENCES hidra_simulation_scenario (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_constraint VALIDATE CONSTRAINT fk_hra111_simulation_010;

-- hidra_simulation_constraint.severity_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_constraint
    ADD CONSTRAINT fk_hra111_simulation_011
    FOREIGN KEY (severity_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_constraint VALIDATE CONSTRAINT fk_hra111_simulation_011;

-- hidra_simulation_input_dataset.dataset_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_input_dataset
    ADD CONSTRAINT fk_hra111_simulation_012
    FOREIGN KEY (dataset_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_input_dataset VALIDATE CONSTRAINT fk_hra111_simulation_012;

-- hidra_simulation_input_snapshot.scenario_id -> hidra_simulation_scenario.id
ALTER TABLE hidra_simulation_input_snapshot
    ADD CONSTRAINT fk_hra111_simulation_013
    FOREIGN KEY (scenario_id) REFERENCES hidra_simulation_scenario (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_input_snapshot VALIDATE CONSTRAINT fk_hra111_simulation_013;

-- hidra_simulation_model_version.model_id -> hidra_simulation_model.id
ALTER TABLE hidra_simulation_model_version
    ADD CONSTRAINT fk_hra111_simulation_014
    FOREIGN KEY (model_id) REFERENCES hidra_simulation_model (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_model_version VALIDATE CONSTRAINT fk_hra111_simulation_014;

-- hidra_simulation_model_version.solver_profile_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_model_version
    ADD CONSTRAINT fk_hra111_simulation_015
    FOREIGN KEY (solver_profile_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_model_version VALIDATE CONSTRAINT fk_hra111_simulation_015;

-- hidra_simulation_model.model_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_model
    ADD CONSTRAINT fk_hra111_simulation_016
    FOREIGN KEY (model_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_model VALIDATE CONSTRAINT fk_hra111_simulation_016;

-- hidra_simulation_objective.objective_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_objective
    ADD CONSTRAINT fk_hra111_simulation_017
    FOREIGN KEY (objective_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_objective VALIDATE CONSTRAINT fk_hra111_simulation_017;

-- hidra_simulation_objective.scenario_id -> hidra_simulation_scenario.id
ALTER TABLE hidra_simulation_objective
    ADD CONSTRAINT fk_hra111_simulation_018
    FOREIGN KEY (scenario_id) REFERENCES hidra_simulation_scenario (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_objective VALIDATE CONSTRAINT fk_hra111_simulation_018;

-- hidra_simulation_optimization_candidate.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_optimization_candidate
    ADD CONSTRAINT fk_hra111_simulation_019
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_optimization_candidate VALIDATE CONSTRAINT fk_hra111_simulation_019;

-- hidra_simulation_recommendation.recommendation_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_recommendation
    ADD CONSTRAINT fk_hra111_simulation_020
    FOREIGN KEY (recommendation_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_recommendation VALIDATE CONSTRAINT fk_hra111_simulation_020;

-- hidra_simulation_recommendation.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_recommendation
    ADD CONSTRAINT fk_hra111_simulation_021
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_recommendation VALIDATE CONSTRAINT fk_hra111_simulation_021;

-- hidra_simulation_result_series_reference.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_result_series_reference
    ADD CONSTRAINT fk_hra111_simulation_022
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_result_series_reference VALIDATE CONSTRAINT fk_hra111_simulation_022;

-- hidra_simulation_result_series_reference.series_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_result_series_reference
    ADD CONSTRAINT fk_hra111_simulation_023
    FOREIGN KEY (series_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_result_series_reference VALIDATE CONSTRAINT fk_hra111_simulation_023;

-- hidra_simulation_result_summary.result_status_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_result_summary
    ADD CONSTRAINT fk_hra111_simulation_024
    FOREIGN KEY (result_status_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_result_summary VALIDATE CONSTRAINT fk_hra111_simulation_024;

-- hidra_simulation_result_summary.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_result_summary
    ADD CONSTRAINT fk_hra111_simulation_025
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_result_summary VALIDATE CONSTRAINT fk_hra111_simulation_025;

-- hidra_simulation_result_value.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_result_value
    ADD CONSTRAINT fk_hra111_simulation_026
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_result_value VALIDATE CONSTRAINT fk_hra111_simulation_026;

-- hidra_simulation_run_step.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_run_step
    ADD CONSTRAINT fk_hra111_simulation_027
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_run_step VALIDATE CONSTRAINT fk_hra111_simulation_027;

-- hidra_simulation_run.model_version_id -> hidra_simulation_model_version.id
ALTER TABLE hidra_simulation_run
    ADD CONSTRAINT fk_hra111_simulation_028
    FOREIGN KEY (model_version_id) REFERENCES hidra_simulation_model_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_run VALIDATE CONSTRAINT fk_hra111_simulation_028;

-- hidra_simulation_run.run_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_run
    ADD CONSTRAINT fk_hra111_simulation_029
    FOREIGN KEY (run_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_run VALIDATE CONSTRAINT fk_hra111_simulation_029;

-- hidra_simulation_run.scenario_id -> hidra_simulation_scenario.id
ALTER TABLE hidra_simulation_run
    ADD CONSTRAINT fk_hra111_simulation_030
    FOREIGN KEY (scenario_id) REFERENCES hidra_simulation_scenario (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_run VALIDATE CONSTRAINT fk_hra111_simulation_030;

-- hidra_simulation_run.solver_profile_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_run
    ADD CONSTRAINT fk_hra111_simulation_031
    FOREIGN KEY (solver_profile_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_run VALIDATE CONSTRAINT fk_hra111_simulation_031;

-- hidra_simulation_scenario_assumption.assumption_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_scenario_assumption
    ADD CONSTRAINT fk_hra111_simulation_032
    FOREIGN KEY (assumption_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_scenario_assumption VALIDATE CONSTRAINT fk_hra111_simulation_032;

-- hidra_simulation_scenario_assumption.scenario_id -> hidra_simulation_scenario.id
ALTER TABLE hidra_simulation_scenario_assumption
    ADD CONSTRAINT fk_hra111_simulation_033
    FOREIGN KEY (scenario_id) REFERENCES hidra_simulation_scenario (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_scenario_assumption VALIDATE CONSTRAINT fk_hra111_simulation_033;

-- hidra_simulation_scenario.model_id -> hidra_simulation_model.id
ALTER TABLE hidra_simulation_scenario
    ADD CONSTRAINT fk_hra111_simulation_034
    FOREIGN KEY (model_id) REFERENCES hidra_simulation_model (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_scenario VALIDATE CONSTRAINT fk_hra111_simulation_034;

-- hidra_simulation_scenario.model_version_id -> hidra_simulation_model_version.id
ALTER TABLE hidra_simulation_scenario
    ADD CONSTRAINT fk_hra111_simulation_035
    FOREIGN KEY (model_version_id) REFERENCES hidra_simulation_model_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_scenario VALIDATE CONSTRAINT fk_hra111_simulation_035;

-- hidra_simulation_scenario.scenario_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_scenario
    ADD CONSTRAINT fk_hra111_simulation_036
    FOREIGN KEY (scenario_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_scenario VALIDATE CONSTRAINT fk_hra111_simulation_036;

-- hidra_simulation_sensitivity_analysis.base_run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_sensitivity_analysis
    ADD CONSTRAINT fk_hra111_simulation_037
    FOREIGN KEY (base_run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_sensitivity_analysis VALIDATE CONSTRAINT fk_hra111_simulation_037;

-- hidra_simulation_sensitivity_analysis.scenario_id -> hidra_simulation_scenario.id
ALTER TABLE hidra_simulation_sensitivity_analysis
    ADD CONSTRAINT fk_hra111_simulation_038
    FOREIGN KEY (scenario_id) REFERENCES hidra_simulation_scenario (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_sensitivity_analysis VALIDATE CONSTRAINT fk_hra111_simulation_038;

-- hidra_simulation_solver_trace.run_id -> hidra_simulation_run.id
ALTER TABLE hidra_simulation_solver_trace
    ADD CONSTRAINT fk_hra111_simulation_039
    FOREIGN KEY (run_id) REFERENCES hidra_simulation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_solver_trace VALIDATE CONSTRAINT fk_hra111_simulation_039;

-- hidra_simulation_validation_finding.finding_type_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_validation_finding
    ADD CONSTRAINT fk_hra111_simulation_040
    FOREIGN KEY (finding_type_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_validation_finding VALIDATE CONSTRAINT fk_hra111_simulation_040;

-- hidra_simulation_validation_finding.severity_id -> hidra_simulation_catalog_entry.id
ALTER TABLE hidra_simulation_validation_finding
    ADD CONSTRAINT fk_hra111_simulation_041
    FOREIGN KEY (severity_id) REFERENCES hidra_simulation_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_simulation_validation_finding VALIDATE CONSTRAINT fk_hra111_simulation_041;

-- analytics: 32 HRA-111 same-module foreign keys
-- hidra_analytics_catalog_translation.catalog_entry_id -> hidra_analytics_catalog_entry.id
ALTER TABLE hidra_analytics_catalog_translation
    ADD CONSTRAINT fk_hra111_analytics_001
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_analytics_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_catalog_translation VALIDATE CONSTRAINT fk_hra111_analytics_001;

-- hidra_analytics_dataset_lineage.dataset_version_id -> hidra_analytics_dataset_version.id
ALTER TABLE hidra_analytics_dataset_lineage
    ADD CONSTRAINT fk_hra111_analytics_002
    FOREIGN KEY (dataset_version_id) REFERENCES hidra_analytics_dataset_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_dataset_lineage VALIDATE CONSTRAINT fk_hra111_analytics_002;

-- hidra_analytics_dataset_version.dataset_id -> hidra_analytics_dataset.id
ALTER TABLE hidra_analytics_dataset_version
    ADD CONSTRAINT fk_hra111_analytics_003
    FOREIGN KEY (dataset_id) REFERENCES hidra_analytics_dataset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_dataset_version VALIDATE CONSTRAINT fk_hra111_analytics_003;

-- hidra_analytics_dataset.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_dataset
    ADD CONSTRAINT fk_hra111_analytics_004
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_dataset VALIDATE CONSTRAINT fk_hra111_analytics_004;

-- hidra_analytics_feature_set.source_dataset_id -> hidra_analytics_dataset.id
ALTER TABLE hidra_analytics_feature_set
    ADD CONSTRAINT fk_hra111_analytics_005
    FOREIGN KEY (source_dataset_id) REFERENCES hidra_analytics_dataset (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_feature_set VALIDATE CONSTRAINT fk_hra111_analytics_005;

-- hidra_analytics_feature_set.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_feature_set
    ADD CONSTRAINT fk_hra111_analytics_006
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_feature_set VALIDATE CONSTRAINT fk_hra111_analytics_006;

-- hidra_analytics_feature_value.dataset_version_id -> hidra_analytics_dataset_version.id
ALTER TABLE hidra_analytics_feature_value
    ADD CONSTRAINT fk_hra111_analytics_007
    FOREIGN KEY (dataset_version_id) REFERENCES hidra_analytics_dataset_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_feature_value VALIDATE CONSTRAINT fk_hra111_analytics_007;

-- hidra_analytics_feature_value.feature_set_id -> hidra_analytics_feature_set.id
ALTER TABLE hidra_analytics_feature_value
    ADD CONSTRAINT fk_hra111_analytics_008
    FOREIGN KEY (feature_set_id) REFERENCES hidra_analytics_feature_set (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_feature_value VALIDATE CONSTRAINT fk_hra111_analytics_008;

-- hidra_analytics_insight_evidence.analytics_insight_id -> hidra_analytics_insight.id
ALTER TABLE hidra_analytics_insight_evidence
    ADD CONSTRAINT fk_hra111_analytics_009
    FOREIGN KEY (analytics_insight_id) REFERENCES hidra_analytics_insight (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_insight_evidence VALIDATE CONSTRAINT fk_hra111_analytics_009;

-- hidra_analytics_insight.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_insight
    ADD CONSTRAINT fk_hra111_analytics_010
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_insight VALIDATE CONSTRAINT fk_hra111_analytics_010;

-- hidra_analytics_kpi_band.kpi_definition_id -> hidra_analytics_kpi_definition.id
ALTER TABLE hidra_analytics_kpi_band
    ADD CONSTRAINT fk_hra111_analytics_011
    FOREIGN KEY (kpi_definition_id) REFERENCES hidra_analytics_kpi_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_kpi_band VALIDATE CONSTRAINT fk_hra111_analytics_011;

-- hidra_analytics_kpi_band.severity_id -> hidra_analytics_catalog_entry.id
ALTER TABLE hidra_analytics_kpi_band
    ADD CONSTRAINT fk_hra111_analytics_012
    FOREIGN KEY (severity_id) REFERENCES hidra_analytics_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_kpi_band VALIDATE CONSTRAINT fk_hra111_analytics_012;

-- hidra_analytics_kpi_definition.kpi_category_id -> hidra_analytics_catalog_entry.id
ALTER TABLE hidra_analytics_kpi_definition
    ADD CONSTRAINT fk_hra111_analytics_013
    FOREIGN KEY (kpi_category_id) REFERENCES hidra_analytics_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_kpi_definition VALIDATE CONSTRAINT fk_hra111_analytics_013;

-- hidra_analytics_kpi_definition.primary_metric_definition_id -> hidra_analytics_metric_definition.id
ALTER TABLE hidra_analytics_kpi_definition
    ADD CONSTRAINT fk_hra111_analytics_014
    FOREIGN KEY (primary_metric_definition_id) REFERENCES hidra_analytics_metric_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_kpi_definition VALIDATE CONSTRAINT fk_hra111_analytics_014;

-- hidra_analytics_kpi_definition.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_kpi_definition
    ADD CONSTRAINT fk_hra111_analytics_015
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_kpi_definition VALIDATE CONSTRAINT fk_hra111_analytics_015;

-- hidra_analytics_kpi_evaluation.kpi_definition_id -> hidra_analytics_kpi_definition.id
ALTER TABLE hidra_analytics_kpi_evaluation
    ADD CONSTRAINT fk_hra111_analytics_016
    FOREIGN KEY (kpi_definition_id) REFERENCES hidra_analytics_kpi_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_kpi_evaluation VALIDATE CONSTRAINT fk_hra111_analytics_016;

-- hidra_analytics_metric_definition_version.metric_definition_id -> hidra_analytics_metric_definition.id
ALTER TABLE hidra_analytics_metric_definition_version
    ADD CONSTRAINT fk_hra111_analytics_017
    FOREIGN KEY (metric_definition_id) REFERENCES hidra_analytics_metric_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_metric_definition_version VALIDATE CONSTRAINT fk_hra111_analytics_017;

-- hidra_analytics_metric_definition.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_metric_definition
    ADD CONSTRAINT fk_hra111_analytics_018
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_metric_definition VALIDATE CONSTRAINT fk_hra111_analytics_018;

-- hidra_analytics_metric_definition.unit_id -> hidra_analytics_catalog_entry.id
ALTER TABLE hidra_analytics_metric_definition
    ADD CONSTRAINT fk_hra111_analytics_019
    FOREIGN KEY (unit_id) REFERENCES hidra_analytics_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_metric_definition VALIDATE CONSTRAINT fk_hra111_analytics_019;

-- hidra_analytics_metric_evaluation_run.metric_definition_version_id -> hidra_analytics_metric_definition_version.id
ALTER TABLE hidra_analytics_metric_evaluation_run
    ADD CONSTRAINT fk_hra111_analytics_020
    FOREIGN KEY (metric_definition_version_id) REFERENCES hidra_analytics_metric_definition_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_metric_evaluation_run VALIDATE CONSTRAINT fk_hra111_analytics_020;

-- hidra_analytics_metric_value.metric_definition_id -> hidra_analytics_metric_definition.id
ALTER TABLE hidra_analytics_metric_value
    ADD CONSTRAINT fk_hra111_analytics_021
    FOREIGN KEY (metric_definition_id) REFERENCES hidra_analytics_metric_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_metric_value VALIDATE CONSTRAINT fk_hra111_analytics_021;

-- hidra_analytics_metric_value.metric_definition_version_id -> hidra_analytics_metric_definition_version.id
ALTER TABLE hidra_analytics_metric_value
    ADD CONSTRAINT fk_hra111_analytics_022
    FOREIGN KEY (metric_definition_version_id) REFERENCES hidra_analytics_metric_definition_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_metric_value VALIDATE CONSTRAINT fk_hra111_analytics_022;

-- hidra_analytics_metric_value.metric_evaluation_run_id -> hidra_analytics_metric_evaluation_run.id
ALTER TABLE hidra_analytics_metric_value
    ADD CONSTRAINT fk_hra111_analytics_023
    FOREIGN KEY (metric_evaluation_run_id) REFERENCES hidra_analytics_metric_evaluation_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_metric_value VALIDATE CONSTRAINT fk_hra111_analytics_023;

-- hidra_analytics_model_run.analytics_model_version_id -> hidra_analytics_model_version.id
ALTER TABLE hidra_analytics_model_run
    ADD CONSTRAINT fk_hra111_analytics_024
    FOREIGN KEY (analytics_model_version_id) REFERENCES hidra_analytics_model_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_model_run VALIDATE CONSTRAINT fk_hra111_analytics_024;

-- hidra_analytics_model_version.analytics_model_id -> hidra_analytics_model.id
ALTER TABLE hidra_analytics_model_version
    ADD CONSTRAINT fk_hra111_analytics_025
    FOREIGN KEY (analytics_model_id) REFERENCES hidra_analytics_model (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_model_version VALIDATE CONSTRAINT fk_hra111_analytics_025;

-- hidra_analytics_model.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_model
    ADD CONSTRAINT fk_hra111_analytics_026
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_model VALIDATE CONSTRAINT fk_hra111_analytics_026;

-- hidra_analytics_projection_definition.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_projection_definition
    ADD CONSTRAINT fk_hra111_analytics_027
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_projection_definition VALIDATE CONSTRAINT fk_hra111_analytics_027;

-- hidra_analytics_projection_run.projection_definition_id -> hidra_analytics_projection_definition.id
ALTER TABLE hidra_analytics_projection_run
    ADD CONSTRAINT fk_hra111_analytics_028
    FOREIGN KEY (projection_definition_id) REFERENCES hidra_analytics_projection_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_projection_run VALIDATE CONSTRAINT fk_hra111_analytics_028;

-- hidra_analytics_projection_snapshot.projection_definition_id -> hidra_analytics_projection_definition.id
ALTER TABLE hidra_analytics_projection_snapshot
    ADD CONSTRAINT fk_hra111_analytics_029
    FOREIGN KEY (projection_definition_id) REFERENCES hidra_analytics_projection_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_projection_snapshot VALIDATE CONSTRAINT fk_hra111_analytics_029;

-- hidra_analytics_projection_snapshot.projection_run_id -> hidra_analytics_projection_run.id
ALTER TABLE hidra_analytics_projection_snapshot
    ADD CONSTRAINT fk_hra111_analytics_030
    FOREIGN KEY (projection_run_id) REFERENCES hidra_analytics_projection_run (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_projection_snapshot VALIDATE CONSTRAINT fk_hra111_analytics_030;

-- hidra_analytics_trend_analysis.subject_area_id -> hidra_analytics_subject_area.id
ALTER TABLE hidra_analytics_trend_analysis
    ADD CONSTRAINT fk_hra111_analytics_031
    FOREIGN KEY (subject_area_id) REFERENCES hidra_analytics_subject_area (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_trend_analysis VALIDATE CONSTRAINT fk_hra111_analytics_031;

-- hidra_analytics_trend_point.trend_analysis_id -> hidra_analytics_trend_analysis.id
ALTER TABLE hidra_analytics_trend_point
    ADD CONSTRAINT fk_hra111_analytics_032
    FOREIGN KEY (trend_analysis_id) REFERENCES hidra_analytics_trend_analysis (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_analytics_trend_point VALIDATE CONSTRAINT fk_hra111_analytics_032;

-- reporting: 27 HRA-111 same-module foreign keys
-- hidra_reporting_access_policy.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_access_policy
    ADD CONSTRAINT fk_hra111_reporting_001
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_access_policy VALIDATE CONSTRAINT fk_hra111_reporting_001;

-- hidra_reporting_catalog_translation.catalog_entry_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_catalog_translation
    ADD CONSTRAINT fk_hra111_reporting_002
    FOREIGN KEY (catalog_entry_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_catalog_translation VALIDATE CONSTRAINT fk_hra111_reporting_002;

-- hidra_reporting_chart_result.report_section_result_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_chart_result
    ADD CONSTRAINT fk_hra111_reporting_003
    FOREIGN KEY (report_section_result_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_chart_result VALIDATE CONSTRAINT fk_hra111_reporting_003;

-- hidra_reporting_data_source_binding.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_data_source_binding
    ADD CONSTRAINT fk_hra111_reporting_004
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_data_source_binding VALIDATE CONSTRAINT fk_hra111_reporting_004;

-- hidra_reporting_distribution_record.report_output_artifact_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_distribution_record
    ADD CONSTRAINT fk_hra111_reporting_005
    FOREIGN KEY (report_output_artifact_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_distribution_record VALIDATE CONSTRAINT fk_hra111_reporting_005;

-- hidra_reporting_distribution_record.report_publication_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_distribution_record
    ADD CONSTRAINT fk_hra111_reporting_006
    FOREIGN KEY (report_publication_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_distribution_record VALIDATE CONSTRAINT fk_hra111_reporting_006;

-- hidra_reporting_distribution_target.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_distribution_target
    ADD CONSTRAINT fk_hra111_reporting_007
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_distribution_target VALIDATE CONSTRAINT fk_hra111_reporting_007;

-- hidra_reporting_input_snapshot.report_run_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_input_snapshot
    ADD CONSTRAINT fk_hra111_reporting_008
    FOREIGN KEY (report_run_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_input_snapshot VALIDATE CONSTRAINT fk_hra111_reporting_008;

-- hidra_reporting_output_artifact.report_run_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_output_artifact
    ADD CONSTRAINT fk_hra111_reporting_009
    FOREIGN KEY (report_run_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_output_artifact VALIDATE CONSTRAINT fk_hra111_reporting_009;

-- hidra_reporting_parameter_definition.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_parameter_definition
    ADD CONSTRAINT fk_hra111_reporting_010
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_parameter_definition VALIDATE CONSTRAINT fk_hra111_reporting_010;

-- hidra_reporting_parameter_value.parameter_definition_id -> hidra_reporting_parameter_definition.id
ALTER TABLE hidra_reporting_parameter_value
    ADD CONSTRAINT fk_hra111_reporting_011
    FOREIGN KEY (parameter_definition_id) REFERENCES hidra_reporting_parameter_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_parameter_value VALIDATE CONSTRAINT fk_hra111_reporting_011;

-- hidra_reporting_parameter_value.report_request_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_parameter_value
    ADD CONSTRAINT fk_hra111_reporting_012
    FOREIGN KEY (report_request_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_parameter_value VALIDATE CONSTRAINT fk_hra111_reporting_012;

-- hidra_reporting_publication.report_run_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_publication
    ADD CONSTRAINT fk_hra111_reporting_013
    FOREIGN KEY (report_run_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_publication VALIDATE CONSTRAINT fk_hra111_reporting_013;

-- hidra_reporting_report_definition.report_category_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_report_definition
    ADD CONSTRAINT fk_hra111_reporting_014
    FOREIGN KEY (report_category_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_report_definition VALIDATE CONSTRAINT fk_hra111_reporting_014;

-- hidra_reporting_report_template_version.report_template_id -> hidra_reporting_report_template.id
ALTER TABLE hidra_reporting_report_template_version
    ADD CONSTRAINT fk_hra111_reporting_015
    FOREIGN KEY (report_template_id) REFERENCES hidra_reporting_report_template (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_report_template_version VALIDATE CONSTRAINT fk_hra111_reporting_015;

-- hidra_reporting_report_template.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_report_template
    ADD CONSTRAINT fk_hra111_reporting_016
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_report_template VALIDATE CONSTRAINT fk_hra111_reporting_016;

-- hidra_reporting_request.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_request
    ADD CONSTRAINT fk_hra111_reporting_017
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_request VALIDATE CONSTRAINT fk_hra111_reporting_017;

-- hidra_reporting_run.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_run
    ADD CONSTRAINT fk_hra111_reporting_018
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_run VALIDATE CONSTRAINT fk_hra111_reporting_018;

-- hidra_reporting_run.report_request_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_run
    ADD CONSTRAINT fk_hra111_reporting_019
    FOREIGN KEY (report_request_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_run VALIDATE CONSTRAINT fk_hra111_reporting_019;

-- hidra_reporting_run.template_version_id -> hidra_reporting_report_template_version.id
ALTER TABLE hidra_reporting_run
    ADD CONSTRAINT fk_hra111_reporting_020
    FOREIGN KEY (template_version_id) REFERENCES hidra_reporting_report_template_version (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_run VALIDATE CONSTRAINT fk_hra111_reporting_020;

-- hidra_reporting_schedule_parameter.parameter_definition_id -> hidra_reporting_parameter_definition.id
ALTER TABLE hidra_reporting_schedule_parameter
    ADD CONSTRAINT fk_hra111_reporting_021
    FOREIGN KEY (parameter_definition_id) REFERENCES hidra_reporting_parameter_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_schedule_parameter VALIDATE CONSTRAINT fk_hra111_reporting_021;

-- hidra_reporting_schedule_parameter.report_schedule_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_schedule_parameter
    ADD CONSTRAINT fk_hra111_reporting_022
    FOREIGN KEY (report_schedule_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_schedule_parameter VALIDATE CONSTRAINT fk_hra111_reporting_022;

-- hidra_reporting_schedule.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_schedule
    ADD CONSTRAINT fk_hra111_reporting_023
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_schedule VALIDATE CONSTRAINT fk_hra111_reporting_023;

-- hidra_reporting_section_definition.report_definition_id -> hidra_reporting_report_definition.id
ALTER TABLE hidra_reporting_section_definition
    ADD CONSTRAINT fk_hra111_reporting_024
    FOREIGN KEY (report_definition_id) REFERENCES hidra_reporting_report_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_section_definition VALIDATE CONSTRAINT fk_hra111_reporting_024;

-- hidra_reporting_section_result.report_run_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_section_result
    ADD CONSTRAINT fk_hra111_reporting_025
    FOREIGN KEY (report_run_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_section_result VALIDATE CONSTRAINT fk_hra111_reporting_025;

-- hidra_reporting_section_result.section_definition_id -> hidra_reporting_section_definition.id
ALTER TABLE hidra_reporting_section_result
    ADD CONSTRAINT fk_hra111_reporting_026
    FOREIGN KEY (section_definition_id) REFERENCES hidra_reporting_section_definition (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_section_result VALIDATE CONSTRAINT fk_hra111_reporting_026;

-- hidra_reporting_table_result.report_section_result_id -> hidra_reporting_catalog_entry.id
ALTER TABLE hidra_reporting_table_result
    ADD CONSTRAINT fk_hra111_reporting_027
    FOREIGN KEY (report_section_result_id) REFERENCES hidra_reporting_catalog_entry (id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_reporting_table_result VALIDATE CONSTRAINT fk_hra111_reporting_027;

