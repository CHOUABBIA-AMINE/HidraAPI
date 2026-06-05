create table hidra_topology_pipeline_system (
    id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    description varchar(500),
    product_type varchar(60) not null,
    status varchar(40) not null,
    operational_owner_reference_type varchar(80),
    operational_owner_reference_id varchar(120),
    operational_owner_reference_code varchar(120),
    operational_owner_reference_name varchar(160),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_pipeline_system primary key (id),
    constraint uk_hidra_topology_pipeline_system_code unique (code),
    constraint ck_hidra_topology_pipeline_system_product_type check (
        product_type in ('GAS', 'CRUDE_OIL', 'CONDENSATE', 'LPG', 'REFINED_PRODUCT', 'MULTIPHASE', 'UNKNOWN')
    ),
    constraint ck_hidra_topology_pipeline_system_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_pipeline_system_owner_ref check (
        (
            operational_owner_reference_type is null
            and operational_owner_reference_id is null
            and operational_owner_reference_code is null
            and operational_owner_reference_name is null
        )
        or
        (
            operational_owner_reference_type is not null
            and operational_owner_reference_id is not null
            and operational_owner_reference_code is not null
            and operational_owner_reference_name is not null
        )
    ),
    constraint ck_hidra_topology_pipeline_system_updated_at check (updated_at >= created_at)
);

create table hidra_topology_pipeline (
    id varchar(80) not null,
    pipeline_system_id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    description varchar(500),
    product_type varchar(60) not null,
    nominal_diameter_inches numeric(19, 3) not null,
    design_length_km numeric(19, 3) not null,
    status varchar(40) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_pipeline primary key (id),
    constraint uk_hidra_topology_pipeline_code unique (code),
    constraint fk_hidra_topology_pipeline_system foreign key (pipeline_system_id)
        references hidra_topology_pipeline_system (id),
    constraint ck_hidra_topology_pipeline_product_type check (
        product_type in ('GAS', 'CRUDE_OIL', 'CONDENSATE', 'LPG', 'REFINED_PRODUCT', 'MULTIPHASE', 'UNKNOWN')
    ),
    constraint ck_hidra_topology_pipeline_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_pipeline_diameter check (nominal_diameter_inches > 0),
    constraint ck_hidra_topology_pipeline_length check (design_length_km > 0),
    constraint ck_hidra_topology_pipeline_updated_at check (updated_at >= created_at)
);

create table hidra_topology_facility (
    id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    facility_type varchar(80) not null,
    product_type varchar(60) not null,
    status varchar(40) not null,
    latitude numeric(10, 7),
    longitude numeric(10, 7),
    organization_unit_reference_type varchar(80),
    organization_unit_reference_id varchar(120),
    organization_unit_reference_code varchar(120),
    organization_unit_reference_name varchar(160),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_facility primary key (id),
    constraint uk_hidra_topology_facility_code unique (code),
    constraint ck_hidra_topology_facility_type check (
        facility_type in (
            'COMPRESSION_STATION',
            'PUMPING_STATION',
            'METERING_STATION',
            'VALVE_STATION',
            'TERMINAL',
            'PROCESSING_PLANT',
            'PRODUCTION_FIELD',
            'GATHERING_CENTER',
            'STORAGE_FACILITY',
            'DELIVERY_FACILITY',
            'RECEIPT_FACILITY',
            'DISPATCHING_CENTER',
            'OTHER'
        )
    ),
    constraint ck_hidra_topology_facility_product_type check (
        product_type in ('GAS', 'CRUDE_OIL', 'CONDENSATE', 'LPG', 'REFINED_PRODUCT', 'MULTIPHASE', 'UNKNOWN')
    ),
    constraint ck_hidra_topology_facility_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_facility_coordinate_pair check (
        (latitude is null and longitude is null)
        or
        (latitude is not null and longitude is not null)
    ),
    constraint ck_hidra_topology_facility_latitude check (latitude is null or latitude between -90 and 90),
    constraint ck_hidra_topology_facility_longitude check (longitude is null or longitude between -180 and 180),
    constraint ck_hidra_topology_facility_org_ref check (
        (
            organization_unit_reference_type is null
            and organization_unit_reference_id is null
            and organization_unit_reference_code is null
            and organization_unit_reference_name is null
        )
        or
        (
            organization_unit_reference_type is not null
            and organization_unit_reference_id is not null
            and organization_unit_reference_code is not null
            and organization_unit_reference_name is not null
        )
    ),
    constraint ck_hidra_topology_facility_updated_at check (updated_at >= created_at)
);

create table hidra_topology_node (
    id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    node_type varchar(80) not null,
    facility_id varchar(80),
    pipeline_appurtenance_id varchar(80),
    latitude numeric(10, 7),
    longitude numeric(10, 7),
    elevation_meters numeric(19, 3),
    status varchar(40) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_node primary key (id),
    constraint uk_hidra_topology_node_code unique (code),
    constraint fk_hidra_topology_node_facility foreign key (facility_id)
        references hidra_topology_facility (id),
    constraint ck_hidra_topology_node_type check (
        node_type in (
            'FACILITY_INLET',
            'FACILITY_OUTLET',
            'FACILITY_INTERNAL',
            'PIPELINE_JUNCTION',
            'PIPELINE_VALVE_POINT',
            'INJECTION_POINT',
            'EXTRACTION_POINT',
            'PURGE_POINT',
            'VENT_POINT',
            'DRAIN_POINT',
            'METERING_POINT',
            'SAMPLING_POINT',
            'SCRAPER_POINT',
            'RECEIPT_POINT',
            'DELIVERY_POINT',
            'CONNECTION_POINT',
            'OTHER'
        )
    ),
    constraint ck_hidra_topology_node_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_node_coordinate_pair check (
        (latitude is null and longitude is null)
        or
        (latitude is not null and longitude is not null)
    ),
    constraint ck_hidra_topology_node_latitude check (latitude is null or latitude between -90 and 90),
    constraint ck_hidra_topology_node_longitude check (longitude is null or longitude between -180 and 180),
    constraint ck_hidra_topology_node_updated_at check (updated_at >= created_at)
);

create table hidra_topology_pipeline_segment (
    id varchar(80) not null,
    pipeline_id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    from_node_id varchar(80) not null,
    to_node_id varchar(80) not null,
    length_km numeric(19, 3) not null,
    diameter_inches numeric(19, 3) not null,
    status varchar(40) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_pipeline_segment primary key (id),
    constraint uk_hidra_topology_pipeline_segment_code unique (code),
    constraint fk_hidra_topology_pipeline_segment_pipeline foreign key (pipeline_id)
        references hidra_topology_pipeline (id),
    constraint fk_hidra_topology_pipeline_segment_from_node foreign key (from_node_id)
        references hidra_topology_node (id),
    constraint fk_hidra_topology_pipeline_segment_to_node foreign key (to_node_id)
        references hidra_topology_node (id),
    constraint ck_hidra_topology_pipeline_segment_nodes check (from_node_id <> to_node_id),
    constraint ck_hidra_topology_pipeline_segment_length check (length_km > 0),
    constraint ck_hidra_topology_pipeline_segment_diameter check (diameter_inches > 0),
    constraint ck_hidra_topology_pipeline_segment_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_pipeline_segment_updated_at check (updated_at >= created_at)
);

create table hidra_topology_pipeline_appurtenance (
    id varchar(80) not null,
    pipeline_id varchar(80) not null,
    node_id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    appurtenance_type varchar(80) not null,
    valve_type varchar(80),
    pipeline_kilometer_point numeric(19, 3) not null,
    status varchar(40) not null,
    latitude numeric(10, 7),
    longitude numeric(10, 7),
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_pipeline_appurtenance primary key (id),
    constraint uk_hidra_topology_pipeline_appurtenance_code unique (code),
    constraint uk_hidra_topology_pipeline_appurtenance_node unique (node_id),
    constraint fk_hidra_topology_pipeline_appurtenance_pipeline foreign key (pipeline_id)
        references hidra_topology_pipeline (id),
    constraint fk_hidra_topology_pipeline_appurtenance_node foreign key (node_id)
        references hidra_topology_node (id),
    constraint ck_hidra_topology_pipeline_appurtenance_type check (
        appurtenance_type in (
            'VALVE',
            'INJECTION_POINT',
            'EXTRACTION_POINT',
            'PURGE_POINT',
            'VENT_POINT',
            'DRAIN_POINT',
            'SAMPLING_POINT',
            'METERING_POINT',
            'SCRAPER_LAUNCHER',
            'SCRAPER_RECEIVER',
            'HOT_TAP_POINT',
            'BYPASS_POINT',
            'CONNECTION_POINT',
            'OTHER'
        )
    ),
    constraint ck_hidra_topology_pipeline_appurtenance_valve_type check (
        valve_type is null
        or valve_type in (
            'BLOCK_VALVE',
            'SECTIONALIZING_VALVE',
            'ISOLATION_VALVE',
            'SHUTDOWN_VALVE',
            'CONTROL_VALVE',
            'CHECK_VALVE',
            'RELIEF_VALVE',
            'PRESSURE_REGULATING_VALVE',
            'BYPASS_VALVE',
            'DRAIN_VALVE',
            'VENT_VALVE',
            'ESD_VALVE',
            'MANUAL_VALVE',
            'MOTORIZED_VALVE',
            'OTHER'
        )
    ),
    constraint ck_hidra_topology_pipeline_appurtenance_valve_consistency check (
        (appurtenance_type = 'VALVE' and valve_type is not null)
        or
        (appurtenance_type <> 'VALVE' and valve_type is null)
    ),
    constraint ck_hidra_topology_pipeline_appurtenance_kp check (pipeline_kilometer_point >= 0),
    constraint ck_hidra_topology_pipeline_appurtenance_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_pipeline_appurtenance_coordinate_pair check (
        (latitude is null and longitude is null)
        or
        (latitude is not null and longitude is not null)
    ),
    constraint ck_hidra_topology_pipeline_appurtenance_latitude check (latitude is null or latitude between -90 and 90),
    constraint ck_hidra_topology_pipeline_appurtenance_longitude check (longitude is null or longitude between -180 and 180),
    constraint ck_hidra_topology_pipeline_appurtenance_updated_at check (updated_at >= created_at)
);

alter table hidra_topology_node
    add constraint fk_hidra_topology_node_pipeline_appurtenance foreign key (pipeline_appurtenance_id)
    references hidra_topology_pipeline_appurtenance (id);

create table hidra_topology_connection (
    id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    from_node_id varchar(80) not null,
    to_node_id varchar(80) not null,
    connection_type varchar(80) not null,
    linked_asset_type varchar(80) not null,
    linked_asset_id varchar(120) not null,
    status varchar(40) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_connection primary key (id),
    constraint uk_hidra_topology_connection_code unique (code),
    constraint fk_hidra_topology_connection_from_node foreign key (from_node_id)
        references hidra_topology_node (id),
    constraint fk_hidra_topology_connection_to_node foreign key (to_node_id)
        references hidra_topology_node (id),
    constraint ck_hidra_topology_connection_nodes check (from_node_id <> to_node_id),
    constraint ck_hidra_topology_connection_type check (
        connection_type in (
            'PIPELINE_SEGMENT',
            'FACILITY_INTERNAL',
            'VALVE_CONNECTION',
            'METERING_CONNECTION',
            'JUNCTION_CONNECTION',
            'APPURTENANCE_CONNECTION',
            'OTHER'
        )
    ),
    constraint ck_hidra_topology_connection_asset_type check (
        linked_asset_type in (
            'PIPELINE_SYSTEM',
            'PIPELINE',
            'FACILITY',
            'NODE',
            'SEGMENT',
            'APPURTENANCE',
            'CONNECTION',
            'EQUIPMENT'
        )
    ),
    constraint ck_hidra_topology_connection_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_connection_updated_at check (updated_at >= created_at)
);

create table hidra_topology_equipment (
    id varchar(80) not null,
    code varchar(80) not null,
    name varchar(160) not null,
    equipment_type varchar(80) not null,
    parent_asset_type varchar(80) not null,
    parent_asset_id varchar(120) not null,
    status varchar(40) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_hidra_topology_equipment primary key (id),
    constraint uk_hidra_topology_equipment_code unique (code),
    constraint ck_hidra_topology_equipment_type check (
        equipment_type in (
            'COMPRESSOR',
            'PUMP',
            'VALVE',
            'METER',
            'SEPARATOR',
            'SCRAPER_LAUNCHER',
            'SCRAPER_RECEIVER',
            'ACTUATOR',
            'CONTROL_PANEL',
            'INSTRUMENTATION',
            'OTHER'
        )
    ),
    constraint ck_hidra_topology_equipment_parent_asset_type check (
        parent_asset_type in (
            'PIPELINE_SYSTEM',
            'PIPELINE',
            'FACILITY',
            'NODE',
            'SEGMENT',
            'APPURTENANCE',
            'CONNECTION'
        )
    ),
    constraint ck_hidra_topology_equipment_status check (
        status in ('PLANNED', 'ACTIVE', 'INACTIVE', 'UNDER_MAINTENANCE', 'RETIRED', 'DECOMMISSIONED')
    ),
    constraint ck_hidra_topology_equipment_updated_at check (updated_at >= created_at)
);

create index idx_hidra_topology_pipeline_system_product_type
    on hidra_topology_pipeline_system (product_type);

create index idx_hidra_topology_pipeline_system_status
    on hidra_topology_pipeline_system (status);

create index idx_hidra_topology_pipeline_system_owner_ref
    on hidra_topology_pipeline_system (operational_owner_reference_type, operational_owner_reference_id);

create index idx_hidra_topology_pipeline_pipeline_system_id
    on hidra_topology_pipeline (pipeline_system_id);

create index idx_hidra_topology_pipeline_product_type
    on hidra_topology_pipeline (product_type);

create index idx_hidra_topology_pipeline_status
    on hidra_topology_pipeline (status);

create index idx_hidra_topology_facility_type
    on hidra_topology_facility (facility_type);

create index idx_hidra_topology_facility_product_type
    on hidra_topology_facility (product_type);

create index idx_hidra_topology_facility_status
    on hidra_topology_facility (status);

create index idx_hidra_topology_facility_org_ref
    on hidra_topology_facility (organization_unit_reference_type, organization_unit_reference_id);

create index idx_hidra_topology_node_facility_id
    on hidra_topology_node (facility_id);

create index idx_hidra_topology_node_appurtenance_id
    on hidra_topology_node (pipeline_appurtenance_id);

create index idx_hidra_topology_node_type
    on hidra_topology_node (node_type);

create index idx_hidra_topology_node_status
    on hidra_topology_node (status);

create index idx_hidra_topology_pipeline_segment_pipeline_id
    on hidra_topology_pipeline_segment (pipeline_id);

create index idx_hidra_topology_pipeline_segment_from_node_id
    on hidra_topology_pipeline_segment (from_node_id);

create index idx_hidra_topology_pipeline_segment_to_node_id
    on hidra_topology_pipeline_segment (to_node_id);

create index idx_hidra_topology_pipeline_segment_status
    on hidra_topology_pipeline_segment (status);

create index idx_hidra_topology_pipeline_appurtenance_pipeline_id
    on hidra_topology_pipeline_appurtenance (pipeline_id);

create index idx_hidra_topology_pipeline_appurtenance_type
    on hidra_topology_pipeline_appurtenance (appurtenance_type);

create index idx_hidra_topology_pipeline_appurtenance_valve_type
    on hidra_topology_pipeline_appurtenance (valve_type);

create index idx_hidra_topology_pipeline_appurtenance_kp
    on hidra_topology_pipeline_appurtenance (pipeline_id, pipeline_kilometer_point);

create index idx_hidra_topology_pipeline_appurtenance_status
    on hidra_topology_pipeline_appurtenance (status);

create index idx_hidra_topology_connection_from_node_id
    on hidra_topology_connection (from_node_id);

create index idx_hidra_topology_connection_to_node_id
    on hidra_topology_connection (to_node_id);

create index idx_hidra_topology_connection_type
    on hidra_topology_connection (connection_type);

create index idx_hidra_topology_connection_linked_asset
    on hidra_topology_connection (linked_asset_type, linked_asset_id);

create index idx_hidra_topology_connection_status
    on hidra_topology_connection (status);

create index idx_hidra_topology_equipment_type
    on hidra_topology_equipment (equipment_type);

create index idx_hidra_topology_equipment_parent_asset
    on hidra_topology_equipment (parent_asset_type, parent_asset_id);

create index idx_hidra_topology_equipment_status
    on hidra_topology_equipment (status);
