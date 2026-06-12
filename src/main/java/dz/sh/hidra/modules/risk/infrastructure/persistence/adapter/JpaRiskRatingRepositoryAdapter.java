/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskRatingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskRating.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskRatingRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskRating;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskRatingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskRating.
 */
@Component
public class JpaRiskRatingRepositoryAdapter implements RiskRatingRepositoryPort {

    private final RiskRatingJpaRepository repository;

    public JpaRiskRatingRepositoryAdapter(RiskRatingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskRatingJpaRepository must not be null.");
    }

    @Override
    public RiskRating save(RiskRating model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskRating> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
