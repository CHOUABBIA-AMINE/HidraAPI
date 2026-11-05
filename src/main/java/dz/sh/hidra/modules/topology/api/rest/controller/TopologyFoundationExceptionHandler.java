package dz.sh.hidra.modules.topology.api.rest.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
@org.springframework.core.annotation.Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes={TopologyFoundationController.class,StationGraphController.class})
public class TopologyFoundationExceptionHandler {
    @ExceptionHandler(EngineeringNetworkDraftFailure.class)
    public ResponseEntity<ProblemDetail> failure(EngineeringNetworkDraftFailure e){int s=switch(e.reason()){case DENIED->403;case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};return ResponseEntity.status(s).body(ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(s),"Topology operation "+e.reason().name()));}
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> denied(Exception e){return ResponseEntity.status(403).body(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"Current topology authority required"));}
    @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception e){return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid topology request"));}
    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
    public ResponseEntity<ProblemDetail> conflict(Exception e){return ResponseEntity.status(409).body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Topology identity already registered"));}
    @ExceptionHandler({IllegalStateException.class,org.springframework.dao.DataAccessException.class})
    public ResponseEntity<ProblemDetail> unavailable(Exception e){return ResponseEntity.status(503).body(ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE,"Topology source unavailable; inspect before retry"));}
}
