-- HAPI-778 B3: forward repair for the JOURNAL receipt alias / PLpgSQL record collision.
-- Preserve all installed migration bytes, POLICY behavior, graph checks, fences, ACLs and clock bounds.
CREATE OR REPLACE FUNCTION public.hidra_org_appointment_read_guard(
 p_expected jsonb,p_sample timestamptz,p_until timestamptz,p_pid integer,p_xid bigint,
 p_observed timestamptz) RETURNS boolean
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog SET timezone='UTC' AS $read_guard$
DECLARE r record; own oid; actual jsonb; checked_at timestamptz; chosen jsonb; entry record; part record; binding_row jsonb;
 policy_row jsonb; binding_event jsonb; policy_event jsonb; matched boolean:=false; body jsonb; amount integer;
BEGIN
 IF p_expected IS NULL OR p_sample IS NULL OR p_until IS NULL OR p_pid IS NULL OR p_xid IS NULL
   OR p_observed IS NULL OR NOT pg_catalog.isfinite(p_sample) OR NOT pg_catalog.isfinite(p_until)
   OR p_pid<>pg_catalog.pg_backend_pid() OR p_xid<>pg_catalog.txid_current()
   OR p_sample>=p_until OR p_observed<p_sample OR p_observed>=p_until
   OR p_observed>pg_catalog.clock_timestamp()
   OR pg_catalog.jsonb_typeof(p_expected) IS DISTINCT FROM 'object'
   OR pg_catalog.octet_length(p_expected::text)>131072
   OR p_expected->>'kind' NOT IN ('POLICY','JOURNAL')
   OR p_expected->>'kind' IS NULL
   OR (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p_expected))<>(CASE WHEN p_expected->>'kind'='POLICY' THEN 7 ELSE 3 END)
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 PERFORM public.hidra_identity_org_commit_caller();
 SELECT proowner INTO own FROM pg_catalog.pg_proc
  WHERE oid='public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz)'::regprocedure;
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
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_org_appointment_configuration_locks()','EXECUTE')
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_org_appointment_configuration_context(text)','EXECUTE')
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_identity_org_commit_admin(bigint,text,uuid,name,bytea)','EXECUTE')
   OR pg_catalog.has_function_privilege(session_user,'public.hidra_identity_org_commit_verify_consume(bytea,bytea,jsonb)','EXECUTE')
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 FOR r IN SELECT * FROM (VALUES
  ('public.hidra_identity_attribute_definition','ShareRowExclusiveLock'),
  ('public.hidra_identity_authorization_policy','ShareRowExclusiveLock'),
  ('public.hidra_identity_authorization_policy_rule','ShareRowExclusiveLock'),
  ('public.hidra_identity_authorization_policy_version','ShareRowExclusiveLock'),
  ('public.hidra_identity_external_group_mapping','ShareRowExclusiveLock'),
  ('public.hidra_identity_external_identity','ShareRowExclusiveLock'),
  ('public.hidra_identity_external_permission_mapping','ShareRowExclusiveLock'),
  ('public.hidra_identity_external_role_mapping','ShareRowExclusiveLock'),
  ('public.hidra_identity_group','ShareRowExclusiveLock'),
  ('public.hidra_identity_group_role_grant','ShareRowExclusiveLock'),
  ('public.hidra_identity_local_credential','ShareRowExclusiveLock'),
  ('public.hidra_identity_login_session','ShareRowExclusiveLock'),
  ('public.hidra_identity_operation_binding','ShareRowExclusiveLock'),
  ('public.hidra_identity_permission','ShareRowExclusiveLock'),
  ('public.hidra_identity_provider','ShareRowExclusiveLock'),
  ('public.hidra_identity_role','ShareRowExclusiveLock'),
  ('public.hidra_identity_role_permission_grant','ShareRowExclusiveLock'),
  ('public.hidra_identity_subject_security_attribute','ShareRowExclusiveLock'),
  ('public.hidra_identity_user','ShareRowExclusiveLock'),
  ('public.hidra_identity_user_group_membership','ShareRowExclusiveLock'),
  ('public.hidra_identity_user_permission_grant','ShareRowExclusiveLock'),
  ('public.hidra_identity_user_role_grant','ShareRowExclusiveLock'),
  ('public.hidra_identity_external_mapping_generation','AccessShareLock'),
  ('public.hidra_identity_bound_source_claim_evidence','ShareLock'),
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
  ('public.hidra_org_appointment_receipt','AccessShareLock')
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
  ('public.hidra_org_appointment_final_guard(uuid,uuid,uuid,text,text,text,text,timestamp with time zone,timestamp with time zone,integer,bigint,bytea,bytea,timestamp with time zone)',true),
  ('public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz)',true)
 ) f(signature,definer) LOOP
  IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_proc p WHERE p.oid=pg_catalog.to_regprocedure(r.signature)
      AND p.proowner=own AND p.prosecdef=r.definer
      AND (CASE WHEN r.definer THEN 'search_path=pg_catalog' ELSE 'search_path=pg_catalog, public' END)=ANY(p.proconfig)
      AND pg_catalog.cardinality(p.proconfig)=CASE WHEN r.definer THEN 2 ELSE 1 END
      AND (NOT r.definer OR EXISTS(SELECT 1 FROM pg_catalog.unnest(p.proconfig) c WHERE pg_catalog.lower(c)='timezone=utc'))
      AND NOT EXISTS(SELECT 1 FROM pg_catalog.aclexplode(coalesce(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
       WHERE a.grantee=0 AND a.privilege_type='EXECUTE'))
  THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END LOOP;
 IF p_expected->>'kind'='POLICY' THEN
 -- The complete current revision/head/activation-event graph must equal the pre-guard image.
 SELECT pg_catalog.jsonb_build_object('kind','POLICY',
   'bindingHead',pg_catalog.to_jsonb(bh),'bindingRevision',pg_catalog.to_jsonb(b),
   'bindingEvent',pg_catalog.to_jsonb(be),'policyHead',pg_catalog.to_jsonb(ph),
   'policyRevision',pg_catalog.to_jsonb(p),'policyEvent',pg_catalog.to_jsonb(pe))
 INTO actual
 FROM public.hidra_org_appointment_binding_head bh
 JOIN public.hidra_org_appointment_binding_revision b
   ON b.organization_unit_id=bh.organization_unit_id AND b.binding_id=bh.binding_id
  AND b.revision=bh.revision AND b.company_party_id=bh.company_party_id
 JOIN public.hidra_org_appointment_policy_head ph ON ph.company_party_id=b.company_party_id
 JOIN public.hidra_org_appointment_policy_revision p
   ON p.company_party_id=ph.company_party_id AND p.policy_id=ph.policy_id AND p.revision=ph.revision
 JOIN public.hidra_org_appointment_configuration_event be ON be.event_id=bh.last_event_id
 JOIN public.hidra_org_appointment_configuration_event pe ON pe.event_id=ph.last_event_id
 WHERE bh.organization_unit_id=p_expected->'bindingHead'->>'organization_unit_id'
   AND bh.company_party_id=p_expected->'bindingHead'->>'company_party_id'
   AND bh.generation=bh.revision AND ph.generation=ph.revision
   AND bh.event_kind='BINDING' AND ph.event_kind='POLICY'
   AND be.kind='BINDING' AND be.scope_id=b.organization_unit_id
   AND be.identity_id=b.binding_id AND be.revision=b.revision
   AND be.company_party_id=b.company_party_id AND be.after_payload=pg_catalog.to_jsonb(b)
   AND pe.kind='POLICY' AND pe.scope_id=p.company_party_id
   AND pe.identity_id=p.policy_id AND pe.revision=p.revision
   AND pe.company_party_id=p.company_party_id AND pe.after_payload=pg_catalog.to_jsonb(p)
   AND p.canonical_payload=pg_catalog.convert_to(p.policy::text,'UTF8')
   AND p.sha256=pg_catalog.encode(pg_catalog.sha256(p.canonical_payload),'hex')
   AND public.hidra_org_appointment_policy_valid(p.policy);

 ELSE
 chosen:=p_expected->'selector';
 IF pg_catalog.jsonb_typeof(chosen) IS DISTINCT FROM 'object'
   OR (SELECT count(*) FROM pg_catalog.jsonb_object_keys(chosen))<>9
   OR NOT pg_catalog.jsonb_exists_all(chosen,ARRAY['scope','company','assignment','employee','unit','position','version','limit','cursor'])
   OR pg_catalog.jsonb_typeof(chosen->'company') IS DISTINCT FROM 'string'
   OR NOT public.hidra_org_appointment_exact_id(chosen->>'company')
   OR chosen->>'scope' NOT IN ('ASSIGNMENT','EMPLOYEE','POSITION','EXACT')
   OR chosen->>'scope' IS NULL
   OR pg_catalog.jsonb_typeof(p_expected->'events') IS DISTINCT FROM 'array'
   OR pg_catalog.jsonb_array_length(p_expected->'events') NOT BETWEEN 1 AND 1024
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 IF chosen->>'scope'='EXACT' THEN
   IF pg_catalog.jsonb_typeof(chosen->'assignment') IS DISTINCT FROM 'string'
      OR NOT public.hidra_org_appointment_exact_id(chosen->>'assignment')
      OR pg_catalog.jsonb_typeof(chosen->'version') IS DISTINCT FROM 'number'
      OR (chosen->>'version') !~ '^[1-9][0-9]*$'
      OR (chosen->>'version')::numeric>9223372036854775807
      OR chosen->'employee'<>'null'::jsonb OR chosen->'unit'<>'null'::jsonb
      OR chosen->'position'<>'null'::jsonb OR chosen->'limit'<>'null'::jsonb OR chosen->'cursor'<>'null'::jsonb
   THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 ELSE
   IF pg_catalog.jsonb_typeof(chosen->'limit') IS DISTINCT FROM 'number'
      OR (chosen->>'limit') !~ '^[1-9][0-9]*$' OR (chosen->>'limit')::numeric NOT BETWEEN 1 AND 100
      OR chosen->'version'<>'null'::jsonb
      OR (chosen->'cursor'<>'null'::jsonb AND (pg_catalog.jsonb_typeof(chosen->'cursor') IS DISTINCT FROM 'string'
          OR pg_catalog.length(chosen->>'cursor') NOT BETWEEN 1 AND 2048))
   THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
   IF (chosen->>'scope'='ASSIGNMENT' AND (pg_catalog.jsonb_typeof(chosen->'assignment') IS DISTINCT FROM 'string'
           OR NOT public.hidra_org_appointment_exact_id(chosen->>'assignment')
           OR chosen->'employee'<>'null'::jsonb OR chosen->'unit'<>'null'::jsonb OR chosen->'position'<>'null'::jsonb))
      OR (chosen->>'scope'='EMPLOYEE' AND (pg_catalog.jsonb_typeof(chosen->'employee') IS DISTINCT FROM 'string'
           OR NOT public.hidra_org_appointment_exact_id(chosen->>'employee')
           OR chosen->'assignment'<>'null'::jsonb OR chosen->'unit'<>'null'::jsonb OR chosen->'position'<>'null'::jsonb))
      OR (chosen->>'scope'='POSITION' AND (pg_catalog.jsonb_typeof(chosen->'unit') IS DISTINCT FROM 'string'
           OR pg_catalog.jsonb_typeof(chosen->'position') IS DISTINCT FROM 'string'
           OR NOT public.hidra_org_appointment_exact_id(chosen->>'unit')
           OR NOT public.hidra_org_appointment_exact_id(chosen->>'position')
           OR chosen->'assignment'<>'null'::jsonb OR chosen->'employee'<>'null'::jsonb))
   THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END IF;
 WITH selected AS (
   SELECT e.*,pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb body
     FROM public.hidra_org_appointment_event e
    WHERE CASE chosen->>'scope'
      WHEN 'ASSIGNMENT' THEN e.assignment_id=chosen->>'assignment' OR e.successor_id=chosen->>'assignment'
      WHEN 'EXACT' THEN e.assignment_id=chosen->>'assignment' OR e.successor_id=chosen->>'assignment'
      WHEN 'EMPLOYEE' THEN e.employee_id=chosen->>'employee'
      WHEN 'POSITION' THEN EXISTS(SELECT 1 FROM (VALUES
          (pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'before'),
          (pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'after'),
          (pg_catalog.convert_from(e.canonical_payload,'UTF8')::jsonb->'successor')) s(snapshot)
          WHERE s.snapshot->'assignment'->>'organization_unit_id'=chosen->>'unit'
            AND s.snapshot->'assignment'->>'position_id'=chosen->>'position')
      ELSE false END
    ORDER BY e.sampled_at,e.event_id LIMIT 1025)
 SELECT pg_catalog.jsonb_build_object('kind','JOURNAL','selector',chosen,
     'events',pg_catalog.jsonb_agg(pg_catalog.jsonb_build_object('event',pg_catalog.to_jsonb(e)-'body',
                       'receipt',pg_catalog.to_jsonb(receipt_row),'pins',(
                       SELECT pg_catalog.jsonb_agg(pg_catalog.jsonb_build_object('slot',s.slot,
                           'binding',pg_catalog.to_jsonb(b),'policy',pg_catalog.to_jsonb(p),
                           'bindingEvent',pg_catalog.to_jsonb(be),'policyEvent',pg_catalog.to_jsonb(pe)) ORDER BY s.slot)
                         FROM (VALUES ('before',e.body->'before'),('after',e.body->'after'),
                             ('successor',e.body->'successor')) s(slot,snapshot)
                         LEFT JOIN public.hidra_org_appointment_binding_revision b
                           ON b.binding_id=s.snapshot->'assignment'->>'binding_id'
                          AND b.revision=(s.snapshot->'assignment'->>'binding_revision')::bigint
                         LEFT JOIN public.hidra_org_appointment_policy_revision p
                           ON p.policy_id=s.snapshot->'assignment'->>'policy_id'
                          AND p.revision=(s.snapshot->'assignment'->>'policy_revision')::bigint
                         LEFT JOIN public.hidra_org_appointment_configuration_event be
                           ON be.kind='BINDING' AND be.identity_id=b.binding_id AND be.revision=b.revision
                         LEFT JOIN public.hidra_org_appointment_configuration_event pe
                           ON pe.kind='POLICY' AND pe.identity_id=p.policy_id AND pe.revision=p.revision
                        WHERE s.snapshot IS NOT NULL AND s.snapshot<>'null'::jsonb)) ORDER BY e.sampled_at,e.event_id)),count(*)
 INTO actual,amount FROM selected e
 LEFT JOIN public.hidra_org_appointment_receipt receipt_row ON receipt_row.event_id=e.event_id;
 IF amount NOT BETWEEN 1 AND 1024 OR actual IS DISTINCT FROM p_expected
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 -- Validate every retained receipt and every immutable pin, including slots outside a returned page.
 FOR entry IN SELECT value AS image FROM pg_catalog.jsonb_array_elements(actual->'events') LOOP
   body:=pg_catalog.convert_from(pg_catalog.decode(pg_catalog.substr(entry.image->'event'->>'canonical_payload',3),'hex'),'UTF8')::jsonb;
   IF NOT coalesce(
       entry.image->'receipt'->>'event_id'=entry.image->'event'->>'event_id'
       AND entry.image->'receipt'->>'actor_user_id'=entry.image->'event'->>'actor_user_id'
       AND entry.image->'receipt'->>'command_sha256'=entry.image->'event'->>'command_sha256'
       AND entry.image->'receipt'->>'canonical_result'=entry.image->'event'->>'canonical_payload'
       AND entry.image->'receipt'->>'request_id'=body->'command'->>'requestId'
       AND body->'command'->>'kind'=entry.image->'event'->>'operation'
       AND body->'command'->>'companyPartyId'=entry.image->'event'->>'company_party_id'
       AND entry.image->'receipt'->>'backend_pid'=entry.image->'event'->>'backend_pid'
       AND entry.image->'receipt'->>'transaction_id'=entry.image->'event'->>'transaction_id'
       AND (entry.image->'receipt'->>'guarded_at')::timestamptz>=(entry.image->'event'->>'sampled_at')::timestamptz
       AND (entry.image->'receipt'->>'guarded_at')::timestamptz<(entry.image->'event'->>'valid_until')::timestamptz
       AND pg_catalog.encode(pg_catalog.convert_to(body::text,'UTF8'),'hex')=pg_catalog.substr(entry.image->'event'->>'canonical_payload',3)
       AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to((body->'command')::text,'UTF8')),'hex')=entry.image->'event'->>'command_sha256'
       AND pg_catalog.jsonb_typeof(body->'after')='object'
       AND CASE entry.image->'event'->>'operation'
         WHEN 'CREATE' THEN body->'before'='null'::jsonb AND body->'successor'='null'::jsonb
         WHEN 'END' THEN pg_catalog.jsonb_typeof(body->'before')='object' AND body->'successor'='null'::jsonb
         WHEN 'CORRECT' THEN pg_catalog.jsonb_typeof(body->'before')='object' AND body->'successor'='null'::jsonb
         WHEN 'TRANSFER' THEN pg_catalog.jsonb_typeof(body->'before')='object' AND pg_catalog.jsonb_typeof(body->'successor')='object'
         ELSE false END,false)
   THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
   FOR part IN SELECT * FROM (VALUES ('before',body->'before'),('after',body->'after'),('successor',body->'successor')) s(slot,snapshot)
     WHERE s.snapshot IS NOT NULL AND s.snapshot<>'null'::jsonb LOOP
     SELECT value->'binding',value->'policy',value->'bindingEvent',value->'policyEvent'
       INTO binding_row,policy_row,binding_event,policy_event
       FROM pg_catalog.jsonb_array_elements(entry.image->'pins') WHERE value->>'slot'=part.slot;
     IF NOT coalesce(pg_catalog.jsonb_typeof(part.snapshot->'assignment')='object'
        AND part.snapshot->'binding'=binding_row
        AND part.snapshot->'assignment'->>'binding_id'=binding_row->>'binding_id'
        AND part.snapshot->'assignment'->>'binding_revision'=binding_row->>'revision'
        AND part.snapshot->'assignment'->>'organization_unit_id'=binding_row->>'organization_unit_id'
        AND part.snapshot->'assignment'->>'employee_id'=entry.image->'event'->>'employee_id'
        AND part.snapshot->'assignment'->>'id'=CASE WHEN part.slot='successor'
            THEN entry.image->'event'->>'successor_id' ELSE entry.image->'event'->>'assignment_id' END
        AND (part.snapshot->'assignment'->>'appointment_version')::bigint>0
        AND binding_row->>'company_party_id'=entry.image->'event'->>'company_party_id'
        AND part.snapshot->'assignment'->>'policy_id'=policy_row->>'policy_id'
        AND part.snapshot->'assignment'->>'policy_revision'=policy_row->>'revision'
        AND policy_row->>'company_party_id'=binding_row->>'company_party_id'
        AND pg_catalog.encode(pg_catalog.convert_to((policy_row->'policy')::text,'UTF8'),'hex')=pg_catalog.substr(policy_row->>'canonical_payload',3)
        AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.decode(pg_catalog.substr(policy_row->>'canonical_payload',3),'hex')),'hex')=policy_row->>'sha256'
        AND public.hidra_org_appointment_policy_valid(policy_row->'policy')
        AND binding_event->>'kind'='BINDING' AND binding_event->>'scope_id'=binding_row->>'organization_unit_id'
        AND binding_event->>'company_party_id'=binding_row->>'company_party_id'
        AND binding_event->>'identity_id'=binding_row->>'binding_id' AND binding_event->>'revision'=binding_row->>'revision'
        AND binding_event->'after_payload'=binding_row
        AND policy_event->>'kind'='POLICY' AND policy_event->>'scope_id'=policy_row->>'company_party_id'
        AND policy_event->>'company_party_id'=policy_row->>'company_party_id'
        AND policy_event->>'identity_id'=policy_row->>'policy_id' AND policy_event->>'revision'=policy_row->>'revision'
        AND policy_event->'after_payload'=policy_row,false)
     THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
     IF binding_row->>'company_party_id'=chosen->>'company' AND
       (chosen->>'scope'<>'EXACT' OR (part.snapshot->'assignment'->>'id'=chosen->>'assignment'
         AND part.snapshot->'assignment'->>'appointment_version'=chosen->>'version'))
     THEN matched:=true; END IF;
   END LOOP;
 END LOOP;
 IF NOT matched THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 END IF;

 IF actual IS NULL OR actual IS DISTINCT FROM p_expected
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 checked_at:=pg_catalog.clock_timestamp();
 IF checked_at<p_sample OR checked_at>=p_until
 THEN RAISE EXCEPTION 'Appointment finalization unavailable'; END IF;
 RETURN true;
EXCEPTION WHEN OTHERS THEN RAISE EXCEPTION 'Appointment finalization unavailable';
END;
$read_guard$;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz) FROM PUBLIC;


