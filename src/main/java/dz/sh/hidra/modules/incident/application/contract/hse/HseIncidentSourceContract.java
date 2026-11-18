/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseIncidentSourceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.contract.hse
 * @Description : Reviewed T16 Incident scope and independently approved resolution snapshot.
 */
package dz.sh.hidra.modules.incident.application.contract.hse;

import java.util.Optional;
public interface HseIncidentSourceContract {
    record Source(String id,String code,String title,String companyId,String facilityId,
                  String reviewerId,String reviewVersion){}
    Optional<Source> reviewed(String incidentId,String companyId,String facilityId);
}
