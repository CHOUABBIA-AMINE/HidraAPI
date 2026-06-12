/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEquipmentAttributeDefinitionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for EquipmentAttributeDefinition.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentAttributeDefinitionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.EquipmentAttributeDefinition;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentAttributeDefinitionJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaEquipmentAttributeDefinitionRepositoryAdapter implements EquipmentAttributeDefinitionRepositoryPort {
    private final EquipmentAttributeDefinitionJpaRepository repository;
    public JpaEquipmentAttributeDefinitionRepositoryAdapter(EquipmentAttributeDefinitionJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "EquipmentAttributeDefinitionJpaRepository must not be null."); }
    public EquipmentAttributeDefinition save(EquipmentAttributeDefinition model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<EquipmentAttributeDefinition> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
