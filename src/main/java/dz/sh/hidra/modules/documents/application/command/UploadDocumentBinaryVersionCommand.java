/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UploadDocumentBinaryVersionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.command
 *
 * @Description : Application command carrying authoritative metadata plus a document binary stream.
 *
 */
package dz.sh.hidra.modules.documents.application.command;

import java.io.InputStream;
import java.time.LocalDate;

public record UploadDocumentBinaryVersionCommand(
        String documentId,
        int versionNumber,
        String versionLabel,
        String titleAr,
        String titleFr,
        String titleEn,
        String description,
        String languageCode,
        LocalDate documentDate,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String uploadedByActorId,
        String uploadedByDisplayNameSnapshot,
        String originalFilename,
        String contentType,
        InputStream content
) {
}
