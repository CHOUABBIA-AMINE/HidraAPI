-- HIDRA audit module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity
-- Module: audit

CREATE TABLE IF NOT EXISTS hidra_audit_access_record (
    id varchar(80) PRIMARY KEY,
    actor_id varchar(120) NOT NULL,
    actor_display_name_snapshot varchar(160),
    access_type varchar(40) NOT NULL,
    audit_event_id varchar(80),
    search_filter_hash varchar(256),
    export_request_id varchar(80),
    result_count integer,
    purpose_text varchar(500),
    accessed_at timestamp with time zone NOT NULL,
    correlation_id varchar(120)
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_access_record_actor_id ON hidra_audit_access_record (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_access_record_audit_event_id ON hidra_audit_access_record (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_access_record_export_request_id ON hidra_audit_access_record (export_request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_access_record_correlation_id ON hidra_audit_access_record (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_audit_action_reference (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    action_code varchar(120) NOT NULL,
    action_type_id varchar(80) NOT NULL,
    operation varchar(60) NOT NULL,
    command_name varchar(160),
    result_status varchar(40) NOT NULL,
    failure_reason_code varchar(120),
    captured_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_action_reference_audit_event_id ON hidra_audit_action_reference (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_action_reference_action_type_id ON hidra_audit_action_reference (action_type_id);

CREATE TABLE IF NOT EXISTS hidra_audit_actor_snapshot (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    actor_id varchar(120),
    actor_type varchar(40) NOT NULL,
    username_snapshot varchar(120),
    display_name_snapshot varchar(160),
    email_masked varchar(160),
    role_code_snapshot varchar(120),
    employee_id varchar(120),
    employee_number_snapshot varchar(80),
    organization_unit_id varchar(120),
    organization_unit_code_snapshot varchar(120),
    organization_unit_name_snapshot varchar(160),
    position_code_snapshot varchar(120),
    captured_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_actor_snapshot_audit_event_id ON hidra_audit_actor_snapshot (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_actor_snapshot_actor_id ON hidra_audit_actor_snapshot (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_actor_snapshot_employee_id ON hidra_audit_actor_snapshot (employee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_actor_snapshot_organization_unit_id ON hidra_audit_actor_snapshot (organization_unit_id);

CREATE TABLE IF NOT EXISTS hidra_audit_before_after_value (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    field_path varchar(240) NOT NULL,
    field_label_snapshot varchar(240),
    value_type varchar(40) NOT NULL,
    before_value_text varchar(2000),
    after_value_text varchar(2000),
    before_value_hash varchar(256),
    after_value_hash varchar(256),
    masked boolean NOT NULL,
    mask_reason_id varchar(80),
    changed boolean NOT NULL,
    recorded_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_before_after_value_audit_event_id ON hidra_audit_before_after_value (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_before_after_value_mask_reason_id ON hidra_audit_before_after_value (mask_reason_id);

CREATE TABLE IF NOT EXISTS hidra_audit_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_catalog_entry_code ON hidra_audit_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_catalog_entry_active ON hidra_audit_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_catalog_entry_created_at ON hidra_audit_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_catalog_entry_updated_at ON hidra_audit_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_audit_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_catalog_translation_catalog_entry_id ON hidra_audit_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_catalog_translation_created_at ON hidra_audit_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_catalog_translation_updated_at ON hidra_audit_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_audit_correlation_context (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    correlation_id varchar(120),
    request_id varchar(120),
    causation_id varchar(120),
    session_id_hash varchar(256),
    trace_id varchar(120),
    span_id varchar(120),
    source_system_code varchar(120),
    source_message_id varchar(120),
    captured_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_correlation_context_audit_event_id ON hidra_audit_correlation_context (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_correlation_context_correlation_id ON hidra_audit_correlation_context (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_correlation_context_request_id ON hidra_audit_correlation_context (request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_correlation_context_causation_id ON hidra_audit_correlation_context (causation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_correlation_context_trace_id ON hidra_audit_correlation_context (trace_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_correlation_context_span_id ON hidra_audit_correlation_context (span_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_correlation_context_source_message_id ON hidra_audit_correlation_context (source_message_id);

CREATE TABLE IF NOT EXISTS hidra_audit_decision_context (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    decision_code varchar(120) NOT NULL,
    decision_type_id varchar(80),
    reason_id varchar(80),
    reason_text varchar(1000),
    comment_text varchar(2000),
    policy_code varchar(120),
    workflow_instance_id varchar(120),
    workflow_task_id varchar(120),
    workflow_action_id varchar(120),
    from_state varchar(80),
    to_state varchar(80),
    decided_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_decision_context_audit_event_id ON hidra_audit_decision_context (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_decision_context_decision_type_id ON hidra_audit_decision_context (decision_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_decision_context_reason_id ON hidra_audit_decision_context (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_decision_context_workflow_instance_id ON hidra_audit_decision_context (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_decision_context_workflow_task_id ON hidra_audit_decision_context (workflow_task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_decision_context_workflow_action_id ON hidra_audit_decision_context (workflow_action_id);

CREATE TABLE IF NOT EXISTS hidra_audit_event (
    id varchar(80) PRIMARY KEY,
    event_type_id varchar(80) NOT NULL,
    event_category_id varchar(80) NOT NULL,
    severity_id varchar(80),
    source_module varchar(80) NOT NULL,
    source_component varchar(120),
    source_event_id varchar(120),
    action_code varchar(120) NOT NULL,
    action_label_snapshot varchar(240),
    event_status varchar(40) NOT NULL,
    actor_id varchar(120),
    actor_type varchar(40) NOT NULL,
    actor_display_name_snapshot varchar(160),
    actor_username_snapshot varchar(120),
    actor_role_code_snapshot varchar(120),
    organization_unit_id varchar(120),
    organization_unit_code_snapshot varchar(120),
    organization_unit_name_snapshot varchar(160),
    target_module varchar(80) NOT NULL,
    target_type varchar(120) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_code_snapshot varchar(120),
    target_label_snapshot varchar(240),
    operation varchar(60) NOT NULL,
    decision_code varchar(120),
    reason_id varchar(80),
    reason_text varchar(1000),
    comment_text varchar(2000),
    workflow_instance_id varchar(120),
    workflow_task_id varchar(120),
    workflow_action_id varchar(120),
    workflow_from_state varchar(80),
    workflow_to_state varchar(80),
    request_id varchar(120),
    correlation_id varchar(120),
    causation_id varchar(120),
    ip_address_masked varchar(80),
    user_agent_snapshot varchar(500),
    source_system_code varchar(120),
    occurred_at timestamp with time zone NOT NULL,
    recorded_at timestamp with time zone NOT NULL,
    retention_policy_id varchar(80),
    hash_value varchar(256),
    previous_hash_value varchar(256),
    payload_json jsonb
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_event_type_id ON hidra_audit_event (event_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_event_category_id ON hidra_audit_event (event_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_severity_id ON hidra_audit_event (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_source_event_id ON hidra_audit_event (source_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_actor_id ON hidra_audit_event (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_organization_unit_id ON hidra_audit_event (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_target_id ON hidra_audit_event (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_reason_id ON hidra_audit_event (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_workflow_instance_id ON hidra_audit_event (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_workflow_task_id ON hidra_audit_event (workflow_task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_workflow_action_id ON hidra_audit_event (workflow_action_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_request_id ON hidra_audit_event (request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_correlation_id ON hidra_audit_event (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_causation_id ON hidra_audit_event (causation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_event_retention_policy_id ON hidra_audit_event (retention_policy_id);

CREATE TABLE IF NOT EXISTS hidra_audit_evidence_link (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    evidence_type_id varchar(80) NOT NULL,
    reference_module varchar(80),
    reference_type varchar(120) NOT NULL,
    reference_id varchar(120) NOT NULL,
    reference_code_snapshot varchar(120),
    reference_label_snapshot varchar(240),
    external_uri_masked varchar(500),
    checksum varchar(256),
    linked_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_evidence_link_audit_event_id ON hidra_audit_evidence_link (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_evidence_link_evidence_type_id ON hidra_audit_evidence_link (evidence_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_evidence_link_reference_id ON hidra_audit_evidence_link (reference_id);

CREATE TABLE IF NOT EXISTS hidra_audit_export_request (
    id varchar(80) PRIMARY KEY,
    requested_by_actor_id varchar(120) NOT NULL,
    requested_by_display_name_snapshot varchar(160),
    purpose_id varchar(80) NOT NULL,
    filter_json jsonb NOT NULL,
    format varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    workflow_instance_id varchar(120),
    result_document_reference_id varchar(120),
    record_count integer,
    checksum varchar(256),
    requested_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    expires_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_export_request_requested_by_actor_id ON hidra_audit_export_request (requested_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_export_request_purpose_id ON hidra_audit_export_request (purpose_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_export_request_status ON hidra_audit_export_request (status);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_export_request_workflow_instance_id ON hidra_audit_export_request (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_export_request_result_document_reference_id ON hidra_audit_export_request (result_document_reference_id);

CREATE TABLE IF NOT EXISTS hidra_audit_integrity_seal (
    id varchar(80) PRIMARY KEY,
    seal_type_id varchar(80) NOT NULL,
    audit_event_id varchar(80),
    from_recorded_at timestamp with time zone,
    to_recorded_at timestamp with time zone,
    event_count integer NOT NULL,
    hash_algorithm varchar(80) NOT NULL,
    root_hash varchar(256) NOT NULL,
    previous_seal_hash varchar(256),
    sealed_by_actor_id varchar(120),
    sealed_at timestamp with time zone NOT NULL,
    verification_status varchar(40) NOT NULL,
    verified_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_integrity_seal_seal_type_id ON hidra_audit_integrity_seal (seal_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_integrity_seal_audit_event_id ON hidra_audit_integrity_seal (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_integrity_seal_sealed_by_actor_id ON hidra_audit_integrity_seal (sealed_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_audit_retention_policy (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    event_category_id varchar(80),
    retention_days integer NOT NULL,
    archive_after_days integer,
    legal_hold_supported boolean NOT NULL,
    purge_allowed boolean NOT NULL,
    active boolean NOT NULL,
    valid_from date NOT NULL,
    valid_to date,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_retention_policy_code ON hidra_audit_retention_policy (code);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_retention_policy_event_category_id ON hidra_audit_retention_policy (event_category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_retention_policy_active ON hidra_audit_retention_policy (active);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_retention_policy_created_at ON hidra_audit_retention_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_retention_policy_updated_at ON hidra_audit_retention_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_audit_search_projection (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    source_module varchar(80) NOT NULL,
    event_category_code varchar(120) NOT NULL,
    event_type_code varchar(120) NOT NULL,
    action_code varchar(120) NOT NULL,
    actor_id varchar(120),
    actor_display_name_search varchar(240),
    organization_unit_id varchar(120),
    target_module varchar(80) NOT NULL,
    target_type varchar(120) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_search_text varchar(500),
    decision_code varchar(120),
    correlation_id varchar(120),
    request_id varchar(120),
    occurred_at timestamp with time zone NOT NULL,
    recorded_at timestamp with time zone NOT NULL,
    indexed_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_search_projection_audit_event_id ON hidra_audit_search_projection (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_search_projection_actor_id ON hidra_audit_search_projection (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_search_projection_organization_unit_id ON hidra_audit_search_projection (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_search_projection_target_id ON hidra_audit_search_projection (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_search_projection_correlation_id ON hidra_audit_search_projection (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_search_projection_request_id ON hidra_audit_search_projection (request_id);

CREATE TABLE IF NOT EXISTS hidra_audit_target_reference (
    id varchar(80) PRIMARY KEY,
    audit_event_id varchar(80) NOT NULL,
    target_module varchar(80) NOT NULL,
    target_type varchar(120) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_code_snapshot varchar(120),
    target_label_snapshot varchar(240),
    target_version varchar(80),
    topology_asset_type_code varchar(120),
    topology_asset_id varchar(120),
    topology_asset_code_snapshot varchar(120),
    captured_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_target_reference_audit_event_id ON hidra_audit_target_reference (audit_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_target_reference_target_id ON hidra_audit_target_reference (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_audit_target_reference_topology_asset_id ON hidra_audit_target_reference (topology_asset_id);
