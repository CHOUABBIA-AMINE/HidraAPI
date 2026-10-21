-- HAPI-778 / protected-storage package: explicit configuration persistence.
-- Development-only additive migration. No logins, default policy, role grants,
-- appointment adoption/backfill, active lifecycle writer or HTTP administration.
-- The existing physical Organization unit is public.hidra_org_unit.
CREATE FUNCTION public.hidra_org_appointment_exact_id(p text) RETURNS boolean
LANGUAGE sql IMMUTABLE PARALLEL SAFE SET search_path=pg_catalog AS $exact$
 SELECT p IS NOT NULL AND pg_catalog.char_length(p) BETWEEN 1 AND 80
   AND NOT EXISTS(SELECT 1 FROM pg_catalog.generate_series(1,pg_catalog.char_length(p)) n
      WHERE pg_catalog.ascii(pg_catalog.substr(p,n,1)) BETWEEN 0 AND 31 OR pg_catalog.ascii(pg_catalog.substr(p,n,1)) BETWEEN 127 AND 159)
   AND pg_catalog.ascii(pg_catalog.left(p,1))<>ALL(ARRAY[32,160,5760,8192,8193,8194,8195,8196,8197,8198,8199,8200,8201,8202,8232,8233,8239,8287,12288])
   AND pg_catalog.ascii(pg_catalog.right(p,1))<>ALL(ARRAY[32,160,5760,8192,8193,8194,8195,8196,8197,8198,8199,8200,8201,8202,8232,8233,8239,8287,12288])
$exact$;
CREATE FUNCTION public.hidra_org_appointment_policy_valid(p jsonb) RETURNS boolean
LANGUAGE plpgsql IMMUTABLE PARALLEL SAFE SET search_path=pg_catalog AS $policy$
DECLARE a text; b text; types text[]:=ARRAY['PRIMARY','SECONDARY','TEMPORARY','ACTING'];
BEGIN
 IF p IS NULL OR pg_catalog.jsonb_typeof(p)<>'object' OR
    (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p))<>3 OR
    NOT(p ?& ARRAY['overlaps','maxConcurrentPrimary','positionCapacity']) OR
    pg_catalog.jsonb_typeof(p->'maxConcurrentPrimary')<>'number' OR
    (p->>'maxConcurrentPrimary')!~'^[1-9][0-9]{0,9}$' OR
    (p->>'maxConcurrentPrimary')::numeric>2147483647 OR
    pg_catalog.jsonb_typeof(p->'positionCapacity')<>'string' OR
    p->>'positionCapacity' NOT IN ('UNRESTRICTED','SINGLE_HOLDER') OR
    pg_catalog.jsonb_typeof(p->'overlaps')<>'object' THEN RETURN false; END IF;
 IF (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p->'overlaps'))<>4 THEN RETURN false; END IF;
 FOREACH a IN ARRAY types LOOP
  IF pg_catalog.jsonb_typeof(p->'overlaps'->a) IS DISTINCT FROM 'object' OR
     (SELECT count(*) FROM pg_catalog.jsonb_object_keys(p->'overlaps'->a))<>4 THEN RETURN false; END IF;
  FOREACH b IN ARRAY types LOOP
   IF pg_catalog.jsonb_typeof(p->'overlaps'->a->b) IS DISTINCT FROM 'boolean' OR
      p->'overlaps'->a->b IS DISTINCT FROM p->'overlaps'->b->a THEN RETURN false; END IF;
  END LOOP;
 END LOOP;
 RETURN true;
EXCEPTION WHEN invalid_text_representation OR numeric_value_out_of_range OR invalid_parameter_value THEN RETURN false;
END;
$policy$;
CREATE TABLE public.hidra_org_appointment_binding_revision (
 binding_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(binding_id)),
 revision bigint NOT NULL CHECK(revision>0),
 organization_unit_id varchar(80) NOT NULL REFERENCES public.hidra_org_unit(id),
 company_party_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(company_party_id)),
 party_generation bigint NOT NULL CHECK(party_generation>0),
 recorded_at timestamptz NOT NULL,
 database_admin name NOT NULL,
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 PRIMARY KEY(binding_id,revision),
 UNIQUE(organization_unit_id,revision),
 UNIQUE(organization_unit_id,binding_id,revision,company_party_id)
);
CREATE TABLE public.hidra_org_appointment_policy_revision (
 policy_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(policy_id)),
 revision bigint NOT NULL CHECK(revision>0),
 company_party_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(company_party_id)),
 policy jsonb NOT NULL CHECK(public.hidra_org_appointment_policy_valid(policy)),
 canonical_payload bytea NOT NULL,
 sha256 varchar(64) NOT NULL CHECK(sha256=pg_catalog.encode(pg_catalog.sha256(canonical_payload),'hex')),
 party_generation bigint NOT NULL CHECK(party_generation>0),
 recorded_at timestamptz NOT NULL,
 database_admin name NOT NULL,
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 PRIMARY KEY(policy_id,revision),
 UNIQUE(company_party_id,revision),
 UNIQUE(company_party_id,policy_id,revision),
 CHECK(canonical_payload=pg_catalog.convert_to(policy::text,'UTF8'))
);
CREATE TABLE public.hidra_org_appointment_configuration_event (
 event_id uuid PRIMARY KEY,
 kind varchar(16) NOT NULL CHECK(kind IN ('BINDING','POLICY')),
 scope_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(scope_id)),
 identity_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(identity_id)),
 prior_revision bigint NOT NULL CHECK(prior_revision>=0 AND prior_revision<9223372036854775807),
 revision bigint NOT NULL CHECK(revision=prior_revision+1),
 company_party_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(company_party_id)),
 before_payload jsonb,
 after_payload jsonb NOT NULL,
 canonical_payload bytea NOT NULL,
 sha256 varchar(64) NOT NULL CHECK(sha256=pg_catalog.encode(pg_catalog.sha256(canonical_payload),'hex')),
 database_admin name NOT NULL,
 recorded_at timestamptz NOT NULL,
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 UNIQUE(kind,scope_id,revision),
 UNIQUE(kind,scope_id,identity_id,revision,event_id),
 CHECK((prior_revision=0)=(before_payload IS NULL)),
 CHECK(canonical_payload=pg_catalog.convert_to(pg_catalog.jsonb_build_object('kind',kind,'scope',scope_id,
    'identity',identity_id,'priorRevision',prior_revision,'revision',revision,
    'company',company_party_id,'before',before_payload,'after',after_payload)::text,'UTF8'))
);
CREATE TABLE public.hidra_org_appointment_binding_head (
 organization_unit_id varchar(80) PRIMARY KEY REFERENCES public.hidra_org_unit(id),
 binding_id varchar(80) NOT NULL,
 revision bigint NOT NULL CHECK(revision>0),
 company_party_id varchar(80) NOT NULL,
 generation bigint NOT NULL CHECK(generation=revision),
 last_event_id uuid NOT NULL,
 event_kind varchar(16) NOT NULL DEFAULT 'BINDING' CHECK(event_kind='BINDING'),
 FOREIGN KEY(organization_unit_id,binding_id,revision,company_party_id)
  REFERENCES public.hidra_org_appointment_binding_revision(organization_unit_id,binding_id,revision,company_party_id),
 FOREIGN KEY(event_kind,organization_unit_id,binding_id,revision,last_event_id)
  REFERENCES public.hidra_org_appointment_configuration_event(kind,scope_id,identity_id,revision,event_id)
);
CREATE TABLE public.hidra_org_appointment_policy_head (
 company_party_id varchar(80) PRIMARY KEY CHECK(public.hidra_org_appointment_exact_id(company_party_id)),
 policy_id varchar(80) NOT NULL,
 revision bigint NOT NULL CHECK(revision>0),
 generation bigint NOT NULL CHECK(generation=revision),
 last_event_id uuid NOT NULL,
 event_kind varchar(16) NOT NULL DEFAULT 'POLICY' CHECK(event_kind='POLICY'),
 FOREIGN KEY(company_party_id,policy_id,revision)
  REFERENCES public.hidra_org_appointment_policy_revision(company_party_id,policy_id,revision),
 FOREIGN KEY(event_kind,company_party_id,policy_id,revision,last_event_id)
  REFERENCES public.hidra_org_appointment_configuration_event(kind,scope_id,identity_id,revision,event_id)
);
-- Immutable ledger storage is deliberately ungranted. Publisher DML/final guard
-- activation belongs to the subsequent native-issuer-backed adapter cutover.
CREATE TABLE public.hidra_org_appointment_lock_key (
 kind varchar(16) NOT NULL CHECK(kind IN ('EMPLOYEE','OCCUPANCY','REQUEST')),
 key_one varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(key_one)),
 key_two varchar(80) NOT NULL,
 key_three varchar(80) NOT NULL,
 PRIMARY KEY(kind,key_one,key_two,key_three),
 CHECK((kind='EMPLOYEE' AND key_two='' AND key_three='') OR
       (kind='OCCUPANCY' AND public.hidra_org_appointment_exact_id(key_two) AND key_three='') OR
       (kind='REQUEST' AND key_two ~ '^[0-9a-f]{64}$' AND public.hidra_org_appointment_exact_id(key_three)))
);
CREATE TABLE public.hidra_org_appointment_event (
 event_id uuid PRIMARY KEY,
 operation varchar(16) NOT NULL CHECK(operation IN ('CREATE','END','TRANSFER','CORRECT')),
 assignment_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(assignment_id)),
 successor_id varchar(80) CHECK(successor_id IS NULL OR public.hidra_org_appointment_exact_id(successor_id)),
 employee_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(employee_id)),
 company_party_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(company_party_id)),
 policy_id varchar(80) NOT NULL,
 policy_revision bigint NOT NULL CHECK(policy_revision>0),
 canonical_payload bytea NOT NULL CHECK(pg_catalog.octet_length(canonical_payload) BETWEEN 16 AND 1048576),
 command_sha256 varchar(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),
 actor_binding_id uuid NOT NULL,
 actor_session_id uuid NOT NULL,
 actor_user_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(actor_user_id)),
 sampled_at timestamptz NOT NULL,
 valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at),
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 FOREIGN KEY(company_party_id,policy_id,policy_revision)
  REFERENCES public.hidra_org_appointment_policy_revision(company_party_id,policy_id,revision),
 CHECK((operation='TRANSFER')=(successor_id IS NOT NULL)),
 CHECK(successor_id IS NULL OR successor_id<>assignment_id)
);
CREATE INDEX appointment_event_assignment_history ON public.hidra_org_appointment_event(assignment_id,sampled_at,event_id);
CREATE INDEX appointment_event_employee_history ON public.hidra_org_appointment_event(employee_id,sampled_at,event_id);
CREATE INDEX appointment_event_successor_history ON public.hidra_org_appointment_event(successor_id,sampled_at,event_id) WHERE successor_id IS NOT NULL;
CREATE TABLE public.hidra_org_appointment_receipt (
 event_id uuid PRIMARY KEY REFERENCES public.hidra_org_appointment_event(event_id),
 actor_user_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(actor_user_id)),
 scope_key varchar(64) NOT NULL CHECK(scope_key ~ '^[0-9a-f]{64}$'),
 request_id varchar(80) NOT NULL CHECK(public.hidra_org_appointment_exact_id(request_id)),
 command_sha256 varchar(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),
 canonical_result bytea NOT NULL CHECK(pg_catalog.octet_length(canonical_result) BETWEEN 16 AND 1048576),
 guarded_at timestamptz NOT NULL,
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 UNIQUE(actor_user_id,scope_key,request_id)
);
CREATE FUNCTION public.hidra_org_appointment_configuration_immutable() RETURNS trigger
LANGUAGE plpgsql SET search_path=pg_catalog AS $immutable$
BEGIN RAISE EXCEPTION 'Immutable appointment configuration' USING ERRCODE='23514'; END;
$immutable$;
CREATE TRIGGER appointment_binding_revision_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
 ON public.hidra_org_appointment_binding_revision FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE TRIGGER appointment_policy_revision_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
 ON public.hidra_org_appointment_policy_revision FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE TRIGGER appointment_configuration_event_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
 ON public.hidra_org_appointment_configuration_event FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE TRIGGER appointment_binding_head_retain BEFORE DELETE OR TRUNCATE
 ON public.hidra_org_appointment_binding_head FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE TRIGGER appointment_policy_head_retain BEFORE DELETE OR TRUNCATE
 ON public.hidra_org_appointment_policy_head FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE TRIGGER appointment_lock_key_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
 ON public.hidra_org_appointment_lock_key FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE TRIGGER appointment_event_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
 ON public.hidra_org_appointment_event FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE TRIGGER appointment_receipt_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
 ON public.hidra_org_appointment_receipt FOR EACH STATEMENT
 EXECUTE FUNCTION public.hidra_org_appointment_configuration_immutable();
CREATE FUNCTION public.hidra_org_appointment_configuration_context(p_signature text) RETURNS void
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
    ('public.hidra_org_activate_appointment_binding(text,text,text,bigint)','aa1d61a07175006e3a68a648305ee3b993d913056947024dbbb1aa27ca69ec19'),
    ('public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb)','fbc3ac80ec8785e07dc2262e54634bf5ccefb9d631e11fe70dd6d2d0bc2cb11a'),
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
CREATE FUNCTION public.hidra_org_appointment_configuration_locks() RETURNS void
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,public SET timezone='UTC' AS $locks$
BEGIN
 LOCK TABLE public.hidra_identity_attribute_definition IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_authorization_policy IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_authorization_policy_rule IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_authorization_policy_version IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_external_group_mapping IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_external_identity IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_external_permission_mapping IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_external_role_mapping IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_group IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_group_role_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_local_credential IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_login_session IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_operation_binding IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_permission IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_provider IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_role IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_role_permission_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_subject_security_attribute IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_user IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_user_group_membership IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_user_permission_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_user_role_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_external_mapping_generation IN ACCESS SHARE MODE NOWAIT;
 LOCK TABLE public.hidra_identity_bound_source_claim_evidence IN SHARE MODE NOWAIT;
 LOCK TABLE public.hidra_party_party IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_party_eligibility_generation IN ACCESS SHARE MODE NOWAIT;
 LOCK TABLE public.hidra_org_employee IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_unit IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_position IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_binding_revision IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_binding_head IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_policy_revision IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_policy_head IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_configuration_event IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_lock_key IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_employee_assignment IN SHARE ROW EXCLUSIVE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_event IN ACCESS SHARE MODE NOWAIT;
 LOCK TABLE public.hidra_org_appointment_receipt IN ACCESS SHARE MODE NOWAIT;
END;
$locks$;
CREATE FUNCTION public.hidra_org_activate_appointment_binding(
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
 INSERT INTO public.hidra_org_appointment_binding_head VALUES(p_unit,p_binding,next_revision,p_company,next_revision,eid,'BINDING')
 ON CONFLICT(organization_unit_id) DO UPDATE SET revision=EXCLUDED.revision,company_party_id=EXCLUDED.company_party_id,
  generation=EXCLUDED.generation,last_event_id=EXCLUDED.last_event_id;
 RETURN eid;
END;
$binding$;
CREATE FUNCTION public.hidra_org_activate_appointment_policy(
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
 INSERT INTO public.hidra_org_appointment_policy_head VALUES(p_company,p_policy,next_revision,next_revision,eid,'POLICY')
 ON CONFLICT(company_party_id) DO UPDATE SET revision=EXCLUDED.revision,generation=EXCLUDED.generation,last_event_id=EXCLUDED.last_event_id;
 RETURN eid;
END;
$activate_policy$;
CREATE FUNCTION public.hidra_org_appointment_configuration_complete() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,public SET timezone='UTC' AS $complete$
DECLARE e public.hidra_org_appointment_configuration_event%ROWTYPE; image jsonb; prior_image jsonb; linked boolean;
BEGIN
 IF TG_TABLE_NAME='hidra_org_appointment_configuration_event' THEN
  e:=NEW;
 ELSE
  SELECT * INTO e FROM public.hidra_org_appointment_configuration_event
   WHERE kind=CASE WHEN TG_TABLE_NAME LIKE '%binding%' THEN 'BINDING' ELSE 'POLICY' END
    AND identity_id=CASE WHEN TG_TABLE_NAME LIKE '%binding%' THEN pg_catalog.to_jsonb(NEW)->>'binding_id' ELSE pg_catalog.to_jsonb(NEW)->>'policy_id' END
    AND revision=NEW.revision;
  IF NOT FOUND THEN RAISE EXCEPTION 'Incomplete appointment configuration' USING ERRCODE='23514'; END IF;
 END IF;
 PERFORM public.hidra_org_appointment_configuration_context(CASE WHEN e.kind='BINDING' THEN
  'public.hidra_org_activate_appointment_binding(text,text,text,bigint)' ELSE
  'public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb)' END);
 IF e.kind='BINDING' THEN
  SELECT pg_catalog.to_jsonb(r) INTO image FROM public.hidra_org_appointment_binding_revision r
   WHERE binding_id=e.identity_id AND revision=e.revision AND organization_unit_id=e.scope_id AND company_party_id=e.company_party_id;
  SELECT pg_catalog.to_jsonb(r) INTO prior_image FROM public.hidra_org_appointment_binding_revision r
   WHERE binding_id=e.identity_id AND revision=e.prior_revision AND organization_unit_id=e.scope_id;
  SELECT EXISTS(SELECT 1 FROM public.hidra_org_appointment_binding_head h
    WHERE h.organization_unit_id=e.scope_id AND h.binding_id=e.identity_id AND h.revision>=e.revision)
    INTO linked;
 ELSE
  SELECT pg_catalog.to_jsonb(r) INTO image FROM public.hidra_org_appointment_policy_revision r
   WHERE policy_id=e.identity_id AND revision=e.revision AND company_party_id=e.scope_id AND company_party_id=e.company_party_id;
  SELECT pg_catalog.to_jsonb(r) INTO prior_image FROM public.hidra_org_appointment_policy_revision r
   WHERE policy_id=e.identity_id AND revision=e.prior_revision AND company_party_id=e.scope_id;
  SELECT EXISTS(SELECT 1 FROM public.hidra_org_appointment_policy_head h
    WHERE h.company_party_id=e.scope_id AND h.policy_id=e.identity_id AND h.revision>=e.revision)
    INTO linked;
 END IF;
 IF image IS NULL OR image IS DISTINCT FROM e.after_payload OR
    prior_image IS DISTINCT FROM e.before_payload OR NOT linked OR e.backend_pid<>pg_catalog.pg_backend_pid() OR
    e.transaction_id<>pg_catalog.txid_current() OR e.database_admin<>session_user OR
    image->>'database_admin'<>session_user::text OR (image->>'transaction_id')::bigint<>e.transaction_id OR
    (image->>'backend_pid')::integer<>e.backend_pid OR (image->>'recorded_at')::timestamptz<>e.recorded_at THEN
  RAISE EXCEPTION 'Incomplete appointment configuration' USING ERRCODE='23514'; END IF;
 RETURN NULL;
END;
$complete$;
CREATE CONSTRAINT TRIGGER appointment_binding_revision_complete AFTER INSERT
 ON public.hidra_org_appointment_binding_revision DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.hidra_org_appointment_configuration_complete();
CREATE CONSTRAINT TRIGGER appointment_policy_revision_complete AFTER INSERT
 ON public.hidra_org_appointment_policy_revision DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.hidra_org_appointment_configuration_complete();
CREATE CONSTRAINT TRIGGER appointment_configuration_event_complete AFTER INSERT
 ON public.hidra_org_appointment_configuration_event DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.hidra_org_appointment_configuration_complete();
CREATE FUNCTION public.hidra_org_appointment_head_transition() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,public SET timezone='UTC' AS $head$
DECLARE k text; scope text; ident text;
BEGIN
 k:=CASE WHEN TG_TABLE_NAME LIKE '%binding%' THEN 'BINDING' ELSE 'POLICY' END;
 scope:=CASE WHEN k='BINDING' THEN pg_catalog.to_jsonb(NEW)->>'organization_unit_id' ELSE pg_catalog.to_jsonb(NEW)->>'company_party_id' END;
 ident:=CASE WHEN k='BINDING' THEN pg_catalog.to_jsonb(NEW)->>'binding_id' ELSE pg_catalog.to_jsonb(NEW)->>'policy_id' END;
 IF TG_OP='INSERT' AND NEW.revision<>1 OR TG_OP='UPDATE' AND
   (OLD.revision=9223372036854775807 OR NEW.revision<>OLD.revision+1 OR NEW.generation<>OLD.generation+1 OR
    (pg_catalog.to_jsonb(NEW)-CASE WHEN k='BINDING' THEN ARRAY['revision','generation','last_event_id','company_party_id']
      ELSE ARRAY['revision','generation','last_event_id'] END) IS DISTINCT FROM
    (pg_catalog.to_jsonb(OLD)-CASE WHEN k='BINDING' THEN ARRAY['revision','generation','last_event_id','company_party_id']
      ELSE ARRAY['revision','generation','last_event_id'] END)) OR
   NOT EXISTS(SELECT 1 FROM public.hidra_org_appointment_configuration_event e WHERE e.event_id=NEW.last_event_id
     AND e.kind=k AND e.scope_id=scope AND e.identity_id=ident AND e.revision=NEW.revision
     AND e.company_party_id=NEW.company_party_id AND e.prior_revision=CASE WHEN TG_OP='INSERT' THEN 0 ELSE OLD.revision END
     AND e.backend_pid=pg_catalog.pg_backend_pid() AND e.transaction_id=pg_catalog.txid_current() AND e.database_admin=session_user) THEN
  RAISE EXCEPTION 'Invalid appointment configuration head' USING ERRCODE='23514'; END IF;
 RETURN NEW;
END;
$head$;
CREATE TRIGGER appointment_binding_head_transition BEFORE INSERT OR UPDATE ON public.hidra_org_appointment_binding_head
 FOR EACH ROW EXECUTE FUNCTION public.hidra_org_appointment_head_transition();
CREATE TRIGGER appointment_policy_head_transition BEFORE INSERT OR UPDATE ON public.hidra_org_appointment_policy_head
 FOR EACH ROW EXECUTE FUNCTION public.hidra_org_appointment_head_transition();
-- Fixed namespace and explicit future grants only. SELECT/UPDATE needed for
-- owner locks must be reviewed for the publisher when its adapter is implemented.
REVOKE ALL ON public.hidra_org_appointment_binding_revision,public.hidra_org_appointment_binding_head,
 public.hidra_org_appointment_policy_revision,public.hidra_org_appointment_policy_head,
 public.hidra_org_appointment_configuration_event,public.hidra_org_appointment_lock_key,
 public.hidra_org_appointment_event,public.hidra_org_appointment_receipt FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_exact_id(text),
 public.hidra_org_appointment_policy_valid(jsonb),
 public.hidra_org_appointment_configuration_immutable(),
 public.hidra_org_appointment_configuration_context(text),
 public.hidra_org_appointment_configuration_locks(),
 public.hidra_org_appointment_configuration_complete(),
 public.hidra_org_appointment_head_transition(),
 public.hidra_org_activate_appointment_binding(text,text,text,bigint),
 public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb) FROM PUBLIC;
