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
import dz.sh.hidra.modules.hse.api.rest.request.CreateHseCapaRequest;
import dz.sh.hidra.modules.hse.api.rest.request.OpenHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaResponse;
import dz.sh.hidra.modules.hse.api.rest.response.HseCaseResponse;
import dz.sh.hidra.modules.hse.application.port.in.CreateHseCapaUseCase;
import dz.sh.hidra.modules.hse.application.port.in.OpenHseCaseUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing hse REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/hse")
public final class SpringHseController implements HseController {

    private final OpenHseCaseUseCase openHseCaseUseCase;
    private final CreateHseCapaUseCase createHseCapaUseCase;

    public SpringHseController(
            OpenHseCaseUseCase openHseCaseUseCase,
            CreateHseCapaUseCase createHseCapaUseCase
    ) {
        this.openHseCaseUseCase = Objects.requireNonNull(openHseCaseUseCase, "OpenHseCaseUseCase must not be null.");
        this.createHseCapaUseCase = Objects.requireNonNull(createHseCapaUseCase, "CreateHseCapaUseCase must not be null.");
    }


    @Override
    @PostMapping("/open-hse-case")
    public HseCaseResponse openHseCase(@Valid @RequestBody OpenHseCaseRequest request) {
        Objects.requireNonNull(request, "OpenHseCaseRequest must not be null.");
        return HseRestMapper.toResponse(openHseCaseUseCase.openHseCase(HseRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/create-hse-capa")
    public HseCapaResponse createHseCapa(@Valid @RequestBody CreateHseCapaRequest request) {
        Objects.requireNonNull(request, "CreateHseCapaRequest must not be null.");
        return HseRestMapper.toResponse(createHseCapaUseCase.createHseCapa(HseRestMapper.toCommand(request)));
    }

}
