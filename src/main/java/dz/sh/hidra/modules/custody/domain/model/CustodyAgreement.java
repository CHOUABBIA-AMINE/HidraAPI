/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyAgreement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreement id must not be blank.");
        }
        // HRA-051 required: agreementNumber
        if (agreementNumber == null || agreementNumber.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreement agreement number must not be blank.");
        }
        // HRA-051 required: agreementTypeId
        if (agreementTypeId == null || agreementTypeId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreement agreement type id must not be blank.");
        }
        // HRA-051 required: transferPointId
        if (transferPointId == null || transferPointId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyAgreement transfer point id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidCustodyValueException("CustodyAgreement status must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidCustodyValueException("CustodyAgreement valid from must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidCustodyValueException("CustodyAgreement valid to must not be before valid from.");
        }

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
