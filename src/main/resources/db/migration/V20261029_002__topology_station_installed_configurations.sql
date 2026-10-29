-- HAPI-600 Package A: Topology-owned immutable, versioned installed-station declarations.
-- Protected SAVE/read/replay authorization and native writer are Package B.
-- No current station graph, installed unit or manufacturer evidence is modified.
CREATE TABLE public.hidra_topology_station_configuration_revision (
    company_party_id varchar(80) NOT NULL,
    facility_id varchar(80) NOT NULL,
    revision_id varchar(80) NOT NULL,
    request_id varchar(80) NOT NULL,
    expected_epoch bigint NOT NULL,
    selected_epoch bigint NOT NULL,
    graph_head_epoch bigint NOT NULL,
    graph_revision_id text NOT NULL,
    graph_payload_format text NOT NULL,
    graph_sha256 varchar(64) NOT NULL,
    payload_format text NOT NULL,
    canonical_payload bytea NOT NULL,
    canonical_sha256 varchar(64) NOT NULL,
    effective_from timestamptz NOT NULL,
    effective_until timestamptz,
    recorded_at timestamptz NOT NULL,
    source_reference text NOT NULL,
    actor_binding_id varchar(80) NOT NULL,
    actor_session_id varchar(80) NOT NULL,
    actor_user_id varchar(80) NOT NULL,
    CONSTRAINT pk_station_configuration_revision PRIMARY KEY(company_party_id,facility_id,revision_id),
    CONSTRAINT uq_station_configuration_selected_epoch UNIQUE(company_party_id,facility_id,selected_epoch),
    CONSTRAINT uq_station_configuration_revision_epoch UNIQUE(company_party_id,facility_id,revision_id,selected_epoch),
    CONSTRAINT fk_station_configuration_graph FOREIGN KEY(facility_id,graph_revision_id)
        REFERENCES public.hidra_topology_station_graph_revision(facility_id,revision_id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT ck_station_configuration_identity CHECK (
        company_party_id=btrim(company_party_id) AND length(company_party_id)>0 AND
        facility_id=btrim(facility_id) AND length(facility_id)>0 AND
        revision_id=btrim(revision_id) AND length(revision_id)>0 AND
        request_id=btrim(request_id) AND length(request_id)>0 AND
        actor_binding_id=btrim(actor_binding_id) AND length(actor_binding_id)>0 AND
        actor_session_id=btrim(actor_session_id) AND length(actor_session_id)>0 AND
        actor_user_id=btrim(actor_user_id) AND length(actor_user_id)>0),
    CONSTRAINT ck_station_configuration_epoch CHECK (
        expected_epoch>=0 AND expected_epoch<9223372036854775807::bigint AND
        selected_epoch=expected_epoch+1 AND graph_head_epoch>0),
    CONSTRAINT ck_station_configuration_period CHECK (
        effective_until IS NULL OR effective_until>effective_from),
    CONSTRAINT ck_station_configuration_format CHECK (
        graph_payload_format='HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1' AND
        payload_format='HIDRA_TOPOLOGY_STATION_INSTALLED_CONFIGURATION_V1' AND
        octet_length(canonical_payload) BETWEEN 1 AND 1048576),
    CONSTRAINT ck_station_configuration_integrity CHECK (
        graph_sha256 ~ '^[0-9a-f]{64}$' AND
        canonical_sha256 ~ '^[0-9a-f]{64}$' AND
        canonical_sha256=encode(sha256(canonical_payload),'hex')),
    CONSTRAINT ck_station_configuration_source CHECK (
        length(btrim(source_reference))>0 AND source_reference=btrim(source_reference))
);

CREATE TABLE public.hidra_topology_station_configuration_member (
    company_party_id varchar(80) NOT NULL,
    facility_id varchar(80) NOT NULL,
    revision_id varchar(80) NOT NULL,
    position_id varchar(80) NOT NULL,
    disposition varchar(16) NOT NULL,
    installed_unit_id varchar(80),
    installation_id varchar(80),
    installation_revision bigint,
    installation_from timestamptz,
    installation_until timestamptz,
    installation_evidence_reference text,
    CONSTRAINT pk_station_configuration_member PRIMARY KEY(company_party_id,facility_id,revision_id,position_id),
    CONSTRAINT fk_station_configuration_member_revision FOREIGN KEY(company_party_id,facility_id,revision_id)
        REFERENCES public.hidra_topology_station_configuration_revision(company_party_id,facility_id,revision_id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_station_configuration_member_position FOREIGN KEY(position_id)
        REFERENCES public.hidra_topology_equipment(id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_station_configuration_member_unit_company FOREIGN KEY(installed_unit_id,company_party_id)
        REFERENCES public.hidra_topology_installed_unit(id,company_party_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_station_configuration_member_event FOREIGN KEY(installation_id,installation_revision)
        REFERENCES public.hidra_topology_installed_unit_installation_event(installation_id,revision)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT ck_station_configuration_member_disposition CHECK (disposition IN ('CONNECTED','ISOLATED','UNOCCUPIED')),
    CONSTRAINT ck_station_configuration_member_binding CHECK (
        (disposition='UNOCCUPIED' AND installed_unit_id IS NULL AND
         installation_id IS NULL AND installation_revision IS NULL AND
         installation_from IS NULL AND installation_until IS NULL AND
         installation_evidence_reference IS NULL)
      OR
        (disposition IN ('CONNECTED','ISOLATED') AND installed_unit_id IS NOT NULL AND
         installation_id IS NOT NULL AND installation_revision>0 AND
         installation_from IS NOT NULL AND
         (installation_until IS NULL OR installation_until>installation_from) AND
         nullif(btrim(installation_evidence_reference),'') IS NOT NULL)),
    CONSTRAINT ck_station_configuration_member_position CHECK (
        position_id=btrim(position_id) AND length(position_id)>0)
);
CREATE UNIQUE INDEX uq_station_configuration_member_physical_unit
    ON public.hidra_topology_station_configuration_member(company_party_id,facility_id,revision_id,installed_unit_id)
    WHERE installed_unit_id IS NOT NULL;
CREATE UNIQUE INDEX uq_station_configuration_member_installation
    ON public.hidra_topology_station_configuration_member(company_party_id,facility_id,revision_id,installation_id)
    WHERE installation_id IS NOT NULL;

CREATE TABLE public.hidra_topology_station_configuration_head (
    company_party_id varchar(80) NOT NULL,
    facility_id varchar(80) NOT NULL,
    revision_id varchar(80) NOT NULL,
    head_epoch bigint NOT NULL,
    CONSTRAINT pk_station_configuration_head PRIMARY KEY(company_party_id,facility_id),
    CONSTRAINT ck_station_configuration_head_epoch CHECK (head_epoch>0),
    CONSTRAINT fk_station_configuration_head_selection
        FOREIGN KEY(company_party_id,facility_id,revision_id,head_epoch)
        REFERENCES public.hidra_topology_station_configuration_revision(company_party_id,facility_id,revision_id,selected_epoch)
        ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE public.hidra_topology_station_configuration_request (
    company_party_id varchar(80) NOT NULL,
    facility_id varchar(80) NOT NULL,
    request_id varchar(80) NOT NULL,
    command_sha256 varchar(64) NOT NULL,
    revision_id varchar(80) NOT NULL,
    selected_epoch bigint NOT NULL,
    actor_binding_id varchar(80) NOT NULL,
    recorded_at timestamptz NOT NULL,
    CONSTRAINT pk_station_configuration_request PRIMARY KEY(company_party_id,facility_id,request_id),
    CONSTRAINT fk_station_configuration_request_result FOREIGN KEY(company_party_id,facility_id,revision_id,selected_epoch)
        REFERENCES public.hidra_topology_station_configuration_revision(company_party_id,facility_id,revision_id,selected_epoch)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT ck_station_configuration_request_digest CHECK (command_sha256 ~ '^[0-9a-f]{64}$'),
    CONSTRAINT ck_station_configuration_request_ids CHECK (
        length(btrim(request_id))>0 AND request_id=btrim(request_id) AND
        length(btrim(actor_binding_id))>0 AND actor_binding_id=btrim(actor_binding_id))
);

CREATE FUNCTION public.hidra_station_configuration_immutable() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
    RAISE EXCEPTION 'Station configuration immutable evidence cannot be changed' USING ERRCODE='23514';
END;
$guard$;
CREATE TRIGGER trg_station_configuration_revision_immutable
BEFORE UPDATE OR DELETE ON public.hidra_topology_station_configuration_revision
FOR EACH ROW EXECUTE FUNCTION public.hidra_station_configuration_immutable();
CREATE TRIGGER trg_station_configuration_revision_no_truncate
BEFORE TRUNCATE ON public.hidra_topology_station_configuration_revision
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_station_configuration_immutable();
CREATE TRIGGER trg_station_configuration_member_immutable
BEFORE UPDATE OR DELETE ON public.hidra_topology_station_configuration_member
FOR EACH ROW EXECUTE FUNCTION public.hidra_station_configuration_immutable();
CREATE TRIGGER trg_station_configuration_member_no_truncate
BEFORE TRUNCATE ON public.hidra_topology_station_configuration_member
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_station_configuration_immutable();
CREATE TRIGGER trg_station_configuration_request_immutable
BEFORE UPDATE OR DELETE ON public.hidra_topology_station_configuration_request
FOR EACH ROW EXECUTE FUNCTION public.hidra_station_configuration_immutable();
CREATE TRIGGER trg_station_configuration_request_no_truncate
BEFORE TRUNCATE ON public.hidra_topology_station_configuration_request
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_station_configuration_immutable();

CREATE FUNCTION public.hidra_station_configuration_head_cas() RETURNS trigger
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $guard$
BEGIN
    IF TG_OP='INSERT' THEN
        IF NEW.head_epoch<>1 THEN
            RAISE EXCEPTION 'Station configuration first selection must be epoch one' USING ERRCODE='23514';
        END IF;
    ELSIF TG_OP='UPDATE' THEN
        IF NEW.company_party_id<>OLD.company_party_id OR NEW.facility_id<>OLD.facility_id OR
           OLD.head_epoch=9223372036854775807::bigint OR NEW.head_epoch<>OLD.head_epoch+1 OR
           NEW.revision_id=OLD.revision_id THEN
            RAISE EXCEPTION 'Station configuration selection requires a new revision and one epoch step' USING ERRCODE='23514';
        END IF;
    ELSE
        RAISE EXCEPTION 'Station configuration selection cannot be removed' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$guard$;
CREATE TRIGGER trg_station_configuration_head_cas
BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_topology_station_configuration_head
FOR EACH ROW EXECUTE FUNCTION public.hidra_station_configuration_head_cas();
CREATE TRIGGER trg_station_configuration_head_no_truncate
BEFORE TRUNCATE ON public.hidra_topology_station_configuration_head
FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_station_configuration_immutable();

REVOKE ALL ON public.hidra_topology_station_configuration_revision,
    public.hidra_topology_station_configuration_member,
    public.hidra_topology_station_configuration_head,
    public.hidra_topology_station_configuration_request FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_station_configuration_immutable(),
    public.hidra_station_configuration_head_cas() FROM PUBLIC;
-- No role grants or producer authorization are provisioned by Package A.
