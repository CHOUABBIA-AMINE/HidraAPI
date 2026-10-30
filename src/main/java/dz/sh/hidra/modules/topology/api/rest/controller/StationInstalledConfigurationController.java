/**
 * HidraAPI — protected, human-authored station configuration REST entry point.
 * No field actuation, permission inference or engineering qualification.
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.mapper.StationInstalledConfigurationRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.Save;
import dz.sh.hidra.modules.topology.api.rest.response.StationInstalledConfigurationResponses.Configuration;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@io.swagger.v3.oas.annotations.responses.ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="400",description="Invalid bounded request",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="401",description="Authentication required"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="403",description="Current protected company/facility/unit authority denied",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="404",description="Authenticated and authorized configuration absent",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="409",description="Proven version or request replay conflict",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="503",description="Unavailable or indeterminate commit; no blind retry",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class)))
})
@RestController
@RequestMapping("/api/v1/topology/facilities/{facilityId}/installed-configuration")
public class StationInstalledConfigurationController {
    private final StationInstalledConfigurationUseCase owner;
    public StationInstalledConfigurationController(StationInstalledConfigurationUseCase owner){
        this.owner=Objects.requireNonNull(owner);
    }
    private static void authenticated(){
        var auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken)
            throw new StationInstalledConfigurationFailure(
                StationInstalledConfigurationFailure.Kind.UNAUTHENTICATED,null);
    }
    /** All existence, unit references, history rows and related counts are read after native authority. */
    private Configuration execute(StationInstalledConfigurationUseCase.Command command,boolean requirePresent,int limit){
        authenticated();
        var result=owner.execute(command);
        if(requirePresent && result.declaration()==null)
            throw new StationInstalledConfigurationFailure(StationInstalledConfigurationFailure.Kind.NOT_FOUND,null);
        return StationInstalledConfigurationRestMapper.from(result,limit);
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Saved immutable revision or exact authorized replay",useReturnTypeSchema=true)
    @Operation(operationId="saveInstalledStationConfiguration",summary="Record the exact protected structural configuration at an expected epoch")
    @PutMapping
    public Configuration save(@PathVariable("facilityId") String facility,@RequestBody Save body){
        authenticated();
        return execute(StationInstalledConfigurationRestMapper.save(facility,body),true,0);
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Authorized current structural station configuration",useReturnTypeSchema=true)
    @Operation(operationId="readCurrentInstalledStationConfiguration",summary="Read current authorized immutable installed-station structure")
    @GetMapping
    public Configuration current(@PathVariable("facilityId") String facility,
        @RequestParam("companyPartyId") String company){
        authenticated();
        return execute(StationInstalledConfigurationRestMapper.current(facility,company),true,0);
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Authorized immutable revision",useReturnTypeSchema=true)
    @Operation(operationId="readInstalledStationConfigurationRevision",summary="Read an exact previously saved structural revision")
    @GetMapping("/revisions/{revisionId}")
    public Configuration revision(@PathVariable("facilityId") String facility,
        @PathVariable("revisionId") String revisionId,
        @RequestParam("companyPartyId") String company){
        authenticated();
        return execute(StationInstalledConfigurationRestMapper.revision(facility,company,revisionId),true,0);
    }
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Authorized descending immutable history with exclusive epoch cursor",useReturnTypeSchema=true)
    @Operation(operationId="listInstalledStationConfigurationHistory",summary="Page immutable station configuration history in authorized epoch order")
    @GetMapping("/history")
    public Configuration history(@PathVariable("facilityId") String facility,
        @RequestParam("companyPartyId") String company,
        @RequestParam(value="limit",defaultValue="100") int limit,
        @RequestParam(value="cursor",required=false) String cursor){
        authenticated();
        return execute(StationInstalledConfigurationRestMapper.history(facility,company,limit,cursor),false,limit);
    }
}
