/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringTopologyController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing topology REST endpoints.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSystemRequest;
import dz.sh.hidra.modules.topology.api.rest.request.RegisterFacilityRequest;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSystemResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.RegisterFacilityUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing topology REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/topology")
public class SpringTopologyController implements TopologyController {

    private final CreatePipelineSystemUseCase createPipelineSystemUseCase;
    private final RegisterFacilityUseCase registerFacilityUseCase;

    public SpringTopologyController(
            CreatePipelineSystemUseCase createPipelineSystemUseCase,
            RegisterFacilityUseCase registerFacilityUseCase
    ) {
        this.createPipelineSystemUseCase = Objects.requireNonNull(createPipelineSystemUseCase, "CreatePipelineSystemUseCase must not be null.");
        this.registerFacilityUseCase = Objects.requireNonNull(registerFacilityUseCase, "RegisterFacilityUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-pipeline-system")
    public PipelineSystemResponse createPipelineSystem(@Valid @RequestBody CreatePipelineSystemRequest request) {
        Objects.requireNonNull(request, "CreatePipelineSystemRequest must not be null.");
        return TopologyRestMapper.toResponse(createPipelineSystemUseCase.createPipelineSystem(TopologyRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/register-facility")
    public FacilityResponse registerFacility(@Valid @RequestBody RegisterFacilityRequest request) {
        Objects.requireNonNull(request, "RegisterFacilityRequest must not be null.");
        return TopologyRestMapper.toResponse(registerFacilityUseCase.registerFacility(TopologyRestMapper.toCommand(request)));
    }

}
