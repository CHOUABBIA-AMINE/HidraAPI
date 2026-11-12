-- T08: register commissioning privilege. Business grants are provisioned separately by Identity.
-- This does not commission an OT source or change any existing telemetry records.
INSERT INTO public.hidra_identity_permission
    (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
    ('t08-commission','telemetry:commission:manage','telemetry','TELEMETRY_POINT','manage',
     true,'ACTIVE',now(),now());
