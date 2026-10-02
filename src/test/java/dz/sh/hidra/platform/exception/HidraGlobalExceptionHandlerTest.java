/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraGlobalExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Verifies stable HTTP status mapping for not-found and forbidden remediation paths.
 *
 */
package dz.sh.hidra.platform.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

class HidraGlobalExceptionHandlerTest {

    private final HidraGlobalExceptionHandler handler = new HidraGlobalExceptionHandler(false);

    @Test
    void mapsMissingResourceTo404() {
        var detail = handler.handleNotFoundException(
                new NoSuchElementException("Unknown workflow task: task-1"),
                request("/api/v1/workflow/tasks/task-1")
        );

        assertEquals(HttpStatus.NOT_FOUND.value(), detail.getStatus());
        assertEquals("RESOURCE_NOT_FOUND", detail.getTitle());
    }

    @Test
    void mapsAuthorizationFailureTo403() {
        var detail = handler.handleAccessDeniedException(
                new AccessDeniedException("Missing required permission"),
                request("/api/v1/telemetry/points/point-1/readings")
        );

        assertEquals(HttpStatus.FORBIDDEN.value(), detail.getStatus());
        assertEquals("ACCESS_DENIED", detail.getTitle());
    }

    private static MockHttpServletRequest request(String path) {
        return new MockHttpServletRequest("GET", path);
    }
}
