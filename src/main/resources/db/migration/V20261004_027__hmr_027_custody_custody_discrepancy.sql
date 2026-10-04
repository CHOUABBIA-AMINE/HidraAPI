-- HMR-027: CustodyDiscrepancy semantic remediation.
-- Existing migrations remain immutable.
-- quantity_unit_id remains optional, but every populated value must resolve
-- to the Custody-owned controlled-value catalog.

ALTER TABLE hidra_custody_discrepancy
    ADD CONSTRAINT fk_hmr027_custody_discrepancy_quantity_unit
    FOREIGN KEY (quantity_unit_id)
    REFERENCES hidra_custody_catalog_entry (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_custody_discrepancy
    VALIDATE CONSTRAINT fk_hmr027_custody_discrepancy_quantity_unit;
