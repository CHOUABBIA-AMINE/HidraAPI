-- ORG-030 - Provision Audit taxonomy required by Organization responsibility governance.
-- Module owner: audit
--
-- These rows are semantic catalog definitions consumed by the Audit-owned Organization adapter.

INSERT INTO hidra_audit_catalog_entry (
    id, catalog_name, code, active, sort_order, system_defined, created_at, updated_at
)
SELECT
    'audit-category-business',
    'EVENT_CATEGORY',
    'BUSINESS',
    true,
    100,
    true,
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_audit_catalog_entry
    WHERE catalog_name = 'EVENT_CATEGORY'
      AND code = 'BUSINESS'
);

INSERT INTO hidra_audit_catalog_entry (
    id, catalog_name, code, active, sort_order, system_defined, created_at, updated_at
)
SELECT
    'audit-event-org-responsibility-assigned',
    'EVENT_TYPE',
    'ORGANIZATION_RESPONSIBILITY_ASSIGNED',
    true,
    100,
    true,
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_audit_catalog_entry
    WHERE catalog_name = 'EVENT_TYPE'
      AND code = 'ORGANIZATION_RESPONSIBILITY_ASSIGNED'
);

INSERT INTO hidra_audit_catalog_entry (
    id, catalog_name, code, active, sort_order, system_defined, created_at, updated_at
)
SELECT
    'audit-event-org-responsibility-revoked',
    'EVENT_TYPE',
    'ORGANIZATION_RESPONSIBILITY_REVOKED',
    true,
    110,
    true,
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_audit_catalog_entry
    WHERE catalog_name = 'EVENT_TYPE'
      AND code = 'ORGANIZATION_RESPONSIBILITY_REVOKED'
);

INSERT INTO hidra_audit_catalog_entry (
    id, catalog_name, code, active, sort_order, system_defined, created_at, updated_at
)
SELECT
    'audit-event-org-responsibility-reconciled',
    'EVENT_TYPE',
    'ORGANIZATION_RESPONSIBILITY_RECONCILED',
    true,
    120,
    true,
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_audit_catalog_entry
    WHERE catalog_name = 'EVENT_TYPE'
      AND code = 'ORGANIZATION_RESPONSIBILITY_RECONCILED'
);
