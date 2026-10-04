/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Suspected leak event with governed topology provenance and derived severity.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.service.LeakConfidenceClassifier;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Suspected leak event.
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

    private static final LeakConfidenceClassifier CONFIDENCE_CLASSIFIER = new LeakConfidenceClassifier();

    public LeakCandidate {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCandidate id must not be blank.");
        }
        // HRA-051 required: profileId
        if (profileId == null || profileId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCandidate profile id must not be blank.");
        }
        // HRA-051 required: candidateNumber
        if (candidateNumber == null || candidateNumber.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCandidate candidate number must not be blank.");
        }
        if (topologyAssetType == null || topologyAssetType.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCandidate topology asset type must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCandidate topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCandidate topology asset code must not be blank.");
        }
        // HRA-051 required: suspectedAt
        if (suspectedAt == null) {
            throw new InvalidLeakDetectionValueException("LeakCandidate suspected at must not be null.");
        }
        // HRA-051 required: confidenceScore
        if (confidenceScore == null) {
            throw new InvalidLeakDetectionValueException("LeakCandidate confidence score must not be null.");
        }
        // HRA-051 required: severityLevel
        if (severityLevel == null) {
            throw new InvalidLeakDetectionValueException("LeakCandidate severity level must not be null.");
        }
        LeakSeverityLevel derivedSeverity = CONFIDENCE_CLASSIFIER.classify(confidenceScore);
        if (severityLevel != derivedSeverity) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate severity level must be derived from confidence score."
            );
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidLeakDetectionValueException("LeakCandidate status must not be null.");
        }
        if (createdAt == null || updatedAt == null) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate createdAt and updatedAt must not be null."
            );
        }

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
