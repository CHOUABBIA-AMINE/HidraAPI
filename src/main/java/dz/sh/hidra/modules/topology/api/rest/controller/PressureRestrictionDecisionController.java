/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes authorized pressure restriction requests, publication, and evidence.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.request.PressureRestrictionDecisionRequests;
import dz.sh.hidra.modules.topology.api.rest.response.PressureRestrictionDecisionResponses;
import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase;
import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.Target;
import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.PressureBasis;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topology/operating-constraints")
public final class PressureRestrictionDecisionController {
    private static final String REQUESTS = "/pipelines/{pipelineId}/restriction-requests";
    private final PressureRestrictionDecisionUseCase owner;

    public PressureRestrictionDecisionController(PressureRestrictionDecisionUseCase owner) {
        this.owner = owner;
    }

    @PostMapping(REQUESTS)
    @Operation(operationId = "submitPressureRestrictionRequest")
    public ResponseEntity<PressureRestrictionDecisionResponses.Request> submit(
            @PathVariable String pipelineId, @Valid @RequestBody PressureRestrictionDecisionRequests.Submission body) {
        var scope = new Scope(body.companyPartyId(), pipelineId, body.sectionId(), body.conditionCode());
        PressureRestrictionDecisionRequest.Change change = switch (body.change()) {
            case PressureRestrictionDecisionRequests.Initial proposal ->
                    new PressureRestrictionDecisionRequest.Initial(pressure(proposal.pressure()));
            case PressureRestrictionDecisionRequests.Replacement proposal ->
                    new PressureRestrictionDecisionRequest.Replacement(proposal.predecessorId(), pressure(proposal.pressure()));
            case PressureRestrictionDecisionRequests.Withdrawal proposal ->
                    new PressureRestrictionDecisionRequest.Withdrawal(proposal.predecessorId(), proposal.effectiveAt());
        };
        var submission = new PressureRestrictionDecisionRequest(body.requestId(), scope, body.baselineReceiptId(),
                body.expectedVersion(), change);
        return result(PressureRestrictionDecisionResponses.Request.from(owner.submit(submission)));
    }

    @GetMapping(REQUESTS + "/stream")
    @Operation(operationId = "readPressureRestrictionStreamVersion",
            summary = "Read the current submission version for an exact restriction scope")
    public ResponseEntity<PressureRestrictionDecisionResponses.Stream> stream(@PathVariable String pipelineId,
            @RequestParam String companyPartyId, @RequestParam(required = false) String sectionId,
            @RequestParam String conditionCode) {
        var scope = new Scope(companyPartyId, pipelineId, sectionId, conditionCode);
        return result(new PressureRestrictionDecisionResponses.Stream(scope.companyPartyId(), scope.pipelineId(),
                scope.sectionId(), scope.conditionCode(), owner.currentStreamVersion(scope)));
    }

    // Reviewers receive candidate coordinates from the authorized Workflow instance.
    @GetMapping("/pressure-restriction-review-proposals/{candidateId}")
    @Operation(operationId = "readPressureRestrictionReviewProposal")
    public ResponseEntity<PressureRestrictionDecisionResponses.ReviewProposal> reviewProposal(
            @PathVariable UUID candidateId, @RequestParam String targetLocator,
            @RequestParam String taskId) {
        return result(PressureRestrictionDecisionResponses.ReviewProposal.from(
                owner.reviewProposal(candidateId, targetLocator, taskId)));
    }

    @GetMapping(REQUESTS + "/{requestId}/publication-inputs")
    @Operation(operationId = "readPressureRestrictionPublicationInputs")
    public ResponseEntity<PressureRestrictionDecisionResponses.PublicationInputs> publicationInputs(
            @PathVariable String pipelineId, @PathVariable UUID requestId, @RequestParam String companyPartyId,
            @RequestParam(required = false) String sectionId) {
        return result(PressureRestrictionDecisionResponses.PublicationInputs.from(
                owner.publicationInputs(new Target(companyPartyId, pipelineId, sectionId, requestId))));
    }

    @GetMapping(REQUESTS + "/{requestId}")
    @Operation(operationId = "getPressureRestrictionRequest")
    public ResponseEntity<PressureRestrictionDecisionResponses.Request> request(
            @PathVariable String pipelineId, @PathVariable UUID requestId, @RequestParam String companyPartyId,
            @RequestParam(required = false) String sectionId) {
        var target = new Target(companyPartyId, pipelineId, sectionId, requestId);
        return result(PressureRestrictionDecisionResponses.Request.from(owner.request(target)));
    }

    @PostMapping(REQUESTS + "/{requestId}/publication")
    @Operation(operationId = "publishPressureRestrictionRequest")
    public ResponseEntity<PressureRestrictionDecisionResponses.Publication> publish(
            @PathVariable String pipelineId, @PathVariable UUID requestId,
            @Valid @RequestBody PressureRestrictionDecisionRequests.Publication body) {
        var target = new Target(body.companyPartyId(), pipelineId, body.sectionId(), requestId);
        var decisions = body.decisions().stream().map(reference -> new PressureRestrictionDecisionUseCase.DecisionReference(
                reference.candidateId(), reference.instanceId(), reference.taskId(), reference.actionId())).toList();
        var publication = new PressureRestrictionDecisionUseCase.Publish(target, body.expectedVersion(), decisions);
        return result(PressureRestrictionDecisionResponses.Publication.from(owner.publish(publication)));
    }

    @GetMapping(REQUESTS + "/{requestId}/evidence")
    @Operation(operationId = "getPressureRestrictionDecisionEvidence")
    public ResponseEntity<PressureRestrictionDecisionResponses.Evidence> evidence(
            @PathVariable String pipelineId, @PathVariable UUID requestId, @RequestParam String companyPartyId,
            @RequestParam(required = false) String sectionId) {
        var target = new Target(companyPartyId, pipelineId, sectionId, requestId);
        return result(PressureRestrictionDecisionResponses.Evidence.from(owner.evidence(target)));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> unavailable() {
        return problem(HttpStatus.NOT_FOUND, "Resource unavailable");
    }

    @ExceptionHandler(PressureRestrictionDecisionUseCase.Conflict.class)
    public ResponseEntity<ProblemDetail> conflict() {
        return problem(HttpStatus.CONFLICT, "Pressure restriction request conflicts with its current state");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalid() {
        return problem(HttpStatus.BAD_REQUEST, "Invalid pressure restriction request");
    }

    private static PressureRestrictionDecisionRequest.Pressure pressure(PressureRestrictionDecisionRequests.Pressure input) {
        return new PressureRestrictionDecisionRequest.Pressure(input.upperPressure(), input.unit(),
                PressureBasis.valueOf(input.basis().name()), input.effectiveFrom(), input.effectiveUntil());
    }

    private static <T> ResponseEntity<T> result(T body) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore().cachePrivate()).body(body);
    }

    private static ResponseEntity<ProblemDetail> problem(HttpStatus status, String detail) {
        return ResponseEntity.status(status).cacheControl(CacheControl.noStore().cachePrivate())
                .body(ProblemDetail.forStatusAndDetail(status, detail));
    }
}
