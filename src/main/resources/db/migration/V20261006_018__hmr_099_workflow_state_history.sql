-- Hidra Workflow semantic remediation; owner-authorized Batch 7, 2026-10-06.
-- Additive guards: invalid legacy rows abort migration; never fabricate or retag evidence.
ALTER TABLE hidra_workflow_state_history ADD CONSTRAINT ck_hmr099_history_evidence CHECK(btrim(to_status)<>'' AND btrim(actor_display_name_snapshot)<>'');
ALTER TABLE hidra_workflow_state_history ADD CONSTRAINT fk_hmr099_history_instance FOREIGN KEY(instance_id) REFERENCES hidra_workflow_instance(id);
ALTER TABLE hidra_workflow_state_history ADD CONSTRAINT fk_hmr099_history_task_instance FOREIGN KEY(task_id,instance_id) REFERENCES hidra_workflow_task(id,instance_id);
ALTER TABLE hidra_workflow_state_history ADD CONSTRAINT fk_hmr099_history_action_instance FOREIGN KEY(action_id,instance_id) REFERENCES hidra_workflow_action(id,instance_id);
ALTER TABLE hidra_workflow_state_history ADD CONSTRAINT fk_hmr099_history_from_step FOREIGN KEY(from_step_id) REFERENCES hidra_workflow_step(id);
ALTER TABLE hidra_workflow_state_history ADD CONSTRAINT fk_hmr099_history_to_step FOREIGN KEY(to_step_id) REFERENCES hidra_workflow_step(id);
ALTER TABLE hidra_workflow_state_history ADD CONSTRAINT fk_hmr099_history_reason FOREIGN KEY(reason_id) REFERENCES hidra_workflow_type_catalog(id);
DO $$ BEGIN
 IF EXISTS(SELECT 1 FROM hidra_workflow_state_history h JOIN hidra_workflow_instance i ON i.id=h.instance_id
 LEFT JOIN hidra_workflow_step f ON f.id=h.from_step_id LEFT JOIN hidra_workflow_step t ON t.id=h.to_step_id
 LEFT JOIN hidra_workflow_task k ON k.id=h.task_id LEFT JOIN hidra_workflow_action a ON a.id=h.action_id
 LEFT JOIN hidra_workflow_type_catalog r ON r.id=h.reason_id
 WHERE (h.from_step_id IS NOT NULL AND f.definition_id<>i.definition_id)
 OR (h.to_step_id IS NOT NULL AND t.definition_id<>i.definition_id)
 OR (h.from_step_id IS NOT NULL AND h.task_id IS NOT NULL AND f.id<>k.step_id)
 OR (h.action_id IS NOT NULL AND (h.actor_id<>a.actor_id OR (h.task_id IS NOT NULL AND h.task_id IS DISTINCT FROM a.task_id)
 OR (h.reason_id IS NOT NULL AND h.reason_id IS DISTINCT FROM a.reason_id)))
 OR (h.reason_id IS NOT NULL AND r.catalog_name<>'WORKFLOW_REASON'))
 THEN RAISE EXCEPTION 'HMR099: legacy history references/evidence need owner reconciliation'; END IF;
END $$;
CREATE FUNCTION hidra_hmr099_history_insert_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE definition varchar(80); task_step varchar(80); action_row hidra_workflow_action%ROWTYPE;
BEGIN
 SELECT definition_id INTO definition FROM hidra_workflow_instance WHERE id=NEW.instance_id;
 IF definition IS NULL THEN RAISE EXCEPTION 'HMR099: unknown history instance'; END IF;
 IF NEW.from_step_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM hidra_workflow_step WHERE id=NEW.from_step_id AND definition_id=definition)
 OR NEW.to_step_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM hidra_workflow_step WHERE id=NEW.to_step_id AND definition_id=definition)
 THEN RAISE EXCEPTION 'HMR099: history steps must belong to instance definition'; END IF;
 IF NEW.task_id IS NOT NULL THEN
  SELECT step_id INTO task_step FROM hidra_workflow_task WHERE id=NEW.task_id AND instance_id=NEW.instance_id;
  IF task_step IS NULL OR (NEW.from_step_id IS NOT NULL AND NEW.from_step_id<>task_step) THEN RAISE EXCEPTION 'HMR099: history task/source incoherent'; END IF;
 END IF;
 IF NEW.action_id IS NOT NULL THEN
  SELECT * INTO action_row FROM hidra_workflow_action WHERE id=NEW.action_id AND instance_id=NEW.instance_id;
  IF NOT FOUND OR action_row.actor_id<>NEW.actor_id OR (NEW.task_id IS NOT NULL AND NEW.task_id IS DISTINCT FROM action_row.task_id)
  OR (NEW.reason_id IS NOT NULL AND NEW.reason_id IS DISTINCT FROM action_row.reason_id)
  THEN RAISE EXCEPTION 'HMR099: history action evidence incoherent'; END IF;
  IF action_row.task_id IS NOT NULL AND NEW.from_step_id IS NOT NULL THEN
   SELECT step_id INTO task_step FROM hidra_workflow_task WHERE id=action_row.task_id AND instance_id=NEW.instance_id;
   IF task_step IS NULL OR task_step<>NEW.from_step_id THEN RAISE EXCEPTION 'HMR099: history action source incoherent'; END IF;
  END IF;
 END IF;
 IF NEW.reason_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.reason_id AND catalog_name='WORKFLOW_REASON' AND active)
 THEN RAISE EXCEPTION 'HMR099: active history reason catalog required'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr099_history_insert BEFORE INSERT ON hidra_workflow_state_history FOR EACH ROW EXECUTE FUNCTION hidra_hmr099_history_insert_guard();
CREATE FUNCTION hidra_hmr099_history_immutable() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN
 RAISE EXCEPTION 'HMR099: Workflow state history is append-only'; END $$;
CREATE TRIGGER tr_hmr099_history_immutable BEFORE UPDATE OR DELETE ON hidra_workflow_state_history FOR EACH ROW EXECUTE FUNCTION hidra_hmr099_history_immutable();
CREATE TRIGGER tr_hmr099_history_truncate BEFORE TRUNCATE ON hidra_workflow_state_history FOR EACH STATEMENT EXECUTE FUNCTION hidra_hmr099_history_immutable();
