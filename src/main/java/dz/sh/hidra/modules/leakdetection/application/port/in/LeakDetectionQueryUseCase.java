/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.in
 *
 * @Description : Read-only leak candidate and case query contract.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface LeakDetectionQueryUseCase {

    Page<LeakCandidateView> candidates(int page, int size);

    LeakCandidateView candidate(String id);

    Page<LeakCaseView> cases(int page, int size);

    LeakCaseView leakCase(String id);

    record Page<T>(
            List<T> content,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean hasNext
    ) { }

    record LeakCandidateView(
            String id,
            String runId,
            String profileId,
            String candidateNumber,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetName,
            Instant suspectedAt,
            Instant firstEvidenceAt,
            BigDecimal confidenceScore,
            String severityLevel,
            String status,
            String summary,
            String correlationId,
            Instant createdAt,
            Instant updatedAt
    ) { }

    record LeakCaseView(
            String id,
            String caseNumber,
            String primaryCandidateId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String owningOrganizationUnitId,
            String status,
            String severityLevel,
            BigDecimal confidenceScore,
            Instant openedAt,
            Instant closedAt,
            String openedByActorId,
            String closedByActorId,
            String closureReasonId,
            String correlationId,
            Instant createdAt,
            Instant updatedAt
    ) { }
}
