/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringIncidentController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing incident REST endpoints.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.controller;

import dz.sh.hidra.modules.incident.api.rest.mapper.IncidentRestMapper;
import dz.sh.hidra.modules.incident.api.rest.request.OpenIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.RecordIncidentResponseActionRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentResponse;
import dz.sh.hidra.modules.incident.application.port.in.OpenIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.RecordIncidentResponseActionUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing incident REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/incident")
public final class SpringIncidentController implements IncidentController {

    private final OpenIncidentUseCase openIncidentUseCase;
    private final RecordIncidentResponseActionUseCase recordIncidentResponseActionUseCase;

    public SpringIncidentController(
            OpenIncidentUseCase openIncidentUseCase,
            RecordIncidentResponseActionUseCase recordIncidentResponseActionUseCase
    ) {
        this.openIncidentUseCase = Objects.requireNonNull(openIncidentUseCase, "OpenIncidentUseCase must not be null.");
        this.recordIncidentResponseActionUseCase = Objects.requireNonNull(recordIncidentResponseActionUseCase, "RecordIncidentResponseActionUseCase must not be null.");
    }


    @Override
    @PostMapping("/open-incident")
    public IncidentResponse openIncident(@Valid @RequestBody OpenIncidentRequest request) {
        Objects.requireNonNull(request, "OpenIncidentRequest must not be null.");
        return IncidentRestMapper.toResponse(openIncidentUseCase.openIncident(IncidentRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/record-response-action")
    public String recordResponseAction(@Valid @RequestBody RecordIncidentResponseActionRequest request) {
        Objects.requireNonNull(request, "RecordIncidentResponseActionRequest must not be null.");
        return recordIncidentResponseActionUseCase.recordResponseAction(IncidentRestMapper.toCommand(request));
    }

}
