/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAssetDocumentReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AssetDocumentReference.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.AssetDocumentReferenceRepositoryPort;
import dz.sh.hidra.modules.assets.domain.model.AssetDocumentReference;
import dz.sh.hidra.modules.assets.infrastructure.persistence.mapper.AssetsPersistenceMapper;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.AssetDocumentReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AssetDocumentReference.
 */
@Component
public class JpaAssetDocumentReferenceRepositoryAdapter implements AssetDocumentReferenceRepositoryPort {

    private final AssetDocumentReferenceJpaRepository repository;

    public JpaAssetDocumentReferenceRepositoryAdapter(AssetDocumentReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AssetDocumentReferenceJpaRepository must not be null.");
    }

    @Override
    public AssetDocumentReference save(AssetDocumentReference model) {
        return AssetsPersistenceMapper.toDomain(repository.save(AssetsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AssetDocumentReference> findById(String id) {
        return repository.findById(id).map(AssetsPersistenceMapper::toDomain);
    }
}
