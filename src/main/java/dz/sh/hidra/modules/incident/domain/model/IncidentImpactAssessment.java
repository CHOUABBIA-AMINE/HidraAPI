/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentImpactAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Operational impact assessment.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Operational impact assessment.
     *
         * @param id id
     * @param incidentId incidentId
     * @param impactTypeId impactTypeId
     * @param impactLevelId impactLevelId
     * @param estimated estimated
     * @param description description
     * @param affectedTopologyAssetTypeCode affectedTopologyAssetTypeCode
     * @param affectedTopologyAssetId affectedTopologyAssetId
     * @param affectedOrganizationUnitId affectedOrganizationUnitId
     * @param estimatedVolumeLoss estimatedVolumeLoss
     * @param estimatedVolumeUnitId estimatedVolumeUnitId
     * @param estimatedDurationMinutes estimatedDurationMinutes
     * @param assessedByActorId assessedByActorId
     * @param assessedAt assessedAt
     */
    public record IncidentImpactAssessment(
            String id,
        String incidentId,
        String impactTypeId,
        String impactLevelId,
        boolean estimated,
        String description,
        String affectedTopologyAssetTypeCode,
        String affectedTopologyAssetId,
        String affectedOrganizationUnitId,
        BigDecimal estimatedVolumeLoss,
        String estimatedVolumeUnitId,
        Integer estimatedDurationMinutes,
        String assessedByActorId,
        Instant assessedAt
    ) {

        public IncidentImpactAssessment {
        id = normalize(id);
        incidentId = normalize(incidentId);
        impactTypeId = normalize(impactTypeId);
        impactLevelId = normalize(impactLevelId);
        description = normalize(description);
        affectedTopologyAssetTypeCode = normalize(affectedTopologyAssetTypeCode);
        affectedTopologyAssetId = normalize(affectedTopologyAssetId);
        affectedOrganizationUnitId = normalize(affectedOrganizationUnitId);
        estimatedVolumeUnitId = normalize(estimatedVolumeUnitId);
        assessedByActorId = normalize(assessedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
