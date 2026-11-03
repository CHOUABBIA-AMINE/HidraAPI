-- HAPI-605: versioned draft manifests only. No authoritative freeze or source-owner grants.
CREATE TABLE public.hidra_topology_engineering_baseline_draft (
    company_party_id varchar(80) NOT NULL,
    pipeline_system_id varchar(80) NOT NULL REFERENCES public.hidra_topology_pipeline_system(id) ON DELETE RESTRICT,
    manifest_id uuid NOT NULL,
    head_version bigint NOT NULL CHECK (head_version > 0),
    created_at timestamptz NOT NULL,
    created_by_user_id varchar(80) NOT NULL,
    PRIMARY KEY (company_party_id,pipeline_system_id,manifest_id),
    CHECK (company_party_id=btrim(company_party_id) AND length(company_party_id)>0
        AND pipeline_system_id=btrim(pipeline_system_id) AND length(pipeline_system_id)>0
        AND created_by_user_id=btrim(created_by_user_id) AND length(created_by_user_id)>0
        AND isfinite(created_at))
);

CREATE TABLE public.hidra_topology_engineering_baseline_revision (
    company_party_id varchar(80) NOT NULL,
    pipeline_system_id varchar(80) NOT NULL,
    manifest_id uuid NOT NULL,
    version bigint NOT NULL CHECK (version>0),
    payload_format varchar(48) NOT NULL CHECK (payload_format='HIDRA_ENGINEERING_BASELINE_MANIFEST_V1'),
    canonical_payload bytea NOT NULL CHECK (octet_length(canonical_payload) BETWEEN 64 AND 4194304),
    canonical_sha256 varchar(64) NOT NULL CHECK (canonical_sha256=encode(sha256(canonical_payload),'hex')),
    findings jsonb NOT NULL CHECK (jsonb_typeof(findings)='array'),
    complete_at_capture boolean NOT NULL,
    engineering_qualified boolean NOT NULL DEFAULT false CHECK (NOT engineering_qualified),
    authoritative boolean NOT NULL DEFAULT false CHECK (NOT authoritative),
    frozen boolean NOT NULL DEFAULT false CHECK (NOT frozen),
    actor_binding_id uuid NOT NULL,
    actor_session_id uuid NOT NULL,
    actor_user_id varchar(80) NOT NULL,
    sampled_at timestamptz NOT NULL,
    valid_until timestamptz NOT NULL,
    backend_pid integer NOT NULL CHECK (backend_pid>0),
    transaction_id bigint NOT NULL CHECK (transaction_id>0),
    database_role name NOT NULL,
    PRIMARY KEY (company_party_id,pipeline_system_id,manifest_id,version),
    FOREIGN KEY (company_party_id,pipeline_system_id,manifest_id)
        REFERENCES public.hidra_topology_engineering_baseline_draft(company_party_id,pipeline_system_id,manifest_id)
        ON DELETE RESTRICT DEFERRABLE INITIALLY DEFERRED,
    CHECK (isfinite(sampled_at) AND isfinite(valid_until) AND sampled_at<valid_until
        AND actor_user_id=btrim(actor_user_id) AND length(actor_user_id)>0
        AND complete_at_capture=(jsonb_array_length(findings)=0))
);

ALTER TABLE public.hidra_topology_engineering_baseline_draft
    ADD CONSTRAINT fk_topology_baseline_current_revision
    FOREIGN KEY (company_party_id,pipeline_system_id,manifest_id,head_version)
    REFERENCES public.hidra_topology_engineering_baseline_revision(company_party_id,pipeline_system_id,manifest_id,version)
    ON DELETE RESTRICT DEFERRABLE INITIALLY DEFERRED;

CREATE TABLE public.hidra_topology_engineering_baseline_request (
    company_party_id varchar(80) NOT NULL,
    pipeline_system_id varchar(80) NOT NULL,
    manifest_id uuid NOT NULL,
    actor_user_id varchar(80) NOT NULL,
    request_id uuid NOT NULL,
    operation varchar(8) NOT NULL CHECK (operation IN ('CREATE','UPDATE')),
    expected_version bigint NOT NULL CHECK (expected_version>=0),
    version bigint NOT NULL CHECK (version>0),
    command_sha256 varchar(64) NOT NULL CHECK (command_sha256 ~ '^[0-9a-f]{64}$'),
    actor_binding_id uuid NOT NULL,
    actor_session_id uuid NOT NULL,
    sampled_at timestamptz NOT NULL,
    valid_until timestamptz NOT NULL,
    backend_pid integer NOT NULL CHECK (backend_pid>0),
    transaction_id bigint NOT NULL CHECK (transaction_id>0),
    database_role name NOT NULL,
    PRIMARY KEY (company_party_id,pipeline_system_id,manifest_id,actor_user_id,request_id),
    FOREIGN KEY (company_party_id,pipeline_system_id,manifest_id,version)
        REFERENCES public.hidra_topology_engineering_baseline_revision(company_party_id,pipeline_system_id,manifest_id,version)
        ON DELETE RESTRICT,
    CHECK (version=expected_version+1 AND (operation='CREATE')=(expected_version=0)
        AND actor_user_id=btrim(actor_user_id) AND length(actor_user_id)>0
        AND isfinite(sampled_at) AND isfinite(valid_until) AND sampled_at<valid_until)
);

CREATE FUNCTION public.hidra_topology_baseline_immutable_guard()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    RAISE EXCEPTION 'Engineering baseline draft history is immutable' USING ERRCODE='23514';
END;
$body$;

CREATE FUNCTION public.hidra_topology_baseline_original_transaction_guard()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
DECLARE sampled_clock timestamptz;
BEGIN
    sampled_clock:=pg_catalog.clock_timestamp();
    IF NEW.backend_pid IS DISTINCT FROM pg_catalog.pg_backend_pid()
       OR NEW.transaction_id IS DISTINCT FROM pg_catalog.txid_current()
       OR NEW.database_role IS DISTINCT FROM session_user
       OR current_user IS DISTINCT FROM session_user
       OR pg_catalog.current_setting('transaction_isolation')<>'read committed'
       OR pg_catalog.current_setting('transaction_read_only')<>'off'
       OR pg_catalog.current_setting('session_replication_role')<>'origin'
       OR sampled_clock<NEW.sampled_at OR sampled_clock>=NEW.valid_until THEN
        RAISE EXCEPTION 'Original engineering baseline transaction unavailable' USING ERRCODE='42501';
    END IF;
    IF TG_TABLE_NAME='hidra_topology_engineering_baseline_revision' THEN
        IF NOT EXISTS (
            SELECT 1 FROM public.hidra_topology_engineering_baseline_draft d
             WHERE d.company_party_id=NEW.company_party_id AND d.pipeline_system_id=NEW.pipeline_system_id
               AND d.manifest_id=NEW.manifest_id
               AND (NEW.version=1 AND d.head_version=1
                    OR d.head_version<9223372036854775807::bigint AND NEW.version=d.head_version+1)
        ) THEN
            RAISE EXCEPTION 'Stale engineering baseline draft revision' USING ERRCODE='23514';
        END IF;
    ELSE
        IF NOT EXISTS (
            SELECT 1 FROM public.hidra_topology_engineering_baseline_revision r
             WHERE r.company_party_id=NEW.company_party_id AND r.pipeline_system_id=NEW.pipeline_system_id
               AND r.manifest_id=NEW.manifest_id AND r.version=NEW.version
               AND r.actor_user_id=NEW.actor_user_id AND r.actor_binding_id=NEW.actor_binding_id
               AND r.actor_session_id=NEW.actor_session_id AND r.backend_pid=NEW.backend_pid
               AND r.transaction_id=NEW.transaction_id AND r.database_role=NEW.database_role
               AND r.sampled_at=NEW.sampled_at AND r.valid_until=NEW.valid_until
        ) THEN
            RAISE EXCEPTION 'Engineering baseline receipt origin mismatch' USING ERRCODE='23514';
        END IF;
    END IF;
    RETURN NEW;
END;
$body$;

CREATE FUNCTION public.hidra_topology_baseline_head_guard()
RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    IF NEW.company_party_id IS DISTINCT FROM OLD.company_party_id
       OR NEW.pipeline_system_id IS DISTINCT FROM OLD.pipeline_system_id
       OR NEW.manifest_id IS DISTINCT FROM OLD.manifest_id
       OR NEW.created_at IS DISTINCT FROM OLD.created_at
       OR NEW.created_by_user_id IS DISTINCT FROM OLD.created_by_user_id
       OR OLD.head_version=9223372036854775807::bigint
       OR NEW.head_version<>OLD.head_version+1
       OR NOT EXISTS (
           SELECT 1 FROM public.hidra_topology_engineering_baseline_revision r
            WHERE r.company_party_id=NEW.company_party_id AND r.pipeline_system_id=NEW.pipeline_system_id
              AND r.manifest_id=NEW.manifest_id AND r.version=NEW.head_version
              AND r.backend_pid=pg_catalog.pg_backend_pid() AND r.transaction_id=pg_catalog.txid_current()
              AND r.database_role=session_user
       ) THEN
        RAISE EXCEPTION 'Invalid engineering baseline draft head transition' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END;
$body$;

CREATE TRIGGER trg_topology_baseline_revision_origin
    BEFORE INSERT ON public.hidra_topology_engineering_baseline_revision
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_baseline_original_transaction_guard();
CREATE TRIGGER trg_topology_baseline_request_origin
    BEFORE INSERT ON public.hidra_topology_engineering_baseline_request
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_baseline_original_transaction_guard();
CREATE TRIGGER trg_topology_baseline_revision_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_baseline_revision
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();
CREATE TRIGGER trg_topology_baseline_request_immutable
    BEFORE UPDATE OR DELETE ON public.hidra_topology_engineering_baseline_request
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();
CREATE TRIGGER trg_topology_baseline_head_transition
    BEFORE UPDATE ON public.hidra_topology_engineering_baseline_draft
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_baseline_head_guard();
CREATE TRIGGER trg_topology_baseline_head_delete
    BEFORE DELETE ON public.hidra_topology_engineering_baseline_draft
    FOR EACH ROW EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();
CREATE TRIGGER trg_topology_baseline_draft_truncate
    BEFORE TRUNCATE ON public.hidra_topology_engineering_baseline_draft
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();
CREATE TRIGGER trg_topology_baseline_revision_truncate
    BEFORE TRUNCATE ON public.hidra_topology_engineering_baseline_revision
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();
CREATE TRIGGER trg_topology_baseline_request_truncate
    BEFORE TRUNCATE ON public.hidra_topology_engineering_baseline_request
    FOR EACH STATEMENT EXECUTE FUNCTION public.hidra_topology_baseline_immutable_guard();

REVOKE ALL ON public.hidra_topology_engineering_baseline_draft FROM PUBLIC;
REVOKE ALL ON public.hidra_topology_engineering_baseline_revision FROM PUBLIC;
REVOKE ALL ON public.hidra_topology_engineering_baseline_request FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_baseline_immutable_guard() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_baseline_original_transaction_guard() FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_topology_baseline_head_guard() FROM PUBLIC;

-- Permission definitions only. Assignment remains explicit Identity-owned runtime data.
INSERT INTO public.hidra_identity_permission
    (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
    ('hapi605-baseline-create','topology:engineering-baseline:create','topology','ENGINEERING_BASELINE_DRAFT','create',true,'ACTIVE',now(),now()),
    ('hapi605-baseline-read','topology:engineering-baseline:read','topology','ENGINEERING_BASELINE_DRAFT','read',true,'ACTIVE',now(),now()),
    ('hapi605-baseline-update','topology:engineering-baseline:update','topology','ENGINEERING_BASELINE_DRAFT','update',true,'ACTIVE',now(),now()),
    ('hapi605-baseline-validate','topology:engineering-baseline:validate','topology','ENGINEERING_BASELINE_DRAFT','validate',true,'ACTIVE',now(),now());

-- Private lock capability only; no permission, data output or role assignment.
CREATE FUNCTION public.hidra_topology_baseline_lock_storage() RETURNS boolean
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    LOCK TABLE public.hidra_party_eligibility_generation,public.hidra_party_party,public.hidra_topology_engineering_baseline_draft,public.hidra_topology_engineering_baseline_request,public.hidra_topology_engineering_baseline_revision,public.hidra_topology_pipeline_system,public.hidra_topology_pipeline_system_type IN SHARE ROW EXCLUSIVE MODE NOWAIT;
    RETURN true;
END;
$body$;
REVOKE ALL ON FUNCTION public.hidra_topology_baseline_lock_storage() FROM PUBLIC;

-- Private lock capability only; no permission, data output or role assignment.
CREATE FUNCTION public.hidra_topology_baseline_lock_sources() RETURNS boolean
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    LOCK TABLE public.hidra_simulation_manufacturer_characteristic_revision,public.hidra_topology_engineering_context,public.hidra_topology_engineering_publication_event,public.hidra_topology_engineering_publication_head,public.hidra_topology_engineering_publication_snapshot,public.hidra_topology_facility,public.hidra_topology_installed_characteristic_approval,public.hidra_topology_installed_characteristic_revision,public.hidra_topology_installed_unit,public.hidra_topology_installed_unit_installation,public.hidra_topology_installed_unit_installation_event,public.hidra_topology_station_configuration_head,public.hidra_topology_station_configuration_revision IN SHARE ROW EXCLUSIVE MODE NOWAIT;
    RETURN true;
END;
$body$;
REVOKE ALL ON FUNCTION public.hidra_topology_baseline_lock_sources() FROM PUBLIC;

-- Private lock capability only; no permission, data output or role assignment.
CREATE FUNCTION public.hidra_custody_baseline_lock_product_source() RETURNS boolean
LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $body$
BEGIN
    LOCK TABLE public.hidra_custody_catalog_entry,public.hidra_custody_product_publication,public.hidra_custody_product_publication_event,public.hidra_custody_product_publication_head IN SHARE ROW EXCLUSIVE MODE NOWAIT;
    RETURN true;
END;
$body$;
REVOKE ALL ON FUNCTION public.hidra_custody_baseline_lock_product_source() FROM PUBLIC;
