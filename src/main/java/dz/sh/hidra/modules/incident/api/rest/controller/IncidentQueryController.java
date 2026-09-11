/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.controller
 *
 * @Description : Exposes incident register and detail reads.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.controller;

import dz.sh.hidra.modules.incident.application.port.in.IncidentQueryUseCase;
import dz.sh.hidra.modules.incident.application.port.in.IncidentQueryUseCase.IncidentView;
import dz.sh.hidra.modules.incident.application.port.in.IncidentQueryUseCase.Page;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/incident/incidents")
public class IncidentQueryController {

    private final IncidentQueryUseCase queryUseCase;

    public IncidentQueryController(IncidentQueryUseCase queryUseCase) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "IncidentQueryUseCase must not be null.");
    }

    @GetMapping
    public Page<IncidentView> incidents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.incidents(page, size);
    }

    @GetMapping("/{id}")
    public IncidentView incident(@PathVariable String id) {
        return queryUseCase.incident(id);
    }
}
