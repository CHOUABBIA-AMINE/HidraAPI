/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.in
 * @Description : Typed, signed, company-and-facility HSE CAPA lifecycle use cases and read models.
 */
package dz.sh.hidra.modules.hse.application.port.in;

import dz.sh.hidra.modules.hse.domain.value.HseImpactDomain;
import dz.sh.hidra.modules.hse.domain.value.HseImpactSeverity;
import java.time.Instant;
import java.util.List;
public interface HseCapaLifecycleUseCase {
    record Enroll(String requestId,String incidentId,String caseNumber,String title,
                  String description,String caseTypeId,String severityId){}
    record Propose(String requestId,long expectedCaseVersion,String actionNumber,String actionTypeId,String title,
                   String description,String ownerActorId){}
    record AssessImpact(String requestId, long expectedCaseVersion, HseImpactDomain impactDomain,
                        String impactTypeId, HseImpactSeverity severity, String description){}
    record ImpactReceipt(String assessmentId, String caseId, long caseVersion, boolean replayed){}
    ImpactReceipt assessImpact(String company, String facility, String caseId, AssessImpact input);
    record Action(String requestId,long expectedVersion,String reason,String referenceId,
                  String supportingId,String finding){}
    record CaseView(String id,String companyId,String facilityId,String incidentId,
                    String caseNumber,String title,String status,String createdBy,Instant createdAt,
                    String closureId){}
    record CapaView(String id,String caseId,String actionNumber,String title,String status,
                    long version,String proposerId,String ownerId,String verifierId,String evidenceId){}
    record EventView(String id,String caseId,String capaId,String operation,String beforeState,
                     String afterState,long version,String actorId,String reason,
                     String referenceId,Instant at){}
    record Page<T>(List<T> items,long total,int page,int size){}
    record CaseResult(CaseView value,boolean replayed){}
    record CapaResult(CapaView value,boolean replayed){}
    CaseResult enroll(String company,String facility,Enroll input);
    /** Optional exact business key; omission preserves unfiltered discovery. */
    Page<CaseView> cases(String company,String facility,int page,int size,String caseNumber);
    default Page<CaseView> cases(String company,String facility,int page,int size) {
        return cases(company,facility,page,size,null);
    }
    CaseView caseDetail(String company,String facility,String caseId);
    CapaResult propose(String company,String facility,String caseId,Propose input);
    Page<CapaView> capas(String company,String facility,String caseId,int page,int size,String actionNumber);
    default Page<CapaView> capas(String company,String facility,String caseId,int page,int size) {
        return capas(company,facility,caseId,page,size,null);
    }
    CapaView capaDetail(String company,String facility,String caseId,String capaId);
    CapaResult approve(String company,String facility,String caseId,String capaId,Action input);
    CapaResult assign(String company,String facility,String caseId,String capaId,Action input);
    CapaResult evidence(String company,String facility,String caseId,String capaId,Action input);
    CapaResult complete(String company,String facility,String caseId,String capaId,Action input);
    CapaResult verify(String company,String facility,String caseId,String capaId,Action input);
    CaseResult close(String company,String facility,String caseId,Action input);
    Page<EventView> history(String company,String facility,String caseId,int page,int size);
}
