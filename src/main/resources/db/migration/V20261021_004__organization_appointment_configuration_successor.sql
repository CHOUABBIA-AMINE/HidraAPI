-- HAPI-778 / Full811 repair. Preserve the already-applied003 migration.
-- PostgreSQL BEFORE INSERT triggers execute before ON CONFLICT arbitration.
-- Use distinct INSERT/UPDATE statements so revision2 reaches the UPDATE guard.
-- Keep every head invariant, owner fence, CAS, ACL, provenance and deferred check.
-- Only these three routine bodies change; no relation metadata or business grant.

CREATE OR REPLACE FUNCTION public.hidra_org_activate_appointment_binding(
 p_binding text,p_unit text,p_company text,p_expected bigint) RETURNS uuid
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,public SET timezone='UTC' AS $binding$
DECLARE previous public.hidra_org_appointment_binding_head%ROWTYPE; before_image jsonb;
 after_image jsonb; next_revision bigint; eid uuid:=pg_catalog.gen_random_uuid(); t timestamptz; g bigint; bytes bytea;
BEGIN
 PERFORM public.hidra_org_appointment_configuration_context(
  'public.hidra_org_activate_appointment_binding(text,text,text,bigint)');
 IF NOT public.hidra_org_appointment_exact_id(p_binding) OR
    NOT public.hidra_org_appointment_exact_id(p_unit) OR NOT public.hidra_org_appointment_exact_id(p_company) OR
    p_expected IS NULL OR p_expected<0 OR p_expected=9223372036854775807 THEN
  RAISE EXCEPTION 'Appointment configuration unavailable' USING ERRCODE='23514'; END IF;
 PERFORM public.hidra_org_appointment_configuration_locks();
 SELECT generation INTO STRICT g FROM public.hidra_party_require_organization_configuration_reference(p_company);
 t:=pg_catalog.clock_timestamp();
 IF NOT EXISTS(SELECT 1 FROM public.hidra_org_unit WHERE id=p_unit AND status='ACTIVE'
    AND valid_from<=t AND (valid_to IS NULL OR valid_to>t)) THEN
  RAISE EXCEPTION 'Appointment configuration unavailable' USING ERRCODE='23514'; END IF;
 SELECT * INTO previous FROM public.hidra_org_appointment_binding_head WHERE organization_unit_id=p_unit;
 IF COALESCE(previous.revision,0)<>p_expected OR (previous.binding_id IS NOT NULL AND previous.binding_id<>p_binding) OR
    EXISTS(SELECT 1 FROM public.hidra_org_appointment_binding_revision WHERE binding_id=p_binding AND organization_unit_id<>p_unit) THEN
  RAISE EXCEPTION 'Stale appointment configuration' USING ERRCODE='40001'; END IF;
 next_revision:=p_expected+1;
 IF previous.revision IS NOT NULL THEN
  SELECT pg_catalog.to_jsonb(r) INTO before_image FROM public.hidra_org_appointment_binding_revision r
   WHERE binding_id=previous.binding_id AND revision=previous.revision;
 END IF;
 INSERT INTO public.hidra_org_appointment_binding_revision VALUES
  (p_binding,next_revision,p_unit,p_company,g,t,session_user,pg_catalog.pg_backend_pid(),pg_catalog.txid_current());
 SELECT pg_catalog.to_jsonb(r) INTO after_image FROM public.hidra_org_appointment_binding_revision r
  WHERE binding_id=p_binding AND revision=next_revision;
 bytes:=pg_catalog.convert_to(pg_catalog.jsonb_build_object('kind','BINDING','scope',p_unit,'identity',p_binding,
  'priorRevision',p_expected,'revision',next_revision,'company',p_company,
  'before',before_image,'after',after_image)::text,'UTF8');
 INSERT INTO public.hidra_org_appointment_configuration_event VALUES
  (eid,'BINDING',p_unit,p_binding,p_expected,next_revision,p_company,before_image,after_image,bytes,
   pg_catalog.encode(pg_catalog.sha256(bytes),'hex'),session_user,t,pg_catalog.pg_backend_pid(),pg_catalog.txid_current());
 IF previous.revision IS NULL THEN
  INSERT INTO public.hidra_org_appointment_binding_head
   VALUES(p_unit,p_binding,next_revision,p_company,next_revision,eid,'BINDING');
 ELSE
  UPDATE public.hidra_org_appointment_binding_head SET revision=next_revision,company_party_id=p_company,
   generation=next_revision,last_event_id=eid
   WHERE organization_unit_id=p_unit AND binding_id=p_binding AND revision=p_expected;
  IF NOT FOUND THEN RAISE EXCEPTION 'Stale appointment configuration' USING ERRCODE='40001'; END IF;
 END IF;
 RETURN eid;
END;
$binding$;

CREATE OR REPLACE FUNCTION public.hidra_org_activate_appointment_policy(
 p_policy text,p_company text,p_expected bigint,p_policy_value jsonb) RETURNS uuid
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,public SET timezone='UTC' AS $activate_policy$
DECLARE previous public.hidra_org_appointment_policy_head%ROWTYPE; before_image jsonb;
 after_image jsonb; next_revision bigint; eid uuid:=pg_catalog.gen_random_uuid(); t timestamptz; g bigint; bytes bytea; canonical bytea;
BEGIN
 PERFORM public.hidra_org_appointment_configuration_context(
  'public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb)');
 IF NOT public.hidra_org_appointment_exact_id(p_policy) OR NOT public.hidra_org_appointment_exact_id(p_company) OR
    p_expected IS NULL OR p_expected<0 OR p_expected=9223372036854775807 OR
    p_policy_value IS NULL OR pg_catalog.octet_length(p_policy_value::text)>8192 OR
    NOT public.hidra_org_appointment_policy_valid(p_policy_value) THEN
  RAISE EXCEPTION 'Appointment configuration unavailable' USING ERRCODE='23514'; END IF;
 PERFORM public.hidra_org_appointment_configuration_locks();
 SELECT generation INTO STRICT g FROM public.hidra_party_require_organization_configuration_reference(p_company);
 SELECT * INTO previous FROM public.hidra_org_appointment_policy_head WHERE company_party_id=p_company;
 IF COALESCE(previous.revision,0)<>p_expected OR (previous.policy_id IS NOT NULL AND previous.policy_id<>p_policy) OR
    EXISTS(SELECT 1 FROM public.hidra_org_appointment_policy_revision WHERE policy_id=p_policy AND company_party_id<>p_company) THEN
  RAISE EXCEPTION 'Stale appointment configuration' USING ERRCODE='40001'; END IF;
 next_revision:=p_expected+1; t:=pg_catalog.clock_timestamp(); canonical:=pg_catalog.convert_to(p_policy_value::text,'UTF8');
 IF previous.revision IS NOT NULL THEN
  SELECT pg_catalog.to_jsonb(r) INTO before_image FROM public.hidra_org_appointment_policy_revision r
   WHERE policy_id=previous.policy_id AND revision=previous.revision;
 END IF;
 INSERT INTO public.hidra_org_appointment_policy_revision VALUES
  (p_policy,next_revision,p_company,p_policy_value,canonical,pg_catalog.encode(pg_catalog.sha256(canonical),'hex'),g,t,session_user,pg_catalog.pg_backend_pid(),pg_catalog.txid_current());
 SELECT pg_catalog.to_jsonb(r) INTO after_image FROM public.hidra_org_appointment_policy_revision r
  WHERE policy_id=p_policy AND revision=next_revision;
 bytes:=pg_catalog.convert_to(pg_catalog.jsonb_build_object('kind','POLICY','scope',p_company,'identity',p_policy,
  'priorRevision',p_expected,'revision',next_revision,'company',p_company,'before',before_image,'after',after_image)::text,'UTF8');
 INSERT INTO public.hidra_org_appointment_configuration_event VALUES
  (eid,'POLICY',p_company,p_policy,p_expected,next_revision,p_company,before_image,after_image,bytes,
   pg_catalog.encode(pg_catalog.sha256(bytes),'hex'),session_user,t,pg_catalog.pg_backend_pid(),pg_catalog.txid_current());
 IF previous.revision IS NULL THEN
  INSERT INTO public.hidra_org_appointment_policy_head
   VALUES(p_company,p_policy,next_revision,next_revision,eid,'POLICY');
 ELSE
  UPDATE public.hidra_org_appointment_policy_head SET revision=next_revision,generation=next_revision,last_event_id=eid
   WHERE company_party_id=p_company AND policy_id=p_policy AND revision=p_expected;
  IF NOT FOUND THEN RAISE EXCEPTION 'Stale appointment configuration' USING ERRCODE='40001'; END IF;
 END IF;
 RETURN eid;
END;
$activate_policy$;

CREATE OR REPLACE FUNCTION public.hidra_org_appointment_configuration_context(p_signature text) RETURNS void
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,public SET timezone='UTC' AS $context$
DECLARE rel text;
BEGIN
 IF NOT pg_catalog.has_function_privilege(session_user,p_signature,'EXECUTE') OR
    EXISTS(SELECT 1 FROM pg_proc p CROSS JOIN LATERAL pg_catalog.aclexplode(COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
           WHERE p.oid=p_signature::regprocedure AND a.grantee=0 AND a.privilege_type='EXECUTE') OR
    pg_catalog.current_setting('role') NOT IN ('none',session_user::text) OR
    pg_catalog.current_setting('transaction_isolation')<>'read committed' OR
    pg_catalog.current_setting('transaction_read_only')<>'off' OR pg_catalog.current_setting('session_replication_role')<>'origin' OR
    pg_catalog.has_schema_privilege(session_user,'public','CREATE') OR pg_catalog.has_schema_privilege(session_user,'pg_catalog','CREATE') OR
    EXISTS(SELECT 1 FROM pg_roles r WHERE (r.rolname=session_user OR pg_catalog.pg_has_role(session_user,r.oid,'MEMBER')) AND
      (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication OR
       r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files',
                    'pg_read_all_data','pg_write_all_data','pg_signal_backend','pg_checkpoint'))) THEN
  RAISE EXCEPTION 'Appointment configuration unavailable' USING ERRCODE='23514';
 END IF;
 FOREACH rel IN ARRAY ARRAY['hidra_org_appointment_binding_revision','hidra_org_appointment_binding_head',
   'hidra_org_appointment_policy_revision','hidra_org_appointment_policy_head','hidra_org_appointment_configuration_event'] LOOP
  IF pg_catalog.has_table_privilege(session_user,'public.'||rel,'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER') OR
     pg_catalog.has_any_column_privilege(session_user,'public.'||rel,'INSERT,UPDATE,REFERENCES') OR
     EXISTS(SELECT 1 FROM pg_class c WHERE c.oid=('public.'||rel)::regclass AND
       (c.relkind<>'r' OR c.relpersistence<>'p' OR c.relispartition OR c.relrowsecurity OR c.relforcerowsecurity OR
        pg_catalog.pg_has_role(session_user,c.relowner,'MEMBER'))) OR
     EXISTS(SELECT 1 FROM pg_inherits WHERE inhrelid=('public.'||rel)::regclass OR inhparent=('public.'||rel)::regclass) THEN
   RAISE EXCEPTION 'Appointment configuration unavailable' USING ERRCODE='23514';
  END IF;
 END LOOP;
 IF NOT (SELECT count(*)=8 AND pg_catalog.bool_and(
    pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(p.prosrc,'UTF8')),'hex')=expected.sha AND
    CASE WHEN expected.signature IN ('public.hidra_org_appointment_exact_id(text)',
      'public.hidra_org_appointment_policy_valid(jsonb)','public.hidra_org_appointment_configuration_immutable()')
    THEN NOT p.prosecdef AND p.proconfig=ARRAY['search_path=pg_catalog']
    ELSE p.prosecdef AND pg_catalog.cardinality(p.proconfig)=2 AND
      'search_path=pg_catalog, public'=ANY(p.proconfig) AND
      EXISTS(SELECT 1 FROM pg_catalog.unnest(p.proconfig) v WHERE pg_catalog.lower(v)='timezone=utc') END AND
    NOT EXISTS(SELECT 1 FROM pg_catalog.aclexplode(COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
      WHERE a.grantee=0 AND a.privilege_type='EXECUTE'))
   FROM (VALUES
    ('public.hidra_org_appointment_exact_id(text)','ef75cca90db3f80338e710d62d8db04ed4b8e9d000f3e78b878058592761e5d2'),
    ('public.hidra_org_appointment_policy_valid(jsonb)','62e41666f14398fd36f19184ff7e77ea094f8110fb67b306a7b2f609dc3fffda'),
    ('public.hidra_org_appointment_configuration_immutable()','77db52e487758bad1904239e7d83f166a3e1f6089fd2ac0be07b1f5d90e8e713'),
    ('public.hidra_org_appointment_configuration_locks()','3473858642398f81ad5bbbd876ca78b4f0ba6a18167a145182546505d5d71383'),
    ('public.hidra_org_activate_appointment_binding(text,text,text,bigint)','af95d48f202326b5d48594c8e6f87cf93521ab8c3194faa7c2f467cb46dcfe1b'),
    ('public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb)','e2741065f63d483473cbb12d9ea8a81bfd47931321acd04a7107adfda9cfe921'),
    ('public.hidra_org_appointment_configuration_complete()','7608688dcabd3fdafb256155e5e739d2e6146226fe5530138ea9915dbedb34d7'),
    ('public.hidra_org_appointment_head_transition()','cd1dd4aa3bc5a258109189ada8c68b2d1ed227f06d0e631212c00a6e4ccdb553')
   ) expected(signature,sha) JOIN pg_proc p ON p.oid=expected.signature::regprocedure) OR
   NOT (SELECT count(*)=10 AND pg_catalog.bool_and(tgenabled='O' AND tgqual IS NULL AND tgnargs=0 AND tgparentid=0)
    FROM pg_trigger WHERE NOT tgisinternal AND tgrelid=ANY(ARRAY[
      'public.hidra_org_appointment_binding_revision'::regclass,'public.hidra_org_appointment_binding_head'::regclass,
      'public.hidra_org_appointment_policy_revision'::regclass,'public.hidra_org_appointment_policy_head'::regclass,
      'public.hidra_org_appointment_configuration_event'::regclass])) THEN
  RAISE EXCEPTION 'Appointment configuration unavailable' USING ERRCODE='23514';
 END IF;
END;
$context$;
