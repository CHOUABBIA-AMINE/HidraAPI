-- ALM-SUP-006A - Provision Audit taxonomy required by Alarm suppression expiry orchestration.
-- Module owner: audit

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
    'audit-event-alarm-suppression-expired',
    'EVENT_TYPE',
    'ALARM_SUPPRESSION_EXPIRED',
    true,
    210,
    true,
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_audit_catalog_entry
    WHERE catalog_name = 'EVENT_TYPE'
      AND code = 'ALARM_SUPPRESSION_EXPIRED'
);
