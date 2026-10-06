/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import java.util.Optional;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
@Service
public class PlanningWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private final PlanRevisionRepositoryPort revisions;
    private final OperationalPlanRepositoryPort plans;
    public PlanningWorkflowTargetLookup(PlanRevisionRepositoryPort revisions, OperationalPlanRepositoryPort plans) {
        this.revisions=Objects.requireNonNull(revisions);this.plans=Objects.requireNonNull(plans);
    }
    public String module(){return "planning";}
    public Set<String> targetTypeCodes(){return Set.of("PLAN_REVISION","PLANNING_PLAN");}
    public Optional<Target> eligibleTarget(String code,String id) {
        if(id==null || id.isBlank()) return Optional.empty();
        if("PLAN_REVISION".equals(code)) return revisions.findById(id)
            .filter(r->Set.of("DRAFT","SUBMITTED").contains(r.status().name()))
            .map(r->new Target(r.id(),r.revisionCode(),r.revisionCode()));
        if("PLANNING_PLAN".equals(code)) return plans.findById(id)
            .filter(p->Set.of("DRAFT","SUBMITTED").contains(p.status().name()))
            .map(p->new Target(p.id(),p.code(),p.nameFr()));
        return Optional.empty();
    }
}
