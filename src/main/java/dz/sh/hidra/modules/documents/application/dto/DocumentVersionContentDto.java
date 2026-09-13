/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionContentDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.dto
 *
 * @Description : Application DTO for streaming one document-version binary.
 *
 */
package dz.sh.hidra.modules.documents.application.dto;

import java.io.InputStream;

public record DocumentVersionContentDto(
        String versionId,
        String originalFilename,
        String contentType,
        long contentLengthBytes,
        String checksumAlgorithm,
        String checksumValue,
        InputStream content
) {
}
