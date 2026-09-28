/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentScope
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Scope item to which a risk assessment applies.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import java.time.Instant;

    /**
     * Scope item to which a risk assessment applies.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param scopeCodeSnapshot scopeCodeSnapshot
     * @param scopeLabelSnapshot scopeLabelSnapshot
     * @param topologySnapshotId topologySnapshotId
     * @param operationalPeriodStart operationalPeriodStart
     * @param operationalPeriodEnd operationalPeriodEnd
     * @param included included
     * @param scopeNote scopeNote
     * @param createdAt createdAt
     */
    public record RiskAssessmentScope(
            String id,
        String riskAssessmentId,
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot,
        String topologySnapshotId,
        Instant operationalPeriodStart,
        Instant operationalPeriodEnd,
        boolean included,
        String scopeNote,
        Instant createdAt
    ) {

        public RiskAssessmentScope {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessmentScope id must not be blank.");
        }
        // HRA-051 required: riskAssessmentId
        if (riskAssessmentId == null || riskAssessmentId.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessmentScope risk assessment id must not be blank.");
        }
        // HRA-051 required: scopeId
        if (scopeId == null || scopeId.isBlank()) {
            throw new InvalidRiskValueException("RiskAssessmentScope scope id must not be blank.");
        }

        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        scopeCodeSnapshot = normalize(scopeCodeSnapshot);
        scopeLabelSnapshot = normalize(scopeLabelSnapshot);
        topologySnapshotId = normalize(topologySnapshotId);
        scopeNote = normalize(scopeNote);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
