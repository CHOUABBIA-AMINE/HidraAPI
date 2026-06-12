/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanningCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanningCatalogEntry.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanningCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanningCatalogEntry;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanningCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanningCatalogEntry.
 */
@Component
public class JpaPlanningCatalogEntryRepositoryAdapter implements PlanningCatalogEntryRepositoryPort {

    private final PlanningCatalogEntryJpaRepository repository;

    public JpaPlanningCatalogEntryRepositoryAdapter(PlanningCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PlanningCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public PlanningCatalogEntry save(PlanningCatalogEntry model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PlanningCatalogEntry> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
