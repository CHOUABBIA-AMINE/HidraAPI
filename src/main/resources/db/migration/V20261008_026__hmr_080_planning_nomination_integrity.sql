-- HMR-080: only explicit reviewed mapping transitions. Invalid history fails closed.
LOCK TABLE hidra_planning_nomination, hidra_planning_nomination_reference_mapping,
    hidra_custody_planning_product_policy, hidra_custody_catalog_entry,
    hidra_telemetry_planning_unit_role, hidra_telemetry_planning_unit_pair, hidra_telemetry_unit,
    hidra_planning_catalog_entry, hidra_planning_plan_revision, hidra_planning_plan_scenario
    IN SHARE ROW EXCLUSIVE MODE;
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM hidra_planning_nomination n
        LEFT JOIN hidra_planning_plan_revision r ON r.id=n.revision_id
        LEFT JOIN hidra_planning_plan_scenario s ON s.id=n.scenario_id
        LEFT JOIN hidra_planning_catalog_entry t ON t.id=n.nomination_type_id
        WHERE n.quantity<=0 OR n.period_start>=n.period_end OR n.created_at IS NULL OR n.updated_at IS NULL
           OR nullif(btrim(n.code),'') IS NULL OR n.code<>btrim(n.code)
           OR r.id IS NULL OR t.id IS NULL OR t.catalog_name<>'NOMINATION_TYPE'
           OR (n.scenario_id IS NOT NULL AND (s.id IS NULL OR s.revision_id<>n.revision_id))
           OR ((nullif(btrim(n.source_asset_type),'') IS NULL)<>(nullif(btrim(n.source_asset_id),'') IS NULL))
           OR ((nullif(btrim(n.destination_asset_type),'') IS NULL)<>(nullif(btrim(n.destination_asset_id),'') IS NULL))
    ) OR EXISTS (SELECT 1 FROM hidra_planning_nomination GROUP BY revision_id,code HAVING count(*)>1)
    THEN RAISE EXCEPTION 'HMR-080: reconcile invalid quantity/interval/audit/code/family/parent/asset history before 026'; END IF;
    IF EXISTS (
        SELECT 1 FROM hidra_planning_nomination n
        LEFT JOIN hidra_planning_nomination_reference_mapping p ON p.nomination_id=n.id AND p.field_name='PRODUCT'
        LEFT JOIN hidra_planning_nomination_reference_mapping q ON q.nomination_id=n.id AND q.field_name='QUANTITY_UNIT'
        LEFT JOIN hidra_planning_nomination_reference_mapping r ON r.nomination_id=n.id AND r.field_name='RATE_UNIT'
        LEFT JOIN hidra_custody_planning_product_policy pp ON pp.catalog_entry_id=p.canonical_owner_id
        LEFT JOIN hidra_telemetry_planning_unit_role qr ON qr.unit_id=q.canonical_owner_id AND qr.usage_role='QUANTITY'
        LEFT JOIN hidra_telemetry_planning_unit_role rr ON rr.unit_id=r.canonical_owner_id AND rr.usage_role='RATE'
        LEFT JOIN hidra_telemetry_planning_unit_pair pair ON pair.quantity_unit_id=q.canonical_owner_id AND pair.rate_unit_id=r.canonical_owner_id
        WHERE p.nomination_id IS NULL OR q.nomination_id IS NULL
           OR p.legacy_id<>n.product_type_id OR q.legacy_id<>n.quantity_unit_id
           OR pp.catalog_entry_id IS NULL OR qr.unit_id IS NULL
           OR (n.rate_unit_id IS NOT NULL AND (r.nomination_id IS NULL OR r.legacy_id<>n.rate_unit_id OR rr.unit_id IS NULL OR pair.quantity_unit_id IS NULL))
           OR (n.rate_unit_id IS NULL AND r.nomination_id IS NOT NULL)
    ) THEN RAISE EXCEPTION 'HMR-080: provision explicit per-record owner mappings, approved products, unit roles and compatible pairs before 026'; END IF;
    IF EXISTS (
        SELECT 1 FROM hidra_telemetry_planning_unit_pair p
        LEFT JOIN hidra_telemetry_planning_unit_role q ON q.unit_id=p.quantity_unit_id AND q.usage_role='QUANTITY'
        LEFT JOIN hidra_telemetry_planning_unit_role r ON r.unit_id=p.rate_unit_id AND r.usage_role='RATE'
        WHERE q.unit_id IS NULL OR r.unit_id IS NULL
    ) THEN RAISE EXCEPTION 'HMR-080: approved unit pairs require both owner roles'; END IF;
END $$;

-- These references now belong to other modules. Never replace them with cross-module FKs.
ALTER TABLE hidra_planning_nomination
    DROP CONSTRAINT fk_hra111_planning_010,
    DROP CONSTRAINT fk_hra111_planning_011;
UPDATE hidra_planning_nomination n SET
    product_type_id=p.canonical_owner_id,
    quantity_unit_id=q.canonical_owner_id,
    rate_unit_id=r.canonical_owner_id
FROM hidra_planning_nomination_reference_mapping p
JOIN hidra_planning_nomination_reference_mapping q ON q.nomination_id=p.nomination_id AND q.field_name='QUANTITY_UNIT'
LEFT JOIN hidra_planning_nomination_reference_mapping r ON r.nomination_id=p.nomination_id AND r.field_name='RATE_UNIT'
WHERE n.id=p.nomination_id AND p.field_name='PRODUCT';

ALTER TABLE hidra_planning_nomination
    ADD CONSTRAINT uq_hmr080_revision_code UNIQUE(revision_id,code),
    ADD CONSTRAINT ck_hmr080_quantity CHECK(quantity>0),
    ADD CONSTRAINT ck_hmr080_interval CHECK(period_start<period_end),
    ADD CONSTRAINT ck_hmr080_code CHECK(nullif(btrim(code),'') IS NOT NULL AND code=btrim(code)),
    ADD CONSTRAINT ck_hmr080_source_pair CHECK((nullif(btrim(source_asset_type),'') IS NULL)=(nullif(btrim(source_asset_id),'') IS NULL)),
    ADD CONSTRAINT ck_hmr080_destination_pair CHECK((nullif(btrim(destination_asset_type),'') IS NULL)=(nullif(btrim(destination_asset_id),'') IS NULL)),
    ADD CONSTRAINT fk_hmr080_scenario_revision FOREIGN KEY(scenario_id,revision_id)
        REFERENCES hidra_planning_plan_scenario(id,revision_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE FUNCTION hmr080_nomination_type_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE family varchar(80); eligible boolean; unchanged boolean:=false;
BEGIN
    IF TG_OP='UPDATE' THEN unchanged:=NEW.nomination_type_id IS NOT DISTINCT FROM OLD.nomination_type_id; END IF;
    SELECT catalog_name,active INTO family,eligible FROM hidra_planning_catalog_entry WHERE id=NEW.nomination_type_id FOR SHARE;
    IF family IS DISTINCT FROM 'NOMINATION_TYPE' OR (NOT unchanged AND eligible IS DISTINCT FROM true)
    THEN RAISE EXCEPTION 'HMR-080: exact active fresh NOMINATION_TYPE required'; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr080_nomination_type BEFORE INSERT OR UPDATE ON hidra_planning_nomination
    FOR EACH ROW EXECUTE FUNCTION hmr080_nomination_type_guard();

CREATE FUNCTION hmr080_used_nomination_family_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.catalog_name IS DISTINCT FROM 'NOMINATION_TYPE'
       AND EXISTS(SELECT 1 FROM hidra_planning_nomination WHERE nomination_type_id=OLD.id)
    THEN RAISE EXCEPTION 'HMR-080: used nomination family cannot change'; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr080_used_nomination_family BEFORE UPDATE ON hidra_planning_catalog_entry
    FOR EACH ROW EXECUTE FUNCTION hmr080_used_nomination_family_guard();
CREATE FUNCTION hmr080_nomination_catalog_truncate_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM hidra_planning_nomination)
    THEN RAISE EXCEPTION 'HMR-080: nomination catalog truncation would destroy historical identity'; END IF;
    RETURN NULL;
END $$;
CREATE TRIGGER hmr080_nomination_catalog_truncate BEFORE TRUNCATE ON hidra_planning_catalog_entry
    FOR EACH STATEMENT EXECUTE FUNCTION hmr080_nomination_catalog_truncate_guard();

-- Installed only after the approved transition; before 026 owners may correct draft provisioning.
CREATE FUNCTION hmr080_approval_history_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
    IF TG_OP IN ('DELETE','TRUNCATE') THEN RAISE EXCEPTION 'HMR-080: approved owner history is immutable'; END IF;
    IF (to_jsonb(NEW)-'active') IS DISTINCT FROM (to_jsonb(OLD)-'active')
    THEN RAISE EXCEPTION 'HMR-080: approved identities and provenance cannot be remapped'; END IF;
    RETURN NEW;
END $$;
CREATE TRIGGER hmr080_product_history BEFORE UPDATE OR DELETE ON hidra_custody_planning_product_policy
    FOR EACH ROW EXECUTE FUNCTION hmr080_approval_history_guard();
CREATE TRIGGER hmr080_product_truncate BEFORE TRUNCATE ON hidra_custody_planning_product_policy
    FOR EACH STATEMENT EXECUTE FUNCTION hmr080_approval_history_guard();
CREATE TRIGGER hmr080_role_history BEFORE UPDATE OR DELETE ON hidra_telemetry_planning_unit_role
    FOR EACH ROW EXECUTE FUNCTION hmr080_approval_history_guard();
CREATE TRIGGER hmr080_role_truncate BEFORE TRUNCATE ON hidra_telemetry_planning_unit_role
    FOR EACH STATEMENT EXECUTE FUNCTION hmr080_approval_history_guard();
CREATE TRIGGER hmr080_pair_history BEFORE UPDATE OR DELETE ON hidra_telemetry_planning_unit_pair
    FOR EACH ROW EXECUTE FUNCTION hmr080_approval_history_guard();
CREATE TRIGGER hmr080_pair_truncate BEFORE TRUNCATE ON hidra_telemetry_planning_unit_pair
    FOR EACH STATEMENT EXECUTE FUNCTION hmr080_approval_history_guard();
CREATE TRIGGER hmr080_mapping_history BEFORE UPDATE OR DELETE ON hidra_planning_nomination_reference_mapping
    FOR EACH ROW EXECUTE FUNCTION hmr080_approval_history_guard();
CREATE TRIGGER hmr080_mapping_truncate BEFORE TRUNCATE ON hidra_planning_nomination_reference_mapping
    FOR EACH STATEMENT EXECUTE FUNCTION hmr080_approval_history_guard();
