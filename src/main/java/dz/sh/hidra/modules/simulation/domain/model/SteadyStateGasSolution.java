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

/** Synthetic ideal-gas reference results; no field qualification or production EOS. */
public record SteadyStateGasSolution(
        boolean converged,
        String status,
        Map<String, Double> nodePressurePascalsAbsolute,
        Map<String, Double> pipeMassFlowKilogramsPerSecond,
        Map<String, Double> nodeMassResidualKilogramsPerSecond,
        Map<String, Double> pipePressureSquaredResidualPascalsSquared,
        int iterations,
        Map<String, Double> pressureBoundaryInjectionKilogramsPerSecond
) {
    public SteadyStateGasSolution {
        if (status == null || status.isBlank() || iterations < 0) {
            throw new IllegalArgumentException("Status and nonnegative iteration count required.");
        }
        nodePressurePascalsAbsolute = immutableFinite(nodePressurePascalsAbsolute);
        pipeMassFlowKilogramsPerSecond = immutableFinite(pipeMassFlowKilogramsPerSecond);
        nodeMassResidualKilogramsPerSecond = immutableFinite(nodeMassResidualKilogramsPerSecond);
        pipePressureSquaredResidualPascalsSquared = immutableFinite(pipePressureSquaredResidualPascalsSquared);
        pressureBoundaryInjectionKilogramsPerSecond = immutableFinite(pressureBoundaryInjectionKilogramsPerSecond);
        if (converged && (nodePressurePascalsAbsolute.isEmpty()
                || nodePressurePascalsAbsolute.values().stream().anyMatch(p -> p <= 0))) {
            throw new IllegalArgumentException("Convergence requires finite positive pressures.");
        }
    }

    /** Source-compatible constructor for the historical tree reference. */
    public SteadyStateGasSolution(boolean converged, String status, Map<String, Double> pressures,
            Map<String, Double> flows, Map<String, Double> massResiduals,
            Map<String, Double> momentumResiduals, int iterations) {
        this(converged, status, pressures, flows, massResiduals, momentumResiduals, iterations, Map.of());
    }

    private static Map<String, Double> immutableFinite(Map<String, Double> values) {
        var copy = Map.copyOf(values);
        if (copy.entrySet().stream().anyMatch(e -> e.getKey().isBlank() || !Double.isFinite(e.getValue()))) {
            throw new IllegalArgumentException("Result identities and values must be finite and explicit.");
        }
        return copy;
    }
}
