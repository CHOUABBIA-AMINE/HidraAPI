/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleStorePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 * @Description : Atomic HSE-only current scope, locks, CAS, immutable decisions and readback.
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase.*;
import dz.sh.hidra.modules.hse.domain.model.HseCapaEvidence;
import dz.sh.hidra.modules.hse.domain.model.HseCapaVerification;
import dz.sh.hidra.modules.hse.domain.value.CapaStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
public interface HseCapaLifecycleStorePort {
    record Scope(String caseId,String companyId,String facilityId,String incidentId,
                 String createRequestId,String createDigest,String actorId,Instant createdAt){}
    record State(String capaId,String caseId,CapaStatus status,long version,
                 String proposerId,String ownerId,String verifierId,String evidenceId){}
    record Receipt(String id,String caseId,String capaId,String requestId,String digest,
                   String actorId,String operation,String beforeState,String afterState,
                   long version,String referenceId,Instant at){}
    void serializeEnrollment(String company,String facility,String requestId);
    Optional<Scope> created(String company,String facility,String requestId);
    void bind(Scope scope);
    Optional<Scope> scope(String caseId);
    void lockCase(String caseId);
    Page<CaseView> cases(String company,String facility,int page,int size);
    CaseView caseView(String caseId);
    void addCapa(String capaId,String caseId,String proposerId,String ownerId,String requestId,
                 String digest,String actorId,Instant at);
    void lockCapa(String capaId);
    State state(String caseId,String capaId);
    Page<CapaView> capas(String caseId,int page,int size);
    CapaView capaView(String caseId,String capaId);
    Optional<Receipt> replay(String caseId,String requestId);
    Receipt advance(String caseId,String capaId,String requestId,String digest,String actorId,
                    String operation,String before,String after,long expectedVersion,
                    String referenceId,String reason,Instant at);
    void setOwner(String capaId,String actorId);
    void attach(HseCapaEvidence evidence);
    List<HseCapaEvidence> evidence(String capaId);
    void verify(HseCapaVerification finding);
    List<HseCapaVerification> verification(String capaId);
    List<State> states(String caseId);
    boolean impactAssessed(String caseId);
    String closeCase(String caseId,String actorId,String actorDisplay,String reason,
                     HseCapaWorkflowPort.Decision decision,String requestId,String digest,
                     long expectedVersion,Instant at);
    Page<EventView> history(String caseId,int page,int size);
}
