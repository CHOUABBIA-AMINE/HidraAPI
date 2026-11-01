-- HAPI-602 bounded draft-link persistence foundation (fresh schema, synthetic only).
-- Drafts are NEVER approved, Workflow-qualified, manufacturer-certified or simulator-ready.
-- This migration does not publish a route, grant a role, or provide native authority.
-- Existing source/manufacturer/installed-unit migrations and their histories remain intact.

CREATE TABLE public.hidra_topology_installed_characteristic_revision (
    company_party_id varchar(80) NOT NULL,
    installed_unit_id varchar(80) NOT NULL,
    link_id varchar(80) NOT NULL,
    link_revision bigint NOT NULL,
    source_id varchar(80) NOT NULL,
    source_revision_id varchar(80) NOT NULL,
    source_sha256 char(64) NOT NULL,
    source_kind varchar(32) NOT NULL,
    effective_from timestamptz NOT NULL,
    effective_until timestamptz,
    recorded_at timestamptz NOT NULL,
    evidence_reference text NOT NULL,
    installation_id varchar(80),
    link_state varchar(16) NOT NULL DEFAULT 'DRAFT',
    operation_binding_id varchar(80) NOT NULL,
    actor_session_id varchar(80) NOT NULL,
    actor_user_id varchar(80) NOT NULL,
    backend_pid integer NOT NULL,
    transaction_id bigint NOT NULL,
    database_role name NOT NULL,
    inserted_at timestamptz NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT pk_topology_characteristic_revision PRIMARY KEY
        (company_party_id,installed_unit_id,link_id,link_revision),
    CONSTRAINT fk_topology_characteristic_unit FOREIGN KEY (installed_unit_id,company_party_id)
        REFERENCES public.hidra_topology_installed_unit(id,company_party_id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_topology_characteristic_source FOREIGN KEY
        (company_party_id,source_id,source_revision_id)
        REFERENCES public.hidra_simulation_manufacturer_characteristic_revision
            (company_party_id,source_id,revision_id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT ck_topology_characteristic_identity CHECK (
        company_party_id=btrim(company_party_id) AND length(company_party_id)>0 AND
        installed_unit_id=btrim(installed_unit_id) AND length(installed_unit_id)>0 AND
        link_id=btrim(link_id) AND length(link_id)>0 AND
        source_id=btrim(source_id) AND length(source_id)>0 AND
        source_revision_id=btrim(source_revision_id) AND length(source_revision_id)>0 AND
        operation_binding_id=btrim(operation_binding_id) AND length(operation_binding_id)>0 AND
        actor_session_id=btrim(actor_session_id) AND length(actor_session_id)>0 AND
        actor_user_id=btrim(actor_user_id) AND length(actor_user_id)>0 AND
        (installation_id IS NULL OR (installation_id=btrim(installation_id) AND length(installation_id)>0))),
    CONSTRAINT ck_topology_characteristic_draft CHECK (
        link_revision>0 AND link_state='DRAFT'
        AND source_sha256 ~ '^[0-9a-f]{64}$'
        AND source_kind IN ('COMPRESSOR_MAP','VALVE_MAP','PARAMETER_TABLE','OPERATING_LIMITS')
        AND effective_until IS DISTINCT FROM effective_from
        AND (effective_until IS NULL OR effective_until>effective_from)
        AND evidence_reference=btrim(evidence_reference)
        AND length(evidence_reference)>0
        AND octet_length(evidence_reference)<=65536
        AND backend_pid>0 AND transaction_id>0),
    CONSTRAINT ex_topology_characteristic_selection EXCLUDE USING gist (
        company_party_id WITH =,
        installed_unit_id WITH =,
        source_kind WITH =,
        tstzrange(effective_from,effective_until,'[)') WITH &&
    )
);
CREATE INDEX ix_topology_characteristic_history
    ON public.hidra_topology_installed_characteristic_revision
    (company_party_id,installed_unit_id,link_id,link_revision DESC);

-- One head per physical-unit/link identity. Original authenticated owner must
-- perform expected-epoch CAS in its approved protected transaction.
CREATE TABLE public.hidra_topology_installed_characteristic_head (
    company_party_id varchar(80) NOT NULL,
    installed_unit_id varchar(80) NOT NULL,
    link_id varchar(80) NOT NULL,
    link_revision bigint NOT NULL,
    head_epoch bigint NOT NULL,
    CONSTRAINT pk_topology_characteristic_head PRIMARY KEY
        (company_party_id,installed_unit_id,link_id),
    CONSTRAINT ck_topology_characteristic_head CHECK (
        head_epoch>0 AND link_revision=head_epoch),
    CONSTRAINT fk_topology_characteristic_head FOREIGN KEY
        (company_party_id,installed_unit_id,link_id,link_revision)
        REFERENCES public.hidra_topology_installed_characteristic_revision
            (company_party_id,installed_unit_id,link_id,link_revision)
        ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Append-only idempotency receipts, not proof of permission by themselves.
CREATE TABLE public.hidra_topology_installed_characteristic_request (
    company_party_id varchar(80) NOT NULL,
    installed_unit_id varchar(80) NOT NULL,
    link_id varchar(80) NOT NULL,
    request_id varchar(80) NOT NULL,
    command_sha256 char(64) NOT NULL,
    link_revision bigint NOT NULL,
    actor_binding_id varchar(80) NOT NULL,
    actor_session_id varchar(80) NOT NULL,
    actor_user_id varchar(80) NOT NULL,
    backend_pid integer NOT NULL,
    transaction_id bigint NOT NULL,
    database_role name NOT NULL,
    recorded_at timestamptz NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT pk_topology_characteristic_request PRIMARY KEY
        (company_party_id,installed_unit_id,link_id,request_id),
    CONSTRAINT fk_topology_characteristic_request FOREIGN KEY
        (company_party_id,installed_unit_id,link_id,link_revision)
        REFERENCES public.hidra_topology_installed_characteristic_revision
            (company_party_id,installed_unit_id,link_id,link_revision)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT ck_topology_characteristic_request CHECK (
        request_id=btrim(request_id) AND length(request_id)>0
        AND command_sha256 ~ '^[0-9a-f]{64}$'
        AND actor_binding_id=btrim(actor_binding_id) AND length(actor_binding_id)>0
        AND actor_session_id=btrim(actor_session_id) AND length(actor_session_id)>0
        AND actor_user_id=btrim(actor_user_id) AND length(actor_user_id)>0
        AND backend_pid>0 AND transaction_id>0)
);

CREATE FUNCTION public.hidra_topology_characteristic_immutable()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    RAISE EXCEPTION 'Installed characteristic draft evidence is immutable' USING ERRCODE='23514';
END;
$body$;

CREATE TRIGGER trg_topology_characteristic_revision_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_topology_installed_characteristic_revision
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();
CREATE TRIGGER trg_topology_characteristic_revision_truncate
    BEFORE TRUNCATE ON public.hidra_topology_installed_characteristic_revision
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();
CREATE TRIGGER trg_topology_characteristic_request_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_topology_installed_characteristic_request
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();
CREATE TRIGGER trg_topology_characteristic_request_truncate
    BEFORE TRUNCATE ON public.hidra_topology_installed_characteristic_request
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();

CREATE FUNCTION public.hidra_topology_characteristic_head_cas()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF TG_OP='INSERT' THEN
        IF NEW.head_epoch<>1 OR NEW.link_revision<>1 THEN
            RAISE EXCEPTION 'First characteristic draft must be revision one' USING ERRCODE='23514';
        END IF;
    ELSIF TG_OP='UPDATE' THEN
        IF NEW.company_party_id IS DISTINCT FROM OLD.company_party_id
           OR NEW.installed_unit_id IS DISTINCT FROM OLD.installed_unit_id
           OR NEW.link_id IS DISTINCT FROM OLD.link_id
           OR OLD.head_epoch=9223372036854775807::bigint
           OR NEW.head_epoch<>OLD.head_epoch+1
           OR NEW.link_revision<>OLD.link_revision+1 THEN
            RAISE EXCEPTION 'Characteristic draft head requires a new consecutive revision' USING ERRCODE='23514';
        END IF;
    ELSE
        RAISE EXCEPTION 'Characteristic draft head cannot be removed' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$body$;
CREATE TRIGGER trg_topology_characteristic_head_cas
    BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_topology_installed_characteristic_head
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_head_cas();
CREATE TRIGGER trg_topology_characteristic_head_truncate
    BEFORE TRUNCATE ON public.hidra_topology_installed_characteristic_head
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();

-- Publisher must already hold the original protected source/Identity/Party
-- authorization. These database checks are additional integrity fences only.
CREATE FUNCTION public.hidra_topology_characteristic_insert_guard()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF NEW.backend_pid IS DISTINCT FROM pg_catalog.pg_backend_pid()
       OR NEW.transaction_id IS DISTINCT FROM pg_catalog.txid_current()
       OR NEW.database_role IS DISTINCT FROM session_user
       OR pg_catalog.current_setting('transaction_isolation')<>'read committed'
       OR pg_catalog.current_setting('transaction_read_only')<>'off'
       OR pg_catalog.current_setting('session_replication_role')<>'origin' THEN
        RAISE EXCEPTION 'Characteristic draft original transaction invalid' USING ERRCODE='42501';
    END IF;
    IF TG_TABLE_NAME='hidra_topology_installed_characteristic_revision' THEN
        IF NOT EXISTS (
            SELECT 1 FROM public.hidra_simulation_manufacturer_characteristic_revision r
            WHERE r.company_party_id=NEW.company_party_id
              AND r.source_id=NEW.source_id
              AND r.revision_id=NEW.source_revision_id
              AND r.payload_sha256=NEW.source_sha256
        ) THEN
            RAISE EXCEPTION 'Exact manufacturer source digest not found' USING ERRCODE='23514';
        END IF;
    END IF;
    RETURN NEW;
END;
$body$;
CREATE TRIGGER trg_topology_characteristic_revision_origin
    BEFORE INSERT ON public.hidra_topology_installed_characteristic_revision
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_insert_guard();
CREATE TRIGGER trg_topology_characteristic_request_origin
    BEFORE INSERT ON public.hidra_topology_installed_characteristic_request
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_insert_guard();

-- Fixed source relation lock: not an authorization token. Only an independently
-- configured restricted publisher can be granted EXECUTE for protected operations.
CREATE FUNCTION public.hidra_topology_characteristic_lock_sources()
RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF pg_catalog.current_setting('transaction_isolation')<>'read committed'
       OR pg_catalog.current_setting('transaction_read_only')<>'off'
       OR pg_catalog.current_setting('session_replication_role')<>'origin' THEN
        RAISE EXCEPTION 'Characteristic draft source lock unavailable' USING ERRCODE='42501';
    END IF;
    LOCK TABLE
        public.hidra_simulation_manufacturer_characteristic_revision,
        public.hidra_topology_installed_unit,
        public.hidra_topology_installed_characteristic_revision,
        public.hidra_topology_installed_characteristic_head,
        public.hidra_topology_installed_characteristic_request
    IN SHARE ROW EXCLUSIVE MODE NOWAIT;
END;
$body$;

REVOKE ALL ON FUNCTION public.hidra_topology_characteristic_immutable() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_characteristic_head_cas() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_characteristic_insert_guard() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_characteristic_lock_sources() FROM PUBLIC;
REVOKE ALL ON public.hidra_topology_installed_characteristic_revision FROM PUBLIC;
REVOKE ALL ON public.hidra_topology_installed_characteristic_head FROM PUBLIC;
REVOKE ALL ON public.hidra_topology_installed_characteristic_request FROM PUBLIC;
-- No role creation, privileges for ordinary users or runtime writes in this stage.
