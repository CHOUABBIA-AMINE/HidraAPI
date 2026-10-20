-- HAPI-589B: additive immutable survey snapshots; development/fresh-schema validation.
CREATE TABLE public.hidra_topology_engineering_survey_snapshot (
    draft_id uuid NOT NULL,
    branch_id varchar(80) NOT NULL CHECK (octet_length(branch_id) BETWEEN 1 AND 80),
    version bigint NOT NULL CHECK (version > 0),
    graph_version bigint NOT NULL CHECK (graph_version > 0 AND graph_version < version),
    graph_sha256 varchar(64) NOT NULL CHECK (graph_sha256 ~ '^[0-9a-f]{64}$'),
    payload_format varchar(48) NOT NULL CHECK (payload_format='HIDRA_ENGINEERING_SURVEY_DRAFT_V1'),
    canonical_payload bytea NOT NULL CHECK (octet_length(canonical_payload) BETWEEN 16 AND 16777216),
    sha256 varchar(64) NOT NULL CHECK (sha256=encode(sha256(canonical_payload),'hex')),
    transaction_id bigint NOT NULL CHECK (transaction_id > 0),
    CONSTRAINT pk_p25_engineering_survey_snapshot PRIMARY KEY (draft_id,branch_id,version),
    CONSTRAINT fk_p25_survey_metadata_version FOREIGN KEY (draft_id,version)
        REFERENCES public.hidra_topology_engineering_draft_version(draft_id,version) ON DELETE RESTRICT,
    CONSTRAINT fk_p25_survey_graph_version FOREIGN KEY (draft_id,graph_version)
        REFERENCES public.hidra_topology_engineering_graph_snapshot(draft_id,version) ON DELETE RESTRICT
);
CREATE FUNCTION public.hidra_p25_survey_snapshot_same_transaction()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path = pg_catalog, pg_temp AS $guard$
BEGIN
    IF TG_RELID <> 'public.hidra_topology_engineering_survey_snapshot'::regclass
       OR TG_NAME <> 'trg_p25_engineering_survey_same_transaction'
       OR TG_WHEN <> 'BEFORE' OR TG_LEVEL <> 'ROW' OR TG_OP <> 'INSERT' OR TG_NARGS <> 0 THEN
        RAISE EXCEPTION 'Invalid survey insertion context' USING ERRCODE = '23514';
    END IF;
    IF NEW.transaction_id <> txid_current() OR NOT EXISTS (
        SELECT 1 FROM public.hidra_topology_engineering_draft_version v
        WHERE v.draft_id=NEW.draft_id AND v.version=NEW.version AND v.transaction_id=NEW.transaction_id
          AND v.payload_format='HIDRA_ENGINEERING_METADATA_V1'
          AND v.sha256=encode(sha256(v.canonical_payload),'hex')
    ) OR NOT EXISTS (
        SELECT 1 FROM public.hidra_topology_engineering_graph_snapshot g
        WHERE g.draft_id=NEW.draft_id AND g.version=NEW.graph_version AND g.sha256=NEW.graph_sha256
          AND g.sha256=encode(sha256(g.canonical_payload),'hex')
          AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_graph_snapshot n
            WHERE n.draft_id=g.draft_id AND n.version>g.version AND n.version<=NEW.version)
    ) THEN
        RAISE EXCEPTION 'Survey metadata transaction or graph binding invalid' USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END;
$guard$;
CREATE TRIGGER trg_p25_engineering_survey_same_transaction
BEFORE INSERT ON public.hidra_topology_engineering_survey_snapshot
FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_survey_snapshot_same_transaction();
CREATE TRIGGER trg_p25_engineering_survey_immutable
BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_survey_snapshot
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER trg_p25_engineering_survey_no_truncate
BEFORE TRUNCATE ON public.hidra_topology_engineering_survey_snapshot
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
REVOKE ALL ON TABLE public.hidra_topology_engineering_survey_snapshot FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_p25_survey_snapshot_same_transaction() FROM PUBLIC;
-- Reviewed private publisher: SELECT,INSERT,UPDATE only; UPDATE is for lock eligibility.
-- Immutable triggers reject mutation. No ordinary-role writes, DELETE/TRUNCATE/TRIGGER or function EXECUTE.
