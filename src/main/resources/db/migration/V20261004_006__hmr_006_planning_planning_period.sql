-- HMR-006: PlanningPeriod semantic remediation.
-- Existing migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr006_planning_period_code
    ON hidra_planning_period (code);

ALTER TABLE hidra_planning_period
    ADD CONSTRAINT ck_hmr006_planning_period_non_zero_interval
    CHECK (period_start < period_end) NOT VALID;

ALTER TABLE hidra_planning_period
    VALIDATE CONSTRAINT ck_hmr006_planning_period_non_zero_interval;
