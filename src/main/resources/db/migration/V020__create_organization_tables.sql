-- HidraAPI Organization module persistence schema.
-- STB-006 — db(organization): confirm organization flyway migration.
-- Creates only organization-owned tables required by organization JPA entities.
-- Does not create identity, platform, topology, physical station asset, or future business-module tables.

CREATE TABLE IF NOT EXISTS hidra_org_employee (
    id VARCHAR(80) PRIMARY KEY,
    employee_number VARCHAR(40) NOT NULL,
    full_name VARCHAR(160) NOT NULL,
    email VARCHAR(120),
    status VARCHAR(40) NOT NULL,
    identity_user_reference VARCHAR(120),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    activated_at TIMESTAMP WITH TIME ZONE,
    suspended_at TIMESTAMP WITH TIME ZONE,
    disabled_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_hidra_org_employee_employee_number UNIQUE (employee_number)
);

CREATE TABLE IF NOT EXISTS hidra_org_position (
    id VARCHAR(80) PRIMARY KEY,
    code VARCHAR(80) NOT NULL,
    title VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_hidra_org_position_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS hidra_org_unit (
    id VARCHAR(80) PRIMARY KEY,
    code VARCHAR(80) NOT NULL,
    name VARCHAR(160) NOT NULL,
    status VARCHAR(40) NOT NULL,
    unit_type VARCHAR(60) NOT NULL,
    parent_id VARCHAR(80),
    operational_scope_type VARCHAR(80),
    operational_scope_id VARCHAR(120),
    operational_scope_code VARCHAR(120),
    operational_scope_name VARCHAR(160),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_hidra_org_unit_code UNIQUE (code),
    CONSTRAINT fk_hidra_org_unit_parent
        FOREIGN KEY (parent_id)
        REFERENCES hidra_org_unit (id)
);

CREATE TABLE IF NOT EXISTS hidra_org_employee_assignment (
    id VARCHAR(80) PRIMARY KEY,
    employee_id VARCHAR(80) NOT NULL,
    organization_unit_id VARCHAR(80) NOT NULL,
    position_id VARCHAR(80) NOT NULL,
    operational_scope_type VARCHAR(80),
    operational_scope_id VARCHAR(120),
    operational_scope_code VARCHAR(120),
    operational_scope_name VARCHAR(160),
    effective_from DATE NOT NULL,
    effective_to DATE,
    CONSTRAINT fk_hidra_org_employee_assignment_employee
        FOREIGN KEY (employee_id)
        REFERENCES hidra_org_employee (id),
    CONSTRAINT fk_hidra_org_employee_assignment_unit
        FOREIGN KEY (organization_unit_id)
        REFERENCES hidra_org_unit (id),
    CONSTRAINT fk_hidra_org_employee_assignment_position
        FOREIGN KEY (position_id)
        REFERENCES hidra_org_position (id)
);

CREATE TABLE IF NOT EXISTS hidra_org_reporting_line (
    id VARCHAR(80) PRIMARY KEY,
    employee_id VARCHAR(80) NOT NULL,
    manager_employee_id VARCHAR(80) NOT NULL,
    reporting_line_type VARCHAR(60) NOT NULL,
    primary_line BOOLEAN NOT NULL,
    effective_from DATE NOT NULL,
    effective_to DATE,
    description VARCHAR(500),
    CONSTRAINT fk_hidra_org_reporting_line_employee
        FOREIGN KEY (employee_id)
        REFERENCES hidra_org_employee (id),
    CONSTRAINT fk_hidra_org_reporting_line_manager
        FOREIGN KEY (manager_employee_id)
        REFERENCES hidra_org_employee (id),
    CONSTRAINT ck_hidra_org_reporting_line_not_self
        CHECK (employee_id <> manager_employee_id)
);

CREATE INDEX IF NOT EXISTS idx_hidra_org_employee_status
    ON hidra_org_employee (status);

CREATE INDEX IF NOT EXISTS idx_hidra_org_employee_identity_user_reference
    ON hidra_org_employee (identity_user_reference);

CREATE INDEX IF NOT EXISTS idx_hidra_org_position_active
    ON hidra_org_position (active);

CREATE INDEX IF NOT EXISTS idx_hidra_org_unit_status
    ON hidra_org_unit (status);

CREATE INDEX IF NOT EXISTS idx_hidra_org_unit_type
    ON hidra_org_unit (unit_type);

CREATE INDEX IF NOT EXISTS idx_hidra_org_unit_parent
    ON hidra_org_unit (parent_id);

CREATE INDEX IF NOT EXISTS idx_hidra_org_unit_operational_scope
    ON hidra_org_unit (operational_scope_type, operational_scope_code);

CREATE INDEX IF NOT EXISTS idx_hidra_org_employee_assignment_employee
    ON hidra_org_employee_assignment (employee_id);

CREATE INDEX IF NOT EXISTS idx_hidra_org_employee_assignment_unit
    ON hidra_org_employee_assignment (organization_unit_id);

CREATE INDEX IF NOT EXISTS idx_hidra_org_employee_assignment_position
    ON hidra_org_employee_assignment (position_id);

CREATE INDEX IF NOT EXISTS idx_hidra_org_employee_assignment_scope
    ON hidra_org_employee_assignment (operational_scope_type, operational_scope_code);

CREATE INDEX IF NOT EXISTS idx_hidra_org_employee_assignment_effective_dates
    ON hidra_org_employee_assignment (effective_from, effective_to);

CREATE INDEX IF NOT EXISTS idx_hidra_org_reporting_line_employee
    ON hidra_org_reporting_line (employee_id);

CREATE INDEX IF NOT EXISTS idx_hidra_org_reporting_line_manager
    ON hidra_org_reporting_line (manager_employee_id);

CREATE INDEX IF NOT EXISTS idx_hidra_org_reporting_line_type
    ON hidra_org_reporting_line (reporting_line_type);

CREATE INDEX IF NOT EXISTS idx_hidra_org_reporting_line_primary
    ON hidra_org_reporting_line (primary_line);

CREATE INDEX IF NOT EXISTS idx_hidra_org_reporting_line_effective_dates
    ON hidra_org_reporting_line (effective_from, effective_to);
