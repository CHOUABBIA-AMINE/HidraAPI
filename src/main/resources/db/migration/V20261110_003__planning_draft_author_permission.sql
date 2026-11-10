-- T02 adds an explicit draft-author capability. No business grants or industrial values are seeded.
INSERT INTO public.hidra_identity_permission
    (id, code, permission_domain, resource_type, action, sensitive, status, created_at, updated_at)
VALUES
    ('t02-plan-author', 'planning:scoped-plan:author', 'planning', 'OPERATIONAL_PLAN', 'author',
        true, 'ACTIVE', now(), now());
