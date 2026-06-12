/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Neutral link from a document to business objects.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import java.time.Instant;

    /**
     * Neutral link from a document to business objects.
     *
         * @param id id
     * @param documentId documentId
     * @param documentVersionId documentVersionId
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param linkRoleId linkRoleId
     * @param primaryLink primaryLink
     * @param linkedByActorId linkedByActorId
     * @param linkedAt linkedAt
     * @param unlinkedAt unlinkedAt
     * @param active active
     */
    public record DocumentTargetLink(
            String id,
        String documentId,
        String documentVersionId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String linkRoleId,
        boolean primaryLink,
        String linkedByActorId,
        Instant linkedAt,
        Instant unlinkedAt,
        boolean active
    ) {

        public DocumentTargetLink {
        id = normalize(id);
        documentId = normalize(documentId);
        documentVersionId = normalize(documentVersionId);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        linkRoleId = normalize(linkRoleId);
        linkedByActorId = normalize(linkedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
