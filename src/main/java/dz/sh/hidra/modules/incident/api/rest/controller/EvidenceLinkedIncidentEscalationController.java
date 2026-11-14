/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : EvidenceLinkedIncidentEscalationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.controller
 * @Description : T14 protected operator-origin Incident case escalation.
 */
package dz.sh.hidra.modules.incident.api.rest.controller;

import dz.sh.hidra.modules.incident.api.rest.request.EscalateEvidenceToIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentEscalationReceiptResponse;
import dz.sh.hidra.modules.incident.application.port.in.EscalateEvidenceToIncidentUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/incident/incidents")
public class EvidenceLinkedIncidentEscalationController {
    private final EscalateEvidenceToIncidentUseCase service;
    public EvidenceLinkedIncidentEscalationController(EscalateEvidenceToIncidentUseCase service) {
        this.service=Objects.requireNonNull(service);
    }
    @NativeProtectedOperation @Operation(operationId="escalateVerifiedAlarmOrLeakCandidate")
    @PostMapping("/from-evidence")
    public ResponseEntity<IncidentEscalationReceiptResponse> escalate(
            @Valid @RequestBody EscalateEvidenceToIncidentRequest request) {
        var result=service.escalate(new EscalateEvidenceToIncidentUseCase.Command(
                request.sourceType(),request.sourceId(),request.classificationId(),
                request.severityId(),request.titleFr(),request.reason()));
        return ResponseEntity.status(result.created()?HttpStatus.CREATED:HttpStatus.OK)
                .body(IncidentEscalationReceiptResponse.from(result));
    }
    @NativeProtectedOperation @Operation(operationId="readProtectedIncidentEscalation")
    @GetMapping("/{id}/escalation")
    public IncidentEscalationReceiptResponse escalation(@PathVariable String id) {
        return IncidentEscalationReceiptResponse.from(service.receipt(id));
    }
    @NativeProtectedOperation @Operation(operationId="readProtectedIncidentEscalationTimeline")
    @GetMapping("/{id}/timeline")
    public List<IncidentEscalationReceiptResponse> timeline(@PathVariable String id) {
        return service.timeline(id).stream().map(IncidentEscalationReceiptResponse::from).toList();
    }
    @ExceptionHandler(java.util.NoSuchElementException.class)
    ProblemDetail hidden(java.util.NoSuchElementException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Protected Incident unavailable.");
    }
    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail conflict(IllegalStateException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Conflicting Incident escalation.");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid or ineligible Incident escalation declaration.");
    }
}
