/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.dto
 *
 * @Description : Document version summary DTO.
 *
 */
package dz.sh.hidra.modules.documents.application.dto;

import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;

import java.time.Instant;

/**
 * Document version summary DTO.
 */
public record DocumentVersionSummaryDto(
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
