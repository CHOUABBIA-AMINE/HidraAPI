/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEquipmentTypeVersionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for EquipmentTypeVersion.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentTypeVersionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.EquipmentTypeVersion;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentTypeVersionJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaEquipmentTypeVersionRepositoryAdapter implements EquipmentTypeVersionRepositoryPort {
    private final EquipmentTypeVersionJpaRepository repository;
    public JpaEquipmentTypeVersionRepositoryAdapter(EquipmentTypeVersionJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "EquipmentTypeVersionJpaRepository must not be null."); }
    public EquipmentTypeVersion save(EquipmentTypeVersion model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<EquipmentTypeVersion> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
