/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.mapper
 *
 * @Description : Maps documents domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.documents.application.mapper;

import dz.sh.hidra.modules.documents.application.dto.DocumentSummaryDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentTargetLinkSummaryDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import dz.sh.hidra.modules.documents.domain.model.Document;
import dz.sh.hidra.modules.documents.domain.model.DocumentTargetLink;
import dz.sh.hidra.modules.documents.domain.model.DocumentVersion;

/**
 * Maps documents domain models to DTOs.
 */
public final class DocumentsApplicationMapper {

    private DocumentsApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static DocumentSummaryDto toSummary(Document document) {
        return new DocumentSummaryDto(document.id(), document.code(), document.titleFr(), document.documentTypeId(), document.classificationId(), document.confidentialityLevel(), document.status(), document.currentVersionId(), document.ownerModule(), document.ownerTargetId(), document.createdAt());
    }

    public static DocumentVersionSummaryDto toSummary(DocumentVersion version) {
        return new DocumentVersionSummaryDto(version.id(), version.documentId(), version.versionNumber(), version.versionLabel(), version.mimeType(), version.originalFilename(), version.fileSizeBytes(), version.checksumValue(), version.versionStatus(), version.uploadedAt());
    }

    public static DocumentTargetLinkSummaryDto toSummary(DocumentTargetLink link) {
        return new DocumentTargetLinkSummaryDto(link.id(), link.documentId(), link.documentVersionId(), link.targetModule(), link.targetTypeCode(), link.targetId(), link.linkRoleId(), link.primaryLink(), link.linkedAt(), link.active());
    }
}
