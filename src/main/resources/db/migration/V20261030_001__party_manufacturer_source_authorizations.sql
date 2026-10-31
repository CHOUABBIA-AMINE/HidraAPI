-- HAPI-601 Package B. Fresh-schema, Party-owned, explicit manufacturer-company/model scope.
-- No inferred PartyRelationshipType grants; no synthetic auto-seeding and no PUBLIC privileges.
CREATE TABLE public.hidra_party_manufacturer_source_authorization (
    authorization_id varchar(80) PRIMARY KEY,
    company_party_id varchar(80) NOT NULL REFERENCES public.hidra_party_party(id),
    manufacturer_party_id varchar(80) NOT NULL REFERENCES public.hidra_party_party(id),
    manufacturer_model_reference varchar(240) NOT NULL,
    authorization_status varchar(16) NOT NULL CHECK (authorization_status IN ('ACTIVE','REVOKED','EXPIRED')),
    valid_from timestamptz NOT NULL,
    valid_until timestamptz,
    revoked_at timestamptz,
    generation bigint NOT NULL CHECK (generation>0),
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    CONSTRAINT ck_party_manufacturer_authorization_reference CHECK (
        btrim(authorization_id)=authorization_id AND length(authorization_id)>0
        AND btrim(manufacturer_model_reference)=manufacturer_model_reference
        AND length(manufacturer_model_reference)>0
        AND (valid_until IS NULL OR valid_until>valid_from)
        AND updated_at>=created_at
        AND (authorization_status='REVOKED')=(revoked_at IS NOT NULL)
        AND (revoked_at IS NULL OR revoked_at>=created_at)
    )
);
CREATE UNIQUE INDEX uq_party_manufacturer_active_company_model
    ON public.hidra_party_manufacturer_source_authorization
    (company_party_id, manufacturer_party_id, manufacturer_model_reference)
    WHERE authorization_status='ACTIVE' AND revoked_at IS NULL;

CREATE FUNCTION public.hidra_party_manufacturer_authorization_guard()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF TG_OP='DELETE' THEN
       RAISE EXCEPTION 'Manufacturer authorization deletion prohibited' USING ERRCODE='42501';
    END IF;
    IF TG_OP='UPDATE' THEN
        IF (NEW.authorization_id,NEW.company_party_id,NEW.manufacturer_party_id,
            NEW.manufacturer_model_reference,NEW.valid_from,NEW.created_at)
           IS DISTINCT FROM
           (OLD.authorization_id,OLD.company_party_id,OLD.manufacturer_party_id,
            OLD.manufacturer_model_reference,OLD.valid_from,OLD.created_at)
           OR NEW.generation<=OLD.generation
           OR OLD.authorization_status<>'ACTIVE'
           OR (NEW.authorization_status='ACTIVE' AND NEW.revoked_at IS NOT NULL)
        THEN RAISE EXCEPTION 'Manufacturer authorization identity/history immutable' USING ERRCODE='42501'; END IF;
    END IF;
    RETURN NEW;
END;
$body$;
CREATE TRIGGER tr_party_manufacturer_authorization_guard
BEFORE UPDATE OR DELETE ON public.hidra_party_manufacturer_source_authorization
FOR EACH ROW EXECUTE FUNCTION public.hidra_party_manufacturer_authorization_guard();

-- The restricted publisher may execute this only after a separate explicit DBA grant;
-- the function obtains fixed Party owner locks in the same original transaction.
CREATE FUNCTION public.hidra_party_manufacturer_authorization_lock()
RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF pg_catalog.current_setting('transaction_isolation') <> 'read committed'
       OR pg_catalog.current_setting('transaction_read_only') <> 'off'
       OR pg_catalog.current_setting('session_replication_role') <> 'origin'
       OR pg_catalog.pg_backend_pid() < 1 OR pg_catalog.txid_current() < 1
    THEN RAISE EXCEPTION 'Manufacturer Party scope lock unavailable' USING ERRCODE='42501'; END IF;
    LOCK TABLE public.hidra_party_party,
        public.hidra_party_eligibility_generation,
        public.hidra_party_role,
        public.hidra_party_role_assignment,
        public.hidra_party_manufacturer_profile,
        public.hidra_party_manufacturer_source_authorization
    IN SHARE ROW EXCLUSIVE MODE NOWAIT;
END;
$body$;
REVOKE ALL ON FUNCTION public.hidra_party_manufacturer_authorization_lock() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_party_manufacturer_authorization_guard() FROM PUBLIC;
REVOKE ALL ON public.hidra_party_manufacturer_source_authorization FROM PUBLIC;
-- No password, role, publisher grant, historical Party data repair or production backfill here.
