-- HMR-031: TopologyConnection semantic remediation.
-- Existing migrations remain immutable.
-- Replace fixed connection_type enum/string persistence with a Topology-owned
-- catalog reference, reject self-loops, and protect optional PipelineSegment links.

CREATE TABLE hidra_topology_connection_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    CONSTRAINT uq_hmr031_topology_connection_type_code UNIQUE (code)
);

INSERT INTO hidra_topology_connection_type (
    id,
    code,
    name_ar,
    name_fr,
    name_en,
    active,
    created_at,
    updated_at
) VALUES
    ('PIPELINE_SEGMENT', 'PIPELINE_SEGMENT', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('DIRECT_LINK', 'DIRECT_LINK', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('VIRTUAL_LINK', 'VIRTUAL_LINK', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('TRANSFER_LINK', 'TRANSFER_LINK', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('MEASUREMENT_LINK', 'MEASUREMENT_LINK', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_topology_connection
        WHERE connection_type NOT IN (
            'PIPELINE_SEGMENT',
            'DIRECT_LINK',
            'VIRTUAL_LINK',
            'TRANSFER_LINK',
            'MEASUREMENT_LINK'
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-031 cannot migrate unsupported TopologyConnection connection_type values'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_topology_connection
        WHERE from_node_id = to_node_id
    ) THEN
        RAISE EXCEPTION
            'HMR-031 cannot retain TopologyConnection self-loops'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_topology_connection connection
        WHERE connection.pipeline_segment_id IS NOT NULL
          AND NOT EXISTS (
              SELECT 1
              FROM hidra_topology_pipeline_segment segment
              WHERE segment.id = connection.pipeline_segment_id
          )
    ) THEN
        RAISE EXCEPTION
            'HMR-031 cannot retain dangling TopologyConnection PipelineSegment references'
            USING ERRCODE = '23503';
    END IF;
END;
$$;

ALTER TABLE hidra_topology_connection
    ADD COLUMN connection_type_id varchar(80);

UPDATE hidra_topology_connection connection
SET connection_type_id = type.id
FROM hidra_topology_connection_type type
WHERE type.code = connection.connection_type;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_topology_connection
        WHERE connection_type_id IS NULL
    ) THEN
        RAISE EXCEPTION
            'HMR-031 failed to backfill TopologyConnection connection type references'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_topology_connection
    ALTER COLUMN connection_type_id SET NOT NULL;

ALTER TABLE hidra_topology_connection
    ADD CONSTRAINT fk_hmr031_topology_connection_type
    FOREIGN KEY (connection_type_id)
    REFERENCES hidra_topology_connection_type (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_topology_connection
    ADD CONSTRAINT fk_hmr031_topology_connection_pipeline_segment
    FOREIGN KEY (pipeline_segment_id)
    REFERENCES hidra_topology_pipeline_segment (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_topology_connection
    ADD CONSTRAINT ck_hmr031_topology_connection_no_self_loop
    CHECK (from_node_id <> to_node_id)
    NOT VALID;

ALTER TABLE hidra_topology_connection
    VALIDATE CONSTRAINT fk_hmr031_topology_connection_type;

ALTER TABLE hidra_topology_connection
    VALIDATE CONSTRAINT fk_hmr031_topology_connection_pipeline_segment;

ALTER TABLE hidra_topology_connection
    VALIDATE CONSTRAINT ck_hmr031_topology_connection_no_self_loop;

CREATE INDEX ix_hidra_topology_connection_connection_type_id
    ON hidra_topology_connection (connection_type_id);

ALTER TABLE hidra_topology_connection
    DROP COLUMN connection_type;
