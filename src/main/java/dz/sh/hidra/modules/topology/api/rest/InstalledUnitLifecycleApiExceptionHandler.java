/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Maps typed protected owner errors to non-disclosing HTTP ProblemDetail.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.InstalledUnitLifecycleController;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=InstalledUnitLifecycleController.class)
public class InstalledUnitLifecycleApiExceptionHandler {
    private static ProblemDetail problem(int status,String code) {
        var problem=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),
                "Installed-unit operation " + (status==503?"unavailable":"not completed"));
        problem.setInstance(java.net.URI.create("urn:hidra:installed-unit"));
        problem.setProperty("code",code);
        return problem;
    }
    @ExceptionHandler(InstalledUnitLifecycleFailure.class)
    public ResponseEntity<ProblemDetail> failure(InstalledUnitLifecycleFailure error) {
        int status=switch(error.kind()) {
            case INVALID -> 400;
            case UNAUTHENTICATED -> 401;
            case DENIED -> 403;
            case NOT_FOUND -> 404;
            case CONFLICT -> 409;
            case UNAVAILABLE,INDETERMINATE -> 503;
        };
        return ResponseEntity.status(status).body(problem(status,error.kind().name()));
    }
    @ExceptionHandler({IllegalArgumentException.class,dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException.class,
            java.time.DateTimeException.class,
            HttpMessageNotReadableException.class,MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception failure) {
        return ResponseEntity.badRequest().body(problem(400,"INVALID"));
    }
}
