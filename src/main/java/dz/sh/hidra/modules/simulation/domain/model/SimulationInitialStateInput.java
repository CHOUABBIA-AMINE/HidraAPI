/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInitialStateInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Pins immutable initial quantities to an effective operating-state revision.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Knowledge;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Quantity;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.TargetKind;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

/** Partial and unknown data remain explicit; no solver-readiness claim is made. */
public record SimulationInitialStateInput(
        String id,
        SimulationInputSourceVersion sourceVersion,
        Instant stateAt,
        List<SimulationStateQuantityInput> quantities
) {
    public SimulationInitialStateInput {
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("Initial state identity must not be blank.");
        }
        id = id.trim();
        if (sourceVersion == null || sourceVersion.kind() != SourceKind.OPERATING_STATE || stateAt == null) {
            throw new InvalidSimulationValueException("Initial state requires an OPERATING_STATE source and state time.");
        }
        if (!sourceVersion.effectiveAt(stateAt) || stateAt.isAfter(sourceVersion.recordedAt())) {
            throw new InvalidSimulationValueException("State time must select an effective revision already recorded for that state.");
        }
        if (quantities == null || quantities.isEmpty() || quantities.stream().anyMatch(value -> value == null)) {
            throw new InvalidSimulationValueException("Initial quantities must be nonempty without null entries.");
        }
        quantities = List.copyOf(quantities);
        var keys = new HashSet<QuantityKey>();
        for (var quantity : quantities) {
            if (!keys.add(new QuantityKey(quantity.targetKind(), quantity.targetId(), quantity.quantity()))) {
                throw new InvalidSimulationValueException("Duplicate initial target/quantity key.");
            }
            if (quantity.knowledge() != Knowledge.UNKNOWN
                    && (quantity.valueAt().isAfter(stateAt)
                    || quantity.evidenceRecordedAt().isAfter(sourceVersion.recordedAt()))) {
                throw new InvalidSimulationValueException("Known initial values and evidence must precede state and revision times.");
            }
        }
    }

    private record QuantityKey(TargetKind targetKind, String targetId, Quantity quantity) { }
}
