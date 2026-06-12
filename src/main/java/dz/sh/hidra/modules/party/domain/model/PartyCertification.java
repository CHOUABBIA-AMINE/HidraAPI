/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCertification
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Certification or approval evidence for party capability.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Certification or approval evidence for party capability.
     *
         * @param id id
     * @param partyId partyId
     * @param certificationCode certificationCode
     * @param certificationBodyPartyId certificationBodyPartyId
     * @param certificateNumber certificateNumber
     * @param issuedAt issuedAt
     * @param expiresAt expiresAt
     * @param status status
     * @param documentReferenceId documentReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyCertification(
            String id,
        String partyId,
        String certificationCode,
        String certificationBodyPartyId,
        String certificateNumber,
        Instant issuedAt,
        Instant expiresAt,
        PartyCertificationStatus status,
        String documentReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyCertification {
        id = normalize(id);
        partyId = normalize(partyId);
        certificationCode = normalize(certificationCode);
        certificationBodyPartyId = normalize(certificationBodyPartyId);
        certificateNumber = normalize(certificateNumber);
        documentReferenceId = normalize(documentReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
