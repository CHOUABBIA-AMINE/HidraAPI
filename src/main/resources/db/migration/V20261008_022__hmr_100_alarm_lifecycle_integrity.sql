-- HMR-100: fail closed; no data repair, catalog provisioning or fabricated history.
LOCK TABLE hidra_alarm, hidra_alarm_catalog_entry, hidra_alarm_lifecycle_event IN SHARE ROW EXCLUSIVE MODE;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_alarm a LEFT JOIN hidra_alarm_catalog_entry t ON t.id=a.alarm_type_id
        LEFT JOIN hidra_alarm_catalog_entry s ON s.id=a.severity_id
        LEFT JOIN hidra_alarm_catalog_entry p ON p.id=a.priority_id
        WHERE a.title_fr IS NULL OR btrim(a.title_fr)='' OR t.catalog_name IS DISTINCT FROM 'ALARM_TYPE'
           OR s.catalog_name IS DISTINCT FROM 'ALARM_SEVERITY'
           OR (a.priority_id IS NOT NULL AND p.catalog_name IS DISTINCT FROM 'ALARM_PRIORITY')) THEN
        RAISE EXCEPTION 'HMR-100: existing Alarm has blank title or invalid type/severity/priority family; owner remediation required';
    END IF;
    IF EXISTS (SELECT alarm_id FROM hidra_alarm_lifecycle_event WHERE event_type='RAISED' GROUP BY alarm_id HAVING count(*)>1) THEN
        RAISE EXCEPTION 'HMR-100: duplicate historical RAISED events; no history repair is authorized';
    END IF;
    IF EXISTS (SELECT 1 FROM hidra_alarm_lifecycle_event e LEFT JOIN hidra_alarm a ON a.id=e.alarm_id WHERE a.id IS NULL)
       OR EXISTS (SELECT 1 FROM hidra_alarm_acknowledgement e LEFT JOIN hidra_alarm a ON a.id=e.alarm_id WHERE a.id IS NULL)
       OR EXISTS (SELECT 1 FROM hidra_alarm_closure e LEFT JOIN hidra_alarm a ON a.id=e.alarm_id WHERE a.id IS NULL)
       OR EXISTS (SELECT 1 FROM hidra_alarm_shelving e LEFT JOIN hidra_alarm a ON a.id=e.alarm_id WHERE a.id IS NULL) THEN
        RAISE EXCEPTION 'HMR-100: dangling same-module Alarm evidence';
    END IF;
END $$;
ALTER TABLE hidra_alarm ADD CONSTRAINT ck_hmr100_title CHECK (btrim(title_fr)<>'');
ALTER TABLE hidra_alarm ADD CONSTRAINT fk_hmr100_priority FOREIGN KEY(priority_id) REFERENCES hidra_alarm_catalog_entry(id) ON DELETE RESTRICT;
CREATE UNIQUE INDEX uq_hmr100_raised_event ON hidra_alarm_lifecycle_event(alarm_id) WHERE event_type='RAISED';

CREATE FUNCTION hmr100_require_family(entry_id varchar, expected varchar) RETURNS void LANGUAGE plpgsql AS $$
DECLARE family varchar;
BEGIN
    SELECT catalog_name INTO family FROM hidra_alarm_catalog_entry WHERE id=entry_id FOR SHARE;
    IF family IS DISTINCT FROM expected THEN
        RAISE EXCEPTION 'HMR-100: catalog entry % must belong to %', entry_id, expected;
    END IF;
END $$;
CREATE FUNCTION hmr100_alarm_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    PERFORM hmr100_require_family(NEW.alarm_type_id,'ALARM_TYPE');
    PERFORM hmr100_require_family(NEW.severity_id,'ALARM_SEVERITY');
    IF NEW.priority_id IS NOT NULL THEN PERFORM hmr100_require_family(NEW.priority_id,'ALARM_PRIORITY'); END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr100_alarm_catalog BEFORE INSERT OR UPDATE ON hidra_alarm FOR EACH ROW EXECUTE FUNCTION hmr100_alarm_catalog_guard();

CREATE FUNCTION hmr100_used_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP='TRUNCATE' THEN
        IF EXISTS(SELECT 1 FROM hidra_alarm) THEN RAISE EXCEPTION 'HMR-100: used Alarm catalog cannot be truncated'; END IF;
        RETURN NULL;
    END IF;
    IF TG_OP='UPDATE' AND NEW.id IS NOT DISTINCT FROM OLD.id AND NEW.catalog_name IS NOT DISTINCT FROM OLD.catalog_name THEN RETURN NEW; END IF;
    IF EXISTS(SELECT 1 FROM hidra_alarm WHERE alarm_type_id=OLD.id OR severity_id=OLD.id OR priority_id=OLD.id) THEN
        RAISE EXCEPTION 'HMR-100: used Alarm catalog identity/family cannot be changed or deleted';
    END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr100_used_catalog BEFORE UPDATE OR DELETE ON hidra_alarm_catalog_entry FOR EACH ROW EXECUTE FUNCTION hmr100_used_catalog_guard();
CREATE TRIGGER tr_hmr100_catalog_truncate BEFORE TRUNCATE ON hidra_alarm_catalog_entry FOR EACH STATEMENT EXECUTE FUNCTION hmr100_used_catalog_guard();

CREATE FUNCTION hmr100_append_only_event() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN RAISE EXCEPTION 'HMR-100: lifecycle history is append-only; update/delete/truncate denied'; END $$;
CREATE TRIGGER tr_hmr100_event_immutable BEFORE UPDATE OR DELETE ON hidra_alarm_lifecycle_event FOR EACH ROW EXECUTE FUNCTION hmr100_append_only_event();
CREATE TRIGGER tr_hmr100_event_truncate BEFORE TRUNCATE ON hidra_alarm_lifecycle_event FOR EACH STATEMENT EXECUTE FUNCTION hmr100_append_only_event();
