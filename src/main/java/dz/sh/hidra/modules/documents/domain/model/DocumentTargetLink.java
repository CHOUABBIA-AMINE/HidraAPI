/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentTargetLink id must not be blank.");
        }
        // HRA-051 required: documentId
        if (documentId == null || documentId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentTargetLink document id must not be blank.");
        }
        // HRA-051 required: targetTypeCode
        if (targetTypeCode == null || targetTypeCode.isBlank()) {
            throw new InvalidDocumentValueException("DocumentTargetLink target type code must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentTargetLink target id must not be blank.");
        }
        // HRA-051 required: linkRoleId
        if (linkRoleId == null || linkRoleId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentTargetLink link role id must not be blank.");
        }
        // HRA-051 required: linkedByActorId
        if (linkedByActorId == null || linkedByActorId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentTargetLink linked by actor id must not be blank.");
        }
        // HRA-051 required: linkedAt
        if (linkedAt == null) {
            throw new InvalidDocumentValueException("DocumentTargetLink linked at must not be null.");
        }

        if(targetModule==null || targetModule.isBlank())throw new InvalidDocumentValueException("Target module required.");
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
