-- HMR-080: empty owner-approved metadata. No provisioning, classifications or data repair.
CREATE TABLE hidra_custody_planning_product_policy (
    catalog_entry_id varchar(80) PRIMARY KEY,
    active boolean NOT NULL,
    approval_reference text NOT NULL CHECK (nullif(btrim(approval_reference),'') IS NOT NULL),
    CONSTRAINT fk_hmr080_product_policy_owner FOREIGN KEY(catalog_entry_id)
        REFERENCES hidra_custody_catalog_entry(id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE hidra_telemetry_planning_unit_role (
    unit_id varchar(80) NOT NULL,
    usage_role varchar(16) NOT NULL CHECK(usage_role IN ('QUANTITY','RATE')),
    active boolean NOT NULL,
    approval_reference text NOT NULL CHECK(nullif(btrim(approval_reference),'') IS NOT NULL),
    PRIMARY KEY(unit_id,usage_role),
    CONSTRAINT fk_hmr080_unit_role_owner FOREIGN KEY(unit_id)
        REFERENCES hidra_telemetry_unit(id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE hidra_telemetry_planning_unit_pair (
    quantity_unit_id varchar(80) NOT NULL,
    rate_unit_id varchar(80) NOT NULL,
    active boolean NOT NULL,
    approval_reference text NOT NULL CHECK(nullif(btrim(approval_reference),'') IS NOT NULL),
    PRIMARY KEY(quantity_unit_id,rate_unit_id),
    CONSTRAINT fk_hmr080_quantity_pair_owner FOREIGN KEY(quantity_unit_id)
        REFERENCES hidra_telemetry_unit(id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_hmr080_rate_pair_owner FOREIGN KEY(rate_unit_id)
        REFERENCES hidra_telemetry_unit(id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
CREATE TABLE hidra_planning_nomination_reference_mapping (
    nomination_id varchar(80) NOT NULL,
    field_name varchar(16) NOT NULL CHECK(field_name IN ('PRODUCT','QUANTITY_UNIT','RATE_UNIT')),
    legacy_id varchar(80) NOT NULL CHECK(nullif(btrim(legacy_id),'') IS NOT NULL),
    canonical_owner_id varchar(80) NOT NULL CHECK(nullif(btrim(canonical_owner_id),'') IS NOT NULL),
    approval_reference text NOT NULL CHECK(nullif(btrim(approval_reference),'') IS NOT NULL),
    PRIMARY KEY(nomination_id,field_name),
    CONSTRAINT fk_hmr080_mapping_nomination FOREIGN KEY(nomination_id)
        REFERENCES hidra_planning_nomination(id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
