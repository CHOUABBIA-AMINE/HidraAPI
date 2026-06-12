/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEquipmentTypeRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for EquipmentType.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentTypeRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.EquipmentType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentTypeJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaEquipmentTypeRepositoryAdapter implements EquipmentTypeRepositoryPort {
    private final EquipmentTypeJpaRepository repository;
    public JpaEquipmentTypeRepositoryAdapter(EquipmentTypeJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "EquipmentTypeJpaRepository must not be null."); }
    public EquipmentType save(EquipmentType model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<EquipmentType> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
