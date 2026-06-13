/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Exposes generic read, detail, list, and search endpoints for Hidra operational workbenches.
 *
 */
package dz.sh.hidra.platform.workbench;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes generic read, detail, list, and search endpoints for Hidra operational workbenches.
 */
@RestController
@Validated
@RequestMapping("/api/v1")
public class HidraOperationalWorkbenchController {

    private final HidraOperationalWorkbenchService workbenchService;

    public HidraOperationalWorkbenchController(HidraOperationalWorkbenchService workbenchService) {
        this.workbenchService = Objects.requireNonNull(workbenchService, "HidraOperationalWorkbenchService must not be null.");
    }

    @GetMapping("/workbench/modules")
    public List<String> modules() {
        return workbenchService.listModules();
    }

    @GetMapping({"/workbench/{module}/resources", "/{module}/workbench/resources"})
    public List<OperationalResourceDescriptor> resources(@PathVariable String module) {
        return workbenchService.listResources(module);
    }

    @GetMapping({"/workbench/{module}/{resource}", "/{module}/workbench/{resource}"})
    public OperationalPageResponse list(
            @PathVariable String module,
            @PathVariable String resource,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "q", required = false) String query
    ) {
        return workbenchService.list(module, resource, page, size, query);
    }

    @GetMapping({"/workbench/{module}/{resource}/{id}", "/{module}/workbench/{resource}/{id}"})
    public OperationalRecordResponse detail(
            @PathVariable String module,
            @PathVariable String resource,
            @PathVariable String id
    ) {
        return workbenchService.detail(module, resource, id);
    }

    @PostMapping({"/workbench/{module}/{resource}/search", "/{module}/workbench/{resource}/search"})
    public OperationalPageResponse search(
            @PathVariable String module,
            @PathVariable String resource,
            @Valid @RequestBody(required = false) OperationalSearchRequest request
    ) {
        return workbenchService.search(module, resource, request);
    }
}
