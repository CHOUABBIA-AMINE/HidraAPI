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
import dz.sh.hidra.modules.monitoring.application.port.in.RecordDeviationUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing monitoring REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/monitoring")
public class SpringMonitoringController implements MonitoringController {

    private final CreateMonitoringRuleUseCase createMonitoringRuleUseCase;
    private final RecordDeviationUseCase recordDeviationUseCase;

    public SpringMonitoringController(
            CreateMonitoringRuleUseCase createMonitoringRuleUseCase,
            RecordDeviationUseCase recordDeviationUseCase
    ) {
        this.createMonitoringRuleUseCase = Objects.requireNonNull(createMonitoringRuleUseCase, "CreateMonitoringRuleUseCase must not be null.");
        this.recordDeviationUseCase = Objects.requireNonNull(recordDeviationUseCase, "RecordDeviationUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-monitoring-rule")
    public MonitoringRuleResponse createMonitoringRule(@Valid @RequestBody CreateMonitoringRuleRequest request) {
        Objects.requireNonNull(request, "CreateMonitoringRuleRequest must not be null.");
        return MonitoringRestMapper.toResponse(createMonitoringRuleUseCase.createMonitoringRule(MonitoringRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/record-deviation")
    public DeviationResponse recordDeviation(@Valid @RequestBody RecordDeviationRequest request) {
        Objects.requireNonNull(request, "RecordDeviationRequest must not be null.");
        return MonitoringRestMapper.toResponse(recordDeviationUseCase.recordDeviation(MonitoringRestMapper.toCommand(request)));
    }

}
