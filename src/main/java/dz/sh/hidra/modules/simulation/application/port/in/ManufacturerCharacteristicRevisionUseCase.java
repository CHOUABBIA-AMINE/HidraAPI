/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.in
 *
 * @Description : Protected owner operations for exact immutable manufacturer characteristic revisions.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.in;

import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/** Operations are descriptors, never authorization tokens or proof of execution. */
public interface ManufacturerCharacteristicRevisionUseCase {
    enum Operation { SAVE, READ_REVISION, LIST_HISTORY, READ_CERTIFICATE, QUALIFY }

    record ApprovalReference(String definitionId, int definitionVersion, String targetTypeId, String purposeId,
                             String instanceId, String taskId, String actionId) {
        public ApprovalReference {
            for (String v : List.of(definitionId, targetTypeId, purposeId, instanceId, taskId, actionId)) exact(v);
            if (definitionVersion < 1) throw new IllegalArgumentException("Workflow definition version must be positive");
        }
    }

    record Command(Operation operation, String companyPartyId, String sourceId, String revisionId,
                   ManufacturerCharacteristicRevision suppliedRevision, ApprovalReference approval) {
        public Command {
            Objects.requireNonNull(operation, "Operation required");
            exact(companyPartyId); exact(sourceId);
            if (operation == Operation.LIST_HISTORY) {
                if (revisionId != null || suppliedRevision != null || approval != null)
                    throw new IllegalArgumentException("History request is source-scoped");
            } else exact(revisionId);
            if (operation == Operation.SAVE) {
                Objects.requireNonNull(suppliedRevision, "Supplied revision required");
                if (!sourceId.equals(suppliedRevision.sourceId()) || !revisionId.equals(suppliedRevision.revisionId()))
                    throw new IllegalArgumentException("Supplied revision identity mismatch");
            } else if (suppliedRevision != null) throw new IllegalArgumentException("Unexpected supplied revision");
            if (operation == Operation.QUALIFY) {
                Objects.requireNonNull(approval, "Exact terminal Workflow approval required");
            } else if (approval != null) throw new IllegalArgumentException("Unexpected approval reference");
        }
        @Override public String toString() { return "ManufacturerCharacteristicCommand[redacted]"; }
    }

    record RevisionSummary(String sourceId, String revisionId, String payloadSha256, Instant recordedAt,
                           Instant effectiveFrom, Instant effectiveUntil, boolean workflowQualified) {
        public RevisionSummary {
            exact(sourceId); exact(revisionId); digest(payloadSha256);
            Objects.requireNonNull(recordedAt); Objects.requireNonNull(effectiveFrom);
        }
    }

    /** Never release this result until the original protected transaction has committed. */
    record Result(Operation operation, String companyPartyId, String sourceId, String revisionId,
                  String payloadSha256, ManufacturerCharacteristicRevision revision,
                  List<RevisionSummary> history, Instant completedAt, boolean workflowQualified) {
        public Result {
            Objects.requireNonNull(operation); exact(companyPartyId); exact(sourceId);
            if (revisionId != null) exact(revisionId);
            if (payloadSha256 != null) digest(payloadSha256);
            history = history == null ? List.of() : List.copyOf(history);
            Objects.requireNonNull(completedAt);
        }
        @Override public String toString() { return "ManufacturerCharacteristicResult[redacted]"; }
    }


    /** A live view; no cross-request snapshot or reproducibility guarantee. */
    record HistoryPageQuery(String companyPartyId,String sourceId,Integer requestedSize,String cursor) {
        public HistoryPageQuery { exact(companyPartyId); exact(sourceId); }
        @Override public String toString(){ return "ManufacturerHistoryPageQuery[redacted]"; }
    }
    record HistoryPageResult(List<RevisionSummary> history,String nextCursor,Instant completedAt) {
        public HistoryPageResult {
            history=List.copyOf(history);
            Objects.requireNonNull(completedAt);
        }
        @Override public String toString(){ return "ManufacturerHistoryPageResult[redacted]"; }
    }
    HistoryPageResult pageHistory(HistoryPageQuery query);

    Result execute(Command command);

    private static void exact(String text) {
        if (text == null || text.isBlank() || !text.equals(text.strip()) || text.codePointCount(0,text.length()) > 80
                || text.codePoints().anyMatch(x -> Character.isISOControl(x) || x >= 0xd800 && x <= 0xdfff))
            throw new IllegalArgumentException("Exact bounded identifier required");
    }
    private static void digest(String value) {
        if (value == null || !value.matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("Canonical SHA-256 required");
    }
}
