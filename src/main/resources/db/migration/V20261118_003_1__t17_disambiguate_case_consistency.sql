-- Keep the deferred consistency checks using a variable distinct from hidra_hse_case.target_id.
CREATE OR REPLACE FUNCTION public.t17_check_case_consistency() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE scoped_case_id varchar(80); parent record; cap record; head record;
BEGIN
 IF TG_TABLE_NAME='hidra_hse_case' THEN scoped_case_id:=NEW.id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_case_scope' THEN scoped_case_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_capa_event' THEN scoped_case_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_case_close_receipt' THEN scoped_case_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_capa_state' THEN scoped_case_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_capa' THEN scoped_case_id:=NEW.hse_case_id;
 ELSE RETURN NULL;
 END IF;
 IF scoped_case_id IS NULL OR NOT EXISTS(SELECT 1 FROM public.hidra_hse_t17_case_scope WHERE case_id=scoped_case_id)
 THEN RETURN NULL; END IF;
 SELECT * INTO parent FROM public.hidra_hse_case WHERE id=scoped_case_id;
 IF NOT FOUND THEN RAISE EXCEPTION 'T17 case parent missing'; END IF;
 IF parent.target_module<>'topology' OR parent.target_type_code<>'FACILITY'
    OR parent.target_id<>(SELECT facility_id FROM public.hidra_hse_t17_case_scope WHERE case_id=scoped_case_id)
    OR parent.source_type<>'INCIDENT_REFERENCE' OR parent.incident_reference_id<>
        (SELECT incident_id FROM public.hidra_hse_t17_case_scope WHERE case_id=scoped_case_id)
 THEN RAISE EXCEPTION 'T17 enrolled case lost exact owner identity'; END IF;
 IF EXISTS (SELECT 1 FROM public.hidra_hse_capa c WHERE c.hse_case_id=scoped_case_id
        AND NOT EXISTS(SELECT 1 FROM public.hidra_hse_t17_capa_state x
            WHERE x.capa_id=c.id AND x.case_id=scoped_case_id))
 THEN RAISE EXCEPTION 'T17 enrolled case cannot contain a legacy context-free CAPA'; END IF;
 FOR cap IN SELECT s.*,c.hse_case_id,c.status AS physical_status,c.owner_actor_id
    FROM public.hidra_hse_t17_capa_state s JOIN public.hidra_hse_capa c ON c.id=s.capa_id
    WHERE s.case_id=scoped_case_id LOOP
   IF cap.hse_case_id<>scoped_case_id OR cap.physical_status<>cap.status
      OR cap.owner_actor_id IS DISTINCT FROM cap.owner_id
      OR (cap.revision<>(SELECT count(*)-1 FROM public.hidra_hse_t17_capa_event e
           WHERE e.capa_id=cap.capa_id))
   THEN RAISE EXCEPTION 'T17 CAPA parent, head and event revision mismatch'; END IF;
   IF cap.status IN ('COMPLETED','VERIFIED','CLOSED') AND NOT EXISTS(
      SELECT 1 FROM public.hidra_hse_t17_capa_evidence e WHERE e.capa_id=cap.capa_id)
   THEN RAISE EXCEPTION 'T17 completion requires immutable accepted evidence'; END IF;
   IF cap.status IN ('VERIFIED','CLOSED') AND NOT EXISTS(
      SELECT 1 FROM public.hidra_hse_t17_capa_verification v
       WHERE v.capa_id=cap.capa_id AND v.outcome='EFFECTIVE'
         AND v.reviewer_id=cap.verifier_id AND v.evidence_id=cap.evidence_id
         AND v.reviewer_id<>cap.proposer_id AND v.reviewer_id<>cap.owner_id)
   THEN RAISE EXCEPTION 'T17 verified CAPA requires independent effective finding'; END IF;
 END LOOP;
 IF parent.status='CLOSED' THEN
   SELECT * INTO head FROM public.hidra_hse_t17_case_close_receipt r WHERE r.case_id=scoped_case_id;
   IF NOT FOUND OR NOT EXISTS(SELECT 1 FROM public.hidra_hse_closure c WHERE c.id=head.closure_id
        AND c.hse_case_id=scoped_case_id AND c.closed_by_actor_id=head.actor_id AND c.closed_at=head.closed_at)
      OR NOT EXISTS(SELECT 1 FROM public.hidra_hse_t17_capa_state WHERE case_id=scoped_case_id)
      OR EXISTS(SELECT 1 FROM public.hidra_hse_t17_capa_state WHERE case_id=scoped_case_id AND status<>'CLOSED')
   THEN RAISE EXCEPTION 'T17 case close requires closure receipt and terminal enrolled CAPAs'; END IF;
 ELSIF EXISTS(SELECT 1 FROM public.hidra_hse_t17_case_close_receipt WHERE case_id=scoped_case_id) THEN
   RAISE EXCEPTION 'T17 closed receipt attached to open case';
 END IF;
 RETURN NULL;
END $$;
