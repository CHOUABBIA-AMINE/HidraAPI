/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ComplianceObligation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Compliance obligation.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Compliance obligation.
     *
         * @param id id
     * @param obligationNumber obligationNumber
     * @param obligationTypeId obligationTypeId
     * @param regulatoryReference regulatoryReference
     * @param title title
     * @param description description
     * @param jurisdictionId jurisdictionId
     * @param responsibleOrganizationUnitId responsibleOrganizationUnitId
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ComplianceObligation(
            String id,
        String obligationNumber,
        String obligationTypeId,
        String regulatoryReference,
        String title,
        String description,
        String jurisdictionId,
        String responsibleOrganizationUnitId,
        Instant effectiveFrom,
        Instant effectiveTo,
        ComplianceStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ComplianceObligation {
        id = normalize(id);
        obligationNumber = normalize(obligationNumber);
        obligationTypeId = normalize(obligationTypeId);
        regulatoryReference = normalize(regulatoryReference);
        title = normalize(title);
        description = normalize(description);
        jurisdictionId = normalize(jurisdictionId);
        responsibleOrganizationUnitId = normalize(responsibleOrganizationUnitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
