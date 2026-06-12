/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRemainingLifeEstimateRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RemainingLifeEstimate.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.RemainingLifeEstimateRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.RemainingLifeEstimate;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.RemainingLifeEstimateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RemainingLifeEstimate.
 */
@Component
public class JpaRemainingLifeEstimateRepositoryAdapter implements RemainingLifeEstimateRepositoryPort {

    private final RemainingLifeEstimateJpaRepository repository;

    public JpaRemainingLifeEstimateRepositoryAdapter(RemainingLifeEstimateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RemainingLifeEstimateJpaRepository must not be null.");
    }

    @Override
    public RemainingLifeEstimate save(RemainingLifeEstimate model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RemainingLifeEstimate> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
