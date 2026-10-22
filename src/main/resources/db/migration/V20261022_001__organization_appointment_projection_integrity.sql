-- HAPI-778: physical projection/version integrity, before publisher activation.
-- No grants, adoption, policy defaults or native authorization are introduced.
-- Source/event/receipt structural equality is necessary, not native authority:
-- the issuer-backed adapter/final guards and legacy writer cutover remain pending.
ALTER TABLE public.hidra_org_employee_assignment
 ADD COLUMN appointment_version bigint,
 ADD COLUMN binding_id varchar(80),
 ADD COLUMN binding_revision bigint,
 ADD COLUMN policy_id varchar(80),
 ADD COLUMN policy_revision bigint,
 ADD COLUMN last_event_id uuid,
 ADD CONSTRAINT appointment_metadata_complete CHECK (
   pg_catalog.num_nonnulls(appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) IN (0,6)),
 ADD CONSTRAINT appointment_protected_values CHECK (appointment_version IS NULL OR (
   appointment_version>0 AND binding_revision>0 AND policy_revision>0
   AND assignment_type IN ('PRIMARY','SECONDARY','TEMPORARY','ACTING')
   AND status IN ('ACTIVE','SUSPENDED','ENDED','CANCELLED')
   AND (valid_to IS NULL OR valid_to>valid_from)
   AND (status<>'ENDED' OR (valid_to IS NOT NULL AND valid_to<=updated_at))
   AND updated_at>=created_at)),
 ADD CONSTRAINT appointment_binding_revision_fk FOREIGN KEY(binding_id,binding_revision)
   REFERENCES public.hidra_org_appointment_binding_revision(binding_id,revision),
 ADD CONSTRAINT appointment_policy_revision_fk FOREIGN KEY(policy_id,policy_revision)
   REFERENCES public.hidra_org_appointment_policy_revision(policy_id,revision),
 ADD CONSTRAINT appointment_last_event_fk FOREIGN KEY(last_event_id)
   REFERENCES public.hidra_org_appointment_event(event_id) DEFERRABLE INITIALLY DEFERRED;

CREATE FUNCTION public.hidra_org_appointment_projection_transition() RETURNS trigger
LANGUAGE plpgsql SET search_path=pg_catalog AS $projection_transition$
BEGIN
 IF TG_OP='DELETE' THEN
  IF OLD.appointment_version IS NOT NULL THEN
   RAISE EXCEPTION 'Protected appointment cannot be deleted' USING ERRCODE='23514';
  END IF;
  RETURN OLD;
 END IF;
 -- Keep private helper ACL checks out of the legacy CHECK-expression plan.
 -- Only a protected row reaches this separately compiled statement.
 IF NEW.appointment_version IS NOT NULL THEN
  IF NOT (public.hidra_org_appointment_exact_id(NEW.id)
      AND public.hidra_org_appointment_exact_id(NEW.employee_id)
      AND public.hidra_org_appointment_exact_id(NEW.organization_unit_id)
      AND public.hidra_org_appointment_exact_id(NEW.position_id)
      AND public.hidra_org_appointment_exact_id(NEW.binding_id)
      AND public.hidra_org_appointment_exact_id(NEW.policy_id)) THEN
   RAISE EXCEPTION 'Invalid appointment identifier' USING ERRCODE='23514';
  END IF;
 END IF;
 IF TG_OP='UPDATE' THEN
  -- A retained unbound row is never silently adopted, nor a protected one reset.
  IF (OLD.appointment_version IS NULL)<>(NEW.appointment_version IS NULL) THEN
   RAISE EXCEPTION 'Appointment ownership cannot be inferred or removed' USING ERRCODE='23514';
  END IF;
  IF OLD.appointment_version IS NOT NULL AND (
     NEW.id IS DISTINCT FROM OLD.id OR NEW.employee_id IS DISTINCT FROM OLD.employee_id
     OR NEW.created_at IS DISTINCT FROM OLD.created_at
     OR OLD.appointment_version=9223372036854775807
     OR NEW.appointment_version IS DISTINCT FROM OLD.appointment_version+1
     OR NEW.last_event_id IS NOT DISTINCT FROM OLD.last_event_id
     OR NEW.updated_at<OLD.updated_at) THEN
   RAISE EXCEPTION 'Invalid appointment version transition' USING ERRCODE='23514';
  END IF;
 ELSIF NEW.appointment_version IS NOT NULL AND NEW.appointment_version<>1 THEN
  RAISE EXCEPTION 'Appointment must start at version one' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$projection_transition$;
CREATE TRIGGER appointment_projection_transition BEFORE INSERT OR UPDATE OR DELETE
 ON public.hidra_org_employee_assignment FOR EACH ROW
 EXECUTE FUNCTION public.hidra_org_appointment_projection_transition();
-- TRUNCATE has no row callbacks. Reject it even when the table is empty.
CREATE TRIGGER appointment_projection_retain BEFORE TRUNCATE
 ON public.hidra_org_employee_assignment FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();

-- Snapshot bytes use the exact database row shape plus its immutable binding.
-- SECURITY INVOKER: this helper conveys no privilege or caller attestation.
CREATE FUNCTION public.hidra_org_appointment_snapshot(p public.hidra_org_employee_assignment) RETURNS jsonb
LANGUAGE sql STABLE SET search_path=pg_catalog SET timezone='UTC' AS $projection_snapshot$
 SELECT pg_catalog.jsonb_build_object('assignment',pg_catalog.to_jsonb(p),'binding',pg_catalog.to_jsonb(b))
 FROM public.hidra_org_appointment_binding_revision b
 JOIN public.hidra_org_appointment_policy_revision r
   ON r.policy_id=(p).policy_id AND r.revision=(p).policy_revision AND r.company_party_id=b.company_party_id
 WHERE b.binding_id=(p).binding_id AND b.revision=(p).binding_revision AND b.organization_unit_id=(p).organization_unit_id
$projection_snapshot$;

CREATE FUNCTION public.hidra_org_appointment_projection_complete() RETURNS trigger
LANGUAGE plpgsql SET search_path=pg_catalog SET timezone='UTC' AS $projection_complete$
DECLARE e public.hidra_org_appointment_event%ROWTYPE; payload jsonb; actual jsonb; prior jsonb; slot text;
BEGIN
 IF NEW.appointment_version IS NULL THEN RETURN NULL; END IF;
 SELECT * INTO e FROM public.hidra_org_appointment_event WHERE event_id=NEW.last_event_id;
 IF NOT FOUND OR e.backend_pid<>pg_catalog.pg_backend_pid() OR e.transaction_id<>pg_catalog.txid_current()
    OR e.employee_id<>NEW.employee_id OR e.sampled_at<>NEW.updated_at THEN
  RAISE EXCEPTION 'Incomplete appointment event' USING ERRCODE='23514';
 END IF;
 actual:=public.hidra_org_appointment_snapshot(NEW);
 IF actual IS NULL OR actual->'binding'->>'company_party_id'<>e.company_party_id THEN
  RAISE EXCEPTION 'Inconsistent appointment ownership' USING ERRCODE='23514';
 END IF;
 payload:=pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb;
 slot:=CASE WHEN NEW.id=e.assignment_id THEN 'after' WHEN NEW.id=e.successor_id THEN 'successor' ELSE NULL END;
 IF slot IS NULL OR payload->slot IS DISTINCT FROM actual
    OR NOT EXISTS(SELECT 1 FROM public.hidra_org_employee_assignment a
       WHERE a.id=NEW.id AND pg_catalog.to_jsonb(a)=pg_catalog.to_jsonb(NEW)) THEN
  RAISE EXCEPTION 'Appointment snapshot does not match projection' USING ERRCODE='23514';
 END IF;
 IF TG_OP='INSERT' THEN
  IF NEW.appointment_version<>1 OR NEW.created_at<>e.sampled_at
     OR (slot='after' AND (e.operation<>'CREATE' OR payload->'before'<>'null'::jsonb))
     OR (slot='successor' AND e.operation<>'TRANSFER') THEN
   RAISE EXCEPTION 'Invalid appointment insertion event' USING ERRCODE='23514';
  END IF;
 ELSE
  prior:=public.hidra_org_appointment_snapshot(OLD);
  IF prior IS NULL OR slot<>'after' OR e.operation='CREATE' OR payload->'before' IS DISTINCT FROM prior
     OR prior->'binding'->>'company_party_id' IS DISTINCT FROM e.company_party_id THEN
   RAISE EXCEPTION 'Appointment prior snapshot mismatch' USING ERRCODE='23514';
  END IF;
  IF e.operation IN ('END','TRANSFER') AND (
     OLD.status<>'ACTIVE' OR NEW.organization_unit_id<>OLD.organization_unit_id
     OR NEW.position_id<>OLD.position_id OR NEW.assignment_type<>OLD.assignment_type
     OR NEW.valid_from<>OLD.valid_from OR NEW.valid_to IS NULL
     OR (OLD.valid_to IS NOT NULL AND NEW.valid_to>OLD.valid_to)
     OR NEW.binding_id<>OLD.binding_id OR NEW.binding_revision<>OLD.binding_revision
     OR NEW.status<>(CASE WHEN NEW.valid_to>e.sampled_at THEN 'ACTIVE' ELSE 'ENDED' END)
     OR (e.operation='TRANSFER' AND OLD.valid_to IS NOT NULL AND NEW.valid_to>=OLD.valid_to)) THEN
   RAISE EXCEPTION 'Invalid appointment ending transition' USING ERRCODE='23514';
  END IF;
  IF e.operation='CORRECT' AND NEW.organization_unit_id=OLD.organization_unit_id
     AND (NEW.binding_id<>OLD.binding_id OR NEW.binding_revision<>OLD.binding_revision) THEN
   RAISE EXCEPTION 'Same-unit correction must retain ownership pin' USING ERRCODE='23514';
  END IF;
 END IF;
 RETURN NULL;
END;
$projection_complete$;
CREATE CONSTRAINT TRIGGER appointment_projection_complete AFTER INSERT OR UPDATE
 ON public.hidra_org_employee_assignment DEFERRABLE INITIALLY DEFERRED FOR EACH ROW
 EXECUTE FUNCTION public.hidra_org_appointment_projection_complete();

-- Both sides schedule this check: an event without a source, a source without
-- an event, or a receipt without its event cannot evade the transaction proof.
CREATE FUNCTION public.hidra_org_appointment_ledger_complete() RETURNS trigger
LANGUAGE plpgsql SET search_path=pg_catalog SET timezone='UTC' AS $ledger_complete$
DECLARE e public.hidra_org_appointment_event%ROWTYPE; r public.hidra_org_appointment_receipt%ROWTYPE;
        a public.hidra_org_employee_assignment%ROWTYPE; s public.hidra_org_employee_assignment%ROWTYPE;
        payload jsonb; command jsonb; total bigint;
BEGIN
 SELECT * INTO e FROM public.hidra_org_appointment_event WHERE event_id=NEW.event_id;
 IF NOT FOUND OR e.backend_pid<>pg_catalog.pg_backend_pid() OR e.transaction_id<>pg_catalog.txid_current() THEN
  RAISE EXCEPTION 'Incomplete appointment ledger' USING ERRCODE='23514';
 END IF;
 payload:=pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb;
 IF pg_catalog.jsonb_typeof(payload) IS DISTINCT FROM 'object' THEN
  RAISE EXCEPTION 'Invalid appointment payload' USING ERRCODE='23514';
 END IF;
 command:=payload->'command';
 IF (SELECT count(*) FROM pg_catalog.jsonb_object_keys(payload))<>4
    OR NOT(payload ?& ARRAY['command','before','after','successor'])
    OR pg_catalog.jsonb_typeof(command) IS DISTINCT FROM 'object'
    OR command->>'kind' IS DISTINCT FROM e.operation
    OR command->>'companyPartyId' IS DISTINCT FROM e.company_party_id
    OR NOT public.hidra_org_appointment_exact_id(command->>'requestId')
    OR e.command_sha256<>pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(command::text,'UTF8')),'hex')
    OR e.canonical_payload<>pg_catalog.convert_to(payload::text,'UTF8') THEN
  RAISE EXCEPTION 'Appointment canonical command mismatch' USING ERRCODE='23514';
 END IF;
 SELECT * INTO a FROM public.hidra_org_employee_assignment WHERE id=e.assignment_id AND last_event_id=e.event_id;
 IF NOT FOUND OR a.employee_id<>e.employee_id OR a.policy_id<>e.policy_id OR a.policy_revision<>e.policy_revision
    OR payload->'after' IS DISTINCT FROM public.hidra_org_appointment_snapshot(a)
    OR (e.operation='CREATE' AND (a.appointment_version<>1 OR payload->'before'<>'null'::jsonb))
    OR (e.operation<>'CREATE' AND (a.appointment_version<=1 OR pg_catalog.jsonb_typeof(payload->'before') IS DISTINCT FROM 'object')) THEN
  RAISE EXCEPTION 'Appointment event projection mismatch' USING ERRCODE='23514';
 END IF;
 SELECT count(*) INTO total FROM public.hidra_org_employee_assignment WHERE last_event_id=e.event_id;
 IF total<>(CASE WHEN e.operation='TRANSFER' THEN 2 ELSE 1 END) THEN
  RAISE EXCEPTION 'Incomplete appointment transfer' USING ERRCODE='23514';
 END IF;
 IF e.operation='TRANSFER' THEN
  SELECT * INTO s FROM public.hidra_org_employee_assignment WHERE id=e.successor_id AND last_event_id=e.event_id;
  IF NOT FOUND OR s.appointment_version<>1 OR s.employee_id<>e.employee_id OR s.valid_from<>a.valid_to
     OR s.created_at<>e.sampled_at OR s.updated_at<>e.sampled_at
     OR s.status<>(CASE WHEN s.valid_to IS NOT NULL AND s.valid_to<=e.sampled_at THEN 'ENDED' ELSE 'ACTIVE' END)
     OR payload->'successor' IS DISTINCT FROM public.hidra_org_appointment_snapshot(s)
     OR payload->'successor'->'binding'->>'company_party_id' IS DISTINCT FROM e.company_party_id THEN
   RAISE EXCEPTION 'Invalid appointment successor' USING ERRCODE='23514';
  END IF;
 ELSIF payload->'successor'<>'null'::jsonb THEN
  RAISE EXCEPTION 'Unexpected appointment successor' USING ERRCODE='23514';
 END IF;
 SELECT * INTO r FROM public.hidra_org_appointment_receipt WHERE event_id=e.event_id;
 IF NOT FOUND OR r.actor_user_id<>e.actor_user_id OR r.command_sha256<>e.command_sha256
    OR r.request_id IS DISTINCT FROM command->>'requestId'
    OR r.backend_pid<>e.backend_pid OR r.transaction_id<>e.transaction_id
    OR r.guarded_at<e.sampled_at OR r.guarded_at>=e.valid_until
    OR r.canonical_result<>e.canonical_payload THEN
  RAISE EXCEPTION 'Appointment receipt does not match event' USING ERRCODE='23514';
 END IF;
 RETURN NULL;
END;
$ledger_complete$;
CREATE CONSTRAINT TRIGGER appointment_event_complete AFTER INSERT ON public.hidra_org_appointment_event
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.hidra_org_appointment_ledger_complete();
CREATE CONSTRAINT TRIGGER appointment_receipt_complete AFTER INSERT ON public.hidra_org_appointment_receipt
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.hidra_org_appointment_ledger_complete();

REVOKE ALL ON FUNCTION public.hidra_org_appointment_projection_transition() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_snapshot(public.hidra_org_employee_assignment) FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_projection_complete() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_ledger_complete() FROM PUBLIC;
