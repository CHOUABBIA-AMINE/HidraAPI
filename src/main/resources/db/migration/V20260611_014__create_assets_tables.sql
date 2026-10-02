-- HIDRA assets module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity
-- Module: assets

CREATE TABLE IF NOT EXISTS hidra_asset_catalog_entry (
    id varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_catalog_entry_code ON hidra_asset_catalog_entry (code);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_catalog_entry_active ON hidra_asset_catalog_entry (active);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_catalog_entry_created_at ON hidra_asset_catalog_entry (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_catalog_entry_updated_at ON hidra_asset_catalog_entry (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_catalog_translation (
    id varchar(80) PRIMARY KEY,
    catalog_entry_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_catalog_translation_catalog_entry_id ON hidra_asset_catalog_translation (catalog_entry_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_catalog_translation_created_at ON hidra_asset_catalog_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_catalog_translation_updated_at ON hidra_asset_catalog_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_condition_record (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    condition_status varchar(40) NOT NULL,
    condition_type_id varchar(80),
    source_module varchar(80),
    source_reference_id varchar(80),
    summary text,
    condition_score numeric(10,4),
    observed_at timestamp with time zone NOT NULL,
    observed_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_condition_record_maintainable_asset_id ON hidra_asset_condition_record (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_condition_record_condition_type_id ON hidra_asset_condition_record (condition_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_condition_record_source_reference_id ON hidra_asset_condition_record (source_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_condition_record_observed_by_actor_id ON hidra_asset_condition_record (observed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_condition_record_created_at ON hidra_asset_condition_record (created_at);

CREATE TABLE IF NOT EXISTS hidra_asset_document_reference (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    document_type varchar(80) NOT NULL,
    document_reference_id varchar(80) NOT NULL,
    document_code_snapshot varchar(160),
    document_title_snapshot varchar(255),
    attached_at timestamp with time zone NOT NULL,
    attached_by_actor_id varchar(80)
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_document_reference_maintainable_asset_id ON hidra_asset_document_reference (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_document_reference_document_reference_id ON hidra_asset_document_reference (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_document_reference_attached_by_actor_id ON hidra_asset_document_reference (attached_by_actor_id);

CREATE TABLE IF NOT EXISTS hidra_asset_installation (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    installation_number varchar(80) NOT NULL,
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    installed_at timestamp with time zone NOT NULL,
    commissioned_at timestamp with time zone,
    installed_by_party_id varchar(80),
    installed_by_name_snapshot varchar(255),
    commissioning_document_id varchar(80),
    notes text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_installation_maintainable_asset_id ON hidra_asset_installation (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_installation_topology_asset_id ON hidra_asset_installation (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_installation_installed_by_party_id ON hidra_asset_installation (installed_by_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_installation_commissioning_document_id ON hidra_asset_installation (commissioning_document_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_installation_created_at ON hidra_asset_installation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_installation_updated_at ON hidra_asset_installation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_lifecycle_event (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    event_type varchar(80) NOT NULL,
    old_status varchar(40),
    new_status varchar(40) NOT NULL,
    event_reason_id varchar(80),
    event_comment text,
    actor_id varchar(80),
    event_at timestamp with time zone NOT NULL,
    correlation_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_lifecycle_event_maintainable_asset_id ON hidra_asset_lifecycle_event (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_lifecycle_event_event_reason_id ON hidra_asset_lifecycle_event (event_reason_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_lifecycle_event_actor_id ON hidra_asset_lifecycle_event (actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_lifecycle_event_correlation_id ON hidra_asset_lifecycle_event (correlation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_lifecycle_event_created_at ON hidra_asset_lifecycle_event (created_at);

CREATE TABLE IF NOT EXISTS hidra_asset_manufacturer_reference (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80),
    manufacturer_party_id varchar(80) NOT NULL,
    manufacturer_code_snapshot varchar(160),
    manufacturer_name_snapshot varchar(255),
    manufacturer_role_code_snapshot varchar(80),
    manufacturer_reference_number varchar(160),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_manufacturer_reference_maintainable_asset_id ON hidra_asset_manufacturer_reference (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_manufacturer_reference_manufacturer_party_id ON hidra_asset_manufacturer_reference (manufacturer_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_manufacturer_reference_created_at ON hidra_asset_manufacturer_reference (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_manufacturer_reference_updated_at ON hidra_asset_manufacturer_reference (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_meter_reading_reference (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    reading_type varchar(80) NOT NULL,
    reading_reference_id varchar(80) NOT NULL,
    reading_code_snapshot varchar(160),
    reading_value_snapshot numeric(18,6),
    unit_id varchar(80),
    reading_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_meter_reading_reference_maintainable_asset_id ON hidra_asset_meter_reading_reference (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_meter_reading_reference_reading_reference_id ON hidra_asset_meter_reading_reference (reading_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_meter_reading_reference_unit_id ON hidra_asset_meter_reading_reference (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_meter_reading_reference_created_at ON hidra_asset_meter_reading_reference (created_at);

CREATE TABLE IF NOT EXISTS hidra_asset_model (
    id varchar(80) PRIMARY KEY,
    model_code varchar(80) NOT NULL,
    model_name varchar(160) NOT NULL,
    asset_type_id varchar(80) NOT NULL,
    manufacturer_party_id varchar(80),
    manufacturer_name_snapshot varchar(255),
    technical_description text,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_model_asset_type_id ON hidra_asset_model (asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_model_manufacturer_party_id ON hidra_asset_model (manufacturer_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_model_active ON hidra_asset_model (active);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_model_created_at ON hidra_asset_model (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_model_updated_at ON hidra_asset_model (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_serial_identity (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    serial_number varchar(160) NOT NULL,
    batch_number varchar(160),
    manufacturer_part_number varchar(160),
    nameplate_data_json jsonb,
    manufactured_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_serial_identity_maintainable_asset_id ON hidra_asset_serial_identity (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_serial_identity_created_at ON hidra_asset_serial_identity (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_serial_identity_updated_at ON hidra_asset_serial_identity (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_service_contract_reference (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    contract_reference_id varchar(80) NOT NULL,
    contract_code_snapshot varchar(160),
    service_provider_party_id varchar(80),
    service_provider_name_snapshot varchar(255),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_service_contract_reference_maintainable_asset ON hidra_asset_service_contract_reference (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_service_contract_reference_contract_reference ON hidra_asset_service_contract_reference (contract_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_service_contract_reference_service_provider_p ON hidra_asset_service_contract_reference (service_provider_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_service_contract_reference_active ON hidra_asset_service_contract_reference (active);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_service_contract_reference_created_at ON hidra_asset_service_contract_reference (created_at);

CREATE TABLE IF NOT EXISTS hidra_asset_spare_part_compatibility (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80),
    asset_type_id varchar(80),
    asset_model_id varchar(80),
    spare_part_id varchar(80) NOT NULL,
    compatibility_rule text,
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_compatibility_maintainable_asset_i ON hidra_asset_spare_part_compatibility (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_compatibility_asset_type_id ON hidra_asset_spare_part_compatibility (asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_compatibility_asset_model_id ON hidra_asset_spare_part_compatibility (asset_model_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_compatibility_spare_part_id ON hidra_asset_spare_part_compatibility (spare_part_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_compatibility_status ON hidra_asset_spare_part_compatibility (status);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_compatibility_created_at ON hidra_asset_spare_part_compatibility (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_compatibility_updated_at ON hidra_asset_spare_part_compatibility (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_technical_attribute_definition (
    id varchar(80) PRIMARY KEY,
    asset_type_id varchar(80) NOT NULL,
    code varchar(80) NOT NULL,
    name varchar(160) NOT NULL,
    data_type varchar(40) NOT NULL,
    unit_id varchar(80),
    required boolean NOT NULL,
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_definition_asset_type_id ON hidra_asset_technical_attribute_definition (asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_definition_code ON hidra_asset_technical_attribute_definition (code);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_definition_unit_id ON hidra_asset_technical_attribute_definition (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_definition_active ON hidra_asset_technical_attribute_definition (active);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_definition_created_at ON hidra_asset_technical_attribute_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_definition_updated_at ON hidra_asset_technical_attribute_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_technical_attribute_value (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    attribute_definition_id varchar(80) NOT NULL,
    text_value text,
    numeric_value numeric(18,6),
    boolean_value boolean NOT NULL,
    date_value timestamp with time zone,
    catalog_value_id varchar(80),
    unit_id varchar(80),
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_value_maintainable_asset_ ON hidra_asset_technical_attribute_value (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_value_attribute_definitio ON hidra_asset_technical_attribute_value (attribute_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_value_catalog_value_id ON hidra_asset_technical_attribute_value (catalog_value_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_value_unit_id ON hidra_asset_technical_attribute_value (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_value_created_at ON hidra_asset_technical_attribute_value (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_technical_attribute_value_updated_at ON hidra_asset_technical_attribute_value (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_type (
    id varchar(80) PRIMARY KEY,
    code varchar(80) NOT NULL,
    parent_type_id varchar(80),
    active boolean NOT NULL,
    sort_order integer NOT NULL,
    system_defined boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_code ON hidra_asset_type (code);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_parent_type_id ON hidra_asset_type (parent_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_active ON hidra_asset_type (active);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_created_at ON hidra_asset_type (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_updated_at ON hidra_asset_type (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_type_translation (
    id varchar(80) PRIMARY KEY,
    asset_type_id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_translation_asset_type_id ON hidra_asset_type_translation (asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_translation_created_at ON hidra_asset_type_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_type_translation_updated_at ON hidra_asset_type_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_warranty (
    id varchar(80) PRIMARY KEY,
    maintainable_asset_id varchar(80) NOT NULL,
    warranty_number varchar(80) NOT NULL,
    provider_party_id varchar(80),
    provider_name_snapshot varchar(255),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone NOT NULL,
    status varchar(40) NOT NULL,
    terms_summary text,
    document_reference_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_warranty_maintainable_asset_id ON hidra_asset_warranty (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_warranty_provider_party_id ON hidra_asset_warranty (provider_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_warranty_status ON hidra_asset_warranty (status);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_warranty_document_reference_id ON hidra_asset_warranty (document_reference_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_warranty_created_at ON hidra_asset_warranty (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_warranty_updated_at ON hidra_asset_warranty (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_maintainable_asset (
    id varchar(80) PRIMARY KEY,
    asset_number varchar(80) NOT NULL,
    asset_code varchar(160) NOT NULL,
    asset_name varchar(255) NOT NULL,
    asset_type_id varchar(80) NOT NULL,
    topology_asset_type_code varchar(80) NOT NULL,
    topology_asset_id varchar(80) NOT NULL,
    topology_asset_code_snapshot varchar(160),
    topology_asset_name_snapshot varchar(500),
    parent_asset_id varchar(80),
    status varchar(40) NOT NULL,
    criticality_id varchar(80),
    owner_organization_unit_id varchar(80),
    owner_organization_unit_name_snapshot varchar(500),
    manufacturer_party_id varchar(80),
    manufacturer_name_snapshot varchar(255),
    model_id varchar(80),
    serial_identity_id varchar(80),
    registered_at timestamp with time zone NOT NULL,
    installed_at timestamp with time zone,
    commissioned_at timestamp with time zone,
    retired_at timestamp with time zone,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_asset_type_id ON hidra_asset_maintainable_asset (asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_topology_asset_id ON hidra_asset_maintainable_asset (topology_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_parent_asset_id ON hidra_asset_maintainable_asset (parent_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_status ON hidra_asset_maintainable_asset (status);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_criticality_id ON hidra_asset_maintainable_asset (criticality_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_owner_organization_unit_id ON hidra_asset_maintainable_asset (owner_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_manufacturer_party_id ON hidra_asset_maintainable_asset (manufacturer_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_model_id ON hidra_asset_maintainable_asset (model_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_serial_identity_id ON hidra_asset_maintainable_asset (serial_identity_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_created_by_actor_id ON hidra_asset_maintainable_asset (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_created_at ON hidra_asset_maintainable_asset (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintainable_asset_updated_at ON hidra_asset_maintainable_asset (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_maintenance_execution_record (
    id varchar(80) PRIMARY KEY,
    work_order_id varchar(80) NOT NULL,
    task_id varchar(80),
    execution_result varchar(40) NOT NULL,
    performed_by_actor_id varchar(80),
    executed_at timestamp with time zone NOT NULL,
    duration_minutes integer,
    result_summary text,
    measurement_json jsonb,
    follow_up_recommendation_id varchar(80),
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_execution_record_work_order_id ON hidra_asset_maintenance_execution_record (work_order_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_execution_record_task_id ON hidra_asset_maintenance_execution_record (task_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_execution_record_performed_by_act ON hidra_asset_maintenance_execution_record (performed_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_execution_record_follow_up_recomm ON hidra_asset_maintenance_execution_record (follow_up_recommendation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_execution_record_created_at ON hidra_asset_maintenance_execution_record (created_at);

CREATE TABLE IF NOT EXISTS hidra_asset_maintenance_plan (
    id varchar(80) PRIMARY KEY,
    plan_code varchar(80) NOT NULL,
    name varchar(160) NOT NULL,
    maintainable_asset_id varchar(80) NOT NULL,
    maintenance_strategy_id varchar(80),
    frequency_id varchar(80),
    next_due_at timestamp with time zone,
    last_executed_at timestamp with time zone,
    status varchar(40) NOT NULL,
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_plan_maintainable_asset_id ON hidra_asset_maintenance_plan (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_plan_maintenance_strategy_id ON hidra_asset_maintenance_plan (maintenance_strategy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_plan_frequency_id ON hidra_asset_maintenance_plan (frequency_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_plan_status ON hidra_asset_maintenance_plan (status);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_plan_created_by_actor_id ON hidra_asset_maintenance_plan (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_plan_created_at ON hidra_asset_maintenance_plan (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_plan_updated_at ON hidra_asset_maintenance_plan (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_maintenance_strategy (
    id varchar(80) PRIMARY KEY,
    strategy_code varchar(80) NOT NULL,
    name varchar(160) NOT NULL,
    strategy_type_id varchar(80) NOT NULL,
    asset_type_id varchar(80),
    description text,
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_strategy_strategy_type_id ON hidra_asset_maintenance_strategy (strategy_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_strategy_asset_type_id ON hidra_asset_maintenance_strategy (asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_strategy_status ON hidra_asset_maintenance_strategy (status);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_strategy_created_at ON hidra_asset_maintenance_strategy (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_strategy_updated_at ON hidra_asset_maintenance_strategy (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_maintenance_task_template (
    id varchar(80) PRIMARY KEY,
    template_code varchar(80) NOT NULL,
    name varchar(160) NOT NULL,
    asset_type_id varchar(80),
    maintenance_strategy_id varchar(80),
    task_type_id varchar(80) NOT NULL,
    instructions text,
    estimated_duration_minutes integer,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_task_template_asset_type_id ON hidra_asset_maintenance_task_template (asset_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_task_template_maintenance_strateg ON hidra_asset_maintenance_task_template (maintenance_strategy_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_task_template_task_type_id ON hidra_asset_maintenance_task_template (task_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_task_template_active ON hidra_asset_maintenance_task_template (active);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_task_template_created_at ON hidra_asset_maintenance_task_template (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_task_template_updated_at ON hidra_asset_maintenance_task_template (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_maintenance_work_order (
    id varchar(80) PRIMARY KEY,
    work_order_number varchar(80) NOT NULL,
    maintainable_asset_id varchar(80) NOT NULL,
    maintenance_plan_id varchar(80),
    source_recommendation_id varchar(80),
    work_order_type_id varchar(80) NOT NULL,
    priority_id varchar(80),
    status varchar(40) NOT NULL,
    title varchar(255) NOT NULL,
    description text,
    assigned_organization_unit_id varchar(80),
    assigned_actor_id varchar(80),
    planned_start_at timestamp with time zone,
    planned_end_at timestamp with time zone,
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    workflow_instance_id varchar(80),
    created_by_actor_id varchar(80),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_maintainable_asset_id ON hidra_asset_maintenance_work_order (maintainable_asset_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_maintenance_plan_id ON hidra_asset_maintenance_work_order (maintenance_plan_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_source_recommendation_ ON hidra_asset_maintenance_work_order (source_recommendation_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_work_order_type_id ON hidra_asset_maintenance_work_order (work_order_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_priority_id ON hidra_asset_maintenance_work_order (priority_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_status ON hidra_asset_maintenance_work_order (status);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_assigned_organization_ ON hidra_asset_maintenance_work_order (assigned_organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_assigned_actor_id ON hidra_asset_maintenance_work_order (assigned_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_workflow_instance_id ON hidra_asset_maintenance_work_order (workflow_instance_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_created_by_actor_id ON hidra_asset_maintenance_work_order (created_by_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_created_at ON hidra_asset_maintenance_work_order (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_updated_at ON hidra_asset_maintenance_work_order (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_maintenance_work_order_task (
    id varchar(80) PRIMARY KEY,
    work_order_id varchar(80) NOT NULL,
    task_template_id varchar(80),
    task_number varchar(80) NOT NULL,
    task_type_id varchar(80) NOT NULL,
    description text,
    status varchar(40) NOT NULL,
    sequence_number integer NOT NULL,
    assigned_actor_id varchar(80),
    started_at timestamp with time zone,
    completed_at timestamp with time zone,
    result_summary text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_task_work_order_id ON hidra_asset_maintenance_work_order_task (work_order_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_task_task_template_id ON hidra_asset_maintenance_work_order_task (task_template_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_task_task_type_id ON hidra_asset_maintenance_work_order_task (task_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_task_status ON hidra_asset_maintenance_work_order_task (status);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_task_assigned_actor_id ON hidra_asset_maintenance_work_order_task (assigned_actor_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_task_created_at ON hidra_asset_maintenance_work_order_task (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_maintenance_work_order_task_updated_at ON hidra_asset_maintenance_work_order_task (updated_at);

CREATE TABLE IF NOT EXISTS hidra_asset_spare_part (
    id varchar(80) PRIMARY KEY,
    part_number varchar(80) NOT NULL,
    name varchar(160) NOT NULL,
    description text,
    manufacturer_party_id varchar(80),
    manufacturer_name_snapshot varchar(255),
    unit_id varchar(80),
    category_id varchar(80),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_manufacturer_party_id ON hidra_asset_spare_part (manufacturer_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_unit_id ON hidra_asset_spare_part (unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_category_id ON hidra_asset_spare_part (category_id);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_active ON hidra_asset_spare_part (active);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_created_at ON hidra_asset_spare_part (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_asset_spare_part_updated_at ON hidra_asset_spare_part (updated_at);
