/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentScope
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Referenced topology scope.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import java.time.Instant;

    /**
     * Referenced topology scope.
     *
         * @param id id
     * @param assessmentId assessmentId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param scopeRoleId scopeRoleId
     * @param topologySnapshotId topologySnapshotId
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     */
    public record IntegrityAssessmentScope(
            String id,
        String assessmentId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String topologyAssetNameSnapshot,
        String scopeRoleId,
        String topologySnapshotId,
        Instant validFrom,
        Instant validTo,
        Instant createdAt
    ) {

        public IntegrityAssessmentScope {
        id = normalize(id);
        assessmentId = normalize(assessmentId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        scopeRoleId = normalize(scopeRoleId);
        topologySnapshotId = normalize(topologySnapshotId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
