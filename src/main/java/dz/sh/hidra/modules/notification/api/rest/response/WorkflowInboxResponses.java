/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : WorkflowInboxResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.response
 * @Description : Safe T27 inbox page and item API schemas without Workflow identities or private recipient fields.
 */
package dz.sh.hidra.modules.notification.api.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

public final class WorkflowInboxResponses {
    private WorkflowInboxResponses(){}
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record T27InboxPage(List<T27InboxItem> items,String nextCursor) {}
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record T27InboxItem(String notificationId,String eventCode,String messageCode,
            String channel,String deliveryStatus,Instant deliveredAt,
            String acknowledgementStatus,Instant acknowledgedAt) {}
}
