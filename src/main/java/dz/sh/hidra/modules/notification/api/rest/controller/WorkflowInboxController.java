/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : WorkflowInboxController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.controller
 * @Description : Three exact native recipient-scoped T27 notification inbox routes.
 */
package dz.sh.hidra.modules.notification.api.rest.controller;

import dz.sh.hidra.modules.notification.api.rest.response.WorkflowInboxResponses;
import dz.sh.hidra.modules.notification.application.port.in.WorkflowInboxUseCase;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem.Scope;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Objects;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification/companies/{companyId}/facilities/{facilityId}")
public final class WorkflowInboxController {
    private final WorkflowInboxUseCase inbox;
    public WorkflowInboxController(WorkflowInboxUseCase inbox){this.inbox=Objects.requireNonNull(inbox);}
    @GetMapping("/inbox")
    @NativeProtectedOperation @Operation(operationId="listT27NotificationInbox")
    public WorkflowInboxResponses.T27InboxPage list(@PathVariable String companyId,
            @PathVariable String facilityId,@RequestParam(required=false) Integer limit,
            @RequestParam(required=false) String cursor) {
        var page=inbox.list(new Scope(companyId,facilityId),limit,cursor);
        return new WorkflowInboxResponses.T27InboxPage(
                page.items().stream().map(WorkflowInboxController::item).toList(),page.nextCursor());
    }
    @GetMapping("/inbox/{notificationId}")
    @NativeProtectedOperation @Operation(operationId="readT27NotificationInboxItem")
    public WorkflowInboxResponses.T27InboxItem detail(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String notificationId) {
        return item(inbox.detail(new Scope(companyId,facilityId),notificationId));
    }
    @PostMapping("/inbox/{notificationId}/acknowledgement")
    @NativeProtectedOperation @Operation(operationId="acknowledgeT27NotificationInboxItem")
    public WorkflowInboxResponses.T27InboxItem acknowledge(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String notificationId) {
        return item(inbox.acknowledge(new Scope(companyId,facilityId),notificationId));
    }
    private static WorkflowInboxResponses.T27InboxItem item(WorkflowInboxItem item) {
        return new WorkflowInboxResponses.T27InboxItem(item.id(),item.eventCode(),item.messageCode(),
                item.channel().name(),item.deliveryStatus(),item.deliveredAt(),
                item.acknowledgementStatus().name(),item.acknowledgedAt());
    }
}
