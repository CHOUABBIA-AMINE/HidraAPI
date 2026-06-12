/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditCatalogTranslation;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditCatalogTranslation.
 */
@Component
public class JpaAuditCatalogTranslationRepositoryAdapter implements AuditCatalogTranslationRepositoryPort {

    private final AuditCatalogTranslationJpaRepository repository;

    public JpaAuditCatalogTranslationRepositoryAdapter(AuditCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public AuditCatalogTranslation save(AuditCatalogTranslation model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditCatalogTranslation> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
