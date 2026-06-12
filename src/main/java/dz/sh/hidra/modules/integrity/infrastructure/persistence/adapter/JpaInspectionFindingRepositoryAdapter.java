/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaInspectionFindingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for InspectionFinding.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.InspectionFindingRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.InspectionFinding;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.InspectionFindingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for InspectionFinding.
 */
@Component
public class JpaInspectionFindingRepositoryAdapter implements InspectionFindingRepositoryPort {

    private final InspectionFindingJpaRepository repository;

    public JpaInspectionFindingRepositoryAdapter(InspectionFindingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "InspectionFindingJpaRepository must not be null.");
    }

    @Override
    public InspectionFinding save(InspectionFinding model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<InspectionFinding> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
