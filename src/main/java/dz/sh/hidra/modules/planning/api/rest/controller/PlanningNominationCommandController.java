/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningNominationCommandController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Exposes exact native-protected nomination operations.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.api.rest.request.PlanningNominationCommandRequest;
import dz.sh.hidra.modules.planning.api.rest.response.PlanningNominationCommandResponse;
import dz.sh.hidra.modules.planning.application.port.in.PlanningNominationCommandUseCase;
import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.service.NominationLifecyclePolicy;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/planning")
public class PlanningNominationCommandController {
    private final PlanningNominationCommandUseCase commands;

    public PlanningNominationCommandController(PlanningNominationCommandUseCase commands) {
        this.commands = commands;
    }

    @NativeProtectedOperation
    @Operation(operationId = "createScopedPlanningNomination")
    @PostMapping("/revisions/{revisionId}/nominations")
    public PlanningNominationCommandResponse.Receipt create(@PathVariable String revisionId,
            @Valid @RequestBody PlanningNominationCommandRequest.Create request) {
        var command = new PlanningNominationCommandUseCase.Create(request.commandId(), request.expectedRevisionUpdatedAt(),
                request.code(), request.nominationTypeId(), request.productTypeId(), request.quantity(),
                request.quantityUnitId(), request.rate(), request.rateUnitId(), request.destinationFacilityId(),
                request.periodStart(), request.periodEnd());
        return PlanningNominationCommandResponse.Receipt.from(commands.create(revisionId, command));
    }

    @NativeProtectedOperation
    @Operation(operationId = "disposeScopedPlanningNomination")
    @PostMapping("/nominations/{id}/disposition")
    public PlanningNominationCommandResponse.Receipt dispose(@PathVariable String id,
            @Valid @RequestBody PlanningNominationCommandRequest.Disposition request) {
        var decision = switch (request.decision()) {
            case CONFIRM -> NominationLifecyclePolicy.Decision.CONFIRM;
            case REJECT -> NominationLifecyclePolicy.Decision.REJECT;
        };
        var command = new PlanningNominationCommandUseCase.Disposition(request.commandId(), request.expectedVersion(),
                decision, request.reason());
        return PlanningNominationCommandResponse.Receipt.from(commands.dispose(id, command));
    }

    @NativeProtectedOperation
    @Operation(operationId = "getScopedPlanningNominationHistory")
    @GetMapping("/nominations/{id}/history")
    public List<PlanningNominationCommandResponse.Event> history(@PathVariable String id) {
        return commands.history(id).stream().map(PlanningNominationCommandResponse.Event::from).toList();
    }

    @ExceptionHandler(NoSuchElementException.class)
    ProblemDetail unavailable() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Planning resource unavailable.");
    }

    @ExceptionHandler(InvalidPlanningValueException.class)
    ProblemDetail invalid(InvalidPlanningValueException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(PlanningRevisionConflictException.class)
    ProblemDetail conflict(PlanningRevisionConflictException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
}
