/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Defines topology module constants.
 *
 */
package dz.sh.hidra.modules.topology;

public final class TopologyModule {
    public static final String MODULE_NAME = "topology";
    public static final String TABLE_PREFIX = "hidra_topology_";
    private TopologyModule() { throw new UnsupportedOperationException("Utility class must not be instantiated."); }
}
