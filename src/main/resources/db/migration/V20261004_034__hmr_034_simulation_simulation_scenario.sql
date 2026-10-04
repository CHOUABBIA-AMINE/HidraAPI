-- HMR-034: SimulationScenario semantic remediation.
-- Existing migrations remain immutable.

ALTER TABLE hidra_simulation_scenario
    ADD CONSTRAINT uk_hmr034_simulation_scenario_code UNIQUE (code);

ALTER TABLE hidra_simulation_model_version
    ADD CONSTRAINT uk_hmr034_model_version_parent UNIQUE (id, model_id);

ALTER TABLE hidra_simulation_scenario
    ADD CONSTRAINT fk_hmr034_scenario_model_version_parent
        FOREIGN KEY (model_version_id, model_id)
        REFERENCES hidra_simulation_model_version (id, model_id)
        ON DELETE RESTRICT;

ALTER TABLE hidra_simulation_scenario
    ADD CONSTRAINT ck_hmr034_scenario_required_text
    CHECK (btrim(name_fr) <> '' AND btrim(created_by_display_name_snapshot) <> '');

DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM hidra_simulation_scenario scenario
        LEFT JOIN hidra_simulation_catalog_entry catalog
          ON catalog.id = scenario.scenario_type_id
        WHERE catalog.id IS NULL
           OR catalog.catalog_name <> 'SIMULATION_SCENARIO_TYPE'
    ) THEN
        RAISE EXCEPTION
            'HMR-034 cannot retain SimulationScenario rows outside SIMULATION_SCENARIO_TYPE'
            USING ERRCODE = '23514';
    END IF;
END;
$$;

CREATE OR REPLACE FUNCTION hmr034_guard_simulation_scenario_type()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM hidra_simulation_catalog_entry catalog
        WHERE catalog.id = NEW.scenario_type_id
          AND catalog.catalog_name = 'SIMULATION_SCENARIO_TYPE'
    ) THEN
        RAISE EXCEPTION
            'SimulationScenario scenario_type_id must resolve to SIMULATION_SCENARIO_TYPE: %',
            NEW.scenario_type_id
            USING ERRCODE = '23514';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr034_guard_simulation_scenario_type
    BEFORE INSERT OR UPDATE OF scenario_type_id
    ON hidra_simulation_scenario
    FOR EACH ROW
    EXECUTE FUNCTION hmr034_guard_simulation_scenario_type();
