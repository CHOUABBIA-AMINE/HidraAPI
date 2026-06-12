/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetSparePartCompatibilityRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetSparePartCompatibility.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetSparePartCompatibilityRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetSparePartCompatibility;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetSparePartCompatibilityJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetSparePartCompatibility.
 */
@Component
public class JpaAssetSparePartCompatibilityRepositoryAdapter implements AssetSparePartCompatibilityRepositoryPort {

    private final AssetSparePartCompatibilityJpaRepository repository;

    public JpaAssetSparePartCompatibilityRepositoryAdapter(AssetSparePartCompatibilityJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetSparePartCompatibilityJpaRepository must not be null.");
    }

    @Override
    public AssetSparePartCompatibility save(AssetSparePartCompatibility model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetSparePartCompatibility> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
