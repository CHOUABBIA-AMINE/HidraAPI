/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCompressorCurveInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Preserves immutable declared compressor speed-line data and explicit reference bases.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import java.math.BigDecimal;
import java.util.List;

/** Declared map data only: no interpolation, correction, applicability or operating-envelope claim. */
public record SimulationCompressorCurveInput(
        String id,
        SimulationInputSourceVersion sourceVersion,
        String fluidInputId,
        String fluidRevisionId,
        String headDefinitionReference,
        String efficiencyDefinitionReference,
        String interpolationMethodReference,
        BigDecimal referenceInletPressurePascalsAbsolute,
        BigDecimal referenceInletTemperatureKelvin,
        List<SpeedLine> speedLines
) {
    public SimulationCompressorCurveInput {
        id = required(id, "Curve identity");
        fluidInputId = required(fluidInputId, "Fluid input identity");
        fluidRevisionId = required(fluidRevisionId, "Fluid revision identity");
        headDefinitionReference = required(headDefinitionReference, "Head definition reference");
        efficiencyDefinitionReference = required(efficiencyDefinitionReference, "Efficiency definition reference");
        interpolationMethodReference = required(interpolationMethodReference, "Interpolation method reference");
        if (sourceVersion == null || sourceVersion.kind() != SourceKind.EQUIPMENT_PARAMETERS) {
            throw new InvalidSimulationValueException("Compressor curve requires an EQUIPMENT_PARAMETERS source version.");
        }
        positive(referenceInletPressurePascalsAbsolute, "Reference absolute inlet pressure");
        positive(referenceInletTemperatureKelvin, "Reference inlet temperature");
        speedLines = immutable(speedLines, "Speed lines");
        if (speedLines.isEmpty()) {
            throw new InvalidSimulationValueException("Curve requires speed lines.");
        }
        BigDecimal previous = null;
        for (var line : speedLines) {
            if (previous != null && line.rotationalSpeedRevolutionsPerMinute().compareTo(previous) <= 0) {
                throw new InvalidSimulationValueException("Curve speeds must be strictly ascending and unique.");
            }
            previous = line.rotationalSpeedRevolutionsPerMinute();
        }
    }

    public record SpeedLine(BigDecimal rotationalSpeedRevolutionsPerMinute, List<Point> points) {
        public SpeedLine {
            positive(rotationalSpeedRevolutionsPerMinute, "Rotational speed");
            points = immutable(points, "Curve points");
            if (points.size() < 2) {
                throw new InvalidSimulationValueException("Each speed line requires at least two points.");
            }
            BigDecimal previous = null;
            for (var point : points) {
                if (previous != null && point.massFlowKilogramsPerSecond().compareTo(previous) <= 0) {
                    throw new InvalidSimulationValueException("Per-line mass flows must be strictly ascending and unique.");
                }
                previous = point.massFlowKilogramsPerSecond();
            }
        }
    }

    public record Point(BigDecimal massFlowKilogramsPerSecond, BigDecimal specificHeadJoulesPerKilogram,
            BigDecimal efficiencyFraction) {
        public Point {
            positive(massFlowKilogramsPerSecond, "Mass flow");
            if (specificHeadJoulesPerKilogram == null || specificHeadJoulesPerKilogram.signum() < 0) {
                throw new InvalidSimulationValueException("Specific head must be nonnegative.");
            }
            if (efficiencyFraction == null || efficiencyFraction.signum() <= 0
                    || efficiencyFraction.compareTo(BigDecimal.ONE) > 0) {
                throw new InvalidSimulationValueException("Efficiency fraction must be greater than zero and at most one.");
            }
        }
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }

    private static void positive(BigDecimal value, String field) {
        if (value == null || value.signum() <= 0) {
            throw new InvalidSimulationValueException(field + " must be positive.");
        }
    }

    private static <T> List<T> immutable(List<T> values, String field) {
        if (values == null || values.stream().anyMatch(value -> value == null)) {
            throw new InvalidSimulationValueException(field + " must be present without null entries.");
        }
        return List.copyOf(values);
    }
}
