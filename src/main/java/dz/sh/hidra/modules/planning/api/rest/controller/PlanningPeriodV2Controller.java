/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriodV2Controller
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Exposes authenticated company-scoped period creation.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.api.rest.mapper.PlanningRestMapper;
import dz.sh.hidra.modules.planning.api.rest.request.CreateCompanyPlanningPeriodRequest;
import dz.sh.hidra.modules.planning.api.rest.response.PlanningPeriodResponse;
import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.port.in.CreatePlanningPeriodUseCase;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanningPeriodV2Controller {

    private final CreatePlanningPeriodUseCase createPeriod;

    public PlanningPeriodV2Controller(CreatePlanningPeriodUseCase createPeriod) {
        this.createPeriod = createPeriod;
    }

    @PostMapping("/api/v2/planning/periods")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "403", description = "The company or create authority is unavailable.")
    @ApiResponse(responseCode = "409", description = "A period with this code already exists in the selected company.")
    public PlanningPeriodResponse createCompanyPlanningPeriod(
            @Valid @RequestBody CreateCompanyPlanningPeriodRequest request) {
        var command = new CreatePlanningPeriodCommand(request.code(), request.nameAr(), request.nameFr(),
                request.nameEn(), request.periodTypeId(), request.periodStart(), request.periodEnd(),
                request.timeZone(), null);
        return PlanningRestMapper.toResponse(createPeriod.createCompanyPlanningPeriod(request.companyPartyId(), command));
    }
}
