-- HIDRA configuration module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity
-- Module: configuration

CREATE TABLE IF NOT EXISTS hidra_configuration_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_catalog_entry_code ON hidra_configuration_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_catalog_entry_active ON hidra_configuration_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_catalog_entry_created_at ON hidra_configuration_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_catalog_entry_updated_at ON hidra_configuration_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description varchar(500),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_catalog_translation_catalog_entry_id ON hidra_configuration_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_catalog_translation_created_at ON hidra_configuration_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_catalog_translation_updated_at ON hidra_configuration_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_change_request (
    id varchar(80) PRIMARY KEY,
    request_number varchar(120) NOT NULL,
    definition_id varchar(80),
    profile_id varchar(80),
    feature_flag_id varchar(80),
    requested_value varchar(4000),
    requested_json_value jsonb,
    status varchar(40) NOT NULL,
    reason varchar(1000),
    requested_by_actor_id varchar(80) NOT NULL,
    requested_at timestamp with time zone NOT NULL,
    workflow_instance_id varchar(80),
    approved_by_actor_id varchar(80),
    approved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_definition_id ON hidra_configuration_change_request (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_profile_id ON hidra_configuration_change_request (profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_feature_flag_id ON hidra_configuration_change_request (feature_flag_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_status ON hidra_configuration_change_request (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_requested_by_actor_id ON hidra_configuration_change_request (requested_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_workflow_instance_id ON hidra_configuration_change_request (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_approved_by_actor_id ON hidra_configuration_change_request (approved_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_created_at ON hidra_configuration_change_request (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_change_request_updated_at ON hidra_configuration_change_request (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_definition (
    id varchar(80) PRIMARY KEY,
    namespace_id varchar(80) NOT NULL,
    key varchar(160) NOT NULL,
    display_name_fr varchar(160) NOT NULL,
    display_name_ar varchar(160),
    display_name_en varchar(160),
    value_type varchar(40) NOT NULL,
    sensitivity varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    scoped boolean NOT NULL,
    requires_approval boolean NOT NULL,
    default_value varchar(2000),
    description varchar(1000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_namespace_id ON hidra_configuration_definition (namespace_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_status ON hidra_configuration_definition (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_created_at ON hidra_configuration_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_updated_at ON hidra_configuration_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_definition_version (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    schema_json jsonb,
    default_value varchar(2000),
    validation_summary varchar(1000),
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    active boolean NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_version_definition_id ON hidra_configuration_definition_version (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_version_created_by_actor_i ON hidra_configuration_definition_version (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_version_created_at ON hidra_configuration_definition_version (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_definition_version_active ON hidra_configuration_definition_version (active);

CREATE TABLE IF NOT EXISTS hidra_configuration_deployment (
    id varchar(80) PRIMARY KEY,
    deployment_number varchar(120) NOT NULL,
    change_request_id varchar(80),
    profile_id varchar(80),
    environment varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    deployed_by_actor_id varchar(80),
    started_at timestamp with time zone NOT NULL,
    completed_at timestamp with time zone,
    rollback_deployment_id varchar(80),
    failure_reason varchar(2000),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_deployment_change_request_id ON hidra_configuration_deployment (change_request_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_deployment_profile_id ON hidra_configuration_deployment (profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_deployment_status ON hidra_configuration_deployment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_deployment_deployed_by_actor_id ON hidra_configuration_deployment (deployed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_deployment_rollback_deployment_id ON hidra_configuration_deployment (rollback_deployment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_deployment_created_at ON hidra_configuration_deployment (created_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_external_reference (
    id varchar(80) PRIMARY KEY,
    target_type varchar(120) NOT NULL,
    target_id varchar(120) NOT NULL,
    target_code_snapshot varchar(120),
    target_label_snapshot varchar(240),
    reference_module varchar(80),
    reference_type varchar(120) NOT NULL,
    reference_id varchar(120) NOT NULL,
    reference_code_snapshot varchar(120),
    reference_label_snapshot varchar(240),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_external_reference_target_id ON hidra_configuration_external_reference (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_external_reference_reference_id ON hidra_configuration_external_reference (reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_external_reference_active ON hidra_configuration_external_reference (active);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_external_reference_created_at ON hidra_configuration_external_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_external_reference_updated_at ON hidra_configuration_external_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_namespace (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_fr varchar(160) NOT NULL,
    name_ar varchar(160),
    name_en varchar(160),
    owner_module varchar(80),
    owner_team varchar(160),
    status varchar(40) NOT NULL,
    description varchar(1000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_namespace_code ON hidra_configuration_namespace (code);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_namespace_status ON hidra_configuration_namespace (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_namespace_created_at ON hidra_configuration_namespace (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_namespace_updated_at ON hidra_configuration_namespace (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_profile_entry (
    id varchar(80) PRIMARY KEY,
    profile_id varchar(80) NOT NULL,
    definition_id varchar(80) NOT NULL,
    configuration_value_id varchar(80),
    scoped_override_id varchar(80),
    priority_order integer NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_entry_profile_id ON hidra_configuration_profile_entry (profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_entry_definition_id ON hidra_configuration_profile_entry (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_entry_configuration_value_id ON hidra_configuration_profile_entry (configuration_value_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_entry_scoped_override_id ON hidra_configuration_profile_entry (scoped_override_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_entry_active ON hidra_configuration_profile_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_entry_created_at ON hidra_configuration_profile_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_entry_updated_at ON hidra_configuration_profile_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_profile (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_fr varchar(160) NOT NULL,
    name_ar varchar(160),
    name_en varchar(160),
    environment varchar(40) NOT NULL,
    status varchar(40) NOT NULL,
    description varchar(1000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_code ON hidra_configuration_profile (code);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_status ON hidra_configuration_profile (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_created_at ON hidra_configuration_profile (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_profile_updated_at ON hidra_configuration_profile (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_validation_rule (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    rule_code varchar(120) NOT NULL,
    rule_type_id varchar(80) NOT NULL,
    expression varchar(2000),
    configuration_json jsonb,
    status varchar(40) NOT NULL,
    failure_message varchar(1000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_validation_rule_definition_id ON hidra_configuration_validation_rule (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_validation_rule_rule_type_id ON hidra_configuration_validation_rule (rule_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_validation_rule_status ON hidra_configuration_validation_rule (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_validation_rule_created_at ON hidra_configuration_validation_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_validation_rule_updated_at ON hidra_configuration_validation_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_value (
    id varchar(80) PRIMARY KEY,
    definition_id varchar(80) NOT NULL,
    definition_version_id varchar(80),
    environment varchar(40) NOT NULL,
    raw_value varchar(4000),
    json_value jsonb,
    secret_reference varchar(255),
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_value_definition_id ON hidra_configuration_value (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_value_definition_version_id ON hidra_configuration_value (definition_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_value_status ON hidra_configuration_value (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_value_created_by_actor_id ON hidra_configuration_value (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_value_created_at ON hidra_configuration_value (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_value_updated_at ON hidra_configuration_value (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_feature_flag (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_fr varchar(160) NOT NULL,
    name_ar varchar(160),
    name_en varchar(160),
    owning_module varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    evaluation_strategy varchar(40) NOT NULL,
    default_enabled boolean NOT NULL,
    description varchar(1000),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_code ON hidra_configuration_feature_flag (code);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_status ON hidra_configuration_feature_flag (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_created_at ON hidra_configuration_feature_flag (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_updated_at ON hidra_configuration_feature_flag (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_feature_flag_rule (
    id varchar(80) PRIMARY KEY,
    feature_flag_id varchar(80) NOT NULL,
    rule_name varchar(160) NOT NULL,
    scope_type varchar(40) NOT NULL,
    scope_id varchar(120),
    condition_expression varchar(2000),
    percentage integer,
    enabled boolean NOT NULL,
    priority_order integer NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_rule_feature_flag_id ON hidra_configuration_feature_flag_rule (feature_flag_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_rule_scope_id ON hidra_configuration_feature_flag_rule (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_rule_active ON hidra_configuration_feature_flag_rule (active);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_rule_created_at ON hidra_configuration_feature_flag_rule (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_feature_flag_rule_updated_at ON hidra_configuration_feature_flag_rule (updated_at);

CREATE TABLE IF NOT EXISTS hidra_configuration_resolved_snapshot (
    id varchar(80) PRIMARY KEY,
    snapshot_number varchar(120) NOT NULL,
    profile_id varchar(80),
    namespace_id varchar(80),
    target_module varchar(80),
    scope_type varchar(40),
    scope_id varchar(120),
    environment varchar(40) NOT NULL,
    resolved_values_json jsonb NOT NULL,
    hash_value varchar(256) NOT NULL,
    status varchar(40) NOT NULL,
    resolved_at timestamp with time zone NOT NULL,
    expires_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_resolved_snapshot_profile_id ON hidra_configuration_resolved_snapshot (profile_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_resolved_snapshot_namespace_id ON hidra_configuration_resolved_snapshot (namespace_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_resolved_snapshot_scope_id ON hidra_configuration_resolved_snapshot (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_resolved_snapshot_status ON hidra_configuration_resolved_snapshot (status);

CREATE TABLE IF NOT EXISTS hidra_configuration_scoped_override (
    id varchar(80) PRIMARY KEY,
    configuration_value_id varchar(80) NOT NULL,
    definition_id varchar(80) NOT NULL,
    scope_type varchar(40) NOT NULL,
    scope_id varchar(120),
    module_name varchar(80),
    organization_unit_id varchar(80),
    override_value varchar(4000),
    override_json_value jsonb,
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_scoped_override_configuration_value_i ON hidra_configuration_scoped_override (configuration_value_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_scoped_override_definition_id ON hidra_configuration_scoped_override (definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_scoped_override_scope_id ON hidra_configuration_scoped_override (scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_scoped_override_organization_unit_id ON hidra_configuration_scoped_override (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_scoped_override_status ON hidra_configuration_scoped_override (status);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_scoped_override_created_at ON hidra_configuration_scoped_override (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_configuration_scoped_override_updated_at ON hidra_configuration_scoped_override (updated_at);
