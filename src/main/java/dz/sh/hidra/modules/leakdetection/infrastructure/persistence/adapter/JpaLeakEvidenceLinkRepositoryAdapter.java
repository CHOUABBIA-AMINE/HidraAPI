/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakEvidenceLink.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakEvidenceLink;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakEvidenceLink.
 */
@Component
public class JpaLeakEvidenceLinkRepositoryAdapter implements LeakEvidenceLinkRepositoryPort {

    private final LeakEvidenceLinkJpaRepository repository;

    public JpaLeakEvidenceLinkRepositoryAdapter(LeakEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public LeakEvidenceLink save(LeakEvidenceLink model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakEvidenceLink> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
