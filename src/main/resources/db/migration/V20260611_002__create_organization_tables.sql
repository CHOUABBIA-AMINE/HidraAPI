-- HIDRA organization module database schema
-- Generated from JPA entity metadata in src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity
-- Module: organization

CREATE TABLE IF NOT EXISTS hidra_org_administrative_district (
    id varchar(80) PRIMARY KEY,
    state_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_district_state_id ON hidra_org_administrative_district (state_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_district_code ON hidra_org_administrative_district (code);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_district_active ON hidra_org_administrative_district (active);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_district_created_at ON hidra_org_administrative_district (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_district_updated_at ON hidra_org_administrative_district (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_administrative_locality (
    id varchar(80) PRIMARY KEY,
    district_id varchar(80) NOT NULL,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    postal_code varchar(40),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_locality_district_id ON hidra_org_administrative_locality (district_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_locality_code ON hidra_org_administrative_locality (code);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_locality_active ON hidra_org_administrative_locality (active);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_locality_created_at ON hidra_org_administrative_locality (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_locality_updated_at ON hidra_org_administrative_locality (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_administrative_state (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_state_code ON hidra_org_administrative_state (code);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_state_active ON hidra_org_administrative_state (active);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_state_created_at ON hidra_org_administrative_state (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_administrative_state_updated_at ON hidra_org_administrative_state (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_employee_address (
    id varchar(80) PRIMARY KEY,
    employee_id varchar(80) NOT NULL,
    address_type varchar(80) NOT NULL,
    locality_id varchar(80) NOT NULL,
    street_line1 varchar(255),
    street_line2 varchar(255),
    postal_code_snapshot varchar(40),
    primary_address boolean NOT NULL,
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_address_employee_id ON hidra_org_employee_address (employee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_address_locality_id ON hidra_org_employee_address (locality_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_address_created_at ON hidra_org_employee_address (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_address_updated_at ON hidra_org_employee_address (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_employee_assignment (
    id varchar(80) PRIMARY KEY,
    employee_id varchar(80) NOT NULL,
    organization_unit_id varchar(80) NOT NULL,
    position_id varchar(80) NOT NULL,
    assignment_type varchar(80) NOT NULL,
    operational_scope_type varchar(80),
    operational_scope_id varchar(120),
    operational_scope_code varchar(120),
    operational_scope_name varchar(255),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_assignment_employee_id ON hidra_org_employee_assignment (employee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_assignment_organization_unit_id ON hidra_org_employee_assignment (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_assignment_position_id ON hidra_org_employee_assignment (position_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_assignment_operational_scope_id ON hidra_org_employee_assignment (operational_scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_assignment_status ON hidra_org_employee_assignment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_assignment_created_at ON hidra_org_employee_assignment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_assignment_updated_at ON hidra_org_employee_assignment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_employee (
    id varchar(80) PRIMARY KEY,
    employee_number varchar(120) NOT NULL,
    first_name_ar varchar(255),
    last_name_ar varchar(255),
    first_name_lt varchar(255),
    last_name_lt varchar(255),
    display_name_ar varchar(255),
    display_name_lt varchar(255),
    email_address varchar(254),
    mobile_number varchar(80),
    employee_type varchar(80) NOT NULL,
    status varchar(40) NOT NULL,
    identity_user_reference varchar(120),
    hired_at timestamp with time zone,
    terminated_at timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_status ON hidra_org_employee (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_created_at ON hidra_org_employee (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_employee_updated_at ON hidra_org_employee (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_contact_point (
    id varchar(80) PRIMARY KEY,
    contact_point_type varchar(80) NOT NULL,
    target_type varchar(80) NOT NULL,
    target_id varchar(80) NOT NULL,
    label varchar(255),
    value varchar(255) NOT NULL,
    primary_contact boolean NOT NULL,
    emergency_contact boolean NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_contact_point_target_id ON hidra_org_contact_point (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_contact_point_active ON hidra_org_contact_point (active);
CREATE INDEX IF NOT EXISTS ix_hidra_org_contact_point_created_at ON hidra_org_contact_point (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_contact_point_updated_at ON hidra_org_contact_point (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_delegation (
    id varchar(80) PRIMARY KEY,
    delegator_employee_id varchar(80) NOT NULL,
    delegate_employee_id varchar(80) NOT NULL,
    responsibility_assignment_id varchar(80),
    reason text,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone NOT NULL,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    revoked_at timestamp with time zone
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_delegation_delegator_employee_id ON hidra_org_delegation (delegator_employee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_delegation_delegate_employee_id ON hidra_org_delegation (delegate_employee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_delegation_responsibility_assignment_id ON hidra_org_delegation (responsibility_assignment_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_delegation_status ON hidra_org_delegation (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_delegation_created_at ON hidra_org_delegation (created_at);

CREATE TABLE IF NOT EXISTS hidra_org_hierarchy_snapshot (
    id varchar(80) PRIMARY KEY,
    snapshot_code varchar(120) NOT NULL,
    captured_at timestamp with time zone NOT NULL,
    captured_by_employee_id varchar(80),
    status varchar(40) NOT NULL,
    snapshot_payload jsonb NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_hierarchy_snapshot_captured_by_employee_id ON hidra_org_hierarchy_snapshot (captured_by_employee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_hierarchy_snapshot_status ON hidra_org_hierarchy_snapshot (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_hierarchy_snapshot_created_at ON hidra_org_hierarchy_snapshot (created_at);

CREATE TABLE IF NOT EXISTS hidra_org_unit (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    unit_type_id varchar(80) NOT NULL,
    parent_unit_id varchar(80),
    status varchar(40) NOT NULL,
    operational_scope_type varchar(80),
    operational_scope_id varchar(120),
    operational_scope_code varchar(120),
    operational_scope_name varchar(255),
    valid_from timestamp with time zone,
    valid_to timestamp with time zone,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_code ON hidra_org_unit (code);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_unit_type_id ON hidra_org_unit (unit_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_parent_unit_id ON hidra_org_unit (parent_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_status ON hidra_org_unit (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_operational_scope_id ON hidra_org_unit (operational_scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_created_at ON hidra_org_unit (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_updated_at ON hidra_org_unit (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_unit_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    kind varchar(80) NOT NULL,
    description text,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_type_code ON hidra_org_unit_type (code);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_type_active ON hidra_org_unit_type (active);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_type_created_at ON hidra_org_unit_type (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_type_updated_at ON hidra_org_unit_type (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_unit_type_translation (
    id varchar(80) PRIMARY KEY,
    unit_type_id varchar(80) NOT NULL,
    language_code varchar(10) NOT NULL,
    label varchar(255) NOT NULL,
    description text,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_type_translation_unit_type_id ON hidra_org_unit_type_translation (unit_type_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_type_translation_created_at ON hidra_org_unit_type_translation (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_unit_type_translation_updated_at ON hidra_org_unit_type_translation (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_position (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    title_ar varchar(255),
    title_fr varchar(255),
    title_en varchar(255),
    level varchar(80),
    description text,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_position_code ON hidra_org_position (code);
CREATE INDEX IF NOT EXISTS ix_hidra_org_position_status ON hidra_org_position (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_position_created_at ON hidra_org_position (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_position_updated_at ON hidra_org_position (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_reporting_line (
    id varchar(80) PRIMARY KEY,
    reporting_line_type varchar(80) NOT NULL,
    source_type varchar(80) NOT NULL,
    source_id varchar(80) NOT NULL,
    target_type varchar(80) NOT NULL,
    target_id varchar(80) NOT NULL,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_reporting_line_source_id ON hidra_org_reporting_line (source_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_reporting_line_target_id ON hidra_org_reporting_line (target_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_reporting_line_active ON hidra_org_reporting_line (active);
CREATE INDEX IF NOT EXISTS ix_hidra_org_reporting_line_created_at ON hidra_org_reporting_line (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_reporting_line_updated_at ON hidra_org_reporting_line (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_responsibility_assignment (
    id varchar(80) PRIMARY KEY,
    responsibility_type varchar(80) NOT NULL,
    assignee_type varchar(80) NOT NULL,
    assignee_id varchar(80) NOT NULL,
    operational_scope_type varchar(80) NOT NULL,
    operational_scope_id varchar(120) NOT NULL,
    operational_scope_code varchar(120),
    operational_scope_name varchar(255),
    description text,
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_responsibility_assignment_assignee_id ON hidra_org_responsibility_assignment (assignee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_responsibility_assignment_operational_scope_id ON hidra_org_responsibility_assignment (operational_scope_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_responsibility_assignment_status ON hidra_org_responsibility_assignment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_responsibility_assignment_created_at ON hidra_org_responsibility_assignment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_responsibility_assignment_updated_at ON hidra_org_responsibility_assignment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_shift_assignment (
    id varchar(80) PRIMARY KEY,
    employee_id varchar(80) NOT NULL,
    shift_id varchar(80) NOT NULL,
    organization_unit_id varchar(80),
    valid_from timestamp with time zone NOT NULL,
    valid_to timestamp with time zone,
    status varchar(40) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_assignment_employee_id ON hidra_org_shift_assignment (employee_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_assignment_shift_id ON hidra_org_shift_assignment (shift_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_assignment_organization_unit_id ON hidra_org_shift_assignment (organization_unit_id);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_assignment_status ON hidra_org_shift_assignment (status);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_assignment_created_at ON hidra_org_shift_assignment (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_assignment_updated_at ON hidra_org_shift_assignment (updated_at);

CREATE TABLE IF NOT EXISTS hidra_org_shift (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name varchar(255) NOT NULL,
    shift_type varchar(80) NOT NULL,
    start_time varchar(20) NOT NULL,
    end_time varchar(20) NOT NULL,
    timezone varchar(80) NOT NULL,
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_code ON hidra_org_shift (code);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_active ON hidra_org_shift (active);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_created_at ON hidra_org_shift (created_at);
CREATE INDEX IF NOT EXISTS ix_hidra_org_shift_updated_at ON hidra_org_shift (updated_at);
