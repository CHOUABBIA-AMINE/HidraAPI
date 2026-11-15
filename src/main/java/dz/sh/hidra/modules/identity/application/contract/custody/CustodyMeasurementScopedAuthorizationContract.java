/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.custody
 * @Description : Checks the signed actor and paired current Custody company and transfer-point grants.
 */
package dz.sh.hidra.modules.identity.application.contract.custody;

public interface CustodyMeasurementScopedAuthorizationContract {
    String actor();
    void require(String permission,String companyId,String transferPointId);
}
