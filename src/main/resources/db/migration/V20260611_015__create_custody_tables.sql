-- HIDRA custody module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity
-- Module: custody

CREATE TABLE IF NOT EXISTS hidra_custody_agreement (
    id varchar(80) PRIMARY KEY,
    agreement_number varchar(80) NOT NULL,
    agreement_type_id varchar(80) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    transfer_point_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    terms_snapshot_json jsonb,
    document_reference_id varchar(80),
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_agreement_type_id ON hidra_custody_agreement (agreement_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_transfer_point_id ON hidra_custody_agreement (transfer_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_status ON hidra_custody_agreement (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_document_reference_id ON hidra_custody_agreement (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_created_by_actor_id ON hidra_custody_agreement (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_created_at ON hidra_custody_agreement (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_updated_at ON hidra_custody_agreement (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_agreement_party (
    id varchar(80) PRIMARY KEY,
    agreement_id varchar(80) NOT NULL,
    party_role_id varchar(80) NOT NULL,
    party_id varchar(80) NOT NULL,
    party_code_snapshot varchar(160),
    party_name_snapshot varchar(255) NOT NULL,
    party_role_code_snapshot varchar(80),
    ownership_share_percent numeric(10,6),
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_party_agreement_id ON hidra_custody_agreement_party (agreement_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_party_party_role_id ON hidra_custody_agreement_party (party_role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_party_party_id ON hidra_custody_agreement_party (party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_agreement_party_created_at ON hidra_custody_agreement_party (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_approval_reference (
    id varchar(80) PRIMARY KEY,
    target_type varchar(80) NOT NULL,
    target_id varchar(80) NOT NULL,
    workflow_instance_id varchar(80),
    workflow_task_id varchar(80),
    approved_by_actor_id varchar(80),
    approval_status varchar(40) NOT NULL,
    approval_comment text,
    approved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_approval_reference_target_id ON hidra_custody_approval_reference (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_approval_reference_workflow_instance_id ON hidra_custody_approval_reference (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_approval_reference_workflow_task_id ON hidra_custody_approval_reference (workflow_task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_approval_reference_approved_by_actor_id ON hidra_custody_approval_reference (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_approval_reference_created_at ON hidra_custody_approval_reference (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_batch (
    id varchar(80) PRIMARY KEY,
    batch_number varchar(80) NOT NULL,
    measurement_period_id varchar(80) NOT NULL,
    agreement_id varchar(80) NOT NULL,
    product_type_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    batch_start timestamp with time zone,
    batch_end timestamp with time zone,
    expected_quantity numeric(18,6),
    expected_quantity_unit_id varchar(80),
    source_plan_target_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_measurement_period_id ON hidra_custody_batch (measurement_period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_agreement_id ON hidra_custody_batch (agreement_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_product_type_id ON hidra_custody_batch (product_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_status ON hidra_custody_batch (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_expected_quantity_unit_id ON hidra_custody_batch (expected_quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_source_plan_target_id ON hidra_custody_batch (source_plan_target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_created_at ON hidra_custody_batch (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_batch_updated_at ON hidra_custody_batch (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_catalog_entry_code ON hidra_custody_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_catalog_entry_active ON hidra_custody_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_catalog_entry_created_at ON hidra_custody_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_catalog_entry_updated_at ON hidra_custody_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_catalog_translation_catalog_entry_id ON hidra_custody_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_catalog_translation_created_at ON hidra_custody_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_catalog_translation_updated_at ON hidra_custody_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_correction_factor (
    id varchar(80) PRIMARY KEY,
    quantity_calculation_id varchar(80) NOT NULL,
    factor_type_id varchar(80) NOT NULL,
    factor_code varchar(80) NOT NULL,
    factor_value numeric(18,8) NOT NULL,
    basis_description text,
    source_reference_id varchar(80),
    applied_at timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_correction_factor_quantity_calculation_id ON hidra_custody_correction_factor (quantity_calculation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_correction_factor_factor_type_id ON hidra_custody_correction_factor (factor_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_correction_factor_source_reference_id ON hidra_custody_correction_factor (source_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_correction_factor_created_at ON hidra_custody_correction_factor (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_discrepancy (
    id varchar(80) PRIMARY KEY,
    discrepancy_number varchar(80) NOT NULL,
    reconciliation_id varchar(80) NOT NULL,
    discrepancy_type_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    difference_quantity numeric(18,6),
    quantity_unit_id varchar(80),
    description text,
    root_cause_text text,
    resolution_text text,
    assigned_actor_id varchar(80),
    opened_at timestamp with time zone NOT NULL,
    resolved_at timestamp with time zone,
    closed_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_discrepancy_reconciliation_id ON hidra_custody_discrepancy (reconciliation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_discrepancy_discrepancy_type_id ON hidra_custody_discrepancy (discrepancy_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_discrepancy_status ON hidra_custody_discrepancy (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_discrepancy_quantity_unit_id ON hidra_custody_discrepancy (quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_discrepancy_assigned_actor_id ON hidra_custody_discrepancy (assigned_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_discrepancy_created_at ON hidra_custody_discrepancy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_discrepancy_updated_at ON hidra_custody_discrepancy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_document_reference (
    id varchar(80) PRIMARY KEY,
    target_type varchar(80) NOT NULL,
    target_id varchar(80) NOT NULL,
    document_type varchar(80) NOT NULL,
    document_reference_id varchar(80) NOT NULL,
    document_code_snapshot varchar(160),
    document_title_snapshot varchar(255),
    attached_at timestamp with time zone NOT NULL,
    attached_by_actor_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_document_reference_target_id ON hidra_custody_document_reference (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_document_reference_document_reference_id ON hidra_custody_document_reference (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_document_reference_attached_by_actor_id ON hidra_custody_document_reference (attached_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_custody_measurement_period (
    id varchar(80) PRIMARY KEY,
    period_code varchar(80) NOT NULL,
    agreement_id varchar(80) NOT NULL,
    transfer_point_id varchar(80) NOT NULL,
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    status varchar(40) NOT NULL,
    locked_by_actor_id varchar(80),
    locked_at timestamp with time zone,
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_period_agreement_id ON hidra_custody_measurement_period (agreement_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_period_transfer_point_id ON hidra_custody_measurement_period (transfer_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_period_status ON hidra_custody_measurement_period (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_period_locked_by_actor_id ON hidra_custody_measurement_period (locked_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_period_approved_by_actor_id ON hidra_custody_measurement_period (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_period_created_at ON hidra_custody_measurement_period (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_period_updated_at ON hidra_custody_measurement_period (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_measurement_snapshot (
    id varchar(80) PRIMARY KEY,
    measurement_period_id varchar(80) NOT NULL,
    batch_id varchar(80),
    meter_run_snapshot_id varchar(80),
    telemetry_reading_reference_id varchar(80),
    telemetry_point_reference_id varchar(80),
    measurement_type_id varchar(80) NOT NULL,
    observed_value numeric(18,6) NOT NULL,
    observed_unit_id varchar(80) NOT NULL,
    standard_value numeric(18,6),
    standard_unit_id varchar(80),
    measured_at timestamp with time zone NOT NULL,
    accepted_for_custody boolean NOT NULL,
    quality_flag_snapshot varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_measurement_period_id ON hidra_custody_measurement_snapshot (measurement_period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_batch_id ON hidra_custody_measurement_snapshot (batch_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_meter_run_snapshot_id ON hidra_custody_measurement_snapshot (meter_run_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_telemetry_reading_refe ON hidra_custody_measurement_snapshot (telemetry_reading_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_telemetry_point_refere ON hidra_custody_measurement_snapshot (telemetry_point_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_measurement_type_id ON hidra_custody_measurement_snapshot (measurement_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_observed_unit_id ON hidra_custody_measurement_snapshot (observed_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_standard_unit_id ON hidra_custody_measurement_snapshot (standard_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_measurement_snapshot_created_at ON hidra_custody_measurement_snapshot (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_meter_run_snapshot (
    id varchar(80) PRIMARY KEY,
    measurement_period_id varchar(80) NOT NULL,
    metering_system_id varchar(80) NOT NULL,
    meter_run_code_snapshot varchar(160) NOT NULL,
    meter_serial_snapshot varchar(160),
    calibration_certificate_snapshot varchar(160),
    configuration_snapshot_json jsonb,
    snapshot_at timestamp with time zone NOT NULL,
    snapshot_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_meter_run_snapshot_measurement_period_id ON hidra_custody_meter_run_snapshot (measurement_period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_meter_run_snapshot_metering_system_id ON hidra_custody_meter_run_snapshot (metering_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_meter_run_snapshot_snapshot_by_actor_id ON hidra_custody_meter_run_snapshot (snapshot_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_meter_run_snapshot_created_at ON hidra_custody_meter_run_snapshot (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_metering_system (
    id varchar(80) PRIMARY KEY,
    metering_system_code varchar(80) NOT NULL,
    name varchar(160) NOT NULL,
    transfer_point_id varchar(80) NOT NULL,
    topology_asset_type_code varchar(80),
    topology_asset_id varchar(80),
    measurement_standard_id varchar(80),
    calibration_certificate_id varchar(80),
    active boolean NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_metering_system_transfer_point_id ON hidra_custody_metering_system (transfer_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_metering_system_topology_asset_id ON hidra_custody_metering_system (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_metering_system_measurement_standard_id ON hidra_custody_metering_system (measurement_standard_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_metering_system_calibration_certificate_id ON hidra_custody_metering_system (calibration_certificate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_metering_system_active ON hidra_custody_metering_system (active);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_metering_system_created_at ON hidra_custody_metering_system (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_metering_system_updated_at ON hidra_custody_metering_system (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_quality_certificate (
    id varchar(80) PRIMARY KEY,
    certificate_number varchar(80) NOT NULL,
    quality_sample_id varchar(80),
    document_reference_id varchar(80),
    issued_by_party_id varchar(80),
    issued_by_name_snapshot varchar(255),
    issued_at timestamp with time zone NOT NULL,
    certificate_summary_json jsonb,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_certificate_quality_sample_id ON hidra_custody_quality_certificate (quality_sample_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_certificate_document_reference_id ON hidra_custody_quality_certificate (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_certificate_issued_by_party_id ON hidra_custody_quality_certificate (issued_by_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_certificate_status ON hidra_custody_quality_certificate (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_certificate_created_at ON hidra_custody_quality_certificate (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_quality_sample (
    id varchar(80) PRIMARY KEY,
    sample_number varchar(80) NOT NULL,
    measurement_period_id varchar(80) NOT NULL,
    batch_id varchar(80),
    sample_type_id varchar(80) NOT NULL,
    product_type_id varchar(80) NOT NULL,
    sampled_at timestamp with time zone NOT NULL,
    sampled_by_actor_id varchar(80),
    laboratory_party_id varchar(80),
    laboratory_name_snapshot varchar(255),
    result_summary text,
    certificate_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_measurement_period_id ON hidra_custody_quality_sample (measurement_period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_batch_id ON hidra_custody_quality_sample (batch_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_sample_type_id ON hidra_custody_quality_sample (sample_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_product_type_id ON hidra_custody_quality_sample (product_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_sampled_by_actor_id ON hidra_custody_quality_sample (sampled_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_laboratory_party_id ON hidra_custody_quality_sample (laboratory_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_certificate_id ON hidra_custody_quality_sample (certificate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quality_sample_created_at ON hidra_custody_quality_sample (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_quantity_calculation (
    id varchar(80) PRIMARY KEY,
    calculation_number varchar(80) NOT NULL,
    measurement_period_id varchar(80) NOT NULL,
    batch_id varchar(80),
    quantity_basis varchar(80) NOT NULL,
    gross_observed_quantity numeric(18,6),
    gross_standard_quantity numeric(18,6),
    net_standard_quantity numeric(18,6),
    mass_quantity numeric(18,6),
    quantity_unit_id varchar(80) NOT NULL,
    calculation_method_id varchar(80),
    calculation_details_json jsonb,
    calculated_by_actor_id varchar(80),
    calculated_at timestamp with time zone NOT NULL,
    official boolean NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quantity_calculation_measurement_period_id ON hidra_custody_quantity_calculation (measurement_period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quantity_calculation_batch_id ON hidra_custody_quantity_calculation (batch_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quantity_calculation_quantity_unit_id ON hidra_custody_quantity_calculation (quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quantity_calculation_calculation_method_id ON hidra_custody_quantity_calculation (calculation_method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quantity_calculation_calculated_by_actor_id ON hidra_custody_quantity_calculation (calculated_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_quantity_calculation_created_at ON hidra_custody_quantity_calculation (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_reconciliation (
    id varchar(80) PRIMARY KEY,
    reconciliation_number varchar(80) NOT NULL,
    measurement_period_id varchar(80) NOT NULL,
    agreement_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    total_ticket_quantity numeric(18,6),
    total_measured_quantity numeric(18,6),
    difference_quantity numeric(18,6),
    quantity_unit_id varchar(80),
    difference_percent numeric(10,6),
    reconciled_by_actor_id varchar(80),
    reconciled_at timestamp with time zone,
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_measurement_period_id ON hidra_custody_reconciliation (measurement_period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_agreement_id ON hidra_custody_reconciliation (agreement_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_status ON hidra_custody_reconciliation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_quantity_unit_id ON hidra_custody_reconciliation (quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_reconciled_by_actor_id ON hidra_custody_reconciliation (reconciled_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_approved_by_actor_id ON hidra_custody_reconciliation (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_created_at ON hidra_custody_reconciliation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_reconciliation_updated_at ON hidra_custody_reconciliation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_ticket_line (
    id varchar(80) PRIMARY KEY,
    transfer_ticket_id varchar(80) NOT NULL,
    line_number integer NOT NULL,
    line_type_id varchar(80) NOT NULL,
    product_type_id varchar(80) NOT NULL,
    quantity numeric(18,6) NOT NULL,
    quantity_unit_id varchar(80) NOT NULL,
    quality_value numeric(18,6),
    quality_unit_id varchar(80),
    description text,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_ticket_line_transfer_ticket_id ON hidra_custody_ticket_line (transfer_ticket_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_ticket_line_line_type_id ON hidra_custody_ticket_line (line_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_ticket_line_product_type_id ON hidra_custody_ticket_line (product_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_ticket_line_quantity_unit_id ON hidra_custody_ticket_line (quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_ticket_line_quality_unit_id ON hidra_custody_ticket_line (quality_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_ticket_line_created_at ON hidra_custody_ticket_line (created_at);

CREATE TABLE IF NOT EXISTS hidra_custody_transfer_point (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    topology_asset_name_snapshot varchar(500),
    direction varchar(40) NOT NULL,
    product_type_id varchar(80),
    measurement_location_id varchar(80),
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_point_code ON hidra_custody_transfer_point (code);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_point_topology_asset_id ON hidra_custody_transfer_point (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_point_product_type_id ON hidra_custody_transfer_point (product_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_point_measurement_location_id ON hidra_custody_transfer_point (measurement_location_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_point_status ON hidra_custody_transfer_point (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_point_created_at ON hidra_custody_transfer_point (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_point_updated_at ON hidra_custody_transfer_point (updated_at);

CREATE TABLE IF NOT EXISTS hidra_custody_transfer_ticket (
    id varchar(80) PRIMARY KEY,
    ticket_number varchar(80) NOT NULL,
    measurement_period_id varchar(80) NOT NULL,
    agreement_id varchar(80) NOT NULL,
    transfer_point_id varchar(80) NOT NULL,
    batch_id varchar(80),
    quantity_calculation_id varchar(80),
    status varchar(40) NOT NULL,
    ticket_date timestamp with time zone NOT NULL,
    issued_by_actor_id varchar(80),
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    workflow_instance_id varchar(80),
    audit_reference_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_measurement_period_id ON hidra_custody_transfer_ticket (measurement_period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_agreement_id ON hidra_custody_transfer_ticket (agreement_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_transfer_point_id ON hidra_custody_transfer_ticket (transfer_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_batch_id ON hidra_custody_transfer_ticket (batch_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_quantity_calculation_id ON hidra_custody_transfer_ticket (quantity_calculation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_status ON hidra_custody_transfer_ticket (status);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_issued_by_actor_id ON hidra_custody_transfer_ticket (issued_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_approved_by_actor_id ON hidra_custody_transfer_ticket (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_workflow_instance_id ON hidra_custody_transfer_ticket (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_audit_reference_id ON hidra_custody_transfer_ticket (audit_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_created_at ON hidra_custody_transfer_ticket (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_custody_transfer_ticket_updated_at ON hidra_custody_transfer_ticket (updated_at);
