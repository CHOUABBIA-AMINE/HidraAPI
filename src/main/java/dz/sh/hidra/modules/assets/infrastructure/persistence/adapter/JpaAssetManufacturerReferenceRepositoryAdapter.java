/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetManufacturerReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetManufacturerReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetManufacturerReferenceRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetManufacturerReference;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetManufacturerReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetManufacturerReference.
 */
@Component
public class JpaAssetManufacturerReferenceRepositoryAdapter implements AssetManufacturerReferenceRepositoryPort {

    private final AssetManufacturerReferenceJpaRepository repository;

    public JpaAssetManufacturerReferenceRepositoryAdapter(AssetManufacturerReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetManufacturerReferenceJpaRepository must not be null.");
    }

    @Override
    public AssetManufacturerReference save(AssetManufacturerReference model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetManufacturerReference> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
