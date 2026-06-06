-- HidraAPI topology correction.
-- COR2-008 — standardize pipeline multilingual labels.
-- Converts pipeline name and description from single display columns to first-class Arabic, French, and English columns.

ALTER TABLE hidra_topology_pipeline
    ADD COLUMN IF NOT EXISTS name_ar VARCHAR(160),
    ADD COLUMN IF NOT EXISTS name_fr VARCHAR(160),
    ADD COLUMN IF NOT EXISTS name_en VARCHAR(160),
    ADD COLUMN IF NOT EXISTS description_ar VARCHAR(500),
    ADD COLUMN IF NOT EXISTS description_fr VARCHAR(500),
    ADD COLUMN IF NOT EXISTS description_en VARCHAR(500);

UPDATE hidra_topology_pipeline
SET name_ar = COALESCE(name_ar, name),
    name_fr = COALESCE(name_fr, name),
    name_en = COALESCE(name_en, name),
    description_ar = COALESCE(description_ar, description),
    description_fr = COALESCE(description_fr, description),
    description_en = COALESCE(description_en, description);

ALTER TABLE hidra_topology_pipeline
    ALTER COLUMN name_ar SET NOT NULL,
    ALTER COLUMN name_fr SET NOT NULL,
    ALTER COLUMN name_en SET NOT NULL;

ALTER TABLE hidra_topology_pipeline
    DROP COLUMN IF EXISTS name,
    DROP COLUMN IF EXISTS description;

CREATE INDEX IF NOT EXISTS idx_hidra_topology_pipeline_name_ar
    ON hidra_topology_pipeline (name_ar);

CREATE INDEX IF NOT EXISTS idx_hidra_topology_pipeline_name_fr
    ON hidra_topology_pipeline (name_fr);

CREATE INDEX IF NOT EXISTS idx_hidra_topology_pipeline_name_en
    ON hidra_topology_pipeline (name_en);
