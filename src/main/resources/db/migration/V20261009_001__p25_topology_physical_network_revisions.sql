-- Topology-owned physical source revisions; integrity does not establish operational approval.
CREATE TABLE hidra_topology_physical_network_revision (
    source_id text NOT NULL,
    revision_id text NOT NULL,
    payload_format text NOT NULL,
    canonical_payload bytea NOT NULL,
    sha256 varchar(64) NOT NULL,
    CONSTRAINT pk_topology_physical_network_revision PRIMARY KEY (source_id, revision_id),
    CONSTRAINT ck_topology_physical_source_id CHECK (source_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND source_id !~ '^[[:space:]]*$'),
    CONSTRAINT ck_topology_physical_revision_id CHECK (revision_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND revision_id !~ '^[[:space:]]*$'),
    CONSTRAINT ck_topology_physical_format CHECK (payload_format = 'HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1'),
    CONSTRAINT ck_topology_physical_payload CHECK (octet_length(canonical_payload) > 0),
    CONSTRAINT ck_topology_physical_digest CHECK (
        sha256 ~ '^[0-9a-f]{64}$' AND sha256 = encode(sha256(canonical_payload), 'hex')
    )
);

CREATE FUNCTION hidra_topology_reject_physical_revision_mutation() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'Topology physical source revisions are append-only' USING ERRCODE = '23514';
END;
$$;

CREATE TRIGGER topology_physical_revision_no_update_delete
BEFORE UPDATE OR DELETE ON hidra_topology_physical_network_revision
FOR EACH ROW EXECUTE FUNCTION hidra_topology_reject_physical_revision_mutation();

CREATE TRIGGER topology_physical_revision_no_truncate
BEFORE TRUNCATE ON hidra_topology_physical_network_revision
FOR EACH STATEMENT EXECUTE FUNCTION hidra_topology_reject_physical_revision_mutation();
