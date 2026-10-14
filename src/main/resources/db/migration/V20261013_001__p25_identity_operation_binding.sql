-- HPR-P25-050B: fresh development schema for immutable operation authentication.
-- No deployed data conversion, token backfill or inference of producer origin.
-- Structural SQL validity alone is not proof: owner reader must verify the private tag.
CREATE TABLE public.hidra_identity_operation_binding (
    binding_id varchar(36) NOT NULL,
    format_version smallint NOT NULL,
    native_issuer text NOT NULL,
    native_audience text NOT NULL,
    jti varchar(36) NOT NULL,
    session_id varchar(36) NOT NULL,
    user_id varchar(80) NOT NULL,
    authentication_type varchar(40) NOT NULL,
    session_protocol varchar(80) NOT NULL,
    provider_id varchar(80) NOT NULL,
    provider_security_version bigint NOT NULL,
    external_identity_id varchar(80),
    external_subject text,
    external_security_version bigint,
    source_issuer text,
    source_expires_at timestamptz,
    credential_id varchar(80),
    credential_security_version bigint,
    issued_at timestamptz NOT NULL,
    expires_at timestamptz NOT NULL,
    producer_authenticator bytea NOT NULL,
    CONSTRAINT p25_binding_pk PRIMARY KEY (binding_id),
    CONSTRAINT p25_binding_issuer_jti_unique UNIQUE (native_issuer, jti),
    CONSTRAINT p25_binding_session_unique UNIQUE (session_id),
    CONSTRAINT p25_binding_format CHECK (format_version = 1),
    CONSTRAINT p25_binding_binding_uuid CHECK (binding_id ~ '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$'),
    CONSTRAINT p25_binding_jti_uuid CHECK (jti ~ '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$'),
    CONSTRAINT p25_binding_session_uuid CHECK (session_id ~ '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$'),
    CONSTRAINT p25_binding_provider_generation CHECK (provider_security_version > 0),
    CONSTRAINT p25_binding_tag CHECK (octet_length(producer_authenticator) = 32),
    CONSTRAINT p25_binding_interval CHECK (
        isfinite(issued_at) AND isfinite(expires_at) AND issued_at < expires_at
        AND date_trunc('second', issued_at) = issued_at AND date_trunc('second', expires_at) = expires_at),
    CONSTRAINT p25_binding_branches CHECK (
        (authentication_type = 'LOCAL' AND session_protocol = 'LOCAL'
         AND credential_id IS NOT NULL AND credential_security_version IS NOT NULL AND credential_security_version > 0
         AND external_identity_id IS NULL AND external_subject IS NULL AND external_security_version IS NULL
         AND source_issuer IS NULL AND source_expires_at IS NULL)
        OR (authentication_type IN ('LDAP','ACTIVE_DIRECTORY') AND session_protocol = 'LDAP'
         AND external_identity_id IS NOT NULL AND external_subject IS NOT NULL AND external_security_version IS NOT NULL
         AND external_subject !~ '^[\u0009\u000a\u000b\u000c\u000d\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]*$' AND external_security_version > 0
         AND credential_id IS NULL AND credential_security_version IS NULL AND source_issuer IS NULL AND source_expires_at IS NULL)
        OR (authentication_type = 'OIDC' AND session_protocol = 'OIDC'
         AND external_identity_id IS NOT NULL AND external_subject IS NOT NULL AND external_security_version IS NOT NULL
         AND external_subject !~ '^[\u0009\u000a\u000b\u000c\u000d\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]*$' AND external_security_version > 0
         AND source_issuer IS NOT NULL AND source_issuer !~ '^[\u0009\u000a\u000b\u000c\u000d\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]*$'
         AND source_expires_at IS NOT NULL AND isfinite(source_expires_at) AND expires_at <= source_expires_at
         AND credential_id IS NULL AND credential_security_version IS NULL)),
    CONSTRAINT p25_binding_native_issuer_exact CHECK (length(native_issuer) > 0 AND native_issuer !~ '^[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]|[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]$' AND octet_length(native_issuer) <= 512),
    CONSTRAINT p25_binding_native_audience_exact CHECK (length(native_audience) > 0 AND native_audience !~ '^[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]|[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]$' AND octet_length(native_audience) <= 512),
    CONSTRAINT p25_binding_user_id_exact CHECK (length(user_id) > 0 AND user_id !~ '^[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]|[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]$'),
    CONSTRAINT p25_binding_provider_id_exact CHECK (length(provider_id) > 0 AND provider_id !~ '^[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]|[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]$'),
    CONSTRAINT p25_binding_external_identity_id_exact CHECK (external_identity_id IS NULL OR (length(external_identity_id) > 0 AND external_identity_id !~ '^[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]|[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]$')),
    CONSTRAINT p25_binding_credential_id_exact CHECK (credential_id IS NULL OR (length(credential_id) > 0 AND credential_id !~ '^[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]|[\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u000a\u000b\u000c\u000d\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f\u0020\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2008\u2009\u200a\u2028\u2029\u205f\u3000]$')),
    CONSTRAINT p25_binding_session_fk FOREIGN KEY (session_id) REFERENCES public.hidra_identity_login_session(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE,
    CONSTRAINT p25_binding_user_fk FOREIGN KEY (user_id) REFERENCES public.hidra_identity_user(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE,
    CONSTRAINT p25_binding_provider_fk FOREIGN KEY (provider_id) REFERENCES public.hidra_identity_provider(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE,
    CONSTRAINT p25_binding_link_fk FOREIGN KEY (external_identity_id) REFERENCES public.hidra_identity_external_identity(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE,
    CONSTRAINT p25_binding_credential_fk FOREIGN KEY (credential_id) REFERENCES public.hidra_identity_local_credential(id)
        ON UPDATE RESTRICT ON DELETE RESTRICT NOT DEFERRABLE
);
CREATE INDEX p25_binding_user_idx ON public.hidra_identity_operation_binding(user_id);
CREATE INDEX p25_binding_provider_idx ON public.hidra_identity_operation_binding(provider_id);
CREATE INDEX p25_binding_link_idx ON public.hidra_identity_operation_binding(external_identity_id);
CREATE INDEX p25_binding_credential_idx ON public.hidra_identity_operation_binding(credential_id);
COMMENT ON TABLE public.hidra_identity_operation_binding IS
    'Identity-owned immutable bearer/session association; structural SQL validity is not producer verification or permission.';
COMMENT ON COLUMN public.hidra_identity_operation_binding.producer_authenticator IS
    'Private format-one HMAC-SHA256 over the complete immutable scalar tuple; owner validation required, never exported.';

CREATE FUNCTION public.hidra_identity_operation_binding_guard()
RETURNS trigger LANGUAGE plpgsql VOLATILE SECURITY INVOKER
SET search_path = pg_catalog, public
AS $$
DECLARE
    p public.hidra_identity_provider%ROWTYPE;
    e public.hidra_identity_external_identity%ROWTYPE;
    c public.hidra_identity_local_credential%ROWTYPE;
    u public.hidra_identity_user%ROWTYPE;
    s public.hidra_identity_login_session%ROWTYPE;
    previous public.hidra_identity_operation_binding%ROWTYPE;
    observed timestamptz;
BEGIN
    IF TG_OP <> 'INSERT' THEN
        RAISE EXCEPTION 'Immutable authentication bindings cannot be mutated' USING ERRCODE = '23514';
    END IF;
    IF current_setting('transaction_isolation') <> 'read committed' THEN
        RAISE EXCEPTION 'Authentication binding requires READ COMMITTED' USING ERRCODE = '23514';
    END IF;
    -- Fixed owner lock order; each VOLATILE SELECT observes the row after a lock wait.
    SELECT * INTO p FROM public.hidra_identity_provider WHERE id = NEW.provider_id FOR UPDATE;
    IF NOT FOUND THEN RAISE EXCEPTION 'Invalid authentication owner tuple' USING ERRCODE = '23514'; END IF;
    IF NEW.authentication_type = 'LOCAL' THEN
        SELECT * INTO c FROM public.hidra_identity_local_credential WHERE id = NEW.credential_id FOR UPDATE;
        IF NOT FOUND THEN RAISE EXCEPTION 'Invalid authentication owner tuple' USING ERRCODE = '23514'; END IF;
    ELSE
        SELECT * INTO e FROM public.hidra_identity_external_identity WHERE id = NEW.external_identity_id FOR UPDATE;
        IF NOT FOUND THEN RAISE EXCEPTION 'Invalid authentication owner tuple' USING ERRCODE = '23514'; END IF;
    END IF;
    SELECT * INTO u FROM public.hidra_identity_user WHERE id = NEW.user_id FOR UPDATE;
    IF NOT FOUND THEN RAISE EXCEPTION 'Invalid authentication owner tuple' USING ERRCODE = '23514'; END IF;
    SELECT * INTO s FROM public.hidra_identity_login_session WHERE id = NEW.session_id FOR UPDATE;
    IF NOT FOUND THEN RAISE EXCEPTION 'Invalid authentication owner tuple' USING ERRCODE = '23514'; END IF;
    SELECT * INTO previous FROM public.hidra_identity_operation_binding
        WHERE binding_id = NEW.binding_id OR (native_issuer = NEW.native_issuer AND jti = NEW.jti) OR session_id = NEW.session_id;
    IF FOUND THEN
        IF previous IS DISTINCT FROM NEW THEN
            RAISE EXCEPTION 'Conflicting immutable authentication binding' USING ERRCODE = '23514';
        END IF;
        -- Exact storage replay may observe a terminal/expired or subsequently revoked tuple.
        -- Suppress a new row; this never mints a receipt or returns a new bearer.
        RETURN NULL;
    END IF;
    observed := clock_timestamp();
    IF p.provider_type IS DISTINCT FROM NEW.authentication_type OR p.status IS DISTINCT FROM 'ACTIVE'
        OR p.security_version IS DISTINCT FROM NEW.provider_security_version
        OR u.user_type IS DISTINCT FROM 'HUMAN' OR u.status IS DISTINCT FROM 'ACTIVE'
        OR (u.locked_until IS NOT NULL AND u.locked_until > observed)
        OR s.user_id IS DISTINCT FROM NEW.user_id OR s.identity_provider_id IS DISTINCT FROM NEW.provider_id
        OR s.external_identity_id IS DISTINCT FROM NEW.external_identity_id OR s.session_type IS DISTINCT FROM NEW.session_protocol
        OR s.started_at IS DISTINCT FROM NEW.issued_at OR s.expires_at IS DISTINCT FROM NEW.expires_at
        OR s.status IS DISTINCT FROM 'ACTIVE' OR s.ended_at IS NOT NULL
        OR NEW.issued_at > observed OR NEW.expires_at <= observed THEN
        RAISE EXCEPTION 'Ineligible authentication owner tuple' USING ERRCODE = '23514';
    END IF;
    IF NEW.authentication_type = 'LOCAL' THEN
        IF c.user_id IS DISTINCT FROM NEW.user_id OR c.credential_status IS DISTINCT FROM 'ACTIVE'
            OR c.security_version IS DISTINCT FROM NEW.credential_security_version THEN
            RAISE EXCEPTION 'Ineligible authentication credential tuple' USING ERRCODE = '23514';
        END IF;
    ELSE
        IF e.user_id IS DISTINCT FROM NEW.user_id OR e.identity_provider_id IS DISTINCT FROM NEW.provider_id
            OR e.external_subject IS DISTINCT FROM NEW.external_subject OR e.status IS DISTINCT FROM 'LINKED'
            OR e.security_version IS DISTINCT FROM NEW.external_security_version THEN
            RAISE EXCEPTION 'Ineligible authentication external tuple' USING ERRCODE = '23514';
        END IF;
        IF NEW.authentication_type = 'OIDC' AND (p.issuer_uri IS DISTINCT FROM NEW.source_issuer
                OR NEW.source_expires_at IS NULL OR NEW.source_expires_at <= observed) THEN
            RAISE EXCEPTION 'Ineligible authentication source tuple' USING ERRCODE = '23514';
        END IF;
    END IF;
    RETURN NEW;
END;
$$;

CREATE FUNCTION public.hidra_identity_session_isolation_guard()
RETURNS trigger LANGUAGE plpgsql VOLATILE SECURITY INVOKER
SET search_path = pg_catalog, public
AS $$
BEGIN
    IF current_setting('transaction_isolation') <> 'read committed' THEN
        RAISE EXCEPTION 'Potentially bound session mutation requires READ COMMITTED' USING ERRCODE = '23514';
    END IF;
    RETURN NULL;
END;
$$;

CREATE FUNCTION public.hidra_identity_bound_session_guard()
RETURNS trigger LANGUAGE plpgsql VOLATILE SECURITY INVOKER
SET search_path = pg_catalog, public
AS $$
BEGIN
    IF TG_OP = 'TRUNCATE' THEN
        RAISE EXCEPTION 'Authentication session TRUNCATE is unsupported' USING ERRCODE = '23514';
    END IF;
    -- DML already owns the session row; refresh existence after any row-lock wait.
    IF NOT EXISTS (SELECT 1 FROM public.hidra_identity_operation_binding WHERE session_id = OLD.id) THEN
        IF TG_OP = 'DELETE' THEN RETURN OLD; ELSE RETURN NEW; END IF;
    END IF;
    IF TG_OP = 'DELETE' THEN
        RAISE EXCEPTION 'Bound authentication session deletion is unsupported' USING ERRCODE = '23514';
    END IF;
    IF (to_jsonb(NEW) - ARRAY['last_seen_at','status','ended_at']) IS DISTINCT FROM
       (to_jsonb(OLD) - ARRAY['last_seen_at','status','ended_at']) THEN
        RAISE EXCEPTION 'Bound authentication session tuple is immutable' USING ERRCODE = '23514';
    END IF;
    IF OLD.status = 'ACTIVE' THEN
        IF NOT ((NEW.status = 'ACTIVE' AND NEW.ended_at IS NULL)
                OR (NEW.status IN ('EXPIRED','REVOKED','LOGGED_OUT') AND NEW.ended_at IS NOT NULL
                    AND isfinite(NEW.ended_at) AND NEW.ended_at >= OLD.started_at)) THEN
            RAISE EXCEPTION 'Invalid bound authentication termination' USING ERRCODE = '23514';
        END IF;
    ELSIF NEW.status IS DISTINCT FROM OLD.status OR NEW.ended_at IS DISTINCT FROM OLD.ended_at THEN
        RAISE EXCEPTION 'Bound authentication terminal state is immutable' USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_p25_binding_insert BEFORE INSERT ON public.hidra_identity_operation_binding
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_operation_binding_guard();
CREATE TRIGGER trg_p25_binding_update BEFORE UPDATE ON public.hidra_identity_operation_binding
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_operation_binding_guard();
CREATE TRIGGER trg_p25_binding_delete BEFORE DELETE ON public.hidra_identity_operation_binding
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_operation_binding_guard();
CREATE TRIGGER trg_p25_binding_truncate BEFORE TRUNCATE ON public.hidra_identity_operation_binding
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_operation_binding_guard();
CREATE TRIGGER trg_p25_session_isolation BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_identity_login_session
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_session_isolation_guard();
CREATE TRIGGER trg_p25_bound_session_update BEFORE UPDATE ON public.hidra_identity_login_session
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_bound_session_guard();
CREATE TRIGGER trg_p25_bound_session_delete BEFORE DELETE ON public.hidra_identity_login_session
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_bound_session_guard();
CREATE TRIGGER trg_p25_bound_session_truncate BEFORE TRUNCATE ON public.hidra_identity_login_session
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_bound_session_guard();
