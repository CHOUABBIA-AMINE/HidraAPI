-- HMR-029: RiskMatrixCell semantic remediation.
-- Existing migrations remain immutable.
-- Enforce deterministic matrix coordinates, nonnegative scores, and exact
-- likelihood/consequence Risk catalog-family semantics.

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_risk_matrix_cell
        GROUP BY risk_matrix_id, likelihood_level_id, consequence_level_id
        HAVING count(*) > 1
    ) THEN
        RAISE EXCEPTION
            'HMR-029 cannot retain duplicate RiskMatrixCell coordinates'
            USING ERRCODE = '23505';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_risk_matrix_cell
        WHERE score_value < 0
    ) THEN
        RAISE EXCEPTION
            'HMR-029 cannot retain RiskMatrixCell rows with negative score values'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_risk_matrix_cell cell
        WHERE NOT EXISTS (
            SELECT 1
            FROM hidra_risk_catalog_entry entry
            WHERE entry.id = cell.likelihood_level_id
              AND entry.catalog_name = 'RISK_LIKELIHOOD_LEVEL'
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-029 cannot retain RiskMatrixCell rows with invalid likelihood catalog family'
            USING ERRCODE = '23514';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM hidra_risk_matrix_cell cell
        WHERE NOT EXISTS (
            SELECT 1
            FROM hidra_risk_catalog_entry entry
            WHERE entry.id = cell.consequence_level_id
              AND entry.catalog_name = 'RISK_CONSEQUENCE_LEVEL'
        )
    ) THEN
        RAISE EXCEPTION
            'HMR-029 cannot retain RiskMatrixCell rows with invalid consequence catalog family'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

CREATE UNIQUE INDEX uq_hmr029_risk_matrix_cell_coordinate
    ON hidra_risk_matrix_cell (
        risk_matrix_id,
        likelihood_level_id,
        consequence_level_id
    );

ALTER TABLE hidra_risk_matrix_cell
    ADD CONSTRAINT ck_hmr029_risk_matrix_cell_score_nonnegative
    CHECK (score_value >= 0)
    NOT VALID;

ALTER TABLE hidra_risk_matrix_cell
    VALIDATE CONSTRAINT ck_hmr029_risk_matrix_cell_score_nonnegative;

CREATE OR REPLACE FUNCTION hmr029_validate_risk_matrix_cell_catalog_families()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_risk_catalog_entry entry
        WHERE entry.id = NEW.likelihood_level_id
          AND entry.catalog_name = 'RISK_LIKELIHOOD_LEVEL'
    ) THEN
        RAISE EXCEPTION
            'RiskMatrixCell likelihood_level_id must belong to RISK_LIKELIHOOD_LEVEL: %',
            NEW.likelihood_level_id
            USING ERRCODE = '23514';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM hidra_risk_catalog_entry entry
        WHERE entry.id = NEW.consequence_level_id
          AND entry.catalog_name = 'RISK_CONSEQUENCE_LEVEL'
    ) THEN
        RAISE EXCEPTION
            'RiskMatrixCell consequence_level_id must belong to RISK_CONSEQUENCE_LEVEL: %',
            NEW.consequence_level_id
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr029_risk_matrix_cell_catalog_families
    BEFORE INSERT OR UPDATE OF likelihood_level_id, consequence_level_id
    ON hidra_risk_matrix_cell
    FOR EACH ROW
    EXECUTE FUNCTION hmr029_validate_risk_matrix_cell_catalog_families();
