-- HIDRA integration module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity
-- Module: integration

CREATE TABLE IF NOT EXISTS hidra_integration_connector_instance (
    id varchar(80) PRIMARY KEY,
    external_system_id varchar(80) NOT NULL,
    endpoint_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    connector_type_id varchar(80) NOT NULL,
    connector_implementation varchar(255) NOT NULL,
    direction varchar(30) NOT NULL,
    configuration_json jsonb,
    max_concurrency integer,
    active boolean NOT NULL,
    health_status varchar(40),
    last_health_check_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_connector_instance_external_system_id ON hidra_integration_connector_instance (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_connector_instance_endpoint_id ON hidra_integration_connector_instance (endpoint_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_connector_instance_code ON hidra_integration_connector_instance (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_connector_instance_connector_type_id ON hidra_integration_connector_instance (connector_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_connector_instance_active ON hidra_integration_connector_instance (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_connector_instance_created_at ON hidra_integration_connector_instance (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_connector_instance_updated_at ON hidra_integration_connector_instance (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_external_endpoint (
    id varchar(80) PRIMARY KEY,
    external_system_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    endpoint_type_id varchar(80) NOT NULL,
    direction varchar(30) NOT NULL,
    endpoint_uri varchar(1000),
    host varchar(255),
    port integer,
    path_or_topic varchar(500),
    protocol_id varchar(80) NOT NULL,
    polling_interval_seconds integer,
    timeout_seconds integer,
    credential_reference varchar(255),
    tls_required boolean NOT NULL,
    active boolean NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_endpoint_external_system_id ON hidra_integration_external_endpoint (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_endpoint_code ON hidra_integration_external_endpoint (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_endpoint_endpoint_type_id ON hidra_integration_external_endpoint (endpoint_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_endpoint_protocol_id ON hidra_integration_external_endpoint (protocol_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_endpoint_active ON hidra_integration_external_endpoint (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_endpoint_created_at ON hidra_integration_external_endpoint (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_endpoint_updated_at ON hidra_integration_external_endpoint (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_external_object_reference (
    id varchar(80) PRIMARY KEY,
    external_system_id varchar(80) NOT NULL,
    external_object_type varchar(120) NOT NULL,
    external_object_id varchar(255) NOT NULL,
    external_object_code varchar(255),
    target_module varchar(80) NOT NULL,
    target_type_code varchar(120) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_code_snapshot varchar(120),
    target_label_snapshot varchar(240),
    confidence_level varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_object_reference_external_syst ON hidra_integration_external_object_reference (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_object_reference_external_obje ON hidra_integration_external_object_reference (external_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_object_reference_target_id ON hidra_integration_external_object_reference (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_object_reference_status ON hidra_integration_external_object_reference (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_object_reference_created_at ON hidra_integration_external_object_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_object_reference_updated_at ON hidra_integration_external_object_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_external_system (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    system_type_id varchar(80) NOT NULL,
    owner_organization_unit_id varchar(80),
    environment varchar(40) NOT NULL,
    criticality varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    description varchar(1000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_system_code ON hidra_integration_external_system (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_system_system_type_id ON hidra_integration_external_system (system_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_system_owner_organization_unit ON hidra_integration_external_system (owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_system_status ON hidra_integration_external_system (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_system_created_at ON hidra_integration_external_system (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_external_system_updated_at ON hidra_integration_external_system (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_catalog_entry_code ON hidra_integration_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_catalog_entry_active ON hidra_integration_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_catalog_entry_created_at ON hidra_integration_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_catalog_entry_updated_at ON hidra_integration_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_catalog_translation_catalog_entry_id ON hidra_integration_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_catalog_translation_created_at ON hidra_integration_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_catalog_translation_updated_at ON hidra_integration_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_data_contract (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_fr varchar(160) NOT NULL,
    name_ar varchar(160),
    name_en varchar(160),
    contract_type_id varchar(80) NOT NULL,
    payload_format_id varchar(80) NOT NULL,
    owning_target_module varchar(80),
    description varchar(1000),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_data_contract_code ON hidra_integration_data_contract (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_data_contract_contract_type_id ON hidra_integration_data_contract (contract_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_data_contract_payload_format_id ON hidra_integration_data_contract (payload_format_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_data_contract_status ON hidra_integration_data_contract (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_data_contract_created_at ON hidra_integration_data_contract (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_data_contract_updated_at ON hidra_integration_data_contract (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_dead_letter_record (
    id varchar(80) PRIMARY KEY,
    external_system_id varchar(80) NOT NULL,
    job_run_id varchar(80),
    exchange_message_id varchar(80),
    inbound_record_id varchar(80),
    outbound_record_id varchar(80),
    target_module varchar(80),
    failure_stage varchar(80) NOT NULL,
    reason_code varchar(120) NOT NULL,
    reason_message varchar(2000) NOT NULL,
    payload_hash varchar(128),
    sanitized_payload jsonb,
    status varchar(40) NOT NULL,
    resolved_by_actor_id varchar(80),
    resolved_at timestamp with time zone,
    resolution_comment varchar(2000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_external_system_id ON hidra_integration_dead_letter_record (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_job_run_id ON hidra_integration_dead_letter_record (job_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_exchange_message_id ON hidra_integration_dead_letter_record (exchange_message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_inbound_record_id ON hidra_integration_dead_letter_record (inbound_record_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_outbound_record_id ON hidra_integration_dead_letter_record (outbound_record_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_status ON hidra_integration_dead_letter_record (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_resolved_by_actor_id ON hidra_integration_dead_letter_record (resolved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_created_at ON hidra_integration_dead_letter_record (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_dead_letter_record_updated_at ON hidra_integration_dead_letter_record (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_exchange_message (
    id varchar(80) PRIMARY KEY,
    job_run_id varchar(80),
    external_system_id varchar(80) NOT NULL,
    endpoint_id varchar(80),
    direction varchar(30) NOT NULL,
    message_type_id varchar(80) NOT NULL,
    external_message_id varchar(255),
    payload_format_id varchar(80) NOT NULL,
    payload_storage_mode varchar(40) NOT NULL,
    payload_sanitized jsonb,
    payload_reference varchar(1000),
    payload_hash varchar(128) NOT NULL,
    content_length_bytes bigint,
    received_or_sent_at timestamp with time zone NOT NULL,
    correlation_id varchar(120),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_job_run_id ON hidra_integration_exchange_message (job_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_external_system_id ON hidra_integration_exchange_message (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_endpoint_id ON hidra_integration_exchange_message (endpoint_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_message_type_id ON hidra_integration_exchange_message (message_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_external_message_id ON hidra_integration_exchange_message (external_message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_payload_format_id ON hidra_integration_exchange_message (payload_format_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_correlation_id ON hidra_integration_exchange_message (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_status ON hidra_integration_exchange_message (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_exchange_message_created_at ON hidra_integration_exchange_message (created_at);

CREATE TABLE IF NOT EXISTS hidra_integration_field_mapping (
    id varchar(80) PRIMARY KEY,
    mapping_profile_id varchar(80) NOT NULL,
    source_path varchar(500) NOT NULL,
    target_path varchar(500) NOT NULL,
    data_type varchar(50) NOT NULL,
    required boolean NOT NULL,
    default_value varchar(1000),
    unit_code varchar(80),
    transformation_rule_id varchar(80),
    display_order integer NOT NULL,
    active boolean NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_field_mapping_mapping_profile_id ON hidra_integration_field_mapping (mapping_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_field_mapping_transformation_rule_id ON hidra_integration_field_mapping (transformation_rule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_field_mapping_active ON hidra_integration_field_mapping (active);

CREATE TABLE IF NOT EXISTS hidra_integration_health_snapshot (
    id varchar(80) PRIMARY KEY,
    external_system_id varchar(80) NOT NULL,
    endpoint_id varchar(80),
    connector_instance_id varchar(80),
    job_definition_id varchar(80),
    health_status varchar(40) NOT NULL,
    latency_ms bigint,
    last_success_at timestamp with time zone,
    last_failure_at timestamp with time zone,
    error_code varchar(120),
    error_message varchar(2000),
    captured_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_health_snapshot_external_system_id ON hidra_integration_health_snapshot (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_health_snapshot_endpoint_id ON hidra_integration_health_snapshot (endpoint_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_health_snapshot_connector_instance_id ON hidra_integration_health_snapshot (connector_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_health_snapshot_job_definition_id ON hidra_integration_health_snapshot (job_definition_id);

CREATE TABLE IF NOT EXISTS hidra_integration_inbound_record (
    id varchar(80) PRIMARY KEY,
    exchange_message_id varchar(80) NOT NULL,
    job_run_id varchar(80),
    record_sequence bigint NOT NULL,
    mapping_profile_id varchar(80),
    target_module varchar(80) NOT NULL,
    target_type_code varchar(120) NOT NULL,
    target_id varchar(120),
    target_code_snapshot varchar(120),
    mapped_payload jsonb,
    validation_status varchar(40) NOT NULL,
    submission_status varchar(40) NOT NULL,
    target_response_code varchar(120),
    target_response_message varchar(2000),
    error_code varchar(120),
    error_message varchar(2000),
    created_at timestamp with time zone NOT NULL,
    submitted_at timestamp with time zone,
    completed_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_inbound_record_exchange_message_id ON hidra_integration_inbound_record (exchange_message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_inbound_record_job_run_id ON hidra_integration_inbound_record (job_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_inbound_record_mapping_profile_id ON hidra_integration_inbound_record (mapping_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_inbound_record_target_id ON hidra_integration_inbound_record (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_inbound_record_created_at ON hidra_integration_inbound_record (created_at);

CREATE TABLE IF NOT EXISTS hidra_integration_job_definition (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_fr varchar(160) NOT NULL,
    name_ar varchar(160),
    name_en varchar(160),
    connector_instance_id varchar(80) NOT NULL,
    mapping_profile_id varchar(80),
    job_type_id varchar(80) NOT NULL,
    direction varchar(30) NOT NULL,
    target_module varchar(80),
    schedule_expression varchar(255),
    manual_run_allowed boolean NOT NULL,
    retry_policy_id varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_code ON hidra_integration_job_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_connector_instance_id ON hidra_integration_job_definition (connector_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_mapping_profile_id ON hidra_integration_job_definition (mapping_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_job_type_id ON hidra_integration_job_definition (job_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_retry_policy_id ON hidra_integration_job_definition (retry_policy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_active ON hidra_integration_job_definition (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_created_at ON hidra_integration_job_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_definition_updated_at ON hidra_integration_job_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_job_run (
    id varchar(80) PRIMARY KEY,
    job_definition_id varchar(80) NOT NULL,
    run_number bigint NOT NULL,
    trigger_type varchar(40) NOT NULL,
    triggered_by_actor_id varchar(80),
    status varchar(40) NOT NULL,
    correlation_id varchar(120),
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    received_count bigint NOT NULL,
    mapped_count bigint NOT NULL,
    accepted_count bigint NOT NULL,
    rejected_count bigint NOT NULL,
    dead_letter_count bigint NOT NULL,
    retry_count bigint NOT NULL,
    failure_reason varchar(2000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_job_definition_id ON hidra_integration_job_run (job_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_triggered_by_actor_id ON hidra_integration_job_run (triggered_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_status ON hidra_integration_job_run (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_correlation_id ON hidra_integration_job_run (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_created_at ON hidra_integration_job_run (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_updated_at ON hidra_integration_job_run (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_job_run_step (
    id varchar(80) PRIMARY KEY,
    job_run_id varchar(80) NOT NULL,
    step_name varchar(120) NOT NULL,
    status varchar(40) NOT NULL,
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    processed_count bigint NOT NULL,
    error_count bigint NOT NULL,
    details_json jsonb
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_step_job_run_id ON hidra_integration_job_run_step (job_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_job_run_step_status ON hidra_integration_job_run_step (status);

CREATE TABLE IF NOT EXISTS hidra_integration_mapping_profile (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    external_system_id varchar(80) NOT NULL,
    data_contract_id varchar(80) NOT NULL,
    schema_version_id varchar(80),
    target_module varchar(80) NOT NULL,
    target_type_code varchar(120) NOT NULL,
    direction varchar(30) NOT NULL,
    status varchar(40) NOT NULL,
    validation_mode varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_mapping_profile_code ON hidra_integration_mapping_profile (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_mapping_profile_external_system_id ON hidra_integration_mapping_profile (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_mapping_profile_data_contract_id ON hidra_integration_mapping_profile (data_contract_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_mapping_profile_schema_version_id ON hidra_integration_mapping_profile (schema_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_mapping_profile_status ON hidra_integration_mapping_profile (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_mapping_profile_created_at ON hidra_integration_mapping_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_mapping_profile_updated_at ON hidra_integration_mapping_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_outbound_record (
    id varchar(80) PRIMARY KEY,
    exchange_message_id varchar(80),
    job_run_id varchar(80),
    source_module varchar(80) NOT NULL,
    source_type_code varchar(120) NOT NULL,
    source_id varchar(120) NOT NULL,
    source_code_snapshot varchar(120),
    source_label_snapshot varchar(240),
    mapping_profile_id varchar(80),
    outbound_payload jsonb,
    external_system_id varchar(80) NOT NULL,
    external_object_id varchar(255),
    status varchar(40) NOT NULL,
    error_code varchar(120),
    error_message varchar(2000),
    created_at timestamp with time zone NOT NULL,
    sent_at timestamp with time zone,
    acknowledged_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_exchange_message_id ON hidra_integration_outbound_record (exchange_message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_job_run_id ON hidra_integration_outbound_record (job_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_source_id ON hidra_integration_outbound_record (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_mapping_profile_id ON hidra_integration_outbound_record (mapping_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_external_system_id ON hidra_integration_outbound_record (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_external_object_id ON hidra_integration_outbound_record (external_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_status ON hidra_integration_outbound_record (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_outbound_record_created_at ON hidra_integration_outbound_record (created_at);

CREATE TABLE IF NOT EXISTS hidra_integration_reconciliation_issue (
    id varchar(80) PRIMARY KEY,
    reconciliation_run_id varchar(80) NOT NULL,
    issue_type varchar(80) NOT NULL,
    external_object_type varchar(120),
    external_object_id varchar(255),
    target_module varchar(80),
    target_type_code varchar(120),
    target_id varchar(120),
    field_path varchar(500),
    hidra_value_snapshot varchar(1000),
    external_value_snapshot varchar(1000),
    severity varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    resolution_comment varchar(2000),
    created_at timestamp with time zone NOT NULL,
    resolved_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_issue_reconciliation_run ON hidra_integration_reconciliation_issue (reconciliation_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_issue_external_object_id ON hidra_integration_reconciliation_issue (external_object_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_issue_target_id ON hidra_integration_reconciliation_issue (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_issue_status ON hidra_integration_reconciliation_issue (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_issue_created_at ON hidra_integration_reconciliation_issue (created_at);

CREATE TABLE IF NOT EXISTS hidra_integration_reconciliation_run (
    id varchar(80) PRIMARY KEY,
    external_system_id varchar(80) NOT NULL,
    job_definition_id varchar(80),
    target_module varchar(80) NOT NULL,
    target_type_code varchar(120) NOT NULL,
    reconciliation_period_start timestamp with time zone,
    reconciliation_period_end timestamp with time zone,
    status varchar(40) NOT NULL,
    hidra_count bigint NOT NULL,
    external_count bigint NOT NULL,
    matched_count bigint NOT NULL,
    missing_in_hidra_count bigint NOT NULL,
    missing_externally_count bigint NOT NULL,
    mismatch_count bigint NOT NULL,
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_run_external_system_id ON hidra_integration_reconciliation_run (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_run_job_definition_id ON hidra_integration_reconciliation_run (job_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_run_status ON hidra_integration_reconciliation_run (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_reconciliation_run_created_at ON hidra_integration_reconciliation_run (created_at);

CREATE TABLE IF NOT EXISTS hidra_integration_retry_attempt (
    id varchar(80) PRIMARY KEY,
    retry_policy_id varchar(80),
    target_record_type varchar(80) NOT NULL,
    target_record_id varchar(120) NOT NULL,
    attempt_number integer NOT NULL,
    status varchar(40) NOT NULL,
    scheduled_at timestamp with time zone NOT NULL,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    error_code varchar(120),
    error_message varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_attempt_retry_policy_id ON hidra_integration_retry_attempt (retry_policy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_attempt_target_record_id ON hidra_integration_retry_attempt (target_record_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_attempt_status ON hidra_integration_retry_attempt (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_attempt_created_at ON hidra_integration_retry_attempt (created_at);

CREATE TABLE IF NOT EXISTS hidra_integration_retry_policy (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    max_attempts integer NOT NULL,
    initial_delay_seconds integer NOT NULL,
    max_delay_seconds integer NOT NULL,
    backoff_strategy varchar(40) NOT NULL,
    retryable_error_codes jsonb,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_policy_code ON hidra_integration_retry_policy (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_policy_active ON hidra_integration_retry_policy (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_policy_created_at ON hidra_integration_retry_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_retry_policy_updated_at ON hidra_integration_retry_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_schema_version (
    id varchar(80) PRIMARY KEY,
    data_contract_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    schema_definition jsonb NOT NULL,
    checksum varchar(128) NOT NULL,
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_schema_version_data_contract_id ON hidra_integration_schema_version (data_contract_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_schema_version_status ON hidra_integration_schema_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_schema_version_created_at ON hidra_integration_schema_version (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_schema_version_updated_at ON hidra_integration_schema_version (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_sync_cursor (
    id varchar(80) PRIMARY KEY,
    job_definition_id varchar(80) NOT NULL,
    external_system_id varchar(80) NOT NULL,
    cursor_name varchar(120) NOT NULL,
    cursor_value varchar(1000),
    cursor_payload jsonb,
    last_successful_run_id varchar(80),
    last_successful_at timestamp with time zone,
    status varchar(40) NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_sync_cursor_job_definition_id ON hidra_integration_sync_cursor (job_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_sync_cursor_external_system_id ON hidra_integration_sync_cursor (external_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_sync_cursor_last_successful_run_id ON hidra_integration_sync_cursor (last_successful_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_sync_cursor_status ON hidra_integration_sync_cursor (status);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_sync_cursor_updated_at ON hidra_integration_sync_cursor (updated_at);

CREATE TABLE IF NOT EXISTS hidra_integration_transformation_rule (
    id varchar(80) PRIMARY KEY,
    mapping_profile_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    rule_type_id varchar(80) NOT NULL,
    expression varchar(2000),
    configuration_json jsonb,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_transformation_rule_mapping_profile_id ON hidra_integration_transformation_rule (mapping_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_transformation_rule_code ON hidra_integration_transformation_rule (code);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_transformation_rule_rule_type_id ON hidra_integration_transformation_rule (rule_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_transformation_rule_active ON hidra_integration_transformation_rule (active);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_transformation_rule_created_at ON hidra_integration_transformation_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_integration_transformation_rule_updated_at ON hidra_integration_transformation_rule (updated_at);
