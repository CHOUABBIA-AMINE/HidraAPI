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
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class RiskAssessmentWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private final RiskAssessmentJpaRepository assessments;
    public RiskAssessmentWorkflowTargetLookup(RiskAssessmentJpaRepository assessments) { this.assessments=assessments; }
    public String module() { return "risk"; }
    public Set<String> targetTypeCodes() { return Set.of("RISK_ASSESSMENT"); }
    public Optional<Target> eligibleTarget(String type, String id) {
        if (!targetTypeCodes().contains(type) || id==null || id.isBlank()) return Optional.empty();
        return assessments.findById(id).filter(a -> a.status()==RiskAssessmentStatus.UNDER_REVIEW)
                .map(a -> new Target(a.id(),a.assessmentNumber(),a.title()));
    }
}
