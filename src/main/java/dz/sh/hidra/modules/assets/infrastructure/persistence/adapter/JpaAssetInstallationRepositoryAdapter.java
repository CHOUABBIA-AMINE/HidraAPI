/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetInstallationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetInstallation.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetInstallationRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetInstallation;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetInstallationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetInstallation.
 */
@Component
public class JpaAssetInstallationRepositoryAdapter implements AssetInstallationRepositoryPort {

    private final AssetInstallationJpaRepository repository;

    public JpaAssetInstallationRepositoryAdapter(AssetInstallationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetInstallationJpaRepository must not be null.");
    }

    @Override
    public AssetInstallation save(AssetInstallation model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetInstallation> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
