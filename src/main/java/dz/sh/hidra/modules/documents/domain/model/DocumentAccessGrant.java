/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentAccessGrant
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Document-specific access metadata.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
import dz.sh.hidra.modules.documents.domain.value.*;
import java.time.Instant;

    /**
     * Document-specific access metadata.
     *
         * @param id id
     * @param documentId documentId
     * @param documentVersionId documentVersionId
     * @param principalType principalType
     * @param principalId principalId
     * @param principalLabelSnapshot principalLabelSnapshot
     * @param accessLevel accessLevel
     * @param grantedByActorId grantedByActorId
     * @param grantedAt grantedAt
     * @param validFrom validFrom
     * @param validTo validTo
     * @param revokedAt revokedAt
     * @param active active
     */
    public record DocumentAccessGrant(
            String id,
        String documentId,
        String documentVersionId,
        DocumentPrincipalType principalType,
        String principalId,
        String principalLabelSnapshot,
        DocumentAccessLevel accessLevel,
        String grantedByActorId,
        Instant grantedAt,
        Instant validFrom,
        Instant validTo,
        Instant revokedAt,
        boolean active
    ) {

        public DocumentAccessGrant {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentAccessGrant id must not be blank.");
        }
        // HRA-051 required: documentId
        if (documentId == null || documentId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentAccessGrant document id must not be blank.");
        }
        // HRA-051 required: principalType
        if (principalType == null) {
            throw new InvalidDocumentValueException("DocumentAccessGrant principal type must not be null.");
        }
        // HRA-051 required: principalId
        if (principalId == null || principalId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentAccessGrant principal id must not be blank.");
        }
        // HRA-051 required: accessLevel
        if (accessLevel == null) {
            throw new InvalidDocumentValueException("DocumentAccessGrant access level must not be null.");
        }
        // HRA-051 required: grantedByActorId
        if (grantedByActorId == null || grantedByActorId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentAccessGrant granted by actor id must not be blank.");
        }
        // HRA-051 required: grantedAt
        if (grantedAt == null) {
            throw new InvalidDocumentValueException("DocumentAccessGrant granted at must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidDocumentValueException("DocumentAccessGrant valid to must not be before valid from.");
        }

        id = normalize(id);
        documentId = normalize(documentId);
        documentVersionId = normalize(documentVersionId);
        principalId = normalize(principalId);
        principalLabelSnapshot = normalize(principalLabelSnapshot);
        grantedByActorId = normalize(grantedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
