/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.documents
 * @Description : Current LOCAL actor and independent paired company/facility Documents scope.
 */
package dz.sh.hidra.modules.identity.application.contract.documents;

public interface ControlledDocumentScopedAuthorizationContract {
    String currentActor();
    void require(String permission,String companyId,String facilityId);
}
