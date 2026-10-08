-- Policy rows require explicit operator approval; no direction is inferred from catalog codes.
CREATE TABLE IF NOT EXISTS hidra_incident_relationship_policy (
    relationship_type_id varchar(80) PRIMARY KEY REFERENCES hidra_incident_catalog_entry(id),
    direction varchar(20) NOT NULL CHECK(direction IN ('SYMMETRIC','DIRECTIONAL')),
    reciprocal_type_id varchar(80) REFERENCES hidra_incident_catalog_entry(id),
    active boolean NOT NULL,
    CHECK(direction<>'SYMMETRIC' OR reciprocal_type_id IS NULL),
    CHECK(reciprocal_type_id IS NULL OR reciprocal_type_id<>relationship_type_id)
);
CREATE TRIGGER tr_hmr091_policy_catalog BEFORE INSERT OR UPDATE ON hidra_incident_relationship_policy FOR EACH ROW
 EXECUTE FUNCTION hidra_incident_catalog_guard('relationship_type_id','RELATED_INCIDENT_RELATIONSHIP_TYPE','reciprocal_type_id','RELATED_INCIDENT_RELATIONSHIP_TYPE');
CREATE FUNCTION hidra_incident_relationship_policy_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM hidra_incident_relationship_policy p LEFT JOIN hidra_incident_relationship_policy q ON q.relationship_type_id=p.reciprocal_type_id
        WHERE p.reciprocal_type_id IS NOT NULL AND (q.direction IS DISTINCT FROM 'DIRECTIONAL' OR q.reciprocal_type_id IS DISTINCT FROM p.relationship_type_id OR q.active IS DISTINCT FROM p.active))
    THEN RAISE EXCEPTION 'Incident reciprocal policy must be an explicit coherent pair'; END IF;
    IF TG_OP<>'INSERT' AND EXISTS (SELECT 1 FROM hidra_incident_related_incident WHERE relationship_type_id=OLD.relationship_type_id)
    THEN RAISE EXCEPTION 'Relationship policy used by historical evidence is immutable'; END IF;
    RETURN NULL;
END $$;
CREATE CONSTRAINT TRIGGER tr_hmr091_policy_coherence AFTER INSERT OR UPDATE OR DELETE ON hidra_incident_relationship_policy DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE FUNCTION hidra_incident_relationship_policy_guard();
DO $$ BEGIN
 IF EXISTS (SELECT 1 FROM hidra_incident_related_incident r LEFT JOIN hidra_incident i ON i.id=r.related_incident_id
    LEFT JOIN hidra_incident_catalog_entry c ON c.id=r.relationship_type_id LEFT JOIN hidra_incident_relationship_policy p ON p.relationship_type_id=r.relationship_type_id
    WHERE i.id IS NULL OR r.incident_id=r.related_incident_id OR r.created_at IS NULL OR c.catalog_name IS DISTINCT FROM 'RELATED_INCIDENT_RELATIONSHIP_TYPE' OR p.relationship_type_id IS NULL)
 THEN RAISE EXCEPTION 'HMR-091: reconcile legacy Incident links and approved direction policy'; END IF;
 IF EXISTS (SELECT 1 FROM hidra_incident_related_incident r JOIN hidra_incident_relationship_policy p ON p.relationship_type_id=r.relationship_type_id
    WHERE p.direction='SYMMETRIC' AND r.incident_id>r.related_incident_id)
 OR EXISTS (SELECT 1 FROM hidra_incident_related_incident r JOIN hidra_incident_relationship_policy p ON p.relationship_type_id=r.relationship_type_id
    JOIN hidra_incident_related_incident q ON q.incident_id=r.related_incident_id AND q.related_incident_id=r.incident_id AND q.relationship_type_id=p.reciprocal_type_id)
 THEN RAISE EXCEPTION 'HMR-091: reconcile canonical direction and reciprocal legacy duplicates'; END IF;
END $$;
ALTER TABLE hidra_incident_related_incident DROP CONSTRAINT fk_hra111_incident_013;
ALTER TABLE hidra_incident_related_incident ADD CONSTRAINT fk_hmr091_related_incident FOREIGN KEY(related_incident_id) REFERENCES hidra_incident(id) ON DELETE RESTRICT;
ALTER TABLE hidra_incident_related_incident ADD CONSTRAINT fk_hmr091_policy FOREIGN KEY(relationship_type_id) REFERENCES hidra_incident_relationship_policy(relationship_type_id) ON DELETE RESTRICT;
ALTER TABLE hidra_incident_related_incident ADD CONSTRAINT ck_hmr091_not_self CHECK(incident_id<>related_incident_id);
ALTER TABLE hidra_incident_related_incident ADD CONSTRAINT uq_hmr091_relationship UNIQUE(incident_id,related_incident_id,relationship_type_id);
CREATE TRIGGER tr_hmr091_link_catalog BEFORE INSERT OR UPDATE ON hidra_incident_related_incident FOR EACH ROW
 EXECUTE FUNCTION hidra_incident_catalog_guard('relationship_type_id','RELATED_INCIDENT_RELATIONSHIP_TYPE');
CREATE FUNCTION hidra_incident_relationship_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE p hidra_incident_relationship_policy%ROWTYPE; temp text;
BEGIN
    IF TG_OP<>'INSERT' THEN RAISE EXCEPTION 'Incident relationship evidence is append-only'; END IF;
    -- Deterministic locking serializes exact/inverse creation from either direction.
    PERFORM 1 FROM hidra_incident WHERE id IN (NEW.incident_id,NEW.related_incident_id) ORDER BY id FOR UPDATE;
    SELECT * INTO p FROM hidra_incident_relationship_policy WHERE relationship_type_id=NEW.relationship_type_id AND active FOR SHARE;
    IF NOT FOUND THEN RAISE EXCEPTION 'Explicit active Incident relationship policy required'; END IF;
    IF p.direction='SYMMETRIC' AND NEW.incident_id>NEW.related_incident_id THEN temp:=NEW.incident_id;NEW.incident_id:=NEW.related_incident_id;NEW.related_incident_id:=temp; END IF;
    IF EXISTS (SELECT 1 FROM hidra_incident_related_incident r WHERE (r.incident_id=NEW.incident_id AND r.related_incident_id=NEW.related_incident_id AND r.relationship_type_id=NEW.relationship_type_id)
        OR (p.reciprocal_type_id IS NOT NULL AND r.incident_id=NEW.related_incident_id AND r.related_incident_id=NEW.incident_id AND r.relationship_type_id=p.reciprocal_type_id))
    THEN RAISE EXCEPTION 'Duplicate or reciprocal Incident relationship'; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER tr_hmr091_link_integrity BEFORE INSERT OR UPDATE OR DELETE ON hidra_incident_related_incident FOR EACH ROW EXECUTE FUNCTION hidra_incident_relationship_guard();
