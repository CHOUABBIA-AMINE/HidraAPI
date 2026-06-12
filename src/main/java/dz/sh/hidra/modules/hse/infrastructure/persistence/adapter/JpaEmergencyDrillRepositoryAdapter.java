/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEmergencyDrillRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for EmergencyDrill.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.EmergencyDrillRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.EmergencyDrill;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.EmergencyDrillJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for EmergencyDrill.
 */
@Component
public class JpaEmergencyDrillRepositoryAdapter implements EmergencyDrillRepositoryPort {

    private final EmergencyDrillJpaRepository repository;

    public JpaEmergencyDrillRepositoryAdapter(EmergencyDrillJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "EmergencyDrillJpaRepository must not be null.");
    }

    @Override
    public EmergencyDrill save(EmergencyDrill model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<EmergencyDrill> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
