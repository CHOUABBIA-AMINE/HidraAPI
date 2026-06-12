/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseCaseRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseCase.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseCaseRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCase;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCaseJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseCase.
 */
@Component
public class JpaHseCaseRepositoryAdapter implements HseCaseRepositoryPort {

    private final HseCaseJpaRepository repository;

    public JpaHseCaseRepositoryAdapter(HseCaseJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseCaseJpaRepository must not be null.");
    }

    @Override
    public HseCase save(HseCase model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseCase> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
