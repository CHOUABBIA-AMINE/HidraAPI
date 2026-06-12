/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetCatalogTranslation;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetCatalogTranslation.
 */
@Component
public class JpaAssetCatalogTranslationRepositoryAdapter implements AssetCatalogTranslationRepositoryPort {

    private final AssetCatalogTranslationJpaRepository repository;

    public JpaAssetCatalogTranslationRepositoryAdapter(AssetCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public AssetCatalogTranslation save(AssetCatalogTranslation model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetCatalogTranslation> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
