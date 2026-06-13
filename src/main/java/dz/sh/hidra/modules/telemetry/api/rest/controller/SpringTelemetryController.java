/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringTelemetryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing telemetry REST endpoints.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;

import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryRestMapper;
import dz.sh.hidra.modules.telemetry.api.rest.request.CreateTelemetrySourceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetryPointRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetrySourceResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.CreateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryPointUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing telemetry REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
public final class SpringTelemetryController implements TelemetryController {

    private final CreateTelemetrySourceUseCase createTelemetrySourceUseCase;
    private final RegisterTelemetryPointUseCase registerTelemetryPointUseCase;

    public SpringTelemetryController(
            CreateTelemetrySourceUseCase createTelemetrySourceUseCase,
            RegisterTelemetryPointUseCase registerTelemetryPointUseCase
    ) {
        this.createTelemetrySourceUseCase = Objects.requireNonNull(createTelemetrySourceUseCase, "CreateTelemetrySourceUseCase must not be null.");
        this.registerTelemetryPointUseCase = Objects.requireNonNull(registerTelemetryPointUseCase, "RegisterTelemetryPointUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-telemetry-source")
    public TelemetrySourceResponse createTelemetrySource(@Valid @RequestBody CreateTelemetrySourceRequest request) {
        Objects.requireNonNull(request, "CreateTelemetrySourceRequest must not be null.");
        return TelemetryRestMapper.toResponse(createTelemetrySourceUseCase.createTelemetrySource(TelemetryRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/register-telemetry-point")
    public TelemetryPointResponse registerTelemetryPoint(@Valid @RequestBody RegisterTelemetryPointRequest request) {
        Objects.requireNonNull(request, "RegisterTelemetryPointRequest must not be null.");
        return TelemetryRestMapper.toResponse(registerTelemetryPointUseCase.registerTelemetryPoint(TelemetryRestMapper.toCommand(request)));
    }

}
