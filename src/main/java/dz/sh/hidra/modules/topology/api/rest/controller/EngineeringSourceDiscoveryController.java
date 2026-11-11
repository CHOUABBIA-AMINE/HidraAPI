/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringSourceDiscoveryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes small scoped source-selection responses through the protected Topology read owner.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase.*;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringSourceDiscoveryResponse.*;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topology/engineering-sources")
public final class EngineeringSourceDiscoveryController {
    private final EngineeringSourceDiscoveryUseCase owner;
    public EngineeringSourceDiscoveryController(EngineeringSourceDiscoveryUseCase owner) {
        this.owner=Objects.requireNonNull(owner);
    }
    private static UUID exactUuid(String value) {
        UUID parsed=UUID.fromString(value);
        if(!parsed.toString().equals(value))throw new IllegalArgumentException("Canonical UUID required");
        return parsed;
    }
    /**
     * MVP: businessKey is the canonical known engineering draft UUID.
     * Broader company-wide code discovery is not asserted by this endpoint.
     */
    @NativeProtectedOperation
    @GetMapping
    @Operation(operationId="discoverExactEngineeringSource",
            summary="Resolve one known engineering draft to its currently active publication, under native owner authority")
    public Page discover(@RequestParam String companyPartyId,@RequestParam String pipelineSystemId,
                         @RequestParam Kind kind,@RequestParam String businessKey,
                         @RequestParam(defaultValue="20") int limit,@RequestParam(required=false) String cursor) {
        if(limit<1 || limit>50 || cursor!=null)
            throw new IllegalArgumentException("Known-key lookup has no continuation cursor");
        var current=owner.current(new Lookup(kind,companyPartyId,pipelineSystemId,exactUuid(businessKey),null,null));
        return new Page(current.map(s->List.of(Item.from(s))).orElseGet(List::of),null);
    }
    @NativeProtectedOperation
    @GetMapping("/{kind}/{ownerId}/{sourceId}/revisions/{revisionId}")
    @Operation(operationId="readExactEngineeringSource",
            summary="Read exact retained publication evidence without claiming current eligibility or qualification")
    public Item exact(@PathVariable Kind kind,@PathVariable String ownerId,@PathVariable String sourceId,
                      @PathVariable String revisionId,@RequestParam String companyPartyId,
                      @RequestParam String pipelineSystemId) {
        return Item.from(owner.exact(new Lookup(kind,companyPartyId,pipelineSystemId,
                exactUuid(sourceId),exactUuid(ownerId),exactUuid(revisionId))));
    }
}
