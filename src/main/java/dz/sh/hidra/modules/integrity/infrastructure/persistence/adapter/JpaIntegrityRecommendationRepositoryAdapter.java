/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityRecommendationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityRecommendation.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityRecommendationRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityRecommendation;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityRecommendationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityRecommendation.
 */
@Component
public class JpaIntegrityRecommendationRepositoryAdapter implements IntegrityRecommendationRepositoryPort {

    private final IntegrityRecommendationJpaRepository repository;

    public JpaIntegrityRecommendationRepositoryAdapter(IntegrityRecommendationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrityRecommendationJpaRepository must not be null.");
    }

    @Override
    public IntegrityRecommendation save(IntegrityRecommendation model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityRecommendation> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
