/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentsApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 * @Description : Scoped Documents discovery, byte-verified acceptance and immutable supersession orchestration.
 */
package dz.sh.hidra.modules.documents.application.service;

import dz.sh.hidra.modules.documents.application.command.RegisterDocumentCommand;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionContentDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import dz.sh.hidra.modules.documents.application.port.in.*;
import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase.*;
import dz.sh.hidra.modules.documents.application.port.out.*;
import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentRepositoryPort.Scope;
import dz.sh.hidra.modules.documents.domain.model.*;
import dz.sh.hidra.modules.documents.domain.service.ControlledDocumentPolicy;
import dz.sh.hidra.modules.documents.domain.value.DocumentId;
import dz.sh.hidra.modules.documents.domain.value.DocumentStorageStatus;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ControlledDocumentsApplicationService implements ControlledDocumentsUseCase {
    private final ControlledDocumentRepositoryPort repository;
    private final ControlledDocumentAuthorizationPort authorization;
    private final ControlledDocumentAccessService access;
    private final RegisterDocumentUseCase registration;
    private final UploadDocumentBinaryVersionUseCase binaryUpload;
    private final DownloadDocumentVersionContentUseCase binaryDownload;
    private final DocumentRepositoryPort documents;
    private final DocumentVersionRepositoryPort versions;
    private final DocumentStorageObjectRepositoryPort storageObjects;
    private final DocumentBinaryStoragePort binary;
    private final DocumentsCatalogEligibilityPort catalogs;
    private final ControlledDocumentPolicy policy=new ControlledDocumentPolicy();

    public ControlledDocumentsApplicationService(ControlledDocumentRepositoryPort repository,
            ControlledDocumentAuthorizationPort authorization,ControlledDocumentAccessService access,
            RegisterDocumentUseCase registration,UploadDocumentBinaryVersionUseCase binaryUpload,
            DownloadDocumentVersionContentUseCase binaryDownload,DocumentRepositoryPort documents,
            DocumentVersionRepositoryPort versions,DocumentStorageObjectRepositoryPort storageObjects,
            DocumentBinaryStoragePort binary,DocumentsCatalogEligibilityPort catalogs) {
        this.repository=Objects.requireNonNull(repository);
        this.authorization=Objects.requireNonNull(authorization);
        this.access=Objects.requireNonNull(access);
        this.registration=Objects.requireNonNull(registration);
        this.binaryUpload=Objects.requireNonNull(binaryUpload);
        this.binaryDownload=Objects.requireNonNull(binaryDownload);
        this.documents=Objects.requireNonNull(documents);
        this.versions=Objects.requireNonNull(versions);
        this.storageObjects=Objects.requireNonNull(storageObjects);
        this.binary=Objects.requireNonNull(binary);
        this.catalogs=Objects.requireNonNull(catalogs);
    }
    @Override @Transactional
    public CreateResult create(String company,String facility,Create input) {
        Objects.requireNonNull(input);
        requireText(input.requestId(),"Request ID");
        requireText(input.code(),"Document code");
        requireText(input.titleFr(),"French title");
        requireText(input.documentTypeId(),"Document type");
        requireText(input.classificationId(),"Classification");
        authorize(company,facility,"documents:controlled:create");
        String actor=authorization.currentLocalActor();
        String digest=digest(company,facility,actor,input.code(),input.titleFr(),
                input.documentTypeId(),input.classificationId());
        repository.serializeCreate(company,facility,input.requestId());
        var existing=repository.findCreated(company,facility,input.requestId());
        if(existing.isPresent()){
            if(!digest.equals(existing.get().payloadDigest()))throw conflict();
            return new CreateResult(repository.detail(existing.get().documentId())
                    .orElseThrow(ControlledDocumentsApplicationService::hidden),true);
        }
        var doc=registration.registerDocument(new RegisterDocumentCommand(
                input.code(),null,input.titleFr(),null,input.documentTypeId(),null,
                input.classificationId(),0,"topology","FACILITY",facility,null,null,actor,null));
        repository.bind(new Scope(doc.id(),company,facility,input.requestId(),digest,actor,Instant.now()));
        return new CreateResult(repository.detail(doc.id())
                .orElseThrow(ControlledDocumentsApplicationService::hidden),false);
    }
    @Override @Transactional(readOnly=true)
    public Page<DocumentView> discover(String company,String facility,int page,int size,String code){
        requirePage(page,size);authorize(company,facility,"documents:controlled:read");
        return repository.discover(company,facility,page,size,code);
    }
    @Override @Transactional(readOnly=true)
    public DocumentView detail(String company,String facility,String documentId){
        access.requireBound(documentId,company,facility,"documents:controlled:read");
        return repository.detail(documentId).orElseThrow(ControlledDocumentsApplicationService::hidden);
    }
    @Override @Transactional(readOnly=true)
    public Page<VersionView> versions(String company,String facility,String documentId,int page,int size){
        requirePage(page,size);
        access.requireBound(documentId,company,facility,"documents:controlled:read");
        return repository.versions(documentId,page,size);
    }
    @Override @Transactional(readOnly=true)
    public Page<LinkView> links(String company,String facility,String documentId,int page,int size){
        requirePage(page,size);
        access.requireBound(documentId,company,facility,"documents:controlled:read");
        return repository.links(documentId,page,size);
    }
    @Override @Transactional
    public DocumentVersionSummaryDto upload(String company,String facility,String documentId,
            UploadDocumentBinaryVersionCommand command){
        access.requireBound(documentId,company,facility,"documents:controlled:write");
        if(command==null||!documentId.equals(command.documentId())
                ||!authorization.currentLocalActor().equals(command.uploadedByActorId()))throw hidden();
        return binaryUpload.uploadDocumentBinaryVersion(command);
    }
    @Override @Transactional(readOnly=true)
    public DocumentVersionContentDto content(String company,String facility,String documentId,String versionId){
        access.requireBound(documentId,company,facility,"documents:controlled:read");
        var version=versions.findById(versionId)
                .filter(v->documentId.equals(v.documentId())).orElseThrow(ControlledDocumentsApplicationService::hidden);
        if(version.versionStatus()!=dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus.CURRENT
                && version.versionStatus()!=dz.sh.hidra.modules.documents.domain.value.DocumentVersionStatus.SUPERSEDED)
            throw hidden();
        return binaryDownload.downloadDocumentVersionContent(versionId);
    }
    @Override @Transactional
    public DecisionResult accept(String company,String facility,String documentId,String versionId,Accept input){
        return decide(company,facility,documentId,versionId,Objects.requireNonNull(input).requestId(),
                input.reason(),input.linkRoleId(),null);
    }
    @Override @Transactional
    public DecisionResult supersede(String company,String facility,String documentId,String versionId,Supersede input){
        Objects.requireNonNull(input);
        return decide(company,facility,documentId,versionId,input.requestId(),
                input.reason(),input.linkRoleId(),requireText(input.expectedCurrentVersionId(),"Expected head"));
    }
    private DecisionResult decide(String company,String facility,String documentId,String versionId,
            String requestId,String reason,String linkRoleId,String expected) {
        requireText(requestId,"Request ID");requireText(reason,"Reason");requireText(linkRoleId,"Link role");
        access.requireBound(documentId,company,facility,"documents:controlled:accept");
        var actor=authorization.currentLocalActor();
        String digest=digest(company,facility,documentId,versionId,requestId,reason,linkRoleId,
                expected==null?"INITIAL":expected,actor);
        repository.lockDocument(documentId);
        var replay=repository.decision(documentId,requestId);
        if(replay.isPresent()){
            policy.requireReplay(replay.get(),digest);
            return new DecisionResult(replay.get(),true);
        }
        var document=documents.findById(documentId).orElseThrow(ControlledDocumentsApplicationService::hidden);
        var version=versions.findById(versionId)
                .filter(v->documentId.equals(v.documentId())).orElseThrow(ControlledDocumentsApplicationService::hidden);
        DocumentVersion prior=expected==null?null:versions.findById(expected)
                .filter(v->documentId.equals(v.documentId())).orElseThrow(ControlledDocumentsApplicationService::hidden);
        policy.requireAcceptance(document,version,actor,prior);
        catalogs.requireActive(linkRoleId,"DOCUMENT_LINK_ROLE");
        var stored=storageObjects.findById(version.storageObjectId())
                .orElseThrow(ControlledDocumentsApplicationService::hidden);
        if(stored.storageStatus()!=DocumentStorageStatus.AVAILABLE
                ||stored.contentLengthBytes()!=version.fileSizeBytes()
                ||!"SHA-256".equalsIgnoreCase(stored.checksumAlgorithm())
                ||!"SHA-256".equalsIgnoreCase(version.checksumAlgorithm())
                ||!stored.checksumValue().equalsIgnoreCase(version.checksumValue()))throw conflict();
        try (InputStream verified=binary.verifiedOpen(stored.id(),version.fileSizeBytes(),version.checksumValue())) {
            // The port verifies the complete binary and closes its handle before accepting.
        } catch (IOException exception) {throw new IllegalStateException("Content verification failed.",exception);}
        var current=authorization.requireActiveFacility(facility);
        if(!company.equals(current.companyId()))throw hidden();
        var decision=new ControlledDocumentDecision(DocumentId.newId().value(),documentId,
                versionId,expected,requestId,digest,actor,reason,
                expected==null?"ACCEPT":"SUPERSEDE",version.checksumValue().toLowerCase(Locale.ROOT),
                version.fileSizeBytes(),company,facility,linkRoleId,DocumentId.newId().value(),Instant.now());
        repository.accept(decision,current.code(),current.label());
        return new DecisionResult(decision,false);
    }
    private void authorize(String company,String facility,String permission){
        requireText(company,"Company");requireText(facility,"Facility");
        authorization.require("documents:controlled:read",company,facility);
        if(!permission.equals("documents:controlled:read"))authorization.require(permission,company,facility);
        var current=authorization.requireActiveFacility(facility);
        if(!company.equals(current.companyId()))throw hidden();
    }
    private static void requirePage(int page,int size){
        if(page<0||size<1||size>100)throw new IllegalArgumentException("Invalid page or page size.");
    }
    private static String requireText(String value,String label){
        if(value==null||value.isBlank()||value.length()>500)throw new IllegalArgumentException(label+" is required.");
        return value.trim();
    }
    private static String digest(String... values){
        try {
            MessageDigest hash=MessageDigest.getInstance("SHA-256");
            for(String value:values){
                byte[] raw=String.valueOf(value).getBytes(StandardCharsets.UTF_8);
                hash.update(java.nio.ByteBuffer.allocate(Integer.BYTES).putInt(raw.length).array());
                hash.update(raw);
            }
            return HexFormat.of().formatHex(hash.digest());
        }catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static NoSuchElementException hidden(){
        return new NoSuchElementException("Protected Documents resource unavailable.");
    }
    private static IllegalStateException conflict(){
        return new IllegalStateException("Controlled document state or immutable content conflict.");
    }
}
