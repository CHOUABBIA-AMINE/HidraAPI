/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityCaseStatusHistoryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityCaseStatusHistory.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityCaseStatusHistoryRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityCaseStatusHistory;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityCaseStatusHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityCaseStatusHistory.
 */
@Component
public class JpaIntegrityCaseStatusHistoryRepositoryAdapter implements IntegrityCaseStatusHistoryRepositoryPort {

    private final IntegrityCaseStatusHistoryJpaRepository repository;

    public JpaIntegrityCaseStatusHistoryRepositoryAdapter(IntegrityCaseStatusHistoryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrityCaseStatusHistoryJpaRepository must not be null.");
    }

    @Override
    public IntegrityCaseStatusHistory save(IntegrityCaseStatusHistory model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityCaseStatusHistory> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
