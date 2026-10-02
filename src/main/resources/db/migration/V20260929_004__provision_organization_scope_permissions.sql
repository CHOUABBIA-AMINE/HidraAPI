-- ORG-030 - Provision Organization operational-scope/responsibility permission definitions.
-- Module owner: identity
--
-- This migration defines permission catalog rows only. It does not grant permissions to any role
-- or user; effective authorization remains owned by Identity grants.

INSERT INTO hidra_identity_permission (
    id, code, name_ar, name_fr, name_en, description,
    permission_domain, resource_type, action, sensitive, status, created_at, updated_at
)
SELECT
    'perm-org-operational-scope-register',
    'organization:operational-scope:register',
    'تسجيل نطاق تشغيلي',
    'Enregistrer un périmètre opérationnel',
    'Register operational scope',
    'Register an owner-validated operational scope in the Organization registry.',
    'organization',
    'operational_scope',
    'register',
    true,
    'ACTIVE',
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_identity_permission
    WHERE code = 'organization:operational-scope:register'
);

INSERT INTO hidra_identity_permission (
    id, code, name_ar, name_fr, name_en, description,
    permission_domain, resource_type, action, sensitive, status, created_at, updated_at
)
SELECT
    'perm-org-responsibility-assign',
    'organization:responsibility:assign',
    'إسناد مسؤولية تشغيلية',
    'Attribuer une responsabilité opérationnelle',
    'Assign operational responsibility',
    'Assign an effective-dated Organization responsibility to a registered scope.',
    'organization',
    'responsibility',
    'assign',
    true,
    'ACTIVE',
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_identity_permission
    WHERE code = 'organization:responsibility:assign'
);

INSERT INTO hidra_identity_permission (
    id, code, name_ar, name_fr, name_en, description,
    permission_domain, resource_type, action, sensitive, status, created_at, updated_at
)
SELECT
    'perm-org-responsibility-revoke',
    'organization:responsibility:revoke',
    'إنهاء مسؤولية تشغيلية',
    'Révoquer une responsabilité opérationnelle',
    'Revoke operational responsibility',
    'End an Organization responsibility while preserving its historical record.',
    'organization',
    'responsibility',
    'revoke',
    true,
    'ACTIVE',
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_identity_permission
    WHERE code = 'organization:responsibility:revoke'
);

INSERT INTO hidra_identity_permission (
    id, code, name_ar, name_fr, name_en, description,
    permission_domain, resource_type, action, sensitive, status, created_at, updated_at
)
SELECT
    'perm-org-responsibility-reconcile',
    'organization:responsibility:reconcile',
    'مراجعة تكامل المسؤوليات',
    'Réconcilier l’intégrité des responsabilités',
    'Reconcile responsibility integrity',
    'Run read-only Organization responsibility integrity reconciliation.',
    'organization',
    'responsibility',
    'reconcile',
    true,
    'ACTIVE',
    now(),
    now()
WHERE NOT EXISTS (
    SELECT 1 FROM hidra_identity_permission
    WHERE code = 'organization:responsibility:reconcile'
);
