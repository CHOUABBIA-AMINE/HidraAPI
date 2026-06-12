/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFacilityAttributeValueRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for FacilityAttributeValue.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.FacilityAttributeValueRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.FacilityAttributeValue;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityAttributeValueJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaFacilityAttributeValueRepositoryAdapter implements FacilityAttributeValueRepositoryPort {
    private final FacilityAttributeValueJpaRepository repository;
    public JpaFacilityAttributeValueRepositoryAdapter(FacilityAttributeValueJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "FacilityAttributeValueJpaRepository must not be null."); }
    public FacilityAttributeValue save(FacilityAttributeValue model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<FacilityAttributeValue> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
