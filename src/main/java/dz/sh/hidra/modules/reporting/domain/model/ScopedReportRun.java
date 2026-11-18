/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.domain.model;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public final class ScopedReportRun {
    private ScopedReportRun() {}
    public enum Phase {
        SUBMITTED, QUEUED, RUNNING, RENDERED, FAILED, COMPLETED;
        public boolean pending() { return this == QUEUED || this == RUNNING; }
        public void require(Phase expected) {
            if (this != expected) throw new IllegalStateException("Report state conflict.");
        }
    }
    public record Scope(String companyId, String facilityId) {
        public Scope { bounded(companyId, 96); bounded(facilityId, 120); }
    }
    public record Projection(String metricRunId, String definitionVersionId, String methodVersion,
            BigDecimal value, String unitId, String evaluationId, String ruleId, long ruleRevision,
            String targetId, String targetRevisionId, String approvalId, String trustedReadingId,
            String rawReadingId, String assessmentId, String bindingId, Instant evaluatedAt, Instant recordedAt) {
        public Projection {
            for (String field : new String[]{metricRunId, definitionVersionId, methodVersion, unitId,
                    evaluationId, ruleId, targetId, targetRevisionId, approvalId, trustedReadingId,
                    rawReadingId, assessmentId, bindingId}) bounded(field, 500);
            Objects.requireNonNull(value);
            Objects.requireNonNull(evaluatedAt);
            Objects.requireNonNull(recordedAt);
            if (ruleRevision < 0 || value.precision() > 256 || Math.abs((long) value.scale()) > 256)
                throw new IllegalArgumentException("Invalid metric projection.");
        }
    }
    public static String bounded(String text, int max) {
        if (text == null || text.isBlank() || text.length() > max || !text.equals(text.strip()))
            throw new IllegalArgumentException("Invalid report selector.");
        return text;
    }
}
