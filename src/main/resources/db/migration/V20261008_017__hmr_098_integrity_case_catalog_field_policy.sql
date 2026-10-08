-- HMR-098: owner-approved field-to-family metadata; deliberately unseeded.
-- CASE_TYPE is an internal field role, not an inferred catalog_name.
CREATE TABLE hidra_integrity_catalog_field_policy (
    field_role varchar(80) PRIMARY KEY,
    catalog_name varchar(80) NOT NULL CHECK (btrim(catalog_name) <> ''),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
-- Existing cases may block forward 018 until actual approved metadata is provisioned.
