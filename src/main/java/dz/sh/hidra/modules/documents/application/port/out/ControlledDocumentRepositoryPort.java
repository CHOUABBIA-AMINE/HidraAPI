/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 * @Description : Scoped controlled-document append-only persistence and guarded history queries.
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase.*;
import dz.sh.hidra.modules.documents.domain.model.ControlledDocumentDecision;
import java.time.Instant;
import java.util.Optional;

public interface ControlledDocumentRepositoryPort {
    record Scope(String documentId,String companyId,String facilityId,String requestId,
                 String payloadDigest,String createdByActorId,Instant createdAt){}
    void serializeCreate(String companyId,String facilityId,String requestId);
    Optional<Scope> findCreated(String companyId,String facilityId,String requestId);
    void bind(Scope scope);
    Optional<Scope> byDocument(String documentId);
    Optional<Scope> byVersion(String versionId);
    Optional<Scope> byStorage(String storageId);
    Page<DocumentView> discover(String companyId,String facilityId,int page,int size,String code);
    Optional<DocumentView> detail(String documentId);
    Page<VersionView> versions(String documentId,int page,int size);
    Page<LinkView> links(String documentId,int page,int size);
    void lockDocument(String documentId);
    Optional<ControlledDocumentDecision> decision(String documentId,String requestId);
    void accept(ControlledDocumentDecision decision,String targetCode,String targetLabel);
}
