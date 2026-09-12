/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetConcurrencyApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.service
 *
 * @Description : Provides typed maintainable-asset reads and concurrency-protected metadata updates.
 *
 */
package dz.sh.hidra.modules.assets.application.service;

import dz.sh.hidra.modules.assets.application.port.in.GetMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.application.port.in.UpdateMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.application.port.out.MaintainableAssetRepositoryPort;
import dz.sh.hidra.modules.assets.domain.exception.MaintainableAssetConflictException;
import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class MaintainableAssetConcurrencyApplicationService implements GetMaintainableAssetUseCase, UpdateMaintainableAssetUseCase {

    private final MaintainableAssetRepositoryPort repository;

    public MaintainableAssetConcurrencyApplicationService(MaintainableAssetRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "MaintainableAssetRepositoryPort must not be null.");
    }

    @Override
    public MaintainableAsset get(String assetId) {
        String normalizedId = requireAssetId(assetId);
        return repository.findById(normalizedId)
                .orElseThrow(() -> new NoSuchElementException("Unknown maintainable asset: " + normalizedId));
    }

    @Override
    @Transactional
    public MaintainableAsset update(String assetId, UpdateMaintainableAssetUseCase.Command command) {
        String normalizedId = requireAssetId(assetId);
        Objects.requireNonNull(command, "Maintainable asset update command must not be null.");

        MaintainableAsset asset = repository.findByIdForUpdate(normalizedId)
                .orElseThrow(() -> new NoSuchElementException("Unknown maintainable asset: " + normalizedId));

        if (!command.expectedUpdatedAt().equals(asset.updatedAt())) {
            throw new MaintainableAssetConflictException(
                    "Maintainable asset changed after it was loaded. Refetch the asset before retrying."
            );
        }

        Instant updatedAt = Instant.now();
        if (!updatedAt.isAfter(asset.updatedAt())) {
            updatedAt = asset.updatedAt().plusNanos(1);
        }

        return repository.save(new MaintainableAsset(
                asset.id(),
                asset.assetNumber(),
                asset.assetCode(),
                command.assetName(),
                asset.assetTypeId(),
                asset.topologyAssetTypeCode(),
                asset.topologyAssetId(),
                asset.topologyAssetCodeSnapshot(),
                asset.topologyAssetNameSnapshot(),
                asset.parentAssetId(),
                asset.status(),
                asset.criticalityId(),
                asset.ownerOrganizationUnitId(),
                asset.ownerOrganizationUnitNameSnapshot(),
                asset.manufacturerPartyId(),
                asset.manufacturerNameSnapshot(),
                asset.modelId(),
                asset.serialIdentityId(),
                asset.registeredAt(),
                asset.installedAt(),
                asset.commissionedAt(),
                asset.retiredAt(),
                asset.createdByActorId(),
                asset.createdAt(),
                updatedAt
        ));
    }

    private static String requireAssetId(String assetId) {
        if (assetId == null || assetId.isBlank()) {
            throw new IllegalArgumentException("assetId must not be null or blank.");
        }
        return assetId.trim();
    }
}
