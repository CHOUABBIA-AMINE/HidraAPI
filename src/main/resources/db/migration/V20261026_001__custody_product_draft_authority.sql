-- HAPI-594A: permission definitions only; no roles, grants, operational products or catalog policy seeds.
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('custody-engineering-product-create','custody:engineering-product:create','custody','PRODUCT_ENGINEERING_DRAFT','create',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('custody-engineering-product-read','custody:engineering-product:read','custody','PRODUCT_ENGINEERING_DRAFT','read',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('custody-engineering-product-update','custody:engineering-product:update','custody','PRODUCT_ENGINEERING_DRAFT','update',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
DO $verify$
BEGIN
 IF (SELECT count(*) FROM public.hidra_identity_permission
     WHERE code IN ('custody:engineering-product:create','custody:engineering-product:read','custody:engineering-product:update')
       AND permission_domain='custody' AND resource_type='PRODUCT_ENGINEERING_DRAFT'
       AND code='custody:engineering-product:'||action AND status='ACTIVE' AND NOT sensitive)<>3 THEN
  RAISE EXCEPTION 'Product draft permission catalog mismatch';
 END IF;
END;
$verify$;
