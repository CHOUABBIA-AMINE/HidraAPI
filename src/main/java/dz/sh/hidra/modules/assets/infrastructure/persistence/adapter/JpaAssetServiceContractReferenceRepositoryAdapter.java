/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetServiceContractReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetServiceContractReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetServiceContractReferenceRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetServiceContractReference;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetServiceContractReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetServiceContractReference.
 */
@Component
public class JpaAssetServiceContractReferenceRepositoryAdapter implements AssetServiceContractReferenceRepositoryPort {

    private final AssetServiceContractReferenceJpaRepository repository;

    public JpaAssetServiceContractReferenceRepositoryAdapter(AssetServiceContractReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetServiceContractReferenceJpaRepository must not be null.");
    }

    @Override
    public AssetServiceContractReference save(AssetServiceContractReference model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetServiceContractReference> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
