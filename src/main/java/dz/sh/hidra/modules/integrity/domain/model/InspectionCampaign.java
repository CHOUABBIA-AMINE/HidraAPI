/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InspectionCampaign
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Inspection campaign.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Inspection campaign.
     *
         * @param id id
     * @param programId programId
     * @param campaignNumber campaignNumber
     * @param name name
     * @param inspectionTypeId inspectionTypeId
     * @param contractorPartyId contractorPartyId
     * @param contractorNameSnapshot contractorNameSnapshot
     * @param status status
     * @param plannedStartAt plannedStartAt
     * @param plannedEndAt plannedEndAt
     * @param actualStartAt actualStartAt
     * @param actualEndAt actualEndAt
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record InspectionCampaign(
            String id,
        String programId,
        String campaignNumber,
        String name,
        String inspectionTypeId,
        String contractorPartyId,
        String contractorNameSnapshot,
        InspectionCampaignStatus status,
        Instant plannedStartAt,
        Instant plannedEndAt,
        Instant actualStartAt,
        Instant actualEndAt,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public InspectionCampaign {
        id = normalize(id);
        programId = normalize(programId);
        campaignNumber = normalize(campaignNumber);
        name = normalize(name);
        inspectionTypeId = normalize(inspectionTypeId);
        contractorPartyId = normalize(contractorPartyId);
        contractorNameSnapshot = normalize(contractorNameSnapshot);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
