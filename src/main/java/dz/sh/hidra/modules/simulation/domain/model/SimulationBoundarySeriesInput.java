/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationBoundarySeriesInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Captures ordered immutable boundary points with explicit interpolation and provenance.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.Origin;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Quantity;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.TargetKind;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/** Interpolation is declared, never executed; no extrapolation or actuation is provided. */
public record SimulationBoundarySeriesInput(
        String id,
        TargetKind targetKind,
        String targetId,
        Quantity quantity,
        Interpolation interpolation,
        List<Point> points
) {
    public SimulationBoundarySeriesInput {
        id = required(id, "Boundary series identity");
        targetId = required(targetId, "Boundary target identity");
        if (quantity == null || interpolation == null || targetKind == TargetKind.PIPE) {
            throw new InvalidSimulationValueException("Boundary requires explicit quantity/interpolation and a node or equipment target.");
        }
        quantity.validateTarget(targetKind);
        if (points == null || points.size() < 2 || points.stream().anyMatch(value -> value == null)) {
            throw new InvalidSimulationValueException("Boundary series requires at least two points without null entries.");
        }
        points = List.copyOf(points);
        Instant previous = null;
        for (var point : points) {
            quantity.validateValue(point.value());
            if (previous != null && !point.at().isAfter(previous)) {
                throw new InvalidSimulationValueException("Boundary point times must be strictly ascending and unique.");
            }
            previous = point.at();
        }
    }

    /** STEP_PREVIOUS uses [knot,nextKnot); LINEAR declares adjacent-knot interpolation. */
    public enum Interpolation { STEP_PREVIOUS, LINEAR }

    public record Point(Instant at, BigDecimal value, Origin origin, Instant recordedAt, String evidenceReference) {
        public Point {
            evidenceReference = required(evidenceReference, "Boundary point evidence");
            if (at == null || value == null || recordedAt == null || origin == null) {
                throw new InvalidSimulationValueException("Boundary point requires time, value, origin and recording time.");
            }
            if (origin != Origin.TRUSTED_TELEMETRY && origin != Origin.SCENARIO
                    && origin != Origin.FORECAST && origin != Origin.SYNTHETIC) {
                throw new InvalidSimulationValueException("Boundary origin must distinguish observed, scenario, forecast or synthetic data.");
            }
            if (origin == Origin.TRUSTED_TELEMETRY && at.isAfter(recordedAt)) {
                throw new InvalidSimulationValueException("Observed boundary point must not be later than its recording time.");
            }
        }
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }
}
