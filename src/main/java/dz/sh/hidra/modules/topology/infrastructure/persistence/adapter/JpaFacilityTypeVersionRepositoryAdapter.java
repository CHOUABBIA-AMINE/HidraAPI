/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFacilityTypeVersionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for FacilityTypeVersion.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.FacilityTypeVersionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.FacilityTypeVersion;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityTypeVersionJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaFacilityTypeVersionRepositoryAdapter implements FacilityTypeVersionRepositoryPort {
    private final FacilityTypeVersionJpaRepository repository;
    public JpaFacilityTypeVersionRepositoryAdapter(FacilityTypeVersionJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "FacilityTypeVersionJpaRepository must not be null."); }
    public FacilityTypeVersion save(FacilityTypeVersion model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<FacilityTypeVersion> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
