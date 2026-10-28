-- HAPI-596 Package B. Immutable authoring evidence only; no grants or business configuration.
CREATE TABLE public.hidra_custody_product_publication_candidate (
 product_draft_id uuid NOT NULL REFERENCES public.hidra_custody_product_engineering_draft(id),
 company_party_id varchar(80) NOT NULL, catalog_product_id varchar(80) NOT NULL, candidate_digest varchar(64) NOT NULL CHECK(candidate_digest=sha256),
 metadata_version bigint NOT NULL CHECK(metadata_version>0), supplied_version bigint NOT NULL CHECK(supplied_version>0),
 payload_format varchar(40) NOT NULL CHECK(payload_format='HIDRA_PRODUCT_PUBLICATION_V1'),
 canonical_payload bytea NOT NULL CHECK(octet_length(canonical_payload) BETWEEN 8 AND 4194304 AND substring(canonical_payload FROM 1 FOR 8)=int4send(1)||int4send(1)),
 sha256 varchar(64) NOT NULL CHECK(sha256=encode(sha256(canonical_payload),'hex')), 
 actor_binding_id uuid NOT NULL, actor_session_id uuid NOT NULL, actor_user_id varchar(80) NOT NULL,
 party_generation bigint NOT NULL CHECK(party_generation>0),
 sampled_at timestamptz NOT NULL, valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at AND valid_until<=sampled_at+interval '500 milliseconds'),
 backend_pid integer NOT NULL CHECK(backend_pid>0), transaction_id bigint NOT NULL CHECK(transaction_id>0),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),
 PRIMARY KEY(candidate_digest), UNIQUE(product_draft_id,candidate_digest),
 FOREIGN KEY(product_draft_id,metadata_version) REFERENCES public.hidra_custody_product_engineering_draft_version(draft_id,version),
 FOREIGN KEY(product_draft_id,supplied_version) REFERENCES public.hidra_custody_supplied_fluid_property_version(product_draft_id,version)
);
CREATE TABLE public.hidra_custody_product_publication (
 product_draft_id uuid NOT NULL REFERENCES public.hidra_custody_product_engineering_draft(id),
 company_party_id varchar(80) NOT NULL, catalog_product_id varchar(80) NOT NULL, publication_id uuid PRIMARY KEY, candidate_digest varchar(64) NOT NULL,
 payload_format varchar(40) NOT NULL CHECK(payload_format='HIDRA_PRODUCT_PUBLICATION_V1'),
 canonical_payload bytea NOT NULL CHECK(octet_length(canonical_payload) BETWEEN 8 AND 4194304 AND substring(canonical_payload FROM 1 FOR 8)=int4send(1)||int4send(2)),
 sha256 varchar(64) NOT NULL CHECK(sha256=encode(sha256(canonical_payload),'hex')), 
 actor_binding_id uuid NOT NULL, actor_session_id uuid NOT NULL, actor_user_id varchar(80) NOT NULL,
 party_generation bigint NOT NULL CHECK(party_generation>0),
 sampled_at timestamptz NOT NULL, valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at AND valid_until<=sampled_at+interval '500 milliseconds'),
 backend_pid integer NOT NULL CHECK(backend_pid>0), transaction_id bigint NOT NULL CHECK(transaction_id>0),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),
 UNIQUE(product_draft_id,publication_id),
 FOREIGN KEY(product_draft_id,candidate_digest) REFERENCES public.hidra_custody_product_publication_candidate(product_draft_id,candidate_digest)
);
CREATE TABLE public.hidra_custody_product_publication_event (
 product_draft_id uuid NOT NULL REFERENCES public.hidra_custody_product_engineering_draft(id),
 company_party_id varchar(80) NOT NULL, catalog_product_id varchar(80) NOT NULL, event_id uuid PRIMARY KEY, epoch bigint NOT NULL CHECK(epoch>0),
 action varchar(10) NOT NULL CHECK(action IN ('PUBLISH','WITHDRAW','REACTIVATE')),
 prior_publication_id uuid, publication_id uuid NOT NULL, state varchar(9) NOT NULL CHECK(state IN ('ACTIVE','WITHDRAWN')),
 request_id uuid NOT NULL, command_sha256 varchar(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),
 effective_at timestamptz NOT NULL, payload_format varchar(40) NOT NULL CHECK(payload_format='HIDRA_PRODUCT_PUBLICATION_V1'),
 canonical_payload bytea NOT NULL CHECK(octet_length(canonical_payload) BETWEEN 8 AND 4194304 AND substring(canonical_payload FROM 1 FOR 8)=int4send(1)||int4send(3)),
 sha256 varchar(64) NOT NULL CHECK(sha256=encode(sha256(canonical_payload),'hex')), 
 actor_binding_id uuid NOT NULL, actor_session_id uuid NOT NULL, actor_user_id varchar(80) NOT NULL,
 party_generation bigint NOT NULL CHECK(party_generation>0),
 sampled_at timestamptz NOT NULL, valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at AND valid_until<=sampled_at+interval '500 milliseconds'),
 backend_pid integer NOT NULL CHECK(backend_pid>0), transaction_id bigint NOT NULL CHECK(transaction_id>0),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),
 UNIQUE(product_draft_id,epoch), UNIQUE(product_draft_id,request_id), UNIQUE(product_draft_id,event_id),
 FOREIGN KEY(product_draft_id,publication_id) REFERENCES public.hidra_custody_product_publication(product_draft_id,publication_id),
 FOREIGN KEY(product_draft_id,prior_publication_id) REFERENCES public.hidra_custody_product_publication(product_draft_id,publication_id),
 CHECK((epoch=1)=(prior_publication_id IS NULL)), CHECK((action='WITHDRAW')=(state='WITHDRAWN')),
 CHECK((action='PUBLISH' AND publication_id IS DISTINCT FROM prior_publication_id) OR (action<>'PUBLISH' AND publication_id=prior_publication_id))
);
CREATE TABLE public.hidra_custody_product_publication_head (
 product_draft_id uuid NOT NULL REFERENCES public.hidra_custody_product_engineering_draft(id),
 company_party_id varchar(80) NOT NULL, catalog_product_id varchar(80) NOT NULL, publication_id uuid NOT NULL, epoch bigint NOT NULL CHECK(epoch>0),
 state varchar(9) NOT NULL CHECK(state IN ('ACTIVE','WITHDRAWN')),event_id uuid NOT NULL,effective_at timestamptz NOT NULL,
 
 actor_binding_id uuid NOT NULL, actor_session_id uuid NOT NULL, actor_user_id varchar(80) NOT NULL,
 party_generation bigint NOT NULL CHECK(party_generation>0),
 sampled_at timestamptz NOT NULL, valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at AND valid_until<=sampled_at+interval '500 milliseconds'),
 backend_pid integer NOT NULL CHECK(backend_pid>0), transaction_id bigint NOT NULL CHECK(transaction_id>0),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),PRIMARY KEY(product_draft_id),
 FOREIGN KEY(product_draft_id,publication_id) REFERENCES public.hidra_custody_product_publication(product_draft_id,publication_id),
 FOREIGN KEY(product_draft_id,event_id) REFERENCES public.hidra_custody_product_publication_event(product_draft_id,event_id)
);
CREATE TABLE public.hidra_custody_product_publication_request (
 product_draft_id uuid NOT NULL REFERENCES public.hidra_custody_product_engineering_draft(id),
 company_party_id varchar(80) NOT NULL, catalog_product_id varchar(80) NOT NULL, request_id uuid PRIMARY KEY,kind varchar(10) NOT NULL CHECK(kind IN ('PREPARE','PUBLISH','WITHDRAW','REACTIVATE')),
 command_sha256 varchar(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),candidate_digest varchar(64) NOT NULL,event_id uuid,
 payload_format varchar(40) NOT NULL CHECK(payload_format='HIDRA_PRODUCT_PUBLICATION_V1'),
 canonical_payload bytea NOT NULL CHECK(octet_length(canonical_payload) BETWEEN 8 AND 4194304 AND substring(canonical_payload FROM 1 FOR 8)=int4send(1)||int4send(4)),
 sha256 varchar(64) NOT NULL CHECK(sha256=encode(sha256(canonical_payload),'hex')), 
 actor_binding_id uuid NOT NULL, actor_session_id uuid NOT NULL, actor_user_id varchar(80) NOT NULL,
 party_generation bigint NOT NULL CHECK(party_generation>0),
 sampled_at timestamptz NOT NULL, valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at AND valid_until<=sampled_at+interval '500 milliseconds'),
 backend_pid integer NOT NULL CHECK(backend_pid>0), transaction_id bigint NOT NULL CHECK(transaction_id>0),
 created_at timestamptz NOT NULL DEFAULT clock_timestamp(),UNIQUE(product_draft_id,request_id),
 FOREIGN KEY(product_draft_id,candidate_digest) REFERENCES public.hidra_custody_product_publication_candidate(product_draft_id,candidate_digest),
 FOREIGN KEY(product_draft_id,event_id) REFERENCES public.hidra_custody_product_publication_event(product_draft_id,event_id),
 CHECK((kind='PREPARE')=(event_id IS NULL))
);
CREATE FUNCTION public.hidra_custody_publication_immutable() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
 RAISE EXCEPTION 'Immutable product publication evidence' USING ERRCODE='23514';
END;
$guard$;
CREATE FUNCTION public.hidra_custody_publication_admit() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
DECLARE observed timestamptz; previous public.hidra_custody_product_publication_head%ROWTYPE; latest bigint; transition public.hidra_custody_product_publication_event%ROWTYPE;
BEGIN
 observed:=clock_timestamp();
 IF NEW.backend_pid<>pg_backend_pid() OR NEW.transaction_id<>txid_current() OR observed<NEW.sampled_at OR observed>=NEW.valid_until THEN
  RAISE EXCEPTION 'Invalid product publication origin' USING ERRCODE='23514'; END IF;
 PERFORM id FROM public.hidra_custody_product_engineering_draft d WHERE d.id=NEW.product_draft_id
  AND d.company_party_id=NEW.company_party_id AND d.catalog_product_id=NEW.catalog_product_id FOR UPDATE;
 IF NOT FOUND THEN RAISE EXCEPTION 'Product publication ownership mismatch' USING ERRCODE='23514'; END IF;
 IF TG_TABLE_NAME='hidra_custody_product_publication_candidate' THEN
  IF NEW.metadata_version IS DISTINCT FROM (SELECT max(version) FROM public.hidra_custody_product_engineering_draft_version WHERE draft_id=NEW.product_draft_id)
   OR NEW.supplied_version IS DISTINCT FROM (SELECT max(version) FROM public.hidra_custody_supplied_fluid_property_version WHERE product_draft_id=NEW.product_draft_id)
   OR NOT EXISTS(SELECT 1 FROM public.hidra_custody_supplied_fluid_property_version v WHERE v.product_draft_id=NEW.product_draft_id AND v.version=NEW.supplied_version AND v.product_metadata_version=NEW.metadata_version) THEN
   RAISE EXCEPTION 'Stale publication candidate sources' USING ERRCODE='23514'; END IF;
 ELSIF TG_TABLE_NAME='hidra_custody_product_publication_event' THEN
  SELECT * INTO previous FROM public.hidra_custody_product_publication_head WHERE product_draft_id=NEW.product_draft_id;
  SELECT COALESCE(max(epoch),0) INTO latest FROM public.hidra_custody_product_publication_event WHERE product_draft_id=NEW.product_draft_id;
  IF latest=9223372036854775807::bigint OR NEW.epoch<>latest+1 OR COALESCE(previous.epoch,0)<>latest
   OR NEW.prior_publication_id IS DISTINCT FROM previous.publication_id OR NEW.effective_at>observed OR NEW.effective_at<NEW.sampled_at
   OR (latest>0 AND NEW.effective_at<=previous.effective_at)
   OR (NEW.action='WITHDRAW' AND previous.state IS DISTINCT FROM 'ACTIVE')
   OR (NEW.action='REACTIVATE' AND previous.state IS DISTINCT FROM 'WITHDRAWN') THEN
   RAISE EXCEPTION 'Invalid publication transition' USING ERRCODE='23514'; END IF;
 ELSIF TG_TABLE_NAME='hidra_custody_product_publication_head' THEN
  SELECT * INTO transition FROM public.hidra_custody_product_publication_event WHERE event_id=NEW.event_id AND product_draft_id=NEW.product_draft_id;
  IF NOT FOUND OR (NEW.publication_id,NEW.epoch,NEW.state,NEW.effective_at) IS DISTINCT FROM
    (transition.publication_id,transition.epoch,transition.state,transition.effective_at)
   OR NEW.epoch IS DISTINCT FROM (SELECT max(epoch) FROM public.hidra_custody_product_publication_event WHERE product_draft_id=NEW.product_draft_id)
   OR (TG_OP='INSERT' AND NEW.epoch<>1) THEN RAISE EXCEPTION 'Invalid publication head' USING ERRCODE='23514'; END IF;
  IF TG_OP='UPDATE' THEN
   IF NEW.product_draft_id<>OLD.product_draft_id OR NEW.company_party_id<>OLD.company_party_id OR NEW.catalog_product_id<>OLD.catalog_product_id
    OR NEW.epoch<>OLD.epoch+1 OR transition.prior_publication_id<>OLD.publication_id THEN RAISE EXCEPTION 'Stale publication head' USING ERRCODE='23514'; END IF;
  END IF;
 ELSIF TG_TABLE_NAME='hidra_custody_product_publication_request' AND NEW.kind<>'PREPARE' THEN
  IF NOT EXISTS(SELECT 1 FROM public.hidra_custody_product_publication_event e WHERE e.event_id=NEW.event_id AND e.product_draft_id=NEW.product_draft_id
    AND e.request_id=NEW.request_id AND e.command_sha256=NEW.command_sha256 AND e.action=NEW.kind AND e.actor_user_id=NEW.actor_user_id) THEN
   RAISE EXCEPTION 'Invalid publication receipt' USING ERRCODE='23514'; END IF;
 END IF;
 NEW.created_at:=observed;
 RETURN NEW;
END;
$guard$;
CREATE TRIGGER custody_publication_candidate_admit BEFORE INSERT ON public.hidra_custody_product_publication_candidate FOR EACH ROW EXECUTE FUNCTION public.hidra_custody_publication_admit();
CREATE TRIGGER custody_publication_candidate_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_product_publication_candidate FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
CREATE TRIGGER custody_publication_candidate_no_truncate BEFORE TRUNCATE ON public.hidra_custody_product_publication_candidate FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
REVOKE ALL ON public.hidra_custody_product_publication_candidate FROM PUBLIC;
CREATE TRIGGER custody_publication_snapshot_admit BEFORE INSERT ON public.hidra_custody_product_publication FOR EACH ROW EXECUTE FUNCTION public.hidra_custody_publication_admit();
CREATE TRIGGER custody_publication_snapshot_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_product_publication FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
CREATE TRIGGER custody_publication_snapshot_no_truncate BEFORE TRUNCATE ON public.hidra_custody_product_publication FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
REVOKE ALL ON public.hidra_custody_product_publication FROM PUBLIC;
CREATE TRIGGER custody_publication_head_admit BEFORE INSERT OR UPDATE ON public.hidra_custody_product_publication_head FOR EACH ROW EXECUTE FUNCTION public.hidra_custody_publication_admit();
CREATE TRIGGER custody_publication_head_immutable BEFORE DELETE ON public.hidra_custody_product_publication_head FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
CREATE TRIGGER custody_publication_head_no_truncate BEFORE TRUNCATE ON public.hidra_custody_product_publication_head FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
REVOKE ALL ON public.hidra_custody_product_publication_head FROM PUBLIC;
CREATE TRIGGER custody_publication_event_admit BEFORE INSERT ON public.hidra_custody_product_publication_event FOR EACH ROW EXECUTE FUNCTION public.hidra_custody_publication_admit();
CREATE TRIGGER custody_publication_event_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_product_publication_event FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
CREATE TRIGGER custody_publication_event_no_truncate BEFORE TRUNCATE ON public.hidra_custody_product_publication_event FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
REVOKE ALL ON public.hidra_custody_product_publication_event FROM PUBLIC;
CREATE TRIGGER custody_publication_request_admit BEFORE INSERT ON public.hidra_custody_product_publication_request FOR EACH ROW EXECUTE FUNCTION public.hidra_custody_publication_admit();
CREATE TRIGGER custody_publication_request_immutable BEFORE UPDATE OR DELETE ON public.hidra_custody_product_publication_request FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
CREATE TRIGGER custody_publication_request_no_truncate BEFORE TRUNCATE ON public.hidra_custody_product_publication_request FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_custody_publication_immutable();
REVOKE ALL ON public.hidra_custody_product_publication_request FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_custody_publication_immutable(),public.hidra_custody_publication_admit() FROM PUBLIC;
