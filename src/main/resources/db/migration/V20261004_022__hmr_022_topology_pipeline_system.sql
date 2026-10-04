-- HMR-022: PipelineSystem semantic remediation.
-- Existing migrations remain immutable.
-- PipelineSystem classification is a dedicated Topology-owned catalog.
-- The six seeded codes are migrated from the former enum; localized labels remain unresolved.

CREATE TABLE hidra_topology_pipeline_system_type (
    id varchar(80) PRIMARY KEY,
    code varchar(120) NOT NULL,
    name_ar varchar(255),
    name_fr varchar(255),
    name_en varchar(255),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    CONSTRAINT uq_hmr022_pipeline_system_type_code UNIQUE (code)
);

INSERT INTO hidra_topology_pipeline_system_type (
    id,
    code,
    name_ar,
    name_fr,
    name_en,
    active,
    created_at,
    updated_at
) VALUES
    ('TRANSPORT', 'TRANSPORT', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('GATHERING', 'GATHERING', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('DISTRIBUTION', 'DISTRIBUTION', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('EXPORT', 'EXPORT', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('IMPORT', 'IMPORT', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('MIXED', 'MIXED', NULL, NULL, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_topology_pipeline_system ps
        LEFT JOIN hidra_topology_pipeline_system_type pst
               ON pst.code = ps.system_type
        WHERE pst.id IS NULL
    ) THEN
        RAISE EXCEPTION
            'HMR-022 cannot migrate unknown PipelineSystem system_type values'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

ALTER TABLE hidra_topology_pipeline_system
    ADD COLUMN system_type_id varchar(80);

UPDATE hidra_topology_pipeline_system ps
SET system_type_id = pst.id
FROM hidra_topology_pipeline_system_type pst
WHERE pst.code = ps.system_type;

ALTER TABLE hidra_topology_pipeline_system
    ALTER COLUMN system_type_id SET NOT NULL;

ALTER TABLE hidra_topology_pipeline_system
    ADD CONSTRAINT fk_hmr022_pipeline_system_type
    FOREIGN KEY (system_type_id)
    REFERENCES hidra_topology_pipeline_system_type (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_topology_pipeline_system
    VALIDATE CONSTRAINT fk_hmr022_pipeline_system_type;

CREATE INDEX ix_hidra_topology_pipeline_system_system_type_id
    ON hidra_topology_pipeline_system (system_type_id);

ALTER TABLE hidra_topology_pipeline_system
    DROP COLUMN system_type;
