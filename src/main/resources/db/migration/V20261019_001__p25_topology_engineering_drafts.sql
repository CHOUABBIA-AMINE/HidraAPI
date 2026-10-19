-- HAPI-587; synthetic/fresh-schema development. No existing migration is rewritten.
-- Owner-approved privilege correction: publisher SELECT/INSERT/UPDATE; UPDATE is
-- needed for SHARE ROW EXCLUSIVE locks and rejected by statement-level immutable triggers.
-- Grant no privileges to PUBLIC, no business grants, and no hardcoded deployment roles.
CREATE TABLE public.hidra_topology_engineering_context (
 id uuid PRIMARY KEY,
 company_party_id varchar(80) NOT NULL CHECK (company_party_id=btrim(company_party_id) AND length(company_party_id)>0),
 pipeline_system_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline_system(id),
 UNIQUE(company_party_id,pipeline_system_id)
);
CREATE TABLE public.hidra_topology_engineering_draft (
 id uuid PRIMARY KEY,
 context_id uuid NOT NULL REFERENCES public.hidra_topology_engineering_context(id),
 creation_request_id uuid NOT NULL,
 request_sha256 varchar(64) NOT NULL CHECK (request_sha256 ~ '^[0-9a-f]{64}$'),
 UNIQUE(context_id,creation_request_id)
);
CREATE TABLE public.hidra_topology_engineering_draft_version (
 draft_id uuid NOT NULL REFERENCES public.hidra_topology_engineering_draft(id),
 version bigint NOT NULL CHECK (version>0),
 payload_format varchar(40) NOT NULL CHECK (payload_format='HIDRA_ENGINEERING_METADATA_V1'),
 canonical_payload bytea NOT NULL CHECK (octet_length(canonical_payload) BETWEEN 16 AND 32768),
 sha256 varchar(64) NOT NULL CHECK (sha256=encode(sha256(canonical_payload),'hex')),
 predecessor_sha256 varchar(64),
 actor_binding_id uuid NOT NULL,
 actor_session_id uuid NOT NULL,
 actor_user_id varchar(80) NOT NULL CHECK (length(btrim(actor_user_id))>0),
 party_generation bigint NOT NULL CHECK (party_generation>0),
 sampled_at timestamptz NOT NULL,
 valid_until timestamptz NOT NULL CHECK (valid_until>sampled_at),
 backend_pid integer NOT NULL CHECK (backend_pid>0),
 transaction_id bigint NOT NULL CHECK (transaction_id>0),
 PRIMARY KEY(draft_id,version),
 CHECK ((version=1 AND predecessor_sha256 IS NULL) OR (version>1 AND predecessor_sha256 ~ '^[0-9a-f]{64}$'))
);
CREATE FUNCTION public.hidra_p25_draft_immutable() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
 RAISE EXCEPTION 'Immutable engineering draft storage' USING ERRCODE='23514';
END;
$guard$;
CREATE FUNCTION public.hidra_p25_draft_next_version() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
DECLARE last_version bigint; last_digest text;
BEGIN
 PERFORM id FROM public.hidra_topology_engineering_draft WHERE id=NEW.draft_id FOR UPDATE;
 IF NOT FOUND THEN RAISE EXCEPTION 'Missing draft' USING ERRCODE='23503'; END IF;
 SELECT version,sha256 INTO last_version,last_digest
 FROM public.hidra_topology_engineering_draft_version WHERE draft_id=NEW.draft_id ORDER BY version DESC LIMIT 1;
 IF (last_version IS NULL AND (NEW.version<>1 OR NEW.predecessor_sha256 IS NOT NULL))
    OR (last_version IS NOT NULL AND (last_version=9223372036854775807::bigint
        OR NEW.version<>last_version+1 OR NEW.predecessor_sha256 IS DISTINCT FROM last_digest)) THEN
  RAISE EXCEPTION 'Stale engineering draft version' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END;
$guard$;
CREATE TRIGGER p25_engineering_context_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_context
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_engineering_context_no_truncate BEFORE TRUNCATE ON public.hidra_topology_engineering_context
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
REVOKE ALL ON public.hidra_topology_engineering_context FROM PUBLIC;
CREATE TRIGGER p25_engineering_draft_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_draft
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_engineering_draft_no_truncate BEFORE TRUNCATE ON public.hidra_topology_engineering_draft
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
REVOKE ALL ON public.hidra_topology_engineering_draft FROM PUBLIC;
CREATE TRIGGER p25_engineering_draft_version_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_draft_version
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_engineering_draft_version_no_truncate BEFORE TRUNCATE ON public.hidra_topology_engineering_draft_version
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
REVOKE ALL ON public.hidra_topology_engineering_draft_version FROM PUBLIC;
CREATE TRIGGER p25_engineering_next_version BEFORE INSERT ON public.hidra_topology_engineering_draft_version
 FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_draft_next_version();
REVOKE ALL ON FUNCTION public.hidra_p25_draft_immutable(),public.hidra_p25_draft_next_version() FROM PUBLIC;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('topology-engineering-draft-create','topology:engineering-draft:create','topology','ENGINEERING_NETWORK_DRAFT','create',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('topology-engineering-draft-read','topology:engineering-draft:read','topology','ENGINEERING_NETWORK_DRAFT','read',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('topology-engineering-draft-update','topology:engineering-draft:update','topology','ENGINEERING_NETWORK_DRAFT','update',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES ('topology-engineering-draft-validate','topology:engineering-draft:validate','topology','ENGINEERING_NETWORK_DRAFT','validate',false,'ACTIVE',clock_timestamp(),clock_timestamp())
 ON CONFLICT (code) DO NOTHING;
DO $verify$
BEGIN
 IF (SELECT count(*) FROM public.hidra_identity_permission
     WHERE code IN ('topology:engineering-draft:create','topology:engineering-draft:read','topology:engineering-draft:update','topology:engineering-draft:validate')
       AND permission_domain='topology' AND resource_type='ENGINEERING_NETWORK_DRAFT'
       AND code='topology:engineering-draft:'||action AND status='ACTIVE' AND NOT sensitive)<>4 THEN
  RAISE EXCEPTION 'Engineering draft permission catalog mismatch';
 END IF;
END;
$verify$;
