/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetLifecycleEventRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetLifecycleEvent.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetLifecycleEventRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetLifecycleEvent;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetLifecycleEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetLifecycleEvent.
 */
@Component
public class JpaAssetLifecycleEventRepositoryAdapter implements AssetLifecycleEventRepositoryPort {

    private final AssetLifecycleEventJpaRepository repository;

    public JpaAssetLifecycleEventRepositoryAdapter(AssetLifecycleEventJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetLifecycleEventJpaRepository must not be null.");
    }

    @Override
    public AssetLifecycleEvent save(AssetLifecycleEvent model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetLifecycleEvent> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
