-- HIDRA organization internal reference integrity
-- Module: organization
-- Roadmap: ORG-046
--
-- Fail closed before adding constraints. Only Organization-owned references receive
-- database referential integrity here. Topology/identity/external ownership remains
-- resolver-backed and intentionally has no cross-module foreign key.

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_administrative_district child
        LEFT JOIN hidra_org_administrative_state parent ON parent.id = child.state_id
        WHERE parent.id IS NULL
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan administrative district state_id';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_administrative_locality child
        LEFT JOIN hidra_org_administrative_district parent ON parent.id = child.district_id
        WHERE parent.id IS NULL
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan administrative locality district_id';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_employee_address child
        LEFT JOIN hidra_org_employee employee ON employee.id = child.employee_id
        LEFT JOIN hidra_org_administrative_locality locality ON locality.id = child.locality_id
        WHERE employee.id IS NULL OR locality.id IS NULL
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan employee address reference';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit child
        LEFT JOIN hidra_org_unit_type unit_type ON unit_type.id = child.unit_type_id
        LEFT JOIN hidra_org_unit parent_unit ON parent_unit.id = child.parent_unit_id
        WHERE unit_type.id IS NULL
           OR (child.parent_unit_id IS NOT NULL AND parent_unit.id IS NULL)
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan organization unit reference';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_employee_assignment child
        LEFT JOIN hidra_org_employee employee ON employee.id = child.employee_id
        LEFT JOIN hidra_org_unit unit_ref ON unit_ref.id = child.organization_unit_id
        LEFT JOIN hidra_org_position position_ref ON position_ref.id = child.position_id
        WHERE employee.id IS NULL OR unit_ref.id IS NULL OR position_ref.id IS NULL
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan employee assignment reference';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_shift_assignment child
        LEFT JOIN hidra_org_employee employee ON employee.id = child.employee_id
        LEFT JOIN hidra_org_shift shift_ref ON shift_ref.id = child.shift_id
        LEFT JOIN hidra_org_unit unit_ref ON unit_ref.id = child.organization_unit_id
        WHERE employee.id IS NULL
           OR shift_ref.id IS NULL
           OR (child.organization_unit_id IS NOT NULL AND unit_ref.id IS NULL)
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan shift assignment reference';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_delegation child
        LEFT JOIN hidra_org_employee delegator ON delegator.id = child.delegator_employee_id
        LEFT JOIN hidra_org_employee delegatee ON delegatee.id = child.delegate_employee_id
        LEFT JOIN hidra_org_responsibility_assignment responsibility
            ON responsibility.id = child.responsibility_assignment_id
        WHERE delegator.id IS NULL
           OR delegatee.id IS NULL
           OR (child.responsibility_assignment_id IS NOT NULL AND responsibility.id IS NULL)
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan delegation reference';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_hierarchy_snapshot child
        LEFT JOIN hidra_org_employee employee ON employee.id = child.captured_by_employee_id
        WHERE child.captured_by_employee_id IS NOT NULL AND employee.id IS NULL
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan hierarchy snapshot employee reference';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_contact_point
        WHERE target_type NOT IN ('EMPLOYEE', 'ORGANIZATION_UNIT')
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: unsupported contact-point target_type';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_contact_point cp
        WHERE (cp.target_type = 'EMPLOYEE'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_employee e WHERE e.id = cp.target_id))
           OR (cp.target_type = 'ORGANIZATION_UNIT'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_unit u WHERE u.id = cp.target_id))
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan contact-point target';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_reporting_line
        WHERE source_type NOT IN ('EMPLOYEE', 'POSITION', 'ORGANIZATION_UNIT')
           OR target_type NOT IN ('EMPLOYEE', 'POSITION', 'ORGANIZATION_UNIT')
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: unsupported reporting-line subject type';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_reporting_line line
        WHERE (line.source_type = 'EMPLOYEE'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_employee e WHERE e.id = line.source_id))
           OR (line.source_type = 'POSITION'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_position p WHERE p.id = line.source_id))
           OR (line.source_type = 'ORGANIZATION_UNIT'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_unit u WHERE u.id = line.source_id))
           OR (line.target_type = 'EMPLOYEE'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_employee e WHERE e.id = line.target_id))
           OR (line.target_type = 'POSITION'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_position p WHERE p.id = line.target_id))
           OR (line.target_type = 'ORGANIZATION_UNIT'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_unit u WHERE u.id = line.target_id))
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan reporting-line subject';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_responsibility_assignment
        WHERE assignee_type NOT IN ('EMPLOYEE', 'ORGANIZATION_UNIT')
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: unsupported responsibility assignee_type';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_responsibility_assignment assignment
        WHERE (assignment.assignee_type = 'EMPLOYEE'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_employee e WHERE e.id = assignment.assignee_id))
           OR (assignment.assignee_type = 'ORGANIZATION_UNIT'
               AND NOT EXISTS (SELECT 1 FROM hidra_org_unit u WHERE u.id = assignment.assignee_id))
    ) THEN
        RAISE EXCEPTION 'ORG-046 preflight failed: orphan responsibility assignee';
    END IF;
END
$$;

ALTER TABLE hidra_org_administrative_district
    ADD CONSTRAINT fk_org_district_state
    FOREIGN KEY (state_id) REFERENCES hidra_org_administrative_state (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_administrative_locality
    ADD CONSTRAINT fk_org_locality_district
    FOREIGN KEY (district_id) REFERENCES hidra_org_administrative_district (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_employee_address
    ADD CONSTRAINT fk_org_employee_address_employee
    FOREIGN KEY (employee_id) REFERENCES hidra_org_employee (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_employee_address_locality
    FOREIGN KEY (locality_id) REFERENCES hidra_org_administrative_locality (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_unit
    ADD CONSTRAINT fk_org_unit_type
    FOREIGN KEY (unit_type_id) REFERENCES hidra_org_unit_type (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_unit_parent
    FOREIGN KEY (parent_unit_id) REFERENCES hidra_org_unit (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_employee_assignment
    ADD CONSTRAINT fk_org_employee_assignment_employee
    FOREIGN KEY (employee_id) REFERENCES hidra_org_employee (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_employee_assignment_unit
    FOREIGN KEY (organization_unit_id) REFERENCES hidra_org_unit (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_employee_assignment_position
    FOREIGN KEY (position_id) REFERENCES hidra_org_position (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_shift_assignment
    ADD CONSTRAINT fk_org_shift_assignment_employee
    FOREIGN KEY (employee_id) REFERENCES hidra_org_employee (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_shift_assignment_shift
    FOREIGN KEY (shift_id) REFERENCES hidra_org_shift (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_shift_assignment_unit
    FOREIGN KEY (organization_unit_id) REFERENCES hidra_org_unit (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_delegation
    ADD CONSTRAINT fk_org_delegation_delegator
    FOREIGN KEY (delegator_employee_id) REFERENCES hidra_org_employee (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_delegation_delegate
    FOREIGN KEY (delegate_employee_id) REFERENCES hidra_org_employee (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_org_delegation_responsibility
    FOREIGN KEY (responsibility_assignment_id)
    REFERENCES hidra_org_responsibility_assignment (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_hierarchy_snapshot
    ADD CONSTRAINT fk_org_hierarchy_snapshot_employee
    FOREIGN KEY (captured_by_employee_id) REFERENCES hidra_org_employee (id) ON DELETE RESTRICT;

ALTER TABLE hidra_org_contact_point
    ADD CONSTRAINT ck_org_contact_target_type
    CHECK (target_type IN ('EMPLOYEE', 'ORGANIZATION_UNIT'));

ALTER TABLE hidra_org_reporting_line
    ADD CONSTRAINT ck_org_reporting_source_type
    CHECK (source_type IN ('EMPLOYEE', 'POSITION', 'ORGANIZATION_UNIT')),
    ADD CONSTRAINT ck_org_reporting_target_type
    CHECK (target_type IN ('EMPLOYEE', 'POSITION', 'ORGANIZATION_UNIT'));

ALTER TABLE hidra_org_responsibility_assignment
    ADD CONSTRAINT ck_org_responsibility_assignee_type
    CHECK (assignee_type IN ('EMPLOYEE', 'ORGANIZATION_UNIT'));
