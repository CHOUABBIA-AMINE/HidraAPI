-- HIDRA reporting module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity
-- Module: reporting

CREATE TABLE IF NOT EXISTS hidra_reporting_access_policy (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    scope_type varchar(40) NOT NULL,
    scope_reference_id varchar(120),
    permission_code varchar(120) NOT NULL,
    restricted boolean NOT NULL,
    mask_sensitive_values boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_access_policy_report_definition_id ON hidra_reporting_access_policy (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_access_policy_scope_reference_id ON hidra_reporting_access_policy (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_access_policy_created_at ON hidra_reporting_access_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_access_policy_updated_at ON hidra_reporting_access_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_catalog_entry_code ON hidra_reporting_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_catalog_entry_active ON hidra_reporting_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_catalog_entry_created_at ON hidra_reporting_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_catalog_entry_updated_at ON hidra_reporting_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_catalog_translation_catalog_entry_id ON hidra_reporting_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_catalog_translation_created_at ON hidra_reporting_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_catalog_translation_updated_at ON hidra_reporting_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_chart_result (
    id varchar(80) PRIMARY KEY,
    report_section_result_id varchar(80) NOT NULL,
    chart_type varchar(40) NOT NULL,
    series_schema_json jsonb,
    image_reference varchar(500),
    interactive_spec_reference varchar(500),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_chart_result_report_section_result_id ON hidra_reporting_chart_result (report_section_result_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_chart_result_created_at ON hidra_reporting_chart_result (created_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_data_source_binding (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    source_module varchar(80) NOT NULL,
    source_type varchar(40) NOT NULL,
    source_name varchar(160) NOT NULL,
    source_contract_version varchar(80),
    required boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_data_source_binding_report_definition_id ON hidra_reporting_data_source_binding (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_data_source_binding_created_at ON hidra_reporting_data_source_binding (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_data_source_binding_updated_at ON hidra_reporting_data_source_binding (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_report_definition (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    report_category_id varchar(80) NOT NULL,
    owner_module varchar(80) NOT NULL,
    description varchar(1000),
    active boolean NOT NULL,
    current_template_version_id varchar(80),
    requires_approval boolean NOT NULL,
    restricted boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_definition_code ON hidra_reporting_report_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_definition_report_category_id ON hidra_reporting_report_definition (report_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_definition_active ON hidra_reporting_report_definition (active);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_definition_current_template_versio ON hidra_reporting_report_definition (current_template_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_definition_created_at ON hidra_reporting_report_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_definition_updated_at ON hidra_reporting_report_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_distribution_record (
    id varchar(80) PRIMARY KEY,
    report_publication_id varchar(80) NOT NULL,
    report_output_artifact_id varchar(80) NOT NULL,
    target_type varchar(40) NOT NULL,
    target_reference varchar(255) NOT NULL,
    notification_request_id varchar(120),
    integration_outbound_record_id varchar(120),
    status varchar(40) NOT NULL,
    requested_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    failure_reason varchar(2000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_record_report_publication_id ON hidra_reporting_distribution_record (report_publication_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_record_report_output_artifac ON hidra_reporting_distribution_record (report_output_artifact_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_record_notification_request_ ON hidra_reporting_distribution_record (notification_request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_record_integration_outbound_ ON hidra_reporting_distribution_record (integration_outbound_record_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_record_status ON hidra_reporting_distribution_record (status);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_record_created_at ON hidra_reporting_distribution_record (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_record_updated_at ON hidra_reporting_distribution_record (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_distribution_target (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    target_type varchar(40) NOT NULL,
    target_reference varchar(255) NOT NULL,
    channel_id varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_target_report_definition_id ON hidra_reporting_distribution_target (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_target_channel_id ON hidra_reporting_distribution_target (channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_target_active ON hidra_reporting_distribution_target (active);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_target_created_at ON hidra_reporting_distribution_target (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_distribution_target_updated_at ON hidra_reporting_distribution_target (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_input_snapshot (
    id varchar(80) PRIMARY KEY,
    report_run_id varchar(80) NOT NULL,
    snapshot_type varchar(40) NOT NULL,
    source_module varchar(80) NOT NULL,
    source_reference_id varchar(120) NOT NULL,
    source_reference_code varchar(120),
    source_reference_label varchar(240),
    source_version varchar(80),
    snapshot_hash varchar(160) NOT NULL,
    captured_at timestamp with time zone NOT NULL,
    metadata_json jsonb
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_input_snapshot_report_run_id ON hidra_reporting_input_snapshot (report_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_input_snapshot_source_reference_id ON hidra_reporting_input_snapshot (source_reference_id);

CREATE TABLE IF NOT EXISTS hidra_reporting_output_artifact (
    id varchar(80) PRIMARY KEY,
    report_run_id varchar(80) NOT NULL,
    artifact_type varchar(40) NOT NULL,
    format varchar(20) NOT NULL,
    file_name varchar(255) NOT NULL,
    mime_type varchar(120) NOT NULL,
    storage_object_reference_id varchar(120),
    document_reference_id varchar(120),
    checksum varchar(160) NOT NULL,
    size_bytes bigint,
    generated_at timestamp with time zone NOT NULL,
    expires_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_output_artifact_report_run_id ON hidra_reporting_output_artifact (report_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_output_artifact_storage_object_reference_ ON hidra_reporting_output_artifact (storage_object_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_output_artifact_document_reference_id ON hidra_reporting_output_artifact (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_output_artifact_created_at ON hidra_reporting_output_artifact (created_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_parameter_definition (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    label_ar varchar(160),
    label_fr varchar(160) NOT NULL,
    label_en varchar(160),
    parameter_type varchar(40) NOT NULL,
    required boolean NOT NULL,
    default_value varchar(1000),
    allowed_values_reference varchar(500),
    validation_expression varchar(1000),
    sort_order integer NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_definition_report_definition_id ON hidra_reporting_parameter_definition (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_definition_code ON hidra_reporting_parameter_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_definition_active ON hidra_reporting_parameter_definition (active);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_definition_created_at ON hidra_reporting_parameter_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_definition_updated_at ON hidra_reporting_parameter_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_parameter_value (
    id varchar(80) PRIMARY KEY,
    report_request_id varchar(80) NOT NULL,
    parameter_definition_id varchar(80) NOT NULL,
    parameter_code varchar(120) NOT NULL,
    value_type varchar(40) NOT NULL,
    value_text varchar(2000),
    value_number numeric(18,6),
    value_boolean boolean,
    value_date date,
    value_date_time timestamp with time zone,
    value_json jsonb,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_value_report_request_id ON hidra_reporting_parameter_value (report_request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_value_parameter_definition_id ON hidra_reporting_parameter_value (parameter_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_parameter_value_created_at ON hidra_reporting_parameter_value (created_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_publication (
    id varchar(80) PRIMARY KEY,
    report_run_id varchar(80) NOT NULL,
    publication_status varchar(40) NOT NULL,
    published_by_actor_id varchar(80),
    published_by_display_name_snapshot varchar(160),
    published_at timestamp with time zone,
    publication_note varchar(2000),
    workflow_reference_id varchar(120),
    audit_reference_id varchar(120),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_publication_report_run_id ON hidra_reporting_publication (report_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_publication_published_by_actor_id ON hidra_reporting_publication (published_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_publication_workflow_reference_id ON hidra_reporting_publication (workflow_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_publication_audit_reference_id ON hidra_reporting_publication (audit_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_publication_created_at ON hidra_reporting_publication (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_publication_updated_at ON hidra_reporting_publication (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_request (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    requested_by_actor_id varchar(80) NOT NULL,
    requested_by_username_snapshot varchar(120),
    requested_by_display_name_snapshot varchar(160),
    requested_by_role_code_snapshot varchar(120),
    organization_unit_id varchar(80),
    organization_unit_name_snapshot varchar(160),
    requested_at timestamp with time zone NOT NULL,
    purpose varchar(1000),
    status varchar(40) NOT NULL,
    correlation_id varchar(120),
    workflow_reference_id varchar(120),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_report_definition_id ON hidra_reporting_request (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_requested_by_actor_id ON hidra_reporting_request (requested_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_organization_unit_id ON hidra_reporting_request (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_status ON hidra_reporting_request (status);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_correlation_id ON hidra_reporting_request (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_workflow_reference_id ON hidra_reporting_request (workflow_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_created_at ON hidra_reporting_request (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_request_updated_at ON hidra_reporting_request (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_run (
    id varchar(80) PRIMARY KEY,
    report_request_id varchar(80) NOT NULL,
    report_definition_id varchar(80) NOT NULL,
    template_version_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    run_mode varchar(40) NOT NULL,
    queued_at timestamp with time zone NOT NULL,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    failed_at timestamp with time zone,
    failure_reason varchar(2000),
    record_count bigint,
    output_count bigint,
    execution_duration_ms bigint,
    correlation_id varchar(120),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_run_report_request_id ON hidra_reporting_run (report_request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_run_report_definition_id ON hidra_reporting_run (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_run_template_version_id ON hidra_reporting_run (template_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_run_status ON hidra_reporting_run (status);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_run_correlation_id ON hidra_reporting_run (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_run_created_at ON hidra_reporting_run (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_run_updated_at ON hidra_reporting_run (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_schedule (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    cron_expression varchar(255) NOT NULL,
    timezone varchar(80) NOT NULL,
    active boolean NOT NULL,
    next_run_at timestamp with time zone,
    last_run_at timestamp with time zone,
    created_by_actor_id varchar(80) NOT NULL,
    created_by_display_name_snapshot varchar(160),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_report_definition_id ON hidra_reporting_schedule (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_code ON hidra_reporting_schedule (code);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_active ON hidra_reporting_schedule (active);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_created_by_actor_id ON hidra_reporting_schedule (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_created_at ON hidra_reporting_schedule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_updated_at ON hidra_reporting_schedule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_schedule_parameter (
    id varchar(80) PRIMARY KEY,
    report_schedule_id varchar(80) NOT NULL,
    parameter_definition_id varchar(80) NOT NULL,
    parameter_code varchar(120) NOT NULL,
    value_type varchar(40) NOT NULL,
    value_json jsonb,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_parameter_report_schedule_id ON hidra_reporting_schedule_parameter (report_schedule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_parameter_parameter_definition_i ON hidra_reporting_schedule_parameter (parameter_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_parameter_created_at ON hidra_reporting_schedule_parameter (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_schedule_parameter_updated_at ON hidra_reporting_schedule_parameter (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_section_definition (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    title_ar varchar(160),
    title_fr varchar(160) NOT NULL,
    title_en varchar(160),
    section_type varchar(40) NOT NULL,
    sort_order integer NOT NULL,
    visible_by_default boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_definition_report_definition_id ON hidra_reporting_section_definition (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_definition_code ON hidra_reporting_section_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_definition_created_at ON hidra_reporting_section_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_definition_updated_at ON hidra_reporting_section_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_section_result (
    id varchar(80) PRIMARY KEY,
    report_run_id varchar(80) NOT NULL,
    section_definition_id varchar(80) NOT NULL,
    section_code varchar(120) NOT NULL,
    status varchar(40) NOT NULL,
    result_reference varchar(500),
    row_count bigint,
    warning_count bigint,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_result_report_run_id ON hidra_reporting_section_result (report_run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_result_section_definition_id ON hidra_reporting_section_result (section_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_result_status ON hidra_reporting_section_result (status);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_section_result_created_at ON hidra_reporting_section_result (created_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_table_result (
    id varchar(80) PRIMARY KEY,
    report_section_result_id varchar(80) NOT NULL,
    column_schema_json jsonb NOT NULL,
    row_count bigint NOT NULL,
    content_reference varchar(500),
    checksum varchar(160),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_table_result_report_section_result_id ON hidra_reporting_table_result (report_section_result_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_table_result_created_at ON hidra_reporting_table_result (created_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_report_template (
    id varchar(80) PRIMARY KEY,
    report_definition_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    template_engine varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_report_definition_id ON hidra_reporting_report_template (report_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_code ON hidra_reporting_report_template (code);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_active ON hidra_reporting_report_template (active);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_created_at ON hidra_reporting_report_template (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_updated_at ON hidra_reporting_report_template (updated_at);

CREATE TABLE IF NOT EXISTS hidra_reporting_report_template_version (
    id varchar(80) PRIMARY KEY,
    report_template_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    status varchar(40) NOT NULL,
    layout_content_reference varchar(500) NOT NULL,
    style_reference varchar(500),
    checksum varchar(160) NOT NULL,
    created_by_actor_id varchar(80),
    created_by_display_name_snapshot varchar(160),
    created_at timestamp with time zone NOT NULL,
    activated_at timestamp with time zone,
    retired_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_version_report_template_i ON hidra_reporting_report_template_version (report_template_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_version_status ON hidra_reporting_report_template_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_version_created_by_actor_ ON hidra_reporting_report_template_version (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_reporting_report_template_version_created_at ON hidra_reporting_report_template_version (created_at);
