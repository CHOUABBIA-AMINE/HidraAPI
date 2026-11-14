/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.incident
 * @Description : Exact current Incident company and asset native authorization.
 */
package dz.sh.hidra.modules.identity.application.contract.incident;

public interface IncidentScopedAuthorizationContract {
    boolean permitted(String permission, String company, String assetType, String assetId);
}
