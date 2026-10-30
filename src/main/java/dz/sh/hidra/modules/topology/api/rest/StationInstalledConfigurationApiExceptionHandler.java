/**
 * HidraAPI — non-disclosing protected station configuration error surface.
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.StationInstalledConfigurationController;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=StationInstalledConfigurationController.class)
public final class StationInstalledConfigurationApiExceptionHandler {
    private static ProblemDetail problem(int status,String code) {
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),
            status==503?"Station configuration operation unavailable; confirm completion before retry":
                "Station configuration operation not completed");
        p.setInstance(java.net.URI.create("urn:hidra:station-configuration"));
        p.setProperty("code",code);
        return p;
    }
    @ExceptionHandler(StationInstalledConfigurationFailure.class)
    public ResponseEntity<ProblemDetail> failure(StationInstalledConfigurationFailure error) {
        int status=switch(error.kind()){
            case INVALID -> 400;
            case UNAUTHENTICATED -> 401;
            case DENIED -> 403;
            case NOT_FOUND -> 404;
            case CONFLICT -> 409;
            case UNAVAILABLE,INDETERMINATE -> 503;
        };
        return ResponseEntity.status(status).body(problem(status,error.kind().name()));
    }
    @ExceptionHandler({IllegalArgumentException.class,
        dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException.class,
        java.time.DateTimeException.class,
        HttpMessageNotReadableException.class,MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception error) {
        return ResponseEntity.badRequest().body(problem(400,"INVALID"));
    }
}
