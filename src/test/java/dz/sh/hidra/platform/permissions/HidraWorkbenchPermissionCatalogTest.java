/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraWorkbenchPermissionCatalogTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Verifies dynamic workbench templates are excluded from grantable route permissions.
 *
 */
package dz.sh.hidra.platform.permissions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.platform.workbench.HidraOperationalWorkbenchController;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

class HidraWorkbenchPermissionCatalogTest {
    @Test
    void retainsOrdinaryRoutePermissionsButDoesNotPublishWorkbenchTemplateGrants() throws Exception {
        var mapping = mock(RequestMappingHandlerMapping.class);
        var ordinary = new HandlerMethod(new Fixture(), Fixture.class.getDeclaredMethod("handle"));
        var controller = new HidraOperationalWorkbenchController(mock(dz.sh.hidra.platform.workbench.HidraOperationalWorkbenchService.class));
        var workbench = new HandlerMethod(controller, HidraOperationalWorkbenchController.class.getMethod("detail", String.class, String.class, String.class));
        var ordinaryInfo = RequestMappingInfo.paths("/api/v1/telemetry/points").methods(RequestMethod.GET).build();
        var workbenchInfo = RequestMappingInfo.paths("/api/v1/workbench/{module}/{resource}/{id}", "/api/v1/{module}/workbench/{resource}/{id}").methods(RequestMethod.GET).build();
        when(mapping.getHandlerMethods()).thenReturn(Map.of(ordinaryInfo, ordinary, workbenchInfo, workbench));
        var catalog = new HidraRoutePermissionCatalogService(mapping, new HidraRoutePermissionNaming());
        assertEquals(1, catalog.routes().size());
        assertEquals("telemetry:points:read", catalog.routes().getFirst().permission());
        assertTrue(catalog.catalog().containsKey("workbenchPermissions"));
    }

    static final class Fixture {
        void handle() { }
    }
}
