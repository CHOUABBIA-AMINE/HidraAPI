-- HIDRA planning module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity
-- Module: planning

CREATE TABLE IF NOT EXISTS hidra_planning_expected_flow_state (
    id varchar(80) PRIMARY KEY,
    revision_id varchar(80) NOT NULL,
    scenario_id varchar(80),
    plan_target_id varchar(80),
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    expected_at timestamp with time zone NOT NULL,
    expected_flow_rate numeric(18,6),
    flow_rate_unit_id varchar(80),
    expected_pressure_in numeric(18,6),
    expected_pressure_out numeric(18,6),
    pressure_unit_id varchar(80),
    expected_temperature numeric(18,6),
    temperature_unit_id varchar(80),
    expected_volume numeric(18,6),
    volume_unit_id varchar(80),
    expected_operating_mode varchar(160),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_revision_id ON hidra_planning_expected_flow_state (revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_scenario_id ON hidra_planning_expected_flow_state (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_plan_target_id ON hidra_planning_expected_flow_state (plan_target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_topology_asset_id ON hidra_planning_expected_flow_state (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_flow_rate_unit_id ON hidra_planning_expected_flow_state (flow_rate_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_pressure_unit_id ON hidra_planning_expected_flow_state (pressure_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_temperature_unit_id ON hidra_planning_expected_flow_state (temperature_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_volume_unit_id ON hidra_planning_expected_flow_state (volume_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_expected_flow_state_created_at ON hidra_planning_expected_flow_state (created_at);

CREATE TABLE IF NOT EXISTS hidra_planning_forecast_point (
    id varchar(80) PRIMARY KEY,
    forecast_series_id varchar(80) NOT NULL,
    forecast_at timestamp with time zone NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone NOT NULL,
    value numeric(18,6) NOT NULL,
    unit_id varchar(80) NOT NULL,
    confidence_level numeric(10,4),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_point_forecast_series_id ON hidra_planning_forecast_point (forecast_series_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_point_unit_id ON hidra_planning_forecast_point (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_point_created_at ON hidra_planning_forecast_point (created_at);

CREATE TABLE IF NOT EXISTS hidra_planning_forecast_series (
    id varchar(80) PRIMARY KEY,
    period_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    forecast_type_id varchar(80) NOT NULL,
    topology_asset_type varchar(160),
    topology_asset_id varchar(80),
    product_type_id varchar(80),
    source_module varchar(160),
    source_reference_id varchar(80),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_period_id ON hidra_planning_forecast_series (period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_code ON hidra_planning_forecast_series (code);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_forecast_type_id ON hidra_planning_forecast_series (forecast_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_topology_asset_id ON hidra_planning_forecast_series (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_product_type_id ON hidra_planning_forecast_series (product_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_source_reference_id ON hidra_planning_forecast_series (source_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_status ON hidra_planning_forecast_series (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_created_at ON hidra_planning_forecast_series (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_forecast_series_updated_at ON hidra_planning_forecast_series (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_nomination (
    id varchar(80) PRIMARY KEY,
    revision_id varchar(80) NOT NULL,
    scenario_id varchar(80),
    code varchar(80) NOT NULL,
    nomination_type_id varchar(80) NOT NULL,
    product_type_id varchar(80) NOT NULL,
    quantity numeric(18,6) NOT NULL,
    quantity_unit_id varchar(80) NOT NULL,
    rate numeric(18,6),
    rate_unit_id varchar(80),
    source_asset_type varchar(160),
    source_asset_id varchar(80),
    source_asset_code varchar(160),
    destination_asset_type varchar(160),
    destination_asset_id varchar(80),
    destination_asset_code varchar(160),
    shipper_party_id varchar(80),
    shipper_party_code_snapshot varchar(160),
    counterparty_id varchar(80),
    contract_reference_id varchar(80),
    priority integer,
    status varchar(40) NOT NULL,
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_revision_id ON hidra_planning_nomination (revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_scenario_id ON hidra_planning_nomination (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_code ON hidra_planning_nomination (code);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_nomination_type_id ON hidra_planning_nomination (nomination_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_product_type_id ON hidra_planning_nomination (product_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_quantity_unit_id ON hidra_planning_nomination (quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_rate_unit_id ON hidra_planning_nomination (rate_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_source_asset_id ON hidra_planning_nomination (source_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_destination_asset_id ON hidra_planning_nomination (destination_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_shipper_party_id ON hidra_planning_nomination (shipper_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_counterparty_id ON hidra_planning_nomination (counterparty_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_contract_reference_id ON hidra_planning_nomination (contract_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_status ON hidra_planning_nomination (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_created_at ON hidra_planning_nomination (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_updated_at ON hidra_planning_nomination (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_nomination_schedule_line (
    id varchar(80) PRIMARY KEY,
    nomination_id varchar(80) NOT NULL,
    sequence_number integer NOT NULL,
    line_start timestamp with time zone NOT NULL,
    line_end timestamp with time zone NOT NULL,
    planned_quantity numeric(18,6),
    quantity_unit_id varchar(80),
    planned_rate numeric(18,6),
    rate_unit_id varchar(80),
    notes varchar(500)
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_schedule_line_nomination_id ON hidra_planning_nomination_schedule_line (nomination_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_schedule_line_quantity_unit_id ON hidra_planning_nomination_schedule_line (quantity_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_nomination_schedule_line_rate_unit_id ON hidra_planning_nomination_schedule_line (rate_unit_id);

CREATE TABLE IF NOT EXISTS hidra_planning_operational_plan (
    id varchar(80) PRIMARY KEY,
    period_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    plan_type_id varchar(80) NOT NULL,
    product_type_id varchar(80),
    topology_scope_type varchar(160) NOT NULL,
    topology_scope_id varchar(80) NOT NULL,
    topology_scope_code varchar(160) NOT NULL,
    topology_scope_name_snapshot varchar(500),
    responsible_organization_unit_id varchar(80),
    status varchar(40) NOT NULL,
    current_revision_id varchar(80),
    approved_revision_id varchar(80),
    created_by_actor_id varchar(80) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_period_id ON hidra_planning_operational_plan (period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_code ON hidra_planning_operational_plan (code);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_plan_type_id ON hidra_planning_operational_plan (plan_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_product_type_id ON hidra_planning_operational_plan (product_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_topology_scope_id ON hidra_planning_operational_plan (topology_scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_responsible_organization_ ON hidra_planning_operational_plan (responsible_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_status ON hidra_planning_operational_plan (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_current_revision_id ON hidra_planning_operational_plan (current_revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_approved_revision_id ON hidra_planning_operational_plan (approved_revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_created_by_actor_id ON hidra_planning_operational_plan (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_created_at ON hidra_planning_operational_plan (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operational_plan_updated_at ON hidra_planning_operational_plan (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_actual_review_snapshot (
    id varchar(80) PRIMARY KEY,
    plan_target_id varchar(80) NOT NULL,
    monitoring_deviation_id varchar(80),
    trusted_telemetry_reading_id varchar(80),
    actual_value numeric(18,6),
    planned_value numeric(18,6),
    difference_value numeric(18,6),
    difference_percent numeric(10,4),
    unit_id varchar(80),
    reviewed_at timestamp with time zone NOT NULL,
    review_source varchar(40) NOT NULL,
    notes varchar(500)
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_actual_review_snapshot_plan_target_id ON hidra_planning_actual_review_snapshot (plan_target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_actual_review_snapshot_monitoring_deviatio ON hidra_planning_actual_review_snapshot (monitoring_deviation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_actual_review_snapshot_trusted_telemetry_r ON hidra_planning_actual_review_snapshot (trusted_telemetry_reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_actual_review_snapshot_unit_id ON hidra_planning_actual_review_snapshot (unit_id);

CREATE TABLE IF NOT EXISTS hidra_planning_plan_approval_reference (
    id varchar(80) PRIMARY KEY,
    revision_id varchar(80) NOT NULL,
    workflow_instance_id varchar(80) NOT NULL,
    workflow_definition_code_snapshot varchar(160),
    approval_status_snapshot varchar(40) NOT NULL,
    submitted_by_actor_id varchar(80),
    submitted_at timestamp with time zone,
    decided_by_actor_id varchar(80),
    decided_at timestamp with time zone,
    decision_reason_snapshot varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_approval_reference_revision_id ON hidra_planning_plan_approval_reference (revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_approval_reference_workflow_instance_ ON hidra_planning_plan_approval_reference (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_approval_reference_submitted_by_actor ON hidra_planning_plan_approval_reference (submitted_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_approval_reference_decided_by_actor_i ON hidra_planning_plan_approval_reference (decided_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_approval_reference_created_at ON hidra_planning_plan_approval_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_approval_reference_updated_at ON hidra_planning_plan_approval_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_plan_constraint (
    id varchar(80) PRIMARY KEY,
    revision_id varchar(80) NOT NULL,
    scenario_id varchar(80),
    constraint_type_id varchar(80) NOT NULL,
    severity varchar(40) NOT NULL,
    topology_asset_type varchar(160),
    topology_asset_id varchar(80),
    topology_asset_code varchar(160),
    constraint_value numeric(18,6),
    unit_id varchar(80),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    source_module varchar(160),
    source_reference_id varchar(80),
    description varchar(500),
    blocking boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_revision_id ON hidra_planning_plan_constraint (revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_scenario_id ON hidra_planning_plan_constraint (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_constraint_type_id ON hidra_planning_plan_constraint (constraint_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_topology_asset_id ON hidra_planning_plan_constraint (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_unit_id ON hidra_planning_plan_constraint (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_source_reference_id ON hidra_planning_plan_constraint (source_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_status ON hidra_planning_plan_constraint (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_created_at ON hidra_planning_plan_constraint (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_constraint_updated_at ON hidra_planning_plan_constraint (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_plan_revision (
    id varchar(80) PRIMARY KEY,
    plan_id varchar(80) NOT NULL,
    revision_number integer NOT NULL,
    revision_code varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    change_reason_code_id varchar(80),
    change_reason_text varchar(500),
    base_revision_id varchar(80),
    submitted_by_actor_id varchar(80),
    submitted_at timestamp with time zone,
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    workflow_instance_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_plan_id ON hidra_planning_plan_revision (plan_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_status ON hidra_planning_plan_revision (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_change_reason_code_id ON hidra_planning_plan_revision (change_reason_code_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_base_revision_id ON hidra_planning_plan_revision (base_revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_submitted_by_actor_id ON hidra_planning_plan_revision (submitted_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_approved_by_actor_id ON hidra_planning_plan_revision (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_workflow_instance_id ON hidra_planning_plan_revision (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_created_at ON hidra_planning_plan_revision (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_revision_updated_at ON hidra_planning_plan_revision (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_plan_scenario (
    id varchar(80) PRIMARY KEY,
    revision_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    scenario_type_id varchar(80) NOT NULL,
    primary_scenario boolean NOT NULL,
    status varchar(40) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_scenario_revision_id ON hidra_planning_plan_scenario (revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_scenario_code ON hidra_planning_plan_scenario (code);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_scenario_scenario_type_id ON hidra_planning_plan_scenario (scenario_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_scenario_status ON hidra_planning_plan_scenario (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_scenario_created_at ON hidra_planning_plan_scenario (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_scenario_updated_at ON hidra_planning_plan_scenario (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_plan_target (
    id varchar(80) PRIMARY KEY,
    revision_id varchar(80) NOT NULL,
    scenario_id varchar(80),
    nomination_id varchar(80),
    target_type_id varchar(80) NOT NULL,
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    topology_asset_name_snapshot varchar(500),
    telemetry_point_id varchar(80),
    telemetry_point_code_snapshot varchar(160),
    target_value numeric(18,6),
    target_text_value varchar(160),
    unit_id varchar(80),
    tolerance_low numeric(18,6),
    tolerance_high numeric(18,6),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone NOT NULL,
    priority integer,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_revision_id ON hidra_planning_plan_target (revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_scenario_id ON hidra_planning_plan_target (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_nomination_id ON hidra_planning_plan_target (nomination_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_target_type_id ON hidra_planning_plan_target (target_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_topology_asset_id ON hidra_planning_plan_target (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_telemetry_point_id ON hidra_planning_plan_target (telemetry_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_unit_id ON hidra_planning_plan_target (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_status ON hidra_planning_plan_target (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_created_at ON hidra_planning_plan_target (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_plan_target_updated_at ON hidra_planning_plan_target (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_operation_window (
    id varchar(80) PRIMARY KEY,
    revision_id varchar(80) NOT NULL,
    scenario_id varchar(80),
    code varchar(80) NOT NULL,
    window_type_id varchar(80) NOT NULL,
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    planned_start timestamp with time zone NOT NULL,
    planned_end timestamp with time zone NOT NULL,
    capacity_impact_percent numeric(10,4),
    description varchar(500),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_revision_id ON hidra_planning_operation_window (revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_scenario_id ON hidra_planning_operation_window (scenario_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_code ON hidra_planning_operation_window (code);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_window_type_id ON hidra_planning_operation_window (window_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_topology_asset_id ON hidra_planning_operation_window (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_status ON hidra_planning_operation_window (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_created_at ON hidra_planning_operation_window (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_operation_window_updated_at ON hidra_planning_operation_window (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_catalog_entry_code ON hidra_planning_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_catalog_entry_active ON hidra_planning_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_catalog_entry_created_at ON hidra_planning_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_catalog_entry_updated_at ON hidra_planning_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(160) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_catalog_translation_catalog_entry_id ON hidra_planning_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_catalog_translation_created_at ON hidra_planning_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_catalog_translation_updated_at ON hidra_planning_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_planning_period (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    period_type_id varchar(80) NOT NULL,
    period_start timestamp with time zone NOT NULL,
    period_end timestamp with time zone NOT NULL,
    time_zone varchar(120) NOT NULL,
    status varchar(40) NOT NULL,
    created_by_actor_id varchar(80) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_period_code ON hidra_planning_period (code);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_period_period_type_id ON hidra_planning_period (period_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_period_status ON hidra_planning_period (status);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_period_created_by_actor_id ON hidra_planning_period (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_period_created_at ON hidra_planning_period (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_planning_period_updated_at ON hidra_planning_period (updated_at);
