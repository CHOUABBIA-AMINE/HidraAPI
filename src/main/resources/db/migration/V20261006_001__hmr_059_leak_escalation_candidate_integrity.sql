-- HMR-059: optional same-module candidate provenance; no cross-module FK.
-- Fail closed on orphan legacy rows; do not delete or rewrite evidence.
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM hidra_leak_detection_escalation_reference e
        LEFT JOIN hidra_leak_detection_candidate c ON c.id = e.candidate_id
        WHERE e.candidate_id IS NOT NULL AND c.id IS NULL
    ) THEN
        RAISE EXCEPTION 'HMR-059 preflight failed: orphan escalation candidate_id';
    END IF;
END $$;

ALTER TABLE hidra_leak_detection_escalation_reference
    ADD CONSTRAINT fk_hmr059_escalation_candidate
    FOREIGN KEY (candidate_id) REFERENCES hidra_leak_detection_candidate (id)
    ON DELETE RESTRICT NOT VALID;
ALTER TABLE hidra_leak_detection_escalation_reference
    VALIDATE CONSTRAINT fk_hmr059_escalation_candidate;
