/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : WorkflowInboxUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.in
 * @Description : Recipient-only bounded WEB inbox read and idempotent acknowledgement operations.
 */
package dz.sh.hidra.modules.notification.application.port.in;

import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem.Scope;
import java.util.List;

public interface WorkflowInboxUseCase {
    record Page(List<WorkflowInboxItem> items,String nextCursor) {
        public Page { items=List.copyOf(items); }
    }
    Page list(Scope scope,Integer limit,String cursor);
    WorkflowInboxItem detail(Scope scope,String notificationId);
    WorkflowInboxItem acknowledge(Scope scope,String notificationId);
}
