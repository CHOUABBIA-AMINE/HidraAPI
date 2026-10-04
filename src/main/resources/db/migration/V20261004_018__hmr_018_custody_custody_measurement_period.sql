-- HMR-018: CustodyMeasurementPeriod semantic remediation.
-- Existing migrations remain immutable.

CREATE UNIQUE INDEX uk_hmr018_custody_agreement_id_transfer_point
    ON hidra_custody_agreement (id, transfer_point_id);

ALTER TABLE hidra_custody_measurement_period
    ADD CONSTRAINT fk_hmr018_custody_period_agreement_transfer_point
    FOREIGN KEY (agreement_id, transfer_point_id)
    REFERENCES hidra_custody_agreement (id, transfer_point_id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_custody_measurement_period
    VALIDATE CONSTRAINT fk_hmr018_custody_period_agreement_transfer_point;
