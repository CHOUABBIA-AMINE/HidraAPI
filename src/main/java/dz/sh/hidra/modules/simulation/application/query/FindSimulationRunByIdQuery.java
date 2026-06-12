/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindSimulationRunByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.query
 *
 * @Description : Query to find simulation run by ID.
 *
 */
package dz.sh.hidra.modules.simulation.application.query;

/**
 * Query to find simulation run by ID.
 */
public record FindSimulationRunByIdQuery(String runId) {
}
