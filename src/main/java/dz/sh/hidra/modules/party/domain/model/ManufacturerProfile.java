/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Manufacturer-specific identity and capability metadata.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Manufacturer-specific identity and capability metadata.
     *
         * @param id id
     * @param partyId partyId
     * @param capabilityType capabilityType
     * @param brandName brandName
     * @param manufacturerCode manufacturerCode
     * @param qualificationStatus qualificationStatus
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ManufacturerProfile(
            String id,
        String partyId,
        ManufacturerCapabilityType capabilityType,
        String brandName,
        String manufacturerCode,
        QualificationStatus qualificationStatus,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ManufacturerProfile {
        id = normalize(id);
        partyId = normalize(partyId);
        brandName = normalize(brandName);
        manufacturerCode = normalize(manufacturerCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
