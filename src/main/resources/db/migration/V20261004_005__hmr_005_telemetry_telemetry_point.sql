-- HMR-005: TelemetryPoint semantic remediation.
-- Adds technical compatibility metadata without seeding or inferring business taxonomy values.

ALTER TABLE hidra_telemetry_type_catalog
    ADD COLUMN value_shape varchar(20),
    ADD COLUMN numeric_unit_exempt boolean NOT NULL DEFAULT false;

ALTER TABLE hidra_telemetry_type_catalog
    ADD CONSTRAINT ck_hmr005_signal_value_shape
    CHECK (
        (catalog_name = 'SIGNAL_TYPE' AND value_shape IN ('NUMERIC', 'TEXT', 'BOOLEAN'))
        OR (catalog_name <> 'SIGNAL_TYPE' AND value_shape IS NULL)
    ) NOT VALID;

ALTER TABLE hidra_telemetry_type_catalog
    ADD CONSTRAINT ck_hmr005_point_type_unit_exemption
    CHECK (catalog_name = 'POINT_TYPE' OR numeric_unit_exempt = false) NOT VALID;

ALTER TABLE hidra_telemetry_type_catalog
    VALIDATE CONSTRAINT ck_hmr005_signal_value_shape;

ALTER TABLE hidra_telemetry_type_catalog
    VALIDATE CONSTRAINT ck_hmr005_point_type_unit_exemption;

CREATE UNIQUE INDEX uk_hmr005_telemetry_point_device_code
    ON hidra_telemetry_point (device_id, code);

ALTER TABLE hidra_telemetry_point
    ADD CONSTRAINT fk_hmr005_telemetry_point_unit
    FOREIGN KEY (unit_id) REFERENCES hidra_telemetry_unit (id)
    ON DELETE RESTRICT NOT VALID;

ALTER TABLE hidra_telemetry_point
    VALIDATE CONSTRAINT fk_hmr005_telemetry_point_unit;
