/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyExternalReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Reference to ERP, procurement, registry, or external master-data system.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Reference to ERP, procurement, registry, or external master-data system.
     *
         * @param id id
     * @param partyId partyId
     * @param externalSystemType externalSystemType
     * @param externalSystemCode externalSystemCode
     * @param externalReference externalReference
     * @param externalLabelSnapshot externalLabelSnapshot
     * @param status status
     * @param lastSynchronizedAt lastSynchronizedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyExternalReference(
            String id,
        String partyId,
        ExternalSystemType externalSystemType,
        String externalSystemCode,
        String externalReference,
        String externalLabelSnapshot,
        PartyCatalogStatus status,
        Instant lastSynchronizedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyExternalReference {
        id = normalize(id);
        partyId = normalize(partyId);
        externalSystemCode = normalize(externalSystemCode);
        externalReference = normalize(externalReference);
        externalLabelSnapshot = normalize(externalLabelSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
