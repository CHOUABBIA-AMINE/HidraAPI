/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineReadController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes protected exact, current, effective, history and comparison queries.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.mapper.EngineeringBaselinePublicationRestMapper;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselinePublicationResponses.Published;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselineReadResponses.*;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase.*;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import java.time.Instant;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topology/engineering-baselines/publications")
public final class EngineeringBaselineReadController {
    private final EngineeringBaselineReadUseCase owner;

    public EngineeringBaselineReadController(EngineeringBaselineReadUseCase owner) {
        this.owner = owner;
    }

    @GetMapping("/{receiptId}")
    @NativeProtectedOperation
    @Operation(operationId="readExactEngineeringBaseline",summary="Read immutable baseline publication by exact receipt")
    public Published exact(@PathVariable UUID receiptId, @RequestParam String companyPartyId,
                           @RequestParam String pipelineSystemId) {
        return EngineeringBaselinePublicationRestMapper.published(owner.read(
                new Query(Action.EXACT, companyPartyId, pipelineSystemId, receiptId, null, null, 0, null)).primary());
    }

    @GetMapping("/current")
    @NativeProtectedOperation
    @Operation(operationId="readCurrentEngineeringBaseline",summary="Read highest committed baseline publication epoch")
    public Published current(@RequestParam String companyPartyId, @RequestParam String pipelineSystemId) {
        return EngineeringBaselinePublicationRestMapper.published(owner.read(
                new Query(Action.CURRENT, companyPartyId, pipelineSystemId, null, null, null, 0, null)).primary());
    }

    @GetMapping("/effective")
    @NativeProtectedOperation
    @Operation(operationId="readEffectiveEngineeringBaseline",summary="Read published baseline effective at the requested instant")
    public Published effective(@RequestParam String companyPartyId, @RequestParam String pipelineSystemId,
                               @RequestParam Instant asOf) {
        return EngineeringBaselinePublicationRestMapper.published(owner.read(
                new Query(Action.EFFECTIVE, companyPartyId, pipelineSystemId, null, null, asOf, 0, null)).primary());
    }

    @GetMapping("/history")
    @NativeProtectedOperation
    @Operation(operationId="readEngineeringBaselineHistory",summary="Read bounded immutable publication history with a stable epoch cursor")
    public History history(@RequestParam String companyPartyId, @RequestParam String pipelineSystemId,
                           @RequestParam Instant asOf, @RequestParam(defaultValue="20") int limit,
                           @RequestParam(required=false) String cursor) {
        return EngineeringBaselinePublicationRestMapper.history(owner.read(
                new Query(Action.HISTORY, companyPartyId, pipelineSystemId, null, null, asOf, limit, cursor)));
    }

    @GetMapping("/compare")
    @NativeProtectedOperation
    @Operation(operationId="compareEngineeringBaselinePublications",summary="Compare two immutable publications within one authorized scope")
    public Comparison compare(@RequestParam String companyPartyId, @RequestParam String pipelineSystemId,
                              @RequestParam UUID leftReceiptId, @RequestParam UUID rightReceiptId) {
        return EngineeringBaselinePublicationRestMapper.comparison(owner.read(
                new Query(Action.COMPARE, companyPartyId, pipelineSystemId, leftReceiptId, rightReceiptId, null, 0, null)));
    }
}
