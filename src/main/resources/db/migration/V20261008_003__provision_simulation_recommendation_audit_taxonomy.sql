-- Audit-owned taxonomy needed for atomic Simulation recommendation publication.
-- Never silently reactivate, reclassify or invent historical publication evidence.
LOCK TABLE hidra_audit_catalog_entry IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF (SELECT count(*) FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_TYPE'
        AND code='SIMULATION_RECOMMENDATION_PUBLISHED') > 1
        OR (SELECT count(*) FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_CATEGORY' AND code='BUSINESS') > 1
        OR EXISTS (SELECT 1 FROM hidra_audit_catalog_entry WHERE NOT active AND (
            (catalog_name='EVENT_TYPE' AND code='SIMULATION_RECOMMENDATION_PUBLISHED')
            OR (catalog_name='EVENT_CATEGORY' AND code='BUSINESS')))
    THEN RAISE EXCEPTION 'Simulation publication Audit taxonomy is conflicting/inactive; owner reconciliation required'; END IF;
END $$;
INSERT INTO hidra_audit_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
SELECT 'audit-event-simulation-recommendation-published','EVENT_TYPE','SIMULATION_RECOMMENDATION_PUBLISHED',true,230,true,now(),now()
WHERE NOT EXISTS (SELECT 1 FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_TYPE' AND code='SIMULATION_RECOMMENDATION_PUBLISHED');
INSERT INTO hidra_audit_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
SELECT 'audit-category-business','EVENT_CATEGORY','BUSINESS',true,100,true,now(),now()
WHERE NOT EXISTS (SELECT 1 FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_CATEGORY' AND code='BUSINESS');
