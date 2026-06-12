/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseCaseStatusHistoryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseCaseStatusHistory.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseCaseStatusHistoryRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCaseStatusHistory;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCaseStatusHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseCaseStatusHistory.
 */
@Component
public class JpaHseCaseStatusHistoryRepositoryAdapter implements HseCaseStatusHistoryRepositoryPort {

    private final HseCaseStatusHistoryJpaRepository repository;

    public JpaHseCaseStatusHistoryRepositoryAdapter(HseCaseStatusHistoryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseCaseStatusHistoryJpaRepository must not be null.");
    }

    @Override
    public HseCaseStatusHistory save(HseCaseStatusHistory model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseCaseStatusHistory> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
