-- T26: additive synthetic Integration-to-Documents draft receipts.
-- No production connectors, data or grants are seeded.  All physical ownership here is Integration.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t26-exchange-read','integration:synthetic-exchange:read','integration','SYNTHETIC_EXCHANGE','read',true,'ACTIVE',now(),now()),
('t26-exchange-execute','integration:synthetic-exchange:execute','integration','SYNTHETIC_EXCHANGE','execute',true,'ACTIVE',now(),now()),
('t26-exchange-replay','integration:synthetic-exchange:replay','integration','SYNTHETIC_EXCHANGE','replay',true,'ACTIVE',now(),now());

CREATE TABLE public.hidra_integration_t26_mapping_binding (
    id varchar(80) PRIMARY KEY,
    company_id varchar(96) NOT NULL CHECK (btrim(company_id)<>'' AND company_id=btrim(company_id)),
    facility_id varchar(120) NOT NULL CHECK (btrim(facility_id)<>'' AND facility_id=btrim(facility_id)),
    mapping_profile_id varchar(80) NOT NULL REFERENCES public.hidra_integration_mapping_profile(id),
    schema_version_id varchar(80) NOT NULL REFERENCES public.hidra_integration_schema_version(id),
    job_definition_id varchar(80) NOT NULL REFERENCES public.hidra_integration_job_definition(id),
    external_system_id varchar(80) NOT NULL REFERENCES public.hidra_integration_external_system(id),
    message_type_id varchar(80) NOT NULL REFERENCES public.hidra_integration_catalog_entry(id),
    payload_format_id varchar(80) NOT NULL REFERENCES public.hidra_integration_catalog_entry(id),
    document_type_id varchar(80) NOT NULL,
    classification_id varchar(80) NOT NULL,
    status varchar(20) NOT NULL CHECK (status IN ('ACTIVE','RETIRED')),
    created_at timestamptz NOT NULL DEFAULT now(),
    UNIQUE(company_id,facility_id,mapping_profile_id),
    UNIQUE(id,company_id,facility_id,mapping_profile_id)
);

CREATE TABLE public.hidra_integration_t26_exchange_receipt (
    id varchar(80) PRIMARY KEY,
    binding_id varchar(80) NOT NULL,
    company_id varchar(96) NOT NULL,
    facility_id varchar(120) NOT NULL,
    request_key varchar(80) NOT NULL CHECK (btrim(request_key)<>'' AND request_key=btrim(request_key)),
    submitter_id varchar(80) NOT NULL,
    mapping_profile_id varchar(80) NOT NULL,
    schema_version_id varchar(80) NOT NULL,
    schema_checksum varchar(128) NOT NULL CHECK (btrim(schema_checksum)<>''),
    mapping_digest char(64) NOT NULL CHECK (mapping_digest ~ '^[a-f0-9]{64}$'),
    payload_digest char(64) NOT NULL CHECK (payload_digest ~ '^[a-f0-9]{64}$'),
    projection text NOT NULL CHECK (
       octet_length(projection) BETWEEN 1 AND 1000
       AND jsonb_typeof(projection::jsonb)='object'
       AND encode(sha256(convert_to(projection,'UTF8')),'hex')=payload_digest),
    title_fr varchar(160) NOT NULL CHECK (btrim(title_fr)<>''),
    document_type_id varchar(80) NOT NULL,
    classification_id varchar(80) NOT NULL,
    job_definition_id varchar(80) NOT NULL REFERENCES public.hidra_integration_job_definition(id),
    external_system_id varchar(80) NOT NULL REFERENCES public.hidra_integration_external_system(id),
    message_type_id varchar(80) NOT NULL REFERENCES public.hidra_integration_catalog_entry(id),
    payload_format_id varchar(80) NOT NULL REFERENCES public.hidra_integration_catalog_entry(id),
    phase varchar(20) NOT NULL CHECK (phase IN ('CLAIMED','FAILED','SUCCEEDED','ABANDONED')),
    latest_attempt integer NOT NULL CHECK (latest_attempt BETWEEN 1 AND 2),
    document_id varchar(80),
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    CONSTRAINT t26_exchange_binding FOREIGN KEY (binding_id,company_id,facility_id,mapping_profile_id)
        REFERENCES public.hidra_integration_t26_mapping_binding(id,company_id,facility_id,mapping_profile_id),
    CONSTRAINT t26_exchange_receipt_shape CHECK ((phase='SUCCEEDED')=(document_id IS NOT NULL)),
    CONSTRAINT t26_exchange_one_key UNIQUE(company_id,facility_id,request_key),
    CONSTRAINT t26_exchange_one_document UNIQUE(document_id),
    UNIQUE(id,latest_attempt)
);

CREATE TABLE public.hidra_integration_t26_attempt_receipt (
    id varchar(80) PRIMARY KEY,
    exchange_id varchar(80) NOT NULL REFERENCES public.hidra_integration_t26_exchange_receipt(id),
    attempt_ordinal integer NOT NULL CHECK (attempt_ordinal BETWEEN 1 AND 2),
    actor_id varchar(80) NOT NULL,
    request_key varchar(80) NOT NULL,
    reason varchar(500),
    job_run_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_integration_job_run(id),
    message_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_integration_exchange_message(id),
    inbound_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_integration_inbound_record(id),
    dead_letter_id varchar(80) UNIQUE REFERENCES public.hidra_integration_dead_letter_record(id),
    phase varchar(20) NOT NULL CHECK (phase IN ('CLAIMED','FAILED','SUCCEEDED','ABANDONED')),
    failure_code varchar(120),
    document_id varchar(80),
    claimed_at timestamptz NOT NULL,
    expires_at timestamptz NOT NULL CHECK (expires_at>claimed_at),
    completed_at timestamptz,
    UNIQUE(exchange_id,attempt_ordinal),
    UNIQUE(exchange_id,request_key),
    CONSTRAINT t26_attempt_shape CHECK (
        (phase='CLAIMED' AND completed_at IS NULL AND failure_code IS NULL AND dead_letter_id IS NULL AND document_id IS NULL)
        OR (phase='SUCCEEDED' AND completed_at IS NOT NULL AND failure_code IS NULL
            AND dead_letter_id IS NULL AND document_id IS NOT NULL)
        OR (phase='FAILED' AND completed_at IS NOT NULL AND failure_code IS NOT NULL
            AND dead_letter_id IS NOT NULL AND document_id IS NULL)
        OR (phase='ABANDONED' AND completed_at IS NOT NULL AND failure_code IS NOT NULL AND document_id IS NULL)),
    CONSTRAINT t26_attempt_order CHECK (completed_at IS NULL OR completed_at>=claimed_at)
);

CREATE INDEX t26_attempt_history ON public.hidra_integration_t26_attempt_receipt(exchange_id,attempt_ordinal);
CREATE INDEX t26_exchange_scope ON public.hidra_integration_t26_exchange_receipt(company_id,facility_id,id);

-- Evidence never changes its pinned source identity, mapping or actor after admission.
CREATE FUNCTION public.t26_preserve_exchange() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='DELETE' OR TG_OP='TRUNCATE' THEN
        RAISE EXCEPTION 'T26 exchange evidence cannot be deleted' USING ERRCODE='23514';
    END IF;
    IF ROW(NEW.id,NEW.binding_id,NEW.company_id,NEW.facility_id,NEW.request_key,NEW.submitter_id,
            NEW.mapping_profile_id,NEW.schema_version_id,NEW.schema_checksum,NEW.mapping_digest,
            NEW.payload_digest,NEW.projection,NEW.title_fr,NEW.document_type_id,
            NEW.classification_id,NEW.job_definition_id,NEW.external_system_id,
            NEW.message_type_id,NEW.payload_format_id,NEW.created_at)
       IS DISTINCT FROM
       ROW(OLD.id,OLD.binding_id,OLD.company_id,OLD.facility_id,OLD.request_key,OLD.submitter_id,
            OLD.mapping_profile_id,OLD.schema_version_id,OLD.schema_checksum,OLD.mapping_digest,
            OLD.payload_digest,OLD.projection,OLD.title_fr,OLD.document_type_id,
            OLD.classification_id,OLD.job_definition_id,OLD.external_system_id,
            OLD.message_type_id,OLD.payload_format_id,OLD.created_at)
       OR (OLD.phase='SUCCEEDED' AND NEW.phase<>'SUCCEEDED')
       OR (OLD.phase='FAILED' AND NEW.phase NOT IN ('FAILED','CLAIMED'))
       OR NEW.latest_attempt < OLD.latest_attempt
       OR NEW.latest_attempt > OLD.latest_attempt + 1
       OR (NEW.phase='CLAIMED' AND NEW.latest_attempt>1 AND OLD.phase NOT IN ('FAILED','ABANDONED'))
       OR (OLD.document_id IS NOT NULL AND NEW.document_id IS DISTINCT FROM OLD.document_id) THEN
       RAISE EXCEPTION 'T26 pinned exchange or terminal receipt cannot be rewritten' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER t26_exchange_preservation BEFORE UPDATE OR DELETE
 ON public.hidra_integration_t26_exchange_receipt FOR EACH ROW EXECUTE FUNCTION public.t26_preserve_exchange();
CREATE TRIGGER t26_exchange_no_truncate BEFORE TRUNCATE ON public.hidra_integration_t26_exchange_receipt
 FOR EACH STATEMENT EXECUTE FUNCTION public.t26_preserve_exchange();

CREATE FUNCTION public.t26_preserve_attempt() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='DELETE' OR TG_OP='TRUNCATE' THEN
        RAISE EXCEPTION 'T26 attempt evidence cannot be deleted' USING ERRCODE='23514';
    END IF;
    IF ROW(NEW.id,NEW.exchange_id,NEW.attempt_ordinal,NEW.actor_id,NEW.request_key,NEW.reason,
           NEW.job_run_id,NEW.message_id,NEW.inbound_id,NEW.claimed_at,NEW.expires_at)
       IS DISTINCT FROM
       ROW(OLD.id,OLD.exchange_id,OLD.attempt_ordinal,OLD.actor_id,OLD.request_key,OLD.reason,
           OLD.job_run_id,OLD.message_id,OLD.inbound_id,OLD.claimed_at,OLD.expires_at)
       OR OLD.phase<>'CLAIMED' OR NEW.phase='CLAIMED' THEN
        RAISE EXCEPTION 'T26 attempt evidence is immutable after completion' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER t26_attempt_preservation BEFORE UPDATE OR DELETE
 ON public.hidra_integration_t26_attempt_receipt FOR EACH ROW EXECUTE FUNCTION public.t26_preserve_attempt();
CREATE TRIGGER t26_attempt_no_truncate BEFORE TRUNCATE ON public.hidra_integration_t26_attempt_receipt
 FOR EACH STATEMENT EXECUTE FUNCTION public.t26_preserve_attempt();

-- On commit, the receipt, native run/message/inbound row and T24 draft must agree.
CREATE FUNCTION public.t26_verify_evidence() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE xid varchar(80); e public.hidra_integration_t26_exchange_receipt%ROWTYPE;
BEGIN
    IF TG_TABLE_NAME='hidra_integration_t26_exchange_receipt' THEN xid:=NEW.id;
    ELSIF TG_TABLE_NAME='hidra_integration_t26_attempt_receipt' THEN xid:=NEW.exchange_id;
    ELSE RETURN NULL; END IF;
    SELECT * INTO e FROM public.hidra_integration_t26_exchange_receipt WHERE id=xid;
    IF NOT FOUND THEN RETURN NULL; END IF;
    IF (SELECT count(*) FROM public.hidra_integration_t26_attempt_receipt a WHERE a.exchange_id=xid) <> e.latest_attempt
       OR NOT EXISTS (
           SELECT 1 FROM public.hidra_integration_t26_attempt_receipt a
           JOIN public.hidra_integration_job_run r ON r.id=a.job_run_id
           JOIN public.hidra_integration_exchange_message m ON m.id=a.message_id
           JOIN public.hidra_integration_inbound_record i ON i.id=a.inbound_id
           WHERE a.exchange_id=xid AND a.attempt_ordinal=e.latest_attempt
             AND a.phase=e.phase AND a.document_id IS NOT DISTINCT FROM e.document_id
             AND r.job_definition_id=e.job_definition_id AND r.trigger_type='MANUAL'
             AND r.triggered_by_actor_id=a.actor_id AND r.correlation_id='T26:'||xid||':'||a.attempt_ordinal
             AND m.job_run_id=r.id AND m.external_system_id=e.external_system_id
             AND m.external_message_id='T26:'||xid||':'||a.attempt_ordinal
             AND m.payload_hash=e.payload_digest AND m.payload_storage_mode='INLINE_SANITIZED'
             AND i.job_run_id=r.id AND i.exchange_message_id=m.id AND i.mapping_profile_id=e.mapping_profile_id
             AND i.target_module='documents' AND i.target_type_code='DOCUMENT'
             AND i.target_id IS NOT DISTINCT FROM a.document_id
             AND (CASE a.phase
                WHEN 'CLAIMED' THEN r.status='RUNNING' AND m.status='RECEIVED' AND i.submission_status='NOT_SUBMITTED'
                WHEN 'SUCCEEDED' THEN r.status='COMPLETED' AND m.status='ACCEPTED' AND i.submission_status='ACCEPTED'
                     AND EXISTS (SELECT 1 FROM public.hidra_documents_controlled_scope s
                       WHERE s.document_id=a.document_id AND s.company_id=e.company_id
                         AND s.facility_id=e.facility_id AND s.create_request_id='T26-'||xid)
                WHEN 'FAILED' THEN r.status='FAILED' AND m.status='DEAD_LETTERED' AND i.submission_status='DEAD_LETTERED'
                     AND EXISTS (SELECT 1 FROM public.hidra_integration_dead_letter_record d
                        WHERE d.id=a.dead_letter_id AND d.job_run_id=r.id AND d.exchange_message_id=m.id
                         AND d.inbound_record_id=i.id AND d.status IN ('OPEN','REPLAYED'))
                WHEN 'ABANDONED' THEN r.status='FAILED' AND i.submission_status='DEAD_LETTERED'
                ELSE false END)
           )
       THEN RAISE EXCEPTION 'T26 native exchange or receiver receipt mismatch' USING ERRCODE='23514';
    END IF;
    RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER t26_exchange_commit AFTER INSERT OR UPDATE
 ON public.hidra_integration_t26_exchange_receipt DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.t26_verify_evidence();
CREATE CONSTRAINT TRIGGER t26_attempt_commit AFTER INSERT OR UPDATE
 ON public.hidra_integration_t26_attempt_receipt DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.t26_verify_evidence();

-- A reserved correlation or message identifier cannot be created from the legacy paths.
CREATE FUNCTION public.t26_guard_native_reserved() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE native_id varchar(80); matched boolean;
BEGIN
    IF TG_TABLE_NAME='hidra_integration_job_run' THEN
        IF NEW.correlation_id IS NULL OR NEW.correlation_id NOT LIKE 'T26:%' THEN RETURN NULL; END IF;
        native_id:=NEW.id;
        SELECT EXISTS(SELECT 1 FROM public.hidra_integration_t26_attempt_receipt a
                      WHERE a.job_run_id=native_id) INTO matched;
    ELSE
        IF NEW.external_message_id IS NULL OR NEW.external_message_id NOT LIKE 'T26:%' THEN RETURN NULL; END IF;
        native_id:=NEW.id;
        SELECT EXISTS(SELECT 1 FROM public.hidra_integration_t26_attempt_receipt a
                      WHERE a.message_id=native_id) INTO matched;
    END IF;
    IF NOT matched THEN
        RAISE EXCEPTION 'Reserved T26 native identity requires a governed attempt receipt'
          USING ERRCODE='23514';
    END IF;
    RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER t26_native_run_guard AFTER INSERT OR UPDATE
 ON public.hidra_integration_job_run DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.t26_guard_native_reserved();
CREATE CONSTRAINT TRIGGER t26_native_message_guard AFTER INSERT OR UPDATE
 ON public.hidra_integration_exchange_message DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.t26_guard_native_reserved();
