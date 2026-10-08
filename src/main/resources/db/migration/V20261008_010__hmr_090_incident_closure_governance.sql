-- No inferred minor threshold, approval or policy defaults. Operators approve exact family pairs.
CREATE TABLE IF NOT EXISTS hidra_incident_closure_policy (
 classification_id varchar(80) NOT NULL REFERENCES hidra_incident_catalog_entry(id),
 severity_id varchar(80) NOT NULL REFERENCES hidra_incident_catalog_entry(id),
 active boolean NOT NULL,
 evidence_required boolean NOT NULL,
 workflow_required boolean NOT NULL,
 root_cause_required boolean NOT NULL,
 corrective_action_type_id varchar(80) REFERENCES hidra_incident_catalog_entry(id),
 preventive_action_type_id varchar(80) REFERENCES hidra_incident_catalog_entry(id),
 PRIMARY KEY(classification_id,severity_id)
);
CREATE TRIGGER tr_hmr090_policy_catalog BEFORE INSERT OR UPDATE ON hidra_incident_closure_policy FOR EACH ROW
 EXECUTE FUNCTION hidra_incident_catalog_guard('classification_id','INCIDENT_CLASSIFICATION','severity_id','INCIDENT_SEVERITY','corrective_action_type_id','RESPONSE_ACTION_TYPE','preventive_action_type_id','RESPONSE_ACTION_TYPE');
ALTER TABLE hidra_incident_closure ADD CONSTRAINT uq_hmr090_one_closure UNIQUE(incident_id);
ALTER TABLE hidra_incident_closure ADD CONSTRAINT ck_hmr090_confirmations CHECK(NULLIF(btrim(closure_summary),'') IS NOT NULL AND resolution_verified AND evidence_reviewed);
CREATE FUNCTION hidra_incident_closure_consistent(target text) RETURNS void LANGUAGE plpgsql AS $$
DECLARE i hidra_incident%ROWTYPE; c hidra_incident_closure%ROWTYPE; p hidra_incident_closure_policy%ROWTYPE; r hidra_incident_resolution%ROWTYPE;
BEGIN
 SELECT * INTO i FROM hidra_incident WHERE id=target;
 IF NOT FOUND THEN RETURN; END IF;
 SELECT * INTO c FROM hidra_incident_closure WHERE incident_id=target;
 IF NOT FOUND THEN
    IF i.status='CLOSED' THEN RAISE EXCEPTION 'CLOSED Incident requires atomic closure evidence'; END IF;
    RETURN;
 END IF;
 IF i.status<>'CLOSED' OR i.closed_at IS DISTINCT FROM c.closed_at OR i.resolved_at IS NULL THEN RAISE EXCEPTION 'Incident closure and parent state/time must commit together'; END IF;
 SELECT * INTO p FROM hidra_incident_closure_policy WHERE classification_id=i.classification_id AND severity_id=i.severity_id AND active;
 IF NOT FOUND THEN RAISE EXCEPTION 'Explicit active Incident closure policy required'; END IF;
 IF (SELECT count(*) FROM hidra_incident_resolution WHERE incident_id=target)<>1 THEN RAISE EXCEPTION 'Incident closure requires exactly one resolution'; END IF;
 SELECT * INTO r FROM hidra_incident_resolution WHERE incident_id=target;
 IF NULLIF(btrim(r.resolution_summary),'') IS NULL OR NULLIF(btrim(r.resolved_by_actor_id),'') IS NULL OR r.resolved_at IS DISTINCT FROM i.resolved_at OR NOT EXISTS(SELECT 1 FROM hidra_incident_catalog_entry WHERE id=r.resolution_type_id AND catalog_name='RESOLUTION_TYPE') THEN RAISE EXCEPTION 'Resolution summary and parent resolution time are required'; END IF;
 IF p.evidence_required AND NOT EXISTS (SELECT 1 FROM hidra_incident_evidence_link WHERE incident_id=target AND NULLIF(btrim(evidence_reference_id),'') IS NOT NULL) THEN RAISE EXCEPTION 'Incident closure requires persisted evidence'; END IF;
 IF p.workflow_required AND NULLIF(btrim(c.workflow_instance_id),'') IS NULL THEN RAISE EXCEPTION 'Incident policy requires Workflow reference; owner adapter attests actual approval'; END IF;
 IF p.root_cause_required AND (NOT c.root_cause_reviewed OR NOT EXISTS(SELECT 1 FROM hidra_incident_root_cause_analysis WHERE incident_id=target AND NULLIF(btrim(summary),'') IS NOT NULL)) THEN RAISE EXCEPTION 'Incident closure requires reviewed root-cause evidence'; END IF;
 IF r.corrective_action_required AND (NOT c.follow_up_actions_created OR p.corrective_action_type_id IS NULL OR NOT EXISTS(SELECT 1 FROM hidra_incident_response_action WHERE incident_id=target AND action_type_id=p.corrective_action_type_id AND action_status IN ('PLANNED','IN_PROGRESS','COMPLETED'))) THEN RAISE EXCEPTION 'Incident closure requires configured corrective-action evidence'; END IF;
 IF r.preventive_action_required AND (NOT c.follow_up_actions_created OR p.preventive_action_type_id IS NULL OR NOT EXISTS(SELECT 1 FROM hidra_incident_response_action WHERE incident_id=target AND action_type_id=p.preventive_action_type_id AND action_status IN ('PLANNED','IN_PROGRESS','COMPLETED'))) THEN RAISE EXCEPTION 'Incident closure requires configured preventive-action evidence'; END IF;
END $$;
-- Fail legacy inconsistencies, never fabricate resolution/policy/approval or rewrite old records.
DO $$ DECLARE target text; BEGIN
 FOR target IN SELECT id FROM hidra_incident WHERE status='CLOSED' UNION SELECT incident_id FROM hidra_incident_closure LOOP
    PERFORM hidra_incident_closure_consistent(target);
 END LOOP;
END $$;
CREATE FUNCTION hidra_incident_closure_deferred() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF TG_TABLE_NAME='hidra_incident' THEN PERFORM hidra_incident_closure_consistent(NEW.id);
 ELSE PERFORM hidra_incident_closure_consistent(NEW.incident_id); END IF;
 RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER tr_hmr090_parent_consistency AFTER INSERT OR UPDATE ON hidra_incident DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hidra_incident_closure_deferred();
CREATE CONSTRAINT TRIGGER tr_hmr090_closure_consistency AFTER INSERT ON hidra_incident_closure DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hidra_incident_closure_deferred();
CREATE FUNCTION hidra_incident_closure_insert() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE parent_status text;
BEGIN
 IF TG_OP<>'INSERT' THEN RAISE EXCEPTION 'Incident closure evidence is append-only'; END IF;
 SELECT status INTO parent_status FROM hidra_incident WHERE id=NEW.incident_id FOR UPDATE;
 IF NOT FOUND OR parent_status<>'RESOLVED' THEN RAISE EXCEPTION 'Only RESOLVED Incident may close'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr090_closure_insert BEFORE INSERT OR UPDATE OR DELETE ON hidra_incident_closure FOR EACH ROW EXECUTE FUNCTION hidra_incident_closure_insert();
CREATE FUNCTION hidra_incident_closure_policy_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF EXISTS(SELECT 1 FROM hidra_incident_closure c JOIN hidra_incident i ON i.id=c.incident_id WHERE i.classification_id=OLD.classification_id AND i.severity_id=OLD.severity_id)
 THEN RAISE EXCEPTION 'Closure policy bound to historical evidence is immutable'; END IF;
 IF TG_OP='DELETE' THEN RETURN OLD; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr090_policy_immutable BEFORE UPDATE OR DELETE ON hidra_incident_closure_policy FOR EACH ROW EXECUTE FUNCTION hidra_incident_closure_policy_immutable();
CREATE FUNCTION hidra_incident_closed_child_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE target text; parent_status text;
BEGIN
 target:=CASE WHEN TG_OP='DELETE' THEN OLD.incident_id ELSE NEW.incident_id END;
 SELECT status INTO parent_status FROM hidra_incident WHERE id=target FOR UPDATE;
 IF parent_status='CLOSED' THEN RAISE EXCEPTION 'Closed Incident evidence is immutable'; END IF;
 IF TG_OP='UPDATE' AND OLD.incident_id IS DISTINCT FROM NEW.incident_id THEN
    SELECT status INTO parent_status FROM hidra_incident WHERE id=OLD.incident_id FOR UPDATE;
    IF parent_status='CLOSED' THEN RAISE EXCEPTION 'Closed Incident evidence cannot be reparented'; END IF;
 END IF;
 IF TG_OP='DELETE' THEN RETURN OLD; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr090_resolution_frozen BEFORE INSERT OR UPDATE OR DELETE ON hidra_incident_resolution FOR EACH ROW EXECUTE FUNCTION hidra_incident_closed_child_guard();
CREATE TRIGGER tr_hmr090_evidence_frozen BEFORE INSERT OR UPDATE OR DELETE ON hidra_incident_evidence_link FOR EACH ROW EXECUTE FUNCTION hidra_incident_closed_child_guard();
CREATE TRIGGER tr_hmr090_root_cause_frozen BEFORE INSERT OR UPDATE OR DELETE ON hidra_incident_root_cause_analysis FOR EACH ROW EXECUTE FUNCTION hidra_incident_closed_child_guard();
CREATE TRIGGER tr_hmr090_response_frozen BEFORE INSERT OR UPDATE OR DELETE ON hidra_incident_response_action FOR EACH ROW EXECUTE FUNCTION hidra_incident_closed_child_guard();
CREATE FUNCTION hidra_incident_evidence_no_truncate() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN RAISE EXCEPTION 'Incident governed evidence cannot be truncated'; END $$;
DO $$ DECLARE t text; BEGIN
 FOREACH t IN ARRAY ARRAY['hidra_incident_closure','hidra_incident','hidra_incident_resolution','hidra_incident_evidence_link','hidra_incident_root_cause_analysis','hidra_incident_response_action','hidra_incident_closure_policy','hidra_incident_related_incident','hidra_incident_relationship_policy'] LOOP
 EXECUTE format('CREATE TRIGGER tr_hmr090_no_truncate BEFORE TRUNCATE ON %I FOR EACH STATEMENT EXECUTE FUNCTION hidra_incident_evidence_no_truncate()',t);
 END LOOP;
END $$;
