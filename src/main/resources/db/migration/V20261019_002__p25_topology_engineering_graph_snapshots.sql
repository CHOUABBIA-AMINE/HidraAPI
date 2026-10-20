-- HAPI-588B / HPR-P25-052B — REVIEW CANDIDATE, NOT PUBLISHED OR EXECUTED.
-- Additive fresh-schema PostgreSQL storage for immutable engineering-graph drafts.
-- No existing Flyway migration changes; no production-data backfill/calibration.
-- A graph snapshot must be inserted in exactly the same PostgreSQL transaction as
-- its metadata-version row. It is not an effective/published engineering revision.
CREATE TABLE public.hidra_topology_engineering_graph_snapshot (
    draft_id uuid NOT NULL,
    version bigint NOT NULL CHECK (version > 0),
    payload_format varchar(40) NOT NULL
        CHECK (payload_format = 'HIDRA_ENGINEERING_GRAPH_DRAFT_V1'),
    canonical_payload bytea NOT NULL
        CHECK (octet_length(canonical_payload) BETWEEN 16 AND 1048576),
    sha256 varchar(64) NOT NULL
        CHECK (sha256 = encode(sha256(canonical_payload), 'hex')),
    transaction_id bigint NOT NULL CHECK (transaction_id > 0),
    CONSTRAINT pk_p25_engineering_graph_snapshot PRIMARY KEY (draft_id,version),
    CONSTRAINT fk_p25_engineering_graph_metadata_version
        FOREIGN KEY (draft_id,version)
        REFERENCES public.hidra_topology_engineering_draft_version(draft_id,version)
        ON DELETE RESTRICT
);

CREATE FUNCTION public.hidra_p25_graph_snapshot_same_transaction()
RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER
SET search_path = pg_catalog, pg_temp AS $guard$
BEGIN
    IF TG_RELID <> 'public.hidra_topology_engineering_graph_snapshot'::regclass
       OR TG_NAME <> 'trg_p25_engineering_graph_same_transaction'
       OR TG_WHEN <> 'BEFORE' OR TG_LEVEL <> 'ROW' OR TG_OP <> 'INSERT'
       OR TG_NARGS <> 0 THEN
        RAISE EXCEPTION 'Invalid graph snapshot insertion context' USING ERRCODE = '23514';
    END IF;
    IF NEW.transaction_id <> txid_current() OR NOT EXISTS (
        SELECT 1 FROM public.hidra_topology_engineering_draft_version AS v
        WHERE v.draft_id = NEW.draft_id AND v.version = NEW.version
          AND v.transaction_id = NEW.transaction_id
          AND v.payload_format = 'HIDRA_ENGINEERING_METADATA_V1'
          AND v.sha256 = encode(sha256(v.canonical_payload), 'hex')
    ) THEN
        RAISE EXCEPTION 'Graph and metadata versions must share a physical transaction'
            USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END;
$guard$;

CREATE TRIGGER trg_p25_engineering_graph_same_transaction
BEFORE INSERT ON public.hidra_topology_engineering_graph_snapshot
FOR EACH ROW EXECUTE FUNCTION public.hidra_p25_graph_snapshot_same_transaction();
CREATE TRIGGER trg_p25_engineering_graph_immutable
BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_graph_snapshot
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();
CREATE TRIGGER trg_p25_engineering_graph_no_truncate
BEFORE TRUNCATE ON public.hidra_topology_engineering_graph_snapshot
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_p25_draft_immutable();

REVOKE ALL ON TABLE public.hidra_topology_engineering_graph_snapshot FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_p25_graph_snapshot_same_transaction() FROM PUBLIC;
-- The existing protected publication credential must be granted the precise
-- SELECT/INSERT/UPDATE table privileges by reviewed runtime-role provisioning.
-- UPDATE is needed for the approved lock mode but is rejected by the immutable
-- statement trigger. Never grant DELETE/TRUNCATE/TRIGGER to application actors.
