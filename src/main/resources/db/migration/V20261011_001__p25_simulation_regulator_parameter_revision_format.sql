-- Forward-only format admission for supplied synthetic regulator revisions.
-- Existing constraint names were recovered from the authenticated 143-migration catalog.
-- No row rewrite, key, qualification, digest, mutation trigger or owner table change.
ALTER TABLE public.hidra_simulation_equipment_characteristic_revision
    DROP CONSTRAINT hidra_simulation_equipment_characteristic_revision_kind_check,
    DROP CONSTRAINT hidra_simulation_equipment_characteristic_revision_check,
    ADD CONSTRAINT hidra_simulation_equipment_characteristic_revision_kind_check
        CHECK (kind IN ('COMPRESSOR','VALVE','REGULATOR')),
    ADD CONSTRAINT hidra_simulation_equipment_characteristic_revision_check
        CHECK ((kind='COMPRESSOR' AND payload_format='HIDRA_SIMULATION_COMPRESSOR_CURVE_V1')
            OR (kind='VALVE' AND payload_format='HIDRA_SIMULATION_VALVE_CHARACTERISTIC_V1')
            OR (kind='REGULATOR' AND payload_format='HIDRA_SIMULATION_REGULATOR_CHARACTERISTIC_V1'));

ALTER TABLE public.hidra_simulation_equipment_parameter_revision
    DROP CONSTRAINT hidra_simulation_equipment_parameter_revis_payload_format_check,
    ADD CONSTRAINT hidra_simulation_equipment_parameter_revis_payload_format_check
        CHECK (payload_format IN ('HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V1',
            'HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V2'));
