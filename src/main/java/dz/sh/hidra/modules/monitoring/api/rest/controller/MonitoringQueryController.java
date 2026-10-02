/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.controller
 *
 * @Description : Exposes monitoring-rule and deviation query APIs for operational workspaces.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.controller;

import dz.sh.hidra.modules.monitoring.application.port.in.MonitoringQueryUseCase;
import dz.sh.hidra.modules.monitoring.application.port.in.MonitoringQueryUseCase.DeviationView;
import dz.sh.hidra.modules.monitoring.application.port.in.MonitoringQueryUseCase.MonitoringRuleView;
import dz.sh.hidra.modules.monitoring.application.port.in.MonitoringQueryUseCase.Page;
import java.time.Instant;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/monitoring")
public class MonitoringQueryController {

    private final MonitoringQueryUseCase useCase;

    public MonitoringQueryController(MonitoringQueryUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase, "MonitoringQueryUseCase must not be null.");
    }

    @GetMapping("/rules")
    public Page<MonitoringRuleView> rules(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String topologyAssetId,
            @RequestParam(required = false) String telemetryPointId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return useCase.rules(status, topologyAssetId, telemetryPointId, page, size);
    }

    @GetMapping("/rules/{id}")
    public MonitoringRuleView rule(@PathVariable String id) {
        return useCase.rule(id);
    }

    @GetMapping("/deviations")
    public Page<DeviationView> deviations(
            @RequestParam(required = false) String planTargetId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String topologyAssetId,
            @RequestParam(required = false) String telemetryPointId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return useCase.deviations(planTargetId, status, severity, topologyAssetId, telemetryPointId, from, to, page, size);
    }

    @GetMapping("/deviations/{id}")
    public DeviationView deviation(@PathVariable String id) {
        return useCase.deviation(id);
    }
}
