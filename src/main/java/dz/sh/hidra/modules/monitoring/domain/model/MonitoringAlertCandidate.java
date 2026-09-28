/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAlertCandidate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Candidate for formal future alarm management.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;

    /**
     * Candidate for formal future alarm management.
     *
         * @param id id
     * @param deviationId deviationId
     * @param evaluationId evaluationId
     * @param ruleId ruleId
     * @param candidateCode candidateCode
     * @param candidateTypeId candidateTypeId
     * @param severity severity
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param telemetryPointId telemetryPointId
     * @param summary summary
     * @param lifecycleStatus lifecycleStatus
     * @param candidateStatus candidateStatus
     * @param escalationReferenceId escalationReferenceId
     * @param createdAt createdAt
     * @param expiresAt expiresAt
     */
    public record MonitoringAlertCandidate(
            String id,
        String deviationId,
        String evaluationId,
        String ruleId,
        String candidateCode,
        String candidateTypeId,
        DeviationSeverity severity,
        String topologyAssetType,
        String topologyAssetId,
        String telemetryPointId,
        String summary,
        MonitoringLifecycleStatus lifecycleStatus,
        AlertCandidateStatus candidateStatus,
        String escalationReferenceId,
        Instant createdAt,
        Instant expiresAt
    ) {

        public MonitoringAlertCandidate {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringAlertCandidate id must not be blank.");
        }
        // HRA-051 required: candidateCode
        if (candidateCode == null || candidateCode.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringAlertCandidate candidate code must not be blank.");
        }
        // HRA-051 required: candidateTypeId
        if (candidateTypeId == null || candidateTypeId.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringAlertCandidate candidate type id must not be blank.");
        }
        // HRA-051 required: severity
        if (severity == null) {
            throw new InvalidMonitoringValueException("MonitoringAlertCandidate severity must not be null.");
        }
        // HRA-051 required: lifecycleStatus
        if (lifecycleStatus == null) {
            throw new InvalidMonitoringValueException("MonitoringAlertCandidate lifecycle status must not be null.");
        }
        // HRA-051 required: candidateStatus
        if (candidateStatus == null) {
            throw new InvalidMonitoringValueException("MonitoringAlertCandidate candidate status must not be null.");
        }

        id = normalize(id);
        deviationId = normalize(deviationId);
        evaluationId = normalize(evaluationId);
        ruleId = normalize(ruleId);
        candidateCode = normalize(candidateCode);
        candidateTypeId = normalize(candidateTypeId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        telemetryPointId = normalize(telemetryPointId);
        summary = normalize(summary);
        escalationReferenceId = normalize(escalationReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
