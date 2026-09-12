/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetConcurrencyController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.controller
 *
 * @Description : Publishes the typed maintainable-asset read and stale-write-protected metadata update contract.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.controller;

import dz.sh.hidra.modules.assets.application.port.in.GetMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.application.port.in.UpdateMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;
import dz.sh.hidra.modules.assets.domain.value.AssetLifecycleStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/assets/maintainable-assets")
public class MaintainableAssetConcurrencyController {

    private final GetMaintainableAssetUseCase getUseCase;
    private final UpdateMaintainableAssetUseCase updateUseCase;

    public MaintainableAssetConcurrencyController(
            GetMaintainableAssetUseCase getUseCase,
            UpdateMaintainableAssetUseCase updateUseCase
    ) {
        this.getUseCase = Objects.requireNonNull(getUseCase, "GetMaintainableAssetUseCase must not be null.");
        this.updateUseCase = Objects.requireNonNull(updateUseCase, "UpdateMaintainableAssetUseCase must not be null.");
    }

    @GetMapping("/{assetId}")
    public Response get(@PathVariable String assetId) {
        return Response.from(getUseCase.get(assetId));
    }

    @PatchMapping("/{assetId}")
    public Response update(
            @PathVariable String assetId,
            @Valid @RequestBody Request request
    ) {
        MaintainableAsset asset = updateUseCase.update(assetId, new UpdateMaintainableAssetUseCase.Command(
                request.expectedUpdatedAt(), request.assetName()
        ));
        return Response.from(asset);
    }

    public record Request(
            @NotNull Instant expectedUpdatedAt,
            @NotBlank String assetName
    ) {
    }

    public record Response(
            String id,
            String assetNumber,
            String assetCode,
            String assetName,
            String assetTypeId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            AssetLifecycleStatus status,
            String criticalityId,
            Instant registeredAt,
            Instant updatedAt
    ) {
        private static Response from(MaintainableAsset asset) {
            return new Response(
                    asset.id(),
                    asset.assetNumber(),
                    asset.assetCode(),
                    asset.assetName(),
                    asset.assetTypeId(),
                    asset.topologyAssetTypeCode(),
                    asset.topologyAssetId(),
                    asset.status(),
                    asset.criticalityId(),
                    asset.registeredAt(),
                    asset.updatedAt()
            );
        }
    }
}
