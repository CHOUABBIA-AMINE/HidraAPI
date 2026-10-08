-- Audit-owned taxonomy needed for atomic Risk assessment approval.
-- Never silently reactivate, reclassify or invent historical publication evidence.
LOCK TABLE hidra_audit_catalog_entry IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF (SELECT count(*) FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_TYPE'
        AND code='RISK_ASSESSMENT_APPROVED') > 1
        OR (SELECT count(*) FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_CATEGORY' AND code='BUSINESS') > 1
        OR EXISTS (SELECT 1 FROM hidra_audit_catalog_entry WHERE NOT active AND (
            (catalog_name='EVENT_TYPE' AND code='RISK_ASSESSMENT_APPROVED')
            OR (catalog_name='EVENT_CATEGORY' AND code='BUSINESS')))
    THEN RAISE EXCEPTION 'Risk approval Audit taxonomy is conflicting/inactive; owner reconciliation required'; END IF;
END $$;
INSERT INTO hidra_audit_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
SELECT 'audit-event-risk-assessment-approved','EVENT_TYPE','RISK_ASSESSMENT_APPROVED',true,240,true,now(),now()
WHERE NOT EXISTS (SELECT 1 FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_TYPE' AND code='RISK_ASSESSMENT_APPROVED');
INSERT INTO hidra_audit_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
SELECT 'audit-category-business','EVENT_CATEGORY','BUSINESS',true,100,true,now(),now()
WHERE NOT EXISTS (SELECT 1 FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_CATEGORY' AND code='BUSINESS');
