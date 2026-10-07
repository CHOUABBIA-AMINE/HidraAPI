/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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

import org.springframework.stereotype.Service;

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

import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import dz.sh.hidra.modules.documents.application.port.out.DocumentsCatalogEligibilityPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentTargetLookupPort;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Objects;

/**
 * Application service for document registration, versioning, and linking.
 */
@Service
public class DocumentsApplicationService implements RegisterDocumentUseCase, UploadDocumentVersionUseCase, LinkDocumentToTargetUseCase {

    private final DocumentRepositoryPort documentRepositoryPort;
    private final DocumentVersionRepositoryPort versionRepositoryPort;
    private final DocumentTargetLinkRepositoryPort targetLinkRepositoryPort;

    private final DocumentsActorContract actors;
    private final DocumentsCatalogEligibilityPort catalogs;
    private final DocumentTargetLookupPort targets;
    private final CurrentSecurityContext security;

    public DocumentsApplicationService(
            DocumentRepositoryPort documentRepositoryPort,
            DocumentVersionRepositoryPort versionRepositoryPort,
            DocumentTargetLinkRepositoryPort targetLinkRepositoryPort,
            DocumentsActorContract actors, DocumentsCatalogEligibilityPort catalogs,
            DocumentTargetLookupPort targets, CurrentSecurityContext security
    ) {
        this.documentRepositoryPort = Objects.requireNonNull(documentRepositoryPort, "Document repository port must not be null.");
        this.versionRepositoryPort = Objects.requireNonNull(versionRepositoryPort, "Document version repository port must not be null.");
        this.targetLinkRepositoryPort = Objects.requireNonNull(targetLinkRepositoryPort, "Document target link repository port must not be null.");
        this.actors=Objects.requireNonNull(actors);this.catalogs=Objects.requireNonNull(catalogs);
        this.targets=Objects.requireNonNull(targets);this.security=Objects.requireNonNull(security);
    }

    @Override
    @Transactional
    public DocumentSummaryDto registerDocument(RegisterDocumentCommand command) {
        Objects.requireNonNull(command, "Register document command must not be null.");
        Instant now = Instant.now();
        var actor=currentActor(command.createdByActorId(),now);
        catalogs.requireActive(command.documentTypeId(),"DOCUMENT_TYPE");
        catalogs.requireActive(command.classificationId(),"DOCUMENT_CLASSIFICATION");
        if(text(command.documentCategoryId())!=null)catalogs.requireActive(command.documentCategoryId(),"DOCUMENT_CATEGORY");
        String module=text(command.ownerModule()),type=text(command.ownerTargetTypeCode()),id=text(command.ownerTargetId());
        var owner=module==null && type==null && id==null ? null : targets.requireTarget(module,type,id);
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
                module,
                type,
                id,
                owner==null?null:owner.code(),
                owner==null?null:owner.label(),
                actor.id(),
                actor.displayName(),
                now,
                now,
                null
        );
        return DocumentsApplicationMapper.toSummary(documentRepositoryPort.save(document));
    }

    @Override
    @Transactional
    public DocumentVersionSummaryDto uploadDocumentVersion(UploadDocumentVersionCommand command) {
        Objects.requireNonNull(command, "Upload document version command must not be null.");
        Instant now = Instant.now();
        var actor=currentActor(command.uploadedByActorId(),now);
        String documentId=text(command.documentId());
        if(documentId==null || documentRepositoryPort.findById(documentId).isEmpty())
            throw new IllegalArgumentException("Existing document required.");
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
                actor.id(),
                actor.displayName(),
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

    private DocumentsActorContract.Actor currentActor(String supplied,Instant at){
        String id=security.currentPrincipal().filter(p->p.authenticated()).map(p->p.actorId().value())
            .orElseThrow(()->new SecurityException("Authenticated actor required."));
        if(!id.equals(text(supplied)))throw new SecurityException("Actor must match authenticated principal.");
        return actors.eligibleActor(id,at).filter(a->id.equals(a.id()))
            .orElseThrow(()->new IllegalArgumentException("Eligible Identity actor required."));
    }
    private static String text(String value){return value==null || value.isBlank()?null:value.trim();}
}
