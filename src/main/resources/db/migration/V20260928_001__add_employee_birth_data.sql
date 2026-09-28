-- HIDRA employee canonical birth data persistence
-- Module: organization
-- Roadmap: HRA-013
--
-- Adds optional canonical birth state to the Organization-owned employee table.
-- birth_locality_id is an optional same-module reference to the normalized locality
-- hierarchy. Free-text multilingual birthplace values remain independent so foreign
-- or historical places do not require fabricated locality records.

ALTER TABLE hidra_org_employee
    ADD COLUMN date_of_birth date,
    ADD COLUMN birth_locality_id varchar(80),
    ADD COLUMN birth_place_ar varchar(255),
    ADD COLUMN birth_place_fr varchar(255),
    ADD COLUMN birth_place_en varchar(255);

CREATE INDEX ix_hidra_org_employee_birth_locality_id
    ON hidra_org_employee (birth_locality_id);

ALTER TABLE hidra_org_employee
    ADD CONSTRAINT fk_org_employee_birth_locality
    FOREIGN KEY (birth_locality_id)
    REFERENCES hidra_org_administrative_locality (id)
    ON DELETE RESTRICT;
