/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEnvironmentalEventRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for EnvironmentalEvent.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.EnvironmentalEventRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.EnvironmentalEvent;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.EnvironmentalEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for EnvironmentalEvent.
 */
@Component
public class JpaEnvironmentalEventRepositoryAdapter implements EnvironmentalEventRepositoryPort {

    private final EnvironmentalEventJpaRepository repository;

    public JpaEnvironmentalEventRepositoryAdapter(EnvironmentalEventJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "EnvironmentalEventJpaRepository must not be null.");
    }

    @Override
    public EnvironmentalEvent save(EnvironmentalEvent model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<EnvironmentalEvent> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
