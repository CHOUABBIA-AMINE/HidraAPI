/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPhysicalInputPayload
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Binds immutable gas scalar payloads to exact manifest sources and coherent physical references.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.Origin;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentInput.Kind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Knowledge;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Quantity;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.TargetKind;
import dz.sh.hidra.modules.simulation.domain.value.SimulationInputMode;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Schema 1 scalar data coherence only: neither content integrity nor numerical/operational readiness. */
public record SimulationPhysicalInputPayload(
        SimulationInputManifest manifest,
        SimulationConnectedNetworkInput network,
        SimulationGasFluidInput fluid,
        SimulationInitialStateInput initialState,
        SimulationBoundaryScheduleInput boundarySchedule
) {
    public SimulationPhysicalInputPayload {
        if (manifest == null || network == null || fluid == null || initialState == null) {
            throw new InvalidSimulationValueException("Manifest, network, fluid and initial state are required.");
        }
        if (manifest.schemaVersion() != 1) {
            throw new InvalidSimulationValueException("Physical assembly supports exactly schema version one.");
        }
        var sources = new EnumMap<SourceKind, SimulationInputSourceVersion>(SourceKind.class);
        for (var source : manifest.sources()) {
            sources.put(source.kind(), source);
        }
        var equipmentModel = network.equipmentModel();
        bind(sources, network.sourceVersion());
        bind(sources, fluid.sourceVersion());
        bind(sources, equipmentModel.sourceVersion());
        bind(sources, initialState.sourceVersion());
        if (!network.scopeType().equals(manifest.scopeType()) || !network.scopeId().equals(manifest.scopeId())
                || !fluid.productReference().equals(manifest.productReference())
                || !initialState.stateAt().equals(manifest.stateAt())) {
            throw new InvalidSimulationValueException("Payload scope, product and state time must match the manifest.");
        }
        for (var curve : equipmentModel.compressorCurves()) {
            var source = curve.sourceVersion();
            if (!curve.fluidInputId().equals(fluid.id()) || !curve.fluidRevisionId().equals(fluid.sourceVersion().revisionId())
                    || !source.effectiveAt(manifest.stateAt()) || source.recordedAt().isAfter(manifest.capturedAt())
                    || source.recordedAt().isAfter(equipmentModel.sourceVersion().recordedAt())) {
                throw new InvalidSimulationValueException("Every curve must select the exact fluid and coherent frozen source revision.");
            }
            propagateSynthetic(source.origin() == Origin.SYNTHETIC, equipmentModel.sourceVersion());
        }
        var nodeIds = new HashSet<String>();
        var pipeIds = new HashSet<String>();
        var equipmentById = new HashMap<String, SimulationEquipmentInput>();
        network.nodes().forEach(node -> nodeIds.add(node.id()));
        network.pipeSegments().forEach(pipe -> pipeIds.add(pipe.id()));
        equipmentModel.equipment().forEach(item -> equipmentById.put(item.id(), item));
        var quantities = new HashMap<QuantityKey, SimulationStateQuantityInput>();
        for (var quantity : initialState.quantities()) {
            reference(quantity.targetKind(), quantity.targetId(), quantity.quantity(), nodeIds, pipeIds, equipmentById);
            quantities.put(new QuantityKey(quantity.targetKind(), quantity.targetId(), quantity.quantity()), quantity);
            propagateSynthetic(quantity.knowledge() == Knowledge.SYNTHETIC, initialState.sourceVersion());
            if (quantity.knowledge() == Knowledge.MEASURED && manifest.measurementWatermark() != null
                    && quantity.valueAt().isAfter(manifest.measurementWatermark())) {
                throw new InvalidSimulationValueException("Measured initial value must not be later than declared measurement watermark.");
            }
        }
        for (var id : nodeIds) {
            known(quantities, TargetKind.NODE, id, Quantity.PRESSURE_PASCALS_ABSOLUTE);
            known(quantities, TargetKind.NODE, id, Quantity.TEMPERATURE_KELVIN);
        }
        for (var id : pipeIds) {
            known(quantities, TargetKind.PIPE, id, Quantity.PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND);
            if (manifest.mode() == SimulationInputMode.TRANSIENT) {
                known(quantities, TargetKind.PIPE, id, Quantity.PRESSURE_PASCALS_ABSOLUTE);
                known(quantities, TargetKind.PIPE, id, Quantity.TEMPERATURE_KELVIN);
            }
        }
        for (var item : equipmentById.values()) {
            known(quantities, TargetKind.EQUIPMENT, item.id(), item.kind() == Kind.COMPRESSOR
                    ? Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE : Quantity.VALVE_OPENING_FRACTION);
        }
        if (manifest.mode() == SimulationInputMode.STEADY_STATE) {
            if (boundarySchedule != null) {
                throw new InvalidSimulationValueException("Steady-state payload must not contain a transient boundary schedule.");
            }
        } else {
            if (boundarySchedule == null) {
                throw new InvalidSimulationValueException("Transient payload requires a boundary schedule.");
            }
            bind(sources, boundarySchedule.sourceVersion());
            if (!boundarySchedule.startsAt().equals(manifest.stateAt()) || !boundarySchedule.endsAt().equals(manifest.horizonEnd())) {
                throw new InvalidSimulationValueException("Transient schedule must match the manifest state and horizon.");
            }
            for (var series : boundarySchedule.series()) {
                reference(series.targetKind(), series.targetId(), series.quantity(), nodeIds, pipeIds, equipmentById);
                known(quantities, series.targetKind(), series.targetId(), series.quantity());
                for (var point : series.points()) {
                    propagateSynthetic(point.origin() == Origin.SYNTHETIC, boundarySchedule.sourceVersion());
                }
            }
        }
    }

    /** Declared synthetic provenance; never a physics-readiness assessment. */
    public boolean synthetic() {
        return manifest.synthetic();
    }

    private static void bind(Map<SourceKind, SimulationInputSourceVersion> sources, SimulationInputSourceVersion selected) {
        if (!selected.equals(sources.get(selected.kind()))) {
            throw new InvalidSimulationValueException("Payload must match the complete manifest source version: " + selected.kind());
        }
    }

    private static void propagateSynthetic(boolean nestedSynthetic, SimulationInputSourceVersion parent) {
        if (nestedSynthetic && parent.origin() != Origin.SYNTHETIC) {
            throw new InvalidSimulationValueException("Synthetic nested data requires a synthetic parent source declaration.");
        }
    }

    private static void reference(TargetKind target, String id, Quantity quantity, Set<String> nodes, Set<String> pipes,
            Map<String, SimulationEquipmentInput> equipment) {
        boolean exists = switch (target) {
            case NODE -> nodes.contains(id);
            case PIPE -> pipes.contains(id);
            case EQUIPMENT -> equipment.containsKey(id);
        };
        if (!exists) {
            throw new InvalidSimulationValueException("Quantity target must exist in the selected physical namespace.");
        }
        if (target == TargetKind.EQUIPMENT) {
            var kind = equipment.get(id).kind();
            if ((quantity == Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE && kind != Kind.COMPRESSOR)
                    || (quantity == Quantity.VALVE_OPENING_FRACTION && kind != Kind.VALVE)) {
                throw new InvalidSimulationValueException("Equipment quantity must match the selected equipment kind.");
            }
        }
    }

    private static void known(Map<QuantityKey, SimulationStateQuantityInput> quantities, TargetKind target, String id, Quantity quantity) {
        var value = quantities.get(new QuantityKey(target, id, quantity));
        if (value == null || value.knowledge() == Knowledge.UNKNOWN) {
            throw new InvalidSimulationValueException("Required scalar initial quantity must be present and known: " + quantity);
        }
    }

    private record QuantityKey(TargetKind targetKind, String targetId, Quantity quantity) { }
}
