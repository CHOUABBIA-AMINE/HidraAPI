/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringIntegrationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing integration REST endpoints.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.controller;
import dz.sh.hidra.modules.integration.api.rest.mapper.IntegrationRestMapper;
import dz.sh.hidra.modules.integration.api.rest.request.RecordExchangeMessageRequest;
import dz.sh.hidra.modules.integration.api.rest.request.RegisterExternalSystemRequest;
import dz.sh.hidra.modules.integration.api.rest.request.StartIntegrationJobRunRequest;
import dz.sh.hidra.modules.integration.api.rest.response.ExternalSystemResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationExchangeMessageResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationJobRunResponse;
import dz.sh.hidra.modules.integration.application.port.in.RecordExchangeMessageUseCase;
import dz.sh.hidra.modules.integration.application.port.in.RegisterExternalSystemUseCase;
import dz.sh.hidra.modules.integration.application.port.in.StartIntegrationJobRunUseCase;
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
 * Spring MVC adapter exposing integration REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/integration")
public class SpringIntegrationController implements IntegrationController {

    private final RecordExchangeMessageUseCase recordExchangeMessageUseCase;
    private final RegisterExternalSystemUseCase registerExternalSystemUseCase;
    private final StartIntegrationJobRunUseCase startIntegrationJobRunUseCase;

    public SpringIntegrationController(
            RecordExchangeMessageUseCase recordExchangeMessageUseCase,
            RegisterExternalSystemUseCase registerExternalSystemUseCase,
            StartIntegrationJobRunUseCase startIntegrationJobRunUseCase
    ) {
        this.recordExchangeMessageUseCase = Objects.requireNonNull(recordExchangeMessageUseCase, "RecordExchangeMessageUseCase must not be null.");
        this.registerExternalSystemUseCase = Objects.requireNonNull(registerExternalSystemUseCase, "RegisterExternalSystemUseCase must not be null.");
        this.startIntegrationJobRunUseCase = Objects.requireNonNull(startIntegrationJobRunUseCase, "StartIntegrationJobRunUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "integration",
                "mission", "Coordinate external systems, exchange messages, and integration job runs.",
                "objectives", List.of(
                "Register external systems.",
                "Start integration job runs.",
                "Record exchange messages for traceability."
        ),
                "operations", List.of(
                "recordExchangeMessage",
                "registerExternalSystem",
                "startIntegrationJobRun"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/integration/exchange-messages",
                "POST /api/v1/integration/external-systems",
                "POST /api/v1/integration/job-runs"
        )
        );
    }

    @Override
    @PostMapping({"/record-exchange-message", "/exchange-messages"})
    public IntegrationExchangeMessageResponse recordExchangeMessage(@Valid @RequestBody RecordExchangeMessageRequest request) {
        Objects.requireNonNull(request, "RecordExchangeMessageRequest must not be null.");
        return IntegrationRestMapper.toResponse(recordExchangeMessageUseCase.recordExchangeMessage(IntegrationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/register-external-system", "/external-systems"})
    public ExternalSystemResponse registerExternalSystem(@Valid @RequestBody RegisterExternalSystemRequest request) {
        Objects.requireNonNull(request, "RegisterExternalSystemRequest must not be null.");
        return IntegrationRestMapper.toResponse(registerExternalSystemUseCase.registerExternalSystem(IntegrationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/start-integration-job-run", "/job-runs"})
    public IntegrationJobRunResponse startIntegrationJobRun(@Valid @RequestBody StartIntegrationJobRunRequest request) {
        Objects.requireNonNull(request, "StartIntegrationJobRunRequest must not be null.");
        return IntegrationRestMapper.toResponse(startIntegrationJobRunUseCase.startIntegrationJobRun(IntegrationRestMapper.toCommand(request)));
    }

}
