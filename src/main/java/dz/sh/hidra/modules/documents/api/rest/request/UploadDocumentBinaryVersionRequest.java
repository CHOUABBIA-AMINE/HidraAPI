/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UploadDocumentBinaryVersionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.request
 *
 * @Description : Multipart metadata request for a document-version binary upload.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record UploadDocumentBinaryVersionRequest(
        @NotBlank String documentId,
        @Positive int versionNumber,
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
        String uploadedByDisplayNameSnapshot
) {
}
