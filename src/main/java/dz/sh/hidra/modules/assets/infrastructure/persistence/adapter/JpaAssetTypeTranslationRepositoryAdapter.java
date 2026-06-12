/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetTypeTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetTypeTranslation.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetTypeTranslationRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetTypeTranslation;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetTypeTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetTypeTranslation.
 */
@Component
public class JpaAssetTypeTranslationRepositoryAdapter implements AssetTypeTranslationRepositoryPort {

    private final AssetTypeTranslationJpaRepository repository;

    public JpaAssetTypeTranslationRepositoryAdapter(AssetTypeTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetTypeTranslationJpaRepository must not be null.");
    }

    @Override
    public AssetTypeTranslation save(AssetTypeTranslation model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetTypeTranslation> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
