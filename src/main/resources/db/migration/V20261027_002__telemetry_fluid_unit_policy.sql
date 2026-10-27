-- HAPI-595A: empty owner policy. No unit/product seeds, role/user grants or conversion execution.
CREATE TABLE public.hidra_telemetry_fluid_unit_policy (
 unit_id varchar(80) NOT NULL,
 usage_role varchar(80) NOT NULL,
 expected_dimension varchar(80) NOT NULL,
 active boolean NOT NULL,
 created_at timestamptz NOT NULL,
 updated_at timestamptz NOT NULL,
 PRIMARY KEY(unit_id,usage_role),
 CONSTRAINT hidra_fluid_unit_owner_fk FOREIGN KEY(unit_id) REFERENCES public.hidra_telemetry_unit(id),
 CONSTRAINT hidra_fluid_unit_role_ck CHECK(usage_role IN ('DENSITY','DYNAMIC_VISCOSITY','COMPRESSIBILITY_FACTOR','ABSOLUTE_PRESSURE','ABSOLUTE_TEMPERATURE')),
 CONSTRAINT hidra_fluid_unit_dimension_ck CHECK(length(btrim(expected_dimension))>0 AND expected_dimension=btrim(expected_dimension)),
 CONSTRAINT hidra_fluid_unit_times_ck CHECK(updated_at>=created_at)
);
