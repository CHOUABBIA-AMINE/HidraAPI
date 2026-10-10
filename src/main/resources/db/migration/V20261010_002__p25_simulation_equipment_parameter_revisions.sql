-- Simulation owns immutable supplied equipment characteristics, revisions and exact qualifications.
-- No live catalogue, Workflow or asset table is changed; no physical or approval seeds.
CREATE FUNCTION public.hidra_simulation_equipment_revision_deny_mutation() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'Simulation equipment revision evidence is append-only';
END;
$$;

CREATE TABLE public.hidra_simulation_equipment_characteristic_revision (
    kind text NOT NULL CHECK (kind IN ('COMPRESSOR','VALVE')),
    characteristic_id text NOT NULL CHECK (characteristic_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND characteristic_id !~ '^[[:space:]]*$'),
    revision_id text NOT NULL CHECK (revision_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND revision_id !~ '^[[:space:]]*$'),
    payload_format text NOT NULL CHECK ((kind='COMPRESSOR' AND payload_format='HIDRA_SIMULATION_COMPRESSOR_CURVE_V1') OR (kind='VALVE' AND payload_format='HIDRA_SIMULATION_VALVE_CHARACTERISTIC_V1')),
    canonical_payload bytea NOT NULL CHECK (octet_length(canonical_payload) > 0),
    sha256 varchar(64) NOT NULL CHECK (sha256 ~ '^[0-9a-f]{64}$' AND sha256 = encode(sha256(canonical_payload), 'hex')),
    PRIMARY KEY (kind, characteristic_id, revision_id)
);
CREATE TRIGGER hidra_simulation_equipment_characteristic_revision_no_update_delete
BEFORE UPDATE OR DELETE ON public.hidra_simulation_equipment_characteristic_revision
FOR EACH ROW EXECUTE FUNCTION public.hidra_simulation_equipment_revision_deny_mutation();
CREATE TRIGGER hidra_simulation_equipment_characteristic_revision_no_truncate
BEFORE TRUNCATE ON public.hidra_simulation_equipment_characteristic_revision
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_simulation_equipment_revision_deny_mutation();

CREATE TABLE public.hidra_simulation_equipment_parameter_revision (
    source_id text NOT NULL CHECK (source_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND source_id !~ '^[[:space:]]*$'),
    revision_id text NOT NULL CHECK (revision_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND revision_id !~ '^[[:space:]]*$'),
    payload_format text NOT NULL CHECK (payload_format = 'HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V1'),
    canonical_payload bytea NOT NULL CHECK (octet_length(canonical_payload) > 0),
    sha256 varchar(64) NOT NULL CHECK (sha256 ~ '^[0-9a-f]{64}$' AND sha256 = encode(sha256(canonical_payload), 'hex')),
    PRIMARY KEY (source_id, revision_id),
    UNIQUE (sha256)
);
CREATE TRIGGER hidra_simulation_equipment_parameter_revision_no_update_delete
BEFORE UPDATE OR DELETE ON public.hidra_simulation_equipment_parameter_revision
FOR EACH ROW EXECUTE FUNCTION public.hidra_simulation_equipment_revision_deny_mutation();
CREATE TRIGGER hidra_simulation_equipment_parameter_revision_no_truncate
BEFORE TRUNCATE ON public.hidra_simulation_equipment_parameter_revision
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_simulation_equipment_revision_deny_mutation();

CREATE TABLE public.hidra_simulation_equipment_parameter_qualification (
    qualification_id text NOT NULL CHECK (qualification_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND qualification_id !~ '^[[:space:]]*$'),
    source_id text NOT NULL CHECK (source_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND source_id !~ '^[[:space:]]*$'),
    revision_id text NOT NULL CHECK (revision_id !~ '^[\x01-\x20]|[\x01-\x20]$' AND revision_id !~ '^[[:space:]]*$'),
    payload_format text NOT NULL CHECK (payload_format = 'HIDRA_SIMULATION_EQUIPMENT_QUALIFICATION_V1'),
    canonical_payload bytea NOT NULL CHECK (octet_length(canonical_payload) > 0),
    sha256 varchar(64) NOT NULL CHECK (sha256 ~ '^[0-9a-f]{64}$' AND sha256 = encode(sha256(canonical_payload), 'hex')),
    PRIMARY KEY (qualification_id),
    FOREIGN KEY (source_id, revision_id) REFERENCES public.hidra_simulation_equipment_parameter_revision (source_id, revision_id)
);
CREATE TRIGGER hidra_simulation_equipment_parameter_qualification_no_update_delete
BEFORE UPDATE OR DELETE ON public.hidra_simulation_equipment_parameter_qualification
FOR EACH ROW EXECUTE FUNCTION public.hidra_simulation_equipment_revision_deny_mutation();
CREATE TRIGGER hidra_simulation_equipment_parameter_qualification_no_truncate
BEFORE TRUNCATE ON public.hidra_simulation_equipment_parameter_qualification
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_simulation_equipment_revision_deny_mutation();
