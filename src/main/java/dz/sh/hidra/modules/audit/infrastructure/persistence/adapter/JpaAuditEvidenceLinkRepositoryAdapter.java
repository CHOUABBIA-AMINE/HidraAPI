/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuditEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AuditEvidenceLink.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.AuditEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.AuditEvidenceLink;
import dz.sh.hidra.modules.audit.infrastructure.persistence.mapper.AuditPersistenceMapper;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.AuditEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuditEvidenceLink.
 */
@Component
public class JpaAuditEvidenceLinkRepositoryAdapter implements AuditEvidenceLinkRepositoryPort {

    private final AuditEvidenceLinkJpaRepository repository;

    public JpaAuditEvidenceLinkRepositoryAdapter(AuditEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuditEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public AuditEvidenceLink save(AuditEvidenceLink model) {
        return AuditPersistenceMapper.toDomain(repository.save(AuditPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuditEvidenceLink> findById(String id) {
        return repository.findById(id).map(AuditPersistenceMapper::toDomain);
    }
}
