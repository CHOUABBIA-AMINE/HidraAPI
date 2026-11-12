/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.mapper.EngineeringBaselinePublicationRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringBaselinePublicationRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselinePublicationResponses.*;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.*;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topology/engineering-baselines")
public final class EngineeringBaselinePublicationController {
    private final EngineeringBaselinePublicationUseCase owner;

    public EngineeringBaselinePublicationController(EngineeringBaselinePublicationUseCase owner) {
        this.owner = owner;
    }

    @PostMapping("/{manifestId}/submissions")
    @NativeProtectedOperation
    @Operation(operationId = "submitEngineeringBaseline", summary = "Submit one exact baseline revision for Workflow review")
    public Submitted submit(@PathVariable UUID manifestId, @RequestBody Submit body) {
        return EngineeringBaselinePublicationRestMapper.submitted(
                owner.execute(EngineeringBaselinePublicationRestMapper.submit(manifestId, body)).submission());
    }

    @PostMapping("/submissions/{submissionId}/publications")
    @NativeProtectedOperation
    @Operation(operationId = "publishEngineeringBaseline", summary = "Publish the independently approved exact baseline")
    public Published publish(@PathVariable UUID submissionId, @RequestBody Publish body) {
        return EngineeringBaselinePublicationRestMapper.published(
                owner.execute(EngineeringBaselinePublicationRestMapper.publish(submissionId, body)).receipt());
    }

    @GetMapping("/publication-receipts/{receiptId}")
    @NativeProtectedOperation
    @Operation(operationId = "readEngineeringBaselinePublicationReceipt", summary = "Read the exact publication receipt")
    public Published receipt(@PathVariable UUID receiptId, @RequestParam String companyPartyId,
                             @RequestParam String pipelineSystemId) {
        var command = new Command(Action.RECEIPT, companyPartyId, pipelineSystemId, receiptId, null,
                0, null, null, 0, null);
        return EngineeringBaselinePublicationRestMapper.published(owner.execute(command).receipt());
    }
}
