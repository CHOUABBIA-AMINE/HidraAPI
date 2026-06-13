/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPermissionCatalogController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Exposes route-specific permission metadata for frontend guards and dynamic menus.
 *
 */
package dz.sh.hidra.platform.permissions;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes route-specific permission metadata for frontend guards and dynamic menus.
 */
@RestController
@Validated
@RequestMapping("/api/v1/security/permissions")
public class HidraPermissionCatalogController {

    private final HidraRoutePermissionCatalogService catalogService;

    public HidraPermissionCatalogController(HidraRoutePermissionCatalogService catalogService) {
        this.catalogService = Objects.requireNonNull(catalogService, "HidraRoutePermissionCatalogService must not be null.");
    }

    @GetMapping("/catalog")
    public Map<String, Object> catalog() {
        return catalogService.catalog();
    }

    @GetMapping("/routes")
    public List<RoutePermissionDescriptor> routes() {
        return catalogService.routes();
    }
}
