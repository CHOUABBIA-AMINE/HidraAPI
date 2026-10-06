-- Hidra Workflow semantic remediation; owner-authorized Batch 7, 2026-10-06.
-- Additive guards: invalid legacy rows abort migration; never fabricate or retag evidence.
ALTER TABLE hidra_workflow_task ADD CONSTRAINT ck_hmr066_assignment CHECK(
 status NOT IN ('OPEN','CLAIMED','IN_REVIEW') OR NULLIF(btrim(assigned_actor_id),'') IS NOT NULL OR NULLIF(btrim(assigned_organization_unit_id),'') IS NOT NULL);
ALTER TABLE hidra_workflow_task ADD CONSTRAINT ck_hmr066_pairs_time CHECK(
 (claimed_by_actor_id IS NULL)=(claimed_at IS NULL) AND (completed_by_actor_id IS NULL)=(completed_at IS NULL)
 AND (claimed_by_actor_id IS NULL OR btrim(claimed_by_actor_id)<>'') AND (completed_by_actor_id IS NULL OR btrim(completed_by_actor_id)<>'')
 AND (claimed_at IS NULL OR claimed_at>=created_at) AND (completed_at IS NULL OR completed_at>=created_at)
 AND updated_at>=created_at AND (claimed_by_actor_id IS NULL OR assigned_actor_id IS NULL OR claimed_by_actor_id=assigned_actor_id)
 AND (status NOT IN ('OPEN','CLAIMED','IN_REVIEW') OR completed_at IS NULL));
DO $$ BEGIN
 IF EXISTS(SELECT 1 FROM hidra_workflow_task t LEFT JOIN hidra_workflow_type_catalog c ON c.id=t.priority_id
 WHERE t.priority_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'WORKFLOW_PRIORITY'))
 OR EXISTS(SELECT 1 FROM hidra_workflow_task t LEFT JOIN hidra_workflow_type_catalog c ON c.id=t.assignment_mode_id
 WHERE t.assignment_mode_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'WORKFLOW_ASSIGNMENT_MODE')) THEN
 RAISE EXCEPTION 'HMR066: task catalogs require owner reconciliation'; END IF;
END $$;
CREATE FUNCTION hidra_hmr066_task_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE instance_definition varchar(80);
BEGIN
 IF TG_OP='UPDATE' THEN
  IF OLD.status NOT IN ('OPEN','CLAIMED','IN_REVIEW') THEN RAISE EXCEPTION 'HMR066: terminal task evidence is immutable'; END IF;
  IF (OLD.instance_id,OLD.step_id,OLD.created_at) IS DISTINCT FROM (NEW.instance_id,NEW.step_id,NEW.created_at)
  THEN RAISE EXCEPTION 'HMR066: task instance/step/creation cannot be reassigned'; END IF;
 END IF;
 SELECT definition_id INTO instance_definition FROM hidra_workflow_instance WHERE id=NEW.instance_id;
 IF instance_definition IS NULL OR NOT EXISTS(SELECT 1 FROM hidra_workflow_step WHERE id=NEW.step_id AND definition_id=instance_definition)
 THEN RAISE EXCEPTION 'HMR066: task step must belong to instance definition'; END IF;
 IF NEW.priority_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.priority_id AND catalog_name='WORKFLOW_PRIORITY' AND active)
 OR NEW.assignment_mode_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.assignment_mode_id AND catalog_name='WORKFLOW_ASSIGNMENT_MODE' AND active)
 THEN RAISE EXCEPTION 'HMR066: active task priority/assignment catalogs required'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr066_task BEFORE INSERT OR UPDATE ON hidra_workflow_task FOR EACH ROW EXECUTE FUNCTION hidra_hmr066_task_guard();
CREATE FUNCTION hidra_hmr066_task_delete_guard() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN
 IF OLD.status NOT IN ('OPEN','CLAIMED','IN_REVIEW') THEN RAISE EXCEPTION 'HMR066: terminal task evidence cannot be deleted'; END IF;
 RETURN OLD; END $$;
CREATE TRIGGER tr_hmr066_task_delete BEFORE DELETE ON hidra_workflow_task FOR EACH ROW EXECUTE FUNCTION hidra_hmr066_task_delete_guard();

CREATE FUNCTION hidra_hmr066_task_truncate_guard() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN
 IF EXISTS(SELECT 1 FROM hidra_workflow_task WHERE status NOT IN ('OPEN','CLAIMED','IN_REVIEW'))
 THEN RAISE EXCEPTION 'HMR066: terminal task evidence cannot be truncated'; END IF;
 RETURN NULL; END $$;
CREATE TRIGGER tr_hmr066_task_truncate BEFORE TRUNCATE ON hidra_workflow_task FOR EACH STATEMENT EXECUTE FUNCTION hidra_hmr066_task_truncate_guard();
DO $$ BEGIN
 IF EXISTS(SELECT 1 FROM hidra_workflow_task t LEFT JOIN hidra_workflow_instance i ON i.id=t.instance_id
 LEFT JOIN hidra_workflow_step s ON s.id=t.step_id AND s.definition_id=i.definition_id WHERE i.id IS NULL OR s.id IS NULL)
 THEN RAISE EXCEPTION 'HMR066: existing task step/definition ownership needs reconciliation'; END IF;
END $$;
