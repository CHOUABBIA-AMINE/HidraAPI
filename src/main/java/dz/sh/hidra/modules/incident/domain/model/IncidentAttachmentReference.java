/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentAttachmentReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIncidentValueException("IncidentAttachmentReference id must not be blank.");
        }
        // HRA-051 required: incidentId
        if (incidentId == null || incidentId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentAttachmentReference incident id must not be blank.");
        }
        // HRA-051 required: documentReferenceId
        if (documentReferenceId == null || documentReferenceId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentAttachmentReference document reference id must not be blank.");
        }
        // HRA-051 required: uploadedByActorId
        if (uploadedByActorId == null || uploadedByActorId.isBlank()) {
            throw new InvalidIncidentValueException("IncidentAttachmentReference uploaded by actor id must not be blank.");
        }
        // HRA-051 required: uploadedAt
        if (uploadedAt == null) {
            throw new InvalidIncidentValueException("IncidentAttachmentReference uploaded at must not be null.");
        }

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
