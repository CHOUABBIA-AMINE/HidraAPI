/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation
 *
 * @Description : Defines simulation module constants.
 *
 */
package dz.sh.hidra.modules.simulation;

/**
 * Simulation module constants.
 */
public final class SimulationModule {

    public static final String MODULE_NAME = "simulation";
    public static final String TABLE_PREFIX = "hidra_simulation_";

    private SimulationModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
