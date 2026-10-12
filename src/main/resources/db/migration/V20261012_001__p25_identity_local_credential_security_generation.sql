-- HPR-P25-046: database-owned LOCAL credential security generation.
-- Existing credentials start at 1 for future authentication, not legacy-token proof.
-- Ordinary writers cannot reset generations or delete/recreate credential identity.
-- DDL owners/superusers remain outside this normal-writer enforcement boundary.
ALTER TABLE public.hidra_identity_local_credential
    ADD COLUMN security_version bigint NOT NULL DEFAULT 1,
    ADD CONSTRAINT ck_p25_local_credential_security_version CHECK (security_version > 0);

COMMENT ON COLUMN public.hidra_identity_local_credential.security_version IS
    'Database-owned monotonic LOCAL credential security generation; not token or session authority.';

CREATE FUNCTION public.hidra_identity_local_credential_security_guard()
RETURNS trigger
LANGUAGE plpgsql
SECURITY INVOKER
SET search_path = pg_catalog, public
AS $$
BEGIN
    IF TG_OP IN ('DELETE', 'TRUNCATE') THEN
        RAISE EXCEPTION 'LOCAL credential identity must be retained; disable instead of deleting or truncating'
            USING ERRCODE = '23514';
    END IF;
    IF TG_OP = 'INSERT' THEN
        IF NEW.security_version IS DISTINCT FROM 1::bigint THEN
            RAISE EXCEPTION 'Initial LOCAL credential security generation must be 1'
                USING ERRCODE = '23514';
        END IF;
        RETURN NEW;
    END IF;
    IF NEW.id IS DISTINCT FROM OLD.id
        OR NEW.user_id IS DISTINCT FROM OLD.user_id
        OR NEW.created_at IS DISTINCT FROM OLD.created_at THEN
        RAISE EXCEPTION 'LOCAL credential identity and creation evidence are immutable'
            USING ERRCODE = '23514';
    END IF;
    IF NEW.security_version IS DISTINCT FROM OLD.security_version THEN
        RAISE EXCEPTION 'LOCAL credential security generation is database-owned'
            USING ERRCODE = '23514';
    END IF;
    IF NEW.password_hash IS DISTINCT FROM OLD.password_hash
        OR NEW.credential_status IS DISTINCT FROM OLD.credential_status
        OR NEW.password_changed_at IS DISTINCT FROM OLD.password_changed_at THEN
        IF OLD.security_version = 9223372036854775807::bigint THEN
            RAISE EXCEPTION 'LOCAL credential security generation is exhausted'
                USING ERRCODE = '22003';
        END IF;
        NEW.security_version := OLD.security_version + 1;
    ELSE
        NEW.security_version := OLD.security_version;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_p25_local_credential_security_write
    BEFORE INSERT OR UPDATE ON public.hidra_identity_local_credential
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_local_credential_security_guard();
CREATE TRIGGER trg_p25_local_credential_security_delete
    BEFORE DELETE ON public.hidra_identity_local_credential
    FOR EACH ROW EXECUTE FUNCTION public.hidra_identity_local_credential_security_guard();
CREATE TRIGGER trg_p25_local_credential_security_truncate
    BEFORE TRUNCATE ON public.hidra_identity_local_credential
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_identity_local_credential_security_guard();
