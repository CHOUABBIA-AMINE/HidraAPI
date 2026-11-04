/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.controller
 *
 * @Description : Exposes protected bounded Party discovery, lifecycle and history.
 *
 */
package dz.sh.hidra.modules.party.api.rest.controller;

import dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase;
import dz.sh.hidra.modules.party.api.rest.request.PartyAdministrationRequests.*;
import dz.sh.hidra.modules.party.api.rest.response.PartyAdministrationResponses.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.function.Supplier;
@RestController @RequestMapping("/api/v1/party")
public class PartyAdministrationController {
    private final PartyAdministrationUseCase service;
    public PartyAdministrationController(PartyAdministrationUseCase service) { this.service=java.util.Objects.requireNonNull(service); }
    @GetMapping("/parties/{id}") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationGet") public View get(@PathVariable String id) { return call(()->View.of(service.get(id))); }
    @GetMapping("/parties") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationSearch") public Page search(@RequestParam(required=false) String q,@RequestParam(required=false) String typeId,@RequestParam(required=false) String roleId,@RequestParam(defaultValue="false") boolean eligibleOnly,@RequestParam(defaultValue="0") int offset,@RequestParam(defaultValue="50") int limit) { return call(()->Page.of(service.search(q,typeId,roleId,eligibleOnly,offset,limit))); }
    @GetMapping("/reference/party-types") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationTypes") public List<Choice> types(@RequestParam(defaultValue="en") String language) { return call(()->service.types(language).stream().map(Choice::of).toList()); }
    @GetMapping("/reference/party-roles") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationRoles") public List<Choice> roles(@RequestParam(defaultValue="en") String language) { return call(()->service.roles(language).stream().map(Choice::of).toList()); }
    @GetMapping("/parties/{id}/history") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationHistory") public List<History> history(@PathVariable String id,@RequestParam(defaultValue="0") int offset,@RequestParam(defaultValue="50") int limit) { return call(()->service.history(id,offset,limit).stream().map(History::of).toList()); }
    @PostMapping("/parties/{id}/activate") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationActivate") public View activate(@PathVariable String id,@Valid @RequestBody Transition input) { return call(()->View.of(service.transition(id,input.expectedUpdatedAt(),true,input.reason()))); }
    @PostMapping("/parties/{id}/deactivate") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationDeactivate") public View deactivate(@PathVariable String id,@Valid @RequestBody Transition input) { return call(()->View.of(service.transition(id,input.expectedUpdatedAt(),false,input.reason()))); }
    @PutMapping("/parties/{id}") @io.swagger.v3.oas.annotations.Operation(operationId="partyAdministrationEdit") public View edit(@PathVariable String id,@Valid @RequestBody Metadata input) { return call(()->View.of(service.edit(id,input.expectedUpdatedAt(),input.legalName(),input.tradeName(),input.shortName(),input.reason()))); }
    static <T> T call(Supplier<T> action) {
        try { return action.get(); }
        catch(org.springframework.security.access.AccessDeniedException e) { throw e; }
        catch(java.util.NoSuchElementException e) { throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Party unavailable."); }
        catch(IllegalArgumentException e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Party request."); }
        catch(IllegalStateException|org.springframework.dao.DataIntegrityViolationException e) { throw new ResponseStatusException(HttpStatus.CONFLICT,"Party version or reference conflict."); }
    }
}
