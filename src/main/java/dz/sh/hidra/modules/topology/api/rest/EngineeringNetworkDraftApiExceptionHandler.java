/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=EngineeringNetworkDraftController.class)
public class EngineeringNetworkDraftApiExceptionHandler {
    @ExceptionHandler(EngineeringNetworkDraftFailure.class)
    public ResponseEntity<ProblemDetail> failure(EngineeringNetworkDraftFailure e){
        int status=switch(e.reason()){case DENIED->403;case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),switch(e.reason()){
            case DENIED->"Engineering draft access denied";case NOT_FOUND->"Engineering draft target unavailable";
            case CONFLICT->"Engineering draft version or creation request conflict";case UNAVAILABLE->"Engineering draft service unavailable";
            case INDETERMINATE->"Engineering draft completion is indeterminate";});
        p.setInstance(java.net.URI.create("urn:hidra:engineering-draft"));
        p.setProperty("code",e.reason().name());return ResponseEntity.status(status).body(p);
    }
    @ExceptionHandler({IllegalArgumentException.class,HttpMessageNotReadableException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception e){
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid engineering draft request");
        p.setInstance(java.net.URI.create("urn:hidra:engineering-draft"));return ResponseEntity.badRequest().body(p);
    }
}
