/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportDocumentContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.contract.reporting
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.documents.application.contract.reporting;
public interface ScopedReportDocumentContract {
    record Draft(String documentId,String versionId,String checksum,long length) {}
    final class StorageFailure extends RuntimeException {
        public StorageFailure(Throwable cause) { super(cause); }
    }
    void requireDraftAuthority(String company,String facility);
    Draft draft(String company,String facility,String run,String actor,byte[] bytes);
    String seal(String company,String facility,String run,Draft draft);
    byte[] content(String company,String facility,String run,Draft draft,String acceptance);
}
