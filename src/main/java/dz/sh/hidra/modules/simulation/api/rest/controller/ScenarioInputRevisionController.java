/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputRevisionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.controller
 * @Description : Four exact native protected endpoints for baseline-pinned immutable scenario inputs.
 */
package dz.sh.hidra.modules.simulation.api.rest.controller;


import dz.sh.hidra.modules.simulation.api.rest.request.ScenarioInputRevisionRequests;
import dz.sh.hidra.modules.simulation.api.rest.response.ScenarioInputRevisionResponses;
import dz.sh.hidra.modules.simulation.application.port.in.ScenarioInputRevisionUseCase;
import dz.sh.hidra.modules.simulation.application.service.ScenarioInputRevisionApplicationService;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.*;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simulation/companies/{companyId}/pipeline-systems/{pipelineSystemId}/scenarios")
public final class ScenarioInputRevisionController {
    private final ScenarioInputRevisionUseCase useCase;
    public ScenarioInputRevisionController(ScenarioInputRevisionUseCase useCase) {
        this.useCase=Objects.requireNonNull(useCase);
    }
    private static void authenticated() {
        var auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth==null||!auth.isAuthenticated()||auth instanceof AnonymousAuthenticationToken)
            throw new AuthenticationCredentialsNotFoundException("Scenario authentication required");
    }
    private static Scope scope(String company,String pipeline){return new Scope(company,pipeline);}
    private static Inputs inputs(UUID receipt,String digest,String name,String mode,
            List<ScenarioInputRevisionRequests.Boundary> boundaries,Integer duration,Integer step,String key) {
        if(boundaries==null)throw new IllegalArgumentException("Boundaries required");
        var resolved=boundaries.stream().map(b->new ScenarioInputRevision.Boundary(
                Slot.valueOf(b.slot()),b.value(),Unit.valueOf(b.unit()))).toList();
        return new Inputs(name,Mode.valueOf(mode),receipt,digest,resolved,duration,step,key);
    }
    @NativeProtectedOperation
    @PostMapping
    @Operation(operationId="createT31Scenario",summary="Create immutable baseline-pinned scenario input revision one")
    public ResponseEntity<ScenarioInputRevisionResponses.Revision> create(
            @PathVariable String companyId,@PathVariable String pipelineSystemId,
            @Valid @RequestBody ScenarioInputRevisionRequests.Create request) {
        authenticated();
        var result=useCase.create(new ScenarioInputRevisionUseCase.Create(scope(companyId,pipelineSystemId),
                inputs(request.baselineReceiptId(),request.expectedManifestDigest(),request.name(),
                request.mode(),request.boundaries(),request.durationSeconds(),request.stepSeconds(),request.requestKey())));
        return ResponseEntity.status(result.replay()?HttpStatus.OK:HttpStatus.CREATED)
                .body(ScenarioInputRevisionResponses.revision(result));
    }
    @NativeProtectedOperation
    @PostMapping("/{scenarioId}/revisions")
    @Operation(operationId="appendT31ScenarioRevision",summary="Append the next exact immutable scenario revision")
    public ResponseEntity<ScenarioInputRevisionResponses.Revision> append(
            @PathVariable String companyId,@PathVariable String pipelineSystemId,@PathVariable UUID scenarioId,
            @Valid @RequestBody ScenarioInputRevisionRequests.Append request) {
        authenticated();
        var result=useCase.append(new ScenarioInputRevisionUseCase.Append(scope(companyId,pipelineSystemId),
                scenarioId,request.expectedRevision(),
                inputs(request.baselineReceiptId(),request.expectedManifestDigest(),request.name(),
                request.mode(),request.boundaries(),request.durationSeconds(),request.stepSeconds(),request.requestKey())));
        return ResponseEntity.status(result.replay()?HttpStatus.OK:HttpStatus.CREATED)
                .body(ScenarioInputRevisionResponses.revision(result));
    }
    @NativeProtectedOperation
    @GetMapping("/{scenarioId}/revisions/{revision}")
    @Operation(operationId="readT31ScenarioRevision",summary="Read one exact authorized scenario input revision")
    public ScenarioInputRevisionResponses.Revision read(
            @PathVariable String companyId,@PathVariable String pipelineSystemId,
            @PathVariable UUID scenarioId,@PathVariable long revision) {
        authenticated();
        return ScenarioInputRevisionResponses.revision(useCase.read(
                new ScenarioInputRevisionUseCase.Read(scope(companyId,pipelineSystemId),scenarioId,revision)));
    }
    @NativeProtectedOperation
    @GetMapping("/{scenarioId}/revisions/{revision}/validation")
    @Operation(operationId="validateT31ScenarioRevision",summary="Recompute structural validation without executing a solver")
    public ScenarioInputRevisionResponses.Validation validate(
            @PathVariable String companyId,@PathVariable String pipelineSystemId,
            @PathVariable UUID scenarioId,@PathVariable long revision) {
        authenticated();
        return ScenarioInputRevisionResponses.validation(useCase.validate(
                new ScenarioInputRevisionUseCase.Read(scope(companyId,pipelineSystemId),scenarioId,revision)));
    }
    private static ResponseEntity<ProblemDetail> error(HttpStatus status,String code) {
        var body=ProblemDetail.forStatusAndDetail(status,"Scenario input request not completed");
        body.setInstance(URI.create("urn:hidra:simulation:scenario-inputs"));
        body.setProperty("code",code);
        return ResponseEntity.status(status).body(body);
    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ProblemDetail> unauthenticated(AuthenticationCredentialsNotFoundException e) {
        return error(HttpStatus.UNAUTHORIZED,"UNAUTHENTICATED");
    }
    @ExceptionHandler({NoSuchElementException.class,AccessDeniedException.class})
    public ResponseEntity<ProblemDetail> missing(RuntimeException e) {
        return error(HttpStatus.NOT_FOUND,"NOT_FOUND");
    }
    @ExceptionHandler(ScenarioInputRevisionApplicationService.Conflict.class)
    public ResponseEntity<ProblemDetail> conflict(ScenarioInputRevisionApplicationService.Conflict e) {
        return error(HttpStatus.CONFLICT,"REVISION_CONFLICT");
    }
    @ExceptionHandler({IllegalArgumentException.class,org.springframework.http.converter.HttpMessageNotReadableException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> badRequest(Exception e) {
        return error(HttpStatus.BAD_REQUEST,"INVALID_INPUT");
    }
    @ExceptionHandler({IllegalStateException.class,ProtectedTransactionExecutor.ExecutionFailure.class})
    public ResponseEntity<ProblemDetail> unavailable(RuntimeException e) {
        return error(HttpStatus.SERVICE_UNAVAILABLE,"UNAVAILABLE");
    }
}
