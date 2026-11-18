/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.mapper
 * @Description : Explicit mapping from validated transport records to inward HSE use cases.
 */
package dz.sh.hidra.modules.hse.api.rest.mapper;

import dz.sh.hidra.modules.hse.api.rest.request.HseCapaLifecycleRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaLifecycleResponse;
import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase;
public final class HseCapaLifecycleRestMapper {
    private HseCapaLifecycleRestMapper(){}
    public static HseCapaLifecycleUseCase.AssessImpact impact(
            HseCapaLifecycleRequest.HseCaseImpactAssessmentRequest request) {
        return new HseCapaLifecycleUseCase.AssessImpact(request.requestId(), request.expectedCaseVersion(),
                request.impactDomain(), request.impactTypeId(), request.severity(), request.description());
    }

    public static HseCapaLifecycleResponse.HseCaseImpactAssessmentReceipt impact(
            HseCapaLifecycleUseCase.ImpactReceipt receipt) {
        return new HseCapaLifecycleResponse.HseCaseImpactAssessmentReceipt(receipt.assessmentId(),
                receipt.caseId(), receipt.caseVersion(), receipt.replayed());
    }

    public static HseCapaLifecycleUseCase.Enroll enroll(HseCapaLifecycleRequest.Enroll r){
        return new HseCapaLifecycleUseCase.Enroll(r.requestId(),r.incidentId(),
                r.caseNumber(),r.title(),r.description(),r.caseTypeId(),r.severityId());
    }
    public static HseCapaLifecycleUseCase.Propose propose(HseCapaLifecycleRequest.Propose r){
        return new HseCapaLifecycleUseCase.Propose(r.requestId(),r.expectedCaseVersion(),r.actionNumber(),
                r.actionTypeId(),r.title(),r.description(),r.ownerActorId());
    }
    public static HseCapaLifecycleUseCase.Action action(HseCapaLifecycleRequest.ScopedHseCapaAction r){
        return new HseCapaLifecycleUseCase.Action(r.requestId(),r.expectedVersion(),
                r.reason(),r.referenceId(),r.supportingId(),r.finding());
    }
    public static HseCapaLifecycleResponse.ScopedHsePage<HseCapaLifecycleResponse.CaseView> cases(
            HseCapaLifecycleUseCase.Page<HseCapaLifecycleUseCase.CaseView> page){
        return new HseCapaLifecycleResponse.ScopedHsePage<>(
                page.items().stream().map(HseCapaLifecycleResponse.CaseView::from).toList(),
                page.total(),page.page(),page.size());
    }
    public static HseCapaLifecycleResponse.ScopedHsePage<HseCapaLifecycleResponse.ScopedHseCapaView> capas(
            HseCapaLifecycleUseCase.Page<HseCapaLifecycleUseCase.CapaView> page){
        return new HseCapaLifecycleResponse.ScopedHsePage<>(
                page.items().stream().map(HseCapaLifecycleResponse.ScopedHseCapaView::from).toList(),
                page.total(),page.page(),page.size());
    }
    public static HseCapaLifecycleResponse.ScopedHsePage<HseCapaLifecycleResponse.EventView> events(
            HseCapaLifecycleUseCase.Page<HseCapaLifecycleUseCase.EventView> page){
        return new HseCapaLifecycleResponse.ScopedHsePage<>(
                page.items().stream().map(HseCapaLifecycleResponse.EventView::from).toList(),
                page.total(),page.page(),page.size());
    }
}
