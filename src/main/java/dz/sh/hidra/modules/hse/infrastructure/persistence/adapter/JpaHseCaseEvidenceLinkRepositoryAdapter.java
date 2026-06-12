/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseCaseEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseCaseEvidenceLink.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseCaseEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCaseEvidenceLink;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCaseEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseCaseEvidenceLink.
 */
@Component
public class JpaHseCaseEvidenceLinkRepositoryAdapter implements HseCaseEvidenceLinkRepositoryPort {

    private final HseCaseEvidenceLinkJpaRepository repository;

    public JpaHseCaseEvidenceLinkRepositoryAdapter(HseCaseEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseCaseEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public HseCaseEvidenceLink save(HseCaseEvidenceLink model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseCaseEvidenceLink> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
