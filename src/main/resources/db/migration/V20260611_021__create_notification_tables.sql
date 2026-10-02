-- HIDRA notification module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity
-- Module: notification

CREATE TABLE IF NOT EXISTS hidra_notification_acknowledgement (
    id varchar(80) PRIMARY KEY,
    message_id varchar(80) NOT NULL,
    recipient_id varchar(80) NOT NULL,
    acknowledgement_status varchar(40) NOT NULL,
    acknowledged_by_actor_id varchar(80),
    acknowledged_by_display_name_snapshot varchar(160),
    acknowledged_at timestamp with time zone,
    comment_text varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_acknowledgement_message_id ON hidra_notification_acknowledgement (message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_acknowledgement_recipient_id ON hidra_notification_acknowledgement (recipient_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_acknowledgement_acknowledged_by_actor_ ON hidra_notification_acknowledgement (acknowledged_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_acknowledgement_created_at ON hidra_notification_acknowledgement (created_at);

CREATE TABLE IF NOT EXISTS hidra_notification_batch (
    id varchar(80) PRIMARY KEY,
    request_id varchar(80),
    batch_type varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    message_count integer NOT NULL,
    success_count integer NOT NULL,
    failure_count integer NOT NULL,
    created_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_batch_request_id ON hidra_notification_batch (request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_batch_status ON hidra_notification_batch (status);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_batch_created_at ON hidra_notification_batch (created_at);

CREATE TABLE IF NOT EXISTS hidra_notification_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_catalog_entry_code ON hidra_notification_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_catalog_entry_active ON hidra_notification_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_catalog_entry_created_at ON hidra_notification_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_catalog_entry_updated_at ON hidra_notification_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_catalog_translation_catalog_entry_id ON hidra_notification_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_catalog_translation_created_at ON hidra_notification_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_catalog_translation_updated_at ON hidra_notification_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_channel (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    channel_type varchar(40) NOT NULL,
    active boolean NOT NULL,
    provider_reference varchar(255),
    supports_delivery_receipt boolean NOT NULL,
    supports_read_receipt boolean NOT NULL,
    supports_html boolean NOT NULL,
    supports_attachments boolean NOT NULL,
    max_payload_size integer,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_channel_code ON hidra_notification_channel (code);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_channel_active ON hidra_notification_channel (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_channel_created_at ON hidra_notification_channel (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_channel_updated_at ON hidra_notification_channel (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_contact_point (
    id varchar(80) PRIMARY KEY,
    recipient_profile_id varchar(80) NOT NULL,
    channel_id varchar(80) NOT NULL,
    address_value varchar(500) NOT NULL,
    address_label varchar(160),
    verified boolean NOT NULL,
    primary_for_channel boolean NOT NULL,
    active boolean NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_contact_point_recipient_profile_id ON hidra_notification_contact_point (recipient_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_contact_point_channel_id ON hidra_notification_contact_point (channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_contact_point_active ON hidra_notification_contact_point (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_contact_point_created_at ON hidra_notification_contact_point (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_contact_point_updated_at ON hidra_notification_contact_point (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_delivery_attempt (
    id varchar(80) PRIMARY KEY,
    message_id varchar(80) NOT NULL,
    attempt_number integer NOT NULL,
    channel_id varchar(80) NOT NULL,
    provider_reference varchar(255),
    provider_message_id varchar(255),
    attempt_status varchar(40) NOT NULL,
    attempted_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    failure_code varchar(120),
    failure_message varchar(2000),
    next_retry_at timestamp with time zone,
    correlation_id varchar(120),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_delivery_attempt_message_id ON hidra_notification_delivery_attempt (message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_delivery_attempt_channel_id ON hidra_notification_delivery_attempt (channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_delivery_attempt_provider_message_id ON hidra_notification_delivery_attempt (provider_message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_delivery_attempt_correlation_id ON hidra_notification_delivery_attempt (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_delivery_attempt_created_at ON hidra_notification_delivery_attempt (created_at);

CREATE TABLE IF NOT EXISTS hidra_notification_evidence_link (
    id varchar(80) PRIMARY KEY,
    notification_request_id varchar(80),
    message_id varchar(80),
    evidence_type varchar(120) NOT NULL,
    reference_module varchar(80) NOT NULL,
    reference_type varchar(120) NOT NULL,
    reference_id varchar(120) NOT NULL,
    reference_code_snapshot varchar(120),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_evidence_link_notification_request_id ON hidra_notification_evidence_link (notification_request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_evidence_link_message_id ON hidra_notification_evidence_link (message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_evidence_link_reference_id ON hidra_notification_evidence_link (reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_evidence_link_created_at ON hidra_notification_evidence_link (created_at);

CREATE TABLE IF NOT EXISTS hidra_notification_message (
    id varchar(80) PRIMARY KEY,
    request_id varchar(80) NOT NULL,
    recipient_id varchar(80) NOT NULL,
    channel_id varchar(80) NOT NULL,
    template_id varchar(80),
    template_version_id varchar(80),
    locale varchar(10),
    subject_rendered varchar(1000),
    body_rendered text,
    short_text_rendered varchar(500),
    payload_hash varchar(160),
    priority_id varchar(80),
    status varchar(40) NOT NULL,
    scheduled_at timestamp with time zone,
    expires_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_request_id ON hidra_notification_message (request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_recipient_id ON hidra_notification_message (recipient_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_channel_id ON hidra_notification_message (channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_template_id ON hidra_notification_message (template_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_template_version_id ON hidra_notification_message (template_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_priority_id ON hidra_notification_message (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_status ON hidra_notification_message (status);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_created_at ON hidra_notification_message (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_updated_at ON hidra_notification_message (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_message_variable (
    id varchar(80) PRIMARY KEY,
    message_id varchar(80) NOT NULL,
    variable_name varchar(160) NOT NULL,
    value_type varchar(40) NOT NULL,
    value_snapshot varchar(2000),
    masked boolean NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_variable_message_id ON hidra_notification_message_variable (message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_message_variable_created_at ON hidra_notification_message_variable (created_at);

CREATE TABLE IF NOT EXISTS hidra_notification_policy (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    source_module varchar(80) NOT NULL,
    category_id varchar(80) NOT NULL,
    priority_id varchar(80),
    default_template_id varchar(80),
    default_channel_id varchar(80),
    recipient_resolution_mode varchar(80) NOT NULL,
    allow_preference_override boolean NOT NULL,
    allow_quiet_hour_delay boolean NOT NULL,
    requires_acknowledgement boolean NOT NULL,
    max_retry_count integer,
    retry_policy_id varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_code ON hidra_notification_policy (code);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_category_id ON hidra_notification_policy (category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_priority_id ON hidra_notification_policy (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_default_template_id ON hidra_notification_policy (default_template_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_default_channel_id ON hidra_notification_policy (default_channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_retry_policy_id ON hidra_notification_policy (retry_policy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_active ON hidra_notification_policy (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_created_at ON hidra_notification_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_policy_updated_at ON hidra_notification_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_preference (
    id varchar(80) PRIMARY KEY,
    recipient_profile_id varchar(80) NOT NULL,
    channel_id varchar(80) NOT NULL,
    category_id varchar(80) NOT NULL,
    enabled boolean NOT NULL,
    quiet_hours_enabled boolean NOT NULL,
    quiet_hours_start varchar(10),
    quiet_hours_end varchar(10),
    timezone varchar(80),
    max_frequency_per_hour integer,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_preference_recipient_profile_id ON hidra_notification_preference (recipient_profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_preference_channel_id ON hidra_notification_preference (channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_preference_category_id ON hidra_notification_preference (category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_preference_created_at ON hidra_notification_preference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_preference_updated_at ON hidra_notification_preference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_recipient_group (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    group_type_id varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_code ON hidra_notification_recipient_group (code);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_group_type_id ON hidra_notification_recipient_group (group_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_active ON hidra_notification_recipient_group (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_created_at ON hidra_notification_recipient_group (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_updated_at ON hidra_notification_recipient_group (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_recipient_group_member (
    id varchar(80) PRIMARY KEY,
    group_id varchar(80) NOT NULL,
    member_type varchar(40) NOT NULL,
    member_reference_id varchar(120) NOT NULL,
    member_label_snapshot varchar(160),
    active boolean NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_member_group_id ON hidra_notification_recipient_group_member (group_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_member_member_referenc ON hidra_notification_recipient_group_member (member_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_member_active ON hidra_notification_recipient_group_member (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_member_created_at ON hidra_notification_recipient_group_member (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_group_member_updated_at ON hidra_notification_recipient_group_member (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_recipient_profile (
    id varchar(80) PRIMARY KEY,
    recipient_type varchar(40) NOT NULL,
    recipient_reference_id varchar(120) NOT NULL,
    recipient_code_snapshot varchar(120),
    recipient_display_name_snapshot varchar(160),
    preferred_locale varchar(10),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_profile_recipient_reference_ ON hidra_notification_recipient_profile (recipient_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_profile_active ON hidra_notification_recipient_profile (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_profile_created_at ON hidra_notification_recipient_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_recipient_profile_updated_at ON hidra_notification_recipient_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_request (
    id varchar(80) PRIMARY KEY,
    source_module varchar(80) NOT NULL,
    source_event_type varchar(120) NOT NULL,
    source_event_id varchar(120) NOT NULL,
    target_type varchar(120),
    target_id varchar(120),
    target_code_snapshot varchar(120),
    target_label_snapshot varchar(240),
    category_id varchar(80) NOT NULL,
    priority_id varchar(80),
    policy_id varchar(80),
    template_id varchar(80),
    template_version_id varchar(80),
    requested_by_actor_id varchar(80),
    requested_by_display_name_snapshot varchar(160),
    requested_at timestamp with time zone NOT NULL,
    correlation_id varchar(120),
    request_id varchar(120),
    status varchar(40) NOT NULL,
    expires_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_source_event_id ON hidra_notification_request (source_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_target_id ON hidra_notification_request (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_category_id ON hidra_notification_request (category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_priority_id ON hidra_notification_request (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_policy_id ON hidra_notification_request (policy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_template_id ON hidra_notification_request (template_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_template_version_id ON hidra_notification_request (template_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_requested_by_actor_id ON hidra_notification_request (requested_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_correlation_id ON hidra_notification_request (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_request_id ON hidra_notification_request (request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_status ON hidra_notification_request (status);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_created_at ON hidra_notification_request (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_updated_at ON hidra_notification_request (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_request_recipient (
    id varchar(80) PRIMARY KEY,
    request_id varchar(80) NOT NULL,
    recipient_type varchar(40) NOT NULL,
    recipient_reference_id varchar(120) NOT NULL,
    recipient_display_name_snapshot varchar(160),
    recipient_locale varchar(10),
    resolved_from_type varchar(80),
    resolved_from_reference_id varchar(120),
    resolution_status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_recipient_request_id ON hidra_notification_request_recipient (request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_recipient_recipient_reference_ ON hidra_notification_request_recipient (recipient_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_recipient_resolved_from_refere ON hidra_notification_request_recipient (resolved_from_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_request_recipient_created_at ON hidra_notification_request_recipient (created_at);

CREATE TABLE IF NOT EXISTS hidra_notification_retry_policy (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    max_attempts integer NOT NULL,
    initial_delay_seconds integer NOT NULL,
    max_delay_seconds integer NOT NULL,
    backoff_strategy varchar(40) NOT NULL,
    retry_on_temporary_failure boolean NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_retry_policy_code ON hidra_notification_retry_policy (code);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_retry_policy_active ON hidra_notification_retry_policy (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_retry_policy_created_at ON hidra_notification_retry_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_retry_policy_updated_at ON hidra_notification_retry_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_schedule (
    id varchar(80) PRIMARY KEY,
    request_id varchar(80),
    message_id varchar(80),
    schedule_type varchar(40) NOT NULL,
    scheduled_at timestamp with time zone NOT NULL,
    timezone varchar(80),
    recurrence_rule varchar(500),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_schedule_request_id ON hidra_notification_schedule (request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_schedule_message_id ON hidra_notification_schedule (message_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_schedule_status ON hidra_notification_schedule (status);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_schedule_created_at ON hidra_notification_schedule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_schedule_updated_at ON hidra_notification_schedule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_status_history (
    id varchar(80) PRIMARY KEY,
    entity_type varchar(80) NOT NULL,
    entity_id varchar(80) NOT NULL,
    from_status varchar(80),
    to_status varchar(80) NOT NULL,
    reason_id varchar(80),
    reason_text varchar(1000),
    changed_by_actor_id varchar(80),
    changed_by_display_name_snapshot varchar(160),
    changed_at timestamp with time zone NOT NULL,
    correlation_id varchar(120)
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_status_history_entity_id ON hidra_notification_status_history (entity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_status_history_reason_id ON hidra_notification_status_history (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_status_history_changed_by_actor_id ON hidra_notification_status_history (changed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_status_history_correlation_id ON hidra_notification_status_history (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_notification_suppression_rule (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    source_module varchar(80),
    category_id varchar(80),
    priority_id varchar(80),
    channel_id varchar(80),
    recipient_type varchar(40),
    recipient_reference_id varchar(120),
    reason_id varchar(80) NOT NULL,
    active boolean NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_code ON hidra_notification_suppression_rule (code);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_category_id ON hidra_notification_suppression_rule (category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_priority_id ON hidra_notification_suppression_rule (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_channel_id ON hidra_notification_suppression_rule (channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_recipient_reference_i ON hidra_notification_suppression_rule (recipient_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_reason_id ON hidra_notification_suppression_rule (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_active ON hidra_notification_suppression_rule (active);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_created_at ON hidra_notification_suppression_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_suppression_rule_updated_at ON hidra_notification_suppression_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_template (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    template_type_id varchar(80) NOT NULL,
    category_id varchar(80),
    default_channel_id varchar(80),
    status varchar(40) NOT NULL,
    current_version integer,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_code ON hidra_notification_template (code);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_template_type_id ON hidra_notification_template (template_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_category_id ON hidra_notification_template (category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_default_channel_id ON hidra_notification_template (default_channel_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_status ON hidra_notification_template (status);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_created_at ON hidra_notification_template (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_updated_at ON hidra_notification_template (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_template_translation (
    id varchar(80) PRIMARY KEY,
    template_version_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    subject varchar(1000),
    body text NOT NULL,
    short_text varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_translation_template_version_ ON hidra_notification_template_translation (template_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_translation_created_at ON hidra_notification_template_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_translation_updated_at ON hidra_notification_template_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_notification_template_version (
    id varchar(80) PRIMARY KEY,
    template_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    status varchar(40) NOT NULL,
    subject_template varchar(1000),
    body_template text NOT NULL,
    content_format varchar(40) NOT NULL,
    variable_schema_json jsonb,
    created_by_actor_id varchar(80),
    created_by_display_name_snapshot varchar(160),
    created_at timestamp with time zone NOT NULL,
    activated_at timestamp with time zone,
    retired_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_version_template_id ON hidra_notification_template_version (template_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_version_status ON hidra_notification_template_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_version_created_by_actor_id ON hidra_notification_template_version (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_notification_template_version_created_at ON hidra_notification_template_version (created_at);
