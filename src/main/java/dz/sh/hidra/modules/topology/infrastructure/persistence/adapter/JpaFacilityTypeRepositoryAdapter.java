/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFacilityTypeRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for FacilityType.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.FacilityTypeRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.FacilityType;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityTypeJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaFacilityTypeRepositoryAdapter implements FacilityTypeRepositoryPort {
    private final FacilityTypeJpaRepository repository;
    public JpaFacilityTypeRepositoryAdapter(FacilityTypeJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "FacilityTypeJpaRepository must not be null."); }
    public FacilityType save(FacilityType model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<FacilityType> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
