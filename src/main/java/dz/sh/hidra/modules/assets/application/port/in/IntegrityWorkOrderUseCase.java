/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityWorkOrderUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.in
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.assets.application.port.in;

import java.time.Instant;
import java.util.List;
public interface IntegrityWorkOrderUseCase {
    record Create(String commandId,String approvalId,String maintainableAssetId,
            String workOrderNumber,String workOrderTypeId,String priorityId,
            String title,String description) { }
    record Action(String commandId,long expectedVersion,String operation,
            String assigneeId,String result,String completionId,String verdict,
            String summary,Instant occurredAt) { }
    record Receipt(String workOrderId,String approvalId,String eventId,String operation,
            String status,long version,String actorId,Instant recordedAt,boolean replay) { }
    record Event(String eventId,String operation,String status,long version,String actorId,
            String executionId,String verdict,String summary,Instant recordedAt) { }
    record History(String workOrderId,String approvalId,String recommendationId,String findingId,
            String assetId,String assetType,String companyId,String maintainableAssetId,
            String status,long version,String assignedActorId,String completionId,
            List<Event> events) { }
    Receipt handoff(Create create);
    Receipt transition(String workOrderId,Action action);
    History history(String workOrderId);
}
