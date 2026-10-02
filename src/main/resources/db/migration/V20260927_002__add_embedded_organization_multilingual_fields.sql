-- HIDRA organization embedded multilingual fields
-- Additive migration: Arabic/French/English business labels live on the owning organization entity.
-- Module: organization
--
-- IMPORTANT:
-- 1. Existing applied migrations are immutable.
-- 2. Legacy unit-type translation rows/table are retained for recovery and consumer cutover.
-- 3. Legacy base description/name columns are retained because their language is not authoritative.
-- 4. Backfill is performed only for deterministic normalized ar/fr/en unit-type translation rows.
-- 5. This migration fails before schema/data changes when legacy unit-type translations are unsafe.

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type_translation
        WHERE lower(btrim(language_code)) NOT IN ('ar', 'fr', 'en')
    ) THEN
        RAISE EXCEPTION
            'ORG-036 preflight failed: unsupported organization unit type language_code; expected only ar, fr, en after trim/lower normalization';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type_translation
        GROUP BY unit_type_id, lower(btrim(language_code))
        HAVING count(*) > 1
    ) THEN
        RAISE EXCEPTION
            'ORG-036 preflight failed: duplicate organization unit type translations for the same unit_type_id and normalized language_code';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type_translation translation
        LEFT JOIN hidra_org_unit_type unit_type
            ON unit_type.id = translation.unit_type_id
        WHERE unit_type.id IS NULL
    ) THEN
        RAISE EXCEPTION
            'ORG-036 preflight failed: orphan organization unit type translation references an unknown unit_type_id';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type unit_type
        WHERE NOT EXISTS (
                  SELECT 1
                  FROM hidra_org_unit_type_translation translation
                  WHERE translation.unit_type_id = unit_type.id
                    AND lower(btrim(translation.language_code)) = 'ar'
              )
           OR NOT EXISTS (
                  SELECT 1
                  FROM hidra_org_unit_type_translation translation
                  WHERE translation.unit_type_id = unit_type.id
                    AND lower(btrim(translation.language_code)) = 'fr'
              )
           OR NOT EXISTS (
                  SELECT 1
                  FROM hidra_org_unit_type_translation translation
                  WHERE translation.unit_type_id = unit_type.id
                    AND lower(btrim(translation.language_code)) = 'en'
              )
    ) THEN
        RAISE EXCEPTION
            'ORG-036 preflight failed: every existing organization unit type must have deterministic ar, fr and en translation rows before embedded-field backfill';
    END IF;
END
$$;

ALTER TABLE hidra_org_unit_type
    ADD COLUMN name_ar varchar(255),
    ADD COLUMN name_fr varchar(255),
    ADD COLUMN name_en varchar(255),
    ADD COLUMN description_ar text,
    ADD COLUMN description_fr text,
    ADD COLUMN description_en text;

ALTER TABLE hidra_org_position
    ADD COLUMN description_ar text,
    ADD COLUMN description_fr text,
    ADD COLUMN description_en text;

ALTER TABLE hidra_org_shift
    ADD COLUMN name_ar varchar(255),
    ADD COLUMN name_fr varchar(255),
    ADD COLUMN name_en varchar(255);

UPDATE hidra_org_unit_type unit_type
SET name_ar = translation.label,
    description_ar = translation.description
FROM hidra_org_unit_type_translation translation
WHERE translation.unit_type_id = unit_type.id
  AND lower(btrim(translation.language_code)) = 'ar';

UPDATE hidra_org_unit_type unit_type
SET name_fr = translation.label,
    description_fr = translation.description
FROM hidra_org_unit_type_translation translation
WHERE translation.unit_type_id = unit_type.id
  AND lower(btrim(translation.language_code)) = 'fr';

UPDATE hidra_org_unit_type unit_type
SET name_en = translation.label,
    description_en = translation.description
FROM hidra_org_unit_type_translation translation
WHERE translation.unit_type_id = unit_type.id
  AND lower(btrim(translation.language_code)) = 'en';

-- Deliberately no backfill from:
--   hidra_org_unit_type.description
--   hidra_org_position.description
--   hidra_org_shift.name
-- Their language is not proven by repository evidence. They remain available through cutover.
