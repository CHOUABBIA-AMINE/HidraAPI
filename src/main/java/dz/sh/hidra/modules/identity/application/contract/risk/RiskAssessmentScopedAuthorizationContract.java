/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : RiskAssessmentScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.risk
 * @Description : Identity-owned current paired company and facility Risk authorization.
 */
package dz.sh.hidra.modules.identity.application.contract.risk;

public interface RiskAssessmentScopedAuthorizationContract {
    String currentActor();
    void require(String permission,String companyPartyId,String facilityId);
}
