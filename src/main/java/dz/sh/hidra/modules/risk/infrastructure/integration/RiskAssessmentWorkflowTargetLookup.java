/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.integration
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.integration;

import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskAssessmentJpaRepository;
import dz.sh.hidra.modules.risk.domain.value.RiskAssessmentStatus;
import dz.sh.hidra.modules.risk.application.contract.workflow.RiskAssessmentWorkflowAuthorityContract;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class RiskAssessmentWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private final RiskAssessmentJpaRepository assessments;
    private RiskAssessmentWorkflowAuthorityContract t22;
    @org.springframework.beans.factory.annotation.Autowired
    public void governedRisk(RiskAssessmentWorkflowAuthorityContract authority){this.t22=authority;}
    public RiskAssessmentWorkflowTargetLookup(RiskAssessmentJpaRepository assessments) { this.assessments=assessments; }
    public String module() { return "risk"; }
    public Set<String> targetTypeCodes() { return Set.of("RISK_ASSESSMENT"); }
    public Optional<Target> eligibleTarget(String type, String id) {
        if (!targetTypeCodes().contains(type) || id==null || id.isBlank()) return Optional.empty();

        if(t22!=null&&t22.governed(id))t22.requireStart(id);
        return assessments.findById(id).filter(a -> a.status()==RiskAssessmentStatus.UNDER_REVIEW)
                .map(a -> new Target(a.id(),a.assessmentNumber(),a.title()));
    }
    @Override public boolean protectedReadAllowed(String typeCode,String id,ProtectedRead operation) {
        if(!targetTypeCodes().contains(typeCode)||id==null||t22==null||!t22.governed(id))return false;
        try{t22.requireProtectedRead(id);return true;}
        catch(RuntimeException unavailable){return false;}
    }
}
