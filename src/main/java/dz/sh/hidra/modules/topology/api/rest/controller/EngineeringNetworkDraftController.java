/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.*;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringNetworkDraftRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringNetworkDraftResponses;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringNetworkDraftResponses.Draft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/topology/engineering-networks")
public class EngineeringNetworkDraftController {
    private final EngineeringNetworkDraftUseCase useCase;
    public EngineeringNetworkDraftController(EngineeringNetworkDraftUseCase u){useCase=Objects.requireNonNull(u);}
    @Operation(operationId="createEngineeringNetworkDraft",summary="Create or replay a company-scoped engineering draft")
    @PostMapping public ResponseEntity<Draft> create(@RequestBody Create body){
        var r=useCase.execute(new Command(Kind.CREATE,body.companyPartyId(),body.pipelineSystemId(),null,
            EngineeringNetworkDraft.uuid(body.creationRequestId()),null,body.metadata()));
        return ResponseEntity.status(r.replayed()?200:201).body(EngineeringNetworkDraftResponses.from(r));
    }
    @Operation(operationId="readEngineeringNetworkDraft",summary="Read the current scoped engineering draft")
    @GetMapping("/{draftId}") public Draft read(@PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network){
        return EngineeringNetworkDraftResponses.from(useCase.execute(new Command(Kind.READ,company,network,EngineeringNetworkDraft.uuid(id),null,null,null)));
    }
    @Operation(operationId="updateEngineeringNetworkDraft",summary="Append engineering draft metadata at an expected version")
    @PutMapping("/{draftId}") public Draft update(@PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestBody Update body){
        return EngineeringNetworkDraftResponses.from(useCase.execute(new Command(Kind.UPDATE,company,network,EngineeringNetworkDraft.uuid(id),null,body.expectedVersion(),body.metadata())));
    }
    @Operation(operationId="validateEngineeringNetworkDraft",summary="Validate the exact current draft version without publication")
    @PostMapping("/{draftId}/validate") public Draft validate(@PathVariable("draftId") String id,
            @RequestParam("companyPartyId") String company,@RequestParam("pipelineSystemId") String network,@RequestBody Validate body){
        return EngineeringNetworkDraftResponses.from(useCase.execute(new Command(Kind.VALIDATE,company,network,EngineeringNetworkDraft.uuid(id),null,body.expectedVersion(),null)));
    }
}
