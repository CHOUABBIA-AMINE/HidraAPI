/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyAgreement
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Agreement governing custody transfer.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;

    /**
     * Agreement governing custody transfer.
     *
         * @param id id
     * @param agreementNumber agreementNumber
     * @param agreementTypeId agreementTypeId
     * @param title title
     * @param description description
     * @param transferPointId transferPointId
     * @param status status
     * @param validFrom validFrom
     * @param validTo validTo
     * @param termsSnapshotJson termsSnapshotJson
     * @param documentReferenceId documentReferenceId
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyAgreement(
            String id,
        String agreementNumber,
        String agreementTypeId,
        String title,
        String description,
        String transferPointId,
        CustodyAgreementStatus status,
        Instant validFrom,
        Instant validTo,
        String termsSnapshotJson,
        String documentReferenceId,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyAgreement {
        id = normalize(id);
        agreementNumber = normalize(agreementNumber);
        agreementTypeId = normalize(agreementTypeId);
        title = normalize(title);
        description = normalize(description);
        transferPointId = normalize(transferPointId);
        termsSnapshotJson = normalize(termsSnapshotJson);
        documentReferenceId = normalize(documentReferenceId);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
