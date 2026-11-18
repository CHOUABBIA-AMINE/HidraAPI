/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentAccessService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 * @Description : Live record-aware controlled-document gate reused by legacy Documents entry points.
 */
package dz.sh.hidra.modules.documents.application.service;

import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentAuthorizationPort;
import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentRepositoryPort.Scope;
import dz.sh.hidra.modules.documents.application.port.out.ScopedReportDocumentMarkerPort;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ControlledDocumentAccessService {
    private final ControlledDocumentRepositoryPort repository;
    private final ControlledDocumentAuthorizationPort authority;
    private static final Object TRUSTED_UPLOAD_KEY=ControlledDocumentAccessService.class.getName()+".binary";
    private static final Object REPORT_OPERATION_KEY=ControlledDocumentAccessService.class.getName()+".report";
    private record ReportOperation(String documentId,String permission) {}
    private final ScopedReportDocumentMarkerPort reports;
    public ControlledDocumentAccessService(ControlledDocumentRepositoryPort repository,
            ControlledDocumentAuthorizationPort authority,ScopedReportDocumentMarkerPort reports) {
        this.reports=Objects.requireNonNull(reports);
        this.repository=Objects.requireNonNull(repository);
        this.authority=Objects.requireNonNull(authority);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> forDocument(String documentId,String permission) {
        var scope=repository.byDocument(documentId);
        scope.ifPresent(s->require(s,permission));
        return scope;
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> forVersion(String versionId,String permission) {
        var scope=repository.byVersion(versionId);
        scope.ifPresent(s->require(s,permission));
        return scope;
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> forStorage(String storageId,String permission) {
        var scope=repository.byStorage(storageId);
        scope.ifPresent(s->require(s,permission));
        return scope;
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void require(Scope scope,String permission) {
        authority.require("documents:controlled:read",scope.companyId(),scope.facilityId());
        if (!"documents:controlled:read".equals(permission))
            authority.require(permission,scope.companyId(),scope.facilityId());
        var facility=authority.requireActiveFacility(scope.facilityId());
        if(!scope.facilityId().equals(facility.id()) ||
                !scope.companyId().equals(facility.companyId()))
            throw unavailable();
        requireReportAccess(scope,permission);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public Scope requireBound(String documentId,String company,String facility,String permission){
        authority.require("documents:controlled:read",company,facility);
        if(!"documents:controlled:read".equals(permission)) authority.require(permission,company,facility);
        var current=authority.requireActiveFacility(facility);
        if(!company.equals(current.companyId()))throw unavailable();
        var scope=repository.byDocument(documentId).orElseThrow(ControlledDocumentAccessService::unavailable);
        if(!company.equals(scope.companyId()) || !facility.equals(scope.facilityId()))throw unavailable();
        requireReportAccess(scope,permission);
        return scope;
    }
    private void requireReportAccess(Scope scope,String permission) {
        var marker=reports.find(scope.documentId(),false);
        if (marker.isEmpty()) return;
        if (Objects.equals(new ReportOperation(scope.documentId(),permission),
                TransactionSynchronizationManager.getResource(REPORT_OPERATION_KEY))) return;
        authority.requireReportingRead(scope.companyId(),scope.facilityId());
        // The initial draft and acceptance belong to Reporting. Once sealed,
        // T24 may create a successor without changing the pinned output.
        if (marker.get().acceptanceId()==null) throw unavailable();
    }
    void beginReportOperation(String document,String permission) {
        TransactionSynchronizationManager.bindResource(REPORT_OPERATION_KEY,new ReportOperation(document,permission));
    }
    void finishReportOperation() {
        TransactionSynchronizationManager.unbindResource(REPORT_OPERATION_KEY);
    }
    /**
     * Bind a newly stored binary to its one same-transaction metadata insertion.
     * A legacy metadata-only HTTP alias cannot attach an arbitrary existing blob.
     */
    @Transactional(propagation=Propagation.MANDATORY)
    public void beginBinaryMetadata(String documentId,String storageId){
        if(repository.byDocument(documentId).isEmpty()) return;
        if(TransactionSynchronizationManager.hasResource(TRUSTED_UPLOAD_KEY))
            throw new IllegalStateException("Controlled binary metadata already pending.");
        TransactionSynchronizationManager.bindResource(TRUSTED_UPLOAD_KEY,documentId+":"+storageId);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void finishBinaryMetadata(){
        if(TransactionSynchronizationManager.hasResource(TRUSTED_UPLOAD_KEY))
            TransactionSynchronizationManager.unbindResource(TRUSTED_UPLOAD_KEY);
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void requireMetadataUpload(String documentId,String storageId){
        if(forDocument(documentId,"documents:controlled:write").isEmpty())return;
        var bound=TransactionSynchronizationManager.hasResource(TRUSTED_UPLOAD_KEY)
                ?TransactionSynchronizationManager.getResource(TRUSTED_UPLOAD_KEY):null;
        if(!Objects.equals(bound,documentId+":"+storageId))
            throw unavailable();
    }
    @Transactional(propagation=Propagation.MANDATORY)
    public void requireLink(String documentId,String versionId,String targetModule,
            String targetType,String targetId){
        var scope=forDocument(documentId,"documents:controlled:write");
        if(scope.isEmpty())return;
        if(versionId==null || versionId.isBlank() || repository.byVersion(versionId)
                .filter(s->s.documentId().equals(documentId)).isEmpty()
                || !"topology".equalsIgnoreCase(targetModule)
                || !"FACILITY".equalsIgnoreCase(targetType)
                || !scope.get().facilityId().equals(targetId))throw unavailable();
    }
    private static NoSuchElementException unavailable(){
        return new NoSuchElementException("Protected Documents resource unavailable.");
    }
}
