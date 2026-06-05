/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter implementing EquipmentRepositoryPort.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.value.EquipmentId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;

/**
 * Persistence adapter implementing EquipmentRepositoryPort.
 *
 * <p>Business role:
 * Persists and retrieves topology equipment domain models through Spring Data JPA.
 *
 * <p>Architecture role:
 * This class adapts an application outbound port to infrastructure persistence. Application services
 * depend on the port, not on this adapter or Spring Data repository.
 *
 * <p>Validation:
 * Domain validation happens before persistence. Mapping restores domain value objects and model
 * invariants.
 *
 * <p>Usage:
 * Wire this adapter from topology infrastructure configuration.
 */
public final class EquipmentRepositoryAdapter implements EquipmentRepositoryPort {

    private final EquipmentJpaRepository jpaRepository;
    private final TopologyPersistenceMapper mapper;

    public EquipmentRepositoryAdapter(
            EquipmentJpaRepository jpaRepository,
            TopologyPersistenceMapper mapper) {

        this.jpaRepository = Objects.requireNonNull(jpaRepository, "EquipmentJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Topology persistence mapper must not be null.");
    }

    @Override
    public Equipment save(Equipment equipment) {
        Objects.requireNonNull(equipment, "Equipment must not be null.");
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(equipment)));
    }

    @Override
    public Optional<Equipment> findById(EquipmentId id) {
        Objects.requireNonNull(id, "EquipmentId must not be null.");
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Equipment> findByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.existsByCode(code.value());
    }
}
