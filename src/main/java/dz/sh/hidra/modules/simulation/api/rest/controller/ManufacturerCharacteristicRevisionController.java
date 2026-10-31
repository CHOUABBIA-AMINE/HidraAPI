package dz.sh.hidra.modules.simulation.api.rest.controller;

import dz.sh.hidra.modules.simulation.api.rest.mapper.ManufacturerCharacteristicRevisionRestMapper;
import dz.sh.hidra.modules.simulation.api.rest.request.ManufacturerCharacteristicRevisionRequests;
import dz.sh.hidra.modules.simulation.api.rest.response.ManufacturerCharacteristicRevisionResponses;
import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase.Command;
import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase.Operation;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import java.util.Objects;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/** Protected documentary manufacturer claims, not qualified equipment capabilities or actuation. */
@RestController
@RequestMapping("/api/v1/simulation/manufacturer-characteristics")
public final class ManufacturerCharacteristicRevisionController {
    private final ManufacturerCharacteristicRevisionUseCase owner;

    public ManufacturerCharacteristicRevisionController(ManufacturerCharacteristicRevisionUseCase owner) {
        this.owner=Objects.requireNonNull(owner);
    }

    private static void authenticated() {
        var principal=SecurityContextHolder.getContext().getAuthentication();
        if(principal==null || !principal.isAuthenticated()
                || principal instanceof AnonymousAuthenticationToken)
            throw new AuthenticationCredentialsNotFoundException("Manufacturer characteristic authentication required");
    }

    @NativeProtectedOperation
    @PutMapping("/{sourceId}/revisions/{revisionId}")
    @io.swagger.v3.oas.annotations.Operation(operationId="saveManufacturerCharacteristicRevision",
            summary="Save an exact immutable manufacturer-supplied documentary revision")
    public ManufacturerCharacteristicRevisionResponses.Receipt save(
            @PathVariable("sourceId") String sourceId,
            @PathVariable("revisionId") String revisionId,
            @Valid @RequestBody ManufacturerCharacteristicRevisionRequests.Save body) {
        authenticated();
        Objects.requireNonNull(body,"save body required");
        var original=ManufacturerCharacteristicRevisionRestMapper.toDomain(body.revision());
        return ManufacturerCharacteristicRevisionRestMapper.receipt(owner.execute(new Command(
            Operation.SAVE,body.companyPartyId(),sourceId,revisionId,original,null)));
    }

    @NativeProtectedOperation
    @GetMapping("/{sourceId}/revisions/{revisionId}")
    @io.swagger.v3.oas.annotations.Operation(operationId="readManufacturerCharacteristicRevision",
            summary="Read an authorized immutable documentary revision without certificate evidence")
    public ManufacturerCharacteristicRevisionResponses.Revision revision(
            @PathVariable("sourceId") String sourceId,
            @PathVariable("revisionId") String revisionId,
            @RequestParam("companyPartyId") String companyPartyId) {
        authenticated();
        return ManufacturerCharacteristicRevisionRestMapper.revision(owner.execute(new Command(
            Operation.READ_REVISION,companyPartyId,sourceId,revisionId,null,null)));
    }

    @NativeProtectedOperation
    @GetMapping("/{sourceId}/history")
    @io.swagger.v3.oas.annotations.Operation(operationId="pageManufacturerCharacteristicHistory",
            summary="Page complete-source-authorized immutable history with an opaque live cursor")
    public ManufacturerCharacteristicRevisionResponses.HistoryPage history(
            @PathVariable("sourceId") String sourceId,
            @RequestParam("companyPartyId") String companyPartyId,
            @RequestParam(value="size",required=false) Integer size,
            @RequestParam(value="cursor",required=false) String cursor) {
        authenticated();
        return ManufacturerCharacteristicRevisionRestMapper.history(owner.pageHistory(
            new ManufacturerCharacteristicRevisionUseCase.HistoryPageQuery(
                companyPartyId,sourceId,size,cursor)));
    }

    @NativeProtectedOperation
    @GetMapping("/{sourceId}/revisions/{revisionId}/certificate")
    @io.swagger.v3.oas.annotations.Operation(operationId="readManufacturerCharacteristicCertificate",
            summary="Read an authorized unverified documentary certificate reference separately")
    public ManufacturerCharacteristicRevisionResponses.Certificate certificate(
            @PathVariable("sourceId") String sourceId,
            @PathVariable("revisionId") String revisionId,
            @RequestParam("companyPartyId") String companyPartyId) {
        authenticated();
        return ManufacturerCharacteristicRevisionRestMapper.certificate(owner.execute(new Command(
            Operation.READ_CERTIFICATE,companyPartyId,sourceId,revisionId,null,null)));
    }

    @NativeProtectedOperation
    @PostMapping("/{sourceId}/revisions/{revisionId}/qualification")
    @io.swagger.v3.oas.annotations.Operation(operationId="qualifyManufacturerCharacteristicRevision",
            summary="Record exact approved Workflow evidence for a documentary revision")
    public ManufacturerCharacteristicRevisionResponses.Receipt qualify(
            @PathVariable("sourceId") String sourceId,
            @PathVariable("revisionId") String revisionId,
            @Valid @RequestBody ManufacturerCharacteristicRevisionRequests.Qualify body) {
        authenticated();
        Objects.requireNonNull(body,"qualification body required");
        var v=Objects.requireNonNull(body.approval(),"exact Workflow approval required");
        var approval=new ManufacturerCharacteristicRevisionUseCase.ApprovalReference(
            v.definitionId(),v.definitionVersion(),v.targetTypeId(),v.purposeId(),
            v.instanceId(),v.taskId(),v.actionId());
        return ManufacturerCharacteristicRevisionRestMapper.receipt(owner.execute(new Command(
            Operation.QUALIFY,body.companyPartyId(),sourceId,revisionId,null,approval)));
    }
}
