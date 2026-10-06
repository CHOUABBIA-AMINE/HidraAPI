-- HMR-060: create-only delivery evidence and selected-channel integrity.
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_notification_delivery_attempt a
               LEFT JOIN hidra_notification_message m ON m.id = a.message_id
               WHERE m.id IS NULL OR a.channel_id IS DISTINCT FROM m.channel_id
                  OR (a.attempt_status IN ('FAILED_PERMANENT', 'CANCELLED') AND a.next_retry_at IS NOT NULL)) THEN
        RAISE EXCEPTION 'HMR-060 preflight failed: inconsistent attempt channel or permanent retry'
            USING ERRCODE = '23514';
    END IF;
END $$;
ALTER TABLE hidra_notification_message
    ADD CONSTRAINT uk_hmr060_message_channel UNIQUE (id, channel_id);
ALTER TABLE hidra_notification_delivery_attempt
    ADD CONSTRAINT fk_hmr060_attempt_message_channel FOREIGN KEY (message_id, channel_id)
    REFERENCES hidra_notification_message (id, channel_id) ON DELETE RESTRICT,
    ADD CONSTRAINT ck_hmr060_no_permanent_retry CHECK (
        attempt_status NOT IN ('FAILED_PERMANENT', 'CANCELLED') OR next_retry_at IS NULL);

CREATE FUNCTION hmr060_reject_attempt_rewrite() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    RAISE EXCEPTION 'HMR-060 delivery attempts are append-only; update/delete/truncate denied'
        USING ERRCODE = '23514';
END $$;
CREATE TRIGGER trg_hmr060_attempt_append_only BEFORE UPDATE OR DELETE
    ON hidra_notification_delivery_attempt FOR EACH ROW EXECUTE FUNCTION hmr060_reject_attempt_rewrite();
CREATE TRIGGER trg_hmr060_attempt_no_truncate BEFORE TRUNCATE
    ON hidra_notification_delivery_attempt FOR EACH STATEMENT EXECUTE FUNCTION hmr060_reject_attempt_rewrite();
