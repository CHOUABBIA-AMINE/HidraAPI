-- HIDRA telemetry module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity
-- Module: telemetry

CREATE TABLE IF NOT EXISTS hidra_telemetry_type_catalog (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_type_catalog_code ON hidra_telemetry_type_catalog (code);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_type_catalog_active ON hidra_telemetry_type_catalog (active);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_type_catalog_created_at ON hidra_telemetry_type_catalog (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_type_catalog_updated_at ON hidra_telemetry_type_catalog (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_type_translation (
    id varchar(80) PRIMARY KEY,
    type_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_type_translation_type_id ON hidra_telemetry_type_translation (type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_type_translation_created_at ON hidra_telemetry_type_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_type_translation_updated_at ON hidra_telemetry_type_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_device (
    id varchar(80) PRIMARY KEY,
    source_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    device_type_id varchar(80) NOT NULL,
    external_reference varchar(500),
    manufacturer_party_id varchar(80),
    model_reference varchar(160),
    serial_number varchar(160),
    firmware_version varchar(160),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_device_source_id ON hidra_telemetry_device (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_device_code ON hidra_telemetry_device (code);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_device_device_type_id ON hidra_telemetry_device (device_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_device_manufacturer_party_id ON hidra_telemetry_device (manufacturer_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_device_status ON hidra_telemetry_device (status);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_device_created_at ON hidra_telemetry_device (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_device_updated_at ON hidra_telemetry_device (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_external_tag_mapping (
    id varchar(80) PRIMARY KEY,
    source_id varchar(80) NOT NULL,
    device_id varchar(80),
    point_id varchar(80) NOT NULL,
    external_tag_name varchar(500) NOT NULL,
    external_tag_id varchar(500),
    external_namespace varchar(500),
    external_data_type varchar(80),
    mapping_mode varchar(40) NOT NULL,
    transformation_expression text,
    active boolean NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_external_tag_mapping_source_id ON hidra_telemetry_external_tag_mapping (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_external_tag_mapping_device_id ON hidra_telemetry_external_tag_mapping (device_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_external_tag_mapping_point_id ON hidra_telemetry_external_tag_mapping (point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_external_tag_mapping_external_tag_id ON hidra_telemetry_external_tag_mapping (external_tag_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_external_tag_mapping_active ON hidra_telemetry_external_tag_mapping (active);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_external_tag_mapping_created_at ON hidra_telemetry_external_tag_mapping (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_external_tag_mapping_updated_at ON hidra_telemetry_external_tag_mapping (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_ingestion_batch (
    id varchar(80) PRIMARY KEY,
    source_id varchar(80) NOT NULL,
    endpoint_id varchar(80),
    correlation_id varchar(80),
    status varchar(40) NOT NULL,
    received_count integer NOT NULL,
    accepted_count integer NOT NULL,
    rejected_count integer NOT NULL,
    duplicate_count integer NOT NULL,
    quarantined_count integer NOT NULL,
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    failure_reason text,
    created_by_actor_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_ingestion_batch_source_id ON hidra_telemetry_ingestion_batch (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_ingestion_batch_endpoint_id ON hidra_telemetry_ingestion_batch (endpoint_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_ingestion_batch_correlation_id ON hidra_telemetry_ingestion_batch (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_ingestion_batch_status ON hidra_telemetry_ingestion_batch (status);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_ingestion_batch_created_by_actor_id ON hidra_telemetry_ingestion_batch (created_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_telemetry_point_binding (
    id varchar(80) PRIMARY KEY,
    point_id varchar(80) NOT NULL,
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(80) NOT NULL,
    topology_asset_name_snapshot varchar(500),
    topology_snapshot_id varchar(80),
    binding_role_id varchar(80) NOT NULL,
    active boolean NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_binding_point_id ON hidra_telemetry_point_binding (point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_binding_topology_asset_id ON hidra_telemetry_point_binding (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_binding_topology_snapshot_id ON hidra_telemetry_point_binding (topology_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_binding_binding_role_id ON hidra_telemetry_point_binding (binding_role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_binding_active ON hidra_telemetry_point_binding (active);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_binding_created_at ON hidra_telemetry_point_binding (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_binding_updated_at ON hidra_telemetry_point_binding (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_point (
    id varchar(80) PRIMARY KEY,
    device_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    point_type_id varchar(80) NOT NULL,
    signal_type_id varchar(80) NOT NULL,
    unit_id varchar(80),
    default_aggregation_method_id varchar(80),
    sampling_period_seconds integer,
    external_reference varchar(500),
    deadband_value numeric(18,6),
    min_operational_value numeric(18,6),
    max_operational_value numeric(18,6),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_device_id ON hidra_telemetry_point (device_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_code ON hidra_telemetry_point (code);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_point_type_id ON hidra_telemetry_point (point_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_signal_type_id ON hidra_telemetry_point (signal_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_unit_id ON hidra_telemetry_point (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_default_aggregation_method_id ON hidra_telemetry_point (default_aggregation_method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_status ON hidra_telemetry_point (status);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_created_at ON hidra_telemetry_point (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_updated_at ON hidra_telemetry_point (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_point_state_snapshot (
    point_id varchar(80) PRIMARY KEY,
    last_reading_id varchar(80),
    last_trusted_reading_id varchar(80),
    last_numeric_value numeric(18,6),
    last_text_value text,
    last_boolean_value boolean,
    last_quality_code_id varchar(80),
    last_source_timestamp timestamp with time zone,
    last_received_at timestamp with time zone,
    communication_state varchar(40) NOT NULL,
    stale_since timestamp with time zone,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_state_snapshot_last_reading_id ON hidra_telemetry_point_state_snapshot (last_reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_state_snapshot_last_trusted_reading ON hidra_telemetry_point_state_snapshot (last_trusted_reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_state_snapshot_last_quality_code_id ON hidra_telemetry_point_state_snapshot (last_quality_code_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_point_state_snapshot_updated_at ON hidra_telemetry_point_state_snapshot (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_quality_assessment (
    id varchar(80) PRIMARY KEY,
    reading_id varchar(80) NOT NULL,
    point_id varchar(80) NOT NULL,
    assessment_status varchar(40) NOT NULL,
    input_quality_code_id varchar(80) NOT NULL,
    resolved_quality_code_id varchar(80) NOT NULL,
    trust_level varchar(40) NOT NULL,
    validation_rule_id varchar(80),
    reason_code varchar(80),
    reason_message text,
    assessed_at timestamp with time zone NOT NULL,
    assessed_by_actor_id varchar(80),
    workflow_instance_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quality_assessment_reading_id ON hidra_telemetry_quality_assessment (reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quality_assessment_point_id ON hidra_telemetry_quality_assessment (point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quality_assessment_input_quality_code_id ON hidra_telemetry_quality_assessment (input_quality_code_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quality_assessment_resolved_quality_code_ ON hidra_telemetry_quality_assessment (resolved_quality_code_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quality_assessment_validation_rule_id ON hidra_telemetry_quality_assessment (validation_rule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quality_assessment_assessed_by_actor_id ON hidra_telemetry_quality_assessment (assessed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quality_assessment_workflow_instance_id ON hidra_telemetry_quality_assessment (workflow_instance_id);

CREATE TABLE IF NOT EXISTS hidra_telemetry_quarantine_record (
    id varchar(80) PRIMARY KEY,
    source_id varchar(80) NOT NULL,
    endpoint_id varchar(80),
    ingestion_batch_id varchar(80),
    external_tag_name varchar(500),
    source_timestamp timestamp with time zone,
    received_at timestamp with time zone NOT NULL,
    reason_code varchar(80) NOT NULL,
    reason_message text,
    raw_payload jsonb,
    raw_payload_hash varchar(160),
    status varchar(40) NOT NULL,
    resolved_reading_id varchar(80),
    resolved_at timestamp with time zone,
    resolved_by_actor_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quarantine_record_source_id ON hidra_telemetry_quarantine_record (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quarantine_record_endpoint_id ON hidra_telemetry_quarantine_record (endpoint_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quarantine_record_ingestion_batch_id ON hidra_telemetry_quarantine_record (ingestion_batch_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quarantine_record_status ON hidra_telemetry_quarantine_record (status);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quarantine_record_resolved_reading_id ON hidra_telemetry_quarantine_record (resolved_reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_quarantine_record_resolved_by_actor_id ON hidra_telemetry_quarantine_record (resolved_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_telemetry_reading (
    id varchar(80) PRIMARY KEY,
    point_id varchar(80) NOT NULL,
    numeric_value numeric(18,6),
    text_value text,
    boolean_value boolean,
    quality_code_id varchar(80) NOT NULL,
    source_timestamp timestamp with time zone NOT NULL,
    received_at timestamp with time zone NOT NULL,
    state varchar(40) NOT NULL,
    ingestion_batch_id varchar(80),
    correlation_id varchar(80),
    rejection_reason text,
    source_sequence_number varchar(160),
    external_tag_mapping_id varchar(80),
    raw_payload_hash varchar(160),
    created_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_reading_point_id ON hidra_telemetry_reading (point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_reading_quality_code_id ON hidra_telemetry_reading (quality_code_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_reading_ingestion_batch_id ON hidra_telemetry_reading (ingestion_batch_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_reading_correlation_id ON hidra_telemetry_reading (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_reading_external_tag_mapping_id ON hidra_telemetry_reading (external_tag_mapping_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_reading_created_at ON hidra_telemetry_reading (created_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_source_endpoint (
    id varchar(80) PRIMARY KEY,
    source_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    endpoint_role varchar(80) NOT NULL,
    protocol_id varchar(80) NOT NULL,
    endpoint_uri text,
    host varchar(160),
    port integer,
    path_or_topic varchar(500),
    polling_interval_seconds integer,
    timeout_seconds integer,
    credential_reference varchar(80),
    connection_options_json jsonb,
    active boolean NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_endpoint_source_id ON hidra_telemetry_source_endpoint (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_endpoint_code ON hidra_telemetry_source_endpoint (code);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_endpoint_protocol_id ON hidra_telemetry_source_endpoint (protocol_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_endpoint_active ON hidra_telemetry_source_endpoint (active);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_endpoint_created_at ON hidra_telemetry_source_endpoint (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_endpoint_updated_at ON hidra_telemetry_source_endpoint (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_source (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    source_type_id varchar(80) NOT NULL,
    protocol_id varchar(80) NOT NULL,
    endpoint_uri text,
    external_reference varchar(500),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_code ON hidra_telemetry_source (code);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_source_type_id ON hidra_telemetry_source (source_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_protocol_id ON hidra_telemetry_source (protocol_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_status ON hidra_telemetry_source (status);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_created_at ON hidra_telemetry_source (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_source_updated_at ON hidra_telemetry_source (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_unit (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    symbol varchar(160) NOT NULL,
    dimension varchar(80) NOT NULL,
    base_unit_id varchar(80),
    to_base_factor numeric(18,8),
    to_base_offset numeric(18,8),
    display_precision integer,
    active boolean NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_unit_code ON hidra_telemetry_unit (code);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_unit_base_unit_id ON hidra_telemetry_unit (base_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_unit_active ON hidra_telemetry_unit (active);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_unit_created_at ON hidra_telemetry_unit (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_unit_updated_at ON hidra_telemetry_unit (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_validation_rule (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name varchar(160) NOT NULL,
    scope_type varchar(80) NOT NULL,
    scope_reference_id varchar(80),
    rule_type_id varchar(80) NOT NULL,
    severity varchar(40) NOT NULL,
    action_on_failure varchar(40) NOT NULL,
    expression text,
    configuration_json jsonb,
    active boolean NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_validation_rule_code ON hidra_telemetry_validation_rule (code);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_validation_rule_scope_reference_id ON hidra_telemetry_validation_rule (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_validation_rule_rule_type_id ON hidra_telemetry_validation_rule (rule_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_validation_rule_active ON hidra_telemetry_validation_rule (active);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_validation_rule_created_at ON hidra_telemetry_validation_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_validation_rule_updated_at ON hidra_telemetry_validation_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_telemetry_trusted_reading (
    id varchar(80) PRIMARY KEY,
    reading_id varchar(80) NOT NULL,
    point_id varchar(80) NOT NULL,
    numeric_value numeric(18,6),
    text_value text,
    boolean_value boolean,
    unit_id varchar(80),
    quality_code_id varchar(80) NOT NULL,
    trust_level varchar(40) NOT NULL,
    source_timestamp timestamp with time zone NOT NULL,
    trusted_at timestamp with time zone NOT NULL,
    quality_assessment_id varchar(80) NOT NULL,
    topology_asset_type_code varchar(80),
    topology_asset_id varchar(80),
    topology_asset_code varchar(80),
    topology_snapshot_id varchar(80),
    ingestion_batch_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_reading_id ON hidra_telemetry_trusted_reading (reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_point_id ON hidra_telemetry_trusted_reading (point_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_unit_id ON hidra_telemetry_trusted_reading (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_quality_code_id ON hidra_telemetry_trusted_reading (quality_code_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_quality_assessment_id ON hidra_telemetry_trusted_reading (quality_assessment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_topology_asset_id ON hidra_telemetry_trusted_reading (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_topology_snapshot_id ON hidra_telemetry_trusted_reading (topology_snapshot_id);
CREATE INDEX IF NOT EXISTS ix_hidra_telemetry_trusted_reading_ingestion_batch_id ON hidra_telemetry_trusted_reading (ingestion_batch_id);
