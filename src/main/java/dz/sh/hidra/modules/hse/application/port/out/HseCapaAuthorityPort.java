/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaAuthorityPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 * @Description : Current signed actor and independent paired company-and-facility authorization.
 */
package dz.sh.hidra.modules.hse.application.port.out;

public interface HseCapaAuthorityPort {
    record Actor(String id,String displayName){}
    Actor require(String permission,String companyId,String facilityId);
}
