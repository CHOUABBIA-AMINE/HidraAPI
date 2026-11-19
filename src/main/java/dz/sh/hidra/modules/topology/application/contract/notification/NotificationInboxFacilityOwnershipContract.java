/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : NotificationInboxFacilityOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.notification
 * @Description : Current active facility owner scalar projection for T27 access.
 */
package dz.sh.hidra.modules.topology.application.contract.notification;

import java.util.Optional;
public interface NotificationInboxFacilityOwnershipContract {
    record Facility(String id,String companyId){}
    Optional<Facility> current(String facilityId);
}
