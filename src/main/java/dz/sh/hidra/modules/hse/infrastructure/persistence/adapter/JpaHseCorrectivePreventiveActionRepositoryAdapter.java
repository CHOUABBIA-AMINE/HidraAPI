/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseCorrectivePreventiveActionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseCorrectivePreventiveAction.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseCorrectivePreventiveActionRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCorrectivePreventiveActionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseCorrectivePreventiveAction.
 */
@Component
public class JpaHseCorrectivePreventiveActionRepositoryAdapter implements HseCorrectivePreventiveActionRepositoryPort {

    private final HseCorrectivePreventiveActionJpaRepository repository;

    public JpaHseCorrectivePreventiveActionRepositoryAdapter(HseCorrectivePreventiveActionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseCorrectivePreventiveActionJpaRepository must not be null.");
    }

    @Override
    public HseCorrectivePreventiveAction save(HseCorrectivePreventiveAction model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseCorrectivePreventiveAction> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
