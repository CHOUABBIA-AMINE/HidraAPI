package dz.sh.hidra.modules.simulation.api.rest;

import dz.sh.hidra.modules.simulation.api.rest.controller.ManufacturerCharacteristicRevisionController;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.net.URI;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** Static non-disclosing protected REST errors; never turn a denied source into a 404 or 409. */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes=ManufacturerCharacteristicRevisionController.class)
public final class ManufacturerCharacteristicRevisionApiExceptionHandler {
    private static ResponseEntity<ProblemDetail> problem(int status,String code) {
        var p=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),
                status==503?"Manufacturer operation unavailable; confirm outcome before retry"
                           :"Manufacturer operation not completed");
        p.setInstance(URI.create("urn:hidra:manufacturer-characteristics"));
        p.setProperty("code",code);
        return ResponseEntity.status(status).body(p);
    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ProblemDetail> unauthenticated(AuthenticationCredentialsNotFoundException e) {
        return problem(401,"UNAUTHENTICATED");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> denied(AccessDeniedException e) {
        return problem(403,"DENIED");
    }
    @ExceptionHandler(ProtectedTransactionExecutor.ExecutionFailure.class)
    public ResponseEntity<ProblemDetail> transaction(ProtectedTransactionExecutor.ExecutionFailure e) {
        return problem(503,e.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE
                ?"INDETERMINATE":"UNAVAILABLE");
    }
    @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class,
            InvalidSimulationValueException.class, HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception e) {
        return problem(400,"INVALID");
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> unavailable(IllegalStateException e) {
        return problem(503,"UNAVAILABLE");
    }
}
