/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RestAuthenticationEntryPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.authentication
 *
 * @Description : Writes kernel API error responses for unauthenticated REST requests.
 *
 */
package dz.sh.hidra.platform.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.kernel.api.error.ApiErrorResponse;
import dz.sh.hidra.platform.exception.ApiErrorFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ApiErrorFactory apiErrorFactory;
    private final ObjectMapper objectMapper;

    public RestAuthenticationEntryPoint(ApiErrorFactory apiErrorFactory, ObjectMapper objectMapper) {
        this.apiErrorFactory = apiErrorFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        ApiErrorResponse errorResponse = apiErrorFactory.authenticationError(authException, requestPath(request));
        response.setStatus(errorResponse.status());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }

    private static String requestPath(HttpServletRequest request) {
        return request == null ? null : request.getRequestURI();
    }
}
