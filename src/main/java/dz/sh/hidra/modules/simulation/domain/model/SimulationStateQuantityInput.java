/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationStateQuantityInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Preserves explicit known or unknown initial quantities with SI basis and declared evidence.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.time.Instant;

/** Declared provenance only; a known value does not establish verified telemetry trust. */
public record SimulationStateQuantityInput(
        TargetKind targetKind,
        String targetId,
        Quantity quantity,
        Knowledge knowledge,
        BigDecimal value,
        Instant valueAt,
        Instant evidenceRecordedAt,
        String evidenceReference
) {
    public SimulationStateQuantityInput {
        targetId = required(targetId, "Target identity");
        evidenceReference = required(evidenceReference, "Quantity evidence reference");
        if (quantity == null || knowledge == null) {
            throw new InvalidSimulationValueException("Quantity and knowledge are required.");
        }
        quantity.validateTarget(targetKind);
        if (knowledge == Knowledge.UNKNOWN) {
            if (value != null || valueAt != null || evidenceRecordedAt != null) {
                throw new InvalidSimulationValueException("Unknown quantities must not contain a value or value/evidence times.");
            }
        } else {
            quantity.validateValue(value);
            if (valueAt == null || evidenceRecordedAt == null || valueAt.isAfter(evidenceRecordedAt)) {
                throw new InvalidSimulationValueException("Known quantity requires ordered value and evidence times.");
            }
        }
    }

    public enum TargetKind { NODE, PIPE, EQUIPMENT }
    public enum Knowledge { MEASURED, ESTIMATED, SYNTHETIC, UNKNOWN }

    /** Pipe flow follows pipe orientation; positive node injection adds mass to the scope. */
    public enum Quantity {
        PRESSURE_PASCALS_ABSOLUTE,
        TEMPERATURE_KELVIN,
        PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND,
        NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND,
        COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE,
        VALVE_OPENING_FRACTION;

        void validateTarget(TargetKind target) {
            boolean valid = target != null && switch (this) {
                case PRESSURE_PASCALS_ABSOLUTE, TEMPERATURE_KELVIN -> target == TargetKind.NODE || target == TargetKind.PIPE;
                case PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND -> target == TargetKind.PIPE;
                case NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND -> target == TargetKind.NODE;
                case COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, VALVE_OPENING_FRACTION -> target == TargetKind.EQUIPMENT;
            };
            if (!valid) {
                throw new InvalidSimulationValueException("Quantity is incompatible with its target kind.");
            }
        }

        void validateValue(BigDecimal value) {
            if (value == null) {
                throw new InvalidSimulationValueException("Known quantity value is required.");
            }
            boolean valid = switch (this) {
                case PRESSURE_PASCALS_ABSOLUTE, TEMPERATURE_KELVIN -> value.signum() > 0;
                case PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND, NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND -> true;
                case COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE -> value.signum() >= 0;
                case VALVE_OPENING_FRACTION -> value.signum() >= 0 && value.compareTo(BigDecimal.ONE) <= 0;
            };
            if (!valid) {
                throw new InvalidSimulationValueException("Quantity value is outside its declared physical range.");
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
