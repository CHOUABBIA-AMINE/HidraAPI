/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityEvidenceLink.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityEvidenceLink;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityEvidenceLink.
 */
@Component
public class JpaIntegrityEvidenceLinkRepositoryAdapter implements IntegrityEvidenceLinkRepositoryPort {

    private final IntegrityEvidenceLinkJpaRepository repository;

    public JpaIntegrityEvidenceLinkRepositoryAdapter(IntegrityEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrityEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public IntegrityEvidenceLink save(IntegrityEvidenceLink model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityEvidenceLink> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
