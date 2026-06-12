/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Application service for document registration, versioning, and linking.
 *
 */
package dz.sh.hidra.modules.documents.application.service;

import dz.sh.hidra.modules.documents.application.command.LinkDocumentToTargetCommand;
import dz.sh.hidra.modules.documents.application.command.RegisterDocumentCommand;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentSummaryDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentTargetLinkSummaryDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import dz.sh.hidra.modules.documents.application.mapper.DocumentsApplicationMapper;
import dz.sh.hidra.modules.documents.application.port.in.LinkDocumentToTargetUseCase;
import dz.sh.hidra.modules.documents.application.port.in.RegisterDocumentUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentVersionUseCase;
import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentTargetLinkRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentVersionRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.Document;
import dz.sh.hidra.modules.documents.domain.model.DocumentTargetLink;
import dz.sh.hidra.modules.documents.domain.model.DocumentVersion;
import dz.sh.hidra.modules.documents.domain.value.DocumentId;
import dz.sh.hidra.modules.documents.domain.value.DocumentStatus;
import dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for document registration, versioning, and linking.
 */
public class DocumentsApplicationService implements RegisterDocumentUseCase, UploadDocumentVersionUseCase, LinkDocumentToTargetUseCase {

    private final DocumentRepositoryPort documentRepositoryPort;
    private final DocumentVersionRepositoryPort versionRepositoryPort;
    private final DocumentTargetLinkRepositoryPort targetLinkRepositoryPort;

    public DocumentsApplicationService(
            DocumentRepositoryPort documentRepositoryPort,
            DocumentVersionRepositoryPort versionRepositoryPort,
            DocumentTargetLinkRepositoryPort targetLinkRepositoryPort
    ) {
        this.documentRepositoryPort = Objects.requireNonNull(documentRepositoryPort, "Document repository port must not be null.");
        this.versionRepositoryPort = Objects.requireNonNull(versionRepositoryPort, "Document version repository port must not be null.");
        this.targetLinkRepositoryPort = Objects.requireNonNull(targetLinkRepositoryPort, "Document target link repository port must not be null.");
    }

    @Override
    public DocumentSummaryDto registerDocument(RegisterDocumentCommand command) {
        Objects.requireNonNull(command, "Register document command must not be null.");
        Instant now = Instant.now();
        Document document = new Document(
                DocumentId.newId().value(),
                command.code(),
                command.titleAr(),
                command.titleFr(),
                command.titleEn(),
                command.documentTypeId(),
                command.documentCategoryId(),
                command.classificationId(),
                command.confidentialityLevel(),
                DocumentStatus.DRAFT,
                null,
                command.ownerModule(),
                command.ownerTargetTypeCode(),
                command.ownerTargetId(),
                command.ownerTargetCodeSnapshot(),
                command.ownerTargetLabelSnapshot(),
                command.createdByActorId(),
                command.createdByDisplayNameSnapshot(),
                now,
                now,
                null
        );
        return DocumentsApplicationMapper.toSummary(documentRepositoryPort.save(document));
    }

    @Override
    public DocumentVersionSummaryDto uploadDocumentVersion(UploadDocumentVersionCommand command) {
        Objects.requireNonNull(command, "Upload document version command must not be null.");
        Instant now = Instant.now();
        DocumentVersion version = new DocumentVersion(
                DocumentId.newId().value(),
                command.documentId(),
                command.versionNumber(),
                command.versionLabel(),
                command.titleAr(),
                command.titleFr(),
                command.titleEn(),
                command.description(),
                command.storageObjectId(),
                command.mimeType(),
                command.originalFilename(),
                command.fileExtension(),
                command.fileSizeBytes(),
                command.checksumAlgorithm(),
                command.checksumValue(),
                command.languageCode(),
                command.documentDate(),
                command.effectiveFrom(),
                command.effectiveTo(),
                DocumentVersionStatus.DRAFT,
                command.uploadedByActorId(),
                command.uploadedByDisplayNameSnapshot(),
                now,
                null,
                null,
                null
        );
        return DocumentsApplicationMapper.toSummary(versionRepositoryPort.save(version));
    }

    @Override
    public DocumentTargetLinkSummaryDto linkDocumentToTarget(LinkDocumentToTargetCommand command) {
        Objects.requireNonNull(command, "Link document command must not be null.");
        DocumentTargetLink link = new DocumentTargetLink(
                DocumentId.newId().value(),
                command.documentId(),
                command.documentVersionId(),
                command.targetModule(),
                command.targetTypeCode(),
                command.targetId(),
                command.targetCodeSnapshot(),
                command.targetLabelSnapshot(),
                command.linkRoleId(),
                command.primaryLink(),
                command.linkedByActorId(),
                Instant.now(),
                null,
                true
        );
        return DocumentsApplicationMapper.toSummary(targetLinkRepositoryPort.save(link));
    }
}
