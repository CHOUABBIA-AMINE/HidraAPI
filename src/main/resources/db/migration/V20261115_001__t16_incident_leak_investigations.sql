-- T16: independently reviewed Incident and suspected-leak investigations.
-- All existing Flyway migrations remain immutable. No field truth, calibration or production data.
-- Versioned, immutable command receipts are owned by each bounded context.

INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
 ('t16-incident-review','incident:incidents:review','incident','INCIDENT','review',true,'ACTIVE',now(),now()),
 ('t16-leak-case-review','leakdetection:cases:review','leakdetection','LEAK_CASE','review',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;
-- Catalog entries do not grant actors authority. Explicit independent company+asset grants still required.

CREATE TABLE public.hidra_incident_investigation (
 incident_id varchar(80) PRIMARY KEY REFERENCES public.hidra_incident(id) ON DELETE RESTRICT,
 state varchar(24) NOT NULL CHECK (state IN ('NEW','ASSIGNED','INVESTIGATING','SUBMITTED','APPROVED','CLOSED')),
 version bigint NOT NULL DEFAULT 0 CHECK (version>=0),
 investigator_id varchar(80),
 submitted_by_id varchar(80),
 reviewed_by_id varchar(80),
 resolution_type_id varchar(80) REFERENCES public.hidra_incident_catalog_entry(id) ON DELETE RESTRICT,
 resolution_summary text,
 corrective_required boolean NOT NULL DEFAULT false,
 preventive_required boolean NOT NULL DEFAULT false,
 has_evidence boolean NOT NULL DEFAULT false,
 has_action boolean NOT NULL DEFAULT false,
 updated_at timestamptz NOT NULL CHECK(isfinite(updated_at)),
 CHECK (state NOT IN ('ASSIGNED','INVESTIGATING','SUBMITTED','APPROVED','CLOSED')
        OR NULLIF(btrim(investigator_id),'') IS NOT NULL),
 CHECK (state NOT IN ('SUBMITTED','APPROVED','CLOSED')
        OR (NULLIF(btrim(submitted_by_id),'') IS NOT NULL
            AND NULLIF(btrim(resolution_summary),'') IS NOT NULL
            AND resolution_type_id IS NOT NULL AND has_evidence AND has_action)),
 CHECK (state NOT IN ('APPROVED','CLOSED')
        OR (NULLIF(btrim(reviewed_by_id),'') IS NOT NULL AND reviewed_by_id<>investigator_id))
);
CREATE INDEX ix_t16_incident_investigator
 ON public.hidra_incident_investigation(investigator_id,state);

CREATE TABLE public.hidra_incident_investigation_event (
 id varchar(80) PRIMARY KEY CHECK(NULLIF(btrim(id),'') IS NOT NULL),
 incident_id varchar(80) NOT NULL REFERENCES public.hidra_incident_investigation(incident_id) ON DELETE RESTRICT,
 version bigint NOT NULL CHECK(version>=1),
 command_key varchar(80) NOT NULL CHECK(NULLIF(btrim(command_key),'') IS NOT NULL),
 command_sha256 char(64) NOT NULL CHECK(command_sha256~'^[0-9a-f]{64}$'),
 operation varchar(20) NOT NULL CHECK(operation IN ('ASSIGN','EVIDENCE','ACTION','SUBMIT','REVIEW','CLOSE')),
 actor_id varchar(80) NOT NULL CHECK(NULLIF(btrim(actor_id),'') IS NOT NULL),
 reference_type varchar(80),
 reference_id varchar(80),
 evidence_direction varchar(40),
 detail text NOT NULL CHECK(NULLIF(btrim(detail),'') IS NOT NULL),
 state_before varchar(24) NOT NULL,
 state_after varchar(24) NOT NULL,
 recorded_at timestamptz NOT NULL CHECK(isfinite(recorded_at)),
 UNIQUE(incident_id,command_key),UNIQUE(incident_id,version)
);
CREATE INDEX ix_t16_incident_event_time
 ON public.hidra_incident_investigation_event(incident_id,recorded_at,id);

CREATE TABLE public.hidra_leak_detection_investigation (
 case_id varchar(80) PRIMARY KEY REFERENCES public.hidra_leak_detection_case(id) ON DELETE RESTRICT,
 candidate_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_leak_detection_candidate(id) ON DELETE RESTRICT,
 opening_request_key varchar(80) NOT NULL CHECK(NULLIF(btrim(opening_request_key),'') IS NOT NULL),
 opening_sha256 char(64) NOT NULL CHECK(opening_sha256~'^[0-9a-f]{64}$'),
 state varchar(30) NOT NULL CHECK (state IN
     ('NEW','ASSIGNED','INVESTIGATING','SUBMITTED','INSUFFICIENT_EVIDENCE','REVIEWED')),
 version bigint NOT NULL DEFAULT 0 CHECK(version>=0),
 investigator_id varchar(80),
 submitted_by_id varchar(80),
 reviewed_by_id varchar(80),
 disposition varchar(40) CHECK (disposition IN ('DISMISSED','VERIFIED_LEAK','INSUFFICIENT_EVIDENCE')),
 has_evidence boolean NOT NULL DEFAULT false,
 has_action boolean NOT NULL DEFAULT false,
 updated_at timestamptz NOT NULL CHECK(isfinite(updated_at)),
 CHECK (state NOT IN ('ASSIGNED','INVESTIGATING','SUBMITTED','INSUFFICIENT_EVIDENCE','REVIEWED')
       OR NULLIF(btrim(investigator_id),'') IS NOT NULL),
 CHECK (state NOT IN ('SUBMITTED','INSUFFICIENT_EVIDENCE','REVIEWED')
       OR (NULLIF(btrim(submitted_by_id),'') IS NOT NULL AND disposition IS NOT NULL AND has_evidence AND has_action)),
 CHECK (state NOT IN ('INSUFFICIENT_EVIDENCE','REVIEWED')
       OR (NULLIF(btrim(reviewed_by_id),'') IS NOT NULL AND reviewed_by_id<>investigator_id))
);
CREATE INDEX ix_t16_leak_investigator ON public.hidra_leak_detection_investigation(investigator_id,state);

CREATE TABLE public.hidra_leak_detection_investigation_event (
 id varchar(80) PRIMARY KEY CHECK(NULLIF(btrim(id),'') IS NOT NULL),
 case_id varchar(80) NOT NULL REFERENCES public.hidra_leak_detection_investigation(case_id) ON DELETE RESTRICT,
 version bigint NOT NULL CHECK(version>=0),
 command_key varchar(80) NOT NULL CHECK(NULLIF(btrim(command_key),'') IS NOT NULL),
 command_sha256 char(64) NOT NULL CHECK(command_sha256~'^[0-9a-f]{64}$'),
 operation varchar(20) NOT NULL CHECK(operation IN ('OPEN','ASSIGN','EVIDENCE','ACTION','SUBMIT','REVIEW')),
 actor_id varchar(80) NOT NULL CHECK(NULLIF(btrim(actor_id),'') IS NOT NULL),
 reference_type varchar(80),
 reference_id varchar(80),
 evidence_direction varchar(40),
 detail text NOT NULL CHECK(NULLIF(btrim(detail),'') IS NOT NULL),
 state_before varchar(30) NOT NULL,
 state_after varchar(30) NOT NULL,
 recorded_at timestamptz NOT NULL CHECK(isfinite(recorded_at)),
 UNIQUE(case_id,command_key),UNIQUE(case_id,version)
);
CREATE INDEX ix_t16_leak_event_time
 ON public.hidra_leak_detection_investigation_event(case_id,recorded_at,id);

-- A head may only represent its actual primary candidate. Do not create cross-owner FKs.
CREATE FUNCTION public.t16_leak_primary_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF NOT EXISTS (SELECT 1 FROM public.hidra_leak_detection_case c
     WHERE c.id=NEW.case_id AND c.primary_candidate_id=NEW.candidate_id) THEN
   RAISE EXCEPTION 'T16 primary Leak case/candidate correlation mismatch';
 END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER trg_t16_leak_primary BEFORE INSERT OR UPDATE
 ON public.hidra_leak_detection_investigation FOR EACH ROW
 EXECUTE FUNCTION public.t16_leak_primary_guard();

-- Exact event receipts and owner scope history are immutable, including TRUNCATE.
CREATE FUNCTION public.t16_investigation_event_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'T16 investigation evidence and decisions are append-only';
END $$;
CREATE TRIGGER trg_t16_incident_event_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_incident_investigation_event FOR EACH ROW
 EXECUTE FUNCTION public.t16_investigation_event_immutable();
CREATE TRIGGER trg_t16_incident_event_no_truncate BEFORE TRUNCATE
 ON public.hidra_incident_investigation_event FOR EACH STATEMENT
 EXECUTE FUNCTION public.t16_investigation_event_immutable();
CREATE TRIGGER trg_t16_leak_event_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_leak_detection_investigation_event FOR EACH ROW
 EXECUTE FUNCTION public.t16_investigation_event_immutable();
CREATE TRIGGER trg_t16_leak_event_no_truncate BEFORE TRUNCATE
 ON public.hidra_leak_detection_investigation_event FOR EACH STATEMENT
 EXECUTE FUNCTION public.t16_investigation_event_immutable();

-- A T16 governed parent must retain its final reviewer evidence at database commit;
-- existing HMR-090 closure policy and transaction triggers remain authoritative.
CREATE FUNCTION public.t16_incident_review_consistent() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE i public.hidra_incident%ROWTYPE; h public.hidra_incident_investigation%ROWTYPE;
BEGIN
 SELECT * INTO i FROM public.hidra_incident WHERE id=CASE WHEN TG_TABLE_NAME='hidra_incident' THEN NEW.id ELSE NEW.incident_id END;
 IF NOT FOUND THEN RETURN NULL; END IF;
 SELECT * INTO h FROM public.hidra_incident_investigation WHERE incident_id=i.id;
 IF NOT FOUND THEN RETURN NULL; END IF;
 IF i.status='CLOSED' AND (h.state<>'CLOSED' OR h.reviewed_by_id IS NULL) THEN
   RAISE EXCEPTION 'T16 governed Incident requires reviewed closure history';
 END IF;
 IF h.state IN ('APPROVED','CLOSED') AND i.resolved_at IS NULL THEN
   RAISE EXCEPTION 'T16 approved Incident must retain canonical resolution timestamp';
 END IF;
 RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER trg_t16_incident_review_parent
 AFTER INSERT OR UPDATE ON public.hidra_incident DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.t16_incident_review_consistent();
CREATE CONSTRAINT TRIGGER trg_t16_incident_review_head
 AFTER INSERT OR UPDATE ON public.hidra_incident_investigation DEFERRABLE INITIALLY DEFERRED
 FOR EACH ROW EXECUTE FUNCTION public.t16_incident_review_consistent();
