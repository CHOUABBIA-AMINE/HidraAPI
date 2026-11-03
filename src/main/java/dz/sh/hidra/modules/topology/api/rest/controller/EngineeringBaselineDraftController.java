/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Provides independently authorized draft create update read and completeness operations.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;


import dz.sh.hidra.modules.topology.api.rest.request.EngineeringBaselineDraftRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselineDraftResponses.Draft;
import dz.sh.hidra.modules.topology.api.rest.mapper.EngineeringBaselineDraftRestMapper;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topology/engineering-baselines")
@ApiResponses({
    @ApiResponse(responseCode="400",description="Invalid bounded draft request"),
    @ApiResponse(responseCode="401",description="Authentication required"),
    @ApiResponse(responseCode="403",description="Original company pipeline or source authority denied"),
    @ApiResponse(responseCode="404",description="Authorized draft revision absent"),
    @ApiResponse(responseCode="409",description="Version or exact replay conflict"),
    @ApiResponse(responseCode="503",description="Protected outcome unavailable or commit indeterminate")
})
public final class EngineeringBaselineDraftController {
    private final EngineeringBaselineDraftUseCase owner;
    public EngineeringBaselineDraftController(EngineeringBaselineDraftUseCase owner){this.owner=Objects.requireNonNull(owner);}
    @NativeProtectedOperation @PostMapping
    @Operation(operationId="createEngineeringBaselineDraft",summary="Create an unqualified versioned engineering manifest draft")
    @ApiResponse(responseCode="200",description="Created draft or exact replay",useReturnTypeSchema=true)
    public Draft create(@RequestBody Create body){
        return EngineeringBaselineDraftRestMapper.response(owner.execute(EngineeringBaselineDraftRestMapper.create(body)));
    }
    @NativeProtectedOperation @PutMapping("/{manifestId}")
    @Operation(operationId="updateEngineeringBaselineDraft",summary="Append a new expected-version manifest draft revision")
    @ApiResponse(responseCode="200",description="Updated draft or exact replay",useReturnTypeSchema=true)
    public Draft update(@PathVariable("manifestId") UUID id,@RequestBody Update body){
        return EngineeringBaselineDraftRestMapper.response(owner.execute(EngineeringBaselineDraftRestMapper.update(id,body)));
    }
    @NativeProtectedOperation @GetMapping("/{manifestId}")
    @Operation(operationId="readEngineeringBaselineDraft",summary="Read the currently authorized exact draft head")
    @ApiResponse(responseCode="200",description="Authorized manifest draft",useReturnTypeSchema=true)
    public Draft current(@PathVariable("manifestId") UUID id,@RequestParam("companyPartyId") String company,
                         @RequestParam("pipelineSystemId") String pipeline){
        return EngineeringBaselineDraftRestMapper.response(owner.execute(EngineeringBaselineDraftRestMapper.read(id,company,pipeline,null,false)));
    }
    @NativeProtectedOperation @GetMapping("/{manifestId}/revisions/{version}")
    @Operation(operationId="readEngineeringBaselineDraftRevision",summary="Reconstruct one exact authorized manifest draft revision")
    @ApiResponse(responseCode="200",description="Authorized immutable draft revision",useReturnTypeSchema=true)
    public Draft revision(@PathVariable("manifestId") UUID id,@PathVariable("version") long version,
                          @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String pipeline){
        return EngineeringBaselineDraftRestMapper.response(owner.execute(EngineeringBaselineDraftRestMapper.read(id,company,pipeline,version,false)));
    }
    @NativeProtectedOperation @PostMapping("/{manifestId}/validation")
    @Operation(operationId="validateEngineeringBaselineDraft",summary="Re-evaluate completeness through original protected source owners")
    @ApiResponse(responseCode="200",description="Exact draft with current completeness findings",useReturnTypeSchema=true)
    public Draft validate(@PathVariable("manifestId") UUID id,@RequestParam("companyPartyId") String company,
                          @RequestParam("pipelineSystemId") String pipeline,@RequestParam(value="version",required=false) Long version){
        return EngineeringBaselineDraftRestMapper.response(owner.execute(EngineeringBaselineDraftRestMapper.read(id,company,pipeline,version,true)));
    }
}
