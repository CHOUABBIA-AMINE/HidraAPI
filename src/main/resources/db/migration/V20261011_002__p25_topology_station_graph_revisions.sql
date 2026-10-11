-- HPR-P25-037: owner-private declaration storage, not authenticated publication.
-- All supplied provenance and nanosecond instants remain in immutable V1 bytes.
CREATE TABLE hidra_topology_station_graph_revision (
    facility_id text NOT NULL,
    revision_id text NOT NULL,
    payload_format text NOT NULL,
    canonical_payload bytea NOT NULL,
    sha256 varchar(64) NOT NULL,
    CONSTRAINT pk_topology_station_graph_revision PRIMARY KEY (facility_id, revision_id),
    CONSTRAINT ck_topology_station_graph_facility CHECK (facility_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND facility_id !~ '^[[:space:]]*$'),
    CONSTRAINT ck_topology_station_graph_revision CHECK (revision_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND revision_id !~ '^[[:space:]]*$'),
    CONSTRAINT ck_topology_station_graph_format CHECK (payload_format = 'HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1'),
    CONSTRAINT ck_topology_station_graph_payload CHECK (octet_length(canonical_payload) BETWEEN 1 AND 1048576),
    CONSTRAINT ck_topology_station_graph_digest CHECK (
        sha256 ~ '^[0-9a-f]{64}$' AND sha256 = encode(sha256(canonical_payload), 'hex')
    )
);

CREATE FUNCTION hidra_topology_reject_station_graph_revision_mutation() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'Topology station graph revisions are append-only' USING ERRCODE = '23514';
END;
$$;

CREATE TRIGGER topology_station_graph_revision_no_update_delete
BEFORE UPDATE OR DELETE ON hidra_topology_station_graph_revision
FOR EACH ROW EXECUTE FUNCTION hidra_topology_reject_station_graph_revision_mutation();

CREATE TRIGGER topology_station_graph_revision_no_truncate
BEFORE TRUNCATE ON hidra_topology_station_graph_revision
FOR EACH STATEMENT EXECUTE FUNCTION hidra_topology_reject_station_graph_revision_mutation();
