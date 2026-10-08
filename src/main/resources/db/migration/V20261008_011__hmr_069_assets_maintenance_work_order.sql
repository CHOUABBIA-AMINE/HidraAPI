-- HMR-069: local optional references; preserve cross-module scalar ownership.
ALTER TABLE hidra_asset_maintenance_work_order ADD CONSTRAINT fk_hmr069_maintenance_plan_id
    FOREIGN KEY (maintenance_plan_id) REFERENCES hidra_asset_maintenance_plan(id) ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_asset_maintenance_work_order VALIDATE CONSTRAINT fk_hmr069_maintenance_plan_id;
ALTER TABLE hidra_asset_maintenance_work_order ADD CONSTRAINT ck_hmr069_title
    CHECK (title IS NOT NULL AND btrim(title) <> '') NOT VALID;
ALTER TABLE hidra_asset_maintenance_work_order VALIDATE CONSTRAINT ck_hmr069_title;
