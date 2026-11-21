/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedPressureRestriction
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Pure immutable first-class pressure restriction declarations and independent decision bindings.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * This is a declaration, NEVER a bearer of approval authority.
 * The adapter/application must verify every Workflow, Audit and T07 source pin
 * before allowing the resolver's structural result to become externally QUALIFIED.
 */
public final class QualifiedPressureRestriction {
    private QualifiedPressureRestriction() { }

    public enum PressureBasis { GAUGE, ABSOLUTE }
    public enum EventKind { SUPERSEDE, WITHDRAW }

    public record Scope(String companyPartyId, String pipelineId, String sectionId,
                        String conditionCode) {
        public Scope {
            companyPartyId = id(companyPartyId);
            pipelineId = id(pipelineId);
            sectionId = sectionId == null ? null : id(sectionId);
            conditionCode = id(conditionCode);
        }
        public boolean pipelineLevel() { return sectionId == null; }
    }

    /**
     * Source/decision/audit references, not proof of those references.
     * Reviewer separation is necessary but not sufficient for approval.
     */
    public record Evidence(UUID baselineReceiptId, String baselineDigest,
                           String engineeringSourceRevision, String decisionActionId,
                           String auditEventId, String submittedBy, String reviewedBy,
                           Instant approvedAt) {
        public Evidence {
            Objects.requireNonNull(baselineReceiptId, "baselineReceiptId");
            if (baselineDigest == null || !baselineDigest.matches("[0-9a-f]{64}"))
                throw new IllegalArgumentException("Canonical source digest required");
            engineeringSourceRevision = id(engineeringSourceRevision);
            decisionActionId = id(decisionActionId);
            auditEventId = id(auditEventId);
            submittedBy = id(submittedBy);
            reviewedBy = id(reviewedBy);
            Objects.requireNonNull(approvedAt, "approvedAt");
            if (submittedBy.equals(reviewedBy))
                throw new IllegalArgumentException("Independent reviewer required");
        }
    }

    public record Revision(UUID id, Scope scope, BigDecimal upperPressure,
                           String pressureUnit, PressureBasis basis,
                           Instant effectiveFrom, Instant effectiveUntil,
                           Instant recordedAt, Evidence evidence,
                           UUID supersedes) {
        public Revision {
            Objects.requireNonNull(id, "id");
            Objects.requireNonNull(scope, "scope");
            Objects.requireNonNull(upperPressure, "upperPressure");
            if (upperPressure.signum() <= 0 || upperPressure.precision() > 24 || upperPressure.scale() > 12)
                throw new IllegalArgumentException("Positive bounded pressure required");
            pressureUnit = QualifiedPressureRestriction.id(pressureUnit);
            Objects.requireNonNull(basis, "basis");
            Objects.requireNonNull(effectiveFrom, "effectiveFrom");
            Objects.requireNonNull(recordedAt, "recordedAt");
            Objects.requireNonNull(evidence, "evidence");
            if (effectiveUntil != null && !effectiveUntil.isAfter(effectiveFrom))
                throw new IllegalArgumentException("Half-open effective interval required");
            if (supersedes != null && supersedes.equals(id))
                throw new IllegalArgumentException("Self-supersession is prohibited");
            if (evidence.approvedAt().isAfter(recordedAt))
                throw new IllegalArgumentException("Approval cannot postdate recording");
        }
        public boolean applies(Instant instant) {
            return !instant.isBefore(effectiveFrom)
                    && (effectiveUntil == null || instant.isBefore(effectiveUntil));
        }
    }

    /**
     * The authorized successor or withdrawal is a separate immutable event.
     * A withdrawal does not erase the previously effective historical revision.
     */
    public record Termination(UUID id, Scope scope, UUID retiredRevisionId,
                              EventKind kind, UUID successorRevisionId,
                              Instant effectiveAt, Instant recordedAt,
                              Evidence evidence) {
        public Termination {
            Objects.requireNonNull(id, "id");
            Objects.requireNonNull(scope, "scope");
            Objects.requireNonNull(retiredRevisionId, "retiredRevisionId");
            Objects.requireNonNull(kind, "kind");
            Objects.requireNonNull(effectiveAt, "effectiveAt");
            Objects.requireNonNull(recordedAt, "recordedAt");
            Objects.requireNonNull(evidence, "evidence");
            if (kind == EventKind.SUPERSEDE && (successorRevisionId == null
                    || successorRevisionId.equals(retiredRevisionId))
                    || kind == EventKind.WITHDRAW && successorRevisionId != null)
                throw new IllegalArgumentException("Invalid termination lineage");
            if (evidence.approvedAt().isAfter(recordedAt))
                throw new IllegalArgumentException("Decision cannot postdate recording");
        }
    }

    private static String id(String value) {
        if (value == null || !value.matches("[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}"))
            throw new IllegalArgumentException("Exact bounded identifier required");
        return value;
    }
}
