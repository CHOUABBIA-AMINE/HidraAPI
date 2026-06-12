/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Topology database table constants.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

public final class TopologyPersistence {
    public static final String PIPELINE_SYSTEM_TABLE = "hidra_topology_pipeline_system";
    public static final String PIPELINE_TABLE = "hidra_topology_pipeline";
    public static final String PIPELINE_SEGMENT_TABLE = "hidra_topology_pipeline_segment";
    public static final String TOPOLOGY_NODE_TABLE = "hidra_topology_node";
    public static final String TOPOLOGY_CONNECTION_TABLE = "hidra_topology_connection";
    public static final String FACILITY_TABLE = "hidra_topology_facility";
    public static final String FACILITY_TYPE_TABLE = "hidra_topology_facility_type";
    public static final String FACILITY_TYPE_VERSION_TABLE = "hidra_topology_facility_type_version";
    public static final String FACILITY_ATTRIBUTE_DEFINITION_TABLE = "hidra_topology_facility_attribute_definition";
    public static final String FACILITY_ATTRIBUTE_VALUE_TABLE = "hidra_topology_facility_attribute_value";
    public static final String PIPELINE_SYSTEM_FACILITY_TABLE = "hidra_topology_pipeline_system_facility";
    public static final String FACILITY_NODE_BINDING_TABLE = "hidra_topology_facility_node_binding";
    public static final String EQUIPMENT_TABLE = "hidra_topology_equipment";
    public static final String EQUIPMENT_TYPE_TABLE = "hidra_topology_equipment_type";
    public static final String EQUIPMENT_TYPE_VERSION_TABLE = "hidra_topology_equipment_type_version";
    public static final String EQUIPMENT_ATTRIBUTE_DEFINITION_TABLE = "hidra_topology_equipment_attribute_definition";
    public static final String EQUIPMENT_ATTRIBUTE_VALUE_TABLE = "hidra_topology_equipment_attribute_value";
    public static final String MEASUREMENT_LOCATION_TABLE = "hidra_topology_measurement_location";
    public static final String TOPOLOGY_SNAPSHOT_TABLE = "hidra_topology_snapshot";
    private TopologyPersistence() { throw new UnsupportedOperationException("Utility class must not be instantiated."); }
}
