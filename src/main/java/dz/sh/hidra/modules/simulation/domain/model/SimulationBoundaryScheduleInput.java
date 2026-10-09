/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationBoundaryScheduleInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Pins immutable boundary series with explicit horizon anchors to a selected revision.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Quantity;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.TargetKind;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

/** Closed data interval includes an endpoint anchor; solver/capture completeness is a later gate. */
public record SimulationBoundaryScheduleInput(
        String id,
        SimulationInputSourceVersion sourceVersion,
        Instant startsAt,
        Instant endsAt,
        List<SimulationBoundarySeriesInput> series
) {
    public SimulationBoundaryScheduleInput {
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("Boundary schedule identity must not be blank.");
        }
        id = id.trim();
        if (sourceVersion == null || sourceVersion.kind() != SourceKind.BOUNDARY_SCHEDULE
                || startsAt == null || endsAt == null || !endsAt.isAfter(startsAt)) {
            throw new InvalidSimulationValueException("Schedule requires a BOUNDARY_SCHEDULE source and ordered horizon.");
        }
        if (!sourceVersion.effectiveAt(startsAt)) {
            throw new InvalidSimulationValueException("Schedule source must be effective at horizon start.");
        }
        if (series == null || series.isEmpty() || series.stream().anyMatch(value -> value == null)) {
            throw new InvalidSimulationValueException("Schedule requires nonempty series without null entries.");
        }
        series = List.copyOf(series);
        var identities = new HashSet<String>();
        var keys = new HashSet<QuantityKey>();
        for (var item : series) {
            if (!identities.add(item.id()) || !keys.add(new QuantityKey(item.targetKind(), item.targetId(), item.quantity()))) {
                throw new InvalidSimulationValueException("Schedule series IDs and target/quantity keys must be unique.");
            }
            var points = item.points();
            if (!points.get(0).at().equals(startsAt) || !points.get(points.size() - 1).at().equals(endsAt)) {
                throw new InvalidSimulationValueException("Every boundary series must anchor exactly both horizon ends.");
            }
            for (var point : points) {
                if (point.at().isBefore(startsAt) || point.at().isAfter(endsAt)
                        || point.recordedAt().isAfter(sourceVersion.recordedAt())) {
                    throw new InvalidSimulationValueException("Boundary points must remain inside the horizon and frozen source revision.");
                }
            }
        }
    }

    private record QuantityKey(TargetKind targetKind, String targetId, Quantity quantity) { }
}
