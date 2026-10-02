/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.response
 *
 * @Description : REST response for document version.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.response;

import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;

import java.time.Instant;

/**
 * REST response for document version.
 */
public record DocumentVersionResponse(
        String id,
        String documentId,
        int versionNumber,
        String versionLabel,
        String mimeType,
        String originalFilename,
        long fileSizeBytes,
        String checksumValue,
        DocumentVersionStatus versionStatus,
        Instant uploadedAt
) {
}
