-- HAPI-595A: permission definitions only; no roles, grants, operational products or catalog policy seeds.
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('custody-engineering-product-properties-read','custody:engineering-product-properties:read','custody','PRODUCT_ENGINEERING_PROPERTIES','read',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('custody-engineering-product-properties-write','custody:engineering-product-properties:write','custody','PRODUCT_ENGINEERING_PROPERTIES','write',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
DO $verify$
BEGIN
 IF (SELECT count(*) FROM public.hidra_identity_permission
     WHERE code IN ('custody:engineering-product-properties:read','custody:engineering-product-properties:write')
       AND permission_domain='custody' AND resource_type='PRODUCT_ENGINEERING_PROPERTIES'
       AND code='custody:engineering-product-properties:'||action AND status='ACTIVE' AND NOT sensitive)<>2 THEN
  RAISE EXCEPTION 'Fluid property permission catalog mismatch';
 END IF;
END;
$verify$;
