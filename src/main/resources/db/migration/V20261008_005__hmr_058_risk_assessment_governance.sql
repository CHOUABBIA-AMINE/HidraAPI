-- HMR-058: governed Risk aggregate, own-module integrity only; no invented legacy evidence.
LOCK TABLE hidra_risk_assessment, hidra_risk_assessment_scope, hidra_risk_evidence_link,
    hidra_risk_catalog_entry, hidra_risk_matrix, hidra_risk_matrix_cell,
    hidra_risk_control, hidra_risk_treatment_plan IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a WHERE NOT EXISTS
        (SELECT 1 FROM hidra_risk_assessment_scope s WHERE s.risk_assessment_id=a.id))
        OR EXISTS (SELECT 1 FROM hidra_risk_assessment_scope WHERE scope_type NOT IN
            ('ORGANIZATION_UNIT','PIPELINE_SYSTEM','PIPELINE','FACILITY','EQUIPMENT') OR scope_id !~ '[^[:space:]]')
        OR EXISTS (SELECT 1 FROM hidra_risk_assessment WHERE inherent_likelihood_id IS NOT NULL
            OR inherent_consequence_id IS NOT NULL OR inherent_score IS NOT NULL OR inherent_rating_id IS NOT NULL
            OR residual_likelihood_id IS NOT NULL OR residual_consequence_id IS NOT NULL OR residual_score IS NOT NULL
            OR residual_rating_id IS NOT NULL OR status IN ('APPROVED','ACTIVE'))
    THEN RAISE EXCEPTION 'HMR-058: legacy scope/scoring/approval requires explicit owner reconciliation; no provenance is inferred'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment_scope ADD CONSTRAINT ck_risk_assessment_scope_identity
CHECK(scope_type IN ('ORGANIZATION_UNIT','PIPELINE_SYSTEM','PIPELINE','FACILITY','EQUIPMENT') AND scope_id ~ '[^[:space:]]');
CREATE TABLE hidra_risk_assessment_scoring (
    assessment_id varchar(80) PRIMARY KEY REFERENCES hidra_risk_assessment(id),
    inherent_cell_id varchar(80) NOT NULL REFERENCES hidra_risk_matrix_cell(id),
    inherent_matrix_id varchar(80) NOT NULL REFERENCES hidra_risk_matrix(id),
    inherent_matrix_version varchar(80) NOT NULL CHECK(inherent_matrix_version ~ '[^[:space:]]'),
    residual_cell_id varchar(80) REFERENCES hidra_risk_matrix_cell(id),
    residual_matrix_id varchar(80) REFERENCES hidra_risk_matrix(id),
    residual_matrix_version varchar(80),
    residual_context_type varchar(80),
    residual_context_id varchar(80),
    CONSTRAINT ck_risk_residual_context_complete CHECK (
        (residual_cell_id IS NULL AND residual_matrix_id IS NULL AND residual_matrix_version IS NULL
            AND residual_context_type IS NULL AND residual_context_id IS NULL)
        OR (residual_cell_id IS NOT NULL AND residual_matrix_id IS NOT NULL
            AND residual_matrix_version IS NOT NULL AND residual_matrix_version ~ '[^[:space:]]'
            AND residual_context_type IS NOT NULL AND residual_context_type IN ('CONTROL','TREATMENT_PLAN')
            AND residual_context_id IS NOT NULL AND residual_context_id ~ '[^[:space:]]'))
);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.assessment_type_id
            WHERE a.assessment_type_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_ASSESSMENT_TYPE'))
    THEN RAISE EXCEPTION 'HMR-058: legacy assessment_type_id has invalid catalog family'; END IF;
END $$;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.methodology_id
            WHERE a.methodology_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_METHODLOGY'))
    THEN RAISE EXCEPTION 'HMR-058: legacy methodology_id has invalid catalog family'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment ADD CONSTRAINT fk_risk_assessment_inherent_likelihood_id FOREIGN KEY (inherent_likelihood_id) REFERENCES hidra_risk_catalog_entry(id);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.inherent_likelihood_id
            WHERE a.inherent_likelihood_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_LIKELIHOOD_LEVEL'))
    THEN RAISE EXCEPTION 'HMR-058: legacy inherent_likelihood_id has invalid catalog family'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment ADD CONSTRAINT fk_risk_assessment_residual_likelihood_id FOREIGN KEY (residual_likelihood_id) REFERENCES hidra_risk_catalog_entry(id);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.residual_likelihood_id
            WHERE a.residual_likelihood_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_LIKELIHOOD_LEVEL'))
    THEN RAISE EXCEPTION 'HMR-058: legacy residual_likelihood_id has invalid catalog family'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment ADD CONSTRAINT fk_risk_assessment_inherent_consequence_id FOREIGN KEY (inherent_consequence_id) REFERENCES hidra_risk_catalog_entry(id);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.inherent_consequence_id
            WHERE a.inherent_consequence_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_CONSEQUENCE_LEVEL'))
    THEN RAISE EXCEPTION 'HMR-058: legacy inherent_consequence_id has invalid catalog family'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment ADD CONSTRAINT fk_risk_assessment_residual_consequence_id FOREIGN KEY (residual_consequence_id) REFERENCES hidra_risk_catalog_entry(id);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.residual_consequence_id
            WHERE a.residual_consequence_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_CONSEQUENCE_LEVEL'))
    THEN RAISE EXCEPTION 'HMR-058: legacy residual_consequence_id has invalid catalog family'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment ADD CONSTRAINT fk_risk_assessment_inherent_rating_id FOREIGN KEY (inherent_rating_id) REFERENCES hidra_risk_catalog_entry(id);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.inherent_rating_id
            WHERE a.inherent_rating_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_RATING'))
    THEN RAISE EXCEPTION 'HMR-058: legacy inherent_rating_id has invalid catalog family'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment ADD CONSTRAINT fk_risk_assessment_residual_rating_id FOREIGN KEY (residual_rating_id) REFERENCES hidra_risk_catalog_entry(id);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.residual_rating_id
            WHERE a.residual_rating_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_RATING'))
    THEN RAISE EXCEPTION 'HMR-058: legacy residual_rating_id has invalid catalog family'; END IF;
END $$;
ALTER TABLE hidra_risk_assessment ADD CONSTRAINT fk_risk_assessment_confidence_level_id FOREIGN KEY (confidence_level_id) REFERENCES hidra_risk_catalog_entry(id);
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_assessment a LEFT JOIN hidra_risk_catalog_entry c ON c.id=a.confidence_level_id
            WHERE a.confidence_level_id IS NOT NULL AND (c.id IS NULL OR c.catalog_name<>'RISK_CONFIDENCE_LEVEL'))
    THEN RAISE EXCEPTION 'HMR-058: legacy confidence_level_id has invalid catalog family'; END IF;
END $$;
CREATE FUNCTION hidra_risk_assessment_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE col text; family text; ref text; previous text; entry hidra_risk_catalog_entry%ROWTYPE;
BEGIN
    IF TG_OP IN ('UPDATE','DELETE') AND OLD.status IN ('APPROVED','ACTIVE') THEN
        IF TG_OP='DELETE' OR NEW IS DISTINCT FROM OLD THEN
            RAISE EXCEPTION 'Approved Risk assessment is immutable; create a new review/revision';
        END IF;
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    IF NEW.valid_from>NEW.valid_to THEN RAISE EXCEPTION 'Assessment validity interval is reversed'; END IF;
    FOR col,family IN SELECT * FROM (VALUES
        ('assessment_type_id','RISK_ASSESSMENT_TYPE'),
        ('methodology_id','RISK_METHODLOGY'),
        ('inherent_likelihood_id','RISK_LIKELIHOOD_LEVEL'),
        ('residual_likelihood_id','RISK_LIKELIHOOD_LEVEL'),
        ('inherent_consequence_id','RISK_CONSEQUENCE_LEVEL'),
        ('residual_consequence_id','RISK_CONSEQUENCE_LEVEL'),
        ('inherent_rating_id','RISK_RATING'),
        ('residual_rating_id','RISK_RATING'),
        ('confidence_level_id','RISK_CONFIDENCE_LEVEL')) AS families(col,family) LOOP
        ref=to_jsonb(NEW)->>col;
        previous=CASE WHEN TG_OP='UPDATE' THEN to_jsonb(OLD)->>col ELSE NULL END;
        IF ref IS NOT NULL THEN
            SELECT * INTO entry FROM hidra_risk_catalog_entry WHERE id=ref FOR SHARE;
            IF NOT FOUND OR entry.catalog_name<>family OR (ref IS DISTINCT FROM previous AND NOT entry.active) THEN
                RAISE EXCEPTION 'Invalid/ineligible Risk assessment catalog %',family;
            END IF;
        END IF;
    END LOOP;
    IF NEW.status IN ('APPROVED','ACTIVE') THEN
        IF NEW.reviewed_by_actor_id IS NULL OR NEW.reviewed_by_actor_id !~ '[^[:space:]]'
            OR NEW.reviewed_by_display_name_snapshot IS NULL OR NEW.reviewed_by_display_name_snapshot !~ '[^[:space:]]'
            OR NEW.approved_by_actor_id IS NULL OR NEW.approved_by_actor_id !~ '[^[:space:]]'
            OR NEW.approved_by_display_name_snapshot IS NULL OR NEW.approved_by_display_name_snapshot !~ '[^[:space:]]'
            OR NEW.approved_at IS NULL OR NEW.workflow_reference_id IS NULL OR NEW.workflow_reference_id !~ '[^[:space:]]'
            OR NEW.audit_reference_id IS NULL OR NEW.audit_reference_id !~ '[^[:space:]]'
            OR NOT EXISTS(SELECT 1 FROM hidra_risk_evidence_link WHERE risk_assessment_id=NEW.id)
        THEN RAISE EXCEPTION 'Approved assessment requires actual approval context and evidence'; END IF;
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_assessment_guard BEFORE INSERT OR UPDATE OR DELETE ON hidra_risk_assessment
FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_guard();

-- Lock both old and new parents deterministically, excluding association changes after approval.
CREATE FUNCTION hidra_risk_assessment_child_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE old_parent text; new_parent text; parent record;
BEGIN
    IF TG_OP<>'INSERT' THEN old_parent=COALESCE(to_jsonb(OLD)->>'risk_assessment_id',to_jsonb(OLD)->>'assessment_id'); END IF;
    IF TG_OP<>'DELETE' THEN new_parent=COALESCE(to_jsonb(NEW)->>'risk_assessment_id',to_jsonb(NEW)->>'assessment_id'); END IF;
    FOR parent IN SELECT id,status FROM hidra_risk_assessment WHERE id IN (old_parent,new_parent) ORDER BY id FOR UPDATE LOOP
        IF parent.status IN ('APPROVED','ACTIVE') THEN RAISE EXCEPTION 'Approved assessment associations are immutable'; END IF;
    END LOOP;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_scope_freeze BEFORE INSERT OR UPDATE OR DELETE ON hidra_risk_assessment_scope
FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_child_guard();
CREATE TRIGGER tr_risk_evidence_freeze BEFORE INSERT OR UPDATE OR DELETE ON hidra_risk_evidence_link
FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_child_guard();
CREATE TRIGGER tr_risk_scoring_freeze BEFORE INSERT OR UPDATE OR DELETE ON hidra_risk_assessment_scoring
FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_child_guard();

CREATE FUNCTION hidra_risk_assessment_complete() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE old_parent text; new_parent text; pid text; a hidra_risk_assessment%ROWTYPE;
    provenance hidra_risk_assessment_scoring%ROWTYPE; cell hidra_risk_matrix_cell%ROWTYPE; version text;
BEGIN
    IF TG_OP<>'INSERT' THEN old_parent=COALESCE(to_jsonb(OLD)->>'risk_assessment_id',to_jsonb(OLD)->>'assessment_id',to_jsonb(OLD)->>'id'); END IF;
    IF TG_OP<>'DELETE' THEN new_parent=COALESCE(to_jsonb(NEW)->>'risk_assessment_id',to_jsonb(NEW)->>'assessment_id',to_jsonb(NEW)->>'id'); END IF;
    FOR pid IN SELECT DISTINCT p FROM unnest(ARRAY[old_parent,new_parent]) p WHERE p IS NOT NULL ORDER BY p LOOP
        SELECT * INTO a FROM hidra_risk_assessment WHERE id=pid FOR UPDATE;
        IF NOT FOUND THEN CONTINUE; END IF;
        IF NOT EXISTS(SELECT 1 FROM hidra_risk_assessment_scope WHERE risk_assessment_id=pid) THEN
            RAISE EXCEPTION 'Assessment requires at least one scope at commit'; END IF;
        SELECT * INTO provenance FROM hidra_risk_assessment_scoring WHERE assessment_id=pid;
        IF NOT FOUND THEN
            IF a.inherent_likelihood_id IS NOT NULL OR a.inherent_consequence_id IS NOT NULL OR a.inherent_score IS NOT NULL
                OR a.inherent_rating_id IS NOT NULL OR a.residual_likelihood_id IS NOT NULL
                OR a.residual_consequence_id IS NOT NULL OR a.residual_score IS NOT NULL OR a.residual_rating_id IS NOT NULL
            THEN RAISE EXCEPTION 'Scoring requires explicit matrix-cell provenance'; END IF;
            CONTINUE;
        END IF;
        SELECT * INTO cell FROM hidra_risk_matrix_cell WHERE id=provenance.inherent_cell_id FOR SHARE;
        SELECT m.version INTO version FROM hidra_risk_matrix m WHERE id=provenance.inherent_matrix_id FOR SHARE;
        IF cell.risk_matrix_id IS DISTINCT FROM provenance.inherent_matrix_id OR version IS DISTINCT FROM provenance.inherent_matrix_version
            OR a.inherent_likelihood_id IS DISTINCT FROM cell.likelihood_level_id
            OR a.inherent_consequence_id IS DISTINCT FROM cell.consequence_level_id
            OR a.inherent_score IS DISTINCT FROM cell.score_value OR a.inherent_rating_id IS DISTINCT FROM cell.rating_id
        THEN RAISE EXCEPTION 'Inherent scoring tuple must match selected cell and matrix version'; END IF;
        IF provenance.residual_cell_id IS NULL THEN
            IF a.residual_likelihood_id IS NOT NULL OR a.residual_consequence_id IS NOT NULL OR a.residual_score IS NOT NULL OR a.residual_rating_id IS NOT NULL
            THEN RAISE EXCEPTION 'Residual tuple requires explicit residual provenance'; END IF;
        ELSE
            SELECT * INTO cell FROM hidra_risk_matrix_cell WHERE id=provenance.residual_cell_id FOR SHARE;
            SELECT m.version INTO version FROM hidra_risk_matrix m WHERE id=provenance.residual_matrix_id FOR SHARE;
            IF cell.risk_matrix_id IS DISTINCT FROM provenance.residual_matrix_id OR version IS DISTINCT FROM provenance.residual_matrix_version
                OR a.residual_likelihood_id IS DISTINCT FROM cell.likelihood_level_id
                OR a.residual_consequence_id IS DISTINCT FROM cell.consequence_level_id
                OR a.residual_score IS DISTINCT FROM cell.score_value OR a.residual_rating_id IS DISTINCT FROM cell.rating_id
            THEN RAISE EXCEPTION 'Residual scoring tuple must match selected cell and matrix version'; END IF;
            IF provenance.residual_context_type='CONTROL' THEN
                PERFORM id FROM hidra_risk_control WHERE id=provenance.residual_context_id AND risk_assessment_id=pid FOR SHARE;
            ELSE
                PERFORM id FROM hidra_risk_treatment_plan WHERE id=provenance.residual_context_id AND risk_assessment_id=pid FOR SHARE;
            END IF;
            IF NOT FOUND THEN RAISE EXCEPTION 'Residual context must belong to the same assessment'; END IF;
        END IF;
    END LOOP;
    RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER tr_risk_assessment_complete AFTER INSERT OR UPDATE ON hidra_risk_assessment
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_complete();
CREATE CONSTRAINT TRIGGER tr_risk_scope_complete AFTER INSERT OR UPDATE OR DELETE ON hidra_risk_assessment_scope
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_complete();
CREATE CONSTRAINT TRIGGER tr_risk_scoring_complete AFTER INSERT OR UPDATE OR DELETE ON hidra_risk_assessment_scoring
DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_complete();

CREATE FUNCTION hidra_risk_approved_matrix_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM hidra_risk_assessment_scoring s JOIN hidra_risk_assessment a ON a.id=s.assessment_id
        WHERE a.status IN ('APPROVED','ACTIVE') AND
            ((TG_TABLE_NAME='hidra_risk_matrix_cell' AND OLD.id IN(s.inherent_cell_id,s.residual_cell_id))
            OR (TG_TABLE_NAME='hidra_risk_matrix' AND OLD.id IN(s.inherent_matrix_id,s.residual_matrix_id)))) THEN
        IF TG_OP='DELETE' OR NEW IS DISTINCT FROM OLD THEN RAISE EXCEPTION 'Approved scoring matrix/cell provenance is immutable'; END IF;
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_approved_cell BEFORE UPDATE OR DELETE ON hidra_risk_matrix_cell
FOR EACH ROW EXECUTE FUNCTION hidra_risk_approved_matrix_guard();
CREATE TRIGGER tr_risk_approved_matrix BEFORE UPDATE OR DELETE ON hidra_risk_matrix
FOR EACH ROW EXECUTE FUNCTION hidra_risk_approved_matrix_guard();

CREATE FUNCTION hidra_risk_residual_context_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF (TG_OP='DELETE' OR NEW.risk_assessment_id IS DISTINCT FROM OLD.risk_assessment_id OR NEW.id IS DISTINCT FROM OLD.id)
        AND EXISTS(SELECT 1 FROM hidra_risk_assessment_scoring WHERE residual_context_id=OLD.id
            AND residual_context_type=CASE WHEN TG_TABLE_NAME='hidra_risk_control' THEN 'CONTROL' ELSE 'TREATMENT_PLAN' END)
    THEN RAISE EXCEPTION 'Referenced residual context cannot be deleted/reparented'; END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_residual_control BEFORE UPDATE OR DELETE ON hidra_risk_control
FOR EACH ROW EXECUTE FUNCTION hidra_risk_residual_context_guard();
CREATE TRIGGER tr_risk_residual_treatment BEFORE UPDATE OR DELETE ON hidra_risk_treatment_plan
FOR EACH ROW EXECUTE FUNCTION hidra_risk_residual_context_guard();

CREATE FUNCTION hidra_risk_assessment_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='UPDATE' AND NEW.catalog_name IS DISTINCT FROM OLD.catalog_name AND EXISTS(
        SELECT 1 FROM hidra_risk_assessment WHERE OLD.id IN (
assessment_type_id, methodology_id, inherent_likelihood_id, residual_likelihood_id, inherent_consequence_id, residual_consequence_id, inherent_rating_id, residual_rating_id, confidence_level_id)) THEN RAISE EXCEPTION 'Referenced assessment catalog family cannot change'; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_risk_assessment_catalog BEFORE UPDATE ON hidra_risk_catalog_entry
FOR EACH ROW EXECUTE FUNCTION hidra_risk_assessment_catalog_guard();

-- TRUNCATE bypasses row triggers; preserve scope completeness and approved history.
CREATE FUNCTION hidra_risk_assessment_truncate_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM hidra_risk_assessment WHERE status IN ('APPROVED','ACTIVE'))
        OR (TG_TABLE_NAME IN ('hidra_risk_assessment_scope','hidra_risk_assessment_scoring')
            AND EXISTS(SELECT 1 FROM hidra_risk_assessment))
    THEN RAISE EXCEPTION 'TRUNCATE would destroy governed Risk aggregate evidence'; END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER tr_risk_assessment_truncate BEFORE TRUNCATE ON hidra_risk_assessment
FOR EACH STATEMENT EXECUTE FUNCTION hidra_risk_assessment_truncate_guard();
CREATE TRIGGER tr_risk_scope_truncate BEFORE TRUNCATE ON hidra_risk_assessment_scope
FOR EACH STATEMENT EXECUTE FUNCTION hidra_risk_assessment_truncate_guard();
CREATE TRIGGER tr_risk_scoring_truncate BEFORE TRUNCATE ON hidra_risk_assessment_scoring
FOR EACH STATEMENT EXECUTE FUNCTION hidra_risk_assessment_truncate_guard();
CREATE TRIGGER tr_risk_evidence_truncate BEFORE TRUNCATE ON hidra_risk_evidence_link
FOR EACH STATEMENT EXECUTE FUNCTION hidra_risk_assessment_truncate_guard();

CREATE FUNCTION hidra_risk_residual_context_truncate_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM hidra_risk_assessment_scoring WHERE residual_context_type=
        CASE WHEN TG_TABLE_NAME='hidra_risk_control' THEN 'CONTROL' ELSE 'TREATMENT_PLAN' END)
    THEN RAISE EXCEPTION 'TRUNCATE would destroy selected residual context'; END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER tr_risk_control_truncate BEFORE TRUNCATE ON hidra_risk_control
FOR EACH STATEMENT EXECUTE FUNCTION hidra_risk_residual_context_truncate_guard();
CREATE TRIGGER tr_risk_treatment_truncate BEFORE TRUNCATE ON hidra_risk_treatment_plan
FOR EACH STATEMENT EXECUTE FUNCTION hidra_risk_residual_context_truncate_guard();
