/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Controlled lifecycle record grouping leak candidates.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Controlled lifecycle record grouping leak candidates.
     *
         * @param id id
     * @param caseNumber caseNumber
     * @param primaryCandidateId primaryCandidateId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param owningOrganizationUnitId owningOrganizationUnitId
     * @param status status
     * @param severityLevel severityLevel
     * @param confidenceScore confidenceScore
     * @param openedAt openedAt
     * @param closedAt closedAt
     * @param openedByActorId openedByActorId
     * @param closedByActorId closedByActorId
     * @param closureReasonId closureReasonId
     * @param correlationId correlationId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record LeakDetectionCase(
            String id,
        String caseNumber,
        String primaryCandidateId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String owningOrganizationUnitId,
        LeakDetectionCaseStatus status,
        LeakSeverityLevel severityLevel,
        BigDecimal confidenceScore,
        Instant openedAt,
        Instant closedAt,
        String openedByActorId,
        String closedByActorId,
        String closureReasonId,
        String correlationId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public LeakDetectionCase {
        id = normalize(id);
        caseNumber = normalize(caseNumber);
        primaryCandidateId = normalize(primaryCandidateId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        owningOrganizationUnitId = normalize(owningOrganizationUnitId);
        openedByActorId = normalize(openedByActorId);
        closedByActorId = normalize(closedByActorId);
        closureReasonId = normalize(closureReasonId);
        correlationId = normalize(correlationId);
        }
        public boolean openLifecycle() {
            return status != LeakDetectionCaseStatus.CLOSED
                    && status != LeakDetectionCaseStatus.DISMISSED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
