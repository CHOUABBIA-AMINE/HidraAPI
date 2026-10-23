-- HAPI-778 Full840: disambiguate final receipt clock from PRIMARY sweep columns.
-- Preserve001 and every accepted invariant, signature, ACL, crypto and lock boundary.
CREATE OR REPLACE FUNCTION public.hidra_org_appointment_final_guard(
 p_event uuid,p_binding uuid,p_session uuid,p_actor text,p_scope text,p_request text,p_command text,
 p_sample timestamptz,p_native_until timestamptz,p_pid integer,p_xid bigint,
 p_frame bytea,p_mac bytea,p_observed timestamptz) RETURNS boolean
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog SET timezone='UTC' AS $guard$
DECLARE e public.hidra_org_appointment_event%ROWTYPE; payload jsonb; snap jsonb; item jsonb;
 a public.hidra_org_employee_assignment%ROWTYPE; b public.hidra_org_appointment_binding_revision%ROWTYPE;
 policy public.hidra_org_appointment_policy_revision%ROWTYPE; peers jsonb; pairs jsonb;
 slots jsonb; expected jsonb; target text; needs_current boolean; row_count integer; guarded_at_time timestamptz;
BEGIN
 PERFORM public.hidra_org_appointment_final_context();
 IF p_event IS NULL OR p_binding IS NULL OR p_session IS NULL OR p_actor IS NULL OR p_scope IS NULL
   OR p_request IS NULL OR p_command IS NULL OR p_sample IS NULL OR p_native_until IS NULL
   OR p_pid IS NULL OR p_xid IS NULL OR p_observed IS NULL OR p_frame IS NULL OR p_mac IS NULL
   OR p_pid<>pg_catalog.pg_backend_pid() OR p_xid<>pg_catalog.txid_current()
   OR p_observed<p_sample OR p_observed>pg_catalog.clock_timestamp() OR p_observed>=p_native_until
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 SELECT * INTO e FROM public.hidra_org_appointment_event WHERE event_id=p_event;
 IF NOT FOUND OR e.actor_binding_id<>p_binding OR e.actor_session_id<>p_session OR e.actor_user_id<>p_actor
   OR e.command_sha256<>p_command OR e.sampled_at<>p_sample OR e.valid_until>p_native_until
   OR e.backend_pid<>p_pid OR e.transaction_id<>p_xid OR e.valid_until<=p_observed
   OR EXISTS(SELECT 1 FROM public.hidra_org_appointment_receipt WHERE event_id=p_event)
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 payload:=pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb;
 IF e.canonical_payload<>pg_catalog.convert_to(payload::text,'UTF8')
   OR (SELECT count(*) FROM pg_catalog.jsonb_object_keys(payload))<>4
   OR NOT(payload ?& ARRAY['command','before','after','successor'])
   OR payload->'command'->>'kind' IS DISTINCT FROM e.operation
   OR payload->'command'->>'companyPartyId' IS DISTINCT FROM e.company_party_id
   OR payload->'command'->>'requestId' IS DISTINCT FROM p_request
   OR e.command_sha256<>pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to((payload->'command')::text,'UTF8')),'hex')
   OR p_scope<>pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(pg_catalog.jsonb_build_object(
      'family','organization-appointment-v1','companyPartyId',e.company_party_id)::text,'UTF8')),'hex')
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 -- Every full snapshot's immutable binding is real; after/successor match physical rows.
 FOR item IN SELECT value FROM pg_catalog.jsonb_each(payload) WHERE key IN ('before','after','successor') LOOP
  IF item='null'::jsonb THEN CONTINUE; END IF;
  a:=pg_catalog.jsonb_populate_record(NULL::public.hidra_org_employee_assignment,item->'assignment');
  SELECT * INTO b FROM public.hidra_org_appointment_binding_revision
    WHERE binding_id=a.binding_id AND revision=a.binding_revision;
  IF NOT FOUND OR a.employee_id IS DISTINCT FROM e.employee_id
    OR b.organization_unit_id IS DISTINCT FROM a.organization_unit_id OR b.company_party_id IS DISTINCT FROM e.company_party_id
    OR item IS DISTINCT FROM public.hidra_org_appointment_snapshot(a)
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END LOOP;
 SELECT * INTO a FROM public.hidra_org_employee_assignment WHERE id=e.assignment_id AND last_event_id=p_event;
 IF NOT FOUND OR payload->'after' IS DISTINCT FROM public.hidra_org_appointment_snapshot(a)
    OR a.employee_id<>e.employee_id OR a.policy_id<>e.policy_id OR a.policy_revision<>e.policy_revision
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 IF e.successor_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM public.hidra_org_employee_assignment x
     WHERE x.id=e.successor_id AND x.last_event_id=p_event AND x.employee_id=e.employee_id
       AND payload->'successor'=public.hidra_org_appointment_snapshot(x))
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 -- The active policy is exact, never an inferred/default or historical head.
 SELECT r.* INTO policy FROM public.hidra_org_appointment_policy_revision r
  JOIN public.hidra_org_appointment_policy_head h ON h.company_party_id=r.company_party_id
   AND h.policy_id=r.policy_id AND h.revision=r.revision WHERE r.company_party_id=e.company_party_id;
 IF NOT FOUND OR policy.policy_id<>e.policy_id OR policy.revision<>e.policy_revision
   OR NOT public.hidra_org_appointment_policy_valid(pg_catalog.convert_from(policy.canonical_payload,'UTF8')::jsonb)
   OR policy.sha256<>pg_catalog.encode(pg_catalog.sha256(policy.canonical_payload),'hex')
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 SELECT pg_catalog.jsonb_agg(pg_catalog.jsonb_build_array(company,unit) ORDER BY public.hidra_identity_org_commit_utf16(company),public.hidra_identity_org_commit_utf16(unit))
  INTO pairs FROM (SELECT DISTINCT value->'binding'->>'company_party_id' company,value->'binding'->>'organization_unit_id' unit
    FROM pg_catalog.jsonb_each(payload) WHERE key IN ('before','after','successor') AND value<>'null'::jsonb) q;
 SELECT pg_catalog.jsonb_agg(pg_catalog.jsonb_build_array(unit,position_id)) INTO slots FROM (
  SELECT DISTINCT value->'assignment'->>'organization_unit_id' unit,value->'assignment'->>'position_id' position_id
   FROM pg_catalog.jsonb_each(payload) WHERE key IN ('before','after','successor') AND value<>'null'::jsonb) q;
 needs_current:=e.operation IN ('CREATE','TRANSFER') OR
   (e.operation='CORRECT' AND payload->'before'->'assignment'->>'organization_unit_id'
       IS DISTINCT FROM payload->'after'->'assignment'->>'organization_unit_id');
 IF needs_current THEN
  snap:=CASE WHEN e.operation='TRANSFER' THEN payload->'successor' ELSE payload->'after' END;
  IF NOT EXISTS(SELECT 1 FROM public.hidra_party_party WHERE id=e.company_party_id AND status='ACTIVE')
    OR NOT EXISTS(SELECT 1 FROM public.hidra_org_appointment_binding_head h
      WHERE h.organization_unit_id=snap->'assignment'->>'organization_unit_id'
       AND h.binding_id=snap->'binding'->>'binding_id' AND h.revision=(snap->'binding'->>'revision')::bigint)
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END IF;
 FOR item IN SELECT value FROM pg_catalog.jsonb_array_elements(slots) LOOP
  IF NOT EXISTS(SELECT 1 FROM public.hidra_org_employee x
     JOIN public.hidra_org_unit u ON u.id=item->>0 JOIN public.hidra_org_position p ON p.id=item->>1
     WHERE x.id=e.employee_id AND x.status='ACTIVE' AND (x.hired_at IS NULL OR x.hired_at<=p_sample)
      AND (x.terminated_at IS NULL OR (x.terminated_at>p_sample AND e.valid_until<=x.terminated_at))
      AND u.status='ACTIVE' AND u.valid_from<=p_sample
      AND (u.valid_to IS NULL OR (u.valid_to>p_sample AND e.valid_until<=u.valid_to)) AND p.status='ACTIVE')
     OR NOT EXISTS(SELECT 1 FROM public.hidra_org_appointment_lock_key WHERE kind='OCCUPANCY'
       AND key_one=item->>0 AND key_two=item->>1 AND key_three='')
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END LOOP;
 IF NOT EXISTS(SELECT 1 FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=e.employee_id AND key_two='' AND key_three='')
   OR NOT EXISTS(SELECT 1 FROM public.hidra_org_appointment_lock_key WHERE kind='REQUEST' AND key_one=p_actor AND key_two=p_scope AND key_three=p_request)
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 -- Complete bounded final conflict set, including otherwise private foreign-company peers.
 SELECT pg_catalog.jsonb_agg(pg_catalog.to_jsonb(q)) INTO peers FROM (
  SELECT x.* FROM public.hidra_org_employee_assignment x
   WHERE x.id=e.assignment_id OR x.id=e.successor_id OR (x.status='ACTIVE' AND (x.employee_id=e.employee_id
     OR EXISTS(SELECT 1 FROM pg_catalog.jsonb_array_elements(slots) slot WHERE x.organization_unit_id=slot->>0 AND x.position_id=slot->>1)))
   ORDER BY x.id COLLATE "C" LIMIT 1025) q;
 IF peers IS NULL OR pg_catalog.jsonb_array_length(peers)>1024 OR EXISTS(
   SELECT 1 FROM pg_catalog.jsonb_populate_recordset(NULL::public.hidra_org_employee_assignment,peers) x
   WHERE public.hidra_org_appointment_snapshot(x) IS NULL)
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 WITH selected AS (SELECT * FROM pg_catalog.jsonb_populate_recordset(NULL::public.hidra_org_employee_assignment,peers))
 SELECT count(*) INTO row_count FROM selected x JOIN selected y ON x.id COLLATE "C"<y.id COLLATE "C"
  WHERE x.status='ACTIVE' AND y.status='ACTIVE'
   AND (x.valid_to IS NULL OR y.valid_from<x.valid_to) AND (y.valid_to IS NULL OR x.valid_from<y.valid_to)
   AND ((x.employee_id=y.employee_id AND NOT (pg_catalog.convert_from(policy.canonical_payload,'UTF8')::jsonb
       ->'overlaps'->x.assignment_type->>y.assignment_type)::boolean)
    OR (x.employee_id<>y.employee_id AND x.organization_unit_id=y.organization_unit_id AND x.position_id=y.position_id
      AND pg_catalog.convert_from(policy.canonical_payload,'UTF8')::jsonb->>'positionCapacity'='SINGLE_HOLDER'));
 IF row_count<>0 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 IF EXISTS(WITH selected AS (SELECT * FROM pg_catalog.jsonb_populate_recordset(NULL::public.hidra_org_employee_assignment,peers)),
   boundaries AS (SELECT employee_id,valid_from at_time,1 delta FROM selected WHERE status='ACTIVE' AND assignment_type='PRIMARY'
    UNION ALL SELECT employee_id,valid_to,-1 FROM selected WHERE status='ACTIVE' AND assignment_type='PRIMARY' AND valid_to IS NOT NULL),
   totals AS (SELECT t.employee_id,t.at_time,sum(t.delta) delta FROM boundaries t GROUP BY t.employee_id,t.at_time),
   peaks AS (SELECT sum(t.delta) OVER(PARTITION BY t.employee_id ORDER BY t.at_time) peak FROM totals t)
   SELECT 1 FROM peaks WHERE peak>(pg_catalog.convert_from(policy.canonical_payload,'UTF8')::jsonb->>'maxConcurrentPrimary')::integer)
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 expected:=pg_catalog.jsonb_build_object('binding',p_binding,'session',p_session,'actor',p_actor,'action',e.operation,
   'pairs',pairs,'employee',e.employee_id,'sampled',(EXTRACT(epoch FROM e.sampled_at)*1000000)::bigint,
   'nativeUntil',(EXTRACT(epoch FROM p_native_until)*1000000)::bigint,
   'operationUntil',(EXTRACT(epoch FROM e.valid_until)*1000000)::bigint,'event',p_event,
   'command',e.command_sha256,'payload',pg_catalog.encode(pg_catalog.sha256(e.canonical_payload),'hex'));
 -- Consumption and receipt are in this one external statement and one physical XID.
 PERFORM public.hidra_identity_org_commit_verify_consume(p_frame,p_mac,expected);
 guarded_at_time:=pg_catalog.clock_timestamp();
 IF guarded_at_time<e.sampled_at OR guarded_at_time>=e.valid_until THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,
   canonical_result,guarded_at,backend_pid,transaction_id)
 VALUES(p_event,p_actor,p_scope,p_request,p_command,e.canonical_payload,guarded_at_time,p_pid,p_xid);
 -- Execute typed-command/OLD-NEW/complete-transfer/receipt obligations now, before returning true.
 -- A downstream failure rolls back nonce consumption and receipt as part of the failed statement.
 SET CONSTRAINTS ALL IMMEDIATE;
 RETURN true;
EXCEPTION WHEN OTHERS THEN
 RAISE EXCEPTION USING ERRCODE='P0001',MESSAGE='Appointment finalization unavailable';
END;
$guard$;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz) FROM PUBLIC;
