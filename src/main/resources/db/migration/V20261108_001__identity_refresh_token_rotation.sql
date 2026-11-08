-- HAPI-929: forward-only, LOCAL-only refresh families with fixed absolute expiry.
-- Never store a raw refresh token or JWT, never synthesize legacy refresh grants.
CREATE TABLE public.hidra_identity_refresh_family (
    id varchar(36) PRIMARY KEY,
    user_id varchar(80) NOT NULL REFERENCES public.hidra_identity_user(id),
    provider_id varchar(80) NOT NULL REFERENCES public.hidra_identity_provider(id),
    credential_id varchar(80) NOT NULL REFERENCES public.hidra_identity_local_credential(id),
    provider_generation bigint NOT NULL CHECK(provider_generation > 0),
    credential_generation bigint NOT NULL CHECK(credential_generation > 0),
    rights_digest bytea NOT NULL CHECK(octet_length(rights_digest) = 32),
    created_at timestamptz NOT NULL,
    deadline timestamptz NOT NULL,
    revoked_at timestamptz,
    CONSTRAINT ck_hidra_identity_refresh_family_uuid
        CHECK (id ~ '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$'),
    CONSTRAINT ck_hidra_identity_refresh_family_interval CHECK (
        isfinite(created_at) AND isfinite(deadline) AND created_at < deadline
        AND deadline <= created_at + interval '720 hours'
        AND (revoked_at IS NULL OR (isfinite(revoked_at) AND revoked_at >= created_at)))
);
CREATE INDEX ix_hidra_identity_refresh_family_user ON public.hidra_identity_refresh_family(user_id)
 WHERE revoked_at IS NULL;

CREATE TABLE public.hidra_identity_refresh_token (
    family_id varchar(36) NOT NULL REFERENCES public.hidra_identity_refresh_family(id),
    generation bigint NOT NULL CHECK(generation >= 0),
    digest bytea NOT NULL CHECK(octet_length(digest) = 32),
    binding_id varchar(36) NOT NULL REFERENCES public.hidra_identity_operation_binding(binding_id),
    native_issuer text NOT NULL,
    native_jti varchar(36) NOT NULL,
    session_id varchar(80) NOT NULL REFERENCES public.hidra_identity_login_session(id),
    issued_at timestamptz NOT NULL,
    consumed_at timestamptz,
    CONSTRAINT pk_hidra_identity_refresh_token PRIMARY KEY(family_id, generation),
    CONSTRAINT uq_hidra_identity_refresh_token_digest UNIQUE(digest),
    CONSTRAINT uq_hidra_identity_refresh_token_session UNIQUE(session_id),
    CONSTRAINT uq_hidra_identity_refresh_token_binding UNIQUE(binding_id),
    CONSTRAINT ck_hidra_identity_refresh_token_interval CHECK (
       isfinite(issued_at) AND (consumed_at IS NULL OR
       (isfinite(consumed_at) AND consumed_at >= issued_at)))
);
CREATE INDEX ix_hidra_identity_refresh_token_family ON public.hidra_identity_refresh_token
    (family_id,generation DESC);

CREATE FUNCTION public.hidra_identity_refresh_family_immutable()
RETURNS trigger LANGUAGE plpgsql SECURITY INVOKER SET search_path = pg_catalog,public AS $$
BEGIN
    IF TG_OP <> 'UPDATE' OR OLD.revoked_at IS NOT NULL
        OR NEW.revoked_at IS NULL
        OR (to_jsonb(NEW)-'revoked_at') IS DISTINCT FROM (to_jsonb(OLD)-'revoked_at')
    THEN
        RAISE EXCEPTION 'Invalid refresh-family mutation' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER trg_hidra_identity_refresh_family_guard
BEFORE UPDATE OR DELETE ON public.hidra_identity_refresh_family
FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_refresh_family_immutable();

CREATE FUNCTION public.hidra_identity_refresh_token_immutable()
RETURNS trigger LANGUAGE plpgsql SECURITY INVOKER SET search_path=pg_catalog,public AS $$
BEGIN
    IF TG_OP <> 'UPDATE' OR OLD.consumed_at IS NOT NULL
        OR NEW.consumed_at IS NULL
        OR (to_jsonb(NEW)-'consumed_at') IS DISTINCT FROM (to_jsonb(OLD)-'consumed_at')
    THEN
        RAISE EXCEPTION 'Invalid refresh-token mutation' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER trg_hidra_identity_refresh_token_guard
BEFORE UPDATE OR DELETE ON public.hidra_identity_refresh_token
FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_refresh_token_immutable();

REVOKE ALL ON public.hidra_identity_refresh_family,public.hidra_identity_refresh_token FROM PUBLIC;
-- Owner provisions the runtime role explicitly. The local kit uses a separate role named hidra.
DO $local$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_catalog.pg_roles WHERE rolname='hidra') THEN
        GRANT SELECT,INSERT ON public.hidra_identity_refresh_family,
            public.hidra_identity_refresh_token TO hidra;
        GRANT UPDATE(revoked_at) ON public.hidra_identity_refresh_family TO hidra;
        GRANT UPDATE(consumed_at) ON public.hidra_identity_refresh_token TO hidra;
    END IF;
END;
$local$;
