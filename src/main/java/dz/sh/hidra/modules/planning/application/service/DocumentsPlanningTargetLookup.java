/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsPlanningTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.documents.application.contract.target.DocumentsOwnedTargetLookup;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import java.util.Optional;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
@Service
public class DocumentsPlanningTargetLookup implements DocumentsOwnedTargetLookup {
    private final PlanRevisionRepositoryPort revisions;
    private final OperationalPlanRepositoryPort plans;
    public DocumentsPlanningTargetLookup(PlanRevisionRepositoryPort revisions, OperationalPlanRepositoryPort plans) {
        this.revisions=Objects.requireNonNull(revisions);this.plans=Objects.requireNonNull(plans);
    }
    public String module(){return "planning";}
    public Set<String> targetTypeCodes(){return Set.of("PLAN_REVISION","OPERATIONAL_PLAN");}
    public Optional<Target> resolve(String code,String id) {
        if(id==null || id.isBlank()) return Optional.empty();
        if("PLAN_REVISION".equals(code)) return revisions.findById(id)
            .map(r->new Target(r.id(),r.revisionCode(),r.revisionCode()));
        if("OPERATIONAL_PLAN".equals(code)) return plans.findById(id)
            .map(p->new Target(p.id(),p.code(),p.nameFr()));
        return Optional.empty();
    }
}
