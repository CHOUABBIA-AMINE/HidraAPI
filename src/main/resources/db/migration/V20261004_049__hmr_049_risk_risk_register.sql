-- HMR-049: RiskRegister semantic remediation.
-- Cross-module Organization/Topology/Audit semantics remain application-authoritative.
-- review_frequency_id intentionally remains opaque until a defensible catalog family is registered.

ALTER TABLE hidra_risk_register
    ADD CONSTRAINT ck_hmr049_risk_register_scope_type_nonblank
    CHECK (btrim(scope_type) <> '')
    NOT VALID;

ALTER TABLE hidra_risk_register
    VALIDATE CONSTRAINT ck_hmr049_risk_register_scope_type_nonblank;

ALTER TABLE hidra_risk_register
    ADD CONSTRAINT ck_hmr049_risk_register_scope_id_nonblank
    CHECK (btrim(scope_id) <> '')
    NOT VALID;

ALTER TABLE hidra_risk_register
    VALIDATE CONSTRAINT ck_hmr049_risk_register_scope_id_nonblank;

ALTER TABLE hidra_risk_register
    ADD CONSTRAINT ck_hmr049_risk_register_owner_unit_nonblank
    CHECK (owner_organization_unit_id IS NULL OR btrim(owner_organization_unit_id) <> '')
    NOT VALID;

ALTER TABLE hidra_risk_register
    VALIDATE CONSTRAINT ck_hmr049_risk_register_owner_unit_nonblank;

ALTER TABLE hidra_risk_register
    ADD CONSTRAINT ck_hmr049_risk_register_review_frequency_nonblank
    CHECK (review_frequency_id IS NULL OR btrim(review_frequency_id) <> '')
    NOT VALID;

ALTER TABLE hidra_risk_register
    VALIDATE CONSTRAINT ck_hmr049_risk_register_review_frequency_nonblank;
