-- T17: governed, company-and-facility scoped HSE CAPA. Synthetic baseline only.
-- Existing HMR-082/096/097 migrations and legacy rows are preserved.
-- Permissions are catalogue entries ONLY; never a grant to a user/role.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t17-hse-capa-create','hse:capa:create','hse','HSE_CAPA','create',true,'ACTIVE',now(),now()),
('t17-hse-capa-read','hse:capa:read','hse','HSE_CAPA','read',true,'ACTIVE',now(),now()),
('t17-hse-capa-execute','hse:capa:execute','hse','HSE_CAPA','execute',true,'ACTIVE',now(),now()),
('t17-hse-capa-review','hse:capa:review','hse','HSE_CAPA','review',true,'ACTIVE',now(),now()),
('t17-hse-capa-close','hse:capa:close','hse','HSE_CAPA','close',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;

CREATE TABLE public.hidra_hse_t17_case_scope (
    case_id varchar(80) PRIMARY KEY REFERENCES public.hidra_hse_case(id) ON DELETE RESTRICT,
    company_id varchar(120) NOT NULL CHECK(length(btrim(company_id))>0),
    facility_id varchar(120) NOT NULL CHECK(length(btrim(facility_id))>0),
    incident_id varchar(80) NOT NULL CHECK(length(btrim(incident_id))>0),
    create_request_id varchar(80) NOT NULL CHECK(length(btrim(create_request_id))>0),
    create_digest char(64) NOT NULL CHECK(create_digest~'^[0-9a-f]{64}$'),
    created_by_actor_id varchar(80) NOT NULL CHECK(length(btrim(created_by_actor_id))>0),
    created_at timestamptz NOT NULL CHECK(isfinite(created_at)),
    UNIQUE(company_id,facility_id,create_request_id)
);
CREATE INDEX ix_t17_scope_company_asset
    ON public.hidra_hse_t17_case_scope(company_id,facility_id,case_id);

CREATE TABLE public.hidra_hse_t17_capa_state (
    capa_id varchar(80) PRIMARY KEY REFERENCES public.hidra_hse_capa(id) ON DELETE RESTRICT,
    case_id varchar(80) NOT NULL REFERENCES public.hidra_hse_t17_case_scope(case_id) ON DELETE RESTRICT,
    status varchar(40) NOT NULL CHECK(status IN ('PROPOSED','APPROVED','IN_PROGRESS','COMPLETED','VERIFIED','CLOSED')),
    revision bigint NOT NULL CHECK(revision>=0),
    proposer_id varchar(80) NOT NULL CHECK(length(btrim(proposer_id))>0),
    owner_id varchar(80) NOT NULL CHECK(length(btrim(owner_id))>0),
    verifier_id varchar(80),
    evidence_id varchar(80),
    updated_at timestamptz NOT NULL CHECK(isfinite(updated_at)),
    UNIQUE(case_id,capa_id),
    CHECK(status NOT IN ('VERIFIED','CLOSED') OR (verifier_id IS NOT NULL AND evidence_id IS NOT NULL))
);
CREATE INDEX ix_t17_capa_case ON public.hidra_hse_t17_capa_state(case_id,capa_id);

CREATE TABLE public.hidra_hse_t17_capa_event (
    id varchar(80) PRIMARY KEY,
    case_id varchar(80) NOT NULL REFERENCES public.hidra_hse_t17_case_scope(case_id) ON DELETE RESTRICT,
    capa_id varchar(80) REFERENCES public.hidra_hse_t17_capa_state(capa_id) ON DELETE RESTRICT,
    request_id varchar(80) NOT NULL CHECK(length(btrim(request_id))>0),
    command_sha256 char(64) NOT NULL CHECK(command_sha256~'^[0-9a-f]{64}$'),
    operation varchar(20) NOT NULL CHECK(operation IN ('PROPOSE','APPROVE','ASSIGN','EVIDENCE','COMPLETE','VERIFY','FINALIZE','CLOSE')),
    before_status varchar(40),
    after_status varchar(40) NOT NULL,
    revision bigint NOT NULL CHECK(revision>=0),
    actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id))>0),
    reason_text varchar(500) NOT NULL CHECK(length(btrim(reason_text))>0),
    reference_id varchar(80),
    occurred_at timestamptz NOT NULL CHECK(isfinite(occurred_at)),
    UNIQUE(case_id,request_id),
    UNIQUE(capa_id,revision),
    CONSTRAINT fk_t17_event_parent_pair FOREIGN KEY(case_id,capa_id)
        REFERENCES public.hidra_hse_t17_capa_state(case_id,capa_id) ON DELETE RESTRICT,
    CHECK((operation='CLOSE' AND capa_id IS NULL AND after_status='CLOSED') OR
          (operation<>'CLOSE' AND capa_id IS NOT NULL))
);
CREATE INDEX ix_t17_history ON public.hidra_hse_t17_capa_event(case_id,occurred_at,id);

CREATE TABLE public.hidra_hse_t17_capa_evidence (
    id varchar(80) PRIMARY KEY,
    capa_id varchar(80) NOT NULL REFERENCES public.hidra_hse_t17_capa_state(capa_id) ON DELETE RESTRICT,
    document_id varchar(80) NOT NULL,
    version_id varchar(80) NOT NULL,
    acceptance_id varchar(80) NOT NULL,
    checksum_sha256 char(64) NOT NULL CHECK(checksum_sha256~'^[0-9a-f]{64}$'),
    content_length bigint NOT NULL CHECK(content_length>=0),
    company_id varchar(120) NOT NULL,
    facility_id varchar(120) NOT NULL,
    actor_id varchar(80) NOT NULL,
    attached_at timestamptz NOT NULL CHECK(isfinite(attached_at)),
    UNIQUE(capa_id,document_id,version_id,acceptance_id),
    UNIQUE(id,capa_id)
);

CREATE TABLE public.hidra_hse_t17_capa_verification (
    id varchar(80) PRIMARY KEY,
    capa_id varchar(80) NOT NULL REFERENCES public.hidra_hse_t17_capa_state(capa_id) ON DELETE RESTRICT,
    evidence_id varchar(80) NOT NULL REFERENCES public.hidra_hse_t17_capa_evidence(id) ON DELETE RESTRICT,
    outcome varchar(20) NOT NULL CHECK(outcome IN ('EFFECTIVE','INEFFECTIVE','INDETERMINATE')),
    reason_text varchar(500) NOT NULL CHECK(length(btrim(reason_text))>0),
    reviewer_id varchar(80) NOT NULL CHECK(length(btrim(reviewer_id))>0),
    verified_at timestamptz NOT NULL CHECK(isfinite(verified_at)),
    UNIQUE(id,capa_id),
    CONSTRAINT fk_t17_verification_same_capa FOREIGN KEY(evidence_id,capa_id)
        REFERENCES public.hidra_hse_t17_capa_evidence(id,capa_id) ON DELETE RESTRICT
);
CREATE INDEX ix_t17_verification_capa ON public.hidra_hse_t17_capa_verification(capa_id,verified_at,id);

CREATE TABLE public.hidra_hse_t17_case_close_receipt (
    case_id varchar(80) PRIMARY KEY REFERENCES public.hidra_hse_t17_case_scope(case_id) ON DELETE RESTRICT,
    closure_id varchar(80) NOT NULL UNIQUE REFERENCES public.hidra_hse_closure(id) ON DELETE RESTRICT,
    workflow_action_id varchar(80) NOT NULL UNIQUE,
    workflow_instance_id varchar(80) NOT NULL,
    workflow_digest char(64) NOT NULL CHECK(workflow_digest~'^[0-9a-f]{64}$'),
    actor_id varchar(80) NOT NULL CHECK(length(btrim(actor_id))>0),
    closed_at timestamptz NOT NULL CHECK(isfinite(closed_at))
);

CREATE FUNCTION public.t17_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'T17 retained HSE evidence is append-only';
END $$;
CREATE TRIGGER t17_scope_immutable BEFORE UPDATE OR DELETE ON public.hidra_hse_t17_case_scope
 FOR EACH ROW EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_event_immutable BEFORE UPDATE OR DELETE ON public.hidra_hse_t17_capa_event
 FOR EACH ROW EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_evidence_immutable BEFORE UPDATE OR DELETE ON public.hidra_hse_t17_capa_evidence
 FOR EACH ROW EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_verification_immutable BEFORE UPDATE OR DELETE ON public.hidra_hse_t17_capa_verification
 FOR EACH ROW EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_close_immutable BEFORE UPDATE OR DELETE ON public.hidra_hse_t17_case_close_receipt
 FOR EACH ROW EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_scope_no_truncate BEFORE TRUNCATE ON public.hidra_hse_t17_case_scope
 FOR EACH STATEMENT EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_state_no_truncate BEFORE TRUNCATE ON public.hidra_hse_t17_capa_state
 FOR EACH STATEMENT EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_event_no_truncate BEFORE TRUNCATE ON public.hidra_hse_t17_capa_event
 FOR EACH STATEMENT EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_evidence_no_truncate BEFORE TRUNCATE ON public.hidra_hse_t17_capa_evidence
 FOR EACH STATEMENT EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_verification_no_truncate BEFORE TRUNCATE ON public.hidra_hse_t17_capa_verification
 FOR EACH STATEMENT EXECUTE FUNCTION public.t17_immutable();
CREATE TRIGGER t17_receipt_no_truncate BEFORE TRUNCATE ON public.hidra_hse_t17_case_close_receipt
 FOR EACH STATEMENT EXECUTE FUNCTION public.t17_immutable();

-- A direct legacy update, missing accepted evidence or forged terminal state
-- must fail even if bypassing application handlers. All checks run at commit.
CREATE FUNCTION public.t17_check_case_consistency() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE target_id varchar(80); parent record; cap record; head record;
BEGIN
 IF TG_TABLE_NAME='hidra_hse_case' THEN target_id:=NEW.id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_case_scope' THEN target_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_capa_event' THEN target_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_case_close_receipt' THEN target_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_t17_capa_state' THEN target_id:=NEW.case_id;
 ELSIF TG_TABLE_NAME='hidra_hse_capa' THEN target_id:=NEW.hse_case_id;
 ELSE RETURN NULL;
 END IF;
 IF target_id IS NULL OR NOT EXISTS(SELECT 1 FROM public.hidra_hse_t17_case_scope WHERE case_id=target_id)
 THEN RETURN NULL; END IF;
 SELECT * INTO parent FROM public.hidra_hse_case WHERE id=target_id;
 IF NOT FOUND THEN RAISE EXCEPTION 'T17 case parent missing'; END IF;
 IF parent.target_module<>'topology' OR parent.target_type_code<>'FACILITY'
    OR parent.target_id<>(SELECT facility_id FROM public.hidra_hse_t17_case_scope WHERE case_id=target_id)
    OR parent.source_type<>'INCIDENT_REFERENCE' OR parent.incident_reference_id<>
        (SELECT incident_id FROM public.hidra_hse_t17_case_scope WHERE case_id=target_id)
 THEN RAISE EXCEPTION 'T17 enrolled case lost exact owner identity'; END IF;
 IF EXISTS (SELECT 1 FROM public.hidra_hse_capa c WHERE c.hse_case_id=target_id
        AND NOT EXISTS(SELECT 1 FROM public.hidra_hse_t17_capa_state x
            WHERE x.capa_id=c.id AND x.case_id=target_id))
 THEN RAISE EXCEPTION 'T17 enrolled case cannot contain a legacy context-free CAPA'; END IF;
 FOR cap IN SELECT s.*,c.hse_case_id,c.status AS physical_status,c.owner_actor_id
    FROM public.hidra_hse_t17_capa_state s JOIN public.hidra_hse_capa c ON c.id=s.capa_id
    WHERE s.case_id=target_id LOOP
   IF cap.hse_case_id<>target_id OR cap.physical_status<>cap.status
      OR cap.owner_actor_id IS DISTINCT FROM cap.owner_id
      OR (cap.revision<>(SELECT count(*)-1 FROM public.hidra_hse_t17_capa_event e
           WHERE e.capa_id=cap.capa_id))
   THEN RAISE EXCEPTION 'T17 CAPA parent, head and event revision mismatch'; END IF;
   IF cap.status IN ('COMPLETED','VERIFIED','CLOSED') AND NOT EXISTS(
      SELECT 1 FROM public.hidra_hse_t17_capa_evidence e WHERE e.capa_id=cap.capa_id)
   THEN RAISE EXCEPTION 'T17 completion requires immutable accepted evidence'; END IF;
   IF cap.status IN ('VERIFIED','CLOSED') AND NOT EXISTS(
      SELECT 1 FROM public.hidra_hse_t17_capa_verification v
       WHERE v.capa_id=cap.capa_id AND v.outcome='EFFECTIVE'
         AND v.reviewer_id=cap.verifier_id AND v.evidence_id=cap.evidence_id
         AND v.reviewer_id<>cap.proposer_id AND v.reviewer_id<>cap.owner_id)
   THEN RAISE EXCEPTION 'T17 verified CAPA requires independent effective finding'; END IF;
 END LOOP;
 IF parent.status='CLOSED' THEN
   SELECT * INTO head FROM public.hidra_hse_t17_case_close_receipt r WHERE r.case_id=target_id;
   IF NOT FOUND OR NOT EXISTS(SELECT 1 FROM public.hidra_hse_closure c WHERE c.id=head.closure_id
        AND c.hse_case_id=target_id AND c.closed_by_actor_id=head.actor_id AND c.closed_at=head.closed_at)
      OR NOT EXISTS(SELECT 1 FROM public.hidra_hse_t17_capa_state WHERE case_id=target_id)
      OR EXISTS(SELECT 1 FROM public.hidra_hse_t17_capa_state WHERE case_id=target_id AND status<>'CLOSED')
   THEN RAISE EXCEPTION 'T17 case close requires closure receipt and terminal enrolled CAPAs'; END IF;
 ELSIF EXISTS(SELECT 1 FROM public.hidra_hse_t17_case_close_receipt WHERE case_id=target_id) THEN
   RAISE EXCEPTION 'T17 closed receipt attached to open case';
 END IF;
 RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER t17_case_consistency AFTER INSERT OR UPDATE ON public.hidra_hse_case
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t17_check_case_consistency();
CREATE CONSTRAINT TRIGGER t17_scope_consistency AFTER INSERT ON public.hidra_hse_t17_case_scope
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t17_check_case_consistency();
CREATE CONSTRAINT TRIGGER t17_state_consistency AFTER INSERT OR UPDATE ON public.hidra_hse_t17_capa_state
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t17_check_case_consistency();
CREATE CONSTRAINT TRIGGER t17_capa_consistency AFTER INSERT OR UPDATE ON public.hidra_hse_capa
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t17_check_case_consistency();
CREATE CONSTRAINT TRIGGER t17_event_consistency AFTER INSERT ON public.hidra_hse_t17_capa_event
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t17_check_case_consistency();
CREATE CONSTRAINT TRIGGER t17_receipt_consistency AFTER INSERT ON public.hidra_hse_t17_case_close_receipt
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION public.t17_check_case_consistency();

-- Prevent unscoped legacy JPA/direct updates of enrolled records from re-authoring identity.
CREATE FUNCTION public.t17_guard_enrolled_parent() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF EXISTS(SELECT 1 FROM public.hidra_hse_t17_case_scope WHERE case_id=OLD.id) THEN
   IF TG_OP='DELETE' THEN RAISE EXCEPTION 'T17 enrolled HSE case cannot be deleted'; END IF;
   IF (to_jsonb(NEW)-'status'-'closed_at'-'updated_at')
       IS DISTINCT FROM (to_jsonb(OLD)-'status'-'closed_at'-'updated_at')
       OR OLD.status IN ('CLOSED','CANCELLED')
       OR NEW.status<>'CLOSED' OR NEW.closed_at IS NULL OR OLD.closed_at IS NOT NULL
   THEN RAISE EXCEPTION 'T17 enrolled HSE case mutation requires authoritative close'; END IF;
 END IF;
 RETURN COALESCE(NEW,OLD);
END $$;
CREATE TRIGGER t17_parent_write_guard BEFORE UPDATE OR DELETE ON public.hidra_hse_case
 FOR EACH ROW EXECUTE FUNCTION public.t17_guard_enrolled_parent();

CREATE FUNCTION public.t17_guard_enrolled_capa() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE caseid varchar(80);
BEGIN
 caseid:=CASE WHEN TG_OP='DELETE' THEN OLD.hse_case_id ELSE NEW.hse_case_id END;
 IF EXISTS(SELECT 1 FROM public.hidra_hse_t17_case_scope WHERE case_id=caseid) THEN
   IF TG_OP='DELETE' THEN RAISE EXCEPTION 'T17 enrolled HSE CAPA cannot be deleted'; END IF;
   IF TG_OP='UPDATE' AND ((to_jsonb(NEW)-'status'-'completed_at'-'verified_by_actor_id'-'verified_at'-'updated_at')
       IS DISTINCT FROM (to_jsonb(OLD)-'status'-'completed_at'-'verified_by_actor_id'-'verified_at'-'updated_at')
       OR NEW.status NOT IN ('PROPOSED','APPROVED','IN_PROGRESS','COMPLETED','VERIFIED','CLOSED'))
   THEN RAISE EXCEPTION 'T17 CAPA immutable identity or illegal terminal edit'; END IF;
 END IF;
 RETURN COALESCE(NEW,OLD);
END $$;
CREATE TRIGGER t17_capa_write_guard BEFORE INSERT OR UPDATE OR DELETE ON public.hidra_hse_capa
 FOR EACH ROW EXECUTE FUNCTION public.t17_guard_enrolled_capa();
