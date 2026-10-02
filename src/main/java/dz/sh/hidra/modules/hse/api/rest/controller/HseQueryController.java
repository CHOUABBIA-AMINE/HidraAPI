/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.controller
 *
 * @Description : Exposes HSE case and CAPA list/detail reads.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.controller;

import dz.sh.hidra.modules.hse.application.port.in.HseQueryUseCase;
import dz.sh.hidra.modules.hse.application.port.in.HseQueryUseCase.CapaView;
import dz.sh.hidra.modules.hse.application.port.in.HseQueryUseCase.HseCaseView;
import dz.sh.hidra.modules.hse.application.port.in.HseQueryUseCase.Page;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hse")
public class HseQueryController {

    private final HseQueryUseCase queryUseCase;

    public HseQueryController(HseQueryUseCase queryUseCase) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "HseQueryUseCase must not be null.");
    }

    @GetMapping("/cases")
    public Page<HseCaseView> cases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.cases(page, size);
    }

    @GetMapping("/cases/{id}")
    public HseCaseView hseCase(@PathVariable String id) {
        return queryUseCase.hseCase(id);
    }

    @GetMapping("/capas")
    public Page<CapaView> capas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.capas(page, size);
    }

    @GetMapping("/capas/{id}")
    public CapaView capa(@PathVariable String id) {
        return queryUseCase.capa(id);
    }
}
