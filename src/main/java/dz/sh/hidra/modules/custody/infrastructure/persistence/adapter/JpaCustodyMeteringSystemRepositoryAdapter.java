/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyMeteringSystemRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyMeteringSystem.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyMeteringSystemRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeteringSystem;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyMeteringSystemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyMeteringSystem.
 */
@Component
public class JpaCustodyMeteringSystemRepositoryAdapter implements CustodyMeteringSystemRepositoryPort {

    private final CustodyMeteringSystemJpaRepository repository;

    public JpaCustodyMeteringSystemRepositoryAdapter(CustodyMeteringSystemJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyMeteringSystemJpaRepository must not be null.");
    }

    @Override
    public CustodyMeteringSystem save(CustodyMeteringSystem model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyMeteringSystem> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
