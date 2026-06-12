/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetMeterReadingReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetMeterReadingReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetMeterReadingReferenceRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetMeterReadingReference;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetMeterReadingReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetMeterReadingReference.
 */
@Component
public class JpaAssetMeterReadingReferenceRepositoryAdapter implements AssetMeterReadingReferenceRepositoryPort {

    private final AssetMeterReadingReferenceJpaRepository repository;

    public JpaAssetMeterReadingReferenceRepositoryAdapter(AssetMeterReadingReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetMeterReadingReferenceJpaRepository must not be null.");
    }

    @Override
    public AssetMeterReadingReference save(AssetMeterReadingReference model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetMeterReadingReference> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
