/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResolution
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Resolution details.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import java.time.Instant;

    /**
     * Resolution details.
     *
         * @param id id
     * @param incidentId incidentId
     * @param resolutionTypeId resolutionTypeId
     * @param resolutionSummary resolutionSummary
     * @param correctiveActionRequired correctiveActionRequired
     * @param preventiveActionRequired preventiveActionRequired
     * @param residualRiskLevelId residualRiskLevelId
     * @param resolvedByActorId resolvedByActorId
     * @param resolvedAt resolvedAt
     * @param workflowInstanceId workflowInstanceId
     */
    public record IncidentResolution(
            String id,
        String incidentId,
        String resolutionTypeId,
        String resolutionSummary,
        boolean correctiveActionRequired,
        boolean preventiveActionRequired,
        String residualRiskLevelId,
        String resolvedByActorId,
        Instant resolvedAt,
        String workflowInstanceId
    ) {

        public IncidentResolution {
        id = normalize(id);
        incidentId = normalize(incidentId);
        resolutionTypeId = normalize(resolutionTypeId);
        resolutionSummary = normalize(resolutionSummary);
        residualRiskLevelId = normalize(residualRiskLevelId);
        resolvedByActorId = normalize(resolvedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
