DO $$ BEGIN
 IF EXISTS (SELECT 1 FROM hidra_incident_response_action a LEFT JOIN hidra_incident i ON i.id=a.incident_id
    LEFT JOIN hidra_incident_catalog_entry c ON c.id=a.action_type_id WHERE i.id IS NULL OR c.catalog_name IS DISTINCT FROM 'RESPONSE_ACTION_TYPE' OR NULLIF(btrim(a.description),'') IS NULL)
 THEN RAISE EXCEPTION 'HMR-092: reconcile legacy response description, Incident or catalog family'; END IF;
END $$;
ALTER TABLE hidra_incident_response_action ADD CONSTRAINT ck_hmr092_description CHECK(NULLIF(btrim(description),'') IS NOT NULL);
CREATE TRIGGER tr_hmr092_action_catalog BEFORE INSERT OR UPDATE ON hidra_incident_response_action FOR EACH ROW
 EXECUTE FUNCTION hidra_incident_catalog_guard('action_type_id','RESPONSE_ACTION_TYPE');
CREATE FUNCTION hidra_incident_response_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE parent_status text;
BEGIN
 SELECT status INTO parent_status FROM hidra_incident WHERE id=NEW.incident_id FOR UPDATE;
 IF NOT FOUND OR parent_status IN ('DRAFT','CLOSED','CANCELLED','MERGED') THEN RAISE EXCEPTION 'Incident cannot receive response actions in this state'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr092_action_parent BEFORE INSERT OR UPDATE ON hidra_incident_response_action FOR EACH ROW EXECUTE FUNCTION hidra_incident_response_guard();
