/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes only protected installed-unit operations through the owner use case.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.mapper.InstalledUnitLifecycleRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledUnitLifecycleRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.InstalledUnitLifecycleResponses.Lifecycle;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.Command;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

/** HTTP transport is not authority; current native permissions are always checked by the owner. */
@io.swagger.v3.oas.annotations.responses.ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="400",description="Invalid request",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="401",description="Authentication required",content=@io.swagger.v3.oas.annotations.media.Content),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="403",description="Current scope denied",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="404",description="Authorized installation or unit absent",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="409",description="Known version, request or occupancy conflict",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="503",description="Source unavailable or indeterminate commit; inspect before retry",content=@io.swagger.v3.oas.annotations.media.Content(mediaType="application/problem+json",schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=org.springframework.http.ProblemDetail.class)))
})
@RestController
@RequestMapping("/api/v1/topology/installed-units")
public class InstalledUnitLifecycleController {
    private final InstalledUnitLifecycleUseCase useCase;
    public InstalledUnitLifecycleController(InstalledUnitLifecycleUseCase useCase) {
        this.useCase=Objects.requireNonNull(useCase);
    }
    private Lifecycle execute(Command command) {
        var authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken)
            throw new InstalledUnitLifecycleFailure(InstalledUnitLifecycleFailure.Kind.UNAUTHENTICATED,null);
        return InstalledUnitLifecycleRestMapper.from(useCase.execute(command));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Protected unit registration or explicit replay",useReturnTypeSchema=true)
    @Operation(operationId="registerInstalledUnit",summary="Register an independently identified installed unit")
    @PutMapping("/{unitId}")
    public Lifecycle register(@PathVariable("unitId") String unitId,@RequestBody Register body) {
        return execute(InstalledUnitLifecycleRestMapper.register(unitId,body));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Current-authorized unit",useReturnTypeSchema=true)
    @Operation(operationId="readInstalledUnit",summary="Read an exact company-scoped installed unit")
    @GetMapping("/{unitId}")
    public Lifecycle readUnit(@PathVariable("unitId") String unitId,@RequestParam("companyPartyId") String companyPartyId) {
        return execute(InstalledUnitLifecycleRestMapper.readUnit(unitId,companyPartyId));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Installed-unit occupancy and replay",useReturnTypeSchema=true)
    @Operation(operationId="recordInstalledUnitInstallation",summary="Record an authorized installed-unit placement")
    @PostMapping("/{unitId}/installations")
    public Lifecycle record(@PathVariable("unitId") String unitId,@RequestBody RecordInstallation body) {
        return execute(InstalledUnitLifecycleRestMapper.record(unitId,body));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Recorded installation closure or replay",useReturnTypeSchema=true)
    @Operation(operationId="closeInstalledUnitInstallation",summary="Close an exact current installation")
    @PostMapping("/{unitId}/installations/{installationId}/close")
    public Lifecycle close(@PathVariable("unitId") String unitId,@PathVariable("installationId") String installationId,
            @RequestBody Close body) {
        return execute(InstalledUnitLifecycleRestMapper.close(unitId,installationId,body));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Atomic same-unit relocation",useReturnTypeSchema=true)
    @Operation(operationId="relocateInstalledUnitInstallation",summary="Atomically relocate one physical unit")
    @PostMapping("/{unitId}/installations/{installationId}/relocate")
    public Lifecycle relocate(@PathVariable("unitId") String unitId,@PathVariable("installationId") String installationId,
            @RequestBody Relocate body) {
        return execute(InstalledUnitLifecycleRestMapper.relocate(unitId,installationId,body));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Atomic two-unit replacement or replay",useReturnTypeSchema=true)
    @Operation(operationId="replaceInstalledUnitInstallation",summary="Atomically replace a physical unit at one exact position")
    @PostMapping("/{unitId}/installations/{installationId}/replace")
    public Lifecycle replace(@PathVariable("unitId") String unitId,@PathVariable("installationId") String installationId,
            @RequestBody Replace body) {
        return execute(InstalledUnitLifecycleRestMapper.replace(unitId,installationId,body));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Current-authorized exact installation",useReturnTypeSchema=true)
    @Operation(operationId="readInstalledUnitInstallation",summary="Read an exact protected installation")
    @GetMapping("/{unitId}/installations/{installationId}")
    public Lifecycle readInstallation(@PathVariable("unitId") String unitId,
            @PathVariable("installationId") String installationId,
            @RequestParam("companyPartyId") String companyPartyId,@RequestParam("positionId") String positionId,
            @RequestParam("facilityId") String facilityId) {
        return execute(InstalledUnitLifecycleRestMapper.readInstallation(unitId,installationId,companyPartyId,positionId,facilityId));
    }

    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Current-authorized immutable installation versions",useReturnTypeSchema=true)
    @Operation(operationId="readInstalledUnitInstallationHistory",summary="Read at most two versions of one installation")
    @GetMapping("/{unitId}/installations/{installationId}/history")
    public Lifecycle history(@PathVariable("unitId") String unitId,
            @PathVariable("installationId") String installationId,
            @RequestParam("companyPartyId") String companyPartyId,@RequestParam("positionId") String positionId,
            @RequestParam("facilityId") String facilityId) {
        return execute(InstalledUnitLifecycleRestMapper.history(unitId,installationId,companyPartyId,positionId,facilityId));
    }
}
