-- HPR-P25-047: database-owned provider and external-link security generations.
-- Initial generation does not establish historical token/session provenance.
-- Ordinary writers must retain identity; DDL/replication bypass is outside this boundary.
ALTER TABLE public.hidra_identity_provider
    ADD COLUMN security_version bigint NOT NULL DEFAULT 1,
    ADD CONSTRAINT ck_p25_provider_security_version CHECK (security_version > 0);

COMMENT ON COLUMN public.hidra_identity_provider.security_version IS
    'Database-owned monotonic security generation; not token, session or permission authority.';

CREATE FUNCTION public.hidra_identity_provider_security_guard()
RETURNS trigger
LANGUAGE plpgsql
SECURITY INVOKER
SET search_path = pg_catalog, public
AS $$
BEGIN
    IF TG_OP IN ('DELETE', 'TRUNCATE') THEN
        RAISE EXCEPTION 'provider identity must be retained; disable instead of deleting or truncating'
            USING ERRCODE = '23514';
    END IF;
    IF TG_OP = 'INSERT' THEN
        IF NEW.security_version IS DISTINCT FROM 1::bigint THEN
            RAISE EXCEPTION 'Initial provider security generation must be 1'
                USING ERRCODE = '23514';
        END IF;
        RETURN NEW;
    END IF;
    IF NEW.id IS DISTINCT FROM OLD.id
        OR NEW.created_at IS DISTINCT FROM OLD.created_at THEN
        RAISE EXCEPTION 'provider identity and creation evidence are immutable'
            USING ERRCODE = '23514';
    END IF;
    IF NEW.security_version IS DISTINCT FROM OLD.security_version THEN
        RAISE EXCEPTION 'provider security generation is database-owned'
            USING ERRCODE = '23514';
    END IF;
    IF NEW.code IS DISTINCT FROM OLD.code
        OR NEW.provider_type IS DISTINCT FROM OLD.provider_type
        OR NEW.issuer_uri IS DISTINCT FROM OLD.issuer_uri
        OR NEW.authorization_endpoint IS DISTINCT FROM OLD.authorization_endpoint
        OR NEW.token_endpoint IS DISTINCT FROM OLD.token_endpoint
        OR NEW.jwks_uri IS DISTINCT FROM OLD.jwks_uri
        OR NEW.directory_base_dn IS DISTINCT FROM OLD.directory_base_dn
        OR NEW.user_search_base IS DISTINCT FROM OLD.user_search_base
        OR NEW.group_search_base IS DISTINCT FROM OLD.group_search_base
        OR NEW.username_attribute IS DISTINCT FROM OLD.username_attribute
        OR NEW.email_attribute IS DISTINCT FROM OLD.email_attribute
        OR NEW.display_name_attribute IS DISTINCT FROM OLD.display_name_attribute
        OR NEW.external_id_attribute IS DISTINCT FROM OLD.external_id_attribute
        OR NEW.group_membership_attribute IS DISTINCT FROM OLD.group_membership_attribute
        OR NEW.sync_enabled IS DISTINCT FROM OLD.sync_enabled
        OR NEW.just_in_time_provisioning_enabled IS DISTINCT FROM OLD.just_in_time_provisioning_enabled
        OR NEW.status IS DISTINCT FROM OLD.status
        OR NEW.metadata IS DISTINCT FROM OLD.metadata
        OR NEW.secret_reference IS DISTINCT FROM OLD.secret_reference THEN
        IF OLD.security_version = 9223372036854775807::bigint THEN
            RAISE EXCEPTION 'provider security generation is exhausted'
                USING ERRCODE = '22003';
        END IF;
        NEW.security_version := OLD.security_version + 1;
    ELSE
        NEW.security_version := OLD.security_version;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_p25_provider_security_write
    BEFORE INSERT OR UPDATE ON public.hidra_identity_provider
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_provider_security_guard();
CREATE TRIGGER trg_p25_provider_security_delete
    BEFORE DELETE ON public.hidra_identity_provider
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_provider_security_guard();
CREATE TRIGGER trg_p25_provider_security_truncate
    BEFORE TRUNCATE ON public.hidra_identity_provider
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_provider_security_guard();

ALTER TABLE public.hidra_identity_external_identity
    ADD COLUMN security_version bigint NOT NULL DEFAULT 1,
    ADD CONSTRAINT ck_p25_external_identity_security_version CHECK (security_version > 0);

COMMENT ON COLUMN public.hidra_identity_external_identity.security_version IS
    'Database-owned monotonic security generation; not token, session or permission authority.';

CREATE FUNCTION public.hidra_identity_external_identity_security_guard()
RETURNS trigger
LANGUAGE plpgsql
SECURITY INVOKER
SET search_path = pg_catalog, public
AS $$
BEGIN
    IF TG_OP IN ('DELETE', 'TRUNCATE') THEN
        RAISE EXCEPTION 'external_identity identity must be retained; disable instead of deleting or truncating'
            USING ERRCODE = '23514';
    END IF;
    IF TG_OP = 'INSERT' THEN
        IF NEW.security_version IS DISTINCT FROM 1::bigint THEN
            RAISE EXCEPTION 'Initial external_identity security generation must be 1'
                USING ERRCODE = '23514';
        END IF;
        RETURN NEW;
    END IF;
    IF NEW.id IS DISTINCT FROM OLD.id
        OR NEW.created_at IS DISTINCT FROM OLD.created_at THEN
        RAISE EXCEPTION 'external_identity identity and creation evidence are immutable'
            USING ERRCODE = '23514';
    END IF;
    IF NEW.security_version IS DISTINCT FROM OLD.security_version THEN
        RAISE EXCEPTION 'external_identity security generation is database-owned'
            USING ERRCODE = '23514';
    END IF;
    IF NEW.user_id IS DISTINCT FROM OLD.user_id
        OR NEW.identity_provider_id IS DISTINCT FROM OLD.identity_provider_id
        OR NEW.external_subject IS DISTINCT FROM OLD.external_subject
        OR NEW.external_immutable_id IS DISTINCT FROM OLD.external_immutable_id
        OR NEW.external_username IS DISTINCT FROM OLD.external_username
        OR NEW.external_distinguished_name IS DISTINCT FROM OLD.external_distinguished_name
        OR NEW.status IS DISTINCT FROM OLD.status THEN
        IF OLD.security_version = 9223372036854775807::bigint THEN
            RAISE EXCEPTION 'external_identity security generation is exhausted'
                USING ERRCODE = '22003';
        END IF;
        NEW.security_version := OLD.security_version + 1;
    ELSE
        NEW.security_version := OLD.security_version;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_p25_external_identity_security_write
    BEFORE INSERT OR UPDATE ON public.hidra_identity_external_identity
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_external_identity_security_guard();
CREATE TRIGGER trg_p25_external_identity_security_delete
    BEFORE DELETE ON public.hidra_identity_external_identity
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_external_identity_security_guard();
CREATE TRIGGER trg_p25_external_identity_security_truncate
    BEFORE TRUNCATE ON public.hidra_identity_external_identity
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_external_identity_security_guard();

