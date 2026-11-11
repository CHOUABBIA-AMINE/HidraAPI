/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringSourceDiscoveryApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Maps discovery errors without exposing protected source existence or exceptions.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringSourceDiscoveryController;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=EngineeringSourceDiscoveryController.class)
public final class EngineeringSourceDiscoveryApiExceptionHandler {
    private static ResponseEntity<ProblemDetail> problem(HttpStatus status,String code) {
        var p=ProblemDetail.forStatusAndDetail(status,"Engineering source lookup not completed");
        p.setProperty("code",code);
        return ResponseEntity.status(status).body(p);
    }
    @ExceptionHandler(EngineeringRevisionPublicationFailure.class)
    public ResponseEntity<ProblemDetail> protectedRead(EngineeringRevisionPublicationFailure error) {
        return switch(error.reason()) {
            case UNAUTHENTICATED -> problem(HttpStatus.UNAUTHORIZED,"UNAUTHENTICATED");
            case DENIED,NOT_FOUND -> problem(HttpStatus.FORBIDDEN,"DENIED");
            case INVALID -> problem(HttpStatus.BAD_REQUEST,"INVALID");
            case CONFLICT -> problem(HttpStatus.CONFLICT,"CONFLICT");
            case UNAVAILABLE,INDETERMINATE -> problem(HttpStatus.SERVICE_UNAVAILABLE,"UNAVAILABLE");
        };
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> denied(AccessDeniedException error) {
        return problem(HttpStatus.FORBIDDEN,"DENIED");
    }
    @ExceptionHandler({IllegalArgumentException.class,MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception error) {
        return problem(HttpStatus.BAD_REQUEST,"INVALID");
    }
}
