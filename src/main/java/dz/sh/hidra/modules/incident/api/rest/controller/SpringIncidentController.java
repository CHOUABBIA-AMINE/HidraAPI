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
import dz.sh.hidra.modules.incident.api.rest.request.CloseIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.OpenIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.RecordIncidentResponseActionRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentResponse;
import dz.sh.hidra.modules.incident.application.port.in.CloseIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.OpenIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.RecordIncidentResponseActionUseCase;
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
 * Spring MVC adapter exposing incident REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/incident")
public class SpringIncidentController implements IncidentController {

    private final CloseIncidentUseCase closeIncidentUseCase;
    private final OpenIncidentUseCase openIncidentUseCase;
    private final RecordIncidentResponseActionUseCase recordIncidentResponseActionUseCase;

    public SpringIncidentController(
            CloseIncidentUseCase closeIncidentUseCase,
            OpenIncidentUseCase openIncidentUseCase,
            RecordIncidentResponseActionUseCase recordIncidentResponseActionUseCase
    ) {
        this.closeIncidentUseCase = Objects.requireNonNull(closeIncidentUseCase, "CloseIncidentUseCase must not be null.");
        this.openIncidentUseCase = Objects.requireNonNull(openIncidentUseCase, "OpenIncidentUseCase must not be null.");
        this.recordIncidentResponseActionUseCase = Objects.requireNonNull(recordIncidentResponseActionUseCase, "RecordIncidentResponseActionUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "incident",
                "mission", "Manage operational incidents and response actions for hydrocarbon transportation events.",
                "objectives", List.of(
                "Open incidents.",
                "Record incident response actions.",
                "Close incidents with evidence and accountability."
        ),
                "operations", List.of(
                "closeIncident",
                "openIncident",
                "recordResponseAction"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/incident/incidents/closures",
                "POST /api/v1/incident/incidents",
                "POST /api/v1/incident/response-actions"
        )
        );
    }

    @Override
    @PostMapping({"/close-incident", "/incidents/closures"})
    public String closeIncident(@Valid @RequestBody CloseIncidentRequest request) {
        Objects.requireNonNull(request, "CloseIncidentRequest must not be null.");
        return closeIncidentUseCase.closeIncident(IncidentRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/open-incident", "/incidents"})
    public IncidentResponse openIncident(@Valid @RequestBody OpenIncidentRequest request) {
        Objects.requireNonNull(request, "OpenIncidentRequest must not be null.");
        return IncidentRestMapper.toResponse(openIncidentUseCase.openIncident(IncidentRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/record-response-action", "/response-actions"})
    public String recordResponseAction(@Valid @RequestBody RecordIncidentResponseActionRequest request) {
        Objects.requireNonNull(request, "RecordIncidentResponseActionRequest must not be null.");
        return recordIncidentResponseActionUseCase.recordResponseAction(IncidentRestMapper.toCommand(request));
    }

}
