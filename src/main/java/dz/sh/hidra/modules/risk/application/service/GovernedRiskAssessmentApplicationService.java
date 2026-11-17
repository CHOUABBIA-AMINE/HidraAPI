/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : GovernedRiskAssessmentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.service
 * @Description : Current scoped immutable Risk result and Workflow authority.
 */
package dz.sh.hidra.modules.risk.application.service;

import dz.sh.hidra.modules.risk.application.contract.workflow.RiskAssessmentWorkflowAuthorityContract;
import dz.sh.hidra.modules.risk.application.port.in.ReadGovernedRiskAssessmentUseCase;
import dz.sh.hidra.modules.risk.application.port.out.GovernedRiskAssessmentPort;
import dz.sh.hidra.modules.risk.domain.model.GovernedRiskAssessment;
import dz.sh.hidra.modules.identity.application.contract.risk.RiskAssessmentScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.risk.RiskAssessmentAssetOwnershipContract;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GovernedRiskAssessmentApplicationService implements
        ReadGovernedRiskAssessmentUseCase,RiskAssessmentWorkflowAuthorityContract {
    private final GovernedRiskAssessmentPort records;
    private final RiskAssessmentScopedAuthorizationContract rights;
    private final RiskAssessmentAssetOwnershipContract topology;
    private static final ThreadLocal<ApprovalArm> FINALIZING=new ThreadLocal<>();
    private static final class ApprovalArm {
        final String assessmentId;
        ReviewEvidence review;
        ApprovalArm(String id){this.assessmentId=id;}
    }
    public GovernedRiskAssessmentApplicationService(GovernedRiskAssessmentPort records,
            RiskAssessmentScopedAuthorizationContract rights,RiskAssessmentAssetOwnershipContract topology) {
        this.records=Objects.requireNonNull(records);this.rights=Objects.requireNonNull(rights);
        this.topology=Objects.requireNonNull(topology);
    }
    @Override @Transactional
    public GovernedRiskAssessment read(String id) {
        require(id,"risk:assessments:read");
        return records.projection(id).orElseThrow(GovernedRiskAssessmentApplicationService::unavailable);
    }
    @Override @Transactional
    public List<GovernedRiskAssessment.Decision> decisionHistory(String id) {
        require(id,"risk:assessments:read");
        return records.projection(id).orElseThrow(GovernedRiskAssessmentApplicationService::unavailable).decisions();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean governed(String id){ return records.scope(id).isPresent(); }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireStart(String id){ require(id,"risk:assessments:author"); }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireProtectedRead(String id) {
        // An assigned reviewer may inspect the governed Workflow only with current scope rights.
        try { require(id,"risk:assessments:read"); }
        catch(NoSuchElementException unavailable) { require(id,"risk:assessments:review"); }
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireReview(String id,String actor) {
        require(id,"risk:assessments:review");
        var assessment=records.projection(id).orElseThrow(GovernedRiskAssessmentApplicationService::unavailable);
        if(assessment.assessorId()==null||assessment.assessorId().equals(actor)
                ||!Objects.equals(rights.currentActor(),actor)
                ||records.submission(id).isEmpty()) throw unavailable();
    }
    @Override public boolean finalizationArmed(String id) {
        return id!=null&&FINALIZING.get()!=null&&id.equals(FINALIZING.get().assessmentId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public <T> T withFinalization(String id,Supplier<T> action) {
        Objects.requireNonNull(action);
        String actor=rights.currentActor();
        requireReview(id,actor);
        if(FINALIZING.get()!=null)throw unavailable();
        FINALIZING.set(new ApprovalArm(id));
        try { return action.get(); }
        finally { FINALIZING.remove(); }
    }
    @Override public void captureReview(String id,String taskId,java.time.Instant at) {
        if(!finalizationArmed(id)||taskId==null||taskId.isBlank()||at==null)throw unavailable();
        FINALIZING.get().review=new ReviewEvidence(taskId,at);
    }
    @Override public ReviewEvidence reviewEvidence(String id) {
        if(!finalizationArmed(id)||FINALIZING.get().review==null)throw unavailable();
        return FINALIZING.get().review;
    }
    private GovernedRiskAssessmentPort.Scope require(String id,String permission) {
        var scope=records.scope(id).orElseThrow(GovernedRiskAssessmentApplicationService::unavailable);
        var owner=topology.currentFacility(scope.facilityId())
                .orElseThrow(GovernedRiskAssessmentApplicationService::unavailable);
        if(!scope.companyPartyId().equals(owner.companyPartyId()))throw unavailable();
        rights.require(permission,scope.companyPartyId(),scope.facilityId());
        return scope;
    }
    private static NoSuchElementException unavailable(){
        return new NoSuchElementException("Protected Risk assessment unavailable.");
    }
}
