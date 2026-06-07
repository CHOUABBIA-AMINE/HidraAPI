/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Domain policy for telemetry readings.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;

/**
 * Domain policy for telemetry readings.
 *
 * <p>Business role:
 * Verifies readings can be accepted, rejected, duplicated, or quarantined according to point and
 * reading lifecycle state.
 *
 * <p>Architecture role:
 * Pure domain policy. It does not persist readings or calculate flow/analytics.
 */
public final class TelemetryReadingPolicy {

    /**
     * Ensures a reading belongs to the supplied point.
     *
     * @param point telemetry point
     * @param reading telemetry reading
     */
    public void requireReadingBelongsToPoint(TelemetryPoint point, TelemetryReading reading) {
        Objects.requireNonNull(point, "Telemetry point must not be null.");
        Objects.requireNonNull(reading, "Telemetry reading must not be null.");

        if (!point.id().equals(reading.pointId())) {
            throw new BusinessRuleViolationException("Telemetry reading does not belong to the supplied telemetry point.");
        }
    }

    /**
     * Ensures a point can receive a reading.
     *
     * @param point telemetry point
     */
    public void requirePointCanReceiveReading(TelemetryPoint point) {
        Objects.requireNonNull(point, "Telemetry point must not be null.");

        if (!TelemetryPointStatus.ACTIVE.equals(point.status())) {
            throw new BusinessRuleViolationException("Telemetry reading can be received only for an active point.");
        }
    }

    /**
     * Ensures a reading can move to an accepted state.
     *
     * @param reading telemetry reading
     */
    public void requireReadingCanBeAccepted(TelemetryReading reading) {
        requireReceivedOrQuarantined(reading, "accepted");
    }

    /**
     * Ensures a reading can move to a rejected state.
     *
     * @param reading telemetry reading
     * @param reason rejection reason
     */
    public void requireReadingCanBeRejected(TelemetryReading reading, String reason) {
        requireReceivedOrQuarantined(reading, "rejected");

        if (reason == null || reason.isBlank()) {
            throw new BusinessRuleViolationException("Rejecting a telemetry reading requires a reason.");
        }
    }

    /**
     * Ensures a reading can be marked duplicate.
     *
     * @param reading telemetry reading
     */
    public void requireReadingCanBeMarkedDuplicate(TelemetryReading reading) {
        requireReceivedOrQuarantined(reading, "marked duplicate");
    }

    /**
     * Ensures a reading can be quarantined.
     *
     * @param reading telemetry reading
     * @param reason quarantine reason
     */
    public void requireReadingCanBeQuarantined(TelemetryReading reading, String reason) {
        Objects.requireNonNull(reading, "Telemetry reading must not be null.");

        if (!TelemetryReadingState.RECEIVED.equals(reading.state())) {
            throw new BusinessRuleViolationException("Only received telemetry readings can be quarantined.");
        }

        if (reason == null || reason.isBlank()) {
            throw new BusinessRuleViolationException("Quarantining a telemetry reading requires a reason.");
        }
    }

    private void requireReceivedOrQuarantined(TelemetryReading reading, String action) {
        Objects.requireNonNull(reading, "Telemetry reading must not be null.");

        if (!TelemetryReadingState.RECEIVED.equals(reading.state())
                && !TelemetryReadingState.QUARANTINED.equals(reading.state())) {
            throw new BusinessRuleViolationException("Only received or quarantined telemetry readings can be " + action + ".");
        }
    }
}
