/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringMonitoringController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing monitoring REST endpoints.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.controller;
import dz.sh.hidra.modules.monitoring.api.rest.mapper.MonitoringRestMapper;
import dz.sh.hidra.modules.monitoring.api.rest.request.CreateMonitoringRuleRequest;
import dz.sh.hidra.modules.monitoring.api.rest.request.RecordDeviationRequest;
import dz.sh.hidra.modules.monitoring.api.rest.response.DeviationResponse;
import dz.sh.hidra.modules.monitoring.api.rest.response.MonitoringRuleResponse;
import dz.sh.hidra.modules.monitoring.application.port.in.CreateMonitoringRuleUseCase;
import dz.sh.hidra.modules.monitoring.application.port.in.ActivateMonitoringRuleRevisionUseCase;
import dz.sh.hidra.modules.monitoring.api.rest.request.MonitoringRuleRevisionRequests;
import dz.sh.hidra.modules.monitoring.api.rest.response.MonitoringRuleRevisionResponses;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import java.time.Instant;
import dz.sh.hidra.modules.monitoring.application.port.in.RecordDeviationUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing monitoring REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/monitoring")
public class SpringMonitoringController implements MonitoringController {

    private final CreateMonitoringRuleUseCase createMonitoringRuleUseCase;
    private final RecordDeviationUseCase recordDeviationUseCase;
    private final ActivateMonitoringRuleRevisionUseCase lifecycle;

    @Autowired
    public SpringMonitoringController(CreateMonitoringRuleUseCase createMonitoringRuleUseCase,
            RecordDeviationUseCase recordDeviationUseCase,ActivateMonitoringRuleRevisionUseCase lifecycle){
        this.createMonitoringRuleUseCase=Objects.requireNonNull(createMonitoringRuleUseCase);
        this.recordDeviationUseCase=Objects.requireNonNull(recordDeviationUseCase);
        this.lifecycle=Objects.requireNonNull(lifecycle);
    }
    public SpringMonitoringController(CreateMonitoringRuleUseCase createMonitoringRuleUseCase,
            RecordDeviationUseCase recordDeviationUseCase){
        this.createMonitoringRuleUseCase=Objects.requireNonNull(createMonitoringRuleUseCase);
        this.recordDeviationUseCase=Objects.requireNonNull(recordDeviationUseCase);
        this.lifecycle=null;
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "monitoring",
                "mission", "Configure monitoring rules and record deviations for real-time operational awareness.",
                "objectives", List.of(
                "Create monitoring rules.",
                "Record operational deviations from telemetry or manual observations."
        ),
                "operations", List.of(
                "createMonitoringRule",
                "recordDeviation"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/monitoring/rules",
                "POST /api/v1/monitoring/deviations"
        )
        );
    }

    @Override
    @PostMapping({"/create-monitoring-rule", "/rules"})
    @NativeProtectedOperation
    public MonitoringRuleResponse createMonitoringRule(@Valid @RequestBody CreateMonitoringRuleRequest request) {
        Objects.requireNonNull(request, "CreateMonitoringRuleRequest must not be null.");
        return MonitoringRestMapper.toResponse(createMonitoringRuleUseCase.createMonitoringRule(MonitoringRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/record-deviation", "/deviations"})
    public DeviationResponse recordDeviation(@Valid @RequestBody RecordDeviationRequest request) {
        Objects.requireNonNull(request, "RecordDeviationRequest must not be null.");
        return MonitoringRestMapper.toResponse(recordDeviationUseCase.recordDeviation(MonitoringRestMapper.toCommand(request)));
    }

    @PostMapping("/rules/{id}/revisions")
    @NativeProtectedOperation
    public ResponseEntity<MonitoringRuleRevisionResponses.View> createRevision(
            @PathVariable String id,@RequestBody MonitoringRuleRevisionRequests.Draft body){
        var draft=new ActivateMonitoringRuleRevisionUseCase.Draft(
                body.unitId(),body.evidencePolicyCode(),body.validFrom(),body.validTo());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MonitoringRestMapper.toRevisionResponse(lifecycle.revise(id,draft)));
    }

    @PostMapping("/rules/{id}/revisions/{revision}/activate")
    @NativeProtectedOperation
    public MonitoringRuleRevisionResponses.View activateRevision(@PathVariable String id,
            @PathVariable long revision,@RequestBody MonitoringRuleRevisionRequests.Activate body){
        return MonitoringRestMapper.toRevisionResponse(lifecycle.activate(id,revision,
                new ActivateMonitoringRuleRevisionUseCase.Activation(body.expectedHeadRevision(),
                        body.effectiveFrom(),body.effectiveUntil())));
    }

    @GetMapping("/rules/{id}/revisions/{revision}")
    @NativeProtectedOperation
    public MonitoringRuleRevisionResponses.View exactRevision(@PathVariable String id,
            @PathVariable long revision){
        return MonitoringRestMapper.toRevisionResponse(lifecycle.exact(id,revision));
    }

    @GetMapping("/rules/{id}/effective")
    @NativeProtectedOperation
    public MonitoringRuleRevisionResponses.View effectiveRevision(@PathVariable String id,
            @RequestParam Instant at){
        return MonitoringRestMapper.toRevisionResponse(lifecycle.effective(id,at));
    }

    @GetMapping("/rules/{id}/history")
    @NativeProtectedOperation
    public List<MonitoringRuleRevisionResponses.View> ruleHistory(@PathVariable String id,
            @RequestParam(defaultValue="20") int limit){
        return lifecycle.history(id,limit).stream()
                .map(MonitoringRestMapper::toRevisionResponse).toList();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalidRule(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,"Invalid Monitoring rule request."));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> conflictingRule(IllegalStateException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,"Monitoring rule version conflicts with current state."));
    }
    @ExceptionHandler(java.util.NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> unavailableRule(java.util.NoSuchElementException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,"Protected Monitoring rule unavailable."));
    }

}
