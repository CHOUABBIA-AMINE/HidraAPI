-- HMR-014: IntegrationJobRun semantic remediation.
-- Existing migrations remain immutable.

CREATE TABLE hidra_integration_job_run_sequence (
    job_definition_id varchar(80) PRIMARY KEY,
    last_run_number bigint NOT NULL,
    CONSTRAINT fk_hmr014_job_run_sequence_definition
        FOREIGN KEY (job_definition_id)
        REFERENCES hidra_integration_job_definition (id)
        ON DELETE CASCADE,
    CONSTRAINT ck_hmr014_job_run_sequence_non_negative
        CHECK (last_run_number >= 0)
);

INSERT INTO hidra_integration_job_run_sequence (job_definition_id, last_run_number)
SELECT job_definition_id, MAX(run_number)
FROM hidra_integration_job_run
GROUP BY job_definition_id;

CREATE UNIQUE INDEX uk_hmr014_integration_job_run_number
    ON hidra_integration_job_run (job_definition_id, run_number);

ALTER TABLE hidra_integration_job_run
    ADD CONSTRAINT ck_hmr014_integration_job_run_positive_number
    CHECK (run_number > 0) NOT VALID;

ALTER TABLE hidra_integration_job_run
    ADD CONSTRAINT ck_hmr014_integration_job_run_non_negative_counts
    CHECK (
        received_count >= 0
        AND mapped_count >= 0
        AND accepted_count >= 0
        AND rejected_count >= 0
        AND dead_letter_count >= 0
        AND retry_count >= 0
    ) NOT VALID;

ALTER TABLE hidra_integration_job_run
    ADD CONSTRAINT ck_hmr014_integration_job_run_outcome_counts
    CHECK (
        accepted_count::numeric
        + rejected_count::numeric
        + dead_letter_count::numeric
        <= received_count::numeric
    ) NOT VALID;

ALTER TABLE hidra_integration_job_run
    ADD CONSTRAINT ck_hmr014_integration_job_run_completion_order
    CHECK (completed_at IS NULL OR completed_at >= started_at) NOT VALID;

ALTER TABLE hidra_integration_job_run
    VALIDATE CONSTRAINT ck_hmr014_integration_job_run_positive_number;
ALTER TABLE hidra_integration_job_run
    VALIDATE CONSTRAINT ck_hmr014_integration_job_run_non_negative_counts;
ALTER TABLE hidra_integration_job_run
    VALIDATE CONSTRAINT ck_hmr014_integration_job_run_outcome_counts;
ALTER TABLE hidra_integration_job_run
    VALIDATE CONSTRAINT ck_hmr014_integration_job_run_completion_order;

CREATE OR REPLACE FUNCTION hmr014_prepare_job_run_insert()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
    job_active boolean;
    manual_allowed boolean;
    connector_id varchar(80);
    mapping_id varchar(80);
    job_target_module varchar(80);
    job_type_code varchar(120);
    next_run_number bigint;
BEGIN
    SELECT
        jd.active,
        jd.manual_run_allowed,
        jd.connector_instance_id,
        jd.mapping_profile_id,
        jd.target_module,
        upper(c.code)
    INTO
        job_active,
        manual_allowed,
        connector_id,
        mapping_id,
        job_target_module,
        job_type_code
    FROM hidra_integration_job_definition jd
    JOIN hidra_integration_catalog_entry c
      ON c.id = jd.job_type_id
     AND c.catalog_name = 'JOB_TYPE'
     AND c.active = TRUE
    WHERE jd.id = NEW.job_definition_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION
            'IntegrationJobRun requires an existing job definition with an ACTIVE JOB_TYPE entry: %',
            NEW.job_definition_id
            USING ERRCODE = '23514';
    END IF;

    IF job_active <> TRUE THEN
        RAISE EXCEPTION
            'IntegrationJobRun cannot start from an inactive job definition: %',
            NEW.job_definition_id
            USING ERRCODE = '23514';
    END IF;

    IF NEW.trigger_type = 'MANUAL' THEN
        IF manual_allowed <> TRUE THEN
            RAISE EXCEPTION
                'Integration job definition does not allow MANUAL execution'
                USING ERRCODE = '23514';
        END IF;
        IF NEW.triggered_by_actor_id IS NULL OR btrim(NEW.triggered_by_actor_id) = '' THEN
            RAISE EXCEPTION
                'MANUAL IntegrationJobRun requires triggered_by_actor_id'
                USING ERRCODE = '23514';
        END IF;
    ELSIF NOT EXISTS (
        SELECT 1
        FROM hidra_integration_connector_instance ci
        WHERE ci.id = connector_id
          AND ci.active = TRUE
    ) THEN
        RAISE EXCEPTION
            'Automated IntegrationJobRun requires an active ConnectorInstance'
            USING ERRCODE = '23514';
    END IF;

    IF job_type_code IN ('IMPORT', 'SYNC') THEN
        IF mapping_id IS NULL THEN
            RAISE EXCEPTION
                'IMPORT/SYNC requires a mapping profile in the current repository baseline'
                USING ERRCODE = '23514';
        END IF;

        IF NOT EXISTS (
            SELECT 1
            FROM hidra_integration_mapping_profile mp
            WHERE mp.id = mapping_id
              AND (
                  NEW.trigger_type = 'MANUAL'
                  OR mp.status = 'ACTIVE'
              )
              AND (
                  job_target_module IS NULL
                  OR mp.target_module = job_target_module
              )
        ) THEN
            RAISE EXCEPTION
                'IntegrationJobRun mapping profile is missing, ineligible, or targets another module'
                USING ERRCODE = '23514';
        END IF;
    END IF;

    INSERT INTO hidra_integration_job_run_sequence (job_definition_id, last_run_number)
    VALUES (NEW.job_definition_id, 1)
    ON CONFLICT (job_definition_id)
    DO UPDATE SET last_run_number =
        hidra_integration_job_run_sequence.last_run_number + 1
    RETURNING last_run_number INTO next_run_number;

    NEW.run_number := next_run_number;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr014_integration_job_run_insert
    BEFORE INSERT
    ON hidra_integration_job_run
    FOR EACH ROW
    EXECUTE FUNCTION hmr014_prepare_job_run_insert();

CREATE OR REPLACE FUNCTION hmr014_validate_job_run_update()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF NEW.job_definition_id <> OLD.job_definition_id THEN
        RAISE EXCEPTION
            'IntegrationJobRun job_definition_id is immutable'
            USING ERRCODE = '23514';
    END IF;

    IF NEW.run_number <> OLD.run_number THEN
        RAISE EXCEPTION
            'IntegrationJobRun run_number is immutable after allocation'
            USING ERRCODE = '23514';
    END IF;

    IF OLD.status IN ('COMPLETED', 'COMPLETED_WITH_ERRORS', 'FAILED', 'CANCELLED')
       AND NEW.status <> OLD.status THEN
        RAISE EXCEPTION
            'Terminal IntegrationJobRun status is immutable'
            USING ERRCODE = '23514';
    END IF;

    IF OLD.status = 'RUNNING' AND NEW.status = 'PENDING' THEN
        RAISE EXCEPTION
            'IntegrationJobRun status cannot move from RUNNING back to PENDING'
            USING ERRCODE = '23514';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_hmr014_integration_job_run_update
    BEFORE UPDATE OF job_definition_id, run_number, status
    ON hidra_integration_job_run
    FOR EACH ROW
    EXECUTE FUNCTION hmr014_validate_job_run_update();
