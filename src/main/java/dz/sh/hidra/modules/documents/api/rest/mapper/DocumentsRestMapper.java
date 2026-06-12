/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.mapper
 *
 * @Description : Maps documents REST models to application models.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.mapper;

import dz.sh.hidra.modules.documents.api.rest.request.RegisterDocumentRequest;
import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentVersionRequest;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentResponse;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentVersionResponse;
import dz.sh.hidra.modules.documents.application.command.RegisterDocumentCommand;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentSummaryDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;

/**
 * Maps documents REST models to application models.
 */
public final class DocumentsRestMapper {

    private DocumentsRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RegisterDocumentCommand toCommand(RegisterDocumentRequest request) {
        return new RegisterDocumentCommand(request.code(), request.titleAr(), request.titleFr(), request.titleEn(), request.documentTypeId(), request.documentCategoryId(), request.classificationId(), request.confidentialityLevel(), request.ownerModule(), request.ownerTargetTypeCode(), request.ownerTargetId(), request.ownerTargetCodeSnapshot(), request.ownerTargetLabelSnapshot(), request.createdByActorId(), request.createdByDisplayNameSnapshot());
    }

    public static UploadDocumentVersionCommand toCommand(UploadDocumentVersionRequest request) {
        return new UploadDocumentVersionCommand(request.documentId(), request.versionNumber(), request.versionLabel(), request.titleAr(), request.titleFr(), request.titleEn(), request.description(), request.storageObjectId(), request.mimeType(), request.originalFilename(), request.fileExtension(), request.fileSizeBytes(), request.checksumAlgorithm(), request.checksumValue(), request.languageCode(), request.documentDate(), request.effectiveFrom(), request.effectiveTo(), request.uploadedByActorId(), request.uploadedByDisplayNameSnapshot());
    }

    public static DocumentResponse toResponse(DocumentSummaryDto dto) {
        return new DocumentResponse(dto.id(), dto.code(), dto.titleFr(), dto.documentTypeId(), dto.classificationId(), dto.confidentialityLevel(), dto.status(), dto.currentVersionId(), dto.ownerModule(), dto.ownerTargetId(), dto.createdAt());
    }

    public static DocumentVersionResponse toResponse(DocumentVersionSummaryDto dto) {
        return new DocumentVersionResponse(dto.id(), dto.documentId(), dto.versionNumber(), dto.versionLabel(), dto.mimeType(), dto.originalFilename(), dto.fileSizeBytes(), dto.checksumValue(), dto.versionStatus(), dto.uploadedAt());
    }
}
