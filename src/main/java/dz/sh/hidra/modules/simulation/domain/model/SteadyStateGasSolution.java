/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SteadyStateGasSolution
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Returns deterministic synthetic steady gas network results and residuals.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.util.Map;

/** Only the SYNTHETIC_IDEAL_GAS_TREE reference formulation is supported. */
public record SteadyStateGasSolution(
        boolean converged,
        String status,
        Map<String, Double> nodePressurePascalsAbsolute,
        Map<String, Double> pipeMassFlowKilogramsPerSecond,
        Map<String, Double> nodeMassResidualKilogramsPerSecond,
        Map<String, Double> pipePressureSquaredResidualPascalsSquared,
        int iterations
) {
    public SteadyStateGasSolution {
        if (status == null || status.isBlank() || iterations < 0) {
            throw new IllegalArgumentException("Status and nonnegative iteration count required.");
        }
        nodePressurePascalsAbsolute = Map.copyOf(nodePressurePascalsAbsolute);
        pipeMassFlowKilogramsPerSecond = Map.copyOf(pipeMassFlowKilogramsPerSecond);
        nodeMassResidualKilogramsPerSecond = Map.copyOf(nodeMassResidualKilogramsPerSecond);
        pipePressureSquaredResidualPascalsSquared = Map.copyOf(pipePressureSquaredResidualPascalsSquared);
    }
}
