-- Incident-owned constraints only. Cross-module identities remain owner-contract references.
CREATE FUNCTION hidra_incident_catalog_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE v text; previous text; family text; i integer; row_active boolean;
BEGIN
    FOR i IN 0..(TG_NARGS / 2 - 1) LOOP
        v := to_jsonb(NEW)->>TG_ARGV[i*2]; family := TG_ARGV[i*2+1];
        IF v IS NOT NULL THEN
            SELECT c.active INTO row_active FROM hidra_incident_catalog_entry c WHERE c.id=v AND c.catalog_name=family FOR SHARE;
            IF NOT FOUND THEN RAISE EXCEPTION 'Incident catalog reference % must belong to %',v,family; END IF;
            previous := CASE WHEN TG_OP='UPDATE' THEN to_jsonb(OLD)->>TG_ARGV[i*2] ELSE NULL END;
            IF (TG_OP='INSERT' OR previous IS DISTINCT FROM v) AND NOT row_active THEN RAISE EXCEPTION 'Incident catalog reference % must be active',v; END IF;
        END IF;
    END LOOP;
    RETURN NEW;
END $$;
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_incident i LEFT JOIN hidra_incident_catalog_entry c ON c.id=i.classification_id
        LEFT JOIN hidra_incident_catalog_entry s ON s.id=i.severity_id LEFT JOIN hidra_incident_catalog_entry p ON p.id=i.priority_id
        WHERE c.catalog_name IS DISTINCT FROM 'INCIDENT_CLASSIFICATION' OR s.catalog_name IS DISTINCT FROM 'INCIDENT_SEVERITY'
        OR (i.priority_id IS NOT NULL AND p.catalog_name IS DISTINCT FROM 'INCIDENT_PRIORITY'))
    THEN RAISE EXCEPTION 'HMR-062: reconcile legacy Incident catalog families before migration'; END IF;
END $$;
ALTER TABLE hidra_incident ADD CONSTRAINT fk_hmr062_incident_priority FOREIGN KEY(priority_id) REFERENCES hidra_incident_catalog_entry(id) ON DELETE RESTRICT;
ALTER TABLE hidra_incident ADD CONSTRAINT ck_hmr062_temporal_state CHECK (
    detected_at<=reported_at AND (closed_at IS NULL OR status='CLOSED')
    AND (resolved_at IS NULL OR status IN ('RESOLVED','CLOSED'))
    AND (cancelled_at IS NULL OR status='CANCELLED')
    AND ((topology_asset_id IS NULL)=(topology_asset_type_code IS NULL))
    AND (status<>'CLOSED' OR ((NULLIF(btrim(responsible_actor_id),'') IS NOT NULL AND NULLIF(btrim(responsible_actor_name_snapshot),'') IS NOT NULL)
        OR (NULLIF(btrim(responsible_organization_unit_id),'') IS NOT NULL AND NULLIF(btrim(responsible_organization_unit_name_snapshot),'') IS NOT NULL)))
);
CREATE TRIGGER tr_hmr062_incident_catalog BEFORE INSERT OR UPDATE ON hidra_incident FOR EACH ROW
    EXECUTE FUNCTION hidra_incident_catalog_guard('classification_id','INCIDENT_CLASSIFICATION','severity_id','INCIDENT_SEVERITY','priority_id','INCIDENT_PRIORITY');
CREATE FUNCTION hidra_incident_closed_immutable() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF OLD.status='CLOSED' AND (TG_OP='DELETE' OR NEW IS DISTINCT FROM OLD) THEN RAISE EXCEPTION 'Closed Incident is immutable'; END IF;
    IF TG_OP='DELETE' THEN RETURN OLD; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr062_closed_immutable BEFORE UPDATE OR DELETE ON hidra_incident FOR EACH ROW EXECUTE FUNCTION hidra_incident_closed_immutable();
