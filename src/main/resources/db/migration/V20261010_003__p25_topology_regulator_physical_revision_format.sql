-- HPR-P25-008C REGULATOR Slice A: forward-only Topology canonical format admission.
-- Historical V1 bytes, source identities, SHA-256 constraint and immutable triggers remain unchanged.
ALTER TABLE hidra_topology_physical_network_revision
    DROP CONSTRAINT ck_topology_physical_format,
    ADD CONSTRAINT ck_topology_physical_format CHECK (
        payload_format IN ('HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1',
                           'HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V2')
    );
