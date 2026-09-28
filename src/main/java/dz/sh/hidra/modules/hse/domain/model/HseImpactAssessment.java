/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseImpactAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("HseImpactAssessment id must not be blank.");
        }
        // HRA-051 required: hseCaseId
        if (hseCaseId == null || hseCaseId.isBlank()) {
            throw new InvalidHseValueException("HseImpactAssessment hse case id must not be blank.");
        }
        // HRA-051 required: impactDomain
        if (impactDomain == null) {
            throw new InvalidHseValueException("HseImpactAssessment impact domain must not be null.");
        }
        // HRA-051 required: impactTypeId
        if (impactTypeId == null || impactTypeId.isBlank()) {
            throw new InvalidHseValueException("HseImpactAssessment impact type id must not be blank.");
        }
        // HRA-051 required: severity
        if (severity == null) {
            throw new InvalidHseValueException("HseImpactAssessment severity must not be null.");
        }
        // HRA-051 required: assessedAt
        if (assessedAt == null) {
            throw new InvalidHseValueException("HseImpactAssessment assessed at must not be null.");
        }

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
