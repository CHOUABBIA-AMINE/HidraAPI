/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentAccessGrant
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
