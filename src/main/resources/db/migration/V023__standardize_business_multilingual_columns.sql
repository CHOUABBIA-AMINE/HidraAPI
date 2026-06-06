-- HidraAPI Correction 02 persistence multilingual label alignment.
-- COR2-010 — refactor(persistence): standardize multilingual columns.
--
-- Converts remaining business display labels standardized by COR2-009 from single columns
-- into explicit Arabic, French, and English persistence columns.

ALTER TABLE hidra_identity_role
    ADD COLUMN IF NOT EXISTS name_ar VARCHAR(100),
    ADD COLUMN IF NOT EXISTS name_fr VARCHAR(100),
    ADD COLUMN IF NOT EXISTS name_en VARCHAR(100);

UPDATE hidra_identity_role
SET name_ar = COALESCE(name_ar, name),
    name_fr = COALESCE(name_fr, name),
    name_en = COALESCE(name_en, name)
WHERE name IS NOT NULL;

ALTER TABLE hidra_identity_role
    ALTER COLUMN name_ar SET NOT NULL,
    ALTER COLUMN name_fr SET NOT NULL,
    ALTER COLUMN name_en SET NOT NULL;

ALTER TABLE hidra_identity_role
    DROP COLUMN IF EXISTS name;

ALTER TABLE hidra_org_unit
    ADD COLUMN IF NOT EXISTS name_ar VARCHAR(160),
    ADD COLUMN IF NOT EXISTS name_fr VARCHAR(160),
    ADD COLUMN IF NOT EXISTS name_en VARCHAR(160);

UPDATE hidra_org_unit
SET name_ar = COALESCE(name_ar, name),
    name_fr = COALESCE(name_fr, name),
    name_en = COALESCE(name_en, name)
WHERE name IS NOT NULL;

ALTER TABLE hidra_org_unit
    ALTER COLUMN name_ar SET NOT NULL,
    ALTER COLUMN name_fr SET NOT NULL,
    ALTER COLUMN name_en SET NOT NULL;

ALTER TABLE hidra_org_unit
    DROP COLUMN IF EXISTS name;

ALTER TABLE hidra_org_position
    ADD COLUMN IF NOT EXISTS title_ar VARCHAR(120),
    ADD COLUMN IF NOT EXISTS title_fr VARCHAR(120),
    ADD COLUMN IF NOT EXISTS title_en VARCHAR(120),
    ADD COLUMN IF NOT EXISTS description_ar VARCHAR(500),
    ADD COLUMN IF NOT EXISTS description_fr VARCHAR(500),
    ADD COLUMN IF NOT EXISTS description_en VARCHAR(500);

UPDATE hidra_org_position
SET title_ar = COALESCE(title_ar, title),
    title_fr = COALESCE(title_fr, title),
    title_en = COALESCE(title_en, title)
WHERE title IS NOT NULL;

UPDATE hidra_org_position
SET description_ar = COALESCE(description_ar, description),
    description_fr = COALESCE(description_fr, description),
    description_en = COALESCE(description_en, description)
WHERE description IS NOT NULL;

ALTER TABLE hidra_org_position
    ALTER COLUMN title_ar SET NOT NULL,
    ALTER COLUMN title_fr SET NOT NULL,
    ALTER COLUMN title_en SET NOT NULL;

ALTER TABLE hidra_org_position
    DROP COLUMN IF EXISTS title,
    DROP COLUMN IF EXISTS description;
