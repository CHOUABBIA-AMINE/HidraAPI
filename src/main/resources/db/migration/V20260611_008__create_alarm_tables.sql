-- HIDRA alarm module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity
-- Module: alarm

CREATE TABLE IF NOT EXISTS hidra_alarm_acknowledgement (
    id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL,
    acknowledged_by_actor_id varchar(80) NOT NULL,
    acknowledged_by_display_name varchar(255),
    organization_unit_id varchar(80),
    organization_unit_code varchar(160),
    acknowledged_at timestamp with time zone NOT NULL,
    comment text,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_acknowledgement_alarm_id ON hidra_alarm_acknowledgement (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_acknowledgement_acknowledged_by_actor_id ON hidra_alarm_acknowledgement (acknowledged_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_acknowledgement_organization_unit_id ON hidra_alarm_acknowledgement (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_acknowledgement_correlation_id ON hidra_alarm_acknowledgement (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_alarm_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    severity_rank integer,
    color_code varchar(40),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_catalog_entry_code ON hidra_alarm_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_catalog_entry_active ON hidra_alarm_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_catalog_entry_created_at ON hidra_alarm_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_catalog_entry_updated_at ON hidra_alarm_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_alarm_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_catalog_translation_catalog_entry_id ON hidra_alarm_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_catalog_translation_created_at ON hidra_alarm_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_catalog_translation_updated_at ON hidra_alarm_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_alarm_closure (
    id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL,
    closure_type varchar(80) NOT NULL,
    closure_reason_id varchar(80),
    closure_comment text,
    closed_by_actor_id varchar(80) NOT NULL,
    closed_at timestamp with time zone NOT NULL,
    requires_review boolean NOT NULL,
    review_workflow_instance_id varchar(80),
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_closure_alarm_id ON hidra_alarm_closure (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_closure_closure_reason_id ON hidra_alarm_closure (closure_reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_closure_closed_by_actor_id ON hidra_alarm_closure (closed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_closure_review_workflow_instance_id ON hidra_alarm_closure (review_workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_closure_correlation_id ON hidra_alarm_closure (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_alarm_comment (
    id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL,
    comment_text text NOT NULL,
    visibility varchar(80) NOT NULL,
    created_by_actor_id varchar(80) NOT NULL,
    created_by_display_name varchar(255),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_comment_alarm_id ON hidra_alarm_comment (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_comment_created_by_actor_id ON hidra_alarm_comment (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_comment_created_at ON hidra_alarm_comment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_comment_updated_at ON hidra_alarm_comment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_alarm_escalation (
    id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL,
    escalation_level integer NOT NULL,
    escalation_type varchar(80) NOT NULL,
    target_organization_unit_id varchar(80),
    target_actor_id varchar(80),
    workflow_instance_id varchar(80),
    incident_id varchar(80),
    reason_id varchar(80),
    reason_text text,
    escalated_by_actor_id varchar(80) NOT NULL,
    escalated_at timestamp with time zone NOT NULL,
    status varchar(40) NOT NULL,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_alarm_id ON hidra_alarm_escalation (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_target_organization_unit_id ON hidra_alarm_escalation (target_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_target_actor_id ON hidra_alarm_escalation (target_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_workflow_instance_id ON hidra_alarm_escalation (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_incident_id ON hidra_alarm_escalation (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_reason_id ON hidra_alarm_escalation (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_escalated_by_actor_id ON hidra_alarm_escalation (escalated_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_status ON hidra_alarm_escalation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_escalation_correlation_id ON hidra_alarm_escalation (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_alarm_evidence_link (
    id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL,
    evidence_type varchar(80) NOT NULL,
    evidence_reference_id varchar(80) NOT NULL,
    evidence_code_snapshot varchar(160),
    evidence_name_snapshot varchar(500),
    description text,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_evidence_link_alarm_id ON hidra_alarm_evidence_link (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_evidence_link_evidence_reference_id ON hidra_alarm_evidence_link (evidence_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_evidence_link_created_at ON hidra_alarm_evidence_link (created_at);

CREATE TABLE IF NOT EXISTS hidra_alarm (
    id varchar(80) PRIMARY KEY,
    alarm_number varchar(80) NOT NULL,
    alarm_type_id varchar(80) NOT NULL,
    severity_id varchar(80) NOT NULL,
    priority_id varchar(80),
    title_ar varchar(255),
    title_fr varchar(255) NOT NULL,
    title_en varchar(255),
    description_ar text,
    description_fr text,
    description_en text,
    source_type varchar(80) NOT NULL,
    source_reference_id varchar(80),
    monitoring_alert_candidate_id varchar(80),
    monitoring_evaluation_id varchar(80),
    telemetry_reading_id varchar(80),
    planning_target_id varchar(80),
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    topology_asset_name_snapshot varchar(500),
    current_state varchar(40) NOT NULL,
    raised_at timestamp with time zone NOT NULL,
    first_detected_at timestamp with time zone,
    last_updated_at timestamp with time zone NOT NULL,
    cleared_at timestamp with time zone,
    closed_at timestamp with time zone,
    acknowledged_at timestamp with time zone,
    acknowledged_by_actor_id varchar(80),
    owning_organization_unit_id varchar(80),
    owning_organization_unit_code varchar(160),
    owning_organization_unit_name_snapshot varchar(500),
    workflow_instance_id varchar(80),
    incident_id varchar(80),
    correlation_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_alarm_type_id ON hidra_alarm (alarm_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_severity_id ON hidra_alarm (severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_priority_id ON hidra_alarm (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_source_reference_id ON hidra_alarm (source_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_monitoring_alert_candidate_id ON hidra_alarm (monitoring_alert_candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_monitoring_evaluation_id ON hidra_alarm (monitoring_evaluation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_telemetry_reading_id ON hidra_alarm (telemetry_reading_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_planning_target_id ON hidra_alarm (planning_target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_topology_asset_id ON hidra_alarm (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_acknowledged_by_actor_id ON hidra_alarm (acknowledged_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_owning_organization_unit_id ON hidra_alarm (owning_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_workflow_instance_id ON hidra_alarm (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_incident_id ON hidra_alarm (incident_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_correlation_id ON hidra_alarm (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_created_at ON hidra_alarm (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_updated_at ON hidra_alarm (updated_at);

CREATE TABLE IF NOT EXISTS hidra_alarm_lifecycle_event (
    id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL,
    event_type varchar(80) NOT NULL,
    previous_state varchar(40),
    new_state varchar(40) NOT NULL,
    reason_id varchar(80),
    reason_text text,
    actor_id varchar(80) NOT NULL,
    actor_display_name varchar(255),
    organization_unit_id varchar(80),
    organization_unit_code varchar(160),
    organization_unit_name_snapshot varchar(500),
    occurred_at timestamp with time zone NOT NULL,
    correlation_id varchar(80),
    metadata_json jsonb
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_lifecycle_event_alarm_id ON hidra_alarm_lifecycle_event (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_lifecycle_event_reason_id ON hidra_alarm_lifecycle_event (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_lifecycle_event_actor_id ON hidra_alarm_lifecycle_event (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_lifecycle_event_organization_unit_id ON hidra_alarm_lifecycle_event (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_lifecycle_event_correlation_id ON hidra_alarm_lifecycle_event (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_alarm_rule_binding (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    monitoring_rule_id varchar(80),
    monitoring_threshold_id varchar(80),
    candidate_type_id varchar(80),
    alarm_type_id varchar(80) NOT NULL,
    default_severity_id varchar(80) NOT NULL,
    default_priority_id varchar(80),
    auto_raise boolean NOT NULL,
    requires_operator_confirmation boolean NOT NULL,
    active boolean NOT NULL,
    effective_from timestamp with time zone NOT NULL,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_code ON hidra_alarm_rule_binding (code);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_monitoring_rule_id ON hidra_alarm_rule_binding (monitoring_rule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_monitoring_threshold_id ON hidra_alarm_rule_binding (monitoring_threshold_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_candidate_type_id ON hidra_alarm_rule_binding (candidate_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_alarm_type_id ON hidra_alarm_rule_binding (alarm_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_default_severity_id ON hidra_alarm_rule_binding (default_severity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_default_priority_id ON hidra_alarm_rule_binding (default_priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_active ON hidra_alarm_rule_binding (active);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_created_at ON hidra_alarm_rule_binding (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_rule_binding_updated_at ON hidra_alarm_rule_binding (updated_at);

CREATE TABLE IF NOT EXISTS hidra_alarm_shelving (
    id varchar(80) PRIMARY KEY,
    alarm_id varchar(80) NOT NULL,
    shelving_reason_id varchar(80) NOT NULL,
    reason_text text,
    shelved_by_actor_id varchar(80) NOT NULL,
    shelved_at timestamp with time zone NOT NULL,
    shelved_until timestamp with time zone NOT NULL,
    unshelved_at timestamp with time zone,
    unshelved_by_actor_id varchar(80),
    status varchar(40) NOT NULL,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_shelving_alarm_id ON hidra_alarm_shelving (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_shelving_shelving_reason_id ON hidra_alarm_shelving (shelving_reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_shelving_shelved_by_actor_id ON hidra_alarm_shelving (shelved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_shelving_unshelved_by_actor_id ON hidra_alarm_shelving (unshelved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_shelving_status ON hidra_alarm_shelving (status);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_shelving_correlation_id ON hidra_alarm_shelving (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_alarm_suppression (
    id varchar(80) PRIMARY KEY,
    scope_type varchar(80) NOT NULL,
    scope_reference_id varchar(80) NOT NULL,
    alarm_id varchar(80),
    alarm_type_id varchar(80),
    topology_asset_type_code varchar(80),
    topology_asset_id varchar(80),
    suppression_reason_id varchar(80) NOT NULL,
    reason_text text,
    suppressed_by_actor_id varchar(80) NOT NULL,
    suppressed_at timestamp with time zone NOT NULL,
    suppressed_until timestamp with time zone,
    released_at timestamp with time zone,
    released_by_actor_id varchar(80),
    status varchar(40) NOT NULL,
    workflow_instance_id varchar(80),
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_scope_reference_id ON hidra_alarm_suppression (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_alarm_id ON hidra_alarm_suppression (alarm_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_alarm_type_id ON hidra_alarm_suppression (alarm_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_topology_asset_id ON hidra_alarm_suppression (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_suppression_reason_id ON hidra_alarm_suppression (suppression_reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_suppressed_by_actor_id ON hidra_alarm_suppression (suppressed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_released_by_actor_id ON hidra_alarm_suppression (released_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_status ON hidra_alarm_suppression (status);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_workflow_instance_id ON hidra_alarm_suppression (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_alarm_suppression_correlation_id ON hidra_alarm_suppression (correlation_id);
