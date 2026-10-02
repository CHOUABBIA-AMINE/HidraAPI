-- HIDRA identity module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity
-- Module: identity

CREATE TABLE IF NOT EXISTS hidra_identity_attribute_definition (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255) NOT NULL,
    attribute_target varchar(80) NOT NULL,
    data_type varchar(80) NOT NULL,
    description text,
    multi_valued boolean NOT NULL,
    sensitive boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_attribute_definition_code ON hidra_identity_attribute_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_attribute_definition_status ON hidra_identity_attribute_definition (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_attribute_definition_created_at ON hidra_identity_attribute_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_attribute_definition_updated_at ON hidra_identity_attribute_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_authentication_event (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80),
    identity_provider_id varchar(80),
    external_identity_id varchar(80),
    external_subject text,
    event_type varchar(80) NOT NULL,
    protocol varchar(80) NOT NULL,
    client_ip varchar(80),
    user_agent text,
    failure_reason text,
    risk_score numeric(10,4),
    occurred_at timestamp with time zone NOT NULL,
    correlation_id varchar(120)
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authentication_event_user_id ON hidra_identity_authentication_event (user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authentication_event_identity_provider_id ON hidra_identity_authentication_event (identity_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authentication_event_external_identity_id ON hidra_identity_authentication_event (external_identity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authentication_event_correlation_id ON hidra_identity_authentication_event (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_identity_authorization_decision (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80) NOT NULL,
    permission_code varchar(160) NOT NULL,
    resource_type varchar(120),
    resource_reference_id varchar(120),
    scope_type varchar(80),
    scope_reference_id varchar(120),
    scope_code_snapshot varchar(120),
    decision varchar(40) NOT NULL,
    reason_code varchar(120),
    reason_message text,
    matched_grant_ids jsonb,
    matched_policy_rule_ids jsonb,
    external_claims_used jsonb,
    evaluated_at timestamp with time zone NOT NULL,
    correlation_id varchar(120),
    request_id varchar(120)
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_decision_user_id ON hidra_identity_authorization_decision (user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_decision_resource_reference_ ON hidra_identity_authorization_decision (resource_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_decision_scope_reference_id ON hidra_identity_authorization_decision (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_decision_correlation_id ON hidra_identity_authorization_decision (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_decision_request_id ON hidra_identity_authorization_decision (request_id);

CREATE TABLE IF NOT EXISTS hidra_identity_authorization_delegation_grant (
    id varchar(80) PRIMARY KEY,
    delegator_user_id varchar(80) NOT NULL,
    delegate_user_id varchar(80) NOT NULL,
    permission_id varchar(80),
    role_id varchar(80),
    scope_type varchar(80),
    scope_reference_id varchar(120),
    scope_code_snapshot varchar(120),
    approved_by_workflow_id varchar(120),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    revoked_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_delegator_u ON hidra_identity_authorization_delegation_grant (delegator_user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_delegate_us ON hidra_identity_authorization_delegation_grant (delegate_user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_permission_ ON hidra_identity_authorization_delegation_grant (permission_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_role_id ON hidra_identity_authorization_delegation_grant (role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_scope_refer ON hidra_identity_authorization_delegation_grant (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_approved_by ON hidra_identity_authorization_delegation_grant (approved_by_workflow_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_status ON hidra_identity_authorization_delegation_grant (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_delegation_grant_created_at ON hidra_identity_authorization_delegation_grant (created_at);

CREATE TABLE IF NOT EXISTS hidra_identity_authorization_policy (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255) NOT NULL,
    description text,
    policy_domain varchar(120) NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_code ON hidra_identity_authorization_policy (code);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_status ON hidra_identity_authorization_policy (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_created_at ON hidra_identity_authorization_policy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_updated_at ON hidra_identity_authorization_policy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_authorization_policy_rule (
    id varchar(80) PRIMARY KEY,
    policy_version_id varchar(80) NOT NULL,
    rule_code varchar(120) NOT NULL,
    effect varchar(40) NOT NULL,
    priority integer NOT NULL,
    subject_expression jsonb,
    resource_expression jsonb,
    action_expression jsonb,
    context_expression jsonb,
    obligation_expression jsonb,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_rule_policy_version_i ON hidra_identity_authorization_policy_rule (policy_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_rule_status ON hidra_identity_authorization_policy_rule (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_rule_created_at ON hidra_identity_authorization_policy_rule (created_at);

CREATE TABLE IF NOT EXISTS hidra_identity_authorization_policy_version (
    id varchar(80) PRIMARY KEY,
    policy_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone NOT NULL,
    effective_to timestamp with time zone,
    approved_by_workflow_id varchar(120),
    created_at timestamp with time zone NOT NULL,
    activated_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_version_policy_id ON hidra_identity_authorization_policy_version (policy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_version_status ON hidra_identity_authorization_policy_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_version_approved_by_w ON hidra_identity_authorization_policy_version (approved_by_workflow_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_authorization_policy_version_created_at ON hidra_identity_authorization_policy_version (created_at);

CREATE TABLE IF NOT EXISTS hidra_identity_external_group_mapping (
    id varchar(80) PRIMARY KEY,
    identity_provider_id varchar(80) NOT NULL,
    group_id varchar(80) NOT NULL,
    external_group_id text,
    external_group_name text NOT NULL,
    external_group_dn text,
    claim_name varchar(120),
    mapping_mode varchar(80) NOT NULL,
    auto_create_membership boolean NOT NULL,
    status varchar(40) NOT NULL,
    last_synced_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_group_mapping_identity_provider_i ON hidra_identity_external_group_mapping (identity_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_group_mapping_group_id ON hidra_identity_external_group_mapping (group_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_group_mapping_external_group_id ON hidra_identity_external_group_mapping (external_group_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_group_mapping_status ON hidra_identity_external_group_mapping (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_group_mapping_created_at ON hidra_identity_external_group_mapping (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_group_mapping_updated_at ON hidra_identity_external_group_mapping (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_external_identity (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80) NOT NULL,
    identity_provider_id varchar(80) NOT NULL,
    external_subject text NOT NULL,
    external_immutable_id text,
    external_username varchar(255),
    external_email varchar(254),
    external_display_name varchar(255),
    external_distinguished_name text,
    external_attributes_snapshot jsonb,
    last_login_at timestamp with time zone,
    last_synced_at timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_identity_user_id ON hidra_identity_external_identity (user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_identity_identity_provider_id ON hidra_identity_external_identity (identity_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_identity_external_immutable_id ON hidra_identity_external_identity (external_immutable_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_identity_status ON hidra_identity_external_identity (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_identity_created_at ON hidra_identity_external_identity (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_identity_updated_at ON hidra_identity_external_identity (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_external_permission_mapping (
    id varchar(80) PRIMARY KEY,
    identity_provider_id varchar(80) NOT NULL,
    permission_id varchar(80) NOT NULL,
    external_permission_code varchar(160) NOT NULL,
    claim_name varchar(120),
    mapping_mode varchar(80) NOT NULL,
    effect varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_permission_mapping_identity_provi ON hidra_identity_external_permission_mapping (identity_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_permission_mapping_permission_id ON hidra_identity_external_permission_mapping (permission_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_permission_mapping_status ON hidra_identity_external_permission_mapping (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_permission_mapping_created_at ON hidra_identity_external_permission_mapping (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_permission_mapping_updated_at ON hidra_identity_external_permission_mapping (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_external_role_mapping (
    id varchar(80) PRIMARY KEY,
    identity_provider_id varchar(80) NOT NULL,
    role_id varchar(80) NOT NULL,
    external_role_code varchar(160) NOT NULL,
    claim_name varchar(120),
    mapping_mode varchar(80) NOT NULL,
    scope_type varchar(80),
    scope_reference_id varchar(120),
    scope_code_snapshot varchar(120),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_role_mapping_identity_provider_id ON hidra_identity_external_role_mapping (identity_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_role_mapping_role_id ON hidra_identity_external_role_mapping (role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_role_mapping_scope_reference_id ON hidra_identity_external_role_mapping (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_role_mapping_status ON hidra_identity_external_role_mapping (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_role_mapping_created_at ON hidra_identity_external_role_mapping (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_external_role_mapping_updated_at ON hidra_identity_external_role_mapping (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_group (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    description text,
    group_type varchar(40) NOT NULL,
    source_provider_id varchar(120),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_code ON hidra_identity_group (code);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_source_provider_id ON hidra_identity_group (source_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_status ON hidra_identity_group (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_created_at ON hidra_identity_group (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_updated_at ON hidra_identity_group (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_group_role_grant (
    id varchar(80) PRIMARY KEY,
    group_id varchar(80) NOT NULL,
    role_id varchar(80) NOT NULL,
    scope_type varchar(80),
    scope_reference_id varchar(120),
    scope_code_snapshot varchar(120),
    grant_reason text,
    approved_by_workflow_id varchar(120),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_role_grant_group_id ON hidra_identity_group_role_grant (group_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_role_grant_role_id ON hidra_identity_group_role_grant (role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_role_grant_scope_reference_id ON hidra_identity_group_role_grant (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_role_grant_approved_by_workflow_id ON hidra_identity_group_role_grant (approved_by_workflow_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_role_grant_status ON hidra_identity_group_role_grant (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_group_role_grant_created_at ON hidra_identity_group_role_grant (created_at);

CREATE TABLE IF NOT EXISTS hidra_identity_provider (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255) NOT NULL,
    provider_type varchar(80) NOT NULL,
    issuer_uri text,
    authorization_endpoint text,
    token_endpoint text,
    jwks_uri text,
    directory_base_dn text,
    user_search_base text,
    group_search_base text,
    username_attribute varchar(120),
    email_attribute varchar(120),
    display_name_attribute varchar(120),
    external_id_attribute varchar(120),
    group_membership_attribute varchar(120),
    sync_enabled boolean NOT NULL,
    just_in_time_provisioning_enabled boolean NOT NULL,
    status varchar(40) NOT NULL,
    metadata jsonb,
    secret_reference varchar(255),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_provider_code ON hidra_identity_provider (code);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_provider_status ON hidra_identity_provider (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_provider_created_at ON hidra_identity_provider (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_provider_updated_at ON hidra_identity_provider (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_synchronization_job (
    id varchar(80) PRIMARY KEY,
    identity_provider_id varchar(80) NOT NULL,
    sync_type varchar(80) NOT NULL,
    trigger_type varchar(80) NOT NULL,
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    status varchar(40) NOT NULL,
    users_created integer NOT NULL,
    users_updated integer NOT NULL,
    users_disabled integer NOT NULL,
    groups_created integer NOT NULL,
    groups_updated integer NOT NULL,
    memberships_updated integer NOT NULL,
    error_message text,
    correlation_id varchar(120)
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_synchronization_job_identity_provider_id ON hidra_identity_synchronization_job (identity_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_synchronization_job_status ON hidra_identity_synchronization_job (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_synchronization_job_correlation_id ON hidra_identity_synchronization_job (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_identity_synchronization_record (
    id varchar(80) PRIMARY KEY,
    job_id varchar(80) NOT NULL,
    record_type varchar(80) NOT NULL,
    external_reference text NOT NULL,
    local_reference_id varchar(120),
    operation varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    message text,
    occurred_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_synchronization_record_job_id ON hidra_identity_synchronization_record (job_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_synchronization_record_local_reference_id ON hidra_identity_synchronization_record (local_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_synchronization_record_status ON hidra_identity_synchronization_record (status);

CREATE TABLE IF NOT EXISTS hidra_identity_login_session (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80) NOT NULL,
    identity_provider_id varchar(80),
    external_identity_id varchar(80),
    started_at timestamp with time zone NOT NULL,
    last_seen_at timestamp with time zone,
    expires_at timestamp with time zone,
    client_ip varchar(80),
    user_agent text,
    status varchar(40) NOT NULL,
    correlation_id varchar(120)
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_login_session_user_id ON hidra_identity_login_session (user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_login_session_identity_provider_id ON hidra_identity_login_session (identity_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_login_session_external_identity_id ON hidra_identity_login_session (external_identity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_login_session_status ON hidra_identity_login_session (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_login_session_correlation_id ON hidra_identity_login_session (correlation_id);

CREATE TABLE IF NOT EXISTS hidra_identity_permission (
    id varchar(80) PRIMARY KEY,
    code varchar(160) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    description text,
    permission_domain varchar(120) NOT NULL,
    resource_type varchar(120),
    action varchar(80) NOT NULL,
    sensitive boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_permission_code ON hidra_identity_permission (code);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_permission_status ON hidra_identity_permission (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_permission_created_at ON hidra_identity_permission (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_permission_updated_at ON hidra_identity_permission (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_role (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    description text,
    role_type varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_code ON hidra_identity_role (code);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_status ON hidra_identity_role (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_created_at ON hidra_identity_role (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_updated_at ON hidra_identity_role (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_role_permission_grant (
    id varchar(80) PRIMARY KEY,
    role_id varchar(80) NOT NULL,
    permission_id varchar(80) NOT NULL,
    effect varchar(40) NOT NULL,
    condition_expression jsonb,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_permission_grant_role_id ON hidra_identity_role_permission_grant (role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_permission_grant_permission_id ON hidra_identity_role_permission_grant (permission_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_permission_grant_status ON hidra_identity_role_permission_grant (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_role_permission_grant_created_at ON hidra_identity_role_permission_grant (created_at);

CREATE TABLE IF NOT EXISTS hidra_identity_subject_security_attribute (
    id varchar(80) PRIMARY KEY,
    subject_type varchar(80) NOT NULL,
    subject_id varchar(80) NOT NULL,
    attribute_definition_id varchar(80) NOT NULL,
    attribute_value text NOT NULL,
    source_provider_id varchar(120),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_subject_security_attribute_subject_id ON hidra_identity_subject_security_attribute (subject_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_subject_security_attribute_attribute_defin ON hidra_identity_subject_security_attribute (attribute_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_subject_security_attribute_source_provider ON hidra_identity_subject_security_attribute (source_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_subject_security_attribute_status ON hidra_identity_subject_security_attribute (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_subject_security_attribute_created_at ON hidra_identity_subject_security_attribute (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_subject_security_attribute_updated_at ON hidra_identity_subject_security_attribute (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_user_group_membership (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80) NOT NULL,
    group_id varchar(80) NOT NULL,
    membership_type varchar(80) NOT NULL,
    source_provider_id varchar(120),
    source_mapping_id varchar(120),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_group_membership_user_id ON hidra_identity_user_group_membership (user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_group_membership_group_id ON hidra_identity_user_group_membership (group_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_group_membership_source_provider_id ON hidra_identity_user_group_membership (source_provider_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_group_membership_source_mapping_id ON hidra_identity_user_group_membership (source_mapping_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_group_membership_status ON hidra_identity_user_group_membership (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_group_membership_created_at ON hidra_identity_user_group_membership (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_group_membership_updated_at ON hidra_identity_user_group_membership (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_user (
    id varchar(80) PRIMARY KEY,
    username varchar(120) NOT NULL,
    email_address varchar(254),
    display_name varchar(255),
    user_type varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    employee_reference_id varchar(120),
    last_authenticated_at timestamp with time zone,
    failed_login_count integer NOT NULL,
    locked_until timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    activated_at timestamp with time zone,
    suspended_at timestamp with time zone,
    disabled_at timestamp with time zone,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_status ON hidra_identity_user (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_employee_reference_id ON hidra_identity_user (employee_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_created_at ON hidra_identity_user (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_updated_at ON hidra_identity_user (updated_at);

CREATE TABLE IF NOT EXISTS hidra_identity_user_permission_grant (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80) NOT NULL,
    permission_id varchar(80) NOT NULL,
    effect varchar(40) NOT NULL,
    scope_type varchar(80),
    scope_reference_id varchar(120),
    scope_code_snapshot varchar(120),
    grant_reason text NOT NULL,
    approved_by_workflow_id varchar(120),
    emergency_access boolean NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    revoked_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_permission_grant_user_id ON hidra_identity_user_permission_grant (user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_permission_grant_permission_id ON hidra_identity_user_permission_grant (permission_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_permission_grant_scope_reference_id ON hidra_identity_user_permission_grant (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_permission_grant_approved_by_workflow ON hidra_identity_user_permission_grant (approved_by_workflow_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_permission_grant_status ON hidra_identity_user_permission_grant (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_permission_grant_created_at ON hidra_identity_user_permission_grant (created_at);

CREATE TABLE IF NOT EXISTS hidra_identity_user_role_grant (
    id varchar(80) PRIMARY KEY,
    user_id varchar(80) NOT NULL,
    role_id varchar(80) NOT NULL,
    scope_type varchar(80),
    scope_reference_id varchar(120),
    scope_code_snapshot varchar(120),
    grant_reason text,
    approved_by_workflow_id varchar(120),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    revoked_at timestamp with time zone,
    revoked_reason text
);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_role_grant_user_id ON hidra_identity_user_role_grant (user_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_role_grant_role_id ON hidra_identity_user_role_grant (role_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_role_grant_scope_reference_id ON hidra_identity_user_role_grant (scope_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_role_grant_approved_by_workflow_id ON hidra_identity_user_role_grant (approved_by_workflow_id);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_role_grant_status ON hidra_identity_user_role_grant (status);
CREATE INDEX IF NOT EXISTS ix_hidra_identity_user_role_grant_created_at ON hidra_identity_user_role_grant (created_at);
