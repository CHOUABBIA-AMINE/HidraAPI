-- HAPI-926: versioned owner associations over the existing graph store.
CREATE TABLE public.hidra_topology_pipeline_authoring_revision (
 pipeline_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline(id),
 revision bigint NOT NULL CHECK(revision>0),
 company_party_id varchar(80) NOT NULL REFERENCES public.hidra_party_party(id),
 pipeline_system_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline_system(id),
 code varchar(120) NOT NULL CHECK(length(btrim(code))>0),
 pipeline_type_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline_type(id),
 name_ar varchar(255),name_fr varchar(255),name_en varchar(255),
 actor_user_id varchar(120) NOT NULL CHECK(length(btrim(actor_user_id))>0),recorded_at timestamptz NOT NULL,
 PRIMARY KEY(pipeline_id,revision)
);
CREATE TABLE public.hidra_topology_pipeline_graph_snapshot (
 draft_id uuid NOT NULL,version bigint NOT NULL,request_id uuid NOT NULL,
 company_party_id varchar(80) NOT NULL REFERENCES public.hidra_party_party(id),
 pipeline_system_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline_system(id),
 graph_sha256 varchar(64) NOT NULL CHECK(graph_sha256 ~ '^[0-9a-f]{64}$'),
 actor_user_id varchar(120) NOT NULL CHECK(length(btrim(actor_user_id))>0),transaction_id bigint NOT NULL CHECK(transaction_id>0),
 PRIMARY KEY(draft_id,version),UNIQUE(draft_id,request_id),
 FOREIGN KEY(draft_id,version) REFERENCES public.hidra_topology_engineering_graph_snapshot(draft_id,version)
);
CREATE TABLE public.hidra_topology_pipeline_graph_binding (
 draft_id uuid NOT NULL,version bigint NOT NULL,branch_id varchar(80) NOT NULL CHECK(length(btrim(branch_id))>0),
 pipeline_id varchar(80) NOT NULL,pipeline_revision bigint NOT NULL,
 segment_id varchar(80) REFERENCES public.hidra_topology_pipeline_segment(id),
 PRIMARY KEY(draft_id,version,branch_id),
 FOREIGN KEY(draft_id,version) REFERENCES public.hidra_topology_pipeline_graph_snapshot(draft_id,version),
 FOREIGN KEY(pipeline_id,pipeline_revision) REFERENCES public.hidra_topology_pipeline_authoring_revision(pipeline_id,revision)
);
CREATE INDEX ix_b4_pipeline_scope ON public.hidra_topology_pipeline_authoring_revision(company_party_id,pipeline_system_id,pipeline_id,revision);
CREATE FUNCTION public.hidra_b4_pipeline_integrity() RETURNS trigger LANGUAGE plpgsql
SET search_path=pg_catalog,pg_temp AS $$
DECLARE prior public.hidra_topology_pipeline_authoring_revision; s public.hidra_topology_pipeline_graph_snapshot; r public.hidra_topology_pipeline_authoring_revision;
BEGIN
 IF TG_TABLE_NAME='hidra_topology_pipeline_authoring_revision' THEN
  SELECT * INTO prior FROM public.hidra_topology_pipeline_authoring_revision WHERE pipeline_id=NEW.pipeline_id ORDER BY revision DESC LIMIT 1;
  IF NEW.revision<>coalesce(prior.revision,0)+1 OR (prior.pipeline_id IS NOT NULL AND (NEW.company_party_id,NEW.pipeline_system_id,NEW.code,NEW.pipeline_type_id) IS DISTINCT FROM (prior.company_party_id,prior.pipeline_system_id,prior.code,prior.pipeline_type_id)) THEN RAISE EXCEPTION 'Pipeline revision conflict' USING ERRCODE='23514'; END IF;
  IF NOT EXISTS(SELECT 1 FROM public.hidra_topology_pipeline p WHERE p.id=NEW.pipeline_id AND (p.pipeline_system_id,p.code,p.pipeline_type_id,p.name_ar,p.name_fr,p.name_en) IS NOT DISTINCT FROM (NEW.pipeline_system_id,NEW.code,NEW.pipeline_type_id,NEW.name_ar,NEW.name_fr,NEW.name_en)) THEN RAISE EXCEPTION 'Pipeline identity mismatch' USING ERRCODE='23514'; END IF;
 ELSIF TG_TABLE_NAME='hidra_topology_pipeline_graph_snapshot' THEN
  IF NEW.transaction_id<>txid_current() OR NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_graph_snapshot g JOIN public.hidra_topology_engineering_draft d ON d.id=g.draft_id JOIN public.hidra_topology_engineering_context c ON c.id=d.context_id WHERE g.draft_id=NEW.draft_id AND g.version=NEW.version AND g.sha256=NEW.graph_sha256 AND g.transaction_id=NEW.transaction_id AND c.company_party_id=NEW.company_party_id AND c.pipeline_system_id=NEW.pipeline_system_id) THEN RAISE EXCEPTION 'Pipeline graph provenance mismatch' USING ERRCODE='23514'; END IF;
 ELSE
  SELECT * INTO s FROM public.hidra_topology_pipeline_graph_snapshot WHERE draft_id=NEW.draft_id AND version=NEW.version;
  SELECT * INTO r FROM public.hidra_topology_pipeline_authoring_revision WHERE pipeline_id=NEW.pipeline_id AND revision=NEW.pipeline_revision;
  IF s.draft_id IS NULL OR r.pipeline_id IS NULL OR (s.company_party_id,s.pipeline_system_id) IS DISTINCT FROM (r.company_party_id,r.pipeline_system_id) OR (NEW.segment_id IS NOT NULL AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_pipeline_segment p WHERE p.id=NEW.segment_id AND p.pipeline_id=NEW.pipeline_id)) THEN RAISE EXCEPTION 'Pipeline association owner mismatch' USING ERRCODE='23514'; END IF;
 END IF;
 RETURN NEW;
END; $$;
CREATE TRIGGER trg_b4_revision_integrity BEFORE INSERT ON public.hidra_topology_pipeline_authoring_revision FOR EACH ROW EXECUTE FUNCTION public.hidra_b4_pipeline_integrity();
CREATE TRIGGER trg_b4_snapshot_integrity BEFORE INSERT ON public.hidra_topology_pipeline_graph_snapshot FOR EACH ROW EXECUTE FUNCTION public.hidra_b4_pipeline_integrity();
CREATE TRIGGER trg_b4_binding_integrity BEFORE INSERT ON public.hidra_topology_pipeline_graph_binding FOR EACH ROW EXECUTE FUNCTION public.hidra_b4_pipeline_integrity();
CREATE TRIGGER trg_b4_immutable_revision_rows BEFORE UPDATE OR DELETE ON public.hidra_topology_pipeline_authoring_revision FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER trg_b4_immutable_revision_truncate BEFORE TRUNCATE ON public.hidra_topology_pipeline_authoring_revision FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER trg_b4_immutable_snapshot_rows BEFORE UPDATE OR DELETE ON public.hidra_topology_pipeline_graph_snapshot FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER trg_b4_immutable_snapshot_truncate BEFORE TRUNCATE ON public.hidra_topology_pipeline_graph_snapshot FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER trg_b4_immutable_binding_rows BEFORE UPDATE OR DELETE ON public.hidra_topology_pipeline_graph_binding FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER trg_b4_immutable_binding_truncate BEFORE TRUNCATE ON public.hidra_topology_pipeline_graph_binding FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
REVOKE ALL ON public.hidra_topology_pipeline_authoring_revision,public.hidra_topology_pipeline_graph_snapshot,public.hidra_topology_pipeline_graph_binding FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_b4_pipeline_integrity() FROM PUBLIC;
