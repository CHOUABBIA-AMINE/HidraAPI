/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanningCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PlanningCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanningCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanningCatalogTranslation;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanningCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PlanningCatalogTranslation.
 */
@Component
public class JpaPlanningCatalogTranslationRepositoryAdapter implements PlanningCatalogTranslationRepositoryPort {

    private final PlanningCatalogTranslationJpaRepository repository;

    public JpaPlanningCatalogTranslationRepositoryAdapter(PlanningCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PlanningCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public PlanningCatalogTranslation save(PlanningCatalogTranslation model) {
        return PlanningPersistenceMapper.toDomain(repository.save(PlanningPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<PlanningCatalogTranslation> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
