/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.value
 *
 * @Description : Defines SimulationRunStatus values.
 *
 */
package dz.sh.hidra.modules.simulation.domain.value;

/**
 * Defines SimulationRunStatus values.
 */
public enum SimulationRunStatus {
    QUEUED, RUNNING, COMPLETED, FAILED, CANCELLED
}
