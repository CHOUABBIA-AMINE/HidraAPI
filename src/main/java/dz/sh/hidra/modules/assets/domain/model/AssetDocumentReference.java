/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetDocumentReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Neutral reference to document metadata.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Neutral reference to document metadata.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param documentType documentType
     * @param documentReferenceId documentReferenceId
     * @param documentCodeSnapshot documentCodeSnapshot
     * @param documentTitleSnapshot documentTitleSnapshot
     * @param attachedAt attachedAt
     * @param attachedByActorId attachedByActorId
     */
    public record AssetDocumentReference(
            String id,
        String maintainableAssetId,
        DocumentReferenceType documentType,
        String documentReferenceId,
        String documentCodeSnapshot,
        String documentTitleSnapshot,
        Instant attachedAt,
        String attachedByActorId
    ) {

        public AssetDocumentReference {
        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        documentReferenceId = normalize(documentReferenceId);
        documentCodeSnapshot = normalize(documentCodeSnapshot);
        documentTitleSnapshot = normalize(documentTitleSnapshot);
        attachedByActorId = normalize(attachedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
