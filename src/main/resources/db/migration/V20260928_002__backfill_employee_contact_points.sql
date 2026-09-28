-- HIDRA Employee legacy contact backfill
-- Module: organization
-- Roadmap: HRA-021
--
-- Evidence gate:
-- * Repository inspection found legacy employee email_address/mobile_number columns.
-- * No production/seed Employee contact dataset exists in the repository.
-- * Therefore no precedence rule is invented when canonical contact points already exist.
--
-- Deterministic policy:
-- * trim and ignore null/blank legacy values;
-- * backfill only when the employee has no existing contact point of the same type;
-- * never update/delete an existing canonical contact point;
-- * leave conflicting legacy values in place as the quarantine surface for later review;
-- * use deterministic migration IDs and ON CONFLICT so re-execution cannot duplicate rows.

INSERT INTO hidra_org_contact_point (
    id,
    contact_point_type,
    target_type,
    target_id,
    label,
    value,
    primary_contact,
    emergency_contact,
    active,
    created_at,
    updated_at
)
SELECT
    'hra021-email-' || md5(employee.id || '|EMAIL|' || btrim(employee.email_address)),
    'EMAIL',
    'EMPLOYEE',
    employee.id,
    'Legacy employee email',
    btrim(employee.email_address),
    false,
    false,
    true,
    employee.created_at,
    employee.updated_at
FROM hidra_org_employee employee
WHERE employee.email_address IS NOT NULL
  AND btrim(employee.email_address) <> ''
  AND NOT EXISTS (
      SELECT 1
      FROM hidra_org_contact_point contact
      WHERE contact.target_type = 'EMPLOYEE'
        AND contact.target_id = employee.id
        AND contact.contact_point_type = 'EMAIL'
  )
ON CONFLICT (id) DO NOTHING;

INSERT INTO hidra_org_contact_point (
    id,
    contact_point_type,
    target_type,
    target_id,
    label,
    value,
    primary_contact,
    emergency_contact,
    active,
    created_at,
    updated_at
)
SELECT
    'hra021-mobile-' || md5(employee.id || '|MOBILE|' || btrim(employee.mobile_number)),
    'MOBILE',
    'EMPLOYEE',
    employee.id,
    'Legacy employee mobile',
    btrim(employee.mobile_number),
    false,
    false,
    true,
    employee.created_at,
    employee.updated_at
FROM hidra_org_employee employee
WHERE employee.mobile_number IS NOT NULL
  AND btrim(employee.mobile_number) <> ''
  AND NOT EXISTS (
      SELECT 1
      FROM hidra_org_contact_point contact
      WHERE contact.target_type = 'EMPLOYEE'
        AND contact.target_id = employee.id
        AND contact.contact_point_type = 'MOBILE'
  )
ON CONFLICT (id) DO NOTHING;
