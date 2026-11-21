-- Preserve the installed trigger bindings and dispatch identifier access by row type.
CREATE OR REPLACE FUNCTION public.hidra_topology_t36_candidate_publication_guard()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path=pg_catalog,pg_temp
AS $$
DECLARE approved_id uuid; proposed record;
BEGIN
 -- Each trigger row has only the identifier column of its own table.
 IF TG_TABLE_NAME = 'hidra_topology_t36_pressure_revision' THEN
   approved_id := NEW.revision_id;
 ELSIF TG_TABLE_NAME = 'hidra_topology_t36_pressure_termination' THEN
   approved_id := NEW.event_id;
 ELSE
   RAISE EXCEPTION 'Unexpected T36 publication trigger table' USING ERRCODE='23514';
 END IF;
 SELECT c.* INTO proposed
 FROM public.hidra_topology_t36_pressure_candidate c
 WHERE c.candidate_id=approved_id;
 IF NOT FOUND THEN
   RAISE EXCEPTION 'T36 immutable review target missing' USING ERRCODE='23514';
 END IF;
 IF (proposed.company_party_id,proposed.pipeline_id,proposed.section_id,
       proposed.pipeline_system_id,proposed.condition_code,
       proposed.baseline_receipt_id,proposed.baseline_manifest_digest,
       proposed.engineering_source_revision,proposed.submitted_by)
    IS DISTINCT FROM
       (NEW.company_party_id,NEW.pipeline_id,NEW.section_id,
        NEW.pipeline_system_id,NEW.condition_code,
        NEW.baseline_receipt_id,NEW.baseline_manifest_digest,
        NEW.engineering_source_revision,NEW.submitted_by)
    OR proposed.created_at>NEW.approved_at
    OR NOT EXISTS (
        SELECT 1 FROM public.hidra_workflow_action a
        JOIN public.hidra_workflow_instance i ON i.id=a.instance_id
        WHERE a.id=NEW.decision_action_id
          AND i.target_id=approved_id::text
          AND i.target_code_snapshot='t36-v1:'||approved_id::text)
 THEN
   RAISE EXCEPTION 'T36 approved action does not bind exact candidate'
       USING ERRCODE='23514';
 END IF;
 IF TG_TABLE_NAME='hidra_topology_t36_pressure_revision' THEN
   IF proposed.candidate_kind<>'REVISION'
       OR (proposed.upper_pressure,proposed.pressure_unit,proposed.pressure_basis,
           proposed.effective_from,proposed.effective_until,
           proposed.retired_revision_id)
       IS DISTINCT FROM
          (NEW.upper_pressure,NEW.pressure_unit,NEW.pressure_basis,
           NEW.effective_from,NEW.effective_until,NEW.supersedes_revision_id)
   THEN RAISE EXCEPTION 'T36 reviewed pressure proposal differs from publication'
       USING ERRCODE='23514'; END IF;
 ELSE
   IF proposed.candidate_kind<>NEW.event_kind
       OR (proposed.effective_from,proposed.retired_revision_id,
           proposed.successor_revision_id)
       IS DISTINCT FROM
          (NEW.effective_at,NEW.retired_revision_id,NEW.successor_revision_id)
   THEN RAISE EXCEPTION 'T36 reviewed termination differs from publication'
       USING ERRCODE='23514'; END IF;
 END IF;
 RETURN NEW;
END;
$$;
