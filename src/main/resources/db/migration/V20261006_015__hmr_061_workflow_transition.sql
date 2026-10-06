-- Hidra Workflow semantic remediation; owner-authorized Batch 7, 2026-10-06.
-- Additive guards: invalid legacy rows abort migration; never fabricate or retag evidence.
ALTER TABLE hidra_workflow_transition ADD CONSTRAINT ck_hmr061_distinct_steps CHECK(from_step_id<>to_step_id);
ALTER TABLE hidra_workflow_transition ADD CONSTRAINT uq_hmr061_source_decision UNIQUE(definition_id,from_step_id,decision);
ALTER TABLE hidra_workflow_transition ADD CONSTRAINT fk_hmr061_from_definition
 FOREIGN KEY(from_step_id,definition_id) REFERENCES hidra_workflow_step(id,definition_id);
ALTER TABLE hidra_workflow_transition ADD CONSTRAINT fk_hmr061_to_definition
 FOREIGN KEY(to_step_id,definition_id) REFERENCES hidra_workflow_step(id,definition_id);
DO $$ BEGIN
 IF EXISTS(SELECT 1 FROM hidra_workflow_transition t JOIN hidra_workflow_definition d ON d.id=t.definition_id
 WHERE d.status='ACTIVE' AND (NULLIF(btrim(t.condition_expression),'') IS NOT NULL
 OR NULLIF(btrim(t.target_module_callback),'') IS NOT NULL OR t.decision='COMMENT')) THEN
 RAISE EXCEPTION 'HMR061: unsupported ACTIVE transition configuration requires owner reconciliation'; END IF;
END $$;
CREATE FUNCTION hidra_hmr061_configuration_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 PERFORM 1 FROM hidra_workflow_definition WHERE id=NEW.definition_id FOR UPDATE;
 IF EXISTS(SELECT 1 FROM hidra_workflow_definition WHERE id=NEW.definition_id AND status='ACTIVE')
 AND (NULLIF(btrim(NEW.condition_expression),'') IS NOT NULL OR NULLIF(btrim(NEW.target_module_callback),'') IS NOT NULL OR NEW.decision='COMMENT')
 THEN RAISE EXCEPTION 'HMR061: unsupported transition cannot become executable ACTIVE configuration'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr061_configuration BEFORE INSERT OR UPDATE ON hidra_workflow_transition FOR EACH ROW EXECUTE FUNCTION hidra_hmr061_configuration_guard();
CREATE FUNCTION hidra_hmr061_activation_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF NEW.status='ACTIVE' AND EXISTS(SELECT 1 FROM hidra_workflow_transition WHERE definition_id=NEW.id
 AND (NULLIF(btrim(condition_expression),'') IS NOT NULL OR NULLIF(btrim(target_module_callback),'') IS NOT NULL OR decision='COMMENT'))
 THEN RAISE EXCEPTION 'HMR061: definition activation requires supported transition configuration'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr061_activation BEFORE INSERT OR UPDATE ON hidra_workflow_definition FOR EACH ROW EXECUTE FUNCTION hidra_hmr061_activation_guard();
