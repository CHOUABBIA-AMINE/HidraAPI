/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.configuration
 *
 * @Description : Simulation infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.configuration;

/**
 * Simulation infrastructure configuration.
 */
public record SimulationModuleConfiguration(
        boolean decisionSupportOnly,
        boolean fieldActuationBlocked,
        boolean topologyWritesBlocked,
        boolean workflowRequiredForAdoption
) {

    public static SimulationModuleConfiguration defaults() {
        return new SimulationModuleConfiguration(true, true, true, true);
    }
}
