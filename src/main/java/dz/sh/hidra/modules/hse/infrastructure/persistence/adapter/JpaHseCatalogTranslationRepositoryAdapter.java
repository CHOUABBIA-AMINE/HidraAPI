/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCatalogTranslation;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseCatalogTranslation.
 */
@Component
public class JpaHseCatalogTranslationRepositoryAdapter implements HseCatalogTranslationRepositoryPort {

    private final HseCatalogTranslationJpaRepository repository;

    public JpaHseCatalogTranslationRepositoryAdapter(HseCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public HseCatalogTranslation save(HseCatalogTranslation model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseCatalogTranslation> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
