-- T03 registers review authority without granting it to any business principal.
INSERT INTO public.hidra_identity_permission
    (id, code, permission_domain, resource_type, action, sensitive, status, created_at, updated_at)
VALUES
    ('t03-plan-review', 'planning:scoped-plan:review', 'planning', 'OPERATIONAL_PLAN', 'review',
        true, 'ACTIVE', now(), now());
