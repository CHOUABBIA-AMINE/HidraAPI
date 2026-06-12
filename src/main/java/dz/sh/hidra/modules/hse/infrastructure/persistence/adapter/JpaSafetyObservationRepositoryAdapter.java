/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSafetyObservationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SafetyObservation.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.SafetyObservationRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.SafetyObservation;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.SafetyObservationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SafetyObservation.
 */
@Component
public class JpaSafetyObservationRepositoryAdapter implements SafetyObservationRepositoryPort {

    private final SafetyObservationJpaRepository repository;

    public JpaSafetyObservationRepositoryAdapter(SafetyObservationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "SafetyObservationJpaRepository must not be null.");
    }

    @Override
    public SafetyObservation save(SafetyObservation model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<SafetyObservation> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
