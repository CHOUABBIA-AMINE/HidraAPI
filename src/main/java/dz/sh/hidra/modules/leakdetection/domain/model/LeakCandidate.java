/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidate
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Suspected leak event.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Suspected leak event.
     *
         * @param id id
     * @param runId runId
     * @param profileId profileId
     * @param candidateNumber candidateNumber
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param suspectedAt suspectedAt
     * @param firstEvidenceAt firstEvidenceAt
     * @param confidenceScore confidenceScore
     * @param severityLevel severityLevel
     * @param status status
     * @param summary summary
     * @param correlationId correlationId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record LeakCandidate(
            String id,
        String runId,
        String profileId,
        String candidateNumber,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        Instant suspectedAt,
        Instant firstEvidenceAt,
        BigDecimal confidenceScore,
        LeakSeverityLevel severityLevel,
        LeakCandidateStatus status,
        String summary,
        String correlationId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public LeakCandidate {
        id = normalize(id);
        runId = normalize(runId);
        profileId = normalize(profileId);
        candidateNumber = normalize(candidateNumber);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        summary = normalize(summary);
        correlationId = normalize(correlationId);
        }
        public boolean stillOpen() {
            return status == LeakCandidateStatus.NEW
                    || status == LeakCandidateStatus.UNDER_REVIEW
                    || status == LeakCandidateStatus.VERIFIED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
