-- HMR-042: Pipeline semantic remediation.
-- Existing migrations remain immutable.
-- Replace fixed pipeline_type enum/string persistence with a Topology-owned catalog reference.

CREATE TABLE hidra_topology_pipeline_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    CONSTRAINT uq_hmr042_pipeline_type_code UNIQUE (code)
);

INSERT INTO hidra_topology_pipeline_type (
    id,
    code,
    name_ar,
    name_fr,
    name_en,
    active,
    created_at,
    updated_at
) VALUES
    ('CRUDE_OIL', 'CRUDE_OIL', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('CONDENSATE', 'CONDENSATE', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('NATURAL_GAS', 'NATURAL_GAS', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('LPG', 'LPG', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('MULTI_PRODUCT', 'MULTI_PRODUCT', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('WATER', 'WATER', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('OTHER', 'OTHER', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_topology_pipeline pipeline
        LEFT JOIN hidra_topology_pipeline_type type
               ON type.code = pipeline.pipeline_type
        WHERE type.id IS NULL
    ) THEN
        RAISE EXCEPTION
            'HMR-042 cannot migrate unknown Pipeline pipeline_type values'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_topology_pipeline
    ADD COLUMN pipeline_type_id varchar(80);

UPDATE hidra_topology_pipeline pipeline
SET pipeline_type_id = type.id
FROM hidra_topology_pipeline_type type
WHERE type.code = pipeline.pipeline_type;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_topology_pipeline
        WHERE pipeline_type_id IS NULL
    ) THEN
        RAISE EXCEPTION
            'HMR-042 failed to backfill Pipeline classification references'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_topology_pipeline
    ALTER COLUMN pipeline_type_id SET NOT NULL;

ALTER TABLE hidra_topology_pipeline
    ADD CONSTRAINT fk_hmr042_pipeline_type
    FOREIGN KEY (pipeline_type_id)
    REFERENCES hidra_topology_pipeline_type (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_topology_pipeline
    VALIDATE CONSTRAINT fk_hmr042_pipeline_type;

CREATE INDEX ix_hidra_topology_pipeline_pipeline_type_id
    ON hidra_topology_pipeline (pipeline_type_id);

ALTER TABLE hidra_topology_pipeline
    DROP COLUMN pipeline_type;
