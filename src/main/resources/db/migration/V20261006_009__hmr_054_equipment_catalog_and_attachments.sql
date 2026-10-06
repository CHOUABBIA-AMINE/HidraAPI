-- HMR-054: EquipmentType is the sole active equipment classification authority.
-- Preserve removed enum values as unmapped historical data; never guess a taxonomy mapping.
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM hidra_topology_equipment e
               LEFT JOIN hidra_topology_equipment_type t ON t.id = e.equipment_type_id
               WHERE t.id IS NULL OR e.equipment_kind IS DISTINCT FROM t.equipment_kind) THEN
        RAISE EXCEPTION 'HMR-054 preflight failed: missing type or conflicting legacy equipment classification'
            USING ERRCODE = '23514';
    END IF;
    IF EXISTS (SELECT 1 FROM hidra_topology_equipment e
               WHERE (e.facility_id IS NOT NULL AND NOT EXISTS
                        (SELECT 1 FROM hidra_topology_facility f WHERE f.id = e.facility_id))
                  OR (e.node_id IS NOT NULL AND NOT EXISTS
                        (SELECT 1 FROM hidra_topology_node n WHERE n.id = e.node_id))
                  OR (e.pipeline_segment_id IS NOT NULL AND NOT EXISTS
                        (SELECT 1 FROM hidra_topology_pipeline_segment p WHERE p.id = e.pipeline_segment_id))) THEN
        RAISE EXCEPTION 'HMR-054 preflight failed: orphan equipment attachment' USING ERRCODE = '23514';
    END IF;
END $$;
ALTER TABLE hidra_topology_equipment
    RENAME COLUMN equipment_kind TO legacy_equipment_kind;
ALTER TABLE hidra_topology_equipment ALTER COLUMN legacy_equipment_kind DROP NOT NULL;
ALTER TABLE hidra_topology_equipment_type
    RENAME COLUMN equipment_kind TO legacy_equipment_kind;
ALTER TABLE hidra_topology_equipment_type ALTER COLUMN legacy_equipment_kind DROP NOT NULL;
COMMENT ON COLUMN hidra_topology_equipment.legacy_equipment_kind IS
    'Historical pre-HMR-054 enum value; not an active classification source or mapped JPA property.';
COMMENT ON COLUMN hidra_topology_equipment_type.legacy_equipment_kind IS
    'Historical pre-HMR-054 enum value; classification is governed by EquipmentType identity/code.';
ALTER TABLE hidra_topology_equipment
    ADD CONSTRAINT fk_hmr054_equipment_facility FOREIGN KEY (facility_id)
        REFERENCES hidra_topology_facility (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr054_equipment_node FOREIGN KEY (node_id)
        REFERENCES hidra_topology_node (id) ON DELETE RESTRICT,
    ADD CONSTRAINT fk_hmr054_equipment_segment FOREIGN KEY (pipeline_segment_id)
        REFERENCES hidra_topology_pipeline_segment (id) ON DELETE RESTRICT;
