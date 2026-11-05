package dz.sh.hidra.modules.topology.api.rest.controller;
import dz.sh.hidra.modules.topology.api.rest.request.TopologyFoundationRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyFoundationResponses.Page;
import dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase.*;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
/** Ordinary route checks are additive; the protected setup owner independently reauthorizes. */
@RestController
@RequestMapping("/api/v1/topology/foundation")
public class TopologyFoundationController {
    private final TopologyFoundationUseCase owner;
    public TopologyFoundationController(TopologyFoundationUseCase owner){this.owner=Objects.requireNonNull(owner);}
    private int offset(String cursor){return dz.sh.hidra.modules.topology.api.rest.mapper.TopologyDiscoveryCursor.decode(cursor);}

    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@GetMapping("/choices/{catalog}")
    @io.swagger.v3.oas.annotations.Operation(operationId="discoverTopologyFoundationChoices")
    public Page choices(@PathVariable Catalog catalog,@RequestParam String companyPartyId,@RequestParam(required=false) String cursor,@RequestParam(defaultValue="50") int limit){return Page.from(owner.executeFoundation(new Command(Kind.DISCOVER,companyPartyId,null,null,null,null,null,null,null,null,null,catalog,offset(cursor),limit)));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@PostMapping("/pipeline-systems")
    @io.swagger.v3.oas.annotations.Operation(operationId="registerFoundationPipelineSystem")
    public Page system(@RequestBody CreateSystem r){return Page.from(owner.executeFoundation(new Command(Kind.CREATE_SYSTEM,r.companyPartyId(),null,r.requestId(),r.id(),r.code(),r.name(),r.systemTypeId(),null,null,r.reason(),null,0,1)));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@PostMapping("/facilities")
    @io.swagger.v3.oas.annotations.Operation(operationId="registerFoundationFacility")
    public Page facility(@RequestBody RegisterFacility r){return Page.from(owner.executeFoundation(new Command(Kind.REGISTER_FACILITY,r.companyPartyId(),r.id(),r.requestId(),r.id(),r.code(),r.name(),r.facilityTypeId(),r.facilityKind(),null,r.reason(),null,0,1)));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@PostMapping("/facilities/{facilityId}/activation")
    @io.swagger.v3.oas.annotations.Operation(operationId="activateFoundationFacility")
    public Page activate(@PathVariable String facilityId,@RequestBody ActivateFacility r){return Page.from(owner.executeFoundation(new Command(Kind.ACTIVATE_FACILITY,r.companyPartyId(),facilityId,r.requestId(),facilityId,null,null,null,null,r.expectedUpdatedAt(),r.reason(),null,0,1)));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@PostMapping("/facilities/{facilityId}/equipment-positions")
    @io.swagger.v3.oas.annotations.Operation(operationId="registerTopologyEquipmentPosition")
    public Page position(@PathVariable String facilityId,@RequestBody RegisterPosition r){return Page.from(owner.executeFoundation(new Command(Kind.REGISTER_POSITION,r.companyPartyId(),facilityId,r.requestId(),r.id(),r.code(),r.name(),r.equipmentKindId(),null,null,r.reason(),null,0,1)));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@GetMapping("/facilities/{facilityId}/equipment-positions")
    @io.swagger.v3.oas.annotations.Operation(operationId="discoverTopologyEquipmentPositions")
    public Page positions(@PathVariable String facilityId,@RequestParam String companyPartyId,@RequestParam(required=false) String cursor,@RequestParam(defaultValue="50") int limit){return Page.from(owner.executeFoundation(new Command(Kind.POSITIONS,companyPartyId,facilityId,null,null,null,null,null,null,null,null,null,offset(cursor),limit)));}
    @dz.sh.hidra.platform.permissions.NativeProtectedOperation
@GetMapping("/facilities/{facilityId}/installations")
    @io.swagger.v3.oas.annotations.Operation(operationId="discoverTopologyCurrentInstallations")
    public Page installations(@PathVariable String facilityId,@RequestParam String companyPartyId,@RequestParam(required=false) String cursor,@RequestParam(defaultValue="50") int limit){return Page.from(owner.executeFoundation(new Command(Kind.INSTALLATIONS,companyPartyId,facilityId,null,null,null,null,null,null,null,null,null,offset(cursor),limit)));}
}
