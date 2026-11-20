/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalAccessController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.response.RegionalAccessResponse;
import dz.sh.hidra.modules.identity.application.port.in.ResolveRegionalAccessUseCase;
import dz.sh.hidra.modules.identity.application.port.in.ResolveRegionalAccessUseCase.Query;
import dz.sh.hidra.modules.identity.application.port.in.ResolveRegionalAccessUseCase.ResourceKind;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.Objects;

/** One non-disclosing employee regional read check; map and dossiers remain owner-protected. */
@RestController
@RequestMapping("/api/v1/identity/me/regional-access")
@Tag(name = "Identity Regional Access", description = "Current scoped regional access qualification.")
public final class RegionalAccessController {
    private final ResolveRegionalAccessUseCase access;
    public RegionalAccessController(ResolveRegionalAccessUseCase access) {
        this.access = Objects.requireNonNull(access, "regional access");
    }
    @GetMapping("/{resourceKind}/{resourceId}")
    @Operation(summary = "Resolve current regional employee access to a specific Topology resource")
    public RegionalAccessResponse current(@PathVariable ResourceKind resourceKind,
            @PathVariable String resourceId, @RequestParam String companyPartyId) {
        final Query query;
        try {
            query = new Query(resourceKind, resourceId, companyPartyId);
        } catch (IllegalArgumentException invalid) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            if (!access.permitted(query)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException absence) {
            throw absence;
        } catch (RuntimeException unavailable) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new RegionalAccessResponse(resourceKind.name(), resourceId, companyPartyId, true);
    }
}
