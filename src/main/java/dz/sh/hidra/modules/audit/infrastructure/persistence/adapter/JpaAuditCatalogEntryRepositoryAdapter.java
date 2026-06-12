/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditCatalogEntry.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditCatalogEntry;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditCatalogEntry.
 */
@Component
public class JpaAuditCatalogEntryRepositoryAdapter implements AuditCatalogEntryRepositoryPort {

    private final AuditCatalogEntryJpaRepository repository;

    public JpaAuditCatalogEntryRepositoryAdapter(AuditCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public AuditCatalogEntry save(AuditCatalogEntry model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditCatalogEntry> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
