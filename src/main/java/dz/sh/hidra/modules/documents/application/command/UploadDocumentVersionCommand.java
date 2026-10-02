/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UploadDocumentVersionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.command
 *
 * @Description : Command to upload document version metadata.
 *
 */
package dz.sh.hidra.modules.documents.application.command;

import java.time.LocalDate;

/**
 * Command to upload document version metadata.
 */
public record UploadDocumentVersionCommand(
        String documentId,
        int versionNumber,
        String versionLabel,
        String titleAr,
        String titleFr,
        String titleEn,
        String description,
        String storageObjectId,
        String mimeType,
        String originalFilename,
        String fileExtension,
        long fileSizeBytes,
        String checksumAlgorithm,
        String checksumValue,
        String languageCode,
        LocalDate documentDate,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String uploadedByActorId,
        String uploadedByDisplayNameSnapshot
) {
}
