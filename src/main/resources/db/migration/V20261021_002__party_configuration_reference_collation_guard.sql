-- HAPI-778: Additive repair of the Party configuration reference collation guard.
-- Preserve the applied 001 migration and its checksum; replace only this owner routine.
-- No login, role grant, table write, native application permission or employer assertion.
-- Ordinary application and protected publisher principals must not receive EXECUTE.
CREATE OR REPLACE FUNCTION public.hidra_party_require_organization_configuration_reference(p_exact text)
RETURNS TABLE(party_id varchar(80), generation bigint, backend_pid integer, transaction_id bigint)
LANGUAGE plpgsql VOLATILE PARALLEL UNSAFE SECURITY DEFINER CALLED ON NULL INPUT
SET search_path = pg_catalog, pg_temp
AS $configuration_reference$
DECLARE
    result_count bigint;
    boundary integer;
BEGIN
    -- session_user survives nested SECURITY DEFINER calls; current_user does not.
    IF NOT pg_catalog.has_function_privilege(session_user,
            'public.hidra_party_require_organization_configuration_reference(text)'::pg_catalog.regprocedure,'EXECUTE')
       OR EXISTS (SELECT 1 FROM pg_catalog.pg_proc p
            CROSS JOIN LATERAL pg_catalog.aclexplode(COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a
            WHERE p.oid='public.hidra_party_require_organization_configuration_reference(text)'::pg_catalog.regprocedure
              AND a.grantee=0 AND a.privilege_type='EXECUTE')
       OR pg_catalog.current_setting('role') NOT IN ('none',session_user::text)
       OR EXISTS (SELECT 1 FROM pg_catalog.pg_roles r
            WHERE (r.rolname=session_user OR pg_catalog.pg_has_role(session_user,r.oid,'MEMBER'))
              AND (r.rolsuper OR r.rolbypassrls OR r.rolcreatedb OR r.rolcreaterole OR r.rolreplication
                   OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files',
                                    'pg_read_all_data','pg_write_all_data','pg_signal_backend','pg_checkpoint')))
       OR pg_catalog.has_table_privilege(session_user,'public.hidra_party_party',
            'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
       OR pg_catalog.has_any_column_privilege(session_user,'public.hidra_party_party','INSERT,UPDATE,REFERENCES') THEN
        RAISE EXCEPTION 'Organization configuration reference unavailable' USING ERRCODE='23514';
    END IF;
    IF p_exact IS NULL OR pg_catalog.char_length(p_exact) NOT BETWEEN 1 AND 80
       OR EXISTS (SELECT 1 FROM pg_catalog.generate_series(1,pg_catalog.char_length(p_exact)) AS part(n)
                  WHERE pg_catalog.ascii(pg_catalog.substr(p_exact,part.n,1)) BETWEEN 0 AND 31
                     OR pg_catalog.ascii(pg_catalog.substr(p_exact,part.n,1)) BETWEEN 127 AND 159) THEN
        RAISE EXCEPTION 'Organization configuration reference unavailable' USING ERRCODE='23514';
    END IF;
    -- Match the native exact-identifier boundary: trim/Unicode space is not normalization.
    FOREACH boundary IN ARRAY ARRAY[pg_catalog.ascii(pg_catalog.left(p_exact,1)),
                                    pg_catalog.ascii(pg_catalog.right(p_exact,1))] LOOP
        IF boundary IN (32,160,5760,8192,8193,8194,8195,8196,8197,8198,8199,8200,8201,8202,
                        8232,8233,8239,8287,12288) THEN
            RAISE EXCEPTION 'Organization configuration reference unavailable' USING ERRCODE='23514';
        END IF;
    END LOOP;
    IF pg_catalog.current_setting('transaction_isolation')<>'read committed'
       OR pg_catalog.current_setting('transaction_read_only')<>'off'
       OR pg_catalog.current_setting('session_replication_role')<>'origin' THEN
        RAISE EXCEPTION 'Organization configuration reference unavailable' USING ERRCODE='23514';
    END IF;
    -- Same owner order as the application export. Success retains both locks until completion.
    LOCK TABLE public.hidra_party_party IN SHARE ROW EXCLUSIVE MODE NOWAIT;
    LOCK TABLE public.hidra_party_eligibility_generation IN ACCESS SHARE MODE NOWAIT;
    IF NOT (
        SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
          AND pg_catalog.current_setting('transaction_read_only')='off'
          AND pg_catalog.current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(session_user,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(session_user,'pg_catalog','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles r
              WHERE (r.rolname=session_user OR pg_catalog.pg_has_role(session_user,r.oid,'MEMBER'))
                AND (r.rolsuper OR r.rolbypassrls))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_class c
              WHERE c.oid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass)
                AND (c.relkind<>'r' OR c.relpersistence<>'p' OR c.relrowsecurity OR c.relforcerowsecurity
                  OR c.relispartition OR pg_catalog.pg_has_role(session_user,c.relowner,'MEMBER')))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_inherits
              WHERE inhrelid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass)
                 OR inhparent IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass))
          AND NOT pg_catalog.has_table_privilege(session_user,'public.hidra_party_party','TRIGGER')
          AND NOT pg_catalog.has_table_privilege(session_user,'public.hidra_party_eligibility_generation','INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT pg_catalog.has_any_column_privilege(session_user,'public.hidra_party_eligibility_generation','INSERT,UPDATE,REFERENCES')
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(attnotnull) AND
              pg_catalog.array_agg(attname::text ORDER BY attnum)=ARRAY['party_id','generation','present'] AND
              pg_catalog.array_agg(atttypid::integer ORDER BY attnum)=ARRAY[1043,20,16] AND
              pg_catalog.array_agg(atttypmod ORDER BY attnum)=ARRAY[84,-1,-1] AND
              pg_catalog.array_agg(attcollation ORDER BY attnum)=ARRAY['pg_catalog."default"'::pg_catalog.regcollation::oid,0::oid,0::oid]
              FROM pg_catalog.pg_attribute WHERE attrelid='public.hidra_party_eligibility_generation'::regclass AND attnum>0 AND NOT attisdropped)
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.count(*) FILTER (WHERE contype='p' AND convalidated AND NOT condeferrable AND conkey=ARRAY[1]::smallint[])=1
              AND pg_catalog.count(*) FILTER (WHERE contype='c' AND convalidated AND NOT condeferrable
                AND pg_catalog.pg_get_constraintdef(oid)='CHECK ((generation > 0))')=1
              FROM pg_catalog.pg_constraint WHERE conrelid='public.hidra_party_eligibility_generation'::regclass)
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.bool_and(prosecdef AND prokind='f' AND pronargs=0
              AND prorettype='trigger'::regtype AND prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='plpgsql')
              AND NOT proleakproof AND NOT proisstrict AND provolatile='v' AND proparallel='u'
              AND proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND NOT pg_catalog.pg_has_role(session_user,proowner,'MEMBER') AND NOT pg_catalog.has_function_privilege(session_user,oid,'EXECUTE')
              AND ((oid='public.hidra_p25_party_eligibility_write()'::regprocedure AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(prosrc,'UTF8')),'hex')='d7dff6250bd1542dfd341a8749ae705a7cbcd0e4b5fc0f2ec92b236c639fd477')
                OR (oid='public.hidra_p25_party_eligibility_truncate()'::regprocedure AND pg_catalog.encode(pg_catalog.sha256(pg_catalog.convert_to(prosrc,'UTF8')),'hex')='be146fe4ce203bd9b84902dd5f349a501e16b86df8a57fbb7c567ec750640c8d')))
              FROM pg_catalog.pg_proc WHERE oid IN ('public.hidra_p25_party_eligibility_write()'::regprocedure,'public.hidra_p25_party_eligibility_truncate()'::regprocedure))
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(tgenabled='O' AND tgqual IS NULL AND tgnargs=0 AND tgconstraint=0 AND tgparentid=0
              AND ((tgrelid='public.hidra_party_party'::regclass AND tgname='trg_p25_party_eligibility_write' AND tgtype=29
                    AND tgfoid='public.hidra_p25_party_eligibility_write()'::regprocedure
                    AND ARRAY(SELECT a.attname::text FROM pg_catalog.pg_attribute a WHERE a.attrelid=tgrelid
                              AND a.attnum=ANY(tgattr::smallint[]) ORDER BY a.attnum)=ARRAY['id','status'])
                OR (tgrelid='public.hidra_party_party'::regclass AND tgname='trg_p25_party_eligibility_root_truncate' AND tgtype=34
                    AND tgfoid='public.hidra_p25_party_eligibility_truncate()'::regprocedure AND pg_catalog.cardinality(tgattr::smallint[])=0)
                OR (tgrelid='public.hidra_party_eligibility_generation'::regclass AND tgname='trg_p25_party_eligibility_registry_truncate' AND tgtype=34
                    AND tgfoid='public.hidra_p25_party_eligibility_truncate()'::regprocedure AND pg_catalog.cardinality(tgattr::smallint[])=0)))
              FROM pg_catalog.pg_trigger WHERE tgrelid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass) AND NOT tgisinternal)
    ) OR NOT (
        SELECT pg_catalog.count(*)=1 AND pg_catalog.bool_and(a.attnotnull AND a.atttypid=1043 AND a.atttypmod=84 AND a.attcollation='pg_catalog."default"'::pg_catalog.regcollation)
        FROM pg_catalog.pg_attribute a
        WHERE a.attrelid='public.hidra_party_party'::pg_catalog.regclass AND a.attname='id' AND NOT a.attisdropped
    ) OR NOT (
        SELECT pg_catalog.count(*)=1 AND pg_catalog.bool_and(a.attnotnull AND a.atttypid=1043 AND a.atttypmod=44 AND a.attcollation='pg_catalog."default"'::pg_catalog.regcollation)
        FROM pg_catalog.pg_attribute a
        WHERE a.attrelid='public.hidra_party_party'::pg_catalog.regclass AND a.attname='status' AND NOT a.attisdropped
    ) OR NOT (
        SELECT pg_catalog.count(*)=1 FROM pg_catalog.pg_constraint k
        WHERE k.conrelid='public.hidra_party_party'::pg_catalog.regclass
          AND k.contype='p' AND k.convalidated AND NOT k.condeferrable AND k.conkey=ARRAY[1]::smallint[]
    ) OR EXISTS (
        SELECT 1 FROM pg_catalog.pg_constraint k
        WHERE k.conrelid IN ('public.hidra_party_party'::pg_catalog.regclass,
                            'public.hidra_party_eligibility_generation'::pg_catalog.regclass)
          AND (NOT k.convalidated OR k.condeferrable)
    ) OR EXISTS (
        SELECT 1 FROM pg_catalog.pg_class c
        WHERE c.oid IN ('public.hidra_party_party'::pg_catalog.regclass,
                        'public.hidra_party_eligibility_generation'::pg_catalog.regclass)
          AND c.relowner<>(SELECT p.proowner FROM pg_catalog.pg_proc p
                WHERE p.oid='public.hidra_party_require_organization_configuration_reference(text)'::pg_catalog.regprocedure)
    ) OR NOT (
        SELECT pg_catalog.count(*)=2 FROM pg_catalog.pg_locks l
        WHERE l.pid=pg_catalog.pg_backend_pid() AND l.granted AND l.locktype='relation'
          AND ((l.relation='public.hidra_party_party'::pg_catalog.regclass AND l.mode='ShareRowExclusiveLock')
            OR (l.relation='public.hidra_party_eligibility_generation'::pg_catalog.regclass AND l.mode='AccessShareLock'))
    ) THEN
        RAISE EXCEPTION 'Organization configuration reference unavailable' USING ERRCODE='23514';
    END IF;
    RETURN QUERY
        SELECT m.id,g.generation,pg_catalog.pg_backend_pid(),pg_catalog.txid_current()
        FROM public.hidra_party_party m JOIN public.hidra_party_eligibility_generation g ON g.party_id=m.id
        WHERE m.id=p_exact AND m.id::text COLLATE pg_catalog."C"=p_exact COLLATE pg_catalog."C"
          AND g.party_id::text COLLATE pg_catalog."C"=m.id::text COLLATE pg_catalog."C"
          AND m.status::text COLLATE pg_catalog."C"='ACTIVE' COLLATE pg_catalog."C"
          AND g.present AND g.generation>0;
    GET DIAGNOSTICS result_count = ROW_COUNT;
    IF result_count<>1 THEN
        RAISE EXCEPTION 'Organization configuration reference unavailable' USING ERRCODE='23514';
    END IF;
EXCEPTION WHEN OTHERS THEN
    -- Never expose a missing/retired/corrupt selector or private catalog detail to the caller.
    RAISE EXCEPTION 'Organization configuration reference unavailable' USING ERRCODE='23514';
END;
$configuration_reference$;
REVOKE ALL ON FUNCTION public.hidra_party_require_organization_configuration_reference(text) FROM PUBLIC;
COMMENT ON FUNCTION public.hidra_party_require_organization_configuration_reference(text) IS
'Party-owned technical new-reference eligibility for explicitly authorized database configuration; not a native permission, employer assertion or transferable attestation';
