/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseImpactAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Health, safety, environment, and compliance impact assessment.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Health, safety, environment, and compliance impact assessment.
     *
         * @param id id
     * @param hseCaseId hseCaseId
     * @param impactDomain impactDomain
     * @param impactTypeId impactTypeId
     * @param severity severity
     * @param description description
     * @param peopleAffectedCount peopleAffectedCount
     * @param injuryCount injuryCount
     * @param spillVolume spillVolume
     * @param spillVolumeUnitId spillVolumeUnitId
     * @param estimatedCost estimatedCost
     * @param currencyCode currencyCode
     * @param regulatoryReferenceId regulatoryReferenceId
     * @param assessedByActorId assessedByActorId
     * @param assessedAt assessedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record HseImpactAssessment(
            String id,
        String hseCaseId,
        HseImpactDomain impactDomain,
        String impactTypeId,
        HseImpactSeverity severity,
        String description,
        Integer peopleAffectedCount,
        Integer injuryCount,
        BigDecimal spillVolume,
        String spillVolumeUnitId,
        BigDecimal estimatedCost,
        String currencyCode,
        String regulatoryReferenceId,
        String assessedByActorId,
        Instant assessedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public HseImpactAssessment {
        id = normalize(id);
        hseCaseId = normalize(hseCaseId);
        impactTypeId = normalize(impactTypeId);
        description = normalize(description);
        spillVolumeUnitId = normalize(spillVolumeUnitId);
        currencyCode = normalize(currencyCode);
        regulatoryReferenceId = normalize(regulatoryReferenceId);
        assessedByActorId = normalize(assessedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
