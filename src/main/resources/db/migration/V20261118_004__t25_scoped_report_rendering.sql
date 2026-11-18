-- Additive receipts for the fixed scoped GZ2 report profile. Authority stays with each owner.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t25-report-submit','reporting:scoped-report:submit','reporting','SCOPED_REPORT','submit',true,'ACTIVE',now(),now()),
('t25-report-render','reporting:scoped-report:render','reporting','SCOPED_REPORT','render',true,'ACTIVE',now(),now()),
('t25-report-read','reporting:scoped-report:read','reporting','SCOPED_REPORT','read',true,'ACTIVE',now(),now()),
('t25-report-seal','reporting:scoped-report:seal','reporting','SCOPED_REPORT','seal',true,'ACTIVE',now(),now());

CREATE TABLE public.hidra_reporting_t25_run_receipt (
    request_id varchar(80) PRIMARY KEY REFERENCES public.hidra_reporting_request(id),
    company_id varchar(96) NOT NULL CHECK (btrim(company_id) <> ''),
    facility_id varchar(120) NOT NULL CHECK (btrim(facility_id) <> ''),
    request_key varchar(80) NOT NULL,
    requester_id varchar(80) NOT NULL,
    definition_id varchar(80) NOT NULL,
    template_version_id varchar(80) NOT NULL REFERENCES public.hidra_reporting_report_template_version(id),
    template_hash varchar(64) NOT NULL CHECK (template_hash ~ '^[a-f0-9]{64}$'),
    metric_run_id varchar(80) NOT NULL,
    source_hash varchar(64) NOT NULL CHECK (source_hash ~ '^[a-f0-9]{64}$'),
    source_projection text NOT NULL CHECK (jsonb_typeof(source_projection::jsonb) = 'object'
        AND octet_length(source_projection) BETWEEN 1 AND 65536
        AND encode(sha256(convert_to(source_projection,'UTF8')),'hex')=source_hash),
    run_id varchar(80) UNIQUE REFERENCES public.hidra_reporting_run(id),
    render_key varchar(80),
    workflow_instance_id varchar(80) UNIQUE,
    workflow_action_id varchar(80) UNIQUE,
    reviewer_id varchar(80),
    reviewed_at timestamptz,
    workflow_definition_id varchar(80),
    workflow_definition_version integer,
    phase varchar(20) NOT NULL CHECK (phase IN ('SUBMITTED','QUEUED','RUNNING','RENDERED','FAILED','COMPLETED')),
    document_id varchar(80) UNIQUE,
    document_version_id varchar(80) UNIQUE,
    content_length bigint CHECK (content_length BETWEEN 1 AND 65536),
    output_hash varchar(64) CHECK (output_hash = source_hash),
    seal_key varchar(80),
    custodian_id varchar(80),
    acceptance_id varchar(80) UNIQUE,
    artifact_id varchar(80) UNIQUE REFERENCES public.hidra_reporting_output_artifact(id),
    failure_code varchar(80),
    UNIQUE (company_id,facility_id,request_key),
    FOREIGN KEY (request_id,definition_id) REFERENCES public.hidra_reporting_request(id,report_definition_id),
    CHECK (num_nonnulls(run_id,render_key,workflow_instance_id,workflow_action_id,reviewer_id,reviewed_at,
         workflow_definition_id,workflow_definition_version) = CASE WHEN phase='SUBMITTED' THEN 0 ELSE 8 END),
    CHECK (reviewer_id IS NULL OR reviewer_id <> requester_id),
    CHECK (num_nonnulls(document_id,document_version_id,content_length,output_hash) =
           CASE WHEN phase IN ('RENDERED','COMPLETED') THEN 4 ELSE 0 END),
    CHECK (num_nonnulls(seal_key,custodian_id,acceptance_id,artifact_id) =
           CASE WHEN phase='COMPLETED' THEN 4 ELSE 0 END),
    CHECK (custodian_id IS NULL OR (custodian_id <> requester_id AND custodian_id <> reviewer_id)),
    CHECK ((failure_code IS NOT NULL) = (phase='FAILED'))
);

CREATE TABLE public.hidra_documents_t25_report_output (
    document_id varchar(80) PRIMARY KEY REFERENCES public.hidra_documents_controlled_scope(document_id),
    run_id varchar(80) NOT NULL UNIQUE,
    company_id varchar(96) NOT NULL,
    facility_id varchar(120) NOT NULL,
    version_id varchar(80) UNIQUE,
    acceptance_id varchar(80) UNIQUE REFERENCES public.hidra_documents_controlled_decision(id),
    checksum_sha256 varchar(64) NOT NULL CHECK (checksum_sha256 ~ '^[a-f0-9]{64}$'),
    content_length bigint NOT NULL CHECK (content_length BETWEEN 1 AND 65536),
    FOREIGN KEY (document_id,version_id) REFERENCES public.hidra_documents_document_version(document_id,id),
    CHECK (acceptance_id IS NULL OR version_id IS NOT NULL)
);

CREATE FUNCTION public.t25_preserve_receipt() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP <> 'UPDATE' THEN RAISE EXCEPTION 'T25 evidence is retained' USING ERRCODE='23514'; END IF;
    IF ROW(OLD.request_id,OLD.company_id,OLD.facility_id,OLD.request_key,OLD.requester_id,
           OLD.definition_id,OLD.template_version_id,OLD.template_hash,OLD.metric_run_id,OLD.source_hash,OLD.source_projection)
       IS DISTINCT FROM ROW(NEW.request_id,NEW.company_id,NEW.facility_id,NEW.request_key,NEW.requester_id,
           NEW.definition_id,NEW.template_version_id,NEW.template_hash,NEW.metric_run_id,NEW.source_hash,NEW.source_projection)
       OR NOT (ROW(OLD.phase,NEW.phase) IN (ROW('SUBMITTED','QUEUED'),ROW('QUEUED','RUNNING'),
                      ROW('RUNNING','RENDERED'),ROW('RUNNING','FAILED'),ROW('RENDERED','COMPLETED')))
       OR (OLD.run_id IS NOT NULL AND ROW(OLD.run_id,OLD.render_key,OLD.workflow_instance_id,OLD.workflow_action_id,
                       OLD.reviewer_id,OLD.reviewed_at,OLD.workflow_definition_id,OLD.workflow_definition_version)
           IS DISTINCT FROM ROW(NEW.run_id,NEW.render_key,NEW.workflow_instance_id,NEW.workflow_action_id,
                       NEW.reviewer_id,NEW.reviewed_at,NEW.workflow_definition_id,NEW.workflow_definition_version))
       OR (OLD.document_id IS NOT NULL AND ROW(OLD.document_id,OLD.document_version_id,OLD.output_hash,OLD.content_length)
           IS DISTINCT FROM ROW(NEW.document_id,NEW.document_version_id,NEW.output_hash,NEW.content_length)) THEN
        RAISE EXCEPTION 'Invalid T25 report transition' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER t25_receipt_transition BEFORE UPDATE OR DELETE ON public.hidra_reporting_t25_run_receipt
FOR EACH ROW EXECUTE FUNCTION public.t25_preserve_receipt();
CREATE TRIGGER t25_receipt_truncate BEFORE TRUNCATE ON public.hidra_reporting_t25_run_receipt
FOR EACH STATEMENT EXECUTE FUNCTION public.t25_preserve_receipt();

CREATE FUNCTION public.t25_preserve_parameter() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM public.hidra_reporting_t25_run_receipt receipt
        WHERE (TG_OP<>'INSERT' AND receipt.request_id=OLD.report_request_id)
           OR (TG_OP<>'DELETE' AND receipt.request_id=NEW.report_request_id)) THEN
        RAISE EXCEPTION 'T25 report parameters are retained' USING ERRCODE='23514';
    END IF;
    RETURN CASE WHEN TG_OP='DELETE' THEN OLD ELSE NEW END;
END $$;
CREATE TRIGGER t25_parameter_guard BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_reporting_parameter_value
FOR EACH ROW EXECUTE FUNCTION public.t25_preserve_parameter();

CREATE FUNCTION public.t25_preserve_document_marker() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP <> 'UPDATE' THEN RAISE EXCEPTION 'T25 document evidence is retained' USING ERRCODE='23514'; END IF;
    IF ROW(NEW.document_id,NEW.company_id,NEW.facility_id,NEW.run_id,NEW.checksum_sha256,NEW.content_length)
       IS DISTINCT FROM ROW(OLD.document_id,OLD.company_id,OLD.facility_id,OLD.run_id,OLD.checksum_sha256,OLD.content_length)
       OR OLD.acceptance_id IS NOT NULL
       OR NOT ((OLD.version_id IS NULL AND NEW.version_id IS NOT NULL AND NEW.acceptance_id IS NULL)
          OR (OLD.version_id IS NOT NULL AND NEW.version_id = OLD.version_id AND NEW.acceptance_id IS NOT NULL)) THEN
        RAISE EXCEPTION 'Invalid T25 document marker transition' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER t25_marker_transition BEFORE UPDATE OR DELETE ON public.hidra_documents_t25_report_output
FOR EACH ROW EXECUTE FUNCTION public.t25_preserve_document_marker();
CREATE TRIGGER t25_marker_truncate BEFORE TRUNCATE ON public.hidra_documents_t25_report_output
FOR EACH STATEMENT EXECUTE FUNCTION public.t25_preserve_document_marker();

CREATE FUNCTION public.t25_report_consistency() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE scoped_request varchar(80); receipt public.hidra_reporting_t25_run_receipt%ROWTYPE;
BEGIN
    IF TG_TABLE_NAME='hidra_reporting_t25_run_receipt' THEN scoped_request := NEW.request_id;
    ELSIF TG_TABLE_NAME='hidra_reporting_run' THEN scoped_request := NEW.report_request_id;
    ELSIF TG_TABLE_NAME='hidra_reporting_request' THEN scoped_request := NEW.id;
    ELSE
        SELECT rr.report_request_id INTO scoped_request FROM public.hidra_reporting_run rr
        WHERE rr.id=CASE WHEN TG_OP='DELETE' THEN OLD.report_run_id ELSE NEW.report_run_id END;
    END IF;
    SELECT r.* INTO receipt FROM public.hidra_reporting_t25_run_receipt r WHERE r.request_id=scoped_request;
    IF NOT FOUND THEN RETURN NULL; END IF;
    IF NOT EXISTS (SELECT 1 FROM public.hidra_reporting_request r
          WHERE r.id=receipt.request_id AND r.requested_by_actor_id=receipt.requester_id
          AND (CASE WHEN receipt.phase='SUBMITTED' THEN r.status='SUBMITTED' AND r.workflow_reference_id IS NULL
                    ELSE r.status='APPROVED' AND r.workflow_reference_id=receipt.workflow_instance_id END) IS TRUE)
        OR NOT EXISTS (SELECT 1 FROM public.hidra_reporting_parameter_value p
            WHERE p.report_request_id=receipt.request_id AND p.parameter_code='metricRunId'
              AND p.value_type='REFERENCE' AND p.value_text=receipt.metric_run_id)
        OR NOT EXISTS (SELECT 1 FROM public.hidra_analytics_deviation_metric_receipt m
            JOIN public.hidra_analytics_metric_evaluation_run mr ON mr.id=m.run_id
            WHERE m.run_id=receipt.metric_run_id AND m.company_party_id=receipt.company_id
              AND m.facility_id=receipt.facility_id AND mr.run_status='COMPLETED' AND m.value_id IS NOT NULL
              AND m.evaluation_id=receipt.source_projection::jsonb->>'evaluationId'
              AND m.definition_version_id=receipt.source_projection::jsonb->>'definitionVersionId')
        OR (receipt.phase<>'SUBMITTED' AND NOT EXISTS (
            SELECT 1 FROM public.hidra_workflow_action a JOIN public.hidra_workflow_instance i ON i.id=a.instance_id
            JOIN public.hidra_workflow_task t ON t.id=a.task_id AND t.instance_id=i.id
            JOIN public.hidra_workflow_type_catalog target_type ON target_type.id=i.target_type_id
            JOIN public.hidra_workflow_type_catalog purpose ON purpose.id=i.workflow_purpose_id
            WHERE i.id=receipt.workflow_instance_id AND i.definition_id=receipt.workflow_definition_id
              AND i.definition_version=receipt.workflow_definition_version AND i.target_module='reporting'
              AND i.target_id=receipt.request_id AND i.started_by_actor_id=receipt.requester_id AND i.status='COMPLETED'
              AND target_type.catalog_name='WORKFLOW_TARGET_TYPE' AND target_type.code='REPORT_REQUEST'
              AND purpose.catalog_name='WORKFLOW_PURPOSE' AND purpose.code='REPORT_APPROVAL'
              AND a.id=receipt.workflow_action_id AND a.actor_id=receipt.reviewer_id
              AND a.action_type='APPROVE' AND a.decision='APPROVE'
              AND a.acted_at=receipt.reviewed_at AND a.acted_at=i.completed_at))
        OR (receipt.document_id IS NOT NULL AND NOT EXISTS (
            SELECT 1 FROM public.hidra_documents_t25_report_output m
            WHERE m.document_id=receipt.document_id AND m.run_id=receipt.run_id
              AND m.company_id=receipt.company_id AND m.facility_id=receipt.facility_id
              AND m.version_id=receipt.document_version_id AND m.checksum_sha256=receipt.output_hash
              AND m.content_length=receipt.content_length AND m.acceptance_id IS NOT DISTINCT FROM receipt.acceptance_id))
        OR (receipt.phase='COMPLETED' AND NOT EXISTS (
            SELECT 1 FROM public.hidra_documents_controlled_decision d
            WHERE d.id=receipt.acceptance_id AND d.document_id=receipt.document_id AND d.version_id=receipt.document_version_id
              AND d.actor_id=receipt.custodian_id AND d.action='ACCEPT'
              AND d.company_id=receipt.company_id AND d.facility_id=receipt.facility_id))
        OR EXISTS (SELECT 1 FROM public.hidra_reporting_run r WHERE r.report_request_id=receipt.request_id
                       AND r.id IS DISTINCT FROM receipt.run_id)
        OR (receipt.run_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM public.hidra_reporting_run r
          WHERE r.id=receipt.run_id AND r.report_request_id=receipt.request_id AND r.report_definition_id=receipt.definition_id
          AND r.template_version_id=receipt.template_version_id
          AND r.status=(CASE WHEN receipt.phase='RENDERED' THEN 'RUNNING' ELSE receipt.phase END)
          AND (receipt.phase <> 'COMPLETED' OR r.output_count=1)))
        OR EXISTS (SELECT 1 FROM public.hidra_reporting_output_artifact a WHERE a.report_run_id=receipt.run_id
                       AND a.id IS DISTINCT FROM receipt.artifact_id)
        OR (receipt.phase='COMPLETED' AND NOT EXISTS (SELECT 1 FROM public.hidra_reporting_output_artifact a
          WHERE a.id=receipt.artifact_id AND a.report_run_id=receipt.run_id AND a.document_reference_id=receipt.document_id
          AND a.checksum=receipt.output_hash AND a.size_bytes=receipt.content_length AND a.artifact_type='PRIMARY_REPORT'
          AND a.format='JSON' AND a.mime_type='application/json')) THEN
        RAISE EXCEPTION 'T25 report evidence is inconsistent' USING ERRCODE='23514';
    END IF;
    RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER t25_receipt_consistency AFTER INSERT OR UPDATE ON public.hidra_reporting_t25_run_receipt
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t25_report_consistency();
CREATE CONSTRAINT TRIGGER t25_run_consistency AFTER INSERT OR UPDATE ON public.hidra_reporting_run
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t25_report_consistency();
CREATE CONSTRAINT TRIGGER t25_request_consistency AFTER UPDATE ON public.hidra_reporting_request
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t25_report_consistency();
CREATE CONSTRAINT TRIGGER t25_artifact_consistency AFTER INSERT OR UPDATE OR DELETE ON public.hidra_reporting_output_artifact
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t25_report_consistency();

CREATE FUNCTION public.t25_document_consistency() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE marker public.hidra_documents_t25_report_output%ROWTYPE;
BEGIN
    SELECT m.* INTO marker FROM public.hidra_documents_t25_report_output m WHERE m.document_id=NEW.document_id;
    IF NOT FOUND THEN RETURN NULL; END IF;
    IF marker.version_id IS NULL OR NOT EXISTS (
        SELECT 1 FROM public.hidra_documents_controlled_scope s
        JOIN public.hidra_documents_document_version v ON v.document_id=s.document_id
        WHERE s.document_id=marker.document_id AND v.id=marker.version_id
          AND s.company_id=marker.company_id AND s.facility_id=marker.facility_id
          AND v.file_size_bytes=marker.content_length AND v.checksum_value=marker.checksum_sha256)
       OR (marker.acceptance_id IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM public.hidra_documents_controlled_decision d
        WHERE d.id=marker.acceptance_id AND d.document_id=marker.document_id
          AND d.version_id=marker.version_id AND d.checksum_sha256=marker.checksum_sha256
          AND d.content_length=marker.content_length)) THEN
        RAISE EXCEPTION 'T25 document evidence is inconsistent' USING ERRCODE='23514';
    END IF;
    RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER t25_document_marker_consistency AFTER INSERT OR UPDATE ON public.hidra_documents_t25_report_output
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t25_document_consistency();
