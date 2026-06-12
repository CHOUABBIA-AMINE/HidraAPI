/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentAttachmentReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.model
 *
 * @Description : Reference to document or file module.
 *
 */
package dz.sh.hidra.modules.incident.domain.model;

import java.time.Instant;

    /**
     * Reference to document or file module.
     *
         * @param id id
     * @param incidentId incidentId
     * @param documentReferenceId documentReferenceId
     * @param documentTypeId documentTypeId
     * @param filenameSnapshot filenameSnapshot
     * @param contentType contentType
     * @param description description
     * @param uploadedByActorId uploadedByActorId
     * @param uploadedAt uploadedAt
     */
    public record IncidentAttachmentReference(
            String id,
        String incidentId,
        String documentReferenceId,
        String documentTypeId,
        String filenameSnapshot,
        String contentType,
        String description,
        String uploadedByActorId,
        Instant uploadedAt
    ) {

        public IncidentAttachmentReference {
        id = normalize(id);
        incidentId = normalize(incidentId);
        documentReferenceId = normalize(documentReferenceId);
        documentTypeId = normalize(documentTypeId);
        filenameSnapshot = normalize(filenameSnapshot);
        contentType = normalize(contentType);
        description = normalize(description);
        uploadedByActorId = normalize(uploadedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
