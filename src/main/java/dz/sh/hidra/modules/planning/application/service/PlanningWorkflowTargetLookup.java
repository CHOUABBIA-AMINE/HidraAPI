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
    private final PlanningAccessService access;
    public PlanningWorkflowTargetLookup(PlanRevisionRepositoryPort revisions,
            OperationalPlanRepositoryPort plans, PlanningAccessService access) {
        this.revisions=Objects.requireNonNull(revisions);this.plans=Objects.requireNonNull(plans);this.access=Objects.requireNonNull(access);
    }
    public String module(){return "planning";}
    public Set<String> targetTypeCodes(){return Set.of("PLAN_REVISION","PLANNING_PLAN");}
    public Optional<Target> eligibleTarget(String code,String id) {
        if(id==null || id.isBlank()) return Optional.empty();
        if("PLAN_REVISION".equals(code)) return revisions.findById(id)
            .filter(r->Set.of("DRAFT","SUBMITTED").contains(r.status().name()))
            .filter(r -> authorized(r.id(), r.planId(), r.status().name()))
            .map(r->new Target(r.id(),r.revisionCode(),r.revisionCode()));
        if("PLANNING_PLAN".equals(code)) return plans.findById(id)
            .filter(p->Set.of("DRAFT","SUBMITTED").contains(p.status().name()))
            .filter(p -> canRead(p, p.status().name()))
            .map(p->new Target(p.id(),p.code(),p.nameFr()));
        return Optional.empty();
    }
    @Override
    public boolean protectedReadAllowed(String typeCode, String revisionId, ProtectedRead operation) {
        if (!"PLAN_REVISION".equals(typeCode) || operation == null) return false;
        return revisions.findById(revisionId)
                .filter(r -> plans.findById(r.planId())
                        .filter(plan -> canRead(plan, r.status().name())).isPresent()).isPresent();
    }

    private boolean authorized(String revisionId, String planId, String status) {
        return plans.findById(planId)
                .filter(plan -> revisionId.equals(plan.currentRevisionId())
                        || revisionId.equals(plan.approvedRevisionId()))
                .filter(plan -> canRead(plan, status)).isPresent();
    }

    private boolean canRead(dz.sh.hidra.modules.planning.domain.model.OperationalPlan plan, String status) {
        try {
            try {
                access.planAuthor(plan);
                return true;
            } catch (RuntimeException notAuthor) {
                if (!"DRAFT".equals(status)) {
                    access.planReviewer(plan);
                    return true;
                }
                return false;
            }
        } catch (RuntimeException denied) {
            return false;
        }
    }
}

