-- HAPI-601 Package B. Immutable canonical manufacturer source and independently qualified Workflow evidence.
-- Fresh-schema only. Identity, Party, and Workflow remain owners of their corresponding source tables.
CREATE TABLE public.hidra_simulation_manufacturer_characteristic_revision (
    company_party_id varchar(80) NOT NULL,
    source_id varchar(80) NOT NULL,
    revision_id varchar(80) NOT NULL,
    manufacturer_party_id varchar(80) NOT NULL,
    manufacturer_model_reference varchar(240) NOT NULL,
    payload_format varchar(80) NOT NULL DEFAULT 'HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1',
    canonical_payload bytea NOT NULL,
    payload_sha256 char(64) NOT NULL,
    workflow_target_digest char(64) NOT NULL,
    document_sha256 char(64) NOT NULL,
    source_recorded_at timestamptz NOT NULL,
    effective_from timestamptz NOT NULL,
    effective_until timestamptz,
    source_origin varchar(32) NOT NULL CHECK (source_origin IN ('SUPPLIER_DECLARED','SYNTHETIC')),
    creator_user_id varchar(80) NOT NULL,
    operation_binding_id varchar(80) NOT NULL,
    session_id varchar(80) NOT NULL,
    backend_pid integer NOT NULL CHECK (backend_pid>0),
    transaction_id bigint NOT NULL CHECK (transaction_id>0),
    database_role name NOT NULL,
    inserted_at timestamptz NOT NULL,
    CONSTRAINT pk_sim_manufacturer_characteristic_revision PRIMARY KEY(company_party_id,source_id,revision_id),
    CONSTRAINT uq_sim_manufacturer_characteristic_target UNIQUE(workflow_target_digest),
    CONSTRAINT ck_sim_manufacturer_characteristic_integrity CHECK (
       payload_format='HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1'
       AND octet_length(canonical_payload) BETWEEN 1 AND 1048576
       AND payload_sha256 ~ '^[0-9a-f]{64}$'
       AND workflow_target_digest ~ '^[0-9a-f]{64}$'
       AND document_sha256 ~ '^[0-9a-f]{64}$'
       AND char_length(company_party_id)>0 AND char_length(source_id)>0 AND char_length(revision_id)>0
       AND effective_from IS NOT NULL
       AND (effective_until IS NULL OR effective_until>effective_from)
       AND source_recorded_at<=inserted_at
    )
);
CREATE INDEX idx_sim_manufacturer_characteristic_history
 ON public.hidra_simulation_manufacturer_characteristic_revision(company_party_id,source_id,source_recorded_at,revision_id);
CREATE TABLE public.hidra_simulation_manufacturer_characteristic_qualification (
    qualification_id varchar(80) PRIMARY KEY,
    company_party_id varchar(80) NOT NULL,
    source_id varchar(80) NOT NULL,
    revision_id varchar(80) NOT NULL,
    source_sha256 char(64) NOT NULL,
    workflow_target_digest char(64) NOT NULL,
    workflow_instance_id varchar(80) NOT NULL,
    workflow_task_id varchar(80) NOT NULL,
    workflow_action_id varchar(80) NOT NULL,
    approver_id varchar(80) NOT NULL,
    approver_display_name varchar(160) NOT NULL,
    approved_at timestamptz NOT NULL,
    qualified_at timestamptz NOT NULL,
    qualifying_user_id varchar(80) NOT NULL,
    operation_binding_id varchar(80) NOT NULL,
    backend_pid integer NOT NULL CHECK (backend_pid>0),
    transaction_id bigint NOT NULL CHECK (transaction_id>0),
    database_role name NOT NULL,
    CONSTRAINT fk_sim_manufacturer_qualification_revision FOREIGN KEY(company_party_id,source_id,revision_id)
        REFERENCES public.hidra_simulation_manufacturer_characteristic_revision(company_party_id,source_id,revision_id),
    CONSTRAINT uq_sim_manufacturer_one_qualification UNIQUE(company_party_id,source_id,revision_id),
    CONSTRAINT uq_sim_manufacturer_one_workflow_action UNIQUE(workflow_action_id),
    CONSTRAINT ck_sim_manufacturer_qualification CHECK (
        source_sha256 ~ '^[0-9a-f]{64}$'
        AND workflow_target_digest ~ '^[0-9a-f]{64}$'
        AND qualified_at>=approved_at
        AND char_length(approver_id)>0 AND char_length(approver_display_name)>0
    )
);

CREATE FUNCTION public.hidra_simulation_manufacturer_immutable()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN RAISE EXCEPTION 'Manufacturer characteristic evidence is immutable' USING ERRCODE='42501'; END;
$body$;
CREATE TRIGGER trg_sim_manufacturer_revision_immutable
 BEFORE UPDATE OR DELETE ON public.hidra_simulation_manufacturer_characteristic_revision
 FOR EACH ROW EXECUTE FUNCTION public.hidra_simulation_manufacturer_immutable();
CREATE TRIGGER trg_sim_manufacturer_revision_truncate
 BEFORE TRUNCATE ON public.hidra_simulation_manufacturer_characteristic_revision
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_simulation_manufacturer_immutable();
CREATE TRIGGER trg_sim_manufacturer_qualification_immutable
 BEFORE UPDATE OR DELETE ON public.hidra_simulation_manufacturer_characteristic_qualification
 FOR EACH ROW EXECUTE FUNCTION public.hidra_simulation_manufacturer_immutable();
CREATE TRIGGER trg_sim_manufacturer_qualification_truncate
 BEFORE TRUNCATE ON public.hidra_simulation_manufacturer_characteristic_qualification
 FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_simulation_manufacturer_immutable();

-- PostgreSQL 14+ built-in SHA-256 avoids installing a discretionary extension.
CREATE FUNCTION public.hidra_simulation_manufacturer_insert_origin()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
 IF NEW.backend_pid IS DISTINCT FROM pg_catalog.pg_backend_pid()
    OR NEW.transaction_id IS DISTINCT FROM pg_catalog.txid_current()
    OR NEW.database_role IS DISTINCT FROM session_user
    OR pg_catalog.current_setting('transaction_isolation')<>'read committed'
    OR pg_catalog.current_setting('transaction_read_only')<>'off'
    OR pg_catalog.current_setting('session_replication_role')<>'origin'
    THEN RAISE EXCEPTION 'Manufacturer original transaction witness invalid' USING ERRCODE='42501'; END IF;
 IF TG_TABLE_NAME='hidra_simulation_manufacturer_characteristic_revision' THEN
    IF pg_catalog.encode(pg_catalog.sha256(NEW.canonical_payload),'hex')<>NEW.payload_sha256 THEN
       RAISE EXCEPTION 'Manufacturer canonical digest mismatch' USING ERRCODE='23514'; END IF;
 ELSE
    IF NOT EXISTS(
      SELECT 1 FROM public.hidra_simulation_manufacturer_characteristic_revision r
      WHERE r.company_party_id=NEW.company_party_id AND r.source_id=NEW.source_id
      AND r.revision_id=NEW.revision_id AND r.payload_sha256=NEW.source_sha256
      AND r.workflow_target_digest=NEW.workflow_target_digest
      AND NEW.qualified_at>=r.inserted_at
    ) THEN RAISE EXCEPTION 'Manufacturer qualification/source mismatch' USING ERRCODE='23514'; END IF;
 END IF;
 RETURN NEW;
END;
$body$;
CREATE TRIGGER trg_sim_manufacturer_revision_origin BEFORE INSERT
 ON public.hidra_simulation_manufacturer_characteristic_revision
 FOR EACH ROW EXECUTE FUNCTION public.hidra_simulation_manufacturer_insert_origin();
CREATE TRIGGER trg_sim_manufacturer_qualification_origin BEFORE INSERT
 ON public.hidra_simulation_manufacturer_characteristic_qualification
 FOR EACH ROW EXECUTE FUNCTION public.hidra_simulation_manufacturer_insert_origin();

CREATE FUNCTION public.hidra_simulation_manufacturer_lock_sources()
RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
 IF pg_catalog.current_setting('transaction_isolation') <> 'read committed'
    OR pg_catalog.current_setting('transaction_read_only') <> 'off'
 THEN RAISE EXCEPTION 'Protected manufacturer source lock unavailable' USING ERRCODE='42501'; END IF;
 LOCK TABLE public.hidra_simulation_manufacturer_characteristic_revision,
            public.hidra_simulation_manufacturer_characteristic_qualification
 IN SHARE ROW EXCLUSIVE MODE NOWAIT;
END;
$body$;
REVOKE ALL ON FUNCTION public.hidra_simulation_manufacturer_immutable() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_simulation_manufacturer_insert_origin() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_simulation_manufacturer_lock_sources() FROM PUBLIC;
REVOKE ALL ON public.hidra_simulation_manufacturer_characteristic_revision FROM PUBLIC;
REVOKE ALL ON public.hidra_simulation_manufacturer_characteristic_qualification FROM PUBLIC;
-- Privileged DBA grants the configured restricted publisher only to exact owned relations/functions.
