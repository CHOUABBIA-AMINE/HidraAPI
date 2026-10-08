-- HMR-077: preserve the existing assessment FK; never rewrite invalid legacy evidence.
LOCK TABLE hidra_risk_evidence_link IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_risk_evidence_link WHERE
        evidence_module IS NULL OR evidence_module !~ '[^[:space:]]' OR
        evidence_type IS NULL OR evidence_type !~ '[^[:space:]]' OR
        evidence_id IS NULL OR evidence_id !~ '[^[:space:]]') THEN
        RAISE EXCEPTION 'HMR-077: incomplete legacy evidence identity requires explicit reconciliation';
    END IF;
END $$;
ALTER TABLE hidra_risk_evidence_link ADD CONSTRAINT ck_risk_evidence_identity_complete
CHECK (evidence_module ~ '[^[:space:]]' AND evidence_type ~ '[^[:space:]]' AND evidence_id ~ '[^[:space:]]');
