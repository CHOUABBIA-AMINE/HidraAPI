/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Returns fixed publication errors while keeping storage and native provenance private.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringRevisionPublicationController;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=EngineeringRevisionPublicationController.class)
public class EngineeringRevisionPublicationApiExceptionHandler {
    @ExceptionHandler(EngineeringRevisionPublicationFailure.class)
    public ResponseEntity<ProblemDetail> failure(EngineeringRevisionPublicationFailure error) {
        int status=switch(error.reason()){
            case INVALID->400;case UNAUTHENTICATED->401;case DENIED->403;case NOT_FOUND->404;
            case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};
        var problem=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),
                error.reason()==EngineeringRevisionPublicationFailure.Reason.INDETERMINATE
                    ?"Publication completion is indeterminate":"Publication operation unavailable");
        problem.setInstance(java.net.URI.create("urn:hidra:engineering-publication"));
        problem.setProperty("code",error.reason().name());
        return ResponseEntity.status(status).body(problem);
    }
    @ExceptionHandler({IllegalArgumentException.class,java.time.DateTimeException.class,HttpMessageNotReadableException.class,
        MissingServletRequestParameterException.class,MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception error) {
        var problem=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid publication request");
        problem.setInstance(java.net.URI.create("urn:hidra:engineering-publication"));
        return ResponseEntity.badRequest().body(problem);
    }
}
