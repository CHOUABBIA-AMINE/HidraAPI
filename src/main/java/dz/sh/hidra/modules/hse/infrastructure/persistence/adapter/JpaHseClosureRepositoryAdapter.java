/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseClosureRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseClosure.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseClosureRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseClosure;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseClosureJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseClosure.
 */
@Component
public class JpaHseClosureRepositoryAdapter implements HseClosureRepositoryPort {

    private final HseClosureJpaRepository repository;

    public JpaHseClosureRepositoryAdapter(HseClosureJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseClosureJpaRepository must not be null.");
    }

    @Override
    public HseClosure save(HseClosure model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseClosure> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
