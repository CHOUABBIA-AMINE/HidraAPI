-- HAPI-594B. Empty admission policy; no products, role grants or operational fixtures.
-- Publisher needs SELECT/INSERT/UPDATE for the reviewed relation fences. Immutable
-- statement triggers reject UPDATE/DELETE/TRUNCATE even for that publisher.
CREATE TABLE public.hidra_custody_engineering_product_policy (
 catalog_product_id varchar(80) PRIMARY KEY REFERENCES public.hidra_custody_catalog_entry(id),
 active boolean NOT NULL,
 updated_at timestamptz NOT NULL DEFAULT clock_timestamp()
);
CREATE TABLE public.hidra_custody_product_engineering_draft (
 id uuid PRIMARY KEY,
 company_party_id varchar(80) COLLATE "C" NOT NULL CHECK ((company_party_id IS NULL OR (char_length(company_party_id) BETWEEN 1 AND 80 AND company_party_id=btrim(company_party_id,E' \t\n\r\f\013'||U&'\001c\001d\001e\001f\1680\2000\2001\2002\2003\2004\2005\2006\2008\2009\200a\2028\2029\205f\3000')))),
 local_code varchar(80) COLLATE "C" NOT NULL CHECK ((local_code IS NULL OR (char_length(local_code) BETWEEN 1 AND 80 AND local_code=btrim(local_code,E' \t\n\r\f\013'||U&'\001c\001d\001e\001f\1680\2000\2001\2002\2003\2004\2005\2006\2008\2009\200a\2028\2029\205f\3000')))),
 catalog_product_id varchar(80) NOT NULL REFERENCES public.hidra_custody_catalog_entry(id) CHECK ((catalog_product_id IS NULL OR (char_length(catalog_product_id) BETWEEN 1 AND 80 AND catalog_product_id=btrim(catalog_product_id,E' \t\n\r\f\013'||U&'\001c\001d\001e\001f\1680\2000\2001\2002\2003\2004\2005\2006\2008\2009\200a\2028\2029\205f\3000')))),
 creation_request_id uuid NOT NULL,
 request_sha256 varchar(64) NOT NULL CHECK (request_sha256 ~ '^[0-9a-f]{64}$'),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),
 UNIQUE(company_party_id,local_code),
 UNIQUE(company_party_id,creation_request_id)
);
CREATE TABLE public.hidra_custody_product_engineering_draft_version (
 draft_id uuid NOT NULL REFERENCES public.hidra_custody_product_engineering_draft(id),
 version bigint NOT NULL CHECK (version>0),
 payload_format varchar(40) NOT NULL CHECK (payload_format='HIDRA_PRODUCT_METADATA_V1'),
 canonical_payload bytea NOT NULL CHECK (octet_length(canonical_payload) BETWEEN 20 AND 32768),
 sha256 varchar(64) NOT NULL CHECK (sha256=encode(sha256(canonical_payload),'hex')),
 predecessor_sha256 varchar(64),
 name varchar(240) CHECK ((name IS NULL OR (char_length(name) BETWEEN 1 AND 240 AND name=btrim(name,E' \t\n\r\f\013'||U&'\001c\001d\001e\001f\1680\2000\2001\2002\2003\2004\2005\2006\2008\2009\200a\2028\2029\205f\3000')))),
 description varchar(4000) CHECK ((description IS NULL OR (char_length(description) BETWEEN 1 AND 4000 AND description=btrim(description,E' \t\n\r\f\013'||U&'\001c\001d\001e\001f\1680\2000\2001\2002\2003\2004\2005\2006\2008\2009\200a\2028\2029\205f\3000')))),
 evidence_reference varchar(1000) CHECK ((evidence_reference IS NULL OR (char_length(evidence_reference) BETWEEN 1 AND 1000 AND evidence_reference=btrim(evidence_reference,E' \t\n\r\f\013'||U&'\001c\001d\001e\001f\1680\2000\2001\2002\2003\2004\2005\2006\2008\2009\200a\2028\2029\205f\3000')))),
 classification varchar(6) CHECK (classification IN ('GAS','LIQUID')),
 actor_binding_id uuid NOT NULL,
 actor_session_id uuid NOT NULL,
 actor_user_id varchar(80) NOT NULL CHECK ((actor_user_id IS NULL OR (char_length(actor_user_id) BETWEEN 1 AND 80 AND actor_user_id=btrim(actor_user_id,E' \t\n\r\f\013'||U&'\001c\001d\001e\001f\1680\2000\2001\2002\2003\2004\2005\2006\2008\2009\200a\2028\2029\205f\3000')))),
 party_generation bigint NOT NULL CHECK (party_generation>0),
 sampled_at timestamptz NOT NULL,
 valid_until timestamptz NOT NULL CHECK (valid_until>sampled_at),
 backend_pid integer NOT NULL CHECK (backend_pid>0),
 transaction_id bigint NOT NULL CHECK (transaction_id>0),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),
 PRIMARY KEY(draft_id,version),
 CHECK ((version=1 AND predecessor_sha256 IS NULL) OR
        (version>1 AND predecessor_sha256 IS NOT NULL AND predecessor_sha256 ~ '^[0-9a-f]{64}$')),
 CHECK (canonical_payload=int4send(1) || CASE WHEN name IS NULL THEN int4send(-1) ELSE int4send(octet_length(convert_to(name,'UTF8'))) || convert_to(name,'UTF8') END || CASE WHEN description IS NULL THEN int4send(-1) ELSE int4send(octet_length(convert_to(description,'UTF8'))) || convert_to(description,'UTF8') END || CASE WHEN evidence_reference IS NULL THEN int4send(-1) ELSE int4send(octet_length(convert_to(evidence_reference,'UTF8'))) || convert_to(evidence_reference,'UTF8') END || CASE WHEN classification IS NULL THEN int4send(-1) ELSE int4send(octet_length(convert_to(classification,'UTF8'))) || convert_to(classification,'UTF8') END)
);
CREATE FUNCTION public.hidra_custody_product_draft_immutable() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
 RAISE EXCEPTION 'Immutable product draft storage' USING ERRCODE='23514';
END;
$guard$;
CREATE FUNCTION public.hidra_custody_product_draft_next_version() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
DECLARE last_version bigint; last_digest text; observed timestamptz;
BEGIN
 PERFORM id FROM public.hidra_custody_product_engineering_draft WHERE id=NEW.draft_id FOR UPDATE;
 IF NOT FOUND THEN RAISE EXCEPTION 'Missing product draft' USING ERRCODE='23503'; END IF;
 SELECT version,sha256 INTO last_version,last_digest
 FROM public.hidra_custody_product_engineering_draft_version WHERE draft_id=NEW.draft_id ORDER BY version DESC LIMIT 1;
 IF (last_version IS NULL AND (NEW.version<>1 OR NEW.predecessor_sha256 IS NOT NULL))
    OR (last_version IS NOT NULL AND (last_version=9223372036854775807::bigint
        OR NEW.version<>last_version+1 OR NEW.predecessor_sha256 IS DISTINCT FROM last_digest)) THEN
  RAISE EXCEPTION 'Stale product draft version' USING ERRCODE='23514';
 END IF;
 observed:=clock_timestamp();
 IF NEW.backend_pid<>pg_backend_pid() OR NEW.transaction_id<>txid_current()
    OR observed<NEW.sampled_at OR observed>=NEW.valid_until THEN
  RAISE EXCEPTION 'Invalid product draft origin' USING ERRCODE='23514';
 END IF;
 NEW.created_at:=observed;
 RETURN NEW;
END;
$guard$;
CREATE TRIGGER custody_product_draft_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_product_engineering_draft
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_product_draft_immutable();
CREATE TRIGGER custody_product_draft_no_truncate BEFORE TRUNCATE ON public.hidra_custody_product_engineering_draft
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_product_draft_immutable();
REVOKE ALL ON public.hidra_custody_product_engineering_draft FROM PUBLIC;
CREATE TRIGGER custody_product_draft_version_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_product_engineering_draft_version
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_product_draft_immutable();
CREATE TRIGGER custody_product_draft_version_no_truncate BEFORE TRUNCATE ON public.hidra_custody_product_engineering_draft_version
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_product_draft_immutable();
REVOKE ALL ON public.hidra_custody_product_engineering_draft_version FROM PUBLIC;
CREATE TRIGGER custody_product_next_version BEFORE INSERT ON public.hidra_custody_product_engineering_draft_version
 FOR EACH ROW EXECUTE FUNCTION public.hidra_custody_product_draft_next_version();
REVOKE ALL ON public.hidra_custody_engineering_product_policy FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_custody_product_draft_immutable(),public.hidra_custody_product_draft_next_version() FROM PUBLIC;
