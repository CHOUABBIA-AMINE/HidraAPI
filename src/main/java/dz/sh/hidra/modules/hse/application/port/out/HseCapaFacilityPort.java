/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaFacilityPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 * @Description : Current owner-verified Topology facility identity and active company.
 */
package dz.sh.hidra.modules.hse.application.port.out;

import java.util.Optional;
public interface HseCapaFacilityPort {
    record Facility(String id,String companyId,String code,String label){}
    Optional<Facility> active(String facilityId);
}
