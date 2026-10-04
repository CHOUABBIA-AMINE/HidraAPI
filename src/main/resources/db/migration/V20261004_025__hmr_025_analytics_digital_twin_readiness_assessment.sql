-- HMR-025: DigitalTwinReadinessAssessment semantic remediation.
-- Existing migrations remain immutable.
-- DigitalTwinReadinessStatus is the authoritative bounded readiness vocabulary;
-- READINESS_STATUS is not an AnalyticsCatalogEntry family.

ALTER TABLE hidra_analytics_digital_twin_readiness_assessment
    ADD CONSTRAINT ck_hmr025_readiness_scope_type_nonblank
    CHECK (btrim(scope_type) <> '')
    NOT VALID;

ALTER TABLE hidra_analytics_digital_twin_readiness_assessment
    ADD CONSTRAINT ck_hmr025_readiness_status_bounded
    CHECK (
        readiness_status IN (
            'NOT_READY',
            'PARTIAL',
            'READY',
            'ADVANCED',
            'UNKNOWN'
        )
    )
    NOT VALID;

ALTER TABLE hidra_analytics_digital_twin_readiness_assessment
    VALIDATE CONSTRAINT ck_hmr025_readiness_scope_type_nonblank;

ALTER TABLE hidra_analytics_digital_twin_readiness_assessment
    VALIDATE CONSTRAINT ck_hmr025_readiness_status_bounded;
