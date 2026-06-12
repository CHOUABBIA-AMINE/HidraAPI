/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQualityCertificate
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Quality certificate reference.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.time.Instant;

    /**
     * Quality certificate reference.
     *
         * @param id id
     * @param certificateNumber certificateNumber
     * @param qualitySampleId qualitySampleId
     * @param documentReferenceId documentReferenceId
     * @param issuedByPartyId issuedByPartyId
     * @param issuedByNameSnapshot issuedByNameSnapshot
     * @param issuedAt issuedAt
     * @param certificateSummaryJson certificateSummaryJson
     * @param status status
     * @param createdAt createdAt
     */
    public record CustodyQualityCertificate(
            String id,
        String certificateNumber,
        String qualitySampleId,
        String documentReferenceId,
        String issuedByPartyId,
        String issuedByNameSnapshot,
        Instant issuedAt,
        String certificateSummaryJson,
        String status,
        Instant createdAt
    ) {

        public CustodyQualityCertificate {
        id = normalize(id);
        certificateNumber = normalize(certificateNumber);
        qualitySampleId = normalize(qualitySampleId);
        documentReferenceId = normalize(documentReferenceId);
        issuedByPartyId = normalize(issuedByPartyId);
        issuedByNameSnapshot = normalize(issuedByNameSnapshot);
        certificateSummaryJson = normalize(certificateSummaryJson);
        status = normalize(status);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
