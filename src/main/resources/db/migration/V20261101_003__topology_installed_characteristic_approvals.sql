-- HAPI-602 S3: immutable, independently authorized installed-link approval decisions.
-- Separate from DRAFT; no public or application grants, no default Identity authority.
CREATE TABLE public.hidra_topology_installed_characteristic_approval (
    company_party_id varchar(80) NOT NULL,
    installed_unit_id varchar(80) NOT NULL,
    link_id varchar(80) NOT NULL,
    link_revision bigint NOT NULL,
    target_sha256 char(64) NOT NULL,
    draft_actor_user_id varchar(80) NOT NULL,
    approving_actor_user_id varchar(80) NOT NULL,
    workflow_definition_id varchar(80) NOT NULL,
    workflow_definition_version integer NOT NULL,
    workflow_target_type_id varchar(80) NOT NULL,
    workflow_purpose_id varchar(80) NOT NULL,
    workflow_instance_id varchar(80) NOT NULL,
    workflow_task_id varchar(80) NOT NULL,
    workflow_action_id varchar(80) NOT NULL,
    workflow_acted_at timestamptz NOT NULL,
    approved_at timestamptz NOT NULL,
    operation_binding_id varchar(80) NOT NULL,
    actor_session_id varchar(80) NOT NULL,
    backend_pid integer NOT NULL,
    transaction_id bigint NOT NULL,
    database_role name NOT NULL,
    inserted_at timestamptz NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT pk_topology_characteristic_approval
        PRIMARY KEY(company_party_id,installed_unit_id,link_id,link_revision),
    CONSTRAINT fk_topology_characteristic_approval_draft FOREIGN KEY
        (company_party_id,installed_unit_id,link_id,link_revision)
        REFERENCES public.hidra_topology_installed_characteristic_revision
        (company_party_id,installed_unit_id,link_id,link_revision)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT uq_topology_characteristic_approval_action UNIQUE(workflow_instance_id,workflow_action_id),
    CONSTRAINT ck_topology_characteristic_approval CHECK (
        link_revision>0 AND workflow_definition_version>0
        AND target_sha256 ~ '^[0-9a-f]{64}$'
        AND draft_actor_user_id<>approving_actor_user_id
        AND workflow_acted_at<=approved_at
        AND backend_pid>0 AND transaction_id>0
        AND length(draft_actor_user_id)>0 AND draft_actor_user_id=btrim(draft_actor_user_id)
        AND length(approving_actor_user_id)>0 AND approving_actor_user_id=btrim(approving_actor_user_id)
        AND length(operation_binding_id)>0 AND operation_binding_id=btrim(operation_binding_id)
        AND length(actor_session_id)>0 AND actor_session_id=btrim(actor_session_id)
        AND length(workflow_instance_id)>0 AND workflow_instance_id=btrim(workflow_instance_id)
        AND length(workflow_task_id)>0 AND workflow_task_id=btrim(workflow_task_id)
        AND length(workflow_action_id)>0 AND workflow_action_id=btrim(workflow_action_id))
);
CREATE INDEX ix_topology_characteristic_approval_history
ON public.hidra_topology_installed_characteristic_approval
(company_party_id,installed_unit_id,link_id,link_revision DESC);

CREATE TABLE public.hidra_topology_installed_characteristic_approval_request (
    company_party_id varchar(80) NOT NULL,
    installed_unit_id varchar(80) NOT NULL,
    link_id varchar(80) NOT NULL,
    request_id varchar(80) NOT NULL,
    link_revision bigint NOT NULL,
    command_sha256 char(64) NOT NULL,
    approving_actor_user_id varchar(80) NOT NULL,
    operation_binding_id varchar(80) NOT NULL,
    actor_session_id varchar(80) NOT NULL,
    backend_pid integer NOT NULL,
    transaction_id bigint NOT NULL,
    database_role name NOT NULL,
    recorded_at timestamptz NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT pk_topology_characteristic_approval_request
        PRIMARY KEY(company_party_id,installed_unit_id,link_id,request_id),
    CONSTRAINT fk_topology_characteristic_approval_receipt FOREIGN KEY
        (company_party_id,installed_unit_id,link_id,link_revision)
        REFERENCES public.hidra_topology_installed_characteristic_approval
        (company_party_id,installed_unit_id,link_id,link_revision)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT ck_topology_characteristic_approval_receipt CHECK(
        link_revision>0 AND command_sha256 ~ '^[0-9a-f]{64}$'
        AND request_id=btrim(request_id) AND length(request_id)>0
        AND approving_actor_user_id=btrim(approving_actor_user_id) AND length(approving_actor_user_id)>0
        AND operation_binding_id=btrim(operation_binding_id) AND length(operation_binding_id)>0
        AND actor_session_id=btrim(actor_session_id) AND length(actor_session_id)>0
        AND backend_pid>0 AND transaction_id>0)
);

-- Existing immutable trigger function preserves the same append-only policy.
CREATE TRIGGER trg_topology_characteristic_approval_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_topology_installed_characteristic_approval
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();
CREATE TRIGGER trg_topology_characteristic_approval_truncate
    BEFORE TRUNCATE ON public.hidra_topology_installed_characteristic_approval
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();
CREATE TRIGGER trg_topology_characteristic_approval_request_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_topology_installed_characteristic_approval_request
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();
CREATE TRIGGER trg_topology_characteristic_approval_request_truncate
    BEFORE TRUNCATE ON public.hidra_topology_installed_characteristic_approval_request
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_characteristic_immutable();

-- Physical DB provenance is a second integrity check, not a grant or substitute
-- for Workflow/Identity native authority. A caller cannot pre-claim an actor.
CREATE FUNCTION public.hidra_topology_characteristic_approval_insert_guard()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF NEW.backend_pid IS DISTINCT FROM pg_catalog.pg_backend_pid()
       OR NEW.transaction_id IS DISTINCT FROM pg_catalog.txid_current()
       OR NEW.database_role IS DISTINCT FROM session_user
       OR pg_catalog.current_setting('transaction_isolation')<>'read committed'
       OR pg_catalog.current_setting('transaction_read_only')<>'off'
       OR pg_catalog.current_setting('session_replication_role')<>'origin' THEN
        RAISE EXCEPTION 'Original characteristic approval transaction unavailable' USING ERRCODE='42501';
    END IF;
    IF TG_TABLE_NAME='hidra_topology_installed_characteristic_approval' THEN
        IF NOT EXISTS (
            SELECT 1 FROM public.hidra_topology_installed_characteristic_revision r
            JOIN public.hidra_topology_installed_characteristic_head h
              ON h.company_party_id=r.company_party_id AND h.installed_unit_id=r.installed_unit_id
              AND h.link_id=r.link_id AND h.link_revision=r.link_revision
            WHERE r.company_party_id=NEW.company_party_id
              AND r.installed_unit_id=NEW.installed_unit_id
              AND r.link_id=NEW.link_id AND r.link_revision=NEW.link_revision
              AND h.head_epoch=NEW.link_revision AND r.link_state='DRAFT'
              AND r.actor_user_id=NEW.draft_actor_user_id
              AND NEW.workflow_acted_at>=r.recorded_at
        ) THEN
            RAISE EXCEPTION 'Approval source is not the current immutable DRAFT' USING ERRCODE='23514';
        END IF;
    END IF;
    RETURN NEW;
END;
$body$;
CREATE TRIGGER trg_topology_characteristic_approval_origin
    BEFORE INSERT ON public.hidra_topology_installed_characteristic_approval
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_approval_insert_guard();
CREATE TRIGGER trg_topology_characteristic_approval_receipt_origin
    BEFORE INSERT ON public.hidra_topology_installed_characteristic_approval_request
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_characteristic_approval_insert_guard();

-- Separate reviewed lock family, no alteration to legacy DRAFT lock function.
-- Workflow owns nine additional physical tables and locks them independently
-- during evidence resolution; this function covers Topology/source/installation.
CREATE FUNCTION public.hidra_topology_characteristic_lock_approval_sources()
RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF pg_catalog.current_setting('transaction_isolation')<>'read committed'
       OR pg_catalog.current_setting('transaction_read_only')<>'off'
       OR pg_catalog.current_setting('session_replication_role')<>'origin' THEN
       RAISE EXCEPTION 'Characteristic approval source lock unavailable' USING ERRCODE='42501';
    END IF;
    LOCK TABLE
        public.hidra_simulation_manufacturer_characteristic_revision,
        public.hidra_topology_installed_unit,
        public.hidra_topology_installed_unit_installation,
        public.hidra_topology_installed_characteristic_revision,
        public.hidra_topology_installed_characteristic_head,
        public.hidra_topology_installed_characteristic_request,
        public.hidra_topology_installed_characteristic_approval,
        public.hidra_topology_installed_characteristic_approval_request
    IN SHARE ROW EXCLUSIVE MODE NOWAIT;
END;
$body$;

REVOKE ALL ON public.hidra_topology_installed_characteristic_approval FROM PUBLIC;
REVOKE ALL ON public.hidra_topology_installed_characteristic_approval_request FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_characteristic_approval_insert_guard() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_characteristic_lock_approval_sources() FROM PUBLIC;
-- Publisher grants are externally configured for exact operational qualification,
-- never minted by a migration or by a synthetic Workflow target.
