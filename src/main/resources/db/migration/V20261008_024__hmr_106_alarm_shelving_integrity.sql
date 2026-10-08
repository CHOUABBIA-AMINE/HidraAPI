-- HMR-106: preserve valid historical shelving and stop on owner remediation needs.
LOCK TABLE hidra_alarm_shelving, hidra_alarm_catalog_entry IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS(SELECT 1 FROM hidra_alarm_shelving s LEFT JOIN hidra_alarm_catalog_entry c ON c.id=s.shelving_reason_id
        LEFT JOIN hidra_alarm a ON a.id=s.alarm_id
        WHERE s.shelved_until<=s.shelved_at OR c.catalog_name IS DISTINCT FROM 'SHELVING_REASON' OR a.id IS NULL) THEN
        RAISE EXCEPTION 'HMR-106: existing invalid shelving interval, family or Alarm reference; owner remediation required';
    END IF;
    IF EXISTS(SELECT alarm_id FROM hidra_alarm_shelving WHERE status='ACTIVE' GROUP BY alarm_id HAVING count(*)>1) THEN
        RAISE EXCEPTION 'HMR-106: duplicate historical ACTIVE shelving; no deletion or repair is authorized';
    END IF;
END $$;
ALTER TABLE hidra_alarm_shelving ADD CONSTRAINT ck_hmr106_shelving_interval CHECK(shelved_until>shelved_at);
CREATE UNIQUE INDEX uq_hmr106_active_shelving ON hidra_alarm_shelving(alarm_id) WHERE status='ACTIVE';
CREATE FUNCTION hmr106_shelving_family_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN PERFORM hmr100_require_family(NEW.shelving_reason_id,'SHELVING_REASON'); RETURN NEW; END $$;
CREATE TRIGGER tr_hmr106_shelving_family BEFORE INSERT OR UPDATE ON hidra_alarm_shelving FOR EACH ROW EXECUTE FUNCTION hmr106_shelving_family_guard();
CREATE FUNCTION hmr106_used_shelving_reason_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='TRUNCATE' THEN
        IF EXISTS(SELECT 1 FROM hidra_alarm_shelving) THEN RAISE EXCEPTION 'HMR-106: used shelving reason cannot be truncated'; END IF;
        RETURN NULL;
    END IF;
    IF TG_OP='UPDATE' AND NEW.id IS NOT DISTINCT FROM OLD.id AND NEW.catalog_name IS NOT DISTINCT FROM OLD.catalog_name THEN RETURN NEW; END IF;
    IF EXISTS(SELECT 1 FROM hidra_alarm_shelving WHERE shelving_reason_id=OLD.id) THEN
        RAISE EXCEPTION 'HMR-106: used shelving reason identity/family cannot change or be deleted';
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr106_used_reason BEFORE UPDATE OR DELETE ON hidra_alarm_catalog_entry FOR EACH ROW EXECUTE FUNCTION hmr106_used_shelving_reason_guard();
CREATE TRIGGER tr_hmr106_reason_truncate BEFORE TRUNCATE ON hidra_alarm_catalog_entry FOR EACH STATEMENT EXECUTE FUNCTION hmr106_used_shelving_reason_guard();
