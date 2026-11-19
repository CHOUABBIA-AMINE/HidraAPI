/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : NotificationInboxFacilityOwnershipQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 * @Description : Mandatory Topology-owned active facility association for T27 Notification.
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.documents.ControlledDocumentFacilityOwnershipContract;
import dz.sh.hidra.modules.topology.application.contract.notification.NotificationInboxFacilityOwnershipContract;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class NotificationInboxFacilityOwnershipQueryService
        implements NotificationInboxFacilityOwnershipContract {
    private final ControlledDocumentFacilityOwnershipContract facilities;
    public NotificationInboxFacilityOwnershipQueryService(ControlledDocumentFacilityOwnershipContract facilities) {
        this.facilities=facilities;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Facility> current(String facilityId) {
        return facilities.currentFacility(facilityId).map(f->new Facility(f.id(),f.companyId()));
    }
}
