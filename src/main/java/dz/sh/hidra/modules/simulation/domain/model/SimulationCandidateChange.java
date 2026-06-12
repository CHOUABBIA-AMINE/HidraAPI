/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateChange
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Proposed topology or operating change inside a candidate.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.time.Instant;

    /**
     * Proposed topology or operating change inside a candidate.
     *
         * @param id id
     * @param candidateId candidateId
     * @param changeTypeId changeTypeId
     * @param targetType targetType
     * @param targetId targetId
     * @param beforeValue beforeValue
     * @param afterValue afterValue
     * @param unitCode unitCode
     * @param requiresTopologyChange requiresTopologyChange
     * @param requiresOperationalProcedure requiresOperationalProcedure
     * @param safetyCritical safetyCritical
     * @param explanation explanation
     * @param createdAt createdAt
     */
    public record SimulationCandidateChange(
            String id,
        String candidateId,
        String changeTypeId,
        String targetType,
        String targetId,
        String beforeValue,
        String afterValue,
        String unitCode,
        boolean requiresTopologyChange,
        boolean requiresOperationalProcedure,
        boolean safetyCritical,
        String explanation,
        Instant createdAt
    ) {

        public SimulationCandidateChange {
        id = normalize(id);
        candidateId = normalize(candidateId);
        changeTypeId = normalize(changeTypeId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        beforeValue = normalize(beforeValue);
        afterValue = normalize(afterValue);
        unitCode = normalize(unitCode);
        explanation = normalize(explanation);
        }
        public boolean descriptiveOnly() {
            return true;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
