/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.controller
 * @Description : Native-scoped T16 signed human Incident investigation routes.
 */
package dz.sh.hidra.modules.incident.api.rest.controller;

import dz.sh.hidra.modules.incident.api.rest.request.IncidentInvestigationRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentInvestigationResponse;
import dz.sh.hidra.modules.incident.application.port.in.IncidentInvestigationUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/incident/incidents/{id}/investigation")
public final class IncidentInvestigationController {
    private final IncidentInvestigationUseCase service;
    public IncidentInvestigationController(IncidentInvestigationUseCase service) {
        this.service=Objects.requireNonNull(service);
    }
    @NativeProtectedOperation @Operation(operationId="readIncidentInvestigationHistory")
    @GetMapping
    public IncidentInvestigationResponse.View history(@PathVariable String id) {
        return IncidentInvestigationResponse.View.from(service.history(id));
    }
    private IncidentInvestigationResponse.Receipt execute(String id,IncidentInvestigationUseCase.Command cmd) {
        return IncidentInvestigationResponse.Receipt.from(service.execute(id,cmd));
    }
    @NativeProtectedOperation @Operation(operationId="assignIncidentInvestigator")
    @PostMapping("/assign")
    public IncidentInvestigationResponse.Receipt assign(@PathVariable String id,
            @Valid @RequestBody IncidentInvestigationRequest.Assign r) {
        return execute(id,new IncidentInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "ASSIGN",null,null,null,r.reason(),null,false,false));
    }
    @NativeProtectedOperation @Operation(operationId="recordIncidentInvestigationEvidence")
    @PostMapping("/evidence")
    public IncidentInvestigationResponse.Receipt evidence(@PathVariable String id,
            @Valid @RequestBody IncidentInvestigationRequest.Evidence r) {
        return execute(id,new IncidentInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "EVIDENCE",r.sourceType(),r.sourceId(),r.direction(),r.explanation(),null,false,false));
    }
    @NativeProtectedOperation @Operation(operationId="recordIncidentInvestigationAction")
    @PostMapping("/actions")
    public IncidentInvestigationResponse.Receipt action(@PathVariable String id,
            @Valid @RequestBody IncidentInvestigationRequest.InvestigationResponseAction r) {
        return execute(id,new IncidentInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "ACTION","RESPONSE_ACTION_TYPE",r.actionTypeId(),null,r.description(),null,false,false));
    }
    @NativeProtectedOperation @Operation(operationId="submitIncidentInvestigationResolution")
    @PostMapping("/resolution")
    public IncidentInvestigationResponse.Receipt resolution(@PathVariable String id,
            @Valid @RequestBody IncidentInvestigationRequest.Resolve r) {
        return execute(id,new IncidentInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "SUBMIT",null,null,null,r.summary(),r.resolutionTypeId(),
                r.correctiveActionRequired(),r.preventiveActionRequired()));
    }
    @NativeProtectedOperation @Operation(operationId="independentlyReviewIncidentResolution")
    @PostMapping("/review")
    public IncidentInvestigationResponse.Receipt review(@PathVariable String id,
            @Valid @RequestBody IncidentInvestigationRequest.Review r) {
        return execute(id,new IncidentInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "REVIEW",null,null,r.verdict(),r.rationale(),null,false,false));
    }
    @NativeProtectedOperation @Operation(operationId="closeReviewedIncidentUnderExistingClosurePolicy")
    @PostMapping("/close")
    public IncidentInvestigationResponse.Receipt close(@PathVariable String id,
            @Valid @RequestBody IncidentInvestigationRequest.Close r) {
        return execute(id,new IncidentInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "CLOSE","WORKFLOW",r.workflowInstanceId(),null,r.closureSummary(),null,false,false));
    }
    @ExceptionHandler(java.util.NoSuchElementException.class)
    ProblemDetail hidden(java.util.NoSuchElementException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Protected Incident unavailable.");
    }
    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail conflict(IllegalStateException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Conflicting Incident investigation revision.");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid Incident investigation declaration.");
    }
}
