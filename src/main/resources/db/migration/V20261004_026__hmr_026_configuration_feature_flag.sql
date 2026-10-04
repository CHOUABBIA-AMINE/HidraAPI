-- HMR-026: FeatureFlag semantic remediation.
-- Existing migrations remain immutable.
-- owning_module is the required bounded-context ownership discriminator.

ALTER TABLE hidra_configuration_feature_flag
    ADD CONSTRAINT ck_hmr026_feature_flag_owning_module_nonblank
    CHECK (btrim(owning_module) <> '')
    NOT VALID;

ALTER TABLE hidra_configuration_feature_flag
    VALIDATE CONSTRAINT ck_hmr026_feature_flag_owning_module_nonblank;
