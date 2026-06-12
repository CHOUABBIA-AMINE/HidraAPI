/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskReviewRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskReview.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskReviewRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskReview;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskReviewJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskReview.
 */
@Component
public class JpaRiskReviewRepositoryAdapter implements RiskReviewRepositoryPort {

    private final RiskReviewJpaRepository repository;

    public JpaRiskReviewRepositoryAdapter(RiskReviewJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskReviewJpaRepository must not be null.");
    }

    @Override
    public RiskReview save(RiskReview model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskReview> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
