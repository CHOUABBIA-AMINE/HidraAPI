/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApiExceptionHandler
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Non-disclosing HTTP error mapping for protected characteristic drafts.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;
import dz.sh.hidra.modules.topology.api.rest.controller.InstalledCharacteristicLinkController;
import dz.sh.hidra.modules.topology.application.model.InstalledCharacteristicLinkFailure;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.security.access.AccessDeniedException;

@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=InstalledCharacteristicLinkController.class)
public final class InstalledCharacteristicLinkApiExceptionHandler {
    private static ResponseEntity<ProblemDetail> error(int status,String code){
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),
                status==503?"Characteristic draft outcome unavailable; confirm before retry"
                :"Characteristic draft operation not completed");
        p.setInstance(java.net.URI.create("urn:hidra:installed-characteristic-link"));
        p.setProperty("code",code);
        return ResponseEntity.status(status).body(p);
    }
    @ExceptionHandler(InstalledCharacteristicLinkFailure.class)
    public ResponseEntity<ProblemDetail> business(InstalledCharacteristicLinkFailure e){
        int s=switch(e.kind()){
            case INVALID -> 400;case UNAUTHENTICATED->401;case DENIED->403;
            case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;
        };
        return error(s,e.kind().name());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> denied(AccessDeniedException e){return error(403,"DENIED");}
    @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class,
            dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException.class,
            HttpMessageNotReadableException.class,MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception e){return error(400,"INVALID");}
    @ExceptionHandler(ProtectedTransactionExecutor.ExecutionFailure.class)
    public ResponseEntity<ProblemDetail> execution(ProtectedTransactionExecutor.ExecutionFailure e){
        return error(503,e.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE
                ?"INDETERMINATE":"UNAVAILABLE");
    }
}
