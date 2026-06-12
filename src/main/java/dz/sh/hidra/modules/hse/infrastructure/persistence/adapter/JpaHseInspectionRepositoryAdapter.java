/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseInspectionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseInspection.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseInspectionRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseInspection;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseInspectionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseInspection.
 */
@Component
public class JpaHseInspectionRepositoryAdapter implements HseInspectionRepositoryPort {

    private final HseInspectionJpaRepository repository;

    public JpaHseInspectionRepositoryAdapter(HseInspectionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseInspectionJpaRepository must not be null.");
    }

    @Override
    public HseInspection save(HseInspection model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseInspection> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
