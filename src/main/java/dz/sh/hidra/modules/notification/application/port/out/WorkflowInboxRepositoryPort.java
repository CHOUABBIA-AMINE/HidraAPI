/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : WorkflowInboxRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 * @Description : Recipient-keyed immutable delivery and acknowledgement persistence boundary.
 */
package dz.sh.hidra.modules.notification.application.port.out;

import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem.Scope;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface WorkflowInboxRepositoryPort {
    record Cursor(Instant deliveredAt,String id){}
    WorkflowInboxItem deliver(WorkflowInboxItem item);
    List<WorkflowInboxItem> list(Scope scope,String recipient,int max,Cursor before);
    Optional<WorkflowInboxItem> detail(Scope scope,String recipient,String notificationId,boolean lock);
    Instant acknowledge(Scope scope,String recipient,String notificationId);
}
