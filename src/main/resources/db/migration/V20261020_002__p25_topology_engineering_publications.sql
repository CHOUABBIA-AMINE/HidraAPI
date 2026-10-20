-- HAPI-590B. Additive development ledger; no existing migration is rewritten.
CREATE TABLE public.hidra_topology_engineering_publication_snapshot (
 revision_id uuid PRIMARY KEY,
 context_id uuid NOT NULL REFERENCES public.hidra_topology_engineering_context(id),
 draft_id uuid NOT NULL,
 draft_version bigint NOT NULL CHECK(draft_version>0),
 payload_format varchar(48) NOT NULL CHECK(payload_format='HIDRA_ENGINEERING_PUBLICATION_V1'),
 canonical_payload bytea NOT NULL CHECK(octet_length(canonical_payload) BETWEEN 16 AND 33554432),
 sha256 varchar(64) NOT NULL CHECK(sha256=encode(sha256(canonical_payload),'hex')),
 engineering_qualified boolean NOT NULL CHECK(NOT engineering_qualified),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 UNIQUE(context_id,revision_id),
 FOREIGN KEY(draft_id,draft_version) REFERENCES public.hidra_topology_engineering_draft_version(draft_id,version) ON DELETE RESTRICT
);
CREATE TABLE public.hidra_topology_engineering_publication_event (
 event_id uuid PRIMARY KEY,
 context_id uuid NOT NULL REFERENCES public.hidra_topology_engineering_context(id),
 epoch bigint NOT NULL CHECK(epoch>0),
 operation varchar(16) NOT NULL CHECK(operation IN ('PUBLISH','WITHDRAW','REACTIVATE')),
 prior_revision_id uuid,
 revision_id uuid NOT NULL,
 resulting_state varchar(16) NOT NULL CHECK(resulting_state IN ('ACTIVE','WITHDRAWN')),
 request_id uuid NOT NULL,
 command_sha256 varchar(64) NOT NULL CHECK(command_sha256 ~ '^[0-9a-f]{64}$'),
 actor_binding_id uuid NOT NULL,
 actor_session_id uuid NOT NULL,
 actor_user_id varchar(80) NOT NULL CHECK(length(btrim(actor_user_id))>0),
 party_generation bigint NOT NULL CHECK(party_generation>0),
 station_evidence text NOT NULL CHECK(octet_length(station_evidence)<=1048576),
 sampled_at timestamptz NOT NULL,
 valid_until timestamptz NOT NULL CHECK(valid_until>sampled_at),
 effective_at timestamptz NOT NULL CHECK(effective_at=sampled_at),
 backend_pid integer NOT NULL CHECK(backend_pid>0),
 transaction_id bigint NOT NULL CHECK(transaction_id>0),
 UNIQUE(context_id,epoch),
 UNIQUE(context_id,actor_user_id,request_id),
 UNIQUE(context_id,epoch,revision_id,resulting_state,event_id),
 FOREIGN KEY(context_id,revision_id) REFERENCES public.hidra_topology_engineering_publication_snapshot(context_id,revision_id) ON DELETE RESTRICT,
 FOREIGN KEY(context_id,prior_revision_id) REFERENCES public.hidra_topology_engineering_publication_snapshot(context_id,revision_id) ON DELETE RESTRICT,
 CHECK((epoch=1 AND prior_revision_id IS NULL AND operation='PUBLISH') OR (epoch>1 AND prior_revision_id IS NOT NULL)),
 CHECK((operation='WITHDRAW' AND resulting_state='WITHDRAWN') OR (operation IN ('PUBLISH','REACTIVATE') AND resulting_state='ACTIVE')),
 CHECK(operation='PUBLISH' OR prior_revision_id=revision_id),
 CHECK(operation<>'PUBLISH' OR prior_revision_id IS DISTINCT FROM revision_id)
);
CREATE TABLE public.hidra_topology_engineering_publication_head (
 context_id uuid PRIMARY KEY REFERENCES public.hidra_topology_engineering_context(id),
 revision_id uuid NOT NULL,
 epoch bigint NOT NULL CHECK(epoch>0),
 state varchar(16) NOT NULL CHECK(state IN ('ACTIVE','WITHDRAWN')),
 last_event_id uuid NOT NULL,
 FOREIGN KEY(context_id,epoch,revision_id,state,last_event_id)
  REFERENCES public.hidra_topology_engineering_publication_event(context_id,epoch,revision_id,resulting_state,event_id) ON DELETE RESTRICT
);
CREATE FUNCTION public.hidra_p25_publication_snapshot_guard() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
 IF NEW.transaction_id<>txid_current() OR NOT EXISTS(
   SELECT 1 FROM public.hidra_topology_engineering_draft d
   JOIN public.hidra_topology_engineering_draft_version v ON v.draft_id=d.id
   WHERE d.id=NEW.draft_id AND d.context_id=NEW.context_id AND v.version=NEW.draft_version
    AND v.sha256=encode(sha256(v.canonical_payload),'hex')
    AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft_version n WHERE n.draft_id=d.id AND n.version>v.version)
 ) THEN RAISE EXCEPTION 'Invalid publication source' USING ERRCODE='23514'; END IF;
 RETURN NEW;
END;
$guard$;
CREATE FUNCTION public.hidra_p25_publication_event_guard() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
DECLARE previous public.hidra_topology_engineering_publication_head%ROWTYPE; prior_time timestamptz; last_epoch bigint;
BEGIN
 PERFORM id FROM public.hidra_topology_engineering_context WHERE id=NEW.context_id FOR UPDATE;
 IF NOT FOUND THEN RAISE EXCEPTION 'Missing publication context' USING ERRCODE='23503'; END IF;
 SELECT * INTO previous FROM public.hidra_topology_engineering_publication_head WHERE context_id=NEW.context_id;
 SELECT epoch,effective_at INTO last_epoch,prior_time FROM public.hidra_topology_engineering_publication_event
  WHERE context_id=NEW.context_id ORDER BY epoch DESC LIMIT 1;
 IF NEW.backend_pid<>pg_backend_pid() OR NEW.transaction_id<>txid_current()
    OR NEW.sampled_at>clock_timestamp() OR NEW.valid_until<=clock_timestamp()
    OR NEW.effective_at<>NEW.sampled_at OR (prior_time IS NOT NULL AND NEW.effective_at<prior_time)
    OR (previous.context_id IS NULL AND (last_epoch IS NOT NULL OR NEW.epoch<>1 OR NEW.prior_revision_id IS NOT NULL OR NEW.operation<>'PUBLISH'))
    OR (previous.context_id IS NOT NULL AND (last_epoch IS DISTINCT FROM previous.epoch OR previous.epoch=9223372036854775807::bigint
       OR NEW.epoch<>previous.epoch+1 OR NEW.prior_revision_id IS DISTINCT FROM previous.revision_id))
    OR (NEW.operation='WITHDRAW' AND previous.state IS DISTINCT FROM 'ACTIVE')
    OR (NEW.operation='REACTIVATE' AND previous.state IS DISTINCT FROM 'WITHDRAWN')
    OR (NEW.operation<>'PUBLISH' AND NEW.revision_id IS DISTINCT FROM previous.revision_id)
    OR (NEW.operation='PUBLISH' AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_snapshot p
       WHERE p.context_id=NEW.context_id AND p.revision_id=NEW.revision_id AND p.transaction_id=NEW.transaction_id))
 THEN RAISE EXCEPTION 'Invalid publication transition' USING ERRCODE='23514'; END IF;
 RETURN NEW;
END;
$guard$;
CREATE FUNCTION public.hidra_p25_publication_head_guard() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
 IF TG_OP='UPDATE' AND (NEW.context_id<>OLD.context_id OR OLD.epoch=9223372036854775807::bigint OR NEW.epoch<>OLD.epoch+1)
    OR TG_OP='INSERT' AND NEW.epoch<>1
    OR NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_event e
      WHERE e.context_id=NEW.context_id AND e.event_id=NEW.last_event_id AND e.epoch=NEW.epoch
       AND e.revision_id=NEW.revision_id AND e.resulting_state=NEW.state
       AND e.transaction_id=txid_current() AND e.backend_pid=pg_backend_pid()
       AND (TG_OP='INSERT' OR e.prior_revision_id=OLD.revision_id))
 THEN RAISE EXCEPTION 'Invalid publication head' USING ERRCODE='23514'; END IF;
 RETURN NEW;
END;
$guard$;
CREATE TRIGGER p25_publication_snapshot_insert BEFORE INSERT ON public.hidra_topology_engineering_publication_snapshot FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_publication_snapshot_guard();
CREATE TRIGGER p25_publication_snapshot_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_publication_snapshot FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_publication_snapshot_no_truncate BEFORE TRUNCATE ON public.hidra_topology_engineering_publication_snapshot FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_publication_event_insert BEFORE INSERT ON public.hidra_topology_engineering_publication_event FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_publication_event_guard();
CREATE TRIGGER p25_publication_event_immutable BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_publication_event FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_publication_event_no_truncate BEFORE TRUNCATE ON public.hidra_topology_engineering_publication_event FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_publication_head_write BEFORE INSERT OR UPDATE ON public.hidra_topology_engineering_publication_head FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_publication_head_guard();
CREATE TRIGGER p25_publication_head_no_delete BEFORE DELETE ON public.hidra_topology_engineering_publication_head FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER p25_publication_head_no_truncate BEFORE TRUNCATE ON public.hidra_topology_engineering_publication_head FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
REVOKE ALL ON public.hidra_topology_engineering_publication_snapshot,public.hidra_topology_engineering_publication_event,public.hidra_topology_engineering_publication_head FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_p25_publication_snapshot_guard(),public.hidra_p25_publication_event_guard(),public.hidra_p25_publication_head_guard() FROM PUBLIC;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES('topology-engineering-publication-publish','topology:engineering-publication:publish','topology','ENGINEERING_NETWORK_PUBLICATION','publish',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES('topology-engineering-publication-withdraw','topology:engineering-publication:withdraw','topology','ENGINEERING_NETWORK_PUBLICATION','withdraw',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES('topology-engineering-publication-reactivate','topology:engineering-publication:reactivate','topology','ENGINEERING_NETWORK_PUBLICATION','reactivate',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
 VALUES('topology-engineering-publication-read','topology:engineering-publication:read','topology','ENGINEERING_NETWORK_PUBLICATION','read',false,'ACTIVE',clock_timestamp(),clock_timestamp()) ON CONFLICT(code) DO NOTHING;
DO $verify$ BEGIN
 IF (SELECT count(*) FROM public.hidra_identity_permission WHERE permission_domain='topology'
  AND resource_type='ENGINEERING_NETWORK_PUBLICATION' AND code='topology:engineering-publication:'||action
  AND action IN ('publish','withdraw','reactivate','read') AND NOT sensitive AND status='ACTIVE')<>4
 THEN RAISE EXCEPTION 'Publication permission catalog mismatch'; END IF;
END; $verify$;
-- Runtime privilege provisioning is explicit. No runtime role owns these objects.
-- Publisher: SELECT,INSERT,UPDATE; ordinary: none. Immutable UPDATE fails even at zero rows.
