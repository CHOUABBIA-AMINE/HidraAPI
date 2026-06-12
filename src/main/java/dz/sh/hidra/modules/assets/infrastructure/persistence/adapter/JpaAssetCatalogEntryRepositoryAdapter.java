/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetCatalogEntry.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetCatalogEntry;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetCatalogEntry.
 */
@Component
public class JpaAssetCatalogEntryRepositoryAdapter implements AssetCatalogEntryRepositoryPort {

    private final AssetCatalogEntryJpaRepository repository;

    public JpaAssetCatalogEntryRepositoryAdapter(AssetCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public AssetCatalogEntry save(AssetCatalogEntry model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetCatalogEntry> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
