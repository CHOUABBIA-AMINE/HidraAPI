/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityAssessmentPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Deterministically evaluates explicitly configured raw-quality criteria.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.AssessmentStatus;
import dz.sh.hidra.modules.telemetry.domain.value.ReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TrustLevel;
import java.time.Instant;
import java.util.Objects;

public final class TelemetryQualityAssessmentPolicy {
    private TelemetryQualityAssessmentPolicy() { }

    public record Rule(String acceptedQualityCodeId, TrustLevel acceptedTrustLevel,
            long maxPastAgeSeconds, long maxFutureSkewSeconds) {
        public Rule {
            if (acceptedQualityCodeId == null || acceptedQualityCodeId.isBlank()
                    || acceptedTrustLevel == null || acceptedTrustLevel == TrustLevel.UNTRUSTED
                    || acceptedTrustLevel == TrustLevel.LOW
                    || maxPastAgeSeconds <= 0 || maxFutureSkewSeconds <= 0) {
                throw new IllegalArgumentException("Unsupported raw-quality policy.");
            }
        }
    }

    public record Decision(AssessmentStatus status, TrustLevel trustLevel, String reasonCode) { }

    public static Decision decide(TelemetryReading raw, Rule rule, Instant assessedAt) {
        Objects.requireNonNull(raw);
        Objects.requireNonNull(rule);
        Objects.requireNonNull(assessedAt);
        String reason = null;
        if (raw.state() != ReadingState.RECEIVED && raw.state() != ReadingState.ACCEPTED) {
            reason = "RAW_NOT_ELIGIBLE";
        } else if (!raw.qualityCodeId().equals(rule.acceptedQualityCodeId())) {
            reason = "QUALITY_NOT_ELIGIBLE";
        } else if (raw.sourceTimestamp().isBefore(raw.receivedAt().minusSeconds(rule.maxPastAgeSeconds()))
                || raw.sourceTimestamp().isAfter(raw.receivedAt().plusSeconds(rule.maxFutureSkewSeconds()))) {
            reason = "SOURCE_TIME_NOT_ELIGIBLE";
        } else if (raw.sourceTimestamp().isBefore(assessedAt.minusSeconds(rule.maxPastAgeSeconds()))
                || raw.sourceTimestamp().isAfter(assessedAt.plusSeconds(rule.maxFutureSkewSeconds()))) {
            reason = "ASSESSMENT_TIME_NOT_ELIGIBLE";
        } else if (!raw.hasExactlyOneValue()) {
            reason = "VALUE_NOT_ELIGIBLE";
        }
        if (reason != null) return new Decision(AssessmentStatus.FAILED, TrustLevel.UNTRUSTED, reason);
        return new Decision(AssessmentStatus.PASSED, rule.acceptedTrustLevel(), "QUALITY_POLICY_PASSED");
    }
}
