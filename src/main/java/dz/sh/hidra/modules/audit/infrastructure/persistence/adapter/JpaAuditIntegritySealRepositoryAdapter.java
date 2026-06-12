/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditIntegritySealRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditIntegritySeal.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditIntegritySealRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditIntegritySeal;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditIntegritySealJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditIntegritySeal.
 */
@Component
public class JpaAuditIntegritySealRepositoryAdapter implements AuditIntegritySealRepositoryPort {

    private final AuditIntegritySealJpaRepository repository;

    public JpaAuditIntegritySealRepositoryAdapter(AuditIntegritySealJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditIntegritySealJpaRepository must not be null.");
    }

    @Override
    public AuditIntegritySeal save(AuditIntegritySeal model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditIntegritySeal> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
