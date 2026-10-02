-- HIDRA topology module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity
-- Module: topology

CREATE TABLE IF NOT EXISTS hidra_topology_equipment_attribute_definition (
    id varchar(80) PRIMARY KEY,
    equipment_type_version_id varchar(80) NOT NULL,
    attribute_code varchar(120) NOT NULL,
    label varchar(255),
    data_type varchar(80) NOT NULL,
    unit_code varchar(40),
    required boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_definition_equipment_t ON hidra_topology_equipment_attribute_definition (equipment_type_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_definition_status ON hidra_topology_equipment_attribute_definition (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_definition_created_at ON hidra_topology_equipment_attribute_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_definition_updated_at ON hidra_topology_equipment_attribute_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_equipment_attribute_value (
    id varchar(80) PRIMARY KEY,
    equipment_id varchar(80) NOT NULL,
    attribute_definition_id varchar(80) NOT NULL,
    value_text text,
    value_number numeric(14,4),
    value_json jsonb,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_value_equipment_id ON hidra_topology_equipment_attribute_value (equipment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_value_attribute_defini ON hidra_topology_equipment_attribute_value (attribute_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_value_created_at ON hidra_topology_equipment_attribute_value (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_attribute_value_updated_at ON hidra_topology_equipment_attribute_value (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_equipment (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255),
    facility_id varchar(80),
    node_id varchar(80),
    pipeline_segment_id varchar(80),
    equipment_type_id varchar(80) NOT NULL,
    equipment_kind varchar(80) NOT NULL,
    manufacturer_party_id varchar(80),
    manufacturer_party_code_snapshot varchar(120),
    manufacturer_party_name_snapshot varchar(255),
    status varchar(40) NOT NULL,
    installed_at timestamp with time zone,
    retired_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_code ON hidra_topology_equipment (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_facility_id ON hidra_topology_equipment (facility_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_node_id ON hidra_topology_equipment (node_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_pipeline_segment_id ON hidra_topology_equipment (pipeline_segment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_equipment_type_id ON hidra_topology_equipment (equipment_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_manufacturer_party_id ON hidra_topology_equipment (manufacturer_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_status ON hidra_topology_equipment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_created_at ON hidra_topology_equipment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_updated_at ON hidra_topology_equipment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_equipment_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255) NOT NULL,
    equipment_kind varchar(80) NOT NULL,
    description text,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_code ON hidra_topology_equipment_type (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_status ON hidra_topology_equipment_type (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_created_at ON hidra_topology_equipment_type (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_updated_at ON hidra_topology_equipment_type (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_equipment_type_version (
    id varchar(80) PRIMARY KEY,
    equipment_type_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    definition_payload jsonb,
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_version_equipment_type_id ON hidra_topology_equipment_type_version (equipment_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_version_status ON hidra_topology_equipment_type_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_version_created_at ON hidra_topology_equipment_type_version (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_equipment_type_version_updated_at ON hidra_topology_equipment_type_version (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_facility_attribute_definition (
    id varchar(80) PRIMARY KEY,
    facility_type_version_id varchar(80) NOT NULL,
    attribute_code varchar(120) NOT NULL,
    label varchar(255),
    data_type varchar(80) NOT NULL,
    unit_code varchar(40),
    required boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_definition_facility_typ ON hidra_topology_facility_attribute_definition (facility_type_version_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_definition_status ON hidra_topology_facility_attribute_definition (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_definition_created_at ON hidra_topology_facility_attribute_definition (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_definition_updated_at ON hidra_topology_facility_attribute_definition (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_facility_attribute_value (
    id varchar(80) PRIMARY KEY,
    facility_id varchar(80) NOT NULL,
    attribute_definition_id varchar(80) NOT NULL,
    value_text text,
    value_number numeric(14,4),
    value_json jsonb,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_value_facility_id ON hidra_topology_facility_attribute_value (facility_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_value_attribute_definit ON hidra_topology_facility_attribute_value (attribute_definition_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_value_created_at ON hidra_topology_facility_attribute_value (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_attribute_value_updated_at ON hidra_topology_facility_attribute_value (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_facility (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    facility_type_id varchar(80) NOT NULL,
    facility_kind varchar(80) NOT NULL,
    owner_party_id varchar(80),
    owner_party_code_snapshot varchar(120),
    owner_party_name_snapshot varchar(255),
    latitude numeric(10,7),
    longitude numeric(10,7),
    elevation_meters numeric(12,4),
    status varchar(40) NOT NULL,
    commissioned_at timestamp with time zone,
    retired_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_code ON hidra_topology_facility (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_facility_type_id ON hidra_topology_facility (facility_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_owner_party_id ON hidra_topology_facility (owner_party_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_status ON hidra_topology_facility (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_created_at ON hidra_topology_facility (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_updated_at ON hidra_topology_facility (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_facility_node_binding (
    id varchar(80) PRIMARY KEY,
    facility_id varchar(80) NOT NULL,
    node_id varchar(80) NOT NULL,
    binding_role_code varchar(120),
    primary_binding boolean NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_node_binding_facility_id ON hidra_topology_facility_node_binding (facility_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_node_binding_node_id ON hidra_topology_facility_node_binding (node_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_node_binding_status ON hidra_topology_facility_node_binding (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_node_binding_created_at ON hidra_topology_facility_node_binding (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_node_binding_updated_at ON hidra_topology_facility_node_binding (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_facility_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255) NOT NULL,
    facility_kind varchar(80) NOT NULL,
    description text,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_code ON hidra_topology_facility_type (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_status ON hidra_topology_facility_type (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_created_at ON hidra_topology_facility_type (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_updated_at ON hidra_topology_facility_type (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_facility_type_version (
    id varchar(80) PRIMARY KEY,
    facility_type_id varchar(80) NOT NULL,
    version_number integer NOT NULL,
    definition_payload jsonb,
    status varchar(40) NOT NULL,
    effective_from timestamp with time zone,
    effective_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_version_facility_type_id ON hidra_topology_facility_type_version (facility_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_version_status ON hidra_topology_facility_type_version (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_version_created_at ON hidra_topology_facility_type_version (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_facility_type_version_updated_at ON hidra_topology_facility_type_version (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_measurement_location (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    measurement_location_type varchar(80) NOT NULL,
    pipeline_id varchar(80),
    pipeline_segment_id varchar(80),
    facility_id varchar(80),
    node_id varchar(80),
    equipment_id varchar(80),
    kilometer_point numeric(12,4),
    description text,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_code ON hidra_topology_measurement_location (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_pipeline_id ON hidra_topology_measurement_location (pipeline_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_pipeline_segment_id ON hidra_topology_measurement_location (pipeline_segment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_facility_id ON hidra_topology_measurement_location (facility_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_node_id ON hidra_topology_measurement_location (node_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_equipment_id ON hidra_topology_measurement_location (equipment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_status ON hidra_topology_measurement_location (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_created_at ON hidra_topology_measurement_location (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_measurement_location_updated_at ON hidra_topology_measurement_location (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_pipeline (
    id varchar(80) PRIMARY KEY,
    pipeline_system_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    pipeline_type varchar(80) NOT NULL,
    nominal_diameter numeric(12,4),
    diameter_unit_code varchar(40),
    design_pressure numeric(12,4),
    pressure_unit_code varchar(40),
    total_length_km numeric(12,4),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_pipeline_system_id ON hidra_topology_pipeline (pipeline_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_code ON hidra_topology_pipeline (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_status ON hidra_topology_pipeline (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_created_at ON hidra_topology_pipeline (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_updated_at ON hidra_topology_pipeline (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_pipeline_segment (
    id varchar(80) PRIMARY KEY,
    pipeline_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    segment_type varchar(80) NOT NULL,
    from_node_id varchar(80) NOT NULL,
    to_node_id varchar(80) NOT NULL,
    start_kilometer_point numeric(12,4),
    end_kilometer_point numeric(12,4),
    length_km numeric(12,4),
    flow_direction varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_segment_pipeline_id ON hidra_topology_pipeline_segment (pipeline_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_segment_code ON hidra_topology_pipeline_segment (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_segment_from_node_id ON hidra_topology_pipeline_segment (from_node_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_segment_to_node_id ON hidra_topology_pipeline_segment (to_node_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_segment_status ON hidra_topology_pipeline_segment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_segment_created_at ON hidra_topology_pipeline_segment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_segment_updated_at ON hidra_topology_pipeline_segment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_pipeline_system_facility (
    id varchar(80) PRIMARY KEY,
    pipeline_system_id varchar(80) NOT NULL,
    facility_id varchar(80) NOT NULL,
    relationship_code varchar(120),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_facility_pipeline_system_i ON hidra_topology_pipeline_system_facility (pipeline_system_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_facility_facility_id ON hidra_topology_pipeline_system_facility (facility_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_facility_status ON hidra_topology_pipeline_system_facility (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_facility_created_at ON hidra_topology_pipeline_system_facility (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_facility_updated_at ON hidra_topology_pipeline_system_facility (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_pipeline_system (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    system_type varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    description text,
    commissioned_at timestamp with time zone,
    retired_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_code ON hidra_topology_pipeline_system (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_status ON hidra_topology_pipeline_system (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_created_at ON hidra_topology_pipeline_system (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_pipeline_system_updated_at ON hidra_topology_pipeline_system (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_connection (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    from_node_id varchar(80) NOT NULL,
    to_node_id varchar(80) NOT NULL,
    connection_type varchar(80) NOT NULL,
    flow_direction varchar(80) NOT NULL,
    pipeline_segment_id varchar(80),
    nominal_capacity numeric(14,4),
    capacity_unit_code varchar(40),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_connection_code ON hidra_topology_connection (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_connection_from_node_id ON hidra_topology_connection (from_node_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_connection_to_node_id ON hidra_topology_connection (to_node_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_connection_pipeline_segment_id ON hidra_topology_connection (pipeline_segment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_connection_status ON hidra_topology_connection (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_connection_created_at ON hidra_topology_connection (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_connection_updated_at ON hidra_topology_connection (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_node (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255),
    node_type varchar(80) NOT NULL,
    facility_id varchar(80),
    latitude numeric(10,7),
    longitude numeric(10,7),
    elevation_meters numeric(12,4),
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_node_code ON hidra_topology_node (code);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_node_facility_id ON hidra_topology_node (facility_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_node_status ON hidra_topology_node (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_node_created_at ON hidra_topology_node (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_node_updated_at ON hidra_topology_node (updated_at);

CREATE TABLE IF NOT EXISTS hidra_topology_snapshot (
    id varchar(80) PRIMARY KEY,
    snapshot_code varchar(120) NOT NULL,
    version_number integer NOT NULL,
    status varchar(40) NOT NULL,
    snapshot_payload jsonb NOT NULL,
    approved_by_workflow_id varchar(120),
    captured_at timestamp with time zone NOT NULL,
    approved_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_snapshot_status ON hidra_topology_snapshot (status);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_snapshot_approved_by_workflow_id ON hidra_topology_snapshot (approved_by_workflow_id);
CREATE INDEX IF NOT EXISTS ix_hidra_topology_snapshot_created_at ON hidra_topology_snapshot (created_at);
