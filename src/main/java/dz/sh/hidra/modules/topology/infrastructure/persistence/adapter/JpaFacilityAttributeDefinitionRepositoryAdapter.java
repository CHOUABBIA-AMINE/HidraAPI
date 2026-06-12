/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFacilityAttributeDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for FacilityAttributeDefinition.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.FacilityAttributeDefinitionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.FacilityAttributeDefinition;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityAttributeDefinitionJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaFacilityAttributeDefinitionRepositoryAdapter implements FacilityAttributeDefinitionRepositoryPort {
    private final FacilityAttributeDefinitionJpaRepository repository;
    public JpaFacilityAttributeDefinitionRepositoryAdapter(FacilityAttributeDefinitionJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "FacilityAttributeDefinitionJpaRepository must not be null."); }
    public FacilityAttributeDefinition save(FacilityAttributeDefinition model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<FacilityAttributeDefinition> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
