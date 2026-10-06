-- Hidra Workflow semantic remediation; owner-authorized Batch 7, 2026-10-06.
-- Additive guards: invalid legacy rows abort migration; never fabricate or retag evidence.
ALTER TABLE hidra_workflow_instance ADD CONSTRAINT ck_hmr055_instance_evidence CHECK (
 workflow_purpose_id IS NOT NULL AND btrim(workflow_purpose_id)<>'' AND target_module IS NOT NULL
 AND btrim(target_module)<>'' AND definition_version>0 AND btrim(started_by_display_name_snapshot)<>'');
ALTER TABLE hidra_workflow_step ADD CONSTRAINT uq_hmr055_step_definition UNIQUE(id,definition_id);
ALTER TABLE hidra_workflow_definition ADD CONSTRAINT uq_hmr055_definition_version UNIQUE(id,version);
ALTER TABLE hidra_workflow_instance ADD CONSTRAINT fk_hmr055_instance_step_definition
 FOREIGN KEY(current_step_id,definition_id) REFERENCES hidra_workflow_step(id,definition_id);
ALTER TABLE hidra_workflow_instance ADD CONSTRAINT fk_hmr055_instance_definition_version
 FOREIGN KEY(definition_id,definition_version) REFERENCES hidra_workflow_definition(id,version);
CREATE UNIQUE INDEX uq_hmr055_nonterminal_target_purpose ON hidra_workflow_instance
 (target_module,target_type_id,target_id,workflow_purpose_id) WHERE status IN ('DRAFT','STARTED','IN_PROGRESS','WAITING');
CREATE UNIQUE INDEX uq_hmr055_binding ON hidra_workflow_definition_target_binding
 (definition_id,target_module,target_type_id,workflow_purpose_id);
DO $$ BEGIN
 IF EXISTS (SELECT 1 FROM hidra_workflow_instance i LEFT JOIN hidra_workflow_type_catalog c
 ON c.id=i.workflow_purpose_id WHERE c.id IS NULL OR c.catalog_name<>'WORKFLOW_PURPOSE')
 OR EXISTS (SELECT 1 FROM hidra_workflow_definition_target_binding b LEFT JOIN hidra_workflow_type_catalog c
 ON c.id=b.workflow_purpose_id WHERE c.id IS NULL OR c.catalog_name<>'WORKFLOW_PURPOSE') THEN
 RAISE EXCEPTION 'HMR055: owners must reconcile legacy Workflow purpose catalog family before migration'; END IF;
END $$;
CREATE FUNCTION hidra_hmr055_start_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF TG_OP='UPDATE' AND (NEW.definition_id,NEW.definition_version,NEW.workflow_purpose_id,NEW.target_module,NEW.target_type_id,NEW.target_id)
 IS NOT DISTINCT FROM (OLD.definition_id,OLD.definition_version,OLD.workflow_purpose_id,OLD.target_module,OLD.target_type_id,OLD.target_id) THEN RETURN NEW; END IF;
 IF NOT EXISTS(SELECT 1 FROM hidra_workflow_definition WHERE id=NEW.definition_id AND version=NEW.definition_version AND status='ACTIVE')
 OR NOT EXISTS(SELECT 1 FROM hidra_workflow_definition_target_binding WHERE definition_id=NEW.definition_id AND target_module=NEW.target_module
 AND target_type_id=NEW.target_type_id AND workflow_purpose_id=NEW.workflow_purpose_id AND active)
 OR NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.workflow_purpose_id AND catalog_name='WORKFLOW_PURPOSE' AND active)
 OR NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.target_type_id AND catalog_name='WORKFLOW_TARGET_TYPE' AND active)
 THEN RAISE EXCEPTION 'HMR055: ACTIVE definition/version, exact binding and active purpose/target catalogs required'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr055_start BEFORE INSERT OR UPDATE ON hidra_workflow_instance FOR EACH ROW EXECUTE FUNCTION hidra_hmr055_start_guard();
CREATE FUNCTION hidra_hmr055_binding_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.workflow_purpose_id AND catalog_name='WORKFLOW_PURPOSE' AND (NOT NEW.active OR active))
 OR NOT EXISTS(SELECT 1 FROM hidra_workflow_type_catalog WHERE id=NEW.target_type_id AND catalog_name='WORKFLOW_TARGET_TYPE' AND (NOT NEW.active OR active))
 THEN RAISE EXCEPTION 'HMR055: binding requires governed purpose/target families'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr055_binding BEFORE INSERT OR UPDATE ON hidra_workflow_definition_target_binding FOR EACH ROW EXECUTE FUNCTION hidra_hmr055_binding_guard();
