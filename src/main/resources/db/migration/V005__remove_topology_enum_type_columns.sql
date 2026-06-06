-- Remove legacy enum-style topology type columns after catalog foreign keys have been introduced.
--
-- V003 introduced multilingual catalog tables.
-- V004 linked topology asset rows to those catalogs through scalar foreign-key id columns.
-- This migration removes only the old varchar business taxonomy columns and their old enum-style checks.
--
-- Intentionally kept:
-- - status columns and status CHECK constraints
-- - lifecycle/audit constraints
-- - neutral organization/owner reference columns and checks
-- - linked_asset_type and parent_asset_type internal topology asset discriminators

-- -----------------------------------------------------------------------------
-- Legacy indexes on old varchar taxonomy columns
-- -----------------------------------------------------------------------------

drop index if exists idx_hidra_topology_pipeline_system_product_type;
drop index if exists idx_hidra_topology_pipeline_product_type;
drop index if exists idx_hidra_topology_facility_type;
drop index if exists idx_hidra_topology_facility_product_type;
drop index if exists idx_hidra_topology_node_type;
drop index if exists idx_hidra_topology_pipeline_appurtenance_type;
drop index if exists idx_hidra_topology_pipeline_appurtenance_valve_type;
drop index if exists idx_hidra_topology_connection_type;
drop index if exists idx_hidra_topology_equipment_type;

-- -----------------------------------------------------------------------------
-- Legacy enum-style CHECK constraints on old varchar taxonomy columns
-- -----------------------------------------------------------------------------

alter table hidra_topology_pipeline_system
    drop constraint if exists ck_hidra_topology_pipeline_system_product_type;

alter table hidra_topology_pipeline
    drop constraint if exists ck_hidra_topology_pipeline_product_type;

alter table hidra_topology_facility
    drop constraint if exists ck_hidra_topology_facility_type,
    drop constraint if exists ck_hidra_topology_facility_product_type;

alter table hidra_topology_node
    drop constraint if exists ck_hidra_topology_node_type;

alter table hidra_topology_pipeline_appurtenance
    drop constraint if exists ck_hidra_topology_pipeline_appurtenance_type,
    drop constraint if exists ck_hidra_topology_pipeline_appurtenance_valve_type,
    drop constraint if exists ck_hidra_topology_pipeline_appurtenance_valve_consistency,
    drop constraint if exists ck_ht_pa_valve_type_catalog_pair;

alter table hidra_topology_connection
    drop constraint if exists ck_hidra_topology_connection_type;

alter table hidra_topology_equipment
    drop constraint if exists ck_hidra_topology_equipment_type;

-- -----------------------------------------------------------------------------
-- Catalog-based valve consistency after old appurtenance_type / valve_type removal
-- -----------------------------------------------------------------------------

alter table hidra_topology_pipeline_appurtenance
    add constraint ck_ht_pa_valve_type_catalog_consistency check (
        (appurtenance_type_id = 'topology-pat-valve' and valve_type_id is not null)
        or
        (appurtenance_type_id <> 'topology-pat-valve' and valve_type_id is null)
    );

-- -----------------------------------------------------------------------------
-- Remove old varchar business taxonomy columns
-- -----------------------------------------------------------------------------

alter table hidra_topology_pipeline_system
    drop column if exists product_type;

alter table hidra_topology_pipeline
    drop column if exists product_type;

alter table hidra_topology_facility
    drop column if exists facility_type,
    drop column if exists product_type;

alter table hidra_topology_node
    drop column if exists node_type;

alter table hidra_topology_pipeline_appurtenance
    drop column if exists appurtenance_type,
    drop column if exists valve_type;

alter table hidra_topology_connection
    drop column if exists connection_type;

alter table hidra_topology_equipment
    drop column if exists equipment_type;
