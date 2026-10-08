-- Planning-owned metadata only. No business classifications or target data are seeded.
CREATE TABLE hidra_planning_target_value_policy (
    target_type_id varchar(80) PRIMARY KEY
        REFERENCES hidra_planning_catalog_entry(id) ON DELETE RESTRICT,
    representation_kind varchar(16) NOT NULL CHECK (representation_kind IN ('NUMERIC', 'TEXT')),
    active boolean NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT now(),
    updated_at timestamp with time zone NOT NULL DEFAULT now()
);
