/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetConditionRecordRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetConditionRecord.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetConditionRecordRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetConditionRecord;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetConditionRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetConditionRecord.
 */
@Component
public class JpaAssetConditionRecordRepositoryAdapter implements AssetConditionRecordRepositoryPort {

    private final AssetConditionRecordJpaRepository repository;

    public JpaAssetConditionRecordRepositoryAdapter(AssetConditionRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetConditionRecordJpaRepository must not be null.");
    }

    @Override
    public AssetConditionRecord save(AssetConditionRecord model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetConditionRecord> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
