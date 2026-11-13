/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationEvaluation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Immutable outcome of a governed numeric plan comparison.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record AuthoritativeDeviationEvaluation(
        String id, String ruleId, long ruleRevision, Instant evaluatedAt, Instant recordedAt,
        String actorId, String unitId, Outcome outcome, Target target, Observation observation) {

    public enum Outcome { COMPUTED, MISSING_EVIDENCE, STALE, UNCERTAIN, INCOMPATIBLE, POLICY_UNAVAILABLE }

    public record Target(String id, String revisionId, String approvalId, Instant approvedAt, BigDecimal value) { }

    public record Observation(String id, String rawId, String assessmentId, String bindingId,
                              String qualityCodeId, String trustLevel, Instant observedAt,
                              Instant trustedAt, BigDecimal value) { }

    public AuthoritativeDeviationEvaluation {
        Objects.requireNonNull(outcome);
        if (outcome == Outcome.COMPUTED
                && (target == null || target.value() == null || observation == null || observation.value() == null)) {
            throw new IllegalArgumentException("Computed outcome requires numeric evidence.");
        }
    }

    public BigDecimal actual() {
        return outcome == Outcome.COMPUTED ? observation.value() : null;
    }

    public BigDecimal expected() {
        return outcome == Outcome.COMPUTED ? target.value() : null;
    }

    public BigDecimal difference() {
        return outcome == Outcome.COMPUTED ? actual().subtract(expected()) : null;
    }
}
