/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Exposes read-only planning list and detail endpoints for HWEB-010.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.NominationView;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.OperationalPlanView;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.Page;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanRevisionView;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanTargetView;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanningPeriodView;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/planning")
public class PlanningQueryController {

    private final PlanningQueryUseCase queryUseCase;

    public PlanningQueryController(PlanningQueryUseCase queryUseCase) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "PlanningQueryUseCase must not be null.");
    }

    @GetMapping("/periods")
    public Page<PlanningPeriodView> periods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.periods(page, size);
    }

    @GetMapping("/periods/{id}")
    public PlanningPeriodView period(@PathVariable String id) {
        return queryUseCase.period(id);
    }

    @GetMapping("/operational-plans")
    public Page<OperationalPlanView> operationalPlans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.operationalPlans(page, size);
    }

    @GetMapping("/operational-plans/{id}")
    public OperationalPlanView operationalPlan(@PathVariable String id) {
        return queryUseCase.operationalPlan(id);
    }

    @GetMapping("/revisions")
    public Page<PlanRevisionView> revisions(
            @RequestParam String planId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.revisions(planId, page, size);
    }

    @GetMapping("/revisions/{id}")
    public PlanRevisionView revision(@PathVariable String id) {
        return queryUseCase.revision(id);
    }

    @GetMapping("/nominations")
    public Page<NominationView> nominations(
            @RequestParam String revisionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.nominations(revisionId, page, size);
    }

    @GetMapping("/nominations/{id}")
    public NominationView nomination(@PathVariable String id) {
        return queryUseCase.nomination(id);
    }

    @GetMapping("/targets")
    public Page<PlanTargetView> targets(
            @RequestParam String revisionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.targets(revisionId, page, size);
    }

    @GetMapping("/targets/{id}")
    public PlanTargetView target(@PathVariable String id) {
        return queryUseCase.target(id);
    }
}
