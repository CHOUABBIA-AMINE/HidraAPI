/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAuthoringController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.modules.topology.api.rest.request.PipelineAuthoringRequests;
import dz.sh.hidra.modules.topology.api.rest.response.AuthoredTopologyResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.*;
@RestController
@RequestMapping("/api/v1/topology/engineering-networks/{draftId}")
public class PipelineAuthoringController {
    private final PipelineAuthoringUseCase authoring;private final AuthoredTopologyViewUseCase views;
    public PipelineAuthoringController(PipelineAuthoringUseCase authoring,AuthoredTopologyViewUseCase views){this.authoring=Objects.requireNonNull(authoring);this.views=Objects.requireNonNull(views);}
    private PipelineAuthoringUseCase.Scope scope(String company,String system,String draft){return new PipelineAuthoringUseCase.Scope(company,system,EngineeringNetworkDraft.uuid(draft));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
    @PutMapping("/authored-topology") @Operation(operationId="writeFoundationPipelineAndGraph")
    public AuthoredTopologyResponses.View write(@PathVariable String draftId,@RequestParam String companyPartyId,@RequestParam String pipelineSystemId,@RequestBody PipelineAuthoringRequests.Write request){
        Objects.requireNonNull(request);Objects.requireNonNull(request.graph());request.graph().validate();
        var input=request.graph();var graph=new EngineeringGraphDraft(input.nodes().stream().map(n->new EngineeringGraphDraft.Node(n.id(),EngineeringGraphDraft.NodeKind.valueOf(n.kind()),n.facilityId())).toList(),
            input.ports().stream().map(p->new EngineeringGraphDraft.Port(p.id(),p.nodeId(),EngineeringGraphDraft.PortDirection.valueOf(p.direction()))).toList(),
            input.branches().stream().map(b->new EngineeringGraphDraft.Branch(b.id(),EngineeringGraphDraft.BranchKind.valueOf(b.kind()),b.fromPortId(),b.toPortId())).toList());
        var p=request.pipeline();PipelineAuthoringUseCase.PipelineEdit pipeline=p==null?null:new PipelineAuthoringUseCase.PipelineEdit(p.id(),Objects.requireNonNull(p.expectedRevision()),p.code(),p.pipelineTypeId(),p.nameAr(),p.nameFr(),p.nameEn());
        var references=Objects.requireNonNull(request.references()).stream().map(r->new EngineeringGraphDraft.PipelineReference(r.branchId(),r.pipelineId(),r.segmentId())).toList();
        return AuthoredTopologyResponses.View.from(authoring.write(new PipelineAuthoringUseCase.Write(scope(companyPartyId,pipelineSystemId,draftId),input.expectedVersion(),request.requestId(),pipeline,graph,references)));
    }
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
    @GetMapping("/authored-topology") @Operation(operationId="readFoundationAuthoredTopology")
    public AuthoredTopologyResponses.View view(@PathVariable String draftId,@RequestParam String companyPartyId,@RequestParam String pipelineSystemId,@RequestParam(required=false) Long version){return AuthoredTopologyResponses.View.from(views.view(scope(companyPartyId,pipelineSystemId,draftId),version));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
    @GetMapping("/authoring-choices") @Operation(operationId="discoverFoundationTopologyChoices")
    public AuthoredTopologyResponses.Discovery choices(@PathVariable String draftId,@RequestParam String companyPartyId,@RequestParam String pipelineSystemId,@RequestParam(defaultValue="0") int offset,@RequestParam(defaultValue="50") int limit){return AuthoredTopologyResponses.Discovery.from(views.discover(scope(companyPartyId,pipelineSystemId,draftId),offset,limit));}
    @ExceptionHandler(EngineeringNetworkDraftFailure.class)
    public ResponseEntity<ProblemDetail> failure(EngineeringNetworkDraftFailure e){int status=switch(e.reason()){case DENIED->403;case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};var problem=ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status),"Topology authoring "+e.reason().name().toLowerCase(Locale.ROOT));problem.setProperty("code",e.reason().name());return ResponseEntity.status(status).body(problem);}
    @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class,org.springframework.http.converter.HttpMessageNotReadableException.class})
    public ResponseEntity<ProblemDetail> invalid(Exception e){return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid topology authoring request"));}
}
