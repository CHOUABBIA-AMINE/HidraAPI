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

    private final dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase surveyUseCase;
    @Autowired
    public EngineeringNetworkDraftController(EngineeringNetworkDraftUseCase u, EngineeringGraphDraftUseCase g,
            dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase surveys) {
        useCase=Objects.requireNonNull(u); graphUseCase=Objects.requireNonNull(g);surveyUseCase=Objects.requireNonNull(surveys);
    }
    public EngineeringNetworkDraftController(EngineeringNetworkDraftUseCase u,EngineeringGraphDraftUseCase g){
        useCase=Objects.requireNonNull(u);graphUseCase=Objects.requireNonNull(g);surveyUseCase=null;
    }
    /** Retained only for existing standalone metadata tests; graph endpoints must fail closed. */
    public EngineeringNetworkDraftController(EngineeringNetworkDraftUseCase u) {
        useCase=Objects.requireNonNull(u); graphUseCase=null;surveyUseCase=null;
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

    private static dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile surveyFrom(
            dz.sh.hidra.modules.topology.api.rest.request.SurveyRouteProfileRequests.Profile p){
        if(p==null || p.horizontalCrs()==null || p.horizontalCrs().axes()==null || p.points()==null || p.sections()==null)throw new IllegalArgumentException("Survey profile required");
        var c=p.horizontalCrs();var v=p.verticalReference();
        return new dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile(p.branchId(),
            new dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.Crs(c.authority(),c.code(),c.axes().stream().map(a->new dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.Axis(a.name(),a.unitIdentifier())).toList()),
            v==null?null:new dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.VerticalReference(v.datumAuthority(),v.datumCode(),v.unitIdentifier()),
            p.chainageOriginReference(),dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.Direction.valueOf(p.direction()),p.evidenceReference(),
            p.points().stream().map(a->new dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.Point(a.id(),dec(a.firstCoordinate()),dec(a.secondCoordinate()),dec(a.chainageMetres()),a.elevation()==null?null:dec(a.elevation()),dec(a.coordinatePrecision()),dec(a.chainagePrecisionMetres()),a.elevationPrecision()==null?null:dec(a.elevationPrecision()))).toList(),
            p.sections().stream().map(a->new dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.Section(a.id(),dec(a.startChainageMetres()),dec(a.endChainageMetres()),a.evidenceReference())).toList());
    }
    private static java.math.BigDecimal dec(String s){return dz.sh.hidra.modules.topology.api.rest.request.SurveyRouteProfileRequests.decimal(s);}
    private static String decimal(java.math.BigDecimal d){return d==null?null:d.toString();}
    private static dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Survey surveyResponse(
            dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Result r){
        dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Profile profile=null;
        if(r.profile()!=null){var p=r.profile();var c=p.horizontalCrs();var v=p.verticalReference();
            profile=new dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Profile(p.branchId(),
                new dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Crs(c.authority(),c.code(),c.axes().stream().map(a->new dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Axis(a.name(),a.unitIdentifier())).toList()),
                v==null?null:new dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.VerticalReference(v.datumAuthority(),v.datumCode(),v.unitIdentifier()),p.chainageOriginReference(),p.direction().name(),p.evidenceReference(),
                p.points().stream().map(a->new dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Point(a.id(),decimal(a.firstCoordinate()),decimal(a.secondCoordinate()),decimal(a.chainageMetres()),decimal(a.elevation()),decimal(a.coordinatePrecision()),decimal(a.chainagePrecisionMetres()),decimal(a.elevationPrecision()))).toList(),
                p.sections().stream().map(a->new dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Section(a.id(),decimal(a.startChainageMetres()),decimal(a.endChainageMetres()),a.evidenceReference())).toList());
        }
        return new dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Survey(r.draftId(),r.branchId(),r.version(),r.surveyVersion(),r.surveySha256(),r.boundGraphVersion(),r.boundGraphSha256(),profile,"DRAFT",false,r.diagnostics());
    }
    private void requireSurvey(){if(surveyUseCase==null)throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.UNAVAILABLE);}
    @Operation(operationId="readEngineeringNetworkDraftSurvey",summary="Read an authorized current or as-of draft branch survey")
    @GetMapping("/{draftId}/survey/{branchId}")
    public dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Survey readSurvey(
            @PathVariable("draftId") String id,@PathVariable("branchId") String branch,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,
            @RequestParam(value="version",required=false) Long version){
        requireSurvey();return surveyResponse(surveyUseCase.executeSurvey(new dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Kind.READ,company,network,EngineeringNetworkDraft.uuid(id),branch,null,version,null)));
    }
    @Operation(operationId="updateEngineeringNetworkDraftSurvey",summary="Append a survey at the next shared draft version")
    @PutMapping("/{draftId}/survey/{branchId}")
    public dz.sh.hidra.modules.topology.api.rest.response.SurveyRouteProfileResponses.Survey updateSurvey(
            @PathVariable("draftId") String id,@PathVariable("branchId") String branch,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,
            @RequestBody dz.sh.hidra.modules.topology.api.rest.request.SurveyRouteProfileRequests.Update body){
        requireSurvey();return surveyResponse(surveyUseCase.executeSurvey(new dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Command(
            dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Kind.UPDATE,company,network,EngineeringNetworkDraft.uuid(id),branch,body.expectedVersion(),null,surveyFrom(body.profile()))));
    }
}
