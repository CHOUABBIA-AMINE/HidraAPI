/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFacilityNodeBindingRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for FacilityNodeBinding.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.FacilityNodeBindingRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.FacilityNodeBinding;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityNodeBindingJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaFacilityNodeBindingRepositoryAdapter implements FacilityNodeBindingRepositoryPort {
    private final FacilityNodeBindingJpaRepository repository;
    public JpaFacilityNodeBindingRepositoryAdapter(FacilityNodeBindingJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "FacilityNodeBindingJpaRepository must not be null."); }
    public FacilityNodeBinding save(FacilityNodeBinding model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<FacilityNodeBinding> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
