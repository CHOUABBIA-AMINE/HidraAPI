-- HMR-071: fail closed for invalid local evidence and partial resolver provenance.
ALTER TABLE hidra_integration_dead_letter_record ADD CONSTRAINT hmr071_run_fk FOREIGN KEY(job_run_id) REFERENCES hidra_integration_job_run(id);
ALTER TABLE hidra_integration_dead_letter_record ADD CONSTRAINT hmr071_message_fk FOREIGN KEY(exchange_message_id) REFERENCES hidra_integration_exchange_message(id);
ALTER TABLE hidra_integration_dead_letter_record ADD CONSTRAINT hmr071_inbound_fk FOREIGN KEY(inbound_record_id) REFERENCES hidra_integration_inbound_record(id);
ALTER TABLE hidra_integration_dead_letter_record ADD CONSTRAINT hmr071_outbound_fk FOREIGN KEY(outbound_record_id) REFERENCES hidra_integration_outbound_record(id);
ALTER TABLE hidra_integration_dead_letter_record ADD CONSTRAINT hmr071_failure_required CHECK(length(btrim(failure_stage))>0 AND length(btrim(reason_code))>0 AND length(btrim(reason_message))>0);
ALTER TABLE hidra_integration_dead_letter_record ADD CONSTRAINT hmr071_manual_trio CHECK(
    (resolved_by_actor_id IS NULL AND resolved_at IS NULL AND resolution_comment IS NULL)
    OR (resolved_by_actor_id IS NOT NULL AND length(btrim(resolved_by_actor_id))>0 AND resolved_at IS NOT NULL
        AND resolution_comment IS NOT NULL AND length(btrim(resolution_comment))>0));
CREATE FUNCTION hmr071_preserve_manual_resolution() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF OLD.resolved_by_actor_id IS NOT NULL AND
        ROW(OLD.resolved_by_actor_id,OLD.resolved_at,OLD.resolution_comment)
            IS DISTINCT FROM ROW(NEW.resolved_by_actor_id,NEW.resolved_at,NEW.resolution_comment) THEN
        RAISE EXCEPTION 'Recorded manual resolution cannot be replaced or removed' USING ERRCODE='23514';
    END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr071_manual_provenance BEFORE UPDATE ON hidra_integration_dead_letter_record FOR EACH ROW EXECUTE FUNCTION hmr071_preserve_manual_resolution();
