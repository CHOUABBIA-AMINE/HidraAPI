/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrationSyntheticDraftReceiptContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.contract.integration
 * @Description : Single T24-owned idempotent scoped Documents draft receipt export.
 */
package dz.sh.hidra.modules.documents.application.contract.integration;
public interface IntegrationSyntheticDraftReceiptContract {
    record Draft(String documentId,boolean replayed) {}
    void requireAuthority(String companyId,String facilityId);
    Draft create(String companyId,String facilityId,String exchangeId,
            String titleFr,String documentTypeId,String classificationId);
}
