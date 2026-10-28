-- HAPI-599 Package A: owner-private independent physical unit and effective installation storage.
-- No public writer/grant, Party authorization or station-incidence qualification is created here.
-- Existing Flyway scripts and historical Topology Equipment/Assets identities are unchanged.
CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE hidra_topology_installed_unit (
    id varchar(80) PRIMARY KEY,
    company_party_id varchar(80) NOT NULL,
    equipment_kind_id varchar(80) NOT NULL,
    manufacturer_party_value text,
    manufacturer_party_verification varchar(10) NOT NULL,
    manufacturer_party_source text,
    manufacturer_model_value text,
    manufacturer_model_verification varchar(10) NOT NULL,
    manufacturer_model_source text,
    serial_value text,
    serial_verification varchar(10) NOT NULL,
    serial_source text,
    maintainable_asset_id varchar(80),
    created_at timestamptz NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT uq_installed_unit_company UNIQUE(id,company_party_id),
    CONSTRAINT ck_installed_unit_identity CHECK (
        length(btrim(id))>0 AND length(btrim(company_party_id))>0 AND
        length(btrim(equipment_kind_id))>0 AND id=btrim(id) AND
        company_party_id=btrim(company_party_id) AND equipment_kind_id=btrim(equipment_kind_id)),
    CONSTRAINT ck_installed_unit_manufacturer CHECK (
        (manufacturer_party_verification='UNKNOWN' AND manufacturer_party_value IS NULL AND manufacturer_party_source IS NULL) OR
        (manufacturer_party_verification IN ('UNVERIFIED','VERIFIED') AND
         nullif(btrim(manufacturer_party_value),'') IS NOT NULL AND
         nullif(btrim(manufacturer_party_source),'') IS NOT NULL)),
    CONSTRAINT ck_installed_unit_model CHECK (
        (manufacturer_model_verification='UNKNOWN' AND manufacturer_model_value IS NULL AND manufacturer_model_source IS NULL) OR
        (manufacturer_model_verification IN ('UNVERIFIED','VERIFIED') AND
         nullif(btrim(manufacturer_model_value),'') IS NOT NULL AND
         nullif(btrim(manufacturer_model_source),'') IS NOT NULL)),
    CONSTRAINT ck_installed_unit_serial CHECK (
        (serial_verification='UNKNOWN' AND serial_value IS NULL AND serial_source IS NULL) OR
        (serial_verification IN ('UNVERIFIED','VERIFIED') AND
         nullif(btrim(serial_value),'') IS NOT NULL AND nullif(btrim(serial_source),'') IS NOT NULL)),
    CONSTRAINT ck_installed_unit_model_owner CHECK (
        manufacturer_model_verification='UNKNOWN' OR
        (manufacturer_party_verification IN ('UNVERIFIED','VERIFIED') AND
         (manufacturer_model_verification<>'VERIFIED' OR manufacturer_party_verification='VERIFIED')))
);

CREATE TABLE hidra_topology_installed_unit_installation (
    installation_id varchar(80) PRIMARY KEY,
    unit_id varchar(80) NOT NULL,
    position_id varchar(80) NOT NULL REFERENCES hidra_topology_equipment(id),
    company_party_id varchar(80) NOT NULL,
    effective_from timestamptz NOT NULL,
    effective_until timestamptz,
    recorded_at timestamptz NOT NULL,
    evidence_reference text NOT NULL,
    revision bigint NOT NULL,
    CONSTRAINT fk_installed_unit_installation_owner FOREIGN KEY (unit_id,company_party_id)
        REFERENCES hidra_topology_installed_unit(id,company_party_id),
    CONSTRAINT ck_installed_unit_installation_fields CHECK (
        length(btrim(installation_id))>0 AND installation_id=btrim(installation_id) AND
        length(btrim(position_id))>0 AND position_id=btrim(position_id) AND
        nullif(btrim(evidence_reference),'') IS NOT NULL AND
        revision>0 AND (effective_until IS NULL OR effective_until>effective_from)),
    CONSTRAINT ex_installed_unit_effective_unit EXCLUDE USING gist
        (unit_id WITH =, tstzrange(effective_from,effective_until,'[)') WITH &&),
    CONSTRAINT ex_installed_unit_effective_position EXCLUDE USING gist
        (position_id WITH =, tstzrange(effective_from,effective_until,'[)') WITH &&)
);
CREATE INDEX ix_installed_unit_installation_owner ON hidra_topology_installed_unit_installation(company_party_id,unit_id);

-- The effective projection may close once; an immutable event retains each version.
CREATE TABLE hidra_topology_installed_unit_installation_event (
    installation_id varchar(80) NOT NULL,
    revision bigint NOT NULL,
    unit_id varchar(80) NOT NULL,
    position_id varchar(80) NOT NULL,
    company_party_id varchar(80) NOT NULL,
    effective_from timestamptz NOT NULL,
    effective_until timestamptz,
    recorded_at timestamptz NOT NULL,
    evidence_reference text NOT NULL,
    event_at timestamptz NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT pk_installed_unit_installation_event PRIMARY KEY(installation_id,revision),
    CONSTRAINT fk_installed_unit_installation_event_head FOREIGN KEY(installation_id)
        REFERENCES hidra_topology_installed_unit_installation(installation_id),
    CONSTRAINT ck_installed_unit_event_period CHECK (
        revision>0 AND (effective_until IS NULL OR effective_until>effective_from))
);

CREATE FUNCTION hidra_topology_installed_unit_immutable() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'Installed unit identity and installation evidence are immutable' USING ERRCODE='23514';
END;
$$;

CREATE TRIGGER topology_installed_unit_immutable
BEFORE UPDATE OR DELETE ON hidra_topology_installed_unit
FOR EACH ROW EXECUTE FUNCTION hidra_topology_installed_unit_immutable();
CREATE TRIGGER topology_installed_unit_no_truncate
BEFORE TRUNCATE ON hidra_topology_installed_unit
FOR EACH STATEMENT EXECUTE FUNCTION hidra_topology_installed_unit_immutable();

CREATE FUNCTION hidra_topology_installed_installation_guard() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='DELETE' THEN
        RAISE EXCEPTION 'Installation history cannot be deleted' USING ERRCODE='23514';
    END IF;
    IF TG_OP='UPDATE' THEN
        IF NEW.installation_id IS DISTINCT FROM OLD.installation_id
           OR NEW.unit_id IS DISTINCT FROM OLD.unit_id
           OR NEW.position_id IS DISTINCT FROM OLD.position_id
           OR NEW.company_party_id IS DISTINCT FROM OLD.company_party_id
           OR NEW.effective_from IS DISTINCT FROM OLD.effective_from
           OR NEW.recorded_at IS DISTINCT FROM OLD.recorded_at
           OR NEW.evidence_reference IS DISTINCT FROM OLD.evidence_reference
           OR OLD.effective_until IS NOT NULL
           OR NEW.effective_until IS NULL
           OR NEW.effective_until<=OLD.effective_from
           OR NEW.revision<>OLD.revision+1 THEN
            RAISE EXCEPTION 'Only one versioned installation closure is permitted' USING ERRCODE='23514';
        END IF;
    ELSIF NEW.revision<>1 THEN
        RAISE EXCEPTION 'First installation version must be one' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER topology_installed_installation_guard
BEFORE INSERT OR UPDATE OR DELETE ON hidra_topology_installed_unit_installation
FOR EACH ROW EXECUTE FUNCTION hidra_topology_installed_installation_guard();
CREATE TRIGGER topology_installed_installation_no_truncate
BEFORE TRUNCATE ON hidra_topology_installed_unit_installation
FOR EACH STATEMENT EXECUTE FUNCTION hidra_topology_installed_unit_immutable();

CREATE FUNCTION hidra_topology_installed_installation_event_append() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO hidra_topology_installed_unit_installation_event (
        installation_id,revision,unit_id,position_id,company_party_id,
        effective_from,effective_until,recorded_at,evidence_reference
    ) VALUES (
        NEW.installation_id,NEW.revision,NEW.unit_id,NEW.position_id,NEW.company_party_id,
        NEW.effective_from,NEW.effective_until,NEW.recorded_at,NEW.evidence_reference
    );
    RETURN NULL;
END;
$$;
CREATE TRIGGER topology_installed_installation_event_append
AFTER INSERT OR UPDATE ON hidra_topology_installed_unit_installation
FOR EACH ROW EXECUTE FUNCTION hidra_topology_installed_installation_event_append();

CREATE FUNCTION hidra_topology_installed_installation_event_guard() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP <> 'INSERT' OR pg_trigger_depth() <> 2 THEN
        RAISE EXCEPTION 'Installation events can only be appended by the head trigger' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER topology_installed_installation_event_guard
BEFORE INSERT OR UPDATE OR DELETE ON hidra_topology_installed_unit_installation_event
FOR EACH ROW EXECUTE FUNCTION hidra_topology_installed_installation_event_guard();
CREATE TRIGGER topology_installed_installation_event_no_truncate
BEFORE TRUNCATE ON hidra_topology_installed_unit_installation_event
FOR EACH STATEMENT EXECUTE FUNCTION hidra_topology_installed_unit_immutable();
