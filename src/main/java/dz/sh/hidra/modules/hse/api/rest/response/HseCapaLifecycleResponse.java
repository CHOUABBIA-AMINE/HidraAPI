/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.response
 * @Description : Typed scoped HSE case, CAPA, event and idempotency response records.
 */
package dz.sh.hidra.modules.hse.api.rest.response;

import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase;
import java.time.Instant;
import java.util.List;
public final class HseCapaLifecycleResponse {
    private HseCapaLifecycleResponse(){}
    public record CaseView(String id,String companyId,String facilityId,String incidentId,
            String caseNumber,String title,String status,String createdBy,Instant createdAt,String closureId){
        public static CaseView from(HseCapaLifecycleUseCase.CaseView x){
            return new CaseView(x.id(),x.companyId(),x.facilityId(),x.incidentId(),
                    x.caseNumber(),x.title(),x.status(),x.createdBy(),x.createdAt(),x.closureId());
        }
    }
    public record CapaView(String id,String caseId,String actionNumber,String title,String status,
            long version,String proposerId,String ownerId,String verifierId,String evidenceId){
        public static CapaView from(HseCapaLifecycleUseCase.CapaView x){
            return new CapaView(x.id(),x.caseId(),x.actionNumber(),x.title(),x.status(),
                    x.version(),x.proposerId(),x.ownerId(),x.verifierId(),x.evidenceId());
        }
    }
    public record EventView(String id,String caseId,String capaId,String operation,String beforeState,
            String afterState,long version,String actorId,String reason,String referenceId,Instant at){
        public static EventView from(HseCapaLifecycleUseCase.EventView x){
            return new EventView(x.id(),x.caseId(),x.capaId(),x.operation(),x.beforeState(),
                    x.afterState(),x.version(),x.actorId(),x.reason(),x.referenceId(),x.at());
        }
    }
    public record Page<T>(List<T> items,long total,int page,int size){}
    public record CaseReceipt(CaseView value,boolean replayed){
        public static CaseReceipt from(HseCapaLifecycleUseCase.CaseResult x){
            return new CaseReceipt(CaseView.from(x.value()),x.replayed());
        }
    }
    public record CapaReceipt(CapaView value,boolean replayed){
        public static CapaReceipt from(HseCapaLifecycleUseCase.CapaResult x){
            return new CapaReceipt(CapaView.from(x.value()),x.replayed());
        }
    }
}
