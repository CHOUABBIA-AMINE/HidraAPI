/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaIncidentPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 * @Description : Exact reviewed T16 incident qualification for a FACILITY.
 */
package dz.sh.hidra.modules.hse.application.port.out;

import java.util.Optional;
public interface HseCapaIncidentPort {
    record Source(String id,String code,String title,String companyId,String facilityId,
                  String reviewerId,String reviewVersion){}
    Optional<Source> reviewed(String incidentId,String companyId,String facilityId);
}
