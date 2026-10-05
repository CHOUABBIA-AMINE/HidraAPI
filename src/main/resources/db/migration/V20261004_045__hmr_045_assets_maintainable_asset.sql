-- HMR-045: MaintainableAsset semantic remediation.
-- Cross-module Topology/Organization/Party references remain application-authoritative.
-- Only same-module Assets references receive database foreign keys.

ALTER TABLE hidra_asset_maintainable_asset
    ADD CONSTRAINT fk_hmr045_maintainable_asset_parent
    FOREIGN KEY (parent_asset_id)
    REFERENCES hidra_asset_maintainable_asset (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_asset_maintainable_asset
    VALIDATE CONSTRAINT fk_hmr045_maintainable_asset_parent;

ALTER TABLE hidra_asset_maintainable_asset
    ADD CONSTRAINT fk_hmr045_maintainable_asset_model
    FOREIGN KEY (model_id)
    REFERENCES hidra_asset_model (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_asset_maintainable_asset
    VALIDATE CONSTRAINT fk_hmr045_maintainable_asset_model;

ALTER TABLE hidra_asset_maintainable_asset
    ADD CONSTRAINT fk_hmr045_maintainable_asset_serial_identity
    FOREIGN KEY (serial_identity_id)
    REFERENCES hidra_asset_serial_identity (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_asset_maintainable_asset
    VALIDATE CONSTRAINT fk_hmr045_maintainable_asset_serial_identity;
