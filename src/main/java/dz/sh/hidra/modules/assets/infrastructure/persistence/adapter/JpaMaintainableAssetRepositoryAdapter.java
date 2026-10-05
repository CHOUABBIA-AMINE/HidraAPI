/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMaintainableAssetRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MaintainableAsset.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.MaintainableAssetRepositoryPort;
import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintainableAssetJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MaintainableAsset.
 */
@Component
public class JpaMaintainableAssetRepositoryAdapter implements MaintainableAssetRepositoryPort {

    private final MaintainableAssetJpaRepository repository;

    public JpaMaintainableAssetRepositoryAdapter(MaintainableAssetJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MaintainableAssetJpaRepository must not be null.");
    }

    @Override
    public MaintainableAsset save(MaintainableAsset model) {
        Objects.requireNonNull(model, "MaintainableAsset must not be null.");

        if (model.parentAssetId() != null && !repository.existsById(model.parentAssetId())) {
            throw new InvalidAssetsValueException(
                    "MaintainableAsset parentAssetId must reference an existing MaintainableAsset."
            );
        }
        if (model.modelId() != null && !repository.existsModelById(model.modelId())) {
            throw new InvalidAssetsValueException(
                    "MaintainableAsset modelId must reference an existing AssetModel."
            );
        }
        if (model.serialIdentityId() != null
                && !repository.existsSerialIdentityById(model.serialIdentityId())) {
            throw new InvalidAssetsValueException(
                    "MaintainableAsset serialIdentityId must reference an existing AssetSerialIdentity."
            );
        }

        return AssetsPersistenceMapper.toDomain(
                repository.save(AssetsPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<MaintainableAsset> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }

    @Override
    public Optional<MaintainableAsset> findByIdForUpdate(String id) {
        return repository.findByIdForUpdate(id).map(AssetsPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsModelById(String modelId) {
        return modelId != null && !modelId.isBlank() && repository.existsModelById(modelId.trim());
    }

    @Override
    public boolean existsSerialIdentityById(String serialIdentityId) {
        return serialIdentityId != null
                && !serialIdentityId.isBlank()
                && repository.existsSerialIdentityById(serialIdentityId.trim());
    }
}
