/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityWorkOrderStorePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.application.port.in.IntegrityWorkOrderUseCase;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
public interface IntegrityWorkOrderStorePort {
    record State(String workOrderId,String approvalId,String findingId,String recommendationId,
            String companyId,String assetType,String assetId,String maintainableAssetId,
            String status,long version,String assignedActorId,String completionId) { }
    record Event(String workOrderId,String eventId,String commandId,String digest,
            String operation,String status,long version,String actorId,String executionId,
            String verdict,String summary,Instant recordedAt) { }
    State lock(String workOrderId);
    Optional<State> read(String workOrderId);
    Optional<Event> replay(String commandId,String digest);
    boolean hasExistingRecommendation(String recommendationId);
    void createState(State state);
    Event append(State state,String commandId,String digest,String operation,String newStatus,
            String actorId,String newAssignee,String completionId,String verdict,
            String summary,Instant occurredAt);
    List<IntegrityWorkOrderUseCase.Event> history(String workOrderId);
}
