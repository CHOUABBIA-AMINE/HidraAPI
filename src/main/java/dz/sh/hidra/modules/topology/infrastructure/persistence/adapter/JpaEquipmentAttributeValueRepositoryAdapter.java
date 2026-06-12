/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEquipmentAttributeValueRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for EquipmentAttributeValue.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentAttributeValueRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.EquipmentAttributeValue;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentAttributeValueJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaEquipmentAttributeValueRepositoryAdapter implements EquipmentAttributeValueRepositoryPort {
    private final EquipmentAttributeValueJpaRepository repository;
    public JpaEquipmentAttributeValueRepositoryAdapter(EquipmentAttributeValueJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "EquipmentAttributeValueJpaRepository must not be null."); }
    public EquipmentAttributeValue save(EquipmentAttributeValue model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<EquipmentAttributeValue> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
