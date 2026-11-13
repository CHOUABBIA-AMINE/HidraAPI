/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.monitoring
 *
 * @Description : Defines native Monitoring rule permission checks for exact company and asset.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.monitoring;

public interface MonitoringRuleScopedAuthorizationContract {
    enum Operation { CREATE, REVISE, ACTIVATE, READ }
    String require(Operation operation,String company,String assetType,String assetId);
}
