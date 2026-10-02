-- HIDRA organization multilingual compatibility-column retirement
-- Module: organization
-- Roadmap: ORG-047
--
-- Destructive only after fail-closed parity checks. No language is guessed.
-- Non-null ambiguous legacy descriptions must exactly match one embedded language.
-- Legacy shift name must exactly match the runtime compatibility projection
-- used during cutover: name_en -> name_fr -> name_ar -> code.

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type
        WHERE description IS NOT NULL
          AND description IS DISTINCT FROM description_ar
          AND description IS DISTINCT FROM description_fr
          AND description IS DISTINCT FROM description_en
    ) THEN
        RAISE EXCEPTION
            'ORG-047 parity failed: legacy organization unit type description is not preserved in embedded multilingual descriptions';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_position
        WHERE description IS NOT NULL
          AND description IS DISTINCT FROM description_ar
          AND description IS DISTINCT FROM description_fr
          AND description IS DISTINCT FROM description_en
    ) THEN
        RAISE EXCEPTION
            'ORG-047 parity failed: legacy position description is not preserved in embedded multilingual descriptions';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_shift
        WHERE name IS DISTINCT FROM (
            CASE
                WHEN name_en IS NOT NULL AND btrim(name_en) <> '' THEN btrim(name_en)
                WHEN name_fr IS NOT NULL AND btrim(name_fr) <> '' THEN btrim(name_fr)
                WHEN name_ar IS NOT NULL AND btrim(name_ar) <> '' THEN btrim(name_ar)
                ELSE code
            END
        )
    ) THEN
        RAISE EXCEPTION
            'ORG-047 parity failed: legacy shift name differs from canonical multilingual compatibility projection';
    END IF;
END
$$;

ALTER TABLE hidra_org_unit_type DROP COLUMN description;
ALTER TABLE hidra_org_position DROP COLUMN description;
ALTER TABLE hidra_org_shift DROP COLUMN name;
