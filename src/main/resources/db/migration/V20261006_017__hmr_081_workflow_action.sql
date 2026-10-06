-- Hidra Workflow semantic remediation; owner-authorized Batch 7, 2026-10-06.
-- Additive guards: invalid legacy rows abort migration; never fabricate or retag evidence.
ALTER TABLE hidra_workflow_task ADD CONSTRAINT uq_hmr081_task_instance UNIQUE(id,instance_id);
ALTER TABLE hidra_workflow_action ADD CONSTRAINT fk_hmr081_action_task_instance
 FOREIGN KEY(task_id,instance_id) REFERENCES hidra_workflow_task(id,instance_id);
ALTER TABLE hidra_workflow_action ADD CONSTRAINT fk_hmr081_action_instance FOREIGN KEY(instance_id) REFERENCES hidra_workflow_instance(id);
ALTER TABLE hidra_workflow_action ADD CONSTRAINT uq_hmr081_instance_sequence UNIQUE(instance_id,action_sequence);
ALTER TABLE hidra_workflow_action ADD CONSTRAINT uq_hmr081_action_instance UNIQUE(id,instance_id);
ALTER TABLE hidra_workflow_action ADD CONSTRAINT ck_hmr081_action_evidence CHECK(
 btrim(actor_display_name_snapshot)<>'' AND action_sequence>0
 AND (action_type NOT IN ('APPROVE','REJECT','REQUEST_CORRECTION','CORRECT','RETURN','DELEGATE','ESCALATE','CANCEL') OR (decision IS NOT NULL AND action_type=decision))
 AND (decision IS NULL OR action_type=decision)
 AND (action_type NOT IN ('REJECT','REQUEST_CORRECTION','RETURN','DELEGATE','ESCALATE','CANCEL') OR NULLIF(btrim(reason_id),'') IS NOT NULL)
 AND (action_type<>'REQUEST_CORRECTION' OR NULLIF(btrim(comment_text),'') IS NOT NULL));
DO $$ BEGIN
 IF EXISTS(SELECT 1 FROM hidra_workflow_action a LEFT JOIN hidra_workflow_type_catalog c ON c.id=a.reason_id
 WHERE a.reason_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'WORKFLOW_REASON'))
 THEN RAISE EXCEPTION 'HMR081: action reason catalogs need owner reconciliation'; END IF;
END $$;
CREATE FUNCTION hidra_hmr081_action_insert_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE expected bigint;
BEGIN
 PERFORM 1 FROM hidra_workflow_instance WHERE id=NEW.instance_id FOR UPDATE;
 SELECT COALESCE(MAX(action_sequence),0)+1 INTO expected FROM hidra_workflow_action WHERE instance_id=NEW.instance_id;
 IF NEW.action_sequence<>expected THEN RAISE EXCEPTION 'HMR081: action sequence must be next instance sequence'; END IF;
 IF NEW.reason_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.reason_id AND catalog_name='WORKFLOW_REASON' AND active)
 THEN RAISE EXCEPTION 'HMR081: active WORKFLOW_REASON required'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr081_action_insert BEFORE INSERT ON hidra_workflow_action FOR EACH ROW EXECUTE FUNCTION hidra_hmr081_action_insert_guard();
CREATE FUNCTION hidra_hmr081_action_immutable() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN
 RAISE EXCEPTION 'HMR081: Workflow action evidence is append-only'; END $$;
CREATE TRIGGER tr_hmr081_action_immutable BEFORE UPDATE OR DELETE ON hidra_workflow_action FOR EACH ROW EXECUTE FUNCTION hidra_hmr081_action_immutable();
CREATE TRIGGER tr_hmr081_action_truncate BEFORE TRUNCATE ON hidra_workflow_action FOR EACH STATEMENT EXECUTE FUNCTION hidra_hmr081_action_immutable();
