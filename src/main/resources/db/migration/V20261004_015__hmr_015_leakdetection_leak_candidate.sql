-- HMR-015: LeakCandidate semantic remediation.
-- Existing migrations remain immutable.

ALTER TABLE hidra_leak_detection_candidate
    ADD CONSTRAINT fk_hmr015_leak_candidate_run
    FOREIGN KEY (run_id)
    REFERENCES hidra_leak_detection_run (id)
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE hidra_leak_detection_candidate
    ADD CONSTRAINT ck_hmr015_leak_candidate_topology_type
    CHECK (btrim(topology_asset_type) <> '')
    NOT VALID;

ALTER TABLE hidra_leak_detection_candidate
    ADD CONSTRAINT ck_hmr015_leak_candidate_derived_severity
    CHECK (
        (confidence_score >= 0.90 AND severity_level = 'CRITICAL')
        OR (confidence_score >= 0.75 AND confidence_score < 0.90 AND severity_level = 'HIGH')
        OR (confidence_score >= 0.50 AND confidence_score < 0.75 AND severity_level = 'MEDIUM')
        OR (confidence_score < 0.50 AND severity_level = 'LOW')
    )
    NOT VALID;

ALTER TABLE hidra_leak_detection_candidate
    VALIDATE CONSTRAINT fk_hmr015_leak_candidate_run;
ALTER TABLE hidra_leak_detection_candidate
    VALIDATE CONSTRAINT ck_hmr015_leak_candidate_topology_type;
ALTER TABLE hidra_leak_detection_candidate
    VALIDATE CONSTRAINT ck_hmr015_leak_candidate_derived_severity;

CREATE OR REPLACE FUNCTION hmr015_validate_leak_candidate_provenance()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    profile_status varchar(40);
    run_profile_id varchar(80);
    run_status varchar(40);
BEGIN
    SELECT p.status
      INTO profile_status
      FROM hidra_leak_detection_profile p
     WHERE p.id = NEW.profile_id;

    IF profile_status IS NULL OR profile_status <> 'ACTIVE' THEN
        RAISE EXCEPTION
            'LeakCandidate creation requires an ACTIVE LeakDetectionProfile: %',
            NEW.profile_id
            USING ERRCODE = '23514';
    END IF;

    IF NEW.run_id IS NOT NULL THEN
        SELECT r.profile_id, r.status
          INTO run_profile_id, run_status
          FROM hidra_leak_detection_run r
         WHERE r.id = NEW.run_id;

        IF run_profile_id IS NULL THEN
            RAISE EXCEPTION
                'LeakCandidate run must reference an existing LeakDetectionRun: %',
                NEW.run_id
                USING ERRCODE = '23514';
        END IF;

        IF run_profile_id <> NEW.profile_id THEN
            RAISE EXCEPTION
                'LeakCandidate run and profile provenance must match'
                USING ERRCODE = '23514';
        END IF;

        IF run_status NOT IN ('RUNNING', 'COMPLETED') THEN
            RAISE EXCEPTION
                'LeakCandidate run must be RUNNING or COMPLETED'
                USING ERRCODE = '23514';
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr015_leak_candidate_provenance
    BEFORE INSERT OR UPDATE OF profile_id, run_id
    ON hidra_leak_detection_candidate
    FOR EACH ROW
    EXECUTE FUNCTION hmr015_validate_leak_candidate_provenance();
