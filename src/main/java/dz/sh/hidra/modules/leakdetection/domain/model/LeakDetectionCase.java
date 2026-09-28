/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase id must not be blank.");
        }
        // HRA-051 required: caseNumber
        if (caseNumber == null || caseNumber.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase case number must not be blank.");
        }
        // HRA-051 required: primaryCandidateId
        if (primaryCandidateId == null || primaryCandidateId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase primary candidate id must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase topology asset code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase status must not be null.");
        }
        // HRA-051 required: openedAt
        if (openedAt == null) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase opened at must not be null.");
        }
        // HRA-051 order: openedAt <= closedAt
        if (openedAt != null && closedAt != null && closedAt.isBefore(openedAt)) {
            throw new InvalidLeakDetectionValueException("LeakDetectionCase closed at must not be before opened at.");
        }

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
