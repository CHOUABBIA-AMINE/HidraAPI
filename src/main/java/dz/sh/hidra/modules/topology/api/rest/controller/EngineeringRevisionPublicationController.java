/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes eight native protected publication operations with exact company and network scope.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure.Reason;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringRevisionPublicationRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringRevisionPublicationResponses;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringRevisionPublicationResponses.Publication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/topology/engineering-networks/{draftId}/publications")
public class EngineeringRevisionPublicationController {
    private final EngineeringRevisionPublicationUseCase useCase;
    public EngineeringRevisionPublicationController(EngineeringRevisionPublicationUseCase useCase){this.useCase=Objects.requireNonNull(useCase);}
    private static UUID uuid(String text){return EngineeringNetworkDraft.uuid(text);}
    private static UUID optional(String text){return text==null?null:uuid(text);}
    /** Canonical Instant UTC spelling with whole, millisecond or microsecond precision; offsets are rejected. */
    public static Instant asOf(String text) {
        if(text==null)return null;
        Instant value=Instant.parse(text);
        if(!value.toString().equals(text) || value.getNano()%1000!=0)throw new IllegalArgumentException("Canonical UTC microseconds required");
        return value;
    }
    private Publication execute(Command command) {
        var authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken)
            throw new EngineeringRevisionPublicationFailure(Reason.UNAUTHENTICATED);
        // Native issuer/session/subject provenance is enforced by the mandatory protected use case.
        return EngineeringRevisionPublicationResponses.from(useCase.executePublication(command));
    }
    @Operation(operationId="publishEngineeringRevision",summary="Publish an immutable engineering-unqualified authored snapshot")
    @PostMapping public Publication publish(@PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestBody Publish body) {
        return execute(new Command(Kind.PUBLISH,company,network,uuid(draftId),uuid(body.requestId()),body.draftVersion(),
                body.expectedEpoch(),optional(body.expectedRevisionId()),null,null,null,null));
    }
    @Operation(operationId="withdrawEngineeringRevision",summary="Withdraw the exact current context head")
    @PostMapping("/withdraw") public Publication withdraw(@PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestBody Transition body) {
        return execute(new Command(Kind.WITHDRAW,company,network,uuid(draftId),uuid(body.requestId()),null,
                body.expectedEpoch(),uuid(body.expectedRevisionId()),null,null,null,null));
    }
    @Operation(operationId="reactivateEngineeringRevision",summary="Reactivate the exact retained withdrawn context head")
    @PostMapping("/reactivate") public Publication reactivate(@PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestBody Transition body) {
        return execute(new Command(Kind.REACTIVATE,company,network,uuid(draftId),uuid(body.requestId()),null,
                body.expectedEpoch(),uuid(body.expectedRevisionId()),null,null,null,null));
    }
    @Operation(operationId="readExactEngineeringRevision",summary="Read an exact retained revision independently of history pagination")
    @GetMapping("/revisions/{revisionId}") public Publication readExact(@PathVariable("draftId") String draftId,
            @PathVariable("revisionId") String revisionId,@RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network) {
        return execute(new Command(Kind.READ_EXACT,company,network,uuid(draftId),null,null,null,null,uuid(revisionId),null,null,null));
    }
    @Operation(operationId="readCurrentEngineeringRevision",summary="Read context head state and its active snapshot")
    @GetMapping("/current") public Publication readCurrent(@PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network) {
        return execute(new Command(Kind.READ_CURRENT,company,network,uuid(draftId),null,null,null,null,null,null,null,null));
    }
    @Operation(operationId="readEffectiveEngineeringRevision",summary="Resolve committed ledger-as-of history with half-open event boundaries")
    @GetMapping("/effective") public Publication readEffective(@PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestParam("asOf") String at) {
        return execute(new Command(Kind.READ_EFFECTIVE,company,network,uuid(draftId),null,null,null,null,null,asOf(at),null,null));
    }
    @Operation(operationId="listEngineeringRevisionHistory",summary="Read a bounded descending context history window")
    @GetMapping("/history") public Publication listHistory(@PathVariable("draftId") String draftId,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,
            @RequestParam(value="limit",defaultValue="100") Integer limit,@RequestParam(value="cursor",required=false) String cursor,
            @RequestParam(value="asOf",required=false) String at) {
        return execute(new Command(Kind.LIST_HISTORY,company,network,uuid(draftId),null,null,null,null,null,asOf(at),limit,cursor));
    }
    @Operation(operationId="readEngineeringPublicationRequest",summary="Resolve only the currently authorized actor's request receipt")
    @GetMapping("/requests/{requestId}") public Publication readRequest(@PathVariable("draftId") String draftId,
            @PathVariable("requestId") String requestId,@RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network) {
        return execute(new Command(Kind.READ_REQUEST,company,network,uuid(draftId),uuid(requestId),null,null,null,null,null,null,null));
    }
}
