/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionRoutePermissionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Verifies canonical route-derived permissions for suppression REST endpoints.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dz.sh.hidra.platform.permissions.HidraRoutePermissionNaming;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.web.method.HandlerMethod;

class AlarmSuppressionRoutePermissionTest {

    private final HidraRoutePermissionNaming naming = new HidraRoutePermissionNaming();

    @Test
    void derivesCanonicalReadAndExecutePermissions() throws Exception {
        assertEquals(
                "alarm:suppressions:read",
                naming.permissionFor(
                        "/api/v1/alarm/suppressions/{suppressionId}",
                        "GET",
                        handlerMethod("suppression", String.class)
                )
        );
        assertEquals(
                "alarm:suppressions:execute",
                naming.permissionFor(
                        "/api/v1/alarm/suppressions",
                        "POST",
                        handlerMethod(
                                "create",
                                AlarmSuppressionController.CreateSuppressionRequest.class,
                                String.class
                        )
                )
        );
        assertEquals(
                "alarm:suppressions:execute",
                naming.permissionFor(
                        "/api/v1/alarm/suppressions/{suppressionId}/release",
                        "POST",
                        handlerMethod("release", String.class, String.class)
                )
        );
    }

    private static HandlerMethod handlerMethod(String name, Class<?>... parameterTypes)
            throws Exception {
        Method method = AlarmSuppressionController.class.getDeclaredMethod(name, parameterTypes);
        return new HandlerMethod(
                new Fixture(),
                Fixture.class.getDeclaredMethod("handle")
        ) {
            @Override
            public Method getMethod() {
                return method;
            }
        };
    }

    static final class Fixture {
        void handle() {
        }
    }
}
