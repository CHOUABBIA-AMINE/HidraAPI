/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringHseController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing hse REST endpoints.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.controller;
import dz.sh.hidra.modules.hse.api.rest.mapper.HseRestMapper;
import dz.sh.hidra.modules.hse.api.rest.request.CloseHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.request.CreateHseCapaRequest;
import dz.sh.hidra.modules.hse.api.rest.request.OpenHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaResponse;
import dz.sh.hidra.modules.hse.api.rest.response.HseCaseResponse;
import dz.sh.hidra.modules.hse.application.port.in.CloseHseCaseUseCase;
import dz.sh.hidra.modules.hse.application.port.in.CreateHseCapaUseCase;
import dz.sh.hidra.modules.hse.application.port.in.OpenHseCaseUseCase;
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
 * Spring MVC adapter exposing hse REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/hse")
public class SpringHseController implements HseController {

    private final CloseHseCaseUseCase closeHseCaseUseCase;
    private final CreateHseCapaUseCase createHseCapaUseCase;
    private final OpenHseCaseUseCase openHseCaseUseCase;

    public SpringHseController(
            CloseHseCaseUseCase closeHseCaseUseCase,
            CreateHseCapaUseCase createHseCapaUseCase,
            OpenHseCaseUseCase openHseCaseUseCase
    ) {
        this.closeHseCaseUseCase = Objects.requireNonNull(closeHseCaseUseCase, "CloseHseCaseUseCase must not be null.");
        this.createHseCapaUseCase = Objects.requireNonNull(createHseCapaUseCase, "CreateHseCapaUseCase must not be null.");
        this.openHseCaseUseCase = Objects.requireNonNull(openHseCaseUseCase, "OpenHseCaseUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "hse",
                "mission", "Control health, safety, and environmental cases and corrective actions.",
                "objectives", List.of(
                "Open HSE cases.",
                "Create HSE corrective and preventive actions.",
                "Close HSE cases with traceable accountability."
        ),
                "operations", List.of(
                "closeHseCase",
                "createHseCapa",
                "openHseCase"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/hse/cases/closures",
                "POST /api/v1/hse/capas",
                "POST /api/v1/hse/cases"
        )
        );
    }

    @Override
    @PostMapping({"/close-hse-case", "/cases/closures"})
    public String closeHseCase(@Valid @RequestBody CloseHseCaseRequest request) {
        Objects.requireNonNull(request, "CloseHseCaseRequest must not be null.");
        return closeHseCaseUseCase.closeHseCase(HseRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/create-hse-capa", "/capas"})
    public HseCapaResponse createHseCapa(@Valid @RequestBody CreateHseCapaRequest request) {
        Objects.requireNonNull(request, "CreateHseCapaRequest must not be null.");
        return HseRestMapper.toResponse(createHseCapaUseCase.createHseCapa(HseRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/open-hse-case", "/cases"})
    public HseCaseResponse openHseCase(@Valid @RequestBody OpenHseCaseRequest request) {
        Objects.requireNonNull(request, "OpenHseCaseRequest must not be null.");
        return HseRestMapper.toResponse(openHseCaseUseCase.openHseCase(HseRestMapper.toCommand(request)));
    }

}
