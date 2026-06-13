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
import dz.sh.hidra.modules.integration.api.rest.request.RegisterExternalSystemRequest;
import dz.sh.hidra.modules.integration.api.rest.request.StartIntegrationJobRunRequest;
import dz.sh.hidra.modules.integration.api.rest.response.ExternalSystemResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationJobRunResponse;
import dz.sh.hidra.modules.integration.application.port.in.RegisterExternalSystemUseCase;
import dz.sh.hidra.modules.integration.application.port.in.StartIntegrationJobRunUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing integration REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/integration")
public class SpringIntegrationController implements IntegrationController {

    private final RegisterExternalSystemUseCase registerExternalSystemUseCase;
    private final StartIntegrationJobRunUseCase startIntegrationJobRunUseCase;

    public SpringIntegrationController(
            RegisterExternalSystemUseCase registerExternalSystemUseCase,
            StartIntegrationJobRunUseCase startIntegrationJobRunUseCase
    ) {
        this.registerExternalSystemUseCase = Objects.requireNonNull(registerExternalSystemUseCase, "RegisterExternalSystemUseCase must not be null.");
        this.startIntegrationJobRunUseCase = Objects.requireNonNull(startIntegrationJobRunUseCase, "StartIntegrationJobRunUseCase must not be null.");
    }


    @Override
    @PostMapping("/register-external-system")
    public ExternalSystemResponse registerExternalSystem(@Valid @RequestBody RegisterExternalSystemRequest request) {
        Objects.requireNonNull(request, "RegisterExternalSystemRequest must not be null.");
        return IntegrationRestMapper.toResponse(registerExternalSystemUseCase.registerExternalSystem(IntegrationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/start-integration-job-run")
    public IntegrationJobRunResponse startIntegrationJobRun(@Valid @RequestBody StartIntegrationJobRunRequest request) {
        Objects.requireNonNull(request, "StartIntegrationJobRunRequest must not be null.");
        return IntegrationRestMapper.toResponse(startIntegrationJobRunUseCase.startIntegrationJobRun(IntegrationRestMapper.toCommand(request)));
    }

}
