/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseImpactAssessmentRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseImpactAssessment.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseImpactAssessmentRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseImpactAssessment;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseImpactAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseImpactAssessment.
 */
@Component
public class JpaHseImpactAssessmentRepositoryAdapter implements HseImpactAssessmentRepositoryPort {

    private final HseImpactAssessmentJpaRepository repository;

    public JpaHseImpactAssessmentRepositoryAdapter(HseImpactAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseImpactAssessmentJpaRepository must not be null.");
    }

    @Override
    public HseImpactAssessment save(HseImpactAssessment model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseImpactAssessment> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
