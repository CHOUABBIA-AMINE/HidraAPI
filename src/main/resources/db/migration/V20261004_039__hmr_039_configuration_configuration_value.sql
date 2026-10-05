-- HMR-039: ConfigurationValue semantic remediation.
-- Existing migrations remain immutable.

ALTER TABLE hidra_configuration_value
    ADD CONSTRAINT fk_hmr039_configuration_value_definition_version
        FOREIGN KEY (definition_version_id)
        REFERENCES hidra_configuration_definition_version (id)
        ON DELETE RESTRICT;

ALTER TABLE hidra_configuration_value
    ADD CONSTRAINT ck_hmr039_configuration_value_environment
        CHECK (btrim(environment) <> '');
