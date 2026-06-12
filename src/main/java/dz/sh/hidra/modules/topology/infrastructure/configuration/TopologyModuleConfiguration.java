/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.configuration
 *
 * @Description : Topology infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.configuration;

public record TopologyModuleConfiguration(boolean graphProjectionEnabled, boolean topologySnapshotEnabled, boolean visualizationEnabled) { public static TopologyModuleConfiguration defaults() { return new TopologyModuleConfiguration(true, true, true); } }
