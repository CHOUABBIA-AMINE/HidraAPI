-- Link topology asset rows to multilingual topology type catalog entries.
-- This migration is additive: it keeps the legacy varchar type columns and their existing CHECK constraints.
-- COR-013 will remove the legacy enum-style columns and constraints after code has fully moved to catalog foreign keys.

alter table hidra_topology_pipeline_system
    add column product_type_id varchar(80);

update hidra_topology_pipeline_system asset
set product_type_id = catalog.id
from hidra_topology_product_type catalog
where asset.product_type = catalog.code;

alter table hidra_topology_pipeline_system
    alter column product_type_id set not null;

alter table hidra_topology_pipeline_system
    add constraint fk_ht_ps_product_type foreign key (product_type_id)
    references hidra_topology_product_type (id);

create index idx_ht_ps_product_type_id
    on hidra_topology_pipeline_system (product_type_id);

alter table hidra_topology_pipeline
    add column product_type_id varchar(80);

update hidra_topology_pipeline asset
set product_type_id = catalog.id
from hidra_topology_product_type catalog
where asset.product_type = catalog.code;

alter table hidra_topology_pipeline
    alter column product_type_id set not null;

alter table hidra_topology_pipeline
    add constraint fk_ht_p_product_type foreign key (product_type_id)
    references hidra_topology_product_type (id);

create index idx_ht_p_product_type_id
    on hidra_topology_pipeline (product_type_id);

alter table hidra_topology_facility
    add column facility_type_id varchar(80),
    add column product_type_id varchar(80);

update hidra_topology_facility asset
set facility_type_id = catalog.id
from hidra_topology_facility_type catalog
where asset.facility_type = catalog.code;

update hidra_topology_facility asset
set product_type_id = catalog.id
from hidra_topology_product_type catalog
where asset.product_type = catalog.code;

alter table hidra_topology_facility
    alter column facility_type_id set not null,
    alter column product_type_id set not null;

alter table hidra_topology_facility
    add constraint fk_ht_f_facility_type foreign key (facility_type_id)
    references hidra_topology_facility_type (id),
    add constraint fk_ht_f_product_type foreign key (product_type_id)
    references hidra_topology_product_type (id);

create index idx_ht_f_facility_type_id
    on hidra_topology_facility (facility_type_id);

create index idx_ht_f_product_type_id
    on hidra_topology_facility (product_type_id);

alter table hidra_topology_node
    add column node_type_id varchar(80);

update hidra_topology_node asset
set node_type_id = catalog.id
from hidra_topology_node_type catalog
where asset.node_type = catalog.code;

alter table hidra_topology_node
    alter column node_type_id set not null;

alter table hidra_topology_node
    add constraint fk_ht_n_node_type foreign key (node_type_id)
    references hidra_topology_node_type (id);

create index idx_ht_n_node_type_id
    on hidra_topology_node (node_type_id);

alter table hidra_topology_pipeline_appurtenance
    add column appurtenance_type_id varchar(80),
    add column valve_type_id varchar(80);

update hidra_topology_pipeline_appurtenance asset
set appurtenance_type_id = catalog.id
from hidra_topology_pipeline_appurtenance_type catalog
where asset.appurtenance_type = catalog.code;

update hidra_topology_pipeline_appurtenance asset
set valve_type_id = catalog.id
from hidra_topology_valve_type catalog
where asset.valve_type = catalog.code;

alter table hidra_topology_pipeline_appurtenance
    alter column appurtenance_type_id set not null;

alter table hidra_topology_pipeline_appurtenance
    add constraint fk_ht_pa_appurtenance_type foreign key (appurtenance_type_id)
    references hidra_topology_pipeline_appurtenance_type (id),
    add constraint fk_ht_pa_valve_type foreign key (valve_type_id)
    references hidra_topology_valve_type (id),
    add constraint ck_ht_pa_valve_type_catalog_pair check (
        (valve_type is null and valve_type_id is null)
        or
        (valve_type is not null and valve_type_id is not null)
    );

create index idx_ht_pa_appurtenance_type_id
    on hidra_topology_pipeline_appurtenance (appurtenance_type_id);

create index idx_ht_pa_valve_type_id
    on hidra_topology_pipeline_appurtenance (valve_type_id);

alter table hidra_topology_connection
    add column connection_type_id varchar(80);

update hidra_topology_connection asset
set connection_type_id = catalog.id
from hidra_topology_connection_type catalog
where asset.connection_type = catalog.code;

alter table hidra_topology_connection
    alter column connection_type_id set not null;

alter table hidra_topology_connection
    add constraint fk_ht_c_connection_type foreign key (connection_type_id)
    references hidra_topology_connection_type (id);

create index idx_ht_c_connection_type_id
    on hidra_topology_connection (connection_type_id);

alter table hidra_topology_equipment
    add column equipment_type_id varchar(80);

update hidra_topology_equipment asset
set equipment_type_id = catalog.id
from hidra_topology_equipment_type catalog
where asset.equipment_type = catalog.code;

alter table hidra_topology_equipment
    alter column equipment_type_id set not null;

alter table hidra_topology_equipment
    add constraint fk_ht_e_equipment_type foreign key (equipment_type_id)
    references hidra_topology_equipment_type (id);

create index idx_ht_e_equipment_type_id
    on hidra_topology_equipment (equipment_type_id);
