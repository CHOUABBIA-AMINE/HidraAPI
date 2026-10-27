-- HAPI-595B. Supplied authoring only; no operational seed or authority grant.
CREATE TABLE public.hidra_custody_supplied_fluid_property_draft (
 product_draft_id uuid PRIMARY KEY REFERENCES public.hidra_custody_product_engineering_draft(id),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp()
);
CREATE TABLE public.hidra_custody_supplied_fluid_property_version (
 product_draft_id uuid NOT NULL REFERENCES public.hidra_custody_supplied_fluid_property_draft(product_draft_id),
 version bigint NOT NULL CHECK(version>0),
 request_id uuid NOT NULL,
 request_sha256 varchar(64) NOT NULL CHECK(request_sha256 ~ '^[0-9a-f]{64}$'),
 expected_version bigint NOT NULL CHECK(expected_version>=0 AND expected_version=version-1),
 product_metadata_version bigint NOT NULL CHECK(product_metadata_version>0),
 classification varchar(6) NOT NULL CHECK(classification IN ('GAS','LIQUID')),
 payload_format varchar(40) NOT NULL CHECK(payload_format='HIDRA_SUPPLIED_FLUID_V1'),
 canonical_payload bytea NOT NULL CHECK(octet_length(canonical_payload) BETWEEN 18 AND 1048576 AND substring(canonical_payload FROM 1 FOR 4)=int4send(1)),
 sha256 varchar(64) NOT NULL CHECK(sha256=encode(sha256(canonical_payload),'hex')),
 predecessor_sha256 varchar(64),
 actor_binding_id uuid NOT NULL,
 actor_session_id uuid NOT NULL,
 actor_user_id varchar(80) NOT NULL CHECK(length(actor_user_id)>0 AND actor_user_id=btrim(actor_user_id)),
 party_generation bigint NOT NULL CHECK(party_generation>0),
 sampled_at timestamptz NOT NULL,
 valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at),
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),
 PRIMARY KEY(product_draft_id,version),
 UNIQUE(product_draft_id,request_id),
 FOREIGN KEY(product_draft_id,product_metadata_version) REFERENCES public.hidra_custody_product_engineering_draft_version(draft_id,version),
 CHECK((version=1 AND predecessor_sha256 IS NULL) OR (version>1 AND predecessor_sha256 IS NOT NULL AND predecessor_sha256 ~ '^[0-9a-f]{64}$'))
);
CREATE FUNCTION public.hidra_custody_supplied_fluid_immutable() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
 RAISE EXCEPTION 'Immutable supplied fluid storage' USING ERRCODE='23514';
END;
$guard$;
CREATE FUNCTION public.hidra_custody_supplied_fluid_next_version() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
DECLARE last_version bigint; last_digest text; metadata_version bigint; metadata_classification text; observed timestamptz;
BEGIN
 PERFORM product_draft_id FROM public.hidra_custody_supplied_fluid_property_draft WHERE product_draft_id=NEW.product_draft_id FOR UPDATE;
 IF NOT FOUND THEN RAISE EXCEPTION 'Missing supplied fluid root' USING ERRCODE='23503'; END IF;
 SELECT version,sha256 INTO last_version,last_digest FROM public.hidra_custody_supplied_fluid_property_version
 WHERE product_draft_id=NEW.product_draft_id ORDER BY version DESC LIMIT 1;
 IF (last_version IS NULL AND (NEW.version<>1 OR NEW.expected_version<>0 OR NEW.predecessor_sha256 IS NOT NULL))
    OR (last_version IS NOT NULL AND (last_version=9223372036854775807::bigint OR NEW.expected_version<>last_version
        OR NEW.version<>last_version+1 OR NEW.predecessor_sha256 IS DISTINCT FROM last_digest)) THEN
  RAISE EXCEPTION 'Stale supplied fluid version' USING ERRCODE='23514';
 END IF;
 SELECT version,classification INTO metadata_version,metadata_classification FROM public.hidra_custody_product_engineering_draft_version
 WHERE draft_id=NEW.product_draft_id ORDER BY version DESC LIMIT 1;
 IF metadata_version IS DISTINCT FROM NEW.product_metadata_version OR metadata_classification IS DISTINCT FROM NEW.classification THEN
  RAISE EXCEPTION 'Stale product metadata binding' USING ERRCODE='23514';
 END IF;
 observed:=clock_timestamp();
 IF NEW.backend_pid<>pg_backend_pid() OR NEW.transaction_id<>txid_current() OR observed<NEW.sampled_at OR observed>=NEW.valid_until THEN
  RAISE EXCEPTION 'Invalid supplied fluid origin' USING ERRCODE='23514';
 END IF;
 NEW.created_at:=observed;
 RETURN NEW;
END;
$guard$;
CREATE TRIGGER custody_supplied_fluid_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_supplied_fluid_property_draft
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_supplied_fluid_immutable();
CREATE TRIGGER custody_supplied_fluid_no_truncate BEFORE TRUNCATE ON public.hidra_custody_supplied_fluid_property_draft
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_supplied_fluid_immutable();
CREATE TRIGGER custody_supplied_fluid_version_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_supplied_fluid_property_version
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_supplied_fluid_immutable();
CREATE TRIGGER custody_supplied_fluid_version_no_truncate BEFORE TRUNCATE ON public.hidra_custody_supplied_fluid_property_version
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_supplied_fluid_immutable();
CREATE TRIGGER custody_supplied_fluid_next_version BEFORE INSERT ON public.hidra_custody_supplied_fluid_property_version
 FOR EACH ROW EXECUTE FUNCTION public.hidra_custody_supplied_fluid_next_version();
REVOKE ALL ON public.hidra_custody_supplied_fluid_property_draft,public.hidra_custody_supplied_fluid_property_version FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_custody_supplied_fluid_immutable(),public.hidra_custody_supplied_fluid_next_version() FROM PUBLIC;
