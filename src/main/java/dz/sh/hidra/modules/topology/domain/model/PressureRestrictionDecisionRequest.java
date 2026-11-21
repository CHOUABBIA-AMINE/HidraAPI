/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Defines the immutable input and candidate invariants for a pressure restriction decision.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.PressureBasis;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate.Kind;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/** A submission is a proposal. Qualification requires separate persisted owner evidence. */
public record PressureRestrictionDecisionRequest(UUID requestId, Scope scope, UUID baselineReceiptId,
                                                 long expectedVersion, Change change) {
    public PressureRestrictionDecisionRequest {
        require(requestId != null && scope != null && baselineReceiptId != null && change != null,
                "Request, scope, baseline receipt and proposed change are required");
        require(expectedVersion >= 0, "Expected stream version must be nonnegative");
    }

    public sealed interface Change permits Initial, Replacement, Withdrawal { }

    public record Initial(Pressure pressure) implements Change {
        public Initial {
            require(pressure != null, "An initial pressure restriction is required");
        }
    }

    public record Replacement(UUID predecessorId, Pressure pressure) implements Change {
        public Replacement {
            require(predecessorId != null && pressure != null, "A predecessor and successor restriction are required");
        }
    }

    public record Withdrawal(UUID predecessorId, Instant effectiveAt) implements Change {
        public Withdrawal {
            require(predecessorId != null, "The exact restriction to withdraw is required");
            requireAligned(effectiveAt);
        }
    }

    public record Pressure(BigDecimal upperPressure, String unit, PressureBasis basis,
                           Instant effectiveFrom, Instant effectiveUntil) {
        public Pressure {
            require(upperPressure != null && upperPressure.signum() > 0, "Positive upper pressure is required");
            upperPressure = upperPressure.stripTrailingZeros();
            require(upperPressure.precision() - (long) upperPressure.scale() <= 12
                    && upperPressure.scale() <= 12, "Pressure exceeds the stored decimal range");
            if (upperPressure.scale() < 0) upperPressure = upperPressure.setScale(0);
            require(unit != null && unit.matches("[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}") && basis != null,
                    "An exact unit and pressure basis are required");
            requireAligned(effectiveFrom);
            if (effectiveUntil != null) {
                requireAligned(effectiveUntil);
                require(effectiveUntil.isAfter(effectiveFrom), "A nonempty half-open interval is required");
            }
        }
    }

    /** Source identity comes from the protected engineering baseline reader. */
    public record Source(String pipelineSystemId, UUID receiptId, String manifestDigest, String revisionId) { }

    public List<QualifiedPressureWorkflowCandidate> candidates(Source source, String actor, Instant createdAt) {
        require(source != null && baselineReceiptId.equals(source.receiptId()), "The source receipt must be exact");
        return switch (change) {
            case Initial initial ->
                    List.of(revision(initial.pressure(), null, source, actor, createdAt));
            case Replacement replacement -> {
                var successor = revision(replacement.pressure(), replacement.predecessorId(), source, actor, createdAt);
                yield List.of(successor, termination(Kind.SUPERSEDE, replacement.predecessorId(),
                        successor.id(), successor.effectiveFrom(), source, actor, createdAt));
            }
            case Withdrawal withdrawal ->
                    List.of(termination(Kind.WITHDRAW, withdrawal.predecessorId(), null,
                            withdrawal.effectiveAt(), source, actor, createdAt));
        };
    }

    private QualifiedPressureWorkflowCandidate revision(Pressure pressure, UUID predecessor,
                                                        Source source, String actor, Instant createdAt) {
        return new QualifiedPressureWorkflowCandidate(candidateId(Kind.REVISION), Kind.REVISION, scope,
                source.pipelineSystemId(), pressure.upperPressure(), pressure.unit(), pressure.basis(),
                pressure.effectiveFrom(), pressure.effectiveUntil(), predecessor, null, source.receiptId(),
                source.manifestDigest(), source.revisionId(), actor, createdAt);
    }

    private QualifiedPressureWorkflowCandidate termination(Kind kind, UUID predecessor, UUID successor,
                                                           Instant effectiveAt, Source source, String actor,
                                                           Instant createdAt) {
        return new QualifiedPressureWorkflowCandidate(candidateId(kind), kind, scope, source.pipelineSystemId(),
                null, null, null, effectiveAt, null, predecessor, successor, source.receiptId(),
                source.manifestDigest(), source.revisionId(), actor, createdAt);
    }

    private UUID candidateId(Kind kind) {
        String key = "hidra:t38:" + requestId + ":" + kind.name();
        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    }

    private static void requireAligned(Instant time) {
        require(time != null && time.getNano() % 1000 == 0, "A microsecond-aligned instant is required");
    }

    private static void require(boolean condition, String detail) {
        if (!condition) throw new IllegalArgumentException(detail);
    }
}
