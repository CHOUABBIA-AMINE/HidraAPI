-- T28: immutable, company/facility-scoped governed Risk approval Audit evidence.
-- All identifiers are Audit-owned projections; no cross-module FK, legacy rewrite or grants.
INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
 ('t28-scoped-audit-read','audit:scoped-evidence:read','audit','SCOPED_EVIDENCE','read',true,'ACTIVE',now(),now()),
 ('t28-scoped-audit-export','audit:scoped-evidence:export','audit','SCOPED_EVIDENCE','export',true,'ACTIVE',now(),now());

CREATE TABLE public.hidra_audit_t28_event_scope (
 event_id varchar(80) PRIMARY KEY REFERENCES public.hidra_audit_event(id) ON DELETE RESTRICT,
 company_id varchar(96) NOT NULL CHECK(company_id=btrim(company_id) AND company_id<>''),
 facility_id varchar(120) NOT NULL CHECK(facility_id=btrim(facility_id) AND facility_id<>''),
 event_type varchar(80) NOT NULL CHECK(event_type='RISK_ASSESSMENT_APPROVED'),
 workflow_action_id varchar(120) NOT NULL UNIQUE CHECK(btrim(workflow_action_id)<>''),
 occurred_at timestamptz NOT NULL,
 CONSTRAINT t28_event_scope_composite UNIQUE(event_id,company_id,facility_id)
);
CREATE INDEX t28_event_scope_keyset ON public.hidra_audit_t28_event_scope
 (company_id,facility_id,event_type,occurred_at DESC,event_id DESC);

CREATE TABLE public.hidra_audit_t28_export (
 id uuid PRIMARY KEY,
 company_id varchar(96) NOT NULL CHECK(company_id=btrim(company_id) AND company_id<>''),
 facility_id varchar(120) NOT NULL CHECK(facility_id=btrim(facility_id) AND facility_id<>''),
 requester varchar(120) NOT NULL CHECK(btrim(requester)<>'' AND requester=btrim(requester)),
 request_key varchar(80) NOT NULL CHECK(length(request_key) BETWEEN 1 AND 80 AND request_key=btrim(request_key)),
 purpose varchar(40) NOT NULL CHECK(purpose='GZ2_SYNTHETIC_ASSURANCE'),
 format varchar(40) NOT NULL CHECK(format='GZ2_AUDIT_EVIDENCE_JSON_V1'),
 event_type varchar(80) NOT NULL CHECK(event_type='RISK_ASSESSMENT_APPROVED'),
 window_from timestamptz NOT NULL,
 window_to timestamptz NOT NULL,
 event_count integer NOT NULL CHECK(event_count BETWEEN 0 AND 1000),
 byte_length integer NOT NULL CHECK(byte_length BETWEEN 1 AND 262144),
 sha256 char(64) NOT NULL CHECK(sha256 ~ '^[a-f0-9]{64}$'),
 content bytea NOT NULL,
 receipt_id uuid NOT NULL UNIQUE,
 completed_at timestamptz NOT NULL,
 CONSTRAINT t28_export_time CHECK(window_from<window_to AND window_to<=window_from+interval '31 days'
                  AND date_trunc('microseconds',window_from)=window_from
                  AND date_trunc('microseconds',window_to)=window_to),
 CONSTRAINT t28_export_bytes CHECK(octet_length(content)=byte_length
                  AND encode(sha256(content),'hex')=sha256),
 CONSTRAINT t28_export_request UNIQUE(company_id,facility_id,requester,request_key),
 CONSTRAINT t28_export_scope_identity UNIQUE(id,company_id,facility_id)
);

CREATE TABLE public.hidra_audit_t28_access_receipt (
 id uuid PRIMARY KEY,
 company_id varchar(96) NOT NULL CHECK(company_id=btrim(company_id) AND company_id<>''),
 facility_id varchar(120) NOT NULL CHECK(facility_id=btrim(facility_id) AND facility_id<>''),
 actor_id varchar(120) NOT NULL CHECK(actor_id=btrim(actor_id) AND actor_id<>''),
 action varchar(20) NOT NULL CHECK(action IN ('CREATE','SEARCH','MANIFEST','DOWNLOAD')),
 outcome varchar(20) NOT NULL CHECK(outcome IN ('ALLOW','DENY','INTEGRITY_FAILURE')),
 export_id uuid,
 locator_digest char(64) NOT NULL CHECK(locator_digest ~ '^[a-f0-9]{64}$'),
 accessed_at timestamptz NOT NULL,
 CONSTRAINT t28_access_export_scope FOREIGN KEY(export_id,company_id,facility_id)
  REFERENCES public.hidra_audit_t28_export(id,company_id,facility_id) ON DELETE RESTRICT,
 CONSTRAINT t28_receipt_outcome CHECK(
  (action='DOWNLOAD' AND outcome='ALLOW' AND export_id IS NOT NULL)
  OR (action='DOWNLOAD' AND outcome='INTEGRITY_FAILURE' AND export_id IS NOT NULL)
  OR (action='DOWNLOAD' AND outcome='DENY' AND export_id IS NULL)
  OR (action='CREATE' AND outcome='ALLOW' AND export_id IS NOT NULL)
  OR (action IN ('SEARCH','MANIFEST') AND outcome='ALLOW' AND export_id IS NULL))
);
CREATE INDEX t28_access_actor_time ON public.hidra_audit_t28_access_receipt(actor_id,accessed_at DESC);

-- Match the real immutable event; never parse payload JSON or trust a caller-supplied event label.
CREATE FUNCTION public.t28_audit_bind_event_proof() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE real_event record;
BEGIN
 SELECT e.*,cat.code AS type_code INTO real_event
  FROM public.hidra_audit_event e
  JOIN public.hidra_audit_catalog_entry cat ON cat.id=e.event_type_id
  WHERE e.id=NEW.event_id FOR SHARE OF e;
 IF NOT FOUND OR real_event.type_code IS DISTINCT FROM 'RISK_ASSESSMENT_APPROVED'
    OR real_event.source_module IS DISTINCT FROM 'risk'
    OR real_event.target_module IS DISTINCT FROM 'risk'
    OR real_event.target_type IS DISTINCT FROM 'RISK_ASSESSMENT'
    OR real_event.action_code IS DISTINCT FROM 'APPROVE_RISK_ASSESSMENT'
    OR real_event.operation IS DISTINCT FROM 'APPROVE'
    OR real_event.workflow_action_id IS DISTINCT FROM NEW.workflow_action_id
    OR real_event.occurred_at IS DISTINCT FROM NEW.occurred_at THEN
    RAISE EXCEPTION 'T28 event/source/action binding failed' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER t28_scope_binding_proof BEFORE INSERT ON public.hidra_audit_t28_event_scope
 FOR EACH ROW EXECUTE FUNCTION public.t28_audit_bind_event_proof();

CREATE FUNCTION public.t28_audit_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'T28 Audit evidence is immutable' USING ERRCODE='23514';
END $$;
CREATE TRIGGER t28_scope_no_update BEFORE UPDATE OR DELETE ON public.hidra_audit_t28_event_scope
 FOR EACH ROW EXECUTE FUNCTION public.t28_audit_immutable();
CREATE TRIGGER t28_scope_no_truncate BEFORE TRUNCATE ON public.hidra_audit_t28_event_scope
 FOR EACH STATEMENT EXECUTE FUNCTION public.t28_audit_immutable();
CREATE TRIGGER t28_export_no_update BEFORE UPDATE OR DELETE ON public.hidra_audit_t28_export
 FOR EACH ROW EXECUTE FUNCTION public.t28_audit_immutable();
CREATE TRIGGER t28_export_no_truncate BEFORE TRUNCATE ON public.hidra_audit_t28_export
 FOR EACH STATEMENT EXECUTE FUNCTION public.t28_audit_immutable();
CREATE TRIGGER t28_receipt_no_update BEFORE UPDATE OR DELETE ON public.hidra_audit_t28_access_receipt
 FOR EACH ROW EXECUTE FUNCTION public.t28_audit_immutable();
CREATE TRIGGER t28_receipt_no_truncate BEFORE TRUNCATE ON public.hidra_audit_t28_access_receipt
 FOR EACH STATEMENT EXECUTE FUNCTION public.t28_audit_immutable();
