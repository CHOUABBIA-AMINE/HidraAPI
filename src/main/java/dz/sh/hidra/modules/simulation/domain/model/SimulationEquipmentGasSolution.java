/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentGasSolution
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Returns typed synthetic equipment mass flows and residual/power diagnostics.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.util.Map;

/** Equipment flow never masquerades as an actual pipe flow. */
public record SimulationEquipmentGasSolution(
        SteadyStateGasSolution pipeSolution,
        Map<String,Double> equipmentMassFlowsKilogramsPerSecond,
        Map<String,Double> compressorLogPressureResiduals,
        Map<String,Double> valveFlowResidualsKilogramsPerSecond,
        Map<String,Double> compressorShaftPowerWatts) {
    public SimulationEquipmentGasSolution {
        if(pipeSolution==null)throw new IllegalArgumentException("Pipe result required.");
        equipmentMassFlowsKilogramsPerSecond=checked(equipmentMassFlowsKilogramsPerSecond);
        compressorLogPressureResiduals=checked(compressorLogPressureResiduals);
        valveFlowResidualsKilogramsPerSecond=checked(valveFlowResidualsKilogramsPerSecond);
        compressorShaftPowerWatts=checked(compressorShaftPowerWatts);
    }
    private static Map<String,Double> checked(Map<String,Double> source) {
        var result=Map.copyOf(source);
        if(result.entrySet().stream().anyMatch(e->e.getKey().isBlank()||!Double.isFinite(e.getValue())))
            throw new IllegalArgumentException("Finite explicit equipment diagnostics required.");
        return result;
    }
}
