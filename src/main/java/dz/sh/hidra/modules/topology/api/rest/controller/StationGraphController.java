package dz.sh.hidra.modules.topology.api.rest.controller;
import dz.sh.hidra.modules.topology.api.rest.request.StationGraphRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.StationGraphResponses.*;
import dz.sh.hidra.modules.topology.api.rest.mapper.StationInstalledConfigurationRestMapper;
import dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.*;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
@RestController
@RequestMapping("/api/v1/topology/facilities/{facilityId}/station-graph")
public class StationGraphController {
    private final ProtectedStationGraphOperationUseCase owner;
    public StationGraphController(ProtectedStationGraphOperationUseCase owner){this.owner=Objects.requireNonNull(owner);}
    private View execute(Command c){var r=owner.execute(c);var h=r.head();return new View(h==null?null:new Head(h.facilityId(),h.revisionId(),h.epoch(),h.state().name()),r.revision()==null?null:StationInstalledConfigurationRestMapper.graphResponse(r.revision()),r.revision()==null?null:"HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1",r.canonicalSha256(),r.history().stream().map(e->new Event(e.kind().name(),e.resultingEpoch(),e.resultingRevisionId(),e.sampledAt())).toList(),r.observedCommit());}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@PutMapping("/revisions/{revisionId}")
    @io.swagger.v3.oas.annotations.Operation(operationId="authorProtectedStationGraphRevision")
    public View author(@PathVariable String facilityId,@PathVariable String revisionId,@RequestBody Author body){
        var r=StationInstalledConfigurationRestMapper.graph(body.revision());
        if(!facilityId.equals(r.facilityId())||!revisionId.equals(r.revisionId())||r.sourceClassification()==dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.SourceClassification.VERIFIED)throw new IllegalArgumentException("Exact declared or synthetic graph required");
        return execute(new Command(Kind.AUTHOR,body.companyPartyId(),facilityId,revisionId,null,null,null,r,0));
    }
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@PostMapping("/publications")
    @io.swagger.v3.oas.annotations.Operation(operationId="publishProtectedStationGraphRevision")
    public View publish(@PathVariable String facilityId,@RequestBody Publish body){return execute(new Command(Kind.PUBLISH,body.companyPartyId(),facilityId,body.revisionId(),body.expectedEpoch(),body.expectedRevisionId(),null,null,0));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@GetMapping("/publications/current")
    @io.swagger.v3.oas.annotations.Operation(operationId="readCurrentProtectedStationGraphRevision")
    public View current(@PathVariable String facilityId,@RequestParam String companyPartyId){return execute(new Command(Kind.READ_CURRENT,companyPartyId,facilityId,null,null,null,null,null,0));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@GetMapping("/revisions/{revisionId}")
    @io.swagger.v3.oas.annotations.Operation(operationId="readProtectedStationGraphRevision")
    public View revision(@PathVariable String facilityId,@PathVariable String revisionId,@RequestParam String companyPartyId){return execute(new Command(Kind.READ_HISTORY,companyPartyId,facilityId,revisionId,null,null,null,null,0));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@GetMapping("/publications/history")
    @io.swagger.v3.oas.annotations.Operation(operationId="listProtectedStationGraphPublicationHistory")
    public View history(@PathVariable String facilityId,@RequestParam String companyPartyId,@RequestParam(defaultValue="50") int limit){return execute(new Command(Kind.LIST_HISTORY,companyPartyId,facilityId,null,null,null,null,null,limit));}
}
