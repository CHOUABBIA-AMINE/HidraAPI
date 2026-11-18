/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrationSyntheticExchangeAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.integration
 * @Description : Current paired native LOCAL grants for scoped Integration T26 operations.
 */
package dz.sh.hidra.modules.identity.application.contract.integration;
public interface IntegrationSyntheticExchangeAuthorizationContract {
    String require(String permission,String companyId,String facilityId);
}
