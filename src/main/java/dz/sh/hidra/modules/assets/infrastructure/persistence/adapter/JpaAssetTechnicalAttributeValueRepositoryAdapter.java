/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetTechnicalAttributeValueRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetTechnicalAttributeValue.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetTechnicalAttributeValueRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetTechnicalAttributeValue;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetTechnicalAttributeValueJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetTechnicalAttributeValue.
 */
@Component
public class JpaAssetTechnicalAttributeValueRepositoryAdapter implements AssetTechnicalAttributeValueRepositoryPort {

    private final AssetTechnicalAttributeValueJpaRepository repository;

    public JpaAssetTechnicalAttributeValueRepositoryAdapter(AssetTechnicalAttributeValueJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetTechnicalAttributeValueJpaRepository must not be null.");
    }

    @Override
    public AssetTechnicalAttributeValue save(AssetTechnicalAttributeValue model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetTechnicalAttributeValue> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
