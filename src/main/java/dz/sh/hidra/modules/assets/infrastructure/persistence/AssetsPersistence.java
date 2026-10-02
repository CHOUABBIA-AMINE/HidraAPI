/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence
 *
 * @Description : Assets database table constants.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence;

/**
 * Assets database table constants.
 */
public final class AssetsPersistence {

    public static final String MAINTAINABLE_ASSET_TABLE = "hidra_asset_maintainable_asset";
    public static final String ASSET_TYPE_TABLE = "hidra_asset_type";
    public static final String ASSET_TYPE_TRANSLATION_TABLE = "hidra_asset_type_translation";
    public static final String ASSET_TECHNICAL_ATTRIBUTE_DEFINITION_TABLE = "hidra_asset_technical_attribute_definition";
    public static final String ASSET_TECHNICAL_ATTRIBUTE_VALUE_TABLE = "hidra_asset_technical_attribute_value";
    public static final String ASSET_INSTALLATION_TABLE = "hidra_asset_installation";
    public static final String ASSET_MANUFACTURER_REFERENCE_TABLE = "hidra_asset_manufacturer_reference";
    public static final String ASSET_MODEL_TABLE = "hidra_asset_model";
    public static final String ASSET_SERIAL_IDENTITY_TABLE = "hidra_asset_serial_identity";
    public static final String ASSET_LIFECYCLE_EVENT_TABLE = "hidra_asset_lifecycle_event";
    public static final String MAINTENANCE_STRATEGY_TABLE = "hidra_asset_maintenance_strategy";
    public static final String MAINTENANCE_PLAN_TABLE = "hidra_asset_maintenance_plan";
    public static final String MAINTENANCE_TASK_TEMPLATE_TABLE = "hidra_asset_maintenance_task_template";
    public static final String MAINTENANCE_WORK_ORDER_TABLE = "hidra_asset_maintenance_work_order";
    public static final String MAINTENANCE_WORK_ORDER_TASK_TABLE = "hidra_asset_maintenance_work_order_task";
    public static final String MAINTENANCE_EXECUTION_RECORD_TABLE = "hidra_asset_maintenance_execution_record";
    public static final String SPARE_PART_TABLE = "hidra_asset_spare_part";
    public static final String ASSET_SPARE_PART_COMPATIBILITY_TABLE = "hidra_asset_spare_part_compatibility";
    public static final String ASSET_DOCUMENT_REFERENCE_TABLE = "hidra_asset_document_reference";
    public static final String ASSET_WARRANTY_TABLE = "hidra_asset_warranty";
    public static final String ASSET_SERVICE_CONTRACT_REFERENCE_TABLE = "hidra_asset_service_contract_reference";
    public static final String ASSET_CONDITION_RECORD_TABLE = "hidra_asset_condition_record";
    public static final String ASSET_METER_READING_REFERENCE_TABLE = "hidra_asset_meter_reading_reference";
    public static final String ASSET_CATALOG_ENTRY_TABLE = "hidra_asset_catalog_entry";
    public static final String ASSET_CATALOG_TRANSLATION_TABLE = "hidra_asset_catalog_translation";

    private AssetsPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
