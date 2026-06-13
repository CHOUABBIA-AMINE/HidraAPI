/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringPlanningController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing planning REST endpoints.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;
import dz.sh.hidra.modules.planning.api.rest.mapper.PlanningRestMapper;
import dz.sh.hidra.modules.planning.api.rest.request.CreateOperationalPlanRequest;
import dz.sh.hidra.modules.planning.api.rest.request.CreatePlanningPeriodRequest;
import dz.sh.hidra.modules.planning.api.rest.response.OperationalPlanResponse;
import dz.sh.hidra.modules.planning.api.rest.response.PlanningPeriodResponse;
import dz.sh.hidra.modules.planning.application.port.in.CreateOperationalPlanUseCase;
import dz.sh.hidra.modules.planning.application.port.in.CreatePlanningPeriodUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing planning REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/planning")
public class SpringPlanningController implements PlanningController {

    private final CreateOperationalPlanUseCase createOperationalPlanUseCase;
    private final CreatePlanningPeriodUseCase createPlanningPeriodUseCase;

    public SpringPlanningController(
            CreateOperationalPlanUseCase createOperationalPlanUseCase,
            CreatePlanningPeriodUseCase createPlanningPeriodUseCase
    ) {
        this.createOperationalPlanUseCase = Objects.requireNonNull(createOperationalPlanUseCase, "CreateOperationalPlanUseCase must not be null.");
        this.createPlanningPeriodUseCase = Objects.requireNonNull(createPlanningPeriodUseCase, "CreatePlanningPeriodUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "planning",
                "mission", "Support operational planning periods and plans for hydrocarbon transportation activities.",
                "objectives", List.of(
                "Create planning periods.",
                "Create operational plans."
        ),
                "operations", List.of(
                "createOperationalPlan",
                "createPlanningPeriod"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/planning/operational-plans",
                "POST /api/v1/planning/periods"
        )
        );
    }

    @Override
    @PostMapping({"/create-operational-plan", "/operational-plans"})
    public OperationalPlanResponse createOperationalPlan(@Valid @RequestBody CreateOperationalPlanRequest request) {
        Objects.requireNonNull(request, "CreateOperationalPlanRequest must not be null.");
        return PlanningRestMapper.toResponse(createOperationalPlanUseCase.createOperationalPlan(PlanningRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/create-planning-period", "/periods"})
    public PlanningPeriodResponse createPlanningPeriod(@Valid @RequestBody CreatePlanningPeriodRequest request) {
        Objects.requireNonNull(request, "CreatePlanningPeriodRequest must not be null.");
        return PlanningRestMapper.toResponse(createPlanningPeriodUseCase.createPlanningPeriod(PlanningRestMapper.toCommand(request)));
    }

}
