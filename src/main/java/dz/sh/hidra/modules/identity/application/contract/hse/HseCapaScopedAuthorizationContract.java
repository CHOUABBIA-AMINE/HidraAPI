/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaScopedAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.hse
 * @Description : Current actor and five distinct CAPA operation permits against exact paired HSE scopes.
 */
package dz.sh.hidra.modules.identity.application.contract.hse;

public interface HseCapaScopedAuthorizationContract {
    record Actor(String id,String displayName){}
    Actor require(String permission,String companyId,String facilityId);
}
