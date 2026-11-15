/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityWorkOrderResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.response
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.assets.api.rest.response;

import dz.sh.hidra.modules.assets.application.port.in.IntegrityWorkOrderUseCase;
import java.time.Instant;
import java.util.List;
public final class IntegrityWorkOrderResponse {
    private IntegrityWorkOrderResponse() {}
    public record Receipt(String workOrderId,String approvalId,String eventId,String operation,
            String status,long version,String actorId,Instant recordedAt,boolean replay) {
        public static Receipt from(IntegrityWorkOrderUseCase.Receipt r) {
            return new Receipt(r.workOrderId(),r.approvalId(),r.eventId(),r.operation(),
                    r.status(),r.version(),r.actorId(),r.recordedAt(),r.replay());
        }
    }
    public record History(String workOrderId,String approvalId,String recommendationId,String findingId,
            String assetId,String assetType,String companyId,String maintainableAssetId,String status,
            long version,String assignedActorId,String completionId,
            List<IntegrityWorkOrderUseCase.Event> events) {
        public static History from(IntegrityWorkOrderUseCase.History h) {
            return new History(h.workOrderId(),h.approvalId(),h.recommendationId(),h.findingId(),
                    h.assetId(),h.assetType(),h.companyId(),h.maintainableAssetId(),h.status(),
                    h.version(),h.assignedActorId(),h.completionId(),h.events());
        }
    }
}
