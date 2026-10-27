-- HAPI-596A: definitions only; no grants, roles, Workflow decisions or products.
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES ('custody-engineering-product-publication-read','custody:engineering-product-publication:read','custody','PRODUCT_ENGINEERING_PUBLICATION','read',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES ('custody-engineering-product-publication-prepare','custody:engineering-product-publication:prepare','custody','PRODUCT_ENGINEERING_PUBLICATION','prepare',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES ('custody-engineering-product-publication-publish','custody:engineering-product-publication:publish','custody','PRODUCT_ENGINEERING_PUBLICATION','publish',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES ('custody-engineering-product-publication-withdraw','custody:engineering-product-publication:withdraw','custody','PRODUCT_ENGINEERING_PUBLICATION','withdraw',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES ('custody-engineering-product-publication-reactivate','custody:engineering-product-publication:reactivate','custody','PRODUCT_ENGINEERING_PUBLICATION','reactivate',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
DO $verify$ BEGIN
 IF (SELECT count(*) FROM public.hidra_identity_permission WHERE code IN
 ('custody:engineering-product-publication:read','custody:engineering-product-publication:prepare','custody:engineering-product-publication:publish','custody:engineering-product-publication:withdraw','custody:engineering-product-publication:reactivate')
 AND permission_domain='custody' AND resource_type='PRODUCT_ENGINEERING_PUBLICATION'
 AND code='custody:engineering-product-publication:'||action AND status='ACTIVE' AND NOT sensitive)<>5
 THEN RAISE EXCEPTION 'Product publication permission catalog mismatch'; END IF;
END $verify$;
