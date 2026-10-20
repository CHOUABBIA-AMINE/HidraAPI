/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Expose protected engineering draft metadata and graph operations.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.*;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringNetworkDraftRequests.*;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringGraphDraftRequests;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringNetworkDraftResponses;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringNetworkDraftResponses.Draft;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringGraphDraftResponses;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/topology/engineering-networks")
public class EngineeringNetworkDraftController {
    private final EngineeringNetworkDraftUseCase useCase;
    private final EngineeringGraphDraftUseCase graphUseCase;

    @Autowired
    public EngineeringNetworkDraftController(EngineeringNetworkDraftUseCase u, EngineeringGraphDraftUseCase g) {
        useCase=Objects.requireNonNull(u); graphUseCase=Objects.requireNonNull(g);
    }
    /** Retained only for existing standalone metadata tests; graph endpoints must fail closed. */
    public EngineeringNetworkDraftController(EngineeringNetworkDraftUseCase u) {
        useCase=Objects.requireNonNull(u); graphUseCase=null;
    }
    @Operation(operationId="createEngineeringNetworkDraft",summary="Create or replay a company-scoped engineering draft")
    @PostMapping public ResponseEntity<Draft> create(@RequestBody Create body){
        var r=useCase.execute(new Command(Kind.CREATE,body.companyPartyId(),body.pipelineSystemId(),null,
            EngineeringNetworkDraft.uuid(body.creationRequestId()),null,new EngineeringNetworkDraft.Metadata(body.title(),body.description(),body.evidenceReference())));
        return ResponseEntity.status(r.replayed()?200:201).body(EngineeringNetworkDraftResponses.from(r));
    }
    @Operation(operationId="readEngineeringNetworkDraft",summary="Read the current scoped engineering draft")
    @GetMapping("/{draftId}") public Draft read(@PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network){
        return EngineeringNetworkDraftResponses.from(useCase.execute(new Command(Kind.READ,company,network,EngineeringNetworkDraft.uuid(id),null,null,null)));
    }
    @Operation(operationId="updateEngineeringNetworkDraft",summary="Append engineering draft metadata at an expected version")
    @PutMapping("/{draftId}") public Draft update(@PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestBody Update body){
        return EngineeringNetworkDraftResponses.from(useCase.execute(new Command(Kind.UPDATE,company,network,EngineeringNetworkDraft.uuid(id),null,body.expectedVersion(),new EngineeringNetworkDraft.Metadata(body.title(),body.description(),body.evidenceReference()))));
    }
    @Operation(operationId="validateEngineeringNetworkDraft",summary="Validate the exact current draft version without publication")
    @PostMapping("/{draftId}/validate") public Draft validate(@PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestBody Validate body){
        return EngineeringNetworkDraftResponses.from(useCase.execute(new Command(Kind.VALIDATE,company,network,EngineeringNetworkDraft.uuid(id),null,body.expectedVersion(),null)));
    }

    /** Explicit transport-to-domain mapping; REST DTO packages never depend on domain classes. */
    private static EngineeringGraphDraft graphFrom(EngineeringGraphDraftRequests.Update body) {
        body.validate();
        return new EngineeringGraphDraft(
            body.nodes().stream().map(n -> new EngineeringGraphDraft.Node(
                n.id(), EngineeringGraphDraft.NodeKind.valueOf(n.kind()), n.facilityId())).toList(),
            body.ports().stream().map(p -> new EngineeringGraphDraft.Port(
                p.id(), p.nodeId(), EngineeringGraphDraft.PortDirection.valueOf(p.direction()))).toList(),
            body.branches().stream().map(b -> new EngineeringGraphDraft.Branch(
                b.id(), EngineeringGraphDraft.BranchKind.valueOf(b.kind()),
                b.fromPortId(), b.toPortId())).toList());
    }

    @Operation(operationId="updateEngineeringNetworkDraftGraph",summary="Append a draft graph at the next shared metadata version")
    @PutMapping("/{draftId}/graph") public EngineeringGraphDraftResponses.Graph updateGraph(
            @PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,
            @RequestParam("pipelineSystemId") String network,
            @RequestBody EngineeringGraphDraftRequests.Update body) {
        if (graphUseCase == null) throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(
                dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.UNAVAILABLE);
        var command = new EngineeringGraphDraftUseCase.Command(EngineeringGraphDraftUseCase.Kind.UPDATE,
                company, network, EngineeringNetworkDraft.uuid(id), body.expectedVersion(), null, graphFrom(body));
        return EngineeringGraphDraftResponses.Graph.from(graphUseCase.executeGraph(command));
    }

    @Operation(operationId="readEngineeringNetworkDraftGraph",summary="Read the authorized current or historical graph sketch")
    @GetMapping("/{draftId}/graph") public EngineeringGraphDraftResponses.Graph readGraph(
            @PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,
            @RequestParam("pipelineSystemId") String network,
            @RequestParam(value="version",required=false) Long version) {
        if (graphUseCase == null) throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(
                dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.UNAVAILABLE);
        return EngineeringGraphDraftResponses.Graph.from(graphUseCase.executeGraph(new EngineeringGraphDraftUseCase.Command(
                EngineeringGraphDraftUseCase.Kind.READ,company,network,EngineeringNetworkDraft.uuid(id),null,version,null)));
    }
}
