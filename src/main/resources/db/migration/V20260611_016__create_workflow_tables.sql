-- HIDRA workflow module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity
-- Module: workflow

CREATE TABLE IF NOT EXISTS hidra_workflow_action (
    id varchar(80) PRIMARY KEY,
    instance_id varchar(80) NOT NULL,
    task_id varchar(80),
    action_type varchar(40) NOT NULL,
    decision varchar(40),
    reason_id varchar(80),
    decision_note varchar(2000),
    comment_text varchar(2000),
    actor_id varchar(80) NOT NULL,
    actor_username_snapshot varchar(120),
    actor_display_name_snapshot varchar(160) NOT NULL,
    actor_role_code_snapshot varchar(80),
    organization_unit_id varchar(80),
    organization_unit_name_snapshot varchar(160),
    organization_role_code_snapshot varchar(80),
    correlation_id varchar(120),
    action_sequence bigint NOT NULL,
    source_system varchar(80),
    ip_address_hash varchar(128),
    user_agent_hash varchar(128),
    acted_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_action_instance_id ON hidra_workflow_action (instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_action_task_id ON hidra_workflow_action (task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_action_reason_id ON hidra_workflow_action (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_action_actor_id ON hidra_workflow_action (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_action_organization_unit_id ON hidra_workflow_action (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_action_correlation_id ON hidra_workflow_action (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_workflow_assignment (
    id varchar(80) PRIMARY KEY,
    task_id varchar(80) NOT NULL,
    actor_id varchar(80),
    actor_username_snapshot varchar(120),
    actor_display_name_snapshot varchar(160),
    role_code_snapshot varchar(80),
    organization_unit_id varchar(80),
    organization_unit_name_snapshot varchar(160),
    status varchar(40) NOT NULL,
    assigned_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_assignment_task_id ON hidra_workflow_assignment (task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_assignment_actor_id ON hidra_workflow_assignment (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_assignment_organization_unit_id ON hidra_workflow_assignment (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_assignment_status ON hidra_workflow_assignment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_assignment_updated_at ON hidra_workflow_assignment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_audit_outbox_reference (
    id varchar(80) PRIMARY KEY,
    instance_id varchar(80) NOT NULL,
    task_id varchar(80),
    action_id varchar(80),
    event_type varchar(120) NOT NULL,
    outbox_event_id varchar(120),
    emitted_at timestamp with time zone NOT NULL,
    status varchar(40) NOT NULL,
    failure_reason varchar(1000)
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_audit_outbox_reference_instance_id ON hidra_workflow_audit_outbox_reference (instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_audit_outbox_reference_task_id ON hidra_workflow_audit_outbox_reference (task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_audit_outbox_reference_action_id ON hidra_workflow_audit_outbox_reference (action_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_audit_outbox_reference_outbox_event_id ON hidra_workflow_audit_outbox_reference (outbox_event_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_audit_outbox_reference_status ON hidra_workflow_audit_outbox_reference (status);

CREATE TABLE IF NOT EXISTS hidra_workflow_type_catalog (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_type_catalog_code ON hidra_workflow_type_catalog (code);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_type_catalog_active ON hidra_workflow_type_catalog (active);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_type_catalog_created_at ON hidra_workflow_type_catalog (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_type_catalog_updated_at ON hidra_workflow_type_catalog (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_type_translation (
    id varchar(80) PRIMARY KEY,
    type_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_type_translation_type_id ON hidra_workflow_type_translation (type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_type_translation_created_at ON hidra_workflow_type_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_type_translation_updated_at ON hidra_workflow_type_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_comment (
    id varchar(80) PRIMARY KEY,
    instance_id varchar(80) NOT NULL,
    task_id varchar(80),
    actor_id varchar(80) NOT NULL,
    actor_username_snapshot varchar(120),
    actor_display_name_snapshot varchar(160) NOT NULL,
    actor_role_code_snapshot varchar(80),
    comment_text text NOT NULL,
    visibility varchar(40),
    parent_comment_id varchar(80),
    commented_at timestamp with time zone NOT NULL,
    edited_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_comment_instance_id ON hidra_workflow_comment (instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_comment_task_id ON hidra_workflow_comment (task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_comment_actor_id ON hidra_workflow_comment (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_comment_parent_comment_id ON hidra_workflow_comment (parent_comment_id);

CREATE TABLE IF NOT EXISTS hidra_workflow_definition (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    type_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    version integer NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_code ON hidra_workflow_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_type_id ON hidra_workflow_definition (type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_status ON hidra_workflow_definition (status);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_created_at ON hidra_workflow_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_updated_at ON hidra_workflow_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_definition_target_binding (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    target_module varchar(80) NOT NULL,
    target_type_id varchar(80) NOT NULL,
    workflow_purpose_id varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_target_binding_definition_id ON hidra_workflow_definition_target_binding (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_target_binding_target_type_id ON hidra_workflow_definition_target_binding (target_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_target_binding_workflow_purpose ON hidra_workflow_definition_target_binding (workflow_purpose_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_target_binding_active ON hidra_workflow_definition_target_binding (active);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_target_binding_created_at ON hidra_workflow_definition_target_binding (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_definition_target_binding_updated_at ON hidra_workflow_definition_target_binding (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_delegation (
    id varchar(80) PRIMARY KEY,
    task_id varchar(80) NOT NULL,
    from_actor_id varchar(80) NOT NULL,
    from_actor_username_snapshot varchar(120),
    from_actor_display_name_snapshot varchar(160) NOT NULL,
    from_actor_role_code_snapshot varchar(80),
    to_actor_id varchar(80),
    to_actor_username_snapshot varchar(120),
    to_actor_display_name_snapshot varchar(160),
    to_actor_role_code_snapshot varchar(80),
    to_organization_unit_id varchar(80),
    to_organization_unit_name_snapshot varchar(160),
    to_organization_role_code_snapshot varchar(80),
    reason_id varchar(80) NOT NULL,
    delegation_status varchar(40) NOT NULL,
    delegated_at timestamp with time zone NOT NULL,
    accepted_at timestamp with time zone,
    valid_until timestamp with time zone,
    delegation_depth integer
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_delegation_task_id ON hidra_workflow_delegation (task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_delegation_from_actor_id ON hidra_workflow_delegation (from_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_delegation_to_actor_id ON hidra_workflow_delegation (to_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_delegation_to_organization_unit_id ON hidra_workflow_delegation (to_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_delegation_reason_id ON hidra_workflow_delegation (reason_id);

CREATE TABLE IF NOT EXISTS hidra_workflow_escalation_rule (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    step_id varchar(80) NOT NULL,
    after_duration_seconds integer NOT NULL,
    escalate_to_actor_id varchar(80),
    escalate_to_actor_username_snapshot varchar(120),
    escalate_to_actor_display_name_snapshot varchar(160),
    escalate_to_actor_role_code_snapshot varchar(80),
    escalate_to_organization_unit_id varchar(80),
    escalate_to_organization_unit_name_snapshot varchar(160),
    escalate_to_organization_role_code_snapshot varchar(80),
    escalation_reason_id varchar(80),
    repeatable boolean NOT NULL,
    max_repeat_count integer,
    escalation_level integer,
    business_hours_calendar_id varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_definition_id ON hidra_workflow_escalation_rule (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_step_id ON hidra_workflow_escalation_rule (step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_escalate_to_actor_id ON hidra_workflow_escalation_rule (escalate_to_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_escalate_to_organization_u ON hidra_workflow_escalation_rule (escalate_to_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_escalation_reason_id ON hidra_workflow_escalation_rule (escalation_reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_business_hours_calendar_id ON hidra_workflow_escalation_rule (business_hours_calendar_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_active ON hidra_workflow_escalation_rule (active);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_created_at ON hidra_workflow_escalation_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_escalation_rule_updated_at ON hidra_workflow_escalation_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_instance (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    definition_version integer NOT NULL,
    workflow_purpose_id varchar(80),
    target_module varchar(80) NOT NULL,
    target_type_id varchar(80) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_code_snapshot varchar(120),
    target_label_snapshot varchar(240),
    status varchar(40) NOT NULL,
    current_step_id varchar(80),
    started_by_actor_id varchar(80) NOT NULL,
    started_by_username_snapshot varchar(120),
    started_by_display_name_snapshot varchar(160) NOT NULL,
    started_by_role_code_snapshot varchar(80),
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    cancelled_at timestamp with time zone,
    correlation_id varchar(120),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_definition_id ON hidra_workflow_instance (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_workflow_purpose_id ON hidra_workflow_instance (workflow_purpose_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_target_type_id ON hidra_workflow_instance (target_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_target_id ON hidra_workflow_instance (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_status ON hidra_workflow_instance (status);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_current_step_id ON hidra_workflow_instance (current_step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_started_by_actor_id ON hidra_workflow_instance (started_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_correlation_id ON hidra_workflow_instance (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_created_at ON hidra_workflow_instance (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_instance_updated_at ON hidra_workflow_instance (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_sla_policy (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    duration_seconds integer NOT NULL,
    calendar_mode varchar(40) NOT NULL,
    warning_before_seconds integer,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_sla_policy_code ON hidra_workflow_sla_policy (code);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_sla_policy_active ON hidra_workflow_sla_policy (active);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_sla_policy_created_at ON hidra_workflow_sla_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_sla_policy_updated_at ON hidra_workflow_sla_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_state_history (
    id varchar(80) PRIMARY KEY,
    instance_id varchar(80) NOT NULL,
    task_id varchar(80),
    from_step_id varchar(80),
    to_step_id varchar(80),
    from_status varchar(40),
    to_status varchar(40) NOT NULL,
    actor_id varchar(80) NOT NULL,
    actor_username_snapshot varchar(120),
    actor_display_name_snapshot varchar(160) NOT NULL,
    actor_role_code_snapshot varchar(80),
    action_id varchar(80),
    reason_id varchar(80),
    correlation_id varchar(120),
    changed_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_instance_id ON hidra_workflow_state_history (instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_task_id ON hidra_workflow_state_history (task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_from_step_id ON hidra_workflow_state_history (from_step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_to_step_id ON hidra_workflow_state_history (to_step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_actor_id ON hidra_workflow_state_history (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_action_id ON hidra_workflow_state_history (action_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_reason_id ON hidra_workflow_state_history (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_state_history_correlation_id ON hidra_workflow_state_history (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_workflow_step_assignment_rule (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    step_id varchar(80) NOT NULL,
    assignment_mode_id varchar(80) NOT NULL,
    actor_id varchar(80),
    role_code varchar(80),
    organization_unit_id varchar(80),
    organization_role_code varchar(80),
    target_owner_mode varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_definition_id ON hidra_workflow_step_assignment_rule (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_step_id ON hidra_workflow_step_assignment_rule (step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_assignment_mode_id ON hidra_workflow_step_assignment_rule (assignment_mode_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_actor_id ON hidra_workflow_step_assignment_rule (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_organization_unit_id ON hidra_workflow_step_assignment_rule (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_active ON hidra_workflow_step_assignment_rule (active);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_created_at ON hidra_workflow_step_assignment_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_assignment_rule_updated_at ON hidra_workflow_step_assignment_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_step (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    step_order integer NOT NULL,
    mandatory boolean NOT NULL,
    step_type_id varchar(80),
    default_assignment_rule_id varchar(80),
    sla_policy_id varchar(80),
    allow_claim boolean NOT NULL,
    allow_delegation boolean NOT NULL,
    allow_escalation boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_definition_id ON hidra_workflow_step (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_code ON hidra_workflow_step (code);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_step_type_id ON hidra_workflow_step (step_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_default_assignment_rule_id ON hidra_workflow_step (default_assignment_rule_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_sla_policy_id ON hidra_workflow_step (sla_policy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_created_at ON hidra_workflow_step (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_step_updated_at ON hidra_workflow_step (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_task (
    id varchar(80) PRIMARY KEY,
    instance_id varchar(80) NOT NULL,
    step_id varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    assigned_actor_id varchar(80),
    assigned_actor_username_snapshot varchar(120),
    assigned_actor_display_name_snapshot varchar(160),
    assigned_organization_unit_id varchar(80),
    assigned_organization_unit_name_snapshot varchar(160),
    assigned_role_code_snapshot varchar(80),
    priority_id varchar(80),
    due_at timestamp with time zone,
    claimed_by_actor_id varchar(80),
    claimed_at timestamp with time zone,
    completed_by_actor_id varchar(80),
    completed_at timestamp with time zone,
    assignment_mode_id varchar(80),
    task_label_snapshot varchar(240),
    sla_status varchar(40),
    escalated_at timestamp with time zone,
    delegated_at timestamp with time zone,
    expires_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_instance_id ON hidra_workflow_task (instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_step_id ON hidra_workflow_task (step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_status ON hidra_workflow_task (status);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_assigned_actor_id ON hidra_workflow_task (assigned_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_assigned_organization_unit_id ON hidra_workflow_task (assigned_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_priority_id ON hidra_workflow_task (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_claimed_by_actor_id ON hidra_workflow_task (claimed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_completed_by_actor_id ON hidra_workflow_task (completed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_assignment_mode_id ON hidra_workflow_task (assignment_mode_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_created_at ON hidra_workflow_task (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_task_updated_at ON hidra_workflow_task (updated_at);

CREATE TABLE IF NOT EXISTS hidra_workflow_transition (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    from_step_id varchar(80) NOT NULL,
    to_step_id varchar(80) NOT NULL,
    decision varchar(40) NOT NULL,
    reason_required boolean NOT NULL,
    comment_required boolean NOT NULL,
    condition_expression text,
    required_permission_code varchar(120),
    target_module_callback varchar(120),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_transition_definition_id ON hidra_workflow_transition (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_transition_from_step_id ON hidra_workflow_transition (from_step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_transition_to_step_id ON hidra_workflow_transition (to_step_id);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_transition_created_at ON hidra_workflow_transition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_workflow_transition_updated_at ON hidra_workflow_transition (updated_at);
