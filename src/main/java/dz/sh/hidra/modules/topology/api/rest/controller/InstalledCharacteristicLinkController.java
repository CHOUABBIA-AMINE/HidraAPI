/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Authenticated operator-only characteristic draft save/current/revision/history endpoints.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.mapper.InstalledCharacteristicLinkRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledCharacteristicLinkRequests.CharacteristicLinkDraftSave;
import dz.sh.hidra.modules.topology.api.rest.response.InstalledCharacteristicLinkResponses.Page;
import dz.sh.hidra.modules.topology.application.model.InstalledCharacteristicLinkFailure;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@io.swagger.v3.oas.annotations.responses.ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="400",description="Invalid bounded request"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="401",description="Authentication required"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="403",description="Original company, unit, link or source authority denied"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="404",description="Authorized immutable link absent"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="409",description="Version or exact replay conflict"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="503",description="Protected operation unavailable or commit indeterminate")
})
@RestController
@RequestMapping("/api/v1/topology/installed-units/{unitId}/characteristic-links/{linkId}")
public final class InstalledCharacteristicLinkController {
    private final InstalledCharacteristicLinkUseCase owner;
    public InstalledCharacteristicLinkController(InstalledCharacteristicLinkUseCase owner){
        this.owner=Objects.requireNonNull(owner);
    }
    private static void authenticated(){
        var a=SecurityContextHolder.getContext().getAuthentication();
        if(a==null||!a.isAuthenticated()||a instanceof AnonymousAuthenticationToken)
            throw new InstalledCharacteristicLinkFailure(InstalledCharacteristicLinkFailure.Kind.UNAUTHENTICATED);
    }
    private Page run(InstalledCharacteristicLinkUseCase.Command c,boolean present){
        authenticated();
        var v=owner.execute(c);
        if(present&&v.link()==null)
            throw new InstalledCharacteristicLinkFailure(InstalledCharacteristicLinkFailure.Kind.NOT_FOUND);
        return InstalledCharacteristicLinkRestMapper.from(v);
    }
    @Operation(operationId="saveInstalledCharacteristicDraftLink",summary="Record a protected unapproved immutable characteristic draft")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Saved immutable draft or exact replay",useReturnTypeSchema=true)
    @PutMapping
    public Page save(@PathVariable("unitId") String unit,@PathVariable("linkId") String link,@RequestBody CharacteristicLinkDraftSave body){
        authenticated();return run(InstalledCharacteristicLinkRestMapper.save(unit,link,body),true);
    }
    @Operation(operationId="readCurrentInstalledCharacteristicDraftLink",summary="Read current authorized characteristic draft")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Authorized current draft",useReturnTypeSchema=true)
    @GetMapping
    public Page current(@PathVariable("unitId") String unit,@PathVariable("linkId") String link,
                        @RequestParam("companyPartyId") String company){
        return run(InstalledCharacteristicLinkRestMapper.current(unit,link,company),true);
    }
    @Operation(operationId="readInstalledCharacteristicDraftRevision",summary="Read exact authorized immutable characteristic revision")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Authorized historical revision",useReturnTypeSchema=true)
    @GetMapping("/revisions/{revisionId}")
    public Page revision(@PathVariable("unitId") String unit,@PathVariable("linkId") String link,
                         @PathVariable("revisionId") long revision,
                         @RequestParam("companyPartyId") String company){
        return run(InstalledCharacteristicLinkRestMapper.revision(unit,link,company,revision),true);
    }
    @Operation(operationId="listInstalledCharacteristicDraftHistory",summary="Page authorized immutable characteristic revision history")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode="200",description="Bounded authorized descending history",useReturnTypeSchema=true)
    @GetMapping("/history")
    public Page history(@PathVariable("unitId") String unit,@PathVariable("linkId") String link,
                        @RequestParam("companyPartyId") String company,
                        @RequestParam(value="limit",defaultValue="100") int limit,
                        @RequestParam(value="beforeRevision",required=false) Long beforeRevision){
        return run(InstalledCharacteristicLinkRestMapper.history(unit,link,company,limit,beforeRevision),false);
    }
}
