/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyCatalogTranslation;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyCatalogTranslation.
 */
@Component
public class JpaCustodyCatalogTranslationRepositoryAdapter implements CustodyCatalogTranslationRepositoryPort {

    private final CustodyCatalogTranslationJpaRepository repository;

    public JpaCustodyCatalogTranslationRepositoryAdapter(CustodyCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public CustodyCatalogTranslation save(CustodyCatalogTranslation model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyCatalogTranslation> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
