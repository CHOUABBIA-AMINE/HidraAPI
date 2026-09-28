/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentScope
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityAssessmentScope id must not be blank.");
        }
        // HRA-051 required: assessmentId
        if (assessmentId == null || assessmentId.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityAssessmentScope assessment id must not be blank.");
        }
        // HRA-051 required: topologyAssetTypeCode
        if (topologyAssetTypeCode == null || topologyAssetTypeCode.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityAssessmentScope topology asset type code must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidIntegrityValueException("IntegrityAssessmentScope topology asset id must not be blank.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIntegrityValueException("IntegrityAssessmentScope valid to must not be before valid from.");
        }

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
