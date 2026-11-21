/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedPressureWorkflowCandidate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Immutable unapproved pressure-limit decision targets; authority is established separately.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.PressureBasis;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Workflow can review only an immutable, exact proposed restriction. A candidate
 * is not an operating limit and cannot confer approval or publish one.
 */
public record QualifiedPressureWorkflowCandidate(
        UUID id, Kind kind, Scope scope, String pipelineSystemId,
        BigDecimal upperPressure, String pressureUnit, PressureBasis pressureBasis,
        Instant effectiveFrom, Instant effectiveUntil, UUID retiredRevisionId,
        UUID successorRevisionId, UUID baselineReceiptId, String baselineDigest,
        String engineeringSourceRevision, String submittedBy, Instant createdAt) {
    public enum Kind { REVISION, SUPERSEDE, WITHDRAW }

    public QualifiedPressureWorkflowCandidate {
        Objects.requireNonNull(id,"candidateId");
        Objects.requireNonNull(kind,"candidateKind");
        Objects.requireNonNull(scope,"candidateScope");
        pipelineSystemId=identity(pipelineSystemId);
        engineeringSourceRevision=identity(engineeringSourceRevision);
        submittedBy=identity(submittedBy);
        Objects.requireNonNull(baselineReceiptId,"baselineReceiptId");
        Objects.requireNonNull(createdAt,"createdAt");
        if(baselineDigest==null||!baselineDigest.matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("Exact source digest required");
        if(effectiveFrom==null || effectiveFrom.getNano()%1000!=0
                || createdAt.getNano()%1000!=0
                || effectiveUntil!=null && effectiveUntil.getNano()%1000!=0)
            throw new IllegalArgumentException("Microsecond-aligned time required");
        if(kind==Kind.REVISION) {
            Objects.requireNonNull(upperPressure,"upperPressure");
            pressureUnit=identity(pressureUnit);
            Objects.requireNonNull(pressureBasis,"pressureBasis");
            if(upperPressure.signum()<=0 || upperPressure.precision()>24 || upperPressure.scale()>12
                    || effectiveUntil!=null && !effectiveUntil.isAfter(effectiveFrom)
                    || retiredRevisionId!=null && retiredRevisionId.equals(id)
                    || successorRevisionId!=null)
                throw new IllegalArgumentException("Invalid exact proposed pressure revision");
        } else {
            if(upperPressure!=null || pressureUnit!=null || pressureBasis!=null
                    || effectiveUntil!=null || retiredRevisionId==null
                    || retiredRevisionId.equals(id)
                    || kind==Kind.SUPERSEDE && (successorRevisionId==null
                        || successorRevisionId.equals(retiredRevisionId))
                    || kind==Kind.WITHDRAW && successorRevisionId!=null)
                throw new IllegalArgumentException("Invalid exact proposed restriction termination");
        }
    }
    public String workflowLocator() { return "t36-v1:"+id; }
    @Override public String toString(){return "QualifiedPressureWorkflowCandidate[redacted]";}

    private static String identity(String x) {
        if(x==null||!x.matches("[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}"))
            throw new IllegalArgumentException("Exact bounded identifier required");
        return x;
    }
}
