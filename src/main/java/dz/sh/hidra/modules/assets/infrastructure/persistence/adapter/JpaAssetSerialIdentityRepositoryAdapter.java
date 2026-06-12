/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetSerialIdentityRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetSerialIdentity.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetSerialIdentityRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetSerialIdentity;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetSerialIdentityJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetSerialIdentity.
 */
@Component
public class JpaAssetSerialIdentityRepositoryAdapter implements AssetSerialIdentityRepositoryPort {

    private final AssetSerialIdentityJpaRepository repository;

    public JpaAssetSerialIdentityRepositoryAdapter(AssetSerialIdentityJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetSerialIdentityJpaRepository must not be null.");
    }

    @Override
    public AssetSerialIdentity save(AssetSerialIdentity model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetSerialIdentity> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
