/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentContentTransferService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 *
 * @Description : Application service owning document binary upload and download orchestration.
 *
 */
package dz.sh.hidra.modules.documents.application.service;

import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionContentDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import dz.sh.hidra.modules.documents.application.port.in.DownloadDocumentVersionContentUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentBinaryVersionUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentVersionUseCase;
import dz.sh.hidra.modules.documents.application.port.out.DocumentBinaryStoragePort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentStorageObjectRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentVersionRepositoryPort;
import dz.sh.hidra.modules.documents.domain.exception.DocumentContentTransferException;
import dz.sh.hidra.modules.documents.domain.model.DocumentStorageObject;
import dz.sh.hidra.modules.documents.domain.model.DocumentVersion;
import dz.sh.hidra.modules.documents.domain.value.DocumentId;
import dz.sh.hidra.modules.documents.domain.value.DocumentStorageStatus;
import java.io.InputStream;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class DocumentContentTransferService implements UploadDocumentBinaryVersionUseCase, DownloadDocumentVersionContentUseCase {

    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    private final DocumentBinaryStoragePort binaryStoragePort;
    private final DocumentStorageObjectRepositoryPort storageObjectRepositoryPort;
    private final DocumentVersionRepositoryPort versionRepositoryPort;
    private final UploadDocumentVersionUseCase uploadDocumentVersionUseCase;

    public DocumentContentTransferService(
            DocumentBinaryStoragePort binaryStoragePort,
            DocumentStorageObjectRepositoryPort storageObjectRepositoryPort,
            DocumentVersionRepositoryPort versionRepositoryPort,
            UploadDocumentVersionUseCase uploadDocumentVersionUseCase
    ) {
        this.binaryStoragePort = Objects.requireNonNull(binaryStoragePort, "DocumentBinaryStoragePort must not be null.");
        this.storageObjectRepositoryPort = Objects.requireNonNull(storageObjectRepositoryPort, "DocumentStorageObjectRepositoryPort must not be null.");
        this.versionRepositoryPort = Objects.requireNonNull(versionRepositoryPort, "DocumentVersionRepositoryPort must not be null.");
        this.uploadDocumentVersionUseCase = Objects.requireNonNull(uploadDocumentVersionUseCase, "UploadDocumentVersionUseCase must not be null.");
    }

    @Override
    public DocumentVersionSummaryDto uploadDocumentBinaryVersion(UploadDocumentBinaryVersionCommand command) {
        Objects.requireNonNull(command, "Upload document binary command must not be null.");
        if (command.content() == null) {
            throw invalid("Document binary content must not be null.");
        }

        String filename = safeFilename(command.originalFilename());
        String contentType = normalizeContentType(command.contentType());
        String storageObjectId = DocumentId.newId().value();
        DocumentBinaryStoragePort.StoredBinary stored = binaryStoragePort.store(storageObjectId, command.content());
        Instant now = Instant.now();

        storageObjectRepositoryPort.save(new DocumentStorageObject(
                storageObjectId,
                stored.storageProviderId(),
                stored.bucketOrContainer(),
                stored.objectKey(),
                null,
                stored.encrypted(),
                stored.encryptionKeyReference(),
                stored.contentLengthBytes(),
                contentType,
                stored.checksumAlgorithm(),
                stored.checksumValue(),
                DocumentStorageStatus.AVAILABLE,
                now,
                now
        ));

        return uploadDocumentVersionUseCase.uploadDocumentVersion(new UploadDocumentVersionCommand(
                command.documentId(),
                command.versionNumber(),
                command.versionLabel(),
                command.titleAr(),
                command.titleFr(),
                command.titleEn(),
                command.description(),
                storageObjectId,
                contentType,
                filename,
                fileExtension(filename),
                stored.contentLengthBytes(),
                stored.checksumAlgorithm(),
                stored.checksumValue(),
                command.languageCode(),
                command.documentDate(),
                command.effectiveFrom(),
                command.effectiveTo(),
                command.uploadedByActorId(),
                command.uploadedByDisplayNameSnapshot()
        ));
    }

    @Override
    public DocumentVersionContentDto downloadDocumentVersionContent(String versionId) {
        String normalizedVersionId = requireText(versionId, "Document version id must not be blank.");
        DocumentVersion version = versionRepositoryPort.findById(normalizedVersionId)
                .orElseThrow(() -> notFound("Document version was not found: " + normalizedVersionId));
        String storageObjectId = requireText(version.storageObjectId(), "Document version has no storage object.");
        DocumentStorageObject storageObject = storageObjectRepositoryPort.findById(storageObjectId)
                .orElseThrow(() -> notFound("Document storage object was not found for version: " + normalizedVersionId));

        if (storageObject.storageStatus() != DocumentStorageStatus.AVAILABLE || !binaryStoragePort.available(storageObjectId)) {
            throw notFound("Document content is not available for version: " + normalizedVersionId);
        }

        InputStream content = binaryStoragePort.open(storageObjectId);
        return new DocumentVersionContentDto(
                version.id(),
                safeFilename(version.originalFilename()),
                normalizeContentType(version.mimeType()),
                storageObject.contentLengthBytes(),
                storageObject.checksumAlgorithm(),
                storageObject.checksumValue(),
                content
        );
    }

    private static String safeFilename(String value) {
        if (value == null || value.isBlank()) {
            return "document.bin";
        }
        String normalized = value.replace('\\', '/').trim();
        int lastSlash = normalized.lastIndexOf('/');
        String filename = lastSlash >= 0 ? normalized.substring(lastSlash + 1) : normalized;
        filename = filename.replace("\r", "_").replace("\n", "_").trim();
        return filename.isBlank() ? "document.bin" : filename;
    }

    private static String fileExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot <= 0 || dot == filename.length() - 1) {
            return null;
        }
        return filename.substring(dot + 1).toLowerCase(Locale.ROOT);
    }

    private static String normalizeContentType(String value) {
        return value == null || value.isBlank() ? DEFAULT_CONTENT_TYPE : value.trim();
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw invalid(message);
        }
        return value.trim();
    }

    private static DocumentContentTransferException invalid(String message) {
        return new DocumentContentTransferException(DocumentContentTransferException.Reason.INVALID_CONTENT, message);
    }

    private static DocumentContentTransferException notFound(String message) {
        return new DocumentContentTransferException(DocumentContentTransferException.Reason.NOT_FOUND, message);
    }
}
