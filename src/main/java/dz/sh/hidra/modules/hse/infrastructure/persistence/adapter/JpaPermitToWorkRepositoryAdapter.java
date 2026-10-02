/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPermitToWorkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PermitToWork.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.PermitToWorkRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.PermitToWork;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.PermitToWorkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PermitToWork.
 */
@Component
public class JpaPermitToWorkRepositoryAdapter implements PermitToWorkRepositoryPort {

    private final PermitToWorkJpaRepository repository;

    public JpaPermitToWorkRepositoryAdapter(PermitToWorkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PermitToWorkJpaRepository must not be null.");
    }

    @Override
    public PermitToWork save(PermitToWork model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PermitToWork> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
