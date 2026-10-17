-- HAPI-575 C2C: extend immutable issuance guard to verified directory origins.
-- No rewrite/backfill or new source-expiry semantics. All existing fences remain required.
CREATE OR REPLACE FUNCTION public.hidra_identity_bound_source_claim_guard()
RETURNS trigger LANGUAGE plpgsql VOLATILE SECURITY INVOKER
SET search_path = pg_catalog, pg_temp
AS $$
DECLARE
    previous public.hidra_identity_bound_source_claim_evidence%ROWTYPE;
    origin public.hidra_identity_operation_binding%ROWTYPE;
    inserted_xid bigint;
    captured_epoch bigint;
    fence_count integer;
    observed timestamptz;
BEGIN
    IF TG_OP <> 'INSERT' THEN
        RAISE EXCEPTION 'Immutable source-claim evidence cannot be mutated' USING ERRCODE='23514';
    END IF;
    SELECT * INTO previous FROM public.hidra_identity_bound_source_claim_evidence
        WHERE binding_id=NEW.binding_id;
    IF FOUND THEN
        IF previous IS DISTINCT FROM NEW THEN
            RAISE EXCEPTION 'Conflicting source-claim evidence' USING ERRCODE='23514';
        END IF;
        RETURN NULL; -- Exact historical storage replay issues no receipt or token.
    END IF;
    IF current_setting('transaction_isolation') <> 'read committed'
            OR current_setting('transaction_read_only') <> 'off' THEN
        RAISE EXCEPTION 'Source-claim issuance transaction unavailable' USING ERRCODE='23514';
    END IF;
    SELECT count(DISTINCT l.relation) INTO fence_count FROM pg_catalog.pg_locks l
        WHERE l.pid=pg_backend_pid() AND l.granted AND l.locktype='relation'
        AND l.mode='ShareRowExclusiveLock' AND l.relation IN (
        'public.hidra_identity_attribute_definition'::regclass,
        'public.hidra_identity_authorization_policy'::regclass,
        'public.hidra_identity_authorization_policy_rule'::regclass,
        'public.hidra_identity_authorization_policy_version'::regclass,
        'public.hidra_identity_external_group_mapping'::regclass,
        'public.hidra_identity_external_identity'::regclass,
        'public.hidra_identity_external_permission_mapping'::regclass,
        'public.hidra_identity_external_role_mapping'::regclass,
        'public.hidra_identity_group'::regclass,
        'public.hidra_identity_group_role_grant'::regclass,
        'public.hidra_identity_local_credential'::regclass,
        'public.hidra_identity_login_session'::regclass,
        'public.hidra_identity_operation_binding'::regclass,
        'public.hidra_identity_permission'::regclass,
        'public.hidra_identity_provider'::regclass,
        'public.hidra_identity_role'::regclass,
        'public.hidra_identity_role_permission_grant'::regclass,
        'public.hidra_identity_subject_security_attribute'::regclass,
        'public.hidra_identity_user'::regclass,
        'public.hidra_identity_user_group_membership'::regclass,
        'public.hidra_identity_user_permission_grant'::regclass,
        'public.hidra_identity_user_role_grant'::regclass
        );
    IF fence_count <> 22 OR NOT EXISTS (
        SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_backend_pid() AND granted
        AND locktype='relation' AND mode='AccessShareLock'
        AND relation='public.hidra_identity_external_mapping_generation'::regclass) THEN
        RAISE EXCEPTION 'Source-claim issuance fence unavailable' USING ERRCODE='23514';
    END IF;
    SELECT * INTO origin FROM public.hidra_identity_operation_binding WHERE binding_id=NEW.binding_id;
    IF NOT FOUND OR origin.authentication_type IS NULL
            OR origin.authentication_type NOT IN ('OIDC','LDAP','ACTIVE_DIRECTORY') THEN
        RAISE EXCEPTION 'Source-claim binding unavailable' USING ERRCODE='23514';
    END IF;
    SELECT b.xmin::text::bigint INTO inserted_xid FROM public.hidra_identity_operation_binding b
        WHERE b.binding_id=NEW.binding_id;
    IF NEW.issuance_transaction_id <> txid_current()
            OR inserted_xid <> mod(txid_current(),4294967296) THEN
        RAISE EXCEPTION 'Source-claims must accompany original issuance' USING ERRCODE='23514';
    END IF;
    SELECT generation INTO captured_epoch FROM public.hidra_identity_external_mapping_generation
        WHERE provider_id=origin.provider_id;
    observed := clock_timestamp();
    IF captured_epoch IS NULL OR captured_epoch <> NEW.mapping_generation
            OR observed >= origin.expires_at THEN
        RAISE EXCEPTION 'Source-claim evidence is stale or unavailable' USING ERRCODE='23514';
    END IF;
    IF origin.authentication_type = 'OIDC' THEN
        IF origin.source_expires_at IS NULL OR observed >= origin.source_expires_at THEN
            RAISE EXCEPTION 'Source-claim evidence is stale or unavailable' USING ERRCODE='23514';
        END IF;
    ELSIF origin.authentication_type IN ('LDAP','ACTIVE_DIRECTORY') THEN
        IF origin.source_issuer IS NOT NULL OR origin.source_expires_at IS NOT NULL
                OR origin.session_protocol IS DISTINCT FROM 'LDAP' THEN
            RAISE EXCEPTION 'Source-claim binding unavailable' USING ERRCODE='23514';
        END IF;
    ELSE
        RAISE EXCEPTION 'Source-claim binding unavailable' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$$;
REVOKE ALL ON FUNCTION public.hidra_identity_bound_source_claim_guard() FROM PUBLIC;
