/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentsUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.in
 * @Description : Nine scoped controlled-document operator use cases.
 */
package dz.sh.hidra.modules.documents.application.port.in;

import dz.sh.hidra.modules.documents.application.dto.DocumentVersionContentDto;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;

public interface ControlledDocumentsUseCase {
    record Create(String requestId,String code,String titleFr,String documentTypeId,String classificationId){}
    record Accept(String requestId,String reason,String linkRoleId){}
    record Supersede(String requestId,String reason,String linkRoleId,String expectedCurrentVersionId){}
    record DocumentView(String id,String code,String titleFr,String companyId,String facilityId,
            String status,String currentVersionId,String currentChecksum,Instant createdAt){}
    record VersionView(String id,int number,String status,String checksum,long length,
            String uploadedBy,Instant uploadedAt,String supersededBy){}
    record LinkView(String id,String versionId,String targetModule,String targetType,
            String targetId,String roleId,boolean primary,Instant linkedAt){}
    record Page<T>(List<T> items,long total,int page,int size){}
    record CreateResult(DocumentView document,boolean replayed){}
    record DecisionView(String id,String documentId,String versionId,String previousVersionId,
            String action,String requestId,String checksumSha256,long contentLength,
            String companyId,String facilityId,String linkId,Instant decidedAt){}
    record DecisionResult(DecisionView decision,boolean replayed){}
    CreateResult create(String companyId,String facilityId,Create command);
    Page<DocumentView> discover(String companyId,String facilityId,int page,int size,String code);
    DocumentView detail(String companyId,String facilityId,String documentId);
    Page<VersionView> versions(String companyId,String facilityId,String documentId,int page,int size);
    Page<LinkView> links(String companyId,String facilityId,String documentId,int page,int size);
    DocumentVersionSummaryDto upload(String companyId,String facilityId,String documentId,
            dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand command);
    DocumentVersionContentDto content(String companyId,String facilityId,String documentId,String versionId);
    DecisionResult accept(String companyId,String facilityId,String documentId,String versionId,Accept command);
    DecisionResult supersede(String companyId,String facilityId,String documentId,String versionId,Supersede command);
}
