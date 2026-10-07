/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentContentTransferService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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
import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.io.InputStream;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class DocumentContentTransferService implements UploadDocumentBinaryVersionUseCase, DownloadDocumentVersionContentUseCase {

    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    private final DocumentBinaryStoragePort binaryStoragePort;
    private final DocumentStorageObjectRepositoryPort storageObjectRepositoryPort;
    private final DocumentVersionRepositoryPort versionRepositoryPort;
    private final UploadDocumentVersionUseCase uploadDocumentVersionUseCase;

    private final DocumentsActorContract actors;
    private final CurrentSecurityContext security;
    private final DocumentRepositoryPort documents;

    public DocumentContentTransferService(
            DocumentBinaryStoragePort binaryStoragePort,
            DocumentStorageObjectRepositoryPort storageObjectRepositoryPort,
            DocumentVersionRepositoryPort versionRepositoryPort,
            UploadDocumentVersionUseCase uploadDocumentVersionUseCase,
            DocumentsActorContract actors,CurrentSecurityContext security,DocumentRepositoryPort documents
    ) {
        this.binaryStoragePort = Objects.requireNonNull(binaryStoragePort, "DocumentBinaryStoragePort must not be null.");
        this.storageObjectRepositoryPort = Objects.requireNonNull(storageObjectRepositoryPort, "DocumentStorageObjectRepositoryPort must not be null.");
        this.versionRepositoryPort = Objects.requireNonNull(versionRepositoryPort, "DocumentVersionRepositoryPort must not be null.");
        this.uploadDocumentVersionUseCase = Objects.requireNonNull(uploadDocumentVersionUseCase, "UploadDocumentVersionUseCase must not be null.");
        this.actors=Objects.requireNonNull(actors);this.security=Objects.requireNonNull(security);this.documents=Objects.requireNonNull(documents);
    }

    @Override
    @Transactional
    public DocumentVersionSummaryDto uploadDocumentBinaryVersion(UploadDocumentBinaryVersionCommand command) {
        Objects.requireNonNull(command, "Upload document binary command must not be null.");
        if (command.content() == null) {
            throw invalid("Document binary content must not be null.");
        }

        if(!TransactionSynchronizationManager.isActualTransactionActive() || !TransactionSynchronizationManager.isSynchronizationActive())
            throw new IllegalStateException("Binary upload requires transactional orchestration.");
        String actorId=security.currentPrincipal().filter(p->p.authenticated()).map(p->p.actorId().value())
            .orElseThrow(()->new SecurityException("Authenticated uploader required."));
        if(!actorId.equals(command.uploadedByActorId()==null?null:command.uploadedByActorId().trim()))
            throw new SecurityException("Uploader must match authenticated principal.");
        var actor=actors.eligibleActor(actorId,Instant.now()).filter(a->actorId.equals(a.id()))
            .orElseThrow(()->invalid("Eligible Identity uploader required."));
        if(actor.displayName()==null || actor.displayName().isBlank())throw invalid("Uploader display required.");
        String documentId=requireText(command.documentId(),"Document identity required.");
        if(documents.findById(documentId).isEmpty())throw invalid("Existing document required.");
        if(command.versionNumber()<1)throw invalid("Version number must be positive.");
        if(command.effectiveFrom()!=null && command.effectiveTo()!=null && command.effectiveTo().isBefore(command.effectiveFrom()))
            throw invalid("Effective dates must be ordered.");
        String filename = safeFilename(command.originalFilename());
        String contentType = normalizeContentType(command.contentType());
        String storageObjectId = DocumentId.newId().value();
        AtomicBoolean cleanupAttempted=new AtomicBoolean();
        AtomicReference<Throwable> failure=new AtomicReference<>();
        Runnable cleanup=()->{
            if(!cleanupAttempted.compareAndSet(false,true))return;
            try{binaryStoragePort.delete(storageObjectId);}
            catch(RuntimeException | Error cleanupError){
                if(failure.get()!=null && failure.get()!=cleanupError)failure.get().addSuppressed(cleanupError);
                System.getLogger(DocumentContentTransferService.class.getName()).log(System.Logger.Level.ERROR,
                    "New document blob rollback cleanup failed; owner reconciliation required: "+storageObjectId,cleanupError);
            }
        };
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
            @Override public void afterCompletion(int status){
                if(status==STATUS_ROLLED_BACK)cleanup.run();
                else if(status==STATUS_UNKNOWN)System.getLogger(DocumentContentTransferService.class.getName())
                    .log(System.Logger.Level.ERROR,"Unknown document upload commit outcome; preserve blob for owner reconciliation: "+storageObjectId);
            }
        });
        try {
        DocumentBinaryStoragePort.StoredBinary stored = binaryStoragePort.store(storageObjectId, command.content());
        if (!storageObjectRepositoryPort.isActiveStorageProvider(stored.storageProviderId())) {
            throw invalid("Storage provider must reference an active DOCUMENT_STORAGE_PROVIDER catalog entry.");
        }
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
                actor.id(),
                actor.displayName()
        ));
        }catch(RuntimeException | Error error){failure.set(error);cleanup.run();throw error;}
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
