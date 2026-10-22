-- HAPI-778: private Identity commit-proof verifier. No key, login, grant or writer activation.
CREATE SCHEMA hidra_identity_crypto;
REVOKE ALL ON SCHEMA hidra_identity_crypto FROM PUBLIC;
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_catalog.pg_extension WHERE extname='pgcrypto') THEN
        IF NOT EXISTS (
            SELECT 1 FROM pg_catalog.pg_extension e JOIN pg_catalog.pg_namespace n ON n.oid=e.extnamespace
            WHERE e.extname='pgcrypto' AND n.nspname='hidra_identity_crypto'
              AND e.extowner=(SELECT oid FROM pg_catalog.pg_roles WHERE rolname=current_user)
              AND n.nspowner=e.extowner) THEN
            RAISE EXCEPTION 'Unsafe pgcrypto installation';
        END IF;
    ELSE
        CREATE EXTENSION pgcrypto WITH SCHEMA hidra_identity_crypto;
    END IF;
    IF NOT EXISTS (
        SELECT 1 FROM pg_catalog.pg_proc p JOIN pg_catalog.pg_depend d
          ON d.classid='pg_catalog.pg_proc'::regclass AND d.objid=p.oid AND d.deptype='e'
        JOIN pg_catalog.pg_extension e ON e.oid=d.refobjid
        WHERE p.oid='hidra_identity_crypto.hmac(bytea,bytea,text)'::regprocedure
          AND e.extname='pgcrypto' AND p.prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='c')
          AND p.prorettype='bytea'::regtype AND p.proisstrict) THEN
        RAISE EXCEPTION 'Unsafe pgcrypto function';
    END IF;
END $$;
REVOKE ALL ON ALL FUNCTIONS IN SCHEMA hidra_identity_crypto FROM PUBLIC;

CREATE TABLE public.hidra_identity_org_commit_key_event (
    revision bigint PRIMARY KEY CHECK (revision>0),
    key_id varchar(64) NOT NULL,
    audience uuid NOT NULL,
    publisher name NOT NULL,
    active boolean NOT NULL,
    fingerprint bytea CHECK (fingerprint IS NULL OR octet_length(fingerprint)=32),
    administrator name NOT NULL,
    recorded_at timestamptz NOT NULL,
    CHECK (active=(fingerprint IS NOT NULL))
);
CREATE TABLE public.hidra_identity_org_commit_key_head (
    singleton boolean PRIMARY KEY DEFAULT true CHECK (singleton),
    key_id varchar(64) NOT NULL CHECK (key_id ~ '^[a-zA-Z0-9][a-zA-Z0-9._-]{0,63}$'),
    revision bigint NOT NULL UNIQUE REFERENCES public.hidra_identity_org_commit_key_event(revision),
    audience uuid NOT NULL,
    publisher name NOT NULL,
    active boolean NOT NULL,
    key_bytes bytea,
    CHECK ((active AND octet_length(key_bytes)=32) OR (NOT active AND key_bytes IS NULL)),
    CHECK (NOT active OR key_bytes IS NOT NULL)
);
CREATE TABLE public.hidra_identity_org_commit_proof_use (
    revision bigint NOT NULL,
    nonce bytea NOT NULL CHECK (octet_length(nonce)=32),
    key_id varchar(64) NOT NULL,
    backend_pid integer NOT NULL CHECK (backend_pid>0),
    transaction_id bigint NOT NULL CHECK (transaction_id>0),
    event_id uuid NOT NULL UNIQUE,
    command_sha256 bytea NOT NULL CHECK (octet_length(command_sha256)=32),
    payload_sha256 bytea NOT NULL CHECK (octet_length(payload_sha256)=32),
    consumed_at timestamptz NOT NULL,
    PRIMARY KEY (revision,nonce)
);
REVOKE ALL ON public.hidra_identity_org_commit_key_event,
    public.hidra_identity_org_commit_key_head, public.hidra_identity_org_commit_proof_use FROM PUBLIC;

CREATE FUNCTION public.hidra_identity_org_commit_immutable() RETURNS trigger
LANGUAGE plpgsql SET search_path=pg_catalog AS $$
BEGIN RAISE EXCEPTION 'Immutable Identity commit-proof state'; END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_immutable() FROM PUBLIC;
CREATE TRIGGER hidra_identity_org_key_event_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
ON public.hidra_identity_org_commit_key_event FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_org_commit_immutable();
CREATE TRIGGER hidra_identity_org_proof_use_immutable BEFORE UPDATE OR DELETE OR TRUNCATE
ON public.hidra_identity_org_commit_proof_use FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_org_commit_immutable();
CREATE TRIGGER hidra_identity_org_key_head_no_reset BEFORE DELETE OR TRUNCATE
ON public.hidra_identity_org_commit_key_head FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_org_commit_immutable();

-- The trusted migration/function/key administrators are outside the ordinary application boundary.
CREATE FUNCTION public.hidra_identity_org_commit_catalog() RETURNS void
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $$
DECLARE own oid; rel oid; r record; fn oid;
BEGIN
    SELECT proowner INTO own FROM pg_catalog.pg_proc
      WHERE oid='public.hidra_identity_org_commit_catalog()'::regprocedure;
    IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_namespace WHERE nspname='hidra_identity_crypto' AND nspowner=own)
      OR EXISTS (SELECT 1 FROM pg_catalog.pg_namespace n,
                    LATERAL pg_catalog.aclexplode(coalesce(n.nspacl,pg_catalog.acldefault('n',n.nspowner))) a
                 WHERE n.nspname='hidra_identity_crypto' AND a.grantee<>own)
      OR NOT EXISTS (SELECT 1 FROM pg_catalog.pg_extension e JOIN pg_catalog.pg_namespace n ON n.oid=e.extnamespace
                     WHERE e.extname='pgcrypto' AND e.extowner=own AND n.nspname='hidra_identity_crypto')
      OR NOT EXISTS (SELECT 1 FROM pg_catalog.pg_proc p JOIN pg_catalog.pg_depend d
                      ON d.classid='pg_catalog.pg_proc'::regclass AND d.objid=p.oid AND d.deptype='e'
                     JOIN pg_catalog.pg_extension e ON e.oid=d.refobjid
                     WHERE p.oid='hidra_identity_crypto.hmac(bytea,bytea,text)'::regprocedure
                       AND e.extname='pgcrypto' AND p.proowner=own AND p.prorettype='bytea'::regtype
                       AND p.prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='c') AND p.proisstrict)
      THEN RAISE EXCEPTION 'Unsafe Identity proof catalog'; END IF;
    FOREACH rel IN ARRAY ARRAY['public.hidra_identity_org_commit_key_event'::regclass::oid,
        'public.hidra_identity_org_commit_key_head'::regclass::oid,
        'public.hidra_identity_org_commit_proof_use'::regclass::oid] LOOP
        IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_class WHERE oid=rel AND relowner=own AND relkind='r'
                         AND relpersistence='p' AND NOT relrowsecurity AND NOT relforcerowsecurity)
          OR EXISTS (SELECT 1 FROM pg_catalog.pg_class c,
                         LATERAL pg_catalog.aclexplode(coalesce(c.relacl,pg_catalog.acldefault('r',c.relowner))) a
                      WHERE c.oid=rel AND a.grantee<>own)
          OR EXISTS (SELECT 1 FROM pg_catalog.pg_attribute a,
                         LATERAL pg_catalog.aclexplode(a.attacl) acl WHERE a.attrelid=rel AND acl.grantee<>own)
          OR (SELECT count(*) FROM pg_catalog.pg_trigger WHERE tgrelid=rel AND NOT tgisinternal)<>1
          OR EXISTS (SELECT 1 FROM pg_catalog.pg_trigger WHERE tgrelid=rel AND NOT tgisinternal
                      AND (tgenabled<>'O' OR tgfoid<>'public.hidra_identity_org_commit_immutable()'::regprocedure
                        OR tgnargs<>0 OR tgqual IS NOT NULL OR tgconstraint<>0
                        OR tgtype<>CASE WHEN rel='public.hidra_identity_org_commit_key_head'::regclass THEN 42 ELSE 58 END
                        OR tgname<>CASE rel
                          WHEN 'public.hidra_identity_org_commit_key_head'::regclass THEN 'hidra_identity_org_key_head_no_reset'
                          WHEN 'public.hidra_identity_org_commit_key_event'::regclass THEN 'hidra_identity_org_key_event_immutable'
                          ELSE 'hidra_identity_org_proof_use_immutable' END))
          THEN RAISE EXCEPTION 'Unsafe Identity proof catalog'; END IF;
    END LOOP;
    FOR r IN SELECT * FROM pg_catalog.pg_proc
       WHERE pronamespace='public'::regnamespace AND proname LIKE 'hidra_identity_org_commit_%' LOOP
        IF r.proowner<>own OR r.proconfig IS DISTINCT FROM ARRAY['search_path=pg_catalog']
          OR r.prosecdef IS DISTINCT FROM (r.proname IN (
              'hidra_identity_org_commit_catalog','hidra_identity_org_commit_caller',
              'hidra_identity_org_commit_admin','hidra_identity_org_commit_locks',
              'hidra_identity_org_commit_key_context','hidra_identity_org_commit_verify_consume'))
          OR EXISTS (SELECT 1 FROM pg_catalog.aclexplode(coalesce(r.proacl,pg_catalog.acldefault('f',r.proowner))) a
                      WHERE a.grantee=0 AND a.privilege_type='EXECUTE')
          THEN RAISE EXCEPTION 'Unsafe Identity proof catalog'; END IF;
    END LOOP;
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_catalog() FROM PUBLIC;

CREATE FUNCTION public.hidra_identity_org_commit_caller() RETURNS void
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $$
DECLARE own oid;
BEGIN
    PERFORM public.hidra_identity_org_commit_catalog();
    SELECT proowner INTO own FROM pg_catalog.pg_proc WHERE oid='public.hidra_identity_org_commit_caller()'::regprocedure;
    IF current_setting('role') NOT IN ('none',session_user::text)
       OR EXISTS (SELECT 1 FROM pg_catalog.pg_roles r WHERE
                    (r.rolsuper OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication OR r.rolbypassrls OR r.oid=own)
                    AND pg_catalog.pg_has_role(session_user,r.oid,'MEMBER'))
       OR pg_catalog.has_schema_privilege(session_user,'public','CREATE')
       OR pg_catalog.has_schema_privilege(session_user,'hidra_identity_crypto','CREATE')
       THEN RAISE EXCEPTION 'Identity proof caller rejected'; END IF;
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_caller() FROM PUBLIC;

-- Environment admin grants EXECUTE only to a distinct non-elevated Identity key administrator.
-- A NULL new key revokes, replaces old bytes, and advances the same monotonic CAS generation.
CREATE FUNCTION public.hidra_identity_org_commit_admin(expected_revision bigint, new_key_id text,
    new_audience uuid, new_publisher name, new_key bytea) RETURNS bigint
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $$
DECLARE h public.hidra_identity_org_commit_key_head%ROWTYPE; next_revision bigint;
BEGIN
    PERFORM public.hidra_identity_org_commit_caller();
    IF expected_revision IS NULL OR expected_revision<0 OR new_key_id IS NULL
      OR new_key_id !~ '^[a-zA-Z0-9][a-zA-Z0-9._-]{0,63}$' OR new_audience IS NULL
      OR new_publisher IS NULL OR NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles WHERE rolname=new_publisher AND rolcanlogin)
      OR new_publisher=session_user OR pg_catalog.pg_has_role(session_user,new_publisher,'MEMBER')
      OR (new_key IS NOT NULL AND octet_length(new_key)<>32)
      THEN RAISE EXCEPTION 'Identity key administration rejected'; END IF;
    LOCK TABLE public.hidra_identity_org_commit_key_event IN SHARE ROW EXCLUSIVE MODE NOWAIT;
    LOCK TABLE public.hidra_identity_org_commit_key_head IN EXCLUSIVE MODE NOWAIT;
    SELECT * INTO h FROM public.hidra_identity_org_commit_key_head;
    IF coalesce(h.revision,0)<>expected_revision OR (h.revision IS NULL AND new_key IS NULL)
      OR (new_key IS NULL AND (h.key_id<>new_key_id OR h.audience<>new_audience OR h.publisher<>new_publisher))
      THEN RAISE EXCEPTION 'Identity key generation conflict'; END IF;
    next_revision := expected_revision+1;
    INSERT INTO public.hidra_identity_org_commit_key_event
       VALUES(next_revision,new_key_id,new_audience,new_publisher,new_key IS NOT NULL,
          CASE WHEN new_key IS NULL THEN NULL ELSE hidra_identity_crypto.digest(new_key,'sha256') END,
          session_user,pg_catalog.clock_timestamp());
    INSERT INTO public.hidra_identity_org_commit_key_head(singleton,key_id,revision,audience,publisher,active,key_bytes)
       VALUES(true,new_key_id,next_revision,new_audience,new_publisher,new_key IS NOT NULL,new_key)
       ON CONFLICT(singleton) DO UPDATE SET key_id=excluded.key_id,revision=excluded.revision,
           audience=excluded.audience,publisher=excluded.publisher,active=excluded.active,key_bytes=excluded.key_bytes;
    RETURN next_revision;
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_admin(bigint,text,uuid,name,bytea) FROM PUBLIC;

-- Verify held locks without acquiring any late/earlier relation. Native24 is also mandatory.
CREATE FUNCTION public.hidra_identity_org_commit_locks() RETURNS void
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $$
DECLARE r record;
BEGIN
    FOR r IN SELECT * FROM (VALUES
      ('public.hidra_identity_org_commit_key_event','AccessShareLock'),
      ('public.hidra_identity_org_commit_key_head','ShareLock'),
      ('public.hidra_identity_org_commit_proof_use','ShareRowExclusiveLock'),
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
      ('public.hidra_identity_bound_source_claim_evidence','ShareLock')
    ) required(relation,mode) LOOP
        IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_locks l WHERE l.pid=pg_catalog.pg_backend_pid()
          AND l.locktype='relation' AND l.granted AND l.relation=pg_catalog.to_regclass(r.relation)
          AND l.mode=r.mode) THEN RAISE EXCEPTION 'Identity proof fences unavailable'; END IF;
    END LOOP;
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_locks() FROM PUBLIC;

-- Only metadata; never returns secret material, fingerprint or a computed MAC.
CREATE FUNCTION public.hidra_identity_org_commit_key_context(acquire_fences boolean)
RETURNS TABLE(key_id text,key_revision bigint,audience uuid,publisher text,backend_pid integer,transaction_id bigint)
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $$
DECLARE h public.hidra_identity_org_commit_key_head%ROWTYPE;
BEGIN
    PERFORM public.hidra_identity_org_commit_caller();
    IF current_setting('transaction_isolation')<>'read committed' OR current_setting('transaction_read_only')<>'off'
      OR pg_catalog.has_function_privilege(session_user,
           'public.hidra_identity_org_commit_admin(bigint,text,uuid,name,bytea)','EXECUTE')
      OR pg_catalog.has_function_privilege(session_user,
           'public.hidra_identity_org_commit_verify_consume(bytea,bytea,jsonb)','EXECUTE')
      THEN RAISE EXCEPTION 'Identity proof publisher rejected'; END IF;
    IF acquire_fences IS NULL THEN RAISE EXCEPTION 'Identity proof fences unavailable'; END IF;
    IF acquire_fences THEN
        LOCK TABLE public.hidra_identity_org_commit_key_event IN ACCESS SHARE MODE NOWAIT;
        LOCK TABLE public.hidra_identity_org_commit_key_head IN SHARE MODE NOWAIT;
        LOCK TABLE public.hidra_identity_org_commit_proof_use IN SHARE ROW EXCLUSIVE MODE NOWAIT;
    END IF;
    PERFORM public.hidra_identity_org_commit_locks();
    SELECT * INTO h FROM public.hidra_identity_org_commit_key_head;
    IF h.revision IS NULL OR NOT h.active OR h.publisher<>session_user
      THEN RAISE EXCEPTION 'Identity proof key unavailable'; END IF;
    RETURN QUERY SELECT h.key_id::text,h.revision,h.audience,h.publisher::text,
         pg_catalog.pg_backend_pid(),pg_catalog.txid_current();
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_key_context(boolean) FROM PUBLIC;

CREATE FUNCTION public.hidra_identity_org_commit_text(value text) RETURNS bytea
LANGUAGE sql IMMUTABLE STRICT SET search_path=pg_catalog AS $$
 SELECT pg_catalog.int4send(pg_catalog.octet_length(pg_catalog.convert_to(value,'UTF8')))
       || pg_catalog.convert_to(value,'UTF8')
$$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_text(text) FROM PUBLIC;

-- UTF-16BE sort key matches Java String.compareTo, including supplementary code points.
CREATE FUNCTION public.hidra_identity_org_commit_utf16(value text) RETURNS bytea
LANGUAGE plpgsql IMMUTABLE STRICT SET search_path=pg_catalog AS $$
DECLARE result bytea := ''::bytea; cp integer; unit integer; i integer;
BEGIN
    FOR i IN 1..pg_catalog.char_length(value) LOOP
        cp := pg_catalog.ascii(pg_catalog.substr(value,i,1));
        IF cp>65535 THEN
            unit:=55296+((cp-65536)>>10);
            result:=result || pg_catalog.substr(pg_catalog.int4send(unit),3,2);
            unit:=56320+((cp-65536)&1023);
        ELSE unit:=cp; END IF;
        result:=result || pg_catalog.substr(pg_catalog.int4send(unit),3,2);
    END LOOP;
    RETURN result;
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_utf16(text) FROM PUBLIC;

CREATE FUNCTION public.hidra_identity_org_commit_prefix(audience uuid,publisher text,key_id text,revision bigint)
RETURNS bytea LANGUAGE sql IMMUTABLE STRICT SET search_path=pg_catalog AS $$
 SELECT public.hidra_identity_org_commit_text('HIDRA/IDENTITY/ORG-APPOINTMENT-COMMIT/1')
    || pg_catalog.int4send(1) || pg_catalog.uuid_send(audience)
    || public.hidra_identity_org_commit_text(publisher)
    || public.hidra_identity_org_commit_text(key_id) || pg_catalog.int8send(revision)
$$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_prefix(uuid,text,text,bigint) FROM PUBLIC;

-- expected is constructed ONLY by the trusted final-guard owner from actual event and pinned
-- snapshots. No ordinary login may supply this object, or invoke this encoder/verifier directly.
-- Times are exact signed epoch microseconds, hashes are lowercase SHA256, pairs are canonical.
CREATE FUNCTION public.hidra_identity_org_commit_frame(prefix bytea,nonce bytea,expected jsonb,
    backend_pid integer,transaction_id bigint) RETURNS bytea
LANGUAGE plpgsql IMMUTABLE STRICT SET search_path=pg_catalog AS $$
DECLARE result bytea; pair jsonb; prev_company bytea; prev_unit bytea; company text; unit text;
        ck bytea; uk bytea; count_pairs integer; action text; employee text;
        sampled bigint; native_until bigint; operation_until bigint;
BEGIN
    IF pg_catalog.jsonb_typeof(expected)<>'object' OR pg_catalog.jsonb_typeof(expected->'pairs')<>'array'
      OR octet_length(nonce)<>32 OR expected->>'command' !~ '^[0-9a-f]{64}$'
      OR expected->>'payload' !~ '^[0-9a-f]{64}$'
      OR (SELECT count(*) FROM pg_catalog.jsonb_object_keys(expected))<>12
      THEN RAISE EXCEPTION 'Invalid Identity proof expectation'; END IF;
    action:=expected->>'action'; employee:=expected->>'employee';
    count_pairs:=pg_catalog.jsonb_array_length(expected->'pairs');
    IF action IS NULL OR action NOT IN ('CREATE','END','TRANSFER','CORRECT') OR employee IS NULL OR employee=''
      OR count_pairs NOT BETWEEN 1 AND 2 OR (action IN ('CREATE','END') AND count_pairs<>1)
      THEN RAISE EXCEPTION 'Invalid Identity proof expectation'; END IF;
    sampled:=(expected->>'sampled')::bigint;
    native_until:=(expected->>'nativeUntil')::bigint;
    operation_until:=(expected->>'operationUntil')::bigint;
    IF sampled IS NULL OR native_until IS NULL OR operation_until IS NULL
      OR sampled>=operation_until OR operation_until>native_until
      THEN RAISE EXCEPTION 'Invalid Identity proof window'; END IF;
    result:=prefix || nonce || pg_catalog.uuid_send((expected->>'binding')::uuid)
       || pg_catalog.uuid_send((expected->>'session')::uuid)
       || public.hidra_identity_org_commit_text(expected->>'actor')
       || public.hidra_identity_org_commit_text(action) || pg_catalog.int4send(count_pairs);
    FOR pair IN SELECT value FROM pg_catalog.jsonb_array_elements(expected->'pairs') LOOP
        IF pg_catalog.jsonb_typeof(pair)<>'array' OR pg_catalog.jsonb_array_length(pair)<>2
          THEN RAISE EXCEPTION 'Invalid Identity proof scope'; END IF;
        company:=pair->>0; unit:=pair->>1;
        IF company IS NULL OR company='' OR unit IS NULL OR unit=''
          THEN RAISE EXCEPTION 'Invalid Identity proof scope'; END IF;
        ck:=public.hidra_identity_org_commit_utf16(company); uk:=public.hidra_identity_org_commit_utf16(unit);
        IF prev_company IS NOT NULL AND (prev_company<>ck OR prev_unit>=uk)
          THEN RAISE EXCEPTION 'Invalid Identity proof scope'; END IF;
        result:=result || public.hidra_identity_org_commit_text(company) || public.hidra_identity_org_commit_text(unit);
        prev_company:=ck; prev_unit:=uk;
    END LOOP;
    result:=result || public.hidra_identity_org_commit_text(employee) || pg_catalog.int8send(sampled)
      || pg_catalog.int8send(native_until) || pg_catalog.int8send(operation_until)
      || pg_catalog.int4send(backend_pid) || pg_catalog.int8send(transaction_id)
      || pg_catalog.uuid_send((expected->>'event')::uuid)
      || pg_catalog.decode(expected->>'command','hex') || pg_catalog.decode(expected->>'payload','hex');
    IF result IS NULL OR octet_length(result)>4096 THEN RAISE EXCEPTION 'Invalid Identity proof expectation'; END IF;
    RETURN result;
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_frame(bytea,bytea,jsonb,integer,bigint) FROM PUBLIC;

-- EXECUTE remains private. A future trusted Organization final-guard owner calls it internally
-- in the SAME statement that creates the receipt; this migration activates no Organization writer.
CREATE FUNCTION public.hidra_identity_org_commit_verify_consume(frame bytea,mac bytea,expected jsonb)
RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $$
DECLARE h public.hidra_identity_org_commit_key_head%ROWTYPE; prefix bytea; nonce bytea;
        computed bytea; mismatch integer:=0; i integer; observed timestamptz; now_micros bigint; canonical bytea;
BEGIN
    PERFORM public.hidra_identity_org_commit_caller();
    PERFORM public.hidra_identity_org_commit_locks();
    IF current_setting('transaction_isolation')<>'read committed' OR current_setting('transaction_read_only')<>'off'
      OR frame IS NULL OR mac IS NULL OR expected IS NULL OR octet_length(frame) NOT BETWEEN 1 AND 4096
      OR octet_length(mac)<>32 THEN RAISE EXCEPTION 'Identity commit proof rejected'; END IF;
    SELECT * INTO h FROM public.hidra_identity_org_commit_key_head;
    IF h.revision IS NULL OR NOT h.active OR h.publisher<>session_user
      THEN RAISE EXCEPTION 'Identity commit proof rejected'; END IF;
    prefix:=public.hidra_identity_org_commit_prefix(h.audience,h.publisher::text,h.key_id,h.revision);
    nonce:=pg_catalog.substr(frame,octet_length(prefix)+1,32);
    canonical:=public.hidra_identity_org_commit_frame(prefix,nonce,expected,pg_catalog.pg_backend_pid(),pg_catalog.txid_current());
    computed:=hidra_identity_crypto.hmac(frame,h.key_bytes,'sha256');
    FOR i IN 0..31 LOOP mismatch:=mismatch | (pg_catalog.get_byte(computed,i) # pg_catalog.get_byte(mac,i)); END LOOP;
    observed:=pg_catalog.clock_timestamp();
    now_micros:=(EXTRACT(epoch FROM observed)*1000000)::bigint;
    IF mismatch<>0 OR canonical IS DISTINCT FROM frame OR now_micros<(expected->>'sampled')::bigint
      OR now_micros>=(expected->>'operationUntil')::bigint THEN RAISE EXCEPTION 'Identity commit proof rejected'; END IF;
    INSERT INTO public.hidra_identity_org_commit_proof_use
      VALUES(h.revision,nonce,h.key_id,pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),(expected->>'event')::uuid,
         pg_catalog.decode(expected->>'command','hex'),pg_catalog.decode(expected->>'payload','hex'),observed);
EXCEPTION WHEN OTHERS THEN
    -- Strip frames/keys and nested diagnostics from callers. The statement remains failed/aborted.
    RAISE EXCEPTION USING ERRCODE='P0001', MESSAGE='Identity commit proof rejected';
END $$;
REVOKE ALL ON FUNCTION public.hidra_identity_org_commit_verify_consume(bytea,bytea,jsonb) FROM PUBLIC;

DO $$ BEGIN PERFORM public.hidra_identity_org_commit_catalog(); END $$;

