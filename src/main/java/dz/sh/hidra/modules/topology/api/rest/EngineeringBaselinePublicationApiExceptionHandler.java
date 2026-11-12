/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Maps baseline failures without protected existence or database cause disclosure.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;


import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringBaselinePublicationController;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=EngineeringBaselinePublicationController.class)
public final class EngineeringBaselinePublicationApiExceptionHandler {
    private static ResponseEntity<ProblemDetail> error(int status,String code){
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),status==503
            ?"Baseline publication outcome unavailable; confirm the request receipt before retry"
            :"Baseline publication operation not completed");
        p.setInstance(java.net.URI.create("urn:hidra:engineering-baseline-publication"));p.setProperty("code",code);
        return ResponseEntity.status(status).body(p);
    }
    @ExceptionHandler(EngineeringBaselineDraftFailure.class)
    public ResponseEntity<ProblemDetail> business(EngineeringBaselineDraftFailure failure){
        return error(switch(failure.kind()){case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE->503;},failure.kind().name());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> denied(AccessDeniedException denied){return error(403,"DENIED");}
    @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class,HttpMessageNotReadableException.class,
        MissingServletRequestParameterException.class,MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception invalid){return error(400,"INVALID");}
    @ExceptionHandler(ProtectedTransactionExecutor.ExecutionFailure.class)
    public ResponseEntity<ProblemDetail> execution(ProtectedTransactionExecutor.ExecutionFailure failure){
        return error(503,failure.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE?"INDETERMINATE":"UNAVAILABLE");
    }
}
