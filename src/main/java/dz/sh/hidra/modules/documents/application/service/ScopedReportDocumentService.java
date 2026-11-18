/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportDocumentService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.contract.reporting.ScopedReportDocumentContract;
import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase;
import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentAuthorizationPort;
import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.ScopedReportDocumentMarkerPort;
import dz.sh.hidra.modules.documents.application.port.out.ScopedReportDocumentMarkerPort.Marker;
import dz.sh.hidra.modules.documents.domain.exception.DocumentContentTransferException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class ScopedReportDocumentService implements ScopedReportDocumentContract {
    private static final int MAX_BYTES=65_536;
    private static final String TITLE="GZ2 Absolute Deviation";
    private static final String MIME="application/json";
    private final ControlledDocumentsUseCase documents;
    private final ControlledDocumentAccessService access;
    private final ControlledDocumentAuthorizationPort authority;
    private final ScopedReportDocumentMarkerPort markers;
    private final ControlledDocumentRepositoryPort repository;
    public ScopedReportDocumentService(ControlledDocumentsUseCase documents,ControlledDocumentAccessService access,
            ControlledDocumentAuthorizationPort authority,ScopedReportDocumentMarkerPort markers,
            ControlledDocumentRepositoryPort repository) {
        this.documents=documents; this.access=access; this.authority=authority;
        this.markers=markers; this.repository=repository;
    }
    @Override
    public void requireDraftAuthority(String company,String facility) {
        for (String operation : new String[]{"create","write","read"})
            authority.require("documents:controlled:"+operation,company,facility);
        if (!company.equals(authority.requireActiveFacility(facility).companyId())) throw hidden();
        markers.catalog("DOCUMENT_TYPE");
        markers.catalog("DOCUMENT_CLASSIFICATION");
    }
    @Override
    public Draft draft(String company,String facility,String run,String actor,byte[] bytes) {
        requireDraftAuthority(company,facility);
        if (!actor.equals(authority.currentLocalActor()) || bytes.length==0 || bytes.length>MAX_BYTES)
            throw hidden();
        String checksum=digest(bytes);
        String document=documents.create(company,facility,new ControlledDocumentsUseCase.Create(run,"T25-"+run,
                TITLE,markers.catalog("DOCUMENT_TYPE"),markers.catalog("DOCUMENT_CLASSIFICATION"))).document().id();
        markers.bind(new Marker(document,run,company,facility,null,null,checksum,bytes.length));
        access.beginReportOperation(document,"documents:controlled:write");
        try {
            var result=documents.upload(company,facility,document,new UploadDocumentBinaryVersionCommand(
                    document,1,"v1",null,TITLE,null,null,"fr",null,null,null,actor,null,
                    "absolute-deviation.json",MIME,new ByteArrayInputStream(bytes)));
            if (result.fileSizeBytes()!=bytes.length || !checksum.equals(result.checksumValue()))
                throw new IllegalStateException("Generated report storage integrity conflict.");
            markers.uploaded(document,result.id());
            return new Draft(document,result.id(),checksum,bytes.length);
        } catch (DocumentContentTransferException failure) {
            if (failure.reason()!=DocumentContentTransferException.Reason.STORAGE_FAILURE) throw failure;
            throw new StorageFailure(failure);
        } finally {
            access.finishReportOperation();
        }
    }
    @Override
    public String seal(String company,String facility,String run,Draft draft) {
        authority.require("documents:controlled:read",company,facility);
        authority.require("documents:controlled:accept",company,facility);
        var marker=exact(company,facility,run,draft,true);
        if (marker.acceptanceId()!=null) throw new IllegalStateException("Report already sealed.");
        access.beginReportOperation(draft.documentId(),"documents:controlled:accept");
        try {
            var decision=documents.accept(company,facility,draft.documentId(),draft.versionId(),
                new ControlledDocumentsUseCase.Accept(run,"Accept generated scoped report",
                        markers.catalog("DOCUMENT_LINK_ROLE"))).decision();
            if (!draft.checksum().equals(decision.checksumSha256()) || draft.length()!=decision.contentLength())
                throw new IllegalStateException("Report acceptance integrity conflict.");
            markers.sealed(draft.documentId(),draft.versionId(),decision.id());
            return decision.id();
        } finally {
            access.finishReportOperation();
        }
    }
    @Override
    public byte[] content(String company,String facility,String run,Draft draft,String acceptance) {
        authority.require("documents:controlled:read",company,facility);
        var marker=exact(company,facility,run,draft,false);
        if (acceptance==null || !acceptance.equals(marker.acceptanceId())) throw hidden();
        var decision=repository.acceptedVersion(draft.documentId(),draft.versionId()).orElseThrow(
                ScopedReportDocumentService::hidden);
        if (!acceptance.equals(decision.id())) throw hidden();
        access.beginReportOperation(draft.documentId(),"documents:controlled:read");
        try {
            var content=documents.content(company,facility,draft.documentId(),draft.versionId());
            try (var in=content.content()) {
                if (!MIME.equals(content.contentType()) || content.contentLengthBytes()!=draft.length()
                        || !draft.checksum().equals(content.checksumValue())) throw hidden();
                byte[] bytes=in.readNBytes(MAX_BYTES+1);
                if (bytes.length!=draft.length() || !draft.checksum().equals(digest(bytes))) throw hidden();
                return bytes;
            }
        } catch (IOException failure) {
            throw hidden();
        } finally {
            access.finishReportOperation();
        }
    }
    private Marker exact(String company,String facility,String run,Draft draft,boolean lock) {
        return markers.find(draft.documentId(),lock).filter(m->run.equals(m.runId()) && company.equals(m.companyId())
                && facility.equals(m.facilityId()) && Objects.equals(draft.versionId(),m.versionId())
                && draft.length()==m.length() && draft.checksum().equals(m.checksum()))
                .orElseThrow(ScopedReportDocumentService::hidden);
    }
    private static String digest(byte[] bytes) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)); }
        catch (NoSuchAlgorithmException impossible) { throw new IllegalStateException(impossible); }
    }
    private static NoSuchElementException hidden() { return new NoSuchElementException("Protected document unavailable."); }
}
