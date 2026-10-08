-- HMR-070: local optional references; preserve cross-module scalar ownership.
ALTER TABLE hidra_custody_transfer_ticket ADD CONSTRAINT fk_hmr070_batch_id
    FOREIGN KEY (batch_id) REFERENCES hidra_custody_batch(id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_transfer_ticket VALIDATE CONSTRAINT fk_hmr070_batch_id;
ALTER TABLE hidra_custody_transfer_ticket ADD CONSTRAINT fk_hmr070_quantity_calculation_id
    FOREIGN KEY (quantity_calculation_id) REFERENCES hidra_custody_quantity_calculation(id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_custody_transfer_ticket VALIDATE CONSTRAINT fk_hmr070_quantity_calculation_id;
