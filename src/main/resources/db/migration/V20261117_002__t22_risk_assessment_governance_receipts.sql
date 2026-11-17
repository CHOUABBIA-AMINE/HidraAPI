-- T22: new governed Risk FACILITY records only. Forward-only, no legacy reconciliation.
-- Definitions only: grants are Identity-owned and never auto-assigned.
INSERT INTO public.hidra_identity_permission
(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
('t22-risk-author','risk:assessments:author','risk','RISK_ASSESSMENT','author',true,'ACTIVE',now(),now()),
('t22-risk-review','risk:assessments:review','risk','RISK_ASSESSMENT','review',true,'ACTIVE',now(),now()),
('t22-risk-read','risk:assessments:read','risk','RISK_ASSESSMENT','read',true,'ACTIVE',now(),now())
ON CONFLICT (code) DO NOTHING;
CREATE TABLE hidra_risk_governed_scope (
    assessment_id varchar(80) PRIMARY KEY REFERENCES hidra_risk_assessment(id) ON DELETE RESTRICT,
    company_party_id varchar(80) NOT NULL CHECK (company_party_id ~ '[^[:space:]]'),
    facility_id varchar(80) NOT NULL CHECK (facility_id ~ '[^[:space:]]'),
    enrolled_at timestamptz NOT NULL DEFAULT now()
);
CREATE TABLE hidra_risk_governed_submission (
    assessment_id varchar(80) PRIMARY KEY REFERENCES hidra_risk_governed_scope(assessment_id) ON DELETE RESTRICT,
    inherent_cell_id varchar(80) NOT NULL REFERENCES hidra_risk_matrix_cell(id) ON DELETE RESTRICT,
    inherent_matrix_id varchar(80) NOT NULL REFERENCES hidra_risk_matrix(id) ON DELETE RESTRICT,
    inherent_matrix_version varchar(80) NOT NULL CHECK (inherent_matrix_version ~ '[^[:space:]]'),
    inherent_score numeric NOT NULL,
    inherent_rating_id varchar(80) NOT NULL REFERENCES hidra_risk_catalog_entry(id) ON DELETE RESTRICT,
    alarm_id varchar(80) NOT NULL CHECK (alarm_id ~ '[^[:space:]]'),
    alarm_code_snapshot varchar(80) NOT NULL CHECK (alarm_code_snapshot ~ '[^[:space:]]'),
    alarm_raised_at timestamptz NOT NULL,
    digest_format varchar(64) NOT NULL CHECK (digest_format='GZ2-RISK-SNAPSHOT-v1'),
    digest varchar(64) NOT NULL CHECK (digest ~ '^[0-9a-f]{64}$'),
    submitted_at timestamptz NOT NULL
);
CREATE TABLE hidra_risk_governed_approval (
    assessment_id varchar(80) PRIMARY KEY REFERENCES hidra_risk_governed_submission(assessment_id) ON DELETE RESTRICT,
    workflow_instance_id varchar(80) NOT NULL UNIQUE CHECK (workflow_instance_id ~ '[^[:space:]]'),
    review_task_id varchar(80) NOT NULL CHECK (review_task_id ~ '[^[:space:]]'),
    review_action_id varchar(80) NOT NULL UNIQUE CHECK (review_action_id ~ '[^[:space:]]'),
    reviewer_id varchar(80) NOT NULL CHECK (reviewer_id ~ '[^[:space:]]'),
    reviewed_at timestamptz NOT NULL,
    approval_task_id varchar(80) NOT NULL CHECK (approval_task_id ~ '[^[:space:]]'),
    approval_action_id varchar(80) NOT NULL UNIQUE CHECK (approval_action_id ~ '[^[:space:]]'),
    approver_id varchar(80) NOT NULL CHECK (approver_id ~ '[^[:space:]]'),
    approved_at timestamptz NOT NULL CHECK (approved_at >= reviewed_at),
    audit_receipt_id varchar(80) NOT NULL UNIQUE CHECK (audit_receipt_id ~ '[^[:space:]]'),
    submission_digest varchar(64) NOT NULL CHECK (submission_digest ~ '^[0-9a-f]{64}$'),
    CONSTRAINT ck_risk_governed_independence CHECK (reviewer_id <> approver_id)
);
CREATE FUNCTION hidra_risk_governed_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP <> 'INSERT' THEN
        RAISE EXCEPTION 'T22 immutable scope, submission or approval cannot be changed';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_governed_scope_immutable
    BEFORE UPDATE OR DELETE ON hidra_risk_governed_scope FOR EACH ROW EXECUTE FUNCTION hidra_risk_governed_immutable();
CREATE TRIGGER tr_risk_governed_submission_immutable
    BEFORE UPDATE OR DELETE ON hidra_risk_governed_submission FOR EACH ROW EXECUTE FUNCTION hidra_risk_governed_immutable();
CREATE TRIGGER tr_risk_governed_approval_immutable
    BEFORE UPDATE OR DELETE ON hidra_risk_governed_approval FOR EACH ROW EXECUTE FUNCTION hidra_risk_governed_immutable();

-- HMR-058 retains its original trigger and approval invariants. T22 adds a
-- narrower check for enrolled records without changing non-enrolled semantics.
CREATE FUNCTION hidra_risk_governed_parent_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE frozen hidra_risk_governed_submission%ROWTYPE;
receipt hidra_risk_governed_approval%ROWTYPE;
BEGIN
    IF TG_OP = 'DELETE' THEN RETURN OLD; END IF;
    IF EXISTS (SELECT 1 FROM hidra_risk_governed_scope WHERE assessment_id=NEW.id) THEN
        IF TG_OP = 'UPDATE' AND OLD.status IN ('UNDER_REVIEW','APPROVED','ACTIVE') AND NEW IS DISTINCT FROM OLD THEN
            IF NOT (OLD.status='UNDER_REVIEW' AND NEW.status='APPROVED') THEN
                RAISE EXCEPTION 'T22 submitted assessment is immutable';
            END IF;
        END IF;
        IF NEW.status IN ('UNDER_REVIEW','APPROVED','ACTIVE') THEN
            SELECT * INTO frozen FROM hidra_risk_governed_submission WHERE assessment_id=NEW.id;
            IF NOT FOUND OR NEW.inherent_score IS DISTINCT FROM frozen.inherent_score
                    OR NEW.inherent_rating_id IS DISTINCT FROM frozen.inherent_rating_id
                    OR NEW.residual_score IS NOT NULL OR NEW.residual_rating_id IS NOT NULL
                    OR NEW.assessed_by_actor_id IS NULL THEN
                RAISE EXCEPTION 'T22 governed status requires frozen inherent score and evidence';
            END IF;
        END IF;
        IF NEW.status IN ('APPROVED','ACTIVE') THEN
            SELECT * INTO receipt FROM hidra_risk_governed_approval WHERE assessment_id=NEW.id;
            IF NOT FOUND OR NEW.workflow_reference_id IS DISTINCT FROM receipt.workflow_instance_id
                    OR NEW.audit_reference_id IS DISTINCT FROM receipt.audit_receipt_id
                    OR NEW.approved_by_actor_id IS DISTINCT FROM receipt.approver_id
                    OR NEW.reviewed_by_actor_id IS DISTINCT FROM receipt.reviewer_id
                    OR receipt.submission_digest IS DISTINCT FROM frozen.digest
                    OR NEW.assessed_by_actor_id IN (receipt.reviewer_id,receipt.approver_id) THEN
                RAISE EXCEPTION 'T22 approval requires matching independent immutable receipt';
            END IF;
        END IF;
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_governed_parent_guard
    BEFORE UPDATE OR DELETE ON hidra_risk_assessment FOR EACH ROW EXECUTE FUNCTION hidra_risk_governed_parent_guard();

CREATE FUNCTION hidra_risk_governed_child_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE parent_id varchar(80);
BEGIN
    parent_id=COALESCE(to_jsonb(NEW)->>'risk_assessment_id',to_jsonb(NEW)->>'assessment_id',
                       to_jsonb(OLD)->>'risk_assessment_id',to_jsonb(OLD)->>'assessment_id');
    IF EXISTS (SELECT 1 FROM hidra_risk_governed_submission WHERE assessment_id=parent_id) THEN
        RAISE EXCEPTION 'T22 submitted evidence and scoring associations are immutable';
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_governed_evidence_freeze
    BEFORE INSERT OR UPDATE OR DELETE ON hidra_risk_evidence_link
    FOR EACH ROW EXECUTE FUNCTION hidra_risk_governed_child_guard();
CREATE TRIGGER tr_risk_governed_scoring_freeze
    BEFORE INSERT OR UPDATE OR DELETE ON hidra_risk_assessment_scoring
    FOR EACH ROW EXECUTE FUNCTION hidra_risk_governed_child_guard();
CREATE TRIGGER tr_risk_governed_scope_freeze
    BEFORE INSERT OR UPDATE OR DELETE ON hidra_risk_assessment_scope
    FOR EACH ROW EXECUTE FUNCTION hidra_risk_governed_child_guard();
