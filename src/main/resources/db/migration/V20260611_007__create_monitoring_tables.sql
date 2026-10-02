-- HIDRA monitoring module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity
-- Module: monitoring

CREATE TABLE IF NOT EXISTS hidra_monitoring_acknowledgement (
    id varchar(80) PRIMARY KEY,
    target_type varchar(80) NOT NULL,
    target_id varchar(80) NOT NULL,
    acknowledgement_status varchar(40) NOT NULL,
    acknowledged_by_actor_id varchar(80) NOT NULL,
    acknowledged_at timestamp with time zone NOT NULL,
    comment text,
    workflow_instance_id varchar(80),
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_acknowledgement_target_id ON hidra_monitoring_acknowledgement (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_acknowledgement_acknowledged_by_actor_id ON hidra_monitoring_acknowledgement (acknowledged_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_acknowledgement_workflow_instance_id ON hidra_monitoring_acknowledgement (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_acknowledgement_correlation_id ON hidra_monitoring_acknowledgement (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_monitoring_alert_candidate (
    id varchar(80) PRIMARY KEY,
    deviation_id varchar(80),
    evaluation_id varchar(80),
    rule_id varchar(80),
    candidate_code varchar(80) NOT NULL,
    candidate_type_id varchar(80) NOT NULL,
    severity varchar(40) NOT NULL,
    topology_asset_type varchar(160),
    topology_asset_id varchar(80),
    telemetry_point_id varchar(80),
    summary varchar(500),
    lifecycle_status varchar(40) NOT NULL,
    candidate_status varchar(40) NOT NULL,
    escalation_reference_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    expires_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_deviation_id ON hidra_monitoring_alert_candidate (deviation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_evaluation_id ON hidra_monitoring_alert_candidate (evaluation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_rule_id ON hidra_monitoring_alert_candidate (rule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_candidate_type_id ON hidra_monitoring_alert_candidate (candidate_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_topology_asset_id ON hidra_monitoring_alert_candidate (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_telemetry_point_id ON hidra_monitoring_alert_candidate (telemetry_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_escalation_reference_id ON hidra_monitoring_alert_candidate (escalation_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_alert_candidate_created_at ON hidra_monitoring_alert_candidate (created_at);

CREATE TABLE IF NOT EXISTS hidra_monitoring_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_catalog_entry_code ON hidra_monitoring_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_catalog_entry_active ON hidra_monitoring_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_catalog_entry_created_at ON hidra_monitoring_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_catalog_entry_updated_at ON hidra_monitoring_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_monitoring_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_catalog_translation_catalog_entry_id ON hidra_monitoring_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_catalog_translation_created_at ON hidra_monitoring_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_catalog_translation_updated_at ON hidra_monitoring_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_monitoring_evaluation (
    id varchar(80) PRIMARY KEY,
    rule_id varchar(80),
    period_id varchar(80),
    plan_revision_id varchar(80),
    topology_asset_type varchar(160),
    topology_asset_id varchar(80),
    telemetry_point_id varchar(80),
    status varchar(40) NOT NULL,
    result varchar(40),
    evaluation_start timestamp with time zone NOT NULL,
    evaluation_end timestamp with time zone,
    actual_reading_count integer NOT NULL,
    deviation_count integer NOT NULL,
    failure_reason text,
    correlation_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_rule_id ON hidra_monitoring_evaluation (rule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_period_id ON hidra_monitoring_evaluation (period_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_plan_revision_id ON hidra_monitoring_evaluation (plan_revision_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_topology_asset_id ON hidra_monitoring_evaluation (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_telemetry_point_id ON hidra_monitoring_evaluation (telemetry_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_status ON hidra_monitoring_evaluation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_correlation_id ON hidra_monitoring_evaluation (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_evaluation_created_at ON hidra_monitoring_evaluation (created_at);

CREATE TABLE IF NOT EXISTS hidra_monitoring_rule (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    rule_type varchar(80) NOT NULL,
    evaluation_frequency_id varchar(80),
    topology_asset_type varchar(160),
    topology_asset_id varchar(80),
    topology_asset_code varchar(160),
    telemetry_point_id varchar(80),
    planning_target_type_id varchar(80),
    expression text,
    status varchar(40) NOT NULL,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_code ON hidra_monitoring_rule (code);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_evaluation_frequency_id ON hidra_monitoring_rule (evaluation_frequency_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_topology_asset_id ON hidra_monitoring_rule (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_telemetry_point_id ON hidra_monitoring_rule (telemetry_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_planning_target_type_id ON hidra_monitoring_rule (planning_target_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_status ON hidra_monitoring_rule (status);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_created_by_actor_id ON hidra_monitoring_rule (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_created_at ON hidra_monitoring_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_rule_updated_at ON hidra_monitoring_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_monitoring_threshold (
    id varchar(80) PRIMARY KEY,
    rule_id varchar(80) NOT NULL,
    threshold_direction varchar(80) NOT NULL,
    low_value numeric(18,6),
    high_value numeric(18,6),
    expected_text_value varchar(160),
    unit_id varchar(80),
    severity varchar(40) NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_threshold_rule_id ON hidra_monitoring_threshold (rule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_threshold_unit_id ON hidra_monitoring_threshold (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_threshold_active ON hidra_monitoring_threshold (active);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_threshold_created_at ON hidra_monitoring_threshold (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_threshold_updated_at ON hidra_monitoring_threshold (updated_at);

CREATE TABLE IF NOT EXISTS hidra_monitoring_operational_state (
    id varchar(80) PRIMARY KEY,
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160),
    telemetry_point_id varchar(80),
    state_value varchar(40) NOT NULL,
    severity varchar(40),
    reason_code varchar(80),
    reason_message text,
    last_trusted_reading_id varchar(80),
    last_plan_target_id varchar(80),
    state_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_topology_asset_id ON hidra_monitoring_operational_state (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_telemetry_point_id ON hidra_monitoring_operational_state (telemetry_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_last_trusted_reading_i ON hidra_monitoring_operational_state (last_trusted_reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_last_plan_target_id ON hidra_monitoring_operational_state (last_plan_target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_updated_at ON hidra_monitoring_operational_state (updated_at);

CREATE TABLE IF NOT EXISTS hidra_monitoring_operational_state_snapshot (
    id varchar(80) PRIMARY KEY,
    operational_state_id varchar(80) NOT NULL,
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    telemetry_point_id varchar(80),
    state_value varchar(40) NOT NULL,
    severity varchar(40),
    snapshot_payload jsonb,
    captured_at timestamp with time zone NOT NULL,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_snapshot_operational_s ON hidra_monitoring_operational_state_snapshot (operational_state_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_snapshot_topology_asse ON hidra_monitoring_operational_state_snapshot (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_snapshot_telemetry_poi ON hidra_monitoring_operational_state_snapshot (telemetry_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_operational_state_snapshot_correlation_i ON hidra_monitoring_operational_state_snapshot (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_monitoring_plan_actual_deviation (
    id varchar(80) PRIMARY KEY,
    evaluation_id varchar(80),
    plan_target_id varchar(80) NOT NULL,
    expected_flow_state_id varchar(80),
    trusted_telemetry_reading_id varchar(80),
    telemetry_point_id varchar(80),
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160),
    actual_value numeric(18,6),
    expected_value numeric(18,6),
    difference_value numeric(18,6),
    difference_percent numeric(10,4),
    unit_id varchar(80),
    severity varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    detected_at timestamp with time zone NOT NULL,
    resolved_at timestamp with time zone,
    reason_code varchar(80),
    reason_message text
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_evaluation_id ON hidra_monitoring_plan_actual_deviation (evaluation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_plan_target_id ON hidra_monitoring_plan_actual_deviation (plan_target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_expected_flow_stat ON hidra_monitoring_plan_actual_deviation (expected_flow_state_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_trusted_telemetry_ ON hidra_monitoring_plan_actual_deviation (trusted_telemetry_reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_telemetry_point_id ON hidra_monitoring_plan_actual_deviation (telemetry_point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_topology_asset_id ON hidra_monitoring_plan_actual_deviation (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_unit_id ON hidra_monitoring_plan_actual_deviation (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_plan_actual_deviation_status ON hidra_monitoring_plan_actual_deviation (status);

CREATE TABLE IF NOT EXISTS hidra_monitoring_risk_signal (
    id varchar(80) PRIMARY KEY,
    source_deviation_id varchar(80),
    source_evaluation_id varchar(80),
    topology_asset_type varchar(160),
    topology_asset_id varchar(80),
    risk_type_id varchar(80) NOT NULL,
    risk_level varchar(40) NOT NULL,
    risk_score numeric(10,4),
    signal_payload jsonb,
    status varchar(40) NOT NULL,
    raised_at timestamp with time zone NOT NULL,
    expires_at timestamp with time zone,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_risk_signal_source_deviation_id ON hidra_monitoring_risk_signal (source_deviation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_risk_signal_source_evaluation_id ON hidra_monitoring_risk_signal (source_evaluation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_risk_signal_topology_asset_id ON hidra_monitoring_risk_signal (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_risk_signal_risk_type_id ON hidra_monitoring_risk_signal (risk_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_risk_signal_status ON hidra_monitoring_risk_signal (status);
CREATE INDEX IF NOT EXISTS ix_hidra_monitoring_risk_signal_correlation_id ON hidra_monitoring_risk_signal (correlation_id);
