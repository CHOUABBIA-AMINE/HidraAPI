-- HIDRA leakdetection module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity
-- Module: leakdetection

CREATE TABLE IF NOT EXISTS hidra_leak_detection_candidate (
    id varchar(80) PRIMARY KEY,
    run_id varchar(80),
    profile_id varchar(80) NOT NULL,
    candidate_number varchar(80) NOT NULL,
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    topology_asset_name_snapshot varchar(500),
    suspected_at timestamp with time zone NOT NULL,
    first_evidence_at timestamp with time zone,
    confidence_score numeric(10,6) NOT NULL,
    severity_level varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    summary varchar(500),
    correlation_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_candidate_run_id ON hidra_leak_detection_candidate (run_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_candidate_profile_id ON hidra_leak_detection_candidate (profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_candidate_topology_asset_id ON hidra_leak_detection_candidate (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_candidate_status ON hidra_leak_detection_candidate (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_candidate_correlation_id ON hidra_leak_detection_candidate (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_candidate_created_at ON hidra_leak_detection_candidate (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_candidate_updated_at ON hidra_leak_detection_candidate (updated_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_case_status_history (
    id varchar(80) PRIMARY KEY,
    case_id varchar(80) NOT NULL,
    old_status varchar(40),
    new_status varchar(40) NOT NULL,
    reason_id varchar(80),
    reason_text text,
    changed_by_actor_id varchar(80),
    changed_at timestamp with time zone NOT NULL,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_status_history_case_id ON hidra_leak_detection_case_status_history (case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_status_history_reason_id ON hidra_leak_detection_case_status_history (reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_status_history_changed_by_actor ON hidra_leak_detection_case_status_history (changed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_status_history_correlation_id ON hidra_leak_detection_case_status_history (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_case (
    id varchar(80) PRIMARY KEY,
    case_number varchar(80) NOT NULL,
    primary_candidate_id varchar(80) NOT NULL,
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    owning_organization_unit_id varchar(80),
    status varchar(40) NOT NULL,
    severity_level varchar(40),
    confidence_score numeric(10,6),
    opened_at timestamp with time zone NOT NULL,
    closed_at timestamp with time zone,
    opened_by_actor_id varchar(80),
    closed_by_actor_id varchar(80),
    closure_reason_id varchar(80),
    correlation_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_primary_candidate_id ON hidra_leak_detection_case (primary_candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_topology_asset_id ON hidra_leak_detection_case (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_owning_organization_unit_id ON hidra_leak_detection_case (owning_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_status ON hidra_leak_detection_case (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_opened_by_actor_id ON hidra_leak_detection_case (opened_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_closed_by_actor_id ON hidra_leak_detection_case (closed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_closure_reason_id ON hidra_leak_detection_case (closure_reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_correlation_id ON hidra_leak_detection_case (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_created_at ON hidra_leak_detection_case (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_case_updated_at ON hidra_leak_detection_case (updated_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_method (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    method_family varchar(120) NOT NULL,
    description text,
    status varchar(40) NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_method_code ON hidra_leak_detection_method (code);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_method_status ON hidra_leak_detection_method (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_method_created_at ON hidra_leak_detection_method (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_method_updated_at ON hidra_leak_detection_method (updated_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_method_translation (
    id varchar(80) PRIMARY KEY,
    method_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_method_translation_method_id ON hidra_leak_detection_method_translation (method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_method_translation_created_at ON hidra_leak_detection_method_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_method_translation_updated_at ON hidra_leak_detection_method_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_profile (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    topology_asset_name_snapshot varchar(500),
    method_id varchar(80) NOT NULL,
    configuration_json jsonb,
    status varchar(40) NOT NULL,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_profile_code ON hidra_leak_detection_profile (code);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_profile_topology_asset_id ON hidra_leak_detection_profile (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_profile_method_id ON hidra_leak_detection_profile (method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_profile_status ON hidra_leak_detection_profile (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_profile_created_by_actor_id ON hidra_leak_detection_profile (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_profile_created_at ON hidra_leak_detection_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_profile_updated_at ON hidra_leak_detection_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_rule (
    id varchar(80) PRIMARY KEY,
    profile_id varchar(80) NOT NULL,
    method_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    name_fr varchar(160) NOT NULL,
    rule_type varchar(120) NOT NULL,
    expression text,
    parameter_json jsonb,
    threshold_value numeric(18,6),
    unit_id varchar(80),
    status varchar(40) NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_rule_profile_id ON hidra_leak_detection_rule (profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_rule_method_id ON hidra_leak_detection_rule (method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_rule_code ON hidra_leak_detection_rule (code);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_rule_unit_id ON hidra_leak_detection_rule (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_rule_status ON hidra_leak_detection_rule (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_rule_created_at ON hidra_leak_detection_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_rule_updated_at ON hidra_leak_detection_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_run (
    id varchar(80) PRIMARY KEY,
    profile_id varchar(80) NOT NULL,
    method_id varchar(80) NOT NULL,
    run_code varchar(80) NOT NULL,
    evaluation_start timestamp with time zone NOT NULL,
    evaluation_end timestamp with time zone,
    status varchar(40) NOT NULL,
    candidate_count integer NOT NULL,
    failure_reason text,
    correlation_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_run_profile_id ON hidra_leak_detection_run (profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_run_method_id ON hidra_leak_detection_run (method_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_run_status ON hidra_leak_detection_run (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_run_correlation_id ON hidra_leak_detection_run (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_run_created_at ON hidra_leak_detection_run (created_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_dismissal_reason (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    name_ar varchar(160),
    name_fr varchar(160) NOT NULL,
    name_en varchar(160),
    description text,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_dismissal_reason_code ON hidra_leak_detection_dismissal_reason (code);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_dismissal_reason_active ON hidra_leak_detection_dismissal_reason (active);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_dismissal_reason_created_at ON hidra_leak_detection_dismissal_reason (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_dismissal_reason_updated_at ON hidra_leak_detection_dismissal_reason (updated_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_escalation_reference (
    id varchar(80) PRIMARY KEY,
    case_id varchar(80) NOT NULL,
    candidate_id varchar(80),
    target_type varchar(80) NOT NULL,
    target_reference_id varchar(80) NOT NULL,
    target_code_snapshot varchar(160),
    target_name_snapshot varchar(500),
    status varchar(40) NOT NULL,
    escalated_by_actor_id varchar(80),
    escalated_at timestamp with time zone NOT NULL,
    reason_text text,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_escalation_reference_case_id ON hidra_leak_detection_escalation_reference (case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_escalation_reference_candidate_id ON hidra_leak_detection_escalation_reference (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_escalation_reference_target_referenc ON hidra_leak_detection_escalation_reference (target_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_escalation_reference_status ON hidra_leak_detection_escalation_reference (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_escalation_reference_escalated_by_ac ON hidra_leak_detection_escalation_reference (escalated_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_escalation_reference_correlation_id ON hidra_leak_detection_escalation_reference (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_evidence_link (
    id varchar(80) PRIMARY KEY,
    candidate_id varchar(80) NOT NULL,
    evidence_type varchar(80) NOT NULL,
    evidence_reference_id varchar(80) NOT NULL,
    evidence_code_snapshot varchar(160),
    evidence_name_snapshot varchar(500),
    evidence_direction varchar(40) NOT NULL,
    weight numeric(10,6),
    description text,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_evidence_link_candidate_id ON hidra_leak_detection_evidence_link (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_evidence_link_evidence_reference_id ON hidra_leak_detection_evidence_link (evidence_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_evidence_link_created_at ON hidra_leak_detection_evidence_link (created_at);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_localization_estimate (
    id varchar(80) PRIMARY KEY,
    candidate_id varchar(80) NOT NULL,
    topology_asset_type varchar(160) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code varchar(160) NOT NULL,
    estimated_kilometer_point numeric(14,4),
    uncertainty_radius_meters numeric(14,4),
    latitude numeric(10,7),
    longitude numeric(10,7),
    confidence_score numeric(10,6) NOT NULL,
    method_id varchar(80) NOT NULL,
    estimated_at timestamp with time zone NOT NULL,
    notes text
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_localization_estimate_candidate_id ON hidra_leak_detection_localization_estimate (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_localization_estimate_topology_asset ON hidra_leak_detection_localization_estimate (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_localization_estimate_method_id ON hidra_leak_detection_localization_estimate (method_id);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_severity_assessment (
    id varchar(80) PRIMARY KEY,
    candidate_id varchar(80) NOT NULL,
    severity_level varchar(40) NOT NULL,
    confidence_score numeric(10,6) NOT NULL,
    estimated_leak_rate numeric(18,6),
    leak_rate_unit_id varchar(80),
    estimated_volume_loss numeric(18,6),
    volume_unit_id varchar(80),
    assessment_reason text,
    assessed_by_actor_id varchar(80),
    assessed_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_severity_assessment_candidate_id ON hidra_leak_detection_severity_assessment (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_severity_assessment_leak_rate_unit_i ON hidra_leak_detection_severity_assessment (leak_rate_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_severity_assessment_volume_unit_id ON hidra_leak_detection_severity_assessment (volume_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_severity_assessment_assessed_by_acto ON hidra_leak_detection_severity_assessment (assessed_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_leak_detection_verification_action (
    id varchar(80) PRIMARY KEY,
    candidate_id varchar(80) NOT NULL,
    case_id varchar(80),
    action_type varchar(80) NOT NULL,
    assigned_organization_unit_id varchar(80),
    assigned_actor_id varchar(80),
    status varchar(40) NOT NULL,
    requested_at timestamp with time zone NOT NULL,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    result_text text,
    correlation_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_verification_action_candidate_id ON hidra_leak_detection_verification_action (candidate_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_verification_action_case_id ON hidra_leak_detection_verification_action (case_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_verification_action_assigned_organiz ON hidra_leak_detection_verification_action (assigned_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_verification_action_assigned_actor_i ON hidra_leak_detection_verification_action (assigned_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_verification_action_status ON hidra_leak_detection_verification_action (status);
CREATE INDEX IF NOT EXISTS ix_hidra_leak_detection_verification_action_correlation_id ON hidra_leak_detection_verification_action (correlation_id);
