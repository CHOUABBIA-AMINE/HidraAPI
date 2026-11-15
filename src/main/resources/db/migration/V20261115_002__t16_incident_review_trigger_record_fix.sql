-- T16 FAIL repair on exact-SHA CI #1249 (2026-11-15).
-- Source V20261115_001 is immutable. The original deferred constraint function
-- includes NEW.id and NEW.incident_id inside one CASE expression. PostgreSQL
-- resolves both record field expressions against the triggering table's row
-- shape, even though only one CASE arm is selected. Thus a T14 Incident INSERT
-- (NEW.id, no NEW.incident_id) fails at commit before its evidence is accepted.
-- Select the row identifier in a separate PL/pgSQL branch so the unselected
-- expression is never parsed against the other table's row shape.
--
-- Do not drop/disable the constraint triggers or weaken the independent
-- review, resolution timestamp, or terminal-state guards.
CREATE OR REPLACE FUNCTION public.t16_incident_review_consistent()
RETURNS trigger LANGUAGE plpgsql AS $t16$
DECLARE
    target_id varchar(80);
    parent_row public.hidra_incident%ROWTYPE;
    investigation_row public.hidra_incident_investigation%ROWTYPE;
BEGIN
    IF TG_TABLE_SCHEMA <> 'public' THEN
        RAISE EXCEPTION 'T16 investigation trigger bound to unexpected schema';
    END IF;
    IF TG_TABLE_NAME = 'hidra_incident' THEN
        target_id := NEW.id;
    ELSIF TG_TABLE_NAME = 'hidra_incident_investigation' THEN
        target_id := NEW.incident_id;
    ELSE
        RAISE EXCEPTION 'T16 investigation trigger bound to unexpected relation';
    END IF;

    SELECT * INTO parent_row FROM public.hidra_incident
      WHERE id = target_id;
    IF NOT FOUND THEN RETURN NULL; END IF;
    SELECT * INTO investigation_row
      FROM public.hidra_incident_investigation WHERE incident_id = target_id;
    IF NOT FOUND THEN
        -- Pre-T16 Incident has no governed investigation head; existing
        -- incident lifecycle and HMR-090 closure integrity remain operative.
        RETURN NULL;
    END IF;
    IF parent_row.status = 'CLOSED'
       AND (investigation_row.state <> 'CLOSED' OR investigation_row.reviewed_by_id IS NULL) THEN
        RAISE EXCEPTION 'T16 governed Incident requires reviewed closure history';
    END IF;
    IF investigation_row.state IN ('APPROVED', 'CLOSED')
       AND parent_row.resolved_at IS NULL THEN
        RAISE EXCEPTION 'T16 approved Incident must retain canonical resolution timestamp';
    END IF;
    RETURN NULL;
END
$t16$;
