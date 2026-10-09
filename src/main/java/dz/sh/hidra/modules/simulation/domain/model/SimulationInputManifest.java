/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputManifest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Preserves coherent immutable source selections for one calculation input.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.Origin;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import dz.sh.hidra.modules.simulation.domain.value.SimulationInputMode;
import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Validates reference metadata only, not physical completeness, source trust or solver readiness.
 * Initial source versions are selected at stateAt; a transient schedule carries future inputs.
 */
public record SimulationInputManifest(
        String id,
        int schemaVersion,
        String productReference,
        String scopeType,
        String scopeId,
        SimulationInputMode mode,
        Instant stateAt,
        Instant capturedAt,
        Instant measurementWatermark,
        Instant horizonEnd,
        List<SimulationInputSourceVersion> sources
) {
    public SimulationInputManifest {
        id = required(id, "Manifest identity");
        productReference = required(productReference, "Product reference");
        scopeType = required(scopeType, "Scope type");
        scopeId = required(scopeId, "Scope identity");
        if (!Set.of("PIPELINE_SYSTEM", "PIPELINE", "SEGMENT_GROUP", "FACILITY_NETWORK").contains(scopeType)) {
            throw new InvalidSimulationValueException("Unsupported manifest scope type: " + scopeType);
        }
        if (schemaVersion <= 0) {
            throw new InvalidSimulationValueException("Manifest schema version must be positive.");
        }
        if (mode == null || stateAt == null || capturedAt == null) {
            throw new InvalidSimulationValueException("Mode, initial state and capture time are required.");
        }
        if (stateAt.isAfter(capturedAt)) {
            throw new InvalidSimulationValueException("Initial state must not be after capture.");
        }
        if (measurementWatermark != null && measurementWatermark.isAfter(stateAt)) {
            throw new InvalidSimulationValueException("Measurement watermark must not be after initial state.");
        }
        if (sources == null || sources.stream().anyMatch(source -> source == null)) {
            throw new InvalidSimulationValueException("Source versions must not be null or contain null entries.");
        }
        sources = List.copyOf(sources);
        EnumSet<SourceKind> kinds = EnumSet.noneOf(SourceKind.class);
        for (SimulationInputSourceVersion source : sources) {
            if (!kinds.add(source.kind())) {
                throw new InvalidSimulationValueException("Duplicate source kind: " + source.kind());
            }
            if (!source.effectiveAt(stateAt)) {
                throw new InvalidSimulationValueException("Source version must be effective at initial state: " + source.kind());
            }
            if (source.recordedAt().isAfter(capturedAt)) {
                throw new InvalidSimulationValueException("Source version must be recorded by capture: " + source.kind());
            }
        }
        EnumSet<SourceKind> requiredKinds = EnumSet.of(SourceKind.TOPOLOGY_CONFIGURATION,
                SourceKind.FLUID_MODEL, SourceKind.EQUIPMENT_PARAMETERS, SourceKind.OPERATING_STATE);
        if (mode == SimulationInputMode.TRANSIENT) {
            requiredKinds.add(SourceKind.BOUNDARY_SCHEDULE);
            if (horizonEnd == null || !horizonEnd.isAfter(stateAt)) {
                throw new InvalidSimulationValueException("Transient horizon must end after initial state.");
            }
        } else if (horizonEnd != null) {
            throw new InvalidSimulationValueException("Steady-state input must not have a transient horizon.");
        }
        if (!kinds.equals(requiredKinds)) {
            throw new InvalidSimulationValueException("Source kinds must match the calculation mode: " + requiredKinds);
        }
    }

    public boolean synthetic() {
        return sources.stream().anyMatch(source -> source.origin() == Origin.SYNTHETIC);
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }
}
