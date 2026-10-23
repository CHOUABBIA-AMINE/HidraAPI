-- HAPI-778 B3: private final write guard; no role, grant, key or application activation.
-- Preserve all earlier migration bodies. Native Java rechecks and the real executor's
-- no-SQL-after-GUARD/observed-commit contract remain required for application acceptance.
CREATE FUNCTION public.hidra_org_appointment_final_fingerprint(p_relation regclass) RETURNS text
LANGUAGE sql STABLE STRICT SET search_path=pg_catalog AS $fingerprint$
WITH target AS (SELECT p_relation AS oid), lines AS (
    SELECT pg_catalog.concat_ws('|','C',a.attname,
        pg_catalog.format_type(a.atttypid,a.atttypmod),(NOT a.attnotnull)::text,
        COALESCE(pg_catalog.pg_get_expr(d.adbin,d.adrelid),''),
        a.attidentity::text,a.attgenerated::text) AS value
      FROM pg_catalog.pg_attribute a LEFT JOIN pg_catalog.pg_attrdef d
        ON d.adrelid=a.attrelid AND d.adnum=a.attnum
     WHERE a.attrelid=(SELECT oid FROM target) AND a.attnum>0 AND NOT a.attisdropped
    UNION
    SELECT pg_catalog.concat_ws('|','K',k.conname,pg_catalog.pg_get_constraintdef(k.oid,true),
        k.contype::text,k.convalidated::text,k.condeferrable::text,k.condeferred::text)
      FROM pg_catalog.pg_constraint k WHERE k.conrelid=(SELECT oid FROM target)
    UNION
    SELECT pg_catalog.concat_ws('|','I',c.relname,pg_catalog.pg_get_indexdef(i.indexrelid),
        i.indisprimary::text,i.indisunique::text,i.indisvalid::text,i.indisready::text)
      FROM pg_catalog.pg_index i JOIN pg_catalog.pg_class c ON c.oid=i.indexrelid
     WHERE i.indrelid=(SELECT oid FROM target)
    UNION
    SELECT pg_catalog.concat_ws('|','T',t.tgname,pg_catalog.pg_get_triggerdef(t.oid,true),
        t.tgenabled::text,n.nspname||'.'||p.proname||'('||
        pg_catalog.pg_get_function_identity_arguments(p.oid)||')')
      FROM pg_catalog.pg_trigger t JOIN pg_catalog.pg_proc p ON p.oid=t.tgfoid
      JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
     WHERE t.tgrelid=(SELECT oid FROM target) AND NOT t.tgisinternal
    UNION
    SELECT 'F|'||pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(
        pg_catalog.pg_get_functiondef(p.oid),'UTF8')),'hex')
      FROM pg_catalog.pg_trigger t JOIN pg_catalog.pg_proc p ON p.oid=t.tgfoid
     WHERE t.tgrelid=(SELECT oid FROM target) AND NOT t.tgisinternal
)
SELECT pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(
    pg_catalog.string_agg(value,E'\n' ORDER BY value COLLATE "C"),'UTF8')),'hex') FROM lines
$fingerprint$;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_final_fingerprint(regclass) FROM PUBLIC;

CREATE FUNCTION public.hidra_org_appointment_final_context() RETURNS void
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog SET timezone='UTC' AS $context$
DECLARE r record; own oid;
BEGIN
 PERFORM public.hidra_identity_org_commit_caller();
 PERFORM public.hidra_identity_org_commit_locks();
 SELECT proowner INTO own FROM pg_catalog.pg_proc
  WHERE oid='public.hidra_org_appointment_final_context()'::regprocedure;
 IF current_setting('transaction_isolation')<>'read committed'
   OR current_setting('transaction_read_only')<>'off'
   OR current_setting('session_replication_role')<>'origin'
   OR pg_catalog.has_schema_privilege(session_user,'pg_catalog','CREATE')
   OR EXISTS(SELECT 1 FROM pg_catalog.pg_roles x WHERE x.rolname IN
       ('pg_execute_server_program','pg_read_server_files','pg_write_server_files',
        'pg_read_all_data','pg_write_all_data','pg_signal_backend','pg_checkpoint')
       AND pg_catalog.pg_has_role(session_user,x.oid,'MEMBER'))
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_org_activate_appointment_binding(text,text,text,bigint)','EXECUTE')
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb)','EXECUTE')
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_identity_org_commit_admin(bigint,text,uuid,name,bytea)','EXECUTE')
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_identity_org_commit_verify_consume(bytea,bytea,jsonb)','EXECUTE')
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 FOR r IN SELECT * FROM (VALUES
  ('public.hidra_org_employee','ShareRowExclusiveLock'),
  ('public.hidra_org_unit','ShareRowExclusiveLock'),
  ('public.hidra_org_position','ShareRowExclusiveLock'),
  ('public.hidra_org_appointment_binding_revision','AccessShareLock'),
  ('public.hidra_org_appointment_binding_head','ShareRowExclusiveLock'),
  ('public.hidra_org_appointment_policy_revision','AccessShareLock'),
  ('public.hidra_org_appointment_policy_head','ShareRowExclusiveLock'),
  ('public.hidra_org_appointment_configuration_event','AccessShareLock'),
  ('public.hidra_org_appointment_lock_key','ShareRowExclusiveLock'),
  ('public.hidra_org_employee_assignment','ShareRowExclusiveLock'),
  ('public.hidra_org_appointment_event','AccessShareLock'),
  ('public.hidra_org_appointment_receipt','AccessShareLock'),
  ('public.hidra_party_party','ShareRowExclusiveLock'),
  ('public.hidra_party_eligibility_generation','AccessShareLock')
 ) f(relation,mode) LOOP
  IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_locks l WHERE l.pid=pg_catalog.pg_backend_pid()
    AND l.locktype='relation' AND l.granted AND l.relation=pg_catalog.to_regclass(r.relation) AND l.mode=r.mode)
    OR NOT EXISTS(SELECT 1 FROM pg_catalog.pg_class c WHERE c.oid=pg_catalog.to_regclass(r.relation)
      AND c.relowner=own AND c.relkind='r' AND c.relpersistence='p' AND NOT c.relispartition
      AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
      AND NOT pg_catalog.pg_has_role(session_user,c.relowner,'MEMBER'))
    OR EXISTS(SELECT 1 FROM pg_catalog.pg_inherits WHERE inhrelid=pg_catalog.to_regclass(r.relation)
      OR inhparent=pg_catalog.to_regclass(r.relation))
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END LOOP;
 FOR r IN SELECT * FROM (VALUES
  ('public.hidra_org_employee','1a2b849ba9dca9575a80f4351f2d4c4d6cfa5d397f847ed3bb94109f1ad0193e'),
  ('public.hidra_org_unit','afd811b6baa899a7f5647848694592fde824a15fd5b7b4e3af7062da7a1dbfcb'),
  ('public.hidra_org_position','f6a88ae626ae77f49d5adbadc25c4acf4781f3a6cf05c96adc6e4d63d2f22c68'),
  ('public.hidra_org_appointment_binding_revision','9b58d4f707daf02f27fd0eee49471589c82dfe4d1127279f8eb2945f06b200b8'),
  ('public.hidra_org_appointment_binding_head','1a4a34ca838aa14f1007df01e8aeca90c6c1960b0535cc153550e3a495f32be7'),
  ('public.hidra_org_appointment_policy_revision','41c925412d7ea47a3d0e54538f6e45cdd1f379ee8221f05065521a4c25ff072a'),
  ('public.hidra_org_appointment_policy_head','faea4511d597c0f39f0fc4a28a2e99568daf7fb06ffec1182f721f41af3a6e0e'),
  ('public.hidra_org_appointment_configuration_event','f068a3d5c8aad7d23e4d7d407582649c4e58a26b58133a64d1dbaa511918c9ea'),
  ('public.hidra_org_appointment_lock_key','38259a05133312a2ad0c8fa205f073aabea8f2b781ba85de03929f8b1d6444fa'),
  ('public.hidra_org_employee_assignment','32d8113ab0f7d677837cd66c471351efd25ca3b0c05f76aacaeda9aab4b8c406'),
  ('public.hidra_org_appointment_event','c919adf7b59aef43d49ad3bdb2ddcc98c63f0529776132e0edc081aec2669e76'),
  ('public.hidra_org_appointment_receipt','2c6a183766db9b11b96f4441bf5f3fc68baf4b794b7effedcefdf7243c03ab65')
 ) p(relation,sha) LOOP
  IF public.hidra_org_appointment_final_fingerprint(r.relation::regclass) IS DISTINCT FROM r.sha
    OR pg_catalog.has_table_privilege(session_user,r.relation,'DELETE,TRUNCATE,TRIGGER')
    OR pg_catalog.has_any_column_privilege(session_user,r.relation,'REFERENCES')
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
  IF r.relation IN ('public.hidra_org_appointment_binding_revision','public.hidra_org_appointment_policy_revision',
       'public.hidra_org_appointment_configuration_event','public.hidra_org_appointment_receipt') AND
    (pg_catalog.has_table_privilege(session_user,r.relation,'INSERT,UPDATE,REFERENCES')
      OR pg_catalog.has_any_column_privilege(session_user,r.relation,'INSERT,UPDATE,REFERENCES'))
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
  IF r.relation IN ('public.hidra_org_appointment_binding_head','public.hidra_org_appointment_policy_head') AND
    (pg_catalog.has_table_privilege(session_user,r.relation,'INSERT,REFERENCES')
      OR pg_catalog.has_any_column_privilege(session_user,r.relation,'INSERT,REFERENCES'))
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END LOOP;
 -- Every pre-existing routine is pinned to its accepted latest migration body.
 FOR r IN SELECT * FROM (VALUES
  ('hidra_org_appointment_exact_id','ef75cca90db3f80338e710d62d8db04ed4b8e9d000f3e78b878058592761e5d2'),
  ('hidra_org_appointment_policy_valid','62e41666f14398fd36f19184ff7e77ea094f8110fb67b306a7b2f609dc3fffda'),
  ('hidra_org_appointment_configuration_immutable','77db52e487758bad1904239e7d83f166a3e1f6089fd2ac0be07b1f5d90e8e713'),
  ('hidra_org_appointment_configuration_context','7b2d44b63fbaebcffa7aef92e5478027415fd17a4ed2179c91760c244d168607'),
  ('hidra_org_appointment_configuration_locks','3473858642398f81ad5bbbd876ca78b4f0ba6a18167a145182546505d5d71383'),
  ('hidra_org_activate_appointment_binding','af95d48f202326b5d48594c8e6f87cf93521ab8c3194faa7c2f467cb46dcfe1b'),
  ('hidra_org_activate_appointment_policy','e2741065f63d483473cbb12d9ea8a81bfd47931321acd04a7107adfda9cfe921'),
  ('hidra_org_appointment_configuration_complete','7608688dcabd3fdafb256155e5e739d2e6146226fe5530138ea9915dbedb34d7'),
  ('hidra_org_appointment_head_transition','cd1dd4aa3bc5a258109189ada8c68b2d1ed227f06d0e631212c00a6e4ccdb553'),
  ('hidra_org_appointment_projection_transition','525d6eef06bb05e09fa6bfe32dc04ff953286d2cf4fdf2b548d1626b4c160ec9'),
  ('hidra_org_appointment_snapshot','4d5932127c531b2fb2906dee47c9f98607fa080cd6d7a57c94bb3902026c8962'),
  ('hidra_org_appointment_projection_complete','0f6d9118185119c3239b052d2ba53e969e4ada9a4353e7a0e6d351702b1a97a6'),
  ('hidra_org_appointment_ledger_complete','2ad3f0c7c64d18e0934454232c72afc1808e5ca8f2ad6b66e93f0f25d09a238b'),
  ('hidra_org_appointment_command_time_matches','0c6b5b29cdda3e556ead9fdbea9fc7ce41dba5af4e5280f343f1d3d27fcbf5ff'),
  ('hidra_org_appointment_typed_command_complete','9a7129f9c2971fd12bc7572714cfdda5e79dc798b8131b50dd32529636967ce9')
 ) f(name,sha) LOOP
  IF (SELECT count(*)<>1 OR NOT coalesce(bool_and(p.proowner=own AND
      pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')=r.sha
      AND NOT EXISTS(SELECT 1 FROM pg_catalog.aclexplode(coalesce(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
       WHERE a.grantee=0 AND a.privilege_type='EXECUTE')),false)
      FROM pg_catalog.pg_proc p JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace
      WHERE n.nspname='public' AND p.proname=r.name)
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END LOOP;
 -- New routines retain closed ACLs, fixed paths and the same migration owner.
 FOR r IN SELECT * FROM (VALUES
  ('public.hidra_org_appointment_final_fingerprint(regclass)',false),
  ('public.hidra_org_appointment_final_context()',true),
  ('public.hidra_org_appointment_final_guard(uuid,uuid,uuid,text,text,text,text,timestamp with time zone,timestamp with time zone,integer,bigint,bytea,bytea,timestamp with time zone)',true)
 ) f(signature,definer) LOOP
  IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_proc p WHERE p.oid=pg_catalog.to_regprocedure(r.signature)
      AND p.proowner=own AND p.prosecdef=r.definer AND 'search_path=pg_catalog'=ANY(p.proconfig)
      AND pg_catalog.cardinality(p.proconfig)=CASE WHEN r.definer THEN 2 ELSE 1 END
      AND (NOT r.definer OR EXISTS(SELECT 1 FROM pg_catalog.unnest(p.proconfig) c WHERE pg_catalog.lower(c)='timezone=utc'))
      AND NOT EXISTS(SELECT 1 FROM pg_catalog.aclexplode(coalesce(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
       WHERE a.grantee=0 AND a.privilege_type='EXECUTE'))
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END LOOP;
END;
$context$;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_final_context() FROM PUBLIC;

-- Exactly the registered WRITE_GUARD interface. Scalars must match the real event;
-- frame/MAC authenticate the native request and the exact complete payload independently.
CREATE FUNCTION public.hidra_org_appointment_final_guard(
 p_event uuid,p_binding uuid,p_session uuid,p_actor text,p_scope text,p_request text,p_command text,
 p_sample timestamptz,p_native_until timestamptz,p_pid integer,p_xid bigint,
 p_frame bytea,p_mac bytea,p_observed timestamptz) RETURNS boolean
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog SET timezone='UTC' AS $guard$
DECLARE e public.hidra_org_appointment_event%ROWTYPE; payload jsonb; snap jsonb; item jsonb;
 a public.hidra_org_employee_assignment%ROWTYPE; b public.hidra_org_appointment_binding_revision%ROWTYPE;
 policy public.hidra_org_appointment_policy_revision%ROWTYPE; peers jsonb; pairs jsonb;
 slots jsonb; expected jsonb; target text; needs_current boolean; row_count integer; at_time timestamptz;
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
   totals AS (SELECT employee_id,at_time,sum(delta) delta FROM boundaries GROUP BY employee_id,at_time),
   peaks AS (SELECT sum(delta) OVER(PARTITION BY employee_id ORDER BY at_time) peak FROM totals)
   SELECT 1 FROM peaks WHERE peak>(pg_catalog.convert_from(policy.canonical_payload,'UTF8')::jsonb->>'maxConcurrentPrimary')::integer)
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 expected:=pg_catalog.jsonb_build_object('binding',p_binding,'session',p_session,'actor',p_actor,'action',e.operation,
   'pairs',pairs,'employee',e.employee_id,'sampled',(EXTRACT(epoch FROM e.sampled_at)*1000000)::bigint,
   'nativeUntil',(EXTRACT(epoch FROM p_native_until)*1000000)::bigint,
   'operationUntil',(EXTRACT(epoch FROM e.valid_until)*1000000)::bigint,'event',p_event,
   'command',e.command_sha256,'payload',pg_catalog.encode(pg_catalog.sha256(e.canonical_payload),'hex'));
 -- Consumption and receipt are in this one external statement and one physical XID.
 PERFORM public.hidra_identity_org_commit_verify_consume(p_frame,p_mac,expected);
 at_time:=pg_catalog.clock_timestamp();
 IF at_time<e.sampled_at OR at_time>=e.valid_until THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,
   canonical_result,guarded_at,backend_pid,transaction_id)
 VALUES(p_event,p_actor,p_scope,p_request,p_command,e.canonical_payload,at_time,p_pid,p_xid);
 -- Execute typed-command/OLD-NEW/complete-transfer/receipt obligations now, before returning true.
 -- A downstream failure rolls back nonce consumption and receipt as part of the failed statement.
 SET CONSTRAINTS ALL IMMEDIATE;
 RETURN true;
EXCEPTION WHEN OTHERS THEN
 RAISE EXCEPTION USING ERRCODE='P0001',MESSAGE='Appointment finalization unavailable';
END;
$guard$;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz) FROM PUBLIC;
