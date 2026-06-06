-- Add multilingual topology type catalog tables.
-- This migration is additive only: it does not modify V002 asset columns or check constraints.

create table hidra_topology_product_type (
    id varchar(80) not null,
    code varchar(80) not null,
    status varchar(40) not null,
    sort_order integer not null,
    system_defined boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_pt primary key (id),
    constraint uk_ht_pt_code unique (code),
    constraint ck_ht_pt_status check (status in ('ACTIVE', 'INACTIVE')),
    constraint ck_ht_pt_sort_order check (sort_order >= 0),
    constraint ck_ht_pt_updated_at check (updated_at >= created_at)
);

create table hidra_topology_product_type_translation (
    id varchar(80) not null,
    product_type_id varchar(80) not null,
    locale varchar(10) not null,
    name varchar(160) not null,
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_pt_tr primary key (id),
    constraint fk_ht_pt_tr_type foreign key (product_type_id)
        references hidra_topology_product_type (id),
    constraint uk_ht_pt_tr_locale unique (product_type_id, locale),
    constraint ck_ht_pt_tr_locale check (length(trim(locale)) > 0),
    constraint ck_ht_pt_tr_name check (length(trim(name)) > 0),
    constraint ck_ht_pt_tr_updated_at check (updated_at >= created_at)
);

create index idx_ht_pt_status
    on hidra_topology_product_type (status);

create index idx_ht_pt_sort_order
    on hidra_topology_product_type (sort_order);

create index idx_ht_pt_tr_locale
    on hidra_topology_product_type_translation (locale);

insert into hidra_topology_product_type (id, code, status, sort_order, system_defined, created_at, updated_at)
values
    ('topology-pt-gas', 'GAS', 'ACTIVE', 10, true, current_timestamp, current_timestamp),
    ('topology-pt-crude-oil', 'CRUDE_OIL', 'ACTIVE', 11, true, current_timestamp, current_timestamp),
    ('topology-pt-condensate', 'CONDENSATE', 'ACTIVE', 12, true, current_timestamp, current_timestamp),
    ('topology-pt-lpg', 'LPG', 'ACTIVE', 13, true, current_timestamp, current_timestamp),
    ('topology-pt-refined-product', 'REFINED_PRODUCT', 'ACTIVE', 14, true, current_timestamp, current_timestamp),
    ('topology-pt-multiphase', 'MULTIPHASE', 'ACTIVE', 15, true, current_timestamp, current_timestamp),
    ('topology-pt-unknown', 'UNKNOWN', 'ACTIVE', 16, true, current_timestamp, current_timestamp);

insert into hidra_topology_product_type_translation (id, product_type_id, locale, name, description, created_at, updated_at)
values
    ('tr-pt-gas-en', 'topology-pt-gas', 'en', 'Gas', 'Hydrocarbon gas product.', current_timestamp, current_timestamp),
    ('tr-pt-gas-fr', 'topology-pt-gas', 'fr', 'Gaz', 'Hydrocarbon gas product.', current_timestamp, current_timestamp),
    ('tr-pt-gas-ar', 'topology-pt-gas', 'ar', 'غاز', 'Hydrocarbon gas product.', current_timestamp, current_timestamp),
    ('tr-pt-crude-oil-en', 'topology-pt-crude-oil', 'en', 'Crude oil', 'Unrefined liquid hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-crude-oil-fr', 'topology-pt-crude-oil', 'fr', 'Pétrole brut', 'Unrefined liquid hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-crude-oil-ar', 'topology-pt-crude-oil', 'ar', 'نفط خام', 'Unrefined liquid hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-condensate-en', 'topology-pt-condensate', 'en', 'Condensate', 'Light liquid hydrocarbon condensate.', current_timestamp, current_timestamp),
    ('tr-pt-condensate-fr', 'topology-pt-condensate', 'fr', 'Condensat', 'Light liquid hydrocarbon condensate.', current_timestamp, current_timestamp),
    ('tr-pt-condensate-ar', 'topology-pt-condensate', 'ar', 'مكثفات', 'Light liquid hydrocarbon condensate.', current_timestamp, current_timestamp),
    ('tr-pt-lpg-en', 'topology-pt-lpg', 'en', 'Liquefied petroleum gas', 'Liquefied petroleum gas product.', current_timestamp, current_timestamp),
    ('tr-pt-lpg-fr', 'topology-pt-lpg', 'fr', 'Gaz de pétrole liquéfié', 'Liquefied petroleum gas product.', current_timestamp, current_timestamp),
    ('tr-pt-lpg-ar', 'topology-pt-lpg', 'ar', 'غاز البترول المسال', 'Liquefied petroleum gas product.', current_timestamp, current_timestamp),
    ('tr-pt-refined-product-en', 'topology-pt-refined-product', 'en', 'Refined product', 'Refined hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-refined-product-fr', 'topology-pt-refined-product', 'fr', 'Produit raffiné', 'Refined hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-refined-product-ar', 'topology-pt-refined-product', 'ar', 'منتج مكرر', 'Refined hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-multiphase-en', 'topology-pt-multiphase', 'en', 'Multiphase', 'Multiphase hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-multiphase-fr', 'topology-pt-multiphase', 'fr', 'Multiphasique', 'Multiphase hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-multiphase-ar', 'topology-pt-multiphase', 'ar', 'متعدد الأطوار', 'Multiphase hydrocarbon product.', current_timestamp, current_timestamp),
    ('tr-pt-unknown-en', 'topology-pt-unknown', 'en', 'Unknown', 'Unknown or not yet classified product.', current_timestamp, current_timestamp),
    ('tr-pt-unknown-fr', 'topology-pt-unknown', 'fr', 'Inconnu', 'Unknown or not yet classified product.', current_timestamp, current_timestamp),
    ('tr-pt-unknown-ar', 'topology-pt-unknown', 'ar', 'غير معروف', 'Unknown or not yet classified product.', current_timestamp, current_timestamp);

create table hidra_topology_facility_type (
    id varchar(80) not null,
    code varchar(80) not null,
    status varchar(40) not null,
    sort_order integer not null,
    system_defined boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_ft primary key (id),
    constraint uk_ht_ft_code unique (code),
    constraint ck_ht_ft_status check (status in ('ACTIVE', 'INACTIVE')),
    constraint ck_ht_ft_sort_order check (sort_order >= 0),
    constraint ck_ht_ft_updated_at check (updated_at >= created_at)
);

create table hidra_topology_facility_type_translation (
    id varchar(80) not null,
    facility_type_id varchar(80) not null,
    locale varchar(10) not null,
    name varchar(160) not null,
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_ft_tr primary key (id),
    constraint fk_ht_ft_tr_type foreign key (facility_type_id)
        references hidra_topology_facility_type (id),
    constraint uk_ht_ft_tr_locale unique (facility_type_id, locale),
    constraint ck_ht_ft_tr_locale check (length(trim(locale)) > 0),
    constraint ck_ht_ft_tr_name check (length(trim(name)) > 0),
    constraint ck_ht_ft_tr_updated_at check (updated_at >= created_at)
);

create index idx_ht_ft_status
    on hidra_topology_facility_type (status);

create index idx_ht_ft_sort_order
    on hidra_topology_facility_type (sort_order);

create index idx_ht_ft_tr_locale
    on hidra_topology_facility_type_translation (locale);

insert into hidra_topology_facility_type (id, code, status, sort_order, system_defined, created_at, updated_at)
values
    ('topology-ft-compression-station', 'COMPRESSION_STATION', 'ACTIVE', 10, true, current_timestamp, current_timestamp),
    ('topology-ft-pumping-station', 'PUMPING_STATION', 'ACTIVE', 11, true, current_timestamp, current_timestamp),
    ('topology-ft-metering-station', 'METERING_STATION', 'ACTIVE', 12, true, current_timestamp, current_timestamp),
    ('topology-ft-valve-station', 'VALVE_STATION', 'ACTIVE', 13, true, current_timestamp, current_timestamp),
    ('topology-ft-terminal', 'TERMINAL', 'ACTIVE', 14, true, current_timestamp, current_timestamp),
    ('topology-ft-processing-plant', 'PROCESSING_PLANT', 'ACTIVE', 15, true, current_timestamp, current_timestamp),
    ('topology-ft-production-field', 'PRODUCTION_FIELD', 'ACTIVE', 16, true, current_timestamp, current_timestamp),
    ('topology-ft-gathering-center', 'GATHERING_CENTER', 'ACTIVE', 17, true, current_timestamp, current_timestamp),
    ('topology-ft-storage-facility', 'STORAGE_FACILITY', 'ACTIVE', 18, true, current_timestamp, current_timestamp),
    ('topology-ft-delivery-facility', 'DELIVERY_FACILITY', 'ACTIVE', 19, true, current_timestamp, current_timestamp),
    ('topology-ft-receipt-facility', 'RECEIPT_FACILITY', 'ACTIVE', 20, true, current_timestamp, current_timestamp),
    ('topology-ft-dispatching-center', 'DISPATCHING_CENTER', 'ACTIVE', 21, true, current_timestamp, current_timestamp),
    ('topology-ft-other', 'OTHER', 'ACTIVE', 22, true, current_timestamp, current_timestamp);

insert into hidra_topology_facility_type_translation (id, facility_type_id, locale, name, description, created_at, updated_at)
values
    ('tr-ft-compression-station-en', 'topology-ft-compression-station', 'en', 'Compression station', 'Facility that compresses gas for transportation.', current_timestamp, current_timestamp),
    ('tr-ft-compression-station-fr', 'topology-ft-compression-station', 'fr', 'Station de compression', 'Facility that compresses gas for transportation.', current_timestamp, current_timestamp),
    ('tr-ft-compression-station-ar', 'topology-ft-compression-station', 'ar', 'محطة ضغط', 'Facility that compresses gas for transportation.', current_timestamp, current_timestamp),
    ('tr-ft-pumping-station-en', 'topology-ft-pumping-station', 'en', 'Pumping station', 'Facility that pumps liquid hydrocarbons.', current_timestamp, current_timestamp),
    ('tr-ft-pumping-station-fr', 'topology-ft-pumping-station', 'fr', 'Station de pompage', 'Facility that pumps liquid hydrocarbons.', current_timestamp, current_timestamp),
    ('tr-ft-pumping-station-ar', 'topology-ft-pumping-station', 'ar', 'محطة ضخ', 'Facility that pumps liquid hydrocarbons.', current_timestamp, current_timestamp),
    ('tr-ft-metering-station-en', 'topology-ft-metering-station', 'en', 'Metering station', 'Facility used for measurement and custody transfer.', current_timestamp, current_timestamp),
    ('tr-ft-metering-station-fr', 'topology-ft-metering-station', 'fr', 'Station de comptage', 'Facility used for measurement and custody transfer.', current_timestamp, current_timestamp),
    ('tr-ft-metering-station-ar', 'topology-ft-metering-station', 'ar', 'محطة قياس', 'Facility used for measurement and custody transfer.', current_timestamp, current_timestamp),
    ('tr-ft-valve-station-en', 'topology-ft-valve-station', 'en', 'Valve station', 'Facility containing mainline valve assets.', current_timestamp, current_timestamp),
    ('tr-ft-valve-station-fr', 'topology-ft-valve-station', 'fr', 'Station de vannes', 'Facility containing mainline valve assets.', current_timestamp, current_timestamp),
    ('tr-ft-valve-station-ar', 'topology-ft-valve-station', 'ar', 'محطة صمامات', 'Facility containing mainline valve assets.', current_timestamp, current_timestamp),
    ('tr-ft-terminal-en', 'topology-ft-terminal', 'en', 'Terminal', 'Terminal facility for receipt, delivery, storage, or dispatch.', current_timestamp, current_timestamp),
    ('tr-ft-terminal-fr', 'topology-ft-terminal', 'fr', 'Terminal', 'Terminal facility for receipt, delivery, storage, or dispatch.', current_timestamp, current_timestamp),
    ('tr-ft-terminal-ar', 'topology-ft-terminal', 'ar', 'محطة نهائية', 'Terminal facility for receipt, delivery, storage, or dispatch.', current_timestamp, current_timestamp),
    ('tr-ft-processing-plant-en', 'topology-ft-processing-plant', 'en', 'Processing plant', 'Facility that processes hydrocarbons.', current_timestamp, current_timestamp),
    ('tr-ft-processing-plant-fr', 'topology-ft-processing-plant', 'fr', 'Usine de traitement', 'Facility that processes hydrocarbons.', current_timestamp, current_timestamp),
    ('tr-ft-processing-plant-ar', 'topology-ft-processing-plant', 'ar', 'محطة معالجة', 'Facility that processes hydrocarbons.', current_timestamp, current_timestamp),
    ('tr-ft-production-field-en', 'topology-ft-production-field', 'en', 'Production field', 'Production field interface in the topology network.', current_timestamp, current_timestamp),
    ('tr-ft-production-field-fr', 'topology-ft-production-field', 'fr', 'Champ de production', 'Production field interface in the topology network.', current_timestamp, current_timestamp),
    ('tr-ft-production-field-ar', 'topology-ft-production-field', 'ar', 'حقل إنتاج', 'Production field interface in the topology network.', current_timestamp, current_timestamp),
    ('tr-ft-gathering-center-en', 'topology-ft-gathering-center', 'en', 'Gathering center', 'Facility that gathers production streams.', current_timestamp, current_timestamp),
    ('tr-ft-gathering-center-fr', 'topology-ft-gathering-center', 'fr', 'Centre de collecte', 'Facility that gathers production streams.', current_timestamp, current_timestamp),
    ('tr-ft-gathering-center-ar', 'topology-ft-gathering-center', 'ar', 'مركز تجميع', 'Facility that gathers production streams.', current_timestamp, current_timestamp),
    ('tr-ft-storage-facility-en', 'topology-ft-storage-facility', 'en', 'Storage facility', 'Facility used for hydrocarbon storage.', current_timestamp, current_timestamp),
    ('tr-ft-storage-facility-fr', 'topology-ft-storage-facility', 'fr', 'Installation de stockage', 'Facility used for hydrocarbon storage.', current_timestamp, current_timestamp),
    ('tr-ft-storage-facility-ar', 'topology-ft-storage-facility', 'ar', 'منشأة تخزين', 'Facility used for hydrocarbon storage.', current_timestamp, current_timestamp),
    ('tr-ft-delivery-facility-en', 'topology-ft-delivery-facility', 'en', 'Delivery facility', 'Facility used for hydrocarbon delivery.', current_timestamp, current_timestamp),
    ('tr-ft-delivery-facility-fr', 'topology-ft-delivery-facility', 'fr', 'Installation de livraison', 'Facility used for hydrocarbon delivery.', current_timestamp, current_timestamp),
    ('tr-ft-delivery-facility-ar', 'topology-ft-delivery-facility', 'ar', 'منشأة تسليم', 'Facility used for hydrocarbon delivery.', current_timestamp, current_timestamp),
    ('tr-ft-receipt-facility-en', 'topology-ft-receipt-facility', 'en', 'Receipt facility', 'Facility used for hydrocarbon receipt.', current_timestamp, current_timestamp),
    ('tr-ft-receipt-facility-fr', 'topology-ft-receipt-facility', 'fr', 'Installation de réception', 'Facility used for hydrocarbon receipt.', current_timestamp, current_timestamp),
    ('tr-ft-receipt-facility-ar', 'topology-ft-receipt-facility', 'ar', 'منشأة استقبال', 'Facility used for hydrocarbon receipt.', current_timestamp, current_timestamp),
    ('tr-ft-dispatching-center-en', 'topology-ft-dispatching-center', 'en', 'Dispatching center', 'Facility used for dispatching and supervision.', current_timestamp, current_timestamp),
    ('tr-ft-dispatching-center-fr', 'topology-ft-dispatching-center', 'fr', 'Centre de dispatching', 'Facility used for dispatching and supervision.', current_timestamp, current_timestamp),
    ('tr-ft-dispatching-center-ar', 'topology-ft-dispatching-center', 'ar', 'مركز التوزيع', 'Facility used for dispatching and supervision.', current_timestamp, current_timestamp),
    ('tr-ft-other-en', 'topology-ft-other', 'en', 'Other', 'Other physical facility type.', current_timestamp, current_timestamp),
    ('tr-ft-other-fr', 'topology-ft-other', 'fr', 'Autre', 'Other physical facility type.', current_timestamp, current_timestamp),
    ('tr-ft-other-ar', 'topology-ft-other', 'ar', 'أخرى', 'Other physical facility type.', current_timestamp, current_timestamp);

create table hidra_topology_node_type (
    id varchar(80) not null,
    code varchar(80) not null,
    status varchar(40) not null,
    sort_order integer not null,
    system_defined boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_nt primary key (id),
    constraint uk_ht_nt_code unique (code),
    constraint ck_ht_nt_status check (status in ('ACTIVE', 'INACTIVE')),
    constraint ck_ht_nt_sort_order check (sort_order >= 0),
    constraint ck_ht_nt_updated_at check (updated_at >= created_at)
);

create table hidra_topology_node_type_translation (
    id varchar(80) not null,
    node_type_id varchar(80) not null,
    locale varchar(10) not null,
    name varchar(160) not null,
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_nt_tr primary key (id),
    constraint fk_ht_nt_tr_type foreign key (node_type_id)
        references hidra_topology_node_type (id),
    constraint uk_ht_nt_tr_locale unique (node_type_id, locale),
    constraint ck_ht_nt_tr_locale check (length(trim(locale)) > 0),
    constraint ck_ht_nt_tr_name check (length(trim(name)) > 0),
    constraint ck_ht_nt_tr_updated_at check (updated_at >= created_at)
);

create index idx_ht_nt_status
    on hidra_topology_node_type (status);

create index idx_ht_nt_sort_order
    on hidra_topology_node_type (sort_order);

create index idx_ht_nt_tr_locale
    on hidra_topology_node_type_translation (locale);

insert into hidra_topology_node_type (id, code, status, sort_order, system_defined, created_at, updated_at)
values
    ('topology-nt-facility-inlet', 'FACILITY_INLET', 'ACTIVE', 10, true, current_timestamp, current_timestamp),
    ('topology-nt-facility-outlet', 'FACILITY_OUTLET', 'ACTIVE', 11, true, current_timestamp, current_timestamp),
    ('topology-nt-facility-internal', 'FACILITY_INTERNAL', 'ACTIVE', 12, true, current_timestamp, current_timestamp),
    ('topology-nt-pipeline-junction', 'PIPELINE_JUNCTION', 'ACTIVE', 13, true, current_timestamp, current_timestamp),
    ('topology-nt-pipeline-valve-point', 'PIPELINE_VALVE_POINT', 'ACTIVE', 14, true, current_timestamp, current_timestamp),
    ('topology-nt-injection-point', 'INJECTION_POINT', 'ACTIVE', 15, true, current_timestamp, current_timestamp),
    ('topology-nt-extraction-point', 'EXTRACTION_POINT', 'ACTIVE', 16, true, current_timestamp, current_timestamp),
    ('topology-nt-purge-point', 'PURGE_POINT', 'ACTIVE', 17, true, current_timestamp, current_timestamp),
    ('topology-nt-vent-point', 'VENT_POINT', 'ACTIVE', 18, true, current_timestamp, current_timestamp),
    ('topology-nt-drain-point', 'DRAIN_POINT', 'ACTIVE', 19, true, current_timestamp, current_timestamp),
    ('topology-nt-metering-point', 'METERING_POINT', 'ACTIVE', 20, true, current_timestamp, current_timestamp),
    ('topology-nt-sampling-point', 'SAMPLING_POINT', 'ACTIVE', 21, true, current_timestamp, current_timestamp),
    ('topology-nt-scraper-point', 'SCRAPER_POINT', 'ACTIVE', 22, true, current_timestamp, current_timestamp),
    ('topology-nt-receipt-point', 'RECEIPT_POINT', 'ACTIVE', 23, true, current_timestamp, current_timestamp),
    ('topology-nt-delivery-point', 'DELIVERY_POINT', 'ACTIVE', 24, true, current_timestamp, current_timestamp),
    ('topology-nt-connection-point', 'CONNECTION_POINT', 'ACTIVE', 25, true, current_timestamp, current_timestamp),
    ('topology-nt-other', 'OTHER', 'ACTIVE', 26, true, current_timestamp, current_timestamp);

insert into hidra_topology_node_type_translation (id, node_type_id, locale, name, description, created_at, updated_at)
values
    ('tr-nt-facility-inlet-en', 'topology-nt-facility-inlet', 'en', 'Facility inlet', 'Topology node representing a facility inlet.', current_timestamp, current_timestamp),
    ('tr-nt-facility-inlet-fr', 'topology-nt-facility-inlet', 'fr', 'Entrée d''installation', 'Topology node representing a facility inlet.', current_timestamp, current_timestamp),
    ('tr-nt-facility-inlet-ar', 'topology-nt-facility-inlet', 'ar', 'مدخل المنشأة', 'Topology node representing a facility inlet.', current_timestamp, current_timestamp),
    ('tr-nt-facility-outlet-en', 'topology-nt-facility-outlet', 'en', 'Facility outlet', 'Topology node representing a facility outlet.', current_timestamp, current_timestamp),
    ('tr-nt-facility-outlet-fr', 'topology-nt-facility-outlet', 'fr', 'Sortie d''installation', 'Topology node representing a facility outlet.', current_timestamp, current_timestamp),
    ('tr-nt-facility-outlet-ar', 'topology-nt-facility-outlet', 'ar', 'مخرج المنشأة', 'Topology node representing a facility outlet.', current_timestamp, current_timestamp),
    ('tr-nt-facility-internal-en', 'topology-nt-facility-internal', 'en', 'Facility internal node', 'Internal topology node within a facility.', current_timestamp, current_timestamp),
    ('tr-nt-facility-internal-fr', 'topology-nt-facility-internal', 'fr', 'Nœud interne d''installation', 'Internal topology node within a facility.', current_timestamp, current_timestamp),
    ('tr-nt-facility-internal-ar', 'topology-nt-facility-internal', 'ar', 'نقطة داخلية بالمنشأة', 'Internal topology node within a facility.', current_timestamp, current_timestamp),
    ('tr-nt-pipeline-junction-en', 'topology-nt-pipeline-junction', 'en', 'Pipeline junction', 'Topology junction along a pipeline.', current_timestamp, current_timestamp),
    ('tr-nt-pipeline-junction-fr', 'topology-nt-pipeline-junction', 'fr', 'Jonction de pipeline', 'Topology junction along a pipeline.', current_timestamp, current_timestamp),
    ('tr-nt-pipeline-junction-ar', 'topology-nt-pipeline-junction', 'ar', 'وصلة خط الأنابيب', 'Topology junction along a pipeline.', current_timestamp, current_timestamp),
    ('tr-nt-pipeline-valve-point-en', 'topology-nt-pipeline-valve-point', 'en', 'Pipeline valve point', 'Node representing a valve location along a pipeline.', current_timestamp, current_timestamp),
    ('tr-nt-pipeline-valve-point-fr', 'topology-nt-pipeline-valve-point', 'fr', 'Point de vanne pipeline', 'Node representing a valve location along a pipeline.', current_timestamp, current_timestamp),
    ('tr-nt-pipeline-valve-point-ar', 'topology-nt-pipeline-valve-point', 'ar', 'نقطة صمام على الخط', 'Node representing a valve location along a pipeline.', current_timestamp, current_timestamp),
    ('tr-nt-injection-point-en', 'topology-nt-injection-point', 'en', 'Injection point', 'Node representing an injection point.', current_timestamp, current_timestamp),
    ('tr-nt-injection-point-fr', 'topology-nt-injection-point', 'fr', 'Point d''injection', 'Node representing an injection point.', current_timestamp, current_timestamp),
    ('tr-nt-injection-point-ar', 'topology-nt-injection-point', 'ar', 'نقطة حقن', 'Node representing an injection point.', current_timestamp, current_timestamp),
    ('tr-nt-extraction-point-en', 'topology-nt-extraction-point', 'en', 'Extraction point', 'Node representing an extraction point.', current_timestamp, current_timestamp),
    ('tr-nt-extraction-point-fr', 'topology-nt-extraction-point', 'fr', 'Point d''extraction', 'Node representing an extraction point.', current_timestamp, current_timestamp),
    ('tr-nt-extraction-point-ar', 'topology-nt-extraction-point', 'ar', 'نقطة استخراج', 'Node representing an extraction point.', current_timestamp, current_timestamp),
    ('tr-nt-purge-point-en', 'topology-nt-purge-point', 'en', 'Purge point', 'Node representing a purge point.', current_timestamp, current_timestamp),
    ('tr-nt-purge-point-fr', 'topology-nt-purge-point', 'fr', 'Point de purge', 'Node representing a purge point.', current_timestamp, current_timestamp),
    ('tr-nt-purge-point-ar', 'topology-nt-purge-point', 'ar', 'نقطة تطهير', 'Node representing a purge point.', current_timestamp, current_timestamp),
    ('tr-nt-vent-point-en', 'topology-nt-vent-point', 'en', 'Vent point', 'Node representing a vent point.', current_timestamp, current_timestamp),
    ('tr-nt-vent-point-fr', 'topology-nt-vent-point', 'fr', 'Point d''évent', 'Node representing a vent point.', current_timestamp, current_timestamp),
    ('tr-nt-vent-point-ar', 'topology-nt-vent-point', 'ar', 'نقطة تنفيس', 'Node representing a vent point.', current_timestamp, current_timestamp),
    ('tr-nt-drain-point-en', 'topology-nt-drain-point', 'en', 'Drain point', 'Node representing a drain point.', current_timestamp, current_timestamp),
    ('tr-nt-drain-point-fr', 'topology-nt-drain-point', 'fr', 'Point de vidange', 'Node representing a drain point.', current_timestamp, current_timestamp),
    ('tr-nt-drain-point-ar', 'topology-nt-drain-point', 'ar', 'نقطة تصريف', 'Node representing a drain point.', current_timestamp, current_timestamp),
    ('tr-nt-metering-point-en', 'topology-nt-metering-point', 'en', 'Metering point', 'Node representing a metering point.', current_timestamp, current_timestamp),
    ('tr-nt-metering-point-fr', 'topology-nt-metering-point', 'fr', 'Point de comptage', 'Node representing a metering point.', current_timestamp, current_timestamp),
    ('tr-nt-metering-point-ar', 'topology-nt-metering-point', 'ar', 'نقطة قياس', 'Node representing a metering point.', current_timestamp, current_timestamp),
    ('tr-nt-sampling-point-en', 'topology-nt-sampling-point', 'en', 'Sampling point', 'Node representing a sampling point.', current_timestamp, current_timestamp),
    ('tr-nt-sampling-point-fr', 'topology-nt-sampling-point', 'fr', 'Point d''échantillonnage', 'Node representing a sampling point.', current_timestamp, current_timestamp),
    ('tr-nt-sampling-point-ar', 'topology-nt-sampling-point', 'ar', 'نقطة أخذ عينات', 'Node representing a sampling point.', current_timestamp, current_timestamp),
    ('tr-nt-scraper-point-en', 'topology-nt-scraper-point', 'en', 'Scraper point', 'Node representing a scraper launcher or receiver location.', current_timestamp, current_timestamp),
    ('tr-nt-scraper-point-fr', 'topology-nt-scraper-point', 'fr', 'Point racleur', 'Node representing a scraper launcher or receiver location.', current_timestamp, current_timestamp),
    ('tr-nt-scraper-point-ar', 'topology-nt-scraper-point', 'ar', 'نقطة كاشطة', 'Node representing a scraper launcher or receiver location.', current_timestamp, current_timestamp),
    ('tr-nt-receipt-point-en', 'topology-nt-receipt-point', 'en', 'Receipt point', 'Node representing a receipt point.', current_timestamp, current_timestamp),
    ('tr-nt-receipt-point-fr', 'topology-nt-receipt-point', 'fr', 'Point de réception', 'Node representing a receipt point.', current_timestamp, current_timestamp),
    ('tr-nt-receipt-point-ar', 'topology-nt-receipt-point', 'ar', 'نقطة استقبال', 'Node representing a receipt point.', current_timestamp, current_timestamp),
    ('tr-nt-delivery-point-en', 'topology-nt-delivery-point', 'en', 'Delivery point', 'Node representing a delivery point.', current_timestamp, current_timestamp),
    ('tr-nt-delivery-point-fr', 'topology-nt-delivery-point', 'fr', 'Point de livraison', 'Node representing a delivery point.', current_timestamp, current_timestamp),
    ('tr-nt-delivery-point-ar', 'topology-nt-delivery-point', 'ar', 'نقطة تسليم', 'Node representing a delivery point.', current_timestamp, current_timestamp),
    ('tr-nt-connection-point-en', 'topology-nt-connection-point', 'en', 'Connection point', 'Node representing a connection point.', current_timestamp, current_timestamp),
    ('tr-nt-connection-point-fr', 'topology-nt-connection-point', 'fr', 'Point de connexion', 'Node representing a connection point.', current_timestamp, current_timestamp),
    ('tr-nt-connection-point-ar', 'topology-nt-connection-point', 'ar', 'نقطة اتصال', 'Node representing a connection point.', current_timestamp, current_timestamp),
    ('tr-nt-other-en', 'topology-nt-other', 'en', 'Other', 'Other topology node type.', current_timestamp, current_timestamp),
    ('tr-nt-other-fr', 'topology-nt-other', 'fr', 'Autre', 'Other topology node type.', current_timestamp, current_timestamp),
    ('tr-nt-other-ar', 'topology-nt-other', 'ar', 'أخرى', 'Other topology node type.', current_timestamp, current_timestamp);

create table hidra_topology_pipeline_appurtenance_type (
    id varchar(80) not null,
    code varchar(80) not null,
    status varchar(40) not null,
    sort_order integer not null,
    system_defined boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_pat primary key (id),
    constraint uk_ht_pat_code unique (code),
    constraint ck_ht_pat_status check (status in ('ACTIVE', 'INACTIVE')),
    constraint ck_ht_pat_sort_order check (sort_order >= 0),
    constraint ck_ht_pat_updated_at check (updated_at >= created_at)
);

create table hidra_topology_pipeline_appurtenance_type_translation (
    id varchar(80) not null,
    pipeline_appurtenance_type_id varchar(80) not null,
    locale varchar(10) not null,
    name varchar(160) not null,
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_pat_tr primary key (id),
    constraint fk_ht_pat_tr_type foreign key (pipeline_appurtenance_type_id)
        references hidra_topology_pipeline_appurtenance_type (id),
    constraint uk_ht_pat_tr_locale unique (pipeline_appurtenance_type_id, locale),
    constraint ck_ht_pat_tr_locale check (length(trim(locale)) > 0),
    constraint ck_ht_pat_tr_name check (length(trim(name)) > 0),
    constraint ck_ht_pat_tr_updated_at check (updated_at >= created_at)
);

create index idx_ht_pat_status
    on hidra_topology_pipeline_appurtenance_type (status);

create index idx_ht_pat_sort_order
    on hidra_topology_pipeline_appurtenance_type (sort_order);

create index idx_ht_pat_tr_locale
    on hidra_topology_pipeline_appurtenance_type_translation (locale);

insert into hidra_topology_pipeline_appurtenance_type (id, code, status, sort_order, system_defined, created_at, updated_at)
values
    ('topology-pat-valve', 'VALVE', 'ACTIVE', 10, true, current_timestamp, current_timestamp),
    ('topology-pat-injection-point', 'INJECTION_POINT', 'ACTIVE', 11, true, current_timestamp, current_timestamp),
    ('topology-pat-extraction-point', 'EXTRACTION_POINT', 'ACTIVE', 12, true, current_timestamp, current_timestamp),
    ('topology-pat-purge-point', 'PURGE_POINT', 'ACTIVE', 13, true, current_timestamp, current_timestamp),
    ('topology-pat-vent-point', 'VENT_POINT', 'ACTIVE', 14, true, current_timestamp, current_timestamp),
    ('topology-pat-drain-point', 'DRAIN_POINT', 'ACTIVE', 15, true, current_timestamp, current_timestamp),
    ('topology-pat-sampling-point', 'SAMPLING_POINT', 'ACTIVE', 16, true, current_timestamp, current_timestamp),
    ('topology-pat-metering-point', 'METERING_POINT', 'ACTIVE', 17, true, current_timestamp, current_timestamp),
    ('topology-pat-scraper-launcher', 'SCRAPER_LAUNCHER', 'ACTIVE', 18, true, current_timestamp, current_timestamp),
    ('topology-pat-scraper-receiver', 'SCRAPER_RECEIVER', 'ACTIVE', 19, true, current_timestamp, current_timestamp),
    ('topology-pat-hot-tap-point', 'HOT_TAP_POINT', 'ACTIVE', 20, true, current_timestamp, current_timestamp),
    ('topology-pat-bypass-point', 'BYPASS_POINT', 'ACTIVE', 21, true, current_timestamp, current_timestamp),
    ('topology-pat-connection-point', 'CONNECTION_POINT', 'ACTIVE', 22, true, current_timestamp, current_timestamp),
    ('topology-pat-other', 'OTHER', 'ACTIVE', 23, true, current_timestamp, current_timestamp);

insert into hidra_topology_pipeline_appurtenance_type_translation (id, pipeline_appurtenance_type_id, locale, name, description, created_at, updated_at)
values
    ('tr-pat-valve-en', 'topology-pat-valve', 'en', 'Valve', 'Pipeline valve appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-valve-fr', 'topology-pat-valve', 'fr', 'Vanne', 'Pipeline valve appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-valve-ar', 'topology-pat-valve', 'ar', 'صمام', 'Pipeline valve appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-injection-point-en', 'topology-pat-injection-point', 'en', 'Injection point', 'Pipeline injection point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-injection-point-fr', 'topology-pat-injection-point', 'fr', 'Point d''injection', 'Pipeline injection point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-injection-point-ar', 'topology-pat-injection-point', 'ar', 'نقطة حقن', 'Pipeline injection point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-extraction-point-en', 'topology-pat-extraction-point', 'en', 'Extraction point', 'Pipeline extraction point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-extraction-point-fr', 'topology-pat-extraction-point', 'fr', 'Point d''extraction', 'Pipeline extraction point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-extraction-point-ar', 'topology-pat-extraction-point', 'ar', 'نقطة استخراج', 'Pipeline extraction point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-purge-point-en', 'topology-pat-purge-point', 'en', 'Purge point', 'Pipeline purge point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-purge-point-fr', 'topology-pat-purge-point', 'fr', 'Point de purge', 'Pipeline purge point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-purge-point-ar', 'topology-pat-purge-point', 'ar', 'نقطة تطهير', 'Pipeline purge point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-vent-point-en', 'topology-pat-vent-point', 'en', 'Vent point', 'Pipeline vent point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-vent-point-fr', 'topology-pat-vent-point', 'fr', 'Point d''évent', 'Pipeline vent point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-vent-point-ar', 'topology-pat-vent-point', 'ar', 'نقطة تنفيس', 'Pipeline vent point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-drain-point-en', 'topology-pat-drain-point', 'en', 'Drain point', 'Pipeline drain point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-drain-point-fr', 'topology-pat-drain-point', 'fr', 'Point de vidange', 'Pipeline drain point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-drain-point-ar', 'topology-pat-drain-point', 'ar', 'نقطة تصريف', 'Pipeline drain point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-sampling-point-en', 'topology-pat-sampling-point', 'en', 'Sampling point', 'Pipeline sampling point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-sampling-point-fr', 'topology-pat-sampling-point', 'fr', 'Point d''échantillonnage', 'Pipeline sampling point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-sampling-point-ar', 'topology-pat-sampling-point', 'ar', 'نقطة أخذ عينات', 'Pipeline sampling point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-metering-point-en', 'topology-pat-metering-point', 'en', 'Metering point', 'Pipeline metering point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-metering-point-fr', 'topology-pat-metering-point', 'fr', 'Point de comptage', 'Pipeline metering point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-metering-point-ar', 'topology-pat-metering-point', 'ar', 'نقطة قياس', 'Pipeline metering point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-scraper-launcher-en', 'topology-pat-scraper-launcher', 'en', 'Scraper launcher', 'Pipeline scraper launcher appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-scraper-launcher-fr', 'topology-pat-scraper-launcher', 'fr', 'Lanceur de racleur', 'Pipeline scraper launcher appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-scraper-launcher-ar', 'topology-pat-scraper-launcher', 'ar', 'قاذف الكاشطة', 'Pipeline scraper launcher appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-scraper-receiver-en', 'topology-pat-scraper-receiver', 'en', 'Scraper receiver', 'Pipeline scraper receiver appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-scraper-receiver-fr', 'topology-pat-scraper-receiver', 'fr', 'Récepteur de racleur', 'Pipeline scraper receiver appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-scraper-receiver-ar', 'topology-pat-scraper-receiver', 'ar', 'مستقبل الكاشطة', 'Pipeline scraper receiver appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-hot-tap-point-en', 'topology-pat-hot-tap-point', 'en', 'Hot tap point', 'Hot tap point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-hot-tap-point-fr', 'topology-pat-hot-tap-point', 'fr', 'Point de piquage en charge', 'Hot tap point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-hot-tap-point-ar', 'topology-pat-hot-tap-point', 'ar', 'نقطة ربط تحت الضغط', 'Hot tap point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-bypass-point-en', 'topology-pat-bypass-point', 'en', 'Bypass point', 'Pipeline bypass point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-bypass-point-fr', 'topology-pat-bypass-point', 'fr', 'Point de dérivation', 'Pipeline bypass point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-bypass-point-ar', 'topology-pat-bypass-point', 'ar', 'نقطة تجاوز', 'Pipeline bypass point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-connection-point-en', 'topology-pat-connection-point', 'en', 'Connection point', 'Pipeline connection point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-connection-point-fr', 'topology-pat-connection-point', 'fr', 'Point de connexion', 'Pipeline connection point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-connection-point-ar', 'topology-pat-connection-point', 'ar', 'نقطة اتصال', 'Pipeline connection point appurtenance.', current_timestamp, current_timestamp),
    ('tr-pat-other-en', 'topology-pat-other', 'en', 'Other', 'Other pipeline appurtenance type.', current_timestamp, current_timestamp),
    ('tr-pat-other-fr', 'topology-pat-other', 'fr', 'Autre', 'Other pipeline appurtenance type.', current_timestamp, current_timestamp),
    ('tr-pat-other-ar', 'topology-pat-other', 'ar', 'أخرى', 'Other pipeline appurtenance type.', current_timestamp, current_timestamp);

create table hidra_topology_valve_type (
    id varchar(80) not null,
    code varchar(80) not null,
    status varchar(40) not null,
    sort_order integer not null,
    system_defined boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_vt primary key (id),
    constraint uk_ht_vt_code unique (code),
    constraint ck_ht_vt_status check (status in ('ACTIVE', 'INACTIVE')),
    constraint ck_ht_vt_sort_order check (sort_order >= 0),
    constraint ck_ht_vt_updated_at check (updated_at >= created_at)
);

create table hidra_topology_valve_type_translation (
    id varchar(80) not null,
    valve_type_id varchar(80) not null,
    locale varchar(10) not null,
    name varchar(160) not null,
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_vt_tr primary key (id),
    constraint fk_ht_vt_tr_type foreign key (valve_type_id)
        references hidra_topology_valve_type (id),
    constraint uk_ht_vt_tr_locale unique (valve_type_id, locale),
    constraint ck_ht_vt_tr_locale check (length(trim(locale)) > 0),
    constraint ck_ht_vt_tr_name check (length(trim(name)) > 0),
    constraint ck_ht_vt_tr_updated_at check (updated_at >= created_at)
);

create index idx_ht_vt_status
    on hidra_topology_valve_type (status);

create index idx_ht_vt_sort_order
    on hidra_topology_valve_type (sort_order);

create index idx_ht_vt_tr_locale
    on hidra_topology_valve_type_translation (locale);

insert into hidra_topology_valve_type (id, code, status, sort_order, system_defined, created_at, updated_at)
values
    ('topology-vt-block-valve', 'BLOCK_VALVE', 'ACTIVE', 10, true, current_timestamp, current_timestamp),
    ('topology-vt-sectionalizing-valve', 'SECTIONALIZING_VALVE', 'ACTIVE', 11, true, current_timestamp, current_timestamp),
    ('topology-vt-isolation-valve', 'ISOLATION_VALVE', 'ACTIVE', 12, true, current_timestamp, current_timestamp),
    ('topology-vt-shutdown-valve', 'SHUTDOWN_VALVE', 'ACTIVE', 13, true, current_timestamp, current_timestamp),
    ('topology-vt-control-valve', 'CONTROL_VALVE', 'ACTIVE', 14, true, current_timestamp, current_timestamp),
    ('topology-vt-check-valve', 'CHECK_VALVE', 'ACTIVE', 15, true, current_timestamp, current_timestamp),
    ('topology-vt-relief-valve', 'RELIEF_VALVE', 'ACTIVE', 16, true, current_timestamp, current_timestamp),
    ('topology-vt-pressure-regulating-valve', 'PRESSURE_REGULATING_VALVE', 'ACTIVE', 17, true, current_timestamp, current_timestamp),
    ('topology-vt-bypass-valve', 'BYPASS_VALVE', 'ACTIVE', 18, true, current_timestamp, current_timestamp),
    ('topology-vt-drain-valve', 'DRAIN_VALVE', 'ACTIVE', 19, true, current_timestamp, current_timestamp),
    ('topology-vt-vent-valve', 'VENT_VALVE', 'ACTIVE', 20, true, current_timestamp, current_timestamp),
    ('topology-vt-esd-valve', 'ESD_VALVE', 'ACTIVE', 21, true, current_timestamp, current_timestamp),
    ('topology-vt-manual-valve', 'MANUAL_VALVE', 'ACTIVE', 22, true, current_timestamp, current_timestamp),
    ('topology-vt-motorized-valve', 'MOTORIZED_VALVE', 'ACTIVE', 23, true, current_timestamp, current_timestamp),
    ('topology-vt-other', 'OTHER', 'ACTIVE', 24, true, current_timestamp, current_timestamp);

insert into hidra_topology_valve_type_translation (id, valve_type_id, locale, name, description, created_at, updated_at)
values
    ('tr-vt-block-valve-en', 'topology-vt-block-valve', 'en', 'Block valve', 'Valve used to isolate pipeline sections.', current_timestamp, current_timestamp),
    ('tr-vt-block-valve-fr', 'topology-vt-block-valve', 'fr', 'Vanne de sectionnement', 'Valve used to isolate pipeline sections.', current_timestamp, current_timestamp),
    ('tr-vt-block-valve-ar', 'topology-vt-block-valve', 'ar', 'صمام عزل', 'Valve used to isolate pipeline sections.', current_timestamp, current_timestamp),
    ('tr-vt-sectionalizing-valve-en', 'topology-vt-sectionalizing-valve', 'en', 'Sectionalizing valve', 'Valve used to sectionalize pipeline segments.', current_timestamp, current_timestamp),
    ('tr-vt-sectionalizing-valve-fr', 'topology-vt-sectionalizing-valve', 'fr', 'Vanne de tronçonnement', 'Valve used to sectionalize pipeline segments.', current_timestamp, current_timestamp),
    ('tr-vt-sectionalizing-valve-ar', 'topology-vt-sectionalizing-valve', 'ar', 'صمام تقسيم', 'Valve used to sectionalize pipeline segments.', current_timestamp, current_timestamp),
    ('tr-vt-isolation-valve-en', 'topology-vt-isolation-valve', 'en', 'Isolation valve', 'Valve used for isolation.', current_timestamp, current_timestamp),
    ('tr-vt-isolation-valve-fr', 'topology-vt-isolation-valve', 'fr', 'Vanne d''isolement', 'Valve used for isolation.', current_timestamp, current_timestamp),
    ('tr-vt-isolation-valve-ar', 'topology-vt-isolation-valve', 'ar', 'صمام عزل', 'Valve used for isolation.', current_timestamp, current_timestamp),
    ('tr-vt-shutdown-valve-en', 'topology-vt-shutdown-valve', 'en', 'Shutdown valve', 'Valve used for shutdown operations.', current_timestamp, current_timestamp),
    ('tr-vt-shutdown-valve-fr', 'topology-vt-shutdown-valve', 'fr', 'Vanne d''arrêt', 'Valve used for shutdown operations.', current_timestamp, current_timestamp),
    ('tr-vt-shutdown-valve-ar', 'topology-vt-shutdown-valve', 'ar', 'صمام إيقاف', 'Valve used for shutdown operations.', current_timestamp, current_timestamp),
    ('tr-vt-control-valve-en', 'topology-vt-control-valve', 'en', 'Control valve', 'Valve used for control or regulation.', current_timestamp, current_timestamp),
    ('tr-vt-control-valve-fr', 'topology-vt-control-valve', 'fr', 'Vanne de régulation', 'Valve used for control or regulation.', current_timestamp, current_timestamp),
    ('tr-vt-control-valve-ar', 'topology-vt-control-valve', 'ar', 'صمام تحكم', 'Valve used for control or regulation.', current_timestamp, current_timestamp),
    ('tr-vt-check-valve-en', 'topology-vt-check-valve', 'en', 'Check valve', 'Valve preventing reverse flow.', current_timestamp, current_timestamp),
    ('tr-vt-check-valve-fr', 'topology-vt-check-valve', 'fr', 'Clapet anti-retour', 'Valve preventing reverse flow.', current_timestamp, current_timestamp),
    ('tr-vt-check-valve-ar', 'topology-vt-check-valve', 'ar', 'صمام عدم رجوع', 'Valve preventing reverse flow.', current_timestamp, current_timestamp),
    ('tr-vt-relief-valve-en', 'topology-vt-relief-valve', 'en', 'Relief valve', 'Valve used for pressure relief.', current_timestamp, current_timestamp),
    ('tr-vt-relief-valve-fr', 'topology-vt-relief-valve', 'fr', 'Soupape de sûreté', 'Valve used for pressure relief.', current_timestamp, current_timestamp),
    ('tr-vt-relief-valve-ar', 'topology-vt-relief-valve', 'ar', 'صمام تنفيس', 'Valve used for pressure relief.', current_timestamp, current_timestamp),
    ('tr-vt-pressure-regulating-valve-en', 'topology-vt-pressure-regulating-valve', 'en', 'Pressure regulating valve', 'Valve used to regulate pressure.', current_timestamp, current_timestamp),
    ('tr-vt-pressure-regulating-valve-fr', 'topology-vt-pressure-regulating-valve', 'fr', 'Vanne de régulation de pression', 'Valve used to regulate pressure.', current_timestamp, current_timestamp),
    ('tr-vt-pressure-regulating-valve-ar', 'topology-vt-pressure-regulating-valve', 'ar', 'صمام تنظيم الضغط', 'Valve used to regulate pressure.', current_timestamp, current_timestamp),
    ('tr-vt-bypass-valve-en', 'topology-vt-bypass-valve', 'en', 'Bypass valve', 'Valve on a bypass line.', current_timestamp, current_timestamp),
    ('tr-vt-bypass-valve-fr', 'topology-vt-bypass-valve', 'fr', 'Vanne de dérivation', 'Valve on a bypass line.', current_timestamp, current_timestamp),
    ('tr-vt-bypass-valve-ar', 'topology-vt-bypass-valve', 'ar', 'صمام تجاوز', 'Valve on a bypass line.', current_timestamp, current_timestamp),
    ('tr-vt-drain-valve-en', 'topology-vt-drain-valve', 'en', 'Drain valve', 'Valve used for draining.', current_timestamp, current_timestamp),
    ('tr-vt-drain-valve-fr', 'topology-vt-drain-valve', 'fr', 'Vanne de vidange', 'Valve used for draining.', current_timestamp, current_timestamp),
    ('tr-vt-drain-valve-ar', 'topology-vt-drain-valve', 'ar', 'صمام تصريف', 'Valve used for draining.', current_timestamp, current_timestamp),
    ('tr-vt-vent-valve-en', 'topology-vt-vent-valve', 'en', 'Vent valve', 'Valve used for venting.', current_timestamp, current_timestamp),
    ('tr-vt-vent-valve-fr', 'topology-vt-vent-valve', 'fr', 'Vanne d''évent', 'Valve used for venting.', current_timestamp, current_timestamp),
    ('tr-vt-vent-valve-ar', 'topology-vt-vent-valve', 'ar', 'صمام تنفيس', 'Valve used for venting.', current_timestamp, current_timestamp),
    ('tr-vt-esd-valve-en', 'topology-vt-esd-valve', 'en', 'Emergency shutdown valve', 'Emergency shutdown valve.', current_timestamp, current_timestamp),
    ('tr-vt-esd-valve-fr', 'topology-vt-esd-valve', 'fr', 'Vanne d''arrêt d''urgence', 'Emergency shutdown valve.', current_timestamp, current_timestamp),
    ('tr-vt-esd-valve-ar', 'topology-vt-esd-valve', 'ar', 'صمام إيقاف الطوارئ', 'Emergency shutdown valve.', current_timestamp, current_timestamp),
    ('tr-vt-manual-valve-en', 'topology-vt-manual-valve', 'en', 'Manual valve', 'Manually operated valve.', current_timestamp, current_timestamp),
    ('tr-vt-manual-valve-fr', 'topology-vt-manual-valve', 'fr', 'Vanne manuelle', 'Manually operated valve.', current_timestamp, current_timestamp),
    ('tr-vt-manual-valve-ar', 'topology-vt-manual-valve', 'ar', 'صمام يدوي', 'Manually operated valve.', current_timestamp, current_timestamp),
    ('tr-vt-motorized-valve-en', 'topology-vt-motorized-valve', 'en', 'Motorized valve', 'Motorized valve.', current_timestamp, current_timestamp),
    ('tr-vt-motorized-valve-fr', 'topology-vt-motorized-valve', 'fr', 'Vanne motorisée', 'Motorized valve.', current_timestamp, current_timestamp),
    ('tr-vt-motorized-valve-ar', 'topology-vt-motorized-valve', 'ar', 'صمام آلي', 'Motorized valve.', current_timestamp, current_timestamp),
    ('tr-vt-other-en', 'topology-vt-other', 'en', 'Other', 'Other valve type.', current_timestamp, current_timestamp),
    ('tr-vt-other-fr', 'topology-vt-other', 'fr', 'Autre', 'Other valve type.', current_timestamp, current_timestamp),
    ('tr-vt-other-ar', 'topology-vt-other', 'ar', 'أخرى', 'Other valve type.', current_timestamp, current_timestamp);

create table hidra_topology_equipment_type (
    id varchar(80) not null,
    code varchar(80) not null,
    status varchar(40) not null,
    sort_order integer not null,
    system_defined boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_et primary key (id),
    constraint uk_ht_et_code unique (code),
    constraint ck_ht_et_status check (status in ('ACTIVE', 'INACTIVE')),
    constraint ck_ht_et_sort_order check (sort_order >= 0),
    constraint ck_ht_et_updated_at check (updated_at >= created_at)
);

create table hidra_topology_equipment_type_translation (
    id varchar(80) not null,
    equipment_type_id varchar(80) not null,
    locale varchar(10) not null,
    name varchar(160) not null,
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_et_tr primary key (id),
    constraint fk_ht_et_tr_type foreign key (equipment_type_id)
        references hidra_topology_equipment_type (id),
    constraint uk_ht_et_tr_locale unique (equipment_type_id, locale),
    constraint ck_ht_et_tr_locale check (length(trim(locale)) > 0),
    constraint ck_ht_et_tr_name check (length(trim(name)) > 0),
    constraint ck_ht_et_tr_updated_at check (updated_at >= created_at)
);

create index idx_ht_et_status
    on hidra_topology_equipment_type (status);

create index idx_ht_et_sort_order
    on hidra_topology_equipment_type (sort_order);

create index idx_ht_et_tr_locale
    on hidra_topology_equipment_type_translation (locale);

insert into hidra_topology_equipment_type (id, code, status, sort_order, system_defined, created_at, updated_at)
values
    ('topology-et-compressor', 'COMPRESSOR', 'ACTIVE', 10, true, current_timestamp, current_timestamp),
    ('topology-et-pump', 'PUMP', 'ACTIVE', 11, true, current_timestamp, current_timestamp),
    ('topology-et-valve', 'VALVE', 'ACTIVE', 12, true, current_timestamp, current_timestamp),
    ('topology-et-meter', 'METER', 'ACTIVE', 13, true, current_timestamp, current_timestamp),
    ('topology-et-separator', 'SEPARATOR', 'ACTIVE', 14, true, current_timestamp, current_timestamp),
    ('topology-et-scraper-launcher', 'SCRAPER_LAUNCHER', 'ACTIVE', 15, true, current_timestamp, current_timestamp),
    ('topology-et-scraper-receiver', 'SCRAPER_RECEIVER', 'ACTIVE', 16, true, current_timestamp, current_timestamp),
    ('topology-et-actuator', 'ACTUATOR', 'ACTIVE', 17, true, current_timestamp, current_timestamp),
    ('topology-et-control-panel', 'CONTROL_PANEL', 'ACTIVE', 18, true, current_timestamp, current_timestamp),
    ('topology-et-instrumentation', 'INSTRUMENTATION', 'ACTIVE', 19, true, current_timestamp, current_timestamp),
    ('topology-et-other', 'OTHER', 'ACTIVE', 20, true, current_timestamp, current_timestamp);

insert into hidra_topology_equipment_type_translation (id, equipment_type_id, locale, name, description, created_at, updated_at)
values
    ('tr-et-compressor-en', 'topology-et-compressor', 'en', 'Compressor', 'Compressor equipment.', current_timestamp, current_timestamp),
    ('tr-et-compressor-fr', 'topology-et-compressor', 'fr', 'Compresseur', 'Compressor equipment.', current_timestamp, current_timestamp),
    ('tr-et-compressor-ar', 'topology-et-compressor', 'ar', 'ضاغط', 'Compressor equipment.', current_timestamp, current_timestamp),
    ('tr-et-pump-en', 'topology-et-pump', 'en', 'Pump', 'Pump equipment.', current_timestamp, current_timestamp),
    ('tr-et-pump-fr', 'topology-et-pump', 'fr', 'Pompe', 'Pump equipment.', current_timestamp, current_timestamp),
    ('tr-et-pump-ar', 'topology-et-pump', 'ar', 'مضخة', 'Pump equipment.', current_timestamp, current_timestamp),
    ('tr-et-valve-en', 'topology-et-valve', 'en', 'Valve', 'Valve equipment.', current_timestamp, current_timestamp),
    ('tr-et-valve-fr', 'topology-et-valve', 'fr', 'Vanne', 'Valve equipment.', current_timestamp, current_timestamp),
    ('tr-et-valve-ar', 'topology-et-valve', 'ar', 'صمام', 'Valve equipment.', current_timestamp, current_timestamp),
    ('tr-et-meter-en', 'topology-et-meter', 'en', 'Meter', 'Metering equipment.', current_timestamp, current_timestamp),
    ('tr-et-meter-fr', 'topology-et-meter', 'fr', 'Compteur', 'Metering equipment.', current_timestamp, current_timestamp),
    ('tr-et-meter-ar', 'topology-et-meter', 'ar', 'عداد', 'Metering equipment.', current_timestamp, current_timestamp),
    ('tr-et-separator-en', 'topology-et-separator', 'en', 'Separator', 'Separator equipment.', current_timestamp, current_timestamp),
    ('tr-et-separator-fr', 'topology-et-separator', 'fr', 'Séparateur', 'Separator equipment.', current_timestamp, current_timestamp),
    ('tr-et-separator-ar', 'topology-et-separator', 'ar', 'فاصل', 'Separator equipment.', current_timestamp, current_timestamp),
    ('tr-et-scraper-launcher-en', 'topology-et-scraper-launcher', 'en', 'Scraper launcher', 'Scraper launcher equipment.', current_timestamp, current_timestamp),
    ('tr-et-scraper-launcher-fr', 'topology-et-scraper-launcher', 'fr', 'Lanceur de racleur', 'Scraper launcher equipment.', current_timestamp, current_timestamp),
    ('tr-et-scraper-launcher-ar', 'topology-et-scraper-launcher', 'ar', 'قاذف الكاشطة', 'Scraper launcher equipment.', current_timestamp, current_timestamp),
    ('tr-et-scraper-receiver-en', 'topology-et-scraper-receiver', 'en', 'Scraper receiver', 'Scraper receiver equipment.', current_timestamp, current_timestamp),
    ('tr-et-scraper-receiver-fr', 'topology-et-scraper-receiver', 'fr', 'Récepteur de racleur', 'Scraper receiver equipment.', current_timestamp, current_timestamp),
    ('tr-et-scraper-receiver-ar', 'topology-et-scraper-receiver', 'ar', 'مستقبل الكاشطة', 'Scraper receiver equipment.', current_timestamp, current_timestamp),
    ('tr-et-actuator-en', 'topology-et-actuator', 'en', 'Actuator', 'Actuator equipment.', current_timestamp, current_timestamp),
    ('tr-et-actuator-fr', 'topology-et-actuator', 'fr', 'Actionneur', 'Actuator equipment.', current_timestamp, current_timestamp),
    ('tr-et-actuator-ar', 'topology-et-actuator', 'ar', 'مشغل', 'Actuator equipment.', current_timestamp, current_timestamp),
    ('tr-et-control-panel-en', 'topology-et-control-panel', 'en', 'Control panel', 'Control panel equipment.', current_timestamp, current_timestamp),
    ('tr-et-control-panel-fr', 'topology-et-control-panel', 'fr', 'Panneau de contrôle', 'Control panel equipment.', current_timestamp, current_timestamp),
    ('tr-et-control-panel-ar', 'topology-et-control-panel', 'ar', 'لوحة تحكم', 'Control panel equipment.', current_timestamp, current_timestamp),
    ('tr-et-instrumentation-en', 'topology-et-instrumentation', 'en', 'Instrumentation', 'Instrumentation equipment.', current_timestamp, current_timestamp),
    ('tr-et-instrumentation-fr', 'topology-et-instrumentation', 'fr', 'Instrumentation', 'Instrumentation equipment.', current_timestamp, current_timestamp),
    ('tr-et-instrumentation-ar', 'topology-et-instrumentation', 'ar', 'أجهزة قياس وتحكم', 'Instrumentation equipment.', current_timestamp, current_timestamp),
    ('tr-et-other-en', 'topology-et-other', 'en', 'Other', 'Other equipment type.', current_timestamp, current_timestamp),
    ('tr-et-other-fr', 'topology-et-other', 'fr', 'Autre', 'Other equipment type.', current_timestamp, current_timestamp),
    ('tr-et-other-ar', 'topology-et-other', 'ar', 'أخرى', 'Other equipment type.', current_timestamp, current_timestamp);

create table hidra_topology_connection_type (
    id varchar(80) not null,
    code varchar(80) not null,
    status varchar(40) not null,
    sort_order integer not null,
    system_defined boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_ct primary key (id),
    constraint uk_ht_ct_code unique (code),
    constraint ck_ht_ct_status check (status in ('ACTIVE', 'INACTIVE')),
    constraint ck_ht_ct_sort_order check (sort_order >= 0),
    constraint ck_ht_ct_updated_at check (updated_at >= created_at)
);

create table hidra_topology_connection_type_translation (
    id varchar(80) not null,
    connection_type_id varchar(80) not null,
    locale varchar(10) not null,
    name varchar(160) not null,
    description varchar(500),
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_ht_ct_tr primary key (id),
    constraint fk_ht_ct_tr_type foreign key (connection_type_id)
        references hidra_topology_connection_type (id),
    constraint uk_ht_ct_tr_locale unique (connection_type_id, locale),
    constraint ck_ht_ct_tr_locale check (length(trim(locale)) > 0),
    constraint ck_ht_ct_tr_name check (length(trim(name)) > 0),
    constraint ck_ht_ct_tr_updated_at check (updated_at >= created_at)
);

create index idx_ht_ct_status
    on hidra_topology_connection_type (status);

create index idx_ht_ct_sort_order
    on hidra_topology_connection_type (sort_order);

create index idx_ht_ct_tr_locale
    on hidra_topology_connection_type_translation (locale);

insert into hidra_topology_connection_type (id, code, status, sort_order, system_defined, created_at, updated_at)
values
    ('topology-ct-pipeline-segment', 'PIPELINE_SEGMENT', 'ACTIVE', 10, true, current_timestamp, current_timestamp),
    ('topology-ct-facility-internal', 'FACILITY_INTERNAL', 'ACTIVE', 11, true, current_timestamp, current_timestamp),
    ('topology-ct-valve-connection', 'VALVE_CONNECTION', 'ACTIVE', 12, true, current_timestamp, current_timestamp),
    ('topology-ct-metering-connection', 'METERING_CONNECTION', 'ACTIVE', 13, true, current_timestamp, current_timestamp),
    ('topology-ct-junction-connection', 'JUNCTION_CONNECTION', 'ACTIVE', 14, true, current_timestamp, current_timestamp),
    ('topology-ct-appurtenance-connection', 'APPURTENANCE_CONNECTION', 'ACTIVE', 15, true, current_timestamp, current_timestamp),
    ('topology-ct-other', 'OTHER', 'ACTIVE', 16, true, current_timestamp, current_timestamp);

insert into hidra_topology_connection_type_translation (id, connection_type_id, locale, name, description, created_at, updated_at)
values
    ('tr-ct-pipeline-segment-en', 'topology-ct-pipeline-segment', 'en', 'Pipeline segment', 'Connection represented by a pipeline segment.', current_timestamp, current_timestamp),
    ('tr-ct-pipeline-segment-fr', 'topology-ct-pipeline-segment', 'fr', 'Segment de pipeline', 'Connection represented by a pipeline segment.', current_timestamp, current_timestamp),
    ('tr-ct-pipeline-segment-ar', 'topology-ct-pipeline-segment', 'ar', 'مقطع خط أنابيب', 'Connection represented by a pipeline segment.', current_timestamp, current_timestamp),
    ('tr-ct-facility-internal-en', 'topology-ct-facility-internal', 'en', 'Facility internal connection', 'Internal facility topology connection.', current_timestamp, current_timestamp),
    ('tr-ct-facility-internal-fr', 'topology-ct-facility-internal', 'fr', 'Connexion interne d''installation', 'Internal facility topology connection.', current_timestamp, current_timestamp),
    ('tr-ct-facility-internal-ar', 'topology-ct-facility-internal', 'ar', 'اتصال داخلي بالمنشأة', 'Internal facility topology connection.', current_timestamp, current_timestamp),
    ('tr-ct-valve-connection-en', 'topology-ct-valve-connection', 'en', 'Valve connection', 'Connection associated with a valve.', current_timestamp, current_timestamp),
    ('tr-ct-valve-connection-fr', 'topology-ct-valve-connection', 'fr', 'Connexion de vanne', 'Connection associated with a valve.', current_timestamp, current_timestamp),
    ('tr-ct-valve-connection-ar', 'topology-ct-valve-connection', 'ar', 'اتصال صمام', 'Connection associated with a valve.', current_timestamp, current_timestamp),
    ('tr-ct-metering-connection-en', 'topology-ct-metering-connection', 'en', 'Metering connection', 'Connection associated with metering.', current_timestamp, current_timestamp),
    ('tr-ct-metering-connection-fr', 'topology-ct-metering-connection', 'fr', 'Connexion de comptage', 'Connection associated with metering.', current_timestamp, current_timestamp),
    ('tr-ct-metering-connection-ar', 'topology-ct-metering-connection', 'ar', 'اتصال قياس', 'Connection associated with metering.', current_timestamp, current_timestamp),
    ('tr-ct-junction-connection-en', 'topology-ct-junction-connection', 'en', 'Junction connection', 'Connection associated with a junction.', current_timestamp, current_timestamp),
    ('tr-ct-junction-connection-fr', 'topology-ct-junction-connection', 'fr', 'Connexion de jonction', 'Connection associated with a junction.', current_timestamp, current_timestamp),
    ('tr-ct-junction-connection-ar', 'topology-ct-junction-connection', 'ar', 'اتصال وصلة', 'Connection associated with a junction.', current_timestamp, current_timestamp),
    ('tr-ct-appurtenance-connection-en', 'topology-ct-appurtenance-connection', 'en', 'Appurtenance connection', 'Connection associated with a pipeline appurtenance.', current_timestamp, current_timestamp),
    ('tr-ct-appurtenance-connection-fr', 'topology-ct-appurtenance-connection', 'fr', 'Connexion d''accessoire', 'Connection associated with a pipeline appurtenance.', current_timestamp, current_timestamp),
    ('tr-ct-appurtenance-connection-ar', 'topology-ct-appurtenance-connection', 'ar', 'اتصال ملحق', 'Connection associated with a pipeline appurtenance.', current_timestamp, current_timestamp),
    ('tr-ct-other-en', 'topology-ct-other', 'en', 'Other', 'Other topology connection type.', current_timestamp, current_timestamp),
    ('tr-ct-other-fr', 'topology-ct-other', 'fr', 'Autre', 'Other topology connection type.', current_timestamp, current_timestamp),
    ('tr-ct-other-ar', 'topology-ct-other', 'ar', 'أخرى', 'Other topology connection type.', current_timestamp, current_timestamp);
