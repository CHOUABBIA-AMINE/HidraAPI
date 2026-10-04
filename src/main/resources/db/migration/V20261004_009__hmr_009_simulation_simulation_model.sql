-- HMR-009: SimulationModel semantic remediation.
-- Existing migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr009_simulation_model_code
    ON hidra_simulation_model (code);

ALTER TABLE hidra_simulation_model
    ADD CONSTRAINT ck_hmr009_simulation_model_name_fr
    CHECK (btrim(name_fr) <> '') NOT VALID;

ALTER TABLE hidra_simulation_model
    ADD CONSTRAINT ck_hmr009_simulation_model_scope_type
    CHECK (topology_scope_type IN (
        'PIPELINE_SYSTEM',
        'PIPELINE',
        'SEGMENT_GROUP',
        'FACILITY_NETWORK'
    )) NOT VALID;

ALTER TABLE hidra_simulation_model
    VALIDATE CONSTRAINT ck_hmr009_simulation_model_name_fr;

ALTER TABLE hidra_simulation_model
    VALIDATE CONSTRAINT ck_hmr009_simulation_model_scope_type;
