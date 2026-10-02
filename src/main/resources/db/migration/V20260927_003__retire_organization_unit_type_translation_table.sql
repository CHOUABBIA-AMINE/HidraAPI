-- HIDRA organization legacy unit-type translation table retirement
-- Module: organization
-- Roadmap: ORG-039
--
-- This migration is intentionally destructive only after a fail-closed parity check.
-- It never modifies the embedded Arabic/French/English fields and never guesses data.
-- Legacy language-ambiguous base columns remain for their separately governed lifecycle.

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type_translation translation
        WHERE lower(btrim(translation.language_code)) NOT IN ('ar', 'fr', 'en')
    ) THEN
        RAISE EXCEPTION
            'ORG-039 parity failed: unsupported organization unit type language_code remains before translation-table retirement';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type_translation translation
        LEFT JOIN hidra_org_unit_type unit_type
            ON unit_type.id = translation.unit_type_id
        WHERE unit_type.id IS NULL
    ) THEN
        RAISE EXCEPTION
            'ORG-039 parity failed: orphan organization unit type translation remains before translation-table retirement';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_org_unit_type_translation translation
        JOIN hidra_org_unit_type unit_type
            ON unit_type.id = translation.unit_type_id
        WHERE (
                  lower(btrim(translation.language_code)) = 'ar'
                  AND (
                      unit_type.name_ar IS DISTINCT FROM translation.label
                      OR unit_type.description_ar IS DISTINCT FROM translation.description
                  )
              )
           OR (
                  lower(btrim(translation.language_code)) = 'fr'
                  AND (
                      unit_type.name_fr IS DISTINCT FROM translation.label
                      OR unit_type.description_fr IS DISTINCT FROM translation.description
                  )
              )
           OR (
                  lower(btrim(translation.language_code)) = 'en'
                  AND (
                      unit_type.name_en IS DISTINCT FROM translation.label
                      OR unit_type.description_en IS DISTINCT FROM translation.description
                  )
              )
    ) THEN
        RAISE EXCEPTION
            'ORG-039 parity failed: embedded organization unit type multilingual fields differ from legacy translation data';
    END IF;
END
$$;

DROP TABLE hidra_org_unit_type_translation;
