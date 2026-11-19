/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : WorkflowInboxAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.integration
 * @Description : Live Identity and Topology owner bridge for exact recipient-scoped T27 operations.
 */
package dz.sh.hidra.modules.notification.infrastructure.integration;

import dz.sh.hidra.modules.identity.application.contract.notification.NotificationInboxAuthorizationContract;
import dz.sh.hidra.modules.notification.application.port.out.WorkflowInboxAccessPort;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem.Scope;
import dz.sh.hidra.modules.topology.application.contract.notification.NotificationInboxFacilityOwnershipContract;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class WorkflowInboxAccessAdapter implements WorkflowInboxAccessPort {
    private final NotificationInboxAuthorizationContract identity;
    private final NotificationInboxFacilityOwnershipContract topology;
    public WorkflowInboxAccessAdapter(NotificationInboxAuthorizationContract identity,
            NotificationInboxFacilityOwnershipContract topology) {
        this.identity=Objects.requireNonNull(identity);
        this.topology=Objects.requireNonNull(topology);
    }
    @Override public String require(Scope scope,Action action) {
        String actor=identity.require("notification:inbox:"+action.name().toLowerCase(java.util.Locale.ROOT),
                scope.companyId(),scope.facilityId());
        if (topology.current(scope.facilityId()).filter(owner->
                scope.facilityId().equals(owner.id()) && scope.companyId().equals(owner.companyId()))
                .isEmpty()) throw new NoSuchElementException("Protected inbox unavailable.");
        return actor;
    }
}
