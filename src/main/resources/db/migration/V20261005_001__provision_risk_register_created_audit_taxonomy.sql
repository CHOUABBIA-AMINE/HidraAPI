-- HMR-049B - Provision Audit taxonomy required by RiskRegister creation auditing.
-- Module owner: audit

INSERT INTO hidra_audit_catalog_entry (
    id, catalog_name, code, active, sort_order, system_defined, created_at, updated_at
)
SELECT
    'audit-event-risk-register-created',
    'EVENT_TYPE',
    'RISK_REGISTER_CREATED',
    true,
    220,
    true,
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_audit_catalog_entry
    WHERE catalog_name = 'EVENT_TYPE'
      AND code = 'RISK_REGISTER_CREATED'
);
