/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentModelInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Pins immutable equipment configuration and resolves contained compressor curve revisions.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/** Local reference integrity only; whole-network and source-time coherence require later assembly. */
public record SimulationEquipmentModelInput(
        String id,
        SimulationInputSourceVersion sourceVersion,
        List<SimulationEquipmentInput> equipment,
        List<SimulationCompressorCurveInput> compressorCurves
) {
    public SimulationEquipmentModelInput {
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("Equipment model identity must not be blank.");
        }
        id = id.trim();
        if (sourceVersion == null || sourceVersion.kind() != SourceKind.EQUIPMENT_PARAMETERS) {
            throw new InvalidSimulationValueException("Equipment model requires an EQUIPMENT_PARAMETERS source version.");
        }
        equipment = immutable(equipment, "Equipment");
        compressorCurves = immutable(compressorCurves, "Compressor curves");
        if (equipment.isEmpty() && !compressorCurves.isEmpty()) {
            throw new InvalidSimulationValueException("An empty equipment model must not contain curves.");
        }
        var curvesById = new HashMap<String, SimulationCompressorCurveInput>();
        for (var curve : compressorCurves) {
            if (curvesById.putIfAbsent(curve.id(), curve) != null) {
                throw new InvalidSimulationValueException("Duplicate compressor curve identity.");
            }
        }
        var equipmentIds = new HashSet<String>();
        for (var item : equipment) {
            if (!equipmentIds.add(item.id())) {
                throw new InvalidSimulationValueException("Duplicate equipment identity.");
            }
            if (item.kind() == SimulationEquipmentInput.Kind.COMPRESSOR) {
                var configuration = item.compressor();
                var curve = curvesById.get(configuration.curveId());
                if (curve == null || !curve.sourceVersion().revisionId().equals(configuration.curveRevisionId())) {
                    throw new InvalidSimulationValueException("Compressor must resolve its exact contained curve revision.");
                }
            }
        }
    }

    private static <T> List<T> immutable(List<T> values, String field) {
        if (values == null || values.stream().anyMatch(value -> value == null)) {
            throw new InvalidSimulationValueException(field + " must be present without null entries.");
        }
        return List.copyOf(values);
    }
}
